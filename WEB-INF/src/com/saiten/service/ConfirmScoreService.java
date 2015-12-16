package com.saiten.service;

import com.saiten.info.GradeInfo;

/**
 * @author sachin
 * @version 1.0
 * @created 13-Dec-2012 11:53:21 AM
 */
public interface ConfirmScoreService {

	/**
	 * @param bitValue
	 * @param questionSeq
	 * @return GradeInfo
	 */
	public GradeInfo findGradeAndResult(int bitValue, int questionSeq);

	/**
	 * @param selectedCheckPoints
	 * @return int
	 */
	public int calculateBitValue(String selectedCheckPoints);

}