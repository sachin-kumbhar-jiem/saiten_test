package com.saiten.action;

import java.util.Date;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.opensymphony.xwork2.ActionSupport;
import com.saiten.exception.SaitenRuntimeException;
import com.saiten.info.MstScorerInfo;
import com.saiten.info.QuestionInfo;
import com.saiten.info.ScorerAccessLogInfo;
import com.saiten.model.TranScorerSessionInfo;
import com.saiten.service.ScorerLoggingService;
import com.saiten.util.AESEncryptionDecryptionUtil;
import com.saiten.util.ErrorCode;
import com.saiten.util.SaitenMasterUtil;
import com.saiten.util.UnlockAnswerUtil;
import com.saiten.util.WebAppConst;

/**
 * @author sachin
 * @version 1.0
 * @created 13-Dec-2012 11:53:29 AM
 */
public class LogOutAction extends ActionSupport implements SessionAware {

	private static Logger log = Logger.getLogger(LogOutAction.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Map<String, Object> session;

	private String saitenLMSUrl;

	private Properties saitenApplicationProperties;

	private boolean backToLms;

	private ScorerLoggingService scorerLoggingService;

	@SuppressWarnings("rawtypes")
	public String logOut() throws Exception {

		String RESULT = null;

		if (session instanceof SessionMap) {
			try {

				ApplicationContext ctx = ContextLoader
						.getCurrentWebApplicationContext();

				MstScorerInfo mstScorerInfo = (MstScorerInfo) session
						.get("scorerInfo");

				TranScorerSessionInfo tranScorerSessionInfo = ((SaitenMasterUtil) ctx
						.getBean("saitenMasterUtil"))
						.getUserSessionInfoById(mstScorerInfo.getScorerId());

				QuestionInfo questionInfo = (QuestionInfo) session
						.get("questionInfo");

				if (questionInfo != null
						&& !questionInfo.getMenuId().equals(
								WebAppConst.REFERENCE_SAMP_MENU_ID)) {
					int questionSeq = questionInfo.getQuestionSeq();
					String connectionString = questionInfo
							.getConnectionString();
					String lockBy = ((MstScorerInfo) session.get("scorerInfo"))
							.getScorerId();

					if (!StringUtils.isBlank(lockBy)
							&& !StringUtils.isBlank(connectionString)) {
						// unlock answer
						Integer answerSeq = null;
						UnlockAnswerUtil.unlockAnswer(questionSeq, lockBy,
								connectionString, answerSeq);
						/*
						 * MstQuestion mstQuestion = null;
						 * tranScorerSessionInfo.setMstQuestion(mstQuestion);
						 */
					}

					/*
					 * String menuId = questionInfo.getMenuId(); if (menuId
					 * .equals
					 * (WebAppConst.SPECIAL_SCORING_OMR_READ_FAIL_MENU_ID) ||
					 * menuId
					 * .equals(WebAppConst.SPECIAL_SCORING_ENLARGE_TYPE_MENU_ID
					 * )) {
					 * 
					 * String answerFormNum = (String) session
					 * .get("answerFormNum");
					 * 
					 * 
					 * 
					 * SaitenUtil.updateSpecialScoringMap(
					 * questionInfo.getSubjectCode(), answerFormNum);
					 * 
					 * tranScorerSessionInfo.setAnswerFormNum(null);
					 * tranScorerSessionInfo.setSubjectCode(null); }
					 */
				}
				tranScorerSessionInfo.setQuestionSeq(null);
				tranScorerSessionInfo.setAnswerFormNum(null);
				tranScorerSessionInfo.setSubjectCode(null);
				tranScorerSessionInfo.setUpdateDate(new Date());
				// Clear questionSeq, AnswerFormNum, SubjectCoed from
				// tran_scorer_session_info.
				((SaitenMasterUtil) ctx.getBean("saitenMasterUtil"))
						.updateUserSessionInfo(tranScorerSessionInfo);

				// update user logging information.
				ScorerAccessLogInfo scorerAccessLogInfo = (ScorerAccessLogInfo) session
						.get("scorerAccessLogInfo");
				scorerAccessLogInfo.setLogoutTime(new Date());
				scorerAccessLogInfo
						.setStatus(WebAppConst.SCORER_LOGGING_STATUS[1]);
				scorerLoggingService.saveOrUpdate(scorerAccessLogInfo);
				String scorerId = mstScorerInfo.getScorerId();
				String password = mstScorerInfo.getPassword();
				if (session.get("saitenLoginEnabled") == null) {

					/*
					 * saitenLMSUrl = saitenApplicationProperties
					 * .getProperty(WebAppConst.SAITEN_LMS_INDEX_PAGE_URL);
					 */
					Integer lmsInstanceId = (Integer) session
							.get("lmsInstanceId");

					if (backToLms) {
						saitenLMSUrl = scorerLoggingService
								.getUrlById(lmsInstanceId);
						scorerId = AESEncryptionDecryptionUtil
								.encrypt(scorerId);

						saitenLMSUrl += "?login=" + scorerId + "&password="
								+ password;
						log.info(scorerId
								+ "-"
								+ "BackToLms. User Logout. Redirecting to Lms. LMS URL: "
								+ saitenLMSUrl);
						Thread.sleep(2000);
					} else {
						saitenLMSUrl = saitenApplicationProperties
								.getProperty(WebAppConst.SAITEN_LMS_INDEX_PAGE_URL);
						log.info(scorerId + "-"
								+ "User Logout. Redirecting to Lms. LMS URL: "
								+ saitenLMSUrl);
					}
					RESULT = SUCCESS;
				} else {
					log.info(scorerId + "-"
							+ "User Logout. Loading saiten login page.");
					RESULT = INPUT;
				}

				// clear session to avoid repeated execution of code in
				// listener
				session.clear();

				// invalidate session
				((SessionMap) session).invalidate();
			} catch (SaitenRuntimeException we) {
				throw we;
			} catch (Exception e) {
				throw new SaitenRuntimeException(
						ErrorCode.LOGOUT_ACTION_EXCEPTION, e);
			}
		}
		return RESULT;
	}

	/**
	 * 
	 * @param session
	 */
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public String getSaitenLMSUrl() {
		return saitenLMSUrl;
	}

	public void setSaitenLMSUrl(String saitenLMSUrl) {
		this.saitenLMSUrl = saitenLMSUrl;
	}

	public void setSaitenApplicationProperties(
			Properties saitenApplicationProperties) {
		this.saitenApplicationProperties = saitenApplicationProperties;
	}

	public boolean isBackToLms() {
		return backToLms;
	}

	public void setBackToLms(boolean backToLms) {
		this.backToLms = backToLms;
	}

	/**
	 * @param scorerLoggingService
	 *            the scorerLoggingService to set
	 */
	public void setScorerLoggingService(
			ScorerLoggingService scorerLoggingService) {
		this.scorerLoggingService = scorerLoggingService;
	}

}
