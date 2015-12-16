package test.com.saiten.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.orm.hibernate3.HibernateTemplate;

import test.com.saiten.dao.support.SaitenHibernateDAOSupportTest;

import com.saiten.dao.MstGradeDAO;
import com.saiten.dao.MstPendingCategoryDAO;
import com.saiten.dao.MstQuestionDAO;
import com.saiten.dao.MstSamplingEventCondDAO;
import com.saiten.dao.MstSamplingStateCondDAO;
import com.saiten.dao.MstSubjectDAO;
import com.saiten.dao.TranDescScoreHistoryDAO;
import com.saiten.dao.impl.MstGradeDAOImpl;
import com.saiten.dao.impl.MstPendingCategoryDAOImpl;
import com.saiten.dao.impl.MstQuestionDAOImpl;
import com.saiten.dao.impl.MstSamplingEventCondDAOImpl;
import com.saiten.dao.impl.MstSamplingStateCondDAOImpl;
import com.saiten.dao.impl.MstSubjectDAOImpl;
import com.saiten.dao.impl.TranDescScoreHistoryDAOImpl;
import com.saiten.exception.SaitenRuntimeException;
import com.saiten.info.CheckPointInfo;
import com.saiten.info.QuestionInfo;
import com.saiten.info.ScoreInputInfo;
import com.saiten.service.impl.ScoreSearchServiceImpl;
import com.saiten.util.WebAppConst;

/**
 * The class <code>ScoreSearchServiceImplTest</code> contains tests for the
 * class <code>{@link ScoreSearchServiceImpl}</code>.
 * 
 * @generatedBy CodePro at 4/2/13 5:12 PM
 * @author kailash
 * @version $Revision: 1.0 $
 */
public class ScoreSearchServiceImplTest {
	/**
	 * Run the Map<Short, String> findCurrentStateList(String) method test.
	 * 
	 * 
	 * 
	 * @generatedBy CodePro at 4/2/13 5:12 PM
	 * @throws Exception
	 */
	@Test
	public void testFindCurrentStateList_1() throws Exception {

		// method parameter.
		String menuId = "REFERENCE_SAMP_MENU_ID";

		// call to original method and returns 'result'
		Map<Short, String> result = scoreSearchServiceImpl
				.findCurrentStateList(menuId);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this
		// test:
		// java.lang.NullPointerException
		// at
		// org.apache.struts2.ServletActionContext.getServletContext(ServletActionContext.java:139)
		// at
		// com.saiten.service.impl.ScoreSearchServiceImpl.findCurrentStateList(ScoreSearchServiceImpl.java:464)
		assertNotNull(result);

	}

	@Test
	public void testFindCurrentStateList_4() throws Exception {

		// method parameter.
		String menuId = "SCORE_SAMP_MENU_ID";

		// call to original method and returns 'result'
		Map<Short, String> result = scoreSearchServiceImpl
				.findCurrentStateList(menuId);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this
		// test:
		// java.lang.NullPointerException
		// at
		// org.apache.struts2.ServletActionContext.getServletContext(ServletActionContext.java:139)
		// at
		// com.saiten.service.impl.ScoreSearchServiceImpl.findCurrentStateList(ScoreSearchServiceImpl.java:464)
		assertNotNull(result);

	}

	/**
	 * Run the Map<Short, String> findCurrentStateList(String) method test.
	 * 
	 * 
	 * 
	 * @generatedBy CodePro at 4/2/13 5:12 PM
	 * @throws Exception
	 */
	@Test
	public void testFindCurrentStateList_2() throws Exception {

		String menuId = "";

		Map<Short, String> result = scoreSearchServiceImpl
				.findCurrentStateList(menuId);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this
		// test:
		// java.lang.NullPointerException
		// at
		// org.apache.struts2.ServletActionContext.getServletContext(ServletActionContext.java:139)
		// at
		// com.saiten.service.impl.ScoreSearchServiceImpl.findCurrentStateList(ScoreSearchServiceImpl.java:464)

		assertNull(result);
	}

	/**
	 * @throws Exception
	 */
	@Test
	public void testFindCurrentStateList_5() throws Exception {

		String menuId = "";

		Map<Short, String> result = scoreSearchServiceImpl
				.findCurrentStateList(menuId);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this
		// test:
		// java.lang.NullPointerException
		// at
		// org.apache.struts2.ServletActionContext.getServletContext(ServletActionContext.java:139)
		// at
		// com.saiten.service.impl.ScoreSearchServiceImpl.findCurrentStateList(ScoreSearchServiceImpl.java:464)

		assertNull(result);
	}

	/**
	 * Method testFindCurrentStateList_3.
	 * 
	 * @throws Exception
	 * @author kailash
	 */
	@Test
	public void testFindCurrentStateList_3() throws Exception {

		String menuId = "REFERENCE_SAMP_MENU_ID";

		Map<Short, String> result = scoreSearchServiceImpl
				.findCurrentStateList(menuId);

		// check result.size==7, because 7 records for
		// 'reference_sampling_menu_id' in db.
		assertTrue(result.size() == 7);

		// check state_name for corresponding scoring_state, refer db_table
		// 'mst_scoring_state_list'
		assertEquals("選択設問採点待ち", result.get((short) 101).toString());
		assertEquals("1回目採点待ち", result.get((short) 121).toString());
		assertEquals("1回目採点(仮採点)", result.get((short) 122).toString());
		assertEquals("1回目採点(仮保留)", result.get((short) 123).toString());
		assertEquals("2回目採点待ち", result.get((short) 131).toString());
		assertEquals("2回目採点(仮採点)", result.get((short) 132).toString());
		assertEquals("2回目採点(仮保留)", result.get((short) 133).toString());
	}

	/**
	 * @throws Exception
	 */
	@Test
	public void testFindCurrentStateList_6() throws Exception {

		String menuId = "SCORE_SAMP_MENU_ID";

		Map<Short, String> result = scoreSearchServiceImpl
				.findCurrentStateList(menuId);

		// check result.size==7, because 7 records for
		// 'reference_sampling_menu_id' in db.
		assertTrue(result.size() == 4);

		// check state_name for corresponding scoring_state, refer db_table
		// 'mst_scoring_state_list'
		assertEquals("2回目採点待ち", result.get((short) 131).toString());
		assertEquals("確認作業待ち", result.get((short) 141).toString());
		assertEquals("検知作業待ち", result.get((short) 151).toString());
		assertEquals("確定", result.get((short) 500).toString());
	}

