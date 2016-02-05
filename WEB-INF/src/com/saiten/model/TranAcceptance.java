package com.saiten.model;

import java.util.Date;

public class TranAcceptance implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer acceptanceSeq;
	private TranDescScore tranDescScore;
	private Character markFlag;
	private String markBy;
	private String comment;
	private Character explainFlag;
	private String explainBy;
	private Date createDate;
	private Date updateDate;

	public TranAcceptance() {

	}

	public TranAcceptance(TranDescScore tranDescScore, Character markFlag, String markBy,
			String comment, Character explainFlag, String explainBy,
			Date createDate, Date updateDate) {
		this.tranDescScore = tranDescScore;
		this.markFlag = markFlag;
		this.markBy = markBy;
		this.comment = comment;
		this.explainFlag = explainFlag;
		this.explainBy = explainBy;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}
	

	public Integer getAcceptanceSeq() {
		return acceptanceSeq;
	}

	public void setAcceptanceSeq(Integer acceptanceSeq) {
		this.acceptanceSeq = acceptanceSeq;
	}

	public TranDescScore getTranDescScore() {
		return tranDescScore;
	}

	public void setTranDescScore(TranDescScore tranDescScore) {
		this.tranDescScore = tranDescScore;
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
