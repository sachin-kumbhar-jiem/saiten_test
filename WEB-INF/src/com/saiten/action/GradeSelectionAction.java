package com.saiten.action;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.saiten.bean.SaitenConfig;
import com.saiten.exception.SaitenRuntimeException;
import com.saiten.info.MstScorerInfo;
import com.saiten.info.QuestionInfo;
import com.saiten.service.GradeSelectionService;
import com.saiten.util.ErrorCode;
import com.saiten.util.WebAppConst;

/**
 * @author kailash
 * 
 */
public class GradeSelectionAction extends ActionSupport implements SessionAware {

	private static Logger log = Logger.getLogger(GradeSelectionAction.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private GradeSelectionService gradeSelectionService;

	private Map<String, String> gradeMap;

	private String questionList;

	private Map<String, Object> session;

	private String subjectShortName;

	private Short selectedMarkValue;

	private Short denyCategory;
	
	//private List gradeList;

	public String onLoad() {
		try {
			int questionSeq;
			QuestionInfo sessionQuestionInfo = (QuestionInfo) session.get("questionInfo");
			questionSeq = sessionQuestionInfo.getQuestionSeq();
			questionList = (String) session.get("questionList");

			if (sessionQuestionInfo.getScoreType() == WebAppConst.SCORE_TYPE[3]) {
				session.put("selectedMarkValue", selectedMarkValue);
			}

			String[] selectedQuestion = questionList.split(WebAppConst.COLON);
			subjectShortName = selectedQuestion[1];
			String gradeNumText = WebAppConst.GRADE_TEXT;
            
			// Get menuIdAndScoringStateMap from saitenConfigObject
			LinkedHashMap<String, Short> menuIdAndScoringStateMap = ((SaitenConfig) ServletActionContext
					.getServletContext().getAttribute("saitenConfigObject")).getMenuIdAndScoringStateMap();

			
			gradeMap = gradeSelectionService.findGradesByQuestionSeq(questionSeq, gradeNumText, menuIdAndScoringStateMap.get(sessionQuestionInfo.getMenuId()), sessionQuestionInfo.getConnectionString());
			
			//List gradeList = gradeSelectionService.findGradesByQuestionSeq(questionSeq, gradeNumText);

			if (gradeMap.isEmpty()) {
				this.addActionError(getText(WebAppConst.ERROR_NO_GRADE_AVAILABLE_FOR_SELECTED_QUESTION));
			}
			
			

			session.put("questionList", questionList);
			MstScorerInfo scorerInfo = ((MstScorerInfo) session.get("scorerInfo"));
			log.info(scorerInfo.getScorerId() + "-" + sessionQuestionInfo.getMenuId() + "-"
					+ "Loaded Grade Selection Screen." + "-{ Question Sequence: " + sessionQuestionInfo.getQuestionSeq()
					+ "}");
			
			session.put("gradeMap", gradeMap);
			//session.put("gradeList", gradeList);
			
			return SUCCESS;

		} catch (SaitenRuntimeException we) {
			throw we;
		} catch (Exception e) {
			throw new SaitenRuntimeException(ErrorCode.GRADE_SELECTION_ACTION_EXCEPTION, e);
		}
	}

	public Map<String, String> getGradeMap() {
		return gradeMap;
	}

	/**
	 * @param gradeMap
	 */
	public void setGradeMap(Map<String, String> gradeMap) {
		this.gradeMap = gradeMap;
	}

	/**
	 * @param gradeSelectionService
	 */
	public void setGradeSelectionService(GradeSelectionService gradeSelectionService) {
		this.gradeSelectionService = gradeSelectionService;
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

	public Short getDenyCategory() {
		return denyCategory;
	}

	public void setDenyCategory(Short denyCategory) {
		this.denyCategory = denyCategory;
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
