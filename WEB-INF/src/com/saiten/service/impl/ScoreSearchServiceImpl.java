package com.saiten.service.impl;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.HibernateException;

import com.saiten.bean.SaitenConfig;
import com.saiten.dao.MstDenyCategoryDAO;
import com.saiten.dao.MstGradeDAO;
import com.saiten.dao.MstPendingCategoryDAO;
import com.saiten.dao.MstQuestionDAO;
import com.saiten.dao.MstSamplingEventCondDAO;
import com.saiten.dao.MstSamplingStateCondDAO;
import com.saiten.dao.MstSubjectDAO;
import com.saiten.dao.TranDescScoreDAO;
import com.saiten.dao.TranDescScoreHistoryDAO;
import com.saiten.exception.SaitenRuntimeException;
import com.saiten.info.AnswerInfo;
import com.saiten.info.QuestionInfo;
import com.saiten.info.ScoreCurrentInfo;
import com.saiten.info.ScoreHistoryInfo;
import com.saiten.info.ScoreInputInfo;
import com.saiten.info.ScoreSamplingInfo;
import com.saiten.info.TranDescScoreInfo;
import com.saiten.model.MstQuestion;
import com.saiten.service.ConfirmScoreService;
import com.saiten.service.ScoreSearchService;
import com.saiten.util.ErrorCode;
import com.saiten.util.SaitenUtil;
import com.saiten.util.WebAppConst;

/**
 * @author sachin
 * @version 1.0
 * @created 13-Dec-2012 11:53:29 AM
 */
public class ScoreSearchServiceImpl implements ScoreSearchService {

