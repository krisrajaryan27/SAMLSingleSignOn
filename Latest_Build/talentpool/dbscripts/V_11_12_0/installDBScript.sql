use talentpool;

ALTER TABLE tp_position_skills ADD COLUMN skill_category_id INT (11) NOT NULL AFTER position_id;

UPDATE tp_position_skills,tp_skills SET tp_position_skills.skill_category_id=tp_skills.skill_category_id
WHERE tp_position_skills.skill_id=tp_skills.skill_id;

ALTER TABLE tp_position_skills ADD FOREIGN KEY skill_category_id (skill_category_id) 
REFERENCES tp_skill_category (skill_category_id);

CREATE TABLE tp_position_skills_temp_1 SELECT * FROM tp_position_skills;
CREATE TABLE tp_position_skills_temp_2 SELECT * FROM tp_position_skills;

DELETE FROM tp_position_skills WHERE skill_type=1
AND position_id IN 
	(SELECT DISTINCT tps.position_id FROM tp_position_skills_temp_1 tps, tp_position_skills_temp_2 tpst
		WHERE tpst.skill_id=tps.skill_id AND tpst.position_id=tps.position_id AND tpst.skill_type!=tps.skill_type)
AND skill_id IN 
	(SELECT DISTINCT tps.skill_id FROM tp_position_skills_temp_1 tps, tp_position_skills_temp_2 tpst
		WHERE tpst.skill_id=tps.skill_id AND tpst.position_id=tps.position_id AND tpst.skill_type!=tps.skill_type);

DROP TABLE tp_position_skills_temp_1;
DROP TABLE tp_position_skills_temp_2;

ALTER TABLE tp_positions ADD COLUMN position_type_ext_int CHAR (1) DEFAULT '0' NOT NULL COLLATE latin1_swedish_ci AFTER replacement_emp_code;


DELETE FROM tp_position_screen_description WHERE field_id='PositionTypeExtInt';
UPDATE tp_position_screen_description SET field_rank=field_rank+1 WHERE field_rank>5;
INSERT INTO tp_position_screen_description 
	(field_id, field_type, field_rank, field_position_show, field_position_mandatory)
	VALUES('PositionTypeExtInt', 0, 6, 0, 0);

DELETE FROM tp_position_screen WHERE field_id='PositionTypeExtInt';
UPDATE tp_position_screen SET field_rank=field_rank+1 WHERE field_rank>7;
INSERT INTO tp_position_screen 
	(field_id, field_type, 	field_rank, field_position_print_show)
	VALUES('PositionTypeExtInt', 0, 8, 1);

INSERT INTO tp_application_properties (application_property, application_property_value) VALUES ('copy_position_with_code', 0);
INSERT INTO tp_application_properties (application_property, application_property_value) VALUES ('show_joined_candidate_in_search', 0);
INSERT INTO tp_application_properties (application_property, application_property_value) VALUES ('lock_period_for_joined_candidate_shortlist', '5');
INSERT INTO tp_application_properties (application_property, application_property_value) VALUES ('default_number_of_vacancy', '1');

INSERT INTO tp_report_levels (level_id,report_id,report_type)SELECT level_id,25,0 FROM tp_report_level_roles WHERE role_id=1;

UPDATE tp_permissions SET permission_rank = permission_rank + 1
WHERE permission_rank > 65;

INSERT INTO tp_permissions
(permission_id, permission_rank, permission_desc, parent_id, is_permission)
VALUES (93, 66, 'Position Type Decision Maker', 46, 1);

INSERT INTO tp_role_permissions (role_id, permission_id)
VALUES (1,93);

INSERT INTO tp_role_permissions (role_id, permission_id)
VALUES (2,93);

INSERT INTO tp_role_permissions (role_id, permission_id)
VALUES (4,93);

INSERT INTO tp_role_permissions (role_id, permission_id)
VALUES (6,93);

INSERT INTO tp_user_permissions (user_id, permission_id)
SELECT USER_ID, 93 FROM tp_user_roles WHERE ROLE_ID IN (1,2,4,6);