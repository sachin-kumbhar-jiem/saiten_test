package com.saiten.action;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.web.context.ContextLoader;

import com.opensymphony.xwork2.ActionSupport;
import com.saiten.exception.SaitenRuntimeException;
import com.saiten.info.AcceptanceDisplayInfo;
import com.saiten.info.AnswerInfo;
import com.saiten.info.GradeInfo;
import com.saiten.info.KenshuRecordInfo;
import com.saiten.info.KenshuSamplingInfo;
import com.saiten.info.KenshuSamplingSearchRecordInfo;
import com.saiten.info.MstScorerInfo;
import com.saiten.info.QuestionInfo;
import com.saiten.info.ScoreSearchInfo;
import com.saiten.info.TranDescScoreInfo;
import com.saiten.model.TranAcceptance;
import com.saiten.model.TranDescScore;
import com.saiten.service.GradeSelectionService;
import com.saiten.service.KenshuSamplingService;
import com.saiten.service.QuestionSelectionService;
import com.saiten.service.ScoreSearchService;
import com.saiten.service.ScoreService;
import com.saiten.util.ErrorCode;
import com.saiten.util.SaitenUtil;
import com.saiten.util.WebAppConst;

public class KenshuSamplingAction extends ActionSupport implements
		SessionAware, ServletRequestAware, Serializable {

	private static Logger log = Logger.getLogger(KenshuSamplingAction.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, Object> session;
	private String selectedMenuId;
	private ScoreSearchService scoreSearchService;
	private ScoreSearchInfo scoreSearchInfo;
	private KenshuSamplingInfo kenshuSamplingInfo;
	private String subjectName;
	private QuestionSelectionService questionSelectionService;
	private KenshuSamplingService kenshuSamplingService;
	private GradeSelectionService gradeSelectionService;
	List<KenshuSamplingSearchRecordInfo> kenshuSamplingSearchRecordInfoList = new ArrayList<KenshuSamplingSearchRecordInfo>();
	private String slectedGrade;
	private String totalRecordCount;
	private Map<Integer, List<TranDescScoreInfo>> kenshuSamplingGradeWiaseMap = new HashMap<Integer, List<TranDescScoreInfo>>();
	private List<TranDescScoreInfo> tranDescScoreInfoList;
	private TranDescScoreInfo tranDescScoreInfo;
	private GradeInfo gradeInfo;
	private ScoreService scoreService;
	private Boolean prevOrNextFlag;
	private Map<String, String> searchCriteria;
	private AcceptanceDisplayInfo acceptanceDisplayInfo;
	private String kenshuSamplingSearch;
	private String acceptanceDisplayRadio;
	private Map<Integer, KenshuRecordInfo> kenshuRecordInfoMap;
	private KenshuRecordInfo kenshuRecordInfo;
	public static final String FAILURE = "failure";
	private List<TranAcceptance> tranAcceptanceList;
	private Map<Integer, TranAcceptance> tranAcceptanceMap;
	private TranAcceptance tranAcceptance;
	private MstScorerInfo scorerInfo;
	private String sessionClearFlag;

	public String onLoad() {
		scorerInfo = (MstScorerInfo) session.get("scorerInfo");
		log.info(scorerInfo.getScorerId() + "-" + selectedMenuId + "-"
				+ "Kenshu Sampling Screen loading.");
		try {
			if (sessionClearFlag.equals(Boolean.toString(WebAppConst.TRUE))) {
				clearSessionInfo();
			}

			scoreSearchInfo = (ScoreSearchInfo) ContextLoader
					.getCurrentWebApplicationContext().getBean(
							"scoreSearchInfo");
			Map<String, String> subjectNameList = scoreSearchService
					.findSubjectNameList();

			scoreSearchInfo.setSubjectNameList(subjectNameList);
			searchCriteria = new LinkedHashMap<String, String>();
			searchCriteria.put(getText(WebAppConst.KENSHU_SEARCH_ALL),
					getText(WebAppConst.KENSHU_SEARCH_ALL));
			searchCriteria.put(getText(WebAppConst.KENSHU_SEARCH_EXPLAINED),
					getText(WebAppConst.KENSHU_SEARCH_EXPLAINED));
			searchCriteria.put(getText(WebAppConst.KENSHU_SEARCH_UNEXPLAINED),
					getText(WebAppConst.KENSHU_SEARCH_UNEXPLAINED));

			acceptanceDisplayInfo = (AcceptanceDisplayInfo) session
					.get("acceptanceDisplayInfo");

			session.put("scoreSearchInfo", scoreSearchInfo);
			session.put("searchCriteria", searchCriteria);
		} catch (SaitenRuntimeException we) {
			throw we;
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.KENSHU_RECORD_SERVICE_EXCEPTION, e);
		}

		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String search() {

		try {

			if (kenshuSamplingSearch != null) {
				session.put("samplingSearch", kenshuSamplingSearch);
			}
			if (acceptanceDisplayRadio != null) {
				session.put("samplingSearch", acceptanceDisplayRadio);
			}
			String samplingSearch = (String) session.get("samplingSearch");
			if (kenshuSamplingSearch != null
					|| samplingSearch
							.equals(WebAppConst.KENSHU_SAMPLING_SEARCH)) {

				if (kenshuSamplingSearch != null) {
					session.put("samplingSearch", kenshuSamplingSearch);
				}

				if (kenshuSamplingInfo == null) {
					kenshuSamplingInfo = (KenshuSamplingInfo) session
							.get("kenshuSamplingInfo");
				}

				scoreSearchInfo = (ScoreSearchInfo) session
						.get("scoreSearchInfo");

				Set<String> keySet = scoreSearchInfo.getSubjectNameList()
						.keySet();
				for (String key : keySet) {
					if (key.contains(kenshuSamplingInfo.getSubjectCode())) {
						subjectName = scoreSearchInfo.getSubjectNameList().get(
								key);
					}
				}
				/*
				 * String subjectCode = "99-" +
				 * kenshuSamplingInfo.getSubjectCode(); subjectName =
				 * scoreSearchInfo.getSubjectNameList().get( subjectCode);
				 */

				session.remove("kenshuRecordInfo");

				QuestionInfo questionInfo = getQuestionInfo(
						kenshuSamplingInfo.getSubjectCode(),
						kenshuSamplingInfo.getQuestionNum());

				scorerInfo = (MstScorerInfo) session.get("scorerInfo");

				log.info(scorerInfo.getScorerId() + "-"
						+ questionInfo.getMenuId() + "-"
						+ "Search Criteria: -{ " + kenshuSamplingInfo + "}");

				if (kenshuSamplingSearch != null) {
					kenshuSamplingSearchRecordInfoList = kenshuSamplingService
							.getKenshuSamplingRecordsList(
									questionInfo.getQuestionSeq(),
									questionInfo.getConnectionString(),
									kenshuSamplingInfo.getResultCount());
					/*
					 * session.put("tranDescScoreInfoListMap",
					 * tranDescScoreInfoListMap); tranDescScoreInfoList =
					 * tranDescScoreInfoListMap;
					 */
				} else {
					/*
					 * List<TranDescScoreInfo> tranDescScoreInfoListMap =
					 * (List<TranDescScoreInfo>) session
					 * .get("tranDescScoreInfoListMap"); tranDescScoreInfoList =
					 * tranDescScoreInfoListMap;
					 */
					kenshuSamplingSearchRecordInfoList = (List<KenshuSamplingSearchRecordInfo>) session
							.get("kenshuSamplingSearchRecordInfoList");
				}

				// buildGradWaiseRecordList();

				// buildKenshuSamplingSearchList();
				session.put("kenshuSamplingInfo", kenshuSamplingInfo);
				session.put("kenshuSamplingSearchRecordInfoList",
						kenshuSamplingSearchRecordInfoList);
				session.put("questionInfo", questionInfo);
				/*
				 * session.put("kenshuSamplingGradeWiaseMap",
				 * kenshuSamplingGradeWiaseMap);
				 */

			} else if ((acceptanceDisplayRadio != null || samplingSearch
					.equals(WebAppConst.ACCEPTANCE_DISPLAY))) {
				if (acceptanceDisplayRadio != null) {
					session.put("samplingSearch", acceptanceDisplayRadio);
				}

				if (acceptanceDisplayInfo == null) {
					acceptanceDisplayInfo = (AcceptanceDisplayInfo) session
							.get("acceptanceDisplayInfo");
				}

				scoreSearchInfo = (ScoreSearchInfo) session
						.get("scoreSearchInfo");

				Set<String> keySet = scoreSearchInfo.getSubjectNameList()
						.keySet();
				for (String key : keySet) {
					if (key.contains(acceptanceDisplayInfo.getSubjectCode())) {
						subjectName = scoreSearchInfo.getSubjectNameList().get(
								key);
					}
				}
				/*
				 * String subjectCode = "99-" +
				 * acceptanceDisplayInfo.getSubjectCode(); subjectName =
				 * scoreSearchInfo.getSubjectNameList().get( subjectCode);
				 */

				QuestionInfo questionInfo = getQuestionInfo(
						acceptanceDisplayInfo.getSubjectCode(),
						acceptanceDisplayInfo.getQuestionNum());

				scorerInfo = (MstScorerInfo) session.get("scorerInfo");

				log.info(scorerInfo.getScorerId() + "-"
						+ questionInfo.getMenuId() + "-"
						+ "Search Criteria: -{ " + acceptanceDisplayInfo + "}");

				if (acceptanceDisplayRadio != null) {
					String searchCriteria = new String();
					if (acceptanceDisplayInfo.getRecordSearchCriteria().equals(
							getText(WebAppConst.KENSHU_SEARCH_EXPLAINED))) {
						searchCriteria = WebAppConst.KENSHU_SEARCH_EXPLAINED_STRING;
					} else if (acceptanceDisplayInfo
							.getRecordSearchCriteria()
							.equals(getText(WebAppConst.KENSHU_SEARCH_UNEXPLAINED))) {
						searchCriteria = WebAppConst.KENSHU_SEARCH_UNEXPLAINED_STRING;
					}
					tranAcceptanceList = kenshuSamplingService
							.getKenshuMarkeRecordsList(
									questionInfo.getQuestionSeq(),
									searchCriteria,
									questionInfo.getConnectionString(),
									acceptanceDisplayInfo.getKenshuUserId());
				} else {
					tranAcceptanceList = (List<TranAcceptance>) session
							.get("tranAcceptanceList");
				}

				createTrandescScoreInfoList(tranAcceptanceList);

				buildGradWaiseRecordList();
				if (acceptanceDisplayRadio != null) {
					buildKenshuSamplingSearchList();
				} else {
					kenshuSamplingSearchRecordInfoList = (List<KenshuSamplingSearchRecordInfo>) session
							.get("kenshuSamplingSearchRecordInfoList");
				}

				session.put("acceptanceDisplayInfo", acceptanceDisplayInfo);
				session.put("tranAcceptanceList", tranAcceptanceList);
				session.put("kenshuSamplingSearchRecordInfoList",
						kenshuSamplingSearchRecordInfoList);
				session.put("questionInfo", questionInfo);
				session.put("kenshuSamplingGradeWiaseMap",
						kenshuSamplingGradeWiaseMap);

			}
		} catch (SaitenRuntimeException we) {
			throw we;
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.KENSHU_RECORD_SERVICE_EXCEPTION, e);
		}

		return SUCCESS;
	}

	public void buildGradWaiseRecordList() {
		if (tranDescScoreInfoList != null) {
			for (TranDescScoreInfo tranDescScoreInfo : tranDescScoreInfoList) {
				List<TranDescScoreInfo> tranDescScoreInfoGradWiseList;

				tranDescScoreInfoGradWiseList = kenshuSamplingGradeWiaseMap
						.get(tranDescScoreInfo.getGradeNum());
				if (tranDescScoreInfoGradWiseList == null) {
					tranDescScoreInfoGradWiseList = new ArrayList<TranDescScoreInfo>();
					tranDescScoreInfoGradWiseList.add(tranDescScoreInfo);
					kenshuSamplingGradeWiaseMap.put(
							tranDescScoreInfo.getGradeNum(),
							tranDescScoreInfoGradWiseList);
				} else {
					tranDescScoreInfoGradWiseList.add(tranDescScoreInfo);
				}
			}
		}
	}

	public void buildKenshuSamplingSearchList() {
		Set<Integer> gradeList = new TreeSet<Integer>();
		gradeList = kenshuSamplingGradeWiaseMap.keySet();

		int totalRecords = tranDescScoreInfoList.size();
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		for (Integer grade : gradeList) {
			KenshuSamplingSearchRecordInfo kenshuSamplingInfo = new KenshuSamplingSearchRecordInfo();
			List<TranDescScoreInfo> tranDescScoreInfoGradWiseList = kenshuSamplingGradeWiaseMap
					.get(grade);
			int gradeWiasecount = tranDescScoreInfoGradWiseList.size();
			double ratio = ((double) gradeWiasecount / (double) totalRecords) * 100;

			kenshuSamplingInfo.setGradeNum(grade);
			kenshuSamplingInfo.setTotalNumber(gradeWiasecount);
			kenshuSamplingInfo.setRatio(Double.valueOf(df.format(ratio)));

			kenshuSamplingSearchRecordInfoList.add(kenshuSamplingInfo);
		}
	}

	@SuppressWarnings("unchecked")
	public String showKenhuRecords() {
		String result;
		try {
			QuestionInfo questionInfo = (QuestionInfo) session
					.get("questionInfo");

			session.remove("kenshuRecordInfo");

			kenshuSamplingGradeWiaseMap = (Map<Integer, List<TranDescScoreInfo>>) session
					.get("kenshuSamplingGradeWiaseMap");
			if (kenshuSamplingGradeWiaseMap != null
					&& !(kenshuSamplingGradeWiaseMap.isEmpty())) {
				tranDescScoreInfoList = kenshuSamplingGradeWiaseMap.get(Integer
						.parseInt(slectedGrade));
			} else {
				kenshuSamplingGradeWiaseMap = new HashMap<Integer, List<TranDescScoreInfo>>();
			}
			if (tranDescScoreInfoList == null) {

				tranDescScoreInfoList = kenshuSamplingService
						.getKenhuRecordsByGrade(questionInfo.getQuestionSeq(),
								questionInfo.getConnectionString(),
								Integer.parseInt(totalRecordCount),
								Integer.parseInt(slectedGrade));
				kenshuSamplingGradeWiaseMap.put(Integer.parseInt(slectedGrade),
						tranDescScoreInfoList);
				session.put("kenshuSamplingGradeWiaseMap",
						kenshuSamplingGradeWiaseMap);
			}

			session.put("tranDescScoreInfoList", tranDescScoreInfoList);
			session.put("slectedGrade", slectedGrade);

			scorerInfo = (MstScorerInfo) session.get("scorerInfo");

			log.info(scorerInfo.getScorerId() + "-" + questionInfo.getMenuId()
					+ "-" + "loading question of grade -" + slectedGrade);

			questionInfo.setPrevRecordCount(0);
			questionInfo.setNextRecordCount(tranDescScoreInfoList.size() - 1);

			// updatePrevNextRecordCounts(questionInfo);

			result = buildTranDescScoreInfo(questionInfo.getPrevRecordCount());

			getscoringData(questionInfo.getQuestionSeq());

			/*
			 * @SuppressWarnings("unchecked") List<TranDescScoreInfo>
			 * tranDescScoreInfoList = kenshuSamplingService
			 * .getKenshuRecord(slectedGrade, questionInfo.getQuestionSeq(),
			 * questionInfo.getConnectionString());
			 * 
			 * for (TranDescScoreInfo tranDescScoreInfo : tranDescScoreInfoList)
			 * { System.out.println(tranDescScoreInfo.getAnswerFormNumber());
			 * System.out
			 * .println(tranDescScoreInfo.getAnswerInfo().getAnswerSeq());
			 * System.out.println(tranDescScoreInfo.getAnswerInfo()
			 * .getLatestScreenScorerId()); }
			 */
		} catch (SaitenRuntimeException we) {
			throw we;
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.KENSHU_RECORD_SERVICE_EXCEPTION, e);
		}

		if (result.equals(SUCCESS)) {
			return SUCCESS;
		} else if (result.equals(FAILURE)) {
			return FAILURE;
		}
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String findPrevOrNextAnswer() {

		String result;
		try {
			session.remove("kenshuRecordInfo");

			QuestionInfo sessionQuestionInfo = (QuestionInfo) session
					.get("questionInfo");

			tranDescScoreInfoList = (List<TranDescScoreInfo>) session
					.get("tranDescScoreInfoList");

			updatePrevNextRecordCounts(sessionQuestionInfo);

			result = buildTranDescScoreInfo(sessionQuestionInfo
					.getPrevRecordCount());

			getscoringData(sessionQuestionInfo.getQuestionSeq());
		} catch (SaitenRuntimeException we) {
			throw we;
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.KENSHU_RECORD_SERVICE_EXCEPTION, e);
		}

		if (result.equals(SUCCESS)) {
			return SUCCESS;
		} else if (result.equals(FAILURE)) {
			return FAILURE;
		}
		return SUCCESS;
	}

	/**
	 * @param sessionQuestionInfo
	 */
	private void updatePrevNextRecordCounts(QuestionInfo sessionQuestionInfo) {
		if (prevOrNextFlag == WebAppConst.TRUE) {
			sessionQuestionInfo.setPrevRecordCount(sessionQuestionInfo
					.getPrevRecordCount() - 1);
			sessionQuestionInfo.setNextRecordCount(sessionQuestionInfo
					.getNextRecordCount() + 1);
		} else {
			sessionQuestionInfo.setPrevRecordCount(sessionQuestionInfo
					.getPrevRecordCount() + 1);
			sessionQuestionInfo.setNextRecordCount(sessionQuestionInfo
					.getNextRecordCount() - 1);
		}

		session.put("questionInfo", sessionQuestionInfo);
	}

	@SuppressWarnings("unchecked")
	private String buildTranDescScoreInfo(int prevRecordCount) {
		QuestionInfo questionInfo = (QuestionInfo) session.get("questionInfo");

		tranDescScoreInfoList = (List<TranDescScoreInfo>) session
				.get("tranDescScoreInfoList");

		kenshuSamplingSearchRecordInfoList = (List<KenshuSamplingSearchRecordInfo>) session
				.get("kenshuSamplingSearchRecordInfoList");

		int checkedRecordNum = updateChakedRecodCount();
		if (checkedRecordNum == tranDescScoreInfoList.size()) {
			checkedRecordNum--;
		}
		if (prevOrNextFlag != null && prevOrNextFlag == WebAppConst.TRUE) {
		} else {
			questionInfo.setPrevRecordCount(checkedRecordNum);
			questionInfo.setNextRecordCount(tranDescScoreInfoList.size()-(checkedRecordNum+1));
		}
		
		tranDescScoreInfo = tranDescScoreInfoList.get(questionInfo.getPrevRecordCount());

		scorerInfo = (MstScorerInfo) session.get("scorerInfo");

		log.info(scorerInfo.getScorerId() + "-" + questionInfo.getMenuId()
				+ "-" + "Loading answer -{" + tranDescScoreInfo + "}");

		String samplingSearch = (String) session.get("samplingSearch");
		if (samplingSearch.equals(WebAppConst.ACCEPTANCE_DISPLAY)) {
			tranAcceptanceMap = (Map<Integer, TranAcceptance>) session
					.get("tranAcceptanceMap");
			tranAcceptance = tranAcceptanceMap.get(tranDescScoreInfo
					.getAnswerInfo().getAnswerSeq());
			session.put("tranAcceptance", tranAcceptance);
		} else {
			scorerInfo = (MstScorerInfo) session.get("scorerInfo");
			boolean isAnswerChecked = kenshuSamplingService
					.isAnswerAlreadyChecked(tranDescScoreInfo,
							scorerInfo.getScorerId(),
							questionInfo.getConnectionString());
			if (isAnswerChecked) {
				kenshuSamplingService.updateKenshuRecord(tranDescScoreInfo,
						questionInfo.getConnectionString());
			} else {
				return FAILURE;
			}
			kenshuRecordInfoMap = (Map<Integer, KenshuRecordInfo>) session
					.get("kenshuRecordInfoMap");
			if (kenshuRecordInfoMap != null) {
				kenshuRecordInfo = kenshuRecordInfoMap.get(tranDescScoreInfo
						.getAnswerInfo().getAnswerSeq());
			}
			session.put("kenshuRecordInfo", kenshuRecordInfo);
		}
		session.put("tranDescScoreInfo", tranDescScoreInfo);
		return SUCCESS;
	}

	private void getscoringData(int questionSeq) {
		if (tranDescScoreInfo.getGradeSeq() != null) {
			// Get grade info. for scored answer
			gradeInfo = scoreService.buildGradeInfo(
					tranDescScoreInfo.getGradeSeq(), questionSeq);
		}

		Double bitValue = tranDescScoreInfo.getAnswerInfo().getBitValue();

		List<Short> selectedCheckPointList = SaitenUtil
				.getSelectedCheckPointList(bitValue);
		session.put("selectedCheckPointList", selectedCheckPointList);
	}

	private QuestionInfo getQuestionInfo(String subjectCode, short questionNum) {
		List<Integer> questionSeqList = scoreSearchService.findQuestionSeq(
				subjectCode, questionNum);
		Integer questionSeq = !questionSeqList.isEmpty() ? questionSeqList
				.get(0) : null;

		if (questionSeq == null) {

		}

		QuestionInfo questionInfo = questionSelectionService
				.fetchDbInstanceInfo(questionSeqList);
		questionInfo.setQuestionSeq(questionSeq);

		questionInfo.setMenuId(WebAppConst.KENSHU_SAMPLING_MENU_ID);

		questionInfo.setCheckPointList(questionSelectionService
				.findCheckPoints(questionSeq));

		questionInfo.setQuestionSeq(questionSeq);

		session.put("questionInfo", questionInfo);

		return questionInfo;
	}

	private List<TranDescScoreInfo> createTrandescScoreInfoList(
			List<TranAcceptance> recordList) {
		TranDescScoreInfo tranDescScoreInfoObj;

		if (recordList != null) {
			tranAcceptanceMap = new HashMap<Integer, TranAcceptance>();
			tranDescScoreInfoList = new ArrayList<TranDescScoreInfo>();
			for (TranAcceptance tranAcceptance : recordList) {
				tranAcceptanceMap.put(tranAcceptance.getTranDescScore()
						.getAnswerSeq(), tranAcceptance);
				tranDescScoreInfoObj = new TranDescScoreInfo();
				copy(tranDescScoreInfoObj, tranAcceptance.getTranDescScore());
				tranDescScoreInfoList.add(tranDescScoreInfoObj);
			}
		}
		session.put("tranAcceptanceMap", tranAcceptanceMap);
		return tranDescScoreInfoList;
	}

	private void copy(TranDescScoreInfo tranDescScoreInfoObj,
			TranDescScore tranDescScore) {
		AnswerInfo answerInfo = new AnswerInfo();

		answerInfo.setAnswerSeq(tranDescScore.getAnswerSeq());
		answerInfo.setBitValue(tranDescScore.getBitValue());
		answerInfo.setQuestionSeq(tranDescScore.getQuestionSeq());
		answerInfo.setLatestScreenScorerId(tranDescScore
				.getLatestScreenScorerId());

		if (tranDescScore.getSecondLatestScreenScorerId() != null) {
			answerInfo.setSecondLatestScreenScorerId(tranDescScore
					.getSecondLatestScreenScorerId());
		}

		tranDescScoreInfoObj.setAnswerFormNumber(tranDescScore
				.getAnswerFormNum());
		tranDescScoreInfoObj.setAnswerInfo(answerInfo);
		tranDescScoreInfoObj.setGradeNum(tranDescScore.getGradeNum());
		tranDescScoreInfoObj.setGradeSeq(tranDescScore.getGradeSeq());

		tranDescScoreInfoObj.setImageFileName(tranDescScore.getImageFileName());
		tranDescScoreInfoObj.setLatestScreenScorerId(tranDescScore
				.getLatestScreenScorerId());

		tranDescScoreInfoObj.setPendingCategory(tranDescScore
				.getPendingCategory());
	}

	@SuppressWarnings("unchecked")
	private int updateChakedRecodCount() {
		kenshuSamplingSearchRecordInfoList = (List<KenshuSamplingSearchRecordInfo>) session
				.get("kenshuSamplingSearchRecordInfoList");
		String slectedGrade = (String) session.get("slectedGrade");
		int number = 0;
		for (KenshuSamplingSearchRecordInfo recordObj : kenshuSamplingSearchRecordInfoList) {
			if (recordObj.getGradeNum() == Integer.parseInt(slectedGrade)) {
				number = recordObj.getCheckedRecordNumber();
				if (prevOrNextFlag != null
						&& prevOrNextFlag == WebAppConst.TRUE) {
				} else {
					if (recordObj.getTotalNumber() == number) {
						recordObj.setCheckedRecordNumber(number);
					}else {
						recordObj.setCheckedRecordNumber(number + 1);
					}
				}

				return number;
			}

		}
		return number;
	}

	private void clearSessionInfo() {
		session.remove("searchCriteria");
		session.remove("samplingSearch");
		session.remove("kenshuSamplingInfo");
		session.remove("kenshuSamplingSearchRecordInfoList");
		session.remove("questionInfo");
		session.remove("kenshuSamplingGradeWiaseMap");
		session.remove("acceptanceDisplayInfo");
		session.remove("tranAcceptanceList");
		session.remove("kenshuSamplingSearchRecordInfoList");
		session.remove("kenshuSamplingGradeWiaseMap");
		session.remove("tranAcceptance");
		session.remove("kenshuRecordInfo");
		session.remove("tranAcceptanceMap");
	}

	public String getSelectedMenuId() {
		return selectedMenuId;
	}

	public void setSelectedMenuId(String selectedMenuId) {
		this.selectedMenuId = selectedMenuId;
	}

	public ScoreSearchService getScoreSearchService() {
		return scoreSearchService;
	}

	public void setScoreSearchService(ScoreSearchService scoreSearchService) {
		this.scoreSearchService = scoreSearchService;
	}

	public ScoreSearchInfo getScoreSearchInfo() {
		return scoreSearchInfo;
	}

	public void setScoreSearchInfo(ScoreSearchInfo scoreSearchInfo) {
		this.scoreSearchInfo = scoreSearchInfo;
	}

	public KenshuSamplingInfo getKenshuSamplingInfo() {
		return kenshuSamplingInfo;
	}

	public void setKenshuSamplingInfo(KenshuSamplingInfo kenshuSamplingInfo) {
		this.kenshuSamplingInfo = kenshuSamplingInfo;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public List<KenshuSamplingSearchRecordInfo> getKenshuSamplingSearchRecordInfoList() {
		return kenshuSamplingSearchRecordInfoList;
	}

	public void setKenshuSamplingSearchRecordInfoList(
			List<KenshuSamplingSearchRecordInfo> kenshuSamplingSearchRecordInfoList) {
		this.kenshuSamplingSearchRecordInfoList = kenshuSamplingSearchRecordInfoList;
	}

	public QuestionSelectionService getQuestionSelectionService() {
		return questionSelectionService;
	}

	public void setQuestionSelectionService(
			QuestionSelectionService questionSelectionService) {
		this.questionSelectionService = questionSelectionService;
	}

	public KenshuSamplingService getKenshuSamplingService() {
		return kenshuSamplingService;
	}

	public void setKenshuSamplingService(
			KenshuSamplingService kenshuSamplingService) {
		this.kenshuSamplingService = kenshuSamplingService;
	}

	public GradeSelectionService getGradeSelectionService() {
		return gradeSelectionService;
	}

	public void setGradeSelectionService(
			GradeSelectionService gradeSelectionService) {
		this.gradeSelectionService = gradeSelectionService;
	}

	public Map<Integer, List<TranDescScoreInfo>> getKenshuSamplingGradeWiaseMap() {
		return kenshuSamplingGradeWiaseMap;
	}

	public void setKenshuSamplingGradeWiaseMap(
			Map<Integer, List<TranDescScoreInfo>> kenshuSamplingGradeWiaseMap) {
		this.kenshuSamplingGradeWiaseMap = kenshuSamplingGradeWiaseMap;
	}

	public String getSlectedGrade() {
		return slectedGrade;
	}

	public void setSlectedGrade(String slectedGrade) {
		this.slectedGrade = slectedGrade;
	}

	public String getTotalRecordCount() {
		return totalRecordCount;
	}

	public void setTotalRecordCount(String totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}

	public TranDescScoreInfo getTranDescScoreInfo() {
		return tranDescScoreInfo;
	}

	public void setTranDescScoreInfo(TranDescScoreInfo tranDescScoreInfo) {
		this.tranDescScoreInfo = tranDescScoreInfo;
	}

	public List<TranDescScoreInfo> getTranDescScoreInfoList() {
		return tranDescScoreInfoList;
	}

	public void setTranDescScoreInfoList(
			List<TranDescScoreInfo> tranDescScoreInfoList) {
		this.tranDescScoreInfoList = tranDescScoreInfoList;
	}

	public GradeInfo getGradeInfo() {
		return gradeInfo;
	}

	public void setGradeInfo(GradeInfo gradeInfo) {
		this.gradeInfo = gradeInfo;
	}

	public ScoreService getScoreService() {
		return scoreService;
	}

	public void setScoreService(ScoreService scoreService) {
		this.scoreService = scoreService;
	}

	public Boolean getPrevOrNextFlag() {
		return prevOrNextFlag;
	}

	public void setPrevOrNextFlag(Boolean prevOrNextFlag) {
		this.prevOrNextFlag = prevOrNextFlag;
	}

	public Map<String, String> getSearchCriteria() {
		return searchCriteria;
	}

	public void setSearchCriteria(Map<String, String> searchCriteria) {
		this.searchCriteria = searchCriteria;
	}

	public AcceptanceDisplayInfo getAcceptanceDisplayInfo() {
		return acceptanceDisplayInfo;
	}

	public void setAcceptanceDisplayInfo(
			AcceptanceDisplayInfo acceptanceDisplayInfo) {
		this.acceptanceDisplayInfo = acceptanceDisplayInfo;
	}

	public String getKenshuSamplingSearch() {
		return kenshuSamplingSearch;
	}

	public void setKenshuSamplingSearch(String kenshuSamplingSearch) {
		this.kenshuSamplingSearch = kenshuSamplingSearch;
	}

	public String getAcceptanceDisplayRadio() {
		return acceptanceDisplayRadio;
	}

	public void setAcceptanceDisplayRadio(String acceptanceDisplayRadio) {
		this.acceptanceDisplayRadio = acceptanceDisplayRadio;
	}

	public Map<Integer, KenshuRecordInfo> getKenshuRecordInfoMap() {
		return kenshuRecordInfoMap;
	}

	public void setKenshuRecordInfoMap(
			Map<Integer, KenshuRecordInfo> kenshuRecordInfoMap) {
		this.kenshuRecordInfoMap = kenshuRecordInfoMap;
	}

	public KenshuRecordInfo getKenshuRecordInfo() {
		return kenshuRecordInfo;
	}

	public void setKenshuRecordInfo(KenshuRecordInfo kenshuRecordInfo) {
		this.kenshuRecordInfo = kenshuRecordInfo;
	}

	public List<TranAcceptance> getTranAcceptanceList() {
		return tranAcceptanceList;
	}

	public void setTranAcceptanceList(List<TranAcceptance> tranAcceptanceList) {
		this.tranAcceptanceList = tranAcceptanceList;
	}

	public Map<Integer, TranAcceptance> getTranAcceptanceMap() {
		return tranAcceptanceMap;
	}

	public void setTranAcceptanceMap(
			Map<Integer, TranAcceptance> tranAcceptanceMap) {
		this.tranAcceptanceMap = tranAcceptanceMap;
	}

	public TranAcceptance getTranAcceptance() {
		return tranAcceptance;
	}

	public void setTranAcceptance(TranAcceptance tranAcceptance) {
		this.tranAcceptance = tranAcceptance;
	}

	public MstScorerInfo getScorerInfo() {
		return scorerInfo;
	}

	public void setScorerInfo(MstScorerInfo scorerInfo) {
		this.scorerInfo = scorerInfo;
	}

	public String getSessionClearFlag() {
		return sessionClearFlag;
	}

	public void setSessionClearFlag(String sessionClearFlag) {
		this.sessionClearFlag = sessionClearFlag;
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		// TODO Auto-generated method stub

	}

	public Map<String, Object> getSession() {
		return session;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;

	}

}
