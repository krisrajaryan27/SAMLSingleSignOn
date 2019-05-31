package com.talentPool.fetchExc;

/*  SendSmtp.java
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
import java.net.*;
import java.io.*;
import java.text.*;
import java.util.*;

import com.talentPool.common.Logger.TPLogger;

class SendSmtp implements MsgForwarder {
	private String fromSrv; 
	private String dstMailSrv;
	private String dstAddr;
	private boolean fixFrom;
	private String fakeFrom;
	private boolean noEightBitMime;
	PrintWriter streamOut;
	BufferedReader dis;
	
	SendSmtp(String fromSrv, String dstMailSrv, String dstAddr,
				   boolean fixFrom, String fakeFrom,
				   boolean noEightBitMime)
	{
		this.fromSrv=fromSrv;
		this.dstMailSrv=dstMailSrv;
		this.dstAddr=dstAddr;
		this.fixFrom=fixFrom;
		this.fakeFrom=fakeFrom;
		this.noEightBitMime=noEightBitMime;
	}

	public void send(String str) throws IOException {
		streamOut.write(str+"\r\n");
		streamOut.flush();
		//TPLogger.getLogger().error("SMTP sent: "+str);
	}

	public int receive(int expected) throws IOException {
		int retVal=0;
		boolean notexpected=(expected==0);
		String readstr = dis.readLine();
		retVal=Integer.valueOf(readstr.substring(0,3)).intValue();
		//TPLogger.getLogger().debug("SMTP Response: "+readstr);
		//TPLogger.getLogger().debug("SMTP retVal="+retVal);
		if (expected==0) expected=retVal;
		while (dis.ready() || retVal!=expected ) {
			readstr = dis.readLine();
			retVal=Integer.valueOf(readstr.substring(0,3)).intValue();
			if (notexpected) expected=retVal;
			//TPLogger.getLogger().debug("SMTP Response: "+readstr);
		}
		return retVal;
	}

	public boolean processMessage(String from, InputStream fwdMsg) {
		final String HELO = "EHLO ";
		String MAIL_FROM;
		boolean sentOk=false;

		if (from==null) {
			if (fixFrom) {
				TPLogger.getLogger().debug("From: address missing! Using "+fakeFrom);
				from=fakeFrom;
			}	
			else {
				TPLogger.getLogger().debug("From: address missing! Enable ForceFrom property if you want these messages\n forwarded.");
				return sentOk;	
			}
		}
		if (noEightBitMime) 
			MAIL_FROM = "MAIL FROM:<"+from+">";
		else
			MAIL_FROM = "MAIL FROM:<"+from+"> BODY=8BITMIME";
		String RCPT_TO = "RCPT TO:<"+dstAddr+">";
		final String DATA = "DATA";
		final String END="\r\n.";
		final String QUIT = "QUIT";
		String recFrom=null;
		String curDateTime;
		
		Date now = new Date();
		Format formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z (z)",Locale.US);
		curDateTime=formatter.format(now);
		//TPLogger.getLogger().debug("curDateTime="+curDateTime);
		
		try {
			recFrom = "Received: from "+fromSrv+" (["+InetAddress.getByName(fromSrv).getHostAddress()+"]) by localhost with webDAV (fetchExc) for "+dstAddr+" (single-drop); "+curDateTime;
		}
		catch (Exception e) {};
		Socket smtp = null;
		OutputStream os=null;
		try{
			smtp = new Socket(dstMailSrv,25);
			smtp.setSoTimeout(30000);
			os = smtp.getOutputStream();
			streamOut = new PrintWriter(os,true);
			InputStream is = smtp.getInputStream();
			dis = new BufferedReader(new InputStreamReader(is));
		}
		catch (IOException e){
			TPLogger.getLogger().error("Error connecting to "+dstMailSrv+": "+e);
		}

		try{
			int status;
			//Wait for server ready message
			if ((status=receive(220))!=220) {
				TPLogger.getLogger().debug("server ready message error status="+status);
				smtp.close();
				return sentOk;
			} 
			String loc = InetAddress.getLocalHost().getHostName();
			send(HELO +loc);
			if ((status=receive(0))!=250) {
				TPLogger.getLogger().debug("EHLO error status="+status);
				send(QUIT);
				receive(0);
				smtp.close();
				return sentOk;
			}
			send(MAIL_FROM);
			if ((status=receive(0))!=250) {
				boolean stop_this=true;
				int firstLineFeed;
				String firstFrom;
				TPLogger.getLogger().debug("From: <"+from+"> wasn't valid");
				//Are there many from: addresses?
				//This only works if addresses are not in form:
				//From: name@addr.net
				//From: "Name" name@addr.net
				firstLineFeed=from.indexOf(10);
				if (firstLineFeed>0) {
					stop_this=false;
					firstFrom=from.substring(0,firstLineFeed);
					TPLogger.getLogger().debug("Multiple From-addresses. Trying to send using <"+firstFrom+">");
					MAIL_FROM = "MAIL FROM:<"+firstFrom+"> BODY=8BITMIME";
					send(MAIL_FROM);
					if ((status=receive(0))!=250) {
						//Didn't work either
						TPLogger.getLogger().debug("Didn't work - this message will fail.");
						stop_this=true;
					}
				}
				if (fixFrom && stop_this) {
					//Let's try something different
					stop_this=false;
					TPLogger.getLogger().debug("Trying to send using fake address <"+fakeFrom+">");
					MAIL_FROM = "MAIL FROM:<"+fakeFrom+"> BODY=8BITMIME";
					send(MAIL_FROM);
                                        if ((status=receive(0))!=250) {
                                                //Didn't work either
                                                TPLogger.getLogger().debug("Didn't work - this message will fail.");
                                                stop_this=true;
                                        }

				}
				if (stop_this) {
					TPLogger.getLogger().debug("MAIL FROM error status="+status);
					send(QUIT);
					receive(0);
					smtp.close();
					return sentOk;
				}
			}
			send(RCPT_TO);
			if ((status=receive(0))!=250) {
				TPLogger.getLogger().debug("RCPT TO error status="+status);
				send(QUIT);
				receive(0);
				smtp.close();
				return sentOk;
			}
			send(DATA);
			if ((status=receive(354))!=354) {
				TPLogger.getLogger().debug("DATA error status="+status);
				send(QUIT);
				receive(0);
				smtp.close();
				return sentOk;
			};
			send(recFrom);
			int data;
			//Following booleans are for dot-quoting so we don't
			//end message too soon. It seems that end-of-message
			//dot comes after two <lf>'s so we can quote every
			//<cr><lf><.> combo without checking what comes after
			//that.
			boolean cr=false;
			boolean lf=false;
			int byte_count=0;
			
			while (true) {
				data = fwdMsg.read();
				byte_count++;
				if (data=='\r') {
					//we got <cr>
					cr=true;
					lf=false;
					os.write(data);
					continue;
				}
				if (data=='\n') {
					//we got <lf> 
					lf=true;
					os.write(data);
					continue;
				}
				//end of stream so lets get out of here
           			if (data < 0) break;
            			if (cr && lf && data=='.') {
					//now we have <cr><lf><.>
					//and we quote it <cr><lf><.><.>
					os.write(data);
					os.write(data);
					//lets reset so we don't quote
					//other dots
					cr=lf=false;
				}
				else {
					//reset anyway
					cr=lf=false;
					os.write(data);
				}
			}
			send(END);
			if ((status=receive(0))==250) sentOk=true;
			else {
				TPLogger.getLogger().debug("END error status="+status);
			}
			send(QUIT);
			receive(0);
			smtp.close();
			Date elapsednow=new Date();
			long millis = elapsednow.getTime() - now.getTime();
			TPLogger.getLogger().debug("Received "+byte_count+" bytes in "+(millis/1000.)+"s from "+from);
		}
		catch (IOException e){
			TPLogger.getLogger().error("Error sending: "+e);
		}
		//TPLogger.getLogger().error("Message Sent!");
		return sentOk;
	}
}
