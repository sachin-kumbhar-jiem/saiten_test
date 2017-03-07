package com.saiten.dao;

import java.util.List;

/**
 * The Interface AnswerDetailDAO.
 * 
 * @author suwarna
 * @version $Revision: 1.0 $
 */
public interface AnswerDetailDAO {

	/**
	 * Gets the temp data.
	 *
	 * @param questionNumber
	 *            the question number
	 * @param session
	 *            StatelessSession
	 * @return the temp data
	 */
	public List<Object> getObjectiveData(String connectionString, String schoolCodes);

}
