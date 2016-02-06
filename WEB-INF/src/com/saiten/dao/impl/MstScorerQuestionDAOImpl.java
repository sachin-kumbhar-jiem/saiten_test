package com.saiten.dao.impl;

import java.util.List;

import org.apache.commons.lang.ArrayUtils;

import com.saiten.dao.MstScorerQuestionDAO;
import com.saiten.dao.support.SaitenHibernateDAOSupport;
import com.saiten.model.MstScorerQuestion;
import com.saiten.util.WebAppConst;

/**
 * @author sachin
 * @version 1.0
 * @created 06-Dec-2012 2:35:54 PM
 */
public class MstScorerQuestionDAOImpl extends SaitenHibernateDAOSupport
		implements MstScorerQuestionDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.saiten.dao.MstScorerQuestionDAO#findQuestionsByMenuIdAndScorerId(
	 * java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List findQuestionsByMenuIdAndScorerId(String menuId, String scorerId) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT CONCAT(mstScorerQuestion.mstQuestion.mstSubject.subjectShortName, '-', ");
		query.append("mstScorerQuestion.mstQuestion.questionNum), mstScorerQuestion.mstQuestion.questionSeq ");
		query.append("FROM MstScorerQuestion as mstScorerQuestion ");
		query.append("WHERE mstScorerQuestion.mstScorer.scorerId = :SCORER_ID ");
		query.append("AND mstScorerQuestion.mstQuestion.mstEvaluation.scoreType != :SCORE_TYPE ");
		query.append("AND mstScorerQuestion.mstQuestion.mstSubject.scoreFlag = :SCORE_FLAG ");
		query.append("AND mstScorerQuestion.mstQuestion.deleteFlag = :DELETE_FLAG ");
		query.append("AND mstScorerQuestion.deleteFlag = :DELETE_FLAG ");
		query.append("AND mstScorerQuestion.mstQuestion.mstEvaluation.deleteFlag = :DELETE_FLAG ");
		query.append("AND mstScorerQuestion.mstQuestion.mstSubject.deleteFlag = :DELETE_FLAG ");

		if (menuId.equals(WebAppConst.FIRST_SCORING_MENU_ID)
				|| menuId.equals(WebAppConst.SECOND_SCORING_MENU_ID)
				|| menuId.equals(WebAppConst.CHECKING_MENU_ID)
				|| menuId.equals(WebAppConst.INSPECTION_MENU_ID)
				|| menuId.equals(WebAppConst.DENY_MENU_ID)
				|| menuId.equals(WebAppConst.PENDING_MENU_ID)
				|| menuId.equals(WebAppConst.MISMATCH_MENU_ID)
				|| menuId.equals(WebAppConst.NO_GRADE_MENU_ID)
				|| menuId.equals(WebAppConst.OUT_BOUNDARY_MENU_ID)) {
			query.append("AND mstScorerQuestion.mstQuestion.mstEvaluation.mstQuestionType.questionType IN :QUESTION_TYPE ");
			query.append("AND mstScorerQuestion.mstQuestion.mstEvaluation.mstQuestionType.deleteFlag = :DELETE_FLAG ");
		}

		query.append("ORDER BY mstScorerQuestion.mstQuestion.mstSubject.subjectShortName, ");
		query.append("mstScorerQuestion.mstQuestion.questionNum ");

		Object[] questionType = null;
		if (menuId.equals(WebAppConst.FIRST_SCORING_MENU_ID)
				|| menuId.equals(WebAppConst.INSPECTION_MENU_ID)
				|| menuId.equals(WebAppConst.DENY_MENU_ID)
				|| menuId.equals(WebAppConst.PENDING_MENU_ID)
				|| menuId.equals(WebAppConst.NO_GRADE_MENU_ID)
				|| menuId.equals(WebAppConst.OUT_BOUNDARY_MENU_ID)) {
			// for both short and long type questions
			questionType = WebAppConst.QUESTION_TYPE;
		} else if (menuId.equals(WebAppConst.SECOND_SCORING_MENU_ID)
				|| menuId.equals(WebAppConst.MISMATCH_MENU_ID)) {
			// only for short type questions
			questionType = ArrayUtils.subarray(WebAppConst.QUESTION_TYPE, 0, 1);
		} else if (menuId.equals(WebAppConst.CHECKING_MENU_ID)) {
			// only for long type questions
			questionType = ArrayUtils.subarray(WebAppConst.QUESTION_TYPE, 1, 2);
		}

		String[] paramNames = { "SCORER_ID", "SCORE_TYPE", "SCORE_FLAG",
				"QUESTION_TYPE", "DELETE_FLAG" };
		Object[] values = { scorerId, WebAppConst.SCORE_TYPE[0],
				WebAppConst.SCORE_FLAG, questionType, WebAppConst.DELETE_FLAG };

		try {
			return getHibernateTemplate().findByNamedParam(query.toString(),
					paramNames, values);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getQuestionSequencesByScorerId(String scorerId) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT mstScorerQuestion.mstQuestion.questionSeq ");
		query.append("FROM MstScorerQuestion as mstScorerQuestion ");
		query.append("WHERE mstScorerQuestion.mstQuestion.mstEvaluation.scoreType != :SCORE_TYPE ");
		query.append("AND mstScorerQuestion.mstScorer.scorerId = :SCORER_ID ");
		query.append("AND mstScorerQuestion.mstQuestion.mstSubject.scoreFlag = :SCORE_FLAG ");
		query.append("AND mstScorerQuestion.mstQuestion.deleteFlag = :DELETE_FLAG ");
		query.append("AND mstScorerQuestion.deleteFlag = :DELETE_FLAG ");
		query.append("AND mstScorerQuestion.mstQuestion.mstEvaluation.deleteFlag = :DELETE_FLAG ");
		query.append("AND mstScorerQuestion.mstQuestion.mstSubject.deleteFlag = :DELETE_FLAG ");
		query.append("AND mstScorerQuestion.mstQuestion.mstEvaluation.mstQuestionType.deleteFlag = :DELETE_FLAG ");
		//query.append("ORDER BY mstScorerQuestion.id.questionSeq, ");
		query.append("ORDER BY mstScorerQuestion.mstQuestion.mstSubject.subjectShortName, ");
		query.append("mstScorerQuestion.mstQuestion.questionNum ");

		String[] paramNames = { "SCORER_ID", "SCORE_TYPE", "SCORE_FLAG",
		/* "QUESTION_TYPE", */"DELETE_FLAG" };
		Object[] values = { scorerId, WebAppConst.SCORE_TYPE[0],
				WebAppConst.SCORE_FLAG, /* questionType, */
				WebAppConst.DELETE_FLAG };

		try {
			return getHibernateTemplate().findByNamedParam(query.toString(),
					paramNames, values);
		} catch (RuntimeException re) {
			throw re;
		}

	}

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @Override public List<MstScorerQuestion> findByScorerId(String scorerId)
	 * { StringBuilder query = new StringBuilder();
	 * query.append("FROM MstScorerQuestion as mstScorerQuestion ");
	 * query.append(
	 * "WHERE mstScorerQuestion.mstQuestion.mstEvaluation.scoreType != :SCORE_TYPE "
	 * );
	 * query.append("AND mstScorerQuestion.mstScorer.scorerId = :SCORER_ID ");
	 * query.append(
	 * "AND mstScorerQuestion.mstQuestion.mstSubject.scoreFlag = :SCORE_FLAG ");
	 * query
	 * .append("AND mstScorerQuestion.mstQuestion.deleteFlag = :DELETE_FLAG ");
	 * query.append("AND mstScorerQuestion.deleteFlag = :DELETE_FLAG ");
	 * query.append
	 * ("AND mstScorerQuestion.mstQuestion.mstEvaluation.deleteFlag = :DELETE_FLAG "
	 * ); query.append(
	 * "AND mstScorerQuestion.mstQuestion.mstSubject.deleteFlag = :DELETE_FLAG "
	 * );
	 * 
	 * query.append(
	 * "AND mstScorerQuestion.mstQuestion.mstEvaluation.mstQuestionType.deleteFlag = :DELETE_FLAG "
	 * ); query.append("ORDER BY mstScorerQuestion.id.questionSeq, ");
	 * query.append
	 * ("mstScorerQuestion.mstQuestion.mstSubject.subjectShortName, ");
	 * query.append("mstScorerQuestion.mstQuestion.questionNum ");
	 * 
	 * 
	 * String[] paramNames = { "SCORER_ID", "SCORE_TYPE", "SCORE_FLAG",
	 * "DELETE_FLAG" }; Object[] values = { scorerId, WebAppConst.SCORE_TYPE[0],
	 * WebAppConst.SCORE_FLAG, WebAppConst.DELETE_FLAG };
	 * 
	 * try { return getHibernateTemplate().findByNamedParam(query.toString(),
	 * paramNames, values); } catch (RuntimeException re) { throw re; } }
	 */

	/*
	 * @Override public int updateIds() { final StringBuilder query = new
	 * StringBuilder(); query.append("UPDATE MstScorerQuestion ");
	 * query.append("SET id.scorerId = 'AS400' ");
	 * query.append("WHERE id.scorerId = 'ES00010' ");
	 * 
	 * try { return getHibernateTemplate().execute( new
	 * HibernateCallback<Integer>() { public Integer doInHibernate(Session
	 * session) throws HibernateException {
	 * 
	 * Query queryObj = session.createQuery(query .toString()); return
	 * queryObj.executeUpdate(); } }); } catch (RuntimeException re) { throw re;
	 * } }
	 */

	@Override
	public void saveAll(List<MstScorerQuestion> mstScorerQuestionList) {
		try {
			for (MstScorerQuestion mstScorerQuestionObj :mstScorerQuestionList ) {
				getHibernateTemplate().save(mstScorerQuestionObj);
			}
			//getHibernateTemplate().saveOrUpdateAll(mstScorerQuestionList);

		} catch (RuntimeException re) {
			throw re;
		}
	}

	@Override
	public void deleteByScorerid(String scorerId) {
		try {

			getHibernateTemplate().bulkUpdate(
					"DELETE MstScorerQuestion WHERE mstScorer.scorerId = ?",
					scorerId);

		} catch (RuntimeException re) {
			re.printStackTrace();
		}
	}

}