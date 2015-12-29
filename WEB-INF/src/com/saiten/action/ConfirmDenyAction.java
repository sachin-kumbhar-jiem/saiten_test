package com.saiten.action;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.saiten.exception.SaitenRuntimeException;
import com.saiten.info.AnswerInfo;
import com.saiten.info.GradeInfo;
import com.saiten.info.QuestionInfo;
import com.saiten.info.TranDescScoreInfo;
import com.saiten.service.ConfirmScoreService;
import com.saiten.util.ErrorCode;
import com.saiten.util.SaitenUtil;
import com.saiten.util.WebAppConst;

public class ConfirmDenyAction extends ActionSupport implements SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, Object> session;
	private Map<String, Object> result;
	private String checkPoint;
	
	private ConfirmScoreService confirmScoreService;


	public String confirmDeny() {
		try {
			if (session.get("scorerInfo") == null) {
				// Return statusCode as a JSON response for deny category
				result = SaitenUtil.getAjaxCallStatusCode(session);
				return SUCCESS;
			}
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
			GradeInfo gradeInfo = confirmScoreService.findGradeAndResult(
					bitValue, questionSeq);
			// build GradeInfo result to be returned
			buildGradeResultMap(gradeInfo);

			// put GradeInfo object, scorerComment and bitValue in session
			buildSessionInfo(gradeInfo, bitValue, menuId);
		} catch (Exception e) {
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
		
		

		
		answerInfo.setBitValue((double) bitValue);

		tranDescScoreInfo.setAnswerInfo(answerInfo);

		session.put("tranDescScoreInfo", tranDescScoreInfo);

		
	}

	public Map<String, Object> getResult() {
		return result;
	}

	public void setResult(Map<String, Object> result) {
		this.result = result;
	}

	public String getCheckPoint() {
		return checkPoint;
	}

	public void setCheckPoint(String checkPoint) {
		this.checkPoint = checkPoint;
	}


	public ConfirmScoreService getConfirmScoreService() {
		return confirmScoreService;
	}

	public void setConfirmScoreService(ConfirmScoreService confirmScoreService) {
		this.confirmScoreService = confirmScoreService;
	}


	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

}
