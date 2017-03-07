package com.saiten.model;

import java.util.Date;

/**
 * FileInfo entity. @author MyEclipse Persistence Tools
 * 
 * @author suwarna
 * @version $Revision: 1.0 $
 */

public class FileInfo implements java.io.Serializable {

	// Fields

	/** The serial version uid */
	private static final long serialVersionUID = 1L;

	/** The file seq. */
	private Integer fileSeq;

	/** The omr read date. */
	private String omrReadDate;

	/** The serial num. */
	private String serialNum;

	/** The school info count. */
	private Short schoolInfoCount;

	/** The class info count. */
	private Short classInfoCount;

	/** The box num. */
	private String boxNum;

	/** The data file name. */
	private String dataFileName;

	/** The csv file name. */
	private String csvFileName;

	/** The file read status. */
	private Short fileReadStatus;

	/** The delete flag. */
	private String deleteFlag;

	/** The update date. */
	private Date updateDate;

	/** The create date. */
	private Date createDate;
	/*
	 * private Set schoolInfos = new HashSet(0); private Set classInfos = new
	 * HashSet(0);
	 */

	// Constructors

	/**
	 * default constructor.
	 */
	public FileInfo() {
	}

	/**
	 * minimal constructor.
	 *
	 * @param fileSeq
	 *            the file seq
	 */
	public FileInfo(Integer fileSeq) {
		this.fileSeq = fileSeq;
	}

	/**
	 * full constructor.
	 *
	 * @param fileSeq
	 *            the file seq
	 * @param omrReadDate
	 *            the omr read date
	 * @param serialNum
	 *            the serial num
	 * @param schoolInfoCount
	 *            the school info count
	 * @param classInfoCount
	 *            the class info count
	 * @param boxNum
	 *            the box num
	 * @param dataFileName
	 *            the data file name
	 * @param csvFileName
	 *            the csv file name
	 * @param fileReadStatus
	 *            the file read status
	 * @param deleteFlag
	 *            the delete flag
	 * @param updateDate
	 *            the update date
	 * @param createDate
	 *            the create date
	 */
	public FileInfo(Integer fileSeq, String omrReadDate, String serialNum, Short schoolInfoCount, Short classInfoCount,
			String boxNum, String dataFileName, String csvFileName, Short fileReadStatus, String deleteFlag,
			Date updateDate, Date createDate) {
		// ,Set schoolInfos, Set classInfos) {
		this.fileSeq = fileSeq;
		this.omrReadDate = omrReadDate;
		this.serialNum = serialNum;
		this.schoolInfoCount = schoolInfoCount;
		this.classInfoCount = classInfoCount;
		this.boxNum = boxNum;
		this.dataFileName = dataFileName;
		this.csvFileName = csvFileName;
		this.fileReadStatus = fileReadStatus;
		this.deleteFlag = deleteFlag;
		this.updateDate = updateDate;
		this.createDate = createDate;
		/*
		 * this.schoolInfos = schoolInfos; this.classInfos = classInfos;
		 */
	}

	// Property accessors

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

	/**
	 * Gets the omr read date.
	 *
	 * 
	 * @return the omr read date
	 */
	public String getOmrReadDate() {
		return this.omrReadDate;
	}

	/**
	 * Sets the omr read date.
	 *
	 * @param omrReadDate
	 *            the new omr read date
	 */
	public void setOmrReadDate(String omrReadDate) {
		this.omrReadDate = omrReadDate;
	}

	/**
	 * Gets the serial num.
	 *
	 * 
	 * @return the serial num
	 */
	public String getSerialNum() {
		return this.serialNum;
	}

	/**
	 * Sets the serial num.
	 *
	 * @param serialNum
	 *            the new serial num
	 */
	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}

	/**
	 * Gets the school info count.
	 *
	 * 
	 * @return the school info count
	 */
	public Short getSchoolInfoCount() {
		return this.schoolInfoCount;
	}

	/**
	 * Sets the school info count.
	 *
	 * @param schoolInfoCount
	 *            the new school info count
	 */
	public void setSchoolInfoCount(Short schoolInfoCount) {
		this.schoolInfoCount = schoolInfoCount;
	}

	/**
	 * Gets the class info count.
	 *
	 * 
	 * @return the class info count
	 */
	public Short getClassInfoCount() {
		return this.classInfoCount;
	}

	/**
	 * Sets the class info count.
	 *
	 * @param classInfoCount
	 *            the new class info count
	 */
	public void setClassInfoCount(Short classInfoCount) {
		this.classInfoCount = classInfoCount;
	}

	/**
	 * Gets the box num.
	 *
	 * 
	 * @return the box num
	 */
	public String getBoxNum() {
		return this.boxNum;
	}

	/**
	 * Sets the box num.
	 *
	 * @param boxNum
	 *            the new box num
	 */
	public void setBoxNum(String boxNum) {
		this.boxNum = boxNum;
	}

	/**
	 * Gets the data file name.
	 *
	 * 
	 * @return the data file name
	 */
	public String getDataFileName() {
		return this.dataFileName;
	}

	/**
	 * Sets the data file name.
	 *
	 * @param dataFileName
	 *            the new data file name
	 */
	public void setDataFileName(String dataFileName) {
		this.dataFileName = dataFileName;
	}

	/**
	 * Gets the csv file name.
	 *
	 * 
	 * @return the csv file name
	 */
	public String getCsvFileName() {
		return this.csvFileName;
	}

	/**
	 * Sets the csv file name.
	 *
	 * @param csvFileName
	 *            the new csv file name
	 */
	public void setCsvFileName(String csvFileName) {
		this.csvFileName = csvFileName;
	}

	/**
	 * Gets the file read status.
	 *
	 * 
	 * @return the file read status
	 */
	public Short getFileReadStatus() {
		return this.fileReadStatus;
	}

	/**
	 * Sets the file read status.
	 *
	 * @param fileReadStatus
	 *            the new file read status
	 */
	public void setFileReadStatus(Short fileReadStatus) {
		this.fileReadStatus = fileReadStatus;
	}

	/**
	 * Gets the delete flag.
	 *
	 * 
	 * @return the delete flag
	 */
	public String getDeleteFlag() {
		return this.deleteFlag;
	}

	/**
	 * Sets the delete flag.
	 *
	 * @param deleteFlag
	 *            the new delete flag
	 */
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
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
	 * public Set getSchoolInfos() { return this.schoolInfos; }
	 * 
	 * public void setSchoolInfos(Set schoolInfos) { this.schoolInfos =
	 * schoolInfos; }
	 * 
	 * public Set getClassInfos() { return this.classInfos; }
	 * 
	 * public void setClassInfos(Set classInfos) { this.classInfos = classInfos;
	 * }
	 */

}