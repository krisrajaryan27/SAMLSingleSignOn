/**
 * 
 */
package com.talentPool.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.CommonUtils;
import com.talentPool.parser.dataobject.ParsedResultData;
import com.talentPool.parser.dataobject.PhoneParsedData;
import com.talentPool.parser.manager.ExpressionHandler;
import com.talentPool.parser.utils.LongestCommonSubstring;
import com.talentPool.parser.utils.ParserUtils;


/**
 * @author Ajeet
 *
  * Main parser class which takes input as string and returns the map of all the
 * fields extracted.
 */
public class ResumeParser implements Parser {
	private String sourceContent;

	private HashMap resultMap;


	/**
	 * @param sourceContent
	 */
	public ResumeParser(String sourceContent) {
		this.sourceContent = sourceContent;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.talentPool.parser.Parser#parse()
	 */
	public void parse() {
		// This method will call different methods based on prority
		// first reset the hashmap holding results
		// all the function will fill this map
		resultMap = new HashMap();
		if (sourceContent != null)
			sourceContent = sourceContent.trim();

		removeEmailForwardHeader();
		parseSource();
		parseEmail();
		parseName();
		parsePhone();
		parseSkills();
		Set entries = resultMap.entrySet();
	    java.util.Iterator it = entries.iterator();
	    while (it.hasNext()) {
	      Map.Entry entry = (Map.Entry) it.next();
	      TPLogger.getLogger().debug(entry.getKey() + "-->" + entry.getValue());
	    }
	}

	private void parseSkills() {
		SkillsParser skillsParser = new SkillsParser();
		int noOfSkiils = Integer.parseInt(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_MAX_SKILLS_PARSED).trim());
		ArrayList<String> skills = skillsParser.getArrayListOfParsedSkillIdsAndNames(sourceContent, noOfSkiils);
		resultMap.put(ParserConstants.FIELD_SKILLS, skills);
	}

