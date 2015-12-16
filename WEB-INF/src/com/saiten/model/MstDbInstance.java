package com.saiten.model;

// Generated Dec 3, 2012 11:19:29 AM by Hibernate Tools 3.2.1.GA

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * MstDbInstance generated by hbm2java
 */
public class MstDbInstance implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int dbInstanceSeq;
	private String connectionString;
	private Character deleteFlag;
	private String updatePersonId;
	private Date updateDate;
	private Date createDate;
	private Set<MstQuestion> mstQuestions = new HashSet<MstQuestion>(0);

	public MstDbInstance() {
	}

	public MstDbInstance(int dbInstanceSeq) {
		this.dbInstanceSeq = dbInstanceSeq;
	}

	public MstDbInstance(int dbInstanceSeq, String connectionString,
			Character deleteFlag, String updatePersonId, Date updateDate,
			Date createDate, Set<MstQuestion> mstQuestions) {
		this.dbInstanceSeq = dbInstanceSeq;
		this.connectionString = connectionString;
		this.deleteFlag = deleteFlag;
		this.updatePersonId = updatePersonId;
		this.updateDate = updateDate;
		this.createDate = createDate;
		this.mstQuestions = mstQuestions;
	}

	public int getDbInstanceSeq() {
		return this.dbInstanceSeq;
	}

	public void setDbInstanceSeq(int dbInstanceSeq) {
		this.dbInstanceSeq = dbInstanceSeq;
	}

	public String getConnectionString() {
		return this.connectionString;
	}

	public void setConnectionString(String connectionString) {
		this.connectionString = connectionString;
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

	public Set<MstQuestion> getMstQuestions() {
		return this.mstQuestions;
	}

	public void setMstQuestions(Set<MstQuestion> mstQuestions) {
		this.mstQuestions = mstQuestions;
	}

}
