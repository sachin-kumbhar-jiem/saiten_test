/**
 * 
 */
package com.saiten.info;

import java.util.Date;

/**
 * @author user
 * 
 */
public class ScorerAccessLogInfo {

	private Integer id;

	private String scorerId;

	private Date loginTime;

	private Date logoutTime;

	private String status;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
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
	 * @param scorerId
	 *            the scorerId to set
	 */
	public void setScorerId(String scorerId) {
		this.scorerId = scorerId;
	}

	/**
	 * @return the loginTime
	 */
	public Date getLoginTime() {
		return loginTime;
	}

	/**
	 * @param loginTime
	 *            the loginTime to set
	 */
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	/**
	 * @return the logoutTime
	 */
	public Date getLogoutTime() {
		return logoutTime;
	}

	/**
	 * @param logoutTime
	 *            the logoutTime to set
	 */
	public void setLogoutTime(Date logoutTime) {
		this.logoutTime = logoutTime;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

}
