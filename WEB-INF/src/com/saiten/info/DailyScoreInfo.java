package com.saiten.info;

/**
 * @author Rajesh
 * 
 */
public class DailyScoreInfo {

	private Integer firstRtgSecondWaitSpkCnt;
	private Integer firstRtgSecondWaitWrtCnt;
	private Integer firstRtgPendingSpkCnt;
	private Integer firstRtgPendingWrtCnt;
	private Integer secondRtgCompleteSpkCnt;
	private Integer secondRtgCompleteWrtCnt;
	private Integer secondRtgPendingSpkCnt;
	private Integer secondRtgPendingWrtCnt;
	private Integer secondRtgMismatchSpkCnt;
	private Integer secondRtgMismatchWrtCnt;
	private Integer mismatchRtgPendingSpkCnt;
	private Integer mismatchRtgPendingWrtCnt;
	private Integer mismatchRtgCompleteSpkCnt;
	private Integer mismatchRtgCompleteWrtCnt;
	private Integer pending01SecondWaitSpkCnt;
	private Integer pending01SecondWaitWrtCnt;
	private Integer pending01PendingSpkCnt;
	private Integer pending01PendingWrtCnt;
	private Integer pending01CompleteSpkCnt;
	private Integer pending01CompleteWrtCnt;
	private Integer pending01MismatchSpkCnt;
	private Integer pending01MismatchWrtCnt;
	private Integer pending02SecondWaitSpkCnt;
	private Integer pending02SecondWaitWrtCnt;
	private Integer pending02PendingSpkCnt;
	private Integer pending02PendingWrtCnt;
	private Integer pending02CompleteSpkCnt;
	private Integer pending02CompleteWrtCnt;
	private Integer pending02MismatchSpkCnt;
	private Integer pending02MismatchWrtCnt;
	private Integer pending03SecondWaitSpkCnt;
	private Integer pending03SecondWaitWrtCnt;
	private Integer pending03PendingSpkCnt;
	private Integer pending03PendingWrtCnt;
	private Integer pending03CompleteSpkCnt;
	private Integer pending03CompleteWrtCnt;
	private Integer pending03MismatchSpkCnt;
	private Integer pending03MismatchWrtCnt;
	private Integer pending04SecondWaitSpkCnt;
	private Integer pending04SecondWaitWrtCnt;
	private Integer pending04PendingSpkCnt;
	private Integer pending04PendingWrtCnt;
	private Integer pending04CompleteSpkCnt;
	private Integer pending04CompleteWrtCnt;
	private Integer pending04MismatchSpkCnt;
	private Integer pending04MismatchWrtCnt;
	private Integer pending05SecondWaitSpkCnt;
	private Integer pending05SecondWaitWrtCnt;
	private Integer pending05PendingSpkCnt;
	private Integer pending05PendingWrtCnt;
	private Integer pending05CompleteSpkCnt;
	private Integer pending05CompleteWrtCnt;
	private Integer pending05MismatchSpkCnt;
	private Integer pending05MismatchWrtCnt;

