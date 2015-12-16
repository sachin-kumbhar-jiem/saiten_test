package com.saiten.dao.impl;

import java.util.List;

import com.saiten.dao.MstSubjectDAO;
import com.saiten.dao.support.SaitenHibernateDAOSupport;
import com.saiten.util.WebAppConst;

/**
 * @author sachin
 * @version 1.0
 * @created 20-Feb-2013 4:59:11 PM
 */
public class MstSubjectDAOImpl extends SaitenHibernateDAOSupport implements
		MstSubjectDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saiten.dao.MstSubjectDAO#findSubjectNameList()
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List findSubjectNameList() {
		StringBuilder query = new StringBuilder();
		query.append("SELECT CONCAT(mstSubject.schoolType, '-', mstSubject.subjectCode), mstSubject.subjectName ");
		query.append("FROM MstSubject as mstSubject ");
		query.append("WHERE mstSubject.deleteFlag = :DELETE_FLAG ");
		query.append("AND mstSubject.scoreFlag = :SCORE_FLAG ");
		query.append("ORDER BY mstSubject.schoolType, mstSubject.subjectCode");
		String[] paramNames = { "DELETE_FLAG", "SCORE_FLAG" };
		Object[] values = { WebAppConst.DELETE_FLAG, WebAppConst.SCORE_FLAG };

		try {
			return getHibernateTemplate().findByNamedParam(query.toString(),
					paramNames, values);
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saiten.dao.MstSubjectDAO#findSubjectList()
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List findSubjectList() {
		
		StringBuilder query = new StringBuilder();
		query.append("SELECT mstSubject.subjectCode, mstSubject.subjectName ");
		query.append("FROM MstSubject as mstSubject ");
		query.append("WHERE mstSubject.deleteFlag = :DELETE_FLAG ");
		query.append("AND mstSubject.scoreFlag = :SCORE_FLAG ");
		
		String[] paramNames = { "DELETE_FLAG", "SCORE_FLAG" };
		Object[] values = { WebAppConst.DELETE_FLAG, WebAppConst.SCORE_FLAG };
		
		try {
			return getHibernateTemplate().findByNamedParam(query.toString(),
					paramNames, values);
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
}
