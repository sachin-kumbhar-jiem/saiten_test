package com.saiten.action;

import java.util.Date;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.opensymphony.xwork2.ActionSupport;
import com.saiten.exception.SaitenRuntimeException;
import com.saiten.info.MstScorerInfo;
import com.saiten.info.ScorerAccessLogInfo;
import com.saiten.service.LoginService;
import com.saiten.util.AESEncryptionDecryptionUtil;
import com.saiten.util.ErrorCode;
import com.saiten.util.SaitenMasterUtil;
import com.saiten.util.WebAppConst;

/**
 * @author sachin
 * @version 1.0
 * @created 05-Dec-2012 4:59:11 PM
 */
public class LoginServiceAction extends ActionSupport implements SessionAware,
		ServletRequestAware {

	private static Logger log = Logger
			.getLogger(LoginServiceAction.class);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LoginService loginService;
	private Properties saitenApplicationProperties;
	private MstScorerInfo scorerInfo;
	private String saitenLMSUrl;
	private String loginError;
	private Map<String, Object> session;
	HttpServletRequest request;
	private String loginPageUrl;

	private void buildSessionObject() {
		session.put("scorerInfo", scorerInfo);

	}

	public String processLogin() {
		try {

			// will get encrypted scorerId and password.
			String scorerId = request.getParameter("scorerId");
			String password = request.getParameter("password");

			saitenLMSUrl = saitenApplicationProperties
					.getProperty(WebAppConst.SAITEN_LMS_INDEX_PAGE_URL)
					+ "?login="
					+ scorerId
					+ "&password="
					+ password
					+ "&loginError=true";

			// decrypt scorerId and password.
			scorerId = AESEncryptionDecryptionUtil.decrypt(scorerId);
			password = AESEncryptionDecryptionUtil.decrypt(password);

			scorerInfo = loginService.findByScorerIdAndPassword(scorerId,
					password);
			if (scorerInfo == null) {
				this.addActionError(getText(WebAppConst.INCORRECT_USERID_PASSWORD));
				return INPUT;

			} else if ((scorerInfo != null)
					&& (scorerInfo.getRoleId() == WebAppConst.WG_ROLE_ID && scorerInfo
							.getNoDbUpdate() == WebAppConst.NO_DB_UPDATE_TRUE)) {
				this.addActionError(getText(WebAppConst.ERROR_WG_USER_WITH_DUMMY_ROLE));
				return INPUT;
			} else {
				scorerInfo.setPassword(AESEncryptionDecryptionUtil
						.encrypt(password));
			}
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
			log.info(scorerInfo.getScorerId()+"-"+"LoggedIn from LMS Side.");
			return SUCCESS;
		} catch (SaitenRuntimeException we) {
			throw we;
		} catch (Exception e) {
			throw new SaitenRuntimeException(ErrorCode.LOGIN_ACTION_EXCEPTION,
					e);
		}
	}

	public String redirectToLoginPage() {

		loginPageUrl = saitenApplicationProperties
				.getProperty(WebAppConst.LOGIN_PAGE_URL);
		return SUCCESS;
	}

	public MstScorerInfo getScorerInfo() {
		return scorerInfo;
	}

	public void setScorerInfo(MstScorerInfo scorerInfo) {
		this.scorerInfo = scorerInfo;
	}

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	public void setSaitenApplicationProperties(
			Properties saitenApplicationProperties) {
		this.saitenApplicationProperties = saitenApplicationProperties;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletRequest getServletRequest() {
		return this.request;
	}

	public String getSaitenLMSUrl() {
		return saitenLMSUrl;
	}

	public void setSaitenLMSUrl(String saitenLMSUrl) {
		this.saitenLMSUrl = saitenLMSUrl;
	}

	public String getLoginError() {
		return loginError;
	}

	public void setLoginError(String loginError) {
		this.loginError = loginError;
	}

	public String getLoginPageUrl() {
		return loginPageUrl;
	}

	public void setLoginPageUrl(String loginPageUrl) {
		this.loginPageUrl = loginPageUrl;
	}

}