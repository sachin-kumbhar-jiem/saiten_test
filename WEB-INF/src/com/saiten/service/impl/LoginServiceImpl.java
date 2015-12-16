package com.saiten.service.impl;

import java.util.List;

import org.hibernate.HibernateException;

import com.saiten.dao.MstScorerDAO;
import com.saiten.exception.SaitenRuntimeException;
import com.saiten.info.MstScorerInfo;
import com.saiten.service.LoginService;
import com.saiten.util.ErrorCode;
import com.saiten.util.SaitenUtil;

/**
 * @author sachin
 * @version 1.0
 * @created 05-Dec-2012 4:59:11 PM
 */
public class LoginServiceImpl implements LoginService {

	private MstScorerDAO mstScorerDAO;

	/**
	 * 
	 * @param scorerId
	 * @param password
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public MstScorerInfo findByScorerIdAndPassword(String scorerId,
			String password) {
		MstScorerInfo mstScorerInfo = null;
		try {
			List mstScorerInfoList = getMstScorerDAO()
					.findByScorerIdAndPassword(scorerId, password);
			if (!mstScorerInfoList.isEmpty()) {
				mstScorerInfo = (MstScorerInfo) SaitenUtil
						.getApplicationContext().getBean("mstScorerInfo");
				Object[] mstSocrerInfoObjects = (Object[]) mstScorerInfoList
						.get(0);
				mstScorerInfo
						.setNoDbUpdate((Character) mstSocrerInfoObjects[0]);
				mstScorerInfo
						.setRoleDescription((String) mstSocrerInfoObjects[1]);
				mstScorerInfo.setRoleId((Byte) mstSocrerInfoObjects[2]);
				mstScorerInfo.setScorerId((String) mstSocrerInfoObjects[3]);
				mstScorerInfo.setScorerName((String) mstSocrerInfoObjects[4]);
			}
			return mstScorerInfo;
		} catch (HibernateException he) {
			throw new SaitenRuntimeException(
					ErrorCode.LOGIN_HIBERNATE_EXCEPTION, he);
		} catch (Exception e) {
			throw new SaitenRuntimeException(ErrorCode.LOGIN_SERVICE_EXCEPTION,
					e);
		}
	}

	public void setMstScorerDAO(MstScorerDAO mstScorerDAO) {
		this.mstScorerDAO = mstScorerDAO;
	}

	public MstScorerDAO getMstScorerDAO() {
		return mstScorerDAO;
	}

}