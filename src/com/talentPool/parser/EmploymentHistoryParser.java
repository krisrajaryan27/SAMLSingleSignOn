/**
 * 
 */
package com.talentPool.parser;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;

import com.talentPool.applicant.dataobject.EmploymentHistoryData;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.RegexUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.parser.regex.REMatch;
import com.talentPool.parser.utils.ParserUtils;

/**
 * @author shantanu
 *
 */
public class EmploymentHistoryParser {
	
	//public static String dateRegExp = "(((?i)jan|january|feb|february|mar|march|apr|april|may|jun|june|jul|july|aug|august|sep|september|oct|october|nov|november|dec|december) ((19|20)\\d{2}))";
	public static String dateRegExp = "(((?i)jan(uary)?|feb(ruary)?|mar(ch)?|apr(il)?|may|jun(e)?|jul(y)?" 
			+ "|aug(ust)?|sep(tember)?|oct(ober)?|nov(ember)?|dec(ember)?)(-|'|/| |)*((19|20)?\\d{2}))"; 
	
	public EmploymentHistoryData getEmploymentHistoryParsedFromMaster(String content){
		EmploymentHistoryData ehd = new EmploymentHistoryData();
		try{
			if (!Utils.isBlankOrNull(content)) {
				content = ParserUtils.getTokenizedString(content);
				content = content.replaceAll("\\.", "");
				ArrayList<String> employerFromToDate = getParsedEmploymentDate(content);
				String dateFormat = "Utils.regMMMYYYYFormat";
				
				if(employerFromToDate!=null && !employerFromToDate.isEmpty()){
					if(employerFromToDate.size()>0){
						String from = employerFromToDate.get(0).trim();
						Matcher matcher = RegexUtils.getMatcher(from, "d{4}");
						if(!matcher.find()) { 
							dateFormat = Utils.regMMMYYFormat;
						}
						Date fromDt = Utils.convertToDate(from.replace(" ", Utils.dateDescSeparator), dateFormat);
						ehd.setEmployerFromDate(new java.sql.Date(fromDt.getTime()));
					}
					if(employerFromToDate.size()>1){
						String to = employerFromToDate.get(1).trim();
						Matcher matcher = RegexUtils.getMatcher(to, "d{4}");
						if(!matcher.find()) {
							dateFormat = Utils.regMMMYYFormat;
						}						
						Date toDt = Utils.convertToDate(to.replace(" ", Utils.dateDescSeparator), dateFormat);
						ehd.setEmployerToDate(new java.sql.Date(toDt.getTime()));
					}
					
				}
				
				ArrayList results = getListWithAliases("EMPLOYER");
				SimpleDataObject emplopyerSdo = getMatchedDO(results, content);
				results = getListWithAliases("DESIGNATION");
				SimpleDataObject designationSdo = getMatchedDO(results, content);
				
				if(emplopyerSdo!=null){
					ehd.setEmployerName(emplopyerSdo.getId("name"));
				}
				
				if(designationSdo!=null){
					ehd.setDesignationName(designationSdo.getId("name"));
				}
				
			}
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,e);
		}
		return ehd;
	}
	
	public ArrayList<String> getParsedEmploymentDate(String content){		
		ArrayList<String> matches = new ArrayList<String>();
		try{
			for(int i=0;i<2;i++){
				Matcher matcher = RegexUtils.getMatcher(content, dateRegExp);
				if (matcher != null && matcher.find()) {
					String date=content.substring(matcher.start(),matcher.end());
					matches.add(date);
					content = content.replace(date," ");
				}
			}
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,e);
		}
		return matches;
	}
	
	public ArrayList getListWithAliases(String dataType) {
		DBPreparedQuery dq = null;
		ArrayList data = new ArrayList();
		try {
			String dbQuery = "";
			if (dataType.equals("EMPLOYER")) {
				dbQuery = "dEmploymentHistory_getAllEmployersAliases";
			} else if (dataType.equals("DESIGNATION")) {
				dbQuery = "dEmploymentHistory_getAllDesignationsAliases";
			}
			dq = new DBPreparedQuery(dbQuery);
			data = dq.getResult();

		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
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
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return idMatched;
	}
	
//	public static void main(String[] args) {
//		EmploymentHistoryParser eph = new EmploymentHistoryParser();
//		String content =  "jul 2004 to jun'2006";
//		ArrayList<String> matches = new ArrayList<String>();
//		for(int i=0;i<2;i++){
//			System.out.println("content original = "+content);
//			Matcher matcher = RegexUtils.getMatcher(content, dateRegExp);
//			if (matcher != null && matcher.find()) {
//				String date1=content.substring(matcher.start(),matcher.end());
//				System.out.println(date1);
//				matches.add(date1);
//				content = content.replace(date1," ");
//				System.out.println("content = "+content);
//			}
//		}
//		System.out.println(matches);		
//	}
}