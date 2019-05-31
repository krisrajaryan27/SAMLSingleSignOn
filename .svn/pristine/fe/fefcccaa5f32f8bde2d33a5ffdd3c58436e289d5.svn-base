/**
 * 
 */
package com.talentPool.positions.manager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.positions.dataobject.PositionDataForAP;

/**
 * @author ArvindKhatik
 *
 */
public class PositionManagerForAP {

	/**
	 * 
	 */
	public PositionManagerForAP() {
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unused")
	private List<PositionDataForAP> positionCodeListForAP = null;

	/**
	 * @return the positionCodeListForAP
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<PositionDataForAP> getPositionCodeListForAP() throws SQLException {
		List<PositionDataForAP> positions = new ArrayList<PositionDataForAP>();
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dPositionManagerForAP_GetAllPositionsFromDBForAP");
			positions = dq.getResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseTransaction(null);
			}
		}
		return positions;
	}
	
	/**
	 * @return the positionCodeListForAP
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<PositionDataForAP> getPositionDataListFromDBForAP(String companyCode,
			String businessUnitCode,String deptCode, String functionCode,  String jobCode, String payGradeCode, String divisionCode,
			String regionCode, String locationCode, String positionCode) throws SQLException {
		List<PositionDataForAP> positionData = new ArrayList<PositionDataForAP>();
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dPositionManagerForAP_GetPositionDataForAP");
			dq.setString(1, companyCode);
			dq.setString(2, businessUnitCode);
			dq.setString(3, deptCode);
			dq.setString(4, functionCode);
			dq.setString(5, jobCode);
			dq.setString(6, payGradeCode);
			dq.setString(7, divisionCode);
			dq.setString(8, regionCode);
			dq.setString(9, locationCode);
			dq.setString(10, companyCode);
			dq.setString(11, businessUnitCode);
			dq.setString(12, deptCode);
			dq.setString(13, positionCode);
			dq.setString(14, divisionCode);
			dq.setString(15, regionCode);
			dq.setString(16, locationCode);
			dq.setString(17, functionCode);
			dq.setString(18, payGradeCode);
			dq.setString(19, jobCode);
			dq.setString(20, jobCode);
			dq.setString(21, companyCode);
			dq.setString(22, businessUnitCode);
			dq.setString(23, deptCode);
			dq.setString(24, functionCode);
			dq.setString(25, divisionCode);
			dq.setString(26, regionCode);
			dq.setString(27, locationCode);
			dq.setString(28, payGradeCode);
			
			positionData = dq.getResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseTransaction(null);
			}
		}
		return positionData;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<SimpleDataObject> getPositionCodeForAP(String positionCodeForAP) {
		DBPreparedQuery dq = null;
		ArrayList<SimpleDataObject> sDo = null;
		try {
			dq = new DBPreparedQuery("dPositionManagerForAP_GetPositionCodeForAP");
			dq.setString(1, "%" + positionCodeForAP + "%");
			sDo = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return sDo;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<SimpleDataObject> getPositionNameForAP(String positionName) {
		DBPreparedQuery dq = null;
		ArrayList<SimpleDataObject> sDo = null;
		try {
			dq = new DBPreparedQuery("dPositionManagerForAP_GetPositionNameForAP");
			dq.setString(1, "%" + positionName + "%");
			sDo = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return sDo;
	}
	
	public String getPositionEffectiveStartDateForAP(String positionCode) {
		DBPreparedQuery dq = null;
		String getSingleObjResult = StringUtils.EMPTY;
		try {
			dq = new DBPreparedQuery("dPositionManagerForAP_GetPositionEffectiveStartDateForAP");
			dq.setString(1, positionCode );
			getSingleObjResult = dq.getStringResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return getSingleObjResult;
	}

	/**
	 * @param positionCodeListForAP
	 *            the positionCodeListForAP to set
	 */
	public void setPositionCodeListForAP(List<PositionDataForAP> positionCodeListForAP) {
		this.positionCodeListForAP = positionCodeListForAP;
	}

	// A Method to find out existing Position externalCode
	public String getDuplicatePosition(String positionCode) {
		String duplicatePositionCode = "";
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dPositionManagerForAP_GetPositionCodeFromDBForAP");
			dq.setString(1, positionCode);

			duplicatePositionCode = dq.getStringResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return duplicatePositionCode;
	}// end of method getDuplicatePosition

	/**
	 * Method to add Position to DB
	 * 
	 * @throws SQLException
	 */
	public void addPositionToDB(String positionCode, String positionTitle, String effectiveStartDate) throws Exception {
		DBPreparedQuery dq = null;
		try {
			String duplicatePos = getDuplicatePosition(positionCode);
			if (!Utils.isBlankOrNull(duplicatePos)) {
				dq = new DBPreparedQuery("dPositionManagerForAP_UpdatePositionByExternalCodeForAP");
				dq.setString(1, positionTitle);
				dq.setString(2, effectiveStartDate);
				dq.setString(3, positionCode);
				dq.execute();

			} else {
				dq = new DBPreparedQuery("dPositionManagerForAP_AddPositionToDBForAP");
				dq.setString(1, positionCode);
				dq.setString(2, positionTitle);
				dq.setString(3, effectiveStartDate);
				dq.execute();

			}
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error While Inserting Position", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}

}
