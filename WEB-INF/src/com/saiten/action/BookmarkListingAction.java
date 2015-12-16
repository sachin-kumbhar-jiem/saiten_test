package com.saiten.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;

import com.opensymphony.xwork2.ActionSupport;
import com.saiten.exception.SaitenRuntimeException;
import com.saiten.info.BookmarkInfo;
import com.saiten.info.MstScorerInfo;
import com.saiten.info.QuestionInfo;
import com.saiten.info.TranDescScoreInfo;
import com.saiten.service.BookmarkListingService;
import com.saiten.util.ErrorCode;
import com.saiten.util.SaitenUtil;
import com.saiten.util.WebAppConst;

/**
 * @author Administrator
 * @version 1.0
 * @created 21-Dec-2012 11:28:55 AM
 */
public class BookmarkListingAction extends ActionSupport implements
		SessionAware, ServletRequestAware {

	private static Logger log = Logger.getLogger(BookmarkListingAction.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, Object> session;
	List<BookmarkInfo> bookmarkInfoList;
	private BookmarkListingService bookmarkListingService;
	private String[] historySequence;
	private HttpServletRequest request;
	private String pageNumber;
	private Integer questionSequence;
	private boolean forcedScoringFlag;
	private static final String OMR_ENLARGE_PAGINATION = "omrEnlargePagination";

	@SuppressWarnings("unchecked")
	public String deleteBookmarks() {
		try {
			QuestionInfo questionInfo = (QuestionInfo) session
					.get("questionInfo");
			MstScorerInfo mstScorerInfo = (MstScorerInfo) session
					.get("scorerInfo");
			if (questionInfo != null) {
				String menuId = questionInfo.getMenuId();
				if (menuId
						.equals(WebAppConst.SPECIAL_SCORING_OMR_READ_FAIL_MENU_ID)
						|| menuId
								.equals(WebAppConst.SPECIAL_SCORING_ENLARGE_TYPE_MENU_ID)) {
					// Declare DbSpecificHistorySeqMap
					Map<String, List<Integer>> dbSpecificHistorySeqMap = new LinkedHashMap<String, List<Integer>>();
					Map<String, List<Integer>> questionSeqMap = (Map<String, List<Integer>>) session
							.get("dbSpecificQuestionSeqMap");
					Set<String> connectionStringSet = questionSeqMap.keySet();
					for (String connectionString : connectionStringSet) {
						dbSpecificHistorySeqMap.put(connectionString,
								new ArrayList<Integer>());
					}

					// Initialize DbSpecificHistorySeqMap
					buildDbSpecificHistoryseqenceMap(dbSpecificHistorySeqMap);

					// Delete bookmarks Db wise.
					for (String connectionString : connectionStringSet) {
						List<Integer> dbSpecificHistorySeqList = dbSpecificHistorySeqMap
								.get(connectionString);
						if (!dbSpecificHistorySeqList.isEmpty()) {
							Integer[] historySequence = dbSpecificHistorySeqList
									.toArray(new Integer[dbSpecificHistorySeqList
											.size()]);
							// delete bookmarks from db.
							bookmarkListingService.deleteBookmarks(
									(Integer[]) historySequence,
									connectionString);
							log.info(mstScorerInfo.getScorerId() + "-" + menuId
									+ "-" + "Deleted Bookmarks."
									+ "-{ Question sequence: "
									+ questionInfo.getQuestionSeq()
									+ ", History sequences: "
									+ Arrays.toString(historySequence) + "}");
							// delete bookmarks from 'bookmarkInfoList'.
							removeDeletedBookmarks((Integer[]) historySequence,
									connectionString);
						}
					}

				} else {
					Integer[] historySequence = SaitenUtil
							.stringArrayToIntegerArray(this.historySequence);
					bookmarkListingService.deleteBookmarks(
							(Integer[]) historySequence,
							questionInfo.getConnectionString());
					log.info(mstScorerInfo.getScorerId() + "-" + menuId + "-"
							+ "Deleted Bookmarks." + "-{ Question sequence: "
							+ questionInfo.getQuestionSeq()
							+ ", History sequences: "
							+ Arrays.toString(historySequence) + "}");
				}
				if (mstScorerInfo != null && questionInfo != null) {
					int count = 0;
					if (menuId
							.equals(WebAppConst.SPECIAL_SCORING_OMR_READ_FAIL_MENU_ID)
							|| menuId
									.equals(WebAppConst.SPECIAL_SCORING_ENLARGE_TYPE_MENU_ID)) {
						Map<String, List<Integer>> questionSeqMap = (Map<String, List<Integer>>) session
								.get("dbSpecificQuestionSeqMap");
						Set<String> connectionStringSet = questionSeqMap
								.keySet();
						for (String connectionString : connectionStringSet) {
							List<Integer> questionSeqList = questionSeqMap
									.get(connectionString);
							if ((questionSeqList != null)
									&& (!questionSeqList.isEmpty())) {
								count += bookmarkListingService
										.findBookmarkRecordCount(mstScorerInfo,
												questionInfo, connectionString,
												questionSeqList);
							}

						}
					} else {
						String connectionString = null;
						List<Integer> questionSeqList = null;
						count = bookmarkListingService.findBookmarkRecordCount(
								mstScorerInfo, questionInfo, connectionString,
								questionSeqList);
					}
					if (count != 0) {
						session.put("count", count);
						setSessionAttributeForDispalyTag();
					} else {
						session.put("count", count);
						setSessionAttributeForDispalyTag();
					}
				}
			}
			String a = (String) session.get("a");
			if (!StringUtils.isBlank(a)) {
				int startRecord = (Integer.parseInt(a) - 1)
						* Integer
								.parseInt(getText(WebAppConst.BOOKMARK_PAGESIZE));
				Integer count = (Integer) session.get("recordCount");
				if (startRecord > 0 && startRecord == count) {
					session.put("a", Integer.toString(Integer.parseInt(a) - 1));
					pageNumber = session.get("a").toString();
				} else {
					session.put("a", a);
					pageNumber = a;
				}
			} else {
				// pageNumber will be '1' for a=null
				pageNumber = "1";
			}
			String menuId = questionInfo.getMenuId();
			if (menuId
					.equals(WebAppConst.SPECIAL_SCORING_OMR_READ_FAIL_MENU_ID)
					|| menuId
							.equals(WebAppConst.SPECIAL_SCORING_ENLARGE_TYPE_MENU_ID)) {
				return OMR_ENLARGE_PAGINATION;
			} else {
				return SUCCESS;
			}
		} catch (SaitenRuntimeException we) {
			throw we;
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.BOOKMARK_LISTING_ACTION_EXCEPTION, e);
		}
	}

	@SuppressWarnings("unchecked")
	public String loadBookmarkList() {
		int count = 0;
		session.put("count", count); // list count 0
		try {

			TranDescScoreInfo sessionTranDescScoreInfo = (TranDescScoreInfo) session
					.get("tranDescScoreInfo");
			// Unlock the locked history answer before loading bookmark list.
			if (sessionTranDescScoreInfo != null
					&& sessionTranDescScoreInfo.getAnswerInfo().getHistorySeq() != null) {
				SaitenUtil.unlockHistoryAnswer(sessionTranDescScoreInfo);
			}

			if (session.get("a") != null) {
				session.remove("a");
			}
			MstScorerInfo mstScorerInfo = (MstScorerInfo) session
					.get("scorerInfo");
			QuestionInfo questionInfo = (QuestionInfo) session
					.get("questionInfo");
			String menuId = questionInfo.getMenuId();
			log.info(mstScorerInfo.getScorerId() + "-" + menuId + "-"
					+ "Loading bookmark list screen."
					+ "-{ Question Sequence: " + questionInfo.getQuestionSeq()
					+ "}");
			if (mstScorerInfo != null && questionInfo != null) {
				if (menuId
						.equals(WebAppConst.SPECIAL_SCORING_OMR_READ_FAIL_MENU_ID)
						|| menuId
								.equals(WebAppConst.SPECIAL_SCORING_ENLARGE_TYPE_MENU_ID)) {
					Map<String, List<Integer>> questionSeqMap = (Map<String, List<Integer>>) session
							.get("dbSpecificQuestionSeqMap");
					Set<String> connectionStringSet = questionSeqMap.keySet();
					for (String connectionString : connectionStringSet) {
						List<Integer> questionSeqList = questionSeqMap
								.get(connectionString);
						if ((questionSeqList != null)
								&& (!questionSeqList.isEmpty())) {
							count += bookmarkListingService
									.findBookmarkRecordCount(mstScorerInfo,
											questionInfo, connectionString,
											questionSeqList);
						}
					}
				} else {
					String connectionString = null;
					List<Integer> questionSeqList = null;
					count = bookmarkListingService.findBookmarkRecordCount(
							mstScorerInfo, questionInfo, connectionString,
							questionSeqList);
				}
				if (count != 0) {
					session.put("count", count);
					setSessionAttributeForDispalyTag();
					if (menuId
							.equals(WebAppConst.SPECIAL_SCORING_OMR_READ_FAIL_MENU_ID)
							|| menuId
									.equals(WebAppConst.SPECIAL_SCORING_ENLARGE_TYPE_MENU_ID)) {
						loadOmrEnlargeBookmarkList();
					} else {
						return pagination();
					}
				} else {
					session.put("count", count);
					setSessionAttributeForDispalyTag();
				}
			}
			return SUCCESS;
		} catch (SaitenRuntimeException we) {
			throw we;
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.BOOKMARK_LISTING_ACTION_EXCEPTION, e);
		}
	}

	@SuppressWarnings("unchecked")
	private String loadOmrEnlargeBookmarkList() {
		MstScorerInfo mstScorerInfo = (MstScorerInfo) session.get("scorerInfo");
		QuestionInfo questionInfo = (QuestionInfo) session.get("questionInfo");

		Map<String, List<Integer>> questionSeqMap = (Map<String, List<Integer>>) session
				.get("dbSpecificQuestionSeqMap");
		Set<String> connectionStringSet = questionSeqMap.keySet();
		Integer startRecord = null;
		Integer endRecord = null;
		bookmarkInfoList = new ArrayList<BookmarkInfo>();
		for (String connectionString : connectionStringSet) {
			List<Integer> questionSeqList = questionSeqMap
					.get(connectionString);
			if ((questionSeqList != null) && (!questionSeqList.isEmpty())) {
				List<BookmarkInfo> bdSpecificBookmarkInfoList = bookmarkListingService
						.findBookmarkInfoList(mstScorerInfo, questionInfo,
								startRecord, endRecord, connectionString,
								questionSeqList);
				if (bdSpecificBookmarkInfoList != null) {
					bookmarkInfoList.addAll(bdSpecificBookmarkInfoList);
				}
			}
		}
		if (bookmarkInfoList != null) {
			bookmarkInfoList = bookmarkListingService
					.sortBookmarkInfoList(bookmarkInfoList);
			session.put("bookmarkInfoList", bookmarkInfoList);
		}
		return omrEnlargePagination();
	}

	@SuppressWarnings("unchecked")
	public String omrEnlargePagination() {

		Integer startRecord = 0;
		Integer endRecord = Integer
				.parseInt(getText(WebAppConst.BOOKMARK_PAGESIZE));
		try {
			String a = (String) session.get("a");
			String pageNumber = request.getParameter((new ParamEncoder(
					"bookmarkInfoResult")
					.encodeParameterName(TableTagParameters.PARAMETER_PAGE)));

			if (!StringUtils.isBlank(pageNumber)) {
				a = pageNumber;
			}
			if (!StringUtils.isBlank(a)) {
				startRecord = (Integer.parseInt(a) - 1)
						* Integer
								.parseInt(getText(WebAppConst.BOOKMARK_PAGESIZE));
				session.put("a", a);
			}

			List<BookmarkInfo> sessionBookmarkInfoList = (List<BookmarkInfo>) session
					.get("bookmarkInfoList");
			bookmarkInfoList = new ArrayList<BookmarkInfo>();
			if (sessionBookmarkInfoList != null) {
				int limit = sessionBookmarkInfoList.size() > (endRecord + startRecord) ? endRecord
						+ startRecord
						: sessionBookmarkInfoList.size();
				for (int i = startRecord; i < limit; i++) {
					bookmarkInfoList.add(sessionBookmarkInfoList.get(i));
				}
			}
			return SUCCESS;
		} catch (SaitenRuntimeException we) {
			throw we;
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.BOOKMARK_LISTING_ACTION_EXCEPTION, e);
		}
	}

	public String pagination() {

		Integer startRecord = 0;
		Integer endRecord = Integer
				.parseInt(getText(WebAppConst.BOOKMARK_PAGESIZE));
		try {
			String a = (String) session.get("a");
			String pageNumber = request.getParameter((new ParamEncoder(
					"bookmarkInfoResult")
					.encodeParameterName(TableTagParameters.PARAMETER_PAGE)));

			if (!StringUtils.isBlank(pageNumber)) {
				a = pageNumber;
			}
			if (!StringUtils.isBlank(a)) {
				startRecord = (Integer.parseInt(a) - 1)
						* Integer
								.parseInt(getText(WebAppConst.BOOKMARK_PAGESIZE));
				session.put("a", a);
			}

			MstScorerInfo mstScorerInfo = (MstScorerInfo) session
					.get("scorerInfo");
			QuestionInfo questionInfo = (QuestionInfo) session
					.get("questionInfo");

			String connectionString = null;
			List<Integer> questionSeqList = null;
			if (mstScorerInfo != null && questionInfo != null) {
				bookmarkInfoList = bookmarkListingService.findBookmarkInfoList(
						mstScorerInfo, questionInfo, startRecord, endRecord,
						connectionString, questionSeqList);
			}
			return SUCCESS;
		} catch (SaitenRuntimeException we) {
			throw we;
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.BOOKMARK_LISTING_ACTION_EXCEPTION, e);
		}

	}

	private void setSessionAttributeForDispalyTag() {
		session.put("pagesize",
				Integer.parseInt(getText(WebAppConst.BOOKMARK_PAGESIZE)));
		session.put("recordCount", session.get("count"));
	}

	@SuppressWarnings("unchecked")
	private void buildDbSpecificHistoryseqenceMap(
			Map<String, List<Integer>> dbSpecificHistorySeqMap) {
		Map<String, List<Integer>> questionSeqMap = (Map<String, List<Integer>>) session
				.get("dbSpecificQuestionSeqMap");
		Set<String> connectionStringSet = questionSeqMap.keySet();
		for (String connectionString : connectionStringSet) {
			List<Integer> dbSpecificHistorySeqList = dbSpecificHistorySeqMap
					.get(connectionString);
			for (String historySequenceObj : historySequence) {
				String[] historySequenceObjArray = historySequenceObj
						.split("-");
				if (historySequenceObjArray[1].equals(connectionString)) {
					dbSpecificHistorySeqList.add(Integer
							.valueOf(historySequenceObjArray[0]));
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void removeDeletedBookmarks(Integer[] historySequence,
			String connectionString) {
		List<Integer> historySeqList = Arrays.asList(historySequence);
		List<BookmarkInfo> sessionBookmarkInfoList = (List<BookmarkInfo>) session
				.get("bookmarkInfoList");
		List<BookmarkInfo> deleteBookmarkList = new ArrayList<BookmarkInfo>();
		for (BookmarkInfo bookmarkInfo : sessionBookmarkInfoList) {
			if (bookmarkInfo.getConnectionString().equals(connectionString)
					&& (historySeqList.contains(bookmarkInfo
							.getHistorySequence()))) {
				deleteBookmarkList.add(bookmarkInfo);
				// sessionBookmarkInfoList.remove(bookmarkInfo);
			}
		}
		sessionBookmarkInfoList.removeAll(deleteBookmarkList);
		session.put("bookmarkInfoList", sessionBookmarkInfoList);
	}

	public List<BookmarkInfo> getBookmarkInfoList() {
		return bookmarkInfoList;
	}

	/**
	 * @param bookmarkInfoList
	 */
	public void setBookmarkInfoList(List<BookmarkInfo> bookmarkInfoList) {
		this.bookmarkInfoList = bookmarkInfoList;
	}

	public String[] getHistorySequence() {
		return historySequence;
	}

	/**
	 * @param historySequence
	 */
	public void setHistorySequence(String[] historySequence) {
		this.historySequence = historySequence;
	}

	/**
	 * @param bookmarkListingService
	 */
	public void setBookmarkListingService(
			BookmarkListingService bookmarkListingService) {
		this.bookmarkListingService = bookmarkListingService;
	}

	/**
	 * 
	 * @param session
	 */
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletRequest getServletRequest() {
		return request;
	}

	public String getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getQuestionSequence() {
		return questionSequence;
	}

	public void setQuestionSequence(Integer questionSequence) {
		this.questionSequence = questionSequence;
	}

	public boolean isForcedScoringFlag() {
		return forcedScoringFlag;
	}

	public void setForcedScoringFlag(boolean forcedScoringFlag) {
		this.forcedScoringFlag = forcedScoringFlag;
	}

}