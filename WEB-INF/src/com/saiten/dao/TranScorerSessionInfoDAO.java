/**
 * 
 */
package com.saiten.dao;

import com.saiten.model.TranScorerSessionInfo;

/**
 * @author user
 * 
 */
public interface TranScorerSessionInfoDAO {
	public void saveOrUpdate(TranScorerSessionInfo tranScorerSessionInfo);

	public TranScorerSessionInfo findById(String scorerId);

	public Long countScorersByAnswerFormNumAndSubjectCode(String answerFormNum,
			String subjectCode);
}
