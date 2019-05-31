use talentpool;

DELETE FROM tp_recent_searches;

DELETE FROM tp_template_generic_vars WHERE template_variable_type=10 AND template_variable='LINK_TO_ORIGINAL_RESUME_EXTERNAL';
DELETE FROM tp_template_generic_vars WHERE template_variable_type=11 AND template_variable='LINK_TO_FEEDBACK_FORM_EXTERNAL';
DELETE FROM tp_template_generic_vars WHERE template_variable_type=13 AND template_variable='LINK_TO_REQUISITION_APPROVAL_FEEDBACK_EXTERNAL';

INSERT INTO tp_template_generic_vars (template_variable_type,template_variable) VALUES (10, 'LINK_TO_ORIGINAL_RESUME_EXTERNAL');
INSERT INTO tp_template_generic_vars (template_variable_type,template_variable) VALUES (11, 'LINK_TO_FEEDBACK_FORM_EXTERNAL');
INSERT INTO tp_template_generic_vars (template_variable_type,template_variable) VALUES (13, 'LINK_TO_REQUISITION_APPROVAL_FEEDBACK_EXTERNAL');
INSERT INTO tp_template_generic_vars (template_variable_type,template_variable) VALUES (10, 'POSITION_STEP_NAME');

CREATE TABLE `tp_report_scheduler` (                                                                              
           `schedule_id` bigint(20) NOT NULL auto_increment,                                                               
           `report_id` smallint(6) default NULL,                                                                           
           `frequency` char(1) default NULL,                                                                               
           `start_date` date default NULL,                                                                                 
           `everyday` char(1) default NULL,                                                                                
           `weekdays` char(1) default NULL,                                                                                
           `day_of_week` tinyint(4) default NULL,                                                                          
           `day_of_month` tinyint(4) default NULL,                                                                         
           `schedule_time` datetime default NULL,                                                                          
           `schedule_status` varchar(255) default NULL,                                                                    
           `email_ids` varchar(255) default NULL,                                                                          
           `date_created` datetime default NULL,                                                                           
           `user_id` bigint(20) default NULL,                                                                              
           `department_id` int(11) default NULL,                                                                           
           `position_id` int(11) default NULL,                                                                             
           `filter_id` int(11) default NULL,                                                                               
           `source_id` int(11) default NULL,                                                                               
           `action_id` varchar(1) default NULL,                                                                            
           `degree_ids` int(11) default NULL,                                                                              
           `step_ids` int(11) default NULL,                                                                                
           `report_type` char(1) default NULL,                                                                             
           `report_format` char(1) default NULL,                                                                           
           `applicants` varchar(255) default NULL,                                                                         
           `users` varchar(255) default NULL,                                                                              
           `interviewers` varchar(255) default NULL,                                                                       
           `from_date` varchar(11) default NULL,                                                                           
           `to_date` varchar(11) default NULL,                                                                             
           `from_month` varchar(2) default NULL,                                                                           
           `to_month` varchar(2) default NULL,                                                                             
           `from_year` varchar(4) default NULL,                                                                            
           `to_year` varchar(4) default NULL,                                                                              
           `date_range` varchar(3) default NULL,                                                                           
           `max_exp` varchar(5) default NULL,                                                                              
           `min_exp` varchar(5) default NULL,                                                                              
           `stages` varchar(255) default NULL,                                                                             
           `order_by` varchar(2) default NULL,                                                                             
           `cost_report_type` varchar(255) default NULL,                                                                   
           `email_subject` varchar(255) default NULL,                                                                      
           `email_body` text,                                                                                              
           PRIMARY KEY  (`schedule_id`),                                                                                   
           KEY `FK_tp_report_scheduler_1` (`user_id`),                                                                     
           KEY `FK_tp_report_scheduler_2` (`position_id`),                                                                 
           KEY `FK_tp_report_scheduler_3` (`department_id`),                                                               
           KEY `FK_tp_report_scheduler_4` (`source_id`),                                                                   
           CONSTRAINT `tp_report_scheduler_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `tp_users` (`USER_ID`),              
           CONSTRAINT `tp_report_scheduler_ibfk_2` FOREIGN KEY (`position_id`) REFERENCES `tp_positions` (`position_id`),  
           CONSTRAINT `tp_report_scheduler_ibfk_3` FOREIGN KEY (`department_id`) REFERENCES `tp_departments` (`dept_id`),  
           CONSTRAINT `tp_report_scheduler_ibfk_4` FOREIGN KEY (`source_id`) REFERENCES `tp_sources` (`source_id`)         
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
                                          
CREATE TABLE `tp_report_scheduler_users` (     
		 `schedule_id` bigint(20) default NULL,       
		 `scheduled_to_user_id` int(11) default NULL  
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ;     

                    
ALTER TABLE tp_positions ADD `position_priority` char(1) NOT NULL;
UPDATE tp_positions SET position_priority=1 WHERE position_priority="";

UPDATE tp_permissions SET permission_rank=permission_rank+1 WHERE permission_rank >46;

INSERT INTO tp_permissions (permission_id,permission_rank,permission_desc,parent_id,is_permission) VALUES (64,47,'Position Priority',46,1);

INSERT INTO tp_role_permissions(role_id,permission_id) VALUES(1,64);
INSERT INTO tp_role_permissions(role_id,permission_id) VALUES(4,64);
INSERT INTO tp_role_permissions(role_id,permission_id) VALUES(6,64);

INSERT INTO tp_user_permissions (user_id,permission_id) SELECT tu.user_id, 64 FROM tp_users tu, tp_user_roles tur WHERE tur.USER_ID = tu.USER_ID AND tur.role_id IN (1,6,4);

ALTER TABLE tp_report_scheduler MODIFY degree_ids VARCHAR(255);
 
                     
                     
