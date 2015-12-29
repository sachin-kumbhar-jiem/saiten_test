package com.saiten.info;

import java.util.List;
import java.util.Map;

/**
 * @author sachin
 * @version 1.0
 * @created 07-Dec-2012 3:25:28 PM
 */
public class QuestionInfo {

	private String answerScoreHistoryTable;
	private String answerScoreTable;
	private String connectionString;
	private int historyRecordCount;
	private String menuId;
	private int nextRecordCount;
	private int prevRecordCount;
	private int questionSeq;
	private String subjectShortName;
	private String subjectName;
	private List<CheckPointInfo> checkPointList;
	private Map<Integer, String> pendingCategoryGroupMap;
	private String scorerComment;
	private String manualDocument;
	private String questionFileName;
	private String subjectCode;
	private Short questionNum;
	private String side;
	private Character questionType;
	private Character scoreType;
	private Map<Integer,String>denyCategoryGroupMap;

	public Map<Integer, String> getPendingCategoryGroupMap() {
		return pendingCategoryGroupMap;
	}

	/**
	 * 
	 * @param pendingCategoryGroupMap
	 */
	public void setPendingCategoryGroupMap(
			Map<Integer, String> pendingCategoryGroupMap) {
		this.pendingCategoryGroupMap = pendingCategoryGroupMap;
	}

	public int getNextRecordCount() {
		return nextRecordCount;
	}

	/**
	 * 
	 * @param nextRecordCount
	 */
	public void setNextRecordCount(int nextRecordCount) {
		this.nextRecordCount = nextRecordCount;
	}

	public int getPrevRecordCount() {
		return prevRecordCount;
	}

	/**
	 * 
	 * @param prevRecordCount
	 */
	public void setPrevRecordCount(int prevRecordCount) {
		this.prevRecordCount = prevRecordCount;
	}

	public String getQuestionFileName() {
		return questionFileName;
	}

	/**
	 * 
	 * @param questionFileName
	 */
	public void setQuestionFileName(String questionFileName) {
		this.questionFileName = questionFileName;
	}

	public String getManualDocument() {
		return manualDocument;
	}

	/**
	 * 
	 * @param manualDocument
	 */
	public void setManualDocument(String manualDocument) {
		this.manualDocument = manualDocument;
	}

	public String getScorerComment() {
		return scorerComment;
	}

	/**
	 * 
	 * @param scorerComment
	 */
	public void setScorerComment(String scorerComment) {
		this.scorerComment = scorerComment;
	}

	public List<CheckPointInfo> getCheckPointList() {
		return checkPointList;
	}

	public int getQuestionSeq() {
		return questionSeq;
	}

	public String getAnswerScoreHistoryTable() {
		return answerScoreHistoryTable;
	}

	public String getAnswerScoreTable() {
		return answerScoreTable;
	}

	public String getConnectionString() {
		return connectionString;
	}

	public int getHistoryRecordCount() {
		return historyRecordCount;
	}

	public String getMenuId() {
		return menuId;
	}

	public String getSubjectShortName() {
		return subjectShortName;
	}

	/**
	 * 
	 * @param answerScoreHistoryTable
	 */
	public void setAnswerScoreHistoryTable(String answerScoreHistoryTable) {
		this.answerScoreHistoryTable = answerScoreHistoryTable;
	}

	/**
	 * 
	 * @param answerScoreTable
	 */
	public void setAnswerScoreTable(String answerScoreTable) {
		this.answerScoreTable = answerScoreTable;
	}

	/**
	 * 
	 * @param connectionString
	 */
	public void setConnectionString(String connectionString) {
		this.connectionString = connectionString;
	}

	/**
	 * 
	 * @param historyRecordCount
	 */
	public void setHistoryRecordCount(int historyRecordCount) {
		this.historyRecordCount = historyRecordCount;
	}

	/**
	 * 
	 * @param menuId
	 */
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	/**
	 * 
	 * @param subjectShortName
	 */
	public void setSubjectShortName(String subjectShortName) {
		this.subjectShortName = subjectShortName;
	}

	/**
	 * 
	 * @param questionSeq
	 */
	public void setQuestionSeq(int questionSeq) {
		this.questionSeq = questionSeq;
	}

	/**
	 * 
	 * @param checkPointList
	 */
	public void setCheckPointList(List<CheckPointInfo> checkPointList) {
		this.checkPointList = checkPointList;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	/**
	 * 
	 * @param subjectCode
	 */
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public Short getQuestionNum() {
		return questionNum;
	}

	/**
	 * 
	 * @param questionNum
	 */
	public void setQuestionNum(Short questionNum) {
		this.questionNum = questionNum;
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}

	/**
	 * @return the questionType
	 */
	public Character getQuestionType() {
		return questionType;
	}

	/**
	 * @param questionType
	 *            the questionType to set
	 */
	public void setQuestionType(Character questionType) {
		this.questionType = questionType;
	}

	/**
	 * @return the scoreType
	 */
	public Character getScoreType() {
		return scoreType;
	}

	/**
	 * @param scoreType
	 *            the scoreType to set
	 */
	public void setScoreType(Character scoreType) {
		this.scoreType = scoreType;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	
	public Map<Integer, String> getDenyCategoryGroupMap() {
		return denyCategoryGroupMap;
	}

	public void setDenyCategoryGroupMap(Map<Integer, String> denyCategoryGroupMap) {
		this.denyCategoryGroupMap = denyCategoryGroupMap;
	}
}