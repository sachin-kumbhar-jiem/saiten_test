package com.saiten.model;

import java.util.Date;

public class MstTestsetnumQuestion implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	/** The testsetnum seq. */
	private Integer testsetnumSeq;
	
	/** The testset code. */
	private String testsetCode;
	
	/** The testset num. */
	private Short testsetNum;
	
	/** The question seq. */
	private Integer questionSeq;
	
	/** The delete flag. */
	private Character deleteFlag;
	
	/** The update person id. */
	private String updatePersonId;
	
	/** The update date. */
	private Date updateDate;
	
	/** The create date. */
	private Date createDate;
	
	/** The side. */
	private Character side;

	/**
	 * Instantiates a new mst testsetnum question.
	 */
	public MstTestsetnumQuestion() {
	}

	/**
	 * Instantiates a new mst testsetnum question.
	 *
	 * @param testsetCode the testset code
	 * @param testsetNum the testset num
	 * @param questionSeq the question seq
	 * @param deleteFlag the delete flag
	 * @param updatePersonId the update person id
	 * @param updateDate the update date
	 * @param createDate the create date
	 */
	public MstTestsetnumQuestion(String testsetCode, Short testsetNum,
			Integer questionSeq, Character deleteFlag, String updatePersonId,
			Date updateDate, Date createDate,Character side) {
		this.testsetCode = testsetCode;
		this.testsetNum = testsetNum;
		this.questionSeq = questionSeq;
		this.deleteFlag = deleteFlag;
		this.updatePersonId = updatePersonId;
		this.updateDate = updateDate;
		this.createDate = createDate;
		this.side = side;
	}

	/**
	 * Gets the testsetnum seq.
	 *
	 * @return the testsetnum seq
	 */
	public Integer getTestsetnumSeq() {
		return this.testsetnumSeq;
	}

	/**
	 * Sets the testsetnum seq.
	 *
	 * @param testsetnumSeq the new testsetnum seq
	 */
	public void setTestsetnumSeq(Integer testsetnumSeq) {
		this.testsetnumSeq = testsetnumSeq;
	}

	/**
	 * Gets the testset code.
	 *
	 * @return the testset code
	 */
	public String getTestsetCode() {
		return this.testsetCode;
	}

	/**
	 * Sets the testset code.
	 *
	 * @param testsetCode the new testset code
	 */
	public void setTestsetCode(String testsetCode) {
		this.testsetCode = testsetCode;
	}

	/**
	 * Gets the testset num.
	 *
	 * @return the testset num
	 */
	public Short getTestsetNum() {
		return this.testsetNum;
	}

	/**
	 * Sets the testset num.
	 *
	 * @param testsetNum the new testset num
	 */
	public void setTestsetNum(Short testsetNum) {
		this.testsetNum = testsetNum;
	}

	/**
	 * Gets the question seq.
	 *
	 * @return the question seq
	 */
	public Integer getQuestionSeq() {
		return this.questionSeq;
	}

	/**
	 * Sets the question seq.
	 *
	 * @param questionSeq the new question seq
	 */
	public void setQuestionSeq(Integer questionSeq) {
		this.questionSeq = questionSeq;
	}

	/**
	 * Gets the delete flag.
	 *
	 * @return the delete flag
	 */
	public Character getDeleteFlag() {
		return this.deleteFlag;
	}

	/**
	 * Sets the delete flag.
	 *
	 * @param deleteFlag the new delete flag
	 */
	public void setDeleteFlag(Character deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	/**
	 * Gets the update person id.
	 *
	 * @return the update person id
	 */
	public String getUpdatePersonId() {
		return this.updatePersonId;
	}

	/**
	 * Sets the update person id.
	 *
	 * @param updatePersonId the new update person id
	 */
	public void setUpdatePersonId(String updatePersonId) {
		this.updatePersonId = updatePersonId;
	}

	/**
	 * Gets the update date.
	 *
	 * @return the update date
	 */
	public Date getUpdateDate() {
		return this.updateDate;
	}

	/**
	 * Sets the update date.
	 *
	 * @param updateDate the new update date
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * Gets the creates the date.
	 *
	 * @return the creates the date
	 */
	public Date getCreateDate() {
		return this.createDate;
	}

	/**
	 * Sets the creates the date.
	 *
	 * @param createDate the new creates the date
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * Gets the side.
	 *
	 * @return the side
	 */
	public Character getSide() {
		return side;
	}

	/**
	 * Sets the side.
	 *
	 * @param side the new side
	 */
	public void setSide(Character side) {
		this.side = side;
	}
	
	

}
