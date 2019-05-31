use talentpool;

alter table `tp_reminder` change `applicant_id` `applicant_id` bigint (20)   NULL;

insert into `tp_application_properties` (`application_property`,`application_property_value`) values ( 'show_reminder','1');

insert into `tp_application_properties` (`application_property`,`application_property_value`) 
values ( 'default_hire_by_duration_in_days','45');

alter table `tp_flags` 
add column `flag_type` char (1)  DEFAULT '0' NOT NULL  after `flag_text`, 
add column `flag_status` char (1)  DEFAULT '1' NOT NULL  after `flag_type`, 
add column `user_id` bigint  DEFAULT '1' NOT NULL  after `flag_status`, 
add column `date_created` datetime   NOT NULL  after `user_id`;

update `tp_flags` set `flag_image`='flag_blue.gif' where `flag_id`='1';
update `tp_flags` set `flag_image`='flag_green.gif' where `flag_id`='2';
update `tp_flags` set `flag_image`='flag_red.gif' where `flag_id`='3';
update `tp_flags` set `flag_image`='flag_yellow.gif' where `flag_id`='4';
update `tp_flags` set `flag_image`='flag_grey.gif' where `flag_id`='5';
update `tp_flags` set `flag_image`='flag_purple.gif' where `flag_id`='6';

update tp_flags
set date_created=now();

update tp_permissions
set permission_rank=(permission_rank+1) 
where permission_rank > 55;

insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) 
values ( '67','56','Manage All Flags','52','1');

insert into `tp_user_permissions` (`user_id`,`permission_id`) values ( '1','67');

insert into `tp_application_properties` (`application_property`,`application_property_value`) values ( 'maximum_no_of_public_flags_allowed','10');
insert into `tp_application_properties` (`application_property`,`application_property_value`) values ( 'maximum_no_of_private_flags_allowed','5');