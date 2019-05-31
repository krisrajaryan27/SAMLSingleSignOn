/**
 * 
 */
package com.talentPool.documents.dataobject;

import java.sql.Date;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.documents.utils.DocumentUtils;

/**
 * @author shivprasad
 * 
 */
public class DocumentData extends SimpleDataObject {
	public void setDocumentId(String documentId) {
		setAttribute("documentId", documentId);
	}

	public String getDocumentId() {
		return getString("documentId");
	}

	public void setApplicantId(String applicantId) {
		setAttribute("applicantId", applicantId);
	}
	
	public String getApplicantId() {
		return getString("applicantId");
	}

	public String getPositionId() {
		return getString("positionId");
	}
	
	public void setPositionId(String positionId) {
		setAttribute("positionId", positionId);
	}

	public void setUserId(String userId) {
		setAttribute("userId", userId);
	}

	public String getUserId() {
		return getString("userId");
	}

	public void setDateCreated(Date dateCreated) {
		setAttribute("dateCreated", dateCreated);
	}

	public Date getDateCreated() {
		try {
			return getDate("dateCreated");
		} catch (ClassCastException cce) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,cce);
		}
		return null;
	}

	public void setOriginalFileName(String originalFileName) {
		setAttribute("originalFileName", originalFileName);
	}

	public String getOriginalFileName() {
		return getString("originalFileName");
	}

	public void setChangedFileName(String changedFileName) {
		setAttribute("changedFileName", changedFileName);
	}

	public String getChangedFileName() {
		return getString("changedFileName");
	}
	
	public void setOwnerName(String ownerName) {
		setAttribute("ownerName", ownerName);
	}

	public String getOwnerName() {
		return getString("ownerName");
	}

	public void setRelativeFilePath(String relativeFilePath) {
		setAttribute("relativeFilePath", relativeFilePath);
	}

	public String getRelativeFilePath() {
		return getString("relativeFilePath");
	}

	public void setAbsoluteFilePath(String absoluteFilePath) {
		setAttribute("absoluteFilePath", absoluteFilePath);
	}

	public String getAbsoluteFilePath() {
		return getString("absoluteFilePath");
	}

	public void setDocumentIsHidden(String documentIsHidden) {
		setAttribute("documentIsHidden", documentIsHidden);
	}

	public String getDocumentIsHidden() {
		return getString("documentIsHidden");
	}

	public String getImageName() {
		return DocumentUtils.getFileImageName(getOriginalFileName());
	}

}
