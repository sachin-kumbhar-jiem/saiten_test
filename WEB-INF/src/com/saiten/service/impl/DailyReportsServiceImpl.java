package com.saiten.service.impl;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.hibernate.HibernateException;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.TextProvider;
import com.saiten.dao.AnswerDetailDAO;
import com.saiten.dao.MstDbInstanceDAO;
import com.saiten.dao.TranDescScoreDAO;
import com.saiten.dao.TranDescScoreHistoryDAO;
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
/**
 * @author suwarna
 *
 */
public class DailyReportsServiceImpl extends ActionSupport implements DailyReportsService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TranDescScoreDAO tranDescScoreDAO;
	private MstDbInstanceDAO mstDbInstanceDAO;
	private DailyReportsInfo dailyReportsInfo;
	private TranScorerAccessLogDAO tranScorerAccessLogDAO;
	private TranDescScoreHistoryDAO tranDescScoreHistoryDAO;
	private AnswerDetailDAO answerDetailDAO;

	private List<String> studCntListForAllQues;
	private List<String> allQuesCountList;

	private List<String> confAndInspWaitCntList;
	private List<String> confAndInspWaitList;

	private List<String> specifiedQuesCntList;
	private List<String> specfQuesList;

	private List<String> scorerAccessLogList;
	private List<String> accessLogList;

	private List<String> questionWiseCountListForPendCategory;
	private List<String> pendingCategoryIsSetlist;

	private List<String> gradeWiseCountListForGradeAvailable;
	private List<String> gradeIsAvailablelist;

	private List<String> dateTimeWiseCountList;

	@SuppressWarnings({ "unchecked" })
	public String processDownloadDailyReportRequest(String dailyReports, String quesSequences,
			String dailyReportCurrentDate, DailyReportsInfo dailyReportsInfo) throws Exception {

		allQuesCountList = new ArrayList<String>();
		confAndInspWaitList = new ArrayList<String>();
		specfQuesList = new ArrayList<String>();
		accessLogList = new ArrayList<String>();
		gradeIsAvailablelist = new ArrayList<String>();
		pendingCategoryIsSetlist = new ArrayList<String>();
		dateTimeWiseCountList = new ArrayList<String>();
		String fileToDownload = null;

		try {
			List<String> connectionStringList = new ArrayList<String>();

			List<MstDbInstance> mstDbInstanceList = mstDbInstanceDAO.findAll();

			TextProvider textProvider = (TextProvider) ActionContext.getContext().getActionInvocation().getAction();

			String downloadDirBasePath = textProvider.getTexts(WebAppConst.APPLICATION_PROPERTIES_FILE)
					.getString("saiten.daily.report.download.basepath");

			String directoryName = getDirectoryName(SaitenUtil.getLoggedinScorerId());

			for (MstDbInstance mstDbInstance : mstDbInstanceList) {
				connectionStringList.add(mstDbInstance.getConnectionString());
			}

			File downloadDir = null;

			if (dailyReports.equals(WebAppConst.STUD_COUNT_FOR_ALL_QUES)) {

				studCntListForAllQues = new ArrayList<String>();

				List<String> allQuestionsCountList = new ArrayList<String>();
				for (String connectionString : connectionStringList) {

					studCntListForAllQues = tranDescScoreDAO.getQuesStatewiseStudCountForAllQues(connectionString);

					if (studCntListForAllQues != null && !studCntListForAllQues.isEmpty()) {
						allQuestionsCountList.addAll(studCntListForAllQues);
					}
				}

				if (allQuestionsCountList != null && !allQuestionsCountList.isEmpty()) {
					downloadDir = SaitenFileUtil.createDirectory(downloadDirBasePath,
							textProvider.getText("daily.report.download.for.all.ques.zip.file.name.prefix")
									+ directoryName);
					getReportHeadersForAllQues(textProvider);

					getReportRowsForAllQues(allQuestionsCountList);

					File txtFile = new File(downloadDir.getPath() + File.separator
							+ textProvider.getText("daily.csv.data.report.for.all.ques.csv.prefix")
							+ WebAppConst.TXT_FILE_EXTENSION);

					txtFile.createNewFile();

					FileUtils.writeLines(txtFile, WebAppConst.FILE_ENCODING, allQuesCountList, WebAppConst.CRLF);

					if (!SaitenFileUtil.isEmptyDirectory(downloadDir)) {

						SaitenFileUtil.createZipFileFromDirectory(downloadDir.getPath(),
								downloadDir.getPath() + WebAppConst.ZIP_FILE_EXTENSION);

						fileToDownload = downloadDir.getPath() + WebAppConst.ZIP_FILE_EXTENSION;
					}
					SaitenFileUtil.deleteDirectory(downloadDir);
				}
			}

			else if (dailyReports.equals(WebAppConst.SPECIFIC_STUD_QUES_COUNT)) {

				List<String> questionSeqsInfoList = Arrays.asList(quesSequences.split(","));

				specifiedQuesCntList = new ArrayList<String>();
				List<String> specQuesCountList = new ArrayList<String>();

				for (String connectionString : connectionStringList) {

					if (questionSeqsInfoList != null) {
						specifiedQuesCntList = tranDescScoreDAO.getQuesWiseStudCntForSpecQues(connectionString,
								questionSeqsInfoList);
					}

					if (specifiedQuesCntList != null && !specifiedQuesCntList.isEmpty()) {
						specQuesCountList.addAll(specifiedQuesCntList);
					}
				}

				if (specQuesCountList != null && !specQuesCountList.isEmpty()) {

					downloadDir = SaitenFileUtil.createDirectory(downloadDirBasePath,
							textProvider.getText("daily.report.download.for.spec.ques.zip.file.name.prefix")
									+ directoryName);
					getReportHeadersForSpecQues(textProvider);

					getReportRowsForSpecQues(specQuesCountList);

					File txtFile = new File(downloadDir.getPath() + File.separator
							+ textProvider.getText("daily.csv.data.report.for.spec.ques.csv.prefix")
							+ WebAppConst.TXT_FILE_EXTENSION);
					txtFile.createNewFile();

					FileUtils.writeLines(txtFile, WebAppConst.FILE_ENCODING, specfQuesList, WebAppConst.CRLF);

					if (!SaitenFileUtil.isEmptyDirectory(downloadDir)) {

						SaitenFileUtil.createZipFileFromDirectory(downloadDir.getPath(),
								downloadDir.getPath() + WebAppConst.ZIP_FILE_EXTENSION);

						fileToDownload = downloadDir.getPath() + WebAppConst.ZIP_FILE_EXTENSION;
					}
					SaitenFileUtil.deleteDirectory(downloadDir);
				}
			}

			else if (dailyReports.equals(WebAppConst.CONFIRM_AND_WAIT_STATE)
					|| dailyReports.equals(WebAppConst.NOT_CONFIRM_AND_WAIT_STATE)) {

				File csvfile = null;
				confAndInspWaitCntList = new ArrayList<String>();
				List<String> questionCountList = new ArrayList<String>();
				for (String connectionString : connectionStringList) {

					confAndInspWaitCntList = tranDescScoreDAO.getConfirmAndInspectionWaitCount(dailyReports,
							connectionString);

					if (confAndInspWaitCntList != null && !confAndInspWaitCntList.isEmpty()) {
						questionCountList.addAll(confAndInspWaitCntList);
					}
				}

				if (questionCountList != null && !questionCountList.isEmpty()) {

					if (dailyReports.equals(WebAppConst.CONFIRM_AND_WAIT_STATE)) {

						downloadDir = SaitenFileUtil.createDirectory(downloadDirBasePath,
								textProvider
										.getText("daily.report.for.confirm.and.inspection.wait.zip.file.name.prefix")
										+ directoryName);
						csvfile = new File(downloadDir.getPath() + File.separator
								+ textProvider.getText("daily.report.for.confirm.and.inspection.wait.csv.prefix")
								+ WebAppConst.CSV_FILE_EXTENSION);

					} else if (dailyReports.equals(WebAppConst.NOT_CONFIRM_AND_WAIT_STATE)) {

						downloadDir = SaitenFileUtil.createDirectory(downloadDirBasePath,
								textProvider.getText(
										"daily.report.for.not.confirm.and.inspection.wait.zip.file.name.prefix")
										+ directoryName);
						csvfile = new File(downloadDir.getPath() + File.separator
								+ textProvider.getText("daily.report.for.not.confirm.and.inspection.wait.csv.prefix")
								+ WebAppConst.CSV_FILE_EXTENSION);
					}

					getDailyCSVReportHeaders(textProvider);

					getDailyCSVReportRows(questionCountList);

					FileUtils.writeLines(csvfile, WebAppConst.FILE_ENCODING, confAndInspWaitList, WebAppConst.CRLF);

					if (!SaitenFileUtil.isEmptyDirectory(downloadDir)) {

						SaitenFileUtil.createZipFileFromDirectory(downloadDir.getPath(),
								downloadDir.getPath() + WebAppConst.ZIP_FILE_EXTENSION);

						fileToDownload = downloadDir.getPath() + WebAppConst.ZIP_FILE_EXTENSION;
					}
					SaitenFileUtil.deleteDirectory(downloadDir);
				}
			}

			else if (dailyReports.equals(WebAppConst.LOGIN_LOGOUT_REPORT)) {

				List<String> tempList = new ArrayList<String>();
				scorerAccessLogList = new ArrayList<String>();

				scorerAccessLogList = tranScorerAccessLogDAO.loginLogoutReport(dailyReportCurrentDate);

				if (scorerAccessLogList != null && !scorerAccessLogList.isEmpty()) {

					tempList.addAll(scorerAccessLogList);
				}

				if (tempList != null && !tempList.isEmpty()) {
					downloadDir = SaitenFileUtil.createDirectory(downloadDirBasePath,
							textProvider.getText("daily.report.for.login.logout.zip.file.name.prefix") + directoryName);
					getHeadersForLoginLogoutReport(textProvider);

					getRowsForLoginLogoutReport(tempList);

					File csvfile = new File(downloadDir.getPath() + File.separator
							+ textProvider.getText("daily.report.for.login.logout.csv.file.name.prefix")
							+ WebAppConst.CSV_FILE_EXTENSION);
					FileUtils.writeLines(csvfile, WebAppConst.FILE_ENCODING, accessLogList, WebAppConst.CRLF);

					if (!SaitenFileUtil.isEmptyDirectory(downloadDir)) {

						SaitenFileUtil.createZipFileFromDirectory(downloadDir.getPath(),
								downloadDir.getPath() + WebAppConst.ZIP_FILE_EXTENSION);

						fileToDownload = downloadDir.getPath() + WebAppConst.ZIP_FILE_EXTENSION;
					}
					SaitenFileUtil.deleteDirectory(downloadDir);
				}
			}

			else if (dailyReports.equals(WebAppConst.STUD_COUNT_FOR_ALL_QUES_FOR_WG_ONLY)) {

				studCntListForAllQues = new ArrayList<String>();
				List<String> allQuestionsCountList = new ArrayList<String>();

				for (String connectionString : connectionStringList) {
					studCntListForAllQues = tranDescScoreDAO.getQuesStatewiseStudCountForAllQues(connectionString);

					if (studCntListForAllQues != null && !studCntListForAllQues.isEmpty()) {
						allQuestionsCountList.addAll(studCntListForAllQues);
					}
				}

				if (allQuestionsCountList != null && !allQuestionsCountList.isEmpty()) {
					downloadDir = SaitenFileUtil.createDirectory(downloadDirBasePath,
							textProvider.getText("daily.report.for.wg.only.for.all.ques.count.zip.prefix")
									+ directoryName);
					getReportHeadersForAllQues(textProvider);
					getReportRowsForAllQuesWGOnly(allQuestionsCountList);

					File txtFile = new File(downloadDir.getPath() + File.separator
							+ textProvider.getText("daily.report.for.wg.only.for.all.ques.count.txt.prefix")
							+ WebAppConst.TXT_FILE_EXTENSION);

					txtFile.createNewFile();

					FileUtils.writeLines(txtFile, WebAppConst.FILE_ENCODING, allQuesCountList, WebAppConst.CRLF);

					if (!SaitenFileUtil.isEmptyDirectory(downloadDir)) {

						SaitenFileUtil.createZipFileFromDirectory(downloadDir.getPath(),
								downloadDir.getPath() + WebAppConst.ZIP_FILE_EXTENSION);

						fileToDownload = downloadDir.getPath() + WebAppConst.ZIP_FILE_EXTENSION;
					}
					SaitenFileUtil.deleteDirectory(downloadDir);
				}

			}

			else if (dailyReports.equals(WebAppConst.GRADEWISE_COUNT_WHERE_GRADE_IS_AVAILABLE)) {

				List<String> gradeCountList = new ArrayList<String>();
				for (String connectionString : connectionStringList) {

					gradeWiseCountListForGradeAvailable = tranDescScoreDAO
							.questionSeqGradeCountWhereGradeIsAvailable(connectionString);

					if (gradeWiseCountListForGradeAvailable != null && !gradeWiseCountListForGradeAvailable.isEmpty()) {
						gradeCountList.addAll(gradeWiseCountListForGradeAvailable);
					}
				}

				if (!gradeCountList.isEmpty() && gradeCountList != null) {

					downloadDir = SaitenFileUtil.createDirectory(downloadDirBasePath,
							textProvider.getText("daily.report.for.gradewise.count.where.grade.is.available.zip.prefix")
									+ directoryName);
					getHeadersForGradeAvailable(textProvider);
					getReportRowsForGradeAvailability(gradeCountList);

					File csvFile = new File(downloadDir.getPath() + File.separator
							+ textProvider
									.getText("daily.report.for.gradewise.count.where.grade.is.available.csv.prefix")
							+ WebAppConst.CSV_FILE_EXTENSION);

					FileUtils.writeLines(csvFile, WebAppConst.FILE_ENCODING, gradeIsAvailablelist, WebAppConst.CRLF);

					if (!SaitenFileUtil.isEmptyDirectory(downloadDir)) {

						SaitenFileUtil.createZipFileFromDirectory(downloadDir.getPath(),
								downloadDir.getPath() + WebAppConst.ZIP_FILE_EXTENSION);

						fileToDownload = downloadDir.getPath() + WebAppConst.ZIP_FILE_EXTENSION;
					}
					SaitenFileUtil.deleteDirectory(downloadDir);

				}

			} else if (dailyReports.equals(WebAppConst.QUES_SEQ_WISE_COUNT_WHERE_PENDING_CATEGORY_IS_SET)) {

				List<String> pendingCategoryCountList = new ArrayList<String>();

				for (String connectionString : connectionStringList) {
					questionWiseCountListForPendCategory = tranDescScoreDAO
							.questionSeqWiseCountWherePendingCategorySet(connectionString);

					if (questionWiseCountListForPendCategory != null
							&& !questionWiseCountListForPendCategory.isEmpty()) {
						pendingCategoryCountList.addAll(questionWiseCountListForPendCategory);
					}
				}

				if (!pendingCategoryCountList.isEmpty() && pendingCategoryCountList != null) {

					downloadDir = SaitenFileUtil.createDirectory(downloadDirBasePath,
							textProvider.getText(
									"daily.report.for.question.wise.count.where.pending.category.is.set.zip.prefix")
									+ directoryName);
					getHeadersWherePendingCategorySet(textProvider);
					getReportRowsForPendingCategorySet(pendingCategoryCountList);

					File csvFile = new File(downloadDir.getPath() + File.separator
							+ textProvider.getText(
									"daily.report.for.question.wise.count.where.pending.category.is.set.csv.prefix")
							+ WebAppConst.CSV_FILE_EXTENSION);

					FileUtils.writeLines(csvFile, WebAppConst.FILE_ENCODING, pendingCategoryIsSetlist,
							WebAppConst.CRLF);

					if (!SaitenFileUtil.isEmptyDirectory(downloadDir)) {

						SaitenFileUtil.createZipFileFromDirectory(downloadDir.getPath(),
								downloadDir.getPath() + WebAppConst.ZIP_FILE_EXTENSION);

						fileToDownload = downloadDir.getPath() + WebAppConst.ZIP_FILE_EXTENSION;
					}
					SaitenFileUtil.deleteDirectory(downloadDir);
				}

			} else if (dailyReports.equals(WebAppConst.QUESTION_WISE_COUNT_FOR_HISTORY_RECORDS)) {

				List<String> dateAndTimeWiseQuesCountList = new ArrayList<String>();
				List<String> dateAndTimeQuesList = new ArrayList<String>();

				for (String connectionString : connectionStringList) {
					dateAndTimeWiseQuesCountList = tranDescScoreHistoryDAO
							.getDateAndTimeWiseQuestionCount(connectionString, dailyReportsInfo);

					if (dateAndTimeWiseQuesCountList != null && !dateAndTimeWiseQuesCountList.isEmpty()) {
						dateAndTimeQuesList.addAll(dateAndTimeWiseQuesCountList);
					}
				}

				if (dateAndTimeQuesList != null && !dateAndTimeQuesList.isEmpty()) {

					downloadDir = SaitenFileUtil.createDirectory(downloadDirBasePath,
							textProvider.getText("daily.report.zip.file.for.history.count") + directoryName);

					getHeadersForDateAndTimeWiseCount(textProvider);
					getRowsForDateAndTimeWiseCount(dateAndTimeQuesList);

					File csvFile = new File(downloadDir.getPath() + File.separator
							+ textProvider.getText("daily.report.csv.file.for.history.count")
							+ WebAppConst.CSV_FILE_EXTENSION);

					FileUtils.writeLines(csvFile, WebAppConst.FILE_ENCODING, dateTimeWiseCountList, WebAppConst.CRLF);

					if (!SaitenFileUtil.isEmptyDirectory(downloadDir)) {

						SaitenFileUtil.createZipFileFromDirectory(downloadDir.getPath(),
								downloadDir.getPath() + WebAppConst.ZIP_FILE_EXTENSION);

						fileToDownload = downloadDir.getPath() + WebAppConst.ZIP_FILE_EXTENSION;
					}
					SaitenFileUtil.deleteDirectory(downloadDir);
				}
			} else if (dailyReports.equals(WebAppConst.GRADE_WISE_OBJECTIVE_RECORDS)) {
				fileToDownload = getObjectiveReport(textProvider, downloadDirBasePath, downloadDir);
			}

		} catch (HibernateException he) {
			throw new SaitenRuntimeException(ErrorCode.DOWNLOAD_DAILY_REPORT_HIBERNATE_EXCEPTION, he);
		} catch (Exception e) {
			throw new SaitenRuntimeException(ErrorCode.DOWNLOAD_DAILY_REPORT_SERVICE_EXCEPTION, e);
		}
		return fileToDownload;
	}

	/**
	 * This method is used to get the objective record details from temp
	 * database, there could be multiple temp databases,for same
	 * questionSeq-markValue combination sum of count of records will displayed.
	 * 
	 * @param textProvider
	 * @param downloadDirBasePath
	 * @param downloadDir
	 */
	@SuppressWarnings("unused")
	private String getObjectiveReport(TextProvider textProvider, String downloadDirBasePath, File downloadDir) {
		List<Object> resultList = new ArrayList<Object>();
		List<Object> tempDBResultList = null;
		List<String> objectiveRecordList = null;
		List<String> tempDbConnectionList = SaitenUtil.getTempDbConnectionString();
		// map => key-question-seq&mark-value value-count of records
		Map<String, BigInteger> objResultMap = new LinkedHashMap<String, BigInteger>();

		for (String tempDBURL : tempDbConnectionList) {

			tempDBResultList = answerDetailDAO.getObjectiveData(tempDBURL, null);

			if (tempDBResultList != null) {

				for (Object record : tempDBResultList) {
					Object[] objRecord = (Object[]) record;
					if (objRecord != null) {
						StringBuilder key = new StringBuilder();
						key.append(objRecord[0]);// questionSeq
						key.append(WebAppConst.HYPHEN_STRING);
						key.append(objRecord[1]);// markValue

						if (!objResultMap.containsKey(key.toString())) {
							objResultMap.put(key.toString(), (BigInteger) objRecord[2]);
						} else {
							if (objResultMap.get(key.toString()) != null) {
								Integer reccount = objResultMap.get(key.toString()).intValue()
										+ ((BigInteger) objRecord[2]).intValue();
								objResultMap.put(key.toString(), new BigInteger((String.valueOf(reccount))));
							}

						}
					}
				}
				// resultList.addAll(tempDBResultList);
			}
		}
		objectiveRecordList = getRowsForObjectiveData(objResultMap, textProvider, objectiveRecordList);
		String fileToDownload = writeObjectiveReport(objectiveRecordList, textProvider, downloadDirBasePath,
				downloadDir);

		return fileToDownload;
	}

	/**
	 * This method is used to format the db result in csv format, it forms the
	 * column with comma seperated string value as a individual row in CSV
	 * format.
	 * 
	 * @param objResultMap
	 *            - map of obj records with key->qseq-markvalue and value->count
	 *            of records.
	 * @param textProvider
	 * @param objectiveRecordList
	 *            - new result list with csv data.
	 * @return list of string with headers and csv data.
	 */
	private List<String> getRowsForObjectiveData(Map<String, BigInteger> objResultMap, TextProvider textProvider,
			List<String> objectiveRecordList) {
		if (!objResultMap.isEmpty()) {
			objectiveRecordList = new ArrayList<String>();
			// First add the headers in list
			getHeadersForObjReport(textProvider, objectiveRecordList);
			// iterate the map and form csvlines.
			for (java.util.Map.Entry<String, BigInteger> entry : objResultMap.entrySet()) {
				StringBuilder csvLine = new StringBuilder();
				csvLine.append(WebAppConst.DOUBLE_QUOTES);
				csvLine.append(entry.getKey().split(WebAppConst.HYPHEN_STRING)[0]);
				csvLine.append(WebAppConst.DOUBLE_QUOTES);
				csvLine.append(WebAppConst.COMMA);
				csvLine.append(WebAppConst.DOUBLE_QUOTES);
				csvLine.append(entry.getKey().split(WebAppConst.HYPHEN_STRING)[1]);
				csvLine.append(WebAppConst.DOUBLE_QUOTES);
				csvLine.append(WebAppConst.COMMA);
				csvLine.append(WebAppConst.DOUBLE_QUOTES);
				csvLine.append(entry.getValue());
				csvLine.append(WebAppConst.DOUBLE_QUOTES);
				// data is formatted in CSV form and added in the list.
				objectiveRecordList.add(csvLine.toString());
			}
		}
		return objectiveRecordList;
	}

	/**
	 * Method is used to write the data (objective type records by
	 * questionSeq,Markvalue wise student count) in csv format.
	 * 
	 * @param objectiveRecordList
	 * @param textProvider
	 * @param downloadDirBasePath
	 * @param downloadDir
	 */
	private String writeObjectiveReport(List<String> objectiveRecordList, TextProvider textProvider,
			String downloadDirBasePath, File downloadDir) {

		String fileName = getObjectiveReportFileName(textProvider);

		downloadDir = SaitenFileUtil.createDirectory(downloadDirBasePath, fileName);
		String fileToDownload = null;
		try {
			File csvFile = new File(downloadDir.getPath() + File.separator + fileName + WebAppConst.CSV_FILE_EXTENSION);

			FileUtils.writeLines(csvFile, WebAppConst.FILE_ENCODING, objectiveRecordList, WebAppConst.CRLF);

			if (!SaitenFileUtil.isEmptyDirectory(downloadDir)) {

				SaitenFileUtil.createZipFileFromDirectory(downloadDir.getPath(),
						downloadDir.getPath() + WebAppConst.ZIP_FILE_EXTENSION);

				fileToDownload = downloadDir.getPath() + WebAppConst.ZIP_FILE_EXTENSION;
			}
			SaitenFileUtil.deleteDirectory(downloadDir);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileToDownload;
	}

	/**
	 * Method used to get file name for objective-questionSeq-Markvaluewise
	 * student count report, filename is like
	 * 'Objective_Question_MarkValuewise_Student_Count_20161013_1230'.
	 * 
	 * @param textProvider
	 * @return
	 */
	private String getObjectiveReportFileName(TextProvider textProvider) {

		String fileName = textProvider.getText("daily.report.for.objective.csv.file.name.prefix");
		fileName = fileName + WebAppConst.UNDERSCORE + getCurrentDateTime(WebAppConst.DATE_yyyyMMdd_HHmm);

		return fileName;
	}

	private void getReportRowsForPendingCategorySet(List<String> pendingCategoryCountList) {

		if (!pendingCategoryCountList.isEmpty()) {

			for (Object record : pendingCategoryCountList) {

				Object[] objectRecordArray = (Object[]) record;

				StringBuilder csvData = new StringBuilder();
				csvData.append(objectRecordArray[0]);
				csvData.append(WebAppConst.COMMA);
				csvData.append(objectRecordArray[1]);
				pendingCategoryIsSetlist.add(csvData.toString());
			}
		}
	}

	private void getHeadersWherePendingCategorySet(TextProvider textProvider) {
		StringBuilder csvHeaders = new StringBuilder();
		csvHeaders.append(textProvider.getText("label.question.seq"));
		csvHeaders.append(WebAppConst.COMMA);
		csvHeaders.append(textProvider.getText("label.count"));

		pendingCategoryIsSetlist.add(csvHeaders.toString());
	}

	private void getHeadersForGradeAvailable(TextProvider textProvider) {

		StringBuilder csvHeaders = new StringBuilder();
		csvHeaders.append(textProvider.getText("label.question.seq"));
		csvHeaders.append(WebAppConst.COMMA);
		csvHeaders.append(textProvider.getText("label.grade.num"));
		csvHeaders.append(WebAppConst.COMMA);
		csvHeaders.append(textProvider.getText("label.count"));

		gradeIsAvailablelist.add(csvHeaders.toString());
	}

	private void getReportRowsForGradeAvailability(List<String> gradeCountList) {
		if (!gradeCountList.isEmpty()) {

			for (Object record : gradeCountList) {

				Object[] objectRecordArray = (Object[]) record;

				StringBuilder csvData = new StringBuilder();
				csvData.append(objectRecordArray[0]);
				csvData.append(WebAppConst.COMMA);
				csvData.append(objectRecordArray[1]);
				csvData.append(WebAppConst.COMMA);
				csvData.append(objectRecordArray[2]);
				gradeIsAvailablelist.add(csvData.toString());
			}
		}

	}

	private void getRowsForLoginLogoutReport(List<String> tempList) {
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

	@SuppressWarnings("rawtypes")
	private void getReportRowsForSpecQues(List specQuesCountList) {
		if (!specQuesCountList.isEmpty()) {

			Map stateMap = SaitenUtil.latestScoringStatesMap();

			for (Object record : specQuesCountList) {

				Object[] objectRecordArray = (Object[]) record;

				StringBuilder csvData = new StringBuilder();

				csvData.append(objectRecordArray[0]);
				csvData.append(WebAppConst.COMMA);

				if (objectRecordArray[1] != null) {
					String stateValues = (String) stateMap.get((int) Short.parseShort(objectRecordArray[1].toString()));
					csvData.append(stateValues);
				}
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

	private void getDailyCSVReportRows(List<String> questionCountList) {

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
					String stateValues = (String) stateMap.get((int) Short.parseShort(objectRecordArray[1].toString()));
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

	@SuppressWarnings("rawtypes")
	private void getReportRowsForAllQuesWGOnly(List allQuestionsCountList) {
		if (!allQuestionsCountList.isEmpty()) {

			Map stateMap = SaitenUtil.latestScoringStatesMapForWg();

			for (Object record : allQuestionsCountList) {

				Object[] objectRecordArray = (Object[]) record;

				StringBuilder csvData = new StringBuilder();

				csvData.append(objectRecordArray[0]);
				csvData.append(WebAppConst.COMMA);

				if (objectRecordArray[1] != null) {
					String stateValues = (String) stateMap.get((int) Short.parseShort(objectRecordArray[1].toString()));
					csvData.append(stateValues);
				}
				csvData.append(WebAppConst.COMMA);
				csvData.append(objectRecordArray[2]);

				allQuesCountList.add(csvData.toString());
			}
		}
	}

	private void getRowsForDateAndTimeWiseCount(List<String> list) {
		if (!list.isEmpty()) {

			for (Object record : list) {
				Object[] objectRecordArray = (Object[]) record;
				StringBuilder csvData = new StringBuilder();

				csvData.append(objectRecordArray[0]);
				csvData.append(WebAppConst.COMMA);
				csvData.append(objectRecordArray[1]);
				csvData.append(WebAppConst.COMMA);
				csvData.append(objectRecordArray[2]);
				dateTimeWiseCountList.add(csvData.toString());
			}
		}
	}

	private void getHeadersForObjReport(TextProvider textProvider, List<String> objectiveRecordList) {

		StringBuilder csvHeaders = new StringBuilder();
		csvHeaders.append(textProvider.getText("label.question.seq"));
		csvHeaders.append(WebAppConst.COMMA);
		csvHeaders.append(textProvider.getText("label.markvalue"));
		csvHeaders.append(WebAppConst.COMMA);
		csvHeaders.append(textProvider.getText("label.count"));

		objectiveRecordList.add(csvHeaders.toString());
	}

	private void getHeadersForDateAndTimeWiseCount(TextProvider textProvider) {

		StringBuilder csvHeaders = new StringBuilder();
		csvHeaders.append(textProvider.getText("label.question.seq"));
		csvHeaders.append(WebAppConst.COMMA);
		csvHeaders.append(textProvider.getText("label.daily.report.scoring.state"));
		csvHeaders.append(WebAppConst.COMMA);
		csvHeaders.append(textProvider.getText("label.daily.report.count"));

		dateTimeWiseCountList.add(csvHeaders.toString());
	}

	private String getDirectoryName(String userID) {
		Date curDate = new Date();
		String pattern = "yyyyMMdd_HHmm";
		return getFormattedDateTimeStamp(curDate, pattern) + WebAppConst.UNDERSCORE + userID;
	}

	private String getFormattedDateTimeStamp(Date date, String format) {
		SimpleDateFormat sdfDate = new SimpleDateFormat(format);
		String strDate = sdfDate.format(date);
		return strDate;
	}

	private String getCurrentDateTime(String format) {
		Date currentDate = new Date();
		SimpleDateFormat sdfDate = new SimpleDateFormat(format);
		String dateTimeStr = sdfDate.format(currentDate);
		return dateTimeStr;
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

	public void setTranScorerAccessLogDAO(TranScorerAccessLogDAO tranScorerAccessLogDAO) {
		this.tranScorerAccessLogDAO = tranScorerAccessLogDAO;
	}

	public TranDescScoreHistoryDAO getTranDescScoreHistoryDAO() {
		return tranDescScoreHistoryDAO;
	}

	public void setTranDescScoreHistoryDAO(TranDescScoreHistoryDAO tranDescScoreHistoryDAO) {
		this.tranDescScoreHistoryDAO = tranDescScoreHistoryDAO;
	}

	public void setAnswerDetailDAO(AnswerDetailDAO answerDetailDAO) {
		this.answerDetailDAO = answerDetailDAO;
	}
}