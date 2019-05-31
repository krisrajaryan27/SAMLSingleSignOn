use talentpool;

DELETE FROM tp_recent_searches;

CREATE TABLE `tp_saved_searches` (                
  `search_id` bigint(20) NOT NULL auto_increment,  
  `user_id` bigint(20) default NULL,            
  `search_name` varchar(250) NOT NULL,                   
  `shared` char(1) NOT NULL default '0',          
  `date_searched` timestamp NULL default NULL,     
  `search_data` blob,                              
  PRIMARY KEY  (`search_id`)                       
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `tp_user_hierarchy` (                                                                 
     `org_id` int(11) NOT NULL auto_increment,                                                        
     `user_id` bigint(20) NOT NULL,                                                                   
     `parent_id` int(11) NOT NULL,                                                                    
     `date_modified` date NOT NULL,                                                                   
     PRIMARY KEY  (`org_id`),                                                                         
     KEY `FK_tp_user_hierarchy` (`user_id`),                                                          
     CONSTRAINT `tp_user_hierarchy_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `tp_users` (`USER_ID`)  
   ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
   
 INSERT tp_user_hierarchy (user_id, parent_id,date_modified)
SELECT user_id,0,now() FROM tp_users WHERE user_status!=0;

UPDATE tp_permissions
SET permission_rank = permission_rank + 1
WHERE permission_rank > 11;

INSERT INTO tp_permissions
(permission_id, permission_rank, permission_desc, parent_id, is_permission)
VALUES
(76, 12, 'Manage User Hierarchy', 10, 1);

INSERT INTO tp_role_permissions
(role_id, permission_id)
VALUES
(1,76);

INSERT INTO tp_user_permissions
(user_id, permission_id)
SELECT USER_ID, 76
FROM tp_user_roles
WHERE ROLE_ID IN (1);

CREATE TABLE `tp_audit_entries` (                 
        `audit_id` bigint(20) NOT NULL auto_increment,  
        `audit_desc` varchar(250) NOT NULL default '',  
        `audit_type` varchar(50) NOT NULL,              
        `entity_id` bigint(20) default NULL,            
        `entity_type` char(2) default '',               
        `entity_field` varchar(100) default '',         
        `user_id` bigint(20) NOT NULL,                  
        `date_created` datetime NOT NULL,               
        PRIMARY KEY  (`audit_id`)                       
      ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `tp_position_branches` (                                                                        
	`position_id` bigint(20) NOT NULL,                                                                         
	`branch_id` int(11) NOT NULL,                                                                              
	KEY `FK_tp_position_branches` (`branch_id`),                                                               
	CONSTRAINT `tp_position_branches_ibfk_1` FOREIGN KEY (`branch_id`) REFERENCES `tp_branches` (`branch_id`)  
      ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `tp_position_degrees` (                                                                       
       `position_id` bigint(20) NOT NULL,                                                                       
       `degree_id` smallint(6) NOT NULL,                                                                        
       KEY `FK_tp_position_degrees` (`degree_id`),                                                              
       CONSTRAINT `tp_position_degrees_ibfk_1` FOREIGN KEY (`degree_id`) REFERENCES `tp_degrees` (`degree_id`)  
     ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
   
INSERT INTO tp_position_branches (position_id,branch_id)
SELECT position_id, branch_id FROM tp_positions WHERE branch_id is not NULL;

INSERT INTO tp_position_degrees (position_id,degree_id)
SELECT position_id, degree_id FROM tp_positions;

alter table `tp_positions` drop foreign key `tp_positions_ibfk_2`;
alter table `tp_positions` drop foreign key `tp_positions_ibfk_3`;
alter table `tp_positions` drop column `degree_id`, drop column `branch_id`;

Delete from tp_template_types where template_type_id=27;
delete from tp_template_vars where template_type_id=27;
delete from tp_templates where template_code='progressEmailToEmployee';
delete from tp_application_properties where application_property='progress_email_to_employee';

insert into tp_template_types (template_type_id,template_type) values (27,'Candidate Progress Email to Employee');
insert into tp_template_vars (template_type_id, template_variable_type) values (27, '2');
insert into tp_template_vars (template_type_id, template_variable_type) values (27, '3');
insert into tp_template_vars (template_type_id, template_variable_type) values (27, '14');
insert into tp_template_vars (template_type_id, template_variable_type) values (27, '15');
insert into tp_templates (template_code, template_name, template_subject_file, template_content_file, template_auto, template_format, 
	template_private, template_type_id, template_is_default, template_date_created,template_is_save_as_draft, do_show_save_as_draft_option, user_id ) 
	values ('progressEmailToEmployee', 'Candidate Progress Email To Employee', 'progressEmailToEmployeeSubject.vm', 
	'progressEmailToEmployeeContent.vm', 0, 0, 0, 27, 1, now(), 0, 0, 1);

insert into tp_application_properties (application_property, application_property_value)
	values ('progress_email_to_employee', 0);
	
alter table tp_positions 
add column vendor_publish_from_date date  NULL  ,
add column vendor_publish_to_date date  NULL ;

alter table tp_positions 
add column employee_publish_from_date date  NULL ,
add column employee_publish_to_date date  NULL ;

alter table tp_positions 
add column walk_in_publish_from_date date  NULL ,
add column walk_in_publish_to_date date  NULL ;

alter table tp_positions 
add column website_publish_from_date date  NULL ,
add column website_publish_to_date date  NULL ;



delete from tp_template_generic_vars where template_variable_type=15;
insert into 
	tp_template_generic_vars (template_variable_type, template_variable) 
values
	(15, 'EMPLOYEE_NAME');
insert into 
	tp_template_generic_vars (template_variable_type, template_variable) 
values
	(15, 'EMPLOYEE_EMAIL');
insert into 
	tp_template_generic_vars (template_variable_type, template_variable) 
values
	(15, 'EMPLOYEE_CODE');
insert into 
	tp_template_generic_vars (template_variable_type, template_variable) 
values
	(15, 'EMPLOYEE_PHONE');
insert into 
	tp_template_generic_vars (template_variable_type, template_variable) 
values
	(15, 'EMPLOYEE_MOBILE');

delete from tp_template_types where template_type_id=28;
delete from tp_template_vars where template_type_id=28;
delete from tp_templates where template_code='joinedEmailToEmployee';
delete from tp_application_properties where application_property='joined_email_to_employee';
delete from tp_template_vars where template_type_id=22 and template_variable_type='15';
delete from tp_template_vars where template_type_id=28 and template_variable_type='1';

insert into tp_template_types (template_type_id,template_type) values (28,'Candidate Joined Email to Employee');
insert into tp_template_vars (template_type_id, template_variable_type) values (28, '2');
insert into tp_template_vars (template_type_id, template_variable_type) values (28, '3');
insert into tp_template_vars (template_type_id, template_variable_type) values (28, '14');
insert into tp_template_vars (template_type_id, template_variable_type) values (28, '15');
insert into tp_templates (template_code, template_name, template_subject_file, template_content_file, template_auto, template_format, 
	template_private, template_type_id, template_is_default, template_date_created,template_is_save_as_draft, do_show_save_as_draft_option, user_id ) 
	values ('joinedEmailToEmployee', 'Candidate Joined Email To Employee', 'joinedEmailToEmployeeSubject.vm', 
	'joinedEmailToEmployeeContent.vm', 0, 0, 0, 28, 1, now(), 0, 0, 1);

insert into tp_application_properties (application_property, application_property_value)
	values ('joined_email_to_employee', 0);
insert into tp_template_vars (template_type_id, template_variable_type) values (22, '15');
insert into tp_template_vars (template_type_id, template_variable_type) values (28, '1');

delete from tp_application_properties where application_property='validate_ctc_as_numeric';
insert into tp_application_properties (application_property, application_property_value)
	values ('validate_ctc_as_numeric', 0);
	
UPDATE tp_permissions
SET permission_rank = permission_rank + 2
WHERE permission_rank > 6;

INSERT INTO tp_permissions
(permission_id, permission_rank, permission_desc, parent_id, is_permission)
VALUES
(7, 7, 'Can own budget item', 0, 1);

INSERT INTO tp_permissions
(permission_id, permission_rank, permission_desc, parent_id, is_permission)
VALUES
(8, 8, 'Can not own budget item', 0, 1);

INSERT INTO tp_permissions
(permission_id, permission_rank, permission_desc, parent_id, is_permission)
VALUES
(77, 75, 'Budget', 0, 1);

INSERT INTO tp_permissions
(permission_id, permission_rank, permission_desc, parent_id, is_permission)
VALUES
(78, 76, 'View', 77, 1);

INSERT INTO tp_permissions
(permission_id, permission_rank, permission_desc, parent_id, is_permission)
VALUES
(79, 77, 'Edit', 77, 1);

INSERT INTO tp_permissions
(permission_id, permission_rank, permission_desc, parent_id, is_permission)
VALUES
(80, 78, 'Add', 77, 1);

INSERT INTO tp_permissions
(permission_id, permission_rank, permission_desc, parent_id, is_permission)
VALUES
(81, 79, 'Delete', 77, 1);

INSERT INTO tp_permission_module
(permission_id, module_id)
VALUES (7, 24);

INSERT INTO tp_permission_module
(permission_id, module_id)
VALUES (8, 24);

INSERT INTO tp_permission_module
(permission_id, module_id)
VALUES (77, 24);

INSERT INTO tp_permission_module
(permission_id, module_id)
VALUES (78, 24);

INSERT INTO tp_permission_module
(permission_id, module_id)
VALUES (79, 24);

INSERT INTO tp_permission_module
(permission_id, module_id)
VALUES (80, 24);

INSERT INTO tp_permission_module
(permission_id, module_id)
VALUES (81, 24);

INSERT INTO tp_role_permissions
(role_id, permission_id)
VALUES
(6,7);

INSERT INTO tp_role_permissions
(role_id, permission_id)
VALUES
(1,7);

INSERT INTO tp_user_permissions
(user_id, permission_id)
SELECT USER_ID, 7
FROM tp_user_roles
WHERE ROLE_ID IN (6);

INSERT INTO tp_user_permissions
(user_id, permission_id)
SELECT USER_ID, 7
FROM tp_user_roles
WHERE ROLE_ID IN (1);

INSERT INTO tp_role_permissions
(role_id, permission_id)
VALUES
(1,77);

INSERT INTO tp_user_permissions
(user_id, permission_id)
SELECT USER_ID, 77
FROM tp_user_roles
WHERE ROLE_ID IN (1);

INSERT INTO tp_role_permissions
(role_id, permission_id)
VALUES
(1,78);

INSERT INTO tp_user_permissions
(user_id, permission_id)
SELECT USER_ID, 78
FROM tp_user_roles
WHERE ROLE_ID IN (1);

INSERT INTO tp_role_permissions
(role_id, permission_id)
VALUES
(1,79);

INSERT INTO tp_user_permissions
(user_id, permission_id)
SELECT USER_ID, 79
FROM tp_user_roles
WHERE ROLE_ID IN (1);

INSERT INTO tp_role_permissions
(role_id, permission_id)
VALUES
(1,80);

INSERT INTO tp_user_permissions
(user_id, permission_id)
SELECT USER_ID, 80
FROM tp_user_roles
WHERE ROLE_ID IN (1);

INSERT INTO tp_role_permissions
(role_id, permission_id)
VALUES
(1,81);

INSERT INTO tp_user_permissions
(user_id, permission_id)
SELECT USER_ID, 81
FROM tp_user_roles
WHERE ROLE_ID IN (1);

INSERT INTO tp_role_permissions
(role_id, permission_id)
VALUES
(6,77);

INSERT INTO tp_user_permissions
(user_id, permission_id)
SELECT USER_ID, 77
FROM tp_user_roles
WHERE ROLE_ID IN (6);

INSERT INTO tp_role_permissions
(role_id, permission_id)
VALUES
(6,78);

INSERT INTO tp_user_permissions
(user_id, permission_id)
SELECT USER_ID, 78
FROM tp_user_roles
WHERE ROLE_ID IN (6);

INSERT INTO tp_role_permissions
(role_id, permission_id)
VALUES
(6,79);

INSERT INTO tp_user_permissions
(user_id, permission_id)
SELECT USER_ID, 79
FROM tp_user_roles
WHERE ROLE_ID IN (6);

INSERT INTO tp_role_permissions
(role_id, permission_id)
VALUES
(6,80);

INSERT INTO tp_user_permissions
(user_id, permission_id)
SELECT USER_ID, 80
FROM tp_user_roles
WHERE ROLE_ID IN (6);

INSERT INTO tp_role_permissions
(role_id, permission_id)
VALUES
(6,81);

INSERT INTO tp_user_permissions
(user_id, permission_id)
SELECT USER_ID, 81
FROM tp_user_roles
WHERE ROLE_ID IN (6);

UPDATE tp_permissions
SET permission_rank = permission_rank + 1
WHERE permission_rank > 61;

INSERT INTO tp_permissions
(permission_id, permission_rank, permission_desc, parent_id, is_permission)
VALUES
(82, 62, 'Budget Grade/Band Master', 52, 1);

INSERT INTO tp_permission_module
(permission_id, module_id)
VALUES (82, 24);

INSERT INTO tp_role_permissions
(role_id, permission_id)
VALUES
(1,82);

INSERT INTO tp_user_permissions
(user_id, permission_id)
SELECT USER_ID, 82
FROM tp_user_roles
WHERE ROLE_ID IN (1);

INSERT INTO tp_application_properties
(application_property, application_property_value)
VALUES ('budget_module_status', 0);

INSERT INTO tp_application_properties
(application_property, application_property_value)
VALUES ('budget_mode', 0);

INSERT INTO tp_application_properties
(application_property, application_property_value)
VALUES ('budget_item_grade_label', 'Grade');

INSERT INTO tp_application_properties
(application_property, application_property_value)
VALUES ('budget_item_band_label', 'Band');

CREATE TABLE `tp_budget_grades` (                                                                 
     `grade_id` int(11) NOT NULL auto_increment,   
	 `grade_name` varchar(250) NOT NULL,   	 
	 `grade_desc` varchar(250) NOT NULL,                                                                
     PRIMARY KEY  (`grade_id`)
   ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
   
CREATE TABLE `tp_budget_bands` (                                                                 
     `band_id` int(11) NOT NULL auto_increment,   
	 `band_name` varchar(250) NOT NULL,   	 
	 `band_desc` varchar(250) NOT NULL,                                                                
     PRIMARY KEY  (`band_id`)
   ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
   

CREATE TABLE `tp_budget_items` (                                                                            
           `budget_item_id` bigint(20) NOT NULL auto_increment,                                                      
           `budget_item_name` varchar(250) NOT NULL,                                                                 
           `owner_id` bigint(20) default NULL,                                                                       
           `start_time` date NOT NULL,                                                                               
           `end_time` date NOT NULL,                                                                                 
           `dept_id` bigint(20) default NULL,                                                                        
           `sub_dept_id` bigint(20) default NULL,                                                                    
           `sub_sub_dept_id` bigint(20) default NULL,                                                                
           `grade_id` int(11) default NULL,                                                                          
           `band_id` int(11) default NULL,                                                                           
           `head_count` bigint(20) NOT NULL,                                                                         
           `status` char(1) NOT NULL default '0',                                                                    
           `user_id` bigint(20) default NULL,                                                                        
           `date_created` datetime default NULL,                                                                     
           PRIMARY KEY  (`budget_item_id`),                                                                          
           KEY `FK_tp_budget_items` (`band_id`),                                                                     
           KEY `FK_tp_budget_items_1` (`grade_id`),                                                                  
           CONSTRAINT `tp_budget_items_ibfk_2` FOREIGN KEY (`grade_id`) REFERENCES `tp_budget_grades` (`grade_id`),  
           CONSTRAINT `tp_budget_items_ibfk_1` FOREIGN KEY (`band_id`) REFERENCES `tp_budget_bands` (`band_id`)      
         ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

alter table `tp_positions` add column `budget_item_id` bigint NULL after `vendor_publish_from_date`;
alter table `tp_positions` add column `is_budget_committed` int(1) DEFAULT '1' NULL after `budget_item_id`;

ALTER TABLE `tp_applicant_joining_history` ADD COLUMN `budget_item_id` bigint(20) NULL AFTER `date_created`;

alter table `tp_todo` add column `budget_item_id` bigint (20) NULL after `applicant_id`;

INSERT INTO tp_template_generic_vars
(template_variable_type, template_variable)
VALUES (16, 'BUDGET_ITEM_NAME');

INSERT INTO tp_template_generic_vars
(template_variable_type, template_variable)
VALUES (16, 'BUDGET_OWNER_NAME');

INSERT INTO tp_template_generic_vars
(template_variable_type, template_variable)
VALUES (16, 'BUDGET_DEPT_NAME');

INSERT INTO tp_template_generic_vars
(template_variable_type, template_variable)
VALUES (16, 'BUDGET_SUB_DEPT_NAME');

INSERT INTO tp_template_generic_vars
(template_variable_type, template_variable)
VALUES (16, 'BUDGET_SUB_SUB_DEPT_NAME');

INSERT INTO tp_template_generic_vars
(template_variable_type, template_variable)
VALUES (16, 'BUDGET_GRADE');

INSERT INTO tp_template_generic_vars
(template_variable_type, template_variable)
VALUES (16, 'BUDGET_BAND');

INSERT INTO tp_template_generic_vars
(template_variable_type, template_variable)
VALUES (16, 'BUDGET_HEAD_COUNT');

INSERT INTO tp_template_generic_vars
(template_variable_type, template_variable)
VALUES (16, 'BUDGET_START_DATE');

INSERT INTO tp_template_generic_vars
(template_variable_type, template_variable)
VALUES (16, 'BUDGET_END_DATE');

INSERT INTO tp_template_generic_vars
(template_variable_type, template_variable)
VALUES (17, 'BUDGET_TRANSFERRED_HEADS');

INSERT INTO tp_template_generic_vars
(template_variable_type, template_variable)
VALUES (18, 'POS_DESC_TITLE');

INSERT INTO tp_template_generic_vars
(template_variable_type, template_variable)
VALUES (18, 'POS_DESC_VACANCIES');

INSERT INTO tp_template_generic_vars
(template_variable_type, template_variable)
VALUES (18, 'POS_DESC_HIRE_BY_DATE');

INSERT INTO tp_template_types
(template_type_id, template_type)
VALUES (29,'Budget Item Creation Notification to Owner');

INSERT INTO tp_template_types
(template_type_id, template_type)
VALUES (30,'Budget Item Update Notification to Owner');

INSERT INTO tp_template_types
(template_type_id, template_type)
VALUES (31,'Budget Item Deletion Notification to Owner');

INSERT INTO tp_template_types
(template_type_id, template_type)
VALUES (32,'Track Mode Vacancy Notification to Owner');

INSERT INTO tp_template_types
(template_type_id, template_type)
VALUES (33,'Track Mode Hire by date Notification to Owner');

INSERT INTO tp_template_types
(template_type_id, template_type)
VALUES (34,'Budget Item Heads Transfer Notification to Owner');

INSERT INTO tp_template_vars
(template_type_id, template_variable_type)
VALUES (29,16);

INSERT INTO tp_template_vars
(template_type_id, template_variable_type)
VALUES (29,2);

INSERT INTO tp_template_vars
(template_type_id, template_variable_type)
VALUES (30,16);

INSERT INTO tp_template_vars
(template_type_id, template_variable_type)
VALUES (30,2);

INSERT INTO tp_template_vars
(template_type_id, template_variable_type)
VALUES (31,16);

INSERT INTO tp_template_vars
(template_type_id, template_variable_type)
VALUES (31,2);

INSERT INTO tp_template_vars
(template_type_id, template_variable_type)
VALUES (32,16);

INSERT INTO tp_template_vars
(template_type_id, template_variable_type)
VALUES (32,18);

INSERT INTO tp_template_vars
(template_type_id, template_variable_type)
VALUES (32,2);

INSERT INTO tp_template_vars
(template_type_id, template_variable_type)
VALUES (33,16);

INSERT INTO tp_template_vars
(template_type_id, template_variable_type)
VALUES (33,18);

INSERT INTO tp_template_vars
(template_type_id, template_variable_type)
VALUES (33,2);

INSERT INTO tp_template_vars
(template_type_id, template_variable_type)
VALUES (34,16);

INSERT INTO tp_template_vars
(template_type_id, template_variable_type)
VALUES (34,17);

INSERT INTO tp_template_vars
(template_type_id, template_variable_type)
VALUES (34,2);

insert into tp_templates (template_code, template_name, template_subject_file, template_content_file, template_auto, template_format, 
	template_private, template_type_id, template_is_default, template_date_created,template_is_save_as_draft, do_show_save_as_draft_option, user_id ) 
	values ('budgetItemCreationNotificationtoOwner', 'Budget Item Creation Notification to Owner', 'budgetItemCreationNotificationtoOwnerSubject.vm', 
	'budgetItemCreationNotificationtoOwnerContent.vm', 0, 0, 0, 29, 1, now(), 0, 0, 1);

insert into tp_templates (template_code, template_name, template_subject_file, template_content_file, template_auto, template_format, 
	template_private, template_type_id, template_is_default, template_date_created,template_is_save_as_draft, do_show_save_as_draft_option, user_id ) 
	values ('budgetItemUpdateNotificationtoOwner', 'Budget Item Update Notification to Owner', 'budgetItemUpdateNotificationtoOwnerSubject.vm', 
	'budgetItemUpdateNotificationtoOwnerContent.vm', 0, 0, 0, 30, 1, now(), 0, 0, 1);

insert into tp_templates (template_code, template_name, template_subject_file, template_content_file, template_auto, template_format, 
	template_private, template_type_id, template_is_default, template_date_created,template_is_save_as_draft, do_show_save_as_draft_option, user_id ) 
	values ('budgetItemDeletionNotificationtoOwner', 'Budget Item Deletion Notification to Owner', 'budgetItemDeletionNotificationtoOwnerSubject.vm', 
	'budgetItemDeletionNotificationtoOwnerContent.vm', 0, 0, 0, 31, 1, now(), 0, 0, 1);

insert into tp_templates (template_code, template_name, template_subject_file, template_content_file, template_auto, template_format, 
	template_private, template_type_id, template_is_default, template_date_created,template_is_save_as_draft, do_show_save_as_draft_option, user_id ) 
	values ('trackModeVacancyNotificationtoOwner', 'Track Mode Vacancy Notification to Owner', 'trackModeVacancyNotificationtoOwnerSubject.vm', 
	'trackModeVacancyNotificationtoOwnerContent.vm', 0, 0, 0, 32, 1, now(), 0, 0, 1);

insert into tp_templates (template_code, template_name, template_subject_file, template_content_file, template_auto, template_format, 
	template_private, template_type_id, template_is_default, template_date_created,template_is_save_as_draft, do_show_save_as_draft_option, user_id ) 
	values ('trackModeHirebydateNotificationtoOwner', 'Track Mode Hire by date Notification to Owner', 'trackModeHirebydateNotificationtoOwnerSubject.vm', 
	'trackModeHirebydateNotificationtoOwnerContent.vm', 0, 0, 0, 33, 1, now(), 0, 0, 1);

insert into tp_templates (template_code, template_name, template_subject_file, template_content_file, template_auto, template_format, 
	template_private, template_type_id, template_is_default, template_date_created,template_is_save_as_draft, do_show_save_as_draft_option, user_id ) 
	values ('budgetItemHeadsTransferNotificationtoOwner', 'Budget Item Heads Transfer Notification to Owner', 'budgetItemHeadsTransferNotificationtoOwnerSubject.vm', 
	'budgetItemHeadsTransferNotificationtoOwnerContent.vm', 0, 0, 0, 34, 1, now(), 0, 0, 1);


-- Budget Module Ends

Drop table if exists tp_position_screen;
CREATE TABLE `tp_position_screen` (               
		     `field_id` varchar(250) NOT NULL,                              
		     `field_type` char(1) NOT NULL default '0',                     
		     `field_rank` tinyint(4) NOT NULL default '0',                  
		     `field_position_print_show` char(1) NOT NULL default '0'       
		     );
delete from tp_position_screen;
insert into tp_position_screen (field_id, field_type, field_rank, field_position_print_show) values('PositionName','0','1','1');
insert into tp_position_screen (field_id, field_type, field_rank, field_position_print_show) values('PositionCode','0','2','1');
insert into tp_position_screen (field_id, field_type, field_rank, field_position_print_show) values('PositionCreatedOn','0','3','1');
insert into tp_position_screen (field_id, field_type, field_rank, field_position_print_show) values('Location','0','4','1');
insert into tp_position_screen (field_id, field_type, field_rank, field_position_print_show) values('DepartmentLevel1','0','5','1');
insert into tp_position_screen (field_id, field_type, field_rank, field_position_print_show) values('DepartmentLevel2','0','6','1');
insert into tp_position_screen (field_id, field_type, field_rank, field_position_print_show) values('DepartmentLevel3','0','7','1');
insert into tp_position_screen (field_id, field_type, field_rank, field_position_print_show) values('Vacancies','0','8','1');
insert into tp_position_screen (field_id, field_type, field_rank, field_position_print_show) values('HireByDate','0','9','1');
insert into tp_position_screen (field_id, field_type, field_rank, field_position_print_show) values('PositionLevel','0','10','1');
insert into tp_position_screen (field_id, field_type, field_rank, field_position_print_show) values('ReferalFees','0','11','1');
insert into tp_position_screen (field_id, field_type, field_rank, field_position_print_show) values('Responsibilities','0','12','1');
insert into tp_position_screen (field_id, field_type, field_rank, field_position_print_show) values('Requirements','0','13','1');
insert into tp_position_screen (field_id, field_type, field_rank, field_position_print_show) values('PrimarySkills','0','14','1');
insert into tp_position_screen (field_id, field_type, field_rank, field_position_print_show) values('SecondarySkills','0','15','1');
insert into tp_position_screen (field_id, field_type, field_rank, field_position_print_show) values('Education','0','16','1');
insert into tp_position_screen (field_id, field_type, field_rank, field_position_print_show) values('Branch','0','17','1');
insert into tp_position_screen (field_id, field_type, field_rank, field_position_print_show) values('Experience','0','18','1');
insert into tp_position_screen (field_id, field_type, field_rank, field_position_print_show) values('Note','0','19','1');
insert into tp_position_screen (field_id, field_type, field_rank, field_position_print_show) values('RequestedBy','0','20','1');
insert into tp_position_screen (field_id, field_type, field_rank, field_position_print_show) values('ApprovedBy','0','21','1');

alter table tp_position_screen add column number int NOT NULL AUTO_INCREMENT  after field_position_print_show, add primary key (number);

INSERT INTO tp_position_screen
(field_id, field_type,field_position_print_show)
SELECT custom_field_name, '1','1'
FROM tp_custom_fields WHERE custom_field_entity_type =2;

UPDATE tp_position_screen SET field_rank=number WHERE field_type=1;

alter table tp_position_screen drop column number;


UPDATE tp_positions tp, tp_position_vendors tpv
SET tp.vendor_publish_from_date = now(),
tp.vendor_publish_to_date = DATE_FORMAT(tp.position_date_expiry , '%Y-%m-%d')
WHERE tp.position_id=tpv.position_id;

UPDATE tp_positions tp
SET tp.website_publish_from_date = now(),
tp.website_publish_to_date = DATE_FORMAT(tp.position_date_expiry , '%Y-%m-%d')
where tp.is_published_to_web_site=1;

UPDATE tp_positions tp
SET tp.employee_publish_from_date = now(),
tp.employee_publish_to_date = DATE_FORMAT(tp.position_date_expiry , '%Y-%m-%d')
where tp.is_published_for_emp_portal=1;

UPDATE tp_positions tp
SET tp.walk_in_publish_from_date = now(),
tp.walk_in_publish_to_date = DATE_FORMAT(tp.position_date_expiry , '%Y-%m-%d')
where tp.is_published_for_walk_in=1;

update tp_screen_configurations set field_applicant_mandatory_on_site='1' where field_id='Email1' and field_type='0'; 
update tp_screen_configurations set field_applicant_show_on_site='1' where field_id='Email1' and field_type='0'; 