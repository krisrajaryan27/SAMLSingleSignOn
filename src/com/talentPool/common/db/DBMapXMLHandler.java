package com.talentPool.common.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;


import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBColumnMap;
import com.talentPool.common.db.DBConstants;
import com.talentPool.common.db.DQMetaData;
import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.common.utils.Utils;

/**
 * @author shivprasad
 * 
 * parses xml files where all queries are stored queries are stored in the following format
 * 
 * <dataquery name="dGetRole" dbquery="qGetRole" object="com.talentpool.user.roleData">
 * 
 * <attribute name="navLinkId" column="NavLink_ID" type="INT" />
 * 
 * <attribute name="navLinkTitle" column="NavLink_Title" type="STRING" />
 * 
 * </dataquery>
 * 
 * <dbquery name="qLoadNavLinksForRole"> select NavLink_ID, navLinkTitle from tnavlinks </dbquery>
 */

public final class DBMapXMLHandler extends DefaultHandler {
	private HashMap<String, String> _dbQueriesMap = new HashMap<String, String>();
	private HashMap<String, DQMetaData> _dqMetaDataMap = new HashMap<String, DQMetaData>();
	private String tmpDBQueryName;
	private boolean isWithinDBQueryTag = false;
	private boolean tmpIsDQMetaData;
	private DQMetaData tmpDQMetaData;
	private StringBuffer tmpDBQuerySB = new StringBuffer();
	private static DBMapXMLHandler xmlhandler = null;

	
	/**
	 * @return object of DBMapXMLHandler
	 * 
	 * Only one object of this class is created during the lifetime of an application  
	 */
	public static DBMapXMLHandler getInstance() {
		if (xmlhandler == null) {
			xmlhandler = new DBMapXMLHandler();
		}
		return (xmlhandler);
	}

	/**
	 * Constructor which is private Only one object of this class can be constructed and can be accessed using getInstance() method
	 */
	private DBMapXMLHandler() {
		try {
			String dbFolder = Utils.concatFilePath(TPApplicationProperties.getProperty("application.path"),TPApplicationProperties.getProperty("ui.dir"));
			dbFolder = Utils.concatFilePath(dbFolder, TPApplicationProperties.getProperty("dbmap.folder.relative"));
			File dirDbMaps = new File(dbFolder);
			//File Name filter is added just to intake xml files
			
			String[] children = dirDbMaps.list(new FilenameFilter() {
		        public boolean accept(File dir, String name) {
		        	if(name.endsWith(".xml"))
		        	return true;
		        	return false;
		        }
		      });
			
			DefaultHandler handler = this;
			System.setProperty("org.xml.sax.driver", "org.apache.xerces.parsers.SAXParser");
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser reader = factory.newSAXParser();
			if (children != null) {
				for (int i = 0; i < children.length; i++) {
					reader.parse(new FileInputStream(Utils.concatFilePath(dbFolder, children[i])), handler);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			TPLogger.getLogger().fatal("Exception while parsing XML", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
	 */
	public void characters(char[] buf, int offset, int len) throws SAXException {
		if (isWithinDBQueryTag) {
			tmpDBQuerySB.append(buf, offset, len);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#startDocument()
	 */
	public void startDocument() throws SAXException {
		super.startDocument();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#endDocument()
	 */
	public void endDocument() throws SAXException {
		super.endDocument();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	public void startElement(String namespaceURI, String localName, String fullName, Attributes attributes) throws SAXException {
		if (localName.trim().equals("")) {
			localName = fullName;
		}
		if (localName.equals("dbquery")) {
			tmpDBQueryName = attributes.getValue("name");
			isWithinDBQueryTag = true;
		}
		if (localName.equals("dataquery")) {
			tmpIsDQMetaData = true;
			tmpDQMetaData = new DQMetaData();
			tmpDQMetaData.setDqName(attributes.getValue("name"));
			tmpDQMetaData.setDbQueryName(attributes.getValue("dbquery"));
			tmpDQMetaData.setDataObjectClassName(attributes.getValue("object"));
			// Following code is commented as never used, kept for future reference
			// tmpDQMetaData.isSelfLoadable = "true".equals(attributes.getValue("selfloadable"));
			// tmpDQMetaData.tableName = attributes.getValue("table");
			tmpDQMetaData.setDbColumnMaps(new ArrayList());
		} else if (localName.equals("attribute")) {
			String columnName = attributes.getValue("column");
			String attributeName = attributes.getValue("name");
			DBColumnMap columnMap = new DBColumnMap(attributeName,columnName, getType(attributes.getValue("type")));
			// Following code is commented as not used
			/*
			 * if ("id".equals(attributeName)) tmpDQMetaData.identifier = columnName;
			 */
			if (tmpIsDQMetaData)
				tmpDQMetaData.getDbColumnMaps().add(columnMap);
		}
	}

	public static int getType(String s) {
		if ("STRING".equals(s))
			return DBConstants.STRING;
		else if ("INT".equals(s))
			return DBConstants.INT;
		else if ("ID".equals(s))
			return DBConstants.STRING;
		else if ("DATE".equals(s))
			return DBConstants.DATE;
		else if ("DATETIME".equals(s))
			return DBConstants.DATETIME;
		else
			return DBConstants.INT;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void endElement(String namespaceURI, String localName, String fullName) throws SAXException {
		if (localName.trim().equals("")) {
			localName = fullName;
		}
		if (localName.equals("dbquery")) {
			_dbQueriesMap.put(tmpDBQueryName, Utils.removeExtraWhiteSpace(tmpDBQuerySB.toString()));
			tmpDBQuerySB = new StringBuffer();
			isWithinDBQueryTag = false;
		}
		if (localName.equals("dataquery")) {
			_dqMetaDataMap.put(tmpDQMetaData.getDqName(), tmpDQMetaData);
		}
	}

	public DQMetaData getDQMetaData(String dQuery) {
		return _dqMetaDataMap.get(dQuery);
	}

	public String getDBQuery(String dbQuery) {
		return _dbQueriesMap.get(dbQuery);
	}

	/**
	 * @return Returns the _dbQueriesMap.
	 */
	public HashMap<String, String> get_dbQueriesMap() {
		return _dbQueriesMap;
	}

	/**
	 * @return Returns the _dqMetaDataMap.
	 */
	public HashMap<String, DQMetaData> get_dqMetaDataMap() {
		return _dqMetaDataMap;
	}

}