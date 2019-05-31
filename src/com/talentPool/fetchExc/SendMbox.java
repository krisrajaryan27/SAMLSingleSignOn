package com.talentPool.fetchExc;

/*  SendMbox.java
 Copyright (C) 2005 Juhani Rautiainen, Robert Clark

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
import java.nio.channels.*;

import com.talentPool.common.Logger.TPLogger;

class SendMbox implements MsgForwarder {
	private String fromSrv;
	private String mboxFile;
	
	SendMbox(String fromSrv, String mboxFile) {
		this.fromSrv=fromSrv;
		this.mboxFile=mboxFile;
	}
	private static final Format LONG_FORMAT = new SimpleDateFormat(
			"EEE, d MMM yyyy HH:mm:ss Z (z)");

	private static final Format SHORT_FORMAT = new SimpleDateFormat(
			"EEE MMM dd HH:mm:ss yyyy");

	public boolean processMessage(String from, InputStream fwdMsg) {
		Date now = new Date();

		final String curDateTime = LONG_FORMAT.format(now);
		final String curDateTime2 = SHORT_FORMAT.format(now);

		String hostAddress;
		try {
			hostAddress = InetAddress.getByName(fromSrv).getHostAddress();
		} catch (Exception e) {
			hostAddress = "unknown";
		}
		final String recFrom = "Received: from "
				+ fromSrv
				+ " (["
				+ hostAddress
				+ "]) by localhost with webDAV (fetchExc) for mbox (single-drop); "
				+ curDateTime;

		BufferedOutputStream bos = null;
		FileLock lock;
		FileOutputStream fos;

		try {
			fos = new FileOutputStream(mboxFile, true); // open in append mode
		} catch (IOException ioe) {
			System.err.println("Can't open file:" + mboxFile);
			return false;
		}
		try {
			lock = lockFile(fos.getChannel(), false); // will block until we
														// get a lock.
		} catch (IOException ioe) {
			TPLogger.getLogger().error("error in locking file:" + mboxFile);
			ioe.printStackTrace();
			return false;
		}
		bos = new BufferedOutputStream(fos);

		try {
			int data;
			int fromCnt = 0;
			boolean fromOn = false; // From quoting
			boolean cr = false, lf = false;
			String fromQuote = "From ";
			int fromLen = fromQuote.length();
			String fromLine = "From " + from + "  " + curDateTime2;
			bos.write(fromLine.getBytes(), 0, fromLine.length());
			bos.write('\n');
			String returnPath = "Return-Path: <" + from + ">";
			bos.write(returnPath.getBytes(), 0, returnPath.length());
			bos.write('\n');
			bos.write(recFrom.getBytes(), 0, recFrom.length());
			bos.write('\n');
			while (true) {
				data = fwdMsg.read();
				if (data < 0)
					break;
				if (data == '\r' && fromOn == false) {
					// we got <cr>
					cr = true;
					lf = false;
					continue;
				}
				if (data == '\n' && fromOn == false) {
					// we got <lf>
					lf = true;
					bos.write(data);
					continue;
				}

				if (cr == true && lf == true && data == fromQuote.charAt(0)) {
					// First character found
					cr = false;
					lf = false;
					fromOn = true;
					fromCnt = 1;
					continue;
				}
				if (fromOn == true && data == fromQuote.charAt(fromCnt)) {
					// Next character found
					if (fromCnt < fromLen - 1) {
						// Still not whole quote
						fromCnt++;
						continue;
					} else {
						// Whole quote found
						// Put extra > in
						bos.write('>');
						bos.write(fromQuote.getBytes(), 0, fromLen);
						fromOn = false;
						continue;
					}
				}
				if (fromOn == true) {
					// not whole match
					bos.write(fromQuote.getBytes(), 0, fromCnt);
					fromOn = false;
				}
				cr = false;
				lf = false;
				if (data != '\r')
					bos.write(data);
			}
			bos.write('\n');
			bos.flush();
			lock.release();
			bos.close();
		} catch (IOException e) {
			TPLogger.getLogger().error("Error in writing to mbox: " + e);
			e.printStackTrace();
			return false;
		}

		finally {
			// Always try to release the lock, whether or not there were errors
			try {
				lock.release();
			} catch (IOException ioe) {
				// This fails if file is closed already
				// so dont' report for now
				// TPLogger.getLogger().error( "Error releasing mbox lock: " + ioe );
			}
		}

		return true;
	}

	/**
	 * Tries to lock the file channel provided. We try a spin lock first and if
	 * that fails, we optionally fall back on a blocking lock.
	 * 
	 * @returns a FileLock for the file or null if no lock could be obtained.
	 */
	private FileLock lockFile(FileChannel lockChannel, boolean spinLockOnly)
			throws IOException {
		final int LOCK_COUNT = 5;
		FileLock lock = null;

		for (int i = 0; i < LOCK_COUNT; i++) {
			lock = lockChannel.tryLock();
			if (lock != null) {
				// Ya! We have a lock on this file
				break;
			}

		}

		if (lock == null && !spinLockOnly) {
			// Spin locks failed, try a blocking lock.
			// TPLogger.getLogger().error("File locked by another process, waiting until
			// it is available to lock it ourselves");
			lock = lockChannel.lock();
		}

		// May be null if we could not get a spin lock and were told not
		// to wait (spinLockOnly = false) .
		return lock;

	}

}
