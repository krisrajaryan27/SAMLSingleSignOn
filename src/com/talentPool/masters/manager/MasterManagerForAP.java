/**
 * 
 */
package com.talentPool.masters.manager;

import java.sql.SQLException;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.masters.dataobject.DepartmentData;
import com.talentPool.masters.dataobject.LocationData;
import com.talentPool.masters.dataobject.MasterDataForAP;
import com.talentPool.masters.exception.MasterExistException;

/**
 * @author ArvindKhatik
 *
 */
public class MasterManagerForAP {

	/**
	 * 
	 */
	public MasterManagerForAP() {
		// TODO Auto-generated constructor stub
	}

	// A Method to find out existing department externalCode
	public String getDuplicateDepartment(String externalCode, String dept_level) {
		String duplicateExternalCode = "";
		DBPreparedQuery dq = null;
		if (!Utils.isBlankOrNull(externalCode)) {
			try {
				dq = new DBPreparedQuery("dMastersManagerForAP_GetDepExternalCodeFromDBForAP");
				dq.setString(1, externalCode);
				dq.setString(2, dept_level);

				duplicateExternalCode = dq.getStringResult();
			} catch (Exception e) {
				TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			} finally {
				if (dq != null) {
					dq.releaseConnection();
				}
			}
		}
		return duplicateExternalCode;
	}// end of method getDuplicateDeptExternalCode

	/**
	 * Method to add Dept to DB
	 * 
	 * @param departmentMaster
	 * @throws SQLException
	 */
	public void addDeptToDB(String departmentName, String parentDepartmentId, String externalCode, String dept_level)
			throws Exception {
		DBPreparedQuery dq = null;
		if (!Utils.isBlankOrNull(externalCode)) {
			try {
				String duplicateDept = getDuplicateDepartment(externalCode, dept_level);
				if (!Utils.isBlankOrNull(duplicateDept)) {
					dq = new DBPreparedQuery("dMastersManagerForAP_UpdateDeptByExternalCodeForAP");
					dq.setString(1, departmentName);
					if (parentDepartmentId.equals("0")) {
						parentDepartmentId = null;
					}
					dq.setString(2, parentDepartmentId);
					dq.setString(3, externalCode);
					dq.setString(4, dept_level);
					dq.execute();

				} else {
					dq = new DBPreparedQuery("dMastersManagerForAP_AddDepToDBForAP");
					dq.setString(1, departmentName);
					if (parentDepartmentId.equals("0")) {
						parentDepartmentId = null;
					}
					dq.setString(2, parentDepartmentId);
					dq.setString(3, externalCode);
					dq.setString(4, dept_level);
					dq.execute();

				}
			} catch (SQLException e) {
				TPLogger.getLogger().error("Error While Inserting Dept", e);
			} finally {
				if (dq != null) {
					dq.releaseConnection();
				}
			}
		}
	}

	// A Method to find out existing JobFunction externalCode
	public String getDuplicateJobFunction(String externalCode) {
		String duplicateExternalCode = "";
		DBPreparedQuery dq = null;
		if (!Utils.isBlankOrNull(externalCode)) {
			try {
				dq = new DBPreparedQuery("dMastersManagerForAP_GetJobFunctionExternalCodeFromDBForAP");
				dq.setString(1, externalCode);

				duplicateExternalCode = dq.getStringResult();
			} catch (Exception e) {
				TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			} finally {
				if (dq != null) {
					dq.releaseConnection();
				}
			}
		}
		return duplicateExternalCode;
	}// end of method getDuplicateDeptExternalCode

