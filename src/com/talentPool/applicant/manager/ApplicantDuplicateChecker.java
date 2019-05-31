/**
 * 
 */
package com.talentPool.applicant.manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.talentPool.admin.DuplicateSettingsConstants;
import com.talentPool.admin.dataobject.DuplicateSettingsData;
import com.talentPool.admin.manager.DuplicateSettings;
import com.talentPool.applicant.ApplicantConstants;
import com.talentPool.applicant.constants.ImportConfigurationConstants;
import com.talentPool.applicant.dataobject.ApplicantDuplicateSearchData;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.utils.Utils;
import com.talentPool.custom.constants.CustomFieldConstants;
import com.talentPool.custom.dataobject.CustomFieldData;
import com.talentPool.custom.manager.CustomFieldManager;
import com.talentPool.selectionProcess.SelectionProcessConstants;

/**
 * @author shivprasad
 * 
 */
public class ApplicantDuplicateChecker {
	Pattern pattern;

	Matcher matcher;

	/**
	 * @param fields
	 * @return
	 */
	public boolean isDuplicateVendorResume(HashMap<String, String[]> fields) {
		boolean isDuplicate = false;
		ArrayList<ApplicantDuplicateSearchData> applicants = null;
		ArrayList<DuplicateSettingsData> internalSettings = DuplicateSettings.getVendorSettings();
		CustomFieldManager customFieldManager = new CustomFieldManager();
		try {
			for (int i = 0; i < internalSettings.size(); i++) {
				DuplicateSettingsData data = internalSettings.get(i);
				if (!Utils.isBlankOrNull(data.getFieldCheckType())) {
					if (data.getFieldId().equals(DuplicateSettingsConstants.NAME_ID) 
							&& data.getFieldType().equals(DuplicateSettingsConstants.FIELD_TYPE_SYSTEM)
							&& ImportConfigurationManager.isVendorFieldShow(ImportConfigurationConstants.FIELD_NAME)) {
						String[] fieldValues = fields.get(data.getFieldLabel());
						if (fieldValues != null) {
							ArrayList<ApplicantDuplicateSearchData> matchedApplicants = null;
							if (data.getFieldCheckType().equals(DuplicateSettingsConstants.MATCH_EXAXCT)) {
								matchedApplicants = splitNameAndGetExactCandidate(fieldValues[0]);
							} else {
								matchedApplicants = getApproxNames(fieldValues[0]);
							}
							if (matchedApplicants != null && matchedApplicants.size() > 0) {
								isDuplicate = true;
							}
						}

					}  else if (data.getFieldId().equals(DuplicateSettingsConstants.EMAIL_ID) 
							&& data.getFieldType().equals(DuplicateSettingsConstants.FIELD_TYPE_SYSTEM)
							&& (ImportConfigurationManager.isVendorFieldShow(ImportConfigurationConstants.FIELD_EMAIL1) 
									|| ImportConfigurationManager.isVendorFieldShow(ImportConfigurationConstants.FIELD_EMAIL2))) {
						String[] fieldValues = fields.get(data.getFieldLabel());
						if (fieldValues != null) {
							ArrayList<ApplicantDuplicateSearchData> matchedApplicants = checkIfExistsEmail(fieldValues[0], fieldValues[1]);
//							applicants = mergeApplicants(applicants, matchedApplicants);
							if (matchedApplicants != null && matchedApplicants.size() > 0) {
								isDuplicate = true;
							}
						}
					} else if (data.getFieldId().equals(DuplicateSettingsConstants.PHONE_ID) 
							&& data.getFieldType().equals(DuplicateSettingsConstants.FIELD_TYPE_SYSTEM)
							&& ImportConfigurationManager.isVendorFieldShow(ImportConfigurationConstants.FIELD_MOBILE)) {
						String[] fieldValues = fields.get(data.getFieldLabel());
						if (fieldValues != null) {
							ArrayList<ApplicantDuplicateSearchData> matchedApplicants = getDuplicatePhoneCandidates(fieldValues[0]);
//							applicants = mergeApplicants(applicants, matchedApplicants);
							if (matchedApplicants != null && matchedApplicants.size() > 0) {
								isDuplicate = true;
							}
						}
					} else if (data.getFieldType().equals(DuplicateSettingsConstants.FIELD_TYPE_CUSTOM)) {
						CustomFieldData customFieldData = customFieldManager.getCustomField(data.getFieldId());
						if(customFieldData != null && ImportConfigurationManager.isVendorFieldShow(customFieldData.getFieldName())) {
							String[] fieldValues = fields.get(data.getFieldLabel());
							if (fieldValues != null) {
								ArrayList<ApplicantDuplicateSearchData> matchedApplicants = checkDuplicateForCustomFieldValues(customFieldData.getFieldId(), fieldValues,
										customFieldData.getFieldDisplayName(), customFieldData.getFieldType());
								if (matchedApplicants != null && matchedApplicants.size() > 0) {
									isDuplicate = true;
								}
							}
						}
					}
				}
				if (isDuplicate) {
					break;
				}

			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in duplicate check", e);
		}
		return isDuplicate;
	}

	public ArrayList<ApplicantDuplicateSearchData> getVendorDuplicateChecked(String applicantId, String name, String email1, String email2, String cellPhone, ArrayList<CustomFieldData> customFields) {
		ArrayList<ApplicantDuplicateSearchData> applicants = null;
		ArrayList<DuplicateSettingsData> internalSettings = DuplicateSettings.getVendorSettings();
		try {
			for (int i = 0; i < internalSettings.size(); i++) {
				DuplicateSettingsData data = internalSettings.get(i);
				if (!Utils.isBlankOrNull(data.getFieldCheckType())) {
					if (data.getFieldId().equals(DuplicateSettingsConstants.NAME_ID) && data.getFieldType().equals(DuplicateSettingsConstants.FIELD_TYPE_SYSTEM)) {
						ArrayList<ApplicantDuplicateSearchData> matchedApplicants = null;
						if (data.getFieldCheckType().equals(DuplicateSettingsConstants.MATCH_EXAXCT) || !Utils.isBlankOrNull(applicantId)) {
							matchedApplicants = splitNameAndGetExactCandidate(name);
						} else {
							matchedApplicants = getApproxNames(name);
						}
						applicants = mergeApplicants(applicants, matchedApplicants);
					} else if (data.getFieldType().equals(DuplicateSettingsConstants.FIELD_TYPE_CUSTOM)) {
						// check custom fields
						for (int k = 0; customFields!=null && k < customFields.size(); k++) {
							CustomFieldData customFieldData = customFields.get(k);
							if (customFieldData.getFieldId().equals(data.getFieldId())) {
								ArrayList<ApplicantDuplicateSearchData> matchedApplicants = checkDuplicateForCustomFieldValues(customFieldData.getFieldId(), customFieldData.getFieldValues(),
										customFieldData.getFieldDisplayName(), customFieldData.getFieldType());
								applicants = mergeApplicants(applicants, matchedApplicants);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in duplicate check", e);
		}
		applicants = filterApplicant(applicants, applicantId);
		applicants = sortSearchResult(applicants);
		return applicants;
	}
	
	/**
	 * @param applicantId
	 * @param name
	 * @param email1
	 * @param email2
	 * @param cellPhone
	 * @param customFields
	 * @return
	 */
	public ArrayList<ApplicantDuplicateSearchData> getInternalDuplicateChecked(String applicantId, String name, String email1, String email2, String cellPhone, ArrayList<CustomFieldData> customFields) {
		ArrayList<ApplicantDuplicateSearchData> applicants = null;
		ArrayList<DuplicateSettingsData> internalSettings = DuplicateSettings.getInternalSettings();
		try {
			for (int i = 0; i < internalSettings.size(); i++) {
				DuplicateSettingsData data = internalSettings.get(i);
				if (!Utils.isBlankOrNull(data.getFieldCheckType())) {
					if (data.getFieldId().equals(DuplicateSettingsConstants.NAME_ID) 
							&& data.getFieldType().equals(DuplicateSettingsConstants.FIELD_TYPE_SYSTEM)
							&& ImportConfigurationManager.isImportFieldShow(ImportConfigurationConstants.FIELD_NAME)) {
						ArrayList<ApplicantDuplicateSearchData> matchedApplicants = null;
						if (data.getFieldCheckType().equals(DuplicateSettingsConstants.MATCH_EXAXCT) || !Utils.isBlankOrNull(applicantId)) {
							matchedApplicants = splitNameAndGetExactCandidate(name);
						} else {
							matchedApplicants = getApproxNames(name);
						}
						applicants = mergeApplicants(applicants, matchedApplicants);
					} else if (data.getFieldId().equals(DuplicateSettingsConstants.EMAIL_ID) 
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
					} else if (data.getFieldType().equals(DuplicateSettingsConstants.FIELD_TYPE_CUSTOM)) {
						// check custom fields
						for (int k = 0; customFields!=null && k < customFields.size(); k++) {
							CustomFieldData customFieldData = customFields.get(k);							
							if(customFieldData.getFieldId().equals(data.getFieldId()) &&
									ImportConfigurationManager.isImportFieldShow(customFieldData.getFieldName())) {
								ArrayList<ApplicantDuplicateSearchData> matchedApplicants = checkDuplicateForCustomFieldValues(customFieldData.getFieldId(), customFieldData.getFieldValues(),
											customFieldData.getFieldDisplayName(), customFieldData.getFieldType());
								applicants = mergeApplicants(applicants, matchedApplicants);
								break;
							}							
						}
					}
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in duplicate check", e);
		}		
		applicants = filterApplicant(applicants, applicantId);
		applicants = sortSearchResult(applicants);
		applicants = getDuplicateApplicantStatusDetails(applicants);
		return applicants;
	}

	/**
	 * remove the applicant, importrant while editing applicant record, so the apllicant which is
	 * edited removed from duplicates
	 * 
	 * @param applicants
	 * @param applicantId
	 * @return
	 */
	private ArrayList<ApplicantDuplicateSearchData> filterApplicant(ArrayList<ApplicantDuplicateSearchData> applicants, String applicantId) {
		if (!Utils.isBlankOrNull(applicantId)) {
			for (int i = 0; applicants!=null && i < applicants.size(); i++) {
				if (applicants.get(i).getApplicantId().equals(applicantId)) {
					applicants.remove(i);
				}
			}
		}
		return applicants;
	}

	/**
	 * sort the search by priority
	 * 
	 * @param applicants
	 * @return
	 */
	private ArrayList<ApplicantDuplicateSearchData> sortSearchResult(ArrayList<ApplicantDuplicateSearchData> applicants) {
		if (applicants != null) {
			List ul = Collections.synchronizedList(applicants);
			Collections.sort(ul, new Comparator() {
				public int compare(Object o1, Object o2) {
					int p1 = ((ApplicantDuplicateSearchData) o1).getPriority();
					int p2 = ((ApplicantDuplicateSearchData) o2).getPriority();
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
	 * set the fields matched
	 * 
	 * @param applicants
	 * @param labelMatched
	 * @return
	 */
	private ArrayList<ApplicantDuplicateSearchData> setMatchedLabels(ArrayList<ApplicantDuplicateSearchData> applicants, String labelMatched, String labelValue) {
		if (applicants != null) {
			for (int i = 0; i < applicants.size(); i++) {
				applicants.get(i).getMatchedFields().add(labelMatched);
				applicants.get(i).getMatchedFieldValues().add(labelValue);
			}
		}
		return applicants;
	}

	/**
	 * mearge all the duplciates in single list and set the priority
	 * 
	 * @param applicants
	 * @param matchedApplicants
	 * @return
	 */
	private ArrayList<ApplicantDuplicateSearchData> mergeApplicants(ArrayList<ApplicantDuplicateSearchData> applicants, ArrayList<ApplicantDuplicateSearchData> matchedApplicants) {
		if (applicants == null) {
			applicants = matchedApplicants;
		} else {
			if (matchedApplicants != null) {
				for (int i = 0; i < matchedApplicants.size(); i++) {
					boolean exists = false;
					for (int k = 0; k < applicants.size(); k++) {
						if (applicants.get(k).getApplicantId().equals(matchedApplicants.get(i).getApplicantId())) {
							applicants.get(k).setPriority(applicants.get(k).getPriority() + 1);
							applicants.get(k).getMatchedFields().addAll(matchedApplicants.get(i).getMatchedFields());
							applicants.get(k).getMatchedFieldValues().addAll(matchedApplicants.get(i).getMatchedFieldValues());
							exists = true;
							break;
						}
					}
					if (!exists) {
						applicants.add(matchedApplicants.get(i));
					}
				}
			}
		}
		return applicants;
	}

	private ArrayList<ApplicantDuplicateSearchData> getDuplicateApplicantStatusDetails(ArrayList<ApplicantDuplicateSearchData> applicants){
		String applicantIds = "";
		if(applicants!=null && applicants.size()>0){
			for (ApplicantDuplicateSearchData applicantDuplicateSearchData : applicants) {
				if(applicantIds.length()>0){
					applicantIds += ",";
				}
				applicantIds += applicantDuplicateSearchData.getApplicantId();
			}
		
			String[] dynParam = new String[1];
			dynParam[0] = applicantIds;
			DBPreparedQuery dq = null;
			ArrayList<ApplicantDuplicateSearchData> applicantsWithStatusData = null;
			try{
				dq = new DBPreparedQuery("dDuplicateChecker_GetDuplicateApplicantStatusDetails",dynParam);					
				dq.setString(1, ApplicantConstants.APPLICANT_STATUS_BLACKLISTED);
				dq.setString(2, SelectionProcessConstants.STEP_REJECT);
				dq.setString(3, SelectionProcessConstants.STEP_TITLE_REJECT);
				dq.setString(4, SelectionProcessConstants.STEP_ON_HOLD);
				dq.setString(5, SelectionProcessConstants.STEP_TITLE_ON_HOLD);
				dq.setString(6, SelectionProcessConstants.STEP_JOIN );
				dq.setString(7, SelectionProcessConstants.STEP_TITLE_JOINED );
				dq.setString(8, SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT);
				dq.setString(9, SelectionProcessConstants.STEP_TITLE_REJECT);
				dq.setString(10, SelectionProcessConstants.STEP_NOT_ATTENDED);
				dq.setString(11, SelectionProcessConstants.STEP_TITLE_REJECT);
				dq.setString(12, SelectionProcessConstants.STEP_POSITION_CLOSED_REJECT);
				dq.setString(13, SelectionProcessConstants.STEP_TITLE_POSITION_CLOSED_REJECT);
				applicantsWithStatusData = dq.getResult();
			} catch (Exception e) {
				TPLogger.getLogger().error("Error while getting status detail for applicants", e);
			} finally {
				if (dq != null) {
					dq.releaseConnection();
				}
			}
			
			applicants = mergeApplicants(applicantsWithStatusData, applicants);
		}
		return applicants;
	}
	/**
	 * 
	 * @param name
	 * @return all approximate name found applicants
	 */
	private ArrayList<ApplicantDuplicateSearchData> getApproxNames(String name) {
		ArrayList<ApplicantDuplicateSearchData> applicants = null;
		try {
			applicants = splitNameAndGetAllApproxCandidate(name);
			applicants = doApproxNameCheck(name, applicants);
			applicants = setMatchedLabels(applicants, DuplicateSettingsConstants.LABEL_NAME, name);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in duplicate approx name", e);
		}
		return applicants;
	}

	/**
	 * 
	 * @param name
	 * @param applicants
	 * @return
	 */
	private ArrayList<ApplicantDuplicateSearchData> doApproxNameCheck(String name, ArrayList<ApplicantDuplicateSearchData> applicants) {
		ArrayList<ApplicantDuplicateSearchData> applicatsList = new ArrayList<ApplicantDuplicateSearchData>();
		for (int i = 0; i < applicants.size(); i++) {
			ApplicantDuplicateSearchData applicantDuplicateSearchData = applicants.get(i);
			String[] names = removeDuplicateWords(name, applicantDuplicateSearchData.getApplicantName());
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
				applicatsList.add(applicantDuplicateSearchData);
			}
		}
		return applicatsList;
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
	private ArrayList<ApplicantDuplicateSearchData> splitNameAndGetAllApproxCandidate(String name) {
		ArrayList<ApplicantDuplicateSearchData> applicants = null;
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
						sb.append(" applicant_name REGEXP ? ");
						exps.add("[[:<:]]" + names[i] + "[[:>:]]");
					}
				}
			}
			if (exps.size() > 0) {
				String[] dynParam = new String[1];
				dynParam[0] = sb.toString();
				dq = new DBPreparedQuery("dDuplicateChecker_GetApproxSimilarNames", dynParam);
				for (int i = 0; i < exps.size(); i++) {
					dq.setString(i + 1, exps.get(i));
				}
				applicants = dq.getResult();
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

	private ArrayList<ApplicantDuplicateSearchData> splitNameAndGetExactCandidate(String name) {
		ArrayList<ApplicantDuplicateSearchData> applicants = null;
		DBPreparedQuery dq = null;
		try {
			String[] names = name.split(" ");
			ArrayList<String> exps = new ArrayList<String>();
			StringBuffer sb = new StringBuffer();
			String namePart = "";
			for (int i = 0; i < names.length; i++) {
				namePart = names[i];
				if (!Utils.isBlankOrNull(namePart)) {
					namePart = namePart.replace(".", "");
					if (namePart.length() > 1) {
						if (exps.size() == 0) {
							sb.append(" WHERE ");
						} else {
							sb.append(" AND ");
						}
						sb.append(" applicant_name REGEXP ? ");
						exps.add("[[:<:]]" + namePart + "[[:>:]]");
					}
				}
			}
			if (exps.size() > 0) {
				sb.append(" AND length(applicant_name) = ? ");
				String[] dynParam = new String[1];
				dynParam[0] = sb.toString();
				dq = new DBPreparedQuery("dDuplicateChecker_GetApproxSimilarNames", dynParam);
				for (int i = 0; i < exps.size(); i++) {
					dq.setString(i + 1, exps.get(i));
				}
				dq.setInt(exps.size() + 1, name.length());
				applicants = dq.getResult();
				applicants = setMatchedLabels(applicants, DuplicateSettingsConstants.LABEL_NAME, name);
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

	private ArrayList<ApplicantDuplicateSearchData> checkIfExistsEmail(String email1, String email2) {
		ArrayList<ApplicantDuplicateSearchData> applicants1 = getDuplicateEmailCandidates(email1);
		ArrayList<ApplicantDuplicateSearchData> applicants2 = getDuplicateEmailCandidates(email2);
		ArrayList<ApplicantDuplicateSearchData> applicants = null;
		StringBuffer labelValue = new StringBuffer();
		if (applicants1 != null && applicants1.size() > 0) {
			applicants = applicants1;
			labelValue = labelValue.append(email1);
		}
		if (applicants2 != null && applicants2.size() > 0) {
			if (applicants == null) {
				applicants = applicants1;
			} else {
				applicants.addAll(applicants2);
			}
			if(labelValue.length() > 0) {
				labelValue = labelValue.append(", ");
			}
			labelValue = labelValue.append(email2);
		}
		applicants = removeDuplicates(applicants);
		applicants = setMatchedLabels(applicants, DuplicateSettingsConstants.LABEL_EMAIL, labelValue.toString());
		return applicants;
	}

	private ArrayList<ApplicantDuplicateSearchData> removeDuplicates(ArrayList<ApplicantDuplicateSearchData> applicants) {
		if (applicants != null) {
			for (int i = 1; i < applicants.size(); i++) {
				for (int k = 0; k < i; k++) {
					if (applicants.get(i).getApplicantId().equals(applicants.get(k).getApplicantId())) {
						applicants.remove(i);
						break;
					}
				}
			}
		}
		return applicants;
	}

	private ArrayList<ApplicantDuplicateSearchData> getDuplicateEmailCandidates(String emailId) {
		DBPreparedQuery dq = null;
		ArrayList<ApplicantDuplicateSearchData> applicants = null;
		try {
			if (!Utils.isBlankOrNull(emailId)) {
				dq = new DBPreparedQuery("dDuplicateChecker_GetDuplicateEmailCandidates");
				dq.setString(1, emailId);
				dq.setString(2, emailId);
				applicants = dq.getResult();
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while retriving applicant with duplicate emails", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return applicants;
	}

	private ArrayList<ApplicantDuplicateSearchData> getDuplicatePhoneCandidates(String cellPhoneNumber) {
		DBPreparedQuery dq = null;
		ArrayList<ApplicantDuplicateSearchData> applicants = null;
		try {
			if (!Utils.isBlankOrNull(cellPhoneNumber)) {
				dq = new DBPreparedQuery("dDuplicateChecker_GetDuplicatePhoneCandidates");
				dq.setString(1, cellPhoneNumber);
				applicants = dq.getResult();
				applicants = setMatchedLabels(applicants, DuplicateSettingsConstants.LABEL_PHONE, cellPhoneNumber);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while retriving applicant with duplicate cell", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return applicants;
	}

	private ArrayList<ApplicantDuplicateSearchData> checkDuplicateForCustomFieldValues(String customFieldId, String[] vals, String displayLabel, String customFieldType) {
		ArrayList<ApplicantDuplicateSearchData> applicants = null;
		StringBuffer labelValue = new StringBuffer();
		if (vals != null) {
			for (int c = 0; c < vals.length; c++) {
				ArrayList<ApplicantDuplicateSearchData> matchedApplicants = getDuplicateCustomFieldCandidates(customFieldId, vals[c], customFieldType);
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

	private ArrayList<ApplicantDuplicateSearchData> getDuplicateCustomFieldCandidates(String customFieldId, String customFieldValue, String customFieldType) {
		DBPreparedQuery dq = null;
		ArrayList<ApplicantDuplicateSearchData> applicants = null;
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
				
				dq = new DBPreparedQuery("dDuplicateChecker_GetDuplicateCustomFieldCandidates", dynParams);
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
	 * Checks whether the resume uploaded by employee is duplicate or not.
	 * This method only says whether employee is duplicate or not, but does not give the duplicate applicants data.
	 * @param fields
	 * @return
	 */
	public boolean isDuplicateEmployeeResume(HashMap<String, String[]> fields) {
		boolean isDuplicate = false;
		ArrayList<ApplicantDuplicateSearchData> applicants = null;
		ArrayList<DuplicateSettingsData> internalSettings = DuplicateSettings.getEmployeeSettings();
		CustomFieldManager customFieldManager = new CustomFieldManager();
		try {
			for (int i = 0; i < internalSettings.size(); i++) {
				DuplicateSettingsData data = internalSettings.get(i);
				if (!Utils.isBlankOrNull(data.getFieldCheckType())) {
					if (data.getFieldId().equals(DuplicateSettingsConstants.NAME_ID) 
							&& data.getFieldType().equals(DuplicateSettingsConstants.FIELD_TYPE_SYSTEM)
							&& ImportConfigurationManager.isEmployeeFieldShow(ImportConfigurationConstants.FIELD_NAME)) {
						String[] fieldValues = fields.get(data.getFieldLabel());
						if (fieldValues != null) {
							ArrayList<ApplicantDuplicateSearchData> matchedApplicants = null;
							if (data.getFieldCheckType().equals(DuplicateSettingsConstants.MATCH_EXAXCT)) {
								matchedApplicants = splitNameAndGetExactCandidate(fieldValues[0]);
							} else {
								matchedApplicants = getApproxNames(fieldValues[0]);
							}
							if (matchedApplicants != null && matchedApplicants.size() > 0) {
								isDuplicate = true;
							}
						}

					}  else if (data.getFieldId().equals(DuplicateSettingsConstants.EMAIL_ID) 
							&& data.getFieldType().equals(DuplicateSettingsConstants.FIELD_TYPE_SYSTEM)
							&& (ImportConfigurationManager.isEmployeeFieldShow(ImportConfigurationConstants.FIELD_EMAIL1) 
									|| ImportConfigurationManager.isEmployeeFieldShow(ImportConfigurationConstants.FIELD_EMAIL2))) {
						String[] fieldValues = fields.get(data.getFieldLabel());
						if (fieldValues != null) {
							ArrayList<ApplicantDuplicateSearchData> matchedApplicants = checkIfExistsEmail(fieldValues[0], fieldValues[1]);
							//applicants = mergeApplicants(applicants, matchedApplicants);
							if (matchedApplicants != null && matchedApplicants.size() > 0) {
								isDuplicate = true;
							}
						}
					} else if (data.getFieldId().equals(DuplicateSettingsConstants.PHONE_ID) 
							&& data.getFieldType().equals(DuplicateSettingsConstants.FIELD_TYPE_SYSTEM)
							&& ImportConfigurationManager.isEmployeeFieldShow(ImportConfigurationConstants.FIELD_MOBILE)) {
						String[] fieldValues = fields.get(data.getFieldLabel());
						if (fieldValues != null) {
							ArrayList<ApplicantDuplicateSearchData> matchedApplicants = getDuplicatePhoneCandidates(fieldValues[0]);
							//applicants = mergeApplicants(applicants, matchedApplicants);
							if (matchedApplicants != null && matchedApplicants.size() > 0) {
								isDuplicate = true;
							}
						}
					} else if (data.getFieldType().equals(DuplicateSettingsConstants.FIELD_TYPE_CUSTOM)) {
						CustomFieldData customFieldData = customFieldManager.getCustomField(data.getFieldId());
						if(customFieldData != null && ImportConfigurationManager.isEmployeeFieldShow(customFieldData.getFieldName())) {
							String[] fieldValues = fields.get(data.getFieldLabel());
							if (fieldValues != null) {
								ArrayList<ApplicantDuplicateSearchData> matchedApplicants = checkDuplicateForCustomFieldValues(customFieldData.getFieldId(), fieldValues, customFieldData.getFieldDisplayName(), customFieldData.getFieldType());
								if (matchedApplicants != null && matchedApplicants.size() > 0) {
									isDuplicate = true;
								}
							}
						}
					}
				}
				if (isDuplicate) {
					break;
				}

			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in duplicate check", e);
		}
		return isDuplicate;
	}

	public ArrayList<ApplicantDuplicateSearchData> getEmployeeDuplicateChecked(String applicantId, String name, String email1, String email2, String cellPhone, ArrayList<CustomFieldData> customFields) {
		ArrayList<ApplicantDuplicateSearchData> applicants = null;
		ArrayList<DuplicateSettingsData> internalSettings = DuplicateSettings.getEmployeeSettings();
		try {
			for (int i = 0; i < internalSettings.size(); i++) {
				DuplicateSettingsData data = internalSettings.get(i);
				if (!Utils.isBlankOrNull(data.getFieldCheckType())) {
					if (data.getFieldId().equals(DuplicateSettingsConstants.NAME_ID) && data.getFieldType().equals(DuplicateSettingsConstants.FIELD_TYPE_SYSTEM)) {
						ArrayList<ApplicantDuplicateSearchData> matchedApplicants = null;
						if (data.getFieldCheckType().equals(DuplicateSettingsConstants.MATCH_EXAXCT) || !Utils.isBlankOrNull(applicantId)) {
							matchedApplicants = splitNameAndGetExactCandidate(name);
						} else {
							matchedApplicants = getApproxNames(name);
						}
						applicants = mergeApplicants(applicants, matchedApplicants);
					} else if (data.getFieldType().equals(DuplicateSettingsConstants.FIELD_TYPE_CUSTOM)) {
						// check custom fields
						for (int k = 0; customFields!=null && k < customFields.size(); k++) {
							CustomFieldData customFieldData = customFields.get(k);
							if (customFieldData.getFieldId().equals(data.getFieldId())) {
								ArrayList<ApplicantDuplicateSearchData> matchedApplicants = checkDuplicateForCustomFieldValues(customFieldData.getFieldId(), customFieldData.getFieldValues(),
										customFieldData.getFieldDisplayName(), customFieldData.getFieldType());
								applicants = mergeApplicants(applicants, matchedApplicants);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in duplicate check", e);
		}
		applicants = filterApplicant(applicants, applicantId);
		applicants = sortSearchResult(applicants);
		return applicants;
	}
	
	
}
