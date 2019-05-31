/**
 * 
 */
package com.talentPool.repository;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Explanation;
import org.apache.lucene.search.FilteredQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.QueryWrapperFilter;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.util.BytesRef;

import com.talentPool.applicant.ApplicantConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.constants.DateConstants;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.custom.constants.CustomFieldConstants;
import com.talentPool.custom.dataobject.CustomFieldData;
import com.talentPool.positions.PositionConstants;
import com.talentPool.search.SearchConstants;

/**
 * @author shivprasad
 * 
 */
public class TpDocRepositorySearcher extends TPDocRepository {

//	private Analyzer analyzer = new LimitTokenCountAnalyzer(new StandardAnalyzer(),Integer.MAX_VALUE);
	private Analyzer analyzer =  TpCustomAnalyzer.getAnalyzer();
	private Query query;
	private Query keywordQuery;

	public Explanation getExplaination(int id) throws IOException {
		Explanation explanation = getIndexSearcher().explain(query, id);
		return explanation;
	}

	/**
	 * @return Returns the keywordQuery.
	 */
	public Query getKeywordQuery() {
		return keywordQuery;
	}

	/**
	 * @return Returns the query.
	 */
	public Query getQuery() {
		return query;
	}

	/**
	 * @param query
	 *            The query to set.
	 */
	public void setQuery(Query query) {
		this.query = query;
	}

	private void setMainQueryKeywordSearch(String miscSearch, String matchCondition, BooleanQuery bQuery, QueryParser parser) throws ParseException {
		StringBuffer sb = null;
		// misc query to be executed on content and keyword
		if (!Utils.isBlankOrNull(miscSearch)) {
			if (matchCondition.equals(SearchConstants.MATCH_ALL)) {
				ArrayList<String> genericTerms = getTokens(miscSearch);
				for (int i = 0; i < genericTerms.size(); i++) {
					sb = new StringBuffer();
					sb.append(TPDocument.KEYWORDS_FIELD + ":" + genericTerms.get(i) + " ");
					sb.append(TPDocument.CONTENTS_FIELD + ":" + genericTerms.get(i) + " ");
					Query thisQry = parser.parse(sb.toString());
					BooleanClause thisClause = new BooleanClause(thisQry, BooleanClause.Occur.MUST);
					bQuery.add(thisClause);
				}
			} else if (matchCondition.equals(SearchConstants.MATCH_ANY)) {
				ArrayList<String> genericTerms = getTokens(miscSearch);
				sb = new StringBuffer();
				for (int i = 0; i < genericTerms.size(); i++) {
					sb.append(TPDocument.KEYWORDS_FIELD + ":" + genericTerms.get(i) + " ");
					sb.append(TPDocument.CONTENTS_FIELD + ":" + genericTerms.get(i) + " ");
				}
				if (genericTerms.size() > 0) {
					Query thisQry = parser.parse(sb.toString());
					BooleanClause thisClause = new BooleanClause(thisQry, BooleanClause.Occur.MUST);
					bQuery.add(thisClause);
				}
			} else if (matchCondition.equals(SearchConstants.MATCH_EXACT)) {
				sb = new StringBuffer();
				sb.append(TPDocument.KEYWORDS_FIELD + ":\"" + miscSearch + "\" ");
				sb.append(TPDocument.CONTENTS_FIELD + ":\"" + miscSearch + "\" ");
				Query thisQry = parser.parse(sb.toString());
				BooleanClause thisClause = new BooleanClause(thisQry, BooleanClause.Occur.MUST);
				bQuery.add(thisClause);
			} else if (matchCondition.equals(SearchConstants.MATCH_ID)) {
				ArrayList<String> genericTerms = getTokens(miscSearch);
				sb = new StringBuffer();
				for (int i = 0; i < genericTerms.size(); i++) {
					sb.append(TPDocument.ID_FIELD + ":" + genericTerms.get(i) + " ");
				}
				if (genericTerms.size() > 0) {
					Query thisQry = parser.parse(sb.toString());
					BooleanClause thisClause = new BooleanClause(thisQry, BooleanClause.Occur.MUST);
					bQuery.add(thisClause);
				}
			} else if (matchCondition.equals(SearchConstants.MATCH_EMP_ID)) {
				ArrayList<String> genericTerms = getTokens(miscSearch);
				sb = new StringBuffer();
				for (int i = 0; i < genericTerms.size(); i++) {
					sb.append(TPDocument.EMP_ID_FIELD + ":" + genericTerms.get(i) + " ");
				}
				if (genericTerms.size() > 0) {
					Query thisQry = parser.parse(sb.toString());
					BooleanClause thisClause = new BooleanClause(thisQry, BooleanClause.Occur.MUST);
					bQuery.add(thisClause);
				}
			}
		}

	}

	private void applyExperienceFilter(double minExp, double maxExp, BooleanQuery filter) {
		if (minExp > 0 || maxExp > 0) {
			Date maxDate = null;
			Date minDate = null;
			if (minExp > 0) {
				Calendar rightNow = Calendar.getInstance();
				int days = (int) (minExp * 365);
				rightNow.add(Calendar.DATE, (0 - days));
				minDate = rightNow.getTime();
			}
			if (maxExp > 0) {
				Calendar rightNow = Calendar.getInstance();
				int days = (int) (maxExp * 365);
				rightNow.add(Calendar.DATE, (0 - days));
				maxDate = rightNow.getTime();
			}
			BytesRef maxWorkingSince = null;
			BytesRef minWorkingSince = null;
			if (maxDate != null) {
				maxWorkingSince = new BytesRef(TPDateTools.dateToString(maxDate, TPDateTools.Resolution.MONTH).getBytes());
			}
			if (minDate != null) {
				minWorkingSince =new BytesRef(TPDateTools.dateToString(minDate, TPDateTools.Resolution.MONTH).getBytes());;
			}
			Query expQry = new TermRangeQuery(TPDocument.EXP_FIELD, maxWorkingSince,minWorkingSince, true,true);
			BooleanClause expClause = new BooleanClause(expQry, BooleanClause.Occur.MUST);
			filter.add(expClause);
		}

	}
	
