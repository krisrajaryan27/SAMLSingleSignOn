DELIMITER $$

DROP PROCEDURE IF EXISTS `PROC_GET_APPLICANT_SHORTLIST_SUMMARY`$$

CREATE PROCEDURE `PROC_GET_APPLICANT_SHORTLIST_SUMMARY`(
	IN _applicantId BIGINT, IN _positionId INT, IN  _stepId INT, IN  _appointmentStatusNoShow INT ,
	IN _onlyActionDetails INT
)
BEGIN
DECLARE v_noRecord INT DEFAULT 0;
DECLARE v_process_id bigint(20);
DECLARE v_process_date_created datetime;
DECLARE v_position_title varchar(100);
DECLARE v_position_step_title varchar(100);
DECLARE v_position_step_isscheduled char(1); 
DECLARE v_appointment_from_date datetime;
DECLARE v_appointment_id bigint(20);
DECLARE vv_process_id int(11);
DECLARE vv_position_step_id_to int(11);
DECLARE v_action_type varchar(20);
DECLARE v_users_responsible varchar(250);
DECLARE v_status_message varchar(300);
DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_noRecord = 1;
/* debug tables */
/*
DROP TABLE IF EXISTS tmp_logs;
CREATE TEMPORARY TABLE tmp_logs (
	log_id bigint(20),                            
	log_desc varchar(250)
) ENGINE=MyISAM;
*/
/* select process_id and date_created when candidate is moved to current step */
SELECT process_id, process_date_created FROM 
	(SELECT  process_id, process_date_created FROM tp_applicant_selection_process
	WHERE position_step_id_to = _stepId AND applicant_id = _applicantId 
	ORDER BY process_id DESC) as x
LIMIT 0,1 INTO  v_process_id, v_process_date_created;
/*
insert into tmp_logs values(v_process_id,v_process_date_created);
*/
/* select position details */
IF _onlyActionDetails=0 THEN
	SELECT tp.position_title, tps.position_step_title, tps.position_step_isscheduled
	FROM tp_positions tp, tp_position_steps tps
	WHERE tp.position_id = tps.position_id AND tp.position_id = _positionId 
	AND tps.position_step_id = _stepId
	INTO v_position_title, v_position_step_title, v_position_step_isscheduled;
ELSE
	SELECT position_step_isscheduled FROM tp_position_steps WHERE position_step_id = _stepId
	INTO v_position_step_isscheduled;
