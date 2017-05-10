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

	private static Logger log = Logger.getLogger(SaitenInitializeListener.class);

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
			throw new SaitenRuntimeException(ErrorCode.SAITEN_INITIALIZE_LISTENER_EXCEPTION, e);
		}
	}

	private void buildConfigMap() {

		Map<String, String> configMap = new LinkedHashMap<String, String>();
		// Get current WebApp Context
		ApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();

		// Read application.properties file.
		Properties applicationProperties = (Properties) ctx.getBean("saitenApplicationProperties");
		Properties configProperties = null;
		if (Boolean.valueOf((String) applicationProperties.get(WebAppConst.SAITEN_RELEASE))) {
			configProperties = (Properties) ctx.getBean("saitenConfigProperties");
		} else if (Boolean.valueOf((String) applicationProperties.get(WebAppConst.SHINEIGO_RELEASE))) {
			configProperties = (Properties) ctx.getBean("shinEigoConfigProperties");
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
		ApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();

		// Read database.properties file
		Properties saitenDbProperties = (Properties) ctx.getBean("saitenDbProperties");

		// HibernateTemplate & PlatformTransactionManager map's
		LinkedHashMap<String, HibernateTemplate> hibernateTemplateMap = new LinkedHashMap<String, HibernateTemplate>();
		LinkedHashMap<String, PlatformTransactionManager> platformTransactionManagerMap = new LinkedHashMap<String, PlatformTransactionManager>();

		LinkedHashMap<String, HibernateTemplate> hibernateTemplateReplicaMap = new LinkedHashMap<String, HibernateTemplate>();
		LinkedHashMap<String, PlatformTransactionManager> platformTransactionManagerReplicaMap = new LinkedHashMap<String, PlatformTransactionManager>();

		String key = null;
		String hibernateTemplateBeanId = null;
		String transactionManagerBeanId = null;

		// String keyReplica = null;

		String hibernateTemplateBeanIdReplica = null;
		String transactionManagerBeanIdReplica = null;

		/*
		 * Properties applicationProperties = (Properties) ctx
		 * .getBean("saitenApplicationProperties"); int databaseCount = 0;
		 * 
		 * if (Boolean.valueOf((String) applicationProperties
		 * .get(WebAppConst.REPLICATION_ON))) { databaseCount =
		 * saitenDbProperties.size() / 8; } else { databaseCount =
		 * (saitenDbProperties.size() / 4) - 1; }
		 * 
		 * for (int i = 0; i <= databaseCount; i++) {
		 * 
		 * if (i == 0) { // Master DB key = "saiten.masterdb.url";
		 * hibernateTemplateBeanId = "saitenMasterDbSessionFactory";
		 * transactionManagerBeanId = "saitenMasterDbTransactionManager"; } else
		 * if (i == 1) { // Common DB key = "saiten.commondb.url";
		 * hibernateTemplateBeanId = "saitenCommonDbSessionFactory";
		 * transactionManagerBeanId = "saitenCommonDbTransactionManager"; } else
		 * { // Transaction DB int j = i - 1; key = "saiten.transactiondb" + j +
		 * ".url"; hibernateTemplateBeanId = "saitenTranDb" + j +
		 * "SessionFactory"; transactionManagerBeanId = "saitenTranDb" + j +
		 * "TransactionManager";
		 * 
		 * if (Boolean.valueOf((String) applicationProperties
		 * .get(WebAppConst.REPLICATION_ON))) { keyReplica =
		 * "saiten.replica.transactiondb" + j + ".url";
		 * hibernateTemplateBeanIdReplica = "saitenReplicaTranDb" + j +
		 * "SessionFactory"; transactionManagerBeanIdReplica =
		 * "saitenReplicaTranDb" + j + "TransactionManager"; } }
		 */

		// Comment by Suwarna, code added for db properties keyset handling
		///////////// Start///////////////

		// for db replica,check flag in application property file

		boolean isReplicaOn, keyReplica = false;

		Properties applicationProperties = (Properties) ctx.getBean("saitenApplicationProperties");

		isReplicaOn = Boolean.valueOf((String) applicationProperties.get(WebAppConst.REPLICATION_ON));

		for (Object obj : saitenDbProperties.keySet()) {
			if (obj != null && obj.toString().contains("url")) {
				key = obj.toString();
				if (key.contains("masterdb")) {
					hibernateTemplateBeanId = "saitenMasterDbSessionFactory";
					transactionManagerBeanId = "saitenMasterDbTransactionManager";
				} else if (key.contains("commondb")) {
					hibernateTemplateBeanId = "saitenCommonDbSessionFactory";
					transactionManagerBeanId = "saitenCommonDbTransactionManager";
				} else if (key.contains("tempdb")) {
					String tempdbNum = key.replaceAll("[^0-9]", "");
					hibernateTemplateBeanId = "saitenTempDb" + tempdbNum + "SessionFactory";
					transactionManagerBeanId = "saitenTempDb" + tempdbNum + "TransactionManager";
				} else if (isReplicaOn && key.contains("replica")) {
					String tempdbNum = key.replaceAll("[^0-9]", "");
					hibernateTemplateBeanIdReplica = "saitenReplicaTranDb" + tempdbNum + "SessionFactory";
					transactionManagerBeanIdReplica = "saitenReplicaTranDb" + tempdbNum + "TransactionManager";
					keyReplica = true;
				} else if (key.contains("tran") && (!key.contains("replica"))) {
					String tranNum = key.replaceAll("[^0-9]", "");
					hibernateTemplateBeanId = "saitenTranDb" + tranNum + "SessionFactory";
					transactionManagerBeanId = "saitenTranDb" + tranNum + "TransactionManager";
				}

				/////////////////// End//////////////
				if (!keyReplica) {
					HibernateTemplate hibernateTemplate = new HibernateTemplate(
							(SessionFactory) ctx.getBean(hibernateTemplateBeanId));

					PlatformTransactionManager platformTransactionManager = (PlatformTransactionManager) ctx
							.getBean(transactionManagerBeanId);

					// Map<ConnectionString, HibernateTemplate>()

					hibernateTemplateMap.put(saitenDbProperties.getProperty(key), hibernateTemplate);
					// Map<ConnectionString, PlatformTransactionManager>()
					platformTransactionManagerMap.put(saitenDbProperties.getProperty(key), platformTransactionManager);
				} else if (keyReplica) {
					String keyObj = key.replace("replica.", "");
					HibernateTemplate hibernateTemplateReplica = new HibernateTemplate(
							(SessionFactory) ctx.getBean(hibernateTemplateBeanIdReplica));

					PlatformTransactionManager platformTransactionManagerReplica = (PlatformTransactionManager) ctx
							.getBean(transactionManagerBeanIdReplica);

					hibernateTemplateReplicaMap.put(saitenDbProperties.getProperty(keyObj), hibernateTemplateReplica);

					platformTransactionManagerReplicaMap.put(saitenDbProperties.getProperty(keyObj),
							platformTransactionManagerReplica);
					keyReplica = false;
				}

			} // if ends
		} // for ends

		// Get ServletContext
		ServletContext context = getContext();

		// Put data in ServletContext
		context.setAttribute("hibernateTemplateMap", hibernateTemplateMap);
		context.setAttribute("platformTransactionManagerMap", platformTransactionManagerMap);
		context.setAttribute("hibernateTemplateReplicaMap", hibernateTemplateReplicaMap);
		context.setAttribute("platformTransactionManagerReplicaMap", platformTransactionManagerReplicaMap);

	}

	private void buildSaitenConfigObject() {
		ApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();

		CreateSaitenConfigLogic createSaitenConfigLogic = (CreateSaitenConfigLogic) ctx
				.getBean("createSaitenConfigLogic");
		SaitenConfig saitenConfigObject = createSaitenConfigLogic.buildSaitenConfigObject();

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
