package com.saiten.service;

import java.util.List;
import java.util.Map;

import com.saiten.info.DailyStatusReportListInfo;
import com.saiten.info.DailyStatusSearchInfo;
import com.saiten.info.DailyStatusSearchScorerInfo;
import com.saiten.info.QuestionInfo;

/**
 * @author Abhijeet
 * @version 1.0
 * @created 18-July-2014
 */
public interface DailyStatusSearchService {

	public Map<Byte, String> getScorerRollMapByID(String Id);

	public List<DailyStatusReportListInfo> getSearchListByQuestion(
			DailyStatusSearchInfo dailyStatusSearchInfo, int roleId);

	public List<DailyStatusSearchScorerInfo> getSearchListByScorer(
			DailyStatusSearchInfo dailyStatusSearchInfo);

	public QuestionInfo getQusetionInfoByQuestionSeq(String questionSeq);

	public List<DailyStatusReportListInfo> getGradeWiseAnswerDetails(
			String questionSeq, String connectionString, Character questionType);

	public List<DailyStatusReportListInfo> getPendingCategoryWiseAnswerDetails(
			String questionSeq, String connectionString);

	public List findQuestionTypeList();

	public List<DailyStatusReportListInfo> getMarkValueWiseAnswerDetails(
			String questionSeq, String connectionString, Character questionType);

	@SuppressWarnings("rawtypes")
	public String getProgressReports(List gradeWiseList,
			List markValueWiseList,List pendingCategoryWiseList, QuestionInfo questionInfo,
			String selectedMenuId);
}