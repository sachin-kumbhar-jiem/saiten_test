package com.saiten.util;

import java.text.ParseException;
import java.util.Date;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.web.context.ContextLoader;

import com.saiten.info.MstScorerInfo;
import com.saiten.info.QuestionInfo;
import com.saiten.info.ScorerAccessLogInfo;
import com.saiten.manager.SaitenTransactionManager;
import com.saiten.model.TranScorerSessionInfo;
import com.saiten.service.ScoreService;

/**
 * @author sachin
 * @version 1.0
 * @created 06-Dec-2012 2:32:49 PM
 */
public class UnlockAnswerUtil implements HttpSessionListener {

	private static SaitenTransactionManager saitenTransactionManager = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http
	 * .HttpSessionEvent)
	 */
	public void sessionCreated(HttpSessionEvent event) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet
	 * .http.HttpSessionEvent)
	 */
	public void sessionDestroyed(HttpSessionEvent event) {
		QuestionInfo questionInfo = (QuestionInfo) event.getSession()
				.getAttribute("questionInfo");
		MstScorerInfo mstScorerInfo = (MstScorerInfo) event.getSession()
				.getAttribute("scorerInfo");
		TranScorerSessionInfo sessionTranScorerSessionInfo = (TranScorerSessionInfo) event
				.getSession().getAttribute("tranScorerSessionInfo");
		ApplicationContext ctx = ContextLoader
				.getCurrentWebApplicationContext();
		TranScorerSessionInfo tranScorerSessionInfo = null;
		if (mstScorerInfo != null) {
			tranScorerSessionInfo = ((SaitenMasterUtil) ctx
					.getBean("saitenMasterUtil"))
					.getUserSessionInfoById(mstScorerInfo.getScorerId());
			;
		}

		// If entry made in tran_scorer_session_info by current session, then
		// clears entry while session timeout.

		if ((tranScorerSessionInfo != null)
				&& (sessionTranScorerSessionInfo != null)) {
			Date dbUpdateDate = null;
			Date sessionUpdateDate = null;
			try {
				dbUpdateDate = SaitenUtil
						.getSaitenFormatDate(tranScorerSessionInfo
								.getUpdateDate());
				sessionUpdateDate = SaitenUtil
						.getSaitenFormatDate(sessionTranScorerSessionInfo
								.getUpdateDate());

				if (dbUpdateDate.equals(sessionUpdateDate)) {
					if (questionInfo != null) {

						int questionSeq = questionInfo.getQuestionSeq();
						String connectionString = questionInfo
								.getConnectionString();
						String menuId = questionInfo.getMenuId();
						String lockBy = ((MstScorerInfo) event.getSession()
								.getAttribute("scorerInfo")).getScorerId();

						if (!StringUtils.isBlank(lockBy)
								&& !StringUtils.isBlank(connectionString)
								&& !menuId
										.equals(WebAppConst.REFERENCE_SAMP_MENU_ID)) {
							// Unlock answer when session is invalidated
							Integer answerSeq = null;
							unlockAnswer(questionSeq, lockBy, connectionString,
									answerSeq);
							tranScorerSessionInfo.setQuestionSeq(null);
						}

						if (menuId
								.equals(WebAppConst.SPECIAL_SCORING_OMR_READ_FAIL_MENU_ID)
								|| menuId
										.equals(WebAppConst.SPECIAL_SCORING_ENLARGE_TYPE_MENU_ID)) {
							/*
							 * String answerFormNumber = (String)
							 * event.getSession()
							 * .getAttribute("answerFormNum");
							 * 
							 * SaitenUtil.updateSpecialScoringMap(
							 * questionInfo.getSubjectCode(), answerFormNumber);
							 */

							tranScorerSessionInfo.setAnswerFormNum(null);
							tranScorerSessionInfo.setSubjectCode(null);
						}
					}
				}

			} catch (ParseException e1) {
				e1.printStackTrace();
			}

			tranScorerSessionInfo.setUpdateDate(new Date());
			// Clear questionSeq, AnswerFormNum, SubjectCoed from
			// tran_scorer_session_info.
			((SaitenMasterUtil) ctx.getBean("saitenMasterUtil"))
					.updateUserSessionInfo(tranScorerSessionInfo);
		}

		// update user logging information.
		ScorerAccessLogInfo scorerAccessLogInfo = (ScorerAccessLogInfo) event
				.getSession().getAttribute("scorerAccessLogInfo");
		if (scorerAccessLogInfo != null) {
			scorerAccessLogInfo.setLogoutTime(new Date());
			scorerAccessLogInfo.setStatus(WebAppConst.SCORER_LOGGING_STATUS[2]);
			((SaitenMasterUtil) ctx.getBean("saitenMasterUtil"))
					.updateUserLoggingInformation(scorerAccessLogInfo);
		}
	}

	/**
	 * @param questionSeq
	 * @param lockBy
	 * @param connectionString
	 * @return String
	 */
	public static String unlockAnswer(int questionSeq, String lockBy,
			String connectionString, Integer answerSeq) {
		PlatformTransactionManager platformTransactionManager = null;
		TransactionStatus transactionStatus = null;
		try {
			ApplicationContext ctx = ContextLoader
					.getCurrentWebApplicationContext();

			saitenTransactionManager = (SaitenTransactionManager) ctx
					.getBean("saitenTransactionManager");
			platformTransactionManager = saitenTransactionManager
					.getTransactionManger();
			transactionStatus = saitenTransactionManager
					.beginTransaction(platformTransactionManager);

			// Get current WebApp Context

			ScoreService scoreService = (ScoreService) ctx
					.getBean("scoreService");
			scoreService.unlockAnswer(questionSeq, lockBy, connectionString,
					answerSeq);
			platformTransactionManager.commit(transactionStatus);

		} catch (Exception e) {
			if (platformTransactionManager != null)
				platformTransactionManager.rollback(transactionStatus);
		}
		return "success";
	}

}
