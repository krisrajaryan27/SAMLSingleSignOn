/**
 * 
 */
package com.talentPool.masters.manager;

import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.CommonUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.xmlutils.XMLWriter;
import com.talentPool.masters.dataobject.DepartmentData;
import com.talentPool.masters.dataobject.LocationData;

/**
 * @author Ajeet
 *
 */
public class LocationManager {

	public List<LocationData> getAllLocations() throws SQLException {
		List<LocationData> locations = new ArrayList<LocationData>();
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dMastersManager_GetAllLocations");
			locations = (List<LocationData>) dq.getResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		}finally {
			if(dq != null) {
				dq.releaseTransaction(null);
			}
		}
		return locations;
	}
	
	public String getXMLForLocations(List<LocationData> locations) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);

		try {
			wr.startDocument();
			wr.startElement("rows");
			if(locations != null && locations.size() > 0) {
				for (int i = 0; locations != null && i < locations.size(); i++) {
					LocationData data = locations.get(i);
					String officeName = Utils.isBlankOrNull(data.getOfficeName())? "NA":data.getOfficeName();
					
					AttributesImpl at = new AttributesImpl();
					at.addAttribute("", "id", "", "", data.getLocationId());
					wr.startElement("", "row", "", at);

					at = new AttributesImpl();
					at.addAttribute("", "name", "", "", "Col_I_Comment");
					wr.startElement("", "userdata", "", at);
					wr.characters("Delete");
					wr.endElement("userdata");

					at = new AttributesImpl();
					at.addAttribute("", "name", "", "", "locationName");
					wr.startElement("", "userdata", "", at);
					wr.characters(data.getLocationName());
					wr.endElement("userdata");
					
					//removed as we are now giving multiple location insteed of multi level locations
					/*at = new AttributesImpl();
					at.addAttribute("", "name", "", "", "officeName");
					wr.startElement("", "userdata", "", at);
					wr.characters(officeName);
					wr.endElement("userdata");*/

					wr.startElement("cell");
					wr.characters("<img src=\"images/ico_delete.gif\" border=0>^javascript:deleteRecord(" + data.getLocationId() + ");^_self");
					wr.endElement("cell");

					wr.startElement("cell");
					wr.characters(wr.doubleEscape(data.getLocationName()) + "^javascript:editRecord(" + data.getLocationId() + ");^_self");
					wr.endElement("cell");
					
					/*wr.startElement("cell");					
					wr.characters(wr.doubleEscape(officeName) + "^javascript:manageOffices(" + data.getLocationId() + ");^_self");
					wr.endElement("cell");*/

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

	public List<LocationData> getOfficesForLocation(String locationId) throws SQLException {
		List<LocationData> offices = new ArrayList<LocationData>();
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dMastersManager_GetOfficesForLocation");
			dq.setString(1, locationId);
			offices = (List<LocationData>) dq.getResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		}finally {
			if(dq != null) {
				dq.releaseTransaction(null);
			}
		}
		return offices;
	}

	public String getXMLForOffices(List<LocationData> offices) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);

		try {
			wr.startDocument();
			wr.startElement("rows");
			if(offices != null && offices.size() > 0) {
				for (int i = 0; offices != null && i < offices.size(); i++) {
					LocationData data = offices.get(i);
					
					String officeAddress = Utils.isBlankOrNull(data.getOfficeAddress())?"":data.getOfficeAddress();
					
					AttributesImpl at = new AttributesImpl();
					at.addAttribute("", "id", "", "", data.getOfficeId());
					wr.startElement("", "row", "", at);

					at = new AttributesImpl();
					at.addAttribute("", "name", "", "", "Col_I_Comment");
					wr.startElement("", "userdata", "", at);
					wr.characters("Delete");
					wr.endElement("userdata");
					
					at = new AttributesImpl();
					at.addAttribute("", "name", "", "", "officeName");
					wr.startElement("", "userdata", "", at);
					wr.characters(data.getOfficeName());
					wr.endElement("userdata");
					
					at = new AttributesImpl();
					at.addAttribute("", "name", "", "", "officeAddress");
					wr.startElement("", "userdata", "", at);
					wr.characters(officeAddress);
					wr.endElement("userdata");

					wr.startElement("cell");
					wr.characters("<img src=\"images/ico_delete.gif\" border=0>^javascript:deleteRecord(" + data.getOfficeId() + ");^_self");
					wr.endElement("cell");

					wr.startElement("cell");
					wr.characters(wr.doubleEscape(data.getOfficeName()) + "^javascript:editRecord(" + data.getOfficeId() + ");^_self");
					wr.endElement("cell");
					
					wr.startElement("cell");
					wr.characters(wr.doubleEscape(officeAddress));
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

	public LocationData getLocationByLocationId(String locationId) throws SQLException {
		LocationData data = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dMastersManager_GetLocationById");
			dq.setString(1, locationId);
			data = (LocationData) dq.getSingleObjectResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		} finally {
			if(dq != null) {
				dq.releaseTransaction(null);
			}
		}
		return data;
	}
	
	public List<LocationData> getLocationsListByLocationId(String locationId) throws SQLException {
		List<LocationData> data = null;
		DBPreparedQuery dq = null;
		String[] dynParam = new String[1];
		try {
			dynParam[0] = "";
			ArrayList<String> dynamicContent = new ArrayList<String>();
			String qMarks = Utils.setDynamicParamsAndReturnQmarks(locationId, dynamicContent);
			dynParam[0] = qMarks;
			dq = new DBPreparedQuery("dMastersManager_GetLocationsById",dynParam);
			int cnt = 1;
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}
			data = (List<LocationData>) dq.getResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		} finally {
			if(dq != null) {
				dq.releaseTransaction(null);
			}
		}
		return data;
	}
	public List<LocationData> getLocationsListByName(String locationName) throws SQLException {
		DBPreparedQuery dq = null;
		List<LocationData> data = null;
		String[] dynParam = new String[1];
		try {
			dynParam[0] = "";
			ArrayList<String> dynamicContent = new ArrayList<String>();
			String qMarks = Utils.setDynamicParamsAndReturnQmarks(locationName, dynamicContent);
			dynParam[0] = qMarks;
			dq = new DBPreparedQuery("dMastersManager_GetLocationsByName",dynParam);
			int cnt = 1;
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}
			data = (List<LocationData>) dq.getResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		}finally {
			if(dq != null) {
				dq.releaseTransaction(null);
			}
		}
		return data;
	}
	
	public LocationData getLocationByName(String locationName) throws SQLException {
		DBPreparedQuery dq = null;
		LocationData data = null;
		try {
			dq = new DBPreparedQuery("dMastersManager_GetLocationByName");
			dq.setString(1, locationName);
			data = (LocationData) dq.getSingleObjectResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		}finally {
			if(dq != null) {
				dq.releaseTransaction(null);
			}
		}
		return data;
	}
	
	public void addLocation(String locationName) throws SQLException {
		DBPreparedQuery dq = null;		
		try {
			dq = new DBPreparedQuery("dMastersManager_AddLocation");
			dq.setString(1, locationName);
			dq.execute();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		}finally {
			if(dq != null) {
				dq.releaseTransaction(null);
			}
		}
	}
	
	public void updateLocation(String locationId, String locationName) throws SQLException {
		DBPreparedQuery dq = null;		
		try {
			dq = new DBPreparedQuery("dMastersManager_UpdateLocation");
			dq.setString(1, locationName);
			dq.setString(2, locationId);
			dq.execute();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		}finally {
			if(dq != null) {
				dq.releaseTransaction(null);
			}
		}
	}
	
	public void deleteLocation(String locationId) throws SQLException {
		DBPreparedQuery dq = null;		
		try {
			dq = new DBPreparedQuery("dMastersManager_DeleteLocation");			
			dq.setString(1, locationId);
			dq.execute();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		}finally {
			if(dq != null) {
				dq.releaseTransaction(null);
			}
		}
	}

	public LocationData getOfficeData(String officeId) throws SQLException {
		LocationData data = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dMastersManager_GetOfficeData");
			dq.setString(1, officeId);
			data = (LocationData) dq.getSingleObjectResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		} finally {
			if(dq != null) {
				dq.releaseTransaction(null);
			}
		}
		return data;
	}

	public void addOffice(LocationData locationData) throws SQLException {
		DBPreparedQuery dq = null;		
		try {
			dq = new DBPreparedQuery("dMastersManager_AddOffice");
			dq.setString(1, locationData.getOfficeName());
			dq.setString(2, locationData.getOfficeAddress());
			dq.setString(3, locationData.getOfficeDesc());
			dq.setString(4, locationData.getLocationId());
			dq.execute();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		}finally {
			if(dq != null) {
				dq.releaseTransaction(null);
			}
		}
	}
	public void updateOffice(LocationData locationData) throws SQLException {
		DBPreparedQuery dq = null;		
		try {
			dq = new DBPreparedQuery("dMastersManager_UpdateOffice");
			dq.setString(1, locationData.getOfficeName());
			dq.setString(2, locationData.getOfficeAddress());
			dq.setString(3, locationData.getOfficeDesc());
			dq.setString(4, locationData.getOfficeId());
			dq.execute();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		}finally {
			if(dq != null) {
				dq.releaseTransaction(null);
			}
		}
	}
	
	public void deleteOffice(String officeId) throws SQLException {
		DBPreparedQuery dq = null;		
		try {
			dq = new DBPreparedQuery("dMastersManager_DeleteOffice");			
			dq.setString(1, officeId);
			dq.execute();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		}finally {
			if(dq != null) {
				dq.releaseTransaction(null);
			}
		}
	}
	
	//Added for Asian Paints BEGIN
	public String getJSArrayLocations() {
		List locIds = CommonUtils.getLocIds();
		List locNames = CommonUtils.getLocNames();
		String jsArrayLocations = CommonUtils.getListJavaScriptArray((ArrayList) locIds, (ArrayList) locNames);
		return jsArrayLocations;
	}
	
	public String getJSArrayRegions(String locId) {
		String jsArraySubLocations = "new Array()";
		if (!Utils.isBlankOrNull(locId)) {
			ArrayList<LocationData> locations = getRegions(locId);
			jsArraySubLocations = CommonUtils.getJobCodeListJavaScriptArrayWithProperties(locations, "itemId", "itemName", "itemExternalCode");
		}
		return jsArraySubLocations;
	}
	
	public ArrayList<LocationData> getRegions(String locId){
		ArrayList<LocationData> locations = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dPositionManager_GetRegions");
			dq.setId(1, locId);
			locations = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return locations;
	}
	
	public ArrayList<LocationData> getStates(String locId){
		ArrayList<LocationData> locations = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dPositionManager_GetStates");
			dq.setId(1, locId);
			locations = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return locations;
	}
	
	public String getJSArrayState(String locId) {
		String jsArrayState = "new Array()";
		if (!Utils.isBlankOrNull(locId)) {
			ArrayList<LocationData> locations = getStates(locId);
			jsArrayState = CommonUtils.getListJavaScriptArrayWithProperties(locations, "itemId", "itemName");
		}
		return jsArrayState;
	}
	
	public String getJSArrayCountry() {
		List countryIds = CommonUtils.getCountryIds();
		List countryNames = CommonUtils.getCountryNames();
		String jsArrayCountry = CommonUtils.getListJavaScriptArray((ArrayList) countryIds, (ArrayList) countryNames);
		return jsArrayCountry;
	}
	
	public String getJSArraySubLocations(String locId) {
		String jsArraySubLocations = "new Array()";
		if (!Utils.isBlankOrNull(locId)) {
			ArrayList<LocationData> locations = getSubLocations(locId);
			jsArraySubLocations = CommonUtils.getJobCodeListJavaScriptArrayWithProperties(locations, "itemId", "itemName", "itemExternalCode");
		}
		return jsArraySubLocations;
	}
	
	
	
	public ArrayList<LocationData> getSubLocations(String locId){
		ArrayList<LocationData> locations = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dPositionManager_GetSubLocations");
			dq.setId(1, locId);
			locations = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return locations;
	}
	
	public String getJSArrayLocationsById(String locId) {
		String jsArrayLocations = "new Array()";
		if (!Utils.isBlankOrNull(locId)) {
			ArrayList<LocationData> locations = getLocationsById(locId);
			jsArrayLocations = CommonUtils.getJobCodeListJavaScriptArrayWithProperties(locations, "itemId", "itemName","itemExternalCode");
		}
		return jsArrayLocations;
	}
	
	public ArrayList<LocationData> getLocationsById(String locId){
		ArrayList<LocationData> locations = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dPositionManagerForAP_GetLocationById");
			dq.setId(1, locId);
			locations = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return locations;
	}
	//Added for Asian Paints END
	
	

	
}
