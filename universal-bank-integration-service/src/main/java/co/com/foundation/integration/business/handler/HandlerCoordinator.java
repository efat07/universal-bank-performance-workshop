package co.com.foundation.integration.business.handler;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

@Stateless
public class HandlerCoordinator {

	private static final Logger LOGGER = LogManager.getLogger(HandlerCoordinator.class);

	@EJB(beanName = "filterHandler")
	private Handler<CRMBusinessEvent> filterHandler;

	@EJB(beanName = "persistenceHandler")
	private Handler<CRMBusinessEvent> persistenceHandler;

	@EJB(beanName = "dispatcherHandler")
	private Handler<CRMBusinessEvent> dispatcherHandler;

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void startPipeline(final CRMBusinessEvent businessEvent) {

		LOGGER.info("start handler coordinator");

		if (filterHandler.process(businessEvent)) {
			persistenceHandler.process(businessEvent);
			dispatcherHandler.process(businessEvent);
		}else {
			LOGGER.info("message has been disregarded by filter");
		}

		LOGGER.info("end handler coordinator");
	}

}
