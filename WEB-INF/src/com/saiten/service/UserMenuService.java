package com.saiten.service;

import java.util.List;

/**
 * @author sachin
 * @version 1.0
 * @created 07-Dec-2012 10:33:35 AM
 */
public interface UserMenuService {

	/**
	 * 
	 * @param scorerId
	 * @param roleId
	 */
	public List<String> getUserMenuIdList(byte roleId);

}