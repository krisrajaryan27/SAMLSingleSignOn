use talentpool;

DELIMITER $$

DROP PROCEDURE IF EXISTS `PROC_POSITION_NAUKRI_RANK`$$

CREATE PROCEDURE `PROC_POSITION_NAUKRI_RANK` ()
BEGIN
DECLARE v_rank INT DEFAULT 0;

SELECT field_rank INTO v_rank FROM tp_position_screen_description WHERE field_id = 'RequestedBy';

UPDATE tp_position_screen_description SET field_rank = field_rank+15
WHERE field_rank > v_rank;

INSERT INTO tp_position_screen_description(field_id,field_type,field_rank,field_position_show,field_position_mandatory,is_field_naukri) 
VALUES ('contactPersonName',0,v_rank+1,1,0,1);

INSERT INTO tp_position_screen_description(field_id,field_type,field_rank,field_position_show,field_position_mandatory,is_field_naukri) 
VALUES ('jobIndustryCode',0,v_rank+2,1,1,1);

INSERT INTO tp_position_screen_description(field_id,field_type,field_rank,field_position_show,field_position_mandatory,is_field_naukri) 
VALUES ('jobFunctionCode',0,v_rank+3,1,1,1);

INSERT INTO tp_position_screen_description(field_id,field_type,field_rank,field_position_show,field_position_mandatory,is_field_naukri) 
VALUES ('jobRoleCode',0,v_rank+4,1,1,1);

INSERT INTO tp_position_screen_description(field_id,field_type,field_rank,field_position_show,field_position_mandatory,is_field_naukri) 
VALUES ('jobKeywords',0,v_rank+5,1,1,1);

INSERT INTO tp_position_screen_description(field_id,field_type,field_rank,field_position_show,field_position_mandatory,is_field_naukri) 
VALUES ('country',0,v_rank+6,1,1,1);

INSERT INTO tp_position_screen_description(field_id,field_type,field_rank,field_position_show,field_position_mandatory,is_field_naukri) 
VALUES ('minimumSalary',0,v_rank+7,1,1,1);

INSERT INTO tp_position_screen_description(field_id,field_type,field_rank,field_position_show,field_position_mandatory,is_field_naukri) 
VALUES ('maximumSalary',0,v_rank+8,1,1,1);

INSERT INTO tp_position_screen_description(field_id,field_type,field_rank,field_position_show,field_position_mandatory,is_field_naukri) 
VALUES ('benefitsDescription',0,v_rank+9,1,0,1);

INSERT INTO tp_position_screen_description(field_id,field_type,field_rank,field_position_show,field_position_mandatory,is_field_naukri) 
VALUES ('displaySalary',0,v_rank+10,1,0,1);

INSERT INTO tp_position_screen_description(field_id,field_type,field_rank,field_position_show,field_position_mandatory,is_field_naukri) 
VALUES ('desiredCandidateSummaryText',0,v_rank+11,1,1,1);

INSERT INTO tp_position_screen_description(field_id,field_type,field_rank,field_position_show,field_position_mandatory,is_field_naukri) 
VALUES ('contactPersonEmail',0,v_rank+12,1,0,1);

INSERT INTO tp_position_screen_description(field_id,field_type,field_rank,field_position_show,field_position_mandatory,is_field_naukri) 
VALUES ('applyByWebURL',0,v_rank+13,1,0,1);

INSERT INTO tp_position_screen_description(field_id,field_type,field_rank,field_position_show,field_position_mandatory,is_field_naukri) 
VALUES ('jobFeedResponseEmail',0,v_rank+14,1,1,1);

INSERT INTO tp_position_screen_description(field_id,field_type,field_rank,field_position_show,field_position_mandatory,is_field_naukri) 
VALUES ('salaryCurrency',0,v_rank+15,1,0,1);
END$$

DELIMITER ;
