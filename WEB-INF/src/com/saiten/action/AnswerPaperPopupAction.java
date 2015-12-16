package com.saiten.action;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.saiten.info.QuestionInfo;
import com.saiten.info.TranDescScoreInfo;
import com.saiten.model.MstTestsetnumQuestion;
import com.saiten.service.ScoreService;
import com.saiten.util.SaitenUtil;

public class AnswerPaperPopupAction extends ActionSupport implements
SessionAware, ServletRequestAware {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, Object> session;
	private TranDescScoreInfo tranDescScoreInfo;
	private ScoreService scoreService;
	private MstTestsetnumQuestion mstTestsetnumQuestion;
	

	public String showAnswerPaperPopup () {
		TranDescScoreInfo tranDescScoreInfo = (TranDescScoreInfo)session.get("tranDescScoreInfo");
		QuestionInfo questionInfo = (QuestionInfo)session.get("questionInfo");
		LinkedHashMap<Integer, MstTestsetnumQuestion> mstTestsetnumQuestionMap =  new LinkedHashMap<Integer, MstTestsetnumQuestion>();
		mstTestsetnumQuestionMap = SaitenUtil.getSaitenConfigObject().getMstTestsetnumQuestionMap();
		
		/*System.out.println("this is working");
		System.out.println(tranDescScoreInfo.getAnswerInfo().getAnswerSeq());
		System.out.println(questionInfo.getConnectionString());*/
		int temp =scoreService.findTestsetNumSeq(tranDescScoreInfo.getAnswerInfo().getAnswerSeq(),questionInfo.getConnectionString());
		/*System.out.println(temp);
		System.out.println(mstTestsetnumQuestionMap.size());*/
		setMstTestsetnumQuestion(mstTestsetnumQuestionMap.get(temp));;
		session.put("mstTestsetnumQuestion", mstTestsetnumQuestion);
		System.out.println(mstTestsetnumQuestion.getSide());
		return SUCCESS;
	}

	public TranDescScoreInfo getTranDescScoreInfo() {
		return tranDescScoreInfo;
	}


	public void setTranDescScoreInfo(TranDescScoreInfo tranDescScoreInfo) {
		this.tranDescScoreInfo = tranDescScoreInfo;
	}


	public ScoreService getScoreService() {
		return scoreService;
	}

	public void setScoreService(ScoreService scoreService) {
		this.scoreService = scoreService;
	}

	public MstTestsetnumQuestion getMstTestsetnumQuestion() {
		return mstTestsetnumQuestion;
	}

	public void setMstTestsetnumQuestion(MstTestsetnumQuestion mstTestsetnumQuestion) {
		this.mstTestsetnumQuestion = mstTestsetnumQuestion;
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session=session;
		
	}
}
