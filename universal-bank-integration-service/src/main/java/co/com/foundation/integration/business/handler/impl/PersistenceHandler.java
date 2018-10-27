package co.com.foundation.integration.business.handler.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.com.foundation.integration.business.handler.Handler;
import co.com.foundation.integration.marshaller.impl.JAXBMarshaller;
import co.com.foundation.integration.persistence.dao.FinanceDAO;
import co.com.foundation.integration.persistence.entity.FinanceMessage;
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

@Stateless(name = "persistenceHandler")
public class PersistenceHandler implements Handler<CRMBusinessEvent> {

	private static final Logger LOGGER = LogManager.getLogger(PersistenceHandler.class);

	@EJB
	private FinanceDAO financeDAO;

	@Override
	public boolean process(final CRMBusinessEvent input) {

		LOGGER.info("start persistence handler");

		input.getEnterpriseOperationEventObject().getFinancialActionDomainObject().stream().forEach((action) -> {

			String xml = JAXBMarshaller.newInstance().marshall( action );
			
			FinanceMessage message = new FinanceMessage();
			message.setXmlMessage(xml);
			message.setExternalKey( action.getExternalKey() );
			message.setContainsProduct( action.getFinancialProduct() != null );
			message.setContainsService( action.getFinancialService() != null );
			
			financeDAO.createFinanceMessage(message);
			
		});

		LOGGER.info("end persistence handler");
		return true;
	}

}
