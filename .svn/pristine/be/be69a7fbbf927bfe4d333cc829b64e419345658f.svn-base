/**
 * Copyright 2009 - Nitman Software Pvt. Ltd. All Rights Reserved. 
 * This software is the proprietary information of Nitman Software Pvt. Ltd. 
 * Use is subject to license terms.
 */
package com.talentPool.salaryStructure.utils;

import static com.talentPool.common.CommonConstants.DEFAULT_DELIMITER;
import static com.talentPool.salaryStructure.constants.OfferProposalConstants.OFFER_PROPOSAL_ACTION_APPROVE;
import static com.talentPool.salaryStructure.constants.OfferProposalConstants.OFFER_PROPOSAL_ACTION_REJECT;
import static com.talentPool.salaryStructure.constants.OfferProposalConstants.OFFER_PROPOSAL_ACTION_SUBMIT_FOR_APPROVAL;

import com.talentPool.common.CommonConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.Utils;
import com.talentPool.selectionProcess.SelectionProcessConstants;

/**
 * @author PraveenK
 * @since  May 24, 2012
 */
public class OfferProposalUtils {
	
	private OfferProposalUtils(){}
	
	public static String getOfferProposalActionJSArray(){
		StringBuilder sb = new StringBuilder();
		try {
			sb.append("[");
			Utils.getJSArraySelectOption(OFFER_PROPOSAL_ACTION_SUBMIT_FOR_APPROVAL, TPLabels.getLabel("offer_proposal.label.action_submit_for_approval"), sb);
			Utils.getJSArraySelectOption(OFFER_PROPOSAL_ACTION_APPROVE, TPLabels.getLabel("offer_proposal.label.action_approve"), sb.append(DEFAULT_DELIMITER));
			Utils.getJSArraySelectOption(OFFER_PROPOSAL_ACTION_REJECT, TPLabels.getLabel("offer_proposal.label.action_reject"), sb.append(DEFAULT_DELIMITER));
			sb.append("]");			
		} catch (Exception e) {
			sb = new StringBuilder(CommonConstants.NEW_ARRAY);
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return sb.toString();
	}
	
	public static String getOfferProposalActionTitle(int action){
		String title = null;
		switch (action) {
		case OFFER_PROPOSAL_ACTION_SUBMIT_FOR_APPROVAL:
			title = TPLabels.getLabel("offer_proposal.title.action_submited_for_approval");
			break;
		case OFFER_PROPOSAL_ACTION_APPROVE:
			title = TPLabels.getLabel("offer_proposal.title.action_approved");
			break;
		case OFFER_PROPOSAL_ACTION_REJECT:
			title = TPLabels.getLabel("offer_proposal.title.action_rejected");
			break;
		default:
			title = SelectionProcessConstants.INTERACTION_TYPES.get(""+SelectionProcessConstants.INTERACTION_OFFER_PROPOSAL);
			break;
		}
		return title;
	}
}
