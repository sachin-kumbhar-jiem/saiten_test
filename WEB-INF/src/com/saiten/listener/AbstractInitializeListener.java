package com.saiten.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author sachin
 * @version 1.0
 * @created 05-Dec-2012 12:40:12 PM
 */
public abstract class AbstractInitializeListener implements
		ServletContextListener {

	private ServletContext context;

	/**
	 * 
	 * @param event
	 */
	public void contextDestroyed(ServletContextEvent event) {
		context = event.getServletContext();
	}

	/**
	 * 
	 * @param event
	 */
	public void contextInitialized(ServletContextEvent event) {
		context = event.getServletContext();
	}

	public ServletContext getContext() {
		return this.context;
	}

	/**
	 * 
	 * @param context
	 */
	public void setContext(ServletContext context) {
		this.context = context;
	}
}
