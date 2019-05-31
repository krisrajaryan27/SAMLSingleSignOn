use talentpool;
CREATE TABLE tp_onboarding_candidates (candidate_id bigint(20) NOT NULL AUTO_INCREMENT, candidate_user_name varchar(100), 
candidate_first_name varchar(100), candidate_last_name varchar(100),
candidate_password varchar(100), candidate_email varchar(100), candidate_cell_phone varchar(100),
PRIMARY KEY (`candidate_id`));


CREATE TABLE tp_candidate_uuid_map (                                                                               
                    `candidate_id` bigint(20) NOT NULL,                                                                                
                    `uuid` varchar(150) NOT NULL,                                                                                 
                    `date_created` timestamp NULL DEFAULT NULL,                                                                   
                    KEY `tp_candidate_uuid_map` (`candidate_id`),                                                                        
                    CONSTRAINT `tp_candidate_uuid_map` FOREIGN KEY (`candidate_id`) REFERENCES `tp_onboarding_candidates` (`candidate_id`) ON DELETE CASCADE  
                  );
                  
CREATE TABLE tp_candidate_applicant_mapping (
id bigint(20) NOT NULL AUTO_INCREMENT,
candidate_id bigint(20) NOT NULL,
applicant_id bigint(20),
PRIMARY KEY(id),
UNIQUE(candidate_id,applicant_id),
FOREIGN KEY (candidate_id) REFERENCES tp_onboarding_candidates(candidate_id),
FOREIGN KEY (applicant_id) REFERENCES tp_applicants (applicant_id)
);

CREATE TABLE `tp_position_approval_scheduler` (                         
                   `scheduler_id` tinyint(4) NOT NULL AUTO_INCREMENT,     
                   `scheduler_name` varchar(50) NOT NULL,                 
                   `scheduler_start_hour` timestamp NULL DEFAULT NULL,    
                   `scheduler_frequency` tinyint(4) NOT NULL,             
                   `last_run_status` char(1) DEFAULT '0',                 
                   `last_run_starttime` timestamp NULL DEFAULT NULL,      
                   `last_run_endtime` timestamp NULL DEFAULT NULL,        
                   `last_success_run` timestamp NULL DEFAULT NULL,        
                   `current_status` char(1) DEFAULT '0',                  
                   PRIMARY KEY (`scheduler_id`)                           
                 )   ;
                 
CREATE TABLE `tp_posApproval_last_update` (                        
                     `last_id` bigint(20) NOT NULL AUTO_INCREMENT,           
                     `last_run_date` timestamp NULL DEFAULT NULL,            
                     PRIMARY KEY (`last_id`)                                 
                   ) ;
                   
                   
 CREATE TABLE `tp_duplicate_position_detection_settings` (  
                                   `field_id` smallint(6) NOT NULL,                
                                   `field_type` char(1) NOT NULL,                  
                                   `check_type` char(1) NOT NULL,                  
                                   `check_for` char(1) NOT NULL DEFAULT '0'        
                                 ) ;
                                 
ALTER TABLE tp_onboarding_candidates ADD COLUMN password_date_modified TIMESTAMP NULL;        

CREATE TABLE `tp_tabular_custom_fields` (                                                                                          
                                    `table_id` int(11) NOT NULL AUTO_INCREMENT,                                                                                                      
                                    `table_name` varchar(250) DEFAULT NULL,
									PRIMARY KEY (`table_id`));

CREATE TABLE `tp_tabular_custom_field_table_columns_mapping` (                                                                                          
                                    `table_id` int(11) NOT NULL,                                                                                                      
                                    `custom_field_id` int(11) NOT NULL,
				    				KEY `FK_tp_tabular_custom_fields` (`table_id`),                                                                                     
                                    KEY `FK_tp_custom_fields` (`custom_field_id`),                                                                                 
                                    CONSTRAINT `tp_tabular_custom_field_table_columns_mapping_ibfk_1` FOREIGN KEY (`table_id`) REFERENCES `tp_tabular_custom_fields` (`table_id`),  
                                    CONSTRAINT `tp_tabular_custom_field_table_columns_mapping_ibfk_2` FOREIGN KEY (`custom_field_id`) REFERENCES `tp_custom_fields` (`custom_field_id`));

