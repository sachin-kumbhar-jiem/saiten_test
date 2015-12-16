/**
 * 
 */
package com.saiten.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.saiten.dao.MstEvaluationDAO;
import com.saiten.dao.support.SaitenHibernateDAOSupport;
import com.saiten.util.WebAppConst;

/**
 * @author user
 * 
 */
public class MstEvaluationDAOImpl extends SaitenHibernateDAOSupport implements
		MstEvaluationDAO {

	@SuppressWarnings("rawtypes")
	@Override
	public List getQuestionTypeScoreTypeList() {

		StringBuilder query = new StringBuilder();
		query.append("SELECT mstEvaluation.evalSeq, mstEvaluation.mstQuestionType.questionType, mstEvaluation.scoreType ");
		query.append("FROM MstEvaluation as mstEvaluation ");
		query.append("WHERE mstEvaluation.scoreType IN :SCORE_TYPES ");
		query.append("AND mstEvaluation.deleteFlag = :DELETE_FLAG ");
		query.append("AND mstEvaluation.mstQuestionType.deleteFlag = :DELETE_FLAG ");
		query.append("ORDER BY mstEvaluation.evalSeq ");

		List<Character> scoreTypes = new ArrayList<Character>();
		scoreTypes.add('2');
		scoreTypes.add('4');
		scoreTypes.add('1');

		String[] paramNames = { "SCORE_TYPES", "DELETE_FLAG" };
		Object[] values = { scoreTypes, WebAppConst.DELETE_FLAG };

		try {
			return getHibernateTemplate().findByNamedParam(query.toString(),
					paramNames, values);
		} catch (RuntimeException re) {
			throw re;
		}
	}

}
