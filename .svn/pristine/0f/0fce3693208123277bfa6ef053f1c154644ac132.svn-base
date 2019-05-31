package com.talentPool.desktop.saml.util;

import java.io.IOException;

import org.slf4j.LoggerFactory;

public class Properties {

	private static final org.slf4j.Logger log = LoggerFactory.getLogger(Properties.class);

	private static final java.util.Properties prop = new java.util.Properties();

	static {
		try {
			prop.load(Properties.class.getResourceAsStream("/services.properties"));
		} catch (IOException e) {
			log.warn("Error loading global properties", e);
		}
	}

	public static String getString(final String key) {
		return prop.getProperty(key);
	}

	public static String getString(final String key, final String def) {
		return prop.getProperty(key, def);
	}

	public static boolean getBoolean(final String key, final boolean def) {
		final String res = prop.getProperty(key);
		return res == null ? def : Boolean.parseBoolean(res);
	}

	public static String[] getStringArray(final String key, final String[] def) {
		final String res = prop.getProperty(key);
		return res == null ? def : res.split(" ");
	}
}
