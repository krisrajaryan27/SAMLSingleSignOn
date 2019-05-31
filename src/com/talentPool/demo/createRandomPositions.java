/**
 * 
 */
package com.talentPool.demo;

import java.util.ArrayList;
import java.util.List;

import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.positions.manager.PositionManager;

/**
 * @author Ajeet
 *
 */
public class createRandomPositions {

	public static void main(String[] args) {
		args = new String[1];
		args[0] = "9,10"; //assgin users
		createPosition(args[0], "demo");
	}
	
	public static String createPosition(String assignToUsers, String posName){
		String positionId = "";
		try {
			PositionManager manager = new PositionManager();
			SimpleDataObject position = new SimpleDataObject();
			
			setPositionAttributes(position, posName);
			
			positionId = manager.addNewPosition(position, null,"1", true);
			System.out.println("positionId="+positionId);
//			positionId = "7";

			addPositionSteps(positionId, assignToUsers);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return positionId;
	}

	private static void addPositionSteps(String positionId, String users) {
		try {
			PositionManager manager = new PositionManager();
			SimpleDataObject object = new SimpleDataObject();
			
			//step 2
			String stepLevel = "1";
			String stepName = "Technical Shortlist";
			String feedbackFormId = "2";
			String stepRank = "2";
			setStepAttributes(object, positionId, users, stepName, stepLevel, stepRank, feedbackFormId);
			manager.addOrUpdatePositionStep(object);	
			
			//step 3
			stepLevel = "1";
			stepName = "Technical Interview";
			feedbackFormId = "2";
			stepRank = "3";			
			setStepAttributes(object, positionId, users, stepName, stepLevel, stepRank, feedbackFormId);
			manager.addOrUpdatePositionStep(object);	
			
			//step 4
			stepLevel = "2";
			stepName = "Offer Made";
			feedbackFormId = "2";
			stepRank = "4";			
			setStepAttributes(object, positionId, users, stepName, stepLevel, stepRank, feedbackFormId);
			manager.addOrUpdatePositionStep(object);
			
			//step 5
			stepLevel = "2";
			stepName = "Offer Accepted";
			feedbackFormId = "2";
			stepRank = "5";			
			setStepAttributes(object, positionId, users, stepName, stepLevel, stepRank, feedbackFormId);
			manager.addOrUpdatePositionStep(object);
			
		}catch (Exception e) {
			e.printStackTrace();
		}		
	}

	private static void setStepAttributes(SimpleDataObject object, String positionId,
			String users, String stepName, String stepLevel, String stepRank, String feedbackFormId) {
		 
		 object.setAttribute("scheduledBy", "");
		 object.setAttribute("isInterviewerCanConfirm", "0");
		 object.setAttribute("assignedTo", users);
		 object.setAttribute("isDecisionMakerSameAsAssignedTo", "1");
		 object.setAttribute("stepTitle", stepName);
		 object.setAttribute("stepLevel", stepLevel);
		 object.setAttribute("isNotifyToCandidate", "0");
		 object.setAttribute("feedbackFormId", feedbackFormId);
		 object.setAttribute("isOptional", "0");
		 object.setAttribute("stepRank", stepRank);
		 object.setAttribute("isScheduled", "0");
		 object.setAttribute("isDefault", "0");
		 object.setAttribute("positionId", positionId);
		 object.setAttribute("decisionMaker", users);
		 object.setAttribute("stepId", "0");
		 
		 List<SimpleDataObject> messages = new ArrayList<SimpleDataObject>();
		 SimpleDataObject message = new SimpleDataObject();
		 message.setAttribute("message", "good");
		 message.setAttribute("isDefault", "1");
		 message.setAttribute("messageId", "0");
		 messages.add(message);
		 
		 object.setAttribute("messages", messages);
	}

	private static void setPositionAttributes(SimpleDataObject position, String posName) {
		position.setAttribute("departmentId", "10");
		position.setAttribute("subDepartmentId", "11");
		position.setAttribute("subSubDepartmentId", "-1");
		position.setAttribute("positionCode",  posName + "123");
		position.setAttribute("positionName", posName);
		position.setAttribute("minimumExperience", 2);
		position.setAttribute("maximumExperience", 4);
		position.setAttribute("vacancies", "5");
		position.setAttribute("hireByDate", "16/12/2010");
		position.setAttribute("note", "demo");
		position.setAttribute("degreeId", "1");
		position.setAttribute("branchId", "1");
		position.setAttribute("positionStatus", "1");
		position.setAttribute("requisitionerId", "1");
		position.setAttribute("positionLevel", "1");
		position.setAttribute("positionReferalFees", "10000");
		position.setAttribute("locationId", "10");
		position.setAttribute("requisitionApprovalTemplateId", "1");
		position.setAttribute("responsibilities", "");
		position.setAttribute("requirements", "");
		position.setAttribute("primarySkills", "10");		
	}
	
}