	public void bindResult(DailyScoreInfo dailyScoreInfo) {
		setFirstRtgSecondWaitSpkCnt(firstRtgSecondWaitSpkCnt
				+ dailyScoreInfo.getFirstRtgSecondWaitSpkCnt());
		setFirstRtgSecondWaitWrtCnt(firstRtgSecondWaitWrtCnt
				+ dailyScoreInfo.getFirstRtgSecondWaitWrtCnt());
		setFirstRtgPendingSpkCnt(firstRtgPendingSpkCnt
				+ dailyScoreInfo.getFirstRtgPendingSpkCnt());
		setFirstRtgPendingWrtCnt(firstRtgPendingWrtCnt
				+ dailyScoreInfo.getFirstRtgPendingWrtCnt());
		setSecondRtgCompleteSpkCnt(secondRtgCompleteSpkCnt
				+ dailyScoreInfo.getSecondRtgCompleteSpkCnt());
		setSecondRtgCompleteWrtCnt(secondRtgCompleteWrtCnt
				+ dailyScoreInfo.getSecondRtgCompleteWrtCnt());
		setSecondRtgPendingSpkCnt(secondRtgPendingSpkCnt
				+ dailyScoreInfo.getSecondRtgPendingSpkCnt());
		setSecondRtgPendingWrtCnt(secondRtgPendingWrtCnt
				+ dailyScoreInfo.getSecondRtgPendingWrtCnt());
		setSecondRtgMismatchSpkCnt(secondRtgMismatchSpkCnt
				+ dailyScoreInfo.getSecondRtgMismatchSpkCnt());
		setSecondRtgMismatchWrtCnt(secondRtgMismatchWrtCnt
				+ dailyScoreInfo.getSecondRtgMismatchWrtCnt());
		setMismatchRtgPendingSpkCnt(mismatchRtgPendingSpkCnt
				+ dailyScoreInfo.getMismatchRtgPendingSpkCnt());
		setMismatchRtgPendingWrtCnt(mismatchRtgPendingWrtCnt
				+ dailyScoreInfo.getMismatchRtgPendingWrtCnt());
		setMismatchRtgCompleteSpkCnt(mismatchRtgCompleteSpkCnt
				+ dailyScoreInfo.getMismatchRtgCompleteSpkCnt());
		setMismatchRtgCompleteWrtCnt(mismatchRtgCompleteWrtCnt
				+ dailyScoreInfo.getMismatchRtgCompleteWrtCnt());
		setPending01SecondWaitSpkCnt(pending01SecondWaitSpkCnt
				+ dailyScoreInfo.getPending01SecondWaitSpkCnt());
		setPending01SecondWaitWrtCnt(pending01SecondWaitWrtCnt
				+ dailyScoreInfo.getPending01SecondWaitWrtCnt());
		setPending01PendingSpkCnt(pending01PendingSpkCnt
				+ dailyScoreInfo.getPending01PendingSpkCnt());
		setPending01PendingWrtCnt(pending01PendingWrtCnt
				+ dailyScoreInfo.getPending01PendingWrtCnt());
		setPending01CompleteSpkCnt(pending01CompleteSpkCnt
				+ dailyScoreInfo.getPending01CompleteSpkCnt());
		setPending01CompleteWrtCnt(pending01CompleteWrtCnt
				+ dailyScoreInfo.getPending01CompleteWrtCnt());
		setPending01MismatchSpkCnt(pending01MismatchSpkCnt
				+ dailyScoreInfo.getPending01MismatchSpkCnt());
		setPending01MismatchWrtCnt(pending01MismatchWrtCnt
				+ dailyScoreInfo.getPending01MismatchWrtCnt());
		setPending02SecondWaitSpkCnt(pending02SecondWaitSpkCnt
				+ dailyScoreInfo.getPending02SecondWaitSpkCnt());
		setPending02SecondWaitWrtCnt(pending02SecondWaitWrtCnt
				+ dailyScoreInfo.getPending02SecondWaitWrtCnt());
		setPending02PendingSpkCnt(pending02PendingSpkCnt
				+ dailyScoreInfo.getPending02PendingSpkCnt());
		setPending02PendingWrtCnt(pending02PendingWrtCnt
				+ dailyScoreInfo.getPending02PendingWrtCnt());
		setPending02CompleteSpkCnt(pending02CompleteSpkCnt
				+ dailyScoreInfo.getPending02CompleteSpkCnt());
		setPending02CompleteWrtCnt(pending02CompleteWrtCnt
				+ dailyScoreInfo.getPending02CompleteWrtCnt());
		setPending02MismatchSpkCnt(pending02MismatchSpkCnt
				+ dailyScoreInfo.getPending02MismatchSpkCnt());
		setPending02MismatchWrtCnt(pending02MismatchWrtCnt
				+ dailyScoreInfo.getPending02MismatchWrtCnt());
		setPending03SecondWaitSpkCnt(pending03SecondWaitSpkCnt
				+ dailyScoreInfo.getPending03SecondWaitSpkCnt());
		setPending03SecondWaitWrtCnt(pending03SecondWaitWrtCnt
				+ dailyScoreInfo.getPending03SecondWaitWrtCnt());
		setPending03PendingSpkCnt(pending03PendingSpkCnt
				+ dailyScoreInfo.getPending03PendingSpkCnt());
		setPending03PendingWrtCnt(pending03PendingWrtCnt
				+ dailyScoreInfo.getPending03PendingWrtCnt());
		setPending03CompleteSpkCnt(pending03CompleteSpkCnt
				+ dailyScoreInfo.getPending03CompleteSpkCnt());
		setPending03CompleteWrtCnt(pending03CompleteWrtCnt
				+ dailyScoreInfo.getPending03CompleteWrtCnt());
		setPending03MismatchSpkCnt(pending03MismatchSpkCnt
				+ dailyScoreInfo.getPending03MismatchSpkCnt());
		setPending03MismatchWrtCnt(pending03MismatchWrtCnt
				+ dailyScoreInfo.getPending03MismatchWrtCnt());
		setPending04SecondWaitSpkCnt(pending04SecondWaitSpkCnt
				+ dailyScoreInfo.getPending04SecondWaitSpkCnt());
		setPending04SecondWaitWrtCnt(pending04SecondWaitWrtCnt
				+ dailyScoreInfo.getPending04SecondWaitWrtCnt());
		setPending04PendingSpkCnt(pending04PendingSpkCnt
				+ dailyScoreInfo.getPending04PendingSpkCnt());
		setPending04PendingWrtCnt(pending04PendingWrtCnt
				+ dailyScoreInfo.getPending04PendingWrtCnt());
		setPending04CompleteSpkCnt(pending04CompleteSpkCnt
				+ dailyScoreInfo.getPending04CompleteSpkCnt());
		setPending04CompleteWrtCnt(pending04CompleteWrtCnt
				+ dailyScoreInfo.getPending04CompleteWrtCnt());
		setPending04MismatchSpkCnt(pending04MismatchSpkCnt
				+ dailyScoreInfo.getPending04MismatchSpkCnt());
		setPending04MismatchWrtCnt(pending04MismatchWrtCnt
				+ dailyScoreInfo.getPending04MismatchWrtCnt());
		setPending05SecondWaitSpkCnt(pending05SecondWaitSpkCnt
				+ dailyScoreInfo.getPending05SecondWaitSpkCnt());
		setPending05SecondWaitWrtCnt(pending05SecondWaitWrtCnt
				+ dailyScoreInfo.getPending05SecondWaitWrtCnt());
		setPending05PendingSpkCnt(pending05PendingSpkCnt
				+ dailyScoreInfo.getPending05PendingSpkCnt());
		setPending05PendingWrtCnt(pending05PendingWrtCnt
				+ dailyScoreInfo.getPending05PendingWrtCnt());
		setPending05CompleteSpkCnt(pending05CompleteSpkCnt
				+ dailyScoreInfo.getPending05CompleteSpkCnt());
		setPending05CompleteWrtCnt(pending05CompleteWrtCnt
				+ dailyScoreInfo.getPending05CompleteWrtCnt());
		setPending05MismatchSpkCnt(pending05MismatchSpkCnt
				+ dailyScoreInfo.getPending05MismatchSpkCnt());
		setPending05MismatchWrtCnt(pending05MismatchWrtCnt
				+ dailyScoreInfo.getPending05MismatchWrtCnt());
	}

