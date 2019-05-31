use talentpool;
INSERT INTO tp_cr_last_update (last_run_date) VALUES ( '2006-01-01 00:00:00');
UPDATE tp_custom_report_columns SET column_display_name='application:department_level_1' 		WHERE column_property='P11';
UPDATE tp_custom_report_columns SET column_display_name='application:department_level_2' 		WHERE column_property='P12';
UPDATE tp_custom_report_columns SET column_display_name='application:department_level_3' 		WHERE column_property='P13';
UPDATE tp_custom_report_columns SET column_display_name='application:department_level_4' 		WHERE column_property='P17';
UPDATE tp_custom_report_columns SET column_display_name='application:department_level_5' 		WHERE column_property='P18';
UPDATE tp_custom_report_columns SET column_display_name='application:budget_item_grade_label' 	WHERE column_property='P15';
UPDATE tp_custom_report_columns SET column_display_name='application:budget_item_band_label' 	WHERE column_property='P16';
UPDATE tp_custom_report_columns SET column_display_name='application:cost_center_label' 		WHERE column_property='P27';
UPDATE tp_custom_report_columns SET column_display_name='application:business_unit_label' 		WHERE column_property='P28';

UPDATE tp_custom_report_columns SET table_type='7' WHERE column_property='S01';
UPDATE tp_custom_report_columns SET table_type='5' WHERE column_property='S07';
UPDATE tp_custom_report_columns SET table_type='6' WHERE column_property='S10';
UPDATE tp_cr_table_type SET table_db_name='tp_cr_position_hiring_summary', table_type_name='Position Hiring Summary' WHERE table_type_id='3';
INSERT INTO tp_cr_table_type(table_type_id,table_type_name,table_short_name,table_db_name,table_column_join) values (9,'User Hiring Activity','tres','tp_cr_user_hiring_activity_summary','position_id');

SET FOREIGN_KEY_CHECKS = 0;
 
INSERT INTO tp_custom_report_columns(column_property,column_display_name,column_category,column_data_type,column_dbname,column_width,group_by_column,order_by_column,is_custom_field,is_active,table_type,value_type) VALUES ('S191','User Process Date String Format','3','java.lang.String','DATE(${S19})',75,'S19','S19','0','0',9,'1');
INSERT INTO tp_custom_report_columns(column_property,column_display_name,column_category,column_data_type,column_dbname,column_width,group_by_column,order_by_column,is_custom_field,is_active,table_type,value_type) VALUES ('S19','User Process Date','3','com.talentPool.customReports.djhelper.wrappers.JasperDateWrapper','process_date',75,'S191','S19','0','0',9,'1');
INSERT INTO tp_custom_report_columns(column_property,column_display_name,column_category,column_data_type,column_dbname,column_width,group_by_column,order_by_column,is_custom_field,is_active,table_type,value_type) VALUES ('S20','User Process Month Sequence','3','java.lang.Integer','date_format(${S19}, ''%y%m'')',75,null,null,'0','0',9,'1');
INSERT INTO tp_custom_report_columns(column_property,column_display_name,column_category,column_data_type,column_dbname,column_width,group_by_column,order_by_column,is_custom_field,is_active,table_type,value_type) VALUES ('S21','User Process Month','3','com.talentPool.customReports.djhelper.wrappers.JasperMonthWrapper','date_format(${S19}, "%b-%y")',75,'S21','S20','0','0',9,'1');
INSERT INTO tp_custom_report_columns(column_property,column_display_name,column_category,column_data_type,column_dbname,column_width,group_by_column,order_by_column,is_custom_field,is_active,table_type,value_type) VALUES ('S23','ADDED','3','java.lang.Integer','received',75,null,null,'0','0',9,'2');
INSERT INTO tp_custom_report_columns(column_property,column_display_name,column_category,column_data_type,column_dbname,column_width,group_by_column,order_by_column,is_custom_field,is_active,table_type,value_type) VALUES ('S24','SELECTED','3','java.lang.Integer','cleared',75,null,null,'0','0',9,'2');
INSERT INTO tp_custom_report_columns(column_property,column_display_name,column_category,column_data_type,column_dbname,column_width,group_by_column,order_by_column,is_custom_field,is_active,table_type,value_type) VALUES ('S25','REJECTED','3','java.lang.Integer','rejected',75,null,null,'0','0',9,'2');
INSERT INTO tp_custom_report_columns(column_property,column_display_name,column_category,column_data_type,column_dbname,column_width,group_by_column,order_by_column,is_custom_field,is_active,table_type,value_type) VALUES ('S26','Status as of Date','3','java.lang.Integer','${S23}',72,null,null,'0','0',9,'2');
INSERT INTO tp_custom_report_columns(column_property,column_display_name,column_category,column_data_type,column_dbname,column_width,group_by_column,order_by_column,is_custom_field,is_active,table_type,value_type) VALUES ('S27','User Process Week Sequence','3','java.lang.Integer','date_format(${S19}, ''%y%U'')',75,null,null,'0','0',9,'1');
INSERT INTO tp_custom_report_columns(column_property,column_display_name,column_category,column_data_type,column_dbname,column_width,group_by_column,order_by_column,is_custom_field,is_active,table_type,value_type) VALUES ('S22','User Process Week','3','com.talentPool.customReports.djhelper.wrappers.JasperWeekWrapper','concat(DATE_FORMAT(SUBDATE(${S19},(dayofweek(${S19})-1)),"%d-%b-%y")," to ",DATE_FORMAT(ADDDATE(${S19}, (7-dayofweek(${S19}))),"%d-%b-%y"))',130,null,'S27','0','0',9,'1');
 
