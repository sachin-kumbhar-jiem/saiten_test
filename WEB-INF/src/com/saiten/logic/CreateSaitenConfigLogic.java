package com.saiten.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;

import com.saiten.bean.GradeNumKey;
import com.saiten.bean.GradeResultKey;
import com.saiten.bean.SaitenConfig;
import com.saiten.bean.ScoringStateKey;
import com.saiten.dao.MstGradeDAO;
import com.saiten.dao.MstMarkValueDAO;
import com.saiten.dao.MstPendingCategoryDAO;
import com.saiten.dao.MstScorerDAO;
import com.saiten.dao.MstScoringStateListDAO;
import com.saiten.manager.SaitenTransactionManager;
import com.saiten.model.MstGrade;
import com.saiten.model.MstGradeResult;
import com.saiten.model.MstGradeResultId;
import com.saiten.model.MstQuestion;
import com.saiten.model.MstTestsetnumQuestion;
import com.saiten.util.SaitenMasterUtil;
import com.saiten.util.WebAppConst;

/**
 * @author sachin
 * @version 1.0
 * @created 05-Dec-2012 12:40:12 PM
 */
public class CreateSaitenConfigLogic {

	private static Logger log = Logger.getLogger(CreateSaitenConfigLogic.class);

	private MstGradeDAO mstGradeDAO;

	private MstScoringStateListDAO mstScoringStateListDAO;

	private MstPendingCategoryDAO mstPendingCategoryDAO;

	private SaitenTransactionManager saitenTransactionManager;

	private SaitenConfig saitenConfig;

	private Properties saitenGlobalProperties;

	private SaitenMasterUtil saitenMasterUtil;

	private MstScorerDAO mstScorerDAO;

	private MstMarkValueDAO mstMarkValueDAO;

	public SaitenConfig buildSaitenConfigObject() {

		try {
			saitenConfig = getSaitenConfig();

			// Build mstGradeMap
			LinkedHashMap<GradeResultKey, MstGrade> mstGradeMap = buildMstGradeMap();
			saitenConfig.setMstGradeMap(mstGradeMap);

			LinkedHashMap<Long, String> checkPointsShortCutsMap = buildCheckPointsShortCutMap();
			saitenConfig.setCheckPointsShortCutsMap(checkPointsShortCutsMap);

			// Build mstGradeMap
			LinkedHashMap<ScoringStateKey, List<Short>> historyScoringStatesMap = buildHistoryScoringStatesMap();
			saitenConfig.setHistoryScoringStatesMap(historyScoringStatesMap);

			// Build mstQuestionMap
			List<MstQuestion> mstQuestionListObj = null;
			LinkedHashMap<Integer, MstQuestion> mstQuestionMap = saitenMasterUtil
					.buildMstQuestionMap(mstQuestionListObj);
			saitenConfig.setMstQuestionMap(mstQuestionMap);

			LinkedHashMap<Integer, MstTestsetnumQuestion> mstTestsetnumQuestionMap = saitenMasterUtil
					.buildMstTestsetnumQuestionMap();
			saitenConfig.setMstTestsetnumQuestionMap(mstTestsetnumQuestionMap);

			/*
			 * @SuppressWarnings("rawtypes") LinkedHashMap<String, HashMap>
			 * mstScorerQuestionsAndFlagsMap =
			 * saitenMasterUtil.buildMstScorerQuestionMap(); saitenConfig
			 * .setMstScorerQuestionsAndFlagsMap(mstScorerQuestionsAndFlagsMap);
			 * 
			 * @SuppressWarnings("unchecked") LinkedHashMap<String,
			 * LinkedHashMap<Integer, MstQuestion>> mstScorerQuestionMap =
			 * (LinkedHashMap<String, LinkedHashMap<Integer, MstQuestion>>)
			 * mstScorerQuestionsAndFlagsMap .get("mstScorerQuestionMap");
			 * saitenConfig.setMstScorerQuestionMap(mstScorerQuestionMap);
			 * 
			 * @SuppressWarnings("unchecked") LinkedHashMap<ScorerQuestionKey,
			 * ScorerWiseQuestionsSpecificFlags> scorerWiseQuestionsSpecificMap
			 * = (LinkedHashMap<ScorerQuestionKey,
			 * ScorerWiseQuestionsSpecificFlags>) mstScorerQuestionsAndFlagsMap
			 * .get("scorerWiseQuestionsMap"); saitenConfig
			 * .setScorerWiseQuestionsSpecificMap
			 * (scorerWiseQuestionsSpecificMap);
			 */

			// Build scoringStateMap
			LinkedHashMap<Short, String> scoringStateMap = buildScoringStateMap();
			saitenConfig.setScoringStateMap(scoringStateMap);

			// Build gradeSequenceWiseMstGradeMap
			LinkedHashMap<GradeNumKey, MstGrade> gradeSequenceWiseMstGradeMap = buildGradeSequenceWiseMstGradeMap();
			saitenConfig
					.setGradeSequenceWiseMstGradeMap(gradeSequenceWiseMstGradeMap);

			// Build pendingCategoryMap
			LinkedHashMap<Integer, Short> pendingCategoryMap = buildPendingCategoryMap();
			saitenConfig.setPendingCategoryMap(pendingCategoryMap);

			// Build menuIdAndScoringStateMap
			LinkedHashMap<String, Short> menuIdAndScoringStateMap = buildMenuIdAndScoringStateMap();
			saitenConfig.setMenuIdAndScoringStateMap(menuIdAndScoringStateMap);

			// Build scoringEventMap
			LinkedHashMap<String, Short> scoringEventMap = buildScoringEventMap();
			saitenConfig.setScoringEventMap(scoringEventMap);

			// Build screeIdMap
			LinkedHashMap<String, String> screenIdMap = buildScreenIdMap();
			saitenConfig.setScreenIdMap(screenIdMap);

			LinkedHashMap<Byte, String> mstScorerRoleMap = buildMstScorerRoleMap();
			saitenConfig.setMstScorerRoleMap(mstScorerRoleMap);

			LinkedHashMap<Integer, LinkedHashMap<Integer, Short>> mstMarkValueMap = buildMstMarkValueMap();
			saitenConfig.setMstMarkValueMap(mstMarkValueMap);

		} catch (Exception e) {
			log.error(e.getCause());
		}
		return saitenConfig;
	}

