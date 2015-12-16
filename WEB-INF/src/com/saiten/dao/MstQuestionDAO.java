package com.saiten.dao;

import java.util.List;

import com.saiten.model.MstQuestion;

/**
 * @author sachin
 * @version 1.0
 * @created 05-Dec-2012 4:59:11 PM
 */
public interface MstQuestionDAO {
	List<MstQuestion> findAll();

	/**
	 * @param menuId
	 * @param scorerId
	 * @return List
	 */
	@SuppressWarnings("rawtypes")
	List findAll(String menuId, String scorerId);

	/**
	 * @param menuId
	 * @return List
	 */
	/* List<MstQuestion> findMstQuestions(); */

	/**
	 * @param questionSeq
	 * @return List
	 */
	@SuppressWarnings("rawtypes")
	public List fetchDbInstanceInfo(List<Integer> questionSeq);

	/**
	 * @param subjectCode
	 * @param questionNum
	 * @return List<Integer>
	 */
	public List<Integer> findQuestionSeq(String subjectCode, Short questionNum);

	@SuppressWarnings("rawtypes")
	public List findQuestionList();

	public List<MstQuestion> findQuestionListBySubjectCodeList(
			List<String> subjectCodeList);

	public List<Integer> getQuestionSeqByEvalType(Integer evalType);

}
