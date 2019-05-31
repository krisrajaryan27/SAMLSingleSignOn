use talentpool;

DELETE FROM tp_recent_searches;

alter table `tp_inbox_emails` add column `entry_id` varchar (100)   NULL  after `user_id`;
alter table `tp_applicant_inbox_emails` add column `entry_id` varchar (100)   NULL  after `email_imported`;

CREATE TABLE `tp_bulk_import_sessions` (                                                                                       
	   `session_id` varchar(100) NOT NULL,                                                                                          
	   `source_id` int(11) default NULL,                                                                                            
	   `applicant_city` varchar(50) default NULL,                                                                                   
	   `applicant_working_since` date default NULL,                                                                                 
	   `session_status` char(1) default '0',                                                                                        
	   `user_id` bigint(20) default NULL,                                                                                           
	   `date_created` datetime NOT NULL,                                                                                            
	   `session_type` char(1) NOT NULL default '0',                                                                                 
	   `current_employer` varchar(50) default NULL,                                                                                 
	   `current_ctc` double default NULL,                                                                                           
	   `expected_ctc` double default NULL,                                                                                          
	   `time_to_join` varchar(50) default NULL,                                                                                     
	   `note` text,                                                                                                                 
	   PRIMARY KEY  (`session_id`),                                                                                                 
	   KEY `FK_tp_bulk_import_sessions` (`source_id`),                                                                              
	   KEY `FK_tp_bulk_import_sessions_1` (`user_id`),                                                                              
	   CONSTRAINT `tp_bulk_import_sessions_ibfk_1` FOREIGN KEY (`source_id`) REFERENCES `tp_sources` (`source_id`),                 	   
	   CONSTRAINT `tp_bulk_import_sessions_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `tp_users` (`USER_ID`)                       
	 ) ENGINE=InnoDB DEFAULT CHARSET=latin1; 

CREATE TABLE `tp_bulk_import_session_skills` (
  `session_id` varchar(100) NOT NULL,
  `skill_id` int(11) NOT NULL,
  KEY `FK_tp_bulk_import_session_skills` (`skill_id`),
  KEY `FK_tp_bulk_import_session_skills_1` (`session_id`),
  CONSTRAINT `tp_bulk_import_session_skills_ibfk_2` FOREIGN KEY (`session_id`) REFERENCES `tp_bulk_import_sessions` (`session_id`),
  CONSTRAINT `tp_bulk_import_session_skills_ibfk_1` FOREIGN KEY (`skill_id`) REFERENCES `tp_skills` (`skill_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `tp_bulk_import_session_documents` (
  `document_id` bigint(20) NOT NULL auto_increment,
  `session_id` varchar(100) NOT NULL,
  `document_path` varchar(250) NOT NULL,
  `file_name` varchar(250) NOT NULL,
  `import_status` char(1) NOT NULL default '0',
  `date_created` datetime NOT NULL,
  PRIMARY KEY  (`document_id`),
  KEY `FK_tp_bulk_import_session_documents` (`session_id`),
  CONSTRAINT `tp_bulk_import_session_documents_ibfk_1` FOREIGN KEY (`session_id`) REFERENCES `tp_bulk_import_sessions` (`session_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `tp_bulk_import_session_emails` (
  `email_id` bigint(20) NOT NULL auto_increment,
  `email_from` varchar(100) default NULL,
  `email_to` text,
  `email_cc` text,
  `email_bcc` text,
  `email_subject` varchar(250) default NULL,
  `email_date_send` datetime default NULL,
  `email_date_received` datetime default NULL,
  `email_textbody` longtext,
  `email_htmlbody` longtext,
  `email_size` bigint(20) NOT NULL default '0',
  `entry_id` varchar(100) NOT NULL,
  `session_id` varchar(100) NOT NULL,
  `import_status` char(1) NOT NULL default '0',
  `date_created` datetime NOT NULL,
  PRIMARY KEY  (`email_id`),
  KEY `FK_tp_bulk_import_session_emails` (`session_id`),
  CONSTRAINT `tp_bulk_import_session_emails_ibfk_1` FOREIGN KEY (`session_id`) REFERENCES `tp_bulk_import_sessions` (`session_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `tp_bulk_import_session_email_attachments` (
  `attachment_id` bigint(20) NOT NULL auto_increment,
  `email_id` bigint(20) NOT NULL default '0',
  `attachment_file_path` varchar(150) NOT NULL default '',
  `attachment_original_file_name` varchar(150) default NULL,
  `attachment_content_type` text,
  `attachment_content_id` varchar(150) default NULL,
  `attachment_type` char(1) NOT NULL default '1',
  `attachment_size` bigint(20) NOT NULL default '0',
  `import_status` char(1) NOT NULL default '0',
  PRIMARY KEY  (`attachment_id`),
  KEY `FK_tp_bulk_import_session_email_attachments` (`email_id`),
  CONSTRAINT `tp_bulk_import_session_email_attachments_ibfk_1` FOREIGN KEY (`email_id`) REFERENCES `tp_bulk_import_session_emails` (`email_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `tp_bulk_import_session_results` (                                                                                                 
	  `result_id` bigint(20) NOT NULL auto_increment,                                                                                               
	  `session_id` varchar(100) default NULL,                                                                                                       
	  `email_id` bigint(20) default NULL,                                                                                                           
	  `document_id` bigint(20) default NULL,                                                                                                        
	  `date_created` datetime default NULL,                                                                                                         
	  `parsed_name` varchar(100) default NULL,                                                                                                      
	  `parsed_email1` varchar(50) default NULL,                                                                                                     
	  `parsed_email2` varchar(50) default NULL,                                                                                                     
	  `parsed_cell_phone` varchar(25) default NULL,                                                                                                 
	  `parsed_work_phone` varchar(25) default NULL,                                                                                                 
	  `parsed_home_phone` varchar(25) default NULL,                                                                                                 
	  `parsed_original_resume_path` varchar(100) default NULL,                                                                                      
	  `parsed_original_doc_path` varchar(100) default NULL,                                                                                         
	  `parsed_text_resume` longtext,                                                                                                                
	  `is_imported` char(1) NOT NULL default '0',                                                                                                   
	  `applicant_id` bigint(20) default NULL,                                                                                                       
	  PRIMARY KEY  (`result_id`),                                                                                                                   
	  KEY `FK_tp_bulk_import_session_results` (`session_id`),                                                                                       
	  KEY `FK_tp_bulk_import_session_results_1` (`email_id`),                                                                                       
	  KEY `FK_tp_bulk_import_session_results_2` (`document_id`),                                                                                    
	  CONSTRAINT `tp_bulk_import_session_results_ibfk_1` FOREIGN KEY (`session_id`) REFERENCES `tp_bulk_import_sessions` (`session_id`),            
	  CONSTRAINT `tp_bulk_import_session_results_ibfk_2` FOREIGN KEY (`email_id`) REFERENCES `tp_bulk_import_session_emails` (`email_id`),          
	  CONSTRAINT `tp_bulk_import_session_results_ibfk_3` FOREIGN KEY (`document_id`) REFERENCES `tp_bulk_import_session_documents` (`document_id`)  
	) ENGINE=InnoDB DEFAULT CHARSET=latin1;
	
CREATE TABLE `tp_bulk_import_session_result_skills` (
  `result_id` bigint(20) default NULL,
  `skill_id` int(11) default NULL,
  KEY `FK_tp_bulk_import_session_result_skills_1` (`result_id`),
  KEY `FK_tp_bulk_import_session_result_skills` (`skill_id`),
  CONSTRAINT `tp_bulk_import_session_result_skills_ibfk_2` FOREIGN KEY (`skill_id`) REFERENCES `tp_skills` (`skill_id`),
  CONSTRAINT `tp_bulk_import_session_result_skills_ibfk_1` FOREIGN KEY (`result_id`) REFERENCES `tp_bulk_import_session_results` (`result_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `tp_bulk_import_session_educational_info` (     
	   `educational_info_id` bigint(20) NOT NULL auto_increment,  
	   `session_id` varchar(100) NOT NULL,                        
	   `degree_id` smallint(6) default NULL,                      
	   `branch_id` int(11) default NULL,                          
	   `institute_id` bigint(20) default NULL,                    
	   `educational_info_year_of_passing` date default NULL,      
	   `educational_info_grade` varchar(100) default NULL,        
	   PRIMARY KEY  (`educational_info_id`)                       
	 ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
	 
CREATE TABLE `tp_bulk_import_session_custom_fields` (                                                                                         
	  `custom_field_id` int(11) NOT NULL,                                                                                           
	  `custom_field_value` varchar(250) default NULL,                                                                               
	  `entity_id` varchar(100) default NULL,                                                                                          
	  KEY `FK_tp_bulk_import_session_custom_fields` (`custom_field_id`),                                                                          
	  CONSTRAINT `tp_bulk_import_session_custom_fields_ibfk_1` FOREIGN KEY (`custom_field_id`) REFERENCES `tp_custom_fields` (`custom_field_id`)  
	) ENGINE=InnoDB DEFAULT CHARSET=latin1;
	
alter table tp_sources add column employee_code varchar (25)   NULL  after system_generated;	
alter table tp_sources add unique employee_code ( employee_code );


create table `tp_sources_temp` ( `source_title` varchar (100)   NOT NULL    );
INSERT INTO tp_sources_temp(source_title)
SELECT source_title FROM tp_sources;

INSERT INTO tp_sources (source_type_id, source_title, source_email, source_phone, source_mobile )
SELECT (SELECT source_type_id FROM tp_source_types WHERE source_type_category=2), 
CONCAT(tu.USER_FNAME,' ',tu.USER_LNAME), tu.USER_EMAIL, tu.USER_HOME_PHONE, tu.USER_CELL_PHONE
FROM tp_users tu, tp_user_roles tr
WHERE USER_SOURCE_ID IS NULL 
AND tr.user_id=tu.user_id
AND tu.USER_STATUS = 1 AND tr.role_id!=7
AND CONCAT(tu.USER_FNAME,' ',tu.USER_LNAME) NOT IN (SELECT source_title FROM tp_sources_temp);

UPDATE tp_users tu SET USER_SOURCE_ID = (
			SELECT ts.source_id FROM tp_sources ts
			WHERE ts.source_title = CONCAT(tu.USER_FNAME,' ',tu.USER_LNAME)
			)
WHERE USER_SOURCE_ID IS NULL AND tu.USER_STATUS = 1;

DROP TABLE tp_sources_temp;