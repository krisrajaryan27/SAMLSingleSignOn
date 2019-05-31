use talentpool;

CREATE TABLE IF NOT EXISTS tp_customization_reports( 
	report_id SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT , 
	report_name VARCHAR(50) NOT NULL , 
	report_label VARCHAR(50) NOT NULL , 
	report_jrxml VARCHAR(100) NOT NULL , 
	report_jsp VARCHAR(50) NOT NULL , 
	PRIMARY KEY (report_id))  ;

CREATE TABLE IF NOT EXISTS `tp_naukri_industry_codes` (                                                                                                                                             
                `industry_id` varchar(20) NOT NULL,                                                                                                                       
                `industry` varchar(70) NOT NULL,
		PRIMARY KEY  (`industry_id`));

CREATE TABLE IF NOT EXISTS `tp_naukri_location_codes` (                                                                                                                                             
                `city_id` varchar(20) NOT NULL,                                                                                                                       
                `city` varchar(70) NOT NULL,
		PRIMARY KEY  (`city_id`));

CREATE TABLE IF NOT EXISTS `tp_naukri_country_codes` (                                                                                                                                             
                `country_id` varchar(20) NOT NULL,                                                                                                                       
                `country` varchar(70) NOT NULL,
		PRIMARY KEY  (`country_id`));

CREATE TABLE IF NOT EXISTS `tp_naukri_functional_area_codes` (                                                                                                                                             
                `farea_id` varchar(20) NOT NULL,                                                                                                                       
                `farea` varchar(70) NOT NULL,
		PRIMARY KEY  (`farea_id`));

CREATE TABLE IF NOT EXISTS `tp_naukri_role_codes` (                                                                                                                                             
                `role_id` varchar(20) NOT NULL,                                                                                                                       
                `role_name` varchar(70) NOT NULL);

CREATE TABLE IF NOT EXISTS `tp_position_naukri_fields`(
		position_id bigint(20) NOT NULL,
		contact_person_name varchar(70),
		job_industry_code varchar(10),
		job_function_code varchar(10),
		job_role_code varchar(10),
		job_keywords varchar(100),
		country varchar(50),
		minimum_salary varchar(50),
		maximum_salary varchar(50),
		benefits_description varchar(100),
		display_salary varchar(50),
		desired_candidate_summary_text varchar(100),
		contact_person_email varchar(50),
		apply_by_web_url varchar(50),
		job_feed_response_email varchar(50),
		salary_currency varchar(50),
		PRIMARY KEY  (`position_id`)
);

call PROC_ADD_COLUMN_IF_NOT_EXISTS('tp_position_screen_description', 'field_employee_rank','TINYINT(4) NULL AFTER field_employee_show');
alter table tp_position_screen_description add column is_field_naukri char(1) NOT NULL default '0' after field_employee_rank;


alter table tp_positions add column is_published_to_naukri char(1) NOT NULL default '0' after approval_user_ids;



CREATE TABLE IF NOT EXISTS `tp_pg_education_codes` (                                                                                                                                             
                `course_id` varchar(20) NOT NULL,                                                                                                                       
                `course` varchar(70) NOT NULL);


CREATE TABLE IF NOT EXISTS `tp_pg_education_specialization` (                                                                                                                                             
                `specialization_id` varchar(20) NOT NULL,                                                                                                                       
                `specialization` varchar(70) NOT NULL,
		`course_id` varchar(20) NOT NULL);


		
CREATE TABLE IF NOT EXISTS `tp_naukri_experience` (  
                 `min_exp` varchar(20) NOT NULL,     
                 `max_allowed_exp` varchar(20) NOT NULL         
               ); 
CREATE TABLE IF NOT EXISTS tp_applicant_applied_position_resume_mapping
	(applicant_id bigint(20), position_id bigint(20), applicant_original_resume_path varchar(100),
	applicant_original_doc_path varchar(100),
	applied_status varchar(1) default 1,
	UNIQUE KEY (applicant_id, position_id),
	foreign key(position_id) references tp_positions(position_id),
	foreign key (applicant_id) references tp_applicants(applicant_id));


