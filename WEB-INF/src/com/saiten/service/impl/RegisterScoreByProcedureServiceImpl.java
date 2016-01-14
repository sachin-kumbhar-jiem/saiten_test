package com.saiten.service.impl;

import java.util.ArrayList;
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
import com.saiten.info.RegisterQcScoreInfo;
import com.saiten.info.RegisterScoreInfo;
import com.saiten.info.ScoreInputInfo;
import com.saiten.info.ScoreSamplingInfo;
import com.saiten.service.RegisterScoreByProcedureService;
import com.saiten.util.ErrorCode;
import com.saiten.util.SaitenUtil;
import com.saiten.util.WebAppConst;

/**
 * @author sachin
 * @version 1.0
 * @created 13-Dec-2012 11:35:05 AM
 */
public class RegisterScoreByProcedureServiceImpl implements
		RegisterScoreByProcedureService {

	private static Logger log = Logger
			.getLogger(RegisterScoreServiceImpl.class);

	private TranDescScoreDAO tranDescScoreDAO;

	private TranDescScoreHistoryDAO tranDescScoreHistoryDAO;
	private ScoreSamplingInfo scoreSamplingInfo;
	private TranQualitycheckScoreDAO tranQualitycheckScoreDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.saiten.service.RegisterScoreService#registerScoring(com.saiten.info
	 * .QuestionInfo, com.saiten.info.MstScorerInfo, com.saiten.info.AnswerInfo,
	 * java.lang.Integer, java.lang.String)
	 */
	@Override
	public boolean registerScoring(QuestionInfo questionInfo,
			MstScorerInfo scorerInfo, AnswerInfo answerInfo, Integer gradeSeq,
			Integer gradeNum, Integer denyCategorySeq, Short denyCategory,
			String approveOrDeny, Date updateDate, Integer historyRecordCount) {
		boolean lockFlag = WebAppConst.FALSE;
		String menuId = questionInfo.getMenuId();
		Integer historySeq = answerInfo.getHistorySeq();

		try {

			String actionName = "registerScore";
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
			Short latestScoringState = null;
			if ((menuId.equals(WebAppConst.CHECKING_MENU_ID)
					|| menuId.equals(WebAppConst.INSPECTION_MENU_ID) || menuId
						.equals(WebAppConst.MISMATCH_MENU_ID))
					&& (approveOrDeny.equals(WebAppConst.DENY))) {
				latestScoringState = scoringStatesMap.get(scoringStateKey).get(
						1);
			} else {
				latestScoringState = scoringStatesMap.get(scoringStateKey).get(
						0);
			}
			// Set bivValue and gradeSeq for scoring operation
			Double bitValue = answerInfo.getBitValue();
			int questionSeq = questionInfo.getQuestionSeq();
			// added extra check for bit value. If gets null, then get bit value
			// using grade sequence.
			if (bitValue == null) {
				bitValue = SaitenUtil.getBitValueByGradeSequence(gradeSeq,
						questionSeq);
			}
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
			Integer pendingCategorySeq = null;
			Short pendingCategory = null;
			String isScoreOrPending = "score";
			RegisterScoreInfo registerScoreInfo = new RegisterScoreInfo(
					answerInfo.getAnswerSeq(), scorerInfo.getScorerId(),
					answerInfo.getLatestScreenScorerId(),
					answerInfo.getSecondLatestScreenScorerId(),
					latestScoringState, bitValue, gradeSeq, gradeNum,
					new Date(), qualityCheckFlag, scorerInfo.getRoleId(),
					scoringEventMap.get(menuId), scorerComment,
					answerInfo.getBookMarkFlag(), historySeq,
					secondAndThirdLatestScorerIdFlag, pendingCategorySeq,
					pendingCategory, denyCategorySeq, denyCategory,
					isScoreOrPending);
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
						+ "History Tran record scoring."
						+ "-{ Question Sequence: " + questionSeq
						+ ", Answer Sequence: " + answerInfo.getAnswerSeq()
						+ ", Scoring State: " + latestScoringState
						+ ", Bit Value: " + bitValue + "}");
			} else {
				log.info(scorerInfo.getScorerId() + "-" + menuId + "-"
						+ "Tran record scoring." + "-{ Question Sequence: "
						+ questionSeq + ", Answer Sequence: "
						+ answerInfo.getAnswerSeq() + ", Scoring State: "
						+ latestScoringState + ", Bit Value: " + bitValue + "}");
			}

			return lockFlag;
		} catch (HibernateException he) {
			throw new SaitenRuntimeException(
					ErrorCode.REGISTER_SCORE_HIBERNATE_EXCEPTION, he);
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.REGISTER_SCORE_SERVICE_EXCEPTION, e);
		}

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

	public boolean registerQcScoring(QuestionInfo questionInfo,
			MstScorerInfo scorerInfo, AnswerInfo answerInfo, Integer gradeSeq,
			Integer gradeNum, String approveOrDeny, Date updateDate,
			Integer historyRecordCount, String answerFormNum) {
		boolean lockFlag = WebAppConst.FALSE;
		String connectionString = questionInfo.getConnectionString();
		Integer qcSeq = answerInfo.getQcSeq();

		LinkedHashMap<ScoringStateKey, List<Short>> scoringStatesMap = ((SaitenConfig) ServletActionContext
				.getServletContext().getAttribute("saitenConfigObject"))
				.getHistoryScoringStatesMap();
		ScoringStateKey scoringStateKey = new ScoringStateKey();
		scoringStateKey.setMenuId(questionInfo.getMenuId());
		scoringStateKey.setNoDbUpdate(scorerInfo.getNoDbUpdate());
		// Get latestScoringState based on selected menuId and noDbUpdate
		Short scoringState = scoringStatesMap.get(scoringStateKey).get(0);

		String scorerComment = answerInfo.getScorerComment();
		if ((scorerComment == null) || (scorerComment.equals(""))) {
			scorerComment = null;
		}

		Integer pendingCategorySeq = null;
		Short pendingCategory = null;
		Date createDate = new Date();
		Date updateDateObj = createDate;
		String isScoreOrPending = "score";
		RegisterQcScoreInfo registerQcScoreInfo = new RegisterQcScoreInfo(
				qcSeq, answerInfo.getAnswerSeq(),
				questionInfo.getQuestionSeq(), scorerInfo.getScorerId(),
				scoringState, answerInfo.getBitValue(), gradeSeq, gradeNum,
				pendingCategorySeq, pendingCategory, createDate, updateDateObj,
				scorerComment, answerFormNum, isScoreOrPending);
		int rowCount = tranQualitycheckScoreDAO.registerQcAnswer(
				registerQcScoreInfo, connectionString);
		if (!(rowCount > 0)) {
			lockFlag = WebAppConst.TRUE;
		} else {
			lockFlag = WebAppConst.FALSE;
		}

		if (qcSeq != null) {
			log.info(scorerInfo.getScorerId() + "-" + questionInfo.getMenuId()
					+ "-" + "History Quality record scoring."
					+ "-{ Question Sequence: " + questionInfo.getQuestionSeq()
					+ ", Answer Sequence: " + answerInfo.getAnswerSeq()
					+ ", Scoring State: " + scoringState + ", Bit Value: "
					+ answerInfo.getBitValue() + "}");

		} else {
			log.info(scorerInfo.getScorerId() + "-" + questionInfo.getMenuId()
					+ "-" + "Quality record scoring."
					+ "-{ Question Sequence: " + questionInfo.getQuestionSeq()
					+ ", Answer Sequence: " + answerInfo.getAnswerSeq()
					+ ", Scoring State: " + scoringState + ", Bit Value: "
					+ answerInfo.getBitValue() + "}");
		}
		return lockFlag;
	}

	public int updateInspectFlag(List<Integer> answerSeq,
			QuestionInfo questionInfo,
			List<ScoreSamplingInfo> scoreSamplingInfoList,
			boolean selectAllFlag, ScoreInputInfo scoreInputInfo,
			Integer maxInspectGroupSeq) {
		int updatedCount = 0;
		if (!selectAllFlag) {
			if (answerSeq != null && !answerSeq.isEmpty()) {
				updatedCount = tranDescScoreDAO.updateInspectFlag(answerSeq,
						questionInfo, selectAllFlag, scoreInputInfo,
						maxInspectGroupSeq);

			}
		} else {
			List<Integer> answerSeqList = new ArrayList<Integer>();
			updatedCount = tranDescScoreDAO.updateInspectFlag(answerSeqList,
					questionInfo, selectAllFlag, scoreInputInfo,
					maxInspectGroupSeq);
		}
		return updatedCount;
	}

	/**
	 * @return the scoreSamplingInfo
	 */
	public ScoreSamplingInfo getScoreSamplingInfo() {
		return scoreSamplingInfo;
	}

	/**
	 * @param scoreSamplingInfo
	 *            the scoreSamplingInfo to set
	 */
	public void setScoreSamplingInfo(ScoreSamplingInfo scoreSamplingInfo) {
		this.scoreSamplingInfo = scoreSamplingInfo;
	}

	/**
	 * @param tranQualitycheckScoreDAO
	 *            the tranQualitycheckScoreDAO to set
	 */
	public void setTranQualitycheckScoreDAO(
			TranQualitycheckScoreDAO tranQualitycheckScoreDAO) {
		this.tranQualitycheckScoreDAO = tranQualitycheckScoreDAO;
	}
}