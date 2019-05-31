package com.talentPool.positions.manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.talentPool.admin.DuplicateSettingsConstants;
import com.talentPool.admin.dataobject.DuplicateSettingsData;
import com.talentPool.admin.manager.DuplicatePositionSettings;
import com.talentPool.applicant.ApplicantConstants;
import com.talentPool.applicant.constants.ImportConfigurationConstants;
import com.talentPool.applicant.dataobject.ApplicantDuplicateSearchData;
import com.talentPool.applicant.manager.ImportConfigurationManager;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.utils.Utils;
import com.talentPool.custom.constants.CustomFieldConstants;
import com.talentPool.custom.dataobject.CustomFieldData;
import com.talentPool.positions.constants.PositionConfigurationConstants;
import com.talentPool.positions.dataobject.PositionDuplicateSearchData;
import com.talentPool.selectionProcess.SelectionProcessConstants;

public class PositionDuplicateChecker {
	Pattern pattern;

	Matcher matcher;
	
	/**
	 * @param positionId
	 * @param name
	 * @param customFields
	 * @return
	 */
	public ArrayList<PositionDuplicateSearchData> getInternalDuplicateChecked(String positionId, String name, ArrayList<CustomFieldData> customFields) {
		ArrayList<PositionDuplicateSearchData> positions = null;
		ArrayList<DuplicateSettingsData> internalSettings = DuplicatePositionSettings.getInternalSettings();
		try {
			for (int i = 0; i < internalSettings.size(); i++) {
				DuplicateSettingsData data = internalSettings.get(i);
				if (!Utils.isBlankOrNull(data.getFieldCheckType())) {
					if (data.getFieldId().equals(DuplicateSettingsConstants.POSITION_NAME_ID) 
							&& data.getFieldType().equals(DuplicateSettingsConstants.FIELD_TYPE_SYSTEM)
							&& PositionScreenConfigurationManager.isDescriptionFieldShow(PositionConfigurationConstants.FIELD_NAME)) {
						ArrayList<PositionDuplicateSearchData> matchedPositions = null;
						if (data.getFieldCheckType().equals(DuplicateSettingsConstants.MATCH_EXAXCT)) {
							matchedPositions = splitNameAndGetExactPosition(name);
						} else {
							matchedPositions = getApproxNames(name);
						}
						positions = mergePositions(positions, matchedPositions);
					}/* else if (data.getFieldId().equals(DuplicateSettingsConstants.EMAIL_ID) 
							&& data.getFieldType().equals(DuplicateSettingsConstants.FIELD_TYPE_SYSTEM)
							&& (ImportConfigurationManager.isImportFieldShow(ImportConfigurationConstants.FIELD_EMAIL1)
									|| ImportConfigurationManager.isImportFieldShow(ImportConfigurationConstants.FIELD_EMAIL2))) {
						ArrayList<ApplicantDuplicateSearchData> matchedApplicants = checkIfExistsEmail(email1, email2);
						applicants = mergeApplicants(applicants, matchedApplicants);
					} else if (data.getFieldId().equals(DuplicateSettingsConstants.PHONE_ID) 
							&& data.getFieldType().equals(DuplicateSettingsConstants.FIELD_TYPE_SYSTEM)
							&& ImportConfigurationManager.isImportFieldShow(ImportConfigurationConstants.FIELD_MOBILE)) {
						ArrayList<ApplicantDuplicateSearchData> matchedApplicants = getDuplicatePhoneCandidates(cellPhone);
						applicants = mergeApplicants(applicants, matchedApplicants);
					} */else if (data.getFieldType().equals(DuplicateSettingsConstants.FIELD_TYPE_CUSTOM)) {
						// check custom fields
						for (int k = 0; customFields!=null && k < customFields.size(); k++) {
							CustomFieldData customFieldData = customFields.get(k);							
							if(customFieldData.getFieldId().equals(data.getFieldId()) &&
									PositionScreenConfigurationManager.isDescriptionFieldShow(customFieldData.getFieldName())) {
								ArrayList<PositionDuplicateSearchData> matchedPositions = checkDuplicateForCustomFieldValues(customFieldData.getFieldId(), customFieldData.getFieldValues(),
											customFieldData.getFieldDisplayName(), customFieldData.getFieldType());
								positions = mergePositions(positions, matchedPositions);
								break;
							}							
						}
					}
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in duplicate check", e);
		}		
		positions = filterPosition(positions, positionId);
		positions = sortSearchResult(positions);
		
		return positions;
	}
	
	/**
	 * remove the applicant, importrant while editing applicant record, so the apllicant which is
	 * edited removed from duplicates
	 * 
	 * @param applicants
	 * @param applicantId
	 * @return
	 */
	private ArrayList<PositionDuplicateSearchData> filterPosition(ArrayList<PositionDuplicateSearchData> positions, String positionId) {
		if (!Utils.isBlankOrNull(positionId)) {
			for (int i = 0; positions!=null && i < positions.size(); i++) {
				if (positions.get(i).getPositionId().equals(positionId)) {
					positions.remove(i);
				}
			}
		}
		return positions;
	}

	/**
	 * sort the search by priority
	 * 
	 * @param applicants
	 * @return
	 */
	private ArrayList<PositionDuplicateSearchData> sortSearchResult(ArrayList<PositionDuplicateSearchData> applicants) {
		if (applicants != null) {
			List ul = Collections.synchronizedList(applicants);
			Collections.sort(ul, new Comparator() {
				public int compare(Object o1, Object o2) {
					int p1 = ((PositionDuplicateSearchData) o1).getPriority();
					int p2 = ((PositionDuplicateSearchData) o2).getPriority();
					if (p1 < p2)
						return 1;
					else
						return -1;
				}
			});
			applicants = new ArrayList(ul);
		}
		return applicants;
	}
	
	/**
	 * mearge all the duplciates in single list and set the priority
	 * 
	 * @param positions
	 * @param matchedPositions
	 * @return
	 */
	private ArrayList<PositionDuplicateSearchData> mergePositions(ArrayList<PositionDuplicateSearchData> positions, ArrayList<PositionDuplicateSearchData> matchedPositions) {
		if (positions == null) {
			positions = matchedPositions;
		} else {
			if (matchedPositions != null) {
				for (int i = 0; i < matchedPositions.size(); i++) {
					boolean exists = false;
					for (int k = 0; k < positions.size(); k++) {
						if (positions.get(k).getPositionId().equals(matchedPositions.get(i).getPositionId())) {
							positions.get(k).setPriority(positions.get(k).getPriority() + 1);
							positions.get(k).getMatchedFields().addAll(matchedPositions.get(i).getMatchedFields());
							positions.get(k).getMatchedFieldValues().addAll(matchedPositions.get(i).getMatchedFieldValues());
							exists = true;
							break;
						}
					}
					if (!exists) {
						positions.add(matchedPositions.get(i));
					}
				}
			}
		}
		return positions;
	}

	
	/**
	 * 
	 * @param name
	 * @return all approximate name found applicants
	 */
	private ArrayList<PositionDuplicateSearchData> getApproxNames(String name) {
		ArrayList<PositionDuplicateSearchData> positions = null;
		try {
			positions = splitNameAndGetAllApproxPosition(name);
			positions = doApproxNameCheck(name, positions);
			positions = setMatchedLabels(positions, DuplicateSettingsConstants.LABEL_POSITION_NAME, name);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in duplicate approx name", e);
		}
		return positions;
	}

	/**
	 * 
	 * @param name
	 * @param applicants
	 * @return
	 */
	private ArrayList<PositionDuplicateSearchData> doApproxNameCheck(String name, ArrayList<PositionDuplicateSearchData> positions) {
		ArrayList<PositionDuplicateSearchData> positionsList = new ArrayList<PositionDuplicateSearchData>();
		for (int i = 0; i < positions.size(); i++) {
			PositionDuplicateSearchData positionDuplicateSearchData = positions.get(i);
			String[] names = removeDuplicateWords(name, positionDuplicateSearchData.getPositionTitle());
			boolean nameMatched = false;
			if (Utils.isBlankOrNull(names[0]) || Utils.isBlankOrNull(names[1])) {
				nameMatched = true;
			} else {
				// check for remaining words
				if (isApproximateMatch(names[0], names[1])) {
					nameMatched = true;
				}
			}
			if (nameMatched) {
				// applicantDuplicateSearchData.getMatchedFields().add(DuplicateSettingsConstants.LABEL_NAME);
				positionsList.add(positionDuplicateSearchData);
			}
		}
		return positionsList;
	}

	private boolean isApproximateMatch(String sourceName, String targetName) {
		boolean isApproxMatch = false;
		try {
			String[] names = sourceName.split(" ");
			for (int i = 0; i < names.length; i++) {
				// find word in target and replace it
				if (!Utils.isBlankOrNull(sourceName) && !Utils.isBlankOrNull(targetName) && !Utils.isBlankOrNull(names[i])) {
					boolean wordMatched = false;
					String word = names[i].substring(0, names[i].length() - 1);
					while (word.length() > 0 && !wordMatched) {
						int targetPrevLength = targetName.length();
						targetName = targetName.replaceAll("(?mid)\\b" + word + "\\b", "");
						if (targetPrevLength > targetName.length()) {
							wordMatched = true;
							sourceName = sourceName.replaceAll("(?mid)\\b" + names[i] + "\\b", "");
						}
						word = word.substring(0, word.length() - 1);
					}
					if (!wordMatched) {
						int targetPrevLength = targetName.length();
						targetName = targetName.replaceFirst("(?mid)\\b" + names[i] + ".*?\\b", "");
						if (targetPrevLength > targetName.length()) {
							wordMatched = true;
							sourceName = sourceName.replaceAll("(?mid)\\b" + names[i] + "\\b", "");
						}
					}
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in remove duplicate words", e);
		}
		if (Utils.isBlankOrNull(sourceName) || Utils.isBlankOrNull(targetName)) {
			isApproxMatch = true;
		}
		return isApproxMatch;

	}

	private String[] removeDuplicateWords(String sourceName, String targetName) {
		String[] srcAndTarget = new String[2];
		try {
			String[] names = sourceName.split(" ");
			for (int i = 0; i < names.length; i++) {
				// find word in target and replace it
				int targetPrevLength = targetName.length();
				targetName = targetName.replaceAll("(?mid)\\b" + names[i] + "\\b", "");
				if (targetPrevLength > targetName.length()) {
					sourceName = sourceName.replaceAll("(?mid)\\b" + names[i] + "\\b", "");
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in remove duplicate words", e);
		}
		srcAndTarget[0] = sourceName;
		srcAndTarget[1] = targetName;
		return srcAndTarget;
	}

	/**
	 * This function splits the string with space and search all words where length>1 in DB and
	 * return all matched word records
	 * 
	 * @param name
	 * @return
	 */
	private ArrayList<PositionDuplicateSearchData> splitNameAndGetAllApproxPosition(String name) {
		ArrayList<PositionDuplicateSearchData> positions = null;
		DBPreparedQuery dq = null;
		try {
			String[] names = name.split(" ");
			ArrayList<String> exps = new ArrayList<String>();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < names.length; i++) {
				if (!Utils.isBlankOrNull(names[i])) {
					if (names[i].length() > 1) {
						if (exps.size() == 0) {
							sb.append(" WHERE ");
						} else {
							sb.append(" OR ");
						}
						sb.append("position_title REGEXP ? ");
						exps.add("[[:<:]]" + names[i] + "[[:>:]]");
					}
				}
			}
			if (exps.size() > 0) {
				String[] dynParam = new String[1];
				dynParam[0] = sb.toString();
				dq = new DBPreparedQuery("dDuplicateChecker_GetApproxSimilarPositionNames", dynParam);
				for (int i = 0; i < exps.size(); i++) {
					dq.setString(i + 1, exps.get(i));
				}
				positions = dq.getResult();
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in duplicate approx name", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return positions;
	}

	private ArrayList<PositionDuplicateSearchData> splitNameAndGetExactPosition(String name) {
		ArrayList<PositionDuplicateSearchData> applicants = null;
		DBPreparedQuery dq = null;
		try {
			String[] names = new String[1];
			names[0]=name;
			ArrayList<String> exps = new ArrayList<String>();
			StringBuffer sb = new StringBuffer();
			String namePart = "";
			for (int i = 0; i < names.length; i++) {
				namePart = names[i];
				if (!Utils.isBlankOrNull(namePart)) {
					namePart = namePart.replace(".", "");
					namePart=namePart.trim();
					if (namePart.length() > 1) {
						if (exps.size() == 0) {
							sb.append(" WHERE ");
						} else {
							sb.append(" AND ");
						}
						sb.append(" position_title = ? ");
						exps.add( namePart );
					}
				}
			}
			if (exps.size() > 0) {
				//sb.append(" AND length(position_title) = ? ");
				String[] dynParam = new String[1];
				dynParam[0] = sb.toString();
				dq = new DBPreparedQuery("dDuplicateChecker_GetApproxSimilarPositionNames", dynParam);
				for (int i = 0; i < exps.size(); i++) {
					dq.setString(i + 1, exps.get(i));
				}
			//	dq.setInt(exps.size() + 1, name.length());
				applicants = dq.getResult();
				applicants = setMatchedLabels(applicants, DuplicateSettingsConstants.LABEL_POSITION_NAME, name);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in duplicate approx name", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return applicants;
	}

	

	private ArrayList<PositionDuplicateSearchData> removeDuplicates(ArrayList<PositionDuplicateSearchData> applicants) {
		if (applicants != null) {
			for (int i = 1; i < applicants.size(); i++) {
				for (int k = 0; k < i; k++) {
					if (applicants.get(i).getPositionId().equals(applicants.get(k).getPositionId())) {
						applicants.remove(i);
						break;
					}
				}
			}
		}
		return applicants;
	}



	private ArrayList<PositionDuplicateSearchData> checkDuplicateForCustomFieldValues(String customFieldId, String[] vals, String displayLabel, String customFieldType) {
		ArrayList<PositionDuplicateSearchData> applicants = null;
		StringBuffer labelValue = new StringBuffer();
		if (vals != null) {
			for (int c = 0; c < vals.length; c++) {
				ArrayList<PositionDuplicateSearchData> matchedApplicants = getDuplicateCustomFieldCandidates(customFieldId, vals[c], customFieldType);
				if(labelValue.length() > 0) {
					labelValue.append(", ");
				}
				labelValue = labelValue.append(vals[c]);
				if (matchedApplicants != null) {
					if (applicants == null) {
						applicants = matchedApplicants;
					} else {
						applicants.addAll(matchedApplicants);
					}
				}
			}
		}
		applicants = removeDuplicates(applicants);
		applicants = setMatchedLabels(applicants, displayLabel, labelValue.toString());
		return applicants;
	}

	private ArrayList<PositionDuplicateSearchData> getDuplicateCustomFieldCandidates(String customFieldId, String customFieldValue, String customFieldType) {
		DBPreparedQuery dq = null;
		ArrayList<PositionDuplicateSearchData> applicants = null;
		try {
			if (!Utils.isBlankOrNull(customFieldId) && !Utils.isBlankOrNull(customFieldValue)) {
				String[] dynParams = new String[1];
				dynParams[0] = " ";
				if (customFieldType.equals(CustomFieldConstants.TYPE_DATE)) {
					dynParams[0] += " AND tcfv.date_value = ?";
				} else if (customFieldType.equals(CustomFieldConstants.TYPE_NUMBER)) {
					dynParams[0] += " AND tcfv.number_value = ?";
				} else {
					dynParams[0] += " AND tcfv.string_value = ?";
				}
				
				dq = new DBPreparedQuery("dDuplicateChecker_GetDuplicateCustomFieldPositions", dynParams);
				dq.setId(1, customFieldId);
				if (customFieldType.equals(CustomFieldConstants.TYPE_DATE)) {
					dq.setDate(2, Utils.convertToSQLDate(customFieldValue, Utils.regEUDateFormat));
				} else if (customFieldType.equals(CustomFieldConstants.TYPE_NUMBER)) {
					dq.setDouble(2, Integer.parseInt(customFieldValue));
				} else {
					dq.setString(2, customFieldValue);
				}
				applicants = dq.getResult();
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while retriving applicant with duplicate custom fields", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return applicants;
	}
	
	/**
	 * set the fields matched
	 * 
	 * @param applicants
	 * @param labelMatched
	 * @return
	 */
	private ArrayList<PositionDuplicateSearchData> setMatchedLabels(ArrayList<PositionDuplicateSearchData> positions, String labelMatched, String labelValue) {
		if (positions != null) {
			for (int i = 0; i < positions.size(); i++) {
				positions.get(i).getMatchedFields().add(labelMatched);
				positions.get(i).getMatchedFieldValues().add(labelValue);
			}
		}
		return positions;
	}
}
