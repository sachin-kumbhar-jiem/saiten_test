package com.saiten.info;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author sachin
 * @version 1.0
 * @created 11-Dec-2012 12:40:12 PM
 */
public class ScoreHistoryInfo {
	private boolean historyBlock;
	private String historyScorerId1;
	private String historyScorerId2;
	private String historyScorerId3;
	private String historyScorerId4;
	private String historyScorerId5;
	private List<String> historyScorerIdList;
	private Integer historyCategoryType;
	private Integer[] historyGradeNum;
	@SuppressWarnings("rawtypes")
	private List historyGradeSeqList;
	private String historyPendingCategory;
	@SuppressWarnings("rawtypes")
	private List historyPendingCategorySeqList;
	private String historyIncludeCheckPoints;
	private Integer historyIncludeBitValue;
	private String historyExcludeCheckPoints;
	private Integer historyExcludeBitValue;
	private Short[] historyEventList;
	private Integer historyUpdateDateStartYear;
	private Integer historyUpdateDateStartMonth;
	private Integer historyUpdateDateStartDay;
	private Integer historyUpdateDateStartHours;
	private Integer historyUpdateDateStartMin;
	private Integer historyUpdateDateEndYear;
	private Integer historyUpdateDateEndMonth;
	private Integer historyUpdateDateEndDay;
	private Integer historyUpdateDateEndHours;
	private Integer historyUpdateDateEndMin;
	private Date historyUpdateStartDate;
	private Date historyUpdateEndDate;
	private boolean historyQualityCheckFlag;
	private Integer[] historyScorerRole;

	public Integer getHistoryUpdateDateEndYear() {
		return historyUpdateDateEndYear;
	}

	/**
	 * @param historyUpdateDateEndYear
	 */
	public void setHistoryUpdateDateEndYear(Integer historyUpdateDateEndYear) {
		this.historyUpdateDateEndYear = historyUpdateDateEndYear;
	}

	public Date getHistoryUpdateStartDate() {
		return historyUpdateStartDate;
	}

	/**
	 * @param historyUpdateStartDate
	 */
	public void setHistoryUpdateStartDate(Date historyUpdateStartDate) {
		this.historyUpdateStartDate = historyUpdateStartDate;
	}

	public Date getHistoryUpdateEndDate() {
		return historyUpdateEndDate;
	}

	/**
	 * @param historyUpdateEndDate
	 */
	public void setHistoryUpdateEndDate(Date historyUpdateEndDate) {
		this.historyUpdateEndDate = historyUpdateEndDate;
	}

	public boolean isHistoryBlock() {
		return historyBlock;
	}

	/**
	 * @param historyBlock
	 */
	public void setHistoryBlock(boolean historyBlock) {
		this.historyBlock = historyBlock;
	}

	public Integer[] getHistoryGradeNum() {
		return historyGradeNum;
	}

	/**
	 * @param historyGradeNum
	 */
	public void setHistoryGradeNum(Integer[] historyGradeNum) {
		this.historyGradeNum = historyGradeNum;
	}

	public Integer getHistoryUpdateDateEndMonth() {
		return historyUpdateDateEndMonth;
	}

	/**
	 * @param historyUpdateDateEndMonth
	 */
	public void setHistoryUpdateDateEndMonth(Integer historyUpdateDateEndMonth) {
		this.historyUpdateDateEndMonth = historyUpdateDateEndMonth;
	}

	public Integer getHistoryUpdateDateEndDay() {
		return historyUpdateDateEndDay;
	}

	/**
	 * @param historyUpdateDateEndDay
	 */
	public void setHistoryUpdateDateEndDay(Integer historyUpdateDateEndDay) {
		this.historyUpdateDateEndDay = historyUpdateDateEndDay;
	}

	public Integer getHistoryUpdateDateEndHours() {
		return historyUpdateDateEndHours;
	}

	/**
	 * @param historyUpdateDateEndHours
	 */
	public void setHistoryUpdateDateEndHours(Integer historyUpdateDateEndHours) {
		this.historyUpdateDateEndHours = historyUpdateDateEndHours;
	}

	public Integer getHistoryUpdateDateEndMin() {
		return historyUpdateDateEndMin;
	}

	/**
	 * @param historyUpdateDateEndMin
	 */
	public void setHistoryUpdateDateEndMin(Integer historyUpdateDateEndMin) {
		this.historyUpdateDateEndMin = historyUpdateDateEndMin;
	}

