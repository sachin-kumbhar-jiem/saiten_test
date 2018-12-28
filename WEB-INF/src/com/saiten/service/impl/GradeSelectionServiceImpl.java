package com.saiten.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;

import com.saiten.dao.MstGradeResultDAO;
import com.saiten.dao.TranDescScoreDAO;
import com.saiten.exception.SaitenRuntimeException;
import com.saiten.service.GradeSelectionService;
import com.saiten.util.ErrorCode;

/**
 * @author kailash
 * 
 */
public class GradeSelectionServiceImpl implements GradeSelectionService {

	private MstGradeResultDAO mstGradeResultDAO;
	
	private TranDescScoreDAO tranDescScoreDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.saiten.service.GradeSelectionService#findGradesByQuestionSeq(int)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Map<String, String> findGradesByQuestionSeq(int questionSeq, String gradeNumText, Short latestScoringState, String connectionString) {

		// Map<String, String> map;

		try {

			List gradeList = tranDescScoreDAO.findGradesWithCountByQuestionSeq(questionSeq, latestScoringState, connectionString);

			// map = SaitenUtil.getGradeMapByQuestionSeq(questionSeq,
			// gradeNumText);

			return buildGradeMap(gradeList, gradeNumText);
		} catch (HibernateException he) {
			throw new SaitenRuntimeException(ErrorCode.GRADE_SELECTION_HIBERNATE_EXCEPTION, he);
		} catch (Exception e) {
			throw new SaitenRuntimeException(ErrorCode.GRADE_SELECTION_SERVICE_EXCEPTION, e);
		}

	}

	/**
	 * @param gradeList
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	private Map<String, String> buildGradeMap(List gradeList, String gradeNumText) {
		Map<String, String> gradeMap = new LinkedHashMap<String, String>();

		if (!gradeList.isEmpty()) {
			for (Object gradeListObj : gradeList) {
				// String gradeNum = (String) gradeListObj;
				String gradeNum = String.valueOf(gradeListObj);
				gradeMap.put(gradeNum, gradeNumText + gradeNum);
			}
		}
		return gradeMap;
	}

	/**
	 * @param mstGradeResultDAO
	 */
	public void setMstGradeResultDAO(MstGradeResultDAO mstGradeResultDAO) {
		this.mstGradeResultDAO = mstGradeResultDAO;
	}

	/**
	 * @param tranDescScoreDAO the tranDescScoreDAO to set
	 */
	public void setTranDescScoreDAO(TranDescScoreDAO tranDescScoreDAO) {
		this.tranDescScoreDAO = tranDescScoreDAO;
	}

	/**
	 * @param saitenGlobalProperties
	 */
}
