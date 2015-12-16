/**
 * 
 */
package com.saiten.dao;

import java.util.List;

import com.saiten.model.TranQualitycheckScore;

/**
 * @author rajeshwars
 * 
 */
public interface TranQualitycheckScoreDAO {
	/**
	 * @param tranQualitycheckScore
	 * @param connectionString
	 */
	public void save(TranQualitycheckScore tranQualitycheckScore,
			String connectionString);

	/**
	 * @param scorerId
	 * @param questionSeq
	 * @param connectionString
	 * @param scoringStateList
	 * @param bookmarkScreenFlag
	 * @return
	 */
	public int findQcHistoryRecordCount(String scorerId,
			List<Integer> questionSeq, String connectionString,
			List<Short> scoringStateList, boolean bookmarkScreenFlag);

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
	public List findHistoryInfoList(String scorerId,
			List<Short> scoringStateList, List<Integer> questionSeq,
			String connectionString, Integer startRecord, Integer endRecord,
			boolean bookmarkScreenFlag);

	/**
	 * @param qcSeq
	 * @param connectionString
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List findQcHistoryAnswer(Integer qcSeq, String connectionString);

	public TranQualitycheckScore findById(int qcSeq, String connectionString);

}
