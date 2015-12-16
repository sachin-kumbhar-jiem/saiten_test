package com.saiten.action;

import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.opensymphony.xwork2.ActionSupport;
import com.saiten.exception.SaitenRuntimeException;
import com.saiten.info.MstScorerInfo;
import com.saiten.info.ScorerAccessLogInfo;
import com.saiten.service.LoginService;
import com.saiten.util.ErrorCode;
import com.saiten.util.MD5Hashing;
import com.saiten.util.SaitenMasterUtil;
import com.saiten.util.WebAppConst;

/**
 * @author sachin
 * @version 1.0
 * @created 05-Dec-2012 4:59:11 PM
 */
public class LoginAction extends ActionSupport implements SessionAware {

	private static Logger log = Logger
			.getLogger(LoginAction.class);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LoginService loginService;
	private MstScorerInfo scorerInfo;
	private Map<String, Object> session;
	private MstScorerInfo validScorerInfo;

	/**
	 * method for buld user session. sets 'saitenLoginEnabled' flag true,
	 * beacause user loggedin from Saiten login screen.
	 */
	private void buildSessionObject() {
		session.put("scorerInfo", scorerInfo);
		session.put("saitenLoginEnabled", true);

	}

	/**
	 * method for login, check userId and Password, and then build user session.
	 * 
	 * @return 'SUCCESS' if login successful.
	 * @return 'INPUT' if login not successful.
	 */
	public String processLogin() {

		try {

			String scorerId = scorerInfo.getScorerId();
			String password = scorerInfo.getPassword();

			scorerInfo = loginService.findByScorerIdAndPassword(scorerId,
					password);
			if (scorerInfo == null) {
				// convert plain password into md5, then check in db.
				password = MD5Hashing.md5(password);
				scorerInfo = loginService.findByScorerIdAndPassword(scorerId,
						password);
			}

			if (scorerInfo == null) {
				this.addActionError(getText(WebAppConst.INCORRECT_USERID_PASSWORD));
				return INPUT;
			} /*
			 * else if ((scorerInfo != null) && (scorerInfo.getRoleId() ==
			 * WebAppConst.WG_ROLE_ID && scorerInfo .getNoDbUpdate() ==
			 * WebAppConst.NO_DB_UPDATE_TRUE)) {
			 * this.addActionError(getText(WebAppConst
			 * .ERROR_WG_USER_WITH_DUMMY_ROLE)); return INPUT; }
			 */

			buildSessionObject();

			// update scorer logging information.
			ApplicationContext ctx = ContextLoader
					.getCurrentWebApplicationContext();

			ScorerAccessLogInfo scorerAccessLogInfo = new ScorerAccessLogInfo();
			scorerAccessLogInfo.setScorerId(scorerInfo.getScorerId());
			scorerAccessLogInfo.setLoginTime(new Date());
			scorerAccessLogInfo.setStatus(WebAppConst.SCORER_LOGGING_STATUS[0]);
			// save loggedin user in tra_scorer_access_log table.
			((SaitenMasterUtil) ctx.getBean("saitenMasterUtil"))
					.updateUserLoggingInformation(scorerAccessLogInfo);
			// put scorerAccessLogInfo into session.
			session.put("scorerAccessLogInfo", scorerAccessLogInfo);
			log.info(scorerInfo.getScorerId()+"-"+"Login.");
			log.info(scorerInfo.getScorerId()+"-"+"LoggedIn from Saiten Side.");
			return SUCCESS;
		} catch (SaitenRuntimeException we) {
			throw we;
		} catch (Exception e) {
			throw new SaitenRuntimeException(ErrorCode.LOGIN_ACTION_EXCEPTION,
					e);
		}
	}

	/**
	 * @return the MstScorerInfo.
	 */
	public MstScorerInfo getScorerInfo() {
		return scorerInfo;
	}

	/**
	 * @param scorerInfo
	 */
	public void setScorerInfo(MstScorerInfo scorerInfo) {
		this.scorerInfo = scorerInfo;
	}

	/**
	 * @return the MstScorerInfo.
	 */
	public MstScorerInfo getValidScorerInfo() {
		return validScorerInfo;
	}

	/**
	 * @param validScorerInfo
	 */
	public void setValidScorerInfo(MstScorerInfo validScorerInfo) {
		this.validScorerInfo = validScorerInfo;
	}

	/**
	 * @param loginService
	 */
	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	/**
	 * @see org.apache.struts2.interceptor.SessionAware#setSession(java.util.Map)
	 */
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

}