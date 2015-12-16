/**
 * 
 */
package com.saiten.dao.impl;

import java.util.List;

import com.saiten.dao.MstMarkValueDAO;
import com.saiten.dao.support.SaitenHibernateDAOSupport;
import com.saiten.model.MstMarkValue;
import com.saiten.util.WebAppConst;

/**
 * @author rajeshwars
 * 
 */
public class MstMarkValueDAOImpl extends SaitenHibernateDAOSupport implements
		MstMarkValueDAO {
	@SuppressWarnings("rawtypes")
	public List findAll() {
		@SuppressWarnings("unused")
		List<MstMarkValue> mstMarkValueList = null;
		StringBuilder query = new StringBuilder();
		query.append("SELECT mstMarkValue.questionSeq, mstMarkValue.markValueSeq, mstMarkValue.markValue ");
		query.append("FROM MstMarkValue as mstMarkValue ");
		query.append("WHERE mstMarkValue.deleteFlag= :DELETE_FLAG  ");
		query.append("ORDER BY mstMarkValue.questionSeq, mstMarkValue.markValueSeq Asc ");

		String[] paramNames = { "DELETE_FLAG" };
		Object[] values = { WebAppConst.DELETE_FLAG };
		try {
			return getHibernateTemplate().findByNamedParam(query.toString(),
					paramNames, values);
		} catch (RuntimeException re) {
			throw re;
		}
	}

}
