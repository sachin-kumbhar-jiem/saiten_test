package com.saiten.model;

// Generated Jul 1, 2014 11:56:18 AM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * TranPunchSummary generated by hbm2java
 */
public class TranPunchSummary implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int punchSummarySeq;
	private Integer questionSeq;
	private String punchText;
	private Integer gradeSeq;
	private Double bitValue;
	private Date updateDate;
	private Date createDate;

	public TranPunchSummary() {
	}

	public TranPunchSummary(int punchSummarySeq) {
		this.punchSummarySeq = punchSummarySeq;
	}

	public TranPunchSummary(int punchSummarySeq, Integer questionSeq,
			String punchText, Integer gradeSeq, Double bitValue,
			Date updateDate, Date createDate) {
		this.punchSummarySeq = punchSummarySeq;
		this.questionSeq = questionSeq;
		this.punchText = punchText;
		this.gradeSeq = gradeSeq;
		this.bitValue = bitValue;
		this.updateDate = updateDate;
		this.createDate = createDate;
	}

	public int getPunchSummarySeq() {
		return this.punchSummarySeq;
	}

	public void setPunchSummarySeq(int punchSummarySeq) {
		this.punchSummarySeq = punchSummarySeq;
	}

	public Integer getQuestionSeq() {
		return this.questionSeq;
	}

	public void setQuestionSeq(Integer questionSeq) {
		this.questionSeq = questionSeq;
	}

	public String getPunchText() {
		return this.punchText;
	}

	public void setPunchText(String punchText) {
		this.punchText = punchText;
	}

	public Integer getGradeSeq() {
		return this.gradeSeq;
	}

	public void setGradeSeq(Integer gradeSeq) {
		this.gradeSeq = gradeSeq;
	}

	public Double getBitValue() {
		return this.bitValue;
	}

	public void setBitValue(Double bitValue) {
		this.bitValue = bitValue;
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

}
