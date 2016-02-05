package com.saiten.info;

import java.io.Serializable;

public class KenshuSamplingInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String subjectCode;
	private Short questionNum;
	private Integer resultCount;
	
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
	public Integer getResultCount() {
		return resultCount;
	}
	public void setResultCount(Integer resultCount) {
		this.resultCount = resultCount;
	}
}
