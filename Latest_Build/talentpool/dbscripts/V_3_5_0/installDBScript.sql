use talentpool;

DELETE FROM tp_recent_searches;

CREATE TABLE `tp_screen_configurations` (          
		`field_id` varchar(250) NOT NULL,                
		`field_type` char(1) NOT NULL default '0',       
		`field_rank` tinyint(4) NOT NULL default '0',    
		`field_import_show` char(1) NOT NULL default '0',       
		`field_edit_show` char(1) NOT NULL default '0',  
		`field_import_mandatory` char(1) NOT NULL default '0',   
		`field_vendor_show` char(1) NOT NULL default '0',       
		`field_vendor_mandatory` char(1) NOT NULL default '0'   
	  ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
	  
update tp_permissions
set permission_rank=(permission_rank+3) 
where permission_rank > 13;

insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) 
values ( '69','14','Label Messages','10','1');
insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) 
values ( '70','15','Custom Fields','10','1');
insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) 
values ( '71','16','Screen Configuration','10','1');

insert into `tp_user_permissions` (`user_id`,`permission_id`) values ( '1','69');
insert into `tp_user_permissions` (`user_id`,`permission_id`) values ( '1','70');
insert into `tp_user_permissions` (`user_id`,`permission_id`) values ( '1','71');

INSERT INTO tp_screen_configurations (field_id, field_type, field_rank, field_import_show,
	field_edit_show, field_import_mandatory, field_vendor_show, field_vendor_mandatory)
VALUES ('Name',0,1,1,1,1,1,1);

INSERT INTO tp_screen_configurations (field_id, field_type, field_rank, field_import_show,
	field_edit_show, field_import_mandatory, field_vendor_show, field_vendor_mandatory)
VALUES ('Email1',0,2,1,1,1,1,0);

INSERT INTO tp_screen_configurations (field_id, field_type, field_rank, field_import_show,
	field_edit_show, field_import_mandatory, field_vendor_show, field_vendor_mandatory)
VALUES ('Email2',0,3,1,1,0,1,0);

INSERT INTO tp_screen_configurations (field_id, field_type, field_rank, field_import_show,
	field_edit_show, field_import_mandatory, field_vendor_show, field_vendor_mandatory)
VALUES ('Phone1',0,4,1,1,0,1,0);

INSERT INTO tp_screen_configurations (field_id, field_type, field_rank, field_import_show,
	field_edit_show, field_import_mandatory, field_vendor_show, field_vendor_mandatory)
VALUES ('Phone2',0,5,1,1,0,1,0);

INSERT INTO tp_screen_configurations (field_id, field_type, field_rank, field_import_show,
	field_edit_show, field_import_mandatory, field_vendor_show, field_vendor_mandatory)
VALUES ('Mobile',0,6,1,1,1,1,0);

INSERT INTO tp_screen_configurations (field_id, field_type, field_rank, field_import_show,
	field_edit_show, field_import_mandatory, field_vendor_show, field_vendor_mandatory)
VALUES ('Source',0,7,1,1,1,1,0);

INSERT INTO tp_screen_configurations (field_id, field_type, field_rank, field_import_show,
	field_edit_show, field_import_mandatory, field_vendor_show, field_vendor_mandatory)
VALUES ('CurrentLocation',0,8,1,1,0,1,0);

INSERT INTO tp_screen_configurations (field_id, field_type, field_rank, field_import_show,
	field_edit_show, field_import_mandatory, field_vendor_show, field_vendor_mandatory)
VALUES ('Skills',0,9,1,1,0,1,0);

INSERT INTO tp_screen_configurations (field_id, field_type, field_rank, field_import_show,
	field_edit_show, field_import_mandatory, field_vendor_show, field_vendor_mandatory)
VALUES ('Education',0,10,1,1,0,1,0);

INSERT INTO tp_screen_configurations (field_id, field_type, field_rank, field_import_show,
	field_edit_show, field_import_mandatory, field_vendor_show, field_vendor_mandatory)
VALUES ('Experience',0,11,1,1,1,1,1);

INSERT INTO tp_screen_configurations (field_id, field_type, field_rank, field_import_show,
	field_edit_show, field_import_mandatory, field_vendor_show, field_vendor_mandatory)
VALUES ('CurrentEmployer',0,12,1,1,0,1,0);

INSERT INTO tp_screen_configurations (field_id, field_type, field_rank, field_import_show,
	field_edit_show, field_import_mandatory, field_vendor_show, field_vendor_mandatory)
