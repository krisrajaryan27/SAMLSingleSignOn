package com.talentPool.common.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.talentPool.common.utils.EncryptionUtils;
import com.talentPool.common.utils.Utils;

public enum IterateLogic {
	ITERATE_TEXT_RESUME() {
		@Override
		void resultSetIterator(ResultSet rs, PreparedStatement prest) throws SQLException {
			while (rs.next()) {
				int id = rs.getInt("applicant_id");
				String applicant_text_resume = rs.getString("applicant_text_resume");

				if (!Utils.isBlankOrNull(applicant_text_resume)) {
					if (checkIfNotEncrypted(applicant_text_resume, DBConstants.columnKeyMap.get("applicant_text_resume"))) {
						applicant_text_resume = EncryptionUtils.encrypt(applicant_text_resume, DBConstants.columnKeyMap.get("applicant_text_resume"));
					}
				}
				prest.setString(1, applicant_text_resume);
				prest.setInt(2, id);
				prest.executeUpdate();
			}

		}

	},
	ITERATE_TP_APPLICANTS() {

		@Override
		void resultSetIterator(ResultSet rs, PreparedStatement prest)throws SQLException {
			while (rs.next()) {
				int id = rs.getInt("applicant_id");
				String passport_number = rs.getString("passport_number");
				String current_ctc = rs.getString("current_ctc");
				String expected_ctc = rs.getString("expected_ctc");
				String offered_basic = rs.getString("offered_basic");
				String offered_ctc = rs.getString("offered_ctc");
				String date_of_birth = rs.getString("date_of_birth");

				if (!Utils.isBlankOrNull(passport_number)) {
					if(checkIfNotEncrypted(passport_number, DBConstants.columnKeyMap.get("passport_number"))){
						passport_number = EncryptionUtils.encrypt(passport_number,DBConstants.columnKeyMap.get("passport_number"));
					}
				}
				if (!Utils.isBlankOrNull(current_ctc)) {
					if(checkIfNotEncrypted(current_ctc, DBConstants.columnKeyMap.get("current_ctc"))){
						current_ctc = EncryptionUtils.encrypt(current_ctc,DBConstants.columnKeyMap.get("current_ctc"));
					}
				}
				if (!Utils.isBlankOrNull(expected_ctc)) {
					if(checkIfNotEncrypted(expected_ctc, DBConstants.columnKeyMap.get("expected_ctc"))){
						expected_ctc = EncryptionUtils.encrypt(expected_ctc,DBConstants.columnKeyMap.get("expected_ctc"));
					}
				}
				if (!Utils.isBlankOrNull(offered_basic)) {
					if(checkIfNotEncrypted(offered_basic, DBConstants.columnKeyMap.get("offered_basic"))){
						offered_basic = EncryptionUtils.encrypt(offered_basic,DBConstants.columnKeyMap.get("offered_basic"));
					}
				}
				if (!Utils.isBlankOrNull(offered_ctc)) {
					if(checkIfNotEncrypted(offered_ctc,DBConstants.columnKeyMap.get("offered_ctc"))){
						offered_ctc = EncryptionUtils.encrypt(offered_ctc,DBConstants.columnKeyMap.get("offered_ctc"));
					}
				}
				if (!Utils.isBlankOrNull(date_of_birth)) {
					if(checkIfNotEncrypted(date_of_birth,DBConstants.columnKeyMap.get("date_of_birth"))){
						date_of_birth = EncryptionUtils.encrypt(date_of_birth,DBConstants.columnKeyMap.get("date_of_birth"));
					}
				}

				prest.setString(1, passport_number);
				prest.setString(2, current_ctc);
				prest.setString(3, expected_ctc);
				prest.setString(4, offered_basic);
				prest.setString(5, offered_ctc);
				prest.setString(6, date_of_birth);
				prest.setInt(7, id);
				prest.executeUpdate();
			}

		}

	},
	ITERATE_APPLICANT_CURRENT_DETAILS() {

		@Override
		void resultSetIterator(ResultSet rs, PreparedStatement prest)
				throws SQLException {
			while (rs.next()) {

				int id = rs.getInt("applicant_id");
				String current_basic = rs.getString("current_basic");

				if (!Utils.isBlankOrNull(current_basic)) {
					if(checkIfNotEncrypted(current_basic,DBConstants.columnKeyMap.get("current_basic"))){
						current_basic = EncryptionUtils.encrypt(current_basic,DBConstants.columnKeyMap.get("current_basic"));
					}
				}

				prest.setString(1, current_basic);
				prest.setInt(2, id);
				prest.executeUpdate();
			}

		}

	},
	ITERATE_CR_CANDIDATE_MASTER(){

		@Override
		void resultSetIterator(ResultSet rs, PreparedStatement prest)
				throws SQLException {
			while (rs.next()) {
				int id = rs.getInt("applicant_id");
				String expected_ctc = rs.getString("expected_ctc");
				String current_ctc = rs.getString("current_ctc");
				String passport_number = rs.getString("passport_number");
				String date_of_birth = rs.getString("date_of_birth");

				if (!Utils.isBlankOrNull(expected_ctc)) {
					if(checkIfNotEncrypted(expected_ctc,DBConstants.columnKeyMap.get("expected_ctc"))){
						expected_ctc = EncryptionUtils.encrypt(expected_ctc,DBConstants.columnKeyMap.get("expected_ctc"));
					}
				}
				if (!Utils.isBlankOrNull(current_ctc)) {
					if(checkIfNotEncrypted(current_ctc,DBConstants.columnKeyMap.get("current_ctc"))){
						current_ctc = EncryptionUtils.encrypt(current_ctc,DBConstants.columnKeyMap.get("current_ctc"));
					}
				}
				if (!Utils.isBlankOrNull(passport_number)) {
					if(checkIfNotEncrypted(passport_number,DBConstants.columnKeyMap.get("passport_number"))){
						passport_number = EncryptionUtils.encrypt(passport_number,DBConstants.columnKeyMap.get("passport_number"));
					}
				}
				if (!Utils.isBlankOrNull(date_of_birth)) {
					if(checkIfNotEncrypted(date_of_birth,DBConstants.columnKeyMap.get("date_of_birth"))){
						date_of_birth = EncryptionUtils.encrypt(date_of_birth,DBConstants.columnKeyMap.get("date_of_birth"));
					}
				}

				prest.setString(1, expected_ctc);
				prest.setString(2, current_ctc);
				prest.setString(3, passport_number);
				prest.setString(4, date_of_birth);
				prest.setInt(5, id);
				prest.executeUpdate();
			}

		}
		
	},
	ITERATE_APPLICANT_JOINING_HISTORY(){

		@Override
		void resultSetIterator(ResultSet rs, PreparedStatement prest)
				throws SQLException {
			while (rs.next()) {
				int id = rs.getInt("applicant_joining_history_id");
				String offered_ctc = rs.getString("offered_ctc");

				if (!Utils.isBlankOrNull(offered_ctc)) {
					if(checkIfNotEncrypted(offered_ctc,DBConstants.columnKeyMap.get("offered_ctc"))){
						offered_ctc = EncryptionUtils.encrypt(offered_ctc,DBConstants.columnKeyMap.get("offered_ctc"));
					}
				}

				prest.setString(1, offered_ctc);
				prest.setInt(2, id);
				prest.executeUpdate();
			}
	
		}
		
	},
	ITERATE_EXCEL_IMPORT(){

		@Override
		void resultSetIterator(ResultSet rs, PreparedStatement prest)
				throws SQLException {
			
			while (rs.next()) {
				int id = rs.getInt("row_id");
				String expected_ctc = rs.getString("expected_ctc");
				String current_ctc = rs.getString("current_ctc");

				if (!Utils.isBlankOrNull(expected_ctc)) {
					if(checkIfNotEncrypted(expected_ctc,DBConstants.columnKeyMap.get("expected_ctc"))){
						expected_ctc = EncryptionUtils.encrypt(expected_ctc,DBConstants.columnKeyMap.get("expected_ctc"));
					}
				}
				if (!Utils.isBlankOrNull(current_ctc)) {
					if(checkIfNotEncrypted(current_ctc,DBConstants.columnKeyMap.get("current_ctc"))){
						current_ctc = EncryptionUtils.encrypt(current_ctc,DBConstants.columnKeyMap.get("current_ctc"));
					}
				}

				prest.setString(1, expected_ctc);
				prest.setString(2, current_ctc);
				prest.setInt(3, id);
				prest.executeUpdate();
			}

		}
		
	},
	ITERATE_BULK_IMPORT_SESSIONS(){

		@Override
		void resultSetIterator(ResultSet rs, PreparedStatement prest)
				throws SQLException {
			
			while (rs.next()) {
				String id = rs.getString("session_id");
				String expected_ctc = rs.getString("expected_ctc");
				String current_ctc = rs.getString("current_ctc");

				if (!Utils.isBlankOrNull(expected_ctc)) {
					if(checkIfNotEncrypted(expected_ctc,DBConstants.columnKeyMap.get("expected_ctc"))){
						expected_ctc = EncryptionUtils.encrypt(expected_ctc,DBConstants.columnKeyMap.get("expected_ctc"));
					}
				}
				if (!Utils.isBlankOrNull(current_ctc)) {
					if(checkIfNotEncrypted(current_ctc,DBConstants.columnKeyMap.get("current_ctc"))){
						current_ctc = EncryptionUtils.encrypt(current_ctc,DBConstants.columnKeyMap.get("current_ctc"));
					}
				}

				prest.setString(1, expected_ctc);
				prest.setString(2, current_ctc);
				prest.setString(3, id);
				prest.executeUpdate();
			}

		}
		
	};

	abstract void resultSetIterator(ResultSet rs, PreparedStatement prest)throws SQLException;
	
	private static boolean checkIfNotEncrypted(final String value,final String key){
		String tmp = EncryptionUtils.decrypt(value, key);
		if(Utils.isBlankOrNull(tmp)){
			return true;
		}else{
			return false;
		}
	}
}