	/**
	 * Run the List findGradeSeqList(Integer,Integer[]) method test.
	 * 
	 * 
	 * 
	 * @generatedBy CodePro at 4/2/13 5:12 PM
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void testFindGradeSeqList_1() throws Exception {

		Integer questionSeq = new Integer(2);
		Integer[] gradeNum = new Integer[] { 1, 2, 3 };

		List result = scoreSearchServiceImpl.findGradeSeqList(questionSeq,
				gradeNum);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this
		// test:
		// java.lang.NullPointerException
		// at
		// com.saiten.dao.support.SaitenHibernateDAOSupport.getHibernateTemplate(SaitenHibernateDAOSupport.java:49)
		// at
		// com.saiten.dao.impl.MstGradeDAOImpl.findGradeSeqList(MstGradeDAOImpl.java:52)
		// at
		// com.saiten.service.impl.ScoreSearchServiceImpl.findGradeSeqList(ScoreSearchServiceImpl.java:71)
		assertNotNull(result);
	}

	/**
	 * @throws Exception
	 * @author kailash
	 */
	@SuppressWarnings("rawtypes")
	@Test(expected = SaitenRuntimeException.class)
	public void testFindGradeSeqList_2() throws Exception {

		Integer questionSeq = new Integer(2);
		Integer[] gradeNum = new Integer[] {};

		List result = scoreSearchServiceImpl.findGradeSeqList(questionSeq,
				gradeNum);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this
		// test:
		// java.lang.NullPointerException
		// at
		// com.saiten.dao.support.SaitenHibernateDAOSupport.getHibernateTemplate(SaitenHibernateDAOSupport.java:49)
		// at
		// com.saiten.dao.impl.MstGradeDAOImpl.findGradeSeqList(MstGradeDAOImpl.java:52)
		// at
		// com.saiten.service.impl.ScoreSearchServiceImpl.findGradeSeqList(ScoreSearchServiceImpl.java:71)

		// From here expected result is 'HibernateQueryException'
		assertTrue(result.size() == 0);
	}

	/**
	 * @throws Exception
	 * @author kailash
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void testFindGradeSeqList_3() throws Exception {

		Integer questionSeq = new Integer(62);
		Integer[] gradeNum = new Integer[] { 1, 2, 3 };

		List result = scoreSearchServiceImpl.findGradeSeqList(questionSeq,
				gradeNum);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this
		// test:
		// java.lang.NullPointerException
		// at
		// com.saiten.dao.support.SaitenHibernateDAOSupport.getHibernateTemplate(SaitenHibernateDAOSupport.java:49)
		// at
		// com.saiten.dao.impl.MstGradeDAOImpl.findGradeSeqList(MstGradeDAOImpl.java:52)
		// at
		// com.saiten.service.impl.ScoreSearchServiceImpl.findGradeSeqList(ScoreSearchServiceImpl.java:71)

		assertTrue(result.size() == 0);
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void testFindGradeSeqList_4() throws Exception {

		Integer questionSeq = new Integer(2);
		Integer[] gradeNum = new Integer[] { 1, 2, 3 };

		List result = scoreSearchServiceImpl.findGradeSeqList(questionSeq,
				gradeNum);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this
		// test:
		// java.lang.NullPointerException
		// at
		// com.saiten.dao.support.SaitenHibernateDAOSupport.getHibernateTemplate(SaitenHibernateDAOSupport.java:49)
		// at
		// com.saiten.dao.impl.MstGradeDAOImpl.findGradeSeqList(MstGradeDAOImpl.java:52)
		// at
		// com.saiten.service.impl.ScoreSearchServiceImpl.findGradeSeqList(ScoreSearchServiceImpl.java:71)
		assertTrue(result.contains(200001));
		assertTrue(result.contains(200002));
		assertTrue(result.contains(200003));
	}

	/**
	 * Run the Map<Short, String> findHistoryEventList(String) method test.
	 * 
	 * @throws Exception
	 * 
	 * @generatedBy CodePro at 4/2/13 5:12 PM
	 */
	@Test
	public void testFindHistoryEventList_1() throws Exception {

		String menuId = "REFERENCE_SAMP_MENU_ID";

		Map<Short, String> result = scoreSearchServiceImpl
				.findHistoryEventList(menuId);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this
		// test:
		// java.lang.NullPointerException
		// at
		// org.apache.struts2.ServletActionContext.getServletContext(ServletActionContext.java:139)
		// at
		// com.saiten.service.impl.ScoreSearchServiceImpl.findHistoryEventList(ScoreSearchServiceImpl.java:415)
		assertNotNull(result);
	}

	/**
	 * @throws Exception
	 */
	@Test
	public void testFindHistoryEventList_4() throws Exception {

		String menuId = "SCORE_SAMP_MENU_ID";

		Map<Short, String> result = scoreSearchServiceImpl
				.findHistoryEventList(menuId);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this
		// test:
		// java.lang.NullPointerException
		// at
		// org.apache.struts2.ServletActionContext.getServletContext(ServletActionContext.java:139)
		// at
		// com.saiten.service.impl.ScoreSearchServiceImpl.findHistoryEventList(ScoreSearchServiceImpl.java:415)
		assertNotNull(result);
	}

	/**
	 * Run the Map<Short, String> findHistoryEventList(String) method test.
	 * 
	 * @throws Exception
	 * 
	 * @generatedBy CodePro at 4/2/13 5:12 PM
	 */
	@Test
	public void testFindHistoryEventList_2() throws Exception {

		String menuId = "";

		Map<Short, String> result = scoreSearchServiceImpl
				.findHistoryEventList(menuId);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this
		// test:
		// java.lang.NullPointerException
		// at
		// org.apache.struts2.ServletActionContext.getServletContext(ServletActionContext.java:139)
		// at
		// com.saiten.service.impl.ScoreSearchServiceImpl.findHistoryEventList(ScoreSearchServiceImpl.java:415)
		assertNull(result);
	}

	/**
	 * @throws Exception
	 */
	@Test
	public void testFindHistoryEventList_5() throws Exception {

		String menuId = "";

		Map<Short, String> result = scoreSearchServiceImpl
				.findHistoryEventList(menuId);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this
		// test:
		// java.lang.NullPointerException
		// at
		// org.apache.struts2.ServletActionContext.getServletContext(ServletActionContext.java:139)
		// at
		// com.saiten.service.impl.ScoreSearchServiceImpl.findHistoryEventList(ScoreSearchServiceImpl.java:415)
		assertNull(result);
	}

	/**
	 * @throws Exception
	 * @author kailash
	 */
	@Test
	public void testFindHistoryEventList_3() throws Exception {

		String menuId = "REFERENCE_SAMP_MENU_ID";

		Map<Short, String> result = scoreSearchServiceImpl
				.findHistoryEventList(menuId);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this
		// test:
		// java.lang.NullPointerException
		// at
		// org.apache.struts2.ServletActionContext.getServletContext(ServletActionContext.java:139)
		// at
		// com.saiten.service.impl.ScoreSearchServiceImpl.findHistoryEventList(ScoreSearchServiceImpl.java:415)
		assertTrue(result.size() == 4);
		assertEquals("OMR Read", result.get((short) 10).toString());
		assertEquals("状態遷移", result.get((short) 80).toString());
		assertEquals("1回目採点", result.get((short) 120).toString());
		assertEquals("2回目採点", result.get((short) 130).toString());
	}