VALUES ('CurrentCTC',0,13,1,1,0,1,0);

INSERT INTO tp_screen_configurations (field_id, field_type, field_rank, field_import_show,
	field_edit_show, field_import_mandatory, field_vendor_show, field_vendor_mandatory)
VALUES ('ExpectedCTC',0,14,1,1,0,1,0);

INSERT INTO tp_screen_configurations (field_id, field_type, field_rank, field_import_show,
	field_edit_show, field_import_mandatory, field_vendor_show, field_vendor_mandatory)
VALUES ('NoticePeriod',0,15,1,1,0,1,0);

INSERT INTO tp_screen_configurations (field_id, field_type, field_rank, field_import_show,
	field_edit_show, field_import_mandatory, field_vendor_show, field_vendor_mandatory)
VALUES ('Note',0,16,1,1,0,1,0);

alter table `tp_screen_configurations` add column `auto_increment` tinyint NOT NULL AUTO_INCREMENT  after `field_vendor_mandatory`, add primary key (`auto_increment` );

INSERT INTO tp_screen_configurations (field_id, field_type, field_import_show, field_edit_show, field_vendor_show)
SELECT custom_field_name, 1,1,1,1 FROM tp_custom_fields 
WHERE custom_field_entity_type=1;

UPDATE tp_screen_configurations SET field_rank=auto_increment;

alter table `tp_screen_configurations` drop column `auto_increment`;

alter table `tp_applicant_selection_process` 
drop foreign key `tp_applicant_selection_process_ibfk_3`;
alter table `tp_applicant_selection_process` change `position_id` `position_id` bigint NOT NULL;

alter table `tp_applicants` 
drop foreign key `tp_applicants_ibfk_3`;
alter table `tp_applicants` change `applicant_position_id` `applicant_position_id` bigint   NULL;

alter table `tp_appointments` drop foreign key `tp_appointments_ibfk_2`;
alter table `tp_appointments` change `position_id` `position_id` bigint NULL;

alter table `tp_cost_positions` drop foreign key `tp_cost_positions_ibfk_2`;
alter table `tp_cost_positions` change `position_id` `position_id` bigint   NULL;

alter table `tp_position_requirements` drop foreign key `tp_position_requirements_ibfk_1`;
alter table `tp_position_requirements` change `position_id` `position_id` bigint   NOT NULL;

alter table `tp_position_responsibilities` drop foreign key `tp_position_responsibilities_ibfk_1`;
alter table `tp_position_responsibilities` change `position_id` `position_id` bigint   NOT NULL;

alter table `tp_position_skills` drop foreign key `tp_position_skills_ibfk_1`;
alter table `tp_position_skills` change `position_id` `position_id` bigint   NOT NULL;

alter table `tp_position_steps` drop foreign key `tp_position_steps_ibfk_1`;
alter table `tp_position_steps` change `position_id` `position_id` bigint   NOT NULL;

alter table `tp_report_scheduler` drop foreign key `tp_report_scheduler_ibfk_2`;
alter table `tp_report_scheduler` change `position_id` `position_id` bigint   NULL;

alter table `tp_requisition_approval_feedback` drop foreign key `tp_requisition_approval_feedback_ibfk_1`;
alter table `tp_requisition_approval_feedback` change `position_id` `position_id` bigint   NOT NULL;

alter table `tp_positions` change `position_id` `position_id` bigint NOT NULL AUTO_INCREMENT;

alter table `tp_appointments_notifications` change `position_id` `position_id` bigint   NOT NULL;

alter table `tp_position_step_users` change `position_id` `position_id` bigint   NOT NULL;

alter table `tp_position_vendors` change `position_id` `position_id` bigint   NOT NULL;

alter table `tp_applicant_selection_process` 
add foreign key `tp_applicant_selection_process_ibfk_3`(`position_id`) references `tp_positions` (`position_id`);

alter table `tp_applicants` 
add foreign key `tp_applicants_ibfk_3`(`applicant_position_id`) references `tp_positions` (`position_id`);

alter table `tp_appointments` 
add foreign key `tp_appointments_ibfk_2`(`position_id`) references `tp_positions` (`position_id`);

alter table `tp_cost_positions` 
add foreign key `tp_cost_positions_ibfk_2`(`position_id`) references `tp_positions` (`position_id`);

alter table `tp_position_requirements` 
add foreign key `tp_position_requirements_ibfk_1`(`position_id`) references `tp_positions` (`position_id`);

