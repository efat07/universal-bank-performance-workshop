package co.com.foundation.integration.datagrid;

import java.util.UUID;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gemstone.gemfire.cache.Region;

import co.com.foundation.integration.factory.specifications.RegionDefinition;
import co.com.foundation.integration.factory.specifications.RegionSpecification;
import co.com.foundation.integration.persistence.dao.FinanceDAO;
import co.com.foundation.integration.persistence.entity.ChannelConfiguration;

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
public class DataGridDispatcher {

	private static final Logger LOGGER = LogManager.getLogger(DataGridDispatcher.class);
	
	private static final String GEMFIRE_DATAGRID_ENDPOINT = "GEMFIRE_DATAGRID_ENDPOINT";
	
	@EJB
	private FinanceDAO financeDAO;
	
	@Inject
	@RegionSpecification(RegionDefinition.FINANCE)
	private Region<String,String> financeRegion; 
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public synchronized void publishOnFinanceRegion( final String id, final String message ) {
		
		ChannelConfiguration configuration = financeDAO.getChannelConfigurationById(GEMFIRE_DATAGRID_ENDPOINT);
		LOGGER.info("sending message to finance datagrid to {}:{}", configuration.getHostName(),configuration.getTcpPort());
		financeRegion.put(id.concat(UUID.randomUUID().toString()), message);
	}
	
}
