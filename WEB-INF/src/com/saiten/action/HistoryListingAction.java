package com.saiten.action;

import java.util.ArrayList;
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
import com.saiten.info.HistoryInfo;
import com.saiten.info.MstScorerInfo;
import com.saiten.info.QuestionInfo;
import com.saiten.info.TranDescScoreInfo;
import com.saiten.service.HistoryListingService;
import com.saiten.util.ErrorCode;
import com.saiten.util.SaitenUtil;
import com.saiten.util.WebAppConst;

/**
 * @author Administrator
 * @version 1.0
 * @created 12-Dec-2012 3:54:16 PM
 */
public class HistoryListingAction extends ActionSupport implements
		SessionAware, ServletRequestAware {

	private static Logger log = Logger
			.getLogger(HistoryListingAction.class);	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, Object> session;
	private List<HistoryInfo> historyInfoList;
	private HistoryListingService historyListingService;
	private HttpServletRequest request;
	private boolean forcedScoringFlag;

	@SuppressWarnings("unchecked")
	public String loadHistory() {
		int count = 0;
		session.put("count", count); // list count 0
		try {

			TranDescScoreInfo sessionTranDescScoreInfo = (TranDescScoreInfo) session
					.get("tranDescScoreInfo");
			// Unlock the locked history answer before coming back to history
			// list.
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
			log.info(mstScorerInfo.getScorerId()+"-"+menuId+"-"+"Loading history list screen."+"-{ Question Sequence: "+questionInfo.getQuestionSeq()+"}");
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
						if (questionSeqList != null
								&& !questionSeqList.isEmpty()) {
							count += historyListingService
									.findHistoryRecordCount(mstScorerInfo,
											questionInfo, connectionString,
											questionSeqList);
						}

					}

				} else {
					String connectionString = null;
					List<Integer> questionSeqList = null;
					count = historyListingService.findHistoryRecordCount(
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
						Map<String, List<Integer>> questionSeqMap = (Map<String, List<Integer>>) session
								.get("dbSpecificQuestionSeqMap");
						List<HistoryInfo> historyInfoList = historyListingService
								.loadOmrEnlargeHistory(mstScorerInfo,
										questionInfo, questionSeqMap);

						session.put("historyInfoList", historyInfoList);

						return omrEnlargePagination();
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
					ErrorCode.HISTORY_LISTING_ACTION_EXCEPTION, e);
		}
	}

	@SuppressWarnings("unchecked")
	public String omrEnlargePagination() {
		Integer startRecord = 0;
		Integer endRecord = Integer
				.parseInt(getText(WebAppConst.HISTORY_PAGESIZE));
		try {
			String a = (String) session.get("a");
			String pageNumber = request.getParameter((new ParamEncoder(
					"historyInfoResult")
					.encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
			if (!StringUtils.isBlank(pageNumber)) {
				a = pageNumber;
			}
			if (!StringUtils.isBlank(a)) {
				startRecord = (Integer.parseInt(a) - 1)
						* Integer
								.parseInt(getText(WebAppConst.HISTORY_PAGESIZE));
				session.put("a", a);
			}

			List<HistoryInfo> sessionHistoryInfoList = (List<HistoryInfo>) session
					.get("historyInfoList");
			historyInfoList = new ArrayList<HistoryInfo>();
			if (sessionHistoryInfoList != null) {
				int limit = sessionHistoryInfoList.size() > (endRecord + startRecord) ? endRecord
						+ startRecord
						: sessionHistoryInfoList.size();
				for (int i = startRecord; i < limit; i++) {
					historyInfoList.add(sessionHistoryInfoList.get(i));
				}
			}
			return SUCCESS;
		} catch (SaitenRuntimeException we) {
			throw we;
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.HISTORY_LISTING_ACTION_EXCEPTION, e);
		}
	}

	public String pagination() {

		Integer startRecord = 0;
		Integer endRecord = Integer
				.parseInt(getText(WebAppConst.HISTORY_PAGESIZE));
		try {
			String a = (String) session.get("a");
			String pageNumber = request.getParameter((new ParamEncoder(
					"historyInfoResult")
					.encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
			if (!StringUtils.isBlank(pageNumber)) {
				a = pageNumber;
			}
			if (!StringUtils.isBlank(a)) {
				startRecord = (Integer.parseInt(a) - 1)
						* Integer
								.parseInt(getText(WebAppConst.HISTORY_PAGESIZE));
				session.put("a", a);
			}

			MstScorerInfo mstScorerInfo = (MstScorerInfo) session
					.get("scorerInfo");
			QuestionInfo questionInfo = (QuestionInfo) session
					.get("questionInfo");

			String connectionString = null;
			List<Integer> questionSeqList = null;
			if (mstScorerInfo != null && questionInfo != null) {
				historyInfoList = historyListingService.findHistoryInfoList(
						mstScorerInfo, questionInfo, startRecord, endRecord,
						connectionString, questionSeqList);
			}
			return SUCCESS;
		} catch (SaitenRuntimeException we) {
			throw we;
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.HISTORY_LISTING_ACTION_EXCEPTION, e);
		}

	}

	private void setSessionAttributeForDispalyTag() {
		session.put("pagesize",
				Integer.parseInt(getText(WebAppConst.HISTORY_PAGESIZE)));
		session.put("recordCount", session.get("count"));
	}

	public List<HistoryInfo> getHistoryInfoList() {
		return historyInfoList;
	}

	/**
	 * @param historyInfoList
	 */
	public void setHistoryInfoList(List<HistoryInfo> historyInfoList) {
		this.historyInfoList = historyInfoList;
	}

	/**
	 * @param historyListingService
	 */
	public void setHistoryListingService(
			HistoryListingService historyListingService) {
		this.historyListingService = historyListingService;
	}

	/**
	 * 
	 * @param session
	 */
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.struts2.interceptor.ServletRequestAware#setServletRequest(
	 * javax.servlet.http.HttpServletRequest)
	 */
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletRequest getServletRequest() {
		return request;
	}

	public boolean isForcedScoringFlag() {
		return forcedScoringFlag;
	}

	public void setForcedScoringFlag(boolean forcedScoringFlag) {
		this.forcedScoringFlag = forcedScoringFlag;
	}

}