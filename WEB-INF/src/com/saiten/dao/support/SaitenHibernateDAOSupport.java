package com.saiten.dao.support;

import java.util.LinkedHashMap;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.ServletContextAware;

import com.saiten.util.WebAppConst;

/**
 * @author sachin
 * @version 1.0
 * @created 05-Dec-2012 4:59:11 PM
 */
public abstract class SaitenHibernateDAOSupport implements ServletContextAware {

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
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public HibernateTemplate getHibernateTemplate(String connectionString) {
		// Get HibernateTemplate corresponding to the transaction database URL
		return ((LinkedHashMap<String, HibernateTemplate>) servletContext
				.getAttribute("hibernateTemplateMap")).get(connectionString);
	}

	public HibernateTemplate getHibernateTemplate() {
		// Get application.properties bean
		Properties saitenApplicationProperties = (Properties) ContextLoader
				.getCurrentWebApplicationContext().getBean(
						"saitenApplicationProperties");

		// Get HibernateTemplate object corresponding to the master database URL
		return getHibernateTemplate(saitenApplicationProperties
				.getProperty(WebAppConst.SAITEN_MASTERDB_URL));
	}
	
	
	@SuppressWarnings("unchecked")
	public HibernateTemplate getHibernateTemplateReplica (String connectionString) {
		// Get HibernateTemplate corresponding to the transaction database URL
		return ((LinkedHashMap<String, HibernateTemplate>) servletContext
				.getAttribute("hibernateTemplateReplicaMap")).get(connectionString);
	}

}
