package com.saiten.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.ActionSupport;
import com.saiten.exception.SaitenRuntimeException;
import com.saiten.info.DailyReportsInfo;
import com.saiten.service.DailyReportsService;
import com.saiten.util.ErrorCode;
import com.saiten.util.WebAppConst;

/**
 * @author rahul
 * 
 */
public class DailyReportDownloadAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2327794053194431792L;
	private InputStream inputStream;
	private String fileName;
	private DailyReportsService dailyReportsService;
	private DailyReportsInfo dailyReportsInfo;
	private String dailyReports;
	private String quesSequences;
	private String dailyReportCurrentDate;

	public String onLoad() {
		return SUCCESS;
	}

	public String downloadReport() {

		try {
			if (dailyReports.equals(WebAppConst.SPECIFIC_STUD_QUES_COUNT)) {
				quesSequences = dailyReportsInfo.questionSequences;
			}

			if (dailyReports.equals(WebAppConst.LOGIN_LOGOUT_REPORT)) {
				dailyReportCurrentDate = dailyReportsInfo.currentDate;
			}

			String downloadFilePath = dailyReportsService
					.processDownloadDailyReportRequest(dailyReports,
							quesSequences, dailyReportCurrentDate);

			if (!StringUtils.isBlank(downloadFilePath)) {

				File fileToDownload = new File(downloadFilePath);

				inputStream = new FileInputStream(fileToDownload);

				fileName = fileToDownload.getName();

			} else {
				addActionError(getText("label.data.not.found"));
				return INPUT;
			}
		} catch (SaitenRuntimeException we) {
			throw we;
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.DOWNLOAD_DAILY_REPORT_ACTION_EXCEPTION, e);
		}
		return SUCCESS;
	}

	public String getDailyReportCurrentDate() {
		return dailyReportCurrentDate;
	}

	public void setDailyReportCurrentDate(String dailyReportCurrentDate) {
		this.dailyReportCurrentDate = dailyReportCurrentDate;
	}

	public String getQuesSequences() {
		return quesSequences;
	}

	public void setQuesSequences(String quesSequences) {
		this.quesSequences = quesSequences;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public DailyReportsService getDailyReportsService() {
		return dailyReportsService;
	}

	public void setDailyReportsService(DailyReportsService dailyReportsService) {
		this.dailyReportsService = dailyReportsService;
	}

	public DailyReportsInfo getDailyReportsInfo() {
		return dailyReportsInfo;
	}

	public void setDailyReportsInfo(DailyReportsInfo dailyReportsInfo) {
		this.dailyReportsInfo = dailyReportsInfo;
	}

	public String getDailyReports() {
		return dailyReports;
	}

	public void setDailyReports(String dailyReports) {
		this.dailyReports = dailyReports;
	}
}
