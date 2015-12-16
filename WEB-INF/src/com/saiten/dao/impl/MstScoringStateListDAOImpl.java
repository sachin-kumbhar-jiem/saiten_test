package com.saiten.dao.impl;

import java.util.List;

import com.saiten.dao.MstScoringStateListDAO;
import com.saiten.dao.support.SaitenHibernateDAOSupport;
import com.saiten.util.WebAppConst;

/**
 * @author sachin
 * @version 1.0
 * @created 06-Dec-2012 2:35:44 PM
 */
public class MstScoringStateListDAOImpl extends SaitenHibernateDAOSupport
		implements MstScoringStateListDAO {

	@SuppressWarnings("rawtypes")
	@Override
	public List findScoringStateList() {
		StringBuilder query = new StringBuilder();
		query.append("SELECT mstScoringStateList.scoringState, mstScoringStateList.stateName ");
		query.append("FROM MstScoringStateList as mstScoringStateList ");
		query.append("WHERE mstScoringStateList.deleteFlag= :DELETE_FLAG ");
		query.append("ORDER BY mstScoringStateList.scoringState");

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
