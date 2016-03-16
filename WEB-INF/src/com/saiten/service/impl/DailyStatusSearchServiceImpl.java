package com.saiten.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.HibernateException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.TextProvider;
import com.saiten.bean.SaitenConfig;
import com.saiten.dao.MstDbInstanceDAO;
import com.saiten.dao.MstEvaluationDAO;
import com.saiten.dao.MstQuestionDAO;
import com.saiten.dao.MstScorerDAO;
import com.saiten.dao.TranDescScoreDAO;
import com.saiten.exception.SaitenRuntimeException;
import com.saiten.info.DailyStatusReportListInfo;
import com.saiten.info.DailyStatusSearchInfo;
import com.saiten.info.DailyStatusSearchScorerInfo;
import com.saiten.info.QuestionInfo;
import com.saiten.manager.SaitenTransactionManager;
import com.saiten.model.MstDbInstance;
import com.saiten.model.MstQuestion;
import com.saiten.model.MstScorer;
import com.saiten.service.DailyStatusSearchService;
import com.saiten.service.ScoreSearchService;
import com.saiten.util.ErrorCode;
import com.saiten.util.SaitenFileUtil;
import com.saiten.util.SaitenUtil;
import com.saiten.util.WebAppConst;

/**
 * @author sachin
 * @version 1.0
 * @created 05-Dec-2012 4:59:11 PM
 */
public class DailyStatusSearchServiceImpl implements DailyStatusSearchService {
	private TranDescScoreDAO tranDescScoreDAO;
	private MstQuestionDAO mstQuestionDAO;
	private SaitenTransactionManager saitenTransactionManager;
	private MstScorerDAO mstScorerDAO;
	private MstDbInstanceDAO mstDbInstanceDAO;
	private MstEvaluationDAO mstEvaluationDAO;
	private ScoreSearchService scoreSearchService;

	@SuppressWarnings("rawtypes")
	List finalResultList = new ArrayList();
	
	@SuppressWarnings("rawtypes")
	List pendCategoryResultList = new ArrayList();

