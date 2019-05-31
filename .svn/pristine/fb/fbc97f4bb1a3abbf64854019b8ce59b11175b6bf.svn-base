/**
 * 
 */
package com.talentPool.common.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.TimeZone;

import com.talentPool.common.MyThreadLocal;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.utils.DBUtils;
import com.talentPool.common.utils.EncryptionUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.utils.Utils;
import com.talentPool.timeZone.TimeZoneUtils;

/**
 * @author shivprasad
 * 
 */
public class DBPreparedQuery extends QueryFrame {
	private PreparedStatement stmt = null;
	private ResultSet rs = null;
	private ConcurrentHashMap<Integer, String> parameterMap = null;
	private String timeZone;

	/**
	 * Creates a new instance of DBPreparedQuery which behaves as prepared statement
	 * 
	 * @param dqName
	 *            name of the dataquery entry as defined in the xml file.
	 * @throws SQLException 
	 */

	public DBPreparedQuery(String dqName) {
		super(dqName);
		try {
			this.con = DBManager.getConnection();
			this.stmt = con.prepareStatement(this.sql);
			this.parameterMap = new ConcurrentHashMap<Integer, String>();
			new ArrayList<Integer>();
			applyEncryptionDecryption(this.sql);
			try {
				//this.timeZone = TimeZoneUtils.getOffset(TimeZone.getDefault().getID());
				if (MyThreadLocal.get() != null && !Utils.isBlankOrNull(MyThreadLocal.get().getTimeZone())){
					this.timeZone = TimeZoneUtils.getOffset(MyThreadLocal.get().getTimeZone());
				}else {
					this.timeZone = TimeZoneUtils.getOffset(TimeZone.getDefault().getID());
				}
			} catch(Exception e){
				timeZone = "+00:00";
			}
		} catch (SQLException e) {
			TPLogger.getLogger().error("Caught Exception", e);
		}
	}

	/**
	 * Creates a new instance of DBPreparedQuery which behaves as prepared statement
	 * 
	 * @param dqName
	 *            name of the dataquery entry as defined in the xml file.
	 * @param tr
	 *            The DBTransaction object, of which this query wants to be a part.
	 * @throws SQLException 
	 */

	public DBPreparedQuery(String dqName, DBTransaction tr) {
		super(dqName);
		try {
			this.isTransaction = true;
			this.con = tr.getConnection();
			this.stmt = con.prepareStatement(this.sql);
			this.parameterMap = new ConcurrentHashMap<Integer, String>();
			new ArrayList<Integer>();
			applyEncryptionDecryption(this.sql);
			try {
				//this.timeZone = TimeZoneUtils.getOffset(TimeZone.getDefault().getID());
				//this.timeZone = TimeZoneUtils.getOffset(MyThreadLocal.get().getTimeZone());
				if (MyThreadLocal.get() != null && !Utils.isBlankOrNull(MyThreadLocal.get().getTimeZone())){
					this.timeZone = TimeZoneUtils.getOffset(MyThreadLocal.get().getTimeZone());
				}else {
					this.timeZone = TimeZoneUtils.getOffset(TimeZone.getDefault().getID());
				}
			} catch(Exception e){
				this.timeZone = "+00:00";
			}
		} catch (SQLException e) {
			TPLogger.getLogger().error("Caught Exception", e);
		}
	}

	/**
	 * Creates a new instance of DBPreparedQuery
	 * 
	 * @param dqName
	 *            name of the dataquery entry as defined in the xml file.
	 * @param dynamicParams
	 *            String array to be replaced dynamically
	 * @throws SQLException 
	 */
	public DBPreparedQuery(String dqName, String[] dynamicParams) {
		super(dqName);
		try {
			this.sql = getSubstitutedQuery(this.sql, dynamicParams);
			this.con = DBManager.getConnection();
			this.stmt = con.prepareStatement(this.sql);
			this.parameterMap = new ConcurrentHashMap<Integer, String>();
			new ArrayList<Integer>();
			applyEncryptionDecryption(this.sql);
			try {
				//this.timeZone = TimeZoneUtils.getOffset(TimeZone.getDefault().getID());
				if (MyThreadLocal.get() != null && !Utils.isBlankOrNull(MyThreadLocal.get().getTimeZone())){
					this.timeZone = TimeZoneUtils.getOffset(MyThreadLocal.get().getTimeZone());
				}else {
					this.timeZone = TimeZoneUtils.getOffset(TimeZone.getDefault().getID());
				}
			} catch(Exception e){
				this.timeZone = "+00:00";
			}
		} catch (SQLException e) {
			TPLogger.getLogger().error("Caught Exception", e);
		}
	}

