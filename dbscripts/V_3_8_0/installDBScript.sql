use talentpool;

DELETE FROM tp_recent_searches;

insert into `tp_application_properties` (`application_property`,`application_property_value`) values ( 'position_code_template','{ppp}-{sss}-{lll}-{yyyy}-{mm}-{dd}-{nnnn}');

update tp_permissions
set permission_rank=(permission_rank+2) 
where permission_rank > 4;

insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) 
values ( '5','5','Show confidential profile','0','1');

insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) 
values ( '6','6','Do not show confidential profile','0','1');

insert into tp_role_permissions
select tr.role_id, 5 
from tp_roles tr 
where tr.role_id in (1,4,6);

insert into tp_role_permissions
select tr.role_id, 6 
from tp_roles tr 
where tr.role_id not in (1,4,6);

insert into tp_user_permissions
select tu.user_id, trp.permission_id
from tp_users tu, tp_user_roles tur, tp_role_permissions trp
where trp.role_id=tur.role_id and tur.user_id=tu.user_id
and trp.permission_id in (5,6);

alter table tp_applicants add column `is_confidential` char (1)  DEFAULT '0' NULL  after `vendor_id`;

alter table `tp_positions` add column `responsibilities` text  NULL after `position_publish_date`;

alter table `tp_positions` add column `requirements` text   NULL  after `responsibilities`;

SET group_concat_max_len=1048567;

UPDATE tp_positions TPOS
SET responsibilities=(SELECT CONCAT('<OL>', GROUP_CONCAT(responsibility SEPARATOR '<BR/>'), '</OL>') AS responsibility
FROM
(
SELECT position_id, CONCAT('<LI>', responsibility_text, '</LI>') AS responsibility
FROM
(
SELECT position_id, responsibility_rank, CAST(responsibility_text AS CHAR) AS responsibility_text
FROM tp_position_responsibilities
ORDER BY position_id, responsibility_rank
) AS TBL
) AS TBL2
WHERE position_id=TPOS.position_id
GROUP BY position_id);

UPDATE tp_positions TPOS
SET requirements=(SELECT CONCAT('<OL>', GROUP_CONCAT(requirement SEPARATOR '<BR/>'), '<OL>') AS requirement
FROM
(
SELECT position_id, CONCAT('<LI>', requirement_text, '</LI>') AS requirement
FROM
(
SELECT position_id, requirement_rank, CAST(requirement_text AS CHAR) AS requirement_text
FROM tp_position_requirements
ORDER BY position_id, requirement_rank
) AS TBL
) AS TBL2
WHERE position_id=TPOS.position_id
GROUP BY position_id);

alter table `tp_screen_configurations` add column `field_applicant_show_on_site` char (1)  DEFAULT '1' NOT NULL  
COLLATE latin1_spanish_ci  after `field_employee_mandatory`, 
add column `field_applicant_mandatory_on_site` char (1)  DEFAULT '0' NOT NULL  
COLLATE latin1_swedish_ci  after `field_applicant_show_on_site`;

update `tp_screen_configurations` set `field_applicant_mandatory_on_site`='1' where `field_id`='Name' and `field_type`='0'; 

update tp_permissions
set permission_rank=(permission_rank+1) 
where permission_rank > 55;

insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) 
values ( '72','56','Publish position on web site','46','1');

insert into tp_role_permissions
select tr.role_id, 72 
from tp_roles tr 
where tr.role_id in (1,4,6);

insert into `tp_user_permissions` (`user_id`,`permission_id`) values ( '1','72');

