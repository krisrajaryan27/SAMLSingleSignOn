package com.talentPool.common.db.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBManager;

/**
 * @author PraveenK
 * @since  Feb 2, 2012
 */
public class DBPoolServlet extends HttpServlet {

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		try {
			TPLogger.getLogger().debug("Pool Status:");
			TPLogger.getLogger().debug(DBManager.getPoolStatus());
			out.println("Pool Status: ");
			out.println(DBManager.getPoolStatus());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
