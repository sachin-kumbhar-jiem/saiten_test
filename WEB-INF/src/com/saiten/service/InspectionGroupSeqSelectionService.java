package com.saiten.service;

import java.util.Map;

/**
 * @author kailash
 * 
 */
public interface InspectionGroupSeqSelectionService {
	/**
	 * @param questionSeq
	 * @param scorerId
	 * @param connectionString
	 * @return
	 */
	public Map<String, String> fetchInspectionGroupSeqAndCount(int questionSeq,
			String scorerId, String connectionString);
}
