package com.saiten.model;

/**
 * AnswerDetail entity.
 * 
 * @author suwarna
 * @version $Revision: 1.0 $
 */

public class AnswerDetail implements java.io.Serializable, Comparable<AnswerDetail> {

	// Fields

	/** The serial version uid */
	private static final long serialVersionUID = 1L;

	/** The answer detail seq. */
	private Integer answerDetailSeq;

	/** The answer info. */
	private AnswerInfo answerInfo;

	/** The question num. */
	private Short questionNum;

	/** The mark value. */
	private String markValue;

	/** The image file name. */
	private String imageFileName;

	/** The move to main status. */
	private Short moveToMainStatus;

	/** The testsetnum seq. */
	private Integer testsetnumSeq;

	/** The question num. */
	private Short testsetNum;

	// Constructors

	/**
	 * default constructor.
	 */
	public AnswerDetail() {
	}

	/**
	 * minimal constructor.
	 * 
	 * @param answerDetailSeq
	 *            the answer detail seq
	 */
	public AnswerDetail(Integer answerDetailSeq) {
		this.answerDetailSeq = answerDetailSeq;
	}

	/**
	 * full constructor.
	 * 
	 * @param answerDetailSeq
	 *            the answer detail seq
	 * @param answerInfo
	 *            the answer info
	 * @param questionNum
	 *            the question num
	 * @param markValue
	 *            the mark value
	 * @param imageFileName
	 *            the image file name
	 * @param moveToMainStatus
	 *            the move to main status
	 */
	public AnswerDetail(Integer answerDetailSeq, AnswerInfo answerInfo, Short questionNum, String markValue,
			String imageFileName, Short moveToMainStatus) {
		this.answerDetailSeq = answerDetailSeq;
		this.answerInfo = answerInfo;
		this.questionNum = questionNum;
		this.markValue = markValue;
		this.imageFileName = imageFileName;
		this.moveToMainStatus = moveToMainStatus;
	}

	// Property accessors

	/**
	 * Gets the answer detail seq.
	 * 
	 * 
	 * @return the answer detail seq
	 */
	public Integer getAnswerDetailSeq() {
		return this.answerDetailSeq;
	}

	/**
	 * Sets the answer detail seq.
	 * 
	 * @param answerDetailSeq
	 *            the new answer detail seq
	 */
	public void setAnswerDetailSeq(Integer answerDetailSeq) {
		this.answerDetailSeq = answerDetailSeq;
	}

	/**
	 * Gets the answer info.
	 * 
	 * 
	 * @return the answer info
	 */
	public AnswerInfo getAnswerInfo() {
		return answerInfo;
	}

	/**
	 * Sets the answer info.
	 * 
	 * @param answerInfo
	 *            the new answer info
	 */
	public void setAnswerInfo(AnswerInfo answerInfo) {
		this.answerInfo = answerInfo;
	}

	/**
	 * Gets the question num.
	 * 
	 * 
	 * @return the question num
	 */
	public Short getQuestionNum() {
		return this.questionNum;
	}

	/**
	 * Sets the question num.
	 * 
	 * @param questionNum
	 *            the new question num
	 */
	public void setQuestionNum(Short questionNum) {
		this.questionNum = questionNum;
	}

	/**
	 * Gets the mark value.
	 * 
	 * 
	 * @return the mark value
	 */
	public String getMarkValue() {
		return this.markValue;
	}

	/**
	 * Sets the mark value.
	 * 
	 * @param markValue
	 *            the new mark value
	 */
	public void setMarkValue(String markValue) {
		this.markValue = markValue;
	}

	/**
	 * Gets the image file name.
	 * 
	 * 
	 * @return the image file name
	 */
	public String getImageFileName() {
		return this.imageFileName;
	}

	/**
	 * Sets the image file name.
	 * 
	 * @param imageFileName
	 *            the new image file name
	 */
	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	/**
	 * Gets the move to main status.
	 * 
	 * 
	 * @return the move to main status
	 */
	public Short getMoveToMainStatus() {
		return this.moveToMainStatus;
	}

	/**
	 * Sets the move to main status.
	 * 
	 * @param moveToMainStatus
	 *            the new move to main status
	 */
	public void setMoveToMainStatus(Short moveToMainStatus) {
		this.moveToMainStatus = moveToMainStatus;
	}

	/**
	 * Gets the testsetnum seq.
	 *
	 * @return the testsetnum seq
	 */
	public Integer getTestsetnumSeq() {
		return testsetnumSeq;
	}

	/**
	 * Sets the testsetnum seq.
	 *
	 * @param testsetnumSeq
	 *            the new testsetnum seq
	 */
	public void setTestsetnumSeq(Integer testsetnumSeq) {
		this.testsetnumSeq = testsetnumSeq;
	}

	/**
	 * Gets the testset num.
	 *
	 * @return the testset num
	 */
	public Short getTestsetNum() {
		return testsetNum;
	}

	/**
	 * Sets the testset num.
	 *
	 * @param testsetNum
	 *            the new testset num
	 */
	public void setTestsetNum(Short testsetNum) {
		this.testsetNum = testsetNum;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	/**
	 * Method compareTo.
	 * 
	 * @param detail
	 *            AnswerDetail
	 * @return int
	 */
	@Override
	public int compareTo(AnswerDetail detail) {
		if (this.questionNum != null && detail.getQuestionNum() != null) {
			return this.questionNum - detail.getQuestionNum();
		} else {
			return -2;
		}
	}

}