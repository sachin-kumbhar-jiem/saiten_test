package com.saiten.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.saiten.dao.TranDescScoreDAO;
import com.saiten.dao.support.SaitenHibernateDAOSupport;
import com.saiten.info.DailyStatusSearchInfo;
import com.saiten.info.FindAnswerInfo;
import com.saiten.info.QuestionInfo;
import com.saiten.info.ScoreCurrentInfo;
import com.saiten.info.ScoreHistoryInfo;
import com.saiten.info.ScoreInputInfo;
import com.saiten.model.TranDescScore;
import com.saiten.util.SaitenUtil;
import com.saiten.util.WebAppConst;

/**
 * @author sachin
 * @version 1.0
 * @created 11-Dec-2012 12:56:32 PM
 */
public class TranDescScoreDAOImpl extends SaitenHibernateDAOSupport implements TranDescScoreDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saiten.dao.TranDescScoreDAO#findAnswer(int, java.lang.String,
	 * java.lang.String, java.util.LinkedHashMap, java.lang.String)
	 */
	private static Logger log = Logger.getLogger(TranDescScoreDAOImpl.class);

	@SuppressWarnings({ "rawtypes" })
	public List findAnswer(final List<Integer> quetionSeq, final String menuId, final String scorerId,
			final LinkedHashMap<String, Short> menuIdAndScoringStateMap, final String connectionString,
			final String answerFormNum, final Integer historyRecordCount, final int roleId) {

		// HibernateTemplate hibernateTemplate = null;

		final StringBuilder query = new StringBuilder();
		query.append("SELECT tranDescScore.answerSeq, tranDescScore.answerFormNum, ");
		query.append("tranDescScore.imageFileName, tranDescScore.gradeSeq, ");
		query.append("tranDescScore.bitValue, tranDescScore.questionSeq, ");
		query.append("tranDescScore.updateDate,  ");
		query.append("tranDescScore.markValue, ");
		query.append("tranDescScore.latestScreenScorerId,  ");
		query.append("tranDescScore.secondLatestScreenScorerId,  ");
		query.append("tranDescScore.punchText  ");
		query.append("FROM TranDescScore as tranDescScore WHERE ");
		if (!(roleId == WebAppConst.ADMIN_ROLE_ID)
				|| ((roleId == WebAppConst.ADMIN_ROLE_ID) && ((quetionSeq != null) && (!quetionSeq.isEmpty())))) {
			query.append("tranDescScore.questionSeq IN :QUESTION_SEQUENCE AND ");
		}
		query.append("tranDescScore.lockFlag = :UNLOCK ");
		query.append("AND tranDescScore.validFlag = :VALID_FLAG ");
		query.append("AND tranDescScore.latestScoringState = :LATEST_SCORING_STATE ");
		if (answerFormNum != null && (!answerFormNum.isEmpty())) {
			query.append("AND tranDescScore.answerFormNum = :ANSWER_FORM_NUM ");
		}

		/*
		 * List<String> paramNameList = new ArrayList<String>(); List valueList
		 * = new ArrayList();
		 * 
		 * paramNameList.add(0, "UNLOCK"); paramNameList.add(1, "VALID_FLAG");
		 * paramNameList.add(2, "LATEST_SCORING_STATE"); if (!(roleId ==
		 * WebAppConst.ADMIN_ROLE_ID) || ((roleId == WebAppConst.ADMIN_ROLE_ID)
		 * && ((quetionSeq != null) && (!quetionSeq .isEmpty())))) {
		 * paramNameList.add("QUESTION_SEQUENCE"); } if (answerFormNum != null
		 * && (!answerFormNum.isEmpty())) {
		 * paramNameList.add("ANSWER_FORM_NUM"); }
		 * 
		 * valueList.add(0, WebAppConst.UNLOCK); valueList.add(1,
		 * WebAppConst.VALID_FLAG); valueList.add(2,
		 * menuIdAndScoringStateMap.get(menuId)); if (!(roleId ==
		 * WebAppConst.ADMIN_ROLE_ID) || ((roleId == WebAppConst.ADMIN_ROLE_ID)
		 * && ((quetionSeq != null) && (!quetionSeq .isEmpty())))) {
		 * valueList.add(quetionSeq); } if (answerFormNum != null &&
		 * (!answerFormNum.isEmpty())) { valueList.add(answerFormNum); }
		 * 
		 * String[] paramNames = paramNameList.toArray(new String[paramNameList
		 * .size()]); Object[] values = valueList.toArray(new
		 * Object[valueList.size()]);
		 */

		try {
			/*
			 * hibernateTemplate = getHibernateTemplate(connectionString);
			 * 
			 * if (historyRecordCount == null) { hibernateTemplate
			 * .setMaxResults(WebAppConst.ANSWER_RECORD_FETCH_SIZE); }
			 * 
			 * List list = hibernateTemplate.findByNamedParam(query.toString(),
			 * paramNames, values);
			 * 
			 * return list;
			 */

			return getHibernateTemplate(connectionString).execute(new HibernateCallback<List>() {
				public List doInHibernate(Session session) throws HibernateException {
					Query queryObj = session.createQuery(query.toString());

					queryObj.setParameter("UNLOCK", WebAppConst.UNLOCK);
					queryObj.setParameter("VALID_FLAG", WebAppConst.VALID_FLAG);
					queryObj.setParameter("LATEST_SCORING_STATE", menuIdAndScoringStateMap.get(menuId));
					if (!(roleId == WebAppConst.ADMIN_ROLE_ID) || ((roleId == WebAppConst.ADMIN_ROLE_ID)
							&& ((quetionSeq != null) && (!quetionSeq.isEmpty())))) {
						queryObj.setParameterList("QUESTION_SEQUENCE", quetionSeq);
					}
					if (answerFormNum != null && (!answerFormNum.isEmpty())) {
						queryObj.setParameter("ANSWER_FORM_NUM", answerFormNum);
					}

					if (historyRecordCount == null) {
						queryObj.setMaxResults(WebAppConst.ANSWER_RECORD_FETCH_SIZE);
					}

					return queryObj.list();
				}
			});

		} catch (RuntimeException re) {
			throw re;
		} /*
			 * finally { if (hibernateTemplate != null && historyRecordCount ==
			 * null) { hibernateTemplate.setMaxResults(WebAppConst.ZERO); } }
			 */
	}

	// Above query implemented in Hibernate.

	/*
	 * @SuppressWarnings({ "rawtypes", "unchecked" })
	 * 
	 * @Override public List findAnswer(final List<Integer> quetionSeq, final
	 * String menuId, final String scorerId, LinkedHashMap<String, Short>
	 * menuIdAndScoringStateMap, String connectionString, final List<Integer>
	 * gradeSeqList, final Integer pendingCategorySeq, final String
	 * answerFormNum, Integer historyRecordCount) { HibernateTemplate
	 * hibernateTemplate = null; StringBuilder query = new StringBuilder();
	 * query.append("SELECT count(*) ");
	 * query.append("FROM TranDescScore as tranDescScore ");
	 * query.append("WHERE tranDescScore.questionSeq IN :QUESTION_SEQUENCE ");
	 * query.append("AND tranDescScore.lockFlag = :UNLOCK ");
	 * query.append("AND tranDescScore.validFlag = :VALID_FLAG ");
	 * query.append("AND tranDescScore.latestScorerId != :SCORER_ID ");
	 * 
	 * if (!(menuId.equals(WebAppConst.SPECIAL_SCORING_ENLARGE_TYPE_MENU_ID) ||
	 * menuId .equals(WebAppConst.SPECIAL_SCORING_OMR_READ_FAIL_MENU_ID) ||
	 * menuId .equals(WebAppConst.SPECIAL_SCORING_BLIND_TYPE_MENU_ID) || menuId
	 * .equals(WebAppConst.SPECIAL_SCORING_LANGUAGE_SUPPORT_MENU_ID))) {
	 * query.append(
	 * "AND (tranDescScore.latestScreenScorerId != :SCORER_ID OR tranDescScore.latestScreenScorerId IS null) "
	 * ); }
	 * 
	 * query.
	 * append("AND tranDescScore.latestScoringState = :LATEST_SCORING_STATE " );
	 * if ((menuId.equals(WebAppConst.CHECKING_MENU_ID) ||
	 * menuId.equals(WebAppConst.INSPECTION_MENU_ID) || menuId
	 * .equals(WebAppConst.DENY_MENU_ID)) && (!gradeSeqList.isEmpty())) {
	 * query.append("AND tranDescScore.gradeSeq IN :GRADE_SEQUENCE_LIST "); }
	 * else if (menuId.equals(WebAppConst.PENDING_MENU_ID)) { query.
	 * append("AND tranDescScore.pendingCategorySeq = :PENDING_CATEGORY_SEQ " );
	 * } if (answerFormNum != null && (!answerFormNum.isEmpty())) {
	 * query.append("AND tranDescScore.answerFormNum = :ANSWER_FORM_NUM "); }
	 * 
	 * query.append("ORDER BY RAND() ");
	 * query.append("AND tranDescScore.answerSeq like '%" + randomNumber +
	 * "' ");
	 * 
	 * 
	 * List<String> paramNameList = new ArrayList<String>(); List valueList =
	 * new ArrayList();
	 * 
	 * paramNameList.add(0, "QUESTION_SEQUENCE"); paramNameList.add(1,
	 * "UNLOCK"); paramNameList.add(2, "VALID_FLAG"); paramNameList.add(3,
	 * "SCORER_ID"); paramNameList.add(4, "LATEST_SCORING_STATE"); if
	 * ((menuId.equals(WebAppConst.CHECKING_MENU_ID) ||
	 * menuId.equals(WebAppConst.INSPECTION_MENU_ID) || menuId
	 * .equals(WebAppConst.DENY_MENU_ID)) && (!gradeSeqList.isEmpty())) {
	 * paramNameList.add("GRADE_SEQUENCE_LIST"); } else if
	 * (menuId.equals(WebAppConst.PENDING_MENU_ID)) {
	 * paramNameList.add("PENDING_CATEGORY_SEQ"); }
	 * 
	 * if (answerFormNum != null && (!answerFormNum.isEmpty())) {
	 * paramNameList.add("ANSWER_FORM_NUM"); }
	 * 
	 * valueList.add(0, quetionSeq); valueList.add(1, WebAppConst.UNLOCK);
	 * valueList.add(2, WebAppConst.VALID_FLAG); valueList.add(3, scorerId);
	 * valueList.add(4, menuIdAndScoringStateMap.get(menuId)); if
	 * ((menuId.equals(WebAppConst.CHECKING_MENU_ID) ||
	 * menuId.equals(WebAppConst.INSPECTION_MENU_ID) || menuId
	 * .equals(WebAppConst.DENY_MENU_ID)) && (!gradeSeqList.isEmpty())) {
	 * valueList.add(gradeSeqList); } else if
	 * (menuId.equals(WebAppConst.PENDING_MENU_ID)) {
	 * valueList.add(pendingCategorySeq); }
	 * 
	 * if (answerFormNum != null && (!answerFormNum.isEmpty())) {
	 * valueList.add(answerFormNum); }
	 * 
	 * String[] paramNames = paramNameList.toArray(new String[paramNameList
	 * .size()]); Object[] values = valueList.toArray(new
	 * Object[valueList.size()]);
	 * 
	 * hibernateTemplate = getHibernateTemplate(connectionString);
	 * 
	 * List<Long> list = hibernateTemplate.findByNamedParam(query.toString(),
	 * paramNames, values);
	 * 
	 * Random randomGenerator = new Random(); final int randomNumber =
	 * randomGenerator.nextInt(Integer.valueOf(list .get(0).toString()) - 1);
	 * 
	 * final Short latestScoringState = menuIdAndScoringStateMap.get(menuId);
	 * 
	 * try { List recordsList = getHibernateTemplate(connectionString).execute(
	 * new HibernateCallback<List>() {
	 * 
	 * public List doInHibernate(Session session) throws HibernateException {
	 * StringBuilder query = new StringBuilder(); query.
	 * append("SELECT tranDescScore.answerSeq, tranDescScore.answerFormNum, " );
	 * query.append("tranDescScore.imageFileName, tranDescScore.gradeSeq, ");
	 * query.append("tranDescScore.bitValue, tranDescScore.questionSeq, ");
	 * query.append("tranDescScore.updateDate ");
	 * query.append("FROM TranDescScore as tranDescScore ");
	 * query.append("WHERE tranDescScore.questionSeq IN :QUESTION_SEQUENCE ");
	 * query.append("AND tranDescScore.lockFlag = :UNLOCK ");
	 * query.append("AND tranDescScore.validFlag = :VALID_FLAG ");
	 * query.append("AND tranDescScore.latestScorerId != :SCORER_ID ");
	 * 
	 * if (!(menuId .equals(WebAppConst.SPECIAL_SCORING_ENLARGE_TYPE_MENU_ID) ||
	 * menuId .equals(WebAppConst.SPECIAL_SCORING_OMR_READ_FAIL_MENU_ID) ||
	 * menuId .equals(WebAppConst.SPECIAL_SCORING_BLIND_TYPE_MENU_ID) || menuId
	 * .equals(WebAppConst.SPECIAL_SCORING_LANGUAGE_SUPPORT_MENU_ID))) {
	 * query.append(
	 * "AND (tranDescScore.latestScreenScorerId != :SCORER_ID OR tranDescScore.latestScreenScorerId IS null) "
	 * ); }
	 * 
	 * query.
	 * append("AND tranDescScore.latestScoringState = :LATEST_SCORING_STATE " );
	 * if ((menuId.equals(WebAppConst.CHECKING_MENU_ID) || menuId
	 * .equals(WebAppConst.INSPECTION_MENU_ID) || menuId
	 * .equals(WebAppConst.DENY_MENU_ID)) && (!gradeSeqList.isEmpty())) {
	 * query.append("AND tranDescScore.gradeSeq IN :GRADE_SEQUENCE_LIST "); }
	 * else if (menuId .equals(WebAppConst.PENDING_MENU_ID)) { query.
	 * append("AND tranDescScore.pendingCategorySeq = :PENDING_CATEGORY_SEQ " );
	 * } if (answerFormNum != null && (!answerFormNum.isEmpty())) {
	 * query.append("AND tranDescScore.answerFormNum = :ANSWER_FORM_NUM "); }
	 * Query queryObj = session.createQuery(query .toString());
	 * queryObj.setParameterList("QUESTION_SEQUENCE", quetionSeq);
	 * queryObj.setParameter("UNLOCK", WebAppConst.UNLOCK);
	 * queryObj.setParameter("VALID_FLAG", WebAppConst.VALID_FLAG);
	 * queryObj.setParameter("SCORER_ID", scorerId);
	 * queryObj.setParameter("LATEST_SCORING_STATE", latestScoringState); if
	 * ((menuId.equals(WebAppConst.CHECKING_MENU_ID) || menuId
	 * .equals(WebAppConst.INSPECTION_MENU_ID) || menuId
	 * .equals(WebAppConst.DENY_MENU_ID)) && (!gradeSeqList.isEmpty())) {
	 * queryObj.setParameterList( "GRADE_SEQUENCE_LIST", gradeSeqList); } else
	 * if (menuId .equals(WebAppConst.PENDING_MENU_ID)) {
	 * queryObj.setParameter("PENDING_CATEGORY_SEQ", pendingCategorySeq); }
	 * 
	 * if (answerFormNum != null && (!answerFormNum.isEmpty())) {
	 * queryObj.setParameter("ANSWER_FORM_NUM", answerFormNum); }
	 * queryObj.setFirstResult(randomNumber); queryObj.setMaxResults(1); return
	 * queryObj.list(); } }); return recordsList; } catch (RuntimeException re)
	 * { throw re; } finally {
	 * 
	 * if (hibernateTemplate != null && historyRecordCount == null) {
	 * hibernateTemplate.setMaxResults(WebAppConst.ZERO); }
	 * 
	 * } }
	 */

	@SuppressWarnings({ "rawtypes", "unused" })
	public List findQcAnsSeqList(final List<Integer> quetionSeq, final String scorerId, String connectionString,
			final Short selectedMarkValue) {
		HibernateTemplate hibernateTemplate = null;

		/*
		 * StringBuilder query = new StringBuilder(); query.append(
		 * "SELECT tqc1.tranDescScore.answerSeq FROM TranQualitycheckScore As tqc1 "
		 * ); query.append("WHERE tqc1.refFlag= :VALID_FLAG ");
		 * query.append("AND tqc1.questionSeq= :QUESTION_SEQUENCE ");
		 * query.append(
		 * "AND tqc1.validFlag= :VALID_FLAG AND tqc1.tranDescScore.answerSeq NOT IN  "
		 * ); query.append(
		 * "(SELECT tqc2.tranDescScore.answerSeq FROM TranQualitycheckScore As tqc2 "
		 * ); query.append("WHERE tqc2.questionSeq= :QUESTION_SEQUENCE ");
		 * query.append("AND tqc2.scorerId= :SCORER_ID ");
		 * query.append("AND tqc2.validFlag= :VALID_FLAG) ");
		 * 
		 * List<String> paramNameList = new ArrayList<String>(); List valueList
		 * = new ArrayList();
		 * 
		 * paramNameList.add(0, "VALID_FLAG"); paramNameList.add(1,
		 * "QUESTION_SEQUENCE"); paramNameList.add(2, "SCORER_ID");
		 * 
		 * valueList.add(0, WebAppConst.VALID_FLAG); valueList.add(1,
		 * quetionSeq); valueList.add(2, scorerId);
		 * 
		 * String[] paramNames = paramNameList.toArray(new String[paramNameList
		 * .size()]); Object[] values = valueList.toArray(new
		 * Object[valueList.size()]);
		 */

		final StringBuilder query = new StringBuilder();
		if (selectedMarkValue != null) {
			query.append("SELECT D.answer_seq FROM (");
		}

		query.append(" SELECT R.answer_seq ");
		query.append("FROM (SELECT answer_seq ");
		query.append("from tran_qualitycheck_score ");
		query.append("WHERE question_seq IN :QUESTION_SEQUENCE ");
		query.append("and ref_flag = :VALID_FLAG ");
		query.append("and valid_flag = :VALID_FLAG) as R ");
		query.append("LEFT JOIN ");
		query.append("(SELECT answer_seq from tran_qualitycheck_score ");
		query.append("WHERE question_seq IN :QUESTION_SEQUENCE ");
		query.append("and scorer_id = :SCORER_ID ");
		query.append("and valid_flag= :VALID_FLAG) as S ");
		query.append("on R.answer_seq =  S.answer_seq ");
		query.append("WHERE S.answer_seq is null  ");

		if (selectedMarkValue != null) {
			query.append(" ) as D ");
			query.append(" INNER JOIN ");
			query.append("tran_desc_score as T ");
			query.append("on T.answer_seq =  D.answer_seq ");
			query.append("where T.mark_value =:MARK_VALUE");
		}

		try {
			/*
			 * hibernateTemplate = getHibernateTemplate(connectionString);
			 * 
			 * List list = hibernateTemplate.findByNamedParam(query.toString(),
			 * paramNames, values);
			 */

			return getHibernateTemplate(connectionString).execute(new HibernateCallback<List>() {
				public List doInHibernate(Session session) throws HibernateException {
					SQLQuery queryObj = session.createSQLQuery(query.toString());

					queryObj.setParameter("SCORER_ID", scorerId);
					queryObj.setParameterList("QUESTION_SEQUENCE", quetionSeq);
					queryObj.setParameter("VALID_FLAG", WebAppConst.VALID_FLAG);
					if (selectedMarkValue != null) {
						queryObj.setParameter("MARK_VALUE", selectedMarkValue);
					}
					return queryObj.list();
				}

			});

		} catch (RuntimeException re) {
			throw re;
		} /*
			 * finally { if (hibernateTemplate != null) {
			 * hibernateTemplate.setMaxResults(WebAppConst.ZERO); } }
			 */
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List findQualityCheckAnswers(Integer qcAnswerSeq, String connectionString) {
		HibernateTemplate hibernateTemplate = null;

		StringBuilder query = new StringBuilder();
		query.append("SELECT tranDescScore.answerSeq, tranDescScore.answerFormNum, ");
		query.append("tranDescScore.imageFileName, tranDescScore.gradeSeq, ");
		query.append("tranDescScore.bitValue, tranDescScore.questionSeq, ");
		query.append("tranDescScore.updateDate,  ");
		query.append("tranDescScore.markValue, ");
		query.append("tranDescScore.latestScreenScorerId,  ");
		query.append("tranDescScore.secondLatestScreenScorerId, ");
		query.append("tranDescScore.punchText  ");
		query.append("FROM TranDescScore as tranDescScore  ");
		query.append("WHERE tranDescScore.answerSeq = :ANSWER_SEQUENCE ");
		List<String> paramNameList = new ArrayList<String>();
		List valueList = new ArrayList();

		paramNameList.add(0, "ANSWER_SEQUENCE");

		valueList.add(0, qcAnswerSeq);

		String[] paramNames = paramNameList.toArray(new String[paramNameList.size()]);
		Object[] values = valueList.toArray(new Object[valueList.size()]);

		try {
			hibernateTemplate = getHibernateTemplate(connectionString);

			List list = hibernateTemplate.findByNamedParam(query.toString(), paramNames, values);

			return list;
		} catch (RuntimeException re) {
			throw re;
		} /*
			 * finally { if (hibernateTemplate != null) {
			 * hibernateTemplate.setMaxResults(WebAppConst.ZERO); } }
			 */

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List findAnswer(List<Integer> quetionSeq, String menuId, String scorerId,
			LinkedHashMap<String, Short> menuIdAndScoringStateMap, String connectionString, Integer gradeNum,
			Short pendingCategory, Short denyCategory, String answerFormNum, Integer historyRecordCount,
			Integer randomNumberRange, boolean passByRandomFlag, String selectedMarkValue, int roleId,
			boolean qualityFromPendingMenu, Integer inspectGroupSeq, Double bitValue) {
		List answerRecords;
		String questionSeq = null;
		/* String gradeSeq = null; */
		HibernateTemplate hibernateTemplate = null;
		int randomNumber = 10;
		Date date = new Date();
		try {
			hibernateTemplate = getHibernateTemplate(connectionString);
			hibernateTemplate.setMaxResults(WebAppConst.ZERO);
			if (!(quetionSeq == null) && !(quetionSeq.isEmpty())) {
				questionSeq = StringUtils.join(quetionSeq, ",");
			}

			/*
			 * if (!(gradeSeqList == null) && !(gradeSeqList.isEmpty())) {
			 * 
			 * gradeSeq = StringUtils.join(gradeSeqList, ","); }
			 */

			/*
			 * System.out.println(">>>>>>>>>>> findAnswer Query start: " + new
			 * Date().getTime());
			 */// int i = 1;
			Map<String, String> configMap = SaitenUtil.getConfigMap();
			boolean secondAndThirdLatestScorerIdFlag = Boolean
					.valueOf(configMap.get("secondAndThirdLatestScorerIdFlag"));
			boolean qualityFromDenyMenu = false;
			// BigInteger bitValueObj = new BigDecimal(bitValue).toBigInteger();
			if (passByRandomFlag == true) {
				Random randomGenerator = new Random();
				randomNumber = randomGenerator.nextInt(randomNumberRange);
				log.info(scorerId + "-" + menuId + "-" + "fetchAnswerPassByRandomNumber." + "-{ Question Sequence: "
						+ questionSeq + ", randomNumber: " + randomNumber + ", gradeNum: " + gradeNum
						+ ", inspectGroupSeq: " + inspectGroupSeq + ", pendingCategory: " + pendingCategory
						+ ", denyCategory: " + denyCategory + ", answerFormNum: " + answerFormNum
						+ ", selectedMarkValue: " + selectedMarkValue + ", date: " + date + ", roleId: " + roleId
						+ ", qualityFromPendingMenu: " + qualityFromPendingMenu + ", qualityFromDenyMenu: "
						+ qualityFromDenyMenu + ", secondAndThirdLatestScorerIdFlag: "
						+ secondAndThirdLatestScorerIdFlag + "}");

				answerRecords = hibernateTemplate.findByNamedQuery("fetchAnswerPassByRandomNumber", questionSeq,
						randomNumber, scorerId, menuIdAndScoringStateMap.get(menuId), gradeNum, inspectGroupSeq,
						pendingCategory, denyCategory, answerFormNum, selectedMarkValue, date, roleId,
						qualityFromPendingMenu, qualityFromDenyMenu, secondAndThirdLatestScorerIdFlag);
			} else {
				log.info(scorerId + "-" + menuId + "-" + "fetchAnswerOrderByRandom." + "-{ Question Sequence: "
						+ questionSeq + ", gradeNum: " + gradeNum + ", inspectGroupSeq: " + inspectGroupSeq
						+ ", pendingCategory: " + pendingCategory + ", denyCategory: " + denyCategory
						+ ", answerFormNum: " + answerFormNum + ", selectedMarkValue: " + selectedMarkValue + ", date: "
						+ date + ", roleId: " + roleId + ", qualityFromPendingMenu: " + qualityFromPendingMenu
						+ ", qualityFromDenyMenu: " + qualityFromDenyMenu + ", secondAndThirdLatestScorerIdFlag: "
						+ secondAndThirdLatestScorerIdFlag + "}");

				answerRecords = hibernateTemplate.findByNamedQuery("fetchAnswerOrderByRandom", questionSeq, scorerId,
						menuIdAndScoringStateMap.get(menuId), gradeNum, inspectGroupSeq, pendingCategory, denyCategory,
						answerFormNum, selectedMarkValue, date, roleId, qualityFromPendingMenu, qualityFromDenyMenu,
						secondAndThirdLatestScorerIdFlag);
			}
			if (!answerRecords.isEmpty()) {
				FindAnswerInfo findAnswerInfo = (FindAnswerInfo) answerRecords.get(0);
				Object[] objArray = { findAnswerInfo.getAnswerSeq(), findAnswerInfo.getAnswerFormNum(),
						findAnswerInfo.getImageFileName(), findAnswerInfo.getGradeSeq(), findAnswerInfo.getBitValue(),
						findAnswerInfo.getQuestionSeq(), findAnswerInfo.getUpdateDate(), findAnswerInfo.getMarkValue(),
						findAnswerInfo.getLatestScreenScorerId(), findAnswerInfo.getSecondLatestScreenScorerId(), findAnswerInfo.getPunchText() };
				answerRecords = new ArrayList();
				answerRecords.add(objArray);
			}
			return answerRecords;

		} catch (RuntimeException re) {
			/*
			 * String exception = re.toString(); if(exception != null &&
			 * exception.contains("fetch_answer_pass_by_random_number")){
			 * StringBuilder str = new StringBuilder();
			 * str.append("questionSeq: "+questionSeq);
			 * str.append("\n randomNumber: "+randomNumber);
			 * str.append("\n scorerId: "+scorerId);
			 * str.append("\n menuIdAndScoringStateMap.get(menuId): "
			 * +menuIdAndScoringStateMap.get(menuId));
			 * str.append("\n gradeNum: "+gradeNum);
			 * str.append("\n pendingCategory: "+pendingCategory);
			 * str.append("\n answerFormNum: "+answerFormNum);
			 * str.append("\n selectedMarkValue: "+selectedMarkValue);
			 * str.append("\n date: "+date); str.append("\n roleId: "+roleId);
			 * log.error(str.toString()); }
			 */

			throw re;
		} /*
			 * finally { if (hibernateTemplate != null && historyRecordCount ==
			 * null) { hibernateTemplate.setMaxResults(WebAppConst.ZERO); }
			 * 
			 * }
			 */
	}

	/*
	 * @SuppressWarnings({ "rawtypes", "unchecked" })
	 * 
	 * @Override
	 */
	/*
	 * public List findAnswer(final List<Integer> quetionSeq, final String
	 * menuId, final String scorerId, final LinkedHashMap<String, Short>
	 * menuIdAndScoringStateMap, final String connectionString, final
	 * List<Integer> gradeSeqList, final Integer pendingCategorySeq, final
	 * String answerFormNum, final Integer historyRecordCount) {
	 * 
	 * //HibernateTemplate hibernateTemplate = null;
	 * 
	 * final StringBuilder query = new StringBuilder(); query.append(
	 * "SELECT tranDescScore.answer_seq, tranDescScore.answer_form_num, ");
	 * query.append("tranDescScore.image_file_name, tranDescScore.grade_seq, ");
	 * query.append("tranDescScore.bit_value, tranDescScore.question_seq, ");
	 * query.append("tranDescScore.update_date ");
	 * query.append("FROM tran_desc_score as tranDescScore, ");
	 * query.append("(SELECT t.answer_seq as randAnswerSeq ");
	 * query.append("FROM tran_desc_score as t ");
	 * query.append("WHERE t.question_seq IN :QUESTION_SEQUENCE ");
	 * query.append("AND t.lock_flag = :UNLOCK ");
	 * query.append("AND t.valid_flag = :VALID_FLAG "); if
	 * (!(menuId.equals(WebAppConst.SPECIAL_SCORING_ENLARGE_TYPE_MENU_ID) ||
	 * menuId .equals(WebAppConst.SPECIAL_SCORING_OMR_READ_FAIL_MENU_ID) ||
	 * menuId .equals(WebAppConst.SPECIAL_SCORING_BLIND_TYPE_MENU_ID) || menuId
	 * .equals(WebAppConst.SPECIAL_SCORING_LANGUAGE_SUPPORT_MENU_ID))) {
	 * query.append(
	 * "AND (t.latest_screen_scorer_id != :SCORER_ID OR t.latest_screen_scorer_id IS null) "
	 * ); }
	 * 
	 * query.append("AND t.latest_scoring_state = :LATEST_SCORING_STATE "); if
	 * ((menuId.equals(WebAppConst.CHECKING_MENU_ID) ||
	 * menuId.equals(WebAppConst.INSPECTION_MENU_ID) || menuId
	 * .equals(WebAppConst.DENY_MENU_ID)) && (!gradeSeqList.isEmpty())) {
	 * query.append("AND t.grade_seq IN :GRADE_SEQUENCE_LIST "); } else if
	 * (menuId.equals(WebAppConst.PENDING_MENU_ID)) {
	 * query.append("AND t.pending_category_seq = :PENDING_CATEGORY_SEQ "); } if
	 * (answerFormNum != null && (!answerFormNum.isEmpty())) {
	 * query.append("AND t.answer_form_num = :ANSWER_FORM_NUM "); }
	 * query.append("ORDER BY RAND() LIMIT 1 ) as r ");
	 * query.append("WHERE tranDescScore.answer_seq = r.randAnswerSeq ");
	 * 
	 * 
	 * try {
	 * 
	 * return getHibernateTemplate(connectionString).execute( new
	 * HibernateCallback<List>() { public List doInHibernate(Session session)
	 * throws HibernateException { SQLQuery queryObj =
	 * session.createSQLQuery(query .toString());
	 * queryObj.setParameterList("QUESTION_SEQUENCE", quetionSeq);
	 * queryObj.setParameter("UNLOCK", WebAppConst.UNLOCK);
	 * queryObj.setParameter("VALID_FLAG", WebAppConst.VALID_FLAG);
	 * queryObj.setParameter("SCORER_ID", scorerId);
	 * queryObj.setParameter("LATEST_SCORING_STATE",
	 * menuIdAndScoringStateMap.get(menuId)); if
	 * ((menuId.equals(WebAppConst.CHECKING_MENU_ID) ||
	 * menuId.equals(WebAppConst.INSPECTION_MENU_ID) || menuId
	 * .equals(WebAppConst.DENY_MENU_ID)) && (!gradeSeqList.isEmpty())) {
	 * queryObj.setParameterList("GRADE_SEQUENCE_LIST", gradeSeqList); } else if
	 * (menuId.equals(WebAppConst.PENDING_MENU_ID)) {
	 * queryObj.setParameter("PENDING_CATEGORY_SEQ", pendingCategorySeq); }
	 * 
	 * if (answerFormNum != null && (!answerFormNum.isEmpty())) {
	 * queryObj.setParameter("ANSWER_FORM_NUM", answerFormNum); }
	 * 
	 * return queryObj.list(); } });
	 * 
	 * } catch (RuntimeException re) { re.printStackTrace(); throw re; } }
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saiten.dao.TranDescScoreDAO#findById(int, java.lang.String)
	 */
	@Override
	public TranDescScore findById(int answerSeq, String connectionString) {
		try {
			return getHibernateTemplate(connectionString).get(TranDescScore.class, answerSeq);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saiten.dao.TranDescScoreDAO#lockAnswer(int, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void lockAnswer(final int answerSeq, final String scorerId, String connectionString, final Date updateDate) {
		final StringBuilder query = new StringBuilder();
		query.append("UPDATE TranDescScore ");
		query.append("SET lockFlag = :LOCK, lockBy = :LOCK_BY, updateDate = :UPDATE_DATE ");
		query.append("WHERE answerSeq = :ANSWER_SEQUENCE ");

		try {
			getHibernateTemplate(connectionString).execute(new HibernateCallback<Integer>() {
				public Integer doInHibernate(Session session) throws HibernateException {

					Query queryObj = session.createQuery(query.toString());

					queryObj.setParameter("LOCK", WebAppConst.LOCK);
					queryObj.setParameter("LOCK_BY", scorerId);
					queryObj.setParameter("UPDATE_DATE", updateDate);
					queryObj.setParameter("ANSWER_SEQUENCE", answerSeq);
					/*
					 * System.out .println(">>>>>>>>> lockAnswer Query start: "
					 * + new Date().getTime());
					 */
					int updateCount = queryObj.executeUpdate();
					/*
					 * System.out .println(">>>>>>>>> lockAnswer Query end: " +
					 * new Date().getTime());
					 */
					return updateCount;
				}
			});
		} catch (RuntimeException re) {
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saiten.dao.TranDescScoreDAO#unlockAnswer(int, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void unlockAnswer(final int questionSeq, final String lockBy, String connectionString,
			final Integer answerSeq) {
		final StringBuilder query = new StringBuilder();
		query.append("UPDATE TranDescScore ");
		query.append("SET lockFlag = :LOCK, lockBy = :RESET_LOCK_BY, updateDate = :UPDATE_DATE ");
		query.append("WHERE questionSeq = :QUESTION_SEQ ");
		query.append("AND lockBy = :LOCK_BY ");
		if (answerSeq != null) {
			query.append("AND answerSeq = :ANSWER_SEQUENCE ");
		}
		try {
			getHibernateTemplate(connectionString).execute(new HibernateCallback<Integer>() {
				public Integer doInHibernate(Session session) throws HibernateException {

					Query queryObj = session.createQuery(query.toString());

					queryObj.setParameter("LOCK", WebAppConst.UNLOCK);
					queryObj.setParameter("RESET_LOCK_BY", null);
					queryObj.setParameter("UPDATE_DATE", new Date());
					queryObj.setParameter("QUESTION_SEQ", questionSeq);
					queryObj.setParameter("LOCK_BY", lockBy);
					if (answerSeq != null) {
						queryObj.setParameter("ANSWER_SEQUENCE", answerSeq);
					}

					return queryObj.executeUpdate();
				}
			});
		} catch (RuntimeException re) {
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saiten.dao.TranDescScoreDAO#updateInspectFlag(java.util.List,
	 * com.saiten.info.QuestionInfo, java.lang.Short[], boolean)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public int updateInspectFlag(final List<Integer> answerSeq, final QuestionInfo questionInfo,
			final boolean selectAllFlag, final ScoreInputInfo scoreInputInfo, final Integer maxInspectGroupSeq) {
		/*
		 * final StringBuilder query = new StringBuilder();
		 * query.append("UPDATE TranDescScore ");
		 * query.append("SET inspectFlag = :LOCK, updateDate = :UPDATE_DATE ");
		 * query.append("WHERE lockFlag = :UNLOCK AND validFlag = :LOCK ");
		 * query.append("AND latestScoringState IN :CURRENT_STATE_LIST ");
		 * query.append("AND questionSeq = :QUESTION_SEQ  "); if
		 * (!selectAllFlag) {
		 * query.append("AND answerSeq IN :ANSWER_SEQUENCE "); } else {
		 * query.append("AND inspect_flag <> :LOCK  "); }
		 * 
		 * try { return getHibernateTemplate(questionInfo.getConnectionString())
		 * .execute(new HibernateCallback<Integer>() { public Integer
		 * doInHibernate(Session session) throws HibernateException {
		 * 
		 * Query queryObj = session.createQuery(query .toString());
		 * 
		 * queryObj.setParameter("LOCK", WebAppConst.LOCK);
		 * queryObj.setParameter("UNLOCK", WebAppConst.UNLOCK);
		 * queryObj.setParameter("UPDATE_DATE", new Date()); if
		 * (currentStateList != null && currentStateList.length >
		 * WebAppConst.ZERO) { queryObj.setParameterList("CURRENT_STATE_LIST",
		 * currentStateList);
		 * 
		 * } else { queryObj.setParameterList( "CURRENT_STATE_LIST",
		 * WebAppConst.STATE_TRANSITION_CURRENT_STATES); }
		 * queryObj.setParameter("QUESTION_SEQ", questionInfo.getQuestionSeq());
		 * if (!selectAllFlag) { queryObj.setParameterList("ANSWER_SEQUENCE",
		 * answerSeq); } return queryObj.executeUpdate(); } }); } catch
		 * (RuntimeException re) { throw re; }
		 */

		Map<String, String> configMap = SaitenUtil.getConfigMap();
		final boolean searchByScorerRoleId = Boolean.valueOf(configMap.get("search.by.scorer_role_id"));

		final ScoreHistoryInfo scoreHistoryInfo = scoreInputInfo.getScoreHistoryInfo();

		final String menuId = questionInfo.getMenuId();
		final String answerFormNum = scoreInputInfo.getAnswerFormNum();
		final String subjectCode = scoreInputInfo.getSubjectCode();
		final Integer scoreStartRange = scoreInputInfo.getScoreStartRange();
		final Integer scoreEndRange = scoreInputInfo.getScoreEndRange();
		final Integer questionTypekey = scoreInputInfo.getQuestionType();
		final List<String> historyScorerIdList = scoreHistoryInfo != null ? scoreHistoryInfo.getHistoryScorerIdList()
				: null;
		final Integer historyCategoryType = scoreHistoryInfo != null ? scoreHistoryInfo.getHistoryCategoryType() : null;
		final List historyGradeSeqList = scoreHistoryInfo != null ? scoreHistoryInfo.getHistoryGradeSeqList() : null;
		final List historyPendingCategorySeqList = scoreHistoryInfo != null
				? scoreHistoryInfo.getHistoryPendingCategorySeqList() : null;
		final List historyDenyCategorySeqList = scoreHistoryInfo != null
				? scoreHistoryInfo.getHistoryDenyCategorySeqList() : null;
		final String historyIncludeCheckPoints = scoreHistoryInfo != null
				? scoreHistoryInfo.getHistoryIncludeCheckPoints() : null;
		final String historyExcludeCheckPoints = scoreHistoryInfo != null
				? scoreHistoryInfo.getHistoryExcludeCheckPoints() : null;
		final Short[] historyEventList = scoreHistoryInfo != null ? scoreHistoryInfo.getHistoryEventList() : null;
		final Date historyUpdateStartDate = scoreHistoryInfo != null ? scoreHistoryInfo.getHistoryUpdateStartDate()
				: null;
		final Date historyUpdateEndDate = scoreHistoryInfo != null ? scoreHistoryInfo.getHistoryUpdateEndDate() : null;
		final Integer[] historyScorerRoles = scoreHistoryInfo != null ? scoreHistoryInfo.getHistoryScorerRole() : null;

		final ScoreCurrentInfo scoreCurrentInfo = scoreInputInfo.getScoreCurrentInfo();

		final String punchText = scoreCurrentInfo != null ? scoreCurrentInfo.getPunchText() : null;
		final List<String> currentScorerIdList = scoreCurrentInfo != null ? scoreCurrentInfo.getCurrentScorerIdList()
				: null;
		final Integer currentCategoryType = scoreCurrentInfo != null ? scoreCurrentInfo.getCurrentCategoryType() : null;
		final List currentGradeSeqList = scoreCurrentInfo != null ? scoreCurrentInfo.getCurrentGradeSeqList() : null;
		final List currentPendingCategorySeqList = scoreCurrentInfo != null
				? scoreCurrentInfo.getCurrentPendingCategorySeqList() : null;
		final List currentDenyCategorySeqList = scoreCurrentInfo != null
				? scoreCurrentInfo.getCurrentDenyCategorySeqList() : null;
		final String currentIncludeCheckPoints = scoreCurrentInfo != null
				? scoreCurrentInfo.getCurrentIncludeCheckPoints() : null;
		final String currentExcludeCheckPoints = scoreCurrentInfo != null
				? scoreCurrentInfo.getCurrentExcludeCheckPoints() : null;
		final Short[] currentStateList = scoreCurrentInfo != null ? scoreCurrentInfo.getCurrentStateList() : null;
		final Date currentUpdateStartDate = scoreCurrentInfo != null ? scoreCurrentInfo.getCurrentUpdateStartDate()
				: null;
		final Date currentUpdateEndDate = scoreCurrentInfo != null ? scoreCurrentInfo.getCurrentUpdateEndDate() : null;
		final Integer[] currentScorerRoles = scoreCurrentInfo != null ? scoreCurrentInfo.getCurrentScorerRole() : null;

		/*
		 * System.out.println(">>>>>>>>>>>>>> searchAnswerRecords start: " + new
		 * Date().getTime());
		 */
		try {
			return getHibernateTemplate(questionInfo.getConnectionString()).execute(new HibernateCallback<Integer>() {

				public Integer doInHibernate(Session session) throws HibernateException {
					StringBuilder query = new StringBuilder();
					query.append(" UPDATE tran_desc_score target, ");
					query.append(" (SELECT distinct tranDescScore.answer_seq as answerSeq ");
					query.append("FROM tran_desc_score AS tranDescScore ");
					if (menuId.equals(WebAppConst.STATE_TRAN_MENU_ID) && (scoreStartRange != null)
							&& (scoreEndRange != null)) {
						query.append("INNER JOIN ");
						query.append("tran_score_percentage AS tranScorePercentage  ");
						query.append("ON tranDescScore.answer_form_num = tranScorePercentage.answer_form_num ");
					}
					query.append("INNER JOIN ");
					query.append("tran_desc_score_history AS tranDescScoreHistory ");
					query.append("ON tranDescScore.answer_seq = tranDescScoreHistory.answer_seq ");
					query.append("WHERE tranDescScore.question_seq  = :QUESTION_SEQ ");

					if (searchByScorerRoleId == true) {
						if (currentScorerRoles != null) {
							query.append("AND tranDescScore.answer_seq = ");
							query.append("( SELECT answer_seq FROM tran_desc_score_history his1 ");
							query.append("WHERE his1.history_seq = ");
							query.append("( SELECT max(history_seq) FROM tran_desc_score_history his2 ");
							query.append("WHERE his2.answer_seq = tranDescScore.answer_seq ");
							query.append(") ");
							query.append("AND his1.scorer_role_id IN ( :CURRENT_SCORER_ROLES ) ");

							query.append(") ");
						}
					}

					query.append("AND tranDescScore.lock_flag = :UNLOCK ");
					if (!selectAllFlag) {
						query.append("AND tranDescScore.answer_seq IN :ANSWER_SEQUENCE ");
					}

					if (menuId.equals(WebAppConst.STATE_TRAN_MENU_ID)) {
						if ((scoreStartRange != null) && (scoreEndRange != null)) {
							query.append("AND tranScorePercentage.subject_code = :SUBJECT_CODE ");
							String columnName = SaitenUtil.getColumnNameByQuestionType(questionTypekey);
							query.append("AND tranScorePercentage." + columnName + " ");
							query.append("between :SCORE_START_RANGE and :SCORE_END_RANGE ");
						}
						query.append("AND tranDescScore.inspect_flag = :INSPECT_FLAG ");
						/*
						 * if (WebAppConst.SPEAKING_TYPE
						 * .equals(questionInfo.getQuestionType()) ||
						 * WebAppConst.WRITING_TYPE .equals(questionInfo
						 * .getQuestionType())) {
						 */
						query.append("AND tranDescScore.quality_check_flag = :QUALITY_FLAG ");
						/* } */

					}

					if (!StringUtils.isBlank(answerFormNum)) {
						query.append("AND tranDescScore.answer_form_num = :ANSWER_FORM_NUM ");
					}

					if (scoreCurrentInfo != null && scoreCurrentInfo.isCurrentBlock()) {

						if (!StringUtils.isBlank(punchText)) {
							query.append("AND tranDescScore.punch_text = :PUNCH_TEXT ");
						}

						if (!currentScorerIdList.isEmpty()) {
							query.append("AND tranDescScore.latest_screen_scorer_id IN :CURRENT_SCORER_ID_LIST ");
						}

						// This is for Quality Check Flag
						/*
						 * if (WebAppConst.SPEAKING_TYPE
						 * .equals(questionInfo.getQuestionType()) ||
						 * WebAppConst.WRITING_TYPE .equals(questionInfo
						 * .getQuestionType())) {
						 */
						if (scoreCurrentInfo.isCurrentQualityCheckFlag()) {
							query.append("AND tranDescScore.quality_check_flag IN :QUALITY_CHECK_FLAG ");
						}
						/* } */

						if (currentCategoryType != null && currentCategoryType == 2) {
							query.append(
									"AND tranDescScore.grade_seq IS null AND tranDescScore.pending_category_seq IS null ");
						} else if (currentCategoryType != null && currentCategoryType == 3) {

							if (!currentGradeSeqList.isEmpty()
									&& ArrayUtils.contains(scoreCurrentInfo.getCurrentGradeNum(), -1)) {
								query.append("AND (tranDescScore.grade_seq IN :CURRENT_GRADE_SEQ_LIST ");
								query.append("OR tranDescScore.latest_scoring_state = :NO_GRADE_SCORING_STATE) ");
							} else {
								if (!currentGradeSeqList.isEmpty()) {
									query.append("AND tranDescScore.grade_seq IN :CURRENT_GRADE_SEQ_LIST ");
								}

								if (ArrayUtils.contains(scoreCurrentInfo.getCurrentGradeNum(), -1)) {
									query.append("AND tranDescScore.latest_scoring_state = :NO_GRADE_SCORING_STATE ");
								}
							}
						} else if (currentCategoryType != null && currentCategoryType == 4
								&& !currentPendingCategorySeqList.isEmpty()) {
							query.append(
									"AND tranDescScore.pending_category_seq IN :CURRENT_PENDING_CATEGORY_SEQ_LIST ");
						} else if (currentCategoryType != null && currentCategoryType == 5
								&& !currentDenyCategorySeqList.isEmpty()) {
							query.append("AND tranDescScore.deny_category_seq IN :CURRENT_DENY_CATEGORY_SEQ_LIST ");
						}

						if (!StringUtils.isBlank(currentIncludeCheckPoints)
								&& !StringUtils.isBlank(currentExcludeCheckPoints)) {
							if (scoreInputInfo.getScoreCurrentInfo().getCurrentSkpConditions()
									.equals(WebAppConst.SKP_AND_CONDITION)) {
								if (scoreCurrentInfo.getCurrentSkpIncludeConditions()
										.equals(WebAppConst.SKP_AND_CONDITION)) {
									query.append(
											"AND (tranDescScore.bit_value & :CURRENT_INCLUDE_BIT_VALUE) = :CURRENT_INCLUDE_BIT_VALUE ");
								} else if (scoreCurrentInfo.getCurrentSkpIncludeConditions()
										.equals(WebAppConst.SKP_OR_CONDITION)) {
									query.append("AND (tranDescScore.bit_value & :CURRENT_INCLUDE_BIT_VALUE) > 0 ");
								}

								if (scoreCurrentInfo.getCurrentSkpExcludeConditions()
										.equals(WebAppConst.SKP_AND_CONDITION)) {
									query.append(
											"AND (tranDescScore.bit_value & :CURRENT_EXCLUDE_BIT_VALUE) != :CURRENT_EXCLUDE_BIT_VALUE ");
								} else if (scoreCurrentInfo.getCurrentSkpExcludeConditions()
										.equals(WebAppConst.SKP_OR_CONDITION)) {
									query.append("AND (tranDescScore.bit_value & :CURRENT_EXCLUDE_BIT_VALUE) = 0 ");
								}

							} else if (scoreInputInfo.getScoreCurrentInfo().getCurrentSkpConditions()
									.equals(WebAppConst.SKP_OR_CONDITION)) {
								// query.append("AND ((tranDescScore.bit_value &
								// :CURRENT_INCLUDE_BIT_VALUE) > 0 OR
								// (tranDescScore.bit_value &
								// :CURRENT_EXCLUDE_BIT_VALUE) = 0)");
								if (scoreCurrentInfo.getCurrentSkpIncludeConditions()
										.equals(WebAppConst.SKP_AND_CONDITION)) {
									query.append(
											"(tranDescScore.bit_value & :CURRENT_INCLUDE_BIT_VALUE) = :CURRENT_INCLUDE_BIT_VALUE ");
								} else if (scoreCurrentInfo.getCurrentSkpIncludeConditions()
										.equals(WebAppConst.SKP_OR_CONDITION)) {
									query.append("(tranDescScore.bit_value & :CURRENT_INCLUDE_BIT_VALUE) > 0 ");
								}
								query.append("OR ");
								if (scoreCurrentInfo.getCurrentSkpExcludeConditions()
										.equals(WebAppConst.SKP_AND_CONDITION)) {
									query.append(
											"(tranDescScore.bit_value & :CURRENT_EXCLUDE_BIT_VALUE) != :CURRENT_EXCLUDE_BIT_VALUE ");
								} else if (scoreCurrentInfo.getCurrentSkpExcludeConditions()
										.equals(WebAppConst.SKP_OR_CONDITION)) {
									query.append("(tranDescScore.bit_value & :CURRENT_EXCLUDE_BIT_VALUE) = 0 ");
								}
								query.append(") ");
							}
						} else {
							if (!StringUtils.isBlank(currentIncludeCheckPoints)) {
								if (scoreCurrentInfo.getCurrentSkpIncludeConditions()
										.equals(WebAppConst.SKP_AND_CONDITION)) {
									query.append(
											"AND (tranDescScore.bit_value & :CURRENT_INCLUDE_BIT_VALUE) = :CURRENT_INCLUDE_BIT_VALUE ");
								} else if (scoreCurrentInfo.getCurrentSkpIncludeConditions()
										.equals(WebAppConst.SKP_OR_CONDITION)) {
									query.append("AND (tranDescScore.bit_value & :CURRENT_INCLUDE_BIT_VALUE) > 0 ");
								}
							} else if (!StringUtils.isBlank(currentExcludeCheckPoints)) {
								if (scoreCurrentInfo.getCurrentSkpExcludeConditions()
										.equals(WebAppConst.SKP_AND_CONDITION)) {
									query.append(
											"AND (tranDescScore.bit_value & :CURRENT_EXCLUDE_BIT_VALUE) != :CURRENT_EXCLUDE_BIT_VALUE ");
								} else if (scoreCurrentInfo.getCurrentSkpExcludeConditions()
										.equals(WebAppConst.SKP_OR_CONDITION)) {
									query.append("AND (tranDescScore.bit_value & :CURRENT_EXCLUDE_BIT_VALUE) = 0 ");
								}
							}
						}

						if (currentStateList != null && currentStateList.length > 0) {
							query.append("AND tranDescScore.latest_scoring_state IN :CURRENT_STATE_LIST ");
						}

						if (currentUpdateStartDate != null) {
							query.append("AND tranDescScore.update_date >= :CURRENT_UPDATE_START_DATE ");
						}

						if (currentUpdateEndDate != null) {
							query.append("AND tranDescScore.update_date <= :CURRENT_UPDATE_END_DATE ");
						}
					} else if (menuId.equals(WebAppConst.SCORE_SAMP_MENU_ID)
							|| menuId.equals(WebAppConst.STATE_TRAN_MENU_ID)
							|| menuId.equals(WebAppConst.FORCED_MENU_ID)) {
						query.append("AND tranDescScore.latest_scoring_state IN :CURRENT_STATE_LIST ");
					}

					if (menuId.equals(WebAppConst.REFERENCE_SAMP_MENU_ID)
							|| menuId.equals(WebAppConst.SCORE_SAMP_MENU_ID)
							|| menuId.equals(WebAppConst.STATE_TRAN_MENU_ID)) {
						query.append("AND tranDescScore.answer_paper_type NOT IN ( :ANSWER_PAPER_TYPES ) ");
					}

					if (searchByScorerRoleId == true) {
						if (historyScorerRoles != null) {
							query.append("AND tranDescScoreHistory.scorer_role_id IN :HISTORY_SCORER_ROLES ");
						}
					}

					query.append("AND tranDescScore.valid_flag = :VALID_FLAG ");
					query.append("AND tranDescScoreHistory.question_seq  = :QUESTION_SEQ ");
					query.append("AND tranDescScoreHistory.valid_flag = :VALID_FLAG ");

					if (scoreHistoryInfo != null && scoreHistoryInfo.isHistoryBlock()) {

						if (!historyScorerIdList.isEmpty()) {
							query.append(
									"AND tranDescScoreHistory.latest_screen_scorer_id IN :HISTORY_SCORER_ID_LIST ");
						}
						// This is for Quality Check Flag
						/*
						 * if (WebAppConst.SPEAKING_TYPE
						 * .equals(questionInfo.getQuestionType()) ||
						 * WebAppConst.WRITING_TYPE .equals(questionInfo
						 * .getQuestionType())) {
						 */
						if (scoreHistoryInfo.isHistoryQualityCheckFlag()) {
							query.append("AND tranDescScoreHistory.quality_check_flag IN :QUALITY_CHECK_FLAG ");
						}
						/* } */

						if (historyCategoryType != null && historyCategoryType == 2) {
							query.append(
									"AND tranDescScoreHistory.grade_seq IS null AND tranDescScoreHistory.pending_category_seq IS null ");
						} else if (historyCategoryType != null && historyCategoryType == 3) {

							if (!historyGradeSeqList.isEmpty()
									&& ArrayUtils.contains(scoreHistoryInfo.getHistoryGradeNum(), -1)) {
								query.append("AND (tranDescScoreHistory.grade_seq IN :HISTORY_GRADE_SEQ_LIST ");
								query.append("OR tranDescScoreHistory.scoring_state = :NO_GRADE_SCORING_STATE) ");
							} else {
								if (!historyGradeSeqList.isEmpty()) {
									query.append("AND tranDescScoreHistory.grade_seq IN :HISTORY_GRADE_SEQ_LIST ");
								}

								if (ArrayUtils.contains(scoreHistoryInfo.getHistoryGradeNum(), -1)) {
									query.append("AND tranDescScoreHistory.scoring_state = :NO_GRADE_SCORING_STATE ");
								}
							}
						} else if (historyCategoryType != null && historyCategoryType == 4
								&& !historyPendingCategorySeqList.isEmpty()) {
							query.append(
									"AND tranDescScoreHistory.pending_category_seq IN :HISTORY_PENDING_CATEGORY_SEQ_LIST ");
						} else if (historyCategoryType != null && historyCategoryType == 5
								&& !historyDenyCategorySeqList.isEmpty()) {
							query.append(
									"AND tranDescScoreHistory.deny_category_seq IN :HISTORY_DENY_CATEGORY_SEQ_LIST ");
						}

						if (!StringUtils.isBlank(historyIncludeCheckPoints)
								&& !StringUtils.isBlank(historyExcludeCheckPoints)) {
							if (scoreInputInfo.getScoreHistoryInfo().getPastSkpConditions()
									.equals(WebAppConst.SKP_AND_CONDITION)) {
								if (scoreHistoryInfo.getPastSkpIncludeConditions()
										.equals(WebAppConst.SKP_AND_CONDITION)) {
									query.append(
											"AND (tranDescScoreHistory.bit_value & :HISTORY_INCLUDE_BIT_VALUE) = :HISTORY_INCLUDE_BIT_VALUE ");
								} else if (scoreHistoryInfo.getPastSkpIncludeConditions()
										.equals(WebAppConst.SKP_OR_CONDITION)) {
									query.append(
											"AND (tranDescScoreHistory.bit_value & :HISTORY_INCLUDE_BIT_VALUE) > 0 ");
								}
								if (scoreHistoryInfo.getPastSkpExcludeConditions()
										.equals(WebAppConst.SKP_AND_CONDITION)) {
									query.append(
											"AND (tranDescScoreHistory.bit_value & :HISTORY_EXCLUDE_BIT_VALUE) != :HISTORY_EXCLUDE_BIT_VALUE ");
								} else if (scoreHistoryInfo.getPastSkpExcludeConditions()
										.equals(WebAppConst.SKP_OR_CONDITION)) {
									query.append(
											"AND (tranDescScoreHistory.bit_value & :HISTORY_EXCLUDE_BIT_VALUE) = 0 ");
								}

							} else if (scoreInputInfo.getScoreHistoryInfo().getPastSkpConditions()
									.equals(WebAppConst.SKP_OR_CONDITION)) {
								// query.append("AND
								// ((tranDescScoreHistory.bit_value &
								// :HISTORY_INCLUDE_BIT_VALUE) > 0 OR
								// (tranDescScoreHistory.bit_value &
								// :HISTORY_EXCLUDE_BIT_VALUE) = 0)");
								query.append("AND ( ");
								if (scoreHistoryInfo.getPastSkpIncludeConditions()
										.equals(WebAppConst.SKP_AND_CONDITION)) {
									query.append(
											"(tranDescScoreHistory.bit_value & :HISTORY_INCLUDE_BIT_VALUE) = :HISTORY_INCLUDE_BIT_VALUE ");
								} else if (scoreHistoryInfo.getPastSkpIncludeConditions()
										.equals(WebAppConst.SKP_OR_CONDITION)) {
									query.append("(tranDescScoreHistory.bit_value & :HISTORY_INCLUDE_BIT_VALUE) > 0 ");
								}
								query.append("OR ");
								if (scoreHistoryInfo.getPastSkpExcludeConditions()
										.equals(WebAppConst.SKP_AND_CONDITION)) {
									query.append(
											"(tranDescScoreHistory.bit_value & :HISTORY_EXCLUDE_BIT_VALUE) != :HISTORY_EXCLUDE_BIT_VALUE ");
								} else if (scoreHistoryInfo.getPastSkpExcludeConditions()
										.equals(WebAppConst.SKP_OR_CONDITION)) {
									query.append("(tranDescScoreHistory.bit_value & :HISTORY_EXCLUDE_BIT_VALUE) = 0 ");
								}
								query.append(") ");
							}
						} else {
							if (!StringUtils.isBlank(historyIncludeCheckPoints)) {
								if (scoreHistoryInfo.getPastSkpIncludeConditions()
										.equals(WebAppConst.SKP_AND_CONDITION)) {
									query.append(
											"AND (tranDescScoreHistory.bit_value & :HISTORY_INCLUDE_BIT_VALUE) = :HISTORY_INCLUDE_BIT_VALUE ");
								} else if (scoreHistoryInfo.getPastSkpIncludeConditions()
										.equals(WebAppConst.SKP_OR_CONDITION)) {
									query.append(
											"AND (tranDescScoreHistory.bit_value & :HISTORY_INCLUDE_BIT_VALUE) > 0 ");
								}
							} else if (!StringUtils.isBlank(historyExcludeCheckPoints)) {
								if (scoreHistoryInfo.getPastSkpExcludeConditions()
										.equals(WebAppConst.SKP_AND_CONDITION)) {
									query.append(
											"AND (tranDescScoreHistory.bit_value & :HISTORY_EXCLUDE_BIT_VALUE) != :HISTORY_EXCLUDE_BIT_VALUE ");
								} else if (scoreHistoryInfo.getPastSkpExcludeConditions()
										.equals(WebAppConst.SKP_OR_CONDITION)) {
									query.append(
											"AND (tranDescScoreHistory.bit_value & :HISTORY_EXCLUDE_BIT_VALUE) = 0 ");
								}
							}
						}

						if (historyEventList != null && historyEventList.length > 0) {
							query.append("AND tranDescScoreHistory.event_id IN :HISTORY_EVENT_LIST ");
						}

						if (historyUpdateStartDate != null) {
							query.append("AND tranDescScoreHistory.update_date >= :HISTORY_UPDATE_START_DATE ");
						}

						if (historyUpdateEndDate != null) {
							query.append("AND tranDescScoreHistory.update_date <= :HISTORY_UPDATE_END_DATE ");
						}
					}

					if (menuId.equals(WebAppConst.REFERENCE_SAMP_MENU_ID)
							|| menuId.equals(WebAppConst.SCORE_SAMP_MENU_ID)
							|| menuId.equals(WebAppConst.STATE_TRAN_MENU_ID)) {
						query.append(
								"AND ( SELECT count(*) from tran_desc_score_history th where pending_category in ( :PENDING_CATEGORIES ) AND tranDescScore.answer_seq = th.answer_seq )<=0 ");
						// query.append("AND ( SELECT count(*) from
						// tran_desc_score_history th where deny_category in (
						// :DENY_CATEGORIES ) AND tranDescScore.answer_seq =
						// th.answer_seq )<=0 ");
					}

					query.append(") score ");
					query.append(
							"SET inspect_flag = :LOCK, inspect_group_seq = :INSPECTION_GROUP_SEQ, update_date = :UPDATE_DATE ");
					query.append(" WHERE target.answer_seq = score.answerSeq ");

					SQLQuery queryObj = session.createSQLQuery(query.toString());

					if (menuId.equals(WebAppConst.REFERENCE_SAMP_MENU_ID)
							|| menuId.equals(WebAppConst.SCORE_SAMP_MENU_ID)
							|| menuId.equals(WebAppConst.STATE_TRAN_MENU_ID)) {
						queryObj.setParameterList("ANSWER_PAPER_TYPES", WebAppConst.ANSWER_PAPER_TYPES);
						queryObj.setParameterList("PENDING_CATEGORIES", WebAppConst.PENDING_CATEGORIES);
						/*
						 * queryObj.setParameterList("DENY_CATEGORIES",
						 * WebAppConst.PENDING_CATEGORIES);
						 */
					}
					if (menuId.equals(WebAppConst.STATE_TRAN_MENU_ID)) {
						if ((scoreStartRange != null) && (scoreEndRange != null)) {
							queryObj.setParameter("SUBJECT_CODE", subjectCode);
							queryObj.setParameter("SCORE_START_RANGE", scoreStartRange);
							queryObj.setParameter("SCORE_END_RANGE", scoreEndRange);
						}
						queryObj.setParameter("INSPECT_FLAG", WebAppConst.F);
						if (!selectAllFlag) {
							queryObj.setParameterList("ANSWER_SEQUENCE", answerSeq);
						}
						/*
						 * if (WebAppConst.SPEAKING_TYPE
						 * .equals(questionInfo.getQuestionType()) ||
						 * WebAppConst.WRITING_TYPE .equals(questionInfo
						 * .getQuestionType())) {
						 */
						queryObj.setParameter("QUALITY_FLAG", WebAppConst.QUALITY_MARK_FLAG_FALSE);
						/* } */

					}

					if (!StringUtils.isBlank(answerFormNum)) {
						queryObj.setParameter("ANSWER_FORM_NUM", answerFormNum);
					}

					setHistoryParameters(queryObj);

					setCurrentParameters(queryObj);

					queryObj.setInteger("QUESTION_SEQ", questionInfo.getQuestionSeq());
					queryObj.setCharacter("UNLOCK", WebAppConst.UNLOCK);
					queryObj.setCharacter("VALID_FLAG", WebAppConst.VALID_FLAG);
					queryObj.setParameter("LOCK", WebAppConst.LOCK);
					queryObj.setParameter("INSPECTION_GROUP_SEQ", maxInspectGroupSeq);
					queryObj.setParameter("UPDATE_DATE", new Date());

					return queryObj.executeUpdate();

				}

				public void setCurrentParameters(SQLQuery queryObj) {

					if (scoreCurrentInfo != null && scoreCurrentInfo.isCurrentBlock()) {

						if (!StringUtils.isBlank(punchText)) {
							queryObj.setParameter("PUNCH_TEXT", punchText);
						}
						if (!currentScorerIdList.isEmpty()) {
							queryObj.setParameterList("CURRENT_SCORER_ID_LIST", currentScorerIdList);
						}

						if (scoreCurrentInfo.isCurrentQualityCheckFlag()) {
							/*
							 * if (WebAppConst.SPEAKING_TYPE
							 * .equals(questionInfo .getQuestionType()) ||
							 * WebAppConst.WRITING_TYPE .equals(questionInfo
							 * .getQuestionType())) {
							 */
							queryObj.setParameterList("QUALITY_CHECK_FLAG",
									ArrayUtils.subarray(WebAppConst.QUALITY_MARK_FLAG_LIST, 0, 1));
							/* } */

						} /*
							 * else { if (WebAppConst.SPEAKING_TYPE
							 * .equals(questionInfo .getQuestionType()) ||
							 * WebAppConst.WRITING_TYPE .equals(questionInfo
							 * .getQuestionType())) { queryObj.setParameterList(
							 * "QUALITY_CHECK_FLAG",
							 * WebAppConst.QUALITY_MARK_FLAG_LIST); }
							 * 
							 * }
							 */

						if (currentCategoryType != null && currentCategoryType == 3) {

							if (!currentGradeSeqList.isEmpty()) {
								queryObj.setParameterList("CURRENT_GRADE_SEQ_LIST", currentGradeSeqList);
							}

							if (ArrayUtils.contains(scoreCurrentInfo.getCurrentGradeNum(), -1)) {
								queryObj.setShort("NO_GRADE_SCORING_STATE", WebAppConst.NO_GRADE_SCORING_STATE);
							}
						} else if (currentCategoryType != null && currentCategoryType == 4
								&& !currentPendingCategorySeqList.isEmpty()) {

							queryObj.setParameterList("CURRENT_PENDING_CATEGORY_SEQ_LIST",
									scoreCurrentInfo.getCurrentPendingCategorySeqList());
						} else if (currentCategoryType != null && currentCategoryType == 5
								&& !currentDenyCategorySeqList.isEmpty()) {

							queryObj.setParameterList("CURRENT_DENY_CATEGORY_SEQ_LIST",
									scoreCurrentInfo.getCurrentDenyCategorySeqList());
						}

						if (!StringUtils.isBlank(currentIncludeCheckPoints)
								&& !StringUtils.isBlank(currentExcludeCheckPoints)) {
							queryObj.setInteger("CURRENT_INCLUDE_BIT_VALUE",
									scoreCurrentInfo.getCurrentIncludeBitValue());
							queryObj.setInteger("CURRENT_EXCLUDE_BIT_VALUE",
									scoreCurrentInfo.getCurrentExcludeBitValue());
						} else {
							if (!StringUtils.isBlank(currentIncludeCheckPoints)) {
								queryObj.setInteger("CURRENT_INCLUDE_BIT_VALUE",
										scoreCurrentInfo.getCurrentIncludeBitValue());
							} else if (!StringUtils.isBlank(currentExcludeCheckPoints)) {
								queryObj.setInteger("CURRENT_EXCLUDE_BIT_VALUE",
										scoreCurrentInfo.getCurrentExcludeBitValue());
							}
						}

						if (currentStateList != null && currentStateList.length > 0) {
							queryObj.setParameterList("CURRENT_STATE_LIST", scoreCurrentInfo.getCurrentStateList());
						}

						if (currentUpdateStartDate != null) {
							queryObj.setParameter("CURRENT_UPDATE_START_DATE", currentUpdateStartDate);
						}

						if (currentUpdateEndDate != null) {
							queryObj.setParameter("CURRENT_UPDATE_END_DATE", currentUpdateEndDate);
						}
					} else if (menuId.equals(WebAppConst.STATE_TRAN_MENU_ID)) {
						queryObj.setParameterList("CURRENT_STATE_LIST", WebAppConst.STATE_TRANSITION_CURRENT_STATES);
					}

					if (searchByScorerRoleId == true) {
						if (currentScorerRoles != null) {
							queryObj.setParameterList("CURRENT_SCORER_ROLES", Arrays.asList(currentScorerRoles));
						}
					}

				}

				public void setHistoryParameters(SQLQuery queryObj) {

					if (scoreHistoryInfo != null && scoreHistoryInfo.isHistoryBlock()) {

						if (!historyScorerIdList.isEmpty()) {
							queryObj.setParameterList("HISTORY_SCORER_ID_LIST", historyScorerIdList);
						}
						if (scoreHistoryInfo.isHistoryQualityCheckFlag()) {
							/*
							 * if (WebAppConst.SPEAKING_TYPE
							 * .equals(questionInfo .getQuestionType()) ||
							 * WebAppConst.WRITING_TYPE .equals(questionInfo
							 * .getQuestionType())) {
							 */
							queryObj.setParameterList("QUALITY_CHECK_FLAG",
									ArrayUtils.subarray(WebAppConst.QUALITY_MARK_FLAG_LIST, 0, 1));
							/* } */

						} /*
							 * else { if (WebAppConst.SPEAKING_TYPE
							 * .equals(questionInfo .getQuestionType()) ||
							 * WebAppConst.WRITING_TYPE .equals(questionInfo
							 * .getQuestionType())) { queryObj.setParameterList(
							 * "QUALITY_CHECK_FLAG",
							 * WebAppConst.QUALITY_MARK_FLAG_LIST); } }
							 */

						if (historyCategoryType != null && historyCategoryType == 3) {

							if (!historyGradeSeqList.isEmpty()) {
								queryObj.setParameterList("HISTORY_GRADE_SEQ_LIST", historyGradeSeqList);
							}

							if (ArrayUtils.contains(scoreHistoryInfo.getHistoryGradeNum(), -1)) {
								queryObj.setShort("NO_GRADE_SCORING_STATE", WebAppConst.NO_GRADE_SCORING_STATE);
							}
						} else if (historyCategoryType != null && historyCategoryType == 4
								&& !historyPendingCategorySeqList.isEmpty()) {

							queryObj.setParameterList("HISTORY_PENDING_CATEGORY_SEQ_LIST",
									historyPendingCategorySeqList);
						} else if (historyCategoryType != null && historyCategoryType == 5
								&& !historyDenyCategorySeqList.isEmpty()) {

							queryObj.setParameterList("HISTORY_DENY_CATEGORY_SEQ_LIST", historyDenyCategorySeqList);
						}

						if (!StringUtils.isBlank(historyIncludeCheckPoints)
								&& !StringUtils.isBlank(historyExcludeCheckPoints)) {
							queryObj.setInteger("HISTORY_INCLUDE_BIT_VALUE",
									scoreHistoryInfo.getHistoryIncludeBitValue());
							queryObj.setInteger("HISTORY_EXCLUDE_BIT_VALUE",
									scoreHistoryInfo.getHistoryExcludeBitValue());
						} else {
							if (!StringUtils.isBlank(historyIncludeCheckPoints)) {
								queryObj.setInteger("HISTORY_INCLUDE_BIT_VALUE",
										scoreHistoryInfo.getHistoryIncludeBitValue());
							} else if (!StringUtils.isBlank(historyExcludeCheckPoints)) {
								queryObj.setInteger("HISTORY_EXCLUDE_BIT_VALUE",
										scoreHistoryInfo.getHistoryExcludeBitValue());
							}
						}

						if (historyEventList != null && historyEventList.length > 0) {
							queryObj.setParameterList("HISTORY_EVENT_LIST", historyEventList);
						}

						if (historyUpdateStartDate != null) {
							queryObj.setParameter("HISTORY_UPDATE_START_DATE", historyUpdateStartDate);
						}

						if (historyUpdateEndDate != null) {
							queryObj.setParameter("HISTORY_UPDATE_END_DATE", historyUpdateEndDate);
						}

						if (searchByScorerRoleId == true) {
							if (historyScorerRoles != null) {
								queryObj.setParameterList("HISTORY_SCORER_ROLES", Arrays.asList(historyScorerRoles));
							}
						}

					}

				}
			});
		} catch (RuntimeException re) {
			throw re;
		}

	}

	/*
	 * Search For Questionwise DailyStatus Report
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List getDailyuStatusSearchList(DailyStatusSearchInfo dailyStatusSearchInfo, String connectionString,
			String questionSeq) {
		try {
			final StringBuilder queryString = new StringBuilder();
			/*
			 * String fromDate = dailyStatusSearchInfo
			 * .getQuestionwiseSelectedYearFrom() + "-" +
			 * dailyStatusSearchInfo.getQuestionwiseSelectedMonthFrom() + "-" +
			 * dailyStatusSearchInfo.getQuestionwiseSelectedDayFrom() + " " +
			 * dailyStatusSearchInfo.getQuestionwiseSelectedStartHours() + ":" +
			 * dailyStatusSearchInfo.getQuestionwiseSelectedStartMin() + ":00";
			 * String toDate = dailyStatusSearchInfo
			 * .getQuestionwiseSelectedYearTo() + "-" +
			 * dailyStatusSearchInfo.getQuestionwiseSelectedMonthTo() + "-" +
			 * dailyStatusSearchInfo.getQuestionwiseSelectedDayTo() + " " +
			 * dailyStatusSearchInfo.getQuestionwiseSelectedEndHours() + ":" +
			 * dailyStatusSearchInfo.getQuestionwiseSelectedEndMin() + ":00";
			 */

			queryString.append("SELECT ");
			queryString.append(" new List(questionSeq,sum(1) as answerTotal, ");
			queryString.append("sum(case when latestScoringState=500 then 1 else 0 end) as answerDecision,  ");
			queryString.append("sum(case when latestScoringState=501 then 1 else 0 end) as confirmBatch,  ");
			queryString.append("sum(case when latestScoringState=121 then 1 else 0 end) as firstTimeScoreWait,  ");
			queryString.append("sum(case when latestScoringState=122 then 1 else 0 end) as firstTimeScoreTemp,  ");
			queryString.append("sum(case when latestScoringState=123 then 1 else 0 end) as firstTimeScorePending,  ");
			queryString.append("sum(case when latestScoringState=131 then 1 else 0 end) as secondTimeScoreWait,  ");
			queryString.append("sum(case when latestScoringState=132 then 1 else 0 end) as secondTimeScoreTemp,  ");
			queryString.append("sum(case when latestScoringState=133 then 1 else 0 end) as secondTimeScorePending,  ");
			queryString.append("sum(case when latestScoringState=141 then 1 else 0 end) as checkingWorkWait,  ");
			queryString.append("sum(case when latestScoringState=144 then 1 else 0 end) as checkingApproveTemp,  ");
			queryString.append("sum(case when latestScoringState=145 then 1 else 0 end) as chekingDenyTemp,  ");
			queryString.append("sum(case when latestScoringState=161 then 1 else 0 end) as pendingScoringWait,  ");
			queryString.append("sum(case when latestScoringState=162 then 1 else 0 end) as pendingScoringTemp,  ");
			queryString.append("sum(case when latestScoringState=163 then 1 else 0 end) as pendingScorePendingTemp,  ");
			queryString.append("sum(case when latestScoringState=171 then 1 else 0 end) as missmatchScoreWait,  ");
			queryString.append("sum(case when latestScoringState=172 then 1 else 0 end) as missmatchScoreTemp,  ");
			queryString.append("sum(case when latestScoringState=173 then 1 else 0 end) as missmatchPending,  ");
			queryString.append("sum(case when latestScoringState=151 then 1 else 0 end) as inispectionMenuWait,  ");
			queryString.append("sum(case when latestScoringState=154 then 1 else 0 end) as inispectionMenuApprove,  ");
			queryString.append("sum(case when latestScoringState=155 then 1 else 0 end) as inispectionMenuDeny,  ");
			queryString
					.append("sum(case when latestScoringState=181 then 1 else 0 end) as outOfBoundaryScoringWait,  ");
			queryString
					.append("sum(case when latestScoringState=182 then 1 else 0 end) as outOfBoundaryScoringTemp,  ");
			queryString
					.append("sum(case when latestScoringState=183 then 1 else 0 end) as outOfBoundaryPendingTemp,  ");
			queryString.append("sum(case when latestScoringState=191 then 1 else 0 end) as denyScoreWait,  ");
			queryString.append("sum(case when latestScoringState=192 then 1 else 0 end) as denyScoreTemp,  ");
			queryString.append("sum(case when latestScoringState=193 then 1 else 0 end) as denyScorePending,  ");
			queryString.append("sum(case when latestScoringState=211 then 1 else 0 end) as noGradeScoringWait,  ");
			queryString.append("sum(case when latestScoringState=212 then 1 else 0 end) as noGradeScoringTemp,  ");
			queryString.append("sum(case when latestScoringState=213 then 1 else 0 end) as noGradePengingTemp,  ");
			queryString.append("sum(case when latestScoringState=221 then 1 else 0 end) as scoringSamplingTemp,  ");
			queryString.append("sum(case when latestScoringState=222 then 1 else 0 end) as scoringSamplingPending,  ");
			queryString.append("sum(case when latestScoringState=241 then 1 else 0 end) as forceScoringTemp,  ");
			queryString.append("sum(case when latestScoringState=242 then 1 else 0 end) as forceScoringPending) ");
			/*
			 * queryString .append(
			 * "sum(case when latestScoringState=21 then 1 else 0 end) as punchWait, "
			 * ); queryString .append(
			 * "sum(case when latestScoringState=31 then 1 else 0 end) as punchDataReadWait, "
			 * ); queryString .append(
			 * "sum(case when latestScoringState=51 then 1 else 0 end) as batchScoringWait, "
			 * ); queryString .append(
			 * "sum(case when latestScoringState=52 then 1 else 0 end) as batchScoringOputputComplete, "
			 * ); queryString .append(
			 * "sum(case when latestScoringState=53 then 1 else 0 end) as batchScoringComplete, "
			 * ); queryString .append(
			 * "sum(case when latestScoringState=59 then 1 else 0 end) as scoringNotPossibleByBatch, "
			 * ); queryString .append(
			 * "sum(case when latestScoringState=61 then 1 else 0 end) as scoringCompleteByBatch) "
			 * );
			 */

			queryString.append("FROM TranDescScore  ");
			queryString.append(" where ");
			/*
			 * queryString.append(" updateDate>='" + fromDate +
			 * "' and  updateDate<='" + toDate + "'  ");
			 * queryString.append(" and ");
			 */
			queryString.append(" valid_flag='" + WebAppConst.VALID_FLAG + "'");

			queryString.append(
					" and latestScoringState in (500,501,121,122,123,131,132,133,141,144,145,161,162,163,171,172,173,151,154,155,181,182,183,191,192,193,211,212,213,221,222,241,242) ");
			/* ,21,31,51,52,53,59,61 */
			if (questionSeq != null && !questionSeq.equals("")) {
				queryString.append("and questionSeq in (" + questionSeq + ")  ");
			}

			queryString.append("group by questionSeq  ");

			HibernateTemplate hibernateTemplateReplica = getHibernateTemplateReplica(connectionString);
			if (hibernateTemplateReplica != null) {
				return getHibernateTemplateReplica(connectionString).find(queryString.toString());

			} else {
				return getHibernateTemplate(connectionString).find(queryString.toString());
			}
		} catch (RuntimeException re) {
			throw re;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saiten.dao.TranDescScoreDAO#getSearchList(java.lang.Integer,
	 * java.lang.String)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List getDailyuStatusSearchListByScorer(DailyStatusSearchInfo dailyStatusSearchInfo, String connectionString,
			String scorerIds, String questionSeq) {
		try {
			final StringBuilder queryString = new StringBuilder();
			String fromDate = dailyStatusSearchInfo.getScorewiseSelectedYearFrom() + "-"
					+ dailyStatusSearchInfo.getScorewiseSelectedMonthFrom() + "-"
					+ dailyStatusSearchInfo.getScorewiseSelectedDayFrom() + " "
					+ dailyStatusSearchInfo.getScorewiseSelectedStartHours() + ":"
					+ dailyStatusSearchInfo.getScorewiseSelectedStartMin() + ":00";
			String toDate = dailyStatusSearchInfo.getScorewiseSelectedYearTo() + "-"
					+ dailyStatusSearchInfo.getScorewiseSelectedMonthTo() + "-"
					+ dailyStatusSearchInfo.getScorewiseSelectedDayTo() + " "
					+ dailyStatusSearchInfo.getScorewiseSelectedEndHours() + ":"
					+ dailyStatusSearchInfo.getScorewiseSelectedEndMin() + ":00";

			queryString.append("SELECT ");
			queryString.append(" new List(latestScreenScorerId,sum(1) as scoringTotal, ");
			queryString.append(
					"sum(case when (scoringState=122 or scoringState=123) then 1 else 0 end) as firstTimeScoringTotal,  ");
			queryString.append(
					"sum(case when (scoringState=132 or scoringState=133) then 1 else 0 end) as secondTimeScoringTotal,  ");
			queryString.append("sum(case when (scoringState=162 or scoringState=163) then 1 else 0 end) as pending,  ");
			queryString
					.append("sum(case when (scoringState=172 or scoringState=173) then 1 else 0 end) as mismatch,  ");
			queryString.append(
					"sum(case when (scoringState=154 or scoringState=155) then 1 else 0 end) as inspectionMenu,  ");
			queryString.append(
					"sum(case when (scoringState=192 or scoringState=193) then 1 else 0 end) as denyScoringTotal,  ");
			queryString.append(
					"sum(case when (scoringState=221 or scoringState=222) then 1 else 0 end) as scoringSamplingTotal,  ");
			queryString.append(
					"sum(case when (scoringState=241 or scoringState=242) then 1 else 0 end) as forcedScoring )  ");
			queryString.append("FROM TranDescScoreHistory  ");
			queryString.append("where updateDate>='" + fromDate + "' and  updateDate<='" + toDate + "'  ");
			queryString.append("and valid_flag='" + WebAppConst.VALID_FLAG + "'");
			if (scorerIds != null && !scorerIds.equals("")) {
				queryString.append(" and  latestScreenScorerId in (" + scorerIds + ") ");

			}

			if (questionSeq != null && !questionSeq.equals("")) {
				queryString.append(" and  questionSeq in (" + questionSeq + ") ");
			}

			queryString
					.append(" and scoringState in (122,123,132,133,162,163,172,173,154,155,192,193,221,222,241,242) ");
			queryString.append("group by scorerId  ");

			HibernateTemplate hibernateTemplateReplica = getHibernateTemplateReplica(connectionString);
			if (hibernateTemplateReplica != null) {
				return getHibernateTemplateReplica(connectionString).find(queryString.toString());
			} else {
				return getHibernateTemplate(connectionString).find(queryString.toString());
			}

		} catch (RuntimeException re) {
			throw re;
		}

	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getGradeWiseAnswerDetails(String questionSeq, String connectionString, Character questionType) {
		try {
			final StringBuilder queryString = new StringBuilder();
			queryString.append("SELECT ");
			queryString.append("grade_num, ");
			queryString.append("sum(case when latest_scoring_state=500 then 1 else 0 end) as confirm,  ");
			queryString.append("sum(case when latest_scoring_state=122 then 1 else 0 end) as firstTimeScoreTemp,  ");
			if (questionType == Arrays.asList(WebAppConst.QUESTION_TYPE).get(1)) {
				queryString.append("sum(case when latest_scoring_state=141 then 1 else 0 end) as checkingWorkWait,  ");

			} else {
				queryString
						.append("sum(case when latest_scoring_state=131 then 1 else 0 end) as secondTimeScoreWait,  ");
			}

			queryString.append("sum(case when latest_scoring_state=132 then 1 else 0 end) as secondTimeScoreTemp,  ");
			queryString.append("sum(case when latest_scoring_state=144 then 1 else 0 end) as checkingApproveTemp,  ");
			queryString.append("sum(case when latest_scoring_state=145 then 1 else 0 end) as chekingDenyTemp,  ");
			queryString.append("sum(case when latest_scoring_state=162 then 1 else 0 end) as pendingScoringTemp,  ");
			queryString.append("sum(case when latest_scoring_state=172 then 1 else 0 end) as mismatchScoreTemp,  ");
			queryString.append("sum(case when latest_scoring_state=154 then 1 else 0 end) as inspectionMenuApprove,  ");
			queryString.append("sum(case when latest_scoring_state=155 then 1 else 0 end) as inspectionMenuDeny,  ");
			queryString
					.append("sum(case when latest_scoring_state=182 then 1 else 0 end) as outOfBoundaryScoringTemp,  ");
			queryString.append("sum(case when latest_scoring_state=192 then 1 else 0 end) as denyScoreTemp,  ");
			queryString.append("sum(case when latest_scoring_state=212 then 1 else 0 end) as noGradeScoringTemp,  ");
			queryString.append("sum(case when latest_scoring_state=221 then 1 else 0 end) as scoringSamplingTemp,  ");
			queryString.append("sum(case when latest_scoring_state=241 then 1 else 0 end) as forceScoringTemp,  ");
			queryString.append("sum(case when latest_scoring_state=151 then 1 else 0 end) as inspectionMenuWait,  ");
			queryString.append("sum(case when latest_scoring_state=191 then 1 else 0 end) as denyScoringWait  ");

			queryString.append("FROM tran_desc_score  ");

			queryString.append("WHERE valid_flag='" + WebAppConst.VALID_FLAG + "' ");
			queryString.append("AND  question_seq in (" + questionSeq + ") ");

			queryString.append(
					"AND latest_scoring_state in (500,122,131,132,144,145,162,172,154,155,182,192,212,221,241,151,141,191) ");
			queryString.append("GROUP BY grade_num ");
			queryString.append("WITH ROLLUP ");

			HibernateTemplate hibernateTemplateReplica = getHibernateTemplateReplica(connectionString);
			if (hibernateTemplateReplica != null) {
				return getHibernateTemplateReplica(connectionString).execute(new HibernateCallback<List>() {
					public List doInHibernate(Session session) throws HibernateException {
						Query queryObj = session.createSQLQuery(queryString.toString());
						return queryObj.list();
					}
				});
			} else {
				return getHibernateTemplate(connectionString).execute(new HibernateCallback<List>() {
					public List doInHibernate(Session session) throws HibernateException {
						Query queryObj = session.createSQLQuery(queryString.toString());
						return queryObj.list();
					}
				});
			}

		} catch (RuntimeException re) {
			throw re;
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getPendingCategoryWiseAnswerDetails(String questionSeq, String connectionString) {
		try {
			final StringBuilder queryString = new StringBuilder();
			queryString.append("SELECT ");
			queryString.append("pending_category, ");
			queryString.append("sum(case when latest_scoring_state=123 then 1 else 0 end) as firstTimeScorePending,  ");
			queryString
					.append("sum(case when latest_scoring_state=133 then 1 else 0 end) as secondTimeScorePending,  ");
			queryString.append("sum(case when latest_scoring_state=161 then 1 else 0 end) as pendingScoringWait,  ");
			queryString
					.append("sum(case when latest_scoring_state=163 then 1 else 0 end) as pendingScorePendingTemp,  ");
			queryString.append("sum(case when latest_scoring_state=173 then 1 else 0 end) as mismatchPending,  ");
			queryString
					.append("sum(case when latest_scoring_state=183 then 1 else 0 end) as outOfBoundaryPendingTemp,  ");
			queryString.append("sum(case when latest_scoring_state=193 then 1 else 0 end) as denyScorePending,  ");
			queryString.append("sum(case when latest_scoring_state=213 then 1 else 0 end) as noGradePengingTemp,  ");
			queryString
					.append("sum(case when latest_scoring_state=222 then 1 else 0 end) as scoringSamplingPending,  ");
			queryString.append("sum(case when latest_scoring_state=242 then 1 else 0 end) as forceScoringPending  ");

			queryString.append("FROM tran_desc_score  ");

			queryString.append("WHERE valid_flag='" + WebAppConst.VALID_FLAG + "' ");
			queryString.append("AND  question_seq in (" + questionSeq + ") ");

			queryString.append("AND latest_scoring_state in (123,133,161,163,173,183,193,213,222,242) ");
			queryString.append("GROUP BY pending_category ");
			queryString.append("WITH ROLLUP ");

			HibernateTemplate hibernateTemplateReplica = getHibernateTemplateReplica(connectionString);
			if (hibernateTemplateReplica != null) {
				return getHibernateTemplateReplica(connectionString).execute(new HibernateCallback<List>() {
					public List doInHibernate(Session session) throws HibernateException {
						Query queryObj = session.createSQLQuery(queryString.toString());
						return queryObj.list();
					}
				});
			} else {
				return getHibernateTemplate(connectionString).execute(new HibernateCallback<List>() {
					public List doInHibernate(Session session) throws HibernateException {
						Query queryObj = session.createSQLQuery(queryString.toString());
						return queryObj.list();
					}
				});
			}

		} catch (RuntimeException re) {
			throw re;
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getMarkValueWiseAnswerDetails(String questionSeq, String connectionString, Character questionType) {
		try {
			final StringBuilder queryString = new StringBuilder();
			queryString.append("SELECT ");
			queryString.append("mark_value, ");
			queryString.append("sum(case when latest_scoring_state=500 then 1 else 0 end) as confirm,  ");
			queryString.append("sum(case when latest_scoring_state=122 then 1 else 0 end) as firstTimeScoreTemp,  ");

			if (questionType == Arrays.asList(WebAppConst.QUESTION_TYPE).get(1)) {
				queryString.append("sum(case when latest_scoring_state=141 then 1 else 0 end) as checkingWorkWait,  ");
			} else {
				queryString
						.append("sum(case when latest_scoring_state=131 then 1 else 0 end) as secondTimeScoreWait,  ");
			}

			queryString.append("sum(case when latest_scoring_state=132 then 1 else 0 end) as secondTimeScoreTemp,  ");
			queryString.append("sum(case when latest_scoring_state=144 then 1 else 0 end) as checkingApproveTemp,  ");
			queryString.append("sum(case when latest_scoring_state=145 then 1 else 0 end) as chekingDenyTemp,  ");
			queryString.append("sum(case when latest_scoring_state=162 then 1 else 0 end) as pendingScoringTemp,  ");
			queryString.append("sum(case when latest_scoring_state=172 then 1 else 0 end) as mismatchScoreTemp,  ");
			queryString.append("sum(case when latest_scoring_state=154 then 1 else 0 end) as inspectionMenuApprove,  ");
			queryString.append("sum(case when latest_scoring_state=155 then 1 else 0 end) as inspectionMenuDeny,  ");
			queryString
					.append("sum(case when latest_scoring_state=182 then 1 else 0 end) as outOfBoundaryScoringTemp,  ");
			queryString.append("sum(case when latest_scoring_state=192 then 1 else 0 end) as denyScoreTemp,  ");
			queryString.append("sum(case when latest_scoring_state=212 then 1 else 0 end) as noGradeScoringTemp,  ");
			queryString.append("sum(case when latest_scoring_state=221 then 1 else 0 end) as scoringSamplingTemp,  ");
			queryString.append("sum(case when latest_scoring_state=241 then 1 else 0 end) as forceScoringTemp,  ");
			queryString.append("sum(case when latest_scoring_state=151 then 1 else 0 end) as inspectionMenuWait,  ");
			queryString.append("sum(case when latest_scoring_state=191 then 1 else 0 end) as denyScoringWait,  ");

			queryString.append("sum(case when latest_scoring_state=121 then 1 else 0 end) as firstTimeScoreWait  ");

			queryString.append("FROM tran_desc_score  ");

			queryString.append("WHERE valid_flag='" + WebAppConst.VALID_FLAG + "' ");
			queryString.append("AND  question_seq in (" + questionSeq + ") ");

			queryString.append(
					"AND latest_scoring_state in (500,121,122,131,132,144,145,162,172,154,155,182,192,212,221,241,151,141,191) ");
			queryString.append("GROUP BY mark_value ");
			queryString.append("WITH ROLLUP ");

			HibernateTemplate hibernateTemplateReplica = getHibernateTemplateReplica(connectionString);
			if (hibernateTemplateReplica != null) {
				return getHibernateTemplateReplica(connectionString).execute(new HibernateCallback<List>() {
					public List doInHibernate(Session session) throws HibernateException {
						Query queryObj = session.createSQLQuery(queryString.toString());
						return queryObj.list();
					}
				});
			} else {
				return getHibernateTemplate(connectionString).execute(new HibernateCallback<List>() {
					public List doInHibernate(Session session) throws HibernateException {
						Query queryObj = session.createSQLQuery(queryString.toString());
						return queryObj.list();
					}
				});
			}

		} catch (RuntimeException re) {
			throw re;
		}
	}

	/**
	 * 
	 * @param answerSeq
	 * @return testsetnum_seq
	 */
	@SuppressWarnings("rawtypes")
	public List findTestsetNumSeq(final Integer answerSeq, final String connectionString) {
		final StringBuilder query = new StringBuilder();
		query.append("SELECT tranDescScore.testsetnumSeq ");
		query.append("FROM TranDescScore as tranDescScore WHERE ");
		query.append("tranDescScore.answerSeq = :ANSWER_SEQ  ");
		List<String> paramNameList = new ArrayList<String>();
		// List valueList = new ArrayList();

		paramNameList.add(0, "ANSWER_SEQUENCE");

		/*
		 * valueList.add(0, answerSeq);
		 * 
		 * String[] paramNames = paramNameList.toArray(new String[paramNameList
		 * .size()]); Object[] values = valueList.toArray(new
		 * Object[valueList.size()]);
		 */

		// String[] paramNames = { "ANSWER_SEQ" };
		// Object[] values = { answerSeq };
		return getHibernateTemplate(connectionString).execute(new HibernateCallback<List>() {
			public List doInHibernate(Session session) throws HibernateException {
				Query queryObj = session.createQuery(query.toString());
				queryObj.setParameter("ANSWER_SEQ", answerSeq);
				return queryObj.list();
			}
		});
		/*
		 * try { return
		 * getHibernateTemplate().findByNamedParam(query.toString(),
		 * paramNames,values); }catch (RuntimeException re) { throw re; }
		 */
	}

	/**
	 * 
	 * @param questionSeq
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List findMaxInspectGroupSeq(final int questionSeq, final String connectionString) {

		final StringBuilder query = new StringBuilder();
		query.append("SELECT max(tranDescScore.inspectionGroupSeq) as maxInspectGroupSeq ");
		query.append("FROM TranDescScore as tranDescScore WHERE ");
		query.append("tranDescScore.questionSeq = :QUESTION_SEQ  ");
		try {
			return getHibernateTemplate(connectionString).execute(new HibernateCallback<List>() {
				public List doInHibernate(Session session) throws HibernateException {
					Query queryObj = session.createQuery(query.toString());
					queryObj.setParameter("QUESTION_SEQ", questionSeq);
					return queryObj.list();
				}
			});
		} catch (RuntimeException re) {
			throw re;
		}
	}

	@Override
	@SuppressWarnings("rawtypes")
	public List findKenshuRecords(final Integer questionSeq, final String connectionString, final int recordCont) {
		final StringBuilder query = new StringBuilder();

		// query.append("SELECT t.answerSeq, t.answerFormNum, t.imageFileName,
		// ");
		// query.append("t.gradeSeq, t.bitValue, t.questionSeq, ");
		// query.append("t.updateDate, t.markValue, t.latestScreenScorerId, ");
		// query.append("t.secondLatestScreenScorerId, t.gradeNum ");
		// query.append("FROM TranDescScore t ");
		// query.append("WHERE t.questionSeq = :QUESTION_SEQ ");
		// query.append("AND t.latestScoringState IN (:KESNHU_SAMPLING_STATES)
		// ");
		// query.append("AND t.validFlag = :VALID_FLAG ");
		// query.append("AND (t.kenshuSamplingFlag IS NULL ");
		// query.append("OR t.kenshuSamplingFlag = :FALSE) ");
		// query.append("ORDER BY RAND() ");
		query.append("SELECT t.gradeNum, COUNT(*) ");
		query.append("FROM TranDescScore t ");
		query.append("WHERE t.questionSeq = :QUESTION_SEQ ");
		query.append("AND t.latestScoringState IN (:KESNHU_SAMPLING_STATES) ");
		query.append("AND t.validFlag = :VALID_FLAG ");
		/*
		 * query.append("AND (t.kenshuSamplingFlag IS NULL ");
		 * query.append("OR t.kenshuSamplingFlag = :FALSE) ");
		 */
		query.append(" GROUP BY t.gradeNum");

		try {
			return getHibernateTemplate(connectionString).execute(new HibernateCallback<List>() {
				public List doInHibernate(Session session) throws HibernateException {
					Query queryObj = session.createQuery(query.toString());
					queryObj.setParameter("QUESTION_SEQ", questionSeq);
					queryObj.setParameterList("KESNHU_SAMPLING_STATES", WebAppConst.KENSU_RECORDS_STATES);
					queryObj.setParameter("VALID_FLAG", WebAppConst.VALID_FLAG);
					// queryObj.setParameter("FALSE", WebAppConst.F);
					// queryObj.setMaxResults(recordCont);
					return queryObj.list();
				}
			});
		} catch (RuntimeException re) {
			throw re;
		}
	}

	@Override
	@SuppressWarnings("rawtypes")
	public List isAnswerAlreadyChecked(final Integer answerSeq, final Date date, final String connectionString) {
		final StringBuilder query = new StringBuilder();
		query.append("FROM TranDescScore as t WHERE ");
		query.append("t.answerSeq = :ANSWER_SEQ ");
		try {
			return getHibernateTemplate(connectionString).execute(new HibernateCallback<List>() {
				public List doInHibernate(Session session) throws HibernateException {
					Query queryObj = session.createQuery(query.toString());
					queryObj.setParameter("ANSWER_SEQ", answerSeq);
					return queryObj.list();
				}
			});
		} catch (RuntimeException re) {
			throw re;
		}
	}

	@Override
	public int updateKunshuFlagByAnswerseq(final Integer answerSeq, final Date date, final String connectionString) {
		final StringBuilder query = new StringBuilder();
		query.append("UPDATE TranDescScore as t ");
		query.append("SET t.kenshuSamplingFlag = :TRUE , ");
		query.append("t.updateDate = :DATE ");
		query.append("WHERE t.answerSeq = :ANSWER_SEQ ");

		try {
			return getHibernateTemplate(connectionString).execute(new HibernateCallback<Integer>() {
				public Integer doInHibernate(Session session) throws HibernateException {

					Query queryObj = session.createQuery(query.toString());

					queryObj.setParameter("TRUE", WebAppConst.ENABLE);
					queryObj.setParameter("DATE", date);
					queryObj.setParameter("ANSWER_SEQ", answerSeq);

					return queryObj.executeUpdate();
				}
			});

		} catch (RuntimeException re) {
			throw re;
		}

	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getKenshuRecordsByGrade(final int questionSeq, final String connectionString, final int recordCount,
			final int gradeNum) {
		final StringBuilder query = new StringBuilder();

		query.append("SELECT t.answerSeq, t.answerFormNum, t.imageFileName, ");
		query.append("t.gradeSeq, t.bitValue, t.questionSeq, ");
		query.append("t.updateDate, t.markValue, t.latestScreenScorerId, ");
		query.append("t.secondLatestScreenScorerId, t.gradeNum, ");
		query.append(" t.punchText ");
		query.append("FROM TranDescScore t ");
		query.append("WHERE t.questionSeq = :QUESTION_SEQ ");
		query.append(" AND t.gradeNum = :GRADE_NUM ");
		query.append("AND t.latestScoringState IN (:KESNHU_SAMPLING_STATES) ");
		query.append("AND t.validFlag = :VALID_FLAG ");
		/*
		 * query.append("AND (t.kenshuSamplingFlag IS NULL ");
		 * query.append("OR t.kenshuSamplingFlag = :FALSE) ");
		 */
		query.append("ORDER BY RAND() ");
		try {
			return getHibernateTemplate(connectionString).execute(new HibernateCallback<List>() {
				public List doInHibernate(Session session) throws HibernateException {
					Query queryObj = session.createQuery(query.toString());
					queryObj.setParameter("QUESTION_SEQ", questionSeq);
					queryObj.setParameter("GRADE_NUM", gradeNum);
					queryObj.setParameterList("KESNHU_SAMPLING_STATES", WebAppConst.KENSU_RECORDS_STATES);
					queryObj.setParameter("VALID_FLAG", WebAppConst.VALID_FLAG);
					// queryObj.setParameter("FALSE", WebAppConst.F);
					queryObj.setMaxResults(recordCount);
					return queryObj.list();
				}
			});
		} catch (RuntimeException re) {
			throw re;
		}
	}

	@SuppressWarnings("rawtypes")
	public List getQuesStatewiseStudCountForAllQues(String connectionString) {

		try {
			final StringBuilder queryString = new StringBuilder();
			queryString.append("SELECT ");
			queryString.append(" question_seq,latest_scoring_state,count(*) ");
			queryString.append(" FROM");
			queryString.append(" tran_desc_score t ");
			queryString.append("GROUP BY ");
			queryString.append("question_seq,latest_scoring_state ");
			queryString.append(" ORDER BY ");
			queryString.append("question_seq,latest_scoring_state");

			HibernateTemplate hibernateTemplateReplica = getHibernateTemplateReplica(connectionString);
			if (hibernateTemplateReplica != null) {
				return getHibernateTemplateReplica(connectionString).execute(new HibernateCallback<List>() {
					public List doInHibernate(Session session) throws HibernateException {
						Query queryObj = session.createSQLQuery(queryString.toString());
						return queryObj.list();
					}
				});
			} else {
				return getHibernateTemplate(connectionString).execute(new HibernateCallback<List>() {
					public List doInHibernate(Session session) throws HibernateException {
						Query queryObj = session.createSQLQuery(queryString.toString());
						return queryObj.list();
					}
				});
			}

		} catch (RuntimeException re) {
			throw re;
		}
	}

	@SuppressWarnings("rawtypes")
	public List getQuesWiseStudCntForSpecQues(String connectionString, final List<String> queSeqsInfoList) {

		try {
			final StringBuilder queryString = new StringBuilder();
			queryString.append("SELECT ");
			queryString.append(" question_seq,latest_scoring_state,count(*) ");
			queryString.append("FROM");
			queryString.append(" tran_desc_score t ");
			queryString.append("WHERE ");
			queryString.append("question_seq IN :QUE_SEQ_INFO_LIST ");
			queryString.append(" GROUP BY ");
			queryString.append("question_seq,latest_scoring_state");
			queryString.append(" ORDER BY");
			queryString.append(" question_seq,latest_scoring_state");

			HibernateTemplate hibernateTemplateReplica = getHibernateTemplateReplica(connectionString);
			if (hibernateTemplateReplica != null) {
				return getHibernateTemplateReplica(connectionString).execute(new HibernateCallback<List>() {
					public List doInHibernate(Session session) throws HibernateException {
						Query queryObj = session.createSQLQuery(queryString.toString());
						queryObj.setParameterList("QUE_SEQ_INFO_LIST", queSeqsInfoList);
						return queryObj.list();
					}
				});

			} else {
				return getHibernateTemplate(connectionString).execute(new HibernateCallback<List>() {
					public List doInHibernate(Session session) throws HibernateException {
						Query queryObj = session.createSQLQuery(queryString.toString());
						queryObj.setParameterList("QUE_SEQ_INFO_LIST", queSeqsInfoList);
						return queryObj.list();
					}
				});

			}

		} catch (RuntimeException re) {
			throw re;
		}
	}

	@SuppressWarnings("rawtypes")
	public List getConfirmAndInspectionWaitCount(String dataString, String connectionString) {

		try {
			final StringBuilder queryString = new StringBuilder();
			queryString.append("SELECT ");
			queryString.append(" question_seq,grade_num,count(*) ");
			queryString.append(" FROM");
			queryString.append(" tran_desc_score t ");
			queryString.append("WHERE ");

			if (dataString.equals(WebAppConst.CONFIRM_AND_WAIT_STATE)) {
				queryString.append("latest_scoring_state IN :CONFIRM_AND_INSPECTION_WAIT_STATE ");
			}

			else if (dataString.equals(WebAppConst.NOT_CONFIRM_AND_WAIT_STATE)) {
				queryString.append("latest_scoring_state NOT IN :CONFIRM_AND_INSPECTION_WAIT_STATE ");
			}

			queryString.append(" GROUP BY ");
			queryString.append("question_seq,grade_num");

			HibernateTemplate hibernateTemplateReplica = getHibernateTemplateReplica(connectionString);
			if (hibernateTemplateReplica != null) {
				return getHibernateTemplateReplica(connectionString).execute(new HibernateCallback<List>() {
					public List doInHibernate(Session session) throws HibernateException {
						Query queryObj = session.createSQLQuery(queryString.toString());
						queryObj.setParameterList("CONFIRM_AND_INSPECTION_WAIT_STATE",
								WebAppConst.CONFIRM_AND_INSPECTION_WAIT_STATES);

						System.out.println("queryObj :: " + queryObj);
						return queryObj.list();
					}
				});

			} else {
				return getHibernateTemplate(connectionString).execute(new HibernateCallback<List>() {
					public List doInHibernate(Session session) throws HibernateException {
						Query queryObj = session.createSQLQuery(queryString.toString());
						queryObj.setParameterList("CONFIRM_AND_INSPECTION_WAIT_STATE",
								WebAppConst.CONFIRM_AND_INSPECTION_WAIT_STATES);

						System.out.println("queryObj :: " + queryObj);
						return queryObj.list();
					}
				});
			}

		} catch (RuntimeException re) {
			throw re;
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List questionSeqGradeCountWhereGradeIsAvailable(String connectionString) {

		try {
			final StringBuilder queryString = new StringBuilder();
			queryString.append("SELECT ");
			queryString.append("question_seq,grade_num,count(*) ");
			queryString.append("FROM ");
			queryString.append("tran_desc_score t ");
			queryString.append("WHERE ");
			queryString.append("grade_num is not null ");
			queryString.append("GROUP BY ");
			queryString.append("question_seq,grade_num ");
			queryString.append("ORDER BY ");
			queryString.append("question_seq,grade_num");

			return getHibernateTemplate(connectionString).execute(new HibernateCallback<List>() {
				public List doInHibernate(Session session) throws HibernateException {
					Query queryObj = session.createSQLQuery(queryString.toString());
					return queryObj.list();
				}
			});

		} catch (RuntimeException re) {
			throw re;
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List questionSeqWiseCountWherePendingCategorySet(String connectionString) {

		try {
			final StringBuilder queryString = new StringBuilder();
			queryString.append("SELECT ");
			queryString.append("question_seq,count(*) ");
			queryString.append("FROM ");
			queryString.append("tran_desc_score t ");
			queryString.append("WHERE ");
			queryString.append("pending_category is not null ");
			queryString.append("GROUP BY ");
			queryString.append("question_seq ");
			queryString.append("ORDER BY ");
			queryString.append("question_seq");

			return getHibernateTemplate(connectionString).execute(new HibernateCallback<List>() {
				public List doInHibernate(Session session) throws HibernateException {
					Query queryObj = session.createSQLQuery(queryString.toString());
					return queryObj.list();
				}
			});

		} catch (RuntimeException re) {
			throw re;
		}
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public List findGradesWithCountByQuestionSeq(int questionSeq, Short latestScoringState, Short selectedMarkValue, Short denyCategory, Integer inspectionGroupSeq, String connectionString, String scorerId) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT tranDescScore.grade_num , COUNT(tranDescScore.answer_seq) as answerRecordCount ");
		query.append("FROM tran_desc_score as tranDescScore ");
		query.append("LEFT JOIN (SELECT history.answer_seq from tran_desc_score_history history ");
		query.append("WHERE history.scorer_id = :SCORER_ID ");
		query.append("AND history.question_seq = :QUESTION_SEQ) temp ");
		query.append("ON tranDescScore.answer_seq = temp.answer_seq ");
		query.append("WHERE temp.answer_seq IS NULL ");
		query.append("AND (tranDescScore.latest_screen_scorer_id != :SCORER_ID ");
		query.append("OR tranDescScore.latest_screen_scorer_id IS NULL) ");
		query.append("AND tranDescScore.question_seq = :QUESTION_SEQ ");
		query.append("AND tranDescScore.latest_scoring_state = :LATEST_SCORING_STATE ");
		
		if(selectedMarkValue != null){
			if (selectedMarkValue == WebAppConst.MINUS_ONE) {
				query.append("AND ((tranDescScore.mark_value is null) OR (tranDescScore.mark_value = 0) OR (tranDescScore.mark_value like :COMMASEPERATED)) ");
			} else {
				query.append("AND tranDescScore.mark_value = :MARK_VALUE ");
			}
		}
		
		if(denyCategory != null){
			query.append("AND tranDescScore.deny_category = :DENY_CATEGORY ");
		}
		if(inspectionGroupSeq != null){
			query.append("AND tranDescScore.inspect_group_seq = :INSPECTION_GROUP_SEQ ");
		}
	
		query.append("AND tranDescScore.lock_flag = :UNLOCK ");
		query.append("AND tranDescScore.valid_flag = :VALID_FLAG ");
		query.append("GROUP BY tranDescScore.grade_num ");
		query.append("HAVING COUNT(tranDescScore.answer_seq) > 0 ");
		query.append("ORDER BY tranDescScore.grade_num ");		

		try {			

			return getHibernateTemplate(connectionString).execute(new HibernateCallback<List>() {
				public List doInHibernate(Session session) throws HibernateException {
					SQLQuery queryObj = session.createSQLQuery(query.toString());

					queryObj.setParameter("SCORER_ID", scorerId);
					queryObj.setParameter("QUESTION_SEQ", questionSeq);
					queryObj.setParameter("LATEST_SCORING_STATE",latestScoringState);
					if(selectedMarkValue != null){
						if (selectedMarkValue == WebAppConst.MINUS_ONE) {
							queryObj.setParameter("COMMASEPERATED", WebAppConst.PERCENTAGE_CHARACTER + WebAppConst.COMMA + WebAppConst.PERCENTAGE_CHARACTER);
						} else {
							queryObj.setParameter("MARK_VALUE", selectedMarkValue.toString());
						}
					}
					if(denyCategory != null){
						queryObj.setParameter("DENY_CATEGORY",denyCategory);
					}
					if(inspectionGroupSeq != null){
						queryObj.setParameter("INSPECTION_GROUP_SEQ",inspectionGroupSeq);
					}
				
					queryObj.setParameter("UNLOCK",WebAppConst.UNLOCK);
					queryObj.setParameter("VALID_FLAG",WebAppConst.VALID_FLAG);
					
					return queryObj.list();
				}

			});

		} catch (RuntimeException re) {
			throw re;
		}
	}

}