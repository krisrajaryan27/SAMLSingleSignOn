use talentpool;

alter table `tp_positions` add column `position_date_approved` datetime   NULL  after `position_deleted_by`;

UPDATE tp_positions tp,
(SELECT position_id, feedback_date FROM (
	SELECT traf.position_id, traf.feedback_date FROM tp_requisition_approval_feedback traf
	WHERE to_step_id IS NULL 
	ORDER BY feedback_id DESC
	) AS traf
	GROUP BY traf.position_id
) traf
SET tp.position_date_approved = traf.feedback_date
WHERE traf.position_id = tp.position_id;

INSERT INTO tp_report_levels (level_id,report_id,report_type)
SELECT level_id,22,0 FROM tp_report_level_roles WHERE role_id=1;

INSERT INTO tp_report_levels (level_id,report_id,report_type)
SELECT level_id,23,0 FROM tp_report_level_roles WHERE role_id=1;

INSERT INTO tp_report_levels (level_id,report_id,report_type)
SELECT level_id,24,0 FROM tp_report_level_roles WHERE role_id=1;

CREATE TABLE `tp_report_templates` (                        
               `report_template_id` bigint(20) NOT NULL auto_increment,  
               `report_id` bigint(20) NOT NULL,                          
               `template_name` varchar(250) NOT NULL,
               `report_file_path` varchar(250) default NULL,
               `original_file_name` varchar(250) default NULL,
               `date_created` datetime NOT NULL,                         
               `user_id` bigint(20) NOT NULL,                            
               `sheet_index` char(10) default NULL,                      
               `row_index` char(10) default NULL,                        
               PRIMARY KEY  (`report_template_id`)                       
             ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
		
CREATE TABLE `tp_report_template_columns` (                                                                                                  
      `report_template_id` bigint(20) NOT NULL,                                                                                                  
      `column_name` varchar(250) NOT NULL,                                                                                                       
      `column_rank` tinyint(4) NOT NULL,                                                                                                         
      `column_type` char(1) NOT NULL default '0',                                                                                                
      KEY `FK_tp_report_template_columns_1` (`report_template_id`),                                                                              
      CONSTRAINT `tp_report_template_columns_ibfk_1` FOREIGN KEY (`report_template_id`) REFERENCES `tp_report_templates` (`report_template_id`)  
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1 ;
    
insert into tp_template_generic_vars 
 (template_variable_type, template_variable) values ('18', 'POSITION_CODE');
insert into tp_template_generic_vars 
 (template_variable_type, template_variable) values ('18', 'POSITION_CREATED_ON');
insert into tp_template_generic_vars 
 (template_variable_type, template_variable) values ('18', 'POSITION_GRADE');
insert into tp_template_generic_vars 
 (template_variable_type, template_variable) values ('18', 'POSITION_BAND');
insert into tp_template_generic_vars 
 (template_variable_type, template_variable) values ('18', 'POSITION_LOCATION');
insert into tp_template_generic_vars 
 (template_variable_type, template_variable) values ('18', 'POSITION_DEPARTMENT');
insert into tp_template_generic_vars 
 (template_variable_type, template_variable) values ('18', 'POSITION_REFERAL_FEES');
insert into tp_template_generic_vars 
 (template_variable_type, template_variable) values ('18', 'POSITION_EXPERIENCE');
insert into tp_template_generic_vars 
 (template_variable_type, template_variable) values ('18', 'POSITION_BUDGET_ITEM_NAME');


insert into tp_template_vars 
(template_type_id, template_variable_type) values('15', '18');
insert into tp_template_vars 
(template_type_id, template_variable_type) values('18', '18');           
