use talentpool;

CREATE TABLE `tp_cr_column_customfield_map` (                                                                                                             
		`cr_cf_map_id` int(11) NOT NULL auto_increment,                                                                                                         
        `column_property` varchar(100) NOT NULL,                                                                                                                
        `column_display_name` varchar(100) default NULL,                                                                                                        
        `custom_field_id` int(11) default NULL,                                                                                                                 
        `entity_type` int(11) default NULL,                                                                                                                     
        PRIMARY KEY  (`cr_cf_map_id`),                                                                                                                          
        KEY `FK_tp_cr_column_customfield_map_column_property` (`column_property`),                                                                              
        CONSTRAINT `FK_tp_cr_column_customfield_map_column_property` FOREIGN KEY (`column_property`) REFERENCES `tp_custom_report_columns` (`column_property`)  
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1; 

CREATE TABLE `tp_cr_report_types` (             
        `cr_report_type_id` bigint(11) NOT NULL,      
        `cr_report_type_name` varchar(250) NOT NULL,  
        `cr_report_type_kind_id` char(2) NOT NULL,    
        PRIMARY KEY  (`cr_report_type_id`)            
        ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `tp_cr_report_type_column_mapping` (                                                                                                 
       `cr_report_type_id` bigint(11) NOT NULL,                                                                                                        
       `column_property` varchar(100) NOT NULL,                                                                                                        
       KEY `FK_tp_cr_report_type_column_mapping` (`column_property`),                                                                                  
       KEY `FK1_tp_cr_report_type_column_mapping` (`cr_report_type_id`),                                                                               
       CONSTRAINT `tp_cr_report_type_column_mapping_ibfk_2` FOREIGN KEY (`cr_report_type_id`) REFERENCES `tp_cr_report_types` (`cr_report_type_id`),   
       CONSTRAINT `tp_cr_report_type_column_mapping_ibfk_1` FOREIGN KEY (`column_property`) REFERENCES `tp_custom_report_columns` (`column_property`)  
       ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

       
CREATE TABLE `tp_cr_scheduler` (                      
		`scheduler_id` tinyint(4) NOT NULL auto_increment,  
        `scheduler_name` varchar(50) NOT NULL,              
        `scheduler_start_hour` timestamp NOT NULL,           
        `scheduler_frequency` tinyint(4) NOT NULL,          
        `last_run_status` char(1) default '0',              
        `last_run_starttime` datetime default NULL,         
        `last_run_endtime` datetime default NULL,           
        `last_success_run` datetime default NULL,           
        `current_status` char(1) default '0',               
        PRIMARY KEY  (`scheduler_id`)                       
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

ALTER TABLE tp_cr_event_summary CHANGE 
step_level step_level tinyint(2) NOT NULL;

ALTER TABLE tp_cr_event_summary 
ADD CONSTRAINT FK_tp_cr_event_summary_step_level 
FOREIGN KEY (step_level) REFERENCES tp_step_level (step_level) 
ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE tp_step_master CHANGE 
step_level step_level tinyint(2) NOT NULL;

ALTER TABLE tp_cr_event_summary 
ADD COLUMN backlog_ids text AFTER inprocess,
ADD COLUMN received_ids text AFTER backlog_ids,
ADD COLUMN cleared_ids text AFTER received_ids,
ADD COLUMN rejected_ids text AFTER cleared_ids,
ADD COLUMN inprocess_ids text AFTER rejected_ids;

ALTER TABLE `tp_cr_activity_summary` ADD CONSTRAINT `FK_tp_cr_activity_summary` FOREIGN KEY (`user_id`) REFERENCES `tp_users` (`USER_ID`);
ALTER TABLE `tp_cr_activity_summary` ADD INDEX `tp_cr_activity_summary_activity_date` (`activity_date`);

ALTER TABLE `tp_custom_fields` ADD INDEX `tp_custom_fields_entity_type` (`custom_field_entity_type`);

ALTER TABLE `tp_cr_candidate_master` ADD CONSTRAINT `FK_tp_cr_candidate_master` FOREIGN KEY (`applicant_id`) REFERENCES `tp_applicants` (`applicant_id`);
ALTER TABLE `tp_cr_candidate_master` ADD UNIQUE `tp_cr_candidate_master_applicant_id` (`applicant_id`);
ALTER TABLE `tp_cr_candidate_master` ADD INDEX `user_id` (`user_id`);
ALTER TABLE `tp_cr_candidate_master` ADD INDEX `source_id` (`source_id`);

ALTER TABLE `tp_cr_event_log` ADD CONSTRAINT `FK_tp_cr_event_log_process_id` FOREIGN KEY (`process_id`) REFERENCES `tp_applicant_selection_process` (`process_id`);
ALTER TABLE `tp_cr_event_log` ADD CONSTRAINT `FK_tp_cr_event_log_applicant_id` FOREIGN KEY (`applicant_id`) REFERENCES `tp_applicants` (`applicant_id`);
ALTER TABLE `tp_cr_event_log` ADD CONSTRAINT `FK_tp_cr_event_log_position_id` FOREIGN KEY (`position_id`) REFERENCES `tp_positions` (`position_id`);
ALTER TABLE `tp_cr_event_log` ADD CONSTRAINT `FK_tp_cr_event_log_user_id` FOREIGN KEY (`user_id`) REFERENCES `tp_users` (`USER_ID`);
ALTER TABLE `tp_cr_event_log` ADD INDEX `event_log_process_date` (`process_moved_date`);

ALTER TABLE `tp_cr_event_summary` ADD INDEX `event_summary_process_date` (`process_date`);

ALTER TABLE `tp_position_locations` ADD CONSTRAINT `FK_tp_position_locations_position_id` FOREIGN KEY (`position_id`) REFERENCES `tp_positions` (`position_id`);

ALTER TABLE `tp_position_cost_center` ADD CONSTRAINT `FK_tp_position_cost_center_position_id` FOREIGN KEY (`position_id`) REFERENCES `tp_positions` (`position_id`);

ALTER TABLE `tp_position_business_unit` ADD CONSTRAINT `FK_tp_position_business_unit_position_id` FOREIGN KEY (`position_id`) REFERENCES `tp_positions` (`position_id`);

ALTER TABLE `tp_positions` ADD INDEX `tp_positions_status` (`position_status`);

ALTER TABLE `tp_cr_position_master` ADD CONSTRAINT `FK_tp_cr_position_master_position_id` FOREIGN KEY (`position_id`) REFERENCES `tp_positions` (`position_id`);
ALTER TABLE `tp_cr_position_master` ADD INDEX `position_master_status` (`position_status`);

ALTER TABLE `tp_custom_fields` DROP COLUMN `custom_field_report`;
ALTER TABLE tp_cr_activity_summary DROP COLUMN user_name;

CREATE TABLE `tp_greytip_applicant_step_level_change` (      
				`applicant_id` bigint(20) NOT NULL,         
				`position_id` bigint(20) NOT NULL,          
				`step_level_id` char(1) NOT NULL,           
				`date_created` datetime NOT NULL,           
				`greytip_scheduler_run_date` datetime default NULL  
	) ENGINE=InnoDB DEFAULT CHARSET=latin1;
	
ALTER TABLE tp_cr_position_master CHANGE position_note position_note VARCHAR (1000) NULL COLLATE latin1_swedish_ci;


ALTER TABLE tp_cr_candidate_master 
CHANGE custom_field_1 custom_field_1 varchar(250) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
CHANGE custom_field_2 custom_field_2 varchar(250) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
CHANGE custom_field_3 custom_field_3 varchar(250) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
CHANGE custom_field_4 custom_field_4 varchar(250) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
CHANGE custom_field_5 custom_field_5 varchar(250) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
CHANGE custom_field_6 custom_field_6 varchar(250) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
CHANGE custom_field_7 custom_field_7 varchar(250) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
CHANGE custom_field_8 custom_field_8 varchar(250) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
CHANGE custom_field_9 custom_field_9 varchar(250) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
CHANGE custom_field_10 custom_field_10 varchar(250) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
CHANGE custom_field_11 custom_field_11 varchar(250) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
CHANGE custom_field_12 custom_field_12 varchar(250) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
CHANGE custom_field_13 custom_field_13 varchar(250) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
CHANGE custom_field_14 custom_field_14 varchar(250) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
CHANGE custom_field_15 custom_field_15 varchar(250) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
CHANGE custom_field_16 custom_field_16 varchar(250) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
CHANGE custom_field_17 custom_field_17 varchar(250) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
CHANGE custom_field_18 custom_field_18 varchar(250) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
CHANGE custom_field_19 custom_field_19 varchar(250) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
CHANGE custom_field_20 custom_field_20 varchar(250) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL;

ALTER TABLE tp_cr_position_master 
CHANGE custom_field_1 custom_field_1 varchar(250) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
CHANGE custom_field_2 custom_field_2 varchar(250) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
CHANGE custom_field_3 custom_field_3 varchar(250) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
CHANGE custom_field_4 custom_field_4 varchar(250) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
CHANGE custom_field_5 custom_field_5 varchar(250) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
CHANGE custom_field_6 custom_field_6 varchar(250) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
CHANGE custom_field_7 custom_field_7 varchar(250) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
CHANGE custom_field_8 custom_field_8 varchar(250) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
CHANGE custom_field_9 custom_field_9 varchar(250) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
CHANGE custom_field_10 custom_field_10 varchar(250) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
CHANGE custom_field_11 custom_field_11 varchar(250) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
CHANGE custom_field_12 custom_field_12 varchar(250) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
CHANGE custom_field_13 custom_field_13 varchar(250) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
CHANGE custom_field_14 custom_field_14 varchar(250) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
CHANGE custom_field_15 custom_field_15 varchar(250) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
CHANGE custom_field_16 custom_field_16 varchar(250) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
CHANGE custom_field_17 custom_field_17 varchar(250) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
CHANGE custom_field_18 custom_field_18 varchar(250) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
CHANGE custom_field_19 custom_field_19 varchar(250) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
CHANGE custom_field_20 custom_field_20 varchar(250) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL;

ALTER TABLE tp_cr_event_summary 
CHANGE rejected rejected smallint DEFAULT '0' NULL ,
CHANGE onhold onhold smallint DEFAULT '0' NULL , 
CHANGE joined joined smallint DEFAULT '0' NULL , 
CHANGE received received smallint DEFAULT '0' NULL , 
CHANGE cleared cleared smallint DEFAULT '0' NULL , 
CHANGE inprocess inprocess smallint DEFAULT '0' NULL , 
CHANGE backlog backlog smallint DEFAULT '0' NULL;