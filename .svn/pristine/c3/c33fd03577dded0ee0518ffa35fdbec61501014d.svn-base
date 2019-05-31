package com.talentPool.desktop.saml;

import java.io.IOException;
import java.util.BitSet;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.opensaml.common.SAMLException;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.binding.SAMLMessageContext;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.Attribute;
import org.opensaml.saml2.core.AttributeStatement;
import org.opensaml.saml2.core.Conditions;
import org.opensaml.saml2.core.NameID;
import org.opensaml.saml2.core.Response;
import org.opensaml.saml2.core.Status;
import org.opensaml.saml2.core.StatusCode;
import org.opensaml.security.SAMLSignatureProfileValidator;
import org.opensaml.xml.Configuration;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.signature.Signature;
import org.opensaml.xml.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

import com.talentPool.audit.action.AuditAction;
import com.talentPool.audit.constants.AuditConstants;
import com.talentPool.common.base.TPDispatchAction;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.Utils;
import com.talentPool.desktop.saml.context.CircleOfTrust;
import com.talentPool.desktop.saml.context.IdP;
import com.talentPool.desktop.saml.store.SAMLRequestStore;
import com.talentPool.desktop.saml.store.SAMLSessionManager;
import com.talentPool.desktop.saml.util.SAMLUtils;
import com.talentPool.user.dataobject.LoginData;
import com.talentPool.user.manager.LoginManager;
import com.talentPool.user.manager.PermissionSet;
import com.talentPool.user.manager.SessionManager;
import com.talentPool.user.manager.SingleSignOnManager;

public class SAMLResponseVerifier extends TPDispatchAction {

	private static final Logger log = LoggerFactory.getLogger(SAMLResponseVerifier.class);

	private final SAMLRequestStore samlRequestStore = SAMLRequestStore.getInstance();

