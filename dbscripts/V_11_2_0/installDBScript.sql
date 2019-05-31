use talentpool;

DELETE FROM tp_recent_searches;

UPDATE tp_permissions SET permission_rank = permission_rank + 1
WHERE permission_rank > 60;

INSERT INTO tp_permissions
(permission_id, permission_rank, permission_desc, parent_id, is_permission)
VALUES (86, 61, 'Add/Edit Position Template', 46, 1);

INSERT INTO tp_role_permissions (role_id, permission_id)
VALUES (1,86);

INSERT INTO tp_user_permissions (user_id, permission_id)
SELECT USER_ID, 86 FROM tp_user_roles WHERE ROLE_ID IN (1);

delete from tp_application_properties where application_property='export_joined_applicants_data';
insert into tp_application_properties (application_property, application_property_value)
	values ('export_joined_applicants_data', 0);
	
alter table `tp_applicants` 
add column `applicant_status` char(1) NOT NULL default 0 after `applicant_joined`;

CREATE TABLE `tp_applicant_blacklist_history` (
	`interaction_id` bigint(20) auto_increment NOT NULL,  
	`applicant_id` bigint(20) NOT NULL,  
	`reason` text NOT NULL,
	`type` int(10) NOT NULL,
	`user_id` bigint(20) NOT NULL,
	`date_created` datetime NOT NULL,
	PRIMARY KEY  (`interaction_id`),
 	KEY `user_id` (`user_id`),
 	KEY `applicant_id` (`applicant_id`),
 	CONSTRAINT `tp_applicant_blacklist_history_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `tp_users` (`user_id`),
	CONSTRAINT `tp_applicant_blacklist_history_ibfk_2` FOREIGN KEY (`applicant_id`) REFERENCES `tp_applicants` (`applicant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

ALTER TABLE tp_sources 
ADD COLUMN source_blacklisted char(1) NOT NULL default 0;

UPDATE tp_permissions SET permission_rank = permission_rank + 1
WHERE permission_rank > 43;

INSERT INTO tp_permissions
(permission_id, permission_rank, permission_desc, parent_id, is_permission)
VALUES (87, 44, 'Blacklist Candidate', 32, 1);

INSERT INTO tp_role_permissions (role_id, permission_id)
VALUES (1,87);

INSERT INTO tp_user_permissions (user_id, permission_id)
SELECT USER_ID, 87 FROM tp_user_roles WHERE ROLE_ID IN (1);

INSERT INTO tp_role_permissions (role_id, permission_id)
VALUES (6,87);

INSERT INTO tp_user_permissions (user_id, permission_id)
SELECT USER_ID, 87 FROM tp_user_roles WHERE ROLE_ID IN (6);

INSERT INTO tp_role_permissions (role_id, permission_id)
VALUES (4,87);

INSERT INTO tp_user_permissions (user_id, permission_id)
SELECT USER_ID, 87 FROM tp_user_roles WHERE ROLE_ID IN (4);

delete from tp_application_properties where application_property='enable_org_hierarchy_for_visibility';
insert into tp_application_properties (application_property, application_property_value)
values ('enable_org_hierarchy_for_visibility', 0);