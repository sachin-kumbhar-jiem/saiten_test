package com.saiten.action;

import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.saiten.exception.SaitenRuntimeException;
import com.saiten.info.MstScorerInfo;
import com.saiten.info.QuestionInfo;
import com.saiten.service.GradeSelectionService;
import com.saiten.service.InspectionGroupSeqSelectionService;
import com.saiten.util.ErrorCode;
import com.saiten.util.WebAppConst;

/**
 * @author sachin
 * 
 */
public class InspectionGroupSeqSelectionAction extends ActionSupport implements
		SessionAware {

	private static Logger log = Logger.getLogger(GradeSelectionAction.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private InspectionGroupSeqSelectionService inspectionGroupSeqSelectionService;

	private Map<String, Object> session;

	private Map<String, String> inspectionGroupSeqMap;

	private Integer inspectionGroupSeq;

	public String onLoad() {
		try {
			QuestionInfo sessionQuestionInfo = (QuestionInfo) session
					.get("questionInfo");
			int questionSeq = sessionQuestionInfo.getQuestionSeq();
			String scorerId = ((MstScorerInfo) session.get("scorerInfo"))
					.getScorerId();
			String connectionString = sessionQuestionInfo.getConnectionString();

			inspectionGroupSeqMap = inspectionGroupSeqSelectionService
					.fetchInspectionGroupSeqAndCount(questionSeq, scorerId,
							connectionString);
			if (inspectionGroupSeqMap.isEmpty()) {
				this.addActionError(getText(WebAppConst.ERROR_NO_INSPECTION_GROUP_SEQ_AVAILABLE_FOR_SELECTED_QUESTION));
			}

			log.info(scorerId + "-" + sessionQuestionInfo.getMenuId() + "-"
					+ "Loaded InspectionGroupSeqSelection Screen."
					+ "-{ Question Sequence: "
					+ sessionQuestionInfo.getQuestionSeq() + "}");
			return SUCCESS;
		} catch (SaitenRuntimeException we) {
			throw we;
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.INSPECTION_GROUP_SEQ_SELECTION_ACTION_EXCEPTION,
					e);
		}
	}

	public String execute() {
		// update QuestionInfo object with inspectionGroupSeq value
		QuestionInfo sessionQuestionInfo = (QuestionInfo) session
				.get("questionInfo");
		sessionQuestionInfo.setInspectionGroupSeq(inspectionGroupSeq);

		String redirectScreen = null;
		if (WebAppConst.SCORE_TYPE[3]
				.equals(sessionQuestionInfo.getScoreType())) {
			redirectScreen = "markValueSelection";
		} else if ((WebAppConst.SCORE_TYPE[2].equals(sessionQuestionInfo
				.getScoreType()) || WebAppConst.SCORE_TYPE[1]
				.equals(sessionQuestionInfo.getScoreType()))) {
			redirectScreen = "gradeSelection";
		}

		return redirectScreen;
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
	 * @return the inspectionGroupSeqMap
	 */
	public Map<String, String> getInspectionGroupSeqMap() {
		return inspectionGroupSeqMap;
	}

	/**
	 * @param inspectionGroupSeqMap
	 *            the inspectionGroupSeqMap to set
	 */
	public void setInspectionGroupSeqMap(
			Map<String, String> inspectionGroupSeqMap) {
		this.inspectionGroupSeqMap = inspectionGroupSeqMap;
	}

	/**
	 * @param inspectionGroupSeqSelectionService
	 *            the inspectionGroupSeqSelectionService to set
	 */
	public void setInspectionGroupSeqSelectionService(
			InspectionGroupSeqSelectionService inspectionGroupSeqSelectionService) {
		this.inspectionGroupSeqSelectionService = inspectionGroupSeqSelectionService;
	}

	/**
	 * @return the inspectionGroupSeq
	 */
	public Integer getInspectionGroupSeq() {
		return inspectionGroupSeq;
	}

	/**
	 * @param inspectionGroupSeq
	 *            the inspectionGroupSeq to set
	 */
	public void setInspectionGroupSeq(Integer inspectionGroupSeq) {
		this.inspectionGroupSeq = inspectionGroupSeq;
	}

}
