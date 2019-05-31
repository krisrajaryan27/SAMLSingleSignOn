/**
 * 
 */
package com.talentPool.common.xsl;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.util.HashMap;

import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.common.utils.Utils;


/**
 * @author Ajeet
 *
 */
public class XSLTransformer {

	
	public static String XSL_SELECT_FILTER = "select_filter.xsl";
	public static String XSL_DASHBOARD_TODO_SUMMARY = "dashboard_todo_summary.xsl";
	public static String XSL_DASHBOARD_TODO_DETAILS = "dashboard_todo_details.xsl";
	public static String XSL_DASHBOARD_POSITION_SUMMARY = "dashboard_position_summary.xsl";
	public static String XSL_DASHBOARD_POSITION_DETAILS = "dashboard_position_details.xsl";
	
	public static HashMap<String, Templates> cachedXSLT = new HashMap<String, Templates>();
	static {
		try {
			loadXSLTemplates();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
	}

	public static synchronized String getTransformedXMLusingXSL(String xml, String xsl) throws Exception {
		//loadXSLTemplates();
		Source xmlSource = new StreamSource(new ByteArrayInputStream(xml.getBytes()));
		Transformer transformer = cachedXSLT.get(xsl).newTransformer();
		StringWriter swriter = new StringWriter();
		transformer.transform(xmlSource, new StreamResult(swriter));
		return swriter.toString();

	}

	private static void loadXSLTemplates() {
		try {
			String path = Utils.concatFilePath(TPApplicationProperties.getProperty("application.path"), TPApplicationProperties.getProperty("ui.dir"));
			String XSLDIR_PATH = Utils.concatFilePath(path, "xsl");
			TransformerFactory transFact = TransformerFactory.newInstance();
			Templates template = transFact.newTemplates(new StreamSource(Utils.concatFilePath(XSLDIR_PATH, XSL_SELECT_FILTER)));
			cachedXSLT.put(XSL_SELECT_FILTER, template);
			template = transFact.newTemplates(new StreamSource(Utils.concatFilePath(XSLDIR_PATH, XSL_DASHBOARD_TODO_SUMMARY)));
			cachedXSLT.put(XSL_DASHBOARD_TODO_SUMMARY, template);
			template = transFact.newTemplates(new StreamSource(Utils.concatFilePath(XSLDIR_PATH, XSL_DASHBOARD_TODO_DETAILS)));
			cachedXSLT.put(XSL_DASHBOARD_TODO_DETAILS, template);
			template = transFact.newTemplates(new StreamSource(Utils.concatFilePath(XSLDIR_PATH, XSL_DASHBOARD_POSITION_SUMMARY)));
			cachedXSLT.put(XSL_DASHBOARD_POSITION_SUMMARY, template);
			template = transFact.newTemplates(new StreamSource(Utils.concatFilePath(XSLDIR_PATH, XSL_DASHBOARD_POSITION_DETAILS)));
			cachedXSLT.put(XSL_DASHBOARD_POSITION_DETAILS, template);

		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
	}
}

