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
import com.talentPool.masters.dataobject.EmployerAliasesData;
import com.talentPool.masters.dataobject.EmployersData;
import com.talentPool.masters.service.IMasterService;
import com.talentPool.struts2.common.TPActionSupport;

/**
 * @author Shantanu
 *
 */
public class EmployerMasterAction extends TPActionSupport{
	
	private static final long serialVersionUID = 1L;
	private String employerId = null;
	private String employerName = null;
	private String updated = null;
	private String[] alias = new String[3];
	
	private EmployerAliasesData employerAliasesData = new EmployerAliasesData(); 
	
	private IMasterService _masterService;
	
	/**
	 * @return the employerId
	 */
	public String getEmployerId() {
		return employerId;
	}

	/**
	 * @param employerId the employerId to set
	 */
	public void setEmployerId(String employerId) {
		this.employerId = employerId;
	}

	/**
	 * @return the employerName
	 */
	public String getEmployerName() {
		return employerName;
	}

	/**
	 * @param employerName the employerName to set
	 */
	public void setEmployerName(String employerName) {
		this.employerName = employerName;
	}
		
	/**
	 * @return the employerAliasesData
	 */
	public EmployerAliasesData getEmployerAliasesData() {
		return employerAliasesData;
	}

	/**
	 * @param employerAliasesData the employerAliasesData to set
	 */
	public void setEmployerAliasesData(EmployerAliasesData employerAliasesData) {
		this.employerAliasesData = employerAliasesData;
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

	public String manageEmployers() throws Exception {
		ServletActionContext.getRequest().setAttribute("t", NavigationConstants.T_MASTERS);
		ServletActionContext.getRequest().setAttribute("masterType", MastersConstants.MASTER_TYPE_EMPLOYER);		
		return SUCCESS;
	}

	public String getEmployerXML() throws Exception {
		String xmlFile = "";
		try {			
			xmlFile = _masterService.getXMLForEmployers();
			ServletActionContext.getRequest().setAttribute("xmlFile", xmlFile);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}		
		return SUCCESS;
	}
	
	public String addEmployer() throws Exception {
		return SUCCESS;
	}
	
	public String editEmployer() throws Exception {
		try {
			if(!Utils.isBlankOrNull(getEmployerId())){
				List<EmployersData> lstEmpData = _masterService.getEmployer(getEmployerId());
				setEmployerName(lstEmpData.get(0).getEmployerName());
				List<EmployerAliasesData> empAliasesDataList = _masterService.getEmployerAliases(getEmployerId());
				String[] aliasStr = new String[3];				
				for(int i=0; i<aliasStr.length && i<empAliasesDataList.size(); i++){
					EmployerAliasesData empAlDat = empAliasesDataList.get(i);
					aliasStr[i]=empAlDat.getId().getAlias();
				}
				setAlias(aliasStr);
			}			
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return SUCCESS;
	}

	public String saveEmployer() throws Exception {
		try {						
			if(!Utils.isBlankOrNull(getEmployerId())){
				_masterService.updateEmployer(getEmployerId(), getEmployerName(), getAlias());				
			}else{
				_masterService.saveEmployer(getEmployerId(), getEmployerName(), getAlias());				
			}
			setUpdated("1");
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return SUCCESS;
	}
	
	public String deleteEmployer() throws Exception {
		try {
			_masterService.deleteEmployer(getEmployerId());
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}		
		return SUCCESS;
	}
}