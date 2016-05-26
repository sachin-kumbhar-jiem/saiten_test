package com.saiten.service.impl;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;

import com.saiten.dao.TranAcceptanceDao;
import com.saiten.dao.TranDescScoreDAO;
import com.saiten.exception.SaitenRuntimeException;
import com.saiten.info.AnswerInfo;
import com.saiten.info.KenshuSamplingSearchRecordInfo;
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
	public List<KenshuSamplingSearchRecordInfo> getKenshuSamplingRecordsList(
			int questionSeq, String connectionString, int recordCount) {
		try {
			List recordList = tranDescScoreDAO.findKenshuRecords(questionSeq,
					connectionString, recordCount);
			return createGreadeWiseList(recordList, recordCount);

			// return createKenshuSamplingRecord(recordList,recordCount);
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
	@Override
	public List<TranDescScoreInfo> getKenhuRecordsByGrade(int questionSeq,
			String connectionString, int recordCount, int gradeNum) {
		List recordList = tranDescScoreDAO.getKenshuRecordsByGrade(questionSeq,
				connectionString, recordCount, gradeNum);
		return createKenshuSamplingRecord(recordList,recordCount);
	}

	@SuppressWarnings("rawtypes")
	public List<TranDescScoreInfo> createKenshuSamplingRecord(List temp,
			int recordCount) {
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
				
				if (answerRecordObj[8] != null) {
					answerInfo.setLatestScreenScorerId(answerRecordObj[8]
							.toString());
				}

				

				if (answerRecordObj[9] != null) {
					answerInfo.setSecondLatestScreenScorerId(answerRecordObj[9]
							.toString());
				}

				tranDescScoreInfo.setGradeNum(Integer
						.valueOf(answerRecordObj[10].toString()));
				if (answerRecordObj[11] != null) {
					answerInfo.setPunchText(answerRecordObj[11].toString());
				}
					

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

	@SuppressWarnings({ "null", "rawtypes" })
	public List<KenshuSamplingSearchRecordInfo> createGreadeWiseList(List temp, Integer recordNum) {
		List<KenshuSamplingSearchRecordInfo> kenshuSamplingSearchRecordInfoList = null;
		int totalRecords = 0;
		if (temp != null || !(temp.isEmpty())) {

			for (Object gradeRecord : temp) {
				Object[] gradeRecordObj = (Object[]) gradeRecord;
				totalRecords = totalRecords
						+ Integer.parseInt(gradeRecordObj[1].toString());
			}

			if (recordNum > totalRecords) {
				recordNum = totalRecords;
			}

			DecimalFormat df = new DecimalFormat();
			df.setMaximumFractionDigits(2);
			kenshuSamplingSearchRecordInfoList = new ArrayList<KenshuSamplingSearchRecordInfo>();
			for (Object gradeRecord : temp) {
				KenshuSamplingSearchRecordInfo kenshuInfoObj = new KenshuSamplingSearchRecordInfo();
				Object[] gradeRecordObj = (Object[]) gradeRecord;
				kenshuInfoObj.setGradeNum(Integer.parseInt(gradeRecordObj[0]
						.toString()));
				int gradeWiasecount = Integer.parseInt(gradeRecordObj[1]
						.toString());
				double ratio = ((double) gradeWiasecount / (double) totalRecords) * 100;
				kenshuInfoObj.setRatio(Double.valueOf(df.format(ratio)));
				double number = ((double) gradeWiasecount / (double) totalRecords)
						* recordNum;
				number = Math.ceil(number);
				kenshuInfoObj.setTotalNumber((int) number);
				kenshuSamplingSearchRecordInfoList.add(kenshuInfoObj);
			}
		}
		return kenshuSamplingSearchRecordInfoList;
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
