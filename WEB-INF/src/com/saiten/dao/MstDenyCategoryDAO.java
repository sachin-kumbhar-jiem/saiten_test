package com.saiten.dao;

import java.util.List;

public interface MstDenyCategoryDAO {
	public List findDenyCategoryList();

	/**
	 * @param questionSeq
	 * @return List
	 */
	public List findDenyCategoriesByQuestionSeq(int questionSeq);

	/**
	 * @param questionSeq
	 * @param denyCategoryList
	 * @return List
	 */
	public List findDenyCategorySeqList(Integer questionSeq,
			Short[] denyCategoryList);

}