	public void add(DailyScoreInfo dailyScoreInfo) {
		firstRtgSecondWaitSpkCnt = firstRtgSecondWaitSpkCnt
				+ dailyScoreInfo.getFirstRtgSecondWaitSpkCnt();
		firstRtgSecondWaitWrtCnt = firstRtgSecondWaitWrtCnt
				+ dailyScoreInfo.getFirstRtgSecondWaitWrtCnt();
		firstRtgPendingSpkCnt = firstRtgPendingSpkCnt
				+ dailyScoreInfo.getFirstRtgPendingSpkCnt();
		firstRtgPendingWrtCnt = firstRtgPendingWrtCnt
				+ dailyScoreInfo.getFirstRtgPendingWrtCnt();
		secondRtgCompleteSpkCnt = secondRtgCompleteSpkCnt
				+ dailyScoreInfo.getSecondRtgCompleteSpkCnt();
		secondRtgCompleteWrtCnt = secondRtgCompleteWrtCnt
				+ dailyScoreInfo.getSecondRtgCompleteWrtCnt();
		secondRtgPendingSpkCnt = secondRtgPendingSpkCnt
				+ dailyScoreInfo.getSecondRtgPendingSpkCnt();
		secondRtgPendingWrtCnt = secondRtgPendingWrtCnt
				+ dailyScoreInfo.getSecondRtgPendingWrtCnt();
		secondRtgMismatchSpkCnt = secondRtgMismatchSpkCnt
				+ dailyScoreInfo.getSecondRtgMismatchSpkCnt();
		secondRtgMismatchWrtCnt = secondRtgMismatchWrtCnt
				+ dailyScoreInfo.getSecondRtgMismatchWrtCnt();
		mismatchRtgPendingSpkCnt = mismatchRtgPendingSpkCnt
				+ dailyScoreInfo.getMismatchRtgPendingSpkCnt();
		mismatchRtgPendingWrtCnt = mismatchRtgPendingWrtCnt
				+ dailyScoreInfo.getMismatchRtgPendingWrtCnt();
		mismatchRtgCompleteSpkCnt = mismatchRtgCompleteSpkCnt
				+ dailyScoreInfo.getMismatchRtgCompleteSpkCnt();
		mismatchRtgCompleteWrtCnt = mismatchRtgCompleteWrtCnt
				+ dailyScoreInfo.getMismatchRtgCompleteWrtCnt();
		pending01SecondWaitSpkCnt = pending01SecondWaitSpkCnt
				+ dailyScoreInfo.getPending01SecondWaitSpkCnt();
		pending01SecondWaitWrtCnt = pending01SecondWaitWrtCnt
				+ dailyScoreInfo.getPending01SecondWaitWrtCnt();
		pending01PendingSpkCnt = pending01PendingSpkCnt
				+ dailyScoreInfo.getPending01PendingSpkCnt();
		pending01PendingWrtCnt = pending01PendingWrtCnt
				+ dailyScoreInfo.getPending01PendingWrtCnt();
		pending01CompleteSpkCnt = pending01CompleteSpkCnt
				+ dailyScoreInfo.getPending01CompleteSpkCnt();
		pending01CompleteWrtCnt = pending01CompleteWrtCnt
				+ dailyScoreInfo.getPending01CompleteWrtCnt();
		pending01MismatchSpkCnt = pending01MismatchSpkCnt
				+ dailyScoreInfo.getPending01MismatchSpkCnt();
		pending01MismatchWrtCnt = pending01MismatchWrtCnt
				+ dailyScoreInfo.getPending01MismatchWrtCnt();
		pending02SecondWaitSpkCnt = pending02SecondWaitSpkCnt
				+ dailyScoreInfo.getPending02SecondWaitSpkCnt();
		pending02SecondWaitWrtCnt = pending02SecondWaitWrtCnt
				+ dailyScoreInfo.getPending02SecondWaitWrtCnt();
		pending02PendingSpkCnt = pending02PendingSpkCnt
				+ dailyScoreInfo.getPending02PendingSpkCnt();
		pending02PendingWrtCnt = pending02PendingWrtCnt
				+ dailyScoreInfo.getPending02PendingWrtCnt();
		pending02CompleteSpkCnt = pending02CompleteSpkCnt
				+ dailyScoreInfo.getPending02CompleteSpkCnt();
		pending02CompleteWrtCnt = pending02CompleteWrtCnt
				+ dailyScoreInfo.getPending02CompleteWrtCnt();
		pending02MismatchSpkCnt = pending02MismatchSpkCnt
				+ dailyScoreInfo.getPending02MismatchSpkCnt();
		pending02MismatchWrtCnt = pending02MismatchWrtCnt
				+ dailyScoreInfo.getPending02MismatchWrtCnt();
		pending03SecondWaitSpkCnt = pending03SecondWaitSpkCnt
				+ dailyScoreInfo.getPending03SecondWaitSpkCnt();
		pending03SecondWaitWrtCnt = pending03SecondWaitWrtCnt
				+ dailyScoreInfo.getPending03SecondWaitWrtCnt();
		pending03PendingSpkCnt = pending03PendingSpkCnt
				+ dailyScoreInfo.getPending03PendingSpkCnt();
		pending03PendingWrtCnt = pending03PendingWrtCnt
				+ dailyScoreInfo.getPending03PendingWrtCnt();
		pending03CompleteSpkCnt = pending03CompleteSpkCnt
				+ dailyScoreInfo.getPending03CompleteSpkCnt();
		pending03CompleteWrtCnt = pending03CompleteWrtCnt
				+ dailyScoreInfo.getPending03CompleteWrtCnt();
		pending03MismatchSpkCnt = pending03MismatchSpkCnt
				+ dailyScoreInfo.getPending03MismatchSpkCnt();
		pending03MismatchWrtCnt = pending03MismatchWrtCnt
				+ dailyScoreInfo.getPending03MismatchWrtCnt();
		pending04SecondWaitSpkCnt = pending04SecondWaitSpkCnt
				+ dailyScoreInfo.getPending04SecondWaitSpkCnt();
		pending04SecondWaitWrtCnt = pending04SecondWaitWrtCnt
				+ dailyScoreInfo.getPending04SecondWaitWrtCnt();
		pending04PendingSpkCnt = pending04PendingSpkCnt
				+ dailyScoreInfo.getPending04PendingSpkCnt();
		pending04PendingWrtCnt = pending04PendingWrtCnt
				+ dailyScoreInfo.getPending04PendingWrtCnt();
		pending04CompleteSpkCnt = pending04CompleteSpkCnt
				+ dailyScoreInfo.getPending04CompleteSpkCnt();
		pending04CompleteWrtCnt = pending04CompleteWrtCnt
				+ dailyScoreInfo.getPending04CompleteWrtCnt();
		pending04MismatchSpkCnt = pending04MismatchSpkCnt
				+ dailyScoreInfo.getPending04MismatchSpkCnt();
		pending04MismatchWrtCnt = pending04MismatchWrtCnt
				+ dailyScoreInfo.getPending04MismatchWrtCnt();
		pending05SecondWaitSpkCnt = pending05SecondWaitSpkCnt
				+ dailyScoreInfo.getPending05SecondWaitSpkCnt();
		pending05SecondWaitWrtCnt = pending05SecondWaitWrtCnt
				+ dailyScoreInfo.getPending05SecondWaitWrtCnt();
		pending05PendingSpkCnt = pending05PendingSpkCnt
				+ dailyScoreInfo.getPending05PendingSpkCnt();
		pending05PendingWrtCnt = pending05PendingWrtCnt
				+ dailyScoreInfo.getPending05PendingWrtCnt();
		pending05CompleteSpkCnt = pending05CompleteSpkCnt
				+ dailyScoreInfo.getPending05CompleteSpkCnt();
		pending05CompleteWrtCnt = pending05CompleteWrtCnt
				+ dailyScoreInfo.getPending05CompleteWrtCnt();
		pending05MismatchSpkCnt = pending05MismatchSpkCnt
				+ dailyScoreInfo.getPending05MismatchSpkCnt();
		pending05MismatchWrtCnt = pending05MismatchWrtCnt
				+ dailyScoreInfo.getPending05MismatchWrtCnt();
	}

