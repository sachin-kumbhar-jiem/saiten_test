package com.saiten.info;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author sachin
 * @version 1.0
 * @created 11-Dec-2012 12:40:12 PM
 */
public class ScoreCurrentInfo {
	private boolean currentBlock;
	private String currentScorerId1;
	private String currentScorerId2;
	private String currentScorerId3;
	private String currentScorerId4;
	private String currentScorerId5;
	private String punchTextData;
	private List<String> currentScorerIdList;
	private Integer currentCategoryType;
	private Integer[] currentGradeNum;
	@SuppressWarnings("rawtypes")
	private List currentGradeSeqList;
	private String currentPendingCategory;
	@SuppressWarnings("rawtypes")
	private List currentPendingCategorySeqList;
	private String currentDenyCategory;
	@SuppressWarnings("rawtypes")
	private List currentDenyCategorySeqList;
	private String currentIncludeCheckPoints;
	private Integer currentIncludeBitValue;
	private String currentExcludeCheckPoints;
	private Integer currentExcludeBitValue;
	private Short[] currentStateList;
	private Integer currentUpdateDateStartYear;
	private Integer currentUpdateDateStartMonth;
	private Integer currentUpdateDateStartDay;
	private Integer currentUpdateDateStartHours;
	private Integer currentUpdateDateStartMin;
	private Integer currentUpdateDateEndYear;
	private boolean currentQualityCheckFlag;
	private Integer currentUpdateDateEndMonth;
	private Integer currentUpdateDateEndDay;
	private Integer currentUpdateDateEndHours;
	private Integer currentUpdateDateEndMin;
	private Date currentUpdateStartDate;
	private String punchText;
	private boolean lookAfterwardsFlag;
	private Integer[] currentScorerRole;
	private String currentSkpConditions;

	public Integer getCurrentUpdateDateEndYear() {
		return currentUpdateDateEndYear;
	}

	public void setCurrentUpdateDateEndYear(Integer currentUpdateDateEndYear) {
		this.currentUpdateDateEndYear = currentUpdateDateEndYear;
	}

	@SuppressWarnings("rawtypes")
	public List getCurrentGradeSeqList() {
		return currentGradeSeqList;
	}

	@SuppressWarnings("rawtypes")
	public void setCurrentGradeSeqList(List currentGradeSeqList) {
		this.currentGradeSeqList = currentGradeSeqList;
	}

	@SuppressWarnings("rawtypes")
	public List getCurrentPendingCategorySeqList() {
		return currentPendingCategorySeqList;
	}

	@SuppressWarnings("rawtypes")
	public void setCurrentPendingCategorySeqList(
			List currentPendingCategorySeqList) {
		this.currentPendingCategorySeqList = currentPendingCategorySeqList;
	}

	public Integer getCurrentIncludeBitValue() {
		return currentIncludeBitValue;
	}

	public void setCurrentIncludeBitValue(Integer currentIncludeBitValue) {
		this.currentIncludeBitValue = currentIncludeBitValue;
	}

	public Integer getCurrentExcludeBitValue() {
		return currentExcludeBitValue;
	}

	public void setCurrentExcludeBitValue(Integer currentExcludeBitValue) {
		this.currentExcludeBitValue = currentExcludeBitValue;
	}

	public List<String> getCurrentScorerIdList() {
		return currentScorerIdList;
	}

	public void setCurrentScorerIdList(List<String> currentScorerIdList) {
		this.currentScorerIdList = currentScorerIdList;
	}

	public Date getCurrentUpdateStartDate() {
		return currentUpdateStartDate;
	}

	public void setCurrentUpdateStartDate(Date currentUpdateStartDate) {
		this.currentUpdateStartDate = currentUpdateStartDate;
	}

	public Date getCurrentUpdateEndDate() {
		return currentUpdateEndDate;
	}

	public void setCurrentUpdateEndDate(Date currentUpdateEndDate) {
		this.currentUpdateEndDate = currentUpdateEndDate;
	}

	private Date currentUpdateEndDate;

	public Integer getCurrentUpdateDateEndMonth() {
		return currentUpdateDateEndMonth;
	}

	public void setCurrentUpdateDateEndMonth(Integer currentUpdateDateEndMonth) {
		this.currentUpdateDateEndMonth = currentUpdateDateEndMonth;
	}

	public Integer getCurrentUpdateDateEndDay() {
		return currentUpdateDateEndDay;
	}

