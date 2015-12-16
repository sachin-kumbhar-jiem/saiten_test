package com.saiten.dao;

import java.util.List;

/**
 * @author sachin
 * @version 1.0
 * @created 05-Dec-2012 4:59:11 PM
 */
public interface MstMenuRoleDAO {

	/**
	 * @param roleId
	 * @return List
	 */
	@SuppressWarnings("rawtypes")
	public List getUserMenuIdList(byte roleId);
}
