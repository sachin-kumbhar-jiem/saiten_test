package com.saiten.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;
import org.hibernate.HibernateException;

import com.saiten.dao.MstPendingCategoryDAO;
import com.saiten.exception.SaitenRuntimeException;
import com.saiten.service.PendingCategorySelectionService;
import com.saiten.util.ErrorCode;
import com.saiten.util.SaitenUtil;

public class PendingCategorySelectionServiceImpl implements
		PendingCategorySelectionService {

	private MstPendingCategoryDAO mstPendingCategoryDAO;

	@SuppressWarnings("rawtypes")
	@Override
	public Map<Short, String> findPendingCategoriesByQuestionSeq(int questionSeq) {

		try {
			List pendingCategoryList = mstPendingCategoryDAO
					.findPendingCategoriesByQuestionSeq(questionSeq);

			return buildPendingCategoryMap(pendingCategoryList);
		} catch (HibernateException he) {
			throw new SaitenRuntimeException(
					ErrorCode.PENDING_CATEGORY_SELECTION_HIBERNATE_EXCEPTION,
					he);
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.PENDING_CATEGORY_SELECTION_SERVICE_EXCEPTION, e);
		}

	}

	/**
	 * @param pendingCategoryList
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private Map<Short, String> buildPendingCategoryMap(List pendingCategoryList) {
		Map<Short, String> pendingCategoryMap = new LinkedHashMap<Short, String>();

		if (!pendingCategoryList.isEmpty()) {
			for (Object pendingCategoryListObj : pendingCategoryList) {
				Object[] pendingCategoryObj = (Object[]) pendingCategoryListObj;
				Short pendingCategory = (Short) pendingCategoryObj[0];
				Map<String, String> configMap = SaitenUtil.getConfigMap();
				/*
				 * boolean removeTagsFromDescription = Boolean
				 * .valueOf(configMap.get("removeTagsFromDescription"));
				 * if(removeTagsFromDescription){
				 */
				// commented this code. now this will be used for zenkoku also.
				/*
				 * pendingCategoryMap.put(pendingCategory,
				 * (String.valueOf(pendingCategoryObj
				 * [1])).replaceAll("\\<.*?>",""));
				 */
				/*
				 * pendingCategoryMap.put(pendingCategory,
				 * HtmlUtils.htmlEscape(String.valueOf(pendingCategoryObj[1]),
				 * "UTF-8"));
				 */
				pendingCategoryMap.put(pendingCategory, StringEscapeUtils
						.unescapeHtml4(String.valueOf(pendingCategoryObj[1])));
				/*
				 * pendingCategoryMap.put(pendingCategory,
				 * String.valueOf(pendingCategoryObj[1]));
				 */
				/*
				 * }else{ pendingCategoryMap.put(pendingCategory,
				 * (String.valueOf(pendingCategoryObj[1]))); }
				 */

			}
		}
		return pendingCategoryMap;
	}

	/**
	 * @param mstPendingCategoryDAO
	 */
	public void setMstPendingCategoryDAO(
			MstPendingCategoryDAO mstPendingCategoryDAO) {
		this.mstPendingCategoryDAO = mstPendingCategoryDAO;
	}

}
