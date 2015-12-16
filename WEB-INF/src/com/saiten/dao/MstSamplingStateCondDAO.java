package com.saiten.dao;

import java.util.List;

/**
 * @author sachin
 * @version 1.0
 * @created 05-Dec-2012 4:59:11 PM
 */
public interface MstSamplingStateCondDAO {

	/**
	 * @param screenId
	 * @return List
	 */
	@SuppressWarnings("rawtypes")
	public List findCurrentStateList(String screenId);
}
