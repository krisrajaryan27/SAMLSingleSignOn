package com.talentPool.positions.utils;

import java.io.StringWriter;
import java.util.ArrayList;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.DateUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.xmlutils.XMLWriter;
import com.talentPool.positions.constants.PositionDraftConstants;
import com.talentPool.user.UserConstants;
import com.talentPool.user.dataobject.LoginData;
import com.talentPool.user.manager.LoginManager;
import com.talentPool.user.manager.PermissionSet;

public class PositionDraftsXMLGenerator {
	
	public String getXMLforPositionsDraftsHome(ArrayList positionDrafts, String userId, PermissionSet permissionSet){
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			wr.startDocument();
			wr.startElement("rows");
			if (positionDrafts != null && positionDrafts.size() > 0) {
				
				for (int i = 0; i < positionDrafts.size(); i++) {
					SimpleDataObject sDo = (SimpleDataObject) positionDrafts.get(i);
					
					AttributesImpl atr = new AttributesImpl();
					atr.addAttribute("", "id", "", "", String.valueOf(sDo.getString("draftId")));
					wr.startElement("", "row", "", atr);
					
					//blank
					wr.startElement("cell");
					wr.characters(" ") ;
					wr.endElement("cell");
				
					//delete button
					String uId = sDo.getString("userId");
					
					LoginManager loginManager = new LoginManager();
					LoginData loginData = loginManager.getUser(userId);
					
					String userRoles = loginData.getRoleId();
					
					boolean deleteAllowed = false;
					String deleteLabel = "";
					if(Integer.parseInt(userRoles) == UserConstants.ROLE_ADMIN || uId.equalsIgnoreCase(userId)){
						deleteAllowed = true;
					}
					
					wr.startElement("cell");
					if (deleteAllowed) {
						wr.characters("<a href=\"#\" onclick=\"onClickDeleteDraft(" + sDo.getString("draftId") + ");\" title=\"Delete\"><img src=\"images/ico_delete.gif\" border=0></a>");
						deleteLabel = TPLabels.getLabel("position.draft.label.Delete");
					} else {
						wr.characters(" ");
						deleteLabel = "";
					}
					wr.endElement("cell");
					
					atr = new AttributesImpl();
					atr.addAttribute("", "name", "", "", "delete");
					wr.startElement("", "userdata", "", atr);
					wr.characters(deleteLabel);
					wr.endElement("userdata");
					
					//Draft file Name
					String fileName = sDo.getString("fileName");
					
					atr = new AttributesImpl();
					atr.addAttribute("", "name", "", "", "fileName");
					wr.startElement("", "userdata", "", atr);
					wr.characters(fileName);
					wr.endElement("userdata");
					
					wr.startElement("cell");
					wr.characters("<a href=\"#\" onclick=\"javascript:onClickPositionDraft('" + sDo.getString("filePath") + "');\" title=\"Position Draft\">" +fileName+"</a>") ;
					wr.endElement("cell");
					
					
					LoginData loginData1 = loginManager.getUser(uId);
					String fullName =  loginData1.getFirstName()+" "+loginData1.getLastName();

					atr = new AttributesImpl();
					atr.addAttribute("", "name", "", "", "fullName");
					wr.startElement("", "userdata", "", atr);
					wr.characters(fullName);
					wr.endElement("userdata");
					
					wr.startElement("cell");
					wr.characters(fullName) ;
					wr.endElement("cell");
					
					String dateCreated = "";
					
					try {
						dateCreated = DateUtils.getSystemDateFormat(sDo.getDate("dateCreated"));
					} catch (ClassCastException cce) {
						TPLogger.getLogger().error(GlobalConstants.ERROR, cce);
					}
					
					atr = new AttributesImpl();
					atr.addAttribute("", "name", "", "", "dateCreated");
					wr.startElement("", "userdata", "", atr);
					wr.characters(Utils.getBlankIfNull(sDo.getString("dateCreated")));
					wr.endElement("userdata");
					
					wr.startElement("cell");
					wr.characters(dateCreated) ;
					wr.endElement("cell");
					
					String visibility = sDo.getString("visibility");
					String visibilityType = "";
					String draftType = ""; // This var used to check is allowed to change status to private
					if(visibility.equalsIgnoreCase(PositionDraftConstants.POSITION_DRAFT_STATUS_PRIVATE)){
						visibilityType = TPLabels.getLabel("position.draft.status.label.private");
						draftType = PositionDraftConstants.POSITION_DRAFT_STATUS_PRIVATE;
					}else if(visibility.equalsIgnoreCase(PositionDraftConstants.POSITION_DRAFT_STATUS_SHARED)){
						visibilityType = TPLabels.getLabel("position.draft.status.label.shared");
						draftType = PositionDraftConstants.POSITION_DRAFT_STATUS_SHARED;
					}
						
					atr = new AttributesImpl();
					atr.addAttribute("", "name", "", "", "visibilityType");
					wr.startElement("", "userdata", "", atr);
					wr.characters(visibilityType);
					wr.endElement("userdata");
					
					atr = new AttributesImpl();
					atr.addAttribute("", "name", "", "", "draftType");
					wr.startElement("", "userdata", "", atr);
					wr.characters(draftType);
					wr.endElement("userdata");
					
					wr.startElement("cell");
					wr.characters(visibilityType) ;
					wr.endElement("cell");
					
					//set owner attribute
					String draftOwner ="";
					if(Integer.parseInt(userRoles) == UserConstants.ROLE_ADMIN || uId.equalsIgnoreCase(userId)){ 
						draftOwner = "1";
					}else{
						draftOwner = "0";
					}
					atr = new AttributesImpl();
					atr.addAttribute("", "name", "", "", "draftOwner");
					wr.startElement("", "userdata", "", atr);
					wr.characters(draftOwner);
					wr.endElement("userdata");
					
					wr.endElement("row");
				}
			}
			wr.endElement("rows");
			wr.endDocument();
		}catch (SAXException e) {
			TPLogger.getLogger().error("Error while creating xml for Position Drafts", e);
		}
		return sWr.getBuffer().toString();
	}
}
