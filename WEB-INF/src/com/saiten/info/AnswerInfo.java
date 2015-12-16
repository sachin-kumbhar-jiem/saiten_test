package com.saiten.info;

import java.util.Date;

/**
 * @author sachin
 * @version 1.0
 * @created 11-Dec-2012 12:40:12 PM
 */
public class AnswerInfo {
	private int answerSeq;
	private Integer historySeq;
	private Integer qcSeq;
	private Double bitValue;
	private String lockBy;
	private Character bookMarkFlag;
	private String scorerComment;
	private Date updateDate;
	private Integer pendingCategorySeq;
	private String latestScorerId;
	private String punchText;
	private int questionSeq;
	private Character qualityCheckFlag;
	private Integer lookAftSeq;
	private String latestScreenScorerId;
	private String secondLatestScreenScorerId;

	public int getQuestionSeq() {
		return questionSeq;
	}

	public void setQuestionSeq(int questionSeq) {
		this.questionSeq = questionSeq;
	}

	public String getPunchText() {
		return punchText;
	}

	public void setPunchText(String punchText) {
		this.punchText = punchText;
	}

	public String getLatestScorerId() {
		return latestScorerId;
	}

	public void setLatestScorerId(String latestScorerId) {
		this.latestScorerId = latestScorerId;
	}

	public Integer getPendingCategorySeq() {
		return pendingCategorySeq;
	}

	/**
	 * 
	 * @param pendingCategorySeq
	 */
	public void setPendingCategorySeq(Integer pendingCategorySeq) {
		this.pendingCategorySeq = pendingCategorySeq;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * 
	 * @param updateDate
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Character getBookMarkFlag() {
		return bookMarkFlag;
	}

	/**
	 * 
	 * @param bookMarkFlag
	 */
	public void setBookMarkFlag(Character bookMarkFlag) {
		this.bookMarkFlag = bookMarkFlag;
	}

	public String getScorerComment() {
		return scorerComment;
	}

	/**
	 * 
	 * @param scorerComment
	 */
	public void setScorerComment(String scorerComment) {
		this.scorerComment = scorerComment;
	}

	public String getLockBy() {
		return lockBy;
	}

	/**
	 * 
	 * @param lockBy
	 */
	public void setLockBy(String lockBy) {
		this.lockBy = lockBy;
	}

	public int getAnswerSeq() {
		return answerSeq;
	}

	/**
	 * 
	 * @param answerSeq
	 */
	public void setAnswerSeq(int answerSeq) {
		this.answerSeq = answerSeq;
	}

	public Integer getHistorySeq() {
		return historySeq;
	}

	/**
	 * 
	 * @param historySeq
	 */
	public void setHistorySeq(Integer historySeq) {
		this.historySeq = historySeq;
	}

	public Double getBitValue() {
		return bitValue;
	}

	/**
	 * 
	 * @param bitValue
	 */
	public void setBitValue(Double bitValue) {
		this.bitValue = bitValue;
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

	public Integer getQcSeq() {
		return qcSeq;
	}

	public void setQcSeq(Integer qcSeq) {
		this.qcSeq = qcSeq;
	}

	/**
	 * @return the lookAftSeq
	 */
	public Integer getLookAftSeq() {
		return lookAftSeq;
	}

	/**
	 * @param lookAftSeq
	 *            the lookAftSeq to set
	 */
	public void setLookAftSeq(Integer lookAftSeq) {
		this.lookAftSeq = lookAftSeq;
	}

	public String getLatestScreenScorerId() {
		return latestScreenScorerId;
	}

	public void setLatestScreenScorerId(String latestScreenScorerId) {
		this.latestScreenScorerId = latestScreenScorerId;
	}

	public String getSecondLatestScreenScorerId() {
		return secondLatestScreenScorerId;
	}

	public void setSecondLatestScreenScorerId(String secondLatestScreenScorerId) {
		this.secondLatestScreenScorerId = secondLatestScreenScorerId;
	}

	@Override
	public String toString() {
		StringBuilder data = new StringBuilder();
		data.append("{ AnswerSequence: "+answerSeq+", HistorySequence: "+historySeq+", QcSequence: "+qcSeq+",  BitValue: "+bitValue+", LockBy: "+lockBy+", BookMarkFlag: "+bookMarkFlag+", ScorerComment: "+scorerComment+", UpdateDate: "+updateDate+", PendingCategorySequence: "+pendingCategorySeq+", LatestScorerId: "+latestScorerId+", PunchText: "+punchText+", QuestionSequence: "+questionSeq+", QualityCheckFlag: "+qualityCheckFlag+", LookAftSeq: "+lookAftSeq+", LatestScreenScorerId: "+latestScreenScorerId+", SecondLatestScreenScorerId: "+secondLatestScreenScorerId);
		data.append("}");
		return data.toString();
	}
}
