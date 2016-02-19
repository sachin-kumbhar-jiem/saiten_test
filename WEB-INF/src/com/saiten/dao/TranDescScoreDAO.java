package com.saiten.dao;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import com.saiten.info.DailyStatusSearchInfo;
import com.saiten.info.QuestionInfo;
import com.saiten.info.ScoreInputInfo;
import com.saiten.model.TranDescScore;

/**
 * @author sachin
 * @version 1.0
 * @created 11-Dec-2012 12:56:32 PM
 */
public interface TranDescScoreDAO {

	/**
	 * @param quetionSeq
	 * @param menuId
	 * @param scorerId
	 * @param menuIdAndScoringStateMap
	 * @param connectionString
	 * @return List
	 */
	@SuppressWarnings("rawtypes")
	public List findAnswer(List<Integer> quetionSeq, String menuId,
			String scorerId,
			LinkedHashMap<String, Short> menuIdAndScoringStateMap,
			String connectionString, Integer gradeNum, Short pendingCategory,
			Short denyCategory, String answerFormNum,
			Integer historyRecordCount, Integer randomNumberRange,
			boolean passByRandomFlag, String selectedMarkValue, int roleId,
			boolean qualityFromPendingMenu, Integer inspectGroupSeq);

	/**
	 * @param answerSeq
	 * @param connectionString
	 * @return TranDescScore
	 */
	public TranDescScore findById(int answerSeq, String connectionString);

	/**
	 * @param answerSeq
	 * @param scorerId
	 * @param connectionString
	 */
	public void lockAnswer(int answerSeq, String scorerId,
			String connectionString, Date updateDate);

	/**
	 * @param questionSeq
	 * @param lockBy
	 * @param connectionString
	 */
	public void unlockAnswer(int questionSeq, String lockBy,
			String connectionString, Integer answerSeq);

	/**
	 * @param answerSeq
	 * @param selectAllFlag
	 * @param questionInfo
	 */
	public int updateInspectFlag(List<Integer> answerSeq,
			QuestionInfo questionInfo, boolean selectAllFlag,
			ScoreInputInfo scoreInputInfo, Integer maxInspectGroupSeq);

	/**
	 * @param quetionSeq
	 * @param menuId
	 * @param scorerId
	 * @param menuIdAndScoringStateMap
	 * @param connectionString
	 * @param answerFormNum
	 * @param historyRecordCount
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List findAnswer(List<Integer> quetionSeq, String menuId,
			String scorerId,
			LinkedHashMap<String, Short> menuIdAndScoringStateMap,
			String connectionString, String answerFormNum,
			Integer historyRecordCount, int roleId);

	/**
	 * @param quetionSeq
	 * @param menuId
	 * @param scorerId
	 * @param menuIdAndScoringStateMap
	 * @param connectionString
	 * @param answerFormNum
	 * @param historyRecordCount
	 * @param roleId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List findQcAnsSeqList(List<Integer> quetionSeq, String scorerId,
			String connectionString);

	/**
	 * @param qcAnswerSeq
	 * @param connectionString
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List findQualityCheckAnswers(Integer qcAnswerSeq,
			String connectionString);

	/**
	 * @param dailyStatusSearchInfo
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List getDailyuStatusSearchList(
			DailyStatusSearchInfo dailyStatusSearchInfo,
			String connectionString, String questionSeq);

	/**
	 * @param dailyStatusSearchInfo
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List getDailyuStatusSearchListByScorer(
			DailyStatusSearchInfo dailyStatusSearchInfo,
			String connectionString, String scorerIds, String questionSeq);

	/**
	 * @param questionSeq
	 * @param connectionString
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List getGradeWiseAnswerDetails(String questionSeq,
			String connectionString, Character questionType);

	/**
	 * @param questionSeq
	 * @param connectionString
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List getPendingCategoryWiseAnswerDetails(String questionSeq,
			String connectionString);

	/**
	 * 
	 * @param answerSeq
	 * @return testsetnum_seq
	 */
	@SuppressWarnings("rawtypes")
	public List findTestsetNumSeq(Integer answerSeq, String connectionString);

	/**
	 * 
	 * @param questionSeq
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List findMaxInspectGroupSeq(int questionSeq, String connectionString);

	/**
	 * 
	 * @param questionSeq
	 * @return List
	 */
	@SuppressWarnings("rawtypes")
	public List findKenshuRecords(Integer questionSeq, String connectionString,
			int recordCount);

	/**
	 * 
	 * @param gradeNum
	 * @return Integer
	 */
	@SuppressWarnings("rawtypes")
	public List isAnswerAlreadyChecked(Integer answerSeq, Date date,
			String connectionString);

	/**
	 * 
	 * @param answerSeq
	 */
	public int updateKunshuFlagByAnswerseq(Integer answerSeq, Date date,
			String connectionString);
	/**
	 * 
	 * @param questionSeq
	 * @param connectionString
	 * @param recordCount
	 * @param gradeNum
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List getKenshuRecordsByGrade(int questionSeq,
			String connectionString, int recordCount, int gradeNum);
}