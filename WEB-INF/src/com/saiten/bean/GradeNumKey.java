package com.saiten.bean;

/**
 * @author sachin
 * @version 1.0
 * @created 24-Dec-2012 12:22:57 PM
 */
public class GradeNumKey {
	private Integer gradeSeq;

	private int questionSeq;

	public Integer getGradeSeq() {
		return gradeSeq;
	}

	/**
	 * 
	 * @param gradeSeq
	 */
	public void setGradeSeq(Integer gradeSeq) {
		this.gradeSeq = gradeSeq;
	}

	public int getQuestionSeq() {
		return questionSeq;
	}

	/**
	 * 
	 * @param questionSeq
	 */
	public void setQuestionSeq(int questionSeq) {
		this.questionSeq = questionSeq;
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
		if (!(other instanceof GradeNumKey))
			return false;
		GradeNumKey castOther = (GradeNumKey) other;

		return (this.getGradeSeq().equals(castOther.getGradeSeq()))
				&& (this.getQuestionSeq() == castOther.getQuestionSeq());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getGradeSeq();
		result = 37 * result + this.getQuestionSeq();
		return result;
	}

}
