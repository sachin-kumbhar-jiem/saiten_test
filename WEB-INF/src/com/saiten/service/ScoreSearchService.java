package com.saiten.service;

import java.util.List;
import java.util.Map;

import com.saiten.info.QuestionInfo;
import com.saiten.info.ScoreInputInfo;
import com.saiten.info.TranDescScoreInfo;

/**
 * @author sachin
 * @version 1.0
 * @created 13-Dec-2012 11:53:21 AM
 */
public interface ScoreSearchService {
	public Map<String, String> findSubjectNameList();

	/**
	 * @param menuId
	 * @return Map<Short, String>
	 */
	public Map<Short, String> findHistoryEventList(String menuId);

	/**
	 * @param menuId
	 * @return Map<Short, String>
	 */
	public Map<Short, String> findCurrentStateList(String menuId);

	/**
	 * @param questionInfo
	 * @param scoreInputInfo
	 * @return List<TranDescScoreInfo>
	 */
	@SuppressWarnings("rawtypes")
	public List searchAnswerRecords(QuestionInfo questionInfo,
			ScoreInputInfo scoreInputInfo, String scorerId,
			Boolean forceAndStateTransitionFlag, Integer startRecord,
			Integer endRecord);

	/**
	 * @param subjectCode
	 * @param questionNum
	 * @return List<Integer>
	 */
	public List<Integer> findQuestionSeq(String subjectCode, Short questionNum);

	/**
	 * @param questionSeq
	 * @param gradeNum
	 * @return List
	 */
	@SuppressWarnings("rawtypes")
	public List findGradeSeqList(Integer questionSeq, Integer[] gradeNum);

	/**
	 * @param questionSeq
	 * @param pendingCategoryList
	 * @return List
	 */
	@SuppressWarnings("rawtypes")
	public List findPendingCategorySeqList(Integer questionSeq,
			Short[] pendingCategoryList);

	public List<TranDescScoreInfo> findSpecialScoringAnswerRecords(
			List<Integer> quetionSeqList, String menuId, String scorerId,
			String connectionString, String answerFormNum,
			Integer historyRecordCount, int roleId);

	public List<String> buildLoggedInScorerSubjectList(String scorerId);

	/**
	 * @param answerSequence
	 * @param connectionString
	 * @return List<TranDescScoreInfo>
	 */
	public List<TranDescScoreInfo> findProcessDetails(Integer answerSequence,
			String connectionString);

}