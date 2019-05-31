package com.talentPool.common.db;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.talentPool.common.Logger.TPLogger;

public class EncyptRecord {

	private static final int BATCH_SIZE = 1000;

	public static void update_Tp_Applicants(Connection conn)
			throws InvalidKeyException, NoSuchAlgorithmException,
			InvalidKeySpecException, NoSuchPaddingException,
			InvalidParameterSpecException, IllegalBlockSizeException,
			BadPaddingException, UnsupportedEncodingException,
			InvalidAlgorithmParameterException {
		Statement stmt = null;
		TPLogger.getLogger().info("TP_Applicant table update started");
		try {
			String sql = "select count(applicant_id) as cnt from tp_applicants";
			stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(sql);
			result.next();
			int count = result.getInt("cnt");

			String sqlQuery = "SELECT * FROM tp_applicants";
			String updateQuery = "UPDATE tp_applicants SET passport_number = ?,current_ctc= ? ,expected_ctc= ?,offered_basic= ?, offered_ctc= ?,date_of_birth= ? WHERE applicant_id = ? ";
			runExecutorService(conn, sqlQuery, updateQuery, 2, count,"TP_Applicants");
			TPLogger.getLogger().info("TP_Applicant table update completed ");
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException xe) {
				xe.printStackTrace();
			}
			TPLogger.getLogger().error("Error occured while Updating TP_Applicants ", e);
		} catch (InterruptedException e) {
			TPLogger.getLogger().error("Error occured while Updating TP_Applicants ", e);
		} finally {
			closeStatement("TP_Applicants", stmt);
		}
	}

	private static void closeStatement(String tableName, Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				TPLogger.getLogger().error("Error occured while Closing" + tableName+ "  Statement ", e);
			}
		}
	}

	public static void update_Tp_Applicant_Current_Details(Connection conn)
			throws InvalidKeyException, NoSuchAlgorithmException,
			InvalidKeySpecException, NoSuchPaddingException,
			InvalidParameterSpecException, IllegalBlockSizeException,
			BadPaddingException, UnsupportedEncodingException,
			InvalidAlgorithmParameterException {
		Statement stmt = null;
		TPLogger.getLogger().info("Tp_Applicant_Current_Details table update started");
		try {
			String sql = "select count(applicant_id) as cnt from tp_applicant_current_details";
			stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(sql);
			result.next();
			int count = result.getInt("cnt");

			String sqlQuery = "select * from tp_applicant_current_details";
			String updateQuery = "Update tp_applicant_current_details set current_basic=? where applicant_id=?";
			runExecutorService(conn, sqlQuery, updateQuery, 3, count,"Tp_Applicant_Current_Details");
			TPLogger.getLogger().info("Tp_Applicant_Current_Details table update completed");
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException xe) {
				xe.printStackTrace();
			}
			TPLogger.getLogger().error("Error occured while Updating Tp_Applicant_Current_Details ",e);
		} catch (InterruptedException e) {
			TPLogger.getLogger().error(	"error thread interrupted while encrypting records");
		} finally {
			closeStatement("Tp_Applicant_Current_Details", stmt);

		}
	}

	public static void update_Tp_Applicants_Text_Resume(Connection conn)
			throws InvalidKeyException, NoSuchAlgorithmException,
			InvalidKeySpecException, NoSuchPaddingException,
			InvalidParameterSpecException, IllegalBlockSizeException,
			BadPaddingException, UnsupportedEncodingException,
			InvalidAlgorithmParameterException {

		TPLogger.getLogger().info("Tp_Applicants_Text_Resume table update started");
		Statement stmt = null;
		try {
			String sql = "select count(applicant_text_resumes_id) as cnt from tp_applicant_text_resumes";
			stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(sql);
			result.next();
			int count = result.getInt("cnt");
			String sqlQuery = "select * from tp_applicant_text_resumes ";
			String updateQuery = "Update tp_applicant_text_resumes set applicant_text_resume=? where applicant_id=?";
			runExecutorService(conn, sqlQuery, updateQuery, 1, count,"Tp_Applicants_Text_Resume");
			TPLogger.getLogger().info("Tp_Applicants_Text_Resume table update completed");
			conn.commit();
		} catch (InterruptedException e) {
			TPLogger.getLogger().error("error thread interrupted while encrypting records");
		} catch (SQLException e) {
			TPLogger.getLogger().error("error querying for count of tables");
			try {
				conn.rollback();
			} catch (SQLException e1) {
				TPLogger.getLogger().error("error closing sql statement while encrypting textresumes");
			}
		} finally {
			closeStatement("Tp_Applicants_Text_Resume", stmt);
		}
	}

	public static void update_Tp_Cr_Candidate_Master(Connection conn)
			throws InvalidKeyException, NoSuchAlgorithmException,
			InvalidKeySpecException, NoSuchPaddingException,
			InvalidParameterSpecException, IllegalBlockSizeException,
			BadPaddingException, UnsupportedEncodingException,
			InvalidAlgorithmParameterException {
		Statement stmt = null;
		TPLogger.getLogger().info("Tp_Cr_Candidate_Master table update started");
		try {
			String sql = "select count(applicant_id) as cnt from tp_cr_candidate_master";
			stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(sql);
			result.next();
			int count = result.getInt("cnt");

			String sqlQuery = "select * from tp_cr_candidate_master";
			String updateQuery = "Update  tp_cr_candidate_master set expected_ctc= ?,current_ctc= ?,passport_number= ?,date_of_birth= ? where applicant_id= ?";
			runExecutorService(conn, sqlQuery, updateQuery, 4, count,"Tp_Cr_Candidate_Master");
			TPLogger.getLogger().info("Tp_Cr_Candidate_Master table update completed");
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException xe) {
				xe.printStackTrace();
			}
			TPLogger.getLogger().error("Error occured while Updating Tp_Cr_Candidate_Master ", e);
		} catch (InterruptedException e) {
			TPLogger.getLogger().error("error thread interrupted while encrypting records");
		} finally {
			closeStatement(" Tp_Cr_Candidate_Master", stmt);

		}
	}

	public static void update_Tp_Applicant_Joining_History(Connection conn)
			throws InvalidKeyException, NoSuchAlgorithmException,
			InvalidKeySpecException, NoSuchPaddingException,
			InvalidParameterSpecException, IllegalBlockSizeException,
			BadPaddingException, UnsupportedEncodingException,
			InvalidAlgorithmParameterException {
		Statement stmt = null;
		TPLogger.getLogger().info("Tp_Applicant_Joining_History table update started");
		try {
			String sql = "select count(applicant_joining_history_id) as cnt from tp_applicant_joining_history";
			stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(sql);
			result.next();
			int count = result.getInt("cnt");
			
			String sqlQuery = "select * from tp_applicant_joining_history";
			String updateQuery = "Update tp_applicant_joining_history set offered_ctc= ? where applicant_joining_history_id= ?";
			runExecutorService(conn, sqlQuery, updateQuery, 5, count,"Tp_Applicant_Joining_History");
			TPLogger.getLogger().info("Tp_Applicant_Joining_History table update completed");
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException xe) {
				xe.printStackTrace();
			}
			TPLogger.getLogger().error("Error occured while Updating Tp_Applicant_Joining_History ",e);
			e.printStackTrace();
		} catch (InterruptedException e) {
			TPLogger.getLogger().error("error thread interrupted while encrypting records");
		} finally {
			closeStatement(" Tp_Applicant_Joining_History", stmt);
		}
	}

	public static void update_Tp_Excel_Import(Connection conn)
			throws InvalidKeyException, NoSuchAlgorithmException,
			InvalidKeySpecException, NoSuchPaddingException,
			InvalidParameterSpecException, IllegalBlockSizeException,
			BadPaddingException, UnsupportedEncodingException,
			InvalidAlgorithmParameterException {
		Statement stmt = null;
		TPLogger.getLogger().info("Tp_Excel_Import table update started");
		try {
			String sql = "select count(session_id) as cnt from tp_excel_import";
			stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(sql);
			result.next();
			int count = result.getInt("cnt");

			String sqlQuery = "select * from tp_excel_import";
			String updateQuery = "Update tp_excel_import  set expected_ctc=?,current_ctc=? where row_id= ?";
			runExecutorService(conn, sqlQuery, updateQuery, 6, count,"Tp_Excel_Import");
			TPLogger.getLogger().info("Tp_Excel_Import table update completed");
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException xe) {
				xe.printStackTrace();
			}
			TPLogger.getLogger().error("Error occured while Updating Tp_Excel_Import ", e);
		} catch (InterruptedException e) {
			TPLogger.getLogger().error("error thread interrupted while encrypting records");
		} finally {
			closeStatement(" Tp_Excel_Import", stmt);

		}
	}

	public static void update_Tp_Bulk_Import_Session(Connection conn)
			throws InvalidKeyException, NoSuchAlgorithmException,
			InvalidKeySpecException, NoSuchPaddingException,
			InvalidParameterSpecException, IllegalBlockSizeException,
			BadPaddingException, UnsupportedEncodingException,
			InvalidAlgorithmParameterException {
		Statement stmt = null;
		TPLogger.getLogger().info("Tp_Bulk_Import_Session table update started");
		try {
			String sql = "select count(session_id) as cnt from tp_bulk_import_sessions";
			stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(sql);
			result.next();
			int count = result.getInt("cnt");

			String sqlQuery = "select * from tp_bulk_import_sessions";
			String updateQuery = "Update tp_bulk_import_sessions  set expected_ctc=?,current_ctc=? where session_id= ?";
			runExecutorService(conn, sqlQuery, updateQuery,7,count,"Tp_Bulk_Import_Session");
			TPLogger.getLogger().info("Tp_Bulk_Import_Session table update completed");
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException xe) {
				xe.printStackTrace();
			}
			TPLogger.getLogger().error("Error occured while Updating Tp_Bulk_Import_Session ", e);
		} catch (InterruptedException e) {
			TPLogger.getLogger().error("error thread interrupted while encrypting records");
		} finally {
			closeStatement(" Tp_Bulk_Import_Session", stmt);
		}
	}

	public static void encyptTableRecord() {
		Connection conn = null;
		try {
			conn = DBManager.getConnection();

			update_Tp_Applicants(conn);
			update_Tp_Applicant_Current_Details(conn);
			update_Tp_Applicants_Text_Resume(conn);
			update_Tp_Cr_Candidate_Master(conn);
			update_Tp_Applicant_Joining_History(conn);
			update_Tp_Excel_Import(conn);
			update_Tp_Bulk_Import_Session(conn);

		} catch (SQLException se) {
			TPLogger.getLogger().error("Error while establishing connection ",
					se);
			se.printStackTrace();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while updating records ", e);
			e.printStackTrace();
		} finally {

			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				TPLogger.getLogger().error("Error while closing connection ",
						se);
				se.printStackTrace();
			}
		}
	}

	private static void runExecutorService(Connection conn, String sqlQuery,
			String updateQuery, int operation, int count,String tableName)
			throws InterruptedException {
		ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		for (int i = 0; i < count; i = i + BATCH_SIZE) {
			service.submit(new MyEncrypterThread(conn, sqlQuery, updateQuery,operation, i, BATCH_SIZE,tableName));
		}
		service.shutdown();
		service.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
	}

	static class MyEncrypterThread implements Runnable {

		private int start;
		private int batch;
		private Connection conn;
		private String sqlQuery;
		private String updateQuery;
		private int operation;
		private String tableName;

		public MyEncrypterThread(Connection conn, String sqlQuery,
				String updateQuery, int operation, int start, int batch,String tableName) {
			this.start = start;
			this.sqlQuery = sqlQuery;
			this.batch = batch;
			this.conn = conn;
			this.updateQuery = updateQuery;
			this.operation = operation;
			this.tableName = tableName;
		}

		@Override
		public void run() {
			Statement stmt = null;
			try {
				TPLogger.getLogger().info("inside thread " + Thread.currentThread().getName()+ " start: " + start);
				conn.setAutoCommit(false);
				stmt = conn.createStatement();
				String sql = sqlQuery + " limit " + start + "," + batch;
				ResultSet rs = stmt.executeQuery(sql);
				PreparedStatement prest = conn.prepareStatement(updateQuery);
				switch (operation) {
				case 1:
					IterateLogic.ITERATE_TEXT_RESUME.resultSetIterator(rs,prest);
					break;
				case 2:
					IterateLogic.ITERATE_TP_APPLICANTS.resultSetIterator(rs,prest);
					break;
				case 3:
					IterateLogic.ITERATE_APPLICANT_CURRENT_DETAILS.resultSetIterator(rs, prest);
					break;
				case 4:
					IterateLogic.ITERATE_CR_CANDIDATE_MASTER.resultSetIterator(rs, prest);
					break;
				case 5:
					IterateLogic.ITERATE_APPLICANT_JOINING_HISTORY.resultSetIterator(rs, prest);
					break;
				case 6:
					IterateLogic.ITERATE_EXCEL_IMPORT.resultSetIterator(rs, prest);
					break;
				case 7:
					IterateLogic.ITERATE_BULK_IMPORT_SESSIONS.resultSetIterator(rs, prest);
					break;
				default:
					break;
				}
			} catch (SQLException e) {
				TPLogger.getLogger().error("error encrypting from thread batch: " + start);
				try {
					conn.rollback();
				} catch (SQLException xe) {
					TPLogger.getLogger().error("Error occured while Updating Tp_Applicants_Text_Resume ",e);
				}
			} finally {
				closeStatement(tableName, stmt);

			}
		}

	}

}
