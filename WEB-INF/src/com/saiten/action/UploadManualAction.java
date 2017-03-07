package com.saiten.action;

import java.io.File;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.saiten.exception.SaitenRuntimeException;
import com.saiten.info.MstScorerInfo;
import com.saiten.manager.SaitenTransactionManager;
import com.saiten.service.UploadManualService;
import com.saiten.util.ErrorCode;
import com.saiten.util.WebAppConst;

public class UploadManualAction extends ActionSupport implements SessionAware {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6671405766416067813L;
	private File manual;
	private String questionSequence;
	private String manualType;
	private UploadManualService uploadManualService;
	private String manualFileName;
	private Map<String, Object> session;
	private SaitenTransactionManager saitenTransactionManager;

	public String execute() {

		PlatformTransactionManager platformTransactionManager = null;
		TransactionStatus transactionStatus = null;

		try {
			String[] selectedQuestion = questionSequence.split(WebAppConst.COLON);
			int questionSeq = Integer.valueOf(selectedQuestion[0]);

			MstScorerInfo scorerInfo = (MstScorerInfo) session.get("scorerInfo");
			String scorerId = scorerInfo.getScorerId();

			platformTransactionManager = saitenTransactionManager.getTransactionManger();
			transactionStatus = saitenTransactionManager.beginTransaction(platformTransactionManager);

			uploadManualService.uploadManual(questionSeq, manual, manualFileName, manualType, scorerId);

			platformTransactionManager.commit(transactionStatus);

		} catch (SaitenRuntimeException we) {
			if (platformTransactionManager != null)
				platformTransactionManager.rollback(transactionStatus);

			throw we;
		} catch (Exception e) {
			if (platformTransactionManager != null)
				platformTransactionManager.rollback(transactionStatus);

			throw new SaitenRuntimeException(ErrorCode.MANUAL_UPLOAD_ACTION_EXCEPTION, e);
		}

		return SUCCESS;
	}

	public File getManual() {
		return manual;
	}

	@RequiredFieldValidator(key = "error.manualupload.mandatoryfilefield")
	public void setManual(File manual) {
		this.manual = manual;
	}

	public String getQuestionSequence() {
		return questionSequence;
	}

	public void setQuestionSequence(String questionSequence) {
		this.questionSequence = questionSequence;
	}

	public String getManualType() {
		return manualType;
	}

	public void setManualType(String manualType) {
		this.manualType = manualType;
	}

	public String getManualFileName() {
		return manualFileName;
	}

	@StringLengthFieldValidator(key = "error.manualupload.maxfilenamelength", maxLength = "80")
	public void setManualFileName(String manualFileName) {
		this.manualFileName = manualFileName;
	}

	public UploadManualService getUploadManualService() {
		return uploadManualService;
	}

	public void setUploadManualService(UploadManualService uploadManualService) {
		this.uploadManualService = uploadManualService;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public void setSaitenTransactionManager(SaitenTransactionManager saitenTransactionManager) {
		this.saitenTransactionManager = saitenTransactionManager;
	}
}