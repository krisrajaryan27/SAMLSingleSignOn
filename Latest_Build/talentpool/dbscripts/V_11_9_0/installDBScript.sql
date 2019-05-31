use talentpool;
CREATE TABLE tp_application_properties_employee_portal (                                                                     
     property_name varchar(250) NOT NULL,
     property_value varchar(250) NOT NULL,
     last_updated_by bigint(20) NOT NULL,
     last_updated_on datetime NOT NULL,
     created_on datetime NOT NULL,
     KEY FK_tp_application_properties_employee_portal (last_updated_by),
     CONSTRAINT FK_tp_application_properties_employee_portal FOREIGN KEY (last_updated_by) REFERENCES tp_users (USER_ID)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DELETE FROM tp_application_properties_employee_portal WHERE property_name='prop_can_emp_register';
INSERT INTO tp_application_properties_employee_portal (property_name,property_value,last_updated_by,last_updated_on,created_on) VALUES ('prop_can_emp_register','1','1',now(),now());

DELETE FROM tp_application_properties_employee_portal WHERE property_name='max_val_in_exp_filter';
INSERT INTO tp_application_properties_employee_portal (property_name,property_value,last_updated_by,last_updated_on,created_on) VALUES ('max_val_in_exp_filter','15','1',now(),now());

DELETE FROM tp_application_properties WHERE application_property='enable_bcc_while_sending_email';
INSERT INTO tp_application_properties (application_property, application_property_value) VALUES ('enable_bcc_while_sending_email', 0);

CREATE TABLE tp_date_time_patterns (
	pattern_id INT(11) NOT NULL ,
	pattern_type INT(1) NOT NULL,
	pattern_value VARCHAR(50) NOT NULL,
	PRIMARY KEY (pattern_id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
INSERT INTO tp_date_time_patterns (pattern_id,pattern_type,pattern_value) VALUES ('1','1','dd-MMM-yy');
INSERT INTO tp_date_time_patterns (pattern_id,pattern_type,pattern_value) VALUES ('2','1','dd-MMM-yyyy');
INSERT INTO tp_date_time_patterns (pattern_id,pattern_type,pattern_value) VALUES ('3','1','dd/MMM/yy');
INSERT INTO tp_date_time_patterns (pattern_id,pattern_type,pattern_value) VALUES ('4','1','dd-MM-yy');
INSERT INTO tp_date_time_patterns (pattern_id,pattern_type,pattern_value) VALUES ('5','1','dd-MM-yyyy');
INSERT INTO tp_date_time_patterns (pattern_id,pattern_type,pattern_value) VALUES ('6','1','dd/MM/yy');
INSERT INTO tp_date_time_patterns (pattern_id,pattern_type,pattern_value) VALUES ('7','1','EEE, dd-MMM-yy');
INSERT INTO tp_date_time_patterns (pattern_id,pattern_type,pattern_value) VALUES ('8','2','hh:mm a');
INSERT INTO tp_date_time_patterns (pattern_id,pattern_type,pattern_value) VALUES ('9','2','HH:mm');

DELETE FROM tp_application_properties WHERE application_property='default_dateformat';
INSERT INTO tp_application_properties (application_property, application_property_value) VALUES ('default_dateformat', '1');

DELETE FROM tp_application_properties WHERE application_property='default_timeformat';
INSERT INTO tp_application_properties (application_property, application_property_value) VALUES ('default_timeformat', '8');