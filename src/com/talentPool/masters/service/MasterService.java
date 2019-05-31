/**
 * 
 */
package com.talentPool.masters.service;

import java.io.StringWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.xmlutils.XMLWriter;
import com.talentPool.masters.dao.IMasterDAO;
import com.talentPool.masters.dataobject.DesignationAliasesData;
import com.talentPool.masters.dataobject.DesignationAliasesId;
import com.talentPool.masters.dataobject.DesignationsData;
import com.talentPool.masters.dataobject.EmployerAliasesData;
import com.talentPool.masters.dataobject.EmployerAliasesId;
import com.talentPool.masters.dataobject.EmployersData;

/**
 * @author Shantanu
 *
 */
public class MasterService implements IMasterService {
	
	private IMasterDAO _masterDAO;
	
	/**
	 * @return the _masterDAO
	 */
	public IMasterDAO getMasterDAO() {
		return _masterDAO;
	}

	/**
	 * @param masterDao the _masterDAO to set
	 */
	public void setMasterDAO(IMasterDAO masterDao) {
		this._masterDAO = masterDao;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EmployersData> getEmployers(){
		List<EmployersData> empDataList = null;		
		try{
			empDataList = _masterDAO.getEmployers();
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,e);
		}
		return empDataList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<EmployersData> getEmployer(String employerIds){
		List<EmployersData> empDataList = null;
		try{			
			empDataList = _masterDAO.getEmployer(convertToLongArray(employerIds));
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,e);
		}
		return empDataList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<EmployerAliasesData> getEmployerAliases(String employerIds){
		List<EmployerAliasesData> empAliasesDataList = null;
		try{
			empAliasesDataList = _masterDAO.getEmployerAliases(convertToLongArray(employerIds));	
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,e);
		}
		return empAliasesDataList;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void saveEmployer(String employerId, String employerName, String[] aliases){
		EmployersData empData = new EmployersData();
		EmployerAliasesData empAlData =  null;
		EmployerAliasesId empAlId = null;
		Set<EmployerAliasesData>  setEmpData = new HashSet<EmployerAliasesData>();
		List<EmployersData> empDataList = null;
		List<EmployerAliasesData> empAliasesList = null;
		boolean dupEntry=false;
		try{
			empDataList = _masterDAO.getEmployers();
			empAliasesList = _masterDAO.getEmployersAliases();
			for(EmployersData ed:empDataList){
				if(ed.getEmployerName().equalsIgnoreCase(employerName)){
					dupEntry=true;
					break;
				}	
				for(String strAl:aliases){
					if(ed.getEmployerName().equalsIgnoreCase(strAl)){
						dupEntry=true;
						break;
					}	
				}
			}
			for(EmployerAliasesData ead:empAliasesList){
				if(ead.getId().getAlias().equalsIgnoreCase(employerName)){
					dupEntry = true;
					break;
				}
				for(String strAl:aliases){
					if(ead.getId().getAlias().equalsIgnoreCase(strAl)){
						dupEntry = true;
						break;
					}
				}
			}
			if(!dupEntry){
				empData.setEmployerName(employerName);
				for(String str:aliases){
					if(!Utils.isBlankOrNull(str)){					
						empAlId = new EmployerAliasesId(0,str);
						empAlData =  new EmployerAliasesData(empAlId,empData);
						setEmpData.add(empAlData);
					}
				}
				empData.setEmployerAliasesesData(setEmpData);
				_masterDAO.saveEmployer(empData);
			}
		}catch (HibernateException he) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, he);
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
	}
		
	
	@SuppressWarnings("unchecked")
	@Override
	public void updateEmployer(String employerId, String employerName, String[] aliases){
		EmployersData empData = new EmployersData();
		Set<EmployerAliasesData> empAliasesesDataSet = new HashSet<EmployerAliasesData>(0);
		try{
			empData.setEmployerId(Long.parseLong(employerId));
			empData.setEmployerName(employerName);
			for(String str:aliases){
				if(!Utils.isBlankOrNull(str)){
					EmployerAliasesData empAliasesData = new EmployerAliasesData();
					EmployerAliasesId empAliasesesId = new EmployerAliasesId();
					empAliasesesId.setEmployerId(Long.parseLong(employerId));
					empAliasesesId.setAlias(str);
					empAliasesData.setId(empAliasesesId);
					empAliasesData.setEmployersData(empData);
					empAliasesesDataSet.add(empAliasesData);
				}
			}
			empData.setEmployerAliasesesData(empAliasesesDataSet);
			_masterDAO.updateEmployer(empData);
		}catch (HibernateException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String getXMLForEmployers(){
		List<EmployersData> empDataList = null;
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			empDataList = getEmployers();
			wr.startDocument();
			wr.startElement("rows");
			if(empDataList != null && empDataList.size() > 0) {				
				for(EmployersData empData : empDataList){
					String employerId = empData.getEmployerId()+"";
					String employerName = empData.getEmployerName();
					
					AttributesImpl at = new AttributesImpl();
					at.addAttribute("", "id", "", "", employerId);
					wr.startElement("", "row", "", at);

					at = new AttributesImpl();
					at.addAttribute("", "name", "", "", "Col_I_Comment");
					wr.startElement("", "userdata", "", at);
					wr.characters("Delete");
					wr.endElement("userdata");
				
					wr.startElement("cell");
					wr.characters("<img src=\"images/ico_delete.gif\" border=0 onclick=\"javascript:deleteRecord(" + employerId  + ");\" />");												
					wr.endElement("cell");
					
					at = new AttributesImpl();
					at.addAttribute("", "name", "", "", "itemName");
					wr.startElement("", "userdata", "", at);
					wr.characters((String) employerName);
					wr.endElement("userdata");

					wr.startElement("cell");
					wr.characters(wr.doubleEscape((String) employerName) + "^javascript:editRecord(" + employerId + ");^_self");
					wr.endElement("cell");

					List<EmployerAliasesData> aliases = getEmployerAliases(employerId);
					StringBuffer sb = new StringBuffer();
					if (!aliases.isEmpty() && aliases!=null) {						
						for(int i=0;i<aliases.size();i++){
							EmployerAliasesData empAliasData = aliases.get(i);
							sb.append(empAliasData.getId().getAlias());
							if(i<aliases.size()-1){
								sb.append(", ");
							}
						}
					}

					at = new AttributesImpl();
					at.addAttribute("", "name", "", "", "alias");
					wr.startElement("", "userdata", "", at);
					wr.characters(sb.toString());
					wr.endElement("userdata");

					wr.startElement("cell");
					wr.characters(wr.doubleEscape(sb.toString().trim()));
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
	
	@SuppressWarnings("unchecked")
	@Override
	public void deleteEmployer(String employerId){
		try{
			_masterDAO.deleteEmployer(Long.parseLong(employerId));
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DesignationsData> getDesignations(){
		List<DesignationsData> designationDataList = null;
		try{
			designationDataList = _masterDAO.getDesignations();
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,e);
		}
		return designationDataList;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DesignationsData> getDesignation(String designationIds){
		List<DesignationsData> designationDataList = null;
		try{			
			designationDataList = _masterDAO.getDesignation(convertToLongArray(designationIds));
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,e);
		}
		return designationDataList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DesignationAliasesData> getDesignationAliases(String designationIds){
		List<DesignationAliasesData> designationAliasesDataList = null;
		try{
			designationAliasesDataList = _masterDAO.getDesignationAliases(convertToLongArray(designationIds));	
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,e);
		}
		return designationAliasesDataList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String getXMLForDesignations(){		
		List<DesignationsData> designationDataList = null;
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			designationDataList = getDesignations();
			wr.startDocument();
			wr.startElement("rows");
			if(designationDataList != null && designationDataList.size() > 0) {				
				for(DesignationsData designationData : designationDataList){
					String designationId = designationData.getDesignationId()+"";
					String designationName = designationData.getDesignationName();
					
					AttributesImpl at = new AttributesImpl();
					at.addAttribute("", "id", "", "", designationId);
					wr.startElement("", "row", "", at);

					at = new AttributesImpl();
					at.addAttribute("", "name", "", "", "Col_I_Comment");
					wr.startElement("", "userdata", "", at);					
					wr.characters("Delete");						
					wr.endElement("userdata");
				
					wr.startElement("cell");
					wr.characters("<img src=\"images/ico_delete.gif\" border=0 onclick=\"javascript:deleteRecord(" + designationId  + ");\" />");												
					wr.endElement("cell");
					
					at = new AttributesImpl();
					at.addAttribute("", "name", "", "", "itemName");
					wr.startElement("", "userdata", "", at);					
					wr.characters((String) designationName);						
					wr.endElement("userdata");

					wr.startElement("cell");
					wr.characters(wr.doubleEscape((String) designationName) + "^javascript:editRecord(" + designationId + ");^_self");
					wr.endElement("cell");	
					
					List<DesignationAliasesData> aliases = getDesignationAliases(designationId);
					StringBuffer sb = new StringBuffer();
					if (!aliases.isEmpty() && aliases!=null) {						
						for(int i=0;i<aliases.size();i++){
							DesignationAliasesData designationAliasData = aliases.get(i);
							sb.append(designationAliasData.getId().getAlias());
							if(i<aliases.size()-1){
								sb.append(", ");
							}
						}
					}

					at = new AttributesImpl();
					at.addAttribute("", "name", "", "", "alias");
					wr.startElement("", "userdata", "", at);
					wr.characters(sb.toString());
					wr.endElement("userdata");

					wr.startElement("cell");
					wr.characters(wr.doubleEscape(sb.toString().trim()));
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
	
	@SuppressWarnings("unchecked")
	@Override
	public void saveDesignation(String designationId, String designationName, String[] aliases){
		DesignationsData designationData = new DesignationsData();
		DesignationAliasesData designationAlData = null;
		DesignationAliasesId designationAlId = null;
		Set<DesignationAliasesData> setDesignationAlData = new HashSet<DesignationAliasesData>();
		List<DesignationsData> designationDataList = null;
		List<DesignationAliasesData> designationAliasesList = null;
		boolean dupEntry=false;
		try{
			designationDataList = _masterDAO.getDesignations();
			designationAliasesList = _masterDAO.getDesignationsAliases();
			for(DesignationsData dd:designationDataList){
				if(dd.getDesignationName().equalsIgnoreCase(designationName)){
					dupEntry=true;
					break;
				}
				for(String strAl:aliases){
					if(dd.getDesignationName().equalsIgnoreCase(strAl)){
						dupEntry=true;
						break;
					}
				}
			}
			for(DesignationAliasesData dad:designationAliasesList){
				if(dad.getId().getAlias().equalsIgnoreCase(designationName)){
					dupEntry = true;
					break;
				}
				for(String strAl:aliases){
					if(dad.getId().getAlias().equalsIgnoreCase(strAl)){
						dupEntry=true;
						break;
					}
				}
			}
			
			if(!dupEntry){
				designationData.setDesignationName(designationName);
				for(String str:aliases){
					if(!Utils.isBlankOrNull(str)){					
						designationAlId = new DesignationAliasesId(0,str);
						designationAlData =  new DesignationAliasesData(designationAlId,designationData);
						setDesignationAlData.add(designationAlData);
					}
				}
				designationData.setDesignationsAliasesesData(setDesignationAlData);			
				_masterDAO.saveDesignation(designationData);
			}
		}catch (HibernateException he) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, he);
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void updateDesignation(String designationId, String designationName, String[] aliases){
		DesignationsData designationData = new DesignationsData();
		Set<DesignationAliasesData> desAlSet =  new HashSet<DesignationAliasesData>();
		try{
			designationData.setDesignationId(Long.parseLong(designationId));
			designationData.setDesignationName(designationName);
			
			for(String str:aliases){
				if(!Utils.isBlankOrNull(str)){
					DesignationAliasesData desAliasesData = new DesignationAliasesData();
					DesignationAliasesId desAliasesesId = new DesignationAliasesId();
					desAliasesesId.setDesignationId(Long.parseLong(designationId));
					desAliasesesId.setAlias(str);
					desAliasesData.setId(desAliasesesId);
					desAliasesData.setDesignationsData(designationData);
					desAlSet.add(desAliasesData);
				}
			}
			designationData.setDesignationsAliasesesData(desAlSet);
			_masterDAO.updateDesignation(designationData);
		}catch (HibernateException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
	}
	
	public void deleteDesignation(String designationId){
		try{
			_masterDAO.deleteDesignation(Long.parseLong(designationId));
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
	}
	
	private Long[] convertToLongArray(String commaSeparatedStrIds){
		Long[] commaSeparatedLongIds = null;
		String[] arrStr = null;
		try{
			arrStr = commaSeparatedStrIds.split(",");
			commaSeparatedLongIds = new Long[arrStr.length];			
			for(int i=0;i<arrStr.length;i++){
				commaSeparatedLongIds[i]=Long.parseLong(arrStr[i]);
			}			
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,e);
		}
		return commaSeparatedLongIds;
	}
	

}