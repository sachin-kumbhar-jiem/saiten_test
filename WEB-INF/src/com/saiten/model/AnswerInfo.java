package com.saiten.model;

import java.util.HashSet;
import java.util.Set;;

/**
 * AnswerInfo entity. @author MyEclipse Persistence Tools
 * 
 * @author suwarna
 * @version $Revision: 1.0 $
 */

public class AnswerInfo extends CommonCSVInfo implements java.io.Serializable {

	// Fields

	/** The serial version uid */
	private static final long serialVersionUID = 1L;

	/** The answer seq. */
	private Integer answerSeq;

	/** The class seq. */
	private Integer classSeq;

	/** The attendance num. */
	private String attendanceNum;

	/** The gender. */
	private String gender;

	/** The answer form num. */
	private String answerFormNum;

	/** The check digit. */
	private String checkDigit;

	/** The error flag. */
	private String errorFlag;

	/** The school comment. */
	private String schoolComment;

	/** The answer details. */
	private Set<AnswerDetail> answerDetails = new HashSet<AnswerDetail>(0);
	// Constructors

	private String subjectCode;

	private String schoolCode;

	private String classNum;

	private String boxNum;

	private Short position;

	private String testsetCode;

	/** The answer detail feedbacks. */
	private Set<AnswerDetailFeedback> answerDetailFeedbacks = new HashSet<AnswerDetailFeedback>(0);

	/** The answer detail objs. */
	private Set<AnswerDetailObj> answerDetailObjs = new HashSet<AnswerDetailObj>(0);

	/**
	 * default constructor.
	 */
	public AnswerInfo() {
	}

	/**
	 * minimal constructor.
	 * 
	 * @param answerSeq
	 *            the answer seq
	 * @param classSeq
	 *            the class seq
	 */
	public AnswerInfo(Integer answerSeq, Integer classSeq) {
		this.answerSeq = answerSeq;
		this.classSeq = classSeq;
	}

	/**
	 * full constructor.
	 * 
	 * @param answerSeq
	 *            the answer seq
	 * @param classSeq
	 *            the class seq
	 * @param subjectCode
	 *            the subject code
	 * @param schoolCode
	 *            the school code
	 * @param classNum
	 *            the class num
	 * @param attendanceNum
	 *            the attendance num
	 * @param gender
	 *            the gender
	 * @param answerFormNum
	 *            the answer form num
	 * @param checkDigit
	 *            the check digit
	 * @param errorFlag
	 *            the error flag
	 * @param boxNum
	 *            the box num
	 * @param position
	 *            the position
	 * @param schoolComment
	 *            the school comment
	 * @param answerDetails
	 *            the answer details
	 */
	public AnswerInfo(Integer answerSeq, Integer classSeq, String subjectCode, String schoolCode, String classNum,
			String attendanceNum, String gender, String answerFormNum, String checkDigit, String errorFlag,
			String boxNum, Short position, String schoolComment, String testSetCode, Set<AnswerDetail> answerDetails,
			Set<AnswerDetailFeedback> answerDetailFeedbacks, Set<AnswerDetailObj> answerDetailObjs) {
		this.answerSeq = answerSeq;
		this.classSeq = classSeq;
		this.subjectCode = subjectCode;
		this.testsetCode = testSetCode;
		this.schoolCode = schoolCode;
		this.classNum = classNum;
		this.attendanceNum = attendanceNum;
		this.gender = gender;
		this.answerFormNum = answerFormNum;
		this.checkDigit = checkDigit;
		this.errorFlag = errorFlag;
		this.boxNum = boxNum;
		this.position = position;
		this.schoolComment = schoolComment;
		this.answerDetails = answerDetails;
		this.answerDetailFeedbacks = answerDetailFeedbacks;
		this.answerDetailObjs = answerDetailObjs;
	}

	// Property accessors

	/**
	 * Gets the answer seq.
	 * 
	 * 
	 * @return the answer seq
	 */
	public Integer getAnswerSeq() {
		return this.answerSeq;
	}

	/**
	 * Sets the answer seq.
	 * 
	 * @param answerSeq
	 *            the new answer seq
	 */
	public void setAnswerSeq(Integer answerSeq) {
		this.answerSeq = answerSeq;
	}

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

