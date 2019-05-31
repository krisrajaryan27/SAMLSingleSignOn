/**
 * 
 */
package com.talentPool.common.db;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.utils.Utils;

/**
 * @author shivprasad
 * 
 */
public class SimpleDataObject implements IDataObject, Serializable {
	private static final long serialVersionUID = 1L;
	Map values;

	/** Creates a new instance of DataObject */
	public SimpleDataObject() {
		values = new HashMap();
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

	public String toString() {
		return "" + values;
	}

	public String getId(String key) {
		Object val = values.get(key);
		return (val == null) ? null : val.toString();
	}

	public String getString(String key) {
		Object val = values.get(key);
		if (val instanceof String) {
			return (String) val;
		}
		return (val == null) ? null : "" + val;
	}

	public int getInt(String key) throws NumberFormatException {
		Object val = values.get(key);
		// MPLogger.getLogger().finer(""+val.getClass().getName());
		if (val == null) {
			TPLogger.getLogger().debug("value for " + key + "=null, but returning 0.");
			return 0;
		}
		if (val instanceof BigDecimal) {
			return ((BigDecimal) val).intValue();
		} else if (val instanceof Integer) {
			return ((Integer) val).intValue();
		} else if (val instanceof String) {
			return Integer.parseInt((String) val);
		}
		try {
			return Integer.parseInt("" + val);
		} catch (Exception e) {
			TPLogger.getLogger().debug("Exception occured, but returning 0.", e);
			return 0;
		}

	}

	public int getInt(String key, int defaultVal) {
		int retVal = defaultVal;
		try {
			retVal = getInt(key);
		} catch (Exception e) {
		}
		return retVal;
	}

	public long getLong(String key) throws NumberFormatException {
		Object val = values.get(key);
		// MPLogger.getLogger().finer(""+val.getClass().getName());
		if (val == null) {
			TPLogger.getLogger().debug("value for " + key + "=null, but returning 0.");
			return 0;
		}
		if (val instanceof BigDecimal) {
			return ((BigDecimal) val).longValue();
		} else if (val instanceof Long) {
			return ((Long) val).longValue();
		} else if (val instanceof Integer) {
			return ((Integer) val).longValue();
		} else if (val instanceof String) {
			return Long.parseLong((String) val);
		}
		try {
			return Long.parseLong("" + val);
		} catch (Exception e) {
			TPLogger.getLogger().debug("Exception occured, but returning 0.", e);
			return 0;
		}

	}

	public BigDecimal getBigDecimal(String key) {
		Object val = values.get(key);
		return ((BigDecimal) val);
	}

	public BigDecimal getTwoDecimal(String key) {
		BigDecimal obj = new BigDecimal(key);
		try {
			obj = obj.setScale(2, BigDecimal.ROUND_HALF_UP);
		} catch (Exception e) {
			TPLogger.getLogger().debug("Exception occured", e);
		}
		return obj;
	}

	public java.sql.Date getDate(String key) {
		Object val = values.get(key);
		if (val instanceof Timestamp) {
			return new java.sql.Date(((Timestamp) val).getTime());
		} else if (val instanceof java.sql.Date) {
			return ((java.sql.Date) val);
		} else if(val instanceof java.lang.String) { //Try converting db date formatted string value
			return Utils.convertToSQLDate(val.toString(), Utils.redYYYYMMDDFormat);
		} else {
			// This may throw a class cast exception
			return ((java.sql.Date) val);
		}
	}

	public double getDouble(String key) {
		Object val = values.get(key);
		if (val == null) {
			TPLogger.getLogger().debug("value for " + key + "=null, but returning 0.");
			return 0;
		}
		if (val instanceof BigDecimal) {
			return ((BigDecimal) val).doubleValue();
		} else if (val instanceof Double) {
			return ((Double) val).doubleValue();
		} else if (val instanceof Float) {
			return ((Float) val).doubleValue();
		} else if (val instanceof String) {
			return Double.parseDouble((String) val);
		}
		try {
			return Double.parseDouble("" + val);
		} catch (Exception e) {
			TPLogger.getLogger().debug("Exception occured while getting double, but returning 0.", e);
			return 0f;
		}
	}

	public float getFloat(String key) {
		Object val = values.get(key);
		if (val == null) {
			TPLogger.getLogger().debug("value for " + key + "=null, but returning 0.");
			return 0f;
		}
		if (val instanceof BigDecimal) {
			return ((BigDecimal) val).floatValue();
		} else if (val instanceof Float) {
			return ((Float) val).floatValue();
		} else if (val instanceof String) {
			return Float.parseFloat((String) val);
		}
		try {
			return Float.parseFloat("" + val);
		} catch (Exception e) {
			TPLogger.getLogger().debug("Exception occured while getting float, but returning 0.", e);
			return 0f;
		}
	}

	public boolean getBoolean(String key) {
		Object val = values.get(key);
		if (val == null) {
			return false;
		} else if (val instanceof Boolean) {
			return ((Boolean) val).booleanValue();
		} else {
			return Boolean.valueOf((String) val);
		}
	}

	public void setAttributes() {
	}

	public Timestamp getTimestamp(String key) {
		Object val = values.get(key);
		return (Timestamp) val;
	}

	public boolean exists(String key) {
		return values.containsKey(key);
	}
	
	public void addAttributes(SimpleDataObject sdo){
		values.putAll(sdo.getAttributes());
	}

}
