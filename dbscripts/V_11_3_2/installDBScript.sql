use talentpool;

DELETE FROM tp_recent_searches;

alter table `tp_users` 
add column `sub3_dept_id` int (10)   NULL  after `sub_sub_dept_id`, 
add column `sub4_dept_id` int (10)   NULL  after `sub3_dept_id`, 
add column `grade_id` int (11)   NULL  after `sub4_dept_id`, 
add column `band_id` int (11)   NULL  after `grade_id`;

alter table `tp_users` add foreign key `FK_tp_users_3`(`dept_id`) references `tp_departments` (`dept_id`);
alter table `tp_users` add foreign key `FK_tp_users_4`(`sub_sub_dept_id`) references `tp_departments` (`dept_id`);
alter table `tp_users` add foreign key `FK_tp_users_5`(`sub3_dept_id`) references `tp_departments` (`dept_id`);
alter table `tp_users` add foreign key `FK_tp_users_6`(`sub4_dept_id`) references `tp_departments` (`dept_id`);
alter table `tp_users` add foreign key `FK_tp_users_7`(`grade_id`) references `tp_budget_grades` (`grade_id`);
alter table `tp_users` add foreign key `FK_tp_users_8`(`band_id`) references `tp_budget_bands` (`band_id`);

alter table `tp_budget_grades` add column `grade_rank` int (11)   NOT NULL  after `grade_desc`;
alter table `tp_budget_grades` change `grade_rank` `grade_rank` int (11)  DEFAULT '0' NOT NULL;

delete from tp_application_properties where application_property='department_level_4';
insert into tp_application_properties values ('department_level_4','Sub Group');
delete from tp_application_properties where application_property='department_level_5';
insert into tp_application_properties values ('department_level_5','Team');
delete from tp_application_properties where application_property='max_dept_level';
insert into tp_application_properties values ('max_dept_level','3');
delete from tp_application_properties where application_property='position_display_in_grid_template';
insert into `tp_application_properties` (`application_property`,`application_property_value`) values ( 'position_display_in_grid_template','n [c]');

alter table `tp_positions` add column `sub3_dept_id` int (10)   NULL  after `sub_sub_dept_id`, add column `sub4_dept_id` int (10)   NULL  after `sub3_dept_id`;
alter table `tp_positions` add foreign key `FK_tp_positions_8`(`sub3_dept_id`) references `tp_departments` (`dept_id`);
alter table `tp_positions` add foreign key `FK_tp_positions_9`(`sub4_dept_id`) references `tp_departments` (`dept_id`);

alter table `tp_salary_formulae` 
change `variable1_factor` `variable1_factor` decimal(10,5) NOT NULL, 
change `constant_factor` `constant_factor` decimal(10,5) DEFAULT '0' NOT NULL;

UPDATE tp_position_screen SET field_rank = field_rank + 2
WHERE field_rank > 7;

INSERT INTO tp_position_screen
(field_id, field_type, field_rank,field_position_print_show)
VALUES ('DepartmentLevel4', 0, 8, 1);

INSERT INTO tp_position_screen
(field_id, field_type, field_rank,field_position_print_show)
VALUES ('DepartmentLevel5', 0, 9, 1);

alter table `tp_budget_items` 
add column `sub3_dept_id` int (10)   NULL  after `sub_sub_dept_id`, 
add column `sub4_dept_id` int (10)   NULL  after `sub3_dept_id`;