	public String getSubjectCode() {
		return this.subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public String getSchoolCode() {
		return this.schoolCode;
	}

	public void setSchoolCode(String schoolCode) {
		this.schoolCode = schoolCode;
	}

	public String getClassNum() {
		return this.classNum;
	}

	public void setClassNum(String classNum) {
		this.classNum = classNum;
	}

	/**
	 * Gets the attendance num.
	 * 
	 * 
	 * @return the attendance num
	 */
	public String getAttendanceNum() {
		return this.attendanceNum;
	}

	/**
	 * Sets the attendance num.
	 * 
	 * @param attendanceNum
	 *            the new attendance num
	 */
	public void setAttendanceNum(String attendanceNum) {
		this.attendanceNum = attendanceNum;
	}

	/**
	 * Gets the gender.
	 * 
	 * 
	 * @return the gender
	 */
	public String getGender() {
		return this.gender;
	}

	/**
	 * Sets the gender.
	 * 
	 * @param gender
	 *            the new gender
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * Gets the answer form num.
	 * 
	 * 
	 * @return the answer form num
	 */
	public String getAnswerFormNum() {
		return this.answerFormNum;
	}

	/**
	 * Sets the answer form num.
	 * 
	 * @param answerFormNum
	 *            the new answer form num
	 */
	public void setAnswerFormNum(String answerFormNum) {
		this.answerFormNum = answerFormNum;
	}

	/**
	 * Gets the check digit.
	 * 
	 * 
	 * @return the check digit
	 */
	public String getCheckDigit() {
		return this.checkDigit;
	}

	/**
	 * Sets the check digit.
	 * 
	 * @param checkDigit
	 *            the new check digit
	 */
	public void setCheckDigit(String checkDigit) {
		this.checkDigit = checkDigit;
	}

	/**
	 * Gets the error flag.
	 * 
	 * 
	 * @return the error flag
	 */
	public String getErrorFlag() {
		return this.errorFlag;
	}

	/**
	 * Sets the error flag.
	 * 
	 * @param errorFlag
	 *            the new error flag
	 */
	public void setErrorFlag(String errorFlag) {
		this.errorFlag = errorFlag;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saiten.model.CommonCSVInfo#getBoxNum()
	 */
	public String getBoxNum() {
		return this.boxNum;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saiten.model.CommonCSVInfo#setBoxNum(java.lang.String)
	 */
	public void setBoxNum(String boxNum) {
		this.boxNum = boxNum;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saiten.model.CommonCSVInfo#getPosition()
	 */
	public Short getPosition() {
		return this.position;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saiten.model.CommonCSVInfo#setPosition(java.lang.Short)
	 */
	public void setPosition(Short position) {
		this.position = position;
	}

	/**
	 * Gets the school comment.
	 * 
	 * 
	 * @return the school comment
	 */
	public String getSchoolComment() {
		return this.schoolComment;
	}

	/**
	 * Sets the school comment.
	 * 
	 * @param schoolComment
	 *            the new school comment
	 */
	public void setSchoolComment(String schoolComment) {
		this.schoolComment = schoolComment;
	}

	/**
	 * Gets the answer details.
	 * 
	 * 
	 * @return the answer details
	 */
	public Set<AnswerDetail> getAnswerDetails() {
		return answerDetails;
	}

	/**
	 * Sets the answer details.
	 * 
	 * @param answerDetails
	 *            the new answer details
	 */
	public void setAnswerDetails(Set<AnswerDetail> answerDetails) {
		this.answerDetails = answerDetails;
	}

	/**
	 * Gets the answer detail feedbacks.
	 * 
	 * @return the answer detail feedbacks
	 */
	public Set<AnswerDetailFeedback> getAnswerDetailFeedbacks() {
		return answerDetailFeedbacks;
	}

	/**
	 * Sets the answer detail feedbacks.
	 * 
	 * @param answerDetailFeedbacks
	 *            the new answer detail feedbacks
	 */
	public void setAnswerDetailFeedbacks(Set<AnswerDetailFeedback> answerDetailFeedbacks) {
		this.answerDetailFeedbacks = answerDetailFeedbacks;
	}

	/**
	 * Gets the answer detail objs.
	 * 
	 * @return the answer detail objs
	 */
	public Set<AnswerDetailObj> getAnswerDetailObjs() {
		return answerDetailObjs;
	}

	/**
	 * Sets the answer detail objs.
	 * 
	 * @param answerDetailObjs
	 *            the new answer detail objs
	 */
	public void setAnswerDetailObjs(Set<AnswerDetailObj> answerDetailObjs) {
		this.answerDetailObjs = answerDetailObjs;
	}

	/**
	 * Gets the testset code.
	 *
	 * @return the testset code
	 */
	public String getTestsetCode() {
		return testsetCode;
	}

	/**
	 * Sets the testset code.
	 *
	 * @param testsetCode
	 *            the new testset code
	 */
	public void setTestsetCode(String testsetCode) {
		this.testsetCode = testsetCode;
	}

}