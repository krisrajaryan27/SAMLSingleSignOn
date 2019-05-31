use talentpool;

INSERT INTO tp_custom_report_columns 
	(column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, 
	order_by_column, is_custom_field, is_active, table_type, value_type) 
	VALUES ('P30','Date Closed','2','java.util.Date','position_date_closed','75',NULL,NULL,'0','1','2','1');
	
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (1,'P30');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (2,'P30');

INSERT INTO tp_cr_last_update (last_run_date) VALUES ( '2012-04-01 00:00:00');
