/**
 * 
 */
package com.talentPool.lookupTalentpool.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.lookupTalentpool.manager.LookupTPManager;

/**
 * @author Shantanu
 *
 */
public class LookupTPShortlistCompareServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8737012727843547357L;

	public void init(ServletConfig servletConfig) throws ServletException{
		super.init(servletConfig);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
	
	public void destroy() {
	
	}
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{		
		TPLogger.getLogger().debug("**************** IN SERVLET : INSIDE PROCESS REQUEST ****************");
		try{
			PrintWriter out = response.getWriter();
			LookupTPManager ltm = new LookupTPManager();
			StringBuffer resultBuffer = new StringBuffer();
			
		    String applicantName = request.getParameter("applicantName");
		    String applicantEmail2 = request.getParameter("applicantEmail2");
		    String applicantEmail1 = request.getParameter("applicantEmail1");
		    String applicantCellPhone = request.getParameter("applicantCellPhone");
		    String applicantHomePhone = request.getParameter("applicantHomePhone");
		    String applicantWorkPhone = request.getParameter("applicantWorkPhone");
		    
			SimpleDataObject sdo = null;
			
			//Though all the applicant related data is fetched only Step Level is sent back in response. 
			List shortlistList = ltm.duplicateShortlistedFromLookup(applicantName, applicantEmail2, applicantEmail1, applicantCellPhone, applicantHomePhone, applicantWorkPhone);			
			for(int i=0;i<shortlistList.size();i++){
				sdo = (SimpleDataObject) shortlistList.get(i);				
				resultBuffer.append(sdo.getString("positionStepLevel"));
			}
			response.setContentType("text/html");
			response.setHeader("Pragma", "no-cache");
			response.addHeader("Expires", "0");
			response.addHeader("Cache-Control", "no-store");
			response.addHeader("X-Frame-Options", "SAMEORIGIN");
			response.addHeader("X-Content-Type-Options", "nosniff");
			response.addHeader("X-XSS-Protection", "1; mode=block");
			out.print(resultBuffer.toString());
			out.close();
			
		}catch (Exception e) {
			TPLogger.getLogger().debug(GlobalConstants.ERROR,e);
		}
	}
}
