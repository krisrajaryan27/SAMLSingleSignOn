DELIMITER $$

DROP FUNCTION IF EXISTS `titleCase`$$

CREATE FUNCTION `titleCase` ( _InputString varchar(255))
	RETURNS varchar(255)
BEGIN
DECLARE aIndex INT;
DECLARE aChar           CHAR(1);
DECLARE outputString   VARCHAR(255);

SET outputString=LCASE(_InputString);
SET aIndex=2;
SET outputString=INSERT(outputString,1,1,UCASE(SUBSTRING(_InputString,1,1)));


WHILE aIndex <= LENGTH(_InputString) DO 
	SET aChar = SUBSTRING(_InputString, aIndex, 1);
	IF aChar IN (' ', ';', ':', '!', '?', ',', '.', '_', '-', '/', '&','''', '(') THEN
		IF aIndex + 1 <= LENGTH(_InputString) THEN
			IF aChar != '' OR UPPER(SUBSTRING(_InputString, aIndex + 1, 1)) != 'S' THEN
				SET outputString = INSERT(outputString, aIndex + 1, 1,UPPER(SUBSTRING(_InputString, aIndex + 1, 1)));
			END IF;
		END IF;
	END IF;

	SET aIndex = aIndex + 1;
END WHILE;

RETURN outputString;

END$$
DELIMITER ;