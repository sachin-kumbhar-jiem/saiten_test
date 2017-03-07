package com.saiten.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.springframework.web.context.ContextLoader;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.TextProvider;
import com.saiten.dao.MstQuestionDAO;
import com.saiten.exception.SaitenRuntimeException;
import com.saiten.service.UploadManualService;
import com.saiten.util.ErrorCode;
import com.saiten.util.WebAppConst;

/**
 * @author sachin
 * @version 1.0
 * @created 01-Sept-2016 10:33:35 AM
 */
public class UploadManualServiceImpl implements UploadManualService {

	private MstQuestionDAO mstQuestionDAO;

	@Override
	public void uploadManual(int questionSequence, File manual, String manualFileName, String manualType,
			String scorerId) {
		TextProvider textProvider = (TextProvider) ActionContext.getContext().getActionInvocation().getAction();

		boolean isManual1 = false;
		if (manualType.equals(textProvider.getText(WebAppConst.MANUAL1))) {
			isManual1 = true;
		}

		try {
			int manualUpdateCount = mstQuestionDAO.updateManualInfo(questionSequence, manualFileName, isManual1,
					scorerId);

			if (manualUpdateCount > 0) {
				uploadManualToRemoteServer(manual, manualFileName, isManual1);
			} else {
				throw new SaitenRuntimeException(ErrorCode.MANUAL_UPLOAD_SERVICE_EXCEPTION);
			}
		} catch (HibernateException he) {
			throw new SaitenRuntimeException(ErrorCode.MANUAL_UPLOAD_HIBERNATE_EXCEPTION, he);
		} catch (Exception e) {
			throw new SaitenRuntimeException(ErrorCode.MANUAL_UPLOAD_SERVICE_EXCEPTION, e);
		}
	}

	public void uploadManualToRemoteServer(File manual, String manualFileName, boolean isManual1)
			throws FileNotFoundException, JSchException, SftpException {

		Properties applicationProperties = (Properties) ContextLoader.getCurrentWebApplicationContext()
				.getBean("saitenApplicationProperties");

		Session jschSession = null;
		ChannelSftp channel = null;

		try {
			JSch jsch = new JSch();
			/*
			 * jsch.addIdentity((String) applicationProperties
			 * .get(WebAppConst.SAITEN_RESOURCE_SERVER_PPK_LOCATION));
			 */

			String resourceServerUserName = (String) applicationProperties
					.get(WebAppConst.SAITEN_RESOURCE_SERVER_USERNAME);
			String resourceServerPassword = (String) applicationProperties
					.get(WebAppConst.SAITEN_RESOURCE_SERVER_PASSWORD);
			String resourceServerAddress = (String) applicationProperties.get(WebAppConst.SAITEN_RESOURCE_SERVER);
			int resourceServerPort = Integer
					.valueOf((String) applicationProperties.get(WebAppConst.SAITEN_RESOURCE_SERVER_PORT));

			jschSession = jsch.getSession(resourceServerUserName, resourceServerAddress, resourceServerPort);
			jschSession.setPassword(resourceServerPassword);
			jschSession.setConfig("StrictHostKeyChecking", "no");
			jschSession.connect();

			channel = (ChannelSftp) jschSession.openChannel("sftp");
			channel.connect();

			// Set the target location
			String destPath = null;
			if (isManual1) {
				destPath = (String) applicationProperties.get(WebAppConst.SAITEN_MANUAL1_UPLOAD_LOCATION);
			} else {
				destPath = (String) applicationProperties.get(WebAppConst.SAITEN_MANUAL2_UPLOAD_LOCATION);
			}

			channel.cd(destPath);

			// Copy the file
			channel.put(new FileInputStream(manual), manualFileName);
		} catch (FileNotFoundException e) {
			throw e;
		} catch (JSchException e) {
			throw e;
		} catch (SftpException e) {
			throw e;
		} finally {
			if (channel != null && channel.isConnected()) {
				channel.disconnect();
			}
			if (jschSession != null && jschSession.isConnected()) {
				jschSession.disconnect();
			}
		}
	}

	public void setMstQuestionDAO(MstQuestionDAO mstQuestionDAO) {
		this.mstQuestionDAO = mstQuestionDAO;
	}
}