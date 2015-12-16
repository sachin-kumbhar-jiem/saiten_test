package com.saiten.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.HibernateException;

import com.saiten.dao.MstQuestionDAO;
import com.saiten.dao.MstScorerQuestionDAO;
import com.saiten.dao.TranDescScoreHistoryDAO;
import com.saiten.dao.TranQualitycheckScoreDAO;
import com.saiten.exception.SaitenRuntimeException;
import com.saiten.info.CheckPointInfo;
import com.saiten.info.QuestionInfo;
import com.saiten.model.MstCheckPoint;
import com.saiten.model.MstCheckPointGroup;
import com.saiten.model.MstPendingCategory;
import com.saiten.model.MstQuestion;
import com.saiten.service.QuestionSelectionService;
import com.saiten.util.ErrorCode;
import com.saiten.util.SaitenUtil;
import com.saiten.util.WebAppConst;

/**
 * @author sachin
 * @version 1.0
 * @created 06-Dec-2012 2:35:30 PM
 */
public class QuestionSelectionServiceImpl implements QuestionSelectionService {

	@SuppressWarnings("unused")
	private MstScorerQuestionDAO mstScorerQuestionDAO;

	private MstQuestionDAO mstQuestionDAO;

	private TranDescScoreHistoryDAO tranDescScoreHistoryDAO;