	public Integer getFirstRtgSecondWaitSpkCnt() {
		return firstRtgSecondWaitSpkCnt;
	}

	public void setFirstRtgSecondWaitSpkCnt(Integer firstRtgSecondWaitSpkCnt) {
		this.firstRtgSecondWaitSpkCnt = firstRtgSecondWaitSpkCnt;
	}

	public Integer getFirstRtgSecondWaitWrtCnt() {
		return firstRtgSecondWaitWrtCnt;
	}

	public void setFirstRtgSecondWaitWrtCnt(Integer firstRtgSecondWaitWrtCnt) {
		this.firstRtgSecondWaitWrtCnt = firstRtgSecondWaitWrtCnt;
	}

	public Integer getFirstRtgPendingSpkCnt() {
		return firstRtgPendingSpkCnt;
	}

	public void setFirstRtgPendingSpkCnt(Integer firstRtgPendingSpkCnt) {
		this.firstRtgPendingSpkCnt = firstRtgPendingSpkCnt;
	}

	public Integer getFirstRtgPendingWrtCnt() {
		return firstRtgPendingWrtCnt;
	}

	public void setFirstRtgPendingWrtCnt(Integer firstRtgPendingWrtCnt) {
		this.firstRtgPendingWrtCnt = firstRtgPendingWrtCnt;
	}

