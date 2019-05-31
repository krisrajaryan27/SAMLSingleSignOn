package com.talentPool.recaptcha;

import java.net.URLEncoder;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.recaptcha.http.HttpLoader;
import com.talentPool.recaptcha.http.SimpleHttpLoader;
import com.talentPool.recaptcha.properties.ReCaptchaProperties;

public class ReCaptchaImpl implements ReCaptcha {

	private HttpLoader httpLoader = new SimpleHttpLoader();
	
	public static final String HTTP_SERVER = ReCaptchaProperties.getProperty("http.server");
	public static final String HTTPS_SERVER = ReCaptchaProperties.getProperty("https.server");
	public static final String VERIFY_URL = ReCaptchaProperties.getProperty("verify.url");
	
	private String privateKey = ReCaptchaProperties.getProperty("private.key");
	private String publicKey = ReCaptchaProperties.getProperty("public.key");
	private String recaptchaServer = HTTP_SERVER;
	
	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
	public void setRecaptchaServer(String recaptchaServer) {
		this.recaptchaServer = recaptchaServer;
	}
	public void setHttpLoader(HttpLoader httpLoader) {
		this.httpLoader  = httpLoader;
	}
	
	public ReCaptchaResponse checkAnswer(String remoteAddr, String challenge, String response) {
		boolean valid = false;
		String errorMessage = "";
		try{
			String postParameters = "privatekey=" + URLEncoder.encode(privateKey) + "&remoteip=" + URLEncoder.encode(remoteAddr) +
			"&challenge=" + URLEncoder.encode(challenge) + "&response=" + URLEncoder.encode(response);

			String message = httpLoader.httpPost(VERIFY_URL, postParameters);
	
			if (message == null) {
				return new ReCaptchaResponse(false, "Null read from server.");
			}
	
			String[] a = message.split("\r?\n");
			if (a.length < 1) {
				return new ReCaptchaResponse(false, "No answer returned from recaptcha: " + message);
			}
			valid = "true".equals(a[0]);
			errorMessage = null;
			if (!valid) {
				if (a.length > 1)
					errorMessage = a[1];
				else
					errorMessage = "recaptcha4j-missing-error-message";
			}
		}catch (Exception e) {
			TPLogger.getLogger().error("Error while checking entered Captcha code from web site", e);
		}
		return new ReCaptchaResponse(valid, errorMessage);
	}
}
