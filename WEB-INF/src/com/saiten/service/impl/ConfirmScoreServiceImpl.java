package com.saiten.service.impl;

import java.util.LinkedHashMap;

import org.apache.struts2.ServletActionContext;

import com.saiten.bean.GradeResultKey;
import com.saiten.bean.SaitenConfig;
import com.saiten.exception.SaitenRuntimeException;
import com.saiten.info.GradeInfo;
import com.saiten.model.MstGrade;
import com.saiten.service.ConfirmScoreService;
import com.saiten.util.WebAppConst;
import com.saiten.util.ErrorCode;

/**
 * @author sachin
 * @version 1.0
 * @created 13-Dec-2012 11:53:29 AM
 */
public class ConfirmScoreServiceImpl implements ConfirmScoreService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.saiten.service.ConfirmScoreService#calculateBitValue(java.lang.String
	 * )
	 */
	public int calculateBitValue(String selectedCheckPoints) {
		try {
			int bitValue = WebAppConst.ZERO;
			String[] checkPoints = selectedCheckPoints.split(WebAppConst.COMMA);

			for (String checkPointValue : checkPoints) {
				bitValue += Math.pow(2, Integer.valueOf(checkPointValue));
			}
			return bitValue;
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.CONFIRM_SCORE_SERVICE_EXCEPTION, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saiten.service.ConfirmScoreService#findGradeAndResult(int, int)
	 */
	@Override
	public GradeInfo findGradeAndResult(int bitValue, int questionSeq) {
		GradeInfo gradeInfo = null;
		try {
			LinkedHashMap<GradeResultKey, MstGrade> mstGradeMap = ((SaitenConfig) ServletActionContext
					.getServletContext().getAttribute("saitenConfigObject"))
					.getMstGradeMap();

			GradeResultKey gradeResultKey = new GradeResultKey();
			gradeResultKey.setQuestionSeq(questionSeq);
			gradeResultKey.setBitValue(bitValue);

			gradeInfo = new GradeInfo();
			MstGrade mstGrade = mstGradeMap.get(gradeResultKey);

			if (mstGrade != null && mstGrade.getMstGradeResult() != null) {
				// Fetch gradeNum and result using questionSeq and bitValue
				gradeInfo.setGradeNum(String.valueOf(mstGrade
						.getMstGradeResult().getId().getGradeNum()));
				gradeInfo.setGradeSeq(mstGrade.getGradeSeq());
				gradeInfo.setResult(getScoreResult(mstGrade.getMstGradeResult()
						.getResult()));
			} else {
				// gradeNum and result will be '-', if it not available
				gradeInfo.setGradeNum(String.valueOf(WebAppConst.HYPHEN));
				gradeInfo.setResult(WebAppConst.HYPHEN);
			}
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.CONFIRM_SCORE_SERVICE_EXCEPTION, e);
		}

		return gradeInfo;
	}

	/**
	 * @param resultValue
	 * @return Character
	 */
	private Character getScoreResult(Character resultValue) {
		switch (resultValue) {
		case 'F':
			return WebAppConst.FAIL;
		case 'D':
			return WebAppConst.DOUBLE_CIRCLE;
		case 'S':
			return WebAppConst.SINGLE_CIRCLE;
		default:
			return null;
		}
	}

}