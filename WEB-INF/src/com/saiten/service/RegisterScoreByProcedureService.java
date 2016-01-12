/**
 * 
 */
package com.saiten.service;

import java.util.Date;
import java.util.List;

import com.saiten.info.AnswerInfo;
import com.saiten.info.MstScorerInfo;
import com.saiten.info.QuestionInfo;
import com.saiten.info.ScoreInputInfo;
import com.saiten.info.ScoreSamplingInfo;

/**
 * @author user
 *
 */
public interface RegisterScoreByProcedureService {

	/**
	 * @param questionInfo
	 * @param scorerInfo
	 * @param answerInfo
	 * @param gradeSeq
	 * @param approveOrDeny
	 * @return
	 */
	public boolean registerScoring(QuestionInfo questionInfo,
			MstScorerInfo scorerInfo, AnswerInfo answerInfo, Integer gradeSeq,
			Integer gradeNum, Integer denyCategorySeq, Short denyCategory, String approveOrDeny, Date updateDate,
			Integer historyRecordCount);

	/**
	 * @param answerSeq
	 * @param questionInfo
	 * @param currentStateList
	 * @param scoreSamplingInfoList
	 * @return
	 */
	public int updateInspectFlag(List<Integer> answerSeq,
			QuestionInfo questionInfo,
			List<ScoreSamplingInfo> scoreSamplingInfoList,
			boolean selectAllFlag, ScoreInputInfo scoreInputInfo,Integer maxInspectGroupSeq);

	/**
	 * @param questionInfo
	 * @param scorerInfo
	 * @param answerInfo
	 * @param gradeSeq
	 * @param approveOrDeny
	 * @param updateDate
	 * @param historyRecordCount
	 * @return
	 */
	public boolean registerQcScoring(QuestionInfo questionInfo,
			MstScorerInfo scorerInfo, AnswerInfo answerInfo, Integer gradeSeq,
			Integer gradeNum, String approveOrDeny, Date updateDate,
			Integer historyRecordCount);
}
