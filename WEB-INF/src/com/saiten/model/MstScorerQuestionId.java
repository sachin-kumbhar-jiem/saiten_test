package com.saiten.model;

// Generated Dec 3, 2012 11:19:29 AM by Hibernate Tools 3.2.1.GA

/**
 * MstScorerQuestionId generated by hbm2java
 */
public class MstScorerQuestionId implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String scorerId;
	private int questionSeq;

	public MstScorerQuestionId() {
	}

	public MstScorerQuestionId(String scorerId, int questionSeq) {
		this.scorerId = scorerId;
		this.questionSeq = questionSeq;
	}

	public String getScorerId() {
		return this.scorerId;
	}

	public void setScorerId(String scorerId) {
		this.scorerId = scorerId;
	}

	public int getQuestionSeq() {
		return this.questionSeq;
	}

	public void setQuestionSeq(int questionSeq) {
		this.questionSeq = questionSeq;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof MstScorerQuestionId))
			return false;
		MstScorerQuestionId castOther = (MstScorerQuestionId) other;

		return ((this.getScorerId() == castOther.getScorerId()) || (this
				.getScorerId() != null && castOther.getScorerId() != null && this
				.getScorerId().equals(castOther.getScorerId())))
				&& (this.getQuestionSeq() == castOther.getQuestionSeq());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getScorerId() == null ? 0 : this.getScorerId().hashCode());
		result = 37 * result + this.getQuestionSeq();
		return result;
	}

}