	/**
	 * @throws Exception
	 */
	@Test
	public void testFindHistoryEventList_6() throws Exception {

		String menuId = "SCORE_SAMP_MENU_ID";

		Map<Short, String> result = scoreSearchServiceImpl
				.findHistoryEventList(menuId);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this
		// test:
		// java.lang.NullPointerException
		// at
		// org.apache.struts2.ServletActionContext.getServletContext(ServletActionContext.java:139)
		// at
		// com.saiten.service.impl.ScoreSearchServiceImpl.findHistoryEventList(ScoreSearchServiceImpl.java:415)
		assertTrue(result.size() == 14);
		assertEquals("OMR Read", result.get((short) 10).toString());
		assertEquals("バッチサマリ採点反映", result.get((short) 60).toString());
		assertEquals("1回目採点", result.get((short) 120).toString());
		assertEquals("2回目採点", result.get((short) 130).toString());
		assertEquals("確認作業", result.get((short) 140).toString());
		assertEquals("検知作業", result.get((short) 150).toString());
		assertEquals("保留採点", result.get((short) 160).toString());
		assertEquals("類型不一致採点", result.get((short) 170).toString());
		assertEquals("はみ出し採点", result.get((short) 180).toString());
		assertEquals("否認採点", result.get((short) 190).toString());
		assertEquals("類型こぼれ採点", result.get((short) 210).toString());
		assertEquals("サンプリング(＋採点)", result.get((short) 220).toString());
		assertEquals("強制採点", result.get((short) 240).toString());
		assertEquals("特別採点", result.get((short) 250).toString());
	}

	/**
	 * Run the List findPendingCategorySeqList(Integer,Short[]) method test.
	 * 
	 * @throws Exception
	 * 
	 * @generatedBy CodePro at 4/2/13 5:12 PM
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void testFindPendingCategorySeqList_1() throws Exception {

		Integer questionSeq = new Integer(2);
		Short[] pendingCategoryList = new Short[] { 60, 70, 90 };

		List result = scoreSearchServiceImpl.findPendingCategorySeqList(
				questionSeq, pendingCategoryList);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this
		// test:
		// java.lang.NullPointerException
		// at
		// com.saiten.dao.support.SaitenHibernateDAOSupport.getHibernateTemplate(SaitenHibernateDAOSupport.java:49)
		// at
		// com.saiten.dao.impl.MstPendingCategoryDAOImpl.findPendingCategorySeqList(MstPendingCategoryDAOImpl.java:77)
		// at
		// com.saiten.service.impl.ScoreSearchServiceImpl.findPendingCategorySeqList(ScoreSearchServiceImpl.java:85)
		assertNotNull(result);
	}

	/**
	 * @throws Exception
	 * @author kailash
	 */
	@SuppressWarnings("rawtypes")
	@Test(expected = SaitenRuntimeException.class)
	public void testFindPendingCategorySeqList_2() throws Exception {

		Integer questionSeq = new Integer(2);
		Short[] pendingCategoryList = new Short[] {};

		List result = scoreSearchServiceImpl.findPendingCategorySeqList(
				questionSeq, pendingCategoryList);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this
		// test:
		// java.lang.NullPointerException
		// at
		// com.saiten.dao.support.SaitenHibernateDAOSupport.getHibernateTemplate(SaitenHibernateDAOSupport.java:49)
		// at
		// com.saiten.dao.impl.MstPendingCategoryDAOImpl.findPendingCategorySeqList(MstPendingCategoryDAOImpl.java:77)
		// at
		// com.saiten.service.impl.ScoreSearchServiceImpl.findPendingCategorySeqList(ScoreSearchServiceImpl.java:85)
		assertTrue(result.size() == 0);
	}

	/**
	 * @throws Exception
	 * @author kailash
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void testFindPendingCategorySeqList_3() throws Exception {

		Integer questionSeq = new Integer(62);
		Short[] pendingCategoryList = new Short[] { 60, 70, 90 };

		List result = scoreSearchServiceImpl.findPendingCategorySeqList(
				questionSeq, pendingCategoryList);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this
		// test:
		// java.lang.NullPointerException
		// at
		// com.saiten.dao.support.SaitenHibernateDAOSupport.getHibernateTemplate(SaitenHibernateDAOSupport.java:49)
		// at
		// com.saiten.dao.impl.MstPendingCategoryDAOImpl.findPendingCategorySeqList(MstPendingCategoryDAOImpl.java:77)
		// at
		// com.saiten.service.impl.ScoreSearchServiceImpl.findPendingCategorySeqList(ScoreSearchServiceImpl.java:85)
		assertTrue(result.size() == 0);
	}

	/**
	 * @throws Exception
	 * @author kailash
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void testFindPendingCategorySeqList_4() throws Exception {

		Integer questionSeq = new Integer(2);
		Short[] pendingCategoryList = new Short[] { 60, 70, 90 };

		List result = scoreSearchServiceImpl.findPendingCategorySeqList(
				questionSeq, pendingCategoryList);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this
		// test:
		// java.lang.NullPointerException
		// at
		// com.saiten.dao.support.SaitenHibernateDAOSupport.getHibernateTemplate(SaitenHibernateDAOSupport.java:49)
		// at
		// com.saiten.dao.impl.MstPendingCategoryDAOImpl.findPendingCategorySeqList(MstPendingCategoryDAOImpl.java:77)
		// at
		// com.saiten.service.impl.ScoreSearchServiceImpl.findPendingCategorySeqList(ScoreSearchServiceImpl.java:85)
		assertTrue(result.contains(260));
		assertTrue(result.contains(290));
	}

	/**
	 * Run the Integer findQuestionSeq(String,Short) method test.
	 * 
	 * @throws Exception
	 * 
	 * @generatedBy CodePro at 4/2/13 5:12 PM
	 */
	@Test
	public void testFindQuestionSeq_1() throws Exception {

		String subjectCode = new String("1");
		Short questionNum = new Short((short) 2);

		List<Integer> result = scoreSearchServiceImpl.findQuestionSeq(
				subjectCode, questionNum);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this
		// test:
		// java.lang.NullPointerException
		// at
		// com.saiten.dao.support.SaitenHibernateDAOSupport.getHibernateTemplate(SaitenHibernateDAOSupport.java:49)
		// at
		// com.saiten.dao.impl.MstQuestionDAOImpl.findQuestionSeq(MstQuestionDAOImpl.java:155)
		// at
		// com.saiten.service.impl.ScoreSearchServiceImpl.findQuestionSeq(ScoreSearchServiceImpl.java:58)
		assertNotNull(result);
	}

	/**
	 * @throws Exception
	 * @author kailash
	 */
	@Test
	public void testFindQuestionSeq_2() throws Exception {

		String subjectCode = new String("6");
		Short questionNum = new Short((short) 2);

		List<Integer> result = scoreSearchServiceImpl.findQuestionSeq(
				subjectCode, questionNum);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this
		// test:
		// java.lang.NullPointerException
		// at
		// com.saiten.dao.support.SaitenHibernateDAOSupport.getHibernateTemplate(SaitenHibernateDAOSupport.java:49)
		// at
		// com.saiten.dao.impl.MstQuestionDAOImpl.findQuestionSeq(MstQuestionDAOImpl.java:155)
		// at
		// com.saiten.service.impl.ScoreSearchServiceImpl.findQuestionSeq(ScoreSearchServiceImpl.java:58)
		assertEquals(true, result.isEmpty());
	}

