/**
 * 
 */
package com.talentPool.parser.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBQuery;
import com.talentPool.common.db.SimpleDataObject;

/**
 * @author shivprasad
 * 
 */
public class ParserManager {

	public ArrayList getDegrees() {
		ArrayList degrees = new ArrayList();
		DBQuery dq = null;
		try {
			dq = new DBQuery("dParserManager_LoadDegreesToParse");
			degrees = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting degrees", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return degrees;
	}

	public  Map getDegreeRegexForWordParser() {
		List regexList = new ArrayList();
		Map regexMap = new HashMap();
		try {
			regexList = getDegrees();
			if (regexList != null && regexList.size() > 0) {
				Iterator itr = regexList.iterator();
				while (itr.hasNext()) {
					SimpleDataObject object = (SimpleDataObject) itr.next();
					String key = object.getString("degreeRegExp");
					String value = object.getString("degree");
					regexMap.put(key, value);
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting regular expressions for degrees", e);
		}
		return regexMap;
	}

	public ArrayList getExpressionsForSections() {
		ArrayList sections = new ArrayList();
		DBQuery dq = null;
		try {
			dq = new DBQuery("dParserManager_GetExpSections");
			sections = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while section headings", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return sections;
	}

	public Map getDegreesWithIds() {
		Map degrees = new HashMap();
		DBQuery dq = null;
		try {
			dq = new DBQuery("dParserManager_GetAllDegrees");
			List result = dq.getResult();
			if (result != null && result.size() > 0) {
				Iterator itr = result.iterator();
				while (itr.hasNext()) {
					SimpleDataObject sDo = (SimpleDataObject) itr.next();
					degrees.put(sDo.getString("title"), sDo.getString("degreeId"));
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting degrees with ids", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return degrees;
	}
}