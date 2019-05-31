/**
 * Copyright 2009 - Nitman Software Pvt. Ltd. All Rights Reserved. 
 * This software is the proprietary information of Nitman Software Pvt. Ltd. 
 * Use is subject to license terms.
 */
package com.talentPool.masters.action;

import java.io.StringWriter;
import java.util.List;

import org.xml.sax.SAXException;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.dataobject.DHTMLXUserData;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.xmlutils.DHTMLXXMLWriter;
import com.talentPool.masters.service.ISalCompCategoryService;
import com.talentPool.salaryStructure.entity.SalaryComponentCategory;
import com.talentPool.struts2.common.TPActionSupport;

/**
 * @author praveenk
 * @since  May 9, 2012
 */
public class SalCompCategoryMasterAction extends TPActionSupport {
	
	private int salCompCategoryId;
	private String salCompCategoryName;

	/**
	 * Auto Generated serial version id
	 */
	private static final long serialVersionUID = -3954059199347214914L;
	
	private ISalCompCategoryService _salCompCategoryService;
	
	/**
	 * @return the _salCompCategoryService
	 */
	public ISalCompCategoryService getSalCompCategoryService() {
		return _salCompCategoryService;
	}

	/**
	 * @param _salCompCategoryService the _salCompCategoryService to set
	 */
	public void setSalCompCategoryService(ISalCompCategoryService salCompCategoryService) {
		this._salCompCategoryService = salCompCategoryService;
	}
	
	/* (non-Javadoc)
	 * @see com.talentPool.struts2.common.TPActionSupport#execute()
	 */
	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}
	
	public String getSalCompCategoryXML() throws Exception {
		String xmlFile = "";
		try {
			List<SalaryComponentCategory> salCompCatList = _salCompCategoryService.findAll();
			xmlFile = generateSalCompCategoryXML(salCompCatList);
			setXMLInRequest(xmlFile);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return SUCCESS;
	}
	
	/**
	 * @param salCompCatList
	 * @return
	 */
	private String generateSalCompCategoryXML(List<SalaryComponentCategory> salCompCatList){
		StringWriter sWr = new StringWriter();
		DHTMLXXMLWriter wr = new DHTMLXXMLWriter(sWr);		
		try {
			wr.start();
			if (!Utils.isListEmptyOrNull(salCompCatList)) {
				for (SalaryComponentCategory salCompCat : salCompCatList) {
					String delCell = "<img src=\"images/ico_delete.gif\" style=\"cursor:pointer;\" border=0 onclick=\"javascript:deleteRecord(" + salCompCat.getCategoryId() + ");\" />";
					String catNameCell = wr.doubleEscape(salCompCat.getCategoryName()) + "^javascript:editRecord(" + salCompCat.getCategoryId() + ");^_self";
					DHTMLXUserData[] userData = {new DHTMLXUserData("itemName",salCompCat.getCategoryName())};
					wr.createDHTMLXRow(salCompCat.getCategoryId()+"", userData, new String[]{delCell, catNameCell});
				}
			}
			wr.end();
		}catch (SAXException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return sWr.getBuffer().toString();
	}
	
	public String addSalCompCategory() {
		return SUCCESS;
	}
	
	public String editSalCompCategory() throws Exception {
		try {
			SalaryComponentCategory salCompCategory = _salCompCategoryService.findById(getSalCompCategoryId());
			setSalCompCategoryId(salCompCategory.getCategoryId());
			setSalCompCategoryName(salCompCategory.getCategoryName());
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String saveSalCompCategory() throws Exception {
		try {
			_salCompCategoryService.saveOrUpdate(getSalCompCategoryId(), getSalCompCategoryName());
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			return ERROR;
		}
		return SUCCESS;
	}

	public String deleteSalCompCategory() throws Exception {
		try {
			_salCompCategoryService.deleteSalCompCategory(getSalCompCategoryId());		
		} catch (Exception e ) {
			TPLogger.getLogger().error("Error while deleting Salary Component Category :"+getSalCompCategoryId(), e);
			setXMLInRequest(Utils.getXMLForError());
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * @return the salCompCategoryId
	 */
	public int getSalCompCategoryId() {
		return salCompCategoryId;
	}

	/**
	 * @param salCompCategoryId the salCompCategoryId to set
	 */
	public void setSalCompCategoryId(int salCompCategoryId) {
		this.salCompCategoryId = salCompCategoryId;
	}

	/**
	 * @return the salCompCategoryName
	 */
	public String getSalCompCategoryName() {
		return salCompCategoryName;
	}

	/**
	 * @param salCompCategoryName the salCompCategoryName to set
	 */
	public void setSalCompCategoryName(String salCompCategoryName) {
		this.salCompCategoryName = salCompCategoryName;
	}
}
