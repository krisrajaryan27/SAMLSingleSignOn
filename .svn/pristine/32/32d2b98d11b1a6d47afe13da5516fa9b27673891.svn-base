use talentpool;

DROP TABLE IF EXISTS tp_employers;
CREATE TABLE tp_employers ( 
        employer_id bigint(20) NOT NULL auto_increment,  
        employer_name varchar(150) NOT NULL,             
        PRIMARY KEY  (employer_id),                      
        UNIQUE KEY employer_name (employer_name)      
        ) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS tp_employer_aliases;
CREATE TABLE tp_employer_aliases (                                                                                                  
	employer_id bigint(20) NOT NULL,                                                                                                  
        alias varchar(150) NOT NULL,                                                                                                       
        UNIQUE KEY alias (alias),                                                                                                        
        KEY tp_employer_aliases_ibfk_1 (employer_id), 
        CONSTRAINT tp_employer_aliases_ibfk_1 FOREIGN KEY (employer_id) REFERENCES tp_employers (employer_id) ON DELETE CASCADE
        ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

        
DROP TABLE IF EXISTS tp_designations;
CREATE TABLE tp_designations ( 
        designation_id bigint(20) NOT NULL auto_increment,  
        designation_name varchar(150) NOT NULL,             
        PRIMARY KEY  (designation_id),                      
        UNIQUE KEY designation_name (designation_name)      
        ) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS tp_designations_aliases;
CREATE TABLE tp_designations_aliases (                                                                                                  
	designation_id bigint(20) NOT NULL,                                                                                                  
        alias varchar(150) NOT NULL,                                                                                                       
        UNIQUE KEY alias (alias),                                                                                                        
        KEY tp_designations_aliases_ibfk_1 (designation_id), 
        CONSTRAINT tp_designations_aliases_ibfk_1 FOREIGN KEY (designation_id) REFERENCES tp_designations (designation_id) ON DELETE CASCADE
        ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
        
CREATE TABLE tp_salary_component_categories( 
category_id SMALLINT NOT NULL auto_increment, 
category_name varchar(40) CHARSET latin1 COLLATE latin1_swedish_ci NOT NULL , 
PRIMARY KEY (category_id)) ENGINE=InnoDB DEFAULT CHARSET=latin1;

ALTER TABLE tp_salary_components 
ADD COLUMN salary_component_category_id SMALLINT NOT NULL AFTER salary_component_type;

CREATE TABLE tp_existing_salary_details (                                                                                                                    
	applicant_id bigint(10) NOT NULL,                                                                                                                          
	sal_category_id SMALLINT(4) NOT NULL,                                                                                                                       
	existing_value int(11) default NULL,                                                                                                                       
	PRIMARY KEY (applicant_id,sal_category_id),                                                                                                             
	KEY FK_tp_existing_salary_details_1 (sal_category_id),                                                                                                   
	CONSTRAINT FK_tp_existing_salary_details_1 FOREIGN KEY (sal_category_id) REFERENCES tp_salary_component_categories (category_id) ON DELETE CASCADE,  
	CONSTRAINT FK_tp_existing_salary_details FOREIGN KEY (applicant_id) REFERENCES tp_applicants (applicant_id) ON DELETE CASCADE                        
) ENGINE=InnoDB DEFAULT CHARSET=latin1;  

CREATE TABLE tp_applicant_current_details (
	applicant_id bigint(20) NOT NULL,                                                                                    
	current_basic mediumint(8) unsigned default NULL,                                                                    
	current_designation varchar(50) default NULL,                                                                        
	current_level varchar(50) default NULL,                                                                              
	PRIMARY KEY  (applicant_id),                                                                                         
	CONSTRAINT FK_applicants FOREIGN KEY (applicant_id) REFERENCES tp_applicants (applicant_id) ON DELETE CASCADE  
) ENGINE=InnoDB DEFAULT CHARSET=latin1;  

CREATE TABLE tp_applicant_offer_sheet_details (                                                                                                                    
    applicant_id bigint(20) NOT NULL,                                                                                                                                
    offer_code varchar(40) default NULL,                                                                                                                             
    offer_letter_doc_id bigint(20) NOT NULL,                                                                                                                         
    offer_sheet_template_id int(11) default NULL,                                                                                                                    
    PRIMARY KEY  (applicant_id),                                                                                                                                     
    KEY FK_tp_applicant_offer_details_1 (offer_letter_doc_id),                                                                                                     
    KEY FK_tp_applicant_offer_sheet_details (offer_sheet_template_id),                                                                                             
    CONSTRAINT FK_tp_applicant_offer_sheet_details FOREIGN KEY (offer_sheet_template_id) REFERENCES tp_offersheet_templates (template_id) ON DELETE SET NULL,  
    CONSTRAINT FK_tp_applicant_offer_details FOREIGN KEY (applicant_id) REFERENCES tp_applicants (applicant_id) ON DELETE CASCADE,                             
    CONSTRAINT FK_tp_applicant_offer_details_1 FOREIGN KEY (offer_letter_doc_id) REFERENCES tp_applicant_documents (document_id) ON DELETE CASCADE             
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE tp_custom_increment_counter (        
   variable_name varchar(20) NOT NULL,             
   incremental_value bigint(20) unsigned NOT NULL  
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `tp_applicant_employment_history` (                                                                                      
           `employment_history_id` bigint(20) NOT NULL auto_increment,                                                                         
           `applicant_id` bigint(20) NOT NULL,                                                                                                 
           `employmer_from_date` date default NULL,                                                                                       
           `employmer_to_date` date default NULL,                                                                                         
           `employer_id` bigint(20) default NULL,                                                                                              
           `designation_id` bigint(20) default NULL,                                                                                           
           `employer_experience` bigint(5) default '0',           
           PRIMARY KEY  (`employment_history_id`),                                                                                             
           KEY `FK_tp_applicant_employment_history` (`applicant_id`),                                                                          
           KEY `FK_tp_applicant_employment_history_designation` (`designation_id`),                                                            
           KEY `FK_tp_applicant_employment_history_employer` (`employer_id`),                                                                  
           CONSTRAINT `tp_applicant_employment_history_ibfk_3` FOREIGN KEY (`employer_id`) REFERENCES `tp_employers` (`employer_id`),          
           CONSTRAINT `tp_applicant_employment_history_ibfk_1` FOREIGN KEY (`applicant_id`) REFERENCES `tp_applicants` (`applicant_id`),       
           CONSTRAINT `tp_applicant_employment_history_ibfk_2` FOREIGN KEY (`designation_id`) REFERENCES `tp_designations` (`designation_id`)  
         ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE tp_offer_details_modified_interactions (                                                                                               
  interaction_id bigint(20) unsigned NOT NULL auto_increment,                                                                                       
  applicant_id bigint(20) NOT NULL,                                                                                                                 
  interaction_type tinyint(3) unsigned NOT NULL,                                                                                                    
  interaction_title varchar(150) default NULL,                                                                                                      
  previous_ctc varchar(20) default NULL,                                                                                                            
  changed_ctc varchar(20) default NULL,                                                                                                             
  previous_basic varchar(20) default NULL,                                                                                                          
  changed_basic varchar(20) default NULL,                                                                                                           
  previous_designation varchar(50) default NULL,                                                                                                    
  changed_designation varchar(50) default NULL,                                                                                                     
  previous_level varchar(20) default NULL,                                                                                                          
  changed_level varchar(20) default NULL,                                                                                                           
  interaction_hidden char(1) NOT NULL default '1',                                                                                                  
  offer_code varchar(40) default NULL,                                                                                                              
  offer_sheet_name varchar(250) default NULL,                                                                                                       
  user_id bigint(20) NOT NULL,                                                                                                                      
  date_created datetime NOT NULL,                                                                                                                   
  PRIMARY KEY  (interaction_id),                                                                                                                    
  KEY FK_tp_offer_details_modified_interactions (applicant_id),                                                                                   
  KEY FK_tp_offer_details_modified_interactions1 (user_id),                                                                                       
  CONSTRAINT FK_tp_offer_details_modified_interactions FOREIGN KEY (applicant_id) REFERENCES tp_applicants (applicant_id) ON DELETE CASCADE,  
  CONSTRAINT FK_tp_offer_details_modified_interactions1 FOREIGN KEY (user_id) REFERENCES tp_users (USER_ID) ON DELETE NO ACTION               
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

ALTER TABLE tp_salary_components 
ADD COLUMN system_defined CHAR CHARSET latin1 COLLATE latin1_swedish_ci DEFAULT '0' NOT NULL AFTER salary_component_category_id;

ALTER TABLE tp_salary_formulae 
ADD COLUMN system_defined CHAR CHARSET latin1 COLLATE latin1_swedish_ci DEFAULT '0' NOT NULL AFTER date_created;
ALTER TABLE tp_applicants ADD COLUMN input_salary_variable INT UNSIGNED NULL AFTER offered_ctc;
ALTER TABLE tp_applicant_joining_history ADD COLUMN input_salary_variable INT UNSIGNED NULL AFTER designation_offered;
ALTER TABLE tp_offer_details_modified_interactions 
ADD COLUMN previous_input_salary_variable INT UNSIGNED NULL AFTER changed_level, 
ADD COLUMN changed_input_salary_variable INT UNSIGNED NULL AFTER previous_input_salary_variable;
ALTER TABLE tp_salary_formulae ADD COLUMN rounding_type SMALLINT DEFAULT '0' NULL AFTER is_adjustable;
ALTER TABLE tp_salary_formulae CHANGE variable1_factor variable1_factor DECIMAL(10,7) NOT NULL, CHANGE constant_factor constant_factor DECIMAL(15,7) DEFAULT '0.00000' NOT NULL;

ALTER TABLE tp_salary_formulae DROP FOREIGN KEY tp_salary_formula_ibfk_1;
ALTER TABLE tp_salary_formulae ADD CONSTRAINT tp_salary_formula_ibfk_1 FOREIGN KEY (grade_id) REFERENCES tp_budget_grades (grade_id) ON DELETE CASCADE;