-- phpMyAdmin SQL Dump
-- version 2.8.0
-- http://www.phpmyadmin.net
-- 
-- Host: localhost
-- Generation Time: Jul 25, 2006 at 12:20 PM
-- Server version: 5.0.18
-- PHP Version: 5.1.2
-- 
-- Database: `livetalentpool`
-- 

-- --------------------------------------------------------
drop database if exists talentpool;
create database talentpool;

use talentpool;

set global foreign_key_checks =0;

-- 
-- Table structure for table `tp_applicant_educational_info`
-- 

CREATE TABLE `tp_applicant_educational_info` (
  `educational_info_id` bigint(20) NOT NULL auto_increment,
  `applicant_id` bigint(20) NOT NULL,
  `degree_id` smallint(6) default NULL,
  `educational_info_major` varchar(100) default NULL,
  `educational_info_institute` varchar(150) default NULL,
  `educational_info_year_of_passing` date default NULL,
  `educational_info_grade` varchar(100) default NULL,
  PRIMARY KEY  (`educational_info_id`),
  KEY `applicant_id` (`applicant_id`),
  KEY `degree_id` (`degree_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

-- 
-- Table structure for table `tp_applicant_employment`
-- 

CREATE TABLE `tp_applicant_employment` (
  `employment_id` bigint(20) NOT NULL auto_increment,
  `applicant_id` bigint(20) NOT NULL,
  `employment_from` date default NULL,
  `employment_to` date default NULL,
  `employment_company` varchar(250) default NULL,
  `employment_location` varchar(100) default NULL,
  `employment_responsibility` text,
  PRIMARY KEY  (`employment_id`),
  KEY `applicant_id` (`applicant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;



-- --------------------------------------------------------

-- 
-- Table structure for table `tp_applicant_inbox_email_attachments`
-- 

CREATE TABLE `tp_applicant_inbox_email_attachments` (
  `attachment_id` bigint(20) NOT NULL auto_increment,
  `email_id` bigint(20) NOT NULL,
  `attachment_file_path` varchar(150) NOT NULL,
  `attachment_original_file_name` varchar(150) default NULL,
  `attachment_content_type` varchar(150) default NULL,
  `attachment_content_id` varchar(150) default NULL,
  `attachment_type` char(1) NOT NULL default '1',
  `attachment_size` bigint(20) NOT NULL default '0',
  PRIMARY KEY  (`attachment_id`),
  KEY `email_id` (`email_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- 
-- Table structure for table `tp_applicant_inbox_emails`
-- 

CREATE TABLE `tp_applicant_inbox_emails` (
  `email_id` bigint(20) NOT NULL auto_increment,
  `email_from` varchar(100) NOT NULL,
  `email_to` text NOT NULL,
  `email_cc` text,
  `email_bcc` text,
  `email_subject` varchar(250) NOT NULL,
  `email_date_send` datetime default NULL,
  `email_date_received` datetime default NULL,
  `email_textbody` longtext,
  `email_htmlbody` longtext,
  `email_size` bigint(20) NOT NULL,
  `folder_id` int(11) NOT NULL default '1',
  `user_id` bigint(20) NOT NULL,
  `applicant_id` bigint(20) NOT NULL,
  `email_imported` char(1) NOT NULL default '0',
  PRIMARY KEY  (`email_id`),
  KEY `applicant_id` (`applicant_id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;


-- 
-- Table structure for table `tp_applicant_selection_process`
-- 

CREATE TABLE `tp_applicant_selection_process` (
  `process_id` bigint(20) NOT NULL auto_increment,
  `applicant_id` bigint(20) NOT NULL,
  `position_step_id_from` int(11) NOT NULL,
  `position_step_id_to` int(11) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `process_moved_date` datetime NOT NULL,
  `process_comment` text,
  PRIMARY KEY  (`process_id`),
  KEY `user_id` (`user_id`),
  KEY `applicant_id` (`applicant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;


-- 
-- Table structure for table `tp_applicant_selection_process_traits`
-- 

CREATE TABLE `tp_applicant_selection_process_traits` (
  `process_id` bigint(20) NOT NULL,
  `position_step_trait_id` bigint(20) NOT NULL,
  `trait_comment` text NOT NULL,
  KEY `process_id` (`process_id`),
  KEY `position_step_trait_id` (`position_step_trait_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- 
-- Table structure for table `tp_applicant_skills`
-- 

CREATE TABLE `tp_applicant_skills` (
  `applicant_id` bigint(20) NOT NULL,
  `skill_id` int(11) NOT NULL,
  `skill_status` char(1) NOT NULL default '0',
  KEY `applicant_id` (`applicant_id`),
  KEY `skill_id` (`skill_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- 
-- Table structure for table `tp_applicants`
-- 

CREATE TABLE `tp_applicants` (
  `applicant_id` bigint(20) NOT NULL auto_increment,
  `applicant_name` varchar(100) NOT NULL,
  `applicant_address` text,
  `applicant_address_type` char(1) default NULL,
  `applicant_city` varchar(50) default NULL,
  `applicant_email1` varchar(50) default NULL,
  `applicant_email2` varchar(50) default NULL,
  `applicant_home_phone` varchar(25) default NULL,
  `applicant_cell_phone` varchar(25) default NULL,
  `applicant_work_phone` varchar(25) default NULL,
  `applicant_working_since` date NOT NULL,
  `applicant_position_id` int(11) default NULL,
  `applicant_step_id` int(11) default NULL,
  `applicant_date_created` datetime NOT NULL,
  `source_id` int(11) NOT NULL,
  `applicant_joined` char(1) NOT NULL default '0',
  `applicant_notes` text,
  `applicant_original_resume_path` varchar(100) NOT NULL,
  PRIMARY KEY  (`applicant_id`),
  KEY `applicant_step_id` (`applicant_step_id`),
  KEY `source_id` (`source_id`),
  KEY `applicant_position_id` (`applicant_position_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;


-- 
-- Table structure for table `tp_appointment_attendees`
-- 

CREATE TABLE `tp_appointment_attendees` (
  `appointment_id` bigint(20) NOT NULL,
  `attendee_id` bigint(20) NOT NULL,
  KEY `appointment_id` (`appointment_id`),
  KEY `attendee_id` (`attendee_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- 
-- Table structure for table `tp_appointments`
-- 

CREATE TABLE `tp_appointments` (
  `appointment_id` bigint(20) NOT NULL auto_increment,
  `appointment_subject` varchar(250) default NULL,
  `appointment_from_date` datetime NOT NULL,
  `appointment_to_date` datetime NOT NULL,
  `appointment_date_created` date NOT NULL,
  `appointment_created_by` bigint(20) NOT NULL,
  `applicant_id` bigint(20) NOT NULL,
  `position_id` int(11) default NULL,
  `position_step_id` int(11) default NULL,
  `remind_me` int(11) NOT NULL,
  `remind_attendee` int(11) NOT NULL,
  `remind_applicant` int(11) NOT NULL,
  PRIMARY KEY  (`appointment_id`),
  KEY `applicant_id` (`applicant_id`),
  KEY `position_id` (`position_id`),
  KEY `position_step_id` (`position_step_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;


-- 
-- Table structure for table `tp_branches`
-- 

CREATE TABLE `tp_branches` (
  `branch_id` int(11) NOT NULL auto_increment,
  `branch_name` varchar(100) NOT NULL,
  `branch_status` char(1) NOT NULL default '1',
  PRIMARY KEY  (`branch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=14;

-- 
-- Dumping data for table `tp_branches`
-- 

-- --------------------------------------------------------

-- 
-- Table structure for table `tp_cities`
-- 

CREATE TABLE `tp_cities` (
  `city_id` bigint(20) NOT NULL auto_increment,
  `city_name` varchar(50) NOT NULL,
  PRIMARY KEY  (`city_id`),
  UNIQUE KEY `city_name` (`city_name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- 
-- Dumping data for table `tp_cities`
-- 

INSERT INTO `tp_cities` (`city_name`) VALUES ('Ahmedabad'),
('Amritsar'),
('Aurangabad'),
('Banglore'),
('Chandigarh'),
('Chennai'),
('Coimbatore'),
('Delhi'),
('Gandhinagar'),
('Guwahati'),
('Gwalior'),
('Hyderabad'),
('Indore'),
('Jabalpur'),
('Jaipur'),
('Jalgaon'),
('Jamnagar'),
('Jhansi'),
('Kolhapur'),
('Kolkata'),
('Lucknow'),
('Mumbai'),
('Mysore'),
('Nagpur'),
('Nanded'),
('Nasik'),
('Panji'),
('Patna');

-- --------------------------------------------------------

-- 
-- Table structure for table `tp_communications`
-- 

CREATE TABLE `tp_communications` (
  `communication_id` bigint(20) NOT NULL auto_increment,
  `communication_type_id` tinyint(4) NOT NULL,
  `applicant_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `communication_date` datetime NOT NULL,
  `communication_text` text NOT NULL,
  `phone_no` varchar(100) default NULL,
  PRIMARY KEY  (`communication_id`),
  KEY `applicant_id` (`applicant_id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;




-- --------------------------------------------------------

-- --------------------------------------------------------

-- 
-- Table structure for table `tp_degrees`
-- 

CREATE TABLE `tp_degrees` (
  `degree_id` smallint(6) NOT NULL auto_increment,
  `degree_title` varchar(250) NOT NULL,
  `degree_level` tinyint(4) NOT NULL,
  `degree_status` char(1) NOT NULL default '1',
  PRIMARY KEY  (`degree_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

-- 
-- Dumping data for table `tp_degrees`
-- 

insert into `tp_degrees` (`degree_id`,`degree_title`,`degree_level`,`degree_status`) values (1,'BCom',30,'1');
insert into `tp_degrees` (`degree_id`,`degree_title`,`degree_level`,`degree_status`) values (2,'BSc',30,'1');
insert into `tp_degrees` (`degree_id`,`degree_title`,`degree_level`,`degree_status`) values (3,'BCS',30,'1');
insert into `tp_degrees` (`degree_id`,`degree_title`,`degree_level`,`degree_status`) values (4,'MCS',40,'1');
insert into `tp_degrees` (`degree_id`,`degree_title`,`degree_level`,`degree_status`) values (5,'MSc',40,'1');
insert into `tp_degrees` (`degree_id`,`degree_title`,`degree_level`,`degree_status`) values (6,'MCom',40,'1');
insert into `tp_degrees` (`degree_id`,`degree_title`,`degree_level`,`degree_status`) values (7,'MCA',40,'1');
insert into `tp_degrees` (`degree_id`,`degree_title`,`degree_level`,`degree_status`) values (11,'MCM',40,'1');
insert into `tp_degrees` (`degree_id`,`degree_title`,`degree_level`,`degree_status`) values (12,'BE',50,'1');
insert into `tp_degrees` (`degree_id`,`degree_title`,`degree_level`,`degree_status`) values (13,'ME',60,'1');
insert into `tp_degrees` (`degree_id`,`degree_title`,`degree_level`,`degree_status`) values (14,'MBA',60,'1');

-- --------------------------------------------------------

-- 
-- Table structure for table `tp_departments`
-- 

CREATE TABLE `tp_departments` (
  `dept_id` int(10) NOT NULL auto_increment,
  `dept_name` varchar(50) NOT NULL default '',
  `dept_status` char(1) NOT NULL default '1',
  PRIMARY KEY  (`dept_id`),
  UNIQUE KEY `dept_name` (`dept_name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;


-- 
-- Table structure for table `tp_email_templates`
-- 

CREATE TABLE `tp_email_templates` (
  `EMAIL_TEMPLATE` varchar(50) NOT NULL,
  `EMAIL_SUBJECT` varchar(150) NOT NULL,
  `EMAIL_FROM` varchar(150) NOT NULL,
  `EMAIL_TYPE` tinyint(4) NOT NULL default '0',
  `EMAIL_DEFAULT_BODY` text NOT NULL,
  PRIMARY KEY  (`EMAIL_TEMPLATE`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- 
-- Dumping data for table `tp_email_templates`
-- 

INSERT INTO `tp_email_templates` (`EMAIL_TEMPLATE`, `EMAIL_SUBJECT`, `EMAIL_FROM`, `EMAIL_TYPE`, `EMAIL_DEFAULT_BODY`) VALUES ('email_forgot_password', 'Talentpool password remainder', 'admin@talentpool.com', 0, 'Dear _%user_name%_,\r\n\r\nYour new password is: _%new_password%_ \r\nIf you have any difficulty, please\r\nwrite to Customer Support at  customercare@talentpool.com.\r\n\r\nSincerely,\r\nTalentpool Team\r\n\r\n \r\nNote: This message was sent to you by an automated system. Please do not\r\nreply to it.   \r\n');

-- --------------------------------------------------------

-- 
-- Table structure for table `tp_grades`
-- 

CREATE TABLE `tp_grades` (
  `grade_id` tinyint(4) NOT NULL auto_increment,
  `grade_title` varchar(100) NOT NULL,
  `grade_desc` varchar(100) NOT NULL,
  PRIMARY KEY  (`grade_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;



-- 
-- Table structure for table `tp_inbox_email_attachments`
-- 

CREATE TABLE `tp_inbox_email_attachments` (
  `attachment_id` bigint(20) NOT NULL auto_increment,
  `email_id` bigint(20) NOT NULL,
  `attachment_file_path` varchar(150) NOT NULL,
  `attachment_original_file_name` varchar(150) default NULL,
  `attachment_content_type` varchar(150) default NULL,
  `attachment_content_id` varchar(150) default NULL,
  `attachment_type` char(1) NOT NULL default '1',
  `attachment_size` bigint(20) NOT NULL default '0',
  PRIMARY KEY  (`attachment_id`),
  KEY `email_id` (`email_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- 
-- Table structure for table `tp_inbox_email_read`
-- 

CREATE TABLE `tp_inbox_email_read` (
  `email_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  KEY `email_id` (`email_id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- 
-- Table structure for table `tp_inbox_emails`
-- 

CREATE TABLE `tp_inbox_emails` (
  `email_id` bigint(20) NOT NULL auto_increment,
  `inbox_id` smallint(6) NOT NULL,
  `email_from` varchar(100) NOT NULL,
  `email_to` text NOT NULL,
  `email_cc` text,
  `email_bcc` text,
  `email_subject` varchar(250) NOT NULL,
  `email_date_send` datetime default NULL,
  `email_date_received` datetime default NULL,
  `email_textbody` longtext,
  `email_htmlbody` longtext,
  `email_size` bigint(20) NOT NULL,
  `folder_id` int(11) NOT NULL default '1',
  PRIMARY KEY  (`email_id`),
  KEY `inbox_id` (`inbox_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;


-- 
-- Table structure for table `tp_inbox_folders`
-- 

CREATE TABLE `tp_inbox_folders` (
  `folder_id` int(11) NOT NULL,
  `folder_name` varchar(100) NOT NULL,
  `inbox_id` bigint(20) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- 
-- Dumping data for table `tp_inbox_folders`
-- 


-- --------------------------------------------------------

-- 
-- Table structure for table `tp_inbox_settings`
-- 

CREATE TABLE `tp_inbox_settings` (
  `inbox_id` smallint(6) NOT NULL auto_increment,
  `inbox_email` varchar(100) NOT NULL,
  `inbox_username` varchar(100) NOT NULL,
  `inbox_password` varchar(50) NOT NULL,
  `inbox_smtp_host` varchar(100) NOT NULL,
  `inbox_pop_host` varchar(100) NOT NULL,
  `inbox_polling_duration` int(11) NOT NULL default '0',
  `inbox_status` char(1) NOT NULL default '1',
  PRIMARY KEY  (`inbox_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

-- 
-- Dumping data for table `tp_inbox_settings`
-- 

INSERT INTO `tp_inbox_settings` (`inbox_id`, `inbox_email`, `inbox_username`, `inbox_password`, `inbox_smtp_host`, `inbox_pop_host`, `inbox_polling_duration`, `inbox_status`) VALUES (1, '', '', '', '', '', 0, '1');

-- --------------------------------------------------------

-- 
-- Table structure for table `tp_institutes`
-- 

CREATE TABLE `tp_institutes` (
  `institute_id` bigint(20) NOT NULL auto_increment,
  `institute_name` varchar(150) NOT NULL,
  PRIMARY KEY  (`institute_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=49 ;

-- 
-- Dumping data for table `tp_institutes`
-- 

INSERT INTO `tp_institutes` (`institute_id`, `institute_name`) VALUES (1, 'IIT-Mumbai'),
(2, 'IIT-Guwahati'),
(3, 'IIT-Chennai'),
(4, 'IIT-Delhi'),
(5, 'IIT-Kanpur'),
(6, 'IIT-Kharagpur '),
(7, 'IIT Roorkee'),
(8, 'IISC-Bangalore'),
(9, 'Motilal Nehru Institute of Technology- Allahabad'),
(10, '( REC) - National Institute of Technology Calicut New'),
(11, 'Regional Engineering College - Durgapur'),
(12, '( REC )- National Institute of technology - Hamirpur ( H.P )'),
(13, 'Dr.Ambedkar Regional Engineering College - Jalandhar'),
(14, 'Malaviya Regional Engineering College - Jaipur'),
(15, 'Regional Engineering College - Kurukshethra'),
(16, 'Visvesvaraya National Institute of Technology - Nagpur ( Maharashtra) New'),
(17, 'Regional Engineering College - Rourkela'),
(18, 'Sardar Vallabhbhai National Institute Of Technology, Surat New'),
(19, 'Regional Engineering College - Suratkal ( Karnataka)'),
(20, 'NATIONAL INSTITUTE OF TECHNOLOGY,SILCHAR'),
(21, 'Regional Engineering College - Tiruchirappalli'),
(22, 'Regional Engineering College - Warangal ( A.P ) New'),
(23, 'Regional Institute of technology- Jamshedpur'),
(24, 'College of Engineering Pune'),
(25, 'VJTI Mumbai'),
(26, 'BITS Pilani'),
(27, 'BITS Ranchi'),
(28, 'IIIT Hyderabad'),
(29, 'ISM Dhanbad'),
(30, 'IT- BHU Varanasi'),
(31, 'IIIT Bangalore'),
(38, 'Rajeev Gandhi University'),
(39, 'Holkar Science college, D. A. V. V Indore'),
(40, 'University of Roorkee, Roorkee'),
(41, 'IIT Delhi '),
(42, 'IIT Bombay'),
(43, 'Osmania university'),
(44, 'JNTU College of Engineering-Kakinada, JNTU Hyderabad.'),
(45, 'V.J.T.I., University of Mumbai'),
(46, 'VIRGINIA TECHS WASHINGTON SEMESTER'),
(47, 'Uiversity of Texas'),
(48, 'Oregon College of Science');

-- --------------------------------------------------------

-- 
-- Table structure for table `tp_notifications`
-- 

CREATE TABLE `tp_notifications` (
  `tp_notification_numberOfMinutes` int(11) NOT NULL,
  `tp_notification_description` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- 
-- Dumping data for table `tp_notifications`
-- 

INSERT INTO `tp_notifications` (`tp_notification_numberOfMinutes`, `tp_notification_description`) VALUES (15, '15 min before'),
(30, '30 min before'),
(60, '1 hr before'),
(120, '2 hrs before'),
(240, '4 hrs before'),
(1440, '1 day before'),
(2880, '2 days before');

-- --------------------------------------------------------

-- 
-- Table structure for table `tp_parser_degrees`
-- 

CREATE TABLE `tp_parser_degrees` (
  `degree_abbr` varchar(50) NOT NULL,
  `degree_expression` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- 
-- Dumping data for table `tp_parser_degrees`
-- 

INSERT INTO `tp_parser_degrees` (`degree_abbr`, `degree_expression`) VALUES ('Aviation', '(?mid)\\bAviation\\b	'),
('B.A.', '(?md)\\bB\\.\\ ?A\\.?\\b'),
('B.A.', '(?mid)\\bBachelor[\\ \\t]+Of[\\ \\t]+Art(s)?\\b'),
('B.Arch.', '(?md)\\bB\\.?\\ ?(Arch|ARCH)\\.?\\b'),
('B.Arch.', '(?mid)\\bB\\.\\ ?Arch\\.?\\b'),
('B.Arch.', '(?mid)\\bBachelor[\\ \\t]+Of[\\ \\t]+(Architecture|Architect|Archetect|Archetecture)\\b'),
('B.B.A.', '(?md)\\bB\\.B\\.A\\.?\\b'),
('B.Com.', '(?md)\\bB\\.?(Com|COM)\\.?\\b'),
('B.Com.', '(?mid)\\bB\\.\\ ?com\\.?\\b'),
('B.Com.', '(?mid)\\bBachelor[\\ \\t]+Of[\\ \\t]+commerce\\b'),
('B.E.', '(?md)\\bB\\.E\\.?\\b'),
('B.E.', '(?mid)\\bBachelor[\\ \\t]+Of[\\ \\t]+(Engg|Engineer(ing)?)\\b'),
('B.Ed.', '(?md)\\bB\\.?Ed\\.?\\b'),
('B.Ed.', '(?mid)\\bB\\.\\ ?Ed\\.?\\b'),
('B.Ed.', '(?mid)\\bBachelor[\\ \\t]+Of[\\ \\t]+(education(s)?|edu)\\.?\\b'),
('B.P.Ed.', '(?md)\\bB\\.?P\\.?Ed\\.?\\b'),
('B.P.Ed.', '(?mid)\\bB\\.P\\.Ed\\.?\\b'),
('B.P.Ed.', '(?mid)\\bBachelor[\\ \\t]+of Physical Education\\b'),
('B.Pharma.', '(?md)\\bB\\.?(Pharm|PHARM|Pharma|PHARMA)\\.?\\b'),
('B.Pharma.', '(?mid)\\bB\\.\\ ?Pharm(a)?\\.?\\b'),
('B.Pharma.', '(?mid)\\bBachelor[\\ \\t]+Of[\\ \\t]+Pharmacy\\.?\\b'),
('B.Sc.', '(?md)\\bB\\.?(Sc|SC)\\.?\\b'),
('B.Sc.', '(?mid)\\bB\\.\\ ?Sc\\.?\\b'),
('B.Sc.', '(?mid)\\bBachelor[\\ \\t]+Of[\\ \\t]+sci(ennce)?\\.?\\b'),
('B.Tech.', '(?md)\\bB\\.?(Tech|TECH|tech)\\.?\\b'),
('B.Tech.', '(?mid)\\bB\\.\\ ?tech\\.?\\b'),
('B.Tech.', '(?mid)\\bBachelor[\\ \\t]+Of[\\ \\t]+Tech(nology|nologies)?\\.?\\b'),
('BCA', '(?md)\\bB\\.?C\\.?A\\.?\\b'),
('BCA', '(?mid)\\bBachelor[\\t\\ ]+of[\\t\\ ]+Computer[\\t\\ ]+Application(s)?\\b'),
('BDS', '(?md)\\bB\\.?D\\.?S\\.?\\b'),
('BDS', '(?mid)\\bBachelor[\\t\\ ]+of[\\t\\ ]+Dental[\\t\\ ]+Surgery\\b'),
('BHM', '(?md)\\bB\\.?H\\.?M\\.?\\b'),
('BHM', '(?mid)\\bBachelor[\\t\\ ]+of[\\t\\ ]+hotel[\\t\\ ]+management\\b'),
('BL', '(?md)\\bBL\\b'),
('BL', '(?mid)\\bBachelor[\\ \\t]+Of[\\ \\t]+Law\\b'),
('BVSc', '(?md)\\bB\\.?V\\.?(Sc|SC)\\b'),
('BVSc', '(?mid)\\bBachelor[\\t\\ ]+Veterinary[\\t\\ ]+Science\\b'),
('CA', '(?md)\\bC\\.?A\\.?\\b'),
('CA', '(?mid)\\bChartered[\\t\\ ]?Accountant\\b'),
('CS', '(?md)\\bC\\.?S\\.?\\b'),
('CS', '(?mid)\\bComputer[\\t\\ ]+Science\\b'),
('Class 12', '(?md)\\b(Class 12|class 12)\\b'),
('ICWA', '(?md)\\bICWA\\b'),
('LLB', '(?md)\\bLL\\.?B\\.?\\b'),
('LLB', '(?mid)\\bBachelor[\\ \\t]+of[\\ \\t]+Laws([\\t\\ ]*\\([\\t\\ ]*(Honours|hon(s)?)\\.?[\\t\\ ]*\\))?'),
('LLM', '(?md)\\bLL\\.?M\\.?\\b'),
('LLM', '(?mid)\\bMaster['']?[s]?[\\ \\t]+of[\\ \\t]+Laws([\\t\\ ]*\\([\\t\\ ]*(Honours|hon(s)?)\\.?[\\t\\ ]*\\))?'),
('M.A.', '(?md)\\bM\\.\\ ?A\\.?\\b'),
('M.A.', '(?mid)\\bMaster['']?[s]?[\\ \\t]+Of[\\ \\t]+Art(s)?\\b'),
('M.Arch.', '(?md)\\bM\\.?\\ ?(Arch|ARCH)\\.?\\b'),
('M.Arch.', '(?mid)\\bM\\.\\ ?Arch\\.?\\b'),
('M.Arch.', '(?mid)\\bMaster['']?[s]?[\\ \\t]+Of[\\ \\t]+(Architecture|Architect|Archetect|Archetecture)\\b'),
('M.Com.', '(?md)\\bM\\.?(Com|COM)\\.?\\b'),
('M.Com.', '(?mid)\\bM\\.\\ ?com\\.?\\b'),
('M.Com.', '(?mid)\\bMaster['']?[s]?[\\ \\t]+Of[\\ \\t]+commerce\\b'),
('M.E.', '(?md)\\bM\\.E\\.?\\b'),
('M.E.', '(?mid)\\bMaster['']?[s]?[\\ \\t]+Of[\\ \\t]+(Engg|Engineer(ing)?)\\b'),
('M.Ed.', '(?md)\\bM\\.?Ed\\.?\\b'),
('M.Ed.', '(?mid)\\bM\\.\\ ?Ed\\.?\\b'),
('M.Ed.', '(?mid)\\bMaster['']?[s]?[\\ \\t]+Of[\\ \\t]+(education(s)?|edu)\\.?\\b'),
('M.Pharma.', '(?md)\\bM\\.?(Pharm|PHARM|Pharma|PHARMA)\\.?\\b'),
('M.Pharma.', '(?mid)\\bM\\.\\ ?Pharm(a)?\\.?\\b'),
('M.Pharma.', '(?mid)\\bMaster['']?[s]?[\\ \\t]+Of[\\ \\t]+Pharmacy\\.?\\b'),
('M.Sc.', '(?md)\\bM\\.?(Sc|SC)\\.?\\b'),
('M.Sc.', '(?mid)\\bM\\.\\ ?Sc\\.?\\b'),
('M.Sc.', '(?mid)\\bMaster['']?[s]?[\\ \\t]+Of[\\ \\t]+sci(ennce)?\\.?\\b'),
('M.Tech.', '(?md)\\bM\\.?(Tech|TECH|tech)\\.?\\b'),
('M.Tech.', '(?mid)\\bM\\.\\ ?tech\\.?\\b'),
('M.Tech.', '(?mid)\\bMaster['']?[s]?[\\ \\t]+Of[\\ \\t]+Tech(nology|nologies)?\\.?\\b'),
('MBA', '(?md)\\b(MBA|M\\.B\\.A\\.?)\\b'),
('MBA', '(?mid)\\bMaster['']?[s]?[\\ \\t]+Of[\\ \\t]+Business[\\t\\ ]+Administration\\b'),
('MBBS', '(?md)\\bMBBS\\b'),
('MBBS', '(?mid)\\bBachelor[\\ \\t]+of[\\ \\t]+Medicine[\\ \\t]+[&\\/\\\\][\\ \\t]+Bachelor[\\ \\t]+of[\\ \\t]+Surgery\\b'),
('MBBS', '(?mid)\\bBachelor[\\ \\t]+of[\\ \\t]+Medicine[\\ \\t]+(and|&)[\\ \\t]+Surgery\\b'),
('MCA', '(?md)\\bMCA\\b'),
('MCA', '(?mid)\\bMaster['']?[s]?[\\ \\t]+of[\\ \\t]+Computer[\\ \\t]+Applications\\b'),
('MCM', '(?md)\\bMCM\\b'),
('MCM', '(?mid)\\bMaster['']?[s]?[\\ \\t]+of[\\ \\t]+Computer[\\ \\t]+Management\\b'),
('MD', '(?md)\\bMD\\b'),
('MD', '(?mid)\\bMaster['']?[s]? Degree\\b'),
('ML', '(?md)\\bML\\b'),
('MPHIL', '(?md)\\bM\\.?(PHIL|Phil|phil)\\b'),
('MPHIL', '(?mid)\\bM\\.\\ ?Phil\\.?\\b'),
('MPHIL', '(?mid)\\bMaster['']?[s]?[\\ \\t]+of[\\ \\t]+Philosophy\\b'),
('MS', '(?md)\\bMS\\b'),
('MS', '(?mid)\\bMaster[\\ \\t]+of[\\ \\t]+Science\\b'),
('MVSc', '(?md)\\bM\\.?V\\.?(Sc|SC)\\b'),
('MVSc', '(?mid)\\bMaster['']?[s]?[\\t\\ ]+Veterinary[\\t\\ ]+Science\\b'),
('PG Diploma', '(?mid)\\bPG[\\t\\ ]+Diploma\\b'),
('PG Diploma', '(?mid)\\bPost[\\t\\ ]+graduate[\\t\\ ]+diploma\\b'),
('PGDCA', '(?md)\\bPGDCA\\b'),
('PGDCA', '(?mid)\\bPost[\\t\\ ]+GraduateDiploma[\\t\\ ]+in[\\t\\ ]+Computer[\\t\\ ]+Applications\\b'),
('PGDM', '(?md)\\bPGDM\\b'),
('PGDM', '(?mid)\\bPost[\\t\\ ]+Graduate[\\t\\ ]+Diploma[\\t\\ ]+in[\\t\\ ]+Management\\b'),
('Ph.D', '(?mid)\\bPh\\.D\\b'),
('Ph.D', '(?mid)\\bDoctor[\\ \\t]+of[\\ \\t]+Philosophy\\b');

-- --------------------------------------------------------

-- 
-- Table structure for table `tp_parser_expressions`
-- 

CREATE TABLE `tp_parser_expressions` (
  `parser_expression_id` int(11) NOT NULL auto_increment,
  `parser_expression_field_name` varchar(100) NOT NULL,
  `parser_expression` varchar(500) NOT NULL,
  `parser_expression_priority` tinyint(4) NOT NULL,
  `parser_expression_desc` varchar(500) NOT NULL,
  PRIMARY KEY  (`parser_expression_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=26 ;

-- 
-- Dumping data for table `tp_parser_expressions`
-- 

INSERT INTO `tp_parser_expressions` (`parser_expression_id`, `parser_expression_field_name`, `parser_expression`, `parser_expression_priority`, `parser_expression_desc`) VALUES (1, 'email', '(?mid)[a-z0-9._\\-]+@([a-z0-9\\-_]+\\.)+(com|net|org|edu|int|mil|gov|arpa|biz|aero|name|coop|info|pro|museum|tv|([a-z]{2}))', 1, 'regular expression for email'),
(2, 'name', '(?mid)(?<=((?<!(father|mother|project|company|organization|institute|thesis|reference).{0,3}\\s{1,10})name\\s{0,5}[:]?[-]?\\s{0,5}))[a-z\\.]+([\\ \\t][\\ \\t]?[a-z\\.]+)?([\\ \\t][\\ \\t]?[a-z\\.]+)?([\\ \\t][\\ \\t]?[a-z\\.]+)?', 1, 'Regular expression for Aditya P. Singh or Aditya P Singh'),
(4, 'name', '(?mid)[a-z][a-z]+[\\ \\t]+[a-z][\\.]?[\\ \\t]+?[a-z][a-z]+[\\s]+', 2, ''),
(5, 'name', '(?mid)\\b[a-z][\\.]?[\\ \\t]+[a-z][\\.]?[\\ \\t]+?[a-z][a-z]+', 3, ''),
(6, 'name', '(?mid)[a-z][a-z]+[\\ \\t]+[a-z][\\.]?[\\ \\t]+?[a-z][\\.]?[\\s]+', 4, ''),
(7, 'name', '(?mid)[a-z][a-z]+[\\ \\t]+[a-z][a-z]+[\\ \\t]+?[a-z][\\.]?[\\s]+', 5, ''),
(8, 'name', '(?mid)^\\s*?[a-z][a-z]+[\\ \\t]+[a-z][a-z]+\\s*$', 6, ''),
(9, 'name', '(?mid)^\\s*?[a-z][a-z]+[\\ \\t]+[a-z][a-z]+[\\ \\t]+[a-z][a-z]+\\s*$', 7, ''),
(10, 'name', '(?md)[A-Z][a-z]+[\\ \\t][\\ \\t]?[A-Z][\\.]?[\\ \\t][\\ \\t]?[A-Z][a-z]+', 10, ''),
(11, 'name', '(?md)[A-Z][A-Z]+[\\ \\t][\\ \\t]?[A-Z][\\.]?[\\ \\t][\\ \\t]?[A-Z][A-Z]+', 11, ''),
(12, 'name', '(?md)[A-Z][a-z]+[\\ \\t][\\ \\t]?[A-Z][a-z]+', 12, ''),
(13, 'name', '(?md)[A-Z][A-Z]+[\\ \\t][\\ \\t]?[A-Z][A-Z]+', 13, ''),
(14, 'name', '(?md)[A-Z][a-z]+[\\ \\t][\\ \\t]?[A-Z][a-z]+[\\ \\t][\\ \\t]?[A-Z][a-z]+', 14, ''),
(15, 'name', '(?md)[A-Z][A-Z]+[\\ \\t][\\ \\t]?[A-Z][A-Z]+[\\ \\t][\\ \\t]?[A-Z][A-Z]+', 15, ''),
(17, 'name', '(?mid)([\\t\\ ]+|^)[a-z]+[\\.]{1}[a-z]+(?:(?=[\\t\\ ])([\\t\\ ][\\t\\ ]?[a-z\\.]+){0,3})', 17, ''),
(18, 'name', '(?mid)^[\\s]*[a-z\\.]+[\\t\\ ][\\t\\ ]?[a-z\\.]+([\\t\\ ][\\t\\ ]?[a-z\\.]+){0,2}\\b', 18, ''),
(19, 'name', '(?mid)[a-z\\.]+[\\t\\ ][\\t\\ ]?[a-z\\.]+([\\t\\ ][\\t\\ ]?[a-z\\.]+){0,2}\\b[\\s]*$', 20, ''),
(20, 'phone', '(?mid)([(]?\\d{2,3}[^0-9a-z\\n\\r,]{1,3})?\\d{2,4}[^0-9a-z\\n\\r,]{1,3}\\d{6,9}\\b', 1, 'Expression for phone'),
(21, 'phone', '(?mid)\\d{10,13}', 2, 'Expression for mobile phone numbers'),
(22, 'phone', '(?mid)\\d{3,}[\\-\\.+()\\ \\t]{1,3}?\\d{3,}[\\-\\.+()\\ \\t]{1,3}\\d{4,}\\b', 3, 'expression for phone 1234 12343'),
(23, 'phone', '(?<=(Phone\\ No|Phone\\ Number|Phone|Tel|Telephone\\ no|Telephone\\ Number|Telephone|Home|Office|Mobile|Cell|Contact\\ Number|Contact\\ No))\\W*(\\d+[^0-9a-z\\n\\r,]+)?(\\d+[^0-9a-z\\n\\r,]+)?(\\d+[^0-9a-z\\n\\r,]+)?\\d+\\b', 4, 'numbers preceeding phone text'),
(25, 'phone', '(?mid)\\d{3,}[\\-\\.+()\\ \\t]{1,3}?\\d{5,}', 5, '');

-- --------------------------------------------------------

-- 
-- Table structure for table `tp_parser_sections`
-- 

CREATE TABLE `tp_parser_sections` (
  `parser_section_heading` varchar(100) NOT NULL,
  `parser_section_expression` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- 
-- Dumping data for table `tp_parser_sections`
-- 

INSERT INTO `tp_parser_sections` (`parser_section_heading`, `parser_section_expression`) VALUES ('Education', '(?mid)^[\\W]*[\\t\\ ]*(Education(al)?|Academic(s)?|Qualification(s)?)[\\t\\ ]*(Qualification|Profile|Chronicle|Record|Background|History)?(s)?[\\W]*$'),
('Education', '(?mid)^[\\W]*[\\t\\ ]*(Education(al)?|Academic(s)?|Qualification(s)?)[\\t\\ ]*(Qualification|Profile|Chronicle|Record|Background|History)?(s)?[\\t\\ ]*[:\\-)]+'),
('Education', '(?mid)^[\\W]*[\\t\\ ]*\\b[a-z]+\\b[\\t\\ ]+(Education|Academic)[\\W]*$'),
('Education', '(?mid)^[\\W]*[\\t\\ ]*(Education|Academic)[\\t\\ ]+\\b[a-z]+\\b[\\W]*$'),
('Skills', '(?mid)^[\\W]*[\\t\\ ]*Skill(s)?[\\t\\ ]*(Summary|Set(s)?|Profile(s)?)?[\\W]*$'),
('Skills', '(?mid)^[\\W]*[\\t\\ ]*Skill(s)?[\\t\\ ]*(Summary|Set(s)?|Profile(s)?)?[\\t\\ ]*[:\\-)]+'),
('Skills', '(?mid)^[\\W]*[\\t\\ ]*\\b[a-z]+\\b[\\t\\ ]+Skill(s)?[\\W]*$'),
('Skills', '(?mid)^[\\W]*[\\t\\ ]*Technical[\\t\\ ]+\\b[a-z]+\\b[\\W]*$'),
('Skills', '(?mid)^[\\W]*[\\t\\ ]*(Skill(s)?[\\t\\ ]+Set[\\t\\ ]+Summary|Software[\\t\\ ]+Knowledge|Tools|Other[\\t\\ ]+Tools|Computer[\\t\\ ]+Proficiency|Operating[\\t\\ ]+System|Technical[\\t\\ ]+Skill[\\t\\ ]+Set|Technical[\\t\\ ]+Experience[\\t\\ ]+Summary)[\\W]*$'),
('Employment', '(?mid)^[\\W]*[\\t\\ ]*(Emp|Employment|Work(ing)?|Experience|Professional|Industrial)[\\t\\ ]*(Summary|Detail(s)?|Profile(s)?|Chronicle|Chronology|History|Exp|Experience)?[\\W]*$'),
('Employment', '(?mid)^[\\W]*[\\t\\ ]*(Emp|Employment|Work(ing)?|Experience|Professional|Industrial)[\\t\\ ]*(Summary|Detail(s)?|Profile(s)?|Chronicle|Chronology|History|Exp|Experience)?[\\t\\ ]*[:\\-)]+'),
('Employment', '(?mid)^[\\W]*[\\t\\ ]*(Project(s)?)[\\t\\ ](Work|Detail(s)?|Description|Handled|Undertaken|Profile)[\\W]*$'),
('Employment', '(?mid)^[\\W]*[\\t\\ ]*(Project(s)?)[\\t\\ ](Work|Detail(s)?|Description|Handled|Undertaken|Profile)[\\t\\ ]*[:\\-)]+'),
('Objective', '(?mid)^[\\W]*[\\t\\ ]*((Career|Professional|Job)[\\t\\ ]+)?(Objective|Goal)(s)?[\\W]*$'),
('Objective', '(?mid)^[\\W]*[\\t\\ ]*((Career|Professional|Job)[\\t\\ ]+)?(Objective|Goal)(s)?[\\W]*[:\\-)]+'),
('Personal', '(?mid)^[\\W]*[\\t\\ ]*Personal[\\t\\ ]+(Detail(s)?|Information|Summary|Status|Profile)?[\\W]*$'),
('Personal', '(?mid)^[\\W]*[\\t\\ ]*Personal[\\t\\ ]+(Detail(s)?|Information|Summary|Status)?[\\t\\ ]*[:\\-)]+'),
('Personal', '(?mid)^[\\W]*[\\t\\ ]*((Present|Permanent|Current|Temporary|Contact)[\\t\\ ]+)?Address[\\W]*$'),
('Personal', '(?mid)^[\\W]*[\\t\\ ]*((Present|Permanent|Current|Temporary|Contact)[\\t\\ ]+)?Address[\\t\\ ]*[:\\-)]+'),
('Personal', '(?mid)^[\\W]*[\\t\\ ]*(Contact|General)[\\t\\ ]+(Info|Information)'),
('Personal', '(?mid)^[\\W]*[\\t\\ ]*(Dob|Date[\\t\\ ]+Of[\\t\\ ]+Birth|E-mail|email|Gender|Name|Nationality|Passport[\\t\\ ]+(No|Number)|Phone[\\t\\ ]+(No|Number)?|Sex|(Mobile|Mobi|Mob|Cell)[\\t\\ ]+(No|Number))[\\W]*$'),
('Personal', '(?mid)^[\\W]*[\\t\\ ]*(Dob|Date[\\t\\ ]+Of[\\t\\ ]+Birth|E-mail|email|Gender|Name|Nationality|Passport[\\t\\ ]+(No|Number)|Phone[\\t\\ ]+(No|Number)?|Sex|(Mobile|Mobi|Mob|Cell)[\\t\\ ]+(No|Number))[\\t\\ ]*[:\\-)]+'),
('Other', '(?mid)^[\\W]*[\\t\\ ]*(Summary|Extra|Addendum(s)?|Achievement(s)?|Reference(s)?|Synopsis|Strength(s)|Elective|Hobbies|Overview|Extra[\\t\\ ]+Curricular[\\t\\ ]+Activities|Special[\\t\\ ]+Achivement(s)?)[\\W]*$'),
('Other', '(?mid)^[\\W]*[\\t\\ ]*(Summary|Extra|Addendum(s)?|Achievement(s)?|Reference(s)?|Synopsis|Strength(s)|Elective|Hobbies|Overview|Extra[\\t\\ ]+Curricular[\\t\\ ]+Activities|Special[\\t\\ ]+Achivement(s)?)[\\t\\ ]+[:\\-)]');

-- --------------------------------------------------------

-- 
-- Table structure for table `tp_position_requirements`
-- 

CREATE TABLE `tp_position_requirements` (
  `requirement_id` int(11) NOT NULL auto_increment,
  `position_id` int(11) NOT NULL,
  `requirement_text` text NOT NULL,
  `requirement_rank` tinyint(4) NOT NULL,
  PRIMARY KEY  (`requirement_id`),
  KEY `position_id` (`position_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;


-- 
-- Table structure for table `tp_position_responsibilities`
-- 

CREATE TABLE `tp_position_responsibilities` (
  `responsibility_id` int(11) NOT NULL auto_increment,
  `position_id` int(11) NOT NULL,
  `responsibility_text` text NOT NULL,
  `responsibility_rank` tinyint(4) NOT NULL,
  PRIMARY KEY  (`responsibility_id`),
  KEY `position_id` (`position_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

-- 
-- Table structure for table `tp_position_skills`
-- 

CREATE TABLE `tp_position_skills` (
  `position_id` int(11) NOT NULL,
  `skill_id` int(11) NOT NULL,
  `skill_status` char(1) NOT NULL default '0',
  KEY `position_id` (`position_id`),
  KEY `skill_id` (`skill_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- 
-- Table structure for table `tp_position_step_traits`
-- 

CREATE TABLE `tp_position_step_traits` (
  `position_step_trait_id` bigint(20) NOT NULL auto_increment,
  `position_step_id` int(11) NOT NULL,
  `trait_title` varchar(250) NOT NULL,
  PRIMARY KEY  (`position_step_trait_id`),
  KEY `position_step_id` (`position_step_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;


-- 
-- Table structure for table `tp_position_steps`
-- 

CREATE TABLE `tp_position_steps` (
  `position_step_id` int(11) NOT NULL auto_increment,
  `position_id` int(11) NOT NULL,
  `step_type_id` tinyint(4) NOT NULL,
  `position_step_title` varchar(100) NOT NULL,
  `position_step_rank` tinyint(4) NOT NULL,
  `position_step_status` char(1) NOT NULL default '1',
  `position_step_description` varchar(250) default NULL,
  PRIMARY KEY  (`position_step_id`),
  KEY `position_id` (`position_id`),
  KEY `step_type_id` (`step_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1  AUTO_INCREMENT=5 ;


-- 
-- Table structure for table `tp_position_users`
-- 

CREATE TABLE `tp_position_users` (
  `position_id` int(11) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `role_id` smallint(6) default NULL,
  UNIQUE KEY `position_id` (`position_id`,`user_id`,`role_id`),
  KEY `user_id` (`user_id`),
  KEY `role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- 
-- Table structure for table `tp_positions`
-- 

CREATE TABLE `tp_positions` (
  `position_id` int(11) NOT NULL auto_increment,
  `position_code` varchar(30) default NULL,
  `position_title` varchar(100) NOT NULL,
  `position_min_exp` decimal(4,2) NOT NULL default '0.00',
  `position_max_exp` decimal(4,2) NOT NULL,
  `position_no_of_openings` smallint(6) NOT NULL,
  `position_current_openings` smallint(6) NOT NULL,
  `position_date_expiry` datetime default NULL,
  `position_date_created` datetime NOT NULL,
  `position_note` text,
  `dept_id` int(10) default NULL,
  `degree_id` smallint(6) NOT NULL,
  `branch_id` int(11) default NULL,
  `position_status` char(1) NOT NULL default '2',
  PRIMARY KEY  (`position_id`),
  KEY `dept_id` (`dept_id`),
  KEY `degree_id` (`degree_id`),
  KEY `branch_id` (`branch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- 
-- Table structure for table `tp_role_tasks`
-- 

CREATE TABLE `tp_role_tasks` (
  `ROLE_ID` smallint(6) NOT NULL,
  `TASK_ID` smallint(6) NOT NULL,
  KEY `ROLE_ID` (`ROLE_ID`),
  KEY `TASK_ID_2` (`TASK_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- 
-- Dumping data for table `tp_role_tasks`
-- 

INSERT INTO `tp_role_tasks` (`ROLE_ID`, `TASK_ID`) VALUES (1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(1, 6),
(1, 7),
(1, 8),
(1, 9),
(1, 10),
(1, 11),
(1, 12),
(1, 13),
(1, 14),
(1, 15),
(1, 16),
(1, 17),
(1, 18),
(1, 19),
(1, 20),
(1, 21),
(1, 22),
(1, 23),
(4, 1),
(4, 2),
(4, 3),
(4, 4),
(4, 5),
(4, 6),
(4, 7),
(4, 8),
(4, 9),
(4, 10),
(4, 11),
(4, 12),
(4, 13),
(4, 14),
(4, 15),
(4, 16),
(4, 17),
(4, 18),
(4, 19),
(4, 20),
(4, 21),
(4, 22),
(4, 23),
(5, 1),
(5, 2),
(5, 4),
(5, 5),
(5, 6),
(5, 8),
(5, 9),
(5, 11),
(5, 12),
(5, 25),
(2, 1),
(2, 2),
(2, 3),
(2, 4),
(2, 5),
(2, 6),
(2, 8),
(2, 9),
(2, 11),
(2, 12),
(2, 16),
(2, 17),
(2, 18),
(2, 19),
(2, 20),
(3, 1),
(3, 2),
(3, 3),
(3, 4),
(3, 5),
(3, 6),
(3, 7),
(3, 8),
(3, 9),
(3, 10),
(3, 11),
(3, 12),
(3, 13),
(3, 14),
(3, 15),
(3, 19),
(3, 20),
(3, 22),
(3, 21),
(5, 3),
(1, 24),
(1, 25),
(4, 24),
(4, 25),
(2, 15),
(2, 25),
(3, 25),
(5, 15),
(5, 20);

-- --------------------------------------------------------

-- 
-- Table structure for table `tp_roles`
-- 

CREATE TABLE `tp_roles` (
  `ROLE_ID` smallint(6) NOT NULL,
  `ROLE_TITLE` varchar(100) NOT NULL,
  PRIMARY KEY  (`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- 
-- Dumping data for table `tp_roles`
-- 

INSERT INTO `tp_roles` (`ROLE_ID`, `ROLE_TITLE`) VALUES (1, 'Administrator'),
(2, 'Requisitioner'),
(3, 'Recruiter'),
(4, 'HR Manager'),
(5, 'Interviewer');

-- --------------------------------------------------------

-- 
-- Table structure for table `tp_saved_searches`
-- 

CREATE TABLE `tp_saved_searches` (
  `search_id` bigint(11) NOT NULL auto_increment,
  `search_name` varchar(100) default NULL,
  `search_date` datetime default NULL,
  `search_by_user_id` varchar(100) default NULL,
  `search_degree_id` smallint(6) default NULL,
  `search_branch_name` varchar(100) default NULL,
  `search_min_experience` decimal(4,2) default NULL,
  `search_max_experience` decimal(4,2) default NULL,
  `search_skill_ids` varchar(150) default NULL,
  `search_import_duration` int(4) default NULL,
  `search_current_location` varchar(250) default NULL,
  `search_source_id` int(11) default NULL,
  `search_last_contacted` int(4) default NULL,
  `search_include_candidates` varchar(2) default NULL,
  PRIMARY KEY  (`search_id`),
  UNIQUE KEY `search_name` (`search_name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;



-- --------------------------------------------------------

-- 
-- Table structure for table `tp_skill_category`
-- 

CREATE TABLE `tp_skill_category` (
  `skill_category_id` int(11) NOT NULL,
  `skill_category_description` varchar(250) NOT NULL,
  PRIMARY KEY  (`skill_category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- 
-- Dumping data for table `tp_skill_category`
-- 

INSERT INTO `tp_skill_category` (`skill_category_id`, `skill_category_description`) VALUES (1, 'Others');

ALTER TABLE `tp_skill_category` CHANGE `skill_category_id` `skill_category_id` INT( 11 ) NOT NULL AUTO_INCREMENT;

-- --------------------------------------------------------

-- 
-- Table structure for table `tp_skills`
-- 

CREATE TABLE `tp_skills` (
  `skill_id` int(11) NOT NULL auto_increment,
  `skill_category_id` int(11) NOT NULL,
  `skill` varchar(250) NOT NULL,
  `skill_status` char(1) NOT NULL default '1',
  PRIMARY KEY  (`skill_id`),
  KEY `skill_category_id` (`skill_category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;


-- --------------------------------------------------------

-- 
-- Table structure for table `tp_source_types`
-- 

CREATE TABLE `tp_source_types` (
  `source_type_id` smallint(6) NOT NULL,
  `source_type` varchar(250) NOT NULL,
  PRIMARY KEY  (`source_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- 
-- Dumping data for table `tp_source_types`
-- 

INSERT INTO `tp_source_types` (`source_type_id`, `source_type`) VALUES (1, 'Referral'),
(2, 'Employment Agency'),
(3, 'Job Sites');


ALTER TABLE `tp_source_types` CHANGE `source_type_id` `source_type_id` SMALLINT( 6 ) NOT NULL AUTO_INCREMENT;
-- --------------------------------------------------------

-- 
-- Table structure for table `tp_sources`
-- 

CREATE TABLE `tp_sources` (
  `source_id` int(11) NOT NULL auto_increment,
  `source_type_id` smallint(6) NOT NULL,
  `source_title` varchar(250) NOT NULL,
  `source_status` char(1) NOT NULL default '1',
  PRIMARY KEY  (`source_id`),
  KEY `source_type_id` (`source_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

-- 
-- Dumping data for table `tp_sources`
-- 

INSERT INTO `tp_sources` (`source_id`, `source_type_id`, `source_title`, `source_status`) VALUES (1, 1, 'Referral', '1'),
(2, 3, 'Naukri.com', '1'),
(3, 3, 'Monster.com', '1'),
(4, 3, 'Hireme.com', '1');


-- --------------------------------------------------------

-- 
-- Table structure for table `tp_step_types`
-- 

CREATE TABLE `tp_step_types` (
  `step_type_id` tinyint(4) NOT NULL,
  `step_type_title` varchar(100) NOT NULL,
  `step_type_desc` varchar(250) default NULL,
  `step_type_image` varchar(100) default NULL,
  `step_type_default` char(1) NOT NULL default '0',
  PRIMARY KEY  (`step_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ;

-- 
-- Dumping data for table `tp_step_types`
-- 

INSERT INTO `tp_step_types` (`step_type_id`, `step_type_title`, `step_type_desc`, `step_type_image`, `step_type_default`) VALUES (1, 'Shortlist', NULL, NULL, '1'),
(2, 'Offer Made', NULL, NULL, '1'),
(3, 'Interview', NULL, NULL, '0'),
(4, 'Written Test', NULL, NULL, '0'),
(5, 'Group Discussion', NULL, NULL, '0');

-- --------------------------------------------------------

-- 
-- Table structure for table `tp_task_overrides`
-- 

CREATE TABLE `tp_task_overrides` (
  `TASK_ID` smallint(6) NOT NULL,
  `USER_ID` bigint(20) NOT NULL,
  `OVERRIDE_TYPE` tinyint(4) NOT NULL default '0',
  KEY `USER_ID` (`USER_ID`),
  KEY `TASK_ID` (`TASK_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



-- --------------------------------------------------------

-- 
-- Table structure for table `tp_tmp_attachments`
-- 

CREATE TABLE `tp_tmp_attachments` (
  `attachment_id` bigint(20) NOT NULL auto_increment,
  `email_id` char(12) NOT NULL,
  `attachment_file_path` varchar(100) NOT NULL,
  `attachment_original_file_name` varchar(100) default NULL,
  `attachment_content_type` varchar(100) default NULL,
  `attachment_content_id` varchar(150) default NULL,
  `attachment_type` char(1) NOT NULL default '1',
  `attachment_size` bigint(20) NOT NULL default '0',
  `attachment_date` datetime NOT NULL,
  `attachment_confirmed` char(1) NOT NULL default '0',
  `attachment_removed` char(1) NOT NULL default '0',
  PRIMARY KEY  (`attachment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;



-- 
-- Table structure for table `tp_user_roles`
-- 

CREATE TABLE `tp_user_roles` (
  `USER_ID` bigint(20) NOT NULL,
  `ROLE_ID` smallint(6) NOT NULL,
  KEY `USER_ID` (`USER_ID`),
  KEY `ROLE_ID` (`ROLE_ID`),
  KEY `ROLE_ID_2` (`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- 
-- Dumping data for table `tp_user_roles`
-- 

INSERT INTO `tp_user_roles` (`USER_ID`, `ROLE_ID`) VALUES (1, 1);

-- --------------------------------------------------------

-- 
-- Table structure for table `tp_users`
-- 

CREATE TABLE `tp_users` (
  `USER_ID` bigint(20) NOT NULL auto_increment,
  `USER_NAME` varchar(25) NOT NULL,
  `USER_FNAME` varchar(20) default NULL,
  `USER_LNAME` varchar(20) default NULL,
  `USER_PASSWORD` varchar(50) NOT NULL,
  `USER_EMAIL` varchar(50) NOT NULL,
  `USER_LAST_LOGIN` datetime default NULL,
  `USER_STATUS` char(1) NOT NULL default '1',
  PRIMARY KEY  (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

-- 
-- Dumping data for table `tp_users`
-- 

INSERT INTO `tp_users` (`USER_ID`, `USER_NAME`, `USER_FNAME`, `USER_LNAME`, `USER_PASSWORD`, `USER_EMAIL`, `USER_LAST_LOGIN`, `USER_STATUS`) VALUES (1, 'admin', 'Super', 'Admin', '601F1889667EFAEBB33B8C12572835DA3F027F78', 'support@nitman.co.in', '2006-09-04 09:53:48', '1');

-- 
-- Constraints for dumped tables
-- 

-- 
-- Constraints for table `tp_applicant_educational_info`
-- 
ALTER TABLE `tp_applicant_educational_info`
  ADD CONSTRAINT `tp_applicant_educational_info_ibfk_1` FOREIGN KEY (`applicant_id`) REFERENCES `tp_applicants` (`applicant_id`),
  ADD CONSTRAINT `tp_applicant_educational_info_ibfk_2` FOREIGN KEY (`degree_id`) REFERENCES `tp_degrees` (`degree_id`);

-- 
-- Constraints for table `tp_applicant_employment`
-- 
ALTER TABLE `tp_applicant_employment`
  ADD CONSTRAINT `tp_applicant_employment_ibfk_1` FOREIGN KEY (`applicant_id`) REFERENCES `tp_applicants` (`applicant_id`);

-- 
-- Constraints for table `tp_applicant_inbox_email_attachments`
-- 
ALTER TABLE `tp_applicant_inbox_email_attachments`
  ADD CONSTRAINT `tp_applicant_inbox_email_attachments_ibfk_1` FOREIGN KEY (`email_id`) REFERENCES `tp_applicant_inbox_emails` (`email_id`);

-- 
-- Constraints for table `tp_applicant_inbox_emails`
-- 
ALTER TABLE `tp_applicant_inbox_emails`
  ADD CONSTRAINT `tp_applicant_inbox_emails_ibfk_1` FOREIGN KEY (`applicant_id`) REFERENCES `tp_applicants` (`applicant_id`),
  ADD CONSTRAINT `tp_applicant_inbox_emails_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `tp_users` (`USER_ID`);

-- 
-- Constraints for table `tp_applicant_selection_process`
-- 
ALTER TABLE `tp_applicant_selection_process`
  ADD CONSTRAINT `tp_applicant_selection_process_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `tp_users` (`USER_ID`),
  ADD CONSTRAINT `tp_applicant_selection_process_ibfk_2` FOREIGN KEY (`applicant_id`) REFERENCES `tp_applicants` (`applicant_id`);

-- 
-- Constraints for table `tp_applicant_selection_process_traits`
-- 
ALTER TABLE `tp_applicant_selection_process_traits`
  ADD CONSTRAINT `tp_applicant_selection_process_traits_ibfk_1` FOREIGN KEY (`process_id`) REFERENCES `tp_applicant_selection_process` (`process_id`),
  ADD CONSTRAINT `tp_applicant_selection_process_traits_ibfk_2` FOREIGN KEY (`position_step_trait_id`) REFERENCES `tp_position_step_traits` (`position_step_trait_id`);

-- 
-- Constraints for table `tp_applicant_skills`
-- 
ALTER TABLE `tp_applicant_skills`
  ADD CONSTRAINT `tp_applicant_skills_ibfk_1` FOREIGN KEY (`applicant_id`) REFERENCES `tp_applicants` (`applicant_id`),
  ADD CONSTRAINT `tp_applicant_skills_ibfk_2` FOREIGN KEY (`skill_id`) REFERENCES `tp_skills` (`skill_id`);

-- 
-- Constraints for table `tp_applicants`
-- 
ALTER TABLE `tp_applicants`
  ADD CONSTRAINT `tp_applicants_ibfk_1` FOREIGN KEY (`applicant_step_id`) REFERENCES `tp_position_steps` (`position_step_id`),
  ADD CONSTRAINT `tp_applicants_ibfk_2` FOREIGN KEY (`source_id`) REFERENCES `tp_sources` (`source_id`),
  ADD CONSTRAINT `tp_applicants_ibfk_3` FOREIGN KEY (`applicant_position_id`) REFERENCES `tp_positions` (`position_id`);

-- 
-- Constraints for table `tp_appointment_attendees`
-- 
ALTER TABLE `tp_appointment_attendees`
  ADD CONSTRAINT `tp_appointment_attendees_ibfk_1` FOREIGN KEY (`appointment_id`) REFERENCES `tp_appointments` (`appointment_id`),
  ADD CONSTRAINT `tp_appointment_attendees_ibfk_2` FOREIGN KEY (`attendee_id`) REFERENCES `tp_users` (`USER_ID`);

-- 
-- Constraints for table `tp_appointments`
-- 
ALTER TABLE `tp_appointments`
  ADD CONSTRAINT `tp_appointments_ibfk_1` FOREIGN KEY (`applicant_id`) REFERENCES `tp_applicants` (`applicant_id`),
  ADD CONSTRAINT `tp_appointments_ibfk_2` FOREIGN KEY (`position_id`) REFERENCES `tp_positions` (`position_id`),
  ADD CONSTRAINT `tp_appointments_ibfk_3` FOREIGN KEY (`position_step_id`) REFERENCES `tp_position_steps` (`position_step_id`);

-- 
-- Constraints for table `tp_communications`
-- 
ALTER TABLE `tp_communications`
  ADD CONSTRAINT `tp_communications_ibfk_1` FOREIGN KEY (`applicant_id`) REFERENCES `tp_applicants` (`applicant_id`),
  ADD CONSTRAINT `tp_communications_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `tp_users` (`USER_ID`);


-- 
-- Constraints for table `tp_inbox_email_attachments`
-- 
ALTER TABLE `tp_inbox_email_attachments`
  ADD CONSTRAINT `tp_inbox_email_attachments_ibfk_1` FOREIGN KEY (`email_id`) REFERENCES `tp_inbox_emails` (`email_id`);

-- 
-- Constraints for table `tp_inbox_email_read`
-- 
ALTER TABLE `tp_inbox_email_read`
  ADD CONSTRAINT `tp_inbox_email_read_ibfk_1` FOREIGN KEY (`email_id`) REFERENCES `tp_inbox_emails` (`email_id`),
  ADD CONSTRAINT `tp_inbox_email_read_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `tp_users` (`USER_ID`);

-- 
-- Constraints for table `tp_inbox_emails`
-- 
ALTER TABLE `tp_inbox_emails`
  ADD CONSTRAINT `tp_inbox_emails_ibfk_1` FOREIGN KEY (`inbox_id`) REFERENCES `tp_inbox_settings` (`inbox_id`);

-- 
-- Constraints for table `tp_position_requirements`
-- 
ALTER TABLE `tp_position_requirements`
  ADD CONSTRAINT `tp_position_requirements_ibfk_1` FOREIGN KEY (`position_id`) REFERENCES `tp_positions` (`position_id`);

-- 
-- Constraints for table `tp_position_responsibilities`
-- 
ALTER TABLE `tp_position_responsibilities`
  ADD CONSTRAINT `tp_position_responsibilities_ibfk_1` FOREIGN KEY (`position_id`) REFERENCES `tp_positions` (`position_id`);

-- 
-- Constraints for table `tp_position_skills`
-- 
ALTER TABLE `tp_position_skills`
  ADD CONSTRAINT `tp_position_skills_ibfk_1` FOREIGN KEY (`position_id`) REFERENCES `tp_positions` (`position_id`),
  ADD CONSTRAINT `tp_position_skills_ibfk_2` FOREIGN KEY (`skill_id`) REFERENCES `tp_skills` (`skill_id`);

-- 
-- Constraints for table `tp_position_step_traits`
-- 
ALTER TABLE `tp_position_step_traits`
  ADD CONSTRAINT `tp_position_step_traits_ibfk_2` FOREIGN KEY (`position_step_id`) REFERENCES `tp_position_steps` (`position_step_id`);

-- 
-- Constraints for table `tp_position_steps`
-- 
ALTER TABLE `tp_position_steps`
  ADD CONSTRAINT `tp_position_steps_ibfk_1` FOREIGN KEY (`position_id`) REFERENCES `tp_positions` (`position_id`),
  ADD CONSTRAINT `tp_position_steps_ibfk_2` FOREIGN KEY (`step_type_id`) REFERENCES `tp_step_types` (`step_type_id`);

-- 
-- Constraints for table `tp_position_users`
-- 
ALTER TABLE `tp_position_users`
  ADD CONSTRAINT `tp_position_users_ibfk_1` FOREIGN KEY (`position_id`) REFERENCES `tp_positions` (`position_id`),
  ADD CONSTRAINT `tp_position_users_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `tp_users` (`USER_ID`),
  ADD CONSTRAINT `tp_position_users_ibfk_3` FOREIGN KEY (`role_id`) REFERENCES `tp_roles` (`ROLE_ID`);

-- 
-- Constraints for table `tp_positions`
-- 
ALTER TABLE `tp_positions`
  ADD CONSTRAINT `tp_positions_ibfk_1` FOREIGN KEY (`dept_id`) REFERENCES `tp_departments` (`dept_id`),
  ADD CONSTRAINT `tp_positions_ibfk_2` FOREIGN KEY (`degree_id`) REFERENCES `tp_degrees` (`degree_id`),
  ADD CONSTRAINT `tp_positions_ibfk_3` FOREIGN KEY (`branch_id`) REFERENCES `tp_branches` (`branch_id`);

-- 
-- Constraints for table `tp_role_tasks`
-- 
ALTER TABLE `tp_role_tasks`
  ADD CONSTRAINT `tp_role_tasks_ibfk_1` FOREIGN KEY (`ROLE_ID`) REFERENCES `tp_roles` (`ROLE_ID`);

-- 
-- Constraints for table `tp_skills`
-- 
ALTER TABLE `tp_skills`
  ADD CONSTRAINT `tp_skills_ibfk_2` FOREIGN KEY (`skill_category_id`) REFERENCES `tp_skill_category` (`skill_category_id`);

-- 
-- Constraints for table `tp_sources`
-- 
ALTER TABLE `tp_sources`
  ADD CONSTRAINT `tp_sources_ibfk_1` FOREIGN KEY (`source_type_id`) REFERENCES `tp_source_types` (`source_type_id`);

-- 
-- Constraints for table `tp_task_overrides`
-- 
ALTER TABLE `tp_task_overrides`
  ADD CONSTRAINT `tp_task_overrides_ibfk_1` FOREIGN KEY (`USER_ID`) REFERENCES `tp_users` (`USER_ID`);

-- 
-- Constraints for table `tp_user_roles`
-- 
ALTER TABLE `tp_user_roles`
  ADD CONSTRAINT `tp_user_roles_ibfk_1` FOREIGN KEY (`USER_ID`) REFERENCES `tp_users` (`USER_ID`),
  ADD CONSTRAINT `tp_user_roles_ibfk_2` FOREIGN KEY (`ROLE_ID`) REFERENCES `tp_roles` (`ROLE_ID`);

--
-- 07092006 build changes
--
CREATE TABLE `tp_appointment_status` (
  `appointment_status_id` tinyint(4) NOT NULL,
  `appointment_status_text` varchar(40) NOT NULL,
  PRIMARY KEY  (`appointment_status_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- 
-- Dumping data for table `tp_appointment_status`
-- 

INSERT INTO `tp_appointment_status` (`appointment_status_id`, `appointment_status_text`) VALUES (1, 'Tentative Interview Schedule'),
(2, 'Confirm Interview Schedule'),
(3, 'Reschedule Interview');

ALTER TABLE `tp_appointments` ADD `appointment_status_id` TINYINT NOT NULL DEFAULT '1';

ALTER TABLE `tp_appointments`
  ADD CONSTRAINT `tp_appointments_ibfk_4` FOREIGN KEY (`appointment_status_id`) REFERENCES `tp_appointment_status` (`appointment_status_id`);

INSERT INTO `tp_role_tasks` ( `ROLE_ID` , `TASK_ID` ) 
VALUES ('1', '26');


ALTER TABLE `tp_sources` ADD UNIQUE (`source_title`);

--
-- 09092006 build changes
--
CREATE TABLE `tp_skills_aliases` (
`skill_id` INT NOT NULL ,
`skill_alias` VARCHAR( 150 ) NOT NULL 
)  ENGINE=InnoDB DEFAULT CHARSET=latin1;


ALTER TABLE `tp_skills_aliases`
ADD CONSTRAINT `tp_skills_aliases_ibfk_1` FOREIGN KEY (`skill_id`) REFERENCES `tp_skills` (`skill_id`);

ALTER TABLE `tp_skills_aliases` ADD UNIQUE (`skill_alias` );

ALTER TABLE `tp_inbox_settings` ADD `inbox_display_name` VARCHAR( 150 ) NULL ;


--
-- Give access to applicant list menu
--
INSERT INTO `tp_role_tasks` (`ROLE_ID`, `TASK_ID`) VALUES 
(1, 27),
(4, 27),
(5, 27),
(2, 27),
(3, 27);

CREATE TABLE `tp_bugs` (
`bug_id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
`bug_desc` TEXT NOT NULL ,
`user_id` BIGINT NOT NULL ,
`bug_date` DATETIME NOT NULL 
) ;

--
-- add one more parser expression
--
INSERT INTO `tp_parser_expressions` ( `parser_expression_id` , `parser_expression_field_name` , `parser_expression` , `parser_expression_priority` , `parser_expression_desc` ) 
VALUES (
'26', 'forward_header', '(?mid)(^(From|Subject|To|Sent|Date)[:\\-\\s]+[^\\n]*?\\n){3,4}', '1', 'remove email forward headers if exist'
);

--
-- Added one more appointment status.
--
INSERT INTO `tp_appointment_status` (`appointment_status_id`, `appointment_status_text`) VALUES (4, 'No Show');

--
-- added column
--
ALTER TABLE `tp_position_steps` ADD `position_step_isoptional` TINYINT NOT NULL DEFAULT '0';

--
-- ADD CASCADE ON DELETE FOR SKILLS AND ALIASES
--
ALTER TABLE `tp_skills_aliases`  DROP FOREIGN KEY `tp_skills_aliases_ibfk_1`;

ALTER TABLE `tp_skills_aliases`
ADD CONSTRAINT `tp_skills_aliases_ibfk_1` FOREIGN KEY (`skill_id`) REFERENCES `tp_skills` (`skill_id`) ON DELETE CASCADE;

--
-- Change Offer Made to Make Offer
--
UPDATE `tp_step_types` SET `step_type_title` = 'Make Offer' WHERE `step_type_id` =2;

--
-- ADD CASCADE ON DELETE FOR ATTENDE 
--
ALTER TABLE `tp_appointment_attendees` DROP FOREIGN KEY `tp_appointment_attendees_ibfk_1`;

ALTER TABLE `tp_appointment_attendees`
  ADD CONSTRAINT `tp_appointment_attendees_ibfk_1` FOREIGN KEY (`appointment_id`) REFERENCES `tp_appointments` (`appointment_id`)
ON DELETE CASCADE;

--
-- Table  for Messaging Module TP_MESSAGES
--
CREATE TABLE `tp_messages` (
  `message_id` bigint(20) NOT NULL auto_increment,
  `applicant_id` bigint(20) NOT NULL,
  `message_from` bigint(20) NOT NULL,
  `message_to` bigint(20) NOT NULL,
  `message_text` text,
  `message_date` datetime default NULL,
  `message_read` tinyint(4) NOT NULL default '0',
  PRIMARY KEY  (`message_id`),
  KEY `tp_messages_1` (`applicant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Tables to store all the data pertaining to messages' ;


ALTER TABLE `tp_messages`
ADD CONSTRAINT `tp_messages_1` FOREIGN KEY (`applicant_id`) REFERENCES `tp_applicants` (`applicant_id`);

--
-- added column
--
alter table tp_position_step_traits add column `position_step_trait_rank` tinyint(4) not null default 0;

--
-- added column
--
alter table tp_position_steps add column `position_step_has_comment` tinyint(4) not null default '1';

alter table `tp_position_step_traits` change `trait_title` `trait_title` varchar( 250 ) character set latin1 collate latin1_swedish_ci not null default '""';

alter table `tp_position_step_traits` add `is_system_generated` tinyint not null default '0';

create table tp_temp (
	`position_step_id` int(11)
) engine=innodb default charset=latin1;

insert into tp_temp (position_step_id)
	select distinct tp_position_step_traits.position_step_id
	from tp_position_step_traits;
	
insert into tp_temp (position_step_id)
	select tp_position_steps.position_step_id
	from tp_position_steps where step_type_id in (1);	

insert into tp_position_step_traits (position_step_id)
	select distinct tp_temp.position_step_id
	from tp_temp;
		
update tp_position_step_traits set trait_title='Comments', is_system_generated='1' where trait_title='""';

drop table tp_temp;

--
-- added column
--
alter table `tp_applicant_selection_process_traits` add `user_id` bigint not null default '0';

update tp_applicant_selection_process_traits tpaspt
set user_id = (select user_id 
	       from tp_applicant_selection_process tpasp 
	       where tpasp.process_id = tpaspt.process_id);

create table tp_temp (
	process_id  	bigint(20),
	position_step_id_from 	bigint(20),
	position_step_trait_id 	bigint(20),
	trait_comment 	text,
	user_id 	bigint(20)
)engine=innodb default charset=latin1;
	       
insert into tp_temp (process_id, position_step_id_from, trait_comment, user_id)
	select tp_applicant_selection_process.process_id, 
		   tp_applicant_selection_process.position_step_id_from, 
		   tp_applicant_selection_process.process_comment, 
		   tp_applicant_selection_process.user_id
	from tp_applicant_selection_process where tp_applicant_selection_process.process_comment is not null;
	
update tp_temp set position_step_trait_id=(select tppsp.position_step_trait_id 
										   from tp_position_step_traits tppsp 
										   where tppsp.position_step_id = tp_temp.position_step_id_from 
										   and tppsp.is_system_generated=1);	
										   
insert into tp_applicant_selection_process_traits (process_id, position_step_trait_id, trait_comment, user_id)
	select tp_temp.process_id, 
		   tp_temp.position_step_trait_id, 
		   tp_temp.trait_comment, 
		   tp_temp.user_id
	from tp_temp where tp_temp.position_step_trait_id is not null;		
	
drop table tp_temp;		

alter table tp_applicant_selection_process drop column process_comment;

alter table `tp_applicant_selection_process_traits`
add constraint
`tp_applicant_selection_process_traits_1` foreign key (`user_id`) references `tp_users`(`user_id`);

alter table `tp_applicant_selection_process_traits` add unique (
`process_id` ,
`position_step_trait_id` ,
`user_id`
);

create table `tp_temp` (
  `communication_type_id` tinyint(4),
  `applicant_id` bigint(20),
  `user_id` bigint(20),
  `communication_date` datetime,
  `communication_text` text
) engine=innodb default charset=latin1;

insert into tp_temp (applicant_id, communication_text, communication_date) select applicant_id, applicant_notes, applicant_date_created from tp_applicants where applicant_notes is not null;

update tp_temp set communication_type_id=11, user_id=1;

insert into tp_communications (communication_type_id, applicant_id, 
user_id, communication_date, communication_text) select communication_type_id, applicant_id, 
user_id, communication_date, communication_text from tp_temp;

drop table tp_temp;		

alter table `tp_applicants` drop `applicant_notes`;

--
-- Added system generated trait for step type offer made.
--
create table tp_temp (
	`position_step_id` int(11)
) engine=innodb default charset=latin1;

insert into tp_temp (position_step_id)
	select tp_position_steps.position_step_id
	from tp_position_steps where step_type_id in (2);	

insert into tp_position_step_traits (position_step_id)
	select distinct tp_temp.position_step_id
	from tp_temp;
		
update tp_position_step_traits set trait_title='Comments', is_system_generated='1' where trait_title='""';

drop table tp_temp;

--
-- Added column
--
alter table `tp_communications` add `communication_date_created` datetime not null;

update `tp_communications` set `communication_date_created`=`communication_date`;

alter table `tp_appointments` change `appointment_date_created` `appointment_date_created` datetime not null;

--
-- migration of position_step_trait_rank values
--
create table tp_temp (
	`position_step_id` int(11),
	`position_step_trait_rank` tinyint(4)
) engine=innodb default charset=latin1;

insert into tp_temp select `position_step_id`, `position_step_trait_rank` from tp_position_step_traits;

update tp_position_step_traits t1
set position_step_trait_rank=(select count(*) from tp_temp t2 where t2.position_step_id = t1.position_step_id);

drop table tp_temp;

create function getcount (id1 int(11), id2 tinyint(4)) returns int(11)
return id2 + 1 - (select count(*) from tp_position_step_traits where position_step_id = id1 and position_step_trait_rank = id2);

update tp_position_step_traits 
set position_step_trait_rank=(select getcount(position_step_id, position_step_trait_rank));

drop function getcount;

--
-- insert new reminder templates
--
INSERT INTO `tp_email_templates` (`EMAIL_TEMPLATE`, `EMAIL_SUBJECT`, `EMAIL_FROM`, `EMAIL_TYPE`, `EMAIL_DEFAULT_BODY`) VALUES ('email_reminder_candidate', 'Interview Reminder', 'invalidaddress@talentica.com', 0, '<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"\r\n"http://www.w3.org/TR/html4/loose.dtd">\r\n<html>\r\n<head>\r\n<title>Untitled Document</title>\r\n<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">\r\n<style>\r\n\r\nbody , table, td {\r\nfont-family:Verdana;\r\nfont-size:12px;\r\ncolor:#000000;\r\n}\r\n\r\n</style>\r\n</head>\r\n\r\n<body>\r\n_%candidate_name%_,<br><br>\r\nThis mail comes as a gentle reminder about the interview with us. The details are as follows<br>\r\n<br>\r\n<table cellpadding="3" cellspacing="3">\r\n	<tr>\r\n	<td>\r\n		Date:\r\n	</td>\r\n	<td>\r\n		_%date%_\r\n</td>\r\n	</tr>\r\n	<tr>\r\n	<td>\r\n		Time:\r\n	</td>\r\n	<td>\r\n		_%time_from%_</td>\r\n	</tr>\r\n	<tr>\r\n	<td valign="top">\r\n		Venue:\r\n	</td>\r\n	<td>\r\n			<strong>Talentica Software (I) Pvt. Ltd.</strong><br>\r\n             B-7/8, Anmol Pride <br>\r\n              Survey No. 270/1/16, Baner<br>\r\n              Pune 411045 <br>\r\n              India \r\n	</td>\r\n	</tr>\r\n	<tr>\r\n	<td valign="top">\r\n		Contact Person:\r\n	</td>\r\n	<td>\r\n		_%contact_person%_<br>\r\n        Tel: +91 20 2729 2748 <br>\r\n        Cell: 9850038514 <br>\r\n      	Fax: +91 20 2729 2749	\r\n	</td>\r\n	</tr>\r\n</table>                        \r\n\r\n\r\n</body>\r\n</html>\r\n');
INSERT INTO `tp_email_templates` (`EMAIL_TEMPLATE`, `EMAIL_SUBJECT`, `EMAIL_FROM`, `EMAIL_TYPE`, `EMAIL_DEFAULT_BODY`) VALUES ('email_reminder_interviewer', 'Interview Reminder', 'invalidaddress@talentica.com', 0, '<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"\r\n"http://www.w3.org/TR/html4/loose.dtd">\r\n<html>\r\n<head>\r\n<title>Untitled Document</title>\r\n<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">\r\n<style>\r\n\r\ntable td {\r\nfont-family:Verdana;\r\nfont-size:12px;\r\ncolor:#000000;\r\n}\r\n.header{\r\nfont-size:15px;\r\ncolor:#FFFFFF;\r\nfont-weight:bold;\r\n}\r\n.lightGreen{\r\n\r\ncolor:#9EAC64;\r\ntext-align:right;\r\n}\r\n</style>\r\n</head>\r\n\r\n<body>\r\n<table cellpadding="5" bgcolor="#9EAC64" cellspacing="8" align="center">\r\n<tr>\r\n<td  class="header">\r\nInterview Reminder\r\n</td>\r\n</tr>\r\n<tr>\r\n<td bgcolor="#FFFFFF">\r\n	<table cellspacing="2" cellpadding="3">\r\n	<tr>\r\n	<td class="lightGreen">\r\n	Candidate: \r\n	</td>\r\n	<td>\r\n	_%candidate_name_with_link%_\r\n	</td>\r\n	</tr>\r\n	<tr>\r\n	<td class="lightGreen">\r\n	Date: \r\n	</td>\r\n	<td >\r\n	_%date%_\r\n	</td>\r\n	</tr>\r\n	<tr>\r\n	<td class="lightGreen">\r\n	Time: \r\n	</td>\r\n	<td>\r\n	_%time%_\r\n	</td>\r\n	</tr>\r\n	<tr>\r\n	<td class="lightGreen">\r\n	Position: \r\n	</td>\r\n	<td>\r\n	_%position_name%_\r\n	</td>\r\n	</tr>\r\n	</table>\r\n</td>\r\n</tr>\r\n<tr>\r\n<td>\r\n</td>\r\n</tr>\r\n</table>\r\n</body>\r\n</html>\r\n');
INSERT INTO `tp_email_templates` (`EMAIL_TEMPLATE`, `EMAIL_SUBJECT`, `EMAIL_FROM`, `EMAIL_TYPE`, `EMAIL_DEFAULT_BODY`) VALUES ('email_reminder_owner', 'Interview Reminder', 'invalidaddress@talentica.com', 0, '<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"\r\n"http://www.w3.org/TR/html4/loose.dtd">\r\n<html>\r\n<head>\r\n<title>Untitled Document</title>\r\n<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">\r\n<style>\r\n\r\ntable td {\r\nfont-family:Verdana;\r\nfont-size:12px;\r\ncolor:#000000;\r\n}\r\n.header{\r\nfont-size:15px;\r\ncolor:#FFFFFF;\r\nfont-weight:bold;\r\n}\r\n.lightGreen{\r\n\r\ncolor:#9EAC64;\r\ntext-align:right;\r\n}\r\n</style>\r\n</head>\r\n\r\n<body>\r\n<table cellpadding="5" bgcolor="#9EAC64" cellspacing="8" align="center">\r\n<tr>\r\n<td  class="header">\r\nInterview Reminder\r\n</td>\r\n</tr>\r\n<tr>\r\n<td bgcolor="#FFFFFF">\r\n	<table cellspacing="2" cellpadding="3">\r\n	<tr>\r\n	<td class="lightGreen">\r\n	Candidate: \r\n	</td>\r\n	<td>\r\n	_%candidate_name_with_link%_\r\n	</td>\r\n	</tr>\r\n	<tr>\r\n	<td class="lightGreen">\r\n	Date: \r\n	</td>\r\n	<td >\r\n	_%date%_\r\n	</td>\r\n	</tr>\r\n	<tr>\r\n	<td class="lightGreen">\r\n	Time: \r\n	</td>\r\n	<td>\r\n	_%time%_\r\n	</td>\r\n	</tr>\r\n	<tr>\r\n	<td class="lightGreen">\r\n	Position: \r\n	</td>\r\n	<td>\r\n	_%position_name%_\r\n	</td>\r\n	</tr>\r\n	</table>\r\n</td>\r\n</tr>\r\n<tr>\r\n<td>\r\n</td>\r\n</tr>\r\n</table>\r\n</body>\r\n</html>\r\n');

--
-- add column to store text resume
--
ALTER TABLE `tp_applicants` ADD `applicant_text_resume` LONGTEXT NULL ;

--
-- table to store temporary text resumes
--
CREATE TABLE `tp_applicants_tmp_text_resume` (
`text_resume_id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
`text_resume` LONGTEXT NOT NULL ,
`text_resume_date` DATE NOT NULL 
) ;

--
-- add column to save resume include to db
--
ALTER TABLE `tp_saved_searches` ADD `search_resume_includes` VARCHAR( 250 ) NULL ;

-- Oct 17th, 2006
-- added contact information of the talentpool users.
--
alter table `tp_users` add `user_home_phone` varchar( 25 ) null after `user_email` ,
add `user_cell_phone` varchar( 25 ) null after `user_home_phone`;

-- 
-- script changes to make columns unique 
-- 
ALTER TABLE `tp_degrees` ADD UNIQUE (`degree_title`);
ALTER TABLE `tp_branches` ADD UNIQUE (`branch_name`);
ALTER TABLE `tp_skill_category` ADD UNIQUE (`skill_category_description`);
ALTER TABLE `tp_source_types` ADD UNIQUE (`source_type` );
-- 
-- 
--
ALTER TABLE `tp_positions` 
CHANGE `position_code` `position_code` VARCHAR( 50 ) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL; 

UPDATE `tp_email_templates` SET `EMAIL_DEFAULT_BODY`='<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"\r\n"http://www.w3.org/TR/html4/loose.dtd">\r\n<html>\r\n<head>\r\n<title>Untitled Document</title>\r\n<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">\r\n<style>\r\n\r\nbody , table, td {\r\nfont-family:Verdana;\r\nfont-size:12px;\r\ncolor:#000000;\r\n}\r\n\r\n</style>\r\n</head>\r\n\r\n<body>\r\n_%candidate_name%_,<br><br>\r\nThis mail comes as a gentle reminder about the interview with us. The details are as follows<br>\r\n<br>\r\n<table cellpadding="3" cellspacing="3">\r\n	<tr>\r\n	<td>\r\n		Date:\r\n	</td>\r\n	<td>\r\n		_%date%_\r\n</td>\r\n	</tr>\r\n	<tr>\r\n	<td>\r\n		Time:\r\n	</td>\r\n	<td>\r\n		_%time_from%_</td>\r\n	</tr>\r\n	<tr>\r\n	<td valign="top">\r\n		Venue:\r\n	</td>\r\n	<td>\r\n			<strong>Talentica Software (I) Pvt. Ltd.</strong><br>\r\n             B-7/8, Anmol Pride <br>\r\n              Survey No. 270/1/16, Baner<br>\r\n              Pune 411045 <br>\r\n              India\r\n	</td>\r\n	</tr>\r\n	<tr>\r\n	<td valign="top">\r\n		Contact Person:\r\n	</td>\r\n	<td>\r\n		_%contact_person%_<br>\r\n        Tel: +91 20 2729 2748 <br>\r\n        Cell: _%contact_person_cell%_ <br>\r\n      	Fax: +91 20 2729 2749\r\n	</td>\r\n	</tr>\r\n</table>\r\n\r\n\r\n</body>\r\n</html>\r\n'
WHERE `EMAIL_TEMPLATE`='email_reminder_candidate';

ALTER TABLE `tp_positions` ADD `position_created_by` BIGINT NULL AFTER `position_date_created` ,
ADD `position_date_closed` DATETIME NULL AFTER `position_created_by` ,
ADD `position_closed_by` BIGINT NULL AFTER `position_date_closed` ,
ADD `position_date_deleted` DATETIME NULL AFTER `position_closed_by` ,
ADD `position_deleted_by` BIGINT NULL AFTER `position_date_deleted` ;

-- 
-- import script db changes
-- 

CREATE TABLE `tp_degree_aliases` (
`degree_id` SMALLINT NOT NULL ,
`alias` VARCHAR( 100 ) NOT NULL 
) ;

insert into `tp_degree_aliases` (`degree_id`,`alias`) values (12,'Bachelor of engineering');
insert into `tp_degree_aliases` (`degree_id`,`alias`) values (12,'Bachelor of technology');
insert into `tp_degree_aliases` (`degree_id`,`alias`) values (12,'BTech');
insert into `tp_degree_aliases` (`degree_id`,`alias`) values (13,'Master of engineering');
insert into `tp_degree_aliases` (`degree_id`,`alias`) values (13,'Master of technology');
insert into `tp_degree_aliases` (`degree_id`,`alias`) values (13,'MTech');
insert into `tp_degree_aliases` (`degree_id`,`alias`) values (14,'PGDM');

CREATE TABLE `tp_branch_aliases` (
`branch_id` INT NOT NULL ,
`alias` VARCHAR( 100 ) NOT NULL 
) ;

CREATE TABLE `tp_institute_aliases` (
`institute_id` BIGINT NOT NULL ,
`alias` VARCHAR( 150 ) NOT NULL 
) ;


ALTER TABLE `tp_degree_aliases`
ADD CONSTRAINT `tp_degree_aliases_ibfk_1` FOREIGN KEY (`degree_id`) REFERENCES `tp_degrees` (`degree_id`) ON DELETE CASCADE;

ALTER TABLE `tp_branch_aliases`
ADD CONSTRAINT `tp_branch_aliases_ibfk_1` FOREIGN KEY (`branch_id`) REFERENCES `tp_branches` (`branch_id`) ON DELETE CASCADE;

ALTER TABLE `tp_institute_aliases`
ADD CONSTRAINT `tp_institute_aliases_ibfk_1` FOREIGN KEY (`institute_id`) REFERENCES `tp_institutes` (`institute_id`) ON DELETE CASCADE;


ALTER TABLE `tp_applicant_educational_info` ADD `branch_id` INT NULL AFTER `degree_id` ,
ADD `institute_id` BIGINT NULL AFTER `branch_id` ;

ALTER TABLE `tp_applicant_educational_info`
  ADD CONSTRAINT `tp_applicant_educational_info_ibfk_3` FOREIGN KEY (`branch_id`) REFERENCES `tp_branches` (`branch_id`),
  ADD CONSTRAINT `tp_applicant_educational_info_ibfk_4` FOREIGN KEY (`institute_id`) REFERENCES `tp_institutes` (`institute_id`);

ALTER TABLE `tp_applicant_skills` DROP `skill_status` ;
ALTER TABLE `tp_applicants` ADD `applicant_current_employer` VARCHAR( 250 ) NULL ;

-- 
-- Add/Edit position changes
--
alter table `tp_position_steps` add `position_step_isdefault` char(1) not null;

update tp_position_steps ps set position_step_isdefault = (select step_type_default
from tp_step_types st
where ps.step_type_id is not null
and st.step_type_id = ps.step_type_id);

alter table `tp_position_steps` add `position_step_isscheduled` char(1) not null default '0';

create table `tp_position_step_users` (
`position_id` int(11) not null ,
`position_step_id` int(11) not null ,
`user_id` bigint(20) not null ,
`is_responsible_for_scheduling` char(1) not null
) ;

alter table `tp_position_step_users` add unique (
`position_id` ,
`position_step_id` ,
`user_id`,
`is_responsible_for_scheduling`
);

update tp_position_steps set `position_step_isdefault`=0 where `step_type_id`=2;

create table `tp_position_step_status_messages` (
`status_message_id` bigint( 20 ) not null auto_increment primary key ,
`position_step_id` int( 11 ) not null ,
`status_message` varchar( 300 ) not null
) ;

create table `tp_applicant_status_messages` (
`applicant_id` bigint not null ,
`position_step_id` int not null ,
`status_message` varchar( 300 ) not null ,
`user_id` bigint not null ,
`date_created` datetime not null
) ;

drop table `tp_position_users`;

alter table tp_position_steps drop foreign key 
`tp_position_steps_ibfk_2`;

alter table `tp_position_steps` drop index `step_type_id`;

delete from tp_step_types where `step_type_default`=0;

alter table `tp_position_steps` drop `step_type_id`;

alter table `tp_applicant_status_messages` add `status_message_id` bigint not null auto_increment primary key first ;

alter table `tp_position_step_status_messages` add `is_default` char( 1 ) not null default '0';

ALTER TABLE `tp_applicant_selection_process` ADD `position_id` INT NULL;

update tp_applicant_selection_process tasp
set tasp.position_id = (select tps.position_id 
		from tp_position_steps tps
		where tps.position_step_id = tasp.position_step_id_from);

update tp_applicant_selection_process tasp
set tasp.position_id = (select tps.position_id 
		from tp_position_steps tps
		where tps.position_step_id = tasp.position_step_id_to)
where position_id is null;

update tp_applicants ta
set ta.applicant_current_employer = (select tae.employment_company
				from tp_applicant_employment tae
				where tae.applicant_id = ta.applicant_id
				order by employment_id limit 0, 1);
				
ALTER TABLE `tp_institute_aliases` ADD UNIQUE (`alias`);
ALTER TABLE `tp_branch_aliases` ADD UNIQUE (`alias`);
ALTER TABLE `tp_degree_aliases` ADD UNIQUE (`alias` );
				
insert into `tp_branches` (`branch_id`,`branch_name`,`branch_status`) values (1,'Mechanical Engineering','1');
insert into `tp_branches` (`branch_id`,`branch_name`,`branch_status`) values (2,'Electronics & Telecommunication','1');
insert into `tp_branches` (`branch_id`,`branch_name`,`branch_status`) values (3,'Computer Science','1');
insert into `tp_branches` (`branch_id`,`branch_name`,`branch_status`) values (4,'Electrical Engineering','1');
insert into `tp_branches` (`branch_id`,`branch_name`,`branch_status`) values (5,'Production Engineering','1');
insert into `tp_branches` (`branch_id`,`branch_name`,`branch_status`) values (6,'Instrumentation','1');
insert into `tp_branches` (`branch_id`,`branch_name`,`branch_status`) values (7,'Chemical Engineering','1');
insert into `tp_branches` (`branch_id`,`branch_name`,`branch_status`) values (8,'Textile Engineering','1');
insert into `tp_branches` (`branch_id`,`branch_name`,`branch_status`) values (9,'Aerospace Engineering','1');
insert into `tp_branches` (`branch_id`,`branch_name`,`branch_status`) values (10,'Civil Engineering','1');
insert into `tp_branches` (`branch_id`,`branch_name`,`branch_status`) values (11,'Agricultural Engineering','1');
insert into `tp_branches` (`branch_id`,`branch_name`,`branch_status`) values (12,'Material Science','1');
insert into `tp_branches` (`branch_id`,`branch_name`,`branch_status`) values (13,'Mining Engineering','1');
insert into `tp_branches` (`branch_id`,`branch_name`,`branch_status`) values (14,'Physics','1');
insert into `tp_branches` (`branch_id`,`branch_name`,`branch_status`) values (15,'Statistics','1');
insert into `tp_branches` (`branch_id`,`branch_name`,`branch_status`) values (16,'Economics','1');
insert into `tp_branches` (`branch_id`,`branch_name`,`branch_status`) values (17,'Information Technology','1');
insert into `tp_branches` (`branch_id`,`branch_name`,`branch_status`) values (18,'Ocean Engineering','1');
insert into `tp_branches` (`branch_id`,`branch_name`,`branch_status`) values (19,'Mathematics','1');
insert into `tp_branches` (`branch_id`,`branch_name`,`branch_status`) values (20,'Architecture','1');
insert into `tp_branches` (`branch_id`,`branch_name`,`branch_status`) values (21,'Chemistry','1');

insert into `tp_branch_aliases` (`branch_id`,`alias`) values (1,'Mech');
insert into `tp_branch_aliases` (`branch_id`,`alias`) values (1,'Mechanical');
insert into `tp_branch_aliases` (`branch_id`,`alias`) values (2,'Electronics');
insert into `tp_branch_aliases` (`branch_id`,`alias`) values (2,'Telecom');
insert into `tp_branch_aliases` (`branch_id`,`alias`) values (2,'Telecommunication');
insert into `tp_branch_aliases` (`branch_id`,`alias`) values (3,'Computer');
insert into `tp_branch_aliases` (`branch_id`,`alias`) values (4,'Electrical');
insert into `tp_branch_aliases` (`branch_id`,`alias`) values (4,'Power');
insert into `tp_branch_aliases` (`branch_id`,`alias`) values (5,'Production');
insert into `tp_branch_aliases` (`branch_id`,`alias`) values (7,'Chemical');
insert into `tp_branch_aliases` (`branch_id`,`alias`) values (8,'Textile');
insert into `tp_branch_aliases` (`branch_id`,`alias`) values (9,'Aeronautical');
insert into `tp_branch_aliases` (`branch_id`,`alias`) values (9,'Aerospace');
insert into `tp_branch_aliases` (`branch_id`,`alias`) values (9,'Aviation');
insert into `tp_branch_aliases` (`branch_id`,`alias`) values (10,'Civil');
insert into `tp_branch_aliases` (`branch_id`,`alias`) values (11,'Agricultural');
insert into `tp_branch_aliases` (`branch_id`,`alias`) values (11,'Agriculture');
insert into `tp_branch_aliases` (`branch_id`,`alias`) values (12,'Metallurgical');
insert into `tp_branch_aliases` (`branch_id`,`alias`) values (12,'Metallurgy');
insert into `tp_branch_aliases` (`branch_id`,`alias`) values (13,'Mining');
insert into `tp_branch_aliases` (`branch_id`,`alias`) values (18,'Naval');
insert into `tp_branch_aliases` (`branch_id`,`alias`) values (18,'Naval Architecture');
insert into `tp_branch_aliases` (`branch_id`,`alias`) values (18,'Ocean');
insert into `tp_branch_aliases` (`branch_id`,`alias`) values (20,'Architectural Engineering');


update tp_applicant_educational_info taei
set taei.branch_id = (select tb.branch_id 
		from tp_branches tb
		where tb.branch_name = taei.educational_info_major);

update tp_applicant_educational_info taei
set taei.institute_id = (select tb.institute_id 
		from tp_institutes tb
		where tb.institute_name = taei.educational_info_institute);
		
create table tp_temp (
	`applicant_id` bigint(20),
	`count` tinyint(4),
	`educational_info_id` bigint(20)
) engine=innodb default charset=latin1;


insert into tp_temp (applicant_id)
	select distinct `applicant_id`
	from `tp_applicant_educational_info`;

update tp_temp tt 
set tt.count=(select count(*) 
	from tp_applicant_educational_info taei 
	where taei.applicant_id=tt.applicant_id);

update tp_temp tt 
set tt.educational_info_id=(select educational_info_id 
	from tp_applicant_educational_info taei 
	where taei.applicant_id=tt.applicant_id 
	order by taei.educational_info_year_of_passing desc limit 0, 1);

delete from tp_applicant_educational_info 
where educational_info_id not in (select tt.educational_info_id from tp_temp tt);

drop table `tp_temp`;


insert into `tp_role_tasks` ( `role_id` , `task_id` )
values ('1', '28'), 
('2', '28'),
('3', '28'),
('4', '28');

insert into `tp_role_tasks` ( `role_id` , `task_id` )
values ('2', '10'), 
('2', '13'),
('2', '14'),
('2', '20'),
('2', '21'),
('2', '22');

insert into `tp_role_tasks` ( `role_id` , `task_id` )
values ('3', '16'), 
('3', '17'),
('3', '18');

insert into `tp_role_tasks` ( `role_id` , `task_id` )
values ('5', '10'), 
('5', '13'),
('5', '14'), 
('5', '21'),
('5', '22');

delete from tp_step_types where step_type_id=2;
insert into `tp_parser_expressions` (`parser_expression_id`,`parser_expression_field_name`,`parser_expression`,`parser_expression_priority`,`parser_expression_desc`) values ( NULL,'year_of_passing','(?mid)\\b\\d{4}\\b','1','');

alter table `tp_positions` add `position_requested_by` bigint not null ;
update tp_positions set position_requested_by=1;

insert into `tp_roles` ( `role_id` , `role_title` )
values (
'6', 'CXO'
);

insert into `tp_role_tasks` ( `role_id` , `task_id` )
values ('6', '1'), 
('6', '2'),
('6', '3'), 
('6', '4'),
('6', '5'),
('6', '6'), 
('6', '7'),
('6', '8'), 
('6', '9'),
('6', '10'),
('6', '11'), 
('6', '12'),
('6', '13'), 
('6', '14'),
('6', '15'),
('6', '16'), 
('6', '17'),
('6', '18'), 
('6', '19'),
('6', '20'),
('6', '21'), 
('6', '22'),
('6', '23'), 
('6', '24'),
('6', '25'),
('6', '27'),
('6', '28');

ALTER TABLE `tp_skills` ADD UNIQUE (`skill` );
ALTER TABLE `tp_institutes` ADD UNIQUE (`institute_name` );

create table `tp_position_step_level` (
`level_id` smallint not null ,
`level_text` varchar( 20 ) not null
) ;

insert into `tp_position_step_level` ( `level_id` , `level_text` )
values (
'0', 'select'
), (
'1', 'accept'
);

alter table `tp_position_steps` add `position_step_level` char( 1 ) not null default '0';

alter table `tp_applicants` add column `applicant_original_doc_path` varchar (100) NULL  after `applicant_original_resume_path`;
update tp_applicants set applicant_original_doc_path=applicant_original_resume_path;

ALTER TABLE `tp_applicants` ADD `user_id` BIGINT NOT NULL ;

UPDATE tp_applicants SET user_id=1;

ALTER TABLE `tp_applicants`
ADD CONSTRAINT `tp_applicants_ibfk_4` FOREIGN KEY (`user_id`) REFERENCES `tp_users` (`user_id`);

CREATE TABLE `tp_last_viewed` (
  `entity_id` bigint(20) NOT NULL,
  `entity_type` char(1) NOT NULL,
  `entity_date_viewed` datetime NOT NULL,
  `entity_viewed_by` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `tp_recent_searches` (                
  `search_id` bigint(20) NOT NULL auto_increment,  
  `user_id` bigint(20) default NULL,               
  `date_searched` timestamp NULL default NULL,     
  `search_data` blob,                              
  PRIMARY KEY  (`search_id`)                       
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

alter table `tp_applicants` add column `applicant_date_joined` date   NULL  after `applicant_joined`;

alter table `tp_applicants` change `applicant_working_since` `applicant_working_since` date   NULL;

update tp_applicants ta
set applicant_position_id=(select position_id 
			from tp_applicant_selection_process tasp
			where tasp.applicant_id = ta.applicant_id
			and tasp.position_step_id_to=-2),
applicant_date_joined=(select process_moved_date
			from tp_applicant_selection_process tasp
			where tasp.applicant_id = ta.applicant_id
			and tasp.position_step_id_to=-2)
where ta.applicant_joined=1;

DELETE FROM TP_ROLE_TASKS;

INSERT INTO `tp_role_tasks` (`ROLE_ID`, `TASK_ID`) VALUES (1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(1, 6),
(1, 7),
(1, 8),
(1, 9),
(1, 10),
(1, 21),
(1, 22),
(1, 23),
(1, 24),
(1, 31),
(1, 32),
(1, 33),
(1, 34),
(1, 41),
(1, 42),
(1, 43),
(1, 44),
(1, 51),
(1, 52),
(1, 53),
(1, 61),
(1, 62),
(4, 1),
(4, 2),
(4, 3),
(4, 4),
(4, 5),
(4, 6),
(4, 7),
(4, 8),
(4, 9),
(4, 21),
(4, 22),
(4, 23),
(4, 24),
(4, 31),
(4, 32),
(4, 33),
(4, 34),
(4, 41),
(4, 42),
(4, 43),
(4, 44),
(4, 51),
(4, 52),
(4, 53),
(6, 1),
(6, 2),
(6, 3),
(6, 4),
(6, 5),
(6, 6),
(6, 7),
(6, 8),
(6, 9),
(6, 21),
(6, 22),
(6, 23),
(6, 24),
(6, 31),
(6, 32),
(6, 33),
(6, 34),
(6, 41),
(6, 42),
(6, 43),
(6, 44),
(6, 51),
(6, 52),
(6, 53),
(5, 1),
(5, 3),
(5, 4),
(5, 5),
(5, 7),
(5, 8),
(5, 21),
(5, 22),
(5, 23),
(5, 24),
(5, 31),
(5, 32),
(5, 33),
(5, 51),
(5, 52),
(5, 53),
(2, 1),
(2, 3),
(2, 4),
(2, 5),
(2, 6),
(2, 7),
(2, 8),
(2, 21),
(2, 22),
(2, 23),
(2, 24),
(2, 31),
(2, 32),
(2, 33),
(2, 34),
(2, 41),
(2, 42),
(2, 43),
(2, 44),
(2, 51),
(2, 52),
(2, 53),
(3, 1),
(3, 2),
(3, 3),
(3, 4),
(3, 5),
(3, 6),
(3, 7),
(3, 8),
(3, 9),
(3, 21),
(3, 22),
(3, 23),
(3, 24),
(3, 31),
(3, 32),
(3, 33),
(3, 34),
(3, 41),
(3, 42),
(3, 43),
(3, 44),
(3, 51),
(3, 52),
(3, 53);

DELETE FROM TP_TASK_OVERRIDES;

alter table `tp_positions` change `position_code` `position_code` varchar (20)   NULL  COLLATE latin1_swedish_ci;

DROP TABLE IF EXISTS `tp_template_generic_vars`;

-- 
-- Table structure for table `tp_template_generic_vars` 
--
CREATE TABLE `tp_template_generic_vars` (
  `template_type` smallint(6) NOT NULL,
  `template_var` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- 
-- Data for table `tp_template_generic_vars` 
--
insert into `tp_template_generic_vars` (`template_type`,`template_var`) values (1,'COMPANY_NAME');
insert into `tp_template_generic_vars` (`template_type`,`template_var`) values (2,'USER_FNAME');
insert into `tp_template_generic_vars` (`template_type`,`template_var`) values (2,'USER_LNAME');
insert into `tp_template_generic_vars` (`template_type`,`template_var`) values (2,'USER_EMAIL');
insert into `tp_template_generic_vars` (`template_type`,`template_var`) values (2,'USER_HOME_PHONE');
insert into `tp_template_generic_vars` (`template_type`,`template_var`) values (2,'USER_CELL_PHONE');
insert into `tp_template_generic_vars` (`template_type`,`template_var`) values (3,'CANDIDATE_NAME');
insert into `tp_template_generic_vars` (`template_type`,`template_var`) values (3,'CANDIDATE_CURRENT_LOCATION');
insert into `tp_template_generic_vars` (`template_type`,`template_var`) values (3,'CANDIDATE_EMAIL_1');
insert into `tp_template_generic_vars` (`template_type`,`template_var`) values (3,'CANDIDATE_EMAIL_2');
insert into `tp_template_generic_vars` (`template_type`,`template_var`) values (3,'CANDIDATE_PHONE_1');
insert into `tp_template_generic_vars` (`template_type`,`template_var`) values (3,'CANDIDATE_PHONE_2');
insert into `tp_template_generic_vars` (`template_type`,`template_var`) values (3,'CANDIDATE_CELL_PHONE');
insert into `tp_template_generic_vars` (`template_type`,`template_var`) values (3,'CANDIDATE_CURRENT_EMPLOYER');
insert into `tp_template_generic_vars` (`template_type`,`template_var`) values (3,'CANDIDATE_ID');
insert into `tp_template_generic_vars` (`template_type`,`template_var`) values (10,'APPOINTMENT_DATE');
insert into `tp_template_generic_vars` (`template_type`,`template_var`) values (10,'APPOINTMENT_TIME_FROM');
insert into `tp_template_generic_vars` (`template_type`,`template_var`) values (10,'LINK_TO_ORIGINAL_RESUME');
insert into `tp_template_generic_vars` (`template_type`,`template_var`) values (10,'APPOINTMENT_POSITION_NAME');
insert into `tp_template_generic_vars` (`template_type`,`template_var`) values (10,'CONTACT_PERSON_FNAME');
insert into `tp_template_generic_vars` (`template_type`,`template_var`) values (10,'CONTACT_PERSON_LNAME');
insert into `tp_template_generic_vars` (`template_type`,`template_var`) values (10,'CONTACT_PERSON_CELL_PHONE');
insert into `tp_template_generic_vars` (`template_type`,`template_var`) values (10,'CONTACT_PERSON_HOME_PHONE');
insert into `tp_template_generic_vars` (`template_type`,`template_var`) values (10,'CONTACT_PERSON_EMAIL');
insert into `tp_template_generic_vars` (`template_type`,`template_var`) values (10,'APPOINTMENT_TIME_TO');
insert into `tp_template_generic_vars` (`template_type`,`template_var`) values (11,'LINK_TO_FEEDBACK_FORM');

-- 
-- Table structure for table `tp_templates` 
--

DROP TABLE IF EXISTS `tp_templates`;

CREATE TABLE `tp_templates` (
  `template_id` bigint(20) NOT NULL auto_increment,
  `template_code` varchar(100) NOT NULL,
  `template_name` varchar(100) NOT NULL,
  `template_subject_file` varchar(150) NOT NULL,
  `template_content_file` varchar(150) NOT NULL,
  `template_auto` char(1) default '0',
  `template_date_created` datetime default NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`template_id`),
  UNIQUE KEY `template_code` (`template_code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- 
-- Data for the table `tp_templates`
--
insert into `tp_templates` (`template_id`,`template_code`,`template_name`,`template_subject_file`,`template_content_file`,`template_auto`,`template_date_created`,`user_id`) values (1,'appointmentReminderOwner','Appointment Reminder to Owner','appointmentReminderOwnerSubject.vm','appointmentReminderOwnerContent.vm','0','2007-02-27 00:00:00',1);
insert into `tp_templates` (`template_id`,`template_code`,`template_name`,`template_subject_file`,`template_content_file`,`template_auto`,`template_date_created`,`user_id`) values (2,'appointmentReminderInterviewer','Appointment Reminder to Interviewer','appointmentReminderInterviewerSubject.vm','appointmentReminderInterviewerContent.vm','0','2007-02-27 00:00:00',1);
insert into `tp_templates` (`template_id`,`template_code`,`template_name`,`template_subject_file`,`template_content_file`,`template_auto`,`template_date_created`,`user_id`) values (3,'appointmentReminderCandidate','Appointment Reminder to Candidate','appointmentReminderCandidateSubject.vm','appointmentReminderCandidateContent.vm','0','2007-02-27 00:00:00',1);
insert into `tp_templates` (`template_id`,`template_code`,`template_name`,`template_subject_file`,`template_content_file`,`template_auto`,`template_date_created`,`user_id`) values (4,'appointmentFeedbackReminder','Feedback Reminder to Interviewer','appointmentFeedbackReminderSubject.vm','appointmentFeedbackReminderContent.vm','0','2007-02-27 00:00:00',1);

-- 
-- Table structure for table `tp_template_vars` 
--
DROP TABLE IF EXISTS `tp_template_vars`;

CREATE TABLE `tp_template_vars` (
  `template_code` varchar(100) default NULL,
  `template_type` smallint(6) default NULL,
  KEY `FK_tp_template_vars` (`template_code`),
  CONSTRAINT `tp_template_vars_ibfk_1` FOREIGN KEY (`template_code`) REFERENCES `tp_templates` (`template_code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
-- 
-- Data for the table `tp_template_vars`
--
insert into `tp_template_vars` (`template_code`,`template_type`) values ('appointmentReminderOwner',1);
insert into `tp_template_vars` (`template_code`,`template_type`) values ('appointmentReminderOwner',3);
insert into `tp_template_vars` (`template_code`,`template_type`) values ('appointmentReminderOwner',10);
insert into `tp_template_vars` (`template_code`,`template_type`) values ('appointmentReminderInterviewer',1);
insert into `tp_template_vars` (`template_code`,`template_type`) values ('appointmentReminderInterviewer',3);
insert into `tp_template_vars` (`template_code`,`template_type`) values ('appointmentReminderInterviewer',10);
insert into `tp_template_vars` (`template_code`,`template_type`) values ('appointmentReminderCandidate',1);
insert into `tp_template_vars` (`template_code`,`template_type`) values ('appointmentReminderCandidate',3);
insert into `tp_template_vars` (`template_code`,`template_type`) values ('appointmentReminderCandidate',10);
insert into `tp_template_vars` (`template_code`,`template_type`) values ('appointmentFeedbackReminder',1);
insert into `tp_template_vars` (`template_code`,`template_type`) values ('appointmentFeedbackReminder',3);
insert into `tp_template_vars` (`template_code`,`template_type`) values ('appointmentFeedbackReminder',10);
insert into `tp_template_vars` (`template_code`,`template_type`) values ('appointmentFeedbackReminder',11);

-- 
-- drop old templates table
-- 
drop table `tp_email_templates`;

alter table `tp_templates` add column `template_format` char (1)  DEFAULT '0' NOT NULL  after `template_auto`, 
add column `template_private` char (1)  DEFAULT '0' NOT NULL  after `template_format`;

alter table `tp_templates` change `template_auto` `template_auto` char (1)  DEFAULT '0' NOT NULL  COLLATE latin1_swedish_ci , 
change `template_date_created` `template_date_created` datetime   NOT NULL; 

DROP TABLE IF EXISTS `tp_outbound`;

CREATE TABLE `tp_outbound` (
  `outbound_id` int(11) NOT NULL auto_increment,
  `sent_status` char(1) NOT NULL default '0',
  `send_to` text,
  `send_from` varchar(250) default NULL,
  `outbound_type` varchar(250) NOT NULL,
  `outbound_mode` varchar(250) NOT NULL,
  `send_date` datetime default NULL,
  `sent_date` datetime default NULL,
  `sent_message_id` text,
  `entity_id` bigint(20) default NULL,
  `entity_type` char(1) default '0',
  `user_id` bigint(20) NOT NULL,
  `data_object` blob,
  PRIMARY KEY  (`outbound_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

insert into `tp_templates` (`template_id`,`template_code`,`template_name`,`template_subject_file`,`template_content_file`,`template_auto`,`template_format`,`template_private`,`template_date_created`,`user_id`) values (5,'positionRequirement','Position requirement','positionRequirementSubject.vm','positionRequirementContent.vm','0','0','1','2007-02-27 00:00:00',1);
insert into `tp_template_vars` (`template_code`,`template_type`) values ('positionRequirement',1);
insert into `tp_template_vars` (`template_code`,`template_type`) values ('positionRequirement',3);


alter table `tp_positions` 
add column `position_primary_skills` text   NOT NULL  after `position_date_expiry`, 
add column `position_secondary_skills` text   NULL  after `position_primary_skills`;

alter table `tp_position_step_status_messages` 
add column `status_message_rank` tinyint   NOT NULL  after `is_default`;

update tp_positions tp
set position_primary_skills=(select group_concat(tps.skill) 
		from tp_skills tps, tp_position_skills tpps 
		where tps.skill_id=tpps.skill_id 
		and tpps.position_id=tp.position_id
		and tpps.skill_status=0);

update tp_positions tp
set position_secondary_skills=(select group_concat(tps.skill) 
		from tp_skills tps, tp_position_skills tpps 
		where tps.skill_id=tpps.skill_id 
		and tpps.position_id=tp.position_id
		and tpps.skill_status=1);
		
alter table `tp_applicants` 
add column `applicant_home_phone_is_invalid` char (1)  DEFAULT '0' NOT NULL  after `applicant_home_phone`, 
add column `applicant_cell_phone_is_invalid` char (1)  DEFAULT '0' NOT NULL  after `applicant_cell_phone`, 
add column `applicant_work_phone_is_invalid` char (1)  DEFAULT '0' NOT NULL  after `applicant_work_phone`;

--
-- changes after beta 
--
alter table `tp_inbox_emails` 
change `email_from` `email_from` varchar (100)   NULL  COLLATE latin1_swedish_ci , 
change `email_to` `email_to` text   NULL  COLLATE latin1_swedish_ci , 
change `email_subject` `email_subject` varchar (250)   NULL  COLLATE latin1_swedish_ci;

alter table `tp_applicant_inbox_emails` 
change `email_from` `email_from` varchar (100)   NULL  COLLATE latin1_swedish_ci , 
change `email_to` `email_to` text   NULL  COLLATE latin1_swedish_ci , 
change `email_subject` `email_subject` varchar (250)   NULL  COLLATE latin1_swedish_ci;

alter table `tp_applicant_status_messages` add column `system_generated` char (1)  
DEFAULT '0' NOT NULL  after `date_created`;

INSERT INTO `tp_role_tasks` (`ROLE_ID`, `TASK_ID`) VALUES (4, 10), (4, 62);

alter table `tp_inbox_emails` 
add column `auto_import_format` tinyint (1)  DEFAULT '0' NULL  after `folder_id`, 
add column `auto_import_tried` boolean   NOT NULL  after `auto_import_format`;

alter table `tp_inbox_emails` add column `auto_import_errors` varchar (250)   NULL  after `auto_import_tried`;

alter table `tp_applicant_educational_info` add column `educational_level` smallint (1)  DEFAULT '0' NOT NULL  after `educational_info_grade`;
alter table `tp_applicant_educational_info` drop column `educational_info_major`, drop column `educational_info_institute`;

-- 
-- CTC Chnges
-- 
alter table `tp_applicants` add column `current_ctc` varchar (10)   NULL after `user_id`, 
add column `current_ctc_date` date   NULL  after `current_ctc`, 
add column `expected_ctc` varchar (10)   NULL  after `current_ctc_date`, 
add column `expected_ctc_date` date   NULL  after `expected_ctc`;

insert into `tp_role_tasks` (`ROLE_ID`,`TASK_ID`) values ( '1','25');
insert into `tp_role_tasks` (`ROLE_ID`,`TASK_ID`) values ( '3','25');
insert into `tp_role_tasks` (`ROLE_ID`,`TASK_ID`) values ( '4','25');
insert into `tp_role_tasks` (`ROLE_ID`,`TASK_ID`) values ( '6','25');

alter table `tp_position_step_users` add column `is_authorized_to_move` char (1)  DEFAULT '0' NULL  after `is_responsible_for_scheduling`,
change `is_responsible_for_scheduling` `is_responsible_for_scheduling` char (1)  DEFAULT '0' NOT NULL;

alter table `tp_position_step_users` drop key `position_id`, 
add unique `position_id` ( `position_id`, `position_step_id`, `user_id`, `is_responsible_for_scheduling`, `is_authorized_to_move` );

alter table `tp_appointments` add column `appointment_modified_by` bigint (20)   NULL  after `appointment_status_id`, 
add column `appointment_date_modified` datetime   NULL  after `appointment_modified_by`;

update `tp_template_generic_vars` set `template_type`='4',`template_var`='CONTACT_PERSON_FNAME' where `template_type`='10' and `template_var`='CONTACT_PERSON_FNAME';
update `tp_template_generic_vars` set `template_type`='4',`template_var`='CONTACT_PERSON_LNAME' where `template_type`='10' and `template_var`='CONTACT_PERSON_LNAME';
update `tp_template_generic_vars` set `template_type`='4',`template_var`='CONTACT_PERSON_CELL_PHONE' where `template_type`='10' and `template_var`='CONTACT_PERSON_CELL_PHONE';
update `tp_template_generic_vars` set `template_type`='4',`template_var`='CONTACT_PERSON_HOME_PHONE' where `template_type`='10' and `template_var`='CONTACT_PERSON_HOME_PHONE';
update `tp_template_generic_vars` set `template_type`='4',`template_var`='CONTACT_PERSON_EMAIL' where `template_type`='10' and `template_var`='CONTACT_PERSON_EMAIL';

insert into `tp_template_vars` (`template_code`,`template_type`) values ('appointmentReminderOwner',4);
insert into `tp_template_vars` (`template_code`,`template_type`) values ('appointmentReminderInterviewer',4);
insert into `tp_template_vars` (`template_code`,`template_type`) values ('appointmentReminderCandidate',4);
insert into `tp_template_vars` (`template_code`,`template_type`) values ('appointmentFeedbackReminder',4);


insert into `tp_templates` (`template_code`,`template_name`,`template_subject_file`,`template_content_file`,`template_auto`,`template_date_created`,`user_id`) values ('newAppointmentNotification','New Appointment Notification','newAppointmentNotificationSubject.vm','newAppointmentNotificationContent.vm','0',now(),1);
insert into `tp_templates` (`template_code`,`template_name`,`template_subject_file`,`template_content_file`,`template_auto`,`template_date_created`,`user_id`) values ('modifiedAppointmentNotification','Modified Appointment Notification','modifiedAppointmentNotificationSubject.vm','modifiedAppointmentNotificationContent.vm','0',now(),1);
insert into `tp_templates` (`template_code`,`template_name`,`template_subject_file`,`template_content_file`,`template_auto`,`template_date_created`,`user_id`) values ('cancelledAppointmentNotification','Cancelled Appointment Notification','cancelledAppointmentNotificationSubject.vm','cancelledAppointmentNotificationContent.vm','0',now(),1);

insert into `tp_template_vars` (`template_code`,`template_type`) values ('newAppointmentNotification',1);
insert into `tp_template_vars` (`template_code`,`template_type`) values ('newAppointmentNotification',2);
insert into `tp_template_vars` (`template_code`,`template_type`) values ('newAppointmentNotification',3);
insert into `tp_template_vars` (`template_code`,`template_type`) values ('newAppointmentNotification',4);
insert into `tp_template_vars` (`template_code`,`template_type`) values ('newAppointmentNotification',10);

insert into `tp_template_vars` (`template_code`,`template_type`) values ('modifiedAppointmentNotification',1);
insert into `tp_template_vars` (`template_code`,`template_type`) values ('modifiedAppointmentNotification',2);
insert into `tp_template_vars` (`template_code`,`template_type`) values ('modifiedAppointmentNotification',3);
insert into `tp_template_vars` (`template_code`,`template_type`) values ('modifiedAppointmentNotification',4);
insert into `tp_template_vars` (`template_code`,`template_type`) values ('modifiedAppointmentNotification',10);

insert into `tp_template_vars` (`template_code`,`template_type`) values ('cancelledAppointmentNotification',1);
insert into `tp_template_vars` (`template_code`,`template_type`) values ('cancelledAppointmentNotification',2);
insert into `tp_template_vars` (`template_code`,`template_type`) values ('cancelledAppointmentNotification',3);
insert into `tp_template_vars` (`template_code`,`template_type`) values ('cancelledAppointmentNotification',4);
insert into `tp_template_vars` (`template_code`,`template_type`) values ('cancelledAppointmentNotification',10);

CREATE TABLE `tp_appointments_notifications` (                
`notification_id` bigint(20) NOT NULL auto_increment,
`appointment_id` bigint(20) NOT NULL,       
`appointment_subject` varchar(250) default NULL,
`appointment_from_date` datetime NOT NULL,            
`appointment_to_date` datetime NOT NULL,
`appointment_date_created` datetime default NULL,
`appointment_created_by` bigint(20) NOT NULL,           
`applicant_id` bigint(20) NOT NULL,               
`position_id` int(11) NOT NULL,                         
`position_step_id` int(11) NOT NULL,
`appointment_attendee` varchar(250) NOT NULL,
`appointment_from_date_new` datetime default NULL,
`appointment_to_date_new` datetime default NULL,
`appointment_attendee_new` varchar(250) default NULL,
`appointment_notify_type` tinyint(4) NOT NULL default '0',
`appointment_processed` tinyint(4) NOT NULL default '0',
`appointment_processed_date` datetime default NULL,
`appointment_date_modified` datetime default NULL,
`appointment_sequence_no` int(11) NOT NULL default '0',
`remind_me` int(11) default '0',
`remind_attendee` int(11) default '0',
PRIMARY KEY  (`notification_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
     
alter table `tp_applicant_selection_process` add column `process_date_created` datetime   NULL  after `user_id`;

update tp_applicant_selection_process set process_date_created=process_moved_date;

alter table `tp_applicant_selection_process` change `position_id` `position_id` int (11)   NOT NULL;
alter table `tp_applicant_selection_process` add foreign key `FK_tp_applicant_selection_process`(`position_id`) references `tp_positions` (`position_id`);
alter table `tp_applicant_status_messages` add foreign key `FK_tp_applicant_status_messages`(`applicant_id`) references `tp_applicants` (`applicant_id`);
alter table `tp_applicant_status_messages` add foreign key `FK_tp_applicant_status_messages_1`(`position_step_id`) references `tp_position_steps` (`position_step_id`);
alter table `tp_applicant_status_messages` add foreign key `FK_tp_applicant_status_messages_2`(`user_id`) references `tp_users` (`USER_ID`);
alter table `tp_last_viewed` add index `entity_date_viewed` ( `entity_date_viewed` );
alter table `tp_last_viewed` add index `entity_viewed_by` ( `entity_viewed_by` );
alter table `tp_messages` add foreign key `FK_tp_messages`(`message_from`) references `tp_users` (`USER_ID`);
alter table `tp_messages` add foreign key `FK_tp_messages_1`(`message_to`) references `tp_users` (`USER_ID`);
alter table `tp_position_step_status_messages` add foreign key `FK_tp_position_step_status_messages`(`position_step_id`) references `tp_position_steps` (`position_step_id`);
alter table `tp_recent_searches` add foreign key `FK_tp_recent_searches`(`user_id`) references `tp_users` (`USER_ID`);

drop table `tp_saved_searches`;

insert into tp_position_step_traits (position_step_id, trait_title,is_system_generated)
select tps.position_step_id, 'Comment' as comment1, 1 as system_generated 
from tp_position_steps tps
where tps.position_step_id not in (select tpst.position_step_id
from tp_position_step_traits tpst
where tpst.is_system_generated=1) and tps.position_step_isdefault=0;

DROP TABLE IF EXISTS `tp_release_info`;

CREATE TABLE `tp_release_info` (
  `rel_id` bigint(20) NOT NULL auto_increment,
  `product_code` varchar(20) NOT NULL,
  `product_name` varchar(20) NOT NULL,
  `version_number` varchar(15) NOT NULL,
  `build_number` varchar(15) NOT NULL,
  `date_of_release` date NOT NULL,
  PRIMARY KEY  (`rel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;