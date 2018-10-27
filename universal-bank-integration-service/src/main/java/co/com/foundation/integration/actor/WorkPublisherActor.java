package co.com.foundation.integration.actor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.japi.pf.FI.UnitApply;
import akka.japi.pf.ReceiveBuilder;
import co.com.foundation.integration.workmodel.PostponedWorkItem;
import co.com.foundation.integration.workmodel.WorkItem;

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

public class WorkPublisherActor extends AbstractActor {

	private static final Logger LOGGER = LogManager.getLogger(WorkPublisherActor.class);

	private ActorRef masterActor;
	
	public WorkPublisherActor(final ActorRef masterActor) {
		super();
		this.masterActor = masterActor;
	}

	@Override
	public Receive createReceive() {
		return ReceiveBuilder.create()
				.match(WorkItem.class, work -> work.getStatus().equals(WorkItem.WORK_STATE.CREATED), publish())
				.build();
	}

	private UnitApply<WorkItem> publish() {
		return (work) -> {

			LOGGER.info("processing work: work-id -> {}", work.getId());

			try {
				work.trackAttempt();
				PostponedWorkItem postponedWorkItem = new PostponedWorkItem(work, ActorConstants.MASTER_DELAY_TIME);
				masterActor.tell(postponedWorkItem, getSelf());
			} catch (Exception e) {
				LOGGER.error("error publishing message to master actor {}", work.getId());
			}
		};
	}


}
