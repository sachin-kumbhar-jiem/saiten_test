/**
 * 
 */
package com.saiten.dao.impl;

import com.saiten.dao.TranScorerSessionInfoDAO;
import com.saiten.dao.support.SaitenHibernateDAOSupport;
import com.saiten.model.TranScorerSessionInfo;

/**
 * @author user
 * 
 */
public class TranScorerSessionInfoDAOImpl extends SaitenHibernateDAOSupport
		implements TranScorerSessionInfoDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.saiten.dao.TranScorerSessionInfoDAO#saveOrUpdate(com.saiten.model
	 * .TranScorerSessionInfo)
	 */
	@Override
	public void saveOrUpdate(TranScorerSessionInfo tranScorerSessionInfo) {
		try {

			getHibernateTemplate().saveOrUpdate(tranScorerSessionInfo);

		} catch (RuntimeException re) {
			throw re;
		}
	}

	@Override
	public TranScorerSessionInfo findById(String scorerId) {
		try {

			return getHibernateTemplate().get(TranScorerSessionInfo.class,
					scorerId);

		} catch (RuntimeException re) {
			throw re;
		}
	}

	@Override
	public Long countScorersByAnswerFormNumAndSubjectCode(String answerFormNum,
			String subjectCode) {

		StringBuilder query = new StringBuilder();
		query.append("SELECT count(scorerId) ");
		query.append("FROM TranScorerSessionInfo as tranScorerSessionInfo ");
		query.append("WHERE tranScorerSessionInfo.answerFormNum = :ANSWER_FORM_NUM ");
		query.append("AND tranScorerSessionInfo.subjectCode = :SUBJECT_CODE ");

		String[] params = { "ANSWER_FORM_NUM", "SUBJECT_CODE" };
		Object[] values = { answerFormNum, subjectCode };

		try {

			return (Long) getHibernateTemplate().findByNamedParam(
					query.toString(), params, values).get(0);

		} catch (RuntimeException re) {
			throw re;
		}
	}

}
