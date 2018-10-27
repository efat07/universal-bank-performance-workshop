package co.com.foundation.integration.business;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import akka.actor.ActorRef;
import co.com.foundation.integration.factory.specifications.ActorDefinition;
import co.com.foundation.integration.factory.specifications.ActorSpecification;
import co.com.foundation.integration.factory.specifications.MQDefinition;
import co.com.foundation.integration.factory.specifications.MQSpecification;
import co.com.foundation.integration.util.MQDispatcher;
import co.com.foundation.integration.util.XMLUtils;
import co.com.foundation.integration.util.XPathConstants;
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

@Stateless
public class PreActionsExecutor {
	
	
	private static final Logger LOGGER = LogManager.getLogger(PreActionsExecutor.class);
	
	@Inject
	@ActorSpecification(value = ActorDefinition.WORK_PUBLISHER_ACTOR)
	private ActorRef workPublisher;
	
	@Inject
	@MQSpecification(MQDefinition.TRACKING_QUEUE)
	private MQDispatcher dispatcher;
	
	public void execute( final WorkItem workItem ){
		
		LOGGER.info("processing work {} before sending message to work-publisher-actor", workItem.getId());
		String officeCode = XMLUtils.evaluateExpression(XPathConstants.OFFICE_CODE, workItem.getXmlContent());
		String operationKey = XMLUtils.evaluateExpression(XPathConstants.ENTERPRISE_OPERATION_KEY, workItem.getXmlContent());
		LOGGER.info("analysing office code {} and operation key", officeCode, operationKey);

		if( officeCode != null && operationKey !=null ) {
			dispatcher.dispatchMessage(workItem.getXmlContent());
		}
		
		workPublisher.tell(workItem, ActorRef.noSender());
	}
	
}