	private TranQualitycheckScoreDAO tranQualitycheckScoreDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saiten.service.QuestionSelectionService#fetchDbInstanceInfo(int)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public QuestionInfo fetchDbInstanceInfo(List<Integer> questionSeq) {
		try {
			// Fetch dbInstanceInfoList
			List dbInstanceInfoList = getMstQuestionDAO().fetchDbInstanceInfo(
					questionSeq);

			// Build questionInfo object with dbInstanceInfo
			return buildDbInstanceInfo(dbInstanceInfoList);
		} catch (HibernateException he) {
			throw new SaitenRuntimeException(
					ErrorCode.QUESTION_SELECTION_HIBERNATE_EXCEPTION, he);
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.QUESTION_SELECTION_SERVICE_EXCEPTION, e);
		}
	}

	/**
	 * @param questionSeq
	 * @param specialScoringMenuId
	 * @return List<QuestionInfo>
	 */
	@SuppressWarnings("rawtypes")
	public Map<Integer, QuestionInfo> fetchDbInstanceInfo(
			List<Integer> questionSeqList, String specialScoringMenuId) {
		try {
			// Fetch dbInstanceInfoList
			List dbInstanceInfoList = getMstQuestionDAO().fetchDbInstanceInfo(
					questionSeqList);

			// Build questionInfo object with dbInstanceInfo
			return buildDbInstanceInfoList(dbInstanceInfoList,
					specialScoringMenuId);
		} catch (HibernateException he) {
			throw new SaitenRuntimeException(
					ErrorCode.QUESTION_SELECTION_HIBERNATE_EXCEPTION, he);
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.QUESTION_SELECTION_SERVICE_EXCEPTION, e);
		}
	}

	/**
	 * @param dbInstanceInfoList
	 * @return QuestionInfo
	 */
	@SuppressWarnings("rawtypes")
	private Map<Integer, QuestionInfo> buildDbInstanceInfoList(
			List dbInstanceInfoList, String specialScoringMenuId) {

		Map<Integer, QuestionInfo> questionInfoMap = null;
		if (!dbInstanceInfoList.isEmpty()) {
			questionInfoMap = new LinkedHashMap<Integer, QuestionInfo>();
			for (Object dbInstanceInfo : dbInstanceInfoList) {

				Object[] dbInstanceInfoObj = (Object[]) dbInstanceInfo;

				QuestionInfo questionInfo = new QuestionInfo();

				questionInfo.setConnectionString((String) dbInstanceInfoObj[0]);
				questionInfo.setAnswerScoreTable((String) dbInstanceInfoObj[1]);
				questionInfo
						.setAnswerScoreHistoryTable((String) dbInstanceInfoObj[2]);
				questionInfo.setManualDocument((String) dbInstanceInfoObj[3]);
				questionInfo.setQuestionFileName((String) dbInstanceInfoObj[4]);
				questionInfo.setSubjectCode((String) dbInstanceInfoObj[5]);
				questionInfo.setQuestionNum((Short) dbInstanceInfoObj[6]);
				questionInfo.setSide((String) dbInstanceInfoObj[7]);
				questionInfo.setSubjectShortName((String) dbInstanceInfoObj[8]);
				questionInfo.setQuestionSeq((Integer) dbInstanceInfoObj[9]);
				questionInfo.setQuestionType((Character) dbInstanceInfoObj[10]);
				questionInfo.setScoreType((Character) dbInstanceInfoObj[11]);
				questionInfo.setMenuId(specialScoringMenuId);

				questionInfoMap
						.put(questionInfo.getQuestionSeq(), questionInfo);
			}
		}
		return questionInfoMap;
	}

	/**
	 * @param dbInstanceInfoList
	 * @return QuestionInfo
	 */
	@SuppressWarnings("rawtypes")
	private QuestionInfo buildDbInstanceInfo(List dbInstanceInfoList) {

		QuestionInfo questionInfo = null;
		if (!dbInstanceInfoList.isEmpty()) {
			Object[] dbInstanceInfoObj = (Object[]) dbInstanceInfoList.get(0);

			questionInfo = new QuestionInfo();
			questionInfo.setConnectionString((String) dbInstanceInfoObj[0]);
			questionInfo.setAnswerScoreTable((String) dbInstanceInfoObj[1]);
			questionInfo
					.setAnswerScoreHistoryTable((String) dbInstanceInfoObj[2]);
			questionInfo.setManualDocument((String) dbInstanceInfoObj[3]);
			questionInfo.setQuestionFileName((String) dbInstanceInfoObj[4]);
			questionInfo.setSubjectCode((String) dbInstanceInfoObj[5]);
			questionInfo.setQuestionNum((Short) dbInstanceInfoObj[6]);
			questionInfo.setSide((String) dbInstanceInfoObj[7]);
			questionInfo.setSubjectShortName((String) dbInstanceInfoObj[8]);
			questionInfo.setQuestionType((Character) dbInstanceInfoObj[10]);
			questionInfo.setScoreType((Character) dbInstanceInfoObj[11]);
		}
		return questionInfo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.saiten.service.QuestionSelectionService#findQuestionsByMenuIdAndScorerId
	 * (java.lang.String, java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Map<String, String> findQuestionsByMenuIdAndScorerId(String menuId,
			String scorerId, int roleId) {
		@SuppressWarnings("unused")
		List questionList = null;
		Map<String, String> questionMap = null;

		try {
			/*
			 * if (roleDescription.equals(WebAppConst.SCORER_ROLE) ||
			 * roleDescription.equals(WebAppConst.SV_ROLE) ||
			 * roleDescription.equals(WebAppConst.WG_ROLE)) { // Fetch questions
			 * for role scorer, SV and WG questionList = mstScorerQuestionDAO
			 * .findQuestionsByMenuIdAndScorerId(menuId, scorerId); } else if
			 * (roleDescription.equals(WebAppConst.ADMIN_ROLE)) { // Fetch
			 * questions for role admin questionList =
			 * getMstQuestionDAO().findAll(menuId, scorerId); }
			 */

			// Build questionMap
			questionMap = buildQuestionMap(menuId, scorerId, roleId);
		} catch (HibernateException he) {
			throw new SaitenRuntimeException(
					ErrorCode.QUESTION_SELECTION_HIBERNATE_EXCEPTION, he);
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.QUESTION_SELECTION_SERVICE_EXCEPTION, e);
		}

		return questionMap;
	}

	/**
	 * @param questionList
	 * @return Map<String, String>
	 */
	private Map<String, String> buildQuestionMap(String menuId,
			String scorerId, int roleId) {
		Map<String, String> questionMap = new LinkedHashMap<String, String>();

		/*
		 * LinkedHashMap<ScorerQuestionKey, ScorerWiseQuestionsSpecificFlags>
		 * scorerWiseQuestionsSpecificMap = SaitenUtil
		 * .getSaitenConfigObject().getScorerWiseQuestionsSpecificMap();
		 */
		/*
		 * LinkedHashMap<ScorerQuestionKey, ScorerWiseQuestionsSpecificFlags>
		 * scorerWiseQuestionsSpecificMap = (LinkedHashMap<ScorerQuestionKey,
		 * ScorerWiseQuestionsSpecificFlags>) SaitenUtil
		 * .getSession().get("scorerWiseQuestionsSpecificMap");
		 */
		List<Character> questionTypeList = new ArrayList<Character>();
		if (menuId.equals(WebAppConst.FIRST_SCORING_MENU_ID)
				|| menuId.equals(WebAppConst.INSPECTION_MENU_ID)
				|| menuId.equals(WebAppConst.DENY_MENU_ID)
				|| menuId.equals(WebAppConst.PENDING_MENU_ID)
				|| menuId.equals(WebAppConst.NO_GRADE_MENU_ID)
				|| menuId.equals(WebAppConst.OUT_BOUNDARY_MENU_ID)) {
			questionTypeList = Arrays.asList(WebAppConst.QUESTION_TYPE);
		} else if (menuId.equals(WebAppConst.SECOND_SCORING_MENU_ID)
				|| menuId.equals(WebAppConst.MISMATCH_MENU_ID)) {
			questionTypeList.add(Arrays.asList(WebAppConst.QUESTION_TYPE)
					.get(0));
			questionTypeList.add(Arrays.asList(WebAppConst.QUESTION_TYPE)
					.get(2));
			questionTypeList.add(Arrays.asList(WebAppConst.QUESTION_TYPE)
					.get(3));

		} else if (menuId.equals(WebAppConst.CHECKING_MENU_ID)) {
			// only for long type questions
			questionTypeList.add(Arrays.asList(WebAppConst.QUESTION_TYPE)
					.get(1));
		} else if (menuId
				.equals(WebAppConst.FIRST_SCORING_QUALITY_CHECK_MENU_ID)) {
			// for speaking And writing type questions - Note: removed this
			// condition for saitama release.
			/*
			 * questionTypeList.add(Arrays.asList(WebAppConst.QUESTION_TYPE)
			 * .get(2));
			 * questionTypeList.add(Arrays.asList(WebAppConst.QUESTION_TYPE)
			 * .get(3));
			 */
			questionTypeList = Arrays.asList(WebAppConst.QUESTION_TYPE);
		}

		LinkedHashMap<Integer, MstQuestion> mstQuestionMap = new LinkedHashMap<Integer, MstQuestion>();
		if (roleId == WebAppConst.SCORER_ROLE_ID
				|| roleId == WebAppConst.SV_ROLE_ID
				|| roleId == WebAppConst.WG_ROLE_ID) {
			// Fetch questions for role scorer, SV and WG

			/*
			 * LinkedHashMap<String, LinkedHashMap<Integer, MstQuestion>>
			 * mstScorerQuestionMap = SaitenUtil
			 * .getSaitenConfigObject().getMstScorerQuestionMap();
			 */
			/*
			 * LinkedHashMap<String, LinkedHashMap<Integer, MstQuestion>>
			 * mstScorerQuestionMap = (LinkedHashMap<String,
			 * LinkedHashMap<Integer, MstQuestion>>) SaitenUtil
			 * .getSession().get("mstScorerQuestionMap");
			 */

			mstQuestionMap = SaitenUtil.getMstQuestionMapByScorerId(scorerId);

		} else if (roleId == WebAppConst.ADMIN_ROLE_ID) {
			// Fetch questions for role admin

			mstQuestionMap = SaitenUtil.getSaitenConfigObject()
					.getMstQuestionMap();

		}

		Map<String, String> configMap = SaitenUtil.getConfigMap();

		for (MstQuestion mstQuestion : mstQuestionMap.values()) {
			if (questionTypeList.contains(mstQuestion.getMstEvaluation()
					.getMstQuestionType().getQuestionType())) {

				StringBuilder tempSubjectShortName = new StringBuilder();
				tempSubjectShortName.append(mstQuestion.getMstSubject()
						.getSubjectShortName());
				tempSubjectShortName.append("-");
				tempSubjectShortName.append(mstQuestion.getQuestionNum());
				if (Boolean.valueOf(configMap.get("attribute1"))) {
					tempSubjectShortName.append("-(");
					tempSubjectShortName.append(mstQuestion.getAttribute1());
					tempSubjectShortName.append(")");
				}

				String subjectShortName = tempSubjectShortName.toString();

				/*
				 * String subjectShortName = (String)
				 * mstQuestion.getMstSubject() .getSubjectShortName() + "-" +
				 * mstQuestion.getQuestionNum() + "-" + "(" +
				 * mstQuestion.getAttribute1() + ")";
				 */
				String questionSeq = Integer.valueOf(
						mstQuestion.getQuestionSeq()).toString();

				// subjectShortName = subjectShortName - questionNum
				// questionMapKey = questionSeq : subjectShortName
				StringBuilder questionMapKey = new StringBuilder();
				questionMapKey.append(questionSeq);
				questionMapKey.append(WebAppConst.COLON);
				questionMapKey.append(subjectShortName);
				/*
				 * ScorerQuestionKey scorerQuestionKey = new
				 * ScorerQuestionKey(); scorerQuestionKey.setScorerId(scorerId);
				 * scorerQuestionKey.setQuestionSeq(Integer.valueOf(mstQuestion
				 * .getQuestionSeq()));
				 */
				if (roleId == WebAppConst.SCORER_ROLE_ID) {
					/*
					 * if (scorerWiseQuestionsSpecificMap.get(scorerQuestionKey)
					 * .getIsSaitensha() == WebAppConst.ENABLE) {
					 */
					questionMap
							.put(questionMapKey.toString(), subjectShortName);
					/* } */
				} else if (roleId == WebAppConst.SV_ROLE_ID
						|| roleId == WebAppConst.WG_ROLE_ID) {
					if ((menuId.equals(WebAppConst.FIRST_SCORING_MENU_ID)
							|| menuId.equals(WebAppConst.INSPECTION_MENU_ID)
							|| menuId.equals(WebAppConst.DENY_MENU_ID)
							|| menuId.equals(WebAppConst.PENDING_MENU_ID)
							|| menuId
									.equals(WebAppConst.SECOND_SCORING_MENU_ID) || menuId
								.equals(WebAppConst.CHECKING_MENU_ID))
					/*
					 * && ((scorerWiseQuestionsSpecificMap.get(
					 * scorerQuestionKey).getIsSaitensha() ==
					 * WebAppConst.ENABLE))
					 */) {
						questionMap.put(questionMapKey.toString(),
								subjectShortName);
					} else if ((menuId.equals(WebAppConst.MISMATCH_MENU_ID)
							|| menuId.equals(WebAppConst.NO_GRADE_MENU_ID) || menuId
								.equals(WebAppConst.OUT_BOUNDARY_MENU_ID))
					/*
					 * && ((scorerWiseQuestionsSpecificMap.get(
					 * scorerQuestionKey).getIsKantokusha() ==
					 * WebAppConst.ENABLE))
					 */) {
						questionMap.put(questionMapKey.toString(),
								subjectShortName);
					} else if ((menuId
							.equals(WebAppConst.FIRST_SCORING_QUALITY_CHECK_MENU_ID) /*
																					 * &&
																					 * (
																					 * (
																					 * scorerWiseQuestionsSpecificMap
																					 * .
																					 * get
																					 * (
																					 * scorerQuestionKey
																					 * )
																					 * .
																					 * getIsSaitensha
																					 * (
																					 * )
																					 * ==
																					 * WebAppConst
																					 * .
																					 * ENABLE
																					 * )
																					 * )
																					 */)) {
						questionMap.put(questionMapKey.toString(),
								subjectShortName);

					}
				} else {
					questionMap
							.put(questionMapKey.toString(), subjectShortName);
				}

			}

		}
		return questionMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.saiten.service.QuestionSelectionService#findHistoryRecordCount(java
	 * .lang.String, int, java.lang.String, java.util.List)
	 */
	@Override
	public int findHistoryRecordCount(String scorerId,
			List<Integer> questionSeq, String connectionString,
			List<Short> scoringStateList) {
		try {

			// Get historyRecordCount for current date
			boolean bookmarkScreenFlag = false;
			return tranDescScoreHistoryDAO.findHistoryRecordCount(scorerId,
					questionSeq, connectionString, scoringStateList,
					bookmarkScreenFlag);
		} catch (HibernateException he) {
			throw new SaitenRuntimeException(
					ErrorCode.QUESTION_SELECTION_HIBERNATE_EXCEPTION, he);
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.QUESTION_SELECTION_SERVICE_EXCEPTION, e);
		}
	}

	@Override
	public int findQcHistoryRecordCount(String scorerId,
			List<Integer> questionSeq, String connectionString,
			List<Short> scoringStateList) {
		try {

			// Get historyRecordCount for current date
			boolean bookmarkScreenFlag = false;
			return tranQualitycheckScoreDAO.findQcHistoryRecordCount(scorerId,
					questionSeq, connectionString, scoringStateList,
					bookmarkScreenFlag);
		} catch (HibernateException he) {
			throw new SaitenRuntimeException(
					ErrorCode.QUESTION_SELECTION_HIBERNATE_EXCEPTION, he);
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.QUESTION_SELECTION_SERVICE_EXCEPTION, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saiten.service.QuestionSelectionService#findCheckPoints(int)
	 */
	@Override
	public List<CheckPointInfo> findCheckPoints(int questionSeq) {
		List<CheckPointInfo> checkPointList = null;

		try {
			LinkedHashMap<Integer, MstQuestion> mstQuestionMap = SaitenUtil
					.getSaitenConfigObject().getMstQuestionMap();

			// Get Set<MstCheckPoint> from saitenConfigObject
			Set<MstCheckPoint> mstCheckPoints = new LinkedHashSet<MstCheckPoint>();
			MstQuestion mstQuestion = mstQuestionMap.get(questionSeq);
			if (mstQuestion != null) {
				mstCheckPoints = mstQuestion.getMstCheckPoints();
			}

			if (!mstCheckPoints.isEmpty()) {
				// Build checkPointList for selected question
				checkPointList = new ArrayList<CheckPointInfo>();

				for (MstCheckPoint mstCheckPoint : mstCheckPoints) {
					CheckPointInfo checkPointInfo = new CheckPointInfo();

					checkPointInfo.setCheckPoint(mstCheckPoint.getCheckPoint());

					MstCheckPointGroup mstCheckPointGroup = mstCheckPoint
							.getMstCheckPointGroup();
					if (mstCheckPointGroup != null) {
						checkPointInfo.setCheckPointGroupSeq(mstCheckPointGroup
								.getCheckPointGroupSeq());
						checkPointInfo.setGroupType(mstCheckPointGroup
								.getGroupType());
						checkPointInfo.setGroupId(mstCheckPointGroup
								.getGroupId());
						checkPointInfo.setBgColor(mstCheckPointGroup
								.getBgColor());
					}

					checkPointInfo.setCheckPointDescription(mstCheckPoint
							.getCheckPointDescription());

					checkPointList.add(checkPointInfo);
				}
			}
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.QUESTION_SELECTION_SERVICE_EXCEPTION, e);
		}

		return checkPointList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.saiten.service.QuestionSelectionService#findPendingCategories(int)
	 */
	@Override
	public Map<Integer, String> findPendingCategories(int questionSeq) {
		Map<Integer, String> pendingCategoryGroupMap = null;

		try {
			LinkedHashMap<Integer, MstQuestion> mstQuestionMap = SaitenUtil
					.getSaitenConfigObject().getMstQuestionMap();

			// Get Set<MstPendingCategory> from saitenConfigObject
			Set<MstPendingCategory> mstPendingCategorySet = mstQuestionMap.get(
					questionSeq).getMstPendingCategories();

			if (!mstPendingCategorySet.isEmpty()) {
				// Build pendingCategoryGroupMap for selected question
				pendingCategoryGroupMap = new LinkedHashMap<Integer, String>();

				for (MstPendingCategory mstPendingCategory : mstPendingCategorySet) {

					StringBuilder pendingCategoryDescription = new StringBuilder();

					pendingCategoryDescription.append(mstPendingCategory
							.getPendingCategory());
					pendingCategoryDescription.append(WebAppConst.DOT);
					pendingCategoryDescription.append(mstPendingCategory
							.getPendingDescription());

					pendingCategoryGroupMap.put(
							mstPendingCategory.getPendingCategorySeq(),
							pendingCategoryDescription.toString());
				}
			}
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.QUESTION_SELECTION_SERVICE_EXCEPTION, e);
		}

		return pendingCategoryGroupMap;
	}

	/**
	 * @param mstScorerQuestionDAO
	 */
	public void setMstScorerQuestionDAO(
			MstScorerQuestionDAO mstScorerQuestionDAO) {
		this.mstScorerQuestionDAO = mstScorerQuestionDAO;
	}

	/**
	 * @param mstQuestionDAO
	 */
	public void setMstQuestionDAO(MstQuestionDAO mstQuestionDAO) {
		this.mstQuestionDAO = mstQuestionDAO;
	}

	public MstQuestionDAO getMstQuestionDAO() {
		return mstQuestionDAO;
	}

	/**
	 * @param tranDescScoreHistoryDAO
	 */
	public void setTranDescScoreHistoryDAO(
			TranDescScoreHistoryDAO tranDescScoreHistoryDAO) {
		this.tranDescScoreHistoryDAO = tranDescScoreHistoryDAO;
	}

	/**
	 * @param session
	 *            the session to set
	 */
	public void setSession(Map<String, Object> session) {
	}

	/**
	 * @param tranQualitycheckScoreDAO
	 *            the tranQualitycheckScoreDAO to set
	 */
	public void setTranQualitycheckScoreDAO(
			TranQualitycheckScoreDAO tranQualitycheckScoreDAO) {
		this.tranQualitycheckScoreDAO = tranQualitycheckScoreDAO;
	}
}