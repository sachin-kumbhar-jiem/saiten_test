/**
 * 
 */
package test.com.saiten.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.orm.hibernate3.HibernateTemplate;

import test.com.saiten.dao.support.SaitenHibernateDAOSupportTest;

import com.saiten.dao.MstScorerDAO;
import com.saiten.dao.impl.MstScorerDAOImpl;
import com.saiten.info.MstScorerInfo;
import com.saiten.service.impl.LoginServiceImpl;

/**
 * @author kailash
 * 
 */
public class LoginServiceImplTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link com.saiten.service.impl.LoginServiceImpl#findByScorerIdAndPassword(java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public void testFindByScorerIdAndPassword() {
		// fail("Not yet implemented");
		String scorerId = "ES00010";
		String password = "6357";
		try {
			MstScorerInfo mstScorerInfo = loginServiceImpl
					.findByScorerIdAndPassword(scorerId, password);

			assertEquals('F', mstScorerInfo.getNoDbUpdate());
			assertEquals("採点者", mstScorerInfo.getRoleDescription());
			assertEquals(1, mstScorerInfo.getRoleId());
			assertEquals(scorerId, mstScorerInfo.getScorerId());
			assertEquals("ES00010", mstScorerInfo.getScorerName());

		} catch (Exception e) {
			e.printStackTrace();
			fail("test failed");
		}
	}

	final LoginServiceImpl loginServiceImpl = new LoginServiceImpl() {
		public MstScorerDAO getMstScorerDAO() {
			return new MstScorerDAOImpl() {
				public HibernateTemplate getHibernateTemplate() {
					return SaitenHibernateDAOSupportTest.getHibernateTemplate();
				}

				public HibernateTemplate getHibernateTemplate(
						String connectionString) {
					return SaitenHibernateDAOSupportTest
							.getHibernateTemplate(connectionString);
				}

			};
		}
	};
}
