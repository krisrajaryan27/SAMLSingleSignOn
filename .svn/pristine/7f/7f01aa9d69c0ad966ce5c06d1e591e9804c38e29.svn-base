package com.talentPool.common.db;

import java.sql.*;

import com.talentPool.common.Logger.TPLogger;

/**
 * This class helps handle database transactions. To execute two queries in a
 * transaction, this class will be used thus:
 * 
 * <pre>
 *  DBTransaction trans = null;
 *  try{
 *  	DBTransaction trans = new DBTransaction();
 *  	try{
 *  		DataQuery dq1 = new DataQuery(&quot;dQuery1&quot;, trans);
 *  		dq1.setInt(1, param11);
 *  		...
 *  		dq1.execute();
 *  	}
 *  	catch (SQLException sqle1) {
 *  		// first query of the transaction failed. Exit
 *  		throw new UserDefinedException();
 *  	}
 * 
 *  	DataQuery dq2 = new DataQuery(&quot;dQuery2&quot;, trans);
 *  	dq2.setInt(1, param21);
 *  	...
 *  	dq2.execute();
 *  	trans.commit();
 *  }
 *  catch (SQLException sqle2) {
 *  	// Either the second query, or the commit() failed.
 *  	// Rollback and throw exception.
 *  	try{
 *  		trans.rollback();
 *  	}
 *  	catch (Exception e){
 *  	}
 *  	throw new UserDefinedException();
 *  }
 *  catch(DBTransactionCreationException dbce){
 *  	// The transaction creation itself failed
 *  	// Throw user defined exception
 *  	throw new UserDefinedException();
 *  }
 *  finally{
 *         // Internally free the connection used
 *         // by this transaction and reset auto_commit
 *         // by calling trans.release();
 *  	if (trans!=null) trans.release();
 *  }
 * </pre>
 * 
 * @author shivprasad
 */
public class DBTransaction {
	private Connection con;

	/**
	 * Creates a new instance of DBTransaction
	 * 
	 * @throws DBTransactionCreationException
	 */
	public DBTransaction() throws SQLException {
		try {
			this.con = DBManager.getConnection();
			con.setAutoCommit(false);
		} catch (SQLException sqle) {
			TPLogger.getLogger().error("Error Creating DBTransaction ", sqle);
			throw sqle;
		} 

	}

	/**
	 * Returns the connection internally used by this DBTransaction.
	 * 
	 * @return
	 */
	public Connection getConnection() {
		return this.con;
	}

	/**
	 * Commits the transaction.
	 * 
	 * @throws SQLException
	 */
	public void commit() throws SQLException {
		con.commit();
	}

	public boolean getAutoCommit() throws SQLException {
		return con.getAutoCommit();
	}

	/**
	 * Rolls back the transaction.
	 * 
	 * @throws SQLException
	 */
	public void rollback() throws SQLException {
		try {
			con.rollback();
		} catch (SQLException e) {
			throw e;
		}
	}

	/**
	 * Releases the connection used by this transaction back to the pool. ALWAYS
	 * call this method in your <i>finally</i> clause.
	 */
	public void release() {
		if (con != null) {
			try {
				TPLogger.getLogger().debug("releasing connection");
				con.setAutoCommit(true);
				DBManager.release(con);
				this.con=null;
			} catch (SQLException e) {
				TPLogger.getLogger().error("Error", e);
			}
		}

	}
}
