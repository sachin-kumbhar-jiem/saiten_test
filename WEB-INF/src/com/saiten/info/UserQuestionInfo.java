/**
 * 
 */
package com.saiten.info;

/**
 * @author user
 * 
 */
public class UserQuestionInfo {

	private String scorerId;

	private QuestionInfo questionInfo;

	private String answerFormNum;

	private ScorerAccessLogInfo scorerAccessLogInfo;

	/**
	 * @return the scorerId
	 */
	public String getScorerId() {
		return scorerId;
	}

	/**
	 * @param scorerId
	 *            the scorerId to set
	 */
	public void setScorerId(String scorerId) {
		this.scorerId = scorerId;
	}

	/**
	 * @return the questionInfo
	 */
	public QuestionInfo getQuestionInfo() {
		return questionInfo;
	}

	/**
	 * @param questionInfo
	 *            the questionInfo to set
	 */
	public void setQuestionInfo(QuestionInfo questionInfo) {
		this.questionInfo = questionInfo;
	}

	/**
	 * @return the answerFormNum
	 */
	public String getAnswerFormNum() {
		return answerFormNum;
	}

	/**
	 * @param answerFormNum
	 *            the answerFormNum to set
	 */
	public void setAnswerFormNum(String answerFormNum) {
		this.answerFormNum = answerFormNum;
	}

	/**
	 * @return the scorerAccessLogInfo
	 */
	public ScorerAccessLogInfo getScorerAccessLogInfo() {
		return scorerAccessLogInfo;
	}

	/**
	 * @param scorerAccessLogInfo
	 *            the scorerAccessLogInfo to set
	 */
	public void setScorerAccessLogInfo(ScorerAccessLogInfo scorerAccessLogInfo) {
		this.scorerAccessLogInfo = scorerAccessLogInfo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((scorerId == null) ? 0 : scorerId.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof UserQuestionInfo))
			return false;
		UserQuestionInfo other = (UserQuestionInfo) obj;
		if (scorerId == null) {
			if (other.scorerId != null)
				return false;
		} else if (!scorerId.equals(other.scorerId))
			return false;
		return true;
	}
}
