package com.saiten.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.saiten.dao.TranDescScoreHistoryDAO;
import com.saiten.dao.support.SaitenHibernateDAOSupport;
import com.saiten.info.DailyScoreInfo;
import com.saiten.info.QuestionInfo;
import com.saiten.info.RatingInfo;
import com.saiten.info.ScoreCurrentInfo;
import com.saiten.info.ScoreHistoryInfo;
import com.saiten.info.ScoreInputInfo;
import com.saiten.model.TranDescScoreHistory;
import com.saiten.util.SaitenUtil;
import com.saiten.util.WebAppConst;

/**
 * @author sachin
 * @version 1.0
 * @created 11-Dec-2012 12:56:32 PM
 */
public class TranDescScoreHistoryDAOImpl extends SaitenHibernateDAOSupport
		implements TranDescScoreHistoryDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.saiten.dao.TranDescScoreHistoryDAO#findHistoryRecordCount(java.lang
	 * .String, int, java.lang.String, java.util.List, boolean)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int findHistoryRecordCount(String scorerId,
			List<Integer> questionSeq, String connectionString,
			List<Short> scoringStateList, boolean bookmarkScreenFlag) {
		StringBuilder query = new StringBuilder();
		
		query.append("SELECT COUNT(*) ");
		query.append("FROM TranDescScoreHistory as tranDescScoreHistory ");
		query.append("WHERE tranDescScoreHistory.scorerId = :SCORER_ID ");
		if ((questionSeq != null) && (!questionSeq.isEmpty())) {
			query.append("AND tranDescScoreHistory.questionSeq IN :QUESTION_SEQUENCE ");
		}
		query.append("AND tranDescScoreHistory.scoringState IN :SCORING_STATE_LIST ");
		query.append("AND tranDescScoreHistory.validFlag = :VALID_FLAG ");
		if (bookmarkScreenFlag) {
			query.append("AND tranDescScoreHistory.bookMarkFlag = :BOOKMARK_FLAG_TRUE ");
		} else {
			query.append("AND DATE_FORMAT(tranDescScoreHistory.createDate, '%Y-%m-%d') = ");
			query.append("DATE_FORMAT(:CREATE_DATE, '%Y-%m-%d') ");
			query.append("AND tranDescScoreHistory.tranDescScore.latestScorerId = :SCORER_ID ");
			query.append("AND tranDescScoreHistory.tranDescScore.questionSeq IN :QUESTION_SEQUENCE ");
			query.append("AND tranDescScoreHistory.tranDescScore.latestScoringState IN :SCORING_STATE_LIST ");
			query.append("AND tranDescScoreHistory.tranDescScore.validFlag = :VALID_FLAG ");
			query.append("AND DATE_FORMAT(tranDescScoreHistory.tranDescScore.updateDate, '%Y-%m-%d') = ");
			query.append("DATE_FORMAT(:CREATE_DATE, '%Y-%m-%d') ");
		}

		List<String> paramNameList = new ArrayList<String>();
		List valueList = new ArrayList();

		paramNameList.add(0, "SCORER_ID");
		paramNameList.add(1, "SCORING_STATE_LIST");
		paramNameList.add(2, "VALID_FLAG");
		if ((questionSeq != null) && (!questionSeq.isEmpty())) {
			paramNameList.add("QUESTION_SEQUENCE");
		}
		if (bookmarkScreenFlag) {
			paramNameList.add("BOOKMARK_FLAG_TRUE");
		} else {
			paramNameList.add("CREATE_DATE");
		}

		valueList.add(0, scorerId);
		valueList.add(1, scoringStateList);
		valueList.add(2, WebAppConst.VALID_FLAG);
		if ((questionSeq != null) && (!questionSeq.isEmpty())) {
			valueList.add(questionSeq);
		}
		if (bookmarkScreenFlag) {
			valueList.add(WebAppConst.BOOKMARK_FLAG_TRUE);
		} else {
			valueList.add(new Date());
		}

		String[] paramNames = paramNameList.toArray(new String[paramNameList
				.size()]);
		Object[] values = valueList.toArray(new Object[valueList.size()]);

		try {
			return Integer.valueOf(DataAccessUtils.uniqueResult(
					getHibernateTemplate(connectionString).findByNamedParam(
							query.toString(), paramNames, values)).toString());

		} catch (RuntimeException re) {
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.saiten.dao.TranDescScoreHistoryDAO#findHistoryInfoList(java.lang.
	 * String, java.util.List, int, java.lang.String, java.lang.Integer,
	 * java.lang.Integer, boolean)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List findHistoryInfoList(final String scorerId,
			final List<Short> scoringStateList,
			final List<Integer> questionSequence,
			final String connectionString, final Integer startRecord,
			final Integer endRecord, final boolean bookmarkScreenFlag) {
		final StringBuilder query = new StringBuilder();
		query.append("SELECT tranDescScoreHistory.historySeq, ");
		query.append("tranDescScoreHistory.bookMarkFlag, ");
		query.append("tranDescScoreHistory.answerFormNum, ");
		query.append("tranDescScoreHistory.scoringState, ");
		query.append("tranDescScoreHistory.updateDate, ");
		query.append("tranDescScoreHistory.gradeSeq, ");
		query.append("tranDescScoreHistory.pendingCategorySeq, ");
		query.append("tranDescScoreHistory.scorerComment, ");
		query.append("tranDescScoreHistory.questionSeq, ");
		query.append("tranDescScoreHistory.qualityCheckFlag ");
		query.append("FROM TranDescScoreHistory as tranDescScoreHistory ");
		query.append("WHERE tranDescScoreHistory.scorerId = :SCORER_ID ");
		query.append("AND tranDescScoreHistory.questionSeq IN :QUESTION_SEQUENCE ");
		query.append("AND tranDescScoreHistory.scoringState IN :SCORING_STATE_LIST ");
		query.append("AND tranDescScoreHistory.validFlag = :VALID_FLAG ");
		if (bookmarkScreenFlag) {
			query.append("AND tranDescScoreHistory.bookMarkFlag = :BOOKMARK_FLAG_TRUE ");
		} else {
			query.append("AND DATE_FORMAT(tranDescScoreHistory.createDate, '%Y-%m-%d') = ");
			query.append("DATE_FORMAT(:CREATE_DATE, '%Y-%m-%d') ");
			query.append("AND tranDescScoreHistory.tranDescScore.latestScorerId = :SCORER_ID ");
			query.append("AND tranDescScoreHistory.tranDescScore.questionSeq IN :QUESTION_SEQUENCE ");
			query.append("AND tranDescScoreHistory.tranDescScore.latestScoringState IN :SCORING_STATE_LIST ");
			query.append("AND tranDescScoreHistory.tranDescScore.validFlag = :VALID_FLAG ");
			query.append("AND DATE_FORMAT(tranDescScoreHistory.tranDescScore.updateDate, '%Y-%m-%d') = ");
			query.append("DATE_FORMAT(:CREATE_DATE, '%Y-%m-%d') ");
		}
		query.append("ORDER BY tranDescScoreHistory.updateDate DESC ");

		try {

			return getHibernateTemplate(connectionString).execute(
					new HibernateCallback<List>() {
						public List doInHibernate(Session session)
								throws HibernateException {
							Query queryObj = session.createQuery(query
									.toString());

							queryObj.setParameter("SCORER_ID", scorerId);
							queryObj.setParameterList("QUESTION_SEQUENCE",
									questionSequence);
							queryObj.setParameterList("SCORING_STATE_LIST",
									scoringStateList);
							queryObj.setParameter("VALID_FLAG",
									WebAppConst.VALID_FLAG);
							if (bookmarkScreenFlag) {
								queryObj.setParameter("BOOKMARK_FLAG_TRUE",
										WebAppConst.BOOKMARK_FLAG_TRUE);
							} else {
								queryObj.setParameter("CREATE_DATE", new Date());
							}

							if (endRecord != null) {
								queryObj.setFirstResult(startRecord);
								queryObj.setMaxResults(endRecord);
							}
							return queryObj.list();
						}
					});

		} catch (RuntimeException re) {
			throw re;
		} 
	}

	@SuppressWarnings("rawtypes")
	public List findQcAndHistoryInfoList(final String scorerId,
			final List<Short> scoringStateList,
			final List<Integer> questionSequence,
			final String connectionString, final Integer startRecord,
			final Integer endRecord, final boolean bookmarkScreenFlag) {
		final StringBuilder query = new StringBuilder();
		query.append("SELECT unr.seq,unr.flag,unr.answer_form_num, ");
		query.append("unr.scoring_state,unr.update_date, ");
		query.append("unr.grade_seq,unr.pending_category_seq, ");
		query.append("unr.scorer_comment,unr.question_seq,unr.tbl from ");
		query.append("(SELECT tran_history.history_seq As seq, tran_history.book_mark_flag As flag, tran_history.answer_form_num, ");
		query.append("tran_history.scoring_state, tran_history.update_date, tran_history.grade_seq, ");
		query.append("tran_history.pending_category_seq, tran_history.scorer_comment, ");
		query.append("tran_history.question_seq, 0 as tbl ");
		query.append("FROM tran_desc_score_history as tran_history ");
		query.append("JOIN tran_desc_score as tran ");
		query.append("WHERE tran_history.answer_seq = tran.answer_seq ");
		query.append("AND tran_history.scorer_id = :SCORER_ID ");
		query.append("AND tran_history.question_seq IN :QUESTION_SEQUENCE ");
		query.append("AND tran_history.scoring_state IN :SCORING_STATE_LIST ");
		query.append("AND tran_history.valid_flag = :VALID_FLAG AND DATE_FORMAT(tran_history.create_date, '%Y-%m-%d') = DATE_FORMAT(:CREATE_DATE, '%Y-%m-%d') ");
		query.append("AND tran.latest_scorer_id = :SCORER_ID ");
		query.append("AND tran.question_seq IN :QUESTION_SEQUENCE ");
		query.append("AND tran.latest_scoring_state IN :SCORING_STATE_LIST ");
		query.append("AND tran.valid_flag = :VALID_FLAG ");
		query.append("AND DATE_FORMAT(tran.update_date, '%Y-%m-%d') = ");
		query.append("DATE_FORMAT(:CREATE_DATE, '%Y-%m-%d') ");
		query.append("UNION ");
		query.append("SELECT qc_seq As seq, ref_flag As flag, answer_form_num, ");
		query.append("scoring_state, update_date, grade_seq, ");
		query.append("pending_category_seq, scorer_comment, ");
		query.append("question_seq, 1 as tbl ");
		query.append("FROM tran_qualitycheck_score ");
		query.append("WHERE scorer_id = :SCORER_ID ");
		query.append("AND question_seq IN :QUESTION_SEQUENCE ");
		query.append("AND scoring_state IN :SCORING_STATE_LIST ");
		query.append("AND valid_flag = :VALID_FLAG ");
		query.append("AND DATE_FORMAT(create_date, '%Y-%m-%d') = DATE_FORMAT(:CREATE_DATE, '%Y-%m-%d'))  As unr ");

		/*
		 * if (bookmarkScreenFlag) {
		 * query.append("AND book_mark_flag = :BOOKMARK_FLAG_TRUE "); } else {
		 * query.append("AND DATE_FORMAT(create_date, '%Y-%m-%d') = ");
		 * query.append("DATE_FORMAT(NOW(), '%Y-%m-%d') "); }
		 */
		query.append("ORDER BY update_date DESC ");

		try {

			return getHibernateTemplate(connectionString).execute(
					new HibernateCallback<List>() {
						public List doInHibernate(Session session)
								throws HibernateException {
							SQLQuery queryObj = session.createSQLQuery(query
									.toString());

							queryObj.setParameter("SCORER_ID", scorerId);
							queryObj.setParameterList("QUESTION_SEQUENCE",
									questionSequence);
							queryObj.setParameterList("SCORING_STATE_LIST",
									scoringStateList);
							queryObj.setParameter("VALID_FLAG",
									WebAppConst.VALID_FLAG);
							queryObj.setParameter("CREATE_DATE", new Date());
							/*
							 * if (bookmarkScreenFlag) {
							 * queryObj.setParameter("BOOKMARK_FLAG_TRUE",
							 * WebAppConst.BOOKMARK_FLAG_TRUE); }
							 */

							if (endRecord != null) {
								queryObj.setFirstResult(startRecord);
								queryObj.setMaxResults(endRecord);
							}
							return queryObj.list();
						}
					});

		} catch (RuntimeException re) {
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saiten.dao.TranDescScoreHistoryDAO#findById(java.lang.Integer,
	 * java.lang.String)
	 */
	@Override
	public TranDescScoreHistory findById(Integer historySeq,
			String connectionString) {
		try {
			return getHibernateTemplate(connectionString).get(
					TranDescScoreHistory.class, historySeq);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.saiten.dao.TranDescScoreHistoryDAO#findHistoryAnswer(java.lang.Integer
	 * , java.lang.String)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List findHistoryAnswer(Integer historySeq, String connectionString) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT tranDescScoreHistory.tranDescScore.answerSeq, tranDescScoreHistory.answerFormNum, ");
		query.append("tranDescScoreHistory.tranDescScore.imageFileName, ");
		query.append("tranDescScoreHistory.bookMarkFlag, tranDescScoreHistory.scorerComment, ");
		query.append("tranDescScoreHistory.gradeSeq, tranDescScoreHistory.bitValue, ");
		query.append("tranDescScoreHistory.updateDate, tranDescScoreHistory.pendingCategorySeq, ");
		query.append("tranDescScoreHistory.scoringState, tranDescScoreHistory.questionSeq, ");
		query.append("tranDescScoreHistory.qualityCheckFlag  ");
		query.append("FROM TranDescScoreHistory as tranDescScoreHistory ");
		query.append("WHERE tranDescScoreHistory.historySeq = :HISTORY_SEQUENCE ");
		query.append("AND tranDescScoreHistory.validFlag = :VALID_FLAG ");
		query.append("AND tranDescScoreHistory.tranDescScore.lockFlag = :UNLOCK ");
		query.append("AND tranDescScoreHistory.tranDescScore.validFlag = :VALID_FLAG ");

		String[] paramNames = { "HISTORY_SEQUENCE", "VALID_FLAG", "UNLOCK" };
		Object[] values = { historySeq, WebAppConst.VALID_FLAG,
				WebAppConst.UNLOCK };
		try {
			HibernateTemplate hibernateTemplate = getHibernateTemplate(connectionString);

			return hibernateTemplate.findByNamedParam(query.toString(),
					paramNames, values);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saiten.dao.TranDescScoreHistoryDAO#save(com.saiten.model.
	 * TranDescScoreHistory, java.lang.String)
	 */
	@Override
	public void save(TranDescScoreHistory tranDescScoreHistory,
			String connectionString) {
		try {
			getHibernateTemplate(connectionString).save(tranDescScoreHistory);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.saiten.dao.TranDescScoreHistoryDAO#deleteBookmarks(java.util.List,
	 * java.lang.String)
	 */
	@Override
	public int deleteBookmarks(final List<Integer> historySequenceList,
			final String connectionString) {
		final StringBuilder query = new StringBuilder();
		query.append("UPDATE TranDescScoreHistory ");
		query.append("SET bookMarkFlag= :BOOKMARK_FLAG_FALSE ");
		query.append(", updateDate= :CREATE_DATE ");
		query.append("WHERE historySeq IN :HISTORY_SEQUENCE_LIST ");

		try {
			return getHibernateTemplate(connectionString).execute(
					new HibernateCallback<Integer>() {
						public Integer doInHibernate(Session session)
								throws HibernateException {

							Query queryObj = session.createQuery(query
									.toString());

							queryObj.setParameter("BOOKMARK_FLAG_FALSE",
									WebAppConst.BOOKMARK_FLAG_FALSE);
							queryObj.setParameterList("HISTORY_SEQUENCE_LIST",
									historySequenceList);
							queryObj.setParameter("CREATE_DATE", new Date());
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
	 * @see
	 * com.saiten.dao.TranDescScoreHistoryDAO#findPrevOrNextHistoryAnswer(int,
	 * java.lang.String, java.lang.String, java.lang.String, java.util.List,
	 * java.util.Date, boolean)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List findPrevOrNextHistoryAnswer(final int quetionSeq, final String menuId,
			final String scorerId, final String connectionString,
			final List<Short> scoringStateList, final Date updateDate, final boolean isPrevious) {

		//HibernateTemplate hibernateTemplate = null;

		final StringBuilder query = new StringBuilder();
		query.append("SELECT tranDescScoreHistory.tranDescScore.answerSeq, tranDescScoreHistory.answerFormNum, ");
		query.append("tranDescScoreHistory.tranDescScore.imageFileName, ");
		query.append("tranDescScoreHistory.bookMarkFlag, tranDescScoreHistory.scorerComment, ");
		query.append("tranDescScoreHistory.gradeSeq, tranDescScoreHistory.bitValue, ");
		query.append("tranDescScoreHistory.updateDate, tranDescScoreHistory.pendingCategorySeq, ");
		query.append("tranDescScoreHistory.scoringState, tranDescScoreHistory.questionSeq, ");
		query.append("tranDescScoreHistory.qualityCheckFlag, tranDescScoreHistory.historySeq, 0 as tb1  ");
		query.append("FROM TranDescScoreHistory as tranDescScoreHistory ");
		query.append("WHERE tranDescScoreHistory.questionSeq = :QUESTION_SEQUENCE ");
		query.append("AND tranDescScoreHistory.validFlag = :VALID_FLAG ");
		query.append("AND tranDescScoreHistory.scorerId = :SCORER_ID ");
		query.append("AND tranDescScoreHistory.scoringState IN :SCORING_STATE_LIST ");
		query.append("AND DATE_FORMAT(tranDescScoreHistory.createDate, '%Y-%m-%d') = ");
		query.append("DATE_FORMAT(:CREATE_DATE, '%Y-%m-%d') ");
		query.append("AND tranDescScoreHistory.tranDescScore.latestScorerId = :SCORER_ID ");
		query.append("AND tranDescScoreHistory.tranDescScore.questionSeq = :QUESTION_SEQUENCE ");
		query.append("AND tranDescScoreHistory.tranDescScore.latestScoringState IN :SCORING_STATE_LIST ");
		query.append("AND tranDescScoreHistory.tranDescScore.validFlag = :VALID_FLAG ");
		query.append("AND DATE_FORMAT(tranDescScoreHistory.tranDescScore.updateDate, '%Y-%m-%d') = ");
		query.append("DATE_FORMAT(:CREATE_DATE, '%Y-%m-%d') ");
		if (isPrevious) {
			if (updateDate != null) {
				query.append("AND tranDescScoreHistory.updateDate < :LATEST_UPDATE_DATE ");
			}
			query.append("ORDER BY tranDescScoreHistory.updateDate DESC ");
		} else {
			if (updateDate != null) {
				query.append("AND tranDescScoreHistory.updateDate > :LATEST_UPDATE_DATE ");
			}
			query.append("ORDER BY tranDescScoreHistory.updateDate ASC ");
		}

/*		List<String> paramNames = new ArrayList<String>();
		paramNames.add("QUESTION_SEQUENCE");
		paramNames.add("VALID_FLAG");
		paramNames.add("SCORER_ID");
		paramNames.add("SCORING_STATE_LIST");
		paramNames.add("CREATE_DATE");

		List<Object> values = new ArrayList<Object>();
		values.add(quetionSeq);
		values.add(WebAppConst.VALID_FLAG);
		values.add(scorerId);
		values.add(scoringStateList);
		values.add(new Date());
		if (updateDate != null) {
			paramNames.add("LATEST_UPDATE_DATE");
			values.add(updateDate);
		}*/

		try {
			/*hibernateTemplate = getHibernateTemplate(connectionString);
			hibernateTemplate
					.setMaxResults(WebAppConst.ANSWER_RECORD_FETCH_SIZE);

			return hibernateTemplate.findByNamedParam(query.toString(),
					paramNames.toArray(new String[paramNames.size()]),
					values.toArray());*/
			
			return getHibernateTemplate(connectionString).execute(
					new HibernateCallback<List>() {
						public List doInHibernate(Session session)
								throws HibernateException {
							Query queryObj = session.createQuery(query
									.toString());

							queryObj.setParameter("SCORER_ID", scorerId);
							queryObj.setParameter("VALID_FLAG", WebAppConst.VALID_FLAG);
							queryObj.setParameterList("SCORING_STATE_LIST", scoringStateList);
							queryObj.setParameter("QUESTION_SEQUENCE", quetionSeq);
							queryObj.setParameter("CREATE_DATE", new Date());
						
							if (updateDate != null) {
								queryObj.setParameter("LATEST_UPDATE_DATE", updateDate);
							}
							queryObj.setMaxResults(WebAppConst.ANSWER_RECORD_FETCH_SIZE);
							return queryObj.list();
						}
					});
		} catch (RuntimeException re) {
			throw re;
		} /*finally {
			if (hibernateTemplate != null) {
				hibernateTemplate.setMaxResults(WebAppConst.ZERO);
			}
		}*/
	}

	@SuppressWarnings("rawtypes")
	public List findPrevOrNextHistoryAndQcAnswer(final int quetionSeq,
			final String menuId, final String scorerId,
			final String connectionString, final List<Short> scoringStateList,
			final Date updateDate, final boolean isPrevious) {

		// HibernateTemplate hibernateTemplate = null;

		final StringBuilder query = new StringBuilder();
		query.append("SELECT unr.seq,unr.answer_form_num,unr.image_file_name,unr.flag, ");
		query.append("unr.scorer_comment,unr.grade_seq,unr.bit_value,unr.update_date, ");
		query.append("unr.pending_category_seq,unr.scoring_state,unr.question_seq, ");
		query.append("unr.flag1,unr.seq1, tbl FROM ");
		query.append("(SELECT th.answer_seq As seq,th.answer_form_num, ");
		query.append("ts.image_file_name,th.book_mark_flag As flag, ");
		query.append("th.scorer_comment,th.grade_seq,th.bit_value, ");
		query.append("th.update_date,th.pending_category_seq,");
		query.append("th.scoring_state,th.question_seq,th.quality_check_flag As flag1, ");
		query.append("th.history_seq As seq1,0 As tbl ");
		query.append("FROM tran_desc_score_history th join tran_desc_score ts ");
		query.append("ON th.answer_seq = ts.answer_seq ");
		query.append("WHERE th.scorer_id = :SCORER_ID AND th.question_seq = :QUESTION_SEQUENCE ");
		query.append("AND th.scoring_state IN :SCORING_STATE_LIST ");
		query.append("AND th.valid_flag = :VALID_FLAG  AND DATE(th.create_date)=date(:CREATE_DATE)");
		query.append("AND ts.latest_scorer_id = :SCORER_ID ");
		query.append("AND ts.question_seq = :QUESTION_SEQUENCE ");
		query.append("AND ts.latest_scoring_state IN :SCORING_STATE_LIST ");
		query.append("AND ts.valid_flag = :VALID_FLAG ");
		query.append("AND DATE_FORMAT(ts.update_date, '%Y-%m-%d') = ");
		query.append("DATE_FORMAT(:CREATE_DATE, '%Y-%m-%d') ");
		query.append(" UNION ");
		query.append("SELECT tq.answer_seq As seq,tq.answer_form_num, ");
		query.append("ts.image_file_name,tq.ref_flag,tq.scorer_comment, ");
		query.append("tq.grade_seq,tq.bit_value,tq.create_date,tq.pending_category_seq, "); // Change made to bring create date instead of update date from tran_qualitycheck table
		query.append("tq.scoring_state,tq.question_seq,tq.ref_flag As flag1,tq.qc_seq As seq1,1 As tbl ");
		query.append("FROM tran_qualitycheck_score tq  join tran_desc_score ts ");
		query.append("ON tq.answer_seq = ts.answer_seq ");
		query.append("WHERE tq.scorer_id = :SCORER_ID AND tq.question_seq = :QUESTION_SEQUENCE ");
		query.append("AND tq.scoring_state IN :SCORING_STATE_LIST AND tq.valid_flag = :VALID_FLAG ");
		query.append("AND DATE(tq.create_date)=date(:CREATE_DATE ) ) As unr ");
		if (isPrevious) {
			if (updateDate != null) {
				query.append("WHERE unr.update_date < :LATEST_UPDATE_DATE ");
			}
			query.append("ORDER BY unr.update_date DESC limit 1 ");
		} else {
			if (updateDate != null) {
				query.append("WHERE unr.update_date > :LATEST_UPDATE_DATE ");
			}
			query.append("ORDER BY unr.update_date ASC limit 1 ");
		}

		try {

			return getHibernateTemplate(connectionString).execute(
					new HibernateCallback<List>() {
						public List doInHibernate(Session session)
								throws HibernateException {
							SQLQuery queryObj = session.createSQLQuery(query
									.toString());

							queryObj.setParameter("SCORER_ID", scorerId);
							queryObj.setParameter("QUESTION_SEQUENCE",
									quetionSeq);
							queryObj.setParameterList("SCORING_STATE_LIST",
									scoringStateList);
							queryObj.setParameter("VALID_FLAG",
									WebAppConst.VALID_FLAG);
							queryObj.setParameter("CREATE_DATE", new Date());
							if (updateDate != null) {
								queryObj.setParameter("LATEST_UPDATE_DATE",
										updateDate);
							}

							return queryObj.list();
						}
					});

		} catch (RuntimeException re) {
			throw re;
		}
	}

	// original query: order by rand()----> answer_seq
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.saiten.dao.TranDescScoreHistoryDAO#searchAnswerRecords(com.saiten
	 * .info.QuestionInfo, com.saiten.info.ScoreInputInfo)
	 */
	/*
	 * @SuppressWarnings("rawtypes")
	 * 
	 * @Override public List searchAnswerRecords(final QuestionInfo
	 * questionInfo, final ScoreInputInfo scoreInputInfo, final Boolean
	 * forceAndStateTransitionFlag, final Integer startRecord, final Integer
	 * endRecord) {
	 * 
	 * final ScoreHistoryInfo scoreHistoryInfo = scoreInputInfo
	 * .getScoreHistoryInfo();
	 * 
	 * final String menuId = questionInfo.getMenuId(); final boolean
	 * samplingFlag = scoreInputInfo.isSamplingFlag(); final String
	 * answerFormNum = scoreInputInfo.getAnswerFormNum();
	 * 
	 * final List<String> historyScorerIdList = scoreHistoryInfo != null ?
	 * scoreHistoryInfo .getHistoryScorerIdList() : null; final Integer
	 * historyCategoryType = scoreHistoryInfo != null ? scoreHistoryInfo
	 * .getHistoryCategoryType() : null; final List historyGradeSeqList =
	 * scoreHistoryInfo != null ? scoreHistoryInfo .getHistoryGradeSeqList() :
	 * null; final List historyPendingCategorySeqList = scoreHistoryInfo != null
	 * ? scoreHistoryInfo .getHistoryPendingCategorySeqList() : null; final
	 * String historyIncludeCheckPoints = scoreHistoryInfo != null ?
	 * scoreHistoryInfo .getHistoryIncludeCheckPoints() : null; final String
	 * historyExcludeCheckPoints = scoreHistoryInfo != null ? scoreHistoryInfo
	 * .getHistoryExcludeCheckPoints() : null; final Short[] historyEventList =
	 * scoreHistoryInfo != null ? scoreHistoryInfo .getHistoryEventList() :
	 * null; final Date historyUpdateStartDate = scoreHistoryInfo != null ?
	 * scoreHistoryInfo .getHistoryUpdateStartDate() : null; final Date
	 * historyUpdateEndDate = scoreHistoryInfo != null ? scoreHistoryInfo
	 * .getHistoryUpdateEndDate() : null;
	 * 
	 * final ScoreCurrentInfo scoreCurrentInfo = scoreInputInfo
	 * .getScoreCurrentInfo();
	 * 
	 * final List<String> currentScorerIdList = scoreCurrentInfo != null ?
	 * scoreCurrentInfo .getCurrentScorerIdList() : null; final Integer
	 * currentCategoryType = scoreCurrentInfo != null ? scoreCurrentInfo
	 * .getCurrentCategoryType() : null; final List currentGradeSeqList =
	 * scoreCurrentInfo != null ? scoreCurrentInfo .getCurrentGradeSeqList() :
	 * null; final List currentPendingCategorySeqList = scoreCurrentInfo != null
	 * ? scoreCurrentInfo .getCurrentPendingCategorySeqList() : null; final
	 * String currentIncludeCheckPoints = scoreCurrentInfo != null ?
	 * scoreCurrentInfo .getCurrentIncludeCheckPoints() : null; final String
	 * currentExcludeCheckPoints = scoreCurrentInfo != null ? scoreCurrentInfo
	 * .getCurrentExcludeCheckPoints() : null; final Short[] currentStateList =
	 * scoreCurrentInfo != null ? scoreCurrentInfo .getCurrentStateList() :
	 * null; final Date currentUpdateStartDate = scoreCurrentInfo != null ?
	 * scoreCurrentInfo .getCurrentUpdateStartDate() : null; final Date
	 * currentUpdateEndDate = scoreCurrentInfo != null ? scoreCurrentInfo
	 * .getCurrentUpdateEndDate() : null;
	 * System.out.println(">>>>>>>>>>>>>> searchAnswerRecords start: "+new
	 * Date().getTime()); try { List answerRecordsList = getHibernateTemplate(
	 * questionInfo.getConnectionString()).execute( new
	 * HibernateCallback<List>() {
	 * 
	 * public List doInHibernate(Session session) throws HibernateException {
	 * StringBuilder query = new StringBuilder(); if
	 * ((forceAndStateTransitionFlag == null) || (forceAndStateTransitionFlag ==
	 * WebAppConst.TRUE)) { query.append(
	 * "SELECT t.answer_seq AS answerSeq, t.answer_form_num AS answerFormNum, "
	 * );
	 * query.append("t.image_file_name AS imageFileName, t.grade_seq AS gradeSeq, "
	 * ); query.append(
	 * "t.bit_value AS bitValue, t.latest_scorer_id AS latestScorerId, "); if
	 * (!(menuId .equals(WebAppConst.STATE_TRAN_MENU_ID) || menuId
	 * .equals(WebAppConst.FORCED_MENU_ID))) { query.append(
	 * "(SELECT scorer_comment FROM tran_desc_score_history AS scoreHistory ");
	 * query.append("WHERE scoreHistory.answer_seq = t.answer_seq ");
	 * query.append("AND scoreHistory.update_date = "); query.append(
	 * "(SELECT max(history.update_date) FROM tran_desc_score_history history "
	 * ); query.append(
	 * "WHERE history.answer_seq = scoreHistory.answer_seq)) AS scorerComment, "
	 * ); } query.append("t.punch_text AS punchText, "); query.append(
	 * "t.pending_category_seq AS pendingCategorySeq, t.latest_scoring_state, "
	 * ); if (((forceAndStateTransitionFlag != null) &&
	 * (forceAndStateTransitionFlag == WebAppConst.TRUE)) || (menuId
	 * .equals(WebAppConst.REFERENCE_SAMP_MENU_ID) &&
	 * (forceAndStateTransitionFlag == null))) { query.append(
	 * "t.question_seq AS questionSeq, t.latest_screen_scorer_id AS latestScreenScorerId, "
	 * ); } if (menuId.equals(WebAppConst.FORCED_MENU_ID) &&
	 * (forceAndStateTransitionFlag != null && forceAndStateTransitionFlag ==
	 * WebAppConst.TRUE)) {
	 * query.append("(SELECT count(*) FROM tran_desc_score_history AS scoreHistory "
	 * ); query.append("WHERE scoreHistory.answer_seq = t.answer_seq ");
	 * query.append("AND scoreHistory.question_seq = :QUESTION_SEQ ");
	 * query.append(
	 * "AND scoreHistory.scorer_comment != '' AND scoreHistory.scorer_comment is not null ) AS commentCount, "
	 * ); } query.append("t.update_date AS updateDate ");
	 * 
	 * query.append("FROM  tran_desc_score AS t, ( ");
	 * query.append("SELECT tranDescScore.answer_seq AS answerSeq "); } else {
	 * query.append("SELECT SUM(rowCount) "); query.append(
	 * "FROM (SELECT count(distinct tranDescScore.answer_seq) as rowCount "); }
	 * query.append("FROM tran_desc_score AS tranDescScore ");
	 * 
	 * query.append("INNER JOIN ");
	 * query.append("tran_desc_score_history AS tranDescScoreHistory ");
	 * query.append
	 * ("ON tranDescScore.answer_seq = tranDescScoreHistory.answer_seq ");
	 * query.append("WHERE tranDescScore.question_seq  = :QUESTION_SEQ ");
	 * query.append("AND tranDescScoreHistory.question_seq  = :QUESTION_SEQ ");
	 * query.append("AND tranDescScore.lock_flag = :UNLOCK ");
	 * 
	 * if (menuId.equals(WebAppConst.STATE_TRAN_MENU_ID)) {
	 * query.append("AND tranDescScore.inspect_flag = :INSPECT_FLAG "); }
	 * 
	 * if (menuId.equals(WebAppConst.SCORE_SAMP_MENU_ID)) {
	 * query.append("AND tranDescScore.sampling_flag IN :SAMPLING_FLAG_LIST ");
	 * }
	 * 
	 * if (!StringUtils.isBlank(answerFormNum)) {
	 * query.append("AND tranDescScore.answer_form_num = :ANSWER_FORM_NUM "); }
	 * 
	 * if (scoreHistoryInfo != null && scoreHistoryInfo.isHistoryBlock()) {
	 * 
	 * if (!historyScorerIdList.isEmpty()) { query.append(
	 * "AND tranDescScoreHistory.latest_screen_scorer_id IN :HISTORY_SCORER_ID_LIST "
	 * ); }
	 * 
	 * if (historyCategoryType != null && historyCategoryType == 2) {
	 * query.append(
	 * "AND tranDescScoreHistory.grade_seq IS null AND tranDescScoreHistory.pending_category_seq IS null "
	 * ); } else if (historyCategoryType != null && historyCategoryType == 3) {
	 * 
	 * if (!historyGradeSeqList.isEmpty() && ArrayUtils.contains(
	 * scoreHistoryInfo .getHistoryGradeNum(), -1)) { query.append(
	 * "AND (tranDescScoreHistory.grade_seq IN :HISTORY_GRADE_SEQ_LIST ");
	 * query.
	 * append("OR tranDescScoreHistory.scoring_state = :NO_GRADE_SCORING_STATE) "
	 * ); } else { if (!historyGradeSeqList.isEmpty()) {
	 * query.append("AND tranDescScoreHistory.grade_seq IN :HISTORY_GRADE_SEQ_LIST "
	 * ); }
	 * 
	 * if (ArrayUtils.contains( scoreHistoryInfo .getHistoryGradeNum(), -1)) {
	 * query
	 * .append("AND tranDescScoreHistory.scoring_state = :NO_GRADE_SCORING_STATE "
	 * ); } } } else if (historyCategoryType != null && historyCategoryType == 4
	 * && !historyPendingCategorySeqList .isEmpty()) { query.append(
	 * "AND tranDescScoreHistory.pending_category_seq IN :HISTORY_PENDING_CATEGORY_SEQ_LIST "
	 * ); }
	 * 
	 * if (!StringUtils .isBlank(historyIncludeCheckPoints) && !StringUtils
	 * .isBlank(historyExcludeCheckPoints)) { query.append(
	 * "AND (tranDescScoreHistory.bit_value & :HISTORY_INCLUDE_BIT_VALUE) > 0 "
	 * ); query.append(
	 * "AND (tranDescScoreHistory.bit_value & :HISTORY_EXCLUDE_BIT_VALUE) = 0 "
	 * ); } else { if (!StringUtils .isBlank(historyIncludeCheckPoints)) {
	 * query.append(
	 * "AND (tranDescScoreHistory.bit_value & :HISTORY_INCLUDE_BIT_VALUE) > 0 "
	 * ); } else if (!StringUtils .isBlank(historyExcludeCheckPoints)) {
	 * query.append
	 * ("AND (tranDescScoreHistory.bit_value & :HISTORY_EXCLUDE_BIT_VALUE) = 0 "
	 * ); } }
	 * 
	 * if (historyEventList != null && historyEventList.length > 0) {
	 * query.append
	 * ("AND tranDescScoreHistory.event_id IN :HISTORY_EVENT_LIST "); }
	 * 
	 * if (historyUpdateStartDate != null) { query.append(
	 * "AND tranDescScoreHistory.update_date >= :HISTORY_UPDATE_START_DATE "); }
	 * 
	 * if (historyUpdateEndDate != null) { query.append(
	 * "AND tranDescScoreHistory.update_date <= :HISTORY_UPDATE_END_DATE "); } }
	 * 
	 * if (scoreCurrentInfo != null && scoreCurrentInfo.isCurrentBlock()) {
	 * 
	 * if (!currentScorerIdList.isEmpty()) { query.append(
	 * "AND tranDescScore.latest_screen_scorer_id IN :CURRENT_SCORER_ID_LIST ");
	 * }
	 * 
	 * if (currentCategoryType != null && currentCategoryType == 2) {
	 * query.append(
	 * "AND tranDescScore.grade_seq IS null AND tranDescScore.pending_category_seq IS null "
	 * ); } else if (currentCategoryType != null && currentCategoryType == 3) {
	 * 
	 * if (!currentGradeSeqList.isEmpty() && ArrayUtils.contains(
	 * scoreCurrentInfo .getCurrentGradeNum(), -1)) {
	 * query.append("AND (tranDescScore.grade_seq IN :CURRENT_GRADE_SEQ_LIST ");
	 * query
	 * .append("OR tranDescScore.latest_scoring_state = :NO_GRADE_SCORING_STATE) "
	 * ); } else { if (!currentGradeSeqList.isEmpty()) {
	 * query.append("AND tranDescScore.grade_seq IN :CURRENT_GRADE_SEQ_LIST ");
	 * }
	 * 
	 * if (ArrayUtils.contains( scoreCurrentInfo .getCurrentGradeNum(), -1)) {
	 * query
	 * .append("AND tranDescScore.latest_scoring_state = :NO_GRADE_SCORING_STATE "
	 * ); } } } else if (currentCategoryType != null && currentCategoryType == 4
	 * && !currentPendingCategorySeqList .isEmpty()) { query.append(
	 * "AND tranDescScore.pending_category_seq IN :CURRENT_PENDING_CATEGORY_SEQ_LIST "
	 * ); }
	 * 
	 * if (!StringUtils .isBlank(currentIncludeCheckPoints) && !StringUtils
	 * .isBlank(currentExcludeCheckPoints)) { query.append(
	 * "AND (tranDescScore.bit_value & :CURRENT_INCLUDE_BIT_VALUE) > 0 ");
	 * query.
	 * append("AND (tranDescScore.bit_value & :CURRENT_EXCLUDE_BIT_VALUE) = 0 "
	 * ); } else { if (!StringUtils .isBlank(currentIncludeCheckPoints)) {
	 * query.
	 * append("AND (tranDescScore.bit_value & :CURRENT_INCLUDE_BIT_VALUE) > 0 "
	 * ); } else if (!StringUtils .isBlank(currentExcludeCheckPoints)) {
	 * query.append
	 * ("AND (tranDescScore.bit_value & :CURRENT_EXCLUDE_BIT_VALUE) = 0 "); } }
	 * 
	 * if (currentStateList != null && currentStateList.length > 0) {
	 * query.append
	 * ("AND tranDescScore.latest_scoring_state IN :CURRENT_STATE_LIST "); }
	 * 
	 * if (currentUpdateStartDate != null) {
	 * query.append("AND tranDescScore.update_date >= :CURRENT_UPDATE_START_DATE "
	 * ); }
	 * 
	 * if (currentUpdateEndDate != null) {
	 * query.append("AND tranDescScore.update_date <= :CURRENT_UPDATE_END_DATE "
	 * ); } } else if (menuId .equals(WebAppConst.SCORE_SAMP_MENU_ID) || menuId
	 * .equals(WebAppConst.STATE_TRAN_MENU_ID) || menuId
	 * .equals(WebAppConst.FORCED_MENU_ID)) {
	 * query.append("AND tranDescScore.latest_scoring_state IN :CURRENT_STATE_LIST "
	 * ); }
	 * 
	 * if ((scoreHistoryInfo == null || !scoreHistoryInfo .isHistoryBlock()) &&
	 * (scoreCurrentInfo == null || !scoreCurrentInfo .isCurrentBlock())) { if
	 * (menuId .equals(WebAppConst.REFERENCE_SAMP_MENU_ID)) { query.append(
	 * "AND tranDescScore.latest_scoring_state NOT IN :SPECIAL_SCORING_WAIT_STATES "
	 * ); } else if (menuId .equals(WebAppConst.SCORE_SAMP_MENU_ID)) {
	 * query.append
	 * ("AND tranDescScore.latest_scoring_state NOT IN :SPECIAL_SCORING_STATES "
	 * ); }
	 * 
	 * } if (!(menuId.equals(WebAppConst.STATE_TRAN_MENU_ID) || menuId
	 * .equals(WebAppConst.FORCED_MENU_ID))) { query.append(
	 * "AND tranDescScoreHistory.scoring_state NOT IN :DUMMY_SCORING_STATES ");
	 * query.append(
	 * "AND tranDescScore.latest_scoring_state NOT IN :DUMMY_SCORING_STATES ");
	 * } query.append("AND tranDescScore.valid_flag = :VALID_FLAG ");
	 * query.append("AND tranDescScoreHistory.valid_flag = :VALID_FLAG ");
	 * query.append("GROUP BY tranDescScore.answer_seq "); if
	 * ((forceAndStateTransitionFlag != null) && (forceAndStateTransitionFlag ==
	 * WebAppConst.TRUE) && (menuId .equals(WebAppConst.FORCED_MENU_ID))) {
	 * query.append("ORDER BY tranDescScore.update_date DESC ");
	 * query.append("LIMIT "); if (endRecord != null) { if (startRecord > 0) {
	 * query.append(startRecord + " , " + endRecord); } else {
	 * query.append(endRecord + " "); } } } if
	 * (!(menuId.equals(WebAppConst.STATE_TRAN_MENU_ID) || menuId
	 * .equals(WebAppConst.FORCED_MENU_ID))) { query.append("ORDER BY RAND() ");
	 * query.append("LIMIT :RECORD_COUNT_LIMIT "); query.append(") score ");
	 * query.append("WHERE t.answer_seq = score.answerSeq ");
	 * query.append("ORDER BY t.update_date DESC "); } else if
	 * ((forceAndStateTransitionFlag != null) && (forceAndStateTransitionFlag ==
	 * WebAppConst.TRUE)) { query.append(") score ");
	 * query.append("WHERE t.answer_seq = score.answerSeq "); if
	 * (!menuId.equals(WebAppConst.FORCED_MENU_ID)) {
	 * query.append("ORDER BY updateDate DESC "); } } else if
	 * ((forceAndStateTransitionFlag != null) && (forceAndStateTransitionFlag ==
	 * WebAppConst.FALSE)) { query.append(") score "); }
	 * 
	 * SQLQuery queryObj = session.createSQLQuery(query .toString());
	 * 
	 * if (menuId.equals(WebAppConst.STATE_TRAN_MENU_ID)) {
	 * queryObj.setParameter("INSPECT_FLAG", WebAppConst.F); }
	 * 
	 * if (menuId.equals(WebAppConst.SCORE_SAMP_MENU_ID)) { if (samplingFlag ==
	 * WebAppConst.TRUE) { queryObj.setParameterList( "SAMPLING_FLAG_LIST",
	 * WebAppConst.SCORE_SAMPLING_FLAG_LIST); } else {
	 * queryObj.setParameterList( "SAMPLING_FLAG_LIST", ArrayUtils .subarray(
	 * WebAppConst.SCORE_SAMPLING_FLAG_LIST, 0, 1)); } }
	 * 
	 * if (!StringUtils.isBlank(answerFormNum)) {
	 * queryObj.setParameter("ANSWER_FORM_NUM", answerFormNum); }
	 * 
	 * setHistoryParameters(queryObj);
	 * 
	 * setCurrentParameters(queryObj);
	 * 
	 * queryObj.setInteger("QUESTION_SEQ", questionInfo.getQuestionSeq());
	 * queryObj.setCharacter("UNLOCK", WebAppConst.UNLOCK);
	 * queryObj.setCharacter("VALID_FLAG", WebAppConst.VALID_FLAG); if
	 * (!(menuId.equals(WebAppConst.STATE_TRAN_MENU_ID) || menuId
	 * .equals(WebAppConst.FORCED_MENU_ID))) {
	 * queryObj.setInteger("RECORD_COUNT_LIMIT",
	 * scoreInputInfo.getResultCount()); }
	 * 
	 * if ((scoreHistoryInfo == null || !scoreHistoryInfo .isHistoryBlock()) &&
	 * (scoreCurrentInfo == null || !scoreCurrentInfo .isCurrentBlock())) { if
	 * (menuId .equals(WebAppConst.REFERENCE_SAMP_MENU_ID)) {
	 * queryObj.setParameterList( "SPECIAL_SCORING_WAIT_STATES",
	 * WebAppConst.SPECIAL_SCORING_WAIT_STATES); } else if (menuId
	 * .equals(WebAppConst.SCORE_SAMP_MENU_ID)) { queryObj.setParameterList(
	 * "SPECIAL_SCORING_STATES", WebAppConst.SPECIAL_SCORING_STATES); }
	 * 
	 * } if (!(menuId.equals(WebAppConst.STATE_TRAN_MENU_ID) || menuId
	 * .equals(WebAppConst.FORCED_MENU_ID))) { queryObj.setParameterList(
	 * "DUMMY_SCORING_STATES", WebAppConst.DUMMY_SCORING_STATES); }
	 * 
	 * return queryObj.list();
	 * 
	 * }
	 * 
	 * public void setCurrentParameters(SQLQuery queryObj) {
	 * 
	 * if (scoreCurrentInfo != null && scoreCurrentInfo.isCurrentBlock()) { if
	 * (!currentScorerIdList.isEmpty()) { queryObj.setParameterList(
	 * "CURRENT_SCORER_ID_LIST", currentScorerIdList); }
	 * 
	 * if (currentCategoryType != null && currentCategoryType == 3) {
	 * 
	 * if (!currentGradeSeqList.isEmpty()) { queryObj.setParameterList(
	 * "CURRENT_GRADE_SEQ_LIST", currentGradeSeqList); }
	 * 
	 * if (ArrayUtils.contains(scoreCurrentInfo .getCurrentGradeNum(), -1)) {
	 * queryObj.setShort( "NO_GRADE_SCORING_STATE",
	 * WebAppConst.NO_GRADE_SCORING_STATE); } } else if (currentCategoryType !=
	 * null && currentCategoryType == 4 && !currentPendingCategorySeqList
	 * .isEmpty()) {
	 * 
	 * queryObj.setParameterList( "CURRENT_PENDING_CATEGORY_SEQ_LIST",
	 * scoreCurrentInfo .getCurrentPendingCategorySeqList()); }
	 * 
	 * if (!StringUtils .isBlank(currentIncludeCheckPoints) && !StringUtils
	 * .isBlank(currentExcludeCheckPoints)) { queryObj.setInteger(
	 * "CURRENT_INCLUDE_BIT_VALUE", scoreCurrentInfo
	 * .getCurrentIncludeBitValue()); queryObj.setInteger(
	 * "CURRENT_EXCLUDE_BIT_VALUE", scoreCurrentInfo
	 * .getCurrentExcludeBitValue()); } else { if (!StringUtils
	 * .isBlank(currentIncludeCheckPoints)) { queryObj.setInteger(
	 * "CURRENT_INCLUDE_BIT_VALUE", scoreCurrentInfo
	 * .getCurrentIncludeBitValue()); } else if (!StringUtils
	 * .isBlank(currentExcludeCheckPoints)) { queryObj.setInteger(
	 * "CURRENT_EXCLUDE_BIT_VALUE", scoreCurrentInfo
	 * .getCurrentExcludeBitValue()); } }
	 * 
	 * if (currentStateList != null && currentStateList.length > 0) {
	 * queryObj.setParameterList( "CURRENT_STATE_LIST", scoreCurrentInfo
	 * .getCurrentStateList()); }
	 * 
	 * if (currentUpdateStartDate != null) { queryObj.setParameter(
	 * "CURRENT_UPDATE_START_DATE", currentUpdateStartDate); }
	 * 
	 * if (currentUpdateEndDate != null) { queryObj.setParameter(
	 * "CURRENT_UPDATE_END_DATE", currentUpdateEndDate); } } else if (menuId
	 * .equals(WebAppConst.SCORE_SAMP_MENU_ID)) { queryObj.setParameterList(
	 * "CURRENT_STATE_LIST", WebAppConst.SCORE_SAMPLING_CURRENT_STATES); } else
	 * if (menuId .equals(WebAppConst.STATE_TRAN_MENU_ID)) {
	 * queryObj.setParameterList( "CURRENT_STATE_LIST",
	 * WebAppConst.STATE_TRANSITION_CURRENT_STATES); } else if (menuId
	 * .equals(WebAppConst.FORCED_MENU_ID)) { queryObj.setParameterList(
	 * "CURRENT_STATE_LIST", WebAppConst.FORCED_SCORING_CURRENT_STATES); }
	 * 
	 * }
	 * 
	 * public void setHistoryParameters(SQLQuery queryObj) {
	 * 
	 * if (scoreHistoryInfo != null && scoreHistoryInfo.isHistoryBlock()) {
	 * 
	 * if (!historyScorerIdList.isEmpty()) { queryObj.setParameterList(
	 * "HISTORY_SCORER_ID_LIST", historyScorerIdList); }
	 * 
	 * if (historyCategoryType != null && historyCategoryType == 3) {
	 * 
	 * if (!historyGradeSeqList.isEmpty()) { queryObj.setParameterList(
	 * "HISTORY_GRADE_SEQ_LIST", historyGradeSeqList); }
	 * 
	 * if (ArrayUtils.contains(scoreHistoryInfo .getHistoryGradeNum(), -1)) {
	 * queryObj.setShort( "NO_GRADE_SCORING_STATE",
	 * WebAppConst.NO_GRADE_SCORING_STATE); } } else if (historyCategoryType !=
	 * null && historyCategoryType == 4 && !historyPendingCategorySeqList
	 * .isEmpty()) {
	 * 
	 * queryObj.setParameterList( "HISTORY_PENDING_CATEGORY_SEQ_LIST",
	 * historyPendingCategorySeqList); }
	 * 
	 * if (!StringUtils .isBlank(historyIncludeCheckPoints) && !StringUtils
	 * .isBlank(historyExcludeCheckPoints)) { queryObj.setInteger(
	 * "HISTORY_INCLUDE_BIT_VALUE", scoreHistoryInfo
	 * .getHistoryIncludeBitValue()); queryObj.setInteger(
	 * "HISTORY_EXCLUDE_BIT_VALUE", scoreHistoryInfo
	 * .getHistoryExcludeBitValue()); } else { if (!StringUtils
	 * .isBlank(historyIncludeCheckPoints)) { queryObj.setInteger(
	 * "HISTORY_INCLUDE_BIT_VALUE", scoreHistoryInfo
	 * .getHistoryIncludeBitValue()); } else if (!StringUtils
	 * .isBlank(historyExcludeCheckPoints)) { queryObj.setInteger(
	 * "HISTORY_EXCLUDE_BIT_VALUE", scoreHistoryInfo
	 * .getHistoryExcludeBitValue()); } }
	 * 
	 * if (historyEventList != null && historyEventList.length > 0) {
	 * queryObj.setParameterList( "HISTORY_EVENT_LIST", historyEventList); }
	 * 
	 * if (historyUpdateStartDate != null) { queryObj.setParameter(
	 * "HISTORY_UPDATE_START_DATE", historyUpdateStartDate); }
	 * 
	 * if (historyUpdateEndDate != null) { queryObj.setParameter(
	 * "HISTORY_UPDATE_END_DATE", historyUpdateEndDate); }
	 * 
	 * }
	 * 
	 * } }); System.out.println(">>>>>>>>>>>>>> searchAnswerRecords end: "+new
	 * Date().getTime()); return answerRecordsList; } catch (RuntimeException
	 * re) { throw re; } }
	 */

	// get limited answer sequences using Percentage rand() query: merged rand()
	// and count.
	// and then using separate query selectAnswerByAnswerSequence() selects
	// record for specified answerSequence.
	/*
	 * @SuppressWarnings("rawtypes")
	 * 
	 * @Override public List searchAnswerRecords(final QuestionInfo
	 * questionInfo, final ScoreInputInfo scoreInputInfo, final Boolean
	 * forceAndStateTransitionFlag, final Integer startRecord, final Integer
	 * endRecord) { List randomAnswerSequences =
	 * fetchRandomAnswerSequences(questionInfo, scoreInputInfo,
	 * forceAndStateTransitionFlag, startRecord, endRecord);
	 * System.out.println(">>>>>>>>> randomAnswerSequences size: "
	 * +randomAnswerSequences.size()); Integer answerSeq =
	 * (Integer)randomAnswerSequences.get(0); return
	 * selectAnswerByAnswerSequence(questionInfo, scoreInputInfo,
	 * forceAndStateTransitionFlag, startRecord, endRecord, answerSeq); }
	 * 
	 * @SuppressWarnings("rawtypes") private List
	 * selectAnswerByAnswerSequence(final QuestionInfo questionInfo, final
	 * ScoreInputInfo scoreInputInfo, final Boolean forceAndStateTransitionFlag,
	 * final Integer startRecord, final Integer endRecord, final Integer
	 * answerSeq) {
	 * 
	 * final String menuId = questionInfo.getMenuId();
	 * System.out.println(">>>>>>>>>>>>>> selectAnswerByAnswerSequence end: " +
	 * new Date().getTime()); try { List answerRecordsList =
	 * getHibernateTemplate( questionInfo.getConnectionString()).execute( new
	 * HibernateCallback<List>() {
	 * 
	 * public List doInHibernate(Session session) throws HibernateException {
	 * StringBuilder query = new StringBuilder(); if
	 * ((forceAndStateTransitionFlag == null) || (forceAndStateTransitionFlag ==
	 * WebAppConst.TRUE)) { query.append(
	 * "SELECT t.answer_seq AS answerSeq, t.answer_form_num AS answerFormNum, "
	 * );
	 * query.append("t.image_file_name AS imageFileName, t.grade_seq AS gradeSeq, "
	 * ); query.append(
	 * "t.bit_value AS bitValue, t.latest_scorer_id AS latestScorerId, "); if
	 * (!(menuId .equals(WebAppConst.STATE_TRAN_MENU_ID) || menuId
	 * .equals(WebAppConst.FORCED_MENU_ID))) { query.append(
	 * "(SELECT scorer_comment FROM tran_desc_score_history AS scoreHistory ");
	 * query.append("WHERE scoreHistory.answer_seq = t.answer_seq ");
	 * query.append("AND scoreHistory.update_date = "); query.append(
	 * "(SELECT max(history.update_date) FROM tran_desc_score_history history "
	 * ); query.append(
	 * "WHERE history.answer_seq = scoreHistory.answer_seq)) AS scorerComment, "
	 * ); } query.append("t.punch_text AS punchText, "); query.append(
	 * "t.pending_category_seq AS pendingCategorySeq, t.latest_scoring_state, "
	 * ); if (((forceAndStateTransitionFlag != null) &&
	 * (forceAndStateTransitionFlag == WebAppConst.TRUE)) || (menuId
	 * .equals(WebAppConst.REFERENCE_SAMP_MENU_ID) &&
	 * (forceAndStateTransitionFlag == null))) { query.append(
	 * "t.question_seq AS questionSeq, t.latest_screen_scorer_id AS latestScreenScorerId, "
	 * ); } if (menuId.equals(WebAppConst.FORCED_MENU_ID) &&
	 * (forceAndStateTransitionFlag != null && forceAndStateTransitionFlag ==
	 * WebAppConst.TRUE)) {
	 * query.append("(SELECT count(*) FROM tran_desc_score_history AS scoreHistory "
	 * ); query.append("WHERE scoreHistory.answer_seq = t.answer_seq ");
	 * query.append("AND scoreHistory.question_seq = :QUESTION_SEQ ");
	 * query.append(
	 * "AND scoreHistory.scorer_comment != '' AND scoreHistory.scorer_comment is not null ) AS commentCount, "
	 * ); } query.append("t.update_date AS updateDate ");
	 * 
	 * } else { query.append("SELECT SUM(rowCount) "); query.append(
	 * "FROM (SELECT count(distinct tranDescScore.answer_seq) as rowCount "); }
	 * query.append("FROM  tran_desc_score AS t ");
	 * 
	 * query.append("INNER JOIN ");
	 * query.append("tran_desc_score_history AS tranDescScoreHistory ");
	 * query.append("ON t.answer_seq = tranDescScoreHistory.answer_seq ");
	 * query.append("WHERE t.answer_seq  = :ANSWER_SEQ ");
	 * 
	 * SQLQuery queryObj = session.createSQLQuery(query .toString());
	 * 
	 * queryObj.setInteger("ANSWER_SEQ", answerSeq); return queryObj.list();
	 * 
	 * } }); System.out
	 * .println(">>>>>>>>>>>>>> selectAnswerByAnswerSequence end: " + new
	 * Date().getTime()); return answerRecordsList; } catch (RuntimeException
	 * re) { throw re; } }
	 * 
	 * @SuppressWarnings("rawtypes") private List
	 * fetchRandomAnswerSequences(final QuestionInfo questionInfo, final
	 * ScoreInputInfo scoreInputInfo, final Boolean forceAndStateTransitionFlag,
	 * final Integer startRecord, final Integer endRecord) {
	 * 
	 * final ScoreHistoryInfo scoreHistoryInfo = scoreInputInfo
	 * .getScoreHistoryInfo();
	 * 
	 * final String menuId = questionInfo.getMenuId(); final boolean
	 * samplingFlag = scoreInputInfo.isSamplingFlag(); final String
	 * answerFormNum = scoreInputInfo.getAnswerFormNum();
	 * 
	 * final List<String> historyScorerIdList = scoreHistoryInfo != null ?
	 * scoreHistoryInfo .getHistoryScorerIdList() : null; final Integer
	 * historyCategoryType = scoreHistoryInfo != null ? scoreHistoryInfo
	 * .getHistoryCategoryType() : null; final List historyGradeSeqList =
	 * scoreHistoryInfo != null ? scoreHistoryInfo .getHistoryGradeSeqList() :
	 * null; final List historyPendingCategorySeqList = scoreHistoryInfo != null
	 * ? scoreHistoryInfo .getHistoryPendingCategorySeqList() : null; final
	 * String historyIncludeCheckPoints = scoreHistoryInfo != null ?
	 * scoreHistoryInfo .getHistoryIncludeCheckPoints() : null; final String
	 * historyExcludeCheckPoints = scoreHistoryInfo != null ? scoreHistoryInfo
	 * .getHistoryExcludeCheckPoints() : null; final Short[] historyEventList =
	 * scoreHistoryInfo != null ? scoreHistoryInfo .getHistoryEventList() :
	 * null; final Date historyUpdateStartDate = scoreHistoryInfo != null ?
	 * scoreHistoryInfo .getHistoryUpdateStartDate() : null; final Date
	 * historyUpdateEndDate = scoreHistoryInfo != null ? scoreHistoryInfo
	 * .getHistoryUpdateEndDate() : null;
	 * 
	 * final ScoreCurrentInfo scoreCurrentInfo = scoreInputInfo
	 * .getScoreCurrentInfo();
	 * 
	 * final List<String> currentScorerIdList = scoreCurrentInfo != null ?
	 * scoreCurrentInfo .getCurrentScorerIdList() : null; final Integer
	 * currentCategoryType = scoreCurrentInfo != null ? scoreCurrentInfo
	 * .getCurrentCategoryType() : null; final List currentGradeSeqList =
	 * scoreCurrentInfo != null ? scoreCurrentInfo .getCurrentGradeSeqList() :
	 * null; final List currentPendingCategorySeqList = scoreCurrentInfo != null
	 * ? scoreCurrentInfo .getCurrentPendingCategorySeqList() : null; final
	 * String currentIncludeCheckPoints = scoreCurrentInfo != null ?
	 * scoreCurrentInfo .getCurrentIncludeCheckPoints() : null; final String
	 * currentExcludeCheckPoints = scoreCurrentInfo != null ? scoreCurrentInfo
	 * .getCurrentExcludeCheckPoints() : null; final Short[] currentStateList =
	 * scoreCurrentInfo != null ? scoreCurrentInfo .getCurrentStateList() :
	 * null; final Date currentUpdateStartDate = scoreCurrentInfo != null ?
	 * scoreCurrentInfo .getCurrentUpdateStartDate() : null; final Date
	 * currentUpdateEndDate = scoreCurrentInfo != null ? scoreCurrentInfo
	 * .getCurrentUpdateEndDate() : null;
	 * 
	 * System.out.println(">>>>>>>>>>>>>> fetchRandomAnswerSequences start: "+new
	 * Date().getTime()); try { List answerRecordsList = getHibernateTemplate(
	 * questionInfo.getConnectionString()).execute( new
	 * HibernateCallback<List>() {
	 * 
	 * public List doInHibernate(Session session) throws HibernateException {
	 * StringBuilder query = new StringBuilder(); if
	 * ((forceAndStateTransitionFlag == null) || (forceAndStateTransitionFlag ==
	 * WebAppConst.TRUE)) { query.append("SELECT answerSeq FROM ( ");
	 * query.append("SELECT tranDescScore.answer_seq AS answerSeq, ");
	 * query.append("tranDescScore.update_date AS updateDate "); } else {
	 * //getting count to display on countPage, for FORCED_SCORING and
	 * STATE_TRANSITION. query.append("SELECT SUM(rowCount) "); query.append(
	 * "FROM (SELECT count(distinct tranDescScore.answer_seq) as rowCount "); }
	 * query.append("FROM tran_desc_score AS tranDescScore ");
	 * 
	 * query.append("INNER JOIN ");
	 * query.append("tran_desc_score_history AS tranDescScoreHistory ");
	 * query.append
	 * ("ON tranDescScore.answer_seq = tranDescScoreHistory.answer_seq ");
	 * query.append("WHERE tranDescScore.question_seq  = :QUESTION_SEQ ");
	 * query.append("AND tranDescScoreHistory.question_seq  = :QUESTION_SEQ ");
	 * if (!(menuId.equals(WebAppConst.STATE_TRAN_MENU_ID) || menuId
	 * .equals(WebAppConst.FORCED_MENU_ID))) { StringBuilder countSubQuery = new
	 * StringBuilder(); countSubQuery.append("(SELECT count(*) ");
	 * countSubQuery.append("FROM tran_desc_score AS t ");
	 * countSubQuery.append("INNER JOIN ");
	 * countSubQuery.append("tran_desc_score_history AS t1 ");
	 * countSubQuery.append("ON t.answer_seq = t1.answer_seq ");
	 * countSubQuery.append("WHERE t.question_seq  = :QUESTION_SEQ ");
	 * countSubQuery.append("AND t1.question_seq  = :QUESTION_SEQ ");
	 * countSubQuery.append("AND t.lock_flag = :UNLOCK "); if
	 * (menuId.equals(WebAppConst.STATE_TRAN_MENU_ID)) {
	 * countSubQuery.append("AND t.inspect_flag = :INSPECT_FLAG "); }
	 * 
	 * if (menuId.equals(WebAppConst.SCORE_SAMP_MENU_ID)) {
	 * countSubQuery.append("AND t.sampling_flag IN :SAMPLING_FLAG_LIST "); }
	 * 
	 * if (!StringUtils.isBlank(answerFormNum)) {
	 * countSubQuery.append("AND t.answer_form_num = :ANSWER_FORM_NUM "); }
	 * 
	 * if (scoreHistoryInfo != null && scoreHistoryInfo.isHistoryBlock()) {
	 * 
	 * if (!historyScorerIdList.isEmpty()) { countSubQuery.append(
	 * "AND t1.latest_screen_scorer_id IN :HISTORY_SCORER_ID_LIST "); }
	 * 
	 * if (historyCategoryType != null && historyCategoryType == 2) {
	 * countSubQuery
	 * .append("AND t1.grade_seq IS null AND t1.pending_category_seq IS null ");
	 * } else if (historyCategoryType != null && historyCategoryType == 3) {
	 * 
	 * if (!historyGradeSeqList.isEmpty() && ArrayUtils.contains(
	 * scoreHistoryInfo .getHistoryGradeNum(), -1)) {
	 * countSubQuery.append("AND (t1.grade_seq IN :HISTORY_GRADE_SEQ_LIST ");
	 * countSubQuery.append("OR t1.scoring_state = :NO_GRADE_SCORING_STATE) ");
	 * } else { if (!historyGradeSeqList.isEmpty()) {
	 * countSubQuery.append("AND t1.grade_seq IN :HISTORY_GRADE_SEQ_LIST "); }
	 * 
	 * if (ArrayUtils.contains( scoreHistoryInfo .getHistoryGradeNum(), -1)) {
	 * countSubQuery.append("AND t1.scoring_state = :NO_GRADE_SCORING_STATE ");
	 * } } } else if (historyCategoryType != null && historyCategoryType == 4 &&
	 * !historyPendingCategorySeqList .isEmpty()) { countSubQuery.append(
	 * "AND t1.pending_category_seq IN :HISTORY_PENDING_CATEGORY_SEQ_LIST "); }
	 * 
	 * if (!StringUtils .isBlank(historyIncludeCheckPoints) && !StringUtils
	 * .isBlank(historyExcludeCheckPoints)) {
	 * countSubQuery.append("AND (t1.bit_value & :HISTORY_INCLUDE_BIT_VALUE) > 0 "
	 * );
	 * countSubQuery.append("AND (t1.bit_value & :HISTORY_EXCLUDE_BIT_VALUE) = 0 "
	 * ); } else { if (!StringUtils .isBlank(historyIncludeCheckPoints)) {
	 * countSubQuery
	 * .append("AND (t1.bit_value & :HISTORY_INCLUDE_BIT_VALUE) > 0 "); } else
	 * if (!StringUtils .isBlank(historyExcludeCheckPoints)) {
	 * countSubQuery.append
	 * ("AND (t1.bit_value & :HISTORY_EXCLUDE_BIT_VALUE) = 0 "); } }
	 * 
	 * if (historyEventList != null && historyEventList.length > 0) {
	 * countSubQuery.append("AND t1.event_id IN :HISTORY_EVENT_LIST "); }
	 * 
	 * if (historyUpdateStartDate != null) {
	 * countSubQuery.append("AND t1.update_date >= :HISTORY_UPDATE_START_DATE "
	 * ); }
	 * 
	 * if (historyUpdateEndDate != null) {
	 * countSubQuery.append("AND t1.update_date <= :HISTORY_UPDATE_END_DATE ");
	 * } }
	 * 
	 * if (scoreCurrentInfo != null && scoreCurrentInfo.isCurrentBlock()) {
	 * 
	 * if (!currentScorerIdList.isEmpty()) { countSubQuery.append(
	 * "AND t.latest_screen_scorer_id IN :CURRENT_SCORER_ID_LIST "); }
	 * 
	 * if (currentCategoryType != null && currentCategoryType == 2) {
	 * countSubQuery
	 * .append("AND t.grade_seq IS null AND t.pending_category_seq IS null "); }
	 * else if (currentCategoryType != null && currentCategoryType == 3) {
	 * 
	 * if (!currentGradeSeqList.isEmpty() && ArrayUtils.contains(
	 * scoreCurrentInfo .getCurrentGradeNum(), -1)) {
	 * countSubQuery.append("AND (t.grade_seq IN :CURRENT_GRADE_SEQ_LIST ");
	 * countSubQuery
	 * .append("OR t.latest_scoring_state = :NO_GRADE_SCORING_STATE) "); } else
	 * { if (!currentGradeSeqList.isEmpty()) {
	 * countSubQuery.append("AND t.grade_seq IN :CURRENT_GRADE_SEQ_LIST "); }
	 * 
	 * if (ArrayUtils.contains( scoreCurrentInfo .getCurrentGradeNum(), -1)) {
	 * countSubQuery
	 * .append("AND t.latest_scoring_state = :NO_GRADE_SCORING_STATE "); } } }
	 * else if (currentCategoryType != null && currentCategoryType == 4 &&
	 * !currentPendingCategorySeqList .isEmpty()) { countSubQuery.append(
	 * "AND t.pending_category_seq IN :CURRENT_PENDING_CATEGORY_SEQ_LIST "); }
	 * 
	 * if (!StringUtils .isBlank(currentIncludeCheckPoints) && !StringUtils
	 * .isBlank(currentExcludeCheckPoints)) {
	 * countSubQuery.append("AND (t.bit_value & :CURRENT_INCLUDE_BIT_VALUE) > 0 "
	 * );
	 * countSubQuery.append("AND (t.bit_value & :CURRENT_EXCLUDE_BIT_VALUE) = 0 "
	 * ); } else { if (!StringUtils .isBlank(currentIncludeCheckPoints)) {
	 * countSubQuery
	 * .append("AND (t.bit_value & :CURRENT_INCLUDE_BIT_VALUE) > 0 "); } else if
	 * (!StringUtils .isBlank(currentExcludeCheckPoints)) {
	 * countSubQuery.append(
	 * "AND (t.bit_value & :CURRENT_EXCLUDE_BIT_VALUE) = 0 "); } }
	 * 
	 * if (currentStateList != null && currentStateList.length > 0) {
	 * countSubQuery
	 * .append("AND t.latest_scoring_state IN :CURRENT_STATE_LIST "); }
	 * 
	 * if (currentUpdateStartDate != null) {
	 * countSubQuery.append("AND t.update_date >= :CURRENT_UPDATE_START_DATE ");
	 * }
	 * 
	 * if (currentUpdateEndDate != null) {
	 * countSubQuery.append("AND t.update_date <= :CURRENT_UPDATE_END_DATE "); }
	 * } else if (menuId .equals(WebAppConst.SCORE_SAMP_MENU_ID) || menuId
	 * .equals(WebAppConst.STATE_TRAN_MENU_ID) || menuId
	 * .equals(WebAppConst.FORCED_MENU_ID)) {
	 * countSubQuery.append("AND t.latest_scoring_state IN :CURRENT_STATE_LIST "
	 * ); }
	 * 
	 * if ((scoreHistoryInfo == null || !scoreHistoryInfo .isHistoryBlock()) &&
	 * (scoreCurrentInfo == null || !scoreCurrentInfo .isCurrentBlock())) { if
	 * (menuId .equals(WebAppConst.REFERENCE_SAMP_MENU_ID)) {
	 * countSubQuery.append
	 * ("AND t.latest_scoring_state NOT IN :SPECIAL_SCORING_WAIT_STATES "); }
	 * else if (menuId .equals(WebAppConst.SCORE_SAMP_MENU_ID)) {
	 * countSubQuery.append
	 * ("AND t.latest_scoring_state NOT IN :SPECIAL_SCORING_STATES "); }
	 * 
	 * } if (!(menuId.equals(WebAppConst.STATE_TRAN_MENU_ID) || menuId
	 * .equals(WebAppConst.FORCED_MENU_ID))) {
	 * countSubQuery.append("AND t1.scoring_state NOT IN :DUMMY_SCORING_STATES "
	 * ); countSubQuery.append(
	 * "AND t.latest_scoring_state NOT IN :DUMMY_SCORING_STATES "); }
	 * countSubQuery.append("AND t.valid_flag = :VALID_FLAG ");
	 * countSubQuery.append("AND t1.valid_flag = :VALID_FLAG) ");
	 * 
	 * query.append("AND RAND()<((:RECORD_COUNT_LIMIT"+"/");
	 * query.append(countSubQuery); query.append(")*10) "); }
	 * 
	 * query.append("AND tranDescScore.lock_flag = :UNLOCK ");
	 * 
	 * if (menuId.equals(WebAppConst.STATE_TRAN_MENU_ID)) {
	 * query.append("AND tranDescScore.inspect_flag = :INSPECT_FLAG "); }
	 * 
	 * if (menuId.equals(WebAppConst.SCORE_SAMP_MENU_ID)) {
	 * query.append("AND tranDescScore.sampling_flag IN :SAMPLING_FLAG_LIST ");
	 * }
	 * 
	 * if (!StringUtils.isBlank(answerFormNum)) {
	 * query.append("AND tranDescScore.answer_form_num = :ANSWER_FORM_NUM "); }
	 * 
	 * if (scoreHistoryInfo != null && scoreHistoryInfo.isHistoryBlock()) {
	 * 
	 * if (!historyScorerIdList.isEmpty()) { query.append(
	 * "AND tranDescScoreHistory.latest_screen_scorer_id IN :HISTORY_SCORER_ID_LIST "
	 * ); }
	 * 
	 * if (historyCategoryType != null && historyCategoryType == 2) {
	 * query.append(
	 * "AND tranDescScoreHistory.grade_seq IS null AND tranDescScoreHistory.pending_category_seq IS null "
	 * ); } else if (historyCategoryType != null && historyCategoryType == 3) {
	 * 
	 * if (!historyGradeSeqList.isEmpty() && ArrayUtils.contains(
	 * scoreHistoryInfo .getHistoryGradeNum(), -1)) { query.append(
	 * "AND (tranDescScoreHistory.grade_seq IN :HISTORY_GRADE_SEQ_LIST ");
	 * query.
	 * append("OR tranDescScoreHistory.scoring_state = :NO_GRADE_SCORING_STATE) "
	 * ); } else { if (!historyGradeSeqList.isEmpty()) {
	 * query.append("AND tranDescScoreHistory.grade_seq IN :HISTORY_GRADE_SEQ_LIST "
	 * ); }
	 * 
	 * if (ArrayUtils.contains( scoreHistoryInfo .getHistoryGradeNum(), -1)) {
	 * query
	 * .append("AND tranDescScoreHistory.scoring_state = :NO_GRADE_SCORING_STATE "
	 * ); } } } else if (historyCategoryType != null && historyCategoryType == 4
	 * && !historyPendingCategorySeqList .isEmpty()) { query.append(
	 * "AND tranDescScoreHistory.pending_category_seq IN :HISTORY_PENDING_CATEGORY_SEQ_LIST "
	 * ); }
	 * 
	 * if (!StringUtils .isBlank(historyIncludeCheckPoints) && !StringUtils
	 * .isBlank(historyExcludeCheckPoints)) { query.append(
	 * "AND (tranDescScoreHistory.bit_value & :HISTORY_INCLUDE_BIT_VALUE) > 0 "
	 * ); query.append(
	 * "AND (tranDescScoreHistory.bit_value & :HISTORY_EXCLUDE_BIT_VALUE) = 0 "
	 * ); } else { if (!StringUtils .isBlank(historyIncludeCheckPoints)) {
	 * query.append(
	 * "AND (tranDescScoreHistory.bit_value & :HISTORY_INCLUDE_BIT_VALUE) > 0 "
	 * ); } else if (!StringUtils .isBlank(historyExcludeCheckPoints)) {
	 * query.append
	 * ("AND (tranDescScoreHistory.bit_value & :HISTORY_EXCLUDE_BIT_VALUE) = 0 "
	 * ); } }
	 * 
	 * if (historyEventList != null && historyEventList.length > 0) {
	 * query.append
	 * ("AND tranDescScoreHistory.event_id IN :HISTORY_EVENT_LIST "); }
	 * 
	 * if (historyUpdateStartDate != null) { query.append(
	 * "AND tranDescScoreHistory.update_date >= :HISTORY_UPDATE_START_DATE "); }
	 * 
	 * if (historyUpdateEndDate != null) { query.append(
	 * "AND tranDescScoreHistory.update_date <= :HISTORY_UPDATE_END_DATE "); } }
	 * 
	 * if (scoreCurrentInfo != null && scoreCurrentInfo.isCurrentBlock()) {
	 * 
	 * if (!currentScorerIdList.isEmpty()) { query.append(
	 * "AND tranDescScore.latest_screen_scorer_id IN :CURRENT_SCORER_ID_LIST ");
	 * }
	 * 
	 * if (currentCategoryType != null && currentCategoryType == 2) {
	 * query.append(
	 * "AND tranDescScore.grade_seq IS null AND tranDescScore.pending_category_seq IS null "
	 * ); } else if (currentCategoryType != null && currentCategoryType == 3) {
	 * 
	 * if (!currentGradeSeqList.isEmpty() && ArrayUtils.contains(
	 * scoreCurrentInfo .getCurrentGradeNum(), -1)) {
	 * query.append("AND (tranDescScore.grade_seq IN :CURRENT_GRADE_SEQ_LIST ");
	 * query
	 * .append("OR tranDescScore.latest_scoring_state = :NO_GRADE_SCORING_STATE) "
	 * ); } else { if (!currentGradeSeqList.isEmpty()) {
	 * query.append("AND tranDescScore.grade_seq IN :CURRENT_GRADE_SEQ_LIST ");
	 * }
	 * 
	 * if (ArrayUtils.contains( scoreCurrentInfo .getCurrentGradeNum(), -1)) {
	 * query
	 * .append("AND tranDescScore.latest_scoring_state = :NO_GRADE_SCORING_STATE "
	 * ); } } } else if (currentCategoryType != null && currentCategoryType == 4
	 * && !currentPendingCategorySeqList .isEmpty()) { query.append(
	 * "AND tranDescScore.pending_category_seq IN :CURRENT_PENDING_CATEGORY_SEQ_LIST "
	 * ); }
	 * 
	 * if (!StringUtils .isBlank(currentIncludeCheckPoints) && !StringUtils
	 * .isBlank(currentExcludeCheckPoints)) { query.append(
	 * "AND (tranDescScore.bit_value & :CURRENT_INCLUDE_BIT_VALUE) > 0 ");
	 * query.
	 * append("AND (tranDescScore.bit_value & :CURRENT_EXCLUDE_BIT_VALUE) = 0 "
	 * ); } else { if (!StringUtils .isBlank(currentIncludeCheckPoints)) {
	 * query.
	 * append("AND (tranDescScore.bit_value & :CURRENT_INCLUDE_BIT_VALUE) > 0 "
	 * ); } else if (!StringUtils .isBlank(currentExcludeCheckPoints)) {
	 * query.append
	 * ("AND (tranDescScore.bit_value & :CURRENT_EXCLUDE_BIT_VALUE) = 0 "); } }
	 * 
	 * if (currentStateList != null && currentStateList.length > 0) {
	 * query.append
	 * ("AND tranDescScore.latest_scoring_state IN :CURRENT_STATE_LIST "); }
	 * 
	 * if (currentUpdateStartDate != null) {
	 * query.append("AND tranDescScore.update_date >= :CURRENT_UPDATE_START_DATE "
	 * ); }
	 * 
	 * if (currentUpdateEndDate != null) {
	 * query.append("AND tranDescScore.update_date <= :CURRENT_UPDATE_END_DATE "
	 * ); } } else if (menuId .equals(WebAppConst.SCORE_SAMP_MENU_ID) || menuId
	 * .equals(WebAppConst.STATE_TRAN_MENU_ID) || menuId
	 * .equals(WebAppConst.FORCED_MENU_ID)) {
	 * query.append("AND tranDescScore.latest_scoring_state IN :CURRENT_STATE_LIST "
	 * ); }
	 * 
	 * if ((scoreHistoryInfo == null || !scoreHistoryInfo .isHistoryBlock()) &&
	 * (scoreCurrentInfo == null || !scoreCurrentInfo .isCurrentBlock())) { if
	 * (menuId .equals(WebAppConst.REFERENCE_SAMP_MENU_ID)) { query.append(
	 * "AND tranDescScore.latest_scoring_state NOT IN :SPECIAL_SCORING_WAIT_STATES "
	 * ); } else if (menuId .equals(WebAppConst.SCORE_SAMP_MENU_ID)) {
	 * query.append
	 * ("AND tranDescScore.latest_scoring_state NOT IN :SPECIAL_SCORING_STATES "
	 * ); }
	 * 
	 * } if (!(menuId.equals(WebAppConst.STATE_TRAN_MENU_ID) || menuId
	 * .equals(WebAppConst.FORCED_MENU_ID))) { query.append(
	 * "AND tranDescScoreHistory.scoring_state NOT IN :DUMMY_SCORING_STATES ");
	 * query.append(
	 * "AND tranDescScore.latest_scoring_state NOT IN :DUMMY_SCORING_STATES ");
	 * } query.append("AND tranDescScore.valid_flag = :VALID_FLAG ");
	 * query.append("AND tranDescScoreHistory.valid_flag = :VALID_FLAG ");
	 * query.append("GROUP BY tranDescScore.answer_seq "); if
	 * ((forceAndStateTransitionFlag != null) && (forceAndStateTransitionFlag ==
	 * WebAppConst.TRUE) && (menuId .equals(WebAppConst.FORCED_MENU_ID))) {
	 * query.append("ORDER BY tranDescScore.update_date DESC ");
	 * query.append("LIMIT "); if (endRecord != null) { if (startRecord > 0) {
	 * query.append(startRecord + " , " + endRecord); } else {
	 * query.append(endRecord + " "); } } } if
	 * (!(menuId.equals(WebAppConst.STATE_TRAN_MENU_ID) || menuId
	 * .equals(WebAppConst.FORCED_MENU_ID))) {
	 * query.append("LIMIT :RECORD_COUNT_LIMIT "); query.append(") score ");
	 * query.append("ORDER BY updateDate DESC "); } else if
	 * ((forceAndStateTransitionFlag != null) && (forceAndStateTransitionFlag ==
	 * WebAppConst.TRUE)) { query.append(") score "); if
	 * (!menuId.equals(WebAppConst.FORCED_MENU_ID)) {
	 * query.append("ORDER BY updateDate DESC "); } } else if
	 * ((forceAndStateTransitionFlag != null) && (forceAndStateTransitionFlag ==
	 * WebAppConst.FALSE)) { query.append(") score "); }
	 * 
	 * SQLQuery queryObj = session.createSQLQuery(query .toString());
	 * 
	 * if (menuId.equals(WebAppConst.STATE_TRAN_MENU_ID)) {
	 * queryObj.setParameter("INSPECT_FLAG", WebAppConst.F); }
	 * 
	 * if (menuId.equals(WebAppConst.SCORE_SAMP_MENU_ID)) { if (samplingFlag ==
	 * WebAppConst.TRUE) { queryObj.setParameterList( "SAMPLING_FLAG_LIST",
	 * WebAppConst.SCORE_SAMPLING_FLAG_LIST); } else {
	 * queryObj.setParameterList( "SAMPLING_FLAG_LIST", ArrayUtils .subarray(
	 * WebAppConst.SCORE_SAMPLING_FLAG_LIST, 0, 1)); } }
	 * 
	 * if (!StringUtils.isBlank(answerFormNum)) {
	 * queryObj.setParameter("ANSWER_FORM_NUM", answerFormNum); }
	 * 
	 * setHistoryParameters(queryObj);
	 * 
	 * setCurrentParameters(queryObj);
	 * 
	 * queryObj.setInteger("QUESTION_SEQ", questionInfo.getQuestionSeq());
	 * queryObj.setCharacter("UNLOCK", WebAppConst.UNLOCK);
	 * queryObj.setCharacter("VALID_FLAG", WebAppConst.VALID_FLAG); if
	 * (!(menuId.equals(WebAppConst.STATE_TRAN_MENU_ID) || menuId
	 * .equals(WebAppConst.FORCED_MENU_ID))) {
	 * queryObj.setInteger("RECORD_COUNT_LIMIT",
	 * scoreInputInfo.getResultCount()); }
	 * 
	 * if ((scoreHistoryInfo == null || !scoreHistoryInfo .isHistoryBlock()) &&
	 * (scoreCurrentInfo == null || !scoreCurrentInfo .isCurrentBlock())) { if
	 * (menuId .equals(WebAppConst.REFERENCE_SAMP_MENU_ID)) {
	 * queryObj.setParameterList( "SPECIAL_SCORING_WAIT_STATES",
	 * WebAppConst.SPECIAL_SCORING_WAIT_STATES); } else if (menuId
	 * .equals(WebAppConst.SCORE_SAMP_MENU_ID)) { queryObj.setParameterList(
	 * "SPECIAL_SCORING_STATES", WebAppConst.SPECIAL_SCORING_STATES); }
	 * 
	 * } if (!(menuId.equals(WebAppConst.STATE_TRAN_MENU_ID) || menuId
	 * .equals(WebAppConst.FORCED_MENU_ID))) { queryObj.setParameterList(
	 * "DUMMY_SCORING_STATES", WebAppConst.DUMMY_SCORING_STATES); }
	 * 
	 * return queryObj.list();
	 * 
	 * }
	 * 
	 * public void setCurrentParameters(SQLQuery queryObj) {
	 * 
	 * if (scoreCurrentInfo != null && scoreCurrentInfo.isCurrentBlock()) { if
	 * (!currentScorerIdList.isEmpty()) { queryObj.setParameterList(
	 * "CURRENT_SCORER_ID_LIST", currentScorerIdList); }
	 * 
	 * if (currentCategoryType != null && currentCategoryType == 3) {
	 * 
	 * if (!currentGradeSeqList.isEmpty()) { queryObj.setParameterList(
	 * "CURRENT_GRADE_SEQ_LIST", currentGradeSeqList); }
	 * 
	 * if (ArrayUtils.contains(scoreCurrentInfo .getCurrentGradeNum(), -1)) {
	 * queryObj.setShort( "NO_GRADE_SCORING_STATE",
	 * WebAppConst.NO_GRADE_SCORING_STATE); } } else if (currentCategoryType !=
	 * null && currentCategoryType == 4 && !currentPendingCategorySeqList
	 * .isEmpty()) {
	 * 
	 * queryObj.setParameterList( "CURRENT_PENDING_CATEGORY_SEQ_LIST",
	 * scoreCurrentInfo .getCurrentPendingCategorySeqList()); }
	 * 
	 * if (!StringUtils .isBlank(currentIncludeCheckPoints) && !StringUtils
	 * .isBlank(currentExcludeCheckPoints)) { queryObj.setInteger(
	 * "CURRENT_INCLUDE_BIT_VALUE", scoreCurrentInfo
	 * .getCurrentIncludeBitValue()); queryObj.setInteger(
	 * "CURRENT_EXCLUDE_BIT_VALUE", scoreCurrentInfo
	 * .getCurrentExcludeBitValue()); } else { if (!StringUtils
	 * .isBlank(currentIncludeCheckPoints)) { queryObj.setInteger(
	 * "CURRENT_INCLUDE_BIT_VALUE", scoreCurrentInfo
	 * .getCurrentIncludeBitValue()); } else if (!StringUtils
	 * .isBlank(currentExcludeCheckPoints)) { queryObj.setInteger(
	 * "CURRENT_EXCLUDE_BIT_VALUE", scoreCurrentInfo
	 * .getCurrentExcludeBitValue()); } }
	 * 
	 * if (currentStateList != null && currentStateList.length > 0) {
	 * queryObj.setParameterList( "CURRENT_STATE_LIST", scoreCurrentInfo
	 * .getCurrentStateList()); }
	 * 
	 * if (currentUpdateStartDate != null) { queryObj.setParameter(
	 * "CURRENT_UPDATE_START_DATE", currentUpdateStartDate); }
	 * 
	 * if (currentUpdateEndDate != null) { queryObj.setParameter(
	 * "CURRENT_UPDATE_END_DATE", currentUpdateEndDate); } } else if (menuId
	 * .equals(WebAppConst.SCORE_SAMP_MENU_ID)) { queryObj.setParameterList(
	 * "CURRENT_STATE_LIST", WebAppConst.SCORE_SAMPLING_CURRENT_STATES); } else
	 * if (menuId .equals(WebAppConst.STATE_TRAN_MENU_ID)) {
	 * queryObj.setParameterList( "CURRENT_STATE_LIST",
	 * WebAppConst.STATE_TRANSITION_CURRENT_STATES); } else if (menuId
	 * .equals(WebAppConst.FORCED_MENU_ID)) { queryObj.setParameterList(
	 * "CURRENT_STATE_LIST", WebAppConst.FORCED_SCORING_CURRENT_STATES); }
	 * 
	 * }
	 * 
	 * public void setHistoryParameters(SQLQuery queryObj) {
	 * 
	 * if (scoreHistoryInfo != null && scoreHistoryInfo.isHistoryBlock()) {
	 * 
	 * if (!historyScorerIdList.isEmpty()) { queryObj.setParameterList(
	 * "HISTORY_SCORER_ID_LIST", historyScorerIdList); }
	 * 
	 * if (historyCategoryType != null && historyCategoryType == 3) {
	 * 
	 * if (!historyGradeSeqList.isEmpty()) { queryObj.setParameterList(
	 * "HISTORY_GRADE_SEQ_LIST", historyGradeSeqList); }
	 * 
	 * if (ArrayUtils.contains(scoreHistoryInfo .getHistoryGradeNum(), -1)) {
	 * queryObj.setShort( "NO_GRADE_SCORING_STATE",
	 * WebAppConst.NO_GRADE_SCORING_STATE); } } else if (historyCategoryType !=
	 * null && historyCategoryType == 4 && !historyPendingCategorySeqList
	 * .isEmpty()) {
	 * 
	 * queryObj.setParameterList( "HISTORY_PENDING_CATEGORY_SEQ_LIST",
	 * historyPendingCategorySeqList); }
	 * 
	 * if (!StringUtils .isBlank(historyIncludeCheckPoints) && !StringUtils
	 * .isBlank(historyExcludeCheckPoints)) { queryObj.setInteger(
	 * "HISTORY_INCLUDE_BIT_VALUE", scoreHistoryInfo
	 * .getHistoryIncludeBitValue()); queryObj.setInteger(
	 * "HISTORY_EXCLUDE_BIT_VALUE", scoreHistoryInfo
	 * .getHistoryExcludeBitValue()); } else { if (!StringUtils
	 * .isBlank(historyIncludeCheckPoints)) { queryObj.setInteger(
	 * "HISTORY_INCLUDE_BIT_VALUE", scoreHistoryInfo
	 * .getHistoryIncludeBitValue()); } else if (!StringUtils
	 * .isBlank(historyExcludeCheckPoints)) { queryObj.setInteger(
	 * "HISTORY_EXCLUDE_BIT_VALUE", scoreHistoryInfo
	 * .getHistoryExcludeBitValue()); } }
	 * 
	 * if (historyEventList != null && historyEventList.length > 0) {
	 * queryObj.setParameterList( "HISTORY_EVENT_LIST", historyEventList); }
	 * 
	 * if (historyUpdateStartDate != null) { queryObj.setParameter(
	 * "HISTORY_UPDATE_START_DATE", historyUpdateStartDate); }
	 * 
	 * if (historyUpdateEndDate != null) { queryObj.setParameter(
	 * "HISTORY_UPDATE_END_DATE", historyUpdateEndDate); }
	 * 
	 * }
	 * 
	 * } });
	 * System.out.println(">>>>>>>>>>>>>> fetchRandomAnswerSequences end: "+new
	 * Date().getTime()); return answerRecordsList; } catch (RuntimeException
	 * re) { throw re; } }
	 */

	/*
	 * //ReferenceSampling & ScoreSampling get all coloumns using Percentage
	 * rand() query: merged rand() and count.
	 * 
	 * @SuppressWarnings("rawtypes")
	 * 
	 * @Override public List searchAnswerRecords(final QuestionInfo
	 * questionInfo, final ScoreInputInfo scoreInputInfo, final Boolean
	 * forceAndStateTransitionFlag, final Integer startRecord, final Integer
	 * endRecord, final String orderByRandAttempt) {
	 * 
	 * final ScoreHistoryInfo scoreHistoryInfo = scoreInputInfo
	 * .getScoreHistoryInfo();
	 * 
	 * final String menuId = questionInfo.getMenuId(); final boolean
	 * samplingFlag = scoreInputInfo.isSamplingFlag(); final String
	 * answerFormNum = scoreInputInfo.getAnswerFormNum();
	 * 
	 * final List<String> historyScorerIdList = scoreHistoryInfo != null ?
	 * scoreHistoryInfo .getHistoryScorerIdList() : null; final Integer
	 * historyCategoryType = scoreHistoryInfo != null ? scoreHistoryInfo
	 * .getHistoryCategoryType() : null; final List historyGradeSeqList =
	 * scoreHistoryInfo != null ? scoreHistoryInfo .getHistoryGradeSeqList() :
	 * null; final List historyPendingCategorySeqList = scoreHistoryInfo != null
	 * ? scoreHistoryInfo .getHistoryPendingCategorySeqList() : null; final
	 * String historyIncludeCheckPoints = scoreHistoryInfo != null ?
	 * scoreHistoryInfo .getHistoryIncludeCheckPoints() : null; final String
	 * historyExcludeCheckPoints = scoreHistoryInfo != null ? scoreHistoryInfo
	 * .getHistoryExcludeCheckPoints() : null; final Short[] historyEventList =
	 * scoreHistoryInfo != null ? scoreHistoryInfo .getHistoryEventList() :
	 * null; final Date historyUpdateStartDate = scoreHistoryInfo != null ?
	 * scoreHistoryInfo .getHistoryUpdateStartDate() : null; final Date
	 * historyUpdateEndDate = scoreHistoryInfo != null ? scoreHistoryInfo
	 * .getHistoryUpdateEndDate() : null;
	 * 
	 * final ScoreCurrentInfo scoreCurrentInfo = scoreInputInfo
	 * .getScoreCurrentInfo();
	 * 
	 * final List<String> currentScorerIdList = scoreCurrentInfo != null ?
	 * scoreCurrentInfo .getCurrentScorerIdList() : null; final Integer
	 * currentCategoryType = scoreCurrentInfo != null ? scoreCurrentInfo
	 * .getCurrentCategoryType() : null; final List currentGradeSeqList =
	 * scoreCurrentInfo != null ? scoreCurrentInfo .getCurrentGradeSeqList() :
	 * null; final List currentPendingCategorySeqList = scoreCurrentInfo != null
	 * ? scoreCurrentInfo .getCurrentPendingCategorySeqList() : null; final
	 * String currentIncludeCheckPoints = scoreCurrentInfo != null ?
	 * scoreCurrentInfo .getCurrentIncludeCheckPoints() : null; final String
	 * currentExcludeCheckPoints = scoreCurrentInfo != null ? scoreCurrentInfo
	 * .getCurrentExcludeCheckPoints() : null; final Short[] currentStateList =
	 * scoreCurrentInfo != null ? scoreCurrentInfo .getCurrentStateList() :
	 * null; final Date currentUpdateStartDate = scoreCurrentInfo != null ?
	 * scoreCurrentInfo .getCurrentUpdateStartDate() : null; final Date
	 * currentUpdateEndDate = scoreCurrentInfo != null ? scoreCurrentInfo
	 * .getCurrentUpdateEndDate() : null;
	 * 
	 * System.out.println(">>>>>>>>>>>>>> searchAnswerRecords start: " + new
	 * Date().getTime()); try { List answerRecordsList = getHibernateTemplate(
	 * questionInfo.getConnectionString()).execute( new
	 * HibernateCallback<List>() {
	 * 
	 * public List doInHibernate(Session session) throws HibernateException {
	 * StringBuilder query = new StringBuilder(); if
	 * ((forceAndStateTransitionFlag == null) || (forceAndStateTransitionFlag ==
	 * WebAppConst.TRUE)) { query.append("SELECT * FROM ( "); query.append(
	 * "SELECT tranDescScore.answer_seq AS answerSeq, tranDescScore.answer_form_num AS answerFormNum, "
	 * ); query.append(
	 * "tranDescScore.image_file_name AS imageFileName, tranDescScore.grade_seq AS gradeSeq, "
	 * ); query.append(
	 * "tranDescScore.bit_value AS bitValue, tranDescScore.latest_scorer_id AS latestScorerId, "
	 * ); if (!(menuId .equals(WebAppConst.STATE_TRAN_MENU_ID) || menuId
	 * .equals(WebAppConst.FORCED_MENU_ID))) { query.append(
	 * "(SELECT scorer_comment FROM tran_desc_score_history AS scoreHistory ");
	 * query
	 * .append("WHERE scoreHistory.answer_seq = tranDescScore.answer_seq ");
	 * query.append("AND scoreHistory.update_date = "); query.append(
	 * "(SELECT max(history.update_date) FROM tran_desc_score_history history "
	 * ); query.append(
	 * "WHERE history.answer_seq = scoreHistory.answer_seq)) AS scorerComment, "
	 * ); } query.append("tranDescScore.punch_text AS punchText, ");
	 * query.append(
	 * "tranDescScore.pending_category_seq AS pendingCategorySeq, tranDescScore.latest_scoring_state, "
	 * ); if (((forceAndStateTransitionFlag != null) &&
	 * (forceAndStateTransitionFlag == WebAppConst.TRUE)) || (menuId
	 * .equals(WebAppConst.REFERENCE_SAMP_MENU_ID) &&
	 * (forceAndStateTransitionFlag == null))) { query.append(
	 * "tranDescScore.question_seq AS questionSeq, tranDescScore.latest_screen_scorer_id AS latestScreenScorerId, "
	 * ); }
	 * 
	 * // here is count means no of comments. if
	 * (menuId.equals(WebAppConst.FORCED_MENU_ID) &&
	 * (forceAndStateTransitionFlag != null && forceAndStateTransitionFlag ==
	 * WebAppConst.TRUE)) {
	 * query.append("(SELECT count(*) FROM tran_desc_score_history AS scoreHistory "
	 * );
	 * query.append("WHERE scoreHistory.answer_seq = tranDescScore.answer_seq "
	 * ); query.append("AND scoreHistory.question_seq = :QUESTION_SEQ ");
	 * query.append(
	 * "AND scoreHistory.scorer_comment != '' AND scoreHistory.scorer_comment is not null ) AS commentCount, "
	 * ); } query.append("tranDescScore.update_date AS updateDate ");
	 * 
	 * } else { // getting count to display on countPage, for // FORCED_SCORING
	 * and STATE_TRANSITION. query.append("SELECT SUM(rowCount) ");
	 * query.append(
	 * "FROM (SELECT count(distinct tranDescScore.answer_seq) as rowCount "); }
	 * query.append("FROM tran_desc_score AS tranDescScore ");
	 * 
	 * query.append("INNER JOIN ");
	 * query.append("tran_desc_score_history AS tranDescScoreHistory ");
	 * query.append
	 * ("ON tranDescScore.answer_seq = tranDescScoreHistory.answer_seq ");
	 * query.append("WHERE tranDescScore.question_seq  = :QUESTION_SEQ ");
	 * query.append("AND tranDescScoreHistory.question_seq  = :QUESTION_SEQ ");
	 * if (!(menuId.equals(WebAppConst.STATE_TRAN_MENU_ID) || menuId
	 * .equals(WebAppConst.FORCED_MENU_ID)) && (orderByRandAttempt
	 * .equals(WebAppConst.ORDER_BY_ATTEMPT1))) { query.append(
	 * "AND tranDescScore.answer_seq like CONCAT('%',(FLOOR(RAND()*10))) "); }
	 * else if (!(menuId .equals(WebAppConst.STATE_TRAN_MENU_ID) || menuId
	 * .equals(WebAppConst.FORCED_MENU_ID)) && (orderByRandAttempt
	 * .equals(WebAppConst.ORDER_BY_ATTEMPT2))) { query.append(
	 * "AND tranDescScore.answer_seq like CONCAT('%',(FLOOR(RAND()*10)),'%') ");
	 * } else if (!(menuId .equals(WebAppConst.STATE_TRAN_MENU_ID) || menuId
	 * .equals(WebAppConst.FORCED_MENU_ID)) && (orderByRandAttempt
	 * .equals(WebAppConst.ORDER_BY_ATTEMPT3))) { StringBuilder countSubQuery =
	 * new StringBuilder(); countSubQuery.append("(SELECT count(*) ");
	 * countSubQuery .append("FROM tran_desc_score AS t ");
	 * countSubQuery.append("INNER JOIN "); countSubQuery
	 * .append("tran_desc_score_history AS t1 "); countSubQuery
	 * .append("ON t.answer_seq = t1.answer_seq "); countSubQuery
	 * .append("WHERE t.question_seq  = :QUESTION_SEQ "); countSubQuery
	 * .append("AND t1.question_seq  = :QUESTION_SEQ "); countSubQuery
	 * .append("AND t.lock_flag = :UNLOCK "); if (menuId
	 * .equals(WebAppConst.STATE_TRAN_MENU_ID)) { countSubQuery
	 * .append("AND t.inspect_flag = :INSPECT_FLAG "); }
	 * 
	 * if (menuId .equals(WebAppConst.SCORE_SAMP_MENU_ID)) { countSubQuery
	 * .append("AND t.sampling_flag IN :SAMPLING_FLAG_LIST "); }
	 * 
	 * if (!StringUtils.isBlank(answerFormNum)) { countSubQuery
	 * .append("AND t.answer_form_num = :ANSWER_FORM_NUM "); }
	 * 
	 * if (scoreHistoryInfo != null && scoreHistoryInfo.isHistoryBlock()) {
	 * 
	 * if (!historyScorerIdList.isEmpty()) { countSubQuery
	 * .append("AND t1.latest_screen_scorer_id IN :HISTORY_SCORER_ID_LIST "); }
	 * 
	 * if (historyCategoryType != null && historyCategoryType == 2) {
	 * countSubQuery
	 * .append("AND t1.grade_seq IS null AND t1.pending_category_seq IS null ");
	 * } else if (historyCategoryType != null && historyCategoryType == 3) {
	 * 
	 * if (!historyGradeSeqList.isEmpty() && ArrayUtils.contains(
	 * scoreHistoryInfo .getHistoryGradeNum(), -1)) { countSubQuery
	 * .append("AND (t1.grade_seq IN :HISTORY_GRADE_SEQ_LIST "); countSubQuery
	 * .append("OR t1.scoring_state = :NO_GRADE_SCORING_STATE) "); } else { if
	 * (!historyGradeSeqList.isEmpty()) { countSubQuery
	 * .append("AND t1.grade_seq IN :HISTORY_GRADE_SEQ_LIST "); }
	 * 
	 * if (ArrayUtils.contains( scoreHistoryInfo .getHistoryGradeNum(), -1)) {
	 * countSubQuery .append("AND t1.scoring_state = :NO_GRADE_SCORING_STATE ");
	 * } } } else if (historyCategoryType != null && historyCategoryType == 4 &&
	 * !historyPendingCategorySeqList .isEmpty()) { countSubQuery
	 * .append("AND t1.pending_category_seq IN :HISTORY_PENDING_CATEGORY_SEQ_LIST "
	 * ); }
	 * 
	 * if (!StringUtils .isBlank(historyIncludeCheckPoints) && !StringUtils
	 * .isBlank(historyExcludeCheckPoints)) { countSubQuery
	 * .append("AND (t1.bit_value & :HISTORY_INCLUDE_BIT_VALUE) > 0 ");
	 * countSubQuery
	 * .append("AND (t1.bit_value & :HISTORY_EXCLUDE_BIT_VALUE) = 0 "); } else {
	 * if (!StringUtils .isBlank(historyIncludeCheckPoints)) { countSubQuery
	 * .append("AND (t1.bit_value & :HISTORY_INCLUDE_BIT_VALUE) > 0 "); } else
	 * if (!StringUtils .isBlank(historyExcludeCheckPoints)) { countSubQuery
	 * .append("AND (t1.bit_value & :HISTORY_EXCLUDE_BIT_VALUE) = 0 "); } }
	 * 
	 * if (historyEventList != null && historyEventList.length > 0) {
	 * countSubQuery .append("AND t1.event_id IN :HISTORY_EVENT_LIST "); }
	 * 
	 * if (historyUpdateStartDate != null) { countSubQuery
	 * .append("AND t1.update_date >= :HISTORY_UPDATE_START_DATE "); }
	 * 
	 * if (historyUpdateEndDate != null) { countSubQuery
	 * .append("AND t1.update_date <= :HISTORY_UPDATE_END_DATE "); } }
	 * 
	 * if (scoreCurrentInfo != null && scoreCurrentInfo.isCurrentBlock()) {
	 * 
	 * if (!currentScorerIdList.isEmpty()) { countSubQuery
	 * .append("AND t.latest_screen_scorer_id IN :CURRENT_SCORER_ID_LIST "); }
	 * 
	 * if (currentCategoryType != null && currentCategoryType == 2) {
	 * countSubQuery
	 * .append("AND t.grade_seq IS null AND t.pending_category_seq IS null "); }
	 * else if (currentCategoryType != null && currentCategoryType == 3) {
	 * 
	 * if (!currentGradeSeqList.isEmpty() && ArrayUtils.contains(
	 * scoreCurrentInfo .getCurrentGradeNum(), -1)) { countSubQuery
	 * .append("AND (t.grade_seq IN :CURRENT_GRADE_SEQ_LIST "); countSubQuery
	 * .append("OR t.latest_scoring_state = :NO_GRADE_SCORING_STATE) "); } else
	 * { if (!currentGradeSeqList.isEmpty()) { countSubQuery
	 * .append("AND t.grade_seq IN :CURRENT_GRADE_SEQ_LIST "); }
	 * 
	 * if (ArrayUtils.contains( scoreCurrentInfo .getCurrentGradeNum(), -1)) {
	 * countSubQuery
	 * .append("AND t.latest_scoring_state = :NO_GRADE_SCORING_STATE "); } } }
	 * else if (currentCategoryType != null && currentCategoryType == 4 &&
	 * !currentPendingCategorySeqList .isEmpty()) { countSubQuery
	 * .append("AND t.pending_category_seq IN :CURRENT_PENDING_CATEGORY_SEQ_LIST "
	 * ); }
	 * 
	 * if (!StringUtils .isBlank(currentIncludeCheckPoints) && !StringUtils
	 * .isBlank(currentExcludeCheckPoints)) { countSubQuery
	 * .append("AND (t.bit_value & :CURRENT_INCLUDE_BIT_VALUE) > 0 ");
	 * countSubQuery
	 * .append("AND (t.bit_value & :CURRENT_EXCLUDE_BIT_VALUE) = 0 "); } else {
	 * if (!StringUtils .isBlank(currentIncludeCheckPoints)) { countSubQuery
	 * .append("AND (t.bit_value & :CURRENT_INCLUDE_BIT_VALUE) > 0 "); } else if
	 * (!StringUtils .isBlank(currentExcludeCheckPoints)) { countSubQuery
	 * .append("AND (t.bit_value & :CURRENT_EXCLUDE_BIT_VALUE) = 0 "); } }
	 * 
	 * if (currentStateList != null && currentStateList.length > 0) {
	 * countSubQuery
	 * .append("AND t.latest_scoring_state IN :CURRENT_STATE_LIST "); }
	 * 
	 * if (currentUpdateStartDate != null) { countSubQuery
	 * .append("AND t.update_date >= :CURRENT_UPDATE_START_DATE "); }
	 * 
	 * if (currentUpdateEndDate != null) { countSubQuery
	 * .append("AND t.update_date <= :CURRENT_UPDATE_END_DATE "); } } else if
	 * (menuId .equals(WebAppConst.SCORE_SAMP_MENU_ID) || menuId
	 * .equals(WebAppConst.STATE_TRAN_MENU_ID) || menuId
	 * .equals(WebAppConst.FORCED_MENU_ID)) { countSubQuery
	 * .append("AND t.latest_scoring_state IN :CURRENT_STATE_LIST "); }
	 * 
	 * if ((scoreHistoryInfo == null || !scoreHistoryInfo .isHistoryBlock()) &&
	 * (scoreCurrentInfo == null || !scoreCurrentInfo .isCurrentBlock())) { if
	 * (menuId .equals(WebAppConst.REFERENCE_SAMP_MENU_ID)) { countSubQuery
	 * .append
	 * ("AND t.latest_scoring_state NOT IN :SPECIAL_SCORING_WAIT_STATES "); }
	 * else if (menuId .equals(WebAppConst.SCORE_SAMP_MENU_ID)) { countSubQuery
	 * .append("AND t.latest_scoring_state NOT IN :SPECIAL_SCORING_STATES "); }
	 * 
	 * } if (!(menuId .equals(WebAppConst.STATE_TRAN_MENU_ID) || menuId
	 * .equals(WebAppConst.FORCED_MENU_ID))) { countSubQuery
	 * .append("AND t1.scoring_state NOT IN :DUMMY_SCORING_STATES ");
	 * countSubQuery
	 * .append("AND t.latest_scoring_state NOT IN :DUMMY_SCORING_STATES "); }
	 * countSubQuery .append("AND t.valid_flag = :VALID_FLAG "); countSubQuery
	 * .append("AND t1.valid_flag = :VALID_FLAG) ");
	 * 
	 * query.append("AND RAND()<((:RECORD_COUNT_LIMIT" + "/");
	 * query.append(countSubQuery); query.append(")*10) "); }
	 * 
	 * query.append("AND tranDescScore.lock_flag = :UNLOCK ");
	 * 
	 * if (menuId.equals(WebAppConst.STATE_TRAN_MENU_ID)) {
	 * query.append("AND tranDescScore.inspect_flag = :INSPECT_FLAG "); }
	 * 
	 * if (menuId.equals(WebAppConst.SCORE_SAMP_MENU_ID)) {
	 * query.append("AND tranDescScore.sampling_flag IN :SAMPLING_FLAG_LIST ");
	 * }
	 * 
	 * if (!StringUtils.isBlank(answerFormNum)) {
	 * query.append("AND tranDescScore.answer_form_num = :ANSWER_FORM_NUM "); }
	 * 
	 * if (scoreHistoryInfo != null && scoreHistoryInfo.isHistoryBlock()) {
	 * 
	 * if (!historyScorerIdList.isEmpty()) { query.append(
	 * "AND tranDescScoreHistory.latest_screen_scorer_id IN :HISTORY_SCORER_ID_LIST "
	 * ); }
	 * 
	 * if (historyCategoryType != null && historyCategoryType == 2) {
	 * query.append(
	 * "AND tranDescScoreHistory.grade_seq IS null AND tranDescScoreHistory.pending_category_seq IS null "
	 * ); } else if (historyCategoryType != null && historyCategoryType == 3) {
	 * 
	 * if (!historyGradeSeqList.isEmpty() && ArrayUtils.contains(
	 * scoreHistoryInfo .getHistoryGradeNum(), -1)) { query.append(
	 * "AND (tranDescScoreHistory.grade_seq IN :HISTORY_GRADE_SEQ_LIST ");
	 * query.
	 * append("OR tranDescScoreHistory.scoring_state = :NO_GRADE_SCORING_STATE) "
	 * ); } else { if (!historyGradeSeqList.isEmpty()) {
	 * query.append("AND tranDescScoreHistory.grade_seq IN :HISTORY_GRADE_SEQ_LIST "
	 * ); }
	 * 
	 * if (ArrayUtils.contains( scoreHistoryInfo .getHistoryGradeNum(), -1)) {
	 * query
	 * .append("AND tranDescScoreHistory.scoring_state = :NO_GRADE_SCORING_STATE "
	 * ); } } } else if (historyCategoryType != null && historyCategoryType == 4
	 * && !historyPendingCategorySeqList .isEmpty()) { query.append(
	 * "AND tranDescScoreHistory.pending_category_seq IN :HISTORY_PENDING_CATEGORY_SEQ_LIST "
	 * ); }
	 * 
	 * if (!StringUtils .isBlank(historyIncludeCheckPoints) && !StringUtils
	 * .isBlank(historyExcludeCheckPoints)) { query.append(
	 * "AND (tranDescScoreHistory.bit_value & :HISTORY_INCLUDE_BIT_VALUE) > 0 "
	 * ); query.append(
	 * "AND (tranDescScoreHistory.bit_value & :HISTORY_EXCLUDE_BIT_VALUE) = 0 "
	 * ); } else { if (!StringUtils .isBlank(historyIncludeCheckPoints)) {
	 * query.append(
	 * "AND (tranDescScoreHistory.bit_value & :HISTORY_INCLUDE_BIT_VALUE) > 0 "
	 * ); } else if (!StringUtils .isBlank(historyExcludeCheckPoints)) {
	 * query.append
	 * ("AND (tranDescScoreHistory.bit_value & :HISTORY_EXCLUDE_BIT_VALUE) = 0 "
	 * ); } }
	 * 
	 * if (historyEventList != null && historyEventList.length > 0) {
	 * query.append
	 * ("AND tranDescScoreHistory.event_id IN :HISTORY_EVENT_LIST "); }
	 * 
	 * if (historyUpdateStartDate != null) { query.append(
	 * "AND tranDescScoreHistory.update_date >= :HISTORY_UPDATE_START_DATE "); }
	 * 
	 * if (historyUpdateEndDate != null) { query.append(
	 * "AND tranDescScoreHistory.update_date <= :HISTORY_UPDATE_END_DATE "); } }
	 * 
	 * if (scoreCurrentInfo != null && scoreCurrentInfo.isCurrentBlock()) {
	 * 
	 * if (!currentScorerIdList.isEmpty()) { query.append(
	 * "AND tranDescScore.latest_screen_scorer_id IN :CURRENT_SCORER_ID_LIST ");
	 * }
	 * 
	 * if (currentCategoryType != null && currentCategoryType == 2) {
	 * query.append(
	 * "AND tranDescScore.grade_seq IS null AND tranDescScore.pending_category_seq IS null "
	 * ); } else if (currentCategoryType != null && currentCategoryType == 3) {
	 * 
	 * if (!currentGradeSeqList.isEmpty() && ArrayUtils.contains(
	 * scoreCurrentInfo .getCurrentGradeNum(), -1)) {
	 * query.append("AND (tranDescScore.grade_seq IN :CURRENT_GRADE_SEQ_LIST ");
	 * query
	 * .append("OR tranDescScore.latest_scoring_state = :NO_GRADE_SCORING_STATE) "
	 * ); } else { if (!currentGradeSeqList.isEmpty()) {
	 * query.append("AND tranDescScore.grade_seq IN :CURRENT_GRADE_SEQ_LIST ");
	 * }
	 * 
	 * if (ArrayUtils.contains( scoreCurrentInfo .getCurrentGradeNum(), -1)) {
	 * query
	 * .append("AND tranDescScore.latest_scoring_state = :NO_GRADE_SCORING_STATE "
	 * ); } } } else if (currentCategoryType != null && currentCategoryType == 4
	 * && !currentPendingCategorySeqList .isEmpty()) { query.append(
	 * "AND tranDescScore.pending_category_seq IN :CURRENT_PENDING_CATEGORY_SEQ_LIST "
	 * ); }
	 * 
	 * if (!StringUtils .isBlank(currentIncludeCheckPoints) && !StringUtils
	 * .isBlank(currentExcludeCheckPoints)) { query.append(
	 * "AND (tranDescScore.bit_value & :CURRENT_INCLUDE_BIT_VALUE) > 0 ");
	 * query.
	 * append("AND (tranDescScore.bit_value & :CURRENT_EXCLUDE_BIT_VALUE) = 0 "
	 * ); } else { if (!StringUtils .isBlank(currentIncludeCheckPoints)) {
	 * query.
	 * append("AND (tranDescScore.bit_value & :CURRENT_INCLUDE_BIT_VALUE) > 0 "
	 * ); } else if (!StringUtils .isBlank(currentExcludeCheckPoints)) {
	 * query.append
	 * ("AND (tranDescScore.bit_value & :CURRENT_EXCLUDE_BIT_VALUE) = 0 "); } }
	 * 
	 * if (currentStateList != null && currentStateList.length > 0) {
	 * query.append
	 * ("AND tranDescScore.latest_scoring_state IN :CURRENT_STATE_LIST "); }
	 * 
	 * if (currentUpdateStartDate != null) {
	 * query.append("AND tranDescScore.update_date >= :CURRENT_UPDATE_START_DATE "
	 * ); }
	 * 
	 * if (currentUpdateEndDate != null) {
	 * query.append("AND tranDescScore.update_date <= :CURRENT_UPDATE_END_DATE "
	 * ); } } else if (menuId .equals(WebAppConst.SCORE_SAMP_MENU_ID) || menuId
	 * .equals(WebAppConst.STATE_TRAN_MENU_ID) || menuId
	 * .equals(WebAppConst.FORCED_MENU_ID)) {
	 * query.append("AND tranDescScore.latest_scoring_state IN :CURRENT_STATE_LIST "
	 * ); }
	 * 
	 * if ((scoreHistoryInfo == null || !scoreHistoryInfo .isHistoryBlock()) &&
	 * (scoreCurrentInfo == null || !scoreCurrentInfo .isCurrentBlock())) { if
	 * (menuId .equals(WebAppConst.REFERENCE_SAMP_MENU_ID)) { query.append(
	 * "AND tranDescScore.latest_scoring_state NOT IN :SPECIAL_SCORING_WAIT_STATES "
	 * ); } else if (menuId .equals(WebAppConst.SCORE_SAMP_MENU_ID)) {
	 * query.append
	 * ("AND tranDescScore.latest_scoring_state NOT IN :SPECIAL_SCORING_STATES "
	 * ); }
	 * 
	 * } if (!(menuId.equals(WebAppConst.STATE_TRAN_MENU_ID) || menuId
	 * .equals(WebAppConst.FORCED_MENU_ID))) { query.append(
	 * "AND tranDescScoreHistory.scoring_state NOT IN :DUMMY_SCORING_STATES ");
	 * query.append(
	 * "AND tranDescScore.latest_scoring_state NOT IN :DUMMY_SCORING_STATES ");
	 * } query.append("AND tranDescScore.valid_flag = :VALID_FLAG ");
	 * query.append("AND tranDescScoreHistory.valid_flag = :VALID_FLAG ");
	 * query.append("GROUP BY tranDescScore.answer_seq "); if
	 * ((forceAndStateTransitionFlag != null) && (forceAndStateTransitionFlag ==
	 * WebAppConst.TRUE) && (menuId .equals(WebAppConst.FORCED_MENU_ID))) {
	 * query.append("ORDER BY tranDescScore.update_date DESC ");
	 * query.append("LIMIT "); if (endRecord != null) { if (startRecord > 0) {
	 * query.append(startRecord + " , " + endRecord); } else {
	 * query.append(endRecord + " "); } } } if
	 * (!(menuId.equals(WebAppConst.STATE_TRAN_MENU_ID) || menuId
	 * .equals(WebAppConst.FORCED_MENU_ID))) {
	 * query.append("LIMIT :RECORD_COUNT_LIMIT "); query.append(") score ");
	 * query.append("ORDER BY updateDate DESC "); } else if
	 * ((forceAndStateTransitionFlag != null) && (forceAndStateTransitionFlag ==
	 * WebAppConst.TRUE)) { query.append(") score "); if
	 * (!menuId.equals(WebAppConst.FORCED_MENU_ID)) {
	 * query.append("ORDER BY updateDate DESC "); } } else if
	 * ((forceAndStateTransitionFlag != null) && (forceAndStateTransitionFlag ==
	 * WebAppConst.FALSE)) { query.append(") score "); }
	 * 
	 * SQLQuery queryObj = session.createSQLQuery(query .toString());
	 * 
	 * if (menuId.equals(WebAppConst.STATE_TRAN_MENU_ID)) {
	 * queryObj.setParameter("INSPECT_FLAG", WebAppConst.F); }
	 * 
	 * if (menuId.equals(WebAppConst.SCORE_SAMP_MENU_ID)) { if (samplingFlag ==
	 * WebAppConst.TRUE) { queryObj.setParameterList( "SAMPLING_FLAG_LIST",
	 * WebAppConst.SCORE_SAMPLING_FLAG_LIST); } else {
	 * queryObj.setParameterList( "SAMPLING_FLAG_LIST", ArrayUtils .subarray(
	 * WebAppConst.SCORE_SAMPLING_FLAG_LIST, 0, 1)); } }
	 * 
	 * if (!StringUtils.isBlank(answerFormNum)) {
	 * queryObj.setParameter("ANSWER_FORM_NUM", answerFormNum); }
	 * 
	 * setHistoryParameters(queryObj);
	 * 
	 * setCurrentParameters(queryObj);
	 * 
	 * queryObj.setInteger("QUESTION_SEQ", questionInfo.getQuestionSeq());
	 * queryObj.setCharacter("UNLOCK", WebAppConst.UNLOCK);
	 * queryObj.setCharacter("VALID_FLAG", WebAppConst.VALID_FLAG); if
	 * (!(menuId.equals(WebAppConst.STATE_TRAN_MENU_ID) || menuId
	 * .equals(WebAppConst.FORCED_MENU_ID))) {
	 * queryObj.setInteger("RECORD_COUNT_LIMIT",
	 * scoreInputInfo.getResultCount()); }
	 * 
	 * if ((scoreHistoryInfo == null || !scoreHistoryInfo .isHistoryBlock()) &&
	 * (scoreCurrentInfo == null || !scoreCurrentInfo .isCurrentBlock())) { if
	 * (menuId .equals(WebAppConst.REFERENCE_SAMP_MENU_ID)) {
	 * queryObj.setParameterList( "SPECIAL_SCORING_WAIT_STATES",
	 * WebAppConst.SPECIAL_SCORING_WAIT_STATES); } else if (menuId
	 * .equals(WebAppConst.SCORE_SAMP_MENU_ID)) { queryObj.setParameterList(
	 * "SPECIAL_SCORING_STATES", WebAppConst.SPECIAL_SCORING_STATES); }
	 * 
	 * } if (!(menuId.equals(WebAppConst.STATE_TRAN_MENU_ID) || menuId
	 * .equals(WebAppConst.FORCED_MENU_ID))) { queryObj.setParameterList(
	 * "DUMMY_SCORING_STATES", WebAppConst.DUMMY_SCORING_STATES); }
	 * 
	 * return queryObj.list();
	 * 
	 * }
	 * 
	 * public void setCurrentParameters(SQLQuery queryObj) {
	 * 
	 * if (scoreCurrentInfo != null && scoreCurrentInfo.isCurrentBlock()) { if
	 * (!currentScorerIdList.isEmpty()) { queryObj.setParameterList(
	 * "CURRENT_SCORER_ID_LIST", currentScorerIdList); }
	 * 
	 * if (currentCategoryType != null && currentCategoryType == 3) {
	 * 
	 * if (!currentGradeSeqList.isEmpty()) { queryObj.setParameterList(
	 * "CURRENT_GRADE_SEQ_LIST", currentGradeSeqList); }
	 * 
	 * if (ArrayUtils.contains(scoreCurrentInfo .getCurrentGradeNum(), -1)) {
	 * queryObj.setShort( "NO_GRADE_SCORING_STATE",
	 * WebAppConst.NO_GRADE_SCORING_STATE); } } else if (currentCategoryType !=
	 * null && currentCategoryType == 4 && !currentPendingCategorySeqList
	 * .isEmpty()) {
	 * 
	 * queryObj.setParameterList( "CURRENT_PENDING_CATEGORY_SEQ_LIST",
	 * scoreCurrentInfo .getCurrentPendingCategorySeqList()); }
	 * 
	 * if (!StringUtils .isBlank(currentIncludeCheckPoints) && !StringUtils
	 * .isBlank(currentExcludeCheckPoints)) { queryObj.setInteger(
	 * "CURRENT_INCLUDE_BIT_VALUE", scoreCurrentInfo
	 * .getCurrentIncludeBitValue()); queryObj.setInteger(
	 * "CURRENT_EXCLUDE_BIT_VALUE", scoreCurrentInfo
	 * .getCurrentExcludeBitValue()); } else { if (!StringUtils
	 * .isBlank(currentIncludeCheckPoints)) { queryObj.setInteger(
	 * "CURRENT_INCLUDE_BIT_VALUE", scoreCurrentInfo
	 * .getCurrentIncludeBitValue()); } else if (!StringUtils
	 * .isBlank(currentExcludeCheckPoints)) { queryObj.setInteger(
	 * "CURRENT_EXCLUDE_BIT_VALUE", scoreCurrentInfo
	 * .getCurrentExcludeBitValue()); } }
	 * 
	 * if (currentStateList != null && currentStateList.length > 0) {
	 * queryObj.setParameterList( "CURRENT_STATE_LIST", scoreCurrentInfo
	 * .getCurrentStateList()); }
	 * 
	 * if (currentUpdateStartDate != null) { queryObj.setParameter(
	 * "CURRENT_UPDATE_START_DATE", currentUpdateStartDate); }
	 * 
	 * if (currentUpdateEndDate != null) { queryObj.setParameter(
	 * "CURRENT_UPDATE_END_DATE", currentUpdateEndDate); } } else if (menuId
	 * .equals(WebAppConst.SCORE_SAMP_MENU_ID)) { queryObj.setParameterList(
	 * "CURRENT_STATE_LIST", WebAppConst.SCORE_SAMPLING_CURRENT_STATES); } else
	 * if (menuId .equals(WebAppConst.STATE_TRAN_MENU_ID)) {
	 * queryObj.setParameterList( "CURRENT_STATE_LIST",
	 * WebAppConst.STATE_TRANSITION_CURRENT_STATES); } else if (menuId
	 * .equals(WebAppConst.FORCED_MENU_ID)) { queryObj.setParameterList(
	 * "CURRENT_STATE_LIST", WebAppConst.FORCED_SCORING_CURRENT_STATES); }
	 * 
	 * }
	 * 
	 * public void setHistoryParameters(SQLQuery queryObj) {
	 * 
	 * if (scoreHistoryInfo != null && scoreHistoryInfo.isHistoryBlock()) {
	 * 
	 * if (!historyScorerIdList.isEmpty()) { queryObj.setParameterList(
	 * "HISTORY_SCORER_ID_LIST", historyScorerIdList); }
	 * 
	 * if (historyCategoryType != null && historyCategoryType == 3) {
	 * 
	 * if (!historyGradeSeqList.isEmpty()) { queryObj.setParameterList(
	 * "HISTORY_GRADE_SEQ_LIST", historyGradeSeqList); }
	 * 
	 * if (ArrayUtils.contains(scoreHistoryInfo .getHistoryGradeNum(), -1)) {
	 * queryObj.setShort( "NO_GRADE_SCORING_STATE",
	 * WebAppConst.NO_GRADE_SCORING_STATE); } } else if (historyCategoryType !=
	 * null && historyCategoryType == 4 && !historyPendingCategorySeqList
	 * .isEmpty()) {
	 * 
	 * queryObj.setParameterList( "HISTORY_PENDING_CATEGORY_SEQ_LIST",
	 * historyPendingCategorySeqList); }
	 * 
	 * if (!StringUtils .isBlank(historyIncludeCheckPoints) && !StringUtils
	 * .isBlank(historyExcludeCheckPoints)) { queryObj.setInteger(
	 * "HISTORY_INCLUDE_BIT_VALUE", scoreHistoryInfo
	 * .getHistoryIncludeBitValue()); queryObj.setInteger(
	 * "HISTORY_EXCLUDE_BIT_VALUE", scoreHistoryInfo
	 * .getHistoryExcludeBitValue()); } else { if (!StringUtils
	 * .isBlank(historyIncludeCheckPoints)) { queryObj.setInteger(
	 * "HISTORY_INCLUDE_BIT_VALUE", scoreHistoryInfo
	 * .getHistoryIncludeBitValue()); } else if (!StringUtils
	 * .isBlank(historyExcludeCheckPoints)) { queryObj.setInteger(
	 * "HISTORY_EXCLUDE_BIT_VALUE", scoreHistoryInfo
	 * .getHistoryExcludeBitValue()); } }
	 * 
	 * if (historyEventList != null && historyEventList.length > 0) {
	 * queryObj.setParameterList( "HISTORY_EVENT_LIST", historyEventList); }
	 * 
	 * if (historyUpdateStartDate != null) { queryObj.setParameter(
	 * "HISTORY_UPDATE_START_DATE", historyUpdateStartDate); }
	 * 
	 * if (historyUpdateEndDate != null) { queryObj.setParameter(
	 * "HISTORY_UPDATE_END_DATE", historyUpdateEndDate); }
	 * 
	 * }
	 * 
	 * } }); System.out.println(">>>>>>>>>>>>>> searchAnswerRecords end: " + new
	 * Date().getTime()); return answerRecordsList; } catch (RuntimeException
	 * re) { throw re; } }
	 */

	// ReferenceSampling & ScoreSampling: get random answer sequence only using
	// Percentage rand() query: merged rand() and count.
	@SuppressWarnings("rawtypes")
	@Override
	public List searchAnswerRecords(final QuestionInfo questionInfo,
			final ScoreInputInfo scoreInputInfo,
			final Boolean forceAndStateTransitionFlag,
			final Integer startRecord, final Integer endRecord,
			final String orderByRandAttempt) {
		
		Map<String, String> configMap = SaitenUtil.getConfigMap();
		final boolean searchByScorerRoleId = Boolean
				.valueOf(configMap.get("search.by.scorer_role_id"));
		
		final ScoreHistoryInfo scoreHistoryInfo = scoreInputInfo
				.getScoreHistoryInfo();

		final String menuId = questionInfo.getMenuId();
		final boolean samplingFlag = scoreInputInfo.isSamplingFlag();
		final String answerFormNum = scoreInputInfo.getAnswerFormNum();

		final List<String> historyScorerIdList = scoreHistoryInfo != null ? scoreHistoryInfo
				.getHistoryScorerIdList() : null;
		final Integer historyCategoryType = scoreHistoryInfo != null ? scoreHistoryInfo
				.getHistoryCategoryType() : null;
		final List historyGradeSeqList = scoreHistoryInfo != null ? scoreHistoryInfo
				.getHistoryGradeSeqList() : null;
		final List historyPendingCategorySeqList = scoreHistoryInfo != null ? scoreHistoryInfo
				.getHistoryPendingCategorySeqList() : null;
		final List historyDenyCategorySeqList = scoreHistoryInfo != null ? scoreHistoryInfo
				.getHistoryDenyCategorySeqList() : null;
		final String historyIncludeCheckPoints = scoreHistoryInfo != null ? scoreHistoryInfo
				.getHistoryIncludeCheckPoints() : null;
		final String historyExcludeCheckPoints = scoreHistoryInfo != null ? scoreHistoryInfo
				.getHistoryExcludeCheckPoints() : null;
		final Short[] historyEventList = scoreHistoryInfo != null ? scoreHistoryInfo
				.getHistoryEventList() : null;
		final Date historyUpdateStartDate = scoreHistoryInfo != null ? scoreHistoryInfo
				.getHistoryUpdateStartDate() : null;
		final Date historyUpdateEndDate = scoreHistoryInfo != null ? scoreHistoryInfo
				.getHistoryUpdateEndDate() : null;
		final Integer[] historyScorerRoles = scoreHistoryInfo != null ? scoreHistoryInfo.getHistoryScorerRole() : null;

		final ScoreCurrentInfo scoreCurrentInfo = scoreInputInfo
				.getScoreCurrentInfo();

		final String punchText = scoreCurrentInfo != null ? scoreCurrentInfo
				.getPunchText() : null;
		final String punchTextCondition = scoreCurrentInfo != null ? scoreCurrentInfo
				.getPunchTextData() : null;
		final List<String> currentScorerIdList = scoreCurrentInfo != null ? scoreCurrentInfo
				.getCurrentScorerIdList() : null;
		final Integer currentCategoryType = scoreCurrentInfo != null ? scoreCurrentInfo
				.getCurrentCategoryType() : null;
		final List currentGradeSeqList = scoreCurrentInfo != null ? scoreCurrentInfo
				.getCurrentGradeSeqList() : null;
		final List currentPendingCategorySeqList = scoreCurrentInfo != null ? scoreCurrentInfo
				.getCurrentPendingCategorySeqList() : null;
		final List currentDenyCategorySeqList = scoreCurrentInfo != null ? scoreCurrentInfo
				.getCurrentDenyCategorySeqList() : null;
		final String currentIncludeCheckPoints = scoreCurrentInfo != null ? scoreCurrentInfo
				.getCurrentIncludeCheckPoints() : null;
		final String currentExcludeCheckPoints = scoreCurrentInfo != null ? scoreCurrentInfo
				.getCurrentExcludeCheckPoints() : null;
		final Short[] currentStateList = scoreCurrentInfo != null ? scoreCurrentInfo
				.getCurrentStateList() : null;
		final Date currentUpdateStartDate = scoreCurrentInfo != null ? scoreCurrentInfo
				.getCurrentUpdateStartDate() : null;
		final Date currentUpdateEndDate = scoreCurrentInfo != null ? scoreCurrentInfo
				.getCurrentUpdateEndDate() : null;
		final Integer[] currentScorerRoles = scoreCurrentInfo != null ? scoreCurrentInfo.getCurrentScorerRole() : null;

		/*
		 * System.out.println(">>>>>>>>>>>>>> searchAnswerRecords start: " + new
		 * Date().getTime());
		 */
		try {
			List answerRecordsList = getHibernateTemplate(
					questionInfo.getConnectionString()).execute(
					new HibernateCallback<List>() {

						public List doInHibernate(Session session)
								throws HibernateException {
							StringBuilder query = new StringBuilder();
							if ((forceAndStateTransitionFlag == null)
									|| (forceAndStateTransitionFlag == WebAppConst.TRUE)) {
								query.append("SELECT t.answer_seq AS answerSeq, t.answer_form_num AS answerFormNum, ");
								query.append("t.image_file_name AS imageFileName, t.grade_seq AS gradeSeq, ");
								query.append("t.bit_value AS bitValue, t.latest_scorer_id AS latestScorerId, ");
								if (!(menuId
										.equals(WebAppConst.STATE_TRAN_MENU_ID) || menuId
										.equals(WebAppConst.FORCED_MENU_ID))) {
									query.append("(SELECT scorer_comment FROM tran_desc_score_history AS scoreHistory ");
									query.append("WHERE scoreHistory.answer_seq = score.answerSeq ");
									query.append("AND scoreHistory.update_date = ");
									query.append("(SELECT max(history.update_date) FROM tran_desc_score_history history ");
									query.append("WHERE history.answer_seq = scoreHistory.answer_seq and history.valid_flag = 'T') AND scoreHistory.valid_flag = 'T') AS scorerComment, ");
								}
								query.append("t.punch_text AS punchText, ");
								query.append("t.pending_category_seq AS pendingCategorySeq, t.latest_scoring_state, ");
								query.append("t.deny_category_seq AS denyCategorySeq, ");
								if (((forceAndStateTransitionFlag != null) && (forceAndStateTransitionFlag == WebAppConst.TRUE))
										|| (menuId
												.equals(WebAppConst.REFERENCE_SAMP_MENU_ID) && (forceAndStateTransitionFlag == null))) {
									query.append("t.question_seq AS questionSeq, t.latest_screen_scorer_id AS latestScreenScorerId, ");
								}

								// here is count means no of comments.
								if (menuId.equals(WebAppConst.FORCED_MENU_ID)
										&& (forceAndStateTransitionFlag != null && forceAndStateTransitionFlag == WebAppConst.TRUE)) {
									query.append("(SELECT count(*) FROM tran_desc_score_history AS scoreHistory ");
									query.append("WHERE scoreHistory.answer_seq = score.answerSeq ");
									query.append("AND scoreHistory.question_seq = :QUESTION_SEQ ");
									query.append("AND scoreHistory.scorer_comment != '' AND scoreHistory.scorer_comment is not null ) AS commentCount, ");
								}
								query.append("t.mark_value AS markValue, ");
								query.append("t.update_date AS updateDate ");
								if (menuId.equals(WebAppConst.FORCED_MENU_ID)) {
									query.append(", score.lookAftSeq AS lookAftSeq ");
								}
								if(menuId.equals(WebAppConst.SCORE_SAMP_MENU_ID)){
									query.append(", t.latest_screen_scorer_id AS latestScreenScorerId ");
									query.append(", t.second_latest_screen_scorer_id AS secondLatestScreenScorerId ");
								}
							} else {
								// getting count to display on countPage, for
								// FORCED_SCORING and STATE_TRANSITION.
								query.append("SELECT SUM(rowCount) ");
								query.append("FROM (SELECT count(distinct tranDescScore.answer_seq) as rowCount ");
							}
							if ((forceAndStateTransitionFlag == null)
									|| (forceAndStateTransitionFlag == WebAppConst.TRUE)) {
								query.append("FROM tran_desc_score AS t, ");
								query.append("(SELECT tranDescScore.answer_seq AS answerSeq ");
								if (menuId.equals(WebAppConst.FORCED_MENU_ID)) {
									query.append(", tranLookAfterwards.look_aft_seq AS lookAftSeq ");
								}
							}

							query.append("FROM tran_desc_score AS tranDescScore ");

							query.append("INNER JOIN ");
							query.append("tran_desc_score_history AS tranDescScoreHistory ");
							query.append("ON tranDescScore.answer_seq = tranDescScoreHistory.answer_seq ");
							if (menuId.equals(WebAppConst.FORCED_MENU_ID)) {
								if (scoreCurrentInfo != null
										&& scoreCurrentInfo
												.isLookAfterwardsFlag()) {
									query.append("INNER JOIN tran_look_afterwards AS tranLookAfterwards ");
								} else {
									query.append("LEFT JOIN tran_look_afterwards AS tranLookAfterwards ");
								}
								query.append("ON tranDescScore.answer_seq = tranLookAfterwards.answer_seq ");
							}
							
							query.append("WHERE tranDescScore.question_seq  = :QUESTION_SEQ ");
							query.append("AND tranDescScoreHistory.question_seq  = :QUESTION_SEQ ");
							
							if(searchByScorerRoleId==true){
								if(currentScorerRoles != null){
									query.append("AND tranDescScore.answer_seq = ");
									query.append("( SELECT answer_seq FROM tran_desc_score_history his1 ");
									query.append("WHERE his1.history_seq = ");
									query.append("( SELECT max(history_seq) FROM tran_desc_score_history his2 ");
									query.append("WHERE his2.answer_seq = tranDescScore.answer_seq ");
									query.append("AND his2.scorer_role_id IN ( :CURRENT_SCORER_ROLES ) ");
									query.append(") ");
									query.append(") ");								
								}
							}
							
							if (!(menuId.equals(WebAppConst.STATE_TRAN_MENU_ID) || menuId
									.equals(WebAppConst.FORCED_MENU_ID))
									&& (orderByRandAttempt
											.equals(WebAppConst.ORDER_BY_ATTEMPT1))) {
								query.append("AND tranDescScore.answer_seq like CONCAT('%',(FLOOR(RAND()*10))) ");
							} else if (!(menuId
									.equals(WebAppConst.STATE_TRAN_MENU_ID) || menuId
									.equals(WebAppConst.FORCED_MENU_ID))
									&& (orderByRandAttempt
											.equals(WebAppConst.ORDER_BY_ATTEMPT2))) {
								query.append("AND tranDescScore.answer_seq like CONCAT('%',(FLOOR(RAND()*10)),'%') ");
							} else if (!(menuId
									.equals(WebAppConst.STATE_TRAN_MENU_ID) || menuId
									.equals(WebAppConst.FORCED_MENU_ID))
									&& (orderByRandAttempt
											.equals(WebAppConst.ORDER_BY_ATTEMPT3))) {
								StringBuilder countSubQuery = new StringBuilder();
								countSubQuery.append("(SELECT count(*) ");
								countSubQuery
										.append("FROM tran_desc_score AS t ");
								countSubQuery.append("INNER JOIN ");
								countSubQuery
										.append("tran_desc_score_history AS t1 ");
								countSubQuery
										.append("ON t.answer_seq = t1.answer_seq ");
								if (menuId.equals(WebAppConst.FORCED_MENU_ID)) {
									if (scoreCurrentInfo != null
											&& scoreCurrentInfo
													.isLookAfterwardsFlag()) {
										countSubQuery
												.append("INNER JOIN tran_look_afterwards AS lft ");
									} else {
										countSubQuery
												.append("LEFT JOIN tran_look_afterwards AS lft ");
									}
									countSubQuery
											.append("ON t.answer_seq = lft.answer_seq ");
								}
								countSubQuery
										.append("WHERE t.question_seq  = :QUESTION_SEQ ");
								countSubQuery
										.append("AND t1.question_seq  = :QUESTION_SEQ ");
								if(searchByScorerRoleId==true){
									if(currentScorerRoles != null){
										countSubQuery.append("AND t.answer_seq = ");
										countSubQuery.append("( SELECT answer_seq FROM tran_desc_score_history his1 ");
										countSubQuery.append("WHERE his1.history_seq = ");
										countSubQuery.append("( SELECT max(history_seq) FROM tran_desc_score_history his2 ");
										countSubQuery.append("WHERE his2.answer_seq = t.answer_seq ");
										countSubQuery.append("AND his2.scorer_role_id IN ( :CURRENT_SCORER_ROLES ) ");
										countSubQuery.append(") ");
										countSubQuery.append(") ");								
									}									
								}
								countSubQuery
										.append("AND t.lock_flag = :UNLOCK ");
								if (menuId
										.equals(WebAppConst.STATE_TRAN_MENU_ID)) {
									countSubQuery
											.append("AND t.inspect_flag = :INSPECT_FLAG ");
									/*
									 * if (WebAppConst.SPEAKING_TYPE
									 * .equals(questionInfo .getQuestionType())
									 * || WebAppConst.WRITING_TYPE
									 * .equals(questionInfo .getQuestionType()))
									 * {
									 */
									countSubQuery
											.append("AND t.quality_check_flag = :QUALITY_FLAG ");
									/* } */
								}

								if (menuId
										.equals(WebAppConst.SCORE_SAMP_MENU_ID)) {
									countSubQuery
											.append("AND t.sampling_flag IN :SAMPLING_FLAG_LIST ");
									/*
									 * if (WebAppConst.SPEAKING_TYPE
									 * .equals(questionInfo .getQuestionType())
									 * || WebAppConst.WRITING_TYPE
									 * .equals(questionInfo .getQuestionType()))
									 * {
									 */
									countSubQuery
											.append("AND t.quality_check_flag = :QUALITY_FLAG ");
									/* } */
								}

								if (!StringUtils.isBlank(answerFormNum)) {
									countSubQuery
											.append("AND t.answer_form_num = :ANSWER_FORM_NUM ");
								}

								if (scoreHistoryInfo != null
										&& scoreHistoryInfo.isHistoryBlock()) {

									if (!historyScorerIdList.isEmpty()) {
										countSubQuery
												.append("AND t1.latest_screen_scorer_id IN :HISTORY_SCORER_ID_LIST ");
									}
									// This is for Quality Check Flag
									/*
									 * if (WebAppConst.SPEAKING_TYPE
									 * .equals(questionInfo .getQuestionType())
									 * || WebAppConst.WRITING_TYPE
									 * .equals(questionInfo .getQuestionType()))
									 * {
									 */
									if (scoreHistoryInfo
											.isHistoryQualityCheckFlag()) {
										countSubQuery
												.append("AND t.quality_check_flag IN :QUALITY_CHECK_FLAG ");
									}

									/* } */

									if (historyCategoryType != null
											&& historyCategoryType == 2) {
										countSubQuery
												.append("AND t1.grade_seq IS null AND t1.pending_category_seq IS null ");
									} else if (historyCategoryType != null
											&& historyCategoryType == 3) {

										if (!historyGradeSeqList.isEmpty()
												&& ArrayUtils.contains(
														scoreHistoryInfo
																.getHistoryGradeNum(),
														-1)) {
											countSubQuery
													.append("AND (t1.grade_seq IN :HISTORY_GRADE_SEQ_LIST ");
											countSubQuery
													.append("OR t1.scoring_state = :NO_GRADE_SCORING_STATE) ");
										} else {
											if (!historyGradeSeqList.isEmpty()) {
												countSubQuery
														.append("AND t1.grade_seq IN :HISTORY_GRADE_SEQ_LIST ");
											}

											if (ArrayUtils.contains(
													scoreHistoryInfo
															.getHistoryGradeNum(),
													-1)) {
												countSubQuery
														.append("AND t1.scoring_state = :NO_GRADE_SCORING_STATE ");
											}
										}
									} else if (historyCategoryType != null
											&& historyCategoryType == 4
											&& !historyPendingCategorySeqList
													.isEmpty()) {
										countSubQuery
												.append("AND t1.pending_category_seq IN :HISTORY_PENDING_CATEGORY_SEQ_LIST ");
									} else if (historyCategoryType != null
											&& historyCategoryType == 5
											&& !historyDenyCategorySeqList
													.isEmpty()) {
										countSubQuery
												.append("AND t1.deny_category_seq IN :HISTORY_DENY_CATEGORY_SEQ_LIST ");
									}

									if (!StringUtils
											.isBlank(historyIncludeCheckPoints)
											&& !StringUtils
													.isBlank(historyExcludeCheckPoints)) {
										countSubQuery
												.append("AND (t1.bit_value & :HISTORY_INCLUDE_BIT_VALUE) > 0 ");
										countSubQuery
												.append("AND (t1.bit_value & :HISTORY_EXCLUDE_BIT_VALUE) = 0 ");
									} else {
										if (!StringUtils
												.isBlank(historyIncludeCheckPoints)) {
											countSubQuery
													.append("AND (t1.bit_value & :HISTORY_INCLUDE_BIT_VALUE) > 0 ");
										} else if (!StringUtils
												.isBlank(historyExcludeCheckPoints)) {
											countSubQuery
													.append("AND (t1.bit_value & :HISTORY_EXCLUDE_BIT_VALUE) = 0 ");
										}
									}

									if (historyEventList != null
											&& historyEventList.length > 0) {
										countSubQuery
												.append("AND t1.event_id IN :HISTORY_EVENT_LIST ");
									}

									if (historyUpdateStartDate != null) {
										countSubQuery
												.append("AND t1.update_date >= :HISTORY_UPDATE_START_DATE ");
									}

									if (historyUpdateEndDate != null) {
										countSubQuery
												.append("AND t1.update_date <= :HISTORY_UPDATE_END_DATE ");
									}
								}

								if (scoreCurrentInfo != null
										&& scoreCurrentInfo.isCurrentBlock()) {

									if (!StringUtils.isBlank(punchText)) {
										countSubQuery
												.append("AND t.punch_text like :PUNCH_TEXT escape '" +WebAppConst.ESCAPE_CHARACTER + "'");
									}

									if (!currentScorerIdList.isEmpty()) {
										countSubQuery
												.append("AND t.latest_screen_scorer_id IN :CURRENT_SCORER_ID_LIST ");
									}

									// This is for Quality Check Flag
									/*
									 * if (WebAppConst.SPEAKING_TYPE
									 * .equals(questionInfo .getQuestionType())
									 * || WebAppConst.WRITING_TYPE
									 * .equals(questionInfo .getQuestionType()))
									 * {
									 */
									if (scoreCurrentInfo
											.isCurrentQualityCheckFlag()) {
										countSubQuery
												.append("AND t.quality_check_flag IN :QUALITY_CHECK_FLAG ");
									}

									/* } */

									if (currentCategoryType != null
											&& currentCategoryType == 2) {
										countSubQuery
												.append("AND t.grade_seq IS null AND t.pending_category_seq IS null ");
									} else if (currentCategoryType != null
											&& currentCategoryType == 3) {

										if (!currentGradeSeqList.isEmpty()
												&& ArrayUtils.contains(
														scoreCurrentInfo
																.getCurrentGradeNum(),
														-1)) {
											countSubQuery
													.append("AND (t.grade_seq IN :CURRENT_GRADE_SEQ_LIST ");
											countSubQuery
													.append("OR t.latest_scoring_state = :NO_GRADE_SCORING_STATE) ");
										} else {
											if (!currentGradeSeqList.isEmpty()) {
												countSubQuery
														.append("AND t.grade_seq IN :CURRENT_GRADE_SEQ_LIST ");
											}

											if (ArrayUtils.contains(
													scoreCurrentInfo
															.getCurrentGradeNum(),
													-1)) {
												countSubQuery
														.append("AND t.latest_scoring_state = :NO_GRADE_SCORING_STATE ");
											}
										}
									} else if (currentCategoryType != null
											&& currentCategoryType == 4
											&& !currentPendingCategorySeqList
													.isEmpty()) {
										countSubQuery
												.append("AND t.pending_category_seq IN :CURRENT_PENDING_CATEGORY_SEQ_LIST ");
									} else if (currentCategoryType != null
											&& currentCategoryType == 5
											&& !currentDenyCategorySeqList
													.isEmpty()) {
										countSubQuery
												.append("AND t.deny_category_seq IN :CURRENT_DENY_CATEGORY_SEQ_LIST ");
									}

									if (!StringUtils
											.isBlank(currentIncludeCheckPoints)
											&& !StringUtils
													.isBlank(currentExcludeCheckPoints)) {
										countSubQuery
												.append("AND (t.bit_value & :CURRENT_INCLUDE_BIT_VALUE) > 0 ");
										countSubQuery
												.append("AND (t.bit_value & :CURRENT_EXCLUDE_BIT_VALUE) = 0 ");
									} else {
										if (!StringUtils
												.isBlank(currentIncludeCheckPoints)) {
											countSubQuery
													.append("AND (t.bit_value & :CURRENT_INCLUDE_BIT_VALUE) > 0 ");
										} else if (!StringUtils
												.isBlank(currentExcludeCheckPoints)) {
											countSubQuery
													.append("AND (t.bit_value & :CURRENT_EXCLUDE_BIT_VALUE) = 0 ");
										}
									}

									if (currentStateList != null
											&& currentStateList.length > 0) {
										countSubQuery
												.append("AND t.latest_scoring_state IN :CURRENT_STATE_LIST ");
									}

									if (currentUpdateStartDate != null) {
										countSubQuery
												.append("AND t.update_date >= :CURRENT_UPDATE_START_DATE ");
									}

									if (currentUpdateEndDate != null) {
										countSubQuery
												.append("AND t.update_date <= :CURRENT_UPDATE_END_DATE ");
									}
								} else if (menuId
										.equals(WebAppConst.SCORE_SAMP_MENU_ID)
										|| menuId
												.equals(WebAppConst.STATE_TRAN_MENU_ID)
										|| menuId
												.equals(WebAppConst.FORCED_MENU_ID)) {
									countSubQuery
											.append("AND t.latest_scoring_state IN :CURRENT_STATE_LIST ");
								}

								if ((scoreHistoryInfo == null || !scoreHistoryInfo
										.isHistoryBlock())
										&& (scoreCurrentInfo == null || !scoreCurrentInfo
												.isCurrentBlock())) {
									if (menuId
											.equals(WebAppConst.REFERENCE_SAMP_MENU_ID)) {
										countSubQuery
												.append("AND t.latest_scoring_state NOT IN :SPECIAL_SCORING_AND_BATCH_STATES ");
									} else if (menuId
											.equals(WebAppConst.SCORE_SAMP_MENU_ID)) {
										countSubQuery
												.append("AND t.latest_scoring_state NOT IN :SPECIAL_SCORING_STATES ");
									}

								}
								if (!(menuId
										.equals(WebAppConst.STATE_TRAN_MENU_ID) || menuId
										.equals(WebAppConst.FORCED_MENU_ID))) {
									countSubQuery
											.append("AND t1.scoring_state NOT IN :DUMMY_SCORING_STATES ");
									countSubQuery
											.append("AND t.latest_scoring_state NOT IN :DUMMY_SCORING_STATES ");
								}

								if (menuId
										.equals(WebAppConst.REFERENCE_SAMP_MENU_ID)
										|| menuId
												.equals(WebAppConst.SCORE_SAMP_MENU_ID)
										|| menuId
												.equals(WebAppConst.STATE_TRAN_MENU_ID)) {
									countSubQuery
											.append("AND t.answer_paper_type NOT IN ( :ANSWER_PAPER_TYPES ) ");
									countSubQuery
											.append("AND ( SELECT count(*) from tran_desc_score_history th where pending_category in ( :PENDING_CATEGORIES ) AND t.answer_seq = th.answer_seq )<=0 ");
									countSubQuery
										.append("AND ( SELECT count(*) from tran_desc_score_history th where deny_category in ( :DENY_CATEGORIES ) AND t.answer_seq = th.answer_seq )<=0 ");
								}
								
								if(searchByScorerRoleId==true){
									if(historyScorerRoles != null){
										countSubQuery.append("AND t1.scorer_role_id IN :HISTORY_SCORER_ROLES ");
									}									
								}

								
								if (menuId.equals(WebAppConst.FORCED_MENU_ID)) {
									if (scoreCurrentInfo != null
											&& scoreCurrentInfo
													.isLookAfterwardsFlag()) {
										countSubQuery
												.append("AND lft.look_aft_flag = :VALID_FLAG ");
									}
								}

								countSubQuery
										.append("AND t.valid_flag = :VALID_FLAG ");
								countSubQuery
										.append("AND t1.valid_flag = :VALID_FLAG) ");

								query.append("AND RAND()<((:RECORD_COUNT_LIMIT"
										+ "/");
								query.append(countSubQuery);
								query.append(")*10) ");
							}
							
							query.append("AND tranDescScore.lock_flag = :UNLOCK ");

							if (menuId.equals(WebAppConst.STATE_TRAN_MENU_ID)) {
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

							if (menuId.equals(WebAppConst.SCORE_SAMP_MENU_ID)) {
								query.append("AND tranDescScore.sampling_flag IN :SAMPLING_FLAG_LIST ");
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

							if (scoreHistoryInfo != null
									&& scoreHistoryInfo.isHistoryBlock()) {

								if (!historyScorerIdList.isEmpty()) {
									query.append("AND tranDescScoreHistory.latest_screen_scorer_id IN :HISTORY_SCORER_ID_LIST ");
								}
								// This is for Quality Check Flag
								/*
								 * if (WebAppConst.SPEAKING_TYPE
								 * .equals(questionInfo.getQuestionType()) ||
								 * WebAppConst.WRITING_TYPE .equals(questionInfo
								 * .getQuestionType())) {
								 */
								if (scoreHistoryInfo
										.isHistoryQualityCheckFlag()) {
									query.append("AND tranDescScoreHistory.quality_check_flag IN :QUALITY_CHECK_FLAG ");
								}
								/* } */

								if (historyCategoryType != null
										&& historyCategoryType == 2) {
									query.append("AND tranDescScoreHistory.grade_seq IS null AND tranDescScoreHistory.pending_category_seq IS null ");
								} else if (historyCategoryType != null
										&& historyCategoryType == 3) {

									if (!historyGradeSeqList.isEmpty()
											&& ArrayUtils.contains(
													scoreHistoryInfo
															.getHistoryGradeNum(),
													-1)) {
										query.append("AND (tranDescScoreHistory.grade_seq IN :HISTORY_GRADE_SEQ_LIST ");
										query.append("OR tranDescScoreHistory.scoring_state = :NO_GRADE_SCORING_STATE) ");
									} else {
										if (!historyGradeSeqList.isEmpty()) {
											query.append("AND tranDescScoreHistory.grade_seq IN :HISTORY_GRADE_SEQ_LIST ");
										}

										if (ArrayUtils.contains(
												scoreHistoryInfo
														.getHistoryGradeNum(),
												-1)) {
											query.append("AND tranDescScoreHistory.scoring_state = :NO_GRADE_SCORING_STATE ");
										}
									}
								} else if (historyCategoryType != null
										&& historyCategoryType == 4
										&& !historyPendingCategorySeqList
												.isEmpty()) {
									query.append("AND tranDescScoreHistory.pending_category_seq IN :HISTORY_PENDING_CATEGORY_SEQ_LIST ");
								} else if (historyCategoryType != null
										&& historyCategoryType == 5
										&& !historyDenyCategorySeqList
												.isEmpty()) {
									query.append("AND tranDescScoreHistory.deny_category_seq IN :HISTORY_DENY_CATEGORY_SEQ_LIST ");
								}

								if (!StringUtils
										.isBlank(historyIncludeCheckPoints)
										&& !StringUtils
												.isBlank(historyExcludeCheckPoints)) {
									query.append("AND (tranDescScoreHistory.bit_value & :HISTORY_INCLUDE_BIT_VALUE) > 0 ");
									query.append("AND (tranDescScoreHistory.bit_value & :HISTORY_EXCLUDE_BIT_VALUE) = 0 ");
								} else {
									if (!StringUtils
											.isBlank(historyIncludeCheckPoints)) {
										query.append("AND (tranDescScoreHistory.bit_value & :HISTORY_INCLUDE_BIT_VALUE) > 0 ");
									} else if (!StringUtils
											.isBlank(historyExcludeCheckPoints)) {
										query.append("AND (tranDescScoreHistory.bit_value & :HISTORY_EXCLUDE_BIT_VALUE) = 0 ");
									}
								}

								if (historyEventList != null
										&& historyEventList.length > 0) {
									query.append("AND tranDescScoreHistory.event_id IN :HISTORY_EVENT_LIST ");
								}

								if (historyUpdateStartDate != null) {
									query.append("AND tranDescScoreHistory.update_date >= :HISTORY_UPDATE_START_DATE ");
								}

								if (historyUpdateEndDate != null) {
									query.append("AND tranDescScoreHistory.update_date <= :HISTORY_UPDATE_END_DATE ");
								}
							}

							if (scoreCurrentInfo != null
									&& scoreCurrentInfo.isCurrentBlock()) {

								if (!StringUtils.isBlank(punchText)) {
									query.append("AND tranDescScore.punch_text like :PUNCH_TEXT escape '" + WebAppConst.ESCAPE_CHARACTER + "'");
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
								if (scoreCurrentInfo
										.isCurrentQualityCheckFlag()) {
									query.append("AND tranDescScore.quality_check_flag IN :QUALITY_CHECK_FLAG ");
								}
								/* } */

								if (currentCategoryType != null
										&& currentCategoryType == 2) {
									query.append("AND tranDescScore.grade_seq IS null AND tranDescScore.pending_category_seq IS null ");
								} else if (currentCategoryType != null
										&& currentCategoryType == 3) {

									if (!currentGradeSeqList.isEmpty()
											&& ArrayUtils.contains(
													scoreCurrentInfo
															.getCurrentGradeNum(),
													-1)) {
										query.append("AND (tranDescScore.grade_seq IN :CURRENT_GRADE_SEQ_LIST ");
										query.append("OR tranDescScore.latest_scoring_state = :NO_GRADE_SCORING_STATE) ");
									} else {
										if (!currentGradeSeqList.isEmpty()) {
											query.append("AND tranDescScore.grade_seq IN :CURRENT_GRADE_SEQ_LIST ");
										}

										if (ArrayUtils.contains(
												scoreCurrentInfo
														.getCurrentGradeNum(),
												-1)) {
											query.append("AND tranDescScore.latest_scoring_state = :NO_GRADE_SCORING_STATE ");
										}
									}
								} else if (currentCategoryType != null
										&& currentCategoryType == 4
										&& !currentPendingCategorySeqList
												.isEmpty()) {
									query.append("AND tranDescScore.pending_category_seq IN :CURRENT_PENDING_CATEGORY_SEQ_LIST ");
								} else if (currentCategoryType != null
										&& currentCategoryType == 5
										&& !currentDenyCategorySeqList
												.isEmpty()) {
									query.append("AND tranDescScore.deny_category_seq IN :CURRENT_DENY_CATEGORY_SEQ_LIST ");
								}

								if (!StringUtils
										.isBlank(currentIncludeCheckPoints)
										&& !StringUtils
												.isBlank(currentExcludeCheckPoints)) {
									query.append("AND (tranDescScore.bit_value & :CURRENT_INCLUDE_BIT_VALUE) > 0 ");
									query.append("AND (tranDescScore.bit_value & :CURRENT_EXCLUDE_BIT_VALUE) = 0 ");
								} else {
									if (!StringUtils
											.isBlank(currentIncludeCheckPoints)) {
										query.append("AND (tranDescScore.bit_value & :CURRENT_INCLUDE_BIT_VALUE) > 0 ");
									} else if (!StringUtils
											.isBlank(currentExcludeCheckPoints)) {
										query.append("AND (tranDescScore.bit_value & :CURRENT_EXCLUDE_BIT_VALUE) = 0 ");
									}
								}

								if (currentStateList != null
										&& currentStateList.length > 0) {
									query.append("AND tranDescScore.latest_scoring_state IN :CURRENT_STATE_LIST ");
								}

								if (currentUpdateStartDate != null) {
									query.append("AND tranDescScore.update_date >= :CURRENT_UPDATE_START_DATE ");
								}

								if (currentUpdateEndDate != null) {
									query.append("AND tranDescScore.update_date <= :CURRENT_UPDATE_END_DATE ");
								}
							} else if (menuId
									.equals(WebAppConst.SCORE_SAMP_MENU_ID)
									|| menuId
											.equals(WebAppConst.STATE_TRAN_MENU_ID)
									|| menuId
											.equals(WebAppConst.FORCED_MENU_ID)) {
								query.append("AND tranDescScore.latest_scoring_state IN :CURRENT_STATE_LIST ");
							}

							if ((scoreHistoryInfo == null || !scoreHistoryInfo
									.isHistoryBlock())
									&& (scoreCurrentInfo == null || !scoreCurrentInfo
											.isCurrentBlock())) {
								if (menuId
										.equals(WebAppConst.REFERENCE_SAMP_MENU_ID)) {
									query.append("AND tranDescScore.latest_scoring_state NOT IN :SPECIAL_SCORING_AND_BATCH_STATES ");
								} else if (menuId
										.equals(WebAppConst.SCORE_SAMP_MENU_ID)) {
									query.append("AND tranDescScore.latest_scoring_state NOT IN :SPECIAL_SCORING_STATES ");
								}

							}
							if (!(menuId.equals(WebAppConst.STATE_TRAN_MENU_ID) || menuId
									.equals(WebAppConst.FORCED_MENU_ID))) {
								query.append("AND tranDescScoreHistory.scoring_state NOT IN :DUMMY_SCORING_STATES ");
								query.append("AND tranDescScore.latest_scoring_state NOT IN :DUMMY_SCORING_STATES ");
							}

							if (menuId
									.equals(WebAppConst.REFERENCE_SAMP_MENU_ID)
									|| menuId
											.equals(WebAppConst.SCORE_SAMP_MENU_ID)
									|| menuId
											.equals(WebAppConst.STATE_TRAN_MENU_ID)) {
								query.append("AND tranDescScore.answer_paper_type NOT IN ( :ANSWER_PAPER_TYPES ) ");
								query.append("AND ( SELECT count(*) from tran_desc_score_history th where pending_category in ( :PENDING_CATEGORIES ) AND tranDescScore.answer_seq = th.answer_seq )<=0 ");
								query.append("AND ( SELECT count(*) from tran_desc_score_history th where deny_category in ( :DENY_CATEGORIES ) AND tranDescScore.answer_seq = th.answer_seq )<=0 ");
							}

							if(searchByScorerRoleId==true){
								if(historyScorerRoles != null){
									query.append("AND tranDescScoreHistory.scorer_role_id IN :HISTORY_SCORER_ROLES ");
								}								
							}
							
							if (menuId.equals(WebAppConst.FORCED_MENU_ID)) {
								if (scoreCurrentInfo != null
										&& scoreCurrentInfo
												.isLookAfterwardsFlag()) {
									query.append("AND tranLookAfterwards.look_aft_flag = :VALID_FLAG ");
								}
							}
							
							query.append("AND tranDescScore.valid_flag = :VALID_FLAG ");
							query.append("AND tranDescScoreHistory.valid_flag = :VALID_FLAG ");
							if ((forceAndStateTransitionFlag != null)
									&& (forceAndStateTransitionFlag == WebAppConst.FALSE)) {
								query.append("GROUP BY tranDescScore.answer_seq ");
							} else {
								query.append("GROUP BY answerSeq ");
							}
							if ((forceAndStateTransitionFlag != null)
									&& (forceAndStateTransitionFlag == WebAppConst.TRUE)
									&& (menuId
											.equals(WebAppConst.FORCED_MENU_ID))) {
								query.append("ORDER BY tranDescScore.update_date DESC ");
								query.append("LIMIT ");
								if (endRecord != null) {
									if (startRecord > 0) {
										query.append(startRecord + " , "
												+ endRecord);
									} else {
										query.append(endRecord + " ");
									}
								}
							}
							if (!(menuId.equals(WebAppConst.STATE_TRAN_MENU_ID) || menuId
									.equals(WebAppConst.FORCED_MENU_ID))) {
								query.append("LIMIT :RECORD_COUNT_LIMIT ");
								query.append(") score ");
								query.append("WHERE t.answer_seq = score.answerSeq ");
								query.append("ORDER BY updateDate DESC ");
							} else if ((forceAndStateTransitionFlag != null)
									&& (forceAndStateTransitionFlag == WebAppConst.TRUE)) {
								query.append(") score ");
								if (!menuId.equals(WebAppConst.FORCED_MENU_ID)) {
									query.append("ORDER BY updateDate DESC ");
								}
							} else if ((forceAndStateTransitionFlag != null)
									&& (forceAndStateTransitionFlag == WebAppConst.FALSE)) {
								query.append(") score ");
							}

							SQLQuery queryObj = session.createSQLQuery(query
									.toString());

							if (menuId
									.equals(WebAppConst.REFERENCE_SAMP_MENU_ID)
									|| menuId
											.equals(WebAppConst.SCORE_SAMP_MENU_ID)
									|| menuId
											.equals(WebAppConst.STATE_TRAN_MENU_ID)) {
								queryObj.setParameterList("ANSWER_PAPER_TYPES",
										WebAppConst.ANSWER_PAPER_TYPES);
								queryObj.setParameterList("PENDING_CATEGORIES",
										WebAppConst.PENDING_CATEGORIES);
								queryObj.setParameterList("DENY_CATEGORIES",
										WebAppConst.PENDING_CATEGORIES);
							}

							if (menuId.equals(WebAppConst.STATE_TRAN_MENU_ID)) {
								queryObj.setParameter("INSPECT_FLAG",
										WebAppConst.F);
								/*
								 * if (WebAppConst.SPEAKING_TYPE
								 * .equals(questionInfo.getQuestionType()) ||
								 * WebAppConst.WRITING_TYPE .equals(questionInfo
								 * .getQuestionType())) {
								 */
								queryObj.setParameter("QUALITY_FLAG",
										WebAppConst.QUALITY_MARK_FLAG_FALSE);
								/* } */

							}

							if (menuId.equals(WebAppConst.SCORE_SAMP_MENU_ID)) {
								if (samplingFlag == WebAppConst.TRUE) {
									queryObj.setParameterList(
											"SAMPLING_FLAG_LIST",
											WebAppConst.SCORE_SAMPLING_FLAG_LIST);
								} else {
									queryObj.setParameterList(
											"SAMPLING_FLAG_LIST",
											ArrayUtils
													.subarray(
															WebAppConst.SCORE_SAMPLING_FLAG_LIST,
															0, 1));
								}
								/*
								 * if (WebAppConst.SPEAKING_TYPE
								 * .equals(questionInfo.getQuestionType()) ||
								 * WebAppConst.WRITING_TYPE .equals(questionInfo
								 * .getQuestionType())) {
								 */
								queryObj.setParameter("QUALITY_FLAG",
										WebAppConst.QUALITY_MARK_FLAG_FALSE);
								/* } */

							}

							if (!StringUtils.isBlank(answerFormNum)) {
								queryObj.setParameter("ANSWER_FORM_NUM",
										answerFormNum);
							}

							setHistoryParameters(queryObj);

							setCurrentParameters(queryObj);

							queryObj.setInteger("QUESTION_SEQ",
									questionInfo.getQuestionSeq());
							queryObj.setCharacter("UNLOCK", WebAppConst.UNLOCK);
							queryObj.setCharacter("VALID_FLAG",
									WebAppConst.VALID_FLAG);
							if (!(menuId.equals(WebAppConst.STATE_TRAN_MENU_ID) || menuId
									.equals(WebAppConst.FORCED_MENU_ID))) {
								queryObj.setInteger("RECORD_COUNT_LIMIT",
										scoreInputInfo.getResultCount());
							}

							if ((scoreHistoryInfo == null || !scoreHistoryInfo
									.isHistoryBlock())
									&& (scoreCurrentInfo == null || !scoreCurrentInfo
											.isCurrentBlock())) {
								if (menuId
										.equals(WebAppConst.REFERENCE_SAMP_MENU_ID)) {
									queryObj.setParameterList(
											"SPECIAL_SCORING_AND_BATCH_STATES",
											WebAppConst.SPECIAL_SCORING_AND_BATCH_STATES);
								} else if (menuId
										.equals(WebAppConst.SCORE_SAMP_MENU_ID)) {
									queryObj.setParameterList(
											"SPECIAL_SCORING_STATES",
											WebAppConst.SPECIAL_SCORING_STATES);
								}

							}
							if (!(menuId.equals(WebAppConst.STATE_TRAN_MENU_ID) || menuId
									.equals(WebAppConst.FORCED_MENU_ID))) {
								queryObj.setParameterList(
										"DUMMY_SCORING_STATES",
										WebAppConst.DUMMY_SCORING_STATES);
							}

							return queryObj.list();

						}

						public void setCurrentParameters(SQLQuery queryObj) {

							if (scoreCurrentInfo != null
									&& scoreCurrentInfo.isCurrentBlock()) {

								if (!StringUtils.isBlank(punchText)) {
									if(punchTextCondition == null || punchTextCondition.equals("0")) {
										queryObj.setParameter("PUNCH_TEXT",
												punchText);
									} else if(punchTextCondition.equals("1")) {
										queryObj.setParameter("PUNCH_TEXT",
												punchText+"%");
									} else if(punchTextCondition.equals("2")) {
										queryObj.setParameter("PUNCH_TEXT",
												"%"+punchText);
									} else if(punchTextCondition.equals("3")) {
										queryObj.setParameter("PUNCH_TEXT",
												"%"+punchText+"%");
									}
								}

								if (!currentScorerIdList.isEmpty()) {
									queryObj.setParameterList(
											"CURRENT_SCORER_ID_LIST",
											currentScorerIdList);
								}

								if (scoreCurrentInfo
										.isCurrentQualityCheckFlag()) {
									/*
									 * if (WebAppConst.SPEAKING_TYPE
									 * .equals(questionInfo .getQuestionType())
									 * || WebAppConst.WRITING_TYPE
									 * .equals(questionInfo .getQuestionType()))
									 * {
									 */
									queryObj.setParameterList(
											"QUALITY_CHECK_FLAG",
											ArrayUtils
													.subarray(
															WebAppConst.QUALITY_MARK_FLAG_LIST,
															0, 1));
									/* } */

								} /*
								 * else { if (WebAppConst.SPEAKING_TYPE
								 * .equals(questionInfo .getQuestionType()) ||
								 * WebAppConst.WRITING_TYPE .equals(questionInfo
								 * .getQuestionType())) {
								 * queryObj.setParameterList(
								 * "QUALITY_CHECK_FLAG",
								 * WebAppConst.QUALITY_MARK_FLAG_LIST); }
								 * 
								 * }
								 */

								if (currentCategoryType != null
										&& currentCategoryType == 3) {

									if (!currentGradeSeqList.isEmpty()) {
										queryObj.setParameterList(
												"CURRENT_GRADE_SEQ_LIST",
												currentGradeSeqList);
									}

									if (ArrayUtils.contains(scoreCurrentInfo
											.getCurrentGradeNum(), -1)) {
										queryObj.setShort(
												"NO_GRADE_SCORING_STATE",
												WebAppConst.NO_GRADE_SCORING_STATE);
									}
								} else if (currentCategoryType != null
										&& currentCategoryType == 4
										&& !currentPendingCategorySeqList
												.isEmpty()) {

									queryObj.setParameterList(
											"CURRENT_PENDING_CATEGORY_SEQ_LIST",
											scoreCurrentInfo
													.getCurrentPendingCategorySeqList());
								} else if (currentCategoryType != null
										&& currentCategoryType == 5
										&& !currentDenyCategorySeqList
												.isEmpty()) {

									queryObj.setParameterList(
											"CURRENT_DENY_CATEGORY_SEQ_LIST",
											currentDenyCategorySeqList);
								}

								if (!StringUtils
										.isBlank(currentIncludeCheckPoints)
										&& !StringUtils
												.isBlank(currentExcludeCheckPoints)) {
									queryObj.setInteger(
											"CURRENT_INCLUDE_BIT_VALUE",
											scoreCurrentInfo
													.getCurrentIncludeBitValue());
									queryObj.setInteger(
											"CURRENT_EXCLUDE_BIT_VALUE",
											scoreCurrentInfo
													.getCurrentExcludeBitValue());
								} else {
									if (!StringUtils
											.isBlank(currentIncludeCheckPoints)) {
										queryObj.setInteger(
												"CURRENT_INCLUDE_BIT_VALUE",
												scoreCurrentInfo
														.getCurrentIncludeBitValue());
									} else if (!StringUtils
											.isBlank(currentExcludeCheckPoints)) {
										queryObj.setInteger(
												"CURRENT_EXCLUDE_BIT_VALUE",
												scoreCurrentInfo
														.getCurrentExcludeBitValue());
									}
								}

								if (currentStateList != null
										&& currentStateList.length > 0) {
									queryObj.setParameterList(
											"CURRENT_STATE_LIST",
											scoreCurrentInfo
													.getCurrentStateList());
								}

								if (currentUpdateStartDate != null) {
									queryObj.setParameter(
											"CURRENT_UPDATE_START_DATE",
											currentUpdateStartDate);
								}

								if (currentUpdateEndDate != null) {
									queryObj.setParameter(
											"CURRENT_UPDATE_END_DATE",
											currentUpdateEndDate);
								}
							} else if (menuId
									.equals(WebAppConst.SCORE_SAMP_MENU_ID)) {
								queryObj.setParameterList(
										"CURRENT_STATE_LIST",
										WebAppConst.SCORE_SAMPLING_CURRENT_STATES);
							} else if (menuId
									.equals(WebAppConst.STATE_TRAN_MENU_ID)) {
								queryObj.setParameterList(
										"CURRENT_STATE_LIST",
										WebAppConst.STATE_TRANSITION_CURRENT_STATES);
							} else if (menuId
									.equals(WebAppConst.FORCED_MENU_ID)) {
								queryObj.setParameterList(
										"CURRENT_STATE_LIST",
										WebAppConst.FORCED_SCORING_CURRENT_STATES);
							}
							
							if(searchByScorerRoleId==true){
								if(currentScorerRoles != null){
									queryObj.setParameterList("CURRENT_SCORER_ROLES", Arrays.asList(currentScorerRoles));
								}								
							}
							
						}

						public void setHistoryParameters(SQLQuery queryObj) {

							if (scoreHistoryInfo != null
									&& scoreHistoryInfo.isHistoryBlock()) {

								if (!historyScorerIdList.isEmpty()) {
									queryObj.setParameterList(
											"HISTORY_SCORER_ID_LIST",
											historyScorerIdList);
								}
								if (scoreHistoryInfo
										.isHistoryQualityCheckFlag()) {
									/*
									 * if (WebAppConst.SPEAKING_TYPE
									 * .equals(questionInfo .getQuestionType())
									 * || WebAppConst.WRITING_TYPE
									 * .equals(questionInfo .getQuestionType()))
									 * {
									 */
									queryObj.setParameterList(
											"QUALITY_CHECK_FLAG",
											ArrayUtils
													.subarray(
															WebAppConst.QUALITY_MARK_FLAG_LIST,
															0, 1));
									/* } */

								} /*
								 * else { if (WebAppConst.SPEAKING_TYPE
								 * .equals(questionInfo .getQuestionType()) ||
								 * WebAppConst.WRITING_TYPE .equals(questionInfo
								 * .getQuestionType())) {
								 * queryObj.setParameterList(
								 * "QUALITY_CHECK_FLAG",
								 * WebAppConst.QUALITY_MARK_FLAG_LIST); } }
								 */

								if (historyCategoryType != null
										&& historyCategoryType == 3) {

									if (!historyGradeSeqList.isEmpty()) {
										queryObj.setParameterList(
												"HISTORY_GRADE_SEQ_LIST",
												historyGradeSeqList);
									}

									if (ArrayUtils.contains(scoreHistoryInfo
											.getHistoryGradeNum(), -1)) {
										queryObj.setShort(
												"NO_GRADE_SCORING_STATE",
												WebAppConst.NO_GRADE_SCORING_STATE);
									}
								} else if (historyCategoryType != null
										&& historyCategoryType == 4
										&& !historyPendingCategorySeqList
												.isEmpty()) {

									queryObj.setParameterList(
											"HISTORY_PENDING_CATEGORY_SEQ_LIST",
											historyPendingCategorySeqList);
								} else if (historyCategoryType != null
										&& historyCategoryType == 5
										&& !historyDenyCategorySeqList
												.isEmpty()) {

									queryObj.setParameterList(
											"HISTORY_DENY_CATEGORY_SEQ_LIST",
											historyDenyCategorySeqList);
								}

								if (!StringUtils
										.isBlank(historyIncludeCheckPoints)
										&& !StringUtils
												.isBlank(historyExcludeCheckPoints)) {
									queryObj.setInteger(
											"HISTORY_INCLUDE_BIT_VALUE",
											scoreHistoryInfo
													.getHistoryIncludeBitValue());
									queryObj.setInteger(
											"HISTORY_EXCLUDE_BIT_VALUE",
											scoreHistoryInfo
													.getHistoryExcludeBitValue());
								} else {
									if (!StringUtils
											.isBlank(historyIncludeCheckPoints)) {
										queryObj.setInteger(
												"HISTORY_INCLUDE_BIT_VALUE",
												scoreHistoryInfo
														.getHistoryIncludeBitValue());
									} else if (!StringUtils
											.isBlank(historyExcludeCheckPoints)) {
										queryObj.setInteger(
												"HISTORY_EXCLUDE_BIT_VALUE",
												scoreHistoryInfo
														.getHistoryExcludeBitValue());
									}
								}

								if (historyEventList != null
										&& historyEventList.length > 0) {
									queryObj.setParameterList(
											"HISTORY_EVENT_LIST",
											historyEventList);
								}

								if (historyUpdateStartDate != null) {
									queryObj.setParameter(
											"HISTORY_UPDATE_START_DATE",
											historyUpdateStartDate);
								}

								if (historyUpdateEndDate != null) {
									queryObj.setParameter(
											"HISTORY_UPDATE_END_DATE",
											historyUpdateEndDate);
								}
								
								if(searchByScorerRoleId==true){
									if(historyScorerRoles != null){
										queryObj.setParameterList("HISTORY_SCORER_ROLES", Arrays.asList(historyScorerRoles));
									}									
								}

							}

						}
					});
			/*
			 * System.out.println(">>>>>>>>>>>>>> searchAnswerRecords end: " +
			 * new Date().getTime());
			 */
			return answerRecordsList;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	/*
	 * @SuppressWarnings("rawtypes") private Object getCount(final QuestionInfo
	 * questionInfo, final ScoreInputInfo scoreInputInfo, final Boolean
	 * forceAndStateTransitionFlag, final Integer startRecord, final Integer
	 * endRecord) {
	 * 
	 * final ScoreHistoryInfo scoreHistoryInfo = scoreInputInfo
	 * .getScoreHistoryInfo();
	 * 
	 * final String menuId = questionInfo.getMenuId(); final boolean
	 * samplingFlag = scoreInputInfo.isSamplingFlag(); final String
	 * answerFormNum = scoreInputInfo.getAnswerFormNum();
	 * 
	 * final List<String> historyScorerIdList = scoreHistoryInfo != null ?
	 * scoreHistoryInfo .getHistoryScorerIdList() : null; final Integer
	 * historyCategoryType = scoreHistoryInfo != null ? scoreHistoryInfo
	 * .getHistoryCategoryType() : null; final List historyGradeSeqList =
	 * scoreHistoryInfo != null ? scoreHistoryInfo .getHistoryGradeSeqList() :
	 * null; final List historyPendingCategorySeqList = scoreHistoryInfo != null
	 * ? scoreHistoryInfo .getHistoryPendingCategorySeqList() : null; final
	 * String historyIncludeCheckPoints = scoreHistoryInfo != null ?
	 * scoreHistoryInfo .getHistoryIncludeCheckPoints() : null; final String
	 * historyExcludeCheckPoints = scoreHistoryInfo != null ? scoreHistoryInfo
	 * .getHistoryExcludeCheckPoints() : null; final Short[] historyEventList =
	 * scoreHistoryInfo != null ? scoreHistoryInfo .getHistoryEventList() :
	 * null; final Date historyUpdateStartDate = scoreHistoryInfo != null ?
	 * scoreHistoryInfo .getHistoryUpdateStartDate() : null; final Date
	 * historyUpdateEndDate = scoreHistoryInfo != null ? scoreHistoryInfo
	 * .getHistoryUpdateEndDate() : null;
	 * 
	 * final ScoreCurrentInfo scoreCurrentInfo = scoreInputInfo
	 * .getScoreCurrentInfo();
	 * 
	 * final List<String> currentScorerIdList = scoreCurrentInfo != null ?
	 * scoreCurrentInfo .getCurrentScorerIdList() : null; final Integer
	 * currentCategoryType = scoreCurrentInfo != null ? scoreCurrentInfo
	 * .getCurrentCategoryType() : null; final List currentGradeSeqList =
	 * scoreCurrentInfo != null ? scoreCurrentInfo .getCurrentGradeSeqList() :
	 * null; final List currentPendingCategorySeqList = scoreCurrentInfo != null
	 * ? scoreCurrentInfo .getCurrentPendingCategorySeqList() : null; final
	 * String currentIncludeCheckPoints = scoreCurrentInfo != null ?
	 * scoreCurrentInfo .getCurrentIncludeCheckPoints() : null; final String
	 * currentExcludeCheckPoints = scoreCurrentInfo != null ? scoreCurrentInfo
	 * .getCurrentExcludeCheckPoints() : null; final Short[] currentStateList =
	 * scoreCurrentInfo != null ? scoreCurrentInfo .getCurrentStateList() :
	 * null; final Date currentUpdateStartDate = scoreCurrentInfo != null ?
	 * scoreCurrentInfo .getCurrentUpdateStartDate() : null; final Date
	 * currentUpdateEndDate = scoreCurrentInfo != null ? scoreCurrentInfo
	 * .getCurrentUpdateEndDate() : null;
	 * 
	 * try { Object recordCount = getHibernateTemplate(
	 * questionInfo.getConnectionString()).execute( new
	 * HibernateCallback<Object>() {
	 * 
	 * public Object doInHibernate(Session session) throws HibernateException {
	 * StringBuilder query = new StringBuilder(); if
	 * ((forceAndStateTransitionFlag == null) || (forceAndStateTransitionFlag ==
	 * WebAppConst.TRUE)) { query.append(
	 * "SELECT t.answer_seq AS answerSeq, t.answer_form_num AS answerFormNum, "
	 * );
	 * query.append("t.image_file_name AS imageFileName, t.grade_seq AS gradeSeq, "
	 * ); query.append(
	 * "t.bit_value AS bitValue, t.latest_scorer_id AS latestScorerId, "); if
	 * (!(menuId .equals(WebAppConst.STATE_TRAN_MENU_ID) || menuId
	 * .equals(WebAppConst.FORCED_MENU_ID))) { query.append(
	 * "(SELECT scorer_comment FROM tran_desc_score_history AS scoreHistory ");
	 * query.append("WHERE scoreHistory.answer_seq = t.answer_seq ");
	 * query.append("AND scoreHistory.update_date = "); query.append(
	 * "(SELECT max(history.update_date) FROM tran_desc_score_history history "
	 * ); query.append(
	 * "WHERE history.answer_seq = scoreHistory.answer_seq)) AS scorerComment, "
	 * ); } query.append("t.punch_text AS punchText, "); query.append(
	 * "t.pending_category_seq AS pendingCategorySeq, t.latest_scoring_state, "
	 * ); if (((forceAndStateTransitionFlag != null) &&
	 * (forceAndStateTransitionFlag == WebAppConst.TRUE)) || (menuId
	 * .equals(WebAppConst.REFERENCE_SAMP_MENU_ID) &&
	 * (forceAndStateTransitionFlag == null))) { query.append(
	 * "t.question_seq AS questionSeq, t.latest_screen_scorer_id AS latestScreenScorerId, "
	 * ); } if (menuId.equals(WebAppConst.FORCED_MENU_ID) &&
	 * (forceAndStateTransitionFlag != null && forceAndStateTransitionFlag ==
	 * WebAppConst.TRUE)) {
	 * query.append("(SELECT count(*) FROM tran_desc_score_history AS scoreHistory "
	 * ); query.append("WHERE scoreHistory.answer_seq = t.answer_seq ");
	 * query.append("AND scoreHistory.question_seq = :QUESTION_SEQ ");
	 * query.append(
	 * "AND scoreHistory.scorer_comment != '' AND scoreHistory.scorer_comment is not null ) AS commentCount, "
	 * ); } query.append("t.update_date AS updateDate ");
	 * 
	 * query.append("FROM  tran_desc_score AS t, ( ");
	 * query.append("SELECT count(*) AS count "); } else {
	 * query.append("SELECT SUM(rowCount) "); query.append(
	 * "FROM (SELECT count(distinct tranDescScore.answer_seq) as rowCount "); }
	 * query.append("FROM tran_desc_score AS tranDescScore ");
	 * 
	 * query.append("INNER JOIN ");
	 * query.append("tran_desc_score_history AS tranDescScoreHistory ");
	 * query.append
	 * ("ON tranDescScore.answer_seq = tranDescScoreHistory.answer_seq ");
	 * query.append("WHERE tranDescScore.question_seq  = :QUESTION_SEQ ");
	 * query.append("AND tranDescScoreHistory.question_seq  = :QUESTION_SEQ ");
	 * query.append("AND tranDescScore.lock_flag = :UNLOCK ");
	 * 
	 * if (menuId.equals(WebAppConst.STATE_TRAN_MENU_ID)) {
	 * query.append("AND tranDescScore.inspect_flag = :INSPECT_FLAG "); }
	 * 
	 * if (menuId.equals(WebAppConst.SCORE_SAMP_MENU_ID)) {
	 * query.append("AND tranDescScore.sampling_flag IN :SAMPLING_FLAG_LIST ");
	 * }
	 * 
	 * if (!StringUtils.isBlank(answerFormNum)) {
	 * query.append("AND tranDescScore.answer_form_num = :ANSWER_FORM_NUM "); }
	 * 
	 * if (scoreHistoryInfo != null && scoreHistoryInfo.isHistoryBlock()) {
	 * 
	 * if (!historyScorerIdList.isEmpty()) { query.append(
	 * "AND tranDescScoreHistory.latest_screen_scorer_id IN :HISTORY_SCORER_ID_LIST "
	 * ); }
	 * 
	 * if (historyCategoryType != null && historyCategoryType == 2) {
	 * query.append(
	 * "AND tranDescScoreHistory.grade_seq IS null AND tranDescScoreHistory.pending_category_seq IS null "
	 * ); } else if (historyCategoryType != null && historyCategoryType == 3) {
	 * 
	 * if (!historyGradeSeqList.isEmpty() && ArrayUtils.contains(
	 * scoreHistoryInfo .getHistoryGradeNum(), -1)) { query.append(
	 * "AND (tranDescScoreHistory.grade_seq IN :HISTORY_GRADE_SEQ_LIST ");
	 * query.
	 * append("OR tranDescScoreHistory.scoring_state = :NO_GRADE_SCORING_STATE) "
	 * ); } else { if (!historyGradeSeqList.isEmpty()) {
	 * query.append("AND tranDescScoreHistory.grade_seq IN :HISTORY_GRADE_SEQ_LIST "
	 * ); }
	 * 
	 * if (ArrayUtils.contains( scoreHistoryInfo .getHistoryGradeNum(), -1)) {
	 * query
	 * .append("AND tranDescScoreHistory.scoring_state = :NO_GRADE_SCORING_STATE "
	 * ); } } } else if (historyCategoryType != null && historyCategoryType == 4
	 * && !historyPendingCategorySeqList .isEmpty()) { query.append(
	 * "AND tranDescScoreHistory.pending_category_seq IN :HISTORY_PENDING_CATEGORY_SEQ_LIST "
	 * ); }
	 * 
	 * if (!StringUtils .isBlank(historyIncludeCheckPoints) && !StringUtils
	 * .isBlank(historyExcludeCheckPoints)) { query.append(
	 * "AND (tranDescScoreHistory.bit_value & :HISTORY_INCLUDE_BIT_VALUE) > 0 "
	 * ); query.append(
	 * "AND (tranDescScoreHistory.bit_value & :HISTORY_EXCLUDE_BIT_VALUE) = 0 "
	 * ); } else { if (!StringUtils .isBlank(historyIncludeCheckPoints)) {
	 * query.append(
	 * "AND (tranDescScoreHistory.bit_value & :HISTORY_INCLUDE_BIT_VALUE) > 0 "
	 * ); } else if (!StringUtils .isBlank(historyExcludeCheckPoints)) {
	 * query.append
	 * ("AND (tranDescScoreHistory.bit_value & :HISTORY_EXCLUDE_BIT_VALUE) = 0 "
	 * ); } }
	 * 
	 * if (historyEventList != null && historyEventList.length > 0) {
	 * query.append
	 * ("AND tranDescScoreHistory.event_id IN :HISTORY_EVENT_LIST "); }
	 * 
	 * if (historyUpdateStartDate != null) { query.append(
	 * "AND tranDescScoreHistory.update_date >= :HISTORY_UPDATE_START_DATE "); }
	 * 
	 * if (historyUpdateEndDate != null) { query.append(
	 * "AND tranDescScoreHistory.update_date <= :HISTORY_UPDATE_END_DATE "); } }
	 * 
	 * if (scoreCurrentInfo != null && scoreCurrentInfo.isCurrentBlock()) {
	 * 
	 * if (!currentScorerIdList.isEmpty()) { query.append(
	 * "AND tranDescScore.latest_screen_scorer_id IN :CURRENT_SCORER_ID_LIST ");
	 * }
	 * 
	 * if (currentCategoryType != null && currentCategoryType == 2) {
	 * query.append(
	 * "AND tranDescScore.grade_seq IS null AND tranDescScore.pending_category_seq IS null "
	 * ); } else if (currentCategoryType != null && currentCategoryType == 3) {
	 * 
	 * if (!currentGradeSeqList.isEmpty() && ArrayUtils.contains(
	 * scoreCurrentInfo .getCurrentGradeNum(), -1)) {
	 * query.append("AND (tranDescScore.grade_seq IN :CURRENT_GRADE_SEQ_LIST ");
	 * query
	 * .append("OR tranDescScore.latest_scoring_state = :NO_GRADE_SCORING_STATE) "
	 * ); } else { if (!currentGradeSeqList.isEmpty()) {
	 * query.append("AND tranDescScore.grade_seq IN :CURRENT_GRADE_SEQ_LIST ");
	 * }
	 * 
	 * if (ArrayUtils.contains( scoreCurrentInfo .getCurrentGradeNum(), -1)) {
	 * query
	 * .append("AND tranDescScore.latest_scoring_state = :NO_GRADE_SCORING_STATE "
	 * ); } } } else if (currentCategoryType != null && currentCategoryType == 4
	 * && !currentPendingCategorySeqList .isEmpty()) { query.append(
	 * "AND tranDescScore.pending_category_seq IN :CURRENT_PENDING_CATEGORY_SEQ_LIST "
	 * ); }
	 * 
	 * if (!StringUtils .isBlank(currentIncludeCheckPoints) && !StringUtils
	 * .isBlank(currentExcludeCheckPoints)) { query.append(
	 * "AND (tranDescScore.bit_value & :CURRENT_INCLUDE_BIT_VALUE) > 0 ");
	 * query.
	 * append("AND (tranDescScore.bit_value & :CURRENT_EXCLUDE_BIT_VALUE) = 0 "
	 * ); } else { if (!StringUtils .isBlank(currentIncludeCheckPoints)) {
	 * query.
	 * append("AND (tranDescScore.bit_value & :CURRENT_INCLUDE_BIT_VALUE) > 0 "
	 * ); } else if (!StringUtils .isBlank(currentExcludeCheckPoints)) {
	 * query.append
	 * ("AND (tranDescScore.bit_value & :CURRENT_EXCLUDE_BIT_VALUE) = 0 "); } }
	 * 
	 * if (currentStateList != null && currentStateList.length > 0) {
	 * query.append
	 * ("AND tranDescScore.latest_scoring_state IN :CURRENT_STATE_LIST "); }
	 * 
	 * if (currentUpdateStartDate != null) {
	 * query.append("AND tranDescScore.update_date >= :CURRENT_UPDATE_START_DATE "
	 * ); }
	 * 
	 * if (currentUpdateEndDate != null) {
	 * query.append("AND tranDescScore.update_date <= :CURRENT_UPDATE_END_DATE "
	 * ); } } else if (menuId .equals(WebAppConst.SCORE_SAMP_MENU_ID) || menuId
	 * .equals(WebAppConst.STATE_TRAN_MENU_ID) || menuId
	 * .equals(WebAppConst.FORCED_MENU_ID)) {
	 * query.append("AND tranDescScore.latest_scoring_state IN :CURRENT_STATE_LIST "
	 * ); }
	 * 
	 * if ((scoreHistoryInfo == null || !scoreHistoryInfo .isHistoryBlock()) &&
	 * (scoreCurrentInfo == null || !scoreCurrentInfo .isCurrentBlock())) { if
	 * (menuId .equals(WebAppConst.REFERENCE_SAMP_MENU_ID)) { query.append(
	 * "AND tranDescScore.latest_scoring_state NOT IN :SPECIAL_SCORING_WAIT_STATES "
	 * ); } else if (menuId .equals(WebAppConst.SCORE_SAMP_MENU_ID)) {
	 * query.append
	 * ("AND tranDescScore.latest_scoring_state NOT IN :SPECIAL_SCORING_STATES "
	 * ); }
	 * 
	 * } if (!(menuId.equals(WebAppConst.STATE_TRAN_MENU_ID) || menuId
	 * .equals(WebAppConst.FORCED_MENU_ID))) { query.append(
	 * "AND tranDescScoreHistory.scoring_state NOT IN :DUMMY_SCORING_STATES ");
	 * query.append(
	 * "AND tranDescScore.latest_scoring_state NOT IN :DUMMY_SCORING_STATES ");
	 * } query.append("AND tranDescScore.valid_flag = :VALID_FLAG ");
	 * query.append("AND tranDescScoreHistory.valid_flag = :VALID_FLAG ");
	 * query.append("GROUP BY tranDescScore.answer_seq "); if
	 * ((forceAndStateTransitionFlag != null) && (forceAndStateTransitionFlag ==
	 * WebAppConst.TRUE) && (menuId .equals(WebAppConst.FORCED_MENU_ID))) {
	 * query.append("ORDER BY tranDescScore.update_date DESC ");
	 * query.append("LIMIT "); if (endRecord != null) { if (startRecord > 0) {
	 * query.append(startRecord + " , " + endRecord); } else {
	 * query.append(endRecord + " "); } } } if
	 * (!(menuId.equals(WebAppConst.STATE_TRAN_MENU_ID) || menuId
	 * .equals(WebAppConst.FORCED_MENU_ID))) { query.append("ORDER BY RAND() ");
	 * query.append("LIMIT :RECORD_COUNT_LIMIT "); query.append(") score ");
	 * query.append("WHERE t.answer_seq = score.answerSeq ");
	 * query.append("ORDER BY t.update_date DESC "); } else if
	 * ((forceAndStateTransitionFlag != null) && (forceAndStateTransitionFlag ==
	 * WebAppConst.TRUE)) { query.append(") score ");
	 * query.append("WHERE t.answer_seq = score.answerSeq "); if
	 * (!menuId.equals(WebAppConst.FORCED_MENU_ID)) {
	 * query.append("ORDER BY updateDate DESC "); } } else if
	 * ((forceAndStateTransitionFlag != null) && (forceAndStateTransitionFlag ==
	 * WebAppConst.FALSE)) { query.append(") score "); }
	 * 
	 * SQLQuery queryObj = session.createSQLQuery(query .toString());
	 * 
	 * if (menuId.equals(WebAppConst.STATE_TRAN_MENU_ID)) {
	 * queryObj.setParameter("INSPECT_FLAG", WebAppConst.F); }
	 * 
	 * if (menuId.equals(WebAppConst.SCORE_SAMP_MENU_ID)) { if (samplingFlag ==
	 * WebAppConst.TRUE) { queryObj.setParameterList( "SAMPLING_FLAG_LIST",
	 * WebAppConst.SCORE_SAMPLING_FLAG_LIST); } else {
	 * queryObj.setParameterList( "SAMPLING_FLAG_LIST", ArrayUtils .subarray(
	 * WebAppConst.SCORE_SAMPLING_FLAG_LIST, 0, 1)); } }
	 * 
	 * if (!StringUtils.isBlank(answerFormNum)) {
	 * queryObj.setParameter("ANSWER_FORM_NUM", answerFormNum); }
	 * 
	 * setHistoryParameters(queryObj);
	 * 
	 * setCurrentParameters(queryObj);
	 * 
	 * queryObj.setInteger("QUESTION_SEQ", questionInfo.getQuestionSeq());
	 * queryObj.setCharacter("UNLOCK", WebAppConst.UNLOCK);
	 * queryObj.setCharacter("VALID_FLAG", WebAppConst.VALID_FLAG); if
	 * (!(menuId.equals(WebAppConst.STATE_TRAN_MENU_ID) || menuId
	 * .equals(WebAppConst.FORCED_MENU_ID))) {
	 * queryObj.setInteger("RECORD_COUNT_LIMIT",
	 * scoreInputInfo.getResultCount()); }
	 * 
	 * if ((scoreHistoryInfo == null || !scoreHistoryInfo .isHistoryBlock()) &&
	 * (scoreCurrentInfo == null || !scoreCurrentInfo .isCurrentBlock())) { if
	 * (menuId .equals(WebAppConst.REFERENCE_SAMP_MENU_ID)) {
	 * queryObj.setParameterList( "SPECIAL_SCORING_WAIT_STATES",
	 * WebAppConst.SPECIAL_SCORING_WAIT_STATES); } else if (menuId
	 * .equals(WebAppConst.SCORE_SAMP_MENU_ID)) { queryObj.setParameterList(
	 * "SPECIAL_SCORING_STATES", WebAppConst.SPECIAL_SCORING_STATES); }
	 * 
	 * } if (!(menuId.equals(WebAppConst.STATE_TRAN_MENU_ID) || menuId
	 * .equals(WebAppConst.FORCED_MENU_ID))) { queryObj.setParameterList(
	 * "DUMMY_SCORING_STATES", WebAppConst.DUMMY_SCORING_STATES); }
	 *//*
	 * if ((forceAndStateTransitionFlag != null) &&
	 * (forceAndStateTransitionFlag == WebAppConst.TRUE) && (menuId
	 * .equals(WebAppConst.FORCED_MENU_ID))) { if (endRecord != null) {
	 * queryObj.setFirstResult(startRecord); queryObj.setMaxResults(endRecord);
	 * } }
	 */
	/*
	 * 
	 * return queryObj.uniqueResult(); }
	 * 
	 * public void setCurrentParameters(SQLQuery queryObj) {
	 * 
	 * if (scoreCurrentInfo != null && scoreCurrentInfo.isCurrentBlock()) { if
	 * (!currentScorerIdList.isEmpty()) { queryObj.setParameterList(
	 * "CURRENT_SCORER_ID_LIST", currentScorerIdList); }
	 * 
	 * if (currentCategoryType != null && currentCategoryType == 3) {
	 * 
	 * if (!currentGradeSeqList.isEmpty()) { queryObj.setParameterList(
	 * "CURRENT_GRADE_SEQ_LIST", currentGradeSeqList); }
	 * 
	 * if (ArrayUtils.contains(scoreCurrentInfo .getCurrentGradeNum(), -1)) {
	 * queryObj.setShort( "NO_GRADE_SCORING_STATE",
	 * WebAppConst.NO_GRADE_SCORING_STATE); } } else if (currentCategoryType !=
	 * null && currentCategoryType == 4 && !currentPendingCategorySeqList
	 * .isEmpty()) {
	 * 
	 * queryObj.setParameterList( "CURRENT_PENDING_CATEGORY_SEQ_LIST",
	 * scoreCurrentInfo .getCurrentPendingCategorySeqList()); }
	 * 
	 * if (!StringUtils .isBlank(currentIncludeCheckPoints) && !StringUtils
	 * .isBlank(currentExcludeCheckPoints)) { queryObj.setInteger(
	 * "CURRENT_INCLUDE_BIT_VALUE", scoreCurrentInfo
	 * .getCurrentIncludeBitValue()); queryObj.setInteger(
	 * "CURRENT_EXCLUDE_BIT_VALUE", scoreCurrentInfo
	 * .getCurrentExcludeBitValue()); } else { if (!StringUtils
	 * .isBlank(currentIncludeCheckPoints)) { queryObj.setInteger(
	 * "CURRENT_INCLUDE_BIT_VALUE", scoreCurrentInfo
	 * .getCurrentIncludeBitValue()); } else if (!StringUtils
	 * .isBlank(currentExcludeCheckPoints)) { queryObj.setInteger(
	 * "CURRENT_EXCLUDE_BIT_VALUE", scoreCurrentInfo
	 * .getCurrentExcludeBitValue()); } }
	 * 
	 * if (currentStateList != null && currentStateList.length > 0) {
	 * queryObj.setParameterList( "CURRENT_STATE_LIST", scoreCurrentInfo
	 * .getCurrentStateList()); }
	 * 
	 * if (currentUpdateStartDate != null) { queryObj.setParameter(
	 * "CURRENT_UPDATE_START_DATE", currentUpdateStartDate); }
	 * 
	 * if (currentUpdateEndDate != null) { queryObj.setParameter(
	 * "CURRENT_UPDATE_END_DATE", currentUpdateEndDate); } } else if (menuId
	 * .equals(WebAppConst.SCORE_SAMP_MENU_ID)) { queryObj.setParameterList(
	 * "CURRENT_STATE_LIST", WebAppConst.SCORE_SAMPLING_CURRENT_STATES); } else
	 * if (menuId .equals(WebAppConst.STATE_TRAN_MENU_ID)) {
	 * queryObj.setParameterList( "CURRENT_STATE_LIST",
	 * WebAppConst.STATE_TRANSITION_CURRENT_STATES); } else if (menuId
	 * .equals(WebAppConst.FORCED_MENU_ID)) { queryObj.setParameterList(
	 * "CURRENT_STATE_LIST", WebAppConst.FORCED_SCORING_CURRENT_STATES); }
	 * 
	 * }
	 * 
	 * public void setHistoryParameters(SQLQuery queryObj) {
	 * 
	 * if (scoreHistoryInfo != null && scoreHistoryInfo.isHistoryBlock()) {
	 * 
	 * if (!historyScorerIdList.isEmpty()) { queryObj.setParameterList(
	 * "HISTORY_SCORER_ID_LIST", historyScorerIdList); }
	 * 
	 * if (historyCategoryType != null && historyCategoryType == 3) {
	 * 
	 * if (!historyGradeSeqList.isEmpty()) { queryObj.setParameterList(
	 * "HISTORY_GRADE_SEQ_LIST", historyGradeSeqList); }
	 * 
	 * if (ArrayUtils.contains(scoreHistoryInfo .getHistoryGradeNum(), -1)) {
	 * queryObj.setShort( "NO_GRADE_SCORING_STATE",
	 * WebAppConst.NO_GRADE_SCORING_STATE); } } else if (historyCategoryType !=
	 * null && historyCategoryType == 4 && !historyPendingCategorySeqList
	 * .isEmpty()) {
	 * 
	 * queryObj.setParameterList( "HISTORY_PENDING_CATEGORY_SEQ_LIST",
	 * historyPendingCategorySeqList); }
	 * 
	 * if (!StringUtils .isBlank(historyIncludeCheckPoints) && !StringUtils
	 * .isBlank(historyExcludeCheckPoints)) { queryObj.setInteger(
	 * "HISTORY_INCLUDE_BIT_VALUE", scoreHistoryInfo
	 * .getHistoryIncludeBitValue()); queryObj.setInteger(
	 * "HISTORY_EXCLUDE_BIT_VALUE", scoreHistoryInfo
	 * .getHistoryExcludeBitValue()); } else { if (!StringUtils
	 * .isBlank(historyIncludeCheckPoints)) { queryObj.setInteger(
	 * "HISTORY_INCLUDE_BIT_VALUE", scoreHistoryInfo
	 * .getHistoryIncludeBitValue()); } else if (!StringUtils
	 * .isBlank(historyExcludeCheckPoints)) { queryObj.setInteger(
	 * "HISTORY_EXCLUDE_BIT_VALUE", scoreHistoryInfo
	 * .getHistoryExcludeBitValue()); } }
	 * 
	 * if (historyEventList != null && historyEventList.length > 0) {
	 * queryObj.setParameterList( "HISTORY_EVENT_LIST", historyEventList); }
	 * 
	 * if (historyUpdateStartDate != null) { queryObj.setParameter(
	 * "HISTORY_UPDATE_START_DATE", historyUpdateStartDate); }
	 * 
	 * if (historyUpdateEndDate != null) { queryObj.setParameter(
	 * "HISTORY_UPDATE_END_DATE", historyUpdateEndDate); }
	 * 
	 * }
	 * 
	 * } });
	 * 
	 * return recordCount; } catch (RuntimeException re) { throw re; } }
	 */

	// Using limited random answerSequences.
	/*
	 * @SuppressWarnings("rawtypes")
	 * 
	 * @Override public List searchAnswerRecords(final QuestionInfo
	 * questionInfo, final ScoreInputInfo scoreInputInfo, final Boolean
	 * forceAndStateTransitionFlag, final Integer startRecord, final Integer
	 * endRecord) {
	 * 
	 * final ScoreHistoryInfo scoreHistoryInfo = scoreInputInfo
	 * .getScoreHistoryInfo();
	 * 
	 * final String menuId = questionInfo.getMenuId(); final boolean
	 * samplingFlag = scoreInputInfo.isSamplingFlag(); final String
	 * answerFormNum = scoreInputInfo.getAnswerFormNum();
	 * 
	 * final List<String> historyScorerIdList = scoreHistoryInfo != null ?
	 * scoreHistoryInfo .getHistoryScorerIdList() : null; final Integer
	 * historyCategoryType = scoreHistoryInfo != null ? scoreHistoryInfo
	 * .getHistoryCategoryType() : null; final List historyGradeSeqList =
	 * scoreHistoryInfo != null ? scoreHistoryInfo .getHistoryGradeSeqList() :
	 * null; final List historyPendingCategorySeqList = scoreHistoryInfo != null
	 * ? scoreHistoryInfo .getHistoryPendingCategorySeqList() : null; final
	 * String historyIncludeCheckPoints = scoreHistoryInfo != null ?
	 * scoreHistoryInfo .getHistoryIncludeCheckPoints() : null; final String
	 * historyExcludeCheckPoints = scoreHistoryInfo != null ? scoreHistoryInfo
	 * .getHistoryExcludeCheckPoints() : null; final Short[] historyEventList =
	 * scoreHistoryInfo != null ? scoreHistoryInfo .getHistoryEventList() :
	 * null; final Date historyUpdateStartDate = scoreHistoryInfo != null ?
	 * scoreHistoryInfo .getHistoryUpdateStartDate() : null; final Date
	 * historyUpdateEndDate = scoreHistoryInfo != null ? scoreHistoryInfo
	 * .getHistoryUpdateEndDate() : null;
	 * 
	 * final ScoreCurrentInfo scoreCurrentInfo = scoreInputInfo
	 * .getScoreCurrentInfo();
	 * 
	 * final List<String> currentScorerIdList = scoreCurrentInfo != null ?
	 * scoreCurrentInfo .getCurrentScorerIdList() : null; final Integer
	 * currentCategoryType = scoreCurrentInfo != null ? scoreCurrentInfo
	 * .getCurrentCategoryType() : null; final List currentGradeSeqList =
	 * scoreCurrentInfo != null ? scoreCurrentInfo .getCurrentGradeSeqList() :
	 * null; final List currentPendingCategorySeqList = scoreCurrentInfo != null
	 * ? scoreCurrentInfo .getCurrentPendingCategorySeqList() : null; final
	 * String currentIncludeCheckPoints = scoreCurrentInfo != null ?
	 * scoreCurrentInfo .getCurrentIncludeCheckPoints() : null; final String
	 * currentExcludeCheckPoints = scoreCurrentInfo != null ? scoreCurrentInfo
	 * .getCurrentExcludeCheckPoints() : null; final Short[] currentStateList =
	 * scoreCurrentInfo != null ? scoreCurrentInfo .getCurrentStateList() :
	 * null; final Date currentUpdateStartDate = scoreCurrentInfo != null ?
	 * scoreCurrentInfo .getCurrentUpdateStartDate() : null; final Date
	 * currentUpdateEndDate = scoreCurrentInfo != null ? scoreCurrentInfo
	 * .getCurrentUpdateEndDate() : null;
	 * 
	 * System.out.println(">>>>>>>>>>>>>> getRandomAnswerSequences start: "+new
	 * Date().getTime());
	 * 
	 * final List answerSeqList = getRandomAnswerSequences(questionInfo,
	 * scoreInputInfo, forceAndStateTransitionFlag, startRecord, endRecord);
	 * 
	 * System.out.println(">>>>>>>>>>>>>> getRandomAnswerSequences end: "+new
	 * Date().getTime());
	 * System.out.println(">>>>>>>>>>>>>> searchAnswerRecords start: "+new
	 * Date().getTime()); try { List answerRecordsList = getHibernateTemplate(
	 * questionInfo.getConnectionString()).execute( new
	 * HibernateCallback<List>() {
	 * 
	 * public List doInHibernate(Session session) throws HibernateException {
	 * StringBuilder query = new StringBuilder(); if
	 * ((forceAndStateTransitionFlag == null) || (forceAndStateTransitionFlag ==
	 * WebAppConst.TRUE)) { query.append(
	 * "SELECT tranDescScore.answer_seq AS answerSeq, tranDescScore.answer_form_num AS answerFormNum, "
	 * ); query.append(
	 * "tranDescScore.image_file_name AS imageFileName, tranDescScore.grade_seq AS gradeSeq, "
	 * ); query.append(
	 * "tranDescScore.bit_value AS bitValue, tranDescScore.latest_scorer_id AS latestScorerId, "
	 * ); if (!(menuId .equals(WebAppConst.STATE_TRAN_MENU_ID) || menuId
	 * .equals(WebAppConst.FORCED_MENU_ID))) { query.append(
	 * "(SELECT scorer_comment FROM tran_desc_score_history AS scoreHistory ");
	 * query
	 * .append("WHERE scoreHistory.answer_seq = tranDescScore.answer_seq ");
	 * query.append("AND scoreHistory.update_date = "); query.append(
	 * "(SELECT max(history.update_date) FROM tran_desc_score_history history "
	 * ); query.append(
	 * "WHERE history.answer_seq = scoreHistory.answer_seq)) AS scorerComment, "
	 * ); } query.append("tranDescScore.punch_text AS punchText, ");
	 * query.append(
	 * "tranDescScore.pending_category_seq AS pendingCategorySeq, tranDescScore.latest_scoring_state, "
	 * ); if (((forceAndStateTransitionFlag != null) &&
	 * (forceAndStateTransitionFlag == WebAppConst.TRUE)) || (menuId
	 * .equals(WebAppConst.REFERENCE_SAMP_MENU_ID) &&
	 * (forceAndStateTransitionFlag == null))) { query.append(
	 * "tranDescScore.question_seq AS questionSeq, tranDescScore.latest_screen_scorer_id AS latestScreenScorerId, "
	 * ); } if (menuId.equals(WebAppConst.FORCED_MENU_ID) &&
	 * (forceAndStateTransitionFlag != null && forceAndStateTransitionFlag ==
	 * WebAppConst.TRUE)) {
	 * query.append("(SELECT count(*) FROM tran_desc_score_history AS scoreHistory "
	 * );
	 * query.append("WHERE scoreHistory.answer_seq = tranDescScore.answer_seq "
	 * ); query.append("AND scoreHistory.question_seq = :QUESTION_SEQ ");
	 * query.append(
	 * "AND scoreHistory.scorer_comment != '' AND scoreHistory.scorer_comment is not null ) AS commentCount, "
	 * ); } query.append("tranDescScore.update_date AS updateDate ");
	 * 
	 * query.append("FROM  tran_desc_score AS t, ( ");
	 * query.append("SELECT tranDescScore.answer_seq AS answerSeq "); } else {
	 * query.append("SELECT SUM(rowCount) "); query.append(
	 * "FROM (SELECT count(distinct tranDescScore.answer_seq) as rowCount "); }
	 * query.append("FROM tran_desc_score AS tranDescScore ");
	 * 
	 * query.append("INNER JOIN ");
	 * query.append("tran_desc_score_history AS tranDescScoreHistory ");
	 * query.append
	 * ("ON tranDescScore.answer_seq = tranDescScoreHistory.answer_seq ");
	 * query.append("WHERE tranDescScore.question_seq  = :QUESTION_SEQ ");
	 * query.append("AND tranDescScoreHistory.question_seq  = :QUESTION_SEQ ");
	 * query.append("AND tranDescScore.lock_flag = :UNLOCK ");
	 * 
	 * if (menuId.equals(WebAppConst.STATE_TRAN_MENU_ID)) {
	 * query.append("AND tranDescScore.inspect_flag = :INSPECT_FLAG "); }
	 * 
	 * if (menuId.equals(WebAppConst.SCORE_SAMP_MENU_ID)) {
	 * query.append("AND tranDescScore.sampling_flag IN :SAMPLING_FLAG_LIST ");
	 * }
	 * 
	 * if (!StringUtils.isBlank(answerFormNum)) {
	 * query.append("AND tranDescScore.answer_form_num = :ANSWER_FORM_NUM "); }
	 * 
	 * if (scoreHistoryInfo != null && scoreHistoryInfo.isHistoryBlock()) {
	 * 
	 * if (!historyScorerIdList.isEmpty()) { query.append(
	 * "AND tranDescScoreHistory.latest_screen_scorer_id IN :HISTORY_SCORER_ID_LIST "
	 * ); }
	 * 
	 * if (historyCategoryType != null && historyCategoryType == 2) {
	 * query.append(
	 * "AND tranDescScoreHistory.grade_seq IS null AND tranDescScoreHistory.pending_category_seq IS null "
	 * ); } else if (historyCategoryType != null && historyCategoryType == 3) {
	 * 
	 * if (!historyGradeSeqList.isEmpty() && ArrayUtils.contains(
	 * scoreHistoryInfo .getHistoryGradeNum(), -1)) { query.append(
	 * "AND (tranDescScoreHistory.grade_seq IN :HISTORY_GRADE_SEQ_LIST ");
	 * query.
	 * append("OR tranDescScoreHistory.scoring_state = :NO_GRADE_SCORING_STATE) "
	 * ); } else { if (!historyGradeSeqList.isEmpty()) {
	 * query.append("AND tranDescScoreHistory.grade_seq IN :HISTORY_GRADE_SEQ_LIST "
	 * ); }
	 * 
	 * if (ArrayUtils.contains( scoreHistoryInfo .getHistoryGradeNum(), -1)) {
	 * query
	 * .append("AND tranDescScoreHistory.scoring_state = :NO_GRADE_SCORING_STATE "
	 * ); } } } else if (historyCategoryType != null && historyCategoryType == 4
	 * && !historyPendingCategorySeqList .isEmpty()) { query.append(
	 * "AND tranDescScoreHistory.pending_category_seq IN :HISTORY_PENDING_CATEGORY_SEQ_LIST "
	 * ); }
	 * 
	 * if (!StringUtils .isBlank(historyIncludeCheckPoints) && !StringUtils
	 * .isBlank(historyExcludeCheckPoints)) { query.append(
	 * "AND (tranDescScoreHistory.bit_value & :HISTORY_INCLUDE_BIT_VALUE) > 0 "
	 * ); query.append(
	 * "AND (tranDescScoreHistory.bit_value & :HISTORY_EXCLUDE_BIT_VALUE) = 0 "
	 * ); } else { if (!StringUtils .isBlank(historyIncludeCheckPoints)) {
	 * query.append(
	 * "AND (tranDescScoreHistory.bit_value & :HISTORY_INCLUDE_BIT_VALUE) > 0 "
	 * ); } else if (!StringUtils .isBlank(historyExcludeCheckPoints)) {
	 * query.append
	 * ("AND (tranDescScoreHistory.bit_value & :HISTORY_EXCLUDE_BIT_VALUE) = 0 "
	 * ); } }
	 * 
	 * if (historyEventList != null && historyEventList.length > 0) {
	 * query.append
	 * ("AND tranDescScoreHistory.event_id IN :HISTORY_EVENT_LIST "); }
	 * 
	 * if (historyUpdateStartDate != null) { query.append(
	 * "AND tranDescScoreHistory.update_date >= :HISTORY_UPDATE_START_DATE "); }
	 * 
	 * if (historyUpdateEndDate != null) { query.append(
	 * "AND tranDescScoreHistory.update_date <= :HISTORY_UPDATE_END_DATE "); } }
	 * 
	 * if (scoreCurrentInfo != null && scoreCurrentInfo.isCurrentBlock()) {
	 * 
	 * if (!currentScorerIdList.isEmpty()) { query.append(
	 * "AND tranDescScore.latest_screen_scorer_id IN :CURRENT_SCORER_ID_LIST ");
	 * }
	 * 
	 * if (currentCategoryType != null && currentCategoryType == 2) {
	 * query.append(
	 * "AND tranDescScore.grade_seq IS null AND tranDescScore.pending_category_seq IS null "
	 * ); } else if (currentCategoryType != null && currentCategoryType == 3) {
	 * 
	 * if (!currentGradeSeqList.isEmpty() && ArrayUtils.contains(
	 * scoreCurrentInfo .getCurrentGradeNum(), -1)) {
	 * query.append("AND (tranDescScore.grade_seq IN :CURRENT_GRADE_SEQ_LIST ");
	 * query
	 * .append("OR tranDescScore.latest_scoring_state = :NO_GRADE_SCORING_STATE) "
	 * ); } else { if (!currentGradeSeqList.isEmpty()) {
	 * query.append("AND tranDescScore.grade_seq IN :CURRENT_GRADE_SEQ_LIST ");
	 * }
	 * 
	 * if (ArrayUtils.contains( scoreCurrentInfo .getCurrentGradeNum(), -1)) {
	 * query
	 * .append("AND tranDescScore.latest_scoring_state = :NO_GRADE_SCORING_STATE "
	 * ); } } } else if (currentCategoryType != null && currentCategoryType == 4
	 * && !currentPendingCategorySeqList .isEmpty()) { query.append(
	 * "AND tranDescScore.pending_category_seq IN :CURRENT_PENDING_CATEGORY_SEQ_LIST "
	 * ); }
	 * 
	 * if (!StringUtils .isBlank(currentIncludeCheckPoints) && !StringUtils
	 * .isBlank(currentExcludeCheckPoints)) { query.append(
	 * "AND (tranDescScore.bit_value & :CURRENT_INCLUDE_BIT_VALUE) > 0 ");
	 * query.
	 * append("AND (tranDescScore.bit_value & :CURRENT_EXCLUDE_BIT_VALUE) = 0 "
	 * ); } else { if (!StringUtils .isBlank(currentIncludeCheckPoints)) {
	 * query.
	 * append("AND (tranDescScore.bit_value & :CURRENT_INCLUDE_BIT_VALUE) > 0 "
	 * ); } else if (!StringUtils .isBlank(currentExcludeCheckPoints)) {
	 * query.append
	 * ("AND (tranDescScore.bit_value & :CURRENT_EXCLUDE_BIT_VALUE) = 0 "); } }
	 * 
	 * if (currentStateList != null && currentStateList.length > 0) {
	 * query.append
	 * ("AND tranDescScore.latest_scoring_state IN :CURRENT_STATE_LIST "); }
	 * 
	 * if (currentUpdateStartDate != null) {
	 * query.append("AND tranDescScore.update_date >= :CURRENT_UPDATE_START_DATE "
	 * ); }
	 * 
	 * if (currentUpdateEndDate != null) {
	 * query.append("AND tranDescScore.update_date <= :CURRENT_UPDATE_END_DATE "
	 * ); } } else if (menuId .equals(WebAppConst.SCORE_SAMP_MENU_ID) || menuId
	 * .equals(WebAppConst.STATE_TRAN_MENU_ID) || menuId
	 * .equals(WebAppConst.FORCED_MENU_ID)) {
	 * query.append("AND tranDescScore.latest_scoring_state IN :CURRENT_STATE_LIST "
	 * ); }
	 * 
	 * if ((scoreHistoryInfo == null || !scoreHistoryInfo .isHistoryBlock()) &&
	 * (scoreCurrentInfo == null || !scoreCurrentInfo .isCurrentBlock())) { if
	 * (menuId .equals(WebAppConst.REFERENCE_SAMP_MENU_ID)) { query.append(
	 * "AND tranDescScore.latest_scoring_state NOT IN :SPECIAL_SCORING_WAIT_STATES "
	 * ); } else if (menuId .equals(WebAppConst.SCORE_SAMP_MENU_ID)) {
	 * query.append
	 * ("AND tranDescScore.latest_scoring_state NOT IN :SPECIAL_SCORING_STATES "
	 * ); }
	 * 
	 * } if (!(menuId.equals(WebAppConst.STATE_TRAN_MENU_ID) || menuId
	 * .equals(WebAppConst.FORCED_MENU_ID))) { query.append(
	 * "AND tranDescScoreHistory.scoring_state NOT IN :DUMMY_SCORING_STATES ");
	 * query.append(
	 * "AND tranDescScore.latest_scoring_state NOT IN :DUMMY_SCORING_STATES ");
	 * } query.append("AND tranDescScore.valid_flag = :VALID_FLAG ");
	 * query.append("AND tranDescScoreHistory.valid_flag = :VALID_FLAG ");
	 * query.append("AND tranDescScore.answer_seq IN :ANSWER_SEQ_LIST ");
	 * query.append("GROUP BY tranDescScore.answer_seq "); if
	 * ((forceAndStateTransitionFlag != null) && (forceAndStateTransitionFlag ==
	 * WebAppConst.TRUE) && (menuId .equals(WebAppConst.FORCED_MENU_ID))) {
	 * query.append("ORDER BY tranDescScore.update_date DESC ");
	 * query.append("LIMIT "); if (endRecord != null) { if (startRecord > 0) {
	 * query.append(startRecord + " , " + endRecord); } else {
	 * query.append(endRecord + " "); } } } if
	 * (!(menuId.equals(WebAppConst.STATE_TRAN_MENU_ID) || menuId
	 * .equals(WebAppConst.FORCED_MENU_ID))) { query.append("ORDER BY RAND() ");
	 * query.append("ORDER BY tranDescScore.update_date DESC ");
	 * query.append("LIMIT :RECORD_COUNT_LIMIT "); query.append(") score ");
	 * query.append("WHERE t.answer_seq = score.answerSeq ");
	 * 
	 * } else if ((forceAndStateTransitionFlag != null) &&
	 * (forceAndStateTransitionFlag == WebAppConst.TRUE)) {
	 * query.append(") score ");
	 * query.append("WHERE t.answer_seq = score.answerSeq "); if
	 * (!menuId.equals(WebAppConst.FORCED_MENU_ID)) {
	 * query.append("ORDER BY updateDate DESC "); } } else if
	 * ((forceAndStateTransitionFlag != null) && (forceAndStateTransitionFlag ==
	 * WebAppConst.FALSE)) { query.append(") score "); }
	 * 
	 * SQLQuery queryObj = session.createSQLQuery(query .toString());
	 * 
	 * if (menuId.equals(WebAppConst.STATE_TRAN_MENU_ID)) {
	 * queryObj.setParameter("INSPECT_FLAG", WebAppConst.F); }
	 * 
	 * if (menuId.equals(WebAppConst.SCORE_SAMP_MENU_ID)) { if (samplingFlag ==
	 * WebAppConst.TRUE) { queryObj.setParameterList( "SAMPLING_FLAG_LIST",
	 * WebAppConst.SCORE_SAMPLING_FLAG_LIST); } else {
	 * queryObj.setParameterList( "SAMPLING_FLAG_LIST", ArrayUtils .subarray(
	 * WebAppConst.SCORE_SAMPLING_FLAG_LIST, 0, 1)); } }
	 * 
	 * if (!StringUtils.isBlank(answerFormNum)) {
	 * queryObj.setParameter("ANSWER_FORM_NUM", answerFormNum); }
	 * 
	 * setHistoryParameters(queryObj);
	 * 
	 * setCurrentParameters(queryObj);
	 * 
	 * queryObj.setInteger("QUESTION_SEQ", questionInfo.getQuestionSeq());
	 * queryObj.setCharacter("UNLOCK", WebAppConst.UNLOCK);
	 * queryObj.setCharacter("VALID_FLAG", WebAppConst.VALID_FLAG); if
	 * (!(menuId.equals(WebAppConst.STATE_TRAN_MENU_ID) || menuId
	 * .equals(WebAppConst.FORCED_MENU_ID))) {
	 * queryObj.setInteger("RECORD_COUNT_LIMIT",
	 * scoreInputInfo.getResultCount());
	 * queryObj.setParameterList("ANSWER_SEQ_LIST", answerSeqList);
	 * 
	 * }
	 * 
	 * if ((scoreHistoryInfo == null || !scoreHistoryInfo .isHistoryBlock()) &&
	 * (scoreCurrentInfo == null || !scoreCurrentInfo .isCurrentBlock())) { if
	 * (menuId .equals(WebAppConst.REFERENCE_SAMP_MENU_ID)) {
	 * queryObj.setParameterList( "SPECIAL_SCORING_WAIT_STATES",
	 * WebAppConst.SPECIAL_SCORING_WAIT_STATES); } else if (menuId
	 * .equals(WebAppConst.SCORE_SAMP_MENU_ID)) { queryObj.setParameterList(
	 * "SPECIAL_SCORING_STATES", WebAppConst.SPECIAL_SCORING_STATES); }
	 * 
	 * } if (!(menuId.equals(WebAppConst.STATE_TRAN_MENU_ID) || menuId
	 * .equals(WebAppConst.FORCED_MENU_ID))) { queryObj.setParameterList(
	 * "DUMMY_SCORING_STATES", WebAppConst.DUMMY_SCORING_STATES); }
	 * 
	 * 
	 * if ((forceAndStateTransitionFlag != null) && (forceAndStateTransitionFlag
	 * == WebAppConst.TRUE) && (menuId .equals(WebAppConst.FORCED_MENU_ID))) {
	 * if (endRecord != null) { queryObj.setFirstResult(startRecord);
	 * queryObj.setMaxResults(endRecord); } }
	 * 
	 * 
	 * return queryObj.list();
	 * 
	 * }
	 * 
	 * public void setCurrentParameters(SQLQuery queryObj) {
	 * 
	 * if (scoreCurrentInfo != null && scoreCurrentInfo.isCurrentBlock()) { if
	 * (!currentScorerIdList.isEmpty()) { queryObj.setParameterList(
	 * "CURRENT_SCORER_ID_LIST", currentScorerIdList); }
	 * 
	 * if (currentCategoryType != null && currentCategoryType == 3) {
	 * 
	 * if (!currentGradeSeqList.isEmpty()) { queryObj.setParameterList(
	 * "CURRENT_GRADE_SEQ_LIST", currentGradeSeqList); }
	 * 
	 * if (ArrayUtils.contains(scoreCurrentInfo .getCurrentGradeNum(), -1)) {
	 * queryObj.setShort( "NO_GRADE_SCORING_STATE",
	 * WebAppConst.NO_GRADE_SCORING_STATE); } } else if (currentCategoryType !=
	 * null && currentCategoryType == 4 && !currentPendingCategorySeqList
	 * .isEmpty()) {
	 * 
	 * queryObj.setParameterList( "CURRENT_PENDING_CATEGORY_SEQ_LIST",
	 * scoreCurrentInfo .getCurrentPendingCategorySeqList()); }
	 * 
	 * if (!StringUtils .isBlank(currentIncludeCheckPoints) && !StringUtils
	 * .isBlank(currentExcludeCheckPoints)) { queryObj.setInteger(
	 * "CURRENT_INCLUDE_BIT_VALUE", scoreCurrentInfo
	 * .getCurrentIncludeBitValue()); queryObj.setInteger(
	 * "CURRENT_EXCLUDE_BIT_VALUE", scoreCurrentInfo
	 * .getCurrentExcludeBitValue()); } else { if (!StringUtils
	 * .isBlank(currentIncludeCheckPoints)) { queryObj.setInteger(
	 * "CURRENT_INCLUDE_BIT_VALUE", scoreCurrentInfo
	 * .getCurrentIncludeBitValue()); } else if (!StringUtils
	 * .isBlank(currentExcludeCheckPoints)) { queryObj.setInteger(
	 * "CURRENT_EXCLUDE_BIT_VALUE", scoreCurrentInfo
	 * .getCurrentExcludeBitValue()); } }
	 * 
	 * if (currentStateList != null && currentStateList.length > 0) {
	 * queryObj.setParameterList( "CURRENT_STATE_LIST", scoreCurrentInfo
	 * .getCurrentStateList()); }
	 * 
	 * if (currentUpdateStartDate != null) { queryObj.setParameter(
	 * "CURRENT_UPDATE_START_DATE", currentUpdateStartDate); }
	 * 
	 * if (currentUpdateEndDate != null) { queryObj.setParameter(
	 * "CURRENT_UPDATE_END_DATE", currentUpdateEndDate); } } else if (menuId
	 * .equals(WebAppConst.SCORE_SAMP_MENU_ID)) { queryObj.setParameterList(
	 * "CURRENT_STATE_LIST", WebAppConst.SCORE_SAMPLING_CURRENT_STATES); } else
	 * if (menuId .equals(WebAppConst.STATE_TRAN_MENU_ID)) {
	 * queryObj.setParameterList( "CURRENT_STATE_LIST",
	 * WebAppConst.STATE_TRANSITION_CURRENT_STATES); } else if (menuId
	 * .equals(WebAppConst.FORCED_MENU_ID)) { queryObj.setParameterList(
	 * "CURRENT_STATE_LIST", WebAppConst.FORCED_SCORING_CURRENT_STATES); }
	 * 
	 * }
	 * 
	 * public void setHistoryParameters(SQLQuery queryObj) {
	 * 
	 * if (scoreHistoryInfo != null && scoreHistoryInfo.isHistoryBlock()) {
	 * 
	 * if (!historyScorerIdList.isEmpty()) { queryObj.setParameterList(
	 * "HISTORY_SCORER_ID_LIST", historyScorerIdList); }
	 * 
	 * if (historyCategoryType != null && historyCategoryType == 3) {
	 * 
	 * if (!historyGradeSeqList.isEmpty()) { queryObj.setParameterList(
	 * "HISTORY_GRADE_SEQ_LIST", historyGradeSeqList); }
	 * 
	 * if (ArrayUtils.contains(scoreHistoryInfo .getHistoryGradeNum(), -1)) {
	 * queryObj.setShort( "NO_GRADE_SCORING_STATE",
	 * WebAppConst.NO_GRADE_SCORING_STATE); } } else if (historyCategoryType !=
	 * null && historyCategoryType == 4 && !historyPendingCategorySeqList
	 * .isEmpty()) {
	 * 
	 * queryObj.setParameterList( "HISTORY_PENDING_CATEGORY_SEQ_LIST",
	 * historyPendingCategorySeqList); }
	 * 
	 * if (!StringUtils .isBlank(historyIncludeCheckPoints) && !StringUtils
	 * .isBlank(historyExcludeCheckPoints)) { queryObj.setInteger(
	 * "HISTORY_INCLUDE_BIT_VALUE", scoreHistoryInfo
	 * .getHistoryIncludeBitValue()); queryObj.setInteger(
	 * "HISTORY_EXCLUDE_BIT_VALUE", scoreHistoryInfo
	 * .getHistoryExcludeBitValue()); } else { if (!StringUtils
	 * .isBlank(historyIncludeCheckPoints)) { queryObj.setInteger(
	 * "HISTORY_INCLUDE_BIT_VALUE", scoreHistoryInfo
	 * .getHistoryIncludeBitValue()); } else if (!StringUtils
	 * .isBlank(historyExcludeCheckPoints)) { queryObj.setInteger(
	 * "HISTORY_EXCLUDE_BIT_VALUE", scoreHistoryInfo
	 * .getHistoryExcludeBitValue()); } }
	 * 
	 * if (historyEventList != null && historyEventList.length > 0) {
	 * queryObj.setParameterList( "HISTORY_EVENT_LIST", historyEventList); }
	 * 
	 * if (historyUpdateStartDate != null) { queryObj.setParameter(
	 * "HISTORY_UPDATE_START_DATE", historyUpdateStartDate); }
	 * 
	 * if (historyUpdateEndDate != null) { queryObj.setParameter(
	 * "HISTORY_UPDATE_END_DATE", historyUpdateEndDate); }
	 * 
	 * }
	 * 
	 * } }); System.out.println(">>>>>>>>>>>>>> searchAnswerRecords end: "+new
	 * Date().getTime()); return answerRecordsList; } catch (RuntimeException
	 * re) { throw re; } }
	 * 
	 * 
	 * @SuppressWarnings("rawtypes") private List getRandomAnswerSequences(final
	 * QuestionInfo questionInfo, final ScoreInputInfo scoreInputInfo, final
	 * Boolean forceAndStateTransitionFlag, final Integer startRecord, final
	 * Integer endRecord) {
	 * 
	 * final ScoreHistoryInfo scoreHistoryInfo = scoreInputInfo
	 * .getScoreHistoryInfo();
	 * 
	 * final String menuId = questionInfo.getMenuId(); final boolean
	 * samplingFlag = scoreInputInfo.isSamplingFlag(); final String
	 * answerFormNum = scoreInputInfo.getAnswerFormNum();
	 * 
	 * final List<String> historyScorerIdList = scoreHistoryInfo != null ?
	 * scoreHistoryInfo .getHistoryScorerIdList() : null; final Integer
	 * historyCategoryType = scoreHistoryInfo != null ? scoreHistoryInfo
	 * .getHistoryCategoryType() : null; final List historyGradeSeqList =
	 * scoreHistoryInfo != null ? scoreHistoryInfo .getHistoryGradeSeqList() :
	 * null; final List historyPendingCategorySeqList = scoreHistoryInfo != null
	 * ? scoreHistoryInfo .getHistoryPendingCategorySeqList() : null; final
	 * String historyIncludeCheckPoints = scoreHistoryInfo != null ?
	 * scoreHistoryInfo .getHistoryIncludeCheckPoints() : null; final String
	 * historyExcludeCheckPoints = scoreHistoryInfo != null ? scoreHistoryInfo
	 * .getHistoryExcludeCheckPoints() : null; final Short[] historyEventList =
	 * scoreHistoryInfo != null ? scoreHistoryInfo .getHistoryEventList() :
	 * null; final Date historyUpdateStartDate = scoreHistoryInfo != null ?
	 * scoreHistoryInfo .getHistoryUpdateStartDate() : null; final Date
	 * historyUpdateEndDate = scoreHistoryInfo != null ? scoreHistoryInfo
	 * .getHistoryUpdateEndDate() : null;
	 * 
	 * final ScoreCurrentInfo scoreCurrentInfo = scoreInputInfo
	 * .getScoreCurrentInfo();
	 * 
	 * final List<String> currentScorerIdList = scoreCurrentInfo != null ?
	 * scoreCurrentInfo .getCurrentScorerIdList() : null; final Integer
	 * currentCategoryType = scoreCurrentInfo != null ? scoreCurrentInfo
	 * .getCurrentCategoryType() : null; final List currentGradeSeqList =
	 * scoreCurrentInfo != null ? scoreCurrentInfo .getCurrentGradeSeqList() :
	 * null; final List currentPendingCategorySeqList = scoreCurrentInfo != null
	 * ? scoreCurrentInfo .getCurrentPendingCategorySeqList() : null; final
	 * String currentIncludeCheckPoints = scoreCurrentInfo != null ?
	 * scoreCurrentInfo .getCurrentIncludeCheckPoints() : null; final String
	 * currentExcludeCheckPoints = scoreCurrentInfo != null ? scoreCurrentInfo
	 * .getCurrentExcludeCheckPoints() : null; final Short[] currentStateList =
	 * scoreCurrentInfo != null ? scoreCurrentInfo .getCurrentStateList() :
	 * null; final Date currentUpdateStartDate = scoreCurrentInfo != null ?
	 * scoreCurrentInfo .getCurrentUpdateStartDate() : null; final Date
	 * currentUpdateEndDate = scoreCurrentInfo != null ? scoreCurrentInfo
	 * .getCurrentUpdateEndDate() : null;
	 * 
	 * try { List answerRecordsList = getHibernateTemplate(
	 * questionInfo.getConnectionString()).execute( new
	 * HibernateCallback<List>() {
	 * 
	 * public List doInHibernate(Session session) throws HibernateException {
	 * StringBuilder query = new StringBuilder(); if
	 * ((forceAndStateTransitionFlag == null) || (forceAndStateTransitionFlag ==
	 * WebAppConst.TRUE)) { query.append(
	 * "SELECT t.answer_seq AS answerSeq, t.answer_form_num AS answerFormNum, "
	 * );
	 * query.append("t.image_file_name AS imageFileName, t.grade_seq AS gradeSeq, "
	 * ); query.append(
	 * "t.bit_value AS bitValue, t.latest_scorer_id AS latestScorerId, "); if
	 * (!(menuId .equals(WebAppConst.STATE_TRAN_MENU_ID) || menuId
	 * .equals(WebAppConst.FORCED_MENU_ID))) { query.append(
	 * "(SELECT scorer_comment FROM tran_desc_score_history AS scoreHistory ");
	 * query.append("WHERE scoreHistory.answer_seq = t.answer_seq ");
	 * query.append("AND scoreHistory.update_date = "); query.append(
	 * "(SELECT max(history.update_date) FROM tran_desc_score_history history "
	 * ); query.append(
	 * "WHERE history.answer_seq = scoreHistory.answer_seq)) AS scorerComment, "
	 * ); } query.append("t.punch_text AS punchText, "); query.append(
	 * "t.pending_category_seq AS pendingCategorySeq, t.latest_scoring_state, "
	 * ); if (((forceAndStateTransitionFlag != null) &&
	 * (forceAndStateTransitionFlag == WebAppConst.TRUE)) || (menuId
	 * .equals(WebAppConst.REFERENCE_SAMP_MENU_ID) &&
	 * (forceAndStateTransitionFlag == null))) { query.append(
	 * "t.question_seq AS questionSeq, t.latest_screen_scorer_id AS latestScreenScorerId, "
	 * ); } if (menuId.equals(WebAppConst.FORCED_MENU_ID) &&
	 * (forceAndStateTransitionFlag != null && forceAndStateTransitionFlag ==
	 * WebAppConst.TRUE)) {
	 * query.append("(SELECT count(*) FROM tran_desc_score_history AS scoreHistory "
	 * ); query.append("WHERE scoreHistory.answer_seq = t.answer_seq ");
	 * query.append("AND scoreHistory.question_seq = :QUESTION_SEQ ");
	 * query.append(
	 * "AND scoreHistory.scorer_comment != '' AND scoreHistory.scorer_comment is not null ) AS commentCount, "
	 * ); } query.append("t.update_date AS updateDate ");
	 * 
	 * query.append("FROM  tran_desc_score AS t, ( ");
	 * query.append("SELECT tranDescScore.answer_seq AS answerSeq "); } else {
	 * query.append("SELECT SUM(rowCount) "); query.append(
	 * "FROM (SELECT count(distinct tranDescScore.answer_seq) as rowCount "); }
	 * query.append("FROM tran_desc_score AS tranDescScore ");
	 * 
	 * query.append("INNER JOIN ");
	 * query.append("tran_desc_score_history AS tranDescScoreHistory ");
	 * query.append
	 * ("ON tranDescScore.answer_seq = tranDescScoreHistory.answer_seq ");
	 * query.append("WHERE tranDescScore.question_seq  = :QUESTION_SEQ ");
	 * query.append("AND tranDescScoreHistory.question_seq  = :QUESTION_SEQ ");
	 * query.append("AND tranDescScore.lock_flag = :UNLOCK ");
	 * 
	 * if (menuId.equals(WebAppConst.STATE_TRAN_MENU_ID)) {
	 * query.append("AND tranDescScore.inspect_flag = :INSPECT_FLAG "); }
	 * 
	 * if (menuId.equals(WebAppConst.SCORE_SAMP_MENU_ID)) {
	 * query.append("AND tranDescScore.sampling_flag IN :SAMPLING_FLAG_LIST ");
	 * }
	 * 
	 * if (!StringUtils.isBlank(answerFormNum)) {
	 * query.append("AND tranDescScore.answer_form_num = :ANSWER_FORM_NUM "); }
	 * 
	 * if (scoreHistoryInfo != null && scoreHistoryInfo.isHistoryBlock()) {
	 * 
	 * if (!historyScorerIdList.isEmpty()) { query.append(
	 * "AND tranDescScoreHistory.latest_screen_scorer_id IN :HISTORY_SCORER_ID_LIST "
	 * ); }
	 * 
	 * if (historyCategoryType != null && historyCategoryType == 2) {
	 * query.append(
	 * "AND tranDescScoreHistory.grade_seq IS null AND tranDescScoreHistory.pending_category_seq IS null "
	 * ); } else if (historyCategoryType != null && historyCategoryType == 3) {
	 * 
	 * if (!historyGradeSeqList.isEmpty() && ArrayUtils.contains(
	 * scoreHistoryInfo .getHistoryGradeNum(), -1)) { query.append(
	 * "AND (tranDescScoreHistory.grade_seq IN :HISTORY_GRADE_SEQ_LIST ");
	 * query.
	 * append("OR tranDescScoreHistory.scoring_state = :NO_GRADE_SCORING_STATE) "
	 * ); } else { if (!historyGradeSeqList.isEmpty()) {
	 * query.append("AND tranDescScoreHistory.grade_seq IN :HISTORY_GRADE_SEQ_LIST "
	 * ); }
	 * 
	 * if (ArrayUtils.contains( scoreHistoryInfo .getHistoryGradeNum(), -1)) {
	 * query
	 * .append("AND tranDescScoreHistory.scoring_state = :NO_GRADE_SCORING_STATE "
	 * ); } } } else if (historyCategoryType != null && historyCategoryType == 4
	 * && !historyPendingCategorySeqList .isEmpty()) { query.append(
	 * "AND tranDescScoreHistory.pending_category_seq IN :HISTORY_PENDING_CATEGORY_SEQ_LIST "
	 * ); }
	 * 
	 * if (!StringUtils .isBlank(historyIncludeCheckPoints) && !StringUtils
	 * .isBlank(historyExcludeCheckPoints)) { query.append(
	 * "AND (tranDescScoreHistory.bit_value & :HISTORY_INCLUDE_BIT_VALUE) > 0 "
	 * ); query.append(
	 * "AND (tranDescScoreHistory.bit_value & :HISTORY_EXCLUDE_BIT_VALUE) = 0 "
	 * ); } else { if (!StringUtils .isBlank(historyIncludeCheckPoints)) {
	 * query.append(
	 * "AND (tranDescScoreHistory.bit_value & :HISTORY_INCLUDE_BIT_VALUE) > 0 "
	 * ); } else if (!StringUtils .isBlank(historyExcludeCheckPoints)) {
	 * query.append
	 * ("AND (tranDescScoreHistory.bit_value & :HISTORY_EXCLUDE_BIT_VALUE) = 0 "
	 * ); } }
	 * 
	 * if (historyEventList != null && historyEventList.length > 0) {
	 * query.append
	 * ("AND tranDescScoreHistory.event_id IN :HISTORY_EVENT_LIST "); }
	 * 
	 * if (historyUpdateStartDate != null) { query.append(
	 * "AND tranDescScoreHistory.update_date >= :HISTORY_UPDATE_START_DATE "); }
	 * 
	 * if (historyUpdateEndDate != null) { query.append(
	 * "AND tranDescScoreHistory.update_date <= :HISTORY_UPDATE_END_DATE "); } }
	 * 
	 * if (scoreCurrentInfo != null && scoreCurrentInfo.isCurrentBlock()) {
	 * 
	 * if (!currentScorerIdList.isEmpty()) { query.append(
	 * "AND tranDescScore.latest_screen_scorer_id IN :CURRENT_SCORER_ID_LIST ");
	 * }
	 * 
	 * if (currentCategoryType != null && currentCategoryType == 2) {
	 * query.append(
	 * "AND tranDescScore.grade_seq IS null AND tranDescScore.pending_category_seq IS null "
	 * ); } else if (currentCategoryType != null && currentCategoryType == 3) {
	 * 
	 * if (!currentGradeSeqList.isEmpty() && ArrayUtils.contains(
	 * scoreCurrentInfo .getCurrentGradeNum(), -1)) {
	 * query.append("AND (tranDescScore.grade_seq IN :CURRENT_GRADE_SEQ_LIST ");
	 * query
	 * .append("OR tranDescScore.latest_scoring_state = :NO_GRADE_SCORING_STATE) "
	 * ); } else { if (!currentGradeSeqList.isEmpty()) {
	 * query.append("AND tranDescScore.grade_seq IN :CURRENT_GRADE_SEQ_LIST ");
	 * }
	 * 
	 * if (ArrayUtils.contains( scoreCurrentInfo .getCurrentGradeNum(), -1)) {
	 * query
	 * .append("AND tranDescScore.latest_scoring_state = :NO_GRADE_SCORING_STATE "
	 * ); } } } else if (currentCategoryType != null && currentCategoryType == 4
	 * && !currentPendingCategorySeqList .isEmpty()) { query.append(
	 * "AND tranDescScore.pending_category_seq IN :CURRENT_PENDING_CATEGORY_SEQ_LIST "
	 * ); }
	 * 
	 * if (!StringUtils .isBlank(currentIncludeCheckPoints) && !StringUtils
	 * .isBlank(currentExcludeCheckPoints)) { query.append(
	 * "AND (tranDescScore.bit_value & :CURRENT_INCLUDE_BIT_VALUE) > 0 ");
	 * query.
	 * append("AND (tranDescScore.bit_value & :CURRENT_EXCLUDE_BIT_VALUE) = 0 "
	 * ); } else { if (!StringUtils .isBlank(currentIncludeCheckPoints)) {
	 * query.
	 * append("AND (tranDescScore.bit_value & :CURRENT_INCLUDE_BIT_VALUE) > 0 "
	 * ); } else if (!StringUtils .isBlank(currentExcludeCheckPoints)) {
	 * query.append
	 * ("AND (tranDescScore.bit_value & :CURRENT_EXCLUDE_BIT_VALUE) = 0 "); } }
	 * 
	 * if (currentStateList != null && currentStateList.length > 0) {
	 * query.append
	 * ("AND tranDescScore.latest_scoring_state IN :CURRENT_STATE_LIST "); }
	 * 
	 * if (currentUpdateStartDate != null) {
	 * query.append("AND tranDescScore.update_date >= :CURRENT_UPDATE_START_DATE "
	 * ); }
	 * 
	 * if (currentUpdateEndDate != null) {
	 * query.append("AND tranDescScore.update_date <= :CURRENT_UPDATE_END_DATE "
	 * ); } } else if (menuId .equals(WebAppConst.SCORE_SAMP_MENU_ID) || menuId
	 * .equals(WebAppConst.STATE_TRAN_MENU_ID) || menuId
	 * .equals(WebAppConst.FORCED_MENU_ID)) {
	 * query.append("AND tranDescScore.latest_scoring_state IN :CURRENT_STATE_LIST "
	 * ); }
	 * 
	 * if ((scoreHistoryInfo == null || !scoreHistoryInfo .isHistoryBlock()) &&
	 * (scoreCurrentInfo == null || !scoreCurrentInfo .isCurrentBlock())) { if
	 * (menuId .equals(WebAppConst.REFERENCE_SAMP_MENU_ID)) { query.append(
	 * "AND tranDescScore.latest_scoring_state NOT IN :SPECIAL_SCORING_WAIT_STATES "
	 * ); } else if (menuId .equals(WebAppConst.SCORE_SAMP_MENU_ID)) {
	 * query.append
	 * ("AND tranDescScore.latest_scoring_state NOT IN :SPECIAL_SCORING_STATES "
	 * ); }
	 * 
	 * } if (!(menuId.equals(WebAppConst.STATE_TRAN_MENU_ID) || menuId
	 * .equals(WebAppConst.FORCED_MENU_ID))) { query.append(
	 * "AND tranDescScoreHistory.scoring_state NOT IN :DUMMY_SCORING_STATES ");
	 * query.append(
	 * "AND tranDescScore.latest_scoring_state NOT IN :DUMMY_SCORING_STATES ");
	 * } query.append("AND tranDescScore.valid_flag = :VALID_FLAG ");
	 * query.append("AND tranDescScoreHistory.valid_flag = :VALID_FLAG ");
	 * query.append("GROUP BY tranDescScore.answer_seq "); if
	 * ((forceAndStateTransitionFlag != null) && (forceAndStateTransitionFlag ==
	 * WebAppConst.TRUE) && (menuId .equals(WebAppConst.FORCED_MENU_ID))) {
	 * query.append("ORDER BY tranDescScore.update_date DESC ");
	 * query.append("LIMIT "); if (endRecord != null) { if (startRecord > 0) {
	 * query.append(startRecord + " , " + endRecord); } else {
	 * query.append(endRecord + " "); } } } if
	 * (!(menuId.equals(WebAppConst.STATE_TRAN_MENU_ID) || menuId
	 * .equals(WebAppConst.FORCED_MENU_ID))) { query.append("ORDER BY RAND() ");
	 * query.append("LIMIT :RECORD_COUNT_LIMIT "); query.append(") score ");
	 * query.append("WHERE t.answer_seq = score.answerSeq ");
	 * query.append("ORDER BY t.update_date DESC "); } else if
	 * ((forceAndStateTransitionFlag != null) && (forceAndStateTransitionFlag ==
	 * WebAppConst.TRUE)) { query.append(") score ");
	 * query.append("WHERE t.answer_seq = score.answerSeq "); if
	 * (!menuId.equals(WebAppConst.FORCED_MENU_ID)) {
	 * query.append("ORDER BY updateDate DESC "); } } else if
	 * ((forceAndStateTransitionFlag != null) && (forceAndStateTransitionFlag ==
	 * WebAppConst.FALSE)) { query.append(") score "); }
	 * 
	 * SQLQuery queryObj = session.createSQLQuery(query .toString());
	 * 
	 * if (menuId.equals(WebAppConst.STATE_TRAN_MENU_ID)) {
	 * queryObj.setParameter("INSPECT_FLAG", WebAppConst.F); }
	 * 
	 * if (menuId.equals(WebAppConst.SCORE_SAMP_MENU_ID)) { if (samplingFlag ==
	 * WebAppConst.TRUE) { queryObj.setParameterList( "SAMPLING_FLAG_LIST",
	 * WebAppConst.SCORE_SAMPLING_FLAG_LIST); } else {
	 * queryObj.setParameterList( "SAMPLING_FLAG_LIST", ArrayUtils .subarray(
	 * WebAppConst.SCORE_SAMPLING_FLAG_LIST, 0, 1)); } }
	 * 
	 * if (!StringUtils.isBlank(answerFormNum)) {
	 * queryObj.setParameter("ANSWER_FORM_NUM", answerFormNum); }
	 * 
	 * setHistoryParameters(queryObj);
	 * 
	 * setCurrentParameters(queryObj);
	 * 
	 * queryObj.setInteger("QUESTION_SEQ", questionInfo.getQuestionSeq());
	 * queryObj.setCharacter("UNLOCK", WebAppConst.UNLOCK);
	 * queryObj.setCharacter("VALID_FLAG", WebAppConst.VALID_FLAG); if
	 * (!(menuId.equals(WebAppConst.STATE_TRAN_MENU_ID) || menuId
	 * .equals(WebAppConst.FORCED_MENU_ID))) {
	 * queryObj.setInteger("RECORD_COUNT_LIMIT",
	 * scoreInputInfo.getResultCount()); }
	 * 
	 * if ((scoreHistoryInfo == null || !scoreHistoryInfo .isHistoryBlock()) &&
	 * (scoreCurrentInfo == null || !scoreCurrentInfo .isCurrentBlock())) { if
	 * (menuId .equals(WebAppConst.REFERENCE_SAMP_MENU_ID)) {
	 * queryObj.setParameterList( "SPECIAL_SCORING_WAIT_STATES",
	 * WebAppConst.SPECIAL_SCORING_WAIT_STATES); } else if (menuId
	 * .equals(WebAppConst.SCORE_SAMP_MENU_ID)) { queryObj.setParameterList(
	 * "SPECIAL_SCORING_STATES", WebAppConst.SPECIAL_SCORING_STATES); }
	 * 
	 * } if (!(menuId.equals(WebAppConst.STATE_TRAN_MENU_ID) || menuId
	 * .equals(WebAppConst.FORCED_MENU_ID))) { queryObj.setParameterList(
	 * "DUMMY_SCORING_STATES", WebAppConst.DUMMY_SCORING_STATES); }
	 * 
	 * 
	 * if ((forceAndStateTransitionFlag != null) && (forceAndStateTransitionFlag
	 * == WebAppConst.TRUE) && (menuId .equals(WebAppConst.FORCED_MENU_ID))) {
	 * if (endRecord != null) { queryObj.setFirstResult(startRecord);
	 * queryObj.setMaxResults(endRecord); } }
	 * 
	 * 
	 * return queryObj.list();
	 * 
	 * }
	 * 
	 * public void setCurrentParameters(SQLQuery queryObj) {
	 * 
	 * if (scoreCurrentInfo != null && scoreCurrentInfo.isCurrentBlock()) { if
	 * (!currentScorerIdList.isEmpty()) { queryObj.setParameterList(
	 * "CURRENT_SCORER_ID_LIST", currentScorerIdList); }
	 * 
	 * if (currentCategoryType != null && currentCategoryType == 3) {
	 * 
	 * if (!currentGradeSeqList.isEmpty()) { queryObj.setParameterList(
	 * "CURRENT_GRADE_SEQ_LIST", currentGradeSeqList); }
	 * 
	 * if (ArrayUtils.contains(scoreCurrentInfo .getCurrentGradeNum(), -1)) {
	 * queryObj.setShort( "NO_GRADE_SCORING_STATE",
	 * WebAppConst.NO_GRADE_SCORING_STATE); } } else if (currentCategoryType !=
	 * null && currentCategoryType == 4 && !currentPendingCategorySeqList
	 * .isEmpty()) {
	 * 
	 * queryObj.setParameterList( "CURRENT_PENDING_CATEGORY_SEQ_LIST",
	 * scoreCurrentInfo .getCurrentPendingCategorySeqList()); }
	 * 
	 * if (!StringUtils .isBlank(currentIncludeCheckPoints) && !StringUtils
	 * .isBlank(currentExcludeCheckPoints)) { queryObj.setInteger(
	 * "CURRENT_INCLUDE_BIT_VALUE", scoreCurrentInfo
	 * .getCurrentIncludeBitValue()); queryObj.setInteger(
	 * "CURRENT_EXCLUDE_BIT_VALUE", scoreCurrentInfo
	 * .getCurrentExcludeBitValue()); } else { if (!StringUtils
	 * .isBlank(currentIncludeCheckPoints)) { queryObj.setInteger(
	 * "CURRENT_INCLUDE_BIT_VALUE", scoreCurrentInfo
	 * .getCurrentIncludeBitValue()); } else if (!StringUtils
	 * .isBlank(currentExcludeCheckPoints)) { queryObj.setInteger(
	 * "CURRENT_EXCLUDE_BIT_VALUE", scoreCurrentInfo
	 * .getCurrentExcludeBitValue()); } }
	 * 
	 * if (currentStateList != null && currentStateList.length > 0) {
	 * queryObj.setParameterList( "CURRENT_STATE_LIST", scoreCurrentInfo
	 * .getCurrentStateList()); }
	 * 
	 * if (currentUpdateStartDate != null) { queryObj.setParameter(
	 * "CURRENT_UPDATE_START_DATE", currentUpdateStartDate); }
	 * 
	 * if (currentUpdateEndDate != null) { queryObj.setParameter(
	 * "CURRENT_UPDATE_END_DATE", currentUpdateEndDate); } } else if (menuId
	 * .equals(WebAppConst.SCORE_SAMP_MENU_ID)) { queryObj.setParameterList(
	 * "CURRENT_STATE_LIST", WebAppConst.SCORE_SAMPLING_CURRENT_STATES); } else
	 * if (menuId .equals(WebAppConst.STATE_TRAN_MENU_ID)) {
	 * queryObj.setParameterList( "CURRENT_STATE_LIST",
	 * WebAppConst.STATE_TRANSITION_CURRENT_STATES); } else if (menuId
	 * .equals(WebAppConst.FORCED_MENU_ID)) { queryObj.setParameterList(
	 * "CURRENT_STATE_LIST", WebAppConst.FORCED_SCORING_CURRENT_STATES); }
	 * 
	 * }
	 * 
	 * public void setHistoryParameters(SQLQuery queryObj) {
	 * 
	 * if (scoreHistoryInfo != null && scoreHistoryInfo.isHistoryBlock()) {
	 * 
	 * if (!historyScorerIdList.isEmpty()) { queryObj.setParameterList(
	 * "HISTORY_SCORER_ID_LIST", historyScorerIdList); }
	 * 
	 * if (historyCategoryType != null && historyCategoryType == 3) {
	 * 
	 * if (!historyGradeSeqList.isEmpty()) { queryObj.setParameterList(
	 * "HISTORY_GRADE_SEQ_LIST", historyGradeSeqList); }
	 * 
	 * if (ArrayUtils.contains(scoreHistoryInfo .getHistoryGradeNum(), -1)) {
	 * queryObj.setShort( "NO_GRADE_SCORING_STATE",
	 * WebAppConst.NO_GRADE_SCORING_STATE); } } else if (historyCategoryType !=
	 * null && historyCategoryType == 4 && !historyPendingCategorySeqList
	 * .isEmpty()) {
	 * 
	 * queryObj.setParameterList( "HISTORY_PENDING_CATEGORY_SEQ_LIST",
	 * historyPendingCategorySeqList); }
	 * 
	 * if (!StringUtils .isBlank(historyIncludeCheckPoints) && !StringUtils
	 * .isBlank(historyExcludeCheckPoints)) { queryObj.setInteger(
	 * "HISTORY_INCLUDE_BIT_VALUE", scoreHistoryInfo
	 * .getHistoryIncludeBitValue()); queryObj.setInteger(
	 * "HISTORY_EXCLUDE_BIT_VALUE", scoreHistoryInfo
	 * .getHistoryExcludeBitValue()); } else { if (!StringUtils
	 * .isBlank(historyIncludeCheckPoints)) { queryObj.setInteger(
	 * "HISTORY_INCLUDE_BIT_VALUE", scoreHistoryInfo
	 * .getHistoryIncludeBitValue()); } else if (!StringUtils
	 * .isBlank(historyExcludeCheckPoints)) { queryObj.setInteger(
	 * "HISTORY_EXCLUDE_BIT_VALUE", scoreHistoryInfo
	 * .getHistoryExcludeBitValue()); } }
	 * 
	 * if (historyEventList != null && historyEventList.length > 0) {
	 * queryObj.setParameterList( "HISTORY_EVENT_LIST", historyEventList); }
	 * 
	 * if (historyUpdateStartDate != null) { queryObj.setParameter(
	 * "HISTORY_UPDATE_START_DATE", historyUpdateStartDate); }
	 * 
	 * if (historyUpdateEndDate != null) { queryObj.setParameter(
	 * "HISTORY_UPDATE_END_DATE", historyUpdateEndDate); }
	 * 
	 * }
	 * 
	 * } });
	 * 
	 * Collections.shuffle(answerRecordsList); answerRecordsList =
	 * answerRecordsList.subList(0, scoreInputInfo.getResultCount()+1000);
	 * return answerRecordsList; } catch (RuntimeException re) { throw re; } }
	 */

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<Double> getFirstTimeSecondTimeCheckPoints(final int answerSeq,
			final Short[] latestScoringState, final QuestionInfo questionInfo,
			final Short currentScoringState) {

		try {
			@SuppressWarnings("rawtypes")
			List<Double> bitValueList = getHibernateTemplate(
					questionInfo.getConnectionString()).execute(
					new HibernateCallback<List>() {

						@SuppressWarnings("rawtypes")
						public List doInHibernate(Session session)
								throws HibernateException {
							StringBuilder query = new StringBuilder();
							query.append("SELECT  bit_value ");
							query.append("FROM tran_desc_score_history  ");
							query.append("where answer_seq = :ANSWER_SEQ ");
							query.append("AND scoring_state IN :SCORING_STATE order by history_seq desc ");
							if ((ArrayUtils.contains(
									WebAppConst.DENY_SCORING_STATES,
									currentScoringState) || questionInfo
									.getMenuId().equals(
											WebAppConst.DENY_MENU_ID))
									&& (questionInfo.getQuestionType() == WebAppConst.SPEAKING_TYPE || questionInfo
											.getQuestionType() == WebAppConst.WRITING_TYPE)) {
								query.append("limit 3 ");
							} else {
								query.append("limit 2 ");
							}

							SQLQuery queryObj = session.createSQLQuery(query
									.toString());
							queryObj.setParameter("ANSWER_SEQ", answerSeq);
							queryObj.setParameterList("SCORING_STATE",
									latestScoringState);
							return queryObj.list();
						}
					});
			return bitValueList;
		} catch (RuntimeException re) {
			throw re;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TranDescScoreHistory> findHistoryRecord(String scorerId,
			int answerSeq, List<Short> scoringStateList, String connectionString) {
		StringBuilder query = new StringBuilder();

		query.append("FROM TranDescScoreHistory as tranDescScoreHistory ");
		query.append("WHERE tranDescScoreHistory.scorerId = :SCORER_ID ");
		query.append("AND tranDescScoreHistory.tranDescScore.answerSeq = :ANSWER_SEQUENCE ");
		query.append("AND tranDescScoreHistory.scoringState IN :SCORING_STATE_LIST ");
		query.append("AND tranDescScoreHistory.validFlag = :VALID_FLAG ");
		query.append("AND DATE_FORMAT(tranDescScoreHistory.createDate, '%Y-%m-%d') = ");
		query.append("DATE_FORMAT(:CREATE_DATE, '%Y-%m-%d') ");

		String[] paramNames = { "SCORER_ID", "ANSWER_SEQUENCE",
				"SCORING_STATE_LIST", "VALID_FLAG", "CREATE_DATE" };
		Object[] values = { scorerId, answerSeq, scoringStateList,
				WebAppConst.VALID_FLAG, new Date() };

		try {
			HibernateTemplate hibernateTemplate = getHibernateTemplate(connectionString);

			return hibernateTemplate.findByNamedParam(query.toString(),
					paramNames, values);
		} catch (RuntimeException re) {
			throw re;
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List getCommentsByAnswerSequences(List<Integer> answerSeqList,
			QuestionInfo questionInfo) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT tranDescScoreHistory.tranDescScore.answerSeq, CONCAT(tranDescScoreHistory.latestScreenScorerId, ': ', ");
		query.append("tranDescScoreHistory.scorerComment) ");
		query.append("FROM TranDescScoreHistory as tranDescScoreHistory ");
		query.append("WHERE tranDescScoreHistory.tranDescScore.answerSeq IN :ANSWER_SEQUENCES ");
		query.append("AND tranDescScoreHistory.questionSeq = :QUESTION_SEQUENCE ");
		query.append("AND tranDescScoreHistory.eventId != :BATCH_EVENT_ID ");
		query.append("AND tranDescScoreHistory.scorerComment != '' ");
		query.append("AND tranDescScoreHistory.scorerComment is not null ");
		query.append("ORDER BY tranDescScoreHistory.updateDate DESC ");

		String[] paramNames = { "QUESTION_SEQUENCE", "ANSWER_SEQUENCES",
				"BATCH_EVENT_ID" };
		Object[] values = { questionInfo.getQuestionSeq(), answerSeqList,
				WebAppConst.BATCH_EVENT_ID };

		try {
			return getHibernateTemplate(questionInfo.getConnectionString())
					.findByNamedParam(query.toString(), paramNames, values);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List searchAnswerRecords(final QuestionInfo questionInfo,
			final ScoreInputInfo scoreInputInfo,
			final Boolean forceAndStateTransitionFlag,
			final Integer startRecord, final Integer endRecord) {
		
		Map<String, String> configMap = SaitenUtil.getConfigMap();
		final boolean searchByScorerRoleId = Boolean
				.valueOf(configMap.get("search.by.scorer_role_id"));
		
		final ScoreHistoryInfo scoreHistoryInfo = scoreInputInfo
				.getScoreHistoryInfo();

		final String menuId = questionInfo.getMenuId();
		final boolean samplingFlag = scoreInputInfo.isSamplingFlag();
		final String answerFormNum = scoreInputInfo.getAnswerFormNum();

		final List<String> historyScorerIdList = scoreHistoryInfo != null ? scoreHistoryInfo
				.getHistoryScorerIdList() : null;
		final Integer historyCategoryType = scoreHistoryInfo != null ? scoreHistoryInfo
				.getHistoryCategoryType() : null;
		final List historyGradeSeqList = scoreHistoryInfo != null ? scoreHistoryInfo
				.getHistoryGradeSeqList() : null;
		final List historyPendingCategorySeqList = scoreHistoryInfo != null ? scoreHistoryInfo
				.getHistoryPendingCategorySeqList() : null;
		final List historyDenyCategorySeqList = scoreHistoryInfo != null ? scoreHistoryInfo
						.getHistoryDenyCategorySeqList() : null;
		final String historyIncludeCheckPoints = scoreHistoryInfo != null ? scoreHistoryInfo
				.getHistoryIncludeCheckPoints() : null;
		final String historyExcludeCheckPoints = scoreHistoryInfo != null ? scoreHistoryInfo
				.getHistoryExcludeCheckPoints() : null;
		final Short[] historyEventList = scoreHistoryInfo != null ? scoreHistoryInfo
				.getHistoryEventList() : null;
		final Date historyUpdateStartDate = scoreHistoryInfo != null ? scoreHistoryInfo
				.getHistoryUpdateStartDate() : null;
		final Date historyUpdateEndDate = scoreHistoryInfo != null ? scoreHistoryInfo
				.getHistoryUpdateEndDate() : null;
		final Integer[] historyScorerRoles = scoreHistoryInfo != null ? scoreHistoryInfo.getHistoryScorerRole() : null;


		final ScoreCurrentInfo scoreCurrentInfo = scoreInputInfo
				.getScoreCurrentInfo();

		final String punchText = scoreCurrentInfo != null ? scoreCurrentInfo
				.getPunchText() : null;
		final String punchTextCondition = scoreCurrentInfo != null ? scoreCurrentInfo
				.getPunchTextData() : null;
		final List<String> currentScorerIdList = scoreCurrentInfo != null ? scoreCurrentInfo
				.getCurrentScorerIdList() : null;
		final Integer currentCategoryType = scoreCurrentInfo != null ? scoreCurrentInfo
				.getCurrentCategoryType() : null;
		final List currentGradeSeqList = scoreCurrentInfo != null ? scoreCurrentInfo
				.getCurrentGradeSeqList() : null;
		final List currentPendingCategorySeqList = scoreCurrentInfo != null ? scoreCurrentInfo
				.getCurrentPendingCategorySeqList() : null;
		final List currentDenyCategorySeqList = scoreCurrentInfo != null ? scoreCurrentInfo
				.getCurrentDenyCategorySeqList() : null;
		final String currentIncludeCheckPoints = scoreCurrentInfo != null ? scoreCurrentInfo
				.getCurrentIncludeCheckPoints() : null;
		final String currentExcludeCheckPoints = scoreCurrentInfo != null ? scoreCurrentInfo
				.getCurrentExcludeCheckPoints() : null;
		final Short[] currentStateList = scoreCurrentInfo != null ? scoreCurrentInfo
				.getCurrentStateList() : null;
		final Date currentUpdateStartDate = scoreCurrentInfo != null ? scoreCurrentInfo
				.getCurrentUpdateStartDate() : null;
		final Date currentUpdateEndDate = scoreCurrentInfo != null ? scoreCurrentInfo
				.getCurrentUpdateEndDate() : null;
		final Integer[] currentScorerRoles = scoreCurrentInfo != null ? scoreCurrentInfo.getCurrentScorerRole() : null;

		/*
		 * System.out.println(">>>>>>>>>>>> recordCount start: "+ new
		 * Date().getTime()); BigInteger recordCount =
		 * (BigInteger)getCount(questionInfo, scoreInputInfo,
		 * forceAndStateTransitionFlag, startRecord, endRecord);
		 * System.out.println
		 * ("Connection String: "+questionInfo.getConnectionString());
		 * System.out.println(">>>>>>>>> recordCount: "+recordCount); final
		 * double randomNumber =
		 * ((double)scoreInputInfo.getResultCount()/recordCount.intValue())*10;
		 * System.out.println(">>>>>>>>> randomNumber: "+randomNumber);
		 * System.out.println(">>>>>>>>>>>> recordCount end: "+ new
		 * Date().getTime());
		 */

		/*
		 * System.out.println(">>>>>>>>>>>>>> searchAnswerRecords start: " + new
		 * Date().getTime());
		 */
		try {
			List answerRecordsList = getHibernateTemplate(
					questionInfo.getConnectionString()).execute(
					new HibernateCallback<List>() {

						public List doInHibernate(Session session)
								throws HibernateException {
							StringBuilder query = new StringBuilder();
							if ((forceAndStateTransitionFlag == null)
									|| (forceAndStateTransitionFlag == WebAppConst.TRUE)) {
								query.append("SELECT t.answer_seq AS answerSeq, t.answer_form_num AS answerFormNum, ");
								query.append("t.image_file_name AS imageFileName, t.grade_seq AS gradeSeq, ");
								query.append("t.bit_value AS bitValue, t.latest_scorer_id AS latestScorerId, ");

								query.append("t.punch_text AS punchText, ");
								query.append("t.pending_category_seq AS pendingCategorySeq, t.latest_scoring_state, ");
								query.append("t.deny_category_seq AS denyCategorySeq, ");
								if (((forceAndStateTransitionFlag != null) && (forceAndStateTransitionFlag == WebAppConst.TRUE))
										|| (menuId
												.equals(WebAppConst.REFERENCE_SAMP_MENU_ID) && (forceAndStateTransitionFlag == null))) {
									query.append("t.question_seq AS questionSeq, t.latest_screen_scorer_id AS latestScreenScorerId, ");
								}

								// here is count means no of comments.
								if (menuId.equals(WebAppConst.FORCED_MENU_ID)
										&& (forceAndStateTransitionFlag != null && forceAndStateTransitionFlag == WebAppConst.TRUE)) {
									query.append("(SELECT count(*) FROM tran_desc_score_history AS scoreHistory ");
									query.append("WHERE scoreHistory.answer_seq = score.answerSeq ");
									query.append("AND scoreHistory.question_seq = :QUESTION_SEQ ");
									query.append("AND scoreHistory.event_id != :BATCH_EVENT_ID ");
									query.append("AND scoreHistory.scorer_comment != '' AND scoreHistory.scorer_comment is not null ) AS commentCount, ");
								}
								query.append("t.mark_value AS markValue, ");
								query.append("t.update_date AS updateDate, ");
								query.append("t.quality_check_flag AS qualityCheckFlag ");
								if (menuId.equals(WebAppConst.FORCED_MENU_ID)) {
									query.append(", score.lookAftSeq AS lookAftSeq ");
									query.append(", t.latest_screen_scorer_id AS latestScreenScorerId ");
									query.append(", t.second_latest_screen_scorer_id AS secondLatestScreenScorerId ");
								}
							} else {
								// getting count to display on countPage, for
								// FORCED_SCORING and STATE_TRANSITION.
								query.append("SELECT SUM(rowCount) ");
								query.append("FROM (SELECT count(distinct tranDescScore.answer_seq) as rowCount ");
							}
							query.append("FROM tran_desc_score AS t, ");
							query.append("(SELECT tranDescScore.answer_seq AS answerSeq ");
							if (menuId.equals(WebAppConst.FORCED_MENU_ID)) {
								query.append(", tranLookAfterwards.look_aft_seq AS lookAftSeq ");
							}
							query.append("FROM tran_desc_score AS tranDescScore ");
							query.append("INNER JOIN ");
							query.append("tran_desc_score_history AS tranDescScoreHistory ");
							query.append("ON tranDescScore.answer_seq = tranDescScoreHistory.answer_seq ");
							if (menuId.equals(WebAppConst.FORCED_MENU_ID)) {
								if (scoreCurrentInfo != null
										&& scoreCurrentInfo
												.isLookAfterwardsFlag()) {
									query.append("INNER JOIN tran_look_afterwards AS tranLookAfterwards ");
								} else {
									query.append("LEFT JOIN tran_look_afterwards AS tranLookAfterwards ");
								}
								query.append("ON tranDescScore.answer_seq = tranLookAfterwards.answer_seq ");
							}
							query.append("WHERE tranDescScore.question_seq  = :QUESTION_SEQ ");
							query.append("AND tranDescScoreHistory.question_seq  = :QUESTION_SEQ ");
							
							if(searchByScorerRoleId==true){
								if(currentScorerRoles != null){
									query.append("AND tranDescScore.answer_seq = ");
									query.append("( SELECT answer_seq FROM tran_desc_score_history his1 ");
									query.append("WHERE his1.history_seq = ");
									query.append("( SELECT max(history_seq) FROM tran_desc_score_history his2 ");
									query.append("WHERE his2.answer_seq = tranDescScore.answer_seq ");
									query.append("AND his2.scorer_role_id IN ( :CURRENT_SCORER_ROLES ) ");
									query.append(") ");
									query.append(") ");								
								}								
							}
							
							query.append("AND tranDescScore.lock_flag = :UNLOCK ");

							if (menuId.equals(WebAppConst.STATE_TRAN_MENU_ID)) {
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

							if (menuId.equals(WebAppConst.SCORE_SAMP_MENU_ID)) {
								query.append("AND tranDescScore.sampling_flag IN :SAMPLING_FLAG_LIST ");
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

							if (scoreHistoryInfo != null
									&& scoreHistoryInfo.isHistoryBlock()) {

								if (!historyScorerIdList.isEmpty()) {
									query.append("AND tranDescScoreHistory.latest_screen_scorer_id IN :HISTORY_SCORER_ID_LIST ");
								}

								// This is for Quality Check Flag
								/*
								 * if (WebAppConst.SPEAKING_TYPE
								 * .equals(questionInfo.getQuestionType()) ||
								 * WebAppConst.WRITING_TYPE .equals(questionInfo
								 * .getQuestionType())) {
								 */
								if (scoreHistoryInfo
										.isHistoryQualityCheckFlag()) {
									query.append("AND tranDescScoreHistory.quality_check_flag IN :QUALITY_CHECK_FLAG ");
								}
								/* } */
								if (historyCategoryType != null
										&& historyCategoryType == 2) {
									query.append("AND tranDescScoreHistory.grade_seq IS null AND tranDescScoreHistory.pending_category_seq IS null ");
								} else if (historyCategoryType != null
										&& historyCategoryType == 3) {

									if (!historyGradeSeqList.isEmpty()
											&& ArrayUtils.contains(
													scoreHistoryInfo
															.getHistoryGradeNum(),
													-1)) {
										query.append("AND (tranDescScoreHistory.grade_seq IN :HISTORY_GRADE_SEQ_LIST ");
										query.append("OR tranDescScoreHistory.scoring_state = :NO_GRADE_SCORING_STATE) ");
									} else {
										if (!historyGradeSeqList.isEmpty()) {
											query.append("AND tranDescScoreHistory.grade_seq IN :HISTORY_GRADE_SEQ_LIST ");
										}

										if (ArrayUtils.contains(
												scoreHistoryInfo
														.getHistoryGradeNum(),
												-1)) {
											query.append("AND tranDescScoreHistory.scoring_state = :NO_GRADE_SCORING_STATE ");
										}
									}
								} else if (historyCategoryType != null
										&& historyCategoryType == 4
										&& !historyPendingCategorySeqList
												.isEmpty()) {
									query.append("AND tranDescScoreHistory.pending_category_seq IN :HISTORY_PENDING_CATEGORY_SEQ_LIST ");
								}else if (historyCategoryType != null
										&& historyCategoryType == 5
										&& !historyDenyCategorySeqList
												.isEmpty()) {
									query.append("AND tranDescScoreHistory.deny_category_seq IN :HISTORY_DENY_CATEGORY_SEQ_LIST ");
								}

								if (!StringUtils
										.isBlank(historyIncludeCheckPoints)
										&& !StringUtils
												.isBlank(historyExcludeCheckPoints)) {
									query.append("AND (tranDescScoreHistory.bit_value & :HISTORY_INCLUDE_BIT_VALUE) > 0 ");
									query.append("AND (tranDescScoreHistory.bit_value & :HISTORY_EXCLUDE_BIT_VALUE) = 0 ");
								} else {
									if (!StringUtils
											.isBlank(historyIncludeCheckPoints)) {
										query.append("AND (tranDescScoreHistory.bit_value & :HISTORY_INCLUDE_BIT_VALUE) > 0 ");
									} else if (!StringUtils
											.isBlank(historyExcludeCheckPoints)) {
										query.append("AND (tranDescScoreHistory.bit_value & :HISTORY_EXCLUDE_BIT_VALUE) = 0 ");
									}
								}

								if (historyEventList != null
										&& historyEventList.length > 0) {
									query.append("AND tranDescScoreHistory.event_id IN :HISTORY_EVENT_LIST ");
								}

								if (historyUpdateStartDate != null) {
									query.append("AND tranDescScoreHistory.update_date >= :HISTORY_UPDATE_START_DATE ");
								}

								if (historyUpdateEndDate != null) {
									query.append("AND tranDescScoreHistory.update_date <= :HISTORY_UPDATE_END_DATE ");
								}
							}

							if (scoreCurrentInfo != null
									&& scoreCurrentInfo.isCurrentBlock()) {

								if (!StringUtils.isBlank(punchText)) {
									query.append("AND tranDescScore.punch_text like :PUNCH_TEXT escape '" +WebAppConst.ESCAPE_CHARACTER + "'");
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
								if (scoreCurrentInfo
										.isCurrentQualityCheckFlag()) {
									query.append("AND tranDescScore.quality_check_flag IN :QUALITY_CHECK_FLAG ");
								}
								/* } */
								if (currentCategoryType != null
										&& currentCategoryType == 2) {
									query.append("AND tranDescScore.grade_seq IS null AND tranDescScore.pending_category_seq IS null ");
								} else if (currentCategoryType != null
										&& currentCategoryType == 3) {

									if (!currentGradeSeqList.isEmpty()
											&& ArrayUtils.contains(
													scoreCurrentInfo
															.getCurrentGradeNum(),
													-1)) {
										query.append("AND (tranDescScore.grade_seq IN :CURRENT_GRADE_SEQ_LIST ");
										query.append("OR tranDescScore.latest_scoring_state = :NO_GRADE_SCORING_STATE) ");
									} else {
										if (!currentGradeSeqList.isEmpty()) {
											query.append("AND tranDescScore.grade_seq IN :CURRENT_GRADE_SEQ_LIST ");
										}

										if (ArrayUtils.contains(
												scoreCurrentInfo
														.getCurrentGradeNum(),
												-1)) {
											query.append("AND tranDescScore.latest_scoring_state = :NO_GRADE_SCORING_STATE ");
										}
									}
								} else if (currentCategoryType != null
										&& currentCategoryType == 4
										&& !currentPendingCategorySeqList
												.isEmpty()) {
									query.append("AND tranDescScore.pending_category_seq IN :CURRENT_PENDING_CATEGORY_SEQ_LIST ");
								} else if (currentCategoryType != null
										&& currentCategoryType == 5
										&& !currentDenyCategorySeqList
												.isEmpty()) {
									query.append("AND tranDescScore.deny_category_seq IN :CURRENT_DENY_CATEGORY_SEQ_LIST ");
								}

								if (!StringUtils
										.isBlank(currentIncludeCheckPoints)
										&& !StringUtils
												.isBlank(currentExcludeCheckPoints)) {
									query.append("AND (tranDescScore.bit_value & :CURRENT_INCLUDE_BIT_VALUE) > 0 ");
									query.append("AND (tranDescScore.bit_value & :CURRENT_EXCLUDE_BIT_VALUE) = 0 ");
								} else {
									if (!StringUtils
											.isBlank(currentIncludeCheckPoints)) {
										query.append("AND (tranDescScore.bit_value & :CURRENT_INCLUDE_BIT_VALUE) > 0 ");
									} else if (!StringUtils
											.isBlank(currentExcludeCheckPoints)) {
										query.append("AND (tranDescScore.bit_value & :CURRENT_EXCLUDE_BIT_VALUE) = 0 ");
									}
								}

								if (currentStateList != null
										&& currentStateList.length > 0) {
									query.append("AND tranDescScore.latest_scoring_state IN :CURRENT_STATE_LIST ");
								}

								if (currentUpdateStartDate != null) {
									query.append("AND tranDescScore.update_date >= :CURRENT_UPDATE_START_DATE ");
								}

								if (currentUpdateEndDate != null) {
									query.append("AND tranDescScore.update_date <= :CURRENT_UPDATE_END_DATE ");
								}
							} else if (menuId
									.equals(WebAppConst.SCORE_SAMP_MENU_ID)
									|| menuId
											.equals(WebAppConst.STATE_TRAN_MENU_ID)
									|| menuId
											.equals(WebAppConst.FORCED_MENU_ID)) {
								query.append("AND tranDescScore.latest_scoring_state IN :CURRENT_STATE_LIST ");
							}

							if ((scoreHistoryInfo == null || !scoreHistoryInfo
									.isHistoryBlock())
									&& (scoreCurrentInfo == null || !scoreCurrentInfo
											.isCurrentBlock())) {
								if (menuId
										.equals(WebAppConst.REFERENCE_SAMP_MENU_ID)) {
									query.append("AND tranDescScore.latest_scoring_state NOT IN :SPECIAL_SCORING_AND_BATCH_STATES ");
								} else if (menuId
										.equals(WebAppConst.SCORE_SAMP_MENU_ID)) {
									query.append("AND tranDescScore.latest_scoring_state NOT IN :SPECIAL_SCORING_STATES ");
								}

							}
							if (!(menuId.equals(WebAppConst.STATE_TRAN_MENU_ID) || menuId
									.equals(WebAppConst.FORCED_MENU_ID))) {
								query.append("AND tranDescScoreHistory.scoring_state NOT IN :DUMMY_SCORING_STATES ");
								query.append("AND tranDescScore.latest_scoring_state NOT IN :DUMMY_SCORING_STATES ");
							}

							if (menuId
									.equals(WebAppConst.REFERENCE_SAMP_MENU_ID)
									|| menuId
											.equals(WebAppConst.SCORE_SAMP_MENU_ID)
									|| menuId
											.equals(WebAppConst.STATE_TRAN_MENU_ID)) {
								query.append("AND tranDescScore.answer_paper_type NOT IN ( :ANSWER_PAPER_TYPES ) ");
								query.append("AND ( SELECT count(*) from tran_desc_score_history th where pending_category in ( :PENDING_CATEGORIES ) AND tranDescScore.answer_seq = th.answer_seq )<=0 ");
								query.append("AND ( SELECT count(*) from tran_desc_score_history th where deny_category in ( :DENY_CATEGORIES ) AND tranDescScore.answer_seq = th.answer_seq )<=0 ");
							}
							
							if(searchByScorerRoleId==true){
								if(historyScorerRoles != null){
									query.append("AND tranDescScoreHistory.scorer_role_id IN :HISTORY_SCORER_ROLES ");
								}								
							}
							
							if (menuId.equals(WebAppConst.FORCED_MENU_ID)) {
								if (scoreCurrentInfo != null
										&& scoreCurrentInfo
												.isLookAfterwardsFlag()) {
									query.append("AND tranLookAfterwards.look_aft_flag = :VALID_FLAG ");
								}
							}

							query.append("AND tranDescScore.valid_flag = :VALID_FLAG ");
							query.append("AND tranDescScoreHistory.valid_flag = :VALID_FLAG ");
							query.append("GROUP BY tranDescScore.answer_seq ");
							if ((forceAndStateTransitionFlag != null)
									&& (forceAndStateTransitionFlag == WebAppConst.TRUE)) {
								query.append("ORDER BY tranDescScore.update_date DESC ");
								query.append("LIMIT ");
								if (endRecord != null) {
									if (startRecord > 0) {
										query.append(startRecord + " , "
												+ endRecord);
									} else {
										query.append(endRecord + " ");
									}
								}
							}
							if (!(menuId.equals(WebAppConst.STATE_TRAN_MENU_ID) || menuId
									.equals(WebAppConst.FORCED_MENU_ID))) {
								query.append("LIMIT :RECORD_COUNT_LIMIT ");
								query.append(") score ");
								query.append("ORDER BY updateDate DESC ");
							} else if ((forceAndStateTransitionFlag != null)
									&& (forceAndStateTransitionFlag == WebAppConst.TRUE)) {
								query.append(") score ");
							} else if ((forceAndStateTransitionFlag != null)
									&& (forceAndStateTransitionFlag == WebAppConst.FALSE)) {
								query.append(") score ");
							}
							query.append("WHERE t.answer_seq = score.answerSeq ");

							SQLQuery queryObj = session.createSQLQuery(query
									.toString());

							if (menuId
									.equals(WebAppConst.REFERENCE_SAMP_MENU_ID)
									|| menuId
											.equals(WebAppConst.SCORE_SAMP_MENU_ID)
									|| menuId
											.equals(WebAppConst.STATE_TRAN_MENU_ID)) {
								queryObj.setParameterList("ANSWER_PAPER_TYPES",
										WebAppConst.ANSWER_PAPER_TYPES);
								queryObj.setParameterList("PENDING_CATEGORIES",
										WebAppConst.PENDING_CATEGORIES);
								queryObj.setParameterList("DENY_CATEGORIES",
										WebAppConst.PENDING_CATEGORIES);
							}

							if (menuId.equals(WebAppConst.STATE_TRAN_MENU_ID)) {
								queryObj.setParameter("INSPECT_FLAG",
										WebAppConst.F);
								/*
								 * if (WebAppConst.SPEAKING_TYPE
								 * .equals(questionInfo.getQuestionType()) ||
								 * WebAppConst.WRITING_TYPE .equals(questionInfo
								 * .getQuestionType())) {
								 */
								queryObj.setParameter("QUALITY_FLAG",
										WebAppConst.QUALITY_MARK_FLAG_FALSE);
								/* } */

							}

							if (menuId.equals(WebAppConst.SCORE_SAMP_MENU_ID)) {
								if (samplingFlag == WebAppConst.TRUE) {
									queryObj.setParameterList(
											"SAMPLING_FLAG_LIST",
											WebAppConst.SCORE_SAMPLING_FLAG_LIST);
								} else {
									queryObj.setParameterList(
											"SAMPLING_FLAG_LIST",
											ArrayUtils
													.subarray(
															WebAppConst.SCORE_SAMPLING_FLAG_LIST,
															0, 1));
								}
								/*
								 * if (WebAppConst.SPEAKING_TYPE
								 * .equals(questionInfo.getQuestionType()) ||
								 * WebAppConst.WRITING_TYPE .equals(questionInfo
								 * .getQuestionType())) {
								 */
								queryObj.setParameter("QUALITY_FLAG",
										WebAppConst.QUALITY_MARK_FLAG_FALSE);
								/* } */
							}

							if (!StringUtils.isBlank(answerFormNum)) {
								queryObj.setParameter("ANSWER_FORM_NUM",
										answerFormNum);
							}

							if (menuId.equals(WebAppConst.FORCED_MENU_ID)
									&& (forceAndStateTransitionFlag != null && forceAndStateTransitionFlag == WebAppConst.TRUE)) {
								queryObj.setParameter("BATCH_EVENT_ID",
										WebAppConst.BATCH_EVENT_ID);
							}

							setHistoryParameters(queryObj);

							setCurrentParameters(queryObj);

							queryObj.setInteger("QUESTION_SEQ",
									questionInfo.getQuestionSeq());
							queryObj.setCharacter("UNLOCK", WebAppConst.UNLOCK);
							queryObj.setCharacter("VALID_FLAG",
									WebAppConst.VALID_FLAG);
							if (!(menuId.equals(WebAppConst.STATE_TRAN_MENU_ID) || menuId
									.equals(WebAppConst.FORCED_MENU_ID))) {
								queryObj.setInteger("RECORD_COUNT_LIMIT",
										scoreInputInfo.getResultCount());
							}

							if ((scoreHistoryInfo == null || !scoreHistoryInfo
									.isHistoryBlock())
									&& (scoreCurrentInfo == null || !scoreCurrentInfo
											.isCurrentBlock())) {
								if (menuId
										.equals(WebAppConst.REFERENCE_SAMP_MENU_ID)) {
									queryObj.setParameterList(
											"SPECIAL_SCORING_AND_BATCH_STATES",
											WebAppConst.SPECIAL_SCORING_AND_BATCH_STATES);
								} else if (menuId
										.equals(WebAppConst.SCORE_SAMP_MENU_ID)) {
									queryObj.setParameterList(
											"SPECIAL_SCORING_STATES",
											WebAppConst.SPECIAL_SCORING_STATES);
								}

							}
							if (!(menuId.equals(WebAppConst.STATE_TRAN_MENU_ID) || menuId
									.equals(WebAppConst.FORCED_MENU_ID))) {
								queryObj.setParameterList(
										"DUMMY_SCORING_STATES",
										WebAppConst.DUMMY_SCORING_STATES);
							}

							// This code is replace with sql query: limit
							// offset, count.
							// Refer code after 'GROUP BY
							// tranDescScore.answer_seq' statement.
							/*
							 * if ((forceAndStateTransitionFlag != null) &&
							 * (forceAndStateTransitionFlag == WebAppConst.TRUE)
							 * && (menuId .equals(WebAppConst.FORCED_MENU_ID)))
							 * { if (endRecord != null) {
							 * queryObj.setFirstResult(startRecord);
							 * queryObj.setMaxResults(endRecord); } }
							 */

							return queryObj.list();

						}

						public void setCurrentParameters(SQLQuery queryObj) {

							if (scoreCurrentInfo != null
									&& scoreCurrentInfo.isCurrentBlock()) {

								if (!StringUtils.isBlank(punchText)) {
									if(punchTextCondition == null || punchTextCondition.equals("0")) {
										queryObj.setParameter("PUNCH_TEXT",
												punchText);
									} else if(punchTextCondition.equals("1")) {
										queryObj.setParameter("PUNCH_TEXT",
												punchText+"%");
									} else if(punchTextCondition.equals("2")) {
										queryObj.setParameter("PUNCH_TEXT",
												"%"+punchText);
									} else if(punchTextCondition.equals("3")) {
										queryObj.setParameter("PUNCH_TEXT",
												"%"+punchText+"%");
									}
								}

								if (!currentScorerIdList.isEmpty()) {
									queryObj.setParameterList(
											"CURRENT_SCORER_ID_LIST",
											currentScorerIdList);
								}

								if (scoreCurrentInfo
										.isCurrentQualityCheckFlag()) {
									/*
									 * if (WebAppConst.SPEAKING_TYPE
									 * .equals(questionInfo .getQuestionType())
									 * || WebAppConst.WRITING_TYPE
									 * .equals(questionInfo .getQuestionType()))
									 * {
									 */
									queryObj.setParameterList(
											"QUALITY_CHECK_FLAG",
											ArrayUtils
													.subarray(
															WebAppConst.QUALITY_MARK_FLAG_LIST,
															0, 1));
									/* } */
								} /*
								 * else { if (WebAppConst.SPEAKING_TYPE
								 * .equals(questionInfo .getQuestionType()) ||
								 * WebAppConst.WRITING_TYPE .equals(questionInfo
								 * .getQuestionType())) {
								 * queryObj.setParameterList(
								 * "QUALITY_CHECK_FLAG",
								 * WebAppConst.QUALITY_MARK_FLAG_LIST); }
								 * 
								 * }
								 */

								if (currentCategoryType != null
										&& currentCategoryType == 3) {

									if (!currentGradeSeqList.isEmpty()) {
										queryObj.setParameterList(
												"CURRENT_GRADE_SEQ_LIST",
												currentGradeSeqList);
									}

									if (ArrayUtils.contains(scoreCurrentInfo
											.getCurrentGradeNum(), -1)) {
										queryObj.setShort(
												"NO_GRADE_SCORING_STATE",
												WebAppConst.NO_GRADE_SCORING_STATE);
									}
								} else if (currentCategoryType != null
										&& currentCategoryType == 4
										&& !currentPendingCategorySeqList
												.isEmpty()) {

									queryObj.setParameterList(
											"CURRENT_PENDING_CATEGORY_SEQ_LIST",
											scoreCurrentInfo
													.getCurrentPendingCategorySeqList());
								} else if (currentCategoryType != null
										&& currentCategoryType == 5
										&& !currentDenyCategorySeqList
												.isEmpty()) {

									queryObj.setParameterList(
											"CURRENT_DENY_CATEGORY_SEQ_LIST",
											scoreCurrentInfo
													.getCurrentDenyCategorySeqList());
								}

								if (!StringUtils
										.isBlank(currentIncludeCheckPoints)
										&& !StringUtils
												.isBlank(currentExcludeCheckPoints)) {
									queryObj.setInteger(
											"CURRENT_INCLUDE_BIT_VALUE",
											scoreCurrentInfo
													.getCurrentIncludeBitValue());
									queryObj.setInteger(
											"CURRENT_EXCLUDE_BIT_VALUE",
											scoreCurrentInfo
													.getCurrentExcludeBitValue());
								} else {
									if (!StringUtils
											.isBlank(currentIncludeCheckPoints)) {
										queryObj.setInteger(
												"CURRENT_INCLUDE_BIT_VALUE",
												scoreCurrentInfo
														.getCurrentIncludeBitValue());
									} else if (!StringUtils
											.isBlank(currentExcludeCheckPoints)) {
										queryObj.setInteger(
												"CURRENT_EXCLUDE_BIT_VALUE",
												scoreCurrentInfo
														.getCurrentExcludeBitValue());
									}
								}

								if (currentStateList != null
										&& currentStateList.length > 0) {
									queryObj.setParameterList(
											"CURRENT_STATE_LIST",
											scoreCurrentInfo
													.getCurrentStateList());
								}

								if (currentUpdateStartDate != null) {
									queryObj.setParameter(
											"CURRENT_UPDATE_START_DATE",
											currentUpdateStartDate);
								}

								if (currentUpdateEndDate != null) {
									queryObj.setParameter(
											"CURRENT_UPDATE_END_DATE",
											currentUpdateEndDate);
								}
							} else if (menuId
									.equals(WebAppConst.SCORE_SAMP_MENU_ID)) {
								queryObj.setParameterList(
										"CURRENT_STATE_LIST",
										WebAppConst.SCORE_SAMPLING_CURRENT_STATES);
							} else if (menuId
									.equals(WebAppConst.STATE_TRAN_MENU_ID)) {
								queryObj.setParameterList(
										"CURRENT_STATE_LIST",
										WebAppConst.STATE_TRANSITION_CURRENT_STATES);
							} else if (menuId
									.equals(WebAppConst.FORCED_MENU_ID)) {
								queryObj.setParameterList(
										"CURRENT_STATE_LIST",
										WebAppConst.FORCED_SCORING_CURRENT_STATES);
							}
							
							if(searchByScorerRoleId==true){
								if(currentScorerRoles != null){
									queryObj.setParameterList("CURRENT_SCORER_ROLES", Arrays.asList(currentScorerRoles));
								}								
							}

						}

						public void setHistoryParameters(SQLQuery queryObj) {

							if (scoreHistoryInfo != null
									&& scoreHistoryInfo.isHistoryBlock()) {

								if (!historyScorerIdList.isEmpty()) {
									queryObj.setParameterList(
											"HISTORY_SCORER_ID_LIST",
											historyScorerIdList);
								}

								if (scoreHistoryInfo
										.isHistoryQualityCheckFlag()) {
									/*
									 * if (WebAppConst.SPEAKING_TYPE
									 * .equals(questionInfo .getQuestionType())
									 * || WebAppConst.WRITING_TYPE
									 * .equals(questionInfo .getQuestionType()))
									 * {
									 */
									queryObj.setParameterList(
											"QUALITY_CHECK_FLAG",
											ArrayUtils
													.subarray(
															WebAppConst.QUALITY_MARK_FLAG_LIST,
															0, 1));
									/* } */
								} /*
								 * else { if (WebAppConst.SPEAKING_TYPE
								 * .equals(questionInfo .getQuestionType()) ||
								 * WebAppConst.WRITING_TYPE .equals(questionInfo
								 * .getQuestionType())) {
								 * queryObj.setParameterList(
								 * "QUALITY_CHECK_FLAG",
								 * WebAppConst.QUALITY_MARK_FLAG_LIST); }
								 * 
								 * }
								 */

								if (historyCategoryType != null
										&& historyCategoryType == 3) {

									if (!historyGradeSeqList.isEmpty()) {
										queryObj.setParameterList(
												"HISTORY_GRADE_SEQ_LIST",
												historyGradeSeqList);
									}

									if (ArrayUtils.contains(scoreHistoryInfo
											.getHistoryGradeNum(), -1)) {
										queryObj.setShort(
												"NO_GRADE_SCORING_STATE",
												WebAppConst.NO_GRADE_SCORING_STATE);
									}
								} else if (historyCategoryType != null
										&& historyCategoryType == 4
										&& !historyPendingCategorySeqList
												.isEmpty()) {

									queryObj.setParameterList(
											"HISTORY_PENDING_CATEGORY_SEQ_LIST",
											historyPendingCategorySeqList);
								} else if (historyCategoryType != null
										&& historyCategoryType == 5
										&& !historyDenyCategorySeqList
												.isEmpty()) {

									queryObj.setParameterList(
											"HISTORY_DENY_CATEGORY_SEQ_LIST",
											historyDenyCategorySeqList);
								}

								if (!StringUtils
										.isBlank(historyIncludeCheckPoints)
										&& !StringUtils
												.isBlank(historyExcludeCheckPoints)) {
									queryObj.setInteger(
											"HISTORY_INCLUDE_BIT_VALUE",
											scoreHistoryInfo
													.getHistoryIncludeBitValue());
									queryObj.setInteger(
											"HISTORY_EXCLUDE_BIT_VALUE",
											scoreHistoryInfo
													.getHistoryExcludeBitValue());
								} else {
									if (!StringUtils
											.isBlank(historyIncludeCheckPoints)) {
										queryObj.setInteger(
												"HISTORY_INCLUDE_BIT_VALUE",
												scoreHistoryInfo
														.getHistoryIncludeBitValue());
									} else if (!StringUtils
											.isBlank(historyExcludeCheckPoints)) {
										queryObj.setInteger(
												"HISTORY_EXCLUDE_BIT_VALUE",
												scoreHistoryInfo
														.getHistoryExcludeBitValue());
									}
								}

								if (historyEventList != null
										&& historyEventList.length > 0) {
									queryObj.setParameterList(
											"HISTORY_EVENT_LIST",
											historyEventList);
								}

								if (historyUpdateStartDate != null) {
									queryObj.setParameter(
											"HISTORY_UPDATE_START_DATE",
											historyUpdateStartDate);
								}

								if (historyUpdateEndDate != null) {
									queryObj.setParameter(
											"HISTORY_UPDATE_END_DATE",
											historyUpdateEndDate);
								}
								
								if(searchByScorerRoleId==true){
									if(historyScorerRoles != null){
										queryObj.setParameterList("HISTORY_SCORER_ROLES", Arrays.asList(historyScorerRoles));
									}									
								}

							}

						}
					});
			/*
			 * System.out.println(">>>>>>>>>>>>>> searchAnswerRecords end: " +
			 * new Date().getTime());
			 */
			return answerRecordsList;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List findProcessDetails(Integer answerSequence,
			String connectionString) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT tranDescScoreHistory.scoringState, tranDescScoreHistory.latestScreenScorerId, tranDescScoreHistory.gradeNum, ");
		query.append("tranDescScoreHistory.pendingCategory, tranDescScoreHistory.updateDate, tranDescScoreHistory.questionSeq, tranDescScoreHistory.gradeSeq ");
		query.append("FROM TranDescScoreHistory as tranDescScoreHistory ");
		query.append("WHERE tranDescScoreHistory.tranDescScore.answerSeq = :ANSWER_SEQUENCE ");
		query.append("AND tranDescScoreHistory.validFlag = :VALID_FLAG ");

		String[] paramNames = { "ANSWER_SEQUENCE", "VALID_FLAG" };
		Object[] values = { answerSequence, WebAppConst.VALID_FLAG };

		try {
			return getHibernateTemplate(connectionString).findByNamedParam(
					query.toString(), paramNames, values);
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getDailyDiscrepancyAnalysisData(
			String connectionString, final String fromDate, final String toDate) {

		final StringBuilder query = new StringBuilder();
		query.append("SELECT ");
		query.append("result.scorer_id,	");
		query.append("result.question_seq, ");
		query.append("count(*) as score_count, ");
		query.append("sum(case when result.stateList like '%171' then 1 else 0 end) as discrepancy_count, ");
		query.append("Round(sum(case when result.stateList like '%171' then 1 else 0 end)*100/count(*)) as percentage ");
		query.append("FROM ");
		query.append("(SELECT ");
		query.append("scorer_id, ");
		query.append("question_seq, ");
		query.append("(select GROUP_CONCAT(scoring_state) from tran_desc_score_history tdsh1 ");
		query.append("where tdsh1.answer_seq = tdsh2.answer_seq ");
		query.append("and tdsh1.update_date between :FROM_DATE AND :TO_DATE	");
		query.append("and tdsh1.valid_flag = :VALID_FLAG) as stateList ");
		query.append("FROM tran_desc_score_history tdsh2 ");
		query.append("WHERE update_date between :FROM_DATE AND :TO_DATE	");
		query.append("AND scorer_id not like :BATCH_SCORER_ID ");
		query.append("AND valid_flag = :VALID_FLAG ");
		query.append("group by scorer_id,answer_seq) as result ");
		query.append("GROUP BY result.scorer_id,result.question_seq Having discrepancy_count > 0 order by question_seq,scorer_id ");

		try {
			return getHibernateTemplate(connectionString).execute(
					new HibernateCallback<List<Object[]>>() {
						public List<Object[]> doInHibernate(Session session)
								throws HibernateException {
							SQLQuery queryObj = session.createSQLQuery(query
									.toString());
							queryObj.setParameter("FROM_DATE", fromDate);
							queryObj.setParameter("TO_DATE", toDate);
							queryObj.setParameter("VALID_FLAG",
									WebAppConst.VALID_FLAG);
							queryObj.setParameter("BATCH_SCORER_ID",
									WebAppConst.BATCH_SCORER_ID);
							return queryObj.list();
						}
					});
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<RatingInfo> getSummaryDiscrepancyAnalysisData(
			String connectionString, final String fromDate, final String toDate) {

		final StringBuilder query = new StringBuilder();
		query.append("SELECT ");
		query.append("result.scorer_id as scorerId, ");
		query.append("result.question_type as questionType, ");
		query.append("(select count(*) from tran_desc_score_history tdh, tran_desc_score tds ");
		query.append("where tdh.answer_seq = tds.answer_seq ");
		query.append("and tdh.scorer_id = result.scorer_id ");
		query.append("and tdh.update_date between :FROM_DATE AND :TO_DATE ");
		query.append("and tdh.valid_flag = :VALID_FLAG ");
		query.append("and tds.valid_flag = :VALID_FLAG ");
		query.append("and tds.image_file_name like ");
		query.append("case when result.question_type = :QUES_TYPE_SPEAKING then :MP3_FORMAT else :TXT_FORMAT end) as totalRating, ");
		query.append("count(*) as totalDiscrepancyCount, ");
		query.append("sum(result.matchCount) as matchCount, ");
		query.append("(count(*)-sum(result.matchCount)) as discrepancyCount ");
		query.append("FROM ");
		query.append("(select ");
		query.append("tdh1.answer_seq, tdh1.question_seq, ");
		query.append("(case when ");
		query.append("(select image_file_name from tran_desc_score as tds where tds.answer_seq=tdh1.answer_seq) like :MP3_FORMAT ");
		query.append("then :QUES_TYPE_SPEAKING else :QUES_TYPE_WRITING end) as question_type, ");
		query.append("tdh1.scorer_id, ");
		query.append("tdh1.scoring_state,tdh2.stateList,tdh1.bit_value,tdh2.latest500BitValue, ");
		query.append("(case when tdh1.bit_value = tdh2.latest500BitValue then 1 else 0 end) as matchCount ");
		query.append("from ");
		query.append("tran_desc_score_history tdh1, ");
		query.append("(select answer_seq, ");
		query.append("GROUP_CONCAT(scoring_state) as stateList, ");
		query.append("SUBSTRING_INDEX(GROUP_CONCAT(bit_value), ',', -1) as latest500BitValue ");
		query.append("FROM tran_desc_score_history ");
		query.append("where valid_flag = :VALID_FLAG ");
		query.append("GROUP BY answer_seq HAVING stateList like '%171%500') tdh2 ");
		query.append("where tdh1.answer_seq = tdh2.answer_seq ");
		query.append("and tdh1.scorer_id not like :BATCH_SCORER_ID ");
		query.append("and tdh1.update_date between :FROM_DATE AND :TO_DATE ");
		query.append("and tdh1.scoring_state in :STATE_LIST ");
		query.append("and tdh1.valid_flag = :VALID_FLAG ");
		query.append("group by tdh1.answer_seq,tdh1.scorer_id) as result ");
		query.append("group by result.question_type,result.scorer_id");

		try {
			return getHibernateTemplate(connectionString).execute(
					new HibernateCallback<List<RatingInfo>>() {
						public List<RatingInfo> doInHibernate(Session session)
								throws HibernateException {
							SQLQuery queryObj = session.createSQLQuery(query
									.toString());
							queryObj.setParameter("FROM_DATE", fromDate);
							queryObj.setParameter("TO_DATE", toDate);
							queryObj.setParameter("VALID_FLAG",
									WebAppConst.VALID_FLAG);
							queryObj.setParameter("QUES_TYPE_SPEAKING",
									WebAppConst.QUES_TYPE_SPEAKING);
							queryObj.setParameter("QUES_TYPE_WRITING",
									WebAppConst.QUES_TYPE_WRITING);
							queryObj.setParameter("MP3_FORMAT",
									WebAppConst.MP3_FORMAT);
							queryObj.setParameter("TXT_FORMAT",
									WebAppConst.TXT_FORMAT);
							queryObj.setParameter("BATCH_SCORER_ID",
									WebAppConst.BATCH_SCORER_ID);
							queryObj.setParameterList("STATE_LIST",
									WebAppConst.SUMMARY_DISCR_REPORT_STATES);

							queryObj.addScalar("scorerId");
							queryObj.addScalar("questionType");
							queryObj.addScalar("totalRating",
									IntegerType.INSTANCE);
							queryObj.addScalar("totalDiscrepancyCount",
									IntegerType.INSTANCE);
							queryObj.addScalar("matchCount",
									IntegerType.INSTANCE);
							queryObj.addScalar("discrepancyCount",
									IntegerType.INSTANCE);
							queryObj.setResultTransformer(Transformers
									.aliasToBean(RatingInfo.class));
							return queryObj.list();
						}
					});
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public DailyScoreInfo getDailyScoreInfo(String connectionString,
			final String fromDate, final String toDate) {

		final StringBuilder query = new StringBuilder();
		query.append("SELECT ");
		query.append("sum(case when result.stateList like :FIRST_RTG_SECOND_WAIT and result.question_type=:QUES_TYPE_SPEAKING then 1 else 0 end) firstRtgSecondWaitSpkCnt, ");
		query.append("sum(case when result.stateList like :FIRST_RTG_SECOND_WAIT and result.question_type=:QUES_TYPE_WRITING then 1 else 0 end) firstRtgSecondWaitWrtCnt, ");
		query.append("sum(case when result.stateList like :FIRST_RTG_PENDING and result.question_type=:QUES_TYPE_SPEAKING then 1 else 0 end) firstRtgPendingSpkCnt, ");
		query.append("sum(case when result.stateList like :FIRST_RTG_PENDING and result.question_type=:QUES_TYPE_WRITING then 1 else 0 end) firstRtgPendingWrtCnt, ");
		query.append("sum(case when result.stateList like :SECOND_RTG_COMPLETE and result.question_type=:QUES_TYPE_SPEAKING then 1 else 0 end) secondRtgCompleteSpkCnt, ");
		query.append("sum(case when result.stateList like :SECOND_RTG_COMPLETE and result.question_type=:QUES_TYPE_WRITING then 1 else 0 end) secondRtgCompleteWrtCnt, ");
		query.append("sum(case when result.stateList like :SECOND_RTG_PENDING and result.question_type=:QUES_TYPE_SPEAKING then 1 else 0 end) secondRtgPendingSpkCnt, ");
		query.append("sum(case when result.stateList like :SECOND_RTG_PENDING and result.question_type=:QUES_TYPE_WRITING then 1 else 0 end) secondRtgPendingWrtCnt, ");
		query.append("sum(case when result.stateList like :SECOND_RTG_MISMATCH and result.question_type=:QUES_TYPE_SPEAKING then 1 else 0 end) secondRtgMismatchSpkCnt, ");
		query.append("sum(case when result.stateList like :SECOND_RTG_MISMATCH and result.question_type=:QUES_TYPE_WRITING then 1 else 0 end) secondRtgMismatchWrtCnt, ");
		query.append("sum(case when result.stateList like :MISMATCH_RTG_PENDING and result.question_type=:QUES_TYPE_SPEAKING then 1 else 0 end) mismatchRtgPendingSpkCnt, ");
		query.append("sum(case when result.stateList like :MISMATCH_RTG_PENDING and result.question_type=:QUES_TYPE_WRITING then 1 else 0 end) mismatchRtgPendingWrtCnt, ");
		query.append("sum(case when result.stateList like :MISMATCH_RTG_COMPLETE and result.question_type=:QUES_TYPE_SPEAKING then 1 else 0 end) mismatchRtgCompleteSpkCnt, ");
		query.append("sum(case when result.stateList like :MISMATCH_RTG_COMPLETE and result.question_type=:QUES_TYPE_WRITING then 1 else 0 end) mismatchRtgCompleteWrtCnt, ");
		query.append("sum(case when result.stateList like :PENDING_RTG_SECOND_WAIT and (select GROUP_CONCAT(pending_category) as pendingCategoryList from tran_desc_score_history where answer_seq=result.answer_seq) like :PENDING_01 and result.question_type=:QUES_TYPE_SPEAKING then 1 else 0 end) pending01SecondWaitSpkCnt, ");
		query.append("sum(case when result.stateList like :PENDING_RTG_SECOND_WAIT and (select GROUP_CONCAT(pending_category) as pendingCategoryList from tran_desc_score_history where answer_seq=result.answer_seq) like :PENDING_01 and result.question_type=:QUES_TYPE_WRITING then 1 else 0 end) pending01SecondWaitWrtCnt, ");
		query.append("sum(case when result.stateList like :PENDING_RTG_PENDING and (select GROUP_CONCAT(pending_category) as pendingCategoryList from tran_desc_score_history where answer_seq=result.answer_seq) like :PENDING_01 and result.question_type=:QUES_TYPE_SPEAKING then 1 else 0 end) pending01PendingSpkCnt, ");
		query.append("sum(case when result.stateList like :PENDING_RTG_PENDING and (select GROUP_CONCAT(pending_category) as pendingCategoryList from tran_desc_score_history where answer_seq=result.answer_seq) like :PENDING_01 and result.question_type=:QUES_TYPE_WRITING then 1 else 0 end) pending01PendingWrtCnt, ");
		query.append("sum(case when result.stateList like :PENDING_RTG_COMPLETE and (select GROUP_CONCAT(pending_category) as pendingCategoryList from tran_desc_score_history where answer_seq=result.answer_seq) like :PENDING_01 and result.question_type=:QUES_TYPE_SPEAKING then 1 else 0 end) pending01CompleteSpkCnt, ");
		query.append("sum(case when result.stateList like :PENDING_RTG_COMPLETE and (select GROUP_CONCAT(pending_category) as pendingCategoryList from tran_desc_score_history where answer_seq=result.answer_seq) like :PENDING_01 and result.question_type=:QUES_TYPE_WRITING then 1 else 0 end) pending01CompleteWrtCnt, ");
		query.append("sum(case when result.stateList like :PENDING_RTG_MISMATCH and (select GROUP_CONCAT(pending_category) as pendingCategoryList from tran_desc_score_history where answer_seq=result.answer_seq) like :PENDING_01 and result.question_type=:QUES_TYPE_SPEAKING then 1 else 0 end) pending01MismatchSpkCnt, ");
		query.append("sum(case when result.stateList like :PENDING_RTG_MISMATCH and (select GROUP_CONCAT(pending_category) as pendingCategoryList from tran_desc_score_history where answer_seq=result.answer_seq) like :PENDING_01 and result.question_type=:QUES_TYPE_WRITING then 1 else 0 end) pending01MismatchWrtCnt, ");
		query.append("sum(case when result.stateList like :PENDING_RTG_SECOND_WAIT and (select GROUP_CONCAT(pending_category) as pendingCategoryList from tran_desc_score_history where answer_seq=result.answer_seq) like :PENDING_02 and result.question_type=:QUES_TYPE_SPEAKING then 1 else 0 end) pending02SecondWaitSpkCnt, ");
		query.append("sum(case when result.stateList like :PENDING_RTG_SECOND_WAIT and (select GROUP_CONCAT(pending_category) as pendingCategoryList from tran_desc_score_history where answer_seq=result.answer_seq) like :PENDING_02 and result.question_type=:QUES_TYPE_WRITING then 1 else 0 end) pending02SecondWaitWrtCnt, ");
		query.append("sum(case when result.stateList like :PENDING_RTG_PENDING and (select GROUP_CONCAT(pending_category) as pendingCategoryList from tran_desc_score_history where answer_seq=result.answer_seq) like :PENDING_02 and result.question_type=:QUES_TYPE_SPEAKING then 1 else 0 end) pending02PendingSpkCnt, ");
		query.append("sum(case when result.stateList like :PENDING_RTG_PENDING and (select GROUP_CONCAT(pending_category) as pendingCategoryList from tran_desc_score_history where answer_seq=result.answer_seq) like :PENDING_02 and result.question_type=:QUES_TYPE_WRITING then 1 else 0 end) pending02PendingWrtCnt, ");
		query.append("sum(case when result.stateList like :PENDING_RTG_COMPLETE and (select GROUP_CONCAT(pending_category) as pendingCategoryList from tran_desc_score_history where answer_seq=result.answer_seq) like :PENDING_02 and result.question_type=:QUES_TYPE_SPEAKING then 1 else 0 end) pending02CompleteSpkCnt, ");
		query.append("sum(case when result.stateList like :PENDING_RTG_COMPLETE and (select GROUP_CONCAT(pending_category) as pendingCategoryList from tran_desc_score_history where answer_seq=result.answer_seq) like :PENDING_02 and result.question_type=:QUES_TYPE_WRITING then 1 else 0 end) pending02CompleteWrtCnt, ");
		query.append("sum(case when result.stateList like :PENDING_RTG_MISMATCH and (select GROUP_CONCAT(pending_category) as pendingCategoryList from tran_desc_score_history where answer_seq=result.answer_seq) like :PENDING_02 and result.question_type=:QUES_TYPE_SPEAKING then 1 else 0 end) pending02MismatchSpkCnt, ");
		query.append("sum(case when result.stateList like :PENDING_RTG_MISMATCH and (select GROUP_CONCAT(pending_category) as pendingCategoryList from tran_desc_score_history where answer_seq=result.answer_seq) like :PENDING_02 and result.question_type=:QUES_TYPE_WRITING then 1 else 0 end) pending02MismatchWrtCnt, ");
		query.append("sum(case when result.stateList like :PENDING_RTG_SECOND_WAIT and (select GROUP_CONCAT(pending_category) as pendingCategoryList from tran_desc_score_history where answer_seq=result.answer_seq) like :PENDING_03 and result.question_type=:QUES_TYPE_SPEAKING then 1 else 0 end) pending03SecondWaitSpkCnt, ");
		query.append("sum(case when result.stateList like :PENDING_RTG_SECOND_WAIT and (select GROUP_CONCAT(pending_category) as pendingCategoryList from tran_desc_score_history where answer_seq=result.answer_seq) like :PENDING_03 and result.question_type=:QUES_TYPE_WRITING then 1 else 0 end) pending03SecondWaitWrtCnt, ");
		query.append("sum(case when result.stateList like :PENDING_RTG_PENDING and (select GROUP_CONCAT(pending_category) as pendingCategoryList from tran_desc_score_history where answer_seq=result.answer_seq) like :PENDING_03 and result.question_type=:QUES_TYPE_SPEAKING then 1 else 0 end) pending03PendingSpkCnt, ");
		query.append("sum(case when result.stateList like :PENDING_RTG_PENDING and (select GROUP_CONCAT(pending_category) as pendingCategoryList from tran_desc_score_history where answer_seq=result.answer_seq) like :PENDING_03 and result.question_type=:QUES_TYPE_WRITING then 1 else 0 end) pending03PendingWrtCnt, ");
		query.append("sum(case when result.stateList like :PENDING_RTG_COMPLETE and (select GROUP_CONCAT(pending_category) as pendingCategoryList from tran_desc_score_history where answer_seq=result.answer_seq) like :PENDING_03 and result.question_type=:QUES_TYPE_SPEAKING then 1 else 0 end) pending03CompleteSpkCnt, ");
		query.append("sum(case when result.stateList like :PENDING_RTG_COMPLETE and (select GROUP_CONCAT(pending_category) as pendingCategoryList from tran_desc_score_history where answer_seq=result.answer_seq) like :PENDING_03 and result.question_type=:QUES_TYPE_WRITING then 1 else 0 end) pending03CompleteWrtCnt, ");
		query.append("sum(case when result.stateList like :PENDING_RTG_MISMATCH and (select GROUP_CONCAT(pending_category) as pendingCategoryList from tran_desc_score_history where answer_seq=result.answer_seq) like :PENDING_03 and result.question_type=:QUES_TYPE_SPEAKING then 1 else 0 end) pending03MismatchSpkCnt, ");
		query.append("sum(case when result.stateList like :PENDING_RTG_MISMATCH and (select GROUP_CONCAT(pending_category) as pendingCategoryList from tran_desc_score_history where answer_seq=result.answer_seq) like :PENDING_03 and result.question_type=:QUES_TYPE_WRITING then 1 else 0 end) pending03MismatchWrtCnt, ");
		query.append("sum(case when result.stateList like :PENDING_RTG_SECOND_WAIT and (select GROUP_CONCAT(pending_category) as pendingCategoryList from tran_desc_score_history where answer_seq=result.answer_seq) like :PENDING_04 and result.question_type=:QUES_TYPE_SPEAKING then 1 else 0 end) pending04SecondWaitSpkCnt, ");
		query.append("sum(case when result.stateList like :PENDING_RTG_SECOND_WAIT and (select GROUP_CONCAT(pending_category) as pendingCategoryList from tran_desc_score_history where answer_seq=result.answer_seq) like :PENDING_04 and result.question_type=:QUES_TYPE_WRITING then 1 else 0 end) pending04SecondWaitWrtCnt, ");
		query.append("sum(case when result.stateList like :PENDING_RTG_PENDING and (select GROUP_CONCAT(pending_category) as pendingCategoryList from tran_desc_score_history where answer_seq=result.answer_seq) like :PENDING_04 and result.question_type=:QUES_TYPE_SPEAKING then 1 else 0 end) pending04PendingSpkCnt, ");
		query.append("sum(case when result.stateList like :PENDING_RTG_PENDING and (select GROUP_CONCAT(pending_category) as pendingCategoryList from tran_desc_score_history where answer_seq=result.answer_seq) like :PENDING_04 and result.question_type=:QUES_TYPE_WRITING then 1 else 0 end) pending04PendingWrtCnt, ");
		query.append("sum(case when result.stateList like :PENDING_RTG_COMPLETE and (select GROUP_CONCAT(pending_category) as pendingCategoryList from tran_desc_score_history where answer_seq=result.answer_seq) like :PENDING_04 and result.question_type=:QUES_TYPE_SPEAKING then 1 else 0 end) pending04CompleteSpkCnt, ");
		query.append("sum(case when result.stateList like :PENDING_RTG_COMPLETE and (select GROUP_CONCAT(pending_category) as pendingCategoryList from tran_desc_score_history where answer_seq=result.answer_seq) like :PENDING_04 and result.question_type=:QUES_TYPE_WRITING then 1 else 0 end) pending04CompleteWrtCnt, ");
		query.append("sum(case when result.stateList like :PENDING_RTG_MISMATCH and (select GROUP_CONCAT(pending_category) as pendingCategoryList from tran_desc_score_history where answer_seq=result.answer_seq) like :PENDING_04 and result.question_type=:QUES_TYPE_SPEAKING then 1 else 0 end) pending04MismatchSpkCnt, ");
		query.append("sum(case when result.stateList like :PENDING_RTG_MISMATCH and (select GROUP_CONCAT(pending_category) as pendingCategoryList from tran_desc_score_history where answer_seq=result.answer_seq) like :PENDING_04 and result.question_type=:QUES_TYPE_WRITING then 1 else 0 end) pending04MismatchWrtCnt, ");
		query.append("sum(case when result.stateList like :PENDING_RTG_SECOND_WAIT and (select GROUP_CONCAT(pending_category) as pendingCategoryList from tran_desc_score_history where answer_seq=result.answer_seq) like :PENDING_05 and result.question_type=:QUES_TYPE_SPEAKING then 1 else 0 end) pending05SecondWaitSpkCnt, ");
		query.append("sum(case when result.stateList like :PENDING_RTG_SECOND_WAIT and (select GROUP_CONCAT(pending_category) as pendingCategoryList from tran_desc_score_history where answer_seq=result.answer_seq) like :PENDING_05 and result.question_type=:QUES_TYPE_WRITING then 1 else 0 end) pending05SecondWaitWrtCnt, ");
		query.append("sum(case when result.stateList like :PENDING_RTG_PENDING and (select GROUP_CONCAT(pending_category) as pendingCategoryList from tran_desc_score_history where answer_seq=result.answer_seq) like :PENDING_05 and result.question_type=:QUES_TYPE_SPEAKING then 1 else 0 end) pending05PendingSpkCnt, ");
		query.append("sum(case when result.stateList like :PENDING_RTG_PENDING and (select GROUP_CONCAT(pending_category) as pendingCategoryList from tran_desc_score_history where answer_seq=result.answer_seq) like :PENDING_05 and result.question_type=:QUES_TYPE_WRITING then 1 else 0 end) pending05PendingWrtCnt, ");
		query.append("sum(case when result.stateList like :PENDING_RTG_COMPLETE and (select GROUP_CONCAT(pending_category) as pendingCategoryList from tran_desc_score_history where answer_seq=result.answer_seq) like :PENDING_05 and result.question_type=:QUES_TYPE_SPEAKING then 1 else 0 end) pending05CompleteSpkCnt, ");
		query.append("sum(case when result.stateList like :PENDING_RTG_COMPLETE and (select GROUP_CONCAT(pending_category) as pendingCategoryList from tran_desc_score_history where answer_seq=result.answer_seq) like :PENDING_05 and result.question_type=:QUES_TYPE_WRITING then 1 else 0 end) pending05CompleteWrtCnt, ");
		query.append("sum(case when result.stateList like :PENDING_RTG_MISMATCH and (select GROUP_CONCAT(pending_category) as pendingCategoryList from tran_desc_score_history where answer_seq=result.answer_seq) like :PENDING_05 and result.question_type=:QUES_TYPE_SPEAKING then 1 else 0 end) pending05MismatchSpkCnt, ");
		query.append("sum(case when result.stateList like :PENDING_RTG_MISMATCH and (select GROUP_CONCAT(pending_category) as pendingCategoryList from tran_desc_score_history where answer_seq=result.answer_seq) like :PENDING_05 and result.question_type=:QUES_TYPE_WRITING then 1 else 0 end) pending05MismatchWrtCnt ");
		query.append("FROM ");
		query.append("(select answer_seq, ");
		query.append("(case when ");
		query.append("(select image_file_name from tran_desc_score as tds where tds.answer_seq=tdsh.answer_seq) like :MP3_FORMAT ");
		query.append("then :QUES_TYPE_SPEAKING else :QUES_TYPE_WRITING end) as question_type, ");
		query.append("GROUP_CONCAT(scoring_state) as stateList ");
		query.append("FROM tran_desc_score_history as tdsh ");
		query.append("WHERE update_date between :FROM_DATE AND :TO_DATE ");
		query.append("AND valid_flag = :VALID_FLAG ");
		query.append("GROUP BY answer_seq) as result");
		
		try {
			return getHibernateTemplate(connectionString).execute(
					new HibernateCallback<DailyScoreInfo>() {
						public DailyScoreInfo doInHibernate(Session session)
								throws HibernateException {
							SQLQuery queryObj = session.createSQLQuery(query
									.toString());
							queryObj.setParameter("FROM_DATE", fromDate);
							queryObj.setParameter("TO_DATE", toDate);
							queryObj.setParameter("VALID_FLAG",
									WebAppConst.VALID_FLAG);
							queryObj.setParameter("QUES_TYPE_SPEAKING",
									WebAppConst.QUES_TYPE_SPEAKING);
							queryObj.setParameter("QUES_TYPE_WRITING",
									WebAppConst.QUES_TYPE_WRITING);
							queryObj.setParameter("MP3_FORMAT",
									WebAppConst.MP3_FORMAT);
							queryObj.setParameter("FIRST_RTG_SECOND_WAIT",
									WebAppConst.FIRST_RTG_SECOND_WAIT);
							queryObj.setParameter("FIRST_RTG_PENDING",
									WebAppConst.FIRST_RTG_PENDING);
							queryObj.setParameter("SECOND_RTG_COMPLETE",
									WebAppConst.SECOND_RTG_COMPLETE);
							queryObj.setParameter("SECOND_RTG_PENDING",
									WebAppConst.SECOND_RTG_PENDING);
							queryObj.setParameter("SECOND_RTG_MISMATCH",
									WebAppConst.SECOND_RTG_MISMATCH);
							queryObj.setParameter("MISMATCH_RTG_PENDING",
									WebAppConst.MISMATCH_RTG_PENDING);
							queryObj.setParameter("MISMATCH_RTG_COMPLETE",
									WebAppConst.MISMATCH_RTG_COMPLETE);
							queryObj.setParameter("PENDING_RTG_SECOND_WAIT",
									WebAppConst.PENDING_RTG_SECOND_WAIT);
							queryObj.setParameter("PENDING_RTG_PENDING",
									WebAppConst.PENDING_RTG_PENDING);
							queryObj.setParameter("PENDING_RTG_COMPLETE",
									WebAppConst.PENDING_RTG_COMPLETE);
							queryObj.setParameter("PENDING_RTG_MISMATCH",
									WebAppConst.PENDING_RTG_MISMATCH);
							queryObj.setParameter("PENDING_01",
									WebAppConst.PEND_CATE_01);
							queryObj.setParameter("PENDING_02",
									WebAppConst.PEND_CATE_02);
							queryObj.setParameter("PENDING_03",
									WebAppConst.PEND_CATE_03);
							queryObj.setParameter("PENDING_04",
									WebAppConst.PEND_CATE_04);
							queryObj.setParameter("PENDING_05",
									WebAppConst.PEND_CATE_05);

							setResultTransformerProperties(queryObj);
							
							return (DailyScoreInfo) queryObj.uniqueResult();
						}
					});
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> getDailyScoreQuesSeqInfo(
			String connectionString, final String fromDate, final String toDate) {

		final StringBuilder query = new StringBuilder();
		query.append("SELECT ");
		query.append("result.question_seq ");
		query.append("FROM ");
		query.append("(select question_seq, ");
		query.append("GROUP_CONCAT(scoring_state) as stateList ");
		query.append("FROM tran_desc_score_history tdsh ");
		query.append("WHERE update_date between :FROM_DATE AND :TO_DATE ");
		query.append("AND valid_flag = :VALID_FLAG ");
		query.append("GROUP BY answer_seq ");
		query.append("Having stateList like :FIRST_RTG_SECOND_WAIT ");
		query.append("OR stateList like :FIRST_RTG_PENDING ");
		query.append("OR stateList like :SECOND_RTG_COMPLETE ");
		query.append("OR stateList like :SECOND_RTG_PENDING ");
		query.append("OR stateList like :SECOND_RTG_MISMATCH ");
		query.append("OR stateList like :MISMATCH_RTG_PENDING ");
		query.append("OR stateList like :MISMATCH_RTG_COMPLETE ");
		query.append("OR stateList like :PENDING_RTG_SECOND_WAIT ");
		query.append("OR stateList like :PENDING_RTG_PENDING ");
		query.append("OR stateList like :PENDING_RTG_COMPLETE ");
		query.append("OR stateList like :PENDING_RTG_MISMATCH) as result GROUP BY result.question_seq");
		try {
			return getHibernateTemplate(connectionString).execute(
					new HibernateCallback<List<Integer>>() {
						public List<Integer> doInHibernate(Session session)
								throws HibernateException {
							SQLQuery queryObj = session.createSQLQuery(query
									.toString());
							queryObj.setParameter("FROM_DATE", fromDate);
							queryObj.setParameter("TO_DATE", toDate);
							queryObj.setParameter("VALID_FLAG",
									WebAppConst.VALID_FLAG);
							queryObj.setParameter("FIRST_RTG_SECOND_WAIT",
									WebAppConst.FIRST_RTG_SECOND_WAIT);
							queryObj.setParameter("FIRST_RTG_PENDING",
									WebAppConst.FIRST_RTG_PENDING);
							queryObj.setParameter("SECOND_RTG_COMPLETE",
									WebAppConst.SECOND_RTG_COMPLETE);
							queryObj.setParameter("SECOND_RTG_PENDING",
									WebAppConst.SECOND_RTG_PENDING);
							queryObj.setParameter("SECOND_RTG_MISMATCH",
									WebAppConst.SECOND_RTG_MISMATCH);
							queryObj.setParameter("MISMATCH_RTG_PENDING",
									WebAppConst.MISMATCH_RTG_PENDING);
							queryObj.setParameter("MISMATCH_RTG_COMPLETE",
									WebAppConst.MISMATCH_RTG_COMPLETE);
							queryObj.setParameter("PENDING_RTG_SECOND_WAIT",
									WebAppConst.PENDING_RTG_SECOND_WAIT);
							queryObj.setParameter("PENDING_RTG_PENDING",
									WebAppConst.PENDING_RTG_PENDING);
							queryObj.setParameter("PENDING_RTG_COMPLETE",
									WebAppConst.PENDING_RTG_COMPLETE);
							queryObj.setParameter("PENDING_RTG_MISMATCH",
									WebAppConst.PENDING_RTG_MISMATCH);

							return queryObj.list();
						}
					});
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getStateWiseScoringData(String connectionString,
			final String stateList, final String fromDate, final String toDate) {

		final StringBuilder query = new StringBuilder();
		query.append("SELECT ");
		query.append("result.question_seq, ");
		query.append("result.question_type, ");
		query.append("count(*) as questionSeqWiseCount ");
		query.append("FROM ");
		query.append("(select question_seq, ");
		query.append("(case when ");
		query.append("(select image_file_name from tran_desc_score as tds where tds.answer_seq=tdsh.answer_seq) like :MP3_FORMAT ");
		query.append("then :QUES_TYPE_SPEAKING else :QUES_TYPE_WRITING end) as question_type, ");
		query.append("GROUP_CONCAT(scoring_state) as stateList ");
		query.append("FROM tran_desc_score_history tdsh ");
		query.append("WHERE update_date between :FROM_DATE AND :TO_DATE ");
		query.append("AND valid_flag = :VALID_FLAG ");
		query.append("GROUP BY answer_seq HAVING stateList like :STATE_LIST) as result GROUP BY result.question_seq");
		try {
			return getHibernateTemplate(connectionString).execute(
					new HibernateCallback<List<Object[]>>() {
						public List<Object[]> doInHibernate(Session session)
								throws HibernateException {
							SQLQuery queryObj = session.createSQLQuery(query
									.toString());
							queryObj.setParameter("FROM_DATE", fromDate);
							queryObj.setParameter("TO_DATE", toDate);
							queryObj.setParameter("STATE_LIST", stateList);
							queryObj.setParameter("QUES_TYPE_SPEAKING",
									WebAppConst.QUES_TYPE_SPEAKING);
							queryObj.setParameter("QUES_TYPE_WRITING",
									WebAppConst.QUES_TYPE_WRITING);
							queryObj.setParameter("MP3_FORMAT",
									WebAppConst.MP3_FORMAT);
							queryObj.setParameter("VALID_FLAG",
									WebAppConst.VALID_FLAG);
							return queryObj.list();
						}
					});
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getPendCategoryAndStateWiseScoringData(
			String connectionString, final String stateList,
			final String pendingCategory, final String fromDate,
			final String toDate) {

		final StringBuilder query = new StringBuilder();
		query.append("SELECT ");
		query.append("result.question_seq, ");
		query.append("result.question_type, ");
		query.append("count(*) as questionSeqWiseCount ");
		query.append("FROM ");
		query.append("(select question_seq, ");
		query.append("(case when ");
		query.append("(select image_file_name from tran_desc_score as tds where tds.answer_seq=tdsh.answer_seq) like :MP3_FORMAT ");
		query.append("then :QUES_TYPE_SPEAKING else :QUES_TYPE_WRITING end) as question_type, ");
		query.append("GROUP_CONCAT(scoring_state) as stateList ");
		query.append("FROM tran_desc_score_history tdsh ");
		query.append("WHERE update_date between :FROM_DATE AND :TO_DATE ");
		query.append("AND valid_flag = :VALID_FLAG ");
		query.append("GROUP BY answer_seq HAVING stateList like :STATE_LIST and ");
		query.append("(select GROUP_CONCAT(pending_category) as pendingCategoryList ");
		query.append("from tran_desc_score_history ");
		query.append("where answer_seq=tdsh.answer_seq) like :PENDING_CATEGORY) as result ");
		query.append("GROUP BY result.question_seq");
		try {
			return getHibernateTemplate(connectionString).execute(
					new HibernateCallback<List<Object[]>>() {
						public List<Object[]> doInHibernate(Session session)
								throws HibernateException {
							SQLQuery queryObj = session.createSQLQuery(query
									.toString());
							queryObj.setParameter("FROM_DATE", fromDate);
							queryObj.setParameter("TO_DATE", toDate);
							queryObj.setParameter("STATE_LIST", stateList);
							queryObj.setParameter("PENDING_CATEGORY", pendingCategory);
							queryObj.setParameter("QUES_TYPE_SPEAKING",
									WebAppConst.QUES_TYPE_SPEAKING);
							queryObj.setParameter("QUES_TYPE_WRITING",
									WebAppConst.QUES_TYPE_WRITING);
							queryObj.setParameter("MP3_FORMAT",
									WebAppConst.MP3_FORMAT);
							queryObj.setParameter("VALID_FLAG",
									WebAppConst.VALID_FLAG);
							return queryObj.list();
						}
					});
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public Object[] getDefectiveSciptCount(String connectionString) {
		final StringBuilder query = new StringBuilder();
		query.append("SELECT ");
		query.append("sum(case when image_file_name like :MP3_FORMAT then 1 else 0 end) as speakingCnt, ");
		query.append("sum(case when image_file_name like :TXT_FORMAT then 1 else 0 end) as writingCnt ");
		query.append("FROM tran_desc_score ");
		query.append("WHERE pending_category like :PENDING_CATEGORY ");
		query.append("AND valid_flag = :VALID_FLAG ");
		try {
			return getHibernateTemplate(connectionString).execute(
					new HibernateCallback<Object[]>() {
						public Object[] doInHibernate(Session session)
								throws HibernateException {
							Query queryObj = session.createSQLQuery(query
									.toString());
							queryObj.setParameter("MP3_FORMAT",
									WebAppConst.MP3_FORMAT);
							queryObj.setParameter("TXT_FORMAT",
									WebAppConst.TXT_FORMAT);
							queryObj.setParameter("PENDING_CATEGORY",
									WebAppConst.PEND_CATE_04);
							queryObj.setParameter("VALID_FLAG",
									WebAppConst.VALID_FLAG);
							return (Object[]) queryObj.uniqueResult();
						}
					});
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	private void setResultTransformerProperties(SQLQuery queryObj) {
		queryObj.addScalar("firstRtgSecondWaitSpkCnt", IntegerType.INSTANCE);
		queryObj.addScalar("firstRtgSecondWaitWrtCnt", IntegerType.INSTANCE);
		queryObj.addScalar("firstRtgPendingSpkCnt", IntegerType.INSTANCE);
		queryObj.addScalar("firstRtgPendingWrtCnt", IntegerType.INSTANCE);
		queryObj.addScalar("secondRtgCompleteSpkCnt", IntegerType.INSTANCE);
		queryObj.addScalar("secondRtgCompleteWrtCnt", IntegerType.INSTANCE);
		queryObj.addScalar("secondRtgPendingSpkCnt", IntegerType.INSTANCE);
		queryObj.addScalar("secondRtgPendingWrtCnt", IntegerType.INSTANCE);
		queryObj.addScalar("secondRtgMismatchSpkCnt", IntegerType.INSTANCE);
		queryObj.addScalar("secondRtgMismatchWrtCnt", IntegerType.INSTANCE);
		queryObj.addScalar("mismatchRtgPendingSpkCnt", IntegerType.INSTANCE);
		queryObj.addScalar("mismatchRtgPendingWrtCnt", IntegerType.INSTANCE);
		queryObj.addScalar("mismatchRtgCompleteSpkCnt", IntegerType.INSTANCE);
		queryObj.addScalar("mismatchRtgCompleteWrtCnt", IntegerType.INSTANCE);
		queryObj.addScalar("pending01SecondWaitSpkCnt", IntegerType.INSTANCE);
		queryObj.addScalar("pending01SecondWaitWrtCnt", IntegerType.INSTANCE);
		queryObj.addScalar("pending01PendingSpkCnt", IntegerType.INSTANCE);
		queryObj.addScalar("pending01PendingWrtCnt", IntegerType.INSTANCE);
		queryObj.addScalar("pending01CompleteSpkCnt", IntegerType.INSTANCE);
		queryObj.addScalar("pending01CompleteWrtCnt", IntegerType.INSTANCE);
		queryObj.addScalar("pending01MismatchSpkCnt", IntegerType.INSTANCE);
		queryObj.addScalar("pending01MismatchWrtCnt", IntegerType.INSTANCE);
		queryObj.addScalar("pending02SecondWaitSpkCnt", IntegerType.INSTANCE);
		queryObj.addScalar("pending02SecondWaitWrtCnt", IntegerType.INSTANCE);
		queryObj.addScalar("pending02PendingSpkCnt", IntegerType.INSTANCE);
		queryObj.addScalar("pending02PendingWrtCnt", IntegerType.INSTANCE);
		queryObj.addScalar("pending02CompleteSpkCnt", IntegerType.INSTANCE);
		queryObj.addScalar("pending02CompleteWrtCnt", IntegerType.INSTANCE);
		queryObj.addScalar("pending02MismatchSpkCnt", IntegerType.INSTANCE);
		queryObj.addScalar("pending02MismatchWrtCnt", IntegerType.INSTANCE);
		queryObj.addScalar("pending03SecondWaitSpkCnt", IntegerType.INSTANCE);
		queryObj.addScalar("pending03SecondWaitWrtCnt", IntegerType.INSTANCE);
		queryObj.addScalar("pending03PendingSpkCnt", IntegerType.INSTANCE);
		queryObj.addScalar("pending03PendingWrtCnt", IntegerType.INSTANCE);
		queryObj.addScalar("pending03CompleteSpkCnt", IntegerType.INSTANCE);
		queryObj.addScalar("pending03CompleteWrtCnt", IntegerType.INSTANCE);
		queryObj.addScalar("pending03MismatchSpkCnt", IntegerType.INSTANCE);
		queryObj.addScalar("pending03MismatchWrtCnt", IntegerType.INSTANCE);
		queryObj.addScalar("pending04SecondWaitSpkCnt", IntegerType.INSTANCE);
		queryObj.addScalar("pending04SecondWaitWrtCnt", IntegerType.INSTANCE);
		queryObj.addScalar("pending04PendingSpkCnt", IntegerType.INSTANCE);
		queryObj.addScalar("pending04PendingWrtCnt", IntegerType.INSTANCE);
		queryObj.addScalar("pending04CompleteSpkCnt", IntegerType.INSTANCE);
		queryObj.addScalar("pending04CompleteWrtCnt", IntegerType.INSTANCE);
		queryObj.addScalar("pending04MismatchSpkCnt", IntegerType.INSTANCE);
		queryObj.addScalar("pending04MismatchWrtCnt", IntegerType.INSTANCE);
		queryObj.addScalar("pending05SecondWaitSpkCnt", IntegerType.INSTANCE);
		queryObj.addScalar("pending05SecondWaitWrtCnt", IntegerType.INSTANCE);
		queryObj.addScalar("pending05PendingSpkCnt", IntegerType.INSTANCE);
		queryObj.addScalar("pending05PendingWrtCnt", IntegerType.INSTANCE);
		queryObj.addScalar("pending05CompleteSpkCnt", IntegerType.INSTANCE);
		queryObj.addScalar("pending05CompleteWrtCnt", IntegerType.INSTANCE);
		queryObj.addScalar("pending05MismatchSpkCnt", IntegerType.INSTANCE);
		queryObj.addScalar("pending05MismatchWrtCnt", IntegerType.INSTANCE);
		queryObj.setResultTransformer(Transformers
				.aliasToBean(DailyScoreInfo.class));
	}
	
	public static String getPropertyFromPropertyFile(String propertyFileName,
			String key) {
		return ResourceBundle.getBundle(propertyFileName).getString(key);
	}
}
