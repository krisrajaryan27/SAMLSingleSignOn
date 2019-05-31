use talentpool;

DELETE FROM tp_recent_searches;

CREATE TABLE `tp_permissions` (                 
                  `permission_id` int(11) NOT NULL,             
                  `permission_rank` int(11) default NULL,       
                  `permission_desc` varchar(255) default NULL,  
                  `parent_id` int(11) default NULL,             
                  `is_permission` char(1) default NULL,         
                  PRIMARY KEY  (`permission_id`)                
                ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
                
CREATE TABLE `tp_permission_module` (                                                                                   
                        `permission_id` int(11) default NULL,                                                                                 
                        `module_id` int(11) default NULL,                                                                                     
                        KEY `FK_tp_permission_module` (`permission_id`),                                                                      
                        CONSTRAINT `tp_permission_module_ibfk_1` FOREIGN KEY (`permission_id`) REFERENCES `tp_permissions` (`permission_id`)  
                      ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
                      
CREATE TABLE `tp_role_permissions` (                                                                                    
                       `role_id` smallint(6) default NULL,                                                                                   
                       `permission_id` int(11) default NULL,                                                                                 
                       KEY `FK_tp_role_permissions` (`permission_id`),                                                                       
                       KEY `FK_tp_role_permissions_1` (`role_id`),                                                                           
                       CONSTRAINT `tp_role_permissions_ibfk_1` FOREIGN KEY (`permission_id`) REFERENCES `tp_permissions` (`permission_id`),  
                       CONSTRAINT `tp_role_permissions_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `tp_roles` (`ROLE_ID`)                     
                     ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `tp_user_permissions` (                                                                                   
                       `user_id` bigint(20) default NULL,                                                                                   
                       `permission_id` int(11) default NULL,                                                                                
                       KEY `FK_tp_user_permissions` (`user_id`),                                                                            
                       KEY `FK_tp_user_permissions_1` (`permission_id`),                                                                    
                       CONSTRAINT `tp_user_permissions_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `tp_users` (`USER_ID`),                   
                       CONSTRAINT `tp_user_permissions_ibfk_2` FOREIGN KEY (`permission_id`) REFERENCES `tp_permissions` (`permission_id`)  
                     ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
                     

insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) values (1,1,'Show all positions',0,'1');
insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) values (2,2,'Show only positions with rights',0,'1');
insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) values (3,3,'Show confidential data',0,'1');
insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) values (4,4,'Do not show confidential data',0,'1');
insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) values (10,5,'Admin section',0,'1');
insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) values (11,6,'Manage Roles',10,'1');
insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) values (12,7,'Manage Users',10,'1');
insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) values (13,8,'Application Settings',10,'1');
insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) values (14,9,'Inbox Settings',10,'1');
insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) values (15,10,'Duplicate detection settings',10,'1');
insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) values (16,11,'Requisition approval steps settings',10,'1');
insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) values (17,12,'SMS settings',10,'1');
insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) values (18,13,'LDAP settings',10,'1');
insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) values (19,14,'My Account',0,'0');
insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) values (20,15,'Change password',19,'1');
insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) values (21,16,'Dashboard',0,'0');
insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) values (22,17,'Show position summary on dashboard',22,'1');
insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) values (23,18,'Import',0,'1');
insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) values (24,19,'Show sent emails',23,'1');
insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) values (25,20,'Delete email',23,'1');
insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) values (26,21,'Import resume from email',23,'1');
insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) values (27,22,'Import resume from desktop',23,'1');
insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) values (28,23,'Send emails from import screen',23,'1');
insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) values (29,24,'Screen',0,'1');
insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) values (30,25,'Mass email',29,'1');
insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) values (31,26,'Delete applicant',29,'1');
insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) values (32,27,'Candidate Details Screen',0,'0');
insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) values (33,28,'Edit candidate details',32,'1');
insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) values (34,29,'Set flag',32,'1');
insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) values (35,30,'Send SMS',32,'1');
insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) values (36,31,'Send email',32,'1');
insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) values (37,32,'Upload document',32,'1');
insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) values (38,33,'Shortlist',32,'1');
insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) values (39,34,'Schedule',32,'1');
insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) values (40,35,'Update followup',32,'1');
insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) values (41,36,'Select',0,'1');
insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) values (42,37,'Call list',41,'1');
insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) values (43,38,'Hire',0,'1');
insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) values (44,39,'Joined candidates',43,'1');
insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) values (45,40,'Reports',0,'1');
insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) values (46,41,'Positions',0,'1');
insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) values (47,42,'Edit Positions',46,'1');
insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) values (48,43,'View position details',46,'1');
insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) values (49,44,'Delete position',46,'1');
insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) values (50,45,'Publish position',46,'1');
insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) values (51,46,'Open/Close position',46,'1');
insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) values (52,47,'Masters',0,'1');
insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) values (53,48,'Branch/Degree/Institute/Department Master',52,'1');
insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) values (54,49,'Skills Master',52,'1');
insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) values (55,50,'Source Master',52,'1');
insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) values (56,51,'Template Master',52,'1');
insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) values (57,52,'Flag Master',52,'1');
insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) values (58,53,'Rating Master',52,'1');
insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) values (59,54,'Feedback fields master',52,'1');
insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) values (60,55,'Feedback Forms',52,'1');
insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) values (61,56,'Expenses',0,'1');
insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) values (62,57,'Add Expenses',61,'1');
insert into `tp_permissions` (`permission_id`,`permission_rank`,`permission_desc`,`parent_id`,`is_permission`) values (63,58,'Expense types',61,'1');

