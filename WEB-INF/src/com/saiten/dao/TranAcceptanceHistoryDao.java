package com.saiten.dao;

import com.saiten.model.TranAcceptanceHistory;

public interface TranAcceptanceHistoryDao {
	/**
	 * 
	 * @param tranAcceptance
	 * @param connectionString
	 * @return Integer
	 */
	public Integer save(TranAcceptanceHistory tranAcceptanceHistory, String connectionString);
	
	/**
	 * 
	 * @param tranAcceptance
	 * @param connectionString
	 */
	public void update(TranAcceptanceHistory tranAcceptanceHistory, String connectionString);
	
	/**
	 * 
	 * @param answerSeq
	 * @param updatePersonId
	 * @param connectionString
	 * @return TranAcceptance
	 */
	
	public TranAcceptanceHistory fetchByAnswerSeqAndUpdatePersonId(Integer answerSeq,
			String updatePersonId, String connectionString);

}
