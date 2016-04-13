package com.saiten.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.StrutsStatics;
import org.apache.struts2.dispatcher.SessionMap;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.saiten.info.MstScorerInfo;
import com.saiten.info.ScorerAccessLogInfo;
import com.saiten.util.SaitenMasterUtil;
import com.saiten.util.WebAppConst;

public class LoginInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;

	private static final String LOGIN_ATTEMPT = "loginAttempt";

	/*
	 * @Autowired private SessionRegistry sessionRegistry;
	 */

	@SuppressWarnings("rawtypes")
	public String intercept(ActionInvocation invocation) throws Exception {
		// Get the action context from the invocation so we can access the
		// request and session objects.

		final Map session;

		final HttpServletRequest request;

		final ActionContext context = invocation.getInvocationContext();
		request = (HttpServletRequest) context.get(StrutsStatics.HTTP_REQUEST);

		session = context.getSession();
		// Is there a "user" object stored in the user's session?
		Object mstScorerInfoObj = session.get("scorerInfo");
		String scorerIdObj = null;
		if (mstScorerInfoObj != null) {
			scorerIdObj = ((MstScorerInfo) mstScorerInfoObj).getScorerId();
		}/*
		 * else{ scorerIdObj = request.getParameter("scorerId");; }
		 */
		// The user has not logged in yet - check login attempting
		String loginAttempt = request.getParameter(LOGIN_ATTEMPT);

		if (scorerIdObj == null) {

			if (!StringUtils.isBlank(loginAttempt)) {
				return invocation.invoke();
			} else {
				request.setAttribute("sessionTimeout", true);
				return ActionSupport.ERROR;
			}

		} else {

			ScorerAccessLogInfo scorerAccessLogInfo = (ScorerAccessLogInfo) session
					.get("scorerAccessLogInfo");
			String userStatus = new String();
			if (scorerAccessLogInfo != null) {
				ApplicationContext ctx = ContextLoader
						.getCurrentWebApplicationContext();
				Integer id = scorerAccessLogInfo.getId();
				userStatus = ((SaitenMasterUtil) ctx
						.getBean("saitenMasterUtil")).findUserStatusById(id);
			}

			if ((!userStatus.equals(WebAppConst.SCORER_LOGGING_STATUS[0]))
					&& (StringUtils.isBlank(loginAttempt))) {
				request.setAttribute("lmsInstanceId", session.get("lmsInstanceId"));
				request.setAttribute("duplicateLogout", true);
				session.clear();
				// invalidate session
				((SessionMap) session).invalidate();
				return ActionSupport.LOGIN;
			}
			return invocation.invoke();
		}
	}

	/*
	 * public void setSessionRegistry(SessionRegistry sessionRegistry) {
	 * this.sessionRegistry = sessionRegistry; }
	 */

}
