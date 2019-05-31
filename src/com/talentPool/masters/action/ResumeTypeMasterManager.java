package com.talentPool.masters.action;

import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.xmlutils.XMLWriter;

public class ResumeTypeMasterManager {
	
	public void addResumeType(String resumeType) throws SQLException {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dResumeTypeMasters_AddResumeType");
			dq.setString(1, resumeType.trim());
			dq.execute();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw new SQLException();
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}
	
	public SimpleDataObject getResumeType(String resumeTypeId) throws SQLException {
		DBPreparedQuery dq = null;
		try {			
			dq = new DBPreparedQuery("dResumeTypeMasters_GetResumeType");			
			dq.setString(1, resumeTypeId);
			return (SimpleDataObject) dq.getSingleObjectResult();
		}catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);		
			throw new SQLException();
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}
	
	public ArrayList<SimpleDataObject> getResumeTypes() throws SQLException {
		ArrayList<SimpleDataObject> resumeTypes = new ArrayList<SimpleDataObject>();
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dResumeTypeMasters_GetAllResumeTypes");
			resumeTypes = dq.getResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		}finally {
			if(dq != null) {
				dq.releaseConnection();
			}
		}
		return resumeTypes;
	}
	
	public void updateResumeType(String resumeTypeId, String resumeType ) throws SQLException {
		DBPreparedQuery dq = null;
		try {			
			dq = new DBPreparedQuery("dResumeTypeMasters_UpdateResumeType");
			dq.setString(1, resumeType.trim());			
			dq.setString(2, resumeTypeId);
			dq.execute();
		}catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);			
			throw new SQLException();
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}
	
	public void deleteResumeType(String resumeTypeId) throws SQLException {
		DBPreparedQuery dq = null;
		try {			
			dq = new DBPreparedQuery("dResumeTypeMasters_DeleteResumeType");			
			dq.setString(1, resumeTypeId);
			dq.execute();
		}catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);			
			throw new SQLException();
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}

	public String getXMLForResumeTypes(ArrayList<SimpleDataObject> resumeTypes) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);

		try {
			wr.startDocument();
			wr.startElement("rows");
			if(resumeTypes != null && resumeTypes.size() > 0) {
				for (int i = 0; resumeTypes != null && i < resumeTypes.size(); i++) {
					SimpleDataObject data = resumeTypes.get(i);

					AttributesImpl at = new AttributesImpl();
					at.addAttribute("", "id", "", "", data.getAttribute("resumeTypeId")+"");
					wr.startElement("", "row", "", at);

					at = new AttributesImpl();
					at.addAttribute("", "name", "", "", "Col_I_Comment");
					wr.startElement("", "userdata", "", at);					
					wr.characters("Delete");						
					wr.endElement("userdata");
				
					wr.startElement("cell");
					wr.characters("<img src=\"images/ico_delete.gif\" border=0 onclick=\"javascript:deleteRecord(" + data.getAttribute("resumeTypeId") + ");\" />");												
					wr.endElement("cell");
					
					at = new AttributesImpl();
					at.addAttribute("", "name", "", "", "itemName");
					wr.startElement("", "userdata", "", at);					
					wr.characters((String) data.getAttribute("resumeType"));						
					wr.endElement("userdata");

					wr.startElement("cell");
					wr.characters(wr.doubleEscape((String) data.getAttribute("resumeType")) + "^javascript:editRecord(" + data.getAttribute("resumeTypeId") + ");^_self");
					wr.endElement("cell");	

					
					wr.endElement("row");
				}
			}			
			wr.endElement("rows");
			wr.endDocument();
		} catch (SAXException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}

		return sWr.getBuffer().toString();
	}
}
