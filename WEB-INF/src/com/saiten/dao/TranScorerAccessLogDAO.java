/**
 * 
 */
package com.saiten.dao;

import java.util.List;

import com.saiten.model.TranScorerAccessLog;

/**
 * @author user
 * 
 */
public interface TranScorerAccessLogDAO {

	public Integer saveOrUpdate(TranScorerAccessLog tranScorerAccessLog);

	public String findStatusById(Integer id);

	public List<TranScorerAccessLog> getAllLoggedInScorers(Integer id,
			String scorerId);

}
