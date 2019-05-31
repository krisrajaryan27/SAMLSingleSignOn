package com.talentPool.common.properties;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.talentPool.common.utils.Utils;

/**
 * @author shivprasad
 *
 */
public class TPLabels {
	private static ResourceBundle rb = null;
	private static ResourceBundle customrb = null;
	
	static{
        rb = ResourceBundle.getBundle("talentpoollabels");
        customrb = ResourceBundle.getBundle("customTalentpoollabels");
    }    
    /** Creates a new instance of StringLabels */
    public TPLabels() {
    }

    public static String getLabel(String key){
    	String value = null;
    	try{
    		value = rb.getString(key);
    	} catch (MissingResourceException e){
    		value = customrb.getString(key);
    	}
        return value;
    }
    
    /**
     * Retrieves label from resource bundle and replaces {0} with value of key1 in resource bundle.
     * <br><br><br>For eg.: 
     * <br>If value for "<code>select_option.all_positions=All {0}</code>"
     * then we can use <br>
     * <b><code>getLabel("select_option.all_positions","common.positions")</code></b>
     * <br>which will return String <b>"All Positions"</b>
     * @param key
     * @param param1
     * @return formatted value for given keys from resource bundle
     * @see TPLabels.getLabel(key, key1);
     */
    public static String getLabel(String key, String key1){
    	return getLabel(key, new String[]{getLabel(key1)});
    }
    
    /**
     * @param key
     * @param param1
     * @param param2
     * @return formatted value for given keys from resource bundle
     * @see TPLabels.getLabel(key, key1);
     */
    public static String getLabel(String key, String key1, String key2){
        return getLabel(key, new String[]{getLabel(key1), getLabel(key2)}); 
    }
    
    
    public static String getLabel(String key,Object[] args){
    	if(!Utils.isBlankOrNull(getLabel(key))){
    		try {
    			MessageFormat form = new MessageFormat(getLabel(key));
    			return form.format(args);
			} catch (IllegalArgumentException  e) {
				return getLabel(key);
			}
    	}else {
    		return getLabel(key);
		}
    }
}