Alter table tp_custom_field_values_applicant add column row_id varchar(10) default null, add column table_id int(11) DEFAULT NULL;

Alter table tp_tabular_custom_fields add column entity_type int(11);

Alter table tp_custom_field_values_applicant Add constraint FK_TABLE_ID FOREIGN KEY (table_id) references tp_tabular_custom_field_table_columns_mapping (table_id);

Alter table tp_tabular_custom_field_table_columns_mapping add constraint UNIQUE_TAB_CUSTFIELD UNIQUE(table_id,custom_field_id);

Alter table tp_custom_fields add column table_id int(11);

Alter table tp_custom_field_values_position add column table_id int(11);
Alter table tp_bulk_import_session_custom_fields add column table_id int(11);
Alter table tp_excel_import_custom_field_values add column table_id int(11);

alter table tp_applicant_employment_history add column reason_for_leaving text;
alter table tp_applicant_employment_history add column duties_involved text;

alter table tp_applicant_employment_history add column gross_salary varchar(15), add column allowance varchar(15);

alter table tp_applicant_educational_info add column remarks text;
alter table tp_applicant_educational_info add column from_year date;

CREATE TABLE `tp_dynamic_field` (
  `id` smallint(4) NOT NULL AUTO_INCREMENT,
  `display_name` varchar(250) DEFAULT NULL,
  `field_type` tinyint(3) DEFAULT NULL,
  `regex` varchar(1000) DEFAULT NULL,
  `regex_message` text,
  `option_list` mediumtext,
  `default_value` varchar(250) DEFAULT NULL,
  `watermark_placeholder` varchar(250) DEFAULT NULL,
  `api_url` varchar(500) DEFAULT NULL,
  `css_class_name` varchar(200) DEFAULT NULL,
  `entity_type` tinyint(3) DEFAULT NULL,
  `reference_field_id` varchar(250) DEFAULT NULL,
  `property_name` varchar(250) DEFAULT NULL,
  `parent_id` smallint(4) DEFAULT NULL,
  `entity_name` varchar(200) DEFAULT NULL,
  `no_of_rows` tinyint(4) DEFAULT '0',
  `is_required` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ;


ALTER TABLE tp_feedback_field_categories MODIFY category_name VARCHAR(500);

ALTER TABLE tp_feedback_fields MODIFY feedback_field_title VARCHAR(500);

ALTER TABLE tp_position_steps add column applicant_feedback_form_id int(11);

ALTER TABLE tp_custom_field_values_applicant MODIFY string_value VARCHAR(1000);
ALTER TABLE tp_custom_field_values_position MODIFY string_value VARCHAR(1000);

CREATE TABLE `tp_user_scheduler` (                         
                   `scheduler_id` tinyint(4) NOT NULL auto_increment,     
                   `scheduler_name` varchar(50) NOT NULL,                 
                   `scheduler_start_hour` tinyint(4) NOT NULL,            
                   `scheduler_frequency` tinyint(4) NOT NULL,             
                   `last_run_status` char(1) default '0',                 
                   `last_run_starttime` datetime default NULL,            
                   `last_run_endtime` datetime default NULL,              
                   `last_success_run` datetime default NULL,              
                   `current_status` char(1) default '0',                  
                   PRIMARY KEY  (`scheduler_id`)                          
                 );
                 



CREATE TABLE `tp_applicant_questionnaire_response` (                                                                                 
                                  `process_id` bigint(20) NOT NULL AUTO_INCREMENT,                                                                              
                                  `applicant_id` bigint(20) NOT NULL,                                                                                           
                                  `position_step_id_from` int(11) NOT NULL,                                                                                     
                                  `position_step_id_to` int(11) NOT NULL,                                                                                       
                                  `user_id` bigint(20) NOT NULL,                                                                                                
                                  `process_date_created` timestamp NULL DEFAULT NULL,                                                                           
                                  `process_moved_date` timestamp NULL DEFAULT NULL,                                                                             
                                  `position_id` bigint(20) NOT NULL,                                                                                            
                                  `selection_process_is_hidden` char(1) NOT NULL DEFAULT '0',                                                                   
                                  `hold_for_days` smallint(6) NOT NULL DEFAULT '0',                                                                             
                                  PRIMARY KEY (`process_id`),                                                                                                   
                                  KEY `user_id` (`user_id`),                                                                                                    
                                  KEY `applicant_id` (`applicant_id`),                                                                                          
                                  KEY `tp_applicant_questionnaire_response_ibfk_3` (`position_id`),                                                                  
                                  CONSTRAINT `tp_applicant_questionnaire_response_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `tp_users` (`USER_ID`),                 
                                  CONSTRAINT `tp_applicant_questionnaire_response_ibfk_2` FOREIGN KEY (`applicant_id`) REFERENCES `tp_applicants` (`applicant_id`),  
                                  CONSTRAINT `tp_applicant_questionnaire_response_ibfk_3` FOREIGN KEY (`position_id`) REFERENCES `tp_positions` (`position_id`)      
                                ) ;  
                                
CREATE TABLE `tp_applicant_questionnaire_traits` (                                                                                              
                                         `process_id` bigint(20) NOT NULL,                                                                                                                 
                                         `feedback_form_field_id` bigint(20) NOT NULL,                                                                                                     
                                         `trait_comment` text,                                                                                                                             
                                         `user_id` bigint(20) NOT NULL DEFAULT '0',                                                                                                        
                                         `rating_field_id` int(11) DEFAULT NULL,                                                                                                           
                                         `select_field_id` varchar(250) DEFAULT NULL,                                                                                                      
                                         `feedback_form_id` int(11) NOT NULL,                                                                                                              
                                         UNIQUE KEY `process_id_2` (`process_id`,`feedback_form_field_id`,`user_id`),                                                                      
                                         KEY `process_id` (`process_id`),                                                                                                                  
                                         KEY `position_step_trait_id` (`feedback_form_field_id`),                                                                                          
                                         KEY `tp_applicant_questionnaire_traits_1` (`user_id`),                                                                                        
                                         KEY `FK_tp_applicant_questionnaire_traits` (`rating_field_id`),                                                                               
                                         KEY `FK_tp_applicant_questionnaire_traits_4` (`feedback_form_id`),                                                                            
                                         CONSTRAINT `tp_applicant_questionnaire_traits_1` FOREIGN KEY (`user_id`) REFERENCES `tp_users` (`USER_ID`),                                   
                                         CONSTRAINT `tp_applicant_questionnaire_traits_ibfk_1` FOREIGN KEY (`process_id`) REFERENCES `tp_applicant_questionnaire_response` (`process_id`),  
                                         CONSTRAINT `tp_applicant_questionnaire_traits_ibfk_2` FOREIGN KEY (`rating_field_id`) REFERENCES `tp_rating_fields` (`rating_field_id`),      
                                         CONSTRAINT `tp_applicant_questionnaire_traits_ibfk_3` FOREIGN KEY (`feedback_form_id`) REFERENCES `tp_feedback_forms` (`feedback_form_id`)    
                                       ) ;                                
alter table tp_audit_entries modify column entity_id  varchar(40);
ALTER TABLE tp_applicants add column profile_pic_path varchar(250);

CREATE TABLE IF NOT EXISTS `tp_applicant_offer_details` (                                                                                                                                             
                `applicant_id` bigint(20) NOT NULL,
                `position_id` bigint(20) NOT NULL,
                `offer_code` varchar(40),
                `template_id` int(11),
				`attribute_name` varchar(100),
                `attribute_value` varchar(100),
                `version` int(4),
                `date_created` date,
	            foreign key(position_id) references tp_positions(position_id),
	            foreign key (applicant_id) references tp_applicants(applicant_id),
                KEY `FK_template_variables` (`template_id`),
                CONSTRAINT `FK_template_variables` FOREIGN KEY (`template_id`) REFERENCES `tp_offersheet_templates` (`template_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

alter table tp_applicant_offer_sheet_details add column version int(4);
ALTER table tp_report_scheduler add column template_id int(11);
ALTER TABLE `tp_audit_entries`
  CHANGE COLUMN `audit_desc` `audit_desc` VARCHAR(1000)NOT NULL DEFAULT' ';
  
ALTER TABLE `tp_applicant_offer_details` DROP FOREIGN KEY `FK_template_variables`;