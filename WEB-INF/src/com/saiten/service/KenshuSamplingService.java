package com.saiten.service;

import java.util.List;

import com.saiten.info.TranDescScoreInfo;

public interface KenshuSamplingService {
	/**
	 * 
	 * @param questionSeq
	 * @return List
	 */
	@SuppressWarnings("rawtypes")
	public List getKenshuSamplingRecordsList(int questionSeq,
			String connectionString, int recordCount);

	/**
	 * 
	 * @param gradeNum
	 * @return List
	 */
	public int updateKenshuRecord(TranDescScoreInfo tranDescScoreInfo,
			String connectionString);

	/**
	 * 
	 * @param answerSeq
	 * @param connectionString
	 * @return boolean
	 */
	public boolean isAnswerAlreadyChecked(TranDescScoreInfo tranDescScoreInfo,
			String scorerId, String connectionString);

	/**
	 * 
	 * @param questionNum
	 * @param ConnectionString
	 * @param kenshuUserId
	 * @return recordList for kenshuUserId
	 */
	@SuppressWarnings("rawtypes")
	public List getKenshuMarkeRecordsList(int questionSeq,
			String searchCriteria, String ConnectionString, String kenshuUserId);

	@SuppressWarnings("rawtypes")
	public List getKenhuRecordsByGrade(int questionSeq,
			String connectionString, int recordCount, int gradeNum);
}
