use talentpool;

-- scripts for new social media permissions and sources.

INSERT INTO `tp_source_types` (source_type, system_generated, source_type_category)
VALUES ('SOCIAL MEDIA', 1, 4);

insert into `tp_sources` ( source_type_id, source_title, source_email, source_phone, source_mobile, 
is_send_email_to_source, is_send_sms_to_source, lock_in_period_on_import, source_status, system_generated)
values((select source_type_id from tp_source_types where source_type_category = 4 limit 0, 1),'LinkedIn','','','',0,0, 0,1,1),
((select source_type_id from tp_source_types where source_type_category = 4 limit 0, 1),'Facebook','','','',0,0, 0,1,1);

insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) 
values ( '98','94','Socail Media Access','0','1');

insert into tp_role_permissions (`role_id`, `permission_id`) values ('1','98');

INSERT INTO `tp_user_permissions`
(SELECT usr.user_id, '98' FROM tp_user_roles usr
where usr.role_id = '1');

insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) 
values ( '99','95','Post job on social media','98','1');

insert into tp_role_permissions (`role_id`, `permission_id`) values ('1','99');

INSERT INTO `tp_user_permissions`
(SELECT usr.user_id, '99' FROM tp_user_roles usr
where usr.role_id = '1');

insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) 
values ( '100','96','Publish position on Social Web','98','1');

insert into tp_role_permissions (`role_id`, `permission_id`) values ('1','100');

INSERT INTO `tp_user_permissions`
(SELECT usr.user_id, '100' FROM tp_user_roles usr
where usr.role_id = '1');


insert into tp_template_types (template_type_id, template_type) values ('44' , 'Social Media Job Posting Template');
insert into tp_template_vars (template_type_id, template_variable_type) values ('44','18');
INSERT INTO tp_templates (template_code, template_name, template_subject_file, template_content_file, 
	template_auto, template_format, template_private, template_type_id, template_is_default, 
	template_date_created,template_is_save_as_draft, do_show_save_as_draft_option, user_id ) 
VALUES ('socialMediaPositionPosting', 'Social Media Job Posting Template', 
	'socialMediaPositionPostingSubject.vm', 'socialMediaPositionPostingContent.vm', 0, 0, 0, 44, 1, 
	now(), 0, 0, 1);
	
INSERT INTO tp_social_api_implementation (implementation_id,api_type,source_id) 
VALUES (1,'LinkedIn-Group',(SELECT source_id FROM tp_sources WHERE source_title = 'LinkedIn'));

INSERT INTO tp_social_api_implementation (implementation_id,api_type,source_id) 
VALUES (2,'LinkedIn-Company',(SELECT source_id FROM tp_sources WHERE source_title = 'LinkedIn'));

INSERT INTO tp_social_api_implementation (implementation_id,api_type,source_id) 
VALUES (3,'Facebook-Group',(SELECT source_id FROM tp_sources WHERE source_title = 'Facebook'));

INSERT INTO tp_social_api_implementation (implementation_id,api_type,source_id) 
VALUES (4,'Facebook-Company',(SELECT source_id FROM tp_sources WHERE source_title = 'Facebook'));

INSERT INTO tp_template_generic_vars (template_variable_type, template_variable) 
	VALUES ('18', 'COMPANY_CAREERS_PAGE_LINK');

INSERT INTO tp_application_properties (application_property, application_property_value) 
	VALUES ('careers_page_url', '');