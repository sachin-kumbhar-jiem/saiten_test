package com.saiten.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.saiten.exception.SaitenRuntimeException;
import com.saiten.info.MstScorerInfo;
import com.saiten.model.TranScorerSessionInfo;
import com.saiten.service.UserMenuService;
import com.saiten.util.ErrorCode;
import com.saiten.util.SaitenMasterUtil;
import com.saiten.util.SaitenUtil;
import com.saiten.util.WebAppConst;

/**
 * @author sachin
 * @version 1.0
 * @created 07-Dec-2012 10:33:34 AM
 */
public class UserMenuAction extends ActionSupport implements SessionAware {

	private static Logger log = Logger
			.getLogger(UserMenuAction.class);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8985339395821445616L;

	private List<String> userMenuIdList;

	private UserMenuService userMenuService;

	private Map<String, Object> session;

	public String showUserMenu() {
		byte roleId = 0;
		try {
			SaitenUtil.clearSessionInfo();
			// clear question_seq, answer_form_num & subject_code from
			// tran_scorer_session_info table.
			clearScorerSessionInfo();
			MstScorerInfo mstScorerInfo = (MstScorerInfo) session
					.get("scorerInfo");
			if (mstScorerInfo != null) {
				roleId = mstScorerInfo.getRoleId();
			}
			userMenuIdList = userMenuService.getUserMenuIdList(roleId);

			Boolean saitenRelease = Boolean.valueOf(SaitenUtil
					.getPropertyFromPropertyFile(
							WebAppConst.APPLICATION_PROPERTIES_FILE,
							WebAppConst.SAITEN_RELEASE));
			Boolean shinEigoRelease = Boolean.valueOf(SaitenUtil
					.getPropertyFromPropertyFile(
							WebAppConst.APPLICATION_PROPERTIES_FILE,
							WebAppConst.SHINEIGO_RELEASE));
			log.info(mstScorerInfo.getScorerId()+"-"+"Loaded User Menu Screen.");
			if (saitenRelease) {
				return "saiten-success";
			} else if (shinEigoRelease) {
				return "shineigo-success";
			}
			return SUCCESS;
		} catch (SaitenRuntimeException we) {
			throw we;
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.USER_MENU_ACTION_EXCEPTION, e);
		}
	}

	private void clearScorerSessionInfo() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		ApplicationContext ctx = ContextLoader
				.getCurrentWebApplicationContext();
		TranScorerSessionInfo tranScorerSessionInfo = (TranScorerSessionInfo) session
				.get("tranScorerSessionInfo");
		if (tranScorerSessionInfo != null) {
			tranScorerSessionInfo.setMstQuestion(null);
			tranScorerSessionInfo.setAnswerFormNum(null);
			tranScorerSessionInfo.setSubjectCode(null);
			tranScorerSessionInfo.setUpdateDate(new Date());
			((SaitenMasterUtil) ctx.getBean("saitenMasterUtil"))
					.updateUserSessionInfo(tranScorerSessionInfo);
			session.put("tranScorerSessionInfo", tranScorerSessionInfo);
		}
	}

	public List<String> getUserMenuIdList() {
		return userMenuIdList;
	}

	/**
	 * @param userMenuIdList
	 */
	public void setUserMenuIdLIst(List<String> userMenuIdList) {
		this.userMenuIdList = userMenuIdList;
	}

	/**
	 * @param userMenuService
	 */
	public void setUserMenuService(UserMenuService userMenuService) {
		this.userMenuService = userMenuService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.struts2.interceptor.SessionAware#setSession(java.util.Map)
	 */
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

}