	private LinkedHashMap<GradeResultKey, MstGrade> buildMstGradeMap() {

		LinkedHashMap<GradeResultKey, MstGrade> mstGradeMap = null;
		PlatformTransactionManager platformTransactionManager = null;
		TransactionStatus transactionStatus = null;
		try {
			platformTransactionManager = getSaitenTransactionManager()
					.getTransactionManger();
			transactionStatus = getSaitenTransactionManager().beginTransaction(
					platformTransactionManager);

			List<MstGrade> mstGradeList = getMstGradeDAO().findAll();

			if (!mstGradeList.isEmpty()) {
				mstGradeMap = new LinkedHashMap<GradeResultKey, MstGrade>();

				for (MstGrade mstGrade : mstGradeList) {

					MstGradeResultId mstGradeResultIdObj = mstGrade
							.getMstGradeResult().getId();

					// Build mstGradeResultId object
					MstGradeResultId mstGradeResultId = new MstGradeResultId(
							mstGradeResultIdObj.getQuestionSeq(),
							mstGradeResultIdObj.getGradeNum());

					MstGradeResult mstGradeResultObj = mstGrade
							.getMstGradeResult();

					// Build mstGradeResult object
					MstGradeResult mstGradeResult = createMstGradeResult(
							mstGradeResultId, mstGradeResultObj);

					// Build mstGradeObj object
					MstGrade mstGradeObj = createMstGrade(mstGrade,
							mstGradeResult);

					// Build mstGradeMap key (gradeResultKey)
					GradeResultKey gradeResultKey = new GradeResultKey();
					gradeResultKey.setQuestionSeq(mstGradeResultIdObj
							.getQuestionSeq());
					gradeResultKey.setBitValue(mstGradeObj.getBitValue());

					mstGradeMap.put(gradeResultKey, mstGradeObj);

				}
			}
			platformTransactionManager.commit(transactionStatus);
		} catch (Exception e) {
			if (platformTransactionManager != null)
				platformTransactionManager.rollback(transactionStatus);

			log.error("============ Building MstGradeMap Error ============");
			log.error(e.getCause());
		}
		return mstGradeMap;
	}

