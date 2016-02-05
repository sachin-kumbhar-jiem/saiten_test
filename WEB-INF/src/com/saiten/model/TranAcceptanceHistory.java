package com.saiten.model;

import java.util.Date;

public class TranAcceptanceHistory implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer acceptanceHistorySeq;
	private TranAcceptance tranAcceptance;
	private Integer answerSeq;
	private Character markFlag;
	private String markBy;
	private String comment;
	private Character explainFlag;
	private String explainBy;
	private Date createDate;
	private Date updateDate;

	public TranAcceptanceHistory() {

	}

	public TranAcceptanceHistory(TranAcceptance tranAcceptance,
			Integer answerSeq, Character markFlag, String markBy,
			String comment, Character explainFlag, String explainBy,
			Date createDate, Date updateDate) {
		this.tranAcceptance = tranAcceptance;
		this.answerSeq = answerSeq;
		this.markFlag = markFlag;
		this.markBy = markBy;
		this.comment = comment;
		this.explainFlag = explainFlag;
		this.explainBy = explainBy;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}

	public Integer getAcceptanceHistorySeq() {
		return acceptanceHistorySeq;
	}

	public void setAcceptanceHistorySeq(Integer acceptanceHistorySeq) {
		this.acceptanceHistorySeq = acceptanceHistorySeq;
	}

	public TranAcceptance getTranAcceptance() {
		return tranAcceptance;
	}

	public void setTranAcceptance(TranAcceptance tranAcceptance) {
		this.tranAcceptance = tranAcceptance;
	}

	public Integer getAnswerSeq() {
		return answerSeq;
	}

	public void setAnswerSeq(Integer answerSeq) {
		this.answerSeq = answerSeq;
	}

	public Character getMarkFlag() {
		return markFlag;
	}

	public void setMarkFlag(Character markFlag) {
		this.markFlag = markFlag;
	}

	public String getMarkBy() {
		return markBy;
	}

	public void setMarkBy(String markBy) {
		this.markBy = markBy;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Character getExplainFlag() {
		return explainFlag;
	}

	public void setExplainFlag(Character explainFlag) {
		this.explainFlag = explainFlag;
	}

	public String getExplainBy() {
		return explainBy;
	}

	public void setExplainBy(String explainBy) {
		this.explainBy = explainBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	

}
