package com.saiten.info;

import java.util.Date;

public class KenshuRecordInfo {
	private Integer acceptenceSeq;
	private Integer answerSeq;
	private Character markFlag;
	private String markBy;
	private String comment;
	private Character explainFlag;
	private String explainBy;
	private Date createDate;
	private Date updateDate;
	private TranDescScoreInfo tranDescScoreInfo;
	
	public Integer getAcceptenceSeq() {
		return acceptenceSeq;
	}
	public void setAcceptenceSeq(Integer acceptenceSeq) {
		this.acceptenceSeq = acceptenceSeq;
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
	public TranDescScoreInfo getTranDescScoreInfo() {
		return tranDescScoreInfo;
	}
	public void setTranDescScoreInfo(TranDescScoreInfo tranDescScoreInfo) {
		this.tranDescScoreInfo = tranDescScoreInfo;
	}
	
	
}
