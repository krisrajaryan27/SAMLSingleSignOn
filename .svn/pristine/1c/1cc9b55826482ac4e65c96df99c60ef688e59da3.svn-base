use talentpool;

DELETE FROM tp_recent_searches;

alter table tp_bulk_import_session_emails change entry_id entry_id varchar(250) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT '' NOT NULL;

alter table tp_inbox_emails change entry_id entry_id varchar(250) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL ;

alter table tp_position_screen add column field_position_description_show char(1) CHARSET latin1 COLLATE latin1_swedish_ci DEFAULT '0' NOT NULL after field_position_print_show, add column field_position_mandatory char(1) CHARSET latin1 COLLATE latin1_swedish_ci DEFAULT '0' NOT NULL after field_position_description_show;

create temporary table if not exists tp_temp_1 as (select * from tp_position_screen where field_rank > (select field_rank from tp_position_screen where field_id = 'DepartmentLevel5') ) ;
update tp_position_screen set field_rank = field_rank - 1
where field_rank in (select field_rank from tp_temp_1);
drop table tp_temp_1;

delete from tp_position_screen where field_id='DepartmentLevel5';

create temporary table if not exists tp_temp_1 as (select * from tp_position_screen where field_rank > (select field_rank from tp_position_screen where field_id = 'DepartmentLevel4') ) ;
update tp_position_screen set field_rank = field_rank - 1
where field_rank in (select field_rank from tp_temp_1);
drop table tp_temp_1;

delete from tp_position_screen where field_id='DepartmentLevel4';

create temporary table if not exists tp_temp_1 as (select * from tp_position_screen where field_rank > (select field_rank from tp_position_screen where field_id = 'DepartmentLevel3') ) ;
update tp_position_screen set field_rank = field_rank - 1
where field_rank in (select field_rank from tp_temp_1);
drop table tp_temp_1;

delete from tp_position_screen where field_id='DepartmentLevel3';

create temporary table if not exists tp_temp_1 as (select * from tp_position_screen where field_rank > (select field_rank from tp_position_screen where field_id = 'DepartmentLevel2') ) ;
update tp_position_screen set field_rank = field_rank - 1
where field_rank in (select field_rank from tp_temp_1);
drop table tp_temp_1;

delete from tp_position_screen where field_id='DepartmentLevel2';

update tp_position_screen set field_id = 'DepartmentLevels' where field_id='DepartmentLevel1';

ALTER TABLE `tp_position_screen_configurations` 
ADD COLUMN `field_list_is_filter` char(1) CHARSET latin1 COLLATE latin1_swedish_ci DEFAULT '0' NOT NULL after `field_position_list_show`, 
ADD COLUMN `field_list_is_filter_editable` char(1) CHARSET latin1 COLLATE latin1_swedish_ci DEFAULT '0' NOT NULL after `field_list_is_filter`;

update `tp_position_screen_configurations` 
set `field_list_is_filter`='0',`field_list_is_filter_editable`='1'
where `field_id`='Location';

update `tp_position_screen_configurations` 
set `field_list_is_filter`='0',`field_list_is_filter_editable`='1'
where `field_id`='PrimarySkills';

update `tp_position_screen_configurations` 
set `field_list_is_filter`='0',`field_list_is_filter_editable`='1'
where `field_id`='DepartmentLevel1';

ALTER TABLE `tp_templates` ADD COLUMN `is_repeat` char (1)  DEFAULT '0' NOT NULL  COLLATE latin1_swedish_ci  AFTER `user_id`;
INSERT INTO tp_template_vars (template_type_id,template_variable_type)  
VALUES ('10','18');
INSERT INTO tp_template_vars (template_type_id,template_variable_type)  
VALUES ('10','2');

update tp_templates set template_name='Forward Resumes' where template_name='forwardResumes';

alter table tp_bulk_import_session_results change parsed_ctc parsed_ctc varchar(10) NULL;


