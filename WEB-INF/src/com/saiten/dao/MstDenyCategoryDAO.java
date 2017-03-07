package com.saiten.dao;

import java.util.List;

public interface MstDenyCategoryDAO {
	@SuppressWarnings("rawtypes")
	public List findDenyCategoryList();

	/**
	 * @param questionSeq
	 * @return List
	 */
	@SuppressWarnings("rawtypes")
	public List findDenyCategoriesByQuestionSeq(int questionSeq);

	/**
	 * @param questionSeq
	 * @param denyCategoryList
	 * @return List
	 */
	@SuppressWarnings("rawtypes")
	public List findDenyCategorySeqList(Integer questionSeq,
			Short[] denyCategoryList);

}
