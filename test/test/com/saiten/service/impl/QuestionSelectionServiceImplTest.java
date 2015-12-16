/**
 * 
 */
package test.com.saiten.service.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.orm.hibernate3.HibernateTemplate;

import test.com.saiten.dao.support.SaitenHibernateDAOSupportTest;

import com.saiten.dao.MstQuestionDAO;
import com.saiten.dao.impl.MstQuestionDAOImpl;
import com.saiten.info.CheckPointInfo;
import com.saiten.info.QuestionInfo;
import com.saiten.service.impl.QuestionSelectionServiceImpl;

/**
 * @author kailash
 *
 */
public class QuestionSelectionServiceImplTest {

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
	 * Test method for {@link com.saiten.service.impl.QuestionSelectionServiceImpl#fetchDbInstanceInfo(java.util.List)}.
	 */
	@Test
	public void testFetchDbInstanceInfo_1() {
		
		List<Integer> questionSeqList = new ArrayList<Integer>();
		questionSeqList.add(9);
		QuestionInfo questionInfo = questionSelectionServiceImpl.fetchDbInstanceInfo(questionSeqList);
		
		assertNotNull(questionInfo);
	}

	/**
	 * Test method for {@link com.saiten.service.impl.QuestionSelectionServiceImpl#fetchDbInstanceInfo(java.util.List)}.
	 */
	@Test
	public void testFetchDbInstanceInfo_2() {
		
		List<Integer> questionSeqList = new ArrayList<Integer>();
		questionSeqList.add(9);
		QuestionInfo questionInfo = questionSelectionServiceImpl.fetchDbInstanceInfo(questionSeqList);
		
		assertEquals("jdbc:mysql://kailashk:3306/saiten_transaction1", questionInfo.getConnectionString());
		assertEquals("tran_obj_score", questionInfo.getAnswerScoreTable());
		assertEquals("tran_desc_score_history", questionInfo.getAnswerScoreHistoryTable());
		assertEquals("manual_1_09.pdf", questionInfo.getManualDocument());
		assertEquals("detail_1_09.pdf", questionInfo.getQuestionFileName());
		assertEquals('1', questionInfo.getSubjectCode());
		assertEquals((long)9, (long)questionInfo.getQuestionNum());
		assertEquals("98", questionInfo.getSide());
		assertEquals("小国A-9", questionInfo.getSubjectShortName());
	}
	/**
	 * Test method for {@link com.saiten.service.impl.QuestionSelectionServiceImpl#fetchDbInstanceInfo(java.util.List, java.lang.String)}.
	 */
	@Test
	public void testFetchDbInstanceInfoListOfIntegerString() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.saiten.service.impl.QuestionSelectionServiceImpl#findQuestionsByMenuIdAndScorerId(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testFindQuestionsByMenuIdAndScorerId() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.saiten.service.impl.QuestionSelectionServiceImpl#findHistoryRecordCount(java.lang.String, java.util.List, java.lang.String, java.util.List)}.
	 */
	@Test
	public void testFindHistoryRecordCount() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.saiten.service.impl.QuestionSelectionServiceImpl#findCheckPoints(int)}.
	 */
	@Test
	public void testFindCheckPoints_1() {
		
		int questionSeq = 9;
		
		List<CheckPointInfo> checkPointInfoList = questionSelectionServiceImpl.findCheckPoints(questionSeq);
		
		assertNotNull(checkPointInfoList);
		
	}
	
	/**
	 * Test method for {@link com.saiten.service.impl.QuestionSelectionServiceImpl#findCheckPoints(int)}.
	 */
	@Test
	public void testFindCheckPoints_2() {
		
		int questionSeq = 9;
		
		List<CheckPointInfo> checkPointInfoList = questionSelectionServiceImpl.findCheckPoints(questionSeq);
		
		assertEquals(6, checkPointInfoList.size());
		
	}
	
	/**
	 * Test method for {@link com.saiten.service.impl.QuestionSelectionServiceImpl#findCheckPoints(int)}.
	 */
	@Test
	public void testFindCheckPoints_3() {
		
		int questionSeq = 9;
		
		List<CheckPointInfo> checkPointInfoList = questionSelectionServiceImpl.findCheckPoints(questionSeq);
		
		CheckPointInfo checkPointInfo = checkPointInfoList.get(0);
		assertNotNull(checkPointInfo);
		assertEquals((short)0, (short)checkPointInfo.getCheckPoint());
		assertEquals("無解答", checkPointInfo.getCheckPointDescription());
		assertEquals(901, checkPointInfo.getCheckPointGroupSeq());
		assertEquals((byte)4, (byte)checkPointInfo.getGroupType());
	}

	/**
	 * Test method for {@link com.saiten.service.impl.QuestionSelectionServiceImpl#findPendingCategories(int)}.
	 */
	@Test
	public void testFindPendingCategories_1() {
		
		int questionSeq = 9;
		
		Map<Integer, String> pendingCategoryGroupMap = questionSelectionServiceImpl.findPendingCategories(questionSeq);
		
		assertNotNull(pendingCategoryGroupMap);
		
	}
	
	/**
	 * Test method for {@link com.saiten.service.impl.QuestionSelectionServiceImpl#findPendingCategories(int)}.
	 */
	@Test
	public void testFindPendingCategories_2() {
		
		int questionSeq = 9;
		
		Map<Integer, String> pendingCategoryGroupMap = questionSelectionServiceImpl.findPendingCategories(questionSeq);
		
		assertEquals(2, pendingCategoryGroupMap.size());
		
	}
	
	/**
	 * Test method for {@link com.saiten.service.impl.QuestionSelectionServiceImpl#findPendingCategories(int)}.
	 */
	@Test
	public void testFindPendingCategories_3() {
		
		int questionSeq = 9;
		
		Map<Integer, String> pendingCategoryGroupMap = questionSelectionServiceImpl.findPendingCategories(questionSeq);
		
		String pendingCategoryDescription = pendingCategoryGroupMap.get(960);
		assertEquals("60.類型こぼれ", pendingCategoryDescription);
		
		pendingCategoryDescription = pendingCategoryGroupMap.get(990);
		assertEquals("90.不一致解答", pendingCategoryDescription);
	}

	/**
	 * Test method for {@link com.saiten.service.impl.QuestionSelectionServiceImpl#findQuestionInfo(short, char)}.
	 */
	@Test
	public void testFindQuestionInfo() {
		fail("Not yet implemented");
	}
	
	final QuestionSelectionServiceImpl questionSelectionServiceImpl = new QuestionSelectionServiceImpl(){
		public MstQuestionDAO getMstQuestionDAO(){
			return new MstQuestionDAOImpl(){
				public HibernateTemplate getHibernateTemplate() {
					return SaitenHibernateDAOSupportTest.getHibernateTemplate();
				}
			};
		}	
	};
}
