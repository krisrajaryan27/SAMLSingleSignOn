DELIMITER $$

DROP FUNCTION IF EXISTS `getdatediff`$$

CREATE FUNCTION `getdatediff` (fdt date, tdt date, subfdt date, subtdt date)
	RETURNS int(11)
BEGIN

RETURN CASE WHEN fdt <= subfdt AND tdt >= subtdt THEN (datediff(subtdt, subfdt) + 1 )
     WHEN fdt <= subfdt AND tdt <= subtdt AND tdt >= subfdt THEN datediff(tdt, subfdt) + 1
     WHEN fdt >= subfdt AND tdt >= subtdt AND fdt <= subtdt THEN datediff(subtdt, fdt) + 1
     WHEN fdt >= subfdt AND tdt <= subtdt THEN datediff(tdt, fdt) + 1 
ELSE 0
END;

END$$

DELIMITER ;