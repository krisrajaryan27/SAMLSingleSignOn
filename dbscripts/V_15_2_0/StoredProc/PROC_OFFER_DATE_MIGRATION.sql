use talentpool;
DELIMITER $$

DROP PROCEDURE IF EXISTS PROC_OFFER_DATE_MIGRATION $$

CREATE PROCEDURE PROC_OFFER_DATE_MIGRATION()
BEGIN

DECLARE v_noRecord INT DEFAULT 0;
DECLARE v_applicant_id BIGINT(20);
DECLARE v_offer_issuance_date DATE;
DECLARE v_doc_id BIGINT(20);
DECLARE v_offer_count INT DEFAULT 0;

DECLARE cur1 CURSOR FOR 
	SELECT entity_id, date_value
	FROM tp_custom_field_values_applicant tcfva
		JOIN tp_custom_fields tcf ON (tcf.custom_field_id = tcfva.custom_field_id 
			AND custom_field_display_name = 'Offer Issuance Date');

DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_noRecord = 1;

OPEN cur1;
REPEAT
	FETCH cur1 INTO v_applicant_id, v_offer_issuance_date;
    
	SELECT COUNT(1) INTO v_offer_count FROM tp_applicant_offer_sheet_details WHERE applicant_id = v_applicant_id;

	IF v_offer_count = 0 THEN
		INSERT INTO tp_applicant_documents (applicant_id, file_name, document_path, user_id, date_created)
			VALUES (v_applicant_id, '', '', 1, v_offer_issuance_date);

		SELECT LAST_INSERT_ID() INTO v_doc_id; 

		INSERT INTO tp_applicant_offer_sheet_details (applicant_id, offer_letter_doc_id)
			VALUES (v_applicant_id, v_doc_id);
	END IF;

UNTIL v_noRecord = 1
END REPEAT;

CLOSE cur1;

END $$

DELIMITER ;