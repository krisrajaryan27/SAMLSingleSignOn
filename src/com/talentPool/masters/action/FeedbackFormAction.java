/**
 * 
 */
package com.talentPool.masters.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.talentPool.applicant.manager.ImportConfigurationManager;
import com.talentPool.common.CommonConstants;
import com.talentPool.common.NavigationConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.base.TPDispatchAction;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.CommonUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.masters.constants.FeedbackFieldsConstant;
import com.talentPool.masters.constants.FeedbackFormConstants;
import com.talentPool.masters.constants.MastersConstants;
import com.talentPool.masters.dataobject.FeedbackFieldCategoryData;
import com.talentPool.masters.dataobject.FeedbackFieldData;
import com.talentPool.masters.dataobject.FeedbackFormData;
import com.talentPool.masters.dataobject.FeedbackFormFieldData;
import com.talentPool.masters.dataobject.MultipleSelectsData;
import com.talentPool.masters.dataobject.RatingsData;
import com.talentPool.masters.form.FeedbackForm;
import com.talentPool.masters.manager.FeedbackFieldsManager;
import com.talentPool.masters.manager.FeedbackFormManager;
import com.talentPool.masters.manager.MultipleSelectsManager;
import com.talentPool.masters.manager.RatingsManager;
import com.talentPool.masters.utils.FeedbackFormUtils;
import com.talentPool.masters.utils.FeedbackFormXmlGenerator;
import com.talentPool.reports.ReportConstants;
import com.talentPool.reports.ReportUtils;
import com.talentPool.reports.manager.ReportManager;
import com.talentPool.reports.views.FeedbackFormView;
import com.talentPool.user.constants.PermissionConstants;
import com.talentPool.user.manager.SessionManager;

/**
 * @author shivprasad
 * 
 */
public class FeedbackFormAction extends TPDispatchAction {
	public ActionForward feedbackFormHome(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "feedbackFormHome";
		Integer[] permissions = new Integer[2];
		permissions[0] = PermissionConstants.PERMISSION_MASTERS;
		permissions[1] = PermissionConstants.PERMISSION_FEEDBACK_FORM_MASTER;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		
		request.setAttribute("masterType", MastersConstants.MASTER_TYPE_FEEDBACK_FORMS);
		request.setAttribute("t", NavigationConstants.T_MASTERS);
		return mapping.findForward(forward);
	}

	public ActionForward getAllTemplates(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				FeedbackFormManager feedbackFormManager = new FeedbackFormManager();
				ArrayList<FeedbackFormData> feedbackForms = feedbackFormManager.getActiveFeedbackForms();
				FeedbackFormXmlGenerator feedbackFormXmlGenerator = new FeedbackFormXmlGenerator();
				xmlFile = feedbackFormXmlGenerator.getXMLForFeedbackForms(feedbackForms);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
			xmlFile = Utils.getXMLForError(null);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}

	public ActionForward deleteFeedbackForm(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				FeedbackForm feedbackForm = (FeedbackForm) actionForm;
				String feedbackFormId = feedbackForm.getFeedbackFormId();
				FeedbackFormManager feedbackFormManager = new FeedbackFormManager();
				int cnt = feedbackFormManager.getOpenPositionsForForm(feedbackFormId);
				if (cnt > 0) {
					xmlFile = Utils.getXMLForError(null);
				} else {
					feedbackFormManager.changeFeedbackFormStatus(feedbackFormId);
					xmlFile = Utils.getXMLForIds(feedbackFormId);
				}

			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
			xmlFile = Utils.getXMLForError(null);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}

