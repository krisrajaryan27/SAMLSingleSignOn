package com.talentPool.fetchExc;

import com.talentPool.common.Logger.TPLogger;

/*  fetchExc.java
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
class fetchExc {

	public static void main(String[] args) throws Exception {
		String propFile = null;
		String arg;

		for (int argc = 0; argc < args.length; argc++) {
			arg = args[argc];
			if (arg.startsWith("-")) {
				if (arg.equals("-p")) {
					if (args.length >= argc + 1) {
						propFile = args[argc + 1];
					} else {
						TPLogger.getLogger().error("Properties file missing");
						return;
					}
				}
			}
		}
		fetchMail mail = new fetchMail(propFile);
		mail.fetchAll();
	}
}
