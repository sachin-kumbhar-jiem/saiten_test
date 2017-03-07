package com.saiten.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.saiten.dao.MstQuestionDAO;
import com.saiten.dao.support.SaitenHibernateDAOSupport;
import com.saiten.model.MstQuestion;
import com.saiten.util.SaitenUtil;
import com.saiten.util.WebAppConst;

/**
 * @author sachin
 * @version 1.0
 * @created 05-Dec-2012 4:59:11 PM
 */
public class MstQuestionDAOImpl extends SaitenHibernateDAOSupport implements MstQuestionDAO {

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @Override public List<MstQuestion> findAll() { StringBuilder query = new
	 * StringBuilder(); query.append("FROM MstQuestion as mstQuestion ");
	 * query.append("WHERE mstQuestion.deleteFlag= :DELETE_FLAG ");
	 * query.append("AND mstQuestion.mstSubject.deleteFlag= :DELETE_FLAG ");
	 * query.append("ORDER BY mstQuestion.questionSeq"); String[] paramNames = {
	 * "DELETE_FLAG" }; Object[] values = { WebAppConst.DELETE_FLAG };
	 * 
	 * try { return getHibernateTemplate().findByNamedParam(query.toString(),
	 * paramNames, values); } catch (RuntimeException re) { throw re; } }
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saiten.dao.MstQuestionDAO#findAll(java.lang.String)
	 */

