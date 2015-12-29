package com.saiten.service.impl;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.hibernate.HibernateException;
import org.springframework.web.context.ContextLoader;

import com.saiten.bean.SaitenConfig;
import com.saiten.bean.ScoringStateKey;
import com.saiten.dao.TranDescScoreDAO;
import com.saiten.dao.TranDescScoreHistoryDAO;
import com.saiten.dao.TranQualitycheckScoreDAO;
import com.saiten.exception.SaitenRuntimeException;
import com.saiten.info.AnswerInfo;
import com.saiten.info.MstScorerInfo;
import com.saiten.info.QuestionInfo;
import com.saiten.model.TranDescScore;
import com.saiten.model.TranDescScoreHistory;
import com.saiten.model.TranQualitycheckScore;
import com.saiten.service.RegisterDenyService;
import com.saiten.util.ErrorCode;
import com.saiten.util.SaitenUtil;
import com.saiten.util.WebAppConst;

public class RegisterDenyServiceImpl implements RegisterDenyService {

	private static Logger log = Logger.getLogger(RegisterDenyServiceImpl.class);

	private TranDescScoreDAO tranDescScoreDAO;

	private TranDescScoreHistoryDAO tranDescScoreHistoryDAO;
	private TranQualitycheckScoreDAO tranQualitycheckScoreDAO;

