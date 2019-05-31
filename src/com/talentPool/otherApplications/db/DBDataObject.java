/**
 * 
 */
package com.talentPool.otherApplications.db;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Shantanu
 *
 */
public class DBDataObject {
	
	Map<String, Object> values;
	
	public DBDataObject() {
		values = new HashMap<String, Object>();
	}

	public void setAttribute(String key, Object value) {
		values.put(key, value);
	}

	public Object getAttribute(String key) {
		return values.get(key);
	}

	public Map getAttributes() {
		return values;
	}

	public void setAttributes(Map attributes) {
		values = attributes;
	}
}
