/**
 * 
 */
package com.talentPool.masters.action;

import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.talentPool.common.NavigationConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.masters.constants.MastersConstants;
import com.talentPool.masters.dataobject.DesignationAliasesData;
import com.talentPool.masters.dataobject.DesignationsData;
import com.talentPool.masters.service.IMasterService;
import com.talentPool.struts2.common.TPActionSupport;

/**
 * @author Shantanu
 *
 */
public class DesignationMasterAction extends TPActionSupport{
	
	private static final long serialVersionUID = 1L;
	private String designationId = null;
	private String designationName = null;
	private String updated = null;
	private String[] alias = new String[3];
	
	private DesignationAliasesData designationAliasesData = null; 
	
	private IMasterService _masterService;
	
	/**
	 * @return the designationId
	 */
	public String getDesignationId() {
		return designationId;
	}

	/**
	 * @param designationId the designationId to set
	 */
	public void setDesignationId(String designationId) {
		this.designationId = designationId;
	}

	/**
	 * @return the designationName
	 */
	public String getDesignationName() {
		return designationName;
	}

	/**
	 * @param designationName the designationName to set
	 */
	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}

	/**
	 * @return the designationAliasesData
	 */
	public DesignationAliasesData getDesignationAliasesData() {
		return designationAliasesData;
	}

	/**
	 * @param designationAliasesData the designationAliasesData to set
	 */
	public void setDesignationAliasesData(
			DesignationAliasesData designationAliasesData) {
		this.designationAliasesData = designationAliasesData;
	}
	
	/**
	 * @return the _masterService
	 */
	public IMasterService getMasterService() {
		return _masterService;
	}

	/**
	 * @param masterService the _masterService to set
	 */
	public void setMasterService(IMasterService masterService) {
		this._masterService = masterService;
	}

	
	
	/**
	 * @return the updated
	 */
	public String getUpdated() {
		return updated;
	}

	/**
	 * @param updated the updated to set
	 */
	public void setUpdated(String updated) {
		this.updated = updated;
	}

	/**
	 * @return the alias
	 */
	public String[] getAlias() {
		return alias;
	}

	/**
	 * @param alias the alias to set
	 */
	public void setAlias(String[] alias) {
		this.alias = Utils.getArrayCopy(alias);
	}

	public String manageDesignations() throws Exception {
		ServletActionContext.getRequest().setAttribute("t", NavigationConstants.T_MASTERS);
		ServletActionContext.getRequest().setAttribute("masterType", MastersConstants.MASTER_TYPE_DESIGNATION);
		return SUCCESS;
	}
	
	public String getDesignationXML() throws Exception {
		String xmlFile = "";
		try {
			xmlFile = _masterService.getXMLForDesignations();
			ServletActionContext.getRequest().setAttribute("xmlFile", xmlFile);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}		
		return SUCCESS;
	}
	
	public String addDesignation() throws Exception {
		return SUCCESS;
	}
	
	public String editDesignation() throws Exception {
		try {
			if(!Utils.isBlankOrNull(getDesignationId())){
				List<DesignationsData> lstDesignationData = _masterService.getDesignation(getDesignationId());
				setDesignationName(lstDesignationData.get(0).getDesignationName());
				List<DesignationAliasesData> lstDesignationAliasData = _masterService.getDesignationAliases(getDesignationId()); 
				String[] aliasStr = new String[3];				
				for(int i=0; i<aliasStr.length && i<lstDesignationAliasData.size(); i++){
					DesignationAliasesData designationAlDat = lstDesignationAliasData.get(i);
					aliasStr[i]=designationAlDat.getId().getAlias();
				}
				setAlias(aliasStr);
			}			
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return SUCCESS;
	}

	public String saveDesignation() throws Exception {
		try {
			if(!Utils.isBlankOrNull(getDesignationId())){
				_masterService.updateDesignation(getDesignationId(), getDesignationName(), getAlias());				
			}else{			
				_masterService.saveDesignation(getDesignationId(), getDesignationName(), getAlias());
			}			
			setUpdated("1");
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return SUCCESS;
	}
	
	public String deleteDesignation() throws Exception {
		String xmlFile = "";
		try{
			_masterService.deleteDesignation(getDesignationId());
		} catch(Exception e){
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			xmlFile = Utils.getXMLForError(null);
		}
		getRequest().setAttribute("xmlFile", xmlFile);
		return SUCCESS;
	}	
}