	public Integer getSecondRtgCompleteSpkCnt() {
		return secondRtgCompleteSpkCnt;
	}

	public void setSecondRtgCompleteSpkCnt(Integer secondRtgCompleteSpkCnt) {
		this.secondRtgCompleteSpkCnt = secondRtgCompleteSpkCnt;
	}

	public Integer getSecondRtgCompleteWrtCnt() {
		return secondRtgCompleteWrtCnt;
	}

	public void setSecondRtgCompleteWrtCnt(Integer secondRtgCompleteWrtCnt) {
		this.secondRtgCompleteWrtCnt = secondRtgCompleteWrtCnt;
	}

	public Integer getSecondRtgPendingSpkCnt() {
		return secondRtgPendingSpkCnt;
	}

	public void setSecondRtgPendingSpkCnt(Integer secondRtgPendingSpkCnt) {
		this.secondRtgPendingSpkCnt = secondRtgPendingSpkCnt;
	}

	public Integer getSecondRtgPendingWrtCnt() {
		return secondRtgPendingWrtCnt;
	}

	public void setSecondRtgPendingWrtCnt(Integer secondRtgPendingWrtCnt) {
		this.secondRtgPendingWrtCnt = secondRtgPendingWrtCnt;
	}

	public Integer getSecondRtgMismatchSpkCnt() {
		return secondRtgMismatchSpkCnt;
	}

