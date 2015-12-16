package test.com.saiten.dao.support;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.springframework.orm.hibernate3.HibernateTemplate;

import test.com.saiten.AllTests;

import com.saiten.util.WebAppConst;

/**
 * @author sachin
 * @version 1.0
 * @created 05-Dec-2012 4:59:11 PM
 */
public class SaitenHibernateDAOSupportTest {

	public static HibernateTemplate getHibernateTemplate(){
		Properties applicationProperties=new Properties();
		try {
			applicationProperties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties"));
		}catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return getHibernateTemplate(applicationProperties.getProperty(WebAppConst.SAITEN_MASTERDB_URL));
	}

	@SuppressWarnings("unchecked")
	public static HibernateTemplate getHibernateTemplate(String connectionString){
		
		ServletContext servletContext = AllTests.getServletContext();
	
		return ((LinkedHashMap<String, HibernateTemplate>) servletContext
				.getAttribute("hibernateTemplateMap")).get(connectionString);

	}
}
