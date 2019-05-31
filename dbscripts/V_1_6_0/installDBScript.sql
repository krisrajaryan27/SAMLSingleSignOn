use talentpool;

delete from tp_recent_searches;

delete from `tp_role_tasks` where `ROLE_ID`='5' and `TASK_ID`='3';

alter table `tp_applicants` add column `resume_updated_date` datetime  NOT NULL  after `expected_ctc_date`;

update tp_applicants set resume_updated_date=applicant_date_created;

insert into `tp_templates` (`template_code`,`template_name`,
`template_subject_file`,`template_content_file`,`template_auto`,`template_date_created`,`user_id`) 
values ('autoReplyEmail','Auto reply email template',
'autoReplyEmailSubject.vm','autoReplyEmailContent.vm','0',now(),1);

insert into `tp_application_properties` (`application_property`,`application_property_value`) 
values ( 'send_auto_reply_email','0');

create table `tp_template_types` (    
`template_type_id` smallint NOT NULL AUTO_INCREMENT,  
`template_type` varchar(50) NOT NULL, 
PRIMARY KEY (`template_type_id`));

insert into `tp_template_types` (`template_type`) values ('Appointment Reminder - Applicant');
insert into `tp_template_types` (`template_type`) values ('Appointment Reminder - Interviewer');
insert into `tp_template_types` (`template_type`) values ('Appointment Reminder - Owner');
insert into `tp_template_types` (`template_type`) values ('New Appointment Notification');
insert into `tp_template_types` (`template_type`) values ('Modified Appointment Notification');
insert into `tp_template_types` (`template_type`) values ('SMS Reminder - Applicant');
insert into `tp_template_types` (`template_type`) values ('SMS Reminder - Interviewer');
insert into `tp_template_types` (`template_type`) values ('SMS Reminder - Owner');
insert into `tp_template_types` (`template_type`) values ('Feedback Reminder - Interviewer');
insert into `tp_template_types` (`template_type`) values ('Email / Mass Email - Applicant');
insert into `tp_template_types` (`template_type`) values ('Email - User');
insert into `tp_template_types` (`template_type`) values ('Auto Reply Email Template');
insert into `tp_template_types` (`template_type`) values ('Cancelled Appointment Notification');

delete from tp_template_vars
where template_code in (select template_code from tp_templates where template_auto=1);

delete from tp_templates 
where template_auto=1;

alter table `tp_templates` add column `template_is_default` char(1)  DEFAULT '0' NOT NULL  after `template_private`;

update tp_templates set template_is_default=1 where template_private=0;

update tp_templates
set template_is_default=1
where template_code=(select template_code
from
(
select template_code, template_type
from tp_template_vars
where template_type != 1
and template_code in (select template_code from tp_templates where template_private!=0)
group by template_code
order by template_type desc
) as t
where template_type=2 limit 0,1);

update tp_templates
set template_is_default=1
where template_code=(select template_code
from
(
select template_code, template_type
from tp_template_vars
where template_type != 1
and template_code in (select template_code from tp_templates where template_private!=0)
group by template_code
order by template_type desc
) as t
where template_type=3 limit 0,1);

alter table `tp_templates` 
add column `template_type_id` char (3)  DEFAULT '0' NOT NULL  
after `template_private`;

update tp_templates
set template_type_id=3
where template_code='appointmentReminderOwner';

update tp_templates
set template_type_id=2
where template_code='appointmentReminderInterviewer';

update tp_templates
set template_type_id=1
where template_code='appointmentReminderCandidate';

update tp_templates
set template_type_id=9
where template_code='appointmentFeedbackReminder';

update tp_templates
set template_type_id=4
where template_code='newAppointmentNotification';

update tp_templates
set template_type_id=5
where template_code='modifiedAppointmentNotification';

update tp_templates
set template_type_id=13
where template_code='cancelledAppointmentNotification';

update tp_templates
set template_type_id=8
where template_code='smsReminderOwner';

update tp_templates
set template_type_id=7
where template_code='smsReminderInterviewer';

update tp_templates
set template_type_id=6
where template_code='smsReminderCandidate';

update tp_templates
set template_type_id=12
where template_code='autoReplyEmail';

update tp_templates
set template_type_id=10
where template_code in (select template_code
from
(
select template_code, template_type
from tp_template_vars
where template_type != 1
and template_code in (select template_code from tp_templates where template_private!=0)
group by template_code
order by template_type desc
) as t
where template_type=3);

update tp_templates
set template_type_id=11
where template_code in (select template_code
from
(
select template_code, template_type
from tp_template_vars
where template_type != 1
and template_code in (select template_code from tp_templates where template_private!=0)
group by template_code
order by template_type desc
) as t
where template_type=2);

alter table `tp_template_generic_vars` change `template_type` `template_variable_type` smallint(6) NOT NULL , change `template_var` `template_variable` varchar (100) NOT NULL;

alter table `tp_template_vars` change `template_type` `template_variable_type` smallint(6) NULL;

alter table `tp_tmp_attachments` drop column `attachment_removed`;

drop table `tp_template_vars`;

create table `tp_template_vars` (
`template_type_id` smallint NOT NULL,
`template_variable_type` smallint NOT NULL
);

insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values (3,1);
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values (3,3);
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values (3,10);
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values (2,1);
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values (2,3);
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values (2,10);
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values (1,1);
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values (1,3);
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values (1,10);
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values (9,1);
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values (9,3);
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values (9,10);
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values (9,11);
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values (10,1);
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values (10,3);
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values (11,1);
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values (11,2);
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values (2,4);
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values (1,4);
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values (9,4);
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values (4,1);
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values (4,3);
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values (4,4);
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values (4,10);
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values (5,1);
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values (5,3);
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values (5,4);
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values (5,10);
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values (13,1);
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values (13,3);
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values (13,4);
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values (13,10);
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values (4,2);
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values (5,2);
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values (13,2);
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values (8,3);
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values (8,10);
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values (7,3);
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values (7,10);
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values (6,1);
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values (6,4);
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values (6,10);