	private void removeEmailForwardHeader() {
		ArrayList expressions = ExpressionHandler.getExpressionsForField(ParserConstants.FIELD_FORWARD_HEADER);
		applyExpressionsAndLoadResultsInMap(expressions, sourceContent, ParserConstants.FIELD_FORWARD_HEADER);
		ArrayList headers = (ArrayList) resultMap.get(ParserConstants.FIELD_FORWARD_HEADER);
		if (headers != null) {
			int prevEnd = 0;
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < headers.size(); i++) {
				ParsedResultData pData = (ParsedResultData) headers.get(i);
				sb.append(sourceContent.substring(prevEnd, pData.getStart()));
				prevEnd = pData.getEnd();
			}
			sb.append(sourceContent.substring(prevEnd));
			sourceContent = sb.toString();
		}
	}

	private void parseSource() {
		SourceParser sourceParser = new SourceParser();
		String sourceId = sourceParser.getParsedSourceIdFromMaster(sourceContent);
		if(sourceId!=null){
			ArrayList<String> src = new ArrayList<String>();
			src.add(0, sourceId);
			src.add(1, CommonUtils.getSourceName(sourceId));
			resultMap.put(ParserConstants.FIELD_SOURCE, src);
		}
	}

	private void parsePhone() {
		try {
			ArrayList expressions = ExpressionHandler.getExpressionsForField(ParserConstants.FIELD_PHONE);
			applyExpressionsAndLoadResultsInMap(expressions, sourceContent, ParserConstants.FIELD_PHONE);
			ArrayList phones = (ArrayList) resultMap.get(ParserConstants.FIELD_PHONE);
			if (phones != null) {
				// Remove unwanted characters and trim the phone numbers
				// also remove the phone number with length<6
				for (int i = 0; i < phones.size(); i++) {
					ParsedResultData pData = (ParsedResultData) phones.get(i);
					pData.setMatchedString(pData.getMatchedString().replaceAll("[\\D\\ \\t]+", " ").trim());
					if (pData.getMatchedString().replaceAll("\\ ", "").length() < 6) {
						TPLogger.getLogger().debug("Phone length<6 removing the match");
						phones.remove(i);
						i--;
					} else {
						TPLogger.getLogger().debug("Phone length>6 ");
					}
				}
				// remove short phone numbers
				// remove duplicates follows different algorithm
				// If ph1 and ph2 are equal then ph2 is removed.
				// else if ph1 > ph2 then ph2 is compared with the last portion of ph1
				// if equal then ph2 is removed
				// else if ph2 > ph1 then ph1 is compared with the last portion of ph2
				// if equal then ph1 is removed
				for (int i = 0; i < phones.size(); i++) {
					ParsedResultData pData = (ParsedResultData) phones.get(i);
					String tmpPh1 = pData.getMatchedString().replaceAll("[\\ ]", "");
					for (int k = i + 1; k < phones.size(); k++) {
						ParsedResultData pData2 = (ParsedResultData) phones.get(k);
						String tmpPh2 = pData2.getMatchedString().replaceAll("[\\ ]", "");
						if (tmpPh2.equals(tmpPh1)) {
							phones.remove(k);
							k--;
						} else {
							String tmpTrimPh1 = tmpPh1;
							String tmpTrimPh2 = tmpPh2;
							if (tmpPh2.length() > tmpPh1.length()) {
								tmpTrimPh2 = tmpPh2.substring(tmpPh2.length() - tmpPh1.length());
							} else if (tmpPh2.length() < tmpPh1.length()) {
								tmpTrimPh1 = tmpPh1.substring(tmpPh1.length() - tmpPh2.length());
							}
							if (tmpTrimPh1.equals(tmpTrimPh2)) {
								if (tmpPh2.length() > tmpPh1.length() && tmpPh2.length() < 16) {
									phones.remove(i);
									i--;
								} else {
									phones.remove(k);
									k++;
								}
							}
						}
					}
				}

				phones = decidePhoneTypes(phones);
				resultMap.put(ParserConstants.FIELD_PHONE, phones);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting phone", e);
		}
	}

	private ArrayList<PhoneParsedData> decidePhoneTypes(ArrayList phones) {
		ArrayList<PhoneParsedData> newPhones = new ArrayList<PhoneParsedData>();
		try {
			if (phones != null && phones.size() > 0) {
				// iterate through all and search non spaced number having digit
				// "9" on the 10th position from right
				for (int i = 0; i < phones.size(); i++) {
					ParsedResultData pData = (ParsedResultData) phones.get(i);
					String tmpPh = pData.getMatchedString();
					boolean mobile = false;
					if (tmpPh.indexOf(" ") >= 0) {
						tmpPh = tmpPh.substring(tmpPh.lastIndexOf(" "));
					}
					if (tmpPh.length() >= 10) {
						if (tmpPh.charAt(tmpPh.length() - 10) == '9') {
							mobile = true;
						}
					}

					if (!mobile) {
						tmpPh = pData.getMatchedString().replaceAll("\\ ", "");
						if (tmpPh.length() >= 10) {
							if (tmpPh.charAt(tmpPh.length() - 10) == '9') {
								mobile = true;
							}
						}
					}

					if (mobile) {
						newPhones.add(new PhoneParsedData(pData, ParserConstants.PHONE_TYPE_MOBILE));
					} else {
						newPhones.add(new PhoneParsedData(pData, ParserConstants.PHONE_TYPE_OTHER));
					}
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in decidePhoneTypes", e);
		}
		return newPhones;
	}

	private void parseEmail() {
		ArrayList expressions = ExpressionHandler.getExpressionsForField(ParserConstants.FIELD_EMAIL);
		applyExpressionsAndLoadResultsInMap(expressions, sourceContent, ParserConstants.FIELD_EMAIL);
		ArrayList emails = (ArrayList) resultMap.get(ParserConstants.FIELD_EMAIL);
		emails = ParserUtils.removeDuplicateMatches(emails);
	}

	private void parseName() {
		ArrayList expressions = ExpressionHandler.getExpressionsForField(ParserConstants.FIELD_NAME);
		applyExpressionsAndLoadResultsInMap(expressions, sourceContent, ParserConstants.FIELD_NAME);
		ArrayList names = (ArrayList) resultMap.get(ParserConstants.FIELD_NAME);

		// optimize and Remove duplicate matches without changing order
		names = ParserUtils.removeDuplicateMatches(names);
		// Remove short names
		names = ParserUtils.removeShortMatches(names, 4);
		// Move the matched parts upward
		names = moveStringsUpwardsFoundInPossibleRange(names);
		// check probability with emails
		names = crossCheckWithEmailAndSetProbability(names);

		for (int i = 0; i < names.size(); i++) {
			ParsedResultData pRData = (ParsedResultData) names.get(i);
			String matchedName = pRData.getMatchedString().replaceAll("\\.", " ");
			matchedName = matchedName.replaceAll("\\b[a-zA-Z]\\b", "");
			// If matched name contains very common words found in resume or any
			// grammar then simply remove it
			if (containsCommonDictionaryWords(matchedName)) {
				names.remove(i);
				i = i - 1;
			} else {
				if (containsDictionaryWords(matchedName)) {
					if (pRData.getProbability() == 0) {
						names.remove(i);
						i = i - 1;
					}
				}
			}
		}

		// Check the name in source
		ArrayList src = (ArrayList) resultMap.get(ParserConstants.FIELD_SOURCE);
		if (src != null) {
			String srcName = (String) src.get(1);
			for (int i = 0; i < names.size(); i++) {
				ParsedResultData pData = (ParsedResultData) names.get(i);
				if (srcName.replaceAll("[\\s\\.\\W]+", " ").trim().equalsIgnoreCase(pData.getMatchedString().replaceAll("[\\s\\.\\W]+", " ").trim())) {
					names.remove(i--);
				}
			}
		}

		resultMap.put(ParserConstants.FIELD_NAME, names);
	}

	private ArrayList moveStringsUpwardsFoundInPossibleRange(ArrayList al) {
		// find the index for first 10 and last 10 lines
		ArrayList bounds = getUpperIndexOfEachLine(sourceContent);
		int lowerIndex = 0;
		int upperIndex = 0;
		if (bounds.size() > 10) {
			upperIndex = Integer.parseInt((String) bounds.get(10));
		}
		if (bounds.size() > 20) {
			lowerIndex = Integer.parseInt((String) bounds.get(bounds.size() - 11));
		}

		ArrayList<ParsedResultData> upperAl = new ArrayList<ParsedResultData>();
		ArrayList<ParsedResultData> lowerAl = new ArrayList<ParsedResultData>();
		for (int i = 0; i < al.size(); i++) {
			ParsedResultData pRData = (ParsedResultData) al.get(i);
			int endIndex = pRData.getEnd();
			boolean removed = false;
			if (upperIndex > 0) {
				if (upperIndex > endIndex) {
					upperAl.add(pRData);
					al.remove(i);
					i = i - 1;
					removed = true;
				}
			}
			if (lowerIndex > 0 && removed == false) {
				if (lowerIndex < endIndex) {
					al.remove(i);
					i = i - 1;
					lowerAl.add(pRData);
				}
			}
		}

		// sort two array list
		if (upperAl.size() > 0) {
			List ul = Collections.synchronizedList(upperAl);
			Collections.sort(ul, new Comparator() {
				public int compare(Object o1, Object o2) {
					int p1 = ((ParsedResultData) o1).getEnd();
					int p2 = ((ParsedResultData) o2).getEnd();
					if (p1 > p2)
						return 1;
					else
						return -1;
				}
			});
			upperAl = new ArrayList(ul);
		}
		if (lowerAl.size() > 0) {
			List ul = Collections.synchronizedList(lowerAl);
			Collections.sort(ul, new Comparator() {
				public int compare(Object o1, Object o2) {
					int p1 = ((ParsedResultData) o1).getEnd();
					int p2 = ((ParsedResultData) o2).getEnd();
					if (p1 < p2)
						return 1;
					else
						return -1;
				}
			});
			lowerAl = new ArrayList(ul);
		}

		// merge arrayLists
		upperAl.addAll(lowerAl);
		upperAl.addAll(al);
		return upperAl;
	}

	private ArrayList crossCheckWithEmailAndSetProbability(ArrayList names) {
		if (names != null) {
			// get all emails, get the text before @ sign and remove all not
			// alphabets and store the result in one arraylist
			ArrayList emails = (ArrayList) resultMap.get(ParserConstants.FIELD_EMAIL);
			ArrayList<String> formatedEmails = new ArrayList<String>();
			if (emails != null) {
				for (int k = 0; k < emails.size(); k++) {
					ParsedResultData eMData = (ParsedResultData) emails.get(k);
					String email = eMData.getMatchedString();
					String namePart = email.substring(0, email.indexOf("@"));
					namePart = namePart.replaceAll("[^a-zA-Z]", "");
					formatedEmails.add(namePart.toLowerCase());
				}
			}
			LongestCommonSubstring application = new LongestCommonSubstring();
			String common = "";
			int maxProbability = 0;
			for (int i = 0; i < names.size(); i++) {
				ParsedResultData pRData = (ParsedResultData) names.get(i);
				// Check this in to emails
				int probForEmail = 0;
				for (int k = 0; k < formatedEmails.size(); k++) {
					common = application.lcs(pRData.getMatchedString().toLowerCase(), (String) formatedEmails.get(k));
					if (common.length() > probForEmail)
						probForEmail = common.length();
				}
				if (probForEmail < 4)
					probForEmail = 0;
				if (probForEmail > maxProbability)
					maxProbability = probForEmail;
				pRData.setProbability(probForEmail);

			}
			if (maxProbability > 0) {
				names = resetProbabilities(names, maxProbability);
				names = new ArrayList(reOrderListUsingProbability(names));
			}
		}
		return names;
	}

	private ArrayList resetProbabilities(ArrayList al, int minProbability) {
		for (int i = 0; i < al.size(); i++) {
			ParsedResultData pRData = (ParsedResultData) al.get(i);
			if (pRData.getProbability() < minProbability) {
				pRData.setProbability(0);
			}
		}
		return al;
	}

	private List reOrderListUsingProbability(List al) {
		List list = Collections.synchronizedList(al);
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				int p1 = ((ParsedResultData) o1).getProbability();
				int p2 = ((ParsedResultData) o2).getProbability();
				if (p1 < p2)
					return 1;
				else
					return -1;
			}
		});
		return list;
	}

	private ArrayList<String> getUpperIndexOfEachLine(String content) {
		ArrayList<String> bounds = new ArrayList<String>();
		Pattern p = Pattern.compile("(?mi)^.*[a-z]+.*$");
		boolean theEnd = false;
		Matcher m = p.matcher(content);
		while (!theEnd) {
			theEnd = !m.find();
			if (!theEnd) {
				bounds.add("" + m.end());
			}
		}
		return bounds;
	}

	public void applyExpressionsAndLoadResultsInMap(ArrayList expressions, String content, String fieldType) {
		try {
			if (expressions != null) {
				ArrayList results = (ArrayList) resultMap.get(fieldType);
				if (results == null) {
					results = new ArrayList();
				}

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
				resultMap.put(fieldType, results);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while applying expressions", e);
		}
	}

	public boolean containsCommonDictionaryWords(String strToCheck) {
		boolean found = false;
		String searchPattern = "\\b(" + ParserUtils.getPipeSeparatedString(ParserConstants.commonWords) + ")\\b";
		Pattern p = Pattern.compile(searchPattern, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
		boolean theEnd = false;
		Matcher m = p.matcher(strToCheck);
		while (!theEnd) {
			theEnd = !m.find();
			if (!theEnd) {
				found = true;
				break;
			}
		}
		return found;
	}

	public boolean containsDictionaryWords(String strToCheck) {
		boolean found = false;
		try {
			if (strToCheck != null) {
				String searchPattern = "\\b(" + ParserUtils.getPipeSeparatedString(strToCheck) + ")\\b";

				if (searchPattern.indexOf("|") > 0) {
					Pattern p = Pattern.compile(searchPattern, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
					// Check for common words first
					boolean theEnd = false;
					if (!found) {
						theEnd = false;
						Matcher m = p.matcher(ParserConstants.dictionary);
						while (!theEnd) {
							theEnd = !m.find();
							if (!theEnd) {
								found = true;
								break;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Caught Exception", e);
		}
		return found;
	}

	public HashMap getParsedMap() {
		parse();
		return resultMap;
	}


}
