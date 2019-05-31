use talentpool;

DELIMITER $$

DROP PROCEDURE IF EXISTS `PROC_MIGRATE_SKILLS`$$

CREATE PROCEDURE `PROC_MIGRATE_SKILLS` ()
BEGIN
DECLARE pSkill VARCHAR(3000);
DECLARE sSkill VARCHAR(3000);
DECLARE positionId INT(11) DEFAULT 0;
DECLARE cnt INT(11) DEFAULT 0;
DECLARE oneSkill VARCHAR(250);
DECLARE tmpIndex int(11);
DECLARE noRecord INT DEFAULT 0;
DECLARE cur_skills CURSOR FOR SELECT position_id, position_primary_skills, position_secondary_skills from tp_positions;
DECLARE CONTINUE HANDLER FOR NOT FOUND SET noRecord = 1;

DROP TABLE IF EXISTS `tmp_skills`;

CREATE TABLE `tmp_skills` (             
              `position_id` int(11) default NULL,   
              `skill` varchar(250) default NULL,    
              `skill_type` char(1) default NULL     
            ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

OPEN cur_skills;
REPEAT
	FETCH cur_skills INTO positionId, pSkill, sSkill;
	IF pSkill is not null THEN
		SET cnt=1;
		WHILE LENGTH(pSkill)>0 DO 
			SET oneSkill = TRIM(SUBSTRING_INDEX(pSkill,',',1));
			IF LENGTH(oneSkill)>0 THEN
				insert into tmp_skills(position_id,skill,skill_type) values(positionId,oneSkill,'0');
			END IF;
			
			SET pSkill = TRIM(SUBSTRING(pSkill, LENGTH(oneSkill)+1));
			SET tmpIndex = LOCATE(pSkill,',');
			IF tmpIndex = 0 THEN
				SET pSkill = TRIM(SUBSTRING(pSkill,2));
			END IF;
		END WHILE;
	END IF;

	IF sSkill is not null THEN
		SET cnt=1;
		WHILE LENGTH(sSkill)>0 DO 
			SET oneSkill = TRIM(SUBSTRING_INDEX(sSkill,',',1));
			IF LENGTH(oneSkill)>0 THEN
				insert into tmp_skills(position_id,skill,skill_type) values(positionId,oneSkill,'1');
			END IF;
			
			SET sSkill = TRIM(SUBSTRING(sSkill, LENGTH(oneSkill)+1));
			SET tmpIndex = LOCATE(sSkill,',');
			IF tmpIndex = 0 THEN
				SET sSkill = TRIM(SUBSTRING(sSkill,2));
			END IF;
		END WHILE;
	END IF;

UNTIL noRecord = 1 
END REPEAT;
CLOSE cur_skills;

DELETE FROM tp_position_skills;
INSERT INTO tp_position_skills(position_id,skill_id,skill_type) SELECT position_id, skill_id, skill_type from tmp_skills tms, tp_skills ts where 
TRIM(ts.skill) = TRIM(tms.skill);


END$$

DELIMITER ;