	public String getHistoryScorerId1() {
		return historyScorerId1;
	}

	/**
	 * @param historyScorerId1
	 */
	public void setHistoryScorerId1(String historyScorerId1) {
		this.historyScorerId1 = historyScorerId1;
	}

	public String getHistoryScorerId2() {
		return historyScorerId2;
	}

	/**
	 * @param historyScorerId2
	 */
	public void setHistoryScorerId2(String historyScorerId2) {
		this.historyScorerId2 = historyScorerId2;
	}

	public String getHistoryScorerId3() {
		return historyScorerId3;
	}

	/**
	 * @param historyScorerId3
	 */
	public void setHistoryScorerId3(String historyScorerId3) {
		this.historyScorerId3 = historyScorerId3;
	}

	public String getHistoryScorerId4() {
		return historyScorerId4;
	}

	/**
	 * @param historyScorerId4
	 */
	public void setHistoryScorerId4(String historyScorerId4) {
		this.historyScorerId4 = historyScorerId4;
	}

	public String getHistoryScorerId5() {
		return historyScorerId5;
	}

	/**
	 * @param historyScorerId5
	 */
	public void setHistoryScorerId5(String historyScorerId5) {
		this.historyScorerId5 = historyScorerId5;
	}

	public Integer getHistoryCategoryType() {
		return historyCategoryType;
	}

	/**
	 * @param historyCategoryType
	 */
	public void setHistoryCategoryType(Integer historyCategoryType) {
		this.historyCategoryType = historyCategoryType;
	}

	@SuppressWarnings("rawtypes")
	public List getHistoryGradeSeqList() {
		return historyGradeSeqList;
	}

	/**
	 * @param historyGradeSeqList
	 */
	@SuppressWarnings("rawtypes")
	public void setHistoryGradeSeqList(List historyGradeSeqList) {
		this.historyGradeSeqList = historyGradeSeqList;
	}

	public String getHistoryPendingCategory() {
		return historyPendingCategory;
	}

	/**
	 * @param historyPendingCategory
	 */
	public void setHistoryPendingCategory(String historyPendingCategory) {
		this.historyPendingCategory = historyPendingCategory;
	}

	public String getHistoryIncludeCheckPoints() {
		return historyIncludeCheckPoints;
	}

	/**
	 * @param historyIncludeCheckPoints
	 */
	public void setHistoryIncludeCheckPoints(String historyIncludeCheckPoints) {
		this.historyIncludeCheckPoints = historyIncludeCheckPoints;
	}

	public String getHistoryExcludeCheckPoints() {
		return historyExcludeCheckPoints;
	}

	/**
	 * @param historyExcludeCheckPoints
	 */
	public void setHistoryExcludeCheckPoints(String historyExcludeCheckPoints) {
		this.historyExcludeCheckPoints = historyExcludeCheckPoints;
	}

	public Short[] getHistoryEventList() {
		return historyEventList;
	}

	/**
	 * @param historyEventList
	 */
	public void setHistoryEventList(Short[] historyEventList) {
		this.historyEventList = historyEventList;
	}

	public Integer getHistoryUpdateDateStartYear() {
		return historyUpdateDateStartYear;
	}

	/**
	 * @param historyUpdateDateStartYear
	 */
	public void setHistoryUpdateDateStartYear(Integer historyUpdateDateStartYear) {
		this.historyUpdateDateStartYear = historyUpdateDateStartYear;
	}

	public Integer getHistoryUpdateDateStartMonth() {
		return historyUpdateDateStartMonth;
	}

	/**
	 * @param historyUpdateDateStartMonth
	 */
	public void setHistoryUpdateDateStartMonth(
			Integer historyUpdateDateStartMonth) {
		this.historyUpdateDateStartMonth = historyUpdateDateStartMonth;
	}

	public Integer getHistoryUpdateDateStartDay() {
		return historyUpdateDateStartDay;
	}

	/**
	 * @param historyUpdateDateStartDay
	 */
	public void setHistoryUpdateDateStartDay(Integer historyUpdateDateStartDay) {
		this.historyUpdateDateStartDay = historyUpdateDateStartDay;
	}

	public Integer getHistoryUpdateDateStartHours() {
		return historyUpdateDateStartHours;
	}

