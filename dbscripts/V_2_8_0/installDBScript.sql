use talentpool;

DELETE FROM tp_recent_searches;

drop table `tp_inbox_folders`;

alter table `tp_inbox_emails` drop foreign key `tp_inbox_emails_ibfk_1`;
alter table `tp_inbox_emails` drop column `inbox_id`;

alter table `tp_inbox_settings` change `inbox_id` `inbox_id` int   NOT NULL AUTO_INCREMENT;

CREATE TABLE `tp_inbox_folders` (                                                                             
        `folder_id` int(11) NOT NULL auto_increment,                                                                
        `folder_name` varchar(100) NOT NULL,                                                                        
        `is_system_defined` char(1) NOT NULL default '0',                                                           
        `inbox_id` int(11) NOT NULL,                                                                                
        `date_created` datetime NOT NULL,                                                                           
        `user_id` bigint(20) NOT NULL,                                                                              
        `folder_created_by` bigint(20) NOT NULL,                                                                    
        PRIMARY KEY  (`folder_id`),                                                                                 
        KEY `FK_tp_inbox_folders` (`inbox_id`),                                                                     
        KEY `FK_tp_inbox_folders_1` (`user_id`),                                                                    
        KEY `FK_tp_inbox_folders_2` (`folder_created_by`),                                                          
        CONSTRAINT `tp_inbox_folders_ibfk_3` FOREIGN KEY (`folder_created_by`) REFERENCES `tp_users` (`USER_ID`),   
        CONSTRAINT `tp_inbox_folders_ibfk_1` FOREIGN KEY (`inbox_id`) REFERENCES `tp_inbox_settings` (`inbox_id`),  
        CONSTRAINT `tp_inbox_folders_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `tp_users` (`USER_ID`)              
      ) ENGINE=InnoDB DEFAULT CHARSET=latin1 ;

insert into `tp_inbox_folders` (`folder_id`,`folder_name`,`is_system_defined`,`inbox_id`,`date_created`,`user_id`,`folder_created_by`) values (1,'Inbox','1',1,now(),1,1);
insert into `tp_inbox_folders` (`folder_id`,`folder_name`,`is_system_defined`,`inbox_id`,`date_created`,`user_id`,`folder_created_by`) values (2,'Sent Items','1',1,now(),1,1);


insert into `tp_template_generic_vars` (`template_variable_type`,`template_variable`) values ( '14','POSITION_NAME');
insert into `tp_template_generic_vars` (`template_variable_type`,`template_variable`) values ( '14','POSITION_STEP_NAME');

insert into `tp_template_types` (`template_type_id`,`template_type`) values ( '17','Feedback Notification To Assigned User');
insert into `tp_templates` (`template_id`,`template_code`,`template_name`,`template_subject_file`,`template_content_file`,`template_auto`,`template_format`,`template_private`,`template_type_id`,`template_is_default`,`template_date_created`,`user_id`) values (83,'feedbackReminderAssigned','Feedback Notification To Assigned User','feedbackReminderAssignedSubject.vm','feedbackReminderAssignedContent.vm','0','0','0','17','1',now(),1);
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values ( '17','1');
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values ( '17','3');
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values ( '17','4');
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values ( '17','11');
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values ( '17','14');

UPDATE tp_permissions SET permission_rank=permission_rank+1 WHERE permission_rank >37;
INSERT INTO `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) VALUES (65,38,'Send Notification to User for non Schedulable step',41,'1');

INSERT INTO tp_user_permissions(user_id,permission_id) VALUES(1,65);

UPDATE tp_permissions SET permission_rank=permission_rank+1 WHERE permission_rank >48;
INSERT INTO `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) VALUES (66,49,'Publish position for walk-in',46,'1');

insert into tp_role_permissions (role_id,permission_id) values (1,66);
insert into tp_role_permissions (role_id,permission_id) values (3,66);
insert into tp_role_permissions (role_id,permission_id) values (4,66);
insert into tp_role_permissions (role_id,permission_id) values (6,66);

insert into tp_user_permissions (user_id,permission_id)
(select tu.user_id, 66 from tp_users tu left join tp_user_roles tur on (tu.user_id=tur.user_id) where tur.ROLE_ID in (1,3,4,6));
