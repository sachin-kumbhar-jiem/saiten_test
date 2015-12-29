package com.saiten.info;

import java.util.Date;
import java.util.List;

public class ScoreSamplingInfo {
	private Integer answerSeq;
	private String answerNumber;
	private Date updateDate;
	private String subjectName;
	private Short questionNumber;
	private String scoringStateName;
	private Integer gradeNum;
	private Character result;
	private String latestScreenScorerId;
	private String checkPoints;
	private long commentCount;
	private String imageFileName;
	private Integer gradeSeq;
	private Short scoringState;
	private AnswerInfo answerInfo;
	private Short pendingCategory;
	private String comments;
	private List<Short> markValueList;
	private Short denyCategory;

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
	 * @return the subjectName
	 */
	public String getSubjectName() {
		return subjectName;
	}

	/**
	 * @param subjectName
	 *            the subjectName to set
	 */
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	/**
	 * @return the questionNumber
	 */
	public Short getQuestionNumber() {
		return questionNumber;
	}

	/**
	 * @param questionNumber
	 *            the questionNumber to set
	 */
	public void setQuestionNumber(Short questionNumber) {
		this.questionNumber = questionNumber;
	}

	/**
	 * @return the scoringStateName
	 */
	public String getScoringStateName() {
		return scoringStateName;
	}

	/**
	 * @param scoringStateName
	 *            the scoringStateName to set
	 */
	public void setScoringStateName(String scoringStateName) {
		this.scoringStateName = scoringStateName;
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
	 * @return the result
	 */
	public Character getResult() {
		return result;
	}

	/**
	 * @param result
	 *            the result to set
	 */
	public void setResult(Character result) {
		this.result = result;
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
	 * @return the checkPoints
	 */
	public String getCheckPoints() {
		return checkPoints;
	}

	/**
	 * @param checkPoints
	 *            the checkPoints to set
	 */
	public void setCheckPoints(String checkPoints) {
		this.checkPoints = checkPoints;
	}

	/**
	 * @return the answerNumber
	 */
	public String getAnswerNumber() {
		return answerNumber;
	}

	/**
	 * @param answerNumber
	 *            the answerNumber to set
	 */
	public void setAnswerNumber(String answerNumber) {
		this.answerNumber = answerNumber;
	}

	public long getCommentCount() {
		return commentCount;
	}

	/**
	 * @param commentCount
	 */
	public void setCommentCount(long commentCount) {
		this.commentCount = commentCount;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	/**
	 * @param imageFileName
	 */
	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public Integer getGradeSeq() {
		return gradeSeq;
	}

	public void setGradeSeq(Integer gradeSeq) {
		this.gradeSeq = gradeSeq;
	}

	public Short getScoringState() {
		return scoringState;
	}

	public void setScoringState(Short scoringState) {
		this.scoringState = scoringState;
	}

	public AnswerInfo getAnswerInfo() {
		return answerInfo;
	}

	/**
	 * @param answerInfo
	 */
	public void setAnswerInfo(AnswerInfo answerInfo) {
		this.answerInfo = answerInfo;
	}

	public Short getPendingCategory() {
		return pendingCategory;
	}

	public void setPendingCategory(Short pendingCategory) {
		this.pendingCategory = pendingCategory;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * @return the markValueList
	 */
	public List<Short> getMarkValueList() {
		return markValueList;
	}

	/**
	 * @param markValueList
	 *            the markValueList to set
	 */
	public void setMarkValueList(List<Short> markValueList) {
		this.markValueList = markValueList;
	}

	public Short getDenyCategory() {
		return denyCategory;
	}

	public void setDenyCategory(Short denyCategory) {
		this.denyCategory = denyCategory;
	}

	@Override
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ScoreSamplingInfo))
			return false;
		ScoreSamplingInfo castOther = (ScoreSamplingInfo) other;

		return this.getAnswerSeq().equals(castOther.getAnswerSeq());
	}

}
