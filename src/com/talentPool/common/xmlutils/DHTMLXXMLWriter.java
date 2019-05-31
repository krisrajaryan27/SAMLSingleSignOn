/**
 * 
 */
package com.talentPool.common.xmlutils;

import java.io.Writer;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.talentPool.common.constants.DHTMLXXMLWriterConstants;
import com.talentPool.common.dataobject.DHTMLXUserData;

/**
 * @author PraveenK
 * @since  Nov 15, 2011
 */
public class DHTMLXXMLWriter extends XMLWriter implements DHTMLXXMLWriterConstants {
	
	
	public DHTMLXXMLWriter(Writer writer) {
		super(writer);
	}
	
	public void createDHTMLXRow(String rowId, String[] cellValues) throws SAXException {
		createDHTMLXRow(rowId, null, cellValues);
	}

	
	public void createDHTMLXRow(String rowId, DHTMLXUserData[] userData, String[] cellValues) throws SAXException {
		AttributesImpl atr = new AttributesImpl();					
		atr.addAttribute("", ID, "", "", rowId);
		startElement("", ROW, "", atr);
		writeUserData(userData);
		writeCellValues(cellValues);
		endElement(ROW);	
	}
	
	public void writeUserData(DHTMLXUserData[] userData) throws SAXException{
		if(userData!=null){
			AttributesImpl atr = new AttributesImpl();
			for (DHTMLXUserData userDataObj : userData) {
				atr = new AttributesImpl();
				atr.addAttribute("", NAME, "", "", userDataObj.getUserDataName());
				startElement("", USER_DATA, "", atr);					
				characters(userDataObj.getUserDataValue());
				endElement(USER_DATA);			
			}
		}
	}
	
	public void writeCellValues(String[] cellValues) throws SAXException{
		for (String cellValue : cellValues) {
			startElement(CELL);
			characters(cellValue);
			endElement(CELL);	
		}
	}
	
	public void start() throws SAXException {
		startDocument();
		startElement(DHTMLXXMLWriterConstants.ROWS);		
	}
	
	public void end() throws SAXException {
		endElement(DHTMLXXMLWriterConstants.ROWS);
		endDocument();		
	}

}
