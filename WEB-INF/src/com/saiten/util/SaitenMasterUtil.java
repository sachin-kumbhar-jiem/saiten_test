/**
 * 
 */
package com.saiten.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.web.context.ContextLoader;

import com.saiten.dao.MstQuestionDAO;
import com.saiten.dao.MstScorerQuestionDAO;
import com.saiten.dao.MstTestsetnumQuestionDAO;
import com.saiten.dao.TranScorerAccessLogDAO;
import com.saiten.dao.TranScorerSessionInfoDAO;
import com.saiten.info.ScorerAccessLogInfo;
import com.saiten.manager.SaitenTransactionManager;
import com.saiten.model.MstCheckPoint;
import com.saiten.model.MstCheckPointGroup;
import com.saiten.model.MstDbInstance;
import com.saiten.model.MstEvaluation;
import com.saiten.model.MstGrade;
import com.saiten.model.MstGradeResult;
import com.saiten.model.MstGradeResultId;
import com.saiten.model.MstPendingCategory;
import com.saiten.model.MstQuestion;
import com.saiten.model.MstQuestionType;
import com.saiten.model.MstSubject;
import com.saiten.model.MstTestsetnumQuestion;
import com.saiten.model.TranScorerAccessLog;
import com.saiten.model.TranScorerSessionInfo;
import com.saiten.service.ScorerLoggingService;

/**
 * @author user
 * 
 */
public class SaitenMasterUtil {

	private static Logger log = Logger.getLogger(SaitenMasterUtil.class);

	private SaitenTransactionManager saitenTransactionManager;

	private MstScorerQuestionDAO mstScorerQuestionDAO;

	private MstQuestionDAO mstQuestionDAO;

	private MstTestsetnumQuestionDAO mstTestsetnumQuestionDAO;

	private TranScorerAccessLogDAO tranScorerAccessLogDAO;

	private TranScorerSessionInfoDAO tranScorerSessionInfoDAO;

	public Map<String, List<Integer>> buildMstScorerQuestionMapByScorerId(
			String scorerId) {
		PlatformTransactionManager platformTransactionManager = null;
		TransactionStatus transactionStatus = null;
		Map<String, List<Integer>> mstScorerQuestionMap = new LinkedHashMap<String, List<Integer>>();
		try {
			platformTransactionManager = getSaitenTransactionManager()
					.getTransactionManger();
			transactionStatus = getSaitenTransactionManager().beginTransaction(
					platformTransactionManager);
			List<Integer> questionSeqList = getMstScorerQuestionDAO()
					.getQuestionSequencesByScorerId(scorerId);
			mstScorerQuestionMap.put(scorerId, questionSeqList);
			platformTransactionManager.commit(transactionStatus);
		} catch (Exception e) {
			if (platformTransactionManager != null)
				platformTransactionManager.rollback(transactionStatus);

			log.error("============ Building MstScorerQuestionMapByScorerId Error ============");
			log.error(e.getCause());
		}
		return mstScorerQuestionMap;
	}

