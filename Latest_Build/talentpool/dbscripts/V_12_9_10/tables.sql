use talentpool;

CREATE TABLE tp_customization_reports( 
	report_id SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT , 
	report_name VARCHAR(50) NOT NULL , 
	report_label VARCHAR(50) NOT NULL , 
	report_jrxml VARCHAR(100) NOT NULL , 
	report_jsp VARCHAR(50) NOT NULL , 
	PRIMARY KEY (report_id))  ;
	
ALTER TABLE tp_report_scheduler CHANGE report_id report_id VARCHAR(50) NULL ;	

call PROC_DROP_FOREIGN_KEY_IF_EXISTS('tp_report_scheduler','tp_report_scheduler_ibfk_3');
call PROC_DROP_FOREIGN_KEY_IF_EXISTS('tp_report_scheduler','tp_report_scheduler_ibfk_2');

ALTER TABLE tp_report_scheduler CHANGE department_id department_id VARCHAR(255) NULL;
ALTER TABLE tp_report_scheduler CHANGE position_id position_id VARCHAR(255) NULL;