	/**
	 * Creates a new instance of DBPreparedQuery
	 * 
	 * @param dqName
	 *            name of the dataquery entry as defined in the xml file.
	 * @param dynamicParams
	 *            String array to be replaced dynamically
	 * @param tr
	 *            Transaction of this query is part
	 * @throws SQLException 
	 */
	public DBPreparedQuery(String dqName, String[] dynamicParams, DBTransaction tr) {
		super(dqName);
		try {
			this.isTransaction = true;
			this.sql = getSubstitutedQuery(this.sql, dynamicParams);
			this.con = tr.getConnection();
			this.stmt = con.prepareStatement(this.sql);
			this.parameterMap = new ConcurrentHashMap<Integer, String>();
			new ArrayList<Integer>();
			applyEncryptionDecryption(this.sql);
			try {
				//this.timeZone = TimeZoneUtils.getOffset(TimeZone.getDefault().getID());
				if (MyThreadLocal.get() != null && !Utils.isBlankOrNull(MyThreadLocal.get().getTimeZone())){
					this.timeZone = TimeZoneUtils.getOffset(MyThreadLocal.get().getTimeZone());
				}else {
					this.timeZone = TimeZoneUtils.getOffset(TimeZone.getDefault().getID());
				}
			} catch(Exception e){
				this.timeZone = "+00:00";
			}
		} catch (SQLException e) {
			TPLogger.getLogger().error("Caught Exception", e);
		}
	}

	/**
	 * Returns an ArrayList of IDataObject's. The actual implemented class of the IDataObject is as specified in the dataquery xml file.
	 * 
	 * @throws SQLException
	 * @return ArrayList of IDataObject objects.
	 */
	public ArrayList getResult() throws SQLException {
		ArrayList list = new ArrayList();
		try {
			Class cls = Class.forName(this.dqMetaData.getDataObjectClassName());
			String timeZoneSQL = "set time_zone = ";
			String timeZoneValue = "'"+timeZone+"'";
			stmt.execute(timeZoneSQL+timeZoneValue);
			rs = stmt.executeQuery();
			
			// after execute, 
			// 1. check if it is a 'select' query
			// 2. if yes, see if it contains the encrypted columns (may be present as different aliases)
			// 3. if yes, get the corresponding db_map column name
			// 4. apply decryption on the value returned from resultset by above db_map column name
			
			String sqlString = this.sql.toLowerCase();
			HashMap<String, String> encryptedDbColumns = new HashMap<String, String>();
			if(sqlString.startsWith("select")) {
				encryptedDbColumns = DBUtils.findEncryptedDbColumns(sqlString);				
			}
			
			while (rs.next()) {
				IDataObject dObj = (IDataObject) (cls.newInstance());
				setAttributes(dObj, rs, this.dqMetaData.getDbColumnMaps(), encryptedDbColumns);
				list.add(dObj);
			}			
			rs.close();
			stmt.close();
		} catch (ClassNotFoundException e) {
			TPLogger.getLogger().error("Error while getting result", e);
		} catch (InstantiationException e) {
			TPLogger.getLogger().error("Error while getting result", e);
		} catch (IllegalAccessException e) {
			TPLogger.getLogger().error("Error while getting result", e);
		}  finally {
			if (!isTransaction) {
				releaseConnection();
			}
		}
		return list;
	}

