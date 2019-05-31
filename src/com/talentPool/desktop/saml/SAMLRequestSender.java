package com.talentPool.desktop.saml;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;

import org.joda.time.DateTime;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.SAMLObjectBuilder;
import org.opensaml.common.SAMLVersion;
import org.opensaml.common.binding.BasicSAMLMessageContext;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.binding.encoding.HTTPRedirectDeflateEncoder;
import org.opensaml.saml2.core.AuthnContextClassRef;
import org.opensaml.saml2.core.AuthnContextComparisonTypeEnumeration;
import org.opensaml.saml2.core.AuthnRequest;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.LogoutRequest;
import org.opensaml.saml2.core.RequestedAuthnContext;
import org.opensaml.saml2.core.impl.AuthnRequestBuilder;
import org.opensaml.saml2.core.impl.IssuerBuilder;
import org.opensaml.ws.message.encoder.MessageEncodingException;
import org.opensaml.ws.transport.http.HTTPTransportUtils;
import org.opensaml.ws.transport.http.HttpServletRequestAdapter;
import org.opensaml.ws.transport.http.HttpServletResponseAdapter;
import org.opensaml.xml.Configuration;
import org.opensaml.xml.XMLObjectBuilder;
import org.opensaml.xml.security.credential.Credential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.talentPool.desktop.saml.context.CircleOfTrust;
import com.talentPool.desktop.saml.context.IdP;
import com.talentPool.desktop.saml.context.SP;
import com.talentPool.desktop.saml.store.SAMLRequestStore;
import com.talentPool.desktop.saml.util.Binding;
import com.talentPool.desktop.saml.util.Constants;
import com.talentPool.desktop.saml.util.SAMLUtils;

public class SAMLRequestSender {

	private static final Logger log = LoggerFactory.getLogger(SAMLRequestSender.class);

	public void sendSAMLAuthRequest(final HttpServletRequest request, final HttpServletResponse servletResponse,
			final IdP idp) throws Exception {
		SP sp = CircleOfTrust.getInstance().getSp();
		AuthnRequest authnRequest = buildRequest(sp, idp);
		HttpServletResponseAdapter responseAdapter = new HttpServletResponseAdapter(servletResponse,
				request.isSecure());

		String relayState = request.getParameter(Constants.RELAY);
		final BasicSAMLMessageContext<SAMLObject, AuthnRequest, SAMLObject> context = getMessageContext(idp,
				authnRequest, CircleOfTrust.getInstance().getSp().getCredential(), relayState, responseAdapter);
		HTTPTransportUtils.addNoCacheHeaders(responseAdapter);
		HTTPTransportUtils.setUTF8Encoding(responseAdapter);
		try {
			new HTTPRedirectDeflateEncoder().encode(context);
		} catch (MessageEncodingException e) {
			log.error("Error encoding AuthN Request", e);
		}
	}

	public AuthnRequest buildRequest(final SP sp, final IdP idp) throws ServletException {
		IssuerBuilder issuerBuilder = (IssuerBuilder) getSamlObject(Issuer.DEFAULT_ELEMENT_NAME);
		Issuer issuer = issuerBuilder.buildObject();
		issuer.setValue(sp.getEntityid());
		// Creation of AuthRequestObject
		DateTime issueInstant = new DateTime();
		AuthnRequestBuilder authRequestBuilder = (AuthnRequestBuilder) getSamlObject(AuthnRequest.DEFAULT_ELEMENT_NAME);
		AuthnRequest authRequest = authRequestBuilder.buildObject();
		// Store SAML 2.0 authentication request
		authRequest.setID(SAMLRequestStore.getInstance().storeRequest());
		authRequest.setIsPassive(false);
		authRequest.setForceAuthn(false);
		authRequest.setIssueInstant(issueInstant);
		authRequest.setProtocolBinding(SAMLConstants.SAML2_POST_BINDING_URI);
		authRequest.setAssertionConsumerServiceURL(sp.getAcs());
		authRequest.setIssuer(issuer);
		authRequest.setVersion(SAMLVersion.VERSION_20);
		authRequest.setRequestedAuthnContext(buildAuthnContext());
		authRequest.setDestination(idp.getSSOLocation(Binding.REDIRECT).getLocation());
		log.debug("Entity Id: " + sp.getEntityid() + " ACS: " + sp.getAcs() + " AuthnRequest Destination: "
				+ authRequest.getDestination());
		log.debug("SAML Authentication message : {} ", SAMLUtils.SAMLObjectToString(authRequest));
		return authRequest;
	}

	@SuppressWarnings("unchecked")
	public <T extends SAMLObject> T build(QName qName) {
		return (T) Configuration.getBuilderFactory().getBuilder(qName).buildObject(qName);
	}

	private BasicSAMLMessageContext<SAMLObject, AuthnRequest, SAMLObject> getMessageContext(final IdP idp,
			final AuthnRequest authnRequest, final Credential credential, final String relayState,
			final HttpServletResponseAdapter responseAdapter) {

		final BasicSAMLMessageContext<SAMLObject, AuthnRequest, SAMLObject> context = new BasicSAMLMessageContext<>();

		context.setPeerEntityEndpoint(idp.getSSOLocation(Binding.REDIRECT));
		context.setOutboundSAMLMessage(authnRequest);
		context.setOutboundMessageTransport(responseAdapter);
		context.setOutboundSAMLMessageSigningCredential(credential);
		context.setRelayState(relayState);

		return context;
	}

	private static XMLObjectBuilder getSamlObject(QName qname) {
		return Configuration.getBuilderFactory().getBuilder(qname);
	}

	private RequestedAuthnContext buildAuthnContext() {
		@SuppressWarnings("unchecked")
		SAMLObjectBuilder<AuthnContextClassRef> contextRefBuilder = (SAMLObjectBuilder<AuthnContextClassRef>) getSamlObject(
				AuthnContextClassRef.DEFAULT_ELEMENT_NAME);
		AuthnContextClassRef classRef = contextRefBuilder.buildObject();
		classRef.setAuthnContextClassRef(Constants.PASSWD_PROTECTED_CLASSREF);
		//classRef.setAuthnContextClassRef(Constants.URN_FED_WINDOWS_CLASSREF);
		@SuppressWarnings("unchecked")
		SAMLObjectBuilder<RequestedAuthnContext> builder = (SAMLObjectBuilder<RequestedAuthnContext>) getSamlObject(
				RequestedAuthnContext.DEFAULT_ELEMENT_NAME);
		RequestedAuthnContext authnContext = builder.buildObject();
		authnContext.setComparison(AuthnContextComparisonTypeEnumeration.EXACT);
		authnContext.getAuthnContextClassRefs().add(classRef);
		return authnContext;
	}

	private static BasicSAMLMessageContext<LogoutRequest, ?, ?> getMessageContextFromRequest(HttpServletRequest request) {
        // Unpack the <LogoutRequest> from the request
        BasicSAMLMessageContext<LogoutRequest, ?, ?> messageContext = new BasicSAMLMessageContext<LogoutRequest, SAMLObject, SAMLObject>();
        messageContext.setInboundMessageTransport(new HttpServletRequestAdapter(request));
        return messageContext;
    }
}
