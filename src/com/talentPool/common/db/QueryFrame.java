/**
/**
 * 
 */
package com.talentPool.common.db;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Date;
import java.util.TimeZone;

import javax.crypto.NoSuchPaddingException;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DQMetaData;
import com.talentPool.common.db.DBColumnMap;
import com.talentPool.common.db.Exception.NoResultFoundException;
import com.talentPool.common.db.IDataObject;
import com.talentPool.common.utils.EncryptionUtils;
import com.talentPool.common.utils.Utils;

/**
 * @author shivprasad
 * 
 */
public abstract class QueryFrame {
	static DBMapXMLHandler xmlHandler;
	String dqName = "";
	DQMetaData dqMetaData = null;
	Connection con;
	boolean isTransaction = false;
	String sql;
	private static String _delimiter = "@@";

	static {
		xmlHandler = DBMapXMLHandler.getInstance();
	}

	public QueryFrame(String dqName) {
		try {
			this.dqName = dqName;
			this.dqMetaData = getDQMetaData(dqName);
			if (this.dqMetaData == null) {
				// TODO MPLogger.getLogger().fine("No sql found for dqName:"+dqName);
			}
			this.sql = getDBQuery(dqMetaData.getDbQueryName());
			TPLogger.getLogger().debug(sql);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error Creating QueryFrame ", e);
		}
	}

	public abstract IDataObject getSingleObjectResult() throws SQLException;

	public abstract Object getSingleSimpleObjectResult() throws SQLException;

	public abstract ArrayList getResult() throws SQLException;

	public abstract int execute() throws SQLException;

	public abstract Statement getStatement();

	public abstract ResultSet getResultSet();

	/**
	 * Utility method to get the String value from a query which has just one string in the result
	 * set.
	 * 
	 * @throws SQLException
	 * @return String
	 */
	public String getStringResult() throws SQLException {
		return (String) (getSingleSimpleObjectResult());
	}

	/**
	 * Utility method to get the Integer value from a query which has just one integer in the result
	 * set.
	 * 
	 * @throws SQLException
	 * @return Integer
	 */
	public int getIntResult() throws SQLException, NoResultFoundException {
		Object val = getSingleSimpleObjectResult();
		if (val == null)
			throw new NoResultFoundException();
		if (val instanceof BigDecimal) {
			return ((BigDecimal) val).intValue();
		} else if (val instanceof Integer) {
			return ((Integer) val).intValue();
		} else if (val instanceof String) {
			return Integer.parseInt((String) val);
		}
		return Integer.parseInt("" + val);
	}
	
	/**
	 * Utility method to get the Long value from a query which has just one bigint or long value in the result
	 * set.
	 * 
	 * @throws SQLException
	 * @return Integer
	 */
	public Long getLongResult() throws SQLException, NoResultFoundException {
		Object val = getSingleSimpleObjectResult();
		if (val == null)
			throw new NoResultFoundException();
		if (val instanceof BigDecimal) {
			return ((BigDecimal) val).longValue();
		} else if (val instanceof Integer) {
			return ((Integer) val).longValue();
		} else if (val instanceof String) {
			return Long.parseLong((String) val);
		}
		return Long.parseLong("" + val);
	}

	/**
	 * Works same as getIntResult(), but returns default value if the result is null
	 * 
	 * @throws SQLException
	 * @return Integer
	 */
	public int getIntResult(int defaultVal) throws SQLException {
		Object val = getSingleSimpleObjectResult();
		if (val == null)
			return 0;
		if (val instanceof BigDecimal) {
			return ((BigDecimal) val).intValue();
		} else if (val instanceof Integer) {
			return ((Integer) val).intValue();
		} else if (val instanceof String) {
			return Integer.parseInt((String) val);
		}
		return Integer.parseInt("" + val);
	}

	/**
	 * Utility method to get the id value from a query. This method gets the integer value from the
	 * guery result set and converts it into a String and returns it.
	 * 
	 * @throws SQLException
	 * @return
	 */
	public String getIdResult() throws SQLException {
		Object o = getSingleSimpleObjectResult();
		return (o == null) ? null : o.toString();
	}

	/**
	 * @param dObj
	 * @param rs
	 * @param colList
	 * @throws SQLException
	 */
	public void setAttributes(IDataObject dObj, ResultSet rs,
			ArrayList colList) throws SQLException {
		setAttributes(dObj, rs, colList, null);
	}
	
