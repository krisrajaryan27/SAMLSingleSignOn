use talentpool;

INSERT INTO tp_user_activity (activity, interaction_id, interaction_type, interaction_date,
position_id, applicant_id, user_id, interaction_is_hidden, date_created)
select activity, interaction_id, interaction_type, interaction_date,
case when ta.applicant_position_id is null then (select taspp.position_id 
				from tp_applicant_selection_process taspp
				where taspp.position_step_id_to in (0, -4, -6, -7) and taspp.applicant_id=ta.applicant_id 
				order by taspp.process_moved_date desc limit 0,1)
else ta.applicant_position_id
end as position_id,
interactions.applicant_id, tu.user_id, interactions.interaction_is_hidden, now()
	from
	(
	(
		select tasp.process_id as interaction_id, 4 as interaction_type, concat((case when position_step_id_to=-2 then 'Advanced to Joined'
		else concat((case when position_step_id_to in (0, -4, -6, -7) then 'Rejected from' 
		when (position_step_id_to in (-1, -3)) 
		then 'Entered feedback for'
		when position_step_id_to = -5 then 'Confirmed attendance for'  else 'Advanced to' end),' ', tps.position_step_title) end), ' for ', td.dept_name, '-', tpp.position_title) as activity,
		tasp.process_moved_date as interaction_date, tasp.user_id as user_id, tasp.applicant_id, tasp.selection_process_is_hidden as interaction_is_hidden 
		from tp_applicant_selection_process tasp, tp_positions tpp, tp_position_steps tps, tp_departments td
		where case when tasp.position_step_id_to in (0, -1, -2, -3, -4, -5,-6, -7) then tps.position_step_id=tasp.position_step_id_from
		else tps.position_step_id=tasp.position_step_id_to end
		and tpp.position_id=tasp.position_id
		and tpp.dept_id=td.dept_id 
	)
	union
	(
		select te.email_id as interaction_id, 
		case when te.folder_id =2 then 2 else 1 end as interaction_type, 
		case when te.folder_id =2 then 'Sent Email' else 'Recieve Email' end as activity,
		case when te.folder_id =2 then email_date_send else email_date_received end as interaction_date, 
		te.user_id as user_id, te.applicant_id, te.email_is_hidden as interaction_is_hidden
		from tp_applicant_inbox_emails te
	)
	union
	(
		select tc.communication_id as interaction_id, tc.communication_type_id as interaction_type, 
		case when tc.communication_type_id = 10 then 'Called'
		     when tc.communication_type_id = 11 then 'Added Note'
		     when tc.communication_type_id = 13 then 'SMS sent'
		     end as activity,
		tc.communication_date_created as interaction_date, tc.user_id as user_id, tc.applicant_id, 
		tc.communication_is_hidden AS interaction_is_hidden
		from tp_communications tc
	)
	union
	(
		select ta.appointment_id as interaction_id, 3 as interaction_type, concat('Schdule', ' ', tps.position_step_title, ' ', 'for', ' ', tdd.dept_name, '-', tpp.position_title, ' ', 'on', ' ', date_format(ta.appointment_from_date, "%d-%b"), ' ', 'at', ' ', date_format(ta.appointment_from_date, "%I:%i %p")) as activity,
		ta.appointment_date_created as interaction_date, ta.appointment_created_by as user_id, ta.applicant_id, '0' interaction_is_hidden
		from tp_appointments ta, tp_applicants taa, tp_positions tpp, tp_departments tdd, tp_position_steps tps
		where taa.applicant_id = ta.applicant_id
		and tpp.position_id = (case when taa.applicant_position_id is null then (select taspp.position_id 
					from tp_applicant_selection_process taspp
					where taspp.position_step_id_to in (0, -4, -6, -7) and taspp.applicant_id=taa.applicant_id 
					order by taspp.process_moved_date desc limit 0,1)
		else taa.applicant_position_id
		end)
		and tps.position_step_id = ((case when taa.applicant_step_id is null then (select taspp.position_step_id_from 
					from tp_applicant_selection_process taspp
					where taspp.position_step_id_to in (0, -4, -6, -7) and taspp.applicant_id=taa.applicant_id 
					order by taspp.process_moved_date desc limit 0,1)
		else taa.applicant_step_id
		end))
		and tdd.dept_id = tpp.dept_id
	)
	union
	(
		select tasm.status_message_id as interaction_id, 12 as interaction_type, concat('Change Status to', ' ', tasm.status_message) as activity,
		tasm.date_created as interaction_date, tasm.user_id as user_id, tasm.applicant_id, tasm.status_message_is_hidden AS interaction_is_hidden 
		from tp_applicant_status_messages tasm
		where tasm.system_generated = 0
	)				
	)as interactions, tp_users tu, tp_applicants ta
	where tu.user_id = interactions.user_id
	and ta.applicant_id = interactions.applicant_id
	and datediff(now(), interaction_date) <= 7				
	and ta.applicant_id in (select tapp.applicant_id 
					from tp_applicants tapp, tp_position_steps tstp 
					where case when tapp.applicant_step_id is null 
					then (select tasps.position_step_id_from 
						from tp_applicant_selection_process tasps
						where tasps.applicant_id=tapp.applicant_id 
						and tasps.position_step_id_to in (0, -2, -4, -6, -7) 
						order by process_moved_date desc limit 0, 1)=tstp.position_step_id
						else tapp.applicant_step_id=tstp.position_step_id  end 
						and (select case when tapp.applicant_position_id is null 
						then (select taasps.position_id 
							from tp_applicant_selection_process taasps
							where taasps.applicant_id=tapp.applicant_id 
							and taasps.position_step_id_to in (0, -2, -4, -6, -7) 
							order by process_moved_date desc limit 0, 1)  
							else tapp.applicant_position_id end
						) in (select position_id from tp_positions)
					)
	order by interaction_date desc;

