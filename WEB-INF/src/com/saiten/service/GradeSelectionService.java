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
	public Map<String, String> findGradesByQuestionSeq(int questionSeq, String gradeNumText, Short latestScoringState,
			Short selectedMarkValue, Short denyCategory, Integer inspectionGroupSeq, String connectionString);
	
	//public List findGradesByQuestionSeq(int questionSeq, String gradeNumText);
}
