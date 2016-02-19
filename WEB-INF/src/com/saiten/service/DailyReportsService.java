package com.saiten.service;

/**
 * @author rahul
 *
 */
public interface DailyReportsService {
     String processDownloadDailyReportRequest(String dailyReports,String questionSeqInfo,String dailyReportCurrentDate) throws Exception;
}