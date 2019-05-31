/**
 * 
 */
package com.talentPool.desktop.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.utils.FileHandler;
import com.talentPool.common.utils.Utils;
import com.talentPool.desktop.constants.DesktopConstants;


/**
 * @author shivprasad
 * 
 */
public class DesktopUploadUtils {
	public String headerFormatExp = "(?mids)(emailfrom:.*)(emailto:.*)(emailcc:.*)(emailbcc:.*)(emailsubject:.*)(datesent:.*)(datereceived:.*)(emailsize:.*)";
	
	public void saveUploadedEmailFile(InputStream in, String fileName, String sessionId, String emailId) throws Exception {

		// check if session folder exists else create new
		FileOutputStream out = null;
		try {
			String sessionFolderPath = Utils.concatFilePath(DesktopConstants.uploadsPath, sessionId);
			String emailFolderPath = Utils.concatFilePath(sessionFolderPath, emailId);
			FileHandler fileHandler = new FileHandler();
			fileHandler.createDirectory(sessionFolderPath);
			fileHandler.createDirectory(emailFolderPath);
			BufferedInputStream bufIn = new BufferedInputStream(in);
			String filePath = Utils.concatFilePath(emailFolderPath, fileName);
			out = new FileOutputStream(new File(filePath));
			for (;;) {
				int data = bufIn.read();
				// Check for EOF
				if (data == -1)
					break;
				else
					out.write(data);
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			out.close();
		}

	}
	
	public ArrayList<String> getHeaderGroups(String content, String exp) {
		ArrayList<String> groups = new ArrayList<String>();
		try {
			Pattern p = Pattern.compile(exp);
			Matcher m = p.matcher(content);
			int groupCount = m.groupCount();
			if (groupCount > 1) {
				m.find();
				for (int i = 1; i <= groupCount; i++) {
					String grp = m.group(i);
					grp = grp.substring(grp.indexOf(":") + 1).trim();
					groups.add(grp);
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
		}
		return groups;
	}

	
}
