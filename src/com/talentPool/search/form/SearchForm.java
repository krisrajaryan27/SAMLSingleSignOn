/**
 * 
 */
package com.talentPool.search.form;

import com.talentPool.common.Pagination;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.base.TPActionForm;
import com.talentPool.common.constants.DateConstants;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.CommonUtils;
import com.talentPool.common.utils.DateUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.search.SearchConstants;
import com.talentPool.search.dataobjects.SearchCriteriaData;
import com.talentPool.search.dataobjects.SearchResultData;

/**
 * @author shivprasad
 * 
 */
public class SearchForm extends TPActionForm {
	private String searchText;
	private String matchCondition = SearchConstants.MATCH_ALL;
	private String currentLocation;
	private String minExperience;
	private String maxExperience;
	private String source;
	private String sourceTypeId;
	private String resumeTypeId;

	private String degreeId;
	private String degree;
	private String branchId;
	private String branch;
	private String flagId;
	private String flag;
	private String institute;
	private String excludeInprocess;
	private String excludeRejectedInPast;
	
	private SearchResultData searchResultData;
	private String pageNo;
	private SearchCriteriaData searchCriteriaData = null;
	private String sortBy;
	private String searchId;
	private String advancedSearch;
	private String degreeCriteria = SearchConstants.OR;
	private String resetSearch;

	private String applicantId;
	private String flagIdToSet;
	private String flagStateToSet;

	private String jsFlagsArray;
	private String rejectReason;
	
	private String importDaysFilter;
	private String importDaysFROM;
	private String importDaysTO;
	private String birthDayFilter;
	private String birthDaysFROM;
	private String birthDaysTO;
	
	// added for Asian paints
	private String tenthMarksFilter;
	private String tenthMarksFrom;
	private String tenthMarksTo;
	private String twelvethMarksFilter;
	private String twelvethMarksFrom;
	private String twelvethMarksTo;
	private String gradeMarksFilter;
	private String gradeMarksFrom;
	private String gradeMarksTo;
	private String postGradeMarksFilter;
	private String postGradeMarksFrom;
	private String postGradeMarksTo;
	private String ageFilter;
	private String minAge;
	private String maxAge;
	private String yearOfExperienceFilter;
	private String minimumExperience;
	private String maximumExperience;
	private String positionId;
	
	private String lastInteractionFilter;
	private String lastInteractionFROM;
	private String lastInteractionTO;
	
	private String jsPositionsArray;
	private String jsDepartmentArray;
	private String jsLocationArray;
	private String departmentId;
	private String locationId;
	private String searchByRequirementsString;
	
	//save Search
	private String searchName;
	private String shared;
	private String showShared;
	
	private String currentEmployer;
	private String passport;
	
	private String previousEmployer;
	private String designation;
	
	private String applicantStatus;
	
	private String deleteApplicantTokenId;
	private String phone1;
	
	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getShowShared() {
		return showShared;
	}

	public void setShowShared(String showShared) {
		this.showShared = showShared;
	}

	public SearchForm() {
	}

	public SearchCriteriaData getSearchCriteriaData() {
		if (searchCriteriaData == null) {
			constructSearchCriteriaData();
		}
		return searchCriteriaData;
	}

	public void setSearchCriteriaData(SearchCriteriaData searchCriteriaData) {
		this.searchCriteriaData = searchCriteriaData;
	}

