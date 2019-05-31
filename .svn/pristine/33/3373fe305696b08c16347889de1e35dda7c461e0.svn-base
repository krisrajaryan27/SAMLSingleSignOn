package com.talentPool.customReports.scheduler;

import java.io.File;
import java.io.FileInputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.talentPool.admin.manager.AdminManager;
import com.talentPool.budget.dataobject.BudgetFilterData;
import com.talentPool.budget.dataobject.BudgetItem;
import com.talentPool.budget.manager.BudgetManager;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.DBTransaction;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.utils.FileUtils.FileHandlerUtils;
import com.talentPool.inbox.InboxConstants;
import com.talentPool.inbox.TPMailSender;
import com.talentPool.inbox.dataobject.InboxData;
import com.talentPool.inbox.dataobject.MessageData;
import com.talentPool.inbox.manager.InboxManager;
import com.talentPool.porting.dataobject.FailedStatusObject;
import com.talentPool.porting.manager.UserImportManager;
import com.talentPool.scheduler.SchedulerConstants;
import com.talentPool.scheduler.manager.UserSchedulerManager;
import com.talentPool.user.dataobject.LoginData;
import com.talentPool.user.dataobject.RoleData;
import com.talentPool.user.manager.LoginManager;

import java.util.HashMap;

/**
 * @author SiddharthK
 *
 */
public class UpdateUsersJob implements Job{
	
	private static Logger log = TPLogger.getLogger();
	private static HashMap<String,String> fieldCellMapping = new HashMap<String,String>();
	
