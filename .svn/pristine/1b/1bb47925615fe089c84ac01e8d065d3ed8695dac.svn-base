use talentpool;

alter table `tp_appointments` 
add column `sms_remind_me` int  DEFAULT '0' NOT NULL  after `remind_applicant`, 
add column `sms_remind_attendee` int  DEFAULT '0' NOT NULL  after `sms_remind_me`, 
add column `sms_remind_applicant` int  DEFAULT '0' NOT NULL  after `sms_remind_attendee`;

insert into `tp_templates` (`template_code`,`template_name`,`template_subject_file`,`template_content_file`,`template_auto`,`template_date_created`,`user_id`) 
values ('smsReminderOwner','SMS Reminder to Owner','smsReminderOwnerSubject.vm','smsReminderOwnerContent.vm','0',now(),1);
insert into `tp_templates` (`template_code`,`template_name`,`template_subject_file`,`template_content_file`,`template_auto`,`template_date_created`,`user_id`) 
values ('smsReminderInterviewer','SMS Reminder to Interviewer','smsReminderInterviewerSubject.vm','smsReminderInterviewerContent.vm','0',now(),1);
insert into `tp_templates` (`template_code`,`template_name`,`template_subject_file`,`template_content_file`,`template_auto`,`template_date_created`,`user_id`) 
values ('smsReminderCandidate','SMS Reminder to Candidate','smsReminderCandidateSubject.vm','smsReminderCandidateContent.vm','0',now(),1);

insert into `tp_template_vars` (`template_code`,`template_type`) values ('smsReminderOwner',3);
insert into `tp_template_vars` (`template_code`,`template_type`) values ('smsReminderOwner',10);
insert into `tp_template_vars` (`template_code`,`template_type`) values ('smsReminderInterviewer',3);
insert into `tp_template_vars` (`template_code`,`template_type`) values ('smsReminderInterviewer',10);
insert into `tp_template_vars` (`template_code`,`template_type`) values ('smsReminderCandidate',1);
insert into `tp_template_vars` (`template_code`,`template_type`) values ('smsReminderCandidate',4);
insert into `tp_template_vars` (`template_code`,`template_type`) values ('smsReminderCandidate',10);

CREATE TABLE `tp_sms_settings` (
  `sms_provider` varchar(50) default NULL,
  `sms_provider_property` varchar(50) default NULL,
  `sms_provider_property_value` varchar(200) default NULL,
  `sms_provider_isdefault` char(1) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

insert into `tp_sms_settings` 
(`sms_provider`,`sms_provider_property`,`sms_provider_property_value`,`sms_provider_isdefault`) 
values 
('SMS2India','URL','http://www.sms2india.in/smslink.php?username=&password=&mbno=$PHONE&msg=$MESSAGE','1');

alter table `tp_users` add column `USER_SMS_ENABLED` char (1)  DEFAULT '0' NULL  after `USER_CELL_PHONE`;
update `tp_users` set `USER_SMS_ENABLED`='1' where `USER_ID`='1';

CREATE TABLE `tp_application_properties` (            
     `application_property` varchar(250) NOT NULL,       
     `application_property_value` varchar(250) NOT NULL  
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

insert into `tp_application_properties` (`application_property`,`application_property_value`) values ('max_attachment_size_in_mb','10');
insert into `tp_application_properties` (`application_property`,`application_property_value`) values ('max_skills_parsed','5');
insert into `tp_application_properties` (`application_property`,`application_property_value`) values ('search_result_page_size','20');
insert into `tp_application_properties` (`application_property`,`application_property_value`) values ('duration_as_new_resume','7');
insert into `tp_application_properties` (`application_property`,`application_property_value`) values ('send_reminder_to_me','1');
insert into `tp_application_properties` (`application_property`,`application_property_value`) values ('send_reminder_to_interviewer','1');
insert into `tp_application_properties` (`application_property`,`application_property_value`) values ('send_reminder_to_candidate','1');
insert into `tp_application_properties` (`application_property`,`application_property_value`) values ('send_appointment','1');
insert into `tp_application_properties` (`application_property`,`application_property_value`) values ('default_appointment_duration','30');
insert into `tp_application_properties` (`application_property`,`application_property_value`) values ('default_reminder_duration','30');
insert into `tp_application_properties` (`application_property`,`application_property_value`) values ('sms_enabled','0');
insert into `tp_application_properties` (`application_property`,`application_property_value`) values ('send_feedback_reminders','1');

                           
alter table `tp_inbox_settings` 
add column `inbox_incoming_port_no` varchar (10)  DEFAULT '110' NULL  after `inbox_smtp_password`, 
add column `inbox_incoming_ssl_enabled` smallint  DEFAULT '0' NULL  after `inbox_incoming_port_no`, 
add column `inbox_outgoing_port_no` varchar (10)  DEFAULT '25' NULL  after `inbox_incoming_ssl_enabled`, 
add column `inbox_outgoing_ssl_enabled` smallint  DEFAULT '0' NULL  after `inbox_outgoing_port_no`;

update tp_inbox_settings set inbox_incoming_port_no='143' where inbox_server_type=1;
