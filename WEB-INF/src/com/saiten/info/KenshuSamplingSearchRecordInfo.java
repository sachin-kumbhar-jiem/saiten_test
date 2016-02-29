package com.saiten.info;

import java.io.Serializable;

public class KenshuSamplingSearchRecordInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int gradeNum;
	private double ratio;
	private int checkedRecordNumber;
	private int totalNumber;
	public int getGradeNum() {
		return gradeNum;
	}
	public void setGradeNum(int gradeNum) {
		this.gradeNum = gradeNum;
	}
	public double getRatio() {
		return ratio;
	}
	public void setRatio(double ratio) {
		this.ratio = ratio;
	}
	public int getCheckedRecordNumber() {
		return checkedRecordNumber;
	}
	public void setCheckedRecordNumber(int checkedRecordNumber) {
		this.checkedRecordNumber = checkedRecordNumber;
	}
	public int getTotalNumber() {
		return totalNumber;
	}
	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}
	
	

}
