use talentpool;

alter table `tp_release_info` 
add column `date_of_installation` datetime   NOT NULL  after `build_number`;

update tp_release_info
set date_of_installation=date_of_release;

alter table tp_inbox_settings 
add column inbox_server_type smallint DEFAULT '0' NOT NULL  after inbox_display_name, 
add column inbox_smtp_auth_required smallint  DEFAULT '1' NOT NULL  after inbox_server_type, 
add column inbox_smtp_auth_same smallint  DEFAULT '1' NOT NULL  after inbox_smtp_auth_required, 
add column inbox_smtp_username varchar (100)   NULL  after inbox_smtp_auth_same, 
add column inbox_smtp_password varchar (100)   NULL  after inbox_smtp_username;