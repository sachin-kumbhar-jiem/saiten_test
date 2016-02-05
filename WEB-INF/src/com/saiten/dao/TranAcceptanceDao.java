package com.saiten.dao;

import java.util.List;

import com.saiten.model.TranAcceptance;

public interface TranAcceptanceDao {
	/**
	 * 
	 * @param tranAcceptance
	 * @param connectionString
	 * @return acceptanceSeq
	 */
	public Integer save(TranAcceptance tranAcceptance, String connectionString);

	/**
	 * 
	 * @param tranAcceptance
	 * @param connectionString
	 */
	public void update(TranAcceptance tranAcceptance, String connectionString);

	public TranAcceptance fetchByAnswerSeqAndUpdatePersonId(Integer answerSeq,
			String updatePersonId, String connectionString);

	public List<TranAcceptance> fetchByQuestionNumAndMarkBy(
			Integer questionSeq, String markBy, String searchCriteria,
			String connectionString);
}
