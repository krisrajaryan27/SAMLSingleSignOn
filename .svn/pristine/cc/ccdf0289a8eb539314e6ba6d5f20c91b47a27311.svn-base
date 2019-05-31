use talentpool;

UPDATE tp_positions SET position_note=left(position_note, 1000);
ALTER TABLE tp_positions change position_note position_note VARCHAR (1000) NULL COLLATE latin1_swedish_ci;

alter table tp_custom_fields add column custom_field_report char (1)  DEFAULT '0' NOT NULL  after custom_field_entity_type;

alter table tp_position_steps add column step_id tinyint (4)   NULL  after position_id;
alter table tp_position_steps add foreign key FK_tp_position_steps_3(step_id) references tp_step_master (step_id);

ALTER TABLE tp_date_time_patterns ADD COLUMN db_pattern_value VARCHAR(50) CHARSET latin1 COLLATE latin1_swedish_ci NOT NULL AFTER pattern_value;

UPDATE tp_date_time_patterns SET db_pattern_value='%d-%b-%y' WHERE pattern_id='1';
UPDATE tp_date_time_patterns SET db_pattern_value='%d-%b-%Y' WHERE pattern_id='2';
UPDATE tp_date_time_patterns SET db_pattern_value='%d/%b/%y' WHERE pattern_id='3';
UPDATE tp_date_time_patterns SET db_pattern_value='%d-%c-%y' where pattern_id='4';
UPDATE tp_date_time_patterns SET db_pattern_value='%d-%c-%Y' where pattern_id='5';
UPDATE tp_date_time_patterns SET db_pattern_value='%d/%c/%y' where pattern_id='6';
UPDATE tp_date_time_patterns SET db_pattern_value='%W, %d-%b-%y' where pattern_id='7';
UPDATE tp_date_time_patterns SET db_pattern_value='%l:%i %p' where pattern_id='8';
UPDATE tp_date_time_patterns SET db_pattern_value='%k:%i' where pattern_id='9';

INSERT INTO `tp_step_level` (`step_level`, `step_level_name`, `modified_date`, `modified_by`) values('0','Shortlist',now(),'1');
INSERT INTO `tp_step_level` (`step_level`, `step_level_name`, `modified_date`, `modified_by`) values('1','Select',now(),'1');
INSERT INTO `tp_step_level` (`step_level`, `step_level_name`, `modified_date`, `modified_by`) values('2','Hire',now(),'1');

INSERT INTO tp_step_master (step_id,step_name,step_desc,step_rank,system_step,step_level,step_schedulable,step_disabled,step_deleted,user_id,date_modified,date_created) values ( '1','Shortlist','Default First Step','1','1','0','0','0','0','1',now(),now());
INSERT INTO tp_step_master (step_id,step_name,step_desc,step_rank,system_step,step_level,step_schedulable,step_disabled,step_deleted,user_id,date_modified,date_created) values ( '2','Joined & Closed','Default Last Step','2','1','2','0','0','0','1',now(),now());

UPDATE tp_position_steps SET step_id=1 WHERE position_step_rank=1 AND position_step_level=0 AND position_step_isdefault=1 AND position_step_status=1;

UPDATE tp_position_steps tps JOIN 
(SELECT  main.position_step_id , 
	@row_num:=IF(@prev_value=main.position_id,@row_num+1,1) AS new_rank,
	@prev_value := main.position_id
FROM (
	SELECT tps.position_id, tps.position_step_id, tps.position_step_rank FROM tp_position_steps tps
	JOIN (
		SELECT tps.position_id FROM tp_position_steps tps, tp_positions tp
		WHERE tps.position_id=tp.position_id AND tps.position_step_status=1 AND tp.position_status IN (1,2,3,5,6)
		GROUP BY tps.position_id HAVING 
		count(distinct(tps.position_step_rank))!=count(tps.position_step_id)) A 
	ON (A.position_id=tps.position_id)
	WHERE tps.position_step_status=1
	ORDER BY position_id, position_step_rank, position_step_level, position_step_id
) main, (SELECT @row_num:=0) X, (SELECT @prev_value := '') Y
) update_main ON (tps.position_step_id=update_main.position_step_id)
SET tps.position_step_rank=update_main.new_rank;