	/*
	 * @SuppressWarnings({ "unchecked", "rawtypes" }) public
	 * LinkedHashMap<String, HashMap> buildMstScorerQuestionMap() {
	 * LinkedHashMap<String, LinkedHashMap<Integer, MstQuestion>>
	 * mstScorerQuestionMap = new LinkedHashMap<String, LinkedHashMap<Integer,
	 * MstQuestion>>(); LinkedHashMap<String, ArrayList<MstQuestion>>
	 * scorerWiseMstQuestionMap = new LinkedHashMap<String,
	 * ArrayList<MstQuestion>>(); LinkedHashMap<ScorerQuestionKey,
	 * ScorerWiseQuestionsSpecificFlags> scorerWiseQuestionsMap = new
	 * LinkedHashMap<ScorerQuestionKey, ScorerWiseQuestionsSpecificFlags>();
	 * LinkedHashMap<String, HashMap> mstScorerQuestionsAndFlagsMap = new
	 * LinkedHashMap<String, HashMap>(); PlatformTransactionManager
	 * platformTransactionManager = null; TransactionStatus transactionStatus =
	 * null; try { platformTransactionManager = getSaitenTransactionManager()
	 * .getTransactionManger(); transactionStatus =
	 * getSaitenTransactionManager().beginTransaction(
	 * platformTransactionManager);
	 * 
	 * List<MstScorerQuestion> mstScorerQuestionList = getMstScorerQuestionDAO()
	 * .findByScorerId(SaitenUtil.getLoggedinScorerId()); if
	 * (!mstScorerQuestionList.isEmpty()) { for (MstScorerQuestion
	 * mstScorerQuestion : mstScorerQuestionList) { String scorerId =
	 * mstScorerQuestion.getId().getScorerId(); if
	 * (scorerWiseMstQuestionMap.isEmpty() ||
	 * !scorerWiseMstQuestionMap.containsKey(scorerId)) { ArrayList<MstQuestion>
	 * mstQuestionListObj = new ArrayList<MstQuestion>();
	 * mstQuestionListObj.add(mstScorerQuestion .getMstQuestion());
	 * 
	 * scorerWiseMstQuestionMap.put(scorerId, mstQuestionListObj);
	 * ScorerQuestionKey scorerQuestionKey = new ScorerQuestionKey();
	 * scorerQuestionKey.setScorerId(mstScorerQuestion.getId() .getScorerId());
	 * scorerQuestionKey.setQuestionSeq(mstScorerQuestion
	 * .getMstQuestion().getQuestionSeq()); ScorerWiseQuestionsSpecificFlags
	 * scorerWiseQuestionsSpecificFlags = new
	 * ScorerWiseQuestionsSpecificFlags(); scorerWiseQuestionsSpecificFlags
	 * .setIsSaitensha(mstScorerQuestion .getIsSaitensha());
	 * scorerWiseQuestionsSpecificFlags .setIsKantokusha(mstScorerQuestion
	 * .getIsKantokusha()); scorerWiseQuestionsMap.put(scorerQuestionKey,
	 * scorerWiseQuestionsSpecificFlags); mstScorerQuestionsAndFlagsMap.put(
	 * "scorerWiseQuestionsMap", scorerWiseQuestionsMap); } else if
	 * (scorerWiseMstQuestionMap.containsKey(scorerId)) { ArrayList<MstQuestion>
	 * mstQuestionListObj = (ArrayList<MstQuestion>) scorerWiseMstQuestionMap
	 * .get(scorerId).clone(); mstQuestionListObj.add(mstScorerQuestion
	 * .getMstQuestion()); scorerWiseMstQuestionMap.put(scorerId,
	 * mstQuestionListObj); ScorerQuestionKey scorerQuestionKey = new
	 * ScorerQuestionKey();
	 * scorerQuestionKey.setScorerId(mstScorerQuestion.getId() .getScorerId());
	 * scorerQuestionKey.setQuestionSeq(mstScorerQuestion
	 * .getMstQuestion().getQuestionSeq()); ScorerWiseQuestionsSpecificFlags
	 * scorerWiseQuestionsSpecificFlags = new
	 * ScorerWiseQuestionsSpecificFlags(); scorerWiseQuestionsSpecificFlags
	 * .setIsSaitensha(mstScorerQuestion .getIsSaitensha());
	 * scorerWiseQuestionsSpecificFlags .setIsKantokusha(mstScorerQuestion
	 * .getIsKantokusha()); scorerWiseQuestionsMap.put(scorerQuestionKey,
	 * scorerWiseQuestionsSpecificFlags); mstScorerQuestionsAndFlagsMap.put(
	 * "scorerWiseQuestionsMap", scorerWiseQuestionsMap); } }
	 * 
	 * for (String scorerId : scorerWiseMstQuestionMap.keySet()) {
	 * List<MstQuestion> mstQuestionListObj = scorerWiseMstQuestionMap
	 * .get(scorerId); LinkedHashMap<Integer, MstQuestion> mstQuestionMap =
	 * buildMstQuestionMap(mstQuestionListObj); if
	 * (mstScorerQuestionMap.isEmpty() ||
	 * !mstScorerQuestionMap.containsKey(scorerId)) {
	 * mstScorerQuestionMap.put(scorerId, mstQuestionMap); } }
	 * 
	 * }
	 * 
	 * } catch (Exception e) { if (platformTransactionManager != null)
	 * platformTransactionManager.rollback(transactionStatus);
	 * 
	 * log.error("============ Building MstScorerQuestionMap Error ============")
	 * ; log.error(e.getCause()); }
	 * mstScorerQuestionsAndFlagsMap.put("mstScorerQuestionMap",
	 * mstScorerQuestionMap); return mstScorerQuestionsAndFlagsMap; }
	 */

