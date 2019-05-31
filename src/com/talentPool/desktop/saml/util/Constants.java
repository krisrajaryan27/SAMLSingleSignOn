package com.talentPool.desktop.saml.util;

public class Constants {
	
    public final static String ENTITYID = "entityID";

    public final static String FORMATS = "nameID.formats";

    public final static String CONSUMER = "assertion.consumer.service";
    
    public final static String IDP_FOLDER = "idp_directory_location";

    public final static String LOGOUT = "single.logout.service";
    
    public final static String KEYSTORE_NAME = "keystore.name";
    
    public final static String KEYSTORE_CERT_ALIAS = "keystore.cert.alias";
    
    public final static String KEYSTORE = "keystore";

    public final static String STOREPASS = "storepass";

    public final static String KEYPASS = "keypass";
    
    public final static String AUTHN_CONTEXT_CLASS_REF="authnContextClassRef";
    
    public final static String WANT_ASSERTIONS_SIGNED="wantAssertionsSigned";

	public final static String AUTHN_REQUESTS_SIGNED="authnRequestsSigned";

    public final static String CERT_ALIAS = "certificate.alias";

    public final static String PROTOCOL = "urn:oasis:names:tc:SAML:2.0:protocol";

    public final static String ASSERTION = "urn:oasis:names:tc:SAML:2.0:assertion";

    public final static String SAML_URL_SERVLET_PREFIX="com.talentpool.plugin.samlauth.saml_url_servlet_prefix";
    
    public final static String ISSUER = "Issuer";

    public final static String NS_PREFIX = "saml2sp";
    
    public final static String ISSUER_NS_PREFIX = "saml2";

    public final static String AUTHN_REQUEST = "AuthnRequest";

    public final static String RELAY = "RelayState";

    public final static String SAML_AUTHN_RESPONSE_PARAMETER_NAME = "SAMLResponse";

    public final static String SAML_AUTHN_REQUEST_PARAMETER_NAME = "SAMLRequest";
    
    public final static Integer ALOWED_CLOCK_DRIFT = 180; // 3 min in seconds
    
    public final static String SINGLELOGOUT = "com.talentpool.plugin.samlauth.singlelogout";
    
    public final static String LOGOUT_REASON="urn:oasis:names:tc:SAML:2.0:logout:user";
    

	// NameID Formats	
	public final static String NAMEID_EMAIL_ADDRESS = "urn:oasis:names:tc:SAML:1.1:nameid-format:emailAddress";
	public final static String NAMEID_X509_SUBJECT_NAME = "urn:oasis:names:tc:SAML:1.1:nameid-format:X509SubjectName";
	public final static String NAMEID_WINDOWS_DOMAIN_QUALIFIED_NAME = "urn:oasis:names:tc:SAML:1.1:nameid-format:WindowsDomainQualifiedName";
	public final static String NAMEID_UNSPECIFIED = "urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified";
	public final static String NAMEID_KERBEROS = "urn:oasis:names:tc:SAML:2.0:nameid-format:kerberos";
	public final static String NAMEID_ENTITY = "urn:oasis:names:tc:SAML:2.0:nameid-format:entity";
	public final static String NAMEID_TRANSIENT = "urn:oasis:names:tc:SAML:2.0:nameid-format:transient";
	public final static String NAMEID_PERSISTENT = "urn:oasis:names:tc:SAML:2.0:nameid-format:persistent";
	public final static String NAMEID_ENCRYPTED = "urn:oasis:names:tc:SAML:2.0:nameid-format:encrypted";
	
	// Attribute Name Formats
	public final static String ATTRNAME_FORMAT_UNSPECIFIED = "urn:oasis:names:tc:SAML:2.0:attrname-format:unspecified";
	public final static String ATTRNAME_FORMAT_URI = "urn:oasis:names:tc:SAML:2.0:attrname-format:uri";
	public final static String ATTRNAME_FORMAT_BASIC = "urn:oasis:names:tc:SAML:2.0:attrname-format:basic";

	// Namespaces
	public final static String NS_SAML = "urn:oasis:names:tc:SAML:2.0:assertion";
	public final static String NS_SAMLP = "urn:oasis:names:tc:SAML:2.0:protocol";
	public final static String NS_SOAP = "http://schemas.xmlsoap.org/soap/envelope/";
	public final static String NS_MD = "urn:oasis:names:tc:SAML:2.0:metadata";
	public final static String NS_XS = "http://www.w3.org/2001/XMLSchema";
	public final static String NS_XSI = "http://www.w3.org/2001/XMLSchema-instance";
	public final static String NS_XENC = "http://www.w3.org/2001/04/xmlenc#";
	public final static String NS_DS = "http://www.w3.org/2000/09/xmldsig#";

	// Bindings
	public final static String BINDING_HTTP_POST = "urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST";
	public final static String BINDING_HTTP_REDIRECT = "urn:oasis:names:tc:SAML:2.0:bindings:HTTP-Redirect";
	public final static String BINDING_HTTP_ARTIFACT = "urn:oasis:names:tc:SAML:2.0:bindings:HTTP-Artifact";
	public final static String BINDING_SOAP = "urn:oasis:names:tc:SAML:2.0:bindings:SOAP";
	public final static String BINDING_DEFLATE = "urn:oasis:names:tc:SAML:2.0:bindings:URL-Encoding:DEFLATE";

