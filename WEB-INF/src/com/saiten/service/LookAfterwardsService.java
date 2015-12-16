/**
 * 
 */
package com.saiten.service;

import java.util.Map;

import com.saiten.info.LookAfterwardsInfo;

/**
 * @author user
 * 
 */
public interface LookAfterwardsService {
	public Integer doMark(LookAfterwardsInfo lookAfterwardsInfo,
			String connectionString);

	public void doUnmark(LookAfterwardsInfo lookAfterwardsInfo,
			String connectionString);

	public Map<String, Object> fetchCommentsByAnswerSeq(Integer answerSeq,
			String connectionString);

	public int unamrkAll(LookAfterwardsInfo lookAfterwardsInfo,
			String connectionString);
}
