package com.saiten.bean;

/**
 * @author sachin
 * @version 1.0
 * @created 24-Dec-2012 12:22:57 PM
 */
public class ScoringStateKey {
	private String menuId;
	private char noDbUpdate;

	public String getMenuId() {
		return menuId;
	}

	/**
	 * 
	 * @param menuId
	 */
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public char getNoDbUpdate() {
		return noDbUpdate;
	}

	/**
	 * 
	 * @param noDbUpdate
	 */
	public void setNoDbUpdate(char noDbUpdate) {
		this.noDbUpdate = noDbUpdate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ScoringStateKey))
			return false;
		ScoringStateKey castOther = (ScoringStateKey) other;

		return (this.getMenuId().equals(castOther.getMenuId()))
				&& (this.getNoDbUpdate() == castOther.getNoDbUpdate());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getMenuId() == null ? 0 : this.getMenuId().hashCode());
		result = 37 * result + this.getNoDbUpdate();
		return result;
	}
}
