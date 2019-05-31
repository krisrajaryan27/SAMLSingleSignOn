use talentpool;

DELETE FROM tp_recent_searches;
DROP TABLE tp_grades;

CREATE TABLE `tp_ratings` (                                                                 
              `rating_id` int(11) NOT NULL auto_increment,                                              
              `rating_title` varchar(255) NOT NULL,                                                     
              `date_created` date NOT NULL,                                                             
              `user_id` bigint(20) NOT NULL,                                                            
              `rating_status` char(1) NOT NULL default '1',                                             
              PRIMARY KEY  (`rating_id`),                                                               
              KEY `FK_tp_ratings` (`user_id`),                                                          
              CONSTRAINT `tp_ratings_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `tp_users` (`USER_ID`)  
            ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
            
CREATE TABLE `tp_rating_fields` (                                                                       
                    `rating_field_id` int(11) NOT NULL auto_increment,                                                    
                    `rating_id` int(11) NOT NULL,                                                                         
                    `rating_field_desc` varchar(255) NOT NULL,                                                            
                    `rating_field_status` char(1) NOT NULL default '1',                                                   
                    `rating_rank` smallint(6) NOT NULL,                                                                   
                    PRIMARY KEY  (`rating_field_id`),                                                                     
                    KEY `FK_tp_rating_fields` (`rating_id`),                                                              
                    CONSTRAINT `tp_rating_fields_ibfk_1` FOREIGN KEY (`rating_id`) REFERENCES `tp_ratings` (`rating_id`)  
                  ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
                  
CREATE TABLE `tp_feedback_field_categories` (      
                                `category_id` int(11) NOT NULL auto_increment,   
                                `category_name` varchar(255) NOT NULL,           
                                `category_status` char(1) NOT NULL default '1',  
                                PRIMARY KEY  (`category_id`)                     
                              ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `tp_feedback_fields` (                                                                                             
                      `feedback_field_id` int(11) NOT NULL auto_increment,                                                                          
                      `category_id` int(11) NOT NULL,                                                                                               
                      `feedback_field_title` varchar(255) default NULL,                                                                             
                      `feedback_field_desc` text,                                                                                                   
                      `feedback_field_status` char(1) NOT NULL default '1',                                                                         
                      PRIMARY KEY  (`feedback_field_id`),                                                                                           
                      KEY `FK_tp_feedback_fields` (`category_id`),                                                                                  
                      CONSTRAINT `tp_feedback_fields_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `tp_feedback_field_categories` (`category_id`)  
                    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `tp_feedback_forms` (                                                                 
                     `feedback_form_id` int(11) NOT NULL auto_increment,                                              
                     `feedback_form_title` varchar(255) NOT NULL,                                                     
                     `feedback_form_header` text,                                                                     
                     `user_id` bigint(20) NOT NULL,                                                                   
                     `date_created` date NOT NULL,                                                                    
                     `feedback_form_status` char(1) NOT NULL default '1',                                             
                     `last_modified` date NOT NULL,                                                                   
                     PRIMARY KEY  (`feedback_form_id`),                                                               
                     KEY `FK_tp_feedback_forms` (`user_id`),                                                          
                     CONSTRAINT `tp_feedback_forms_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `tp_users` (`USER_ID`)  
                   ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `tp_feedback_form_fields` (                                                                                               
                           `feedback_form_field_id` bigint(20) NOT NULL auto_increment,                                                                         
                           `feedback_form_id` int(11) NOT NULL,                                                                                                 
                           `feedback_field_id` int(11) NOT NULL,                                                                                                
                           `feedback_form_field_desc` text NOT NULL,                                                                                            
                           `feedback_form_field_rank` smallint(6) NOT NULL,                                                                                     
                           `feedback_form_field_type` char(1) default '1',                                                                                      
                           `rating_id` int(11) default NULL,                                                                                                    
                           `feedback_form_field_comment_required` char(1) NOT NULL default '1',                                                                 
                           PRIMARY KEY  (`feedback_form_field_id`),                                                                                             
                           KEY `FK_tp_feedback_form_fields` (`feedback_form_id`),                                                                               
                           KEY `FK_tp_feedback_form_fields_1` (`rating_id`),                                                                                    
                           KEY `FK_tp_feedback_form_fields_2` (`feedback_field_id`),                                                                            
                           CONSTRAINT `tp_feedback_form_fields_ibfk_1` FOREIGN KEY (`feedback_form_id`) REFERENCES `tp_feedback_forms` (`feedback_form_id`),    
                           CONSTRAINT `tp_feedback_form_fields_ibfk_2` FOREIGN KEY (`rating_id`) REFERENCES `tp_ratings` (`rating_id`)
                         ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
                                                                                         
alter table `tp_position_steps` add column `feedback_form_id` int   NULL  after `position_step_level`;
alter table `tp_position_steps` add foreign key `FK_tp_position_steps`(`feedback_form_id`) references `tp_feedback_forms` (`feedback_form_id`);                                              

alter table `tp_applicant_selection_process_traits` drop foreign key `tp_applicant_selection_process_traits_ibfk_2`; 
alter table `tp_applicant_selection_process_traits` change `position_step_trait_id` `feedback_form_field_id` bigint (20)   NOT NULL , 
change `trait_comment` `trait_comment` text   NULL  COLLATE latin1_swedish_ci;
alter table `tp_position_steps` drop column `position_step_has_comment`;

alter table `tp_applicant_selection_process_traits` add column `rating_field_id` int   NULL  after `user_id`;
alter table `tp_applicant_selection_process_traits` add foreign key `FK_tp_applicant_selection_process_traits`(`rating_field_id`) references `tp_rating_fields` (`rating_field_id`);



alter table `tp_feedback_forms` add column `position_step_id` int   NULL  after `last_modified`;

INSERT INTO tp_feedback_forms (feedback_form_title,feedback_form_header,user_id,date_created,feedback_form_status,
last_modified,position_step_id) SELECT 
CONCAT('AUTO_', tp.position_title, '_', tps.position_step_title,'_',tps.position_step_id),
'',1,now(),1,now(),tps.position_step_id
FROM tp_position_steps tps, tp_positions tp 
WHERE tp.position_id=tps.position_id AND position_step_isdefault=0;

UPDATE tp_position_steps SET feedback_form_id = (select feedback_form_id FROM tp_feedback_forms WHERE 
position_step_id = tp_position_steps.position_step_id);

INSERT INTO tp_feedback_field_categories(category_name,category_status) values('Feedback fields','0');

INSERT INTO tp_feedback_fields(feedback_field_title, category_id, feedback_field_desc, 
feedback_field_status)SELECT  distinct(trait_title), 
(SELECT max(category_id) FROM tp_feedback_field_categories) as catid, '','0' FROM tp_position_step_traits;

alter table `tp_feedback_form_fields` 
add column `position_step_trait_id` bigint   NULL  after `feedback_form_field_comment_required`;

alter table `tp_applicant_selection_process_traits` add column `position_step_trait_id` bigint   NULL  after `rating_field_id`;
UPDATE tp_applicant_selection_process_traits SET position_step_trait_id= feedback_form_field_id;

INSERT INTO tp_feedback_form_fields(feedback_form_id, feedback_field_id, feedback_form_field_desc,
feedback_form_field_rank, feedback_form_field_type, rating_id, feedback_form_field_comment_required, 
position_step_trait_id) SELECT tps.feedback_form_id, tff.feedback_field_id,'', tpst.position_step_trait_rank,'1',null,'1',
tpst.position_step_trait_id
FROM tp_position_steps tps, tp_position_step_traits tpst 
,tp_feedback_fields tff 
WHERE tps.position_step_id = tpst.position_step_id AND tps.feedback_form_id is not null
AND tpst.trait_title=tff.feedback_field_title
ORDER BY  tps.feedback_form_id, tpst.position_step_trait_rank;

DELETE from tp_applicant_selection_process_traits where feedback_form_field_id not in (
select position_step_trait_id FROM tp_feedback_form_fields );

alter table `tp_applicant_selection_process_traits` drop key `process_id_2`;

UPDATE tp_applicant_selection_process_traits
SET feedback_form_field_id = (SELECT feedback_form_field_id FROM tp_feedback_form_fields WHERE 
position_step_trait_id = tp_applicant_selection_process_traits.position_step_trait_id LIMIT 0,1);

alter table `tp_applicant_selection_process_traits` add unique `process_id_2` ( `process_id`, `feedback_form_field_id`, `user_id` );
alter table `tp_feedback_forms` drop column `position_step_id`;
alter table `tp_feedback_form_fields` drop column `position_step_trait_id`;
alter table `tp_applicant_selection_process_traits` drop column `position_step_trait_id`;
DELETE FROM tp_feedback_field_categories WHERE category_id NOT IN (SELECT category_id FROM tp_feedback_fields);