alter table `tp_position_responsibilities` 
add foreign key `tp_position_responsibilities_ibfk_1`(`position_id`) references `tp_positions` (`position_id`);

alter table `tp_position_skills` 
add foreign key `tp_position_skills_ibfk_1`(`position_id`) references `tp_positions` (`position_id`);

alter table `tp_position_steps` 
add foreign key `tp_position_steps_ibfk_1`(`position_id`) references `tp_positions` (`position_id`);

alter table `tp_report_scheduler` 
add foreign key `tp_report_scheduler_ibfk_2`(`position_id`) references `tp_positions` (`position_id`);

alter table `tp_requisition_approval_feedback` 
add foreign key `tp_requisition_approval_feedback_ibfk_1`(`position_id`) references `tp_positions` (`position_id`);

CREATE TABLE `tp_custom_field_values_applicant` (                                                                                          
    `custom_field_id` int(11) NOT NULL,                                                                                                      
    `string_value` varchar(250) default NULL,                                                                                                
    `number_value` double default NULL,                                                                                                      
    `date_value` date default NULL,                                                                                                          
    `entity_id` bigint(20) default NULL,                                                                                                     
    KEY `FK_tp_custom_field_values` (`custom_field_id`),                                                                                     
    KEY `FK_tp_custom_field_values_applicant` (`entity_id`),                                                                                 
    CONSTRAINT `tp_custom_field_values_applicant_ibfk_1` FOREIGN KEY (`custom_field_id`) REFERENCES `tp_custom_fields` (`custom_field_id`),  
    CONSTRAINT `tp_custom_field_values_applicant_ibfk_2` FOREIGN KEY (`entity_id`) REFERENCES `tp_applicants` (`applicant_id`)               
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `tp_custom_field_values_position` (                                                                                          
   `custom_field_id` int(11) NOT NULL,                                                                                                     
   `string_value` varchar(250) default NULL,                                                                                               
   `number_value` double default NULL,                                                                                                     
   `number_value_to` double default NULL,                                                                                                  
   `date_value` date default NULL,                                                                                                         
   `date_value_to` date default NULL,                                                                                                      
   `range_criteria` varchar(2) default NULL,                                                                                               
   `entity_id` bigint(20) default NULL,                                                                                                    
   KEY `FK_tp_custom_field_values_position` (`custom_field_id`),                                                                           
   KEY `FK_tp_custom_field_values_position_1` (`entity_id`),                                                                               
   CONSTRAINT `tp_custom_field_values_position_ibfk_1` FOREIGN KEY (`custom_field_id`) REFERENCES `tp_custom_fields` (`custom_field_id`),  
   CONSTRAINT `tp_custom_field_values_position_ibfk_2` FOREIGN KEY (`entity_id`) REFERENCES `tp_positions` (`position_id`)                 
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

UPDATE tp_custom_fields
SET custom_field_options = REPLACE(custom_field_options, '[', '{')
WHERE custom_field_options IS NOT NULL;

UPDATE tp_custom_fields
SET custom_field_options = REPLACE(custom_field_options, ']', '}')
WHERE custom_field_options IS NOT NULL;

UPDATE tp_custom_fields
SET custom_field_options = REPLACE(custom_field_options, ',', '|')
WHERE custom_field_options IS NOT NULL;

INSERT INTO tp_custom_field_values_position
(custom_field_id, string_value, entity_id)
SELECT custom_field_id, custom_field_value, entity_id
FROM tp_custom_field_values
WHERE custom_field_id IN (SELECT custom_field_id
			FROM tp_custom_fields
			WHERE custom_field_entity_type = 2 && custom_field_type != 'date');

INSERT INTO tp_custom_field_values_position
(custom_field_id, date_value, entity_id)
SELECT custom_field_id, STR_TO_DATE(custom_field_value,'%d/%m/%Y'), entity_id
FROM tp_custom_field_values
WHERE custom_field_id IN (SELECT custom_field_id
			FROM tp_custom_fields
			WHERE custom_field_entity_type = 2 && custom_field_type = 'date');


INSERT INTO tp_custom_field_values_applicant
(custom_field_id, string_value, entity_id)
SELECT custom_field_id, custom_field_value, entity_id
FROM tp_custom_field_values
WHERE custom_field_id IN (SELECT custom_field_id
			FROM tp_custom_fields
			WHERE custom_field_entity_type = 1 && custom_field_type != 'date');

INSERT INTO tp_custom_field_values_applicant
(custom_field_id, date_value, entity_id)
SELECT custom_field_id, STR_TO_DATE(custom_field_value,'%d/%m/%Y'), entity_id
FROM tp_custom_field_values
WHERE custom_field_id IN (SELECT custom_field_id
			FROM tp_custom_fields
			WHERE custom_field_entity_type = 1 && custom_field_type = 'date');
			
drop table `tp_custom_field_values`;

drop table `tp_bulk_import_session_custom_fields`;

CREATE TABLE `tp_bulk_import_session_custom_fields` (                                                                                          
	`custom_field_id` int(11) NOT NULL,                                                                                                          
	`string_value` varchar(250) default NULL,                                                                                                    
	`number_value` double default NULL,                                                                                                          
	`date_value` date default NULL,                                                                                                              
	`entity_id` varchar(100) NOT NULL,                                                                                                           
	KEY `FK_tp_bulk_import_session_custom_fields_1` (`custom_field_id`),                                                                         
	KEY `FK_tp_bulk_import_session_custom_fields_3` (`entity_id`),                                                                               
	CONSTRAINT `tp_bulk_import_session_custom_fields_ibfk_2` FOREIGN KEY (`custom_field_id`) REFERENCES `tp_custom_fields` (`custom_field_id`),  
	CONSTRAINT `tp_bulk_import_session_custom_fields_ibfk_3` FOREIGN KEY (`entity_id`) REFERENCES `tp_bulk_import_sessions` (`session_id`)       
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

UPDATE tp_custom_fields
SET custom_field_type='listbox' WHERE custom_field_type='select';

insert into `tp_template_types` (`template_type_id`,`template_type`) values ( 19,'Vendor Resume Upload Notification');
insert into `tp_template_types` (`template_type_id`,`template_type`) values ( 20,'Rejection Email to Vendor');

insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values ( '19','3');
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values ( '19','14');

insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values ( '20','3');
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values ( '20','14');

insert into `tp_templates` (`template_id`,`template_code`,`template_name`,`template_subject_file`,`template_content_file`,`template_auto`,`template_format`,`template_private`,`template_type_id`,`template_is_default`,`template_date_created`,`user_id`) values ( NULL,'vendorResumeUploadNotification','Resume Upload by vendor','vendorResumeUploadNotificationSubject.vm','vendorResumeUploadNotificationContent.vm','0','0','0','19','1','2009-06-22 00:00:00','1');
insert into `tp_templates` (`template_id`,`template_code`,`template_name`,`template_subject_file`,`template_content_file`,`template_auto`,`template_format`,`template_private`,`template_type_id`,`template_is_default`,`template_date_created`,`user_id`) values ( NULL,'rejectEmailToVendor','Reject Email To Vendor','rejectEmailToVendorSubject.vm','rejectEmailToVendorContent.vm','0','0','0','20','1','2009-06-22 00:00:00','1');

insert into `tp_application_properties` (`application_property`,`application_property_value`) values ( 'vendor_resume_upload_notification','1');
insert into `tp_application_properties` (`application_property`,`application_property_value`) values ( 'rejection_email_to_vendor','1');


UPDATE tp_permissions SET permission_rank=permission_rank+1 WHERE permission_rank >49;
INSERT INTO `tp_permissions` 
(`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) 
VALUES (68,50,'Publish Position for Employee Portal',46,'1');

insert into tp_role_permissions (role_id,permission_id) values (1,68);
insert into tp_role_permissions (role_id,permission_id) values (3,68);
insert into tp_role_permissions (role_id,permission_id) values (4,68);
insert into tp_role_permissions (role_id,permission_id) values (6,68);

insert into tp_user_permissions (user_id,permission_id)
(select tu.user_id, 68 from tp_users tu left join tp_user_roles tur on (tu.user_id=tur.user_id) where tur.ROLE_ID in (1,3,4,6));

ALTER TABLE `tp_positions` ADD COLUMN `is_published_for_emp_portal` char(1) DEFAULT '0' NOT NULL AFTER `is_published_for_walk_in`;

UPDATE `tp_positions` SET is_published_for_emp_portal='1' WHERE position_id is not null;

CREATE TABLE `tp_recently_used_sources` (  
    `source_id` int(11) NOT NULL,            
    `time_used` datetime NOT NULL,
    UNIQUE KEY `source_id` (`source_id`)           
  ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

 insert into `tp_application_properties` (`application_property`,`application_property_value`) values ( 'enable_single_sign_on','0');
 insert into `tp_permission_module` (`permission_id`,`module_id`) values (68,22);
  