	/**
	 * Method to add JobFunction to DB
	 * 
	 * @param JobFunctionMaster
	 * @throws SQLException
	 */
	public void addJobFunctionToDB(String jobFunctionName, String parentJobFunctionId, String externalCode)
			throws Exception {
		DBPreparedQuery dq = null;
		if (!Utils.isBlankOrNull(externalCode)) {
			try {
				String duplicateJobFunction = getDuplicateJobFunction(externalCode);
				if (!Utils.isBlankOrNull(duplicateJobFunction)) {
					dq = new DBPreparedQuery("dMastersManagerForAP_UpdateJobFunctionByExternalCodeForAP");
					dq.setString(1, jobFunctionName);
					if (parentJobFunctionId.equals("0")) {
						parentJobFunctionId = null;
					}
					dq.setString(2, parentJobFunctionId);
					dq.setString(3, externalCode);
					dq.execute();

				} else {
					dq = new DBPreparedQuery("dMastersManagerForAP_AddJobFunctionToDBForAP");
					dq.setString(1, jobFunctionName);
					if (parentJobFunctionId.equals("0")) {
						parentJobFunctionId = null;
					}
					dq.setString(2, parentJobFunctionId);
					dq.setString(3, externalCode);
					dq.execute();

				}
			} catch (SQLException e) {
				TPLogger.getLogger().error("Error While Inserting JobFunction", e);
			} finally {
				if (dq != null) {
					dq.releaseConnection();
				}
			}
		}
	}

	// To get department parent id
	public MasterDataForAP getDepParentIdFromDBByExternalCode(String externalCode) {
		DBPreparedQuery dq = null;
		MasterDataForAP adata = null;
		if (!Utils.isBlankOrNull(externalCode)) {
			try {
				dq = new DBPreparedQuery("dMastersManagerForAP_GetDepParentIdFromDBByExternalCode");
				dq.setString(1, externalCode);
				adata = (MasterDataForAP) dq.getSingleObjectResult();
			} catch (SQLException e) {
				TPLogger.getLogger().error("Error While getting  Dept Parent ID", e);
			} finally {
				if (dq != null) {
					dq.releaseConnection();
				}
			}
		}
		return adata;
	}

	// A Method to find out existing JobCode externalCode
	public String getDuplicateJobCode(String externalCode) {
		String duplicateExternalCode = "";
		DBPreparedQuery dq = null;
		if (!Utils.isBlankOrNull(externalCode)) {
			try {
				dq = new DBPreparedQuery("dMastersManagerForAP_GetJobExternalCodeFromDBForAP");
				dq.setString(1, externalCode);

				duplicateExternalCode = dq.getStringResult();
			} catch (Exception e) {
				TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			} finally {
				if (dq != null) {
					dq.releaseConnection();
				}
			}
		}
		return duplicateExternalCode;
	}// end of method getDuplicateJobCode

	public void addJobCodeToDB(String jobName, String parentJobId, String externalCode) throws Exception {
		DBPreparedQuery dq = null;
		if (!Utils.isBlankOrNull(externalCode)) {
			try {
				String duplicateJobCode = getDuplicateJobCode(externalCode);
				if (!Utils.isBlankOrNull(duplicateJobCode)) {
					dq = new DBPreparedQuery("dMastersManagerForAP_UpdateJobCodeByExternalCodeForAP");
					dq.setString(1, jobName);
					if (parentJobId.equals("0")) {
						parentJobId = null;
					}
					dq.setString(2, parentJobId);
					dq.setString(3, externalCode);
					dq.execute();

				} else {
					dq = new DBPreparedQuery("dMastersManagerForAP_AddJobCodeToDBForAP");
					dq.setString(1, jobName);
					if (parentJobId.equals("0")) {
						parentJobId = null;
					}
					dq.setString(2, parentJobId);
					dq.setString(3, externalCode);
					dq.execute();

				}
			} catch (SQLException e) {
				TPLogger.getLogger().error("Error While Inserting JobCode", e);
			} finally {
				if (dq != null) {
					dq.releaseConnection();
				}
			}
		}
	}

	// To get JobCode parent id
	public MasterDataForAP getJobCodeParentIdFromDBByExternalCode(String externalCode) {
		DBPreparedQuery dq = null;
		MasterDataForAP adata = null;
		if (!Utils.isBlankOrNull(externalCode)) {
			try {
				dq = new DBPreparedQuery("dMastersManagerForAP_GetJobCodeParentIdFromDBByExternalCode");
				dq.setString(1, externalCode);
				adata = (MasterDataForAP) dq.getSingleObjectResult();
			} catch (SQLException e) {
				TPLogger.getLogger().error("Error While getting  JobCode Parent ID", e);
			} finally {
				if (dq != null) {
					dq.releaseConnection();
				}
			}
		}
		return adata;
	}

