package com.talentPool.desktop.saml;

import static com.talentPool.desktop.saml.util.SAMLUtils.getSAMLMessageContext;

import java.io.IOException;
import java.rmi.ServerException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.opensaml.common.SAMLException;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.binding.SAMLMessageContext;
import org.opensaml.saml2.core.NameID;
import org.opensaml.saml2.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.talentPool.desktop.saml.util.Constants;
import com.talentPool.desktop.saml.util.Properties;

/**
 *
 * @author KrishnaV
 */
public class Consumer extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(Consumer.class);

	/**
	 * Processes AuthNResponses and LogoutRequests only.
	 *
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		final String responseMessage = request.getParameter(Constants.SAML_AUTHN_RESPONSE_PARAMETER_NAME);
		if (responseMessage != null || StringUtils.isNotBlank(responseMessage)) {
			log.debug("Response from Identity Provider is received. Check for SSO response: {}", responseMessage);
			ssoResponse(request, response);
		} else {
			log.warn("Received empty message");
			throw new ServerException("Received message is invalid");
		}
	}

	private void ssoResponse(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("Attempt to secure resource  is intercepted : {}", request.getRequestURL().toString());

		/*
		 * Check if response message is received from identity provider; In case
		 * of successful response system redirects user to relayState (initial)
		 * request
		 */
		try {
			SAMLMessageContext<Response, SAMLObject, NameID> samlMessageContext = getSAMLMessageContext(request,
					response);
			samlMessageContext
					.setLocalEntityId(Properties.getString(Constants.ENTITYID, request.getRequestURL().toString()));
			log.error(" SAML Message Context: "
					+ samlMessageContext.getLocalEntityId() + " Local Entity Id: " + request.getRequestURL().toString()
					+ " Metadata Provider: " + samlMessageContext.getMetadataProvider());
			String relayState = samlMessageContext.getRelayState();
			if (relayState == null) {
				relayState = getSelfRoutedURLNoQuery(request);
			}
			if (!relayState.isEmpty()) {
				samlMessageContext.setRelayState(relayState);
			}
			new SAMLResponseVerifier().verify(request,response,samlMessageContext);
		} catch (SAMLException | IOException e) {
			log.error("Error processing IdP response", e);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 *
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}

	/**
	 * Returns the routed URL of the current host + current view.
	 *
	 * @param request
	 *            HttpServletRequest object to be processed
	 *
	 * @return the current routed url
	 */
	public static String getSelfRoutedURLNoQuery(HttpServletRequest request) {
		String url = getSelfURLhost(request);
		String requestUri = request.getRequestURI();
		if (null != requestUri && !requestUri.isEmpty()) {
			url += requestUri;
		}
		return url;
	}

	/**
	 * Returns the protocol + the current host + the port (if different than
	 * common ports).
	 *
	 * @param request
	 *            HttpServletRequest object to be processed
	 *
	 * @return the HOST URL
	 */
	public static String getSelfURLhost(HttpServletRequest request) {
		String hostUrl = StringUtils.EMPTY;
		final int serverPort = request.getServerPort();
		if ((serverPort == 80) || (serverPort == 443) || serverPort == 0) {
			hostUrl = String.format("%s://%s", request.getScheme(), request.getServerName());
		} else {
			hostUrl = String.format("%s://%s:%s", request.getScheme(), request.getServerName(), serverPort);
		}
		return hostUrl;
	}

}