	public void setCurrentUpdateDateEndDay(Integer currentUpdateDateEndDay) {
		this.currentUpdateDateEndDay = currentUpdateDateEndDay;
	}

	public Integer getCurrentUpdateDateEndHours() {
		return currentUpdateDateEndHours;
	}

	public void setCurrentUpdateDateEndHours(Integer currentUpdateDateEndHours) {
		this.currentUpdateDateEndHours = currentUpdateDateEndHours;
	}

	public Integer getCurrentUpdateDateEndMin() {
		return currentUpdateDateEndMin;
	}

	public void setCurrentUpdateDateEndMin(Integer currentUpdateDateEndMin) {
		this.currentUpdateDateEndMin = currentUpdateDateEndMin;
	}

	public boolean isCurrentBlock() {
		return currentBlock;
	}

	public void setCurrentBlock(boolean currentBlock) {
		this.currentBlock = currentBlock;
	}

	public String getCurrentScorerId1() {
		return currentScorerId1;
	}

	public void setCurrentScorerId1(String currentScorerId1) {
		this.currentScorerId1 = currentScorerId1;
	}

	public String getCurrentScorerId2() {
		return currentScorerId2;
	}

	public void setCurrentScorerId2(String currentScorerId2) {
		this.currentScorerId2 = currentScorerId2;
	}

	public String getCurrentScorerId3() {
		return currentScorerId3;
	}

	public void setCurrentScorerId3(String currentScorerId3) {
		this.currentScorerId3 = currentScorerId3;
	}

	public String getCurrentScorerId4() {
		return currentScorerId4;
	}

	public void setCurrentScorerId4(String currentScorerId4) {
		this.currentScorerId4 = currentScorerId4;
	}

	public String getCurrentScorerId5() {
		return currentScorerId5;
	}

	public String getPunchTextData() {
		return punchTextData;
	}

	public void setPunchTextData(String punchTextData) {
		this.punchTextData = punchTextData;
	}

	public void setCurrentScorerId5(String currentScorerId5) {
		this.currentScorerId5 = currentScorerId5;
	}

	public Integer getCurrentCategoryType() {
		return currentCategoryType;
	}

	public void setCurrentCategoryType(Integer currentCategoryType) {
		this.currentCategoryType = currentCategoryType;
	}

	public Integer[] getCurrentGradeNum() {
		return currentGradeNum;
	}

	public void setCurrentGradeNum(Integer[] currentGradeNum) {
		this.currentGradeNum = currentGradeNum;
	}

	public String getCurrentPendingCategory() {
		return currentPendingCategory;
	}

	public void setCurrentPendingCategory(String currentPendingCategory) {
		this.currentPendingCategory = currentPendingCategory;
	}

	public String getCurrentIncludeCheckPoints() {
		return currentIncludeCheckPoints;
	}

	public void setCurrentIncludeCheckPoints(String currentIncludeCheckPoints) {
		this.currentIncludeCheckPoints = currentIncludeCheckPoints;
	}

	public String getCurrentExcludeCheckPoints() {
		return currentExcludeCheckPoints;
	}

	public void setCurrentExcludeCheckPoints(String currentExcludeCheckPoints) {
		this.currentExcludeCheckPoints = currentExcludeCheckPoints;
	}

	public Short[] getCurrentStateList() {
		return currentStateList;
	}

	public void setCurrentStateList(Short[] currentStateList) {
		this.currentStateList = currentStateList;
	}

	public Integer getCurrentUpdateDateStartYear() {
		return currentUpdateDateStartYear;
	}

	public void setCurrentUpdateDateStartYear(Integer currentUpdateDateStartYear) {
		this.currentUpdateDateStartYear = currentUpdateDateStartYear;
	}

	public Integer getCurrentUpdateDateStartMonth() {
		return currentUpdateDateStartMonth;
	}

	public void setCurrentUpdateDateStartMonth(
			Integer currentUpdateDateStartMonth) {
		this.currentUpdateDateStartMonth = currentUpdateDateStartMonth;
	}

	public Integer getCurrentUpdateDateStartDay() {
		return currentUpdateDateStartDay;
	}

	public void setCurrentUpdateDateStartDay(Integer currentUpdateDateStartDay) {
		this.currentUpdateDateStartDay = currentUpdateDateStartDay;
	}

	public Integer getCurrentUpdateDateStartHours() {
		return currentUpdateDateStartHours;
	}

	public void setCurrentUpdateDateStartHours(
			Integer currentUpdateDateStartHours) {
		this.currentUpdateDateStartHours = currentUpdateDateStartHours;
	}

