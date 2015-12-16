package com.saiten.info;

import java.util.Date;

public class FindAnswerInfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int answerSeq;
	private String answerFormNum;
	private String imageFileName;
	private Integer gradeSeq;
	private Double bitValue;
	private Integer questionSeq;
	private Date updateDate;
	private String markValue;
	private String latestScreenScorerId;
	private String secondLatestScreenScorerId;

	public int getAnswerSeq() {
		return answerSeq;
	}

	public void setAnswerSeq(int answerSeq) {
		this.answerSeq = answerSeq;
	}

	public String getAnswerFormNum() {
		return answerFormNum;
	}

	public void setAnswerFormNum(String answerFormNum) {
		this.answerFormNum = answerFormNum;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public Integer getGradeSeq() {
		return gradeSeq;
	}

	public void setGradeSeq(Integer gradeSeq) {
		this.gradeSeq = gradeSeq;
	}

	public Double getBitValue() {
		return bitValue;
	}

	public void setBitValue(Double bitValue) {
		this.bitValue = bitValue;
	}

	public Integer getQuestionSeq() {
		return questionSeq;
	}

	public void setQuestionSeq(Integer questionSeq) {
		this.questionSeq = questionSeq;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return the markValue
	 */
	public String getMarkValue() {
		return markValue;
	}

	/**
	 * @param markValue
	 *            the markValue to set
	 */
	public void setMarkValue(String markValue) {
		this.markValue = markValue;
	}

	public String getLatestScreenScorerId() {
		return latestScreenScorerId;
	}

	public void setLatestScreenScorerId(String latestScreenScorerId) {
		this.latestScreenScorerId = latestScreenScorerId;
	}

	public String getSecondLatestScreenScorerId() {
		return secondLatestScreenScorerId;
	}

	public void setSecondLatestScreenScorerId(String secondLatestScreenScorerId) {
		this.secondLatestScreenScorerId = secondLatestScreenScorerId;
	}



}
