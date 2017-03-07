package com.saiten.service.impl;

import java.util.List;

import org.hibernate.HibernateException;

import com.saiten.dao.MstQuestionDAO;
import com.saiten.exception.SaitenRuntimeException;
import com.saiten.service.DisplayPdfDocService;
import com.saiten.util.ErrorCode;
import com.saiten.util.WebAppConst;

/**
 * @author sachin
 * @version 1.0
 * @created 07-Dec-2012 10:33:35 AM
 */
public class DisplayPdfDocServiceImpl implements DisplayPdfDocService {

	private MstQuestionDAO mstQuestionDAO;

	@SuppressWarnings("rawtypes")
	public String fetchPdfDocInfo(int questionSeq, String docType) {

		try {
			List pdfDocInfoList = null;
			String manualFileName = null;
			String questionFileName = null;

			pdfDocInfoList = mstQuestionDAO.fetchPdfDocInfo(questionSeq);

			if (!pdfDocInfoList.isEmpty()) {
				Object[] pdfDocInfo = (Object[]) pdfDocInfoList.get(0);
				manualFileName = String.valueOf(pdfDocInfo[0]);
				questionFileName = String.valueOf(pdfDocInfo[1]);
			}
			return docType.equals(WebAppConst.HELP_DOC) ? manualFileName
					: questionFileName;
		} catch (HibernateException he) {
			throw new SaitenRuntimeException(
					ErrorCode.PDF_DOC_HIBERNATE_EXCEPTION, he);
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.PDF_DOC_SERVICE_EXCEPTION, e);
		}
	}

	public void setMstQuestionDAO(MstQuestionDAO mstQuestionDAO) {
		this.mstQuestionDAO = mstQuestionDAO;
	}

}