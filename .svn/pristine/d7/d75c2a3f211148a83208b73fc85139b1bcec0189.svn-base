USE talentpool;

ALTER TABLE tp_positions ADD COLUMN employee_apply_refer char (1)  DEFAULT '0' NOT NULL  COLLATE latin1_swedish_ci  after website_publish_to_date;

CREATE TABLE tp_business_unit (
                   bu_id int(11) NOT NULL auto_increment,
                   bu_name varchar(250) NOT NULL,
                   bu_desc varchar(250) NOT NULL,
		   		   bu_status char(1) NOT NULL default '1',
                   bu_parent_id int(10) default NULL,
                   PRIMARY KEY  (bu_id)
                 ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
                 
CREATE TABLE tp_cost_center (
                   cost_center_id int(11) NOT NULL auto_increment,
                   cost_center_name varchar(250) NOT NULL,
                   cost_center_desc varchar(250) NOT NULL,
		   		   cost_center_status char(1) NOT NULL default '1',
		   		   cost_center_parent_id int(10) default NULL,
                   PRIMARY KEY  (cost_center_id)
                 ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
                 
INSERT INTO tp_position_screen_description (field_id, field_type, field_rank, field_position_show, field_position_mandatory) VALUES('BusinessUnit','0','17','1','0');
INSERT INTO tp_position_screen_description (field_id, field_type, field_rank, field_position_show, field_position_mandatory) VALUES('CostCenter','0','18','1','0');

UPDATE tp_permissions
SET permission_rank = permission_rank + 1
WHERE permission_rank > 76;

INSERT INTO tp_permissions
(permission_id, permission_rank, permission_desc, parent_id, is_permission)
VALUES
(88, 77, 'Manage Business Unit', 52, 1);

INSERT INTO tp_role_permissions
(role_id, permission_id)
VALUES
(1,88);

INSERT INTO tp_user_permissions
(user_id, permission_id)
SELECT USER_ID, 88
FROM tp_user_roles
WHERE ROLE_ID IN (1);


UPDATE tp_permissions
SET permission_rank = permission_rank + 1
WHERE permission_rank > 77;

INSERT INTO tp_permissions
(permission_id, permission_rank, permission_desc, parent_id, is_permission)
VALUES
(89, 78, 'Manage Cost Center', 52, 1);

INSERT INTO tp_role_permissions
(role_id, permission_id)
VALUES
(1,89);

INSERT INTO tp_user_permissions
(user_id, permission_id)
SELECT USER_ID, 89
FROM tp_user_roles
WHERE ROLE_ID IN (1);

CREATE TABLE tp_position_business_unit (
                         position_id bigint(20) NOT NULL,                                                                               
                         bu_id int(11) NOT NULL,                                                                                  
                         KEY FK_tp_position_business_unit (bu_id),                                                                  
                         CONSTRAINT tp_position_business_unit_ibfk_1 FOREIGN KEY (bu_id) REFERENCES tp_business_unit (bu_id)
                       ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
                       
CREATE TABLE tp_position_cost_center (                                                                             
                         position_id bigint(20) NOT NULL,                                                                               
                         cost_center_id int(11) NOT NULL,                                                                                  
                         KEY FK_tp_position_cost_center (cost_center_id),                                                                  
                         CONSTRAINT tp_position_cost_center_ibfk_1 FOREIGN KEY (cost_center_id) REFERENCES tp_cost_center (cost_center_id)  
                       ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE tp_user_business_unit (
                       user_id bigint(20) default NULL,                                                                                   
                       bu_id int(11) default NULL,
                       KEY FK_tp_user_business_unit (user_id),
                       KEY FK_tp_user_business_unit_1 (bu_id),
                       CONSTRAINT tp_user_business_unit_ibfk_1 FOREIGN KEY (user_id) REFERENCES tp_users (user_id),
                       CONSTRAINT tp_user_business_unit_ibfk_2 FOREIGN KEY (bu_id) REFERENCES tp_business_unit (bu_id)
                     ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE tp_user_cost_center (
                       user_id bigint(20) default NULL,
                       cost_center_id int(11) default NULL,
                       KEY FK_tp_user_cost_center (user_id),
                       KEY FK_tp_user_cost_center_1 (cost_center_id),
                       CONSTRAINT tp_user_cost_center_ibfk_1 FOREIGN KEY (user_id) REFERENCES tp_users (user_id),
                       CONSTRAINT tp_user_cost_center_ibfk_2 FOREIGN KEY (cost_center_id) REFERENCES tp_cost_center (cost_center_id)
                     ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO tp_application_properties (application_property, application_property_value) VALUES ('business_unit_property', 0);
INSERT INTO tp_application_properties (application_property, application_property_value) VALUES ('business_unit_label', 'Business Unit');

INSERT INTO tp_application_properties (application_property, application_property_value) VALUES ('cost_center_property', 0);
INSERT INTO tp_application_properties (application_property, application_property_value) VALUES ('cost_center_label', 'Cost Center');

ALTER TABLE tp_positions ADD COLUMN type_of_vacancy CHAR (1)  DEFAULT '1' NOT NULL  COLLATE latin1_swedish_ci AFTER employee_apply_refer;
ALTER TABLE tp_positions ADD COLUMN replacement_emp_code VARCHAR (100) NULL COLLATE latin1_swedish_ci AFTER type_of_vacancy;

INSERT INTO tp_position_screen_description (field_id, field_type, field_rank, field_position_show, field_position_mandatory) VALUES('TypeOfVacancy','0','19','1','0');
INSERT INTO tp_position_screen_description (field_id, field_type, field_rank, field_position_show, field_position_mandatory) VALUES('ReplacementEmpCode','0','20','1','0');

INSERT INTO tp_application_properties (application_property, application_property_value) VALUES ('budget_for_replacement', 0);

ALTER TABLE tp_bulk_import_session_emails CHANGE entry_id entry_id VARCHAR(500);

ALTER TABLE tp_inbox_email_attachments CHANGE attachment_original_file_name attachment_original_file_name VARCHAR(500);
