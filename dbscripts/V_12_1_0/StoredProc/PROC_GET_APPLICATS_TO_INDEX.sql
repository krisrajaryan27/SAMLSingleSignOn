DELIMITER $$

DROP PROCEDURE IF EXISTS `PROC_GET_APPLICATS_TO_INDEX`$$

CREATE PROCEDURE `PROC_GET_APPLICATS_TO_INDEX`(IN _eventType VARCHAR(40), IN _eventId INT,
IN _start INT, IN _batch INT, IN _Type_All VARCHAR(50), IN _Type_Add_Applicant VARCHAR(50),
IN _Type_Update_Applicant VARCHAR(50), IN _Type_Skills VARCHAR(50), IN _Type_Degree VARCHAR(50),
IN _Type_Institute VARCHAR(50), IN _Type_Branch VARCHAR(50), IN _Type_Source VARCHAR(50),
IN _Step_Reject INT, IN _Step_Not_Interested_Reject INT, IN _Step_Not_Attended_Reject INT,
IN _Step_Position_Closed_Reject INT, IN _System_Generated VARCHAR(1), IN applicantJoined INT, IN applicantNotJoined INT)
BEGIN
IF STRCMP(_eventType, _Type_Update_Applicant)=0 THEN
SET _eventType = _Type_Add_Applicant;
END IF;
SET @qry = "
SELECT applicant_id,employee_code ,applicant_name ,applicant_city ,applicant_email1 ,applicant_email2 ,
applicant_home_phone ,applicant_cell_phone ,applicant_work_phone ,applicant_working_since ,
applicant_position_id ,applicant_step_id ,applicant_date_created ,source_id , 
case when (select count(*) 
from tp_applicant_joining_history tajh
where tajh.applicant_id=app.applicant_id) > 0 then ";
SET @qry =CONCAT(@qry, applicantJoined);
SET @qry =CONCAT(@qry, " else ");
SET @qry =CONCAT(@qry, applicantNotJoined);
SET @qry =CONCAT(@qry, " end as applicant_joined");
SET @qry =CONCAT(@qry, " , applicant_original_resume_path , applicant_text_resume ,applicant_current_employer
,source_title, source_type_id, reject_reason_ids, reject_step_levels, skill,
CONCAT(case when skill_aliases is null then '' else skill_aliases end,
' ',case when degree_alias is null then '' else degree_alias end
,' ',case when branch_alias is null then '' else branch_alias end
,' ',case when institute_alias is null then '' else institute_alias end) as alias,
CONCAT(flag_ids , '') as flag_ids, is_confidential,applicant_status,
date_of_birth, passport_number, resume_type, resume_type_id, 
STR_TO_DATE((SELECT interactions.interaction_date
FROM
(
(
SELECT applicant_id,
CASE WHEN email_date_received THEN email_date_send ELSE email_date_received END AS interaction_date
FROM tp_applicant_inbox_emails
)
UNION ALL
(
SELECT applicant_id, appointment_date_created AS interaction_date
FROM tp_appointments ta
)
UNION ALL
(
SELECT applicant_id, tasp.process_moved_date AS interaction_date
FROM tp_applicant_selection_process tasp
)
UNION ALL
(
SELECT applicant_id, communication_date_created AS interaction_date
FROM tp_communications
)
UNION ALL
(
SELECT applicant_id, date_created AS interaction_date
FROM tp_applicant_status_messages WHERE system_generated=");
SET @qry =CONCAT(@qry, _System_Generated);
SET @qry =CONCAT(@qry, "
)
)AS interactions
WHERE interactions.applicant_id=app.applicant_id
ORDER BY interaction_date DESC
LIMIT 0, 1), '%Y-%m-%d') as interaction_date
FROM
(
SELECT ta.applicant_id,ta.employee_code,ta.applicant_name ,ta.applicant_city ,ta.applicant_email1 ,ta.applicant_email2 ,
ta.applicant_home_phone ,ta.applicant_cell_phone ,ta.applicant_work_phone ,ta.applicant_working_since ,
ta.applicant_position_id ,ta.applicant_step_id ,ta.applicant_date_created ,ta.source_id , ta.applicant_joined ,
ta.applicant_original_resume_path , tatr.applicant_text_resume ,ta.applicant_current_employer
,ta.source_title, ta.source_type_id, ta.is_confidential, ta.applicant_status,
group_concat(distinct cast(tasp.position_step_id_to as CHAR) SEPARATOR ',') as reject_reason_ids,
group_concat(distinct cast(tps.position_step_level as CHAR) SEPARATOR ',') as reject_step_levels,
group_concat(distinct tsk.skill separator ' ') as skill,
group_concat(distinct tsa.skill_alias separator ' ') as skill_aliases,
group_concat(distinct tda.alias separator ' ') as degree_alias,
group_concat(distinct tba.alias separator ' ') as branch_alias,
group_concat(distinct tia.alias separator ' ') as institute_alias,
group_concat(distinct taf.flag_id SEPARATOR ',') as flag_ids,
ta.date_of_birth, ta.passport_number, try.resume_type, ta.resume_type_id
FROM
(
SELECT ta.applicant_id,ta.employee_code ,ta.applicant_name ,ta.applicant_city ,ta.applicant_email1 ,ta.applicant_email2 ,
ta.applicant_home_phone ,ta.applicant_cell_phone ,ta.applicant_work_phone ,ta.applicant_working_since ,
ta.applicant_position_id ,ta.applicant_step_id ,ta.applicant_date_created ,ta.source_id , ta.applicant_joined ,
ta.applicant_original_resume_path ,ta.applicant_current_employer,
ts.source_title, tst.source_type_id,ta.is_confidential,ta.applicant_status,
ta.date_of_birth, ta.passport_number, ta.resume_type_id
FROM tp_applicants ta, tp_sources ts, tp_source_types tst
");
IF STRCMP(_eventType, _Type_Skills)=0 THEN
SET @qry =CONCAT(@qry, ", tp_applicant_skills tas ");
END IF;
IF STRCMP(_eventType, _Type_Degree)=0 THEN
SET @qry =CONCAT(@qry,", tp_applicant_educational_info taei ");
END IF;
IF STRCMP(_eventType, _Type_Institute)=0 THEN
SET @qry =CONCAT(@qry,", tp_applicant_educational_info taei ");
END IF;
IF STRCMP(_eventType, _Type_Branch)=0 THEN
SET @qry =CONCAT(@qry,", tp_applicant_educational_info taei ");
END IF;
SET @qry =CONCAT(@qry," WHERE ta.source_id = ts.source_id AND ts.source_type_id=tst.source_type_id");
IF STRCMP(_eventType, _Type_Add_Applicant)=0 THEN
SET @qry =CONCAT(@qry," AND ta.applicant_id =",_eventId);
ELSEIF STRCMP(_eventType, _Type_Skills)=0 THEN
SET @qry =CONCAT(@qry," AND tas.applicant_id = ta.applicant_id ");
SET @qry =CONCAT(@qry," AND tas.skill_id=",_eventId);
ELSEIF STRCMP(_eventType, _Type_Degree)=0 THEN
SET @qry =CONCAT(@qry," AND ta.applicant_id=taei.applicant_id ");
SET @qry =CONCAT(@qry," AND taei.degree_id =",_eventId);
ELSEIF STRCMP(_eventType, _Type_Institute)=0 THEN
SET @qry =CONCAT(@qry," AND ta.applicant_id=taei.applicant_id ");
SET @qry =CONCAT(@qry," AND taei.institute_id =",_eventId);
ELSEIF STRCMP(_eventType, _Type_Branch)=0 THEN
SET @qry =CONCAT(@qry," AND ta.applicant_id=taei.applicant_id ");
SET @qry =CONCAT(@qry," AND taei.branch_id =",_eventId);
ELSEIF STRCMP(_eventType, _Type_Source)=0 THEN
SET @qry =CONCAT(@qry," AND ta.source_id =",_eventId);
END IF;
SET @qry =CONCAT(@qry," LIMIT ",_start ,",", _batch );
SET @qry =CONCAT(@qry,"
) as ta
LEFT JOIN tp_applicant_selection_process tasp ON(ta.applicant_id = tasp.applicant_id AND tasp.position_step_id_to IN ");
SET @qry =CONCAT(@qry,"( ", _Step_Reject , "," , _Step_Not_Interested_Reject, ",", _Step_Not_Attended_Reject, "," , _Step_Position_Closed_Reject ,") ");
SET @qry =CONCAT(@qry," )
LEFT JOIN tp_position_steps tps ON (tasp.position_step_id_from=tps.position_step_id)
LEFT JOIN tp_applicant_skills tas ON (ta.applicant_id = tas.applicant_id)
LEFT JOIN tp_skills_aliases tsa ON (tas.skill_id = tsa.skill_id)
LEFT JOIN tp_skills tsk ON (tas.skill_id = tsk.skill_id)
LEFT JOIN tp_applicant_educational_info taei ON (ta.applicant_id = taei.applicant_id)
LEFT JOIN tp_degree_aliases tda ON (taei.degree_id = tda.degree_id)
LEFT JOIN tp_branch_aliases tba ON (taei.branch_id = tba.branch_id)
LEFT JOIN tp_institute_aliases tia ON (taei.institute_id = tia.institute_id)
LEFT JOIN tp_applicant_flags taf ON( ta.applicant_id = taf.applicant_id)
LEFT JOIN tp_resume_types try ON( try.resume_type_id=ta.resume_type_id)
LEFT JOIN tp_applicant_text_resumes tatr ON( ta.applicant_id = tatr.applicant_id)
GROUP BY ta.applicant_id
) as app
");
PREPARE STMT FROM @qry;
EXECUTE STMT ;
DEALLOCATE PREPARE STMT;
END$$

DELIMITER ;