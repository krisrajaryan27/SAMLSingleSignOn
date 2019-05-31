/**
 * 
 */
package com.talentPool.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

/**
 * @author pallavi
 *
 */
public class PropsUtils {
	public PropsUtils() {
		// TODO Auto-generated constructor stub
	}
    /**
     * Load a properties file from the classpath
     * @param propsName
     * @return Properties
     * @throws Exception
     */
    public Properties load(String propsName) throws Exception {
        Properties props = new Properties();
        URL url = this.getClass().getClassLoader().getResource(propsName);
        props.load(url.openStream());
        return props;
    }

    /**
     * Load a Properties File
     * @param propsFile
     * @return Properties
     * @throws IOException
     */
    public Properties load(File propsFile) throws IOException {
        Properties props = new Properties();
        FileInputStream fis = new FileInputStream(propsFile);
        props.load(fis);    
        fis.close();
        return props;
    }
}
