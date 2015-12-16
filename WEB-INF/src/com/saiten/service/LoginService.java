package com.saiten.service;

import com.saiten.info.MstScorerInfo;

/**
 * @author sachin
 * @version 1.0
 * @created 05-Dec-2012 4:59:11 PM
 */
public interface LoginService {

	/**
	 * 
	 * @param scorerId
	 * @param password
	 */
	public MstScorerInfo findByScorerIdAndPassword(String scorerId, String password);

}