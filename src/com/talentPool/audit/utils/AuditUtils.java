package com.talentPool.audit.utils;

import java.sql.SQLException;

import com.talentPool.audit.constants.AuditConstants;
import com.talentPool.audit.manager.AuditManager;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.positions.manager.PositionManager;
import com.talentPool.selectionProcess.SelectionProcessConstants;

public class AuditUtils {
	
	public static String generateAuditDesc(String entityField, String auditType,String entityId, String entityType, 
				String userId,String positionId,String positionStepIdFrom,String positionStepIdTo, String sourceIp) throws SQLException {
		AuditManager auditManager = new AuditManager();		
		String userName = auditManager.getUserName(userId);
		String entityName ="";
		String entityTypeStr ="";
		String applicantName = "";
		String positionName = "";
		String movedFromStep = "";
		String movedToStep = "";
		
		entityField = entityField==null ? "" : entityField.toLowerCase();
		
		if(entityType.equalsIgnoreCase(AuditConstants.AUDIT_USER)){
			entityName = auditManager.getUserName(entityId);
			entityTypeStr = TPLabels.getLabel("common.user");
		}else if(entityType.equalsIgnoreCase(AuditConstants.AUDIT_POSITION)){
			entityName = auditManager.getPositionName(entityId);
			entityTypeStr = TPLabels.getLabel("common.position");
		}else if(entityType.equalsIgnoreCase(AuditConstants.AUDIT_REQUISITION)){
			entityName = auditManager.getPositionName(entityId);
			entityTypeStr = TPLabels.getLabel("common.position");
		}else if(entityType.equalsIgnoreCase(AuditConstants.AUDIT_CANDIDATE)){
			entityName = auditManager.getCandidateName(entityId);
			entityTypeStr = TPLabels.getLabel("common.candidate");
		}else if(entityType.equalsIgnoreCase(AuditConstants.AUDIT_ROLE)){
			entityName = auditManager.getRoleName(entityId);
			entityTypeStr = TPLabels.getLabel("common.role");
		}else if(entityType.equalsIgnoreCase(AuditConstants.AUDIT_REPORT)){
			entityName = entityId;
			entityTypeStr = TPLabels.getLabel("common.report");
		}else if(entityType.equalsIgnoreCase(AuditConstants.AUDIT_REPORT_LEVEL)){
			entityName = auditManager.getReportLevelName(entityId);
			entityTypeStr = TPLabels.getLabel("common.report_level");
		}else if(entityType.equalsIgnoreCase(AuditConstants.AUDIT_HIRING_PROGRESS)){
			applicantName = auditManager.getCandidateName(entityId);
			positionName = auditManager.getPositionName(positionId);
			movedFromStep = TPLabels.getLabel("common.shortlisted");
			movedToStep = "";
			PositionManager positionManager = new PositionManager();
			if(!positionStepIdFrom.equalsIgnoreCase("0")){
				movedFromStep = positionManager.getPositionStepName(new Integer(positionStepIdFrom));
			}
			if(!positionStepIdTo.equalsIgnoreCase("0")){
				movedToStep = positionManager.getPositionStepName(new Integer(positionStepIdTo));
			}
			if(movedToStep==null){
				if(positionStepIdTo.equalsIgnoreCase(SelectionProcessConstants.STEP_REJECT)){
					movedToStep = TPLabels.getLabel("common.rejected");
				}else if(positionStepIdTo.equalsIgnoreCase(SelectionProcessConstants.STEP_ON_HOLD)){
					movedToStep = TPLabels.getLabel("common.onhold");
				}else if(positionStepIdTo.equalsIgnoreCase(SelectionProcessConstants.STEP_ATTENDED)){
					movedToStep = TPLabels.getLabel("interaction.label.type.interview")+" "+TPLabels.getLabel("common.attended");
				}else if(positionStepIdTo.equalsIgnoreCase(SelectionProcessConstants.STEP_NOT_ATTENDED)){
					movedToStep = TPLabels.getLabel("viewfeedback.label.result_rejected_na");
				}else if(positionStepIdTo.equalsIgnoreCase(SelectionProcessConstants.STEP_REPEAT)){
					movedToStep = TPLabels.getLabel("viewfeedback.label.result_not_attended_reschedule");
				}else if(positionStepIdTo.equalsIgnoreCase(SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT)){
					movedToStep = TPLabels.getLabel("viewfeedback.label.result_rejected_ni");
				}
			}
		}
		
		String auditDesc= "";
		movedToStep = movedToStep==null ? "" : movedToStep;
		if(auditType.equalsIgnoreCase(AuditConstants.TYPE_LOGGED_IN) || auditType.equalsIgnoreCase(AuditConstants.TYPE_LOGGED_OUT) ){
			auditDesc = TPLabels.getLabel("common.has") +" "+ auditType;
		}else if(auditType.equalsIgnoreCase(AuditConstants.TYPE_HIRING_PROGRESS_MOVED)){
			//auditDesc = TPLabels.getLabel("common.has");
			if(movedFromStep.equalsIgnoreCase(TPLabels.getLabel("common.shortlisted"))){
				auditDesc += TPLabels.getLabel("common.shortlisted");
				auditDesc += " "+entityField.toLowerCase()+" "+applicantName;
				auditDesc += " "+TPLabels.getLabel("common.to")+" "+TPLabels.getLabel("common.step");
				auditDesc += " "+movedToStep;
			}else{
				auditDesc += auditType;
				auditDesc += " "+entityField+" "+applicantName ;
				auditDesc += " "+TPLabels.getLabel("common.from")+" "+movedFromStep;
				auditDesc += " "+TPLabels.getLabel("common.to")+" "+TPLabels.getLabel("common.step")+" "+movedToStep;
			}
			auditDesc += " "+TPLabels.getLabel("common.for") +" "+TPLabels.getLabel("common.position")+" "+positionName;
		}else{
			auditDesc = auditType; 
			auditDesc += " "+ entityField; 
			auditDesc += " "+ TPLabels.getLabel("common.for"); 
			auditDesc += " "+ entityTypeStr.toLowerCase() ;
			auditDesc += " "+ entityName;
		}
		auditDesc = "[" + sourceIp + "] " + userName + " " + auditDesc;
		return auditDesc;
	}
	
	public static String getAuditTypeForRejected(String entityField, String auditType,String entityId, String entityType, 
			String userId,String positionId,String positionStepIdFrom,String positionStepIdTo, String sourceIp) throws SQLException {
	AuditManager auditManager = new AuditManager();		
	String movedFromStep = "";
	String movedToStep = "";
	entityField = entityField==null ? "" : entityField.toLowerCase();
	 if(entityType.equalsIgnoreCase(AuditConstants.AUDIT_HIRING_PROGRESS)){
		movedToStep = "";
		PositionManager positionManager = new PositionManager();
		if(!positionStepIdFrom.equalsIgnoreCase("0")){
			movedFromStep = positionManager.getPositionStepName(new Integer(positionStepIdFrom));
		}
		if(!positionStepIdTo.equalsIgnoreCase("0")){
			movedToStep = positionManager.getPositionStepName(new Integer(positionStepIdTo));
		}
		if(movedToStep==null||movedToStep.isEmpty()){
			if(positionStepIdTo.equalsIgnoreCase(SelectionProcessConstants.STEP_REJECT)){
				auditType=AuditConstants.TYPE_REJECTED;
			}else if(positionStepIdTo.equalsIgnoreCase(SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT)){
				auditType=AuditConstants.TYPE_REJECTED;
			}
		}
	}
	return auditType;
}
}
