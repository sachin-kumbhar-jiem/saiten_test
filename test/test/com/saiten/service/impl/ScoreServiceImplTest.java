/**
 * 
 */
package test.com.saiten.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.saiten.info.GradeInfo;
import com.saiten.service.impl.ScoreServiceImpl;

/**
 * @author kailash
 * 
 */
public class ScoreServiceImplTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
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
	 * {@link com.saiten.service.impl.ScoreServiceImpl#findAnswer(int, java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.String)}
	 * .
	 */
	@Test
	public void testFindAnswer() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.saiten.service.impl.ScoreServiceImpl#buildGradeInfo(java.lang.Integer, int)}
	 * .
	 */
	@Test
	public void testBuildGradeInfo_1() {
		Integer gradeSeq = 900004;
		int questionSeq = 9;

		GradeInfo gradeInfo = scoreServiceImpl.buildGradeInfo(gradeSeq,
				questionSeq);

		assertNotNull(gradeInfo);
	}

	/**
	 * Test method for
	 * {@link com.saiten.service.impl.ScoreServiceImpl#buildGradeInfo(java.lang.Integer, int)}
	 * .
	 */
	@Test
	public void testBuildGradeInfo_2() {
		Integer gradeSeq = 900004;
		int questionSeq = 9;

		GradeInfo gradeInfo = scoreServiceImpl.buildGradeInfo(gradeSeq,
				questionSeq);

		assertEquals("4", gradeInfo.getGradeNum());
		assertEquals('D', (char) gradeInfo.getResult());
	}

	/**
	 * Test method for
	 * {@link com.saiten.service.impl.ScoreServiceImpl#findHistoryAnswer(java.lang.Integer, java.lang.String)}
	 * .
	 */
	@Test
	public void testFindHistoryAnswer() {
		fail("Not yet implemented");
	}

	/*	*//**
	 * Test method for
	 * {@link com.saiten.service.impl.ScoreServiceImpl#getSelectedCheckPointList(java.lang.Integer)}
	 * .
	 */
	/*
	 * @Test public void testGetSelectedCheckPointList_1() {
	 * 
	 * Integer bitValue = 32;
	 * 
	 * //List<Short> selectedCheckPointList =
	 * scoreServiceImpl.getSelectedCheckPointList(bitValue);
	 * 
	 * //assertNotNull(selectedCheckPointList); }
	 *//**
	 * Test method for
	 * {@link com.saiten.service.impl.ScoreServiceImpl#getSelectedCheckPointList(java.lang.Integer)}
	 * .
	 */
	/*
	 * @Test public void testGetSelectedCheckPointList_2() {
	 * 
	 * Integer bitValue = 32;
	 * 
	 * //List<Short> selectedCheckPointList =
	 * scoreServiceImpl.getSelectedCheckPointList(bitValue);
	 * 
	 * //assertEquals(1, selectedCheckPointList.size());
	 * 
	 * }
	 *//**
	 * Test method for
	 * {@link com.saiten.service.impl.ScoreServiceImpl#getSelectedCheckPointList(java.lang.Integer)}
	 * .
	 */
	/*
	 * @Test public void testGetSelectedCheckPointList_3() {
	 * 
	 * Integer bitValue = 32;
	 * 
	 * //List<Short> selectedCheckPointList =
	 * scoreServiceImpl.getSelectedCheckPointList(bitValue);
	 * 
	 * //assertEquals(5, (short)selectedCheckPointList.get(0)); }
	 */

	/**
	 * Test method for
	 * {@link com.saiten.service.impl.ScoreServiceImpl#getFirstTimeSecondTimeCheckPoints(java.lang.String, com.saiten.info.QuestionInfo)}
	 * .
	 */
	@Test
	public void testGetFirstTimeSecondTimeCheckPoints() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.saiten.service.impl.ScoreServiceImpl#lockAnswer(int, java.lang.String, java.lang.String, java.util.Date)}
	 * .
	 */
	@Test
	public void testLockAnswer() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.saiten.service.impl.ScoreServiceImpl#unlockAnswer(int, java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public void testUnlockAnswer() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.saiten.service.impl.ScoreServiceImpl#isAnswerAlreadyScored(int, java.lang.String, java.util.Date)}
	 * .
	 */
	@Test
	public void testIsAnswerAlreadyScored() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.saiten.service.impl.ScoreServiceImpl#findPrevOrNextHistoryAnswer(com.saiten.info.QuestionInfo, com.saiten.info.MstScorerInfo, java.util.Date, boolean)}
	 * .
	 */
	@Test
	public void testFindPrevOrNextHistoryAnswer() {
		fail("Not yet implemented");
	}

	final ScoreServiceImpl scoreServiceImpl = new ScoreServiceImpl() {

	};
}
