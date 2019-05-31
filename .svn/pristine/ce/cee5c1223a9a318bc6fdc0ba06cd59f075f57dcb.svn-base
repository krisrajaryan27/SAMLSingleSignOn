/**
 * Copyright 2009- Talentica Software Pvt. Ltd. All Rights Reserved. 
 * This software is the proprietary information of Talentica Software Pvt. Ltd. 
 * Use is subject to license terms.
 */
package com.talentPool.common.Logger;

/**
 * @author anikets
 * 
 */
import java.io.File;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;

import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.common.utils.Utils;

public class TPLog4JInitServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) throws ServletException {
		System.out.println("Log4JInitServlet is initializing log4j");
		String log4jLocation = config.getInitParameter("log4j-properties-location");
		long watchInterval = 2000; // TODO:: Get it from talentpool properties file
		ServletContext sc = config.getServletContext();

		if (log4jLocation == null) {
			// Try initializing from config folder first
			String configPath = "";
			configPath = Utils.concatFilePath(TPApplicationProperties.getProperty("installation.path"), TPApplicationProperties.getProperty("config.dir"));
			if(!Utils.isBlankOrNull(configPath)) {
				configPath = Utils.concatFilePath(configPath, "log4j.properties");
			}
			File log4jPropFile = new File(configPath);
			if (log4jPropFile.exists()) {
				System.out.println("Initializing log4j with: " + configPath);
				PropertyConfigurator.configureAndWatch(configPath, watchInterval);
			} else {
				System.err.println("*** " + configPath + " file not found, so initializing log4j with BasicConfigurator");
				BasicConfigurator.configure();
			}
		} else {			
			String webAppPath = sc.getRealPath("/");
			String log4jProp = webAppPath + log4jLocation;
			File yoMamaYesThisSaysYoMama = new File(log4jProp);
			if (yoMamaYesThisSaysYoMama.exists()) {
				System.out.println("Initializing log4j with: " + log4jProp);
				PropertyConfigurator.configureAndWatch(log4jProp, watchInterval);
			} else {
				String configPath = "";
				configPath = Utils.concatFilePath(TPApplicationProperties.getProperty("installation.path"), TPApplicationProperties.getProperty("config.dir"));
				if(!Utils.isBlankOrNull(configPath)) {
					configPath = Utils.concatFilePath(configPath, "log4j.properties");
				}
				File log4jPropFile = new File(configPath);
				if (log4jPropFile.exists()) {
					System.out.println("Initializing log4j with: " + configPath);
					PropertyConfigurator.configureAndWatch(configPath, watchInterval);
				} else {
					System.err.println("*** " + configPath + " file not found, so initializing log4j with BasicConfigurator");
					BasicConfigurator.configure();
				}
			}
		}
		super.init(config);
	}
}