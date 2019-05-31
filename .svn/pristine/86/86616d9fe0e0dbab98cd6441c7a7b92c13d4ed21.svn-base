insert into `tp_departments` (`dept_id`,`dept_name`,`dept_status`) 
values ( '10','TalentPool-demo','1');
insert into `tp_departments` (`dept_id`,`dept_name`,`dept_status`,`dept_parent_id`) 
values ( '11','Developer-demo','1','10');

insert into `tp_skill_category` (`skill_category_id`,`skill_category_description`) 
values ( '10','TechnicalDemo');
insert into `tp_skills` (`skill_id`,`skill_category_id`,`skill`) values ( '10','10','java demo');
insert into `tp_skills` (`skill_id`,`skill_category_id`,`skill`) values ( '11','10','c demo');
insert into `tp_skills` (`skill_id`,`skill_category_id`,`skill`) values ( '12','10','vb');
insert into `tp_skills` (`skill_id`,`skill_category_id`,`skill`) values ( '13','10','.net');
insert into `tp_skills` (`skill_id`,`skill_category_id`,`skill`) values ( '14','10','sql');
insert into `tp_skills` (`skill_id`,`skill_category_id`,`skill`) values ( '15','10','php');
insert into `tp_skills` (`skill_id`,`skill_category_id`,`skill`) values ( '16','10','ruby');

insert into `tp_ratings` (`rating_id`,`rating_title`,`date_created`,`user_id`,`rating_status`) 
values ( '10','Three Point Raiting','2010-05-05','1','1');
insert into `tp_rating_fields` (`rating_field_id`,`rating_id`,`rating_field_desc`,`rating_field_status`,`rating_rank`) 
values ( '10','10','Good','1','1');
insert into `tp_rating_fields` (`rating_field_id`,`rating_id`,`rating_field_desc`,`rating_field_status`,`rating_rank`) 
values ( '11','10','Ok','1','2');
insert into `tp_rating_fields` (`rating_field_id`,`rating_id`,`rating_field_desc`,`rating_field_status`,`rating_rank`) 
values ( '12','10','Poor','1','3');

insert into `tp_feedback_field_categories` (`category_id`,`category_name`,`category_status`,`system_generated`) 
values ( '20','Radio','1','0');
insert into `tp_feedback_fields` (`feedback_field_id`,`category_id`,`feedback_field_title`,`feedback_field_desc`,`feedback_field_status`,`system_generated`) 
values ( '20','20','Technical Skills','','1','0');
insert into `tp_feedback_forms` (`feedback_form_id`,`feedback_form_title`,`feedback_form_header`,`feedback_form_display_type`,`user_id`,`date_created`,`feedback_form_status`,`last_modified`) 
values ( '50','Default demo','','1','1','2010-05-05','1','2010-05-05');

insert into `tp_feedback_form_fields` (`feedback_form_field_id`,`feedback_form_id`,`feedback_field_id`,`feedback_form_field_desc`,`feedback_form_field_rank`,`feedback_form_field_type`,`rating_id`,`feedback_form_field_comment_required`,`feedback_form_field_display_type`,`feedback_form_field_is_mandatory`) 
values ( '51','50','20','','1','0',NULL,'0','1','0');
insert into `tp_feedback_form_fields` (`feedback_form_field_id`,`feedback_form_id`,`feedback_field_id`,`feedback_form_field_desc`,`feedback_form_field_rank`,`feedback_form_field_type`,`rating_id`,`feedback_form_field_comment_required`,`feedback_form_field_display_type`,`feedback_form_field_is_mandatory`) 
values ( '52','50','20','','2','1','10','0','1','0');
insert into `tp_feedback_form_fields` (`feedback_form_field_id`,`feedback_form_id`,`feedback_field_id`,`feedback_form_field_desc`,`feedback_form_field_rank`,`feedback_form_field_type`,`rating_id`,`feedback_form_field_comment_required`,`feedback_form_field_display_type`,`feedback_form_field_is_mandatory`) 
values ( '53','50','1','','3','0',NULL,'0','1','0');
insert into `tp_feedback_form_fields` (`feedback_form_field_id`,`feedback_form_id`,`feedback_field_id`,`feedback_form_field_desc`,`feedback_form_field_rank`,`feedback_form_field_type`,`rating_id`,`feedback_form_field_comment_required`,`feedback_form_field_display_type`,`feedback_form_field_is_mandatory`) 
values ( '54','50','1','System Generated','4','1',NULL,'1','1','0');

insert into `tp_locations` (`location_id`,`location_name`) 
values ( '10','Pune');
insert into `tp_locations` (`location_id`,`location_name`) 
values ( '11','Mumbai');

insert into `tp_custom_fields` (`custom_field_id`,`custom_field_name`,`custom_field_type`,`custom_field_attributes`,`custom_field_other_attributes`,`custom_field_display_name`,`custom_field_default_value`,`custom_field_options`,`custom_field_input_allowed`,`custom_field_required`,`custom_field_rank`,`custom_field_searchable`,`custom_field_entity_type`) 
values ( '10','app_passportnumber','text','[maxlength="25"][size="15"][class="Grey"]','','Passport Number','','','1','0','10','0','1');
insert into `tp_custom_fields` (`custom_field_id`,`custom_field_name`,`custom_field_type`,`custom_field_attributes`,`custom_field_other_attributes`,`custom_field_display_name`,`custom_field_default_value`,`custom_field_options`,`custom_field_input_allowed`,`custom_field_required`,`custom_field_rank`,`custom_field_searchable`,`custom_field_entity_type`) 
values ( '11','app_dateOfBirth','text','[maxlength="25"][size="15"][class="Grey"]','','Date of Birth','','','1','0','11','0','1');
insert into `tp_custom_fields` (`custom_field_id`,`custom_field_name`,`custom_field_type`,`custom_field_attributes`,`custom_field_other_attributes`,`custom_field_display_name`,`custom_field_default_value`,`custom_field_options`,`custom_field_input_allowed`,`custom_field_required`,`custom_field_rank`,`custom_field_searchable`,`custom_field_entity_type`) 
values ( '12','app_gender','text','[maxlength="25"][size="15"][class="Grey"]','','Gender','','','1','0','12','0','1');

delete from `tp_user_roles` where `USER_ID`='8';
delete from `tp_user_permissions` where `USER_ID`='8';
delete from `tp_report_level_user` where `USER_ID`='8';
delete from `tp_users` where `USER_ID`='8';