	/**
	 * @throws Exception
	 * @author kailash
	 */
	@Test
	public void testFindQuestionSeq_3() throws Exception {

		String subjectCode = new String("1");
		Short questionNum = new Short((short) 108);

		List<Integer> result = scoreSearchServiceImpl.findQuestionSeq(
				subjectCode, questionNum);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this
		// test:
		// java.lang.NullPointerException
		// at
		// com.saiten.dao.support.SaitenHibernateDAOSupport.getHibernateTemplate(SaitenHibernateDAOSupport.java:49)
		// at
		// com.saiten.dao.impl.MstQuestionDAOImpl.findQuestionSeq(MstQuestionDAOImpl.java:155)
		// at
		// com.saiten.service.impl.ScoreSearchServiceImpl.findQuestionSeq(ScoreSearchServiceImpl.java:58)
		assertEquals(true, result.isEmpty());
	}

	/**
	 * @throws Exception
	 * @author kailash
	 */
	@Test
	public void testFindQuestionSeq_4() throws Exception {

		String subjectCode = new String("1");
		Short questionNum = new Short((short) 2);

		List<Integer> result = scoreSearchServiceImpl.findQuestionSeq(
				subjectCode, questionNum);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this
		// test:
		// java.lang.NullPointerException
		// at
		// com.saiten.dao.support.SaitenHibernateDAOSupport.getHibernateTemplate(SaitenHibernateDAOSupport.java:49)
		// at
		// com.saiten.dao.impl.MstQuestionDAOImpl.findQuestionSeq(MstQuestionDAOImpl.java:155)
		// at
		// com.saiten.service.impl.ScoreSearchServiceImpl.findQuestionSeq(ScoreSearchServiceImpl.java:58)
		assertEquals((Integer) 2, result.get(0));
	}

	/**
	 * Run the Map<String, String> findSubjectNameList() method test.
	 * 
	 * @throws Exception
	 * 
	 * @generatedBy CodePro at 4/2/13 5:12 PM
	 */
	@Test
	public void testFindSubjectNameList_1() throws Exception {

		Map<String, String> result = scoreSearchServiceImpl
				.findSubjectNameList();

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this
		// test:
		// java.lang.NullPointerException
		// at
		// com.saiten.dao.support.SaitenHibernateDAOSupport.getHibernateTemplate(SaitenHibernateDAOSupport.java:49)
		// at
		// com.saiten.dao.impl.MstSubjectDAOImpl.findSubjectNameList(MstSubjectDAOImpl.java:29)
		// at
		// com.saiten.service.impl.ScoreSearchServiceImpl.findSubjectNameList(ScoreSearchServiceImpl.java:97)
		assertNotNull(result);
	}

	/**
	 * @throws Exception
	 * @author kailash
	 */
	@Test
	public void testFindSubjectNameList_2() throws Exception {

		Map<String, String> result = scoreSearchServiceImpl
				.findSubjectNameList();

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this
		// test:
		// java.lang.NullPointerException
		// at
		// com.saiten.dao.support.SaitenHibernateDAOSupport.getHibernateTemplate(SaitenHibernateDAOSupport.java:49)
		// at
		// com.saiten.dao.impl.MstSubjectDAOImpl.findSubjectNameList(MstSubjectDAOImpl.java:29)
		// at
		// com.saiten.service.impl.ScoreSearchServiceImpl.findSubjectNameList(ScoreSearchServiceImpl.java:97)
		assertTrue(result.size() == 13);
		assertEquals("中国語A", result.get("5-a"));
		assertEquals("中国語B", result.get("5-b"));
		assertEquals("中学校アンケート", result.get("5-I"));
		assertEquals("中数学A", result.get("5-c"));
		assertEquals("中数学B", result.get("5-d"));
		assertEquals("中生徒アンケート", result.get("5-f"));
		assertEquals("中英語", result.get("5-e"));
		assertEquals("小児童アンケート", result.get("4-5"));
		assertEquals("小国語A", result.get("4-1"));
		assertEquals("小国語B", result.get("4-2"));
		assertEquals("小学校アンケート", result.get("4-9"));
		assertEquals("小算数A", result.get("4-3"));
		assertEquals("小算数B", result.get("4-4"));

	}

