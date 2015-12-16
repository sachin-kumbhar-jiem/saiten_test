package com.saiten.info;

/**
 * @author sachin
 * @version 1.0
 * @created 11-Dec-2012 12:40:12 PM
 */
public class CheckPointInfo {
	private Short checkPoint;
	private int checkPointGroupSeq;
	private Byte groupType;
	private String checkPointDescription;
	private Integer groupId;
	private String bgColor;

	public Short getCheckPoint() {
		return checkPoint;
	}

	/**
	 * 
	 * @param checkPoint
	 */
	public void setCheckPoint(Short checkPoint) {
		this.checkPoint = checkPoint;
	}

	public int getCheckPointGroupSeq() {
		return checkPointGroupSeq;
	}

	/**
	 * 
	 * @param checkPointGroupSeq
	 */
	public void setCheckPointGroupSeq(int checkPointGroupSeq) {
		this.checkPointGroupSeq = checkPointGroupSeq;
	}

	public Byte getGroupType() {
		return groupType;
	}

	/**
	 * 
	 * @param groupType
	 */
	public void setGroupType(Byte groupType) {
		this.groupType = groupType;
	}

	public String getCheckPointDescription() {
		return checkPointDescription;
	}

	/**
	 * 
	 * @param checkPointDescription
	 */
	public void setCheckPointDescription(String checkPointDescription) {
		this.checkPointDescription = checkPointDescription;
	}

	@Override
	public String toString() {
		return checkPoint + ":" + groupType;
	}

	/**
	 * @return the groupId
	 */
	public Integer getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId
	 *            the groupId to set
	 */
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return the bgColor
	 */
	public String getBgColor() {
		return bgColor;
	}

	/**
	 * @param bgColor
	 *            the bgColor to set
	 */
	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}

}
