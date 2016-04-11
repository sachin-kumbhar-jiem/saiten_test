/**
 * 
 */
package com.saiten.dao.impl;

import com.saiten.dao.LmsInstancesDAO;
import com.saiten.dao.support.SaitenHibernateDAOSupport;
import com.saiten.model.LmsInstances;
import com.saiten.util.SaitenUtil;

/**
 * @author user
 * 
 */
public class LmsInstancesDAOImpl extends SaitenHibernateDAOSupport implements
		LmsInstancesDAO {

	@Override
	public String getUrlById(Integer id) {
		try {

			return getHibernateTemplate(
					SaitenUtil.getCommonDbConnectionString()).get(
					LmsInstances.class, id).getLmsUrl();

		} catch (RuntimeException re) {
			throw re;
		}
	}
}
