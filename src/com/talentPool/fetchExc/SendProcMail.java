package com.talentPool.fetchExc;

/*  SendProcMail.java
    Copyright (C) 2006 Juhani Rautiainen

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
import java.io.*;
import java.text.*;
import java.util.*;

import com.talentPool.common.Logger.TPLogger;

class SendProcMail implements MsgForwarder {

	private boolean sentOk;
	private String dstAddr;
	
	SendProcMail(String dstAddr) {
		this.dstAddr=dstAddr;
	}

	public boolean processMessage(String from, InputStream fwdMsg) {
		sentOk=false;
		try {
			// everything after | is the command
			Process child = Runtime.getRuntime().exec(dstAddr);
			// Get output stream to write from it
			OutputStream out = child.getOutputStream();
			//TPLogger.getLogger().error("this: "+get.getResponseBodyAsString());
						
			Date now = new Date();
			final String email_time=new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy").format(now);
			String outFrom = "From " + from + "  " + email_time + "\n";
			out.write(outFrom.getBytes(),0,outFrom.length());

			while (true) {
				int data = fwdMsg.read();
				if (data < 0) break;
				if (data == '\r') continue;
					out.write(data);
				}
			out.close();
			sentOk=true;
		}	
		catch (IOException e){
			TPLogger.getLogger().error("Error sending: "+e);
			return sentOk;
		}
		//TPLogger.getLogger().error("Message Sent!");
		return sentOk;
	}
}
