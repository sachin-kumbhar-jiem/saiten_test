package com.saiten.service;

import com.saiten.info.DailyReportsInfo;

/**
 * @author rahul
 * 
 */
public interface DailyReportsService {
	String processDownloadDailyReportRequest(String dailyReports,
			String questionSeqInfo, String dailyReportCurrentDate,
			DailyReportsInfo dailyReportsInfo) throws Exception;
}