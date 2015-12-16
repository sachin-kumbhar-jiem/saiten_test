package com.saiten.info;

import java.util.Date;

/**
 * @author Administrator
 * @version 1.0
 * @created 12-Dec-2012 3:54:16 PM
 */
public class BookmarkInfo {

	private String answerNumber;
	private String comment;
	private Integer historySequence;
	private Date updateDate;
	private String subjectName;
	private Short questionNumber;
	private String scoringStateName;
	private Integer gradeNum;
	private Character result;
	private Short pendingCategory;
	private Integer questionSequence;
	private String connectionString;
	private int isQualityRecord;

	public String getAnswerNumber() {
		return answerNumber;
	}

	public void setAnswerNumber(String answerNumber) {
		this.answerNumber = answerNumber;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getHistorySequence() {
		return historySequence;
	}

	public void setHistorySequence(Integer historySequence) {
		this.historySequence = historySequence;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public Short getQuestionNumber() {
		return questionNumber;
	}

	public void setQuestionNumber(Short questionNumber) {
		this.questionNumber = questionNumber;
	}

	public String getScoringStateName() {
		return scoringStateName;
	}

	public void setScoringStateName(String scoringStateName) {
		this.scoringStateName = scoringStateName;
	}

	public Integer getGradeNum() {
		return gradeNum;
	}

	public void setGradeNum(Integer gradeNum) {
		this.gradeNum = gradeNum;
	}

	public Character getResult() {
		return result;
	}

	public void setResult(Character result) {
		this.result = result;
	}

	public Short getPendingCategory() {
		return pendingCategory;
	}

	public void setPendingCategory(Short pendingCategory) {
		this.pendingCategory = pendingCategory;
	}

	public Integer getQuestionSequence() {
		return questionSequence;
	}

	public void setQuestionSequence(Integer questionSequence) {
		this.questionSequence = questionSequence;
	}

	public String getConnectionString() {
		return connectionString;
	}

	public void setConnectionString(String connectionString) {
		this.connectionString = connectionString;
	}

	/**
	 * @return the isQualityRecord
	 */
	public int getIsQualityRecord() {
		return isQualityRecord;
	}

	/**
	 * @param isQualityRecord the isQualityRecord to set
	 */
	public void setIsQualityRecord(int isQualityRecord) {
		this.isQualityRecord = isQualityRecord;
	}

}