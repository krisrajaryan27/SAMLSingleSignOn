use talentpool;

SET foreign_key_checks = 0;
ALTER TABLE tp_position_steps DROP FOREIGN KEY tp_position_steps_ibfk_4;
ALTER TABLE tp_position_steps CHANGE step_id step_id smallint(4) NULL ;
ALTER TABLE tp_step_master CHANGE step_id step_id smallint(4) NOT NULL;
ALTER TABLE tp_step_master CHANGE step_rank step_rank smallint(4) DEFAULT '0' NOT NULL;
ALTER TABLE tp_position_steps ADD CONSTRAINT tp_position_steps_ibfk_4 FOREIGN KEY (step_id) REFERENCES tp_step_master (step_id);

ALTER TABLE tp_migration_temp_position_steps CHANGE step_id step_id SMALLINT(4) NULL;
ALTER TABLE tp_migration_temp_steps_grouping CHANGE mapping_step_id mapping_step_id SMALLINT(4) NULL;

ALTER TABLE tp_cr_event_log change step_id_from step_id_from smallint(4) NULL , change step_id_to step_id_to smallint(4) NULL ;
ALTER TABLE tp_cr_event_summary change step_id step_id smallint(4) NOT NULL;
ALTER TABLE tp_cr_event_summary ADD CONSTRAINT FK_tp_cr_event_summary_step_id FOREIGN KEY (step_id) REFERENCES tp_step_master (step_id);

SET foreign_key_checks = 1;