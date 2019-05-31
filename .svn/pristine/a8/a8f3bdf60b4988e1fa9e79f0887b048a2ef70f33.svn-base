USE talentpool;

CREATE TABLE tp_applicant_lookup_flags (
                         lookup_step_level smallint(6) NOT NULL,  
                         flag_id smallint(6) NOT NULL              
						) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO tp_flags (flag_image,flag_text,flag_type,user_id,date_created) VALUES ('flag_green.gif','LEVEL SHORTLIST',0,1,now());
INSERT INTO tp_applicant_lookup_flags (lookup_step_level,flag_id) VALUES (0,LAST_INSERT_ID());

INSERT INTO tp_flags (flag_image,flag_text,flag_type,user_id,date_created) VALUES ('flag_yellow.gif','LEVEL SELECT',0,1,now());
INSERT INTO tp_applicant_lookup_flags (lookup_step_level,flag_id) VALUES (1,LAST_INSERT_ID());

INSERT INTO tp_flags (flag_image,flag_text,flag_type,user_id,date_created) VALUES ('flag_red.gif','LEVEL ACCEPT',0,1,now());
INSERT INTO tp_applicant_lookup_flags (lookup_step_level,flag_id) VALUES (2,LAST_INSERT_ID());

CREATE TABLE tp_applicant_lookup_check (
                         lookup_id bigint(20) NOT NULL auto_increment,  
                         applicant_id bigint(20) NOT NULL,
                         position_id bigint(20) NOT NULL,
			 			 user_id bigint(20) NOT NULL,
                         position_step_id bigint(20) NOT NULL,
                         check_lookup_db char(2) default '0',
                         is_duplicate_flag char(2) default '0',
                         lookup_id_create_date date NOT NULL,
                         check_modified_date date default NULL,
                         PRIMARY KEY  (lookup_id)
                       ) ENGINE=InnoDB DEFAULT CHARSET=latin1;