	/**
	 * Run the List<TranDescScoreInfo>
	 * searchAnswerRecords(QuestionInfo,ScoreInputInfo) method test.
	 * 
	 * @throws Exception
	 * 
	 * @generatedBy CodePro at 4/2/13 5:12 PM
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void testSearchAnswerRecords_1() throws Exception {

		QuestionInfo questionInfo = new QuestionInfo();
		questionInfo.setAnswerScoreHistoryTable("tran_desc_score_history");
		questionInfo.setAnswerScoreTable("tran_obj_score");

		List<CheckPointInfo> checkPointList = new ArrayList<CheckPointInfo>();
		questionInfo.setCheckPointList(checkPointList);

		CheckPointInfo checkPointInfo = new CheckPointInfo();
		checkPointInfo.setCheckPoint((short) 0);
		checkPointInfo.setCheckPointDescription("無解答");
		checkPointInfo.setCheckPointGroupSeq(901);
		checkPointInfo.setGroupType((byte) 4);
		checkPointList.add(checkPointInfo);

		checkPointInfo = new CheckPointInfo();
		checkPointInfo.setCheckPoint((short) 1);
		checkPointInfo.setCheckPointDescription("１と解答しているもの");
		checkPointInfo.setCheckPointGroupSeq(901);
		checkPointInfo.setGroupType((byte) 4);
		checkPointList.add(checkPointInfo);

		checkPointInfo = new CheckPointInfo();
		checkPointInfo.setCheckPoint((short) 2);
		checkPointInfo.setCheckPointDescription("２と解答しているもの");
		checkPointInfo.setCheckPointGroupSeq(901);
		checkPointInfo.setGroupType((byte) 4);
		checkPointList.add(checkPointInfo);

		checkPointInfo = new CheckPointInfo();
		checkPointInfo.setCheckPoint((short) 3);
		checkPointInfo.setCheckPointDescription("３と解答しているもの");
		checkPointInfo.setCheckPointGroupSeq(901);
		checkPointInfo.setGroupType((byte) 4);
		checkPointList.add(checkPointInfo);

		checkPointInfo = new CheckPointInfo();
		checkPointInfo.setCheckPoint((short) 4);
		checkPointInfo.setCheckPointDescription("４と解答しているもの");
		checkPointInfo.setCheckPointGroupSeq(901);
		checkPointInfo.setGroupType((byte) 4);
		checkPointList.add(checkPointInfo);

		checkPointInfo = new CheckPointInfo();
		checkPointInfo.setCheckPoint((short) 24);
		checkPointInfo.setCheckPointDescription("上記以外の解答");
		checkPointInfo.setCheckPointGroupSeq(901);
		checkPointInfo.setGroupType((byte) 4);
		checkPointList.add(checkPointInfo);

		questionInfo
				.setConnectionString("jdbc:mysql://kailashk:3306/saiten_transaction1");
		questionInfo.setManualDocument("manual_1_09.pdf");
		questionInfo.setQuestionFileName("detail_1_09.pdf");
		questionInfo.setQuestionNum((short) 9);
		questionInfo.setQuestionSeq(9);
		questionInfo.setSide("98");
		questionInfo.setSubjectCode("1");
		questionInfo.setSubjectShortName("小国A-9");
		questionInfo.setMenuId(WebAppConst.FORCED_MENU_ID);

		ScoreInputInfo scoreInputInfo = new ScoreInputInfo();
		scoreInputInfo.setScoreHistoryInfo(null);
		scoreInputInfo.setScoreCurrentInfo(null);
		scoreInputInfo.setSubjectCode("1");
		scoreInputInfo.setResultCount(100);
		scoreInputInfo.setQuestionNum((short) 9);
		scoreInputInfo.setAnswerFormNum("");

		String scorerId = "KS00975";

		Integer startRecord = null;
		Integer endRecord = null;
		Boolean forceAndStateTransitionFlag = true;
		List result = scoreSearchServiceImpl.searchAnswerRecords(questionInfo,
				scoreInputInfo, scorerId, forceAndStateTransitionFlag,
				startRecord, endRecord);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this
		// test:
		// java.lang.NullPointerException
		// at
		// com.saiten.dao.support.SaitenHibernateDAOSupport.getHibernateTemplate(SaitenHibernateDAOSupport.java:43)
		// at
		// com.saiten.dao.impl.TranDescScoreHistoryDAOImpl.searchAnswerRecords(TranDescScoreHistoryDAOImpl.java:402)
		// at
		// com.saiten.service.impl.ScoreSearchServiceImpl.searchAnswerRecords(ScoreSearchServiceImpl.java:124)
		assertNotNull(result);
	}

	/**
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void testSearchAnswerRecords_2() throws Exception {

		QuestionInfo questionInfo = new QuestionInfo();
		questionInfo.setAnswerScoreHistoryTable("tran_desc_score_history");
		questionInfo.setAnswerScoreTable("tran_obj_score");

		questionInfo
				.setConnectionString("jdbc:mysql://kailashk:3306/saiten_transaction1");
		questionInfo.setManualDocument("manual_1_09.pdf");
		questionInfo.setQuestionFileName("detail_1_09.pdf");
		questionInfo.setQuestionNum((short) 9);
		questionInfo.setQuestionSeq(9);
		questionInfo.setSide("98");
		questionInfo.setSubjectCode("1");
		questionInfo.setSubjectShortName("小国A-9");
		questionInfo.setMenuId(WebAppConst.FORCED_MENU_ID);

		ScoreInputInfo scoreInputInfo = new ScoreInputInfo();
		scoreInputInfo.setScoreHistoryInfo(null);
		scoreInputInfo.setScoreCurrentInfo(null);
		scoreInputInfo.setSubjectCode("1");
		scoreInputInfo.setResultCount(null);
		scoreInputInfo.setQuestionNum((short) 9);
		scoreInputInfo.setAnswerFormNum("");

		String scorerId = "KS00975";

		Integer startRecord = null;
		Integer endRecord = null;
		Boolean forceAndStateTransitionFlag = true;
		List result = scoreSearchServiceImpl.searchAnswerRecords(questionInfo,
				scoreInputInfo, scorerId, forceAndStateTransitionFlag,
				startRecord, endRecord);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this
		// test:
		// java.lang.NullPointerException
		// at
		// com.saiten.dao.support.SaitenHibernateDAOSupport.getHibernateTemplate(SaitenHibernateDAOSupport.java:43)
		// at
		// com.saiten.dao.impl.TranDescScoreHistoryDAOImpl.searchAnswerRecords(TranDescScoreHistoryDAOImpl.java:402)
		// at
		// com.saiten.service.impl.ScoreSearchServiceImpl.searchAnswerRecords(ScoreSearchServiceImpl.java:124)
		assertNotNull(result);
	}

	/**
	 * Run the List<TranDescScoreInfo>
	 * searchAnswerRecords(QuestionInfo,ScoreInputInfo) method test.
	 * 
	 * @throws Exception
	 * 
	 * @generatedBy CodePro at 4/2/13 5:12 PM
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void testSearchAnswerRecords_3() throws Exception {

		QuestionInfo questionInfo = new QuestionInfo();
		questionInfo.setAnswerScoreHistoryTable("tran_desc_score_history");
		questionInfo.setAnswerScoreTable("tran_obj_score");

		List<CheckPointInfo> checkPointList = new ArrayList<CheckPointInfo>();
		questionInfo.setCheckPointList(checkPointList);

		CheckPointInfo checkPointInfo = new CheckPointInfo();
		checkPointInfo.setCheckPoint((short) 0);
		checkPointInfo.setCheckPointDescription("無解答");
		checkPointInfo.setCheckPointGroupSeq(901);
		checkPointInfo.setGroupType((byte) 4);
		checkPointList.add(checkPointInfo);

		checkPointInfo = new CheckPointInfo();
		checkPointInfo.setCheckPoint((short) 1);
		checkPointInfo.setCheckPointDescription("１と解答しているもの");
		checkPointInfo.setCheckPointGroupSeq(901);
		checkPointInfo.setGroupType((byte) 4);
		checkPointList.add(checkPointInfo);

		checkPointInfo = new CheckPointInfo();
		checkPointInfo.setCheckPoint((short) 2);
		checkPointInfo.setCheckPointDescription("２と解答しているもの");
		checkPointInfo.setCheckPointGroupSeq(901);
		checkPointInfo.setGroupType((byte) 4);
		checkPointList.add(checkPointInfo);

		checkPointInfo = new CheckPointInfo();
		checkPointInfo.setCheckPoint((short) 3);
		checkPointInfo.setCheckPointDescription("３と解答しているもの");
		checkPointInfo.setCheckPointGroupSeq(901);
		checkPointInfo.setGroupType((byte) 4);
		checkPointList.add(checkPointInfo);

		checkPointInfo = new CheckPointInfo();
		checkPointInfo.setCheckPoint((short) 4);
		checkPointInfo.setCheckPointDescription("４と解答しているもの");
		checkPointInfo.setCheckPointGroupSeq(901);
		checkPointInfo.setGroupType((byte) 4);
		checkPointList.add(checkPointInfo);

		checkPointInfo = new CheckPointInfo();
		checkPointInfo.setCheckPoint((short) 24);
		checkPointInfo.setCheckPointDescription("上記以外の解答");
		checkPointInfo.setCheckPointGroupSeq(901);
		checkPointInfo.setGroupType((byte) 4);
		checkPointList.add(checkPointInfo);

		questionInfo
				.setConnectionString("jdbc:mysql://kailashk:3306/saiten_transaction1");
		questionInfo.setManualDocument("manual_1_09.pdf");
		questionInfo.setQuestionFileName("detail_1_09.pdf");
		questionInfo.setQuestionNum((short) 9);
		questionInfo.setQuestionSeq(9);
		questionInfo.setSide("98");
		questionInfo.setSubjectCode("1");
		questionInfo.setSubjectShortName("小国A-9");
		questionInfo.setMenuId(WebAppConst.REFERENCE_SAMP_MENU_ID);

		ScoreInputInfo scoreInputInfo = new ScoreInputInfo();
		scoreInputInfo.setScoreHistoryInfo(null);
		scoreInputInfo.setScoreCurrentInfo(null);
		scoreInputInfo.setSubjectCode("1");
		scoreInputInfo.setResultCount(100);
		scoreInputInfo.setQuestionNum((short) 9);
		scoreInputInfo.setAnswerFormNum("");

		String scorerId = "KS00975";

		Integer startRecord = null;
		Integer endRecord = null;
		Boolean forceAndStateTransitionFlag = null;
		List result = scoreSearchServiceImpl.searchAnswerRecords(questionInfo,
				scoreInputInfo, scorerId, forceAndStateTransitionFlag,
				startRecord, endRecord);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this
		// test:
		// java.lang.NullPointerException
		// at
		// com.saiten.dao.support.SaitenHibernateDAOSupport.getHibernateTemplate(SaitenHibernateDAOSupport.java:43)
		// at
		// com.saiten.dao.impl.TranDescScoreHistoryDAOImpl.searchAnswerRecords(TranDescScoreHistoryDAOImpl.java:402)
		// at
		// com.saiten.service.impl.ScoreSearchServiceImpl.searchAnswerRecords(ScoreSearchServiceImpl.java:124)
		assertNotNull(result);
	}

	/**
	 * Run the List<TranDescScoreInfo>
	 * searchAnswerRecords(QuestionInfo,ScoreInputInfo) method test.
	 * 
	 * @throws Exception
	 * 
	 * @generatedBy CodePro at 4/2/13 5:12 PM
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void testSearchAnswerRecords_4() throws Exception {

		QuestionInfo questionInfo = new QuestionInfo();
		questionInfo.setAnswerScoreHistoryTable("tran_desc_score_history");
		questionInfo.setAnswerScoreTable("tran_obj_score");

		List<CheckPointInfo> checkPointList = new ArrayList<CheckPointInfo>();
		questionInfo.setCheckPointList(checkPointList);

		CheckPointInfo checkPointInfo = new CheckPointInfo();
		checkPointInfo.setCheckPoint((short) 0);
		checkPointInfo.setCheckPointDescription("無解答");
		checkPointInfo.setCheckPointGroupSeq(901);
		checkPointInfo.setGroupType((byte) 4);
		checkPointList.add(checkPointInfo);

		checkPointInfo = new CheckPointInfo();
		checkPointInfo.setCheckPoint((short) 1);
		checkPointInfo.setCheckPointDescription("１と解答しているもの");
		checkPointInfo.setCheckPointGroupSeq(901);
		checkPointInfo.setGroupType((byte) 4);
		checkPointList.add(checkPointInfo);

		checkPointInfo = new CheckPointInfo();
		checkPointInfo.setCheckPoint((short) 2);
		checkPointInfo.setCheckPointDescription("２と解答しているもの");
		checkPointInfo.setCheckPointGroupSeq(901);
		checkPointInfo.setGroupType((byte) 4);
		checkPointList.add(checkPointInfo);

		checkPointInfo = new CheckPointInfo();
		checkPointInfo.setCheckPoint((short) 3);
		checkPointInfo.setCheckPointDescription("３と解答しているもの");
		checkPointInfo.setCheckPointGroupSeq(901);
		checkPointInfo.setGroupType((byte) 4);
		checkPointList.add(checkPointInfo);

		checkPointInfo = new CheckPointInfo();
		checkPointInfo.setCheckPoint((short) 4);
		checkPointInfo.setCheckPointDescription("４と解答しているもの");
		checkPointInfo.setCheckPointGroupSeq(901);
		checkPointInfo.setGroupType((byte) 4);
		checkPointList.add(checkPointInfo);

		checkPointInfo = new CheckPointInfo();
		checkPointInfo.setCheckPoint((short) 24);
		checkPointInfo.setCheckPointDescription("上記以外の解答");
		checkPointInfo.setCheckPointGroupSeq(901);
		checkPointInfo.setGroupType((byte) 4);
		checkPointList.add(checkPointInfo);

		questionInfo
				.setConnectionString("jdbc:mysql://kailashk:3306/saiten_transaction1");
		questionInfo.setManualDocument("manual_1_09.pdf");
		questionInfo.setQuestionFileName("detail_1_09.pdf");
		questionInfo.setQuestionNum((short) 9);
		questionInfo.setQuestionSeq(9);
		questionInfo.setSide("98");
		questionInfo.setSubjectCode("1");
		questionInfo.setSubjectShortName("小国A-9");
		questionInfo.setMenuId(WebAppConst.SCORE_SAMP_MENU_ID);

		ScoreInputInfo scoreInputInfo = new ScoreInputInfo();
		scoreInputInfo.setScoreHistoryInfo(null);
		scoreInputInfo.setScoreCurrentInfo(null);
		scoreInputInfo.setSubjectCode("1");
		scoreInputInfo.setResultCount(100);
		scoreInputInfo.setQuestionNum((short) 9);
		scoreInputInfo.setAnswerFormNum("");

		String scorerId = "KS00975";

		Integer startRecord = null;
		Integer endRecord = null;
		Boolean forceAndStateTransitionFlag = null;
		List result = scoreSearchServiceImpl.searchAnswerRecords(questionInfo,
				scoreInputInfo, scorerId, forceAndStateTransitionFlag,
				startRecord, endRecord);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this
		// test:
		// java.lang.NullPointerException
		// at
		// com.saiten.dao.support.SaitenHibernateDAOSupport.getHibernateTemplate(SaitenHibernateDAOSupport.java:43)
		// at
		// com.saiten.dao.impl.TranDescScoreHistoryDAOImpl.searchAnswerRecords(TranDescScoreHistoryDAOImpl.java:402)
		// at
		// com.saiten.service.impl.ScoreSearchServiceImpl.searchAnswerRecords(ScoreSearchServiceImpl.java:124)
		assertNotNull(result);
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void testSearchAnswerRecords_5() throws Exception {
		QuestionInfo questionInfo = new QuestionInfo();
		questionInfo.setAnswerScoreHistoryTable("tran_desc_score_history");
		questionInfo.setAnswerScoreTable("tran_obj_score");

		List<CheckPointInfo> checkPointList = new ArrayList<CheckPointInfo>();
		questionInfo.setCheckPointList(checkPointList);

		CheckPointInfo checkPointInfo = new CheckPointInfo();
		checkPointInfo.setCheckPoint((short) 0);
		checkPointInfo.setCheckPointDescription("無解答");
		checkPointInfo.setCheckPointGroupSeq(901);
		checkPointInfo.setGroupType((byte) 4);
		checkPointList.add(checkPointInfo);

		checkPointInfo = new CheckPointInfo();
		checkPointInfo.setCheckPoint((short) 1);
		checkPointInfo.setCheckPointDescription("１と解答しているもの");
		checkPointInfo.setCheckPointGroupSeq(901);
		checkPointInfo.setGroupType((byte) 4);
		checkPointList.add(checkPointInfo);

		checkPointInfo = new CheckPointInfo();
		checkPointInfo.setCheckPoint((short) 2);
		checkPointInfo.setCheckPointDescription("２と解答しているもの");
		checkPointInfo.setCheckPointGroupSeq(901);
		checkPointInfo.setGroupType((byte) 4);
		checkPointList.add(checkPointInfo);

		checkPointInfo = new CheckPointInfo();
		checkPointInfo.setCheckPoint((short) 3);
		checkPointInfo.setCheckPointDescription("３と解答しているもの");
		checkPointInfo.setCheckPointGroupSeq(901);
		checkPointInfo.setGroupType((byte) 4);
		checkPointList.add(checkPointInfo);

		checkPointInfo = new CheckPointInfo();
		checkPointInfo.setCheckPoint((short) 4);
		checkPointInfo.setCheckPointDescription("４と解答しているもの");
		checkPointInfo.setCheckPointGroupSeq(901);
		checkPointInfo.setGroupType((byte) 4);
		checkPointList.add(checkPointInfo);

		checkPointInfo = new CheckPointInfo();
		checkPointInfo.setCheckPoint((short) 24);
		checkPointInfo.setCheckPointDescription("上記以外の解答");
		checkPointInfo.setCheckPointGroupSeq(901);
		checkPointInfo.setGroupType((byte) 4);
		checkPointList.add(checkPointInfo);

		questionInfo
				.setConnectionString("jdbc:mysql://localhost:3306/saiten_transaction1");
		questionInfo.setManualDocument("manual_1_09.pdf");
		questionInfo.setQuestionFileName("detail_1_09.pdf");
		questionInfo.setQuestionNum((short) 9);
		questionInfo.setQuestionSeq(9);
		questionInfo.setSide("98");
		questionInfo.setSubjectCode("1");
		questionInfo.setSubjectShortName("小国A-9");
		questionInfo.setMenuId(WebAppConst.STATE_TRAN_MENU_ID);

		ScoreInputInfo scoreInputInfo = new ScoreInputInfo();
		scoreInputInfo.setScoreHistoryInfo(null);
		scoreInputInfo.setScoreCurrentInfo(null);
		scoreInputInfo.setSubjectCode("1");
		scoreInputInfo.setResultCount(100);
		scoreInputInfo.setQuestionNum((short) 9);
		scoreInputInfo.setAnswerFormNum("");

		String scorerId = "ES00038";

		Integer startRecord = null;
		Integer endRecord = null;
		Boolean forceAndStateTransitionFlag = false;

		List result = scoreSearchServiceImpl.searchAnswerRecords(questionInfo,
				scoreInputInfo, scorerId, forceAndStateTransitionFlag,
				startRecord, endRecord);
		assertNotNull(result);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testSearchAnswerRecords_6() throws Exception {
		QuestionInfo questionInfo = new QuestionInfo();
		questionInfo.setAnswerScoreHistoryTable("tran_desc_score_history");
		questionInfo.setAnswerScoreTable("tran_obj_score");

		List<CheckPointInfo> checkPointList = new ArrayList<CheckPointInfo>();
		questionInfo.setCheckPointList(checkPointList);

		CheckPointInfo checkPointInfo = new CheckPointInfo();
		checkPointInfo.setCheckPoint((short) 0);
		checkPointInfo.setCheckPointDescription("無解答");
		checkPointInfo.setCheckPointGroupSeq(901);
		checkPointInfo.setGroupType((byte) 4);
		checkPointList.add(checkPointInfo);

		checkPointInfo = new CheckPointInfo();
		checkPointInfo.setCheckPoint((short) 1);
		checkPointInfo.setCheckPointDescription("１と解答しているもの");
		checkPointInfo.setCheckPointGroupSeq(901);
		checkPointInfo.setGroupType((byte) 4);
		checkPointList.add(checkPointInfo);

		checkPointInfo = new CheckPointInfo();
		checkPointInfo.setCheckPoint((short) 2);
		checkPointInfo.setCheckPointDescription("２と解答しているもの");
		checkPointInfo.setCheckPointGroupSeq(901);
		checkPointInfo.setGroupType((byte) 4);
		checkPointList.add(checkPointInfo);

		checkPointInfo = new CheckPointInfo();
		checkPointInfo.setCheckPoint((short) 3);
		checkPointInfo.setCheckPointDescription("３と解答しているもの");
		checkPointInfo.setCheckPointGroupSeq(901);
		checkPointInfo.setGroupType((byte) 4);
		checkPointList.add(checkPointInfo);

		checkPointInfo = new CheckPointInfo();
		checkPointInfo.setCheckPoint((short) 4);
		checkPointInfo.setCheckPointDescription("４と解答しているもの");
		checkPointInfo.setCheckPointGroupSeq(901);
		checkPointInfo.setGroupType((byte) 4);
		checkPointList.add(checkPointInfo);

		checkPointInfo = new CheckPointInfo();
		checkPointInfo.setCheckPoint((short) 24);
		checkPointInfo.setCheckPointDescription("上記以外の解答");
		checkPointInfo.setCheckPointGroupSeq(901);
		checkPointInfo.setGroupType((byte) 4);
		checkPointList.add(checkPointInfo);

		questionInfo
				.setConnectionString("jdbc:mysql://localhost:3306/saiten_transaction1");
		questionInfo.setManualDocument("manual_1_09.pdf");
		questionInfo.setQuestionFileName("detail_1_09.pdf");
		questionInfo.setQuestionNum((short) 9);
		questionInfo.setQuestionSeq(9);
		questionInfo.setSide("98");
		questionInfo.setSubjectCode("1");
		questionInfo.setSubjectShortName("小国A-9");
		questionInfo.setMenuId(WebAppConst.STATE_TRAN_MENU_ID);

		ScoreInputInfo scoreInputInfo = new ScoreInputInfo();
		scoreInputInfo.setScoreHistoryInfo(null);
		scoreInputInfo.setScoreCurrentInfo(null);
		scoreInputInfo.setSubjectCode("1");
		scoreInputInfo.setResultCount(100);
		scoreInputInfo.setQuestionNum((short) 9);
		scoreInputInfo.setAnswerFormNum("6000833");

		String scorerId = "ES00038";

		Integer startRecord = null;
		Integer endRecord = null;
		Boolean forceAndStateTransitionFlag = false;
		List result = scoreSearchServiceImpl.searchAnswerRecords(questionInfo,
				scoreInputInfo, scorerId, forceAndStateTransitionFlag,
				startRecord, endRecord);
		List count = (List<Integer>) result;
		assertEquals("1", count.get(0));
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void testSearchAnswerRecords_7() throws Exception {
		QuestionInfo questionInfo = new QuestionInfo();
		questionInfo.setAnswerScoreHistoryTable("tran_desc_score_history");
		questionInfo.setAnswerScoreTable("tran_obj_score");

		List<CheckPointInfo> checkPointList = new ArrayList<CheckPointInfo>();
		questionInfo.setCheckPointList(checkPointList);

		CheckPointInfo checkPointInfo = new CheckPointInfo();
		checkPointInfo.setCheckPoint((short) 0);
		checkPointInfo.setCheckPointDescription("無解答");
		checkPointInfo.setCheckPointGroupSeq(901);
		checkPointInfo.setGroupType((byte) 4);
		checkPointList.add(checkPointInfo);

		checkPointInfo = new CheckPointInfo();
		checkPointInfo.setCheckPoint((short) 1);
		checkPointInfo.setCheckPointDescription("１と解答しているもの");
		checkPointInfo.setCheckPointGroupSeq(901);
		checkPointInfo.setGroupType((byte) 4);
		checkPointList.add(checkPointInfo);

		checkPointInfo = new CheckPointInfo();
		checkPointInfo.setCheckPoint((short) 2);
		checkPointInfo.setCheckPointDescription("２と解答しているもの");
		checkPointInfo.setCheckPointGroupSeq(901);
		checkPointInfo.setGroupType((byte) 4);
		checkPointList.add(checkPointInfo);

		checkPointInfo = new CheckPointInfo();
		checkPointInfo.setCheckPoint((short) 3);
		checkPointInfo.setCheckPointDescription("３と解答しているもの");
		checkPointInfo.setCheckPointGroupSeq(901);
		checkPointInfo.setGroupType((byte) 4);
		checkPointList.add(checkPointInfo);

		checkPointInfo = new CheckPointInfo();
		checkPointInfo.setCheckPoint((short) 4);
		checkPointInfo.setCheckPointDescription("４と解答しているもの");
		checkPointInfo.setCheckPointGroupSeq(901);
		checkPointInfo.setGroupType((byte) 4);
		checkPointList.add(checkPointInfo);

		checkPointInfo = new CheckPointInfo();
		checkPointInfo.setCheckPoint((short) 24);
		checkPointInfo.setCheckPointDescription("上記以外の解答");
		checkPointInfo.setCheckPointGroupSeq(901);
		checkPointInfo.setGroupType((byte) 4);
		checkPointList.add(checkPointInfo);

		questionInfo
				.setConnectionString("jdbc:mysql://localhost:3306/saiten_transaction1");
		questionInfo.setManualDocument("manual_1_09.pdf");
		questionInfo.setQuestionFileName("detail_1_09.pdf");
		questionInfo.setQuestionNum((short) 9);
		questionInfo.setQuestionSeq(21);
		questionInfo.setSide("98");
		questionInfo.setSubjectCode("1");
		questionInfo.setSubjectShortName("小国A-9");
		questionInfo.setMenuId(WebAppConst.STATE_TRAN_MENU_ID);

		ScoreInputInfo scoreInputInfo = new ScoreInputInfo();
		scoreInputInfo.setScoreHistoryInfo(null);
		scoreInputInfo.setScoreCurrentInfo(null);
		scoreInputInfo.setSubjectCode("1");
		scoreInputInfo.setResultCount(100);
		scoreInputInfo.setQuestionNum((short) 9);
		scoreInputInfo.setAnswerFormNum("");

		String scorerId = "ES00038";

		Integer startRecord = null;
		Integer endRecord = null;
		Boolean forceAndStateTransitionFlag = false;
		List result = scoreSearchServiceImpl.searchAnswerRecords(questionInfo,
				scoreInputInfo, scorerId, forceAndStateTransitionFlag,
				startRecord, endRecord);
		assertNull(result.get(0));
	}

	/**
	 * Perform pre-test initialization.
	 * 
	 * @throws Exception
	 *             if the initialization fails for some reason
	 * 
	 * @generatedBy CodePro at 4/2/13 5:12 PM
	 */
	@Before
	public void setUp() throws Exception {
		// add additional set up code here
	}

