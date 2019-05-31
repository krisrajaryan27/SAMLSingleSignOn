package com.talentPool.services.filter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.axis.transport.http.AxisServlet;

/**
 * @author Sachinm
 * Servlet that is used to display Access Denied Messaged for wsdl requests
 * 
 */
public class AxisFilterServlet extends AxisServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see org.apache.axis.transport.http.AxisServletBase#service(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (req.getParameterValues("wsdl") != null || req.getParameterValues("WSDL") != null) {
			String[] wsdlParam = req.getParameterValues("wsdl");
			if(wsdlParam.length < 1) {
				wsdlParam = req.getParameterValues("WSDL");
			}
			boolean returnNotFound = false;
			for (int i = 0; i < wsdlParam.length; i++) {
				returnNotFound |= (wsdlParam[i] != null && wsdlParam[i]
						.equals(""));
			}

			if (returnNotFound) {
				resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			} else {
				super.service(req, resp);
			}
		} else {
			super.service(req, resp);
		}
	}
}
