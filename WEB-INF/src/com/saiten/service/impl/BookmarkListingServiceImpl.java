package com.saiten.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.apache.struts2.ServletActionContext;
import org.hibernate.HibernateException;

import com.saiten.bean.SaitenConfig;
import com.saiten.bean.ScoringStateKey;
import com.saiten.dao.TranDescScoreHistoryDAO;
import com.saiten.exception.SaitenRuntimeException;
import com.saiten.info.BookmarkInfo;
import com.saiten.info.MstScorerInfo;
import com.saiten.info.QuestionInfo;
import com.saiten.service.BookmarkListingService;
import com.saiten.util.ErrorCode;
import com.saiten.util.SaitenUtil;
import com.saiten.util.WebAppConst;

public class BookmarkListingServiceImpl implements BookmarkListingService {

	private TranDescScoreHistoryDAO tranDescScoreHistoryDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.saiten.service.BookmarkListingService#deleteBookmarks(java.lang.Integer
	 * [], java.lang.String)
	 */
	@Override
	public int deleteBookmarks(Integer[] historySequence,
			String connectionString) {
		try {
			List<Integer> historySequenceList = Arrays.asList(historySequence);
			return tranDescScoreHistoryDAO.deleteBookmarks(historySequenceList,
					connectionString);
		} catch (HibernateException he) {
			throw new SaitenRuntimeException(
					ErrorCode.BOOKMARK_LISTING_HIBERNATE_EXCEPTION, he);
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.BOOKMARK_LISTING_SERVICE_EXCEPTION, e);
		}
	}

	/**
	 * @param mstScorerInfo
	 * @return
	 */
	private List<Short> createScoringStateList(MstScorerInfo mstScorerInfo) {
		List<Short> scoringStateList = new ArrayList<Short>();
		LinkedHashMap<ScoringStateKey, List<Short>> historyScoringStatesMap = ((SaitenConfig) ServletActionContext
				.getServletContext().getAttribute("saitenConfigObject"))
				.getHistoryScoringStatesMap();
		Set<ScoringStateKey> scoringStateKeys = historyScoringStatesMap
				.keySet();
		char noDbUpdateFlag = mstScorerInfo.getNoDbUpdate();

		if (scoringStateKeys != null) {
			for (ScoringStateKey scoringStateKey : scoringStateKeys) {
				if (scoringStateKey.getNoDbUpdate() == noDbUpdateFlag) {
					scoringStateList.addAll(historyScoringStatesMap
							.get(scoringStateKey));
				}
			}
		}
		return scoringStateList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.saiten.service.BookmarkListingService#findBookmarkInfoList(com.saiten
	 * .info.MstScorerInfo, com.saiten.info.QuestionInfo, java.lang.Integer,
	 * java.lang.Integer)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List<BookmarkInfo> findBookmarkInfoList(MstScorerInfo mstScorerInfo,
			QuestionInfo questionInfo, Integer startRecord, Integer endRecord,
			String connectionString, List<Integer> questionSeqList) {
		List<BookmarkInfo> bookmarkInfoList = null;
		List bookmarkInfoListObj = null;
		boolean bookmarkScreenFlag = WebAppConst.TRUE;
		try {
			List<Short> scoringStateList = createScoringStateList(mstScorerInfo);

			List<Integer> questionSequenceList = new ArrayList<Integer>();
			String connectionStringObj = null;
			String menuId = questionInfo.getMenuId();
			if (menuId
					.equals(WebAppConst.SPECIAL_SCORING_OMR_READ_FAIL_MENU_ID)
					|| menuId
							.equals(WebAppConst.SPECIAL_SCORING_ENLARGE_TYPE_MENU_ID)) {
				questionSequenceList = questionSeqList;
				connectionStringObj = connectionString;
			} else {
				questionSequenceList.add(questionInfo.getQuestionSeq());
				connectionStringObj = questionInfo.getConnectionString();
			}

			bookmarkInfoListObj = tranDescScoreHistoryDAO.findHistoryInfoList(
					mstScorerInfo.getScorerId(), scoringStateList,
					questionSequenceList, connectionStringObj, startRecord,
					endRecord, bookmarkScreenFlag);
			if (!bookmarkInfoListObj.isEmpty()) {
				bookmarkInfoList = new ArrayList<BookmarkInfo>();
				bookmarkInfoList = createBookmarkInfoList(bookmarkInfoListObj,
						questionInfo, connectionStringObj);
			}
			return bookmarkInfoList;
		} catch (HibernateException he) {
			throw new SaitenRuntimeException(
					ErrorCode.BOOKMARK_LISTING_HIBERNATE_EXCEPTION, he);
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.BOOKMARK_LISTING_SERVICE_EXCEPTION, e);
		}
	}

	/**
	 * @param bookmarkInfoListObj
	 * @param questionInfo
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private List<BookmarkInfo> createBookmarkInfoList(List bookmarkInfoListObj,
			QuestionInfo questionInfo, String connectionString) {
		List<BookmarkInfo> bookmarkInfoList = new ArrayList<BookmarkInfo>();
		BookmarkInfo bookmarkInfo = null;
		for (Object object : bookmarkInfoListObj) {
			bookmarkInfo = new BookmarkInfo();

			Object[] bookmarkInfoObj = (Object[]) object;
			bookmarkInfo.setHistorySequence((Integer) bookmarkInfoObj[0]);
			bookmarkInfo.setAnswerNumber((String) bookmarkInfoObj[2]);
			bookmarkInfo.setScoringStateName(SaitenUtil
					.getStateNameByScoringState((Short) bookmarkInfoObj[3]));
			bookmarkInfo.setUpdateDate((Date) bookmarkInfoObj[4]);
			Integer gradeNum = null;
			int questionSeq;
			String menuId = questionInfo.getMenuId();
			if (menuId
					.equals(WebAppConst.SPECIAL_SCORING_OMR_READ_FAIL_MENU_ID)
					|| menuId
							.equals(WebAppConst.SPECIAL_SCORING_ENLARGE_TYPE_MENU_ID)) {
				questionSeq = (Integer) bookmarkInfoObj[8];
			} else {
				questionSeq = questionInfo.getQuestionSeq();
			}
			bookmarkInfo.setQuestionSequence(questionSeq);
			bookmarkInfo.setConnectionString(connectionString);
			// null check since, There is no entry in
			// 'gradeSequenceWiseMstGradeMap' with gradeSeq == null
			if (bookmarkInfoObj[5] != null) {
				gradeNum = SaitenUtil.getGradeNumByGradeSequence(
						(Integer) bookmarkInfoObj[5], questionSeq);
			}
			bookmarkInfo.setGradeNum(gradeNum);

			bookmarkInfo
					.setPendingCategory(SaitenUtil
							.getPendingCategoryByPendingCategorySeq((Integer) bookmarkInfoObj[6]));
			bookmarkInfo.setComment((String) bookmarkInfoObj[7]);
			bookmarkInfo.setSubjectName(SaitenUtil
					.getSubjectNameByQuestionSequence(questionSeq));
			bookmarkInfo.setQuestionNumber(SaitenUtil
					.getQuestionNumByQuestionSequence(questionSeq));

			// null check since, There is no entry in
			// 'gradeSequenceWiseMstGradeMap' with gradeSeq == null
			if (bookmarkInfoObj[5] != null) {
				bookmarkInfo.setResult(SaitenUtil.getResultByGradeSequence(
						(Integer) bookmarkInfoObj[5], questionSeq));
			}
			if (bookmarkInfoObj[9] == WebAppConst.QUALITY_MARK_FLAG_TRUE) {
				bookmarkInfo.setIsQualityRecord(WebAppConst.ONE);
			} else if (bookmarkInfoObj[9] == WebAppConst.QUALITY_MARK_FLAG_FALSE) {
				bookmarkInfo.setIsQualityRecord(WebAppConst.ZERO);
			}
			bookmarkInfoList.add(bookmarkInfo);
		}
		return bookmarkInfoList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.saiten.service.BookmarkListingService#findBookmarkRecordCount(com
	 * .saiten.info.MstScorerInfo, com.saiten.info.QuestionInfo)
	 */
	@Override
	public int findBookmarkRecordCount(MstScorerInfo mstScorerInfo,
			QuestionInfo questionInfo, String connectionString,
			List<Integer> questionSeqList) {
		try {
			boolean bookmarkScreenFlag = WebAppConst.TRUE;
			List<Short> scoringStateList = createScoringStateList(mstScorerInfo);

			List<Integer> questionSequenceList = new ArrayList<Integer>();
			String connectionStringObj = null;
			String menuId = questionInfo.getMenuId();
			if (menuId
					.equals(WebAppConst.SPECIAL_SCORING_OMR_READ_FAIL_MENU_ID)
					|| menuId
							.equals(WebAppConst.SPECIAL_SCORING_ENLARGE_TYPE_MENU_ID)) {
				questionSequenceList = questionSeqList;
				connectionStringObj = connectionString;
			} else {
				questionSequenceList.add(questionInfo.getQuestionSeq());
				connectionStringObj = questionInfo.getConnectionString();
			}

			return tranDescScoreHistoryDAO.findHistoryRecordCount(
					mstScorerInfo.getScorerId(), questionSequenceList,
					connectionStringObj, scoringStateList, bookmarkScreenFlag);
		} catch (HibernateException he) {
			throw new SaitenRuntimeException(
					ErrorCode.BOOKMARK_LISTING_HIBERNATE_EXCEPTION, he);
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.BOOKMARK_LISTING_SERVICE_EXCEPTION, e);
		}
	}

	@Override
	public List<BookmarkInfo> sortBookmarkInfoList(
			List<BookmarkInfo> bookmarkInfoList) {

		Collections.sort(bookmarkInfoList, new Comparator<BookmarkInfo>() {

			@Override
			public int compare(BookmarkInfo bookmarkInfo1,
					BookmarkInfo bookmarkInfo2) {
				return bookmarkInfo2.getUpdateDate().compareTo(
						bookmarkInfo1.getUpdateDate());
			}

		});
		return bookmarkInfoList;

	}

	/**
	 * @param tranDescScoreHistoryDAO
	 */
	public void setTranDescScoreHistoryDAO(
			TranDescScoreHistoryDAO tranDescScoreHistoryDAO) {
		this.tranDescScoreHistoryDAO = tranDescScoreHistoryDAO;
	}

}
