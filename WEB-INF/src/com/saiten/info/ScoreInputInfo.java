package com.saiten.info;

/**
 * @author sachin
 * @version 1.0
 * @created 11-Dec-2012 12:40:12 PM
 */
public class ScoreInputInfo {
	private boolean samplingFlag;
	private String subjectCode;
	private Short questionNum;
	private String answerFormNum;
	private Integer resultCount;
	private ScoreHistoryInfo scoreHistoryInfo;
	private ScoreCurrentInfo scoreCurrentInfo;

	public ScoreHistoryInfo getScoreHistoryInfo() {
		return scoreHistoryInfo;
	}

	public void setScoreHistoryInfo(ScoreHistoryInfo scoreHistoryInfo) {
		this.scoreHistoryInfo = scoreHistoryInfo;
	}

	public ScoreCurrentInfo getScoreCurrentInfo() {
		return scoreCurrentInfo;
	}

	public void setScoreCurrentInfo(ScoreCurrentInfo scoreCurrentInfo) {
		this.scoreCurrentInfo = scoreCurrentInfo;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public Short getQuestionNum() {
		return questionNum;
	}

	public void setQuestionNum(Short questionNum) {
		this.questionNum = questionNum;
	}

	public String getAnswerFormNum() {
		return answerFormNum;
	}

	public void setAnswerFormNum(String answerFormNum) {
		this.answerFormNum = answerFormNum;
	}

	public Integer getResultCount() {
		return resultCount;
	}

	public void setResultCount(Integer resultCount) {
		this.resultCount = resultCount;
	}

	public boolean isSamplingFlag() {
		return samplingFlag;
	}

	public void setSamplingFlag(boolean samplingFlag) {
		this.samplingFlag = samplingFlag;
	}

	@Override
	public String toString() {
		StringBuilder data = new StringBuilder();
		data.append("{ Subject Code: " + subjectCode + ", Question No.: "
				+ questionNum + ", AnswerFormNum: " + answerFormNum
				+ ", Result Count: " + resultCount + ", SamplingFlag: "
				+ samplingFlag);
		data.append("\n");
		data.append("Current Data: " + scoreCurrentInfo);
		data.append("\n");
		data.append("History Data: " + scoreHistoryInfo);
		data.append("}");
		return data.toString();
	}

}