	/**
	 * Returns an IDataObject. This is useful for queries which return a single row as the result. The actual implemented class of the IDataObject is as specified in the dataquery xml file.
	 * 
	 * @throws SQLException
	 * @return single IDataObject populated from the resultset of the query.
	 */
	public IDataObject getSingleObjectResult() throws SQLException {
		IDataObject dObj = null;
		try {
			Class cls = Class.forName(this.dqMetaData.getDataObjectClassName());
			String timeZoneSQL = "set time_zone = ";
			String timeZoneValue = "'"+timeZone+"'";
			stmt.execute(timeZoneSQL+timeZoneValue);
			rs = stmt.executeQuery();
			
			// after execute, 
			// 1. check if it is a 'select' query
			// 2. if yes, see if it contains the encrypted columns (may be present as different aliases)
			// 3. if yes, get the corresponding db_map column name
			// 4. apply decryption on the value returned from resultset by above db_map column name 
			
			String sqlString = this.sql.toLowerCase();
			HashMap<String, String> encryptedDbColumns = new HashMap<String, String>();
			if(sqlString.startsWith("select")) {
				encryptedDbColumns = DBUtils.findEncryptedDbColumns(sqlString);				
			}
			
			if (rs.next()) {
				dObj = (IDataObject) (cls.newInstance());
				setAttributes(dObj, rs, this.dqMetaData.getDbColumnMaps(), encryptedDbColumns);
			}		
			rs.close();
			stmt.close();
		} catch (ClassNotFoundException e) {
			TPLogger.getLogger().error("Error while getting result", e);
		} catch (InstantiationException e) {
			TPLogger.getLogger().error("Error while getting result", e);
		} catch (IllegalAccessException e) {
			TPLogger.getLogger().error("Error while getting result", e);
		} finally {
			if (!isTransaction) {
				releaseConnection();
			}
		}
		return dObj;
	}

	/**
	 * This method is used to get the result of a query returning a single value. The returned Object needs to be typecasted to the desired class.
	 * 
	 * @throws SQLException
	 * @return Object
	 */
	public Object getSingleSimpleObjectResult() throws SQLException {
		Object result = null;
		try {
			String timeZoneSQL = "set time_zone = ";
			String timeZoneValue = "'"+timeZone+"'";
			stmt.execute(timeZoneSQL+timeZoneValue);
			rs = stmt.executeQuery();
			if (rs.next()) {
				result = rs.getObject(1);
			}	
			rs.close();
			stmt.close();
		} finally {
			if (!isTransaction) {
				releaseConnection();
			}
		}
		return result;
	}

	/**
	 * Executes any query which does not expect a result set.
	 * 
	 * @throws SQLException
	 * @see getResult(); getSingleObjectResult()
	 */
	public int execute() throws SQLException {
		int numColsAffected = -1;
		try {
			String timeZoneSQL = "set time_zone = ";
			String timeZoneValue = "'"+timeZone+"'";
			stmt.execute(timeZoneSQL+timeZoneValue);
			boolean b = stmt.execute();
			if (b) {
				// Ignore; This means that a resultset is being returned
				// Use the other methods to get objects populated from the resultset
			} else {
				// Update.
				numColsAffected = stmt.getUpdateCount();
			}
			stmt.close();
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error in execute() Prepared Statement", e);
			throw e;
		} finally {
			if (!isTransaction) {
				releaseConnection();
			}
		}
		return numColsAffected;
	}

	/**
	 * Method to return prepared statement.
	 */
	public Statement getStatement() {		
		return this.stmt;
	}
	
	/**
	 * Method to return result set.
	 */
	public ResultSet getResultSet() {
		return this.rs;
	}
	
	/**
	 * Utility method to set a String where an int is required.
	 * 
	 * @param parameterIndex
	 * @param id
	 * @throws SQLException
	 */
	public void setId(int parameterIndex, String id) throws java.sql.SQLException {
		if (id != null) {
			TPLogger.getLogger().debug(parameterIndex+", "+Integer.parseInt(id));
			stmt.setInt(parameterIndex, Integer.parseInt(id));
		} else {
			stmt.setNull(parameterIndex, java.sql.Types.NUMERIC);
		}
	}

