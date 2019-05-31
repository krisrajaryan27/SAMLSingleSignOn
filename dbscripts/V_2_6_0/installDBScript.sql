use talentpool;

DELETE FROM tp_recent_searches;

ALTER TABLE tp_report_scheduler MODIFY degree_ids VARCHAR(255);

ALTER TABLE tp_users ADD IS_USER_LDAP_SETTING CHAR(1);
UPDATE tp_users SET IS_USER_LDAP_SETTING='0' WHERE IS_USER_LDAP_SETTING IS NULL;

alter table `tp_departments` add column `dept_parent_id` int (10)   NULL  after `dept_status`;
alter table `tp_positions` add column `sub_dept_id` int (10)   NULL  after `position_priority`, add column `sub_sub_dept_id` int (10)   NULL  after `sub_dept_id`;
alter table `tp_positions` add foreign key `FK_tp_positions_3`(`sub_dept_id`) references `tp_departments` (`dept_id`);
alter table `tp_positions` add foreign key `FK_tp_positions_4`(`sub_sub_dept_id`) references `tp_departments` (`dept_id`);
alter table `tp_positions` add column `position_level` varchar (20)   NULL  after `sub_sub_dept_id`;
alter table `tp_positions` add column `position_referal_fees` varchar (50)   NULL  after `position_level`;

alter table `tp_applicants` add column `applicant_notice_period` varchar (25)   NULL  after `source_id_original`, 
add column `applicant_level_offered` varchar (20)   NULL  after `applicant_notice_period`, 
add column `applicant_designation_offered` varchar (50)   NULL  after `applicant_level_offered`, 
add column `offered_ctc` varchar (10)   NULL  after `applicant_designation_offered`;

alter table `tp_source_types` add column `source_type_category` char (1)  DEFAULT '0' NOT NULL  after `system_generated`;
UPDATE tp_source_types SET source_type_category='1' WHERE system_generated='1';
insert into `tp_source_types` (`source_type`,`system_generated`,`source_type_category`) values ( 'Employee Referals','1','2');

insert into `tp_application_properties` (`application_property`,`application_property_value`) values ( 'department_level_1','Department');                                                                    
insert into `tp_application_properties` (`application_property`,`application_property_value`) values ( 'department_level_2','Sub Department');                                                                    
insert into `tp_application_properties` (`application_property`,`application_property_value`) values ( 'department_level_3','Group');                                                                    


alter table `tp_report_scheduler` add column `number_range` char (2)   NULL  after `email_body`,
add column `sub_department_id` int (11)   NULL  after `number_range`, 
add column `sub_sub_department_id` int (11)   NULL  after `sub_department_id`;

alter table `tp_applicants` add column `vendor_id` bigint (20)   NULL  after `offered_ctc`;
alter table `tp_applicants` add foreign key `FK_tp_applicants_5`(`user_id`) references `tp_users` (`USER_ID`);
alter table `tp_applicants` add foreign key `FK_tp_applicants_6`(`vendor_id`) references `tp_users` (`USER_ID`);

update tp_position_steps set position_step_level=2 where position_step_level=1;
update tp_position_steps set position_step_level=1 where position_step_level=0;

update tp_applicants ta set vendor_id=user_id where user_id=(select tur.user_id from tp_user_roles tur where tur.user_id=ta.user_id and tur.role_id=7);

alter table `tp_users` add foreign key `FK_tp_users`(`USER_SOURCE_ID`) references `tp_sources` (`source_id`);

insert into `tp_template_generic_vars` (`template_variable_type`,`template_variable`) values ( '5','USER_PASSWORD');
insert into `tp_template_generic_vars` (`template_variable_type`,`template_variable`) values ( '5','USER_NAME');

insert into `tp_template_types` (`template_type_id`,`template_type`) values ( '16','Forgot password');
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values ( '16','1');
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values ( '16','2');
insert into `tp_template_vars` (`template_type_id`,`template_variable_type`) values ( '16','5');

insert into `tp_templates` (`template_code`,`template_name`,`template_subject_file`,
`template_content_file`,`template_auto`,`template_format`,
`template_private`,`template_type_id`,`template_is_default`,
`template_date_created`,`user_id`) 
values ( 'forgotPassword','Forgot password','forgotPasswordSubject.vm',
'forgotPasswordContent.vm','0','0','0','16','1',now(),'1');


create table tt_temp_user as 
select user_id, user_email, count(*) from tp_users group by user_email
having count(*)>1;

update tp_users tu, tt_temp_user tmp set tu.user_email = concat(tu.user_email,tu.user_id) where tu.user_id!=tmp.user_id 
AND tu.user_email=tmp.user_email;

drop table tt_temp_user;

alter table `tp_users` add unique `USER_EMAIL` ( `USER_EMAIL` );

insert into `tp_roles` (`ROLE_ID`,`ROLE_TITLE`) values ( '8','Employee');

alter table `tp_departments` drop key `dept_name`;