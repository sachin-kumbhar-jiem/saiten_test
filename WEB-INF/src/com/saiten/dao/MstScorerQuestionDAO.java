package com.saiten.dao;

import java.util.List;

import com.saiten.model.MstScorerQuestion;

/**
 * @author sachin
 * @version 1.0
 * @created 06-Dec-2012 2:35:44 PM
 */
public interface MstScorerQuestionDAO {

	/**
	 * @param menuId
	 * @param scorerId
	 * @return List
	 */
	@SuppressWarnings("rawtypes")
	public List findQuestionsByMenuIdAndScorerId(String menuId, String scorerId);

	public List<Integer> getQuestionSequencesByScorerId(String scorerId);

	public void saveAll(List<MstScorerQuestion> mstScorerQuestionList);

	public void deleteByScorerid(String scorerId);
}