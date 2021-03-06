package com.saiten.model;

import java.util.Date;

/**
 * LzhInfo generated by hbm2java
 */
public class LzhInfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer sequence;
	private String lzhName;
	private String boxNum;
	private String tempDbInstanceName;
	private Character deleteFlag;
	private Date updateDate;
	private Date createDate;

	public LzhInfo() {
	}

	public LzhInfo(String lzhName, String boxNum, String tempDbInstanceName, Character deleteFlag, Date updateDate,
			Date createDate) {
		this.lzhName = lzhName;
		this.boxNum = boxNum;
		this.tempDbInstanceName = tempDbInstanceName;
		this.deleteFlag = deleteFlag;
		this.updateDate = updateDate;
		this.createDate = createDate;
	}

	public Integer getSequence() {
		return this.sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public String getLzhName() {
		return this.lzhName;
	}

	public void setLzhName(String lzhName) {
		this.lzhName = lzhName;
	}

	public String getBoxNum() {
		return this.boxNum;
	}

	public void setBoxNum(String boxNum) {
		this.boxNum = boxNum;
	}

	public String getTempDbInstanceName() {
		return this.tempDbInstanceName;
	}

	public void setTempDbInstanceName(String tempDbInstanceName) {
		this.tempDbInstanceName = tempDbInstanceName;
	}

	public Character getDeleteFlag() {
		return this.deleteFlag;
	}

	public void setDeleteFlag(Character deleteFlag) {
		this.deleteFlag = deleteFlag;
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
