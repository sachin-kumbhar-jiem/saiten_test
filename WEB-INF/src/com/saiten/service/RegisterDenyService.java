package com.saiten.service;

import java.util.Date;

import com.saiten.info.AnswerInfo;
import com.saiten.info.MstScorerInfo;
import com.saiten.info.QuestionInfo;

public interface RegisterDenyService {

	/**
	 * @param questionInfo
	 * @param scorerInfo
	 * @param answerInfo
	 * @param denyCategorySeq
	 * @return boolean
	 */
	public boolean registerDeny(QuestionInfo questionInfo,
			MstScorerInfo scorerInfo, AnswerInfo answerInfo, Integer gradeSeq,
			Integer gradeNum, Integer denyCategorySeq, Short denyCategory,
			Date updateDate);

	/**
	 * @param questionInfo
	 * @param scorerInfo
	 * @param answerInfo
	 * @param denyCategorySeq
	 * @param updateDate
	 * @return
	 */
	public boolean registerQcDeny(QuestionInfo questionInfo,
			MstScorerInfo scorerInfo, AnswerInfo answerInfo, Integer gradeSeq,
			Integer gradeNum, Integer denyCategorySeq, Short denyCategory,
			Date updateDate);
}
