DELIMITER $$

DROP PROCEDURE IF EXISTS `PROC_GET_TODO_LIST`$$

CREATE PROCEDURE `PROC_GET_TODO_LIST`(
	IN _userId BIGINT, IN _positionStatusOpen INT, IN  _appointmentStatusNoShow INT,
	IN _appNotJoined INT, IN _positionStepLevelApprove INT, IN _positionStepLevelSelect INT,
	IN _userAuthorisedToMove INT,	IN _positionStatusInprocess INT, IN _stepIdOnHold INT
)
BEGIN

SELECT * FROM
(
	SELECT 
		tasp.applicant_id, tasp.applicant_name, tasp.position_id, 
		tasp.position_title, tasp.position_code, tasp.position_step_id, tasp.position_step_title, 
		tasp.position_step_isscheduled, tasp.is_interviewer_can_confirm, 
		tasp.process_moved_date , tasp.process_date_created, tasp.process_id, 
		tasp.position_step_id_from, tasp.position_step_id_to,
		tasp.appointment_id, tasp.appointment_from_date, tasp.attendee_id,
		tasp.is_responsible_for_scheduling, 
		tasp.is_authorized_to_move,
		tasp.feedback_form_field_id,
		(
			SELECT COUNT(*) 
			FROM tp_appointments itap, tp_appointment_attendees itaa , tp_position_step_users itpsu
			WHERE itap.appointment_id=itaa.appointment_id 
			AND itaa.attendee_id=itpsu.user_id 
			AND itap.position_step_id =itpsu.position_step_id
			AND itpsu.is_authorized_to_move=_userAuthorisedToMove
			AND itap.appointment_id=tasp.appointment_id
			GROUP BY itap.appointment_id
		 ) AS no_of_decision_maker_attendees
	FROM
		(
		SELECT
			tasp.applicant_id, tasp.applicant_name, tasp.position_id, 
			tasp.position_title, tasp.position_code, tasp.position_step_id, tasp.position_step_title, 
			tasp.position_step_isscheduled, tasp.is_interviewer_can_confirm, 
			tasp.process_moved_date , tasp.process_date_created, tasp.process_id, 
			tasp.position_step_id_from, tasp.position_step_id_to,
			tasp.appointment_id, tasp.appointment_from_date, tasp.attendee_id,
			MAX(tasp.is_responsible_for_scheduling) as is_responsible_for_scheduling, 
			MAX(tasp.is_authorized_to_move) as is_authorized_to_move,
			MAX(tasp.feedback_form_field_id) as feedback_form_field_id
			
		FROM
			
			(
				SELECT tasp.applicant_id, tasp.applicant_name, tasp.position_id, 
					tp.position_title, tp.position_code, tps.position_step_id, tps.position_step_title, 
					tps.position_step_isscheduled, tps.is_interviewer_can_confirm, 
					tasp.process_moved_date , tasp.process_date_created, tasp.process_id, 
					tasp.position_step_id_from, tasp.position_step_id_to,
					tapo.appointment_id, tapo.appointment_from_date, taa.attendee_id,
					tpsu.is_responsible_for_scheduling, tpsu.is_authorized_to_move,
					taspt.feedback_form_field_id
				FROM
				
				(
					SELECT tasp.applicant_id, tasp.applicant_name,
						tasp.process_id, tasp.position_step_id_from, tasp.position_step_id_to, 
						CASE WHEN  position_step_id_to <0 THEN position_step_id_from ELSE position_step_id_to END AS current_step_id,
						tasp.process_date_created, tasp.process_moved_date, tasp.position_id
					FROM
					(
						SELECT 
						ta.applicant_id, ta.applicant_name,
						taspx.process_id, taspx.position_step_id_from, taspx.position_step_id_to, 
						taspx.process_date_created, taspx.position_id, taspx.process_moved_date
						FROM tp_applicants ta, tp_applicant_selection_process taspx
						WHERE 
						ta.applicant_id=taspx.applicant_id
						AND ta.applicant_position_id=taspx.position_id
						AND ta.applicant_joined=_appNotJoined
						ORDER BY taspx.process_id DESC
					) as tasp
					GROUP BY applicant_id HAVING position_step_id_to!=_stepIdOnHold
				) as tasp
				LEFT JOIN tp_appointments tapo ON (tasp.position_id= tapo.position_id AND tasp.current_step_id=tapo.position_step_id 
				AND tasp.applicant_id = tapo.applicant_id 
				AND tapo.appointment_date_created > (SELECT itasp.process_date_created  
					FROM tp_applicant_selection_process itasp 
					WHERE itasp.process_id<tasp.process_id 
					AND itasp.applicant_id=tasp.applicant_id
					AND itasp.position_id=tasp.position_id
					ORDER BY itasp.process_id DESC
					LIMIT 0,1)
				AND tapo.appointment_status_id !=_appointmentStatusNoShow)
				LEFT JOIN tp_appointment_attendees taa ON (tapo.appointment_id = taa.appointment_id AND taa.attendee_id=_userId )
				LEFT JOIN tp_applicant_selection_process_traits taspt ON(tasp.process_id=taspt.process_id AND taspt.user_id=_userId)
				,tp_positions tp, tp_position_steps tps, tp_position_step_users tpsu
				WHERE 
				tasp.position_id=tp.position_id
				AND tasp.current_step_id=tps.position_step_id
				AND tps.position_step_id= tpsu.position_step_id
				AND tpsu.user_id= _userId  AND tp.position_status= _positionStatusOpen
				AND (tps.position_step_level= _positionStepLevelSelect OR tps.position_step_level= _positionStepLevelApprove)
			) as tasp
		GROUP BY applicant_id
	) as tasp

UNION

	SELECT null, '', tp.position_id, tp.position_title, tp.position_code, traf_alias2.to_step_id AS position_step_id, 
	tras.requisition_approval_step_name AS position_step_title,
	null,null,null, traf_alias2.feedback_date AS process_date_created, null,
	null,null,null,null,null,null,null,null,null
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
	AND traf_alias2.to_user_id=_userId
) AS x

ORDER BY process_date_created;
END$$

DELIMITER ;