

delimiter //

TRUNCATE TABLE `tp_applicant_skills`//
TRUNCATE TABLE `tp_applicant_educational_info`//
TRUNCATE TABLE `tp_applicant_employment`//
TRUNCATE TABLE `tp_applicants`//


drop procedure autoimport//


CREATE PROCEDURE `autoimport`(IN _recordCount INT, IN _city varchar(50), IN _workingSince varchar(10), 
IN _dateCreated varchar(10), IN _source varchar(50), IN _degree varchar(50), IN _major varchar(100), IN _skills varchar(250))
BEGIN
DECLARE maxAppId, i, newAppId, degreeId, sourceId, pos, skillId, error INT;
DECLARE userName, newUserName varchar(50);
DECLARE skillIds varchar(100);
DECLARE _skill varchar(50);
SET skillIds ='';
SET error =0;


	SELECT source_id INTO sourceId FROM tp_sources WHERE source_title=_source;
	IF sourceId IS NULL THEN
		SELECT 'Please enter valid Source Name';
		SET error=1;
	END IF;



	SELECT degree_id INTO degreeId FROM `tp_degrees` WHERE degree_title=_degree;
	IF degreeId IS NULL THEN
		SELECT 'Please enter valid degree';
		SET error=1;
	END IF;


	-- VARIFY AND GET SKILL IDS
	
	labelSkills: LOOP
		WHILE 0=0 DO

			SET pos = LOCATE(',',_skills);

			IF pos =0 THEN
				IF LENGTH(_skills)=0 THEN
					LEAVE labelSkills;
				ELSE
					set _skill = _skills;
					set _skills='';
				END IF;
			ELSE
				set _skill = SUBSTRING(_skills,1,pos-1);
				set _skills = SUBSTRING(_skills,pos+1);

			END IF;
			SET skillId =0;
			SELECT `skill_id` INTO skillId FROM `tp_skills` WHERE `skill` = _skill LIMIT 0,1;
			
			IF skillId =0 THEN
				select CONCAT('Skill =',_skill,' is not valid. Please check');
				SET error=1;
			ELSE
				IF LENGTH(skillIds)=0 THEN
					SET skillIds = CONCAT(skillIds,skillId);
				ELSE
					SET skillIds = CONCAT(skillIds,',',skillId);
				END IF;
			END IF;
		
		END WHILE;

	END LOOP labelSkills;
	

	IF error=0 THEN

		SELECT count(*) INTO maxAppId from tp_applicants;
		SET i=0;
		SET userName = 'User';
		WHILE i<_recordCount DO

			SET i = i+1;
			SET maxAppId=maxAppId+1;
			SET newUserName = CONCAT(userName,maxAppId);

			INSERT INTO `tp_applicants` (`applicant_name`, `applicant_address_type`, `applicant_city`, `applicant_working_since`, 
			`applicant_date_created`, `source_id`, `applicant_joined`, `applicant_original_resume_path`) 
			VALUES (newUserName, '1', _city, _workingSince, _dateCreated, sourceId, '0', '200610/12/DOC55696.html');

			SELECT LAST_INSERT_ID() INTO newAppId;

			INSERT INTO `tp_applicant_educational_info` (`applicant_id` , `degree_id` , `educational_info_major` ) 
			VALUES (newAppId, degreeId, _major);
			
			
			labelSkillIds: LOOP
				WHILE 0=0 DO
					SET pos = LOCATE(',',skillIds);

					IF pos =0 THEN
						
						IF LENGTH(skillIds)=0 THEN
							LEAVE labelSkillIds;
						ELSE
							set skillId = skillIds;
							set skillIds='';
						END IF;
					ELSE
						set skillId = SUBSTRING(skillIds,1,pos-1);
						set skillIds = SUBSTRING(skillIds,pos+1);

					END IF;
					
					INSERT INTO tp_applicant_skills(applicant_id,skill_id) values(newAppId,skillId);

				END WHILE;

			END LOOP labelSkillIds;
			

		END WHILE;
		
		select CONCAT('Aut inserted ' , _recordCount, ' Successfully ');
	
	END IF;


END//


call autoimport(20,'banglore', '2002-04-01', '2006-10-30', 'Naukri.com' , 'B.E.', 'Electronics', 'java,php,asp,mysql')//

