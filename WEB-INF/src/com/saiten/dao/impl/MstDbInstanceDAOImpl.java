package com.saiten.dao.impl;

import java.util.List;

import com.saiten.dao.MstDbInstanceDAO;
import com.saiten.dao.support.SaitenHibernateDAOSupport;
import com.saiten.model.MstDbInstance;
import com.saiten.util.WebAppConst;

/**
 * @author sachin
 * @version 1.0
 * @created 05-Dec-2012 4:59:11 PM
 */
public class MstDbInstanceDAOImpl extends SaitenHibernateDAOSupport implements
		MstDbInstanceDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saiten.dao.MstGradeDAO#findAll()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MstDbInstance> findAll() {
		StringBuilder query = new StringBuilder();
		query.append("FROM MstDbInstance mstDbInstance ");
		query.append("WHERE mstDbInstance.deleteFlag = :DELETE_FLAG ");

		String[] paramNames = { "DELETE_FLAG" };
		Object[] values = { WebAppConst.DELETE_FLAG };

		try {
			return getHibernateTemplate().findByNamedParam(query.toString(),
					paramNames, values);
		} catch (RuntimeException re) {
			throw re;
		}

	}

}
