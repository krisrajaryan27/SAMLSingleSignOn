package com.talentPool.budget.utils;

import java.io.StringWriter;
import java.util.ArrayList;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.talentPool.budget.BudgetConstants;
import com.talentPool.budget.dataobject.BudgetFilterData;
import com.talentPool.budget.dataobject.BudgetItem;
import com.talentPool.budget.form.BudgetForm;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.xmlutils.XMLWriter;
import com.talentPool.user.manager.PermissionSet;

public class BudgetUtils {
	
	public static boolean isBudgetModuleActive(){
		String budgetModuleStatus = GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_BUDGET_MODULE_STATUS);
		return budgetModuleStatus.equals(GlobalConstants.ENABLED)?true:false;
	}
	
	public static String getXMLforPositionsHome(ArrayList<BudgetItem> budgetItems, String userId, PermissionSet permissionSet) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			wr.startDocument();
			wr.startElement("rows");
			if (budgetItems != null && budgetItems.size() > 0) {
				for (int i = 0; i < budgetItems.size(); i++) {
					BudgetItem sDo = budgetItems.get(i);					
								
					String budgetItemStatus = sDo.getStatus();
					AttributesImpl atr = new AttributesImpl();
					atr.addAttribute("", "id", "", "", String.valueOf(sDo.getBudgetItemId()));
					wr.startElement("", "row", "", atr);
					
					atr = new AttributesImpl();
					atr.addAttribute("", "name", "", "", "budgetItemStatus");
					wr.startElement("", "userdata", "", atr);
					wr.characters(""+budgetItemStatus);
					wr.endElement("userdata");										
					
					String ImgBudgetItemStatus = "";
					if (budgetItemStatus.equals(BudgetConstants.BUDGET_ITEM_STATUS_ACTIVE)) {
						ImgBudgetItemStatus = "<img src=\"images/ico_open_position.gif\" title=\"Active\">";
					} else if (budgetItemStatus.equals(BudgetConstants.BUDGET_ITEM_STATUS_DRAFT)) {
						ImgBudgetItemStatus = "<img src=\"images/ico_onhold_position.gif\" title=\"Inactive\">";
					}
					wr.startElement("cell");
					wr.characters(ImgBudgetItemStatus);
					wr.endElement("cell");
					
					String budgetItemName = ""+sDo.getBudgetItemName();	
					
					atr = new AttributesImpl();
					atr.addAttribute("", "name", "", "", "budgetItemName");
					wr.startElement("", "userdata", "", atr);
					wr.characters(""+budgetItemName);
					wr.endElement("userdata");					
									
					wr.startElement("cell");
					wr.characters(wr.doubleEscape(budgetItemName.length() > 17? budgetItemName.substring(0, 14) + "...": budgetItemName));
					wr.endElement("cell");					
					
					String department = ""+sDo.getDeptName();
					wr.startElement("cell");
					wr.characters(wr.doubleEscape(department.length() > 17? department.substring(0, 14) + "...": department));
					wr.endElement("cell");					
					
					String owner = ""+sDo.getOwnerName();
					wr.startElement("cell");
					wr.characters(wr.doubleEscape(owner.length() > 17? owner.substring(0, 14) + "...": owner));
					wr.endElement("cell");		

					int availableHeadCount = sDo.getAvailableHeadCount()-sDo.getCommittedHeadCount()-sDo.getUsedHeadCount();
					wr.startElement("cell");
					wr.characters(wr.doubleEscape(""+availableHeadCount));
					wr.endElement("cell");		
					
					atr = new AttributesImpl();
					atr.addAttribute("", "name", "", "", "availableHeadCount");
					wr.startElement("", "userdata", "", atr);
					wr.characters(""+availableHeadCount);
					wr.endElement("userdata");		
					
					wr.startElement("cell");
					wr.characters(wr.doubleEscape(""+sDo.getCommittedHeadCount()));
					wr.endElement("cell");	

					wr.startElement("cell");
					wr.characters(wr.doubleEscape(""+sDo.getUsedHeadCount()));
					wr.endElement("cell");				

					wr.startElement("cell");					
					wr.characters(sDo.getStartTimeToDisplay());
					wr.endElement("cell");
					
					wr.startElement("cell");
					wr.characters(sDo.getEndTimeToDisplay());
					wr.endElement("cell");
					
//					String hireByDate = (sDo.getString("expiryDate") == null) ? "" : sDo.getString("expiryDate");
//					
//					wr.startElement("cell");
//					wr.characters(hireByDate);
//					wr.endElement("cell");
//					
//					if(!Utils.isBlankOrNull(hireByDate) && PositionConstants.POSITION_STATUS_OPENED.equals(budgetItemStatus)){
//						Date dt = Utils.convertToDate(hireByDate, Utils.regDDMMMYYFormat);
//						if(dt.before(Calendar.getInstance().getTime())){
//							budgetItemStatus = PositionConstants.POSITION_STATUS_OVERDUE;
//						}
//					}

					wr.endElement("row");
				}
			}
			wr.endElement("rows");
			wr.endDocument();

		} catch (SAXException e) {
			TPLogger.getLogger().error("Error while creating xml for Budget Items", e);
		}
		return sWr.getBuffer().toString();
	}
	
	public static String getXMLForFilter(ArrayList<SimpleDataObject> filters, String filterFor, BudgetFilterData budgetFilterData) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			wr.startDocument();
			wr.startElement("filters");
			String selectedId = "";
			
			if (filterFor.equals(BudgetConstants.FILTER_DEPARTMENT)) {
				selectedId = budgetFilterData.getDeptId();
			}else if (filterFor.equals(BudgetConstants.FILTER_SUB_DEPARTMENT)) {
				selectedId = budgetFilterData.getSubDeptId();
			}else if (filterFor.equals(BudgetConstants.FILTER_SUB_SUB_DEPARTMENT)) {
				selectedId = budgetFilterData.getSubSubDeptId();
			}else if (filterFor.equals(BudgetConstants.FILTER_SUB3_DEPARTMENT)) {
				selectedId = budgetFilterData.getSub3DeptId();
			}else if (filterFor.equals(BudgetConstants.FILTER_SUB4_DEPARTMENT)) {
				selectedId = budgetFilterData.getSub4DeptId();
			}else if (filterFor.equals(BudgetConstants.FILTER_OWNER)) {
				selectedId = budgetFilterData.getOwnerId();
			}else if (filterFor.equals(BudgetConstants.FILTER_POSITION)) {
				selectedId = budgetFilterData.getPositionId();
			}else if (filterFor.equals(BudgetConstants.FILTER_GRADE)) {
				selectedId = budgetFilterData.getGradeId();
			}else if (filterFor.equals(BudgetConstants.FILTER_BAND)) {
				selectedId = budgetFilterData.getBandId();
			}  		
			
			for (int i = 0; filters != null && i < filters.size(); i++) {
				SimpleDataObject sdo = filters.get(i);
				String filterId = sdo.getString("filterId");
				String filterShortName = sdo.getString("filterShortName");
				String filterFullName = sdo.getString("filterFullName");
				StringBuffer sb = new StringBuffer();
				boolean isSelected = false;
				String name = filterShortName;

				if (!Utils.isBlankOrNull(filterId)) {
					if (filterId.equals(selectedId)) {
						isSelected = true;
					}
					if (filterFor.equals(BudgetConstants.FILTER_POSITION) &&
							GlobalConstants.ENABLED.equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SHOW_POSITION_CODE))) {
						name = filterShortName;
					}
					
					filterId = filterId.replaceAll("\"", "&quot;").replaceAll("'", "\\\\'");
					if (isSelected) {
						sb.append("<font class=red>" + wr.doubleEscape(name) + "</font>");
					} else {
						sb.append("<a href=\"#\" onclick=\"applyFilter('" + filterFor + "','" + wr.doubleEscape(filterId) + "');\" class=\"green\">" + wr.doubleEscape(name) + "</a>");
					}
					wr.startElement("filter");

					wr.startElement("link");
					wr.characters(sb.toString());
					wr.endElement("link");

					wr.startElement("title");
					if (filterFor.equals(BudgetConstants.FILTER_POSITION)) {
						wr.characters(wr.doubleEscape(filterFullName + " [" + filterShortName + "]"));
					} else {
						wr.characters(wr.doubleEscape(name));
					}						
					wr.endElement("title");
					wr.endElement("filter");
				}
			}
			wr.endElement("filters");
			wr.endDocument();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return sWr.getBuffer().toString();
	}
	
	public static String getSelectedLink(ArrayList<SimpleDataObject> filters, String filterFor, BudgetFilterData budgetFilterData) {
		String selLink = "";
		try {
			String selectedId = "";
			if (filterFor.equals(BudgetConstants.FILTER_DEPARTMENT)) {
				selectedId = budgetFilterData.getDeptId();
			}else if (filterFor.equals(BudgetConstants.FILTER_SUB_DEPARTMENT)) {
				selectedId = budgetFilterData.getSubDeptId();
			}else if (filterFor.equals(BudgetConstants.FILTER_SUB_SUB_DEPARTMENT)) {
				selectedId = budgetFilterData.getSubSubDeptId();
			}else if (filterFor.equals(BudgetConstants.FILTER_SUB3_DEPARTMENT)) {
				selectedId = budgetFilterData.getSub3DeptId();
			}else if (filterFor.equals(BudgetConstants.FILTER_SUB4_DEPARTMENT)) {
				selectedId = budgetFilterData.getSub4DeptId();
			}else if (filterFor.equals(BudgetConstants.FILTER_OWNER)) {
				selectedId = budgetFilterData.getOwnerId();
			}else if (filterFor.equals(BudgetConstants.FILTER_POSITION)) {
				selectedId = budgetFilterData.getPositionId();
			}else if (filterFor.equals(BudgetConstants.FILTER_STATUS)) {
				selectedId = budgetFilterData.getStatus();
			}else if (filterFor.equals(BudgetConstants.FILTER_GRADE)) {
				selectedId = budgetFilterData.getGradeId();
			}else if (filterFor.equals(BudgetConstants.FILTER_BAND)) {
				selectedId = budgetFilterData.getBandId();
			} 		

			for (int i = 0; filters != null && i < filters.size(); i++) {
				SimpleDataObject sdo = filters.get(i);
				String filterId = sdo.getString("filterId");
				if (!Utils.isBlankOrNull(filterId)) {
					if (filterId.equals(selectedId)) {
						selLink = sdo.getString("filterShortName");
						break;
					}
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return selLink;
	}
	
	public static void populateFormOnBudgetItem(BudgetForm budgetForm, BudgetItem budgetItem){
		budgetItem.setBudgetItemId(budgetForm.getBudgetItemId());
		budgetItem.setBudgetItemName(budgetForm.getBudgetItemName());
		budgetItem.setOwnerId(budgetForm.getOwnerId());
		budgetItem.setDeptId(budgetForm.getDeptId());
		budgetItem.setStartTime(Utils.convertToSQLDate(budgetForm.getStartTime(), Utils.regEUDateFormat));
		budgetItem.setEndTime(Utils.convertToSQLDate(budgetForm.getEndTime(), Utils.regEUDateFormat));
		budgetItem.setAvailableHeadCount(budgetForm.getAvailableHeadCount());
		budgetItem.setCommittedHeadCount(budgetForm.getCommittedHeadCount());
		budgetItem.setUsedHeadCount(budgetForm.getUsedHeadCount());
		budgetItem.setStatus(budgetForm.getStatus());
		budgetItem.setCreatedByUserId(budgetForm.getCreatedByUserId());
		budgetItem.setSubDeptId(budgetForm.getSubDeptId());
		budgetItem.setSubSubDeptId(budgetForm.getSubSubDeptId());
		budgetItem.setSub3DeptId(budgetForm.getSub3DeptId());
		budgetItem.setSub4DeptId(budgetForm.getSub4DeptId());
		budgetItem.setGradeId(budgetForm.getGradeId());
		budgetItem.setBandId(budgetForm.getBandId());
	}
	
	public static void populateBudgetItemOnForm(BudgetItem budgetItem, BudgetForm budgetForm){
		budgetForm.setBudgetItemId(budgetItem.getBudgetItemId());
		budgetForm.setBudgetItemName(budgetItem.getBudgetItemName());
		budgetForm.setOwnerId(budgetItem.getOwnerId());
		budgetForm.setOwnerName(budgetItem.getOwnerName());
		budgetForm.setDeptId(budgetItem.getDeptId());
		budgetForm.setDeptName(budgetItem.getDeptName());		
		budgetForm.setStartTime(Utils.getDateConvertedToString(budgetItem.getStartTime(), Utils.regEUDateFormat));
		budgetForm.setEndTime(Utils.getDateConvertedToString(budgetItem.getEndTime(), Utils.regEUDateFormat));
		budgetForm.setAvailableHeadCount(""+budgetItem.getAvailableHeadCount());
		budgetForm.setCommittedHeadCount(""+budgetItem.getCommittedHeadCount());
		budgetForm.setUsedHeadCount(""+budgetItem.getUsedHeadCount());
		budgetForm.setStatus(budgetItem.getStatus());
		budgetForm.setCreatedByUserId(budgetItem.getCreatedByUserId());
		budgetForm.setCreatedByUserName(budgetItem.getCreatedByUserName());
		budgetForm.setSubDeptId(budgetItem.getSubDeptId());
		budgetForm.setSubDeptName(budgetItem.getSubDeptName());
		budgetForm.setSubSubDeptId(budgetItem.getSubSubDeptId());
		budgetForm.setSubSubDeptName(budgetItem.getSubSubDeptName());
		budgetForm.setSub3DeptId(budgetItem.getSub3DeptId());
		budgetForm.setSub3DeptName(budgetItem.getSub3DeptName());
		budgetForm.setSub4DeptId(budgetItem.getSub4DeptId());
		budgetForm.setSub4DeptName(budgetItem.getSub4DeptName());
		budgetForm.setGradeId(budgetItem.getGradeId());
		budgetForm.setGradeName(budgetItem.getGradeName());
		budgetForm.setBandId(budgetItem.getBandId());	
		budgetForm.setBandName(budgetItem.getBandName());
		budgetForm.setCreationDate(budgetItem.getCreationDate());

	}
	
	public static void populateFormOnBudgetFilterData(BudgetForm budgetForm, BudgetFilterData budgetFilterData){
		budgetFilterData.setBudgetItemName(budgetForm.getBudgetItemName());
		budgetFilterData.setOwnerId(budgetForm.getOwnerId());
		budgetFilterData.setDeptId(budgetForm.getDeptId());
		budgetFilterData.setStatus(budgetForm.getStatus());
		budgetFilterData.setSubDeptId(budgetForm.getSubDeptId());
		budgetFilterData.setSubSubDeptId(budgetForm.getSubSubDeptId());
		budgetFilterData.setSub3DeptId(budgetForm.getSub3DeptId());
		budgetFilterData.setSub4DeptId(budgetForm.getSub4DeptId());
		budgetFilterData.setGradeId(budgetForm.getGradeId());
		budgetFilterData.setBandId(budgetForm.getBandId());
		budgetFilterData.setPositionId(budgetForm.getPositionId());
	}

}
