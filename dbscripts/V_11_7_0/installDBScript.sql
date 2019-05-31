use talentpool;
ALTER TABLE 
tp_screen_configurations ADD COLUMN 
is_process_field char(1) CHARSET latin1 COLLATE latin1_swedish_ci DEFAULT '0' NOT NULL AFTER field_type;

DELETE FROM tp_screen_configurations WHERE field_id='ctc_offered' AND field_type='0';

INSERT INTO tp_screen_configurations 
	(field_id, field_type, is_process_field, field_rank, field_import_show, field_edit_show, 
	field_import_mandatory, field_confidential, field_vendor_show, field_vendor_mandatory, 
	field_employee_show, field_employee_mandatory, field_applicant_show_on_site, 
	field_applicant_mandatory_on_site) 
VALUES ('ctc_offered','0','1',(select max_field_rank+1 FROM (SELECT max(field_rank) AS max_field_rank FROM tp_screen_configurations) AS x),'0','0','0','0','0','0','0','0','0','0');


DELETE FROM tp_screen_configurations WHERE field_id='basic_offered' AND field_type='0';

INSERT INTO tp_screen_configurations 
	(field_id, field_type, is_process_field, field_rank, field_import_show, field_edit_show, 
	field_import_mandatory, field_confidential, field_vendor_show, field_vendor_mandatory, 
	field_employee_show, field_employee_mandatory, field_applicant_show_on_site, 
	field_applicant_mandatory_on_site) 
VALUES ('basic_offered','0','1',(select max_field_rank+1 FROM (SELECT max(field_rank) AS max_field_rank FROM tp_screen_configurations) AS x),'0','0','0','0','0','0','0','0','0','0');

DELETE FROM tp_screen_configurations WHERE field_id='designation_offered' AND field_type='0';

INSERT INTO tp_screen_configurations 
	(field_id, field_type, is_process_field, field_rank, field_import_show, field_edit_show, 
	field_import_mandatory, field_confidential, field_vendor_show, field_vendor_mandatory, 
	field_employee_show, field_employee_mandatory, field_applicant_show_on_site, 
	field_applicant_mandatory_on_site) 
VALUES ('designation_offered','0','1',(select max_field_rank+1 FROM (SELECT max(field_rank) AS max_field_rank FROM tp_screen_configurations) AS x),'0','0','0','0','0','0','0','0','0','0');

DELETE FROM tp_screen_configurations WHERE field_id='level_offered' AND field_type='0';

INSERT INTO tp_screen_configurations 
	(field_id, field_type, is_process_field, field_rank, field_import_show, field_edit_show, 
	field_import_mandatory, field_confidential, field_vendor_show, field_vendor_mandatory, 
	field_employee_show, field_employee_mandatory, field_applicant_show_on_site, 
	field_applicant_mandatory_on_site) 
VALUES ('level_offered','0','1',(select max_field_rank+1 FROM (SELECT max(field_rank) AS max_field_rank FROM tp_screen_configurations) AS x),'0','0','0','0','0','0','0','0','0','0');

ALTER TABLE 
tp_feedback_field_categories ADD COLUMN 
summary_field CHAR(1) CHARSET latin1 COLLATE latin1_swedish_ci DEFAULT '0' NOT NULL AFTER system_generated;

ALTER TABLE tp_feedback_fields 
ADD COLUMN feedback_field_type CHAR(1) CHARSET latin1 COLLATE latin1_swedish_ci DEFAULT '0' NOT NULL AFTER feedback_field_desc, 
ADD COLUMN applicant_field_id VARCHAR(250) CHARSET latin1 COLLATE latin1_swedish_ci NULL AFTER feedback_field_type;