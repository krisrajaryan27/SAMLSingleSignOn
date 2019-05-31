package com.talentPool.recaptcha.properties;

import java.util.ResourceBundle;

/**
 *  * @author sanjay
 *
 */

public class ReCaptchaProperties {
	
private static ResourceBundle rb = null;
	
	static{
        rb = ResourceBundle.getBundle("recaptcha");
    }    
    /** Creates a new instance of StringLabels */
    public ReCaptchaProperties() {
    }

    public static String getProperty(String key){
        return rb.getString(key);
    }
}
