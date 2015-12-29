package com.saiten.dao.impl;

import java.util.List;

import com.saiten.dao.MstDenyCategoryDAO;
import com.saiten.dao.support.SaitenHibernateDAOSupport;
import com.saiten.util.WebAppConst;

public class MstDenyCategoryDAOImpl extends SaitenHibernateDAOSupport implements
		MstDenyCategoryDAO {
	@SuppressWarnings("rawtypes")
	@Override
	public List findDenyCategoryList() {
		StringBuilder query = new StringBuilder();
		query.append("SELECT mstDenyCategory.denyCategorySeq, mstDenyCategory.denyCategory ");
		query.append("FROM MstDenyCategory as mstDenyCategory ");
		query.append("WHERE mstDenyCategory.deleteFlag= :DELETE_FLAG AND mstDenyCategory.validFlag = :VALID_FLAG ");
		query.append("ORDER BY mstDenyCategory.denyCategorySeq");

		String[] paramNames = { "DELETE_FLAG", "VALID_FLAG" };
		Object[] values = { WebAppConst.DELETE_FLAG, WebAppConst.VALID_FLAG };
		try {
			return getHibernateTemplate().findByNamedParam(query.toString(),
					paramNames, values);
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	@SuppressWarnings ("rawtypes")
	@Override
	public List findDenyCategoriesByQuestionSeq(int questionSeq) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT mstDenyCategory.denyCategory, CONCAT(mstDenyCategory.denyCategory, '.', mstDenyCategory.denyDescription) ");
		query.append("FROM MstDenyCategory as mstDenyCategory ");
		query.append("WHERE mstDenyCategory.deleteFlag= :DELETE_FLAG AND mstDenyCategory.validFlag = :VALID_FLAG ");
		query.append("AND mstDenyCategory.mstQuestion.questionSeq = :QUESTION_SEQ ");
		query.append("ORDER BY mstDenyCategory.denyCategory");

		String[] paramNames = { "QUESTION_SEQ", "DELETE_FLAG", "VALID_FLAG" };
		Object[] values = { questionSeq, WebAppConst.DELETE_FLAG,
				WebAppConst.VALID_FLAG };

		try {
			return getHibernateTemplate().findByNamedParam(query.toString(),
					paramNames, values);
		} catch (RuntimeException re) {
			throw re;
		}

	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List findDenyCategorySeqList(Integer questionSeq,
			Short[] denyCategoryList) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT mstDenyCategory.denyCategorySeq ");
		query.append("FROM MstDenyCategory as mstDenyCategory ");
		query.append("WHERE mstDenyCategory.mstQuestion.questionSeq = :QUESTION_SEQ ");
		query.append("AND mstDenyCategory.denyCategory IN :DENY_CATEGORY_LIST ");
		query.append("AND mstDenyCategory.deleteFlag= :DELETE_FLAG AND mstDenyCategory.validFlag = :VALID_FLAG ");
		query.append("ORDER BY mstDenyCategory.denyCategorySeq");

		String[] paramNames = { "QUESTION_SEQ", "DENY_CATEGORY_LIST",
				"DELETE_FLAG", "VALID_FLAG" };
		Object[] values = { questionSeq, denyCategoryList,
				WebAppConst.DELETE_FLAG, WebAppConst.VALID_FLAG };

		try {
			return getHibernateTemplate().findByNamedParam(query.toString(),
					paramNames, values);
		} catch (RuntimeException re) {
			throw re;
		}
	}

}
