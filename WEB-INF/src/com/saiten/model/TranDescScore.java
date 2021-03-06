package com.saiten.model;

// Generated Feb 19, 2015 2:30:02 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * TranDescScore generated by hbm2java
 */
@SuppressWarnings("serial")
public class TranDescScore implements java.io.Serializable {

	private Integer answerSeq;
	private String answerFormNum;
	private Integer questionSeq;
	private String latestScorerId;
	private Short latestScoringState;
	private Integer gradeSeq;
	private Integer gradeNum;
	private Double bitValue;
	private Integer pendingCategorySeq;
	private Short pendingCategory;
	private Character lockFlag;
	private String punchText;
	private String imageFileName;
	private Character inspectFlag;
	private Character samplingFlag;
	private Character answerPaperType;
	private String lockBy;
	private Character validFlag;
	private Date updateDate;
	private Date createDate;
	private String latestScreenScorerId;
	private Character qualityCheckFlag;
	private Character neighbourMatchFlag;
	private String markValue;
	private String secondLatestScreenScorerId;
	private String thirdLatestScreenScorerId;
	private Integer testsetnumSeq;
	private Integer denyCategorySeq;
	private Short denyCategory;
	private Integer inspectionGroupSeq;
	private Character kenshuSamplingFlag;

	public TranDescScore() {
	}

	public TranDescScore(String answerFormNum, Integer questionSeq,
			String latestScorerId, Short latestScoringState, Integer gradeSeq,
			Integer gradeNum, Double bitValue, Integer pendingCategorySeq,
			Short pendingCategory, Character lockFlag, String punchText,
			String imageFileName, Character inspectFlag,
			Character samplingFlag, Character answerPaperType, String lockBy,
			Character validFlag, Date updateDate, Date createDate,
			String latestScreenScorerId, Character qualityCheckFlag,
			Character neighbourMatchFlag, String markValue,
			String secondLatestScreenScorerId,
			String thirdLatestScreenScorerId, Integer testsetnumSeq,
			Integer denyCategorySeq, Short denyCategory, Integer inspectionGroupSeq,Character kenshuSamplingFlag) {
		this.answerFormNum = answerFormNum;
		this.questionSeq = questionSeq;
		this.latestScorerId = latestScorerId;
		this.latestScoringState = latestScoringState;
		this.gradeSeq = gradeSeq;
		this.gradeNum = gradeNum;
		this.bitValue = bitValue;
		this.pendingCategorySeq = pendingCategorySeq;
		this.pendingCategory = pendingCategory;
		this.lockFlag = lockFlag;
		this.punchText = punchText;
		this.imageFileName = imageFileName;
		this.inspectFlag = inspectFlag;
		this.samplingFlag = samplingFlag;
		this.answerPaperType = answerPaperType;
		this.lockBy = lockBy;
		this.validFlag = validFlag;
		this.updateDate = updateDate;
		this.createDate = createDate;
		this.latestScreenScorerId = latestScreenScorerId;
		this.qualityCheckFlag = qualityCheckFlag;
		this.neighbourMatchFlag = neighbourMatchFlag;
		this.markValue = markValue;
		this.secondLatestScreenScorerId = secondLatestScreenScorerId;
		this.thirdLatestScreenScorerId = thirdLatestScreenScorerId;
		this.testsetnumSeq = testsetnumSeq;
		this.denyCategorySeq = denyCategorySeq;
		this.denyCategory = denyCategory;
		this.inspectionGroupSeq = inspectionGroupSeq;
		this.kenshuSamplingFlag = kenshuSamplingFlag;
	}

	public Integer getAnswerSeq() {
		return this.answerSeq;
	}

	public void setAnswerSeq(Integer answerSeq) {
		this.answerSeq = answerSeq;
	}

	public String getAnswerFormNum() {
		return this.answerFormNum;
	}

	public void setAnswerFormNum(String answerFormNum) {
		this.answerFormNum = answerFormNum;
	}

	public Integer getQuestionSeq() {
		return this.questionSeq;
	}

	public void setQuestionSeq(Integer questionSeq) {
		this.questionSeq = questionSeq;
	}

	public String getLatestScorerId() {
		return this.latestScorerId;
	}

	public void setLatestScorerId(String latestScorerId) {
		this.latestScorerId = latestScorerId;
	}

	public Short getLatestScoringState() {
		return this.latestScoringState;
	}

	public void setLatestScoringState(Short latestScoringState) {
		this.latestScoringState = latestScoringState;
	}

	public Integer getGradeSeq() {
		return this.gradeSeq;
	}

