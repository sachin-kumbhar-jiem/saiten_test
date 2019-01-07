package com.saiten.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;

import com.saiten.dao.TranDescScoreDAO;
import com.saiten.exception.SaitenRuntimeException;
import com.saiten.service.GradeSelectionService;
import com.saiten.util.ErrorCode;
import com.saiten.util.WebAppConst;

/**
 * @author kailash
 * 
 */
public class GradeSelectionServiceImpl implements GradeSelectionService {
	
	private TranDescScoreDAO tranDescScoreDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.saiten.service.GradeSelectionService#findGradesByQuestionSeq(int)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Map<String, String> findGradesByQuestionSeq(int questionSeq, String gradeNumText, Short latestScoringState, Short selectedMarkValue, Short denyCategory, Integer inspectionGroupSeq, String connectionString, String scorerId) {

		// Map<String, String> map;

		try {

			List gradeList = tranDescScoreDAO.findGradesWithCountByQuestionSeq(questionSeq, latestScoringState, selectedMarkValue, denyCategory, inspectionGroupSeq, connectionString, scorerId);

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
				Object[] gradeObj = (Object[]) gradeListObj;
				String gradeNum = String.valueOf(gradeObj[0]);
				String answerCount = String.valueOf(gradeObj[1]);
				gradeMap.put(gradeNum, gradeNumText + gradeNum + WebAppConst.SINGLE_SPACE + WebAppConst.OPENING_BRACKET
						+ answerCount + WebAppConst.CLOSING_BRACKET);
			}
		}
		return gradeMap;
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
