package com.saiten.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.HibernateException;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.TextProvider;
import com.saiten.bean.SaitenConfig;
import com.saiten.dao.MstDbInstanceDAO;
import com.saiten.dao.TranDescScoreHistoryDAO;
import com.saiten.exception.SaitenRuntimeException;
import com.saiten.info.DailyScoreInfo;
import com.saiten.info.DownloadInfo;
import com.saiten.info.RatingInfo;
import com.saiten.model.MstDbInstance;
import com.saiten.model.MstQuestion;
import com.saiten.model.MstSubject;
import com.saiten.service.DownloadReportService;
import com.saiten.util.ErrorCode;
import com.saiten.util.SaitenFileUtil;
import com.saiten.util.SaitenUtil;
import com.saiten.util.WebAppConst;

/**
 * @author rajesh
 * @version 1.0
 * @created 26-Aug-2015
 */
public class DownloadReportServiceImpl implements DownloadReportService {

	private TranDescScoreHistoryDAO tranDescScoreHistoryDAO;
	private MstDbInstanceDAO mstDbInstanceDAO;

	@Override
	public String processDownloadRequest(DownloadInfo downloadInfo) {

		String fileToDownload = null;
		try {
			setTime(downloadInfo);

			TextProvider textProvider = (TextProvider) ActionContext
					.getContext().getActionInvocation().getAction();

			List<String> connectionStringList = new ArrayList<String>();

			List<MstDbInstance> mstDbInstanceList = mstDbInstanceDAO.findAll();
			for (MstDbInstance mstDbInstance : mstDbInstanceList) {
				connectionStringList.add(mstDbInstance.getConnectionString());
			}

			String downloadDirBasePath = textProvider.getTexts("application")
					.getString("saiten.download.daily.scoring.report.basepath");

			String directoryName = getDirectoryName(SaitenUtil
					.getLoggedinScorerId());

			File downloadDir;

			if (downloadInfo.getReportType().equals("daily")) {
				downloadDir = SaitenFileUtil.createDirectory(
						downloadDirBasePath,
						textProvider
								.getText("daily.report.zip.file.name.prefix")
								+ directoryName);

				if (downloadInfo.isRatingProgressReport()) {
					createDailyScoreCSVReport(connectionStringList,
							downloadInfo, downloadDir, textProvider);
				}
				if (downloadInfo.isDiscrepancyAnalysisReport()) {
					createDailyDiscrepancyCSVReport(connectionStringList,
							downloadInfo, downloadDir, textProvider);
				}
			} else {
				downloadDir = SaitenFileUtil.createDirectory(
						downloadDirBasePath,
						textProvider
								.getText("summary.report.zip.file.name.prefix")
								+ directoryName);

				createSummaryDiscrepancyCSVReport(connectionStringList,
						downloadInfo, downloadDir, textProvider);
			}

			if (!SaitenFileUtil.isEmptyDirectory(downloadDir)) {

				SaitenFileUtil.createZipFileFromDirectory(
						downloadDir.getPath(), downloadDir.getPath()
								+ WebAppConst.ZIP_FILE_EXTENSION);

				fileToDownload = downloadDir.getPath()
						+ WebAppConst.ZIP_FILE_EXTENSION;
			}

			SaitenFileUtil.deleteDirectory(downloadDir);

		} catch (HibernateException he) {
			throw new SaitenRuntimeException(
					ErrorCode.DOWNLOAD_DAILY_SCORING_SUMMARY_REPORT_HIBERNATE_EXCEPTION,
					he);
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.DOWNLOAD_DAILY_SCORING_SUMMARY_REPORT_REPORT_SERVICE_EXCEPTION,
					e);
		}

