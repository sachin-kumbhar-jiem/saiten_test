package com.saiten.model;

// Generated Dec 3, 2012 11:19:29 AM by Hibernate Tools 3.2.1.GA

import java.util.Date;

/**
 * MstPendingCategory generated by hbm2java
 */
public class MstPendingCategory implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int pendingCategorySeq;
	private MstQuestion mstQuestion;
	private Short pendingCategory;
	private String pendingDescription;
	private Short displayIndex;
	private Character validFlag;
	private Character deleteFlag;
	private String updatePersonId;
	private Date updateDate;
	private Date createDate;

	public MstPendingCategory() {
	}

	public MstPendingCategory(int pendingCategorySeq) {
		this.pendingCategorySeq = pendingCategorySeq;
	}

	public MstPendingCategory(int pendingCategorySeq, MstQuestion mstQuestion,
			Short pendingCategory, String pendingDescription,
			Short displayIndex, Character validFlag, Character deleteFlag,
			String updatePersonId, Date updateDate, Date createDate) {
		this.pendingCategorySeq = pendingCategorySeq;
		this.mstQuestion = mstQuestion;
		this.pendingCategory = pendingCategory;
		this.pendingDescription = pendingDescription;
		this.displayIndex = displayIndex;
		this.validFlag = validFlag;
		this.deleteFlag = deleteFlag;
		this.updatePersonId = updatePersonId;
		this.updateDate = updateDate;
		this.createDate = createDate;
	}

	public int getPendingCategorySeq() {
		return this.pendingCategorySeq;
	}

	public void setPendingCategorySeq(int pendingCategorySeq) {
		this.pendingCategorySeq = pendingCategorySeq;
	}

	public MstQuestion getMstQuestion() {
		return this.mstQuestion;
	}

	public void setMstQuestion(MstQuestion mstQuestion) {
		this.mstQuestion = mstQuestion;
	}

	public Short getPendingCategory() {
		return this.pendingCategory;
	}

	public void setPendingCategory(Short pendingCategory) {
		this.pendingCategory = pendingCategory;
	}

	public String getPendingDescription() {
		return this.pendingDescription;
	}

	public void setPendingDescription(String pendingDescription) {
		this.pendingDescription = pendingDescription;
	}

	public Short getDisplayIndex() {
		return this.displayIndex;
	}

	public void setDisplayIndex(Short displayIndex) {
		this.displayIndex = displayIndex;
	}

	public Character getValidFlag() {
		return this.validFlag;
	}

	public void setValidFlag(Character validFlag) {
		this.validFlag = validFlag;
	}

	public Character getDeleteFlag() {
		return this.deleteFlag;
	}

	public void setDeleteFlag(Character deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getUpdatePersonId() {
		return this.updatePersonId;
	}

	public void setUpdatePersonId(String updatePersonId) {
		this.updatePersonId = updatePersonId;
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
