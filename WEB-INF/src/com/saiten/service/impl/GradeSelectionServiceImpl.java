package com.saiten.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;

import com.saiten.dao.MstGradeResultDAO;
import com.saiten.exception.SaitenRuntimeException;
import com.saiten.service.GradeSelectionService;
import com.saiten.util.ErrorCode;

/**
 * @author kailash
 * 
 */
public class GradeSelectionServiceImpl implements GradeSelectionService {

	private MstGradeResultDAO mstGradeResultDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.saiten.service.GradeSelectionService#findGradesByQuestionSeq(int)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Map<Integer, String> findGradesByQuestionSeq(int questionSeq,
			String gradeNumText) {

		try {
			List gradeList = mstGradeResultDAO
					.findGradesByQuestionSeq(questionSeq);

			return buildGradeMap(gradeList, gradeNumText);
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
	private Map<Integer, String> buildGradeMap(List gradeList,
			String gradeNumText) {
		Map<Integer, String> gradeMap = new LinkedHashMap<Integer, String>();

		if (!gradeList.isEmpty()) {
			for (Object gradeListObj : gradeList) {
				Integer gradeNum = (Integer) gradeListObj;
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
	 * @param saitenGlobalProperties
	 */
}
