package com.saiten.dao.impl;

import java.util.List;

import com.saiten.dao.MstSamplingEventCondDAO;
import com.saiten.dao.support.SaitenHibernateDAOSupport;
import com.saiten.util.WebAppConst;

/**
 * @author sachin
 * @version 1.0
 * @created 05-Dec-2012 4:59:11 PM
 */
public class MstSamplingEventCondDAOImpl extends SaitenHibernateDAOSupport
		implements MstSamplingEventCondDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.saiten.dao.MstSamplingEventCondDAO#findHistoryEventList(java.lang
	 * .String)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List findHistoryEventList(String screenId) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT mstSamplingEventCond.id.eventId, mstSamplingEventCond.mstScoringEventList.eventName ");
		query.append("FROM MstSamplingEventCond as mstSamplingEventCond ");
		query.append("WHERE mstSamplingEventCond.id.screenId = :SCREEN_ID ");
		query.append("AND mstSamplingEventCond.deleteFlag= :DELETE_FLAG ");
		query.append("AND mstSamplingEventCond.mstScoringEventList.deleteFlag= :DELETE_FLAG ");
		query.append("ORDER BY mstSamplingEventCond.displayIndex");

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
