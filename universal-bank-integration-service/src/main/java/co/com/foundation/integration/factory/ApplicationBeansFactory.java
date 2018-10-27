package co.com.foundation.integration.factory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.AccessedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.cache.spi.CachingProvider;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;

import com.gemstone.gemfire.cache.Region;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.FromConfig;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import co.com.foundation.integration.actor.MasterActor;
import co.com.foundation.integration.actor.WorkPublisherActor;
import co.com.foundation.integration.actor.WorkerActor;
import co.com.foundation.integration.business.handler.HandlerCoordinator;
import co.com.foundation.integration.datagrid.RegionFactory;
import co.com.foundation.integration.factory.specifications.ActorDefinition;
import co.com.foundation.integration.factory.specifications.ActorSpecification;
import co.com.foundation.integration.factory.specifications.MQDefinition;
import co.com.foundation.integration.factory.specifications.MQSpecification;
import co.com.foundation.integration.factory.specifications.RegionDefinition;
import co.com.foundation.integration.factory.specifications.RegionSpecification;
import co.com.foundation.integration.persistence.dao.FinanceDAO;
import co.com.foundation.integration.util.MQDispatcher;
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

@ApplicationScoped
public class ApplicationBeansFactory {

	@EJB
	private FinanceDAO financeDAO;
	
	@EJB
	private HandlerCoordinator handlerCoordinator;
	
	@Resource(lookup = "java:/financial-operations-cf")
	private ConnectionFactory connectionFactory;

	@Resource(lookup = "java:/jms/queue/financial-errors")
	private Queue errorQueue;

	@Resource(lookup = "java:/jms/queue/financial-operations")
	private Queue operationsQueue;
	
	@Resource(lookup = "java:/jms/queue/financial-tracking")
	private Queue trackingQueue;
	
	private MQDispatcher trackingQueueDispatcher;
	private MQDispatcher operationsQueueDispatcher;
	private MQDispatcher errorQueueDispatcher;
	
	private Region<String,String> financeRegion; 
	
	private CachingProvider cachingProvider = Caching.getCachingProvider();
	private CacheManager cacheManager = cachingProvider.getCacheManager();
	private Cache<String,WorkItem> worksCache;
	
	private ActorSystem system;
	private Materializer materializer;

	private ActorRef masterActor;
	private ActorRef workerActor;
	private ActorRef publisherActor;

	// --------------------------

	public ApplicationBeansFactory() {
		super();
	}

	private void prepareJMSArtifacts() {
		operationsQueueDispatcher = new MQDispatcher(connectionFactory, operationsQueue);
		errorQueueDispatcher = new MQDispatcher(connectionFactory, errorQueue);
		trackingQueueDispatcher = new MQDispatcher(connectionFactory, trackingQueue);
	}
	
	private void prepareDataGridArtifacts() {
		financeRegion = RegionFactory.newInstance().withFinanceDAO(financeDAO).getRegion("finance");
	}
	
	private void prepareJCacheArtifacts() {
		
		MutableConfiguration<String, WorkItem> worksCacheConfig = new MutableConfiguration<>();
		worksCacheConfig.setTypes(String.class, WorkItem.class);
		worksCacheConfig.setExpiryPolicyFactory(AccessedExpiryPolicy.factoryOf(Duration.ETERNAL));
		
		worksCache = cacheManager.createCache("works-cache", worksCacheConfig);
	}

	@PostConstruct
	public void init() {
		
		prepareDataGridArtifacts();
		prepareJCacheArtifacts();
		prepareJMSArtifacts();
		
		system = ActorSystem.create("transaction-akka-system", ConfigFactory.load("akka-application.conf"));
		materializer = ActorMaterializer.create(system);
		masterActor = system.actorOf(FromConfig.getInstance().props(Props.create(MasterActor.class, worksCache)),ActorDefinition.MASTER_ACTOR.name());
		workerActor = system.actorOf(FromConfig.getInstance().props(Props.create(WorkerActor.class, masterActor, errorQueueDispatcher, handlerCoordinator)),ActorDefinition.WORKER_ACTOR.name());
		publisherActor = system.actorOf(FromConfig.getInstance().props(Props.create(WorkPublisherActor.class, masterActor)),ActorDefinition.WORK_PUBLISHER_ACTOR.name());
	}
	
	@Produces
	@RegionSpecification(value = RegionDefinition.FINANCE)
	public Region<String,String> createFinanceRegion() {
		return financeRegion;
	}
	
	@Produces
	public ActorSystem createActorSystem() {
		return system;
	}

	@Produces
	public Materializer createMaterializer() {
		return materializer;
	}

	@Produces
	@ActorSpecification(value = ActorDefinition.WORKER_ACTOR)
	public ActorRef createWorkerActor() {
		return workerActor;
	}

	@Produces
	@ActorSpecification(value = ActorDefinition.MASTER_ACTOR)
	public ActorRef createMasterActor() {
		return masterActor;
	}

	@Produces
	@ActorSpecification(value = ActorDefinition.WORK_PUBLISHER_ACTOR)
	public ActorRef createWorkPublisherActor() {
		return publisherActor;
	}
	
	@Produces
	@MQSpecification(MQDefinition.OPERATIONS_QUEUE)
	public MQDispatcher createOperationsMQDispatcher() {
		return operationsQueueDispatcher;
	}
	
	@Produces
	@MQSpecification(MQDefinition.TRACKING_QUEUE)
	public MQDispatcher createTrackingMQDispatcher() {
		return trackingQueueDispatcher;
	}
	
	@Produces
	@MQSpecification(MQDefinition.ERROR_QUEUE)
	public MQDispatcher createErrorMQDispatcher() {
		return errorQueueDispatcher;
	}

}
