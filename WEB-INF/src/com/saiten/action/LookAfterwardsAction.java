/**
 * 
 */
package com.saiten.action;

import java.util.Date;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.saiten.exception.SaitenRuntimeException;
import com.saiten.info.AnswerInfo;
import com.saiten.info.LookAfterwardsInfo;
import com.saiten.info.MstScorerInfo;
import com.saiten.info.QuestionInfo;
import com.saiten.info.TranDescScoreInfo;
import com.saiten.service.LookAfterwardsService;
import com.saiten.util.ErrorCode;
import com.saiten.util.WebAppConst;

/**
 * @author user
 * 
 */
public class LookAfterwardsAction extends ActionSupport implements SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, Object> session;
	private LookAfterwardsService lookAfterwardsService;
	private LookAfterwardsInfo lookAfterwardsInfo;
	private String markComment;

	public String doMark() {

		try {
			buildLookAfterwardsInfo();
			lookAfterwardsInfo.setLookAftFlag(WebAppConst.VALID_FLAG);

			QuestionInfo questionInfo = (QuestionInfo) session
					.get("questionInfo");
			String connectionString = null;
			if (questionInfo != null) {
				connectionString = questionInfo.getConnectionString();
			}
			Integer lookAftSeq = lookAfterwardsService.doMark(
					lookAfterwardsInfo, connectionString);
			if (lookAftSeq != null) {
				lookAfterwardsInfo.setLookAftSeq(lookAftSeq);
			}
			session.put("lookAfterwardsInfo", lookAfterwardsInfo);
			return SUCCESS;
		} catch (SaitenRuntimeException we) {
			throw we;
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.LOOK_AFTERWARDS_ACTION_EXCEPTION, e);
		}
	}

	public String doUnmark() {
		try {
			buildLookAfterwardsInfo();
			lookAfterwardsInfo.setLookAftFlag(WebAppConst.DELETE_FLAG);

			QuestionInfo questionInfo = (QuestionInfo) session
					.get("questionInfo");
			String connectionString = null;
			if (questionInfo != null) {
				connectionString = questionInfo.getConnectionString();
			}
			lookAfterwardsService
					.doUnmark(lookAfterwardsInfo, connectionString);
			session.put("lookAfterwardsInfo", lookAfterwardsInfo);
			return SUCCESS;
		} catch (SaitenRuntimeException we) {
			throw we;
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.LOOK_AFTERWARDS_ACTION_EXCEPTION, e);
		}
	}

	public String unmarkAll() {
		try {
			TranDescScoreInfo tranDescScoreInfo = (TranDescScoreInfo) session
					.get("tranDescScoreInfo");
			lookAfterwardsInfo = new LookAfterwardsInfo();
			if (tranDescScoreInfo != null) {
				AnswerInfo answerInfo = tranDescScoreInfo.getAnswerInfo();
				if (answerInfo != null) {
					lookAfterwardsInfo.setAnswerSeq(answerInfo.getAnswerSeq());
				}

				MstScorerInfo mstScorerInfo = (MstScorerInfo) session
						.get("scorerInfo");
				if (mstScorerInfo != null) {
					lookAfterwardsInfo.setUpdatePersonId(mstScorerInfo
							.getScorerId());
				}
				lookAfterwardsInfo.setUpdateDate(new Date());
				lookAfterwardsInfo.setLookAftFlag(WebAppConst.DELETE_FLAG);
				lookAfterwardsInfo.setResolvedBy(mstScorerInfo
							.getScorerId());
				lookAfterwardsInfo.setResolvedFlag(WebAppConst.VALID_FLAG);

				QuestionInfo questionInfo = (QuestionInfo) session
						.get("questionInfo");
				String connectionString = null;
				if (questionInfo != null) {
					connectionString = questionInfo.getConnectionString();
				}
				lookAfterwardsService.unamrkAll(lookAfterwardsInfo,
						connectionString);
			}

			return SUCCESS;
		} catch (SaitenRuntimeException we) {
			throw we;
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.LOOK_AFTERWARDS_ACTION_EXCEPTION, e);
		}
	}

	private void buildLookAfterwardsInfo() {

		lookAfterwardsInfo = (LookAfterwardsInfo) session
				.get("lookAfterwardsInfo");
		if (lookAfterwardsInfo != null) {
			lookAfterwardsInfo.setUpdateDate(new Date());
		} else {
			lookAfterwardsInfo = new LookAfterwardsInfo();
			lookAfterwardsInfo.setCreateDate(new Date());
			lookAfterwardsInfo.setUpdateDate(new Date());
		}

		TranDescScoreInfo tranDescScoreInfo = (TranDescScoreInfo) session
				.get("tranDescScoreInfo");
		if (tranDescScoreInfo != null) {
			AnswerInfo answerInfo = tranDescScoreInfo.getAnswerInfo();
			if (answerInfo != null) {
				lookAfterwardsInfo.setAnswerSeq(answerInfo.getAnswerSeq());
			}
		}

		MstScorerInfo mstScorerInfo = (MstScorerInfo) session.get("scorerInfo");
		if (mstScorerInfo != null) {
			lookAfterwardsInfo.setUpdatePersonId(mstScorerInfo.getScorerId());
		}

		lookAfterwardsInfo.setComment(markComment);

	}

	/**
	 * 
	 * @param session
	 */
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	/**
	 * @param lookAfterwardsService
	 *            the lookAfterwardsService to set
	 */
	public void setLookAfterwardsService(
			LookAfterwardsService lookAfterwardsService) {
		this.lookAfterwardsService = lookAfterwardsService;
	}

	/**
	 * @return the lookAfterwardsInfo
	 */
	public LookAfterwardsInfo getLookAfterwardsInfo() {
		return lookAfterwardsInfo;
	}

	/**
	 * @param lookAfterwardsInfo
	 *            the lookAfterwardsInfo to set
	 */
	public void setLookAfterwardsInfo(LookAfterwardsInfo lookAfterwardsInfo) {
		this.lookAfterwardsInfo = lookAfterwardsInfo;
	}

	/**
	 * @return the markComment
	 */
	public String getMarkComment() {
		return markComment;
	}

	/**
	 * @param markComment
	 *            the markComment to set
	 */
	public void setMarkComment(String markComment) {
		this.markComment = markComment;
	}

}