	public Integer getCurrentUpdateDateStartMin() {
		return currentUpdateDateStartMin;
	}

	public void setCurrentUpdateDateStartMin(Integer currentUpdateDateStartMin) {
		this.currentUpdateDateStartMin = currentUpdateDateStartMin;
	}

	/**
	 * @return the currentQualityCheckFlag
	 */
	public boolean isCurrentQualityCheckFlag() {
		return currentQualityCheckFlag;
	}

	/**
	 * @param currentQualityCheckFlag
	 *            the currentQualityCheckFlag to set
	 */
	public void setCurrentQualityCheckFlag(boolean currentQualityCheckFlag) {
		this.currentQualityCheckFlag = currentQualityCheckFlag;
	}

	/**
	 * @return the punchText
	 */
	public String getPunchText() {
		return punchText;
	}

	/**
	 * @param punchText
	 *            the punchText to set
	 */
	public void setPunchText(String punchText) {
		this.punchText = punchText;
	}

	/**
	 * @return the lookAfterwardsFlag
	 */
	public boolean isLookAfterwardsFlag() {
		return lookAfterwardsFlag;
	}

	/**
	 * @param lookAfterwardsFlag
	 *            the lookAfterwardsFlag to set
	 */
	public void setLookAfterwardsFlag(boolean lookAfterwardsFlag) {
		this.lookAfterwardsFlag = lookAfterwardsFlag;
	}

	/**
	 * @return the currentScorerRole
	 */
	public Integer[] getCurrentScorerRole() {
		return currentScorerRole;
	}

	/**
	 * @param currentScorerRole
	 *            the currentScorerRole to set
	 */
	public void setCurrentScorerRole(Integer[] currentScorerRole) {
		this.currentScorerRole = currentScorerRole;
	}
	

	public String getCurrentDenyCategory() {
		return currentDenyCategory;
	}

	public void setCurrentDenyCategory(String currentDenyCategory) {
		this.currentDenyCategory = currentDenyCategory;
	}
	@SuppressWarnings("rawtypes")
	public List getCurrentDenyCategorySeqList() {
		return currentDenyCategorySeqList;
	}
	@SuppressWarnings("rawtypes")
	public void setCurrentDenyCategorySeqList(List currentDenyCategorySeqList) {
		this.currentDenyCategorySeqList = currentDenyCategorySeqList;
	}

	@Override
	public String toString() {
		StringBuilder data = new StringBuilder();
		data.append("{ CurrentScorerId1: " + currentScorerId1
				+ ", CurrentScorerId2: " + currentScorerId2
				+ ", CurrentScorerId3: " + currentScorerId3
				+ ", CurrentScorerId4: " + currentScorerId4
				+ ", CurrentScorerId5: " + currentScorerId5);
		data.append("\n CurrentScorerRole: "
				+ Arrays.toString(currentScorerRole));
		data.append("\n Punch Text: " + punchText);
		data.append("\n Quality Mark: " + currentQualityCheckFlag
				+ ", LookAfterwardsFlag: " + lookAfterwardsFlag);
		data.append("\n CurrentCategoryType: " + currentCategoryType);
		data.append("\n CurrentGradeNum: " + Arrays.toString(currentGradeNum));
		data.append("\n CurrentPendingCategory: " + currentPendingCategory);
		data.append("\n CurrentIncludeCheckPoints: "
				+ currentIncludeCheckPoints);
		data.append("\n CurrentExcludeCheckPoints: "
				+ currentExcludeCheckPoints);
		data.append("\n CurrentStateList: " + Arrays.toString(currentStateList));
		data.append("\n CurrentUpdateStartDate: " + currentUpdateDateStartYear
				+ "-" + currentUpdateDateStartMonth + "-"
				+ currentUpdateDateStartDay + " " + currentUpdateDateStartHours
				+ ":" + currentUpdateDateStartMin + ", CurrentUpdateEndDate: "
				+ currentUpdateDateEndYear + "-" + currentUpdateDateEndMonth
				+ "-" + currentUpdateDateEndDay + " "
				+ currentUpdateDateEndHours + ":" + currentUpdateDateEndMin);
		data.append("}");
		return data.toString();
	}

	public void setCurrentSkpConditions(String currentSkpConditions) {
		this.currentSkpConditions = currentSkpConditions;
	}

	public String getCurrentSkpConditions() {
		return currentSkpConditions;
	}
}
