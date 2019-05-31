/**
 * 
 */
package com.talentPool.otherApplications.manager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.talentPool.admin.AdminConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.constants.DateConstants;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.DateUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.custom.constants.CustomFieldConstants;
import com.talentPool.custom.dataobject.CustomFieldData;
import com.talentPool.custom.manager.CustomFieldManager;
import com.talentPool.otherApplications.constant.OtherApplicationConstants;

/**
 * @author Shantanu
 *
 */
public class KotakXMLFileProcessor {
	
	/**
	 * This is only used for reading kotak specific xml request file.
	 * The kotak Req XML file is saved in others folder.
	 * Following method will read its elements and populate the data with Talentpool Specific Data.
	 */
	public boolean createKotakXML(String dirXMLFile, String dstXMLFile, SimpleDataObject applicantDetail, String xmlType){
		boolean isFileSaved = false;
		File srcFile = null, dstFile=null;
		InputStream in =null;
		String srcXMLFile = null;
		OutputStream out = null;
		byte[] buf = new byte[1024];
		int len;
		try{
			if(xmlType.equals(OtherApplicationConstants.KOTAK_XML_TYPE_HRMS_CODE)){
				srcXMLFile = Utils.concatFilePath(dirXMLFile,OtherApplicationConstants.KOTAK_HRMS_CODE_REQ_XML);
			}else if (xmlType.equals(OtherApplicationConstants.KOTAK_XML_TYPE_EMPLOYEE_CODE)) {
				srcXMLFile = Utils.concatFilePath(dirXMLFile,OtherApplicationConstants.KOTAK_EMPLOYEE_CODE_REQ_XML);
			}
			srcFile = new File(srcXMLFile);
			dstFile = new File(dstXMLFile);
			in = new FileInputStream(srcFile);
			out = new FileOutputStream(dstFile);			
			
			while ((len = in.read(buf)) > 0){
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();			
			dbf.setCoalescing(true);
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(dstFile);				
					
			KotakXMLFileProcessor obj = new KotakXMLFileProcessor();
			String todayDate =  DateUtils.getDateFormated(applicantDetail.getDate("today_date"),DateConstants.yyyyMMdd);
			
			if(xmlType.equals(OtherApplicationConstants.KOTAK_XML_TYPE_HRMS_CODE)){
				//Header Tags
				obj.changeXMLElementValue(doc, OtherApplicationConstants.KOTAK_HRMS_CODE_REQ_XML_IDREQUEST, Utils.isBlankOrNull(applicantDetail.getString("applicant_id"))?"":applicantDetail.getString("applicant_id"), OtherApplicationConstants.KOTAK_HRMS_CODE_REQ_XML_HEADER);
				obj.changeXMLElementValue(doc, OtherApplicationConstants.KOTAK_HRMS_CODE_REQ_XML_DATPOST, todayDate, OtherApplicationConstants.KOTAK_HRMS_CODE_REQ_XML_HEADER);
				obj.changeXMLElementValue(doc, OtherApplicationConstants.KOTAK_HRMS_CODE_REQ_XML_REFERENCENO, Utils.isBlankOrNull(applicantDetail.getString("applicant_id"))?"":applicantDetail.getString("applicant_id"), OtherApplicationConstants.KOTAK_HRMS_CODE_REQ_XML_HEADER);
				obj.changeXMLElementValue(doc, OtherApplicationConstants.KOTAK_HRMS_CODE_REQ_XML_EXTSYSTEM, OtherApplicationConstants.KOTAK_HRMS_CODE_REQ_XML_EXTSYSTEM_CONSTANT, OtherApplicationConstants.KOTAK_HRMS_CODE_REQ_XML_HEADER);
				//Body Tags
				obj.changeXMLElementValue(doc, OtherApplicationConstants.KOTAK_HRMS_CODE_REQ_XML_PVALIDATE, "0", OtherApplicationConstants.KOTAK_HRMS_CODE_REQ_XML_BODY);
				obj.changeXMLElementValue(doc, OtherApplicationConstants.KOTAK_HRMS_CODE_REQ_XML_PDATERECEIVED, todayDate, OtherApplicationConstants.KOTAK_HRMS_CODE_REQ_XML_BODY);
				obj.changeXMLElementValue(doc, OtherApplicationConstants.KOTAK_HRMS_CODE_REQ_XML_PBUSINESSGROUPID, Utils.isBlankOrNull(applicantDetail.getString("dept_name"))?"41":((applicantDetail.getString("dept_name")).equals("KMBL")?"41":"56"), OtherApplicationConstants.KOTAK_HRMS_CODE_REQ_XML_BODY);
				//obj.changeXMLElementValue(doc, OtherApplicationConstants.KOTAK_HRMS_CODE_REQ_XML_PLASTNAME, Utils.isBlankOrNull(applicantDetail.getString("applicant_name"))?"":applicantDetail.getString("applicant_name"), OtherApplicationConstants.KOTAK_HRMS_CODE_REQ_XML_BODY);
				String personTypeId = OtherApplicationConstants.KOTAK_HRMS_CODE_REQ_XML_PPERSONTYPEID_CONSTANT;
				if(!Utils.isBlankOrNull(applicantDetail.getString("dept_name")) && applicantDetail.getString("dept_name").equals("KMPL")) {
					personTypeId = OtherApplicationConstants.KOTAK_HRMS_CODE_REQ_XML_PPERSONTYPEID_KMPL;
				}
				obj.changeXMLElementValue(doc, OtherApplicationConstants.KOTAK_HRMS_CODE_REQ_XML_PPERSONTYPEID, personTypeId, OtherApplicationConstants.KOTAK_HRMS_CODE_REQ_XML_BODY);
				
				if(!Utils.isBlankOrNull(applicantDetail.getString("applicant_cell_phone"))) {
					obj.changeXMLElementValue(doc, OtherApplicationConstants.KOTAK_HRMS_CODE_REQ_XML_PPHONETYPE, OtherApplicationConstants.KOTAK_HRMS_CODE_REQ_XML_PPHONETYPEMOBILE, OtherApplicationConstants.KOTAK_HRMS_CODE_REQ_XML_BODY);
					obj.changeXMLElementValue(doc, OtherApplicationConstants.KOTAK_HRMS_CODE_REQ_XML_PPHONENUMBER, Utils.isBlankOrNull(applicantDetail.getString("applicant_cell_phone"))?"":applicantDetail.getString("applicant_cell_phone"), OtherApplicationConstants.KOTAK_HRMS_CODE_REQ_XML_BODY);
				} else {
					obj.changeXMLElementValue(doc, OtherApplicationConstants.KOTAK_HRMS_CODE_REQ_XML_PPHONETYPE, OtherApplicationConstants.KOTAK_HRMS_CODE_REQ_XML_PPHONETYPERESIDENTIAL, OtherApplicationConstants.KOTAK_HRMS_CODE_REQ_XML_BODY);
					obj.changeXMLElementValue(doc, OtherApplicationConstants.KOTAK_HRMS_CODE_REQ_XML_PPHONENUMBER, Utils.isBlankOrNull(applicantDetail.getString("applicant_home_phone"))?"":applicantDetail.getString("applicant_home_phone"), OtherApplicationConstants.KOTAK_HRMS_CODE_REQ_XML_BODY);
				}
				
				obj.changeXMLElementValue(doc, OtherApplicationConstants.KOTAK_HRMS_CODE_REQ_XML_PEMAILADDRESS, Utils.isBlankOrNull(applicantDetail.getString("applicant_email1"))?"":applicantDetail.getString("applicant_email1"), OtherApplicationConstants.KOTAK_HRMS_CODE_REQ_XML_BODY);
				obj.changeXMLElementValue(doc, OtherApplicationConstants.KOTAK_HRMS_CODE_REQ_XML_PCURRENTEMPLOYER, Utils.isBlankOrNull(applicantDetail.getString("applicant_current_employer"))?"":applicantDetail.getString("applicant_current_employer"), OtherApplicationConstants.KOTAK_HRMS_CODE_REQ_XML_BODY);
				obj.changeXMLElementValue(doc, OtherApplicationConstants.KOTAK_HRMS_CODE_REQ_XML_PDEPARTMENT, Utils.isBlankOrNull(applicantDetail.getString("dept_name"))?"":applicantDetail.getString("dept_name"), OtherApplicationConstants.KOTAK_HRMS_CODE_REQ_XML_BODY);
				obj.changeXMLElementValue(doc, OtherApplicationConstants.KOTAK_HRMS_CODE_REQ_XML_PPROJECTEDHIREDATE, Utils.isBlankOrNull(applicantDetail.getString("applicant_date_joined"))?"":applicantDetail.getString("applicant_date_joined"), OtherApplicationConstants.KOTAK_HRMS_CODE_REQ_XML_BODY);
				obj.changeXMLElementValue(doc, OtherApplicationConstants.KOTAK_HRMS_CODE_REQ_XML_PTARGETCTC, Utils.isBlankOrNull(applicantDetail.getString("offered_ctc"))?"":applicantDetail.getString("offered_ctc"), OtherApplicationConstants.KOTAK_HRMS_CODE_REQ_XML_BODY);
				obj.changeXMLElementValue(doc, OtherApplicationConstants.KOTAK_HRMS_CODE_REQ_XML_PPOSITIONBUSINESSUNIT, Utils.isBlankOrNull(applicantDetail.getString("position_business_unit"))?"":applicantDetail.getString("position_business_unit"), OtherApplicationConstants.KOTAK_HRMS_CODE_REQ_XML_BODY);
				obj.changeXMLElementValue(doc, OtherApplicationConstants.KOTAK_HRMS_CODE_REQ_XML_PEFFECTIVEDATE, Utils.isBlankOrNull(applicantDetail.getString("applicant_date_created"))?"":DateUtils.getDateFormated(applicantDetail.getDate("applicant_date_created"),DateConstants.yyyyMMdd), OtherApplicationConstants.KOTAK_HRMS_CODE_REQ_XML_BODY);
				
				// Populate vendor name tag with source for all sources except for Employee Referrals and Company Web Site
				String vendor = "";				
				String source = Utils.getBlankIfNull(applicantDetail.getString("source_title"));
				String sourceCategory = Utils.getBlankIfNull(applicantDetail.getString("source_type_category"));
				
				if(!sourceCategory.equals(AdminConstants.SOURCE_CATEGORY_EMPLOYEE_REFERAL)
						&& !sourceCategory.equals(AdminConstants.SOURCE_CATEGORY_WEB_SITE)) {
					vendor = source;
					source = Utils.getBlankIfNull(applicantDetail.getString("source_type"));
				}
				obj.changeXMLElementValue(doc, OtherApplicationConstants.KOTAK_HRMS_CODE_REQ_XML_PSOURCETYPE, source, OtherApplicationConstants.KOTAK_HRMS_CODE_REQ_XML_BODY);
				obj.changeXMLElementValue(doc, OtherApplicationConstants.KOTAK_HRMS_CODE_REQ_XML_PVENDORNAME, vendor, OtherApplicationConstants.KOTAK_HRMS_CODE_REQ_XML_BODY);
				
				obj.addXMLCustomFields(doc, obj, applicantDetail.getString("applicant_id"));
				
				obj.saveXMLFile(dstFile,doc);
			}else if (xmlType.equals(OtherApplicationConstants.KOTAK_XML_TYPE_EMPLOYEE_CODE)) {
				obj.changeXMLElementValue(doc, OtherApplicationConstants.KOTAK_EMPLOYEE_CODE_REQ_XML_DATPOST, todayDate, OtherApplicationConstants.KOTAK_EMPLOYEE_CODE_REQ_XML_HEADER);
				obj.changeXMLElementValue(doc, OtherApplicationConstants.KOTAK_EMPLOYEE_CODE_REQ_XML_EXTSYSTEM, OtherApplicationConstants.KOTAK_HRMS_CODE_REQ_XML_EXTSYSTEM_CONSTANT, OtherApplicationConstants.KOTAK_EMPLOYEE_CODE_REQ_XML_HEADER);
				obj.changeXMLElementValue(doc, OtherApplicationConstants.KOTAK_EMPLOYEE_CODE_REQ_XML_HRMSCODE, Utils.isBlankOrNull(applicantDetail.getString("applicantHrmsCode"))?"":applicantDetail.getString("applicantHrmsCode"), OtherApplicationConstants.KOTAK_EMPLOYEE_CODE_REQ_XML_BODY);
				obj.changeXMLElementValue(doc, OtherApplicationConstants.KOTAK_EMPLOYEE_CODE_REQ_XML_BUSINESSGROUPID, Utils.isBlankOrNull(applicantDetail.getString("dept_name"))?"41":((applicantDetail.getString("dept_name")).equals("KMBL")?"41":"56"), OtherApplicationConstants.KOTAK_EMPLOYEE_CODE_REQ_XML_BODY);
				obj.saveXMLFile(dstFile,doc);
			}
			
			if(dstFile.exists()){
				isFileSaved=true;
			}
		}catch (FileNotFoundException fnfe) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,fnfe);
		}catch (IOException ioe) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,ioe);
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,e);
		}
		return isFileSaved;
	}

	private void changeXMLElementValue(Document doc, String oldValue, String NewValue, String elementTagName) throws Exception {
		Element root = doc.getDocumentElement();
		NodeList childNodes = root.getElementsByTagName(elementTagName);

		for (int i = 0; i < childNodes.getLength(); i++) {
			NodeList subChildNodes = childNodes.item(i).getChildNodes();
			for (int j = 0; j < subChildNodes.getLength(); j++) {
				try {
					if (subChildNodes.item(j).getTextContent().equals(oldValue)) {
						subChildNodes.item(j).setTextContent(NewValue);
					}
				} catch (Exception e) {
					TPLogger.getLogger().error(GlobalConstants.ERROR,e);
				}
			}
		}
	}

	/**
	 * @param Document doc
	 * @param KotakXMLFileProcessor obj
	 * @param applicantId
	 */
	private void addXMLCustomFields(Document doc, KotakXMLFileProcessor obj, String applicantId) {
		ArrayList<CustomFieldData> cDataList = new ArrayList<CustomFieldData>();
		CustomFieldManager cusMngr = new CustomFieldManager(); 
		cDataList = cusMngr.getCustomFieldDataForEntity(applicantId, CustomFieldConstants.ENTITY_TYPE_APPLICANT);
		
		/*Element root = doc.getDocumentElement();
		NodeList childNodes;
		Node cusNode, tempNode, parentNode;*/
		
		try {
			String oldValue = "";
			for (CustomFieldData cData : cDataList) {
				oldValue = OtherApplicationConstants.KOTAK_HRMS_CODE_REQ_XML_PCUSTOMFIELD
						.concat(cData.getFieldName().substring(4));
				String newValue = "";
				if (cData.getFieldType().equals(CustomFieldConstants.TYPE_NUMBER))
					newValue = cData.getFieldNumberValueInString();
				else if (cData.getFieldType().equals(CustomFieldConstants.TYPE_DATE))
					newValue = cData.getFieldDateValue().toString();
				else
					newValue = cData.getFieldStringValue();

				obj.changeXMLElementValue(doc, oldValue, newValue,
						OtherApplicationConstants.KOTAK_HRMS_CODE_REQ_XML_BODY);
				/*
				 * childNodes =
				 * root.getElementsByTagName(OtherApplicationConstants
				 * .KOTAK_HRMS_CODE_REQ_XML_PCUSTOMFIELD); cusNode =
				 * childNodes.item(0);
				 * 
				 * tempNode = cusNode.cloneNode(true); parentNode =
				 * cusNode.getParentNode(); parentNode.insertBefore(tempNode,
				 * cusNode); doc.renameNode(cusNode, null, "p_" +
				 * cData.getFieldName().substring(4));
				 */

			}
			clearCustomFieldXMLValues(doc);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		/*childNodes = root.getElementsByTagName(OtherApplicationConstants.KOTAK_HRMS_CODE_REQ_XML_PCUSTOMFIELD);
		cusNode = childNodes.item(0);
		
		if(cusNode != null)
			cusNode.getParentNode().removeChild(cusNode);*/
		
	}
	
	/**
	 * @param Document doc
	 * @throws Exception
	 */
	private void clearCustomFieldXMLValues(Document doc) throws Exception {
		Element root = doc.getDocumentElement();
		NodeList childNodes = root.getElementsByTagName(OtherApplicationConstants.KOTAK_HRMS_CODE_REQ_XML_BODY);

		for (int i = 0; i < childNodes.getLength(); i++) {
			NodeList subChildNodes = childNodes.item(i).getChildNodes();
			for (int j = 0; j < subChildNodes.getLength(); j++) {
				try {
					if (subChildNodes.item(j).getTextContent().startsWith(OtherApplicationConstants.KOTAK_HRMS_CODE_REQ_XML_PCUSTOMFIELD)) {
						subChildNodes.item(j).setTextContent("");
					}
				} catch (Exception e) {
					TPLogger.getLogger().error(GlobalConstants.ERROR,e);
				}
			}
		}
	}

	private void saveXMLFile(File file ,Document doc){		
		try{
			TransformerFactory factory1 = TransformerFactory.newInstance();
			Transformer transformer = factory1.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);
			DOMSource source = new DOMSource(doc);
			transformer.transform(source, result);
			String s = writer.toString();
			System.out.println(s);

			FileWriter fileWriter = new FileWriter(file);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

			bufferedWriter.write(s);

			bufferedWriter.flush();
			bufferedWriter.close();					
		}catch (IOException ioe) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,ioe);
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,e);
		}		
	}
	
	/**
	 * This code will extract the HRMS Code from Application_Creation response 
	 * Specific to KOTAK response
	 * @param responseXml
	 * @return
	 * elementTagName = OtherApplicationConstants.KOTAK_HRMS_CODE_RES_XML_PAPPLICATIONNUMBER
	 * 					OtherApplicationConstants.KOTAK_
	 */
	public String extractCodeHRMSorEmployee(String responseXml, String elementTagName){
		String hrmsCode=null;
		try{			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		    DocumentBuilder db = dbf.newDocumentBuilder();
		    InputSource is = new InputSource();
		    is.setCharacterStream(new StringReader(responseXml));
		    Document doc = db.parse(is);
		    NodeList name1 = doc.getElementsByTagName(elementTagName);
	        Element line1 = (Element) name1.item(0);
	        Node child1 = line1.getFirstChild();
	        hrmsCode = child1.getNodeValue();
		    		     
		}catch (Exception e) {
			TPLogger.getLogger().debug(GlobalConstants.ERROR,e);
		}
		return hrmsCode;
	}
	
}