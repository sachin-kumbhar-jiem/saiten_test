package com.saiten.model;

// Generated Dec 3, 2012 11:19:29 AM by Hibernate Tools 3.2.1.GA

import java.util.Date;

/**
 * MstMenuRole generated by hbm2java
 */
public class MstMenuRole implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MstMenuRoleId id;
	private MstScorerRole mstScorerRole;
	private Character enable;
	private Character deleteFlag;
	private String updatePersonId;
	private Date updateDate;
	private Date createDate;

	public MstMenuRole() {
	}

	public MstMenuRole(MstMenuRoleId id, MstScorerRole mstScorerRole) {
		this.id = id;
		this.mstScorerRole = mstScorerRole;
	}

	public MstMenuRole(MstMenuRoleId id, MstScorerRole mstScorerRole,
			Character enable, Character deleteFlag, String updatePersonId,
			Date updateDate, Date createDate) {
		this.id = id;
		this.mstScorerRole = mstScorerRole;
		this.enable = enable;
		this.deleteFlag = deleteFlag;
		this.updatePersonId = updatePersonId;
		this.updateDate = updateDate;
		this.createDate = createDate;
	}

	public MstMenuRoleId getId() {
		return this.id;
	}

	public void setId(MstMenuRoleId id) {
		this.id = id;
	}

	public MstScorerRole getMstScorerRole() {
		return this.mstScorerRole;
	}

	public void setMstScorerRole(MstScorerRole mstScorerRole) {
		this.mstScorerRole = mstScorerRole;
	}

	public Character getEnable() {
		return this.enable;
	}

	public void setEnable(Character enable) {
		this.enable = enable;
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
