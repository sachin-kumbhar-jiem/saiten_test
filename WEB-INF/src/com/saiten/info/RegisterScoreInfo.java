/**
 * 
 */
package com.saiten.info;

import java.util.Date;

/**
 * @author user
 * 
 */
public class RegisterScoreInfo {
	private int answerSeq;
	private String scorerId;
	private String latestScreenScorerId;
	private String secondLatestScreenScorerId;
	private Short latestScoringState;
	private Double bitValue;
	private Integer gradeSeq;
	private Integer gradeNum;
	private Date updateDate;
	private Character qualityCheckFlag;
	private byte roleId;
	private Short eventId;
	private String scorerComment;
	private Character bookMarkFlag;
	private Integer historySeq;
	private boolean secondAndThirdLatestScorerIdFlag;
	private Integer pendingCategorySeq;
	private Short pendingCategory;
	private String isScoreOrPending;

	public RegisterScoreInfo() {
	}

	public RegisterScoreInfo(int answerSeq, String scorerId,
			String latestScreenScorerId, String secondLatestScreenScorerId,
			Short latestScoringState, Double bitValue, Integer gradeSeq,
			Integer gradeNum, Date updateDate, Character qualityCheckFlag,
			byte roleId, Short eventId, String scorerComment,
			Character bookMarkFlag, Integer historySeq,
			boolean secondAndThirdLatestScorerIdFlag,
			Integer pendingCategorySeq, Short pendingCategory,
			String isScoreOrPending) {
		this.answerSeq = answerSeq;
		this.scorerId = scorerId;
		this.latestScreenScorerId = latestScreenScorerId;
		this.secondLatestScreenScorerId = secondLatestScreenScorerId;
		this.latestScoringState = latestScoringState;
		this.bitValue = bitValue;
		this.gradeSeq = gradeSeq;
		this.gradeNum = gradeNum;
		this.updateDate = updateDate;
		this.qualityCheckFlag = qualityCheckFlag;
		this.roleId = roleId;
		this.eventId = eventId;
		this.scorerComment = scorerComment;
		this.bookMarkFlag = bookMarkFlag;
		this.historySeq = historySeq;
		this.secondAndThirdLatestScorerIdFlag = secondAndThirdLatestScorerIdFlag;
		this.pendingCategorySeq = pendingCategorySeq;
		this.pendingCategory = pendingCategory;
		this.isScoreOrPending = isScoreOrPending;
	}

	/**
	 * @return the answerSeq
	 */
	public int getAnswerSeq() {
		return answerSeq;
	}

	/**
	 * @param answerSeq
	 *            the answerSeq to set
	 */
	public void setAnswerSeq(int answerSeq) {
		this.answerSeq = answerSeq;
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
	 * @return the latestScreenScorerId
	 */
	public String getLatestScreenScorerId() {
		return latestScreenScorerId;
	}

	/**
	 * @param latestScreenScorerId
	 *            the latestScreenScorerId to set
	 */
	public void setLatestScreenScorerId(String latestScreenScorerId) {
		this.latestScreenScorerId = latestScreenScorerId;
	}

	/**
	 * @return the secondLatestScreenScorerId
	 */
	public String getSecondLatestScreenScorerId() {
		return secondLatestScreenScorerId;
	}

	/**
	 * @param secondLatestScreenScorerId
	 *            the secondLatestScreenScorerId to set
	 */
	public void setSecondLatestScreenScorerId(String secondLatestScreenScorerId) {
		this.secondLatestScreenScorerId = secondLatestScreenScorerId;
	}

	/**
	 * @return the latestScoringState
	 */
	public Short getLatestScoringState() {
		return latestScoringState;
	}

	/**
	 * @param latestScoringState
	 *            the latestScoringState to set
	 */
	public void setLatestScoringState(Short latestScoringState) {
		this.latestScoringState = latestScoringState;
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
	 * @return the qualityCheckFlag
	 */
	public Character getQualityCheckFlag() {
		return qualityCheckFlag;
	}

	/**
	 * @param qualityCheckFlag
	 *            the qualityCheckFlag to set
	 */
	public void setQualityCheckFlag(Character qualityCheckFlag) {
		this.qualityCheckFlag = qualityCheckFlag;
	}

	/**
	 * @return the roleId
	 */
	public byte getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId
	 *            the roleId to set
	 */
	public void setRoleId(byte roleId) {
		this.roleId = roleId;
	}

	/**
	 * @return the eventId
	 */
	public Short getEventId() {
		return eventId;
	}

	/**
	 * @param eventId
	 *            the eventId to set
	 */
	public void setEventId(Short eventId) {
		this.eventId = eventId;
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
	 * @return the bookMarkFlag
	 */
	public Character getBookMarkFlag() {
		return bookMarkFlag;
	}

	/**
	 * @param bookMarkFlag
	 *            the bookMarkFlag to set
	 */
	public void setBookMarkFlag(Character bookMarkFlag) {
		this.bookMarkFlag = bookMarkFlag;
	}

	/**
	 * @return the historySeq
	 */
	public Integer getHistorySeq() {
		return historySeq;
	}

	/**
	 * @param historySeq
	 *            the historySeq to set
	 */
	public void setHistorySeq(Integer historySeq) {
		this.historySeq = historySeq;
	}

	/**
	 * @return the secondAndThirdLatestScorerIdFlag
	 */
	public boolean isSecondAndThirdLatestScorerIdFlag() {
		return secondAndThirdLatestScorerIdFlag;
	}

	/**
	 * @param secondAndThirdLatestScorerIdFlag
	 *            the secondAndThirdLatestScorerIdFlag to set
	 */
	public void setSecondAndThirdLatestScorerIdFlag(
			boolean secondAndThirdLatestScorerIdFlag) {
		this.secondAndThirdLatestScorerIdFlag = secondAndThirdLatestScorerIdFlag;
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