	/**
	 * @param dObj
	 * @param rs
	 * @param colList
	 * @throws SQLException
	 */
	public void setAttributes(IDataObject dObj, ResultSet rs,
			ArrayList colList, HashMap<String, String> encryptedDbColumns) throws SQLException {
		int size = colList.size();
		Set<String> columns = DBConstants.columnKeyMap.keySet();		
		for (int i = 0; i < size; i++) {
			DBColumnMap colMap = (DBColumnMap) (colList.get(i));
			String dbMapColumnName = colMap.getColumnName();
			
			try{
				java.sql.Timestamp ts =(java.sql.Timestamp) rs.getObject(dbMapColumnName);
				
				Date dt = ts;
				if (TimeZone.getDefault().inDaylightTime(dt) && !TimeZone.getDefault().inDaylightTime(new Date())){
					long realTime =ts.getTime() + TimeZone.getDefault().getDSTSavings();
					dt = new java.sql.Timestamp(realTime);
				}
				if (!TimeZone.getDefault().inDaylightTime(dt) && TimeZone.getDefault().inDaylightTime(new Date())){
					long realTime =ts.getTime() - TimeZone.getDefault().getDSTSavings();
					dt = new java.sql.Timestamp(realTime);
				}
				dObj.getAttributes().put(colMap.getAttributeName(), dt);
			}catch (Exception e){
				Object value;
				try{
				     value = rs.getObject(dbMapColumnName);	
				}catch(Exception e1){
					value="";
				}
				if (value != null && !Utils.isBlankOrNull(value.toString())) {
				if(!Utils.isMapEmptyOrNull(encryptedDbColumns)
						&& encryptedDbColumns.containsKey(dbMapColumnName)) {
					value = decryptValue(value, encryptedDbColumns.get(dbMapColumnName));
				}		
				if(Utils.isMapEmptyOrNull(encryptedDbColumns) 
						&& columns.contains(dbMapColumnName.toLowerCase())) { // Considering this for procedures
					value = decryptValue(value, DBConstants.columnKeyMap.get(dbMapColumnName));
				}
				if (dbMapColumnName.equals("date_of_birth")){
					String val = (String) value;
					value = Utils.convertToSQLDate(val, Utils.redYYYYMMDDFormat);
				}
			}
				dObj.getAttributes().put(colMap.getAttributeName(), value);
			}
		}
		dObj.setAttributes();
	}
	
	/**
	 * @param value
	 * @param key
	 * @return decrypted value
	 */
	private Object decryptValue(Object value, String key) {
		value = EncryptionUtils.decrypt(value.toString(), key);
		return value;
	}

	/**
	 * 
	 * @param dqName
	 * @return
	 */
	public DQMetaData getDQMetaData(String dqName) {
		return xmlHandler.getDQMetaData(dqName);
	}

	public String getDBQuery(String qName) {
		return xmlHandler.getDBQuery(qName);
	}

	public static String getSubstitutedQuery(String query, String[] dynamicParams) {
		int queryLength = query.length();
		StringBuffer sb = new StringBuffer();
		int delimiterLength = _delimiter.length();
		int index = query.indexOf(_delimiter);
		int lastIndex = 0;
		int paramIndex = 0;
		while (index != -1) {
			sb.append(query.substring(lastIndex, index));
			sb.append(dynamicParams[paramIndex]);
			lastIndex = index + delimiterLength;
			if (lastIndex < queryLength) {
				index = query.indexOf(_delimiter, lastIndex);
			} else {
				break;
			}
			paramIndex++;
		}
		if (lastIndex < queryLength) {
			sb.append(query.substring(lastIndex));
		}
		return sb.toString();
	}

	/**
	 * close the resultset and statement object opened for this query
	 */
	public void closeOpenCursors() {
		if (this.getResultSet() != null) {
			try {
				this.getResultSet().close();
			} catch (Exception e) {
				TPLogger.getLogger().error("Error while closing result set", e);
			}
		}
		if (this.getStatement() != null) {
			try {
				this.getStatement().close();
			} catch (Exception e) {
				TPLogger.getLogger().error("Error while closing statement", e);
			}
		}
	}

	/**
	 * closes all the open cursors and release the connection back to connection pool. 
	 * Call this method at the end when it is not a transaction
	 */
	public void releaseConnection() {
		closeOpenCursors();
		if (con != null) {
			try {
				DBManager.release(con);
				this.con = null;
			} catch (Exception e) {
				TPLogger.getLogger().error("Error while closing connection", e);
			}
		}
	}

	/**
	 * closes all the open cursors and release the connection back to connection pool also release the transaction.
	 * Call this method at the end in transaction
	 * @param tran
	 */
	public void releaseTransaction(DBTransaction tran) {
		closeOpenCursors();
		
		if (tran != null) {
			tran.release();
		} else {
			if (con != null) {
				try {
					DBManager.release(con);
					this.con = null;
				} catch (Exception e) {
					TPLogger.getLogger().error("Error while closing connection", e);
				}
			}
		}
	}

}