ALTER TABLE tp_applicant_text_resumes ADD COLUMN applied_position_id bigint(20);
ALTER TABLE tp_applicant_text_resumes ADD FOREIGN KEY (applied_position_id) REFERENCES tp_positions(position_id);
CREATE UNIQUE INDEX unique_applicant_position ON tp_applicant_text_resumes (applicant_id, applied_position_id);
alter table `tp_appointments` add column `interview_mode` varchar(200) CHARSET latin1 COLLATE latin1_swedish_ci NULL after `appointment_date_modified`, add column `details_interview_mode` varchar(1000) CHARSET latin1 COLLATE latin1_swedish_ci NULL after `interview_mode`;
alter table `tp_appointments_notifications` add column `interview_mode` varchar(100) CHARSET latin1 COLLATE latin1_swedish_ci NULL after `remind_attendee`, add column `details_interview_mode` varchar(1000) CHARSET latin1 COLLATE latin1_swedish_ci NULL after `interview_mode`;

alter table `tp_applicant_joining_history` add column `joining_Bonus` varchar(50) CHARSET latin1 COLLATE latin1_swedish_ci NULL after `budget_item_id`, add column `variable_Offered` varchar(50) CHARSET latin1 COLLATE latin1_swedish_ci NULL after `joining_Bonus`;
alter table `tp_applicants` add column `joining_Bonus` varchar(70) CHARSET latin1 COLLATE latin1_swedish_ci NULL after `resume_type_id`, add column `variable_Offered` varchar(70) CHARSET latin1 COLLATE latin1_swedish_ci NULL after `joining_Bonus`;
update `tp_application_properties` set `application_property`='applicant_interview_mode',`application_property_value`=' ,Skype,Telephonic,face_2_face,VC_Interview' where `application_property`='applicant_interview_mode' and `application_property_value`='Skype,Telephonic,face_2_face,VC_Interview';

call PROC_POSITION_NAUKRI_RANK();

CREATE TABLE IF NOT EXISTS `tp_ug_education_codes` (                                                                                                                                             
                `course_id` varchar(20) NOT NULL,                                                                                                                       
                `course` varchar(70) NOT NULL);

CREATE TABLE IF NOT EXISTS `tp_ug_education_specialization` (                                                                                                                                             
                `specialization_id` varchar(20) NOT NULL,                                                                                                                       
                `specialization` varchar(70) NOT NULL,
		`course_id` varchar(20) NOT NULL);


CREATE TABLE IF NOT EXISTS `tp_ppg_education_codes` (                                                                                                                                             
                `course_id` varchar(20) NOT NULL,                                                                                                                       
                `course` varchar(70) NOT NULL);


CREATE TABLE IF NOT EXISTS `tp_ppg_education_specialization` (                                                                                                                                             
                `specialization_id` varchar(20) NOT NULL,                                                                                                                       
                `specialization` varchar(70) NOT NULL,
		`course_id` varchar(20) NOT NULL);
		
-- sbi customization

ALTER TABLE tp_cr_candidate_master 
ADD COLUMN custom_field_21 VARCHAR(250) CHARSET latin1 COLLATE latin1_swedish_ci NULL AFTER custom_field_20, 
ADD COLUMN custom_field_22 VARCHAR(250) CHARSET latin1 COLLATE latin1_swedish_ci NULL AFTER custom_field_21, 
ADD COLUMN custom_field_23 VARCHAR(250) CHARSET latin1 COLLATE latin1_swedish_ci NULL AFTER custom_field_22, 
ADD COLUMN custom_field_24 VARCHAR(250) CHARSET latin1 COLLATE latin1_swedish_ci NULL AFTER custom_field_23, 
ADD COLUMN custom_field_25 VARCHAR(250) CHARSET latin1 COLLATE latin1_swedish_ci NULL AFTER custom_field_24, 
ADD COLUMN custom_field_26 VARCHAR(250) CHARSET latin1 COLLATE latin1_swedish_ci NULL AFTER custom_field_25, 
ADD COLUMN custom_field_27 VARCHAR(250) CHARSET latin1 COLLATE latin1_swedish_ci NULL AFTER custom_field_26, 
ADD COLUMN custom_field_28 VARCHAR(250) CHARSET latin1 COLLATE latin1_swedish_ci NULL AFTER custom_field_27, 
ADD COLUMN custom_field_29 VARCHAR(250) CHARSET latin1 COLLATE latin1_swedish_ci NULL AFTER custom_field_28, 
ADD COLUMN custom_field_30 VARCHAR(250) CHARSET latin1 COLLATE latin1_swedish_ci NULL AFTER custom_field_29;

