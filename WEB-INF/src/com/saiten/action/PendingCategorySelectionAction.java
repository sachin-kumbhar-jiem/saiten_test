package com.saiten.action;

import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.saiten.exception.SaitenRuntimeException;
import com.saiten.info.MstScorerInfo;
import com.saiten.info.QuestionInfo;
import com.saiten.service.PendingCategorySelectionService;
import com.saiten.util.ErrorCode;
import com.saiten.util.WebAppConst;

/**
 * @author kailash
 * 
 */
public class PendingCategorySelectionAction extends ActionSupport implements
		SessionAware {

	private static Logger log = Logger
			.getLogger(PendingCategorySelectionAction.class);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private PendingCategorySelectionService pendingCategorySelectionService;

	private Map<String, Object> session;

	private Map<Short, String> pendingCategoryMap;

	private String questionList;

	private String subjectShortName;

	private Short selectedMarkValue;

	public String onLoad() {

		try {
			int questionSeq;
			QuestionInfo sessionQuestionInfo = (QuestionInfo) session
					.get("questionInfo");
			questionSeq = sessionQuestionInfo.getQuestionSeq();
			questionList = (String) session.get("questionList");
			if (sessionQuestionInfo.getScoreType() == WebAppConst.SCORE_TYPE[3]) {
				session.put("selectedMarkValue", selectedMarkValue);
			}
			String[] selectedQuestion = questionList.split(WebAppConst.COLON);
			subjectShortName = selectedQuestion[1];
			pendingCategoryMap = pendingCategorySelectionService
					.findPendingCategoriesByQuestionSeq(questionSeq);
			if (pendingCategoryMap.isEmpty()) {
				this.addActionError(getText(WebAppConst.ERROR_NO_PENDING_CATEGORY_AVAILABLE_FOR_SELECTED_QUESTION));
			}

			session.put("pendingCategoryMap", pendingCategoryMap);

			session.put("questionList", questionList);
			MstScorerInfo scorerInfo = ((MstScorerInfo) session.get("scorerInfo"));
			log.info(scorerInfo.getScorerId()+"-"+sessionQuestionInfo.getMenuId()+"-"+"Loaded Pending Category Selection Screen."+"-{ Question Sequence: "+sessionQuestionInfo.getQuestionSeq()+"}");
			return SUCCESS;
		} catch (SaitenRuntimeException we) {
			throw we;
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.PENDING_CATEGORY_SELECTION_ACTION_EXCEPTION, e);
		}
	}

	public String getQuestionList() {
		return questionList;
	}

	/**
	 * @param questionList
	 */
	public void setQuestionList(String questionList) {
		this.questionList = questionList;
	}

	public Map<Short, String> getPendingCategoryMap() {
		return pendingCategoryMap;
	}

	/**
	 * @param pendingCategoryMap
	 */
	public void setPendingCategoryMap(Map<Short, String> pendingCategoryMap) {
		this.pendingCategoryMap = pendingCategoryMap;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.struts2.interceptor.SessionAware#setSession(java.util.Map)
	 */
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	/**
	 * @param pendingCategorySelectionService
	 */
	public void setPendingCategorySelectionService(
			PendingCategorySelectionService pendingCategorySelectionService) {
		this.pendingCategorySelectionService = pendingCategorySelectionService;
	}

	public String getSubjectShortName() {
		return subjectShortName;
	}

	public void setSubjectShortName(String subjectShortName) {
		this.subjectShortName = subjectShortName;
	}

	/**
	 * @return the selectedMarkValue
	 */
	public Short getSelectedMarkValue() {
		return selectedMarkValue;
	}

	/**
	 * @param selectedMarkValue
	 *            the selectedMarkValue to set
	 */
	public void setSelectedMarkValue(Short selectedMarkValue) {
		this.selectedMarkValue = selectedMarkValue;
	}

}
