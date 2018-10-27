package co.com.foundation.integration.actor;

import java.util.concurrent.TimeUnit;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.japi.pf.FI.UnitApply;
import akka.japi.pf.ReceiveBuilder;
import co.com.foundation.integration.actor.exceptions.RetryException;
import co.com.foundation.integration.business.handler.HandlerCoordinator;
import co.com.foundation.integration.marshaller.impl.JAXBUnmarshaller;
import co.com.foundation.integration.util.MQDispatcher;
import co.com.foundation.integration.workmodel.PostponedWorkItem;
import co.com.foundation.integration.workmodel.RequestWorkItem;
import co.com.foundation.integration.workmodel.WorkItem;
import universal_bank.crm.enterprise_model.framework._1_0.CRMBusinessEvent;
import universal_bank.crm.enterprise_model.framework._1_0.FinancialActionDomainObject;

/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad Javeriana (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación 
 * Licenciado bajo el esquema Academic Free License version 2.1 
 *
 * AES - PICA (http://www.javeriana.edu.co/programas/especializacion-en-arquitectura-empresarial-de-software)
 * Ejercicio: universal bank integration
 * Autor: PICA
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 
 */

public class WorkerActor extends AbstractActor {

	private static final Logger LOGGER = LogManager.getLogger(WorkerActor.class);

	private static final BiPredicate<Exception, WorkItem> EXECUTE_RETRY = (e, w) -> e instanceof RetryException && w.getAttempts() < ActorConstants.MAX_ATTEMPTS;
	private static final Predicate<FinancialActionDomainObject> CONTAINS_SERVICE_OR_PRODUCT = action -> action.getFinancialProduct() != null || action.getFinancialService() != null;

	private ActorRef masterActor;
	private MQDispatcher errorQueueDispatcher;
	private HandlerCoordinator handlerCoordinator;

	public WorkerActor(ActorRef masterActor, MQDispatcher dispatcher, HandlerCoordinator handlerCoordinator) {
		super();
		this.masterActor = masterActor;
		this.errorQueueDispatcher = dispatcher;
		this.handlerCoordinator = handlerCoordinator;
	}

	@Override
	public Receive createReceive() {
		return ReceiveBuilder.create().match(WorkItem.class,
				work -> work.getStatus().equals(WorkItem.WORK_STATE.ASSIGNED_TO_WORKER), processAssignedWork())
				.match(RequestWorkItem.class, askForWork()).build();
	}

	private UnitApply<RequestWorkItem> askForWork() {
		return (request) -> {
			
			LOGGER.info("asking for work to master-actor");
			masterActor.tell(request, getSelf());
		};
	}
	
	private UnitApply<WorkItem> processAssignedWork() {
		return (workItem) -> {

			LOGGER.info("processing work in worker-actor: work-id -> {}", workItem.getId());

			try {
				
				CRMBusinessEvent crmBusinessEvent = JAXBUnmarshaller.newInstance().unmarshall( workItem.getXmlContent() );
				handlerCoordinator.startPipeline(crmBusinessEvent);
				
			} catch (Exception e) {
				handleError(workItem, e);
			}
		};
	}

	private void handleError(final WorkItem workItem, final Exception e) {

		try {

			boolean applyRetry = EXECUTE_RETRY.test(e, workItem);
			LOGGER.info("RETRY: evaluating retry: work-id -> {} apply-retry -> {} attempts -> {}", workItem.getId(),
					applyRetry, workItem.getAttempts());

			if (applyRetry) {

				LOGGER.info("applying delay: work-id -> {} milliseconds to sleep -> {}", workItem.getId(),
						ActorConstants.ERROR_DELAY_TIME);
				
				workItem.trackAttempt();
				
				CRMBusinessEvent crmBusinessEvent = JAXBUnmarshaller.newInstance().unmarshall( workItem.getXmlContent() );
				
				boolean notifyError = crmBusinessEvent.getEnterpriseOperationEventObject()
						.getFinancialActionDomainObject()
						.stream()
						.filter( CONTAINS_SERVICE_OR_PRODUCT ).count() > 1; 
				
				if( notifyError ) {
					errorQueueDispatcher.dispatchMessage(workItem.getXmlContent());
				}
				
				TimeUnit.MILLISECONDS.sleep(ActorConstants.ERROR_DELAY_TIME);
				PostponedWorkItem postponedWorkItem = new PostponedWorkItem(workItem, ActorConstants.MASTER_DELAY_TIME);
				getSender().tell(postponedWorkItem, getSelf());

			} else {
				LOGGER.error("error processing: work-id -> {}  request {}, message disregarded: ", workItem.getId());
			}
		} catch (Exception e1) {
			LOGGER.error("error executing delaying for retry: " + e1.getMessage(), e1);
		}
	}

}
