use talentpool;

DELIMITER $$

DROP PROCEDURE IF EXISTS PROC_DROP_FOREIGN_KEY_IF_EXISTS $$
CREATE PROCEDURE PROC_DROP_FOREIGN_KEY_IF_EXISTS(in theTable varchar(128), in foreignKeyName varchar(128) )
BEGIN
 IF((select count(*) as key_count from information_schema.TABLE_CONSTRAINTS where 
 CONSTRAINT_SCHEMA = database() AND
 CONSTRAINT_TYPE   = 'FOREIGN KEY' and CONSTRAINT_NAME = foreignKeyName
 AND table_name = theTable) > 0) THEN
   SET @s = CONCAT('ALTER TABLE ', theTable, ' DROP FOREIGN KEY ',  foreignKeyName);
   PREPARE stmt FROM @s;
   EXECUTE stmt;
 END IF;
END $$

DELIMITER ;