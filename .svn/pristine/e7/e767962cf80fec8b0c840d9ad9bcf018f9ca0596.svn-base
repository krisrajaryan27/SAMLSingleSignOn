use talentpool;

DELETE FROM tp_recent_searches;

insert into `tp_application_properties` (`application_property`,`application_property_value`) values ( 'show_position_code','0');

alter table `tp_screen_configurations` add column `field_employee_show` char (1)  DEFAULT '1' NOT NULL  COLLATE latin1_swedish_ci  after `field_vendor_mandatory`, add column `field_employee_mandatory` char (1)  DEFAULT '0' NOT NULL  COLLATE latin1_swedish_ci  after `field_employee_show`;

update `tp_screen_configurations` set `field_employee_mandatory` ='1' where `field_id` = 'Name';
update `tp_screen_configurations` set `field_employee_mandatory` ='1' where `field_id` = 'Experience';

insert into `tp_application_properties` (`application_property`,`application_property_value`) values ( 'employee_resume_upload_notification','1');
insert into `tp_application_properties` (`application_property`,`application_property_value`) values ( 'rejection_email_to_employee','1');
  
insert into `tp_templates` (`template_id`,`template_code`,`template_name`,`template_subject_file`,`template_content_file`,`template_auto`,`template_format`,`template_private`,`template_type_id`,`template_is_default`,`template_date_created`,`user_id`) 
values ( NULL,'employeeResumeUploadNotification','Resume Upload by Employee','employeeResumeUploadNotificationSubject.vm','employeeResumeUploadNotificationContent.vm','0','0','0','21','1','2009-07-22 00:00:00','1');

insert into `tp_templates` (`template_id`,`template_code`,`template_name`,`template_subject_file`,`template_content_file`,`template_auto`,`template_format`,`template_private`,`template_type_id`,`template_is_default`,`template_date_created`,`user_id`) 
values ( NULL,'rejectEmailToEmployee','Reject Email To Employee','rejectEmailToEmployeeSubject.vm','rejectEmailToEmployeeContent.vm','0','0','0','22','1','2009-07-22 00:00:00','1');

insert into `tp_template_types` (`template_type_id`,`template_type`) values ( 21,'Employee Resume Upload Notification');
insert into `tp_template_types` (`template_type_id`,`template_type`) values ( 22,'Rejection Email to Employee');

insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values ( '21','2');
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values ( '21','3');
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values ( '21','14');

insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values ( '22','2');
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values ( '22','3');
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values ( '22','14');
  
insert into `tp_template_types` (`template_type_id`,`template_type`) values ( 23,'Duplicate Upload Tried By Employee Notification');

insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values ( '23','12');

insert into `tp_templates` (`template_id`,`template_code`,`template_name`,`template_subject_file`,`template_content_file`,`template_auto`,`template_format`,`template_private`,`template_type_id`,`template_is_default`,`template_date_created`,`user_id`) 
values ( NULL,'duplicateUploadByEmployee','Duplicate upload by employee notification template','duplicateUploadByEmployeeSubject.vm','duplicateUploadByEmployeeContent.vm','0','0','0','23','1','2009-08-03 00:00:00','1');
