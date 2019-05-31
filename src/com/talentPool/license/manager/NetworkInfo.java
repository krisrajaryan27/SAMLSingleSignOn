/**
 * 
 */
package com.talentPool.license.manager;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.text.ParseException;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author pallavi
 * @date Jan 20, 2007
 */
public class NetworkInfo {
	private static final String WINDOWS = "Windows";
	private static final String LINUX = "Linux";
	
	public final String getMacAddress() throws IOException {
		String os = System.getProperty("os.name");
		try {
			if(os.startsWith(WINDOWS)) {
				return windowsParseMacAddress(windowsRunIpConfigCommand());
			} else if(os.startsWith(LINUX)) {
				return linuxParseMacAddress(linuxRunIfConfigCommand());
			} else {
				throw new IOException("unknown operating system: " + os);
			}
		} catch(ParseException ex) {
			ex.printStackTrace();
			throw new IOException(ex.getMessage());
		}
	} 
 
	/*
	 * Linux stuff
	 */
	private final String linuxParseMacAddress(String ipConfigResponse) throws ParseException {
		String localHost = null;
		try {
			localHost = InetAddress.getLocalHost().getHostAddress();
		} catch(java.net.UnknownHostException ex) {
			ex.printStackTrace();
			throw new ParseException(ex.getMessage(), 0);
		} 
		
		StringTokenizer tokenizer = new StringTokenizer(ipConfigResponse, "\n");
		String lastMacAddress = null;
		
		while(tokenizer.hasMoreTokens()) {
			String line = tokenizer.nextToken().trim();
			boolean containsLocalHost = line.indexOf(localHost) >= 0; 
			// see if line contains IP address
			if(containsLocalHost && lastMacAddress != null) {
				return lastMacAddress;
			} 
			// see if line contains MAC address
			int macAddressPosition = line.indexOf("HWaddr");
			if(macAddressPosition <= 0) continue; 
			String macAddressCandidate = line.substring(macAddressPosition + 6).trim();
			if(linuxIsMacAddress(macAddressCandidate)) {
				lastMacAddress = macAddressCandidate;
				continue;
			}
		} 
		ParseException ex = new ParseException
			("cannot read MAC address for " + localHost + " from [" + ipConfigResponse + "]", 0);
		ex.printStackTrace();
		throw ex;
	} 
 
	private final boolean linuxIsMacAddress(String macAddressCandidate) {
		// TODO: use a smart regular expression
		if(macAddressCandidate.length() != 17) return false;
		return true;
	} 
 
	private final String linuxRunIfConfigCommand() throws IOException {
		Process p = Runtime.getRuntime().exec("ifconfig");
		InputStream stdoutStream = new BufferedInputStream(p.getInputStream()); 
		StringBuffer buffer= new StringBuffer();
		for (;;) {
			int c = stdoutStream.read();
			if (c == -1) break;
			buffer.append((char)c);
		}
		String outputText = buffer.toString(); 
		stdoutStream.close(); 
		return outputText;
	} 
 
	/*
	 * Windows stuff
	 */
	private final String windowsParseMacAddress(String ipConfigResponse) throws ParseException {		
		StringTokenizer tokenizer = new StringTokenizer(ipConfigResponse, "\n");
		StringBuffer lastMacAddress = new StringBuffer(); 
		while(tokenizer.hasMoreTokens()) {
			String line = tokenizer.nextToken().trim(); 			
			// see if line contains MAC address
			int macAddressPosition = line.indexOf(":");
			if(macAddressPosition <= 0) continue; 
			String macAddressCandidate = line.substring(macAddressPosition + 1).trim();
			if(windowsIsMacAddress(macAddressCandidate)) {
				if (lastMacAddress.length() > 0) {
					lastMacAddress.append(",");
				}
				lastMacAddress.append(macAddressCandidate);
			}
		} 		
		return lastMacAddress.toString();
	} 
 
	private final boolean windowsIsMacAddress(String macAddressCandidate) {		
		Pattern macPattern = Pattern.compile("[0-9a-fA-F]{2}-[0-9a-fA-F]{2}-[0-9a-fA-F]{2}-[0-9a-fA-F]{2}-[0-9a-fA-F]{2}-[0-9a-fA-F]{2}");
        Matcher m = macPattern.matcher(macAddressCandidate);
        return m.matches();
	} 
 
	private final String windowsRunIpConfigCommand() throws IOException {
		Process p = Runtime.getRuntime().exec("ipconfig /all");
		InputStream stdoutStream = new BufferedInputStream(p.getInputStream()); 
		StringBuffer buffer= new StringBuffer();
		for (;;) {
			int c = stdoutStream.read();
			if (c == -1) break;
			buffer.append((char)c);
		}
		String outputText = buffer.toString(); 
		stdoutStream.close(); 
		return outputText;
	}
}
