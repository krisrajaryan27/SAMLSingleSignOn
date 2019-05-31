/**
 * 
 */
package com.talentPool.masters.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.talentPool.common.CommonConstants;
import com.talentPool.common.NavigationConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.base.TPDispatchAction;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.CommonUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.masters.constants.MastersConstants;
import com.talentPool.masters.dataobject.LocationData;
import com.talentPool.masters.form.MastersForm;
import com.talentPool.masters.manager.LocationManager;
import com.talentPool.user.constants.PermissionConstants;
import com.talentPool.user.manager.SessionManager;

/**
 * @author Ajeet
 *
 */
public class LocationAction extends TPDispatchAction {

	public ActionForward manageLocationsMaster(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "manageLocationsMaster";
		Integer[] permissions = new Integer[1];
		permissions[0] = PermissionConstants.PERMISSION_MASTERS;
		if(!isUserAuthorized(request, 0, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		request.setAttribute("masterType", MastersConstants.MASTER_TYPE_LOCATIONS);
		request.setAttribute("t", NavigationConstants.T_MASTERS);
		return mapping.findForward(forward);
	}
	
	
	public ActionForward getLocations(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				LocationManager locationManager = new LocationManager();
				 List<LocationData> locations = locationManager.getAllLocations();
				xmlFile = locationManager.getXMLForLocations(locations);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			xmlFile = Utils.getXMLForError(null);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}

	public ActionForward addLocation(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "addLocation";
		Integer[] permissions = new Integer[1];
		permissions[0] = PermissionConstants.PERMISSION_MASTERS;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		try {
			MastersForm mastersForm = (MastersForm) actionForm;
			String locationId = mastersForm.getLocationId();
			if(!Utils.isBlankOrNull(locationId)) {
				LocationManager locationManager = new LocationManager();
				LocationData data = locationManager.getLocationByLocationId(locationId);
				mastersForm.setLocationId(data.getLocationId());
				mastersForm.setLocationName(data.getLocationName());
			}
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward saveLocation(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "addLocation";
		
		MastersForm mastersForm = (MastersForm) actionForm;
		String locationId = mastersForm.getLocationId();
		String locationName = mastersForm.getLocationName();
		
		try {
			LocationManager locationManager = new LocationManager();
			LocationData location = locationManager.getLocationByName(locationName);
			ActionErrors errors = validateLocationData(locationId, location);
			if(errors != null && errors.size() > 0) {
				saveErrors(request, errors);
			} else {
				if(!Utils.isBlankOrNull(locationId)) {				
					locationManager.updateLocation(locationId, locationName);
				} else {
					locationManager.addLocation(locationName);
				}
				CommonUtils.setLocationIds(null);
				CommonUtils.setLocationNames(null);
				request.setAttribute("update", "1");
			}			
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			ActionErrors errors = new ActionErrors();
			if(Utils.isBlankOrNull(locationId)) {
				errors.add("master_locations.error.save_location", new ActionError("master_locations.error.save_location"));
			} else {
				errors.add("master_locations.error.update_location", new ActionError("master_locations.error.update_location"));
			}
			saveErrors(request, errors);
		}
		return mapping.findForward(forward);
	}

	private ActionErrors validateLocationData(String locationId, LocationData location) {
		ActionErrors errors = new ActionErrors();
		if(location != null) {
			if(!Utils.isBlankOrNull(locationId)) {
				if(!locationId.equalsIgnoreCase(location.getLocationId())) {
					errors.add("master_locations.error.location_name", new ActionError("master_locations.error.location_name"));
				}
			} else {
				errors.add("master_locations.error.location_name", new ActionError("master_locations.error.location_name"));
			}
		}
		return errors;
	}
	
	public ActionForward deleteLocation(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				MastersForm mastersForm = (MastersForm) actionForm;
				String locationId = mastersForm.getLocationId();
				LocationManager locationManager = new LocationManager();
				locationManager.deleteLocation(locationId);
				CommonUtils.setLocationIds(null);
				CommonUtils.setLocationNames(null);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			xmlFile = Utils.getXMLForError(null);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}
	
	public ActionForward manageOffices(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws SQLException {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "manageOffices";
		Integer[] permissions = new Integer[1];
		permissions[0] = PermissionConstants.PERMISSION_MASTERS;
		if(!isUserAuthorized(request, 0, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		MastersForm mastersForm = (MastersForm) actionForm;
		LocationManager locationManager = new LocationManager();
		String locationId = mastersForm.getLocationId();
		if (!Utils.isBlankOrNull(locationId)) {
			LocationData data = locationManager.getLocationByLocationId(locationId);
			mastersForm.setLocationName(data.getLocationName());
		}
		request.setAttribute("masterType", MastersConstants.MASTER_TYPE_LOCATIONS);
		request.setAttribute("t", NavigationConstants.T_MASTERS);
		return mapping.findForward(forward);
	}
	
	public ActionForward getOffices(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				MastersForm mastersForm = (MastersForm) actionForm;
				LocationManager locationManager = new LocationManager();
				List<LocationData> offices = locationManager.getOfficesForLocation(mastersForm.getLocationId());
				xmlFile = locationManager.getXMLForOffices(offices);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			xmlFile = Utils.getXMLForError(null);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}
	
	public ActionForward addOffice(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "addOffice";
		Integer[] permissions = new Integer[1];
		permissions[0] = PermissionConstants.PERMISSION_MASTERS;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		try {
			MastersForm mastersForm = (MastersForm) actionForm;
			String officeId = mastersForm.getOfficeId();
			if(!Utils.isBlankOrNull(officeId)) {
				LocationManager locationManager = new LocationManager();
				LocationData data = locationManager.getOfficeData(officeId);
				mastersForm.setOfficeId(data.getOfficeId());
				mastersForm.setOfficeName(data.getOfficeName());
				mastersForm.setOfficeAddress(data.getOfficeAddress());
				mastersForm.setOfficeDesc(data.getOfficeDesc());
			}
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}
	
	
	public ActionForward saveOffice(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "addOffice";
		
		MastersForm mastersForm = (MastersForm) actionForm;
		String locationId = mastersForm.getLocationId();
		String officeId = mastersForm.getOfficeId();
		
		LocationData locationData = new LocationData();
		locationData.setLocationId(mastersForm.getLocationId());
		locationData.setOfficeId(mastersForm.getOfficeId());
		locationData.setOfficeName(mastersForm.getOfficeName());
		locationData.setOfficeAddress(mastersForm.getOfficeAddress());
		locationData.setOfficeDesc(mastersForm.getOfficeDesc());
		System.out.println("locationData="+locationData);
		try {
			LocationManager locationManager = new LocationManager();
			if(!Utils.isBlankOrNull(officeId)) {				
				locationManager.updateOffice(locationData);
			} else {
				locationManager.addOffice(locationData);
			}
			request.setAttribute("update", "1");			
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			ActionErrors errors = new ActionErrors();
			if(Utils.isBlankOrNull(officeId)) {
				errors.add("master_locations.error.save_office", new ActionError("master_locations.error.save_office"));
			} else {
				errors.add("master_locations.error.update_office", new ActionError("master_locations.error.update_office"));
			}
			saveErrors(request, errors);
		}
		return mapping.findForward(forward);
	}
	
	public ActionForward deleteOffice(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				MastersForm mastersForm = (MastersForm) actionForm;
				String officeId = mastersForm.getOfficeId();
				LocationManager locationManager = new LocationManager();
				locationManager.deleteOffice(officeId);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			xmlFile = Utils.getXMLForError(null);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}
}