	private void applyAgeFilter(double minAge, double maxAge, BooleanQuery filter,String rangeCriteria) {
	
		
		if (!Utils.isBlankOrNull(rangeCriteria)) {
			
			if (minAge > 0 || maxAge > 0) {
				Date maxDate = null;
				Date minDate = null;
				if (minAge > 0) {
					Calendar rightNow = Calendar.getInstance();
					int days = (int) (minAge * 365);
					rightNow.add(Calendar.DATE, (0 - days));
					minDate = rightNow.getTime();
				}
				if (maxAge > 0) {
					Calendar rightNow = Calendar.getInstance();
					int days = (int) (maxAge * 365);
					rightNow.add(Calendar.DATE, (0 - days));
					maxDate = rightNow.getTime();
				}
				long minD = minDate!=null?getTimestamp(Utils.getDateConvertedToString(minDate,  Utils.regEUDateFormat), Utils.regEUDateFormat):0;
				long maxD =maxDate!=null? getTimestamp(Utils.getDateConvertedToString(maxDate,  Utils.regEUDateFormat), Utils.regEUDateFormat):0;
				TPLogger.getLogger().info("applyAgeFilter minDate"+minD);
				TPLogger.getLogger().info("applyAgeFilter maxDate"+maxD);
				applyDateAgeFilter(TPDocument.BIRTH_DATE_FIELD, rangeCriteria, minD, maxD, filter);
			}
		}

	}
	
	
	private void applyLocationFilter(String location, BooleanQuery filter, QueryParser parser) throws ParseException {
		ArrayList<String> locations = getTokens(location);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < locations.size(); i++) {
			sb.append(TPDocument.LOCATION_FIELD + ":" + locations.get(i) + " ");
		}
		if (locations.size() > 0) {
			Query thisQry = parser.parse(sb.toString());
			BooleanClause thisClause = new BooleanClause(thisQry, BooleanClause.Occur.MUST);
			filter.add(thisClause);
		}

	}

	private void applySourceFilter(String source, BooleanQuery filter) {
		ArrayList<String> sources = getTokens(source);
		for (int i = 0; i < sources.size(); i++) {
			if (!Utils.isBlankOrNull(sources.get(i))) {
				PhraseQuery psQuery = new PhraseQuery();
				psQuery.add(new Term(TPDocument.SOURCE_FIELD, sources.get(i).toLowerCase()));
				BooleanClause srcClause = new BooleanClause(psQuery, BooleanClause.Occur.MUST);
				filter.add(srcClause);
			}
		}
	}
	
	/*private void applyPositionAppliedFilter(String positionName, BooleanQuery filter, QueryParser parser) throws ParseException {
		ArrayList<String> positions = getTokens(positionName);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < positions.size(); i++) {
			sb.append(TPDocument.REQUISITION_APPLIED_FIELD_ID + ":" + positions.get(i) + " ");
			Query thisQry = parser.parse(sb.toString());
			BooleanClause thisClause = new BooleanClause(thisQry, BooleanClause.Occur.MUST);
			filter.add(thisClause);
		}
	}*/
	
	private void applyPositionAppliedFilter(String positionId, BooleanQuery filter) {
		if (!Utils.isBlankOrNull(positionId)) {
			PhraseQuery psQuery = new PhraseQuery();
			psQuery.add(new Term(TPDocument.REQUISITION_APPLIED_FIELD_ID, positionId));
			BooleanClause srcClause = new BooleanClause(psQuery, BooleanClause.Occur.MUST);
			filter.add(srcClause);
		}
	}
	
	private void applyCurrentEmployerFilterq(String currentEmployer, BooleanQuery filter, QueryParser parser) throws ParseException {
		ArrayList<String> currentEmployers = getTokens(currentEmployer);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < currentEmployers.size(); i++) {
			sb.append(TPDocument.LASTEMP_FIELD + ":" + currentEmployers.get(i) + " ");
			Query thisQry = parser.parse(sb.toString());
			BooleanClause thisClause = new BooleanClause(thisQry, BooleanClause.Occur.MUST);
			filter.add(thisClause);
		}		
	}
	
	private void applySourceTypeFilter(String sourceTypeId, BooleanQuery filter) {
		if (!Utils.isBlankOrNull(sourceTypeId)) {
			PhraseQuery psQuery = new PhraseQuery();
			psQuery.add(new Term(TPDocument.SOURCE_TYPE_ID_FIELD, sourceTypeId));
			BooleanClause srcClause = new BooleanClause(psQuery, BooleanClause.Occur.MUST);
			filter.add(srcClause);
		}
	}

	private void applyResumeTypeFilter(String resumeTypeId, BooleanQuery filter) {
		if (!Utils.isBlankOrNull(resumeTypeId)) {
			PhraseQuery psQuery = new PhraseQuery();
			psQuery.add(new Term(TPDocument.RESUME_TYPE_ID_FIELD, resumeTypeId));
			BooleanClause srcClause = new BooleanClause(psQuery, BooleanClause.Occur.MUST);
			filter.add(srcClause);
		}
	}

	private void applyDegreeFilter(ArrayList<String> degrees, String degreeCriteria, BooleanQuery filter) {
		ArrayList<TermQuery> orQrys = new ArrayList<TermQuery>();
		for (int d = 0; d < degrees.size(); d++) {
			String dr = degrees.get(d);
			if (!Utils.isBlankOrNull(dr)) {
				TermQuery tQuery = new TermQuery(new Term(TPDocument.DEGREE_FIELD, dr));
				if (degreeCriteria.equals(SearchConstants.AND)) {
					BooleanClause thisClause = new BooleanClause(tQuery, BooleanClause.Occur.MUST);
					filter.add(thisClause);
				} else {
					orQrys.add(tQuery);
				}
			}
		}
		
		if (orQrys.size() > 0) {
			BooleanQuery combineORQuery = new BooleanQuery();
			for (int i = 0; i < orQrys.size(); i++) {
				combineORQuery.add(orQrys.get(i), BooleanClause.Occur.SHOULD);
			}
			BooleanClause thisClause = new BooleanClause(combineORQuery, BooleanClause.Occur.MUST);
			filter.add(thisClause);
		}

	}

	private void applyMajorFilter(ArrayList<String> majors, BooleanQuery filter) {
		ArrayList<TermQuery> orQrys = new ArrayList<TermQuery>();
		for (int b = 0; b < majors.size(); b++) {
			String br = majors.get(b);
			if (!Utils.isBlankOrNull(br)) {
				TermQuery tQuery = new TermQuery(new Term(TPDocument.MAJOR_FIELD, br));
				orQrys.add(tQuery);
			}
		}
		if (orQrys.size() > 0) {
			BooleanQuery combineORQuery = new BooleanQuery();
			for (int i = 0; i < orQrys.size(); i++) {
				combineORQuery.add(orQrys.get(i), BooleanClause.Occur.SHOULD);
			}
			BooleanClause thisClause = new BooleanClause(combineORQuery, BooleanClause.Occur.MUST);
			filter.add(thisClause);
		}
	}

	private void applyCustomFieldsFilter(ArrayList<CustomFieldData> customFields, BooleanQuery filter) {
		if (customFields != null) {
			for (int i = 0; i < customFields.size(); i++) {
				CustomFieldData cData = customFields.get(i);
				String[] vals = cData.getFieldValues();
				if (vals != null) {
					if (cData.getFieldType().equals(CustomFieldConstants.TYPE_NUMBER) || cData.getFieldType().equals(CustomFieldConstants.TYPE_DATE)) {
						if (cData.getFieldType().equals(CustomFieldConstants.TYPE_NUMBER)) {
							applyNumberRangeFilter(cData.getFieldName(), cData.getRangeCriteria(), vals[0], cData.getToValues()[0], filter);
						} else {
							long minD = getTimestamp(vals[0], cData.getOtherAttribute(CustomFieldConstants.ATTRIBUTE_DATE_FORMAT));
							long maxD = getTimestamp(cData.getToValues()[0], cData.getOtherAttribute(CustomFieldConstants.ATTRIBUTE_DATE_FORMAT));
							applyDateRangeFilter(cData.getFieldName(), cData.getRangeCriteria(), minD, maxD, filter);
						}
					} else {
						// this is terms query
						for (int k = 0; vals != null && k < vals.length; k++) {
							if (!Utils.isBlankOrNull(vals[k])) {
								String trm = vals[k].trim().toLowerCase();
								if (trm.equals(SearchConstants.CRITERIA_NOT_SPECIFIED)) {
									trm = RepositoryConstants.BLANK_STRING;
								}
								TermQuery tQuery = new TermQuery(new Term(cData.getFieldName(), trm));
								BooleanClause thisClause = new BooleanClause(tQuery, BooleanClause.Occur.MUST);
								filter.add(thisClause);
							}
						}
					}
				}
			}
		}
	}

	/*private void applyMultipleIdsFilter(String field, String[] fieldIds, BooleanQuery filter) {
		for (int f = 0; f < fieldIds.length; f++) {
			String fId = fieldIds[f];
			if (!Utils.isBlankOrNull(fId)) {
				TermQuery tQuery = new TermQuery(new Term(field, fId));
				BooleanClause thisClause = new BooleanClause(tQuery, BooleanClause.Occur.SHOULD);
				filter.add(thisClause);
			}
		}
	}*/
	
	private void applyMultipleIdsFilter( String field, String[] fieldIds,BooleanQuery filter) {
		//ArrayList<String> majors=(ArrayList<String>) Arrays.asList(fieldIds);
		ArrayList<String> fieldIdList = new ArrayList<String>();
		for(int i=0;i<fieldIds.length;i++){
			fieldIdList.add(fieldIds[i]);
		}
		
	
		ArrayList<TermQuery> orQrys = new ArrayList<TermQuery>();
		for (int b = 0; b < fieldIdList.size(); b++) {
			String br = fieldIdList.get(b);
			if (!Utils.isBlankOrNull(br)) {
				TermQuery tQuery = new TermQuery(new Term(TPDocument.FLAG_ID, br));
				orQrys.add(tQuery);
			}
		}
		if (orQrys.size() > 0) {
			BooleanQuery combineORQuery = new BooleanQuery();
			for (int i = 0; i < orQrys.size(); i++) {
				combineORQuery.add(orQrys.get(i), BooleanClause.Occur.SHOULD);
			}
			BooleanClause thisClause = new BooleanClause(combineORQuery, BooleanClause.Occur.MUST);
			filter.add(thisClause);
		}
	}
	
	private void applyRejectIdsFilter(String[] fieldIds, BooleanQuery filter) {
		ArrayList<TermQuery> orQrys = new ArrayList<TermQuery>();
		for (int f = 0; f < fieldIds.length; f++) {
			String fId = fieldIds[f];
			if (!Utils.isBlankOrNull(fId)) {
				TermQuery tQuery = null;
				if (fId.equals(SearchConstants.REJECTED_IN_SHORTLIST)) {
					tQuery = new TermQuery(new Term(TPDocument.REJECT_LEVEL_ID, PositionConstants.STEP_LEVEL_SHORTLIST));
				} else if (fId.equals(SearchConstants.REJECTED_IN_SELECTION)) {
					tQuery = new TermQuery(new Term(TPDocument.REJECT_LEVEL_ID, PositionConstants.STEP_LEVEL_SELECT));
				} else if (fId.equals(SearchConstants.REJECTED_IN_HIRE)) {
					tQuery = new TermQuery(new Term(TPDocument.REJECT_LEVEL_ID, PositionConstants.STEP_LEVEL_ACCEPT));
				} else if (fId.equals(SearchConstants.REJECTED_POSITION_CLOSED)) {
					tQuery = new TermQuery(new Term(TPDocument.REJECT_REASON_ID, RepositoryConstants.REJECT_REASON_POSITION_CLOSED));
				}
				if (tQuery != null) {
					orQrys.add(tQuery);
				}
			}
		}
		if (orQrys.size() > 0) {
			BooleanQuery combineORQuery = new BooleanQuery();
			for (int i = 0; i < orQrys.size(); i++) {
				combineORQuery.add(orQrys.get(i), BooleanClause.Occur.SHOULD);
			}
			BooleanClause thisClause = new BooleanClause(combineORQuery, BooleanClause.Occur.MUST);
			filter.add(thisClause);
		}
	}

	private void applyExcludeInprocessFilter(String excludeInprocess, String excludeRejectedInPast, BooleanQuery filter) {
		// must not clause can not appear as alone
		if(SearchConstants.SEARCH_BLACKLISTED.equals(excludeInprocess)){
			applyApplicantStatusFilter(ApplicantConstants.APPLICANT_STATUS_BLACKLISTED, filter);
		} else {
			applyApplicantStatusFilter(ApplicantConstants.APPLICANT_STATUS_NORMAL, filter);
			if (!excludeInprocess.equals(SearchConstants.SEARCH_ALL)) {

				if (excludeInprocess.equals(SearchConstants.SEARCH_NOT_INPROCESS)) {				
					Query termQuery = new TermQuery(new Term(TPDocument.STATE_FIELD, RepositoryConstants.STATE_INPROCESS));
					BooleanClause clause = new BooleanClause(termQuery, BooleanClause.Occur.MUST_NOT);
					filter.add(clause);
					
					termQuery = new TermQuery(new Term(TPDocument.STATE_FIELD, RepositoryConstants.STATE_JOINED));
					clause = new BooleanClause(termQuery, BooleanClause.Occur.MUST_NOT);
					filter.add(clause);
					
					termQuery = new TermQuery(new Term(TPDocument.STATE_FIELD, RepositoryConstants.STATE_NEW));
					clause = new BooleanClause(termQuery, BooleanClause.Occur.SHOULD);
					filter.add(clause);
					
					if (Utils.isBlankOrNull(excludeRejectedInPast)) {
						/**
						 * state should be processed when exclude rejected is not selected.
						 */
						termQuery = new TermQuery(new Term(TPDocument.STATE_FIELD, RepositoryConstants.STATE_PROCESSED));
						clause = new BooleanClause(termQuery, BooleanClause.Occur.SHOULD);
						filter.add(clause);
					}
					
					if (!Utils.isBlankOrNull(excludeRejectedInPast)) {					
						termQuery = new TermQuery(new Term(TPDocument.STATE_FIELD, RepositoryConstants.STATE_PROCESSED));
						clause = new BooleanClause(termQuery, BooleanClause.Occur.MUST_NOT);
						filter.add(clause);
					}
				} else if (excludeInprocess.equals(SearchConstants.SEARCH_INPROCESS)) {
					Query termQuery = new TermQuery(new Term(TPDocument.STATE_FIELD, RepositoryConstants.STATE_INPROCESS));
					BooleanClause clause = new BooleanClause(termQuery, BooleanClause.Occur.MUST);
					filter.add(clause);
				}else if(excludeInprocess.equals(SearchConstants.SEARCH_APPLIED_STAGE)){
					Query termQuery = new TermQuery(new Term(TPDocument.STATE_FIELD, RepositoryConstants.STATE_APPLIED));
					BooleanClause clause = new BooleanClause(termQuery, BooleanClause.Occur.MUST);
					filter.add(clause);
				} else if (excludeInprocess.equals(SearchConstants.SEARCH_EXCLUDE_EMPLOYEE)) {
					Query termQuery = new TermQuery(new Term(TPDocument.IS_EMPLOYEE_FIELD, RepositoryConstants.APPLICANT_NOT_EMPLOYEE));
					BooleanClause clause = new BooleanClause(termQuery, BooleanClause.Occur.MUST);
					filter.add(clause);
				} 
			} else if(GlobalConstants.ENABLED.equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_EMPLOYEE_CAN_APPLY_FOR_JOB))){
				if (filter.getClauses().length == 0) {
					Query termQuery = new TermQuery(new Term(TPDocument.IS_EMPLOYEE_FIELD, RepositoryConstants.APPLICANT_NOT_EMPLOYEE));
					BooleanClause clause = new BooleanClause(termQuery, BooleanClause.Occur.SHOULD);
					filter.add(clause);
					termQuery = new TermQuery(new Term(TPDocument.IS_EMPLOYEE_FIELD, RepositoryConstants.APPLICANT_EMPLOYEE));
					clause = new BooleanClause(termQuery, BooleanClause.Occur.SHOULD);
					filter.add(clause);
				}
			} else if(GlobalConstants.ENABLED.equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SHOW_JOINED_CANDIDATE_IN_SEARCH))){
				if (filter.getClauses().length == 0) {
					Query termQuery = new TermQuery(new Term(TPDocument.IS_EMPLOYEE_FIELD, RepositoryConstants.APPLICANT_NOT_EMPLOYEE));
					BooleanClause clause = new BooleanClause(termQuery, BooleanClause.Occur.SHOULD);
					filter.add(clause);
					termQuery = new TermQuery(new Term(TPDocument.IS_EMPLOYEE_FIELD, RepositoryConstants.APPLICANT_EMPLOYEE));
					clause = new BooleanClause(termQuery, BooleanClause.Occur.SHOULD);
					filter.add(clause);
				}
			}
		}
	}

	private void excludeJoinedCandidates(BooleanQuery bQuery, BooleanQuery filter) {
		// Exclude joined candidates, permananent search exclude joined always
		// must not clause can not appear as alone

		if (bQuery.getClauses().length > 0) {
			Query termQuery = new TermQuery(new Term(TPDocument.STATE_FIELD, RepositoryConstants.STATE_JOINED));
			BooleanClause clause = new BooleanClause(termQuery, BooleanClause.Occur.MUST_NOT);
			bQuery.add(clause);
		}
		// only filter applied without keywords
		if (filter.getClauses().length > 0 && bQuery.getClauses().length == 0) {
			Query termQuery = new TermQuery(new Term(TPDocument.STATE_FIELD, RepositoryConstants.STATE_JOINED));
			BooleanClause clause = new BooleanClause(termQuery, BooleanClause.Occur.MUST_NOT);
			filter.add(clause);
		}

		// If no calsuse exist show all applicants
		if (filter.getClauses().length == 0 && bQuery.getClauses().length == 0) {
			Query termQuery = new TermQuery(new Term(TPDocument.STATE_FIELD, RepositoryConstants.STATE_NEW));
			BooleanClause clause = new BooleanClause(termQuery, BooleanClause.Occur.SHOULD);
			filter.add(clause);
			termQuery = new TermQuery(new Term(TPDocument.STATE_FIELD, RepositoryConstants.STATE_PROCESSED));
			clause = new BooleanClause(termQuery, BooleanClause.Occur.SHOULD);
			filter.add(clause);
			termQuery = new TermQuery(new Term(TPDocument.STATE_FIELD, RepositoryConstants.STATE_INPROCESS));
			clause = new BooleanClause(termQuery, BooleanClause.Occur.SHOULD);
			filter.add(clause);
			termQuery = new TermQuery(new Term(TPDocument.STATE_FIELD, RepositoryConstants.STATE_JOINED));
			clause = new BooleanClause(termQuery, BooleanClause.Occur.MUST_NOT);
			filter.add(clause);
		}
	}

	private Query getQueryConstructedFromMainQueryAndFilter(BooleanQuery bQuery, BooleanQuery filter, IndexSearcher searcher) throws IOException {
		Query qr = null;
		if (filter.getClauses().length > 0 && bQuery.getClauses().length > 0) {
			FilteredQuery filterQuery = new FilteredQuery(bQuery, new QueryWrapperFilter(filter));
			qr = filterQuery.rewrite(searcher.getIndexReader());
		} else if (bQuery.getClauses().length == 0) {
			qr = filter;// .rewrite(searcher.getIndexReader());
		} else {
			qr = bQuery;// .rewrite(searcher.getIndexReader());
		}
		return qr;
	}

	private Sort getSorter(String sortBy) {
		Sort sort = null;
		if (sortBy.equals(SearchConstants.SORT_BY_IMPORT_DATE)) {
			sort = new Sort(new SortField(TPDocument.IMPORTDT_FIELD, SortField.Type.LONG,  true));
		} else if (sortBy.equals(SearchConstants.SORT_BY_EXP)) {
			sort = new Sort(new SortField(TPDocument.EXP_FIELD, SortField.Type.INT));
		} else if (sortBy.equals(SearchConstants.SORT_BY_NAME) || sortBy.equals(SearchConstants.SORT_BY_SCORE)) {
			sort = new Sort(new SortField(TPDocument.NAME_FIELD, SortField.Type.STRING));
		}
		return sort;
	}

	public TopDocs search(int noOfTopDocs, String miscSearch, String matchCondition, double minExp, double maxExp, String location, String currentEmployer, String previousEmployer, String designation,
			String passport,String sourceTypeId, String resumeTypeId, String source, String importDaysFilter, String importDaysFROM, String importDaysTO, String birthDayFilter, String birthDaysFROM, String birthDaysTO,  
			String lastInteractionFilter, String lastInteractionFROM, String lastInteractionTO,
			String excludeInprocess, String excludeRejectedInPast, ArrayList<String> degrees, String degreeCriteria, ArrayList<String> majors, String institute, String[] flagIds, String[] rejectReasons, String sortBy,
			ArrayList<CustomFieldData> customFields, boolean confidentialPermission,String phone1,String phone2,String mobile,String tenthMarksFilter, String tenthMarksFROM, String tenthMarksTO,
			String twelvthMarksFilter, String twelvthMarksFROM, String twelvthMarksTO,String gradeMarksFilter, String gradeMarksFROM, String gradeMarksTO,
			String postGradeMarksFilter, String postGradeMarksFROM, String postGradeMarksTO,long ageFrom,long ageTo,long experienceFrom,long experienceTo,String ageFilter,String workExpFilter,String positionApplied) throws ParseException, IOException {
		// analyzer = new WhitespaceAnalyzer();
		QueryParser parser = new  QueryParser(TPDocument.KEYWORDS_FIELD, analyzer);
		parser.setAllowLeadingWildcard(true);
		// Construct Generic Query
		BooleanQuery bQuery = new BooleanQuery();
		// misc query to be executed on content and keyword
		setMainQueryKeywordSearch(miscSearch, matchCondition, bQuery, parser);
		keywordQuery = (Query) bQuery.clone();
		
		TPLogger.getLogger().info("search underTp doc repository search");
		
		// Construct Query Filter
		BooleanQuery filter = new BooleanQuery();
		applyExperienceFilter(minExp, maxExp, filter);
		applyLocationFilter(location, filter, parser);
		applySourceTypeFilter(sourceTypeId, filter);
		applyResumeTypeFilter(resumeTypeId, filter);
		applySourceFilter(source, filter);
		applyPositionAppliedFilter(positionApplied,filter);
		applyCurrentEmployerFilter(currentEmployer, filter, parser);
		applyPreviousEmployerFilter(previousEmployer, filter, parser);
		applyDesignationFilter(designation, filter, parser);
		applyPassportFilter(passport, filter);
		applyPhoneNumberFilter(phone1,filter,parser);
		applyPhoneNumberFilter(phone2,filter,parser);
		applyPhoneNumberFilter(mobile,filter,parser);
		long minD = getTimestamp(importDaysFROM, Utils.regEUDateFormat);
		long maxD = getTimestamp(importDaysTO, Utils.regEUDateFormat);
		applyDateRangeFilter(TPDocument.IMPORTDT_FIELD, importDaysFilter, minD, maxD, filter);
		
		minD = getTimestamp(birthDaysFROM, Utils.regEUDateFormat);
		maxD = getTimestamp(birthDaysTO, Utils.regEUDateFormat);
		applyDateRangeFilter(TPDocument.BIRTH_DATE_FIELD, birthDayFilter, minD, maxD, filter);
		
		minD = getTimestamp(lastInteractionFROM, Utils.regEUDateFormat);
		maxD = getTimestamp(lastInteractionTO, Utils.regEUDateFormat);
		applyDateRangeFilter(TPDocument.LAST_INTERACTIONDT_FIELD, lastInteractionFilter, minD, maxD, filter);
		
		// asian paints filter search data
		double minMarks=0.0;
		double maxMarks=0.0;
		try {
			minMarks=Double.parseDouble(tenthMarksFROM);
		} catch (Exception e) {
			minMarks=0.0;
		}
		try {
			maxMarks=Long.parseLong(tenthMarksTO);
		} catch (Exception e) {
			maxMarks=0;
		}
		applyMarksRangeFilter(TPDocument.TENTH_GRADE_MARKS,tenthMarksFilter,minMarks,maxMarks,filter);
		
		try {
			minMarks=Long.parseLong(twelvthMarksFROM);
		} catch (Exception e) {
			minMarks=0;
		}
		try {
			maxMarks=Double.parseDouble(twelvthMarksTO);
		} catch (Exception e) {
			maxMarks=0.0;
		}
		 applyMarksRangeFilter(TPDocument.TWELVTH_GRADE_MARKS,twelvthMarksFilter,minMarks,maxMarks,filter);
		
		 try {
				minMarks=Double.parseDouble(gradeMarksFROM);
			} catch (Exception e) {
				minMarks=0;
			}
			try {
				maxMarks=Long.parseLong(gradeMarksTO);
			} catch (Exception e) {
				maxMarks=0.0;
			}
			
		 applyMarksRangeFilter(TPDocument.GRADE_MARKS,gradeMarksFilter,minMarks,maxMarks,filter);
		 
		 try {
				minMarks=Double.parseDouble(postGradeMarksFROM);
			} catch (Exception e) {
				minMarks=0.0;
			}
			try {
				maxMarks=Double.parseDouble(postGradeMarksTO);
			} catch (Exception e) {
				maxMarks=0;
			}
			
		
		 applyMarksRangeFilter(TPDocument.POST_GRADE_MARKS,postGradeMarksFilter,minMarks,maxMarks,filter);
		 applyAgeFilter(ageFrom,ageTo,filter,ageFilter);
		 applyWorkExperienceFilter(experienceFrom, experienceTo, filter,workExpFilter);
		
		applyDegreeFilter(degrees, degreeCriteria, filter);
		applyMajorFilter(majors, filter);
		applyCustomFieldsFilter(customFields, filter);
		applyMultipleIdsFilter(TPDocument.FLAG_ID, flagIds, filter);

		// exclude joined candidates
		if(GlobalConstants.DISABLED.equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_EMPLOYEE_CAN_APPLY_FOR_JOB))
				& GlobalConstants.DISABLED.equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SHOW_JOINED_CANDIDATE_IN_SEARCH))) {
			excludeJoinedCandidates(bQuery, filter);
		}
		
		//aply this filter after exclude joined 
		applyExcludeInprocessFilter(excludeInprocess, excludeRejectedInPast, filter);
		applyRejectIdsFilter(rejectReasons, filter);
		
		applyConfidentialityFilter(confidentialPermission, filter, parser);
		
		// get main query constructed from main query and filter
		IndexSearcher searcher = getIndexSearcher();
		this.query = getQueryConstructedFromMainQueryAndFilter(bQuery, filter, searcher);
		TPLogger.getLogger().info("query="+query);
		// set sort criteria, you can add custom sort
		Sort sort = getSorter(sortBy);
		TopDocs result = null; 
		if (sort != null) {
			result = searcher.search(this.query, null, noOfTopDocs, sort); 
		}else{
			result =searcher.search(this.query, null, noOfTopDocs); 
		}
		closeIndexSearcher();
		return result;
	}

	/**
	 * escapes lucene special charaters \ + - ! ( ) : ^ ] { } ~ * ? from given querystring.
	 * 
	 * @param queryStr
	 * @return
	 */
	// private String escapeSpecial(String queryStr) {
	// // chars to escape
	// return QueryParser.escape(queryStr);
	// }
	private ArrayList<String> getTokens(String content) {
		ArrayList<String> tokens = new ArrayList<String>();
		try {
			//StandardAnalyzer analyzer = new TPStandardAnalyzer(stopwords);
//			WhitespaceAnalyzer sAnalyzer = new WhitespaceAnalyzer();
			Analyzer sAnalyzer = TpCustomAnalyzer.getAnalyzer();
			TokenStream stream = sAnalyzer.tokenStream("", new StringReader(content));
			CharTermAttribute charAttr = stream.addAttribute(CharTermAttribute.class);
			stream.reset();
			while (stream.incrementToken()) {
				tokens.add(charAttr.toString());
			}
			stream.end();
			stream.close();
			sAnalyzer.close();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting tokens", e);
		}
		return tokens;

	}

	private void applyCurrentEmployerFilter(String currentEmployer, BooleanQuery filter, QueryParser parser) throws ParseException {
		ArrayList<String> currentEmployers = getTokens(currentEmployer);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < currentEmployers.size(); i++) {
			sb.append(TPDocument.LASTEMP_FIELD + ":" + currentEmployers.get(i) + " ");
			Query thisQry = parser.parse(sb.toString());
			BooleanClause thisClause = new BooleanClause(thisQry, BooleanClause.Occur.MUST);
			filter.add(thisClause);
		}		
	}
	
	private void applyPassportFilter(String passport, BooleanQuery filter) throws ParseException {
		if (!Utils.isBlankOrNull(passport)) {
			PhraseQuery psQuery = new PhraseQuery();
			psQuery.add(new Term(TPDocument.PASSPORT_FIELD, passport));
			BooleanClause srcClause = new BooleanClause(psQuery, BooleanClause.Occur.MUST);
			filter.add(srcClause);
		}
	}
	
	private void applyPhoneNumberFilter(String phone1, BooleanQuery bQuery, QueryParser parser) throws ParseException {
		StringBuffer sb=null;
		ArrayList<String> genericTerms = getTokens(phone1);
		for (int i = 0; i < genericTerms.size(); i++) {
			sb = new StringBuffer();
			sb.append(TPDocument.KEYWORDS_FIELD + ":" + genericTerms.get(i) + " ");
			sb.append(TPDocument.CONTENTS_FIELD + ":" + genericTerms.get(i) + " ");
			Query thisQry = parser.parse(sb.toString());
			BooleanClause thisClause = new BooleanClause(thisQry, BooleanClause.Occur.MUST);
			bQuery.add(thisClause);
		}
		
	}
	
	private void applyNumberRangeFilter(String fieldName, String rangeCriteria, String rangeFrom, String rangeTo, BooleanQuery filter) {
		if (!Utils.isBlankOrNull(rangeCriteria)) {
			Query q = null;
			boolean minInclusive = true;
			boolean maxInclusive = true;
			if (rangeCriteria.equals(SearchConstants.CRITERIA_NOT_SPECIFIED)) {
				//q = new TrieRangeQuery(fieldName, RepositoryConstants.BLANK_NUMBER, RepositoryConstants.BLANK_NUMBER, TrieUtils.VARIANT_8BIT);
				q = NumericRangeQuery.newDoubleRange(fieldName, RepositoryConstants.BLANK_NUMBER, RepositoryConstants.BLANK_NUMBER, minInclusive, maxInclusive);
			} else if (rangeCriteria.equals(SearchConstants.CRITERIA_LESS_THAN)) {
				//q = new TrieRangeQuery(fieldName, 0d, getDouble(rangeFrom), TrieUtils.VARIANT_8BIT);
				q = NumericRangeQuery.newDoubleRange(fieldName, 0d, getDouble(rangeFrom), minInclusive, maxInclusive);
			} else if (rangeCriteria.equals(SearchConstants.CRITERIA_GREATER_THAN)) {
				//q = new TrieRangeQuery(fieldName, getDouble(rangeFrom), RepositoryConstants.BLANK_NUMBER - 1, TrieUtils.VARIANT_8BIT);
				q = NumericRangeQuery.newDoubleRange(fieldName, getDouble(rangeFrom), RepositoryConstants.BLANK_NUMBER - 1, minInclusive, maxInclusive);
			} else if (rangeCriteria.equals(SearchConstants.CRITERIA_EQUAL_TO)) {
				//q = new TrieRangeQuery(fieldName, getDouble(rangeFrom), getDouble(rangeFrom), TrieUtils.VARIANT_8BIT);
				q = NumericRangeQuery.newDoubleRange(fieldName, getDouble(rangeFrom), getDouble(rangeFrom), minInclusive, maxInclusive);
			} else if (rangeCriteria.equals(SearchConstants.CRITERIA_BETWEEN)) {
				//q = new TrieRangeQuery(fieldName, getDouble(rangeFrom), getDouble(rangeTo), TrieUtils.VARIANT_8BIT);
				q = NumericRangeQuery.newDoubleRange(fieldName, getDouble(rangeFrom), getDouble(rangeTo), minInclusive, maxInclusive);
			}
			BooleanClause expClause = new BooleanClause(q, BooleanClause.Occur.MUST);
			filter.add(expClause);
		}
	}
	


	/**
	 * return Double object and ignore exception in formatting
	 * 
	 * @param dbl
	 * @return
	 */
	private Double getDouble(String dbl) {
		try {
			return Double.parseDouble(dbl);
		} catch (Exception e) {
			return 0d;
		}
	}


	private long getTimestamp(String dt, String format) {
		long val = 0;
		try {
			Date mDt = Utils.convertToDate(dt, format);
			if (mDt != null) {
				val = mDt.getTime();
			}
		} catch (Exception e) {

		}
		return val;
	}	

	private void applyDateAgeFilter(String fieldName, String rangeCriteria, long rangeFrom, long rangeTo, BooleanQuery filter) {
		if (!Utils.isBlankOrNull(rangeCriteria)) {
			Query q = null;
			boolean minInclusive = true;
			boolean maxInclusive = true;
			if (rangeCriteria.equals(SearchConstants.CRITERIA_NOT_SPECIFIED)) {
				//q = new TrieRangeQuery(fieldName, RepositoryConstants.BLANK_LONG, RepositoryConstants.BLANK_LONG, TrieUtils.VARIANT_8BIT);
				q = NumericRangeQuery.newLongRange(fieldName, RepositoryConstants.BLANK_LONG, RepositoryConstants.BLANK_LONG, minInclusive, maxInclusive);
			} else if (rangeCriteria.equals(SearchConstants.CRITERIA_GREATER_THAN)) {
				//q = new TrieRangeQuery(fieldName, 0l, rangeFrom, TrieUtils.VARIANT_8BIT);
				q = NumericRangeQuery.newLongRange(fieldName, 0l, rangeFrom, false, maxInclusive);
			} else if (rangeCriteria.equals(SearchConstants.CRITERIA_LESS_THAN)) {
				//q = new TrieRangeQuery(fieldName, rangeFrom, RepositoryConstants.BLANK_LONG - 1, TrieUtils.VARIANT_8BIT);
				q = NumericRangeQuery.newLongRange(fieldName, rangeFrom, RepositoryConstants.BLANK_LONG - 1, minInclusive, maxInclusive);
			} else if (rangeCriteria.equals(SearchConstants.CRITERIA_EQUAL_TO)) {
				//q = new TrieRangeQuery(fieldName, rangeFrom, rangeFrom, TrieUtils.VARIANT_8BIT);
				q = NumericRangeQuery.newLongRange(fieldName, rangeFrom, rangeFrom+DateConstants.MILLISECONDS_IN_A_DAY, minInclusive, maxInclusive);
			} else if (rangeCriteria.equals(SearchConstants.CRITERIA_BETWEEN)) {
				//q = new TrieRangeQuery(fieldName, rangeFrom, rangeTo, TrieUtils.VARIANT_8BIT);
				q = NumericRangeQuery.newLongRange(fieldName, rangeFrom, rangeTo, minInclusive, maxInclusive);
			}
			BooleanClause expClause = new BooleanClause(q, BooleanClause.Occur.MUST);
			filter.add(expClause);
		}
	}
	
	private void applyDateRangeFilter(String fieldName, String rangeCriteria, long rangeFrom, long rangeTo, BooleanQuery filter) {
		if (!Utils.isBlankOrNull(rangeCriteria)) {
			Query q = null;
			boolean minInclusive = true;
			boolean maxInclusive = true;
			if (rangeCriteria.equals(SearchConstants.CRITERIA_NOT_SPECIFIED)) {
				//q = new TrieRangeQuery(fieldName, RepositoryConstants.BLANK_LONG, RepositoryConstants.BLANK_LONG, TrieUtils.VARIANT_8BIT);
				q = NumericRangeQuery.newLongRange(fieldName, RepositoryConstants.BLANK_LONG, RepositoryConstants.BLANK_LONG, minInclusive, maxInclusive);
			} else if (rangeCriteria.equals(SearchConstants.CRITERIA_LESS_THAN)) {
				//q = new TrieRangeQuery(fieldName, 0l, rangeFrom, TrieUtils.VARIANT_8BIT);
				q = NumericRangeQuery.newLongRange(fieldName, 0l, rangeFrom, false, maxInclusive);
			} else if (rangeCriteria.equals(SearchConstants.CRITERIA_GREATER_THAN)) {
				//q = new TrieRangeQuery(fieldName, rangeFrom, RepositoryConstants.BLANK_LONG - 1, TrieUtils.VARIANT_8BIT);
				q = NumericRangeQuery.newLongRange(fieldName, rangeFrom, RepositoryConstants.BLANK_LONG - 1, minInclusive, maxInclusive);
			} else if (rangeCriteria.equals(SearchConstants.CRITERIA_EQUAL_TO)) {
				//q = new TrieRangeQuery(fieldName, rangeFrom, rangeFrom, TrieUtils.VARIANT_8BIT);
				q = NumericRangeQuery.newLongRange(fieldName, rangeFrom, rangeFrom+DateConstants.MILLISECONDS_IN_A_DAY, minInclusive, maxInclusive);
			} else if (rangeCriteria.equals(SearchConstants.CRITERIA_BETWEEN)) {
				//q = new TrieRangeQuery(fieldName, rangeFrom, rangeTo, TrieUtils.VARIANT_8BIT);
				q = NumericRangeQuery.newLongRange(fieldName, rangeFrom, rangeTo, minInclusive, maxInclusive);
			}
			BooleanClause expClause = new BooleanClause(q, BooleanClause.Occur.MUST);
			filter.add(expClause);
		}
	}
	
	private void applyMarksRangeFilter(String fieldName, String rangeCriteria, double rangeFrom, double rangeTo, BooleanQuery filter) {
		TPLogger.getLogger().info("search under applyMarksRangeFilter rangeCriteria"+rangeCriteria);
		TPLogger.getLogger().info("fieldName"+fieldName);
		TPLogger.getLogger().info("rangeFrom"+rangeFrom);
		TPLogger.getLogger().info("rangeTo"+rangeTo);
		if (!Utils.isBlankOrNull(rangeCriteria)) {
			Query q = null;
			boolean minInclusive = true;
			boolean maxInclusive = true;
			if (rangeCriteria.equals(SearchConstants.CRITERIA_NOT_SPECIFIED)) {
				q = NumericRangeQuery.newDoubleRange(fieldName, RepositoryConstants.BLANK_NUMBER, RepositoryConstants.BLANK_NUMBER, minInclusive, maxInclusive);
			} else if (rangeCriteria.equals(SearchConstants.CRITERIA_LESS_THAN)) {
				q = NumericRangeQuery.newDoubleRange(fieldName, 1.0, rangeFrom, false, maxInclusive);
			} else if (rangeCriteria.equals(SearchConstants.CRITERIA_GREATER_THAN)) {
				q = NumericRangeQuery.newDoubleRange(fieldName, rangeFrom, RepositoryConstants.BLANK_NUMBER - 1, minInclusive, maxInclusive);
			} else if (rangeCriteria.equals(SearchConstants.CRITERIA_EQUAL_TO)) {
				q = NumericRangeQuery.newDoubleRange(fieldName, rangeFrom, rangeFrom, minInclusive, maxInclusive);
			} else if (rangeCriteria.equals(SearchConstants.CRITERIA_BETWEEN)) {
				q = NumericRangeQuery.newDoubleRange(fieldName, rangeFrom, rangeTo, minInclusive, maxInclusive);
			}
			BooleanClause expClause = new BooleanClause(q, BooleanClause.Occur.MUST);
			filter.add(expClause);
		}
	}
	
	private void applyWorkExperienceFilter(double minExp, double maxExp, BooleanQuery filter,String rangeCriteria) {
	
			
			if (!Utils.isBlankOrNull(rangeCriteria)) {
				if(rangeCriteria.equals(SearchConstants.CRITERIA_GREATER_THAN)){
					maxExp=200;
				}
				if (minExp > 0 || maxExp > 0) {
					Date maxDate = null;
					Date minDate = null;
					if (minExp > 0) {
						Calendar rightNow = Calendar.getInstance();
						int days = (int) (minExp * 365);
						rightNow.add(Calendar.DATE, (0 - days));
						minDate = rightNow.getTime();
					}
					if (maxExp > 0) {
						Calendar rightNow = Calendar.getInstance();
						int days = (int) (maxExp * 365);
						rightNow.add(Calendar.DATE, (0 - days));
						maxDate = rightNow.getTime();
					}
					BytesRef maxWorkingSince = null;
					BytesRef minWorkingSince = null;
					if (maxDate != null) {
						maxWorkingSince = new BytesRef(TPDateTools.dateToString(maxDate, TPDateTools.Resolution.MONTH).getBytes());
					}
					if (minDate != null) {
						minWorkingSince =new BytesRef(TPDateTools.dateToString(minDate, TPDateTools.Resolution.MONTH).getBytes());;
					}
					Query expQry = new TermRangeQuery(TPDocument.EXP_FIELD, maxWorkingSince,minWorkingSince, true,true);
				
				if (rangeCriteria.equals(SearchConstants.CRITERIA_NOT_SPECIFIED)) {
					expQry = new TermRangeQuery(TPDocument.EXP_FIELD, maxWorkingSince,minWorkingSince, true,true);
				} else if (rangeCriteria.equals(SearchConstants.CRITERIA_LESS_THAN)) {
					expQry = new TermRangeQuery(TPDocument.EXP_FIELD, minWorkingSince,maxWorkingSince, true,true);
				} else if (rangeCriteria.equals(SearchConstants.CRITERIA_GREATER_THAN)) {
					expQry = new TermRangeQuery(TPDocument.EXP_FIELD, maxWorkingSince,minWorkingSince, true,true);
				} else if (rangeCriteria.equals(SearchConstants.CRITERIA_EQUAL_TO)) {
					expQry = new TermRangeQuery(TPDocument.EXP_FIELD, minWorkingSince,minWorkingSince, true,true);
				} else if (rangeCriteria.equals(SearchConstants.CRITERIA_BETWEEN)) {
					expQry = new TermRangeQuery(TPDocument.EXP_FIELD, maxWorkingSince,minWorkingSince, true,true);
				}
				BooleanClause expClause = new BooleanClause(expQry, BooleanClause.Occur.MUST);
				filter.add(expClause);
				}
			}
			
			
		

	}
	
	
	private void applyConfidentialityFilter(boolean confidentiality, BooleanQuery filter, QueryParser parser) throws ParseException {
		if(!confidentiality) {
			TermQuery tQuery = new TermQuery(new Term(TPDocument.CONFIDENTIAL, ApplicantConstants.APPLICANT_NOT_CONFIDENTIAL));
			BooleanClause thisClause = new BooleanClause(tQuery, BooleanClause.Occur.MUST);
			filter.add(thisClause);
		}
	}
	
	private void applyApplicantStatusFilter(String applicantStatus, BooleanQuery filter) {
		TermQuery tQuery = new TermQuery(new Term(TPDocument.APPLICANT_STATUS, applicantStatus));
		BooleanClause thisClause = new BooleanClause(tQuery, BooleanClause.Occur.MUST);
		filter.add(thisClause);
	}
	
	private void applyPreviousEmployerFilter(String previousEmployer, BooleanQuery filter, QueryParser parser) throws ParseException {
		ArrayList<String> previousEmployers = getTokens(previousEmployer);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < previousEmployers.size(); i++) {
			sb.append(TPDocument.PREVIOUS_EMPLOYERS + ":" + previousEmployers.get(i) + " ");
			Query thisQry = parser.parse(sb.toString());
			BooleanClause thisClause = new BooleanClause(thisQry, BooleanClause.Occur.MUST);
			filter.add(thisClause);
		}		
	}
	
	private void applyDesignationFilter(String designation, BooleanQuery filter, QueryParser parser) throws ParseException {
		ArrayList<String> designationEmployers = getTokens(designation);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < designationEmployers.size(); i++) {
			sb.append(TPDocument.DESIGNATION + ":" + designationEmployers.get(i) + " ");
			Query thisQry = parser.parse(sb.toString());
			BooleanClause thisClause = new BooleanClause(thisQry, BooleanClause.Occur.MUST);
			filter.add(thisClause);
		}		
	}
}
