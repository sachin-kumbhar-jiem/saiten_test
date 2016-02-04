package com.saiten.model;

// Generated 1 Jan, 2015 10:15:11 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * TranScorerAccessLog generated by hbm2java
 */
public class TranScorerAccessLog implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String scorerId;
	private Date loginTime;
	private Date logoutTime;
	private String status;

	public TranScorerAccessLog() {
	}

	
	/**
	 * @param id
	 */
	public TranScorerAccessLog(Integer id) {
		this.id = id;
	}


	/**
	 * @param id
	 * @param scorerId
	 * @param loginTime
	 * @param logoutTime
	 * @param status
	 */
	public TranScorerAccessLog(Integer id, String scorerId, Date loginTime,
			Date logoutTime, String status) {
		this.id = id;
		this.scorerId = scorerId;
		this.loginTime = loginTime;
		this.logoutTime = logoutTime;
		this.status = status;
	}


	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	/**
	 * @return the scorerId
	 */
	public String getScorerId() {
		return scorerId;
	}


	/**
	 * @param scorerId the scorerId to set
	 */
	public void setScorerId(String scorerId) {
		this.scorerId = scorerId;
	}


	public Date getLoginTime() {
		return this.loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Date getLogoutTime() {
		return this.logoutTime;
	}

	public void setLogoutTime(Date logoutTime) {
		this.logoutTime = logoutTime;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
