package com.saiten.service;

import java.util.Date;

import com.saiten.info.AnswerInfo;
import com.saiten.info.MstScorerInfo;
import com.saiten.info.QuestionInfo;

/**
 * @author sachin
 * @version 1.0
 * @created 24-Dec-2012 12:31:43 PM
 */
public interface RegisterPendingByProcedureService {

	/**
	 * @param questionInfo
	 * @param scorerInfo
	 * @param answerInfo
	 * @param pendingCategorySeq
	 * @return boolean
	 */
	public boolean registerPending(QuestionInfo questionInfo,
			MstScorerInfo scorerInfo, AnswerInfo answerInfo,
			Integer pendingCategorySeq, Short pendingCategory, Date updateDate);

	/**
	 * @param questionInfo
	 * @param scorerInfo
	 * @param answerInfo
	 * @param pendingCategorySeq
	 * @param updateDate
	 * @return
	 */
	public boolean registerQcPending(QuestionInfo questionInfo,
			MstScorerInfo scorerInfo, AnswerInfo answerInfo,
			Integer pendingCategorySeq, Short pendingCategory, Date updateDate,
			String answerFormNum);

}