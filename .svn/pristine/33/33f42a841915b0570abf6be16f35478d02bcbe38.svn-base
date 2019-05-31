use talentpool;
DELETE FROM tp_recent_searches;

insert into `tp_template_types` (`template_type_id`,`template_type`) values ( '18','Requisition approval notification email to user');
insert into `tp_template_generic_vars` (`template_variable_type`,`template_variable`) values (13,'REQUISITION_STATUS');
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values ( '18','1');
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values ( '18','2');
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values ( '18','13');


insert into `tp_templates` (`template_code`,`template_name`,`template_subject_file`,
`template_content_file`,`template_auto`,`template_format`,`template_private`,
`template_type_id`,`template_is_default`,`template_date_created`,`user_id`) 
values ('requisitionApprovalNotificationToUser','Requisition approval notification email template','requisitionApprovalNotificationToUserSubject.vm',
'requisitionApprovalNotificationToUserContent.vm','0','0','0','18','1',now(),1);

alter table `tp_inbox_emails` add column `email_from_address` varchar (100)   NULL  after `email_from`;

UPDATE `tp_inbox_emails` SET email_from_address=email_from;

delete from `tp_application_properties` where `application_property`='send_requisition_approval_notification';

alter table `tp_applicants` add index `applicant_email1` ( `applicant_email1`, `applicant_email2` );
