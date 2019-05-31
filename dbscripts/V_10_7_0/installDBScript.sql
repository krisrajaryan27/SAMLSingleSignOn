use talentpool;

DELETE FROM tp_recent_searches;

CREATE TABLE `tp_position_draft` (                
     `draft_id` bigint(20) NOT NULL auto_increment,  
     `draft_name` varchar(100) NOT NULL,              
     `file_path` varchar(100) NOT NULL,              
     `visibility` varchar(2) NOT NULL,               
     `user_id` bigint(20) NOT NULL,                  
     `date_created` datetime NOT NULL,               
     PRIMARY KEY  (`draft_id`)                       
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

UPDATE tp_permissions
SET permission_rank = permission_rank + 1
WHERE permission_rank > 66;

INSERT INTO tp_permissions
(permission_id, permission_rank, permission_desc, parent_id, is_permission)
VALUES
(73, 67, 'Offer Sheet Master', 52, 1);

INSERT INTO tp_role_permissions
(role_id, permission_id)
VALUES
(1,73), (3,73), (4,73), (6,73);

INSERT INTO tp_user_permissions
(user_id, permission_id)
SELECT USER_ID, 73
FROM tp_user_roles
WHERE ROLE_ID IN (1,3,4,6);

CREATE TABLE `tp_offersheet_templates` (           
  `template_id` int(11) NOT NULL auto_increment,          
  `template_name` varchar(250) NOT NULL,                  
  `template_desc` varchar(500) default NULL,              
  `template_file_path` varchar(150) NOT NULL default '',  
  `template_file_name` varchar(150) NOT NULL,             
  `user_id` bigint(20) NOT NULL,                          
  `date_created` datetime NOT NULL,                       
  PRIMARY KEY  (`template_id`)                            
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `tp_offersheet_template_variables` (                                                                                    
    `template_id` int(11) NOT NULL,                                                                                                    
    `template_variable` varchar(50) NOT NULL,                                                                                          
    `attribute` varchar(100) default NULL,                                                                                             
    KEY `FK_tp_offersheet_template_variables` (`template_id`),                                                                         
    CONSTRAINT `FK_tp_offersheet_template_variables` FOREIGN KEY (`template_id`) REFERENCES `tp_offersheet_templates` (`template_id`)  
  ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
 
UPDATE tp_permissions
SET permission_rank = permission_rank + 1
WHERE permission_rank > 45;

INSERT INTO tp_permissions
(permission_id, permission_rank, permission_desc, parent_id, is_permission)
VALUES
(74, 46, 'Generate Offer', 43, 1);

INSERT INTO tp_role_permissions
(role_id, permission_id)
VALUES
(1,74), (3,74), (4,74), (6,74);

INSERT INTO tp_user_permissions
(user_id, permission_id)
SELECT USER_ID, 74
FROM tp_user_roles
WHERE ROLE_ID IN (1,3,4,6);

alter table `tp_communications` 
add column `document_id` bigint NULL after `phone_no`;

insert into `tp_appointment_status` (`appointment_status_id`,`appointment_status_text`) values ( '5','Happened');
insert into `tp_application_properties` (`application_property`,`application_property_value`) values ( 'grid_result_page_size','100');
CREATE TABLE `tp_user_activity` (                        
                    `activity_id` bigint(20) NOT NULL auto_increment,      
                    `activity` text,                                       
                    `interaction_id` bigint(20) default NULL,              
                    `interaction_type` char(2) default NULL,               
                    `interaction_date` datetime default NULL,              
                    `position_id` bigint(20) default NULL,                 
                    `applicant_id` bigint(20) default NULL,                
                    `user_id` bigint(20) default NULL,                     
                    `interaction_is_hidden` char(1) NOT NULL default '0',  
                    `date_created` datetime default NULL,                  
                    PRIMARY KEY  (`activity_id`)                           
                  ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `tp_todo` (                                                                                              
           `user_id` bigint(20) NOT NULL,                                                                                      
           `process_id` bigint(20) default NULL,                                                                               
           `position_id` bigint(20) default NULL,                                                                              
           `applicant_id` bigint(20) default NULL,                                                                             
           `current_step_id` bigint(20) default NULL,                                                                          
           `step_isscheduled` char(1) default '0',                                                                             
           `user_is_decision_maker` char(1) default '0',                                                                       
           `user_responsible_for_scheduling` char(1) default '0',                                                              
           `user_is_interviewer` char(1) default '0',                                                                          
           `interviewer_can_confirm` char(1) default '0',                                                                      
           `appointment_id` bigint(20) default NULL,                                                                           
           `appointment_time` datetime default NULL,                                                                           
           `appointment_status_id` tinyint(4) default NULL,                                                                    
           `todo_type` char(1) NOT NULL,                                                                                       
           `feedback_present` char(1) default '0',                                                                             
           `due_date` datetime NOT NULL,                                                                                       
           `date_created` datetime NOT NULL,                                                                                   
           KEY `FK_tp_todo` (`applicant_id`),                                                                                  
           KEY `FK_tp_todo_1` (`process_id`),                                                                                  
           KEY `FK_tp_todo_2` (`position_id`),                                                                                 
           KEY `FK_tp_todo_3` (`appointment_id`),                                                                              
           KEY `FK_tp_todo_4` (`user_id`),                                                                                     
           CONSTRAINT `tp_todo_ibfk_1` FOREIGN KEY (`applicant_id`) REFERENCES `tp_applicants` (`applicant_id`),               
           CONSTRAINT `tp_todo_ibfk_2` FOREIGN KEY (`process_id`) REFERENCES `tp_applicant_selection_process` (`process_id`),  
           CONSTRAINT `tp_todo_ibfk_3` FOREIGN KEY (`position_id`) REFERENCES `tp_positions` (`position_id`),                  
           CONSTRAINT `tp_todo_ibfk_4` FOREIGN KEY (`appointment_id`) REFERENCES `tp_appointments` (`appointment_id`),         
           CONSTRAINT `tp_todo_ibfk_5` FOREIGN KEY (`user_id`) REFERENCES `tp_users` (`USER_ID`)                               
         ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `tp_reports` (                        
              `report_id` bigint(20) NOT NULL auto_increment,  
              `report_name` varchar(250) NOT NULL,             
              `report_type` char(10) NOT NULL,                 
              `report_desc` text,
              `group_by` char(10) default NULL,                
              `sort_by` char(10) default NULL,                 
              `sort_with` char(1) default NULL,                
              `sort_criteria` char(1) default NULL,            
              `view_type` char(10) default NULL,               
              `date_created` datetime NOT NULL,                
              `user_id` bigint(20) NOT NULL,
              PRIMARY KEY  (`report_id`)                       
            ) ENGINE=InnoDB DEFAULT CHARSET=latin1   ;
            
ALTER TABLE tp_reports AUTO_INCREMENT = 100;
 
CREATE TABLE `tp_report_columns` (                                                                       
                     `report_id` bigint(20) NOT NULL,                                                                       
                     `column_name` varchar(250) NOT NULL,                                                                   
                     `column_rank` tinyint(4) NOT NULL,                                                                     
                     `column_type` char(1) NOT NULL default '0',                                                            
                     KEY `FK_tp_report_columns` (`report_id`),                                                              
                     CONSTRAINT `tp_report_columns_ibfk_1` FOREIGN KEY (`report_id`) REFERENCES `tp_reports` (`report_id`)  
                   ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `tp_report_filters` (                                                                       
                     `report_id` bigint(20) NOT NULL,                                                                       
                     `filter_id` tinyint(4) NOT NULL,                                                                       
                     KEY `FK_tp_report_filters` (`report_id`),                                                              
                     CONSTRAINT `tp_report_filters_ibfk_1` FOREIGN KEY (`report_id`) REFERENCES `tp_reports` (`report_id`)  
                   ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

alter table tp_report_levels add column `report_type` smallint (1)  DEFAULT '0' NULL  after `report_id`;

insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) 
values ( '75','72','Add New Custom Report','0','1');

insert into tp_role_permissions select tr.role_id, 75 from tp_roles tr where tr.role_id in (1,4,6);

insert into tp_user_permissions
select tu.user_id, trp.permission_id
from tp_users tu, tp_user_roles tur, tp_role_permissions trp
where trp.role_id=tur.role_id and tur.user_id=tu.user_id
and trp.permission_id in (75);

DELETE FROM tp_user_permissions WHERE permission_id = 42;
DELETE FROM tp_role_permissions WHERE permission_id = 42;
DELETE FROM tp_permissions WHERE permission_id = 42;
UPDATE tp_permissions 
SET permission_rank = permission_rank-1 
WHERE permission_rank > 42;

alter table `tp_inbox_settings` add column `inbox_outgoing_tls_enabled` smallint (6)  DEFAULT '0' NULL  after `inbox_outgoing_ssl_enabled`;
