use talentpool;

DELIMITER $$

DROP PROCEDURE IF EXISTS PROC_ADD_COLUMN_IF_NOT_EXISTS $$

CREATE PROCEDURE PROC_ADD_COLUMN_IF_NOT_EXISTS(IN tableName VARCHAR(128), IN columnName VARCHAR(128),
	IN columnType VARCHAR(128)) 
BEGIN 
	IF ((SELECT count(*)
        FROM INFORMATION_SCHEMA.COLUMNS 
        WHERE  TABLE_SCHEMA = 'talentpool' 
		AND    TABLE_NAME = tableName
        AND    COLUMN_NAME = columnName) = 0) 
    THEN 
      SET @s = concat('ALTER TABLE ', tableName, ' ADD COLUMN ', columnName, ' ', columnType);
      PREPARE stmt FROM @s;
      EXECUTE stmt;
    END IF;
END $$

DELIMITER ;