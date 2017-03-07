package com.saiten.service;

import java.util.Map;

/**
 * @author kailash
 * 
 */
public interface GradeSelectionService {
	/**
	 * @param questionSeq
	 * @return
	 */
	public Map<String, String> findGradesByQuestionSeq(int questionSeq, String gradeNumText);
}