	// Auth Context Class
	public final static String AC_UNSPECIFIED = "urn:oasis:names:tc:SAML:2.0:ac:classes:unspecified";
	public final static String AC_PASSWORD = "urn:oasis:names:tc:SAML:2.0:ac:classes:Password";
	public final static String AC_X509 = "urn:oasis:names:tc:SAML:2.0:ac:classes:X509";
	public final static String AC_SMARTCARD = "urn:oasis:names:tc:SAML:2.0:ac:classes:Smartcard";
	public final static String AC_KERBEROS = "urn:oasis:names:tc:SAML:2.0:ac:classes:Kerberos";

	// Subject Confirmation
	public final static String CM_BEARER = "urn:oasis:names:tc:SAML:2.0:cm:bearer";
	public final static String CM_HOLDER_KEY = "urn:oasis:names:tc:SAML:2.0:cm:holder-of-key";
	public final static String CM_SENDER_VOUCHES = "urn:oasis:names:tc:SAML:2.0:cm:sender-vouches";

	// Status Codes
	public final static String STATUS_SUCCESS = "urn:oasis:names:tc:SAML:2.0:status:Success";
	public final static String STATUS_REQUESTER = "urn:oasis:names:tc:SAML:2.0:status:Requester";
	public final static String STATUS_RESPONDER = "urn:oasis:names:tc:SAML:2.0:status:Responder";
	public final static String STATUS_VERSION_MISMATCH = "urn:oasis:names:tc:SAML:2.0:status:VersionMismatch";
	public final static String STATUS_NO_PASSIVE = "urn:oasis:names:tc:SAML:2.0:status:NoPassive";
	public final static String STATUS_PARTIAL_LOGOUT = "urn:oasis:names:tc:SAML:2.0:status:PartialLogout";
	public final static String STATUS_PROXY_COUNT_EXCEEDED = "urn:oasis:names:tc:SAML:2.0:status:ProxyCountExceeded";

	// Canonization
	public final static String C14N = "http://www.w3.org/TR/2001/REC-xml-c14n-20010315";
	public final static String C14N_WC = "http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments";
	public final static String C14N11 = "http://www.w3.org/2006/12/xml-c14n11";
	public final static String C14N11_WC = "http://www.w3.org/2006/12/xml-c14n11#WithComments";
	public final static String C14NEXC = "http://www.w3.org/2001/10/xml-exc-c14n#";
	public final static String C14NEXC_WC = "http://www.w3.org/2001/10/xml-exc-c14n#WithComments";
	
    // Sign & Crypt   
	// https://www.w3.org/TR/xmlenc-core/#sec-Alg-MessageDigest
	// https://www.w3.org/TR/xmlsec-algorithms/#signature-method-uris
	// https://tools.ietf.org/html/rfc6931
	public final static String SHA1 = "http://www.w3.org/2000/09/xmldsig#sha1";
	public final static String SHA256 = "http://www.w3.org/2001/04/xmlenc#sha256";
	public final static String SHA384 = "http://www.w3.org/2001/04/xmldsig-more#sha384";
	public final static String SHA512 = "http://www.w3.org/2001/04/xmlenc#sha512";

	public final static String DSA_SHA1 = "http://www.w3.org/2000/09/xmldsig#dsa-sha1";
	public final static String RSA_SHA1 = "http://www.w3.org/2000/09/xmldsig#rsa-sha1";
	public final static String RSA_SHA256 = "http://www.w3.org/2001/04/xmldsig-more#rsa-sha256";
	public final static String RSA_SHA384 = "http://www.w3.org/2001/04/xmldsig-more#rsa-sha384";
	public final static String RSA_SHA512 = "http://www.w3.org/2001/04/xmldsig-more#rsa-sha512";
    
	public final static String TRIPLEDES_CBC = "http://www.w3.org/2001/04/xmlenc#tripledes-cbc";
	public final static String AES128_CBC = "http://www.w3.org/2001/04/xmlenc#aes128-cbc";
	public final static String AES192_CBC = "http://www.w3.org/2001/04/xmlenc#aes192-cbc";
	public final static String AES256_CBC = "http://www.w3.org/2001/04/xmlenc#aes256-cbc";
	public final static String RSA_1_5 = "http://www.w3.org/2001/04/xmlenc#rsa-1_5";
	public final static String RSA_OAEP_MGF1P = "http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p";
	
	public final static String ENVSIG = "http://www.w3.org/2000/09/xmldsig#enveloped-signature";
	
	//AuthenticationContextClassRef
	public final static String PASSWD_PROTECTED_CLASSREF = "urn:oasis:names:tc:SAML:2.0:ac:classes:PasswordProtectedTransport";
	public final static String URN_FED_WINDOWS_CLASSREF = "urn:federation:authentication:windows";
	
	private Constants() {
	      //not called
	}

}
