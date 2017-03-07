package com.saiten.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.saiten.dao.AnswerDetailDAO;
import com.saiten.dao.support.SaitenHibernateDAOSupport;

/**
 * The Class AnswerDetailDAOImpl.
 * 
 * @author suwarna
 * @version $Revision: 1.0 $
 */
public class AnswerDetailDAOImpl extends SaitenHibernateDAOSupport implements AnswerDetailDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saiten.dao.AnswerDetailDAO#getObjectiveData(java.lang.String,
	 * java.lang.String)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Object> getObjectiveData(String connectionString, String schoolCodes) {
		// StatelessSession session = null;
		Session session = null;
		try {
			final StringBuilder query = new StringBuilder();

			query.append("SELECT question_seq,mark_value,count(*) as cnt\n");
			query.append("FROM answer_detail_obj \n");
			query.append("GROUP BY question_seq,mark_value \n");
			query.append("ORDER BY question_seq,mark_value \n");
			/*
			 * Query queryObj = getHibernateTemplate(connectionString)
			 * .getSessionFactory().openStatelessSession()
			 * .createSQLQuery(query.toString()); return queryObj.list();
			 */
			return getHibernateTemplate(connectionString).execute(new HibernateCallback<List>() {
				public List doInHibernate(Session session) throws HibernateException {
					Query queryObj = session.createSQLQuery(query.toString());
					return queryObj.list();
				}
			});

		} catch (HibernateException hibernateException) {
			throw hibernateException;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

}
