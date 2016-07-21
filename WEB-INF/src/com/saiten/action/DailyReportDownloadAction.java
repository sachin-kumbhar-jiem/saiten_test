package com.saiten.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.SessionAware;

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
public class DailyReportDownloadAction extends ActionSupport implements
		SessionAware {

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
	private Map<String, Object> session;
	private Map<String, String> hoursMap;
	private Map<String, String> minsMap;
	private Map<String, String> secsMap;

	public String onLoad() {
		try {
			hoursMap = getHours();
			minsMap = getMinutes();
			secsMap = minsMap;
			setSessionParams();
			session.remove("dailyReports");
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.DOWNLOAD_DAILY_REPORT_ACTION_EXCEPTION, e);
		}
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
							quesSequences, dailyReportCurrentDate,
							dailyReportsInfo);
			
            session.put("dailyReports", dailyReports);
			
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

	private void setSessionParams() {
		session.put("hoursMap", hoursMap);
		session.put("minsMap", minsMap);
		session.put("secsMap", secsMap);
	}

	public static Map<String, String> getHours() {

		Map<String, String> hoursMap = new LinkedHashMap<String, String>();
		for (int i = WebAppConst.CLOCK_FIRST_HOUR; i <= WebAppConst.CLOCK_LAST_HOUR; i++) {
			hoursMap.put(String.format("%02d", i), String.format("%02d", i));
		}
		return hoursMap;
	}

	public static Map<String, String> getMinutes() {

		Map<String, String> minutesMap = new LinkedHashMap<String, String>();
		for (int i = WebAppConst.CLOCK_FIRST_MINUTE; i <= WebAppConst.CLOCK_LAST_MINUTE; i++) {
			minutesMap.put(String.format("%02d", i), String.format("%02d", i));
		}
		return minutesMap;
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

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public Map<String, String> getHoursMap() {
		return hoursMap;
	}

	public void setHoursMap(Map<String, String> hoursMap) {
		this.hoursMap = hoursMap;
	}

	public Map<String, String> getMinsMap() {
		return minsMap;
	}

	public void setMinsMap(Map<String, String> minsMap) {
		this.minsMap = minsMap;
	}

	public Map<String, String> getSecsMap() {
		return secsMap;
	}

	public void setSecsMap(Map<String, String> secsMap) {
		this.secsMap = secsMap;
	}
}