	static{
		fieldCellMapping.put(TPLabels.getLabel("add_user.excel.name"), "fld2");
		fieldCellMapping.put(TPLabels.getLabel("add_user.excel.employee_code"), "fld5");
		fieldCellMapping.put(TPLabels.getLabel("add_user.excel.user_name"), "fld0");
		fieldCellMapping.put(TPLabels.getLabel("add_user.excel.department"), "fld9");
		fieldCellMapping.put(TPLabels.getLabel("add_user.excel.sub_department"), "fld10");
		fieldCellMapping.put(TPLabels.getLabel("add_user.excel.sub_sub_department"), "fld11");
		fieldCellMapping.put(TPLabels.getLabel("add_user.excel.sub_sub_sub_department"), "fld12");
		fieldCellMapping.put(TPLabels.getLabel("add_user.excel.email_id"), "fld4");
		fieldCellMapping.put(TPLabels.getLabel("add_user.excel.sub4_department"), "fld13");
		fieldCellMapping.put(TPLabels.getLabel("add_user.excel.location"), "fld8");
	}
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		UserSchedulerManager schedulerManager = new UserSchedulerManager();
		try {
			schedulerManager.updateSchedulerStartTimeAndStatus();
			String status = runAllSchedulers();
			schedulerManager.updateSchedulerEndTimeAndStatus(status);
		} catch (SQLException e) {
			log.error(GlobalConstants.ERROR, e);
		}
	}
	
	private String runAllSchedulers() throws SQLException {
		DBTransaction tran = null;
		DBPreparedQuery dq = null;
		try {
			tran = new DBTransaction();
		
		
			log.info("========= START USER SCHEDULER JOB ============= : " + new Date());
			
			AdminManager adminManager = new AdminManager();
			String filePath = TPApplicationProperties.getProperty("user.file_path");
			File f = new File(filePath);
			InboxManager inboxManager = new InboxManager();
			InboxData inboxData = inboxManager.getCurrentInboxSettings();
			if (f.isDirectory()){
				File[] allFiles =f.listFiles();
				for (int i=0; i<allFiles.length ; i++){
					int j =0;
					ArrayList<String> allFields = new ArrayList<String>();
					try
			        { 
						if((".xlsx".equalsIgnoreCase(FileHandlerUtils.getFileExtention(allFiles[i].getName(), "")) 
								|| ".xls".equalsIgnoreCase(FileHandlerUtils.getFileExtention(allFiles[i].getName(), "")))  
								&& !allFiles[i].getName().substring(0, 2).equals("~$")) {
				            FileInputStream file = new FileInputStream(allFiles[i]);
				 
				            //Create Workbook instance holding reference to .xlsx file
				            XSSFWorkbook workbook = new XSSFWorkbook(file);
				 
				            //Get first/desired sheet from the workbook
				            XSSFSheet sheet = workbook.getSheetAt(0);
				 
				            //Iterate through each rows one by one
				            Iterator<Row> rowIterator = sheet.iterator();
				            List<SimpleDataObject> newEntities = new ArrayList<SimpleDataObject>();
				            List<SimpleDataObject> existingEntities = new ArrayList<SimpleDataObject>();
				            while (rowIterator.hasNext()) 
				            {
				            	 boolean employeeExists = false;
						         String userSourceId = "";
				                Row row = rowIterator.next();
				                //For each row, iterate through all the columns
				                Iterator<Cell> cellIterator = row.cellIterator();
				                 
				                int k =0;
				                SimpleDataObject object = new SimpleDataObject();
				                while (cellIterator.hasNext()) 
				                {
				                    Cell cell = cellIterator.next();
				                    //Check the cell type and format accordingly
				                    String cellValue = "";
				                    switch (cell.getCellType()) 
				                    {
				                        case Cell.CELL_TYPE_NUMERIC:
				                        	cellValue = String.valueOf((int)cell.getNumericCellValue());
				                            break;
				                        case Cell.CELL_TYPE_STRING:
				                            cellValue = cell.getStringCellValue();
				                            break;
				                    }
				                    if (j == 0){
				                    	allFields.add(cellValue);
				                	} else {
				                		if (fieldCellMapping.containsKey(allFields.get(k))){
				                			if ("fld2".equals(fieldCellMapping.get(allFields.get(k)))){
				                				String[] name = cellValue.split(" ");
				                				if (name.length>1){
				                					String firstName = "";
				                					for (int a=0; a<name.length-1;a++){
				                						if (a==0)
				                							firstName = name[a];
				                						else
				                							firstName = firstName + " " + name[a];
				                					}
				                					object.setAttribute("fld2", firstName);
				                					object.setAttribute("fld3", name[name.length-1]);
				                				} else{
				                					object.setAttribute("fld2", name[0]);
				                				}
				                			} else {
				                				object.setAttribute(fieldCellMapping.get(allFields.get(k)), cellValue);
				                				if ("fld5".equals(fieldCellMapping.get(allFields.get(k)))){
				                					userSourceId = adminManager.getSourceIdForEmployeeCode(cellValue);
				            						int count = adminManager.getEmployeeSourceUser("", userSourceId);
				            						if (count > 0) {
				            							employeeExists = true;
				            						}
				                				}
				                			}
				                		}
				                	}
				                    k++;
				                }
				                if (j>0){
					                if (employeeExists){
					                	String userId = adminManager.getEmployeeSourceUserId(userSourceId);
					                	object.setAttribute("userId", userId);
					                	existingEntities.add(object);
					                	
					                }else {
					                	RoleData role = adminManager.getRoleByTitle("Employee");
					                	object.setAttribute("fld17", role.getRoleTitle());
					                	SimpleDataObject sDo = adminManager.getInboxSettings();
					                	if(sDo!=null && !Utils.isBlankOrNull(sDo.getString("inboxDisplayName"))){
					                		object.setAttribute("fld16", sDo.getString("inboxDisplayName"));
					        			}
						                object.setAttribute("fld1", "123123");
						                newEntities.add(object);
					                }
				                }
				                j++;
				            }
				            file.close();
				            UserImportManager userImportManager = new UserImportManager();
				            List<FailedStatusObject>  failedObjects = new ArrayList<FailedStatusObject>();
				            //If data update is also required this function needs to be uncommented and tested
				            if (existingEntities.size()>0){
				            	//failedObjects.addAll(userImportManager.updateData(existingEntities));
		                	}
				            if (newEntities.size()>0){
				            	failedObjects.addAll(userImportManager.saveData(newEntities));
		                	}
				            
				            if (failedObjects.size()>0){
					            String subject = TPLabels.getLabel("add_user.excel.failed_users_subject");
								StringBuffer content = new StringBuffer("Hi </p> ");
								
								for (FailedStatusObject failedObject:failedObjects){
									content.append("Name:: "+failedObject.getName() + " RowNumber:: "+failedObject.getRowNumber() 
											+" FailureReason:: "+failedObject.getErrorMessage() + " </p>  ");
								}
								
								content.append("Regards </p> "+inboxData.getInboxDisplayName());
								
								MessageData messageData = new MessageData();
								messageData.setFrom(inboxData.getInboxDisplayName() + " <" + inboxData.getInboxEmail() + ">");
								messageData.setReadStatus(InboxConstants.INBOX_EMAIL_STATUS_UNREAD);
								messageData.setHtmlBody(content.toString());
								messageData.setSubject(subject);
								
								TPMailSender sender = new TPMailSender();
								LoginManager loginManager = new LoginManager();
								String notificationUserId = TPApplicationProperties.getProperty("user.failed_users_notification_userId");
								if (Utils.isBlankOrNull(notificationUserId)){
									notificationUserId ="1";
								}
								LoginData requestorData = loginManager.getUser(notificationUserId);
								messageData.setTo(requestorData.getEmail());
								try{
									sender.send(messageData, InboxConstants.EMAIL_BODY_HTML, null);
								}catch(Exception e){
									TPLogger.getLogger().error("Failed to send position change notification email to "+requestorData.getEmail(), e);
								}
								
				            }
						}
			        } 
			        catch (Exception e) 
			        {
			            e.printStackTrace();
			            log.error("Error in reading file in update users job.", e);
			        }
					log.info("========= END USER SCHEDULER JOB ============= : " + new Date());
					
				}
			}else{
				
			}
			tran.commit();
			return SchedulerConstants.SCHEDULER_LAST_STATUS_SUCCESS;
		} catch (Exception e) {
			log.error(GlobalConstants.ERROR, e);
			if(tran != null)
				tran.rollback();
			return SchedulerConstants.SCHEDULER_LAST_STATUS_FAIL;
		} finally {
			if(dq != null) {
				if(tran != null)
					dq.releaseTransaction(tran);
				else
					dq.releaseConnection();
			}
		}
	}
	
	private String updateBudgetReportTableAndSendReport(){
		BudgetManager budgetManager = new BudgetManager();
		ArrayList<BudgetItem> currentBudgets =budgetManager.searchBudgetItems(new BudgetFilterData());
		ArrayList<BudgetItem> oldBudgets = fetchOldBudgetItems();
		String diff = findDifference(currentBudgets, oldBudgets);
		updateTempBudgetTable();
		return diff;
	}
	
	private ArrayList<BudgetItem> fetchOldBudgetItems(){
		DBPreparedQuery dq = null;
		ArrayList budgetItems = null;
		try {
			dq = new DBPreparedQuery("dUsersJob_OldBudgetItems");
			budgetItems = dq.getResult();
		} catch (SQLException exep) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, exep);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return budgetItems;
	}
	
	private void updateTempBudgetTable(){
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dUsersJob_DeleteBudgetItems");
			dq.execute();
			
			dq = new DBPreparedQuery("dUpdateUsersJob_InsertBudgetItems");
			dq.setString(1, TPApplicationProperties.getProperty("sap.user.preseparation_status", ""));
			dq.execute();
		} catch (SQLException exep) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, exep);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}
	
	private String findDifference(ArrayList<BudgetItem> currentBudgets, ArrayList<BudgetItem> oldBudgets){
		StringBuffer sb = new StringBuffer();
		if (!Utils.isListEmptyOrNull(currentBudgets)){
			for (BudgetItem bi:currentBudgets){
				boolean isNewBudget = true;
				if (!Utils.isListEmptyOrNull(oldBudgets)){
					for (int i =0; i<oldBudgets.size(); i++){
						if (bi.getBudgetItemId().equalsIgnoreCase(oldBudgets.get(i).getBudgetItemId())){
							isNewBudget = false;
							BudgetItem sDo = oldBudgets.get(i);
							int totalHeadcount = sDo.getAvailableHeadCount();
							int availableHeadcount = sDo.getUsedHeadCount();
							int gapHeadcount = totalHeadcount - availableHeadcount;
							int offeredHeadcount = sDo.getCommittedHeadCount();
							int netGap = gapHeadcount - offeredHeadcount;
							
							int newTotalHeadcount = bi.getAvailableHeadCount();
							int newAvailableHeadcount = bi.getUsedHeadCount();
							int newGapHeadcount = newTotalHeadcount - newAvailableHeadcount;
							int newOfferedHeadcount = bi.getCommittedHeadCount();
							int newNetGap = newGapHeadcount - newOfferedHeadcount;
							
							if ((newTotalHeadcount != totalHeadcount) || (newAvailableHeadcount != availableHeadcount) || newOfferedHeadcount != offeredHeadcount){
								sb.append("Budget Item Name:: "+bi.getBudgetItemName()+ "\r\n");
								sb.append("New Budgeted =" + newTotalHeadcount+" , Old Budgeted =" + totalHeadcount + "\r\n");
								sb.append("New Available =" + newAvailableHeadcount+" , Old Available =" + availableHeadcount + "\r\n");
								sb.append("New Gap =" + newGapHeadcount+" , Old Gap =" + gapHeadcount + "\r\n");
								sb.append("New Offered =" + newOfferedHeadcount+" , Old Offered =" + offeredHeadcount + "\r\n");
								sb.append("New Net Gap =" + newNetGap+" , Old Net Gap =" + netGap + "\r\n \r\n");
							}
							break;
						}
					}
				}
				if (isNewBudget){
					sb.append(bi.getBudgetItemName() + ":: New Budget Item"+ "\r\n \r\n");
				}
			}
		}
		return sb.toString();
	}

	public static void main(String[] agrs) {
		UpdateUsersJob manager = new UpdateUsersJob();
		try {
			manager.runAllSchedulers();
		} catch (Exception e) {
			log.error(GlobalConstants.ERROR,e);
		} 
	}
}
