/**
 * 
 */
package com.talentPool.common.db;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Date;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import java.util.TimeZone;

import com.talentPool.common.MyThreadLocal;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.utils.Utils;
import com.talentPool.timeZone.TimeZoneUtils;

/**
 * @author shivprasad
 * 
 */
public class DBCallableQuery extends QueryFrame {
	private CallableStatement stmt = null;
	private ResultSet rs = null;
	private String timeZone;
	
	/**
	 * Creates a new instance of DBPreparedQuery which behaves as prepared statement
	 * 
	 * @param dqName
	 *            name of the dataquery entry as defined in the xml file.
	 * @throws SQLException 
	 */

	public DBCallableQuery(String dqName) {
		super(dqName);
		try {
			this.con = DBManager.getConnection();
			this.stmt = con.prepareCall(this.sql);
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
	 * Creates a new instance of DBPreparedQuery which behaves as prepared statement
	 * 
	 * @param dqName
	 *            name of the dataquery entry as defined in the xml file.
	 * @param tr
	 *            The DBTransaction object, of which this query wants to be a part.
	 * @throws SQLException 
	 */

	public DBCallableQuery(String dqName, DBTransaction tr) {
		super(dqName);
		try {
			this.isTransaction = true;
			this.con = tr.getConnection();
			this.stmt = con.prepareCall(this.sql);
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
	 * @throws SQLException 
	 */
	public DBCallableQuery(String dqName, String[] dynamicParams) {
		super(dqName);
		try {
			String substQuery = getSubstitutedQuery(this.sql, dynamicParams);
			this.con = DBManager.getConnection();
			this.stmt = con.prepareCall(substQuery);
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
	public DBCallableQuery(String dqName, String[] dynamicParams, DBTransaction tr) {
		super(dqName);
		try {
			String substQuery = getSubstitutedQuery(this.sql, dynamicParams);
			this.con = tr.getConnection();
			this.stmt = con.prepareCall(substQuery);
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
			String timeZoneSQL = "set time_zone = ";
			String timeZoneValue = "'"+timeZone+"'";
			stmt.execute(timeZoneSQL+timeZoneValue);
			Class cls = Class.forName(this.dqMetaData.getDataObjectClassName());
			rs = stmt.executeQuery();
			while (rs.next()) {
				IDataObject dObj = (IDataObject) (cls.newInstance());
				setAttributes(dObj, rs, this.dqMetaData.getDbColumnMaps());
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
		} finally {
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
			if (rs.next()) {
				dObj = (IDataObject) (cls.newInstance());
				setAttributes(dObj, rs, this.dqMetaData.getDbColumnMaps());
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
			TPLogger.getLogger().debug("Error in execute()", e);
			throw e;
		} finally {
			if (!isTransaction) {
				releaseConnection();
			}
		}
		return numColsAffected;
	}

	/**
	 * Method to return callable statement.
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
		stmt.setDate(parameterIndex, x);
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
		stmt.setString(parameterIndex, x);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.CallableStatement#getArray(int)
	 */
	public Array getArray(int paramIndex) throws SQLException {
		return stmt.getArray(paramIndex);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.CallableStatement#getArray(java.lang.String)
	 */
	public Array getArray(String paramName) throws SQLException {
		return stmt.getArray(paramName);
	}

//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see java.sql.CallableStatement#getBigDecimal(int, int)
//	 */
//	public BigDecimal getBigDecimal(int paramIndex, int scale) throws SQLException {
//		return stmt.getBigDecimal(paramIndex, scale);
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.CallableStatement#getBigDecimal(int)
	 */
	public BigDecimal getBigDecimal(int paramIndex) throws SQLException {
		return stmt.getBigDecimal(paramIndex);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.CallableStatement#getBigDecimal(java.lang.String)
	 */
	public BigDecimal getBigDecimal(String paramName) throws SQLException {
		return stmt.getBigDecimal(paramName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.CallableStatement#getBlob(int)
	 */
	public Blob getBlob(int paramIndex) throws SQLException {
		return stmt.getBlob(paramIndex);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.CallableStatement#getBlob(java.lang.String)
	 */
	public Blob getBlob(String paramName) throws SQLException {
		return stmt.getBlob(paramName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.CallableStatement#getBoolean(int)
	 */
	public boolean getBoolean(int paramIndex) throws SQLException {
		return stmt.getBoolean(paramIndex);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.CallableStatement#getBoolean(java.lang.String)
	 */
	public boolean getBoolean(String paramName) throws SQLException {
		return stmt.getBoolean(paramName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.CallableStatement#getByte(int)
	 */
	public byte getByte(int paramIndex) throws SQLException {
		return stmt.getByte(paramIndex);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.CallableStatement#getByte(java.lang.String)
	 */
	public byte getByte(String paramName) throws SQLException {
		return stmt.getByte(paramName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.CallableStatement#getBytes(int)
	 */
	public byte[] getBytes(int paramIndex) throws SQLException {
		return stmt.getBytes(paramIndex);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.CallableStatement#getBytes(java.lang.String)
	 */
	public byte[] getBytes(String paramName) throws SQLException {
		return stmt.getBytes(paramName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.CallableStatement#getClob(int)
	 */
	public Clob getClob(int paramIndex) throws SQLException {
		return stmt.getClob(paramIndex);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.CallableStatement#getClob(java.lang.String)
	 */
	public Clob getClob(String paramName) throws SQLException {
		return stmt.getClob(paramName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.CallableStatement#getDate(int, java.util.Calendar)
	 */
	public Date getDate(int paramIndex, Calendar arg1) throws SQLException {
		return stmt.getDate(paramIndex);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.CallableStatement#getDate(int)
	 */
	public Date getDate(int paramIndex) throws SQLException {
		return stmt.getDate(paramIndex);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.CallableStatement#getDate(java.lang.String, java.util.Calendar)
	 */
	public Date getDate(String paramName, Calendar arg1) throws SQLException {
		return stmt.getDate(paramName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.CallableStatement#getDate(java.lang.String)
	 */
	public Date getDate(String paramName) throws SQLException {
		return stmt.getDate(paramName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.CallableStatement#getDouble(int)
	 */
	public double getDouble(int paramIndex) throws SQLException {
		return stmt.getDouble(paramIndex);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.CallableStatement#getDouble(java.lang.String)
	 */
	public double getDouble(String paramName) throws SQLException {
		return stmt.getDouble(paramName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.CallableStatement#getFloat(int)
	 */
	public float getFloat(int paramIndex) throws SQLException {
		return stmt.getFloat(paramIndex);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.CallableStatement#getFloat(java.lang.String)
	 */
	public float getFloat(String paramName) throws SQLException {
		return stmt.getFloat(paramName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.CallableStatement#getInt(int)
	 */
	public int getInt(int paramIndex) throws SQLException {
		return stmt.getInt(paramIndex);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.CallableStatement#getInt(java.lang.String)
	 */
	public int getInt(String paramName) throws SQLException {
		return stmt.getInt(paramName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.CallableStatement#getLong(int)
	 */
	public long getLong(int paramIndex) throws SQLException {
		return stmt.getLong(paramIndex);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.CallableStatement#getLong(java.lang.String)
	 */
	public long getLong(String paramName) throws SQLException {
		return stmt.getLong(paramName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.CallableStatement#getObject(int, java.util.Map)
	 */
	public Object getObject(int paramIndex, Map arg1) throws SQLException {
		return stmt.getObject(paramIndex);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.CallableStatement#getObject(int)
	 */
	public Object getObject(int paramIndex) throws SQLException {
		return stmt.getObject(paramIndex);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.CallableStatement#getObject(java.lang.String, java.util.Map)
	 */
	public Object getObject(String paramName, Map arg1) throws SQLException {
		return stmt.getObject(paramName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.CallableStatement#getObject(java.lang.String)
	 */
	public Object getObject(String paramName) throws SQLException {
		return stmt.getObject(paramName);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.CallableStatement#getRef(int)
	 */
	public Ref getRef(int paramIndex) throws SQLException {
		return stmt.getRef(paramIndex);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.CallableStatement#getRef(java.lang.String)
	 */
	public Ref getRef(String paramName) throws SQLException {
		return stmt.getRef(paramName);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.CallableStatement#getShort(int)
	 */
	public short getShort(int paramIndex) throws SQLException {
		return stmt.getShort(paramIndex);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.CallableStatement#getShort(java.lang.String)
	 */
	public short getShort(String paramName) throws SQLException {
		return stmt.getShort(paramName);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.CallableStatement#getString(int)
	 */
	public String getString(int paramIndex) throws SQLException {
		return stmt.getString(paramIndex);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.CallableStatement#getString(java.lang.String)
	 */
	public String getString(String paramName) throws SQLException {
		return stmt.getString(paramName);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.CallableStatement#getTime(int, java.util.Calendar)
	 */
	public Time getTime(int paramIndex, Calendar arg1) throws SQLException {
		return stmt.getTime(paramIndex);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.CallableStatement#getTime(int)
	 */
	public Time getTime(int paramIndex) throws SQLException {
		return stmt.getTime(paramIndex);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.CallableStatement#getTime(java.lang.String, java.util.Calendar)
	 */
	public Time getTime(String paramName, Calendar arg1) throws SQLException {
		return stmt.getTime(paramName);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.CallableStatement#getTime(java.lang.String)
	 */
	public Time getTime(String paramName) throws SQLException {
		return stmt.getTime(paramName);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.CallableStatement#getTimestamp(int, java.util.Calendar)
	 */
	public Timestamp getTimestamp(int paramIndex, Calendar arg1) throws SQLException {
		return stmt.getTimestamp(paramIndex);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.CallableStatement#getTimestamp(int)
	 */
	public Timestamp getTimestamp(int paramIndex) throws SQLException {
		return stmt.getTimestamp(paramIndex);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.CallableStatement#getTimestamp(java.lang.String, java.util.Calendar)
	 */
	public Timestamp getTimestamp(String paramName, Calendar arg1) throws SQLException {
		return stmt.getTimestamp(paramName);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.CallableStatement#getTimestamp(java.lang.String)
	 */
	public Timestamp getTimestamp(String paramName) throws SQLException {
		return stmt.getTimestamp(paramName);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.CallableStatement#getURL(int)
	 */
	public URL getURL(int paramIndex) throws SQLException {
		return stmt.getURL(paramIndex);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.CallableStatement#getURL(java.lang.String)
	 */
	public URL getURL(String paramName) throws SQLException {
		return stmt.getURL(paramName);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.CallableStatement#registerOutParameter(int, int, int)
	 */
	public void registerOutParameter(int paramIndex, int sqlType, int scale) throws SQLException {
		stmt.registerOutParameter(paramIndex, sqlType, scale);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.CallableStatement#registerOutParameter(int, int, java.lang.String)
	 */
	public void registerOutParameter(int paramIndex, int sqlType, String typeName) throws SQLException {
		stmt.registerOutParameter(paramIndex, sqlType, typeName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.CallableStatement#registerOutParameter(int, int)
	 */
	public void registerOutParameter(int paramIndex, int sqlType) throws SQLException {
		stmt.registerOutParameter(paramIndex, sqlType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.CallableStatement#registerOutParameter(java.lang.String, int, int)
	 */
	public void registerOutParameter(String paramName, int sqlType, int scale) throws SQLException {
		stmt.registerOutParameter(paramName, sqlType, scale);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.CallableStatement#registerOutParameter(java.lang.String, int, java.lang.String)
	 */
	public void registerOutParameter(String paramName, int sqlType, String typeName) throws SQLException {
		stmt.registerOutParameter(paramName, sqlType, typeName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.CallableStatement#registerOutParameter(java.lang.String, int)
	 */
	public void registerOutParameter(String paramName, int sqlType) throws SQLException {
		stmt.registerOutParameter(paramName, sqlType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.CallableStatement#wasNull()
	 */
	public boolean wasNull() throws SQLException {
		return stmt.wasNull();
	}

}
