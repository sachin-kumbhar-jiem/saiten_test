package com.saiten.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;

import com.saiten.dao.MstGradeResultDAO;
import com.saiten.exception.SaitenRuntimeException;
import com.saiten.service.GradeSelectionService;
import com.saiten.util.ErrorCode;
import com.saiten.util.SaitenUtil;

/**
 * @author kailash
 * 
 */
public class GradeSelectionServiceImpl implements GradeSelectionService {

	@SuppressWarnings("unused")
	private MstGradeResultDAO mstGradeResultDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.saiten.service.GradeSelectionService#findGradesByQuestionSeq(int)
	 */
	@Override
	public Map<String, String> findGradesByQuestionSeq(int questionSeq, String gradeNumText) {
		Map<String, String> map;
		try {
			/*
			 * List gradeList = mstGradeResultDAO
			 * .findGradesByQuestionSeq(questionSeq);
			 */

			map = SaitenUtil.getGradeMapByQuestionSeq(questionSeq, gradeNumText);

			// return buildGradeMap(gradeList, gradeNumText);
		} catch (HibernateException he) {
			throw new SaitenRuntimeException(ErrorCode.GRADE_SELECTION_HIBERNATE_EXCEPTION, he);
		} catch (Exception e) {
			throw new SaitenRuntimeException(ErrorCode.GRADE_SELECTION_SERVICE_EXCEPTION, e);
		}
		return map;
	}

	/**
	 * @param gradeList
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unused" })
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
	 * @param saitenGlobalProperties
	 */
}
