package com.saiten.dao;

import java.util.List;

/**
 * @author kailash
 * 
 */
public interface MstGradeResultDAO {

	@SuppressWarnings("rawtypes")
	public List findGradesByQuestionSeq(int questionSeq);
}
