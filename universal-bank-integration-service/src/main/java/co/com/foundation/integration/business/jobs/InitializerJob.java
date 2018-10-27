package co.com.foundation.integration.business.jobs;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import akka.actor.ActorRef;
import co.com.foundation.integration.workmodel.RequestWorkItem;

public class InitializerJob extends Thread {
	
	private static final Logger LOGGER = LogManager.getLogger(InitializerJob.class);

	private ActorRef worker;

	public InitializerJob(final ActorRef worker) {
		super();
		this.worker = worker;
	}

	@Override
	public void run() {

		while(true) {
			try {
				TimeUnit.SECONDS.sleep(30L);
				askForJobs();
			} catch (InterruptedException e) {
				LOGGER.error("error executing initializer job", e);
			}
		}
		
	}
	
	private void askForJobs() {
		
		LOGGER.info("start timer service for asking for works " );
		LOGGER.info("current Time : " + new Date());
		LOGGER.info("____________________________________________");
		
		RequestWorkItem requestWorkItem = new RequestWorkItem();
		worker.tell(requestWorkItem, ActorRef.noSender());
		LOGGER.info("work requested to master-actor");
	}

}
