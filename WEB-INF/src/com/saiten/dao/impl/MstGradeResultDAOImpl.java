package com.saiten.dao.impl;

import java.util.List;

import com.saiten.dao.MstGradeResultDAO;
import com.saiten.dao.support.SaitenHibernateDAOSupport;
import com.saiten.util.WebAppConst;

/**
 * @author kailash
 * 
 */
public class MstGradeResultDAOImpl extends SaitenHibernateDAOSupport implements
		MstGradeResultDAO {

	@SuppressWarnings("rawtypes")
	@Override
	public List findGradesByQuestionSeq(int questionSeq) {

		StringBuilder query = new StringBuilder();
		query.append("SELECT mstGradeResult.id.gradeNum ");
		query.append("FROM MstGradeResult as mstGradeResult ");
		query.append("WHERE mstGradeResult.id.questionSeq = :QUESTION_SEQ ");
		query.append("AND mstGradeResult.deleteFlag = :DELETE_FLAG ");
		query.append("ORDER BY mstGradeResult.id.gradeNum ");

		String[] paramNames = { "QUESTION_SEQ", "DELETE_FLAG" };
		Object[] values = { questionSeq, WebAppConst.DELETE_FLAG };

		try {
			return getHibernateTemplate().findByNamedParam(query.toString(),
					paramNames, values);
		} catch (RuntimeException re) {
			throw re;
		}
	}

}
