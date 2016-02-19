package com.saiten.service.impl;

import java.math.BigInteger;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.hibernate.HibernateException;

import com.saiten.bean.SaitenConfig;
import com.saiten.bean.ScoringStateKey;
import com.saiten.dao.TranDescScoreHistoryDAO;
import com.saiten.dao.TranQualitycheckScoreDAO;
import com.saiten.exception.SaitenRuntimeException;
import com.saiten.info.AnswerInfo;
import com.saiten.info.MstScorerInfo;
import com.saiten.info.QuestionInfo;
import com.saiten.info.RegisterQcScoreInfo;
import com.saiten.info.RegisterScoreInfo;
import com.saiten.model.TranDescScoreHistory;
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
	@SuppressWarnings("rawtypes")
	@Override
	public boolean registerPending(QuestionInfo questionInfo,
			MstScorerInfo scorerInfo, AnswerInfo answerInfo,
			Integer pendingCategorySeq, Short pendingCategory, Date updateDate,
			String answerFormNum) {
		boolean lockFlag = WebAppConst.FALSE;
		String menuId = questionInfo.getMenuId();
		Integer historySeq = answerInfo.getHistorySeq();

		try {
			Object[] obj = null;
			String actionName = "registerPending";
			Date logRegisterUsingStoredProcedureStartTime = new Date();
			String scorerId = scorerInfo.getScorerId();
			String subjectCode = questionInfo.getSubjectCode();
			if (menuId.equals(WebAppConst.FORCED_MENU_ID)
					&& SaitenUtil.isEvaluationInProgress(scorerId, subjectCode,
							answerFormNum, menuId)) {
				// The case in which the record of same
				// answerFormNumber, subjectCode, scorerId is evaluating
				// from 'Special Scoring'.
				lockFlag = WebAppConst.TRUE;
			} else {
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

				// Get latestScoringState based on selected menuId and
				// noDbUpdate
				Short latestScoringState = scoringStatesMap
						.get(scoringStateKey).get(1);
				int questionSeq = questionInfo.getQuestionSeq();
				Character qualityCheckFlag = null;
				if (menuId
						.equals(WebAppConst.FIRST_SCORING_QUALITY_CHECK_MENU_ID)
						|| menuId.equals(WebAppConst.FORCED_MENU_ID)) {
					qualityCheckFlag = answerInfo.getQualityCheckFlag();
				} /*else {
					qualityCheckFlag = WebAppConst.QUALITY_MARK_FLAG_FALSE;
				}*/
				String scorerComment = answerInfo.getScorerComment();
				if ((scorerComment == null) || (scorerComment.equals(""))) {
					scorerComment = null;
				}
				boolean secondAndThirdLatestScorerIdFlag = Boolean
						.valueOf(configMap
								.get("secondAndThirdLatestScorerIdFlag"));
				if (menuId.equals(WebAppConst.SCORE_SAMP_MENU_ID)
						&& (historySeq == null)) {
					TranDescScoreHistory tranDescScoreHistory = new TranDescScoreHistory();

					List<Short> scoringStateList = scoringStatesMap
							.get(scoringStateKey);

					@SuppressWarnings("unchecked")
					List<TranDescScoreHistory> tranDescScoreHistoryList = tranDescScoreHistoryDAO
							.findHistoryRecord(scorerInfo.getScorerId(),
									answerInfo.getAnswerSeq(),
									scoringStateList,
									questionInfo.getConnectionString());

					if (!tranDescScoreHistoryList.isEmpty()) {
						tranDescScoreHistory = tranDescScoreHistoryList.get(0);
					}

					if (tranDescScoreHistory.getHistorySeq() != null) {
						historySeq = tranDescScoreHistory.getHistorySeq();
					}
				}
				Double bitValue = null;
				Integer gradeSeq = null;
				Integer gradeNum = null;
				Integer denyCategorySeq = null;
				Short denyCategory = null;
				Integer historyRecordCount = null;
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
						pendingCategory, denyCategorySeq, denyCategory, menuId,
						historyRecordCount, updateDate, isScoreOrPending);
				List list = tranDescScoreHistoryDAO.registerAnswer(
						registerScoreInfo, questionInfo.getConnectionString());
				Date logRegisterUsingStoredProcedureEndTime = new Date();
				long registerByStoredProcedureTime = logRegisterUsingStoredProcedureEndTime
						.getTime()
						- logRegisterUsingStoredProcedureStartTime.getTime();
				log.info(actionName + "-Register By using Stored Procedure: "
						+ registerByStoredProcedureTime);

				obj = (Object[]) list.get(0);
				int rowCount = ((BigInteger) obj[0]).intValue();
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
			Integer pendingCategorySeq, Short pendingCategory, Date updateDate,
			String answerFormNum) {
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

		Integer gradeSeq = null;
		Integer gradeNum = null;
		Double bitValue = null;
		Date createDate = new Date();
		Date updateDateObj = createDate;
		String isScoreOrPending = "pending";
		RegisterQcScoreInfo registerQcScoreInfo = new RegisterQcScoreInfo(
				qcSeq, answerInfo.getAnswerSeq(),
				questionInfo.getQuestionSeq(), scorerInfo.getScorerId(),
				scoringState, bitValue, gradeSeq, gradeNum, pendingCategorySeq,
				pendingCategory, createDate, updateDateObj, scorerComment,
				answerFormNum, isScoreOrPending);
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

}