alter table `tp_appointments` 
add column `remind_me_template_code` varchar(100) NOT NULL after `remind_me`, 
add column `remind_attendee_template_code` varchar(100) NOT NULL after `remind_attendee`,
add column `remind_applicant_template_code` varchar(100) NOT NULL after `remind_applicant`, 
add column `sms_remind_me_template_code` varchar(100) NOT NULL after `sms_remind_me`, 
add column `sms_remind_attendee_template_code` varchar(100) NOT NULL after `sms_remind_attendee`, 
add column `sms_remind_applicant_template_code` varchar(100) NOT NULL after `sms_remind_applicant`;

update tp_appointments
set remind_me_template_code='appointmentReminderOwner',
remind_attendee_template_code='appointmentReminderInterviewer',
remind_applicant_template_code='appointmentReminderCandidate',
sms_remind_me_template_code='smsReminderOwner',
sms_remind_attendee_template_code='smsReminderInterviewer',
sms_remind_applicant_template_code='smsReminderCandidate';

update tp_appointments
set remind_me_template_code=''
where remind_me=0;

update tp_appointments
set remind_attendee_template_code=''
where remind_attendee=0;

update tp_appointments
set remind_applicant_template_code=''
where remind_applicant=0;

update tp_appointments
set sms_remind_me_template_code=''
where sms_remind_me=0;

update tp_appointments
set sms_remind_attendee_template_code=''
where sms_remind_attendee=0;

update tp_appointments
set sms_remind_applicant_template_code=''
where sms_remind_applicant=0;

drop table `tp_applicant_employment`;

create table `tp_flags` 
(
`flag_id` smallint NOT NULL AUTO_INCREMENT,
`flag_image` varchar(50) NOT NULL,
`flag_text` varchar(100) NOT NULL, 
PRIMARY KEY (`flag_id`)
);

insert into `tp_flags` (`flag_id`,`flag_image`,`flag_text`) values (1,'images/flag_blue.gif','Blue Flag');
insert into `tp_flags` (`flag_id`,`flag_image`,`flag_text`) values (2,'images/flag_green.gif','Green Flag');
insert into `tp_flags` (`flag_id`,`flag_image`,`flag_text`) values (3,'images/flag_red.gif','Red Flag');
insert into `tp_flags` (`flag_id`,`flag_image`,`flag_text`) values (4,'images/flag_yellow.gif','Yellow Flag');
insert into `tp_flags` (`flag_id`,`flag_image`,`flag_text`) values (5,'images/flag_grey.gif','Grey Flag');
insert into `tp_flags` (`flag_id`,`flag_image`,`flag_text`) values (6,'images/flag_purple.gif','Purple Flag');

CREATE TABLE `tp_applicant_flags` (                                                                                
  `applicant_id` bigint(20) NOT NULL,                                                                              
  `flag_id` smallint(6) NOT NULL,                                                                                  
  PRIMARY KEY  (`applicant_id`,`flag_id`),                                                                         
  KEY `FK_tp_applicant_flags_2` (`flag_id`),                                                                       
  CONSTRAINT `tp_applicant_flags_ibfk_2` FOREIGN KEY (`flag_id`) REFERENCES `tp_flags` (`flag_id`),                
  CONSTRAINT `tp_applicant_flags_ibfk_1` FOREIGN KEY (`applicant_id`) REFERENCES `tp_applicants` (`applicant_id`)  
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


insert into `tp_role_tasks` (`ROLE_ID`,`TASK_ID`) values ( '1','26');

insert into `tp_application_properties` (`application_property`,`application_property_value`) values ( 'ldap_enabled','0');

CREATE TABLE `tp_ldap_servers` (                        
   `ldap_server_id` int(11) NOT NULL auto_increment,     
   `ldap_server_url` varchar(100) NOT NULL,              
   `ldap_security_principal` varchar(250) default NULL,  
   PRIMARY KEY  (`ldap_server_id`),                      
   UNIQUE KEY `ldap_server_url` (`ldap_server_url`)      
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

update tp_position_steps
set position_step_isdefault=1
where position_step_title='Shortlist';

alter table `tp_users` change `USER_NAME` `USER_NAME` varchar (50)   
NOT NULL  COLLATE latin1_swedish_ci;
update tp_users set user_name=concat(user_name,'%',CURRENT_TIMESTAMP()+0,'%') where user_status=0;

create table tmp_usrs as select user_id, user_name , count(*) from tp_users 
group by user_name having count(*) >1;
update tp_users set user_name=CONCAT(user_name, user_id) where user_id not in(select user_id from 
tmp_usrs) and user_name in (select user_name from tmp_usrs);
drop table tmp_usrs;

alter table `tp_users` add unique `USER_NAME` ( `USER_NAME` );

update `tp_sms_settings` set 
`sms_provider`='sms.globalbulksms.com',
`sms_provider_property_value`='http://sms.globalbulksms.com/sendsms.asp?user=XXX&password=YYY&Sender=$SENDERID&Text=$MESSAGE&PhoneNumber=91$PHONE&track=1';

alter table `tp_inbox_email_attachments` 
change `attachment_content_type` `attachment_content_type` text   NULL  COLLATE latin1_swedish_ci;

alter table `tp_applicant_inbox_email_attachments` 
change `attachment_content_type` `attachment_content_type` text   NULL  COLLATE latin1_swedish_ci;

