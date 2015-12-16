package rest.webservices.java;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;

import com.saiten.dao.MstQuestionDAO;
import com.saiten.dao.MstScorerDAO;
import com.saiten.dao.MstScorerQuestionDAO;
import com.saiten.exception.SaitenRuntimeException;
import com.saiten.info.MstScorerInfo;
import com.saiten.manager.SaitenTransactionManager;
import com.saiten.model.MstQuestion;
import com.saiten.model.MstScorer;
import com.saiten.model.MstScorerQuestion;
import com.saiten.model.MstScorerQuestionId;
import com.saiten.model.MstScorerRole;
import com.saiten.util.WebAppConst;

public class UserRegistration /* implements ServletContextAware */{

	private SaitenTransactionManager saitenTransactionManager;
	private MstScorerDAO mstScorerDAO;
	private MstQuestionDAO mstQuestionDAO;
	private MstScorerQuestionDAO mstScorerQuestionDAO;

	// private ServletContext servletContext;
	// private SaitenMasterUtil saitenMasterUtil;

	public boolean registerWG(MstScorerInfo mstScorerInfo,
			List<String> subjectCodeList) {
		PlatformTransactionManager platformTransactionManager = null;
		TransactionStatus transactionStatus = null;

		try {
			platformTransactionManager = saitenTransactionManager
					.getTransactionManger();
			transactionStatus = saitenTransactionManager
					.beginTransaction(platformTransactionManager);

			if (!getMstScorerDAO().isUserAlreadyExist(
					mstScorerInfo.getScorerId())) {

				saveMstScorer(mstScorerInfo);

				// add new user entry in mst_scorer_question, first fetch
				// question sequences from mst_question table.
				List<Integer> questionSeqList = getQuestionListBySubjectCodeList(subjectCodeList);

				if (questionSeqList != null) {
					insertMstScorerQuestionData(questionSeqList,
							mstScorerInfo.getScorerId());
				}
			} else {
				updateMstScorer(mstScorerInfo);
				deleteByScorerId(mstScorerInfo.getScorerId());
				List<Integer> questionSeqList = getQuestionListBySubjectCodeList(subjectCodeList);
				if (questionSeqList != null) {
					insertMstScorerQuestionData(questionSeqList,
							mstScorerInfo.getScorerId());
				}
			}
			platformTransactionManager.commit(transactionStatus);
			return true;
		} catch (HibernateException he) {
			if (platformTransactionManager != null)
				platformTransactionManager.rollback(transactionStatus);
			throw new SaitenRuntimeException("", he);
		} catch (SaitenRuntimeException we) {
			if (platformTransactionManager != null)
				platformTransactionManager.rollback(transactionStatus);
			throw we;
		} catch (Exception e) {
			if (platformTransactionManager != null)
				platformTransactionManager.rollback(transactionStatus);
			throw new SaitenRuntimeException("", e);
		}

	}

	public boolean registerSaitensha(MstScorerInfo mstScorerInfo,
			List<Integer> questionSeqList) {
		PlatformTransactionManager platformTransactionManager = null;
		TransactionStatus transactionStatus = null;

		try {
			platformTransactionManager = saitenTransactionManager
					.getTransactionManger();
			transactionStatus = saitenTransactionManager
					.beginTransaction(platformTransactionManager);

			if (!getMstScorerDAO().isUserAlreadyExist(
					mstScorerInfo.getScorerId())) {
				saveMstScorer(mstScorerInfo);
				insertMstScorerQuestionData(questionSeqList,
						mstScorerInfo.getScorerId());
			} else {
				updateMstScorer(mstScorerInfo);
				deleteByScorerId(mstScorerInfo.getScorerId());
				insertMstScorerQuestionData(questionSeqList,
						mstScorerInfo.getScorerId());
			}
			platformTransactionManager.commit(transactionStatus);
			return true;
		} catch (HibernateException he) {
			if (platformTransactionManager != null)
				platformTransactionManager.rollback(transactionStatus);
			throw new SaitenRuntimeException("", he);
		} catch (SaitenRuntimeException we) {
			if (platformTransactionManager != null)
				platformTransactionManager.rollback(transactionStatus);
			throw we;
		} catch (Exception e) {
			if (platformTransactionManager != null)
				platformTransactionManager.rollback(transactionStatus);
			throw new SaitenRuntimeException("", e);
		}
	}

