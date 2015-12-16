/**
 * 
 */
package com.saiten.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.saiten.dao.TranQualitycheckScoreDAO;
import com.saiten.dao.support.SaitenHibernateDAOSupport;
import com.saiten.model.TranQualitycheckScore;
import com.saiten.util.WebAppConst;

/**
 * @author rajeshwars
 * 
 */
public class TranQualitycheckScoreDAOImpl extends SaitenHibernateDAOSupport
		implements TranQualitycheckScoreDAO {

	@Override
	public void save(TranQualitycheckScore tranQualitycheckScore,
			String connectionString) {
		try {
			getHibernateTemplate(connectionString).save(tranQualitycheckScore);
		} catch (RuntimeException re) {
			throw re;
		}

	}

	@SuppressWarnings("unchecked")
	public int findQcHistoryRecordCount(String scorerId,
			List<Integer> questionSeq, String connectionString,
			List<Short> scoringStateList, boolean bookmarkScreenFlag) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT COUNT(*) ");
		query.append("FROM TranQualitycheckScore as tranQualitycheckScore ");
		query.append("WHERE tranQualitycheckScore.scorerId = :SCORER_ID ");
		if ((questionSeq != null) && (!questionSeq.isEmpty())) {
			query.append("AND tranQualitycheckScore.questionSeq IN :QUESTION_SEQUENCE ");
		}
		query.append("AND tranQualitycheckScore.scoringState IN :SCORING_STATE_LIST ");
		query.append("AND tranQualitycheckScore.validFlag = :VALID_FLAG ");
		if (bookmarkScreenFlag) {
			query.append("AND tranQualitycheckScore.bookMarkFlag = :BOOKMARK_FLAG_TRUE ");
		} else {
			query.append("AND DATE_FORMAT(tranQualitycheckScore.createDate, '%Y-%m-%d') = ");
			query.append("DATE_FORMAT(:CREATE_DATE, '%Y-%m-%d') ");
		}

		List<String> paramNameList = new ArrayList<String>();
		@SuppressWarnings("rawtypes")
		List valueList = new ArrayList();

		paramNameList.add(0, "SCORER_ID");
		paramNameList.add(1, "SCORING_STATE_LIST");
		paramNameList.add(2, "VALID_FLAG");
		if ((questionSeq != null) && (!questionSeq.isEmpty())) {
			paramNameList.add("QUESTION_SEQUENCE");
		}
		if (bookmarkScreenFlag) {
			paramNameList.add("BOOKMARK_FLAG_TRUE");
		} else {
			paramNameList.add("CREATE_DATE");
		}

		valueList.add(0, scorerId);
		valueList.add(1, scoringStateList);
		valueList.add(2, WebAppConst.VALID_FLAG);
		if ((questionSeq != null) && (!questionSeq.isEmpty())) {
			valueList.add(questionSeq);
		}
		if (bookmarkScreenFlag) {
			valueList.add(WebAppConst.BOOKMARK_FLAG_TRUE);
		} else {
			valueList.add(new Date());
		}

		String[] paramNames = paramNameList.toArray(new String[paramNameList
				.size()]);
		Object[] values = valueList.toArray(new Object[valueList.size()]);

		try {
			return Integer.valueOf(DataAccessUtils.uniqueResult(
					getHibernateTemplate(connectionString).findByNamedParam(
							query.toString(), paramNames, values)).toString());

		} catch (RuntimeException re) {
			throw re;
		}
	}

	@SuppressWarnings("rawtypes")
	public List findHistoryInfoList(final String scorerId,
			final List<Short> scoringStateList,
			final List<Integer> questionSequence,
			final String connectionString, final Integer startRecord,
			final Integer endRecord, final boolean bookmarkScreenFlag) {
		final StringBuilder query = new StringBuilder();
		query.append("SELECT tranQualitycheckScore.qcSeq, ");
		query.append("tranQualitycheckScore.answerFormNum, ");
		query.append("tranQualitycheckScore.scoringState, ");
		query.append("tranQualitycheckScore.updateDate, ");
		query.append("tranQualitycheckScore.gradeSeq, ");
		query.append("tranQualitycheckScore.pendingCategorySeq, ");
		query.append("tranQualitycheckScore.scorerComment, ");
		query.append("tranQualitycheckScore.questionSeq ");
		query.append("FROM TranQualitycheckScore as tranQualitycheckScore ");
		query.append("WHERE tranQualitycheckScore.scorerId = :SCORER_ID ");
		query.append("AND tranQualitycheckScore.questionSeq IN :QUESTION_SEQUENCE ");
		query.append("AND tranQualitycheckScore.scoringState IN :SCORING_STATE_LIST ");
		query.append("AND tranQualitycheckScore.validFlag = :VALID_FLAG ");

		query.append("AND DATE_FORMAT(tranQualitycheckScore.createDate, '%Y-%m-%d') = ");
		query.append("DATE_FORMAT(:CREATE_DATE, '%Y-%m-%d') ");
		query.append("ORDER BY tranQualitycheckScore.updateDate DESC ");

		try {

			return getHibernateTemplate(connectionString).execute(
					new HibernateCallback<List>() {
						public List doInHibernate(Session session)
								throws HibernateException {
							Query queryObj = session.createQuery(query
									.toString());

							queryObj.setParameter("SCORER_ID", scorerId);
							queryObj.setParameterList("QUESTION_SEQUENCE",
									questionSequence);
							queryObj.setParameterList("SCORING_STATE_LIST",
									scoringStateList);
							queryObj.setParameter("VALID_FLAG",
									WebAppConst.VALID_FLAG);
							queryObj.setParameter("CREATE_DATE", new Date());

							if (endRecord != null) {
								queryObj.setFirstResult(startRecord);
								queryObj.setMaxResults(endRecord);
							}
							return queryObj.list();
						}
					});

		} catch (RuntimeException re) {
			throw re;
		}
	}

	@SuppressWarnings("rawtypes")
	public List findQcHistoryAnswer(Integer qcSeq, String connectionString) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT tranQualitycheckScore.tranDescScore.answerSeq, tranQualitycheckScore.answerFormNum, ");
		query.append("tranQualitycheckScore.tranDescScore.imageFileName, ");
		query.append("tranQualitycheckScore.scorerComment, ");
		query.append("tranQualitycheckScore.gradeSeq, tranQualitycheckScore.bitValue, ");
		query.append("tranQualitycheckScore.updateDate, tranQualitycheckScore.pendingCategorySeq, ");
		query.append("tranQualitycheckScore.scoringState, tranQualitycheckScore.questionSeq ");
		query.append("FROM TranQualitycheckScore as tranQualitycheckScore ");
		query.append("WHERE tranQualitycheckScore.qcSeq = :QC_SEQUENCE ");
		query.append("AND tranQualitycheckScore.validFlag = :VALID_FLAG ");
		// query.append("AND tranQualitycheckScore.tranDescScore.lockFlag = :UNLOCK ");
		// query.append("AND tranQualitycheckScore.tranDescScore.validFlag = :VALID_FLAG ");

		String[] paramNames = { "QC_SEQUENCE", "VALID_FLAG" /* , "UNLOCK" */};
		Object[] values = { qcSeq, WebAppConst.VALID_FLAG /* , WebAppConst.UNLOCK */};
		try {
			HibernateTemplate hibernateTemplate = getHibernateTemplate(connectionString);

			return hibernateTemplate.findByNamedParam(query.toString(),
					paramNames, values);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saiten.dao.TranDescScoreDAO#findById(int, java.lang.String)
	 */
	@Override
	public TranQualitycheckScore findById(int qcSeq, String connectionString) {
		try {
			return getHibernateTemplate(connectionString).get(
					TranQualitycheckScore.class, qcSeq);
		} catch (RuntimeException re) {
			throw re;
		}
	}

}
