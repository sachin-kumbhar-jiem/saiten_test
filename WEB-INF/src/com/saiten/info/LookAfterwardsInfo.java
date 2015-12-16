/**
 * 
 */
package com.saiten.info;

import java.util.Date;

/**
 * @author user
 * 
 */
public class LookAfterwardsInfo {
	private Integer lookAftSeq;
	private Integer answerSeq;
	private String updatePersonId;
	private Character lookAftFlag;
	private String resolvedBy;
	private Character resolvedFlag;
	private String comment;
	private Date createDate;
	private Date updateDate;

	/**
	 * @return the lookAftSeq
	 */
	public Integer getLookAftSeq() {
		return lookAftSeq;
	}

	/**
	 * @param lookAftSeq
	 *            the lookAftSeq to set
	 */
	public void setLookAftSeq(Integer lookAftSeq) {
		this.lookAftSeq = lookAftSeq;
	}

	/**
	 * @return the answerSeq
	 */
	public Integer getAnswerSeq() {
		return answerSeq;
	}

	/**
	 * @param answerSeq
	 *            the answerSeq to set
	 */
	public void setAnswerSeq(Integer answerSeq) {
		this.answerSeq = answerSeq;
	}

	/**
	 * @return the updatePersonId
	 */
	public String getUpdatePersonId() {
		return updatePersonId;
	}

	/**
	 * @param updatePersonId
	 *            the updatePersonId to set
	 */
	public void setUpdatePersonId(String updatePersonId) {
		this.updatePersonId = updatePersonId;
	}

	/**
	 * @return the lookAftFlag
	 */
	public Character getLookAftFlag() {
		return lookAftFlag;
	}

	/**
	 * @param lookAftFlag
	 *            the lookAftFlag to set
	 */
	public void setLookAftFlag(Character lookAftFlag) {
		this.lookAftFlag = lookAftFlag;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment
	 *            the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate
	 *            the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate
	 *            the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return the resolvedBy
	 */
	public String getResolvedBy() {
		return resolvedBy;
	}

	/**
	 * @param resolvedBy the resolvedBy to set
	 */
	public void setResolvedBy(String resolvedBy) {
		this.resolvedBy = resolvedBy;
	}

	/**
	 * @return the resolvedFlag
	 */
	public Character getResolvedFlag() {
		return resolvedFlag;
	}

	/**
	 * @param resolvedFlag the resolvedFlag to set
	 */
	public void setResolvedFlag(Character resolvedFlag) {
		this.resolvedFlag = resolvedFlag;
	}
	
	
}
