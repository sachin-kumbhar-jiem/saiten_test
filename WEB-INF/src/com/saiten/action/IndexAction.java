/**
 * 
 */
package com.saiten.action;

import java.util.Properties;

import com.opensymphony.xwork2.ActionSupport;
import com.saiten.util.WebAppConst;

/**
 * @author user
 * 
 */
public class IndexAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String redirectURL;

	private Properties saitenApplicationProperties;

	@Override
	public String execute() throws Exception {
		String isSaitenLoginEnabled = saitenApplicationProperties
				.getProperty(WebAppConst.IS_SAITEN_LOGIN_FUNCTINALITY_ENABLED);
		if (isSaitenLoginEnabled.equals("true")) {
			redirectURL = "indexLogin";
		} else {
			redirectURL = "indexError";
		}
		return SUCCESS;
	}

	public String getRedirectURL() {
		return redirectURL;
	}

	public void setRedirectURL(String redirectURL) {
		this.redirectURL = redirectURL;
	}

	public void setSaitenApplicationProperties(
			Properties saitenApplicationProperties) {
		this.saitenApplicationProperties = saitenApplicationProperties;
	}

}
