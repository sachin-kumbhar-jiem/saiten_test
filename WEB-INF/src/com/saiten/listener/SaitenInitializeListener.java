package com.saiten.listener;

import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.context.ContextLoader;

import com.saiten.bean.SaitenConfig;
import com.saiten.exception.SaitenRuntimeException;
import com.saiten.logic.CreateSaitenConfigLogic;
import com.saiten.util.ErrorCode;
import com.saiten.util.WebAppConst;

/**
 * @author sachin
 * @version 1.0
 * @created 05-Dec-2012 12:40:12 PM
 */
public class SaitenInitializeListener extends AbstractInitializeListener {

	private static Logger log = Logger
			.getLogger(SaitenInitializeListener.class);

	/**
	 * 
	 * @param event
	 */
	@Override
	public void contextInitialized(ServletContextEvent event) {
		log.info("============= Initializing ServletContext Object =============");
		super.contextInitialized(event);

		try {

			// Build ConfigMap.
			buildConfigMap();

			// Build Transaction and HibernateTemplate manager maps
			buildDatabaseManagerMaps();

			// Build saitenConfigObject containing master data
			buildSaitenConfigObject();
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.SAITEN_INITIALIZE_LISTENER_EXCEPTION, e);
		}
	}

	private void buildConfigMap() {

		Map<String, String> configMap = new LinkedHashMap<String, String>();
		// Get current WebApp Context
		ApplicationContext ctx = ContextLoader
				.getCurrentWebApplicationContext();

		// Read application.properties file.
		Properties applicationProperties = (Properties) ctx
				.getBean("saitenApplicationProperties");
		Properties configProperties = null;
		if (Boolean.valueOf((String) applicationProperties
				.get(WebAppConst.SAITEN_RELEASE))) {
			configProperties = (Properties) ctx
					.getBean("saitenConfigProperties");
		} else if (Boolean.valueOf((String) applicationProperties
				.get(WebAppConst.SHINEIGO_RELEASE))) {
			configProperties = (Properties) ctx
					.getBean("shinEigoConfigProperties");
		}

		// Get all keys from config.properties file.
		Enumeration<Object> keys = configProperties.keys();
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			String value = (String) configProperties.get(key);
			System.out.println(key + ": " + value);
			configMap.put(key, value);
		}

		// Get ServletContext
		ServletContext context = getContext();

		// Put data in ServletContext
		context.setAttribute("configMap", configMap);
	}

	private void buildDatabaseManagerMaps() {

		// Get current WebApp Context
		ApplicationContext ctx = ContextLoader
				.getCurrentWebApplicationContext();

		// Read database.properties file
		Properties saitenDbProperties = (Properties) ctx
				.getBean("saitenDbProperties");

		// HibernateTemplate & PlatformTransactionManager map's
		LinkedHashMap<String, HibernateTemplate> hibernateTemplateMap = new LinkedHashMap<String, HibernateTemplate>();
		LinkedHashMap<String, PlatformTransactionManager> platformTransactionManagerMap = new LinkedHashMap<String, PlatformTransactionManager>();

		String key = null;
		String hibernateTemplateBeanId = null;
		String transactionManagerBeanId = null;

		for (int i = 0; i < saitenDbProperties.size() / 4; i++) {

			if (i == 0) {
				// Master DB
				key = "saiten.masterdb.url";
				hibernateTemplateBeanId = "saitenMasterDbSessionFactory";
				transactionManagerBeanId = "saitenMasterDbTransactionManager";
			} else if (i == 1) {
				// Common DB
				key = "saiten.commondb.url";
				hibernateTemplateBeanId = "saitenCommonDbSessionFactory";
				transactionManagerBeanId = "saitenCommonDbTransactionManager";
			} else {
				// Transaction DB
				int j = i - 1;
				key = "saiten.transactiondb" + j + ".url";
				hibernateTemplateBeanId = "saitenTranDb" + j + "SessionFactory";
				transactionManagerBeanId = "saitenTranDb" + j
						+ "TransactionManager";
			}

			HibernateTemplate hibernateTemplate = new HibernateTemplate(
					(SessionFactory) ctx.getBean(hibernateTemplateBeanId));

			PlatformTransactionManager platformTransactionManager = (PlatformTransactionManager) ctx
					.getBean(transactionManagerBeanId);

			// Map<ConnectionString, HibernateTemplate>()
			hibernateTemplateMap.put(saitenDbProperties.getProperty(key),
					hibernateTemplate);
			// Map<ConnectionString, PlatformTransactionManager>()
			platformTransactionManagerMap.put(
					saitenDbProperties.getProperty(key),
					platformTransactionManager);
		}

		// Get ServletContext
		ServletContext context = getContext();

		// Put data in ServletContext
		context.setAttribute("hibernateTemplateMap", hibernateTemplateMap);
		context.setAttribute("platformTransactionManagerMap",
				platformTransactionManagerMap);
	}

	private void buildSaitenConfigObject() {
		ApplicationContext ctx = ContextLoader
				.getCurrentWebApplicationContext();

		CreateSaitenConfigLogic createSaitenConfigLogic = (CreateSaitenConfigLogic) ctx
				.getBean("createSaitenConfigLogic");
		SaitenConfig saitenConfigObject = createSaitenConfigLogic
				.buildSaitenConfigObject();

		getContext().setAttribute("saitenConfigObject", saitenConfigObject);
	}

	/**
	 * 
	 * @param event
	 */
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		log.info("============= contextDestroyed =============");
	}
}
