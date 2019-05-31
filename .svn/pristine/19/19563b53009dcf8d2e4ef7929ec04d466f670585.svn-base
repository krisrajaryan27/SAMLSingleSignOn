package com.talentPool.customReports.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.customReports.jaxb.CustomReport;
import com.talentPool.customReports.jaxb.ObjectFactory;
import com.talentPool.customReports.service.ICRXMLService;

/**
 * DynamicReport XML Service which uses JAXB generates XML report template 
 * which will have all details need to generate the report. Details include:
 * <li>Report title, sub title etc..</li>
 * <li>Columns and their properties.</li>
 * <li>Grouping details in any present.</li>
 * <li>Sorting details in any present.</li>
 * @author PraveenK
 * @since  Nov 7, 2011
 */
public class JaxbXMLService implements ICRXMLService {

	@Override
	public void saveReportXMLFile(CustomReport customReport, String filePath) throws Exception {
		generateXMLFile(customReport, filePath);
	}

	@Override
	public CustomReport getCustomReport(String xmlFilepath) {
		CustomReport customReport	= null;
		try {
			JAXBContext jc = JAXBContext.newInstance("com.talentPool.customReports.jaxb");
			Unmarshaller um = jc.createUnmarshaller();
			File file = new File(xmlFilepath);
			JAXBElement<CustomReport> rt = (JAXBElement) um.unmarshal(file);
			customReport = rt.getValue();
		}catch (JAXBException jbe) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, jbe);
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return customReport;
	}
	
	protected void generateXMLFile(CustomReport customReport, String filePath) throws JAXBException, IOException{
		ObjectFactory of = new ObjectFactory();
		FileOutputStream os = null;
		try {
			JAXBElement<CustomReport> report = of.createReport(customReport);
			JAXBContext jc = JAXBContext.newInstance("com.talentPool.customReports.jaxb");
			Marshaller m = jc.createMarshaller();
			os = new FileOutputStream(new File(filePath));
			m.marshal(report, os);
			os.flush();				
		} catch (FileNotFoundException fnf) {
			throw fnf;
		} 
	}
}
