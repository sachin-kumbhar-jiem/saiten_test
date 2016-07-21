package com.saiten.dao;

import java.util.Date;
import java.util.List;

import com.saiten.info.DailyReportsInfo;
import com.saiten.info.DailyScoreInfo;
import com.saiten.info.QuestionInfo;
import com.saiten.info.RatingInfo;
import com.saiten.info.RegisterScoreInfo;
import com.saiten.info.ScoreInputInfo;
import com.saiten.model.TranDescScoreHistory;

/**
 * @author sachin
 * @version 1.0
 * @created 11-Dec-2012 12:56:32 PM
 */
public interface TranDescScoreHistoryDAO {

	/**
	 * @param scorerId
	 * @param questionSeq
	 * @param connectionString
	 * @param scoringStateList
	 * @param bookmarkScreenFlag
	 * @return int
	 */
	public int findHistoryRecordCount(String scorerId,
			List<Integer> questionSeq, String connectionString,
			List<Short> scoringStateList, boolean bookmarkScreenFlag);

	/**
	 * @param historySeq
	 * @param connectionString
	 * @return TranDescScoreHistory
	 */
	public TranDescScoreHistory findById(Integer historySeq,
			String connectionString);

	/**
	 * @param historySeq
	 * @param connectionString
	 * @return List
	 */
	@SuppressWarnings("rawtypes")
	public List findHistoryAnswer(Integer historySeq, String connectionString);

	/**
	 * @param tranDescScoreHistory
	 * @param connectionString
	 * @return boolean
	 */
	public void save(TranDescScoreHistory tranDescScoreHistory,
			String connectionString);

	/**
	 * @param scorerId
	 * @param scoringStateList
	 * @param questionSequence
	 * @param connectionString
	 * @param startRecord
	 * @param endRecord
	 * @param bookmarkScreenFlag
	 * @return List
	 */
	@SuppressWarnings("rawtypes")
	public List findHistoryInfoList(String scorerId,
			List<Short> scoringStateList, List<Integer> questionSeq,
			String connectionString, Integer startRecord, Integer endRecord,
			boolean bookmarkScreenFlag);

	/**
	 * @param historySequenceList
	 * @param connectionString
	 * @return int
	 */
	public int deleteBookmarks(List<Integer> historySequenceList,
			String connectionString);

	/**
	 * @param quetionSeq
	 * @param menuId
	 * @param scorerId
	 * @param connectionString
	 * @param scoringStateList
	 * @param updateDate
	 * @param isPrevious
	 * @return List
	 */
	@SuppressWarnings("rawtypes")
	public List findPrevOrNextHistoryAnswer(int quetionSeq, String menuId,
			String scorerId, String connectionString,
			List<Short> scoringStateList, Date updateDate, boolean isPrevious);

	@SuppressWarnings("rawtypes")
	public List searchAnswerRecords(QuestionInfo questionInfo,
			ScoreInputInfo scoreInputInfo, Boolean forceAndStateTransitionFlag,
			Integer startRecord, Integer endRecord, String orderByRandAttempt);

	@SuppressWarnings("rawtypes")
	public List searchAnswerRecords(QuestionInfo questionInfo,
			ScoreInputInfo scoreInputInfo, Boolean forceAndStateTransitionFlag,
			Integer startRecord, Integer endRecord);

	/**
	 * @param answerFormNumber
	 * @param questionSeq
	 * @param connectionString
	 * @return
	 */
	public List<Double> getFirstTimeSecondTimeCheckPoints(int answerSeq,
			Short[] latestScoringState, QuestionInfo questionInfo,
			Short currentScoringState);

	@SuppressWarnings("rawtypes")
	public List findHistoryRecord(String scorerId, int answerSeq,
			List<Short> scoringStateList, String connectionString);

	/**
	 * @param answerSeq
	 * @param questionInfo
	 * @return
	 */
	public List<String> getCommentsByAnswerSequences(
			List<Integer> answerSeqList, QuestionInfo questionInfo);

	/**
	 * @param scorerId
	 * @param scoringStateList
	 * @param questionSeq
	 * @param connectionString
	 * @param startRecord
	 * @param endRecord
	 * @param bookmarkScreenFlag
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List findQcAndHistoryInfoList(String scorerId,
			List<Short> scoringStateList, List<Integer> questionSeq,
			String connectionString, Integer startRecord, Integer endRecord,
			boolean bookmarkScreenFlag);

	/**
	 * @param quetionSeq
	 * @param menuId
	 * @param scorerId
	 * @param connectionString
	 * @param scoringStateList
	 * @param updateDate
	 * @param isPrevious
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List findPrevOrNextHistoryAndQcAnswer(int quetionSeq, String menuId,
			String scorerId, String connectionString,
			List<Short> scoringStateList, Date updateDate, boolean isPrevious);

	/**
	 * @param answerSequence
	 * @param connectionString
	 * @return List
	 */
	@SuppressWarnings("rawtypes")
	public List findProcessDetails(Integer answerSequence,
			String connectionString);

	/**
	 * @param connectionString
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	public List<Object[]> getDailyDiscrepancyAnalysisData(
			String connectionString, String fromDate, String toDate);

	/**
	 * @param connectionString
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	public List<RatingInfo> getSummaryDiscrepancyAnalysisData(
			String connectionString, String fromDate, String toDate);

	/**
	 * @param connectionString
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	public DailyScoreInfo getDailyScoreInfo(String connectionString,
			String fromDate, String toDate);

	/**
	 * @param connectionString
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	public List<Integer> getDailyScoreQuesSeqInfo(String connectionString,
			final String fromDate, final String toDate);

	/**
	 * @param connectionString
	 * @param stateList
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	public List<Object[]> getStateWiseScoringData(String connectionString,
			String stateList, String fromDate, String toDate);

	/**
	 * @param connectionString
	 * @param stateList
	 * @param pendingCategory
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	public List<Object[]> getPendCategoryAndStateWiseScoringData(
			String connectionString, String stateList, String pendingCategory,
			String fromDate, String toDate);

	/**
	 * @param connectionString
	 * @return
	 */
	public Object[] getDefectiveSciptCount(String connectionString);

	/**
	 * @param questionSeq
	 * @param scorerId
	 * @param connectionString
	 * @return
	 */
	public List<Object[]> fetchInspectionGroupSeqAndCount(int questionSeq,
			String scorerId, String connectionString);

	public List registerAnswer(RegisterScoreInfo registerScoreInfo,
			String connectionString);

	public List<String> getDateAndTimeWiseQuestionCount(
			String connectionString, DailyReportsInfo dailyReportsInfo);
}