	// this method keep public because it is using in UpdateMasterDataMaps.java
	public LinkedHashMap<Integer, MstQuestion> buildMstQuestionMap(
			List<MstQuestion> mstQuestionListObj) {
		LinkedHashMap<Integer, MstQuestion> mstQuestionMap = null;
		List<MstQuestion> mstQuestionList = null;

		PlatformTransactionManager platformTransactionManager = null;
		TransactionStatus transactionStatus = null;
		try {

			if (mstQuestionListObj == null) {
				platformTransactionManager = getSaitenTransactionManager()
						.getTransactionManger();
				transactionStatus = getSaitenTransactionManager()
						.beginTransaction(platformTransactionManager);

				mstQuestionList = getMstQuestionDAO().findAll();
			} else {
				mstQuestionList = mstQuestionListObj;
			}

			if (mstQuestionList != null) {
				mstQuestionMap = new LinkedHashMap<Integer, MstQuestion>();
				for (MstQuestion mstQuestion : mstQuestionList) {

					Set<MstCheckPoint> mstCheckPointsSet = mstQuestion
							.getMstCheckPoints();
					// Build mstCheckPoints set
					Set<MstCheckPoint> mstCheckPoints = createMstCheckPointsSet(mstCheckPointsSet);

					Set<MstPendingCategory> mstPendingCategoriesSet = mstQuestion
							.getMstPendingCategories();
					// Build mstPendingCategories set
					Set<MstPendingCategory> mstPendingCategories = createMstPendingCategoriesSet(mstPendingCategoriesSet);

					Set<MstGrade> mstGradeSet = mstQuestion.getMstGrades();
					// Build mstGrade set
					Set<MstGrade> mstGrades = createMstGradeSet(mstGradeSet);

					// Build mstQuestionObj
					MstQuestion mstQuestionObj = createMstQuestionObj(
							mstQuestion, mstCheckPoints, mstPendingCategories,
							mstGrades);

					mstQuestionMap.put(mstQuestion.getQuestionSeq(),
							mstQuestionObj);
				}
			}
			if (mstQuestionListObj == null) {
				platformTransactionManager.commit(transactionStatus);
			}
		} catch (Exception e) {
			if (platformTransactionManager != null)
				platformTransactionManager.rollback(transactionStatus);

			log.error("============ Building MstQuestionMap Error ============");
			log.error(e.getCause());
		}
		return mstQuestionMap;
	}

	/**
	 * @param mstCheckPointsSet
	 * @return Set<MstCheckPoint>
	 */
	private Set<MstCheckPoint> createMstCheckPointsSet(
			Set<MstCheckPoint> mstCheckPointsSet) {

		// Build mstCheckPoints comparator
		Set<MstCheckPoint> mstCheckPoints = buildMstCheckPointsComparator();

		for (MstCheckPoint mstCheckPoint : mstCheckPointsSet) {

			// Build mstCheckPoint object if delete_flag is false
			if (mstCheckPoint.getDeleteFlag() == WebAppConst.DELETE_FLAG) {
				MstCheckPointGroup checkPointGroup = mstCheckPoint
						.getMstCheckPointGroup();

				MstCheckPointGroup checkPointGroupObj = null;
				if (checkPointGroup.getDeleteFlag() == WebAppConst.DELETE_FLAG) {
					// Build checkPointGroupObj if delete_flag is false
					checkPointGroupObj = new MstCheckPointGroup(
							checkPointGroup.getCheckPointGroupSeq(),
							checkPointGroup.getGroupId(),
							checkPointGroup.getGroupType(),
							checkPointGroup.getDeleteFlag(),
							checkPointGroup.getUpdatePersonId(),
							checkPointGroup.getUpdateDate(),
							checkPointGroup.getCreateDate(),
							checkPointGroup.getBgColor(), null);

				}

				MstCheckPoint mstCheckPointObj = new MstCheckPoint(
						mstCheckPoint.getCheckPointSeq(), null,
						checkPointGroupObj, mstCheckPoint.getCheckPoint(),
						mstCheckPoint.getCheckPointDescription(),
						mstCheckPoint.getDeleteFlag(),
						mstCheckPoint.getUpdatePersonId(),
						mstCheckPoint.getUpdateDate(),
						mstCheckPoint.getCreateDate());

				// Add mstCheckPointObj in Set<MstCheckPoint>
				mstCheckPoints.add(mstCheckPointObj);
			}

		}

		return mstCheckPoints;

	}

