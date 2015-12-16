package com.saiten.model;

// Generated Dec 3, 2012 11:19:29 AM by Hibernate Tools 3.2.1.GA

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * MstScoringStateList generated by hbm2java
 */
public class MstScoringStateList implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private short scoringState;
	private String stateName;
	private byte stateType;
	private Character deleteFlag;
	private String updatePersonId;
	private Date updateDate;
	private Date createDate;
	private Set<MstSamplingStateCond> mstSamplingStateConds = new HashSet<MstSamplingStateCond>(
			0);

	public MstScoringStateList() {
	}

	public MstScoringStateList(short scoringState) {
		this.scoringState = scoringState;
	}

	public MstScoringStateList(short scoringState, String stateName, byte stateType,
			Character deleteFlag, String updatePersonId, Date updateDate,
			Date createDate, Set<MstSamplingStateCond> mstSamplingStateConds) {
		this.scoringState = scoringState;
		this.stateName = stateName;
		this.stateType = stateType;
		this.deleteFlag = deleteFlag;
		this.updatePersonId = updatePersonId;
		this.updateDate = updateDate;
		this.createDate = createDate;
		this.mstSamplingStateConds = mstSamplingStateConds;
	}

	public short getScoringState() {
		return this.scoringState;
	}

	public void setScoringState(short scoringState) {
		this.scoringState = scoringState;
	}

	public String getStateName() {
		return this.stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public Character getDeleteFlag() {
		return this.deleteFlag;
	}

	public void setDeleteFlag(Character deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getUpdatePersonId() {
		return this.updatePersonId;
	}

	public void setUpdatePersonId(String updatePersonId) {
		this.updatePersonId = updatePersonId;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Set<MstSamplingStateCond> getMstSamplingStateConds() {
		return this.mstSamplingStateConds;
	}

	public void setMstSamplingStateConds(
			Set<MstSamplingStateCond> mstSamplingStateConds) {
		this.mstSamplingStateConds = mstSamplingStateConds;
	}
	
	public byte getStateType() {
		return stateType;
	}

	public void setStateType(byte stateType) {
		this.stateType = stateType;
	}

}
