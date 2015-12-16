package com.saiten.dao;

import java.util.List;

import com.saiten.info.DailyStatusSearchInfo;
import com.saiten.model.MstScorer;

/**
 * @author sachin
 * @version 1.0
 * @created 05-Dec-2012 4:59:11 PM
 */
public interface MstScorerDAO {

	/**
	 * @param scorerId
	 * @param password
	 * @return List
	 */
	@SuppressWarnings("rawtypes")
	public List findByScorerIdAndPassword(String scorerId, String password);

	public boolean isUserAlreadyExist(String scorerId);

	@SuppressWarnings("rawtypes")
	public List getMstScorerRoleList();

	public void save(MstScorer mstScorer);

	public List<MstScorer> scorerByRole(Byte[] rollIds,
			DailyStatusSearchInfo dailyStatusSearchInfo);

	public void update(MstScorer mstScorer);
}