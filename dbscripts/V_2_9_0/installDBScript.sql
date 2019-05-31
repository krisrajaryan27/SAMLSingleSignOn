use talentpool;

DELETE FROM tp_recent_searches;

INSERT INTO tp_user_permissions(user_id,permission_id) VALUES(1,66);

alter table `tp_feedback_forms` add column `feedback_form_display_type` char (1)  DEFAULT '1' NOT NULL  after `feedback_form_header`;

alter table `tp_feedback_form_fields` add column `feedback_form_field_display_type` char (1)  DEFAULT '1' NOT NULL  after `feedback_form_field_comment_required`, add column `feedback_form_field_is_mandatory` char (1)  DEFAULT '0' NOT NULL  after `feedback_form_field_display_type`;

alter table `tp_feedback_form_fields` change `feedback_form_field_display_type` `feedback_form_field_display_type` char (1)  DEFAULT '1' NULL  COLLATE latin1_swedish_ci , change `feedback_form_field_is_mandatory` `feedback_form_field_is_mandatory` char (1)  DEFAULT '0' NULL  COLLATE latin1_swedish_ci;

ALTER TABLE `tp_position_steps` ADD COLUMN `is_dMaker_sameAs_asgnTo` CHAR (1)  DEFAULT '0' NOT NULL  AFTER `is_interviewer_can_confirm`;

CREATE TABLE `tp_excel_import` (            
                   `session_id` bigint(20) default NULL,     
                   `row_id` bigint(20) default NULL,         
                   `applicant_name` char(100) default NULL,  
                   `applicant_id` varchar(20) default NULL,  
                   `status` char(20) default NULL            
                 ) ENGINE=InnoDB DEFAULT CHARSET=latin1;  