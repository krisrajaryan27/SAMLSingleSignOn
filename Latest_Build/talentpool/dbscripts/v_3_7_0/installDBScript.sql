use talentpool;

DELETE FROM tp_recent_searches;

alter table `tp_positions` add column `position_publish_date` datetime   NULL  after `requisition_approval_template_id`;

insert into `tp_application_properties` (`application_property`,`application_property_value`) values ( 'rejection_email_to_candidate','0');

insert into `tp_template_generic_vars` (`template_variable_type`,`template_variable`) values ( '3','CANDIDATE_INTERVIEW_FEEDBACK');

insert into `tp_templates` (`template_id`,`template_code`,`template_name`,`template_subject_file`,`template_content_file`,`template_auto`,`template_format`,`template_private`,`template_type_id`,`template_is_default`,`template_date_created`,`user_id`)
values ( NULL,'rejectEmailToCandidate','Reject Email To Candidate','rejectEmailToCandidateSubject.vm','rejectEmailToCandidateContent.vm','0','0','0','24','1','2009-09-03 00:00:00','1');

insert into `tp_template_types` (`template_type_id`,`template_type`) values ( 24,'Rejection Email to Candidate');

insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values ( '24','2');
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values ( '24','3');
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values ( '24','14');

insert into `tp_inbox_folders` (`folder_id`,`folder_name`,`is_system_defined`,`inbox_id`,`date_created`,`user_id`,`folder_created_by`) values ( NULL,'Drafts','2','1','2009-09-03','1','1');

alter table `tp_templates` add column `template_is_save_as_draft` char (1)  DEFAULT '0' NOT NULL  after `template_date_created`;

UPDATE tp_templates
SET template_is_save_as_draft = 1
WHERE template_type_id IN (20,22,24);

create table `tp_user_configurations` (`conf_id` int NOT NULL ,`user_id` bigint NOT NULL,`value` char (250) NULL );

alter table `tp_templates` add column `do_show_save_as_draft_option` char (1)  DEFAULT '0' NOT NULL  after `template_is_save_as_draft`;

update tp_templates
set do_show_save_as_draft_option = 1
where template_is_save_as_draft = 1;

alter table `tp_inbox_emails` add column `position_id` bigint   NULL  after `entry_id`;

UPDATE tp_templates
SET template_name = 'Candidate Rejected Email to Candidate'
WHERE template_code = 'rejectEmailToCandidate'
AND template_is_default = 1;

UPDATE tp_templates
SET template_name = 'Candidate Rejected Email to Employee'
WHERE template_code = 'rejectEmailToEmployee'
AND template_is_default = 1;

UPDATE tp_templates
SET template_name = 'Candidate Rejected Email to Vendor'
WHERE template_code = 'rejectEmailToVendor'
AND template_is_default = 1;

alter table `tp_inbox_emails` 
add column `applicant_id` bigint   NULL  after `position_id`, 
add column `position_step_id` int   NULL  after `applicant_id`;

alter table `tp_inbox_emails` 
add foreign key `FK_tp_inbox_emails1`(`applicant_id`) references `tp_applicants` (`applicant_id`);

alter table `tp_inbox_emails` 
add foreign key `FK_tp_inbox_emails2`(`position_step_id`) references `tp_position_steps` (`position_step_id`);