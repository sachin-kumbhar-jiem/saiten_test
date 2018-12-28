package com.saiten.service.impl;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.ArrayUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.HibernateException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.saiten.bean.SaitenConfig;
import com.saiten.bean.ScoringStateKey;
import com.saiten.dao.TranDescScoreDAO;
import com.saiten.dao.TranDescScoreHistoryDAO;
import com.saiten.dao.TranQualitycheckScoreDAO;
import com.saiten.exception.SaitenRuntimeException;
import com.saiten.info.AnswerInfo;
import com.saiten.info.GradeInfo;
import com.saiten.info.MstScorerInfo;
import com.saiten.info.QuestionInfo;
import com.saiten.info.TranDescScoreInfo;
import com.saiten.model.MstQuestion;
import com.saiten.model.TranDescScore;
import com.saiten.service.ScoreService;
import com.saiten.util.ErrorCode;
import com.saiten.util.SaitenUtil;
import com.saiten.util.WebAppConst;

/**
 * @author sachin
 * @version 1.0
 * @created 11-Dec-2012 12:58:45 PM
 */
public class ScoreServiceImpl implements ScoreService {

	private TranDescScoreDAO tranDescScoreDAO;
	private TranDescScoreHistoryDAO tranDescScoreHistoryDAO;
	private Properties saitenGlobalProperties;
	private TranQualitycheckScoreDAO tranQualitycheckScoreDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saiten.service.ScoreService#findAnswer(int, java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public TranDescScoreInfo findAnswer(int questionSeq, String menuId, String scorerId, String connectionString,
			Integer gradeNum, Short pendingCategory, Short denyCategory, String answerFormNum,
			Integer historyRecordCount, int roleId, Short selectedMarkValue, QuestionInfo questionInfo,
			Double bitValue) {
		try {
			// Get menuIdAndScoringStateMap from saitenConfigObject
			LinkedHashMap<String, Short> menuIdAndScoringStateMap = ((SaitenConfig) ServletActionContext
					.getServletContext().getAttribute("saitenConfigObject")).getMenuIdAndScoringStateMap();

			/*
			 * List<Integer> gradeSeqList = new ArrayList<Integer>(); if
			 * (gradeNum != null) { gradeSeqList = SaitenUtil
			 * .getGradeSeqListByQuestionSeqAndGradeNum(questionSeq, gradeNum);
			 * }
			 */

			List<Integer> questionSequenceList = new ArrayList<Integer>();
			questionSequenceList.add(questionSeq);
			List tranDescScoreInfoList = null;

			// Fetch answer corresponding to selected question
			if (!(menuId.equals(WebAppConst.SPECIAL_SCORING_BLIND_TYPE_MENU_ID)
					|| menuId.equals(WebAppConst.SPECIAL_SCORING_LANGUAGE_SUPPORT_MENU_ID)
					|| menuId.equals(WebAppConst.SPECIAL_SCORING_ENLARGE_TYPE_MENU_ID)
					|| menuId.equals(WebAppConst.SPECIAL_SCORING_OMR_READ_FAIL_MENU_ID))) {
				Map<String, String> configMap = SaitenUtil.getConfigMap();
				boolean qualityFromPendingMenu = Boolean.valueOf(configMap.get("qualityFromPendingMenu"));
				Integer randomNumberRange = null;
				for (int count = 0; count <= 5; count++) {
					boolean passByRandomFlag = false;
					if (count < 5) {
						randomNumberRange = Integer
								.valueOf(saitenGlobalProperties.getProperty(WebAppConst.RANDOM_NUMBER));
						passByRandomFlag = true;
					}

					String selectedMarkValueObj = null;
					if (selectedMarkValue != null) {
						selectedMarkValueObj = new String();
						if (selectedMarkValue == -1) {
							selectedMarkValueObj = "OTHER";
						} else {
							selectedMarkValueObj = selectedMarkValue.toString();
						}
					}

					tranDescScoreInfoList = tranDescScoreDAO.findAnswer(questionSequenceList, menuId, scorerId,
							menuIdAndScoringStateMap, connectionString, gradeNum, pendingCategory, denyCategory,
							answerFormNum, historyRecordCount, randomNumberRange, passByRandomFlag,
							selectedMarkValueObj, roleId, qualityFromPendingMenu, questionInfo.getInspectionGroupSeq(),
							bitValue);
					if (!tranDescScoreInfoList.isEmpty()) {
						break;
					}
				}

			} else {
				tranDescScoreInfoList = tranDescScoreDAO.findAnswer(questionSequenceList, menuId, scorerId,
						menuIdAndScoringStateMap, connectionString, answerFormNum, historyRecordCount, roleId);
			}
			// Build tranDescScoreInfo object to display on scoring screen
			boolean historyScreenFlag = WebAppConst.FALSE;
			Integer historySeq = null;
			boolean bookmarkScreenFlag = WebAppConst.FALSE;
			Integer qcSeq = null;
			if (!tranDescScoreInfoList.isEmpty()) {
				return buildTranDescScoreInfo(tranDescScoreInfoList, scorerId, historyScreenFlag, historySeq, menuId,
						bookmarkScreenFlag, qcSeq, questionInfo);
			} else {
				TranDescScoreInfo tranDescScoreInfo = null;
				return tranDescScoreInfo;
			}
		} catch (HibernateException he) {
			throw new SaitenRuntimeException(ErrorCode.SCORE_HIBERNATE_EXCEPTION, he);
		} catch (SaitenRuntimeException we) {
			throw we;
		} catch (Exception e) {
			throw new SaitenRuntimeException(ErrorCode.SCORE_SERVICE_EXCEPTION, e);
		}
	}

	/**
	 * @param tranDescScoreInfoList
	 * @param scorerId
	 * @param historyScreenFlag
	 * @param historySeq
	 * @return TranDescScoreInfo
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	@SuppressWarnings("rawtypes")
	private TranDescScoreInfo buildTranDescScoreInfo(List tranDescScoreInfoList, String scorerId,
			boolean historyScreenFlag, Integer historySeq, String menuId, boolean bookmarkScreenFlag, Integer qcSeq,
			QuestionInfo questionInfo) throws IOException, URISyntaxException {
		TranDescScoreInfo tranDescScoreInfo = null;

		if (!tranDescScoreInfoList.isEmpty()) {
			Object[] tranDescScoreObjArray = (Object[]) tranDescScoreInfoList.get(0);

			// Get current WebApp Context
			ApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();

			AnswerInfo answerInfo = (AnswerInfo) ctx.getBean("answerInfo");
			tranDescScoreInfo = (TranDescScoreInfo) ctx.getBean("tranDescScoreInfo");

			tranDescScoreInfo.setAnswerInfo(answerInfo);

			if (tranDescScoreObjArray[0] != null) {
				answerInfo.setAnswerSeq(Integer.valueOf(tranDescScoreObjArray[0].toString()));
			}

			if (!bookmarkScreenFlag) {
				// Set lockBy for new answer and history answer record, except
				// bookmark answer.
				answerInfo.setLockBy(scorerId);
			}

			tranDescScoreInfo.setAnswerFormNumber((String) tranDescScoreObjArray[1]);
			if (questionInfo.getQuestionType() == WebAppConst.WRITING_TYPE) {
				String imageFileName = (String) tranDescScoreObjArray[2];
				tranDescScoreInfo.setImageFileName(SaitenUtil.getAnswerDataText(imageFileName, questionInfo));
			} else {
				tranDescScoreInfo.setImageFileName((String) tranDescScoreObjArray[2]);
			}

			// fetch markvalues for answer.
			if (!(historyScreenFlag || bookmarkScreenFlag)) {
				String markValues = (String) tranDescScoreObjArray[7];
				if (markValues != null && markValues.length() > 0) {
					String[] markValueArray = markValues.split(",");
					Short[] intMarkValues = new Short[markValueArray.length];
					for (int i = 0; i < markValueArray.length; i++) {
						intMarkValues[i] = Short.parseShort(markValueArray[i]);
					}
					List<Short> markValueList = Arrays.asList(intMarkValues);
					tranDescScoreInfo.setMarkValueList(markValueList);
				}
				if ((String) tranDescScoreObjArray[8] != null) {
					answerInfo.setLatestScreenScorerId((String) tranDescScoreObjArray[8]);
				}
				if ((String) tranDescScoreObjArray[9] != null) {
					answerInfo.setSecondLatestScreenScorerId((String) tranDescScoreObjArray[9]);
				}
				if ((String) tranDescScoreObjArray[10] != null) {
					answerInfo.setPunchText((String) tranDescScoreObjArray[10]);
				}
			}

			if (qcSeq != null) {
				answerInfo.setQcSeq(qcSeq);
				answerInfo.setScorerComment((String) tranDescScoreObjArray[3]);

				tranDescScoreInfo.setGradeSeq((Integer) tranDescScoreObjArray[4]);

				answerInfo.setBitValue((Double) tranDescScoreObjArray[5]);
				answerInfo.setUpdateDate((Date) tranDescScoreObjArray[6]);
				answerInfo.setPendingCategorySeq((Integer) tranDescScoreObjArray[7]);
				tranDescScoreInfo.setScoringState((Short) tranDescScoreObjArray[8]);
				answerInfo.setQuestionSeq((Integer) tranDescScoreObjArray[9]);
				answerInfo.setPunchText((String) tranDescScoreObjArray[10]);

			} else {

				if (historyScreenFlag) {
					if (historySeq == null && qcSeq == null) {
						Integer seq = (Integer) tranDescScoreObjArray[12];
						BigInteger bigInt = new BigInteger(tranDescScoreObjArray[13].toString());
						int isQc = bigInt.intValue();
						if (isQc == 1) {
							answerInfo.setQcSeq(seq);
						} else {
							answerInfo.setHistorySeq(seq);
						}
						
						if(tranDescScoreObjArray[14] != null){
							answerInfo.setPunchText((String) tranDescScoreObjArray[14]);
						}
					} else {
						answerInfo.setHistorySeq(historySeq);
						
						if(tranDescScoreObjArray[12] != null){
							answerInfo.setPunchText((String) tranDescScoreObjArray[12]);
						}
					}
					answerInfo.setBookMarkFlag((Character) tranDescScoreObjArray[3]);
					answerInfo.setScorerComment((String) tranDescScoreObjArray[4]);

					tranDescScoreInfo.setGradeSeq((Integer) tranDescScoreObjArray[5]);

					answerInfo.setBitValue((Double) tranDescScoreObjArray[6]);
					answerInfo.setUpdateDate((Date) tranDescScoreObjArray[7]);
					answerInfo.setPendingCategorySeq((Integer) tranDescScoreObjArray[8]);
					tranDescScoreInfo.setScoringState((Short) tranDescScoreObjArray[9]);
					answerInfo.setQuestionSeq((Integer) tranDescScoreObjArray[10]);
					answerInfo.setQualityCheckFlag((Character) tranDescScoreObjArray[11]);
					/*if(tranDescScoreObjArray[12] != null){
						answerInfo.setPunchText((String) tranDescScoreObjArray[12]);
					}*/
				} else if (!historyScreenFlag && (menuId.equals(WebAppConst.CHECKING_MENU_ID)
						|| menuId.equals(WebAppConst.INSPECTION_MENU_ID) || menuId.equals(WebAppConst.DENY_MENU_ID)
						|| menuId.equals(WebAppConst.NO_GRADE_MENU_ID)
						|| menuId.equals(WebAppConst.SPECIAL_SCORING_OMR_READ_FAIL_MENU_ID)
						|| menuId.equals(WebAppConst.SPECIAL_SCORING_ENLARGE_TYPE_MENU_ID))) {
					tranDescScoreInfo.setGradeSeq((Integer) tranDescScoreObjArray[3]);
					answerInfo.setBitValue((Double) tranDescScoreObjArray[4]);
					answerInfo.setQuestionSeq((Integer) tranDescScoreObjArray[5]);
					answerInfo.setUpdateDate((Date) tranDescScoreObjArray[6]);
				}
			}
		}
		
		//Added code for compare question and answer text
		if (tranDescScoreInfo != null && tranDescScoreInfo.getAnswerInfo().getPunchText() != null) {
			LinkedHashMap<Integer, MstQuestion> mstQuestionMap = new LinkedHashMap<Integer, MstQuestion>();
			mstQuestionMap = SaitenUtil.getSaitenConfigObject().getMstQuestionMap();
			MstQuestion mstQuestion = mstQuestionMap.get(questionInfo.getQuestionSeq());
			tranDescScoreInfo.setDuplicateWords(SaitenUtil
					.consecutiveCharacterMatch(mstQuestion.getQuestionContents(),
							tranDescScoreInfo.getAnswerInfo().getPunchText())
					.toString().replaceAll("\\[", "").replaceAll("]", ""));

		}
		
		return tranDescScoreInfo;
	}

	@SuppressWarnings({ "unchecked" })
	public List<Integer> findQcAnsSeqList(int questionSeq, String scorerId, Short selectedMarkValue,
			String connectionString) {
		List<Integer> qcAnswerSeqList = new ArrayList<Integer>();
		List<Integer> questionSequenceList = new ArrayList<Integer>();
		questionSequenceList.add(questionSeq);

		qcAnswerSeqList = tranDescScoreDAO.findQcAnsSeqList(questionSequenceList, scorerId, connectionString,
				selectedMarkValue);

		return qcAnswerSeqList;

	}

	public TranDescScoreInfo findQualityCheckAnswers(int qcAnswerSeq, String menuId, String scorerId,
			String connectionString, QuestionInfo questionInfo) {
		try {
			@SuppressWarnings("rawtypes")
			List tranDescScoreInfoList = new ArrayList();
			tranDescScoreInfoList = tranDescScoreDAO.findQualityCheckAnswers(qcAnswerSeq, connectionString);
			// Build tranDescScoreInfo object to display on scoring screen
			boolean historyScreenFlag = WebAppConst.FALSE;
			Integer historySeq = null;
			boolean bookmarkScreenFlag = WebAppConst.FALSE;
			Integer qcSeq = null;
			if (!tranDescScoreInfoList.isEmpty()) {
				return buildTranDescScoreInfo(tranDescScoreInfoList, scorerId, historyScreenFlag, historySeq, menuId,
						bookmarkScreenFlag, qcSeq, questionInfo);
			} else {
				TranDescScoreInfo tranDescScoreInfo = null;
				return tranDescScoreInfo;
			}
		} catch (HibernateException he) {
			throw new SaitenRuntimeException(ErrorCode.SCORE_HIBERNATE_EXCEPTION, he);
		} catch (SaitenRuntimeException we) {
			throw we;
		} catch (Exception e) {
			throw new SaitenRuntimeException(ErrorCode.SCORE_SERVICE_EXCEPTION, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saiten.service.ScoreService#buildGradeInfo(java.lang.Integer,
	 * int)
	 */
	public GradeInfo buildGradeInfo(Integer gradeSeq, int questionSeq) {
		GradeInfo gradeInfo = (GradeInfo) SaitenUtil.getApplicationContext().getBean("gradeInfo");
		try {
			Integer gradeNum = SaitenUtil.getGradeNumByGradeSequence(gradeSeq, questionSeq);
			if (gradeNum != null) {
				gradeInfo.setGradeNum(gradeNum.toString());
			}
			gradeInfo.setResult(SaitenUtil.getResultByGradeSequence(gradeSeq, questionSeq));
			return gradeInfo;
		} catch (Exception e) {
			throw new SaitenRuntimeException(ErrorCode.HISTORY_SCORE_SERVICE_EXCEPTION, e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saiten.service.ScoreService#findHistoryAnswer(java.lang.Integer,
	 * java.lang.String)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public TranDescScoreInfo findHistoryAnswer(Integer qcSeq, Integer historySeq, String connectionString,
			String scorerId, boolean bookmarkScreenFlag, QuestionInfo questionInfo) {
		boolean historyScreenFlag = WebAppConst.TRUE;
		try {
			List historyInfoList = null;
			@SuppressWarnings("unused")
			boolean qcFlag = false;
			if (qcSeq != null) {
				qcFlag = WebAppConst.TRUE;
				historyInfoList = tranQualitycheckScoreDAO.findQcHistoryAnswer(qcSeq, connectionString);
			} else {
				historyInfoList = tranDescScoreHistoryDAO.findHistoryAnswer(historySeq, connectionString);
			}

			// menuId is not needed in case of history
			String menuId = null;
			return buildTranDescScoreInfo(historyInfoList, scorerId, historyScreenFlag, historySeq, menuId,
					bookmarkScreenFlag, qcSeq, questionInfo);
		} catch (HibernateException he) {
			throw new SaitenRuntimeException(ErrorCode.HISTORY_SCORE_HIBERNATE_EXCEPTION, he);
		} catch (SaitenRuntimeException we) {
			throw we;
		} catch (Exception e) {
			throw new SaitenRuntimeException(ErrorCode.HISTORY_SCORE_SERVICE_EXCEPTION, e);
		}
	}

	@Override
	public List<Double> getFirstTimeSecondTimeCheckPoints(int answerSeq, QuestionInfo questionInfo,
			Short currentScoringState) {
		Short[] latestScoringStates = null;

		try {
			if ((ArrayUtils.contains(WebAppConst.DENY_SCORING_STATES, currentScoringState)
					|| questionInfo.getMenuId().equals(WebAppConst.DENY_MENU_ID))
					&& (questionInfo.getQuestionType() == WebAppConst.SPEAKING_TYPE
							|| questionInfo.getQuestionType() == WebAppConst.WRITING_TYPE)) {

				latestScoringStates = WebAppConst.DENY_PREVIOUS_SCORING_STATES;

			} else {
				latestScoringStates = WebAppConst.MISMATCH_PREVIOUS_SCORING_STATES;
			}

			List<Double> bitValueList = tranDescScoreHistoryDAO.getFirstTimeSecondTimeCheckPoints(answerSeq,
					latestScoringStates, questionInfo, currentScoringState);

			return bitValueList;
		} catch (HibernateException he) {
			throw new SaitenRuntimeException(ErrorCode.HISTORY_SCORE_HIBERNATE_EXCEPTION, he);
		} catch (Exception e) {
			throw new SaitenRuntimeException(ErrorCode.HISTORY_SCORE_SERVICE_EXCEPTION, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saiten.service.ScoreService#lockAnswer(int, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void lockAnswer(int answerSeq, String scorerId, String connectionString, Date updateDate) {
		try {
			// Lock answer
			tranDescScoreDAO.lockAnswer(answerSeq, scorerId, connectionString, updateDate);
		} catch (HibernateException he) {
			throw new SaitenRuntimeException(ErrorCode.SCORE_HIBERNATE_EXCEPTION, he);
		} catch (Exception e) {
			throw new SaitenRuntimeException(ErrorCode.SCORE_SERVICE_EXCEPTION, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saiten.service.ScoreService#unlockAnswer(int, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void unlockAnswer(int questionSeq, String lockBy, String connectionString, Integer answerSeq) {
		try {

			// Unlock answer
			tranDescScoreDAO.unlockAnswer(questionSeq, lockBy, connectionString, answerSeq);

		} catch (HibernateException he) {
			throw new SaitenRuntimeException(ErrorCode.SCORE_HIBERNATE_EXCEPTION, he);
		} catch (Exception e) {
			throw new SaitenRuntimeException(ErrorCode.SCORE_SERVICE_EXCEPTION, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saiten.service.ScoreService#isAnswerAlreadyScored(int,
	 * java.lang.String, java.util.Date)
	 */
	@Override
	public boolean isAnswerAlreadyScored(int answerSeq, String connectionString, Date infoUpdateDate) {
		try {
			TranDescScore tranDescScore = tranDescScoreDAO.findById(answerSeq, connectionString);
			// if updateDate matched it implies answer not already scored.
			// else if updateDate not matched it implies answer already scored.
			return !SaitenUtil.getSaitenFormatDate(tranDescScore.getUpdateDate())
					.equals(SaitenUtil.getSaitenFormatDate(infoUpdateDate));
		} catch (HibernateException he) {
			throw new SaitenRuntimeException(ErrorCode.SCORE_HIBERNATE_EXCEPTION, he);
		} catch (Exception e) {
			throw new SaitenRuntimeException(ErrorCode.SCORE_SERVICE_EXCEPTION, e);
		}
	}

	/**
	 * @param tranDescScoreDAO
	 */
	public void setTranDescScoreDAO(TranDescScoreDAO tranDescScoreDAO) {
		this.tranDescScoreDAO = tranDescScoreDAO;
	}

	/**
	 * @param tranDescScoreHistoryDAO
	 */
	public void setTranDescScoreHistoryDAO(TranDescScoreHistoryDAO tranDescScoreHistoryDAO) {
		this.tranDescScoreHistoryDAO = tranDescScoreHistoryDAO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.saiten.service.ScoreService#findPrevOrNextHistoryAnswer(com.saiten
	 * .info.QuestionInfo, com.saiten.info.MstScorerInfo, java.util.Date,
	 * boolean)
	 */
	@SuppressWarnings({ "rawtypes", "unused" })
	@Override
	public TranDescScoreInfo findPrevOrNextHistoryAnswer(QuestionInfo questionInfo, MstScorerInfo scorerInfo,
			Date updateDate, boolean isPrevious, boolean bookmarkScreenFlag) {
		try {
			int questionSeq = questionInfo.getQuestionSeq();
			String menuId = questionInfo.getMenuId();
			String scorerId = scorerInfo.getScorerId();
			String connectionString = questionInfo.getConnectionString();
			char noDbUpdate = scorerInfo.getNoDbUpdate();
			List tranDescScoreHistoryInfoList = null;
			Character questionType = questionInfo.getQuestionType();
			if (menuId.equals(WebAppConst.FIRST_SCORING_MENU_ID) || menuId.equals(WebAppConst.SECOND_SCORING_MENU_ID)
			/*
			 * && (questionType == WebAppConst.SPEAKING_TYPE || questionType ==
			 * WebAppConst.WRITING_TYPE)
			 */) {
				tranDescScoreHistoryInfoList = tranDescScoreHistoryDAO.findPrevOrNextHistoryAndQcAnswer(questionSeq,
						menuId, scorerId, connectionString, getScoringStateList(menuId, noDbUpdate), updateDate,
						isPrevious);

			} else {

				// Find prev or next history record based on isPrevious value
				tranDescScoreHistoryInfoList = tranDescScoreHistoryDAO.findPrevOrNextHistoryAnswer(questionSeq, menuId,
						scorerId, connectionString, getScoringStateList(menuId, noDbUpdate), updateDate, isPrevious);
			}

			// Build tranDescScoreInfo history record object to display on
			// scoring screen
			boolean historyScreenFlag = WebAppConst.TRUE;
			Integer qcSeq = null;
			return buildTranDescScoreInfo(tranDescScoreHistoryInfoList, scorerId, historyScreenFlag, null, menuId,
					bookmarkScreenFlag, qcSeq, questionInfo);
		} catch (HibernateException he) {
			throw new SaitenRuntimeException(ErrorCode.SCORE_HIBERNATE_EXCEPTION, he);
		} catch (SaitenRuntimeException we) {
			throw we;
		} catch (Exception e) {
			throw new SaitenRuntimeException(ErrorCode.SCORE_SERVICE_EXCEPTION, e);
		}
	}

	/**
	 * @param menuId
	 * @param noDbUpdate
	 * @return List<Short>
	 */
	private List<Short> getScoringStateList(String menuId, char noDbUpdate) {
		ScoringStateKey scoringStateKey = new ScoringStateKey();
		scoringStateKey.setMenuId(menuId);
		scoringStateKey.setNoDbUpdate(noDbUpdate);

		LinkedHashMap<ScoringStateKey, List<Short>> historyScoringStatesMap = ((SaitenConfig) ServletActionContext
				.getServletContext().getAttribute("saitenConfigObject")).getHistoryScoringStatesMap();

		// Get scoringStateList based on menuId and noDbUpdate
		// e.g 122, 123 OR 322, 323
		return historyScoringStatesMap.get(scoringStateKey);
	}

	/**
	 * @param tranDescScoreHistoryInfoList
	 * @return Integer
	 */
	@SuppressWarnings({ "rawtypes", "unused" })
	private Integer getHistorySeq(List tranDescScoreHistoryInfoList) {
		Integer historySeq = null;
		if (!tranDescScoreHistoryInfoList.isEmpty()) {
			// Get historySeq from tranDescScoreHistoryInfoList
			historySeq = Integer.valueOf(((Object[]) tranDescScoreHistoryInfoList.get(0))[12].toString());
		}

		return historySeq;
	}

	/**
	 * This method returns testsetnum_seq of record.
	 * 
	 * @param answerSeq
	 * @return testSetnum_seq
	 */
	@SuppressWarnings("rawtypes")
	public Integer findTestsetNumSeq(Integer answerSeq, String connectionString) {
		List testsetnumseqList = tranDescScoreDAO.findTestsetNumSeq(answerSeq, connectionString);
		if (testsetnumseqList != null) {
			return Integer.parseInt(testsetnumseqList.get(0).toString());
		} else {
			return null;
		}

	}

	/**
	 * @param saitenGlobalProperties
	 *            the saitenGlobalProperties to set
	 */
	public void setSaitenGlobalProperties(Properties saitenGlobalProperties) {
		this.saitenGlobalProperties = saitenGlobalProperties;
	}

	/**
	 * @param tranQualitycheckScoreDAO
	 *            the tranQualitycheckScoreDAO to set
	 */
	public void setTranQualitycheckScoreDAO(TranQualitycheckScoreDAO tranQualitycheckScoreDAO) {
		this.tranQualitycheckScoreDAO = tranQualitycheckScoreDAO;
	}

}