package com.saiten.service;

import java.util.Date;
import java.util.List;

import com.saiten.info.GradeInfo;
import com.saiten.info.MstScorerInfo;
import com.saiten.info.QuestionInfo;
import com.saiten.info.TranDescScoreInfo;

/**
 * @author sachin
 * @version 1.0
 * @created 11-Dec-2012 12:58:34 PM
 */
public interface ScoreService {

	/**
	 * @param quetionSeq
	 * @param menuId
	 * @param scorerId
	 * @param connectionString
	 * @return TranDescScoreInfo
	 */
	public TranDescScoreInfo findAnswer(int quetionSeq, String menuId, String scorerId, String connectionString,
			Integer gradeNum, Short pendingCategory, Short denyCategory, String answerFormNum,
			Integer historyRecordCount, int roleId, Short selectedMarkValue, QuestionInfo questionInfo,
			Double bitValue);

	/**
	 * @param historySeq
	 * @param connectionString
	 * @return TranDescScoreInfo
	 */
	public TranDescScoreInfo findHistoryAnswer(Integer qcSeq, Integer historySeq, String connectionString,
			String scorerId, boolean bookmarkScreenFlag, QuestionInfo questionInfo);

	/**
	 * @param questionInfo
	 * @param scorerInfo
	 * @param updateDate
	 * @param prevOrNext
	 * @return TranDescScoreInfo
	 */
	public TranDescScoreInfo findPrevOrNextHistoryAnswer(QuestionInfo questionInfo, MstScorerInfo scorerInfo,
			Date updateDate, boolean prevOrNext, boolean bookmarkScreenFlag);

	/**
	 * @param answerSeq
	 * @param scorerId
	 * @param connectionString
	 */
	public void lockAnswer(int answerSeq, String scorerId, String connectionString, Date updateDate);

	/**
	 * @param gradeSeq
	 * @param questionSeq
	 * @return GradeInfo
	 */
	public GradeInfo buildGradeInfo(Integer gradeSeq, int questionSeq);

	/**
	 * @param questionSeq
	 * @param lockBy
	 * @param connectionString
	 */
	public void unlockAnswer(int questionSeq, String lockBy, String connectionString, Integer answerSeq);

	/**
	 * @param answerFormNumber
	 * @param questionInfo
	 * @return
	 */
	public List<Double> getFirstTimeSecondTimeCheckPoints(int answerSeq, QuestionInfo questionInfo,
			Short currentScoringState);

	/**
	 * @param answerSeq
	 * @param connectionString
	 * @param infoUpdateDate
	 * @return
	 */
	public boolean isAnswerAlreadyScored(int answerSeq, String connectionString, Date infoUpdateDate);

	/**
	 * @param questionSeq
	 * @param scorerId
	 * @param connectionString
	 * @param selectedMarkValue
	 * @return
	 */
	public List<Integer> findQcAnsSeqList(int questionSeq, String scorerId, Short selectedMarkValue,
			String connectionString);

	/**
	 * @param qcAnswerSeqList
	 * @param connectionString
	 * @return
	 */
	public TranDescScoreInfo findQualityCheckAnswers(int qcAnswerSeq, String menuId, String scorerId,
			String connectionString, QuestionInfo questionInfo);

	/**
	 * 
	 * @param answerSeq
	 * @return testSetnum_seq
	 */
	public Integer findTestsetNumSeq(Integer answerSeq, String connectionString);
	
	
	
	public List<TranDescScoreInfo> findBulkAnswer(int questionSeq, String menuId, String scorerId, String connectionString,
			Integer gradeNum, Short pendingCategory, Short denyCategory, String answerFormNum,
			Integer historyRecordCount, int roleId, Short selectedMarkValue, QuestionInfo questionInfo,
			Double bitValue);

}