CREATE TABLE `tp_position_rules` (                                                                              
     `rule_id` bigint(20) NOT NULL auto_increment,                                                                 
     `rule_type` int(11) NOT NULL,                                                                                 
     `position_id` bigint(20) NOT NULL,                                                                            
     `degree_id` smallint(6) default NULL,                                                                         
     `branch_id` int(11) default NULL,                                                                             
     `min_exp` decimal(4,2) default '0.00',                                                                        
     `max_exp` decimal(4,2) default '0.00',                                                                        
     `current_location` varchar(255) default NULL,                                                                 
     PRIMARY KEY  (`rule_id`),                                                                                     
     KEY `FK_tp_position_rules_position_id` (`position_id`),                                                       
     KEY `FK_tp_position_rules_degree_id` (`degree_id`),                                                           
     KEY `FK_tp_position_rules_branch_id` (`branch_id`),                                                           
     CONSTRAINT `tp_position_rules_ibfk_1` FOREIGN KEY (`position_id`) REFERENCES `tp_positions` (`position_id`),  
     CONSTRAINT `tp_position_rules_ibfk_2` FOREIGN KEY (`degree_id`) REFERENCES `tp_degrees` (`degree_id`),        
     CONSTRAINT `tp_position_rules_ibfk_3` FOREIGN KEY (`branch_id`) REFERENCES `tp_branches` (`branch_id`)        
   ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

alter table `tp_positions` add column `is_published_to_web_site` char (1)  DEFAULT '0' 
NOT NULL  COLLATE latin1_swedish_ci  after `requirements`; 

CREATE TABLE `tp_position_rule_institutes` (                                                                                
	 `id` bigint(20) NOT NULL auto_increment,                                                                   
	 `rule_id` bigint(20) NOT NULL,                                                                                   
	 `institute_id` bigint(20) NOT NULL,                                                                              
	 PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1; 

insert into `tp_templates` (`template_code`,`template_name`,`template_subject_file`,`template_content_file`,`template_auto`,`template_format`,`template_private`,`template_type_id`,`template_is_default`,`template_date_created`,`template_is_save_as_draft`,`do_show_save_as_draft_option`,`user_id`) 
values ( 'forwardResumes','forwardResumes','forwardResumesSubject.vm','forwardResumesContent.vm','0','0','0','10','1',now(),'0','0','1');

insert into tp_application_properties (`application_property`,`application_property_value`) 
values ( 'allow_bulk_feedback','0');

DROP TABLE IF EXISTS `tp_excel_import`;
CREATE TABLE `tp_excel_import` (
                   `session_id` bigint(20) default NULL,
                   `row_id` bigint(20) default NULL,
                  `applicant_name` char(100) default NULL,
                  `email1` varchar(50) default NULL,
                  `email2` varchar(50) default NULL,
                  `phone1` varchar(25) default NULL,
                  `phone2` varchar(25) default NULL,
                  `mobile` varchar(25) default NULL,
                  `experience` varchar(25) default NULL,
                  `source` varchar(50) default NULL,
                  `current_location` varchar(100) default NULL,
                  `skills` varchar(255) default NULL,
                  `yop1` varchar(10) default NULL,
                  `institute1` varchar(100) default NULL,
                  `degree1` varchar(100) default NULL,
                  `branch1` varchar(250) default NULL,
                  `yop2` varchar(10) default NULL,
                  `institute2` varchar(100) default NULL,
                  `degree2` varchar(100) default NULL,
                  `branch2` varchar(250) default NULL,
                  `current_employer` varchar(100) default NULL,
                  `current_ctc` varchar(50) default NULL,
                  `expected_ctc` varchar(50) default NULL,
                  `notice_period` varchar(10) default NULL,
                  `note` text,
                  `error` text,
                  `status` char(20) default NULL,
                  `applicant_id` varchar(20) default NULL,
                  `date_created` datetime default NULL,
                  `original_resume_path` varchar(255) default NULL
                 ) ENGINE=InnoDB DEFAULT CHARSET=latin1 ;

DROP TABLE IF EXISTS `tp_excel_import_custom_field_values`;
CREATE TABLE `tp_excel_import_custom_field_values` (                                                                                         
				`custom_field_id` int(11) NOT NULL,                                                                                                        
                `string_value` varchar(250) default NULL,                                                                                                  
                `number_value` double default NULL,                                                                                                        
                `date_value` date default NULL,                                                                                                            
                `entity_id` bigint(20) default NULL,                                                                                                       
                KEY `FK_tp_excel_import_custom_field_values` (`custom_field_id`),                                                                          
                CONSTRAINT `tp_excel_import_custom_field_values_ibfk_1` FOREIGN KEY (`custom_field_id`) REFERENCES `tp_custom_fields` (`custom_field_id`)  
                ) ENGINE=InnoDB DEFAULT CHARSET=latin1 ;

INSERT INTO tp_screen_configurations (field_id, field_rank, field_import_show,
field_employee_show,field_applicant_show_on_site)
SELECT 'Confidential', tsc.field_rank+1,1,0,0 FROM tp_screen_configurations tsc ORDER BY tsc.field_rank DESC LIMIT 0,1;


alter table `tp_position_steps` add column `is_notify_to_candidate` char (1) 
DEFAULT '0' NOT NULL  COLLATE latin1_swedish_ci  after `is_dMaker_sameAs_asgnTo`;

insert into `tp_templates` (`template_code`,`template_name`,`template_subject_file`,`template_content_file`,`template_auto`,`template_format`,`template_private`,`template_type_id`,`template_is_default`,`template_date_created`,`user_id`)
values ( 'progressEmailToCandidate','Progress Email To Candidate','progressEmailToCandidateSubject.vm','progressEmailToCandidateContent.vm','0','0','0','25','1',now(),'1');

insert into `tp_template_types` (`template_type_id`,`template_type`) values ( 25,'Progress Email to Candidate');

insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values ( '25','1');
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values ( '25','2');
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values ( '25','3');
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values ( '25','14');

insert into `tp_application_properties` (`application_property`,`application_property_value`) values ( 'candidate_progress_notification','0');

CREATE TABLE `tp_browser_import_session_documents` (      
  `document_id` bigint(20) NOT NULL auto_increment,  
  `session_id` varchar(100) NOT NULL,                
  `document_path` varchar(250) NOT NULL,             
  `file_name` varchar(250) NOT NULL,                 
  `import_status` char(1) NOT NULL default '0',      
  `date_created` datetime NOT NULL,                  
  PRIMARY KEY  (`document_id`)                       
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

alter table `tp_bulk_import_session_emails` add column `comment_url` varchar (500)   NULL  after `import_status`;

alter table `tp_browser_import_session_documents` add column `comment_url` varchar (500)   NULL  after `import_status`;

alter table `tp_bulk_import_session_results` 
add column `parsed_city` varchar (50)   NULL  after `parsed_name`, 
add column `parsed_working_since` date   NULL  after `parsed_home_phone`, 
add column `parsed_ctc` double   NULL  after `parsed_working_since`, 
add column `parsed_current_employer` varchar (250)   NULL  after `parsed_ctc`, 
add column `parsed_source_id` int   NULL  after `parsed_current_employer`;

CREATE TABLE `tp_bulk_import_session_result_educational_info` (     
   `educational_info_id` bigint(20) NOT NULL auto_increment,  
   `result_id` bigint(20) NOT NULL,                        
   `degree_id` smallint(6) default NULL,                      
   `branch_id` int(11) default NULL,                          
   `institute_id` bigint(20) default NULL,                    
   `educational_info_year_of_passing` date default NULL,      
   `educational_info_grade` varchar(100) default NULL,        
   PRIMARY KEY  (`educational_info_id`)                       
 ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

insert into `tp_source_types` (source_type,	system_generated, source_type_category)
values ('Company Web Site',1, 3);

insert into `tp_sources` ( source_type_id, source_title, source_email, source_phone, source_mobile, 
is_send_email_to_source, is_send_sms_to_source, lock_in_period_on_import, source_status, system_generated)
values((select source_type_id from tp_source_types where source_type_category = 3 limit 0, 1),'Hosted Company web site','','','',0,0, 0,1,1);


insert into `tp_application_properties` (`application_property`,`application_property_value`) values ( 'employee_can_apply_for_job','0');

alter table `tp_applicants` add column `employee_code` varchar (100)   NULL  COLLATE latin1_swedish_ci  after `is_confidential`;

alter table `tp_feedback_field_categories` add column `system_generated` char (1)  DEFAULT '0' NULL  after `category_status`;

alter table `tp_feedback_fields` add column `system_generated` char (1)  DEFAULT '0' NULL  after `feedback_field_status`;

insert into `tp_feedback_field_categories` (`category_id`,`category_name`,`category_status`,`system_generated`) values ( NULL,'Others','1','1');

INSERT INTO `tp_feedback_fields` (`category_id`,`feedback_field_title`,`feedback_field_desc`,`feedback_field_status`,`system_generated`) 
SELECT category_id,'Remarks','','1','1' FROM tp_feedback_field_categories WHERE category_name = 'Others' AND system_generated=1;

INSERT INTO tp_feedback_form_fields (feedback_form_id,feedback_field_id,feedback_form_field_desc,
feedback_form_field_rank,feedback_form_field_type,feedback_form_field_comment_required,
feedback_form_field_display_type,feedback_form_field_is_mandatory)
SELECT feedback_form_id, feedback_field_id, feedback_form_field_desc, feedback_form_field_rank,
feedback_form_field_type, feedback_form_field_comment_required, 1 , 0
FROM(
SELECT feedback_form_id, category_id as feedback_field_id, 
0 as feedback_form_field_type, '' as feedback_form_field_desc,
0 as feedback_form_field_comment_required,
(SELECT feedback_form_field_rank FROM tp_feedback_form_fields 
WHERE feedback_form_id = tff.feedback_form_id ORDER BY feedback_form_field_rank DESC LIMIT 0,1)+1 AS feedback_form_field_rank
FROM tp_feedback_field_categories, tp_feedback_forms tff
WHERE system_generated=1
UNION
SELECT feedback_form_id, feedback_field_id, 1 as feedback_form_field_type,
feedback_field_desc as feedback_form_field_desc,
1 as feedback_form_field_comment_required,
(SELECT feedback_form_field_rank FROM tp_feedback_form_fields 
WHERE feedback_form_id = tff.feedback_form_id ORDER BY feedback_form_field_rank DESC LIMIT 0,1)+2 AS feedback_form_field_rank
FROM tp_feedback_fields , tp_feedback_forms tff
WHERE system_generated=1
) AS x;

CREATE TABLE `tp_applicant_joining_history` (                                                                                                   
    `applicant_joining_history_id` bigint(20) NOT NULL auto_increment,                                                                            
    `applicant_id` bigint(20) NOT NULL,                                                                                                           
    `position_id` bigint(20) NOT NULL,                                                                                                            
    `offered_ctc` varchar(10) default NULL,                                                                                                       
    `level_offered` varchar(20) default NULL,                                                                                                     
    `designation_offered` varchar(50) default NULL,                                                                                               
    `joining_date` date default NULL,                                                                                                             
    `date_created` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,                                                      
    PRIMARY KEY  (`applicant_joining_history_id`),                                                                                                
    KEY `FK_tp_applicant_joining_history` (`applicant_id`),                                                                                       
    KEY `FK_tp_applicant_joining_history_position` (`position_id`),                                                                               
    CONSTRAINT `tp_applicant_joining_history_ibfk_1` FOREIGN KEY (`applicant_id`) REFERENCES `tp_applicants` (`applicant_id`) ON DELETE CASCADE,  
    CONSTRAINT `tp_applicant_joining_history_ibfk_2` FOREIGN KEY (`position_id`) REFERENCES `tp_positions` (`position_id`)                        
  ) ENGINE=InnoDB DEFAULT CHARSET=latin1;                                                                                                        

  
INSERT INTO tp_applicant_joining_history
(applicant_id, position_id, offered_ctc, level_offered, designation_offered, joining_date, date_created)
SELECT applicant_id, applicant_position_id, offered_ctc, applicant_level_offered, applicant_designation_offered, applicant_date_joined, NOW()
FROM tp_applicants
WHERE applicant_joined=1;

CREATE TABLE `tp_applicant_selection_process_temp_traits` (                                                                                                              
`session_id` varchar(250) NOT NULL,                                                                                                                                    
`applicant_id` bigint(20) NOT NULL,                                                                                                                                    
`feedback_form_field_id` bigint(20) NOT NULL,                                                                                                                          
`trait_comment` text,                                                                                                                                                  
`user_id` bigint(20) NOT NULL default '0',                                                                                                                             
`rating_field_id` int(11) default NULL,                                                                                                                                
KEY `FK_tp_applicant_selection_process_temp_traits` (`applicant_id`),                                                                                                  
KEY `FK_tp_applicant_selection_process_temp_traits_1` (`feedback_form_field_id`),                                                                                      
KEY `FK_tp_applicant_selection_process_temp_traits_2` (`rating_field_id`),                                                                                             
CONSTRAINT `tp_applicant_selection_process_temp_traits_ibfk_3` FOREIGN KEY (`rating_field_id`) REFERENCES `tp_rating_fields` (`rating_field_id`),                      
CONSTRAINT `tp_applicant_selection_process_temp_traits_ibfk_1` FOREIGN KEY (`applicant_id`) REFERENCES `tp_applicants` (`applicant_id`),                               
CONSTRAINT `tp_applicant_selection_process_temp_traits_ibfk_2` FOREIGN KEY (`feedback_form_field_id`) REFERENCES `tp_feedback_form_fields` (`feedback_form_field_id`)  
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `tp_position_screen_configurations` (                                                                      
    `field_id` varchar(250) NOT NULL,                                                                            
    `field_type` char(1) NOT NULL default '0',                                                                   
    `field_rank` tinyint(4) NOT NULL default '0',                                                                
    `field_position_list_show` char(1) NOT NULL default '0',                                                            
    `field_position_details_show` char(1) NOT NULL default '0'
  ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO tp_position_screen_configurations (field_id, field_type, field_rank, field_position_list_show,
	field_position_details_show)
VALUES ('PositionName',0,1,1,1);

INSERT INTO tp_position_screen_configurations (field_id, field_type, field_rank, field_position_list_show,
	field_position_details_show)
VALUES ('PositionCode',0,2,0,1);

INSERT INTO tp_position_screen_configurations (field_id, field_type, field_rank, field_position_list_show,
	field_position_details_show)
VALUES ('PositionCreatedOn',0,3,0,1);

INSERT INTO tp_position_screen_configurations (field_id, field_type, field_rank, field_position_list_show,
	field_position_details_show)
VALUES ('Location',0,4,1,1);

INSERT INTO tp_position_screen_configurations (field_id, field_type, field_rank, field_position_list_show,
	field_position_details_show)
VALUES ('DepartmentLevel1',0,5,1,1);

INSERT INTO tp_position_screen_configurations (field_id, field_type, field_rank, field_position_list_show,
	field_position_details_show)
VALUES ('DepartmentLevel2',0,6,0,1);

INSERT INTO tp_position_screen_configurations (field_id, field_type, field_rank, field_position_list_show,
	field_position_details_show)
VALUES ('DepartmentLevel3',0,7,0,1);

INSERT INTO tp_position_screen_configurations (field_id, field_type, field_rank, field_position_list_show,
	field_position_details_show)
VALUES ('Vacancies',0,8,1,1);

INSERT INTO tp_position_screen_configurations (field_id, field_type, field_rank, field_position_list_show,
	field_position_details_show)
VALUES ('HireByDate',0,9,0,1);

INSERT INTO tp_position_screen_configurations (field_id, field_type, field_rank, field_position_list_show,
	field_position_details_show)
VALUES ('PositionLevel',0,10,0,1);

INSERT INTO tp_position_screen_configurations (field_id, field_type, field_rank, field_position_list_show,
	field_position_details_show)
VALUES ('ReferalFees',0,11,0,1);

INSERT INTO tp_position_screen_configurations (field_id, field_type, field_rank, field_position_list_show,
	field_position_details_show)
VALUES ('Responsibilities',0,12,0,1);

INSERT INTO tp_position_screen_configurations (field_id, field_type, field_rank, field_position_list_show,
	field_position_details_show)
VALUES ('Requirements',0,13,0,1);

INSERT INTO tp_position_screen_configurations (field_id, field_type, field_rank, field_position_list_show,
	field_position_details_show)
VALUES ('PrimarySkills',0,14,1,1);

INSERT INTO tp_position_screen_configurations (field_id, field_type, field_rank, field_position_list_show,
	field_position_details_show)
VALUES ('SecondarySkills',0,15,0,1);

INSERT INTO tp_position_screen_configurations (field_id, field_type, field_rank, field_position_list_show,
	field_position_details_show)
VALUES ('Education',0,16,0,1);

INSERT INTO tp_position_screen_configurations (field_id, field_type, field_rank, field_position_list_show,
	field_position_details_show)
VALUES ('Branch',0,17,0,1);

INSERT INTO tp_position_screen_configurations (field_id, field_type, field_rank, field_position_list_show,
	field_position_details_show)
VALUES ('Experience',0,18,0,1);

INSERT INTO tp_position_screen_configurations (field_id, field_type, field_rank, field_position_list_show,
	field_position_details_show)
VALUES ('Note',0,19,0,1);


ALTER TABLE tp_position_screen_configurations ADD COLUMN `temp` int NOT NULL AUTO_INCREMENT  after `field_position_details_show`, add primary key (`temp` );

INSERT INTO tp_position_screen_configurations (field_id, field_type)
SELECT custom_field_name,'1' FROM tp_custom_fields WHERE custom_field_entity_type=2;

UPDATE tp_position_screen_configurations 
SET field_rank=temp;

ALTER TABLE tp_position_screen_configurations DROP COLUMN `temp`;


insert into `tp_application_properties` (`application_property`,`application_property_value`) values ( 'show_bot_resistant_email_id','0');

DELETE FROM tp_position_vendors
where source_id NOT IN (select USER_SOURCE_ID from tp_users where USER_ID IN (SELECT USER_ID FROM tp_user_roles WHERE ROLE_ID = 7));


INSERT INTO `tp_application_properties` (`application_property`,`application_property_value`)VALUES ( 'audit_trail_recovery_notification','1');

INSERT INTO `tp_templates` (`template_code`,`template_name`,`template_subject_file`, `template_content_file`,`template_auto`,`template_format`,`template_private`, `template_type_id`,`template_is_default`,`template_date_created`,`user_id`)
values ('auditTrailFileRecoveryNotification','Audit Trail File Recovery Notification','auditTrailFileRecoveryNotificationSubject.vm','auditTrailFileRecoveryNotificationContent.vm','0','0','0','26','1',now(),'1');

insert into `tp_template_types` (`template_type_id`,`template_type`) values ( 26,'Audit Trail File Recovery Notification');

alter table `tp_position_screen_configurations` add column `field_position_details_rank` tinyint (4)  DEFAULT '0' NOT NULL  COLLATE latin1_swedish_ci  after `field_position_details_show`;

ALTER TABLE tp_position_screen_configurations ADD COLUMN `temp` int NOT NULL AUTO_INCREMENT, add primary key (`temp` );

UPDATE tp_position_screen_configurations SET field_position_details_rank=temp;

ALTER TABLE tp_position_screen_configurations DROP COLUMN `temp`;

CREATE TABLE `tp_website_screen_settings` (               
	`setting_id` bigint(20) NOT NULL auto_increment,
	`is_show_labels_on_list` char(1) NOT NULL default '0',                     
	`first_line_field` varchar(250) NOT NULL,                 
	`is_other` char(1) NOT NULL default '0',
	PRIMARY KEY  (`setting_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO tp_website_screen_settings (is_show_labels_on_list,first_line_field,is_other)
VALUES(0,'PositionName',0);