	/**
	 * Perform post-test clean-up.
	 * 
	 * 
	 * 
	 * @generatedBy CodePro at 4/2/13 5:12 PM
	 * @throws Exception
	 *             if the clean-up fails for some reason
	 */
	@After
	public void tearDown() throws Exception {
		// Add additional tear down code here
	}

	/**
	 * Launch the test.
	 * 
	 * @param args
	 *            the command line arguments
	 * 
	 * @generatedBy CodePro at 4/2/13 5:12 PM
	 */
	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(ScoreSearchServiceImplTest.class);
	}

	final ScoreSearchServiceImpl scoreSearchServiceImpl = new ScoreSearchServiceImpl() {
		public MstSamplingStateCondDAO getMstSamplingStateCondDAO() {
			return new MstSamplingStateCondDAOImpl() {
				public HibernateTemplate getHibernateTemplate() {
					return SaitenHibernateDAOSupportTest.getHibernateTemplate();
				}
			};
		}

		public MstGradeDAO getMstGradeDAO() {
			return new MstGradeDAOImpl() {
				public HibernateTemplate getHibernateTemplate() {
					return SaitenHibernateDAOSupportTest.getHibernateTemplate();
				}
			};
		}

		public MstSamplingEventCondDAO getMstSamplingEventCondDAO() {
			return new MstSamplingEventCondDAOImpl() {
				public HibernateTemplate getHibernateTemplate() {
					return SaitenHibernateDAOSupportTest.getHibernateTemplate();
				}
			};
		}

		public MstPendingCategoryDAO getMstPendingCategoryDAO() {
			return new MstPendingCategoryDAOImpl() {
				public HibernateTemplate getHibernateTemplate() {
					return SaitenHibernateDAOSupportTest.getHibernateTemplate();
				}
			};
		}

		public MstQuestionDAO getMstQuestionDAO() {
			return new MstQuestionDAOImpl() {
				public HibernateTemplate getHibernateTemplate() {
					return SaitenHibernateDAOSupportTest.getHibernateTemplate();
				}
			};
		}

		public MstSubjectDAO getMstSubjectDAO() {
			return new MstSubjectDAOImpl() {
				public HibernateTemplate getHibernateTemplate() {
					return SaitenHibernateDAOSupportTest.getHibernateTemplate();
				}
			};
		}

		public TranDescScoreHistoryDAO getTranDescScoreHistoryDAO() {
			return new TranDescScoreHistoryDAOImpl() {
				/*
				 * public HibernateTemplate getHibernateTemplate() { return
				 * SaitenHibernateDAOSupportTest.getHibernateTemplate(); }
				 */
				public HibernateTemplate getHibernateTemplate(
						String connectionString) {
					return SaitenHibernateDAOSupportTest
							.getHibernateTemplate(connectionString);
				}
			};
		}
	};
}