	private MstSubjectDAO mstSubjectDAO;
	private MstSamplingEventCondDAO mstSamplingEventCondDAO;
	private MstSamplingStateCondDAO mstSamplingStateCondDAO;
	private TranDescScoreHistoryDAO tranDescScoreHistoryDAO;
	private MstQuestionDAO mstQuestionDAO;
	private MstGradeDAO mstGradeDAO;
	private MstPendingCategoryDAO mstPendingCategoryDAO;
	private ConfirmScoreService confirmScoreService;
	private TranDescScoreDAO tranDescScoreDAO;
	private MstDenyCategoryDAO mstDenyCategoryDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.saiten.service.ScoreSearchService#findQuestionSeq(java.lang.Character
	 * , java.lang.Short)
	 */
	@Override
	public List<Integer> findQuestionSeq(String subjectCode, Short questionNum) {
		try {
			return getMstQuestionDAO()
					.findQuestionSeq(subjectCode, questionNum);
		} catch (HibernateException he) {
			throw new SaitenRuntimeException(
					ErrorCode.SCORE_SEARCH_HIBERNATE_EXCEPTION, he);
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.SCORE_SEARCH_SERVICE_EXCEPTION, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.saiten.service.ScoreSearchService#findGradeSeqList(java.lang.Integer,
	 * java.lang.Integer[])
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List findGradeSeqList(Integer questionSeq, Integer[] gradeNum) {
		try {
			return getMstGradeDAO().findGradeSeqList(questionSeq, gradeNum);
		} catch (HibernateException he) {
			throw new SaitenRuntimeException(
					ErrorCode.SCORE_SEARCH_HIBERNATE_EXCEPTION, he);
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.SCORE_SEARCH_SERVICE_EXCEPTION, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.saiten.service.ScoreSearchService#findPendingCategorySeqList(java
	 * .lang.Integer, java.lang.Short[])
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List findPendingCategorySeqList(Integer questionSeq,
			Short[] pendingCategoryList) {
		try {
			return getMstPendingCategoryDAO().findPendingCategorySeqList(
					questionSeq, pendingCategoryList);
		} catch (HibernateException he) {
			throw new SaitenRuntimeException(
					ErrorCode.SCORE_SEARCH_HIBERNATE_EXCEPTION, he);
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.SCORE_SEARCH_SERVICE_EXCEPTION, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saiten.service.ScoreSearchService#findSubjectNameList()
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Map<String, String> findSubjectNameList() {
		try {
			List subjectNameList = getMstSubjectDAO().findSubjectNameList();
			return buildSubjectNameMap(subjectNameList);
		} catch (HibernateException he) {
			throw new SaitenRuntimeException(
					ErrorCode.SCORE_SEARCH_HIBERNATE_EXCEPTION, he);
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.SCORE_SEARCH_SERVICE_EXCEPTION, e);
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<TranDescScoreInfo> findSpecialScoringAnswerRecords(
			List<Integer> quetionSeqList, String menuId, String scorerId,
			String connectionString, String answerFormNum,
			Integer historyRecordCount, int roleId) {
		// Get menuIdAndScoringStateMap from saitenConfigObject
		LinkedHashMap<String, Short> menuIdAndScoringStateMap = ((SaitenConfig) ServletActionContext
				.getServletContext().getAttribute("saitenConfigObject"))
				.getMenuIdAndScoringStateMap();

		// Fetch answer corresponding to selected question
		/*
		 * List<Integer> gradeSeqList = null; Integer pendingCategorySeq = null;
		 * List answerRecordList = null; if
		 * (!(menuId.equals(WebAppConst.SPECIAL_SCORING_ENLARGE_TYPE_MENU_ID) ||
		 * menuId .equals(WebAppConst.SPECIAL_SCORING_OMR_READ_FAIL_MENU_ID))) {
		 * int randomNumberRange = Integer.parseInt(saitenGlobalProperties
		 * .getProperty(WebAppConst.RANDOM_NUMBER)); answerRecordList =
		 * tranDescScoreDAO.findAnswer(quetionSeqList, menuId, scorerId,
		 * menuIdAndScoringStateMap, connectionString, gradeSeqList,
		 * pendingCategorySeq, answerFormNum, historyRecordCount,
		 * randomNumberRange); } else { answerRecordList =
		 * tranDescScoreDAO.findAnswer(quetionSeqList, menuId, scorerId,
		 * menuIdAndScoringStateMap, connectionString, answerFormNum,
		 * historyRecordCount); }
		 */

		List answerRecordList = tranDescScoreDAO.findAnswer(quetionSeqList,
				menuId, scorerId, menuIdAndScoringStateMap, connectionString,
				answerFormNum, historyRecordCount, roleId);
		return buildSpecialTranDescScoreInfoList(answerRecordList, scorerId);
	}

	@SuppressWarnings("rawtypes")
	private List<TranDescScoreInfo> buildSpecialTranDescScoreInfoList(
			List answerRecordList, String scorerId) {
		List<TranDescScoreInfo> tranDescScoreInfoList = null;
		if (!answerRecordList.isEmpty()) {
			tranDescScoreInfoList = new ArrayList<TranDescScoreInfo>();
			for (Object answerRecord : answerRecordList) {
				Object[] answerRecordObjArray = (Object[]) answerRecord;

				AnswerInfo answerInfo = new AnswerInfo();
				TranDescScoreInfo tranDescScoreInfo = new TranDescScoreInfo();
				tranDescScoreInfo.setAnswerInfo(answerInfo);

				answerInfo.setAnswerSeq(Integer.valueOf(answerRecordObjArray[0]
						.toString()));
				tranDescScoreInfo
						.setAnswerFormNumber((String) answerRecordObjArray[1]);
				tranDescScoreInfo
						.setImageFileName((String) answerRecordObjArray[2]);
				tranDescScoreInfo
						.setGradeSeq((Integer) answerRecordObjArray[3]);
				answerInfo.setBitValue((Double) answerRecordObjArray[4]);
				answerInfo.setQuestionSeq((Integer) answerRecordObjArray[5]);
				answerInfo.setUpdateDate((Date) answerRecordObjArray[6]);
				String markValues = (String) answerRecordObjArray[7];
				if (markValues != null && markValues.length() > 0) {
					String[] markValueArray = markValues.split(",");
					Short[] intMarkValues = new Short[markValueArray.length];
					for (int i = 0; i < markValueArray.length; i++) {
						intMarkValues[i] = Short.parseShort(markValueArray[i]);
					}
					List<Short> markValueList = Arrays.asList(intMarkValues);
					tranDescScoreInfo.setMarkValueList(markValueList);
				}
				if(answerRecordObjArray[10] != null){
					answerInfo.setPunchText((String) answerRecordObjArray[10]);
				}
				
				// Set lockBy for all answer records
				answerInfo.setLockBy(scorerId);

				tranDescScoreInfoList.add(tranDescScoreInfo);
			}
		}

		return tranDescScoreInfoList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.saiten.service.ScoreSearchService#searchAnswerRecords(com.saiten.
	 * info.QuestionInfo, com.saiten.info.ScoreInputInfo)
	 */
	@SuppressWarnings({ "rawtypes" })
	@Override
	public List searchAnswerRecords(QuestionInfo questionInfo,
			ScoreInputInfo scoreInputInfo, String scorerId,
			Boolean forceAndStateTransitionFlag, Integer startRecord,
			Integer endRecord) {
		ScoreHistoryInfo scoreHistoryInfo = scoreInputInfo
				.getScoreHistoryInfo();
		ScoreCurrentInfo scoreCurrentInfo = scoreInputInfo
				.getScoreCurrentInfo();

		try {
			// Build history search criteria info
			buildHistorySearchCriteriaInfo(scoreHistoryInfo);

			// Build current search criteria info
			buildCurrentSearchCriteriaInfo(scoreCurrentInfo);

			// Search records
			String orderByRandAttempt = new String();
			String menuId = questionInfo.getMenuId();
			List searchResultList = null;
			
			if (menuId.equals(WebAppConst.REFERENCE_SAMP_MENU_ID) || menuId.equals(WebAppConst.STATE_TRAN_MENU_ID)) {
				Integer scoreStartRange = scoreInputInfo.getScoreStartRange();
				Integer scoreEndRange = scoreInputInfo.getScoreEndRange();
				if (!(scoreInputInfo.getQuestionType().equals(Integer.valueOf(WebAppConst.MINUS_ONE)))) {
					scoreStartRange = scoreStartRange == null && scoreEndRange != null ? WebAppConst.SCORE_RANGE_START
							: scoreStartRange;
					scoreEndRange = scoreEndRange == null && scoreStartRange != null ? WebAppConst.SCORE_RANGE_END
							: scoreEndRange;
					scoreInputInfo.setScoreStartRange(scoreStartRange);
					scoreInputInfo.setScoreEndRange(scoreEndRange);
				}
			}
			
			if (menuId.equals(WebAppConst.REFERENCE_SAMP_MENU_ID)
					|| menuId.equals(WebAppConst.SCORE_SAMP_MENU_ID)) {
					searchResultList = getTranDescScoreHistoryDAO()
							.searchAnswerRecords(questionInfo, scoreInputInfo,
									forceAndStateTransitionFlag, startRecord,
									endRecord, orderByRandAttempt);
			} else if ((forceAndStateTransitionFlag != null && forceAndStateTransitionFlag == WebAppConst.TRUE)) {
				searchResultList = getTranDescScoreHistoryDAO()
						.searchAnswerRecords(questionInfo, scoreInputInfo,
								forceAndStateTransitionFlag, startRecord,
								endRecord);
			} else {
				searchResultList = getTranDescScoreHistoryDAO()
						.searchAnswerRecords(questionInfo, scoreInputInfo,
								forceAndStateTransitionFlag, startRecord,
								endRecord, orderByRandAttempt);
			}

			// Build answerRecordsList
			if (forceAndStateTransitionFlag == null) {
				return buildTranDescScoreInfoList(searchResultList, scorerId,
						menuId, questionInfo);
			} else if ((forceAndStateTransitionFlag != null)
					&& (forceAndStateTransitionFlag == WebAppConst.TRUE)) {

				return buildScoreSamplingInfoList(searchResultList, scorerId,
						menuId, questionInfo);
			} else {
				return searchResultList;
			}
		} catch (HibernateException he) {
			throw new SaitenRuntimeException(
					ErrorCode.SCORE_SEARCH_HIBERNATE_EXCEPTION, he);
		} catch (SaitenRuntimeException we) {
			throw we;
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.SCORE_SEARCH_SERVICE_EXCEPTION, e);
		}
	}

	/**
	 * @param answerRecordsList
	 * @return List<TranDescScoreInfo>
	 * @throws IOException 
	 */
	@SuppressWarnings("rawtypes")
	private List<TranDescScoreInfo> buildTranDescScoreInfoList(
			List answerRecordsList, String scorerId, String menuId, QuestionInfo questionInfo) throws IOException {
		List<TranDescScoreInfo> tranDescScoreInfoList = null;
		if (!answerRecordsList.isEmpty()) {
			tranDescScoreInfoList = new ArrayList<TranDescScoreInfo>();
			for (Object answerRecord : answerRecordsList) {
				AnswerInfo answerInfo = new AnswerInfo();

				TranDescScoreInfo tranDescScoreInfo = new TranDescScoreInfo();
				tranDescScoreInfo.setAnswerInfo(answerInfo);

				Object[] answerRecordObj = (Object[]) answerRecord;

				answerInfo.setLockBy(scorerId);
				answerInfo.setAnswerSeq(Integer.valueOf(answerRecordObj[0]
						.toString()));
				if (!menuId.equals(WebAppConst.REFERENCE_SAMP_MENU_ID)) {
					String markValues = (String) answerRecordObj[11];
					if (markValues != null && markValues.length() > 0) {
						String[] markValueArray = markValues.split(",");
						Short[] intMarkValues = new Short[markValueArray.length];
						for (int i = 0; i < markValueArray.length; i++) {
							intMarkValues[i] = Short
									.parseShort(markValueArray[i]);
						}
						List<Short> markValueList = Arrays
								.asList(intMarkValues);
						tranDescScoreInfo.setMarkValueList(markValueList);
					}
					answerInfo.setUpdateDate((Date) answerRecordObj[12]);
					if (menuId.equals(WebAppConst.FORCED_MENU_ID)) {
						if (answerRecordObj[13] != null) {
							answerInfo
									.setLookAftSeq((Integer) answerRecordObj[13]);
						}
					}
					if(menuId.equals(WebAppConst.SCORE_SAMP_MENU_ID)){
						if (answerRecordObj[13] != null) {
							answerInfo.setLatestScreenScorerId((String) answerRecordObj[13]);
						}
						if (answerRecordObj[14] != null) {
							answerInfo.setSecondLatestScreenScorerId((String) answerRecordObj[14]);
						}
					}
				} else {
					tranDescScoreInfo
							.setLatestScreenScorerId((String) answerRecordObj[12]);
					String markValues = (String) answerRecordObj[13];
					if (markValues != null && markValues.length() > 0) {
						String[] markValueArray = markValues.split(",");
						Short[] intMarkValues = new Short[markValueArray.length];
						for (int i = 0; i < markValueArray.length; i++) {
							intMarkValues[i] = Short
									.parseShort(markValueArray[i]);
						}
						List<Short> markValueList = Arrays
								.asList(intMarkValues);
						tranDescScoreInfo.setMarkValueList(markValueList);
					}
					answerInfo.setUpdateDate((Date) answerRecordObj[14]);
					if (menuId.equals(WebAppConst.FORCED_MENU_ID)) {
						if (answerRecordObj[15] != null) {
							answerInfo
									.setLookAftSeq((Integer) answerRecordObj[15]);
						}
					}
				}

				tranDescScoreInfo
						.setAnswerFormNumber((String) answerRecordObj[1]);
				if (questionInfo.getQuestionType() == WebAppConst.WRITING_TYPE) {
					String imageFileName = (String) answerRecordObj[2];
					tranDescScoreInfo.setImageFileName(SaitenUtil
							.getAnswerDataText(imageFileName, questionInfo));
				} else {
					tranDescScoreInfo
							.setImageFileName((String) answerRecordObj[2]);
				}

				if (answerRecordObj[3] != null) {
					tranDescScoreInfo.setGradeSeq(Integer
							.valueOf(answerRecordObj[3].toString()));
				}

				if (answerRecordObj[4] != null) {
					answerInfo.setBitValue(Double.valueOf(answerRecordObj[4]
							.toString()));
				}

				answerInfo.setLatestScorerId((String) answerRecordObj[5]);

				if (answerRecordObj[6] != null) {
					answerInfo.setScorerComment((String) answerRecordObj[6]);
				} else {
					answerInfo.setScorerComment(StringUtils.EMPTY);
				}
				answerInfo.setPunchText((String) answerRecordObj[7]);

				if (answerRecordObj[8] != null) {
					answerInfo.setPendingCategorySeq(Integer
							.valueOf(answerRecordObj[8].toString()));
				}
				
				if (answerRecordObj[10] != null) {
					answerInfo.setDenyCategorySeq(Integer
							.valueOf(answerRecordObj[10].toString()));
				}

				tranDescScoreInfo.setScoringState(Short
						.valueOf(answerRecordObj[9].toString()));

				tranDescScoreInfoList.add(tranDescScoreInfo);
			}

		}

		return tranDescScoreInfoList;
	}

	@SuppressWarnings("rawtypes")
	private List<ScoreSamplingInfo> buildScoreSamplingInfoList(
			List searchResultList, String scorerId, String menuId,
			QuestionInfo questionInfo) throws IOException {
		List<ScoreSamplingInfo> scoreSamplingInfoList = null;
		List<Integer> answerSeqList = new ArrayList<Integer>();
		if (!searchResultList.isEmpty()) {
			scoreSamplingInfoList = new ArrayList<ScoreSamplingInfo>();
			for (Object scoreSampling : searchResultList) {
				AnswerInfo answerInfo = new AnswerInfo();

				ScoreSamplingInfo scoreSamplingInfo = new ScoreSamplingInfo();
				scoreSamplingInfo.setAnswerInfo(answerInfo);

				Object[] scoreSamplingInfoObj = (Object[]) scoreSampling;

				answerInfo.setLockBy(scorerId);
				int answerSeq = Integer.valueOf(scoreSamplingInfoObj[0]
						.toString());
				answerSeqList.add(answerSeq);
				answerInfo.setAnswerSeq(answerSeq);

				scoreSamplingInfo
						.setAnswerSeq((Integer) scoreSamplingInfoObj[0]);
				scoreSamplingInfo
						.setAnswerNumber((String) scoreSamplingInfoObj[1]);
				if (questionInfo.getQuestionType() == WebAppConst.WRITING_TYPE) {
					String imageFileName = (String) scoreSamplingInfoObj[2];
					scoreSamplingInfo.setImageFileName(SaitenUtil
							.getAnswerDataText(imageFileName, questionInfo));
				} else {
					scoreSamplingInfo
							.setImageFileName((String) scoreSamplingInfoObj[2]);
				}

				Integer gradeNum = null;
				int questionSeq = (Integer) scoreSamplingInfoObj[10];
				scoreSamplingInfo.setSubjectName(SaitenUtil
						.getSubjectNameByQuestionSequence(questionSeq));

				Integer gradeSeq = (Integer) scoreSamplingInfoObj[3];
				if (gradeSeq != null) {
					scoreSamplingInfo.setGradeSeq(gradeSeq);
					gradeNum = SaitenUtil.getGradeNumByGradeSequence(gradeSeq,
							questionSeq);
					scoreSamplingInfo.setGradeNum(gradeNum);

					scoreSamplingInfo.setResult(SaitenUtil
							.getResultByGradeSequence(gradeSeq, questionSeq));
				}

				Double bitValue = (Double) scoreSamplingInfoObj[4];
				if (bitValue != null) {
					answerInfo.setBitValue(bitValue);
					List<Short> checkPointList = SaitenUtil
							.getSelectedCheckPointList(bitValue);
					checkPointList.toArray().toString();

					String checkPointString = checkPointList.toString();

					scoreSamplingInfo.setCheckPoints(checkPointString
							.substring(1, checkPointString.length() - 1));
				}

				answerInfo.setLatestScorerId((String) scoreSamplingInfoObj[5]);

				answerInfo.setPunchText((String) scoreSamplingInfoObj[6]);

				if (scoreSamplingInfoObj[7] != null) {
					answerInfo.setPendingCategorySeq(Integer
							.valueOf(scoreSamplingInfoObj[7].toString()));
					scoreSamplingInfo
							.setPendingCategory(SaitenUtil
									.getPendingCategoryByPendingCategorySeq((Integer) scoreSamplingInfoObj[7]));
				}
				if (scoreSamplingInfoObj[9] != null) {
					answerInfo.setDenyCategorySeq(Integer
							.valueOf(scoreSamplingInfoObj[9].toString()));
					scoreSamplingInfo
							.setDenyCategory(SaitenUtil
									.getDenyCategoryByDenyCategorySeq((Integer) scoreSamplingInfoObj[9]));
				}

				scoreSamplingInfo.setScoringState(Short
						.valueOf(scoreSamplingInfoObj[8].toString()));

				scoreSamplingInfo
						.setLatestScreenScorerId((String) scoreSamplingInfoObj[11]);

				if (menuId.equals(WebAppConst.FORCED_MENU_ID)) {
					scoreSamplingInfo
							.setCommentCount(((BigInteger) scoreSamplingInfoObj[12])
									.longValue());
					String markValues = (String) scoreSamplingInfoObj[13];
					if (markValues != null && markValues.length() > 0) {
						String[] markValueArray = markValues.split(",");
						Short[] intMarkValues = new Short[markValueArray.length];
						for (int i = 0; i < markValueArray.length; i++) {
							intMarkValues[i] = Short
									.parseShort(markValueArray[i]);
						}
						List<Short> markValueList = Arrays
								.asList(intMarkValues);
						scoreSamplingInfo.setMarkValueList(markValueList);
					}

					scoreSamplingInfo
							.setUpdateDate((Date) scoreSamplingInfoObj[14]);
					answerInfo.setUpdateDate((Date) scoreSamplingInfoObj[14]);
					if (scoreSamplingInfoObj[15] != null) {
						answerInfo
								.setQualityCheckFlag((Character) scoreSamplingInfoObj[15]);
					}

					if (menuId.equals(WebAppConst.FORCED_MENU_ID)) {
						if (scoreSamplingInfoObj[16] != null) {
							answerInfo
									.setLookAftSeq((Integer) scoreSamplingInfoObj[16]);
						}
						if(scoreSamplingInfoObj[17] != null){
							answerInfo.setLatestScreenScorerId((String) scoreSamplingInfoObj[17]);
						}
						if(scoreSamplingInfoObj[18] != null){
							answerInfo.setSecondLatestScreenScorerId((String) scoreSamplingInfoObj[18]);
						}

					}

				} else if (menuId.equals(WebAppConst.STATE_TRAN_MENU_ID)) {
					String markValues = (String) scoreSamplingInfoObj[12];
					if (markValues != null && markValues.length() > 0) {
						String[] markValueArray = markValues.split(",");
						Short[] intMarkValues = new Short[markValueArray.length];
						for (int i = 0; i < markValueArray.length; i++) {
							intMarkValues[i] = Short
									.parseShort(markValueArray[i]);
						}
						List<Short> markValueList = Arrays
								.asList(intMarkValues);
						scoreSamplingInfo.setMarkValueList(markValueList);
					}
					scoreSamplingInfo
							.setUpdateDate((Date) scoreSamplingInfoObj[13]);
				}

				scoreSamplingInfo
						.setScoringStateName(SaitenUtil
								.getStateNameByScoringState((Short) scoreSamplingInfoObj[8]));
				scoreSamplingInfo.setQuestionNumber(SaitenUtil
						.getQuestionNumByQuestionSequence(questionSeq));

				scoreSamplingInfoList.add(scoreSamplingInfo);

			}
			if (menuId.equals(WebAppConst.FORCED_MENU_ID)) {
				/*
				 * System.out
				 * .println(">>>>>>>>>> AnswerSeqCommentList query start: " +
				 * new Date().getTime());
				 */
				List AnswerSeqCommentList = getTranDescScoreHistoryDAO()
						.getCommentsByAnswerSequences(answerSeqList,
								questionInfo);
				/*
				 * System.out
				 * .println(">>>>>>>>>> AnswerSeqCommentList query end: " + new
				 * Date().getTime());
				 */
				if (!AnswerSeqCommentList.isEmpty()) {
					for (Object answerSeqComment : AnswerSeqCommentList) {

						Object[] answerSeqCommentObj = (Object[]) answerSeqComment;
						Integer answerSequence = (Integer) answerSeqCommentObj[0];
						ScoreSamplingInfo tempScoreSamplingInfo = new ScoreSamplingInfo();
						tempScoreSamplingInfo.setAnswerSeq(answerSequence);
						String comment = (String) answerSeqCommentObj[1];
						int index = scoreSamplingInfoList
								.indexOf(tempScoreSamplingInfo);
						if (index != -1) {
							ScoreSamplingInfo scoreSamplingInfo2 = scoreSamplingInfoList
									.get(index);
							if (scoreSamplingInfo2 != null
									&& scoreSamplingInfo2.getComments() == null) {
								scoreSamplingInfo2.setComments(comment);
							} else if (scoreSamplingInfo2 != null
									&& scoreSamplingInfo2.getComments() != null) {
								scoreSamplingInfo2
										.setComments(scoreSamplingInfo2
												.getComments() + comment);
							}
							scoreSamplingInfo2.setComments(scoreSamplingInfo2
									.getComments() + "<br><br>");
						}

					}

				}
			}
		}
		return scoreSamplingInfoList;
	}

	/**
	 * @param scoreHistoryInfo
	 */
	private void buildHistorySearchCriteriaInfo(
			ScoreHistoryInfo scoreHistoryInfo) {
		if (scoreHistoryInfo != null && scoreHistoryInfo.isHistoryBlock()) {
			// Build history scorerId list
			List<String> historyScorerIdList = buildHistoryScorerIdList(scoreHistoryInfo);
			scoreHistoryInfo.setHistoryScorerIdList(historyScorerIdList);

			String historyIncludeCheckPoints = scoreHistoryInfo
					.getHistoryIncludeCheckPoints();
			String historyExcludeCheckPoints = scoreHistoryInfo
					.getHistoryExcludeCheckPoints();

			if (!StringUtils.isBlank(historyIncludeCheckPoints)) {
				// Calculate bitValue for historyIncludeCheckPoints
				scoreHistoryInfo.setHistoryIncludeBitValue(confirmScoreService
						.calculateBitValue(historyIncludeCheckPoints));
			}

			if (!StringUtils.isBlank(historyExcludeCheckPoints)) {
				// Calculate bitValue for historyExcludeCheckPoints
				scoreHistoryInfo.setHistoryExcludeBitValue(confirmScoreService
						.calculateBitValue(historyExcludeCheckPoints));
			}

			// Build historyUpdateStartDate object
			Date historyUpdateStartDate = buildDateObject(
					scoreHistoryInfo.getHistoryUpdateDateStartYear(),
					scoreHistoryInfo.getHistoryUpdateDateStartMonth(),
					scoreHistoryInfo.getHistoryUpdateDateStartDay(),
					scoreHistoryInfo.getHistoryUpdateDateStartHours(),
					scoreHistoryInfo.getHistoryUpdateDateStartMin());
			scoreHistoryInfo.setHistoryUpdateStartDate(historyUpdateStartDate);

			// Build historyUpdateEndDate object
			Date historyUpdateEndDate = buildDateObject(
					scoreHistoryInfo.getHistoryUpdateDateEndYear(),
					scoreHistoryInfo.getHistoryUpdateDateEndMonth(),
					scoreHistoryInfo.getHistoryUpdateDateEndDay(),
					scoreHistoryInfo.getHistoryUpdateDateEndHours(),
					scoreHistoryInfo.getHistoryUpdateDateEndMin());
			scoreHistoryInfo.setHistoryUpdateEndDate(historyUpdateEndDate);
		}
	}

	/**
	 * @param scoreCurrentInfo
	 */
	private void buildCurrentSearchCriteriaInfo(
			ScoreCurrentInfo scoreCurrentInfo) {
		if (scoreCurrentInfo != null && scoreCurrentInfo.isCurrentBlock()) {
			// Build current scorerId list
			List<String> currentScorerIdList = buildCurrentScorerIdList(scoreCurrentInfo);
			scoreCurrentInfo.setCurrentScorerIdList(currentScorerIdList);

			String currentIncludeCheckPoints = scoreCurrentInfo
					.getCurrentIncludeCheckPoints();
			String currentExcludeCheckPoints = scoreCurrentInfo
					.getCurrentExcludeCheckPoints();

			if (!StringUtils.isBlank(currentIncludeCheckPoints)) {
				// Calculate bitValue for currentIncludeCheckPoints
				scoreCurrentInfo.setCurrentIncludeBitValue(confirmScoreService
						.calculateBitValue(currentIncludeCheckPoints));
			}

			if (!StringUtils.isBlank(currentExcludeCheckPoints)) {
				// Calculate bitValue for currentExcludeCheckPoints
				scoreCurrentInfo.setCurrentExcludeBitValue(confirmScoreService
						.calculateBitValue(currentExcludeCheckPoints));
			}

			// Build currentUpdateStartDate object
			Date currentUpdateStartDate = buildDateObject(
					scoreCurrentInfo.getCurrentUpdateDateStartYear(),
					scoreCurrentInfo.getCurrentUpdateDateStartMonth(),
					scoreCurrentInfo.getCurrentUpdateDateStartDay(),
					scoreCurrentInfo.getCurrentUpdateDateStartHours(),
					scoreCurrentInfo.getCurrentUpdateDateStartMin());
			scoreCurrentInfo.setCurrentUpdateStartDate(currentUpdateStartDate);

			// Build currentUpdateEndDate object
			Date currentUpdateEndDate = buildDateObject(
					scoreCurrentInfo.getCurrentUpdateDateEndYear(),
					scoreCurrentInfo.getCurrentUpdateDateEndMonth(),
					scoreCurrentInfo.getCurrentUpdateDateEndDay(),
					scoreCurrentInfo.getCurrentUpdateDateEndHours(),
					scoreCurrentInfo.getCurrentUpdateDateEndMin());
			scoreCurrentInfo.setCurrentUpdateEndDate(currentUpdateEndDate);
		}
	}

	/**
	 * @param year
	 * @param month
	 * @param day
	 * @param hours
	 * @param min
	 * @return Date
	 */
	private Date buildDateObject(Integer year, Integer month, Integer day,
			Integer hours, Integer min) {

		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DATE, day);
		cal.set(Calendar.HOUR_OF_DAY, hours);
		cal.set(Calendar.MINUTE, min);

		return cal.getTime();

	}

	/**
	 * @param scoreHistoryInfo
	 * @return List<String>
	 */
	private List<String> buildHistoryScorerIdList(
			ScoreHistoryInfo scoreHistoryInfo) {

		String historyScorerId1 = scoreHistoryInfo.getHistoryScorerId1();
		String historyScorerId2 = scoreHistoryInfo.getHistoryScorerId2();
		String historyScorerId3 = scoreHistoryInfo.getHistoryScorerId3();
		String historyScorerId4 = scoreHistoryInfo.getHistoryScorerId4();
		String historyScorerId5 = scoreHistoryInfo.getHistoryScorerId5();

		// Common method to build scorerId list
		return buildScorerIdList(historyScorerId1, historyScorerId2,
				historyScorerId3, historyScorerId4, historyScorerId5);
	}

	/**
	 * @param scoreCurrentInfo
	 * @return List<String>
	 */
	private List<String> buildCurrentScorerIdList(
			ScoreCurrentInfo scoreCurrentInfo) {

		String currentScorerId1 = scoreCurrentInfo.getCurrentScorerId1();
		String currentScorerId2 = scoreCurrentInfo.getCurrentScorerId2();
		String currentScorerId3 = scoreCurrentInfo.getCurrentScorerId3();
		String currentScorerId4 = scoreCurrentInfo.getCurrentScorerId4();
		String currentScorerId5 = scoreCurrentInfo.getCurrentScorerId5();

		// Common method to build scorerId list
		return buildScorerIdList(currentScorerId1, currentScorerId2,
				currentScorerId3, currentScorerId4, currentScorerId5);
	}

	/**
	 * @param scorerId1
	 * @param scorerId2
	 * @param scorerId3
	 * @param scorerId4
	 * @param scorerId5
	 * @return List<String>
	 */
	private List<String> buildScorerIdList(String scorerId1, String scorerId2,
			String scorerId3, String scorerId4, String scorerId5) {
		List<String> scorerIdList = new ArrayList<String>();

		if (!StringUtils.isBlank(scorerId1)) {
			scorerIdList.add(scorerId1);
		}

		if (!StringUtils.isBlank(scorerId2)) {
			scorerIdList.add(scorerId2);
		}

		if (!StringUtils.isBlank(scorerId3)) {
			scorerIdList.add(scorerId3);
		}

		if (!StringUtils.isBlank(scorerId4)) {
			scorerIdList.add(scorerId4);
		}

		if (!StringUtils.isBlank(scorerId5)) {
			scorerIdList.add(scorerId5);
		}

		return scorerIdList;
	}

	/**
	 * @param subjectNameList
	 * @return Map<String, String>
	 */
	@SuppressWarnings("rawtypes")
	private Map<String, String> buildSubjectNameMap(List subjectNameList) {
		Map<String, String> subjectNameMap = null;

		if (!subjectNameList.isEmpty()) {
			subjectNameMap = new LinkedHashMap<String, String>();

			for (Object mstSubjectObj : subjectNameList) {
				Object[] mstSubjectObjArray = (Object[]) mstSubjectObj;

				subjectNameMap.put((String) mstSubjectObjArray[0],
						(String) mstSubjectObjArray[1]);
			}
		}

		return subjectNameMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.saiten.service.ScoreSearchService#findHistoryEventList(java.lang.
	 * String)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Map<Short, String> findHistoryEventList(String menuId) {
		// Get screenIdMap from saitenConfigObject
		LinkedHashMap<String, String> screenIdMap = SaitenUtil
				.getSaitenConfigObject().getScreenIdMap();

		String screenId = null;
		if (menuId.equals(WebAppConst.REFERENCE_SAMP_MENU_ID)) {
			// Get screenId for REFERENCE_SAMP_MENU_ID
			screenId = screenIdMap
					.get(WebAppConst.REFERENCE_SAMPLING_SCREEN_ID);
		} else if (menuId.equals(WebAppConst.SCORE_SAMP_MENU_ID)) {
			// Get screenId for SCORE_SAMP_MENU_ID
			screenId = screenIdMap.get(WebAppConst.SCORE_SAMPLING_SCREEN_ID);
		} else if (menuId.equals(WebAppConst.FORCED_MENU_ID)) {
			// Get screenId for FORCED_MENU_ID
			screenId = screenIdMap.get(WebAppConst.FORCE_SAMPLING_SCREEN_ID);
		} else if (menuId.equals(WebAppConst.STATE_TRAN_MENU_ID)) {
			// Get screenId for STATE_TRAN_MENU_ID
			screenId = screenIdMap.get(WebAppConst.STATE_TRANSITION_SCREEN_ID);
		}
		try {
			List historyEventList = getMstSamplingEventCondDAO()
					.findHistoryEventList(screenId);

			return buildHistoryEventMap(historyEventList);
		} catch (HibernateException he) {
			throw new SaitenRuntimeException(
					ErrorCode.SCORE_SEARCH_HIBERNATE_EXCEPTION, he);
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.SCORE_SEARCH_SERVICE_EXCEPTION, e);
		}
	}

	/**
	 * @param historyEventList
	 * @return Map<Short, String>
	 */
	@SuppressWarnings("rawtypes")
	private Map<Short, String> buildHistoryEventMap(List historyEventList) {
		LinkedHashMap<Short, String> historyEventMap = null;
		if (!historyEventList.isEmpty()) {
			historyEventMap = new LinkedHashMap<Short, String>();

			for (Object historyEventObj : historyEventList) {
				Object[] historyEventObjArray = (Object[]) historyEventObj;

				historyEventMap.put((Short) historyEventObjArray[0],
						(String) historyEventObjArray[1]);
			}
		}

		return historyEventMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.saiten.service.ScoreSearchService#findCurrentStateList(java.lang.
	 * String)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Map<Short, String> findCurrentStateList(String menuId) {
		// Get screenIdMap from saitenConfigObject
		LinkedHashMap<String, String> screenIdMap = SaitenUtil
				.getSaitenConfigObject().getScreenIdMap();

		String screenId = null;
		if (menuId.equals(WebAppConst.REFERENCE_SAMP_MENU_ID)) {
			// Get screenId for REFERENCE_SAMP_MENU_ID
			screenId = screenIdMap
					.get(WebAppConst.REFERENCE_SAMPLING_SCREEN_ID);
		} else if (menuId.equals(WebAppConst.SCORE_SAMP_MENU_ID)) {
			// Get screenId for SCORE_SAMP_MENU_ID
			screenId = screenIdMap.get(WebAppConst.SCORE_SAMPLING_SCREEN_ID);
		} else if (menuId.equals(WebAppConst.FORCED_MENU_ID)) {
			// Get screenId for FORCED_MENU_ID
			screenId = screenIdMap.get(WebAppConst.FORCE_SAMPLING_SCREEN_ID);
		} else if (menuId.equals(WebAppConst.STATE_TRAN_MENU_ID)) {
			// Get screenId for STATE_TRAN_MENU_ID
			screenId = screenIdMap.get(WebAppConst.STATE_TRANSITION_SCREEN_ID);
		}
		try {
			List currentStateList = getMstSamplingStateCondDAO()
					.findCurrentStateList(screenId);

			return buildCurrentStateMap(currentStateList);
		} catch (HibernateException he) {
			throw new SaitenRuntimeException(
					ErrorCode.SCORE_SEARCH_HIBERNATE_EXCEPTION, he);
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.SCORE_SEARCH_SERVICE_EXCEPTION, e);
		}
	}

	/**
	 * @param currentStateList
	 * @return Map<Short, String>
	 */
	@SuppressWarnings("rawtypes")
	private Map<Short, String> buildCurrentStateMap(List currentStateList) {
		LinkedHashMap<Short, String> currentStateMap = null;
		if (!currentStateList.isEmpty()) {
			currentStateMap = new LinkedHashMap<Short, String>();

			for (Object currentStateObj : currentStateList) {
				Object[] currentStateObjArray = (Object[]) currentStateObj;

				currentStateMap.put((Short) currentStateObjArray[0],
						(String) currentStateObjArray[1]);
			}
		}

		return currentStateMap;
	}

	@Override
	public List<String> buildLoggedInScorerSubjectList(String scorerId) {

		List<String> loggedInScorerSubjectList = new ArrayList<String>();
		LinkedHashMap<Integer, MstQuestion> mstQuestionMap = null;
		try {
			/*
			 * LinkedHashMap<String, LinkedHashMap<Integer, MstQuestion>>
			 * mstScorerQuestionMap = SaitenUtil
			 * .getSaitenConfigObject().getMstScorerQuestionMap();
			 */
			/*
			 * LinkedHashMap<String, LinkedHashMap<Integer, MstQuestion>>
			 * mstScorerQuestionMap = (LinkedHashMap<String,
			 * LinkedHashMap<Integer, MstQuestion>>) SaitenUtil
			 * .getSession().get("mstScorerQuestionMap"); mstQuestionMap =
			 * mstScorerQuestionMap.get(scorerId);
			 */
			mstQuestionMap = SaitenUtil.getMstQuestionMapByScorerId(scorerId);

			for (MstQuestion mstQuestion : mstQuestionMap.values()) {
				String subjectCode = mstQuestion.getMstSubject()
						.getSubjectCode();
				if (!loggedInScorerSubjectList.contains(subjectCode)) {
					loggedInScorerSubjectList.add(subjectCode);
				}
			}
			return loggedInScorerSubjectList;
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.SCORE_SEARCH_SERVICE_EXCEPTION, e);
		}

	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<TranDescScoreInfo> findProcessDetails(Integer answerSequence,
			String connectionString) {
		List<TranDescScoreInfo> processDetailsList = null;
		try {
			List searchResultList = tranDescScoreHistoryDAO.findProcessDetails(
					answerSequence, connectionString);
			if (searchResultList != null && searchResultList.size() > 0) {
				processDetailsList = new ArrayList<TranDescScoreInfo>();

				for (Object answerRecord : searchResultList) {
					Object[] answerRecordObjArray = (Object[]) answerRecord;

					AnswerInfo answerInfo = new AnswerInfo();
					TranDescScoreInfo tranDescScoreInfo = new TranDescScoreInfo();
					tranDescScoreInfo.setAnswerInfo(answerInfo);

					tranDescScoreInfo.setScoringState(Short
							.valueOf(answerRecordObjArray[0].toString()));
					tranDescScoreInfo
							.setLatestScreenScorerId((String) answerRecordObjArray[1]);
					tranDescScoreInfo
							.setGradeNum((Integer) answerRecordObjArray[2]);
					tranDescScoreInfo
							.setPendingCategory((Short) answerRecordObjArray[3]);
					answerInfo.setUpdateDate((Date) answerRecordObjArray[4]);
					answerInfo
							.setQuestionSeq((Integer) answerRecordObjArray[5]);
					tranDescScoreInfo
							.setGradeSeq((Integer) answerRecordObjArray[6]);

					processDetailsList.add(tranDescScoreInfo);
				}
			}
		} catch (HibernateException he) {
			throw new SaitenRuntimeException(
					ErrorCode.SCORE_SEARCH_HIBERNATE_EXCEPTION, he);
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.SCORE_SEARCH_SERVICE_EXCEPTION, e);
		}
		return processDetailsList;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List findDenyCategorySeqList(Integer questionSeq,
			Short[] denyCategoryList) {
		try {
			return getMstDenyCategoryDAO().findDenyCategorySeqList(questionSeq,
					denyCategoryList);
		} catch (HibernateException he) {
			throw new SaitenRuntimeException(
					ErrorCode.SCORE_SEARCH_HIBERNATE_EXCEPTION, he);
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.SCORE_SEARCH_SERVICE_EXCEPTION, e);
		}
	}

	public MstSubjectDAO getMstSubjectDAO() {
		return mstSubjectDAO;
	}

	/**
	 * @param mstSubjectDAO
	 */
	public void setMstSubjectDAO(MstSubjectDAO mstSubjectDAO) {
		this.mstSubjectDAO = mstSubjectDAO;
	}

	public ConfirmScoreService getConfirmScoreService() {
		return confirmScoreService;
	}

	/**
	 * @param confirmScoreService
	 */
	public void setConfirmScoreService(ConfirmScoreService confirmScoreService) {
		this.confirmScoreService = confirmScoreService;
	}

	public MstPendingCategoryDAO getMstPendingCategoryDAO() {
		return mstPendingCategoryDAO;
	}

	/**
	 * @param mstPendingCategoryDAO
	 */
	public void setMstPendingCategoryDAO(
			MstPendingCategoryDAO mstPendingCategoryDAO) {
		this.mstPendingCategoryDAO = mstPendingCategoryDAO;
	}

	public MstGradeDAO getMstGradeDAO() {
		return mstGradeDAO;
	}

	/**
	 * @param mstGradeDAO
	 */
	public void setMstGradeDAO(MstGradeDAO mstGradeDAO) {
		this.mstGradeDAO = mstGradeDAO;
	}

	public MstQuestionDAO getMstQuestionDAO() {
		return mstQuestionDAO;
	}

	/**
	 * @param mstQuestionDAO
	 */
	public void setMstQuestionDAO(MstQuestionDAO mstQuestionDAO) {
		this.mstQuestionDAO = mstQuestionDAO;
	}

	public TranDescScoreHistoryDAO getTranDescScoreHistoryDAO() {
		return tranDescScoreHistoryDAO;
	}

	/**
	 * @param tranDescScoreHistoryDAO
	 */
	public void setTranDescScoreHistoryDAO(
			TranDescScoreHistoryDAO tranDescScoreHistoryDAO) {
		this.tranDescScoreHistoryDAO = tranDescScoreHistoryDAO;
	}

	public MstSamplingStateCondDAO getMstSamplingStateCondDAO() {
		return mstSamplingStateCondDAO;
	}

	/**
	 * @param mstSamplingStateCondDAO
	 */
	public void setMstSamplingStateCondDAO(
			MstSamplingStateCondDAO mstSamplingStateCondDAO) {
		this.mstSamplingStateCondDAO = mstSamplingStateCondDAO;
	}

	public MstSamplingEventCondDAO getMstSamplingEventCondDAO() {
		return mstSamplingEventCondDAO;
	}

	/**
	 * @param mstSamplingEventCondDAO
	 */
	public void setMstSamplingEventCondDAO(
			MstSamplingEventCondDAO mstSamplingEventCondDAO) {
		this.mstSamplingEventCondDAO = mstSamplingEventCondDAO;
	}

	public TranDescScoreDAO getTranDescScoreDAO() {
		return tranDescScoreDAO;
	}

	/**
	 * @param tranDescScoreDAO
	 */
	public void setTranDescScoreDAO(TranDescScoreDAO tranDescScoreDAO) {
		this.tranDescScoreDAO = tranDescScoreDAO;
	}

	public MstDenyCategoryDAO getMstDenyCategoryDAO() {
		return mstDenyCategoryDAO;
	}

	public void setMstDenyCategoryDAO(MstDenyCategoryDAO mstDenyCategoryDAO) {
		this.mstDenyCategoryDAO = mstDenyCategoryDAO;
	}


}