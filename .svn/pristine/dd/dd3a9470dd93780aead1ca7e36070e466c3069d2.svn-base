/**
 * 
 */
package com.talentPool.parser;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.talentPool.applicant.dataobject.EducationalData;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.utils.Utils;
import com.talentPool.parser.dataobject.ParsedResultData;
import com.talentPool.parser.manager.ExpressionHandler;
import com.talentPool.parser.regex.REMatch;
import com.talentPool.parser.utils.ParserUtils;

/**
 * @author shivprasad
 * 
 */
public class EducationParser {

	public EducationalData getEducationParsedFromMaster(String content) {
		EducationalData eData = new EducationalData();
		try {
			if (!Utils.isBlankOrNull(content)) {
				content = ParserUtils.getTokenizedString(content);
				content = content.replaceAll("\\.", "");
				ArrayList results = getListWithAliases("DEG");
				SimpleDataObject degreeSdo = getMatchedDO(results, content);
				results = getListWithAliases("BRA");
				SimpleDataObject branchSdo = getMatchedDO(results, content);
				results = getListWithAliases("INS");
				SimpleDataObject instituteSdo = getMatchedDO(results, content);

				if (degreeSdo != null) {
					eData.setDegreeId(degreeSdo.getInt("id"));
				}
				if (branchSdo != null) {
					eData.setMajorId(branchSdo.getInt("id"));
				}
				if (instituteSdo != null) {
					eData.setInstitute(instituteSdo.getString("name"));
				}
				String yop = parseYearOfPassing(content);
				if (!Utils.isBlankOrNull(yop)) {
					java.sql.Date dtYOP = Utils.convertToSQLDate("01/01/" + yop, "dd/MM/yyyy");
					eData.setYearOfPassing(dtYOP);
				}
			}

		} catch (Exception e) {
			TPLogger.getLogger().error("Error while parsing education data", e);
		}
		return eData;
	}

	public EducationalData getEducationParsedFromMaster(String degree, String branch, String institute, String yop, String grade) {
		EducationalData eData = new EducationalData();
		try {
			if (!Utils.isBlankOrNull(degree)) {
				degree = ParserUtils.getTokenizedString(degree);
				degree = degree.replaceAll("\\.", "");
				ArrayList results = getListWithAliases("DEG");
				SimpleDataObject degreeSdo = getMatchedDO(results, degree);
				if (degreeSdo != null) {
					eData.setDegreeId(degreeSdo.getInt("id"));
				}
			}
			if (!Utils.isBlankOrNull(branch)) {
				branch = ParserUtils.getTokenizedString(branch);
				branch = branch.replaceAll("\\.", "");
				ArrayList results = getListWithAliases("BRA");
				SimpleDataObject branchSdo = getMatchedDO(results, branch);
				if (branchSdo != null) {
					eData.setMajorId(branchSdo.getInt("id"));
				}
			}
			if (!Utils.isBlankOrNull(institute)) {
				institute = ParserUtils.getTokenizedString(institute);
				institute = institute.replaceAll("\\.", "");
				ArrayList results = getListWithAliases("INS");
				SimpleDataObject instituteSdo = getMatchedDO(results, institute);
				if (instituteSdo != null) {
					eData.setInstitute(instituteSdo.getString("name"));
				}
			}

			yop = parseYearOfPassing(yop);
			if (!Utils.isBlankOrNull(yop)) {
				java.sql.Date dtYOP = Utils.convertToSQLDate("01/01/" + yop, "dd/MM/yyyy");
				eData.setYearOfPassing(dtYOP);
			}
			if (!Utils.isBlankOrNull(grade)) {
				eData.setGrade(grade);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while parsing education data", e);
		}
		return eData;
	}

	public ArrayList getListWithAliases(String dataType) {
		DBPreparedQuery dq = null;
		ArrayList data = new ArrayList();
		try {
			String dbQuery = "";
			if (dataType.equals("DEG")) {
				dbQuery = "dEducationParser_GetDegreesWithAliases";
			} else if (dataType.equals("BRA")) {
				dbQuery = "dEducationParser_GetBranchesWithAliases";
			} else if (dataType.equals("INS")) {
				dbQuery = "dEducationParser_GetInstitutesWithAliases";
			}
			dq = new DBPreparedQuery(dbQuery);
			data = dq.getResult();

		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting data with aliases", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return data;
	}

	public SimpleDataObject getMatchedDO(ArrayList results, String content) {
		SimpleDataObject idMatched = null;
		try {
			if (results != null & results.size() > 0) {
				for (int i = 0; i < results.size(); i++) {
					SimpleDataObject sdo = (SimpleDataObject) results.get(i);
					REMatch reMatch = new REMatch();
					String name = sdo.getString("name");
					if (!Utils.isBlankOrNull(name)) {
						name = ParserUtils.getTokenizedString(name);
						name = name.replaceAll("\\.", ""); // replace all dots
						// to give a good
						// match
						int cntMatched = reMatch.matchREAgainstString(ParserUtils.getREConstructed(name), content).size();
						if (cntMatched > 0) {
							idMatched = sdo;
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while applying regexp", e);
		}
		return idMatched;
	}

	public String parseYearOfPassing(String content) {
		ArrayList expressions = ExpressionHandler.getExpressionsForField(ParserConstants.FIELD_YEAR_OF_PASSING);
		String yop = "";
		ArrayList results = new ArrayList();
		for (int i = 0; i < expressions.size(); i++) {
			String regExp = (String) expressions.get(i);
			Pattern p = Pattern.compile(regExp);
			boolean theEnd = false;
			Matcher m = p.matcher(content);
			while (!theEnd) {
				theEnd = !m.find();
				if (!theEnd) {
					String extract = content.substring(m.start(), m.end());
					ParsedResultData pRData = new ParsedResultData(extract, m.start(), m.end());
					results.add(pRData);
				}
			}
		}
		if (results.size() > 0) {
			int yPass = 0;
			for (int i = 0; i < results.size(); i++) {
				ParsedResultData pRData = (ParsedResultData) results.get(i);
				yop = pRData.getMatchedString().trim();
				int newYop = 0;
				try {
					newYop = Integer.parseInt(yop);
				} catch (Exception e) {
				}
				if (newYop > yPass) {
					yPass = newYop;
				}
			}
			if (yPass != 0)
				yop = "" + yPass;
		}
		return yop;
	}
	
	public String getInstituteParsedFromMaster(String institute) {		
		String instituteId = "";
		try {
			if (!Utils.isBlankOrNull(institute)) {
				institute = ParserUtils.getTokenizedString(institute);
				institute = institute.replaceAll("\\.", "");
				ArrayList results = getListWithAliases("INS");
				SimpleDataObject instituteSdo = getMatchedDO(results, institute);
				if (instituteSdo != null) {
					instituteId = instituteSdo.getString("name");
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while parsing education data", e);
		}
		return instituteId;
	}
}
