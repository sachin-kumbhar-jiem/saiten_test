package com.saiten.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;

import com.saiten.dao.MstMenuRoleDAO;
import com.saiten.exception.SaitenRuntimeException;
import com.saiten.service.UserMenuService;
import com.saiten.util.ErrorCode;

/**
 * @author sachin
 * @version 1.0
 * @created 07-Dec-2012 10:33:35 AM
 */
public class UserMenuServiceImpl implements UserMenuService {

	private MstMenuRoleDAO mstMenuRoleDAO;

	/**
	 * 
	 * @param scorerId
	 * @param roleId
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List<String> getUserMenuIdList(byte roleId) {
		List<String> userMenuIdList = null;
		try {
			List tempUserMenuIdList = mstMenuRoleDAO.getUserMenuIdList(roleId);
			if (tempUserMenuIdList != null && tempUserMenuIdList.size() > 0) {
				userMenuIdList = new ArrayList<String>();
				for (Object object : tempUserMenuIdList) {
					String userMenuId = (String) object;
					userMenuIdList.add(userMenuId);
				}
			}
			return userMenuIdList;
		} catch (HibernateException he) {
			throw new SaitenRuntimeException(
					ErrorCode.USER_MENU_HIBERNATE_EXCEPTION, he);
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.USER_MENU_SERVICE_EXCEPTION, e);
		}
	}

	public void setMstMenuRoleDAO(MstMenuRoleDAO mstMenuRoleDAO) {
		this.mstMenuRoleDAO = mstMenuRoleDAO;
	}

}