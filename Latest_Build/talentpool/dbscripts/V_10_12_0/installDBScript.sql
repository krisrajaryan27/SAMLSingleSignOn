use talentpool;

DELETE FROM tp_recent_searches;

alter table tp_reports add column `report_file_path` varchar (250)   NULL  after `user_id`, 
	add column `sheet_index` char (10)   NULL  after `report_file_path`, 
	add column `row_index` char (10)   NULL  after `sheet_index`;

alter table `tp_template_types` change `template_type` `template_type` varchar (100) NOT NULL COLLATE latin1_swedish_ci;

delete from tp_template_types where template_type_id=36;
delete from tp_template_vars where template_type_id=36;
delete from tp_templates where template_code='appointmentReminderVendor';
delete from tp_application_properties where application_property='send_reminder_to_vendor';

insert into tp_template_types (template_type_id,template_type) values (36,'Appointment Reminder - Vendor');
insert into tp_template_vars (template_type_id, template_variable_type) values (36, '1');
insert into tp_template_vars (template_type_id, template_variable_type) values (36, '3');
insert into tp_template_vars (template_type_id, template_variable_type) values (36, '10');
insert into tp_templates (template_code, template_name, template_subject_file, template_content_file, template_auto, template_format, 
	template_private, template_type_id, template_is_default, template_date_created,template_is_save_as_draft, do_show_save_as_draft_option, user_id ) 
	values ('appointmentReminderVendor', 'Appointment Reminder to Vendor', 'appointmentReminderVendorSubject.vm', 
	'appointmentReminderVendorContent.vm', 0, 0, 0, 36, 1, now(), 0, 0, 1);

insert into tp_application_properties (application_property, application_property_value)
	values ('send_reminder_to_vendor', 0);
	
delete from tp_template_types where template_type_id=37;
delete from tp_template_vars where template_type_id=37;
delete from tp_templates where template_code='referentEmployeeInSelectionProcessNotification';
delete from tp_application_properties where application_property='send_referent_employee_in_selection_process_notification';

insert into tp_template_types (template_type_id,template_type) values (37,'Referent Employee in Selection Process Notification');
insert into tp_template_vars (template_type_id, template_variable_type) values (37, '3');
insert into tp_template_vars (template_type_id, template_variable_type) values (37, '4');
insert into tp_template_vars (template_type_id, template_variable_type) values (37, '10');
insert into tp_template_vars (template_type_id, template_variable_type) values (37, '15');
insert into tp_template_vars (template_type_id, template_variable_type) values (37, '18');

insert into tp_templates (template_code, template_name, template_subject_file, template_content_file, template_auto, template_format, 
	template_private, template_type_id, template_is_default, template_date_created,template_is_save_as_draft, do_show_save_as_draft_option, user_id ) 
	values ('referentEmployeeInSelectionProcessNotification', 'Referent Employee in Selection Process Notification', 'referentEmployeeInSelectionProcessNotificationSubject.vm', 
	'referentEmployeeInSelectionProcessNotificationContent.vm', 0, 0, 0, 37, 1, now(), 0, 0, 1);

insert into tp_application_properties (application_property, application_property_value)
	values ('send_referent_employee_in_selection_process_notification', 0);
	
