use talentpool;

DELETE FROM tp_recent_searches;

create table `tp_locations` (    
	`location_id` int NOT NULL AUTO_INCREMENT,
	`location_name` varchar (50) NOT NULL, 
	PRIMARY KEY ( `location_id` ));
	
alter table `tp_positions` add column `location_id` int NULL  after `branch_id`;

alter table `tp_positions` add foreign key `FK_tp_positions`(`location_id`) references `tp_locations` (`location_id`);

alter table `tp_positions` add column `is_published_for_walk_in` char(1) DEFAULT '0' NOT NULL  after `position_referal_fees`;