ALTER TABLE tp_cr_candidate_master 
ADD COLUMN offered_ctc VARCHAR(50) CHARSET latin1 COLLATE latin1_swedish_ci NULL AFTER expected_ctc, 
ADD COLUMN level_offered VARCHAR(20) CHARSET latin1 COLLATE latin1_swedish_ci NULL AFTER offered_ctc,
ADD COLUMN designation_offered VARCHAR(50) CHARSET latin1 COLLATE latin1_swedish_ci NULL AFTER level_offered;

ALTER TABLE tp_cr_candidate_master 
ADD COLUMN offer_code VARCHAR(40) CHARSET latin1 COLLATE latin1_swedish_ci NULL AFTER designation_offered, 
ADD COLUMN offer_date DATE NULL AFTER offer_code;

ALTER TABLE tp_cr_candidate_master 
ADD COLUMN degrees VARCHAR(250) CHARSET latin1 COLLATE latin1_swedish_ci NULL AFTER institute_name, 
ADD COLUMN employers VARCHAR(250) CHARSET latin1 COLLATE latin1_swedish_ci NULL AFTER degrees;

ALTER TABLE tp_cr_candidate_master 
ADD COLUMN employer3 VARCHAR(250) CHARSET latin1 COLLATE latin1_swedish_ci NULL AFTER employer2,
CHANGE employers employer2 VARCHAR(250) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL;
-- sbi customization

-- Aspire Customization


CREATE TABLE IF NOT EXISTS `tp_positions_dropped_info` (                                                                                            
                             `position_id` bigint(20) NOT NULL,                                                                                                  
                             `drop_reason` char(1) NOT NULL,                                                                                                     
                             `drop_comment` varchar(250) default NULL,                                                                                           
                             `dropped_by` bigint(20) NOT NULL,                                                                                               
                             `date_dropped` datetime NOT NULL,                                                                                                   
                             KEY `FK_tp_positions_dropped_info` (`position_id`),                                                                                 
                             KEY `FK_tp_positions_dropped_info_user` (`dropped_by`),                                                                             
                             CONSTRAINT `FK_tp_positions_dropped_info` FOREIGN KEY (`position_id`) REFERENCES `tp_positions` (`position_id`) ON DELETE CASCADE,  
                             CONSTRAINT `FK_tp_positions_dropped_info_user` FOREIGN KEY (`dropped_by`) REFERENCES `tp_users` (`USER_ID`)                         
                           );
-- Aspire customization

-- 3dplm customization

ALTER TABLE tp_positions ADD COLUMN employee_can_email CHAR(1) DEFAULT '0' 
NOT NULL AFTER position_clone;

-- 3dplm customization
alter table `tp_positions` add column `position_date_opend` timestamp NULL after `is_published_to_naukri`;

alter table tp_naukri_experience add primary key (min_exp);

-- sbi 
alter table `tp_applicants` add column `category` varchar(25) CHARSET latin1 COLLATE latin1_swedish_ci NULL after `variable_Offered`, add column `sub_category` varchar(40) CHARSET latin1 COLLATE latin1_swedish_ci NULL after `category`;
alter table `tp_applicants` add column `is_employee_apply` varchar(40) CHARSET latin1 COLLATE latin1_swedish_ci NULL after `sub_category`;

-- applied date 
ALTER TABLE `tp_applicant_applied_position_resume_mapping` add column `applied_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE `tp_position_step_users` change `user_id` `user_id` varchar(100) NOT NULL;

CREATE TABLE tp_applicant_sched_queue (
	id int NOT NULL AUTO_INCREMENT,
	applicant_id bigint(20) NOT null, 
    index_status varchar(1) NOT null,
	PRIMARY KEY(id),
        FOREIGN KEY(applicant_id) REFERENCES tp_applicants (applicant_id) 
        ON DELETE CASCADE
);
alter table tp_applicant_sched_queue add constraint uni_1 unique(applicant_id);
