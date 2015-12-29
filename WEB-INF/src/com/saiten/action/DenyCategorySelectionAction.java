package com.saiten.action;

import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.saiten.info.MstScorerInfo;
import com.saiten.info.QuestionInfo;
import com.saiten.service.DenyCategorySelectionService;
import com.saiten.util.WebAppConst;

public class DenyCategorySelectionAction extends ActionSupport implements
		SessionAware {
	
	private static Logger log = Logger
			.getLogger(DenyCategorySelectionAction.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private DenyCategorySelectionService denyCategorySelectionService;

	private Map<String, Object> session;

	private Map<Short, String> denyCategoryMap;

	private String questionList;

	private String subjectShortName;

	private Short selectedMarkValue;

	public String onLoad() {
		System.out.println("this is here");

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

		denyCategoryMap = denyCategorySelectionService
				.findDenyCategoriesByQuestionSeq(questionSeq);
		if (denyCategoryMap.isEmpty()) {
			this.addActionError(getText(WebAppConst.ERROR_NO_DENY_CATEGORY_AVAILABLE_FOR_SELECTED_QUESTION));
		}
		session.put("denyCategoryMap", denyCategoryMap);

		session.put("questionList", questionList);
		MstScorerInfo scorerInfo = ((MstScorerInfo) session.get("scorerInfo"));
		log.info(scorerInfo.getScorerId()+"-"+sessionQuestionInfo.getMenuId()+"-"+"Loaded deny Category Selection Screen."+"-{ Question Sequence: "+sessionQuestionInfo.getQuestionSeq()+"}");
		return SUCCESS;
	}

	public DenyCategorySelectionService getDenyCategorySelectionService() {
		return denyCategorySelectionService;
	}

	public void setDenyCategorySelectionService(
			DenyCategorySelectionService denyCategorySelectionService) {
		this.denyCategorySelectionService = denyCategorySelectionService;
	}

	public Map<Short, String> getDenyCategoryMap() {
		return denyCategoryMap;
	}

	public void setDenyCategoryMap(Map<Short, String> denyCategoryMap) {
		this.denyCategoryMap = denyCategoryMap;
	}

	public String getQuestionList() {
		return questionList;
	}

	public void setQuestionList(String questionList) {
		this.questionList = questionList;
	}

	public String getSubjectShortName() {
		return subjectShortName;
	}

	public void setSubjectShortName(String subjectShortName) {
		this.subjectShortName = subjectShortName;
	}

	public Short getSelectedMarkValue() {
		return selectedMarkValue;
	}

	public void setSelectedMarkValue(Short selectedMarkValue) {
		this.selectedMarkValue = selectedMarkValue;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;

	}

}
