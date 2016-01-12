package com.saiten.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;

import com.opensymphony.xwork2.ActionSupport;
import com.saiten.exception.SaitenRuntimeException;
import com.saiten.info.AnswerInfo;
import com.saiten.info.MstScorerInfo;
import com.saiten.info.QuestionInfo;
import com.saiten.info.TranDescScoreInfo;
import com.saiten.manager.SaitenTransactionManager;
import com.saiten.service.RegisterPendingByProcedureService;
import com.saiten.service.RegisterPendingService;
import com.saiten.util.ErrorCode;
import com.saiten.util.SaitenUtil;
import com.saiten.util.WebAppConst;

/**
 * @author sachin
 * @version 1.0
 * @created 24-Dec-2012 12:22:57 PM
 */
public class RegisterPendingAction extends ActionSupport implements
		SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RegisterPendingService registerPendingService;
	private RegisterPendingByProcedureService registerPendingByProcedureService;
	private Map<String, Object> session;
	private SaitenTransactionManager saitenTransactionManager;
	private Integer pendingCategorySeq;
	private boolean bookMarkFlag;
	private boolean lockFlag;
	public static final String FAILURE = "failure";
	public static final String QUERY_STRING_SCORER_COMMENT = "&scorerComment=";
	public static final String SCORE_SAMPLING = "scoreSampling";
	public static final String BACK_TO_FORCED_SCORING_LIST = "backToForcedScoringList";
	private boolean qualityCheckFlag;

	@SuppressWarnings("unchecked")
	public String doRegister() {

		PlatformTransactionManager platformTransactionManager = null;
		TransactionStatus transactionStatus = null;

		QuestionInfo questionInfo = (QuestionInfo) session.get("questionInfo");

		try {
			MstScorerInfo scorerInfo = (MstScorerInfo) session
					.get("scorerInfo");
			String menuId = questionInfo.getMenuId();
			TranDescScoreInfo tranDescScoreInfo = (TranDescScoreInfo) session
					.get("tranDescScoreInfo");
			AnswerInfo answerInfo = tranDescScoreInfo.getAnswerInfo();

			setAnswerInfoParams(answerInfo);

			Short pendingCategory = SaitenUtil
					.getPendingCategoryByPendingCategorySeq(pendingCategorySeq);

			platformTransactionManager = saitenTransactionManager
					.getTransactionManger();
			transactionStatus = saitenTransactionManager
					.beginTransaction(platformTransactionManager);
			List<Integer> qcAnswerSeqList = new ArrayList<Integer>();
			qcAnswerSeqList = (List<Integer>) session.get("qcAnswerSeqList");
			Boolean isQcRecord = false;
			if (session.get("isQcRecord") != null) {
				isQcRecord = (Boolean) session.get("isQcRecord");
			}
			if ((qcAnswerSeqList != null && !qcAnswerSeqList.isEmpty()
					&& (isQcRecord != null && isQcRecord) && (tranDescScoreInfo
					.getAnswerInfo().getHistorySeq() == null))
					|| (tranDescScoreInfo.getAnswerInfo().getQcSeq() != null)) {
				lockFlag = registerPendingService.registerQcPending(
						questionInfo, scorerInfo, answerInfo,
						pendingCategorySeq, pendingCategory, tranDescScoreInfo
								.getAnswerInfo().getUpdateDate());
				if (tranDescScoreInfo.getAnswerInfo().getQcSeq() == null) {
					qcAnswerSeqList.remove(Integer.valueOf(answerInfo
							.getAnswerSeq()));
					session.remove("isQcRecord");
				}
				session.put("qcAnswerSeqList", qcAnswerSeqList);

			} else {
				if (menuId.equals(WebAppConst.FIRST_SCORING_MENU_ID)
						|| menuId.equals(WebAppConst.SECOND_SCORING_MENU_ID)
						|| menuId.equals(WebAppConst.CHECKING_MENU_ID)
						|| menuId.equals(WebAppConst.DENY_MENU_ID)
						|| menuId.equals(WebAppConst.PENDING_MENU_ID)
						|| menuId.equals(WebAppConst.INSPECTION_MENU_ID)
						|| menuId.equals(WebAppConst.MISMATCH_MENU_ID)
						|| menuId.equals(WebAppConst.NO_GRADE_MENU_ID)
						|| menuId.equals(WebAppConst.OUT_BOUNDARY_MENU_ID)
						|| menuId
								.equals(WebAppConst.FIRST_SCORING_QUALITY_CHECK_MENU_ID)) {
					lockFlag = registerPendingByProcedureService
							.registerPending(questionInfo, scorerInfo,
									answerInfo, pendingCategorySeq,
									pendingCategory, tranDescScoreInfo
											.getAnswerInfo().getUpdateDate());
				} else {
					lockFlag = registerPendingService.registerPending(
							questionInfo, scorerInfo, answerInfo,
							pendingCategorySeq, pendingCategory,
							tranDescScoreInfo.getAnswerInfo().getUpdateDate());
				}

			}

			platformTransactionManager.commit(transactionStatus);

			// Remove tranDescScoreInfo from session to load new answer
			updateSessionInfo(tranDescScoreInfo);

		} catch (SaitenRuntimeException we) {
			if (platformTransactionManager != null)
				platformTransactionManager.rollback(transactionStatus);

			throw we;
		} catch (Exception e) {
			if (platformTransactionManager != null)
				platformTransactionManager.rollback(transactionStatus);

			throw new SaitenRuntimeException(
					ErrorCode.REGISTER_PENDING_ACTION_EXCEPTION, e);
		}

		return !lockFlag ? getRedirectAction(questionInfo.getMenuId())
				: FAILURE;
	}

	private String getRedirectAction(String menuId) {

		String redirectAction = null;
		TranDescScoreInfo sessionTranDescScoreInfo = (TranDescScoreInfo) session
				.get("tranDescScoreInfo");
		if (menuId.equals(WebAppConst.SCORE_SAMP_MENU_ID)
				&& (sessionTranDescScoreInfo == null)) {
			redirectAction = SCORE_SAMPLING;
		} else if (menuId.equals(WebAppConst.FORCED_MENU_ID)
				&& (sessionTranDescScoreInfo == null)) {
			redirectAction = BACK_TO_FORCED_SCORING_LIST;
		} else {
			redirectAction = SUCCESS;
		}
		return redirectAction;
	}

	/**
	 * @param answerInfo
	 * @throws UnsupportedEncodingException
	 */
	private void setAnswerInfoParams(AnswerInfo answerInfo)
			throws UnsupportedEncodingException {
		answerInfo
				.setBookMarkFlag(bookMarkFlag == WebAppConst.TRUE ? WebAppConst.BOOKMARK_FLAG_TRUE
						: WebAppConst.BOOKMARK_FLAG_FALSE);

		// Get query string
		String queryString = ServletActionContext.getRequest().getQueryString();

		// Fetch and decode scorerComment
		String scorerComment = URLDecoder.decode(queryString
				.substring(queryString.indexOf(QUERY_STRING_SCORER_COMMENT)
						+ QUERY_STRING_SCORER_COMMENT.length()),
				ServletActionContext.getRequest().getCharacterEncoding());

		answerInfo.setScorerComment(scorerComment);
		answerInfo
				.setQualityCheckFlag(qualityCheckFlag == WebAppConst.TRUE ? WebAppConst.QUALITY_MARK_FLAG_TRUE
						: WebAppConst.QUALITY_MARK_FLAG_FALSE);
	}

	/**
	 * 
	 * @param session
	 */
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	/**
	 * @param tranDescScoreInfo
	 */
	private void updateSessionInfo(TranDescScoreInfo tranDescScoreInfo) {
		QuestionInfo sessionQuestionInfo = (QuestionInfo) session
				.get("questionInfo");
		TranDescScoreInfo tranDescScoreInfoCopy = (TranDescScoreInfo) session
				.get("tranDescScoreInfoCopy");

		if ((tranDescScoreInfo.getAnswerInfo().getHistorySeq() != null)
				|| (tranDescScoreInfo.getAnswerInfo().getQcSeq() != null)) {
			session.put("tranDescScoreInfo", tranDescScoreInfoCopy);
			session.remove("tranDescScoreInfoCopy");
			session.remove("historyScreenFlag");
		} else {
			session.remove("tranDescScoreInfo");

			if (!lockFlag) {
				sessionQuestionInfo.setHistoryRecordCount(sessionQuestionInfo
						.getHistoryRecordCount() + 1);
			}
		}

		sessionQuestionInfo.setPrevRecordCount(sessionQuestionInfo
				.getHistoryRecordCount());
		sessionQuestionInfo.setNextRecordCount(WebAppConst.MINUS_ONE);

		session.put("questionInfo", sessionQuestionInfo);

		// remove selectedCheckPointList before loading new answer
		session.remove("selectedCheckPointList");
	}

	public boolean isLockFlag() {
		return lockFlag;
	}

	/**
	 * @param lockFlag
	 */
	public void setLockFlag(boolean lockFlag) {
		this.lockFlag = lockFlag;
	}

	public Integer getPendingCategorySeq() {
		return pendingCategorySeq;
	}

	/**
	 * @param pendingCategorySeq
	 */
	public void setPendingCategorySeq(Integer pendingCategorySeq) {
		this.pendingCategorySeq = pendingCategorySeq;
	}

	public boolean isBookMarkFlag() {
		return bookMarkFlag;
	}

	/**
	 * @param bookMarkFlag
	 */
	public void setBookMarkFlag(boolean bookMarkFlag) {
		this.bookMarkFlag = bookMarkFlag;
	}

	/**
	 * @return the qualityCheckFlag
	 */
	public boolean isQualityCheckFlag() {
		return qualityCheckFlag;
	}

	/**
	 * @param qualityCheckFlag
	 *            the qualityCheckFlag to set
	 */
	public void setQualityCheckFlag(boolean qualityCheckFlag) {
		this.qualityCheckFlag = qualityCheckFlag;
	}

	/**
	 * 
	 * @param saitenTransactionManager
	 */
	public void setSaitenTransactionManager(
			SaitenTransactionManager saitenTransactionManager) {
		this.saitenTransactionManager = saitenTransactionManager;
	}

	/**
	 * 
	 * @param registerPendingService
	 */
	public void setRegisterPendingService(
			RegisterPendingService registerPendingService) {
		this.registerPendingService = registerPendingService;
	}

	/**
	 * @param registerPendingByProcedureService
	 *            the registerPendingByProcedureService to set
	 */
	public void setRegisterPendingByProcedureService(
			RegisterPendingByProcedureService registerPendingByProcedureService) {
		this.registerPendingByProcedureService = registerPendingByProcedureService;
	}

}