INSERT INTO tp_migration_status (position_id)
SELECT DISTINCT tps.position_id
FROM tp_position_steps tps
JOIN tp_positions tp ON (tp.position_id=tps.position_id)
WHERE tp.position_status NOT IN ('0','4') AND 
tps.position_step_status=1 AND
tps.position_step_isdefault=0;

INSERT INTO `tp_migration_show_message`(`show_message`,`flag_modified_by`,`flag_modified_on`) VALUES ( '1',NULL,NULL);

INSERT INTO tp_applicant_text_resumes (applicant_id,applicant_text_resume)
SELECT applicant_id, applicant_text_resume from tp_applicants;

alter table tp_applicants drop column applicant_address, drop column applicant_address_type, drop column applicant_text_resume;
alter table tp_applicants add column date_of_birth date   NULL  after applicant_hrms_code, add column passport_number varchar (10)   NULL  after date_of_birth;

alter table tp_applicants add column resume_type_id int (11)   NULL  after passport_number;
alter table tp_applicants add foreign key FK_tp_applicants_9(resume_type_id) references tp_resume_types (resume_type_id);

insert into tp_resume_types (resume_type_id,resume_type) values ( NULL,'Accounting / Tax / Company Secretary / Audit');
insert into tp_resume_types (resume_type_id,resume_type) values ( NULL,'Airline / Reservations / Ticketing / Travel');
insert into tp_resume_types (resume_type_id,resume_type) values ( NULL,'Anchoring / TV / Films / Production');
insert into tp_resume_types (resume_type_id,resume_type) values ( NULL,'Architects / Interior Design / Naval Arch');
insert into tp_resume_types (resume_type_id,resume_type) values ( NULL,'Art Director / Graphic / Web Designer');
insert into tp_resume_types (resume_type_id,resume_type) values ( NULL,'Banking and finance');
insert into tp_resume_types (resume_type_id,resume_type) values ( NULL,'Content / Editors / Journalists');
insert into tp_resume_types (resume_type_id,resume_type) values ( NULL,'Corporate Planning / Consulting / Strategy');
insert into tp_resume_types (resume_type_id,resume_type) values ( NULL,'Entrepreneur / Businessman / Outside Management Consultant');
insert into tp_resume_types (resume_type_id,resume_type) values ( NULL,'Export / Import');
insert into tp_resume_types (resume_type_id,resume_type) values ( NULL,'Front Office Staff / Secretarial / Computer Operator');
insert into tp_resume_types (resume_type_id,resume_type) values ( NULL,'Hotels / Restaurant Management');
insert into tp_resume_types (resume_type_id,resume_type) values ( NULL,'HR / Admin / PM / IR / Training');
insert into tp_resume_types (resume_type_id,resume_type) values ( NULL,'ITES / BPO / Operations / Customer Service / Telecalling');
insert into tp_resume_types (resume_type_id,resume_type) values ( NULL,'Legal / Law');
insert into tp_resume_types (resume_type_id,resume_type) values ( NULL,'Medical Professional / Healthcare Practitioner / Technician');
insert into tp_resume_types (resume_type_id,resume_type) values ( NULL,'Mktg / Advtg / MR / Media Planning / PR / Corp. Comm.');
insert into tp_resume_types (resume_type_id,resume_type) values ( NULL,'Packaging Development');
insert into tp_resume_types (resume_type_id,resume_type) values ( NULL,'Production / Service Engineering / Manufacturing / Maintenance');
insert into tp_resume_types (resume_type_id,resume_type) values ( NULL,'Project Management / Site Engineers');
insert into tp_resume_types (resume_type_id,resume_type) values ( NULL,'Sales / Business Development / Client Servicing');
insert into tp_resume_types (resume_type_id,resume_type) values ( NULL,'Software Development - Application Programming');
insert into tp_resume_types (resume_type_id,resume_type) values ( NULL,'Software Development - Client Server');
insert into tp_resume_types (resume_type_id,resume_type) values ( NULL,'Software Development - Database Administration');
insert into tp_resume_types (resume_type_id,resume_type) values ( NULL,'Software Development - e-commerce / Internet Technologies');
insert into tp_resume_types (resume_type_id,resume_type) values ( NULL,'Software Development - Embedded Technologies');
insert into tp_resume_types (resume_type_id,resume_type) values ( NULL,'Software Development - ERP / CRM');
insert into tp_resume_types (resume_type_id,resume_type) values ( NULL,'Software Development - Network Administration');
insert into tp_resume_types (resume_type_id,resume_type) values ( NULL,'Software Development - Others');
insert into tp_resume_types (resume_type_id,resume_type) values ( NULL,'Software Development - QA and Testing');
insert into tp_resume_types (resume_type_id,resume_type) values ( NULL,'Software Development - System Programming');
insert into tp_resume_types (resume_type_id,resume_type) values ( NULL,'Software Development - Systems / EDP / MIS');
insert into tp_resume_types (resume_type_id,resume_type) values ( NULL,'Software Development - Telecom Software');
insert into tp_resume_types (resume_type_id,resume_type) values ( NULL,'Top Management');
insert into tp_resume_types (resume_type_id,resume_type) values ( NULL,'Telecom / IT-Hardware / Tech. Staff / Support');
insert into tp_resume_types (resume_type_id,resume_type) values ( NULL,'Teaching / Education / Language Specialist');
insert into tp_resume_types (resume_type_id,resume_type) values ( NULL,'Any Other');