	public void setSecondRtgMismatchSpkCnt(Integer secondRtgMismatchSpkCnt) {
		this.secondRtgMismatchSpkCnt = secondRtgMismatchSpkCnt;
	}

	public Integer getSecondRtgMismatchWrtCnt() {
		return secondRtgMismatchWrtCnt;
	}

	public void setSecondRtgMismatchWrtCnt(Integer secondRtgMismatchWrtCnt) {
		this.secondRtgMismatchWrtCnt = secondRtgMismatchWrtCnt;
	}

	public Integer getMismatchRtgPendingSpkCnt() {
		return mismatchRtgPendingSpkCnt;
	}

	public void setMismatchRtgPendingSpkCnt(Integer mismatchRtgPendingSpkCnt) {
		this.mismatchRtgPendingSpkCnt = mismatchRtgPendingSpkCnt;
	}

	public Integer getMismatchRtgPendingWrtCnt() {
		return mismatchRtgPendingWrtCnt;
	}

	public void setMismatchRtgPendingWrtCnt(Integer mismatchRtgPendingWrtCnt) {
		this.mismatchRtgPendingWrtCnt = mismatchRtgPendingWrtCnt;
	}

	public Integer getMismatchRtgCompleteSpkCnt() {
		return mismatchRtgCompleteSpkCnt;
	}

	public void setMismatchRtgCompleteSpkCnt(Integer mismatchRtgCompleteSpkCnt) {
		this.mismatchRtgCompleteSpkCnt = mismatchRtgCompleteSpkCnt;
	}

	public Integer getMismatchRtgCompleteWrtCnt() {
		return mismatchRtgCompleteWrtCnt;
	}

	public void setMismatchRtgCompleteWrtCnt(Integer mismatchRtgCompleteWrtCnt) {
		this.mismatchRtgCompleteWrtCnt = mismatchRtgCompleteWrtCnt;
	}

	public Integer getPending01SecondWaitSpkCnt() {
		return pending01SecondWaitSpkCnt;
	}

	public void setPending01SecondWaitSpkCnt(Integer pending01SecondWaitSpkCnt) {
		this.pending01SecondWaitSpkCnt = pending01SecondWaitSpkCnt;
	}

	public Integer getPending01SecondWaitWrtCnt() {
		return pending01SecondWaitWrtCnt;
	}

	public void setPending01SecondWaitWrtCnt(Integer pending01SecondWaitWrtCnt) {
		this.pending01SecondWaitWrtCnt = pending01SecondWaitWrtCnt;
	}

	public Integer getPending01PendingSpkCnt() {
		return pending01PendingSpkCnt;
	}

	public void setPending01PendingSpkCnt(Integer pending01PendingSpkCnt) {
		this.pending01PendingSpkCnt = pending01PendingSpkCnt;
	}

	public Integer getPending01PendingWrtCnt() {
		return pending01PendingWrtCnt;
	}

	public void setPending01PendingWrtCnt(Integer pending01PendingWrtCnt) {
		this.pending01PendingWrtCnt = pending01PendingWrtCnt;
	}

	public Integer getPending01CompleteSpkCnt() {
		return pending01CompleteSpkCnt;
	}

	public void setPending01CompleteSpkCnt(Integer pending01CompleteSpkCnt) {
		this.pending01CompleteSpkCnt = pending01CompleteSpkCnt;
	}

	public Integer getPending01CompleteWrtCnt() {
		return pending01CompleteWrtCnt;
	}

	public void setPending01CompleteWrtCnt(Integer pending01CompleteWrtCnt) {
		this.pending01CompleteWrtCnt = pending01CompleteWrtCnt;
	}

	public Integer getPending01MismatchSpkCnt() {
		return pending01MismatchSpkCnt;
	}

	public void setPending01MismatchSpkCnt(Integer pending01MismatchSpkCnt) {
		this.pending01MismatchSpkCnt = pending01MismatchSpkCnt;
	}

	public Integer getPending01MismatchWrtCnt() {
		return pending01MismatchWrtCnt;
	}

	public void setPending01MismatchWrtCnt(Integer pending01MismatchWrtCnt) {
		this.pending01MismatchWrtCnt = pending01MismatchWrtCnt;
	}

	public Integer getPending02SecondWaitSpkCnt() {
		return pending02SecondWaitSpkCnt;
	}

	public void setPending02SecondWaitSpkCnt(Integer pending02SecondWaitSpkCnt) {
		this.pending02SecondWaitSpkCnt = pending02SecondWaitSpkCnt;
	}

