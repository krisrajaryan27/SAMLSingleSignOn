package com.talentPool.search;

import java.util.ArrayList;
import java.util.BitSet;

import junit.framework.TestCase;

import com.talentPool.common.Pagination;
import com.talentPool.repository.IndexManager;
import com.talentPool.repository.TPDocRepository;
import com.talentPool.repository.TPDocument;
import com.talentPool.repository.TPIndexEvent;
import com.talentPool.search.dataobjects.SearchCriteriaData;
import com.talentPool.search.dataobjects.SearchResultData;
import com.talentPool.search.manager.SearchManager;
import com.talentPool.user.manager.LoginManager;
import com.talentPool.user.manager.PermissionSet;

public class TestSearchManager extends TestCase {

	PermissionSet permissionSet = null;
	String userId = "1";
	
	public void setUp() {
		TPDocRepository.createNewIndex();
		IndexManager.processIndexEvent(new TPIndexEvent(TPIndexEvent.TYPE_ALL, null, TPIndexEvent.PRIORITY_NORMAL));
		LoginManager loginManager = new LoginManager();
		BitSet permissions = loginManager.getUserPermissionsBitSet(userId);
		permissionSet = new PermissionSet(permissions);
	}
	
	private SearchCriteriaData populateSearchCriteria(String searchText) {
		SearchCriteriaData cData = new SearchCriteriaData();
		
		int pageNo = 1;
		int pageSize = 20;
		String matchCondition = SearchConstants.MATCH_ANY;
		String excludeInprocess = SearchConstants.SEARCH_EXCLUDE_EMPLOYEE;
		String sortBy = SearchConstants.SORT_BY_SCORE;

		Pagination pager = new Pagination();
		pager.setPageNo(pageNo);
		pager.setPageSize(pageSize);
		cData.setSearchText(searchText);
		cData.setPager(pager);
		cData.setMatchCondition(matchCondition);
		cData.setExcludeInprocess(excludeInprocess);
		cData.setSortBy(sortBy);
		cData.setFlagId("");
		cData.setFlag("");
		cData.setRejectReason("");
		cData.setCurrentLocation("");
		cData.setSource("");
		return cData;
	}

	
	public void testSearchTextWithOneWord() {
		SearchCriteriaData cData = populateSearchCriteria("java");
		SearchManager searchManager = new SearchManager();
		SearchResultData searchResultData = searchManager.search(cData,permissionSet);
		long recordCount = searchResultData.getPager().getRecordCount();
		if(recordCount == 0) {
			fail("No search data found!");
		} else {
			listCandidates(searchResultData);
		}
	}
	
	public void testSearchTextWithWildcard() {
		SearchCriteriaData cData = populateSearchCriteria("jav*");
		SearchManager searchManager = new SearchManager();
		SearchResultData searchResultData = searchManager.search(cData,permissionSet);
		long recordCount = searchResultData.getPager().getRecordCount();
		if(recordCount == 0) {
			fail("No search data found!");
		} else {
			listCandidates(searchResultData);
		}
	}

	private void listCandidates(SearchResultData searchResultData) {
		ArrayList<TPDocument> data =  searchResultData.getRecords();
		for(TPDocument doc: data) {
			System.out.print(doc.getName() + "::" + doc.getImportDate() + "::" + doc.getLastInteractionDate());
			System.out.print("||");
		}
		System.out.println();
	}
	
	public void testSearchTextWithSpecialCharacters() {
		SearchCriteriaData cData = populateSearchCriteria("C++");
		SearchManager searchManager = new SearchManager();
		SearchResultData searchResultData = searchManager.search(cData,permissionSet);
		long recordCount = searchResultData.getPager().getRecordCount();
		if(recordCount == 0) {
			fail("No search data found!");
		} else {
			listCandidates(searchResultData);
		}
	}
	
	public void testSearchTextWithImportDate() {
		SearchCriteriaData cData = populateSearchCriteria("");
		String importDaysFROM = "12/07/2010";
		String importDaysTO = "16/07/2010";
		cData.setImportDaysFROM(importDaysFROM);
		cData.setImportDaysTO(importDaysTO);
		cData.setImportDaysFilter(SearchConstants.CRITERIA_BETWEEN);
		SearchManager searchManager = new SearchManager();
		SearchResultData searchResultData = searchManager.search(cData,permissionSet);
		long recordCount = searchResultData.getPager().getRecordCount();
		if(recordCount == 0) {
			fail("No search data found!");
		} else {
			listCandidates(searchResultData);
		}
	}
	
	public void testSearchTextWithLastInteractiveDate() {
		SearchCriteriaData cData = populateSearchCriteria("");
		String lastInteractionFROM = "12/07/2010";
		String lastInteractionTO = "16/07/2010";
		cData.setLastInteractionFROM(lastInteractionFROM);
		cData.setLastInteractionTO(lastInteractionTO);
		cData.setLastInteractionFilter(SearchConstants.CRITERIA_BETWEEN);
		SearchManager searchManager = new SearchManager();
		SearchResultData searchResultData = searchManager.search(cData,permissionSet);
		long recordCount = searchResultData.getPager().getRecordCount();
		if(recordCount == 0) {
			fail("No search data found!");
		} else {
			listCandidates(searchResultData);
		}
	}

}

