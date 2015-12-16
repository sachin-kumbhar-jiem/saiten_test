/**
 * 
 */
package com.saiten.bean;


/**
 * @author rajeshwars
 *
 */
public class ScorerQuestionKey {
	private String scorerId;
	private int questionSeq;
	/**
	 * @return the scorerId
	 */
	public String getScorerId() {
		return scorerId;
	}
	/**
	 * @param scorerId the scorerId to set
	 */
	public void setScorerId(String scorerId) {
		this.scorerId = scorerId;
	}
	/**
	 * @return the questionSeq
	 */
	public int getQuestionSeq() {
		return questionSeq;
	}
	/**
	 * @param questionSeq the questionSeq to set
	 */
	public void setQuestionSeq(int questionSeq) {
		this.questionSeq = questionSeq;
	}
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ScorerQuestionKey))
			return false;
		ScorerQuestionKey castOther = (ScorerQuestionKey) other;

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
