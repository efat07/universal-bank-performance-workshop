package co.com.foundation.integration.actor;

import java.util.Queue;
import java.util.concurrent.DelayQueue;

import javax.cache.Cache;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import akka.actor.AbstractActor;
import akka.japi.pf.FI.UnitApply;
import akka.japi.pf.ReceiveBuilder;
import co.com.foundation.integration.workmodel.PostponedWorkItem;
import co.com.foundation.integration.workmodel.RequestWorkItem;
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

public class MasterActor extends AbstractActor {

	private static final Logger LOGGER = LogManager.getLogger(MasterActor.class);

	//delay queue shouldn't be overloaded with payload!!!!! 
	private final static Queue<PostponedWorkItem> delayedQueue = new DelayQueue<PostponedWorkItem>();

	private Cache<String,WorkItem> worksCache;
	
	public MasterActor(Cache<String, WorkItem> worksCache) {
		super();
		this.worksCache = worksCache;
	}

	@Override
	public Receive createReceive() {
		return ReceiveBuilder.create()
				.match(PostponedWorkItem.class, addWorkToDelayQueue())
				.match(RequestWorkItem.class, assignWorkToWorker()).build();
	}

	private UnitApply<PostponedWorkItem> addWorkToDelayQueue() {
		return (postponedWorkItem) -> {
			
			try {
				
				WorkItem workItem = postponedWorkItem.delegateWorkItem();
				workItem.markAsAssignedToMaster();
				worksCache.put(postponedWorkItem.getId(),workItem);
				LOGGER.info("adding work to delayed queue: work-id -> {}", workItem.getId());
				
				delayedQueue.add(postponedWorkItem);

			} catch (Exception e) {
				LOGGER.error("error adding work to queue {}", e);
			}
		};
	}

	private UnitApply<RequestWorkItem> assignWorkToWorker() {
		return (request) -> {

			PostponedWorkItem postponedWorkItem = delayedQueue.poll();
			WorkItem workItem = null;

			try {

				LOGGER.info("request received in master-actor to retrieve a work ");

				if (postponedWorkItem != null) {
					
					workItem = worksCache.get(postponedWorkItem.getId());
					workItem.markAsAssignedToWorker();
					LOGGER.info("request received in master-actor to retrieve a work - work-id: {}", workItem.getId());
					getSender().tell(workItem, getSelf());
				} else {
					LOGGER.info("request received in master-actor to retrieve a work - no work found");
				}
			} catch (Exception e) {
				LOGGER.error("error assigning work to worker {}", e);
			}
		};
	}

}
