package com.saiten.info;

import java.io.Serializable;

public class AcceptanceDisplayInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String subjectCode;
	private Short questionNum;
	private String kenshuUserId;
	private String recordSearchCriteria;
	public String getSubjectCode() {
		return subjectCode;
	}
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}
	public Short getQuestionNum() {
		return questionNum;
	}
	public void setQuestionNum(Short questionNum) {
		this.questionNum = questionNum;
	}
	
	public String getKenshuUserId() {
		return kenshuUserId;
	}
	public void setKenshuUserId(String kenshuUserId) {
		this.kenshuUserId = kenshuUserId;
	}
	public String getRecordSearchCriteria() {
		return recordSearchCriteria;
	}
	public void setRecordSearchCriteria(String recordSearchCriteria) {
		this.recordSearchCriteria = recordSearchCriteria;
	}

}
