package com.saiten.info;

import java.util.Date;

import com.saiten.model.TranDescScoreHistory;
import com.saiten.util.SaitenUtil;

/**
 * @author Administrator
 * @version 1.0
 * @created 12-Dec-2012 3:54:16 PM
 */
public class HistoryInfo {

	private String answerNumber;
	private Character bookmarkFlag;
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
	private Integer qcSeq;
	private boolean qcFlag;
	private int isQualityRecord;

	public String getAnswerNumber() {
		return answerNumber;
	}

	public void setAnswerNumber(String answerNumber) {
		this.answerNumber = answerNumber;
	}

	public Character getBookmarkFlag() {
		return bookmarkFlag;
	}

	public void setBookmarkFlag(Character bookmarkFlag) {
		this.bookmarkFlag = bookmarkFlag;
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

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof HistoryInfo))
			return false;
		HistoryInfo castOther = (HistoryInfo) other;

		return ((this.getHistorySequence() == castOther.getHistorySequence()) || (this
				.getHistorySequence() != null
				&& castOther.getHistorySequence() != null && this
				.getHistorySequence().equals(castOther.getHistorySequence())))
				&& (this.getConnectionString() == castOther
						.getConnectionString() || (this.getConnectionString() != null
						&& castOther.getConnectionString() != null && this
						.getConnectionString().equals(
								castOther.getConnectionString())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getHistorySequence() == null ? 0 : this.getHistorySequence()
						.hashCode());
		result = 37
				* result
				+ (getConnectionString() == null ? 0 : this
						.getConnectionString().hashCode());
		return result;
	}

	public void copy(TranDescScoreHistory tranDescScoreHistory) {
		this.setBookmarkFlag(tranDescScoreHistory.getBookMarkFlag());
		this.setAnswerNumber(tranDescScoreHistory.getAnswerFormNum());
		this.setScoringStateName(SaitenUtil
				.getStateNameByScoringState((Short) tranDescScoreHistory
						.getScoringState()));
		this.setUpdateDate(tranDescScoreHistory.getUpdateDate());
		Integer gradeSeq = tranDescScoreHistory.getGradeSeq();
		Integer questionSeqence = tranDescScoreHistory.getQuestionSeq();
		if (gradeSeq != null) {
			this.setGradeNum(SaitenUtil.getGradeNumByGradeSequence(gradeSeq,
					questionSeqence));
			this.setResult(SaitenUtil.getResultByGradeSequence(gradeSeq,
					questionSeqence));
		}
		this.setPendingCategory(SaitenUtil
				.getPendingCategoryByPendingCategorySeq(tranDescScoreHistory
						.getPendingCategorySeq()));
		this.setComment(tranDescScoreHistory.getScorerComment());
		this.setQuestionSequence(questionSeqence);
		this.setConnectionString(connectionString);
		this.setSubjectName(SaitenUtil
				.getSubjectNameByQuestionSequence(questionSeqence));
		this.setQuestionNumber(SaitenUtil
				.getQuestionNumByQuestionSequence(questionSeqence));
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
	 * @return the qcFlag
	 */
	public boolean isQcFlag() {
		return qcFlag;
	}

	/**
	 * @param qcFlag
	 *            the qcFlag to set
	 */
	public void setQcFlag(boolean qcFlag) {
		this.qcFlag = qcFlag;
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