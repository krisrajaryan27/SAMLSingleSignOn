use talentpool;
UPDATE tp_permissions SET permission_rank = permission_rank + 1
WHERE permission_rank > 53;

INSERT INTO tp_permissions
(permission_id, permission_rank, permission_desc, parent_id, is_permission)
VALUES (96, 54, 'View Offer Proposal', 43, 1);

INSERT INTO tp_role_permissions (role_id, permission_id)
VALUES (1,96);

INSERT INTO tp_role_permissions (role_id, permission_id)
VALUES (3,96);

INSERT INTO tp_role_permissions (role_id, permission_id)
VALUES (4,96);

INSERT INTO tp_role_permissions (role_id, permission_id)
VALUES (6,96);

INSERT INTO tp_user_permissions (user_id, permission_id)
SELECT USER_ID, 96 FROM tp_user_roles WHERE ROLE_ID IN (1,3,4,6);

INSERT INTO tp_salary_component_categories (category_id, category_name) VALUES ('1','Fixed');
INSERT INTO tp_salary_component_categories (category_id, category_name) VALUES ('2','Variable');
INSERT INTO tp_application_properties (application_property, application_property_value) VALUES ('offer_code_template', '{ccc}-{nnnnn}');
INSERT INTO tp_application_properties (application_property, application_property_value) VALUES ('salary_variable_input_salary_label', 'Annual Gross CTC');
INSERT INTO tp_custom_increment_counter (variable_name, incremental_value) VALUES ('offer_number', 0);

UPDATE tp_screen_configurations SET field_rank = field_rank+1 WHERE  field_rank > 12;
INSERT INTO tp_screen_configurations (field_id,field_type,is_process_field,field_rank,field_import_show,field_edit_show,field_import_mandatory,field_confidential,field_vendor_show,field_vendor_mandatory,field_employee_show,field_employee_mandatory,field_applicant_show_on_site,field_applicant_mandatory_on_site) VALUES ('Employment History','0','0',13,'1','1','0','0','0','0','1','0','1','0');

INSERT INTO tp_employers(employer_name) SELECT DISTINCT applicant_current_employer FROM tp_applicants WHERE applicant_current_employer IS NOT NULL;
INSERT INTO tp_applicant_employment_history(applicant_id,employer_id) SELECT ta.applicant_id, te.employer_id FROM tp_applicants ta LEFT JOIN tp_employers te ON (ta.applicant_current_employer=te.employer_name) WHERE te.employer_id IS NOT NULL;

DELETE FROM tp_user_permissions WHERE permission_id = 85;
DELETE FROM tp_role_permissions WHERE permission_id = 85;
DELETE FROM tp_permissions WHERE permission_id = 85;
UPDATE tp_permissions SET permission_rank = permission_rank-1 WHERE permission_rank > 53;

INSERT INTO tp_salary_components
(salary_component_id, salary_component_name, salary_component_description, salary_component_type, salary_component_category_id, system_defined)
VALUES
(200, 'Basic', 'Basic', 'M', '1', '1');

INSERT INTO tp_salary_formulae 
(grade_id, salary_component_id, max_limit, variable1, variable1_factor, constant_factor, is_adjustable, user_id, date_created, system_defined)
SELECT grade_id, 200, 0, 2, 1.0, 0, 0, 1, now(), 1 FROM tp_budget_grades;

INSERT INTO tp_template_generic_vars (template_variable_type,template_variable) VALUES (3,'CANDIDATE_EMPLOYMENT_INFO');

ALTER TABLE tp_excel_import 
ADD COLUMN employ_from_date1 VARCHAR (10) NULL COLLATE latin1_swedish_ci AFTER original_resume_path, 
ADD COLUMN employ_to_date1 VARCHAR (10) NULL COLLATE latin1_swedish_ci AFTER employ_from_date1,
ADD COLUMN employer_name1 VARCHAR (150) NULL COLLATE latin1_swedish_ci AFTER employ_to_date1, 
ADD COLUMN designation_name1 VARCHAR (150) NULL COLLATE latin1_swedish_ci AFTER employer_name1, 
ADD COLUMN employ_from_date2 VARCHAR (10) NULL COLLATE latin1_swedish_ci AFTER designation_name1,
ADD COLUMN employ_to_date2 VARCHAR (10) NULL COLLATE latin1_swedish_ci AFTER employ_from_date2, 
ADD COLUMN employer_name2 VARCHAR (150) NULL COLLATE latin1_swedish_ci AFTER employ_to_date2, 
ADD COLUMN designation_name2 VARCHAR (150) NULL COLLATE latin1_swedish_ci AFTER employer_name2,
ADD COLUMN employ_from_date3 VARCHAR (10) NULL COLLATE latin1_swedish_ci AFTER designation_name2,
ADD COLUMN employ_to_date3 VARCHAR (10) NULL COLLATE latin1_swedish_ci AFTER employ_from_date3,
ADD COLUMN employer_name3 VARCHAR (150) NULL COLLATE latin1_swedish_ci AFTER employ_to_date3,
ADD COLUMN designation_name3 VARCHAR (150) NULL COLLATE latin1_swedish_ci AFTER employer_name3,
ADD COLUMN employ_from_date4 VARCHAR (10) NULL COLLATE latin1_swedish_ci AFTER designation_name3,
ADD COLUMN employ_to_date4 VARCHAR (10) NULL COLLATE latin1_swedish_ci AFTER employ_from_date4,
ADD COLUMN employer_name4 VARCHAR (150) NULL COLLATE latin1_swedish_ci AFTER employ_to_date4,
ADD COLUMN designation_name4 VARCHAR (150) NULL COLLATE latin1_swedish_ci AFTER employer_name4,
ADD COLUMN employ_from_date5 VARCHAR (10) NULL COLLATE latin1_swedish_ci AFTER designation_name4,
ADD COLUMN employ_to_date5 VARCHAR (10) NULL COLLATE latin1_swedish_ci AFTER employ_from_date5,
ADD COLUMN employer_name5 VARCHAR (150) NULL COLLATE latin1_swedish_ci AFTER employ_to_date5,
ADD COLUMN designation_name5 VARCHAR (150) NULL COLLATE latin1_swedish_ci AFTER employer_name5;

DELETE FROM tp_screen_configurations WHERE field_id='input_salary_variable' AND field_type='0';


create temporary table if not exists tp_temp_2 as (
select * from tp_screen_configurations where field_rank > (SELECT field_rank FROM tp_screen_configurations WHERE field_id='level_offered')
);
update tp_screen_configurations set field_rank = field_rank+1 
where field_rank in (select field_rank from tp_temp_2);
drop table tp_temp_2; 

create temporary table if not exists tp_temp_3 as (select field_rank+1 FROM (SELECT field_rank FROM tp_screen_configurations WHERE field_id='level_offered') AS x);
INSERT INTO tp_screen_configurations 
	(field_id, field_type, is_process_field, field_rank, field_import_show, field_edit_show, 
	field_import_mandatory, field_confidential, field_vendor_show, field_vendor_mandatory, 
	field_employee_show, field_employee_mandatory, field_applicant_show_on_site, 
	field_applicant_mandatory_on_site) 
VALUES ('input_salary_variable','0','1',(select * from tp_temp_3),'0','0','0','0','0','0','0','0','0','0');
drop table tp_temp_3;