CREATE TABLE temp_screen_config (         
                      temp_field_rank tinyint(4) default '0'  
                    ) ENGINE=InnoDB DEFAULT CHARSET=latin1 ;

INSERT INTO temp_screen_config (temp_field_rank)
SELECT field_rank FROM tp_screen_configurations WHERE field_id='NoticePeriod';

UPDATE tp_screen_configurations SET field_rank=field_rank+3 
WHERE field_rank>(SELECT temp_field_rank FROM temp_screen_config);

insert into tp_screen_configurations (field_id,field_type,is_process_field,field_rank,field_import_show,field_edit_show,field_import_mandatory,field_confidential,field_vendor_show,field_vendor_mandatory,field_employee_show,field_employee_mandatory,field_applicant_show_on_site,field_applicant_mandatory_on_site) 
values ( 'DateOfBirth','0','0',(SELECT temp_field_rank+1 FROM temp_screen_config),'1','1','0','0','0','0','1','0','1','0');

insert into tp_screen_configurations (field_id,field_type,is_process_field,field_rank,field_import_show,field_edit_show,field_import_mandatory,field_confidential,field_vendor_show,field_vendor_mandatory,field_employee_show,field_employee_mandatory,field_applicant_show_on_site,field_applicant_mandatory_on_site) 
values ( 'PassportNumber','0','0',(SELECT temp_field_rank+2 FROM temp_screen_config),'1','1','0','0','0','0','1','0','1','0');

insert into tp_screen_configurations (field_id,field_type,is_process_field,field_rank,field_import_show,field_edit_show,field_import_mandatory,field_confidential,field_vendor_show,field_vendor_mandatory,field_employee_show,field_employee_mandatory,field_applicant_show_on_site,field_applicant_mandatory_on_site) 
values ( 'ResumeType','0','0',(SELECT temp_field_rank+3 FROM temp_screen_config),'1','1','0','0','0','0','1','0','1','0');

DROP TABLE temp_screen_config;

UPDATE tp_user_activity SET activity=LEFT(activity, 1000);
ALTER TABLE tp_user_activity change activity activity VARCHAR (1000) NULL COLLATE latin1_swedish_ci;
		   
insert into tp_cr_last_update (last_run_date) values ( '2006-01-01 00:00:00');

alter table tp_custom_labels change custom_label_value custom_label_value varchar (250)   NOT NULL  COLLATE latin1_swedish_ci;
DROP TABLE IF EXISTS tmp_skills;


INSERT INTO tp_cr_filters(filter_name) VALUES('As of date filter');
INSERT INTO tp_cr_filters(filter_name) VALUES('Date filter');
INSERT INTO tp_cr_filters(filter_name) VALUES('Position status filter');
INSERT INTO tp_cr_filters(filter_name) VALUES('Position owner filter');
INSERT INTO tp_cr_filters(filter_name) VALUES('Department filter');
INSERT INTO tp_cr_filters(filter_name) VALUES('Position filter');
INSERT INTO tp_cr_filters(filter_name) VALUES('Role Filter');
INSERT INTO tp_cr_filters(filter_name) VALUES('User Filter');
INSERT INTO tp_cr_filters(filter_name) VALUES('Source Category filter');
INSERT INTO tp_cr_filters(filter_name) VALUES('Source filter');
INSERT INTO tp_cr_filters(filter_name) VALUES('Stage Filter');
INSERT INTO tp_cr_filters(filter_name) VALUES('Step Filter');


