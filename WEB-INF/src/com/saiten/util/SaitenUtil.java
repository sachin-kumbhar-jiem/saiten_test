package com.saiten.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.TextProvider;
import com.saiten.bean.GradeNumKey;
import com.saiten.bean.SaitenConfig;
import com.saiten.bean.ScoringStateKey;
import com.saiten.exception.SaitenRuntimeException;
import com.saiten.info.HistoryInfo;
import com.saiten.info.MstScorerInfo;
import com.saiten.info.QuestionInfo;
import com.saiten.info.ScoreSamplingInfo;
import com.saiten.info.TranDescScoreInfo;
import com.saiten.model.MstGrade;
import com.saiten.model.MstGradeResult;
import com.saiten.model.MstGradeResultId;
import com.saiten.model.MstQuestion;
import com.saiten.model.MstSubject;
import com.saiten.model.TranDescScoreHistory;
import com.saiten.model.TranScorerSessionInfo;

import jersey.repackaged.com.google.common.collect.ImmutableMap;
import test.com.saiten.AllTests;

public class SaitenUtil {
	private static Logger log = Logger.getLogger(SaitenUtil.class);
	
	private static int minlen = 4;

	private static Set<String> duplicateWords = new TreeSet<String>();

	public static String getSubjectNameByQuestionSequence(int questionSequence) {
		LinkedHashMap<Integer, MstQuestion> mstQuestionMap = getSaitenConfigObject().getMstQuestionMap();
		MstQuestion mstQuestion = mstQuestionMap.get((Integer) questionSequence);
		if (mstQuestion != null) {
			MstSubject mstSubject = mstQuestion.getMstSubject();
			if (mstSubject != null) {
				return mstSubject.getSubjectName();
			}
		}
		return null;
	}

	public static Short getQuestionNumByQuestionSequence(int questionSequence) {
		LinkedHashMap<Integer, MstQuestion> mstQuestionMap = getSaitenConfigObject().getMstQuestionMap();
		MstQuestion mstQuestion = mstQuestionMap.get((Integer) questionSequence);
		if (mstQuestion != null) {
			return mstQuestion.getQuestionNum();
		}
		return null;
	}

	public static String getStateNameByScoringState(Short scoringState) {
		LinkedHashMap<Short, String> scoringStateMap = getSaitenConfigObject().getScoringStateMap();
		return scoringStateMap.get(scoringState);
	}

	public static Double getBitValueByGradeSequence(Integer gradeSeq, int questionSeq) {
		LinkedHashMap<GradeNumKey, MstGrade> gradeSequenceWiseMstGradeMap = getSaitenConfigObject()
				.getGradeSequenceWiseMstGradeMap();
		GradeNumKey gradeNumKey = new GradeNumKey();
		gradeNumKey.setGradeSeq(gradeSeq);
		gradeNumKey.setQuestionSeq(questionSeq);
		MstGrade mstGrade = gradeSequenceWiseMstGradeMap.get(gradeNumKey);
		if (mstGrade != null) {
			return mstGrade.getBitValue();
		}
		return null;
	}

	public static Integer getGradeNumByGradeSequence(Integer gradeSeq, int questionSeq) {
		LinkedHashMap<GradeNumKey, MstGrade> gradeSequenceWiseMstGradeMap = getSaitenConfigObject()
				.getGradeSequenceWiseMstGradeMap();
		GradeNumKey gradeNumKey = new GradeNumKey();
		gradeNumKey.setGradeSeq(gradeSeq);
		gradeNumKey.setQuestionSeq(questionSeq);
		MstGrade mstGrade = gradeSequenceWiseMstGradeMap.get(gradeNumKey);
		if (mstGrade != null) {
			MstGradeResult mstGradeResult = mstGrade.getMstGradeResult();
			if (mstGradeResult != null) {
				return mstGradeResult.getId().getGradeNum();
			}
		}
		return null;
	}

	public static Character getResultByGradeSequence(Integer gradeSeq, int questionSeq) {
		LinkedHashMap<GradeNumKey, MstGrade> gradeSequenceWiseMstGradeMap = getSaitenConfigObject()
				.getGradeSequenceWiseMstGradeMap();
		GradeNumKey gradeNumKey = new GradeNumKey();
		gradeNumKey.setGradeSeq(gradeSeq);
		gradeNumKey.setQuestionSeq(questionSeq);
		MstGrade mstGrade = gradeSequenceWiseMstGradeMap.get(gradeNumKey);
		if (mstGrade != null) {
			MstGradeResult mstGradeResult = mstGrade.getMstGradeResult();
			if (mstGradeResult != null) {
				return mstGradeResult.getResult();
			}
		}
		return null;
	}

