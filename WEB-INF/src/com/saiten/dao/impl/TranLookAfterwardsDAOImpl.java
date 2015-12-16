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

import com.saiten.dao.TranLookAfterwardsDAO;
import com.saiten.dao.support.SaitenHibernateDAOSupport;
import com.saiten.info.LookAfterwardsInfo;
import com.saiten.model.TranLookAfterwards;
import com.saiten.util.WebAppConst;

/**
 * @author user
 * 
 */
public class TranLookAfterwardsDAOImpl extends SaitenHibernateDAOSupport
		implements TranLookAfterwardsDAO {

	@Override
	public Integer save(TranLookAfterwards tranLookAfterwards,
			String connectionString) {

		try {
			Serializable id = getHibernateTemplate(connectionString).save(
					tranLookAfterwards);
			Integer lookAftSeq = (Integer) id;
			return lookAftSeq;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	@Override
	public void update(TranLookAfterwards tranLookAfterwards,
			String connectionString) {
		try {
			getHibernateTemplate(connectionString).update(tranLookAfterwards);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> fetchCommentsByAnswerSeq(Integer answerSeq,
			String connectionString) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT CONCAT(tranLookAfterwards.updatePersonId, ': ', ");
		query.append("tranLookAfterwards.comment) ");
		query.append("FROM TranLookAfterwards as tranLookAfterwards ");
		query.append("WHERE tranLookAfterwards.answerSeq = :ANSWER_SEQUENCE ");
		query.append("AND tranLookAfterwards.lookAftFlag = :VALID_FLAG ");

		String[] paramNames = { "ANSWER_SEQUENCE", "VALID_FLAG" };
		Object[] values = { answerSeq, WebAppConst.VALID_FLAG };

		try {
			return getHibernateTemplate(connectionString).findByNamedParam(
					query.toString(), paramNames, values);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	@Override
	public int unmarkAll(final LookAfterwardsInfo lookAfterwardsInfo,
			final String connectionString) {

		final StringBuilder query = new StringBuilder();
		query.append("UPDATE TranLookAfterwards ");
		query.append("SET lookAftFlag = :DELETE_FLAG, resolvedBy = :UPDATE_PERSON_ID, resolvedFlag = :VALID_FLAG, updateDate = :UPDATE_DATE ");
		query.append("WHERE answerSeq = :ANSWER_SEQUENCE ");
		query.append("AND resolvedFlag is null ");

		try {
			return getHibernateTemplate(connectionString).execute(
					new HibernateCallback<Integer>() {
						public Integer doInHibernate(Session session)
								throws HibernateException {

							Query queryObj = session.createQuery(query
									.toString());

							queryObj.setParameter("DELETE_FLAG",
									WebAppConst.DELETE_FLAG);
							queryObj.setParameter("UPDATE_PERSON_ID",
									lookAfterwardsInfo.getUpdatePersonId());
							queryObj.setParameter("UPDATE_DATE",
									lookAfterwardsInfo.getUpdateDate());
							queryObj.setParameter("ANSWER_SEQUENCE",
									lookAfterwardsInfo.getAnswerSeq());
							queryObj.setParameter("VALID_FLAG", WebAppConst.VALID_FLAG);
							/*
							 * System.out
							 * .println(">>>>>>>>> lockAnswer Query start: " +
							 * new Date().getTime());
							 */
							int updateCount = queryObj.executeUpdate();
							/*
							 * System.out
							 * .println(">>>>>>>>> lockAnswer Query end: " + new
							 * Date().getTime());
							 */
							return updateCount;
						}
					});
		} catch (RuntimeException re) {
			throw re;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public TranLookAfterwards fetchByAnswerSeqAndUpdatePersonId(
			Integer answerSeq, String updatePersonId, String connectionString) {
		StringBuilder query = new StringBuilder();
		query.append("FROM TranLookAfterwards as lft ");
		query.append("WHERE lft.answerSeq = :ANSWER_SEQUENCE ");
		query.append("AND lft.updatePersonId = :UPDATE_PERSON_ID ");
		query.append("AND lft.resolvedFlag is null ");

		String[] paramNames = { "ANSWER_SEQUENCE", "UPDATE_PERSON_ID" };
		Object[] values = { answerSeq, updatePersonId };

		try {

			List<TranLookAfterwards> list = getHibernateTemplate(
					connectionString).findByNamedParam(query.toString(),
					paramNames, values);
			if (!list.isEmpty() && list.size() > 0) {
				return list.get(0);
			} else {
				return null;
			}

		} catch (RuntimeException re) {
			throw re;
		}
	}

	@Override
	public TranLookAfterwards fetchById(Integer lookAftSeq, String connectionString) {
		return getHibernateTemplate(connectionString).get(TranLookAfterwards.class, lookAftSeq);
	}
}
