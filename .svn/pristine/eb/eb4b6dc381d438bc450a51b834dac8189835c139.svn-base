use talentpool;

INSERT INTO tp_cr_last_update (last_run_date) VALUES ( '2006-01-01 00:00:00');

DELETE FROM tp_custom_reports;

INSERT INTO `tp_custom_reports` (`report_id`,`report_name`,`report_desc`,`created_by`,`date_created`,`modified_by`,`date_modified`,`xml_file_path`,`template_file_path`,`query_file_path`) VALUES (201,'Hiring Status Report','Hiring Status Report','1',now(),'1',now(),'CR_1/CR_1.xml','CR_1/CR_1.jrxml','CR_1/CR_1.sql');
INSERT INTO `tp_custom_reports` (`report_id`,`report_name`,`report_desc`,`created_by`,`date_created`,`modified_by`,`date_modified`,`xml_file_path`,`template_file_path`,`query_file_path`) VALUES (202,'User Activity Report','User Activity Report','1',now(),'1',now(),'CR_2/CR_2.xml','CR_2/CR_2.jrxml','CR_2/CR_2.sql');
INSERT INTO `tp_custom_reports` (`report_id`,`report_name`,`report_desc`,`created_by`,`date_created`,`modified_by`,`date_modified`,`xml_file_path`,`template_file_path`,`query_file_path`) VALUES (203,'Hiring Activity Report','Hiring Activity Report','1',now(),'1',now(),'CR_3/CR_3.xml','CR_3/CR_3.jrxml','CR_3/CR_3.sql');

ALTER TABLE `tp_custom_reports` change `report_id` `report_id` bigint(20) NOT NULL AUTO_INCREMENT;

-- DELETE FROM tp_permissions where permission_desc = 'Add New Custom Report';

INSERT INTO tp_permissions (permission_id, permission_rank, permission_desc, parent_id, is_permission)
VALUES (94, 92, 'Add/Modify Summary Reports', 0, 1);

INSERT INTO tp_role_permissions (role_id, permission_id)
VALUES (1,94);

INSERT INTO tp_user_permissions (user_id, permission_id)
SELECT USER_ID, 94 FROM tp_user_roles WHERE ROLE_ID = 1;

UPDATE tp_custom_report_columns SET column_data_type = 'java.lang.String' 
WHERE column_data_type = 'java.util.String';


INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('PCF1','Custom Field 1()','2','','custom_field_1','100',NULL,NULL,'1','0','2','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('PCF2','Custom Field 2()','2','','custom_field_2','100',NULL,NULL,'1','0','2','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('PCF3','Custom Field 3()','2','','custom_field_3','100',NULL,NULL,'1','0','2','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('PCF4','Custom Field 4()','2','','custom_field_4','100',NULL,NULL,'1','0','2','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('PCF5','Custom Field 5()','2','','custom_field_5','100',NULL,NULL,'1','0','2','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('PCF6','Custom Field 6()','2','','custom_field_6','100',NULL,NULL,'1','0','2','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('PCF7','Custom Field 7()','2','','custom_field_7','100',NULL,NULL,'1','0','2','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('PCF8','Custom Field 8()','2','','custom_field_8','100',NULL,NULL,'1','0','2','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('PCF9','Custom Field 9()','2','','custom_field_9','100',NULL,NULL,'1','0','2','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('PCF10','Custom Field 10()','2','','custom_field_10','100',NULL,NULL,'1','0','2','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('PCF11','Custom Field 11()','2','','custom_field_11','100',NULL,NULL,'1','0','2','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('PCF12','Custom Field 12()','2','','custom_field_12','100',NULL,NULL,'1','0','2','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('PCF13','Custom Field 13()','2','','custom_field_13','100',NULL,NULL,'1','0','2','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('PCF14','Custom Field 14()','2','','custom_field_14','100',NULL,NULL,'1','0','2','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('PCF15','Custom Field 15()','2','','custom_field_15','100',NULL,NULL,'1','0','2','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('PCF16','Custom Field 16()','2','','custom_field_16','100',NULL,NULL,'1','0','2','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('PCF17','Custom Field 17()','2','','custom_field_17','100',NULL,NULL,'1','0','2','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('PCF18','Custom Field 18()','2','','custom_field_18','100',NULL,NULL,'1','0','2','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('PCF19','Custom Field 19()','2','','custom_field_19','100',NULL,NULL,'1','0','2','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('PCF20','Custom Field 20()','2','','custom_field_20','100',NULL,NULL,'1','0','2','1');

INSERT INTO tp_cr_column_customfield_map (column_property, column_display_name, entity_type) VALUES ('PCF1', 'Custom Field 1', '2');
INSERT INTO tp_cr_column_customfield_map (column_property, column_display_name, entity_type) VALUES ('PCF2', 'Custom Field 2', '2');
INSERT INTO tp_cr_column_customfield_map (column_property, column_display_name, entity_type) VALUES ('PCF3', 'Custom Field 3', '2');
INSERT INTO tp_cr_column_customfield_map (column_property, column_display_name, entity_type) VALUES ('PCF4', 'Custom Field 4', '2');
INSERT INTO tp_cr_column_customfield_map (column_property, column_display_name, entity_type) VALUES ('PCF5', 'Custom Field 5', '2');
INSERT INTO tp_cr_column_customfield_map (column_property, column_display_name, entity_type) VALUES ('PCF6', 'Custom Field 6', '2');
INSERT INTO tp_cr_column_customfield_map (column_property, column_display_name, entity_type) VALUES ('PCF7', 'Custom Field 7', '2');
INSERT INTO tp_cr_column_customfield_map (column_property, column_display_name, entity_type) VALUES ('PCF8', 'Custom Field 8', '2');
INSERT INTO tp_cr_column_customfield_map (column_property, column_display_name, entity_type) VALUES ('PCF9', 'Custom Field 9', '2');
INSERT INTO tp_cr_column_customfield_map (column_property, column_display_name, entity_type) VALUES ('PCF10', 'Custom Field 10', '2');
INSERT INTO tp_cr_column_customfield_map (column_property, column_display_name, entity_type) VALUES ('PCF11', 'Custom Field 11', '2');
INSERT INTO tp_cr_column_customfield_map (column_property, column_display_name, entity_type) VALUES ('PCF12', 'Custom Field 12', '2');
INSERT INTO tp_cr_column_customfield_map (column_property, column_display_name, entity_type) VALUES ('PCF13', 'Custom Field 13', '2');
INSERT INTO tp_cr_column_customfield_map (column_property, column_display_name, entity_type) VALUES ('PCF14', 'Custom Field 14', '2');
INSERT INTO tp_cr_column_customfield_map (column_property, column_display_name, entity_type) VALUES ('PCF15', 'Custom Field 15', '2');
INSERT INTO tp_cr_column_customfield_map (column_property, column_display_name, entity_type) VALUES ('PCF16', 'Custom Field 16', '2');
INSERT INTO tp_cr_column_customfield_map (column_property, column_display_name, entity_type) VALUES ('PCF17', 'Custom Field 17', '2');
INSERT INTO tp_cr_column_customfield_map (column_property, column_display_name, entity_type) VALUES ('PCF18', 'Custom Field 18', '2');
INSERT INTO tp_cr_column_customfield_map (column_property, column_display_name, entity_type) VALUES ('PCF19', 'Custom Field 19', '2');
INSERT INTO tp_cr_column_customfield_map (column_property, column_display_name, entity_type) VALUES ('PCF20', 'Custom Field 20', '2');

INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('C32','Applicant Step Title','1','java.lang.String','position_step_title','75',NULL,NULL,'0','1','1','1');

INSERT INTO tp_cr_report_types (cr_report_type_id,cr_report_type_name,cr_report_type_kind_id) VALUES(1,'Hiring Activity Report','1');
INSERT INTO tp_cr_report_types (cr_report_type_id,cr_report_type_name,cr_report_type_kind_id) VALUES(2,'Hiring Status Report','1');
INSERT INTO tp_cr_report_types (cr_report_type_id,cr_report_type_name,cr_report_type_kind_id) VALUES(3,'User Activity Report','2');


INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (1,'P29');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (1,'P01');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (1,'P02');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (1,'P03');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (1,'P04');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (1,'P05');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (1,'P06');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (1,'P07');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (1,'P08');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (1,'P09');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (1,'P10');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (1,'P11');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (1,'P12');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (1,'P13');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (1,'P14');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (1,'P15');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (1,'P16');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (1,'P17');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (1,'P18');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (1,'P19');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (1,'P20');   
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (1,'P21');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (1,'P22');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (1,'P23');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (1,'P24');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (1,'P25');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (1,'P26');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (1,'P27');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (1,'P28');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (1,'S01');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (1,'S02');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (1,'S03');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (1,'S031');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (1,'S04');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (1,'S05');   
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (1,'S06');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (1,'S07');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (1,'S08');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (1,'S09');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (1,'S10');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (1,'S11');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (1,'S12');   
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (1,'S13');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (1,'S14');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (1,'S15');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (1,'S16');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (2,'P29');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (2,'P01');  
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (2,'P02');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (2,'P03');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (2,'P04');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (2,'P05');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (2,'P06');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (2,'P07');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (2,'P08');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (2,'P09');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (2,'P10');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (2,'P11');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (2,'P12');   
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (2,'P13');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (2,'P14');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (2,'P15');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (2,'P16');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (2,'P17');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (2,'P18');   
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (2,'P19');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (2,'P20');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (2,'P21');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (2,'P22');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (2,'P23');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (2,'P24');   
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (2,'P25');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (2,'P26');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (2,'P27');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (2,'P28');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (2,'S01');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (2,'S02');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (2,'S03');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (2,'S031');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (2,'S04');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (2,'S05');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (2,'S06');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (2,'S07');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (2,'S08');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (2,'S09');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (2,'S10');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (2,'S11');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (2,'S17');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (3,'A01');   
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (3,'A02');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (3,'A03');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (3,'A031');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (3,'A04');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (3,'A05');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (3,'A06');   
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (3,'A07');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (3,'A08');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (3,'A09');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (3,'A10');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (3,'A11');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (3,'A12');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (3,'A13');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (3,'A14');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (3,'A15');


INSERT INTO tp_cr_scheduler (scheduler_name, scheduler_start_hour, scheduler_frequency) 
VALUES ('Master Tables Scheduler',CONCAT(CURDATE(), ' 22:00:00'), 24);

UPDATE tp_permissions SET permission_rank = permission_rank + 1 WHERE permission_rank >= 21;
INSERT INTO tp_permissions (permission_id, permission_rank, permission_desc, parent_id, is_permission) VALUES (95, 21, 'Summary Report Scheduler', 10, 1);
INSERT INTO tp_role_permissions (role_id, permission_id) VALUES (1,95);
INSERT INTO tp_user_permissions (user_id, permission_id) SELECT USER_ID, 95 FROM tp_user_roles WHERE ROLE_ID = 1;

ALTER TABLE tp_custom_reports ADD COLUMN cr_report_type_id BIGINT (11) NOT NULL AFTER query_file_path;
UPDATE tp_custom_reports SET cr_report_type_id=2 WHERE report_id=201;
UPDATE tp_custom_reports SET cr_report_type_id=3 WHERE report_id=202;
UPDATE tp_custom_reports SET cr_report_type_id=1 WHERE report_id=203;
ALTER TABLE tp_custom_reports ADD FOREIGN KEY FK_tp_custom_reports(cr_report_type_id) REFERENCES tp_cr_report_types (cr_report_type_id);

UPDATE tp_custom_report_columns SET column_dbname='date_format(${S03}, \'%y%m\')' WHERE column_property='S04';
UPDATE tp_custom_report_columns SET column_data_type='com.talentPool.customReports.djhelper.wrappers.JasperMonthWrapper', column_dbname='date_format(${S03}, "%b-%y")' WHERE column_property='S05';
UPDATE tp_custom_report_columns SET column_data_type='com.talentPool.customReports.djhelper.wrappers.JasperDateWrapper', group_by_column='S031' WHERE column_property='S03';
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('S18','Process Week Sequence','3','java.lang.Integer','date_format(${S03}, \'%y%U\')','75',NULL,NULL,'0','0','3','1');
UPDATE tp_custom_report_columns SET 
column_data_type='com.talentPool.customReports.djhelper.wrappers.JasperWeekWrapper', 
column_dbname='concat(DATE_FORMAT(SUBDATE(${S03},(dayofweek(${S03})-1)),"%d-%b-%y")," to ",DATE_FORMAT(ADDDATE(${S03}, (7-dayofweek(${S03}))),"%d-%b-%y"))', 
column_width='130',
order_by_column='S18' WHERE column_property='S06';

UPDATE tp_custom_report_columns SET is_active='1', value_type='4' WHERE column_property='S15';

UPDATE tp_custom_report_columns SET table_type='4', group_by_column='A04', order_by_column='A03', 
column_dbname='concat(DATE_FORMAT(SUBDATE(${A031},(dayofweek(${A031})-1)),"%d-%b-%y")," to ",DATE_FORMAT(ADDDATE(${A031}, (7-dayofweek(${A031}))),"%d-%b-%y"))'
where column_property='A04';

DELETE FROM tp_user_permissions WHERE permission_id = 75;
DELETE FROM tp_role_permissions WHERE permission_id = 75;
UPDATE tp_permissions SET permission_rank = permission_rank-1 WHERE permission_rank > 87;
DELETE FROM tp_permissions WHERE permission_id = 75;

UPDATE tp_custom_report_columns SET column_dbname='DATE(${S03})' WHERE column_property='S031';
UPDATE tp_custom_Report_columns SET column_data_type='java.lang.Integer' WHERE column_property='P03';


INSERT INTO tp_custom_report_columns 
(column_property, column_display_name, column_category, column_data_type, column_dbname, 
	column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES
('U01', 'User First Name', '5', 'java.lang.String', 'user_fname', '75', NULL, NULL, 0, 0, 7, 1);
INSERT INTO tp_custom_report_columns 
(column_property, column_display_name, column_category, column_data_type, column_dbname, 
	column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES
('U02', 'User Last Name', '5', 'java.lang.String', 'user_lname', '75', NULL, NULL, 0, 0, 7, 1);

UPDATE tp_custom_Report_columns SET column_dbname='CONCAT(${U01}," ",${U02})', table_type='7' WHERE column_property='A02';
UPDATE tp_custom_Report_columns SET column_dbname='CONCAT(${U01}," ",${U02})' WHERE column_property='S02';
UPDATE tp_custom_Report_columns SET column_width='150' WHERE column_property='P05';
