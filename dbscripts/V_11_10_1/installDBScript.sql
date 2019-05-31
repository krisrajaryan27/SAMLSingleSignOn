use talentpool;

-- DELETE FROM qrtz_simple_triggers;
-- DELETE FROM qrtz_triggers;
-- DELETE FROM qrtz_job_details;

DROP TABLE IF EXISTS tp_on_move_to_accept;
CREATE TABLE tp_on_step_level_change(
                        applicant_id bigint(20) NOT NULL,
                        position_id bigint(20) NOT NULL,                          
                        step_level_id char(1) NOT NULL,
                        date_created datetime NOT NULL,                        
                        scheduler_run_date datetime default NULL 
                      ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