	/**
	 * @param mstGradeResultId
	 * @param mstGradeResultObj
	 * @return MstGradeResult
	 */
	private MstGradeResult createMstGradeResult(
			MstGradeResultId mstGradeResultId, MstGradeResult mstGradeResultObj) {
		MstGradeResult mstGradeResult = null;

		// Build mstGradeResult object if delete_flag is false
		if (mstGradeResultObj.getDeleteFlag() == WebAppConst.DELETE_FLAG) {
			mstGradeResult = new MstGradeResult(mstGradeResultId,
					mstGradeResultObj.getGradeDescription(),
					mstGradeResultObj.getResult(),
					mstGradeResultObj.getDeleteFlag(),
					mstGradeResultObj.getUpdatePersonId(),
					mstGradeResultObj.getUpdateDate(),
					mstGradeResultObj.getCreateDate(),
					mstGradeResultObj.getMstGrades());
		}

		return mstGradeResult;
	}

	/**
	 * @param mstGrade
	 * @param mstGradeResult
	 * @return MstGrade
	 */
	private MstGrade createMstGrade(MstGrade mstGrade,
			MstGradeResult mstGradeResult) {
		MstQuestion mstQuestion = mstGrade.getMstQuestion();

		// Get mstQuestion object if delete_flag is false
		mstQuestion = mstQuestion.getDeleteFlag() == WebAppConst.DELETE_FLAG ? mstQuestion
				: null;

		MstGrade mstGradeObj = new MstGrade(mstGrade.getGradeSeq(),
				mstGradeResult, mstQuestion, mstGrade.getBitValue(),
				mstGrade.getDeleteFlag(), mstGrade.getUpdatePersonId(),
				mstGrade.getUpdateDate(), mstGrade.getCreateDate());

		return mstGradeObj;
	}

	private LinkedHashMap<String, Short> buildMenuIdAndScoringStateMap() {
		LinkedHashMap<String, Short> menuIdAndScoringStateMap = null;
		try {
			// Read data from global.properties bean
			String menuIdAndScoringStateData = getSaitenGlobalProperties()
					.getProperty(WebAppConst.MENUID_SCORING_STATE_DATA);

			menuIdAndScoringStateMap = new LinkedHashMap<String, Short>();

			// MenuId and ScoringState e.g. FIRST_SCORING_MENU_ID:121
			String[] menuIdAndScoringStateArray = menuIdAndScoringStateData
					.split(",");

			for (String menuIdAndScoringState : menuIdAndScoringStateArray) {
				String[] menuIdAndScoringStateValues = menuIdAndScoringState
						.split(":");
				menuIdAndScoringStateMap.put(menuIdAndScoringStateValues[0],
						Short.valueOf(menuIdAndScoringStateValues[1]));
			}
		} catch (Exception e) {
			log.error("============ Building MstGradeMap Error =============");
			log.error(e.getCause());
		}

		return menuIdAndScoringStateMap;
	}

	private LinkedHashMap<ScoringStateKey, List<Short>> buildHistoryScoringStatesMap() {
		LinkedHashMap<ScoringStateKey, List<Short>> historyScoringStatesMap = new LinkedHashMap<ScoringStateKey, List<Short>>();
		List<Short> statesList = null;

		try {

			List<String> scoringStatesList = Arrays
					.asList(getSaitenGlobalProperties().getProperty(
							WebAppConst.HISTORY_SCORINGSTATE_MAP_DATA).split(
							WebAppConst.COMMA));
			ScoringStateKey scoringStateKey = null;
			if (scoringStatesList != null && scoringStatesList.size() > 0) {
				for (String scoringStatesKeyValue : scoringStatesList) {
					String[] scoringStatesKeyValueArray = scoringStatesKeyValue
							.split(String.valueOf(WebAppConst.HYPHEN));

					String[] scoringStateKeyArray = scoringStatesKeyValueArray[0]
							.split(WebAppConst.COLON);
					scoringStateKey = new ScoringStateKey();
					scoringStateKey.setMenuId(scoringStateKeyArray[0]);
					scoringStateKey.setNoDbUpdate(scoringStateKeyArray[1]
							.charAt(0));

					String[] scoringStateArray = scoringStatesKeyValueArray[1]
							.split(WebAppConst.COLON);
					statesList = new ArrayList<Short>();
					for (String scoringState : scoringStateArray) {
						statesList.add(Short.parseShort(scoringState));
					}

					historyScoringStatesMap.put(scoringStateKey, statesList);
				}
			}
		} catch (Exception e) {
			log.error("============ Building HistoryScoringStatesMap Error ============");
			log.error(e.getCause());
		}
		return historyScoringStatesMap;
	}

