package com.talentPool.encryption;

import java.io.IOException;
import java.security.KeyPair;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Sachinm
 * Servlet implementation class EncryptionServlet
 *
 */
public class EncryptionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public EncryptionServlet() {
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		if (request.getParameter("generateKeypair") != null) {

			JCryptionUtil jCryptionUtil = new JCryptionUtil();

			KeyPair keys = null;
			if (request.getSession().getAttribute("keys") == null) {
				keys = jCryptionUtil.generateKeypair(512);
			} else {
				keys = (KeyPair)request.getSession().getAttribute("keys");
			}
			request.getSession().setAttribute("keys", keys);

			StringBuffer output = new StringBuffer();

			String e = JCryptionUtil.getPublicKeyExponent(keys);
			String n = JCryptionUtil.getPublicKeyModulus(keys);
			String md = String.valueOf(JCryptionUtil.getMaxDigits(512));

			output.append("{\"e\":\"");
			output.append(e);
			output.append("\",\"n\":\"");
			output.append(n);
			output.append("\",\"maxdigits\":\"");
			output.append(md);
			output.append("\"}");

			output.toString();
			response.getOutputStream().print(
					output.toString().replaceAll("\r", "").replaceAll("\n", "")
							.trim());
		} else {
			response.getOutputStream().print(String.valueOf(false));
		}
	}

}