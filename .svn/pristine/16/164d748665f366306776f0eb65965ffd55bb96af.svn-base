use talentpool;

DELIMITER $$

DROP PROCEDURE IF EXISTS PROC_ALTER_COLUMN_TO_TIMESTAMP $$

CREATE PROCEDURE PROC_ALTER_COLUMN_TO_TIMESTAMP(IN tableName VARCHAR(128), IN columnName VARCHAR(128)) 
BEGIN
	insert into proc_log (table_name, column_name, update_status) values (tableName, columnName, 0);
	BEGIN 
		DECLARE EXIT HANDLER FOR SQLEXCEPTION set @x=1;
		SET @s = concat('ALTER TABLE ', tableName, ' MODIFY COLUMN ', columnName, ' TIMESTAMP NULL');
	
		PREPARE stmt FROM @s;
		EXECUTE stmt;
		
		UPDATE proc_log set update_status =1 where table_name = tableName 
		AND column_name= columnName;

	END;
 
END $$

DELIMITER ;