package com.saiten.service;

import com.saiten.info.KenshuRecordInfo;
import com.saiten.model.TranAcceptance;

public interface MarkKenshuRecordService {
	/**
	 * 
	 * @param kenshuRecordInfo
	 * @param connectionString
	 * @return acceptance_seq
	 */
	public Integer doMark(KenshuRecordInfo kenshuRecordInfo,
			String connectionString);

	/**
	 * 
	 * @param kenshuRecordInfo
	 * @param connectionString
	 * @return acceptance_seq
	 */
	public Integer doUnMark(KenshuRecordInfo kenshuRecordInfo,
			String connectionString);

	/**
	 * 
	 * @param tranacceptance
	 */
	public void updateRecord(TranAcceptance tranAcceptance,
			String connectionString);
}
