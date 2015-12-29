package com.saiten.model;

import java.util.Date;

public class MstDenyCategory implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int denyCategorySeq;
	private MstQuestion mstQuestion;
	private Short denyCategory;
	private String denyDescription;
	private Short displayIndex;
	private Character validFlag;
	private Character deleteFlag;
	private String updatePersonId;
	private Date updateDate;
	private Date createDate;
	
	public MstDenyCategory () {
	}
	
	public MstDenyCategory (int denyCategorySeq) {
		this.denyCategorySeq = denyCategorySeq;
	}
	
	public MstDenyCategory (int denyCategorySeq, MstQuestion mstQuestion,
			Short denyCategory, String denyDescription,
			Short displayIndex, Character validFlag, Character deleteFlag,
			String updatePersonId, Date updateDate, Date createDate) {
		this.denyCategorySeq = denyCategorySeq;
		this.mstQuestion = mstQuestion;
		this.denyCategory = denyCategory;
		this.denyDescription = denyDescription;
		this.displayIndex = displayIndex;
		this.validFlag = validFlag;
		this.deleteFlag = deleteFlag;
		this.updatePersonId = updatePersonId;
		this.updateDate = updateDate;
		this.createDate = createDate;
	}

	public int getDenyCategorySeq() {
		return denyCategorySeq;
	}

	public void setDenyCategorySeq(int denyCategorySeq) {
		this.denyCategorySeq = denyCategorySeq;
	}

	public MstQuestion getMstQuestion() {
		return mstQuestion;
	}

	public void setMstQuestion(MstQuestion mstQuestion) {
		this.mstQuestion = mstQuestion;
	}

	public Short getDenyCategory() {
		return denyCategory;
	}

	public void setDenyCategory(Short denyCategory) {
		this.denyCategory = denyCategory;
	}

	public String getDenyDescription() {
		return denyDescription;
	}

	public void setDenyDescription(String denyDescription) {
		this.denyDescription = denyDescription;
	}

	public Short getDisplayIndex() {
		return displayIndex;
	}

	public void setDisplayIndex(Short displayIndex) {
		this.displayIndex = displayIndex;
	}

	public Character getValidFlag() {
		return validFlag;
	}

	public void setValidFlag(Character validFlag) {
		this.validFlag = validFlag;
	}

	public Character getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Character deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getUpdatePersonId() {
		return updatePersonId;
	}

	public void setUpdatePersonId(String updatePersonId) {
		this.updatePersonId = updatePersonId;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