	@SuppressWarnings("rawtypes")
	private LinkedHashMap<Short, String> buildScoringStateMap() {
		LinkedHashMap<Short, String> scoringStateMap = null;
		List scoringStateList = null;
		try {
			// Fetch scoringStateList
			scoringStateList = getMstScoringStateListDAO()
					.findScoringStateList();

			if (scoringStateList != null) {
				scoringStateMap = new LinkedHashMap<Short, String>();
				for (Object object : scoringStateList) {
					Object[] scoringStateInfoObject = (Object[]) object;

					// key - scoringState, value - stateName
					scoringStateMap.put((Short) scoringStateInfoObject[0],
							(String) scoringStateInfoObject[1]);
				}
			}
		} catch (Exception e) {
			log.error("============ Building ScoringStateMap Error ============");
			log.error(e.getCause());
		}
		return scoringStateMap;
	}

	private LinkedHashMap<GradeNumKey, MstGrade> buildGradeSequenceWiseMstGradeMap() {
		LinkedHashMap<GradeNumKey, MstGrade> gradeSequenceWiseMstGradeMap = null;
		List<MstGrade> mstGradeList = null;
		PlatformTransactionManager platformTransactionManager = null;
		TransactionStatus transactionStatus = null;
		try {
			platformTransactionManager = getSaitenTransactionManager()
					.getTransactionManger();
			transactionStatus = getSaitenTransactionManager().beginTransaction(
					platformTransactionManager);

			mstGradeList = getMstGradeDAO().findAll();
			if (mstGradeList != null) {
				gradeSequenceWiseMstGradeMap = new LinkedHashMap<GradeNumKey, MstGrade>();
				GradeNumKey gradeNumKey = null;
				for (MstGrade mstGrade : mstGradeList) {
					gradeNumKey = new GradeNumKey();
					gradeNumKey.setGradeSeq(mstGrade.getGradeSeq());
					gradeNumKey.setQuestionSeq(mstGrade.getMstQuestion()
							.getQuestionSeq());

					MstGradeResultId mstGradeResultIdObj = mstGrade
							.getMstGradeResult().getId();
					MstGradeResultId mstGradeResultId = new MstGradeResultId(
							mstGradeResultIdObj.getQuestionSeq(),
							mstGradeResultIdObj.getGradeNum());

					MstGradeResult mstGradeResultObj = mstGrade
							.getMstGradeResult();
					MstGradeResult mstGradeResult = null;
					if (mstGradeResultObj.getDeleteFlag() == WebAppConst.DELETE_FLAG) {
						mstGradeResult = new MstGradeResult(mstGradeResultId,
								mstGradeResultObj.getGradeDescription(),
								mstGradeResultObj.getResult(),
								mstGradeResultObj.getDeleteFlag(),
								mstGradeResultObj.getUpdatePersonId(),
								mstGradeResultObj.getUpdateDate(),
								mstGradeResultObj.getCreateDate(),
								mstGradeResultObj.getMstGrades());
					}

					MstGrade mstGradeObj = new MstGrade(mstGrade.getGradeSeq(),
							mstGradeResult, mstGrade.getMstQuestion(),
							mstGrade.getBitValue(), mstGrade.getDeleteFlag(),
							mstGrade.getUpdatePersonId(),
							mstGrade.getUpdateDate(), mstGrade.getCreateDate());

					gradeSequenceWiseMstGradeMap.put(gradeNumKey, mstGradeObj);
				}
			}
			platformTransactionManager.commit(transactionStatus);
		} catch (Exception e) {
			if (platformTransactionManager != null)
				platformTransactionManager.rollback(transactionStatus);
			log.error("============ Building GradeSequenceWiseMstGradeMap Error ============");
			log.error(e.getCause());
		}
		return gradeSequenceWiseMstGradeMap;
	}

	@SuppressWarnings("rawtypes")
	private LinkedHashMap<Integer, Short> buildPendingCategoryMap() {
		LinkedHashMap<Integer, Short> pendingCategoryMap = null;
		List pendingCategoryList = null;
		try {
			// Fetch pendingCategoryList
			pendingCategoryList = getMstPendingCategoryDAO()
					.findPendingCategoryList();

			if (pendingCategoryList != null) {
				pendingCategoryMap = new LinkedHashMap<Integer, Short>();
				for (Object object : pendingCategoryList) {
					Object[] pendingCategoryInfoObject = (Object[]) object;

					// key - pendingCategorySeq, value - pendingCategory
					pendingCategoryMap.put(
							(Integer) pendingCategoryInfoObject[0],
							(Short) pendingCategoryInfoObject[1]);
				}
			}
		} catch (Exception e) {
			log.error("============ Building PandingCategoryMap Error ============");
			log.error(e.getCause());
		}
		return pendingCategoryMap;
	}

