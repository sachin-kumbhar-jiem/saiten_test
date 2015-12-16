package com.saiten.dao;

import java.util.List;

/**
 * @author sachin
 * @version 1.0
 * @created 05-Dec-2012 4:59:11 PM
 */
@SuppressWarnings("rawtypes")
public interface MstPendingCategoryDAO {
	public List findPendingCategoryList();

	/**
	 * @param questionSeq
	 * @return List
	 */
	public List findPendingCategoriesByQuestionSeq(int questionSeq);

	/**
	 * @param questionSeq
	 * @param pendingCategoryList
	 * @return List
	 */
	public List findPendingCategorySeqList(Integer questionSeq,
			Short[] pendingCategoryList);
}
