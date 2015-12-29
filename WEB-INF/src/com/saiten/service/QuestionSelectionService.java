package com.saiten.service;

import java.util.List;
import java.util.Map;

import com.saiten.info.CheckPointInfo;
import com.saiten.info.QuestionInfo;

/**
 * @author sachin
 * @version 1.0
 * @created 06-Dec-2012 2:35:20 PM
 */
public interface QuestionSelectionService {

	/**
	 * @param questionSeq
	 * @return QuestionInfo
	 */
	public QuestionInfo fetchDbInstanceInfo(List<Integer> questionSeq);

	/**
	 * @param menuId
	 * @param scorerId
	 * @param roleDescription
	 * @return Map<String, String>
	 */
	public Map<String, String> findQuestionsByMenuIdAndScorerId(String menuId,
			String scorerId, int roleId);

	/**
	 * @param scorerId
	 * @param questionSeq
	 * @param connectionString
	 * @param scoringStateList
	 * @return List<Integer>
	 */
	public int findHistoryRecordCount(String scorerId,
			List<Integer> questionSeq, String connectionString,
			List<Short> scoringStateList);

	/**
	 * @param questionSeq
	 * @return List<CheckPointInfo>
	 */
	public List<CheckPointInfo> findCheckPoints(int questionSeq);

	/**
	 * @param questionSeq
	 * @return Map<Integer, String>
	 */
	public Map<Integer, String> findPendingCategories(int questionSeq);

	public Map<Integer, QuestionInfo> fetchDbInstanceInfo(
			List<Integer> questionSeq, String specialScoringMenuId);

	/**
	 * @param scorerId
	 * @param questionSeq
	 * @param connectionString
	 * @param scoringStateList
	 * @return
	 */
	public int findQcHistoryRecordCount(String scorerId,
			List<Integer> questionSeq, String connectionString,
			List<Short> scoringStateList);
	
	/**
	 * 
	 * @param QuestionSeq
	 * @return Map<Integer, String>
	 */
	public Map<Integer, String> findDenyCategories (int QuestionSeq);

}