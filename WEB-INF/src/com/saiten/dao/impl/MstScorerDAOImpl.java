package com.saiten.dao.impl;

import java.util.List;

import com.saiten.dao.MstScorerDAO;
import com.saiten.dao.support.SaitenHibernateDAOSupport;
import com.saiten.info.DailyStatusSearchInfo;
import com.saiten.model.MstScorer;
import com.saiten.util.WebAppConst;

/**
 * @author sachin
 * @version 1.0
 * @created 05-Dec-2012 4:59:11 PM
 */
public class MstScorerDAOImpl extends SaitenHibernateDAOSupport implements
		MstScorerDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.saiten.dao.MstScorerDAO#findByScorerIdAndPassword(java.lang.String,
	 * java.lang.String)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List findByScorerIdAndPassword(String scorerId, String password) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT mstScorer.noDbUpdate, mstScorer.mstScorerRole.roleDescription, ");
		query.append("mstScorer.mstScorerRole.roleId, mstScorer.scorerId, mstScorer.scorerName ");
		query.append("FROM MstScorer as mstScorer ");
		query.append("WHERE mstScorer.deleteFlag= :DELETE_FLAG ");
		query.append("AND mstScorer.scorerId= :SCORER_ID ");
		query.append("AND mstScorer.password= :PASSWORD");

		String[] paramNames = { "DELETE_FLAG", "SCORER_ID", "PASSWORD" };
		Object[] values = { WebAppConst.DELETE_FLAG, scorerId, password };
		try {
			return getHibernateTemplate().findByNamedParam(query.toString(),
					paramNames, values);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	@Override
	public boolean isUserAlreadyExist(String scorerId) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT count(*) ");
		query.append("FROM MstScorer as mstScorer ");
		query.append("WHERE mstScorer.deleteFlag= :DELETE_FLAG ");
		query.append("AND mstScorer.scorerId= :SCORER_ID ");

		String[] paramNames = { "DELETE_FLAG", "SCORER_ID" };
		Object[] values = { WebAppConst.DELETE_FLAG, scorerId };

		try {
			Long count = (Long) getHibernateTemplate().findByNamedParam(
					query.toString(), paramNames, values).get(0);
			if (count > 0)
				return true;
			else
				return false;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getMstScorerRoleList() {

		StringBuilder query = new StringBuilder();
		query.append("SELECT mstScorerRole.roleId, mstScorerRole.roleDescription ");
		query.append("FROM MstScorerRole as mstScorerRole ");
		query.append("WHERE mstScorerRole.deleteFlag= :DELETE_FLAG ");

		String[] paramNames = { "DELETE_FLAG" };
		Object[] values = { WebAppConst.DELETE_FLAG };

		try {
			return getHibernateTemplate().findByNamedParam(query.toString(),
					paramNames, values);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	@Override
	public void save(MstScorer mstScorer) {
		try {
			getHibernateTemplate().save(mstScorer);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<MstScorer> scorerByRole(Byte[] rollIds,DailyStatusSearchInfo dailyStatusSearchInfo) {
		StringBuilder query = new StringBuilder();
		query.append("FROM MstScorer  ");
		query.append("WHERE mstScorerRole.deleteFlag= :DELETE_FLAG ");
		if(dailyStatusSearchInfo.getUserId()!=null && !dailyStatusSearchInfo.getUserId().equals("")){
		query.append(" AND  scorerId = '"+dailyStatusSearchInfo.getUserId()+"'");
		}
		
		if(rollIds!=null && rollIds.length>0){
		 query.append(" AND  mstScorerRole.roleId IN :ROLL_IDS ");
	    }
	
		if(rollIds!=null && rollIds.length>0){
		String[] paramNames = { "DELETE_FLAG", "ROLL_IDS" };
		Object[] values = { WebAppConst.DELETE_FLAG, rollIds };
		try {
			return (List<MstScorer>) getHibernateTemplate().findByNamedParam(
					query.toString(), paramNames, values);
		} catch (RuntimeException re) {
			throw re;
		}
		}
		else{
			String[] paramNames = { "DELETE_FLAG"};
			Object[] values = { WebAppConst.DELETE_FLAG};
			try {
				return (List<MstScorer>) getHibernateTemplate().findByNamedParam(
						query.toString(), paramNames, values);
			} catch (RuntimeException re) {
				throw re;
			}
		}
		
	}
	
	@Override
	public void update(MstScorer mstScorer){
		try {
			getHibernateTemplate().update(mstScorer);
		} catch (RuntimeException re) {
			throw re;
		}
	}
}