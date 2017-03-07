package com.saiten.model;

import java.sql.Timestamp;
import java.util.Date;

/**
 * SchoolDetail entity. @author MyEclipse Persistence Tools
 * 
 * @author suwarna
 * @version $Revision: 1.0 $
 */

public class SchoolDetail implements java.io.Serializable {

	// Fields

	/** The serial version uid */
	private static final long serialVersionUID = 1L;

	/** The school detail seq. */
	private Integer schoolDetailSeq;

	/** The school info. */
	private SchoolInfo schoolInfo;

	/** The subject code. */
	private String subjectCode;

	/** The doc type. */
	private String docType;

	/** The doc count. */
	private String docCount;

	/** The update date. */
	private Date updateDate;

	/** The create date. */
	private Date createDate;

	// Constructors

	/**
	 * default constructor.
	 */
	public SchoolDetail() {
	}

	/**
	 * minimal constructor.
	 *
	 * @param schoolDetailSeq
	 *            the school detail seq
	 */
	public SchoolDetail(Integer schoolDetailSeq) {
		this.schoolDetailSeq = schoolDetailSeq;
	}

	/**
	 * full constructor.
	 *
	 * @param schoolDetailSeq
	 *            the school detail seq
	 * @param schoolInfo
	 *            the school info
	 * @param subjectCode
	 *            the subject code
	 * @param docType
	 *            the doc type
	 * @param docCount
	 *            the doc count
	 * @param updateDate
	 *            the update date
	 * @param createDate
	 *            the create date
	 */
	public SchoolDetail(Integer schoolDetailSeq, SchoolInfo schoolInfo, String subjectCode, String docType,
			String docCount, Timestamp updateDate, Timestamp createDate) {
		this.schoolDetailSeq = schoolDetailSeq;
		this.schoolInfo = schoolInfo;
		this.subjectCode = subjectCode;
		this.docType = docType;
		this.docCount = docCount;
		this.updateDate = updateDate;
		this.createDate = createDate;
	}

	// Property accessors

	/**
	 * Gets the school detail seq.
	 *
	 * 
	 * @return the school detail seq
	 */
	public Integer getSchoolDetailSeq() {
		return this.schoolDetailSeq;
	}

	/**
	 * Sets the school detail seq.
	 *
	 * @param schoolDetailSeq
	 *            the new school detail seq
	 */
	public void setSchoolDetailSeq(Integer schoolDetailSeq) {
		this.schoolDetailSeq = schoolDetailSeq;
	}

	/**
	 * Gets the school info.
	 *
	 * 
	 * @return the school info
	 */
	public SchoolInfo getSchoolInfo() {
		return this.schoolInfo;
	}

	/**
	 * Sets the school info.
	 *
	 * @param schoolInfo
	 *            the new school info
	 */
	public void setSchoolInfo(SchoolInfo schoolInfo) {
		this.schoolInfo = schoolInfo;
	}

	/**
	 * Gets the subject code.
	 *
	 * 
	 * @return the subject code
	 */
	public String getSubjectCode() {
		return this.subjectCode;
	}

	/**
	 * Sets the subject code.
	 *
	 * @param subjectCode
	 *            the new subject code
	 */
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	/**
	 * Gets the doc type.
	 *
	 * 
	 * @return the doc type
	 */
	public String getDocType() {
		return this.docType;
	}

	/**
	 * Sets the doc type.
	 *
	 * @param docType
	 *            the new doc type
	 */
	public void setDocType(String docType) {
		this.docType = docType;
	}

	/**
	 * Gets the doc count.
	 *
	 * 
	 * @return the doc count
	 */
	public String getDocCount() {
		return this.docCount;
	}

	/**
	 * Sets the doc count.
	 *
	 * @param docCount
	 *            the new doc count
	 */
	public void setDocCount(String docCount) {
		this.docCount = docCount;
	}

	/**
	 * Gets the update date.
	 *
	 * 
	 * @return the update date
	 */
	public Date getUpdateDate() {
		return this.updateDate;
	}

	/**
	 * Sets the update date.
	 *
	 * @param updateDate
	 *            the new update date
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
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

	/*
	 * @Override public int compareTo(SchoolDetail detail) { if(this.subjectCode
	 * != null) return this.subjectCode.compareTo(detail.getSubjectCode()); else
	 * return -1; }
	 */

}