use talentpool;

DELETE FROM tp_recent_searches;

update tp_template_generic_vars set template_variable='POSITION_TITLE' where template_variable='POS_DESC_TITLE';

update tp_template_generic_vars set template_variable='POSITION_VACANCIES' where template_variable='POS_DESC_VACANCIES';

update tp_template_generic_vars set template_variable='POSITION_HIRE_BY_DATE' where template_variable='POS_DESC_HIRE_BY_DATE';

alter table tp_positions add column `grade_id` bigint(20) DEFAULT '0' NULL after `employee_publish_from_date`, add column `band_id` bigint(20) DEFAULT '0' NULL after `grade_id`;

alter table tp_budget_grades add column `hire_by_duration` int(11) NOT NULL after `grade_desc`;

insert into tp_application_properties(`application_property`,`application_property_value`) values ( 'overdue_duration_for_requisition_approval_notification','5');

insert into tp_template_generic_vars (template_variable_type, template_variable) 
values (19, 'REQUISITION_APPROVAL_STEP_NAME');

insert into tp_template_generic_vars (template_variable_type, template_variable) 
values (19, 'REQUISITION_APPROVAL_STEP_USER');

insert into tp_template_generic_vars (template_variable_type, template_variable) 
values (18, 'POSITION_REQUESTED_BY');

insert into tp_template_types (template_type_id,template_type) values (35,'Requisition escalation Notification To Requestor');

insert into tp_template_vars (template_type_id, template_variable_type) values (35, '18');

insert into tp_template_vars (template_type_id, template_variable_type) values (35, '19');

insert into tp_templates (template_code, template_name, template_subject_file, template_content_file, template_auto, template_format, 
	template_private, template_type_id, template_is_default, template_date_created,template_is_save_as_draft, do_show_save_as_draft_option, user_id ) 
	values ('overdueRequisitionApprovalNotificationToRequestor', 'Requisition escalation Notification To Requestor', 'overdueRequisitionApprovalNotificationToRequestorSubject.vm', 
	'overdueRequisitionApprovalNotificationToRequestorContent.vm', 0, 0, 0, 35, 1, now(), 0, 0, 1);
	
	