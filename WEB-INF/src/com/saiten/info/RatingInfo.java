/**
 * 
 */
package com.saiten.info;

import java.util.Map;

/**
 * @author Rajesh
 * 
 */
public class RatingInfo {

	private String scorerId;
	private String questionType;
	private int totalRating;
	private int totalDiscrepancyCount;
	private int matchCount;
	private int discrepancyCount;
	private int dailyScoreSpkTotalCount;
	private int dailyScoreWrtTotalCount;
	private Map<Integer, Integer> quesSeqWiseCountMap;

	public String getScorerId() {
		return scorerId;
	}

	public void setScorerId(String scorerId) {
		this.scorerId = scorerId;
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public int getTotalRating() {
		return totalRating;
	}

	public void setTotalRating(int totalRating) {
		this.totalRating = totalRating;
	}

	public int getTotalDiscrepancyCount() {
		return totalDiscrepancyCount;
	}

	public void setTotalDiscrepancyCount(int totalDiscrepancyCount) {
		this.totalDiscrepancyCount = totalDiscrepancyCount;
	}

	public int getMatchCount() {
		return matchCount;
	}

	public void setMatchCount(int matchCount) {
		this.matchCount = matchCount;
	}

	public int getDiscrepancyCount() {
		return discrepancyCount;
	}

	public void setDiscrepancyCount(int discrepancyCount) {
		this.discrepancyCount = discrepancyCount;
	}

	public int getDailyScoreSpkTotalCount() {
		return dailyScoreSpkTotalCount;
	}

	public void setDailyScoreSpkTotalCount(int dailyScoreSpkTotalCount) {
		this.dailyScoreSpkTotalCount = dailyScoreSpkTotalCount;
	}

	public int getDailyScoreWrtTotalCount() {
		return dailyScoreWrtTotalCount;
	}

	public void setDailyScoreWrtTotalCount(int dailyScoreWrtTotalCount) {
		this.dailyScoreWrtTotalCount = dailyScoreWrtTotalCount;
	}

	public Map<Integer, Integer> getQuesSeqWiseCountMap() {
		return quesSeqWiseCountMap;
	}

	public void setQuesSeqWiseCountMap(Map<Integer, Integer> quesSeqWiseCountMap) {
		this.quesSeqWiseCountMap = quesSeqWiseCountMap;
	}

}
