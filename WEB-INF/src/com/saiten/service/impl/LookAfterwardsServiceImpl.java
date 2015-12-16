/**
 * 
 */
package com.saiten.service.impl;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;

import com.saiten.dao.TranLookAfterwardsDAO;
import com.saiten.exception.SaitenRuntimeException;
import com.saiten.info.LookAfterwardsInfo;
import com.saiten.model.TranLookAfterwards;
import com.saiten.service.LookAfterwardsService;
import com.saiten.util.ErrorCode;

/**
 * @author user
 * 
 */
public class LookAfterwardsServiceImpl implements LookAfterwardsService {

	private TranLookAfterwardsDAO tranLookAfterwardsDAO;

	/**
	 * @param tranLookAfterwardsDAO
	 *            the tranLookAfterwardsDAO to set
	 */
	public void setTranLookAfterwardsDAO(
			TranLookAfterwardsDAO tranLookAfterwardsDAO) {
		this.tranLookAfterwardsDAO = tranLookAfterwardsDAO;
	}

	@Override
	public Integer doMark(LookAfterwardsInfo lookAfterwardsInfo,
			String connectionString) {

		try {
			TranLookAfterwards tranLookAfterwards = tranLookAfterwardsDAO
					.fetchByAnswerSeqAndUpdatePersonId(
							lookAfterwardsInfo.getAnswerSeq(),
							lookAfterwardsInfo.getUpdatePersonId(),
							connectionString);
			if (tranLookAfterwards == null) {
				tranLookAfterwards = new TranLookAfterwards();
				copy(lookAfterwardsInfo, tranLookAfterwards);
				tranLookAfterwards.setCreateDate(new Date());
			} else {
				tranLookAfterwards.setComment(lookAfterwardsInfo.getComment());
				tranLookAfterwards.setLookAftFlag(lookAfterwardsInfo
						.getLookAftFlag());
				tranLookAfterwards.setUpdateDate(new Date());
			}

			if (tranLookAfterwards.getLookAftSeq() == null) {
				return tranLookAfterwardsDAO.save(tranLookAfterwards,
						connectionString);
			} else {
				Integer lookAftSeq = null;
				lookAftSeq = tranLookAfterwards.getLookAftSeq();
				tranLookAfterwardsDAO.update(tranLookAfterwards,
						connectionString);
				return lookAftSeq;
			}
		} catch (HibernateException he) {
			throw new SaitenRuntimeException(
					ErrorCode.LOOK_AFTERWARDS_HIBERNATE_EXCEPTION, he);
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.LOOK_AFTERWARDS_SERVICE_EXCEPTION, e);
		}
	}

	@Override
	public void doUnmark(LookAfterwardsInfo lookAfterwardsInfo,
			String connectionString) {
		try {
			TranLookAfterwards tranLookAfterwards = tranLookAfterwardsDAO.fetchById(lookAfterwardsInfo.getLookAftSeq(), connectionString);
			copy(lookAfterwardsInfo, tranLookAfterwards);
			tranLookAfterwardsDAO.update(tranLookAfterwards, connectionString);
		} catch (HibernateException he) {
			throw new SaitenRuntimeException(
					ErrorCode.LOOK_AFTERWARDS_HIBERNATE_EXCEPTION, he);
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.LOOK_AFTERWARDS_SERVICE_EXCEPTION, e);
		}

	}

	private void copy(LookAfterwardsInfo lookAfterwardsInfo,
			TranLookAfterwards tranLookAfterwards) {
		tranLookAfterwards.setLookAftSeq(lookAfterwardsInfo.getLookAftSeq());
		tranLookAfterwards.setAnswerSeq(lookAfterwardsInfo.getAnswerSeq());
		tranLookAfterwards.setUpdatePersonId(lookAfterwardsInfo
				.getUpdatePersonId());
		tranLookAfterwards.setLookAftFlag(lookAfterwardsInfo.getLookAftFlag());
		tranLookAfterwards.setComment(lookAfterwardsInfo.getComment());
		tranLookAfterwards.setUpdateDate(new Date());
	}

	@Override
	public Map<String, Object> fetchCommentsByAnswerSeq(Integer answerSeq,
			String connectionString) {
		Map<String, Object> commentMap = new LinkedHashMap<String, Object>();
		try {
			List<String> commentList = tranLookAfterwardsDAO
					.fetchCommentsByAnswerSeq(answerSeq, connectionString);
			StringBuilder comments = null;
			if (!commentList.isEmpty()) {
				commentMap.put("lookAfterwardsCount", commentList.size());
				for (String comment : commentList) {
					if (comment != null && comments == null) {
						comments = new StringBuilder();
						comments.append(comment);
					} else if (comment != null && comments != null) {
						comments.append("\n\n");
						comments.append(comment);
					}
				}
				if (comments != null) {
					commentMap.put("comments", comments.toString());
				}
			}
			return commentMap;
		} catch (HibernateException he) {
			throw new SaitenRuntimeException(
					ErrorCode.LOOK_AFTERWARDS_HIBERNATE_EXCEPTION, he);
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.LOOK_AFTERWARDS_SERVICE_EXCEPTION, e);
		}
	}

	@Override
	public int unamrkAll(LookAfterwardsInfo lookAfterwardsInfo,
			String connectionString) {
		try {
			return tranLookAfterwardsDAO.unmarkAll(lookAfterwardsInfo,
					connectionString);
		} catch (HibernateException he) {
			throw new SaitenRuntimeException(
					ErrorCode.LOOK_AFTERWARDS_HIBERNATE_EXCEPTION, he);
		} catch (Exception e) {
			throw new SaitenRuntimeException(
					ErrorCode.LOOK_AFTERWARDS_SERVICE_EXCEPTION, e);
		}

	}

}