	/**
	 * 
	 * @param parameterIndex
	 * @param sqlType
	 * @throws SQLException
	 */
	public void setNull(int parameterIndex, int sqlType) throws java.sql.SQLException {
		stmt.setNull(parameterIndex, sqlType);
	}

	/**
	 * 
	 * @param parameterIndex
	 * @param x
	 * @throws SQLException
	 */
	public void setDouble(int parameterIndex, double x) throws java.sql.SQLException {
		stmt.setDouble(parameterIndex, x);
	}

	/**
	 * 
	 * @param i
	 * @param x
	 * @throws SQLException
	 */
	public void setBlob(int i, java.sql.Blob x) throws java.sql.SQLException {
		stmt.setBlob(i, x);
	}

	/**
	 * 
	 * @param i
	 * @param x
	 * @throws SQLException
	 */
	public void setArray(int i, java.sql.Array x) throws java.sql.SQLException {
		stmt.setArray(i, x);
	}

	/**
	 * 
	 * @param parameterIndex
	 * @param x
	 * @throws SQLException
	 */
	public void setTimestamp(int parameterIndex, java.sql.Timestamp x) throws java.sql.SQLException {
		stmt.setTimestamp(parameterIndex, x);
	}

	/**
	 * 
	 * @param parameterIndex
	 * @param x
	 * @param length
	 * @throws SQLException
	 */
	public void setAsciiStream(int parameterIndex, java.io.InputStream x, int length) throws java.sql.SQLException {
		stmt.setAsciiStream(parameterIndex, x, length);
	}

	/**
	 * 
	 * @param parameterIndex
	 * @param x
	 * @throws SQLException
	 */
	public void setInt(int parameterIndex, int x) throws java.sql.SQLException {
		TPLogger.getLogger().debug(parameterIndex + " " + x);
		stmt.setInt(parameterIndex, x);
	}

	/**
	 * 
	 * @param parameterIndex
	 * @param x
	 * @throws SQLException
	 */
	public void setShort(int parameterIndex, short x) throws java.sql.SQLException {
		stmt.setShort(parameterIndex, x);
	}

	/**
	 * 
	 * @param parameterIndex
	 * @param x
	 * @throws SQLException
	 */
	public void setByte(int parameterIndex, byte x) throws java.sql.SQLException {
		stmt.setByte(parameterIndex, x);
	}

	/**
	 * 
	 * @param parameterIndex
	 * @param x
	 * @throws SQLException
	 */
	public void setObject(int parameterIndex, Object x) throws java.sql.SQLException {
		stmt.setObject(parameterIndex, x);
	}

	/**
	 * 
	 * @param i
	 * @param x
	 * @throws SQLException
	 */
	public void setRef(int i, java.sql.Ref x) throws java.sql.SQLException {
		stmt.setRef(i, x);
	}

	/**
	 * 
	 * @param parameterIndex
	 * @param x
	 * @throws SQLException
	 */
	public void setFloat(int parameterIndex, float x) throws java.sql.SQLException {
		stmt.setFloat(parameterIndex, x);
	}

	/**
	 * 
	 * @param parameterIndex
	 * @param x
	 * @param targetSqlType
	 * @throws SQLException
	 */
	public void setObject(int parameterIndex, Object x, int targetSqlType) throws java.sql.SQLException {
		stmt.setObject(parameterIndex, x, targetSqlType);
	}

	/**
	 * 
	 * @param parameterIndex
	 * @param x
	 * @param targetSqlType
	 * @param scale
	 * @throws SQLException
	 */
	public void setObject(int parameterIndex, Object x, int targetSqlType, int scale) throws java.sql.SQLException {
		stmt.setObject(parameterIndex, x, targetSqlType, scale);
	}

	/**
	 * 
	 * @param parameterIndex
	 * @param reader
	 * @param length
	 * @throws SQLException
	 */
	public void setCharacterStream(int parameterIndex, java.io.Reader reader, int length) throws java.sql.SQLException {
		stmt.setCharacterStream(parameterIndex, reader, length);
	}