SET FOREIGN_KEY_CHECKS = 1;

ALTER TABLE tp_applicant_selection_process_traits ADD COLUMN feedback_form_id int (11) NOT NULL AFTER select_field_id;
UPDATE tp_applicant_selection_process_traits taspt LEFT JOIN tp_feedback_form_fields tfff ON (tfff.feedback_form_field_id=taspt.feedback_form_field_id) SET taspt.feedback_form_id=(tfff.feedback_form_id);
ALTER TABLE tp_applicant_selection_process_traits ADD FOREIGN KEY FK_tp_applicant_selection_process_traits_4(feedback_form_id) references tp_feedback_forms (feedback_form_id);

UPDATE tp_permissions SET permission_rank = permission_rank + 1 WHERE permission_rank >= 68;
INSERT INTO tp_permissions (permission_id, permission_rank, permission_desc, parent_id, is_permission) VALUES (97, 68, 'View Rejected Candidates', 46, 1);
INSERT INTO tp_role_permissions (role_id, permission_id) VALUES (1,97);
INSERT INTO tp_role_permissions (role_id, permission_id) VALUES (3,97);
INSERT INTO tp_role_permissions (role_id, permission_id) VALUES (4,97);
INSERT INTO tp_role_permissions (role_id, permission_id) VALUES (6,97);
INSERT INTO tp_user_permissions (user_id, permission_id) SELECT USER_ID, 97 FROM tp_user_roles WHERE ROLE_ID IN (1,3,4,6);

-- Rejected Candidates Report 
INSERT INTO tp_report_levels (level_id,report_id,report_type)
SELECT level_id,26,0 FROM tp_report_level_roles WHERE role_id=1;

INSERT INTO tp_salary_component_categories (category_id, category_name) VALUES ('3','Others');
UPDATE tp_salary_components SET salary_component_category_id = 3 
WHERE (salary_component_category_id = 0 OR salary_component_category_id IS NULL);

ALTER TABLE tp_salary_components ADD CONSTRAINT 
FK_tp_salary_components FOREIGN KEY (salary_component_category_id) REFERENCES tp_salary_component_categories (category_id) ON DELETE RESTRICT;

