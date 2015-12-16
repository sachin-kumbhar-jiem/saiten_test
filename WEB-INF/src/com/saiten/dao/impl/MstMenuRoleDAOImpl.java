package com.saiten.dao.impl;

import java.util.List;

import com.saiten.dao.MstMenuRoleDAO;
import com.saiten.dao.support.SaitenHibernateDAOSupport;
import com.saiten.util.WebAppConst;

/**
 * @author sachin
 * @version 1.0
 * @created 05-Dec-2012 4:59:11 PM
 */
public class MstMenuRoleDAOImpl extends SaitenHibernateDAOSupport implements
		MstMenuRoleDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saiten.dao.MstMenuRoleDAO#getUserMenuIdList(byte)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List getUserMenuIdList(byte roleId) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT mstMenuRole.id.menuId ");
		query.append("FROM MstMenuRole as mstMenuRole ");
		query.append("WHERE mstMenuRole.deleteFlag= :DELETE_FLAG ");
		query.append("AND mstMenuRole.enable= :ENABLE ");
		query.append("AND mstMenuRole.id.roleId= :ROLE_ID ");

		String[] paramNames = { "DELETE_FLAG", "ENABLE", "ROLE_ID" };
		Object[] values = { WebAppConst.DELETE_FLAG, WebAppConst.ENABLE, roleId };

		try {
			return getHibernateTemplate().findByNamedParam(query.toString(),
					paramNames, values);
		} catch (RuntimeException re) {
			throw re;
		}

	}

}
