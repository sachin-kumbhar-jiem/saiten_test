package com.saiten.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;

import com.saiten.dao.TranDescScoreHistoryDAO;
import com.saiten.exception.SaitenRuntimeException;
import com.saiten.service.InspectionGroupSeqSelectionService;
import com.saiten.util.ErrorCode;
import com.saiten.util.WebAppConst;

/**
 * @author sachin
 * 
 */
public class InspectionGroupSeqSelectionServiceImpl implements InspectionGroupSeqSelectionService {

	private TranDescScoreHistoryDAO tranDescScoreHistoryDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.saiten.service.GradeSelectionService#fetchInspectionGroupSeqAndCount(int)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Map<String, String> fetchInspectionGroupSeqAndCount(int questionSeq,
			String scorerId, String connectionString) {

		try {
			List inspectionGroupSeqList = tranDescScoreHistoryDAO
					.fetchInspectionGroupSeqAndCount(questionSeq, scorerId, connectionString);

			return buildInspectionGroupSeqMap(inspectionGroupSeqList);
		} catch (HibernateException he) {
			throw new SaitenRuntimeException(
					ErrorCode.GRADE_SELECTION_HIBERNATE_EXCEPTION, he);
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.GRADE_SELECTION_SERVICE_EXCEPTION, e);
		}
	}

	/**
	 * @param gradeList
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private Map<String, String> buildInspectionGroupSeqMap(List inspectionGroupSeqList) {
		Map<String, String> inspectionGroupSeqMap = new LinkedHashMap<String, String>();

		if (!inspectionGroupSeqList.isEmpty()) {
			for (Object inspectionGroupSeqObject : inspectionGroupSeqList) {
				Object[] inspectionGroupSeqObj = (Object[]) inspectionGroupSeqObject;
				inspectionGroupSeqMap.put(String.valueOf(inspectionGroupSeqObj[0]),
						inspectionGroupSeqObj[0] + WebAppConst.SINGLE_SPACE
								+ WebAppConst.OPENING_BRACKET
								+ inspectionGroupSeqObj[1]
								+ WebAppConst.CLOSING_BRACKET);
			}
		}
		return inspectionGroupSeqMap;
	}
	

	public void setTranDescScoreHistoryDAO(
			TranDescScoreHistoryDAO tranDescScoreHistoryDAO) {
		this.tranDescScoreHistoryDAO = tranDescScoreHistoryDAO;
	}

	/**
	 * @param tranDescScoreHistoryDAO
	 */
	/*public void setTranDescScoreHistoryDAO(
			TranDescScoreHistoryDAO tranDescScoreHistoryDAO) {
		this.tranDescScoreHistoryDAO = tranDescScoreHistoryDAO;
	}*/
}
