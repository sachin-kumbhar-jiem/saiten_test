package com.saiten.action;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.saiten.exception.SaitenRuntimeException;
import com.saiten.info.AnswerInfo;
import com.saiten.info.GradeInfo;
import com.saiten.info.MstScorerInfo;
import com.saiten.info.QuestionInfo;
import com.saiten.info.TranDescScoreInfo;
import com.saiten.service.ConfirmScoreService;
import com.saiten.util.ErrorCode;
import com.saiten.util.SaitenUtil;
import com.saiten.util.WebAppConst;

/**
 * @author sachin
 * @version 1.0
 * @created 13-Dec-2012 11:53:11 AM
 */
public class ConfirmScoreAction extends ActionSupport implements SessionAware {

	private static Logger log = Logger
			.getLogger(ConfirmScoreAction.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String checkPoint;
	private String scorerComment;
	private ConfirmScoreService confirmScoreService;
	private Map<String, Object> session;
	private Map<String, Object> result;
	private boolean bookMarkFlag;
	private String approveOrDeny;
	private boolean qualityCheckFlag;

	public String confirmScore() {

		try {

			if (session.get("scorerInfo") == null) {
				// Return statusCode as a JSON response for invalid session
				result = SaitenUtil.getAjaxCallStatusCode(session);
				return SUCCESS;
			}

			// Calculate bitValue from selected check points
			int bitValue = confirmScoreService.calculateBitValue(checkPoint);

			QuestionInfo questionInfo = (QuestionInfo) session
					.get("questionInfo");
			Integer questionSeq = null;
			String menuId = questionInfo.getMenuId();
			TranDescScoreInfo tranDescScoreInfo = (TranDescScoreInfo) session
					.get("tranDescScoreInfo");
			Integer historySeq = tranDescScoreInfo.getAnswerInfo()
					.getHistorySeq();
			if ((menuId
					.equals(WebAppConst.SPECIAL_SCORING_OMR_READ_FAIL_MENU_ID) || menuId
					.equals(WebAppConst.SPECIAL_SCORING_ENLARGE_TYPE_MENU_ID))
					&& (historySeq != null)) {
				questionSeq = tranDescScoreInfo.getAnswerInfo()
						.getQuestionSeq();
			} else {
				questionSeq = questionInfo.getQuestionSeq();
			}

			// Find grade and result from calculated bitValue and questionSeq
			GradeInfo gradeInfo = confirmScoreService.findGradeAndResult(
					bitValue, questionSeq);

			// build GradeInfo result to be returned
			buildGradeResultMap(gradeInfo);

			// put GradeInfo object, scorerComment and bitValue in session
			buildSessionInfo(gradeInfo, bitValue, menuId);

		} catch (SaitenRuntimeException we) {
			throw we;
		} catch (Exception e) {
			MstScorerInfo scorerInfo = ((MstScorerInfo) session
					.get("scorerInfo"));
			QuestionInfo questionInfo = (QuestionInfo) session
					.get("questionInfo");
			TranDescScoreInfo tranDescScoreInfo = (TranDescScoreInfo) session
					.get("tranDescScoreInfo");
			log.error(scorerInfo.getScorerId()+"-"+questionInfo.getMenuId()+"-"+"\n"+"TranDescScoreInfo: "+tranDescScoreInfo);
			throw new SaitenRuntimeException(
					ErrorCode.CONFIRM_SCORE_ACTION_EXCEPTION, e);
		}

		return SUCCESS;
	}

	/**
	 * 
	 * @param gradeInfo
	 */
	private void buildGradeResultMap(GradeInfo gradeInfo) {
		result = new LinkedHashMap<String, Object>();
		Map<String, Object> root = new LinkedHashMap<String, Object>();
		root.put("gradeNum", gradeInfo.getGradeNum());
		root.put("gradeResult", gradeInfo.getResult());
		root.put("approveOrDeny", approveOrDeny);
		result.put("result", root);
	}

	/**
	 * 
	 * @param gradeInfo
	 * @param bitValue
	 */
	private void buildSessionInfo(GradeInfo gradeInfo, int bitValue,
			String menuId) {
		session.put("gradeInfo", gradeInfo);

		TranDescScoreInfo tranDescScoreInfo = (TranDescScoreInfo) session
				.get("tranDescScoreInfo");
		AnswerInfo answerInfo = tranDescScoreInfo.getAnswerInfo();
		answerInfo
				.setBookMarkFlag(bookMarkFlag == WebAppConst.TRUE ? WebAppConst.BOOKMARK_FLAG_TRUE
						: WebAppConst.BOOKMARK_FLAG_FALSE);
		if (menuId.equals(WebAppConst.FIRST_SCORING_QUALITY_CHECK_MENU_ID)
				|| menuId.equals(WebAppConst.FORCED_MENU_ID)) {
			answerInfo
					.setQualityCheckFlag(qualityCheckFlag == WebAppConst.TRUE ? WebAppConst.QUALITY_MARK_FLAG_TRUE
							: WebAppConst.QUALITY_MARK_FLAG_FALSE);
		}

		answerInfo.setScorerComment(scorerComment);
		answerInfo.setBitValue((double) bitValue);

		tranDescScoreInfo.setAnswerInfo(answerInfo);

		session.put("tranDescScoreInfo", tranDescScoreInfo);

		session.put("approveOrDeny", approveOrDeny);
	}

	public String getScorerComment() {
		return scorerComment;
	}

	/**
	 * 
	 * @param scorerComment
	 */
	public void setScorerComment(String scorerComment) {
		this.scorerComment = scorerComment;
	}

	public boolean isBookMarkFlag() {
		return bookMarkFlag;
	}

	/**
	 * 
	 * @param bookMarkFlag
	 */
	public void setBookMarkFlag(boolean bookMarkFlag) {
		this.bookMarkFlag = bookMarkFlag;
	}

	public Map<String, Object> getResult() {
		return result;
	}

	/**
	 * 
	 * @param result
	 */
	public void setResult(Map<String, Object> result) {
		this.result = result;
	}

	public String getCheckPoint() {
		return checkPoint;
	}

	/**
	 * 
	 * @param checkPoint
	 */
	public void setCheckPoint(String checkPoint) {
		this.checkPoint = checkPoint;
	}

	/**
	 * 
	 * @param confirmScoreService
	 */
	public void setConfirmScoreService(ConfirmScoreService confirmScoreService) {
		this.confirmScoreService = confirmScoreService;
	}

	/**
	 * 
	 * @param session
	 */
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public String getApproveOrDeny() {
		return approveOrDeny;
	}

	/**
	 * @param approveOrDeny
	 */
	public void setApproveOrDeny(String approveOrDeny) {
		this.approveOrDeny = approveOrDeny;
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

}