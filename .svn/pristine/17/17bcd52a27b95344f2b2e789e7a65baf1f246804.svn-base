use talentpool;

DELETE FROM tp_recent_searches;

CREATE TABLE `tp_applicant_documents` (                                                                                 
  `document_id` bigint(20) NOT NULL auto_increment,                                                                     
  `applicant_id` bigint(20) NOT NULL,                                                                                   
  `file_name` varchar(250) NOT NULL,                                                                                    
  `document_path` varchar(250) NOT NULL,                                                                                
  `user_id` bigint(20) NOT NULL,                                                                                        
  `date_created` datetime NOT NULL,                                                                                     
  PRIMARY KEY  (`document_id`),                                                                                         
  KEY `FK_tp_applicant_documents` (`applicant_id`),                                                                     
  KEY `FK_tp_applicant_documents_1` (`user_id`),                                                                        
  CONSTRAINT `tp_applicant_documents_ibfk_1` FOREIGN KEY (`applicant_id`) REFERENCES `tp_applicants` (`applicant_id`),  
  CONSTRAINT `tp_applicant_documents_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `tp_users` (`USER_ID`)                  
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

delete from tp_role_tasks
where task_id in (32,33,51,52,53)
and role_id=5;

alter table `tp_inbox_emails` add column `user_id` bigint (20) NULL  after `auto_import_errors`;
alter table `tp_inbox_emails` add foreign key `FK_tp_inbox_emails`(`user_id`) references `tp_users` (`USER_ID`);

update tp_applicant_selection_process
set position_step_id_from=0
where position_step_id_from in (select position_step_id
				from tp_position_steps
				where position_step_isdefault=1);

alter table `tp_applicant_documents` add column `access_level` char (1)  
DEFAULT '0' NOT NULL  after `date_created`;

insert into `tp_role_tasks` (`ROLE_ID`,`TASK_ID`) values ( '1','11');
insert into `tp_role_tasks` (`ROLE_ID`,`TASK_ID`) values ( '3','11');
insert into `tp_role_tasks` (`ROLE_ID`,`TASK_ID`) values ( '4','11');
insert into `tp_role_tasks` (`ROLE_ID`,`TASK_ID`) values ( '6','11');

CREATE TABLE `tp_cost_types` (                     
     `cost_type_id` int(11) NOT NULL auto_increment,  
     `cost_type` varchar(250) NOT NULL,               
     PRIMARY KEY  (`cost_type_id`),                   
     UNIQUE KEY `cost_type` (`cost_type`)             
   ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

insert into `tp_cost_types` (`cost_type_id`,`cost_type`) values (1,'Travel cost candidate');
insert into `tp_cost_types` (`cost_type_id`,`cost_type`) values (2,'Placement agency fees');
insert into `tp_cost_types` (`cost_type_id`,`cost_type`) values (3,'Advertisement');
insert into `tp_cost_types` (`cost_type_id`,`cost_type`) values (4,'Proffessional fees');
insert into `tp_cost_types` (`cost_type_id`,`cost_type`) values (5,'Referral fees');
insert into `tp_cost_types` (`cost_type_id`,`cost_type`) values (6,'Subscription charges');
insert into `tp_cost_types` (`cost_type_id`,`cost_type`) values (7,'Relocation');
               
CREATE TABLE `tp_costs` (                                                                                 
    `cost_id` bigint(20) NOT NULL auto_increment,                                                           
    `cost_type_id` int(11) NOT NULL,                                                                        
    `cost_amount` decimal(12,2) NOT NULL,                                                                   
    `cost_paid_date` date NOT NULL,                                                                         
    `source_id` int(11) default NULL,                                                                       
    `cost_remark` text,                                                                                     
    `user_id` bigint(20) NOT NULL,                                                                          
    `date_created` datetime NOT NULL,                                                                       
    `cost_date_from` date default NULL,                                                                     
    `cost_date_to` date default NULL,                                                                       
    PRIMARY KEY  (`cost_id`),                                                                               
    KEY `FK_tp_costs` (`cost_type_id`),                                                                     
    KEY `FK_tp_costs_2` (`source_id`),                                                                      
    KEY `FK_tp_costs_3` (`user_id`),                                                                        
    CONSTRAINT `tp_costs_ibfk_1` FOREIGN KEY (`cost_type_id`) REFERENCES `tp_cost_types` (`cost_type_id`),  
    CONSTRAINT `tp_costs_ibfk_3` FOREIGN KEY (`source_id`) REFERENCES `tp_sources` (`source_id`),           
    CONSTRAINT `tp_costs_ibfk_4` FOREIGN KEY (`user_id`) REFERENCES `tp_users` (`USER_ID`)                  
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
          
CREATE TABLE `tp_cost_positions` (                                                                             
     `cost_id` bigint(20) default NULL,                                                                           
     `position_id` int(11) default NULL,                                                                          
     KEY `FK_tp_cost_positions` (`cost_id`),                                                                      
     KEY `FK_tp_cost_positions_1` (`position_id`),                                                                
     CONSTRAINT `tp_cost_positions_ibfk_1` FOREIGN KEY (`cost_id`) REFERENCES `tp_costs` (`cost_id`),             
     CONSTRAINT `tp_cost_positions_ibfk_2` FOREIGN KEY (`position_id`) REFERENCES `tp_positions` (`position_id`)  
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
                   
alter table `tp_skills` drop column `skill_status`;

INSERT INTO `tp_application_properties` (`application_property`,`application_property_value`) VALUES( 'financial_yr_start_month','3');

alter table tp_position_skills add column skill_type char (1)  DEFAULT '0' NOT NULL  after skill_status;

update tp_position_step_status_messages
set is_default = 1
where status_message_id in 
(
select * from (
select status_message_id
from
(
select *
from tp_position_step_status_messages
order by position_step_id, is_default desc
) t
group by position_step_id
) as t1);


insert into `tp_roles` (`ROLE_ID`,`ROLE_TITLE`) values ( '7','Vendor');

alter table `tp_users` add column `USER_SOURCE_ID` int NULL after `USER_SMS_ENABLED`;

alter table `tp_sources` 
add column `source_email` varchar(50) NULL after `source_title`, 
add column `source_phone` varchar(50) NULL after `source_email`, 
add column `source_mobile` varchar(50) NULL after `source_phone`, 
add column `is_send_email_to_source` char(1) DEFAULT '0' NULL after `source_mobile`, 
add column `is_send_sms_to_source` char(1) DEFAULT '0' NULL after `is_send_email_to_source`, 
add column `lock_in_period_on_import` int NULL DEFAULT '0' after `is_send_sms_to_source`;

insert into `tp_application_properties` (`application_property`,`application_property_value`) values ( 'send_duplicate_resume_upload_tried_notification','0');
insert into `tp_application_properties` (`application_property`,`application_property_value`) values ( 'send_duplicate_resume_upload_tried_notification_to_email','');

insert into `tp_application_properties` (`application_property`,`application_property_value`) values ( 'duration_to_display_sent_messages','7');
insert into `tp_application_properties` (`application_property`,`application_property_value`) values ( 'duration_to_display_sent_emails','7');

insert into `tp_application_properties` (`application_property`,`application_property_value`) values ( 'notify_vendor_activity_to_hr','1');
insert into `tp_application_properties` (`application_property`,`application_property_value`) values ( 'show_detailed_activity_to_vendor','0');

CREATE TABLE `tp_duplicate_detection_settings` (  
   `field_id` smallint(6) NOT NULL,                
   `field_type` char(1) NOT NULL,                  
   `check_type` char(1) NOT NULL,                  
   `check_for` char(1) NOT NULL default '0'        
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

insert into `tp_duplicate_detection_settings` (`field_id`,`field_type`,`check_type`,`check_for`) values (2,'0','0','0');
                                 
create table `tp_position_vendors` (    
`position_id` int   NOT NULL ,  
`source_id` int   NOT NULL    
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

drop table tp_applicants_tmp_text_resume;
drop table tp_bugs;

create table tp_tmp_positions as select position_code from tp_positions;
UPDATE tp_positions SET position_code = CONCAT(position_code, '-',position_id) where position_code 
in (select position_code  from tp_tmp_positions group by position_code having count(*)>1);
drop table tp_tmp_positions;

alter table tp_positions change `position_code` `position_code` varchar (50)   NULL  COLLATE latin1_swedish_ci;
alter table `tp_positions` add unique `position_code` ( `position_code` );

alter table `tp_source_types` add column `system_generated` char (1)  DEFAULT '0' NOT NULL  after `source_type`;
insert into tp_source_types(source_type,system_generated) values('Lock-in period expired','1');
alter table `tp_sources` add column `system_generated` char (1)  DEFAULT '0' NOT NULL  after `source_status`;
insert into tp_sources(source_type_id,source_title,source_email,source_phone,source_mobile,
is_send_email_to_source,is_send_sms_to_source,lock_in_period_on_import,source_status,system_generated)
values((select source_type_id from tp_source_types where system_generated='1' limit 0,1),
'Lock-in period expired' ,null,null,null,null,null,null,'1','1');

alter table `tp_applicants` add column `resume_locked_date` date   NOT NULL  after `resume_updated_date`;
update tp_applicants set resume_locked_date=applicant_date_created;
alter table `tp_applicants` add column `source_id_original` int (11)   NOT NULL  after `resume_locked_date`;
update tp_applicants set source_id_original=source_id;
alter table `tp_applicants` add foreign key `FK_tp_applicants_4`(`source_id_original`) references `tp_sources` (`source_id`);


insert into `tp_template_generic_vars` (`template_variable_type`,`template_variable`) values ( '12','DUPLICATE_DATA_INFO');
insert into `tp_template_types` (`template_type_id`,`template_type`) values ( '14','Duplicate Upload Tried By Vendor Notification');
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values ( '14','12');

insert into `tp_templates` (`template_code`,`template_name`,`template_subject_file`,
`template_content_file`,`template_auto`,`template_format`,`template_private`,
`template_type_id`,`template_is_default`,`template_date_created`,`user_id`) 
values ('duplicateUploadByVendor','Duplicate upload by vendor notification template','duplicateUploadByVendorSubject.vm',
'duplicateUploadByVendorContent.vm','0','0','0','14','1',now(),1);

call PROC_MIGRATE_SKILLS();

alter table `tp_positions` drop column `position_primary_skills`, drop column `position_secondary_skills`;