	public void populateSearchForm(SearchCriteriaData cData) {
		setSearchText(cData.getSearchText());
		setMatchCondition(cData.getMatchCondition());
		setCurrentLocation(cData.getCurrentLocation());
		setSourceTypeId(cData.getSourceTypeId());
		setResumeTypeId(cData.getResumeTypeId());
		setSource(cData.getSource());
		setPositionId(cData.getPositionId());
		setDegreeId(cData.getDegreeId());
		setDegree(cData.getDegree());
		setBranchId(cData.getBranchId());
		setBranch(cData.getBranch());
		setExcludeInprocess(cData.getExcludeInprocess());
		setExcludeRejectedInPast(cData.getExcludeRejectedInPast());
		setInstitute(cData.getInstitute());
		setDegreeCriteria(cData.getDegreeCriteria());
		setFlagId(cData.getFlagId());
		setFlag(cData.getFlag());
		setRejectReason(cData.getRejectReason());
		setMinExperience(""+cData.getMinExperience());
		setMaxExperience(""+cData.getMaxExperience());		
		Pagination pager = cData.getPager();
		setPageNo("" + pager.getPageNo());
		setSortBy(cData.getSortBy());
		setImportDaysFilter(cData.getImportDaysFilter());
		setImportDaysFROM(cData.getImportDaysFROM());
		setImportDaysTO(cData.getImportDaysTO());
		setBirthDayFilter(cData.getBirthDayFilter());
		setBirthDaysFROM(cData.getBirthDaysFROM());
		setBirthDaysTO(cData.getBirthDaysTO());
		setLastInteractionFilter(cData.getLastInteractionFilter());
		setLastInteractionFROM(cData.getLastInteractionFROM());
		setLastInteractionTO(cData.getLastInteractionTO());
		setCurrentEmployer(cData.getCurrentEmployer());
		setPreviousEmployer(cData.getPreviousEmployer());
		setDesignation(cData.getDesignation());
		setPassport(cData.getPassport());
		//asian paints filter
		setTenthMarksFilter(cData.getTenthMarksFilter());
		setTwelvethMarksFilter(cData.getTwelvethMarksFilter());
		setGradeMarksFilter(cData.getGradeMarksFilter());
		setPostGradeMarksFilter(cData.getPostGradeMarksFilter());
		setAgeFilter(cData.getAgeFilter());
		setYearOfExperienceFilter(cData.getYearOfExperienceFilter());
		setTenthMarksFrom(cData.getTenthMarksFrom());
		setTenthMarksTo(cData.getTenthMarksTo());
		setTwelvethMarksFrom(cData.getTwelvethMarksFrom());
		setTwelvethMarksTo(cData.getTwelvethMarksTo());
		setGradeMarksFrom(cData.getGradeMarksFrom());
		setGradeMarksTo(cData.getGradeMarksTo());
		setPostGradeMarksFrom(cData.getPostGradeMarksFrom());
		setPostGradeMarksTo(cData.getPostGradeMarksTo());
		setMinAge(String.valueOf(cData.getMinAge()));
		setMaxAge(String.valueOf(cData.getMaxAge()));
		setMinimumExperience(String.valueOf(cData.getMinimumExperience()));
		setMaximumExperience(String.valueOf(cData.getMaximumExperience()));
	}

