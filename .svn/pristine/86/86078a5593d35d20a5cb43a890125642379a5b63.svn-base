/**
 * 
 */
package com.talentPool.costs.form;

import java.util.ArrayList;

import com.talentPool.common.base.TPActionForm;
import com.talentPool.common.utils.CommonUtils;
import com.talentPool.costs.dataobject.CostTypeData;
import com.talentPool.costs.manager.CostTypeManager;

/**
 * @author shivprasad
 * 
 */
public class CostForm extends TPActionForm {
	private String costId;
	private String costPaidDate;
	private String amount;
	private String costTypeId;
	private String remarks;
	
	private String sourceId;
	private String subscriptionDateFrom;
	private String subscriptionDateTo;
	
	private String relatedToPosition;
	private String relatedToSource;
	private String relatedToSubscription;
	
	private String submitted;
	
	private ArrayList<String> positionIds;
	private ArrayList<String> positionNames;
	private ArrayList<String> positionIdsSelected;
	private ArrayList<String> positionNamesSelected;
	private String strPositionIds;
	private String listPositions;
	private String listSelPositions;
	
	public String getJSCostTypesArray() {
		CostTypeManager costTypeManager = new CostTypeManager();
		ArrayList<CostTypeData> costTypes = costTypeManager.getCostTypes();
		ArrayList<String> costTypeIds = new ArrayList<String>();
		ArrayList<String> costTypeNames = new ArrayList<String>();
		for(int i=0; costTypes!=null && i<costTypes.size(); i++){
			CostTypeData cData = costTypes.get(i);
			costTypeIds.add(""+cData.getItemId());
			costTypeNames.add(cData.getItemName());
		}
		return CommonUtils.getListJavaScriptArray(costTypeIds, costTypeNames);
	}

	/**
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}

	/**
	 * @return the costPaidDate
	 */
	public String getCostPaidDate() {
		return costPaidDate;
	}

	/**
	 * @param costPaidDate the costPaidDate to set
	 */
	public void setCostPaidDate(String costPaidDate) {
		this.costPaidDate = costPaidDate;
	}

	/**
	 * @return the costTypeId
	 */
	public String getCostTypeId() {
		return costTypeId;
	}

	/**
	 * @param costTypeId the costTypeId to set
	 */
	public void setCostTypeId(String costTypeId) {
		this.costTypeId = costTypeId;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the sourceId
	 */
	public String getSourceId() {
		return sourceId;
	}

	/**
	 * @param sourceId the sourceId to set
	 */
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	/**
	 * @return the subscriptionDateFrom
	 */
	public String getSubscriptionDateFrom() {
		return subscriptionDateFrom;
	}

	/**
	 * @param subscriptionDateFrom the subscriptionDateFrom to set
	 */
	public void setSubscriptionDateFrom(String subscriptionDateFrom) {
		this.subscriptionDateFrom = subscriptionDateFrom;
	}

	/**
	 * @return the subscriptionDateTo
	 */
	public String getSubscriptionDateTo() {
		return subscriptionDateTo;
	}

	/**
	 * @param subscriptionDateTo the subscriptionDateTo to set
	 */
	public void setSubscriptionDateTo(String subscriptionDateTo) {
		this.subscriptionDateTo = subscriptionDateTo;
	}

	/**
	 * @return the relatedToPosition
	 */
	public String getRelatedToPosition() {
		return relatedToPosition;
	}

	/**
	 * @param relatedToPosition the relatedToPosition to set
	 */
	public void setRelatedToPosition(String relatedToPosition) {
		this.relatedToPosition = relatedToPosition;
	}

	/**
	 * @return the relatedToSource
	 */
	public String getRelatedToSource() {
		return relatedToSource;
	}

	/**
	 * @param relatedToSource the relatedToSource to set
	 */
	public void setRelatedToSource(String relatedToSource) {
		this.relatedToSource = relatedToSource;
	}

	/**
	 * @return the relatedToSubscription
	 */
	public String getRelatedToSubscription() {
		return relatedToSubscription;
	}

	/**
	 * @param relatedToSubscription the relatedToSubscription to set
	 */
	public void setRelatedToSubscription(String relatedToSubscription) {
		this.relatedToSubscription = relatedToSubscription;
	}

	/**
	 * @return the costId
	 */
	public String getCostId() {
		return costId;
	}

	/**
	 * @param costId the costId to set
	 */
	public void setCostId(String costId) {
		this.costId = costId;
	}

	/**
	 * @return the submitted
	 */
	public String getSubmitted() {
		return submitted;
	}

	/**
	 * @param submitted the submitted to set
	 */
	public void setSubmitted(String submitted) {
		this.submitted = submitted;
	}

	/**
	 * @return the positionIds
	 */
	public ArrayList<String> getPositionIds() {
		return positionIds;
	}

	/**
	 * @param positionIds the positionIds to set
	 */
	public void setPositionIds(ArrayList<String> positionIds) {
		this.positionIds = positionIds;
	}

	/**
	 * @return the positionIdsSelected
	 */
	public ArrayList<String> getPositionIdsSelected() {
		return positionIdsSelected;
	}

	/**
	 * @param positionIdsSelected the positionIdsSelected to set
	 */
	public void setPositionIdsSelected(ArrayList<String> positionIdsSelected) {
		this.positionIdsSelected = positionIdsSelected;
	}

	/**
	 * @return the positionNames
	 */
	public ArrayList<String> getPositionNames() {
		return positionNames;
	}

	/**
	 * @param positionNames the positionNames to set
	 */
	public void setPositionNames(ArrayList<String> positionNames) {
		this.positionNames = positionNames;
	}

	/**
	 * @return the positionNamesSelected
	 */
	public ArrayList<String> getPositionNamesSelected() {
		return positionNamesSelected;
	}

	/**
	 * @param positionNamesSelected the positionNamesSelected to set
	 */
	public void setPositionNamesSelected(ArrayList<String> positionNamesSelected) {
		this.positionNamesSelected = positionNamesSelected;
	}

	/**
	 * @return the strPositionIds
	 */
	public String getStrPositionIds() {
		return strPositionIds;
	}

	/**
	 * @param strPositionIds the strPositionIds to set
	 */
	public void setStrPositionIds(String strPositionIds) {
		this.strPositionIds = strPositionIds;
	}

	/**
	 * @return the listPositions
	 */
	public String getListPositions() {
		return listPositions;
	}

	/**
	 * @param listPositions the listPositions to set
	 */
	public void setListPositions(String listPositions) {
		this.listPositions = listPositions;
	}

	/**
	 * @return the listSelPositions
	 */
	public String getListSelPositions() {
		return listSelPositions;
	}

	/**
	 * @param listSelPositions the listSelPositions to set
	 */
	public void setListSelPositions(String listSelPositions) {
		this.listSelPositions = listSelPositions;
	}

}
