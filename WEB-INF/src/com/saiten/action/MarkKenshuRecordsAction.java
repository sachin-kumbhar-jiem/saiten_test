package com.saiten.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.saiten.exception.SaitenRuntimeException;
import com.saiten.info.AnswerInfo;
import com.saiten.info.KenshuRecordInfo;
import com.saiten.info.MstScorerInfo;
import com.saiten.info.QuestionInfo;
import com.saiten.info.TranDescScoreInfo;
import com.saiten.model.TranAcceptance;
import com.saiten.service.MarkKenshuRecordService;
import com.saiten.util.ErrorCode;
import com.saiten.util.WebAppConst;

public class MarkKenshuRecordsAction extends ActionSupport implements
		SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, Object> session;
	private KenshuRecordInfo kenshuRecordInfo;
	private MarkKenshuRecordService markKenshuRecordService;
	private String markComment;
	private TranDescScoreInfo tranDescScoreInfo;
	private Map<Integer, KenshuRecordInfo> kenshuRecordInfoMap;
	private TranAcceptance tranAcceptance;
	private MstScorerInfo scorerInfo;

	@SuppressWarnings("unchecked")
	public String doMark() {
		try {
			buildKenshuRecordInfo();

			kenshuRecordInfo.setMarkFlag(WebAppConst.VALID_FLAG);
			QuestionInfo questionInfo = (QuestionInfo) session
					.get("questionInfo");
			String connectionString = null;
			if (questionInfo != null) {
				connectionString = questionInfo.getConnectionString();
			}

			tranDescScoreInfo = (TranDescScoreInfo) session
					.get("tranDescScoreInfo");

			TranAcceptance tranAcceptance = (TranAcceptance) session
					.get("tranAcceptance");
			if (tranAcceptance != null) {
				tranAcceptance.setComment(markComment);
			}

			kenshuRecordInfo.setTranDescScoreInfo(tranDescScoreInfo);

			Integer acceptanceSeq = markKenshuRecordService.doMark(
					kenshuRecordInfo, connectionString);
			if (acceptanceSeq != null) {
				kenshuRecordInfo.setAcceptenceSeq(acceptanceSeq);
			}

			kenshuRecordInfoMap = (Map<Integer, KenshuRecordInfo>) session
					.get("kenshuRecordInfoMap");
			if (kenshuRecordInfoMap == null) {
				kenshuRecordInfoMap = new HashMap<Integer, KenshuRecordInfo>();
			}
			kenshuRecordInfoMap.put(kenshuRecordInfo.getAnswerSeq(),
					kenshuRecordInfo);
			session.put("kenshuRecordInfoMap", kenshuRecordInfoMap);

			session.put("kenshuRecordInfo", kenshuRecordInfo);
			session.put("tranAcceptance", tranAcceptance);
		} catch (SaitenRuntimeException we) {
			throw we;
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.KENSHU_RECORD_SERVICE_EXCEPTION, e);
		}
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String doUnmark() {

		try {
			buildKenshuRecordInfo();

			kenshuRecordInfo.setComment(null);

			kenshuRecordInfo.setMarkFlag(WebAppConst.DELETE_FLAG);
			QuestionInfo questionInfo = (QuestionInfo) session
					.get("questionInfo");
			String connectionString = null;
			if (questionInfo != null) {
				connectionString = questionInfo.getConnectionString();
			}
			markKenshuRecordService
					.doUnMark(kenshuRecordInfo, connectionString);

			kenshuRecordInfoMap = (Map<Integer, KenshuRecordInfo>) session
					.get("kenshuRecordInfoMap");
			if (kenshuRecordInfoMap == null) {
				kenshuRecordInfoMap = new HashMap<Integer, KenshuRecordInfo>();
			}
			kenshuRecordInfoMap.put(kenshuRecordInfo.getAnswerSeq(),
					kenshuRecordInfo);
			session.put("kenshuRecordInfoMap", kenshuRecordInfoMap);

			session.put("kenshuRecordInfo", kenshuRecordInfo);
		} catch (SaitenRuntimeException we) {
			throw we;
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.KENSHU_RECORD_SERVICE_EXCEPTION, e);
		}
		return SUCCESS;
	}

	public void buildKenshuRecordInfo() {
		kenshuRecordInfo = (KenshuRecordInfo) session.get("kenshuRecordinfo");
		if (kenshuRecordInfo != null) {
			kenshuRecordInfo.setUpdateDate(new Date());
		} else {
			kenshuRecordInfo = new KenshuRecordInfo();
			kenshuRecordInfo.setCreateDate(new Date());
			kenshuRecordInfo.setUpdateDate(new Date());
		}
		TranDescScoreInfo tranDescScoreInfo = (TranDescScoreInfo) session
				.get("tranDescScoreInfo");

		if (tranDescScoreInfo != null) {
			AnswerInfo answerInfo = tranDescScoreInfo.getAnswerInfo();
			if (answerInfo != null) {
				kenshuRecordInfo.setAnswerSeq(answerInfo.getAnswerSeq());
			}
		}

		MstScorerInfo mstScorerInfo = (MstScorerInfo) session.get("scorerInfo");
		if (mstScorerInfo != null) {
			kenshuRecordInfo.setMarkBy(mstScorerInfo.getScorerId());
		}

		kenshuRecordInfo.setComment(markComment);
	}

	public String setExplainFlag() {
		try {
			tranAcceptance = (TranAcceptance) session.get("tranAcceptance");
			scorerInfo = (MstScorerInfo) session.get("scorerInfo");

			QuestionInfo questionInfo = (QuestionInfo) session
					.get("questionInfo");
			String connectionString = null;
			if (questionInfo != null) {
				connectionString = questionInfo.getConnectionString();
			}

			tranAcceptance.setExplainFlag(WebAppConst.VALID_FLAG);
			tranAcceptance.setExplainBy(scorerInfo.getScorerId());

			markKenshuRecordService.updateRecord(tranAcceptance,
					connectionString);
		} catch (SaitenRuntimeException we) {
			throw we;
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.KENSHU_RECORD_SERVICE_EXCEPTION, e);
		}

		return null;
	}

	public String clearExplainFlag() {
		try {
			tranAcceptance = (TranAcceptance) session.get("tranAcceptance");

			QuestionInfo questionInfo = (QuestionInfo) session
					.get("questionInfo");
			String connectionString = null;
			if (questionInfo != null) {
				connectionString = questionInfo.getConnectionString();
			}

			tranAcceptance.setExplainFlag(WebAppConst.DELETE_FLAG);
			tranAcceptance.setExplainBy(null);

			markKenshuRecordService.updateRecord(tranAcceptance,
					connectionString);
		} catch (SaitenRuntimeException we) {
			throw we;
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.KENSHU_RECORD_SERVICE_EXCEPTION, e);
		}

		return null;
	}

	public KenshuRecordInfo getKenshuRecordInfo() {
		return kenshuRecordInfo;
	}

	public void setKenshuRecordInfo(KenshuRecordInfo kenshuRecordInfo) {
		this.kenshuRecordInfo = kenshuRecordInfo;
	}

	public String getMarkComment() {
		return markComment;
	}

	public void setMarkComment(String markComment) {
		this.markComment = markComment;
	}

	public MarkKenshuRecordService getMarkKenshuRecordService() {
		return markKenshuRecordService;
	}

	public void setMarkKenshuRecordService(
			MarkKenshuRecordService markKenshuRecordService) {
		this.markKenshuRecordService = markKenshuRecordService;
	}

	public TranDescScoreInfo getTranDescScoreInfo() {
		return tranDescScoreInfo;
	}

	public void setTranDescScoreInfo(TranDescScoreInfo tranDescScoreInfo) {
		this.tranDescScoreInfo = tranDescScoreInfo;
	}

	public Map<Integer, KenshuRecordInfo> getKenshuRecordInfoMap() {
		return kenshuRecordInfoMap;
	}

	public void setKenshuRecordInfoMap(
			Map<Integer, KenshuRecordInfo> kenshuRecordInfoMap) {
		this.kenshuRecordInfoMap = kenshuRecordInfoMap;
	}

	public TranAcceptance getTranAcceptance() {
		return tranAcceptance;
	}

	public void setTranAcceptance(TranAcceptance tranAcceptance) {
		this.tranAcceptance = tranAcceptance;
	}

	public MstScorerInfo getScorerInfo() {
		return scorerInfo;
	}

	public void setScorerInfo(MstScorerInfo scorerInfo) {
		this.scorerInfo = scorerInfo;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;

	}

	public Map<String, Object> getSession() {
		return session;
	}

}
