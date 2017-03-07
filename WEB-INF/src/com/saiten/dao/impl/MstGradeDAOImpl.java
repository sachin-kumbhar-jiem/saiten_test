package com.saiten.dao.impl;

import java.util.List;

import com.saiten.dao.MstGradeDAO;
import com.saiten.dao.support.SaitenHibernateDAOSupport;
import com.saiten.model.MstGrade;
import com.saiten.util.WebAppConst;

/**
 * @author sachin
 * @version 1.0
 * @created 05-Dec-2012 4:59:11 PM
 */
public class MstGradeDAOImpl extends SaitenHibernateDAOSupport implements MstGradeDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saiten.dao.MstGradeDAO#findAll()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MstGrade> findAll() {
		StringBuilder query = new StringBuilder();
		query.append("FROM MstGrade as mstGrade ");
		query.append("WHERE mstGrade.deleteFlag = :DELETE_FLAG ");
		query.append("AND mstGrade.mstGradeResult.deleteFlag = :DELETE_FLAG");

		String[] paramNames = { "DELETE_FLAG" };
		Object[] values = { WebAppConst.DELETE_FLAG };

		try {
			return getHibernateTemplate().findByNamedParam(query.toString(), paramNames, values);
		} catch (RuntimeException re) {
			throw re;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saiten.dao.MstGradeDAO#findGradeSeqList(java.lang.Integer,
	 * java.lang.Integer[])
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List findGradeSeqList(Integer questionSeq, Integer[] gradeNum) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT mstGrade.gradeSeq FROM MstGrade as mstGrade ");
		query.append("WHERE mstGrade.mstGradeResult.id.questionSeq = :QUESTION_SEQ ");
		query.append("AND mstGrade.mstGradeResult.id.gradeNum IN :GRADE_NUM ");
		query.append("AND mstGrade.deleteFlag = :DELETE_FLAG ");
		query.append("AND mstGrade.mstGradeResult.deleteFlag = :DELETE_FLAG");

		String[] paramNames = { "QUESTION_SEQ", "GRADE_NUM", "DELETE_FLAG" };
		Object[] values = { questionSeq, gradeNum, WebAppConst.DELETE_FLAG };

		try {
			return getHibernateTemplate().findByNamedParam(query.toString(), paramNames, values);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	@SuppressWarnings("rawtypes")
	public List getMstGradeDetails() {

		final StringBuilder query = new StringBuilder();
		query.append(
				"SELECT mstGrade.mstQuestion.questionSeq,mstGrade.mstGradeResult.id.gradeNum,mstGrade.bitValue FROM MstGrade as mstGrade ");
		query.append("WHERE mstGrade.deleteFlag = :DELETE_FLAG ");
		query.append("AND mstGrade.mstGradeResult.deleteFlag = :DELETE_FLAG");

		String[] paramNames = { "DELETE_FLAG" };
		Object[] values = { WebAppConst.DELETE_FLAG };

		try {
			return getHibernateTemplate().findByNamedParam(query.toString(), paramNames, values);
		} catch (RuntimeException re) {
			throw re;
		}
	}
}
