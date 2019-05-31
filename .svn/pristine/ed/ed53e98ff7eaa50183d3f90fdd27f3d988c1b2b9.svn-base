use talentpool;

DELETE FROM tp_recent_searches;

create table `tp_requisition_approval_templates` (    
`requisition_approval_template_id` int NOT NULL AUTO_INCREMENT ,  
`requisition_approval_template_name` varchar (255) NOT NULL ,  
`requisition_approval_template_type` tinyint DEFAULT '1' NOT NULL ,  
`requisition_approval_template_status` tinyint DEFAULT '1' NOT NULL ,  
`date_created` datetime NOT NULL, 
PRIMARY KEY ( `requisition_approval_template_id` )  
);

insert into tp_requisition_approval_templates
(requisition_approval_template_id, requisition_approval_template_name,
requisition_approval_template_type, requisition_approval_template_status, date_created)
values
(1, "Default Template", 0, 1, now());

alter table `tp_requisition_approval_steps` 
add column `requisition_approval_template_id` int NOT NULL  first;

update tp_requisition_approval_steps
set requisition_approval_template_id=1;

alter table `tp_requisition_approval_steps` 
add foreign key `FK_tp_requisition_approval_steps`(`requisition_approval_template_id`) 
references `tp_requisition_approval_templates` (`requisition_approval_template_id`);

alter table `tp_positions` 
add column `requisition_approval_template_id` int NOT NULL;

update tp_positions
set requisition_approval_template_id=1;

alter table `tp_positions` 
add foreign key `FK_tp_positions_requisition_approval_template_id`(`requisition_approval_template_id`) 
references `tp_requisition_approval_templates` (`requisition_approval_template_id`);

alter table `tp_requisition_approval_steps_users` 
add unique `indx_unique` ( `requisition_approval_step_id`, `user_id` );