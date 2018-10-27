package co.com.foundation.integration.business.handler.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.com.foundation.integration.business.handler.Handler;
import co.com.foundation.integration.datagrid.DataGridDispatcher;
import co.com.foundation.integration.factory.specifications.MQDefinition;
import co.com.foundation.integration.factory.specifications.MQSpecification;
import co.com.foundation.integration.marshaller.impl.JAXBMarshaller;
import co.com.foundation.integration.util.MQDispatcher;
import universal_bank.crm.enterprise_model.framework._1_0.CRMBusinessEvent;

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

@Stateless(name = "dispatcherHandler")
public class DispatcherHandler implements Handler<CRMBusinessEvent> {

	private static final Logger LOGGER = LogManager.getLogger(DispatcherHandler.class);

	@Inject
	@MQSpecification(MQDefinition.OPERATIONS_QUEUE)
	private MQDispatcher mqDispatcher;
	
	@EJB
	private DataGridDispatcher datagridDispatcher;
	
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public boolean process(final CRMBusinessEvent input) {

		LOGGER.info("start dispatcher handler");
		
		input.getEnterpriseOperationEventObject().getFinancialActionDomainObject().stream().forEach((action) -> {

			String xml = JAXBMarshaller.newInstance().marshall( action );
			datagridDispatcher.publishOnFinanceRegion( action.getExternalKey() , xml);
			mqDispatcher.dispatchMessage(xml);
		});
		
		LOGGER.info("end dispatcher handler");
		return true;
	}
	
}
