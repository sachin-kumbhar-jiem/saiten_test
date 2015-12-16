package com.saiten.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.saiten.exception.SaitenRuntimeException;
import com.saiten.info.DownloadInfo;
import com.saiten.service.DownloadReportService;
import com.saiten.util.ErrorCode;
import com.saiten.util.SaitenUtil;

/**
 * @author rajesh
 * @version 1.0
 * @created 26-Aug-2015
 */
public class DownloadReportAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 1L;
	private Map<String, Object> session;
	private InputStream inputStream;
	private String fileName;
	private DownloadReportService downloadReportService;
	private DownloadInfo downloadInfo;
	private Map<String, String> hoursMap;
	private Map<String, String> minsMap;

	public String onLoad() {
		try {
			hoursMap = SaitenUtil.getClockHours();
			minsMap = SaitenUtil.getClockMinutes();
			setSessionParams();
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.DOWNLOAD_REPORT_ONLOAD_ACTION_EXCEPTION, e);
		}
		return SUCCESS;
	}

	public String download() {

		try {
			String downloadFilePath = downloadReportService
					.processDownloadRequest(downloadInfo);

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
					ErrorCode.DOWNLOAD_DAILY_SCORING_SUMMARY_REPORT_ACTION_EXCEPTION,
					e);
		}
		return SUCCESS;
	}

	private void setSessionParams() {
		session.put("hoursMap", hoursMap);
		session.put("minsMap", minsMap);
	}

	/**
	 * 
	 * @param session
	 */
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public Map<String, Object> getSession() {
		return session;
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

	public void setDownloadReportService(
			DownloadReportService downloadReportService) {
		this.downloadReportService = downloadReportService;
	}

	public DownloadInfo getDownloadInfo() {
		return downloadInfo;
	}

	public void setDownloadInfo(DownloadInfo downloadInfo) {
		this.downloadInfo = downloadInfo;
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

}