	public void verify(HttpServletRequest request, HttpServletResponse response,
			final SAMLMessageContext<Response, SAMLObject, NameID> samlMessageContext)
			throws SAMLException, IOException {

		final Response samlResponse = samlMessageContext.getInboundSAMLMessage();

		final IdP idp = CircleOfTrust.getInstance().getIdP(samlResponse.getIssuer().getValue());

		log.debug("SAML Response message: {}", SAMLUtils.SAMLObjectToString(samlResponse));

		final Element responseDOM = samlResponse.getDOM();
		try {
			Configuration.getUnmarshallerFactory().getUnmarshaller(responseDOM).unmarshall(responseDOM);
		} catch (UnmarshallingException e) {
			throw new SAMLException(e);
		}

		if (samlResponse.isSigned()) {
			try {
				new SAMLSignatureProfileValidator().validate(samlResponse.getSignature());
				log.error("Canonicalization algorithm: {}", samlResponse.getSignature().getCanonicalizationAlgorithm());
				log.error("Signature algorithm: {}", samlResponse.getSignature().getSignatureAlgorithm());
				idp.getSignatureValidatorChain().validate(samlResponse.getSignature());
			} catch (ValidationException e) {
				log.error("SAML signature profile validation has been failed", e);
				throw new SAMLException(e);
			}
		}

		verifyInResponseTo(samlResponse);
		final Status status = samlResponse.getStatus();
		final StatusCode statusCode = status.getStatusCode();
		final String statusCodeURI = statusCode.getValue();

		if (!statusCodeURI.equals(StatusCode.SUCCESS_URI)) {
			log.warn("Incorrect SAML message code : {} ", statusCode.getStatusCode().getValue());
			throw new SAMLException("Incorrect SAML message code : " + statusCode.getValue());
		}

		if (samlResponse.getAssertions().isEmpty()) {
			log.error("Response does not contain any acceptable assertions");
			throw new SAMLException("Response does not contain any acceptable assertions");
		}

		final Assertion assertion = samlResponse.getAssertions().get(0);
		// Assertion must be signed correctly
		if (!assertion.isSigned()) {
			throw new SAMLException("Assertion must be signed");
		}

		// ------------------------------------
		// Verify signature
		// ------------------------------------
		if (CircleOfTrust.getInstance().getSp().isWantAssertionsSigned()) {
			try {
				log.debug("Verify assertion signature .....");
				final Signature sig = assertion.getSignature();
				idp.getSignatureValidatorChain().validate(sig);

			} catch (ValidationException e) {
				log.error("Signature not valid", e);
				throw new SAMLException(e);
			}
		}

		/*verifyConditions(assertion.getConditions(), samlMessageContext);*/
		String audience = assertion.getConditions().getAudienceRestrictions().get(0).getAudiences().get(0)
				.getAudienceURI();
		String status1 = samlResponse.getStatus().getStatusCode().getValue();

		for (AttributeStatement attributeStatement : assertion.getAttributeStatements()) {
			log.debug("Attribute Statement values size: " + attributeStatement.getAttributes().size());
		}
		AttributeStatement assertstatementNode = assertion.getAttributeStatements().get(0);
		String attributeValue = "";
		for (Attribute atr : assertstatementNode.getAttributes()) {
			log.debug("Attribute Names: " + atr.getName());
			if ("username".equalsIgnoreCase(atr.getName())) {
				XMLObject value = atr.getAttributeValues().get(0);
				attributeValue = value.getDOM().getTextContent();
				log.debug("Attribute values text: " + value.getDOM().getTextContent());
			}
		}

		LoginManager loginManager = new LoginManager();
		String userId = loginManager.getUserIdFromEmail(attributeValue);
		LoginData loginData = new LoginData();
		loginData.setUserId(userId);
		loginData = loginManager.getUserFromUserName(attributeValue);
		log.debug(" Audience: " + audience + " status1: " + status1 + " AttributeValue: " + attributeValue + " UserId: "
				+ userId + " LoginData: " + loginData);
		SessionManager sessionManager = new SessionManager();
		HttpSession session = request.getSession(true);
		session.setAttribute("userId", userId);
		if (request.getParameter("JSESSIONID") != null) {
			Cookie userCookie = new Cookie("JSESSIONID", request.getParameter("JSESSIONID"));
			userCookie.setMaxAge(24 * 60 * 60);
			userCookie.setSecure(true);
			userCookie.setHttpOnly(true);
			response.addCookie(userCookie);
		} else {
			String sessionId = session.getId();
			Cookie userCookie = new Cookie("JSESSIONID", sessionId);
			userCookie.setMaxAge(24 * 60 * 60);
			userCookie.setSecure(true);
			userCookie.setHttpOnly(true);
			response.addCookie(userCookie);
		}
		Cookie cookie = new Cookie("tal" + loginData.getUserId(), loginData.getUserId());
		cookie.setMaxAge(24 * 60 * 60);
		cookie.setSecure(true);
		cookie.setHttpOnly(true);
		response.addCookie(cookie);
		SAMLSessionManager.getInstance().createSAMLSession(request.getSession(), samlMessageContext);
		log.debug("User has been successfully authenticated in idP. Redirect to initial requested resource {}",
				samlMessageContext.getRelayState());
		log.debug(request.getContextPath());
		BitSet permissions = loginManager.getUserPermissionsBitSet(loginData.getUserId());
		BitSet reportBitSet = loginManager.getUserReportsBitSet(loginData.getUserId());
		PermissionSet permissionSet = new PermissionSet(permissions);
		String userPassword = loginData.getPassword();
		SingleSignOnManager.addUserIp(userId, getClientIpAddr(request));
		saveToken(request);
		AuditAction auditAction = new AuditAction();
		loginManager.updateLastLogin(userId);
		auditAction.insertAuditInfo(TPLabels.getLabel("common.type_logged_in"), AuditConstants.TYPE_LOGGED_IN, userId,
				AuditConstants.AUDIT_LOGGED_IN, userId, null, null, null, true, getClientIpAddr(request));
		if (!Utils.isBlankOrNull(loginData.getTimeZone())) {
			sessionManager.setSessionVariables(session, loginData, permissionSet, reportBitSet,
					loginData.isValidLdapUser(), loginData.getRoleId(), userPassword, loginData.getTimeZone());
		} else {
			sessionManager.setSessionVariables(session, loginData, permissionSet, reportBitSet,
					loginData.isValidLdapUser(), loginData.getRoleId(), userPassword);
		}
		response.sendRedirect(response.encodeRedirectURL("/TalentPool/dashboard.do?mode=dashboard"));
	}

	private void verifyInResponseTo(final Response samlResponse) {
		final String key = samlResponse.getInResponseTo();

		if (!samlRequestStore.exists(key)) {
			log.error("Response does not match an authentication request");
			throw new RuntimeException("Response does not match an authentication request");
		}

		samlRequestStore.removeRequest(samlResponse.getInResponseTo());
	}

	/*private void verifyConditions(final Conditions conditions,
			final SAMLMessageContext<Response, SAMLObject, NameID> samlMessageContext) throws SAMLException {
		verifyExpirationConditions(conditions);
		verifyAudienceRestrictions(conditions.getAudienceRestrictions(), samlMessageContext);
	}

	private void verifyExpirationConditions(final Conditions conditions) throws SAMLException {
		log.debug("Verifying conditions");

		final DateTime currentTime = new DateTime(DateTimeZone.UTC);
		final DateTime notBefore = conditions.getNotBefore();
		if ((notBefore != null) && currentTime.isBefore(notBefore)) {
			throw new SAMLException("Assertion is not conformed with notBefore condition");
		}

		final DateTime notOnOrAfter = conditions.getNotOnOrAfter();
		if ((notOnOrAfter != null) && currentTime.isAfter(notOnOrAfter)) {
			throw new SAMLException("Assertion is not conformed with notOnOrAfter condition");
		}
	}*/

	/*private void verifyAudienceRestrictions(final List<AudienceRestriction> audienceRestrictions,
			final SAMLMessageContext<?, ?, ?> samlMessageContext) throws SAMLException {
	}*/

}