	@SuppressWarnings({ "unused", "rawtypes" })
	public Map<Byte, String> getScorerRollMapByID(String id) {
		Map<Byte, String> scorerRollMap = new LinkedHashMap<Byte, String>();
		try {
			String[] fetchIds = id.split(",");
			Map<Byte, String> rollMap = ((SaitenConfig) ServletActionContext
					.getServletContext().getAttribute("saitenConfigObject"))
					.getMstScorerRoleMap();
			Iterator it = rollMap.entrySet().iterator();

			for (int i = 0; i < fetchIds.length; i++) {
				Byte key = Byte.parseByte(fetchIds[i]);
				String value = rollMap.get(key);
				scorerRollMap.put(key, value);
			}

		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.SCORER_ROLL_SEARCH_EXCEPTION, e);
		}
		return scorerRollMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.saiten.service.DailyStatusSearchService#getSearchList(com.saiten.
	 * info.DailyStatusSearchInfo)
	 */
	@SuppressWarnings({ "rawtypes" })
	@Override
	public List<DailyStatusReportListInfo> getSearchListByQuestion(
			DailyStatusSearchInfo dailyStatusSearchInfo, int roleId) {
		List<DailyStatusReportListInfo> dailyStatusReportList = new ArrayList<DailyStatusReportListInfo>();
		try {

			List<String> subjectCodeList = new ArrayList<String>();
			subjectCodeList.add(dailyStatusSearchInfo.getSubjectCode());

			/*
			 * Code to get questionseq from saiten map
			 */

			LinkedHashMap<Integer, MstQuestion> mstQuestionMap = new LinkedHashMap<Integer, MstQuestion>();
			if (roleId == WebAppConst.WG_ROLE_ID) {
				mstQuestionMap = SaitenUtil
						.getMstQuestionMapByScorerId(dailyStatusSearchInfo
								.getLogedInScorerId());

			} else if (roleId == WebAppConst.ADMIN_ROLE_ID) {
				// Fetch questions for role admin

				mstQuestionMap = SaitenUtil.getSaitenConfigObject()
						.getMstQuestionMap();

			}

			/*
			 * mstQuestionMap = SaitenUtil
			 * .getMstQuestionMapByScorerId(dailyStatusSearchInfo
			 * .getLogedInScorerId());
			 */

			Map<String, String> questionSequenceMap = new LinkedHashMap<String, String>();
			Map<String, String> subjectQuestionSeqMap = new LinkedHashMap<String, String>();
			Map<String, String> questionNumQuestionSeqMap = new LinkedHashMap<String, String>();

			for (MstQuestion mstQuestion : mstQuestionMap.values()) {
				if (subjectCodeList.contains(mstQuestion.getMstSubject()
						.getSubjectCode())) {
					/*
					 * if (Integer.parseInt(dailyStatusSearchInfo
					 * .getQuetionTypeRadio()) == Integer.parseInt("" +
					 * mstQuestion.getMstEvaluation().getEvalSeq())) {
					 */
					/*
					 * Integer evalSeq = getEvalSeqByQuestionType(Integer
					 * .parseInt(dailyStatusSearchInfo .getQuetionTypeRadio()));
					 */
					Integer evalSeq = dailyStatusSearchInfo.getEvalSeq();

					if ((evalSeq != null)
							&& (evalSeq.equals(mstQuestion.getMstEvaluation()
									.getEvalSeq()))) {
						String subjectCode = subjectCodeList
								.get(WebAppConst.ZERO);
						// IF USER ENTERS QUESTION NUM
						if (dailyStatusSearchInfo != null
								&& dailyStatusSearchInfo.getQuestionNum() != null
								&& !dailyStatusSearchInfo.getQuestionNum()
										.equals("")) {
							Short questionNum = Short
									.parseShort(dailyStatusSearchInfo
											.getQuestionNum());
							List<Integer> questionSeqList = scoreSearchService
									.findQuestionSeq(subjectCode, questionNum);

							Integer questionSeq = !questionSeqList.isEmpty() ? questionSeqList
									.get(0) : null;

							if (questionSeq != null
									&& questionSeq == Integer.parseInt(""
											+ mstQuestion.getQuestionSeq())) {
								String connectionString = mstQuestion
										.getMstDbInstance()
										.getConnectionString();
								String questionSequence = ""
										+ mstQuestion.getQuestionSeq();

								if (questionSequenceMap.get(connectionString) != null) {
									questionSequenceMap.put(
											connectionString,
											questionSequenceMap
													.get(connectionString)
													+ "," + questionSequence);
								} else {
									questionSequenceMap.put(connectionString,
											questionSequence);
								}

								subjectQuestionSeqMap.put(questionSequence,
										mstQuestion.getMstSubject()
												.getSubjectName());
								questionNumQuestionSeqMap.put(questionSequence,
										"" + mstQuestion.getQuestionNum());
								break;
							}
						} else { // IF USER NOT ENTER QUESTION NUM
							String connectionString = mstQuestion
									.getMstDbInstance().getConnectionString();
							String questionSequence = ""
									+ mstQuestion.getQuestionSeq();
							if (questionSequenceMap.get(connectionString) != null) {
								questionSequenceMap.put(
										connectionString,
										questionSequenceMap
												.get(connectionString)
												+ ","
												+ questionSequence);
							} else {
								questionSequenceMap.put(connectionString,
										questionSequence);
							}
							subjectQuestionSeqMap.put(questionSequence,
									mstQuestion.getMstSubject()
											.getSubjectName());
							questionNumQuestionSeqMap.put(questionSequence, ""
									+ mstQuestion.getQuestionNum());
						}
					}

				}
			}

			Iterator readQuerstionSeqMap = questionSequenceMap.entrySet()
					.iterator();
			while (readQuerstionSeqMap.hasNext()) {
				Map.Entry pairs = (Map.Entry) readQuerstionSeqMap.next();

				List dailyStatusRow = tranDescScoreDAO
						.getDailyuStatusSearchList(dailyStatusSearchInfo, ""
								+ pairs.getKey(), "" + pairs.getValue());
				if (dailyStatusRow != null && !dailyStatusRow.isEmpty()) {
					dailyStatusReportList = bindDailyStatusReportListInfo(
							dailyStatusRow, subjectQuestionSeqMap,
							questionNumQuestionSeqMap, dailyStatusReportList);

				}
			}

		} catch (HibernateException he) {
			throw new SaitenRuntimeException(
					ErrorCode.DAILY_STATUS_REPORT_BY_QUESTION_HIBERNATE_EXCEPTION,
					he);
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.DAILY_STATUS_REPORT_BY_QUESTION_SERVICE_EXCEPTION,
					e);
		}
		return dailyStatusReportList;

	}

	private Integer getEvalSeqByQuestionType(Integer questionType) {
		Integer evalSeq = null;
		switch (questionType) {
		case 1:
			evalSeq = WebAppConst.SHORT_TYPE_EVAL_SEQ;
			break;
		case 2:
			evalSeq = WebAppConst.LONG_TYPE_EVAL_SEQ;
			break;
		case 3:
			evalSeq = WebAppConst.SPEAKING_TYPE_EVAL_SEQ;
			break;
		case 4:
			evalSeq = WebAppConst.WRITTING_TYPE_EVAL_SEQ;
			break;
		case 5:
			evalSeq = WebAppConst.SHORT_SCREEN_OBJECTIVE_EVAL_SEQ;
			break;
		case 6:
			evalSeq = WebAppConst.LONG_SCREEN_OBJECTIVE_EVAL_SEQ;
			break;
		case 7:
			evalSeq = WebAppConst.BATCH_SCORING_EVAL_SEQ;
			break;
		default:
			break;
		}
		return evalSeq;
	}

	@SuppressWarnings("rawtypes")
	private List<DailyStatusReportListInfo> bindDailyStatusReportListInfo(
			List dailyStatusRow, Map<String, String> subjectQuestionSeqMap,
			Map<String, String> questionNumQuestionSeqMap,
			List<DailyStatusReportListInfo> dailyStatusReportListPre) {
		List<DailyStatusReportListInfo> dailyStatusReportList = new ArrayList<DailyStatusReportListInfo>();

		if (dailyStatusReportListPre != null
				&& !dailyStatusReportListPre.isEmpty()) {
			dailyStatusReportList = dailyStatusReportListPre;
		}

		try {
			Object[] dailyStatusRowArray = dailyStatusRow.toArray();
			if (dailyStatusRow != null && !dailyStatusRow.isEmpty()) {
				for (int i = 0; i < dailyStatusRowArray.length; i++) {
					DailyStatusReportListInfo dailyStatusReportListInfo = new DailyStatusReportListInfo();
					Object[] getArray = dailyStatusRowArray[i].toString()
							.split(",");
					String questionSeq = ""
							+ getArray[0].toString().replaceAll("\\[", "");
					dailyStatusReportListInfo.setQuestionSeq(questionSeq);
					dailyStatusReportListInfo
							.setQuestionNum(questionNumQuestionSeqMap
									.get(questionSeq));
					dailyStatusReportListInfo
							.setSubjectName(subjectQuestionSeqMap
									.get(questionSeq));
					dailyStatusReportListInfo.setAnswerTotal("" + getArray[1]);
					dailyStatusReportListInfo.setAnswerDecision(""
							+ getArray[2]);
					dailyStatusReportListInfo.setConfirmBatch("" + getArray[3]);
					dailyStatusReportListInfo.setFirstTimeScoringWait(""
							+ getArray[4]);
					dailyStatusReportListInfo.setFirstTimeScoringTemp(""
							+ getArray[5]);
					dailyStatusReportListInfo.setFirstTimeScoringPending(""
							+ getArray[6]);
					dailyStatusReportListInfo.setSecondTimeScoringWait(""
							+ getArray[7]);
					dailyStatusReportListInfo.setSecondTimeScoringTemp(""
							+ getArray[8]);
					dailyStatusReportListInfo.setSecondTimeScoringPending(""
							+ getArray[9]);
					dailyStatusReportListInfo.setCheckingWorkWait(""
							+ getArray[10]);
					dailyStatusReportListInfo.setCheckingApproveTemp(""
							+ getArray[11]);
					dailyStatusReportListInfo.setChekingDenyTemp(""
							+ getArray[12]);
					dailyStatusReportListInfo.setPendingScoringWait(""
							+ getArray[13]);
					dailyStatusReportListInfo.setPendingScoringTemp(""
							+ getArray[14]);
					dailyStatusReportListInfo.setPendingScorePendingTemp(""
							+ getArray[15]);
					dailyStatusReportListInfo.setMismatchScoringWait(""
							+ getArray[16]);
					dailyStatusReportListInfo.setMismatchScoringTemp(""
							+ getArray[17]);
					dailyStatusReportListInfo.setMismatchScoringPending(""
							+ getArray[18]);
					dailyStatusReportListInfo.setInspectionMenuWait(""
							+ getArray[19]);
					dailyStatusReportListInfo.setInspectionMenuApprove(""
							+ getArray[20]);
					dailyStatusReportListInfo.setInspectionMenuDeny(""
							+ getArray[21]);
					dailyStatusReportListInfo.setOutOfBoundaryScoringWait(""
							+ getArray[22]);
					dailyStatusReportListInfo.setOutOfBoundaryScoringTemp(""
							+ getArray[23]);
					dailyStatusReportListInfo.setOutOfBoundaryPendingTemp(""
							+ getArray[24]);
					dailyStatusReportListInfo.setDenyuScoringWait(""
							+ getArray[25]);
					dailyStatusReportListInfo.setDenyuScoringTemp(""
							+ getArray[26]);
					dailyStatusReportListInfo.setDenyuScoringPending(""
							+ getArray[27]);
					dailyStatusReportListInfo.setNoGradeScoringWait(""
							+ getArray[28]);
					dailyStatusReportListInfo.setNoGradeScoringTemp(""
							+ getArray[29]);
					dailyStatusReportListInfo.setNoGradePengingTemp(""
							+ getArray[30]);
					dailyStatusReportListInfo.setScoringSamplingTemp(""
							+ getArray[31]);
					dailyStatusReportListInfo.setScoringSamplingPending(""
							+ getArray[32]);
					dailyStatusReportListInfo.setForcedScoringTemp(""
							+ getArray[33]);
					dailyStatusReportListInfo.setForcedScoringPending(""
							+ getArray[34].toString().replaceAll("]", ""));
					/*
					 * dailyStatusReportListInfo.setPunchWait("" +
					 * getArray[34]);
					 * dailyStatusReportListInfo.setPunchDataReadWait("" +
					 * getArray[35]);
					 * dailyStatusReportListInfo.setBatchScoringWait("" +
					 * getArray[36]);
					 * dailyStatusReportListInfo.setBatchScoringOputputComplete
					 * ("" + getArray[37]);
					 * dailyStatusReportListInfo.setBatchScoringComplete("" +
					 * getArray[38]);
					 * dailyStatusReportListInfo.setScoringNotPossibleByBatch(""
					 * + getArray[39]);
					 * dailyStatusReportListInfo.setScoringCompleteByBatch("" +
					 * getArray[40].toString().replaceAll("]", ""));
					 */

					dailyStatusReportList.add(dailyStatusReportListInfo);
				}
			}
		} catch (RuntimeException re) {
			throw re;
		}
		return dailyStatusReportList;
	}

	/*
	 * Scorerwise Search List
	 */
	@SuppressWarnings({ "rawtypes" })
	@Override
	public List<DailyStatusSearchScorerInfo> getSearchListByScorer(
			DailyStatusSearchInfo dailyStatusSearchInfo) {
		List<DailyStatusSearchScorerInfo> dailyStatusReportList = new ArrayList<DailyStatusSearchScorerInfo>();
		PlatformTransactionManager platformTransactionManager = null;
		TransactionStatus transactionStatus = null;
		try {

			String[] rollSelected = (dailyStatusSearchInfo.getSelectedRoll() != null ? dailyStatusSearchInfo
					.getSelectedRoll().split(",") : new String[0]);
			Byte[] rollIds = new Byte[rollSelected.length];
			for (int i = 0; i < rollSelected.length; i++) {
				rollIds[i] = Byte.parseByte(rollSelected[i].trim());
			}

			List<String> connectionStringList = new ArrayList<String>();
			List<MstDbInstance> mstDbInstanceList = mstDbInstanceDAO.findAll();
			for (MstDbInstance mstDbInstance : mstDbInstanceList) {
				connectionStringList.add(mstDbInstance.getConnectionString());
			}

			platformTransactionManager = getSaitenTransactionManager()
					.getTransactionManger();
			transactionStatus = getSaitenTransactionManager().beginTransaction(
					platformTransactionManager);

			Map<String, DailyStatusSearchScorerInfo> dailyStatusReportListInfoMap = new LinkedHashMap<String, DailyStatusSearchScorerInfo>();

			List<MstScorer> scorerList = mstScorerDAO.scorerByRole(rollIds,
					dailyStatusSearchInfo);

			String scorerId = "";
			for (MstScorer mstScorer : scorerList) {

				DailyStatusSearchScorerInfo dailyStatusReportListInfo = new DailyStatusSearchScorerInfo();
				dailyStatusReportListInfo.setUserId(mstScorer.getScorerId());
				dailyStatusReportListInfo.setUserRoll(mstScorer
						.getMstScorerRole().getRoleDescription());

				dailyStatusReportListInfoMap.put(mstScorer.getScorerId(),
						dailyStatusReportListInfo);

				if (scorerId != null && !scorerId.equals("")) {
					scorerId = scorerId + ",'" + mstScorer.getScorerId() + "'";
				} else {
					scorerId = "'" + mstScorer.getScorerId() + "'";
				}

			}// scorer wise loop
			platformTransactionManager.commit(transactionStatus);

			if ((scorerId != null && !scorerId.equals(""))
					|| (dailyStatusSearchInfo.getSelectedRoll() == null || dailyStatusSearchInfo
							.getSelectedRoll().equals(""))) {
				List<Integer> questionSeqByScorerList = mstQuestionDAO
						.getQuestionSeqByEvalType(dailyStatusSearchInfo
								.getEvalSeq());
				String questionSeqByScorer = "";
				for (Integer tempSeq : questionSeqByScorerList) {
					if (questionSeqByScorer != null
							&& !questionSeqByScorer.equals("")) {
						questionSeqByScorer = questionSeqByScorer + ",";
						questionSeqByScorer = questionSeqByScorer + tempSeq;
					} else {
						questionSeqByScorer = questionSeqByScorer + tempSeq;
					}
				}

				if (questionSeqByScorer != null
						&& !questionSeqByScorer.equals("")) {
					for (String connectionString : connectionStringList) {
						List dailyStatusRow = tranDescScoreDAO
								.getDailyuStatusSearchListByScorer(
										dailyStatusSearchInfo,
										connectionString, scorerId,
										questionSeqByScorer);
						dailyStatusReportListInfoMap = bindDailyStatusReportScorerListInfo(
								dailyStatusRow, dailyStatusReportListInfoMap);

					}
				}
			}

			Iterator dailyStatusReportListInfoMapIterator = dailyStatusReportListInfoMap
					.entrySet().iterator();
			while (dailyStatusReportListInfoMapIterator.hasNext()) {
				Map.Entry pairs = (Map.Entry) dailyStatusReportListInfoMapIterator
						.next();
				DailyStatusSearchScorerInfo finalObj = (DailyStatusSearchScorerInfo) pairs
						.getValue();
				if (finalObj != null && finalObj.getScoringTotal() != null
						&& !finalObj.getScoringTotal().equals("")
						&& Integer.parseInt(finalObj.getScoringTotal()) > 0) {
					dailyStatusReportList.add(finalObj);
				}
			}

		} catch (HibernateException he) {
			try {
				if (platformTransactionManager != null
						&& transactionStatus != null)
					platformTransactionManager.rollback(transactionStatus);
			} catch (Exception skip) {
			}

			throw new SaitenRuntimeException(
					ErrorCode.DAILY_STATUS_REPORT_BY_SCORER_HIBERNATE_EXCEPTION,
					he);
		} catch (Exception e) {
			try {
				if (platformTransactionManager != null
						&& transactionStatus != null)
					platformTransactionManager.rollback(transactionStatus);
			} catch (Exception skip) {
			}

			throw new SaitenRuntimeException(
					ErrorCode.DAILY_STATUS_REPORT_BY_SCORER_SERVICE_EXCEPTION,
					e);
		}
		return dailyStatusReportList;

	}

	@SuppressWarnings("rawtypes")
	private Map<String, DailyStatusSearchScorerInfo> bindDailyStatusReportScorerListInfo(
			List dailyStatusRow,
			Map<String, DailyStatusSearchScorerInfo> dailyStatusReportListInfoMap) {
		Map<String, DailyStatusSearchScorerInfo> tempDailyStatusReportListInfoMap = dailyStatusReportListInfoMap;

		try {
			Object[] dailyStatusRowArray = dailyStatusRow.toArray();

			for (int i = 0; i < dailyStatusRowArray.length; i++) {
				Object[] getArray = dailyStatusRowArray[i].toString()
						.split(",");
				String key = ("" + getArray[0]).replaceAll("\\[", "");
				DailyStatusSearchScorerInfo dailyStatusSearchScorerInfo = tempDailyStatusReportListInfoMap
						.get(key);
				if (dailyStatusSearchScorerInfo != null) {
					dailyStatusSearchScorerInfo.setScoringTotal(getTotal(
							dailyStatusSearchScorerInfo.getScoringTotal(), ""
									+ getArray[1]));
					dailyStatusSearchScorerInfo
							.setFirstTimeScoringTotal(getTotal(
									dailyStatusSearchScorerInfo
											.getFirstTimeScoringTotal(), ""
											+ getArray[2]));
					dailyStatusSearchScorerInfo
							.setSecondTimeScoringTotal(getTotal(
									dailyStatusSearchScorerInfo
											.getSecondTimeScoringTotal(), ""
											+ getArray[3]));
					dailyStatusSearchScorerInfo.setPending(getTotal(
							dailyStatusSearchScorerInfo.getPending(), ""
									+ getArray[4]));
					dailyStatusSearchScorerInfo
							.setMismatchScoringTotal(getTotal(
									dailyStatusSearchScorerInfo
											.getMismatchScoringTotal(), ""
											+ getArray[5]));
					dailyStatusSearchScorerInfo.setInspectionMenu(getTotal(
							dailyStatusSearchScorerInfo.getInspectionMenu(), ""
									+ getArray[6]));
					dailyStatusSearchScorerInfo.setDenyuScoringTotal(getTotal(
							dailyStatusSearchScorerInfo.getDenyuScoringTotal(),
							"" + getArray[7]));
					dailyStatusSearchScorerInfo
							.setScoringSamplingTotal(getTotal(
									dailyStatusSearchScorerInfo
											.getScoringSamplingTotal(), ""
											+ getArray[8]));
					dailyStatusSearchScorerInfo.setForcedScoring(getTotal(
							dailyStatusSearchScorerInfo.getForcedScoring(),
							("" + getArray[9]).replaceAll("]", "")));
					tempDailyStatusReportListInfoMap.remove(key);
					tempDailyStatusReportListInfoMap.put(key,
							dailyStatusSearchScorerInfo);
				}
			}

		} catch (RuntimeException re) {
			throw re;
		}
		return tempDailyStatusReportListInfoMap;
	}

	private String getTotal(String previousTotalStr, String currentTotalStr) {
		String prevScoringTotalStr = previousTotalStr != null ? previousTotalStr
				.trim() : "0";
		String currentScoringTotalStr = currentTotalStr != null ? currentTotalStr
				.trim() : "0";
		Integer currentScoringTotal = currentScoringTotalStr != null
				&& !currentScoringTotalStr.equals("") ? Integer.parseInt(""
				+ currentScoringTotalStr) : 0;
		Integer prevScoringTotal = prevScoringTotalStr != null
				&& !prevScoringTotalStr.equals("") ? Integer.parseInt(""
				+ prevScoringTotalStr) : 0;
		return "" + (prevScoringTotal + currentScoringTotal);
	}

	@Override
	public QuestionInfo getQusetionInfoByQuestionSeq(String questionSeq) {
		QuestionInfo questionInfo = new QuestionInfo();
		try {
			MstQuestion mstQuestion = SaitenUtil.getMstQuestionMap().get(
					Integer.valueOf(questionSeq));
			if (mstQuestion != null) {
				questionInfo.setConnectionString(mstQuestion.getMstDbInstance()
						.getConnectionString());
				questionInfo.setSubjectName(mstQuestion.getMstSubject()
						.getSubjectName());
				questionInfo.setQuestionNum(mstQuestion.getQuestionNum());
				questionInfo.setQuestionType((Character) mstQuestion
						.getMstEvaluation().getMstQuestionType()
						.getQuestionType());
				questionInfo.setScoreType(mstQuestion.getMstEvaluation()
						.getScoreType());
				questionInfo.setSubjectCode(mstQuestion.getMstSubject()
						.getSubjectCode());
			}
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.DAILY_STATUS_QUESTION_WISE_REPORT_SERVICE_EXCEPTION,
					e);
		}
		return questionInfo;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<DailyStatusReportListInfo> getGradeWiseAnswerDetails(
			String questionSeq, String connectionString, Character questionType) {
		List<DailyStatusReportListInfo> gradeWiseReportList = new ArrayList<DailyStatusReportListInfo>();
		try {
			List searchResultList = tranDescScoreDAO.getGradeWiseAnswerDetails(
					questionSeq, connectionString, questionType);
			if (searchResultList != null && !searchResultList.isEmpty()) {
				for (Object object : searchResultList) {
					Object[] array = (Object[]) object;
					DailyStatusReportListInfo dailyStatusReportListInfo = new DailyStatusReportListInfo();
					dailyStatusReportListInfo.setQuestionSeq(questionSeq);
					dailyStatusReportListInfo.setGradeNum("" + array[0]);
					dailyStatusReportListInfo.setConfirmBatch("" + array[1]);
					dailyStatusReportListInfo.setFirstTimeScoringTemp(""
							+ array[2]);
					if (questionType == Arrays
							.asList(WebAppConst.QUESTION_TYPE).get(1)) {
						dailyStatusReportListInfo.setCheckingWorkWait(""
								+ array[3]);
					} else {
						dailyStatusReportListInfo.setSecondTimeScoringWait(""
								+ array[3]);
					}
					dailyStatusReportListInfo.setSecondTimeScoringTemp(""
							+ array[4]);
					dailyStatusReportListInfo.setCheckingApproveTemp(""
							+ array[5]);
					dailyStatusReportListInfo.setChekingDenyTemp("" + array[6]);
					dailyStatusReportListInfo.setPendingScoringTemp(""
							+ array[7]);
					dailyStatusReportListInfo.setMismatchScoringTemp(""
							+ array[8]);
					dailyStatusReportListInfo.setInspectionMenuApprove(""
							+ array[9]);
					dailyStatusReportListInfo.setInspectionMenuDeny(""
							+ array[10]);
					dailyStatusReportListInfo.setOutOfBoundaryScoringTemp(""
							+ array[11]);
					dailyStatusReportListInfo.setDenyuScoringTemp(""
							+ array[12]);
					dailyStatusReportListInfo.setNoGradeScoringTemp(""
							+ array[13]);
					dailyStatusReportListInfo.setScoringSamplingTemp(""
							+ array[14]);
					dailyStatusReportListInfo.setForcedScoringTemp(""
							+ array[15]);
					dailyStatusReportListInfo.setInspectionMenuWait(""
							+ array[16]);
					dailyStatusReportListInfo.setDenyuScoringWait(""
							+ array[17]);
					gradeWiseReportList.add(dailyStatusReportListInfo);
				}
			}
		} catch (HibernateException he) {
			throw new SaitenRuntimeException(
					ErrorCode.DAILY_STATUS_QUESTION_WISE_REPORT_HIBERNATE_EXCEPTION,
					he);
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.DAILY_STATUS_QUESTION_WISE_REPORT_SERVICE_EXCEPTION,
					e);
		}
		return gradeWiseReportList;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<DailyStatusReportListInfo> getPendingCategoryWiseAnswerDetails(
			String questionSeq, String connectionString) {
		List<DailyStatusReportListInfo> pendingCategoryWiseReportList = new ArrayList<DailyStatusReportListInfo>();
		try {
			List searchResultList = tranDescScoreDAO
					.getPendingCategoryWiseAnswerDetails(questionSeq,
							connectionString);
			if (searchResultList != null && !searchResultList.isEmpty()) {
				for (Object object : searchResultList) {
					Object[] array = (Object[]) object;
					DailyStatusReportListInfo dailyStatusReportListInfo = new DailyStatusReportListInfo();
					dailyStatusReportListInfo.setQuestionSeq(questionSeq);
					dailyStatusReportListInfo.setPendingCategory("" + array[0]);
					dailyStatusReportListInfo.setFirstTimeScoringPending(""
							+ array[1]);
					dailyStatusReportListInfo.setSecondTimeScoringPending(""
							+ array[2]);
					dailyStatusReportListInfo.setPendingScoringWait(""
							+ array[3]);
					dailyStatusReportListInfo.setPendingScorePendingTemp(""
							+ array[4]);
					dailyStatusReportListInfo.setMismatchScoringPending(""
							+ array[5]);
					dailyStatusReportListInfo.setOutOfBoundaryPendingTemp(""
							+ array[6]);
					dailyStatusReportListInfo.setDenyuScoringPending(""
							+ array[7]);
					dailyStatusReportListInfo.setNoGradePengingTemp(""
							+ array[8]);
					dailyStatusReportListInfo.setScoringSamplingPending(""
							+ array[9]);
					dailyStatusReportListInfo.setForcedScoringPending(""
							+ array[10]);
					pendingCategoryWiseReportList
							.add(dailyStatusReportListInfo);
				}
			}
		} catch (HibernateException he) {
			throw new SaitenRuntimeException(
					ErrorCode.DAILY_STATUS_QUESTION_WISE_REPORT_HIBERNATE_EXCEPTION,
					he);
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.DAILY_STATUS_QUESTION_WISE_REPORT_SERVICE_EXCEPTION,
					e);
		}
		return pendingCategoryWiseReportList;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<DailyStatusReportListInfo> getMarkValueWiseAnswerDetails(
			String questionSeq, String connectionString, Character questionType) {
		List<DailyStatusReportListInfo> markValueWiseReportList = new ArrayList<DailyStatusReportListInfo>();
		try {
			List searchResultList = tranDescScoreDAO
					.getMarkValueWiseAnswerDetails(questionSeq,
							connectionString, questionType);
			if (searchResultList != null && !searchResultList.isEmpty()) {
				for (Object object : searchResultList) {
					Object[] array = (Object[]) object;
					DailyStatusReportListInfo dailyStatusReportListInfo = new DailyStatusReportListInfo();
					dailyStatusReportListInfo.setQuestionSeq(questionSeq);
					dailyStatusReportListInfo.setMarkValue("" + array[0]);
					dailyStatusReportListInfo.setConfirmBatch("" + array[1]);
					dailyStatusReportListInfo.setFirstTimeScoringTemp(""
							+ array[2]);
					if (questionType == Arrays
							.asList(WebAppConst.QUESTION_TYPE).get(1)) {
						dailyStatusReportListInfo.setCheckingWorkWait(""
								+ array[3]);
					} else {
						dailyStatusReportListInfo.setSecondTimeScoringWait(""
								+ array[3]);
					}
					dailyStatusReportListInfo.setSecondTimeScoringTemp(""
							+ array[4]);
					dailyStatusReportListInfo.setCheckingApproveTemp(""
							+ array[5]);
					dailyStatusReportListInfo.setChekingDenyTemp("" + array[6]);
					dailyStatusReportListInfo.setPendingScoringTemp(""
							+ array[7]);
					dailyStatusReportListInfo.setMismatchScoringTemp(""
							+ array[8]);
					dailyStatusReportListInfo.setInspectionMenuApprove(""
							+ array[9]);
					dailyStatusReportListInfo.setInspectionMenuDeny(""
							+ array[10]);
					dailyStatusReportListInfo.setOutOfBoundaryScoringTemp(""
							+ array[11]);
					dailyStatusReportListInfo.setDenyuScoringTemp(""
							+ array[12]);
					dailyStatusReportListInfo.setNoGradeScoringTemp(""
							+ array[13]);
					dailyStatusReportListInfo.setScoringSamplingTemp(""
							+ array[14]);
					dailyStatusReportListInfo.setForcedScoringTemp(""
							+ array[15]);
					dailyStatusReportListInfo.setInspectionMenuWait(""
							+ array[16]);
					dailyStatusReportListInfo.setDenyuScoringWait(""
							+ array[17]);
					markValueWiseReportList.add(dailyStatusReportListInfo);
				}
			}
		} catch (HibernateException he) {
			throw new SaitenRuntimeException(
					ErrorCode.DAILY_STATUS_QUESTION_WISE_REPORT_HIBERNATE_EXCEPTION,
					he);
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.DAILY_STATUS_QUESTION_WISE_REPORT_SERVICE_EXCEPTION,
					e);
		}
		return markValueWiseReportList;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List findQuestionTypeList() {

		try {

			return mstEvaluationDAO.getQuestionTypeScoreTypeList();

		} catch (HibernateException he) {
			throw new SaitenRuntimeException(
					ErrorCode.DAILY_STATUS_QUESTION_WISE_REPORT_HIBERNATE_EXCEPTION,
					he);
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.DAILY_STATUS_QUESTION_WISE_REPORT_SERVICE_EXCEPTION,
					e);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getProgressReports(List gradeWiseList,
			List markValueWiseList, List pendingCategoryWiseList,
			QuestionInfo questionInfo, String selectedMenuId) {

		String fileToDownload = null;
		List<DailyStatusReportListInfo> gradeList = new ArrayList();
		List<DailyStatusReportListInfo> markValueList = new ArrayList();
		List<DailyStatusReportListInfo> pendCategoryList = new ArrayList();

		try {
			File downloadDir;
			TextProvider textProvider = (TextProvider) ActionContext
					.getContext().getActionInvocation().getAction();

			String downloadDirBasePath = textProvider.getTexts(
					WebAppConst.APPLICATION_PROPERTIES_FILE).getString(
					"saiten.daily.report.download.basepath");

			if (selectedMenuId.equals(WebAppConst.GRADE_WISE_DETAILS_REPORT)) {

				if (gradeWiseList != null && !gradeWiseList.isEmpty()) {
					gradeList.addAll(gradeWiseList);
				}

				if (gradeList != null && !gradeList.isEmpty()) {

					downloadDir = SaitenFileUtil.createDirectory(
							downloadDirBasePath,
							textProvider.getText("grade.wise.report.file.name")
									+ WebAppConst.HYPHEN
									+ questionInfo.getSubjectCode()
									+ WebAppConst.HYPHEN
									+ questionInfo.getQuestionNum());

					getProgressReportHeaders(textProvider, selectedMenuId);

					getGradeWiseReportRows(gradeList, textProvider);

					File txtFile = new File(
							downloadDir.getPath()
									+ File.separator
									+ textProvider
											.getText("grade.wise.report.file.name")
									+ WebAppConst.HYPHEN
									+ questionInfo.getSubjectCode()
									+ WebAppConst.HYPHEN
									+ questionInfo.getQuestionNum()
									+ WebAppConst.CSV_FILE_EXTENSION);

					FileUtils.writeLines(txtFile, WebAppConst.FILE_ENCODING,
							finalResultList, WebAppConst.CRLF);

					if (!SaitenFileUtil.isEmptyDirectory(downloadDir)) {

						SaitenFileUtil.createZipFileFromDirectory(
								downloadDir.getPath(), downloadDir.getPath()
										+ WebAppConst.ZIP_FILE_EXTENSION);

						fileToDownload = downloadDir.getPath()
								+ WebAppConst.ZIP_FILE_EXTENSION;
					}
					SaitenFileUtil.deleteDirectory(downloadDir);
				}

			} else if (selectedMenuId
					.equals(WebAppConst.PENDING_CATEGORY_WISE_DETAILS_REPORT)) {

				if (pendingCategoryWiseList != null
						&& !pendingCategoryWiseList.isEmpty()) {
					pendCategoryList.addAll(pendingCategoryWiseList);
				}

				if (pendCategoryList != null && !pendCategoryList.isEmpty()) {

					downloadDir = SaitenFileUtil
							.createDirectory(
									downloadDirBasePath,
									textProvider
											.getText("pending.category.wise.report.file.name")
											+ WebAppConst.HYPHEN
											+ questionInfo.getSubjectCode()
											+ WebAppConst.HYPHEN
											+ questionInfo.getQuestionNum());

					getPendCategoryReportHeaders(textProvider, selectedMenuId);

					getPendCategoryReportRows(pendCategoryList, textProvider);

					File txtFile = new File(
							downloadDir.getPath()
									+ File.separator
									+ textProvider
											.getText("pending.category.wise.report.file.name")
									+ WebAppConst.HYPHEN
									+ questionInfo.getSubjectCode()
									+ WebAppConst.HYPHEN
									+ questionInfo.getQuestionNum()
									+ WebAppConst.CSV_FILE_EXTENSION);

					FileUtils.writeLines(txtFile, WebAppConst.FILE_ENCODING,
							pendCategoryResultList, WebAppConst.CRLF);

					if (!SaitenFileUtil.isEmptyDirectory(downloadDir)) {

						SaitenFileUtil.createZipFileFromDirectory(
								downloadDir.getPath(), downloadDir.getPath()
										+ WebAppConst.ZIP_FILE_EXTENSION);

						fileToDownload = downloadDir.getPath()
								+ WebAppConst.ZIP_FILE_EXTENSION;
					}
					SaitenFileUtil.deleteDirectory(downloadDir);
				}

			} else if (selectedMenuId
					.equals(WebAppConst.MARK_VALUE_WISE_DETAILS_REPORT)) {

				if (markValueWiseList != null && !markValueWiseList.isEmpty()) {
					markValueList.addAll(markValueWiseList);
				}

				if (markValueList != null && !markValueList.isEmpty()) {

					downloadDir = SaitenFileUtil
							.createDirectory(
									downloadDirBasePath,
									textProvider
											.getText("mark.value.wise.report.file.name")
											+ WebAppConst.HYPHEN
											+ questionInfo.getSubjectCode()
											+ WebAppConst.HYPHEN
											+ questionInfo.getQuestionNum());

					getProgressReportHeaders(textProvider, selectedMenuId);

					getMarkValueWiseReportRows(markValueList, selectedMenuId,
							textProvider);

					File txtFile = new File(
							downloadDir.getPath()
									+ File.separator
									+ textProvider
											.getText("mark.value.wise.report.file.name")
									+ WebAppConst.HYPHEN
									+ questionInfo.getSubjectCode()
									+ WebAppConst.HYPHEN
									+ questionInfo.getQuestionNum()
									+ WebAppConst.CSV_FILE_EXTENSION);
					
					FileUtils.writeLines(txtFile, WebAppConst.FILE_ENCODING,
							finalResultList, WebAppConst.CRLF);

					if (!SaitenFileUtil.isEmptyDirectory(downloadDir)) {

						SaitenFileUtil.createZipFileFromDirectory(
								downloadDir.getPath(), downloadDir.getPath()
										+ WebAppConst.ZIP_FILE_EXTENSION);

						fileToDownload = downloadDir.getPath()
								+ WebAppConst.ZIP_FILE_EXTENSION;
					}
					SaitenFileUtil.deleteDirectory(downloadDir);
				}
			}

		} catch (HibernateException he) {
			throw new SaitenRuntimeException(
					ErrorCode.DAILY_STATUS_QUESTION_WISE_REPORT_HIBERNATE_EXCEPTION,
					he);
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.DAILY_STATUS_QUESTION_WISE_REPORT_SERVICE_EXCEPTION,
					e);
		}

		return fileToDownload;
	}

	@SuppressWarnings("unchecked")
	private void getPendCategoryReportHeaders(TextProvider textProvider,
			String selectedMenuId) {

		StringBuilder csvHeaders = new StringBuilder();
		StringBuilder heading = new StringBuilder();

		heading.append(textProvider
				.getText("label.pending.cat.wise.report.details"));
		csvHeaders.append(textProvider
				.getText("label.prog.report.pending.category"));
		csvHeaders.append(WebAppConst.TAB_CHARACTER);
		csvHeaders.append(textProvider
				.getText("label.prog.report.pending.rating.waiting"));
		csvHeaders.append(WebAppConst.TAB_CHARACTER);
		csvHeaders.append(textProvider
				.getText("lable.prog.report.1st.pending.temp"));
		csvHeaders.append(WebAppConst.TAB_CHARACTER);
		csvHeaders.append(textProvider
				.getText("label.prog.report.2nd.pending.temp"));
		csvHeaders.append(WebAppConst.TAB_CHARACTER);
		csvHeaders.append(textProvider
				.getText("label.prog.report.pending.pending.temp"));
		csvHeaders.append(WebAppConst.TAB_CHARACTER);
		csvHeaders.append(textProvider
				.getText("label.prog.report.discrepancy.pending.temp"));
		csvHeaders.append(WebAppConst.TAB_CHARACTER);
		csvHeaders.append(textProvider
				.getText("label.prog.report.out.of.boundary.pending.temp"));
		csvHeaders.append(WebAppConst.TAB_CHARACTER);
		csvHeaders.append(textProvider
				.getText("label.prog.report.deny.pending.temp"));
		csvHeaders.append(WebAppConst.TAB_CHARACTER);
		csvHeaders.append(textProvider
				.getText("label.prog.report.no.grade.pending.temp"));
		csvHeaders.append(WebAppConst.TAB_CHARACTER);
		csvHeaders.append(textProvider
				.getText("label.prog.report.script.search.pending.temp"));
		csvHeaders.append(WebAppConst.TAB_CHARACTER);
		csvHeaders.append(textProvider
				.getText("label.prog.report.forced.pending.temp"));

		pendCategoryResultList.add(heading.toString());
		pendCategoryResultList.add(csvHeaders.toString());
	}

	@SuppressWarnings("unchecked")
	private void getPendCategoryReportRows(
			List<DailyStatusReportListInfo> pendCategoryList,
			TextProvider textProvider) {

		if (!pendCategoryList.isEmpty()) {

			for (DailyStatusReportListInfo record : pendCategoryList) {

				StringBuilder csvData = new StringBuilder();

				if (record.getPendingCategory().equals("null")) {
					csvData.append(textProvider
							.getText("dailyStatusQuestionWise.report.total"));
				} else {
					csvData.append(record.getPendingCategory());
				}

				csvData.append(WebAppConst.TAB_CHARACTER);
				csvData.append(record.getPendingScoringWait());
				csvData.append(WebAppConst.TAB_CHARACTER);
				csvData.append(record.getFirstTimeScoringPending());
				csvData.append(WebAppConst.TAB_CHARACTER);
				csvData.append(record.getSecondTimeScoringPending());
				csvData.append(WebAppConst.TAB_CHARACTER);
				csvData.append(record.getPendingScorePendingTemp());
				csvData.append(WebAppConst.TAB_CHARACTER);
				csvData.append(record.getMismatchScoringPending());
				csvData.append(WebAppConst.TAB_CHARACTER);
				csvData.append(record.getOutOfBoundaryPendingTemp());
				csvData.append(WebAppConst.TAB_CHARACTER);
				csvData.append(record.getDenyuScoringPending());
				csvData.append(WebAppConst.TAB_CHARACTER);
				csvData.append(record.getNoGradePengingTemp());
				csvData.append(WebAppConst.TAB_CHARACTER);
				csvData.append(record.getScoringSamplingPending());
				csvData.append(WebAppConst.TAB_CHARACTER);
				csvData.append(record.getForcedScoringPending());

				pendCategoryResultList.add(csvData.toString());
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void getProgressReportHeaders(TextProvider textProvider,
			String selectedMenuId) {

		StringBuilder csvHeaders = new StringBuilder();
		StringBuilder heading = new StringBuilder();

		if (selectedMenuId.equals(WebAppConst.GRADE_WISE_DETAILS_REPORT)) {

			heading.append(textProvider
					.getText("label.grade.wise.report.grade.wise.details"));

			csvHeaders.append(textProvider
					.getText("label.grade.wise.report.grade"));

		} else if (selectedMenuId
				.equals(WebAppConst.MARK_VALUE_WISE_DETAILS_REPORT)) {

			heading.append(textProvider
					.getText("label.mark.value.wise.report.details"));

			csvHeaders.append(textProvider
					.getText("label.mark.value.wise.report.markValue"));
		}

		csvHeaders.append(WebAppConst.TAB_CHARACTER);
		csvHeaders.append(textProvider
				.getText("label.grade.wise.report.confirm"));
		csvHeaders.append(WebAppConst.TAB_CHARACTER);
		csvHeaders.append(textProvider
				.getText("label.grade.wise.report.checking.Rtg.waiting"));
		csvHeaders.append(WebAppConst.TAB_CHARACTER);
		csvHeaders.append(textProvider
				.getText("label.grade.wise.report.inspection.Rtg.waiting"));
		csvHeaders.append(WebAppConst.TAB_CHARACTER);
		csvHeaders.append(textProvider
				.getText("label.grade.wise.report.deny.Rtg.waiting"));
		csvHeaders.append(WebAppConst.TAB_CHARACTER);
		csvHeaders.append(textProvider
				.getText("label.grade.wise.report.1st.Rtg.temp"));
		csvHeaders.append(WebAppConst.TAB_CHARACTER);
		csvHeaders.append(textProvider
				.getText("label.grade.wise.report.2nd.Rtg.temp"));
		csvHeaders.append(WebAppConst.TAB_CHARACTER);
		csvHeaders.append(textProvider
				.getText("label.grade.wise.report.pending.Rtg.temp"));
		csvHeaders.append(WebAppConst.TAB_CHARACTER);
		csvHeaders.append(textProvider
				.getText("label.grade.wise.report.discrepancy.Rtg.temp"));
		csvHeaders.append(WebAppConst.TAB_CHARACTER);
		csvHeaders.append(textProvider
				.getText("label.grade.wise.report.out.of.boundary.Rtg.temp"));
		csvHeaders.append(WebAppConst.TAB_CHARACTER);
		csvHeaders.append(textProvider
				.getText("label.grade.wise.report.deny.Rtg.temp"));
		csvHeaders.append(WebAppConst.TAB_CHARACTER);
		csvHeaders.append(textProvider
				.getText("label.grade.wise.report.no.grade.Rtg.temp"));
		csvHeaders.append(WebAppConst.TAB_CHARACTER);
		csvHeaders.append(textProvider
				.getText("label.grade.wise.report.script.search.Rtg.temp"));
		csvHeaders.append(WebAppConst.TAB_CHARACTER);
		csvHeaders.append(textProvider
				.getText("label.grade.wise.report.forced.Rtg.temp"));
		csvHeaders.append(WebAppConst.TAB_CHARACTER);
		csvHeaders
				.append(textProvider
						.getText("label.grade.wise.report.check.operation.approve.temp"));
		csvHeaders.append(WebAppConst.TAB_CHARACTER);
		csvHeaders.append(textProvider
				.getText("label.grade.wise.report.check.operation.deny.temp"));
		csvHeaders.append(WebAppConst.TAB_CHARACTER);
		csvHeaders
				.append(textProvider
						.getText("label.grade.wise.report.inspection.operation.approve.temp"));
		csvHeaders.append(WebAppConst.TAB_CHARACTER);
		csvHeaders
				.append(textProvider
						.getText("label.grade.wise.report.inspection.operation.deny.temp"));

		finalResultList.add(heading.toString());
		finalResultList.add(csvHeaders.toString());
	}

	@SuppressWarnings("unchecked")
	private void getGradeWiseReportRows(
			List<DailyStatusReportListInfo> gradeList, TextProvider textProvider) {

		if (!gradeList.isEmpty()) {

			for (DailyStatusReportListInfo record : gradeList) {

				StringBuilder csvData = new StringBuilder();

				if (record.getGradeNum().equals("null")) {
					csvData.append(textProvider
							.getText("dailyStatusQuestionWise.report.total"));
				} else {
					csvData.append(record.getGradeNum());
				}

				csvData.append(WebAppConst.TAB_CHARACTER);
				csvData.append(record.getConfirmBatch());
				csvData.append(WebAppConst.TAB_CHARACTER);
				csvData.append(record.getCheckingWorkWait());
				csvData.append(WebAppConst.TAB_CHARACTER);
				csvData.append(record.getInspectionMenuWait());
				csvData.append(WebAppConst.TAB_CHARACTER);
				csvData.append(record.getDenyuScoringWait());
				csvData.append(WebAppConst.TAB_CHARACTER);
				csvData.append(record.getFirstTimeScoringTemp());
				csvData.append(WebAppConst.TAB_CHARACTER);
				csvData.append(record.getSecondTimeScoringTemp());
				csvData.append(WebAppConst.TAB_CHARACTER);
				csvData.append(record.getPendingScoringTemp());
				csvData.append(WebAppConst.TAB_CHARACTER);
				csvData.append(record.getMismatchScoringTemp());
				csvData.append(WebAppConst.TAB_CHARACTER);
				csvData.append(record.getOutOfBoundaryScoringTemp());
				csvData.append(WebAppConst.TAB_CHARACTER);
				csvData.append(record.getDenyuScoringTemp());
				csvData.append(WebAppConst.TAB_CHARACTER);
				csvData.append(record.getNoGradeScoringTemp());
				csvData.append(WebAppConst.TAB_CHARACTER);
				csvData.append(record.getScoringSamplingTemp());
				csvData.append(WebAppConst.TAB_CHARACTER);
				csvData.append(record.getForcedScoringTemp());
				csvData.append(WebAppConst.TAB_CHARACTER);
				csvData.append(record.getCheckingApproveTemp());
				csvData.append(WebAppConst.TAB_CHARACTER);
				csvData.append(record.getChekingDenyTemp());
				csvData.append(WebAppConst.TAB_CHARACTER);
				csvData.append(record.getInspectionMenuApprove());
				csvData.append(WebAppConst.TAB_CHARACTER);
				csvData.append(record.getInspectionMenuDeny());
				
				finalResultList.add(csvData.toString());
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void getMarkValueWiseReportRows(
			List<DailyStatusReportListInfo> markValueList,
			String selectedMenuId, TextProvider textProvider) {

		if (!markValueList.isEmpty()) {

			for (DailyStatusReportListInfo record : markValueList) {

				StringBuilder csvData = new StringBuilder();

				if (record.getMarkValue().equals("null")) {
					csvData.append(textProvider
							.getText("dailyStatusQuestionWise.report.total"));
				} else {
					csvData.append(record.getMarkValue());
				}

				csvData.append(WebAppConst.TAB_CHARACTER);
				csvData.append(record.getConfirmBatch());
				csvData.append(WebAppConst.TAB_CHARACTER);
				csvData.append(record.getCheckingWorkWait());
				csvData.append(WebAppConst.TAB_CHARACTER);
				csvData.append(record.getInspectionMenuWait());
				csvData.append(WebAppConst.TAB_CHARACTER);
				csvData.append(record.getDenyuScoringWait());
				csvData.append(WebAppConst.TAB_CHARACTER);
				csvData.append(record.getFirstTimeScoringTemp());
				csvData.append(WebAppConst.TAB_CHARACTER);
				csvData.append(record.getSecondTimeScoringTemp());
				csvData.append(WebAppConst.TAB_CHARACTER);
				csvData.append(record.getPendingScoringTemp());
				csvData.append(WebAppConst.TAB_CHARACTER);
				csvData.append(record.getMismatchScoringTemp());
				csvData.append(WebAppConst.TAB_CHARACTER);
				csvData.append(record.getOutOfBoundaryScoringTemp());
				csvData.append(WebAppConst.TAB_CHARACTER);
				csvData.append(record.getDenyuScoringTemp());
				csvData.append(WebAppConst.TAB_CHARACTER);
				csvData.append(record.getNoGradeScoringTemp());
				csvData.append(WebAppConst.TAB_CHARACTER);
				csvData.append(record.getScoringSamplingTemp());
				csvData.append(WebAppConst.TAB_CHARACTER);
				csvData.append(record.getForcedScoringTemp());
				csvData.append(WebAppConst.TAB_CHARACTER);
				csvData.append(record.getCheckingApproveTemp());
				csvData.append(WebAppConst.TAB_CHARACTER);
				csvData.append(record.getChekingDenyTemp());
				csvData.append(WebAppConst.TAB_CHARACTER);
				csvData.append(record.getInspectionMenuApprove());
				csvData.append(WebAppConst.TAB_CHARACTER);
				csvData.append(record.getInspectionMenuDeny());
				
				finalResultList.add(csvData.toString());
			}
		}
	}

	/**
	 * @return the mstQuestionDAO
	 */
	public MstQuestionDAO getMstQuestionDAO() {
		return mstQuestionDAO;
	}

	/**
	 * @param mstQuestionDAO
	 *            the mstQuestionDAO to set
	 */
	public void setMstQuestionDAO(MstQuestionDAO mstQuestionDAO) {
		this.mstQuestionDAO = mstQuestionDAO;
	}

	/**
	 * @return the tranDescScoreDAO
	 */
	public TranDescScoreDAO getTranDescScoreDAO() {
		return tranDescScoreDAO;
	}

	/**
	 * @param tranDescScoreDAO
	 *            the tranDescScoreDAO to set
	 */
	public void setTranDescScoreDAO(TranDescScoreDAO tranDescScoreDAO) {
		this.tranDescScoreDAO = tranDescScoreDAO;
	}

	/**
	 * @return the saitenTransactionManager
	 */
	public SaitenTransactionManager getSaitenTransactionManager() {
		return saitenTransactionManager;
	}

	/**
	 * @param saitenTransactionManager
	 *            the saitenTransactionManager to set
	 */
	public void setSaitenTransactionManager(
			SaitenTransactionManager saitenTransactionManager) {
		this.saitenTransactionManager = saitenTransactionManager;
	}

	/**
	 * @return the mstScorerDAO
	 */
	public MstScorerDAO getMstScorerDAO() {
		return mstScorerDAO;
	}

	/**
	 * @param mstScorerDAO
	 *            the mstScorerDAO to set
	 */
	public void setMstScorerDAO(MstScorerDAO mstScorerDAO) {
		this.mstScorerDAO = mstScorerDAO;
	}

	/**
	 * @return the mstDbInstanceDAO
	 */
	public MstDbInstanceDAO getMstDbInstanceDAO() {
		return mstDbInstanceDAO;
	}

	/**
	 * @param mstDbInstanceDAO
	 *            the mstDbInstanceDAO to set
	 */
	public void setMstDbInstanceDAO(MstDbInstanceDAO mstDbInstanceDAO) {
		this.mstDbInstanceDAO = mstDbInstanceDAO;
	}

	/**
	 * @return the scoreSearchService
	 */
	public ScoreSearchService getScoreSearchService() {
		return scoreSearchService;
	}

	/**
	 * @param scoreSearchService
	 *            the scoreSearchService to set
	 */
	public void setScoreSearchService(ScoreSearchService scoreSearchService) {
		this.scoreSearchService = scoreSearchService;
	}

	/**
	 * @param mstEvaluationDAO
	 *            the mstEvaluationDAO to set
	 */
	public void setMstEvaluationDAO(MstEvaluationDAO mstEvaluationDAO) {
		this.mstEvaluationDAO = mstEvaluationDAO;
	}

}