package com.saiten.info;

/**
 * @author sachin
 * @version 1.0
 * @created 11-Dec-2012 12:40:12 PM
 */
public class SpecialScoreInputInfo {
	private String subjectCode;
	private Short questionNum;
	private String answerFormNum;
	private Short specialScoringType;

	/**
	 * @return the subjectCode
	 */
	public String getSubjectCode() {
		return subjectCode;
	}

	/**
	 * @param subjectCode
	 *            the subjectCode to set
	 */
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	/**
	 * @return the questionNum
	 */
	public Short getQuestionNum() {
		return questionNum;
	}

	/**
	 * @param questionNum
	 *            the questionNum to set
	 */
	public void setQuestionNum(Short questionNum) {
		this.questionNum = questionNum;
	}

	/**
	 * @return the answerFormNum
	 */
	public String getAnswerFormNum() {
		return answerFormNum;
	}

	/**
	 * @param answerFormNum
	 *            the answerFormNum to set
	 */
	public void setAnswerFormNum(String answerFormNum) {
		this.answerFormNum = answerFormNum;
	}

	/**
	 * @return the specialScoringType
	 */
	public Short getSpecialScoringType() {
		return specialScoringType;
	}

	/**
	 * @param specialScoringType
	 *            the specialScoringType to set
	 */
	public void setSpecialScoringType(Short specialScoringType) {
		this.specialScoringType = specialScoringType;
	}

	@Override
	public String toString() {
		StringBuilder data = new StringBuilder();
		data.append("{ Subject Code: " + subjectCode + ", Question No.: "
				+ questionNum + ", AnswerFormNum: " + answerFormNum
				+ ", SpecialScoringType: " + specialScoringType + "}");
		data.append("}");
		return data.toString();
	}
}
