use talentpool;

CREATE TABLE `tp_step_level` (             
                 `step_level` tinyint(2) NOT NULL,        
                 `step_level_name` varchar(20) NOT NULL,  
                 `modified_date` datetime default NULL,   
                 `modified_by` bigint(20) default NULL,   
                 PRIMARY KEY  (`step_level`)              
               ) ENGINE=InnoDB DEFAULT CHARSET=latin1;                   

CREATE TABLE `tp_step_master` (                                                                        
                  `step_id` tinyint(4) NOT NULL,                                                                       
                  `step_name` varchar(50) NOT NULL,                                                                    
                  `step_desc` varchar(255) default NULL,                                                               
                  `step_rank` tinyint(4) NOT NULL default '0',                                                         
                  `system_step` char(1) NOT NULL default '0',                                                          
                  `step_level` tinyint(4) NOT NULL,                                                                    
                  `step_schedulable` char(1) NOT NULL default '0',                                                     
                  `step_disabled` char(1) NOT NULL default '0',                                                        
                  `step_deleted` char(1) NOT NULL default '0',                                                         
                  `user_id` bigint(20) NOT NULL,                                                                       
                  `date_modified` datetime NOT NULL,                                                                   
                  `date_created` datetime NOT NULL,                                                                    
                  PRIMARY KEY  (`step_id`),                                                                            
                  KEY `FK_tp_step_master` (`step_level`),                                                              
                  CONSTRAINT `FK_tp_step_master` FOREIGN KEY (`step_level`) REFERENCES `tp_step_level` (`step_level`)  
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
                
CREATE TABLE `tp_applicant_text_resumes` (                                                                                
	     `applicant_id` bigint(20) NOT NULL,                                                                                     
	     `applicant_text_resume` longtext,                                                                                                 
	     KEY `FK_tp_applicant_text_resumes` (`applicant_id`),                                                                    
	     CONSTRAINT `tp_applicant_text_resumes_ibfk_1` FOREIGN KEY (`applicant_id`) REFERENCES `tp_applicants` (`applicant_id`)  
	   ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `tp_resume_types` (                     
               `resume_type_id` int(11) NOT NULL auto_increment,  
               `resume_type` varchar(250) NOT NULL,               
               PRIMARY KEY  (`resume_type_id`)                    
             ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `tp_custom_reports` (               
                     `report_id` bigint(20) NOT NULL,               
                     `report_name` varchar(255) NOT NULL,           
                     `report_desc` varchar(255) NOT NULL,           
                     `created_by` varchar(255) NOT NULL,            
                     `date_created` datetime NOT NULL,              
                     `modified_by` varchar(255) NOT NULL,           
                     `date_modified` datetime NOT NULL,             
                     `xml_file_path` varchar(255) default '',       
                     `template_file_path` varchar(255) default '',  
                     `query_file_path` varchar(255) default '',     
                     PRIMARY KEY  (`report_id`)                     
                   ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
                   
CREATE TABLE `tp_cr_table_type` (                   
                    `table_type_id` tinyint(4) NOT NULL,              
                    `table_short_name` varchar(10) NOT NULL,          
                    `table_db_name` varchar(50) NOT NULL default '',  
                    `table_column_join` varchar(50) NOT NULL,         
                    PRIMARY KEY  (`table_type_id`)                    
                  ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
                  
CREATE TABLE `tp_custom_report_columns` (                                                                                                       
		`column_id` smallint(6) NOT NULL auto_increment,                                                                                              
		`column_property` varchar(100) NOT NULL,                                                                                                      
		`column_display_name` varchar(100) NOT NULL,                                                                                                  
		`column_category` char(1) NOT NULL default '',                                                                                                
		`column_data_type` varchar(100) NOT NULL,                                                                                                     
		`column_dbname` varchar(150) NOT NULL,                                                                                                        
		`column_width` int(10) NOT NULL default '75',                                                                                              
		`group_by_column` varchar(100) default NULL,                                                                                                  
		`order_by_column` varchar(100) default NULL,                                                                                                  
		`is_custom_field` char(1) NOT NULL default '0',                                                                                               
		`is_active` char(1) NOT NULL default '1',                                                                                                     
		`table_type` tinyint(4) default NULL,                                                                                                         
		`value_type` char(1) NOT NULL default '1',                                                                                                    
	    PRIMARY KEY  (`column_id`),                                                                                                                   
	    UNIQUE KEY `unique_column_property` (`column_property`),                                                                                      
	    KEY `FK_table_type` (`table_type`),                                                                                                           
	    KEY `FK_tp_custom_report_columns` (`group_by_column`),                                                                                        
	    KEY `FK_ORDER_BY_COLUMN` (`order_by_column`),                                                                                                 
	    CONSTRAINT `FK_GROUP_BY_COLUMN` FOREIGN KEY (`group_by_column`) REFERENCES `tp_custom_report_columns` (`column_property`) ON UPDATE CASCADE,  
	    CONSTRAINT `FK_ORDER_BY_COLUMN` FOREIGN KEY (`order_by_column`) REFERENCES `tp_custom_report_columns` (`column_property`) ON UPDATE CASCADE,  
	    CONSTRAINT `FK_table_type` FOREIGN KEY (`table_type`) REFERENCES `tp_cr_table_type` (`table_type_id`)                                         
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
                          
CREATE TABLE `tp_cr_candidate_master` (                       
	      `applicant_id` bigint(20) NOT NULL,             
	      `applicant_name` varchar(100) NOT NULL,          
	      `applicant_city` varchar(50) default NULL,    
	      `applicant_email1` varchar(50) default NULL,     
	      `applicant_email2` varchar(50) default NULL,     
	      `applicant_home_phone` varchar(25) default NULL,  
	      `applicant_cell_phone` varchar(25) default NULL,                
	      `applicant_work_phone` varchar(25) default NULL,                
	      `applicant_working_since` date default NULL,                    
	      `applicant_date_created` datetime NOT NULL,                     
	      `current_ctc` varchar(10) default NULL,                         
	      `expected_ctc` varchar(10) default NULL,                        
	      `applicant_notice_period` varchar(25) default NULL,             
	      `is_confidential` char(1) default '0',                          
	      `employee_code` varchar(100) default NULL,                      
	      `applicant_hrms_code` varchar(50) default NULL,                 
	      `applicant_joined` char(1) NOT NULL default '0',                
	      `applicant_date_joined` date default NULL,                      
	      `applicant_current_employer` varchar(250) default NULL,         
	      `position_id` bigint(20) default NULL,                          
	      `position_code` varchar(50) default NULL,                       
	      `position_title` varchar(100) default '',                       
	      `dept_name` varchar(50) default '',                             
	      `applicant_step_id` int(11) default NULL,                       
	      `position_step_title` varchar(100) default '',                  
	      `position_step_level` char(1) default NULL,                     
	      `source_id` int(11) NOT NULL,                                   
	      `source_title` varchar(250) default '',                         
	      `source_type_id` smallint(6) default NULL,                      
	      `source_type` varchar(250) default '',                          
	      `user_id` bigint(20) NOT NULL,                                  
	      `user_name` varchar(50) default NULL,                           
	      `YOP` date default NULL,                                        
	      `grade` varchar(100) default NULL,                              
	      `degree_title` varchar(250) default '',                         
	      `branch_name` varchar(100) default '',                          
	      `institute_name` varchar(150) default '',                       
	      `flags` varchar(250) default NULL,                              
	      `skills` varchar(500) default NULL,                             
	      `status_message` varchar(500) default NULL,                     
	      `trait` varchar(500) default NULL,                              
	      `date_of_birth` date default NULL,                              
	      `passport_number` varchar(10) default NULL,                     
	      `resume_type` varchar(250) default NULL,                        
	      `custom_field_1` varchar(100) default NULL,                     
	      `custom_field_2` varchar(100) default NULL,                     
	      `custom_field_3` varchar(100) default NULL,                     
	      `custom_field_4` varchar(100) default NULL,                     
	      `custom_field_5` varchar(100) default NULL,                     
	      `custom_field_6` varchar(100) default NULL,                     
	      `custom_field_7` varchar(100) default NULL,                     
	      `custom_field_8` varchar(100) default NULL,                     
	      `custom_field_9` varchar(100) default NULL,                     
	      `custom_field_10` varchar(100) default NULL,                    
	      `custom_field_11` varchar(100) default NULL,                    
	      `custom_field_12` varchar(100) default NULL,                    
	      `custom_field_13` varchar(100) default NULL,                    
	      `custom_field_14` varchar(100) default NULL,                    
	      `custom_field_15` varchar(100) default NULL,                    
	      `custom_field_16` varchar(100) default NULL,                    
	      `custom_field_17` varchar(100) default NULL,                    
	      `custom_field_18` varchar(100) default NULL,                    
	      `custom_field_19` varchar(100) default NULL,                    
	      `custom_field_20` varchar(100) default NULL,                    
	      `date_created` datetime NOT NULL                                
	    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
	    
CREATE TABLE `tp_cr_position_master` (            
	     `position_id` bigint(20) NOT NULL,                  
	     `position_code` varchar(50) default NULL,           
	     `position_title` varchar(100) NOT NULL default '',  
	     `position_no_of_openings` smallint(6) NOT NULL,   
	     `position_status_id` char(1) DEFAULT '0' NOT NULL,
	     `position_status` varchar(10) NOT NULL default '',                 
	     `position_date_expiry` datetime default NULL,       
	     `position_date_created` datetime NOT NULL,          
	     `position_date_approved` datetime default NULL,     
	     `position_note` varchar(500) default NULL,          
	     `position_priority` varchar(10) NOT NULL default '',    
	     `position_level` varchar(20) default NULL,          
	     `position_referal_fees` varchar(50) default NULL,   
	     `type_of_vacancy` varchar(20) DEFAULT '' NOT NULL,     
	     `replacement_emp_code` varchar(100) default NULL,   
	     `position_created_by` varchar(50) default NULL,   
	     `position_owner_id` bigint(20) NULL,
	     `position_owner` varchar(50) default NULL,          
	     `position_requested_by` varchar(50) default NULL,   
	     `dept_id` int(10) default NULL,
	     `dept_name` varchar(50) default '',                 
	     `sub_dept_id` int(10) default NULL,
	     `sub_dept` varchar(50) default '',                  
	     `sub2_dept_id` int(10) default NULL,
	     `sub2_dept` varchar(50) default '',                 
	     `sub3_dept_id` int(10) default NULL,
	     `sub3_dept` varchar(50) default '',                 
	     `sub4_dept_id` int(10) default NULL,
	     `sub4_dept` varchar(50) default '',                 
	     `budget_item_name` varchar(250) default '',         
	     `grade_name` varchar(250) default '',               
	     `band_name` varchar(250) default '',                
	     `skills` varchar(500) default NULL,                 
	     `locations` varchar(250) default NULL,              
	     `cost_center` varchar(250) default NULL,            
	     `bu` varchar(250) default NULL,                     
	     `custom_field_1` varchar(100) default NULL,         
	     `custom_field_2` varchar(100) default NULL,         
	     `custom_field_3` varchar(100) default NULL,         
	     `custom_field_4` varchar(100) default NULL,         
	     `custom_field_5` varchar(100) default NULL,         
	     `custom_field_6` varchar(100) default NULL,         
	     `custom_field_7` varchar(100) default NULL,         
	     `custom_field_8` varchar(100) default NULL,         
	     `custom_field_9` varchar(100) default NULL,         
	     `custom_field_10` varchar(100) default NULL,        
	     `custom_field_11` varchar(100) default NULL,        
	     `custom_field_12` varchar(100) default NULL,        
	     `custom_field_13` varchar(100) default NULL,        
	     `custom_field_14` varchar(100) default NULL,        
	     `custom_field_15` varchar(100) default NULL,        
	     `custom_field_16` varchar(100) default NULL,        
	     `custom_field_17` varchar(100) default NULL,        
	     `custom_field_18` varchar(100) default NULL,        
	     `custom_field_19` varchar(100) default NULL,        
	     `custom_field_20` varchar(100) default NULL,        
	     `date_created` datetime NOT NULL                    
	   ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
	   
CREATE TABLE `tp_cr_last_update` (          
		  `last_id` bigint(20) NOT NULL auto_increment,  
		  `last_run_date` datetime NOT NULL,             
		  PRIMARY KEY  (`last_id`)                       
		) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `tp_cr_event_log` (                                     
	       `process_id` bigint(20) NOT NULL,                                      
	       `applicant_id` bigint(20) NOT NULL,                                    
	       `applicant_name` varchar(100) NOT NULL default '',                     
	       `position_id` bigint(20) NOT NULL,                                     
	       `position_code` varchar(50) default NULL,                              
	       `position_title` varchar(100) NOT NULL default '',                     
	       `position_step_id_from` int(11) NOT NULL,                              
	       `step_id_from` tinyint(4) default NULL,                                
	       `position_step_title_from` varchar(100) default NULL,                  
	       `position_step_level_from` varchar(1) default NULL,                    
	       `position_step_id_to` int(11) NOT NULL,                                
	       `step_id_to` tinyint(4) default NULL,                                  
	       `position_step_title_to` varchar(100) default NULL,                    
	       `position_step_level_to` varchar(1) default NULL,                      
	       `appointment_id` bigint(20) default NULL,                              
	       `appointment_from_date` datetime default NULL,                         
	       `appointment_status_id` tinyint(4) default NULL,                       
	       `user_id` bigint(20) NOT NULL,                                         
	       `user_name` varchar(50) NOT NULL,                                      
	       `process_date_created` datetime default NULL,                          
	       `process_moved_date` datetime default NULL,  
	       `selection_process_is_hidden` char(1) NOT NULL default '',             
	       `date_created` datetime NOT NULL                                       
	     ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
	     
CREATE TABLE `tp_cr_activity_summary` (          
              `user_id` bigint(20) NOT NULL,                 
              `user_name` varchar(50) NOT NULL,
              `imported` int(11) default NULL,               
              `email_recieved` int(11) default NULL,         
              `email_sent` int(11) default NULL,             
              `appointment` int(11) default NULL,            
              `interview` int(11) default NULL,              
              `messages` int(11) default NULL,               
              `phone` int(11) default NULL,                  
              `note` int(11) default NULL,                   
              `status_message` int(11) default NULL,         
              `sms` int(11) default NULL,                    
              `shortlisted` int(11) default NULL,            
              `offer_detail_modified` int(11) default NULL,  
              `blacklisted` int(11) default NULL,            
              `unblacklisted` int(11) default NULL,          
              `activity_date` datetime NOT NULL,             
              `date_created` datetime NOT NULL               
            ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
          
DROP TABLE IF EXISTS tp_cr_filters;
CREATE TABLE `tp_cr_filters` (                     
                 `filter_id` tinyint(4) NOT NULL auto_increment,  
                 `filter_name` varchar(50) NOT NULL,              
                 PRIMARY KEY  (`filter_id`)                       
               ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
               
DROP TABLE IF EXISTS tp_cr_column_filter_map;
CREATE TABLE `tp_cr_column_filter_map` (                                                                                                 
                           `cf_map_id` bigint(10) NOT NULL auto_increment,                                                                                        
                           `column_property` varchar(100) NOT NULL,                                                                                               
                           `filter_id` tinyint(4) NOT NULL,                                                                                                       
                           PRIMARY KEY  (`cf_map_id`),                                                                                                            
                           KEY `FK_tp_cr_column_filter_map` (`filter_id`),                                                                                        
                           KEY `FK_tp_cr_column_filter_map1` (`column_property`),                                                                                 
                           CONSTRAINT `FK_tp_cr_column_filter_map` FOREIGN KEY (`filter_id`) REFERENCES `tp_cr_filters` (`filter_id`),                            
                           CONSTRAINT `tp_cr_column_filter_map_ibfk_1` FOREIGN KEY (`column_property`) REFERENCES `tp_custom_report_columns` (`column_property`)  
                         ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
                         
CREATE TABLE tp_migration_status (                   
     position_id bigint(20) NOT NULL,                 
     migration_status char(1) DEFAULT 0,
     PRIMARY KEY  (position_id)       
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `tp_migration_temp_position_steps` (  
    `position_id` bigint(20) NOT NULL,               
    `position_step_id` int(11) NOT NULL,             
    `position_step_rank` tinyint(4) NOT NULL,        
    `step_id` tinyint(4) default NULL                
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `tp_migration_users` (                                                                                    
                      `migration_id` tinyint(4) NOT NULL auto_increment,                                                                   
                      `user_id` bigint(20) NOT NULL,                                                                                       
                      `steps_for` char(1) default '',                                                                                      
                      `migration_completion_status` char(1) NOT NULL default '' COMMENT '1 for inprocess, 2 for completed, -1 for reset',  
                      `start_date` datetime NOT NULL,                                                                                      
                      `last_modified` datetime default NULL,                                                                               
                      `end_date` datetime default NULL,                                                                                    
                      PRIMARY KEY  (`migration_id`)                                                                                        
                    ) ENGINE=InnoDB DEFAULT CHARSET=latin1; 

CREATE TABLE `tp_migration_temp_steps_grouping` (                                                                                   
    `migration_id` tinyint(4) NOT NULL,                                                                                               
    `grouping_id` int(11) NOT NULL auto_increment,                                                                                    
    `step_title` varchar(100) NOT NULL,                                                                                               
    `grouped_step_ids` text character set latin1 collate latin1_spanish_ci NOT NULL,                                                  
    `step_level` char(1) NOT NULL,                                                                                                    
    `step_scheduled` char(1) NOT NULL,                                                                                                
    `mapping_step_id` tinyint(4) default NULL,                                                                                        
    PRIMARY KEY  (`grouping_id`),                                                                                                     
    KEY `FK_tp_migration_temp_steps_grouping` (`mapping_step_id`),                                                                    
    KEY `FK_tp_migration_temp_steps_grouping_1` (`migration_id`),                                                                     
    CONSTRAINT `FK_tp_migration_temp_steps_grouping_1` FOREIGN KEY (`migration_id`) REFERENCES `tp_migration_users` (`migration_id`)  
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `tp_migration_show_message` (      
      `show_message` char(1) NOT NULL default '1',  
     `flag_modified_by` int(10) default NULL,      
     `flag_modified_on` datetime default NULL      
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `tp_cr_event_summary` (                                                                              
   `event_id` bigint(20) NOT NULL auto_increment,                                                                  
   `position_id` bigint(20) NOT NULL,                                                                              
   `user_id` bigint(20) NOT NULL,                                                                                  
   `step_id` int(11) NOT NULL,                                                                                     
   `step_level` char(1) NOT NULL,                                                                                  
   `rejected` tinyint(4) default '0',                                                                              
   `onhold` tinyint(4) default '0',                                                                                
   `joined` tinyint(4) default '0',                                                                                
   `received` tinyint(4) default '0',                                                                              
   `cleared` tinyint(4) default '0',                                                                               
   `inprocess` tinyint(4) default '0',                                                                             
   `process_date` datetime NOT NULL,                                                                               
   `date_created` datetime NOT NULL,                                                                               
   `backlog` tinyint(4) default '0',                                                                               
   PRIMARY KEY  (`event_id`),                                                                                      
   KEY `FK_tp_cr_event_summary` (`step_id`),                                                                       
   KEY `FK_tp_cr_event_summary_pos` (`position_id`),                                                               
   KEY `FK_tp_cr_event_summary_user` (`user_id`),                                                                  
   CONSTRAINT `FK_tp_cr_event_summary_pos` FOREIGN KEY (`position_id`) REFERENCES `tp_positions` (`position_id`),  
   CONSTRAINT `FK_tp_cr_event_summary_user` FOREIGN KEY (`user_id`) REFERENCES `tp_users` (`USER_ID`)              
 ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
