package com.saiten.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.web.context.ContextLoader;

import com.opensymphony.xwork2.ActionSupport;
import com.saiten.exception.SaitenRuntimeException;
import com.saiten.info.GradeInfo;
import com.saiten.info.HistoryInfo;
import com.saiten.info.MstScorerInfo;
import com.saiten.info.QuestionInfo;
import com.saiten.info.ScoreCurrentInfo;
import com.saiten.info.ScoreHistoryInfo;
import com.saiten.info.ScoreInputInfo;
import com.saiten.info.ScoreSamplingInfo;
import com.saiten.info.ScoreSearchInfo;
import com.saiten.info.SpecialScoreInputInfo;
import com.saiten.info.TranDescScoreInfo;
import com.saiten.model.MstQuestion;
import com.saiten.model.TranScorerSessionInfo;
import com.saiten.service.HistoryListingService;
import com.saiten.service.LookAfterwardsService;
import com.saiten.service.QuestionSelectionService;
import com.saiten.service.ScoreSearchService;
import com.saiten.service.ScoreService;
import com.saiten.util.ErrorCode;
import com.saiten.util.SaitenMasterUtil;
import com.saiten.util.SaitenUtil;
import com.saiten.util.UnlockAnswerUtil;
import com.saiten.util.WebAppConst;

/**
 * @author sachin
 * @version 1.0
 * @created 13-Dec-2012 11:53:29 AM
 */
public class ScoreSearchAction extends ActionSupport implements SessionAware, ServletRequestAware {

