package com.saiten.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.saiten.exception.SaitenRuntimeException;
import com.saiten.info.GradeInfo;
import com.saiten.info.HistoryInfo;
import com.saiten.info.MstScorerInfo;
import com.saiten.info.QuestionInfo;
import com.saiten.info.SpecialScoreInputInfo;
import com.saiten.info.TranDescScoreInfo;
import com.saiten.service.HistoryListingService;
import com.saiten.service.ScoreService;
import com.saiten.util.ErrorCode;
import com.saiten.util.SaitenUtil;
import com.saiten.util.WebAppConst;

/**
 * @author sachin
 * @version 1.0
 * @created 11-Dec-2012 12:58:57 PM
 */
public class ScoreAction extends ActionSupport implements SessionAware, ServletRequestAware {

	private static Logger log = Logger.getLogger(ScoreAction.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ScoreService scoreService;
	private Properties saitenGlobalProperties;
	private TranDescScoreInfo tranDescScoreInfo;
	private List<TranDescScoreInfo> tranDescInfoList;
	private Map<String, Object> session;
	private Integer historySequence;
	private String connectionString;
	private Integer questionSequence;
	private boolean bookmarkScreenFlag;
	private Boolean prevOrNextFlag;
	private GradeInfo gradeInfo;
	private Integer gradeNum;
	private HttpServletRequest request;
	public static final String FAILURE = "failure";
	private Short pendingCategory;
	private Short denyCategory;
	private String answerFormNum;
	private HistoryListingService historyListingService;
	private String answerFlag;
	private SpecialScoreInputInfo specialScoreInputInfo;
	private Integer qcSeq;
	private Integer answerSequence;
	private Double bitValue;
	private Integer columnSize;
	/*delete*/
	private List<String> numbers = new ArrayList<String>();
	
	public String imageViewTest() {
		System.out.println("Method Start");
		numbers.add("Number 1");
		numbers.add("Number 2");		
		System.out.println("Method End");
		return SUCCESS;
	}
	
	

	@SuppressWarnings({ "unchecked" })
	public String findAnswer() {
		MstScorerInfo scorerInfo = ((MstScorerInfo) session.get("scorerInfo"));
		QuestionInfo questionInfo = (QuestionInfo) session.get("questionInfo");
		
		
		
		
		try {
			Date logActionStartTime = new Date();
			String denyCategoryStr = null;
			if (session.get("denyCategory") != null) {
				denyCategoryStr = session.get("denyCategory").toString();
			}

			if (denyCategoryStr != null && !(denyCategoryStr.isEmpty())) {
				denyCategory = Short.valueOf(denyCategoryStr);
			} else {
				denyCategory = null;
			}
			String menuId = questionInfo.getMenuId();
			SpecialScoreInputInfo specialScoreInputInfo = (SpecialScoreInputInfo) session.get("specialScoreInputInfo");
			tranDescScoreInfo = (TranDescScoreInfo) session.get("tranDescScoreInfo");
			if (tranDescScoreInfo != null) {
				Boolean isQcRecord = false;
				if (session.get("isQcRecord") != null) {
					isQcRecord = (Boolean) session.get("isQcRecord");
				}

				Double bitValue = tranDescScoreInfo.getAnswerInfo().getBitValue();

				if ((WebAppConst.SCORE_TYPE[3].equals(questionInfo.getScoreType()))
						&& (WebAppConst.FIRST_SCORING_MENU_ID.equals(menuId)
								|| WebAppConst.SECOND_SCORING_MENU_ID.equals(menuId)
								|| WebAppConst.PENDING_MENU_ID.equals(menuId)
								|| WebAppConst.OUT_BOUNDARY_MENU_ID.equals(menuId)
								|| WebAppConst.MISMATCH_MENU_ID.equals(menuId)
								|| WebAppConst.SPECIAL_SCORING_BLIND_TYPE_MENU_ID.equals(menuId)
								|| WebAppConst.SPECIAL_SCORING_ENLARGE_TYPE_MENU_ID.equals(menuId)
								|| WebAppConst.SPECIAL_SCORING_LANGUAGE_SUPPORT_MENU_ID.equals(menuId)
								|| WebAppConst.SPECIAL_SCORING_OMR_READ_FAIL_MENU_ID.equals(menuId)
								|| WebAppConst.SCORE_SAMP_MENU_ID.equals(menuId)
								|| WebAppConst.FIRST_SCORING_QUALITY_CHECK_MENU_ID.equals(menuId))
						&& (tranDescScoreInfo.getAnswerInfo().getHistorySeq() == null)
						&& (bookmarkScreenFlag == false)) {
					List<Short> markValueList = tranDescScoreInfo.getMarkValueList();
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

				if (isQcRecord != null && isQcRecord) {
					log.info(scorerInfo.getScorerId() + "-" + menuId + "-" + "Quality record loaded."
							+ "-{ Question Sequence: " + questionInfo.getQuestionSeq() + ", Answer Sequence: "
							+ tranDescScoreInfo.getAnswerInfo().getAnswerSeq() + ", \n TranDescScoreInfo: "
							+ tranDescScoreInfo + "}");
				} else {
					log.info(scorerInfo.getScorerId() + "-" + menuId + "-" + "Tran record loaded."
							+ "-{ Question Sequence: " + questionInfo.getQuestionSeq() + ", Answer Sequence: "
							+ tranDescScoreInfo.getAnswerInfo().getAnswerSeq() + ", \n TranDescScoreInfo: "
							+ tranDescScoreInfo + "}");
				}
			}
			int roleId = scorerInfo.getRoleId();
			Integer questionSequence = null;
			// for writing & speaking, first & second time menu, scorer &
			// supervisor load quality check answers.
			Date logGetQcAnsSeqListStartTime = null;
			Date logGetQcAnsSeqListEndTime = null;
			Date logFindAnswerStartTime = null;
			Date logFindAnswerEndTime = null;
			Date logFindQcAnswerStartTime = null;
			Date logFindQcAnswerEndTime = null;
			if ((menuId.equals(WebAppConst.FIRST_SCORING_MENU_ID) || menuId.equals(WebAppConst.SECOND_SCORING_MENU_ID)
			/*
			 * || menuId.equals(WebAppConst.DENY_MENU_ID) ||
			 * menuId.equals(WebAppConst.PENDING_MENU_ID) ||
			 * menuId.equals(WebAppConst.MISMATCH_MENU_ID) ||
			 * menuId.equals(WebAppConst.NO_GRADE_MENU_ID) || menuId
			 * .equals(WebAppConst.OUT_BOUNDARY_MENU_ID)
			 */) && (roleId == WebAppConst.SCORER_ROLE_ID || roleId == WebAppConst.SV_ROLE_ID)
			/*
			 * && (questionInfo.getQuestionType() == WebAppConst.SPEAKING_TYPE ||
			 * questionInfo .getQuestionType() == WebAppConst.WRITING_TYPE)
			 */) {
				List<Integer> qcAnswerSeqList = new ArrayList<Integer>();
				qcAnswerSeqList = (List<Integer>) session.get("qcAnswerSeqList");
				Boolean loadQcListFlag = (Boolean) session.get("loadQcListFlag");
				if ((qcAnswerSeqList == null || qcAnswerSeqList.isEmpty())
						&& ((loadQcListFlag != null) && (loadQcListFlag == true))) {

					Short selectedMarkValue = (Short) session.get("selectedMarkValue");

					logGetQcAnsSeqListStartTime = new Date();
					qcAnswerSeqList = scoreService.findQcAnsSeqList(questionInfo.getQuestionSeq(),
							scorerInfo.getScorerId(), selectedMarkValue, questionInfo.getConnectionString());
					logGetQcAnsSeqListEndTime = new Date();
					session.remove("loadQcListFlag");
				}
				session.put("qcAnswerSeqList", qcAnswerSeqList);
				Map<String, String> configMap = SaitenUtil.getConfigMap();
				boolean fetchQcAndTranRecordsRandomly = Boolean.valueOf(configMap.get("fetchQcAndTranRecordsRandomly"));
				if (tranDescScoreInfo == null && fetchQcAndTranRecordsRandomly) {
					Short selectedMarkValue = (Short) session.get("selectedMarkValue");
					Integer randomNumberRange = Integer
							.valueOf(saitenGlobalProperties.getProperty(WebAppConst.SINGLE_DIGIT_RANDOM_NUMBER));
					int randomNumber = 10;
					Random randomGenerator = new Random();
					randomNumber = randomGenerator.nextInt(randomNumberRange);
					Integer historyRecordCount = null;
					boolean answerNotAvailable = false;
					boolean isEven = randomNumber % 3 == 0 ? true : false;
					if (!isEven || qcAnswerSeqList.isEmpty()) {
						logFindAnswerStartTime = new Date();
						tranDescScoreInfo = scoreService.findAnswer(questionInfo.getQuestionSeq(), menuId,
								scorerInfo.getScorerId(), questionInfo.getConnectionString(), gradeNum, pendingCategory,
								denyCategory, answerFormNum, historyRecordCount, roleId, selectedMarkValue,
								questionInfo, bitValue);
						logFindAnswerEndTime = new Date();
						if (tranDescScoreInfo == null) {
							answerNotAvailable = true;
						} else {
							log.info(scorerInfo.getScorerId() + "-" + menuId + "-" + "Tran record loaded."
									+ "-{ Question Sequence: " + questionInfo.getQuestionSeq() + ", Answer Sequence: "
									+ tranDescScoreInfo.getAnswerInfo().getAnswerSeq() + ", \n TranDescScoreInfo: "
									+ tranDescScoreInfo + "}");
						}
					}
					if ((isEven || answerNotAvailable) && !qcAnswerSeqList.isEmpty()) {
						logFindQcAnswerStartTime = new Date();
						tranDescScoreInfo = scoreService.findQualityCheckAnswers(qcAnswerSeqList.get(0), menuId,
								scorerInfo.getScorerId(), questionInfo.getConnectionString(), questionInfo);
						logFindQcAnswerEndTime = new Date();
						if (tranDescScoreInfo != null) {
							log.info(scorerInfo.getScorerId() + "-" + menuId + "-" + "Quality record loaded."
									+ "-{ Question Sequence: " + questionInfo.getQuestionSeq() + ", Answer Sequence: "
									+ tranDescScoreInfo.getAnswerInfo().getAnswerSeq() + ", \n TranDescScoreInfo: "
									+ tranDescScoreInfo + "}");
							session.put("isQcRecord", true);
						}

					}
					if (tranDescScoreInfo != null) {
						getScoreActionData(questionInfo.getQuestionSeq(), menuId);
						buildTranDescScoreInfo();
					}

					Date logActionEndTime = new Date();
					String actionName = "scoreAction";
					long total = logActionEndTime.getTime() - logActionStartTime.getTime();
					log.info(actionName + "-Total: " + total);
					if (logGetQcAnsSeqListStartTime != null && logGetQcAnsSeqListEndTime != null) {
						long getQcAnsSeqListTime = logGetQcAnsSeqListEndTime.getTime()
								- logGetQcAnsSeqListStartTime.getTime();
						log.info(actionName + "-getQcAnsSeqList: " + getQcAnsSeqListTime);
					}
					if (logFindAnswerStartTime != null && logFindAnswerEndTime != null) {
						long findAnswerTime = logFindAnswerEndTime.getTime() - logFindAnswerStartTime.getTime();
						log.info(actionName + "-findAnswer: " + findAnswerTime);
					}
					if (logFindQcAnswerStartTime != null && logFindQcAnswerEndTime != null) {
						long findQcAnswerTime = logFindQcAnswerEndTime.getTime() - logFindQcAnswerStartTime.getTime();
						log.info(actionName + "-findQcAnswer: " + findQcAnswerTime);
					}

					if (tranDescScoreInfo != null) {
						return SUCCESS;
					} else {
						log.info(scorerInfo.getScorerId() + "-" + questionInfo.getMenuId() + "-"
								+ "Answer not available." + "-{ Question sequence: " + questionInfo.getQuestionSeq()
								+ ", \n TranDescScoreInfo: " + tranDescScoreInfo + "}");
						return FAILURE;
					}

				} else {
					if (tranDescScoreInfo == null && !qcAnswerSeqList.isEmpty()) {
						logFindQcAnswerStartTime = new Date();
						tranDescScoreInfo = scoreService.findQualityCheckAnswers(qcAnswerSeqList.get(0), menuId,
								scorerInfo.getScorerId(), questionInfo.getConnectionString(), questionInfo);
						logFindQcAnswerEndTime = new Date();
						if (tranDescScoreInfo != null) {
							session.put("isQcRecord", true);
							getScoreActionData(questionInfo.getQuestionSeq(), menuId);
							buildTranDescScoreInfo();
							log.info(scorerInfo.getScorerId() + "-" + menuId + "-" + "Quality record loaded."
									+ "-{ Question Sequence: " + questionInfo.getQuestionSeq() + ", Answer Sequence: "
									+ tranDescScoreInfo.getAnswerInfo().getAnswerSeq() + ", \n TranDescScoreInfo: "
									+ tranDescScoreInfo + "}");
						}

					}
				}

			}

			if (((menuId.equals(WebAppConst.SPECIAL_SCORING_OMR_READ_FAIL_MENU_ID)
					|| menuId.equals(WebAppConst.SPECIAL_SCORING_ENLARGE_TYPE_MENU_ID))
					&& (!(questionInfo.getQuestionType() == WebAppConst.SPEAKING_TYPE
							|| questionInfo.getQuestionType() == WebAppConst.WRITING_TYPE)))
					&& (tranDescScoreInfo != null)) {
				questionSequence = tranDescScoreInfo.getAnswerInfo().getQuestionSeq();
				Map<Integer, QuestionInfo> questionInfoMap = (Map<Integer, QuestionInfo>) session
						.get("questionInfoMap");

				questionInfo = questionInfoMap.get(questionSequence);
				session.put("questionInfo", questionInfo);
			} else {
				questionSequence = questionInfo.getQuestionSeq();
			}

			// Check if answer is already locked or not
			Date logLockAnswerStartTime = null;
			Date logLockAnswerEndTime = null;
			if (tranDescScoreInfo == null && !(menuId.equals(WebAppConst.SCORE_SAMP_MENU_ID)
					|| menuId.equals(WebAppConst.SPECIAL_SCORING_OMR_READ_FAIL_MENU_ID)
					|| menuId.equals(WebAppConst.SPECIAL_SCORING_ENLARGE_TYPE_MENU_ID))) {

				resetGradeNumAndPendingCategory();

				// historyRecordCount - For Special scoring
				Integer historyRecordCount = null;
				if (menuId.equals(WebAppConst.SPECIAL_SCORING_OMR_READ_FAIL_MENU_ID)
						|| menuId.equals(WebAppConst.SPECIAL_SCORING_ENLARGE_TYPE_MENU_ID)
						|| menuId.equals(WebAppConst.SPECIAL_SCORING_BLIND_TYPE_MENU_ID)
						|| menuId.equals(WebAppConst.SPECIAL_SCORING_LANGUAGE_SUPPORT_MENU_ID)
								&& ((specialScoreInputInfo.getAnswerFormNum() != null)
										&& !(specialScoreInputInfo.getAnswerFormNum().isEmpty()))) {
					answerFormNum = specialScoreInputInfo.getAnswerFormNum();
				}

				Short selectedMarkValue = (Short) session.get("selectedMarkValue");
				/* Character questionType = questionInfo.getQuestionType(); */
				// Find answer record
				logFindAnswerStartTime = new Date();
				tranDescScoreInfo = scoreService.findAnswer(questionInfo.getQuestionSeq(), menuId,
						scorerInfo.getScorerId(), questionInfo.getConnectionString(), gradeNum, pendingCategory,
						denyCategory, answerFormNum, historyRecordCount, roleId, selectedMarkValue, questionInfo,
						bitValue);
				logFindAnswerEndTime = new Date();

				if (tranDescScoreInfo != null) {
					log.info(scorerInfo.getScorerId() + "-" + menuId + "-" + "Tran record loaded."
							+ "-{ Question Sequence: " + questionInfo.getQuestionSeq() + ", Answer Sequence: "
							+ tranDescScoreInfo.getAnswerInfo().getAnswerSeq() + ", \n TranDescScoreInfo: "
							+ tranDescScoreInfo + "}");
					Date updateDate = new Date();
					// Lock selected answer record
					if (menuId.equals(WebAppConst.SPECIAL_SCORING_OMR_READ_FAIL_MENU_ID)
							|| menuId.equals(WebAppConst.SPECIAL_SCORING_ENLARGE_TYPE_MENU_ID)
							|| menuId.equals(WebAppConst.SPECIAL_SCORING_BLIND_TYPE_MENU_ID)
							|| menuId.equals(WebAppConst.SPECIAL_SCORING_LANGUAGE_SUPPORT_MENU_ID)) {
						logLockAnswerStartTime = new Date();
						scoreService.lockAnswer(tranDescScoreInfo.getAnswerInfo().getAnswerSeq(),
								scorerInfo.getScorerId(), questionInfo.getConnectionString(), updateDate);
						logLockAnswerEndTime = new Date();
					}
					if (menuId.equals(WebAppConst.MISMATCH_MENU_ID)) {
						getMismatchScoreActionData();

					} else if (menuId.equals(WebAppConst.DENY_MENU_ID)
							&& (questionInfo.getQuestionType() == WebAppConst.SPEAKING_TYPE
									|| questionInfo.getQuestionType() == WebAppConst.WRITING_TYPE)) {
						getMismatchScoreActionData();
						getScoreActionData(questionInfo.getQuestionSeq(), menuId);
					} else {
						// Get the data to be displayed on scoring screen
						getScoreActionData(questionInfo.getQuestionSeq(), menuId);
					}
					// put tranDescScoreInfo in session
					buildTranDescScoreInfo();
				}
			} else if (menuId.equals(WebAppConst.CHECKING_MENU_ID) || menuId.equals(WebAppConst.INSPECTION_MENU_ID)
					|| menuId.equals(WebAppConst.DENY_MENU_ID)
					|| ((menuId.equals(WebAppConst.SPECIAL_SCORING_OMR_READ_FAIL_MENU_ID)
							|| menuId.equals(WebAppConst.SPECIAL_SCORING_BLIND_TYPE_MENU_ID)
							|| menuId.equals(WebAppConst.SPECIAL_SCORING_LANGUAGE_SUPPORT_MENU_ID)
							|| menuId.equals(WebAppConst.SPECIAL_SCORING_ENLARGE_TYPE_MENU_ID)
							|| menuId.equals(WebAppConst.SCORE_SAMP_MENU_ID)
							|| menuId.equals(WebAppConst.FORCED_MENU_ID)) && tranDescScoreInfo != null)) {
				getScoreActionData(questionSequence, menuId);
			} else if (menuId.equals(WebAppConst.MISMATCH_MENU_ID)) {
				getMismatchScoreActionData();
			}
			Date logActionEndTime = new Date();
			String actionName = "scoreAction";
			long total = logActionEndTime.getTime() - logActionStartTime.getTime();
			log.info(actionName + "-Total: " + total);
			if (logGetQcAnsSeqListStartTime != null && logGetQcAnsSeqListEndTime != null) {
				long getQcAnsSeqListTime = logGetQcAnsSeqListEndTime.getTime() - logGetQcAnsSeqListStartTime.getTime();
				log.info(actionName + "-getQcAnsSeqList: " + getQcAnsSeqListTime);
			}
			if (logFindAnswerStartTime != null && logFindAnswerEndTime != null) {
				long findAnswerTime = logFindAnswerEndTime.getTime() - logFindAnswerStartTime.getTime();
				log.info(actionName + "-findAnswer: " + findAnswerTime);
			}
			if (logFindQcAnswerStartTime != null && logFindQcAnswerEndTime != null) {
				long findQcAnswerTime = logFindQcAnswerEndTime.getTime() - logFindQcAnswerStartTime.getTime();
				log.info(actionName + "-findQcAnswer: " + findQcAnswerTime);
			}
			if (logLockAnswerStartTime != null && logLockAnswerEndTime != null) {
				long lockAnswerTime = logLockAnswerEndTime.getTime() - logLockAnswerStartTime.getTime();
				log.info(actionName + "-lockAnswer: " + lockAnswerTime);
			}
		} catch (SaitenRuntimeException we) {
			throw we;
		} catch (Exception e) {
			throw new SaitenRuntimeException(ErrorCode.SCORE_ACTION_EXCEPTION, e);
		}

		if (tranDescScoreInfo != null) {
			return SUCCESS;
		} else {
			log.info(scorerInfo.getScorerId() + "-" + questionInfo.getMenuId() + "-" + "Answer not available."
					+ "-{ Question sequence: " + questionInfo.getQuestionSeq() + ", \n TranDescScoreInfo: "
					+ tranDescScoreInfo + "}");
			return FAILURE;
		}
	}

	public String findBulkAnswer() {
		
		try {
			/* Read Value from session */
			MstScorerInfo scorerInfo = ((MstScorerInfo) session.get("scorerInfo"));
			QuestionInfo questionInfo = (QuestionInfo) session.get("questionInfo");
			Short selectedMarkValue = (Short) session.get("selectedMarkValue");
			/* Select all the value is required for further operation */

			String menuId = questionInfo.getMenuId();
			Integer historyRecordCount = null;
			int roleId = scorerInfo.getRoleId();
			int questionSeq = questionInfo.getQuestionSeq();
			/* To Set latest selected value */
			resetGradeNumAndPendingCategory();

			tranDescInfoList = scoreService.findBulkAnswer(questionInfo.getQuestionSeq(), menuId,
					scorerInfo.getScorerId(), questionInfo.getConnectionString(), gradeNum, pendingCategory,
					denyCategory, answerFormNum, historyRecordCount, roleId, selectedMarkValue, questionInfo, bitValue);

			gradeInfo = scoreService.buildGradeInfo(tranDescInfoList.get(0).getGradeSeq(), questionSeq);
			
			if(tranDescInfoList != null && !(tranDescInfoList.isEmpty())) {
			/*Record found*/
			columnSize=4;	
			return SUCCESS;
			}
			
			return null;
		} catch (SaitenRuntimeException we) {
			throw we;
		} catch (Exception e) {
			throw new SaitenRuntimeException(ErrorCode.SCORE_ACTION_EXCEPTION, e);
		}
		
	}

	@SuppressWarnings("unchecked")
	public String findHistoryAnswer() {
		QuestionInfo questionInfo = (QuestionInfo) session.get("questionInfo");
		MstScorerInfo scorerInfo = ((MstScorerInfo) session.get("scorerInfo"));
		try {

			if (!bookmarkScreenFlag) {
				// For selected history record, modify prev and next counters
				updatePrevNextRecordCounts();
			} else {
				session.remove("historyScreenFlag");
			}

			String connectionString = null;
			Integer questionSequence = null;
			String menuId = questionInfo.getMenuId();
			if (menuId.equals(WebAppConst.SPECIAL_SCORING_OMR_READ_FAIL_MENU_ID)
					|| menuId.equals(WebAppConst.SPECIAL_SCORING_ENLARGE_TYPE_MENU_ID)) {
				connectionString = this.connectionString;
				questionSequence = this.questionSequence;

				Map<Integer, QuestionInfo> questionInfoMap = (Map<Integer, QuestionInfo>) session
						.get("questionInfoMap");

				questionInfo = questionInfoMap.get(questionSequence);
				session.put("questionInfo", questionInfo);

			} else {
				connectionString = questionInfo.getConnectionString();
				questionSequence = questionInfo.getQuestionSeq();
			}
			// Fetch history answer record using historySequence
			tranDescScoreInfo = scoreService.findHistoryAnswer(qcSeq, historySequence, connectionString,
					scorerInfo.getScorerId(), bookmarkScreenFlag, questionInfo);

			if (tranDescScoreInfo != null) {

				Date updateDate = new Date();
				// Lock selected answer record if it is not qc record.
				if (historySequence != null && !bookmarkScreenFlag) {
					scoreService.lockAnswer(tranDescScoreInfo.getAnswerInfo().getAnswerSeq(), scorerInfo.getScorerId(),
							questionInfo.getConnectionString(), updateDate);
				}

				// Get the data to be displayed on scoring screen
				Short scoringState = tranDescScoreInfo.getScoringState();
				if (ArrayUtils.contains(WebAppConst.MISMATCH_STATES, scoringState)) {
					getMismatchScoreActionData();
				} else if (ArrayUtils.contains(WebAppConst.DENY_SCORING_STATES, scoringState)
						&& (questionInfo.getQuestionType() == WebAppConst.SPEAKING_TYPE
								|| questionInfo.getQuestionType() == WebAppConst.WRITING_TYPE)) {
					getMismatchScoreActionData();
					getScoreActionData(questionInfo.getQuestionSeq(), menuId);
				}
				getScoreActionData(questionSequence, menuId);

				// put tranDescScoreInfo in session
				buildTranDescScoreInfo();
				if (!bookmarkScreenFlag) {
					if (qcSeq != null) {
						log.info(scorerInfo.getScorerId() + "-" + menuId + "-" + "History Quality record loaded."
								+ "-{ Question Sequence: " + questionInfo.getQuestionSeq() + ", Qc Sequence: " + qcSeq
								+ ", Answer Sequence: " + tranDescScoreInfo.getAnswerInfo().getAnswerSeq()
								+ ", \n TranDescScoreInfo: " + tranDescScoreInfo + "}");
					} else if (historySequence != null) {
						log.info(scorerInfo.getScorerId() + "-" + menuId + "-" + "History Tran record loaded."
								+ "-{ Question Sequence: " + questionInfo.getQuestionSeq() + ", History Sequence: "
								+ historySequence + ", Answer Sequence: "
								+ tranDescScoreInfo.getAnswerInfo().getAnswerSeq() + ", \n TranDescScoreInfo: "
								+ tranDescScoreInfo + "}");
					}
				} else {
					log.info(scorerInfo.getScorerId() + "-" + menuId + "-" + "Bookmark record loaded."
							+ "-{ Question Sequence: " + questionInfo.getQuestionSeq() + ", History Sequence: "
							+ historySequence + ", Answer Sequence: " + tranDescScoreInfo.getAnswerInfo().getAnswerSeq()
							+ ", \n TranDescScoreInfo: " + tranDescScoreInfo + "}");
				}

			}

			return tranDescScoreInfo != null ? SUCCESS : FAILURE;

		} catch (SaitenRuntimeException we) {
			throw we;
		} catch (Exception e) {
			throw new SaitenRuntimeException(ErrorCode.HISTORY_SCORE_ACTION_EXCEPTION, e);
		}

	}

	private void resetGradeNumAndPendingCategory() {
		QuestionInfo questionInfo = (QuestionInfo) session.get("questionInfo");

		if (gradeNum != null && (questionInfo.getMenuId().equals(WebAppConst.CHECKING_MENU_ID)
				|| questionInfo.getMenuId().equals(WebAppConst.INSPECTION_MENU_ID)
				|| questionInfo.getMenuId().equals(WebAppConst.DENY_MENU_ID))) {
			session.put("gradeNumber", gradeNum);
		} else {
			gradeNum = (Integer) session.get("gradeNumber");
		}

		if (pendingCategory != null && (questionInfo.getMenuId().equals(WebAppConst.PENDING_MENU_ID))) {
			session.put("pendingCategory", pendingCategory);
		} else {
			pendingCategory = (Short) session.get("pendingCategory");
		}
	}

	private void updatePrevNextRecordCounts() {
		Integer rowNumber = Integer.parseInt(request.getParameter("rowNum"));
		Integer pageSize = Integer.parseInt(getText(WebAppConst.HISTORY_PAGESIZE));
		Integer pageNumber = 1;
		if (session.get("a") != null) {
			pageNumber = Integer.parseInt(session.get("a").toString());
		}
		Integer currentCount = pageSize * (pageNumber - 1) + rowNumber;

		QuestionInfo sessionQuestionInfo = (QuestionInfo) session.get("questionInfo");
		String menuId = sessionQuestionInfo.getMenuId();

		if (menuId.equals(WebAppConst.SPECIAL_SCORING_ENLARGE_TYPE_MENU_ID)
				|| menuId.equals(WebAppConst.SPECIAL_SCORING_OMR_READ_FAIL_MENU_ID)) {
			session.put("prevRecordCount", (Integer) session.get("historyRecordCount") - currentCount);
			session.put("nextRecordCount", currentCount - 1);
		} else {
			sessionQuestionInfo.setPrevRecordCount(sessionQuestionInfo.getHistoryRecordCount() - currentCount);
			sessionQuestionInfo.setNextRecordCount(currentCount - 1);

			session.put("questionInfo", sessionQuestionInfo);
		}
	}

	public String findPrevOrNextHistoryAnswer() {
		try {
			MstScorerInfo scorerInfo = ((MstScorerInfo) session.get("scorerInfo"));
			QuestionInfo questionInfo = (QuestionInfo) session.get("questionInfo");
			tranDescScoreInfo = (TranDescScoreInfo) session.get("tranDescScoreInfo");
			Date updateDate = null;
			if (tranDescScoreInfo != null && tranDescScoreInfo.getAnswerInfo().getHistorySeq() != null) {
				updateDate = tranDescScoreInfo.getAnswerInfo().getUpdateDate();

				// Unlock the locked history answer by logged in scorer, before
				// loading another history answer.
				SaitenUtil.unlockHistoryAnswer(tranDescScoreInfo);
			} else if (tranDescScoreInfo != null && tranDescScoreInfo.getAnswerInfo().getQcSeq() != null) {
				updateDate = tranDescScoreInfo.getAnswerInfo().getUpdateDate();
			}

			String menuId = questionInfo.getMenuId();
			if (menuId.equals(WebAppConst.SPECIAL_SCORING_ENLARGE_TYPE_MENU_ID)
					|| menuId.equals(WebAppConst.SPECIAL_SCORING_OMR_READ_FAIL_MENU_ID)) {
				// Update prev / next record counters
				updateSpecialScoringPrevNextRecordCounts();

				questionInfo = getSpecialScoringQuestionInfo(scorerInfo, questionInfo);
			}

			// Find prev or next answer record
			tranDescScoreInfo = scoreService.findPrevOrNextHistoryAnswer(questionInfo, scorerInfo, updateDate,
					prevOrNextFlag, bookmarkScreenFlag);

			if (tranDescScoreInfo != null) {

				updateDate = new Date();
				// Lock selected answer record
				if (tranDescScoreInfo.getAnswerInfo().getQcSeq() != null) {
					log.info(scorerInfo.getScorerId() + "-" + menuId + "-" + "History Quality record loaded."
							+ "-{ Question Sequence: " + questionInfo.getQuestionSeq() + ", Qc Sequence: "
							+ tranDescScoreInfo.getAnswerInfo().getQcSeq() + ", Answer Sequence: "
							+ tranDescScoreInfo.getAnswerInfo().getAnswerSeq() + ", \n TranDescScoreInfo: "
							+ tranDescScoreInfo + "}");
				} else {
					log.info(scorerInfo.getScorerId() + "-" + menuId + "-" + "History Tran record loaded."
							+ "-{ Question Sequence: " + questionInfo.getQuestionSeq() + ", History Sequence: "
							+ tranDescScoreInfo.getAnswerInfo().getHistorySeq() + ", Answer Sequence: "
							+ tranDescScoreInfo.getAnswerInfo().getAnswerSeq() + ", \n TranDescScoreInfo: "
							+ tranDescScoreInfo + "}");
					scoreService.lockAnswer(tranDescScoreInfo.getAnswerInfo().getAnswerSeq(), scorerInfo.getScorerId(),
							questionInfo.getConnectionString(), updateDate);
				}

				// Fetch scoring screen data
				Short scoringState = tranDescScoreInfo.getScoringState();
				if (ArrayUtils.contains(WebAppConst.MISMATCH_STATES, scoringState)) {
					getMismatchScoreActionData();
				}
				getScoreActionData(questionInfo.getQuestionSeq(), menuId);

				if (!(menuId.equals(WebAppConst.SPECIAL_SCORING_ENLARGE_TYPE_MENU_ID)
						|| menuId.equals(WebAppConst.SPECIAL_SCORING_OMR_READ_FAIL_MENU_ID))) {
					// Manage prev and next history counters for each answer
					// evaluation
					updateHistoryCounters();
				}

				buildTranDescScoreInfo();
			}

		} catch (SaitenRuntimeException we) {
			throw we;
		} catch (Exception e) {
			throw new SaitenRuntimeException(ErrorCode.SCORE_ACTION_EXCEPTION, e);
		}

		return tranDescScoreInfo != null ? SUCCESS : FAILURE;

	}

	@SuppressWarnings("unchecked")
	private QuestionInfo getSpecialScoringQuestionInfo(MstScorerInfo scorerInfo, QuestionInfo questionInfo) {

		List<HistoryInfo> historyInfoList = (List<HistoryInfo>) session.get("historyInfoList");

		Integer historyRecordCount = (Integer) session.get("nextRecordCount");
		HistoryInfo historyInfo = historyInfoList.get(historyRecordCount);
		Map<Integer, QuestionInfo> questionInfoMap = (Map<Integer, QuestionInfo>) session.get("questionInfoMap");

		QuestionInfo historyQuestionInfo = questionInfoMap.get(historyInfo.getQuestionSequence());
		session.put("questionInfo", historyQuestionInfo);

		return historyQuestionInfo;

	}

	private void updateSpecialScoringPrevNextRecordCounts() {
		if (prevOrNextFlag == WebAppConst.TRUE) {
			session.put("prevRecordCount", (Integer) session.get("prevRecordCount") - 1);
			session.put("nextRecordCount", (Integer) session.get("nextRecordCount") + 1);
		} else {
			session.put("prevRecordCount", (Integer) session.get("prevRecordCount") + 1);
			session.put("nextRecordCount", (Integer) session.get("nextRecordCount") - 1);
		}
	}

	public String findBookmarkAnswer() {
		bookmarkScreenFlag = WebAppConst.TRUE;
		try {
			// Find bookmark answer and set bookmarkScreenFlag to true
			return findHistoryAnswer();
		} catch (SaitenRuntimeException we) {
			throw we;
		} catch (Exception e) {
			throw new SaitenRuntimeException(ErrorCode.HISTORY_SCORE_ACTION_EXCEPTION, e);
		}
	}

	public String resetHistory() {
		try {
			QuestionInfo sessionQuestionInfo = (QuestionInfo) session.get("questionInfo");
			String menuId = sessionQuestionInfo.getMenuId();

			if (menuId.equals(WebAppConst.SPECIAL_SCORING_ENLARGE_TYPE_MENU_ID)
					|| menuId.equals(WebAppConst.SPECIAL_SCORING_OMR_READ_FAIL_MENU_ID)) {
				session.put("prevRecordCount", (Integer) session.get("historyRecordCount"));
				session.put("nextRecordCount", (Integer) WebAppConst.MINUS_ONE.intValue());
			} else {
				sessionQuestionInfo.setPrevRecordCount(sessionQuestionInfo.getHistoryRecordCount());
				sessionQuestionInfo.setNextRecordCount(WebAppConst.MINUS_ONE);
			}

			TranDescScoreInfo sessionTranDescScoreInfo = (TranDescScoreInfo) session.get("tranDescScoreInfo");
			TranDescScoreInfo tranDescScoreInfoCopy = (TranDescScoreInfo) session.get("tranDescScoreInfoCopy");

			// Reset history answer before loading new answer record
			if (sessionTranDescScoreInfo != null && (sessionTranDescScoreInfo.getAnswerInfo().getHistorySeq() != null
					|| sessionTranDescScoreInfo.getAnswerInfo().getQcSeq() != null)) {

				// Before loading Fresh Answer again, unlock the locked History
				// Answer locked by logged in scorer.
				SaitenUtil.unlockHistoryAnswer(sessionTranDescScoreInfo);

				session.put("tranDescScoreInfo", tranDescScoreInfoCopy);
				session.remove("tranDescScoreInfoCopy");
			}

			session.remove("historyScreenFlag");
			session.remove("selectedCheckPointList");

		} catch (SaitenRuntimeException we) {
			throw we;
		} catch (Exception e) {
			throw new SaitenRuntimeException(ErrorCode.SCORE_ACTION_EXCEPTION, e);
		}
		return SUCCESS;
	}

	public String showSaitenMenu() {
		try {
			QuestionInfo questionInfo = (QuestionInfo) session.get("questionInfo");
			/*
			 * ApplicationContext ctx = ContextLoader .getCurrentWebApplicationContext();
			 */
			MstScorerInfo mstScorerInfo = (MstScorerInfo) session.get("scorerInfo");
			/*
			 * TranScorerSessionInfo tranScorerSessionInfo = (TranScorerSessionInfo) session
			 * .get("tranScorerSessionInfo");
			 */

			if (questionInfo != null) {
				String menuId = questionInfo.getMenuId();
				String lockBy = mstScorerInfo.getScorerId();

				// Unlock answer
				if (questionInfo.getConnectionString() != null && questionInfo.getQuestionSeq() != WebAppConst.ZERO) {
					Integer answerSeq = null;
					scoreService.unlockAnswer(questionInfo.getQuestionSeq(), lockBy, questionInfo.getConnectionString(),
							answerSeq);
					/*
					 * MstQuestion mstQuestion = null;
					 * tranScorerSessionInfo.setMstQuestion(mstQuestion);
					 */

				}

				if (menuId.equals(WebAppConst.SPECIAL_SCORING_OMR_READ_FAIL_MENU_ID)
						|| menuId.equals(WebAppConst.SPECIAL_SCORING_ENLARGE_TYPE_MENU_ID)) {
					/*
					 * String answerFormNumber = (String) session .get("answerFormNum");
					 * 
					 * SaitenUtil.updateSpecialScoringMap( questionInfo.getSubjectCode(),
					 * answerFormNumber);
					 */

					/*
					 * tranScorerSessionInfo.setAnswerFormNum(null);
					 * tranScorerSessionInfo.setSubjectCode(null);
					 */

					/*
					 * Map<SpecialScoringKey, String> specialScoringMap = ((SaitenConfig)
					 * ServletActionContext .getServletContext().getAttribute(
					 * "saitenConfigObject")) .getSpecialScoringMap();
					 * 
					 * Set<SpecialScoringKey> set = specialScoringMap.keySet();
					 * 
					 * for (SpecialScoringKey specialScoringKey : set) { System.out
					 * .println(">>>>>>>>>>>>>>> getAnswerFormNum = " +
					 * specialScoringKey.getAnswerFormNum());
					 * System.out.println(">>>>>>>>>>>>>>> getSubjectCode = " +
					 * specialScoringKey.getSubjectCode()); }
					 * 
					 * System.out.println(">>>>>>>>>>>>>> specialScoringMap = " +
					 * specialScoringMap);
					 */
				}
				/*
				 * tranScorerSessionInfo.setUpdateDate(new Date()); // Clear questionSeq,
				 * AnswerFormNum, SubjectCoed from // tran_scorer_session_info.
				 * ((SaitenMasterUtil) ctx.getBean("saitenMasterUtil"))
				 * .updateUserSessionInfo(tranScorerSessionInfo); // put updated
				 * tranScorerSessionInfo into session. session.put("tranScorerSessionInfo",
				 * tranScorerSessionInfo);
				 */
			}
		} catch (SaitenRuntimeException we) {
			throw we;
		} catch (Exception e) {
			throw new SaitenRuntimeException(ErrorCode.SCORE_ACTION_EXCEPTION, e);
		}

		return SUCCESS;
	}

	/**
	 * @param questionSeq
	 */
	private void getScoreActionData(int questionSeq, String menuId) {
		Double bitValue = tranDescScoreInfo.getAnswerInfo().getBitValue();
		QuestionInfo questionInfo = (QuestionInfo) session.get("questionInfo");

		if ((WebAppConst.SCORE_TYPE[3].equals(questionInfo.getScoreType()))
				&& (WebAppConst.FIRST_SCORING_MENU_ID.equals(menuId)
						|| WebAppConst.SECOND_SCORING_MENU_ID.equals(menuId)
						|| WebAppConst.PENDING_MENU_ID.equals(menuId) || WebAppConst.OUT_BOUNDARY_MENU_ID.equals(menuId)
						|| WebAppConst.MISMATCH_MENU_ID.equals(menuId)
						|| WebAppConst.SPECIAL_SCORING_BLIND_TYPE_MENU_ID.equals(menuId)
						|| WebAppConst.SPECIAL_SCORING_ENLARGE_TYPE_MENU_ID.equals(menuId)
						|| WebAppConst.SPECIAL_SCORING_LANGUAGE_SUPPORT_MENU_ID.equals(menuId)
						|| WebAppConst.SPECIAL_SCORING_OMR_READ_FAIL_MENU_ID.equals(menuId)
						|| WebAppConst.SCORE_SAMP_MENU_ID.equals(menuId)
						|| WebAppConst.FIRST_SCORING_QUALITY_CHECK_MENU_ID.equals(menuId))
				&& (tranDescScoreInfo.getAnswerInfo().getHistorySeq() == null) && (bookmarkScreenFlag == false)) {
			List<Short> markValueList = tranDescScoreInfo.getMarkValueList();
			session.remove("selectedCheckPointList");
			if ((markValueList != null) && (!markValueList.contains((short) 0))) {
				session.put("selectedCheckPointList", markValueList);
			} else if (tranDescScoreInfo.getAnswerInfo().getQcSeq() != null) { // added
																				// this
																				// condition
																				// to
																				// show
																				// checkpoints
																				// for
																				// QC
																				// record
				List<Short> selectedCheckPointList = SaitenUtil.getSelectedCheckPointList(bitValue);
				session.put("selectedCheckPointList", selectedCheckPointList);
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

		if (tranDescScoreInfo.getGradeSeq() != null) {
			// Get grade info. for scored answer
			gradeInfo = scoreService.buildGradeInfo(tranDescScoreInfo.getGradeSeq(), questionSeq);
		}
		if ((tranDescScoreInfo.getAnswerInfo().getHistorySeq() != null
				|| tranDescScoreInfo.getAnswerInfo().getQcSeq() != null) && (!bookmarkScreenFlag)) {
			session.put("historyScreenFlag", WebAppConst.TRUE);
		}
	}

	private void getMismatchScoreActionData() {
		QuestionInfo questionInfo = (QuestionInfo) session.get("questionInfo");
		String menuId = questionInfo.getMenuId();
		if ((menuId.equals(WebAppConst.SPECIAL_SCORING_OMR_READ_FAIL_MENU_ID)
				|| menuId.equals(WebAppConst.SPECIAL_SCORING_ENLARGE_TYPE_MENU_ID))
				&& ((session.get("historyScreenFlag") != null) || (bookmarkScreenFlag))) {
			questionInfo.setConnectionString(this.connectionString);
			questionInfo.setQuestionSeq(this.questionSequence);
		}

		if (WebAppConst.MISMATCH_MENU_ID.equals(menuId)) {
			List<Short> markValueList = tranDescScoreInfo.getMarkValueList();
			session.remove("selectedCheckPointList");
			if ((markValueList != null) && (!markValueList.contains((short) 0))) {
				session.put("selectedCheckPointList", markValueList);
			}
		}

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
			if ((ArrayUtils.contains(WebAppConst.DENY_SCORING_STATES, currentScoringState)
					|| questionInfo.getMenuId().equals(WebAppConst.DENY_MENU_ID))
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
			if ((ArrayUtils.contains(WebAppConst.DENY_SCORING_STATES, currentScoringState)
					|| questionInfo.getMenuId().equals(WebAppConst.DENY_MENU_ID))
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
			// bitValue will be null for pending answer. so remove
			// selectedCheckPointList from session.
			session.remove("firstTimeSelectedCheckPointList");
			session.remove("secondTimeSelectedCheckPointList");
		}

		if (tranDescScoreInfo.getAnswerInfo().getHistorySeq() != null && (!bookmarkScreenFlag)) {
			session.put("historyScreenFlag", WebAppConst.TRUE);
		}
	}

	private void buildTranDescScoreInfo() {
		TranDescScoreInfo tranDescScoreInfoCopy = (TranDescScoreInfo) session.get("tranDescScoreInfoCopy");

		if (((tranDescScoreInfo.getAnswerInfo().getHistorySeq() != null)
				|| (tranDescScoreInfo.getAnswerInfo().getQcSeq() != null)) && tranDescScoreInfoCopy == null) {

			// Create copy of tranDescScoreInfo for history records
			TranDescScoreInfo sessionTranDescScoreInfo = (TranDescScoreInfo) session.get("tranDescScoreInfo");
			if (sessionTranDescScoreInfo != null && (sessionTranDescScoreInfo.getAnswerInfo().getHistorySeq() == null
					&& sessionTranDescScoreInfo.getAnswerInfo().getQcSeq() == null)) {
				session.put("tranDescScoreInfoCopy", sessionTranDescScoreInfo);
			}

		}
		session.put("tranDescScoreInfo", tranDescScoreInfo);
	}

	private void updateHistoryCounters() {
		QuestionInfo sessionQuestionInfo = (QuestionInfo) session.get("questionInfo");

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
	 * 
	 * @param scoreService
	 */
	public void setScoreService(ScoreService scoreService) {
		this.scoreService = scoreService;
	}

	public void setSaitenGlobalProperties(Properties saitenGlobalProperties) {
		this.saitenGlobalProperties = saitenGlobalProperties;
	}

	/**
	 * 
	 * @param session
	 */
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public Integer getHistorySequence() {
		return historySequence;
	}

	/**
	 * 
	 * @param historySequence
	 */
	public void setHistorySequence(Integer historySequence) {
		this.historySequence = historySequence;
	}

	public String getConnectionString() {
		return connectionString;
	}

	/**
	 * @param connectionString
	 */
	public void setConnectionString(String connectionString) {
		this.connectionString = connectionString;
	}

	public Integer getQuestionSequence() {
		return questionSequence;
	}

	public void setQuestionSequence(Integer questionSequence) {
		this.questionSequence = questionSequence;
	}

	public boolean isBookmarkScreenFlag() {
		return bookmarkScreenFlag;
	}

	/**
	 * 
	 * @param bookmarkScreenFlag
	 */
	public void setBookmarkScreenFlag(boolean bookmarkScreenFlag) {
		this.bookmarkScreenFlag = bookmarkScreenFlag;
	}

	public GradeInfo getGradeInfo() {
		return gradeInfo;
	}

	/**
	 * 
	 * @param gradeInfo
	 */
	public void setGradeInfo(GradeInfo gradeInfo) {
		this.gradeInfo = gradeInfo;
	}

	public TranDescScoreInfo getTranDescScoreInfo() {
		return tranDescScoreInfo;
	}

	/**
	 * 
	 * @param tranDescScoreInfo
	 */
	public void setTranDescScoreInfo(TranDescScoreInfo tranDescScoreInfo) {
		this.tranDescScoreInfo = tranDescScoreInfo;
	}

	public Boolean isPrevOrNextFlag() {
		return prevOrNextFlag;
	}

	/**
	 * 
	 * @param prevOrNextFlag
	 */
	public void setPrevOrNextFlag(Boolean prevOrNextFlag) {
		this.prevOrNextFlag = prevOrNextFlag;
	}

	/**
	 * 
	 * @param request
	 */
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletRequest getServletRequest() {
		return request;
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
	 * @return the request
	 */
	public HttpServletRequest getRequest() {
		return request;
	}

	/**
	 * @param request the request to set
	 */
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the scoreService
	 */
	public ScoreService getScoreService() {
		return scoreService;
	}

	/**
	 * @return the saitenGlobalProperties
	 */
	public Properties getSaitenGlobalProperties() {
		return saitenGlobalProperties;
	}

	/**
	 * @return the session
	 */
	public Map<String, Object> getSession() {
		return session;
	}

	/**
	 * @return the prevOrNextFlag
	 */
	public Boolean getPrevOrNextFlag() {
		return prevOrNextFlag;
	}

	/**
	 * @return the failure
	 */
	public static String getFailure() {
		return FAILURE;
	}

	/**
	 * @return the answerFormNum
	 */
	public String getAnswerFormNum() {
		return answerFormNum;
	}

	/**
	 * @param answerFormNum the answerFormNum to set
	 */
	public void setAnswerFormNum(String answerFormNum) {
		this.answerFormNum = answerFormNum;
	}

	public HistoryListingService getHistoryListingService() {
		return historyListingService;
	}

	public void setHistoryListingService(HistoryListingService historyListingService) {
		this.historyListingService = historyListingService;
	}

	/**
	 * @return the answerFlag
	 */
	public String getAnswerFlag() {
		return answerFlag;
	}

	/**
	 * @param answerFlag the answerFlag to set
	 */
	public void setAnswerFlag(String answerFlag) {
		this.answerFlag = answerFlag;
	}

	/**
	 * @return the specialScoreInputInfo
	 */
	public SpecialScoreInputInfo getSpecialScoreInputInfo() {
		return specialScoreInputInfo;
	}

	/**
	 * @param specialScoreInputInfo the specialScoreInputInfo to set
	 */
	public void setSpecialScoreInputInfo(SpecialScoreInputInfo specialScoreInputInfo) {
		this.specialScoreInputInfo = specialScoreInputInfo;
	}

	/**
	 * @return the qcSeq
	 */
	public Integer getQcSeq() {
		return qcSeq;
	}

	/**
	 * @param qcSeq the qcSeq to set
	 */
	public void setQcSeq(Integer qcSeq) {
		this.qcSeq = qcSeq;
	}

	public Integer getAnswerSequence() {
		return answerSequence;
	}

	public void setAnswerSequence(Integer answerSequence) {
		this.answerSequence = answerSequence;
	}

	public void setBitValue(Double bitValue) {
		this.bitValue = bitValue;
	}

	public Double getBitValue() {
		return bitValue;
	}
	
	public List<String> getNumbers() {
		return numbers;
	}

	public void setNumbers(List<String> numbers) {
		this.numbers = numbers;
	}



	public List<TranDescScoreInfo> getTranDescInfoList() {
		return tranDescInfoList;
	}



	public void setTranDescInfoList(List<TranDescScoreInfo> tranDescInfoList) {
		this.tranDescInfoList = tranDescInfoList;
	}
	
	

}