	private void constructSearchCriteriaData() {
		SearchCriteriaData cData = new SearchCriteriaData();
		TPLogger.getLogger().info("constructSearchCriteriaData");
		cData.setSearchText(getSearchText() == null ? "" : getSearchText());
		cData.setMatchCondition(getMatchCondition());
		cData.setCurrentLocation(getCurrentLocation() == null ? "" : getCurrentLocation());
		cData.setSource(getSource() == null ? "" : getSource());
		cData.setPositionId(Utils.isBlankOrNull(getPositionId())?"":getPositionId());
		cData.setSourceTypeId(getSourceTypeId() == null ? "" : getSourceTypeId());
		cData.setResumeTypeId(getResumeTypeId() == null ? "" : getResumeTypeId());
		cData.setDegreeCriteria(getDegreeCriteria() == null ? SearchConstants.OR : getDegreeCriteria());
		cData.setDegreeId(getDegreeId() == null ? "" : getDegreeId());
		cData.setDegree(getDegree() == null ? "" : getDegree());
		cData.setBranchId(getBranchId() == null ? "" : getBranchId());
		cData.setBranch(getBranch() == null ? "" : getBranch());
		if(Utils.isBlankOrNull(getExcludeInprocess())) {
			if(GlobalConstants.ENABLED.equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_EMPLOYEE_CAN_APPLY_FOR_JOB))) {
				cData.setExcludeInprocess(SearchConstants.SEARCH_ALL);
			}else if(GlobalConstants.ENABLED.equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SHOW_JOINED_CANDIDATE_IN_SEARCH))) {
				cData.setExcludeInprocess(SearchConstants.SEARCH_ALL);
			}else {
				cData.setExcludeInprocess(SearchConstants.SEARCH_EXCLUDE_EMPLOYEE);
			}
		} else {
			cData.setExcludeInprocess(getExcludeInprocess());
		}		
		cData.setExcludeRejectedInPast(getExcludeRejectedInPast() == null ? "" : getExcludeRejectedInPast());
		cData.setInstitute(getInstitute() == null ? "" : getInstitute());
		cData.setFlagId(getFlagId() == null ? "" : getFlagId());
		cData.setFlag(getFlag() == null ? "" : getFlag());
		cData.setRejectReason(getRejectReason() == null ? "" : getRejectReason());
		cData.setImportDaysFilter(getImportDaysFilter());
		cData.setImportDaysFROM(getImportDaysFROM());
		cData.setImportDaysTO(getImportDaysTO());
		cData.setImportDaysFromDate(DateUtils.convertToSqlDate(getImportDaysFROM(), DateConstants.INPUT_FORMAT));
		cData.setImportDaysToDate(DateUtils.convertToSqlDate(getImportDaysTO(),DateConstants.INPUT_FORMAT));
		cData.setBirthDayFilter(getBirthDayFilter());
		
		// asian paints filter
		TPLogger.getLogger().info("constructSearchCriteriaData for 10th mrks filter");
		cData.setTenthMarksFilter(getTenthMarksFilter());
		cData.setTwelvethMarksFilter(getTwelvethMarksFilter());
		cData.setGradeMarksFilter(getGradeMarksFilter());
		cData.setPostGradeMarksFilter(getPostGradeMarksFilter());
		cData.setAgeFilter(getAgeFilter());
		cData.setYearOfExperienceFilter(getYearOfExperienceFilter());
		
		cData.setTenthMarksFrom(getTenthMarksFrom());
		cData.setTenthMarksTo(getTenthMarksTo());
		cData.setTwelvethMarksFrom(getTwelvethMarksFrom());
		cData.setTwelvethMarksTo(getTwelvethMarksTo());
		cData.setGradeMarksFrom(getGradeMarksFrom());
		cData.setGradeMarksTo(getGradeMarksTo());
		cData.setPostGradeMarksFrom(getPostGradeMarksFrom());
		cData.setPostGradeMarksTo(getPostGradeMarksTo());
		TPLogger.getLogger().info("constructSearchCriteriaData getPostGradeMarksTo");
		try {
			cData.setMinAge(Long.parseLong(getMinAge()));
		} catch (Exception e) {
			cData.setMinAge(0);
		}
		try {
			cData.setMaxAge(Long.parseLong(getMaxAge()));
		} catch (Exception e) {
			cData.setMaxAge(0);
		}
		try {
			cData.setMinimumExperience(Long.parseLong(getMinimumExperience()));
		} catch (Exception e) {
			cData.setMinimumExperience(0);
		}
		try {
			cData.setMaximumExperience(Long.parseLong(getMaximumExperience()));
		} catch (Exception e) {
			cData.setMaximumExperience(0);
		}
		
		TPLogger.getLogger().info("constructSearchCriteriaData getPostGradeMarksTo");
		cData.setBirthDaysFROM(getBirthDaysFROM());
		cData.setBirthDaysTO(getBirthDaysTO());
		cData.setBirthDaysFromDate(DateUtils.convertToSqlDate(getBirthDaysFROM(), DateConstants.INPUT_FORMAT));
		cData.setBirthDaysToDate(DateUtils.convertToSqlDate(getBirthDaysTO(),DateConstants.INPUT_FORMAT));
		cData.setLastInteractionFilter(getLastInteractionFilter());
		cData.setLastInteractionFROM(getLastInteractionFROM());
		cData.setLastInteractionTO(getLastInteractionTO());
		cData.setLastInteractionFromDate(DateUtils.convertToSqlDate(getLastInteractionFROM(), DateConstants.INPUT_FORMAT));
		cData.setLastInteractionToDate(DateUtils.convertToSqlDate(getLastInteractionTO(), DateConstants.INPUT_FORMAT));
		cData.setCurrentEmployer(getCurrentEmployer() == null ? "" : getCurrentEmployer());		
		cData.setPassport(getPassport() == null ? "" : getPassport());
		cData.setPreviousEmployer(getPreviousEmployer() == null ? "" : getPreviousEmployer());
		cData.setDesignation(getDesignation() == null ? "" : getDesignation());
		cData.setPhone1(getPhone1() == null ? "" : getPhone1());
		try {
			cData.setMinExperience(Double.parseDouble(getMinExperience()));
		} catch (Exception e) {
			cData.setMinExperience(0);
		}
		try {
			cData.setMaxExperience(Double.parseDouble(getMaxExperience()));
		} catch (Exception e) {
			cData.setMaxExperience(0);
		}
		// set pagination data object
		Pagination pager = new Pagination();
		try {
			pager.setPageNo(Integer.parseInt(getPageNo()));
		} catch (Exception e) {
			pager.setPageNo(1);
		}
		try {
			pager.setPageSize(Integer.parseInt(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SEARCH_RESULT_PAGE_SIZE)));
		} catch (Exception e) {
			pager.setPageSize(10);
		}
		cData.setPager(pager);
		cData.setSortBy(getSortBy() == null ? SearchConstants.SORT_BY_SCORE : getSortBy());
		
		setSearchCriteriaData(cData);		
	}

	public String getSearchFilterString() {
		SearchCriteriaData cData = getSearchCriteriaData();
		String filterStr = Utils.getSearchFilterString(cData);
		return filterStr;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	/**
	 * @return Returns the searchResultData.
	 */
	public SearchResultData getSearchResultData() {
		return searchResultData;
	}

	/**
	 * @param searchResultData
	 *            The searchResultData to set.
	 */
	public void setSearchResultData(SearchResultData searchResultData) {
		this.searchResultData = searchResultData;
	}

	/**
	 * @return Returns the currentLocation.
	 */
	public String getCurrentLocation() {
		return currentLocation;
	}

	/**
	 * @param currentLocation
	 *            The currentLocation to set.
	 */
	public void setCurrentLocation(String currentLocation) {
		this.currentLocation = currentLocation;
	}

	/**
	 * @return Returns the maxExperience.
	 */
	public String getMaxExperience() {
		return maxExperience;
	}

	/**
	 * @param maxExperience
	 *            The maxExperience to set.
	 */
	public void setMaxExperience(String maxExperience) {
		this.maxExperience = maxExperience;
	}

	/**
	 * @return Returns the minExperience.
	 */
	public String getMinExperience() {
		return minExperience;
	}

	/**
	 * @param minExperience
	 *            The minExperience to set.
	 */
	public void setMinExperience(String minExperience) {
		this.minExperience = minExperience;
	}

	/**
	 * @return Returns the source.
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param source
	 *            The source to set.
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * @return Returns the branch.
	 */
	public String getBranch() {
		return branch;
	}

	/**
	 * @param branch
	 *            The branch to set.
	 */
	public void setBranch(String branch) {
		this.branch = branch;
	}

	/**
	 * @return Returns the branchId.
	 */
	public String getBranchId() {
		return branchId;
	}

	/**
	 * @param branchId
	 *            The branchId to set.
	 */
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	/**
	 * @return Returns the degree.
	 */
	public String getDegree() {
		return degree;
	}

	/**
	 * @param degree
	 *            The degree to set.
	 */
	public void setDegree(String degree) {
		this.degree = degree;
	}

	/**
	 * @return Returns the degreeId.
	 */
	public String getDegreeId() {
		return degreeId;
	}

	/**
	 * @param degreeId
	 *            The degreeId to set.
	 */
	public void setDegreeId(String degreeId) {
		this.degreeId = degreeId;
	}

	/**
	 * @return Returns the skills.
	 */
	public String getExcludeInprocess() {
		return excludeInprocess;
	}

	/**
	 * @param skills
	 *            The skills to set.
	 */
	public void setExcludeInprocess(String excludeInprocess) {
		this.excludeInprocess = excludeInprocess;
	}

	/**
	 * @return Returns the institute.
	 */
	public String getInstitute() {
		return institute;
	}

	/**
	 * @param institute
	 *            The institute to set.
	 */
	public void setInstitute(String institute) {
		this.institute = institute;
	}

	public String getJSDegreeArray() {
		return CommonUtils.getListJavaScriptArray(CommonUtils.getDegreeIds(), CommonUtils.getDegreeNames());
	}

	public String getJSBranchArray() {
		return CommonUtils.getListJavaScriptArray(CommonUtils.getBranchIds(), CommonUtils.getBranchNames());
	}

	public String getJSSourceArray() {
		return CommonUtils.getListJavaScriptArray(CommonUtils.getSourceIds(), CommonUtils.getSourceNames());
	}

	public String getJSSourceTypeArray() {
		return CommonUtils.getListJavaScriptArray(CommonUtils.getSourceTypeIds(), CommonUtils.getSourceTypeNames());
	}
	
	
	public String getJSResumeTypeArray() {
		return CommonUtils.getListJavaScriptArray(CommonUtils.getResumeTypeIds(), CommonUtils.getResumeTypeNames());
	}

	/**
	 * @return Returns the pageNo.
	 */
	public String getPageNo() {
		return pageNo;
	}

	/**
	 * @param pageNo
	 *            The pageNo to set.
	 */
	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	/**
	 * @return Returns the sortBy.
	 */
	public String getSortBy() {
		return sortBy;
	}

	/**
	 * @param sortBy
	 *            The sortBy to set.
	 */
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	/**
	 * @return Returns the searchId.
	 */
	public String getSearchId() {
		return searchId;
	}

	/**
	 * @param searchId
	 *            The searchId to set.
	 */
	public void setSearchId(String searchId) {
		this.searchId = searchId;
	}

	public String getAdvancedSearch() {
		return advancedSearch;
	}

	public void setAdvancedSearch(String advancedSearch) {
		this.advancedSearch = advancedSearch;
	}

	/**
	 * @return the degreeCriteria
	 */
	public String getDegreeCriteria() {
		return degreeCriteria;
	}

	/**
	 * @param degreeCriteria
	 *            the degreeCriteria to set
	 */
	public void setDegreeCriteria(String degreeCriteria) {
		this.degreeCriteria = degreeCriteria;
	}

	/**
	 * @return the resetSearch
	 */
	public String getResetSearch() {
		return resetSearch;
	}

	/**
	 * @param resetSearch
	 *            the resetSearch to set
	 */
	public void setResetSearch(String resetSearch) {
		this.resetSearch = resetSearch;
	}

	/**
	 * @return the flagId
	 */
	public String getFlagId() {
		return flagId;
	}

	/**
	 * @param flagId
	 *            the flagId to set
	 */
	public void setFlagId(String flagId) {
		this.flagId = flagId;
	}

	/**
	 * @return the flag
	 */
	public String getFlag() {
		return flag;
	}

	/**
	 * @param flag
	 *            the flag to set
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * @return the applicantId
	 */
	public String getApplicantId() {
		return applicantId;
	}

	/**
	 * @param applicantId
	 *            the applicantId to set
	 */
	public void setApplicantId(String applicantId) {
		this.applicantId = applicantId;
	}

	/**
	 * @return the flagIdToSet
	 */
	public String getFlagIdToSet() {
		return flagIdToSet;
	}

	/**
	 * @param flagIdToSet
	 *            the flagIdToSet to set
	 */
	public void setFlagIdToSet(String flagIdToSet) {
		this.flagIdToSet = flagIdToSet;
	}

	/**
	 * @return the flagStateToSet
	 */
	public String getFlagStateToSet() {
		return flagStateToSet;
	}

	/**
	 * @param flagStateToSet
	 *            the flagStateToSet to set
	 */
	public void setFlagStateToSet(String flagStateToSet) {
		this.flagStateToSet = flagStateToSet;
	}

	public String getMatchCondition() {
		return matchCondition;
	}

	public void setMatchCondition(String matchCondition) {
		this.matchCondition = matchCondition;
	}

	/**
	 * @return the jsFlagsArray
	 */
	public String getJsFlagsArray() {
		return jsFlagsArray;
	}

	/**
	 * @param jsFlagsArray
	 *            the jsFlagsArray to set
	 */
	public void setJsFlagsArray(String jsFlagsArray) {
		this.jsFlagsArray = jsFlagsArray;
	}

	/**
	 * @return the sourceTypeId
	 */
	public String getSourceTypeId() {
		return sourceTypeId;
	}

	/**
	 * @param sourceTypeId
	 *            the sourceTypeId to set
	 */
	public void setSourceTypeId(String sourceTypeId) {
		this.sourceTypeId = sourceTypeId;
	}

	/**
	 * @return the resumeTypeId
	 */
	public String getResumeTypeId() {
		return resumeTypeId;
	}

	/**
	 * @param resumeTypeId
	 *            the resumeTypeId to set
	 */
	public void setResumeTypeId(String resumeTypeId) {
		this.resumeTypeId = resumeTypeId;
	}

	/**
	 * @return the rejectReason
	 */
	public String getRejectReason() {
		return rejectReason;
	}

	/**
	 * @param rejectReason
	 *            the rejectReason to set
	 */
	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	/**
	 * @return the excludeRejectedInPast
	 */
	public String getExcludeRejectedInPast() {
		return excludeRejectedInPast;
	}

	/**
	 * @param excludeRejectedInPast the excludeRejectedInPast to set
	 */
	public void setExcludeRejectedInPast(String excludeRejectedInPast) {
		this.excludeRejectedInPast = excludeRejectedInPast;
	}

	/**
	 * @return the importDaysFilter
	 */
	public String getImportDaysFilter() {
		return importDaysFilter;
	}

	/**
	 * @param importDaysFilter the importDaysFilter to set
	 */
	public void setImportDaysFilter(String importDaysFilter) {
		this.importDaysFilter = importDaysFilter;
	}

	/**
	 * @return the importDaysFROM
	 */
	public String getImportDaysFROM() {
		return importDaysFROM;
	}

	/**
	 * @param importDaysFROM the importDaysFROM to set
	 */
	public void setImportDaysFROM(String importDaysFROM) {
		this.importDaysFROM = importDaysFROM;
	}

	/**
	 * @return the importDaysTO
	 */
	public String getImportDaysTO() {
		return importDaysTO;
	}

	/**
	 * @param importDaysTO the importDaysTO to set
	 */
	public void setImportDaysTO(String importDaysTO) {
		this.importDaysTO = importDaysTO;
	}

	/**
	 * @return the birthDayFilter
	 */
	public String getBirthDayFilter() {
		return birthDayFilter;
	}

	/**
	 * @param birthDayFilter to set the birthDayFilter 
	 */
	public void setBirthDayFilter(String birthDayFilter) {
		this.birthDayFilter = birthDayFilter;
	}

	/**
	 * @return the birthDaysFROM
	 */
	public String getBirthDaysFROM() {
		return birthDaysFROM;
	}

	/**
	 * @param birthDaysFROM the birthDaysFROM to set
	 */
	public void setBirthDaysFROM(String birthDaysFROM) {
		this.birthDaysFROM = birthDaysFROM;
	}

	/**
	 * @return the birthDaysTO
	 */
	public String getBirthDaysTO() {
		return birthDaysTO;
	}

	/**
	 * @param birthDaysTO the birthDaysTO to set
	 */
	public void setBirthDaysTO(String birthDaysTO) {
		this.birthDaysTO = birthDaysTO;
	}

	/**
	 * @return the lastInteractionFilter
	 */
	public String getLastInteractionFilter() {
		return lastInteractionFilter;
	}

	/**
	 * @param lastInteractionFilter the lastInteractionFilter to set
	 */
	public void setLastInteractionFilter(String lastInteractionFilter) {
		this.lastInteractionFilter = lastInteractionFilter;
	}

	/**
	 * @return the lastInteractionFROM
	 */
	public String getLastInteractionFROM() {
		return lastInteractionFROM;
	}

	/**
	 * @param lastInteractionFROM the lastInteractionFROM to set
	 */
	public void setLastInteractionFROM(String lastInteractionFROM) {
		this.lastInteractionFROM = lastInteractionFROM;
	}

	/**
	 * @return the lastInteractionTO
	 */
	public String getLastInteractionTO() {
		return lastInteractionTO;
	}

	/**
	 * @param lastInteractionTO the lastInteractionTO to set
	 */
	public void setLastInteractionTO(String lastInteractionTO) {
		this.lastInteractionTO = lastInteractionTO;
	}

	/**
	 * @return the jsPositionsArray
	 */
	public String getJsPositionsArray() {
		return jsPositionsArray;
	}

	/**
	 * @param jsPositionsArray the jsPositionsArray to set
	 */
	public void setJsPositionsArray(String jsPositionsArray) {
		this.jsPositionsArray = jsPositionsArray;
	}

	/**
	 * @return the positionId
	 */
	public String getPositionId() {
		return positionId;
	}

	/**
	 * @param positionId the positionId to set
	 */
	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	/**
	 * @return the jsDepartmentArray
	 */
	public String getJsDepartmentArray() {
		return jsDepartmentArray;
	}

	/**
	 * @param jsDepartmentArray the jsDepartmentArray to set
	 */
	public void setJsDepartmentArray(String jsDepartmentArray) {
		this.jsDepartmentArray = jsDepartmentArray;
	}

	/**
	 * @return the jsLocationArray
	 */
	public String getJsLocationArray() {
		return jsLocationArray;
	}

	/**
	 * @param jsLocationArray the jsLocationArray to set
	 */
	public void setJsLocationArray(String jsLocationArray) {
		this.jsLocationArray = jsLocationArray;
	}

	/**
	 * @return the departmentId
	 */
	public String getDepartmentId() {
		return departmentId;
	}

	/**
	 * @param departmentId the departmentId to set
	 */
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	/**
	 * @return the locationId
	 */
	public String getLocationId() {
		return locationId;
	}

	/**
	 * @param locationId the locationId to set
	 */
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	/**
	 * @return the searchByRequirementsString
	 */
	public String getSearchByRequirementsString() {
		return searchByRequirementsString;
	}

	/**
	 * @param searchByRequirementsString the searchByRequirementsString to set
	 */
	public void setSearchByRequirementsString(String searchByRequirementsString) {
		this.searchByRequirementsString = searchByRequirementsString;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public String getShared() {
		return shared;
	}

	public void setShared(String shared) {
		this.shared = shared;
	}

	public String getCurrentEmployer() {
		return currentEmployer;
	}

	public void setCurrentEmployer(String currentEmployer) {
		this.currentEmployer = currentEmployer;
	}

	public String getPassport() {
		return passport;
	}

	public void setPassport(String passport) {
		this.passport = passport;
	}

	/**
	 * @return the applicantStatus
	 */
	public String getApplicantStatus() {
		return applicantStatus;
	}

	/**
	 * @param applicantStatus the applicantStatus to set
	 */
	public void setApplicantStatus(String applicantStatus) {
		this.applicantStatus = applicantStatus;
	}

	/**
	 * @return the previousEmployer
	 */
	public String getPreviousEmployer() {
		return previousEmployer;
	}

	/**
	 * @param previousEmployer the previousEmployer to set
	 */
	public void setPreviousEmployer(String previousEmployer) {
		this.previousEmployer = previousEmployer;
	}

	/**
	 * @return the designation
	 */
	public String getDesignation() {
		return designation;
	}

	/**
	 * @param designation the designation to set
	 */
	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getDeleteApplicantTokenId() {
		return deleteApplicantTokenId;
	}

	public void setDeleteApplicantTokenId(String deleteApplicantTokenId) {
		this.deleteApplicantTokenId = deleteApplicantTokenId;
	}

	public String getTenthMarksFilter() {
		return tenthMarksFilter;
	}

	public void setTenthMarksFilter(String tenthMarksFilter) {
		this.tenthMarksFilter = tenthMarksFilter;
	}

	public String getTwelvethMarksFilter() {
		return twelvethMarksFilter;
	}

	public void setTwelvethMarksFilter(String twelvethMarksFilter) {
		this.twelvethMarksFilter = twelvethMarksFilter;
	}

	public String getGradeMarksFilter() {
		return gradeMarksFilter;
	}

	public void setGradeMarksFilter(String gradeMarksFilter) {
		this.gradeMarksFilter = gradeMarksFilter;
	}

	public String getPostGradeMarksFilter() {
		return postGradeMarksFilter;
	}

	public void setPostGradeMarksFilter(String postGradeMarksFilter) {
		this.postGradeMarksFilter = postGradeMarksFilter;
	}

	public String getAgeFilter() {
		return ageFilter;
	}

	public void setAgeFilter(String ageFilter) {
		this.ageFilter = ageFilter;
	}

	public String getYearOfExperienceFilter() {
		return yearOfExperienceFilter;
	}

	public void setYearOfExperienceFilter(String yearOfExperienceFilter) {
		this.yearOfExperienceFilter = yearOfExperienceFilter;
	}

	public String getTenthMarksFrom() {
		return tenthMarksFrom;
	}

	public void setTenthMarksFrom(String tenthMarksFrom) {
		this.tenthMarksFrom = tenthMarksFrom;
	}

	public String getTenthMarksTo() {
		return tenthMarksTo;
	}

	public void setTenthMarksTo(String tenthMarksTo) {
		this.tenthMarksTo = tenthMarksTo;
	}

	public String getTwelvethMarksFrom() {
		return twelvethMarksFrom;
	}

	public void setTwelvethMarksFrom(String twelvethMarksFrom) {
		this.twelvethMarksFrom = twelvethMarksFrom;
	}

	public String getTwelvethMarksTo() {
		return twelvethMarksTo;
	}

	public void setTwelvethMarksTo(String twelvethMarksTo) {
		this.twelvethMarksTo = twelvethMarksTo;
	}

	public String getGradeMarksFrom() {
		return gradeMarksFrom;
	}

	public void setGradeMarksFrom(String gradeMarksFrom) {
		this.gradeMarksFrom = gradeMarksFrom;
	}

	public String getGradeMarksTo() {
		return gradeMarksTo;
	}

	public void setGradeMarksTo(String gradeMarksTo) {
		this.gradeMarksTo = gradeMarksTo;
	}

	public String getPostGradeMarksFrom() {
		return postGradeMarksFrom;
	}

	public void setPostGradeMarksFrom(String postGradeMarksFrom) {
		this.postGradeMarksFrom = postGradeMarksFrom;
	}

	public String getPostGradeMarksTo() {
		return postGradeMarksTo;
	}

	public void setPostGradeMarksTo(String postGradeMarksTo) {
		this.postGradeMarksTo = postGradeMarksTo;
	}

	public String getMinAge() {
		return minAge;
	}

	public void setMinAge(String minAge) {
		this.minAge = minAge;
	}

	public String getMaxAge() {
		return maxAge;
	}

	public void setMaxAge(String maxAge) {
		this.maxAge = maxAge;
	}

	public String getMinimumExperience() {
		return minimumExperience;
	}

	public void setMinimumExperience(String minimumExperience) {
		this.minimumExperience = minimumExperience;
	}

	public String getMaximumExperience() {
		return maximumExperience;
	}

	public void setMaximumExperience(String maximumExperience) {
		this.maximumExperience = maximumExperience;
	}

			
}
