use talentpool;
alter table `tp_cr_candidate_master` 
	change `current_ctc` `current_ctc` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL , 
	change `expected_ctc` `expected_ctc` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL , 
	change `date_of_birth` `date_of_birth` varchar(70) NULL , 
	change `passport_number` `passport_number` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL ;

alter table `tp_applicant_current_details` 
	change `current_basic` `current_basic` varchar(50) NULL ;

alter table `tp_applicant_joining_history` 
	change `offered_ctc` `offered_ctc` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL ;
 
alter table `tp_bulk_import_sessions` 
	change `current_ctc` `current_ctc` varchar(50) NULL , 
	change `expected_ctc` `expected_ctc` varchar(50) NULL ;
 
alter table `tp_applicants` 
	change `current_ctc` `current_ctc` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL , 
	change `expected_ctc` `expected_ctc` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL , 
	change `offered_basic` `offered_basic` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL , 
	change `offered_ctc` `offered_ctc` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL , 
	change `date_of_birth` `date_of_birth` varchar(50) NULL ;
 
alter table `tp_applicants` 
	change `passport_number` `passport_number` varchar(70) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL ;
	

alter table tp_sources add column source_cv_limit varchar(10) NULL after source_blacklisted;

