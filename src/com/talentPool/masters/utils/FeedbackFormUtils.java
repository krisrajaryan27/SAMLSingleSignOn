/**
 * 
 */
package com.talentPool.masters.utils;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.xmlutils.XMLWriter;
import com.talentPool.masters.dataobject.FeedbackFieldData;
import com.talentPool.masters.dataobject.FeedbackFormFieldData;
import com.talentPool.masters.dataobject.MultipleSelectFieldsData;
import com.talentPool.masters.dataobject.MultipleSelectsData;
import com.talentPool.masters.dataobject.RatingFieldsData;
import com.talentPool.masters.dataobject.RatingsData;
import com.talentPool.reports.ReportConstants;
import com.talentPool.reports.views.FeedbackFormView;

/**
 * @author shivprasad
 * 
 */
public class FeedbackFormUtils {
	public ArrayList<FeedbackFormFieldData> constructFormFieldsFromString(String strFlds) {
		ArrayList<FeedbackFormFieldData> formFields = null;
		try {
			if (!Utils.isBlankOrNull(strFlds)) {
				formFields = new ArrayList<FeedbackFormFieldData>();
				String[] fields = strFlds.split(":");				
				for (int i = 0; i < fields.length; i++) {
					String[] fld = fields[i].split("\\|");
					FeedbackFormFieldData feedbackFormFieldData = new FeedbackFormFieldData();
					feedbackFormFieldData.setFeedbackFormFieldId(fld[0]);
					feedbackFormFieldData.setFeedbackFieldId(fld[1]);
					feedbackFormFieldData.setFeedbackFieldTitle(fld[2].replaceAll("&#124;", "|").replaceAll("&#58;", ":"));
					feedbackFormFieldData.setFeedbackFormFieldDesc(fld[3].replaceAll("&#124;", "|").replaceAll("&#58;", ":"));
					feedbackFormFieldData.setFeedbackFormFieldType(fld[4]);
					feedbackFormFieldData.setRatingId(fld[5]);
					feedbackFormFieldData.setMultipleSelectId(fld[6]);
					feedbackFormFieldData.setFeedbackFormFieldCommentRequired(fld[7]);
					feedbackFormFieldData.setFieldDisplayType(fld[8]);
					feedbackFormFieldData.setFieldIsMandatory(fld[9]);
					feedbackFormFieldData.setSystemGenerated(fld[10]);
					feedbackFormFieldData.setFeedbackFieldType(fld[11]);
					if(fld.length>12)
						feedbackFormFieldData.setApplicantFieldId(fld[12]);
					else
						feedbackFormFieldData.setApplicantFieldId(null);
					formFields.add(feedbackFormFieldData);
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
		}
		return formFields;
	}

	public static void setFeedbackFormViewAttributes(String fieldTitle, String fieldComment, String rating, String compactHeader, String compactRating, String compactFieldComment, String compactFieldTitle, String multipleSelect, FeedbackFormView feedbackFormView) {
		feedbackFormView.setFieldTitle(fieldTitle);
		feedbackFormView.setFieldComment(fieldComment);
		feedbackFormView.setRating(rating);
		feedbackFormView.setCompactHeader(compactHeader);
		feedbackFormView.setCompactRating(compactRating);
		feedbackFormView.setCompactFieldComment(compactFieldComment);
		feedbackFormView.setCompactFieldTitle(compactFieldTitle);
		feedbackFormView.setMultipleSelect(multipleSelect);
	}
	
	public static String getRatingsCommentConstructed(List<RatingsData> ratings, String ratingsId, String ratingFieldId) {		
		RatingsData ratingsData = getRatingsData(ratings, ratingsId);
		StringBuffer rb = new StringBuffer();
		if (ratingsData != null) {
			ArrayList<RatingFieldsData> ratingFields = ratingsData.getRatingFields();
			for (int i = 0; ratingFields != null && i < ratingFields.size(); i++) {
				RatingFieldsData ratingFieldsData = ratingFields.get(i);
				
				if (ratingFieldsData.getRatingFieldId().equals(ratingFieldId)) {
					rb.append(" [x] ");	
				} else {
					rb.append(" [ ] ");	
				}
				rb.append(ratingFieldsData.getRatingFieldDesc() + "    ");
			}
		}
		return rb.toString();
	}	
	
	public static String[] getCompactRatingsDataConstructed(ArrayList<RatingsData> ratings, String ratingsId, String ratingFieldId, String reportFormat) {
		String[] retVal = new String[2];
		RatingsData ratingData = getRatingsData(ratings, ratingsId);
		StringBuffer compactRating = new StringBuffer();
		StringBuffer compactHeader = new StringBuffer();
		StringBuffer compactRatingDesc = new StringBuffer();
		if (ratingData != null) {
			ArrayList<RatingFieldsData> ratingFields = ratingData.getRatingFields();
			for (int i = 0; ratingFields != null && i < ratingFields.size(); i++) {
				RatingFieldsData ratingFieldsData = ratingFields.get(i);
				String description = ratingFieldsData.getRatingFieldDesc();
				compactRatingDesc.append(description);
				if(i != (ratingFields.size() - 1)) {
					compactRatingDesc.append(", ");
				}
				if (reportFormat.equalsIgnoreCase(ReportConstants.FORMAT_EXCEL)) {
					if(i == 0) {
						compactHeader.append(" ");
					} else {
						compactHeader.append("   ");
					}
					compactHeader.append(description.substring(0, 1));				
					if (ratingFieldsData.getRatingFieldId().equals(ratingFieldId)) {					
						compactRating.append(" [x] ");				
					} else {						
						compactRating.append(" [ ] ");		
					}
				} else if (reportFormat.equalsIgnoreCase(ReportConstants.FORMAT_PDF)) {
					compactHeader.append(" " + description.substring(0, 1) + " ");				
					if (ratingFieldsData.getRatingFieldId().equals(ratingFieldId)) {					
						compactRating.append("[x]");				
					} else {						
						compactRating.append("[  ]");		
					}
				} else if (reportFormat.equalsIgnoreCase(ReportConstants.FORMAT_HTML)) {
					compactHeader.append("  " + description.substring(0, 1));				
					if (ratingFieldsData.getRatingFieldId().equals(ratingFieldId)) {					
						compactRating.append("[x]");				
					} else {						
						compactRating.append("[ ] ");		
					} 
				}				
			}
		}
		if(compactRatingDesc.length() > 0) {
			compactRatingDesc = new StringBuffer("( ").append(compactRatingDesc);
			compactRatingDesc.append(" )");
		}
		compactHeader.append(" ");
		compactHeader.append(compactRatingDesc.toString());
		retVal[0] = compactHeader.toString();
		retVal[1] = compactRating.toString();
		return retVal;
	}
	
	private static RatingsData getRatingsData(List<RatingsData> ratings, String ratingsId) {
		RatingsData ratingsData = null;
		if (ratings != null) {
			for (int i = 0; i < ratings.size(); i++) {
				if (ratings.get(i).getRatingId().equals(ratingsId)) {
					ratingsData = ratings.get(i);
					break;
				}
			}
		}
		return ratingsData;
	}
	
	public static String getMultipleSelectsCommentConstructed(List<MultipleSelectsData> multipleSelects, String multipleSelectId, String multipleSelectFieldId) {		
		MultipleSelectsData multipleSelectsData = getMultipleSelectsData(multipleSelects, multipleSelectId);
		StringBuffer rb = new StringBuffer();
		if (multipleSelectsData != null) {
			ArrayList<MultipleSelectFieldsData> multipleSelectFields = multipleSelectsData.getMultipleSelectFields();
			List<String> multipleSelectFieldIds = new ArrayList<String>();
			if (!Utils.isBlankOrNull(multipleSelectFieldId)) {
				multipleSelectFieldIds = Arrays.asList(multipleSelectFieldId.split(","));
			}
			for (int i = 0; multipleSelectFields != null && i < multipleSelectFields.size(); i++) {
				MultipleSelectFieldsData multipleSelectFieldsData = multipleSelectFields.get(i);
				if (multipleSelectFieldIds.contains(multipleSelectFieldsData.getSelectFieldId())) {
					rb.append(" [x] ");	
				} else {
					rb.append(" [ ] ");	
				}
				rb.append(multipleSelectFieldsData.getSelectFieldDesc() + "    ");
			}
		}
		return rb.toString();
	}
	
	public static String[] getCompactMultipleSelectsDataConstructed(ArrayList<MultipleSelectsData> multipleSelects, String multipleSelectId, String multipleSelectFieldId, String reportFormat) {
		String[] retVal = new String[2];
		MultipleSelectsData multipleSelectData =  getMultipleSelectsData(multipleSelects, multipleSelectId);
		StringBuffer compactMultipleSelect = new StringBuffer();
		StringBuffer compactHeader = new StringBuffer();
		StringBuffer compactMultipleSelectDesc = new StringBuffer();
		if (multipleSelectData != null) {
			ArrayList<MultipleSelectFieldsData> multipleSelectFields = multipleSelectData.getMultipleSelectFields();
			List<String> multipleSelectFieldIds = new ArrayList<String>();
			if (!Utils.isBlankOrNull(multipleSelectFieldId)) {
				multipleSelectFieldIds = Arrays.asList(multipleSelectFieldId.split(","));
			}
				
			for (int i = 0; multipleSelectFields != null && i < multipleSelectFields.size(); i++) {
				MultipleSelectFieldsData multipleSelectFieldsData = multipleSelectFields.get(i);
				String description = multipleSelectFieldsData.getSelectFieldDesc();
				compactMultipleSelectDesc.append(description);
				if(i != (multipleSelectFields.size() - 1)) {
					compactMultipleSelectDesc.append(", ");
				}
				if (reportFormat.equalsIgnoreCase(ReportConstants.FORMAT_EXCEL)) {
					if(i == 0) {
						compactHeader.append(" ");
					} else {
						compactHeader.append("   ");
					}
					compactHeader.append(description.substring(0, 1));				
					if (multipleSelectFieldIds.contains(multipleSelectFieldsData.getSelectFieldId())) {					
						compactMultipleSelect.append(" [x] ");				
					} else {						
						compactMultipleSelect.append(" [ ] ");		
					}
				} else if (reportFormat.equalsIgnoreCase(ReportConstants.FORMAT_PDF)) {
					compactHeader.append(" " + description.substring(0, 1) + " ");				
					if (multipleSelectFieldIds.contains(multipleSelectFieldsData.getSelectFieldId())) {					
						compactMultipleSelect.append("[x]");				
					} else {						
						compactMultipleSelect.append("[  ]");		
					}
				} else if (reportFormat.equalsIgnoreCase(ReportConstants.FORMAT_HTML)) {
					compactHeader.append("  " + description.substring(0, 1));				
					if (multipleSelectFieldIds.contains(multipleSelectFieldsData.getSelectFieldId())) {					
						compactMultipleSelect.append("[x]");				
					} else {						
						compactMultipleSelect.append("[ ] ");		
					} 
				}				
			}
		}
		if(compactMultipleSelectDesc.length() > 0) {
			compactMultipleSelectDesc = new StringBuffer("( ").append(compactMultipleSelectDesc);
			compactMultipleSelectDesc.append(" )");
		}
		compactHeader.append(" ");
		compactHeader.append(compactMultipleSelectDesc.toString());
		retVal[0] = compactHeader.toString();
		retVal[1] = compactMultipleSelect.toString();
		return retVal;
	}
	
	private static MultipleSelectsData getMultipleSelectsData(List<MultipleSelectsData> multipleSelects, String multipleSelectId) {
		MultipleSelectsData multipleSelectsData  = null;
		if (multipleSelects != null) {
			for (int i = 0; i < multipleSelects.size(); i++) {
				if (multipleSelects.get(i).getSelectId().equals(multipleSelectId)) {
					multipleSelectsData = multipleSelects.get(i);
					break;
				}
			}
		}
		return multipleSelectsData;
	}
	
	public static String getFeedbacFieldXML(FeedbackFieldData feedbackFieldData){
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			wr.startDocument();
			wr.startElement("feedbackFieldData");
			wr.startElement("desc");
			wr.characters(Utils.getBlankIfNull(feedbackFieldData.getFeedbackFieldDesc()));
			wr.endElement("desc");
			wr.startElement("feedbackFieldType");
			wr.characters(Utils.getBlankIfNull(feedbackFieldData.getFeedbackFieldType()));
			wr.endElement("feedbackFieldType");
			wr.startElement("applicantFieldId");
			wr.characters(Utils.getBlankIfNull(feedbackFieldData.getApplicantFieldId()));
			wr.endElement("applicantFieldId");
			wr.endElement("feedbackFieldData");
			wr.endDocument();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
		}
		return sWr.toString();
	}
}