	public boolean registerAdmin(MstScorerInfo mstScorerInfo) {
		PlatformTransactionManager platformTransactionManager = null;
		TransactionStatus transactionStatus = null;

		try {
			platformTransactionManager = saitenTransactionManager
					.getTransactionManger();
			transactionStatus = saitenTransactionManager
					.beginTransaction(platformTransactionManager);

			if (!getMstScorerDAO().isUserAlreadyExist(
					mstScorerInfo.getScorerId())) {
				saveMstScorer(mstScorerInfo);
			} else {
				updateMstScorer(mstScorerInfo);
				deleteByScorerId(mstScorerInfo.getScorerId());
			}
			platformTransactionManager.commit(transactionStatus);
			return true;
		} catch (HibernateException he) {
			if (platformTransactionManager != null)
				platformTransactionManager.rollback(transactionStatus);
			throw new SaitenRuntimeException("", he);
		} catch (SaitenRuntimeException we) {
			if (platformTransactionManager != null)
				platformTransactionManager.rollback(transactionStatus);
			throw we;
		} catch (Exception e) {
			if (platformTransactionManager != null)
				platformTransactionManager.rollback(transactionStatus);

			throw new SaitenRuntimeException("", e);
		}
	}

	private void saveMstScorer(MstScorerInfo mstScorerInfo) {
		MstScorer mstScorer = new MstScorer();
		prepareMstScorer(mstScorer, mstScorerInfo);
		// add new user in mst_scorer table.
		getMstScorerDAO().save(mstScorer);
	}

	private void updateMstScorer(MstScorerInfo mstScorerInfo) {
		MstScorer mstScorer = new MstScorer();
		prepareMstScorer(mstScorer, mstScorerInfo);
		// update existing user in mst_scorer table.
		getMstScorerDAO().update(mstScorer);
	}

	private void prepareMstScorer(MstScorer mstScorer,
			MstScorerInfo mstScorerInfo) {
		mstScorer.setScorerId(mstScorerInfo.getScorerId());
		mstScorer.setPassword(mstScorerInfo.getPassword());
		mstScorer.setScorerName(mstScorerInfo.getScorerName());
		mstScorer.setNoDbUpdate(mstScorerInfo.getNoDbUpdate());
		mstScorer.setDeleteFlag(mstScorerInfo.getDeleteFlag());
		mstScorer.setUpdatePersonId(mstScorerInfo.getUpdatePersonId());
		mstScorer.setCreateDate(mstScorerInfo.getCreateDate());
		mstScorer.setUpdateDate(mstScorerInfo.getUpdateDate());

		Map<String, Byte> mstScorerRoleMap = getMstScorerRoleMap();
		Byte roleId = mstScorerRoleMap.get(mstScorerInfo.getUserType());
		MstScorerRole mstScorerRole = new MstScorerRole(roleId);
		mstScorer.setMstScorerRole(mstScorerRole);
	}

	private void insertMstScorerQuestionData(List<Integer> questionSeqList,
			String scorerId) {
		// add new user entry in mst_scorer_question, first fetch
		if (!questionSeqList.isEmpty()) {

			List<MstScorerQuestion> mstScorerQuestionList = prepareMstScorerQuestionList(
					questionSeqList, scorerId);

			getMstScorerQuestionDAO().saveAll(mstScorerQuestionList);

		}
	}

	@SuppressWarnings("rawtypes")
	private Map<String, Byte> getMstScorerRoleMap() {

		Map<String, Byte> mstScorerRoleMap = new LinkedHashMap<String, Byte>();

		List mstScorerRoleList = getMstScorerDAO().getMstScorerRoleList();

		if (!mstScorerRoleList.isEmpty()) {

			for (Object object : mstScorerRoleList) {
				Object[] mstScorerRoleObjects = (Object[]) object;
				mstScorerRoleMap.put((String) mstScorerRoleObjects[1],
						(Byte) mstScorerRoleObjects[0]);
			}
		}

		return mstScorerRoleMap;
	}