	@SuppressWarnings("unchecked")
	@Override
	public List<MstQuestion> findAll() {
		StringBuilder query = new StringBuilder();
		// query.append("SELECT CONCAT(mstQuestion.mstSubject.subjectShortName,
		// '-', ");
		// query.append("mstQuestion.questionNum), mstQuestion.questionSeq ");
		query.append("FROM MstQuestion as mstQuestion ");
		query.append("WHERE mstQuestion.mstEvaluation.scoreType != :SCORE_TYPE ");
		query.append("AND mstQuestion.mstSubject.scoreFlag = :SCORE_FLAG ");
		query.append("AND mstQuestion.deleteFlag = :DELETE_FLAG ");
		query.append("AND mstQuestion.mstEvaluation.deleteFlag = :DELETE_FLAG ");
		query.append("AND mstQuestion.mstSubject.deleteFlag = :DELETE_FLAG ");

		/*
		 * if (menuId.equals(WebAppConst.FIRST_SCORING_MENU_ID) ||
		 * menuId.equals(WebAppConst.SECOND_SCORING_MENU_ID) ||
		 * menuId.equals(WebAppConst.CHECKING_MENU_ID) ||
		 * menuId.equals(WebAppConst.INSPECTION_MENU_ID) ||
		 * menuId.equals(WebAppConst.DENY_MENU_ID) ||
		 * menuId.equals(WebAppConst.PENDING_MENU_ID) ||
		 * menuId.equals(WebAppConst.MISMATCH_MENU_ID) ||
		 * menuId.equals(WebAppConst.NO_GRADE_MENU_ID) ||
		 * menuId.equals(WebAppConst.OUT_BOUNDARY_MENU_ID)) { query.append(
		 * "AND mstQuestion.mstEvaluation.mstQuestionType.questionType IN :QUESTION_TYPE "
		 * );
		 * 
		 * }
		 */

		query.append("AND mstQuestion.mstEvaluation.mstQuestionType.deleteFlag = :DELETE_FLAG ");
		query.append("ORDER BY mstQuestion.mstSubject.subjectShortName, ");
		query.append("mstQuestion.questionNum ");

		/*
		 * Object[] questionType = null; if
		 * (menuId.equals(WebAppConst.FIRST_SCORING_MENU_ID) ||
		 * menuId.equals(WebAppConst.INSPECTION_MENU_ID) ||
		 * menuId.equals(WebAppConst.DENY_MENU_ID) ||
		 * menuId.equals(WebAppConst.PENDING_MENU_ID) ||
		 * menuId.equals(WebAppConst.NO_GRADE_MENU_ID) ||
		 * menuId.equals(WebAppConst.OUT_BOUNDARY_MENU_ID)) { questionType =
		 * WebAppConst.QUESTION_TYPE; } else if
		 * (menuId.equals(WebAppConst.SECOND_SCORING_MENU_ID) ||
		 * menuId.equals(WebAppConst.MISMATCH_MENU_ID)) { questionType =
		 * ArrayUtils.subarray(WebAppConst.QUESTION_TYPE, 0, 1); } else if
		 * (menuId.equals(WebAppConst.CHECKING_MENU_ID)) { // only for long type
		 * questions questionType =
		 * ArrayUtils.subarray(WebAppConst.QUESTION_TYPE, 1, 2); }
		 */

		String[] paramNames = { "SCORE_TYPE", "SCORE_FLAG", /* "QUESTION_TYPE", */
				"DELETE_FLAG" };
		Object[] values = { WebAppConst.SCORE_TYPE[0], WebAppConst.SCORE_FLAG,
				/* questionType, */WebAppConst.DELETE_FLAG };
		try {
			return getHibernateTemplate().findByNamedParam(query.toString(), paramNames, values);
		} catch (RuntimeException re) {
			throw re;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saiten.dao.MstQuestionDAO#findAll(java.lang.String,
	 * java.lang.String)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List findAll(String menuId, String scorerId) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT CONCAT(mstQuestion.mstSubject.subjectShortName, '-', ");
		query.append("mstQuestion.questionNum), mstQuestion.questionSeq ");
		query.append("FROM MstQuestion as mstQuestion ");
		query.append("WHERE mstQuestion.mstEvaluation.scoreType != :SCORE_TYPE ");
		query.append("AND mstQuestion.mstSubject.scoreFlag = :SCORE_FLAG ");
		query.append("AND mstQuestion.deleteFlag = :DELETE_FLAG ");
		query.append("AND mstQuestion.mstEvaluation.deleteFlag = :DELETE_FLAG ");
		query.append("AND mstQuestion.mstSubject.deleteFlag = :DELETE_FLAG ");

		if (menuId.equals(WebAppConst.FIRST_SCORING_MENU_ID) || menuId.equals(WebAppConst.SECOND_SCORING_MENU_ID)
				|| menuId.equals(WebAppConst.CHECKING_MENU_ID) || menuId.equals(WebAppConst.INSPECTION_MENU_ID)
				|| menuId.equals(WebAppConst.DENY_MENU_ID) || menuId.equals(WebAppConst.PENDING_MENU_ID)
				|| menuId.equals(WebAppConst.MISMATCH_MENU_ID) || menuId.equals(WebAppConst.NO_GRADE_MENU_ID)
				|| menuId.equals(WebAppConst.OUT_BOUNDARY_MENU_ID)) {
			query.append("AND mstQuestion.mstEvaluation.mstQuestionType.questionType IN :QUESTION_TYPE ");
			query.append("AND mstQuestion.mstEvaluation.mstQuestionType.deleteFlag = :DELETE_FLAG ");
		}

		query.append("ORDER BY mstQuestion.mstSubject.subjectShortName, ");
		query.append("mstQuestion.questionNum ");

		Object[] questionType = null;
		if (menuId.equals(WebAppConst.FIRST_SCORING_MENU_ID) || menuId.equals(WebAppConst.INSPECTION_MENU_ID)
				|| menuId.equals(WebAppConst.DENY_MENU_ID) || menuId.equals(WebAppConst.PENDING_MENU_ID)
				|| menuId.equals(WebAppConst.NO_GRADE_MENU_ID) || menuId.equals(WebAppConst.OUT_BOUNDARY_MENU_ID)) {
			questionType = WebAppConst.QUESTION_TYPE;
		} else if (menuId.equals(WebAppConst.SECOND_SCORING_MENU_ID) || menuId.equals(WebAppConst.MISMATCH_MENU_ID)) {
			questionType = ArrayUtils.subarray(WebAppConst.QUESTION_TYPE, 0, 1);
		} else if (menuId.equals(WebAppConst.CHECKING_MENU_ID)) {
			// only for long type questions
			questionType = ArrayUtils.subarray(WebAppConst.QUESTION_TYPE, 1, 2);
		}

		String[] paramNames = { "SCORE_TYPE", "SCORE_FLAG", "QUESTION_TYPE", "DELETE_FLAG" };
		Object[] values = { WebAppConst.SCORE_TYPE[0], WebAppConst.SCORE_FLAG, questionType, WebAppConst.DELETE_FLAG };
		try {
			return getHibernateTemplate().findByNamedParam(query.toString(), paramNames, values);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saiten.dao.MstQuestionDAO#fetchDbInstanceInfo(int)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List fetchDbInstanceInfo(List<Integer> questionSeq) {

		Map<String, String> configMap = SaitenUtil.getConfigMap();
		StringBuilder query = new StringBuilder();
		query.append("SELECT mstQuestion.mstDbInstance.connectionString, ");
		query.append("mstQuestion.mstEvaluation.mstQuestionType.answerScoreTable, ");
		query.append("mstQuestion.mstEvaluation.mstQuestionType.answerScoreHistoryTable, ");
		query.append("mstQuestion.manualFileName, mstQuestion.questionFileName, ");
		query.append("mstQuestion.mstSubject.subjectCode, mstQuestion.questionNum, ");
		query.append("CASE WHEN mstQuestion.side= '" + WebAppConst.ZERO + "' THEN '");
		query.append(WebAppConst.SIDE_NINETY_EIGHT + "' ");
		query.append(" WHEN mstQuestion.side= '" + WebAppConst.ONE + "' THEN '");
		query.append(WebAppConst.SIDE_NINETY_NINE + "' ");
		query.append(" WHEN mstQuestion.side= '" + WebAppConst.HYPHEN + "' THEN '");
		query.append(WebAppConst.HYPHEN + "' END, ");
		query.append("CONCAT(mstQuestion.mstSubject.subjectShortName, '-', mstQuestion.questionNum ");
		if (Boolean.valueOf(configMap.get("attribute1"))) {
			query.append(", '-', '(', mstQuestion.attribute1, ')'");
		}
		query.append("), ");
		query.append("mstQuestion.questionSeq, ");
		query.append("mstQuestion.mstEvaluation.mstQuestionType.questionType, ");
		query.append("mstQuestion.mstEvaluation.scoreType ");
		query.append("FROM MstQuestion as mstQuestion ");
		query.append("WHERE mstQuestion.questionSeq IN :QUESTION_SEQUENCE ");
		query.append("AND mstQuestion.deleteFlag = :DELETE_FLAG ");
		query.append("AND mstQuestion.mstDbInstance.deleteFlag = :DELETE_FLAG ");
		query.append("AND mstQuestion.mstEvaluation.mstQuestionType.deleteFlag = :DELETE_FLAG ");

		String[] paramNames = { "QUESTION_SEQUENCE", "DELETE_FLAG" };
		Object[] values = { questionSeq, WebAppConst.DELETE_FLAG };

		try {
			return getHibernateTemplate().findByNamedParam(query.toString(), paramNames, values);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saiten.dao.MstQuestionDAO#findQuestionSeq(java.lang.String,
	 * java.lang.Short)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Integer> findQuestionSeq(String subjectCode, Short questionNum) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT mstQuestion.questionSeq FROM MstQuestion as mstQuestion ");
		query.append("WHERE mstQuestion.mstSubject.subjectCode = :SUBJECT_CODE ");
		query.append("AND mstQuestion.mstEvaluation.scoreType != :SCORE_TYPE ");
		query.append("AND mstQuestion.mstSubject.scoreFlag = :SCORE_FLAG ");
		query.append("AND mstQuestion.mstEvaluation.deleteFlag = :DELETE_FLAG ");
		query.append("AND mstQuestion.mstSubject.deleteFlag = :DELETE_FLAG ");
		query.append("AND mstQuestion.mstEvaluation.mstQuestionType.deleteFlag = :DELETE_FLAG ");
		if (questionNum != null) {
			query.append("AND mstQuestion.questionNum = :QUESTION_NUM ");
		}

		query.append("AND mstQuestion.deleteFlag = :DELETE_FLAG");

		List<String> paramNameList = new ArrayList<String>();
		List valueList = new ArrayList();

		paramNameList.add("SUBJECT_CODE");
		valueList.add(subjectCode);
		if (questionNum != null) {
			paramNameList.add("QUESTION_NUM");
			valueList.add(questionNum);
		}
		paramNameList.add("DELETE_FLAG");
		valueList.add(WebAppConst.DELETE_FLAG);
		paramNameList.add("SCORE_TYPE");
		valueList.add(WebAppConst.SCORE_TYPE[0]);
		paramNameList.add("SCORE_FLAG");
		valueList.add(WebAppConst.SCORE_FLAG);

		String[] paramNames = paramNameList.toArray(new String[paramNameList.size()]);
		Object[] values = valueList.toArray(new Object[valueList.size()]);

		try {
			return getHibernateTemplate().findByNamedParam(query.toString(), paramNames, values);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List findQuestionList() {

		StringBuilder query = new StringBuilder();
		query.append("SELECT mstQuestion.mstSubject.subjectCode, mstQuestion.questionSeq, mstQuestion.questionNum ");
		query.append("FROM MstQuestion as mstQuestion ");
		query.append("WHERE mstQuestion.deleteFlag = :DELETE_FLAG ");
		query.append("ORDER BY mstQuestion.mstSubject.subjectCode, mstQuestion.questionSeq ");

		String[] paramNames = { "DELETE_FLAG" };
		Object[] values = { WebAppConst.DELETE_FLAG };

		try {
			return getHibernateTemplate().findByNamedParam(query.toString(), paramNames, values);
		} catch (RuntimeException re) {
			throw re;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MstQuestion> findQuestionListBySubjectCodeList(List<String> subjectCodeList) {
		StringBuilder query = new StringBuilder();
		// query.append("SELECT mstQuestion.questionSeq ");
		query.append("FROM MstQuestion as mstQuestion ");
		query.append("WHERE mstQuestion.deleteFlag = :DELETE_FLAG ");
		query.append("AND mstQuestion.mstSubject.subjectCode IN :SUBJECT_CODE_LIST ");
		query.append("ORDER BY mstQuestion.mstSubject.subjectCode, mstQuestion.questionSeq ");
		Object[] subjectArray = subjectCodeList.toArray();
		String[] paramNames = { "DELETE_FLAG", "SUBJECT_CODE_LIST" };
		Object[] values = { WebAppConst.DELETE_FLAG, subjectArray };

		try {
			return getHibernateTemplate().findByNamedParam(query.toString(), paramNames, values);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Integer> getQuestionSeqByEvalType(Integer evalType) {
		StringBuilder query = new StringBuilder();
		// query.append("SELECT mstQuestion.questionSeq ");
		query.append("select distinct mstQuestion.questionSeq ");
		query.append("FROM MstQuestion as mstQuestion ");
		query.append("WHERE mstQuestion.deleteFlag = :DELETE_FLAG ");
		query.append("AND mstQuestion.mstEvaluation.evalSeq in (" + evalType + ") ");
		query.append("ORDER BY mstQuestion.mstSubject.subjectCode, mstQuestion.questionSeq ");

		String[] paramNames = { "DELETE_FLAG" };
		Object[] values = { WebAppConst.DELETE_FLAG };

		try {
			return getHibernateTemplate().findByNamedParam(query.toString(), paramNames, values);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List fetchPdfDocInfo(int questionSeq) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT mstQuestion.manualFileName, mstQuestion.questionFileName ");
		query.append("FROM MstQuestion as mstQuestion ");
		query.append("WHERE mstQuestion.deleteFlag = :DELETE_FLAG ");
		query.append("AND mstQuestion.questionSeq = :QUESTION_SEQ ");

		String[] paramNames = { "DELETE_FLAG", "QUESTION_SEQ" };
		Object[] values = { WebAppConst.DELETE_FLAG, questionSeq };

		try {
			return getHibernateTemplate().findByNamedParam(query.toString(), paramNames, values);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	@Override
	public int updateManualInfo(final int questionSequence, final String manualFileName, boolean isManual1,
			final String scorerId) {
		final StringBuilder query = new StringBuilder();
		query.append("UPDATE MstQuestion ");

		if (isManual1) {
			query.append("SET manualFileName = :MANUAL_FILE_NAME, ");
		} else {
			query.append("SET questionFileName = :MANUAL_FILE_NAME, ");
		}

		query.append("updatePersonId = :UPDATE_PERSON_ID, ");
		query.append("updateDate = :UPDATE_DATE ");
		query.append("WHERE questionSeq = :QUESTION_SEQUENCE ");

		try {
			return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
				public Integer doInHibernate(Session session) throws HibernateException {

					Query queryObj = session.createQuery(query.toString());

					queryObj.setParameter("MANUAL_FILE_NAME", manualFileName);
					queryObj.setParameter("UPDATE_PERSON_ID", scorerId);
					queryObj.setParameter("UPDATE_DATE", new Date());
					queryObj.setParameter("QUESTION_SEQUENCE", questionSequence);

					return queryObj.executeUpdate();
				}
			});
		} catch (RuntimeException re) {
			throw re;
		}
	}

}
