DELIMITER $$

DROP PROCEDURE IF EXISTS `RESTORE_SNAPSHOT`$$

CREATE PROCEDURE `RESTORE_SNAPSHOT` ()
BEGIN
DECLARE offsetdays INT DEFAULT 0;
SELECT to_days(now()) - to_days(snapshot_date) INTO offsetdays FROM snapshot_date;

UPDATE tp_applicant_inbox_emails SET 
email_date_send =DATE_ADD(email_date_send, INTERVAL offsetdays DAY),
email_date_received=DATE_ADD(email_date_received, INTERVAL offsetdays DAY);

UPDATE tp_applicant_selection_process SET 
process_date_created=DATE_ADD(process_date_created, INTERVAL offsetdays DAY),
process_moved_date=DATE_ADD(process_moved_date, INTERVAL offsetdays DAY);

UPDATE tp_applicant_status_messages SET 
date_created=DATE_ADD(date_created, INTERVAL offsetdays DAY);

UPDATE tp_applicants SET 
applicant_working_since=DATE_ADD(applicant_working_since, INTERVAL offsetdays DAY),
applicant_date_created=DATE_ADD(applicant_date_created, INTERVAL offsetdays DAY),
applicant_date_joined=DATE_ADD(applicant_date_joined, INTERVAL offsetdays DAY),
resume_updated_date=DATE_ADD(resume_updated_date, INTERVAL offsetdays DAY),
resume_locked_date = DATE_ADD(resume_locked_date, INTERVAL offsetdays DAY);

UPDATE tp_appointments SET 
appointment_from_date=DATE_ADD(appointment_from_date, INTERVAL offsetdays DAY),
appointment_to_date=DATE_ADD(appointment_to_date, INTERVAL offsetdays DAY),
appointment_date_created=DATE_ADD(appointment_date_created, INTERVAL offsetdays DAY),
appointment_date_modified=DATE_ADD(appointment_date_modified, INTERVAL offsetdays DAY);

UPDATE tp_appointments_notifications SET 
appointment_from_date=DATE_ADD(appointment_from_date, INTERVAL offsetdays DAY),
appointment_to_date=DATE_ADD(appointment_to_date, INTERVAL offsetdays DAY),
appointment_date_created=DATE_ADD(appointment_date_created, INTERVAL offsetdays DAY),
appointment_from_date_new=DATE_ADD(appointment_from_date_new, INTERVAL offsetdays DAY),
appointment_to_date_new=DATE_ADD(appointment_to_date_new, INTERVAL offsetdays DAY),
appointment_processed_date=DATE_ADD(appointment_processed_date, INTERVAL offsetdays DAY),
appointment_date_modified=DATE_ADD(appointment_date_modified, INTERVAL offsetdays DAY);

UPDATE tp_communications SET 
communication_date=DATE_ADD(communication_date, INTERVAL offsetdays DAY),
communication_date_created=DATE_ADD(communication_date_created, INTERVAL offsetdays DAY);

UPDATE tp_inbox_emails SET 
email_date_send=DATE_ADD(email_date_send, INTERVAL offsetdays DAY),
email_date_received=DATE_ADD(email_date_received, INTERVAL offsetdays DAY);

UPDATE tp_last_viewed SET 
entity_date_viewed=DATE_ADD(entity_date_viewed, INTERVAL offsetdays DAY);

UPDATE tp_messages SET 
message_date=DATE_ADD(message_date, INTERVAL offsetdays DAY);

UPDATE tp_outbound SET 
send_date=DATE_ADD(send_date, INTERVAL offsetdays DAY),
sent_date=DATE_ADD(sent_date, INTERVAL offsetdays DAY);

UPDATE tp_positions SET 
position_date_expiry=DATE_ADD(position_date_expiry, INTERVAL offsetdays DAY),
position_date_created=DATE_ADD(position_date_created, INTERVAL offsetdays DAY),
position_date_deleted=DATE_ADD(position_date_deleted, INTERVAL offsetdays DAY);

UPDATE tp_recent_searches SET 
date_searched=DATE_ADD(date_searched, INTERVAL offsetdays DAY);

UPDATE tp_templates SET 
template_date_created=DATE_ADD(template_date_created, INTERVAL offsetdays DAY);

UPDATE tp_tmp_attachments SET 
attachment_date=DATE_ADD(attachment_date, INTERVAL offsetdays DAY);

UPDATE tp_users SET 
USER_LAST_LOGIN=DATE_ADD(USER_LAST_LOGIN, INTERVAL offsetdays DAY);

UPDATE tp_applicant_documents SET date_created = DATE_ADD(date_created, INTERVAL offsetdays DAY);

UPDATE tp_costs SET 
cost_paid_date  = DATE_ADD(cost_paid_date, INTERVAL offsetdays DAY),
date_created= DATE_ADD(date_created, INTERVAL offsetdays DAY),
cost_date_from= DATE_ADD(cost_date_from, INTERVAL offsetdays DAY),
cost_date_to= DATE_ADD(cost_date_to, INTERVAL offsetdays DAY);

UPDATE tp_requisition_approval_steps SET 
requisition_approval_step_date_created = DATE_ADD(requisition_approval_step_date_created, INTERVAL offsetdays DAY);

UPDATE tp_requisition_approval_feedback SET 
feedback_date = DATE_ADD(feedback_date, INTERVAL offsetdays DAY);


END$$

DELIMITER ;