END IF;
/*
insert into tmp_logs values('0',CONCAT(v_position_title,' - ', v_position_step_title,' - ', v_position_step_isscheduled));
*/
IF v_position_step_isscheduled = 1 THEN
	SET v_appointment_from_date = null;
	SET v_appointment_id = null;
		
	SELECT appointment_id, appointment_from_date
	FROM (SELECT * from tp_appointments order by appointment_from_date desc) as tapo 
	WHERE tapo.appointment_date_created > v_process_date_created 
	AND tapo.position_step_id = _stepId 
	AND tapo.appointment_status_id !=_appointmentStatusNoShow 
	AND tapo.applicant_id=_applicantId LIMIT 0,1
	INTO  v_appointment_id, v_appointment_from_date;
	IF v_noRecord =1 OR v_appointment_from_date IS null THEN
		SET v_noRecord =0;	
		/*
		insert into tmp_logs values('0','appointment not found, schedule interview');
		*/
		SET v_action_type ='schedule'; /* schedule interview action */
		/* scheduling entry */
		SELECT group_concat(distinct(iu.USER_FNAME) ORDER BY iu.USER_FNAME SEPARATOR ', ') 
		FROM tp_users iu ,tp_position_step_users ipsu
		WHERE iu.user_id = ipsu.user_id 
		AND ipsu.is_responsible_for_scheduling =1 
		AND ipsu.position_step_id =_stepId
		INTO v_users_responsible;
		
	ELSE
		IF v_appointment_from_date > now() THEN
			/* conduct interview entry */
			/*
			insert into tmp_logs values('0','appointment not passed, conduct entry');
			*/
			SET v_action_type ='conduct'; /* conduct interview action */
			SELECT group_concat(distinct(iu.USER_FNAME) ORDER BY iu.USER_FNAME SEPARATOR ', ') 
			FROM tp_users iu ,tp_position_step_users ipsu
			WHERE iu.user_id = ipsu.user_id 
			AND ipsu.is_responsible_for_scheduling =0
			AND ipsu.user_id in (SELECT attendee_id FROM tp_appointment_attendees
						WHERE appointment_id = v_appointment_id)
			AND ipsu.is_authorized_to_move=0
			AND ipsu.position_step_id =_stepId
			INTO v_users_responsible;
			
		ELSE
			/* select traits */
			/*
			insert into tmp_logs values('0','select if traits exists');
			*/
			SELECT process_id, position_step_id_to FROM tp_applicant_selection_process
			WHERE process_id > v_process_id 
			AND position_step_id_from = _stepId 
			AND applicant_id = _applicantId
			INTO vv_process_id, vv_position_step_id_to;
			
			IF v_noRecord =1 OR vv_process_id IS null THEN
				SET v_noRecord = 0;
				/* confirm appointment entry */
				/*
				insert into tmp_logs values('0','confirm appointment required');
				*/
				SET v_action_type ='confirm'; /* confirm interview action */
				SELECT group_concat(distinct(iu.USER_FNAME) ORDER BY iu.USER_FNAME SEPARATOR ', ') 
				FROM tp_users iu ,tp_position_step_users ipsu
				WHERE iu.user_id = ipsu.user_id 
				AND ipsu.is_responsible_for_scheduling =1 
				AND ipsu.position_step_id =_stepId
				INTO v_users_responsible;
			ELSE
				/* feedback required entry */
				/*
				insert into tmp_logs values('0','feedback required entry required');
				*/
				
				SELECT group_concat(distinct(iu.USER_FNAME) ORDER BY iu.USER_FNAME SEPARATOR ', ') 
				FROM tp_users iu ,tp_position_step_users ipsu
				WHERE iu.user_id = ipsu.user_id 
				AND ipsu.is_responsible_for_scheduling =0
				AND( 
					(ipsu.user_id in (SELECT attendee_id FROM tp_appointment_attendees
						WHERE appointment_id = v_appointment_id)
					) OR ipsu.is_authorized_to_move=1
				)
				AND ipsu.user_id NOT IN(SELECT distinct(user_id) FROM 
				tp_applicant_selection_process_traits WHERE process_id=vv_process_id)
				AND ipsu.position_step_id =_stepId
				INTO v_users_responsible;
				
				IF v_users_responsible IS NULL THEN
					SET v_action_type ='hold'; /* on hold*/
				ELSE
					SET v_action_type ='feedback';	/* feedback required */
				END IF;
			END IF;
		END IF;
	END IF;
ELSE
	/* feedback required entry */
	/*
	insert into tmp_logs values('0','feedback required entry required');
	*/
	SELECT group_concat(distinct(iu.USER_FNAME) ORDER BY iu.USER_FNAME SEPARATOR ', ') 
	FROM tp_users iu ,tp_position_step_users ipsu
	WHERE iu.user_id = ipsu.user_id 
	AND ipsu.is_responsible_for_scheduling =0
	AND ipsu.is_authorized_to_move=1
	AND ipsu.position_step_id =_stepId
	AND ipsu.user_id NOT IN(SELECT distinct(taspt.user_id) FROM tp_applicant_selection_process tasp,
	tp_applicant_selection_process_traits taspt WHERE 
	tasp.process_id=taspt.process_id AND tasp.applicant_id=_applicantId 
	AND tasp.position_step_id_from=_stepId AND tasp.process_id>v_process_id)
	INTO v_users_responsible;
	IF v_users_responsible IS NULL THEN
		SET v_action_type ='hold'; /* on hold*/
	ELSE
		SET v_action_type ='feedback';	/* feedback required */
	END IF;
END IF;
IF _onlyActionDetails=0 THEN
	SELECT status_message FROM tp_applicant_status_messages WHERE applicant_id = _applicantId 
	ORDER BY status_message_id DESC LIMIT 0,1 INTO v_status_message;
END IF;
/*
select * from tmp_logs;
*/
SELECT v_position_title as position_title, v_position_step_title as position_step_title, 
v_users_responsible as users_responsible, v_action_type as action_required, 
v_status_message as status_message, v_position_step_isscheduled as position_step_isscheduled,
v_appointment_from_date as appointment_from_date; 
END$$

DELIMITER ;