	/**
	 * 
	 * @param parameterIndex
	 * @param x
	 * @throws SQLException
	 */
	public void setDate(int parameterIndex, java.sql.Date x) throws java.sql.SQLException {
		String y =Utils.getDateConvertedToString(x, Utils.redYYYYMMDDFormat);
		if (parameterMap.containsKey(parameterIndex)){
			String columnKey = DBConstants.columnKeyMap.get(parameterMap.get(parameterIndex));
			try {
				if (!Utils.isBlankOrNull(y)){
					y= EncryptionUtils.encrypt(y, columnKey);
					stmt.setString(parameterIndex, y);
				} else {
					stmt.setString(parameterIndex, y);
				}
			} catch (Exception e){
				TPLogger.getLogger().error("Error in setting Query parameter.", e);
			}
		}else {
			stmt.setDate(parameterIndex, x);
		}
	}

	/**
	 * 
	 * @param parameterIndex
	 * @param x
	 * @throws SQLException
	 */
	public void setBoolean(int parameterIndex, boolean x) throws java.sql.SQLException {
		stmt.setBoolean(parameterIndex, x);
	}

	/**
	 * 
	 * @param parameterIndex
	 * @param x
	 * @throws SQLException
	 */
	public void setURL(int parameterIndex, java.net.URL x) throws java.sql.SQLException {
		stmt.setURL(parameterIndex, x);
	}

	/**
	 * 
	 * @param parameterIndex
	 * @param x
	 * @param length
	 * @throws SQLException
	 */
	public void setBinaryStream(int parameterIndex, java.io.InputStream x, int length) throws java.sql.SQLException {
		stmt.setBinaryStream(parameterIndex, x, length);
	}

	/**
	 * 
	 * @param parameterIndex
	 * @param x
	 * @throws SQLException
	 */
	public void setTime(int parameterIndex, java.sql.Time x) throws java.sql.SQLException {
		stmt.setTime(parameterIndex, x);
	}

	/**
	 * 
	 * @param parameterIndex
	 * @param x
	 * @throws SQLException
	 */
	public void setBigDecimal(int parameterIndex, java.math.BigDecimal x) throws java.sql.SQLException {
		stmt.setBigDecimal(parameterIndex, x);
	}

	/**
	 * 
	 * @param parameterIndex
	 * @param x
	 * @throws SQLException
	 */
	public void setBytes(int parameterIndex, byte[] x) throws java.sql.SQLException {
		stmt.setBytes(parameterIndex, x);
	}

	/**
	 * 
	 * @param i
	 * @param x
	 * @throws SQLException
	 */
	public void setClob(int i, java.sql.Clob x) throws java.sql.SQLException {
		stmt.setClob(i, x);
	}

	/**
	 * 
	 * @param parameterIndex
	 * @param x
	 * @param cal
	 * @throws SQLException
	 */
	public void setDate(int parameterIndex, java.sql.Date x, java.util.Calendar cal) throws java.sql.SQLException {
		stmt.setDate(parameterIndex, x, cal);
	}

	/**
	 * 
	 * @param parameterIndex
	 * @param x
	 * @throws SQLException
	 */
	public void setLong(int parameterIndex, long x) throws java.sql.SQLException {
		stmt.setLong(parameterIndex, x);
	}

	/**
	 * 
	 * @param parameterIndex
	 * @param x
	 * @throws SQLException
	 */
	public void setString(int parameterIndex, String x) throws java.sql.SQLException {
		TPLogger.getLogger().debug(parameterIndex+", "+x);
		if (parameterMap.containsKey(parameterIndex)){
			String columnKey = DBConstants.columnKeyMap.get(parameterMap.get(parameterIndex));
			try {
				if (!Utils.isBlankOrNull(x)){
					x= EncryptionUtils.encrypt(x, columnKey);
					stmt.setString(parameterIndex, x);
				} else {
					stmt.setString(parameterIndex, x);
				}
			} catch (Exception e){
				TPLogger.getLogger().error("Error in setting Query parameter.", e);
			}
		}else {
			stmt.setString(parameterIndex, x);
		}
	}