-- MARK INTERVIE HAPPENED/NO SHOW
CREATE TABLE tp_appointment_temp (appointment_id bigint NULL, status_id char NULL);

INSERT INTO tp_appointment_temp (appointment_id,status_id)
SELECT appointment_id, appointmentStatusId FROM (
	SELECT  tapp.appointment_id,tapp.appointment_from_date , tasp.process_moved_date,
		CASE WHEN tasp.position_step_id_to>=0 OR tasp.position_step_id_to=-5 THEN 5
		WHEN tasp.position_step_id_to=-3 OR tasp.position_step_id_to=-4 OR tasp.position_step_id_to=-6 OR tasp.position_step_id_to=-7 THEN 4
		ELSE tapp.appointment_status_id
		END AS appointmentStatusId
	FROM tp_appointments tapp
	LEFT JOIN tp_applicant_selection_process tasp ON 
		(tasp.applicant_id=tapp.applicant_id 
		AND tasp.position_id=tapp.position_id 
		AND tasp.position_step_id_from=tapp.position_step_id 
		)
	WHERE (appointment_status_id=1 OR appointment_status_id=2)
		AND tasp.process_id IS NOT NULL
		AND (
			(tapp.appointment_from_date < tasp.process_moved_date)
			OR 
			(tapp.appointment_from_date > tasp.process_moved_date 
			AND (tasp.position_step_id_to=-3
				OR
				tasp.position_step_id_to=-4
				OR
				tasp.position_step_id_to=-6
				OR
				tasp.position_step_id_to=-7
				)
			) 			
		)
	ORDER BY tasp.process_moved_date DESC
) AS X
GROUP BY appointment_id
;

UPDATE tp_appointments ta,tp_appointment_temp temp SET ta.appointment_status_id=(temp.status_id)
WHERE ta.appointment_id=temp.appointment_id;

DROP TABLE tp_appointment_temp;