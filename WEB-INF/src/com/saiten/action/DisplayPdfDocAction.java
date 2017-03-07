package com.saiten.action;

import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.opensymphony.xwork2.ActionSupport;
import com.saiten.exception.SaitenRuntimeException;
import com.saiten.info.QuestionInfo;
import com.saiten.service.DisplayPdfDocService;
import com.saiten.util.ErrorCode;
import com.saiten.util.WebAppConst;

/**
 * @author sachin
 * @version 1.0
 * @created 07-Dec-2012 10:33:34 AM
 */
public class DisplayPdfDocAction extends ActionSupport implements SessionAware {

	private static Logger log = Logger.getLogger(DisplayPdfDocAction.class);

	private static final long serialVersionUID = -6171214561484747552L;

	private String docType;

	private DisplayPdfDocService displayPdfDocService;

	private String url;

	private Map<String, Object> session;

	public String onLoad() {
		QuestionInfo questionInfo = (QuestionInfo) session.get("questionInfo");

		try {
			String pdfDocName = displayPdfDocService.fetchPdfDocInfo(questionInfo.getQuestionSeq(), docType);

			ApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
			Properties applicationProperties = (Properties) ctx.getBean("saitenApplicationProperties");
			Properties gloablProperties = (Properties) ctx.getBean("saitenGlobalProperties");

			String forwardSlash = (String) gloablProperties.get("label.forwardslash");
			StringBuilder urlBuilder = new StringBuilder();

			if (docType.equals(WebAppConst.HELP_DOC)) {
				// Get manual file location
				urlBuilder.append(applicationProperties.get(WebAppConst.SAITEN_MANUALFILE_URL));
			} else {
				// Get question file location
				urlBuilder.append(applicationProperties.get(WebAppConst.SAITEN_QUESTIONFILE_URL));
			}

			// Append pdfDocName
			urlBuilder.append(forwardSlash);
			urlBuilder.append(pdfDocName);

			log.info("Pdf document display info" + " - { Question Sequence: " + questionInfo.getQuestionSeq()
					+ ", docType: " + docType + ", url: " + urlBuilder + "}");

			// Set url
			setUrl(urlBuilder.toString());
		} catch (SaitenRuntimeException we) {
			throw we;
		} catch (Exception e) {
			throw new SaitenRuntimeException(ErrorCode.PDF_DOC_ACTION_EXCEPTION, e);
		}

		return SUCCESS;
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

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public void setDisplayPdfDocService(DisplayPdfDocService displayPdfDocService) {
		this.displayPdfDocService = displayPdfDocService;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}