package com.talentPool.inbox.scheduler;

import java.io.File;
import java.util.ArrayList;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.utils.FileHandler;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.utils.FileUtils.FileHandlerUtils;
import com.talentPool.documents.DocumentConstants;

/**
 * @author shivprasad
 * 
 */
public class DeleteFilesThread implements Runnable {
	ArrayList<String> files;
	private boolean deleteAssociated = false;

	public DeleteFilesThread(ArrayList<String> files, boolean deleteAssociated) {
		this.files = files;
		this.deleteAssociated = deleteAssociated;
		Thread t = new Thread(this);
		t.start();
	}

	public void run() {
		try {
			for (int i = 0; i < files.size(); i++) {
				String path = files.get(i);
				try {
					String absolutePath = Utils.concatFilePath(DocumentConstants.documentsPath, path);
					File f = new File(absolutePath);
					if (f.exists()) {
						if (!f.delete()) {
							TPLogger.getLogger().error("unable to delete file = " + absolutePath);
						}
					}
					// check if file is html and then delete the html files
					// associated with it
					if (deleteAssociated) {
						FileHandler fileHandler = new FileHandler();
						String ext = FileHandlerUtils.getFileExtention(absolutePath, "");
						if (ext.equalsIgnoreCase(".doc") || ext.equalsIgnoreCase(".rtf")) {
							String htmlFile = absolutePath.substring(0, absolutePath.lastIndexOf(".")) + ".html";
							f = new File(htmlFile);
							if (f.exists()) {
								if (!f.delete()) {
									TPLogger.getLogger().error("unable to delete file = " + absolutePath);
								}
								String htmlFolderPath = absolutePath.substring(0, absolutePath.lastIndexOf(".")) + "_files";
								
								fileHandler.deleteDirectory(htmlFolderPath);
							}
						}
					}
				} catch (Exception ex) {
					TPLogger.getLogger().error("Error", ex);
				}
			}

		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
		}
	}

}