		return fileToDownload;
	}

	private void createDailyScoreCSVReport(List<String> connectionStringList,
			DownloadInfo downloadInfo, File downloadDir,
			TextProvider textProvider) throws Exception {
		String fromDateTime = downloadInfo.getStartDate()
				+ WebAppConst.SINGLE_SPACE + downloadInfo.getFromTime();
		String toDateTime = downloadInfo.getStartDate()
				+ WebAppConst.SINGLE_SPACE + downloadInfo.getToTime();

		// Get mstQuestionMap from saitenConfigObject
		LinkedHashMap<Integer, MstQuestion> mstQuestionMap = ((SaitenConfig) ServletActionContext
				.getServletContext().getAttribute("saitenConfigObject"))
				.getMstQuestionMap();

		List<String> csvDataList = new ArrayList<String>();
		List<Integer> questionSeqList = new ArrayList<Integer>();

		for (String connectionString : connectionStringList) {
			List<Integer> quesSeqList = tranDescScoreHistoryDAO
					.getDailyScoreQuesSeqInfo(connectionString, fromDateTime,
							toDateTime);
			if (quesSeqList.size() > 0) {
				questionSeqList.addAll(quesSeqList);
			}
		}

		if (questionSeqList.size() > 0) {

			createDailyScoreCSVHeader(csvDataList, downloadInfo,
					questionSeqList, mstQuestionMap, textProvider);

			DailyScoreInfo dailyScoreInfo = new DailyScoreInfo();
			Map<String, RatingInfo> scoreInfoMap = initializeScoreInfoMap(
					dailyScoreInfo, textProvider);

			for (String connectionString : connectionStringList) {
				DailyScoreInfo scoringInfo = tranDescScoreHistoryDAO
						.getDailyScoreInfo(connectionString, fromDateTime,
								toDateTime);
				if (scoringInfo != null) {
					buildScoreInfoMap(scoringInfo, scoreInfoMap,
							connectionString, fromDateTime, toDateTime,
							textProvider);
				}
			}

			createDailyScoreCSVRows(csvDataList, questionSeqList,
					mstQuestionMap, textProvider, scoreInfoMap);

			File csvfile = new File(
					downloadDir.getPath()
							+ File.separator
							+ textProvider
									.getText("daily.rating.progress.data.report.csv.prefix")
							+ downloadInfo.getStartDate().replace(
									WebAppConst.HYPHEN_STRING,
									WebAppConst.EMPTY_STRING)
							+ WebAppConst.UNDERSCORE
							+ downloadInfo.getFromTime()
									.replace(WebAppConst.COLON,
											WebAppConst.EMPTY_STRING)
							+ WebAppConst.UNDERSCORE
							+ downloadInfo.getToTime()
									.replace(WebAppConst.COLON,
											WebAppConst.EMPTY_STRING)
							+ WebAppConst.CSV_FILE_EXTENSION);
			FileUtils.writeLines(csvfile, WebAppConst.FILE_ENCODING,
					csvDataList, WebAppConst.CRLF);
		}
	}

	private void createDailyDiscrepancyCSVReport(
			List<String> connectionStringList, DownloadInfo downloadInfo,
			File downloadDir, TextProvider textProvider) throws Exception {

		boolean isDataAvailable = false;
		String fromDateTime = downloadInfo.getStartDate()
				+ WebAppConst.SINGLE_SPACE + downloadInfo.getFromTime();
		String toDateTime = downloadInfo.getStartDate()
				+ WebAppConst.SINGLE_SPACE + downloadInfo.getToTime();

		// Get mstQuestionMap from saitenConfigObject
		LinkedHashMap<Integer, MstQuestion> mstQuestionMap = ((SaitenConfig) ServletActionContext
				.getServletContext().getAttribute("saitenConfigObject"))
				.getMstQuestionMap();

		List<String> csvDataList = new ArrayList<String>();
		createDailyDiscrepancyCSVHeader(csvDataList, downloadInfo, textProvider);

		for (String connectionString : connectionStringList) {
			List<Object[]> discrepancyInfoList = tranDescScoreHistoryDAO
					.getDailyDiscrepancyAnalysisData(connectionString,
							fromDateTime, toDateTime);
			if (discrepancyInfoList.size() > 0) {
				isDataAvailable = true;
				createDailyDiscrepancyCSVRows(csvDataList, discrepancyInfoList,
						mstQuestionMap);
			}
		}

		if (isDataAvailable) {
			File csvfile = new File(
					downloadDir.getPath()
							+ File.separator
							+ textProvider
									.getText("daily.discrepancy.analysis.report.csv.prefix")
							+ downloadInfo.getStartDate().replace(
									WebAppConst.HYPHEN_STRING,
									WebAppConst.EMPTY_STRING)
							+ WebAppConst.UNDERSCORE
							+ downloadInfo.getFromTime()
									.replace(WebAppConst.COLON,
											WebAppConst.EMPTY_STRING)
							+ WebAppConst.UNDERSCORE
							+ downloadInfo.getToTime()
									.replace(WebAppConst.COLON,
											WebAppConst.EMPTY_STRING)
							+ WebAppConst.CSV_FILE_EXTENSION);
			FileUtils.writeLines(csvfile, WebAppConst.FILE_ENCODING,
					csvDataList, WebAppConst.CRLF);
		}
	}

	private void createSummaryDiscrepancyCSVReport(
			List<String> connectionStringList, DownloadInfo downloadInfo,
			File downloadDir, TextProvider textProvider) throws Exception {
		boolean isDataAvailable = false;
		String fromDateTime = downloadInfo.getStartDate()
				+ WebAppConst.SINGLE_SPACE + downloadInfo.getFromTime();
		String toDateTime = downloadInfo.getEndDate()
				+ WebAppConst.SINGLE_SPACE + downloadInfo.getToTime();

		List<String> csvDataList = new ArrayList<String>();
		Map<String, RatingInfo> summaryReportMap = new LinkedHashMap<String, RatingInfo>();
		createSummaryDiscrepancyCSVHeader(csvDataList, downloadInfo,
				textProvider);

		for (String connectionString : connectionStringList) {
			List<RatingInfo> discrepancyInfoList = tranDescScoreHistoryDAO
					.getSummaryDiscrepancyAnalysisData(connectionString,
							fromDateTime, toDateTime);
			if (discrepancyInfoList.size() > 0) {
				isDataAvailable = true;
				buildSummaryReportMap(discrepancyInfoList, summaryReportMap);
			}
		}

		if (isDataAvailable) {

			createSummaryDiscrepancyCSVRows(csvDataList, summaryReportMap);

			File csvfile = new File(
					downloadDir.getPath()
							+ File.separator
							+ textProvider
									.getText("summary.discrepancy.analysis.report.csv.prefix")
							+ downloadInfo.getStartDate().replace(
									WebAppConst.HYPHEN_STRING,
									WebAppConst.EMPTY_STRING)
							+ WebAppConst.UNDERSCORE
							+ downloadInfo.getEndDate().replace(
									WebAppConst.HYPHEN_STRING,
									WebAppConst.EMPTY_STRING)
							+ WebAppConst.UNDERSCORE
							+ downloadInfo.getFromTime()
									.replace(WebAppConst.COLON,
											WebAppConst.EMPTY_STRING)
							+ WebAppConst.UNDERSCORE
							+ downloadInfo.getToTime()
									.replace(WebAppConst.COLON,
											WebAppConst.EMPTY_STRING)
							+ WebAppConst.CSV_FILE_EXTENSION);
			FileUtils.writeLines(csvfile, WebAppConst.FILE_ENCODING,
					csvDataList, WebAppConst.CRLF);
		}
	}

	private void createDailyScoreCSVHeader(List<String> csvDataList,
			DownloadInfo downloadInfo, List<Integer> questionSeqList,
			LinkedHashMap<Integer, MstQuestion> mstQuestionMap,
			TextProvider textProvider) {
		csvDataList.add(downloadInfo.getStartDate());
		csvDataList.add(downloadInfo.getFromTime() + WebAppConst.TILDE
				+ downloadInfo.getToTime());
		StringBuilder csvHeader = new StringBuilder();
		csvHeader.append(textProvider.getText("label.description"));
		csvHeader.append(WebAppConst.COMMA);
		csvHeader.append(textProvider.getText("label.total"));
		csvHeader.append(WebAppConst.COMMA);
		csvHeader.append(textProvider.getText("label.speaking.total"));
		csvHeader.append(WebAppConst.COMMA);
		csvHeader.append(textProvider.getText("label.writing.total"));
		csvHeader.append(WebAppConst.COMMA);
		for (Integer questionSeq : questionSeqList) {
			csvHeader.append(getSubCodeAndQuesNumByQuesSeq(mstQuestionMap,
					questionSeq));
			csvHeader.append(WebAppConst.COMMA);
		}
		csvDataList.add(csvHeader.substring(0,
				csvHeader.lastIndexOf(WebAppConst.COMMA)));
	}

	private void createDailyScoreCSVRows(List<String> csvDataList,
			List<Integer> questionSeqList,
			LinkedHashMap<Integer, MstQuestion> mstQuestionMap,
			TextProvider textProvider, Map<String, RatingInfo> resultMap) {
		for (Entry<String, RatingInfo> entry : resultMap.entrySet()) {
			String description = entry.getKey();
			RatingInfo ratingInfo = entry.getValue();
			StringBuilder csvData = new StringBuilder();
			csvData.append(description);
			csvData.append(WebAppConst.COMMA);
			csvData.append(ratingInfo.getDailyScoreSpkTotalCount()
					+ ratingInfo.getDailyScoreWrtTotalCount());
			csvData.append(WebAppConst.COMMA);
			csvData.append(ratingInfo.getDailyScoreSpkTotalCount());
			csvData.append(WebAppConst.COMMA);
			csvData.append(ratingInfo.getDailyScoreWrtTotalCount());
			csvData.append(WebAppConst.COMMA);

			Map<Integer, Integer> quesSeqWiseCntMap = ratingInfo
					.getQuesSeqWiseCountMap();
			for (Integer questionSeq : questionSeqList) {
				if (quesSeqWiseCntMap != null) {
					csvData.append(quesSeqWiseCntMap.containsKey(questionSeq) ? quesSeqWiseCntMap
							.get(questionSeq) : WebAppConst.EMPTY_STRING);
				} else {
					csvData.append(WebAppConst.EMPTY_STRING);
				}
				csvData.append(WebAppConst.COMMA);
			}
			csvDataList.add(csvData.substring(0,
					csvData.lastIndexOf(WebAppConst.COMMA)));
		}
	}

	private void createDailyDiscrepancyCSVHeader(List<String> csvDataList,
			DownloadInfo downloadInfo, TextProvider textProvider) {
		csvDataList.add(downloadInfo.getStartDate());
		csvDataList.add(downloadInfo.getFromTime() + WebAppConst.TILDE
				+ downloadInfo.getToTime());
		StringBuilder csvHeader = new StringBuilder();
		csvHeader.append(textProvider.getText("label.scorerId"));
		csvHeader.append(WebAppConst.COMMA);
		csvHeader.append(textProvider.getText("label.subCodeQuesNum"));
		csvHeader.append(WebAppConst.COMMA);
		csvHeader.append(textProvider.getText("label.score.count"));
		csvHeader.append(WebAppConst.COMMA);
		csvHeader.append(textProvider.getText("label.discrepancy.count"));
		csvHeader.append(WebAppConst.COMMA);
		csvHeader.append(textProvider.getText("label.percentage"));
		csvDataList.add(csvHeader.toString());
	}

	private void createDailyDiscrepancyCSVRows(List<String> csvDataList,
			List<Object[]> discrepancyInfoList,
			LinkedHashMap<Integer, MstQuestion> mstQuestionMap) {
		for (Object[] discrepancyInfo : discrepancyInfoList) {
			StringBuilder csvData = new StringBuilder();
			csvData.append(discrepancyInfo[0]);
			csvData.append(WebAppConst.COMMA);
			csvData.append(getSubCodeAndQuesNumByQuesSeq(mstQuestionMap,
					(Integer) discrepancyInfo[1]));
			csvData.append(WebAppConst.COMMA);
			csvData.append(discrepancyInfo[2]);
			csvData.append(WebAppConst.COMMA);
			csvData.append(discrepancyInfo[3]);
			csvData.append(WebAppConst.COMMA);
			csvData.append(discrepancyInfo[4]);
			csvDataList.add(csvData.toString());
		}
	}

	private void createSummaryDiscrepancyCSVHeader(List<String> csvDataList,
			DownloadInfo downloadInfo, TextProvider textProvider) {
		csvDataList.add(downloadInfo.getStartDate() + WebAppConst.TILDE
				+ downloadInfo.getEndDate());
		csvDataList.add(downloadInfo.getFromTime() + WebAppConst.TILDE
				+ downloadInfo.getToTime());
		StringBuilder csvHeader = new StringBuilder();
		csvHeader.append(textProvider.getText("label.scorerId"));
		csvHeader.append(WebAppConst.COMMA);
		csvHeader.append(textProvider.getText("label.question.type"));
		csvHeader.append(WebAppConst.COMMA);
		csvHeader.append(textProvider.getText("label.total.rating"));
		csvHeader.append(WebAppConst.COMMA);
		csvHeader.append(textProvider.getText("label.total.discrepancy.count"));
		csvHeader.append(WebAppConst.COMMA);
		csvHeader.append(textProvider
				.getText("label.total.discrepancy.percentage"));
		csvHeader.append(WebAppConst.COMMA);
		csvHeader.append(textProvider.getText("label.match.count"));
		csvHeader.append(WebAppConst.COMMA);
		csvHeader.append(textProvider.getText("label.match.percentage"));
		csvHeader.append(WebAppConst.COMMA);
		csvHeader.append(textProvider.getText("label.discrepancy.count"));
		csvHeader.append(WebAppConst.COMMA);
		csvHeader.append(textProvider.getText("label.discrepancy.percentage"));
		csvDataList.add(csvHeader.toString());
	}

	private void buildSummaryReportMap(List<RatingInfo> discrepancyInfoList,
			Map<String, RatingInfo> summaryReportMap) {
		for (RatingInfo discrepancyInfo : discrepancyInfoList) {
			String scorerId = discrepancyInfo.getScorerId();
			String questionType = discrepancyInfo.getQuestionType();
			String key = questionType + WebAppConst.HYPHEN + scorerId;
			if (summaryReportMap.containsKey(key)) {
				RatingInfo discrepancyRatingInfo = summaryReportMap.get(key);
				discrepancyRatingInfo.setTotalRating(discrepancyRatingInfo
						.getTotalRating() + discrepancyInfo.getTotalRating());
				discrepancyRatingInfo
						.setTotalDiscrepancyCount(discrepancyRatingInfo
								.getTotalDiscrepancyCount()
								+ discrepancyInfo.getTotalDiscrepancyCount());
				discrepancyRatingInfo.setMatchCount(discrepancyRatingInfo
						.getMatchCount() + discrepancyInfo.getMatchCount());
				discrepancyRatingInfo.setDiscrepancyCount(discrepancyRatingInfo
						.getDiscrepancyCount()
						+ discrepancyInfo.getDiscrepancyCount());
			} else {
				summaryReportMap.put(key, discrepancyInfo);
			}
		}
	}

	private void createSummaryDiscrepancyCSVRows(List<String> csvDataList,
			Map<String, RatingInfo> summaryReportMap) {
		for (RatingInfo discrepancyInfo : summaryReportMap.values()) {
			StringBuilder csvData = new StringBuilder();
			csvData.append(discrepancyInfo.getScorerId());
			csvData.append(WebAppConst.COMMA);
			csvData.append(discrepancyInfo.getQuestionType());
			csvData.append(WebAppConst.COMMA);
			csvData.append(discrepancyInfo.getTotalRating());
			csvData.append(WebAppConst.COMMA);
			csvData.append(discrepancyInfo.getTotalDiscrepancyCount());
			csvData.append(WebAppConst.COMMA);

			int totalRating = discrepancyInfo.getTotalRating();
			int totalDiscrepancyCount = discrepancyInfo
					.getTotalDiscrepancyCount();
			int matchCount = discrepancyInfo.getMatchCount();
			int discrepancyCount = discrepancyInfo.getDiscrepancyCount();

			csvData.append(String.format(WebAppConst.PERCENTAGE_FORMAT,
					(float) totalDiscrepancyCount * 100 / totalRating));
			csvData.append(WebAppConst.COMMA);

			csvData.append(matchCount);
			csvData.append(WebAppConst.COMMA);

			csvData.append(String.format(WebAppConst.PERCENTAGE_FORMAT,
					(float) matchCount * 100 / totalRating));
			csvData.append(WebAppConst.COMMA);

			csvData.append(discrepancyCount);
			csvData.append(WebAppConst.COMMA);

			csvData.append(String.format(WebAppConst.PERCENTAGE_FORMAT,
					(float) discrepancyCount * 100 / totalRating));
			csvDataList.add(csvData.toString());
		}
	}

	private String getSubCodeAndQuesNumByQuesSeq(
			LinkedHashMap<Integer, MstQuestion> mstQuestionMap,
			Integer questionSeq) {
		MstQuestion mstQuestion = mstQuestionMap.get(questionSeq);
		if (mstQuestion != null) {
			MstSubject mstSubject = mstQuestion.getMstSubject();
			if (mstSubject != null) {
				return mstSubject.getSubjectCode()
						+ mstQuestion.getQuestionNum();
			}
		}
		return null;
	}

	private Map<String, RatingInfo> initializeScoreInfoMap(
			DailyScoreInfo dailyScoreInfo, TextProvider textProvider) {

		Map<String, RatingInfo> scoreInfoMap = new LinkedHashMap<String, RatingInfo>();
		scoreInfoMap.put(
				textProvider.getText(WebAppConst.FIRST_RTG_SECOND_WAIT_KEY),
				new RatingInfo());
		scoreInfoMap.put(
				textProvider.getText(WebAppConst.FIRST_RTG_PENDING_KEY),
				new RatingInfo());
		scoreInfoMap.put(
				textProvider.getText(WebAppConst.SECOND_RTG_COMPLETE_KEY),
				new RatingInfo());
		scoreInfoMap.put(
				textProvider.getText(WebAppConst.SECOND_RTG_PENDING_KEY),
				new RatingInfo());
		scoreInfoMap.put(
				textProvider.getText(WebAppConst.SECOND_RTG_MISMATCH_KEY),
				new RatingInfo());
		scoreInfoMap
				.put(textProvider
						.getText(WebAppConst.PENDING01_RTG_SECOND_WAIT_KEY),
						new RatingInfo());
		scoreInfoMap.put(
				textProvider.getText(WebAppConst.PENDING01_RTG_PENDING_KEY),
				new RatingInfo());
		scoreInfoMap.put(
				textProvider.getText(WebAppConst.PENDING01_RTG_COMPLETE_KEY),
				new RatingInfo());
		scoreInfoMap.put(
				textProvider.getText(WebAppConst.PENDING01_RTG_MISMATCH_KEY),
				new RatingInfo());
		scoreInfoMap
				.put(textProvider
						.getText(WebAppConst.PENDING02_RTG_SECOND_WAIT_KEY),
						new RatingInfo());
		scoreInfoMap.put(
				textProvider.getText(WebAppConst.PENDING02_RTG_PENDING_KEY),
				new RatingInfo());
		scoreInfoMap.put(
				textProvider.getText(WebAppConst.PENDING02_RTG_COMPLETE_KEY),
				new RatingInfo());
		scoreInfoMap.put(
				textProvider.getText(WebAppConst.PENDING02_RTG_MISMATCH_KEY),
				new RatingInfo());
		scoreInfoMap
				.put(textProvider
						.getText(WebAppConst.PENDING03_RTG_SECOND_WAIT_KEY),
						new RatingInfo());
		scoreInfoMap.put(
				textProvider.getText(WebAppConst.PENDING03_RTG_PENDING_KEY),
				new RatingInfo());
		scoreInfoMap.put(
				textProvider.getText(WebAppConst.PENDING03_RTG_COMPLETE_KEY),
				new RatingInfo());
		scoreInfoMap.put(
				textProvider.getText(WebAppConst.PENDING03_RTG_MISMATCH_KEY),
				new RatingInfo());
		scoreInfoMap
				.put(textProvider
						.getText(WebAppConst.PENDING04_RTG_SECOND_WAIT_KEY),
						new RatingInfo());
		scoreInfoMap.put(
				textProvider.getText(WebAppConst.PENDING04_RTG_PENDING_KEY),
				new RatingInfo());
		scoreInfoMap.put(
				textProvider.getText(WebAppConst.PENDING04_RTG_COMPLETE_KEY),
				new RatingInfo());
		scoreInfoMap.put(
				textProvider.getText(WebAppConst.PENDING04_RTG_MISMATCH_KEY),
				new RatingInfo());
		scoreInfoMap.put(
				textProvider.getText(WebAppConst.DEFECTIVE_SCRIPT_COUNT_KEY),
				new RatingInfo());
		scoreInfoMap
				.put(textProvider
						.getText(WebAppConst.PENDING05_RTG_SECOND_WAIT_KEY),
						new RatingInfo());
		scoreInfoMap.put(
				textProvider.getText(WebAppConst.PENDING05_RTG_PENDING_KEY),
				new RatingInfo());
		scoreInfoMap.put(
				textProvider.getText(WebAppConst.PENDING05_RTG_COMPLETE_KEY),
				new RatingInfo());
		scoreInfoMap.put(
				textProvider.getText(WebAppConst.PENDING05_RTG_MISMATCH_KEY),
				new RatingInfo());
		scoreInfoMap.put(
				textProvider.getText(WebAppConst.MISMATCH_RTG_PENDING_KEY),
				new RatingInfo());
		scoreInfoMap.put(
				textProvider.getText(WebAppConst.MISMATCH_RTG_COMPLETE_KEY),
				new RatingInfo());

		return scoreInfoMap;
	}

	private void buildScoreInfoMap(DailyScoreInfo scoringInfo,
			Map<String, RatingInfo> scoreInfoMap, String connectionString,
			String fromDateTime, String toDateTime, TextProvider textProvider) {

		if (isEditedParam(scoringInfo.getFirstRtgSecondWaitSpkCnt())
				|| isEditedParam(scoringInfo.getFirstRtgSecondWaitWrtCnt())) {
			bindSpeakingkAndWritingCount(scoreInfoMap.get(textProvider
					.getText(WebAppConst.FIRST_RTG_SECOND_WAIT_KEY)),
					connectionString, fromDateTime, toDateTime,
					scoringInfo.getFirstRtgSecondWaitSpkCnt(),
					scoringInfo.getFirstRtgSecondWaitWrtCnt(),
					WebAppConst.FIRST_RTG_SECOND_WAIT, null);
		}

		if (isEditedParam(scoringInfo.getFirstRtgPendingSpkCnt())
				|| isEditedParam(scoringInfo.getFirstRtgPendingWrtCnt())) {
			bindSpeakingkAndWritingCount(scoreInfoMap.get(textProvider
					.getText(WebAppConst.FIRST_RTG_PENDING_KEY)),
					connectionString, fromDateTime, toDateTime,
					scoringInfo.getFirstRtgPendingSpkCnt(),
					scoringInfo.getFirstRtgPendingWrtCnt(),
					WebAppConst.FIRST_RTG_PENDING, null);
		}

		if (isEditedParam(scoringInfo.getSecondRtgCompleteSpkCnt())
				|| isEditedParam(scoringInfo.getSecondRtgCompleteWrtCnt())) {
			bindSpeakingkAndWritingCount(scoreInfoMap.get(textProvider
					.getText(WebAppConst.SECOND_RTG_COMPLETE_KEY)),
					connectionString, fromDateTime, toDateTime,
					scoringInfo.getSecondRtgCompleteSpkCnt(),
					scoringInfo.getSecondRtgCompleteWrtCnt(),
					WebAppConst.SECOND_RTG_COMPLETE, null);
		}

		if (isEditedParam(scoringInfo.getSecondRtgPendingSpkCnt())
				|| isEditedParam(scoringInfo.getSecondRtgPendingWrtCnt())) {
			bindSpeakingkAndWritingCount(scoreInfoMap.get(textProvider
					.getText(WebAppConst.SECOND_RTG_PENDING_KEY)),
					connectionString, fromDateTime, toDateTime,
					scoringInfo.getSecondRtgPendingSpkCnt(),
					scoringInfo.getSecondRtgPendingWrtCnt(),
					WebAppConst.SECOND_RTG_PENDING, null);
		}

		if (isEditedParam(scoringInfo.getSecondRtgMismatchSpkCnt())
				|| isEditedParam(scoringInfo.getSecondRtgMismatchWrtCnt())) {
			bindSpeakingkAndWritingCount(scoreInfoMap.get(textProvider
					.getText(WebAppConst.SECOND_RTG_MISMATCH_KEY)),
					connectionString, fromDateTime, toDateTime,
					scoringInfo.getSecondRtgMismatchSpkCnt(),
					scoringInfo.getSecondRtgMismatchWrtCnt(),
					WebAppConst.SECOND_RTG_MISMATCH, null);
		}

		if (isEditedParam(scoringInfo.getPending01SecondWaitSpkCnt())
				|| isEditedParam(scoringInfo.getPending01SecondWaitWrtCnt())) {
			bindSpeakingkAndWritingCount(scoreInfoMap.get(textProvider
					.getText(WebAppConst.PENDING01_RTG_SECOND_WAIT_KEY)),
					connectionString, fromDateTime, toDateTime,
					scoringInfo.getPending01SecondWaitSpkCnt(),
					scoringInfo.getPending01SecondWaitWrtCnt(),
					WebAppConst.PENDING_RTG_SECOND_WAIT,
					WebAppConst.PEND_CATE_01);
		}

		if (isEditedParam(scoringInfo.getPending01PendingSpkCnt())
				|| isEditedParam(scoringInfo.getPending01PendingWrtCnt())) {
			bindSpeakingkAndWritingCount(scoreInfoMap.get(textProvider
					.getText(WebAppConst.PENDING01_RTG_PENDING_KEY)),
					connectionString, fromDateTime, toDateTime,
					scoringInfo.getPending01PendingSpkCnt(),
					scoringInfo.getPending01PendingWrtCnt(),
					WebAppConst.PENDING_RTG_PENDING, WebAppConst.PEND_CATE_01);
		}

		if (isEditedParam(scoringInfo.getPending01CompleteSpkCnt())
				|| isEditedParam(scoringInfo.getPending01CompleteWrtCnt())) {
			bindSpeakingkAndWritingCount(scoreInfoMap.get(textProvider
					.getText(WebAppConst.PENDING01_RTG_COMPLETE_KEY)),
					connectionString, fromDateTime, toDateTime,
					scoringInfo.getPending01CompleteSpkCnt(),
					scoringInfo.getPending01CompleteWrtCnt(),
					WebAppConst.PENDING_RTG_COMPLETE, WebAppConst.PEND_CATE_01);
		}

		if (isEditedParam(scoringInfo.getPending01MismatchSpkCnt())
				|| isEditedParam(scoringInfo.getPending01MismatchWrtCnt())) {
			bindSpeakingkAndWritingCount(scoreInfoMap.get(textProvider
					.getText(WebAppConst.PENDING01_RTG_MISMATCH_KEY)),
					connectionString, fromDateTime, toDateTime,
					scoringInfo.getPending01MismatchSpkCnt(),
					scoringInfo.getPending01MismatchWrtCnt(),
					WebAppConst.PENDING_RTG_MISMATCH, WebAppConst.PEND_CATE_01);
		}

		if (isEditedParam(scoringInfo.getPending02SecondWaitSpkCnt())
				|| isEditedParam(scoringInfo.getPending02SecondWaitWrtCnt())) {
			bindSpeakingkAndWritingCount(scoreInfoMap.get(textProvider
					.getText(WebAppConst.PENDING02_RTG_SECOND_WAIT_KEY)),
					connectionString, fromDateTime, toDateTime,
					scoringInfo.getPending02SecondWaitSpkCnt(),
					scoringInfo.getPending02SecondWaitWrtCnt(),
					WebAppConst.PENDING_RTG_SECOND_WAIT,
					WebAppConst.PEND_CATE_02);
		}

		if (isEditedParam(scoringInfo.getPending02PendingSpkCnt())
				|| isEditedParam(scoringInfo.getPending02PendingWrtCnt())) {
			bindSpeakingkAndWritingCount(scoreInfoMap.get(textProvider
					.getText(WebAppConst.PENDING02_RTG_PENDING_KEY)),
					connectionString, fromDateTime, toDateTime,
					scoringInfo.getPending02PendingSpkCnt(),
					scoringInfo.getPending02PendingWrtCnt(),
					WebAppConst.PENDING_RTG_PENDING, WebAppConst.PEND_CATE_02);
		}

		if (isEditedParam(scoringInfo.getPending02CompleteSpkCnt())
				|| isEditedParam(scoringInfo.getPending02CompleteWrtCnt())) {
			bindSpeakingkAndWritingCount(scoreInfoMap.get(textProvider
					.getText(WebAppConst.PENDING02_RTG_COMPLETE_KEY)),
					connectionString, fromDateTime, toDateTime,
					scoringInfo.getPending02CompleteSpkCnt(),
					scoringInfo.getPending02CompleteWrtCnt(),
					WebAppConst.PENDING_RTG_COMPLETE, WebAppConst.PEND_CATE_02);
		}

		if (isEditedParam(scoringInfo.getPending02MismatchSpkCnt())
				|| isEditedParam(scoringInfo.getPending02MismatchWrtCnt())) {
			bindSpeakingkAndWritingCount(scoreInfoMap.get(textProvider
					.getText(WebAppConst.PENDING02_RTG_MISMATCH_KEY)),
					connectionString, fromDateTime, toDateTime,
					scoringInfo.getPending02MismatchSpkCnt(),
					scoringInfo.getPending02MismatchWrtCnt(),
					WebAppConst.PENDING_RTG_MISMATCH, WebAppConst.PEND_CATE_02);
		}

		if (isEditedParam(scoringInfo.getPending03SecondWaitSpkCnt())
				|| isEditedParam(scoringInfo.getPending03SecondWaitWrtCnt())) {
			bindSpeakingkAndWritingCount(scoreInfoMap.get(textProvider
					.getText(WebAppConst.PENDING03_RTG_SECOND_WAIT_KEY)),
					connectionString, fromDateTime, toDateTime,
					scoringInfo.getPending03SecondWaitSpkCnt(),
					scoringInfo.getPending03SecondWaitWrtCnt(),
					WebAppConst.PENDING_RTG_SECOND_WAIT,
					WebAppConst.PEND_CATE_03);
		}

		if (isEditedParam(scoringInfo.getPending03PendingSpkCnt())
				|| isEditedParam(scoringInfo.getPending03PendingWrtCnt())) {
			bindSpeakingkAndWritingCount(scoreInfoMap.get(textProvider
					.getText(WebAppConst.PENDING03_RTG_PENDING_KEY)),
					connectionString, fromDateTime, toDateTime,
					scoringInfo.getPending03PendingSpkCnt(),
					scoringInfo.getPending03PendingWrtCnt(),
					WebAppConst.PENDING_RTG_PENDING, WebAppConst.PEND_CATE_03);
		}

		if (isEditedParam(scoringInfo.getPending03CompleteSpkCnt())
				|| isEditedParam(scoringInfo.getPending03CompleteWrtCnt())) {
			bindSpeakingkAndWritingCount(scoreInfoMap.get(textProvider
					.getText(WebAppConst.PENDING03_RTG_COMPLETE_KEY)),
					connectionString, fromDateTime, toDateTime,
					scoringInfo.getPending03CompleteSpkCnt(),
					scoringInfo.getPending03CompleteWrtCnt(),
					WebAppConst.PENDING_RTG_COMPLETE, WebAppConst.PEND_CATE_03);
		}

		if (isEditedParam(scoringInfo.getPending03MismatchSpkCnt())
				|| isEditedParam(scoringInfo.getPending03MismatchWrtCnt())) {
			bindSpeakingkAndWritingCount(scoreInfoMap.get(textProvider
					.getText(WebAppConst.PENDING03_RTG_MISMATCH_KEY)),
					connectionString, fromDateTime, toDateTime,
					scoringInfo.getPending03MismatchSpkCnt(),
					scoringInfo.getPending03MismatchWrtCnt(),
					WebAppConst.PENDING_RTG_MISMATCH, WebAppConst.PEND_CATE_03);
		}

		if (isEditedParam(scoringInfo.getPending04SecondWaitSpkCnt())
				|| isEditedParam(scoringInfo.getPending04SecondWaitWrtCnt())) {
			bindSpeakingkAndWritingCount(scoreInfoMap.get(textProvider
					.getText(WebAppConst.PENDING04_RTG_SECOND_WAIT_KEY)),
					connectionString, fromDateTime, toDateTime,
					scoringInfo.getPending04SecondWaitSpkCnt(),
					scoringInfo.getPending04SecondWaitWrtCnt(),
					WebAppConst.PENDING_RTG_SECOND_WAIT,
					WebAppConst.PEND_CATE_04);
		}

		if (isEditedParam(scoringInfo.getPending04PendingSpkCnt())
				|| isEditedParam(scoringInfo.getPending04PendingWrtCnt())) {
			bindSpeakingkAndWritingCount(scoreInfoMap.get(textProvider
					.getText(WebAppConst.PENDING04_RTG_PENDING_KEY)),
					connectionString, fromDateTime, toDateTime,
					scoringInfo.getPending04PendingSpkCnt(),
					scoringInfo.getPending04PendingWrtCnt(),
					WebAppConst.PENDING_RTG_PENDING, WebAppConst.PEND_CATE_04);
		}

		if (isEditedParam(scoringInfo.getPending04CompleteSpkCnt())
				|| isEditedParam(scoringInfo.getPending04CompleteWrtCnt())) {
			bindSpeakingkAndWritingCount(scoreInfoMap.get(textProvider
					.getText(WebAppConst.PENDING04_RTG_COMPLETE_KEY)),
					connectionString, fromDateTime, toDateTime,
					scoringInfo.getPending04CompleteSpkCnt(),
					scoringInfo.getPending04CompleteWrtCnt(),
					WebAppConst.PENDING_RTG_COMPLETE, WebAppConst.PEND_CATE_04);
		}

		if (isEditedParam(scoringInfo.getPending04MismatchSpkCnt())
				|| isEditedParam(scoringInfo.getPending04MismatchWrtCnt())) {
			bindSpeakingkAndWritingCount(scoreInfoMap.get(textProvider
					.getText(WebAppConst.PENDING04_RTG_MISMATCH_KEY)),
					connectionString, fromDateTime, toDateTime,
					scoringInfo.getPending04MismatchSpkCnt(),
					scoringInfo.getPending04MismatchWrtCnt(),
					WebAppConst.PENDING_RTG_MISMATCH, WebAppConst.PEND_CATE_04);
		}

		// Defective script count
		bindDefectiveScriptCount(scoreInfoMap.get(textProvider
				.getText(WebAppConst.DEFECTIVE_SCRIPT_COUNT_KEY)),
				connectionString);

		if (isEditedParam(scoringInfo.getPending05SecondWaitSpkCnt())
				|| isEditedParam(scoringInfo.getPending05SecondWaitWrtCnt())) {
			bindSpeakingkAndWritingCount(scoreInfoMap.get(textProvider
					.getText(WebAppConst.PENDING05_RTG_SECOND_WAIT_KEY)),
					connectionString, fromDateTime, toDateTime,
					scoringInfo.getPending05SecondWaitSpkCnt(),
					scoringInfo.getPending05SecondWaitWrtCnt(),
					WebAppConst.PENDING_RTG_SECOND_WAIT,
					WebAppConst.PEND_CATE_05);
		}

		if (isEditedParam(scoringInfo.getPending05PendingSpkCnt())
				|| isEditedParam(scoringInfo.getPending05PendingWrtCnt())) {
			bindSpeakingkAndWritingCount(scoreInfoMap.get(textProvider
					.getText(WebAppConst.PENDING05_RTG_PENDING_KEY)),
					connectionString, fromDateTime, toDateTime,
					scoringInfo.getPending05PendingSpkCnt(),
					scoringInfo.getPending05PendingWrtCnt(),
					WebAppConst.PENDING_RTG_PENDING, WebAppConst.PEND_CATE_05);
		}

		if (isEditedParam(scoringInfo.getPending05CompleteSpkCnt())
				|| isEditedParam(scoringInfo.getPending05CompleteWrtCnt())) {
			bindSpeakingkAndWritingCount(scoreInfoMap.get(textProvider
					.getText(WebAppConst.PENDING05_RTG_COMPLETE_KEY)),
					connectionString, fromDateTime, toDateTime,
					scoringInfo.getPending05CompleteSpkCnt(),
					scoringInfo.getPending05CompleteWrtCnt(),
					WebAppConst.PENDING_RTG_COMPLETE, WebAppConst.PEND_CATE_05);
		}

		if (isEditedParam(scoringInfo.getPending05MismatchSpkCnt())
				|| isEditedParam(scoringInfo.getPending05MismatchWrtCnt())) {
			bindSpeakingkAndWritingCount(scoreInfoMap.get(textProvider
					.getText(WebAppConst.PENDING05_RTG_MISMATCH_KEY)),
					connectionString, fromDateTime, toDateTime,
					scoringInfo.getPending05MismatchSpkCnt(),
					scoringInfo.getPending05MismatchWrtCnt(),
					WebAppConst.PENDING_RTG_MISMATCH, WebAppConst.PEND_CATE_05);
		}

		if (isEditedParam(scoringInfo.getMismatchRtgPendingSpkCnt())
				|| isEditedParam(scoringInfo.getMismatchRtgPendingSpkCnt())) {
			bindSpeakingkAndWritingCount(scoreInfoMap.get(textProvider
					.getText(WebAppConst.MISMATCH_RTG_PENDING_KEY)),
					connectionString, fromDateTime, toDateTime,
					scoringInfo.getMismatchRtgPendingSpkCnt(),
					scoringInfo.getMismatchRtgPendingSpkCnt(),
					WebAppConst.MISMATCH_RTG_PENDING, null);
		}

		if (isEditedParam(scoringInfo.getMismatchRtgCompleteSpkCnt())
				|| isEditedParam(scoringInfo.getMismatchRtgCompleteWrtCnt())) {
			bindSpeakingkAndWritingCount(scoreInfoMap.get(textProvider
					.getText(WebAppConst.MISMATCH_RTG_COMPLETE_KEY)),
					connectionString, fromDateTime, toDateTime,
					scoringInfo.getMismatchRtgCompleteSpkCnt(),
					scoringInfo.getMismatchRtgCompleteWrtCnt(),
					WebAppConst.MISMATCH_RTG_COMPLETE, null);
		}
	}

	private void bindSpeakingkAndWritingCount(RatingInfo ratingInfo,
			String connectionString, String fromDateTime, String toDateTime,
			Integer speakingCnt, Integer writingCnt, String stateListLike,
			String pendCategoryLike) {
		List<Object[]> quesSeqWiseCnt;
		if (pendCategoryLike != null) {
			quesSeqWiseCnt = tranDescScoreHistoryDAO
					.getPendCategoryAndStateWiseScoringData(connectionString,
							stateListLike, pendCategoryLike, fromDateTime,
							toDateTime);
		} else {
			quesSeqWiseCnt = tranDescScoreHistoryDAO.getStateWiseScoringData(
					connectionString, stateListLike, fromDateTime, toDateTime);
		}
		ratingInfo
				.setDailyScoreSpkTotalCount(ratingInfo
						.getDailyScoreSpkTotalCount()
						+ (isEditedParam(speakingCnt) ? speakingCnt
								: WebAppConst.ZERO));
		ratingInfo.setDailyScoreWrtTotalCount(ratingInfo
				.getDailyScoreWrtTotalCount()
				+ (isEditedParam(writingCnt) ? writingCnt : WebAppConst.ZERO));
		bindQuestionSeqWiseCount(ratingInfo, quesSeqWiseCnt);
	}

	private void bindQuestionSeqWiseCount(RatingInfo ratingInfo,
			List<Object[]> quesSeqWiseCnt) {
		Map<Integer, Integer> map = ratingInfo.getQuesSeqWiseCountMap();
		if (map == null) {
			map = new LinkedHashMap<Integer, Integer>();
			ratingInfo.setQuesSeqWiseCountMap(map);
		}
		for (Object[] quesInfo : quesSeqWiseCnt) {
			Integer quesSeq = (Integer) quesInfo[0];
			Integer count = ((BigInteger) quesInfo[2]).intValue();
			Integer prevCount = map.get(quesSeq);
			if (prevCount != null) {
				map.put(quesSeq, count + prevCount);
			} else {
				map.put(quesSeq, count);
			}
		}
	}

	private void bindDefectiveScriptCount(RatingInfo ratingInfo,
			String connectionString) {
		Object[] defectiveScriptCnt = tranDescScoreHistoryDAO
				.getDefectiveSciptCount(connectionString);
		ratingInfo
				.setDailyScoreSpkTotalCount(ratingInfo
						.getDailyScoreSpkTotalCount()
						+ (isEditedParam(defectiveScriptCnt[0]) ? ((BigDecimal) defectiveScriptCnt[0])
								.intValue() : WebAppConst.ZERO));
		ratingInfo
				.setDailyScoreWrtTotalCount(ratingInfo
						.getDailyScoreWrtTotalCount()
						+ (isEditedParam(defectiveScriptCnt[1]) ? ((BigDecimal) defectiveScriptCnt[1])
								.intValue() : WebAppConst.ZERO));
	}

	private void setTime(DownloadInfo downloadInfo) {
		downloadInfo.setFromTime(String.format("%02d",
				Integer.valueOf(downloadInfo.getStartHours()))
				+ WebAppConst.COLON
				+ String.format("%02d",
						Integer.valueOf(downloadInfo.getStartMinutes())));
		downloadInfo.setToTime(String.format("%02d",
				Integer.valueOf(downloadInfo.getEndHours()))
				+ WebAppConst.COLON
				+ String.format("%02d",
						Integer.valueOf(downloadInfo.getEndMinutes())));
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

	private boolean isEditedParam(Integer object) {
		if ((object == null) || (object.equals(new Integer(0)))) {
			return false;
		}
		return true;
	}

	private boolean isEditedParam(Object object) {
		if (object == null) {
			return false;
		}
		return true;
	}

	public void setTranDescScoreHistoryDAO(
			TranDescScoreHistoryDAO tranDescScoreHistoryDAO) {
		this.tranDescScoreHistoryDAO = tranDescScoreHistoryDAO;
	}

	public void setMstDbInstanceDAO(MstDbInstanceDAO mstDbInstanceDAO) {
		this.mstDbInstanceDAO = mstDbInstanceDAO;
	}
}