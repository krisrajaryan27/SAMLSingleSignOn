use talentpool;

DELETE FROM tp_recent_searches;

INSERT INTO tp_report_levels (level_id,report_id)SELECT level_id,18 FROM tp_levels;

ALTER TABLE `tp_position_steps` 
ADD COLUMN `is_interviewer_can_confirm` char (1)  default '0' NOT NULL  after `feedback_form_id`;

CREATE TABLE `tp_reminder` (                         
               `reminder_id` bigint(20) NOT NULL auto_increment,  
               `applicant_id` bigint(20) NOT NULL,                
               `reminder_date` datetime NOT NULL,                 
               `reminder_desc` varchar(255) default NULL,         
               `user_id` bigint(20) NOT NULL,                     
               PRIMARY KEY  (`reminder_id`)                       
             )ENGINE=InnoDB DEFAULT CHARSET=latin1;
             
INSERT INTO tp_template_generic_vars (template_variable_type,template_variable) VALUES (10, 'LINK_TO_ORIGINAL_RESUME_EXTERNAL');
INSERT INTO tp_template_generic_vars (template_variable_type,template_variable) VALUES (11, 'LINK_TO_FEEDBACK_FORM_EXTERNAL');
INSERT INTO tp_template_generic_vars (template_variable_type,template_variable) VALUES (13, 'LINK_TO_REQUISITION_APPROVAL_FEEDBACK_EXTERNAL');

