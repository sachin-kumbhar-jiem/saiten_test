package com.saiten.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;

import com.saiten.dao.MstDenyCategoryDAO;
import com.saiten.service.DenyCategorySelectionService;
import com.saiten.util.SaitenUtil;

public class DenyCategorySelectionServiceImpl implements
		DenyCategorySelectionService {

	private MstDenyCategoryDAO mstDenyCategoryDAO;

	@SuppressWarnings("rawtypes")
	@Override
	public Map<Short, String> findDenyCategoriesByQuestionSeq(int questionSeq) {
		try {
			List denyCategoryList = mstDenyCategoryDAO
					.findDenyCategoriesByQuestionSeq(questionSeq);
			return buildDenyCategoryMap(denyCategoryList);
		} catch (Exception e) {

		}
		return null;
	}

	/**
	 * @param denyCategoryList
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private Map<Short, String> buildDenyCategoryMap(List denyCategoryList) {
		Map<Short, String> denyCategoryMap = new LinkedHashMap<Short, String>();

		if (!denyCategoryList.isEmpty()) {
			for (Object denyCategoryListObj : denyCategoryList) {
				Object[] denyCategoryObj = (Object[]) denyCategoryListObj;
				Short denyCategory = (Short) denyCategoryObj[0];
				Map<String, String> configMap = SaitenUtil.getConfigMap();
				/*
				 * boolean removeTagsFromDescription = Boolean
				 * .valueOf(configMap.get("removeTagsFromDescription"));
				 * if(removeTagsFromDescription){
				 */
				// commented this code. now this will be used for zenkoku also.
				/*
				 * denyCategoryMap.put(denyCategory,
				 * (String.valueOf(denyCategoryObj
				 * [1])).replaceAll("\\<.*?>",""));
				 */
				String denyDescrption = String.valueOf(denyCategoryObj[1]);
				denyDescrption = denyDescrption.replaceAll("<br>[&nbsp;]+", "&nbsp;");
				denyDescrption = denyDescrption.replaceAll("<(.|\n)*?>", "");
				denyCategoryMap.put(denyCategory, denyDescrption);
				/*
				 * }else{ denyCategoryMap.put(denyCategory,
				 * (String.valueOf(denyCategoryObj[1]))); }
				 */

			}
		}
		return denyCategoryMap;
	}

	public MstDenyCategoryDAO getMstDenyCategoryDAO() {
		return mstDenyCategoryDAO;
	}

	public void setMstDenyCategoryDAO(MstDenyCategoryDAO mstDenyCategoryDAO) {
		this.mstDenyCategoryDAO = mstDenyCategoryDAO;
	}

}