	private List<MstScorerQuestion> prepareMstScorerQuestionList(
			List<Integer> questionSeqList, String scorerId) {
		List<MstScorerQuestion> mstScorerQuestionList = new ArrayList<MstScorerQuestion>();

		for (Integer questionSeq : questionSeqList) {
			MstScorerQuestion mstScorerQuestion = new MstScorerQuestion();
			mstScorerQuestion.setCreateDate(new Date());
			mstScorerQuestion.setDeleteFlag(WebAppConst.DELETE_FLAG);

			MstScorerQuestionId mstScorerQuestionId = new MstScorerQuestionId();
			mstScorerQuestionId.setQuestionSeq(questionSeq);
			mstScorerQuestionId.setScorerId(scorerId);
			mstScorerQuestion.setId(mstScorerQuestionId);

			MstQuestion mstQuestion = new MstQuestion();
			mstQuestion.setQuestionSeq(questionSeq);
			mstScorerQuestion.setMstQuestion(mstQuestion);

			MstScorer mstScorer = new MstScorer();
			mstScorer.setScorerId(scorerId);
			mstScorerQuestion.setMstScorer(mstScorer);

			mstScorerQuestion.setUpdateDate(new Date());
			mstScorerQuestion.setUpdatePersonId(scorerId);

			mstScorerQuestionList.add(mstScorerQuestion);
		}

		return mstScorerQuestionList;
	}

	private void deleteByScorerId(String scorerId) {
		mstScorerQuestionDAO.deleteByScorerid(scorerId);
	}

	private List<Integer> getQuestionListBySubjectCodeList(
			List<String> subjectCodeList) {
		List<MstQuestion> mstQuestionList = getMstQuestionDAO()
				.findQuestionListBySubjectCodeList(subjectCodeList);
		List<Integer> questionSeqList = null;
		if (!mstQuestionList.isEmpty()) {
			questionSeqList = new ArrayList<Integer>();
			for (MstQuestion mstQuestion : mstQuestionList) {
				questionSeqList.add(mstQuestion.getQuestionSeq());
			}

		}
		return questionSeqList;
	}

	/*
	 * private void saveInMstScorerQuestionMap(List<MstQuestion>
	 * mstQuestionList, String scorerId) {
	 * 
	 * LinkedHashMap<String, LinkedHashMap<Integer, MstQuestion>>
	 * mstScorerQuestionMap = ((SaitenConfig) servletContext
	 * .getAttribute("saitenConfigObject")).getMstScorerQuestionMap();
	 * 
	 * // reused builsMstQuestionMap() method from createSaitenConfigLogic.
	 * LinkedHashMap<Integer, MstQuestion> mstQuestionMap = saitenMasterUtil
	 * .buildMstQuestionMap(mstQuestionList);
	 * 
	 * mstScorerQuestionMap.put(scorerId, mstQuestionMap);
	 * 
	 * }
	 */

	/**
	 * 
	 * @param saitenTransactionManager
	 */
	public void setSaitenTransactionManager(
			SaitenTransactionManager saitenTransactionManager) {
		this.saitenTransactionManager = saitenTransactionManager;
	}

	public void setMstScorerDAO(MstScorerDAO mstScorerDAO) {
		this.mstScorerDAO = mstScorerDAO;
	}

	public MstScorerDAO getMstScorerDAO() {
		return mstScorerDAO;
	}

	public MstQuestionDAO getMstQuestionDAO() {
		return mstQuestionDAO;
	}

	public void setMstQuestionDAO(MstQuestionDAO mstQuestionDAO) {
		this.mstQuestionDAO = mstQuestionDAO;
	}

	public MstScorerQuestionDAO getMstScorerQuestionDAO() {
		return mstScorerQuestionDAO;
	}

	public void setMstScorerQuestionDAO(
			MstScorerQuestionDAO mstScorerQuestionDAO) {
		this.mstScorerQuestionDAO = mstScorerQuestionDAO;
	}

	/**
	 * 
	 * 
	 * @see org.springframework.web.context.ServletContextAware#setServletContext
	 *      (javax.servlet.ServletContext)
	 */
	/*
	 * @Override public void setServletContext(ServletContext servletContext) {
	 * this.servletContext = servletContext; }
	 */

	/*
	 * public void setSaitenMasterUtil(SaitenMasterUtil saitenMasterUtil) {
	 * this.saitenMasterUtil = saitenMasterUtil; }
	 */

}
