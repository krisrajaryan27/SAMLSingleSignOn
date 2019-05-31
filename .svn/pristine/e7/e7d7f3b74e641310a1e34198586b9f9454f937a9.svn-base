use talentpool;

INSERT INTO tp_report_levels (level_id, report_id, report_type) VALUES ('1','27','0');
INSERT INTO tp_report_levels (level_id, report_id, report_type) VALUES ('1','28','0');
INSERT INTO tp_report_levels (level_id, report_id, report_type) VALUES ('1','29','0');
INSERT INTO tp_report_levels (level_id, report_id, report_type) VALUES ('1','30','0');
INSERT INTO tp_report_levels (level_id, report_id, report_type) VALUES ('1','31','0');

INSERT INTO `tp_application_properties` (`application_property`,`application_property_value`) 
VALUES ('offer_to_joined_step_id',ifnull((SELECT tsm.step_id FROM tp_step_master tsm WHERE tsm.step_level=2 
AND step_deleted=0 AND system_step = '0' AND step_disabled = '0' ORDER BY tsm.step_rank LIMIT 0,1),''))
