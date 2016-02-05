package com.saiten.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;

import com.saiten.dao.TranAcceptanceDao;
import com.saiten.dao.TranDescScoreDAO;
import com.saiten.exception.SaitenRuntimeException;
import com.saiten.info.AnswerInfo;
import com.saiten.info.TranDescScoreInfo;
import com.saiten.model.TranAcceptance;
import com.saiten.model.TranDescScore;
import com.saiten.service.KenshuSamplingService;
import com.saiten.util.ErrorCode;
import com.saiten.util.SaitenUtil;
import com.saiten.util.WebAppConst;

public class KenshuSamplingServiceImpl implements KenshuSamplingService,
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TranDescScoreDAO tranDescScoreDAO;
	private TranAcceptanceDao tranAcceptanceDao;

	@Override
	@SuppressWarnings("rawtypes")
	public List<TranDescScoreInfo> getKenshuSamplingRecordsList(
			int questionSeq, String connectionString, int recordCount) {
		try {
			List recordList = tranDescScoreDAO.findKenshuRecords(questionSeq,
					connectionString, recordCount);
			return createKenshuSamplingRecord(recordList);
		} catch (HibernateException he) {
			throw new SaitenRuntimeException(
					ErrorCode.KENSHU_RECORD_SERVICE_EXCEPTION, he);
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.KENSHU_RECORD_SERVICE_EXCEPTION, e);
		}

	}

	@Override
	public int updateKenshuRecord(TranDescScoreInfo tranDescScoreInfo,
			String connectionString) {
		try {
			synchronized (this) {

				Date date = new Date();

				tranDescScoreInfo.getAnswerInfo().setUpdateDate(date);

				return tranDescScoreDAO.updateKunshuFlagByAnswerseq(
						tranDescScoreInfo.getAnswerInfo().getAnswerSeq(), date,
						connectionString);

			}
		} catch (HibernateException he) {
			throw new SaitenRuntimeException(
					ErrorCode.KENSHU_RECORD_SERVICE_EXCEPTION, he);
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.KENSHU_RECORD_SERVICE_EXCEPTION, e);
		}

	}

	@Override
	public boolean isAnswerAlreadyChecked(TranDescScoreInfo tranDescScoreInfo,
			String scorerId, String connectionString) {

		try {
			@SuppressWarnings("unchecked")
			List<TranDescScore> tranDescScore = tranDescScoreDAO
					.isAnswerAlreadyChecked(tranDescScoreInfo.getAnswerInfo()
							.getAnswerSeq(), tranDescScoreInfo.getAnswerInfo()
							.getUpdateDate(), connectionString);
			TranDescScore tranDescScoreobj = tranDescScore.get(0);

			if (tranDescScoreobj.getKenshuSamplingFlag().equals(
					WebAppConst.VALID_FLAG)) {
				return SaitenUtil.getSaitenFormatDate(
						tranDescScoreobj.getUpdateDate()).equals(
						SaitenUtil.getSaitenFormatDate(tranDescScoreInfo
								.getAnswerInfo().getUpdateDate()));
			} else {
				return true;
			}

		} catch (HibernateException he) {
			throw new SaitenRuntimeException(
					ErrorCode.KENSHU_RECORD_SERVICE_EXCEPTION, he);
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.KENSHU_RECORD_SERVICE_EXCEPTION, e);
		}
	}

	@SuppressWarnings("rawtypes")
	public List<TranDescScoreInfo> createKenshuSamplingRecord(List temp) {
		List<TranDescScoreInfo> tranDescScoreInfoList = null;
		if (!temp.isEmpty()) {
			tranDescScoreInfoList = new ArrayList<TranDescScoreInfo>();
			for (Object answerRecord : temp) {
				AnswerInfo answerInfo = new AnswerInfo();

				TranDescScoreInfo tranDescScoreInfo = new TranDescScoreInfo();
				tranDescScoreInfo.setAnswerInfo(answerInfo);
				Object[] answerRecordObj = (Object[]) answerRecord;

				answerInfo.setAnswerSeq(Integer.valueOf(answerRecordObj[0]
						.toString()));

				tranDescScoreInfo
						.setAnswerFormNumber((String) answerRecordObj[1]);

				tranDescScoreInfo.setImageFileName(answerRecordObj[2]
						.toString());

				tranDescScoreInfo.setGradeSeq(Integer
						.valueOf(answerRecordObj[3].toString()));

				answerInfo.setBitValue(Double.valueOf(answerRecordObj[4]
						.toString()));

				answerInfo.setQuestionSeq(Integer.valueOf(answerRecordObj[5]
						.toString()));

				answerInfo.setUpdateDate((java.util.Date) answerRecordObj[6]);

				/*
				 * tranDescScoreInfo .setMarkValueList((List<Short>)
				 * answerRecordObj[7]);
				 */

				answerInfo.setLatestScreenScorerId(answerRecordObj[8]
						.toString());

				if (answerRecordObj[9] != null) {
					answerInfo.setSecondLatestScreenScorerId(answerRecordObj[9]
							.toString());
				}

				tranDescScoreInfo.setGradeNum(Integer
						.valueOf(answerRecordObj[10].toString()));

				tranDescScoreInfoList.add(tranDescScoreInfo);

			}
		}
		return tranDescScoreInfoList;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getKenshuMarkeRecordsList(int questionSeq,
			String searchCriteria, String ConnectionString, String kenshuUserId) {
		try {
			List<TranAcceptance> recordList = tranAcceptanceDao
					.fetchByQuestionNumAndMarkBy(questionSeq, kenshuUserId,
							searchCriteria, ConnectionString);

			return recordList;
		} catch (HibernateException he) {
			throw new SaitenRuntimeException(
					ErrorCode.KENSHU_RECORD_SERVICE_EXCEPTION, he);
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.KENSHU_RECORD_SERVICE_EXCEPTION, e);
		}
	}

	public TranDescScoreDAO getTranDescScoreDAO() {
		return tranDescScoreDAO;
	}

	public void setTranDescScoreDAO(TranDescScoreDAO tranDescScoreDAO) {
		this.tranDescScoreDAO = tranDescScoreDAO;
	}

	public TranAcceptanceDao getTranAcceptanceDao() {
		return tranAcceptanceDao;
	}

	public void setTranAcceptanceDao(TranAcceptanceDao tranAcceptanceDao) {
		this.tranAcceptanceDao = tranAcceptanceDao;
	}

}