	private TreeSet<MstCheckPoint> buildMstCheckPointsComparator() {
		return new TreeSet<MstCheckPoint>(new Comparator<MstCheckPoint>() {

			@Override
			public int compare(MstCheckPoint checkPoint1,
					MstCheckPoint checkPoint2) {
				return checkPoint1.getCheckPoint().compareTo(
						checkPoint2.getCheckPoint());
			}

		});
	}

	/**
	 * @param mstPendingCategoriesSet
	 * @return Set<MstPendingCategory>
	 */
	private Set<MstPendingCategory> createMstPendingCategoriesSet(
			Set<MstPendingCategory> mstPendingCategoriesSet) {

		// Build mstPendingCategories comparator
		Set<MstPendingCategory> mstPendingCategories = buildMstPendingCategoriesComparator();

		for (MstPendingCategory mstPendingCategory : mstPendingCategoriesSet) {

			// Build mstPendingCategory if delete_flag & valid_flag are false
			if (mstPendingCategory.getDeleteFlag() == WebAppConst.DELETE_FLAG
					&& mstPendingCategory.getValidFlag() == WebAppConst.VALID_FLAG) {
				MstPendingCategory mstPendingCategoryObj = new MstPendingCategory(
						mstPendingCategory.getPendingCategorySeq(), null,
						mstPendingCategory.getPendingCategory(),
						mstPendingCategory.getPendingDescription(),
						mstPendingCategory.getDisplayIndex(),
						mstPendingCategory.getValidFlag(),
						mstPendingCategory.getDeleteFlag(),
						mstPendingCategory.getUpdatePersonId(),
						mstPendingCategory.getUpdateDate(),
						mstPendingCategory.getCreateDate());

				mstPendingCategories.add(mstPendingCategoryObj);
			}
		}

		return mstPendingCategories;
	}

	private TreeSet<MstPendingCategory> buildMstPendingCategoriesComparator() {
		return new TreeSet<MstPendingCategory>(
				new Comparator<MstPendingCategory>() {

					@Override
					public int compare(MstPendingCategory pendingCategory1,
							MstPendingCategory pendingCategory2) {
						return pendingCategory1.getDisplayIndex().compareTo(
								pendingCategory2.getDisplayIndex());
					}

				});
	}

	private Set<MstGrade> createMstGradeSet(Set<MstGrade> mstGradeSet) {
		Set<MstGrade> mstGrades = buildMstGradeComparator();

		for (MstGrade mstGrade : mstGradeSet) {
			// build MstGrade if delete_flag = false
			if (mstGrade.getDeleteFlag() == WebAppConst.DELETE_FLAG) {

				MstGradeResultId mstGradeResultIdObj = mstGrade
						.getMstGradeResult().getId();
				MstGradeResultId mstGradeResultId = new MstGradeResultId(
						mstGradeResultIdObj.getQuestionSeq(),
						mstGradeResultIdObj.getGradeNum());

				MstGradeResult mstGradeResultobj = mstGrade.getMstGradeResult();
				MstGradeResult mstGradeResult = new MstGradeResult(
						mstGradeResultId,
						mstGradeResultobj.getGradeDescription(),
						mstGradeResultobj.getResult(),
						mstGradeResultobj.getDeleteFlag(),
						mstGradeResultobj.getUpdatePersonId(),
						mstGradeResultobj.getUpdateDate(),
						mstGradeResultobj.getCreateDate(), mstGrades);

				MstGrade mstGradeObj = new MstGrade(mstGrade.getGradeSeq(),
						mstGradeResult, mstGrade.getMstQuestion(),
						mstGrade.getBitValue(), mstGrade.getDeleteFlag(),
						mstGrade.getUpdatePersonId(), mstGrade.getUpdateDate(),
						mstGrade.getCreateDate());

				mstGrades.add(mstGradeObj);
			}
		}

		return mstGrades;
	}

	private TreeSet<MstGrade> buildMstGradeComparator() {
		return new TreeSet<MstGrade>(new Comparator<MstGrade>() {

			@Override
			public int compare(MstGrade mstGrade1, MstGrade mstGrade2) {
				return ((Integer) mstGrade1.getGradeSeq())
						.compareTo((Integer) mstGrade2.getGradeSeq());
			}
		});
	}

