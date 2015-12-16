/**
 * 
 */
package com.saiten.dao.impl;

import java.io.Serializable;
import java.util.List;

import com.saiten.dao.TranScorerAccessLogDAO;
import com.saiten.dao.support.SaitenHibernateDAOSupport;
import com.saiten.model.TranScorerAccessLog;
import com.saiten.util.WebAppConst;

/**
 * @author user
 * 
 */
public class TranScorerAccessLogDAOImpl extends SaitenHibernateDAOSupport
		implements TranScorerAccessLogDAO {

	@Override
	public Integer saveOrUpdate(TranScorerAccessLog tranScorerAccessLog) {

		try {
			if (tranScorerAccessLog.getId() == null) {
				Serializable id = getHibernateTemplate().save(
						tranScorerAccessLog);
				return (Integer) id;
			} else {
				getHibernateTemplate().saveOrUpdate(tranScorerAccessLog);
				return tranScorerAccessLog.getId();
			}
		} catch (RuntimeException re) {
			throw re;
		}
	}

	@Override
	public String findStatusById(Integer id) {

		StringBuilder query = new StringBuilder();
		query.append("SELECT tranScorerAccessLog.status ");
		query.append("FROM TranScorerAccessLog as tranScorerAccessLog ");
		query.append("WHERE tranScorerAccessLog.id = :ID ");

		String[] params = { "ID" };
		Object[] values = { id };

		try {

			return getHibernateTemplate()
					.findByNamedParam(query.toString(), params, values).get(0)
					.toString();
		} catch (RuntimeException re) {
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TranScorerAccessLog> getAllLoggedInScorers(Integer id,
			String scorerId) {

		StringBuilder query = new StringBuilder();
		query.append("FROM TranScorerAccessLog as tranScorerAccessLog ");
		query.append("WHERE tranScorerAccessLog.id != :ID ");
		query.append("AND tranScorerAccessLog.mstScorer.scorerId = :SCORER_ID ");
		query.append("AND tranScorerAccessLog.status = :STATUS ");

		String[] params = { "ID", "SCORER_ID", "STATUS" };
		Object[] values = { id, scorerId, WebAppConst.SCORER_LOGGING_STATUS[0] };

		try {

			return getHibernateTemplate().findByNamedParam(query.toString(),
					params, values);
		} catch (RuntimeException re) {
			throw re;
		}
	}

}
