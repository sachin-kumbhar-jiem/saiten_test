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
import com.saiten.info.GradeInfo;
import com.saiten.info.MstScorerInfo;
import com.saiten.info.QuestionInfo;
import com.saiten.info.TranDescScoreInfo;
import com.saiten.manager.SaitenTransactionManager;
import com.saiten.service.RegisterDenyService;
import com.saiten.util.ErrorCode;
import com.saiten.util.SaitenUtil;
import com.saiten.util.WebAppConst;

public class RegisterDenyAction extends ActionSupport implements SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, Object> session;
	private Integer denyCategorySeq;
	private boolean bookMarkFlag;
	private boolean qualityCheckFlag;
	private boolean lockFlag;

	private RegisterDenyService registerDenyService;
	private SaitenTransactionManager saitenTransactionManager;

	public static final String FAILURE = "failure";
	public static final String QUERY_STRING_SCORER_COMMENT = "&scorerComment=";
	public static final String SCORE_SAMPLING = "scoreSampling";
	public static final String BACK_TO_FORCED_SCORING_LIST = "backToForcedScoringList";

	public String doRegister() {
		PlatformTransactionManager platformTransactionManager = null;
		TransactionStatus transactionStatus = null;

		QuestionInfo questionInfo = (QuestionInfo) session.get("questionInfo");

		try {
			MstScorerInfo scorerInfo = (MstScorerInfo) session
					.get("scorerInfo");

			TranDescScoreInfo tranDescScoreInfo = (TranDescScoreInfo) session
					.get("tranDescScoreInfo");
			AnswerInfo answerInfo = tranDescScoreInfo.getAnswerInfo();
			
			System.out.println(session.get("gradeInfo"));
			
			Integer gradeSeq = ((GradeInfo) session.get("gradeInfo"))
					.getGradeSeq();
			String gradeNumText = ((GradeInfo) session.get("gradeInfo"))
					.getGradeNum();
			Integer gradeNum = null;
			if (gradeNumText != null) {
				if (!gradeNumText.contains("-")) {
					gradeNum = Integer.valueOf(gradeNumText);
				}
			}

			setAnswerInfoParams(answerInfo);

			Short denyCategory = SaitenUtil
					.getDenyCategoryByDenyCategorySeq(denyCategorySeq);

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
				lockFlag = registerDenyService.registerQcDeny(questionInfo,
						scorerInfo, answerInfo, gradeSeq, gradeNum,denyCategorySeq, denyCategory,
						tranDescScoreInfo.getAnswerInfo().getUpdateDate());
				if (tranDescScoreInfo.getAnswerInfo().getQcSeq() == null) {
					qcAnswerSeqList.remove(Integer.valueOf(answerInfo
							.getAnswerSeq()));
					session.remove("isQcRecord");
				}
				session.put("qcAnswerSeqList", qcAnswerSeqList);

			} else {

				lockFlag = registerDenyService.registerDeny(questionInfo,
						scorerInfo, answerInfo, gradeSeq, gradeNum, denyCategorySeq, denyCategory,
						tranDescScoreInfo.getAnswerInfo().getUpdateDate());
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
			
			System.out.println(e.getStackTrace());

			// throw new SaitenRuntimeException( e);
		}

		return !lockFlag ? getRedirectAction(questionInfo.getMenuId())
				: FAILURE;
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

	public Integer getDenyCategorySeq() {
		return denyCategorySeq;
	}

	public void setDenyCategorySeq(Integer denyCategorySeq) {
		this.denyCategorySeq = denyCategorySeq;
	}

	public boolean isBookMarkFlag() {
		return bookMarkFlag;
	}

	public void setBookMarkFlag(boolean bookMarkFlag) {
		this.bookMarkFlag = bookMarkFlag;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public boolean isQualityCheckFlag() {
		return qualityCheckFlag;
	}

	public void setQualityCheckFlag(boolean qualityCheckFlag) {
		this.qualityCheckFlag = qualityCheckFlag;
	}

	public boolean isLockFlag() {
		return lockFlag;
	}

	public void setLockFlag(boolean lockFlag) {
		this.lockFlag = lockFlag;
	}

	public RegisterDenyService getRegisterDenyService() {
		return registerDenyService;
	}

	public void setRegisterDenyService(RegisterDenyService registerDenyService) {
		this.registerDenyService = registerDenyService;
	}

	public SaitenTransactionManager getSaitenTransactionManager() {
		return saitenTransactionManager;
	}

	public void setSaitenTransactionManager(
			SaitenTransactionManager saitenTransactionManager) {
		this.saitenTransactionManager = saitenTransactionManager;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

}
