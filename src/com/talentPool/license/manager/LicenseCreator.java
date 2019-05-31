/**
 * 
 */
package com.talentPool.license.manager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.talentPool.license.exception.LicenseException;
import com.talentPool.license.utils.HexToString;
import com.talentPool.license.utils.StringToHex;

import java.io.File;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException; 

/**
 * @author pallavi
 * @date Dec 7, 2006
 */
public class LicenseCreator {
	private static final String SWITCH_HELP = "-help";
	private static final String SWITCH_RUN = "-run";
	private static final String PIPE = "|";
	private static final String LICENSE_FILE = "License.lic";
	public static void main(String[] args) throws LicenseException {
		System.out.println("LicenseCreator");
		LicenseCreator licenseCreator = new LicenseCreator();
		args= new String[3];
		args[0] = SWITCH_RUN;
		args[1]="D:/Project/license/installationKey.dat";
		args[2]="D:/Project/license/LicenseENTPlus.xml";
		if (args == null || args.length > 3) {
			licenseCreator.printHelp();
		} else if (args.length == 1 && args[0].equalsIgnoreCase(SWITCH_HELP)) {
			licenseCreator.printHelp();
		} else if (args.length == 3 && args[0].equalsIgnoreCase(SWITCH_RUN)) {			
			// Create License.
			/*
			 * args[1] is the path to dat file.
			 * args[2] is the path to xml file.
			 */
			String installationKeyString = licenseCreator.getInstallationKeyString(args[1]); 
			String additionalAttributeString = licenseCreator.parseXml(args[2]);
			String licenseString = StringToHex.convert(installationKeyString + PIPE + additionalAttributeString);
			licenseCreator.createLicense(licenseString);
		} else {
			licenseCreator.printHelp();
		}
	}
	
	 public void createLicense(String installationKeyString,String licenseEntPath) throws LicenseException{
		 LicenseCreator licenseCreator = new LicenseCreator();
		 String additionalAttributeString = licenseCreator.parseXml(licenseEntPath);
		 String licenseString = StringToHex.convert(installationKeyString + PIPE + additionalAttributeString);
			licenseCreator.createLicense(licenseString);
	 }
	private void printHelp() {
		System.out.println("********************************************************************************");
		System.out.println("Usage:");
		System.out.println("java LicenseCreator [-run <dat-file-name> <xml-file-name>] [-help]\n");
		System.out.println("where,");
		System.out.println("-run:\n    Run the license creation utility.\n");
		System.out.println("<dat-file-name>:\n    The path to dat file containing the installation information.\n");
		System.out.println("<xml-file-name>:\n    The path to xml file containing the additional license attributes.\n");
		System.out.println("-help:\n    Print the help.\n");
		System.out.println("********************************************************************************");
	}
	
	private String getInstallationKeyString(String fileName) throws LicenseException {		
		StringBuffer fileData = new StringBuffer(1000);
		String installationKeyString = null;
		try {			
	        BufferedReader reader = new BufferedReader(new FileReader(fileName));
	        char[] buf = new char[1024];
	        int numRead=0;
	        while((numRead=reader.read(buf)) != -1){
	            String readData = String.valueOf(buf, 0, numRead);
	            fileData.append(readData);
	            buf = new char[1024];
	        }
	        reader.close();
	        installationKeyString = HexToString.convert(fileData.toString());
		} catch (FileNotFoundException e) {
			throw new LicenseException("The file " + fileName + " is not found.", e);
		} catch (IOException e) {
			throw new LicenseException(e);
		} 
        return installationKeyString;
	}
	
	private String parseXml(String filaName) throws LicenseException {
		StringBuffer additionalAttributes = new StringBuffer();
		try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse (new File(filaName));

            // normalize text representation
            doc.getDocumentElement().normalize();

            NodeList nodes = doc.getElementsByTagName("license");

            Node licenseNode = nodes.item(0);
            if(licenseNode.getNodeType() == Node.ELEMENT_NODE){
                Element license = (Element)licenseNode;

                NodeList noOfDays = license.getElementsByTagName("no-of-days");                	
                Element firstNameElement = (Element)noOfDays.item(0);                	

                NodeList textNoOfDays = firstNameElement.getChildNodes();
                additionalAttributes.append(((Node)textNoOfDays.item(0)).getNodeValue().trim());

                additionalAttributes.append(PIPE);
                
                NodeList lastNameList = license.getElementsByTagName("license-type");
                Element lastNameElement = (Element)lastNameList.item(0);

                NodeList textLicenseType = lastNameElement.getChildNodes();
                additionalAttributes.append(((Node)textLicenseType.item(0)).getNodeValue().trim());
                
                //Started At : Shantanu Sikdar
                //Desc: for adding the License Version, modules and report-exceptions
                
                additionalAttributes.append(PIPE);
                
                NodeList licenseVersionList = license.getElementsByTagName("license-version");                
                Element licenseVersionElement = (Element)licenseVersionList.item(0);                

                NodeList textLicenseVersion = licenseVersionElement.getChildNodes();                
                additionalAttributes.append(((Node)textLicenseVersion.item(0)).getNodeValue().trim());
                                
                additionalAttributes.append(PIPE);
                
                NodeList modulesList = license.getElementsByTagName("modules");
                Element modulesElement = (Element)modulesList.item(0);

                NodeList textModules = modulesElement.getChildNodes();
                additionalAttributes.append(((Node)textModules.item(0)).getNodeValue().trim());
                
                additionalAttributes.append(PIPE);
                
                NodeList reportExceptionsList = license.getElementsByTagName("report-exceptions");
                Element reportExceptionsElement = (Element)reportExceptionsList.item(0);

                NodeList textReportExceptions = reportExceptionsElement.getChildNodes();
                additionalAttributes.append(((Node)textReportExceptions.item(0)).getNodeValue().trim());
               
                
                //End At : Shantanu Sikdar
                
            }
        } catch (SAXParseException e) {
        	throw new LicenseException(e);
        } catch (SAXException e) {
        	throw new LicenseException(e);
        } catch (ParserConfigurationException e) {
        	throw new LicenseException(e);
		} catch (IOException e) {
			throw new LicenseException(e);
		}
		return additionalAttributes.toString();
	}
	
	private final void createLicense(String licenseString) throws LicenseException {
		try {
			FileWriter writer = new FileWriter(LICENSE_FILE);
			writer.write(licenseString);
			writer.close();
		} catch (IOException e) {
			throw new LicenseException(e);
		}
	}
}
