use talentpool;

delete from tp_recent_searches;
alter table tp_applicant_educational_info drop column educational_level;

CREATE TABLE `tp_custom_fields` (                             
                    `custom_field_id` int(11) NOT NULL,                         
                    `custom_field_name` varchar(250) NOT NULL,                  
                    `custom_field_type` varchar(250) NOT NULL,                  
                    `custom_field_attributes` text,                             
                    `custom_field_other_attributes` text,                       
                    `custom_field_display_name` varchar(250) NOT NULL,          
                    `custom_field_default_value` varchar(250) default NULL,     
                    `custom_field_options` text,                                
                    `custom_field_input_allowed` int(11) NOT NULL default '1',  
                    `custom_field_required` int(11) NOT NULL default '0',       
                    `custom_field_rank` int(11) NOT NULL,                       
                    `custom_field_searchable` int(11) NOT NULL default '0',     
                    `custom_field_entity_type` int(11) NOT NULL,                
                    PRIMARY KEY  (`custom_field_id`)                            
                  ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
                  
CREATE TABLE `tp_custom_field_values` (                                                                                         
                          `custom_field_id` int(11) NOT NULL,                                                                                           
                          `custom_field_value` varchar(250) default NULL,                                                                               
                          `entity_id` bigint(20) default NULL,                                                                                          
                          KEY `FK_tp_custom_field_values` (`custom_field_id`),                                                                          
                          CONSTRAINT `tp_custom_field_values_ibfk_1` FOREIGN KEY (`custom_field_id`) REFERENCES `tp_custom_fields` (`custom_field_id`)  
                        ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
                        
CREATE TABLE `tp_custom_pages` (                 
                   `custom_page_type` varchar(100) default NULL,  
                   `custom_page_name` varchar(100) default NULL   
                 ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
                        
