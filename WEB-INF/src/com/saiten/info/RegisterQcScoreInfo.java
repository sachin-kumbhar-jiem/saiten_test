/**
 * 
 */
package com.saiten.info;

import java.util.Date;

/**
 * @author user
 * 
 */
public class RegisterQcScoreInfo {
	private Integer qcSeq;
	private Integer answerSeq;
	private Integer questionSeq;
	private String scorerId;
	private Short scoringState;
	private Double bitValue;
	private Integer gradeSeq;
	private Integer gradeNum;
	private Integer pendingCategorySeq;
	private Short pendingCategory;
	private Date createDate;
	private Date updateDate;
	private String scorerComment;
	private String answerFormNum;
	private String isScoreOrPending;

	/**
	 * @param qcSeq
	 * @param answerSeq
	 * @param questionSeq
	 * @param scorerId
	 * @param scoringState
	 * @param bitValue
	 * @param gradeSeq
	 * @param gradeNum
	 * @param pendingCategorySeq
	 * @param pendingCategory
	 * @param createDate
	 * @param updateDate
	 * @param scorerComment
	 * @param answerFormNum
	 * @param isScoreOrPending
	 */
	public RegisterQcScoreInfo(Integer qcSeq, Integer answerSeq,
			Integer questionSeq, String scorerId, Short scoringState,
			Double bitValue, Integer gradeSeq, Integer gradeNum,
			Integer pendingCategorySeq, Short pendingCategory, Date createDate,
			Date updateDate, String scorerComment, String answerFormNum,
			String isScoreOrPending) {

		this.qcSeq = qcSeq;
		this.answerSeq = answerSeq;
		this.questionSeq = questionSeq;
		this.scorerId = scorerId;
		this.scoringState = scoringState;
		this.bitValue = bitValue;
		this.gradeSeq = gradeSeq;
		this.gradeNum = gradeNum;
		this.pendingCategorySeq = pendingCategorySeq;
		this.pendingCategory = pendingCategory;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.scorerComment = scorerComment;
		this.answerFormNum = answerFormNum;
		this.isScoreOrPending = isScoreOrPending;
	}

	/**
	 * @return the qcSeq
	 */
	public Integer getQcSeq() {
		return qcSeq;
	}

	/**
	 * @param qcSeq
	 *            the qcSeq to set
	 */
	public void setQcSeq(Integer qcSeq) {
		this.qcSeq = qcSeq;
	}

	/**
	 * @return the answerSeq
	 */
	public Integer getAnswerSeq() {
		return answerSeq;
	}

	/**
	 * @param answerSeq
	 *            the answerSeq to set
	 */
	public void setAnswerSeq(Integer answerSeq) {
		this.answerSeq = answerSeq;
	}

	/**
	 * @return the questionSeq
	 */
	public Integer getQuestionSeq() {
		return questionSeq;
	}

	/**
	 * @param questionSeq
	 *            the questionSeq to set
	 */
	public void setQuestionSeq(Integer questionSeq) {
		this.questionSeq = questionSeq;
	}

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
	 * @return the scoringState
	 */
	public Short getScoringState() {
		return scoringState;
	}

	/**
	 * @param scoringState
	 *            the scoringState to set
	 */
	public void setScoringState(Short scoringState) {
		this.scoringState = scoringState;
	}

	/**
	 * @return the bitValue
	 */
	public Double getBitValue() {
		return bitValue;
	}

	/**
	 * @param bitValue
	 *            the bitValue to set
	 */
	public void setBitValue(Double bitValue) {
		this.bitValue = bitValue;
	}

	/**
	 * @return the gradeSeq
	 */
	public Integer getGradeSeq() {
		return gradeSeq;
	}

	/**
	 * @param gradeSeq
	 *            the gradeSeq to set
	 */
	public void setGradeSeq(Integer gradeSeq) {
		this.gradeSeq = gradeSeq;
	}

	/**
	 * @return the gradeNum
	 */
	public Integer getGradeNum() {
		return gradeNum;
	}

	/**
	 * @param gradeNum
	 *            the gradeNum to set
	 */
	public void setGradeNum(Integer gradeNum) {
		this.gradeNum = gradeNum;
	}

	/**
	 * @return the pendingCategorySeq
	 */
	public Integer getPendingCategorySeq() {
		return pendingCategorySeq;
	}

	/**
	 * @param pendingCategorySeq
	 *            the pendingCategorySeq to set
	 */
	public void setPendingCategorySeq(Integer pendingCategorySeq) {
		this.pendingCategorySeq = pendingCategorySeq;
	}

	/**
	 * @return the pendingCategory
	 */
	public Short getPendingCategory() {
		return pendingCategory;
	}

	/**
	 * @param pendingCategory
	 *            the pendingCategory to set
	 */
	public void setPendingCategory(Short pendingCategory) {
		this.pendingCategory = pendingCategory;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate
	 *            the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate
	 *            the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return the scorerComment
	 */
	public String getScorerComment() {
		return scorerComment;
	}

	/**
	 * @param scorerComment
	 *            the scorerComment to set
	 */
	public void setScorerComment(String scorerComment) {
		this.scorerComment = scorerComment;
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
	 * @return the isScoreOrPending
	 */
	public String getIsScoreOrPending() {
		return isScoreOrPending;
	}

	/**
	 * @param isScoreOrPending
	 *            the isScoreOrPending to set
	 */
	public void setIsScoreOrPending(String isScoreOrPending) {
		this.isScoreOrPending = isScoreOrPending;
	}

}
