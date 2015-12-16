package com.saiten.model;

// Generated Feb 19, 2015 2:30:02 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * TranDescScoreHistory generated by hbm2java
 */
@SuppressWarnings("serial")
public class TranDescScoreHistory implements java.io.Serializable {

	private Integer historySeq;
	private TranDescScore tranDescScore;
	private Integer questionSeq;
	private String scorerId;
	private Byte scorerRoleId;
	private Integer gradeSeq;
	private Integer gradeNum;
	private Double bitValue;
	private Short eventId;
	private Short scoringState;
	private Integer pendingCategorySeq;
	private Short pendingCategory;
	private String scorerComment;
	private Character bookMarkFlag;
	private Character inspectFlag;
	private Character samplingFlag;
	private String answerFormNum;
	private Character validFlag;
	private Date updateDate;
	private Date createDate;
	private String latestScreenScorerId;
	private Character qualityCheckFlag;
	private Character neighbourMatchFlag;
	private String secondLatestScreenScorerId;
	private String thirdLatestScreenScorerId;

	public TranDescScoreHistory() {
	}

	public TranDescScoreHistory(TranDescScore tranDescScore) {
		this.tranDescScore = tranDescScore;
	}

	public TranDescScoreHistory(TranDescScore tranDescScore, Integer questionSeq,
			String scorerId, Byte scorerRoleId, Integer gradeSeq,
			Integer gradeNum, Double bitValue, Short eventId,
			Short scoringState, Integer pendingCategorySeq,
			Short pendingCategory, String scorerComment,
			Character bookMarkFlag, Character inspectFlag,
			Character samplingFlag, String answerFormNum, Character validFlag,
			Date updateDate, Date createDate, String latestScreenScorerId,
			Character qualityCheckFlag, Character neighbourMatchFlag, String secondLatestScreenScorerId, String thirdLatestScreenScorerId) {
		this.tranDescScore = tranDescScore;
		this.questionSeq = questionSeq;
		this.scorerId = scorerId;
		this.scorerRoleId = scorerRoleId;
		this.gradeSeq = gradeSeq;
		this.gradeNum = gradeNum;
		this.bitValue = bitValue;
		this.eventId = eventId;
		this.scoringState = scoringState;
		this.pendingCategorySeq = pendingCategorySeq;
		this.pendingCategory = pendingCategory;
		this.scorerComment = scorerComment;
		this.bookMarkFlag = bookMarkFlag;
		this.inspectFlag = inspectFlag;
		this.samplingFlag = samplingFlag;
		this.answerFormNum = answerFormNum;
		this.validFlag = validFlag;
		this.updateDate = updateDate;
		this.createDate = createDate;
		this.latestScreenScorerId = latestScreenScorerId;
		this.qualityCheckFlag = qualityCheckFlag;
		this.neighbourMatchFlag = neighbourMatchFlag;
		this.secondLatestScreenScorerId = secondLatestScreenScorerId;
		this.thirdLatestScreenScorerId = thirdLatestScreenScorerId;
	}

	public Integer getHistorySeq() {
		return this.historySeq;
	}

	public void setHistorySeq(Integer historySeq) {
		this.historySeq = historySeq;
	}

	public TranDescScore getTranDescScore() {
		return this.tranDescScore;
	}

	public void setTranDescScore(TranDescScore tranDescScore) {
		this.tranDescScore = tranDescScore;
	}
	
	public Integer getQuestionSeq() {
		return this.questionSeq;
	}

	public void setQuestionSeq(Integer questionSeq) {
		this.questionSeq = questionSeq;
	}

	public String getScorerId() {
		return this.scorerId;
	}

	public void setScorerId(String scorerId) {
		this.scorerId = scorerId;
	}

	public Byte getScorerRoleId() {
		return this.scorerRoleId;
	}

	public void setScorerRoleId(Byte scorerRoleId) {
		this.scorerRoleId = scorerRoleId;
	}

	public Integer getGradeSeq() {
		return this.gradeSeq;
	}

	public void setGradeSeq(Integer gradeSeq) {
		this.gradeSeq = gradeSeq;
	}

	public Integer getGradeNum() {
		return this.gradeNum;
	}

	public void setGradeNum(Integer gradeNum) {
		this.gradeNum = gradeNum;
	}

	public Double getBitValue() {
		return this.bitValue;
	}

	public void setBitValue(Double bitValue) {
		this.bitValue = bitValue;
	}

	public Short getEventId() {
		return this.eventId;
	}

	public void setEventId(Short eventId) {
		this.eventId = eventId;
	}

	public Short getScoringState() {
		return this.scoringState;
	}

	public void setScoringState(Short scoringState) {
		this.scoringState = scoringState;
	}

	public Integer getPendingCategorySeq() {
		return this.pendingCategorySeq;
	}

	public void setPendingCategorySeq(Integer pendingCategorySeq) {
		this.pendingCategorySeq = pendingCategorySeq;
	}

	public Short getPendingCategory() {
		return this.pendingCategory;
	}

	public void setPendingCategory(Short pendingCategory) {
		this.pendingCategory = pendingCategory;
	}

	public String getScorerComment() {
		return this.scorerComment;
	}

	public void setScorerComment(String scorerComment) {
		this.scorerComment = scorerComment;
	}

	public Character getBookMarkFlag() {
		return this.bookMarkFlag;
	}

	public void setBookMarkFlag(Character bookMarkFlag) {
		this.bookMarkFlag = bookMarkFlag;
	}

	public Character getInspectFlag() {
		return this.inspectFlag;
	}

	public void setInspectFlag(Character inspectFlag) {
		this.inspectFlag = inspectFlag;
	}

	public Character getSamplingFlag() {
		return this.samplingFlag;
	}

	public void setSamplingFlag(Character samplingFlag) {
		this.samplingFlag = samplingFlag;
	}

	public String getAnswerFormNum() {
		return this.answerFormNum;
	}

	public void setAnswerFormNum(String answerFormNum) {
		this.answerFormNum = answerFormNum;
	}

	public Character getValidFlag() {
		return this.validFlag;
	}

	public void setValidFlag(Character validFlag) {
		this.validFlag = validFlag;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getLatestScreenScorerId() {
		return this.latestScreenScorerId;
	}

	public void setLatestScreenScorerId(String latestScreenScorerId) {
		this.latestScreenScorerId = latestScreenScorerId;
	}

	public Character getQualityCheckFlag() {
		return this.qualityCheckFlag;
	}

	public void setQualityCheckFlag(Character qualityCheckFlag) {
		this.qualityCheckFlag = qualityCheckFlag;
	}

	public Character getNeighbourMatchFlag() {
		return this.neighbourMatchFlag;
	}

	public void setNeighbourMatchFlag(Character neighbourMatchFlag) {
		this.neighbourMatchFlag = neighbourMatchFlag;
	}

	public String getSecondLatestScreenScorerId() {
		return secondLatestScreenScorerId;
	}

	public void setSecondLatestScreenScorerId(String secondLatestScreenScorerId) {
		this.secondLatestScreenScorerId = secondLatestScreenScorerId;
	}

	public String getThirdLatestScreenScorerId() {
		return thirdLatestScreenScorerId;
	}

	public void setThirdLatestScreenScorerId(String thirdLatestScreenScorerId) {
		this.thirdLatestScreenScorerId = thirdLatestScreenScorerId;
	}

}
