/**
 * 
 */
package com.talentPool.latestActivity.utils;

import java.io.StringWriter;
import java.util.ArrayList;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.DateUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.xmlutils.XMLWriter;
import com.talentPool.selectionProcess.SelectionProcessConstants;
import com.talentPool.user.manager.PermissionSet;

/**
 * @author Ajeet
 *
 */
public class LatestActivityUtils {

	public static String getXmlForLatestActivity(ArrayList<SimpleDataObject> latestActivities, PermissionSet permissionSet) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			wr.startDocument();
			wr.startElement("rows");
			
			if (latestActivities != null && latestActivities.size() > 0) {
				for (int indx = 0; indx < latestActivities.size(); indx++) {
					SimpleDataObject data = (SimpleDataObject) latestActivities.get(indx);
					String interactionDate = DateUtils.getSystemDateTimeFormat(data.getDate("interactionDate"));

					AttributesImpl atr = new AttributesImpl();
					atr.addAttribute("", "id", "", "", "" + (indx + 1));
					wr.startElement("", "row", "", atr);

					atr = new AttributesImpl();
					atr.addAttribute("", "name", "", "", "date");
					wr.startElement("", "userdata", "", atr);
					wr.characters(Utils.getBlankIfNull(data.getString("interactionDate")));
					wr.endElement("userdata");

					wr.startElement("cell");
					wr.characters(interactionDate);
					wr.endElement("cell");

					String owner = (data.getString("owner") == null) ? "" : data.getString("owner");
					atr = new AttributesImpl();
					atr.addAttribute("", "name", "", "", "owner");
					wr.startElement("", "userdata", "", atr);
					wr.characters(owner);
					wr.endElement("userdata");

					wr.startElement("cell");
					if (owner.length() > 20) {
						owner = owner.substring(0, 17) + "...";
					}
					wr.characters(owner);
					wr.endElement("cell");

					String applicantName = (data.getString("applicantName") == null) ? "" : data.getString("applicantName");
					atr = new AttributesImpl();
					atr.addAttribute("", "name", "", "", "applicantName");
					wr.startElement("", "userdata", "", atr);
					wr.characters(applicantName);
					wr.endElement("userdata");

					String applicantLink = "<a href=\"#\" onclick=\"onClickApplicant('" +  data.getString("applicantId") + "');\" onmouseover=\"showAjaxTip(event,'" + data.getString("applicantId") + "')\" onmouseout=\"hideToolTip()\" >" + wr.doubleEscape(applicantName) + "</a>";
					wr.startElement("cell");
					if (applicantName.length() > 20) {
						applicantName = applicantName.substring(0, 17) + "...";
					}
					wr.characters(applicantLink);
					wr.endElement("cell");

					String activity = (data.getString("activity") == null) ? "" : data.getString("activity");
					atr = new AttributesImpl();
					atr.addAttribute("", "name", "", "", "activity");
					wr.startElement("", "userdata", "", atr);
					wr.characters(activity);
					wr.endElement("userdata");

					atr = new AttributesImpl();
					atr.addAttribute("", "name", "", "", "interactionType");
					wr.startElement("", "userdata", "", atr);
					wr.characters(data.getString("interactionType"));
					wr.endElement("userdata");

					atr = new AttributesImpl();
					atr.addAttribute("", "name", "", "", "applicantId");
					wr.startElement("", "userdata", "", atr);
					wr.characters(data.getString("applicantId"));
					wr.endElement("userdata");

					atr = new AttributesImpl();
					atr.addAttribute("", "name", "", "", "interactionId");
					wr.startElement("", "userdata", "", atr);
					wr.characters(data.getString("interactionId"));
					wr.endElement("userdata");

					String interactionIsHidden = data.getString("interactionIsHidden");
					wr.startElement("cell");
					if (activity.length() > 55) {
						activity = activity.substring(0, 52) + "...";
					}
					if (interactionIsHidden.equals(SelectionProcessConstants.INTERACTION_HIDE) && permissionSet.isDO_NOT_SHOW_CONFIDENTIAL_DATA()) {
						wr.characters(wr.doubleEscape(activity));
					} else {
						wr.characters(wr.doubleEscape(activity) + "^javascript:onClickActivity(" + data.getString("interactionId") + ", " + data.getString("interactionType") + ", " + data.getString("applicantId") + ");^_self");
					}
					wr.endElement("cell");

					atr = new AttributesImpl();
					atr.addAttribute("", "name", "", "", "interactionIsHidden");
					wr.startElement("", "userdata", "", atr);
					wr.characters(interactionIsHidden);
					wr.endElement("userdata");

					wr.startElement("cell");
					wr.characters(interactionIsHidden);
					wr.endElement("cell");

					wr.endElement("row");
				}
			}
			wr.endElement("rows");
			wr.endDocument();

		} catch (SAXException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return sWr.toString();
	}
	
}
