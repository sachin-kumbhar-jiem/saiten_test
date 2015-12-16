package com.saiten.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.saiten.exception.SaitenRuntimeException;
import com.saiten.info.MstScorerInfo;

/**
 * @author sachin
 * @version 1.0
 * @created 05-Dec-2012 12:40:12 PM
 */
public class LogFilterInterceptor extends AbstractInterceptor implements
		StrutsStatics {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(LogFilterInterceptor.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.opensymphony.xwork2.interceptor.AbstractInterceptor#intercept(com
	 * .opensymphony.xwork2.ActionInvocation)
	 */
	public String intercept(ActionInvocation invocation) throws Exception {
		// Get the action context from the invocation so we can access the
		// request and session objects.
		final ActionContext context = invocation.getInvocationContext();
		HttpServletRequest request = (HttpServletRequest) context
				.get(HTTP_REQUEST);

		try {
			return invocation.invoke();
		} catch (SaitenRuntimeException e) {
			log.info("================= LogFilterInterceptor =================== ");
			String errorCode = e.getErrorCode();

			MstScorerInfo scorerInfo = (MstScorerInfo) request.getSession()
					.getAttribute("scorerInfo");

			if (scorerInfo != null) {
				String scoreId = scorerInfo.getScorerId();

				log.error("[errorcode=" + errorCode + ":scoreId=" + scoreId
						+ "]", e.getCause());
			} else if (errorCode != null) {
				log.error("[errorcode=" + errorCode + "]", e.getCause());
			} else {
				log.error("Saiten LogFilterInterceptor error");
			}

			// errorCode to be displayed on error page
			request.setAttribute("errorCode", errorCode);

			return "error";
		}
	}

}