	/**
	 * @param mstQuestion
	 * @param mstCheckPoints
	 * @param mstPendingCategories
	 * @return MstQuestion
	 */
	private MstQuestion createMstQuestionObj(MstQuestion mstQuestion,
			Set<MstCheckPoint> mstCheckPoints,
			Set<MstPendingCategory> mstPendingCategories,
			Set<MstGrade> mstGrades) {

		MstSubject mstSubjectObj = mstQuestion.getMstSubject();

		// Build mstSubject object
		MstSubject mstSubject = createMstSubjectObj(mstSubjectObj);

		// Get mstEvaluation object if delete_flag is false
		MstEvaluation mstEvaluation = mstQuestion.getMstEvaluation();

		if (mstEvaluation.getDeleteFlag() == WebAppConst.DELETE_FLAG) {
			mstEvaluation = createMstEvaluation(mstEvaluation);
		} else {
			mstEvaluation = null;
		}

		// Get mstDbInstance object if delete_flag is false
		MstDbInstance mstDbInstanceObj = mstQuestion.getMstDbInstance();

		// Build mstDbInstance object
		MstDbInstance mstDbInstance = createMstDbInstanceObj(mstDbInstanceObj);

		mstDbInstance = mstDbInstance.getDeleteFlag() == WebAppConst.DELETE_FLAG ? mstDbInstance
				: null;

		// Build mstQuestionObj
		MstQuestion mstQuestionObj = new MstQuestion(
				mstQuestion.getQuestionSeq(), mstSubject, mstEvaluation,
				mstDbInstance, mstQuestion.getQuestionNum(),
				mstQuestion.getQuestionContents(), mstQuestion.getSide(),
				mstQuestion.getImageHeight(), mstQuestion.getImageWidth(),
				mstQuestion.getManualFileName(),
				mstQuestion.getQuestionFileName(),
				mstQuestion.getObjectiveMax(), mstQuestion.getDeleteFlag(),
				mstQuestion.getUpdatePersonId(), mstQuestion.getUpdateDate(),
				mstQuestion.getCreateDate(),
				mstQuestion.getMstScorerQuestions(), mstCheckPoints,
				mstPendingCategories, mstGrades, mstQuestion.getAttribute1());

		return mstQuestionObj;
	}

	/**
	 * @param mstSubjectObj
	 * @return MstSubject
	 */
	private MstSubject createMstSubjectObj(MstSubject mstSubjectObj) {
		MstSubject mstSubject = null;
		if (mstSubjectObj.getDeleteFlag() == WebAppConst.DELETE_FLAG) {
			// Build mstSubjectObj if delete_flag is false
			mstSubject = new MstSubject(mstSubjectObj.getSubjectCode(),
					mstSubjectObj.getSchoolType(),
					mstSubjectObj.getSubjectShortName(),
					mstSubjectObj.getSubjectName(),
					mstSubjectObj.getScoreFlag(), mstSubjectObj.getDocFlag(),
					mstSubjectObj.getDeleteFlag(),
					mstSubjectObj.getUpdatePersonId(),
					mstSubjectObj.getUpdateDate(),
					mstSubjectObj.getCreateDate(),
					mstSubjectObj.getMstQuestions());
		}

		return mstSubject;
	}

	private MstEvaluation createMstEvaluation(MstEvaluation mstEvaluationObj) {
		MstEvaluation mstEvaluation = null;

		MstQuestionType mstQuestionType = mstEvaluationObj.getMstQuestionType();
		if (mstQuestionType.getDeleteFlag() == WebAppConst.DELETE_FLAG) {
			mstQuestionType = createMstQuestionType(mstQuestionType);
		} else {
			mstQuestionType = null;
		}

		mstEvaluation = new MstEvaluation(mstEvaluationObj.getEvalSeq(),
				mstQuestionType, mstEvaluationObj.getCsvQuestionType(),
				mstEvaluationObj.getScoreType(),
				mstEvaluationObj.getDeleteFlag(),
				mstEvaluationObj.getUpdatePersonId(),
				mstEvaluationObj.getUpdateDate(),
				mstEvaluationObj.getCreateDate(),
				mstEvaluationObj.getMstQuestions());

		return mstEvaluation;
	}

