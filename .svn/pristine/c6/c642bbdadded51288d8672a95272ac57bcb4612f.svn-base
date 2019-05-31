/**
 * 
 */
package com.talentPool.otherApplications.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.otherApplications.constant.OtherApplicationConstants;
import com.talentPool.otherApplications.manager.KotakXMLFileProcessor;

/**
 * @author Shantanu
 *
 */
public class PostingDataToURL {
	
	/**
	 * The applicant XML file is converted to a string. Further the string is appended on the HRMS
	 * Link. and This link is  
	 * @param fileToPost
	 * @return
	 */
	public ArrayList<String> postFileToUrl(String fileToPost, String xmlType){
		String responseXML = null;
		String data = null;
		ArrayList<String> hrmsOrEmpCode = new ArrayList<String>();
		KotakXMLFileProcessor xfp = new KotakXMLFileProcessor(); 
		try{
			TPLogger.getLogger().debug("--------- POSTING DATA TO HRMS STARTS HERE -------");
						
			// Construct data by reading the xml file.
			byte[] buffer = new byte[(int) new File(fileToPost).length()];
			FileInputStream f = new FileInputStream(fileToPost);
			f.read(buffer);
			data = new String(buffer);
			TPLogger.getLogger().debug("before replace DATA === " + data);
			data = data.replace("\r\n","");
			TPLogger.getLogger().debug("after replace DATA === " + data);
			data = URLEncoder.encode(data, "UTF-8");
			TPLogger.getLogger().debug("Data after encoding"+data);
						
			String webLinkURL = OtherApplicationConstants.CUSTOMER_HRMS_URL;
			TPLogger.getLogger().debug("without xml webLinkURL = " + webLinkURL);
			webLinkURL = webLinkURL+data;
			TPLogger.getLogger().debug("without xml webLinkURL = " + webLinkURL);
			
		    // Send data
		    TPLogger.getLogger().debug("------ REQUEST DATA STARTS HERE ------- "+webLinkURL);
		    URL url = new URL(webLinkURL);
		    URLConnection conn = url.openConnection();
		    conn.setDoOutput(true);
		    conn.setDoInput(true);
		    
		    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		    wr.flush();

	    	//Get the response
		    TPLogger.getLogger().debug("--------- RESPONSE DATA STARTS -------");
	    	BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	    	StringBuffer sbf = new StringBuffer();
	    	
	    	while((responseXML = rd.readLine()) != null ){	    		
	    		sbf.append(responseXML.trim());
	    	}
	    	wr.close();
	    	rd.close();
	    	
	    	if(xmlType.equals(OtherApplicationConstants.KOTAK_XML_TYPE_HRMS_CODE)){
	    		String hrmsCode = xfp.extractCodeHRMSorEmployee(sbf.toString(),OtherApplicationConstants.KOTAK_HRMS_CODE_RES_XML_PAPPLICATIONNUMBER);
	    		hrmsOrEmpCode.add(0, hrmsCode);
	    		hrmsOrEmpCode.add(1, null);
	    	}else if (xmlType.equals(OtherApplicationConstants.KOTAK_XML_TYPE_EMPLOYEE_CODE)) {
	    		String empCode = xfp.extractCodeHRMSorEmployee(sbf.toString(),OtherApplicationConstants.KOTAK_EMPLOYEE_CODE_RES_XML_EMPLOYEENUMBER);
	    		hrmsOrEmpCode.add(0, empCode);
	    		hrmsOrEmpCode.add(1, null);
	    	}    	
	    	
		}catch (MalformedURLException mue) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,mue);
			hrmsOrEmpCode.add(0, null);
			hrmsOrEmpCode.add(1, mue.toString());
			return hrmsOrEmpCode;
		}catch (Exception e) {			
			TPLogger.getLogger().error(GlobalConstants.ERROR,e);
			hrmsOrEmpCode.add(0, null);
			hrmsOrEmpCode.add(1, e.toString());
			return hrmsOrEmpCode;
		}
		return hrmsOrEmpCode;
	}	
}