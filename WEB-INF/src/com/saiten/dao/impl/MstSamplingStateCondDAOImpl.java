package com.saiten.dao.impl;

import java.util.List;

import com.saiten.dao.MstSamplingStateCondDAO;
import com.saiten.dao.support.SaitenHibernateDAOSupport;
import com.saiten.util.WebAppConst;

/**
 * @author sachin
 * @version 1.0
 * @created 20-Feb-2013 4:59:11 PM
 */
public class MstSamplingStateCondDAOImpl extends SaitenHibernateDAOSupport
		implements MstSamplingStateCondDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.saiten.dao.MstSamplingStateCondDAO#findCurrentStateList(java.lang
	 * .String)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List findCurrentStateList(String screenId) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT mstSamplingStateCond.id.scoringState, mstSamplingStateCond.mstScoringStateList.stateName ");
		query.append("FROM MstSamplingStateCond as mstSamplingStateCond ");
		query.append("WHERE mstSamplingStateCond.id.screenId = :SCREEN_ID ");
		query.append("AND mstSamplingStateCond.deleteFlag = :DELETE_FLAG ");
		query.append("AND mstSamplingStateCond.mstScoringStateList.deleteFlag = :DELETE_FLAG ");
		query.append("ORDER BY mstSamplingStateCond.displayIndex");

		String[] paramNames = { "SCREEN_ID", "DELETE_FLAG" };
		Object[] values = { screenId, WebAppConst.DELETE_FLAG };

		try {
			return getHibernateTemplate().findByNamedParam(query.toString(),
					paramNames, values);
		} catch (RuntimeException re) {
			throw re;
		}
	}

}