	private static Logger log = Logger.getLogger(ScoreSearchAction.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, Object> session;
	private String selectedMenuId;
	private ScoreSearchService scoreSearchService;
	@SuppressWarnings("unused")
	private Properties saitenGlobalProperties;
	private ScoreSearchInfo scoreSearchInfo;
	private ScoreInputInfo scoreInputInfo;
	private SpecialScoreInputInfo specialScoreInputInfo;
	private TranDescScoreInfo tranDescScoreInfo;
	private QuestionSelectionService questionSelectionService;
	private List<TranDescScoreInfo> answerRecordsList;
	private List<ScoreSamplingInfo> scoreSamplingInfoList;
	private GradeInfo gradeInfo;
	private ScoreService scoreService;
	private Boolean prevOrNextFlag;
	private Integer gradeNum;
	private Integer pendingCategorySeq;
	private Map<Integer, QuestionInfo> questionInfoMap;
	private Properties saitenApplicationProperties;
	private HistoryListingService historyListingService;
	public static final String FAILURE = "failure";
	public static final String DECLINE = "decline";
	private Object recordCount;
	private Boolean forceAndStateTransitionFlag;
	private HttpServletRequest request;
	private Map<String, Object> result;
	private boolean selectAllFlag;
	private Integer answerSequence;
	private boolean answerAlreadyScoredFlag;
	private String pageNumber;
	private Short questionNum;
	private String subjectCode;
	private List<String> loggedInScorerSubjectList;
	private List<TranDescScoreInfo> processDetailsList;
	private LookAfterwardsService lookAfterwardsService;
	private Integer denyCategorySeq;
	private double bitValue;

	@SuppressWarnings("unchecked")
	public String findPrevOrNextAnswer() {
		QuestionInfo sessionQuestionInfo = (QuestionInfo) session.get("questionInfo");
		answerRecordsList = (List<TranDescScoreInfo>) session.get("answerRecordsList");

		if (WebAppConst.REFERENCE_SAMP_MENU_ID.equals(sessionQuestionInfo.getMenuId())) {
			session.remove("lookAfterwardsInfo");
		}

		try {
			// Update prev / next record counters
			updatePrevNextRecordCounts(sessionQuestionInfo);

			// Build tranDescScoreInfo object to be displayed
			buildTranDescScoreInfo(sessionQuestionInfo.getPrevRecordCount());

			getScoreSearchActionData(sessionQuestionInfo.getQuestionSeq());
		} catch (SaitenRuntimeException we) {
			throw we;
		} catch (Exception e) {
			throw new SaitenRuntimeException(ErrorCode.SCORE_SEARCH_ACTION_EXCEPTION, e);
		}

		return SUCCESS;
	}

	public String onLoad() {
		MstScorerInfo scorerInfo = ((MstScorerInfo) session.get("scorerInfo"));
		log.info(scorerInfo.getScorerId() + "-" + selectedMenuId + "-" + "Search Screen loading.");
		String pucnText = (String) session.get("punchText");
		if (selectedMenuId.equals(WebAppConst.STATE_TRAN_MENU_ID)) {
			session.remove("updatedCount");
		}

		if (selectedMenuId.equals(WebAppConst.REFERENCE_SAMP_MENU_ID)) {
			session.remove("lookAfterwardsInfo");
		}
		if (selectedMenuId.equals(WebAppConst.SPECIAL_SAMP_INSPECTION_MENU_ID)) {
			session.remove("historyRecordCount");
			session.remove("prevRecordCount");
			session.remove("nextRecordCount");
		}

		scoreSearchInfo = (ScoreSearchInfo) ContextLoader.getCurrentWebApplicationContext().getBean("scoreSearchInfo");

		try {

			if (!(selectedMenuId.equals(WebAppConst.REFERENCE_SAMP_MENU_ID)
					|| selectedMenuId.equals(WebAppConst.SCORE_SAMP_MENU_ID)
					|| selectedMenuId.equals(WebAppConst.FORCED_MENU_ID)
					|| selectedMenuId.equals(WebAppConst.STATE_TRAN_MENU_ID)
					|| selectedMenuId.equals(WebAppConst.SPECIAL_SAMP_INSPECTION_MENU_ID))) {
				SaitenUtil.clearSessionInfo();
			} else {
				// remove the session Info's and unlock the all lock answers
				// locked by logged in scorer.
				if (selectedMenuId.equals(WebAppConst.FORCED_MENU_ID)
						|| selectedMenuId.equals(WebAppConst.SCORE_SAMP_MENU_ID)) {
					session.remove("tranDescScoreInfo");
					session.remove("tranDescScoreInfoCopy");
					session.remove("historyScreenFlag");
					session.remove("answerRecordsList");
					session.remove("recordNumber");
				} else if (selectedMenuId.equals(WebAppConst.STATE_TRAN_MENU_ID)) {
					session.remove("selectAllFlag");
				}

				session.remove("recordNumber");

				QuestionInfo questionInfo = (QuestionInfo) session.get("questionInfo");

				if ((selectedMenuId.equals(WebAppConst.SCORE_SAMP_MENU_ID)
						|| selectedMenuId.equals(WebAppConst.FORCED_MENU_ID)) && (questionInfo != null)) {

					String lockBy = ((MstScorerInfo) session.get("scorerInfo")).getScorerId();

					// On load of search screen, unlock the answers locked by
					// logged in scorer.
					if (questionInfo.getConnectionString() != null
							&& questionInfo.getQuestionSeq() != WebAppConst.ZERO) {
						Integer answerSeq = null;
						scoreService.unlockAnswer(questionInfo.getQuestionSeq(), lockBy,
								questionInfo.getConnectionString(), answerSeq);
					}
				}
			}

			// Build subjectCode - subjectName map
			Map<String, String> subjectNameList = scoreSearchService.findSubjectNameList();
			scoreSearchInfo.setSubjectNameList(subjectNameList);

			// build subject list for logged in scorer.
			int roleId = scorerInfo.getRoleId();
			if (roleId != WebAppConst.ADMIN_ROLE_ID) {
				String scorerId = ((MstScorerInfo) session.get("scorerInfo")).getScorerId();
				loggedInScorerSubjectList = scoreSearchService.buildLoggedInScorerSubjectList(scorerId);
			}

			if (!selectedMenuId.equals(WebAppConst.SPECIAL_SAMP_INSPECTION_MENU_ID)) {
				// Build eventId - eventName map
				Map<Short, String> historyEventList = scoreSearchService.findHistoryEventList(selectedMenuId);
				scoreSearchInfo.setHistoryEventList(historyEventList);

				// Build data for Date dropdown fields
				scoreSearchInfo.setYearList(SaitenUtil.getCalendarYears());
				scoreSearchInfo.setMonthList(SaitenUtil.getCalendarMonths());
				scoreSearchInfo.setDaysList(SaitenUtil.getCalendardays());
				scoreSearchInfo.setHoursList(SaitenUtil.getClockHours());
				scoreSearchInfo.setMinutesList(SaitenUtil.getClockMinutes());
				// Creating list for punch text
				List<String> punchTextList = new ArrayList<String>();
				punchTextList.add(getText(WebAppConst.PUNCH_TEXT_CONDITION_EXACT_MATCH));
				punchTextList.add(getText(WebAppConst.PUNCH_TEXT_CONDITION_FORWORD_MATCH));
				punchTextList.add(getText(WebAppConst.PUNCH_TEXT_CONDITION_BACKWORD_MATCH));
				punchTextList.add(getText(WebAppConst.PUNCH_TEXT_CONDITION_PARTIAL_MATCH));
				scoreSearchInfo.setPunchTextMap(SaitenUtil.getPunchTextMap(punchTextList));

				// Build scoringState - stateName map
				Map<Short, String> currentStateList = scoreSearchService.findCurrentStateList(selectedMenuId);
				scoreSearchInfo.setCurrentStateList(currentStateList);

			}
			// Build questionInfo with menuId
			buildQuestionInfo();
		} catch (SaitenRuntimeException we) {
			throw we;
		} catch (Exception e) {
			throw new SaitenRuntimeException(ErrorCode.SCORE_SEARCH_ACTION_EXCEPTION, e);
		}
		scoreInputInfo = (ScoreInputInfo) session.get("scoreInputInfo");

		if (scoreInputInfo != null && scoreInputInfo.getScoreCurrentInfo() != null) {
			scoreInputInfo.getScoreCurrentInfo().setPunchText(pucnText);
		}

		Boolean saitenRelease = Boolean.valueOf(SaitenUtil
				.getPropertyFromPropertyFile(WebAppConst.APPLICATION_PROPERTIES_FILE, WebAppConst.SAITEN_RELEASE));
		Boolean shinEigoRelease = Boolean.valueOf(SaitenUtil
				.getPropertyFromPropertyFile(WebAppConst.APPLICATION_PROPERTIES_FILE, WebAppConst.SHINEIGO_RELEASE));

		String result = null;
		if (saitenRelease) {
			result = "saiten-success";
		} else if (shinEigoRelease) {
			result = "shineigo-success";
		}

		return result;
	}

	public String fetchRecordsCount() {
		try {
			MstScorerInfo scorerInfo = ((MstScorerInfo) session.get("scorerInfo"));
			QuestionInfo sessionQuestionInfo = (QuestionInfo) session.get("questionInfo");
			log.info(scorerInfo.getScorerId() + "-" + sessionQuestionInfo.getMenuId() + "-"
					+ "Search count screen loading. Search Criteria: -{ " + scoreInputInfo + " } ");
			// adding escape character before any % in punchText
			String tempStr = scoreInputInfo.getScoreCurrentInfo() != null
					? scoreInputInfo.getScoreCurrentInfo().getPunchText() : null;
			String inputString = tempStr;
			session.put("punchText", inputString);
			if (tempStr != null) {
				StringBuilder sb = new StringBuilder();
				sb.append(tempStr);
				for (int i = 0; i < tempStr.length(); i++) {
					if (tempStr.charAt(i) == WebAppConst.PERCENTAGE_CHARACTER) {
						sb.insert(i, WebAppConst.ESCAPE_CHARACTER);
						tempStr = sb.toString();
						i++;
					} else if (tempStr.charAt(i) == WebAppConst.ESCAPE_CHARACTER) {
						sb.insert(i, WebAppConst.ESCAPE_CHARACTER);
						tempStr = sb.toString();
						i++;
					} else if (tempStr.charAt(i) == WebAppConst.UNDER_SCORE) {
						sb.insert(i, WebAppConst.ESCAPE_CHARACTER);
						tempStr = sb.toString();
						i++;

					}
					scoreInputInfo.getScoreCurrentInfo().setPunchText(tempStr);
				}
			}

			// Fetch questionSeq from subjectCode & questionNum
			List<Integer> questionSeqList = scoreSearchService.findQuestionSeq(scoreInputInfo.getSubjectCode(),
					scoreInputInfo.getQuestionNum());

			Integer questionSeq = !questionSeqList.isEmpty() ? questionSeqList.get(0) : null;

			// Put scoreInputInfo in session to display data onLoad
			session.put("scoreInputInfo", scoreInputInfo);

			// Display record not available message, if questionSeq == null
			if (questionSeq == null) {
				sessionQuestionInfo = (QuestionInfo) session.get("questionInfo");

				sessionQuestionInfo.setQuestionSeq(WebAppConst.ZERO);

				session.put("questionInfo", sessionQuestionInfo);
				if (scoreInputInfo != null && scoreInputInfo.getScoreCurrentInfo() != null) {
					scoreInputInfo.getScoreCurrentInfo().setPunchText(inputString);
				}
				return FAILURE;
			}

			// Build history gradeSeqList & pendingCategorySeqList
			fetchScoreHistoryInfo(questionSeq);

			// Build current gradeSeqList & pendingCategorySeqList
			fetchScoreCurrentInfo(questionSeq);

			// Fetch DbInstanceInfo and questionInfo based on questionSeq
			QuestionInfo questionInfo = questionSelectionService.fetchDbInstanceInfo(questionSeqList);
			questionInfo.setQuestionSeq(questionSeq);

			sessionQuestionInfo = (QuestionInfo) session.get("questionInfo");

			questionInfo.setMenuId(sessionQuestionInfo.getMenuId());
			sessionQuestionInfo.setQuestionType(questionInfo.getQuestionType());
			session.put("questionInfo", sessionQuestionInfo);

			if (questionInfo.getMenuId().equals(WebAppConst.FORCED_MENU_ID)) {
				// Get checkPointList for selected question
				questionInfo.setCheckPointList(questionSelectionService.findCheckPoints(questionSeq));

				// Get pending categories for selected question
				questionInfo.setPendingCategoryGroupMap(questionSelectionService.findPendingCategories(questionSeq));

				String scorerId = scorerInfo.getScorerId();

				// Build scoringStateList based on menuId and noDbUpdate value.
				// e.g.
				// 122, 123
				List<Short> scoringStateList = SaitenUtil.buildScoringStateList(scorerInfo.getNoDbUpdate());

				List<Integer> questionSequenceList = new ArrayList<Integer>();
				questionSequenceList.add(questionSeq);

				// Fetch historyRecordCount for selected question
				questionInfo.setHistoryRecordCount(questionSelectionService.findHistoryRecordCount(scorerId,
						questionSequenceList, questionInfo.getConnectionString(), scoringStateList));
			}

			Map<String, List<Integer>> questionSeqMap = null;
			String answerFormNum = null;
			Integer historyRecordCount = null;
			fetchAnswerRecordsList(questionSeq, questionInfo, questionSeqMap, answerFormNum, historyRecordCount);

			// Once again put scoreInputInfo in session because ScoreHistoryInfo
			// and ScoreCurrentInfo states modified.
			session.put("scoreInputInfo", scoreInputInfo);
		} catch (SaitenRuntimeException we) {
			throw we;
		} catch (Exception e) {
			throw new SaitenRuntimeException(ErrorCode.SPECIAL_SCORE_SEARCH_ACTION_EXCEPTION, e);
		}

		return SUCCESS;
	}

	public String selectAll() {
		if (session.get("scorerInfo") == null) {
			// Return statusCode as a JSON response for invalid session
			result = SaitenUtil.getAjaxCallStatusCode(session);
		}
		session.put("selectAllFlag", selectAllFlag);
		return SUCCESS;
	}

	public String search() {
		try {
			QuestionInfo sessionQuestionInfo = (QuestionInfo) session.get("questionInfo");
			MstScorerInfo scorerInfo = ((MstScorerInfo) session.get("scorerInfo"));
			log.info(scorerInfo.getScorerId() + "-" + sessionQuestionInfo.getMenuId() + "-" + "Search Criteria: -{ "
					+ scoreInputInfo + "}");
			// adding escape character before any % in punchText
			String tempStr = scoreInputInfo.getScoreCurrentInfo() != null
					? scoreInputInfo.getScoreCurrentInfo().getPunchText() : null;
			String inputString = tempStr;
			session.put("punchText", inputString);
			if (tempStr != null) {
				StringBuilder sb = new StringBuilder();
				sb.append(tempStr);
				for (int i = 0; i < tempStr.length(); i++) {
					if (tempStr.charAt(i) == WebAppConst.PERCENTAGE_CHARACTER) {
						sb.insert(i, WebAppConst.ESCAPE_CHARACTER);
						tempStr = sb.toString();
						i++;
					} else if (tempStr.charAt(i) == WebAppConst.ESCAPE_CHARACTER) {
						sb.insert(i, WebAppConst.ESCAPE_CHARACTER);
						tempStr = sb.toString();
						i++;
					} else if (tempStr.charAt(i) == WebAppConst.UNDER_SCORE) {
						sb.insert(i, WebAppConst.ESCAPE_CHARACTER);
						tempStr = sb.toString();
						i++;

					}
					scoreInputInfo.getScoreCurrentInfo().setPunchText(tempStr);
				}
			}

			// Fetch questionSeq from subjectCode & questionNum
			List<Integer> questionSeqList = scoreSearchService.findQuestionSeq(scoreInputInfo.getSubjectCode(),
					scoreInputInfo.getQuestionNum());
			Integer questionSeq = !questionSeqList.isEmpty() ? questionSeqList.get(0) : null;
			// Put scoreInputInfo in session to display data onLoad
			session.put("scoreInputInfo", scoreInputInfo);

			// Display record not available message, if questionSeq == null
			if (questionSeq == null) {

				sessionQuestionInfo.setQuestionSeq(WebAppConst.ZERO);
				;

				session.put("questionInfo", sessionQuestionInfo);
				if (scoreInputInfo != null && scoreInputInfo.getScoreCurrentInfo() != null) {
					scoreInputInfo.getScoreCurrentInfo().setPunchText(inputString);
				}
				return FAILURE;
			}

			// Build history gradeSeqList & pendingCategorySeqList
			fetchScoreHistoryInfo(questionSeq);

			// Build current gradeSeqList & pendingCategorySeqList
			fetchScoreCurrentInfo(questionSeq);

			// Fetch DbInstanceInfo and questionInfo based on questionSeq
			QuestionInfo questionInfo = questionSelectionService.fetchDbInstanceInfo(questionSeqList);
			questionInfo.setQuestionSeq(questionSeq);
			sessionQuestionInfo.setQuestionType(questionInfo.getQuestionType());
			session.put("questionInfo", sessionQuestionInfo);

			// Get checkPointList for selected question
			questionInfo.setCheckPointList(questionSelectionService.findCheckPoints(questionSeq));

			questionInfo.setMenuId(sessionQuestionInfo.getMenuId());

			if (sessionQuestionInfo.getMenuId().equals(WebAppConst.SCORE_SAMP_MENU_ID)) {
				// Get pending categories for selected question
				questionInfo.setPendingCategoryGroupMap(questionSelectionService.findPendingCategories(questionSeq));

				String scorerId = scorerInfo.getScorerId();

				// Build scoringStateList based on menuId and noDbUpdate value.
				// e.g.
				// 122, 123
				List<Short> scoringStateList = SaitenUtil.buildScoringStateList(scorerInfo.getNoDbUpdate());

				List<Integer> questionSequenceList = new ArrayList<Integer>();
				questionSequenceList.add(questionSeq);

				// Fetch historyRecordCount for selected question
				questionInfo.setHistoryRecordCount(questionSelectionService.findHistoryRecordCount(scorerId,
						questionSequenceList, questionInfo.getConnectionString(), scoringStateList));
			}

			// Fetch answerRecordsList based on search criteria
			Map<String, List<Integer>> questionSeqMap = null;
			String answerFormNum = null;
			Integer historyRecordCount = null;
			fetchAnswerRecordsList(questionSeq, questionInfo, questionSeqMap, answerFormNum, historyRecordCount);

			/*
			 * if (sessionQuestionInfo.getMenuId().equals(
			 * WebAppConst.STATE_TRAN_MENU_ID) && (scoreSamplingInfoList == null
			 * || scoreSamplingInfoList .isEmpty())) { return SUCCESS;
			 * 
			 * } else
			 */
			if ((!sessionQuestionInfo.getMenuId().equals(WebAppConst.STATE_TRAN_MENU_ID))
					&& (answerRecordsList == null || answerRecordsList.isEmpty())) {
				// Display record not available, if answerRecordsList is null or
				// empty
				if (scoreInputInfo != null && scoreInputInfo.getScoreCurrentInfo() != null) {
					scoreInputInfo.getScoreCurrentInfo().setPunchText(inputString);
				}
				return FAILURE;
			}
		} catch (SaitenRuntimeException we) {
			throw we;
		} catch (Exception e) {
			throw new SaitenRuntimeException(ErrorCode.SCORE_SEARCH_ACTION_EXCEPTION, e);
		}

		return SUCCESS;
	}

	/**
	 * @param questionSeq
	 * @param questionInfo
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void fetchAnswerRecordsList(Integer questionSeq, QuestionInfo questionInfo,
			Map<String, List<Integer>> questionSeqMap, String answerFormNum, Integer historyRecordCount) {

		MstScorerInfo mstScorerInfo = (MstScorerInfo) session.get("scorerInfo");
		String scorerId = mstScorerInfo.getScorerId();

		String menuId = questionInfo.getMenuId();
		if (menuId.equals(WebAppConst.SPECIAL_SCORING_ENLARGE_TYPE_MENU_ID)
				|| menuId.equals(WebAppConst.SPECIAL_SCORING_OMR_READ_FAIL_MENU_ID)) {
			answerRecordsList = fetchSpecialScoringAnswerRecordList(questionSeqMap, menuId, scorerId, answerFormNum,
					historyRecordCount);

			// Load historyInfoList
			List<HistoryInfo> historyInfoList = historyListingService.loadOmrEnlargeHistory(mstScorerInfo, questionInfo,
					questionSeqMap);
			session.put("historyInfoList", historyInfoList);

		} else {
			Integer startRecord = null;
			Integer endRecord = null;

			List searchResultList = scoreSearchService.searchAnswerRecords(questionInfo, scoreInputInfo, scorerId,
					forceAndStateTransitionFlag, startRecord, endRecord);

			if ((forceAndStateTransitionFlag != null) && (forceAndStateTransitionFlag == WebAppConst.FALSE)) {
				if (searchResultList.get(0) != null) {
					recordCount = ((BigDecimal) searchResultList.get(0)).intValue();
				} else {
					recordCount = WebAppConst.ZERO;
				}
				log.info(scorerId + "-" + questionInfo.getMenuId() + "-" + "Search Count: -{ " + recordCount + " } ");
				session.put("searchResultCount", recordCount);
			} else if ((forceAndStateTransitionFlag != null) && (forceAndStateTransitionFlag == WebAppConst.TRUE)) {
				if (searchResultList != null) {
					scoreSamplingInfoList = (List<ScoreSamplingInfo>) searchResultList;
				}

			} else {
				answerRecordsList = (List<TranDescScoreInfo>) searchResultList;
			}
		}

		if (!(menuId.equals(WebAppConst.SPECIAL_SCORING_ENLARGE_TYPE_MENU_ID)
				|| menuId.equals(WebAppConst.SPECIAL_SCORING_OMR_READ_FAIL_MENU_ID))) {
			updateQuestionInfo(questionInfo);
			questionInfo = (QuestionInfo) session.get("questionInfo");
		}

		if (answerRecordsList != null && !answerRecordsList.isEmpty()) {
			session.put("answerRecordsList", answerRecordsList);
			if (menuId.equals(WebAppConst.SCORE_SAMP_MENU_ID)
					|| menuId.equals(WebAppConst.SPECIAL_SCORING_ENLARGE_TYPE_MENU_ID)
					|| menuId.equals(WebAppConst.SPECIAL_SCORING_OMR_READ_FAIL_MENU_ID)) {
				// To get first record from 'answerRecordsList'
				findAnswer();
			} else if (!menuId.equals(WebAppConst.STATE_TRAN_MENU_ID)) {
				buildTranDescScoreInfo(questionInfo.getPrevRecordCount());
				getScoreSearchActionData(questionSeq);
			}
		}
		if (scoreSamplingInfoList != null && !scoreSamplingInfoList.isEmpty()) {
			session.put("scoreSamplingInfoList", scoreSamplingInfoList);
		}
	}

	@SuppressWarnings("unchecked")
	public String findAnswer() {
		try {
			Integer recordNumber = (Integer) session.get("recordNumber");
			recordNumber = recordNumber == null ? 0 : recordNumber;
			answerRecordsList = (List<TranDescScoreInfo>) session.get("answerRecordsList");

			if ((answerRecordsList == null)
					|| (answerRecordsList != null && (recordNumber > (answerRecordsList.size() - 1)))) {
				return FAILURE;
			}

			buildTranDescScoreInfo(recordNumber);

			QuestionInfo questionInfo = (QuestionInfo) session.get("questionInfo");
			getScoreSearchActionData(questionInfo.getQuestionSeq());

			// Increment recordNumber by 1, to get next record from
			// 'answerRecordsList'.
			session.put("recordNumber", ++recordNumber);

		} catch (SaitenRuntimeException we) {
			throw we;
		} catch (Exception e) {
			throw new SaitenRuntimeException(ErrorCode.SCORE_SEARCH_ACTION_EXCEPTION, e);
		}
		return SUCCESS;
	}

	public String findForcedScoringAnswer() {
		try {

			ScoreSamplingInfo scoreSamplingInfo = new ScoreSamplingInfo();
			scoreSamplingInfo.setAnswerSeq(answerSequence);

			String result = buildTranDescScoreInfo(scoreSamplingInfo);
			if (result.equals(FAILURE))
				return FAILURE;

		} catch (SaitenRuntimeException we) {
			throw we;
		} catch (Exception e) {
			throw new SaitenRuntimeException(ErrorCode.SCORE_SEARCH_ACTION_EXCEPTION, e);
		}
		return SUCCESS;
	}

	/**
	 * @param questionSeq
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void fetchScoreHistoryInfo(Integer questionSeq) {
		ScoreHistoryInfo scoreHistoryInfo = scoreInputInfo.getScoreHistoryInfo();

		if (scoreHistoryInfo != null && scoreHistoryInfo.isHistoryBlock()) {
			Integer historyCategoryType = scoreHistoryInfo.getHistoryCategoryType();

			List historyGradeSeqList = buildGradeSeqList(questionSeq, historyCategoryType,
					scoreHistoryInfo.getHistoryGradeNum());
			if (historyGradeSeqList != null && historyGradeSeqList.isEmpty()) {
				historyGradeSeqList.add(WebAppConst.MINUS_ONE);
			}
			scoreHistoryInfo.setHistoryGradeSeqList(historyGradeSeqList);

			List historyPendingCategorySeqList = buildPendingCategorySeqList(questionSeq, historyCategoryType,
					scoreHistoryInfo.getHistoryPendingCategory());
			if (historyPendingCategorySeqList != null && historyPendingCategorySeqList.isEmpty()) {
				historyPendingCategorySeqList.add(WebAppConst.MINUS_ONE);
			}
			scoreHistoryInfo.setHistoryPendingCategorySeqList(historyPendingCategorySeqList);

			List historyDenyCategorySeqList = buildDenyCategorySeqList(questionSeq, historyCategoryType,
					scoreHistoryInfo.getHistoryDenyCategory());
			if (historyDenyCategorySeqList != null && historyDenyCategorySeqList.isEmpty()) {
				historyDenyCategorySeqList.add(WebAppConst.MINUS_ONE);
			}
			scoreHistoryInfo.setHistoryDenyCategorySeqList(historyDenyCategorySeqList);
			System.out.println(historyDenyCategorySeqList);
			System.out.println(scoreHistoryInfo.getHistoryDenyCategory());
		}
	}

	/**
	 * @param questionSeq
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void fetchScoreCurrentInfo(Integer questionSeq) {
		ScoreCurrentInfo scoreCurrentInfo = scoreInputInfo.getScoreCurrentInfo();

		if (scoreCurrentInfo != null && scoreCurrentInfo.isCurrentBlock()) {
			Integer currentCategoryType = scoreCurrentInfo.getCurrentCategoryType();

			List currentGradeSeqList = buildGradeSeqList(questionSeq, currentCategoryType,
					scoreCurrentInfo.getCurrentGradeNum());
			if (currentGradeSeqList != null && currentGradeSeqList.isEmpty()) {
				currentGradeSeqList.add(WebAppConst.MINUS_ONE);
			}
			scoreCurrentInfo.setCurrentGradeSeqList(currentGradeSeqList);

			List currentPendingCategorySeqList = buildPendingCategorySeqList(questionSeq, currentCategoryType,
					scoreCurrentInfo.getCurrentPendingCategory());
			if (currentPendingCategorySeqList != null && currentPendingCategorySeqList.isEmpty()) {
				currentPendingCategorySeqList.add(WebAppConst.MINUS_ONE);
			}
			scoreCurrentInfo.setCurrentPendingCategorySeqList(currentPendingCategorySeqList);

			List currentDenyCategorySeqList = buildDenyCategorySeqList(questionSeq, currentCategoryType,
					scoreCurrentInfo.getCurrentDenyCategory());
			if (currentDenyCategorySeqList != null && currentDenyCategorySeqList.isEmpty()) {
				currentDenyCategorySeqList.add(WebAppConst.MINUS_ONE);
			}
			scoreCurrentInfo.setCurrentDenyCategorySeqList(currentDenyCategorySeqList);
			System.out.println(currentDenyCategorySeqList);
		}
	}

	/**
	 * @param questionSeq
	 */
	private void getScoreSearchActionData(int questionSeq) {
		if (tranDescScoreInfo.getGradeSeq() != null) {
			// Get grade info. for scored answer
			gradeInfo = scoreService.buildGradeInfo(tranDescScoreInfo.getGradeSeq(), questionSeq);
		}
		QuestionInfo questionInfo = (QuestionInfo) session.get("questionInfo");
		String menuId = questionInfo.getMenuId();
		Character scoreType = questionInfo.getScoreType();
		Double bitValue = tranDescScoreInfo.getAnswerInfo().getBitValue();
		Short scoringState = tranDescScoreInfo.getScoringState();
		List<Short> mvSelectionList = Arrays.asList(WebAppConst.MV_SELECTION_STATES);
		if ((WebAppConst.SCORE_TYPE[3].equals(scoreType)) && (WebAppConst.SPECIAL_SCORING_BLIND_TYPE_MENU_ID
				.equals(menuId) || WebAppConst.SPECIAL_SCORING_LANGUAGE_SUPPORT_MENU_ID.equals(menuId)
				|| WebAppConst.SPECIAL_SCORING_ENLARGE_TYPE_MENU_ID.equals(menuId)
				|| WebAppConst.SPECIAL_SCORING_OMR_READ_FAIL_MENU_ID.equals(menuId)
				|| ((menuId.equals(WebAppConst.REFERENCE_SAMP_MENU_ID) || menuId.equals(WebAppConst.SCORE_SAMP_MENU_ID)
						|| menuId.equals(WebAppConst.FORCED_MENU_ID)) && (mvSelectionList.contains(scoringState))))) {
			List<Short> markValueList = tranDescScoreInfo.getMarkValueList();
			session.remove("selectedCheckPointList");
			if ((markValueList != null) && (!markValueList.contains((short) 0))) {
				session.put("selectedCheckPointList", markValueList);
			}
		} else if (bitValue != null) {
			// Get selected checkPointList for history answer
			List<Short> selectedCheckPointList = SaitenUtil.getSelectedCheckPointList(bitValue);
			session.put("selectedCheckPointList", selectedCheckPointList);
		} else {
			// bitValue will be null for pending answer. so remove
			// selectedCheckPointList from session.
			session.remove("selectedCheckPointList");
		}
	}

	/**
	 * @param prevRecordCount
	 */
	@SuppressWarnings("unchecked")
	private void buildTranDescScoreInfo(int prevRecordCount) {
		tranDescScoreInfo = answerRecordsList.get(prevRecordCount);

		MstScorerInfo scorerInfo = ((MstScorerInfo) session.get("scorerInfo"));
		QuestionInfo questionInfo = (QuestionInfo) session.get("questionInfo");
		questionInfoMap = (Map<Integer, QuestionInfo>) session.get("questionInfoMap");
		String menuId = questionInfo.getMenuId();

		if (questionInfoMap != null && (menuId.equals(WebAppConst.SPECIAL_SCORING_ENLARGE_TYPE_MENU_ID)
				|| menuId.equals(WebAppConst.SPECIAL_SCORING_OMR_READ_FAIL_MENU_ID))) {
			questionInfo = questionInfoMap.get(tranDescScoreInfo.getAnswerInfo().getQuestionSeq());

			session.put("questionInfo", questionInfo);
		}

		if (menuId.equals(WebAppConst.SCORE_SAMP_MENU_ID)
				|| menuId.equals(WebAppConst.SPECIAL_SCORING_ENLARGE_TYPE_MENU_ID)
				|| menuId.equals(WebAppConst.SPECIAL_SCORING_OMR_READ_FAIL_MENU_ID)
				|| menuId.equals(WebAppConst.FORCED_MENU_ID)) {

			/*
			 * System.out.
			 * println(">>>>>>>>> isAnswerAlreadyScored query start: " + new
			 * Date().getTime());
			 */

			Object str = "";
			str = request.getParameter("prevOrNextFlag");
			boolean nextFlag;

			if (str == null) {
				nextFlag = false;
			} else {
				nextFlag = true;
			}
			// boolean nextFlag = .equals(null)) ? true : false;
			boolean answerAlreadyScored = scoreService.isAnswerAlreadyScored(
					tranDescScoreInfo.getAnswerInfo().getAnswerSeq(), questionInfo.getConnectionString(),
					tranDescScoreInfo.getAnswerInfo().getUpdateDate());
			/*
			 * System.out.println(">>>>>>>>> isAnswerAlreadyScored query end: "
			 * + new Date().getTime());
			 */
			if (!answerAlreadyScored) {
				Date updateDate = new Date();

				/*
				 * System.out.println(">>>>>>>>> lockAnswer query start: " + new
				 * Date().getTime());
				 */
				scoreService.lockAnswer(tranDescScoreInfo.getAnswerInfo().getAnswerSeq(), scorerInfo.getScorerId(),
						questionInfo.getConnectionString(), updateDate);
				/*
				 * System.out.println(">>>>>>>>> lockAnswer query end: " + new
				 * Date().getTime());
				 */

				tranDescScoreInfo.getAnswerInfo().setUpdateDate(updateDate);
			}
			if (nextFlag) {
				log.info(scorerInfo.getScorerId() + "-" + menuId + "-" + "Next Flag " + nextFlag
						+ "\n TranDescScoreInfo: " + tranDescScoreInfo + ", Timestamp: " + new Date().getTime() + "}");
				String lockBy = ((MstScorerInfo) session.get("scorerInfo")).getScorerId();
				TranDescScoreInfo tranDescScoreInfo1 = answerRecordsList.get(prevRecordCount - 1);
				Integer answerSeq = tranDescScoreInfo1.getAnswerInfo().getAnswerSeq();
				scoreService.unlockAnswer(questionInfo.getQuestionSeq(), lockBy, questionInfo.getConnectionString(),
						answerSeq);
			}
		}

		session.put("tranDescScoreInfo", tranDescScoreInfo);
		log.info(scorerInfo.getScorerId() + "-" + menuId + "-" + "Record loaded." + "-{ Question Sequence: "
				+ questionInfo.getQuestionSeq() + "\n TranDescScoreInfo: " + tranDescScoreInfo + ", Timestamp: "
				+ new Date().getTime() + "}");

		if (ArrayUtils.contains(WebAppConst.MISMATCH_STATES, tranDescScoreInfo.getScoringState())
				|| (ArrayUtils.contains(WebAppConst.DENY_SCORING_STATES, tranDescScoreInfo.getScoringState())
						&& (questionInfo.getQuestionType() == WebAppConst.SPEAKING_TYPE
								|| questionInfo.getQuestionType() == WebAppConst.WRITING_TYPE))) {
			// Get mismatch data for mismatch states
			getMismatchScoreActionData();
		}
	}

	@SuppressWarnings("unchecked")
	private String buildTranDescScoreInfo(ScoreSamplingInfo scoreSamplingInfo) {

		MstScorerInfo scorerInfo = ((MstScorerInfo) session.get("scorerInfo"));
		QuestionInfo questionInfo = (QuestionInfo) session.get("questionInfo");

		tranDescScoreInfo = new TranDescScoreInfo();

		List<ScoreSamplingInfo> sessionScoreSamplingInfoList = (List<ScoreSamplingInfo>) session
				.get("scoreSamplingInfoList");
		int recordIndex = sessionScoreSamplingInfoList.indexOf(scoreSamplingInfo);
		ScoreSamplingInfo scoreSamplingInfoObj = sessionScoreSamplingInfoList.get(recordIndex);

		SaitenUtil.copyScoreSamplingInfoToTranDescScoreInfo(scoreSamplingInfoObj, tranDescScoreInfo);
		Integer answerSeq = tranDescScoreInfo.getAnswerInfo().getAnswerSeq();
		Integer lookAftSeq = tranDescScoreInfo.getAnswerInfo().getLookAftSeq();
		if (answerSeq != null && lookAftSeq != null) {
			Map<String, Object> commentMap = lookAfterwardsService.fetchCommentsByAnswerSeq(answerSeq,
					questionInfo.getConnectionString());
			Object count = commentMap.get("lookAfterwardsCount");
			if (count != null) {
				tranDescScoreInfo.setLookAfterwardsCount((Integer) count);
			}
			Object comments = commentMap.get("comments");
			if (comments != null) {
				if (!StringUtils.isBlank((String) comments)) {
					tranDescScoreInfo.setLookAfterwardsComments((String) comments);
				}
			}
		}

		boolean answerAlreadyScored = scoreService.isAnswerAlreadyScored(
				tranDescScoreInfo.getAnswerInfo().getAnswerSeq(), questionInfo.getConnectionString(),
				tranDescScoreInfo.getAnswerInfo().getUpdateDate());
		if (!answerAlreadyScored) {
			Date updateDate = new Date();
			scoreService.lockAnswer(tranDescScoreInfo.getAnswerInfo().getAnswerSeq(), scorerInfo.getScorerId(),
					questionInfo.getConnectionString(), updateDate);
			tranDescScoreInfo.getAnswerInfo().setUpdateDate(updateDate);
		} else {
			// this flag use on 'answerNotAvailable.jsp' for displaying
			// appropriate error message.
			answerAlreadyScoredFlag = answerAlreadyScored;
			return FAILURE;
		}

		log.info(scorerInfo.getScorerId() + "-" + questionInfo.getMenuId() + "-" + "Record loaded."
				+ "-{ Question Sequence: " + questionInfo.getQuestionSeq() + "\n TranDescScoreInfo: "
				+ tranDescScoreInfo + "}");
		session.put("tranDescScoreInfo", tranDescScoreInfo);

		if (ArrayUtils.contains(WebAppConst.MISMATCH_STATES, tranDescScoreInfo.getScoringState())) {

			// bitValue will be null for mismatch answer. so remove
			// selectedCheckPointList from session.
			session.remove("selectedCheckPointList");

			// Get mismatch data for mismatch states
			getMismatchScoreActionData();
		} else if (ArrayUtils.contains(WebAppConst.DENY_SCORING_STATES, tranDescScoreInfo.getScoringState())
				&& (questionInfo.getQuestionType() == WebAppConst.SPEAKING_TYPE
						|| questionInfo.getQuestionType() == WebAppConst.WRITING_TYPE)) {
			getMismatchScoreActionData();
			getScoreSearchActionData(questionInfo.getQuestionSeq());
		} else {
			getScoreSearchActionData(questionInfo.getQuestionSeq());
		}

		return SUCCESS;
	}

	private void getMismatchScoreActionData() {
		QuestionInfo questionInfo = (QuestionInfo) session.get("questionInfo");
		// ex: 121->122->131->133->161->162
		// As per above ex :firstTimeSelectedCheckPointList contains checkpoints
		// of 122 state
		// As per above ex :secondTimeSelectedCheckPointList contains
		// checkpoints of 162 state
		List<Short> firstTimeSelectedCheckPointList = null;
		List<Short> secondTimeSelectedCheckPointList = null;
		Short currentScoringState = tranDescScoreInfo.getScoringState();
		List<Double> bitValueList = scoreService.getFirstTimeSecondTimeCheckPoints(
				tranDescScoreInfo.getAnswerInfo().getAnswerSeq(), questionInfo, currentScoringState);
		if (!bitValueList.isEmpty() && bitValueList != null) {
			Double firstTimeBitValue = null;
			Double secondTimeBitValue = null;
			if ((ArrayUtils.contains(WebAppConst.DENY_SCORING_STATES, currentScoringState))
					&& (questionInfo.getQuestionType() == WebAppConst.SPEAKING_TYPE
							|| questionInfo.getQuestionType() == WebAppConst.WRITING_TYPE)) {
				if (bitValueList.size() > WebAppConst.TWO) {
					firstTimeBitValue = bitValueList.get(WebAppConst.TWO);
				}
			} else {
				firstTimeBitValue = bitValueList.get(WebAppConst.ONE);
			}

			if (firstTimeBitValue != null) {
				firstTimeSelectedCheckPointList = SaitenUtil.getSelectedCheckPointList(firstTimeBitValue);
			}
			if ((ArrayUtils.contains(WebAppConst.DENY_SCORING_STATES, currentScoringState))
					&& (questionInfo.getQuestionType() == WebAppConst.SPEAKING_TYPE
							|| questionInfo.getQuestionType() == WebAppConst.WRITING_TYPE)) {
				if (bitValueList.size() > WebAppConst.ONE) {
					secondTimeBitValue = bitValueList.get(WebAppConst.ONE);
				} else {
					secondTimeBitValue = bitValueList.get(WebAppConst.ZERO);
				}
			} else {
				secondTimeBitValue = bitValueList.get(WebAppConst.ZERO);
			}
			if (secondTimeBitValue != null) {
				secondTimeSelectedCheckPointList = SaitenUtil.getSelectedCheckPointList(secondTimeBitValue);
			}

			session.put("firstTimeSelectedCheckPointList", firstTimeSelectedCheckPointList);
			session.put("secondTimeSelectedCheckPointList", secondTimeSelectedCheckPointList);

		} else {
			session.remove("firstTimeSelectedCheckPointList");
			session.remove("secondTimeSelectedCheckPointList");
		}
	}

	private void buildQuestionInfo() {
		QuestionInfo questionInfo = (QuestionInfo) ContextLoader.getCurrentWebApplicationContext()
				.getBean("questionInfo");

		Short specialScoringType = specialScoreInputInfo != null ? specialScoreInputInfo.getSpecialScoringType() : null;

		if (specialScoringType != null) {
			questionInfo.setMenuId(getSpecialScoringMenuId(specialScoringType));
		} else {
			questionInfo.setMenuId(selectedMenuId);
		}

		session.put("questionInfo", questionInfo);
	}

	/**
	 * @param specialScoringType
	 * @return String
	 */
	private String getSpecialScoringMenuId(short specialScoringType) {
		switch (specialScoringType) {
		case 251:
			return WebAppConst.SPECIAL_SCORING_BLIND_TYPE_MENU_ID;
		case 253:
			return WebAppConst.SPECIAL_SCORING_ENLARGE_TYPE_MENU_ID;
		case 255:
			return WebAppConst.SPECIAL_SCORING_OMR_READ_FAIL_MENU_ID;
		case 257:
			return WebAppConst.SPECIAL_SCORING_LANGUAGE_SUPPORT_MENU_ID;
		default:
			return null;
		}
	}

	/**
	 * @param sessionQuestionInfo
	 */
	private void updatePrevNextRecordCounts(QuestionInfo sessionQuestionInfo) {

		if (prevOrNextFlag == WebAppConst.TRUE) {
			sessionQuestionInfo.setPrevRecordCount(sessionQuestionInfo.getPrevRecordCount() - 1);
			sessionQuestionInfo.setNextRecordCount(sessionQuestionInfo.getNextRecordCount() + 1);
		} else {
			sessionQuestionInfo.setPrevRecordCount(sessionQuestionInfo.getPrevRecordCount() + 1);
			sessionQuestionInfo.setNextRecordCount(sessionQuestionInfo.getNextRecordCount() - 1);
		}

		session.put("questionInfo", sessionQuestionInfo);
	}

	/**
	 * @param questionInfo
	 */
	private void updateQuestionInfo(QuestionInfo questionInfo) {
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

		if (sessionQuestionInfo.getMenuId().equals(WebAppConst.SCORE_SAMP_MENU_ID)
				|| sessionQuestionInfo.getMenuId().equals(WebAppConst.FORCED_MENU_ID)) {
			sessionQuestionInfo.setHistoryRecordCount(questionInfo.getHistoryRecordCount());
			sessionQuestionInfo.setPrevRecordCount(questionInfo.getHistoryRecordCount());
			sessionQuestionInfo.setNextRecordCount(WebAppConst.MINUS_ONE);
		} else if (sessionQuestionInfo.getMenuId().equals(WebAppConst.REFERENCE_SAMP_MENU_ID)) {
			sessionQuestionInfo.setPrevRecordCount(WebAppConst.ZERO);
			if (answerRecordsList != null && !answerRecordsList.isEmpty()) {
				sessionQuestionInfo.setNextRecordCount(answerRecordsList.size() - 1);
				sessionQuestionInfo.setHistoryRecordCount(answerRecordsList.size());
			}
		} else if (sessionQuestionInfo.getMenuId().equals(WebAppConst.SPECIAL_SCORING_BLIND_TYPE_MENU_ID)
				|| sessionQuestionInfo.getMenuId().equals(WebAppConst.SPECIAL_SCORING_LANGUAGE_SUPPORT_MENU_ID)) {
			sessionQuestionInfo.setPrevRecordCount(questionInfo.getHistoryRecordCount());
			sessionQuestionInfo.setNextRecordCount(WebAppConst.MINUS_ONE);
			sessionQuestionInfo.setHistoryRecordCount(questionInfo.getHistoryRecordCount());
		}

		sessionQuestionInfo.setCheckPointList(questionInfo.getCheckPointList());
		sessionQuestionInfo.setPendingCategoryGroupMap(questionInfo.getPendingCategoryGroupMap());
		sessionQuestionInfo.setSide(questionInfo.getSide());
		if (sessionQuestionInfo.getScoreType() == null) {
			sessionQuestionInfo.setScoreType(questionInfo.getScoreType());
		}
		if (sessionQuestionInfo.getQuestionType() == null) {
			sessionQuestionInfo.setQuestionType(questionInfo.getQuestionType());
		}

		session.put("questionInfo", sessionQuestionInfo);
	}

	/**
	 * @param questionSeq
	 * @param categoryType
	 * @param pendingCategoryString
	 * @return List
	 */
	@SuppressWarnings("rawtypes")
	private List buildPendingCategorySeqList(Integer questionSeq, Integer categoryType, String pendingCategoryString) {
		List pendingCategorySeqList = null;
		if (categoryType != null && categoryType == 4) {
			String[] pendingCategoryArray = pendingCategoryString.split(WebAppConst.COMMA);

			Short[] pendingCategory = buildPendingCategoryArray(pendingCategoryArray);

			pendingCategorySeqList = scoreSearchService.findPendingCategorySeqList(questionSeq, pendingCategory);
		}

		return pendingCategorySeqList;
	}

	/**
	 * @param questionSeq
	 * @param categoryType
	 * @param denyCategoryString
	 * @return List
	 */
	@SuppressWarnings("rawtypes")
	private List buildDenyCategorySeqList(Integer questionSeq, Integer categoryType, String denyCategoryString) {
		List pendingCategorySeqList = null;
		if (categoryType != null && categoryType == 5) {
			String[] denyCategoryArray = denyCategoryString.split(WebAppConst.COMMA);

			Short[] denyCategory = buildDenyCategoryArray(denyCategoryArray);

			pendingCategorySeqList = scoreSearchService.findDenyCategorySeqList(questionSeq, denyCategory);
		}

		return pendingCategorySeqList;
	}

	/**
	 * @param questionSeq
	 * @param categoryType
	 * @param gradeNum
	 * @return List
	 */
	@SuppressWarnings("rawtypes")
	private List buildGradeSeqList(Integer questionSeq, Integer categoryType, Integer[] gradeNum) {
		List gradeSeqList = null;
		if (categoryType != null && categoryType == 3) {
			gradeSeqList = scoreSearchService.findGradeSeqList(questionSeq, gradeNum);
		}

		return gradeSeqList;
	}

	/**
	 * @param pendingCategoryArray
	 * @return Short[]
	 */
	private Short[] buildPendingCategoryArray(String[] pendingCategoryArray) {
		Short[] pendingCategory = new Short[pendingCategoryArray.length];

		for (int i = 0; i < pendingCategoryArray.length; i++) {
			pendingCategory[i] = Short.valueOf(pendingCategoryArray[i]);
		}

		return pendingCategory;
	}

	/**
	 * @param denyCategoryArray
	 * @return Short[]
	 */
	private Short[] buildDenyCategoryArray(String[] denyCategoryArray) {
		Short[] denyCategory = new Short[denyCategoryArray.length];

		for (int i = 0; i < denyCategoryArray.length; i++) {
			denyCategory[i] = Short.valueOf(denyCategoryArray[i]);
		}

		return denyCategory;
	}

	public String omrEnlargeScoreSearch() {
		try {
			SaitenUtil.clearSessionInfo();

			MstScorerInfo scorerInfo = ((MstScorerInfo) session.get("scorerInfo"));
			String scorerId = scorerInfo.getScorerId();
			String subjectCode = specialScoreInputInfo.getSubjectCode();
			String answerFormNum = specialScoreInputInfo.getAnswerFormNum();

			session.put("answerFormNum", answerFormNum);
			session.put("specialScoreInputInfo", specialScoreInputInfo);
			log.info(scorerInfo.getScorerId() + "-" + "Search Criteria: -{ " + specialScoreInputInfo + "}");
			String menuId = null;
			if (SaitenUtil.isEvaluationInProgress(scorerId, subjectCode, answerFormNum, menuId)) {
				return DECLINE;
			}

			// Fetch questionSeqList from subjectCode (questionNum will be null
			// for OMR and Enlarge options)
			List<Integer> questionSeqList = scoreSearchService.findQuestionSeq(subjectCode,
					specialScoreInputInfo.getQuestionNum());

			Short specialScoringType = specialScoreInputInfo.getSpecialScoringType();

			// Get menuId corresponding to specialScoringType
			menuId = getSpecialScoringMenuId(specialScoringType);

			// Fetch questionInfoMap corresponding to questionSeqList
			questionInfoMap = questionSelectionService.fetchDbInstanceInfo(questionSeqList, menuId);
			session.put("questionInfoMap", questionInfoMap);

			QuestionInfo questionInfo = questionInfoMap.get(questionInfoMap.keySet().toArray()[0]);
			session.put("questionInfo", questionInfo);

			// Fetch checkPointList for all questionInfo objects in
			// questionInfoMap
			fetchCheckPointList();

			Map<String, List<Integer>> questionSeqMap = buildDbSpecificQuestionSeqMap();
			session.put("dbSpecificQuestionSeqMap", questionSeqMap);

			Integer historyRecordCount = fetchHistoryRecordCount(questionSeqMap, scorerInfo);
			session.put("historyRecordCount", historyRecordCount);
			session.put("prevRecordCount", historyRecordCount);
			session.put("nextRecordCount", (Integer) WebAppConst.MINUS_ONE.intValue());

			Integer questionSeq = null;
			fetchAnswerRecordsList(questionSeq, questionInfo, questionSeqMap, specialScoreInputInfo.getAnswerFormNum(),
					historyRecordCount);
		} catch (SaitenRuntimeException we) {
			throw we;
		} catch (Exception e) {
			throw new SaitenRuntimeException(ErrorCode.SPECIAL_SCORE_SEARCH_ACTION_EXCEPTION, e);
		}

		return !answerRecordsList.isEmpty() ? SUCCESS : FAILURE;
	}

	@SuppressWarnings("unchecked")
	public String updateSpecialScoringMap() {
		try {
			QuestionInfo questionInfo = (QuestionInfo) session.get("questionInfo");
			String menuId = questionInfo.getMenuId();

			if (!(menuId.equals(WebAppConst.SPECIAL_SCORING_BLIND_TYPE_MENU_ID)
					|| menuId.equals(WebAppConst.SPECIAL_SCORING_LANGUAGE_SUPPORT_MENU_ID))) {
				/*
				 * String subjectCode = questionInfo.getSubjectCode(); String
				 * answerFormNumber = (String) session.get("answerFormNum");
				 */

				SaitenUtil.updateSpecialScoringMap(/*
													 * subjectCode,
													 * answerFormNumber
													 */);
			}

			String lockFlag = ServletActionContext.getRequest().getParameter("lockFlag");
			Integer questionSeq = null;

			if (!StringUtils.isBlank(lockFlag) && Boolean.valueOf(lockFlag) == WebAppConst.TRUE) {
				if (!(menuId.equals(WebAppConst.SPECIAL_SCORING_BLIND_TYPE_MENU_ID)
						|| menuId.equals(WebAppConst.SPECIAL_SCORING_LANGUAGE_SUPPORT_MENU_ID))) {
					tranDescScoreInfo = (TranDescScoreInfo) session.get("tranDescScoreInfo");

					questionSeq = tranDescScoreInfo.getAnswerInfo().getQuestionSeq();
					Map<Integer, QuestionInfo> questionInfoMap = (Map<Integer, QuestionInfo>) session
							.get("questionInfoMap");

					questionInfo = questionInfoMap.get(questionSeq);
				} else {
					questionSeq = questionInfo.getQuestionSeq();
				}

				String connectionString = questionInfo.getConnectionString();
				String lockBy = ((MstScorerInfo) session.get("scorerInfo")).getScorerId();

				// unlock answer
				Integer answerSeq = null;
				UnlockAnswerUtil.unlockAnswer(questionSeq, lockBy, connectionString, answerSeq);
				TranScorerSessionInfo tranScorerSessionInfo = (TranScorerSessionInfo) session
						.get("tranScorerSessionInfo");
				if (tranScorerSessionInfo != null) {
					tranScorerSessionInfo.setQuestionSeq(null);
					tranScorerSessionInfo.setUpdateDate(new Date());
					// clears question_seq from tran_scorer_session_info table.
					ApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
					((SaitenMasterUtil) ctx.getBean("saitenMasterUtil")).updateUserSessionInfo(tranScorerSessionInfo);
					// put updated tranScorerSessionInfo in session.
					session.put("tranScorerSessionInfo", tranScorerSessionInfo);
				}

			}

		} catch (SaitenRuntimeException we) {
			throw we;
		} catch (Exception e) {
			throw new SaitenRuntimeException(ErrorCode.SPECIAL_SCORE_SEARCH_ACTION_EXCEPTION, e);
		}

		return SUCCESS;
	}

	/**
	 * @param questionSeqMap
	 * @param menuId
	 * @param scorerId
	 * @param answerFormNum
	 * @param historyRecordCount
	 * @return List<TranDescScoreInfo>
	 */
	private List<TranDescScoreInfo> fetchSpecialScoringAnswerRecordList(Map<String, List<Integer>> questionSeqMap,
			String menuId, String scorerId, String answerFormNum, Integer historyRecordCount) {

		MstScorerInfo scorerInfo = ((MstScorerInfo) session.get("scorerInfo"));
		int roleId = scorerInfo.getRoleId();
		String subjectCode = specialScoreInputInfo.getSubjectCode();
		Set<String> connectionStringSet = null;
		LinkedHashMap<Integer, MstQuestion> mstQuestionMap = null;
		List<Integer> questionSeqList = new ArrayList<Integer>();
		List<TranDescScoreInfo> tranDescScoreInfoList = null;

		List<TranDescScoreInfo> answerRecordList = new ArrayList<TranDescScoreInfo>();
		Short questionNum = specialScoreInputInfo.getQuestionNum();
		if (!(questionNum == null)) {
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
			 * mstScorerQuestionMap.get(scorerId);
			 */
			mstQuestionMap = SaitenUtil.getMstQuestionMapByScorerId(scorerId);

			// This IF block for role_id= ADMIN & Q.NO and ANSWER_FORM_NUM both
			// entered case
			if (roleId == WebAppConst.ADMIN_ROLE_ID) {

				connectionStringSet = questionSeqMap.keySet();
				for (String connectionString : connectionStringSet) {
					questionSeqList = questionSeqMap.get(connectionString);

					if ((questionSeqList != null) && (!questionSeqList.isEmpty())) {
						tranDescScoreInfoList = scoreSearchService.findSpecialScoringAnswerRecords(questionSeqList,
								menuId, scorerId, connectionString, answerFormNum, historyRecordCount, roleId);
						if (tranDescScoreInfoList != null && !tranDescScoreInfoList.isEmpty())
							answerRecordList.addAll(tranDescScoreInfoList);
					}

				}

			}
			// This Else block for role_id = Normal Scorer & Q.NO and
			// ANSWER_FORM_NUM both entered
			else {

				for (MstQuestion mstQuestion : mstQuestionMap.values()) {
					if ((mstQuestion.getMstSubject().getSubjectCode().equals(subjectCode))
							&& (mstQuestion.getQuestionNum() == questionNum)) {
						Integer questionSeq = mstQuestion.getQuestionSeq();
						String connectionString = mstQuestion.getMstDbInstance().getConnectionString();
						questionSeqList.add(questionSeq);

						if ((questionSeqList != null) && (!questionSeqList.isEmpty())) {
							tranDescScoreInfoList = scoreSearchService.findSpecialScoringAnswerRecords(questionSeqList,
									menuId, scorerId, connectionString, answerFormNum, historyRecordCount, roleId);
							if (tranDescScoreInfoList != null && !tranDescScoreInfoList.isEmpty())
								answerRecordList.addAll(tranDescScoreInfoList);

						}
						// current question sequence should remove from list,
						// since current question sequence related records are
						// added in answerRecordList.
						questionSeqList.remove(questionSeq);
					}
				}
			}

		} else {

			if (roleId == WebAppConst.ADMIN_ROLE_ID) {
				connectionStringSet = questionSeqMap.keySet();
			} else {
				questionSeqMap = buildLogedInScorerIdDbSpecificQuestionSeqMap(scorerId, subjectCode);
				connectionStringSet = questionSeqMap.keySet();
			}
			for (String connectionString : connectionStringSet) {
				questionSeqList = questionSeqMap.get(connectionString);

				if ((questionSeqList != null) && (!questionSeqList.isEmpty())) {
					tranDescScoreInfoList = scoreSearchService.findSpecialScoringAnswerRecords(questionSeqList, menuId,
							scorerId, connectionString, answerFormNum, historyRecordCount, roleId);
					if (tranDescScoreInfoList != null && !tranDescScoreInfoList.isEmpty())
						answerRecordList.addAll(tranDescScoreInfoList);
				}

			}
		}

		return answerRecordList;
	}

	/**
	 * @param questionSeqMap
	 * @param scorerInfo
	 * @return int
	 */
	private int fetchHistoryRecordCount(Map<String, List<Integer>> questionSeqMap, MstScorerInfo scorerInfo) {
		Set<String> connectionStringSet = questionSeqMap.keySet();

		List<Short> scoringStateList = SaitenUtil.buildScoringStateList(scorerInfo.getNoDbUpdate());

		int historyRecordCount = 0;
		for (String connectionString : connectionStringSet) {
			List<Integer> questionSeqList = questionSeqMap.get(connectionString);
			if (!questionSeqList.isEmpty()) {
				historyRecordCount += questionSelectionService.findHistoryRecordCount(scorerInfo.getScorerId(),
						questionSeqList, connectionString, scoringStateList);
			}
		}

		return historyRecordCount;
	}

	@SuppressWarnings("unchecked")
	private Map<String, List<Integer>> buildDbSpecificQuestionSeqMap() {
		LinkedHashMap<String, HibernateTemplate> hibernateTemplateMap = (LinkedHashMap<String, HibernateTemplate>) ServletActionContext
				.getServletContext().getAttribute("hibernateTemplateMap");
		Set<String> connectionStringSet = hibernateTemplateMap.keySet();

		Map<String, List<Integer>> questionSeqMap = new LinkedHashMap<String, List<Integer>>();

		String masterDbUrl = saitenApplicationProperties.getProperty(WebAppConst.SAITEN_MASTERDB_URL);
		for (String connectionString : connectionStringSet) {
			if (!connectionString.equals(masterDbUrl)) {
				questionSeqMap.put(connectionString, new ArrayList<Integer>());
			}
		}

		Set<Integer> questionSeqSet = questionInfoMap.keySet();
		for (Integer questionSeq : questionSeqSet) {
			QuestionInfo questionInfo = questionInfoMap.get(questionSeq);
			List<Integer> dbSpecificQuestionSeqList = questionSeqMap.get(questionInfo.getConnectionString());
			dbSpecificQuestionSeqList.add(questionInfo.getQuestionSeq());
		}

		return questionSeqMap;
	}

	@SuppressWarnings("unchecked")
	private Map<String, List<Integer>> buildLogedInScorerIdDbSpecificQuestionSeqMap(String scorerId,
			String subjectCode) {
		LinkedHashMap<String, HibernateTemplate> hibernateTemplateMap = (LinkedHashMap<String, HibernateTemplate>) ServletActionContext
				.getServletContext().getAttribute("hibernateTemplateMap");
		Set<String> connectionStringSet = hibernateTemplateMap.keySet();
		LinkedHashMap<Integer, MstQuestion> mstQuestionMap = null;
		List<Integer> loggedInScorerQuestionSeqList = new ArrayList<Integer>();
		Map<String, List<Integer>> dbSpecificQuestionSeqMap = new LinkedHashMap<String, List<Integer>>();
		String masterDbUrl = saitenApplicationProperties.getProperty(WebAppConst.SAITEN_MASTERDB_URL);
		for (String connectionString : connectionStringSet) {
			if (!connectionString.equals(masterDbUrl)) {
				dbSpecificQuestionSeqMap.put(connectionString, new ArrayList<Integer>());
			}
		}

		/*
		 * LinkedHashMap<String, LinkedHashMap<Integer, MstQuestion>>
		 * mstScorerQuestionMap = SaitenUtil
		 * .getSaitenConfigObject().getMstScorerQuestionMap();
		 */
		/*
		 * LinkedHashMap<String, LinkedHashMap<Integer, MstQuestion>>
		 * mstScorerQuestionMap = (LinkedHashMap<String, LinkedHashMap<Integer,
		 * MstQuestion>>) SaitenUtil .getSession().get("mstScorerQuestionMap");
		 * mstQuestionMap = mstScorerQuestionMap.get(scorerId);
		 */
		mstQuestionMap = SaitenUtil.getMstQuestionMapByScorerId(scorerId);

		for (MstQuestion mstQuestion : mstQuestionMap.values()) {
			if (mstQuestion.getMstSubject().getSubjectCode().equals(subjectCode)) {
				Integer questionSeq = mstQuestion.getQuestionSeq();
				String connectionString = mstQuestion.getMstDbInstance().getConnectionString();
				loggedInScorerQuestionSeqList = dbSpecificQuestionSeqMap.get(connectionString);
				loggedInScorerQuestionSeqList.add(questionSeq);
			}
		}

		return dbSpecificQuestionSeqMap;
	}

	private void fetchCheckPointList() {
		Set<Integer> questionSeqSet = questionInfoMap.keySet();
		for (Integer questionSeq : questionSeqSet) {
			QuestionInfo questionInfo = questionInfoMap.get(questionSeq);
			questionInfo.setCheckPointList(questionSelectionService.findCheckPoints(questionInfo.getQuestionSeq()));
		}
	}

	public String specialScoreSearch() {
		try {

			MstScorerInfo scorerInfo = ((MstScorerInfo) session.get("scorerInfo"));
			QuestionInfo sessionQuestionInfo = (QuestionInfo) session.get("questionInfo");
			String answerFormNum = specialScoreInputInfo.getAnswerFormNum();
			session.put("answerFormNum", answerFormNum);
			log.info(scorerInfo.getScorerId() + "-" + sessionQuestionInfo.getMenuId() + "-" + "Search Criteria: -{ "
					+ specialScoreInputInfo + "}");

			// Fetch questionSeq from subjectCode & questionNum
			List<Integer> questionSeqList = scoreSearchService.findQuestionSeq(specialScoreInputInfo.getSubjectCode(),
					specialScoreInputInfo.getQuestionNum());
			Integer questionSeq = !questionSeqList.isEmpty() ? questionSeqList.get(0) : null;

			sessionQuestionInfo.setQuestionSeq(questionSeq);
			session.put("questionInfo", sessionQuestionInfo);
			// Display record not available message, if questionSeq == null
			if (questionSeq == null) {
				sessionQuestionInfo.setQuestionSeq(WebAppConst.ZERO);

				session.put("questionInfo", sessionQuestionInfo);
				session.put("specialScoreInputInfo", specialScoreInputInfo);
				return FAILURE;
			}
			// Fetch DbInstanceInfo and questionInfo based on questionSeq

			Short specialScoringType = specialScoreInputInfo.getSpecialScoringType();

			// Get menuId corresponding to specialScoringType
			String menuId = getSpecialScoringMenuId(specialScoringType);

			// Fetch DbInstanceInfo and questionInfo based on questionSeq
			questionInfoMap = questionSelectionService.fetchDbInstanceInfo(questionSeqList, menuId);
			session.put("questionInfoMap", questionInfoMap);

			QuestionInfo questionInfo = questionInfoMap.get(questionInfoMap.keySet().toArray()[0]);
			session.put("questionInfo", questionInfo);
			session.put("specialScoreInputInfo", specialScoreInputInfo);

			// Set MenuId to QuestionInfo & put QuestionInfo in session
			buildQuestionInfo();

			// Get checkPointList for selected question
			questionInfo.setCheckPointList(questionSelectionService.findCheckPoints(questionSeq));

			List<Short> scoringStateList = SaitenUtil.buildScoringStateList(scorerInfo.getNoDbUpdate());

			// Fetch historyRecordCount for selected question
			List<Integer> questionSequenceList = new ArrayList<Integer>();
			questionSequenceList.add(questionSeq);

			questionInfo.setHistoryRecordCount(questionSelectionService.findHistoryRecordCount(scorerInfo.getScorerId(),
					questionSequenceList, questionInfo.getConnectionString(), scoringStateList));
			if (menuId.equals(WebAppConst.SPECIAL_SCORING_ENLARGE_TYPE_MENU_ID)
					|| menuId.equals(WebAppConst.SPECIAL_SCORING_OMR_READ_FAIL_MENU_ID)) {
				Map<String, List<Integer>> questionSeqMap = buildDbSpecificQuestionSeqMap();
				session.put("dbSpecificQuestionSeqMap", questionSeqMap);
				Integer historyRecordCount = fetchHistoryRecordCount(questionSeqMap, scorerInfo);
				session.put("historyRecordCount", historyRecordCount);
				session.put("prevRecordCount", historyRecordCount);
				session.put("nextRecordCount", (Integer) WebAppConst.MINUS_ONE.intValue());
				updateQuestionInfo(questionInfo);
				fetchAnswerRecordsList(questionSeq, questionInfo, questionSeqMap,
						specialScoreInputInfo.getAnswerFormNum(), historyRecordCount);

			} else {

				updateQuestionInfo(questionInfo);

				// historyRecordCount - For Special scoring
				Integer historyRecordCount = null;
				// no markValue selected for special scoring.
				Short selectedMarkValue = null;
				Short pendingCategory = SaitenUtil.getPendingCategoryByPendingCategorySeq(pendingCategorySeq);
				System.out.println(denyCategorySeq);
				Short denyCategory = SaitenUtil.getDenyCategoryByDenyCategorySeq(denyCategorySeq);
				// Find answer record
				tranDescScoreInfo = scoreService.findAnswer(questionInfo.getQuestionSeq(), menuId,
						scorerInfo.getScorerId(), questionInfo.getConnectionString(), gradeNum, pendingCategory,
						denyCategory, specialScoreInputInfo.getAnswerFormNum(), historyRecordCount,
						scorerInfo.getRoleId(), selectedMarkValue, questionInfo, bitValue);

				if (tranDescScoreInfo != null) {
					log.info(scorerInfo.getScorerId() + "-" + menuId + "-" + "Record loaded." + "-{ Question Sequence: "
							+ questionInfo.getQuestionSeq() + "\n TranDescScoreInfo: " + tranDescScoreInfo + "}");
					Date updateDate = new Date();
					// Lock selected answer record
					scoreService.lockAnswer(tranDescScoreInfo.getAnswerInfo().getAnswerSeq(), scorerInfo.getScorerId(),
							questionInfo.getConnectionString(), updateDate);

					getScoreSearchActionData(questionSeq);
					// put tranDescScoreInfo in session
					buildTranDescScoreInfo();

				}
			}
		} catch (SaitenRuntimeException we) {
			throw we;
		} catch (Exception e) {
			throw new SaitenRuntimeException(ErrorCode.SPECIAL_SCORE_SEARCH_ACTION_EXCEPTION, e);
		}

		return tranDescScoreInfo != null ? SUCCESS : FAILURE;
	}

	private void buildTranDescScoreInfo() {
		TranDescScoreInfo tranDescScoreInfoCopy = (TranDescScoreInfo) session.get("tranDescScoreInfoCopy");

		if (tranDescScoreInfo.getAnswerInfo().getHistorySeq() != null && tranDescScoreInfoCopy == null) {

			// Create copy of tranDescScoreInfo for history records
			TranDescScoreInfo sessionTranDescScoreInfo = (TranDescScoreInfo) session.get("tranDescScoreInfo");
			if (sessionTranDescScoreInfo != null && sessionTranDescScoreInfo.getAnswerInfo().getHistorySeq() == null) {
				session.put("tranDescScoreInfoCopy", sessionTranDescScoreInfo);
			}

		}
		session.put("tranDescScoreInfo", tranDescScoreInfo);
	}

	public String loadForcedScoringList() {
		int count = 0;
		session.put("count", count); // list count 0

		try {
			QuestionInfo sessionQuestionInfo = (QuestionInfo) session.get("questionInfo");
			MstScorerInfo scorerInfo = ((MstScorerInfo) session.get("scorerInfo"));
			if (session.get("pageNumber") != null) {
				session.remove("pageNumber");
			}

			// get record list record count
			scoreInputInfo = (ScoreInputInfo) session.get("scoreInputInfo");
			// fetchRecordsCount();
			log.info(scorerInfo.getScorerId() + "-" + sessionQuestionInfo.getMenuId() + "-"
					+ "Search list page loading. Search Criteria: -{ " + scoreInputInfo + " } ");
			count = (Integer) session.get("searchResultCount");
			if (sessionQuestionInfo.getMenuId().equals(WebAppConst.STATE_TRAN_MENU_ID)) {
				Integer updatedCount = (Integer) session.get("updatedCount");
				if ((updatedCount != null) && (updatedCount > WebAppConst.ZERO)) {
					count = count - updatedCount;
					session.put("searchResultCount", count);
					session.remove("updatedCount");
				}
				session.remove("selectAllFlag");
			}

			if (!(sessionQuestionInfo.getMenuId().equals(WebAppConst.STATE_TRAN_MENU_ID))) {
				if (recordCount != null) {
					count = (Integer) recordCount;
				}
			}
			session.put("count", count);
			setSessionAttributeForDispalyTag();

			if (count != WebAppConst.ZERO) {
				return forcedScoringPagination();
			}
		} catch (SaitenRuntimeException we) {
			throw we;
		} catch (Exception e) {
			throw new SaitenRuntimeException(ErrorCode.SPECIAL_SCORE_SEARCH_ACTION_EXCEPTION, e);
		}
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String forcedScoringPagination() {
		Integer startRecord = 0;
		Integer endRecord = Integer.parseInt(getText(WebAppConst.FORCED_AND_STATETRANSITION_LIST_PAGESIZE));
		try {
			String a = (String) session.get("pageNumber");
			String pageNumber = request.getParameter(
					(new ParamEncoder("scoreSamplingInfo").encodeParameterName(TableTagParameters.PARAMETER_PAGE)));

			if (!StringUtils.isBlank(pageNumber)) {
				a = pageNumber;
			}
			if (!StringUtils.isBlank(a)) {
				startRecord = (Integer.parseInt(a) - 1)
						* Integer.parseInt(getText(WebAppConst.FORCED_AND_STATETRANSITION_LIST_PAGESIZE));
				session.put("pageNumber", a);
			}

			QuestionInfo questionInfo = (QuestionInfo) session.get("questionInfo");
			MstScorerInfo mstScorerInfo = (MstScorerInfo) session.get("scorerInfo");
			String scorerId = mstScorerInfo.getScorerId();
			Boolean forceAndStateTransitionFlag = WebAppConst.TRUE;
			scoreInputInfo = (ScoreInputInfo) session.get("scoreInputInfo");
			scoreSamplingInfoList = (List<ScoreSamplingInfo>) scoreSearchService.searchAnswerRecords(questionInfo,
					scoreInputInfo, scorerId, forceAndStateTransitionFlag, startRecord, endRecord);

			if (scoreSamplingInfoList != null && !scoreSamplingInfoList.isEmpty()) {
				session.put("scoreSamplingInfoList", scoreSamplingInfoList);
			}

		} catch (SaitenRuntimeException we) {
			throw we;
		} catch (Exception e) {
			throw new SaitenRuntimeException(ErrorCode.SPECIAL_SCORE_SEARCH_ACTION_EXCEPTION, e);
		}
		return SUCCESS;
	}

	// After scores the forced scoring record, call this method, and redirect to
	// forcedScoringpagination.
	public String backToForcedScoringList() {

		// First remove the session Info's while coming back to list.
		session.remove("tranDescScoreInfo");
		session.remove("tranDescScoreInfoCopy");
		session.remove("historyScreenFlag");

		QuestionInfo questionInfo = (QuestionInfo) session.get("questionInfo");

		Map<String, List<Integer>> questionSeqMap = null;
		String answerFormNum = null;
		Integer historyRecordCount = null;

		try {
			if (questionInfo != null) {

				String lockBy = ((MstScorerInfo) session.get("scorerInfo")).getScorerId();

				if (questionInfo.getConnectionString() != null && questionInfo.getQuestionSeq() != WebAppConst.ZERO) {
					// unlock the all locked answers by logged in scorer, before
					// coming back to list page.
					Integer answerSeq = null;
					scoreService.unlockAnswer(questionInfo.getQuestionSeq(), lockBy, questionInfo.getConnectionString(),
							answerSeq);
				}

				forceAndStateTransitionFlag = WebAppConst.FALSE;
				scoreInputInfo = (ScoreInputInfo) session.get("scoreInputInfo");
				fetchAnswerRecordsList(questionInfo.getQuestionSeq(), questionInfo, questionSeqMap, answerFormNum,
						historyRecordCount);

				int count = 0;
				if (session.get("searchResultCount").equals(WebAppConst.ZERO)) {
					count = (Integer) session.get("searchResultCount");
				} else {
					count = (Integer) session.get("searchResultCount");
				}

				if (recordCount != null) {
					count = (Integer) Integer.parseInt(recordCount.toString());
				}

				session.put("count", count);
				setSessionAttributeForDispalyTag();
				log.info(lockBy + "-" + questionInfo.getMenuId() + "-"
						+ "Search list page loading. Search Criteria: -{ " + scoreInputInfo + " } ");
			}

			String a = (String) session.get("pageNumber");
			if (!StringUtils.isBlank(a)) {
				int startRecord = (Integer.parseInt(a) - 1) * Integer.parseInt(getText(WebAppConst.BOOKMARK_PAGESIZE));
				Integer count = (Integer) session.get("recordCount");
				if (startRecord > 0 && startRecord == count) {
					session.put("pageNumber", Integer.toString(Integer.parseInt(a) - 1));
					pageNumber = session.get("pageNumber").toString();
				} else {
					session.put("pageNumber", a);
					pageNumber = a;
				}
			} else {
				// pageNumber will be '1' for a=null
				pageNumber = "1";
			}

			forceAndStateTransitionFlag = WebAppConst.TRUE;
		} catch (SaitenRuntimeException we) {
			throw we;
		} catch (Exception e) {
			throw new SaitenRuntimeException(ErrorCode.SPECIAL_SCORE_SEARCH_ACTION_EXCEPTION, e);
		}
		return SUCCESS;
	}

	public String validateQuestionNum() {
		try {
			if (session.get("scorerInfo") == null) {
				// Return statusCode as a JSON response for invalid session
				result = SaitenUtil.getAjaxCallStatusCode(session);
				return SUCCESS;
			}

			result = SaitenUtil.validateQuestionNum(session, questionNum, subjectCode);
		} catch (Exception e) {
			throw new SaitenRuntimeException(ErrorCode.SCORE_SEARCH_ACTION_EXCEPTION, e);
		}
		return SUCCESS;
	}

	public String findProcessDetails() {
		QuestionInfo questionInfo = (QuestionInfo) session.get("questionInfo");
		try {
			processDetailsList = scoreSearchService.findProcessDetails(answerSequence,
					questionInfo.getConnectionString());
		} catch (SaitenRuntimeException we) {
			throw we;
		} catch (Exception e) {
			throw new SaitenRuntimeException(ErrorCode.SCORE_SEARCH_ACTION_EXCEPTION, e);
		}
		return SUCCESS;
	}

	private void setSessionAttributeForDispalyTag() {
		session.put("pagesize", Integer.parseInt(getText(WebAppConst.FORCED_AND_STATETRANSITION_LIST_PAGESIZE)));
		session.put("recordCount", session.get("count"));
	}

	/**
	 * 
	 * @param session
	 */
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public TranDescScoreInfo getTranDescScoreInfo() {
		return tranDescScoreInfo;
	}

	/**
	 * @param tranDescScoreInfo
	 */
	public void setTranDescScoreInfo(TranDescScoreInfo tranDescScoreInfo) {
		this.tranDescScoreInfo = tranDescScoreInfo;
	}

	public Boolean getPrevOrNextFlag() {
		return prevOrNextFlag;
	}

	/**
	 * @param prevOrNextFlag
	 */
	public void setPrevOrNextFlag(Boolean prevOrNextFlag) {
		this.prevOrNextFlag = prevOrNextFlag;
	}

	public ScoreService getScoreService() {
		return scoreService;
	}

	/**
	 * @param scoreService
	 */
	public void setScoreService(ScoreService scoreService) {
		this.scoreService = scoreService;
	}

	public GradeInfo getGradeInfo() {
		return gradeInfo;
	}

	/**
	 * @param gradeInfo
	 */
	public void setGradeInfo(GradeInfo gradeInfo) {
		this.gradeInfo = gradeInfo;
	}

	public List<TranDescScoreInfo> getAnswerRecordsList() {
		return answerRecordsList;
	}

	/**
	 * @param answerRecordsList
	 */
	public void setAnswerRecordsList(List<TranDescScoreInfo> answerRecordsList) {
		this.answerRecordsList = answerRecordsList;
	}

	public QuestionSelectionService getQuestionSelectionService() {
		return questionSelectionService;
	}

	/**
	 * @param questionSelectionService
	 */
	public void setQuestionSelectionService(QuestionSelectionService questionSelectionService) {
		this.questionSelectionService = questionSelectionService;
	}

	public ScoreSearchService getScoreSearchService() {
		return scoreSearchService;
	}

	/**
	 * @param scoreSearchService
	 */
	public void setScoreSearchService(ScoreSearchService scoreSearchService) {
		this.scoreSearchService = scoreSearchService;
	}

	public ScoreSearchInfo getScoreSearchInfo() {
		return scoreSearchInfo;
	}

	/**
	 * @param scoreSearchInfo
	 */
	public void setScoreSearchInfo(ScoreSearchInfo scoreSearchInfo) {
		this.scoreSearchInfo = scoreSearchInfo;
	}

	public ScoreInputInfo getScoreInputInfo() {
		return scoreInputInfo;
	}

	/**
	 * @param scoreInputInfo
	 */
	public void setScoreInputInfo(ScoreInputInfo scoreInputInfo) {
		this.scoreInputInfo = scoreInputInfo;
	}

	public String getSelectedMenuId() {
		return selectedMenuId;
	}

	/**
	 * @param selectedMenuId
	 */
	public void setSelectedMenuId(String selectedMenuId) {
		this.selectedMenuId = selectedMenuId;
	}

	/**
	 * @return the specialScoreInputInfo
	 */
	public SpecialScoreInputInfo getSpecialScoreInputInfo() {
		return specialScoreInputInfo;
	}

	/**
	 * @param specialScoreInputInfo
	 *            the specialScoreInputInfo to set
	 */
	public void setSpecialScoreInputInfo(SpecialScoreInputInfo specialScoreInputInfo) {
		this.specialScoreInputInfo = specialScoreInputInfo;
	}

	public Map<Integer, QuestionInfo> getQuestionInfoMap() {
		return questionInfoMap;
	}

	/**
	 * @param questionInfoMap
	 */
	public void setQuestionInfoMap(Map<Integer, QuestionInfo> questionInfoMap) {
		this.questionInfoMap = questionInfoMap;
	}

	/**
	 * @param saitenGlobalProperties
	 */
	public void setSaitenApplicationProperties(Properties saitenApplicationProperties) {
		this.saitenApplicationProperties = saitenApplicationProperties;
	}

	public HistoryListingService getHistoryListingService() {
		return historyListingService;
	}

	/**
	 * @param historyListingService
	 */
	public void setHistoryListingService(HistoryListingService historyListingService) {
		this.historyListingService = historyListingService;
	}

	/**
	 * @return the forceAndStateTransitionFlag
	 */
	public Boolean getForceAndStateTransitionFlag() {
		return forceAndStateTransitionFlag;
	}

	/**
	 * @param forceAndStateTransitionFlag
	 *            the forceAndStateTransitionFlag to set
	 */
	public void setForceAndStateTransitionFlag(Boolean forceAndStateTransitionFlag) {
		this.forceAndStateTransitionFlag = forceAndStateTransitionFlag;
	}

	/**
	 * @return the recordCount
	 */
	public Object getRecordCount() {
		return recordCount;
	}

	/**
	 * @param recordCount
	 *            the recordCount to set
	 */
	public void setRecordCount(Object recordCount) {
		this.recordCount = recordCount;
	}

	public void setSaitenGlobalProperties(Properties saitenGlobalProperties) {
		this.saitenGlobalProperties = saitenGlobalProperties;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.struts2.interceptor.ServletRequestAware#setServletRequest(
	 * javax.servlet.http.HttpServletRequest)
	 */
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletRequest getServletRequest() {
		return request;
	}

	/**
	 * @return the scoreSamplingList
	 */
	public List<ScoreSamplingInfo> getScoreSamplingInfoList() {
		return scoreSamplingInfoList;
	}

	/**
	 * @param scoreSamplingList
	 *            the scoreSamplingList to set
	 */
	public void setScoreSamplingInfoList(List<ScoreSamplingInfo> scoreSamplingInfoList) {
		this.scoreSamplingInfoList = scoreSamplingInfoList;
	}

	/**
	 * @return the result
	 */
	public Map<String, Object> getResult() {
		return result;
	}

	/**
	 * @param result
	 *            the result to set
	 */
	public void setResult(Map<String, Object> result) {
		this.result = result;
	}

	/**
	 * @return the selectAllFlag
	 */
	public boolean isSelectAllFlag() {
		return selectAllFlag;
	}

	/**
	 * @param selectAllFlag
	 *            the selectAllFlag to set
	 */
	public void setSelectAllFlag(boolean selectAllFlag) {
		this.selectAllFlag = selectAllFlag;
	}

	public Integer getAnswerSequence() {
		return answerSequence;
	}

	public void setAnswerSequence(Integer answerSequence) {
		this.answerSequence = answerSequence;
	}

	public boolean isAnswerAlreadyScoredFlag() {
		return answerAlreadyScoredFlag;
	}

	public void setAnswerAlreadyScoredFlag(boolean answerAlreadyScoredFlag) {
		this.answerAlreadyScoredFlag = answerAlreadyScoredFlag;
	}

	public String getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Short getQuestionNum() {
		return questionNum;
	}

	public void setQuestionNum(Short questionNum) {
		this.questionNum = questionNum;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public List<String> getLoggedInScorerSubjectList() {
		return loggedInScorerSubjectList;
	}

	public void setLoggedInScorerSubjectList(List<String> loggedInScorerSubjectList) {
		this.loggedInScorerSubjectList = loggedInScorerSubjectList;
	}

	public List<TranDescScoreInfo> getProcessDetailsList() {
		return processDetailsList;
	}

	public void setProcessDetailsList(List<TranDescScoreInfo> processDetailsList) {
		this.processDetailsList = processDetailsList;
	}

	public Integer getDenyCategorySeq() {
		return denyCategorySeq;
	}

	public void setDenyCategorySeq(Integer denyCategorySeq) {
		this.denyCategorySeq = denyCategorySeq;
	}

	/**
	 * @param lookAfterwardsService
	 *            the lookAfterwardsService to set
	 */
	public void setLookAfterwardsService(LookAfterwardsService lookAfterwardsService) {
		this.lookAfterwardsService = lookAfterwardsService;
	}
}
