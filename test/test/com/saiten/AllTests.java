package test.com.saiten;

import javax.servlet.ServletContext;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.mock.web.MockServletContext;

import test.com.saiten.listener.SaitenInitializeListenerTest;
import test.com.saiten.service.impl.LoginServiceImplTest;
import test.com.saiten.service.impl.QuestionSelectionServiceImplTest;
import test.com.saiten.service.impl.ScoreSearchServiceImplTest;
import test.com.saiten.service.impl.ScoreServiceImplTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ LoginServiceImplTest.class,ScoreSearchServiceImplTest.class,QuestionSelectionServiceImplTest.class,ScoreServiceImplTest.class })
public class AllTests {

	public static ServletContext servletContext;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		servletContext = new MockServletContext();
		saitenInitializeListenerTest.buildDatabaseManagerMaps();
		saitenInitializeListenerTest.buildSaitenConfigObject();
	}

	@Before
	public void setUp() throws Exception {
	}

	public static ServletContext getServletContext() {
		return servletContext;
	}

	final static SaitenInitializeListenerTest saitenInitializeListenerTest = new SaitenInitializeListenerTest();

}