	public void setGradeSeq(Integer gradeSeq) {
		this.gradeSeq = gradeSeq;
	}

	public Integer getGradeNum() {
		return this.gradeNum;
	}

	public void setGradeNum(Integer gradeNum) {
		this.gradeNum = gradeNum;
	}

	public Double getBitValue() {
		return this.bitValue;
	}

	public void setBitValue(Double bitValue) {
		this.bitValue = bitValue;
	}

	public Integer getPendingCategorySeq() {
		return this.pendingCategorySeq;
	}

	public void setPendingCategorySeq(Integer pendingCategorySeq) {
		this.pendingCategorySeq = pendingCategorySeq;
	}

	public Short getPendingCategory() {
		return this.pendingCategory;
	}

	public void setPendingCategory(Short pendingCategory) {
		this.pendingCategory = pendingCategory;
	}

	public Character getLockFlag() {
		return this.lockFlag;
	}

	public void setLockFlag(Character lockFlag) {
		this.lockFlag = lockFlag;
	}

	public String getPunchText() {
		return this.punchText;
	}

	public void setPunchText(String punchText) {
		this.punchText = punchText;
	}

	public String getImageFileName() {
		return this.imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public Character getInspectFlag() {
		return this.inspectFlag;
	}

	public void setInspectFlag(Character inspectFlag) {
		this.inspectFlag = inspectFlag;
	}

	public Character getSamplingFlag() {
		return this.samplingFlag;
	}

	public void setSamplingFlag(Character samplingFlag) {
		this.samplingFlag = samplingFlag;
	}

	public Character getAnswerPaperType() {
		return this.answerPaperType;
	}

	public void setAnswerPaperType(Character answerPaperType) {
		this.answerPaperType = answerPaperType;
	}

	public String getLockBy() {
		return this.lockBy;
	}

	public void setLockBy(String lockBy) {
		this.lockBy = lockBy;
	}

	public Character getValidFlag() {
		return this.validFlag;
	}

	public void setValidFlag(Character validFlag) {
		this.validFlag = validFlag;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getLatestScreenScorerId() {
		return this.latestScreenScorerId;
	}

	public void setLatestScreenScorerId(String latestScreenScorerId) {
		this.latestScreenScorerId = latestScreenScorerId;
	}

	public Character getQualityCheckFlag() {
		return this.qualityCheckFlag;
	}

	public void setQualityCheckFlag(Character qualityCheckFlag) {
		this.qualityCheckFlag = qualityCheckFlag;
	}

	public Character getNeighbourMatchFlag() {
		return this.neighbourMatchFlag;
	}

	public void setNeighbourMatchFlag(Character neighbourMatchFlag) {
		this.neighbourMatchFlag = neighbourMatchFlag;
	}

	public String getMarkValue() {
		return this.markValue;
	}

	public void setMarkValue(String markValue) {
		this.markValue = markValue;
	}

	public String getSecondLatestScreenScorerId() {
		return secondLatestScreenScorerId;
	}

	public void setSecondLatestScreenScorerId(String secondLatestScreenScorerId) {
		this.secondLatestScreenScorerId = secondLatestScreenScorerId;
	}

	public String getThirdLatestScreenScorerId() {
		return thirdLatestScreenScorerId;
	}

	public void setThirdLatestScreenScorerId(String thirdLatestScreenScorerId) {
		this.thirdLatestScreenScorerId = thirdLatestScreenScorerId;
	}

	public Integer getTestsetnumSeq() {
		return testsetnumSeq;
	}

	public void setTestsetnumSeq(Integer testsetnumSeq) {
		this.testsetnumSeq = testsetnumSeq;
	}

	public Integer getDenyCategorySeq() {
		return denyCategorySeq;
	}

	public void setDenyCategorySeq(Integer denyCategorySeq) {
		this.denyCategorySeq = denyCategorySeq;
	}

	public Short getDenyCategory() {
		return denyCategory;
	}

	public void setDenyCategory(Short denyCategory) {
		this.denyCategory = denyCategory;
	}
	
	public Integer getInspectionGroupSeq() {
		return inspectionGroupSeq;
	}

	public void setInspectionGroupSeq(Integer inspectionGroupSeq) {
		this.inspectionGroupSeq = inspectionGroupSeq;
	}
	
	public Character getKenshuSamplingFlag() {
		return kenshuSamplingFlag;
	}

	public void setKenshuSamplingFlag(Character kenshuSamplingFlag) {
		this.kenshuSamplingFlag = kenshuSamplingFlag;
	}
}
