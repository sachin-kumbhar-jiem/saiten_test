package com.saiten.service.impl;

import java.util.Date;

import org.hibernate.HibernateException;

import com.saiten.dao.TranAcceptanceDao;
import com.saiten.dao.TranAcceptanceHistoryDao;
import com.saiten.exception.SaitenRuntimeException;
import com.saiten.info.KenshuRecordInfo;
import com.saiten.model.TranAcceptance;
import com.saiten.model.TranAcceptanceHistory;
import com.saiten.model.TranDescScore;
import com.saiten.service.MarkKenshuRecordService;
import com.saiten.util.ErrorCode;

public class MarkKenshuRecordServiceImpl implements MarkKenshuRecordService {

	private TranAcceptanceDao tranAcceptanceDao;
	private TranAcceptanceHistoryDao tranAcceptanceHistoryDao;

	@Override
	public Integer doMark(KenshuRecordInfo kenshuRecordInfo,
			String connectionString) {
		Integer acceptanceSeq;
		try {
			TranAcceptance tranAcceptance = tranAcceptanceDao
					.fetchByAnswerSeqAndUpdatePersonId(
							kenshuRecordInfo.getAnswerSeq(),
							kenshuRecordInfo.getMarkBy(), connectionString);

			if (tranAcceptance == null) {
				tranAcceptance = new TranAcceptance();
				copy(kenshuRecordInfo, tranAcceptance);
				tranAcceptance.setCreateDate(new Date());
			} else {
				tranAcceptance.setComment(kenshuRecordInfo.getComment());
				tranAcceptance.setMarkFlag(kenshuRecordInfo.getMarkFlag());
				tranAcceptance.setUpdateDate(new Date());
			}
			if (tranAcceptance.getAcceptanceSeq() == null) {
				acceptanceSeq = tranAcceptanceDao.save(tranAcceptance,
						connectionString);
				tranAcceptance.setAcceptanceSeq(acceptanceSeq);

			} else {
				acceptanceSeq = tranAcceptance.getAcceptanceSeq();
				tranAcceptanceDao.update(tranAcceptance, connectionString);

			}
			TranAcceptanceHistory tranAcceptanceHistory = new TranAcceptanceHistory();
			createTranAcceptanceHistoryObj(tranAcceptance,
					tranAcceptanceHistory);
			tranAcceptanceHistoryDao.save(tranAcceptanceHistory,
					connectionString);
		} catch (HibernateException he) {
			throw new SaitenRuntimeException(
					ErrorCode.KENSHU_RECORD_SERVICE_EXCEPTION, he);
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.KENSHU_RECORD_SERVICE_EXCEPTION, e);
		}
		return acceptanceSeq;
	}

	@Override
	public Integer doUnMark(KenshuRecordInfo kenshuRecordInfo,
			String connectionString) {
		try {
			TranAcceptance tranAcceptance = tranAcceptanceDao
					.fetchByAnswerSeqAndUpdatePersonId(
							kenshuRecordInfo.getAnswerSeq(),
							kenshuRecordInfo.getMarkBy(), connectionString);
			copy(kenshuRecordInfo, tranAcceptance);
			tranAcceptanceDao.update(tranAcceptance, connectionString);
			TranAcceptanceHistory tranAcceptanceHistory = new TranAcceptanceHistory();
			createTranAcceptanceHistoryObj(tranAcceptance,
					tranAcceptanceHistory);
			tranAcceptanceHistoryDao.save(tranAcceptanceHistory,
					connectionString);
		} catch (HibernateException he) {
			throw new SaitenRuntimeException(
					ErrorCode.KENSHU_RECORD_SERVICE_EXCEPTION, he);
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.KENSHU_RECORD_SERVICE_EXCEPTION, e);
		}
		return null;
	}

	public void copy(KenshuRecordInfo kenshuRecordInfo,
			TranAcceptance tranAcceptance) {
		// tranAcceptance.setAcceptanceSeq(kenshuRecordInfo.getAcceptenceSeq());
		TranDescScore tranDescScore = new TranDescScore();
		tranDescScore.setAnswerSeq(kenshuRecordInfo.getAnswerSeq());
		tranAcceptance.setTranDescScore(tranDescScore);
		tranAcceptance.setComment(kenshuRecordInfo.getComment());
		tranAcceptance.setMarkBy(kenshuRecordInfo.getMarkBy());
		tranAcceptance.setMarkFlag(kenshuRecordInfo.getMarkFlag());
		tranAcceptance.setUpdateDate(kenshuRecordInfo.getUpdateDate());
	}

	public void createTranAcceptanceHistoryObj(TranAcceptance tranAcceptance,
			TranAcceptanceHistory tranAcceptanceHistory) {
		tranAcceptanceHistory.setTranAcceptance(tranAcceptance);
		tranAcceptanceHistory.setAnswerSeq(tranAcceptance.getTranDescScore()
				.getAnswerSeq());
		tranAcceptanceHistory.setComment(tranAcceptance.getComment());
		tranAcceptanceHistory.setCreateDate(tranAcceptance.getCreateDate());
		tranAcceptanceHistory.setExplainBy(tranAcceptance.getExplainBy());
		tranAcceptanceHistory.setExplainFlag(tranAcceptance.getExplainFlag());
		tranAcceptanceHistory.setMarkBy(tranAcceptance.getMarkBy());
		tranAcceptanceHistory.setMarkFlag(tranAcceptance.getMarkFlag());
		tranAcceptanceHistory.setUpdateDate(tranAcceptance.getUpdateDate());
	}

	@Override
	public void updateRecord(TranAcceptance tranAcceptance,
			String connectionString) {
		try {
			tranAcceptanceDao.update(tranAcceptance, connectionString);
			TranAcceptanceHistory tranAcceptanceHistory = new TranAcceptanceHistory();
			createTranAcceptanceHistoryObj(tranAcceptance,
					tranAcceptanceHistory);
			tranAcceptanceHistoryDao.save(tranAcceptanceHistory,
					connectionString);
		} catch (HibernateException he) {
			throw new SaitenRuntimeException(
					ErrorCode.KENSHU_RECORD_SERVICE_EXCEPTION, he);
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.KENSHU_RECORD_SERVICE_EXCEPTION, e);
		}
	}

	public TranAcceptanceDao getTranAcceptanceDao() {
		return tranAcceptanceDao;
	}

	public void setTranAcceptanceDao(TranAcceptanceDao tranAcceptanceDao) {
		this.tranAcceptanceDao = tranAcceptanceDao;
	}

	public TranAcceptanceHistoryDao getTranAcceptanceHistoryDao() {
		return tranAcceptanceHistoryDao;
	}

	public void setTranAcceptanceHistoryDao(
			TranAcceptanceHistoryDao tranAcceptanceHistoryDao) {
		this.tranAcceptanceHistoryDao = tranAcceptanceHistoryDao;
	}

}