	/**
	 * 
	 * @param parameterIndex
	 * @param x
	 * @param cal
	 * @throws SQLException
	 */
	public void setTimestamp(int parameterIndex, java.sql.Timestamp x, java.util.Calendar cal) throws java.sql.SQLException {
		stmt.setTimestamp(parameterIndex, x, cal);
	}

	/**
	 * 
	 * @param parameterIndex
	 * @param x
	 * @param cal
	 * @throws SQLException
	 */
	public void setTime(int parameterIndex, java.sql.Time x, java.util.Calendar cal) throws java.sql.SQLException {
		stmt.setTime(parameterIndex, x, cal);
	}

	/**
	 * 
	 * @param paramIndex
	 * @param sqlType
	 * @param typeName
	 * @throws SQLException
	 */
	public void setNull(int paramIndex, int sqlType, String typeName) throws java.sql.SQLException {
		stmt.setNull(paramIndex, sqlType, typeName);
	}
	
	private String applyEncryptionDecryption(String sqlString) {
		try{
			sqlString = sqlString.toLowerCase();
			if(sqlString.startsWith("insert")) {
				sqlString = encryptValues(sqlString);
			} else if(sqlString.startsWith("select") || sqlString.startsWith("update")) {
				sqlString = encryptUpdateSelect(sqlString);
			}
		}catch (Exception e){
			TPLogger.getLogger().error("Error in Query encryption", e);
		}
		return sqlString;
	}
	
	private String encryptValues(String sqlString) {
		if(sqlString.startsWith("insert")){
			String[] arr =sqlString.split("[ ](?i)values");
			if (arr.length>1){
				String name = arr[0];
				name = name.substring(name.indexOf("(")+1, name.lastIndexOf(")"));
				String[] names = name.split(",");
				String params = arr[1];
				params = params.substring(params.indexOf("(")+1, params.lastIndexOf(")"));
				String[] parameters = params.split(",");
				int j =0;
				HashMap<String, Integer> parameterIndexes = new HashMap<String, Integer>();
				for (int k=0; k<names.length; k++){
					if (parameters[k].contains("?")){
						j++;
						parameterIndexes.put(names[k].toLowerCase().trim(), j);
					}
				}
				Set<String> toEncColums = DBConstants.columnKeyMap.keySet();
				Iterator<String> it = toEncColums.iterator();
				while(it.hasNext()){
					String val = it.next();
					if (parameterIndexes.containsKey(val.toLowerCase().trim())){
						parameterMap.put(parameterIndexes.get(val.toLowerCase().trim()), val);
					}
				}
//				for (int i=0; i<names.length; i++){
//					Iterator<String> it = toEncColums.iterator();
//					while(it.hasNext()){
//						String val = it.next();
//						if (Pattern.compile(Pattern.quote(val), Pattern.CASE_INSENSITIVE).matcher(names[i]).find()){
//							parameterMap.put(i, val);
//						}
//					}
//				}
			}
		}
		return sqlString;
	}
	
	private String encryptUpdateSelect(String sqlString) {
		sqlString = sqlString.replaceAll("= ", "=");
		sqlString = sqlString.replaceAll(" =", "=");
		String[] parameterNames = sqlString.split(" ");
		int j = 0;
		for (int i=0; i<parameterNames.length; i++){
			if (parameterNames[i].contains("?")){
				j = j +getCount(parameterNames[i], "?");
				Set<String> toEncColums = DBConstants.columnKeyMap.keySet();
				Iterator<String> it = toEncColums.iterator();
				while(it.hasNext()){
					String val = it.next();
					if (parameterNames[i].contains(val.toLowerCase().trim())){
						parameterMap.put(j, val);
					}
				}
			}
		}
		return sqlString;
	}
	
	private int getCount(String name, String match){
		int count = 0;
		for(int i =0; i<name.length(); i++){
			if(match.equals(name.substring(i, i+1))){
				count++;
			}
		}
		return count;
	}
	
}
