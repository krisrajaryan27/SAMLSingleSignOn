package com.talentPool.common.db;

import java.util.Map;

/**
 * @author shivprasad
 * 
 * This is the data object interface which is implemented by each database
 */
public interface IDataObject {
	/**
	 * @param key
	 * @return Object specified by the String key
	 */
	public Object getAttribute(String key);

	/**
	 * @param key
	 * @param value
	 *            Set the attribute with name = String key and value= Object value
	 */
	public void setAttribute(String key, Object value);

	/**
	 * @return Map of all attributes key/value pair
	 */
	public Map getAttributes();

	/**
	 * @param attributes
	 *            set attributes as key/value pair from Map attributes
	 */
	public void setAttributes(Map attributes);

	public void setAttributes();
}