package com.saiten.action;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.web.context.ContextLoader;

import com.opensymphony.xwork2.ActionSupport;
import com.saiten.exception.SaitenRuntimeException;
import com.saiten.info.DailyStatusReportListInfo;
import com.saiten.info.DailyStatusSearchInfo;
import com.saiten.info.DailyStatusSearchScorerInfo;
import com.saiten.info.MstScorerInfo;
import com.saiten.info.QuestionInfo;
import com.saiten.info.ScoreSearchInfo;
import com.saiten.service.DailyStatusSearchService;
import com.saiten.service.ScoreSearchService;
import com.saiten.util.ErrorCode;
import com.saiten.util.SaitenUtil;
import com.saiten.util.WebAppConst;

/**
 * @author sachin
 * @version 1.0
 * @created 13-Dec-2012 11:53:29 AM
 */
public class DailyStatusSearchAction extends ActionSupport implements
		SessionAware, ServletRequestAware {

	private static Logger log = Logger.getLogger(DailyStatusSearchAction.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, Object> session;

	private ScoreSearchInfo scoreSearchInfo;
	private ScoreSearchService scoreSearchService;
	private HttpServletRequest request;
	private DailyStatusSearchInfo dailyStatusSearchInfo;
	private Map<Byte, String> rollMap;
	private DailyStatusSearchService dailyStatusSearchService;
	private Properties saitenGlobalProperties;
	private Byte[] defaultSelectedRoll;
	private List<String> loggedInScorerSubjectList;
	private String questionSeq;
	private QuestionInfo questionInfo;
	private List<DailyStatusReportListInfo> gradeWiseReportList;
	private List<DailyStatusReportListInfo> pendingCategoryWiseReportList;
	private String selectedMenuId;
	List<DailyStatusReportListInfo> dailyStatusReportList;
	List<DailyStatusSearchScorerInfo> dailyStatusSearchScorerInfo;

	@SuppressWarnings("rawtypes")
	public String onLoad() {
		int chkSession = 0;
		dailyStatusSearchInfo = (DailyStatusSearchInfo) session
				.get("dailyStatusSearchInfo");
		if (session.get("dailyStatusSearchInfo") == null) {
			chkSession++;
		}
		// session.put("dailyStatusSearchInfo", null);
		scoreSearchInfo = (ScoreSearchInfo) ContextLoader
				.getCurrentWebApplicationContext().getBean("scoreSearchInfo");

		try {
			// Build subjectCode - subjectName map
			Map<String, String> subjectNameList = scoreSearchService
					.findSubjectNameList();
			scoreSearchInfo.setSubjectNameList(subjectNameList);

			// Build questionType:scoreType - questionType description map.
			List questionTypeList = dailyStatusSearchService
					.findQuestionTypeList();
			Map<Integer, String> questionTypeMap = new LinkedHashMap<Integer, String>();
			Map<Integer, Character> questionTypeAndEvalMap = new LinkedHashMap<Integer, Character>();
			if (!questionTypeList.isEmpty()) {
				for (Object object : questionTypeList) {
					Object[] objs = (Object[]) object;
					Integer evalSeq = (Integer) objs[0];
					Character questionType = (Character) objs[1];
					Character scoreType = (Character) objs[2];
					String description = getQuestionTypeDescriptionByQuestionTypeAndScoreType(
							questionType, scoreType);
					questionTypeMap.put(evalSeq, description);
					questionTypeAndEvalMap.put(evalSeq, questionType);
				}
			}
			session.put("questionTypeAndEvalMap", questionTypeAndEvalMap);
			scoreSearchInfo.setQuestionTypeMap(questionTypeMap);

			scoreSearchInfo.setYearList(SaitenUtil.getCalendarYears());
			scoreSearchInfo.setMonthList(SaitenUtil.getCalendarMonths());
			scoreSearchInfo.setDaysList(SaitenUtil.getCalendardays());
			scoreSearchInfo.setHoursList(SaitenUtil.getClockHours());
			scoreSearchInfo.setMinutesList(SaitenUtil.getClockMinutes());

			// dailyStatusSearchInfo = new DailyStatusSearchInfo();

			String scorerRollForSearch = getText(WebAppConst.ROLL_ID_FOR_DAILY_STATUS_SEARCH_SCREEN);

			if (scorerRollForSearch != null && !scorerRollForSearch.equals("")) {
				rollMap = dailyStatusSearchService
						.getScorerRollMapByID(scorerRollForSearch);
			}

			String defaultSelectedScorerRollForSearch = getText(WebAppConst.ROLL_ID_FOR_DAILY_STATUS_SEARCH_SCREEN_DEFAULT_SELECTED);

			if (defaultSelectedScorerRollForSearch != null
					&& !defaultSelectedScorerRollForSearch.equals("")
					&& chkSession > 0) {

				String[] defaultSelectedScorerRollForSearchArray = defaultSelectedScorerRollForSearch
						.split(",");
				Byte[] defaultSelectedByteArray = new Byte[defaultSelectedScorerRollForSearchArray.length];
				for (int i = 0; i < defaultSelectedScorerRollForSearchArray.length; i++) {
					defaultSelectedByteArray[i] = Byte
							.parseByte(defaultSelectedScorerRollForSearchArray[i]);
				}
				defaultSelectedRoll = defaultSelectedByteArray;
			} else {
				defaultSelectedScorerRollForSearch = dailyStatusSearchInfo
						.getSelectedRoll() != null ? dailyStatusSearchInfo
						.getSelectedRoll() : "";
			}

			// build subject list for logged in scorer.
			MstScorerInfo scorerInfo = ((MstScorerInfo) session
					.get("scorerInfo"));
			int roleId = scorerInfo.getRoleId();
			if (roleId != WebAppConst.ADMIN_ROLE_ID) {
				String scorerId = ((MstScorerInfo) session.get("scorerInfo"))
						.getScorerId();
				loggedInScorerSubjectList = scoreSearchService
						.buildLoggedInScorerSubjectList(scorerId);
			}
			QuestionInfo questionInfo = new QuestionInfo();
			questionInfo.setMenuId(selectedMenuId);
			session.put("questionInfo", questionInfo);
			log.info(scorerInfo.getScorerId() + "-" + selectedMenuId + "-"
					+ "Loaded Daily Status Search Screen.");
		} catch (SaitenRuntimeException we) {
			throw we;
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.DAILY_STATUS_SEARCH_ONLOAD_ERROR, e);
		}

		return SUCCESS;
	}

	/*
	 * @SuppressWarnings("unchecked") public String dailyStatusSearchList() {
	 * Integer startRecord = 0; Integer endRecord = Integer
	 * .parseInt(saitenGlobalProperties
	 * .getProperty(WebAppConst.FORCED_AND_STATETRANSITION_LIST_PAGESIZE)); try
	 * { String a = (String) session.get("pageNumber"); String pageNumber =
	 * request.getParameter((new ParamEncoder( "scoreSamplingInfo")
	 * .encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
	 * 
	 * if (!StringUtils.isBlank(pageNumber)) { a = pageNumber; } if
	 * (!StringUtils.isBlank(a)) { startRecord = (Integer.parseInt(a) - 1)
	 * Integer .parseInt(saitenGlobalProperties
	 * .getProperty(WebAppConst.FORCED_AND_STATETRANSITION_LIST_PAGESIZE));
	 * session.put("pageNumber", a); }
	 * 
	 * QuestionInfo questionInfo = (QuestionInfo) session .get("questionInfo");
	 * MstScorerInfo mstScorerInfo = (MstScorerInfo) session .get("scorerInfo");
	 * String scorerId = mstScorerInfo.getScorerId(); Boolean
	 * forceAndStateTransitionFlag = WebAppConst.TRUE; scoreInputInfo =
	 * (ScoreInputInfo) session.get("scoreInputInfo"); scoreSamplingInfoList =
	 * (List<ScoreSamplingInfo>) scoreSearchService
	 * .searchAnswerRecords(questionInfo, scoreInputInfo, scorerId,
	 * forceAndStateTransitionFlag, startRecord, endRecord);
	 * 
	 * if (scoreSamplingInfoList != null && !scoreSamplingInfoList.isEmpty()) {
	 * session.put("scoreSamplingInfoList", scoreSamplingInfoList); }
	 * 
	 * } catch (SaitenRuntimeException we) { throw we; } catch (Exception e) {
	 * throw new SaitenRuntimeException(
	 * ErrorCode.SPECIAL_SCORE_SEARCH_ACTION_EXCEPTION, e); } return SUCCESS; }
	 */

	public String dailyStatusSearchList() {
		try {
			MstScorerInfo mstScorerInfo = (MstScorerInfo) session
					.get("scorerInfo");
			QuestionInfo questionInfo = (QuestionInfo) session
					.get("questionInfo");
			if (request.getParameter("back") != null
					&& request.getParameter("back").equals("true")) {
				dailyStatusSearchInfo = (DailyStatusSearchInfo) session
						.get("dailyStatusSearchInfo");
			}

			String[] selectedRoll = dailyStatusSearchInfo.getSelectedRoll() != null ? dailyStatusSearchInfo
					.getSelectedRoll().split(",") : new String[0];
			if (selectedRoll != null && selectedRoll.length > 0) {
				List<String> selectedRollList = new ArrayList<String>();
				for (int i = 0; i < selectedRoll.length; i++) {
					selectedRollList.add(selectedRoll[i].trim());
				}
				dailyStatusSearchInfo.setSelectedRollList(selectedRollList);
			}
			session.put("dailyStatusSearchInfo", dailyStatusSearchInfo);
			log.info(mstScorerInfo.getScorerId()
					+ "-"
					+ questionInfo.getMenuId()
					+ "-"
					+ "Loading daily status search List screen. \n Search Criteria: -{ Search Type: "
					+ dailyStatusSearchInfo.getDailyStatusSearchRadioButton()
					+ ", Subject Code: "
					+ dailyStatusSearchInfo.getSubjectCode()
					+ ", Question No.: "
					+ dailyStatusSearchInfo.getQuestionNum() + "}");
			@SuppressWarnings("unchecked")
			Map<Integer, Character> questionTypeAndEvalMap = (Map<Integer, Character>) session
					.get("questionTypeAndEvalMap");
			Character questionType = questionTypeAndEvalMap
					.get(dailyStatusSearchInfo.getEvalSeq());

			int roleId = mstScorerInfo.getRoleId();
			dailyStatusSearchInfo.setLogedInScorerId(mstScorerInfo
					.getScorerId());
			if (dailyStatusSearchInfo.getDailyStatusSearchRadioButton().equals(
					WebAppConst.QUESTIONWISE_SEARCH)) {
				dailyStatusReportList = dailyStatusSearchService
						.getSearchListByQuestion(dailyStatusSearchInfo, roleId);
			} else {
				dailyStatusSearchScorerInfo = dailyStatusSearchService
						.getSearchListByScorer(dailyStatusSearchInfo);
			}
			session.put("questionType", questionType);
			setSessionAttributeForDispalyTag();
		} catch (SaitenRuntimeException we) {
			throw we;
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.DAILY_STATUS_SEARCH_REPORT_ERROR, e);
		}
		return SUCCESS;
	}

	private void setSessionAttributeForDispalyTag() {
		session.put("dailyStatusReportPageSize", Integer
				.parseInt(getText(WebAppConst.DAILY_STATUS_REPORT_PAGESIZE)));
	}

	public String dailyStatusQusetionDetails() {
		try {
			MstScorerInfo mstScorerInfo = (MstScorerInfo) session
					.get("scorerInfo");
			QuestionInfo sessionQuestionInfo = (QuestionInfo) session
					.get("questionInfo");
			questionInfo = dailyStatusSearchService
					.getQusetionInfoByQuestionSeq(questionSeq);
			Character questionType = questionInfo.getQuestionType();
			gradeWiseReportList = dailyStatusSearchService
					.getGradeWiseAnswerDetails(questionSeq,
							questionInfo.getConnectionString(), questionType);
			pendingCategoryWiseReportList = dailyStatusSearchService
					.getPendingCategoryWiseAnswerDetails(questionSeq,
							questionInfo.getConnectionString());
			log.info(mstScorerInfo.getScorerId()
					+ "-"
					+ sessionQuestionInfo.getMenuId()
					+ "-"
					+ "Loaded daily status detail report for selected question. \n Search Criteria: -{ Subject Code: "
					+ questionInfo.getSubjectCode() + ", Question No.: "
					+ questionInfo.getQuestionNum() + "}");
		} catch (SaitenRuntimeException we) {
			throw we;
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.DAILY_STATUS_QUESTION_WISE_REPORT_ACTION_EXCEPTION,
					e);
		}
		return SUCCESS;
	}

	private String getQuestionTypeDescriptionByQuestionTypeAndScoreType(
			Character questionType, Character scoreType) {
		String description = null;

		switch (scoreType) {
		case '1':
			switch (questionType) {
			case '1':
				description = getText(WebAppConst.QUESTION_TYPE_BATCH_SCORING);
				break;
			case '2':
				break;
			case '3':
				break;
			case '4':
				break;
			}
			break;
		case '2':
			switch (questionType) {
			case '1':
				description = getText(WebAppConst.QUESTION_TYPE_SHORT_SCREEN);
				break;
			case '2':
				description = getText(WebAppConst.QUESTION_TYPE_LONG_SCREEN);
				break;
			case '3':
				description = getText(WebAppConst.QUESTION_TYPE_SPEAKING);
				break;
			case '4':
				description = getText(WebAppConst.QUESTION_TYPE_WRITTING);
				break;
			}
			break;
		case '4':
			switch (questionType) {
			case '1':
				description = getText(WebAppConst.QUESTION_TYPE_SHORT_SCREEN_OBJECTIVE);
				break;
			case '2':
				description = getText(WebAppConst.QUESTION_TYPE_LONG_SCREEN_OBJECTIVE);
				break;
			case '3':
				break;
			case '4':
				break;
			}
			break;
		}

		return description;
	}

	/**
	 * 
	 * @param session
	 */
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	/**
	 * @param scoreSearchInfo
	 */
	public void setScoreSearchInfo(ScoreSearchInfo scoreSearchInfo) {
		this.scoreSearchInfo = scoreSearchInfo;
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

	/**
	 * @param scoreSearchService
	 *            the scoreSearchService to set
	 */
	public void setScoreSearchService(ScoreSearchService scoreSearchService) {
		this.scoreSearchService = scoreSearchService;
	}

	/**
	 * @return the dailyStatusSearchInfo
	 */
	public DailyStatusSearchInfo getDailyStatusSearchInfo() {
		return dailyStatusSearchInfo;
	}

	/**
	 * @param dailyStatusSearchInfo
	 *            the dailyStatusSearchInfo to set
	 */
	public void setDailyStatusSearchInfo(
			DailyStatusSearchInfo dailyStatusSearchInfo) {
		this.dailyStatusSearchInfo = dailyStatusSearchInfo;
	}

	/**
	 * @return the scoreSearchInfo
	 */
	public ScoreSearchInfo getScoreSearchInfo() {
		return scoreSearchInfo;
	}

	/**
	 * @return the rollMap
	 */
	public Map<Byte, String> getRollMap() {
		return rollMap;
	}

	/**
	 * @param rollMap
	 *            the rollMap to set
	 */
	public void setRollMap(Map<Byte, String> rollMap) {
		this.rollMap = rollMap;
	}

	/**
	 * @return the dailyStatusSearchService
	 */
	public DailyStatusSearchService getDailyStatusSearchService() {
		return dailyStatusSearchService;
	}

	/**
	 * @param dailyStatusSearchService
	 *            the dailyStatusSearchService to set
	 */
	public void setDailyStatusSearchService(
			DailyStatusSearchService dailyStatusSearchService) {
		this.dailyStatusSearchService = dailyStatusSearchService;
	}

	/**
	 * @return the saitenGlobalProperties
	 */
	public Properties getSaitenGlobalProperties() {
		return saitenGlobalProperties;
	}

	/**
	 * @param saitenGlobalProperties
	 *            the saitenGlobalProperties to set
	 */
	public void setSaitenGlobalProperties(Properties saitenGlobalProperties) {
		this.saitenGlobalProperties = saitenGlobalProperties;
	}

	/**
	 * @return the defaultSelectedRoll
	 */
	public Byte[] getDefaultSelectedRoll() {
		return defaultSelectedRoll;
	}

	/**
	 * @param defaultSelectedRoll
	 *            the defaultSelectedRoll to set
	 */
	public void setDefaultSelectedRoll(Byte[] defaultSelectedRoll) {
		this.defaultSelectedRoll = defaultSelectedRoll;
	}

	/**
	 * @return the loggedInScorerSubjectList
	 */
	public List<String> getLoggedInScorerSubjectList() {
		return loggedInScorerSubjectList;
	}

	/**
	 * @param loggedInScorerSubjectList
	 *            the loggedInScorerSubjectList to set
	 */
	public void setLoggedInScorerSubjectList(
			List<String> loggedInScorerSubjectList) {
		this.loggedInScorerSubjectList = loggedInScorerSubjectList;
	}

	/**
	 * @return the dailyStatusReportList
	 */
	public List<DailyStatusReportListInfo> getDailyStatusReportList() {
		return dailyStatusReportList;
	}

	/**
	 * @param dailyStatusReportList
	 *            the dailyStatusReportList to set
	 */
	public void setDailyStatusReportList(
			List<DailyStatusReportListInfo> dailyStatusReportList) {
		this.dailyStatusReportList = dailyStatusReportList;
	}

	/**
	 * @return the request
	 */
	public HttpServletRequest getRequest() {
		return request;
	}

	/**
	 * @param request
	 *            the request to set
	 */
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * @return the dailyStatusSearchScorerInfo
	 */
	public List<DailyStatusSearchScorerInfo> getDailyStatusSearchScorerInfo() {
		return dailyStatusSearchScorerInfo;
	}

	/**
	 * @param dailyStatusSearchScorerInfo
	 *            the dailyStatusSearchScorerInfo to set
	 */
	public void setDailyStatusSearchScorerInfo(
			List<DailyStatusSearchScorerInfo> dailyStatusSearchScorerInfo) {
		this.dailyStatusSearchScorerInfo = dailyStatusSearchScorerInfo;
	}

	public String getQuestionSeq() {
		return questionSeq;
	}

	public void setQuestionSeq(String questionSeq) {
		this.questionSeq = questionSeq;
	}

	public List<DailyStatusReportListInfo> getGradeWiseReportList() {
		return gradeWiseReportList;
	}

	public void setGradeWiseReportList(
			List<DailyStatusReportListInfo> gradeWiseReportList) {
		this.gradeWiseReportList = gradeWiseReportList;
	}

	public List<DailyStatusReportListInfo> getPendingCategoryWiseReportList() {
		return pendingCategoryWiseReportList;
	}

	public void setPendingCategoryWiseReportList(
			List<DailyStatusReportListInfo> pendingCategoryWiseReportList) {
		this.pendingCategoryWiseReportList = pendingCategoryWiseReportList;
	}

	public QuestionInfo getQuestionInfo() {
		return questionInfo;
	}

	public void setQuestionInfo(QuestionInfo questionInfo) {
		this.questionInfo = questionInfo;
	}

	/**
	 * @return the selectedMenuId
	 */
	public String getSelectedMenuId() {
		return selectedMenuId;
	}

	/**
	 * @param selectedMenuId
	 *            the selectedMenuId to set
	 */
	public void setSelectedMenuId(String selectedMenuId) {
		this.selectedMenuId = selectedMenuId;
	}

}