	// A Method to find out existing JobCode externalCode
	public String getDuplicatePayGrade(String externalCode) {
		String duplicateExternalCode = "";
		DBPreparedQuery dq = null;
		if (!Utils.isBlankOrNull(externalCode)) {
			try {
				dq = new DBPreparedQuery("dMastersManagerForAP_GetPayGradeExternalCodeFromDBForAP");
				dq.setString(1, externalCode);

				duplicateExternalCode = dq.getIdResult();
			} catch (Exception e) {
				TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			} finally {
				if (dq != null) {
					dq.releaseConnection();
				}
			}
		}
		return duplicateExternalCode;
	}// end of method getDuplicateJobCode

	public void addPayGradeToDB(String payGradeName, String parentPayGradeId, String externalCode) throws Exception {
		DBPreparedQuery dq = null;
		if (!Utils.isBlankOrNull(externalCode)) {
			try {
				String duplicatePayGrade = getDuplicatePayGrade(externalCode);
				if (!Utils.isBlankOrNull(duplicatePayGrade)) {
					dq = new DBPreparedQuery("dMastersManagerForAP_UpdatePayGradeByExternalCodeForAP");
					dq.setString(1, payGradeName);
					if (parentPayGradeId.equals("0")) {
						parentPayGradeId = null;
					}
					dq.setString(2, parentPayGradeId);
					dq.setString(3, externalCode);
					dq.execute();

				} else {
					dq = new DBPreparedQuery("dMastersManagerForAP_AddPayGradeToDBForAP");
					dq.setString(1, payGradeName);
					if (parentPayGradeId.equals("0")) {
						parentPayGradeId = null;
					}
					dq.setString(2, parentPayGradeId);
					dq.setString(3, externalCode);
					dq.execute();

				}
			} catch (SQLException e) {
				TPLogger.getLogger().error("Error While Inserting PayGrade", e);
			} finally {
				if (dq != null) {
					dq.releaseConnection();
				}
			}
		}
	}

	// A method to get PayGrade parent id
	public MasterDataForAP getPayGradeParentIdFromDBByExternalCode(String externalCode) {
		DBPreparedQuery dq = null;
		MasterDataForAP adata = null;
		if (!Utils.isBlankOrNull(externalCode)) {
			try {
				dq = new DBPreparedQuery("dMastersManagerForAP_GetPayGradeParentIdFromDBByExternalCode");
				dq.setString(1, externalCode);
				adata = (MasterDataForAP) dq.getSingleObjectResult();
			} catch (SQLException e) {
				TPLogger.getLogger().error("Error While getting  PayGrade Parent ID", e);
			} finally {
				if (dq != null) {
					dq.releaseConnection();
				}
			}
		}
		return adata;
	}//

	// A Method to find out existing location externalCode
	public String getDuplicateLoc(String externalCode, String loc_level) {
		String duplicateExternalCode = "";
		DBPreparedQuery dq = null;
		{
			try {
				dq = new DBPreparedQuery("dMastersManagerForAP_GetLocExternalCodeFromDBForAP");
				dq.setString(1, externalCode);
				dq.setString(2, loc_level);

				duplicateExternalCode = dq.getStringResult();
			} catch (Exception e) {
				TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			} finally {
				if (dq != null) {
					dq.releaseConnection();
				}
			}
		}
		return duplicateExternalCode;
	}// end of method getDuplicateLocations

