package com.saiten.info;

/**
 * @author sachin
 * @version 1.0
 * @created 13-Dec-2012 11:53:02 AM
 */
public class PendingCategoryInfo {
	private int pendingCategorySeq;
	private Short pendingCategory;
	private String pendingDescription;

	public int getPendingCategorySeq() {
		return pendingCategorySeq;
	}

	/**
	 * 
	 * @param pendingCategorySeq
	 */
	public void setPendingCategorySeq(int pendingCategorySeq) {
		this.pendingCategorySeq = pendingCategorySeq;
	}

	public Short getPendingCategory() {
		return pendingCategory;
	}

	/**
	 * 
	 * @param pendingCategory
	 */
	public void setPendingCategory(Short pendingCategory) {
		this.pendingCategory = pendingCategory;
	}

	public String getPendingDescription() {
		return pendingDescription;
	}

	/**
	 * 
	 * @param pendingDescription
	 */
	public void setPendingDescription(String pendingDescription) {
		this.pendingDescription = pendingDescription;
	}

}
