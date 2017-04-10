package com.saiten.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.web.context.ContextLoader;

import com.opensymphony.xwork2.ActionSupport;
import com.saiten.bean.SaitenConfig;
import com.saiten.exception.SaitenRuntimeException;
import com.saiten.info.MstScorerInfo;
import com.saiten.info.QuestionInfo;
import com.saiten.model.MstQuestion;
import com.saiten.service.QuestionSelectionService;
import com.saiten.util.ErrorCode;
import com.saiten.util.SaitenUtil;
import com.saiten.util.WebAppConst;

/**
 * @author sachin
 * @version 1.0
 * @created 06-Dec-2012 2:32:49 PM
 */
public class QuestionSelectionAction extends ActionSupport implements SessionAware {

	private static Logger log = Logger.getLogger(QuestionSelectionAction.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private QuestionInfo questionInfo;
	private QuestionSelectionService questionSelectionService;
	private String selectedMenuId;
	private Integer gradeNum;
	private Short pendingCategory;
	private Map<String, Object> session;
	private Map<String, String> questionMap;
	private String questionList;
	private String subjectShortName;
	private Map<Short, String> selectedQuestionMarkValueMap;
	private Short selectedMarkValue;
	private String denyCategory;
	private List<String> manualList;
	private String selectedGradeNum;
	//private Double bitValue;

	@SuppressWarnings("unused")
	public String fetchDbInstanceInfo() {

		try {
			Date logActionStartTime = new Date();
			int questionSeq;
			// Integer gradeNo = null;
			session.put("denyCategory", denyCategory);
			QuestionInfo sessionQuestionInfo = (QuestionInfo) session.get("questionInfo");
			questionSeq = sessionQuestionInfo.getQuestionSeq();
			MstScorerInfo scorerInfo = ((MstScorerInfo) session.get("scorerInfo"));

			String[] array = new String[] {};
			if (selectedGradeNum != null) {
				array = selectedGradeNum.split(":");
				gradeNum = Integer.valueOf((String) array[0]);
				//bitValue = Double.valueOf((String) array[1]);
			}

			log.info(scorerInfo.getScorerId() + "-" + sessionQuestionInfo.getMenuId() + "-"
					+ "After Question,Grade,Pending Category,Mark Value Selection." + "-{ Question Sequence: "
					+ sessionQuestionInfo.getQuestionSeq() + ", Grade Num: " + gradeNum + ", Pending Category: "
					+ pendingCategory + ", Mark Value: " + selectedMarkValue + "}");
			// mark value selected if score type is 4(objective+screen).
			if ((session.get("selectedMarkValue") == null)
					&& (sessionQuestionInfo.getScoreType() == WebAppConst.SCORE_TYPE[3])) {
				session.put("selectedMarkValue", selectedMarkValue);
			}
			Date logGradeStartTime = null;
			Date logGradeEndTime = null;
			if (sessionQuestionInfo.getMenuId().equals(WebAppConst.CHECKING_MENU_ID)
					|| sessionQuestionInfo.getMenuId().equals(WebAppConst.INSPECTION_MENU_ID)
					|| sessionQuestionInfo.getMenuId().equals(WebAppConst.DENY_MENU_ID)) {
				logGradeStartTime = new Date();
				List<Integer> gradeSeqList = SaitenUtil.getGradeSeqListByQuestionSeqAndGradeNum(questionSeq, gradeNum);
				logGradeEndTime = new Date();
				if (gradeSeqList == null || gradeSeqList.isEmpty()) {
					this.addActionError(getText("error.noRecordAvailableForSelectedGrade"));
					return INPUT;
				}
			}

			List<Integer> questionSeqList = new ArrayList<Integer>();
			questionSeqList.add(questionSeq);

			// Fetch dbInstanceInfo and questionInfo corresponding to selected
			// questionSeq
			Date logQuestionInfoStartTime = new Date();
			questionInfo = questionSelectionService.fetchDbInstanceInfo(questionSeqList);
			Date logQuestionInfoEndTime = new Date();

			String scorerId = scorerInfo.getScorerId();

			// Build scoringStateList based on menuId and noDbUpdate value. e.g.
			// 122, 123
			Date logScoringStateListStartTime = new Date();
			List<Short> scoringStateList = SaitenUtil.buildScoringStateList(scorerInfo.getNoDbUpdate());
			Date logScoringStateListEndTime = new Date();

			selectedMenuId = ((QuestionInfo) session.get("questionInfo")).getMenuId();

			List<Integer> questionSequenceList = new ArrayList<Integer>();
			questionSequenceList.add(questionSeq);

			int qcRecordsCount = 0;
			int historyRecordCount = 0;
			Character questionType = questionInfo.getQuestionType();
			String menuId = sessionQuestionInfo.getMenuId();
			Date logQcHistoryCountStartTime = null;
			Date logQcHistoryCountEndTime = null;
			if ((menuId.equals(WebAppConst.FIRST_SCORING_MENU_ID) || menuId.equals(WebAppConst.SECOND_SCORING_MENU_ID))
			/*
			 * && (questionType == WebAppConst.SPEAKING_TYPE || questionType ==
			 * WebAppConst.WRITING_TYPE)
			 */) {
				logQcHistoryCountStartTime = new Date();
				qcRecordsCount = questionSelectionService.findQcHistoryRecordCount(scorerId, questionSequenceList,
						questionInfo.getConnectionString(), scoringStateList);
				logQcHistoryCountEndTime = new Date();
			}
			Date logHistoryRecordCountStartTime = new Date();
			historyRecordCount = questionSelectionService.findHistoryRecordCount(scorerId, questionSequenceList,
					questionInfo.getConnectionString(), scoringStateList);
			Date logHistoryRecordCountEndTime = new Date();
			// Fetch historyRecordCount for selected question
			questionInfo.setHistoryRecordCount(historyRecordCount + qcRecordsCount);
			questionInfo.setQuestionSeq(questionSeq);

			// Get checkPointList for selected question
			Date logCheckPointsStartTime = new Date();
			questionInfo.setCheckPointList(questionSelectionService.findCheckPoints(questionSeq));
			Date logCheckPointsEndTime = new Date();
			// Get pending categories for selected question
			Date logPendingCategoryStartTime = new Date();
			questionInfo.setPendingCategoryGroupMap(questionSelectionService.findPendingCategories(questionSeq));
			Date logPendingCategoryEndTime = new Date();
			questionInfo.setDenyCategoryGroupMap(questionSelectionService.findDenyCategories(questionSeq));
			// Update questionInfo with the data fetched.
			updateQuestionInfo();

			if ((menuId.equals(WebAppConst.FIRST_SCORING_MENU_ID) || menuId.equals(WebAppConst.SECOND_SCORING_MENU_ID))
			/*
			 * && (questionType == WebAppConst.SPEAKING_TYPE || questionType ==
			 * WebAppConst.WRITING_TYPE)
			 */) {
				session.put("loadQcListFlag", true);
			}
			Date logActionEndTime = new Date();
			String actionName = "showScoringPage";
			long total = logActionEndTime.getTime() - logActionStartTime.getTime();
			log.info(actionName + "-Total: " + total);
			if (logGradeStartTime != null && logGradeEndTime != null) {
				long gradeTime = logGradeEndTime.getTime() - logGradeStartTime.getTime();
				log.info(actionName + "-GetGradeSeqList: " + gradeTime);
			}
			long questionInfoTime = logQuestionInfoEndTime.getTime() - logQuestionInfoStartTime.getTime();
			log.info(actionName + "-GetQuestionInfo: " + questionInfoTime);
			long scoringStateListTime = logScoringStateListEndTime.getTime() - logScoringStateListStartTime.getTime();
			log.info(actionName + "-BuildScoringStateList: " + scoringStateListTime);
			if (logQcHistoryCountStartTime != null && logQcHistoryCountEndTime != null) {
				long QcHistoryCountTime = logQcHistoryCountEndTime.getTime() - logQcHistoryCountStartTime.getTime();
				log.info(actionName + "-GetQcHistoryCount: " + QcHistoryCountTime);
			}
			long historyCountTime = logHistoryRecordCountEndTime.getTime() - logHistoryRecordCountStartTime.getTime();
			log.info(actionName + "-GetHistoryCount: " + historyCountTime);
			long getCheckPointsTime = logCheckPointsEndTime.getTime() - logCheckPointsStartTime.getTime();
			log.info(actionName + "-GetCheckPoints: " + getCheckPointsTime);
			long getPendindCategoriesTime = logPendingCategoryEndTime.getTime() - logPendingCategoryStartTime.getTime();
			log.info(actionName + "-GetPendindCategories: " + getPendindCategoriesTime);
		} catch (SaitenRuntimeException we) {
			throw we;
		} catch (Exception e) {
			throw new SaitenRuntimeException(ErrorCode.QUESTION_SELECTION_ACTION_EXCEPTION, e);
		}

		return SUCCESS;
	}

	public String onLoad() {
		try {

			MstScorerInfo scorerInfo = ((MstScorerInfo) session.get("scorerInfo"));

			if (selectedMenuId.equals(WebAppConst.MANUAL_UPLOAD_MENU_ID)) {
				log.info(scorerInfo.getScorerId() + "-" + "Manual Upload Screen Onload. Selected Menu: "
						+ selectedMenuId);
			} else {
				log.info(
						scorerInfo.getScorerId() + "-" + "Question Selection Onload. Selected Menu: " + selectedMenuId);
			}

			String scorerId = scorerInfo.getScorerId();
			int roleId = scorerInfo.getRoleId();

			// Fetch questions for logged in scorer
			questionMap = questionSelectionService.findQuestionsByMenuIdAndScorerId(selectedMenuId, scorerId, roleId);
			if (questionMap.isEmpty()) {
				this.addActionError(getText(WebAppConst.ERROR_NO_QUESTIONS_ALLOCATED));
			}

			// Start building questionInfo with selected menuId
			buildQuestionInfo();

			if (selectedMenuId.equals(WebAppConst.MANUAL_UPLOAD_MENU_ID)) {
				// Build manual list for Manual upload screen
				manualList = new ArrayList<String>();
				manualList.add(getText(WebAppConst.MANUAL1));
				manualList.add(getText(WebAppConst.MANUAL2));

				session.put("manualList", manualList);
				session.put("manualUploadQuestionMap", questionMap);

				log.info(scorerInfo.getScorerId() + "-" + selectedMenuId + "-" + "Loaded Manual Upload Screen.");
			} else {
				log.info(scorerInfo.getScorerId() + "-" + selectedMenuId + "-" + "Loaded Question Selection Screen.");
			}
		} catch (SaitenRuntimeException we) {
			throw we;
		} catch (Exception e) {
			throw new SaitenRuntimeException(ErrorCode.QUESTION_SELECTION_ACTION_EXCEPTION, e);
		}

		return SUCCESS;
	}

	public String fetchScoringType() {
		// HttpServletRequest request = ServletActionContext.getRequest();
		try {
			// Fetch selected questionSeq
			Date logActionStartTime = new Date();
			String[] selectedQuestion = questionList.split(WebAppConst.COLON);
			int questionSeq = Integer.valueOf(selectedQuestion[0]);
			MstScorerInfo scorerInfo = ((MstScorerInfo) session.get("scorerInfo"));
			QuestionInfo sessionQuestionInfo = (QuestionInfo) session.get("questionInfo");
			log.info(scorerInfo.getScorerId() + "-" + sessionQuestionInfo.getMenuId() + "-"
					+ "Question Selection. Fetch Scoring Type." + "-{ Question Sequence: " + questionSeq + "}");
			List<Integer> questionSeqList = new ArrayList<Integer>();
			questionSeqList.add(questionSeq);

			// Fetch dbInstanceInfo and questionInfo corresponding to selected
			// questionSeq
			Date fetchDbInstanceInfoStartTime = new Date();
			questionInfo = questionSelectionService.fetchDbInstanceInfo(questionSeqList);
			Date fetchDbInstanceInfoEndTime = new Date();
			questionInfo.setQuestionSeq(questionSeq);
			questionInfo.setMenuId(sessionQuestionInfo.getMenuId());

			LinkedHashMap<Integer, MstQuestion> mstQuestionMap = new LinkedHashMap<Integer, MstQuestion>();
			mstQuestionMap = SaitenUtil.getSaitenConfigObject().getMstQuestionMap();
			MstQuestion mstQuestion = mstQuestionMap.get(questionSeq);
			questionInfo.setScoreType(mstQuestion.getMstEvaluation().getScoreType());
			// Update questionInfo with the data fetched.
			updateQuestionInfo();
			session.put("questionList", questionList);
			Date logActionEndTime = new Date();
			String actionName = "selectScoringType";
			long total = logActionEndTime.getTime() - logActionStartTime.getTime();
			log.info(actionName + "-Total: " + total);
			long fetchDbInstanceInfoTime = fetchDbInstanceInfoEndTime.getTime()
					- fetchDbInstanceInfoStartTime.getTime();
			log.info(actionName + "-FetchDbInstanceInfo: " + fetchDbInstanceInfoTime);
			if (WebAppConst.SCORE_TYPE[3].equals(questionInfo.getScoreType())) {
				if (sessionQuestionInfo.getMenuId().equals(WebAppConst.INSPECTION_MENU_ID)) {
					return "inspectionGroupSeqSelection";
				} else {
					return "markValueSelection";
				}
			} else if ((WebAppConst.SCORE_TYPE[2].equals(questionInfo.getScoreType())
					|| WebAppConst.SCORE_TYPE[1].equals(questionInfo.getScoreType()))
					&& (sessionQuestionInfo.getMenuId().equals(WebAppConst.PENDING_MENU_ID))) {
				return "pendingCategorySelection";
			} else if ((WebAppConst.SCORE_TYPE[2].equals(questionInfo.getScoreType())
					|| WebAppConst.SCORE_TYPE[1].equals(questionInfo.getScoreType()))
					&& ((sessionQuestionInfo.getMenuId().equals(WebAppConst.CHECKING_MENU_ID))
							|| (sessionQuestionInfo.getMenuId().equals(WebAppConst.INSPECTION_MENU_ID)))) {
				if (sessionQuestionInfo.getMenuId().equals(WebAppConst.INSPECTION_MENU_ID)) {
					return "inspectionGroupSeqSelection";
				} else {
					return "gradeSelection";
				}
			} else if (sessionQuestionInfo.getMenuId().equals(WebAppConst.DENY_MENU_ID)) {
				return "denyCategorySelection";
			} else {
				return "showScoringPage";
			}
		} catch (SaitenRuntimeException we) {
			throw we;
		} catch (Exception e) {
			throw new SaitenRuntimeException(ErrorCode.QUESTION_SELECTION_ACTION_EXCEPTION, e);
		}
	}

	public String fetchMarkValues() {
		try {
			QuestionInfo questionInfo = (QuestionInfo) session.get("questionInfo");
			LinkedHashMap<Integer, Short> markValuesMap = new LinkedHashMap<Integer, Short>();
			selectedQuestionMarkValueMap = new LinkedHashMap<Short, String>();
			LinkedHashMap<Integer, LinkedHashMap<Integer, Short>> mstMarkValueMap = ((SaitenConfig) ServletActionContext
					.getServletContext().getAttribute("saitenConfigObject")).getMstMarkValueMap();
			markValuesMap = mstMarkValueMap.get(questionInfo.getQuestionSeq());

			if (markValuesMap == null || markValuesMap.isEmpty()) {
				this.addActionError(getText(WebAppConst.ERROR_NO_MARKVALUES_AVAILABLE));
				return INPUT;
			}

			for (Map.Entry<Integer, Short> entry : markValuesMap.entrySet()) {
				selectedQuestionMarkValueMap.put(entry.getValue(), entry.getValue().toString());
			}

			selectedQuestionMarkValueMap.put(new Short(WebAppConst.MINUS_ONE), getText(WebAppConst.OTHERS));
			questionList = (String) session.get("questionList");
			String[] selectedQuestion = questionList.split(WebAppConst.COLON);
			subjectShortName = selectedQuestion[1];
			session.put("selectedQuestionMarkValueMap", selectedQuestionMarkValueMap);
			MstScorerInfo scorerInfo = ((MstScorerInfo) session.get("scorerInfo"));
			log.info(scorerInfo.getScorerId() + "-" + questionInfo.getMenuId() + "-"
					+ "Loaded Mark Value Selection Screen." + "-{ Question Sequence: " + questionInfo.getQuestionSeq()
					+ "}");
		} catch (SaitenRuntimeException we) {
			throw we;
		} catch (Exception e) {
			throw new SaitenRuntimeException(ErrorCode.QUESTION_SELECTION_ACTION_EXCEPTION, e);
		}
		return SUCCESS;
	}

	private void buildQuestionInfo() {
		questionInfo = (QuestionInfo) ContextLoader.getCurrentWebApplicationContext().getBean("questionInfo");
		questionInfo.setMenuId(selectedMenuId);

		session.put("questionInfo", questionInfo);
	}

	private void updateQuestionInfo() {
		QuestionInfo sessionQuestionInfo = (QuestionInfo) session.get("questionInfo");

		sessionQuestionInfo.setConnectionString(questionInfo.getConnectionString());
		sessionQuestionInfo.setAnswerScoreTable(questionInfo.getAnswerScoreTable());
		sessionQuestionInfo.setAnswerScoreHistoryTable(questionInfo.getAnswerScoreHistoryTable());
		sessionQuestionInfo.setManualDocument(questionInfo.getManualDocument());
		sessionQuestionInfo.setQuestionFileName(questionInfo.getQuestionFileName());
		sessionQuestionInfo.setQuestionSeq(questionInfo.getQuestionSeq());
		sessionQuestionInfo.setQuestionNum(questionInfo.getQuestionNum());
		sessionQuestionInfo.setSubjectCode(questionInfo.getSubjectCode());
		sessionQuestionInfo.setSubjectShortName(questionInfo.getSubjectShortName());
		sessionQuestionInfo.setHistoryRecordCount(questionInfo.getHistoryRecordCount());
		sessionQuestionInfo.setPrevRecordCount(questionInfo.getHistoryRecordCount());
		sessionQuestionInfo.setNextRecordCount(WebAppConst.MINUS_ONE);
		sessionQuestionInfo.setCheckPointList(questionInfo.getCheckPointList());
		sessionQuestionInfo.setPendingCategoryGroupMap(questionInfo.getPendingCategoryGroupMap());
		sessionQuestionInfo.setSide(questionInfo.getSide());
		sessionQuestionInfo.setQuestionType(questionInfo.getQuestionType());
		sessionQuestionInfo.setDenyCategoryGroupMap(questionInfo.getDenyCategoryGroupMap());
		if (sessionQuestionInfo.getScoreType() == null) {
			sessionQuestionInfo.setScoreType(questionInfo.getScoreType());
		}
		session.put("questionInfo", sessionQuestionInfo);
	}

	public Map<String, String> getQuestionMap() {
		return questionMap;
	}

	/**
	 * 
	 * @param questionMap
	 */
	public void setQuestionMap(Map<String, String> questionMap) {
		this.questionMap = questionMap;
	}

	public QuestionInfo getQuestionInfo() {
		return questionInfo;
	}

	public String getSelectedMenuId() {
		return selectedMenuId;
	}

	/**
	 * 
	 * @param questionInfo
	 */
	public void setQuestionInfo(QuestionInfo questionInfo) {
		this.questionInfo = questionInfo;
	}

	/**
	 * 
	 * @param questionSelectionService
	 */
	public void setQuestionSelectionService(QuestionSelectionService questionSelectionService) {
		this.questionSelectionService = questionSelectionService;
	}

	/**
	 * @param selectedMenuId
	 */
	public void setSelectedMenuId(String selectedMenuId) {
		this.selectedMenuId = selectedMenuId;
	}

	/**
	 * 
	 * @param session
	 */
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public Integer getGradeNum() {
		return gradeNum;
	}

	public void setGradeNum(Integer gradeNum) {
		this.gradeNum = gradeNum;
	}

	public Short getPendingCategory() {
		return pendingCategory;
	}

	public void setPendingCategory(Short pendingCategory) {
		this.pendingCategory = pendingCategory;
	}

	/**
	 * @return the questionList
	 */
	public String getQuestionList() {
		return questionList;
	}

	/**
	 * @param questionList
	 *            the questionList to set
	 */
	public void setQuestionList(String questionList) {
		this.questionList = questionList;
	}

	/**
	 * @return the subjectShortName
	 */
	public String getSubjectShortName() {
		return subjectShortName;
	}

	/**
	 * @param subjectShortName
	 *            the subjectShortName to set
	 */
	public void setSubjectShortName(String subjectShortName) {
		this.subjectShortName = subjectShortName;
	}

	/**
	 * @return the selectedQuestionMarkValueMap
	 */
	public Map<Short, String> getSelectedQuestionMarkValueMap() {
		return selectedQuestionMarkValueMap;
	}

	/**
	 * @param selectedQuestionMarkValueMap
	 *            the selectedQuestionMarkValueMap to set
	 */
	public void setSelectedQuestionMarkValueMap(Map<Short, String> selectedQuestionMarkValueMap) {
		this.selectedQuestionMarkValueMap = selectedQuestionMarkValueMap;
	}

	/**
	 * @return the selectedMarkValue
	 */
	public Short getSelectedMarkValue() {
		return selectedMarkValue;
	}

	/**
	 * @param selectedMarkValue
	 *            the selectedMarkValue to set
	 */
	public void setSelectedMarkValue(Short selectedMarkValue) {
		this.selectedMarkValue = selectedMarkValue;
	}

	public String getDenyCategory() {
		return denyCategory;
	}

	public void setDenyCategory(String denyCategory) {
		this.denyCategory = denyCategory;
	}

	public List<String> getManualList() {
		return manualList;
	}

	public void setManualList(List<String> manualList) {
		this.manualList = manualList;
	}

	// return default manualType value
	public String getManualType() {
		return getText(WebAppConst.MANUAL1);
	}

	public void setSelectedGradeNum(String selectedGradeNum) {
		this.selectedGradeNum = selectedGradeNum;
	}

	public String getSelectedGradeNum() {
		return selectedGradeNum;
	}

	/*public void setBitValue(Double bitValue) {
		this.bitValue = bitValue;
	}

	public Double getBitValue() {
		return bitValue;
	}*/
}