insert into `tp_permission_module` (`permission_id`,`module_id`) values (17,11);
insert into `tp_permission_module` (`permission_id`,`module_id`) values (18,14);
insert into `tp_permission_module` (`permission_id`,`module_id`) values (30,13);
insert into `tp_permission_module` (`permission_id`,`module_id`) values (35,11);
insert into `tp_permission_module` (`permission_id`,`module_id`) values (50,19);
insert into `tp_permission_module` (`permission_id`,`module_id`) values (61,17);

insert into `tp_role_permissions` (`role_id`,`permission_id`) values (1,1);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (1,3);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (1,10);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (1,11);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (1,12);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (1,13);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (1,14);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (1,15);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (1,16);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (1,17);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (1,18);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (1,20);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (1,22);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (1,23);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (1,24);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (1,25);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (1,26);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (1,27);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (1,28);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (1,29);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (1,30);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (1,31);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (1,33);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (1,34);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (1,35);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (1,36);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (1,37);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (1,38);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (1,39);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (1,40);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (1,41);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (1,42);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (1,43);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (1,44);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (1,45);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (1,46);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (1,47);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (1,48);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (1,49);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (1,50);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (1,51);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (1,52);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (1,53);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (1,54);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (1,55);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (1,56);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (1,57);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (1,58);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (1,59);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (1,60);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (1,61);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (1,62);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (1,63);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (6,1);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (6,3);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (6,10);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (6,11);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (6,12);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (6,15);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (6,16);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (6,20);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (6,22);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (6,23);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (6,24);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (6,25);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (6,26);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (6,27);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (6,28);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (6,29);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (6,30);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (6,33);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (6,34);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (6,35);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (6,36);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (6,37);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (6,38);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (6,39);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (6,40);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (6,41);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (6,42);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (6,43);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (6,44);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (6,45);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (6,46);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (6,47);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (6,48);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (6,49);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (6,50);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (6,51);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (6,52);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (6,53);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (6,54);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (6,55);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (6,56);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (6,57);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (6,58);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (6,59);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (6,60);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (6,61);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (6,62);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (6,63);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (4,1);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (4,3);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (4,10);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (4,11);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (4,12);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (4,15);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (4,16);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (4,20);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (4,22);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (4,23);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (4,24);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (4,25);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (4,26);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (4,27);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (4,28);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (4,29);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (4,30);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (4,33);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (4,34);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (4,35);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (4,36);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (4,37);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (4,38);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (4,39);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (4,40);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (4,41);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (4,42);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (4,43);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (4,44);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (4,45);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (4,46);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (4,47);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (4,48);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (4,49);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (4,50);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (4,51);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (4,52);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (4,53);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (4,54);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (4,55);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (4,56);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (4,57);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (4,58);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (4,59);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (4,60);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (4,61);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (4,62);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (4,63);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (3,2);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (3,3);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (3,20);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (3,22);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (3,23);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (3,24);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (3,25);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (3,26);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (3,27);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (3,28);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (3,29);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (3,30);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (3,33);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (3,34);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (3,35);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (3,36);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (3,37);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (3,38);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (3,39);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (3,40);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (3,41);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (3,42);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (3,43);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (3,44);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (3,45);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (3,46);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (3,47);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (3,48);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (3,49);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (3,50);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (3,51);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (3,52);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (3,53);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (3,54);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (3,55);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (3,56);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (3,57);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (3,58);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (3,59);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (3,60);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (3,61);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (3,62);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (3,63);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (2,2);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (2,4);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (2,20);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (2,22);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (2,29);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (2,33);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (2,34);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (2,37);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (2,38);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (2,39);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (2,40);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (2,41);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (2,43);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (2,44);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (2,45);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (2,46);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (2,47);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (2,48);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (5,2);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (5,4);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (5,20);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (5,22);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (5,33);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (5,37);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (5,41);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (5,43);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (5,46);
insert into `tp_role_permissions` (`role_id`,`permission_id`) values (5,48);

