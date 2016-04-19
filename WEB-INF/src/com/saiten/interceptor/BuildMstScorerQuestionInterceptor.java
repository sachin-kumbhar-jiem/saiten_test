/**
 * 
 */
package com.saiten.interceptor;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.StrutsStatics;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.saiten.info.MstScorerInfo;
import com.saiten.util.SaitenMasterUtil;
import com.saiten.util.SaitenUtil;
import com.saiten.util.WebAppConst;

/**
 * @author rajeshwars
 * 
 */
@SuppressWarnings("serial")
public class BuildMstScorerQuestionInterceptor extends AbstractInterceptor {

	private static Logger log = Logger
			.getLogger(BuildMstScorerQuestionInterceptor.class);

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {

		final Map session;

		@SuppressWarnings("unused")
		final HttpServletRequest request;

		final ActionContext context = invocation.getInvocationContext();
		request = (HttpServletRequest) context.get(StrutsStatics.HTTP_REQUEST);

		session = context.getSession();
		MstScorerInfo scorerInfo = (MstScorerInfo) session.get("scorerInfo");

		try {
			if (session.get("mstScorerQuestionMap") == null
			/* || session.get("scorerWiseQuestionsSpecificMap") == null */) {

				ApplicationContext ctx = ContextLoader
						.getCurrentWebApplicationContext();

				if (scorerInfo != null) {
					log.info(scorerInfo.getScorerId() + " execution start.");
					int roleId = scorerInfo.getRoleId();
					if (WebAppConst.ADMIN_ROLE_ID != roleId) {
						Map<String, List<Integer>> mstScorerQuestionMap = ((SaitenMasterUtil) ctx
								.getBean("saitenMasterUtil"))
								.buildMstScorerQuestionMapByScorerId(SaitenUtil
										.getLoggedinScorerId());
						session.put("mstScorerQuestionMap",
								mstScorerQuestionMap);
					}
					log.info(scorerInfo.getScorerId() + " execution end.");
				}

				/*
				 * LinkedHashMap<String, HashMap> mstScorerQuestionsAndFlagsMap
				 * = ((SaitenMasterUtil) ctx
				 * .getBean("saitenMasterUtil")).buildMstScorerQuestionMap();
				 * 
				 * LinkedHashMap<String, LinkedHashMap<Integer, MstQuestion>>
				 * mstScorerQuestionMap = (LinkedHashMap<String,
				 * LinkedHashMap<Integer, MstQuestion>>)
				 * mstScorerQuestionsAndFlagsMap
				 * .get(WebAppConst.MST_SCORER_QUESTION_MAP);
				 */

				/*
				 * LinkedHashMap<ScorerQuestionKey,
				 * ScorerWiseQuestionsSpecificFlags>
				 * scorerWiseQuestionsSpecificMap =
				 * (LinkedHashMap<ScorerQuestionKey,
				 * ScorerWiseQuestionsSpecificFlags>)
				 * mstScorerQuestionsAndFlagsMap
				 * .get(WebAppConst.SCORER_WISE_QUESTIONSMAP);
				 */

				/*
				 * session.put("scorerWiseQuestionsSpecificMap",
				 * scorerWiseQuestionsSpecificMap);
				 */

			}
			return invocation.invoke();
		} catch (Exception e) {
			if (scorerInfo != null) {
				log.error("[ ScorerId: " + scorerInfo.getScorerId() + "] ", e);
			} else {
				log.error("[ ScorerId: " + null + "] ", e);
			}
			throw e;
		}

	}

}
