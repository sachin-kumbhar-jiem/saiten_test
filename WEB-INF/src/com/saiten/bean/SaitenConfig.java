package com.saiten.bean;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.saiten.model.MstGrade;
import com.saiten.model.MstQuestion;
import com.saiten.model.MstTestsetnumQuestion;
import com.saiten.model.ScorerWiseQuestionsSpecificFlags;

/**
 * @author sachin
 * @version 1.0
 * @created 24-Dec-2012 12:22:57 PM
 */
public class SaitenConfig {
	private LinkedHashMap<GradeResultKey, MstGrade> mstGradeMap;

	private LinkedHashMap<Long, String> checkPointsShortCutsMap;

	private LinkedHashMap<ScoringStateKey, List<Short>> historyScoringStatesMap;

	private LinkedHashMap<Integer, MstQuestion> mstQuestionMap;

	private LinkedHashMap<String, LinkedHashMap<Integer, MstQuestion>> mstScorerQuestionMap;

	private LinkedHashMap<Short, String> scoringStateMap;

	private LinkedHashMap<GradeNumKey, MstGrade> gradeSequenceWiseMstGradeMap;

	private LinkedHashMap<Integer, Short> pendingCategoryMap;

	private LinkedHashMap<String, Short> menuIdAndScoringStateMap;

	private LinkedHashMap<String, Short> scoringEventMap;

	private LinkedHashMap<String, String> screenIdMap;

	private Map<SpecialScoringKey, String> specialScoringMap = new ConcurrentHashMap<SpecialScoringKey, String>();

	private LinkedHashMap<ScorerQuestionKey, ScorerWiseQuestionsSpecificFlags> scorerWiseQuestionsSpecificMap;

	private LinkedHashMap<Byte, String> mstScorerRoleMap;
	
	private LinkedHashMap<Integer, MstTestsetnumQuestion> mstTestsetnumQuestionMap;

	@SuppressWarnings("rawtypes")
	private LinkedHashMap<String, HashMap> mstScorerQuestionsAndFlagsMap;

	private LinkedHashMap<Integer, LinkedHashMap<Integer, Short>> mstMarkValueMap;

	public Map<SpecialScoringKey, String> getSpecialScoringMap() {
		return specialScoringMap;
	}

	public void setSpecialScoringMap(
			Map<SpecialScoringKey, String> specialScoringMap) {
		this.specialScoringMap = specialScoringMap;
	}

	public LinkedHashMap<String, String> getScreenIdMap() {
		return screenIdMap;
	}

	public void setScreenIdMap(LinkedHashMap<String, String> screenIdMap) {
		this.screenIdMap = screenIdMap;
	}

	public LinkedHashMap<String, Short> getScoringEventMap() {
		return scoringEventMap;
	}

	/**
	 * 
	 * @param scoringEventMap
	 */
	public void setScoringEventMap(LinkedHashMap<String, Short> scoringEventMap) {
		this.scoringEventMap = scoringEventMap;
	}

	public LinkedHashMap<String, Short> getMenuIdAndScoringStateMap() {
		return menuIdAndScoringStateMap;
	}

	/**
	 * 
	 * @param menuIdAndScoringStateMap
	 */
	public void setMenuIdAndScoringStateMap(
			LinkedHashMap<String, Short> menuIdAndScoringStateMap) {
		this.menuIdAndScoringStateMap = menuIdAndScoringStateMap;
	}

	public LinkedHashMap<GradeResultKey, MstGrade> getMstGradeMap() {
		return mstGradeMap;
	}

	/**
	 * 
	 * @param mstGradeMap
	 */
	public void setMstGradeMap(
			LinkedHashMap<GradeResultKey, MstGrade> mstGradeMap) {
		this.mstGradeMap = mstGradeMap;
	}

	public LinkedHashMap<ScoringStateKey, List<Short>> getHistoryScoringStatesMap() {
		return historyScoringStatesMap;
	}

	/**
	 * 
	 * @param historyScoringStatesMap
	 */
	public void setHistoryScoringStatesMap(
			LinkedHashMap<ScoringStateKey, List<Short>> historyScoringStatesMap) {
		this.historyScoringStatesMap = historyScoringStatesMap;
	}

	public LinkedHashMap<Integer, MstQuestion> getMstQuestionMap() {
		return mstQuestionMap;
	}

	/**
	 * 
	 * @param mstQuestionMap
	 */
	public void setMstQuestionMap(
			LinkedHashMap<Integer, MstQuestion> mstQuestionMap) {
		this.mstQuestionMap = mstQuestionMap;
	}

	public LinkedHashMap<Short, String> getScoringStateMap() {
		return scoringStateMap;
	}

	/**
	 * 
	 * @param scoringStateMap
	 */
	public void setScoringStateMap(LinkedHashMap<Short, String> scoringStateMap) {
		this.scoringStateMap = scoringStateMap;
	}