CREATE TABLE tp_position_screen_description (                                
                      field_id varchar(250) NOT NULL default '',                     
                      field_type char(1) NOT NULL default '0',                       
                      field_rank tinyint(4) NOT NULL default '0',          
                      field_position_show char(1) NOT NULL default '0',  
                      field_position_mandatory char(1) NOT NULL default '0'          
                    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;    

CREATE TABLE tp_position_screen_requirements (                                
                      field_id varchar(250) NOT NULL default '',                     
                      field_type char(1) NOT NULL default '0',                       
                      field_rank tinyint(4) NOT NULL default '0',          
                      field_position_show char(1) NOT NULL default '0',  
                      field_position_mandatory char(1) NOT NULL default '0'          
                    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

insert into tp_position_screen_description (`field_id`, `field_type`, `field_rank`, `field_position_show`, `field_position_mandatory`) values('PositionName','0','1','1','1');
insert into tp_position_screen_description (`field_id`, `field_type`, `field_rank`, `field_position_show`, `field_position_mandatory`) values('PositionCode','0','2','1','1');
insert into tp_position_screen_description (`field_id`, `field_type`, `field_rank`, `field_position_show`, `field_position_mandatory`) values('PositionCreatedOn','0','3','1','0');
insert into tp_position_screen_description (`field_id`, `field_type`, `field_rank`, `field_position_show`, `field_position_mandatory`) values('Location','0','8','1','0');
insert into tp_position_screen_description (`field_id`, `field_type`, `field_rank`, `field_position_show`, `field_position_mandatory`) values('DepartmentLevels','0','9','1','1');
insert into tp_position_screen_description (`field_id`, `field_type`, `field_rank`, `field_position_show`, `field_position_mandatory`) values('Vacancies','0','10','1','1');
insert into tp_position_screen_description (`field_id`, `field_type`, `field_rank`, `field_position_show`, `field_position_mandatory`) values('HireByDate','0','11','1','1');
insert into tp_position_screen_description (`field_id`, `field_type`, `field_rank`, `field_position_show`, `field_position_mandatory`) values('PositionLevel','0','12','1','0');
insert into tp_position_screen_description (`field_id`, `field_type`, `field_rank`, `field_position_show`, `field_position_mandatory`) values('ReferalFees','0','13','1','0');
insert into tp_position_screen_description (`field_id`, `field_type`, `field_rank`, `field_position_show`, `field_position_mandatory`) values('Responsibilities','0','15','1','0');
insert into tp_position_screen_description (`field_id`, `field_type`, `field_rank`, `field_position_show`, `field_position_mandatory`) values('Note','0','14','1','0');
insert into tp_position_screen_description (`field_id`, `field_type`, `field_rank`, `field_position_show`, `field_position_mandatory`) values('RequestedBy','0','4','1','1');
insert into tp_position_screen_description (`field_id`, `field_type`, `field_rank`, `field_position_show`, `field_position_mandatory`) values('ApprovedBy','0','16','1','0');
insert into tp_position_screen_description (`field_id`, `field_type`, `field_rank`, `field_position_show`, `field_position_mandatory`) values('Grade','0','5','1','0');
insert into tp_position_screen_description (`field_id`, `field_type`, `field_rank`, `field_position_show`, `field_position_mandatory`) values('Band','0','6','1','0');
insert into tp_position_screen_description (`field_id`, `field_type`, `field_rank`, `field_position_show`, `field_position_mandatory`) values('BudgetItem','0','7','1','1');

insert into tp_position_screen_requirements (`field_id`, `field_type`, `field_rank`, `field_position_show`, `field_position_mandatory`) values('Requirements','0','6','1','0');
insert into tp_position_screen_requirements (`field_id`, `field_type`, `field_rank`, `field_position_show`, `field_position_mandatory`) values('PrimarySkills','0','1','1','1');
insert into tp_position_screen_requirements (`field_id`, `field_type`, `field_rank`, `field_position_show`, `field_position_mandatory`) values('SecondarySkills','0','2','1','0');
insert into tp_position_screen_requirements (`field_id`, `field_type`, `field_rank`, `field_position_show`, `field_position_mandatory`) values('Education','0','3','1','1');
insert into tp_position_screen_requirements (`field_id`, `field_type`, `field_rank`, `field_position_show`, `field_position_mandatory`) values('Branch','0','4','1','0');
insert into tp_position_screen_requirements (`field_id`, `field_type`, `field_rank`, `field_position_show`, `field_position_mandatory`) values('Experience','0','5','1','1');

alter table tp_website_screen_settings
add column positions_home_header text CHARSET latin1 COLLATE latin1_swedish_ci NULL after is_other;

alter table tp_position_screen_description 
add column number int NOT NULL AUTO_INCREMENT  after field_position_show, add primary key (number);

INSERT INTO tp_position_screen_description
(field_id, field_type,field_position_show)
SELECT custom_field_name, '1','1'
FROM tp_custom_fields WHERE custom_field_entity_type =2;

UPDATE tp_position_screen_description SET field_rank=number WHERE field_type=1;

alter table tp_position_screen_description drop column number;