use talentpool;

DELETE FROM tp_recent_searches;

alter table tp_degrees  
ADD COLUMN degree_type char(1) NOT NULL default '1';

alter table `tp_applicants` add column `offered_basic` varchar (20)   NULL  after `applicant_designation_offered`,change `offered_ctc` `offered_ctc` varchar (20)   NULL  COLLATE latin1_swedish_ci;

CREATE TABLE `tp_salary_components` (                     
	`salary_component_id` int(11) NOT NULL auto_increment,  
	`salary_component_name` varchar(50) NOT NULL,
	`salary_component_description` varchar(250) NOT NULL,
	`salary_component_type` char(1) NOT NULL,
	PRIMARY KEY  (`salary_component_id`)                    
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `tp_salary_formulae` (                                                                                                  
	`formula_id` int(11) NOT NULL auto_increment,                                                                                      
	`grade_id` int(11) NOT NULL,                                                                                                       
	`salary_component_id` int(11) NOT NULL,                                                                                            
	`max_limit` int(11) default NULL,                                                                                                  
	`variable1` int(11) NOT NULL,                                                                                                      
	`variable1_factor` decimal(10,2) NOT NULL,                                                                                         
	`constant_factor` int(11) NOT NULL default '0',                                                                                    
	`is_adjustable` char(1) NOT NULL,                                                                                                  
	`user_id` bigint(20) NOT NULL,                                                                                                     
	`date_created` datetime NOT NULL,                                                                                                  
	PRIMARY KEY  (`formula_id`),          
        UNIQUE KEY (grade_id,salary_component_id),                                                                                     
	KEY `FK_tp_salary_formula` (`grade_id`),                                                                                           
	KEY `FK_tp_salary_formula_1` (`user_id`),                                                                                          
	KEY `FK_tp_salary_formulae` (`salary_component_id`),                                                                               
	CONSTRAINT `FK_tp_salary_formulae` FOREIGN KEY (`salary_component_id`) REFERENCES `tp_salary_components` (`salary_component_id`),  
	CONSTRAINT `tp_salary_formula_ibfk_1` FOREIGN KEY (`grade_id`) REFERENCES `tp_budget_grades` (`grade_id`),                         
	CONSTRAINT `tp_salary_formula_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `tp_users` (`USER_ID`)                                    
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `tp_announcements` (       
                    `portal` char(10) NOT NULL,           
                    `announcemnts` text,         
                    `user_id` bigint(20) NOT NULL,        
                    `date_modified` datetime NOT NULL     
                  ) ENGINE=InnoDB DEFAULT CHARSET=latin1;   
   
delete from tp_template_generic_vars where template_variable='VENDOR_PUBLISH_FROM_DATE';
delete from tp_template_generic_vars where template_variable='VENDOR_PUBLISH_TO_DATE';

insert into tp_template_generic_vars(template_variable_type, template_variable) values (21, 'VENDOR_PUBLISH_FROM_DATE');
insert into tp_template_generic_vars(template_variable_type, template_variable) values (21, 'VENDOR_PUBLISH_TO_DATE');

delete from tp_template_types where template_type_id=39;
delete from tp_template_vars where template_type_id=39;
delete from tp_templates where template_code='positionOpenNotificationToVendor';
delete from tp_templates where template_code='positionCloseNotificationToVendor';


insert into tp_template_types (template_type_id,template_type) values (39,'Position Related Notification - Vendor');
insert into tp_template_vars (template_type_id, template_variable_type) values (39, '1');
insert into tp_template_vars (template_type_id, template_variable_type) values (39, '18');
insert into tp_template_vars (template_type_id, template_variable_type) values (39, '21');

insert into tp_templates (template_code, template_name, template_subject_file, template_content_file, template_auto, template_format, 
	template_private, template_type_id, template_is_default, template_date_created,template_is_save_as_draft, do_show_save_as_draft_option, user_id ) 
	values ('positionOpenNotificationToVendor', 'Position Open Notification - Vendor', 'positionOpenNotificationToVendorSubject.vm', 
	'positionOpenNotificationToVendorContent.vm', 0, 0, 1, 39, 1, now(), 0, 0, 1);
	
insert into tp_templates (template_code, template_name, template_subject_file, template_content_file, template_auto, template_format, 
	template_private, template_type_id, template_is_default, template_date_created,template_is_save_as_draft, do_show_save_as_draft_option, user_id ) 
	values ('positionCloseNotificationToVendor', 'Position Close Notification - Vendor', 'positionCloseNotificationToVendorSubject.vm', 
	'positionCloseNotificationToVendorContent.vm', 0, 0, 1, 39, 1, now(), 0, 0, 1);

delete from tp_application_properties where application_property='vendor_resume_upload_notification_to_vendor';
insert into tp_application_properties (application_property, application_property_value)
	values ('vendor_resume_upload_notification_to_vendor', 0);
	
delete from tp_template_types where template_type_id=40;
delete from tp_template_vars where template_type_id=40;
delete from tp_templates where template_code='resumeUploadNotificationToVendor';

insert into tp_template_types (template_type_id,template_type) values (40,'Resume Upload Successful Notification - Vendor');
insert into tp_template_vars (template_type_id, template_variable_type) values (40, '1');
insert into tp_template_vars (template_type_id, template_variable_type) values (40, '3');
insert into tp_template_vars (template_type_id, template_variable_type) values (40, '14');

insert into tp_templates (template_code, template_name, template_subject_file, template_content_file, template_auto, template_format, 
	template_private, template_type_id, template_is_default, template_date_created,template_is_save_as_draft, do_show_save_as_draft_option, user_id ) 
	values ('resumeUploadNotificationToVendor', 'Resume Upload Successful Notification - Vendor', 'resumeUploadNotificationToVendorSubject.vm', 
	'resumeUploadNotificationToVendorContent.vm', 0, 0, 0, 40, 1, now(), 0, 0, 1);
	
delete from tp_application_properties where application_property='employee_resume_upload_notification_to_employee';
insert into tp_application_properties (application_property, application_property_value)
	values ('employee_resume_upload_notification_to_employee', 0);
	
delete from tp_template_types where template_type_id=41;
delete from tp_template_vars where template_type_id=41;
delete from tp_templates where template_code='resumeUploadNotificationToEmployee';

insert into tp_template_types (template_type_id,template_type) values (41,'Resume Upload Successful Notification - Employee');
insert into tp_template_vars (template_type_id, template_variable_type) values (41, '1');
insert into tp_template_vars (template_type_id, template_variable_type) values (41, '2');
insert into tp_template_vars (template_type_id, template_variable_type) values (41, '3');
insert into tp_template_vars (template_type_id, template_variable_type) values (41, '14');

insert into tp_templates (template_code, template_name, template_subject_file, template_content_file, template_auto, template_format, 
	template_private, template_type_id, template_is_default, template_date_created,template_is_save_as_draft, do_show_save_as_draft_option, user_id ) 
	values ('resumeUploadNotificationToEmployee', 'Resume Upload Successful Notification - Employee', 'resumeUploadNotificationToEmployeeSubject.vm', 
	'resumeUploadNotificationToEmployeeContent.vm', 0, 0, 0, 41, 1, now(), 0, 0, 1);

delete from tp_template_generic_vars where template_variable='EMPLOYEE_PUBLISH_FROM_DATE';
delete from tp_template_generic_vars where template_variable='EMPLOYEE_PUBLISH_TO_DATE';

insert into tp_template_generic_vars(template_variable_type, template_variable) values (22, 'EMPLOYEE_PUBLISH_FROM_DATE');
insert into tp_template_generic_vars(template_variable_type, template_variable) values (22, 'EMPLOYEE_PUBLISH_TO_DATE');

delete from tp_template_types where template_type_id=42;
delete from tp_template_vars where template_type_id=42;
delete from tp_templates where template_code='positionOpenNotificationToEmployees';
delete from tp_templates where template_code='positionCloseNotificationToEmployees';

insert into tp_template_types (template_type_id,template_type) values (42,'Position Related Notification - Employees');
insert into tp_template_vars (template_type_id, template_variable_type) values (42, '1');
insert into tp_template_vars (template_type_id, template_variable_type) values (42, '18');
insert into tp_template_vars (template_type_id, template_variable_type) values (42, '22');

insert into tp_templates (template_code, template_name, template_subject_file, template_content_file, template_auto, template_format, 
	template_private, template_type_id, template_is_default, template_date_created,template_is_save_as_draft, do_show_save_as_draft_option, user_id ) 
	values ('positionOpenNotificationToEmployees', 'Position Open Notification - Employees', 'positionOpenNotificationToEmployeesSubject.vm', 
	'positionOpenNotificationToEmployeesContent.vm', 0, 0, 1, 42, 1, now(), 0, 0, 1);
	
insert into tp_templates (template_code, template_name, template_subject_file, template_content_file, template_auto, template_format, 
	template_private, template_type_id, template_is_default, template_date_created,template_is_save_as_draft, do_show_save_as_draft_option, user_id ) 
	values ('positionCloseNotificationToEmployees', 'Position Close Notification - Employees', 'positionCloseNotificationToEmployeesSubject.vm', 
	'positionCloseNotificationToEmployeesContent.vm', 0, 0, 1, 42, 1, now(), 0, 0, 1);
	
delete from tp_application_properties where application_property='all_employees_group_email_id';
insert into tp_application_properties (application_property, application_property_value)
	values ('all_employees_group_email_id', '');

delete from tp_application_properties where application_property='send_position_published_notification_to_employees';
insert into tp_application_properties (application_property, application_property_value)
	values ('send_position_published_notification_to_employees', 0);
	
insert into tp_templates (template_code, template_name, template_subject_file, template_content_file, template_auto, template_format, 
	template_private, template_type_id, template_is_default, template_date_created,template_is_save_as_draft, do_show_save_as_draft_option, user_id ) 
	values ('positionPublishedNotificationToEmployees', 'Position Published Notification - Employees', 'positionPublishedNotificationToEmployeesSubject.vm', 
	'positionPublishedNotificationToEmployeesContent.vm', 0, 0, 0, 42, 1, now(), 0, 0, 1);
	
delete from tp_application_properties where application_property='send_position_change_notification';
insert into tp_application_properties (application_property, application_property_value)
	values ('send_position_change_notification', 0);

UPDATE tp_permissions SET permission_rank = permission_rank + 1
WHERE permission_rank > 68;

INSERT INTO tp_permissions
(permission_id, permission_rank, permission_desc, parent_id, is_permission)
VALUES (83, 69, 'Multiple Select Master', 52, 1);

INSERT INTO tp_role_permissions (role_id, permission_id)
VALUES (1,83);

INSERT INTO tp_user_permissions (user_id, permission_id)
SELECT USER_ID, 83 FROM tp_user_roles WHERE ROLE_ID IN (1);

UPDATE tp_permissions SET permission_rank = permission_rank + 1
WHERE permission_rank > 72;

INSERT INTO tp_permissions
(permission_id, permission_rank, permission_desc, parent_id, is_permission)
VALUES (84, 73, 'Manage Salary Calculations', 52, 1);

INSERT INTO tp_role_permissions (role_id, permission_id)
VALUES (1,84);

INSERT INTO tp_user_permissions (user_id, permission_id)
SELECT USER_ID, 84 FROM tp_user_roles WHERE ROLE_ID IN (1);

UPDATE tp_permissions SET permission_rank = permission_rank + 1
WHERE permission_rank > 48;

INSERT INTO tp_permissions
(permission_id, permission_rank, permission_desc, parent_id, is_permission)
VALUES (85, 49, 'View Salary Calculations', 43, 1);

INSERT INTO tp_role_permissions (role_id, permission_id)
VALUES (1,85);

INSERT INTO tp_user_permissions
(user_id, permission_id)
SELECT USER_ID, 85 FROM tp_user_roles WHERE ROLE_ID IN (1);

CREATE TABLE `tp_salary_rounding` (
	`component_name` varchar(25) NOT NULL,               
	`rounding_type` varchar(10) NOT NULL               
)ENGINE=InnoDB DEFAULT CHARSET=latin1;
INSERT INTO tp_salary_rounding (component_name,rounding_type) VALUES ('CTC','0');
insert into `tp_announcements` (`portal`,`announcemnts`,`user_id`,`date_modified`) 
values ( 'ea',NULL,'1',now());
