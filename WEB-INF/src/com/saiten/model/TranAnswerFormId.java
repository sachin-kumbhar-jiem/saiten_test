package com.saiten.model;

// Generated Jul 1, 2014 11:56:18 AM by Hibernate Tools 3.4.0.CR1

/**
 * TranAnswerFormId generated by hbm2java
 */
public class TranAnswerFormId implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String answerFormNum;
	private String schoolCode;
	private String subjectCode;

	public TranAnswerFormId() {
	}

	public TranAnswerFormId(String answerFormNum, String schoolCode,
			String subjectCode) {
		this.answerFormNum = answerFormNum;
		this.schoolCode = schoolCode;
		this.subjectCode = subjectCode;
	}

	public String getAnswerFormNum() {
		return this.answerFormNum;
	}

	public void setAnswerFormNum(String answerFormNum) {
		this.answerFormNum = answerFormNum;
	}

	public String getSchoolCode() {
		return this.schoolCode;
	}

	public void setSchoolCode(String schoolCode) {
		this.schoolCode = schoolCode;
	}

	public String getSubjectCode() {
		return this.subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((answerFormNum == null) ? 0 : answerFormNum.hashCode());
		result = prime * result
				+ ((schoolCode == null) ? 0 : schoolCode.hashCode());
		result = prime * result
				+ ((subjectCode == null) ? 0 : subjectCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TranAnswerFormId))
			return false;
		TranAnswerFormId castOther = (TranAnswerFormId) other;

		return ((this.getAnswerFormNum() == castOther.getAnswerFormNum()) || (this
				.getAnswerFormNum() != null
				&& castOther.getAnswerFormNum() != null && this
				.getAnswerFormNum().equals(castOther.getAnswerFormNum())))
				&& ((this.getSchoolCode() == castOther.getSchoolCode()) || (this
						.getSchoolCode() != null
						&& castOther.getSchoolCode() != null && this
						.getSchoolCode().equals(castOther.getSchoolCode())))
				&& (this.getSubjectCode() == castOther.getSubjectCode());
	}

}
