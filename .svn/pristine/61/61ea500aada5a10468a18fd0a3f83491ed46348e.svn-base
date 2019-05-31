package com.talentPool.fetchExc;

/*  fetchMail.java
 Copyright (C) 2004 Juhani Rautiainen

 This program is free software; you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation; either version 2 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.HttpException;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import org.w3c.dom.*;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.common.utils.Utils;
import com.talentPool.inbox.dataobject.InboxData;

import java.util.*;
import java.net.*;
import java.io.*;
import java.text.*;

enum SendType {
	SMTP, MBOX, PROCMAIL
};

public class fetchMail {
	ArrayList<MailList> files = new ArrayList<MailList>();

	String inboxAddr;

	private String fromSrv;

	private SendType sendMethod = SendType.SMTP;

	private String serverPath;

	private String dstMailSrv;

	private String dstAddr;

	private String username;

	private String password;

	private String domain;

	private String fbapath;

	private boolean allMsgs;

	private boolean justList;

	private boolean justMark;

	private boolean secureConn;

	private String mboxFile;

	private String propFile = null;

	private boolean fixFrom;

	private String fakeFrom;

	private boolean noEightBitMime;

	private boolean debug;

	private boolean ISA;

	private String destination;

	private boolean proxyOn;

	private String proxyHost;

	private int proxyPort;

	private InboxData inboxData = null;

	public fetchMail(String pPropFile) {
		propFile = pPropFile;
	}

	public fetchMail(String pPropFile, InboxData inboxData) {
		propFile = pPropFile;
		this.inboxData = inboxData;
	}

	public void fetchAll() {

		Date now = new Date();
		Format formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
		TPLogger.getLogger().debug("Starting fetchExc v2.0 " + formatter.format(now));
		// changed by shiv to create lock file in dir where mbox file is created
		// if (alreadyRunning("fetchExc.lock")) { to following
		if (alreadyRunning(Utils.concatFilePath(TPApplicationProperties.getProperty("installation.path"), "fetchExc.lock"))) {
			TPLogger.getLogger().debug("Already running. File fetchExc.lock exists.");
			return;
		}
		if (getProperties() == false) {
			return;
		}
		HttpClient client = new HttpClient();
		String fromSrvIP = null;
		String connType;

		if (proxyOn) {
			if (debug) {
				TPLogger.getLogger().debug("--- Using proxy=" + proxyHost + ":" + proxyPort);
			}
			client.getHostConfiguration().setProxy(proxyHost, proxyPort);
		}

		if (secureConn) {
			connType = "https://";
		} else {
			connType = "http://";
		}

		String prootPath = connType + fromSrv + "/" + serverPath + "/";

		for (int l = 0; l < 3; l++) {
			try {
				fromSrvIP = InetAddress.getByName(fromSrv).getHostAddress();
				break;
			} catch (UnknownHostException uhe) {
			}
		}

		if (fromSrvIP == null) {
			TPLogger.getLogger().debug("Can't resolve name. Exiting");
			return;
		}
		NTCredentials creds = new NTCredentials(username, password, "localhost", domain);
		int status = 0;
		GetMethod get = null;

		client.getState().setCredentials(new AuthScope(fromSrv, AuthScope.ANY_PORT), creds);

		String rootPath = getRootPath(client, prootPath);

		if (rootPath == null) {
			rootPath = prootPath; // Path not found maybe
		}
		// FBA is needed
		PropFindMethod propfind = new PropFindMethod(rootPath);
		try {
			propfind.setRequestEntity(new StringRequestEntity(getInboxMsg(), null, null));
			status = client.executeMethod(propfind);
		} catch (HttpException httpe) {
			TPLogger.getLogger().error("HttpException in fetchAll()):" + httpe.getMessage());
		} catch (IOException ioe) {
			TPLogger.getLogger().error("IO error in fetchAll():" + ioe.getMessage());
		}
		if (status == 440 || status == 404) {
			// Try to authenticate with FBA because that's
			// what status 440 is supposed to mean

			status = doFbaAuth(client, connType);
			if (status != 302) {
				// It didn't work. FBApath wrong?
				TPLogger.getLogger().debug("Form based authentication failed. Possibly wrong path:" + fbapath);
				return;
			}

			// Let's try find rootpath again
			rootPath = getRootPath(client, prootPath);
			if (rootPath == null) {
				TPLogger.getLogger().debug("Can't find path for user. Exiting after trying FBA\n");
				return;
			}

			// Let's try again with property find
			propfind = new PropFindMethod(rootPath);
			try {
				propfind.setRequestEntity(new StringRequestEntity(getInboxMsg(), null, null));
				status = client.executeMethod(propfind);
			} catch (HttpException httpe) {
				TPLogger.getLogger().error("HttpException in fetchAll()):" + httpe.getMessage());
			} catch (IOException ioe) {
				TPLogger.getLogger().error("IO error in fetchAll():" + ioe.getMessage());
			}
		}
		if (debug) {
			TPLogger.getLogger().debug("---Inbox reply:");
			try {
				TPLogger.getLogger().debug(propfind.getResponseBodyAsString());
			} catch (IOException ioe) {
				TPLogger.getLogger().error("IO error in fetchAll():" + ioe.getMessage());
			}
			TPLogger.getLogger().debug("---Inbox reply end");
		}

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		Document doc = null;
		try {
			builder = factory.newDocumentBuilder();
			doc = builder.parse(propfind.getResponseBodyAsStream());
		} catch (ParserConfigurationException pce) {
			TPLogger.getLogger().error("ParserConfigurationException in fetchAll()):" + pce.getMessage());
			return;
		} catch (SAXException saxe) {
			TPLogger.getLogger().error("IOException in fetchAll()):" + saxe.getMessage());
			return;
		} catch (IOException ioe) {
			TPLogger.getLogger().error("IOException in fetchAll()):" + ioe.getMessage());
			return;
		}
		searchInbox(doc);
		if (inboxAddr == null) {
			TPLogger.getLogger().debug("Couldn't find inbox for user " + username + ". Exiting!");
			return;
		}

		boolean doFetch = true;
		int loopCount = 0; // how many 100 message batches
		while (doFetch) {
			SearchMethod search = null;
			try {
				search = new SearchMethod(inboxAddr);

				search.setRequestEntity(new StringRequestEntity(getListMailMsg(), null, null));
				status = client.executeMethod(search);
				if (debug) {
					TPLogger.getLogger().debug(status + "\n------\n" + search.getResponseBodyAsString());
				}
			} catch (HttpException httpe) {
				TPLogger.getLogger().error("HttpException in fetchAll()):" + httpe.getMessage());
				return;
			} catch (IOException ioe) {
				TPLogger.getLogger().error("IO error in fetchAll():" + ioe.getMessage());
				return;
			}

			try {
				builder = factory.newDocumentBuilder();
				doc = builder.parse(search.getResponseBodyAsStream());
			} catch (ParserConfigurationException pce) {
				TPLogger.getLogger().error("ParserConfigurationException in fetchAll()):" + pce.getMessage());
				return;
			} catch (SAXException saxe) {
				TPLogger.getLogger().error("IOException in fetchAll()):" + saxe.getMessage());
				return;
			} catch (IOException ioe) {
				TPLogger.getLogger().error("IOException in fetchAll()):" + ioe.getMessage());
				return;
			}
			searchHrefs(doc);

			MailList list;

			String messStr;
			if (files.size() == 1) {
				messStr = "message";
			} else {
				messStr = "messages";
			}
			// If there are more than messages we only get first hundred
			// so prepare to check if there are more messages.
			doFetch = false;
			if (files.size() == 100 && justList == false && !(justMark == true && allMsgs == true)) {
				doFetch = true;
			} else {
				doFetch = false;
			}

			if (files.size() > 0) {
				TPLogger.getLogger().debug(files.size() + " " + messStr + " for " + username + " at " + fromSrv);
			} else {
				if (loopCount < 1)
					TPLogger.getLogger().debug("No mail.");
			}
			for (int k = 0; k < files.size(); k++) {
				int msgNum = k + 1 + loopCount * 100;
				list = files.get(k);

				list.setFilename(fixFileName(list.getFilename()));
				get = new GetMethod(list.getFilename());
				get.setRequestHeader("Translate", "F");
				try {
					status = client.executeMethod(get);
				} catch (HttpException httpe) {
					TPLogger.getLogger().error("HttpException in fetchAll()):" + httpe.getMessage());
					return;
				} catch (IOException ioe) {
					TPLogger.getLogger().error("IO error in fetchAll():" + ioe.getMessage());
					return;
				}

				if (status != 200) {
					TPLogger.getLogger().debug("Getting message failed status=" + status);
					TPLogger.getLogger().debug("Message ID:" + list.getFilename());
				}
				boolean sentOk = false;
				if (!justList) {
					MsgForwarder send = null;
					if (status == 200) {
						switch (sendMethod) {
						case SMTP:
							send = new SendSmtp(fromSrv, dstMailSrv, dstAddr, fixFrom, fakeFrom, noEightBitMime);
							break;
						case MBOX:
							send = new SendMbox(fromSrv, mboxFile);
							break;
						case PROCMAIL:
							send = new SendProcMail(dstAddr);
							break;
						}
					}
					try {
						sentOk = send.processMessage((files.get(k)).getFromaddr(), get.getResponseBodyAsStream());
					} catch (IOException ioe) {
						TPLogger.getLogger().error("IO error in fetchAll():" + ioe.getMessage());
					}
				}
				if (sentOk) {
					TPLogger.getLogger().debug("Message " + msgNum + " sent OK");
					if (!justList) {
						if (justMark) {
							markAsRead(client, list.getFilename());
						} else {
							removeMsg(client, list.getFilename());
						}
					}
				} else
					TPLogger.getLogger().debug("Message " + msgNum + " not sent");

			}
			files.removeAll(files);
			loopCount++;
		}
		if (get != null) {
			get.releaseConnection();
		}
	}

	private void searchHrefs(Node node) {
		// Lets get to response level
		NodeList nodes = node.getChildNodes().item(0).getChildNodes();
		if (nodes == null) {
			return;
		}
		for (int i = 0; i < nodes.getLength(); i++) {
			Node current = nodes.item(i);
			if (current.getNodeName().indexOf(":response") > 0) {
				loopResponse(current);
			}
		}
	}

	private void loopResponse(Node node) {
		MailList list = new MailList();
		NodeList nodes = node.getChildNodes();
		if (nodes == null) {
			return;
		}
		for (int i = 0; i < nodes.getLength(); i++) {
			Node current = nodes.item(i);
			if (current.getNodeName().indexOf(":href") > 0) {
				list.setFilename(current.getChildNodes().item(0).getNodeValue());
			}
			if (current.getNodeName().indexOf(":propstat") > 0) {
				list.setFromaddr(loopPropStat(current));
			}
		}
		files.add(list);
	}

	private String loopPropStat(Node node) {
		NodeList nodes = node.getChildNodes();
		if (nodes == null) {
			return null;
		}
		for (int i = 0; i < nodes.getLength(); i++) {
			Node current = nodes.item(i);
			if (current.getNodeName().indexOf(":prop") > 0) {
				return loopProp(current);
			}
		}
		return null;
	}

	private String loopProp(Node node) {
		NodeList nodes = node.getChildNodes();
		if (nodes == null) {
			return null;
		}
		for (int i = 0; i < nodes.getLength(); i++) {
			Node current = nodes.item(i);
			if (current.getNodeName().indexOf(":fromemail") > 0) {
				if (current.getChildNodes().item(0) == null) {
					// No From??? Obvious spam with empty
					// line in headers. Another problem we
					// have to handle in E2K
					return null;
				}
				return current.getChildNodes().item(0).getNodeValue();
			}
		}
		return null;
	}

	private void searchInbox(Node node) {
		// Lets get to the <prop>-level straight away
		// TODO: Make it little bit more reliable
		try {
			NodeList nodes = node.getChildNodes().item(0).getChildNodes().item(0).getChildNodes().item(1).getChildNodes().item(1).getChildNodes();
			if (nodes == null) {
				return;
			}
			for (int i = 0; i < nodes.getLength(); i++) {
				Node current = nodes.item(i);
				if (current.getNodeName().indexOf(":inbox") > 0) {
					inboxAddr = current.getChildNodes().item(0).getNodeValue();
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Can't find inbox. Set debug=true for more information. Exiting");
			return;
		}
		return;
	}

	private boolean getProperties() {
		Properties prop = new Properties();
		String rPropFile;
		if (propFile != null) {
			rPropFile = propFile;
		} else {
			rPropFile = "fetchExc.properties";
		}

		try {
			FileInputStream propfile = new FileInputStream(rPropFile);
			String propval;
			prop.load(propfile);
			propval = prop.getProperty("ExchangeServer");
			if (propval != null) {
				fromSrv = propval;
			} else {
				TPLogger.getLogger().debug("ExchangeServer property missing");
				return false;
			}
			propval = prop.getProperty("ExchangePath");
			if (propval != null) {
				serverPath = propval;
			} else {
				TPLogger.getLogger().debug("ExchangePath property missing");
				return false;
			}
			propval = prop.getProperty("MboxFile");
			if (propval != null) {
				sendMethod = SendType.MBOX;
				mboxFile = propval;
			} else {
				propval = prop.getProperty("ProcMail");
				if (propval != null) {
					if (propval.compareTo("true") == 0) {
						sendMethod = SendType.PROCMAIL;
					}
				}
				if (sendMethod == SendType.PROCMAIL) {
					propval = prop.getProperty("MailServer");
					if (propval != null) {
						dstMailSrv = propval;
					} else {
						TPLogger.getLogger().debug("MailServer property missing");
						return false;
					}
				}
				propval = prop.getProperty("DestinationAddress");
				if (propval != null) {
					dstAddr = propval;
				} else {
					TPLogger.getLogger().debug("DestinationAddress property missing");
					return false;
				}
			}
			propval = prop.getProperty("Username");
			if (propval != null) {
				username = propval;
			} else {
				TPLogger.getLogger().debug("Username property missing");
				return false;
			}
			propval = prop.getProperty("Password");
			if (propval != null) {
				password = propval;
			} else {
				password = inboxData.getPassword();
				if (password == null || password.trim() == "") {
					TPLogger.getLogger().debug("Password property missing");
					return false;
				}
			}
			propval = prop.getProperty("Domain");
			if (propval != null) {
				domain = propval;
			} else {
				TPLogger.getLogger().debug("Domain property missing");
				return false;
			}
			propval = prop.getProperty("FBApath");
			if (propval != null) {
				fbapath = propval;
			} else {
				// Let's use default if not set
				fbapath = "/exchweb/bin/auth/owaauth.dll";
			}

			ISA = false;
			propval = prop.getProperty("Destination");
			if (propval != null) {
				destination = propval;
				ISA = true;
			}

			secureConn = false;
			propval = prop.getProperty("Secure");
			if (propval != null && propval.compareTo("true") == 0) {
				secureConn = true;
			}

			allMsgs = false;
			propval = prop.getProperty("All");
			if (propval != null && propval.compareTo("true") == 0) {
				allMsgs = true;
			}

			justList = false;
			propval = prop.getProperty("List");
			if (propval != null && propval.compareTo("true") == 0) {
				justList = true;
			}

			justMark = true;
			propval = prop.getProperty("Delete");
			if (propval != null && propval.compareTo("true") == 0) {
				justMark = false;
			}
			fixFrom = false;
			propval = prop.getProperty("ForceFrom");
			if (propval != null && propval.compareTo("true") == 0) {
				fixFrom = true;
			}
			if (fixFrom) {
				propval = prop.getProperty("ForceFromAddr");
				fakeFrom = propval;
			}
			noEightBitMime = false;
			propval = prop.getProperty("NoEightBitMime");
			if (propval != null && propval.compareTo("true") == 0) {
				noEightBitMime = true;
			}
			proxyOn = false;
			propval = prop.getProperty("ProxyHost");
			if (propval != null) {
				proxyOn = true;
				proxyHost = propval;
			}
			if (proxyOn) {
				propval = prop.getProperty("ProxyPort");
				if (propval != null) {
					proxyPort = Integer.parseInt(propval);
				} else {
					proxyPort = 80;
				}
			}
			debug = false;
			propval = prop.getProperty("debug");
			if (propval != null && propval.compareTo("true") == 0) {
					debug = true;
			}
		} catch (IOException oie) {
			oie.printStackTrace();
			return false;
		}
		return true;

	}

	private String getInboxMsg() {
		StringBuffer strBuf = new StringBuffer(200);
		strBuf.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><D:propfind xmlns:D=\"DAV:\" xmlns:a=\"urn:schemas:httpmail:\">\r\n");
		strBuf.append("<D:prop>\r\n");
		strBuf.append("<a:inbox/>\r\n");
		strBuf.append("</D:prop>\r\n");
		strBuf.append("</D:propfind>\r\n");
		return strBuf.toString();
	}

	private String getListMailMsg() {
		StringBuffer strBuf = new StringBuffer(600);
		strBuf.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><searchrequest xmlns=\"DAV:\"><sql>\r\n");
		strBuf.append("SELECT \"urn:schemas:httpmail:fromemail\", \"urn:schemas:httpmail:read\" \r\n");
		strBuf.append("FROM \"\"\r\n");
		strBuf.append("WHERE &quot;DAV:iscollection&quot; = False AND &quot;DAV:ishidden&quot; = False");
		if (allMsgs) {
			strBuf.append("\r\n");
		} else {
			strBuf.append(" AND \"urn:schemas:httpmail:read\"= False\r\n");
		}
		strBuf.append("ORDER BY \"DAV:creationdate\"\r\n");
		strBuf.append("</sql></searchrequest>");
		return strBuf.toString();
	}

	private String getMarkAsReadMsg(String filename) {
		StringBuffer strBuf = new StringBuffer(300);
		strBuf
				.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><D:propertyupdate xmlns:D=\"DAV:\" xmlns:a=\"urn:schemas:httpmail:\" xmlns:T=\"urn:uuid:c2f41010-65b3-11d1-a29f-00aa00c14882/\">\r\n");
		strBuf.append("<D:target>\r\n");
		strBuf.append("<D:href>");
		strBuf.append(filename.substring(filename.lastIndexOf('/') + 1));
		strBuf.append("</D:href>\r\n");
		strBuf.append("</D:target>\r\n");
		strBuf.append("<D:set><D:prop>\r\n");
		strBuf.append("<a:read>1</a:read>\r\n");
		strBuf.append("</D:prop></D:set>\r\n");
		strBuf.append("</D:propertyupdate>\r\n");
		return strBuf.toString();
	}

	private String getDeleteMsg(String filename) {
		StringBuffer strBuf = new StringBuffer(300);
		strBuf.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><D:delete xmlns:D=\"DAV:\" xmlns:a=\"urn:schemas:httpmail:\" xmlns:T=\"urn:uuid:c2f41010-65b3-11d1-a29f-00aa00c14882/\">\r\n");
		strBuf.append("<D:target>\r\n");
		strBuf.append("<D:href>");
		strBuf.append(filename.substring(filename.lastIndexOf('/') + 1));
		strBuf.append("</D:href>\r\n");
		strBuf.append("</D:target>\r\n");
		strBuf.append("</D:delete>\r\n");

		return strBuf.toString();
	}

	private boolean alreadyRunning(String lockfile) {
		try {
			File flagFile = new File(lockfile);
			if (flagFile.createNewFile() == false) {
				return true;
			}

			flagFile.deleteOnExit();
		} catch (IOException e) {
			TPLogger.getLogger().error(e);
		}
		return false;
	}

	String findRootPath(String message) {
		String rootpath = null;
		// Let's try to find <base> where correct root path can be
		// found
		int start = message.indexOf("<BASE");
		if (start >= 0) {
			// find start of url
			int urlstart = message.indexOf('"', start) + 1;
			int urlstop = message.indexOf('"', urlstart);
			if (urlstart < urlstop) {
				rootpath = message.substring(urlstart, urlstop);
			}
		}
		if (debug) {
			TPLogger.getLogger().debug("---User url:" + rootpath);
		}
		return rootpath;
	}

	private String getRootPath(HttpClient client, String prootPath) {
		String rootPath = null;
		GetMethod get = new GetMethod(prootPath);
		get.setDoAuthentication(true);
		int status = 0;
		try {
			status = client.executeMethod(get);
		} catch (IOException ioe) {
			TPLogger.getLogger().debug("Exception in connection:" + ioe.getMessage());
			TPLogger.getLogger().debug("Exiting.");
			return "";
		}

		switch (status) {
		case 401:
			TPLogger.getLogger().debug("Authentication failed. Exiting");
			return null;
		case 404:
			TPLogger.getLogger().debug("Exchange user path doesn't exist. Exiting!");
			return null;

		default:
			TPLogger.getLogger().debug("Status=" + status + ". Exiting!");
			return null;
		case 200: // OK

		}
		String buffer;
		try {
			BufferedReader bodyReader = new BufferedReader(new InputStreamReader(get.getResponseBodyAsStream()));
			while ((buffer = bodyReader.readLine()) != null) {
				if (buffer.indexOf("<BASE") >= 0) {
					rootPath = findRootPath(buffer);
					if (rootPath != null)
						break;
				}
			}
		} catch (IOException ioe) {
			TPLogger.getLogger().error("Exception in getRootPath");
		}

		return rootPath;
	}

	private int doFbaAuth(HttpClient client, String connType) {
		int status = 0;
		PostMethod fbaAuth = new PostMethod(connType + fromSrv + fbapath);
		fbaAuth.setRequestHeader("Content-type", "application:x-www-form-urlencoded");
		// Destination for authenication
		if (ISA) {
			fbaAuth.addParameter("destination", destination);
			fbaAuth.addParameter("flags", "0");
		} else {
			fbaAuth.addParameter("destination", connType + fromSrv + "/" + serverPath + "/");
		}
		fbaAuth.addParameter("username", domain + "\\" + username);
		fbaAuth.addParameter("password", password);

		try {
			status = client.executeMethod(fbaAuth);
		} catch (HttpException httpe) {
			TPLogger.getLogger().error("HttpException in doFbaAuth():" + httpe.getMessage());
		} catch (IOException ioe) {
			TPLogger.getLogger().error("IO error in doFbaAuth():" + ioe.getMessage());
		}
		return status;
	}

	private int markAsRead(HttpClient client, String url) {
		int status = 0;
		BPropPatchMethod setRead = new BPropPatchMethod(inboxAddr + "/");
		try {
			setRead.setRequestEntity(new StringRequestEntity(getMarkAsReadMsg(url), null, null));
		} catch (UnsupportedEncodingException uee) {
			TPLogger.getLogger().error("HttpException in markAsRead():" + uee.getMessage());
		}
		try {
			status = client.executeMethod(setRead);
			if (status != 207 && status != 200) {
				TPLogger.getLogger().debug("Status not normal!!\n");
				TPLogger.getLogger().debug(status + "\n------\n" + setRead.getResponseBodyAsString());
			}
		} catch (HttpException httpe) {
			TPLogger.getLogger().error("HttpException in markAsRead():" + httpe.getMessage());
		} catch (IOException ioe) {
			TPLogger.getLogger().error("IO error in markAsReadh():" + ioe.getMessage());
		}
		return status;
	}

	private int removeMsg(HttpClient client, String url) {
		int status = 0;
		BDeleteMethod del = new BDeleteMethod(inboxAddr + "/");
		try {
			del.setRequestEntity(new StringRequestEntity(getDeleteMsg(url), null, null));
		} catch (UnsupportedEncodingException uee) {
			TPLogger.getLogger().error("HttpException in removeMsg():" + uee.getMessage());
		}
		try {
			status = client.executeMethod(del);
			if (status != 207 && status != 200) {
				TPLogger.getLogger().debug("Status not normal!!\n");
				TPLogger.getLogger().debug(status + "\n------\n" + del.getResponseBodyAsString());
			}
		} catch (HttpException httpe) {
			TPLogger.getLogger().error("HttpException in removeMsg()):" + httpe.getMessage());
		} catch (IOException ioe) {
			TPLogger.getLogger().error("IO error in removeMsg():" + ioe.getMessage());
		}
		return status;
	}

	private String fixFileName(String filename) {
		// Exchange uses several characters in URL's which
		// httpclient (or standards) don't like so
		// we'll replace them with hex reference
		filename = filename.replaceAll("\\[", "%5B");
		filename = filename.replaceAll("\\]", "%5D");
		filename = filename.replaceAll("\\|", "%7C");
		filename = filename.replaceAll("\\^", "%5E");
		filename = filename.replaceAll("\\`", "%60");
		filename = filename.replaceAll("\\{", "%7B");
		filename = filename.replaceAll("\\}", "%7D");
		return filename;
	}
}