	public ActionForward createTemplate(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "createTemplate";
		Integer[] permissions = new Integer[2];
		permissions[0] = PermissionConstants.PERMISSION_MASTERS;
		permissions[1] = PermissionConstants.PERMISSION_FEEDBACK_FORM_MASTER;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		try {
			FeedbackForm feedbackForm = (FeedbackForm) actionForm;
			if (!Utils.isBlankOrNull(feedbackForm.getOptionCreateAs())) {
				ActionErrors errors = new ActionErrors();
				if (Utils.isBlankOrNull(feedbackForm.getFeedbackFormTitle())) {
					errors.add("", new ActionError("feedback_form.error.please_enter_name"));
				}
				if (feedbackForm.getOptionCreateAs().equals(FeedbackFormConstants.CREATE_FROM_EXISTING_FORM)) {
					if (Utils.isBlankOrNull(feedbackForm.getCopyFromId()) || feedbackForm.getCopyFromId().equals("-1")) {
						errors.add("", new ActionError("feedback_form.error.please_select_feedback_form"));
					}
				}
				if (errors.size() == 0) {
					return addForm(mapping, actionForm, request, response);
				} else {
					saveErrors(request, errors);
				}
			}
			FeedbackFormManager feedbackFormManager = new FeedbackFormManager();
			ArrayList<FeedbackFormData> feedbackForms = feedbackFormManager.getActiveFeedbackForms();
			ArrayList<String> feedbackFormIds = new ArrayList<String>();
			ArrayList<String> feedbackFormNames = new ArrayList<String>();
			for (int i = 0; feedbackForms != null && i < feedbackForms.size(); i++) {
				FeedbackFormData feedbackFormData = feedbackForms.get(i);
				feedbackFormIds.add(feedbackFormData.getFeedbackFormId());
				feedbackFormNames.add(feedbackFormData.getFeedbackFormTitle());
			}
			request.setAttribute("feedbackFormIds", feedbackFormIds);
			request.setAttribute("feedbackFormNames", feedbackFormNames);
			if (Utils.isBlankOrNull(feedbackForm.getOptionCreateAs())) {
				feedbackForm.setOptionCreateAs(FeedbackFormConstants.CREATE_NEW_FORM);
			}
			if(Utils.isBlankOrNull(feedbackForm.getDisplayType())) {
				feedbackForm.setDisplayType(FeedbackFormConstants.GENERALISED);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward addForm(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "addForm";
		Integer[] permissions = new Integer[2];
		permissions[0] = PermissionConstants.PERMISSION_MASTERS;
		permissions[1] = PermissionConstants.PERMISSION_FEEDBACK_FORM_MASTER;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		try {
			String userId = (String) request.getSession().getAttribute("userId");
			FeedbackForm feedbackForm = (FeedbackForm) actionForm;
			FeedbackFormManager feedbackFormManager = new FeedbackFormManager();
			ArrayList<FeedbackFormFieldData> formFields = (ArrayList<FeedbackFormFieldData>) request.getAttribute("formFields");
			if (formFields == null) {
				FeedbackFormUtils feedbackFormUtils = new FeedbackFormUtils();
				formFields = feedbackFormUtils.constructFormFieldsFromString(feedbackForm.getStrFeedbackForm());
			}
			if (!Utils.isBlankOrNull(feedbackForm.getCopyFromId())) {
				String newTitle = "";
				if (!Utils.isBlankOrNull(feedbackForm.getFeedbackFormTitle())) {
					newTitle = feedbackForm.getFeedbackFormTitle();
				}
				formFields = populateFormFromExistingForm(feedbackForm, feedbackForm.getCopyFromId());
				if (!Utils.isBlankOrNull(newTitle)) {
					feedbackForm.setFeedbackFormTitle(newTitle);
				}
				
			}
			
			if(formFields==null){
				formFields = constructDefaultFormFields(feedbackForm.getDisplayType());
			}
			
			ArrayList<RatingsData> ratings = CommonUtils.getRatings();
			request.setAttribute("ratings", ratings);
			ArrayList<MultipleSelectsData> multipleSelects = CommonUtils.getMultipleSelects();
			request.setAttribute("multipleSelects", multipleSelects);
			request.setAttribute("formFields", formFields);
			if(Utils.isBlankOrNull(feedbackForm.getFeedbackFormId())) {
				String tempId = ""+System.currentTimeMillis();
				request.setAttribute("tempId", tempId);
				FeedbackFormData feedbackFormData = new FeedbackFormData();
				feedbackFormData.setFeedbackFormFields(formFields);
				feedbackFormData.setFeedbackFormTitle(feedbackForm.getFeedbackFormTitle());
				request.getSession(false).setAttribute(tempId, feedbackFormData);				
			}
			String isFeedbackFormUsed=feedbackFormManager.isFeedBackExistForFeedBackForm(feedbackForm.getFeedbackFormId());
			request.setAttribute("isFeedbackFormUsed", isFeedbackFormUsed);
			// add or update on submit
			if (!Utils.isBlankOrNull(feedbackForm.getIsSubmitted())) {				
				if (Utils.isBlankOrNull(feedbackForm.getFeedbackFormId())) {
					// add
					feedbackFormManager.addFeedbackForm(feedbackForm.getFeedbackFormTitle(), feedbackForm.getFeedbackFormDesc(), feedbackForm.getDisplayType(), formFields, userId);
					forward = "closeModalCall";
				} else {
					feedbackFormManager.updateFeedbackForm(feedbackForm.getFeedbackFormId(), feedbackForm.getFeedbackFormTitle(), feedbackForm.getFeedbackFormDesc(), formFields, userId);
					forward = "closeModalCall";
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
		}
		return mapping.findForward(forward);
	}
	
	private ArrayList<FeedbackFormFieldData> constructDefaultFormFields(String displayType) {
		ArrayList<FeedbackFormFieldData> formFields = null;
		try {
			FeedbackFormManager feedbackFormManager = new FeedbackFormManager();
			formFields = feedbackFormManager.getDefaultFeedbackFormFields(displayType);
			for (int i = 0; i < formFields.size(); i++) {
				formFields.get(i).setFeedbackFormFieldRank(i);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return formFields;
	}

	public ActionForward previewFeedbackForm(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "viewreport";
		Integer[] permissions = new Integer[2];
		permissions[0] = PermissionConstants.PERMISSION_MASTERS;
		permissions[1] = PermissionConstants.PERMISSION_FEEDBACK_FORM_MASTER;
		String userId = (String) request.getSession(false).getAttribute("userId");
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		try {
			FeedbackForm feedbackForm = (FeedbackForm) actionForm;
			
			FeedbackFormData feedbackFormData = null; 
				
			FeedbackFormManager feedbackFormManager = new FeedbackFormManager();
			if(!Utils.isBlankOrNull(feedbackForm.getFeedbackFieldId())) {
				feedbackFormData = feedbackFormManager.getFeedbackFormData(feedbackForm.getFeedbackFieldId());
			} else {
				feedbackFormData = (FeedbackFormData) request.getSession(false).getAttribute(request.getParameter("tempId"));				
			}
			
			ArrayList<FeedbackFormFieldData> formFields = feedbackFormData.getFeedbackFormFields();
			
			String ext = ReportUtils.getReportExtension(feedbackForm.getReportFormat());
			String outputFileName = request.getSession(false).getId() + String.valueOf(System.currentTimeMillis()) + ext;
			String jrXMLName = ReportConstants.JRXML_PRINT_FEEDBACK_FORM_REPORT;
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("candidate_name", TPLabels.getLabel("report.print_preview_feedback_form.label.candidate_name"));			
			params.put("position", TPLabels.getLabel("common.position")+" "+TPLabels.getLabel("common.colon"));
			params.put("interviewed_by", TPLabels.getLabel("report.print_preview_feedback_form.label.interviewed_by"));
			params.put("interview_date", TPLabels.getLabel("report.print_preview_feedback_form.label.interview_date"));
			params.put("signature", TPLabels.getLabel("report.print_preview_feedback_form.label.signature"));
			
			List<FeedbackFormView> reportData = new ArrayList<FeedbackFormView>();
			String categoryName = "";
			
			
			RatingsManager ratingsManager = new RatingsManager();
			ArrayList<RatingsData> ratings = ratingsManager.getAllRatingsWithFields();
			
			MultipleSelectsManager multipleSelectsManager = new MultipleSelectsManager();
			ArrayList<MultipleSelectsData> multipleSelects = multipleSelectsManager.getAllMultipleSelectsWithFields();
			
			
			for (int k = 0; formFields != null && k < formFields.size(); k++) {				
				FeedbackFormFieldData fieldData = formFields.get(k);				
				if (FeedbackFormConstants.FIELD_TYPE_CATEGORY.equals(fieldData.getFeedbackFormFieldType())) {
					categoryName = fieldData.getFeedbackFieldTitle();
				} else {
					FeedbackFormView feedbackFormView = new FeedbackFormView();
					feedbackFormView.setFeedbackFormTitle(feedbackFormData.getFeedbackFormTitle());
					feedbackFormView.setFeedbackFormDesc("");
					feedbackFormView.setCategoryName(categoryName);
					String fieldComment = "";
					String ratingId = fieldData.getRatingId();		
					String multipleSelectId = fieldData.getMultipleSelectId();
					String rating = "";
					String multipleSelect = "";
					
					if(FeedbackFormConstants.COMMENT_REQUIRED.equals(fieldData.getFeedbackFormFieldCommentRequired())) { 
						fieldComment = fieldData.getFeedbackFormFieldCommentRequired();
					}
					if(FeedbackFormConstants.GENERALISED.equalsIgnoreCase(fieldData.getFieldDisplayType())) {
						if (!Utils.isBlankOrNull(ratingId)) {
							rating = FeedbackFormUtils.getRatingsCommentConstructed(ratings, ratingId, "");;
						}
						if(!Utils.isBlankOrNull(multipleSelectId)){
							multipleSelect = FeedbackFormUtils.getMultipleSelectsCommentConstructed(multipleSelects, multipleSelectId, "");
						}
						FeedbackFormUtils.setFeedbackFormViewAttributes(fieldData.getFeedbackFieldTitle(), fieldComment, rating, "", "", "", "",multipleSelect, feedbackFormView);							
					} else {
						String[] retVal = new String[2];
				
						if (!Utils.isBlankOrNull(ratingId)) {
							retVal = FeedbackFormUtils.getCompactRatingsDataConstructed(ratings, ratingId, "", feedbackForm.getReportFormat());						
						}else if(!Utils.isBlankOrNull(multipleSelectId)){
							retVal = FeedbackFormUtils.getCompactMultipleSelectsDataConstructed(multipleSelects, multipleSelectId, "", feedbackForm.getReportFormat());
						}
						FeedbackFormUtils.setFeedbackFormViewAttributes(fieldData.getFeedbackFieldTitle(), "", "", retVal[0], retVal[1], fieldComment, fieldData.getFeedbackFieldTitle(), "", feedbackFormView);
					}																	
					reportData.add(feedbackFormView);
				}
			}			
			ReportManager reportManager = new ReportManager();
			String clientIpAddr = getClientIpAddr(request);
			reportManager.generateReport(jrXMLName, outputFileName, params, (ArrayList<FeedbackFormView>) reportData, 
					feedbackForm.getReportFormat(), userId, clientIpAddr);
			request.setAttribute("fileName", outputFileName);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
		}
		return mapping.findForward(forward);
	}
	
	private ArrayList<FeedbackFormFieldData> populateFormFromExistingForm(FeedbackForm feedbackForm, String feedbackFormId) {
		ArrayList<FeedbackFormFieldData> formFields = null;
		try {
			FeedbackFormManager feedbackFormManager = new FeedbackFormManager();
			FeedbackFormData feedbackFormData = feedbackFormManager.getFeedbackFormData(feedbackFormId);
			if (feedbackFormData != null) {
				feedbackForm.setFeedbackFormTitle(feedbackFormData.getFeedbackFormTitle());
				feedbackForm.setFeedbackFormDesc(feedbackFormData.getFeedbackFormHeader());
				feedbackForm.setDisplayType(feedbackFormData.getDisplayType());
				formFields = feedbackFormData.getFeedbackFormFields();
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
		}
		return formFields;
	}

	public ActionForward editFormTitle(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "editFormTitle";
		Integer[] permissions = new Integer[2];
		permissions[0] = PermissionConstants.PERMISSION_MASTERS;
		permissions[1] = PermissionConstants.PERMISSION_FEEDBACK_FORM_MASTER;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		boolean gotoForm = false;
		try {
			FeedbackForm feedbackForm = (FeedbackForm) actionForm;
			if (!Utils.isBlankOrNull(feedbackForm.getIsSubmitted())) {
				ActionErrors errors = new ActionErrors();
				if (Utils.isBlankOrNull(feedbackForm.getFeedbackFormTitle())) {
					errors.add("", new ActionError("feedback_form_edit_title.error.enter_name"));
					saveErrors(request, errors);
				} else {
					gotoForm = true;
				}

			} else if (!Utils.isBlankOrNull(feedbackForm.getIsCancelled())) {
				feedbackForm.setFeedbackFormTitle(feedbackForm.getPrevFeedbackFormTitle());
				gotoForm = true;
			}
			if (gotoForm) {
				feedbackForm.setIsSubmitted("");
				FeedbackFormUtils feedbackFormUtils = new FeedbackFormUtils();
				ArrayList<FeedbackFormFieldData> formFields = feedbackFormUtils.constructFormFieldsFromString(feedbackForm.getStrFeedbackForm());
				request.setAttribute("formFields", formFields);
				return addForm(mapping, actionForm, request, response);
			}
			feedbackForm.setPrevFeedbackFormTitle(feedbackForm.getFeedbackFormTitle());
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward editFormDesc(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "editFormDesc";
		Integer[] permissions = new Integer[2];
		permissions[0] = PermissionConstants.PERMISSION_MASTERS;
		permissions[1] = PermissionConstants.PERMISSION_FEEDBACK_FORM_MASTER;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		boolean gotoForm = false;
		try {
			FeedbackForm feedbackForm = (FeedbackForm) actionForm;
			if (!Utils.isBlankOrNull(feedbackForm.getIsSubmitted())) {
				gotoForm = true;
			} else if (!Utils.isBlankOrNull(feedbackForm.getIsCancelled())) {
				feedbackForm.setFeedbackFormDesc(feedbackForm.getPrevFeedbackFormDesc());
				gotoForm = true;
			}
			if (gotoForm) {
				feedbackForm.setIsSubmitted("");
				FeedbackFormUtils feedbackFormUtils = new FeedbackFormUtils();
				ArrayList<FeedbackFormFieldData> formFields = feedbackFormUtils.constructFormFieldsFromString(feedbackForm.getStrFeedbackForm());
				request.setAttribute("formFields", formFields);
				return addForm(mapping, actionForm, request, response);
			}
			feedbackForm.setPrevFeedbackFormDesc(feedbackForm.getFeedbackFormDesc());
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward addField(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "addField";
		
		Integer[] permissions = new Integer[2];
		permissions[0] = PermissionConstants.PERMISSION_MASTERS;
		permissions[1] = PermissionConstants.PERMISSION_FEEDBACK_FORM_MASTER;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		boolean gotoForm = false;
		ActionErrors errors = new ActionErrors();
		try {
			FeedbackForm feedbackForm = (FeedbackForm) actionForm;

			if (!Utils.isBlankOrNull(feedbackForm.getIsSubmitted())) {

				errors = validateForm(feedbackForm, errors);
				if (errors.size() == 0) {
					FeedbackFormUtils feedbackFormUtils = new FeedbackFormUtils();
					ArrayList<FeedbackFormFieldData> formFields = feedbackFormUtils.constructFormFieldsFromString(feedbackForm.getStrFeedbackForm());
					if(Utils.isBlankOrNull(feedbackForm.getFeedbackFormFieldIndex()) && FeedbackFieldsConstant.FIELD_TYPE_APPLICANT.equals(feedbackForm.getFeedbackFieldType())){
						validateDuplicantApplicantFields(feedbackForm.getApplicantFieldId(),formFields,errors);
					}
					if(errors.size()==0){
						formFields = setFieldData(formFields, feedbackForm.getFeedbackFormFieldIndex(), feedbackForm.getFeedbackFormFieldType(), feedbackForm.getFeedbackFormFieldCategoryId(),
								feedbackForm.getFeedbackFieldId(), feedbackForm.getFeedbackFormFieldCategory(), feedbackForm.getFeedbackFieldTitle(), feedbackForm.getFeedbackFormFieldDesc(), feedbackForm
										.getFeedbackFormFieldRatingRequired(), feedbackForm.getFeedbackFormFieldRatingId(), feedbackForm.getFeedbackFormFieldMultipleSelectRequired(), feedbackForm.getFeedbackFormFieldMultipleSelectId(), feedbackForm.getFeedbackFormFieldCommentRequired(), 
										feedbackForm.getFieldDisplayType(), feedbackForm.getFieldIsMandatory(),feedbackForm.getFeedbackFieldType(),feedbackForm.getApplicantFieldId());
					}else{
						saveErrors(request, errors);
					}
					request.setAttribute("formFields", formFields);
					gotoForm = true;
				}

			} else if (!Utils.isBlankOrNull(feedbackForm.getIsCancelled())) {
				gotoForm = true;
				FeedbackFormUtils feedbackFormUtils = new FeedbackFormUtils();
				ArrayList<FeedbackFormFieldData> formFields = feedbackFormUtils.constructFormFieldsFromString(feedbackForm.getStrFeedbackForm());
				request.setAttribute("formFields", formFields);
			}
			if (gotoForm) {
				feedbackForm.setIsSubmitted("");
				return addForm(mapping, actionForm, request, response);
			}

			if (Utils.isBlankOrNull(feedbackForm.getFeedbackFormFieldIndex())) {
				FeedbackFieldsManager feedbackFieldsManager = new FeedbackFieldsManager();
				ArrayList<FeedbackFieldCategoryData> feedbackFieldCategories = feedbackFieldsManager.getActiveFeedbackCategories(FeedbackFormConstants.SYSTEM_GENERATED);
				ArrayList<String> categoryIds = new ArrayList<String>();
				ArrayList<String> categoryNames = new ArrayList<String>();
				for (int i = 0; feedbackFieldCategories != null && i < feedbackFieldCategories.size(); i++) {
					FeedbackFieldCategoryData feedbackFieldCategoryData = feedbackFieldCategories.get(i);
					categoryIds.add(feedbackFieldCategoryData.getFeedbackFieldCategoryId());
					categoryNames.add(feedbackFieldCategoryData.getFeedbackFieldCategory());
				}
				request.setAttribute("categoryIds", categoryIds);
				request.setAttribute("categoryNames", categoryNames);
			}
			RatingsManager ratingsManager = new RatingsManager();
			ArrayList<RatingsData> ratings = ratingsManager.getAllActiveRatingsWithFields();

			ArrayList<String> ratingIds = new ArrayList<String>();
			ArrayList<String> ratingNames = new ArrayList<String>();
			for (int i = 0; ratings != null && i < ratings.size(); i++) {
				RatingsData ratingsData = ratings.get(i);
				ratingIds.add(ratingsData.getRatingId());
				ratingNames.add(ratingsData.getRatingTitle());
			}
			request.setAttribute("ratingIds", ratingIds);
			request.setAttribute("ratingNames", ratingNames);
			
			MultipleSelectsManager multipleSelectsManager = new MultipleSelectsManager();
			ArrayList<MultipleSelectsData> multipleSelects = multipleSelectsManager.getAllActiveMultipleSelectsWithFields();
			
			ArrayList<String> selectIds = new ArrayList<String>();
			ArrayList<String> selectNames = new ArrayList<String>();
			
			for (MultipleSelectsData multipleSelectsData : multipleSelects) {
				selectIds.add(multipleSelectsData.getSelectId());
				selectNames.add(multipleSelectsData.getSelectTitle());
			}
			request.setAttribute("multipleSelectIds", selectIds);
			request.setAttribute("multipleSelectNames", selectNames);

		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
		}
		saveErrors(request, errors);
		return mapping.findForward(forward);
	}

	private ArrayList<FeedbackFormFieldData> setFieldData(ArrayList<FeedbackFormFieldData> formFields, String feedbackFormFieldIndex, String feedbackFormFieldType, String feedbackFieldCategoryId,
			String feedbackFieldId, String feedbackFormFieldCategory, String feedbackFieldTitle, String feedbackFormFieldDesc, String feedbackFormFieldRatingRequired,
			String feedbackFormFieldRatingId, String feedbackFormFieldMultipleSelectRequired, String feedbackFormFieldMultipleSelectId, String feedbackFormFieldCommentRequired, String fieldDisplayType, String fieldIsMandatory,String feedbackFieldType,String applicantFieldId) {
		if (Utils.isBlankOrNull(feedbackFormFieldRatingRequired) || feedbackFormFieldRatingRequired.equals("-1") || feedbackFormFieldRatingRequired.equals(FeedbackFormConstants.RATING_NOT_REQUIRED)) {
			feedbackFormFieldRatingId = "";
		}
		if (Utils.isBlankOrNull(feedbackFormFieldMultipleSelectRequired) || feedbackFormFieldMultipleSelectRequired.equals("-1") || feedbackFormFieldMultipleSelectRequired.equals(FeedbackFormConstants.RATING_NOT_REQUIRED)) {
			feedbackFormFieldMultipleSelectId = "";
		}
		if (Utils.isBlankOrNull(feedbackFormFieldIndex)) {
			// new field, check if category exist else add new category
			if (formFields == null) {
				formFields = new ArrayList<FeedbackFormFieldData>();
			}
			int fieldIndexToInsert = -1;
			boolean categoryExists = false;
			for (int i = 0; formFields != null && i < formFields.size(); i++) {
				FeedbackFormFieldData feedbackFormFieldData = formFields.get(i);
				if (feedbackFormFieldData.getFeedbackFormFieldType().equals(FeedbackFormConstants.FIELD_TYPE_CATEGORY)
						& feedbackFormFieldData.getFeedbackFieldId().equalsIgnoreCase(feedbackFieldCategoryId)) {
					// category exists
					categoryExists = true;
					// get last index to insert field
					for (int k = i + 1; k < formFields.size(); k++) {
						FeedbackFormFieldData data = formFields.get(k);
						if (data.getFeedbackFormFieldType().equals(FeedbackFormConstants.FIELD_TYPE_CATEGORY)) {
							fieldIndexToInsert = k;

							break;
						}
					}
					break;
				}
			}
			if (!categoryExists) {
				FeedbackFormFieldData feedbackFormFieldData = new FeedbackFormFieldData();
				feedbackFormFieldData.setFeedbackFormFieldId("0");
				feedbackFormFieldData.setFeedbackFieldId(feedbackFieldCategoryId);
				feedbackFormFieldData.setFeedbackFieldTitle(feedbackFormFieldCategory);
				feedbackFormFieldData.setFeedbackFormFieldType(FeedbackFormConstants.FIELD_TYPE_CATEGORY);
				feedbackFormFieldData.setFeedbackFormFieldCommentRequired(FeedbackFormConstants.COMMENT_NOT_REQUIRED);
				feedbackFormFieldData.setFieldDisplayType(FeedbackFormConstants.GENERALISED);
				feedbackFormFieldData.setFieldIsMandatory(FeedbackFormConstants.FIELD_NOT_REQUIRED);
				formFields.add(feedbackFormFieldData);
			}
			FeedbackFormFieldData feedbackFormFieldData = new FeedbackFormFieldData();
			feedbackFormFieldData.setFeedbackFormFieldId("0");
			feedbackFormFieldData.setFeedbackFieldId(feedbackFieldId);
			feedbackFormFieldData.setFeedbackFieldTitle(feedbackFieldTitle);
			feedbackFormFieldData.setFeedbackFormFieldDesc(feedbackFormFieldDesc);
			feedbackFormFieldData.setFeedbackFormFieldType(FeedbackFormConstants.FIELD_TYPE_FIELD);
			feedbackFormFieldData.setFeedbackFormFieldCommentRequired(feedbackFormFieldCommentRequired);
			feedbackFormFieldData.setRatingId(feedbackFormFieldRatingId);
			feedbackFormFieldData.setMultipleSelectId(feedbackFormFieldMultipleSelectId);
			feedbackFormFieldData.setFieldDisplayType(fieldDisplayType);
			feedbackFormFieldData.setFieldIsMandatory(fieldIsMandatory);
			feedbackFormFieldData.setFeedbackFieldType(feedbackFieldType);
			feedbackFormFieldData.setApplicantFieldId(applicantFieldId);
			if (fieldIndexToInsert < 0) {
				formFields.add(feedbackFormFieldData);
			} else {
				formFields.add(fieldIndexToInsert, feedbackFormFieldData);
			}
		} else {
			FeedbackFormFieldData feedbackFormFieldData = formFields.get(Integer.parseInt(feedbackFormFieldIndex));
			if (feedbackFormFieldData.getFeedbackFormFieldType().equals(FeedbackFormConstants.FIELD_TYPE_FIELD)) {
				feedbackFormFieldData.setFeedbackFormFieldDesc(feedbackFormFieldDesc);
				feedbackFormFieldData.setFeedbackFormFieldCommentRequired(feedbackFormFieldCommentRequired);
				feedbackFormFieldData.setRatingId(feedbackFormFieldRatingId);
				feedbackFormFieldData.setMultipleSelectId(feedbackFormFieldMultipleSelectId);
				feedbackFormFieldData.setFieldDisplayType(fieldDisplayType);
				feedbackFormFieldData.setFieldIsMandatory(fieldIsMandatory);
				feedbackFormFieldData.setFeedbackFieldType(feedbackFieldType);
				feedbackFormFieldData.setApplicantFieldId(applicantFieldId);
			}
		}

		return formFields;
	}

	private ActionErrors validateForm(FeedbackForm feedbackForm, ActionErrors errors) {
		if (Utils.isBlankOrNull(feedbackForm.getFeedbackFormFieldIndex())) {
			if (feedbackForm.getFeedbackFormFieldCategoryId().equals("-1")) {
				errors.add("", new ActionError("feedback_form_add_field.error.select_category"));
			}
			if (feedbackForm.getFeedbackFormFieldId().equals("-1")) {
				errors.add("", new ActionError("feedback_form_add_field.error.select_field"));
			}

		}
		if (feedbackForm.getFeedbackFormFieldType().equals(FeedbackFormConstants.FIELD_TYPE_FIELD)) {
			if (!feedbackForm.getFeedbackFormFieldCommentRequired().equals(FeedbackFormConstants.COMMENT_REQUIRED)
					&& !feedbackForm.getFeedbackFormFieldRatingRequired().equals(FeedbackFormConstants.RATING_REQUIRED)
					&& !feedbackForm.getFeedbackFormFieldMultipleSelectRequired().equals(FeedbackFormConstants.MULTIPLE_SELECT_REQUIRED)) {
				errors.add("", new ActionError("feedback_form_add_field.error.select_rating_or_comment_or_multiple_select"));
			}
			if (feedbackForm.getFeedbackFormFieldRatingRequired().equals(FeedbackFormConstants.RATING_REQUIRED)) {
				if (feedbackForm.getFeedbackFormFieldRatingId().equals("-1")) {
					errors.add("", new ActionError("feedback_form_add_field.error.select_rating"));
				}
			}
			if (feedbackForm.getFeedbackFormFieldMultipleSelectRequired().equals(FeedbackFormConstants.MULTIPLE_SELECT_REQUIRED)) {
				if (feedbackForm.getFeedbackFormFieldMultipleSelectId().equals("-1")) {
					errors.add("", new ActionError("feedback_form_add_field.error.select_multiple_select"));
				}
			}
		}
		return errors;
	}

	public ActionForward getCategoryFields(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				FeedbackForm feedbackForm = (FeedbackForm) actionForm;
				String categoryId = feedbackForm.getFeedbackFormFieldCategoryId();
				FeedbackFieldsManager feedbackFieldsManager = new FeedbackFieldsManager();
				ArrayList<FeedbackFieldData> feedbackFields = feedbackFieldsManager.getActiveFeedbackFieldsForCategory(categoryId);

				ArrayList<String> fieldIds = new ArrayList<String>();
				ArrayList<String> fieldNames = new ArrayList<String>();
				for (int i = 0; feedbackFields != null && i < feedbackFields.size(); i++) {
					FeedbackFieldData feedbackFieldData = feedbackFields.get(i);
					fieldIds.add(feedbackFieldData.getFeedbackFieldId());
					fieldNames.add(feedbackFieldData.getFeedbackFieldTitle());
				}
				String jsArray = CommonUtils.getListJavaScriptArray(fieldIds, fieldNames);
				xmlFile = Utils.getXMLForTagName("fields", jsArray);

			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting xml", e);
			xmlFile = Utils.getXMLForError(null);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}

	public ActionForward getCategoryFieldDesc(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				FeedbackForm feedbackForm = (FeedbackForm) actionForm;
				String feedbackFieldId = feedbackForm.getFeedbackFieldId();
				FeedbackFieldsManager feedbackFieldsManager = new FeedbackFieldsManager();
				FeedbackFieldData feedbackFieldData = feedbackFieldsManager.getFeedbackFieldData(feedbackFieldId);
				xmlFile = FeedbackFormUtils.getFeedbacFieldXML(feedbackFieldData);
				//xmlFile = Utils.getXMLForTagName("desc", feedbackFieldData.getFeedbackFieldDesc());
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting xml", e);
			xmlFile = Utils.getXMLForError(null);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}

	public ActionForward checkDeleteFields(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				FeedbackForm feedbackForm = (FeedbackForm) actionForm;
				String feedbackFormFieldIds = feedbackForm.getFeedbackFormFieldId();
				String FeedbackFormFieldIndexes = feedbackForm.getFeedbackFormFieldIndex();
				FeedbackFormManager feedbackFormManager = new FeedbackFormManager();
				int cnt = feedbackFormManager.getTotalCommentsSubmittedForFields(feedbackFormFieldIds);
				if (cnt > 0) {
					xmlFile = Utils.getXMLForError(null);
				} else {
					xmlFile = Utils.getXMLForIds(FeedbackFormFieldIndexes);
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);

		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}
	
	private void validateDuplicantApplicantFields(String applicantFieldId,ArrayList<FeedbackFormFieldData> formFields,ActionErrors errors){
		String applicantFieldName = null;
		if(formFields!=null){
			for (FeedbackFormFieldData feedbackFormFieldData : formFields) {
				if(FeedbackFieldsConstant.FIELD_TYPE_APPLICANT.equals(feedbackFormFieldData.getFeedbackFieldType()))
					if(applicantFieldId.equals(feedbackFormFieldData.getApplicantFieldId())){
						applicantFieldName = ImportConfigurationManager.getFieldTitle(feedbackFormFieldData.getApplicantFieldId());
						errors.add("feedback_form_add_field.error.duplicate_applicant_field", 
							new ActionError("feedback_form_add_field.error.duplicate_applicant_field", feedbackFormFieldData.getFeedbackFieldTitle(),applicantFieldName));
					}
			}
		}
	}

}
