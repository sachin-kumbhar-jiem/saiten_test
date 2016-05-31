package com.saiten.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.saiten.dao.TranAcceptanceDao;
import com.saiten.dao.support.SaitenHibernateDAOSupport;
import com.saiten.model.TranAcceptance;
import com.saiten.util.WebAppConst;

public class TranAcceptanceDaoImpl extends SaitenHibernateDAOSupport implements
		TranAcceptanceDao {

	@Override
	public Integer save(TranAcceptance tranAcceptance, String connectionString) {
		try {
			Serializable id = getHibernateTemplate(connectionString).save(
					tranAcceptance);
			Integer acceptanceSeq = (Integer) id;
			return acceptanceSeq;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	@Override
	public void update(TranAcceptance tranAcceptance, String connectionString) {

		try {
			getHibernateTemplate(connectionString).update(tranAcceptance);
		} catch (RuntimeException re) {
			throw re;
		}

	}

	@Override
	public TranAcceptance fetchByAnswerSeqAndUpdatePersonId(Integer answerSeq,
			String updatePersonId, String connectionString) {
		StringBuilder query = new StringBuilder();
		query.append("FROM TranAcceptance as ta ");
		query.append("WHERE ta.tranDescScore.answerSeq = :ANSWER_SEQ ");
		query.append("AND ta.markBy = :UPDATE_PERSON_ID ");
		/*query.append("AND ( ta.explainFlag IS NULL ");
		query.append("OR ta.explainFlag = :FALSE )");*/

		String[] paramNames = { "ANSWER_SEQ", "UPDATE_PERSON_ID" };
		Object[] values = { answerSeq, updatePersonId };

		try {
			@SuppressWarnings("unchecked")
			List<TranAcceptance> list = getHibernateTemplate(connectionString)
					.findByNamedParam(query.toString(), paramNames, values);
			if (!list.isEmpty() && list.size() > 0) {
				return list.get(0);
			} else {
				return null;
			}
		} catch (RuntimeException re) {
			throw re;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<TranAcceptance> fetchByQuestionNumAndMarkBy(
			final Integer questionSeq, final String markBy,
			final String searchCriteria, final String connectionString) {

		final StringBuilder query = new StringBuilder();
		query.append("FROM TranAcceptance as ta ");
		query.append("WHERE ta.tranDescScore.questionSeq = :QUESTION_SEQ ");
		query.append("AND ta.markBy = :MARK_BY ");
		query.append("AND ta.markFlag = :MARK_FLAG ");
		if (searchCriteria.equals(WebAppConst.KENSHU_SEARCH_UNEXPLAINED_STRING)) {
			query.append("AND ta.explainFlag IS NULL OR ");
			query.append("ta.explainFlag = :FALSE ");
		} else if (searchCriteria
				.equals(WebAppConst.KENSHU_SEARCH_EXPLAINED_STRING)) {
			query.append("AND ta.explainFlag = :TRUE ");
		}
		// String[] paramNames = { "QUESTION_SEQ", "MARK_BY" };
		// Object[] values = { questionSeq, markBy };

		try {
			/*
			 * @SuppressWarnings("unchecked") List<TranAcceptance> list =
			 * getHibernateTemplate(connectionString)
			 * .findByNamedParam(query.toString(), paramNames, values); return
			 * list;
			 */

			return getHibernateTemplate(connectionString).execute(
					new HibernateCallback<List>() {
						public List doInHibernate(Session session)
								throws HibernateException {
							Query queryObj = session.createQuery(query
									.toString());
							queryObj.setParameter("QUESTION_SEQ", questionSeq);
							queryObj.setParameter("MARK_BY", markBy);
							queryObj.setParameter("MARK_FLAG", WebAppConst.VALID_FLAG);
							if (searchCriteria
									.equals(WebAppConst.KENSHU_SEARCH_UNEXPLAINED_STRING)) {
								queryObj.setParameter("FALSE", WebAppConst.F);
							} else if (searchCriteria
									.equals(WebAppConst.KENSHU_SEARCH_EXPLAINED_STRING)) {
								queryObj.setParameter("TRUE",
										WebAppConst.VALID_FLAG);
							}
							return queryObj.list();
						}
					});

		} catch (RuntimeException e) {
			throw e;
		}

	}

}
