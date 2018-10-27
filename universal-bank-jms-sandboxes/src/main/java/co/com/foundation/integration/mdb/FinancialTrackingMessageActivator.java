package co.com.foundation.integration.mdb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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


@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destination",propertyValue = "java:/jms/queue/financial-tracking")
})
public class FinancialTrackingMessageActivator implements MessageListener{

	private static final Logger LOGGER = LogManager.getLogger(FinancialTrackingMessageActivator.class);
	
	@Override
	public void onMessage(final Message message) {
		try {
			LOGGER.info( "receiving financial tracking" );
			LOGGER.info( ((TextMessage) message).getText() );
		} catch (JMSException e) {
			LOGGER.error("error processing message", e);
		}
	}

}