	private MstDbInstance createMstDbInstanceObj(MstDbInstance mstDbInstanceObj) {
		MstDbInstance mstDbInstance = null;
		if (mstDbInstanceObj.getDeleteFlag() == WebAppConst.DELETE_FLAG) {
			// Build mstDbInstanceObj if delete_flag is false
			mstDbInstance = new MstDbInstance(
					mstDbInstanceObj.getDbInstanceSeq(),
					mstDbInstanceObj.getConnectionString(),
					mstDbInstanceObj.getDeleteFlag(),
					mstDbInstanceObj.getUpdatePersonId(),
					mstDbInstanceObj.getCreateDate(),
					mstDbInstanceObj.getUpdateDate(),
					mstDbInstanceObj.getMstQuestions());
		}

		return mstDbInstance;
	}

	private MstQuestionType createMstQuestionType(
			MstQuestionType mstQuestionTypeObj) {
		MstQuestionType mstQuestionType = null;

		mstQuestionType = new MstQuestionType(
				mstQuestionTypeObj.getQuestionType(),
				mstQuestionTypeObj.getAnswerScoreTable(),
				mstQuestionTypeObj.getAnswerScoreHistoryTable(),
				mstQuestionTypeObj.getDeleteFlag(),
				mstQuestionTypeObj.getUpdatePersonId(),
				mstQuestionTypeObj.getUpdateDate(),
				mstQuestionTypeObj.getCreateDate(),
				mstQuestionTypeObj.getMstEvaluations());

		return mstQuestionType;
	}

	public void updateUserLoggingInformation(
			ScorerAccessLogInfo scorerAccessLogInfo) {
		PlatformTransactionManager platformTransactionManager = null;
		TransactionStatus transactionStatus = null;
		try {
			platformTransactionManager = getSaitenTransactionManager()
					.getTransactionManger();
			transactionStatus = getSaitenTransactionManager().beginTransaction(
					platformTransactionManager);

			// Get current WebApp Context
			ApplicationContext ctx = ContextLoader
					.getCurrentWebApplicationContext();

			ScorerLoggingService scorerLoggingService = (ScorerLoggingService) ctx
					.getBean("scorerLoggingService");
			scorerLoggingService.saveOrUpdate(scorerAccessLogInfo);
			platformTransactionManager.commit(transactionStatus);
		} catch (Exception e) {
			if (platformTransactionManager != null)
				platformTransactionManager.rollback(transactionStatus);
		}

	}

	public String findUserStatusById(Integer id) {
		return getTranScorerAccessLogDAO().findStatusById(id);
	}

	public void updateUserSessionInfo(
			TranScorerSessionInfo tranScorerSessionInfo) {
		PlatformTransactionManager platformTransactionManager = null;
		TransactionStatus transactionStatus = null;
		try {
			platformTransactionManager = getSaitenTransactionManager()
					.getTransactionManger();
			transactionStatus = getSaitenTransactionManager().beginTransaction(
					platformTransactionManager);
			getTranScorerSessionInfoDAO().saveOrUpdate(tranScorerSessionInfo);
			platformTransactionManager.commit(transactionStatus);
		} catch (Exception e) {
			if (platformTransactionManager != null)
				platformTransactionManager.rollback(transactionStatus);
		}

	}

	public TranScorerSessionInfo getUserSessionInfoById(String scorerId) {
		return getTranScorerSessionInfoDAO().findById(scorerId);
	}

	public Long getUsersByAnswerFormNumAndSubjectCode(String answerFormNum,
			String subjectCode) {
		return getTranScorerSessionInfoDAO()
				.countScorersByAnswerFormNumAndSubjectCode(answerFormNum,
						subjectCode);
	}

	public List<ScorerAccessLogInfo> getAllLoggedInScorers(Integer id,
			String scorerId) {
		List<ScorerAccessLogInfo> scorerAccessLogInfoList = new ArrayList<ScorerAccessLogInfo>();
		List<TranScorerAccessLog> tranScorerAccessLogList = getTranScorerAccessLogDAO()
				.getAllLoggedInScorers(id, scorerId);
		if (!tranScorerAccessLogList.isEmpty()) {
			for (TranScorerAccessLog tranScorerAccessLog : tranScorerAccessLogList) {
				ScorerAccessLogInfo scorerAccessLogInfo = new ScorerAccessLogInfo();
				scorerAccessLogInfo.setId(tranScorerAccessLog.getId());
				scorerAccessLogInfo.setScorerId(tranScorerAccessLog
						.getMstScorer().getScorerId());
				scorerAccessLogInfo.setLoginTime(tranScorerAccessLog
						.getLoginTime());
				scorerAccessLogInfo.setLogoutTime(tranScorerAccessLog
						.getLogoutTime());
				scorerAccessLogInfo.setStatus(tranScorerAccessLog.getStatus());
				scorerAccessLogInfoList.add(scorerAccessLogInfo);
			}
		}
		return scorerAccessLogInfoList;
	}

