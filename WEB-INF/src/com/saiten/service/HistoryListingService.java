package com.saiten.service;

import java.util.List;
import java.util.Map;

import com.saiten.info.HistoryInfo;
import com.saiten.info.MstScorerInfo;
import com.saiten.info.QuestionInfo;

/**
 * @author Administrator
 * @version 1.0
 * @created 12-Dec-2012 3:54:16 PM
 */
public interface HistoryListingService {

	/**
	 * @param mstScorerInfo
	 * @param questionInfo
	 * @param startRecord
	 * @param endRecord
	 * @return
	 */
	public List<HistoryInfo> findHistoryInfoList(MstScorerInfo mstScorerInfo,
			QuestionInfo questionInfo, Integer startRecord, Integer endRecord,
			String connectionString, List<Integer> questionSeqList);

	/**
	 * @param mstScorerInfo
	 * @param questionInfo
	 * @return
	 */
	public int findHistoryRecordCount(MstScorerInfo mstScorerInfo,
			QuestionInfo questionInfo, String connectionString,
			List<Integer> questionSeqList);

	/**
	 * @param historyInfoList
	 * @return
	 */
	public List<HistoryInfo> sortHistoryInfoList(
			List<HistoryInfo> historyInfoList);

	public List<HistoryInfo> loadOmrEnlargeHistory(MstScorerInfo mstScorerInfo,
			QuestionInfo questionInfo, Map<String, List<Integer>> questionSeqMap);
}