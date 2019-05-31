DELIMITER $$

DROP PROCEDURE IF EXISTS `PROC_GET_APPLICATS_TO_INDEX`$$

CREATE PROCEDURE `PROC_GET_APPLICATS_TO_INDEX`(IN _eventType VARCHAR(40), IN _eventId INT,
	IN _start INT, IN _batch INT, IN _Type_All VARCHAR(50), IN _Type_Add_Applicant VARCHAR(50),
	IN _Type_Update_Applicant VARCHAR(50), IN _Type_Skills VARCHAR(50), IN _Type_Degree VARCHAR(50),
	IN _Type_Institute VARCHAR(50), IN _Type_Branch VARCHAR(50), IN _Type_Source VARCHAR(50)
)
BEGIN
DECLARE noRecord INT DEFAULT 0;
DECLARE aId BIGINT(20);
DECLARE aSkill VARCHAR(250);
DECLARE prevId BIGINT(20) DEFAULT 0;
DECLARE totStr VARCHAR(3000) DEFAULT ''; 
DECLARE cur_skills CURSOR FOR SELECT tas.applicant_id, ts.skill 
FROM tp_applicant_skills tas , tp_skills ts, tmp_applicants tmpa WHERE 
ts.skill_id = tas.skill_id AND tas.applicant_id = tmpa.applicant_id;
DECLARE cur_aliases CURSOR FOR 
SELECT tmpa.applicant_id, alias FROM
(
	(
	SELECT tas.applicant_id as applicant_id, tsa.skill_alias as alias FROM tp_applicant_skills tas, tp_skills_aliases tsa
	WHERE tas.skill_id = tsa.skill_id
	)
	UNION
	(
	SELECT taei.applicant_id, alias FROM tp_applicant_educational_info taei,  tp_degree_aliases tda
	WHERE taei.degree_id = tda.degree_id
	)
	UNION
	(
	SELECT taei.applicant_id, alias FROM tp_applicant_educational_info taei, tp_branch_aliases tba
	WHERE taei.branch_id = tba.branch_id
	)
	UNION
	(
	SELECT taei.applicant_id, alias FROM tp_applicant_educational_info taei, tp_institute_aliases tia
	WHERE taei.institute_id = tia.institute_id
	) 
) as tx,
tmp_applicants tmpa 
WHERE
tx.applicant_id = tmpa.applicant_id 
ORDER BY tmpa.applicant_id;
DECLARE CONTINUE HANDLER FOR NOT FOUND SET noRecord = 1;
IF STRCMP(_eventType, _Type_Update_Applicant)=0 THEN
	SET _eventType = _Type_Add_Applicant;
END IF;
DROP TABLE IF EXISTS tmp_applicants;
SET @qry = "CREATE TEMPORARY TABLE tmp_applicants (
	  applicant_id bigint(20) ,                            
          applicant_name varchar(100)  ,                         
          applicant_city varchar(50) ,                                 
          applicant_email1 varchar(50) ,                               
          applicant_email2 varchar(50) ,                               
          applicant_home_phone varchar(25) ,                           
          applicant_cell_phone varchar(25) ,                           
          applicant_work_phone varchar(25) ,                           
          applicant_working_since date ,                               
          applicant_position_id int(11) ,                              
          applicant_step_id int(11) ,                                  
          applicant_date_created datetime  default '0000-00-00 00:00:00',  
          source_id int(11) ,                                  
          applicant_joined char(1) ,                           
          applicant_original_resume_path varchar(100)  ,         
          applicant_text_resume longtext,                                          
          applicant_current_employer varchar(250) ,                    
          source_title varchar(250)  ,                           
          processed int(11) 
	)
	ENGINE=MyISAM  AS
	SELECT ta.applicant_id ,ta.applicant_name ,ta.applicant_city ,ta.applicant_email1 ,ta.applicant_email2 ,
	ta.applicant_home_phone ,ta.applicant_cell_phone ,ta.applicant_work_phone ,ta.applicant_working_since ,
	ta.applicant_position_id ,ta.applicant_step_id ,ta.applicant_date_created ,ta.source_id , ta.applicant_joined ,
	ta.applicant_original_resume_path , ta.applicant_text_resume ,ta.applicant_current_employer ,
	ts.source_title, 
	count(tasp.applicant_id) as processed
	";
