package com.saiten.model;

import java.util.Date;

/**
 * ClassInfo entity. @author MyEclipse Persistence Tools
 * 
 * @author suwarna
 * @version $Revision: 1.0 $
 */

public class ClassInfo extends CommonCSVInfo implements java.io.Serializable {

	// Fields

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The class seq. */
	private Integer classSeq;

	/** The file seq. */
	private Integer fileSeq;
	/*
	 * private String subjectCode; private String schoolCode; private String
	 * classNum; private String summaryFlag;
	 */
	/** The answer count. */
	private Short answerCount;
	/*
	 * private String boxNum; private Short position;
	 */
	/** The paper type. */
	private String paperType;

	/** The update date. */
	private Date updateDate;

	/** The create date. */
	private Date createDate;

	// Constructors

	/**
	 * default constructor.
	 */
	public ClassInfo() {
	}

	/**
	 * full constructor.
	 *
	 * @param fileSeq
	 *            the file seq
	 * @param answerCount
	 *            the answer count
	 * @param paperType
	 *            the paper type
	 * @param updateDate
	 *            the update date
	 * @param createDate
	 *            the create date
	 */
	public ClassInfo(Integer fileSeq,
			/*
			 * String subjectCode, String schoolCode, String classNum, String
			 * summaryFlag,
			 */ Short answerCount, /* String boxNum, Short position, */ String paperType, Date updateDate,
			Date createDate) {
		this.fileSeq = fileSeq;
		// this.subjectCode = subjectCode;
		// this.schoolCode = schoolCode;
		// this.classNum = classNum;
		// this.summaryFlag = summaryFlag;
		this.answerCount = answerCount;
		// this.boxNum = boxNum;
		// this.position = position;
		this.paperType = paperType;
		this.updateDate = updateDate;
		this.createDate = createDate;
	}

	// Property accessors

	/**
	 * Gets the class seq.
	 *
	 * 
	 * @return the class seq
	 */
	public Integer getClassSeq() {
		return this.classSeq;
	}

	/**
	 * Sets the class seq.
	 *
	 * @param classSeq
	 *            the new class seq
	 */
	public void setClassSeq(Integer classSeq) {
		this.classSeq = classSeq;
	}

	/**
	 * Gets the file seq.
	 *
	 * 
	 * @return the file seq
	 */
	public Integer getFileSeq() {
		return this.fileSeq;
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
	 * public String getSubjectCode() { return this.subjectCode; }
	 * 
	 * public void setSubjectCode(String subjectCode) { this.subjectCode =
	 * subjectCode; }
	 * 
	 * public String getSchoolCode() { return this.schoolCode; }
	 * 
	 * public void setSchoolCode(String schoolCode) { this.schoolCode =
	 * schoolCode; }
	 * 
	 * public String getClassNum() { return this.classNum; }
	 * 
	 * public void setClassNum(String classNum) { this.classNum = classNum; }
	 * 
	 * public String getSummaryFlag() { return this.summaryFlag; }
	 * 
	 * public void setSummaryFlag(String summaryFlag) { this.summaryFlag =
	 * summaryFlag; }
	 */
	/**
	 * Gets the answer count.
	 *
	 * 
	 * @return the answer count
	 */
	public Short getAnswerCount() {
		return this.answerCount;
	}

	/**
	 * Sets the answer count.
	 *
	 * @param answerCount
	 *            the new answer count
	 */
	public void setAnswerCount(Short answerCount) {
		this.answerCount = answerCount;
	}

	/*
	 * public String getBoxNum() { return this.boxNum; }
	 * 
	 * public void setBoxNum(String boxNum) { this.boxNum = boxNum; }
	 * 
	 * public Short getPosition() { return this.position; }
	 * 
	 * public void setPosition(Short position) { this.position = position; }
	 */
	/**
	 * Gets the paper type.
	 *
	 * 
	 * @return the paper type
	 */
	public String getPaperType() {
		return this.paperType;
	}

	/**
	 * Sets the paper type.
	 *
	 * @param paperType
	 *            the new paper type
	 */
	public void setPaperType(String paperType) {
		this.paperType = paperType;
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

}