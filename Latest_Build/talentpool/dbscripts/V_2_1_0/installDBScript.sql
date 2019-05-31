use talentpool;

DELETE FROM tp_recent_searches;

CREATE TABLE `tp_requisition_approval_steps` (                     
                                 `requisition_approval_step_id` int(11) NOT NULL auto_increment,  
                                 `requisition_approval_step_name` varchar(255) NOT NULL,          
                                 `requisition_approval_step_rank` smallint(6) NOT NULL,           
                                 `requisition_approval_step_type` tinyint(4) NOT NULL,            
                                 `requisition_approval_step_status` tinyint(4) NOT NULL,          
                                 `requisition_approval_step_date_created` date NOT NULL,          
                                 PRIMARY KEY  (`requisition_approval_step_id`)                    
                               ) ENGINE=InnoDB DEFAULT CHARSET=latin1 ;

CREATE TABLE `tp_requisition_approval_steps_users` (                                                                                                                                
                                       `requisition_approval_step_id` int(11) NOT NULL,                                                                                                                                  
                                       `user_id` bigint(20) NOT NULL,                                                                                                                                                    
                                       KEY `FK_tp_requisition_approval_steps_users` (`requisition_approval_step_id`),                                                                                                    
                                       KEY `FK_tp_requisition_approval_steps_users_1` (`user_id`),                                                                                                                       
                                       CONSTRAINT `tp_requisition_approval_steps_users_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `tp_users` (`USER_ID`),                                                                
                                       CONSTRAINT `tp_requisition_approval_steps_users_ibfk_1` FOREIGN KEY (`requisition_approval_step_id`) REFERENCES `tp_requisition_approval_steps` (`requisition_approval_step_id`)  
                                     ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

insert into `tp_requisition_approval_steps` 
(`requisition_approval_step_id`,`requisition_approval_step_name`,`requisition_approval_step_rank`,
`requisition_approval_step_type`,`requisition_approval_step_status`,`requisition_approval_step_date_created`) 
values (1,'Requisition',1,0,1,'2008-01-30'),(2,'Activation',2,0,1,'2008-01-30');

CREATE TABLE `tp_requisition_approval_feedback` (                                                                                                                
                                    `feedback_id` bigint(20) NOT NULL auto_increment,                                                                                                              
                                    `position_id` int(11) NOT NULL,                                                                                                                                
                                    `from_step_id` int(11) NOT NULL,                                                                                                                               
                                    `to_step_id` int(11) default NULL,                                                                                                                             
                                    `by_user_id` bigint(20) NOT NULL,                                                                                                                              
                                    `to_user_id` bigint(20) default NULL,                                                                                                                          
                                    `feedback_decision` smallint(6) NOT NULL,                                                                                                                      
                                    `feedback_comment` varchar(255) default NULL,                                                                                                                  
                                    `feedback_date` datetime NOT NULL,                                                                                                                             
                                    PRIMARY KEY  (`feedback_id`),                                                                                                                                  
                                    KEY `FK_tp_requisition_approval_feedback` (`position_id`),                                                                                                     
                                    KEY `FK_tp_requisition_approval_feedback_1` (`by_user_id`),                                                                                                    
                                    KEY `FK_tp_requisition_approval_feedback_2` (`to_user_id`),                                                                                                    
                                    KEY `FK_tp_requisition_approval_feedback_3` (`from_step_id`),                                                                                                  
                                    KEY `FK_tp_requisition_approval_feedback_4` (`to_step_id`),                                                                                                    
                                    CONSTRAINT `tp_requisition_approval_feedback_ibfk_5` FOREIGN KEY (`to_step_id`) REFERENCES `tp_requisition_approval_steps` (`requisition_approval_step_id`),   
                                    CONSTRAINT `tp_requisition_approval_feedback_ibfk_1` FOREIGN KEY (`position_id`) REFERENCES `tp_positions` (`position_id`),                                    
                                    CONSTRAINT `tp_requisition_approval_feedback_ibfk_2` FOREIGN KEY (`by_user_id`) REFERENCES `tp_users` (`USER_ID`),                                             
                                    CONSTRAINT `tp_requisition_approval_feedback_ibfk_3` FOREIGN KEY (`to_user_id`) REFERENCES `tp_users` (`USER_ID`),                                             
                                    CONSTRAINT `tp_requisition_approval_feedback_ibfk_4` FOREIGN KEY (`from_step_id`) REFERENCES `tp_requisition_approval_steps` (`requisition_approval_step_id`)  
                                  ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
                                  
insert into `tp_application_properties` (`application_property`,`application_property_value`) values ( 'send_requisition_approval_notification','1');                                                                    

insert into `tp_template_types` (`template_type_id`,`template_type`) values ( '15','Requisition approval notification email');
insert into `tp_template_generic_vars` (`template_variable_type`,`template_variable`) values (13,'REQUISITION_TITLE');
insert into `tp_template_generic_vars` (`template_variable_type`,`template_variable`) values (13,'REQUISITION_STEP_TITLE');
insert into `tp_template_generic_vars` (`template_variable_type`,`template_variable`) values (13,'LINK_TO_REQUISITION_APPROVAL_FEEDBACK');
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values ( '15','1');
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values ( '15','2');
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values ( '15','13');


insert into `tp_templates` (`template_code`,`template_name`,`template_subject_file`,
`template_content_file`,`template_auto`,`template_format`,`template_private`,
`template_type_id`,`template_is_default`,`template_date_created`,`user_id`) 
values ('requisitionApprovalNotification','Requisition approval reminder email template','requisitionApprovalNotificationSubject.vm',
'requisitionApprovalNotificationContent.vm','0','0','0','15','1',now(),1);

update tp_positions set position_created_by = 1 where position_created_by is null;

insert into tp_requisition_approval_steps_users (requisition_approval_step_id, user_id)
select '2', position_created_by from tp_positions group by position_created_by;

create table tmp_pids as select distinct position_id as position_id from tp_requisition_approval_feedback;

insert into tp_requisition_approval_feedback(position_id,from_step_id,to_step_id,by_user_id,to_user_id,
feedback_decision,feedback_comment,feedback_date)
SELECT position_id,'2',null,position_created_by,null,'1','',position_date_created from tp_positions 
where position_id not in (select position_id from  tmp_pids);

drop table tmp_pids;

alter table tp_position_skills drop column skill_status;
