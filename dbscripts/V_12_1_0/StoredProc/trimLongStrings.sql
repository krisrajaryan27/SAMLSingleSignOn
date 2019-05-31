DELIMITER $$

DROP FUNCTION IF EXISTS `trimLongStrings`$$

CREATE FUNCTION `trimLongStrings`( _InputString text) RETURNS varchar(255)
BEGIN
DECLARE outputString   VARCHAR(255);
RETURN CASE WHEN length(_InputString) >200 THEN CONCAT(SUBSTR(_InputString,1,197),'...') ELSE _InputString END;
END$$

DELIMITER ;