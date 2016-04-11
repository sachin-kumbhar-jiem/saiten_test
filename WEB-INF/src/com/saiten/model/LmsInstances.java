/**
 * 
 */
package com.saiten.model;

/**
 * @author user
 * 
 */
public class LmsInstances implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String lmsUrl;

	/**
	 * 
	 */
	public LmsInstances() {

	}

	public LmsInstances(Integer id) {
		this.id = id;
	}

	public LmsInstances(Integer id, String lmsUrl) {
		this.id = id;
		this.lmsUrl = lmsUrl;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the lmsUrl
	 */
	public String getLmsUrl() {
		return lmsUrl;
	}

	/**
	 * @param lmsUrl
	 *            the lmsUrl to set
	 */
	public void setLmsUrl(String lmsUrl) {
		this.lmsUrl = lmsUrl;
	}

}
