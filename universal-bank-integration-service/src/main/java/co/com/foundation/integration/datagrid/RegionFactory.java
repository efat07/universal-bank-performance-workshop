package co.com.foundation.integration.datagrid;

import static com.gemstone.gemfire.cache.client.ClientRegionShortcut.PROXY;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;

import co.com.foundation.integration.persistence.dao.FinanceDAO;
import co.com.foundation.integration.persistence.entity.ChannelConfiguration;

public class RegionFactory {

	private static final Logger LOGGER = LogManager.getLogger(RegionFactory.class);

	private static final String GEMFIRE_DATAGRID_ENDPOINT = "GEMFIRE_DATAGRID_ENDPOINT";

	private FinanceDAO financeDAO;

	private RegionFactory() {
		super();
	}

	public static RegionFactory newInstance() {
		return new RegionFactory();
	}

	public RegionFactory withFinanceDAO(final FinanceDAO financeDAO) {
		this.financeDAO = financeDAO;
		return this;
	}

	public Region<String, String> getRegion(final String region) {

		LOGGER.info("creating region for name {}", region);
		
		/**
		cacheFactory = new ClientCacheFactory();
		cacheFactory.set("cache-xml-file", "gemfire/gemfire-client.xml");
		client = cacheFactory.create();
		financeRegion = client.getRegion("finance");
		**/

		ChannelConfiguration configuration = financeDAO.getChannelConfigurationById(GEMFIRE_DATAGRID_ENDPOINT);

		ClientCacheFactory cacheFactory = new ClientCacheFactory();
		cacheFactory.addPoolLocator(configuration.getHostName(), configuration.getTcpPort());
		ClientCache client = cacheFactory.create();

		return client.<String, String>createClientRegionFactory(PROXY).create(region);
	}

}