	public Integer getPending02SecondWaitWrtCnt() {
		return pending02SecondWaitWrtCnt;
	}

	public void setPending02SecondWaitWrtCnt(Integer pending02SecondWaitWrtCnt) {
		this.pending02SecondWaitWrtCnt = pending02SecondWaitWrtCnt;
	}

	public Integer getPending02PendingSpkCnt() {
		return pending02PendingSpkCnt;
	}

	public void setPending02PendingSpkCnt(Integer pending02PendingSpkCnt) {
		this.pending02PendingSpkCnt = pending02PendingSpkCnt;
	}

	public Integer getPending02PendingWrtCnt() {
		return pending02PendingWrtCnt;
	}

	public void setPending02PendingWrtCnt(Integer pending02PendingWrtCnt) {
		this.pending02PendingWrtCnt = pending02PendingWrtCnt;
	}

	public Integer getPending02CompleteSpkCnt() {
		return pending02CompleteSpkCnt;
	}

	public void setPending02CompleteSpkCnt(Integer pending02CompleteSpkCnt) {
		this.pending02CompleteSpkCnt = pending02CompleteSpkCnt;
	}

	public Integer getPending02CompleteWrtCnt() {
		return pending02CompleteWrtCnt;
	}

	public void setPending02CompleteWrtCnt(Integer pending02CompleteWrtCnt) {
		this.pending02CompleteWrtCnt = pending02CompleteWrtCnt;
	}

	public Integer getPending02MismatchSpkCnt() {
		return pending02MismatchSpkCnt;
	}

	public void setPending02MismatchSpkCnt(Integer pending02MismatchSpkCnt) {
		this.pending02MismatchSpkCnt = pending02MismatchSpkCnt;
	}

	public Integer getPending02MismatchWrtCnt() {
		return pending02MismatchWrtCnt;
	}

	public void setPending02MismatchWrtCnt(Integer pending02MismatchWrtCnt) {
		this.pending02MismatchWrtCnt = pending02MismatchWrtCnt;
	}

	public Integer getPending03SecondWaitSpkCnt() {
		return pending03SecondWaitSpkCnt;
	}

	public void setPending03SecondWaitSpkCnt(Integer pending03SecondWaitSpkCnt) {
		this.pending03SecondWaitSpkCnt = pending03SecondWaitSpkCnt;
	}

	public Integer getPending03SecondWaitWrtCnt() {
		return pending03SecondWaitWrtCnt;
	}

	public void setPending03SecondWaitWrtCnt(Integer pending03SecondWaitWrtCnt) {
		this.pending03SecondWaitWrtCnt = pending03SecondWaitWrtCnt;
	}

	public Integer getPending03PendingSpkCnt() {
		return pending03PendingSpkCnt;
	}

	public void setPending03PendingSpkCnt(Integer pending03PendingSpkCnt) {
		this.pending03PendingSpkCnt = pending03PendingSpkCnt;
	}

	public Integer getPending03PendingWrtCnt() {
		return pending03PendingWrtCnt;
	}

	public void setPending03PendingWrtCnt(Integer pending03PendingWrtCnt) {
		this.pending03PendingWrtCnt = pending03PendingWrtCnt;
	}

	public Integer getPending03CompleteSpkCnt() {
		return pending03CompleteSpkCnt;
	}

	public void setPending03CompleteSpkCnt(Integer pending03CompleteSpkCnt) {
		this.pending03CompleteSpkCnt = pending03CompleteSpkCnt;
	}

	public Integer getPending03CompleteWrtCnt() {
		return pending03CompleteWrtCnt;
	}

	public void setPending03CompleteWrtCnt(Integer pending03CompleteWrtCnt) {
		this.pending03CompleteWrtCnt = pending03CompleteWrtCnt;
	}

	public Integer getPending03MismatchSpkCnt() {
		return pending03MismatchSpkCnt;
	}

	public void setPending03MismatchSpkCnt(Integer pending03MismatchSpkCnt) {
		this.pending03MismatchSpkCnt = pending03MismatchSpkCnt;
	}

	public Integer getPending03MismatchWrtCnt() {
		return pending03MismatchWrtCnt;
	}

	public void setPending03MismatchWrtCnt(Integer pending03MismatchWrtCnt) {
		this.pending03MismatchWrtCnt = pending03MismatchWrtCnt;
	}

	public Integer getPending04SecondWaitSpkCnt() {
		return pending04SecondWaitSpkCnt;
	}

