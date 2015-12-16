package com.saiten.info;

import java.util.List;

/**
 * @author sachin
 * @version 1.0
 * @created 11-Dec-2012 12:41:32 PM
 */
public class TranDescScoreInfo {

	private String answerFormNumber;
	private AnswerInfo answerInfo;
	private String helpDocument;
	private String imageFileName;
	private Integer gradeSeq;
	private Short scoringState;
	private String latestScreenScorerId;
	private List<Short> markValueList;
	private Integer gradeNum;
	private Short pendingCategory;
	private Integer lookAfterwardsCount;
	private String lookAfterwardsComments;

	public String getAnswerFormNumber() {
		return answerFormNumber;
	}

	public AnswerInfo getAnswerInfo() {
		return answerInfo;
	}

	public String getHelpDocument() {
		return helpDocument;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	/**
	 * 
	 * @param answerFormNumber
	 */
	public void setAnswerFormNumber(String answerFormNumber) {
		this.answerFormNumber = answerFormNumber;
	}

	/**
	 * 
	 * @param answerInfo
	 */
	public void setAnswerInfo(AnswerInfo answerInfo) {
		this.answerInfo = answerInfo;
	}

	/**
	 * 
	 * @param helpDocument
	 */
	public void setHelpDocument(String helpDocument) {
		this.helpDocument = helpDocument;
	}

	/**
	 * 
	 * @param imageFileName
	 */
	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public Integer getGradeSeq() {
		return gradeSeq;
	}

	/**
	 * 
	 * @param gradeSeq
	 */
	public void setGradeSeq(Integer gradeSeq) {
		this.gradeSeq = gradeSeq;
	}

	/**
	 * @return the scoringState
	 */
	public Short getScoringState() {
		return scoringState;
	}

	/**
	 * @param scoringState
	 *            the scoringState to set
	 */
	public void setScoringState(Short scoringState) {
		this.scoringState = scoringState;
	}

	public String getLatestScreenScorerId() {
		return latestScreenScorerId;
	}

	/**
	 * @param latestScreenScorerId
	 */
	public void setLatestScreenScorerId(String latestScreenScorerId) {
		this.latestScreenScorerId = latestScreenScorerId;
	}

	/**
	 * @return the markValueList
	 */
	public List<Short> getMarkValueList() {
		return markValueList;
	}

	/**
	 * @param markValueList
	 *            the markValueList to set
	 */
	public void setMarkValueList(List<Short> markValueList) {
		this.markValueList = markValueList;
	}

	/**
	 * @return gradeNum
	 */
	public Integer getGradeNum() {
		return gradeNum;
	}

	/**
	 * @param gradeNum
	 */
	public void setGradeNum(Integer gradeNum) {
		this.gradeNum = gradeNum;
	}

	/**
	 * @return pendingCategory
	 */
	public Short getPendingCategory() {
		return pendingCategory;
	}

	/**
	 * @param pendingCategory
	 */
	public void setPendingCategory(Short pendingCategory) {
		this.pendingCategory = pendingCategory;
	}

	/**
	 * @return the lookAfterwardsCount
	 */
	public Integer getLookAfterwardsCount() {
		return lookAfterwardsCount;
	}

	/**
	 * @param lookAfterwardsCount
	 *            the lookAfterwardsCount to set
	 */
	public void setLookAfterwardsCount(Integer lookAfterwardsCount) {
		this.lookAfterwardsCount = lookAfterwardsCount;
	}

	/**
	 * @return the lookAfterwardsComments
	 */
	public String getLookAfterwardsComments() {
		return lookAfterwardsComments;
	}

	/**
	 * @param lookAfterwardsComments
	 *            the lookAfterwardsComments to set
	 */
	public void setLookAfterwardsComments(String lookAfterwardsComments) {
		this.lookAfterwardsComments = lookAfterwardsComments;
	}
	
	@Override
	public String toString() {
		StringBuilder data = new StringBuilder();
		data.append("{ AnswerFormNumber: "+answerFormNumber+", HelpDocument: "+helpDocument+", ImageFileName: "+imageFileName+", GradeSequence: "+gradeSeq+", ScoringState: "+scoringState+", LatestScreenScorerId: "+latestScreenScorerId+", MarkValueList: "+markValueList+", GradeNum: "+gradeNum+", PendingCategory: "+pendingCategory+", LookAfterwardsCount: "+lookAfterwardsCount+", LookAfterwardsComments: "+lookAfterwardsComments);
		data.append("\n");
		data.append("AnswerInfo: "+answerInfo);
		data.append("}");
		return data.toString();
	}

}