use talentpool;

CALL PROC_ADD_COLUMN_IF_NOT_EXISTS('tp_report_scheduler','position_filter','INT(11) NULL');
CALL PROC_ADD_COLUMN_IF_NOT_EXISTS('tp_report_scheduler','department_filter','INT(11) NULL');

INSERT INTO tp_application_properties (application_property,application_property_value) 
VALUES ('all_selected_time_zones','Asia/Calcutta,Europe/London,Europe/Berlin,US/Eastern');

update tp_cr_scheduler
set scheduler_start_hour= now();

UPDATE tp_position_screen_description SET field_vendor_show='1', field_employee_show='1' WHERE field_id='PositionName';