	public static Short getPendingCategoryByPendingCategorySeq(Integer pendingCategorySeq) {
		LinkedHashMap<Integer, Short> pendingCategoryMap = getSaitenConfigObject().getPendingCategoryMap();
		return pendingCategoryMap.get(pendingCategorySeq);
	}

	public static Map<String, Object> getAjaxCallStatusCode(Map<String, Object> session) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		Map<String, Object> root = new LinkedHashMap<String, Object>();

		if (session.get("scorerInfo") == null) {
			root.put("statusCode", WebAppConst.AUTHENTICATION_ERROR_CODE);
		} else {
			root.put("statusCode", WebAppConst.AUTHENTICATION_SUCCESS_CODE);
		}

		result.put("result", root);

		return result;
	}

	public static Map<String, Object> validateQuestionNum(Map<String, Object> session, Short questionNum,
			String subjectCode) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		Map<String, Object> root = new LinkedHashMap<String, Object>();
		LinkedHashMap<Integer, MstQuestion> mstQuestionMap = null;

		MstScorerInfo sessionScorerInfo = (MstScorerInfo) session.get("scorerInfo");
		boolean isValid = false;
		if (sessionScorerInfo.getRoleId() == 4 || sessionScorerInfo.getRoleId() == 6) {
			mstQuestionMap = getMstQuestionMap();
			;
			// root.put("isQuestionNumValid", WebAppConst.QUESTION_NUM_VALID);
		} else {
			/*
			 * LinkedHashMap<String, LinkedHashMap<Integer, MstQuestion>>
			 * mstScorerQuestionMap = SaitenUtil
			 * .getSaitenConfigObject().getMstScorerQuestionMap();
			 */
			/*
			 * LinkedHashMap<String, LinkedHashMap<Integer, MstQuestion>>
			 * mstScorerQuestionMap = (LinkedHashMap<String,
			 * LinkedHashMap<Integer, MstQuestion>>) SaitenUtil
			 * .getSession().get("mstScorerQuestionMap"); mstQuestionMap =
			 * mstScorerQuestionMap.get(sessionScorerInfo .getScorerId());
			 */
			mstQuestionMap = getMstQuestionMapByScorerId(sessionScorerInfo.getScorerId());
		}

		for (MstQuestion mstQuestion : mstQuestionMap.values()) {
			if ((mstQuestion.getQuestionNum().equals(questionNum))
					&& (mstQuestion.getMstSubject().getSubjectCode().equals(subjectCode))) {
				root.put("isQuestionNumValid", WebAppConst.QUESTION_NUM_VALID);
				isValid = true;
				break;
			}
		}
		if (!isValid) {
			root.put("isQuestionNumValid", WebAppConst.QUESTION_NUM_INVALID);
		}

		result.put("result", root);

		return result;
	}

	public static List<Integer> getGradeSeqListByQuestionSeqAndGradeNum(int questionSeq, int gradeNum) {
		List<Integer> gradeSeqList = new ArrayList<Integer>();

		LinkedHashMap<Integer, MstQuestion> mstQuestionMap = getSaitenConfigObject().getMstQuestionMap();

		Set<MstGrade> mstGradeSet = mstQuestionMap.get(questionSeq).getMstGrades();

		for (MstGrade mstGrade : mstGradeSet) {
			MstGradeResultId mstGradeResultIdObj = mstGrade.getMstGradeResult().getId();
			if ((mstGradeResultIdObj.getQuestionSeq() == questionSeq)
					&& (mstGradeResultIdObj.getGradeNum() == gradeNum)) {
				gradeSeqList.add(mstGrade.getGradeSeq());
			}
		}

		return gradeSeqList;
	}

	public static Map<String, String> getCalendarYears() {

		Map<String, String> yearMap = new LinkedHashMap<String, String>();

		Calendar cal = Calendar.getInstance();
		Integer year = cal.get(Calendar.YEAR);

		for (int i = (year - 1); i <= (year + 1); i++) {
			yearMap.put(Integer.toString(i), Integer.toString(i));
		}
		return yearMap;
	}

	public static Map<String, String> getCalendarMonths() {

		Map<String, String> monthMap = new LinkedHashMap<String, String>();
		for (int i = WebAppConst.CALENDAR_FIRST_MONTH; i <= WebAppConst.CALENDAR_LAST_MONTH; i++) {
			monthMap.put(String.valueOf(i), String.format("%02d", i));
		}
		return monthMap;
	}

	public static Map<String, String> getCalendardays() {

		Map<String, String> daysMap = new LinkedHashMap<String, String>();
		for (int i = WebAppConst.CALENDAR_FIRST_DAY; i <= WebAppConst.CALENDAR_LAST_DAY; i++) {
			daysMap.put(String.valueOf(i), String.format("%02d", i));
		}
		return daysMap;
	}

	public static Map<String, String> getClockHours() {

		Map<String, String> hoursMap = new LinkedHashMap<String, String>();
		for (int i = WebAppConst.CLOCK_FIRST_HOUR; i <= WebAppConst.CLOCK_LAST_HOUR; i++) {
			hoursMap.put(String.valueOf(i), String.format("%02d", i));
		}
		return hoursMap;
	}

	public static Map<String, String> getClockMinutes() {

		Map<String, String> minutesMap = new LinkedHashMap<String, String>();
		for (int i = WebAppConst.CLOCK_FIRST_MINUTE; i <= WebAppConst.CLOCK_LAST_MINUTE; i++) {
			minutesMap.put(String.valueOf(i), String.format("%02d", i));
		}
		return minutesMap;
	}

	/**
	 * @param noDbUpdate
	 * @return List<Short>
	 */
	public static List<Short> buildScoringStateList(char noDbUpdate) {
		ScoringStateKey scoringStateKey = (ScoringStateKey) ContextLoader.getCurrentWebApplicationContext()
				.getBean("scoringStateKey");

		QuestionInfo questionInfo = (QuestionInfo) (ActionContext.getContext().getSession()).get("questionInfo");
		scoringStateKey.setMenuId(questionInfo.getMenuId());

		scoringStateKey.setNoDbUpdate(noDbUpdate);

		// Get historyScoringStatesMap from saitenConfigObject
		LinkedHashMap<ScoringStateKey, List<Short>> historyScoringStatesMap = getSaitenConfigObject()
				.getHistoryScoringStatesMap();

		return historyScoringStatesMap.get(scoringStateKey);
	}

	public static Date getSaitenFormatDate(Date date) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return dateFormat.parse(dateFormat.format(date));
	}

	public static void clearSessionInfo() {
		Map<String, Object> session = ActionContext.getContext().getSession();

		session.remove("questionInfo");
		session.remove("tranDescScoreInfo");
		session.remove("tranDescScoreInfoCopy");
		session.remove("gradeInfo");
		session.remove("historyScreenFlag");
		session.remove("selectedCheckPointList");
		session.remove("count");
		session.remove("gradeMap");
		session.remove("questionList");
		session.remove("gradeNumber");
		session.remove("pendingCategory");
		session.remove("approveOrDeny");
		session.remove("pendingCategoryMap");
		session.remove("firstTimeSelectedCheckPointList");
		session.remove("secondTimeSelectedCheckPointList");
		session.remove("answerRecordsList");
		session.remove("scoreInputInfo");
		session.remove("recordNumber");
		session.remove("historyRecordCount");
		session.remove("questionInfoMap");
		session.remove("dbSpecificQuestionSeqMap");
		session.remove("bookmarkInfoList");
		session.remove("historyInfoList");
		session.remove("answerFormNum");
		session.remove("scoreSamplingInfoList");
		session.remove("selectAllFlag");
		session.remove("specialScoreInputInfo");
		session.remove("answerFlag");
		session.remove("updatedCount");
		session.remove("qcAnswerSeqList");
		session.remove("selectedMarkValue");
		session.remove("selectedQuestionMarkValueMap");
		session.remove("dailyStatusSearchInfo");
		session.remove("lookAfterwardsInfo");
		session.remove("loadQcListFlag");
		session.remove("questionType");
		session.remove("questionTypeAndEvalMap");
		session.remove("dailyStatusReportPageSize");
		session.remove("isQcRecord");
	}

	@SuppressWarnings("unchecked")
	public static Map<String, String> getConfigMap() {
		ServletContext servletContext = AllTests.getServletContext();

		if (servletContext != null) {
			return (Map<String, String>) servletContext.getAttribute("configMap");
		} else {
			return (Map<String, String>) ServletActionContext.getServletContext().getAttribute("configMap");
		}
	}

	public static SaitenConfig getSaitenConfigObject() {
		ServletContext servletContext = AllTests.getServletContext();

		if (servletContext != null) {
			return (SaitenConfig) servletContext.getAttribute("saitenConfigObject");
		} else {
			return (SaitenConfig) ServletActionContext.getServletContext().getAttribute("saitenConfigObject");
		}
	}

	public static ApplicationContext getApplicationContext() {
		ApplicationContext applicationContext = ContextLoader.getCurrentWebApplicationContext();
		if (applicationContext != null) {
			return applicationContext;
		} else {
			ServletContext servletContext = AllTests.getServletContext();
			applicationContext = (ApplicationContext) servletContext.getAttribute("applicationContext");
		}
		return applicationContext;
	}

	public static Integer[] stringArrayToIntegerArray(String[] stringArray) {
		Integer[] intArray = new Integer[stringArray.length];
		for (int i = 0; i < stringArray.length; i++) {
			intArray[i] = Integer.valueOf(stringArray[i]);
		}
		return intArray;
	}

	@SuppressWarnings("unchecked")
	public static void updateHistoryInfoList(TranDescScoreHistory tranDescScoreHistory, String connectionString,
			Integer historySeq) {

		Map<String, Object> session = ActionContext.getContext().getSession();
		List<HistoryInfo> sessionHistoryInfoList = (List<HistoryInfo>) session.get("historyInfoList");
		HistoryInfo historyInfoObj = new HistoryInfo();
		historyInfoObj.setHistorySequence(tranDescScoreHistory.getHistorySeq());
		historyInfoObj.setConnectionString(connectionString);
		HistoryInfo updateHistoryInfo = null;

		if (historySeq != null && sessionHistoryInfoList.contains(historyInfoObj)) {
			updateHistoryInfo = sessionHistoryInfoList.remove((int) sessionHistoryInfoList.indexOf(historyInfoObj));
		} else {
			updateHistoryInfo = new HistoryInfo();
		}

		updateHistoryInfo.copy(tranDescScoreHistory);
		sessionHistoryInfoList.add(0, updateHistoryInfo);

		session.put("historyInfoList", sessionHistoryInfoList);

	}

	public static void updateSpecialScoringMap(/*
												 * String subjectCode, String
												 * answerFormNumber
												 */) {
		/*
		 * Map<SpecialScoringKey, String> specialScoringMap = ((SaitenConfig)
		 * ContextLoader .getCurrentWebApplicationContext().getServletContext()
		 * .getAttribute("saitenConfigObject")).getSpecialScoringMap();
		 */

		/*
		 * SpecialScoringKey specialScoringKey = new SpecialScoringKey();
		 * specialScoringKey.setAnswerFormNum(answerFormNumber);
		 * specialScoringKey.setSubjectCode(subjectCode);
		 * 
		 * specialScoringMap.remove(specialScoringKey);
		 */

		// clear answerFormNum & SubjectCode.
		Map<String, Object> session = ActionContext.getContext().getSession();
		ApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
		TranScorerSessionInfo tranScorerSessionInfo = (TranScorerSessionInfo) session.get("tranScorerSessionInfo");
		QuestionInfo questionInfo = (QuestionInfo) session.get("questionInfo");
		if (questionInfo != null) {
			tranScorerSessionInfo.setQuestionSeq(null);
		}
		tranScorerSessionInfo.setAnswerFormNum(null);
		tranScorerSessionInfo.setSubjectCode(null);
		// clear answerformnum & subjectcode & question_seq from
		// tran_scorer_session_info
		// table.
		((SaitenMasterUtil) ctx.getBean("saitenMasterUtil")).updateUserSessionInfo(tranScorerSessionInfo);
		// put updated tranScorerSessionInfo into session.
		session.put("tranScorerSessionInfo", tranScorerSessionInfo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saiten.service.ScoreService#getSelectedCheckPointList(java.lang.
	 * Integer )
	 */
	public static List<Short> getSelectedCheckPointList(Double bitValue) {
		List<Short> selectedCheckPointList = null;
		try {
			if (bitValue > 0) {
				selectedCheckPointList = new ArrayList<Short>();
				String binaryString = Integer.toBinaryString(bitValue.intValue());
				for (int i = binaryString.length() - 1, j = 0; i > -1; i--, j++) {
					if (Integer.valueOf(String.valueOf(binaryString.charAt(i))) == 1)
						selectedCheckPointList.add((short) j);
				}
			}
			return selectedCheckPointList;
		} catch (Exception e) {
			throw new SaitenRuntimeException(ErrorCode.HISTORY_SCORE_SERVICE_EXCEPTION, e);
		}
	}

	public static void copyScoreSamplingInfoToTranDescScoreInfo(ScoreSamplingInfo scoreSamplingInfo,
			TranDescScoreInfo tranDescScoreInfo) {
		tranDescScoreInfo.setAnswerFormNumber(scoreSamplingInfo.getAnswerNumber());
		tranDescScoreInfo.setAnswerInfo(scoreSamplingInfo.getAnswerInfo());
		tranDescScoreInfo.setGradeSeq(scoreSamplingInfo.getGradeSeq());
		tranDescScoreInfo.setScoringState(scoreSamplingInfo.getScoringState());
		tranDescScoreInfo.setImageFileName(scoreSamplingInfo.getImageFileName());
		tranDescScoreInfo.setMarkValueList(scoreSamplingInfo.getMarkValueList());
	}

	/**
	 * @param scorerId
	 * @param subjectCode
	 * @param answerFormNum
	 * @return boolean
	 */
	public static boolean isEvaluationInProgress(String scorerId, String subjectCode, String answerFormNum,
			String menuId) {
		boolean decline = false;
		/*
		 * Map<SpecialScoringKey, String> specialScoringMap = ((SaitenConfig)
		 * ServletActionContext
		 * .getServletContext().getAttribute("saitenConfigObject"))
		 * .getSpecialScoringMap();
		 * 
		 * SpecialScoringKey specialScoringKey = new SpecialScoringKey();
		 * specialScoringKey.setAnswerFormNum(answerFormNum);
		 * specialScoringKey.setSubjectCode(subjectCode);
		 */

		Map<String, Object> session = ActionContext.getContext().getSession();
		ApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
		Long count = ((SaitenMasterUtil) ctx.getBean("saitenMasterUtil"))
				.getUsersByAnswerFormNumAndSubjectCode(answerFormNum, subjectCode);

		synchronized (SaitenUtil.class) {
			/*
			 * if (!specialScoringMap.containsKey(specialScoringKey)) { if
			 * ((menuId == null) || ((menuId != null) && (!menuId
			 * .equals(WebAppConst.FORCED_MENU_ID)))) {
			 * specialScoringMap.put(specialScoringKey, scorerId); }
			 * System.out.println(">>>>>>>>>>>> specialScoringMap = " +
			 * specialScoringMap); decline = false; } else { decline = true; }
			 */

			if (!(count > 0)) {
				if ((menuId == null) || ((menuId != null) && (!menuId.equals(WebAppConst.FORCED_MENU_ID)))) {
					TranScorerSessionInfo tranScorerSessionInfo = (TranScorerSessionInfo) session
							.get("tranScorerSessionInfo");
					if (tranScorerSessionInfo == null) {
						tranScorerSessionInfo = new TranScorerSessionInfo();
						tranScorerSessionInfo.setScorerId(scorerId);
						tranScorerSessionInfo.setCreateDate(new Date());
					}
					tranScorerSessionInfo.setAnswerFormNum(answerFormNum);
					tranScorerSessionInfo.setSubjectCode(subjectCode);
					tranScorerSessionInfo.setUpdateDate(new Date());
					// set answerFormNum & subjectCode in
					// tran_scorer_session_info table for current user.
					((SaitenMasterUtil) ctx.getBean("saitenMasterUtil")).updateUserSessionInfo(tranScorerSessionInfo);
					session.put("tranScorerSessionInfo", tranScorerSessionInfo);
				}
				/*
				 * System.out.println(">>>>>>>>>>>> specialScoringMap = " +
				 * specialScoringMap);
				 */
				decline = false;
			} else {
				decline = true;
			}

		}

		return decline;
	}

	public static void unlockHistoryAnswer(TranDescScoreInfo tranDescScoreInfo) {
		Map<String, Object> session = ActionContext.getContext().getSession();
		MstScorerInfo mstScorerInfo = (MstScorerInfo) session.get("scorerInfo");
		QuestionInfo questionInfo = (QuestionInfo) session.get("questionInfo");
		String lockBy = mstScorerInfo.getScorerId();
		String connectionString = questionInfo.getConnectionString();
		int questionSeq = questionInfo.getQuestionSeq();
		if (connectionString != null && questionSeq != WebAppConst.ZERO) {
			// unlock the all locked history answer by logged in scorer
			UnlockAnswerUtil.unlockAnswer(questionSeq, lockBy, connectionString,
					tranDescScoreInfo.getAnswerInfo().getAnswerSeq());
		}
	}

	public static String getLoggedinScorerId() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		MstScorerInfo mstScorerInfo = (MstScorerInfo) session.get("scorerInfo");
		return mstScorerInfo.getScorerId();
	}

	public static Map<String, Object> getSession() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		return session;
	}

	@SuppressWarnings("unchecked")
	public static LinkedHashMap<Integer, MstQuestion> getMstQuestionMapByScorerId(String scorerId) {
		LinkedHashMap<Integer, MstQuestion> mstQuestionMap = new LinkedHashMap<Integer, MstQuestion>();
		Map<String, List<Integer>> mstScorerQuestionMap = (Map<String, List<Integer>>) getSession()
				.get("mstScorerQuestionMap");
		if (mstScorerQuestionMap != null) {
			List<Integer> questionSeqList = mstScorerQuestionMap.get(scorerId);
			LinkedHashMap<Integer, MstQuestion> tempMstQuestionMap = getSaitenConfigObject().getMstQuestionMap();
			if ((questionSeqList != null) && (!questionSeqList.isEmpty())) {
				for (Integer questionSeq : questionSeqList) {
					mstQuestionMap.put(questionSeq, tempMstQuestionMap.get(questionSeq));
				}
			}
		}
		return mstQuestionMap;
	}

	public static LinkedHashMap<Integer, MstQuestion> getMstQuestionMap() {
		return getSaitenConfigObject().getMstQuestionMap();
	}

	public static String getPropertyFromPropertyFile(String propertyFileName, String key) {
		return ResourceBundle.getBundle(propertyFileName).getString(key);
	}

	public static String getTestSetCodeByImageFileName(String imageFileName) {
		String testsetCode = null;
		if (imageFileName != null) {
			testsetCode = imageFileName.substring(0, imageFileName.length() - 14);
			System.out.println("testsetcode: " + testsetCode);
		}
		return testsetCode;
	}

	public static String getAnswerDataText(String imageFileName, QuestionInfo questionInfo) throws IOException {
		StringBuilder strb = new StringBuilder();
		try {
			Properties saitenApplicationProperties = (Properties) ContextLoader.getCurrentWebApplicationContext()
					.getBean("saitenApplicationProperties");
			String textFilePath = saitenApplicationProperties.getProperty(WebAppConst.ANSWER_IMAGE_URL) + "/"
					+ questionInfo.getQuestionSeq() + WebAppConst.HYPHEN + questionInfo.getSubjectCode()
					+ WebAppConst.HYPHEN + questionInfo.getQuestionNum() + "/" + imageFileName;
			/*
			 * String textFilePath =
			 * "E:/saiten/processed-files/10001-A-1/"+imageFileName;
			 */

			URL url = new URL(textFilePath);
			// Scanner s = new Scanner(url.openStream());

			// Get scanner instance
			Scanner scanner = new Scanner(url.openStream(), WebAppConst.SHIFT_JIS);

			// Set the delimiter used in file
			scanner.useDelimiter(",");

			// Get all tokens and store them in some data structure
			// I am just printing them

			while (scanner.hasNext()) {
				strb.append(scanner.next() + ",");
				// System.out.print(scanner.next()+",");

			}
			if (strb.length() > 0) {
				strb.deleteCharAt(strb.length() - 1);
			} else {
				strb.append("WARNING : " + imageFileName + " : " + "File Is Empty");
				log.error(" ============== File is Empty Error ================ : " + imageFileName);
			}
			// Do not forget to close the scanner
			scanner.close();

		} catch (FileNotFoundException e) {
			String fileNotFoundText = ((TextProvider) ActionContext.getContext().getActionInvocation().getAction())
					.getText("file.not.found");
			throw new SaitenRuntimeException(
					ErrorCode.WRITTING_FILE_NOT_FOUND_EXCEPTION + ": " + fileNotFoundText + " (" + imageFileName + ").",
					e);
		}
		return strb.toString();
	}

	public static String getFacilitatorIdByImageFileName(String imageFileName) {

		String facilitatorId = null;
		if (imageFileName != null) {
			if (imageFileName.endsWith(".mp3") || imageFileName.endsWith(".MP3")) {
				facilitatorId = imageFileName.substring(2, 7);
				System.out.println("Facilitator ID::" + facilitatorId);
			}
		}
		return facilitatorId;
	}

	public static Map<String, String> getPunchTextMap(List<String> punchTextList) {
		Map<String, String> punchTextMap = new LinkedHashMap<String, String>();
		int i = 0;
		for (String str : punchTextList) {
			punchTextMap.put(Integer.toString(i), str);
			i++;
		}
		return punchTextMap;
	}

	public static Short getDenyCategoryByDenyCategorySeq(Integer denyCategorySeq) {
		LinkedHashMap<Integer, Short> CategoryMap = getSaitenConfigObject().getDenyCategoryMap();
		return CategoryMap.get(denyCategorySeq);
	}

	public static String getCommonDbConnectionString() {
		Properties saitenDbProperties = (Properties) ContextLoader.getCurrentWebApplicationContext()
				.getBean("saitenDbProperties");
		return saitenDbProperties.getProperty(WebAppConst.SAITEN_COMMONDB_URL);
	}

	public static List<String> getTempDbConnectionString() {
		List<String> tempDbConnectionList = null;
		Properties saitenDbProperties = (Properties) ContextLoader.getCurrentWebApplicationContext()
				.getBean("saitenDbProperties");
		tempDbConnectionList = new ArrayList<String>();
		for (Object key : saitenDbProperties.keySet()) {
			if (key.toString().contains(WebAppConst.TEMP_DB) && key.toString().contains("url")) {
				tempDbConnectionList.add(saitenDbProperties.getProperty(key.toString()));
			}
		}
		return tempDbConnectionList;
	}

	@SuppressWarnings("rawtypes")
	public static Map latestScoringStatesMap() {

		final Map<Integer, String> statesMap = ImmutableMap.<Integer, String>builder().put(21, "021(パンチ待)")
				.put(31, "031(読込)").put(51, "051(バ採待)").put(52, "052(出力)").put(53, "053(バ採仮)").put(59, "059(バ採こ)")
				.put(61, "061(バ採済)").put(121, "121(1採待)").put(122, "122(1採仮)").put(123, "123(1保仮)").put(131, "131(2採待)")
				.put(132, "132(2採仮)").put(133, "133(2保仮)").put(141, "141(確認待)").put(144, "144(確承仮)")
				.put(145, "145(確否仮)").put(151, "151(検知待)").put(154, "154(検承仮)").put(155, "155(検否仮)").put(161, "161(保待)")
				.put(162, "162(保採仮)").put(163, "163(保保仮)").put(171, "171(不採待)").put(172, "172(不採仮)")
				.put(173, "173(不保仮)").put(181, "181(はみ待)").put(182, "182(は採仮)").put(191, "191(否採待)")
				.put(192, "192(否採仮)").put(193, "193(否保仮)").put(211, "211(類こ待)").put(212, "212(類採仮)")
				.put(221, "221(採サン仮)").put(222, "222(保サン仮)").put(241, "241(強採仮)").put(242, "242(強保仮)")
				.put(253, "253(拡採待)").put(254, "254(拡採仮)").put(255, "255(OMR採待)").put(256, "256(OMR採仮)")
				.put(257, "257(外採待)").put(500, "500(確定)").put(501, "501(確定バッチ)").put(183, "183(は保仮)")
				.put(213, "213(類保仮)").put(251, "251(点字採待)").put(252, "252(点字採仮)").put(258, "258(外採仮)")
				.put(262, "262(品採仮)").put(263, "263(品保仮)").build();

		return statesMap;
	}

	@SuppressWarnings("rawtypes")
	public static Map latestScoringStatesMapForWg() {

		final Map<Integer, String> statesMap = ImmutableMap.<Integer, String>builder().put(21, "パンチ待(21)")
				.put(31, "読込(31)").put(51, "バ採待(51)").put(52, "52(出力)").put(53, "バ採仮(53)").put(59, "バ採こ(59)")
				.put(61, "バ採済(61)").put(121, "1採待(121)").put(122, "1採仮(122)").put(123, "1保仮(123)").put(131, "2採待(131)")
				.put(132, "2採仮(132)").put(133, "2保仮(133)").put(141, "確認待(141)").put(144, "確承仮(144)")
				.put(145, "確否仮(145)").put(151, "検知待(151)").put(154, "検承仮(154)").put(155, "検否仮(155)").put(161, "保待(161)")
				.put(162, "保採仮(162)").put(163, "保保仮(163)").put(171, "不採待(171)").put(172, "不採仮(172)")
				.put(173, "不保仮(173)").put(181, "はみ待(181)").put(182, "は採仮(182)").put(191, "否採待(191)")
				.put(192, "否採仮(192)").put(193, "否保仮(193)").put(211, "類こ待(211)").put(212, "類採仮(212)")
				.put(221, "採サン仮(221)").put(222, "保サン仮(222)").put(241, "強採仮(241)").put(242, "強保仮(242)")
				.put(253, "拡採待(253)").put(254, "拡採仮(254)").put(255, "OMR採待(255)").put(256, "OMR採仮(256)")
				.put(257, "外採待(257)").put(500, "確定(500)").put(501, "確定バッチ(501)").put(183, "は保仮(183)")
				.put(213, "類保仮(213)").put(251, "点字採待(251)").put(252, "点字採仮(252)").put(258, "外採仮(258)")
				.put(262, "品採仮(262)").put(263, "品保仮(263)").build();

		return statesMap;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map<String, String> getGradeMapByQuestionSeq(int questionSeq, String gradeText) {

		ApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
		SaitenConfig saitenConfig = (SaitenConfig) ctx.getBean("saitenConfig");

		Map<Integer, Map> gradeDetailsMap = saitenConfig.getMstGradeDetailsMap();
		Map<Integer, List> gradeNumAndBitValueMap = (Map) gradeDetailsMap.get(questionSeq);

		List<Double> bitValueList = new ArrayList<Double>();
		List<Short> checkPointCombinationList = new ArrayList<Short>();
		Map<String, String> gradeTempMap = new LinkedHashMap<String, String>();

		for (Integer key : gradeNumAndBitValueMap.keySet()) {
			String grade = Integer.toString(key);

			bitValueList = gradeNumAndBitValueMap.get(key);
			String bitvalue = null;

			for (int i = 0; i < bitValueList.size(); i++) {

				String checkpointString = null;
				String gradesWithCheckpoints = null;
				String checkpoints = "";

				Double d = (Double) bitValueList.get(i);
				checkPointCombinationList = SaitenUtil.getSelectedCheckPointList(d);

				checkpointString = checkPointCombinationList.toString().replaceAll("[\\s\\[\\]]", "");
				checkpointString = "{ " + checkpointString + " }" + WebAppConst.SINGLE_SPACE;
				bitvalue = Double.toString(bitValueList.get(i));
				checkpoints = checkpoints + checkpointString;

				if (gradeText != null) {
					gradesWithCheckpoints = gradeText.concat(
							grade + WebAppConst.SINGLE_SPACE + WebAppConst.OPENING_BRACKET + WebAppConst.SINGLE_SPACE
									+ checkpoints + WebAppConst.SINGLE_SPACE + WebAppConst.CLOSING_BRACKET);
				}
				gradeTempMap.put(key + ":" + bitvalue, gradesWithCheckpoints);
			}
		}
		return gradeTempMap;
	}
	
	public static Set<String> consecutiveCharacterMatch(String question, String answer) {

		String searchString = "";
		
		for (int i = 0; i < answer.length(); i++) {
			searchString += answer.charAt(i);
			if (!question.toLowerCase().contains(searchString.toLowerCase())) {

				if(!question.toLowerCase().contains(searchString)) {
					
					String previousSearchString = searchString.substring(0, searchString.length() - 1);
					if(previousSearchString.length() >= minlen) {
						duplicateWords.add(previousSearchString);
					}
					searchString = answer.charAt(i) + "";
				}
			}
		}
		
		if(searchString.length() >= minlen) {
			duplicateWords.add(searchString);
		}
		
		return duplicateWords;
	}
}
