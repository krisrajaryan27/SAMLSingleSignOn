package com.talentPool.services.handler;

import java.util.TimeZone;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;

import org.apache.axis.AxisFault;
import org.apache.axis.Message;
import org.apache.axis.MessageContext;
import org.apache.axis.description.OperationDesc;
import org.apache.axis.handlers.BasicHandler;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.utils.Utils;

/**
 * Axis handler to check for sessions in web services.
 * 
 * @author SiddharthK
 * 
 */
public class SessionHandler extends BasicHandler {

	private static final long serialVersionUID = 1L;

	public void invoke(MessageContext msgContext) throws AxisFault {

		try {
			org.apache.axis.session.Session session = msgContext.getSession();
			Message msg = msgContext.getRequestMessage();
			OperationDesc desc = msgContext.getOperation();
			QName qName = desc.getElementQName();
			String localPart = qName.getLocalPart();
			org.w3c.dom.NodeList methodNode = msg.getSOAPBody().getChildNodes();
			org.w3c.dom.NodeList attributesNode = methodNode.item(0).getChildNodes();
			/*
			 * if (session.get("timeZoneId") != null &&
			 * !Utils.isBlankOrNull((String)session.get("timeZoneId"))){
			 * TimeZone tzone =
			 * TimeZone.getTimeZone((String)session.get("timeZoneId"));
			 * TimeZone.setDefault(tzone); }
			 */
			if (localPart.equalsIgnoreCase("isValidLogin") || localPart.equalsIgnoreCase("getUserIfValidUUID")
					|| localPart.equalsIgnoreCase("sendNewPassword")
					|| localPart.equalsIgnoreCase("createEmployeeAccount")
					|| localPart.equalsIgnoreCase("getEPortalSettings")
					|| localPart.equalsIgnoreCase("sendConfirmationLink")
					|| localPart.equalsIgnoreCase("changePassword") || localPart.equalsIgnoreCase("getIdsAndNames")
					|| localPart.equalsIgnoreCase("updateEmployeePassword")
					|| localPart.equalsIgnoreCase("getUserDataFromEmailID") || localPart.equalsIgnoreCase("addUserIp")
					|| localPart.equalsIgnoreCase("isValidIPRequest")
					|| localPart.equalsIgnoreCase("getApplicationProperty")
					|| localPart.equalsIgnoreCase("getApplicants") || localPart.equalsIgnoreCase("getEmployeeDetail")
					|| localPart.equalsIgnoreCase("getPositions") || localPart.equalsIgnoreCase("getPositionDetails")
					|| localPart.equalsIgnoreCase("getAnnouncements")
					|| localPart.equalsIgnoreCase("getPositionsScreenFiltersData")
					|| localPart.equalsIgnoreCase("getPositionFields")
					|| localPart.equalsIgnoreCase("getPositionsSearched")) {
				// Nothing is to be done here because UUID in itself is a valid
				// authentication.
			} else {
				if (Utils.isBlankOrNull((String) (session.get("userId")))) {
					throw new AxisFault("Invalid Session");
				}
				for (int i = 0; i < attributesNode.getLength(); i++) {
					String paramName = attributesNode.item(i).getLocalName();
					if (paramName.equalsIgnoreCase("userId") || paramName.equalsIgnoreCase("vendorId")) {
						if (!attributesNode.item(i).getFirstChild().getNodeValue().equals(session.get("userId"))) {
							throw new AxisFault("Invalid Session");
						}
					}
				}
			}
		} catch (SOAPException e) {
			TPLogger.getLogger().error("Soap Exception in session validation");
		}

	}

}