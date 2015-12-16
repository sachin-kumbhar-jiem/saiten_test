/**
 * 
 */
package com.saiten.dao;

import java.util.List;

import com.saiten.info.LookAfterwardsInfo;
import com.saiten.model.TranLookAfterwards;

/**
 * @author user
 * 
 */
public interface TranLookAfterwardsDAO {

	public Integer save(TranLookAfterwards tranLookAfterwards,
			String connectionString);

	public void update(TranLookAfterwards tranLookAfterwards,
			String connectionString);

	public List<String> fetchCommentsByAnswerSeq(Integer answerSeq,
			String connectionString);

	public int unmarkAll(LookAfterwardsInfo lookAfterwardsInfo,
			String connectionString);

	public TranLookAfterwards fetchByAnswerSeqAndUpdatePersonId(
			Integer answerSeq, String updatePersonId, String connectionString);
	
	public TranLookAfterwards fetchById(Integer lookAftSeq, String connectionString);
}