	/**
	 * @param historyUpdateDateStartHours
	 */
	public void setHistoryUpdateDateStartHours(
			Integer historyUpdateDateStartHours) {
		this.historyUpdateDateStartHours = historyUpdateDateStartHours;
	}

	public Integer getHistoryUpdateDateStartMin() {
		return historyUpdateDateStartMin;
	}

	/**
	 * @param historyUpdateDateStartMin
	 */
	public void setHistoryUpdateDateStartMin(Integer historyUpdateDateStartMin) {
		this.historyUpdateDateStartMin = historyUpdateDateStartMin;
	}

	@SuppressWarnings("rawtypes")
	public List getHistoryPendingCategorySeqList() {
		return historyPendingCategorySeqList;
	}

	/**
	 * @param historyPendingCategorySeqList
	 */
	@SuppressWarnings("rawtypes")
	public void setHistoryPendingCategorySeqList(
			List historyPendingCategorySeqList) {
		this.historyPendingCategorySeqList = historyPendingCategorySeqList;
	}

	public List<String> getHistoryScorerIdList() {
		return historyScorerIdList;
	}

	/**
	 * @param historyScorerIdList
	 */
	public void setHistoryScorerIdList(List<String> historyScorerIdList) {
		this.historyScorerIdList = historyScorerIdList;
	}

	public Integer getHistoryIncludeBitValue() {
		return historyIncludeBitValue;
	}

	/**
	 * @param historyIncludeBitValue
	 */
	public void setHistoryIncludeBitValue(Integer historyIncludeBitValue) {
		this.historyIncludeBitValue = historyIncludeBitValue;
	}

	public Integer getHistoryExcludeBitValue() {
		return historyExcludeBitValue;
	}

	/**
	 * @param historyExcludeBitValue
	 */
	public void setHistoryExcludeBitValue(Integer historyExcludeBitValue) {
		this.historyExcludeBitValue = historyExcludeBitValue;
	}

	/**
	 * @return the historyQualityCheckFlag
	 */
	public boolean isHistoryQualityCheckFlag() {
		return historyQualityCheckFlag;
	}

	/**
	 * @param historyQualityCheckFlag
	 *            the historyQualityCheckFlag to set
	 */
	public void setHistoryQualityCheckFlag(boolean historyQualityCheckFlag) {
		this.historyQualityCheckFlag = historyQualityCheckFlag;
	}

	/**
	 * @return the historyScorerRole
	 */
	public Integer[] getHistoryScorerRole() {
		return historyScorerRole;
	}

	/**
	 * @param historyScorerRole
	 *            the historyScorerRole to set
	 */
	public void setHistoryScorerRole(Integer[] historyScorerRole) {
		this.historyScorerRole = historyScorerRole;
	}

	@Override
	public String toString() {
		StringBuilder data = new StringBuilder();
		data.append("{ HistoryScorerId1: " + historyScorerId1
				+ ", HistoryScorerId2: " + historyScorerId2
				+ ", HistoryScorerId3: " + historyScorerId3
				+ ", HistoryScorerId4: " + historyScorerId4
				+ ", HistoryScorerId5: " + historyScorerId5);
		data.append("\n HistoryScorerRole: "
				+ Arrays.toString(historyScorerRole));
		data.append("\n Quality Mark: " + historyQualityCheckFlag);
		data.append("\n HistoryCategoryType: " + historyCategoryType);
		data.append("\n HistoryGradeNum: " + Arrays.toString(historyGradeNum));
		data.append("\n HistoryPendingCategory: " + historyPendingCategory);
		data.append("\n HistoryIncludeCheckPoints: "
				+ historyIncludeCheckPoints);
		data.append("\n HistoryExcludeCheckPoints: "
				+ historyExcludeCheckPoints);
		data.append("\n HistoryEventList: " + Arrays.toString(historyEventList));
		data.append("\n HistoryUpdateStartDate: " + historyUpdateDateStartYear
				+ "-" + historyUpdateDateStartMonth + "-"
				+ historyUpdateDateStartDay + " " + historyUpdateDateStartHours
				+ ":" + historyUpdateDateStartMin + ", HistoryUpdateEndDate: "
				+ historyUpdateDateEndYear + "-" + historyUpdateDateEndMonth
				+ "-" + historyUpdateDateEndDay + " "
				+ historyUpdateDateEndHours + ":" + historyUpdateDateEndMin);
		data.append("}");
		return data.toString();
	}

}