	private LinkedHashMap<String, Short> buildScoringEventMap() {

		LinkedHashMap<String, Short> scoringEventMap = null;

		try {
			// Fetch data from global.properties bean
			String menuIdAndScoringEventData = getSaitenGlobalProperties()
					.getProperty(WebAppConst.MENUID_SCORING_EVENT_DATA);
			scoringEventMap = new LinkedHashMap<String, Short>();

			// MenuId and ScoringEvent e.g. FIRST_SCORING_MENU_ID:120
			String[] menuIdAndScoringEventArray = menuIdAndScoringEventData
					.split(",");

			for (String menuIdAndScoringEvent : menuIdAndScoringEventArray) {
				String[] menuIdAndScoringEventValues = menuIdAndScoringEvent
						.split(":");
				scoringEventMap.put(menuIdAndScoringEventValues[0],
						Short.valueOf(menuIdAndScoringEventValues[1]));
			}
		} catch (Exception e) {
			log.error("============ Building ScoringEventMap Error ============");
			log.error(e.getCause());
		}

		return scoringEventMap;

	}

	private LinkedHashMap<Long, String> buildCheckPointsShortCutMap() {
		LinkedHashMap<Long, String> checkPointsShortCutsMap = null;
		try {
			// Fetch data from global.properties bean
			String checkPointsAndShortCutKeysData = getSaitenGlobalProperties()
					.getProperty(WebAppConst.SHORTCUTKEYS_DATA);
			checkPointsShortCutsMap = new LinkedHashMap<Long, String>();

			// CheckPoints and ShortCutKey e.g. 24:q
			String[] checkPointsAndShortCutKeysArray = checkPointsAndShortCutKeysData
					.split(",");
			for (String checkPointsAndShortCutKeys : checkPointsAndShortCutKeysArray) {
				String[] checkPointsAndShortCutKeysValues = checkPointsAndShortCutKeys
						.split(":");
				checkPointsShortCutsMap.put(
						Long.valueOf(checkPointsAndShortCutKeysValues[0]),
						checkPointsAndShortCutKeysValues[1]);
			}

		} catch (Exception e) {
			log.error("============ Building checkPointsShortCutsMap Error ============");
			log.error(e.getCause());
		}
		return checkPointsShortCutsMap;
	}

	private LinkedHashMap<String, String> buildScreenIdMap() {

		LinkedHashMap<String, String> screenIdMap = null;

		try {
			// Fetch data from global.properties bean
			String screenIdList = getSaitenGlobalProperties().getProperty(
					WebAppConst.SAITEN_SCREEN_ID_DATA);
			screenIdMap = new LinkedHashMap<String, String>();

			// MenuId and ScoringEvent e.g. FIRST_SCORING_MENU_ID:120
			String[] screenIdArray = screenIdList.split(",");

			for (String screenIdObj : screenIdArray) {
				String[] screenId = screenIdObj.split(":");
				screenIdMap.put(screenId[0], screenId[1]);
			}
		} catch (Exception e) {
			log.error("============ Building buildScreenIdMap Error ============");
			log.error(e.getCause());
		}

		return screenIdMap;

	}

	@SuppressWarnings("rawtypes")
	private LinkedHashMap<Byte, String> buildMstScorerRoleMap() {
		LinkedHashMap<Byte, String> mstScorerRoleMap = new LinkedHashMap<Byte, String>();
		try {

			List mstScorerRoleList = mstScorerDAO.getMstScorerRoleList();

			if (!mstScorerRoleList.isEmpty()) {

				for (Object object : mstScorerRoleList) {
					Object[] mstScorerRoleObjects = (Object[]) object;
					mstScorerRoleMap.put((Byte) mstScorerRoleObjects[0],
							(String) mstScorerRoleObjects[1]);
				}
			}
		} catch (Exception e) {
			log.error("============ Building buibuildMstScorerRoleMap Error ============");
			log.error(e.getCause());
		}
		return mstScorerRoleMap;
	}

