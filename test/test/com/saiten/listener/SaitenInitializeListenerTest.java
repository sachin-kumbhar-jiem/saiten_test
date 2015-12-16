package test.com.saiten.listener;

import java.util.LinkedHashMap;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import test.com.saiten.AllTests;
import test.com.saiten.dao.support.SaitenHibernateDAOSupportTest;

import com.saiten.bean.SaitenConfig;
import com.saiten.dao.MstGradeDAO;
import com.saiten.dao.MstPendingCategoryDAO;
import com.saiten.dao.MstQuestionDAO;
import com.saiten.dao.MstScoringStateListDAO;
import com.saiten.dao.impl.MstGradeDAOImpl;
import com.saiten.dao.impl.MstPendingCategoryDAOImpl;
import com.saiten.dao.impl.MstQuestionDAOImpl;
import com.saiten.dao.impl.MstScoringStateListDAOImpl;
import com.saiten.logic.CreateSaitenConfigLogic;
import com.saiten.manager.SaitenTransactionManager;
import com.saiten.manager.SaitenTransactionManagerTest;

public class SaitenInitializeListenerTest {

	public void buildDatabaseManagerMaps() {

		ServletContext servletContext = AllTests.getServletContext();

		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "/test/config/spring/SpringBeansTest.xml" });

		servletContext.setAttribute("applicationContext", context);

		Properties saitenDbProperties = (Properties) context
				.getBean("saitenDbProperties");

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
			} else {
				// Transaction DB
				key = "saiten.transactiondb" + i + ".url";
				hibernateTemplateBeanId = "saitenTranDb" + i + "SessionFactory";
				transactionManagerBeanId = "saitenTranDb" + i
						+ "TransactionManager";
			}

			HibernateTemplate hibernateTemplate = new HibernateTemplate(
					(SessionFactory) context.getBean(hibernateTemplateBeanId));

			PlatformTransactionManager platformTransactionManager = (PlatformTransactionManager) context
					.getBean(transactionManagerBeanId);

			// Map<ConnectionString, HibernateTemplate>()
			hibernateTemplateMap.put(saitenDbProperties.getProperty(key),
					hibernateTemplate);
			// Map<ConnectionString, PlatformTransactionManager>()
			platformTransactionManagerMap.put(
					saitenDbProperties.getProperty(key),
					platformTransactionManager);
		}

		servletContext.setAttribute("hibernateTemplateMap",
				hibernateTemplateMap);
		servletContext.setAttribute("platformTransactionManagerMap",
				platformTransactionManagerMap);
	}

	public void buildSaitenConfigObject() {

		ServletContext servletContext = AllTests.getServletContext();

		CreateSaitenConfigLogic createSaitenConfigLogic = getCreateSaitenConfigLogic();
		SaitenConfig saitenConfigObject = createSaitenConfigLogic
				.buildSaitenConfigObject();

		servletContext.setAttribute("saitenConfigObject", saitenConfigObject);
	}

	public CreateSaitenConfigLogic getCreateSaitenConfigLogic() {
		return new CreateSaitenConfigLogic() {
			public SaitenConfig getSaitenConfig() {
				return new SaitenConfig();
			}

			public SaitenTransactionManager getSaitenTransactionManager() {
				return new SaitenTransactionManagerTest();
			}

			public MstGradeDAO getMstGradeDAO() {
				return new MstGradeDAOImpl() {
					public HibernateTemplate getHibernateTemplate() {
						return SaitenHibernateDAOSupportTest
								.getHibernateTemplate();
					}

					public HibernateTemplate getHibernateTemplate(
							String connectionString) {
						return SaitenHibernateDAOSupportTest
								.getHibernateTemplate(connectionString);
					}
				};
			}

			@SuppressWarnings("unused")
			public MstQuestionDAO getMstQuestionDAO() {
				return new MstQuestionDAOImpl() {
					public HibernateTemplate getHibernateTemplate() {
						return SaitenHibernateDAOSupportTest
								.getHibernateTemplate();
					}

					public HibernateTemplate getHibernateTemplate(
							String connectionString) {
						return SaitenHibernateDAOSupportTest
								.getHibernateTemplate(connectionString);
					}
				};
			}

			public MstScoringStateListDAO getMstScoringStateListDAO() {
				return new MstScoringStateListDAOImpl() {
					public HibernateTemplate getHibernateTemplate() {
						return SaitenHibernateDAOSupportTest
								.getHibernateTemplate();

					}

					public HibernateTemplate getHibernateTemplate(
							String connectionString) {
						return SaitenHibernateDAOSupportTest
								.getHibernateTemplate(connectionString);
					}
				};
			}

			public MstPendingCategoryDAO getMstPendingCategoryDAO() {
				return new MstPendingCategoryDAOImpl() {
					public HibernateTemplate getHibernateTemplate() {
						return SaitenHibernateDAOSupportTest
								.getHibernateTemplate();
					}

					public HibernateTemplate getHibernateTemplate(
							String connectionString) {
						return SaitenHibernateDAOSupportTest
								.getHibernateTemplate(connectionString);
					}
				};
			}

			public Properties getSaitenGlobalProperties() {
				ServletContext servletContext = AllTests.getServletContext();
				return (Properties) ((ApplicationContext) servletContext
						.getAttribute("applicationContext"))
						.getBean("saitenGlobalProperties");
			}
		};
	}
}
