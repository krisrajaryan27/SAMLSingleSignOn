use talentpool;

delete from tp_recent_searches;

alter table tp_degrees drop column degree_level;

CREATE TABLE `tp_messages_to` (                                                                          
  `message_id` bigint(20) NOT NULL,                                                                      
  `message_to` bigint(20) NOT NULL,                                                                      
  `message_received_type` tinyint(1) NOT NULL default '0',                                               
  `message_read` tinyint(4) NOT NULL default '0',                                                        
  KEY `FK_tp_messages_to` (`message_id`),                                                                
  KEY `FK_tp_messages_to_2` (`message_to`),                                                              
  CONSTRAINT `tp_messages_to_ibfk_2` FOREIGN KEY (`message_to`) REFERENCES `tp_users` (`USER_ID`),       
  CONSTRAINT `tp_messages_to_ibfk_1` FOREIGN KEY (`message_id`) REFERENCES `tp_messages` (`message_id`)  
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

insert into tp_messages_to(message_id, message_to, message_received_type, message_read)
select message_id,message_to,'0',message_read from tp_messages;

alter table `tp_messages` drop column `message_read`;
alter table `tp_messages` drop foreign key `tp_messages_ibfk_2`;
alter table `tp_messages` drop key `FK_tp_messages_1`;
alter table `tp_messages` drop column `message_to`;
