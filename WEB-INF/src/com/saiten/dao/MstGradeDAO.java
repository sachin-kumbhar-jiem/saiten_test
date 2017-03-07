package com.saiten.dao;

import java.util.List;

import com.saiten.model.MstGrade;

/**
 * @author sachin
 * @version 1.0
 * @created 05-Dec-2012 4:59:11 PM
 */
public interface MstGradeDAO {

	List<MstGrade> findAll();

	/**
	 * @param questionSeq
	 * @param gradeNum
	 * @return List
	 */
	@SuppressWarnings("rawtypes")
	List findGradeSeqList(Integer questionSeq, Integer[] gradeNum);

	@SuppressWarnings("rawtypes")
	List getMstGradeDetails();
}
