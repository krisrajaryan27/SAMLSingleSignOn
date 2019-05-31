use talentpool;

-- from 12.9.3
INSERT INTO tp_security_questions (security_question) VALUES ("What was your childhood nickname?");
INSERT INTO tp_security_questions (security_question) VALUES ("In which city did you meet your spouse?");
INSERT INTO tp_security_questions (security_question) VALUES ("What is the name of your favorite childhood friend?");
INSERT INTO tp_security_questions (security_question) VALUES ("What street did you live on in third grade?");
INSERT INTO tp_security_questions (security_question) VALUES ("What is your oldest sibling’s birthday month and year? (e.g., January 1900)");
INSERT INTO tp_security_questions (security_question) VALUES ("What school did you attend for sixth grade?");
INSERT INTO tp_security_questions (security_question) VALUES ("What is your oldest cousin's first and last name?");
INSERT INTO tp_security_questions (security_question) VALUES ("What was the name of your first stuffed animal?");
INSERT INTO tp_security_questions (security_question) VALUES ("What was the name of your favorite primary school teacher?");
INSERT INTO tp_security_questions (security_question) VALUES ("In which city does your nearest sibling live?");

INSERT INTO tp_template_types (template_type_id,template_type) VALUES (43,'Confirmation Link');
INSERT INTO tp_template_vars (template_type_id, template_variable_type) values (43, '2');
INSERT INTO tp_templates (template_code, template_name, template_subject_file, template_content_file, 
	template_auto, template_format, template_private, template_type_id, template_is_default, 
	template_date_created,template_is_save_as_draft, do_show_save_as_draft_option, user_id ) 
VALUES ('confirmationLinkEmailToUser', 'Password Change Confirmation Link To User', 
	'confirmationLinkEmailToUserSubject.vm', 'confirmationLinkEmailToUserContent.vm', 0, 0, 0, 43, 1, 
	now(), 0, 0, 1);

INSERT INTO tp_template_generic_vars (template_variable_type, template_variable) 
	VALUES ('23', 'CONFIRMATION_LINK');
INSERT INTO tp_template_vars (template_type_id, template_variable_type) values (43, '23');

UPDATE tp_users SET force_password_change = '1' 
WHERE user_id IN (SELECT user_id 
	FROM tp_user_roles tur LEFT JOIN tp_roles tr ON (tur.role_id = tr.role_id) 
	WHERE tr.role_title = 'Vendor');
	
INSERT INTO tp_application_properties (application_property, application_property_value) VALUES ('default_password_expiry_duration', 60);
INSERT INTO tp_application_properties (application_property, application_property_value) VALUES ('default_password_different_from_last', 3);

INSERT INTO tp_application_properties (application_property, application_property_value) VALUES ('force_position_creation_from_template', '0');

UPDATE tp_users SET old_passwords =  user_password;

UPDATE tp_users SET password_date_modified =  now();

-- from 12.9.5
UPDATE tp_users SET password_date_modified =  now() where password_date_modified is null;

-- 13.0.0
INSERT INTO tp_cr_last_update (last_run_date) VALUES ( '2012-04-01 00:00:00');

UPDATE tp_users SET force_password_change = '1' 
WHERE user_id IN (SELECT user_id 
	FROM tp_user_roles tur LEFT JOIN tp_roles tr ON (tur.role_id = tr.role_id) 
	WHERE tr.role_title != 'Vendor');