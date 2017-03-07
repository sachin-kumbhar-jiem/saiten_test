package com.saiten.service;

import java.io.File;

/**
 * @author sachin
 * @version 1.0
 * @created 01-Sept-2016 10:33:35 AM
 */
public interface UploadManualService {
	public void uploadManual(int questionSequence, File manual, String manualFileName, String manualType,
			String scorerId);
}