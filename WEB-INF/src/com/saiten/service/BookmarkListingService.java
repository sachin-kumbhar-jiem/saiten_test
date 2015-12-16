package com.saiten.service;

import java.util.List;

import com.saiten.info.BookmarkInfo;
import com.saiten.info.MstScorerInfo;
import com.saiten.info.QuestionInfo;

/**
 * @author Administrator
 * @version 1.0
 * @created 21-Dec-2012 11:28:56 AM
 */
public interface BookmarkListingService {

	/**
	 * 
	 * @param historySequence
	 */
	public int deleteBookmarks(Integer[] historySequence,
			String connectionString);

	/**
	 * 
	 * @param mstScorerInfo
	 * @param questionInfo
	 */
	public List<BookmarkInfo> findBookmarkInfoList(MstScorerInfo mstScorerInfo,
			QuestionInfo questionInfo, Integer startRecord, Integer endRecord,
			String connectionString, List<Integer> questionSeqList);

	public int findBookmarkRecordCount(MstScorerInfo mstScorerInfo,
			QuestionInfo questionInfo, String connectionString,
			List<Integer> questionSeqList);

	public List<BookmarkInfo> sortBookmarkInfoList(
			List<BookmarkInfo> bookmarkInfoList);

}