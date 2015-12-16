package com.saiten.model;

// Generated Dec 3, 2012 11:19:29 AM by Hibernate Tools 3.2.1.GA

/**
 * MstGradeResultId generated by hbm2java
 */
public class MstGradeResultId implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int questionSeq;
	private int gradeNum;

	public MstGradeResultId() {
	}

	public MstGradeResultId(int questionSeq, int gradeNum) {
		this.questionSeq = questionSeq;
		this.gradeNum = gradeNum;
	}

	public int getQuestionSeq() {
		return this.questionSeq;
	}

	public void setQuestionSeq(int questionSeq) {
		this.questionSeq = questionSeq;
	}

	public int getGradeNum() {
		return this.gradeNum;
	}

	public void setGradeNum(int gradeNum) {
		this.gradeNum = gradeNum;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof MstGradeResultId))
			return false;
		MstGradeResultId castOther = (MstGradeResultId) other;

		return (this.getQuestionSeq() == castOther.getQuestionSeq())
				&& (this.getGradeNum() == castOther.getGradeNum());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getQuestionSeq();
		result = 37 * result + this.getGradeNum();
		return result;
	}

}