	// A Method to find out existing location externalCode
	public String getDuplicateLocationName(String locationName, String parentLocationId) {
		String duplicateLocationName = "";
		DBPreparedQuery dq = null;
		try {
			if (parentLocationId.equals("0")) {
				parentLocationId = null;
				dq = new DBPreparedQuery("dMastersManagerForAP_GetLocFromDBForNullParent");
				dq.setString(1, locationName);
			} else {
				dq = new DBPreparedQuery("dMastersManagerForAP_GetLocFromDB");
				dq.setString(1, locationName);
				dq.setString(2, parentLocationId);
			}

			duplicateLocationName = dq.getIdResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return duplicateLocationName;
	}// end of method getDuplicateLocationName

	/**
	 * Method to add Locations to DB
	 * 
	 * @author ArvindK
	 * @param locationMaster
	 * @throws SQLException
	 */
	public void addLocToDB(String locationName, String parentLocationId, String externalCode, String isRegion,
			String loc_level) throws Exception {
		DBPreparedQuery dq = null;
		if (!Utils.isBlankOrNull(externalCode)) {
			try {
				String duplicateLoc = getDuplicateLoc(externalCode, loc_level);
				if (!Utils.isBlankOrNull(duplicateLoc)) {
					dq = new DBPreparedQuery("dMastersManagerForAP_UpdateLocByExternalCodeForAP");
					dq.setString(1, locationName);
					if (parentLocationId.equals("0")) {
						parentLocationId = null;
					}
					if (isRegion.isEmpty()) {
						isRegion = null;
					}
					dq.setString(2, parentLocationId);
					dq.setString(3, isRegion);
					dq.setString(4, externalCode);
					dq.setString(5, loc_level);
					dq.execute();

				} else {
					dq = new DBPreparedQuery("dMastersManagerForAP_AddLocToDBForAP");
					dq.setString(1, locationName);
					if (parentLocationId.equals("0")) {
						parentLocationId = null;
					}
					if (isRegion.isEmpty()) {
						isRegion = null;
					}
					dq.setString(2, parentLocationId);
					dq.setString(3, externalCode);
					dq.setString(4, isRegion);
					dq.setString(5, loc_level);
					dq.execute();

				}

			} catch (SQLException e) {
				TPLogger.getLogger().error("Error While Inserting Location", e);
			} finally {
				if (dq != null) {
					dq.releaseConnection();
				}
			}
		}
	}// end of method addLocToDB

	/**
	 * Method to add Locations to DB
	 * 
	 * @author ArvindK
	 * @param departmentMaster
	 * @throws SQLException
	 */
	public void addLocationToDB(String locationName, String parentLocationId) throws Exception {
		DBPreparedQuery dq = null;
		try {
			String duplicateDept = getDuplicateLocationName(locationName, parentLocationId);
			if (!Utils.isBlankOrNull(duplicateDept)) {
				throw new MasterExistException();
			} else {
				dq = new DBPreparedQuery("dMastersManagerForAP_AddLocToDB");
				dq.setString(1, locationName);
				if (parentLocationId.equals("0")) {
					parentLocationId = null;
				}
				dq.setString(2, parentLocationId);
				dq.execute();
			}
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error While Inserting Location", e);
			throw new MasterExistException();
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}

	}// end of method addLocationToDB

	// To get Location parent id
	public MasterDataForAP getLocParentIdFromDBByExternalCode(String externalCode) {
		DBPreparedQuery dq = null;
		MasterDataForAP adata = null;
		if (!Utils.isBlankOrNull(externalCode)) {
			try {
				dq = new DBPreparedQuery("dMastersManagerForAP_GetLocParentIdFromDBByExternalCode");
				dq.setString(1, externalCode);
				adata = (MasterDataForAP) dq.getSingleObjectResult();
			} catch (SQLException e) {
				TPLogger.getLogger().error("Error While getting  Location Parent ID", e);
			} finally {
				if (dq != null) {
					dq.releaseConnection();
				}
			}
		}
		return adata;
	}

	/**
	 * Method to get Loc Name
	 * 
	 * @param locId
	 * @return
	 */
	public LocationData getLocData(String locId) {
		DBPreparedQuery dq = null;
		LocationData adata = null;
		try {
			dq = new DBPreparedQuery("dMastersManagerForAP_GetLocData");
			dq.setId(1, locId);
			adata = (LocationData) dq.getSingleObjectResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error While getting  Loc name", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return adata;
	}

	/**
	 * Method to update Loc
	 * 
	 * @param locName
	 * @param locId
	 * @throws SQLException
	 */
	public void updateLoc(String locName, String locId) throws MasterExistException {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dMastersManagerForAP_UpdateLoc");
			dq.setString(1, locName);
			dq.setId(2, locId);
			dq.execute();
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error While Updating Loc", e);
			throw new MasterExistException();
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}

}