SET @qry=CONCAT(@qry,"	FROM tp_applicants ta 
	LEFT JOIN tp_applicant_selection_process tasp ON(ta.applicant_id = tasp.applicant_id ) 
	, tp_sources ts ");
IF STRCMP(_eventType, _Type_Skills)=0  THEN
	SET @qry =CONCAT(@qry, ", tp_applicant_skills tas ");
END IF;
IF STRCMP(_eventType, _Type_Degree)=0  THEN
	SET @qry =CONCAT(@qry,", tp_applicant_educational_info taei ");
END IF;
IF STRCMP(_eventType, _Type_Institute)=0  THEN
	SET @qry =CONCAT(@qry,", tp_applicant_educational_info taei ");
END IF;
IF STRCMP(_eventType, _Type_Branch)=0  THEN
	SET @qry =CONCAT(@qry,", tp_applicant_educational_info taei ");
END IF;
 
SET @qry=CONCAT(@qry,"	WHERE ta.source_id = ts.source_id ");
IF STRCMP(_eventType, _Type_Add_Applicant)=0  THEN
	SET @qry =CONCAT(@qry," AND ta.applicant_id =",_eventId);
ELSEIF STRCMP(_eventType, _Type_Skills)=0  THEN
	SET @qry =CONCAT(@qry," AND tas.applicant_id = ta.applicant_id ");
	SET @qry =CONCAT(@qry," AND tas.skill_id=",_eventId);
ELSEIF STRCMP(_eventType, _Type_Degree)=0  THEN
	SET @qry =CONCAT(@qry," ta.applicant_id=taei.applicant_id ");
	SET @qry =CONCAT(@qry," AND taei.degree_id =",_eventId);
ELSEIF STRCMP(_eventType, _Type_Institute)=0  THEN
	SET @qry =CONCAT(@qry," ta.applicant_id=taei.applicant_id ");
	SET @qry =CONCAT(@qry," AND taei.institute_id =",_eventId);
ELSEIF STRCMP(_eventType, _Type_Branch)=0  THEN
	SET @qry =CONCAT(@qry," ta.applicant_id=taei.applicant_id ");
	SET @qry =CONCAT(@qry," AND taei.branch_id =",_eventId);
ELSEIF STRCMP(_eventType, _Type_Source)=0  THEN
	SET @qry =CONCAT(@qry," AND ta.source_id =",_eventId);
END IF;
SET @qry =CONCAT(@qry," GROUP BY
	ta.applicant_id ,ta.applicant_name ,ta.applicant_city ,ta.applicant_email1 ,ta.applicant_email2 ,
	ta.applicant_home_phone ,ta.applicant_cell_phone ,ta.applicant_work_phone ,ta.applicant_working_since ,
	ta.applicant_position_id ,ta.applicant_step_id ,ta.applicant_date_created ,ta.source_id , ta.applicant_joined ,
	ta.applicant_original_resume_path , ta.applicant_text_resume ,ta.applicant_current_employer ,
	ts.source_title 
	ORDER BY ta.applicant_id ");
SET @qry =CONCAT(@qry," LIMIT ",_start ,",", _batch );
PREPARE STMT FROM @qry;
EXECUTE STMT ;
DEALLOCATE PREPARE STMT;
DROP TABLE IF EXISTS tmp_askills;
CREATE TEMPORARY TABLE tmp_askills ( `applicant_id` bigint(20) NOT NULL,                                                                                
                       `skill` VARCHAR(3000),
			PRIMARY KEY  (`applicant_id`)
                     ) ENGINE=MEMORY  DEFAULT CHARSET=latin1;                                                                             
OPEN cur_skills;
REPEAT
	FETCH cur_skills INTO aId, aSkill;
	IF prevId != aId THEN
		IF prevId !=0 THEN
			insert into tmp_askills(applicant_id,skill) VALUES( prevId, totStr);
			SET totStr = '';
		END IF;
		SET prevId = aId;
	END IF;
	IF noRecord = 0 THEN
		IF totStr != '' THEN
			SET totStr = concat(totStr,' ');
		END IF;
		SET totStr = concat(totStr, aSkill);
	END IF;
UNTIL noRecord = 1 
END REPEAT;
CLOSE cur_skills;
IF prevId != 0 THEN
	insert into tmp_askills(applicant_id,skill) VALUES( prevId, totStr);
END IF;
	
SET noRecord = 0;
SET prevId =0;
SET totStr='';
SET aId=0;
SET aSkill='';
DROP TABLE IF EXISTS tmp_aAliases;
CREATE TEMPORARY TABLE tmp_aAliases ( `applicant_id` bigint(20) NOT NULL,                                                                                
                       `alias` VARCHAR(3000),
			PRIMARY KEY  (`applicant_id`)
                     ) ENGINE=MEMORY  DEFAULT CHARSET=latin1;                                                                             
OPEN cur_aliases;
REPEAT
	FETCH cur_aliases INTO aId, aSkill;
	IF prevId != aId THEN
		IF prevId !=0 THEN
			insert into tmp_aAliases(applicant_id,alias) VALUES( prevId, totStr);
			SET totStr = '';
		END IF;
		SET prevId = aId;
	END IF;
	IF noRecord = 0 THEN
		IF totStr != '' THEN
			SET totStr = concat(totStr,' ');
		END IF;
		SET totStr = concat(totStr, aSkill);
	END IF;
UNTIL noRecord = 1 
END REPEAT;
CLOSE cur_aliases;
IF prevId != 0 THEN
	insert into tmp_aAliases(applicant_id,alias) VALUES( prevId, totStr);
END IF;
DROP TABLE IF EXISTS tmp_aedu;
CREATE TEMPORARY TABLE tmp_aedu ( `applicant_id` bigint(20) NOT NULL,                                                                                
                       `degree` VARCHAR(1000)
                     ) ENGINE=MEMORY  DEFAULT CHARSET=latin1;      
/*select from temporary tables but left join on flags to get flag ids*/                                                                       
SELECT 
tmpapp.applicant_id ,tmpapp.applicant_name ,tmpapp.applicant_city ,tmpapp.applicant_email1 ,tmpapp.applicant_email2 ,
tmpapp.applicant_home_phone ,tmpapp.applicant_cell_phone ,tmpapp.applicant_work_phone ,tmpapp.applicant_working_since ,
tmpapp.applicant_position_id ,tmpapp.applicant_step_id ,tmpapp.applicant_date_created ,tmpapp.source_id , 
tmpapp.applicant_joined , tmpapp.applicant_original_resume_path , tmpapp.applicant_text_resume,
tmpapp.applicant_current_employer, tmpapp.source_title, tmpapp.processed,
tmps.skill, tmpal.alias, group_concat(taf.flag_id SEPARATOR ',') as flag_ids
FROM tmp_applicants tmpapp 
LEFT JOIN tmp_aAliases tmpal ON(tmpapp.applicant_id = tmpal.applicant_id)
LEFT JOIN tmp_askills tmps ON(tmpapp.applicant_id = tmps.applicant_id)
LEFT JOIN tp_applicant_flags taf ON( tmpapp.applicant_id = taf.applicant_id)
GROUP BY
tmpapp.applicant_id ,tmpapp.applicant_name ,tmpapp.applicant_city ,tmpapp.applicant_email1 ,tmpapp.applicant_email2 ,
tmpapp.applicant_home_phone ,tmpapp.applicant_cell_phone ,tmpapp.applicant_work_phone ,tmpapp.applicant_working_since ,
tmpapp.applicant_position_id ,tmpapp.applicant_step_id ,tmpapp.applicant_date_created ,tmpapp.source_id , 
tmpapp.applicant_joined , tmpapp.applicant_original_resume_path , tmpapp.applicant_text_resume,
tmpapp.applicant_current_employer, tmpapp.source_title, tmpapp.processed,
tmps.skill, tmpal.alias;
END$$

DELIMITER ;