INSERT INTO tp_user_permissions(user_id, permission_id) SELECT tu.user_id, trp.permission_id FROM tp_users tu, tp_role_permissions trp, tp_user_roles tur
WHERE tu.user_id=tur.user_id AND tur.role_id=trp.role_id;

CREATE TABLE `tp_levels` (                         
             `level_id` smallint(6) NOT NULL auto_increment,  
             `level_name` varchar(255) NOT NULL,              
             PRIMARY KEY  (`level_id`)                        
           ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `tp_report_level_roles` (                                                                    
                         `role_id` smallint(6) default NULL,                                                                     
                         `level_id` smallint(6) default NULL,                                                                    
                         KEY `FK_tp_report_level_roles_1` (`role_id`),                                                           
                         KEY `FK_tp_report_level_roles` (`level_id`),                                                            
                         CONSTRAINT `tp_report_level_roles_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `tp_roles` (`ROLE_ID`),    
                         CONSTRAINT `tp_report_level_roles_ibfk_2` FOREIGN KEY (`level_id`) REFERENCES `tp_levels` (`level_id`)  
                       ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `tp_report_level_user` (                                                                     
                        `user_id` bigint(20) default NULL,                                                                      
                        `level_id` smallint(6) default NULL,                                                                    
                        KEY `FK_tp_report_level_user_1` (`level_id`),                                                           
                        KEY `FK_tp_report_level_user` (`user_id`),                                                              
                        CONSTRAINT `tp_report_level_user_ibfk_1` FOREIGN KEY (`level_id`) REFERENCES `tp_levels` (`level_id`),  
                        CONSTRAINT `tp_report_level_user_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `tp_users` (`USER_ID`)      
                      ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `tp_report_levels` (                                                                    
                    `level_id` smallint(6) default NULL,                                                               
                    `report_id` smallint(6) default NULL,                                                              
                    KEY `FK_tp_report_levels` (`level_id`),                                                            
                    CONSTRAINT `tp_report_levels_ibfk_1` FOREIGN KEY (`level_id`) REFERENCES `tp_levels` (`level_id`)  
                  ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

alter table `tp_users` drop column `USER_SMS_ENABLED`;
alter table `tp_messages` add column `message_is_hidden` char (1)  DEFAULT '0' NOT NULL  after `message_date`;
alter table `tp_applicant_status_messages` add column `status_message_is_hidden` char (1)  DEFAULT '0' NOT NULL  after `system_generated`;
alter table `tp_communications` add column `communication_is_hidden` char (1)  DEFAULT '0' NOT NULL  after `communication_date_created`;
alter table `tp_applicant_selection_process` add column `selection_process_is_hidden` char (1)  DEFAULT '0' NOT NULL  after `position_id`;
alter table `tp_applicant_inbox_emails` add column `email_is_hidden` char (1)  DEFAULT '0' NOT NULL  after `email_imported`;

drop table `tp_task_overrides`;
drop table `tp_role_tasks`;
alter table `tp_applicant_documents` add column `document_is_hidden` char (1)  DEFAULT '0' NOT NULL  after `access_level`;
alter table `tp_applicant_documents` drop column `access_level`;

insert into `tp_levels` (`level_id`,`level_name`) values ( '1','Level 1');

insert into `tp_report_levels` (`level_id`,`report_id`) values (1,1);
insert into `tp_report_levels` (`level_id`,`report_id`) values (1,2);
insert into `tp_report_levels` (`level_id`,`report_id`) values (1,3);
insert into `tp_report_levels` (`level_id`,`report_id`) values (1,4);
insert into `tp_report_levels` (`level_id`,`report_id`) values (1,5);
insert into `tp_report_levels` (`level_id`,`report_id`) values (1,6);
insert into `tp_report_levels` (`level_id`,`report_id`) values (1,7);
insert into `tp_report_levels` (`level_id`,`report_id`) values (1,8);
insert into `tp_report_levels` (`level_id`,`report_id`) values (1,9);
insert into `tp_report_levels` (`level_id`,`report_id`) values (1,10);
insert into `tp_report_levels` (`level_id`,`report_id`) values (1,11);
insert into `tp_report_levels` (`level_id`,`report_id`) values (1,12);
insert into `tp_report_levels` (`level_id`,`report_id`) values (1,13);
insert into `tp_report_levels` (`level_id`,`report_id`) values (1,14);
insert into `tp_report_levels` (`level_id`,`report_id`) values (1,15);
insert into `tp_report_levels` (`level_id`,`report_id`) values (1,16);
insert into `tp_report_levels` (`level_id`,`report_id`) values (1,17);

INSERT INTO tp_report_level_roles(role_id,level_id) SELECT role_id, 1 FROM tp_roles WHERE ROLE_ID!=7;
INSERT INTO tp_report_level_user(user_id,level_id) SELECT USER_ID, 1 FROM tp_users;

