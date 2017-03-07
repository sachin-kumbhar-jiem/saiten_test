package com.saiten.dao.impl;

import java.io.Serializable;
import java.util.List;

import com.saiten.dao.TranAcceptanceHistoryDao;
import com.saiten.dao.support.SaitenHibernateDAOSupport;
import com.saiten.model.TranAcceptanceHistory;

public class TranAcceptanceHistoryDaoImpl extends SaitenHibernateDAOSupport implements TranAcceptanceHistoryDao {

	@Override
	public Integer save(TranAcceptanceHistory tranAcceptanceHistory, String connectionString) {

		try {
			Serializable id = getHibernateTemplate(connectionString).save(tranAcceptanceHistory);
			Integer acceptanceSeq = (Integer) id;
			return acceptanceSeq;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	@Override
	public void update(TranAcceptanceHistory tranAcceptanceHistory, String connectionString) {

		try {
			getHibernateTemplate(connectionString).update(tranAcceptanceHistory);
		} catch (RuntimeException re) {
			throw re;
		}

	}

	@Override
	public TranAcceptanceHistory fetchByAnswerSeqAndUpdatePersonId(Integer answerSeq, String updatePersonId,
			String connectionString) {
		StringBuilder query = new StringBuilder();
		query.append("FROM TranAcceptanceHistory as ta ");
		query.append("WHERE ta.answerSeq = :ANSWER_SEQ ");
		query.append("AND ta.markBy = :UPDATE_PERSON_ID ");
		query.append("AND ta.explainFlag IS NULL");

		String[] paramNames = { "ANSWER_SEQ", "UPDATE_PERSON_ID" };
		Object[] values = { answerSeq, updatePersonId };

		try {
			@SuppressWarnings("unchecked")
			List<TranAcceptanceHistory> list = getHibernateTemplate(connectionString).findByNamedParam(query.toString(),
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

}
