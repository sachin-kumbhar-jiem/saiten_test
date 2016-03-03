package com.saiten.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.hibernate.HibernateException;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.TextProvider;
import com.saiten.dao.MstDbInstanceDAO;
import com.saiten.dao.TranDescScoreDAO;
import com.saiten.dao.TranScorerAccessLogDAO;
import com.saiten.exception.SaitenRuntimeException;
import com.saiten.info.DailyReportsInfo;
import com.saiten.model.MstDbInstance;
import com.saiten.service.DailyReportsService;
import com.saiten.util.ErrorCode;
import com.saiten.util.SaitenFileUtil;
import com.saiten.util.SaitenUtil;
import com.saiten.util.WebAppConst;

/**
 * @author rahul
 * 
 */
public class DailyReportsServiceImpl extends ActionSupport implements
		DailyReportsService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TranDescScoreDAO tranDescScoreDAO;
	private MstDbInstanceDAO mstDbInstanceDAO;
	private DailyReportsInfo dailyReportsInfo;
	private TranScorerAccessLogDAO tranScorerAccessLogDAO;

	@SuppressWarnings("rawtypes")
	List studCntListForAllQues = new ArrayList();
	List<String> allQuesCountList = new ArrayList<String>();

	@SuppressWarnings("rawtypes")
	List confAndInspWaitCntList = new ArrayList();
	List<String> confAndInspWaitList = new ArrayList<String>();

	@SuppressWarnings("rawtypes")
	List specifiedQuesCntList = new ArrayList();
	List<String> specfQuesList = new ArrayList<String>();

	@SuppressWarnings("rawtypes")
	List scorerAccessLogList = new ArrayList();
	List<String> accessLogList = new ArrayList<String>();

	@SuppressWarnings("unchecked")
	public String processDownloadDailyReportRequest(String dailyReports,
			String quesSequences, String dailyReportCurrentDate)
			throws Exception {
		String fileToDownload = null;
		try {

			TextProvider textProvider = (TextProvider) ActionContext
					.getContext().getActionInvocation().getAction();

			if (dailyReports.equals(WebAppConst.STUD_COUNT_FOR_ALL_QUES)) {

				List<String> connectionStringList = new ArrayList<String>();

				@SuppressWarnings("rawtypes")
				List allQuestionsCountList = new ArrayList();

				List<MstDbInstance> mstDbInstanceList = mstDbInstanceDAO
						.findAll();
				
				for (MstDbInstance mstDbInstance : mstDbInstanceList) {
					connectionStringList.add(mstDbInstance
							.getConnectionString());
				}
				
				String downloadDirBasePath = textProvider.getTexts(
						WebAppConst.APPLICATION_PROPERTIES_FILE).getString(
						"saiten.daily.report.download.basepath");

				String directoryName = getDirectoryName(SaitenUtil
						.getLoggedinScorerId());

				File downloadDir;

				for (String connectionString : connectionStringList) {

					studCntListForAllQues = tranDescScoreDAO
							.getQuesStatewiseStudCountForAllQues(connectionString);

					if (studCntListForAllQues != null
							&& !studCntListForAllQues.isEmpty()) {

						allQuestionsCountList.addAll(studCntListForAllQues);
					}
				}

				if (allQuestionsCountList != null
						&& !allQuestionsCountList.isEmpty()) {
					downloadDir = SaitenFileUtil
							.createDirectory(
									downloadDirBasePath,
									textProvider
											.getText("daily.report.download.for.all.ques.zip.file.name.prefix")
											+ directoryName);
					getReportHeadersForAllQues(textProvider);

					getReportRowsForAllQues(allQuestionsCountList);

					File csvfile = new File(
							downloadDir.getPath()
									+ File.separator
									+ textProvider
											.getText("daily.csv.data.report.for.all.ques.csv.prefix")
									+ WebAppConst.CSV_FILE_EXTENSION);

					FileUtils.writeLines(csvfile, WebAppConst.FILE_ENCODING,
							allQuesCountList, WebAppConst.CRLF);

					if (!SaitenFileUtil.isEmptyDirectory(downloadDir)) {

						SaitenFileUtil.createZipFileFromDirectory(
								downloadDir.getPath(), downloadDir.getPath()
										+ WebAppConst.ZIP_FILE_EXTENSION);

						fileToDownload = downloadDir.getPath()
								+ WebAppConst.ZIP_FILE_EXTENSION;
					}
					SaitenFileUtil.deleteDirectory(downloadDir);
				}
			}

			else if (dailyReports.equals(WebAppConst.SPECIFIC_STUD_QUES_COUNT)) {

				List<String> questionSeqsInfoList = Arrays.asList(quesSequences
						.split(","));
				List<String> connectionStringList = new ArrayList<String>();

				@SuppressWarnings("rawtypes")
				List specQuesCountList = new ArrayList();

				List<MstDbInstance> mstDbInstanceList = mstDbInstanceDAO
						.findAll();

				for (MstDbInstance mstDbInstance : mstDbInstanceList) {
					connectionStringList.add(mstDbInstance
							.getConnectionString());
				}

				String downloadDirBasePath = textProvider.getTexts(
						WebAppConst.APPLICATION_PROPERTIES_FILE).getString(
						"saiten.daily.report.download.basepath");

				String directoryName = getDirectoryName(SaitenUtil
						.getLoggedinScorerId());

				File downloadDir;

				for (String connectionString : connectionStringList) {

					if (questionSeqsInfoList != null) {
						specifiedQuesCntList = tranDescScoreDAO
								.getQuesWiseStudCntForSpecQues(
										connectionString, questionSeqsInfoList);
					}

					if (specifiedQuesCntList != null
							&& !specifiedQuesCntList.isEmpty()) {

						specQuesCountList.addAll(specifiedQuesCntList);
					}
				}

				if (specQuesCountList != null && !specQuesCountList.isEmpty()) {

					downloadDir = SaitenFileUtil
							.createDirectory(
									downloadDirBasePath,
									textProvider
											.getText("daily.report.download.for.spec.ques.zip.file.name.prefix")
											+ directoryName);
					getReportHeadersForSpecQues(textProvider);

					getReportRowsForSpecQues(specQuesCountList);

					File csvfile = new File(
							downloadDir.getPath()
									+ File.separator
									+ textProvider
											.getText("daily.csv.data.report.for.spec.ques.csv.prefix")
									+ WebAppConst.CSV_FILE_EXTENSION);
					FileUtils.writeLines(csvfile, WebAppConst.FILE_ENCODING,
							specfQuesList, WebAppConst.CRLF);

					if (!SaitenFileUtil.isEmptyDirectory(downloadDir)) {

						SaitenFileUtil.createZipFileFromDirectory(
								downloadDir.getPath(), downloadDir.getPath()
										+ WebAppConst.ZIP_FILE_EXTENSION);

						fileToDownload = downloadDir.getPath()
								+ WebAppConst.ZIP_FILE_EXTENSION;
					}
					SaitenFileUtil.deleteDirectory(downloadDir);
				}
			}

			else if (dailyReports.equals(WebAppConst.CONFIRM_AND_WAIT_STATE)
					|| dailyReports
							.equals(WebAppConst.NOT_CONFIRM_AND_WAIT_STATE)) {

				List<String> connectionStringList = new ArrayList<String>();

				@SuppressWarnings("rawtypes")
				List questionCountList = new ArrayList();

				List<MstDbInstance> mstDbInstanceList = mstDbInstanceDAO
						.findAll();

				for (MstDbInstance mstDbInstance : mstDbInstanceList) {
					connectionStringList.add(mstDbInstance
							.getConnectionString());
				}

				String downloadDirBasePath = textProvider.getTexts(
						WebAppConst.APPLICATION_PROPERTIES_FILE).getString(
						"saiten.daily.report.download.basepath");

				String directoryName = getDirectoryName(SaitenUtil
						.getLoggedinScorerId());

				File downloadDir = null;
				File csvfile = null;

				for (String connectionString : connectionStringList) {

					confAndInspWaitCntList = tranDescScoreDAO
							.getConfirmAndInspectionWaitCount(dailyReports,
									connectionString);

					if (confAndInspWaitCntList != null
							&& !confAndInspWaitCntList.isEmpty()) {
						questionCountList.addAll(confAndInspWaitCntList);
					}
				}

				if (questionCountList != null && !questionCountList.isEmpty()) {

					if (dailyReports.equals(WebAppConst.CONFIRM_AND_WAIT_STATE)) {

						downloadDir = SaitenFileUtil
								.createDirectory(
										downloadDirBasePath,
										textProvider
												.getText("daily.report.for.confirm.and.inspection.wait.zip.file.name.prefix")
												+ directoryName);
						csvfile = new File(
								downloadDir.getPath()
										+ File.separator
										+ textProvider
												.getText("daily.report.for.confirm.and.inspection.wait.csv.prefix")
										+ WebAppConst.CSV_FILE_EXTENSION);

					} else if (dailyReports
							.equals(WebAppConst.NOT_CONFIRM_AND_WAIT_STATE)) {

						downloadDir = SaitenFileUtil
								.createDirectory(
										downloadDirBasePath,
										textProvider
												.getText("daily.report.for.not.confirm.and.inspection.wait.zip.file.name.prefix")
												+ directoryName);
						csvfile = new File(
								downloadDir.getPath()
										+ File.separator
										+ textProvider
												.getText("daily.report.for.not.confirm.and.inspection.wait.csv.prefix")
										+ WebAppConst.CSV_FILE_EXTENSION);
					}

					getDailyCSVReportHeaders(textProvider);

					getDailyCSVReportRows(questionCountList);

					FileUtils.writeLines(csvfile, WebAppConst.FILE_ENCODING,
							confAndInspWaitList, WebAppConst.CRLF);

					if (!SaitenFileUtil.isEmptyDirectory(downloadDir)) {

						SaitenFileUtil.createZipFileFromDirectory(
								downloadDir.getPath(), downloadDir.getPath()
										+ WebAppConst.ZIP_FILE_EXTENSION);

						fileToDownload = downloadDir.getPath()
								+ WebAppConst.ZIP_FILE_EXTENSION;
					}
					SaitenFileUtil.deleteDirectory(downloadDir);
				}
			}

			else if (dailyReports.equals(WebAppConst.LOGIN_LOGOUT_REPORT)) {

				@SuppressWarnings("rawtypes")
				List tempList = new ArrayList();

				String downloadDirBasePath = textProvider.getTexts(
						WebAppConst.APPLICATION_PROPERTIES_FILE).getString(
						"saiten.daily.report.download.basepath");

				String directoryName = getDirectoryName(SaitenUtil
						.getLoggedinScorerId());

				File downloadDir;

				scorerAccessLogList = tranScorerAccessLogDAO
						.loginLogoutReport(dailyReportCurrentDate);

				if (scorerAccessLogList != null
						&& !scorerAccessLogList.isEmpty()) {

					tempList.addAll(scorerAccessLogList);
				}

				if (tempList != null && !tempList.isEmpty()) {
					downloadDir = SaitenFileUtil
							.createDirectory(
									downloadDirBasePath,
									textProvider
											.getText("daily.report.for.login.logout.zip.file.name.prefix")
											+ directoryName);
					getHeadersForLoginLogoutReport(textProvider);

					getRowsForLoginLogoutReport(tempList);

					File csvfile = new File(
							downloadDir.getPath()
									+ File.separator
									+ textProvider
											.getText("daily.report.for.login.logout.csv.file.name.prefix")
									+ WebAppConst.CSV_FILE_EXTENSION);
					FileUtils.writeLines(csvfile, WebAppConst.FILE_ENCODING,
							accessLogList, WebAppConst.CRLF);

					if (!SaitenFileUtil.isEmptyDirectory(downloadDir)) {

						SaitenFileUtil.createZipFileFromDirectory(
								downloadDir.getPath(), downloadDir.getPath()
										+ WebAppConst.ZIP_FILE_EXTENSION);

						fileToDownload = downloadDir.getPath()
								+ WebAppConst.ZIP_FILE_EXTENSION;
					}
					SaitenFileUtil.deleteDirectory(downloadDir);
				}
			}

		} catch (HibernateException he) {
			throw new SaitenRuntimeException(
					ErrorCode.DOWNLOAD_DAILY_REPORT_HIBERNATE_EXCEPTION, he);
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.DOWNLOAD_DAILY_REPORT_SERVICE_EXCEPTION, e);
		}
		return fileToDownload;
	}

	private void getRowsForLoginLogoutReport(List tempList) {
		if (!tempList.isEmpty()) {

			for (Object record : tempList) {

				Object[] objectRecordArray = (Object[]) record;

				StringBuilder csvData = new StringBuilder();

				csvData.append(objectRecordArray[0]);
				csvData.append(WebAppConst.COMMA);
				csvData.append(objectRecordArray[1]);
				csvData.append(WebAppConst.COMMA);
				csvData.append(objectRecordArray[2]);
				csvData.append(WebAppConst.COMMA);
				csvData.append(objectRecordArray[3]);
				csvData.append(WebAppConst.COMMA);
				csvData.append(objectRecordArray[4]);

				accessLogList.add(csvData.toString());
			}
		}
	}

	private void getHeadersForLoginLogoutReport(TextProvider textProvider) {

		StringBuilder csvHeaders = new StringBuilder();
		csvHeaders.append(textProvider.getText("label.login.logout.id"));
		csvHeaders.append(WebAppConst.COMMA);
		csvHeaders.append(textProvider.getText("label.login.logout.scorer.id"));
		csvHeaders.append(WebAppConst.COMMA);
		csvHeaders.append(textProvider.getText("label.login.time"));
		csvHeaders.append(WebAppConst.COMMA);
		csvHeaders.append(textProvider.getText("label.logout.time"));
		csvHeaders.append(WebAppConst.COMMA);
		csvHeaders.append(textProvider.getText("label.login.logout.status"));

		accessLogList.add(csvHeaders.toString());
	}

	private void getReportRowsForSpecQues(List specQuesCountList) {
		if (!specQuesCountList.isEmpty()) {

			for (Object record : specQuesCountList) {

				Object[] objectRecordArray = (Object[]) record;

				StringBuilder csvData = new StringBuilder();

				csvData.append(objectRecordArray[0]);
				csvData.append(WebAppConst.COMMA);
				csvData.append(objectRecordArray[1]);
				csvData.append(WebAppConst.COMMA);
				csvData.append(objectRecordArray[2]);

				specfQuesList.add(csvData.toString());
			}
		}
	}

	private void getReportHeadersForSpecQues(TextProvider textProvider) {
		StringBuilder csvHeaders = new StringBuilder();
		csvHeaders.append(textProvider.getText("label.question.seq"));
		csvHeaders.append(WebAppConst.COMMA);
		csvHeaders.append(textProvider.getText("label.latest.scoring.state"));
		csvHeaders.append(WebAppConst.COMMA);
		csvHeaders.append(textProvider.getText("label.count"));

		specfQuesList.add(csvHeaders.toString());
	}

	private void getDailyCSVReportRows(List questionCountList) {

		if (!questionCountList.isEmpty()) {

			for (Object record : questionCountList) {

				Object[] objectRecordArray = (Object[]) record;

				StringBuilder csvData = new StringBuilder();

				csvData.append(objectRecordArray[0]);
				csvData.append(WebAppConst.COMMA);
				csvData.append(objectRecordArray[1]);
				csvData.append(WebAppConst.COMMA);
				csvData.append(objectRecordArray[2]);

				confAndInspWaitList.add(csvData.toString());
			}
		}
	}

	private void getDailyCSVReportHeaders(TextProvider textProvider) {

		StringBuilder csvHeaders = new StringBuilder();

		csvHeaders.append(textProvider.getText("label.question.seq"));
		csvHeaders.append(WebAppConst.COMMA);
		csvHeaders.append(textProvider.getText("label.grade.num"));
		csvHeaders.append(WebAppConst.COMMA);
		csvHeaders.append(textProvider.getText("label.count"));

		confAndInspWaitList.add(csvHeaders.toString());
	}

	@SuppressWarnings("rawtypes")
	private void getReportRowsForAllQues(List allQuestionsCountList) {
		if (!allQuestionsCountList.isEmpty()) {

			Map stateMap = SaitenUtil.latestScoringStatesMap();

			for (Object record : allQuestionsCountList) {

				Object[] objectRecordArray = (Object[]) record;

				StringBuilder csvData = new StringBuilder();

				csvData.append(objectRecordArray[0]);
				csvData.append(WebAppConst.COMMA);

				if (objectRecordArray[1] != null) {
					String stateValues = (String) stateMap.get((int) Short
							.parseShort(objectRecordArray[1].toString()));
					csvData.append(stateValues);
				}
				csvData.append(WebAppConst.COMMA);
				csvData.append(objectRecordArray[2]);

				allQuesCountList.add(csvData.toString());
			}
		}
	}

	private void getReportHeadersForAllQues(TextProvider textProvider) {

		StringBuilder csvHeaders = new StringBuilder();
		csvHeaders.append(textProvider.getText("label.question.seq"));
		csvHeaders.append(WebAppConst.COMMA);
		csvHeaders.append(textProvider.getText("label.latest.scoring.state"));
		csvHeaders.append(WebAppConst.COMMA);
		csvHeaders.append(textProvider.getText("label.count"));

		allQuesCountList.add(csvHeaders.toString());
	}

	private String getDirectoryName(String userID) {
		Date curDate = new Date();
		String pattern = "yyyyMMdd_HHmm";
		return getFormattedDateTimeStamp(curDate, pattern)
				+ WebAppConst.UNDERSCORE + userID;
	}

	private String getFormattedDateTimeStamp(Date date, String format) {
		SimpleDateFormat sdfDate = new SimpleDateFormat(format);
		String strDate = sdfDate.format(date);
		return strDate;
	}

	public MstDbInstanceDAO getMstDbInstanceDAO() {
		return mstDbInstanceDAO;
	}

	public void setMstDbInstanceDAO(MstDbInstanceDAO mstDbInstanceDAO) {
		this.mstDbInstanceDAO = mstDbInstanceDAO;
	}

	public void setTranDescScoreDAO(TranDescScoreDAO tranDescScoreDAO) {
		this.tranDescScoreDAO = tranDescScoreDAO;
	}

	public TranDescScoreDAO getTranDescScoreDAO() {
		return tranDescScoreDAO;
	}

	public void setDailyReportsInfo(DailyReportsInfo dailyReportsInfo) {
		this.dailyReportsInfo = dailyReportsInfo;
	}

	public DailyReportsInfo getDailyReportsInfo() {
		return dailyReportsInfo;
	}

	public TranScorerAccessLogDAO getTranScorerAccessLogDAO() {
		return tranScorerAccessLogDAO;
	}

	public void setTranScorerAccessLogDAO(
			TranScorerAccessLogDAO tranScorerAccessLogDAO) {
		this.tranScorerAccessLogDAO = tranScorerAccessLogDAO;
	}
}