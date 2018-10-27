package co.com.foundation.integration.business;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import akka.actor.ActorRef;
import co.com.foundation.integration.actor.MasterActor;
import co.com.foundation.integration.business.jobs.InitializerJob;
import co.com.foundation.integration.factory.specifications.ActorDefinition;
import co.com.foundation.integration.factory.specifications.ActorSpecification;


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

@Startup
@Singleton
public class JobSchedulerService {

	private static final Logger LOGGER = LogManager.getLogger(MasterActor.class);

	@Inject
	@ActorSpecification(value = ActorDefinition.WORKER_ACTOR)
	private ActorRef worker;
	private InitializerJob initializerJob;

	@PostConstruct
	private void init() {
		execute();
	}

	public void execute() {
		
		LOGGER.info("initializer job has been started");
		initializerJob = new InitializerJob(worker);
		initializerJob.start();
	}

}
