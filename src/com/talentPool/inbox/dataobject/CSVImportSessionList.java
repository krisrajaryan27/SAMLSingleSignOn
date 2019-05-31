/**
 * 
 */
package com.talentPool.inbox.dataobject;

import java.util.HashMap;



/**
 * @author Shantanu
 *
 */
public class CSVImportSessionList {
	
	public static volatile HashMap<String, CSVSessionEvent> sessions;
	static{
		sessions=new HashMap<String, CSVSessionEvent>();
	}
}
