package com.talentPool.domino;

/*import lotus.domino.AgentBase;
import lotus.domino.Document;
import lotus.domino.Database;
import lotus.domino.RichTextItem;
import lotus.domino.Item;
import lotus.domino.EmbeddedObject;
import java.util.Date;
import java.util.Properties;
import java.util.Vector;
import java.util.Enumeration;
import javax.mail.Address;
import javax.mail.Multipart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.activation.*; //I guess these JARs are properly set up at the server level

//~Not sure I'll need this in the end
import java.io.*;*/

//NEED TO SET AGENT PROPERTIES TO "2. ALLOW RESTRICTED OPERATIONS"
//OTHERWISE, THIS NO GO
//Randy

/*
 This code worked in sending emails with a PDF attachment:
 - to yahoo email
 - to Internal Lotus Notes client email
 - to my personal email
 - to email client behind a well set up firewall
 Randy
 */
public class JavaAgent{
//public class JavaAgent extends AgentBase {
	/*

	EmbeddedObject eo;
	String filename;

	// This may help for some type files...but the PDFs in this case don't need
	// it
	// I don't even think I can set this here...I think the JVM property file
	// would need to be updated
	// System.setProperty("mail.mime.encodeeol.strict", "true");

	public void NotesMain() {

		String d_host = "xxxmta01";
		PrintWriter pw = getAgentOutput();

		Database db;
		Document memo;

		try {
			pw.println("Sending email with attachment.....");

			// Get all the Lotus Data
			// ********************************************************************
			lotus.domino.Session s = getSession();
			lotus.domino.AgentContext agentContext = s.getAgentContext();
			db = agentContext.getCurrentDatabase();
			memo = agentContext.getDocumentContext();

			String m_to = memo.getItemValueString("SendTo");
			String m_cc = memo.getItemValueString("CopyTo");
			String m_bcc = memo.getItemValueString("BlindCopyTo");

			String d_email = memo.getItemValueString("From");
			String m_subject = memo.getItemValueString("Subject");
			String m_text = memo.getItemValueString("Body");

			// ~check if this is null after get
			RichTextItem pdfField = (RichTextItem) memo
					.getFirstItem("PDFAttach");
			Vector v = pdfField.getEmbeddedObjects();
			Enumeration e = v.elements();

			while (e.hasMoreElements()) {
				eo = (EmbeddedObject) e.nextElement();
				filename = eo.getName();
				pw.println(eo.getName());
			}
			// ~it might be better if we decide upon a more
			// "Domino - Applications Team" folder
			eo.extractFile("c:\\" + filename);
			// ********************************************************************

			Properties props = new Properties();
			// props.put("mail.smtp.host", "xrxxmta01.rrr.xxx.com");
			props.put("mail.smtp.user", d_email);
			props.put("mail.smtp.host", d_host);
			// specifiying the port is not required in this case
			// props.put("mail.smtp.port", d_port);

			Session session = Session.getInstance(props, null);
			// session.setDebug(true);

			
			 * //I was thinking of passing the Datahandler constructor a URL
			 * instead of the file but I couldn't get it to work... //~try again
			 * later??...I think the URL is just incorrect? String httpURL =
			 * memo.getHttpURL(); httpURL = replace (httpURL, "?OpenDocument" ,
			 * "/$FILE/") + filename ; //Debugging....Does the server not see
			 * it's own name via the web? httpURL = replace (httpURL,
			 * "xxxLNTEST.xxxxxx.com", "127.0.0.1"); pw.println(httpURL); URL
			 * pdfLink =new URL (httpURL);
			 

			try {
				MimeMessage msg = new MimeMessage(session);
				msg.setSubject(m_subject);
				msg.setFrom(new InternetAddress(d_email));
				InternetAddress[] toaddress = InternetAddress.parse(m_to);
				msg.addRecipients(Message.RecipientType.TO, toaddress);

				// parse fails on empty string
				if (m_cc != null) {
					InternetAddress[] ccaddress = InternetAddress.parse(m_cc);
					msg.setRecipients(Message.RecipientType.CC, ccaddress);
				}
				if (m_bcc != null) {
					InternetAddress[] bccaddress = InternetAddress.parse(m_bcc);
					msg.setRecipients(Message.RecipientType.BCC, bccaddress);
				}

				MimeBodyPart p1 = new MimeBodyPart();
				p1.setText(m_text);

				// Put a file in the second part
				MimeBodyPart p2 = new MimeBodyPart();
				FileDataSource fds = new FileDataSource("c:\\" + filename);

				p2.setDataHandler(new DataHandler(fds));
				p2.setFileName(filename);
				// in my tests This Header info didn't seem to have a good or
				// back impact either way
				p2.setHeader("Content-Type", "application/pdf" + "; name=\""
						+ filename + "\"");
				String theType = p2.getContentType();

				// Create the Multipart. Add BodyParts to it.
				Multipart mp = new MimeMultipart();
				mp.addBodyPart(p1);
				mp.addBodyPart(p2);

				// Set Multipart as the message's content
				msg.setContent(mp);

				Transport transport = session.getTransport(new InternetAddress(
						d_email));
				Transport.send(msg);
				// Right here...I should write to a log of the emails sent
				// ...write to the bottom fields of the MMR PDF form
				// ~ yo
				String emailLog;
				String emailLog1;
				String emailLog2;
				String emailLog3;
				emailLog1 = "<p>Email Sent to " + m_to + " on " + new Date();
				emailLog2 = "<br>   - CCed to " + m_cc;
				emailLog3 = "<br>   - From " + d_email;
				// if there is no CC then don't log one
				if (m_cc != null) {
					emailLog = emailLog1 + emailLog2 + emailLog3;
				} else {
					emailLog = emailLog1 + emailLog3;
				}

				// determine if it's the first time sending and logging
				Item logItem = memo.getFirstItem("MMREmailLog");
				if (logItem == null) {
					memo.replaceItemValue("MMREmailLog", emailLog);
				} else {
					String currentValue;
					currentValue = memo.getItemValueString("MMREmailLog");
					memo.replaceItemValue("MMREmailLog", currentValue
							+ emailLog);
				}

				boolean success = (new File("c:\\" + filename)).delete();
				if (!success) {
					// Deletion failed....someone must go delete the file? Hire
					// a thousand monkeys
				}

				pw.println("<p> Email Sent successfully to ");
				pw.println(m_to);

			} catch (MessagingException mex) {
				mex.printStackTrace();
				System.out.println();
				Exception ex = mex;

				do {
					if (ex instanceof SendFailedException) {
						SendFailedException sfex = (SendFailedException) ex;
						Address[] invalid = sfex.getInvalidAddresses();

						if (invalid != null) {
							for (int i = 0; i < invalid.length; i++) {
								System.out.println("Invalid address: "
										+ invalid[i]);
							}
						}
						Address[] validUnsent = sfex.getValidUnsentAddresses();

						if (validUnsent != null) {
							for (int i = 0; i < validUnsent.length; i++) {
								System.out.println("Valid unsent address: "
										+ validUnsent[i]);
							}
						}
						Address[] validSent = sfex.getValidSentAddresses();
						if (validSent != null) {
							for (int i = 0; i < validSent.length; i++) {
								System.out.println("Valid sent address: "
										+ validSent[i]);
							}
						}
					}

					if (ex instanceof MessagingException) {
						ex = ((MessagingException) ex).getNextException();
					} else {
						ex = null;
					}
				} while (ex != null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// System.setProperty("mail.mime.encodeeol.strict", "false");
	// This method is handy..but I may not need it
	static String replace(String str, String pattern, String replace) {
		int s = 0;
		int e = 0;
		StringBuffer result = new StringBuffer();

		while ((e = str.indexOf(pattern, s)) >= 0) {
			result.append(str.substring(s, e));
			result.append(replace);
			s = e + pattern.length();
		}
		result.append(str.substring(s));
		return result.toString();
	}

*/}