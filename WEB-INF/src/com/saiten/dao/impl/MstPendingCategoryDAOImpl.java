package com.saiten.dao.impl;

import java.util.List;

import com.saiten.dao.MstPendingCategoryDAO;
import com.saiten.dao.support.SaitenHibernateDAOSupport;
import com.saiten.util.WebAppConst;

/**
 * @author sachin
 * @version 1.0
 * @created 05-Dec-2012 4:59:11 PM
 */
public class MstPendingCategoryDAOImpl extends SaitenHibernateDAOSupport
		implements MstPendingCategoryDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saiten.dao.MstPendingCategoryDAO#findPendingCategoryList()
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List findPendingCategoryList() {
		StringBuilder query = new StringBuilder();
		query.append("SELECT mstPendingCategory.pendingCategorySeq, mstPendingCategory.pendingCategory ");
		query.append("FROM MstPendingCategory as mstPendingCategory ");
		query.append("WHERE mstPendingCategory.deleteFlag= :DELETE_FLAG AND mstPendingCategory.validFlag = :VALID_FLAG ");
		query.append("ORDER BY mstPendingCategory.pendingCategorySeq");

		String[] paramNames = { "DELETE_FLAG", "VALID_FLAG" };
		Object[] values = { WebAppConst.DELETE_FLAG, WebAppConst.VALID_FLAG };
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
	 * @see
	 * com.saiten.dao.MstPendingCategoryDAO#findPendingCategoriesByQuestionSeq
	 * (int)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List findPendingCategoriesByQuestionSeq(int questionSeq) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT mstPendingCategory.pendingCategory, CONCAT(mstPendingCategory.pendingCategory, '.', mstPendingCategory.pendingDescription) ");
		query.append("FROM MstPendingCategory as mstPendingCategory ");
		query.append("WHERE mstPendingCategory.deleteFlag= :DELETE_FLAG AND mstPendingCategory.validFlag = :VALID_FLAG ");
		query.append("AND mstPendingCategory.mstQuestion.questionSeq = :QUESTION_SEQ ");
		query.append("ORDER BY mstPendingCategory.pendingCategory");

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.saiten.dao.MstPendingCategoryDAO#findPendingCategorySeqList(java.
	 * lang.Integer, java.lang.Short[])
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List findPendingCategorySeqList(Integer questionSeq,
			Short[] pendingCategoryList) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT mstPendingCategory.pendingCategorySeq ");
		query.append("FROM MstPendingCategory as mstPendingCategory ");
		query.append("WHERE mstPendingCategory.mstQuestion.questionSeq = :QUESTION_SEQ ");
		query.append("AND mstPendingCategory.pendingCategory IN :PENDING_CATEGORY_LIST ");
		query.append("AND mstPendingCategory.deleteFlag= :DELETE_FLAG AND mstPendingCategory.validFlag = :VALID_FLAG ");
		query.append("ORDER BY mstPendingCategory.pendingCategorySeq");

		String[] paramNames = { "QUESTION_SEQ", "PENDING_CATEGORY_LIST",
				"DELETE_FLAG", "VALID_FLAG" };
		Object[] values = { questionSeq, pendingCategoryList,
				WebAppConst.DELETE_FLAG, WebAppConst.VALID_FLAG };

		try {
			return getHibernateTemplate().findByNamedParam(query.toString(),
					paramNames, values);
		} catch (RuntimeException re) {
			throw re;
		}
	}
}
