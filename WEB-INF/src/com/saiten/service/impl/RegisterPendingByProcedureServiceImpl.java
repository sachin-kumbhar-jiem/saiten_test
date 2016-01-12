package com.saiten.service.impl;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.hibernate.HibernateException;

import com.saiten.bean.SaitenConfig;
import com.saiten.bean.ScoringStateKey;
import com.saiten.dao.TranDescScoreDAO;
import com.saiten.dao.TranDescScoreHistoryDAO;
import com.saiten.dao.TranQualitycheckScoreDAO;
import com.saiten.exception.SaitenRuntimeException;
import com.saiten.info.AnswerInfo;
import com.saiten.info.MstScorerInfo;
import com.saiten.info.QuestionInfo;
import com.saiten.info.RegisterScoreInfo;
import com.saiten.model.TranDescScore;
import com.saiten.model.TranQualitycheckScore;
import com.saiten.service.RegisterPendingByProcedureService;
import com.saiten.util.ErrorCode;
import com.saiten.util.SaitenUtil;
import com.saiten.util.WebAppConst;

/**
 * @author sachin
 * @version 1.0
 * @created 24-Dec-2012 12:33:21 PM
 */
public class RegisterPendingByProcedureServiceImpl implements
		RegisterPendingByProcedureService {

	private static Logger log = Logger
			.getLogger(RegisterPendingServiceImpl.class);

	private TranDescScoreDAO tranDescScoreDAO;

	private TranDescScoreHistoryDAO tranDescScoreHistoryDAO;
	private TranQualitycheckScoreDAO tranQualitycheckScoreDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.saiten.service.RegisterPendingService#registerPending(com.saiten.
	 * info.QuestionInfo, com.saiten.info.MstScorerInfo,
	 * com.saiten.info.AnswerInfo, java.lang.Integer)
	 */
	@Override
	public boolean registerPending(QuestionInfo questionInfo,
			MstScorerInfo scorerInfo, AnswerInfo answerInfo,
			Integer pendingCategorySeq, Short pendingCategory, Date updateDate) {
		boolean lockFlag = WebAppConst.FALSE;
		String menuId = questionInfo.getMenuId();
		Integer historySeq = answerInfo.getHistorySeq();

		try {
			String actionName = "registerPending";
			Date logRegisterUsingStoredProcedureStartTime = new Date();
			LinkedHashMap<ScoringStateKey, List<Short>> scoringStatesMap = ((SaitenConfig) ServletActionContext
					.getServletContext().getAttribute("saitenConfigObject"))
					.getHistoryScoringStatesMap();
			LinkedHashMap<String, Short> scoringEventMap = ((SaitenConfig) ServletActionContext
					.getServletContext().getAttribute("saitenConfigObject"))
					.getScoringEventMap();
			Map<String, String> configMap = SaitenUtil.getConfigMap();
			ScoringStateKey scoringStateKey = new ScoringStateKey();
			scoringStateKey.setMenuId(menuId);
			scoringStateKey.setNoDbUpdate(scorerInfo.getNoDbUpdate());

			// Get latestScoringState based on selected menuId and noDbUpdate
			Short latestScoringState = scoringStatesMap.get(scoringStateKey)
					.get(1);
			int questionSeq = questionInfo.getQuestionSeq();
			Character qualityCheckFlag = null;
			if (menuId.equals(WebAppConst.FIRST_SCORING_QUALITY_CHECK_MENU_ID)) {
				qualityCheckFlag = answerInfo.getQualityCheckFlag();
			} else {
				qualityCheckFlag = WebAppConst.QUALITY_MARK_FLAG_FALSE;
			}
			String scorerComment = answerInfo.getScorerComment();
			if ((scorerComment == null) || (scorerComment.equals(""))) {
				scorerComment = null;
			}
			boolean secondAndThirdLatestScorerIdFlag = Boolean
					.valueOf(configMap.get("secondAndThirdLatestScorerIdFlag"));
			Double bitValue = null;
			Integer gradeSeq = null;
			Integer gradeNum = null;
			String isScoreOrPending = "pending";
			RegisterScoreInfo registerScoreInfo = new RegisterScoreInfo(
					answerInfo.getAnswerSeq(), scorerInfo.getScorerId(),
					answerInfo.getLatestScreenScorerId(),
					answerInfo.getSecondLatestScreenScorerId(),
					latestScoringState, bitValue, gradeSeq, gradeNum,
					new Date(), qualityCheckFlag, scorerInfo.getRoleId(),
					scoringEventMap.get(menuId), scorerComment,
					answerInfo.getBookMarkFlag(), historySeq,
					secondAndThirdLatestScorerIdFlag, pendingCategorySeq,
					pendingCategory, isScoreOrPending);
			int rowCount = tranDescScoreHistoryDAO.registerAnswer(
					registerScoreInfo, questionInfo.getConnectionString());
			Date logRegisterUsingStoredProcedureEndTime = new Date();
			long registerByStoredProcedureTime = logRegisterUsingStoredProcedureEndTime
					.getTime()
					- logRegisterUsingStoredProcedureStartTime.getTime();
			log.info(actionName + "-Register By using Stored Procedure: "
					+ registerByStoredProcedureTime);
			if (!(rowCount > 0)) {
				lockFlag = WebAppConst.TRUE;
			} else {
				lockFlag = WebAppConst.FALSE;
			}

			if (answerInfo.getHistorySeq() != null) {
				log.info(scorerInfo.getScorerId() + "-" + menuId + "-"
						+ "History Tran record Pending."
						+ "-{ Question Sequence: " + questionSeq
						+ ", Answer Sequence: " + answerInfo.getAnswerSeq()
						+ ", Scoring State: " + latestScoringState
						+ ", Pending Category: " + pendingCategory + "}");
			} else {
				log.info(scorerInfo.getScorerId() + "-" + menuId + "-"
						+ "Tran record Pending." + "-{ Question Sequence: "
						+ questionSeq + ", Answer Sequence: "
						+ answerInfo.getAnswerSeq() + ", Scoring State: "
						+ latestScoringState + ", Pending Category: "
						+ pendingCategory + "}");
			}

			return lockFlag;
		} catch (HibernateException he) {
			throw new SaitenRuntimeException(
					ErrorCode.REGISTER_PENDING_HIBERNATE_EXCEPTION, he);
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.REGISTER_PENDING_SERVICE_EXCEPTION, e);
		}

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
	 * @param tranDescScoreDAO
	 */
	public void setTranDescScoreDAO(TranDescScoreDAO tranDescScoreDAO) {
		this.tranDescScoreDAO = tranDescScoreDAO;
	}

	/**
	 * @param tranDescScoreHistoryDAO
	 */
	public void setTranDescScoreHistoryDAO(
			TranDescScoreHistoryDAO tranDescScoreHistoryDAO) {
		this.tranDescScoreHistoryDAO = tranDescScoreHistoryDAO;
	}

	/**
	 * @param tranQualitycheckScoreDAO
	 *            the tranQualitycheckScoreDAO to set
	 */
	public void setTranQualitycheckScoreDAO(
			TranQualitycheckScoreDAO tranQualitycheckScoreDAO) {
		this.tranQualitycheckScoreDAO = tranQualitycheckScoreDAO;
	}

	public boolean registerQcPending(QuestionInfo questionInfo,
			MstScorerInfo scorerInfo, AnswerInfo answerInfo,
			Integer pendingCategorySeq, Short pendingCategory, Date updateDate) {
		boolean lockFlag = WebAppConst.FALSE;
		String connectionString = questionInfo.getConnectionString();
		Integer qcSeq = answerInfo.getQcSeq();

		if (qcSeq != null) {
			TranQualitycheckScore tranQualitycheckScore = tranQualitycheckScoreDAO
					.findById(qcSeq, connectionString);
			updateTranQualitycheckScoreObj(tranQualitycheckScore,
					pendingCategorySeq, pendingCategory, answerInfo,
					questionInfo, scorerInfo);
		} else {
			// Load tranDescScore object
			TranDescScore tranDescScore = tranDescScoreDAO.findById(
					answerInfo.getAnswerSeq(), connectionString);

			TranQualitycheckScore tranQualitycheckScore = new TranQualitycheckScore();
			tranQualitycheckScore = buildTranQualitycheckScoreObj(questionInfo,
					scorerInfo, answerInfo, tranQualitycheckScore,
					pendingCategorySeq, pendingCategory, tranDescScore);
			save(tranQualitycheckScore, connectionString);
		}

		return lockFlag;

	}

	private void updateTranQualitycheckScoreObj(
			TranQualitycheckScore tranQualitycheckScore,
			Integer pendingCategorySeq, Short pendingCategory,
			AnswerInfo answerInfo, QuestionInfo questionInfo,
			MstScorerInfo scorerInfo) {
		String menuId = questionInfo.getMenuId();
		LinkedHashMap<ScoringStateKey, List<Short>> scoringStatesMap = ((SaitenConfig) ServletActionContext
				.getServletContext().getAttribute("saitenConfigObject"))
				.getHistoryScoringStatesMap();

		// Set bivValue and gradeSeq for scoring operation
		tranQualitycheckScore.setBitValue(answerInfo.getBitValue());
		tranQualitycheckScore.setGradeSeq(null);
		tranQualitycheckScore.setGradeNum(null);
		tranQualitycheckScore.setBitValue(null);
		tranQualitycheckScore.setUpdateDate(new Date());
		tranQualitycheckScore.setPendingCategorySeq(pendingCategorySeq);
		tranQualitycheckScore.setPendingCategory(pendingCategory);

		ScoringStateKey scoringStateKey = new ScoringStateKey();
		scoringStateKey.setMenuId(menuId);
		scoringStateKey.setNoDbUpdate(scorerInfo.getNoDbUpdate());

		// Get latestScoringState based on selected menuId and noDbUpdate
		Short latestScoringState = scoringStatesMap.get(scoringStateKey).get(1);
		tranQualitycheckScore.setScoringState(latestScoringState);
		tranQualitycheckScore.setScorerComment(answerInfo.getScorerComment());
		log.info(scorerInfo.getScorerId() + "-" + menuId + "-"
				+ "History Quality record pending." + "-{ Question Sequence: "
				+ questionInfo.getQuestionSeq() + ", Answer Sequence: "
				+ answerInfo.getAnswerSeq() + ", Scoring State: "
				+ latestScoringState + ", Pending Category: " + pendingCategory
				+ "}");
	}

	private TranQualitycheckScore buildTranQualitycheckScoreObj(
			QuestionInfo questionInfo, MstScorerInfo scorerInfo,
			AnswerInfo answerInfo, TranQualitycheckScore tranQualitycheckScore,
			Integer pendingCategorySeq, Short pendingCategory,
			TranDescScore tranDescScore) {

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
		Short latestScoringState = scoringStatesMap.get(scoringStateKey).get(1);
		tranQualitycheckScore.setScoringState(latestScoringState);
		// Set pendingCategorySeq for pending operation
		tranQualitycheckScore.setPendingCategorySeq(pendingCategorySeq);
		tranQualitycheckScore.setPendingCategory(pendingCategory);

		tranQualitycheckScore.setGradeSeq(null);
		tranQualitycheckScore.setGradeNum(null);
		tranQualitycheckScore.setBitValue(null);

		tranQualitycheckScore.setValidFlag(WebAppConst.VALID_FLAG);
		tranQualitycheckScore.setRefFlag(WebAppConst.F);
		tranQualitycheckScore.setCreateDate(new Date());
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
				+ "Quality record pending." + "-{ Question Sequence: "
				+ questionInfo.getQuestionSeq() + ", Answer Sequence: "
				+ answerInfo.getAnswerSeq() + ", Scoring State: "
				+ latestScoringState + ", Pending Category: " + pendingCategory
				+ "}");
		return tranQualitycheckScore;
	}

}