package com.saiten.info;

import java.util.Date;

/**
 * @author sachin
 * @version 1.0
 * @created 05-Dec-2012 4:59:12 PM
 */
public class MstScorerInfo {

	private char noDbUpdate;
	private String password;
	private String roleDescription;
	private byte roleId;
	private String scorerId;
	private String scorerName;
	private char deleteFlag;
	private String updatePersonId;
	private Date updateDate;
	private Date createDate;
	private String userType;

	public char getNoDbUpdate() {
		return noDbUpdate;
	}

	public void setNoDbUpdate(char noDbUpdate) {
		this.noDbUpdate = noDbUpdate;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRoleDescription() {
		return roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}

	public byte getRoleId() {
		return roleId;
	}

	public void setRoleId(byte roleId) {
		this.roleId = roleId;
	}

	public String getScorerId() {
		return scorerId;
	}

	public void setScorerId(String scorerId) {
		this.scorerId = scorerId;
	}

	public String getScorerName() {
		return scorerName;
	}

	public void setScorerName(String scorerName) {
		this.scorerName = scorerName;
	}

	public char getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(char deleteFlag) {
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

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
}