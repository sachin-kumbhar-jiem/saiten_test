package com.saiten.manager;

import java.util.LinkedHashMap;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.ServletContextAware;

import com.saiten.util.WebAppConst;

/**
 * @author sachin
 * @version 1.0
 * @created 11-Dec-2012 12:58:57 PM
 */
public class SaitenTransactionManager implements ServletContextAware {

	private ServletContext servletContext;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.context.ServletContextAware#setServletContext
	 * (javax.servlet.ServletContext)
	 */
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	/**
	 * @param connectionString
	 * @return PlatformTransactionManager
	 */
	@SuppressWarnings("unchecked")
	public PlatformTransactionManager getTransactionManger(
			String connectionString) {
		// Get transactionManger corresponding to the transaction database URL
		return ((LinkedHashMap<String, PlatformTransactionManager>) servletContext
				.getAttribute("platformTransactionManagerMap"))
				.get(connectionString);
	}

	public PlatformTransactionManager getTransactionManger() {
		// Get application.properties bean
		Properties saitenApplicationProperties = (Properties) ContextLoader
				.getCurrentWebApplicationContext().getBean(
						"saitenApplicationProperties");

		// Get transactionManger corresponding to the master database URL
		return getTransactionManger(saitenApplicationProperties
				.getProperty(WebAppConst.SAITEN_MASTERDB_URL));
	}

	/**
	 * @param platformTransactionManager
	 * @return TransactionStatus
	 */
	public TransactionStatus beginTransaction(
			PlatformTransactionManager platformTransactionManager) {
		TransactionDefinition def = new DefaultTransactionDefinition();

		// Begin and return transaction object
		return platformTransactionManager.getTransaction(def);
	}

}
