package com.saiten.action;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.saiten.exception.SaitenRuntimeException;
import com.saiten.util.ErrorCode;
import com.saiten.util.SaitenUtil;

/**
 * @author sachin
 * @version 1.0
 * @created 13-Dec-2012 11:53:11 AM
 */
public class ConfirmPendingAction extends ActionSupport implements SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, Object> session;
	private Map<String, Object> result;

	public Map<String, Object> getResult() {
		return result;
	}

	/**
	 * @param result
	 */
	public void setResult(Map<String, Object> result) {
		this.result = result;
	}

	public String confirmPending() {
		try {
			// Return statusCode as a JSON response for pending category
			result = SaitenUtil.getAjaxCallStatusCode(session);
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.CONFIRM_SCORE_ACTION_EXCEPTION, e);
		}
		return SUCCESS;
	}

	/**
	 * 
	 * @param session
	 */
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

}