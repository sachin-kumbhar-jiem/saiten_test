package com.saiten.bean;

/**
 * @author sachin
 * @version 1.0
 * @created 24-Dec-2012 12:22:57 PM
 */
public class SpecialScoringKey {
	private String subjectCode;

	private String answerFormNum;

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public String getAnswerFormNum() {
		return answerFormNum;
	}

	public void setAnswerFormNum(String answerFormNum) {
		this.answerFormNum = answerFormNum;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((answerFormNum == null) ? 0 : answerFormNum.hashCode());
		result = prime * result
				+ ((subjectCode == null) ? 0 : subjectCode.hashCode());
		return result;
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof SpecialScoringKey))
			return false;
		SpecialScoringKey castOther = (SpecialScoringKey) other;

		return (this.getSubjectCode().equals(castOther.getSubjectCode()))
				&& (this.getAnswerFormNum()
						.equals(castOther.getAnswerFormNum()));
	}

	/*public int hashCode() {
		int result = 17;

		result = 37 * result + this.getSubjectCode();
		result = 37
				* result
				+ +(getAnswerFormNum() == null ? 0 : this.getAnswerFormNum()
						.hashCode());
		return result;
	}*/
	
	
	
}
