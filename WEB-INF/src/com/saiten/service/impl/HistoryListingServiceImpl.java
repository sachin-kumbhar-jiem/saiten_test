package com.saiten.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.ServletActionContext;
import org.hibernate.HibernateException;
import org.springframework.web.context.ContextLoader;

import com.saiten.bean.SaitenConfig;
import com.saiten.bean.ScoringStateKey;
import com.saiten.dao.TranDescScoreHistoryDAO;
import com.saiten.dao.TranQualitycheckScoreDAO;
import com.saiten.exception.SaitenRuntimeException;
import com.saiten.info.HistoryInfo;
import com.saiten.info.MstScorerInfo;
import com.saiten.info.QuestionInfo;
import com.saiten.service.HistoryListingService;
import com.saiten.util.ErrorCode;
import com.saiten.util.SaitenUtil;
import com.saiten.util.WebAppConst;

/**
 * @author Administrator
 * @version 1.0
 * @created 12-Dec-2012 3:54:16 PM
 */
public class HistoryListingServiceImpl implements HistoryListingService {

	private TranDescScoreHistoryDAO tranDescScoreHistoryDAO;
	private TranQualitycheckScoreDAO tranQualitycheckScoreDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.saiten.service.HistoryListingService#findHistoryInfoList(com.saiten
	 * .info.MstScorerInfo, com.saiten.info.QuestionInfo, java.lang.Integer,
	 * java.lang.Integer)
	 */
	@SuppressWarnings({ "rawtypes", "unused" })
	public List<HistoryInfo> findHistoryInfoList(MstScorerInfo mstScorerInfo, QuestionInfo questionInfo,
			Integer startRecord, Integer endRecord, String connectionString, List<Integer> questionSeqList) {
		List<HistoryInfo> historyInfoList = null;
		List historyInfoListObj = null;
		boolean bookmarkScreenFlag = WebAppConst.FALSE;
		try {
			ScoringStateKey scoringStateKey = (ScoringStateKey) ContextLoader.getCurrentWebApplicationContext()
					.getBean("scoringStateKey");

			scoringStateKey.setMenuId(questionInfo.getMenuId());
			scoringStateKey.setNoDbUpdate(mstScorerInfo.getNoDbUpdate());

			LinkedHashMap<ScoringStateKey, List<Short>> historyScoringStatesMap = ((SaitenConfig) ServletActionContext
					.getServletContext().getAttribute("saitenConfigObject")).getHistoryScoringStatesMap();
			List<Short> scoringStateList = historyScoringStatesMap.get(scoringStateKey);

			List<Integer> questionSequenceList = new ArrayList<Integer>();
			String connectionStringObj = null;
			String menuId = questionInfo.getMenuId();
			if (menuId.equals(WebAppConst.SPECIAL_SCORING_OMR_READ_FAIL_MENU_ID)
					|| menuId.equals(WebAppConst.SPECIAL_SCORING_ENLARGE_TYPE_MENU_ID)) {
				questionSequenceList = questionSeqList;
				connectionStringObj = connectionString;
			} else {
				questionSequenceList.add(questionInfo.getQuestionSeq());
				connectionStringObj = questionInfo.getConnectionString();
			}
			Character questionType = questionInfo.getQuestionType();
			if ((menuId.equals(WebAppConst.FIRST_SCORING_MENU_ID) || menuId.equals(WebAppConst.SECOND_SCORING_MENU_ID))
			/*
			 * && (questionType == WebAppConst.SPEAKING_TYPE || questionType ==
			 * WebAppConst.WRITING_TYPE)
			 */) {
				historyInfoListObj = tranDescScoreHistoryDAO.findQcAndHistoryInfoList(mstScorerInfo.getScorerId(),
						scoringStateList, questionSequenceList, connectionStringObj, startRecord, endRecord,
						bookmarkScreenFlag);

			} else {
				historyInfoListObj = tranDescScoreHistoryDAO.findHistoryInfoList(mstScorerInfo.getScorerId(),
						scoringStateList, questionSequenceList, connectionStringObj, startRecord, endRecord,
						bookmarkScreenFlag);
			}

			if (!historyInfoListObj.isEmpty()) {
				historyInfoList = new ArrayList<HistoryInfo>();
				historyInfoList = createHistoryInfoList(historyInfoListObj, questionInfo, connectionStringObj);
			}
			return historyInfoList;
		} catch (HibernateException he) {
			throw new SaitenRuntimeException(ErrorCode.HISTORY_LISTING_HIBERNATE_EXCEPTION, he);
		} catch (Exception e) {
			throw new SaitenRuntimeException(ErrorCode.HISTORY_LISTING_SERVICE_EXCEPTION, e);
		}
	}

	public List<HistoryInfo> sortHistoryInfoList(List<HistoryInfo> historyInfoList) {

		Collections.sort(historyInfoList, new Comparator<HistoryInfo>() {

			@Override
			public int compare(HistoryInfo historyInfo1, HistoryInfo historyInfo2) {
				return historyInfo2.getUpdateDate().compareTo(historyInfo1.getUpdateDate());
			}

		});
		return historyInfoList;

	}

	/**
	 * @param historyInfoListObj
	 * @param questionInfo
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unused" })
	private List<HistoryInfo> createHistoryInfoList(List historyInfoListObj, QuestionInfo questionInfo,
			String connectionString) {
		String menuId = questionInfo.getMenuId();
		Character questionType = questionInfo.getQuestionType();
		List<HistoryInfo> historyInfoList = new ArrayList<HistoryInfo>();
		HistoryInfo historyInfo = null;
		for (Object object : historyInfoListObj) {
			historyInfo = new HistoryInfo();
			Object[] historyInfoObj = (Object[]) object;
			if ((menuId.equals(WebAppConst.FIRST_SCORING_MENU_ID) || menuId.equals(WebAppConst.SECOND_SCORING_MENU_ID))
			/*
			 * && (questionType == WebAppConst.SPEAKING_TYPE || questionType ==
			 * WebAppConst.WRITING_TYPE)
			 */) {

				if (((BigInteger) historyInfoObj[9]).intValue() == WebAppConst.ONE) {
					historyInfo.setQcSeq((Integer) historyInfoObj[0]);
				} else {
					historyInfo.setHistorySequence((Integer) historyInfoObj[0]);
					historyInfo.setBookmarkFlag((Character) historyInfoObj[1]);
				}
				historyInfo.setIsQualityRecord(((BigInteger) historyInfoObj[9]).intValue());

			} else {
				historyInfo.setHistorySequence((Integer) historyInfoObj[0]);
				historyInfo.setBookmarkFlag((Character) historyInfoObj[1]);
				if (historyInfoObj[9] == WebAppConst.QUALITY_MARK_FLAG_TRUE) {
					historyInfo.setIsQualityRecord(WebAppConst.ONE);
				} else if (historyInfoObj[9] == WebAppConst.QUALITY_MARK_FLAG_FALSE) {
					historyInfo.setIsQualityRecord(WebAppConst.ZERO);
				}
			}
			historyInfo.setAnswerNumber((String) historyInfoObj[2]);
			historyInfo.setScoringStateName(SaitenUtil.getStateNameByScoringState((Short) historyInfoObj[3]));
			historyInfo.setUpdateDate((Date) historyInfoObj[4]);
			Integer gradeNum = null;
			int questionSeq;
			if (menuId.equals(WebAppConst.SPECIAL_SCORING_OMR_READ_FAIL_MENU_ID)
					|| menuId.equals(WebAppConst.SPECIAL_SCORING_ENLARGE_TYPE_MENU_ID)) {
				questionSeq = (Integer) historyInfoObj[8];
			} else {
				questionSeq = questionInfo.getQuestionSeq();
			}
			historyInfo.setQuestionSequence(questionSeq);
			historyInfo.setConnectionString(connectionString);
			// null check since, There is no entry in
			// 'gradeSequenceWiseMstGradeMap' with gradeSeq == null
			if (historyInfoObj[5] != null) {
				gradeNum = SaitenUtil.getGradeNumByGradeSequence((Integer) historyInfoObj[5], questionSeq);
			}
			historyInfo.setGradeNum(gradeNum);

			historyInfo
					.setPendingCategory(SaitenUtil.getPendingCategoryByPendingCategorySeq((Integer) historyInfoObj[6]));
			historyInfo.setComment((String) historyInfoObj[7]);
			historyInfo.setSubjectName(SaitenUtil.getSubjectNameByQuestionSequence(questionSeq));
			historyInfo.setQuestionNumber(SaitenUtil.getQuestionNumByQuestionSequence(questionSeq));
			if (historyInfoObj[5] != null) {
				historyInfo.setResult(SaitenUtil.getResultByGradeSequence((Integer) historyInfoObj[5], questionSeq));
			}
			historyInfo.setAnswerSeq((Integer) historyInfoObj[10]);
			historyInfoList.add(historyInfo);
		}
		return historyInfoList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saiten.service.HistoryListingService#findHistoryRecordCount(com.
	 * saiten .info.MstScorerInfo, com.saiten.info.QuestionInfo)
	 */
	@SuppressWarnings("unused")
	@Override
	public int findHistoryRecordCount(MstScorerInfo mstScorerInfo, QuestionInfo questionInfo, String connectionString,
			List<Integer> questionSeqList) {
		boolean bookmarkScreenFlag = WebAppConst.FALSE;
		try {
			ScoringStateKey scoringStateKey = (ScoringStateKey) ContextLoader.getCurrentWebApplicationContext()
					.getBean("scoringStateKey");
			scoringStateKey.setMenuId(questionInfo.getMenuId());
			scoringStateKey.setNoDbUpdate(mstScorerInfo.getNoDbUpdate());

			LinkedHashMap<ScoringStateKey, List<Short>> historyScoringStatesMap = ((SaitenConfig) ServletActionContext
					.getServletContext().getAttribute("saitenConfigObject")).getHistoryScoringStatesMap();
			List<Short> scoringStateList = historyScoringStatesMap.get(scoringStateKey);

			List<Integer> questionSequenceList = new ArrayList<Integer>();
			String connectionStringObj = null;
			String menuId = questionInfo.getMenuId();
			if (menuId.equals(WebAppConst.SPECIAL_SCORING_OMR_READ_FAIL_MENU_ID)
					|| menuId.equals(WebAppConst.SPECIAL_SCORING_ENLARGE_TYPE_MENU_ID)) {
				questionSequenceList = questionSeqList;
				connectionStringObj = connectionString;
			} else {
				questionSequenceList.add(questionInfo.getQuestionSeq());
				connectionStringObj = questionInfo.getConnectionString();
			}
			int qcRecordsCount = 0;
			int historyRecordCount = 0;
			Character questionType = questionInfo.getQuestionType();
			if (menuId.equals(WebAppConst.FIRST_SCORING_MENU_ID) || menuId.equals(WebAppConst.SECOND_SCORING_MENU_ID)
			/*
			 * && (WebAppConst.SPEAKING_TYPE.equals(questionType) ||
			 * WebAppConst.WRITING_TYPE .equals(questionType))
			 */) {
				qcRecordsCount = tranQualitycheckScoreDAO.findQcHistoryRecordCount(mstScorerInfo.getScorerId(),
						questionSequenceList, connectionStringObj, scoringStateList, bookmarkScreenFlag);
			}

			historyRecordCount = tranDescScoreHistoryDAO.findHistoryRecordCount(mstScorerInfo.getScorerId(),
					questionSequenceList, connectionStringObj, scoringStateList, bookmarkScreenFlag);

			return (qcRecordsCount + historyRecordCount);
		} catch (HibernateException he) {
			throw new SaitenRuntimeException(ErrorCode.HISTORY_LISTING_HIBERNATE_EXCEPTION, he);
		} catch (Exception e) {
			throw new SaitenRuntimeException(ErrorCode.HISTORY_LISTING_SERVICE_EXCEPTION, e);
		}
	}

	@Override
	public List<HistoryInfo> loadOmrEnlargeHistory(MstScorerInfo mstScorerInfo, QuestionInfo questionInfo,
			Map<String, List<Integer>> questionSeqMap) {

		Set<String> connectionStringSet = questionSeqMap.keySet();
		Integer startRecord = null;
		Integer endRecord = null;
		List<HistoryInfo> historyInfoList = new ArrayList<HistoryInfo>();
		for (String connectionString : connectionStringSet) {
			List<Integer> questionSeqList = questionSeqMap.get(connectionString);
			if ((questionSeqList != null) && (!questionSeqList.isEmpty())) {
				List<HistoryInfo> dbSpecificHistoryInfoList = findHistoryInfoList(mstScorerInfo, questionInfo,
						startRecord, endRecord, connectionString, questionSeqList);
				if (dbSpecificHistoryInfoList != null) {
					historyInfoList.addAll(dbSpecificHistoryInfoList);
				}
			}

		}
		if (!historyInfoList.isEmpty()) {
			historyInfoList = sortHistoryInfoList(historyInfoList);
		}

		return historyInfoList;
	}

	/**
	 * @param tranDescScoreHistoryDAO
	 */
	public void setTranDescScoreHistoryDAO(TranDescScoreHistoryDAO tranDescScoreHistoryDAO) {
		this.tranDescScoreHistoryDAO = tranDescScoreHistoryDAO;
	}

	/**
	 * @param tranQualitycheckScoreDAO
	 *            the tranQualitycheckScoreDAO to set
	 */
	public void setTranQualitycheckScoreDAO(TranQualitycheckScoreDAO tranQualitycheckScoreDAO) {
		this.tranQualitycheckScoreDAO = tranQualitycheckScoreDAO;
	}
}