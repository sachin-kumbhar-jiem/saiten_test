/**
 * 
 */
package com.saiten.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.saiten.dao.TranScorerAccessLogDAO;
import com.saiten.dao.support.SaitenHibernateDAOSupport;
import com.saiten.model.TranScorerAccessLog;
import com.saiten.util.SaitenUtil;
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
				Serializable id = getHibernateTemplate(
						SaitenUtil.getCommonDbConnectionString()).save(
						tranScorerAccessLog);
				return (Integer) id;
			} else {
				getHibernateTemplate(SaitenUtil.getCommonDbConnectionString())
						.saveOrUpdate(tranScorerAccessLog);
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

			return getHibernateTemplate(
					SaitenUtil.getCommonDbConnectionString())
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
		query.append("AND tranScorerAccessLog.scorerId = :SCORER_ID ");
		query.append("AND tranScorerAccessLog.status = :STATUS ");

		String[] params = { "ID", "SCORER_ID", "STATUS" };
		Object[] values = { id, scorerId, WebAppConst.SCORER_LOGGING_STATUS[0] };

		try {

			return getHibernateTemplate(
					SaitenUtil.getCommonDbConnectionString()).findByNamedParam(
					query.toString(), params, values);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	@SuppressWarnings("rawtypes")
	public List loginLogoutReport(final String dateString) {

		String connectionString = SaitenUtil.getCommonDbConnectionString();

		try {
			final StringBuilder queryString = new StringBuilder();
			queryString.append("SELECT * ");
			queryString.append("FROM ");
			queryString.append("tran_scorer_access_log ");
			queryString.append("WHERE ");
			queryString.append("login_time like :DATE_STRING");

			return getHibernateTemplate(connectionString).execute(
					new HibernateCallback<List>() {
						public List doInHibernate(Session session)
								throws HibernateException {
							Query queryObj = session.createSQLQuery(queryString
									.toString());

							queryObj.setParameter("DATE_STRING", dateString
									+ WebAppConst.PERCENTAGE_CHARACTER);
							return queryObj.list();
						}
					});

		} catch (RuntimeException re) {
			throw re;
		}
	}

}