CREATE TABLE `tp_multiple_selects` (                                                                 
              `select_id` int(11) NOT NULL auto_increment,                                              
              `select_title` varchar(255) NOT NULL,                                                     
              `date_created` date NOT NULL,                                                             
              `user_id` bigint(20) NOT NULL,                                                            
              `select_status` char(1) NOT NULL default '1',                                             
              PRIMARY KEY  (`select_id`),                                                               
              KEY `FK_tp_multiple_selects` (`user_id`),                                                          
              CONSTRAINT `tp_multiple_selects_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `tp_users` (`USER_ID`)  
            ) ENGINE=InnoDB DEFAULT CHARSET=latin1;     
			
CREATE TABLE `tp_multiple_select_fields` (                                                                       
                    `select_field_id` int(11) NOT NULL auto_increment,                                                    
                    `select_id` int(11) NOT NULL,                                                                         
                    `select_field_desc` varchar(255) NOT NULL,                                                            
                    `select_field_status` char(1) NOT NULL default '1',                                                   
                    `select_field_rank` smallint(6) NOT NULL,                                                                   
                    PRIMARY KEY  (`select_field_id`),                                                                     
                    KEY `FK_tp_multiple_select_fields` (`select_id`),                                                              
                    CONSTRAINT `tp_multiple_select_fields_ibfk_1` FOREIGN KEY (`select_id`) REFERENCES `tp_multiple_selects` (`select_id`)  
                  ) ENGINE=InnoDB DEFAULT CHARSET=latin1;   
				  
alter table `tp_feedback_form_fields` add column `select_id` int(11) NULL after `rating_id`;
alter table `tp_applicant_selection_process_traits` add column `select_field_id` varchar(250) NULL after `rating_field_id`;

CREATE TABLE tp_position_documents (                                                                                 
  document_id bigint(20) NOT NULL auto_increment,                                                                     
  position_id bigint(20) NOT NULL,                                                                                   
  file_name varchar(250) NOT NULL,                                                                                    
  document_path varchar(250) NOT NULL,                                                                                
  user_id bigint(20) NOT NULL,                                                                                        
  date_created datetime NOT NULL,                                                                                     
  document_is_hidden char(1) NOT NULL default '0',                                                                    
  PRIMARY KEY  (document_id),                                                                                         
  KEY FK_tp_position_documents (position_id),                                                                     
  KEY FK_tp_position_documents_1 (user_id),                                                                        
  CONSTRAINT tp_position_documents_ibfk_1 FOREIGN KEY (position_id) REFERENCES tp_positions (position_id),  
  CONSTRAINT tp_position_documents_ibfk_2 FOREIGN KEY (user_id) REFERENCES tp_users (USER_ID)                  
);

alter table `tp_applicant_inbox_emails` change `entry_id` `entry_id` varchar (250)   NULL  COLLATE latin1_swedish_ci;
alter table `tp_screen_configurations` add column `field_confidential` char (1)  DEFAULT '0' NOT NULL  COLLATE latin1_swedish_ci  after `field_import_mandatory`,change `field_import_show` `field_import_show` char (1)  DEFAULT '0' NOT NULL  COLLATE latin1_swedish_ci;

alter table `tp_users` add column `dept_id` int (10)   NULL  after `IS_USER_LDAP_SETTING`, add column `sub_dept_id` int (10)   NULL  after `dept_id`, add column `sub_sub_dept_id` int (10)   NULL  after `sub_dept_id`, add column `location_id` int (11)   NULL  after `sub_sub_dept_id`;
alter table `tp_users` add foreign key `FK_tp_users_2`(`sub_dept_id`) references `tp_departments` (`dept_id`);

CREATE TABLE `tp_data_view_config` (                                                                 
	`user_id` bigint(20) NOT NULL, 
	`view_type` char(2) default NULL,                              
	`column_1` char(250) default NULL,                                                                   
	`column_2` char(250) default NULL,
KEY `FK_tp_data_view_config` (`user_id`),                                                          
CONSTRAINT `tp_data_view_config_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `tp_users` (`USER_ID`)  
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `tp_locations_office` (                                                                             
               `office_id` int(11) NOT NULL auto_increment,                                                                   
               `office_name` varchar(100) NOT NULL,                                                                           
               `office_address` varchar(250) default NULL,                                                                    
               `office_desc` varchar(250) default NULL,                                                                       
               `location_id` int(11) NOT NULL,                                                                                
               PRIMARY KEY  (`office_id`),                                                                                    
               KEY `FK_tp_locations_office` (`location_id`),                                                                  
               CONSTRAINT `tp_locations_office_ibfk_1` FOREIGN KEY (`location_id`) REFERENCES `tp_locations` (`location_id`)  
             ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

delete from tp_template_types where template_type_id=38;
delete from tp_template_vars where template_type_id=38;
delete from tp_templates where template_code='positionChangeNotification';
delete from tp_template_generic_vars where template_variable='FIELD_CHANGE_DETAILS';

insert into tp_template_generic_vars(template_variable_type, template_variable) values (20, 'FIELD_CHANGE_DETAILS');
insert into tp_template_types (template_type_id,template_type) values (38,'Position Change Notification');
insert into tp_template_vars (template_type_id, template_variable_type) values (38, '1');
insert into tp_template_vars (template_type_id, template_variable_type) values (38, '2');
insert into tp_template_vars (template_type_id, template_variable_type) values (38, '18');
insert into tp_template_vars (template_type_id, template_variable_type) values (38, '20');

insert into tp_templates (template_code, template_name, template_subject_file, template_content_file, template_auto, template_format, 
	template_private, template_type_id, template_is_default, template_date_created,template_is_save_as_draft, do_show_save_as_draft_option, user_id ) 
	values ('positionChangeNotification', 'Position Change Notification', 'positionChangeNotificationSubject.vm', 
	'positionChangeNotificationContent.vm', 0, 0, 0, 38, 1, now(), 0, 0, 1);

delete from tp_application_properties where application_property='user_display_in_grid_template';
insert into tp_application_properties (application_property, application_property_value)
	values ('user_display_in_grid_template', 'f n [r]');
	
CREATE TABLE `tp_position_locations` (                                                                        
	`position_id` bigint(20) NOT NULL,                                                                         
	`location_id` int(11) NOT NULL,                                                                              
	KEY `FK_tp_position_locations` (`location_id`),                                                               
	CONSTRAINT `tp_position_locations_ibfk_1` FOREIGN KEY (`location_id`) REFERENCES `tp_locations` (`location_id`)  
	  ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
	  
INSERT INTO tp_position_locations (position_id,location_id)
SELECT position_id, location_id FROM tp_positions WHERE location_id is not NULL;

insert into tp_position_screen (field_id, field_type, field_rank, field_position_print_show) 
	select 'Grade','0',(max(field_rank)+1),'1' from tp_position_screen;
insert into tp_position_screen (field_id, field_type, field_rank, field_position_print_show) 	
	select 'Band','0',(max(field_rank)+1),'1' from tp_position_screen;
insert into tp_position_screen (field_id, field_type, field_rank, field_position_print_show) 
	select 'BudgetItem','0',(max(field_rank)+1),'1' from tp_position_screen;

alter table `tp_positions` drop column `location_id`;
alter table `tp_positions` drop foreign key `tp_positions_ibfk_6`;