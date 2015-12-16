package com.saiten.dao.impl;

import java.util.List;

import com.saiten.dao.MstTestsetnumQuestionDAO;
import com.saiten.dao.support.SaitenHibernateDAOSupport;
import com.saiten.model.MstTestsetnumQuestion;

public class MstTestsetnumQuestionDAOImpl extends SaitenHibernateDAOSupport
		implements MstTestsetnumQuestionDAO {

	@Override
	public List<MstTestsetnumQuestion> findAll() {
		StringBuilder query = new StringBuilder();
		
		query.append("from MstTestsetnumQuestion as mstTestsetnumQuestion");
		try {
			return getHibernateTemplate().find(query.toString());
		} catch (RuntimeException re) {
			throw re;
		}
	}

}
