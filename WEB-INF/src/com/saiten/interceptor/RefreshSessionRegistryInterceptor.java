/**
 * 
 */
package com.saiten.interceptor;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.StrutsStatics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.saiten.info.MstScorerInfo;
import com.saiten.info.UserQuestionInfo;

/**
 * @author user
 * 
 */
public class RefreshSessionRegistryInterceptor extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private SessionRegistry sessionRegistry;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.opensymphony.xwork2.interceptor.AbstractInterceptor#intercept(com
	 * .opensymphony.xwork2.ActionInvocation)
	 */
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {

		String result = invocation.invoke();

		@SuppressWarnings("rawtypes")
		final Map session;

		final HttpServletRequest request;

		final ActionContext context = invocation.getInvocationContext();
		request = (HttpServletRequest) context.get(StrutsStatics.HTTP_REQUEST);

		String sessionId = request.getSession().getId();
		session = context.getSession();
		// Is there a "user" object stored in the user's session?
		Object mstScorerInfoObj = session.get("scorerInfo");
		if (mstScorerInfoObj != null) {
			MstScorerInfo mstScorerInfo = (MstScorerInfo) mstScorerInfoObj;
			UserQuestionInfo userQuestionInfo = new UserQuestionInfo();
			userQuestionInfo.setScorerId(mstScorerInfo.getScorerId());
			List<SessionInformation> userSessions = sessionRegistry
					.getAllSessions(userQuestionInfo, true);
			for (SessionInformation userSession : userSessions) {
				String expiredSessionId = new String();
				expiredSessionId = userSession.getSessionId();
				if (!expiredSessionId.equals(sessionId)) {
					sessionRegistry.removeSessionInformation(expiredSessionId);
				}
			}

		}
		return result;
	}

	public void setSessionRegistry(SessionRegistry sessionRegistry) {
		this.sessionRegistry = sessionRegistry;
	}
}
