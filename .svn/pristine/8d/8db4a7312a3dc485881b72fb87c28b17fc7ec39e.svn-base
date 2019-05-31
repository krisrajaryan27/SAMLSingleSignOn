/**
 * 
 */
package com.talentPool.parser.manager;

import java.util.ArrayList;
import java.util.HashMap;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBQuery;
import com.talentPool.common.db.SimpleDataObject;

/**
 * @author shivprasad
 * 
 */
public class ExpressionHandler {
	private static HashMap _expressions;

	static {
		loadExpressions();
	}

	public static void loadExpressions() {
		DBQuery dq = null;
		try {
			dq = new DBQuery("dGetAllExpressions");
			ArrayList results = dq.getResult();
			if (results != null) {
				String prevName = "";
				ArrayList regExpressions = new ArrayList();
				_expressions = new HashMap();
				int sz = results.size();
				for (int i = 0; i < sz; i++) {
					SimpleDataObject sDo = (SimpleDataObject) results.get(i);
					String newName = sDo.getString("fieldName");
					String eRegExp = sDo.getString("fieldExpression");
					if (!newName.equals(prevName)) {
						if (prevName != "") {
							_expressions.put(prevName, regExpressions);
						}
						regExpressions = new ArrayList();
					}
					regExpressions.add(eRegExp);
					prevName = newName;
					if (i == sz - 1) {
						_expressions.put(prevName, regExpressions);
					}

				}
			}

		} catch (Exception e) {
			TPLogger.getLogger().error("Error while loading expressions", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}

	public static ArrayList getExpressionsForField(String field) {
		ArrayList expressions = null;
		try {
			expressions = (ArrayList) _expressions.get(field);
		} catch (Exception e) {

		}
		return expressions;
	}

}
