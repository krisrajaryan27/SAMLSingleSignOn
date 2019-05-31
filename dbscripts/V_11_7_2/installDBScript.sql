use talentpool;

DELETE FROM tp_template_generic_vars WHERE template_variable_type='3' AND template_variable='LINK_TO_CANDIDATE_PROFILE';
INSERT INTO tp_template_generic_vars (template_variable_type, template_variable) VALUES ('3', 'LINK_TO_CANDIDATE_PROFILE');

DELETE FROM tp_template_generic_vars WHERE template_variable_type='3' AND template_variable='EXTERNAL_LINK_TO_CANDIDATE_PROFILE';
INSERT INTO tp_template_generic_vars (template_variable_type, template_variable) VALUES ('3', 'EXTERNAL_LINK_TO_CANDIDATE_PROFILE');


UPDATE tp_permissions SET permission_rank = permission_rank + 1
WHERE permission_rank > 44;

INSERT INTO tp_permissions
(permission_id, permission_rank, permission_desc, parent_id, is_permission)
VALUES (90, 45, 'Selection Process', 0, 0);

UPDATE tp_permissions SET permission_rank = permission_rank + 1
WHERE permission_rank > 45;

INSERT INTO tp_permissions
(permission_id, permission_rank, permission_desc, parent_id, is_permission)
VALUES (91, 46, 'Move to other position', 90, 1);

INSERT INTO tp_role_permissions (role_id, permission_id)
VALUES (1,91);

INSERT INTO tp_role_permissions (role_id, permission_id)
VALUES (4,91);

INSERT INTO tp_role_permissions (role_id, permission_id)
VALUES (6,91);

INSERT INTO tp_user_permissions (user_id, permission_id)
SELECT USER_ID, 91 FROM tp_user_roles WHERE ROLE_ID IN (1,4,6);

DELETE FROM tp_application_properties WHERE application_property='is_employee_code_mandatory';
INSERT INTO tp_application_properties (application_property, application_property_value)
	VALUES ('is_employee_code_mandatory', 1);
	
ALTER TABLE tp_positions ADD COLUMN 
position_owner_id bigint(20) COMMENT 'By default created by user will be position owner' AFTER position_created_by;
ALTER TABLE tp_positions ADD CONSTRAINT FK_position_owner FOREIGN KEY (position_owner_id) REFERENCES tp_users (USER_ID);

UPDATE tp_permissions SET permission_rank = permission_rank + 1
WHERE permission_rank > 64;

INSERT INTO tp_permissions
(permission_id, permission_rank, permission_desc, parent_id, is_permission)
VALUES (92, 65, 'Position Owner', 46, 1);

INSERT INTO tp_role_permissions (role_id, permission_id)
VALUES (1,92);

INSERT INTO tp_role_permissions (role_id, permission_id)
VALUES (3,92);

INSERT INTO tp_role_permissions (role_id, permission_id)
VALUES (4,92);

INSERT INTO tp_user_permissions (user_id, permission_id)
SELECT USER_ID, 92 FROM tp_user_roles WHERE ROLE_ID IN (1,3,4);

ALTER TABLE tp_position_screen 
DROP COLUMN field_position_description_show, 
DROP COLUMN field_position_mandatory;

DELETE FROM tp_position_screen WHERE field_id='position_owner';
UPDATE tp_position_screen SET field_rank=field_rank+1 WHERE field_rank>2;
INSERT INTO tp_position_screen 
	(field_id, field_type, 	field_rank, field_position_print_show)
VALUES
	('position_owner', 0, 3, 1);

DELETE FROM tp_position_screen_description WHERE field_id='position_owner';
UPDATE tp_position_screen_description SET field_rank=field_rank+1 WHERE field_rank>2;
INSERT INTO tp_position_screen_description 
	(field_id, field_type, field_rank, field_position_show, field_position_mandatory)
	values
	('position_owner', 0, 3, 1, 1);