	public LinkedHashMap<Integer, MstTestsetnumQuestion> buildMstTestsetnumQuestionMap() {

		LinkedHashMap<Integer, MstTestsetnumQuestion> mstTestsetnumQuestionMap = null;
		List<MstTestsetnumQuestion> mstTestsetnumQuestionList = null;

		PlatformTransactionManager platformTransactionManager = null;
		TransactionStatus transactionStatus = null;

		try {
			platformTransactionManager = getSaitenTransactionManager()
					.getTransactionManger();
			transactionStatus = getSaitenTransactionManager().beginTransaction(
					platformTransactionManager);
			mstTestsetnumQuestionList = getMstTestsetnumQuestionDAO().findAll();
			if (mstTestsetnumQuestionList != null) {
				mstTestsetnumQuestionMap = new LinkedHashMap<Integer, MstTestsetnumQuestion>();
				for (MstTestsetnumQuestion mstTestsetnumQuestion : mstTestsetnumQuestionList) {
					mstTestsetnumQuestionMap.put(
							mstTestsetnumQuestion.getTestsetnumSeq(),
							mstTestsetnumQuestion);
				}
			}
			platformTransactionManager.commit(transactionStatus);
			log.info("============ Building MstTestsetnumQuestionMap Succeess ============");

		} catch (Exception e) {
			if (platformTransactionManager != null)
				platformTransactionManager.rollback(transactionStatus);

			log.error("============ Building MstTestsetnumQuestionMap Error ============");
			log.error(e.getCause());
		}

		return mstTestsetnumQuestionMap;
	}

	/**
	 * 
	 * @param saitenTransactionManager
	 */
	public void setSaitenTransactionManager(
			SaitenTransactionManager saitenTransactionManager) {
		this.saitenTransactionManager = saitenTransactionManager;
	}

	public SaitenTransactionManager getSaitenTransactionManager() {
		return saitenTransactionManager;
	}

	public MstScorerQuestionDAO getMstScorerQuestionDAO() {
		return mstScorerQuestionDAO;
	}

	public void setMstScorerQuestionDAO(
			MstScorerQuestionDAO mstScorerQuestionDAO) {
		this.mstScorerQuestionDAO = mstScorerQuestionDAO;
	}

	/**
	 * 
	 * @param mstQuestionDAO
	 */
	public void setMstQuestionDAO(MstQuestionDAO mstQuestionDAO) {
		this.mstQuestionDAO = mstQuestionDAO;
	}

	public MstQuestionDAO getMstQuestionDAO() {
		return mstQuestionDAO;
	}

	public MstTestsetnumQuestionDAO getMstTestsetnumQuestionDAO() {
		return mstTestsetnumQuestionDAO;
	}

	public void setMstTestsetnumQuestionDAO(
			MstTestsetnumQuestionDAO mstTestsetnumQuestionDAO) {
		this.mstTestsetnumQuestionDAO = mstTestsetnumQuestionDAO;
	}

	/**
	 * @return the tranScorerAccessLogDAO
	 */
	public TranScorerAccessLogDAO getTranScorerAccessLogDAO() {
		return tranScorerAccessLogDAO;
	}

	/**
	 * @param tranScorerAccessLogDAO
	 *            the tranScorerAccessLogDAO to set
	 */
	public void setTranScorerAccessLogDAO(
			TranScorerAccessLogDAO tranScorerAccessLogDAO) {
		this.tranScorerAccessLogDAO = tranScorerAccessLogDAO;
	}

	/**
	 * @return the tranScorerSessionInfoDAO
	 */
	public TranScorerSessionInfoDAO getTranScorerSessionInfoDAO() {
		return tranScorerSessionInfoDAO;
	}

	/**
	 * @param tranScorerSessionInfoDAO
	 *            the tranScorerSessionInfoDAO to set
	 */
	public void setTranScorerSessionInfoDAO(
			TranScorerSessionInfoDAO tranScorerSessionInfoDAO) {
		this.tranScorerSessionInfoDAO = tranScorerSessionInfoDAO;
	}

}