INSERT INTO `tp_cr_table_type` (`table_type_id`, `table_short_name`, `table_db_name`, `table_column_join`) values('1','trcm','tp_cr_candidate_master','position_id');
insert into `tp_cr_table_type` (`table_type_id`, `table_short_name`, `table_db_name`, `table_column_join`) values('2','trpm','tp_cr_position_master','position_id');
insert into `tp_cr_table_type` (`table_type_id`, `table_short_name`, `table_db_name`, `table_column_join`) values('3','tres','tp_cr_event_summary','position_id');
insert into `tp_cr_table_type` (`table_type_id`, `table_short_name`, `table_db_name`, `table_column_join`) values('4','tras','tp_cr_activity_summary','user_id');
insert into `tp_cr_table_type` (`table_type_id`, `table_short_name`, `table_db_name`, `table_column_join`) values('5','trsm','tp_step_master','step_id');
insert into `tp_cr_table_type` (`table_type_id`, `table_short_name`, `table_db_name`, `table_column_join`) values('6','tsl','tp_step_level','step_level');
insert into `tp_cr_table_type`(`table_type_id`,`table_short_name`,`table_db_name`,`table_column_join`) values ( '7','tusr','tp_users','user_id');

INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('C01','Applicant Id','1','java.lang.String','applicant_id','75',NULL,NULL,'0','1','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('C02','Applicant Name','1','java.lang.String','applicant_name','150',NULL,NULL,'0','1','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('C03','Applicant Email1','1','java.lang.String','applicant_email1','150',NULL,NULL,'0','1','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('C04','Applicant Email2','1','java.lang.String','applicant_email2','150',NULL,NULL,'0','1','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('C05','Applicant Cell Phone','1','java.lang.String','applicant_cell_phone','75',NULL,NULL,'0','1','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('C06','Applicant Home Phone','1','java.lang.String','applicant_home_phone','75',NULL,NULL,'0','1','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('C07','Applicant Work Phone','1','java.lang.String','applicant_work_phone','75',NULL,NULL,'0','1','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('C08','Applicant Location','1','java.lang.String','applicant_city','75',NULL,NULL,'0','1','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('C09','Skills','1','java.lang.String','skills','75',NULL,NULL,'0','1','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('C10','Branch','1','java.lang.String','branch_name','75',NULL,NULL,'0','1','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('C11','Degree','1','java.lang.String','degree_title','75',NULL,NULL,'0','1','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('C12','YOP','1','java.lang.String','YOP','75',NULL,NULL,'0','1','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('C13','Institute','1','java.lang.String','institute_name','150',NULL,NULL,'0','1','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('C14','Date of Birth','1','java.lang.String','date_of_birth','75',NULL,NULL,'0','1','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('C15','Experience','1','java.lang.String','applicant_working_since','75',NULL,NULL,'0','1','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('C16','Current Employer','1','java.lang.String','applicant_current_employer','75',NULL,NULL,'0','1','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('C17','Applicant Date Created','1','java.util.Date','applicant_date_created','75',NULL,NULL,'0','1','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('C18','Applicant Date Joined','1','java.util.Date','applicant_date_joined','75',NULL,NULL,'0','1','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('C19','Employee Code','1','java.lang.String','employee_code','75',NULL,NULL,'0','1','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('C20','Current CTC','1','java.lang.String','current_ctc','75',NULL,NULL,'0','1','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('C21','Expected CTC','1','java.lang.String','expected_ctc','75',NULL,NULL,'0','1','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('C22','Applicant Notice Period','1','java.lang.String','applicant_notice_period','75',NULL,NULL,'0','1','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('C23','Applicant HRMS Code','1','java.lang.String','applicant_hrms_code','75',NULL,NULL,'0','1','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('C24','Flags','1','java.lang.String','flags','75',NULL,NULL,'0','1','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('C25','Passport Number','1','java.lang.String','passport_number','75',NULL,NULL,'0','1','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('C26','Resume Type','1','java.lang.String','resume_type','75',NULL,NULL,'0','1','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('C27','Source','1','java.lang.String','source_title','75',NULL,NULL,'0','1','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('C28','Source Type','1','java.lang.String','source_type','75',NULL,NULL,'0','1','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('C29','Last Status Message','1','java.lang.String','status_message','150',NULL,NULL,'0','1','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('C30','Last Feedback Entered','1','java.lang.String','trait','150',NULL,NULL,'0','1','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('C31','Applicant Imported By','1','java.lang.String','user_name','100',NULL,NULL,'0','1','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('P01','Position Code','2','java.lang.String','position_code','300',NULL,NULL,'0','1','2','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('P02','Position Title','2','java.lang.String','position_title','300',NULL,'P02','0','1','2','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('P03','Vacancies','2','java.lang.String','position_no_of_openings','75',NULL,NULL,'0','1','2','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('P04','Position Status','2','java.lang.String','position_status','75',NULL,NULL,'0','1','2','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('P05','Owner','2','java.lang.String','position_owner','75',NULL,NULL,'0','1','2','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('P06','Requested By','2','java.lang.String','position_requested_by','75',NULL,NULL,'0','1','2','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('P07','Hire By Date','2','java.util.Date','position_date_expiry','75',NULL,NULL,'0','1','2','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('P08','Created By','2','java.lang.String','position_created_by','75',NULL,NULL,'0','1','2','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('P09','Date Created','2','java.util.Date','position_date_created','75',NULL,NULL,'0','1','2','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('P10','Date Approved','2','java.util.Date','position_date_approved','75',NULL,NULL,'0','1','2','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('P11','Position Department','2','java.lang.String','dept_name','100',NULL,'P11','0','1','2','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('P12','Sub Department','2','java.lang.String','sub_dept','75',NULL,NULL,'0','1','2','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('P13','Group','2','java.lang.String','sub2_dept','75',NULL,NULL,'0','1','2','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('P14','Budget','2','java.lang.String','budget_item_name','75',NULL,NULL,'0','1','2','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('P15','Grade','2','java.lang.String','grade_name','75',NULL,NULL,'0','1','2','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('P16','Band','2','java.lang.String','band_name','75',NULL,NULL,'0','1','2','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('P17','Dept Level 3','2','java.lang.String','sub3_dept','75',NULL,NULL,'0','1','2','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('P18','Dept Level 4','2','java.lang.String','sub4_dept','75',NULL,NULL,'0','1','2','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('P19','Note','2','java.lang.String','position_note','75',NULL,NULL,'0','1','2','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('P20','Priority','2','java.lang.String','position_priority','75',NULL,NULL,'0','1','2','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('P21','Level','2','java.lang.String','position_level','75',NULL,NULL,'0','1','2','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('P22','Referral Fee','2','java.lang.String','position_referal_fees','75',NULL,NULL,'0','1','2','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('P23','Vacancy Type','2','java.lang.String','type_of_vacancy','75',NULL,NULL,'0','1','2','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('P24','Replacement Emp Code','2','java.lang.String','replacement_emp_code','75',NULL,NULL,'0','1','2','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('P25','Skills','2','java.lang.String','skills','75',NULL,NULL,'0','1','2','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('P26','Locations','2','java.lang.String','locations','75',NULL,NULL,'0','1','2','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('P27','Cost Center','2','java.lang.String','cost_center','75',NULL,NULL,'0','1','2','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('P28','Business Unit','2','java.lang.String','bu','75',NULL,NULL,'0','1','2','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('A01','Activity User Id','4','java.lang.Integer','user_id','75',NULL,NULL,'0','0','4','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('A02','Activity BY','4','java.lang.String','user_name','150','A01','A01','0','1','4','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('A03','Activity Date','4','java.util.Date','activity_date','75','A03','A03','0','1','4','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('A031','Activity Date String Format','4','java.lang.String','activity_date','75','A03','A03','0','0','4','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('A04','Activity Week','4','java.lang.String','concat(DATE_FORMAT(SUBDATE(${A031},(dayofweek(${A031})-1)),\"%d-%b\"),\" to \",DATE_FORMAT(ADDDATE(${A031}, (7-dayofweek(${A031}))),\"%d-%b\"))','75',NULL,NULL,'0','1',NULL,'1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('A05','Activity Month','4','java.lang.String','date_format(${A031}, \'%b-%y\')','75','A05','A03','0','1','4','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('A06','Imported','4','java.lang.Long','imported','75',NULL,NULL,'0','1','4','2');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('A07','Email Recieved','4','java.lang.Long','email_recieved','75',NULL,NULL,'0','1','4','2');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('A08','Email Sent','4','java.lang.Long','email_sent','75',NULL,NULL,'0','1','4','2');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('A09','Appointment','4','java.lang.Long','appointment','75',NULL,NULL,'0','1','4','2');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('A10','Interview','4','java.lang.Long','interview','75',NULL,NULL,'0','1','4','2');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('A11','Messages','4','java.lang.Long','messages','75',NULL,NULL,'0','1','4','2');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('A12','Phone','4','java.lang.Long','phone','75',NULL,NULL,'0','1','4','2');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('A13','Note','4','java.lang.Long','note','75',NULL,NULL,'0','1','4','2');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('A14','Status Message','4','java.lang.Long','status_message','75',NULL,NULL,'0','1','4','2');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('A15','Shortlisted','4','java.lang.Long','shortlisted','75',NULL,NULL,'0','1','4','2');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('S01','Process User Id','3','java.lang.Integer','user_id','75',NULL,NULL,'0','0','3','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('S02','Process User','3','java.lang.String','user_name','150','S01','S01','0','1','7','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('S03','Process Date','3','java.util.Date','process_date','75','S03','S03','0','1','3','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('S031','Process Date String Format','3','java.util.String','process_date','75','S03','S03','0','0','3','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('S04','Process Month Sequence','3','java.lang.Integer','date_format(${S031}, \'%c\')','75',NULL,NULL,'0','0','3','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('S05','Process Month','3','com.talentPool.customReports.djhelper.wrappers.MonthNameComparator','date_format(${S031}, \'%b-%y\')','75',NULL,'S04','0','1','3','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('S06','Process Week','3','java.lang.String','concat(DATE_FORMAT(SUBDATE(${S03},(dayofweek(${S03})-1)),\"%d-%b\"),\" to \",DATE_FORMAT(ADDDATE(${S03}, (7-dayofweek(${S03}))),\"%d-%b\"))','75',NULL,NULL,'0','1','3','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('S07','Step Id','3','java.lang.Integer','step_id','75',NULL,NULL,'0','0','3','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('S08','Step Rank','3','java.lang.Integer','step_rank','75',NULL,NULL,'0','0','5','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('S09','Step','3','com.talentPool.customReports.djhelper.wrappers.StepComparator','step_name','75','S07','S08','0','1','5','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('S10','Step Level','3','java.lang.Integer','step_level','75',NULL,NULL,'0','0','3','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('S11','Stage','3','com.talentPool.customReports.djhelper.wrappers.StepLevelComparator','step_level_name','75','S10','S10','0','1','6','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('S12','EXISTING','3','java.lang.Integer','backlog','75',NULL,'S03','0','1','3','3');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('S13','ADDED','3','java.lang.Integer','received','75',NULL,NULL,'0','1','3','2');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('S14','SELECTED','3','java.lang.Integer','cleared','75',NULL,NULL,'0','1','3','2');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('S15','INPROCESS','3','java.lang.Integer','inprocess','75',NULL,NULL,'0','0','3','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('S16','REJECTED','3','java.lang.Integer','rejected','75',NULL,NULL,'0','1','3','2');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('S17','Status as of Date','3','java.lang.Integer','${S13}','75',NULL,NULL,'0','1','3','2');


INSERT INTO tp_cr_column_filter_map(column_property,filter_id) VALUES('S01',8);
INSERT INTO tp_cr_column_filter_map(column_property,filter_id) VALUES('S03',2);
INSERT INTO tp_cr_column_filter_map(column_property,filter_id) VALUES('S09',12);
INSERT INTO tp_cr_column_filter_map(column_property,filter_id) VALUES('S13',2);
INSERT INTO tp_cr_column_filter_map(column_property,filter_id) VALUES('S14',2);
INSERT INTO tp_cr_column_filter_map(column_property,filter_id) VALUES('S16',2);
INSERT INTO tp_cr_column_filter_map(column_property,filter_id) VALUES('S11',11);
INSERT INTO tp_cr_column_filter_map(column_property,filter_id) VALUES('S05',2);
INSERT INTO tp_cr_column_filter_map(column_property,filter_id) VALUES('S12',2);
INSERT INTO tp_cr_column_filter_map(column_property,filter_id) VALUES('S06',2);
INSERT INTO tp_cr_column_filter_map(column_property,filter_id) VALUES('S17',1);
INSERT INTO tp_cr_column_filter_map(column_property,filter_id) VALUES('S15',2);
INSERT INTO tp_cr_column_filter_map(column_property,filter_id) VALUES('A02',8);
INSERT INTO tp_cr_column_filter_map(column_property,filter_id) VALUES('A03',2);
INSERT INTO tp_cr_column_filter_map(column_property,filter_id) VALUES('A05',2);
INSERT INTO tp_cr_column_filter_map(column_property,filter_id) VALUES('A04',2); 
INSERT INTO tp_cr_column_filter_map(column_property,filter_id) VALUES('P01',3);
INSERT INTO tp_cr_column_filter_map(column_property,filter_id) VALUES('P01',4);
INSERT INTO tp_cr_column_filter_map(column_property,filter_id) VALUES('P01',5);
INSERT INTO tp_cr_column_filter_map(column_property,filter_id) VALUES('P01',6);
INSERT INTO tp_cr_column_filter_map(column_property,filter_id) VALUES('P02',3);
INSERT INTO tp_cr_column_filter_map(column_property,filter_id) VALUES('P02',4);
INSERT INTO tp_cr_column_filter_map(column_property,filter_id) VALUES('P02',5);
INSERT INTO tp_cr_column_filter_map(column_property,filter_id) VALUES('P02',6);
INSERT INTO tp_cr_column_filter_map(column_property,filter_id) VALUES('P04',3);
INSERT INTO tp_cr_column_filter_map(column_property,filter_id) VALUES('P11',5);
INSERT INTO tp_cr_column_filter_map(column_property,filter_id) VALUES('P11',6);
INSERT INTO tp_cr_column_filter_map(column_property,filter_id) VALUES('C02',9);
INSERT INTO tp_cr_column_filter_map(column_property,filter_id) VALUES('C02',10);


insert into `tp_custom_reports` (`report_id`,`report_name`,`report_desc`,`created_by`,`date_created`,`modified_by`,`date_modified`,`xml_file_path`,`template_file_path`,`query_file_path`) values (1,'Hiring Status Report','Hiring Status Report','1',now(),'1',now(),'CR_1/CR_1.xml','CR_1/CR_1.jrxml','CR_1/CR_1.sql');
insert into `tp_custom_reports` (`report_id`,`report_name`,`report_desc`,`created_by`,`date_created`,`modified_by`,`date_modified`,`xml_file_path`,`template_file_path`,`query_file_path`) values (2,'User Activity Report','User Activity Report','1',now(),'1',now(),'CR_2/CR_2.xml','CR_2/CR_2.jrxml','CR_2/CR_2.sql');
insert into `tp_custom_reports` (`report_id`,`report_name`,`report_desc`,`created_by`,`date_created`,`modified_by`,`date_modified`,`xml_file_path`,`template_file_path`,`query_file_path`) values (3,'Hiring Activity Report','Hiring Activity Report','1',now(),'1',now(),'CR_3/CR_3.xml','CR_3/CR_3.jrxml','CR_3/CR_3.sql');


INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('P29','Position Id','2','java.lang.String','position_id','75',NULL,NULL,'0','0','2','1');
UPDATE tp_custom_report_columns SET group_by_column='P29' WHERE column_property='P01';
UPDATE tp_custom_report_columns SET group_by_column='P29' WHERE column_property='P02';

ALTER TABLE tp_cr_table_type ADD COLUMN table_type_name VARCHAR(25) CHARSET latin1 COLLATE latin1_swedish_ci NOT NULL after table_type_id;

UPDATE tp_cr_table_type set table_type_name='Candidate Master'WHERE table_type_id='1';
UPDATE tp_cr_table_type set table_type_name='Position Master'WHERE table_type_id='2';
UPDATE tp_cr_table_type set table_type_name='Event Summary' WHERE table_type_id='3';
UPDATE tp_cr_table_type set table_type_name='Activity Summary'WHERE table_type_id='4';
UPDATE tp_cr_table_type set table_type_name='Step Master' WHERE table_type_id='5';
UPDATE tp_cr_table_type set table_type_name='Step Level' WHERE table_type_id='6';
UPDATE tp_cr_table_type set table_type_name='User Master' WHERE table_type_id='7';

INSERT INTO `tp_cr_table_type` (`table_type_id`, table_type_name, `table_short_name`, `table_db_name`, `table_column_join`) values('8','Event Log','tres','tp_cr_event_log','position_id');

UPDATE tp_cr_table_type SET table_column_join='applicant_id' WHERE table_type_id='1';
UPDATE tp_custom_report_columns SET group_by_column='S05' WHERE column_property='S05';