	public LinkedHashMap<GradeNumKey, MstGrade> getGradeSequenceWiseMstGradeMap() {
		return gradeSequenceWiseMstGradeMap;
	}

	/**
	 * 
	 * @param gradeSequenceWiseMstGradeMap
	 */
	public void setGradeSequenceWiseMstGradeMap(
			LinkedHashMap<GradeNumKey, MstGrade> gradeSequenceWiseMstGradeMap) {
		this.gradeSequenceWiseMstGradeMap = gradeSequenceWiseMstGradeMap;
	}

	public LinkedHashMap<Integer, Short> getPendingCategoryMap() {
		return pendingCategoryMap;
	}

	/**
	 * 
	 * @param pendingCategoryMap
	 */
	public void setPendingCategoryMap(
			LinkedHashMap<Integer, Short> pendingCategoryMap) {
		this.pendingCategoryMap = pendingCategoryMap;
	}

	/**
	 * @return the checkPointsShortCutsMap
	 */
	public LinkedHashMap<Long, String> getCheckPointsShortCutsMap() {
		return checkPointsShortCutsMap;
	}

	/**
	 * @param checkPointsShortCutsMap
	 *            the checkPointsShortCutsMap to set
	 */
	public void setCheckPointsShortCutsMap(
			LinkedHashMap<Long, String> checkPointsShortCutsMap) {
		this.checkPointsShortCutsMap = checkPointsShortCutsMap;
	}

	public LinkedHashMap<String, LinkedHashMap<Integer, MstQuestion>> getMstScorerQuestionMap() {
		return mstScorerQuestionMap;
	}

	public void setMstScorerQuestionMap(
			LinkedHashMap<String, LinkedHashMap<Integer, MstQuestion>> mstScorerQuestionMap) {
		this.mstScorerQuestionMap = mstScorerQuestionMap;
	}

	/**
	 * @return the mstScorerQuestionsAndFlagsMap
	 */
	@SuppressWarnings("rawtypes")
	public LinkedHashMap<String, HashMap> getMstScorerQuestionsAndFlagsMap() {
		return mstScorerQuestionsAndFlagsMap;
	}

	/**
	 * @param mstScorerQuestionsAndFlagsMap
	 *            the mstScorerQuestionsAndFlagsMap to set
	 */
	public void setMstScorerQuestionsAndFlagsMap(
			@SuppressWarnings("rawtypes") LinkedHashMap<String, HashMap> mstScorerQuestionsAndFlagsMap) {
		this.mstScorerQuestionsAndFlagsMap = mstScorerQuestionsAndFlagsMap;
	}

	/**
	 * @return the scorerWiseQuestionsSpecificMap
	 */
	public LinkedHashMap<ScorerQuestionKey, ScorerWiseQuestionsSpecificFlags> getScorerWiseQuestionsSpecificMap() {
		return scorerWiseQuestionsSpecificMap;
	}

	/**
	 * @param scorerWiseQuestionsSpecificMap
	 *            the scorerWiseQuestionsSpecificMap to set
	 */
	public void setScorerWiseQuestionsSpecificMap(
			LinkedHashMap<ScorerQuestionKey, ScorerWiseQuestionsSpecificFlags> scorerWiseQuestionsSpecificMap) {
		this.scorerWiseQuestionsSpecificMap = scorerWiseQuestionsSpecificMap;
	}

	/**
	 * @return the mstScorerRoleMap
	 */
	public LinkedHashMap<Byte, String> getMstScorerRoleMap() {
		return mstScorerRoleMap;
	}

	/**
	 * @param mstScorerRoleMap
	 *            the mstScorerRoleMap to set
	 */
	public void setMstScorerRoleMap(LinkedHashMap<Byte, String> mstScorerRoleMap) {
		this.mstScorerRoleMap = mstScorerRoleMap;
	}

	/**
	 * @return the mstMarkValueMap
	 */
	public LinkedHashMap<Integer, LinkedHashMap<Integer, Short>> getMstMarkValueMap() {
		return mstMarkValueMap;
	}

	/**
	 * @param mstMarkValueMap
	 *            the mstMarkValueMap to set
	 */
	public void setMstMarkValueMap(
			LinkedHashMap<Integer, LinkedHashMap<Integer, Short>> mstMarkValueMap) {
		this.mstMarkValueMap = mstMarkValueMap;
	}

	public LinkedHashMap<Integer, MstTestsetnumQuestion> getMstTestsetnumQuestionMap() {
		return mstTestsetnumQuestionMap;
	}

	public void setMstTestsetnumQuestionMap(
			LinkedHashMap<Integer, MstTestsetnumQuestion> mstTestsetnumQuestionMap) {
		this.mstTestsetnumQuestionMap = mstTestsetnumQuestionMap;
	}

}
