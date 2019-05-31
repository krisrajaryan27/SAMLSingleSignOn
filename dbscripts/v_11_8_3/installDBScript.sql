use talentpool;

CREATE TABLE tp_on_move_to_accept (
                        applicant_id bigint(20) NOT NULL,
                        position_id bigint(20) NOT NULL,                          
                        date_created datetime NOT NULL,                        
                        scheduler_run_date datetime default NULL 
                      ) ENGINE=InnoDB DEFAULT CHARSET=latin1;