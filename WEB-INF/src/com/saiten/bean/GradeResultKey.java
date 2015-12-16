package com.saiten.bean;

/**
 * @author sachin
 * @version 1.0
 * @created 24-Dec-2012 12:22:57 PM
 */
public class GradeResultKey {
	private double bitValue;

	private int questionSeq;

	public double getBitValue() {
		return bitValue;
	}

	/**
	 * 
	 * @param bitValue
	 */
	public void setBitValue(double bitValue) {
		this.bitValue = bitValue;
	}

	public int getQuestionSeq() {
		return questionSeq;
	}

	/**
	 * 
	 * @param questionSeq
	 */
	public void setQuestionSeq(int questionSeq) {
		this.questionSeq = questionSeq;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof GradeResultKey))
			return false;
		GradeResultKey castOther = (GradeResultKey) other;

		return (this.getQuestionSeq() == castOther.getQuestionSeq())
				&& (this.getBitValue() == castOther.getBitValue());
	}

	@Override
	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getQuestionSeq();
		result = (int)(37 * result + this.getBitValue());
		return result;
	}

}
