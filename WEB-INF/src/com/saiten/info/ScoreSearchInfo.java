package com.saiten.info;

import java.util.Map;

/**
 * @author sachin
 * @version 1.0
 * @created 11-Dec-2012 12:40:12 PM
 */
public class ScoreSearchInfo {
	private Map<String, String> subjectNameList;

	private Map<Integer, String> gradeList;

	private Map<Short, String> historyEventList;

	private Map<Short, String> currentStateList;

	private Map<String, String> yearList;

	private Map<String, String> monthList;

	private Map<String, String> daysList;

	private Map<String, String> hoursList;

	private Map<String, String> minutesList;

	private Map<Integer, String> questionTypeMap;

	private Map<String, String> scorePercentageQuestionTypeMap;

	private Map<String, String> punchTextMap;

	public Map<String, String> getHoursList() {
		return hoursList;
	}

	public void setHoursList(Map<String, String> hoursList) {
		this.hoursList = hoursList;
	}

	public Map<String, String> getMinutesList() {
		return minutesList;
	}

	public void setMinutesList(Map<String, String> minutesList) {
		this.minutesList = minutesList;
	}

	public Map<String, String> getMonthList() {
		return monthList;
	}

	public void setMonthList(Map<String, String> monthList) {
		this.monthList = monthList;
	}

	public Map<String, String> getDaysList() {
		return daysList;
	}

	public void setDaysList(Map<String, String> daysList) {
		this.daysList = daysList;
	}

	public Map<Integer, String> getGradeList() {
		return gradeList;
	}

	public void setGradeList(Map<Integer, String> gradeList) {
		this.gradeList = gradeList;
	}

	public Map<Short, String> getHistoryEventList() {
		return historyEventList;
	}

	public void setHistoryEventList(Map<Short, String> historyEventList) {
		this.historyEventList = historyEventList;
	}

	public Map<Short, String> getCurrentStateList() {
		return currentStateList;
	}

	public void setCurrentStateList(Map<Short, String> currentStateList) {
		this.currentStateList = currentStateList;
	}

	public Map<String, String> getSubjectNameList() {
		return subjectNameList;
	}

	public void setSubjectNameList(Map<String, String> subjectNameList) {
		this.subjectNameList = subjectNameList;
	}

	public Map<String, String> getYearList() {
		return yearList;
	}

	public void setYearList(Map<String, String> yearList) {
		this.yearList = yearList;
	}

	/**
	 * @return the questionTypeMap
	 */
	public Map<Integer, String> getQuestionTypeMap() {
		return questionTypeMap;
	}

	/**
	 * @param questionTypeMap the questionTypeMap to set
	 */
	public void setQuestionTypeMap(Map<Integer, String> questionTypeMap) {
		this.questionTypeMap = questionTypeMap;
	}

	public Map<String, String> getPunchTextMap() {
		return punchTextMap;
	}

	public void setPunchTextMap(Map<String, String> punchTextMap) {
		this.punchTextMap = punchTextMap;
	}

	public Map<String, String> getScorePercentageQuestionTypeMap() {
		return scorePercentageQuestionTypeMap;
	}

	public void setScorePercentageQuestionTypeMap(Map<String, String> scorePercentageQuestionTypeMap) {
		this.scorePercentageQuestionTypeMap = scorePercentageQuestionTypeMap;
	}
	
	

}