	@Override
	public boolean registerDeny(QuestionInfo questionInfo,
			MstScorerInfo scorerInfo, AnswerInfo answerInfo, Integer gradeSeq,
			Integer gradeNum, Integer denyCategorySeq, Short denyCategory,
			Date updateDate) {
		boolean lockFlag = WebAppConst.FALSE;
		String menuId = questionInfo.getMenuId();
		String connectionString = questionInfo.getConnectionString();
		Integer historySeq = answerInfo.getHistorySeq();

		try {
			if (historySeq != null) {
				// Load tranDescScoreHistory object
				TranDescScoreHistory tranDescScoreHistory = tranDescScoreHistoryDAO
						.findById(historySeq, connectionString);

				int answerSeq = tranDescScoreHistory.getTranDescScore()
						.getAnswerSeq();
				// Load tranDescScore object
				TranDescScore tranDescScore = tranDescScoreDAO.findById(
						answerSeq, connectionString);
				String lockBy = tranDescScore.getLockBy();

				if (lockBy != null && lockBy.equals(answerInfo.getLockBy())) {
					// Update tranDescScoreHistory for history answer evaluation
					updateTranDescScoreHistoryObject(questionInfo, scorerInfo,
							answerInfo, gradeSeq, gradeNum,
							tranDescScoreHistory, denyCategorySeq, denyCategory);
				} else {
					// lockFlag will be true, if lock is acquired by two or more
					// users
					lockFlag = WebAppConst.TRUE;
				}
			} else {
				// Load tranDescScore object
				TranDescScore tranDescScore = tranDescScoreDAO.findById(
						answerInfo.getAnswerSeq(), connectionString);
				String lockBy = tranDescScore.getLockBy();

				if (lockBy != null && lockBy.equals(answerInfo.getLockBy())) {

					String scorerId = scorerInfo.getScorerId();
					String subjectCode = questionInfo.getSubjectCode();
					String answerFormNum = tranDescScore.getAnswerFormNum();

					tranDescScore
							.setUpdateDate(SaitenUtil
									.getSaitenFormatDate(tranDescScore
											.getUpdateDate()));
					// if 'SCORE_SAMP_MENU_ID' then AnswerInfo.updateDate should
					// equal to db update_date.
					if ((menuId.equals(WebAppConst.SCORE_SAMP_MENU_ID) || menuId
							.equals(WebAppConst.FORCED_MENU_ID))
							&& (!tranDescScore.getUpdateDate().equals(
									SaitenUtil.getSaitenFormatDate(updateDate)))) {
						lockFlag = WebAppConst.TRUE;
					} else if (menuId.equals(WebAppConst.FORCED_MENU_ID)
							&& SaitenUtil.isEvaluationInProgress(scorerId,
									subjectCode, answerFormNum, menuId)) {
						// The case in which the record of same
						// answerFormNumber, subjectCode, scorerId is evaluating
						// from 'Special Scoring'.
						lockFlag = WebAppConst.TRUE;
					} else {

						// Update tranDescScore object
						updateTranDescScoreObject(questionInfo, scorerInfo,
								answerInfo, gradeSeq, gradeNum, tranDescScore,
								denyCategorySeq, denyCategory);

						TranDescScoreHistory tranDescScoreHistory = new TranDescScoreHistory();

						if (menuId.equals(WebAppConst.SCORE_SAMP_MENU_ID)) {
							ScoringStateKey scoringStateKey = (ScoringStateKey) ContextLoader
									.getCurrentWebApplicationContext().getBean(
											"scoringStateKey");

							scoringStateKey.setMenuId(menuId);
							scoringStateKey.setNoDbUpdate(scorerInfo
									.getNoDbUpdate());

							LinkedHashMap<ScoringStateKey, List<Short>> historyScoringStatesMap = ((SaitenConfig) ServletActionContext
									.getServletContext().getAttribute(
											"saitenConfigObject"))
									.getHistoryScoringStatesMap();
							List<Short> scoringStateList = historyScoringStatesMap
									.get(scoringStateKey);

							@SuppressWarnings("unchecked")
							List<TranDescScoreHistory> tranDescScoreHistoryList = tranDescScoreHistoryDAO
									.findHistoryRecord(
											scorerInfo.getScorerId(),
											answerInfo.getAnswerSeq(),
											scoringStateList, connectionString);

							if (!tranDescScoreHistoryList.isEmpty()) {
								tranDescScoreHistory = tranDescScoreHistoryList
										.get(0);
							}
						}

						if (tranDescScoreHistory.getHistorySeq() != null
								&& menuId
										.equals(WebAppConst.SCORE_SAMP_MENU_ID)) {
							answerInfo.setHistorySeq(tranDescScoreHistory
									.getHistorySeq());
							updateTranDescScoreHistoryObject(questionInfo,
									scorerInfo, answerInfo, gradeSeq, gradeNum,
									tranDescScoreHistory, denyCategorySeq,
									denyCategory);
						} else {
							// Build tranDescScoreHistory persistent object
							tranDescScoreHistory = buildTranDescScoreHistoryObj(
									tranDescScore, scorerInfo.getRoleId(),
									questionInfo.getMenuId(), answerInfo,
									tranDescScoreHistory);

							// Save tranDescScoreHistory object
							save(tranDescScoreHistory,
									questionInfo.getConnectionString());

						}
					}
				} else {
					// lockFlag will be true, if lock is acquired by two or more
					// users
					lockFlag = WebAppConst.TRUE;
				}
			}
			return lockFlag;
		} catch (HibernateException he) {
			throw new SaitenRuntimeException(
					ErrorCode.REGISTER_DENY_HIBERNATE_EXCEPTION, he);
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.REGISTER_DENY_SERVICE_EXCEPTION, e);
		}
	}

	@Override
	public boolean registerQcDeny(QuestionInfo questionInfo,
			MstScorerInfo scorerInfo, AnswerInfo answerInfo, Integer gradeSeq,
			Integer gradeNum, Integer denyCategorySeq, Short denyCategory,
			Date updateDate) {
		boolean lockFlag = WebAppConst.FALSE;
		String connectionString = questionInfo.getConnectionString();
		Integer qcSeq = answerInfo.getQcSeq();

		if (qcSeq != null) {
			TranQualitycheckScore tranQualitycheckScore = tranQualitycheckScoreDAO
					.findById(qcSeq, connectionString);
			updateTranQualitycheckScoreObj(tranQualitycheckScore, gradeSeq,
					gradeNum, answerInfo, questionInfo, scorerInfo);

		} else {
			// Load tranDescScore object
			TranDescScore tranDescScore = tranDescScoreDAO.findById(
					answerInfo.getAnswerSeq(), connectionString);

			TranQualitycheckScore tranQualitycheckScore = new TranQualitycheckScore();
			tranQualitycheckScore = buildTranQualitycheckScoreObj(questionInfo,
					scorerInfo, answerInfo, tranQualitycheckScore, gradeSeq,
					gradeNum, tranDescScore);
			save(tranQualitycheckScore, connectionString);
		}
		return lockFlag;
	}

	/**
	 * @param questionInfo
	 * @param scorerInfo
	 * @param answerInfo
	 * @param tranDescScoreHistory
	 * @param denyCategorySeq
	 * @return boolean
	 */
	private boolean updateTranDescScoreHistoryObject(QuestionInfo questionInfo,
			MstScorerInfo scorerInfo, AnswerInfo answerInfo, Integer gradeSeq,
			Integer gradeNum, TranDescScoreHistory tranDescScoreHistory,
			Integer denyCategorySeq, Short denyCategory) {

		int answerSeq = tranDescScoreHistory.getTranDescScore().getAnswerSeq();

		// Load tranDescScore object
		TranDescScore tranDescScore = tranDescScoreDAO.findById(answerSeq,
				questionInfo.getConnectionString());

		// Update tranDescScore history object
		updateTranDescScoreObject(questionInfo, scorerInfo, answerInfo,
				gradeSeq, gradeNum, tranDescScore, denyCategorySeq,
				denyCategory);
		tranDescScoreHistory.setTranDescScore(tranDescScore);

		// Build tranDescScoreHistory object to be updated
		buildTranDescScoreHistoryObj(tranDescScore, scorerInfo.getRoleId(),
				questionInfo.getMenuId(), answerInfo, tranDescScoreHistory);

		return WebAppConst.TRUE;
	}

	/**
	 * @param questionInfo
	 * @param scorerInfo
	 * @param answerInfo
	 * @param tranDescScore
	 * @param denyCategorySeq
	 * @return boolean
	 */
	private boolean updateTranDescScoreObject(QuestionInfo questionInfo,
			MstScorerInfo scorerInfo, AnswerInfo answerInfo, Integer gradeSeq,
			Integer gradeNum, TranDescScore tranDescScore,
			Integer denyCategorySeq, Short denyCategory) {

		String menuId = questionInfo.getMenuId();
		Map<String, String> configMap = SaitenUtil.getConfigMap();
		boolean secondAndThirdLatestScorerIdFlag = Boolean.valueOf(configMap
				.get("secondAndThirdLatestScorerIdFlag"));

		LinkedHashMap<ScoringStateKey, List<Short>> scoringStatesMap = ((SaitenConfig) ServletActionContext
				.getServletContext().getAttribute("saitenConfigObject"))
				.getHistoryScoringStatesMap();

		tranDescScore.setLatestScorerId(scorerInfo.getScorerId());
		tranDescScore.setLatestScreenScorerId(scorerInfo.getScorerId());

		if (secondAndThirdLatestScorerIdFlag) {
			if (answerInfo.getLatestScreenScorerId() != null) {
				tranDescScore.setSecondLatestScreenScorerId(answerInfo
						.getLatestScreenScorerId());
			}
			if (answerInfo.getSecondLatestScreenScorerId() != null) {
				tranDescScore.setThirdLatestScreenScorerId(answerInfo
						.getSecondLatestScreenScorerId());
			}
		}

		ScoringStateKey scoringStateKey = new ScoringStateKey();
		scoringStateKey.setMenuId(menuId);
		scoringStateKey.setNoDbUpdate(scorerInfo.getNoDbUpdate());

		// Get latestScoringState based on selected menuId and noDbUpdate
		Short latestScoringState = null;
		if ((menuId.equals(WebAppConst.CHECKING_MENU_ID)
				|| menuId.equals(WebAppConst.INSPECTION_MENU_ID) || menuId
					.equals(WebAppConst.MISMATCH_MENU_ID))) {
			latestScoringState = scoringStatesMap.get(scoringStateKey).get(1);
		} else {
			latestScoringState = scoringStatesMap.get(scoringStateKey).get(0);
		}

		tranDescScore.setLatestScoringState(latestScoringState);

		// Set bivValue and gradeSeq for scoring operation
		Double bitValue = answerInfo.getBitValue();
		int questionSeq = questionInfo.getQuestionSeq();
		// added extra check for bit value. If gets null, then get bit value
		// using grade sequence.
		if (bitValue == null) {
			bitValue = SaitenUtil.getBitValueByGradeSequence(gradeSeq,
					questionSeq);
		}
		tranDescScore.setBitValue(bitValue);
		tranDescScore.setGradeSeq(gradeSeq);
		tranDescScore.setGradeNum(gradeNum);

		tranDescScore.setPendingCategorySeq(null);
		tranDescScore.setPendingCategory(null);
		tranDescScore.setDenyCategory(denyCategory);
		tranDescScore.setDenyCategorySeq(denyCategorySeq);

		// Unlock answer
		tranDescScore.setLockFlag(WebAppConst.UNLOCK);
		tranDescScore.setLockBy(null);

		tranDescScore.setUpdateDate(new Date());

		// if 'SCORE_SAMP_MENU_ID' then set samplingFlag 'T'
		if (menuId.equals(WebAppConst.SCORE_SAMP_MENU_ID)) {
			tranDescScore
					.setSamplingFlag(WebAppConst.SCORE_SAMPLING_FLAG_LIST[1]);
		}
		if (menuId.equals(WebAppConst.FIRST_SCORING_QUALITY_CHECK_MENU_ID)
				|| menuId.equals(WebAppConst.FORCED_MENU_ID)) {
			tranDescScore.setQualityCheckFlag(answerInfo.getQualityCheckFlag());
		} else {
			tranDescScore
					.setQualityCheckFlag(WebAppConst.QUALITY_MARK_FLAG_FALSE);
		}
		return WebAppConst.TRUE;
	}

	/**
	 * @param tranDescScore
	 * @param scorerRoleId
	 * @param menuId
	 * @param answerInfo
	 * @param tranDescScoreHistory
	 * @return TranDescScoreHistory
	 */
	private TranDescScoreHistory buildTranDescScoreHistoryObj(
			TranDescScore tranDescScore, byte scorerRoleId, String menuId,
			AnswerInfo answerInfo, TranDescScoreHistory tranDescScoreHistory) {

		Map<String, String> configMap = SaitenUtil.getConfigMap();
		boolean secondAndThirdLatestScorerIdFlag = Boolean.valueOf(configMap
				.get("secondAndThirdLatestScorerIdFlag"));

		tranDescScoreHistory.setTranDescScore(tranDescScore);

		tranDescScoreHistory.setAnswerFormNum(tranDescScore.getAnswerFormNum());
		tranDescScoreHistory.setQuestionSeq(tranDescScore.getQuestionSeq());
		tranDescScoreHistory.setScorerId(tranDescScore.getLatestScorerId());
		tranDescScoreHistory.setLatestScreenScorerId(tranDescScore
				.getLatestScorerId());
		if (secondAndThirdLatestScorerIdFlag) {
			tranDescScoreHistory.setSecondLatestScreenScorerId(tranDescScore
					.getSecondLatestScreenScorerId());
			tranDescScoreHistory.setThirdLatestScreenScorerId(tranDescScore
					.getThirdLatestScreenScorerId());
		}

		// Set gradeSeq and bitValue for scoring operation
		tranDescScoreHistory.setGradeSeq(tranDescScore.getGradeSeq());
		tranDescScoreHistory.setGradeNum(tranDescScore.getGradeNum());
		tranDescScoreHistory.setBitValue(tranDescScore.getBitValue());

		tranDescScoreHistory.setScoringState(tranDescScore
				.getLatestScoringState());
		tranDescScoreHistory.setInspectFlag(tranDescScore.getInspectFlag());
		tranDescScoreHistory.setSamplingFlag(tranDescScore.getSamplingFlag());

		if (answerInfo.getHistorySeq() == null) {
			tranDescScoreHistory.setCreateDate(tranDescScore.getUpdateDate());
		}
		if (answerInfo.getHistorySeq() != null) {
			// pendingCategorySeq will be null for pending operation
			tranDescScoreHistory.setPendingCategorySeq(null);
			tranDescScoreHistory.setPendingCategory(null);
		}
		tranDescScoreHistory.setDenyCategory(tranDescScore.getDenyCategory());
		tranDescScoreHistory.setDenyCategorySeq(tranDescScore
				.getDenyCategorySeq());

		tranDescScoreHistory.setUpdateDate(tranDescScore.getUpdateDate());
		tranDescScoreHistory.setScorerRoleId(scorerRoleId);

		// Get scoringEventMap from saitenConfigObject
		LinkedHashMap<String, Short> scoringEventMap = ((SaitenConfig) ServletActionContext
				.getServletContext().getAttribute("saitenConfigObject"))
				.getScoringEventMap();

		// Fetch eventId corresponding to the selected menuId
		tranDescScoreHistory.setEventId(scoringEventMap.get(menuId));

		String scorerComment = answerInfo.getScorerComment();
		if ((scorerComment == null) || (scorerComment.equals(""))) {
			tranDescScoreHistory.setScorerComment(null);
		} else {
			tranDescScoreHistory
					.setScorerComment(answerInfo.getScorerComment());
		}
		tranDescScoreHistory.setBookMarkFlag(answerInfo.getBookMarkFlag());

		tranDescScoreHistory.setValidFlag(WebAppConst.VALID_FLAG);
		if (menuId.equals(WebAppConst.FIRST_SCORING_QUALITY_CHECK_MENU_ID)
				|| menuId.equals(WebAppConst.FORCED_MENU_ID)) {
			tranDescScoreHistory.setQualityCheckFlag(answerInfo
					.getQualityCheckFlag());
		} else {
			tranDescScoreHistory
					.setQualityCheckFlag(WebAppConst.QUALITY_MARK_FLAG_FALSE);
		}
		if (answerInfo.getHistorySeq() != null) {
			log.info(tranDescScore.getLatestScorerId() + "-" + menuId + "-"
					+ "History Tran record scoring." + "-{ Question Sequence: "
					+ tranDescScore.getQuestionSeq() + ", Answer Sequence: "
					+ answerInfo.getAnswerSeq() + ", Scoring State: "
					+ tranDescScore.getLatestScoringState() + ", Bit Value: "
					+ answerInfo.getBitValue() + "}");
		} else {
			log.info(tranDescScore.getLatestScorerId() + "-" + menuId + "-"
					+ "Tran record scoring." + "-{ Question Sequence: "
					+ tranDescScore.getQuestionSeq() + ", Answer Sequence: "
					+ answerInfo.getAnswerSeq() + ", Scoring State: "
					+ tranDescScore.getLatestScoringState() + ", Bit Value: "
					+ answerInfo.getBitValue() + "}");
		}
		return tranDescScoreHistory;
	}

	private void updateTranQualitycheckScoreObj(
			TranQualitycheckScore tranQualitycheckScore, Integer gradeSeq,
			Integer gradeNum, AnswerInfo answerInfo, QuestionInfo questionInfo,
			MstScorerInfo scorerInfo) {
		String menuId = questionInfo.getMenuId();
		LinkedHashMap<ScoringStateKey, List<Short>> scoringStatesMap = ((SaitenConfig) ServletActionContext
				.getServletContext().getAttribute("saitenConfigObject"))
				.getHistoryScoringStatesMap();
		// Set bivValue and gradeSeq for scoring operation
		tranQualitycheckScore.setBitValue(answerInfo.getBitValue());
		tranQualitycheckScore.setGradeSeq(gradeSeq);
		tranQualitycheckScore.setGradeNum(gradeNum);
		tranQualitycheckScore.setBitValue(answerInfo.getBitValue());
		tranQualitycheckScore.setUpdateDate(new Date());
		tranQualitycheckScore.setPendingCategorySeq(null);
		tranQualitycheckScore.setPendingCategory(null);
		ScoringStateKey scoringStateKey = new ScoringStateKey();
		scoringStateKey.setMenuId(menuId);
		scoringStateKey.setNoDbUpdate(scorerInfo.getNoDbUpdate());

		// Get latestScoringState based on selected menuId and noDbUpdate
		Short latestScoringState = scoringStatesMap.get(scoringStateKey).get(0);
		tranQualitycheckScore.setScoringState(latestScoringState);
		tranQualitycheckScore.setScorerComment(answerInfo.getScorerComment());
		log.info(scorerInfo.getScorerId() + "-" + menuId + "-"
				+ "History Quality record scoring." + "-{ Question Sequence: "
				+ questionInfo.getQuestionSeq() + ", Answer Sequence: "
				+ answerInfo.getAnswerSeq() + ", Scoring State: "
				+ latestScoringState + ", Bit Value: "
				+ answerInfo.getBitValue() + "}");
	}

	private TranQualitycheckScore buildTranQualitycheckScoreObj(
			QuestionInfo questionInfo, MstScorerInfo scorerInfo,
			AnswerInfo answerInfo, TranQualitycheckScore tranQualitycheckScore,
			Integer gradeSeq, Integer gradeNum, TranDescScore tranDescScore) {

		String menuId = questionInfo.getMenuId();
		LinkedHashMap<ScoringStateKey, List<Short>> scoringStatesMap = ((SaitenConfig) ServletActionContext
				.getServletContext().getAttribute("saitenConfigObject"))
				.getHistoryScoringStatesMap();

		tranQualitycheckScore.setQuestionSeq(questionInfo.getQuestionSeq());
		tranQualitycheckScore.setScorerId(scorerInfo.getScorerId());
		tranQualitycheckScore.setTranDescScore(tranDescScore);

		ScoringStateKey scoringStateKey = new ScoringStateKey();
		scoringStateKey.setMenuId(menuId);
		scoringStateKey.setNoDbUpdate(scorerInfo.getNoDbUpdate());

		// Get latestScoringState based on selected menuId and noDbUpdate
		Short latestScoringState = null;
		if ((menuId.equals(WebAppConst.CHECKING_MENU_ID)
				|| menuId.equals(WebAppConst.INSPECTION_MENU_ID) || menuId
					.equals(WebAppConst.MISMATCH_MENU_ID))) {
			latestScoringState = scoringStatesMap.get(scoringStateKey).get(1);
		} else {
			latestScoringState = scoringStatesMap.get(scoringStateKey).get(0);
		}

		tranQualitycheckScore.setScoringState(latestScoringState);
		// Set bivValue and gradeSeq for scoring operation
		tranQualitycheckScore.setBitValue(answerInfo.getBitValue());
		tranQualitycheckScore.setGradeSeq(gradeSeq);
		tranQualitycheckScore.setGradeNum(gradeNum);

		if (answerInfo.getHistorySeq() == null) {
			tranQualitycheckScore.setCreateDate(new Date());
		}
		if (answerInfo.getHistorySeq() != null) {
			// pendingCategorySeq will be null for pending operation
			tranQualitycheckScore.setPendingCategorySeq(null);
			tranQualitycheckScore.setPendingCategory(null);
		}
		tranQualitycheckScore.setValidFlag(WebAppConst.VALID_FLAG);
		tranQualitycheckScore.setRefFlag(WebAppConst.F);

		tranQualitycheckScore.setUpdateDate(new Date());

		String scorerComment = answerInfo.getScorerComment();
		if ((scorerComment == null) || (scorerComment.equals(""))) {
			tranQualitycheckScore.setScorerComment(null);
		} else {
			tranQualitycheckScore.setScorerComment(answerInfo
					.getScorerComment());
		}
		tranQualitycheckScore
				.setAnswerFormNum(tranDescScore.getAnswerFormNum());
		log.info(scorerInfo.getScorerId() + "-" + menuId + "-"
				+ "Quality record scoring." + "-{ Question Sequence: "
				+ questionInfo.getQuestionSeq() + ", Answer Sequence: "
				+ answerInfo.getAnswerSeq() + ", Scoring State: "
				+ latestScoringState + ", Bit Value: "
				+ answerInfo.getBitValue() + "}");
		return tranQualitycheckScore;
	}

	/**
	 * @param tranQualitycheckScore
	 * @param connectionString
	 */
	private void save(TranQualitycheckScore tranQualitycheckScore,
			String connectionString) {
		tranQualitycheckScoreDAO.save(tranQualitycheckScore, connectionString);
	}

	/**
	 * @param tranDescScoreHistory
	 * @param connectionString
	 */
	private void save(TranDescScoreHistory tranDescScoreHistory,
			String connectionString) {
		tranDescScoreHistoryDAO.save(tranDescScoreHistory, connectionString);
	}

	public TranDescScoreDAO getTranDescScoreDAO() {
		return tranDescScoreDAO;
	}

	public void setTranDescScoreDAO(TranDescScoreDAO tranDescScoreDAO) {
		this.tranDescScoreDAO = tranDescScoreDAO;
	}

	public TranDescScoreHistoryDAO getTranDescScoreHistoryDAO() {
		return tranDescScoreHistoryDAO;
	}

	public void setTranDescScoreHistoryDAO(
			TranDescScoreHistoryDAO tranDescScoreHistoryDAO) {
		this.tranDescScoreHistoryDAO = tranDescScoreHistoryDAO;
	}

	public TranQualitycheckScoreDAO getTranQualitycheckScoreDAO() {
		return tranQualitycheckScoreDAO;
	}

	public void setTranQualitycheckScoreDAO(
			TranQualitycheckScoreDAO tranQualitycheckScoreDAO) {
		this.tranQualitycheckScoreDAO = tranQualitycheckScoreDAO;
	}

}
