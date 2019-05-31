package com.talentPool.desktop.saml;

import java.io.IOException;

import org.opensaml.common.SAMLObject;
import org.opensaml.saml2.binding.encoding.HTTPRedirectDeflateEncoder;
import org.opensaml.ws.message.encoder.MessageEncodingException;
import org.opensaml.xml.util.Base64;
import org.opensaml.xml.util.XMLHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SAML 2.0 HTTP Redirect encoder using the DEFLATE encoding method.
 *
 * This encoder only supports DEFLATE compression and DSA-SHA1 and RSA-SHA1 signatures.
 */
public class HTTPRedirectEncoder extends HTTPRedirectDeflateEncoder {

    private static final Logger log = LoggerFactory.getLogger(SAMLResponseVerifier.class);

    @Override
    protected String deflateAndBase64Encode(SAMLObject message) throws MessageEncodingException {
        log.debug("Base64 encoding SAML message (NO Deflation)");
        try {
            return Base64.encodeBytes(
                    XMLHelper.nodeToString(marshallMessage(message)).getBytes("UTF-8"),
                    Base64.DONT_BREAK_LINES);
        } catch (IOException e) {
            throw new MessageEncodingException("Unable to DEFLATE and Base64 encode SAML message", e);
        }
    }
}