	public void setPending04SecondWaitSpkCnt(Integer pending04SecondWaitSpkCnt) {
		this.pending04SecondWaitSpkCnt = pending04SecondWaitSpkCnt;
	}

	public Integer getPending04SecondWaitWrtCnt() {
		return pending04SecondWaitWrtCnt;
	}

	public void setPending04SecondWaitWrtCnt(Integer pending04SecondWaitWrtCnt) {
		this.pending04SecondWaitWrtCnt = pending04SecondWaitWrtCnt;
	}

	public Integer getPending04PendingSpkCnt() {
		return pending04PendingSpkCnt;
	}

	public void setPending04PendingSpkCnt(Integer pending04PendingSpkCnt) {
		this.pending04PendingSpkCnt = pending04PendingSpkCnt;
	}

	public Integer getPending04PendingWrtCnt() {
		return pending04PendingWrtCnt;
	}

	public void setPending04PendingWrtCnt(Integer pending04PendingWrtCnt) {
		this.pending04PendingWrtCnt = pending04PendingWrtCnt;
	}

	public Integer getPending04CompleteSpkCnt() {
		return pending04CompleteSpkCnt;
	}

	public void setPending04CompleteSpkCnt(Integer pending04CompleteSpkCnt) {
		this.pending04CompleteSpkCnt = pending04CompleteSpkCnt;
	}

	public Integer getPending04CompleteWrtCnt() {
		return pending04CompleteWrtCnt;
	}

	public void setPending04CompleteWrtCnt(Integer pending04CompleteWrtCnt) {
		this.pending04CompleteWrtCnt = pending04CompleteWrtCnt;
	}

	public Integer getPending04MismatchSpkCnt() {
		return pending04MismatchSpkCnt;
	}

	public void setPending04MismatchSpkCnt(Integer pending04MismatchSpkCnt) {
		this.pending04MismatchSpkCnt = pending04MismatchSpkCnt;
	}

	public Integer getPending04MismatchWrtCnt() {
		return pending04MismatchWrtCnt;
	}

	public void setPending04MismatchWrtCnt(Integer pending04MismatchWrtCnt) {
		this.pending04MismatchWrtCnt = pending04MismatchWrtCnt;
	}

	public Integer getPending05SecondWaitSpkCnt() {
		return pending05SecondWaitSpkCnt;
	}

	public void setPending05SecondWaitSpkCnt(Integer pending05SecondWaitSpkCnt) {
		this.pending05SecondWaitSpkCnt = pending05SecondWaitSpkCnt;
	}

	public Integer getPending05SecondWaitWrtCnt() {
		return pending05SecondWaitWrtCnt;
	}

	public void setPending05SecondWaitWrtCnt(Integer pending05SecondWaitWrtCnt) {
		this.pending05SecondWaitWrtCnt = pending05SecondWaitWrtCnt;
	}

	public Integer getPending05PendingSpkCnt() {
		return pending05PendingSpkCnt;
	}

	public void setPending05PendingSpkCnt(Integer pending05PendingSpkCnt) {
		this.pending05PendingSpkCnt = pending05PendingSpkCnt;
	}

	public Integer getPending05PendingWrtCnt() {
		return pending05PendingWrtCnt;
	}

	public void setPending05PendingWrtCnt(Integer pending05PendingWrtCnt) {
		this.pending05PendingWrtCnt = pending05PendingWrtCnt;
	}

	public Integer getPending05CompleteSpkCnt() {
		return pending05CompleteSpkCnt;
	}

	public void setPending05CompleteSpkCnt(Integer pending05CompleteSpkCnt) {
		this.pending05CompleteSpkCnt = pending05CompleteSpkCnt;
	}

	public Integer getPending05CompleteWrtCnt() {
		return pending05CompleteWrtCnt;
	}

	public void setPending05CompleteWrtCnt(Integer pending05CompleteWrtCnt) {
		this.pending05CompleteWrtCnt = pending05CompleteWrtCnt;
	}

	public Integer getPending05MismatchSpkCnt() {
		return pending05MismatchSpkCnt;
	}

	public void setPending05MismatchSpkCnt(Integer pending05MismatchSpkCnt) {
		this.pending05MismatchSpkCnt = pending05MismatchSpkCnt;
	}

	public Integer getPending05MismatchWrtCnt() {
		return pending05MismatchWrtCnt;
	}

	public void setPending05MismatchWrtCnt(Integer pending05MismatchWrtCnt) {
		this.pending05MismatchWrtCnt = pending05MismatchWrtCnt;
	}
}
