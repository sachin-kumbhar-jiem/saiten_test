package com.saiten.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.saiten.exception.SaitenRuntimeException;
import com.saiten.info.AnswerInfo;
import com.saiten.info.GradeInfo;
import com.saiten.info.MstScorerInfo;
import com.saiten.info.QuestionInfo;
import com.saiten.info.ScoreInputInfo;
import com.saiten.info.ScoreSamplingInfo;
import com.saiten.info.TranDescScoreInfo;
import com.saiten.manager.SaitenTransactionManager;
import com.saiten.service.RegisterScoreByProcedureService;
import com.saiten.service.RegisterScoreService;
import com.saiten.util.ErrorCode;
import com.saiten.util.SaitenUtil;
import com.saiten.util.WebAppConst;

/**
 * @author sachin
 * @version 1.0
 * @created 13-Dec-2012 11:34:44 AM
 */
public class RegisterScoreAction extends ActionSupport implements SessionAware {

	private static Logger log = Logger.getLogger(RegisterScoreAction.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RegisterScoreService registerScoreService;
	private RegisterScoreByProcedureService registerScoreByProcedureService;
	private Map<String, Object> session;
	@SuppressWarnings("unused")
	private SaitenTransactionManager saitenTransactionManager;
	private boolean lockFlag;
	public static final String FAILURE = "failure";
	public static final String SCORE_SAMPLING = "scoreSampling";
	public static final String SPECIAL_SAMPLING = "specialSampling";
	public static final String BACK_TO_FORCED_SCORING_LIST = "backToForcedScoringList";
	private boolean selectAllFlag;
	private List<Integer> answerSeq;
	private Integer totalRecordsCount;
	private int updatedCount;
	private Integer denyCategorySeq;
	private Integer denyCategory;
	private boolean bookMarkFlag;
	private boolean qualityCheckFlag;
	public static final String RESET_SCORE_HISTORY = "resetScoreHistory";

	@SuppressWarnings("unchecked")
	public String doRegister() {
		// No need of transaction management, It is handled in procedure.
		/*
		 * PlatformTransactionManager platformTransactionManager = null;
		 * TransactionStatus transactionStatus = null;
		 */

		QuestionInfo questionInfo = (QuestionInfo) session.get("questionInfo");
		Integer historyRecordCount = null;
		String approveOrDeny = session.get("approveOrDeny").toString();
		// boolean historyScreenFlag = (Boolean)
		// session.get("historyScreenFlag");
		Boolean historyScreenFlag = (Boolean) session.get("historyScreenFlag");

		try {

			Date logActionStartTime = new Date();
			MstScorerInfo scorerInfo = (MstScorerInfo) session.get("scorerInfo");
			String menuId = questionInfo.getMenuId();
			TranDescScoreInfo tranDescScoreInfo = (TranDescScoreInfo) session.get("tranDescScoreInfo");
			AnswerInfo answerInfo = tranDescScoreInfo.getAnswerInfo();
			Integer gradeSeq = ((GradeInfo) session.get("gradeInfo")).getGradeSeq();
			String gradeNumText = ((GradeInfo) session.get("gradeInfo")).getGradeNum();
			Integer gradeNum = null;
			if (gradeNumText != null) {
				if (!gradeNumText.contains("-")) {
					gradeNum = Integer.valueOf(gradeNumText);
				}
			}
			log.info(scorerInfo.getScorerId() + "-" + menuId + "-" + "Record going to score \n TranDescScoreInfo: "
					+ tranDescScoreInfo + " \n Grade Seq: " + gradeSeq + ", Grade Num: " + gradeNum + ", Timestamp: "
					+ new Date().getTime() + "}");
			Short denyCategory = null;
			if (approveOrDeny.equals(WebAppConst.DENY)) {
				denyCategory = SaitenUtil.getDenyCategoryByDenyCategorySeq(denyCategorySeq);
			}
			historyRecordCount = (Integer) session.get("historyRecordCount");

			/*
			 * platformTransactionManager = saitenTransactionManager
			 * .getTransactionManger(questionInfo.getConnectionString());
			 * transactionStatus = saitenTransactionManager
			 * .beginTransaction(platformTransactionManager);
			 */
			List<Integer> qcAnswerSeqList = new ArrayList<Integer>();
			qcAnswerSeqList = (List<Integer>) session.get("qcAnswerSeqList");
			Boolean isQcRecord = false;
			if (session.get("isQcRecord") != null) {
				isQcRecord = (Boolean) session.get("isQcRecord");
			}

			if ((qcAnswerSeqList != null && !qcAnswerSeqList.isEmpty() && (isQcRecord != null && isQcRecord)
					&& (tranDescScoreInfo.getAnswerInfo().getHistorySeq() == null))
					|| (tranDescScoreInfo.getAnswerInfo().getQcSeq() != null)) {
				// register qc answer using stored procedure.
				// NOTE: for register without using stored procedure replace
				// 'registerScoreByProcedureService' with 'registerScoreService'
				// in below line.
				lockFlag = registerScoreByProcedureService.registerQcScoring(questionInfo, scorerInfo, answerInfo,
						gradeSeq, gradeNum, session.get("approveOrDeny").toString(),
						tranDescScoreInfo.getAnswerInfo().getUpdateDate(), historyRecordCount,
						tranDescScoreInfo.getAnswerFormNumber());
				if (tranDescScoreInfo.getAnswerInfo().getQcSeq() == null) {
					qcAnswerSeqList.remove(Integer.valueOf(answerInfo.getAnswerSeq()));
					session.remove("isQcRecord");
				}
				session.put("qcAnswerSeqList", qcAnswerSeqList);

			} else {

				// IF - register or update answer by using stored procedure.
				// ELSE - register or update answer without using stored
				// procedure.
				if (menuId.equals(WebAppConst.FIRST_SCORING_MENU_ID)
						|| menuId.equals(WebAppConst.SECOND_SCORING_MENU_ID)
						|| menuId.equals(WebAppConst.CHECKING_MENU_ID) || menuId.equals(WebAppConst.DENY_MENU_ID)
						|| menuId.equals(WebAppConst.PENDING_MENU_ID) || menuId.equals(WebAppConst.INSPECTION_MENU_ID)
						|| menuId.equals(WebAppConst.MISMATCH_MENU_ID) || menuId.equals(WebAppConst.NO_GRADE_MENU_ID)
						|| menuId.equals(WebAppConst.OUT_BOUNDARY_MENU_ID)
						|| menuId.equals(WebAppConst.FIRST_SCORING_QUALITY_CHECK_MENU_ID)
						|| menuId.equals(WebAppConst.SCORE_SAMP_MENU_ID) || menuId.equals(WebAppConst.FORCED_MENU_ID)
						|| menuId.equals(WebAppConst.SPECIAL_SCORING_BLIND_TYPE_MENU_ID)
						|| menuId.equals(WebAppConst.SPECIAL_SCORING_ENLARGE_TYPE_MENU_ID)
						|| menuId.equals(WebAppConst.SPECIAL_SCORING_LANGUAGE_SUPPORT_MENU_ID)
						|| menuId.equals(WebAppConst.SPECIAL_SCORING_OMR_READ_FAIL_MENU_ID)) {
					lockFlag = registerScoreByProcedureService.registerScoring(questionInfo, scorerInfo, answerInfo,
							gradeSeq, gradeNum, denyCategorySeq, denyCategory, session.get("approveOrDeny").toString(),
							tranDescScoreInfo.getAnswerInfo().getUpdateDate(), historyRecordCount,
							tranDescScoreInfo.getAnswerFormNumber());
				} else {
					lockFlag = registerScoreService.registerScoring(questionInfo, scorerInfo, answerInfo, gradeSeq,
							gradeNum, denyCategorySeq, denyCategory, session.get("approveOrDeny").toString(),
							tranDescScoreInfo.getAnswerInfo().getUpdateDate(), historyRecordCount);
				}

			}
			/* platformTransactionManager.commit(transactionStatus); */

			// Modify session info after answer evaluation
			updateSessionInfo(tranDescScoreInfo);

			Date logActionEndTime = new Date();
			String actionName = "registerScore";
			long total = logActionEndTime.getTime() - logActionStartTime.getTime();
			log.info(actionName + "-Total: " + total);
		} catch (SaitenRuntimeException we) {
			/*
			 * if (platformTransactionManager != null)
			 * platformTransactionManager.rollback(transactionStatus);
			 */

			throw we;
		} catch (Exception e) {
			/*
			 * if (platformTransactionManager != null)
			 * platformTransactionManager.rollback(transactionStatus);
			 */

			throw new SaitenRuntimeException(ErrorCode.REGISTER_SCORE_ACTION_EXCEPTION, e);
		}

		return !lockFlag ? getRedirectAction(questionInfo.getMenuId(), historyRecordCount, historyScreenFlag) : FAILURE;
	}

	private String getRedirectAction(String menuId, Integer historyRecordCount, Boolean historyScreenFlag) {

		String redirectAction = null;

		TranDescScoreInfo sessionTranDescScoreInfo = (TranDescScoreInfo) session.get("tranDescScoreInfo");
		if (historyScreenFlag != null && historyScreenFlag && !menuId.equals(WebAppConst.FORCED_MENU_ID)) {
			return RESET_SCORE_HISTORY;
		}
		if (menuId.equals(WebAppConst.SCORE_SAMP_MENU_ID) && (sessionTranDescScoreInfo == null)) {
			return SCORE_SAMPLING;
		}
		if (menuId.equals(WebAppConst.FORCED_MENU_ID) && (sessionTranDescScoreInfo == null)) {
			redirectAction = BACK_TO_FORCED_SCORING_LIST;
		} else if (((menuId.equals(WebAppConst.SPECIAL_SCORING_ENLARGE_TYPE_MENU_ID)
				|| menuId.equals(WebAppConst.SPECIAL_SCORING_OMR_READ_FAIL_MENU_ID)) && historyRecordCount != null)
				&& sessionTranDescScoreInfo == null) {
			redirectAction = SPECIAL_SAMPLING;
		} else {
			redirectAction = SUCCESS;
		}
		return redirectAction;
	}

	/**
	 * 
	 * @param tranDescScoreInfo
	 */
	private void updateSessionInfo(TranDescScoreInfo tranDescScoreInfo) {
		QuestionInfo sessionQuestionInfo = (QuestionInfo) session.get("questionInfo");
		TranDescScoreInfo tranDescScoreInfoCopy = (TranDescScoreInfo) session.get("tranDescScoreInfoCopy");
		String menuId = sessionQuestionInfo.getMenuId();

		if ((tranDescScoreInfo.getAnswerInfo().getHistorySeq() != null)
				|| (tranDescScoreInfo.getAnswerInfo().getQcSeq() != null)) {
			// Put copy of locked record in session after history answer
			// evaluation
			session.put("tranDescScoreInfo", tranDescScoreInfoCopy);
			session.remove("tranDescScoreInfoCopy");
			session.remove("historyScreenFlag");
		} else {
			// Remove tranDescScoreInfo from session to load new answer
			session.remove("tranDescScoreInfo");

			if (!lockFlag) {
				// Do not update historyRecordCount if lock is acquired by two
				// or more users
				if (menuId.equals(WebAppConst.SPECIAL_SCORING_ENLARGE_TYPE_MENU_ID)
						|| menuId.equals(WebAppConst.SPECIAL_SCORING_OMR_READ_FAIL_MENU_ID)) {
					session.put("historyRecordCount", (Integer) session.get("historyRecordCount") + 1);
				} else {
					sessionQuestionInfo.setHistoryRecordCount(sessionQuestionInfo.getHistoryRecordCount() + 1);
				}
			}
		}

		// Re-initialize prev and next history counters
		if (menuId.equals(WebAppConst.SPECIAL_SCORING_ENLARGE_TYPE_MENU_ID)
				|| menuId.equals(WebAppConst.SPECIAL_SCORING_OMR_READ_FAIL_MENU_ID)) {
			session.put("prevRecordCount", (Integer) session.get("historyRecordCount"));
			session.put("nextRecordCount", (Integer) WebAppConst.MINUS_ONE.intValue());
		} else {
			sessionQuestionInfo.setPrevRecordCount(sessionQuestionInfo.getHistoryRecordCount());
			sessionQuestionInfo.setNextRecordCount(WebAppConst.MINUS_ONE);

			session.put("questionInfo", sessionQuestionInfo);
		}

		// remove selectedCheckPointList before loading new answer
		session.remove("selectedCheckPointList");
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String update() {
		List scoreSamplingInfoList = (List<ScoreSamplingInfo>) session.get("scoreSamplingInfoList");
		MstScorerInfo scorerInfo = ((MstScorerInfo) session.get("scorerInfo"));
		QuestionInfo questionInfo = (QuestionInfo) session.get("questionInfo");
		if (selectAllFlag == true) {
			log.info(scorerInfo.getScorerId() + "-" + questionInfo.getMenuId() + "-"
					+ "Set inspection flag for all searched records.");
			totalRecordsCount = (Integer) session.get("searchResultCount");
		} else {
			log.info(scorerInfo.getScorerId() + "-" + questionInfo.getMenuId() + "-"
					+ "Set inspection flag for selected records. \n Answer Sequences: " + answerSeq);
			totalRecordsCount = answerSeq.size();
		}

		ScoreInputInfo scoreInputInfo = (ScoreInputInfo) session.get("scoreInputInfo");
		/*
		 * Short[] currentStateList = null; if
		 * (scoreInputInfo.getScoreCurrentInfo() != null) { currentStateList =
		 * scoreInputInfo.getScoreCurrentInfo() .getCurrentStateList(); }
		 */

		synchronized (RegisterScoreAction.class) {
			Integer inspectGroupSeq = registerScoreService.findMaxInspectGroupSeq(questionInfo.getQuestionSeq(),
					questionInfo.getConnectionString());

			updatedCount = registerScoreService.updateInspectFlag(answerSeq, questionInfo, scoreSamplingInfoList,
					selectAllFlag, scoreInputInfo, inspectGroupSeq);

			session.put("inspectGroupSeq", inspectGroupSeq);
		}

		session.put("updatedCount", updatedCount);
		session.remove("scoreSamplingInfoList");
		return SUCCESS;
	}

	public boolean isLockFlag() {
		return lockFlag;
	}

	/**
	 * 
	 * @param lockFlag
	 */
	public void setLockFlag(boolean lockFlag) {
		this.lockFlag = lockFlag;
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
	 * 
	 * @param saitenTransactionManager
	 */
	public void setSaitenTransactionManager(SaitenTransactionManager saitenTransactionManager) {
		this.saitenTransactionManager = saitenTransactionManager;
	}

	/**
	 * 
	 * @param registerScoreService
	 */
	public void setRegisterScoreService(RegisterScoreService registerScoreService) {
		this.registerScoreService = registerScoreService;
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

	/**
	 * @return the updatedCount
	 */
	public int getUpdatedCount() {
		return updatedCount;
	}

	/**
	 * @param updatedCount
	 *            the updatedCount to set
	 */
	public void setUpdatedCount(int updatedCount) {
		this.updatedCount = updatedCount;
	}

	/**
	 * @return the answerSeq
	 */
	public List<Integer> getAnswerSeq() {
		return answerSeq;
	}

	/**
	 * @param answerSeq
	 *            the answerSeq to set
	 */
	public void setAnswerSeq(List<Integer> answerSeq) {
		this.answerSeq = answerSeq;
	}

	/**
	 * @return the totalRecordsCount
	 */
	public Integer getTotalRecordsCount() {
		return totalRecordsCount;
	}

	/**
	 * @param totalRecordsCount
	 *            the totalRecordsCount to set
	 */
	public void setTotalRecordsCount(Integer totalRecordsCount) {
		this.totalRecordsCount = totalRecordsCount;
	}

	public Integer getDenyCategorySeq() {
		return denyCategorySeq;
	}

	public void setDenyCategorySeq(Integer denyCategorySeq) {
		this.denyCategorySeq = denyCategorySeq;
	}

	public Integer getDenyCategory() {
		return denyCategory;
	}

	public void setDenyCategory(Integer denyCategory) {
		this.denyCategory = denyCategory;
	}

	public boolean isBookMarkFlag() {
		return bookMarkFlag;
	}

	public void setBookMarkFlag(boolean bookMarkFlag) {
		this.bookMarkFlag = bookMarkFlag;
	}

	public boolean isQualityCheckFlag() {
		return qualityCheckFlag;
	}

	public void setQualityCheckFlag(boolean qualityCheckFlag) {
		this.qualityCheckFlag = qualityCheckFlag;
	}

	/**
	 * @param registerScoreByProcedureService
	 *            the registerScoreByProcedureService to set
	 */
	public void setRegisterScoreByProcedureService(RegisterScoreByProcedureService registerScoreByProcedureService) {
		this.registerScoreByProcedureService = registerScoreByProcedureService;
	}

}