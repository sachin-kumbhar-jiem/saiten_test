package com.saiten.info;

/**
 * @author sachin
 * @version 1.0
 * @created 13-Dec-2012 11:53:02 AM
 */
public class GradeInfo {

	private String gradeNum;
	private Integer gradeSeq;
	private Character result;

	public String getGradeNum() {
		return gradeNum;
	}

	public Integer getGradeSeq() {
		return gradeSeq;
	}

	public Character getResult() {
		return result;
	}

	/**
	 * 
	 * @param gradeNum
	 */
	public void setGradeNum(String gradeNum) {
		this.gradeNum = gradeNum;
	}

	/**
	 * 
	 * @param gradeSeq
	 */
	public void setGradeSeq(Integer gradeSeq) {
		this.gradeSeq = gradeSeq;
	}

	/**
	 * 
	 * @param result
	 */
	public void setResult(Character result) {
		this.result = result;
	}

}