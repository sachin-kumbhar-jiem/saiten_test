/**
 * 
 */
package com.saiten.interceptor;

import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.saiten.info.MstScorerInfo;
import com.saiten.info.QuestionInfo;
import com.saiten.model.TranScorerSessionInfo;
import com.saiten.util.SaitenMasterUtil;

/**
 * @author user
 * 
 */
public class UpdateSessionRegistryInterceptor extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger
			.getLogger(UpdateSessionRegistryInterceptor.class);

	/*
	 * @Autowired private SessionRegistry sessionRegistry;
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.opensymphony.xwork2.interceptor.AbstractInterceptor#intercept(com
	 * .opensymphony.xwork2.ActionInvocation)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		String result = null;
		// do post-processing: Put QuestionInfo into sessionRegistry.

		// Get the action context from the invocation so we can access the
		// request and session objects.

		@SuppressWarnings("rawtypes")
		final Map session;

		final ActionContext context = invocation.getInvocationContext();

		session = context.getSession();
		// Is there a "user" object stored in the user's session?
		Object mstScorerInfoObj = session.get("scorerInfo");

		try {

			result = invocation.invoke();

			if (mstScorerInfoObj != null) {
				MstScorerInfo mstScorerInfo = (MstScorerInfo) mstScorerInfoObj;
				/*
				 * UserQuestionInfo userQuestionInfo = new UserQuestionInfo();
				 * userQuestionInfo.setScorerId(mstScorerInfo.getScorerId());
				 */
				log.info(mstScorerInfo.getScorerId() + " execution start.");
				ApplicationContext ctx = ContextLoader
						.getCurrentWebApplicationContext();
				// check user exist into session.
				TranScorerSessionInfo tranScorerSessionInfo = (TranScorerSessionInfo) session
						.get("tranScorerSessionInfo");
				if (tranScorerSessionInfo == null) {
					// check user exist into tran_scorer_session_info table.
					tranScorerSessionInfo = ((SaitenMasterUtil) ctx
							.getBean("saitenMasterUtil"))
							.getUserSessionInfoById(mstScorerInfo.getScorerId());
				}

				if (tranScorerSessionInfo == null) {
					// since user not exist into session and
					// tran_scorer_session_info table, make new entry into
					// tran_scorer_session_info table.
					tranScorerSessionInfo = new TranScorerSessionInfo();
					tranScorerSessionInfo.setCreateDate(new Date());
					tranScorerSessionInfo.setScorerId(mstScorerInfo
							.getScorerId());
				}

				tranScorerSessionInfo.setUpdateDate(new Date());

				/*
				 * List<SessionInformation> userSessions = sessionRegistry
				 * .getAllSessions(userQuestionInfo, false); userQuestionInfo =
				 * (UserQuestionInfo) userSession .getPrincipal();
				 */

				// update question_seq in tran_scorer_session_info table.
				QuestionInfo questionInfo = (QuestionInfo) session
						.get("questionInfo");
				String subjectCode = null;
				if (questionInfo != null) {
					// make question_seq entry into tran_scorer_session_info for
					// logged_in scorer.
					tranScorerSessionInfo.setQuestionSeq(questionInfo
							.getQuestionSeq());
					subjectCode = (String) questionInfo.getSubjectCode();
				}

				// update answerformnum and subjectcode in
				// tran_scorer_session_info.
				String answerFormNum = (String) session.get("answerFormNum");
				if (answerFormNum != null && subjectCode != null) {
					tranScorerSessionInfo.setAnswerFormNum(answerFormNum);
					tranScorerSessionInfo.setSubjectCode(subjectCode);
				}

				/*
				 * ScorerAccessLogInfo scorerAccessLogInfo =
				 * (ScorerAccessLogInfo) session .get("scorerAccessLogInfo"); if
				 * ((scorerAccessLogInfo != null) &&
				 * (userQuestionInfo.getScorerAccessLogInfo() == null)) {
				 * userQuestionInfo
				 * .setScorerAccessLogInfo(scorerAccessLogInfo); }
				 */

				// save or update tran_scorer_session_info table.
				((SaitenMasterUtil) ctx.getBean("saitenMasterUtil"))
						.updateUserSessionInfo(tranScorerSessionInfo);
				// put current tranScorerSessionInfo into session.
				session.put("tranScorerSessionInfo", tranScorerSessionInfo);
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

	/*
	 * public void setSessionRegistry(SessionRegistry sessionRegistry) {
	 * this.sessionRegistry = sessionRegistry; }
	 */
}
