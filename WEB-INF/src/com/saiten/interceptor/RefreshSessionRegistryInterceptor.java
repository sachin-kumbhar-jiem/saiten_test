/**
 * 
 */
package com.saiten.interceptor;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
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

	private static Logger log = Logger
			.getLogger(RefreshSessionRegistryInterceptor.class);

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
		String result = null;
		@SuppressWarnings("rawtypes")
		final Map session;

		final HttpServletRequest request;

		final ActionContext context = invocation.getInvocationContext();
		request = (HttpServletRequest) context.get(StrutsStatics.HTTP_REQUEST);

		String sessionId = request.getSession().getId();
		session = context.getSession();
		// Is there a "user" object stored in the user's session?
		Object mstScorerInfoObj = session.get("scorerInfo");
		try {
			result = invocation.invoke();

			if (mstScorerInfoObj != null) {
				MstScorerInfo mstScorerInfo = (MstScorerInfo) mstScorerInfoObj;
				log.info(mstScorerInfo.getScorerId() + " execution start.");
				UserQuestionInfo userQuestionInfo = new UserQuestionInfo();
				userQuestionInfo.setScorerId(mstScorerInfo.getScorerId());
				List<SessionInformation> userSessions = sessionRegistry
						.getAllSessions(userQuestionInfo, true);
				for (SessionInformation userSession : userSessions) {
					String expiredSessionId = new String();
					expiredSessionId = userSession.getSessionId();
					if (!expiredSessionId.equals(sessionId)) {
						sessionRegistry
								.removeSessionInformation(expiredSessionId);
					}
				}
				log.info(mstScorerInfo.getScorerId() + " execution end.");
			}
		} catch (Exception e) {
			if (mstScorerInfoObj != null) {
				MstScorerInfo mstScorerInfo = (MstScorerInfo) mstScorerInfoObj;
				log.error("[ ScorerId: " + mstScorerInfo.getScorerId() + "] ",
						e);
			} else {
				log.error("[ ScorerId: " + null + "] ", e);
			}
			throw e;
		}

		return result;
	}

	public void setSessionRegistry(SessionRegistry sessionRegistry) {
		this.sessionRegistry = sessionRegistry;
	}
}
