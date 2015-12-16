package com.saiten.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

public class SaitenFileUtil {
	private static Logger log = Logger.getLogger(SaitenFileUtil.class);

	public static void createZipFileFromDirectory(String sourceDirectory,
			String zipFile) throws IOException {
		try {
			FileOutputStream fout = new FileOutputStream(zipFile);

			ZipOutputStream zout = new ZipOutputStream(fout);

			File fileSource = new File(sourceDirectory);

			addDirectory("", zout, fileSource);

			zout.close();

		} catch (FileNotFoundException e) {
			log.error("Error while creating zip file from directory");
			throw e;
		} catch (IOException e) {
			log.error("Error while creating zip file from directory");
			throw e;
		}
	}

	private static void addDirectory(String basePath, ZipOutputStream zout,
			File fileSource) throws IOException {

		File[] files = fileSource.listFiles();

		for (int i = 0; i < files.length; i++) {
			try {
				if (files[i].isDirectory()) {
					String path = basePath + files[i].getName()
							+ File.separator;
					zout.putNextEntry(new ZipEntry(path));
					addDirectory(path, zout, files[i]);
					continue;
				}

				byte[] buffer = new byte[1024];

				FileInputStream fin = new FileInputStream(files[i]);

				zout.putNextEntry(new ZipEntry(basePath + files[i].getName()));

				int length;

				while ((length = fin.read(buffer)) > 0) {
					zout.write(buffer, 0, length);
				}

				zout.closeEntry();

				fin.close();

			} catch (IOException e) {
				log.error("Error while adding directory to zip file");
				throw e;
			}
		}
	}

	public static File createDirectory(String basepath, String directoryName) {
		File dir = new File(basepath + File.separator + directoryName);
		if (!dir.exists())
			dir.mkdirs();
		return dir;
	}

	public static void deleteDirectory(File file) throws Exception {
		try {
			FileUtils.deleteDirectory(file);
		} catch (Exception e) {
			log.error("Error In  deleteDirectory" + e);
			throw e;
		}
	}

	public static boolean isEmptyDirectory(File file) {
		if (file.list().length > 0) {
			return false;
		} else {
			return true;
		}
	}
}
