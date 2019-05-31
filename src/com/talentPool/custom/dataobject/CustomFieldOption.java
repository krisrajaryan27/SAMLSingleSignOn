/**
 * 
 */
package com.talentPool.custom.dataobject;

import com.talentPool.common.db.SimpleDataObject;

/**
 * @author shivprasad
 * 
 */
public class CustomFieldOption extends SimpleDataObject {
	public CustomFieldOption(String key, String value) {
		setKey(key);
		setValue(value);
	}

	public String getKey() {
		return getString("key");
	}

	public String getValue() {
		return getString("value");
	}

	public void setKey(String key) {
		setAttribute("key", key);
	}

	public void setValue(String value) {
		setAttribute("value", value);
	}

}
