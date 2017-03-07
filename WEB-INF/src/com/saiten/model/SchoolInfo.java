package com.saiten.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * SchoolInfo entity. @author MyEclipse Persistence Tools
 * 
 * @author suwarna
 * @version $Revision: 1.0 $
 */

public class SchoolInfo extends CommonCSVInfo implements java.io.Serializable {

	// Fields

	/** The serial version uid */
	private static final long serialVersionUID = 1L;

	/** The school seq. */
	private Integer schoolSeq;

	/** The file seq. */
	private Integer fileSeq;
	// private String schoolCode;
	// private String summaryFlag;
	// private String boxNum;
	// private Short position;
	/** The update time. */
	private Date updateTime;

	/** The create date. */
	private Date createDate;

	/** The school details. */
	private Set<SchoolDetail> schoolDetails = new HashSet<SchoolDetail>(0);

	// Constructors

	/**
	 * default constructor.
	 */
	public SchoolInfo() {
	}

	/**
	 * minimal constructor.
	 *
	 * @param schoolSeq
	 *            the school seq
	 * @param fileSeq
	 *            the file seq
	 */
	public SchoolInfo(Integer schoolSeq, Integer fileSeq) {
		this.schoolSeq = schoolSeq;
		this.fileSeq = fileSeq;
	}

	/**
	 * full constructor.
	 *
	 * @param schoolSeq
	 *            the school seq
	 * @param fileSeq
	 *            the file seq
	 * @param schoolCode
	 *            the school code
	 * @param summaryFlag
	 *            the summary flag
	 * @param boxNum
	 *            the box num
	 * @param position
	 *            the position
	 * @param updateTime
	 *            the update time
	 * @param createDate
	 *            the create date
	 * @param schoolDetails
	 *            the school details
	 */
	public SchoolInfo(Integer schoolSeq, Integer fileSeq, String schoolCode, String summaryFlag, String boxNum,
			Short position, Date updateTime, Date createDate, Set<SchoolDetail> schoolDetails) {
		this.schoolSeq = schoolSeq;
		this.fileSeq = fileSeq;
		// this.schoolCode = schoolCode;
		// this.summaryFlag = summaryFlag;
		// this.boxNum = boxNum;
		// this.position = position;
		this.updateTime = updateTime;
		this.createDate = createDate;
		this.schoolDetails = schoolDetails;
	}

	// Property accessors

	/**
	 * Gets the school seq.
	 *
	 * 
	 * @return the school seq
	 */
	public Integer getSchoolSeq() {
		return this.schoolSeq;
	}

	/**
	 * Sets the school seq.
	 *
	 * @param schoolSeq
	 *            the new school seq
	 */
	public void setSchoolSeq(Integer schoolSeq) {
		this.schoolSeq = schoolSeq;
	}

	/**
	 * Gets the file seq.
	 *
	 * 
	 * @return the file seq
	 */
	public Integer getFileSeq() {
		return fileSeq;
	}

	/**
	 * Sets the file seq.
	 *
	 * @param fileSeq
	 *            the new file seq
	 */
	public void setFileSeq(Integer fileSeq) {
		this.fileSeq = fileSeq;
	}

	/*
	 * public String getSchoolCode() { return this.schoolCode; }
	 * 
	 * public void setSchoolCode(String schoolCode) { this.schoolCode =
	 * schoolCode; }
	 * 
	 * public String getSummaryFlag() { return this.summaryFlag; }
	 * 
	 * public void setSummaryFlag(String summaryFlag) { this.summaryFlag =
	 * summaryFlag; }
	 * 
	 * public String getBoxNum() { return this.boxNum; }
	 * 
	 * public void setBoxNum(String boxNum) { this.boxNum = boxNum; }
	 * 
	 * public Short getPosition() { return this.position; }
	 * 
	 * public void setPosition(Short position) { this.position = position; }
	 */

	/**
	 * Gets the update time.
	 *
	 * 
	 * @return the update time
	 */
	public Date getUpdateTime() {
		return this.updateTime;
	}

	/**
	 * Sets the update time.
	 *
	 * @param updateTime
	 *            the new update time
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * Gets the creates the date.
	 *
	 * 
	 * @return the creates the date
	 */
	public Date getCreateDate() {
		return this.createDate;
	}

	/**
	 * Sets the creates the date.
	 *
	 * @param createDate
	 *            the new creates the date
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * Gets the school details.
	 *
	 * 
	 * @return the school details
	 */
	public Set<SchoolDetail> getSchoolDetails() {
		return this.schoolDetails;
	}

	/**
	 * Sets the school details.
	 *
	 * @param schoolDetails
	 *            the new school details
	 */
	public void setSchoolDetails(Set<SchoolDetail> schoolDetails) {
		this.schoolDetails = schoolDetails;
	}

}