	private LinkedHashMap<Integer, LinkedHashMap<Integer, Short>> buildMstMarkValueMap() {
		LinkedHashMap<Integer, LinkedHashMap<Integer, Short>> questionWiseMarkValuesMap = new LinkedHashMap<Integer, LinkedHashMap<Integer, Short>>();
		LinkedHashMap<Integer, Short> mstMarkValueMap = new LinkedHashMap<Integer, Short>();
		@SuppressWarnings("rawtypes")
		List mstMarkValuesList = null;
		try {
			mstMarkValuesList = getMstMarkValueDAO().findAll();
			if (!mstMarkValuesList.isEmpty()) {
				for (Object object : mstMarkValuesList) {
					Object[] markValueInfoObject = (Object[]) object;
					Integer questionSeq = (Integer) markValueInfoObject[0];
					if (questionWiseMarkValuesMap.isEmpty()
							|| !questionWiseMarkValuesMap
									.containsKey(questionSeq)) {
						mstMarkValueMap = new LinkedHashMap<Integer, Short>();
						mstMarkValueMap.put((Integer) markValueInfoObject[1],
								(Short) markValueInfoObject[2]);
						questionWiseMarkValuesMap.put(
								(Integer) markValueInfoObject[0],
								mstMarkValueMap);

					} else if (questionWiseMarkValuesMap
							.containsKey(questionSeq)) {
						mstMarkValueMap.put((Integer) markValueInfoObject[1],
								(Short) markValueInfoObject[2]);
					}
				}
			}
		} catch (Exception e) {
			log.error("============ Building mstMarkValueMap Error ============");
			log.error(e.getCause());
		}
		return questionWiseMarkValuesMap;
	}

	/**
	 * 
	 * @param saitenGlobalProperties
	 */
	public void setSaitenGlobalProperties(Properties saitenGlobalProperties) {
		this.saitenGlobalProperties = saitenGlobalProperties;
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

	/**
	 * 
	 * @param saitenConfig
	 */
	public void setSaitenConfig(SaitenConfig saitenConfig) {
		this.saitenConfig = saitenConfig;
	}

	/**
	 * 
	 * @param mstGradeDAO
	 */
	public void setMstGradeDAO(MstGradeDAO mstGradeDAO) {
		this.mstGradeDAO = mstGradeDAO;
	}

	/**
	 * 
	 * @param mstScoringStateListDAO
	 */
	public void setMstScoringStateListDAO(
			MstScoringStateListDAO mstScoringStateListDAO) {
		this.mstScoringStateListDAO = mstScoringStateListDAO;
	}

	/**
	 * 
	 * @param mstPendingCategoryDAO
	 */
	public void setMstPendingCategoryDAO(
			MstPendingCategoryDAO mstPendingCategoryDAO) {
		this.mstPendingCategoryDAO = mstPendingCategoryDAO;
	}

	public MstGradeDAO getMstGradeDAO() {
		return mstGradeDAO;
	}

	public MstScoringStateListDAO getMstScoringStateListDAO() {
		return mstScoringStateListDAO;
	}

	public MstPendingCategoryDAO getMstPendingCategoryDAO() {
		return mstPendingCategoryDAO;
	}

	public SaitenConfig getSaitenConfig() {
		return saitenConfig;
	}

	public Properties getSaitenGlobalProperties() {
		return saitenGlobalProperties;
	}

	public SaitenMasterUtil getSaitenMasterUtil() {
		return saitenMasterUtil;
	}

	public void setSaitenMasterUtil(SaitenMasterUtil saitenMasterUtil) {
		this.saitenMasterUtil = saitenMasterUtil;
	}

	/**
	 * @return the mstScorerDAO
	 */
	public MstScorerDAO getMstScorerDAO() {
		return mstScorerDAO;
	}

	/**
	 * @return the mstMarkValueDAO
	 */
	public MstMarkValueDAO getMstMarkValueDAO() {
		return mstMarkValueDAO;
	}

	/**
	 * @param mstMarkValueDAO
	 *            the mstMarkValueDAO to set
	 */
	public void setMstMarkValueDAO(MstMarkValueDAO mstMarkValueDAO) {
		this.mstMarkValueDAO = mstMarkValueDAO;
	}

	/**
	 * @param mstScorerDAO
	 *            the mstScorerDAO to set
	 */
	public void setMstScorerDAO(MstScorerDAO mstScorerDAO) {
		this.mstScorerDAO = mstScorerDAO;
	}

}
