DELIMITER $$

DROP PROCEDURE IF EXISTS `PROC_GET_TODO_LIST`$$

CREATE PROCEDURE `PROC_GET_TODO_LIST`(
	IN _userId BIGINT, IN _positionStatusOpen INT, IN  _appointmentStatusNoShow INT,
	IN _appJoined INT, IN _positionStepLevelSelect INT, IN _includeRequisitionToDo INT,
	IN _positionStatusInprocess INT, IN _todoSchedule INT, IN _todoConfirmAttendance INT,
	IN _todoFeedback INT, IN _todoRequisitionApproval INT
)
BEGIN
DECLARE v_noRecord INT DEFAULT 0;
DECLARE v_applicant_id bigint(20);
DECLARE v_applicant_name varchar(100);
DECLARE v_position_id int(11);
DECLARE v_position_title varchar(100);
DECLARE v_position_code varchar(50);
DECLARE v_position_step_id int(11);
DECLARE v_position_step_title varchar(100);
DECLARE v_position_step_isscheduled char(1); 
DECLARE v_is_interviewer_can_confirm char(1); 
DECLARE v_is_responsible_for_scheduling char(1);
DECLARE v_is_authorized_to_move char(1);
DECLARE v_process_id bigint(20);
DECLARE v_process_moved_date datetime;
DECLARE v_process_date_created datetime;
DECLARE v_appointment_from_date datetime;
DECLARE v_attendee_id bigint(20);
DECLARE vv_process_id int(11);
DECLARE vv_position_step_id_to int(11);
DECLARE vv_trait_count int;
DECLARE vv_is_feedback_pending int;
DECLARE cur_my_applicants CURSOR FOR 
	SELECT
	ta.applicant_id, ta.applicant_name, tp.position_id,
	tp.position_title, tp.position_code, tps.position_step_id, tps.position_step_title, 
	tps.position_step_isscheduled, tps.is_interviewer_can_confirm, tpsu.is_responsible_for_scheduling, 
	tpsu.is_authorized_to_move,
	max(tasp.process_id) as process_id, max(tasp.process_moved_date) as process_moved_date ,
	max(tasp.process_date_created) as process_date_created
	FROM 
	tp_applicants ta, tp_positions tp, tp_position_steps tps, tp_position_step_users tpsu,
	tp_applicant_selection_process tasp
	WHERE 
	ta.applicant_position_id = tp.position_id AND ta.applicant_step_id=tps.position_step_id
	AND ta.applicant_step_id = tpsu.position_step_id AND tasp.applicant_id = ta.applicant_id
	AND tasp.position_id = ta.applicant_position_id AND tasp.position_step_id_to = ta.applicant_step_id
	AND tpsu.user_id= _userId  AND tp.position_status= _positionStatusOpen
	AND ta.applicant_joined= _appJoined AND tps.position_step_level= _positionStepLevelSelect
	GROUP BY  
	ta.applicant_id, ta.applicant_name, ta.applicant_position_id, ta.applicant_step_id , 
	tp.position_title, tps.position_step_title, tps.position_step_isscheduled, tps.is_interviewer_can_confirm,
	tpsu.is_responsible_for_scheduling,tpsu.is_authorized_to_move; 
DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_noRecord = 1;
DROP TABLE IF EXISTS tmp_todos;
CREATE TEMPORARY TABLE tmp_todos (
	applicant_id bigint(20) ,                            
	applicant_name varchar(100)  ,                         
	position_id int(11) , 
	position_title varchar(100),
	position_code varchar(50),
	position_step_id int(11),
	position_step_title varchar(100),
	due_date datetime,			
	action_required int				
) ENGINE=MyISAM;
OPEN cur_my_applicants;
REPEAT
	FETCH cur_my_applicants INTO v_applicant_id, v_applicant_name, v_position_id,
	v_position_title, v_position_code, v_position_step_id, v_position_step_title, 
	v_position_step_isscheduled, v_is_interviewer_can_confirm, v_is_responsible_for_scheduling, 
	v_is_authorized_to_move,
	v_process_id, v_process_moved_date, v_process_date_created;
	if v_noRecord =0 THEN
		IF v_position_step_isscheduled = 1 THEN
			SET v_appointment_from_date = null;
			SET v_attendee_id = null;
			
			SELECT appointment_from_date, attendee_id 
			FROM 
			(SELECT * from tp_appointments order by appointment_from_date desc) as tapo 
			LEFT JOIN tp_appointment_attendees taa 
			ON(tapo.appointment_id = taa.appointment_id AND taa.attendee_id=_userId)
			WHERE tapo.appointment_date_created > v_process_date_created 
			AND tapo.position_step_id = v_position_step_id 
			AND tapo.appointment_status_id !=_appointmentStatusNoShow 
			AND tapo.applicant_id=v_applicant_id LIMIT 0,1
			INTO v_appointment_from_date, v_attendee_id;
					
			IF v_noRecord =1 OR v_appointment_from_date IS null THEN
				SET v_noRecord =0;
				IF v_is_responsible_for_scheduling =1 THEN
				
					INSERT INTO tmp_todos (
					applicant_id, applicant_name, position_id, 
					position_title, position_code, position_step_id, 
					position_step_title, due_date, action_required)
					values(v_applicant_id, v_applicant_name, v_position_id,
					v_position_title, v_position_code, v_position_step_id,
					v_position_step_title, v_process_moved_date, _todoSchedule);
				END IF;
			ELSE
				IF v_appointment_from_date < now() THEN
					SELECT process_id, position_step_id_to FROM tp_applicant_selection_process
					WHERE process_id > v_process_id 
					AND position_step_id_from = v_position_step_id 
					AND applicant_id = v_applicant_id
					AND position_step_id_to != -3 
					INTO vv_process_id, vv_position_step_id_to;
					IF v_noRecord =1 OR vv_process_id IS null THEN
						SET v_noRecord = 0;
						IF v_is_responsible_for_scheduling =1 OR ( v_is_interviewer_can_confirm=1 AND v_is_responsible_for_scheduling=0 AND v_is_authorized_to_move=0) THEN
							
							INSERT INTO tmp_todos (
							applicant_id, applicant_name, position_id, 
							position_title, position_code, position_step_id, 
							position_step_title, due_date, action_required)
							values(v_applicant_id, v_applicant_name, v_position_id,
							v_position_title, v_position_code, v_position_step_id,
							v_position_step_title, v_appointment_from_date, _todoConfirmAttendance);
						END IF;
					ELSE
						IF v_is_responsible_for_scheduling =0 THEN
							SET vv_is_feedback_pending = 1;
							IF v_is_authorized_to_move = 1 THEN
								SET v_attendee_id = _userId;
							ELSE
								IF v_attendee_id IS null THEN
									SET vv_is_feedback_pending = 0;
								END IF;
							END IF;
							IF  vv_is_feedback_pending = 1 THEN
								SELECT count(*) FROM tp_applicant_selection_process_traits
								WHERE process_id = vv_process_id AND
								user_id = v_attendee_id INTO vv_trait_count;
								IF vv_trait_count=0 THEN
									
									INSERT INTO tmp_todos (
									applicant_id, applicant_name, position_id, 
									position_title, position_code, position_step_id, 
									position_step_title, due_date, action_required)
									values(v_applicant_id, v_applicant_name, v_position_id,
									v_position_title, v_position_code, v_position_step_id,
									v_position_step_title, v_appointment_from_date, _todoFeedback);
								END IF;
							END IF;
							
						END IF;
					END IF;
				
				END IF;
			END IF;
		ELSE
			
			SELECT count(*) FROM tp_applicant_selection_process tasp, 
			tp_applicant_selection_process_traits taspt
			WHERE tasp.applicant_id=v_applicant_id 
			AND tasp.position_step_id_from=v_position_step_id 
			AND tasp.process_id > v_process_id
			AND tasp.process_id = taspt.process_id
			AND taspt.user_id = _userId INTO vv_trait_count;
			IF vv_trait_count=0 THEN
				
				INSERT INTO tmp_todos (
				applicant_id, applicant_name, position_id, 
				position_title, position_code, position_step_id, 
				position_step_title, due_date, action_required)
				values(v_applicant_id, v_applicant_name, v_position_id,
				v_position_title, v_position_code, v_position_step_id,
				v_position_step_title, v_process_moved_date, _todoFeedback);
				
			END IF;
		END IF;
	END IF;
	UNTIL v_noRecord = 1
END REPEAT;
CLOSE cur_my_applicants;
IF _includeRequisitionToDo=1 THEN
INSERT INTO tmp_todos (
applicant_id, applicant_name, position_id, 
position_title, position_code, position_step_id, 
position_step_title, due_date, action_required)
SELECT null, '', tp.position_id, tp.position_title, tp.position_code, traf_alias2.to_step_id, 
tras.requisition_approval_step_name,traf_alias2.feedback_date, _todoRequisitionApproval as actionrequired
FROM tp_positions tp, (
	SELECT * from (
	SELECT  traf.* FROM tp_positions tpos, 
	tp_requisition_approval_feedback traf
	WHERE  traf.position_id = tpos.position_id
	AND tpos.position_status=_positionStatusInprocess
	ORDER BY feedback_id desc
	) as traf_alias
	GROUP BY traf_alias.position_id
)as traf_alias2,
tp_requisition_approval_steps tras
WHERE 
tp.position_id = traf_alias2.position_id
AND traf_alias2.to_step_id=tras.requisition_approval_step_id
AND traf_alias2.to_user_id=_userId;
END IF;
SELECT * FROM tmp_todos GROUP BY applicant_id, applicant_name, position_id, 
position_title, position_code, position_step_id, 
position_step_title, due_date, action_required ORDER BY due_date ASC;
END$$

DELIMITER ;