package com.talentPool.positions;

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
import com.talentPool.user.UserConstants;
import com.talentPool.user.dataobject.LoginData;
import com.talentPool.user.manager.LoginManager;
import com.talentPool.user.manager.PermissionSet;

public class PositionTemplateXMLGenerator {
	public String getXMLforPositionTemplatesHome(ArrayList positions, String userId, PermissionSet permissionSet) {
		
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			wr.startDocument();
			wr.startElement("rows");
			if (positions != null && positions.size() > 0) {
				for (int i = 0; i < positions.size(); i++) {
					SimpleDataObject sDo = (SimpleDataObject) positions.get(i);				
					
					AttributesImpl atr = new AttributesImpl();
					atr.addAttribute("", "id", "", "", String.valueOf(sDo.getString("positionId")));
					wr.startElement("", "row", "", atr);
					
					wr.startElement("cell");
					wr.characters(wr.doubleEscape(""));
					wr.endElement("cell");
					
					String uId = sDo.getString("createdById");
					
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
						wr.characters("<a href=\"#\" onclick=\"deleteTemplate(" + sDo.getString("positionId") + ");\" title=\"Delete\"><img src=\"images/ico_delete.gif\" border=0></a>");
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
					
					String positionName = sDo.getString("positionTitle");
					
					atr = new AttributesImpl();
					atr.addAttribute("", "name", "", "", "positionName");
					wr.startElement("", "userdata", "", atr);
					wr.characters(positionName);
					wr.endElement("userdata");

					if (positionName.length() > 38) {
						positionName = positionName.substring(0, 35) + "...";
					}					
					
					if(permissionSet.isPERMISSION_POSITION_DETAILS()){
						wr.startElement("cell");
						wr.characters("<a href=\"#\" onclick=\"javascript:viewDetails(" + sDo.getString("positionId") + ");\">" + wr.doubleEscape(positionName) + "</a>");
						wr.endElement("cell");	
					}else{
						wr.startElement("cell");
						wr.characters(wr.doubleEscape(positionName));
						wr.endElement("cell");
					}
					

					String department = (sDo.getString("departmentName") == null) ? "" : sDo.getString("departmentName");

					atr = new AttributesImpl();
					atr.addAttribute("", "name", "", "", "department");
					wr.startElement("", "userdata", "", atr);
					wr.characters(department);
					wr.endElement("userdata");

					if (department.length() > 17) {
						department = department.substring(0, 14) + "...";
					}
					wr.startElement("cell");
					wr.characters(wr.doubleEscape(department));
					wr.endElement("cell");
					
					String createdBy = (sDo.getString("createdBy") == null) ? "" : sDo.getString("createdBy");
					atr = new AttributesImpl();
					atr.addAttribute("", "name", "", "", "createdBy");
					wr.startElement("", "userdata", "", atr);
					wr.characters(createdBy);
					wr.endElement("userdata");
					
					wr.startElement("cell");
					wr.characters(createdBy);
					wr.endElement("cell");
					
					String creationDate = "";
					try {
						creationDate = DateUtils.getSystemDateFormat(sDo.getDate("creationDate")); 
					} catch (ClassCastException cce) {
						TPLogger.getLogger().error(GlobalConstants.ERROR, cce);
					}
					
					atr = new AttributesImpl();
					atr.addAttribute("", "name", "", "", "creationDate");
					wr.startElement("", "userdata", "", atr);
					wr.characters(Utils.getBlankIfNull(sDo.getString("creationDate")));
					wr.endElement("userdata");					
					
					wr.startElement("cell");
					wr.characters(creationDate);
					wr.endElement("cell");

					wr.endElement("row");
				}
			}
			wr.endElement("rows");
			wr.endDocument();

		} catch (SAXException e) {
			TPLogger.getLogger().error("Error while creating xml for Positions", e);
		}
		return sWr.getBuffer().toString();
	}
}
