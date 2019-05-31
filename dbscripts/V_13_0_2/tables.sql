use talentpool;

ALTER TABLE tp_report_scheduler ADD COLUMN position_filter INT(11) NULL;
ALTER TABLE tp_report_scheduler ADD COLUMN department_filter INT(11) NULL;
ALTER TABLE tp_report_scheduler CHANGE step_ids step_ids VARCHAR(255) NULL;