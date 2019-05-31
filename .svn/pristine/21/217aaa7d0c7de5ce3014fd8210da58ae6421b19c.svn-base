package com.talentPool.inbox.dataobject;

import java.math.BigDecimal;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.SimpleDataObject;

/**
 * @author shivprasad
 * 
 */
public class AttachmentData extends SimpleDataObject {
	/*
	 * file location is full local path with file name D:/Talentpool/abc.txt
	 * 
	 * and file name is just for display
	 * 
	 * abc.txt
	 */

	public AttachmentData() {

	}

	/**
	 * @param fileLocation
	 * @param fileName
	 */
	public AttachmentData(String attachmentFilePath, String originalFileName) {
		setAttachmentFilePath(attachmentFilePath);
		setOriginalFileName(originalFileName);
	}

	public String getAttachmentId() {
		return getId("attachmentId");
	}

	public void setAttachmentId(String attachmentId) {
		setAttribute("attachmentId", attachmentId);
	}

	/**
	 * @return Returns the fileName - this is the name of saved attachment or name of the file saved physically on hard disk
	 */
	public String getAttachmentFilePath() {
		return getString("attachmentFilePath");
	}

	/**
	 * @param fileName
	 */
	public void setAttachmentFilePath(String attachmentFilePath) {
		setAttribute("attachmentFilePath", attachmentFilePath);
	}

	/**
	 * @return Returns the original fileName to be displayed as attachment
	 */
	public String getOriginalFileName() {
		return getString("originalFileName");
	}

	/**
	 * @param originalFileName
	 */
	public void setOriginalFileName(String originalFileName) {
		setAttribute("originalFileName", originalFileName);
	}

	/**
	 * @return Returns the contentType.
	 */
	public String getContentType() {
		return getString("contentType");
	}

	/**
	 * @param contentType
	 *            The contentType to set.
	 */
	public void setContentType(String contentType) {
		setAttribute("contentType", contentType);
	}

	/**
	 * @return Returns the contentId.
	 */
	public String getContentId() {
		return getString("contentId");
	}

	/**
	 * @param contentId
	 *            The contentId to set.
	 */
	public void setContentId(String contentId) {
		setAttribute("contentId", contentId);
	}

	public void setAttachmentType(String attachmentType) {
		setAttribute("attachmentType", attachmentType);
	}

	public String getAttachmentType() {
		return getString("attachmentType");
	}

	public void setAttachmentSize(long attachmentSize) {
		setAttribute("attachmentSize", new Long(attachmentSize));
	}

	public long getAttachmentSize() {
		return getLong("attachmentSize");
	}

	public String getLabeledSize() {
		try {
			long sz = getLong("attachmentSize");

			if (sz < 1024) {
				return "1 KB";
			}
			double kb = (double) sz / 1024;
			if (kb < 1000) {
				BigDecimal bg = new BigDecimal(kb);
				return bg.setScale(0, BigDecimal.ROUND_HALF_UP).toString() + " KB";
			}
			double mb = kb / 1000;
			BigDecimal bg = new BigDecimal(mb);
			return bg.setScale(1, BigDecimal.ROUND_HALF_EVEN).toString() + " MB";
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while converting size", e);
		}
		return "";
	}
	
	public void setImportStatus(String importStatus) {
		setAttribute("importStatus", importStatus);
	}

	public String getImportStatus() {
		return getString("importStatus");
	}
}
