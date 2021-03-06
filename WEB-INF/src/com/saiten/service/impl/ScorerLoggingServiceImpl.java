/**
 * 
 */
package com.saiten.service.impl;

import org.hibernate.HibernateException;

import com.saiten.dao.LmsInstancesDAO;
import com.saiten.dao.TranScorerAccessLogDAO;
import com.saiten.exception.SaitenRuntimeException;
import com.saiten.info.ScorerAccessLogInfo;
import com.saiten.model.MstScorer;
import com.saiten.model.TranScorerAccessLog;
import com.saiten.service.ScorerLoggingService;
import com.saiten.util.ErrorCode;

/**
 * @author user
 * 
 */
public class ScorerLoggingServiceImpl implements ScorerLoggingService {

	private TranScorerAccessLogDAO tranScorerAccessLogDAO;

	private LmsInstancesDAO lmsInstancesDAO;

	@Override
	public void saveOrUpdate(ScorerAccessLogInfo scorerAccessLogInfo) {

		try {

			TranScorerAccessLog tranScorerAccessLog = new TranScorerAccessLog();
			if (scorerAccessLogInfo.getId() != null) {
				tranScorerAccessLog.setId(scorerAccessLogInfo.getId());
			}
			MstScorer mstScorer = new MstScorer();
			mstScorer.setScorerId(scorerAccessLogInfo.getScorerId());
			tranScorerAccessLog.setScorerId(scorerAccessLogInfo.getScorerId());
			tranScorerAccessLog
					.setLoginTime(scorerAccessLogInfo.getLoginTime());
			if (scorerAccessLogInfo.getLogoutTime() != null) {
				tranScorerAccessLog.setLogoutTime(scorerAccessLogInfo
						.getLogoutTime());
			}
			tranScorerAccessLog.setStatus(scorerAccessLogInfo.getStatus());

			Integer id = tranScorerAccessLogDAO
					.saveOrUpdate(tranScorerAccessLog);
			scorerAccessLogInfo.setId(id);

		} catch (HibernateException he) {
			throw new SaitenRuntimeException(
					ErrorCode.SAVE_OR_UPDATE_USER_ACCESS_LOG_HIBERNATE_EXCEPTION,
					he);
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.SAVE_OR_UPDATE_USER_ACCESS_LOG_SERVICE_EXCEPTION,
					e);
		}

	}

	/**
	 * @param tranScorerAccessLogDAO
	 *            the tranScorerAccessLogDAO to set
	 */
	public void setTranScorerAccessLogDAO(
			TranScorerAccessLogDAO tranScorerAccessLogDAO) {
		this.tranScorerAccessLogDAO = tranScorerAccessLogDAO;
	}

	/**
	 * @param lmsInstancesDAO
	 *            the lmsInstancesDAO to set
	 */
	public void setLmsInstancesDAO(LmsInstancesDAO lmsInstancesDAO) {
		this.lmsInstancesDAO = lmsInstancesDAO;
	}

	@Override
	public String getUrlById(Integer id) {
		return lmsInstancesDAO.getUrlById(id);
	}

}
