use talentpool;

create table `tp_custom_labels` (
`custom_label_key` varchar(250) NOT NULL,
`custom_label_value` text NOT NULL
);

alter table `tp_custom_labels` add unique `custom_label_key` ( `custom_label_key` );

alter table `tp_custom_fields` change `custom_field_id` `custom_field_id` int (11)   NOT NULL AUTO_INCREMENT ,drop primary key,  add primary key (`custom_field_id` );