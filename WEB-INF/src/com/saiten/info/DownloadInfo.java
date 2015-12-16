package com.saiten.info;

public class DownloadInfo {

	private String reportType;
	private boolean ratingProgressReport;
	private boolean discrepancyAnalysisReport;
	private String startDate;
	private String endDate;
	private String startHours;
	private String startMinutes;
	private String endHours;
	private String endMinutes;
	private String fromTime;
	private String toTime;

	public String getFromTime() {
		return fromTime;
	}

	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}

	public String getToTime() {
		return toTime;
	}

	public void setToTime(String toTime) {
		this.toTime = toTime;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getStartHours() {
		return startHours;
	}

	public void setStartHours(String startHours) {
		this.startHours = startHours;
	}

	public String getStartMinutes() {
		return startMinutes;
	}

	public void setStartMinutes(String startMinutes) {
		this.startMinutes = startMinutes;
	}

	public String getEndHours() {
		return endHours;
	}

	public void setEndHours(String endHours) {
		this.endHours = endHours;
	}

	public String getEndMinutes() {
		return endMinutes;
	}

	public void setEndMinutes(String endMinutes) {
		this.endMinutes = endMinutes;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public boolean isRatingProgressReport() {
		return ratingProgressReport;
	}

	public void setRatingProgressReport(boolean ratingProgressReport) {
		this.ratingProgressReport = ratingProgressReport;
	}

	public boolean isDiscrepancyAnalysisReport() {
		return discrepancyAnalysisReport;
	}

	public void setDiscrepancyAnalysisReport(boolean discrepancyAnalysisReport) {
		this.discrepancyAnalysisReport = discrepancyAnalysisReport;
	}

}
