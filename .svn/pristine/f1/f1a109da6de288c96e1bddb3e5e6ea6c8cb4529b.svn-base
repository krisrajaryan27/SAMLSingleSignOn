/**
 * 
 */
package com.talentPool.desktop.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.EncryptionUtils;
import com.talentPool.desktop.constants.DesktopConstants;
import com.talentPool.ldap.manager.LDAPManager;
import com.talentPool.user.UserConstants;
import com.talentPool.user.dataobject.LoginData;
import com.talentPool.user.exception.InvalidLoginException;
import com.talentPool.user.manager.LoginManager;


/**
 * @author shivprasad
 * 
 */
public class LoginServlet extends HttpServlet {
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	/**
	 * Destroys the servlet.
	 */
	public void destroy() {

	}

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean success = false;
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		LoginData loginData = null;
		try {
			String userName = request.getParameter("userName");
			String password = request.getParameter("password");

			loginData = new LoginData(userName, password);
			LoginManager loginManager = new LoginManager();
			loginData = loginManager.login(loginData);
			
			boolean simpleValidation = true;
            boolean validLdapUser = false;
            
            if (GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_LDAP_ENABLED).equals(GlobalConstants.ENABLED)) {
                simpleValidation = false;
                LDAPManager manager = new LDAPManager();
                validLdapUser = manager.isValidLdapUserOnAnyServer(userName, password);
                // if invalid login for admin check for normal
                // authentication
                if (!validLdapUser) {
                      if (loginData.getUserId().equals(UserConstants.ADMIN_ID)) {
                            simpleValidation = true;
                      } else if (loginData.getIsUserLdapSetting().equals(UserConstants.IS_USER_LDAP_SETTING_DISABLED)) { // When User can skip ldap validations.
                            simpleValidation = true;
                      } else {
                            loginData = null;
                      }
                }
          }

          if (simpleValidation) {
                String encryptedPassword = EncryptionUtils.encryptString(password);
                if (!(encryptedPassword.equals(loginData.getPassword()))) {
                      loginData = null;
                }
          }          

          if (loginData != null) {
                success = true;
          }
		} catch (InvalidLoginException e) {
			TPLogger.getLogger().error("ERROR ==>" + e.getMessage());
		} catch (Exception e) {
			TPLogger.getLogger().error("ERROR", e);
		}
		if (success) {
			out.print(DesktopConstants.SUCCESS +" "+ loginData.getUserName() +" "+ loginData.getUserId());
		} else {
			out.print(DesktopConstants.FAIL);
		}

	}

	/**
	 * Handles the HTTP <code>GET</code> method.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);

	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 */
	public String getServletInfo() {
		return "Short description";
	}

}
