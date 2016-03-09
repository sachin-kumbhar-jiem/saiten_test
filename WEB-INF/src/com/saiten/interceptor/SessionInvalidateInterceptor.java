/**
 * 
 */
package com.saiten.interceptor;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.saiten.info.MstScorerInfo;
import com.saiten.info.ScorerAccessLogInfo;
import com.saiten.model.MstDbInstance;
import com.saiten.model.MstQuestion;
import com.saiten.model.TranScorerSessionInfo;
import com.saiten.util.SaitenMasterUtil;
import com.saiten.util.SaitenUtil;
import com.saiten.util.UnlockAnswerUtil;
import com.saiten.util.WebAppConst;

/**
 * @author user
 * 
 */
public class SessionInvalidateInterceptor extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {

		String result = invocation.invoke();

		// Get the action context from the invocation so we can access the
		// request and session objects.

		@SuppressWarnings("rawtypes")
		final Map session;

		/* final HttpServletRequest request; */

		final ActionContext context = invocation.getInvocationContext();
		/*
		 * request = (HttpServletRequest)
		 * context.get(StrutsStatics.HTTP_REQUEST);
		 */

		/* String sessionId = request.getSession().getId(); */

		session = context.getSession();
		// Is there a "user" object stored in the user's session?
		Object mstScorerInfoObj = session.get("scorerInfo");
		if (mstScorerInfoObj != null) {

			ApplicationContext ctx = ContextLoader
					.getCurrentWebApplicationContext();

			MstScorerInfo mstScorerInfo = (MstScorerInfo) mstScorerInfoObj;
			ScorerAccessLogInfo scorerAccessLogInfo = (ScorerAccessLogInfo) session
					.get("scorerAccessLogInfo");
			TranScorerSessionInfo tranScorerSessionInfo = ((SaitenMasterUtil) ctx
					.getBean("saitenMasterUtil"))
					.getUserSessionInfoById(mstScorerInfo.getScorerId());

			// get all logged_in scorers for same scorer.
			List<ScorerAccessLogInfo> loggedInScorers = ((SaitenMasterUtil) ctx
					.getBean("saitenMasterUtil")).getAllLoggedInScorers(
					scorerAccessLogInfo.getId(),
					scorerAccessLogInfo.getScorerId());
			Integer questionSequence = null;
			if (tranScorerSessionInfo != null) {
				questionSequence = tranScorerSessionInfo.getQuestionSeq();

				// unlock answer, locked by previous session same user.
				if (questionSequence != null) {
					Map<Integer, MstQuestion> mstQuestionMap = SaitenUtil
							.getSaitenConfigObject().getMstQuestionMap();
					MstQuestion mstQuestionObj = mstQuestionMap
							.get(questionSequence);
					String connectionString = null;
					if (mstQuestionObj != null) {
						MstDbInstance mstDbInstance = mstQuestionObj
								.getMstDbInstance();
						if (mstDbInstance != null) {
							connectionString = mstDbInstance
									.getConnectionString();
						}
					}
					String lockBy = mstScorerInfo.getScorerId();
					if (!StringUtils.isBlank(lockBy)
							&& !StringUtils.isBlank(connectionString)) {
						// unlock answer
						Integer answerSeq = null;
						UnlockAnswerUtil.unlockAnswer(questionSequence, lockBy,
								connectionString, answerSeq);
					}
				}

				// clear questionSeq, answerFormNum & SubjectCode from
				// tran_scorer_session_info.
				tranScorerSessionInfo.setQuestionSeq(null);
				tranScorerSessionInfo.setUpdateDate(new Date());
				tranScorerSessionInfo.setSubjectCode(null);
				tranScorerSessionInfo.setAnswerFormNum(null);
				((SaitenMasterUtil) ctx.getBean("saitenMasterUtil"))
						.updateUserSessionInfo(tranScorerSessionInfo);
			}

			// update user logging information in tran_scorer_access_log_info.
			for (ScorerAccessLogInfo scorer : loggedInScorers) {
				scorer.setLogoutTime(new Date());
				scorer.setStatus(WebAppConst.SCORER_LOGGING_STATUS[3]);
				((SaitenMasterUtil) ctx.getBean("saitenMasterUtil"))
						.updateUserLoggingInformation(scorer);
			}

			/*
			 * UserQuestionInfo userQuestionInfo = new UserQuestionInfo();
			 * userQuestionInfo.setScorerId(mstScorerInfo.getScorerId());
			 * List<SessionInformation> userSessions = sessionRegistry
			 * .getAllSessions(userQuestionInfo, false); for (SessionInformation
			 * userSession : userSessions) { UserQuestionInfo
			 * userQuestionInfoObj = new UserQuestionInfo(); userQuestionInfoObj
			 * = (UserQuestionInfo) userSession .getPrincipal(); QuestionInfo
			 * questionInfo = userQuestionInfoObj .getQuestionInfo(); if
			 * (questionInfo != null) {
			 * 
			 * if (!questionInfo.getMenuId().equals(
			 * WebAppConst.REFERENCE_SAMP_MENU_ID)) { int questionSeq =
			 * questionInfo.getQuestionSeq(); String connectionString =
			 * questionInfo .getConnectionString(); String lockBy =
			 * mstScorerInfo.getScorerId();
			 * 
			 * if (!StringUtils.isBlank(lockBy) &&
			 * !StringUtils.isBlank(connectionString)) { // unlock answer
			 * Integer answerSeq = null;
			 * UnlockAnswerUtil.unlockAnswer(questionSeq, lockBy,
			 * connectionString, answerSeq); } } String menuId =
			 * questionInfo.getMenuId(); if (menuId
			 * .equals(WebAppConst.SPECIAL_SCORING_OMR_READ_FAIL_MENU_ID) ||
			 * menuId .equals(WebAppConst.SPECIAL_SCORING_ENLARGE_TYPE_MENU_ID))
			 * {
			 * 
			 * String answerFormNum = userQuestionInfoObj .getAnswerFormNum();
			 * String subjectCode = questionInfo.getSubjectCode(); if
			 * (answerFormNum != null && subjectCode != null) {
			 * SaitenUtil.updateSpecialScoringMap(
			 * questionInfo.getSubjectCode(), answerFormNum); } } }
			 * 
			 * // update user logging information. ScorerAccessLogInfo
			 * scorerAccessLogInfo = userQuestionInfoObj
			 * .getScorerAccessLogInfo(); scorerAccessLogInfo.setLogoutTime(new
			 * Date()); scorerAccessLogInfo
			 * .setStatus(WebAppConst.SCORER_LOGGING_STATUS[3]);
			 * UnlockAnswerUtil
			 * .updateUserLoggingInformation(scorerAccessLogInfo);
			 * 
			 * userSession.expireNow(); }
			 */

			/* sessionRegistry.registerNewSession(sessionId, userQuestionInfo); */
		}
		return result;
	}

	/*
	 * public void setSessionRegistry(SessionRegistry sessionRegistry) {
	 * this.sessionRegistry = sessionRegistry; }
	 */

}
