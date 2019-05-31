/**
 * 
 */
package com.talentPool.masters.manager;

import java.io.StringWriter;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.talentPool.admin.AdminConstants;
import com.talentPool.admin.manager.AdminManager;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.DBTransaction;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.db.Exception.NoResultFoundException;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.CommonUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.xmlutils.XMLWriter;
import com.talentPool.inbox.dataobject.InboxData;
import com.talentPool.inbox.manager.InboxManager;
import com.talentPool.masters.constants.DegreeConstants;
import com.talentPool.masters.constants.MastersConstants;
import com.talentPool.masters.dataobject.AliasData;
import com.talentPool.masters.dataobject.BranchesData;
import com.talentPool.masters.dataobject.DegreeData;
import com.talentPool.masters.dataobject.DepartmentData;
import com.talentPool.masters.dataobject.InboxFolderData;
import com.talentPool.masters.dataobject.InstituteData;
import com.talentPool.masters.dataobject.SkillCategoryData;
import com.talentPool.masters.dataobject.SkillData;
import com.talentPool.masters.dataobject.SourceData;
import com.talentPool.masters.dataobject.SourceTypeData;
import com.talentPool.masters.exception.AliasExistException;
import com.talentPool.masters.exception.MasterExistException;
import com.talentPool.notifier.TemplateConstants;
import com.talentPool.notifier.dataobject.TemplateData;
import com.talentPool.salaryStructure.manager.SalaryStructureManager;
import com.talentPool.user.UserConstants;
import com.talentPool.user.exception.SourceExistException;
import com.talentPool.user.exception.SourceOrEmployeeCodeExistException;
import com.talentPool.user.manager.ModuleSet;
import com.talentPool.user.manager.PermissionSet;

/**
 * @author shivprasad
 * 
 */
public class MastersManager {
	public boolean checkIfAliasExists(String masterType, String name, DBTransaction tran) {
		DBPreparedQuery dq = null;
		try {
			String isExistQuery = "";

			if (masterType.equals(MastersConstants.MASTER_TYPE_SKILLS)) {
				isExistQuery = "dMastersManager_CheckSkillAlias";
			}
			if (masterType.equals(MastersConstants.MASTER_TYPE_BRANCH)) {
				isExistQuery = "dMastersManager_CheckBranchAlias";
			}
			if (masterType.equals(MastersConstants.MASTER_TYPE_DEGREE_ALIASES)) {
				isExistQuery = "dMastersManager_CheckDegreeAlias";
			}
			if (masterType.equals(MastersConstants.MASTER_TYPE_INSTITUTES)) {
				isExistQuery = "dMastersManager_CheckInstituteAlias";
			}

			dq = new DBPreparedQuery(isExistQuery, tran);
			dq.setString(1, name);
			int count = dq.getIntResult();
			if (count > 0) {
				return true;
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error While Updating Skill", e);
		} finally {
			if (dq != null) {
				dq.closeOpenCursors();
			}
		}
		return false;
	}

	public void updateAliase(String masterType, String id, String[] alias, DBTransaction tran) throws AliasExistException {
		DBPreparedQuery dq = null;
		try {
			String deleteQuery = "";
			String addQuery = "";
			String isExistQuery = "";

			if (masterType.equals(MastersConstants.MASTER_TYPE_SKILLS)) {
				isExistQuery = "dMastersManager_CheckSkill";
				deleteQuery = "dMastersManager_DeleteSkillAlias";
				addQuery = "dMastersManager_AddSkillAlias";
			}
			if (masterType.equals(MastersConstants.MASTER_TYPE_BRANCH)) {
				isExistQuery = "dMastersManager_CheckBranch";
				deleteQuery = "dMastersManager_DeleteBranchAlias";
				addQuery = "dMastersManager_AddBranchAlias";
			}
			if (masterType.equals(MastersConstants.MASTER_TYPE_DEGREE_ALIASES)) {
				isExistQuery = "dMastersManager_CheckDegree";
				deleteQuery = "dMastersManager_DeleteDegreeAlias";
				addQuery = "dMastersManager_AddDegreeAlias";
			}
			if (masterType.equals(MastersConstants.MASTER_TYPE_INSTITUTES)) {
				isExistQuery = "dMastersManager_CheckInstitute";
				deleteQuery = "dMastersManager_DeleteInstituteAlias";
				addQuery = "dMastersManager_AddInstituteAlias";
			}

			dq = new DBPreparedQuery(deleteQuery, tran);
			dq.setString(1, id);
			dq.execute();
			if (alias != null) {
				for (int i = 0; i < alias.length; i++) {
					if (!Utils.isBlankOrNull(alias[i])) {
						dq = new DBPreparedQuery(isExistQuery, tran);
						dq.setString(1, alias[i]);
						int count = dq.getIntResult();
						if (count > 0) {
							throw new AliasExistException();
						}
						dq = new DBPreparedQuery(addQuery, tran);
						dq.setId(1, id);
						dq.setString(2, alias[i]);
						dq.execute();
					}
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error While Updating Skill", e);
			throw new AliasExistException();
		} finally {
			if (dq != null) {
				dq.closeOpenCursors();
			}
		}
	}

	/**
	 * Method to to add Branch to DB
	 * 
	 * @param branchName
	 * @throws SQLException
	 */
	public void addBranchToDB(String branchName, String[] aliases) throws MasterExistException, AliasExistException {
		DBPreparedQuery dq = null;
		String branchId = null;
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			if (checkIfAliasExists(MastersConstants.MASTER_TYPE_BRANCH, branchName, tran)) {
				throw new MasterExistException();
			}

			dq = new DBPreparedQuery("dMastersManager_AddBranchToDB", tran);
			dq.setString(1, branchName);
			dq.execute();

			dq = new DBPreparedQuery("dFetchLastInsertID", tran);
			branchId = dq.getIdResult();

			updateAliase(MastersConstants.MASTER_TYPE_BRANCH, branchId, aliases, tran);

			tran.commit();

		} catch (AliasExistException ae) {
			TPLogger.getLogger().error("Error While Adding branch", ae);
			try {
				tran.rollback();
			} catch (SQLException ex) {
				TPLogger.getLogger().error("Error While Roll back", ex);
			}
			throw ae;
		} catch (Exception e) {
			TPLogger.getLogger().error("Error While Adding branch", e);
			try {
				tran.rollback();
			} catch (SQLException ex) {
				TPLogger.getLogger().error("Error While Roll back", ex);
			}
			throw new MasterExistException();
		} finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}
		}
	}

	/**
	 * Method to update Branch.
	 * 
	 * @param branchName
	 * @param branchId
	 */
	public void updateBranch(String branchName, String branchId, String[] aliases) throws MasterExistException, AliasExistException {
		DBPreparedQuery dq = null;
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			if (checkIfAliasExists(MastersConstants.MASTER_TYPE_BRANCH, branchName, tran)) {
				throw new MasterExistException();
			}

			dq = new DBPreparedQuery("dMastersManager_UpdateBranch", tran);
			dq.setString(1, branchName);
			dq.setId(2, branchId);
			dq.execute();

			updateAliase(MastersConstants.MASTER_TYPE_BRANCH, branchId, aliases, tran);

			tran.commit();

		} catch (AliasExistException ae) {
			TPLogger.getLogger().error("Error While Adding branch", ae);
			try {
				tran.rollback();
			} catch (SQLException ex) {
				TPLogger.getLogger().error("Error While Roll back", ex);
			}
			throw ae;
		} catch (Exception e) {
			TPLogger.getLogger().error("Error While Updating Branch", e);
			try {
				tran.rollback();
			} catch (SQLException ex) {
				TPLogger.getLogger().error("Error While Roll back", ex);
			}
			throw new MasterExistException();
		} finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}
		}

	}

	/**
	 * Method to Delete Branch From The Database.
	 * 
	 * @param branchId
	 */
	public void deleteBranchFromDB(String branchId) throws SQLException {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dMastersManager_DeleteBranchFromDB");
			dq.setId(1, branchId);
			dq.execute();
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error While Deletiing Branch", e);
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}

	/**
	 * Method to get an Array of Branches
	 * 
	 * @return
	 */
	public ArrayList getBranchesWithAliases(String branchId) {
		DBPreparedQuery dq = null;
		ArrayList branches = null;
		try {
			String[] dynParam = new String[1];
			dynParam[0] = " ";
			if (!Utils.isBlankOrNull(branchId)) {
				dynParam[0] = " WHERE tb.branch_id= ? ";
			}
			dq = new DBPreparedQuery("dMastersManager_GetBranchesWithAliases", dynParam);
			if (!Utils.isBlankOrNull(branchId)) {
				dq.setId(1, branchId);
			}
			branches = dq.getResult();
			branches = constructBranchDataWithAliases(branches);
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error While getting  branches for Branch Master", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return branches;
	}

	public ArrayList constructBranchDataWithAliases(ArrayList source) {
		ArrayList results = new ArrayList();
		int prevId = 0;
		BranchesData pDO = null;
		for (int i = 0; i < source.size(); i++) {
			BranchesData newDO = (BranchesData) source.get(i);
			int newId = newDO.getItemId();
			if (newId != prevId) {
				if (pDO != null) {
					results.add(pDO);
				}
				pDO = newDO;
				prevId = newId;
			}

			String alias = newDO.getAlias();
			if (!Utils.isBlankOrNull(alias)) {
				AliasData aliasData = new AliasData("" + newId, alias);
				ArrayList aliases = pDO.getAliases();
				if (aliases == null) {
					aliases = new ArrayList();
				}
				aliases.add(aliasData);
				pDO.setAliases(aliases);
			}

			if (i == source.size() - 1) {
				results.add(pDO);
			}
		}
		return results;

	}

	public String getXMLforBranches(ArrayList branches) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			wr.startDocument();
			wr.startElement("rows");
			for (int i = 0; i < branches.size(); i++) {
				BranchesData data = (BranchesData) branches.get(i);

				AttributesImpl at = new AttributesImpl();
				at.addAttribute("", "id", "", "", "" + data.getItemId());
				wr.startElement("", "row", "", at);

				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "Col_I_Comment");
				wr.startElement("", "userdata", "", at);
				wr.characters("Delete");
				wr.endElement("userdata");

				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "branch");
				wr.startElement("", "userdata", "", at);
				wr.characters(data.getItemName());
				wr.endElement("userdata");

				wr.startElement("cell");
				wr.characters("<img src=\"images/ico_delete.gif\" border=0>^javascript:deleteRecord(" + data.getItemId() + ");^_self");
				wr.endElement("cell");

				wr.startElement("cell");
				wr.characters(wr.doubleEscape(data.getItemName()) + "^javascript:editRecord(" + data.getItemId() + ");^_self");
				wr.endElement("cell");

				ArrayList aliases = data.getAliases();
				StringBuffer sb = new StringBuffer();
				if (aliases != null && aliases.size() > 0) {
					for (int k = 0; k < aliases.size(); k++) {
						AliasData aliasData = (AliasData) aliases.get(k);
						sb.append(aliasData.getAlias());
						if (k < aliases.size() - 1) {
							sb.append(", ");
						}
					}
				}

				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "alias");
				wr.startElement("", "userdata", "", at);
				wr.characters(sb.toString());
				wr.endElement("userdata");

				// wr.startElement("cell");
				// wr.characters(data.getItemName());
				// wr.endElement("cell");

				wr.startElement("cell");
				wr.characters(wr.doubleEscape(sb.toString().trim()));
				wr.endElement("cell");

				wr.endElement("row");
			}
			wr.endElement("rows");
			wr.endDocument();
		} catch (SAXException e) {
			TPLogger.getLogger().error("Error while creating xml file for Branches", e);
		}
		return sWr.getBuffer().toString();
	}

	public String addInstituteToDB(String instituteName, String[] aliases) throws MasterExistException, AliasExistException {

		DBPreparedQuery dq = null;
		String instituteId = null;
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			if (checkIfAliasExists(MastersConstants.MASTER_TYPE_INSTITUTES, instituteName, tran)) {
				throw new MasterExistException();
			}

			dq = new DBPreparedQuery("dMastersManager_AddInstitute", tran);
			dq.setString(1, instituteName);
			dq.execute();

			dq = new DBPreparedQuery("dFetchLastInsertID", tran);
			instituteId = dq.getIdResult();

			updateAliase(MastersConstants.MASTER_TYPE_INSTITUTES, instituteId, aliases, tran);

			tran.commit();

		} catch (AliasExistException ae) {
			TPLogger.getLogger().error("Error While Adding institute", ae);
			try {
				tran.rollback();
			} catch (SQLException ex) {
				TPLogger.getLogger().error("Error While Roll back", ex);
			}
			throw ae;
		} catch (Exception e) {
			TPLogger.getLogger().error("Error While Adding institute", e);
			try {
				tran.rollback();
			} catch (SQLException ex) {
				TPLogger.getLogger().error("Error While Roll back", ex);
			}
			throw new MasterExistException();
		} finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}
		}
		return instituteId;
	}

	public void updateInstitute(String instituteName, String instituteId, String[] aliases) throws MasterExistException, AliasExistException {
		DBPreparedQuery dq = null;
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();

			if (checkIfAliasExists(MastersConstants.MASTER_TYPE_INSTITUTES, instituteName, tran)) {
				throw new MasterExistException();
			}

			dq = new DBPreparedQuery("dMastersManager_UpdateInstitute", tran);
			dq.setString(1, instituteName);
			dq.setId(2, instituteId);
			dq.execute();

			updateAliase(MastersConstants.MASTER_TYPE_INSTITUTES, instituteId, aliases, tran);

			tran.commit();

		} catch (AliasExistException ae) {
			TPLogger.getLogger().error("Error While Updating institute", ae);
			try {
				tran.rollback();
			} catch (SQLException ex) {
				TPLogger.getLogger().error("Error While Roll back", ex);
			}
			throw ae;
		} catch (Exception e) {
			TPLogger.getLogger().error("Error While Updating institute", e);
			try {
				tran.rollback();
			} catch (SQLException ex) {
				TPLogger.getLogger().error("Error While Roll back", ex);
			}
			throw new MasterExistException();
		} finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}
		}

	}

	public void deleteInstituteFromDB(String instituteId) throws SQLException {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dMastersManager_DeleteInstitute");
			dq.setId(1, instituteId);
			dq.execute();
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error While Deletiing Institute", e);
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}

	public ArrayList getInstitutesWithAliases(String instituteId) {
		DBPreparedQuery dq = null;
		ArrayList institutes = null;
		try {
			String[] dynParam = new String[1];
			dynParam[0] = " ";
			if (!Utils.isBlankOrNull(instituteId)) {
				dynParam[0] = " WHERE ti.institute_id=? ";
			}
			dq = new DBPreparedQuery("dMastersManager_GetInstitutesWithAliases", dynParam);
			if (!Utils.isBlankOrNull(instituteId)) {
				dq.setId(1, instituteId);
			}
			institutes = dq.getResult();
			institutes = constructInstituteDataWithAliases(institutes);
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error While getting  degrees with aliases for degree Master", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return institutes;
	}
	
	public String getInstitutesWithAliasesByName(String instituteName) {
		DBPreparedQuery dq = null;
		String institutes = null;
		try {
			dq = new DBPreparedQuery("dMastersManager_GetInstitutesWithAliasesByName");
			dq.setString(1, instituteName);
			dq.setString(2, instituteName);
			institutes = dq.getIdResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error While getting Institutes with aliases.", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return institutes;
	}

	public ArrayList constructInstituteDataWithAliases(ArrayList source) {
		ArrayList results = new ArrayList();
		int prevId = 0;
		InstituteData pDO = null;
		for (int i = 0; i < source.size(); i++) {
			InstituteData newDO = (InstituteData) source.get(i);
			int newId = newDO.getItemId();
			if (newId != prevId) {
				if (pDO != null) {
					results.add(pDO);
				}
				pDO = newDO;
				prevId = newId;
			}

			String alias = newDO.getAlias();
			if (!Utils.isBlankOrNull(alias)) {
				AliasData aliasData = new AliasData("" + newId, alias);
				ArrayList aliases = pDO.getAliases();
				if (aliases == null) {
					aliases = new ArrayList();
				}
				aliases.add(aliasData);
				pDO.setAliases(aliases);
			}

			if (i == source.size() - 1) {
				results.add(pDO);
			}
		}
		return results;

	}

	public String getXMLforInstitutes(ArrayList institutes) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			wr.startDocument();
			wr.startElement("rows");
			for (int i = 0; i < institutes.size(); i++) {
				InstituteData data = (InstituteData) institutes.get(i);

				AttributesImpl at = new AttributesImpl();
				at.addAttribute("", "id", "", "", "" + data.getItemId());
				wr.startElement("", "row", "", at);

				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "Col_I_Comment");
				wr.startElement("", "userdata", "", at);
				wr.characters("Delete");
				wr.endElement("userdata");

				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "institute");
				wr.startElement("", "userdata", "", at);
				wr.characters(data.getItemName());
				wr.endElement("userdata");

				wr.startElement("cell");
				wr.characters("<img src=\"images/ico_delete.gif\" border=0>^javascript:deleteRecord(" + data.getItemId() + ");^_self");
				wr.endElement("cell");

				wr.startElement("cell");
				wr.characters(wr.doubleEscape(data.getItemName()) + "^javascript:editRecord(" + data.getItemId() + ");^_self");
				wr.endElement("cell");

				// wr.startElement("cell");
				// wr.characters(data.getItemName());
				// wr.endElement("cell");

				ArrayList aliases = data.getAliases();
				StringBuffer sb = new StringBuffer();
				if (aliases != null && aliases.size() > 0) {
					for (int k = 0; k < aliases.size(); k++) {
						AliasData aliasData = (AliasData) aliases.get(k);
						sb.append(aliasData.getAlias());
						if (k < aliases.size() - 1) {
							sb.append(", ");
						}
					}
				}

				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "alias");
				wr.startElement("", "userdata", "", at);
				wr.characters(sb.toString());
				wr.endElement("userdata");

				wr.startElement("cell");
				wr.characters(wr.doubleEscape(sb.toString()));
				wr.endElement("cell");

				wr.endElement("row");
			}
			wr.endElement("rows");
			wr.endDocument();
		} catch (SAXException e) {
			TPLogger.getLogger().error("Error while creating xml file for institutes", e);
		}
		return sWr.getBuffer().toString();
	}

	public String getXMLForEditInstitute(InstituteData bdata) {
		return getXMLToEdit("" + bdata.getItemId(), bdata.getItemName(), bdata.getAliases());
	}

	private String getXMLToEdit(String id, String name, ArrayList aliases) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			wr.startDocument();
			wr.startElement("item");

			wr.startElement("id");
			wr.characters("" + id);
			wr.endElement("id");

			wr.startElement("name");
			wr.characters(name);
			wr.endElement("name");

			if (aliases != null) {
				for (int i = 0; i < aliases.size(); i++) {
					AliasData aliasData = (AliasData) aliases.get(i);
					wr.startElement("alias");
					wr.characters(aliasData.getAlias());
					wr.endElement("alias");
				}
			}

			wr.endElement("item");
			wr.endDocument();
		} catch (SAXException e) {
			TPLogger.getLogger().error("Error while creating xml file to edit", e);
		}
		return sWr.getBuffer().toString();

	}

	/**
	 * Method to add Degree to DB
	 * 
	 * @param degreeName
	 * @param degreeType TODO
	 * @param degreeLevel
	 * @throws SQLException
	 */

	public void addDegreeToDB(String degreeName, String[] aliases, String degreeType) throws MasterExistException, AliasExistException {
		DBPreparedQuery dq = null;
		String degreeId = null;
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			if (checkIfAliasExists(MastersConstants.MASTER_TYPE_DEGREE_ALIASES, degreeName, tran)) {
				throw new MasterExistException();
			}
			dq = new DBPreparedQuery("dMastersManager_AddDegreeToDB", tran);
			dq.setString(1, degreeName);
			dq.setString(2, degreeType);
			dq.execute();

			dq = new DBPreparedQuery("dFetchLastInsertID", tran);
			degreeId = dq.getIdResult();

			updateAliase(MastersConstants.MASTER_TYPE_DEGREE_ALIASES, degreeId, aliases, tran);
			tran.commit();
		} catch (AliasExistException ae) {
			TPLogger.getLogger().error("Error While Inserting Degree", ae);
			try {
				tran.rollback();
			} catch (SQLException ex) {
				TPLogger.getLogger().error("Error While Roll back", ex);
			}
			throw ae;
		} catch (Exception e) {
			TPLogger.getLogger().error("Error While Inserting Degree", e);
			try {
				tran.rollback();
			} catch (SQLException ex) {
				TPLogger.getLogger().error("Error While Roll back", ex);
			}
			throw new MasterExistException();
		} finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}
		}

	}

	/**
	 * Method to update Degree Title and Degree Level
	 * 
	 * @param degreeName
	 * @param degreeId
	 * @param degreeType TODO
	 * @param degreeLevel
	 */
	public void updateDegreeTitle(String degreeName, String degreeId, String[] aliases, String degreeType) throws MasterExistException, AliasExistException {
		DBPreparedQuery dq = null;
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			if (checkIfAliasExists(MastersConstants.MASTER_TYPE_DEGREE_ALIASES, degreeName, tran)) {
				throw new MasterExistException();
			}
			dq = new DBPreparedQuery("dMastersManager_UpdateDegreeTitle", tran);
			dq.setString(1, degreeName);
			dq.setString(2, degreeType);
			dq.setId(3, degreeId);
			dq.execute();

			updateAliase(MastersConstants.MASTER_TYPE_DEGREE_ALIASES, degreeId, aliases, tran);
			tran.commit();

		} catch (AliasExistException ae) {
			TPLogger.getLogger().error("Error While Updating Degree", ae);
			try {
				tran.rollback();
			} catch (SQLException ex) {
				TPLogger.getLogger().error("Error While Roll back", ex);
			}
			throw ae;
		} catch (Exception e) {
			TPLogger.getLogger().error("Error While Updating Degree", e);
			try {
				tran.rollback();
			} catch (SQLException ex) {
				TPLogger.getLogger().error("Error While Roll back", ex);
			}
			throw new MasterExistException();
		} finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}
		}
	}

	/**
	 * Method to Delete Degree Title
	 * 
	 * @param degreeId
	 */
	public void deleteDegreeTitle(String degreeId) throws SQLException {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dMastersManager_DeleteDegreeTitleFromDB");
			dq.setId(1, degreeId);
			dq.execute();
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error While Deletiing Degree title", e);
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}

	public String getXMLForEditDegree(DegreeData data) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			wr.startDocument();
			wr.startElement("item");

			wr.startElement("id");
			wr.characters("" + data.getItemId());
			wr.endElement("id");

			wr.startElement("name");
			wr.characters(data.getItemName());
			wr.endElement("name");

			ArrayList aliases = data.getAliases();
			if (aliases != null) {
				for (int i = 0; i < aliases.size(); i++) {
					AliasData aliasData = (AliasData) aliases.get(i);
					wr.startElement("alias");
					wr.characters(aliasData.getAlias());
					wr.endElement("alias");
				}
			}

			wr.endElement("item");
			wr.endDocument();
		} catch (SAXException e) {
			TPLogger.getLogger().error("Error while creating xml file to edit", e);
		}
		return sWr.getBuffer().toString();
	}

	public ArrayList getDegreesWithAliases(String degreeId) {
		DBPreparedQuery dq = null;
		ArrayList degrees = null;
		try {
			String[] dynParam = new String[1];
			dynParam[0] = " ";
			if (!Utils.isBlankOrNull(degreeId)) {
				dynParam[0] = " WHERE td.degree_id =? ";
			}
			dq = new DBPreparedQuery("dMastersManager_GetDegreesWithAliases", dynParam);
			if (!Utils.isBlankOrNull(degreeId)) {
				dq.setId(1, degreeId);
			}
			degrees = dq.getResult();
			degrees = constructDegreeDataWithAliases(degrees);
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error While getting  degrees with aliases for degree Master", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return degrees;
	}
	
	public SimpleDataObject getDegreeFromMaster(String degreeId) {
		DBPreparedQuery dq = null;
		SimpleDataObject sDObj = null;
		try {
			dq = new DBPreparedQuery("dMastersManager_GetDegreeFromMaster");
			dq.setId(1, degreeId);
			sDObj = (SimpleDataObject)dq.getSingleObjectResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error While getting Single degree from Master", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return sDObj;
	}
	

	public SimpleDataObject getBranchFromMaster(String branchId) {
		DBPreparedQuery dq = null;
		SimpleDataObject sDObj = null;
		try {
			dq = new DBPreparedQuery("dMastersManager_GetBranchFromMaster");
			dq.setId(1,branchId);
			sDObj = (SimpleDataObject)dq.getSingleObjectResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error While getting Single branch from Master", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return sDObj;
	}

	public ArrayList constructDegreeDataWithAliases(ArrayList source) {
		ArrayList results = new ArrayList();
		int prevId = 0;
		DegreeData pDO = null;
		for (int i = 0; i < source.size(); i++) {
			DegreeData newDO = (DegreeData) source.get(i);
			int newId = newDO.getItemId();
			if (newId != prevId) {
				if (pDO != null) {
					results.add(pDO);
				}
				pDO = newDO;
				prevId = newId;
			}

			String alias = newDO.getAlias();
			if (!Utils.isBlankOrNull(alias)) {
				AliasData aliasData = new AliasData("" + newId, alias);
				ArrayList aliases = pDO.getAliases();
				if (aliases == null) {
					aliases = new ArrayList();
				}
				aliases.add(aliasData);
				pDO.setAliases(aliases);
			}

			if (i == source.size() - 1) {
				results.add(pDO);
			}
		}
		return results;

	}

	public String getXMLForDegreeAliases(ArrayList source) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			wr.startDocument();
			wr.startElement("rows");
			for (int i = 0; i < source.size(); i++) {
				DegreeData data = (DegreeData) source.get(i);

				AttributesImpl at = new AttributesImpl();
				at.addAttribute("", "id", "", "", "" + data.getItemId());
				wr.startElement("", "row", "", at);

				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "Col_I_Comment");
				wr.startElement("", "userdata", "", at);
				wr.characters("Delete");
				wr.endElement("userdata");

				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "degree");
				wr.startElement("", "userdata", "", at);
				wr.characters(data.getItemName());
				wr.endElement("userdata");

				wr.startElement("cell");
				wr.characters("<img src=\"images/ico_delete.gif\" border=0>^javascript:deleteRecord(" + data.getItemId() + ");^_self");
				wr.endElement("cell");

				wr.startElement("cell");
				wr.characters(wr.doubleEscape(data.getItemName()) + "^javascript:editRecord(" + data.getItemId() + ");^_self");
				wr.endElement("cell");
				
				String degreeType="UG";
				wr.startElement("cell");
				if(data.getDegreeType()!=null){
					if((data.getDegreeType()).equals(DegreeConstants.TYPE_PG))
						degreeType="PG";
					if((data.getDegreeType()).equals(DegreeConstants.TYPE_PPG))
						degreeType="PPG";
				}
				wr.characters(degreeType);
				wr.endElement("cell");

				ArrayList aliases = data.getAliases();
				StringBuffer sb = new StringBuffer();
				if (aliases != null && aliases.size() > 0) {
					for (int k = 0; k < aliases.size(); k++) {
						AliasData aliasData = (AliasData) aliases.get(k);
						sb.append(aliasData.getAlias());
						if (k < aliases.size() - 1) {
							sb.append(", ");
						}
					}
				}

				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "alias");
				wr.startElement("", "userdata", "", at);
				wr.characters(sb.toString());
				wr.endElement("userdata");

				wr.startElement("cell");
				wr.characters(wr.doubleEscape(sb.toString()));
				wr.endElement("cell");

				wr.endElement("row");
			}
			wr.endElement("rows");
			wr.endDocument();
		} catch (SAXException e) {
			TPLogger.getLogger().error("Error while creating xml file for Branches", e);
		}
		return sWr.getBuffer().toString();
	}


	
	public String addSourceCategorytoDB(String category) throws MasterExistException {
		DBPreparedQuery dq = null;
		DBTransaction tran = null;
		String sourceCategoryId = null;
		try {
			tran = new DBTransaction();			
			dq = new DBPreparedQuery("dMastersManager_AddSourceCatToDB", tran);
			dq.setString(1, category);
			dq.execute();
			
			dq = new DBPreparedQuery("dFetchLastInsertID", tran);
			sourceCategoryId = dq.getIdResult();
			
			tran.commit();
			
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error While Inserting Source Category", e);
			throw new MasterExistException();
		} finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}
		}
		
		return sourceCategoryId;
	}
	/**
	 * Method to get Sources according to Source Type.
	 * 
	 * @param sourceTypeId
	 * @return
	 */
	public SourceTypeData getSources(String sourceTypeId) {
		DBPreparedQuery dq = null;
		SourceTypeData sData = new SourceTypeData();
		try {
			dq = new DBPreparedQuery("dMastersManager_GetSourceType");
			dq.setId(1, sourceTypeId);
			ArrayList results = dq.getResult();
			ArrayList sources = new ArrayList();
			for (int i = 0; i < results.size(); i++) {
				SimpleDataObject sDo = (SimpleDataObject) results.get(i);

				String srcTypeId = sDo.getId("sourceTypeId");
				String srcTypeName = sDo.getString("sourceTypeName");
				String systemGenerated = sDo.getString("systemGenerated");
				String srcTypeCategory = sDo.getString("sourceTypeCategory");
				String sourceBlacklisted = sDo.getString("sourceBlacklisted");

				String srcId = sDo.getId("sourceId");
				String src = sDo.getString("sourceTitle");
				String sourceCvLimit=sDo.getString("sourceCvLimit");
				sData.setItemId(srcTypeId);
				sData.setItemName(srcTypeName);
				sData.setSourceTypeCategory(srcTypeCategory);
				
				if (srcId != null) {
					SourceData sourceData = new SourceData();
					sourceData.setItemId(srcId);
					sourceData.setItemName(src);
					sourceData.setSystemGenerated(systemGenerated);
					sourceData.setSourceTypeCategory(srcTypeCategory);
					sourceData.setSourceBlacklisted(sourceBlacklisted);
					sourceData.setSourceCvLimit(sourceCvLimit);
					sources.add(sourceData);
				}
			}

			sData.setSources(sources);
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error While getting Source Type", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}

		return sData;
	}

	/**
	 * Method to add Source to Source Type
	 * 
	 * @param sourceName
	 * @param sourceTypeId
	 * @throws MasterExistException
	 */
	public String addSourcetoSourceType(SourceData data, DBTransaction tran) throws SourceExistException, Exception {
		DBPreparedQuery dq = null;
		String sourceId = null;
		boolean checkTitleExist=checkSourceTitleExist(data);
		boolean commit = false;
		try {
			if (tran == null) {
				tran = new DBTransaction();
				commit = true;
			}
			
			dq = new DBPreparedQuery("dMastersManager_AddSource", tran);
			dq.setId(1, data.getSourceTypeId());
			if(checkTitleExist){
				String modifiedSourceTitle=data.getSourceTitle()+"-"+data.getEmployeeCode();
				dq.setString(2, modifiedSourceTitle);
			}
			else{
				dq.setString(2, data.getSourceTitle());
			}
			
			dq.setString(3, data.getSourceEmail());
			dq.setString(4, data.getSourcePhone());
			dq.setString(5, data.getSourceMobile());
			dq.setString(6, data.getSendEmailToSource());
			dq.setString(7, data.getSendSMSToSource());
			dq.setString(8, data.getLockInPeriodOnImport());
			dq.setString(9, data.getEmployeeCode());
			if(Utils.isBlankOrNull(data.getSourceBlacklisted()))
				dq.setString(10, AdminConstants.SOURCE_NOT_BLACKLISTED);
			else
				dq.setString(10, data.getSourceBlacklisted());
			dq.setString(11, data.getSourceCvLimit());
			dq.execute();

			dq = new DBPreparedQuery("dFetchLastInsertID", tran);
			sourceId = dq.getIdResult();

			if (commit) {
				tran.commit();
			}
			resetSourceResources();
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error While Inserting source", e);
			if (commit) {
				tran.rollback();
			}
			throw new SourceExistException();
		} catch (Exception e) {
			if (commit) {
				tran.rollback();
			}
			throw e;
		} finally {
			if (dq != null) {
				if (commit) {
					dq.releaseTransaction(tran);
				} else {
					dq.closeOpenCursors();
				}
			}
		}
		return sourceId;
	}
	//to check whether Source Title of same name exist for Employee having same firstname and lastname
	public boolean checkSourceTitleExist(SourceData data){
		boolean result=false;
		String sourceTitle=data.getSourceTitle();
		if(!Utils.isBlankOrNull(sourceTitle)){
			String exitingSourceTitle=getExistingSource( sourceTitle) ;
			if(!Utils.isBlankOrNull(exitingSourceTitle)){
				if(exitingSourceTitle.equals(sourceTitle)){
					result=true;
				}
			}
		}
		return result;
	}
	
	
	public String  getExistingSource(String sourceTitle) {
		DBPreparedQuery dq = null;
		SimpleDataObject aData = null;
		String sourceTitleExist=null;
		try {
			dq = new DBPreparedQuery("dApplicantManager_getSourceTitleExisting");
			dq.setString(1, sourceTitle);
			aData = (SimpleDataObject) dq.getSingleObjectResult();
			sourceTitleExist = aData.getString("source_title");
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting applicant summary", e);

		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return sourceTitleExist;
	}
	/**
	 * Method to delete Source
	 * 
	 * @param sourceId
	 * @throws SQLException
	 */
	public void deleteSource(String sourceId, DBTransaction tran) throws SQLException {
		DBPreparedQuery dq = null;
		boolean commit = false;
		try {
			if (tran == null) {
				tran = new DBTransaction();
				commit = true;
			}
			dq = new DBPreparedQuery("dMastersManager_DeleteSourceFromDB", tran);
			dq.setId(1, sourceId);
			dq.execute();

			if (commit) {
				tran.commit();
			}
			resetSourceResources();
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error While Deleting Source", e);
			if (commit) {
				tran.rollback();
			}
			throw e;
		} finally {
			if (dq != null) {
				if (commit) {
					dq.releaseTransaction(tran);
				} else {
					dq.closeOpenCursors();
				}
			}
		}
	}

	/**
	 * Method to delete Source Type
	 * 
	 * @param sourceTypeId
	 */
	public void deleteSourceType(String sourceTypeId) throws SQLException {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dMastersManager_DeleteSourceTypeFromDB");
			dq.setId(1, sourceTypeId);
			dq.execute();
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error While Deletiing SourceType", e);
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}

	}

	/**
	 * Method to update Sources in Source types
	 * 
	 * @param SourceData
	 * @throws SQLException
	 * @throws SQLException
	 */
	public void updateSources(SourceData data, DBTransaction tran) throws SourceOrEmployeeCodeExistException, Exception {
		DBPreparedQuery dq = null;
		boolean commit = false;
		AdminManager adminManager = null;
		try {
			if (tran == null) {
				tran = new DBTransaction();
				commit = true;
			}
			dq = new DBPreparedQuery("dMastersManager_UpdateSource", tran);
			dq.setString(1, data.getSourceTitle());
			dq.setString(2, data.getSourceEmail());
			dq.setString(3, data.getSourcePhone());
			dq.setString(4, data.getSourceMobile());
			dq.setString(5, data.getSendEmailToSource());
			dq.setString(6, data.getSendSMSToSource());
			dq.setString(7, data.getLockInPeriodOnImport());
			dq.setString(8, data.getEmployeeCode());
			if(Utils.isBlankOrNull(data.getSourceBlacklisted()))
				dq.setString(9, AdminConstants.SOURCE_NOT_BLACKLISTED);
			else
				dq.setString(9, data.getSourceBlacklisted());
		
			dq.setString(10, data.getSourceCvLimit());
			dq.setString(11, data.getSourceId());
			dq.execute();
			
			adminManager = new AdminManager();
			if(AdminConstants.SOURCE_BLACKLISTED.equals(data.getSourceBlacklisted())){
				adminManager.changeUserStatusBySourceId(data.getSourceId(),UserConstants.DEACTIVE,tran);
			}else{
				adminManager.changeUserStatusBySourceId(data.getSourceId(),UserConstants.ACTIVE,tran);
			}

			if (commit) {
				tran.commit();
			}
			resetSourceResources();
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error While Updating Source", e);
			if (commit) {
				tran.rollback();
			}
			throw new SourceOrEmployeeCodeExistException();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error While Updating Source", e);
			if (commit) {
				tran.rollback();
			}
			throw e;
		} finally {
			if (dq != null) {
				if (commit) {
					dq.releaseTransaction(tran);
				} else {
					dq.closeOpenCursors();
				}
			}
		}
	}

	/**
	 * Method to get Source Title
	 * 
	 * @param sourceId
	 * @return
	 */
	public SourceData getSource(String sourceId) {
		DBPreparedQuery dq = null;
		SourceData sdata = null;
		try {
			dq = new DBPreparedQuery("dMastersManager_GetSource");
			dq.setId(1, sourceId);
			sdata = (SourceData) dq.getSingleObjectResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error While getting Source", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return sdata;
	}

	/**
	 * Method to update Source Type according to sourcetype Td.
	 * 
	 * @param category
	 * @param sourceTypeId
	 * @throws MasterExistException
	 */
	public void updateSourceType(String category, String sourceTypeId) throws MasterExistException {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dMastersManager_UpdateSourceType");
			dq.setString(1, category);
			dq.setString(2, sourceTypeId);
			dq.execute();
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error While Updating Source Type", e);
			throw new MasterExistException();
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}
	
	

	
	/**
	 * Method to get Source Type according to SourceId
	 * 
	 * @param sourceTypeId
	 * @return
	 */
	public SourceTypeData getSourceType(String sourceTypeId) {
		DBPreparedQuery dq = null;
		SourceTypeData sdata = null;
		try {
			dq = new DBPreparedQuery("dMastersManager_GetSourceCategory");
			dq.setId(1, sourceTypeId);
			sdata = (SourceTypeData) dq.getSingleObjectResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error While getting Source Title", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return sdata;

	}
	
	public SourceTypeData getSourceTypeByName(String sourceType) {
		DBPreparedQuery dq = null;
		SourceTypeData sdata = null;
		try {
			dq = new DBPreparedQuery("dMastersManager_GetSourceCategoryByName");
			dq.setString(1, sourceType);
			sdata = (SourceTypeData) dq.getSingleObjectResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error While getting Source Title", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return sdata;

	}

	/**
	 * Method to get Source Type and Sources
	 * 
	 * @return
	 */
	public ArrayList<SourceTypeData> getSourceTypes() {
		DBPreparedQuery dq = null;
		ArrayList<SourceTypeData> sourceTypes = new ArrayList<SourceTypeData>();
		try {
			dq = new DBPreparedQuery("dMastersManager_GetSourceTypes");
			ArrayList results = dq.getResult();
			if (results != null) {
				SourceTypeData sourceTypedata = null;
				String prevsourcetypeId = "";
				for (int i = 0; i < results.size(); i++) {
					SimpleDataObject sDo = (SimpleDataObject) results.get(i);

					String srcTypeId = sDo.getString("sourceTypeId");
					String srcType = sDo.getString("sourceTypeName");
					String srcTitle = sDo.getString("sourceTitle");
					String systemGenerated = sDo.getString("systemGenerated");
					
					if (!prevsourcetypeId.equals(srcTypeId)) {
						if (sourceTypedata != null) {
							sourceTypes.add(sourceTypedata);
						}
						sourceTypedata = new SourceTypeData();
						sourceTypedata.setItemId(srcTypeId);
						sourceTypedata.setItemName(srcType);
						sourceTypedata.setSystemGenerated(systemGenerated);
						
						sourceTypedata.setSources(new ArrayList());
					}
					if (!Utils.isBlankOrNull(srcTitle)) {
						ArrayList sources = sourceTypedata.getSources();
						SourceData sData = new SourceData();
						sData.setItemName(srcTitle);
						sources.add(sData);
						sourceTypedata.setSources(sources);
					}
					prevsourcetypeId = srcTypeId;
				}
				if (sourceTypedata != null) {
					sourceTypes.add(sourceTypedata);
				}

			}

		} catch (SQLException e) {
			TPLogger.getLogger().error("Error While getting Source Types", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return sourceTypes;

	}

	public String getSourceTypesInXML(List sources) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);

		try {
			wr.startDocument();
			wr.startElement("rows");
			for (int i = 0; i < sources.size(); i++) {
				SourceTypeData data = (SourceTypeData) sources.get(i);

				AttributesImpl at = new AttributesImpl();
				at.addAttribute("", "id", "", "", "" + data.getItemId());
				wr.startElement("", "row", "", at);

				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "Col_I_Comment");
				wr.startElement("", "userdata", "", at);
				wr.characters("Delete");
				wr.endElement("userdata");

				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "sourceType");
				wr.startElement("", "userdata", "", at);
				wr.characters(data.getItemName());
				wr.endElement("userdata");

				wr.startElement("cell");
				if (data.getSystemGenerated().equals(AdminConstants.SYSTEM_GENERATED)) {
					wr.characters("");
				} else {
					wr.characters("<img src=\"images/ico_delete.gif\" border=0>^javascript:deleteRecord(" + data.getItemId() + ");^_self");
				}
				wr.endElement("cell");

				wr.startElement("cell");
				wr.characters(wr.doubleEscape(data.getItemName()) + "^javascript:editRecord(" + data.getItemId() + ");^_self");
				wr.endElement("cell");

				List sourceTypes = data.getSources();
				StringBuffer commaSeptStr = new StringBuffer();
				if(sourceTypes.size()<=3){
					for (int j = 0; j < sourceTypes.size(); j++) {
						SourceData sData = (SourceData) sourceTypes.get(j);
						if (commaSeptStr.length() > 0) {
							commaSeptStr.append(", ");
						}
						commaSeptStr.append(sData.getItemName());
					}
				}
				else{
					commaSeptStr.append(((SourceData)sourceTypes.get(0)).getItemName()+" (more >>)");
				}

				wr.startElement("cell");
				String skillsList = commaSeptStr.toString();
				if (Utils.isBlankOrNull(skillsList)) {
					skillsList = "NA";
				}
				wr.characters(wr.doubleEscape(skillsList) + "^javascript:manageSources(" + data.getItemId() + ");^_self");
				wr.endElement("cell");

				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "skills");
				wr.startElement("", "userdata", "", at);
				wr.characters(skillsList);
				wr.endElement("userdata");
				
				wr.endElement("row");
			}
			wr.endElement("rows");
			wr.endDocument();
		} catch (SAXException e) {
			TPLogger.getLogger().error("error while creating xml file for emails", e);
		}
		return sWr.getBuffer().toString();
	}

	public String getSourcesInXML(List sources) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			wr.startDocument();
			wr.startElement("rows");
			for (int i = 0; i < sources.size(); i++) {
				SourceData data = (SourceData) sources.get(i);
				
				AttributesImpl at = new AttributesImpl();
				at.addAttribute("", "id", "", "", "" + data.getItemId());
				wr.startElement("", "row", "", at);

				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "Col_I_Comment");
				wr.startElement("", "userdata", "", at);
				wr.characters("Delete");
				wr.endElement("userdata");

				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "source");
				wr.startElement("", "userdata", "", at);
				wr.characters(data.getItemName());
				wr.endElement("userdata");

				wr.startElement("cell");
//				if (data.getSystemGenerated().equals(AdminConstants.SYSTEM_GENERATED) || data.getSourceTypeCategory().equals(AdminConstants.SOURCE_CATEGORY_EMPLOYEE_REFERAL)) {
//					wr.characters("<img src=\"images/blank.gif\" border=0>");
//				} else {
					wr.characters("<img src=\"images/ico_delete.gif\" border=0>^javascript:deleteRecord(" + data.getItemId() + ");^_self");
//				}
				wr.endElement("cell");
				
				wr.startElement("cell");
				wr.characters(wr.doubleEscape(data.getItemName()) + "^javascript:editRecord(" + data.getItemId() + ");^_self");
				wr.endElement("cell");
				
				if(AdminConstants.SOURCE_BLACKLISTED.equals(data.getSourceBlacklisted())){
					wr.startElement("cell");
					wr.characters(wr.doubleEscape(TPLabels.getLabel("admin_master_source.label.blacklisted")));
					wr.endElement("cell");
				}

				wr.endElement("row");
			}
			wr.endElement("rows");
			wr.endDocument();
		} catch (SAXException e) {
			TPLogger.getLogger().error("error while creating xml file for emails", e);
		}
		return sWr.getBuffer().toString();
	}

	/**
	 * Method to add Skill Category to DB
	 * 
	 * @param skillCategory
	 * @throws SQLException
	 */
	public String addSkillCategorytoDB(String skillCategory) throws MasterExistException {
		DBPreparedQuery dq = null;
		String skillCategoryId= null;
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			dq = new DBPreparedQuery("dMastersManager_AddSkillCatToDB", tran);
			dq.setString(1, skillCategory);
			dq.execute();
			
			dq = new DBPreparedQuery("dFetchLastInsertID", tran);
			skillCategoryId = dq.getIdResult();
			
			tran.commit();
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error While Inserting Skill Category", e);
			throw new MasterExistException();
		} finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}
		}
		return skillCategoryId;
	}

	/**
	 * Method to delete Skill category from DB
	 * 
	 * @param skillCategoryId
	 */
	public void deleteSkillCategory(String skillCategoryId) throws SQLException {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dMastersManager_DeleteSkillCategoryFromDB");
			dq.setId(1, skillCategoryId);
			dq.execute();
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error While Deletiing Skill Category", e);
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}

	/**
	 * Method to update Skill Category
	 * 
	 * @param skillCategory
	 * @param skillCategoryId
	 * @throws MasterExistException
	 */
	public void updateSkillCategory(String skillCategory, String skillCategoryId) throws MasterExistException {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dMastersManager_UpdateSkillCategory");
			dq.setString(1, skillCategory);
			dq.setString(2, skillCategoryId);
			dq.execute();
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error While Updating Skill Category", e);
			throw new MasterExistException();
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}

	/**
	 * Method to retrieve Skill Category
	 * 
	 * @param skillCategoryId
	 * @return
	 */
	public SkillCategoryData getSkillCategory(String skillCategoryId) {
		DBPreparedQuery dq = null;
		SkillCategoryData sdata = null;
		try {
			dq = new DBPreparedQuery("dMastersManager_GetSkillCategory");
			dq.setId(1, skillCategoryId);
			sdata = (SkillCategoryData) dq.getSingleObjectResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error While getting Skill Category", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return sdata;
	}
	
	public SkillCategoryData getSkillCategoryByName(String skillCategory) {
		DBPreparedQuery dq = null;
		SkillCategoryData sdata = null;
		try {
			dq = new DBPreparedQuery("dMastersManager_GetSkillCategoryByName");
			dq.setString(1, skillCategory);
			sdata = (SkillCategoryData) dq.getSingleObjectResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error While getting Skill Category", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return sdata;
	}

	public String getXMLForEditSkillCategory(SkillCategoryData skillCategoryData) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			wr.startDocument();
			wr.startElement("skillcategory");

			wr.startElement("id");
			wr.characters("" + skillCategoryData.getItemId());
			wr.endElement("id");

			wr.startElement("name");
			wr.characters(skillCategoryData.getItemName());
			wr.endElement("name");

			wr.endElement("skillcategory");
			wr.endDocument();
		} catch (SAXException e) {
			TPLogger.getLogger().error("Error while creating xml file for Department to edit", e);
		}
		return sWr.getBuffer().toString();
	}

	/**
	 * Method to retrieve Skills according to Skill Category
	 * 
	 * @param skillCategoryId
	 * @return
	 */
	public SkillCategoryData getSkills(String skillCategoryId) {
		DBPreparedQuery dq = null;
		SkillCategoryData scatData = new SkillCategoryData();
		try {
			dq = new DBPreparedQuery("dMastersManager_GetSkillCat");
			dq.setId(1, skillCategoryId);
			ArrayList results = dq.getResult();

			ArrayList<SkillData> skillsArray = new ArrayList<SkillData>();
			String prevSkillId = "";
			SkillData pSkillData = null;

			for (int i = 0; i < results.size(); i++) {
				SimpleDataObject sDo = (SimpleDataObject) results.get(i);

				if (i == 0) {
					String skillCatId = sDo.getId("skillCatId");
					String skillCatDesc = sDo.getString("skillCatDesc");
					scatData.setItemId(skillCatId);
					scatData.setItemName(skillCatDesc);
				}

				String skillId = sDo.getId("skillId");
				String skillName = sDo.getString("skill");
				String skillAlias = sDo.getString("skillAlias");
				ArrayList aliases = null;
				if (skillId != null) {
					if (skillId.equals(prevSkillId)) {
						aliases = pSkillData.getAliases();
					} else {
						if (pSkillData != null) {
							skillsArray.add(pSkillData);
						}
						pSkillData = new SkillData();
						pSkillData.setItemId(skillId);
						pSkillData.setItemName(skillName);
						aliases = new ArrayList();
						prevSkillId = skillId;
					}
					if (!Utils.isBlankOrNull(skillAlias)) {
						AliasData aliasData = new AliasData(skillId, skillAlias);
						aliases.add(aliasData);
					}
					pSkillData.setAliases(aliases);

					if (i == results.size() - 1) {
						skillsArray.add(pSkillData);
					}
				}
			}

			scatData.setSkills(skillsArray);
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error While getting Skill", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return scatData;
	}

	public String getXMLForSkill(ArrayList list) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			wr.startDocument();
			wr.startElement("rows");
			for (int i = 0; i < list.size(); i++) {
				SkillData data = (SkillData) list.get(i);

				AttributesImpl at = new AttributesImpl();
				at.addAttribute("", "id", "", "", "" + data.getItemId());
				wr.startElement("", "row", "", at);

				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "Col_I_Comment");
				wr.startElement("", "userdata", "", at);
				wr.characters("Delete");
				wr.endElement("userdata");

				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "skill");
				wr.startElement("", "userdata", "", at);
				wr.characters(data.getItemName());
				wr.endElement("userdata");

				wr.startElement("cell");
				wr.characters("<img src=\"images/ico_delete.gif\" border=0>^javascript:deleteRecord(" + data.getItemId() + ");^_self");
				wr.endElement("cell");

				wr.startElement("cell");
				wr.characters(wr.doubleEscape(data.getItemName()) + "^javascript:editRecord(" + data.getItemId() + ");^_self");
				wr.endElement("cell");

				// get skills for categories
				ArrayList aliases = data.getAliases();
				StringBuffer commaSeptStr = new StringBuffer();
				if (aliases != null) {
					for (int k = 0; k < aliases.size(); k++) {
						AliasData sData = (AliasData) aliases.get(k);
						commaSeptStr.append(sData.getAlias());
						if (k < aliases.size() - 1) {
							commaSeptStr.append(", ");
						}
					}
				}

				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "alias");
				wr.startElement("", "userdata", "", at);
				wr.characters(commaSeptStr.toString());
				wr.endElement("userdata");

				wr.startElement("cell");
				wr.characters(wr.doubleEscape(commaSeptStr.toString()));
				wr.endElement("cell");

				wr.endElement("row");
			}
			wr.endElement("rows");
			wr.endDocument();
		} catch (SAXException e) {
			TPLogger.getLogger().error("Error while creating xml file for skills", e);
		}
		return sWr.getBuffer().toString();
	}

	/**
	 * Method to Delete Skill
	 * 
	 * @param skillId
	 * @throws SQLException
	 */
	public void deleteSkill(String skillId) throws SQLException {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dMastersManager_DeleteSkillFromDB");
			dq.setId(1, skillId);
			dq.execute();
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error While Deletiing Skill", e);
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}

	/**
	 * This method will add Skill to a Skill Category
	 * 
	 * @param skillName
	 * @param skillCategoryId
	 * @throws SQLException
	 */
	public void addSkilltoSkillCategory(String skillName, String skillCategoryId, String[] skillAlias) throws MasterExistException, AliasExistException {
		DBPreparedQuery dq = null;
		String skillId = null;
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			if (checkIfAliasExists(MastersConstants.MASTER_TYPE_SKILLS, skillName, tran)) {
				throw new MasterExistException();
			}
			dq = new DBPreparedQuery("dMastersManager_AddSkill", tran);
			dq.setString(1, skillName);
			dq.setId(2, skillCategoryId);
			dq.execute();

			dq = new DBPreparedQuery("dFetchLastInsertID", tran);
			skillId = dq.getIdResult();
			updateAliase(MastersConstants.MASTER_TYPE_SKILLS, skillId, skillAlias, tran);

			tran.commit();

		} catch (AliasExistException ae) {
			TPLogger.getLogger().error("Error While Adding skill", ae);
			try {
				tran.rollback();
			} catch (SQLException ex) {
				TPLogger.getLogger().error("Error While Roll back", ex);
			}
			throw ae;
		} catch (Exception e) {
			TPLogger.getLogger().error("Error While Adding skill", e);
			try {
				tran.rollback();
			} catch (SQLException ex) {
				TPLogger.getLogger().error("Error While Roll back", ex);
			}
			throw new MasterExistException();
		} finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}
		}
	}

	/**
	 * Method to update Skill
	 * 
	 * @param skillName
	 * @param skillId
	 * @throws SQLException
	 */
	public void updateSkill(String skillName, String skillId, String[] skillAlias) throws MasterExistException, AliasExistException {
		DBPreparedQuery dq = null;
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			if (checkIfAliasExists(MastersConstants.MASTER_TYPE_SKILLS, skillName, tran)) {
				throw new MasterExistException();
			}
			dq = new DBPreparedQuery("dMastersManager_UpdateSkill", tran);
			dq.setString(1, skillName);
			dq.setString(2, skillId);
			dq.execute();

			updateAliase(MastersConstants.MASTER_TYPE_SKILLS, skillId, skillAlias, tran);
			tran.commit();

		} catch (AliasExistException ae) {
			TPLogger.getLogger().error("Error While Updating skill", ae);
			try {
				tran.rollback();
			} catch (SQLException ex) {
				TPLogger.getLogger().error("Error While Roll back", ex);
			}
			throw ae;
		} catch (Exception e) {
			TPLogger.getLogger().error("Error While Updating skill", e);
			try {
				tran.rollback();
			} catch (SQLException ex) {
				TPLogger.getLogger().error("Error While Roll back", ex);
			}
			throw new MasterExistException();
		} finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}
		}
	}

	/**
	 * Method to retrieve Skill
	 * 
	 * @param skillId
	 * @return
	 */
	public SkillData getSkill(String skillId) {
		DBPreparedQuery dq = null;
		SkillData sdata = new SkillData();
		try {
			dq = new DBPreparedQuery("dMastersManager_GetSkill");
			dq.setId(1, skillId);
			ArrayList results = dq.getResult();
			ArrayList<AliasData> skillAliasArray = new ArrayList<AliasData>();

			for (int i = 0; i < results.size(); i++) {
				SimpleDataObject sDo = (SimpleDataObject) results.get(i);

				String SkillId = sDo.getId("skillId");
				String SkillName = sDo.getString("skillName");
				String SkillAlias = sDo.getString("skillAlias");
				if (i == 0) {
					sdata.setItemId(SkillId);
					sdata.setItemName(SkillName);
				}
				if (!Utils.isBlankOrNull(SkillAlias)) {
					AliasData alias = new AliasData(SkillId, SkillAlias);
					skillAliasArray.add(alias);
				}

				sdata.setAliases(skillAliasArray);
			}
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error While getting Skill and alias", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return sdata;
	}

	/**
	 * Method to get Skill Categories and Skills.
	 * 
	 * @return
	 */
	public ArrayList<SkillCategoryData> getSkillCategories() {
		DBPreparedQuery dq = null;
		ArrayList<SkillCategoryData> skillCats = new ArrayList<SkillCategoryData>();

		try {
			dq = new DBPreparedQuery("dMastersManager_GetSkillCategories");
			ArrayList results = dq.getResult();
			if (results != null) {
				SkillCategoryData skillcatdata = null;
				String prevSkillCatId = "";
				for (int i = 0; i < results.size(); i++) {
					SimpleDataObject sDo = (SimpleDataObject) results.get(i);

					String skillCatId = sDo.getString("skillCategoryId");
					String skillCatDesc = sDo.getString("skillCatDesc");
					String skill = sDo.getString("skill");

					if (!prevSkillCatId.equals(skillCatId)) {
						if (skillcatdata != null) {
							skillCats.add(skillcatdata);
						}
						skillcatdata = new SkillCategoryData();
						skillcatdata.setItemId(skillCatId);
						skillcatdata.setItemName(skillCatDesc);
						skillcatdata.setSkills(new ArrayList());
					}
					if (!Utils.isBlankOrNull(skill)) {
						ArrayList skills = skillcatdata.getSkills();
						SkillData skilldata = new SkillData();
						skilldata.setItemName(skill);
						skills.add(skilldata);
						skillcatdata.setSkills(skills);
					}
					prevSkillCatId = skillCatId;
				}
				if (skillcatdata != null) {
					skillCats.add(skillcatdata);
				}
			}
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error While getting Skill Categories", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}

		return skillCats;
	}

	public String getXMLForSkillCategoies(ArrayList list) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			wr.startDocument();
			wr.startElement("rows");
			for (int i = 0; i < list.size(); i++) {
				SkillCategoryData data = (SkillCategoryData) list.get(i);

				AttributesImpl at = new AttributesImpl();
				at.addAttribute("", "id", "", "", "" + data.getItemId());
				wr.startElement("", "row", "", at);

				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "Col_I_Comment");
				wr.startElement("", "userdata", "", at);
				wr.characters("Delete");
				wr.endElement("userdata");

				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "skillCategory");
				wr.startElement("", "userdata", "", at);
				wr.characters(data.getItemName());
				wr.endElement("userdata");

				wr.startElement("cell");
				wr.characters("<img src=\"images/ico_delete.gif\" border=0>^javascript:deleteRecord(" + data.getItemId() + ");^_self");
				wr.endElement("cell");

				wr.startElement("cell");
				wr.characters(wr.doubleEscape(data.getItemName()) + "^javascript:editRecord(" + data.getItemId() + ");^_self");
				wr.endElement("cell");

				// get skills for categories
				ArrayList skills = data.getSkills();
				StringBuffer commaSeptStr = new StringBuffer();
				if (skills != null) {
					for (int k = 0; k < skills.size(); k++) {
						SkillData sData = (SkillData) skills.get(k);
						commaSeptStr.append(sData.getItemName());
						if (k < skills.size() - 1) {
							commaSeptStr.append(", ");
						}
					}
				}
				wr.startElement("cell");
				String skillsList = commaSeptStr.toString();
				if (Utils.isBlankOrNull(skillsList)) {
					skillsList = "NA";
				}
				wr.characters(wr.doubleEscape(skillsList) + "^javascript:manageSkills(" + data.getItemId() + ");^_self");
				wr.endElement("cell");

				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "skills");
				wr.startElement("", "userdata", "", at);
				wr.characters(skillsList);
				wr.endElement("userdata");

				wr.endElement("row");
			}
			wr.endElement("rows");
			wr.endDocument();
		} catch (SAXException e) {
			TPLogger.getLogger().error("Error while creating xml file for Departments", e);
		}
		return sWr.getBuffer().toString();
	}

	/**
	 * Method to add Dept to DB
	 * 
	 * @param departmentMaster
	 * @throws SQLException
	 */
	public void addDeptToDB(String departmentName, String parentDepartmentId) throws Exception {
		DBPreparedQuery dq = null;
		try {
			String duplicateDept=getDuplicateDeptName(departmentName,parentDepartmentId);
			if(!Utils.isBlankOrNull(duplicateDept)){
				throw new MasterExistException();
			}
			else{
			dq = new DBPreparedQuery("dMastersManager_AddDepToDB");
			dq.setString(1, departmentName);
			if (parentDepartmentId.equals("0")) {
				parentDepartmentId = null;
			}
			dq.setString(2, parentDepartmentId);
			dq.execute();
			}
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error While Inserting Dept", e);
			throw new MasterExistException();
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}
// a function to find out existing department under this parent 
	public String getDuplicateDeptName(String departmentName,String parentDepartmentId){
		String duplicateDeptName = "";
		DBPreparedQuery dq = null;
		try {
			if (parentDepartmentId.equals("0")) {
				parentDepartmentId = null;
				dq = new DBPreparedQuery("dMastersManager_GetDepFromDBForNullParent");
				dq.setString(1, departmentName);
			}
			else{
				dq = new DBPreparedQuery("dMastersManager_GetDepFromDB");
				dq.setString(1, departmentName);
				dq.setString(2, parentDepartmentId);
			}
			
			duplicateDeptName = dq.getIdResult();			
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return duplicateDeptName;
	}
	//a function to compare if this department already exist under de
	/**
	 * Method to delete Dept from DB
	 * 
	 * @param deptId
	 * @throws SQLException
	 */
	public void deleteDeptFromDB(String deptId) throws SQLException {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dMastersManager_DeleteDepFromDB");
			dq.setId(1, deptId);
			dq.execute();
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error While Deletiing Dept", e);
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}

	/**
	 * Method to get Dept Name
	 * 
	 * @param deptId
	 * @return
	 */
	public DepartmentData getDeptData(String deptId) {
		DBPreparedQuery dq = null;
		DepartmentData adata = null;
		try {
			dq = new DBPreparedQuery("dMastersManager_GetDeptData");
			dq.setId(1, deptId);
			adata = (DepartmentData) dq.getSingleObjectResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error While getting  Dept name", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return adata;
	}
	
	public DepartmentData getDeptDataByTitle(String deptTitle) {
		DBPreparedQuery dq = null;
		DepartmentData adata = null;
		try {
			dq = new DBPreparedQuery("dMastersManager_GetDeptDataByTitle");
			dq.setString(1, deptTitle);
			adata = (DepartmentData) dq.getSingleObjectResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error While getting  Dept name", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return adata;
	}

	/**
	 * Method to update Dept
	 * 
	 * @param deptName
	 * @param deptId
	 * @throws SQLException
	 */
	public void updateDept(String deptName, String deptId) throws MasterExistException {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dMastersManager_UpdateDept");
			dq.setString(1, deptName);
			dq.setId(2, deptId);
			dq.execute();
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error While Updating Dept", e);
			throw new MasterExistException();
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}

	/**
	 * Method to get Dept Names
	 * @return
	 * 		List of all department arranged in their hierarchy
	 * @author 
	 * 		Last modified on 21st Mar'2011 By Praveen
	 */
	public ArrayList<DepartmentData> getDepartments() {
		ArrayList<DepartmentData> departments = null;
		departments = getDepartmentsList(0, MastersConstants.DEPARTMENT_LEVEL_1);
		for(DepartmentData department1:departments){
			ArrayList<DepartmentData> departments2 =getDepartmentsList(department1.getItemId(), MastersConstants.DEPARTMENT_LEVEL_2);
			department1.setChildrens(departments2);
			for(DepartmentData department2:departments2){
				ArrayList<DepartmentData> departments3 =getDepartmentsList(department2.getItemId(), MastersConstants.DEPARTMENT_LEVEL_3);
				department2.setChildrens(departments3);
				for(DepartmentData department3:departments3){
					ArrayList<DepartmentData> departments4 =getDepartmentsList(department3.getItemId(), MastersConstants.DEPARTMENT_LEVEL_4);
					department3.setChildrens(departments4);
					for(DepartmentData department4:departments4){
						ArrayList<DepartmentData> departments5 =getDepartmentsList(department4.getItemId(), MastersConstants.DEPARTMENT_LEVEL_5);
						department4.setChildrens(departments5);
					}
				}
			}
		}
		return departments;
	}
	
	public ArrayList<DepartmentData> getDepartmentsList(int parentId,String deptLevel) {
		DBPreparedQuery dq = null;
		ArrayList<DepartmentData> departments = null;
		try {
			String[] dynParam = new String[1];
			if(parentId==0){
				dynParam[0] = " dept_parent_id IS NULL ";
			}else {
				dynParam[0] = " dept_parent_id=?";
			}
			dq = new DBPreparedQuery("dMastersManager_GetDeptNames", dynParam);
			dq.setString(1, deptLevel);
			if(parentId!=0)
				dq.setInt(2, parentId);
			departments = dq.getResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error While getting  Dept", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return departments;
	}

	
	public String getXMLForDepartments(ArrayList<DepartmentData> list) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		
		try {
			wr.startDocument();
			wr.startElement("rows");

			InboxManager inboxManager = new InboxManager();
			InboxData inboxData = inboxManager.getCurrentInboxSettings();

			String companyName = "Organization";
			if (inboxData != null && !Utils.isBlankOrNull(inboxData.getInboxDisplayName())) {
				companyName = inboxData.getInboxDisplayName();
			}

			AttributesImpl at = new AttributesImpl();
			at.addAttribute("", "id", "", "", "0");
			wr.startElement("", "row", "", at);

			at = new AttributesImpl();
			at.addAttribute("", "name", "", "", "deptName");
			wr.startElement("", "userdata", "", at);
			wr.characters(companyName);
			wr.endElement("userdata");
			at = new AttributesImpl();
			at.addAttribute("", "name", "", "", "level");
			wr.startElement("", "userdata", "", at);
			wr.characters("0");
			wr.endElement("userdata");

			String link = "<img src=\"images/ico_folder.gif\" border=0 style=\"margin-bottom:-2px;\">&nbsp;&nbsp;" + wr.doubleEscape(companyName);
			wr.startElement("cell");
			wr.characters(link);
			wr.endElement("cell");

			wr.startElement("cell");
			wr.characters("&nbsp;");
			wr.endElement("cell");
			wr.endElement("row");
			
			buildDepartmentXml(list,wr,"&nbsp;&nbsp;&nbsp;&nbsp;");
		
			wr.endElement("rows");
			wr.endDocument();
		} catch (SAXException e) {
			TPLogger.getLogger().error("Error while creating xml file for Departments", e);
		}

		return sWr.getBuffer().toString();
	}
	
	private void buildDepartmentXml(ArrayList<DepartmentData> departmentList,final XMLWriter wr,String spaces) throws SAXException {
		for(DepartmentData department:departmentList){
			getXMLForDepartments(department, wr, spaces);
			if(department.getChildrens()!=null){
				buildDepartmentXml(department.getChildrens(), wr, spaces+"&nbsp;&nbsp;&nbsp;&nbsp;");	
			}
		}
	}

	private void getXMLForDepartments(DepartmentData data,final XMLWriter wr,String spaces) throws SAXException {
		String link = null;
		AttributesImpl at = new AttributesImpl();
		at = new AttributesImpl();
		at.addAttribute("", "id", "", "", "" + data.getItemId());
		wr.startElement("", "row", "", at);

		at = new AttributesImpl();
		at.addAttribute("", "name", "", "", "deptName");
		wr.startElement("", "userdata", "", at);
		wr.characters(data.getItemName());
		wr.endElement("userdata");
		at = new AttributesImpl();
		at.addAttribute("", "name", "", "", "level");
		wr.startElement("", "userdata", "", at);
		wr.characters(data.getDepartmentLevel());
		wr.endElement("userdata");

		link = spaces;
		if(!getLastDepartmentLevel().equals(data.getDepartmentLevel())){
			link +="<img src=\"images/ico_folder.gif\" border=0 style=\"margin-bottom:-2px;\">";	
		}else{
			link +="&nbsp;&nbsp;";
		}
		link+="&nbsp;&nbsp;<a href=\"#\" onclick=\"editRecord(" + data.getItemId() + ");\">" + wr.doubleEscape(data.getItemName()) + "</a>";
		wr.startElement("cell");
		wr.characters(link);
		wr.endElement("cell");

		wr.startElement("cell");
		link = "<a href=\"#\" onclick=\"deleteRecord(" + data.getItemId() + ");\"><img src=\"images/ico_delete.gif\" border=0></a>";
		wr.characters(link);
		wr.endElement("cell");

		wr.endElement("row");
	}
	
	private String getLastDepartmentLevel(){
		return MastersConstants.DEPARTMENT_LEVEL_5;
	}
	
	public String getXMLForTemplates(String userId, int userRoleId, PermissionSet permissionSet) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		List templates = getTemplates(userId, userRoleId, permissionSet);
		try {
			if (templates != null && templates.size() > 0) {
				// remove templates related to modules not available
				int tCount = 0;
				while (tCount < templates.size()) {
					SimpleDataObject template = (SimpleDataObject) templates.get(tCount);
					String templateTypeId = template.getString("templateTypeId");
					boolean templateAvailable = true;
					if (!ModuleSet.isMODULE_SMS()) {
						if (templateTypeId.equals(TemplateConstants.TEMPLATE_TYPE_SMS_REMINDER_APPLICANT) || templateTypeId.equals(TemplateConstants.TEMPLATE_TYPE_SMS_REMINDER_INTERVIEWER) || templateTypeId.equals(TemplateConstants.TEMPLATE_TYPE_SMS_REMINDER_OWNER)) {
							templates.remove(tCount);
							templateAvailable = false;
						}
					}
					if (!ModuleSet.isMODULE_AUTO_RESPONSE_EMAIL()) {
						if (templateTypeId.equals(TemplateConstants.TEMPLATE_TYPE_AUTO_REPLY_EMAIL_TEMPLATE)) {
							templates.remove(tCount);
							templateAvailable = false;
						}
					}
					if (!ModuleSet.isMODULE_OUTLOOK_MEETING_REQUEST()) {
						if (templateTypeId.equals(TemplateConstants.TEMPLATE_TYPE_NEW_APPOINTMENT_NOTIFICATION) || templateTypeId.equals(TemplateConstants.TEMPLATE_TYPE_MODIFIED_APPOINTMENT_NOTIFICATION) || templateTypeId.equals(TemplateConstants.TEMPLATE_TYPE_CANCELLED_APPOINTMENT_NOTIFICATION)) {
							templates.remove(tCount);
							templateAvailable = false;
						}
					}
					if (!ModuleSet.isMODULE_VENDOR()) {
						if (templateTypeId.equals(TemplateConstants.TEMPLATE_TYPE_DUPLICATE_UPLOAD_TRIED_BY_VENDOR_NOTIFICATION)) {
							templates.remove(tCount);
							templateAvailable = false;
						}
					}
					if (!ModuleSet.isMODULE_REQUISITION()) {
						if (templateTypeId.equals(TemplateConstants.TEMPLATE_TYPE_REQUISITION_APPROVAL_NOTIFICATION)) {
							templates.remove(tCount);
							templateAvailable = false;
						}
					}
					if (templateAvailable) {
						tCount++;
					}
				}

				wr.startDocument();
				wr.startElement("rows");
				for (int i = 0; i < templates.size(); i++) {
					SimpleDataObject template = (SimpleDataObject) templates.get(i);

					AttributesImpl at = new AttributesImpl();
					at.addAttribute("", "id", "", "", "" + template.getString("templateId"));
					wr.startElement("", "row", "", at);

					at = new AttributesImpl();
					at.addAttribute("", "name", "", "", "templateName");
					wr.startElement("", "userdata", "", at);
					wr.characters(template.getString("templateName"));
					wr.endElement("userdata");

					at = new AttributesImpl();
					at.addAttribute("", "name", "", "", "Col_I_Comment");
					wr.startElement("", "userdata", "", at);
					if (!TemplateConstants.TEMPLATE_SYSTEM.equalsIgnoreCase(template.getString("templatePrivate")) && userId.equalsIgnoreCase(template.getString("userId"))) {
						wr.characters("Delete");
					} else {
						wr.characters("");
					}
					wr.endElement("userdata");

					wr.startElement("cell");
					if (!TemplateConstants.TEMPLATE_SYSTEM.equalsIgnoreCase(template.getString("templatePrivate")) && userId.equalsIgnoreCase(template.getString("userId"))) {
						wr.characters("<img src=\"images/ico_delete.gif\" border=0 onclick=\"javascript:deleteRecord('" + template.getString("templateId") + "', '" + template.getString("templateCode") + "');\" />");

					} else {
						wr.characters("&nbsp;");
					}

					wr.endElement("cell");

					wr.startElement("cell");
					wr.characters(wr.doubleEscape(template.getString("templateName")) + "^javascript:editRecord(\"" + template.getString("templateCode") + "\");^_self");
					wr.endElement("cell");

					wr.startElement("cell");
					wr.characters(wr.doubleEscape(template.getString("name")));
					wr.endElement("cell");

					wr.endElement("row");
				}
				wr.endElement("rows");
				wr.endDocument();
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while generating XML file containing templates", e);
		}
		return sWr.getBuffer().toString();
	}

	private List getTemplates(String userId, int userRoleId, PermissionSet permissionSet) {
		DBPreparedQuery dq = null;
		List templates = null;
		try {
			String[] dynParam = new String[2];
			ArrayList<String> dynamicContent = new ArrayList<String>();
			if (permissionSet.isSHOW_ALL_POSITIONS()) {
				dynParam[0] = "?, ?, ?";
				dynamicContent.add(TemplateConstants.TEMPLATE_SYSTEM);
				dynamicContent.add(TemplateConstants.TEMPLATE_GLOBAL);
				dynamicContent.add(TemplateConstants.TEMPLATE_PRIVATE);
			} else {
				dynParam[0] = "?, ?";
				dynamicContent.add(TemplateConstants.TEMPLATE_GLOBAL);
				dynamicContent.add(TemplateConstants.TEMPLATE_PRIVATE);

			}
			dynParam[1] = "";
			if (UserConstants.ROLE_ADMIN != userRoleId) {
				dynParam[1] = " and case when tt.template_private = ? then tt.user_id = ? else true end ";
				dynamicContent.add(TemplateConstants.TEMPLATE_PRIVATE);
				dynamicContent.add(userId);
			}
			dq = new DBPreparedQuery("dMastersManager_GetTemplates", dynParam);
			dq.setString(1, TemplateConstants.AUTO_NOT_CREATED);
			int cnt = 2;
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}

			templates = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting the templates", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return templates;
	}

	public void deleteTemplate(String templateCode) throws Exception {
		DBPreparedQuery dq = null;
		try {
			/*
			 * dq = new DBPreparedQuery("dTemplateManager_DeleteTemplateTypes");
			 * dq.setString(1, templateCode); dq.execute();
			 */

			dq = new DBPreparedQuery("dTemplateManager_DeleteTemplate");
			dq.setString(1, templateCode);
			dq.execute();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error occured while deleting the template", e);
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}

	public String getGenericVariablesInXML(TemplateData templateData) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
		//	List variables = getGenericVariables(templateData.getTemplateTypeId());
			String variables = templateData.getTemplateVariables();
			if (!Utils.isBlankOrNull(variables)) {
				wr.startDocument();
				wr.startElement("variables");
				String[] variablesArray = variables.split(",");
				for (int i = 0; i < variablesArray.length; i++) {
					wr.startElement("variable");
					wr.characters("$" + variablesArray[i].trim());
					wr.endElement("variable");
				}
				wr.startElement("doShowSaveAsDraft");
				wr.characters(templateData.getDoShowSaveAsDraftOption());
				wr.endElement("doShowSaveAsDraft");
				wr.endElement("variables");
				wr.endDocument();
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("error while creating xml file for emails", e);
		}
		return sWr.getBuffer().toString();
	}

	private List getGenericVariables(String templateTypeId) {
		DBPreparedQuery dq = null;
		List variables = null;
		try {
			dq = new DBPreparedQuery("dMastersManager_GetGenericVariables");
			dq.setString(1, templateTypeId);
			variables = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting generic variables", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return variables;
	}

	public String getFlagsInXml(List flags, PermissionSet permissionSet, String userId) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);

		try {
			wr.startDocument();
			wr.startElement("rows");
			for (int i = 0; i < flags.size(); i++) {
				SimpleDataObject data = (SimpleDataObject) flags.get(i);
				if(permissionSet.isPERMISSION_MANAGE_ALL_FLAGS() || userId.equalsIgnoreCase(data.getString("userId")) || MastersConstants.FLAG_TYPE_PUBLIC.equalsIgnoreCase(data.getString("flagType"))) {
					if (data.getString("flagId").equalsIgnoreCase("28")){
						System.out.println(data.getString("flagText"));
					}
					AttributesImpl at = new AttributesImpl();
					at.addAttribute("", "id", "", "", "" + data.getString("flagId"));
					wr.startElement("", "row", "", at);

					at = new AttributesImpl();
					at.addAttribute("", "name", "", "", "Col_I_Comment");
					wr.startElement("", "userdata", "", at);
					wr.characters(wr.doubleEscape("Delete"));
					wr.endElement("userdata");

					at = new AttributesImpl();
					at.addAttribute("", "name", "", "", "Col_II_Comment");
					wr.startElement("", "userdata", "", at);
					wr.characters(data.getString("flagText"));
					wr.endElement("userdata");

					wr.startElement("cell");
					if(permissionSet.isPERMISSION_MANAGE_ALL_FLAGS() || (userId.equalsIgnoreCase(data.getString("userId")) && MastersConstants.FLAG_TYPE_PRIVATE.equalsIgnoreCase(data.getString("flagType")))) {
						wr.characters("<img src=\"images/ico_delete.gif\" border=0 style=\"cursor:pointer;\" onclick=\"javascript:deleteRecord(" + data.getString("flagId") + ");\" />");
					} else {
						wr.characters("&nbsp;");
					}					
					wr.endElement("cell");
					
					wr.startElement("cell");
					wr.characters("<img src=\"" + data.getString("flagImage") + "\" border=0>");
					wr.endElement("cell");

					wr.startElement("cell");
					if(permissionSet.isPERMISSION_MANAGE_ALL_FLAGS()) {
						wr.characters(wr.doubleEscape(data.getString("flagText")) + "^javascript:editRecord(" + data.getString("flagId") + ");^_self");
					} else {
						wr.characters(wr.doubleEscape(data.getString("flagText")));
					}					
					wr.endElement("cell");
					
					String categoryType="Public";
					wr.startElement("cell");
					if((data.getString("flagType")).equals(MastersConstants.FLAG_TYPE_PRIVATE)){
						categoryType="Private";
					}
					wr.characters(""+categoryType);
					wr.endElement("cell");
					
					wr.startElement("cell");
					wr.characters(wr.doubleEscape(data.getString("name")));
					wr.endElement("cell");

					wr.endElement("row");
				}				
			}
			wr.endElement("rows");
			wr.endDocument();
		} catch (SAXException e) {
			TPLogger.getLogger().error("Error while creating xml file for flags", e);
		}
		return sWr.getBuffer().toString();
	}

	public SimpleDataObject getFlag(String flagId) {
		DBPreparedQuery dq = null;
		SimpleDataObject flagData = null;
		try {
			dq = new DBPreparedQuery("dMastersManager_GetFlag");
			dq.setString(1, flagId);
			flagData = (SimpleDataObject) dq.getSingleObjectResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting the flag for id " + flagId, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return flagData;
	}

	public List<String> getExistingFlagImages(String userId) {
		DBPreparedQuery dq = null;
		List<SimpleDataObject> flagData = null;
		List<String> flagImages = new ArrayList<String>();
		try {
			dq = new DBPreparedQuery("dMastersManager_GetFlagImages");
			dq.setString(1, MastersConstants.FLAG_TYPE_PUBLIC);
			dq.setString(2, userId);
			flagData = dq.getResult();
			for(int i = 0; flagData != null && i < flagData.size(); i++) {
				SimpleDataObject sdo = flagData.get(i);
				flagImages.add(sdo.getString("flagImage"));
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting the flag images", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return flagImages;
	}
	
	public void modifyFlag(String flagId, String flagText, String flagImage, String flagType) throws Exception {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dMastersManager_UpdateFlag");
			dq.setString(1, flagText);
			dq.setString(2, flagImage);
			dq.setString(3, flagType);
			dq.setString(4, flagId);
			dq.execute();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while modifying the flag for id " + flagId, e);
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}

	public void createFlag(String flagId, String flagText, String flagImage, String flagType, String userId) throws Exception {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dMastersManager_InsertFlag");
			dq.setString(1, flagImage);
			dq.setString(2, flagText);
			dq.setString(3, flagType);
			dq.setString(4, userId);
			dq.execute();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while creating the flag", e);
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}
	
	public List<InboxFolderData> getAllInboxFolders() throws SQLException {
		List<InboxFolderData> folders = new ArrayList<InboxFolderData>();
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dMastersManager_GetAllInboxFolders");
			folders = (List<InboxFolderData>) dq.getResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error while getting all folders", e);
			throw e;
		}finally {
			if(dq != null) {
				dq.releaseTransaction(null);
			}
		}
		return folders;
	}
	
	public InboxFolderData getInboxFolderByInboxFolderId(String folderId) throws SQLException {
		InboxFolderData data = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dMastersManager_GetInboxFolderById");
			dq.setString(1, folderId);
			data = (InboxFolderData) dq.getSingleObjectResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error while getting folder data", e);
			throw e;
		} finally {
			if(dq != null) {
				dq.releaseTransaction(null);
			}
		}
		return data;
	}
	
	public String getXMLForInboxFolders(List<InboxFolderData> folders) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);

		try {
			wr.startDocument();
			wr.startElement("rows");
			if(folders != null && folders.size() > 0) {
				for (int i = 0; folders != null && i < folders.size(); i++) {
					InboxFolderData data = folders.get(i);

					AttributesImpl at = new AttributesImpl();
					at.addAttribute("", "id", "", "", data.getFolderId());
					wr.startElement("", "row", "", at);

					at = new AttributesImpl();
					at.addAttribute("", "name", "", "", "Col_I_Comment");
					wr.startElement("", "userdata", "", at);
					if(MastersConstants.SYSTEM_FOLDER.equals(data.getSystemDefined())) {						
						wr.characters("");
					} else {
						wr.characters("Delete");						
					}	
					wr.endElement("userdata");

					at = new AttributesImpl();
					at.addAttribute("", "name", "", "", "folderName");
					wr.startElement("", "userdata", "", at);
					wr.characters(data.getFolderName());
					wr.endElement("userdata");
					
					wr.startElement("cell");
					if(MastersConstants.SYSTEM_FOLDER.equals(data.getSystemDefined()) || MastersConstants.DRAFT.equals(data.getSystemDefined()) ) {						
						wr.characters("&nbsp;");				
					} else {
						wr.characters("<img src=\"images/ico_delete.gif\" border=0 onclick=\"javascript:deleteRecord(" + data.getFolderId() + ");\" />");												
					}
					wr.endElement("cell");

					wr.startElement("cell");
					wr.characters(wr.doubleEscape(data.getFolderName()) + "^javascript:editRecord(" + data.getFolderId() + ");^_self");
					wr.endElement("cell");			

					wr.endElement("row");
				}
			}			
			wr.endElement("rows");
			wr.endDocument();
		} catch (SAXException e) {
			TPLogger.getLogger().error("Error while creating xml", e);
		}

		return sWr.getBuffer().toString();
	}
	
	public InboxFolderData getInboxFolderByName(String folderName) throws SQLException {
		DBPreparedQuery dq = null;
		InboxFolderData data = null;
		try {
			dq = new DBPreparedQuery("dMastersManager_GetFolderByName");
			dq.setString(1, folderName);
			data = (InboxFolderData) dq.getSingleObjectResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error while getting folder by name", e);
			throw e;
		}finally {
			if(dq != null) {
				dq.releaseTransaction(null);
			}
		}
		return data;
	}
	
	public void addInboxFolder(String folderName, String createdBy) throws SQLException {
		DBPreparedQuery dq = null;		
		try {			
			dq = new DBPreparedQuery("dMastersManager_AddInboxFolder");
			dq.setString(1, folderName);
			dq.setString(2, MastersConstants.NON_SYSTEM_FOLDER);
			dq.setString(3, "1");
			dq.setString(4, UserConstants.ADMIN_ID);
			dq.setString(5, createdBy);
			dq.execute();
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error while creating new folder", e);
			throw e;
		}finally {
			if(dq != null) {
				dq.releaseTransaction(null);
			}
		}
	}
	
	public void updateInboxFolder(String folderId, String folderName) throws SQLException {
		DBPreparedQuery dq = null;		
		try {
			dq = new DBPreparedQuery("dMastersManager_UpdateInboxFolder");
			dq.setString(1, folderName);
			dq.setString(2, folderId);
			dq.execute();
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error while updating folder", e);
			throw e;
		}finally {
			if(dq != null) {
				dq.releaseTransaction(null);
			}
		}
	}
	
	public void deleteInboxFolder(String folderId) throws SQLException {
		DBPreparedQuery dq = null;		
		try {
			dq = new DBPreparedQuery("dMastersManager_DeleteInboxFolder");			
			dq.setString(1, folderId);
			dq.execute();
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error while deleting folder", e);
			throw e;
		}finally {
			if(dq != null) {
				dq.releaseTransaction(null);
			}
		}
	}
	
	public ArrayList<SimpleDataObject> getAllSourceTypes() throws SQLException {
		DBPreparedQuery dq = null;
		ArrayList<SimpleDataObject> sourceTypes = null;
		try {
			dq = new DBPreparedQuery("dMastersManager_GetAllSourceTypes");
			sourceTypes = dq.getResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error while getting source types", e);
			throw e;
		} finally {
			if(dq != null) {
				dq.releaseTransaction(null);
			}
		}
		return sourceTypes;
	}
	
	public ArrayList<SimpleDataObject> getSourcesOfSourceType(String sourceTypeIds) throws SQLException {
		DBPreparedQuery dq = null;
		ArrayList<SimpleDataObject> sources = null;
		String[] dynParam = new String[1];
		try {
			dynParam[0] = "";
			ArrayList<String> dynamicContent = new ArrayList<String>();
			String qMarks = Utils.setDynamicParamsAndReturnQmarks(sourceTypeIds, dynamicContent);
			dynParam[0] = qMarks;
			
			dq = new DBPreparedQuery("dMastersManager_GetSourcesOfSourceType",dynParam);
			int cnt = 1;
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}			
			sources = dq.getResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		} finally {
			if(dq != null) {
				dq.releaseTransaction(null);
			}
		}
		return sources;
	}
	
	public ArrayList<SourceData> getSourcesBySorceCategory(String sourceType) throws SQLException {
		DBPreparedQuery dq = null;
		ArrayList<SourceData> sources = null;
		try {
			dq = new DBPreparedQuery("dMastersManager_GetSourcesBySorceCategory");
			dq.setString(1, sourceType);
			sources = dq.getResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		} finally {
			if(dq != null) {
				dq.releaseTransaction(null);
			}
		}
		return sources;
	}
	
	public List getExcelImportData(String sessionId) {
		DBPreparedQuery dq = null;
		List variables = null;
		try {
			dq = new DBPreparedQuery("dMastersManager_GetExcelImportData");
			dq.setLong(1, new Long(sessionId).longValue());
			variables = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting ExcelImportData", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return variables;
	}
	
	public String getXMLforExcelImport(ArrayList data) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			wr.startDocument();
			wr.startElement("rows");
			for (int i = 0; i < data.size(); i++) {
				SimpleDataObject sData = (SimpleDataObject) data.get(i);

				AttributesImpl at = new AttributesImpl();
				at.addAttribute("", "id", "", "", "" + sData.getString("applicantId"));
				wr.startElement("", "row", "", at);

				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "Col_I_Comment");
				wr.startElement("", "userdata", "", at);
				wr.characters("Delete");
				wr.endElement("userdata");

				wr.startElement("cell");
				wr.characters(wr.doubleEscape(sData.getString("applicantName")));
				wr.endElement("cell");
				
				wr.startElement("cell");
				wr.characters(wr.doubleEscape(sData.getString("rowId")));
				wr.endElement("cell");
				
				wr.startElement("cell");
				wr.characters(wr.doubleEscape(sData.getString("status")));
				wr.endElement("cell");

				wr.endElement("row");
			}
			wr.endElement("rows");
			wr.endDocument();
		} catch (SAXException e) {
			TPLogger.getLogger().error("Error while creating xml file for ExcelImport", e);
		}
		return sWr.getBuffer().toString();
	}
	
	public void deleteFlagFromDB(String flagId) throws SQLException {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dMastersManager_DeleteFlag");
			dq.setString(1, flagId);
			dq.execute();
		} finally {
			if(dq != null) {
				dq.releaseConnection();
			}
		}
	}
	
	public int getFlagsCount(String userId, String flagType) throws Exception {
		DBPreparedQuery dq = null;
		int count = 0;
		try {
			dq = new DBPreparedQuery("dMastersManager_GetFlagCount");
			dq.setString(1, MastersConstants.FLAG_TYPE_PRIVATE);
			dq.setString(2, userId);
			dq.setString(3, flagType);
			count = dq.getIntResult();
		} finally {
			if(dq != null) {
				dq.releaseConnection();
			}
		}
		return count;
	}

	public boolean employeeCodeExists(String employeeCode) {
		DBPreparedQuery dq = null;
		String sourceId = "";
		try {
			dq = new DBPreparedQuery("dMastersManager_GetSourceIdForEmployeeCode");
			dq.setString(1, employeeCode);
			sourceId = dq.getIdResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if(dq != null) {
				dq.releaseConnection();
			}
		}
		if(!Utils.isBlankOrNull(sourceId)){
			return true;
		}else{
			return false;
		}
	}
	
	public int getNumberOfEmailsInFolder(String folderId) {
		int noOfEmails = 0;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dMastersManager_GetNumberOfEmailsInFolder");
			dq.setString(1, folderId);
			noOfEmails = dq.getIntResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} catch (NoResultFoundException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if(dq != null) {
				dq.releaseConnection();
			}
		}
		return noOfEmails;
	}
	
	public SimpleDataObject getInstituteData(String instituteId){
		DBPreparedQuery dq = null;
		SimpleDataObject sdObj= null;
		try {
			dq = new DBPreparedQuery("dMastersManager_GetInstituteData");
			dq.setString(1, instituteId);
			sdObj = (SimpleDataObject)dq.getSingleObjectResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error While getting Institutes with aliases.", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return sdObj;
	}
	
	public String getDegreeId(String degreeName) {		
		String degreeId = "";
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dMastersManager_GetDegreeId");
			dq.setString(1, degreeName);
			dq.setString(2, degreeName);
			degreeId = dq.getIdResult();			
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return degreeId;
	}
	
	public String getBranchId(String branch){
		String branchId = "";
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dMastersManager_GetBranchId");
			dq.setString(1, branch);
			dq.setString(2, branch);
			branchId = dq.getIdResult();			
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return branchId;
	}
	
	public String getGradeId(String grade){
		String gradeId = "";
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dMastersManager_GetGradeId");
			dq.setString(1, grade);
			gradeId = dq.getIdResult();			
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return gradeId;
	}
	public String getBandId(String band){
		String bandId = "";
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dMastersManager_GetBandId");
			dq.setString(1, band);
			bandId = dq.getIdResult();			
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return bandId;
	}
	public String getSkillId(String skill){
		String skillId = "";
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dMastersManager_GetSkillId");
			dq.setString(1, skill);
			dq.setString(2, skill);
			skillId = dq.getIdResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return skillId;
	}
	
	public String getChildDeptId(String deptName, String parentId){
		String childDeptId = "";
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dMastersManager_GetNonParentDeptId");
			dq.setString(1, MastersConstants.ACTIVE);
			dq.setString(2, deptName);
			dq.setString(3, parentId);
			childDeptId = dq.getIdResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return childDeptId;
	}
	
	public String getChildDeptName(String deptId, String parentId){
		String childDeptName = "";
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dMastersManager_GetNonParentDeptName");
			dq.setString(1, MastersConstants.ACTIVE);
			dq.setString(2, deptId);
			dq.setString(3, parentId);
			childDeptName = dq.getStringResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return childDeptName;
	}
	
	public void addBudgetGradeToDB(String gradeName, String description, String hireByDuration) throws MasterExistException, AliasExistException, SQLException {
		DBPreparedQuery dq = null;
		DBTransaction tran = null;
		SalaryStructureManager ssm = new SalaryStructureManager();  
		try {
			tran = new DBTransaction();
			dq = new DBPreparedQuery("dMastersManager_AddBudgetGradeToDB",tran);
			dq.setString(1, gradeName);
			dq.setString(2, description);
			dq.setString(3, hireByDuration);
			dq.execute();

			dq = new DBPreparedQuery("dFetchLastInsertID", tran);
			String gradeId = dq.getIdResult();
			
			dq = new DBPreparedQuery("dMastersManager_UpdateGradeRank",tran);
			dq.setString(1, gradeId);
			dq.setString(2, gradeId);
			dq.execute();
			
			ssm.insertDefaultBasicFormulaForGrade(gradeId, tran);
			
			tran.commit();
			
		}catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			tran.rollback();
			throw new MasterExistException();
		} finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}
		}
	}
	
	public void updateBudgetGradeToDB(String gradeId, String gradeName, String description, String hireByDuration) throws MasterExistException {
		DBPreparedQuery dq = null;
		try {			
			dq = new DBPreparedQuery("dMastersManager_UpdateBudgetGrade");
			dq.setString(1, gradeName);
			dq.setString(2, description);
			if(Utils.isBlankOrNull(hireByDuration)){
				dq.setNull(3, Types.NULL);
			}else{
				dq.setString(3, hireByDuration);
			}
			dq.setString(4, gradeId);
			dq.execute();
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);			
			throw new MasterExistException();
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}
	
	public void deleteBudgetGrade(String gradeId) throws MasterExistException {
		DBPreparedQuery dq = null;
		try {			
			dq = new DBPreparedQuery("dMastersManager_DeleteBudgetGradeFromDB");			
			dq.setString(1, gradeId);
			dq.execute();
		}catch (Exception e) {
			TPLogger.getLogger().error("Error While Deleting Budget Grade", e);			
			throw new MasterExistException();
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}
	
	public SimpleDataObject getBudgetGrade(String gradeId) throws MasterExistException {
		DBPreparedQuery dq = null;
		try {			
			dq = new DBPreparedQuery("dMastersManager_getBudgetGrade");			
			dq.setString(1, gradeId);
			return (SimpleDataObject) dq.getSingleObjectResult();
		}catch (Exception e) {
			TPLogger.getLogger().error("Error While Getting Budget Grade", e);			
			throw new MasterExistException();
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}
	
	public SimpleDataObject getBudgetGradeName(String gradeName) throws MasterExistException {
		DBPreparedQuery dq = null;
		try {			
			dq = new DBPreparedQuery("dMastersManager_getBudgetGrade_name");			
			dq.setString(1, gradeName);
			return (SimpleDataObject) dq.getSingleObjectResult();
		}catch (Exception e) {
			TPLogger.getLogger().error("Error While Getting Budget Grade", e);			
			throw new MasterExistException();
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}
	
	public ArrayList getAllBudgetGrades() throws MasterExistException {
		DBPreparedQuery dq = null;
		ArrayList budgetGrades = null;
		try {
			dq = new DBPreparedQuery("dMastersManager_GetAllBudgetGrades");
			budgetGrades = dq.getResult();
		}catch (Exception e) {
			TPLogger.getLogger().error("Error While getting Budget Grades", e);			
			throw new MasterExistException();
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return budgetGrades;
	}
	
	public ArrayList getAllCountries() throws MasterExistException {
		DBPreparedQuery dq = null;
		ArrayList budgetGrades = null;
		try {
			dq = new DBPreparedQuery("dMastersManager_GetCountries");
			budgetGrades = dq.getResult();
		}catch (Exception e) {
			TPLogger.getLogger().error("Error While getting Budget Grades", e);			
			throw new MasterExistException();
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return budgetGrades;
	}
	public ArrayList getAllJobIndustryCodes() throws MasterExistException {
		DBPreparedQuery dq = null;
		ArrayList budgetGrades = null;
		try {
			dq = new DBPreparedQuery("dMastersManager_GetJobIndustryCodes");
			budgetGrades = dq.getResult();
		}catch (Exception e) {
			TPLogger.getLogger().error("Error While getting Budget Grades", e);			
			throw new MasterExistException();
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return budgetGrades;
	}
	public ArrayList getAllJobFunctionCodes() throws MasterExistException {
		DBPreparedQuery dq = null;
		ArrayList budgetGrades = null;
		try {
			dq = new DBPreparedQuery("dMastersManager_GetJobFunctionCodes");
			budgetGrades = dq.getResult();
		}catch (Exception e) {
			TPLogger.getLogger().error("Error While getting Budget Grades", e);			
			throw new MasterExistException();
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return budgetGrades;
	}
	public ArrayList getAllJobRoleCodes(String id) throws MasterExistException {
		DBPreparedQuery dq = null;
		ArrayList budgetGrades = null;
		try {
			dq = new DBPreparedQuery("dMastersManager_GetJobRoleCodes");
			dq.setString(1, id + "%");
			budgetGrades = dq.getResult();
		}catch (Exception e) {
			TPLogger.getLogger().error("Error While getting Budget Grades", e);			
			throw new MasterExistException();
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return budgetGrades;
	}
	
	public String getXMLForGrades(List<SimpleDataObject> grades) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);

		try {
			wr.startDocument();
			wr.startElement("rows");
			if(grades != null && grades.size() > 0) {
				for (int i = 0; grades != null && i < grades.size(); i++) {
					SimpleDataObject data = grades.get(i);
					
					AttributesImpl at = new AttributesImpl();
					at.addAttribute("", "id", "", "", data.getAttribute("itemId")+"");
					wr.startElement("", "row", "", at);

					at = new AttributesImpl();
					at.addAttribute("", "name", "", "", "Col_I_Comment");
					wr.startElement("", "userdata", "", at);					
					wr.characters("Delete");						
					wr.endElement("userdata");
				
					wr.startElement("cell");
					wr.characters("<img src=\"images/ico_delete.gif\" border=0 onclick=\"javascript:deleteRecord(" + data.getAttribute("itemId") + ");\" />");												
					wr.endElement("cell");
					
					at = new AttributesImpl();
					at.addAttribute("", "name", "", "", "itemName");
					wr.startElement("", "userdata", "", at);					
					wr.characters((String) data.getAttribute("itemName"));						
					wr.endElement("userdata");

					wr.startElement("cell");
					wr.characters(wr.doubleEscape((String) data.getAttribute("itemName")) + "^javascript:editRecord(" + data.getAttribute("itemId") + ");^_self");
					wr.endElement("cell");	
					
					wr.startElement("cell");
					wr.characters((String) data.getAttribute("description"));												
					wr.endElement("cell");

					wr.startElement("cell");				
					wr.characters(data.getString("hireByDuration"));												
					wr.endElement("cell");
					
					wr.startElement("cell");	
					wr.characters("<img src=\"images/btn_uparrow.gif\" border=0 onclick=\"javascript:moveGrade(" + data.getAttribute("itemId") +","+data.getAttribute("rank")+",1"+");\" />"+
							"&nbsp;&nbsp;"+"<img src=\"images/btn_dwnarrow.gif\" border=0 onclick=\"javascript:moveGrade(" + data.getAttribute("itemId") +","+data.getAttribute("rank")+",0"+");\" />");										
					wr.endElement("cell");
					
					wr.endElement("row");
				}
			}			
			wr.endElement("rows");
			wr.endDocument();
		} catch (SAXException e) {
			TPLogger.getLogger().error("Error while creating xml", e);
		}

		return sWr.getBuffer().toString();
	}
	
	public String getXMLForBands(List<SimpleDataObject> bands) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);

		try {
			wr.startDocument();
			wr.startElement("rows");
			if(bands != null && bands.size() > 0) {
				for (int i = 0; bands != null && i < bands.size(); i++) {
					SimpleDataObject data = bands.get(i);

					AttributesImpl at = new AttributesImpl();
					at.addAttribute("", "id", "", "", data.getAttribute("itemId")+"");
					wr.startElement("", "row", "", at);

					at = new AttributesImpl();
					at.addAttribute("", "name", "", "", "Col_I_Comment");
					wr.startElement("", "userdata", "", at);					
					wr.characters("Delete");						
					wr.endElement("userdata");
				
					wr.startElement("cell");
					wr.characters("<img src=\"images/ico_delete.gif\" border=0 onclick=\"javascript:deleteRecord(" + data.getAttribute("itemId") + ");\" />");												
					wr.endElement("cell");
					
					at = new AttributesImpl();
					at.addAttribute("", "name", "", "", "itemName");
					wr.startElement("", "userdata", "", at);					
					wr.characters((String) data.getAttribute("itemName"));						
					wr.endElement("userdata");

					wr.startElement("cell");
					wr.characters(wr.doubleEscape((String) data.getAttribute("itemName")) + "^javascript:editRecord(" + data.getAttribute("itemId") + ");^_self");
					wr.endElement("cell");	
					
					wr.startElement("cell");
					wr.characters((String) data.getAttribute("description"));												
					wr.endElement("cell");
					
					wr.endElement("row");
				}
			}			
			wr.endElement("rows");
			wr.endDocument();
		} catch (SAXException e) {
			TPLogger.getLogger().error("Error while creating xml", e);
		}

		return sWr.getBuffer().toString();
	}
	
	public void addBudgetBandToDB(String bandName, String description) throws MasterExistException, AliasExistException {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dMastersManager_AddBudgetBandToDB");
			dq.setString(1, bandName);
			dq.setString(2, description);
			dq.execute();
		}catch (Exception e) {
			TPLogger.getLogger().error("Error While Adding Budget Band", e);
			throw new MasterExistException();
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}
	
	public void updateBudgetBandToDB(String bandId, String bandName, String description) throws MasterExistException, AliasExistException {
		DBPreparedQuery dq = null;
		try {			
			dq = new DBPreparedQuery("dMastersManager_UpdateBudgetBand");
			dq.setString(1, bandName);
			dq.setString(2, description);
			dq.setString(3, bandId);
			dq.execute();
		}catch (Exception e) {
			TPLogger.getLogger().error("Error While Updating Budget Band", e);			
			throw new MasterExistException();
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}
	
	public void deleteBudgetBand(String bandId) throws MasterExistException {
		DBPreparedQuery dq = null;
		try {			
			dq = new DBPreparedQuery("dMastersManager_DeleteBudgetBandFromDB");			
			dq.setString(1, bandId);
			dq.execute();
		}catch (Exception e) {
			TPLogger.getLogger().error("Error While Deleting Budget Band", e);			
			throw new MasterExistException();
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}
	
	public SimpleDataObject getBudgetBand(String bandId) throws MasterExistException {
		DBPreparedQuery dq = null;
		try {			
			dq = new DBPreparedQuery("dMastersManager_getBudgetBand");			
			dq.setString(1, bandId);
			return (SimpleDataObject) dq.getSingleObjectResult();
		}catch (Exception e) {
			TPLogger.getLogger().error("Error While Getting Budget Band", e);			
			throw new MasterExistException();
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}
	
	public SimpleDataObject getBudgetNameBand(String bandName) throws MasterExistException {
		DBPreparedQuery dq = null;
		try {			
			dq = new DBPreparedQuery("dMastersManager_getBudgetBand_name");			
			dq.setString(1, bandName);
			return (SimpleDataObject) dq.getSingleObjectResult();
		}catch (Exception e) {
			TPLogger.getLogger().error("Error While Getting Budget Band", e);			
			throw new MasterExistException();
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}
	
	public ArrayList getAllBudgetBands() throws MasterExistException {
		DBPreparedQuery dq = null;
		ArrayList budgetGrades = null;
		try {
			dq = new DBPreparedQuery("dMastersManager_GetAllBudgetBands");
			budgetGrades = dq.getResult();
		}catch (Exception e) {
			TPLogger.getLogger().error("Error While getting Budget Bands", e);			
			throw new MasterExistException();
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return budgetGrades;
	}

	public void moveGradesUpDown(String gradeId, String gradeRank, String rankUp) {
		DBPreparedQuery dq = null;
		DBTransaction tran = null;
		try {
			SimpleDataObject toRankData = getGradeDetails(gradeRank, rankUp);
			if(toRankData!=null){
				tran = new DBTransaction();
				
				dq = new DBPreparedQuery("dMastersManager_UpdateGradeRank", tran);
				dq.setObject(1, toRankData.getAttribute("gradeRank"));
				dq.setString(2, gradeId);
				dq.execute();
				
				dq = new DBPreparedQuery("dMastersManager_UpdateGradeRank", tran);
				dq.setString(1, gradeRank);
				dq.setObject(2, toRankData.getAttribute("gradeId"));
				dq.execute();
				
				tran.commit();
			}
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}
		}
	}
	
	public SimpleDataObject getGradeDetails(String rank, String rankUp){
		DBPreparedQuery dq = null;
		SimpleDataObject sd = new SimpleDataObject();
		try {
			String[] dynParam = new String[2];
			dynParam[0]= " < ";
			dynParam[1]= " DESC ";
			
			if(rankUp.equals("0")){
				dynParam[0]= " > ";
				dynParam[1]= " ASC ";
			}
				
			dq = new DBPreparedQuery("dMastersManager_GetGradeDetails",dynParam);
			dq.setString(1, rank);
			sd = (SimpleDataObject)dq.getSingleObjectResult();
		}catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return sd;
	}
	/**
	 * 
	 * @param buName
	 * @param buDescription
	 * @param parentBuId
	 * @throws Exception
	 */
	
	public void addBusinessUnitToDB(String buName, String buDescription) throws Exception {
		DBPreparedQuery dq = null;
		try {
			//dq = new DBPreparedQuery("dMastersManager_AddLOBToDB");
			dq = new DBPreparedQuery("dMastersManager_AddBUToDB");
			dq.setString(1, buName.trim());
			dq.setString(2, buDescription.trim());			
			dq.execute();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw new MasterExistException();
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}
	
	public SimpleDataObject getBusinessUnit(String buId) throws MasterExistException {
		DBPreparedQuery dq = null;
		try {			
			//dq = new DBPreparedQuery("dMastersManager_GetLOBData");			
			dq = new DBPreparedQuery("dMastersManager_GetBUData");
			dq.setString(1, buId);
			return (SimpleDataObject) dq.getSingleObjectResult();
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);		
			throw new MasterExistException();
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}
	
	public ArrayList<SimpleDataObject> getAllBusinessUnitData() throws SQLException {
		ArrayList<SimpleDataObject> allBu = new ArrayList<SimpleDataObject>();
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dMastersManager_GetAllBUData");
			allBu = dq.getResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		}finally {
			if(dq != null) {
				dq.releaseTransaction(null);
			}
		}
		return allBu;
	}

	
	public void updateBusinessUnitToDB(String buId, String buName, String buDescription) throws MasterExistException {
		DBPreparedQuery dq = null;
		try {			
			//dq = new DBPreparedQuery("dMastersManager_UpdateLOB");
			dq = new DBPreparedQuery("dMastersManager_UpdateBU");
			dq.setString(1, buName.trim());
			dq.setString(2, buDescription.trim());			
			dq.setString(3, buId);
			dq.execute();
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);			
			throw new MasterExistException();
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}
	
	public void deleteBusinessUnitFromDB(String buId) throws MasterExistException {
		DBPreparedQuery dq = null;
		try {			
			//dq = new DBPreparedQuery("dMastersManager_DeleteLOBFromDB");
			dq = new DBPreparedQuery("dMastersManager_DeleteBUFromDB");
			dq.setString(1, buId);
			dq.execute();
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);			
			throw new MasterExistException();
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}
	
	public String getXMLForBusinessUnit(List<SimpleDataObject> list) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			wr.startDocument();
			wr.startElement("rows");
			for (int i = 0; i < list.size(); i++) {
				SimpleDataObject data = (SimpleDataObject) list.get(i);
				String buId = data.getId("buId");
				String buName = data.getString("buName");
				String buDescription = data.getString("buDescription");

				AttributesImpl at = new AttributesImpl();
				at.addAttribute("", "id", "", "", "" + buId);
				wr.startElement("", "row", "", at);

				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "Col_I_Comment");
				wr.startElement("", "userdata", "", at);
				wr.characters("Delete");
				wr.endElement("userdata");
				
				wr.startElement("cell");				
				wr.characters("<img src=\"images/ico_delete.gif\" border=0 onclick=\"javascript:deleteRecord(" + buId + ");\" />");
				wr.endElement("cell");


				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "Business_Unit");
				wr.startElement("", "userdata", "", at);
				wr.characters(buName);
				wr.endElement("userdata");
				
				wr.startElement("cell");
				wr.characters(wr.doubleEscape(buName) + "^javascript:editRecord(" + buId + ");^_self");
				wr.endElement("cell");
				
				wr.startElement("cell");
				wr.characters(buDescription);												
				wr.endElement("cell");
				
				wr.endElement("row");
			}
			wr.endElement("rows");
			wr.endDocument();

		} catch (SAXException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}

		return sWr.getBuffer().toString();
	}
	
	
	public ArrayList<SimpleDataObject> getBuListByIds(String buIds) throws SQLException {
		ArrayList<SimpleDataObject> buData = null;
		DBPreparedQuery dq = null;
		String[] dynParam = new String[1];
		try {
			dynParam[0] = "";
			ArrayList<String> dynamicContent = new ArrayList<String>();
			String qMarks = Utils.setDynamicParamsAndReturnQmarks(buIds, dynamicContent);
			dynParam[0] = qMarks;
			//dq = new DBPreparedQuery("dMastersManager_GetLobByIds",dynParam);
			dq = new DBPreparedQuery("dMastersManager_GetBuByIds",dynParam);
			int cnt = 1;
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}
			buData =  dq.getResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		} finally {
			if(dq != null) {
				dq.releaseTransaction(null);
			}
		}
		return buData;
	}
	
	
	public ArrayList<SimpleDataObject> getBuListByName(String buName) throws SQLException {
		DBPreparedQuery dq = null;
		ArrayList<SimpleDataObject> data = null;
		String[] dynParam = new String[1];
		try {
			dynParam[0] = "";
			ArrayList<String> dynamicContent = new ArrayList<String>();
			String qMarks = Utils.setDynamicParamsAndReturnQmarks(buName, dynamicContent);
			dynParam[0] = qMarks;
			//dq = new DBPreparedQuery("dMastersManager_GetLobByName",dynParam);
			dq = new DBPreparedQuery("dMastersManager_GetBuByName",dynParam);
			int cnt = 1;
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}
			data = dq.getResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		}finally {
			if(dq != null) {
				dq.releaseTransaction(null);
			}
		}
		return data;
	}
	
	
	/**
	 * 
	 * @param costCenterName
	 * @param costCenterDescription
	 * @param parentCostCenterId
	 * @throws Exception
	 */
	

	public void addCostCenterToDB(String costCenterName, String costCenterDescription) throws Exception {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dMastersManager_AddCostCenterToDB");
			dq.setString(1, costCenterName.trim());
			dq.setString(2, costCenterDescription.trim());			
			dq.execute();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw new MasterExistException();
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}
	
	public SimpleDataObject getCostCenter(String costCenterId) throws MasterExistException {
		DBPreparedQuery dq = null;
		try {			
			dq = new DBPreparedQuery("dMastersManager_GetCostCenterData");			
			dq.setString(1, costCenterId);
			return (SimpleDataObject) dq.getSingleObjectResult();
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);		
			throw new MasterExistException();
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}
	
	public ArrayList<SimpleDataObject> getAllCostCenterData() throws SQLException {
		ArrayList<SimpleDataObject> allCostCenter = new ArrayList<SimpleDataObject>();
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dMastersManager_GetAllCostCenterData");
			allCostCenter = dq.getResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		}finally {
			if(dq != null) {
				dq.releaseTransaction(null);
			}
		}
		return allCostCenter;
	}

	
	public void updateCostCenterToDB(String costCenterId, String costCenterName, String costCenterDescription) throws MasterExistException {
		DBPreparedQuery dq = null;
		try {			
			dq = new DBPreparedQuery("dMastersManager_UpdateCostCenter");
			dq.setString(1, costCenterName.trim());
			dq.setString(2, costCenterDescription.trim());			
			dq.setString(3, costCenterId);
			dq.execute();
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);			
			throw new MasterExistException();
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}
	
	public void deleteCostCenterFromDB(String costCenterId) throws MasterExistException {
		DBPreparedQuery dq = null;
		try {			
			dq = new DBPreparedQuery("dMastersManager_DeleteCostCenterFromDB");			
			dq.setString(1, costCenterId);
			dq.execute();
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);			
			throw new MasterExistException();
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}
	
	public String getXMLForCostCenter(List<SimpleDataObject> list) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			wr.startDocument();
			wr.startElement("rows");
			for (int i = 0; i < list.size(); i++) {
				SimpleDataObject data = (SimpleDataObject) list.get(i);
				String costCenterId = data.getId("costCenterId");
				String costCenterName = data.getString("costCenterName");
				String costCenterDescription = data.getString("costCenterDescription");

				AttributesImpl at = new AttributesImpl();
				at.addAttribute("", "id", "", "", "" + costCenterId);
				wr.startElement("", "row", "", at);

				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "Col_I_Comment");
				wr.startElement("", "userdata", "", at);
				wr.characters("Delete");
				wr.endElement("userdata");
				
				wr.startElement("cell");				
				wr.characters("<img src=\"images/ico_delete.gif\" border=0 onclick=\"javascript:deleteRecord(" + costCenterId + ");\" />");
				wr.endElement("cell");


				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "Business_Unit");
				wr.startElement("", "userdata", "", at);
				wr.characters(costCenterName);
				wr.endElement("userdata");
				
				wr.startElement("cell");
				wr.characters(wr.doubleEscape(costCenterName) + "^javascript:editRecord(" + costCenterId + ");^_self");
				wr.endElement("cell");
				
				wr.startElement("cell");
				wr.characters(costCenterDescription);												
				wr.endElement("cell");
				
				wr.endElement("row");
			}
			wr.endElement("rows");
			wr.endDocument();

		} catch (SAXException e) {
			TPLogger.getLogger().error("Error while creating xml file for Departments", e);
		}

		return sWr.getBuffer().toString();
	}
	
	public ArrayList<SimpleDataObject> getCostCenterListByIds(String costCenterIds) throws SQLException {
		ArrayList<SimpleDataObject> costCenterData = null;
		DBPreparedQuery dq = null;
		String[] dynParam = new String[1];
		try {
			dynParam[0] = "";
			ArrayList<String> dynamicContent = new ArrayList<String>();
			String qMarks = Utils.setDynamicParamsAndReturnQmarks(costCenterIds, dynamicContent);
			dynParam[0] = qMarks;
			dq = new DBPreparedQuery("dMastersManager_GetCostCenterByIds",dynParam);
			int cnt = 1;
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}
			costCenterData =  dq.getResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		} finally {
			if(dq != null) {
				dq.releaseTransaction(null);
			}
		}
		return costCenterData;
	}
	
	public ArrayList<SimpleDataObject> getCostCenterListByName(String costCenterName) throws SQLException {
		DBPreparedQuery dq = null;
		ArrayList<SimpleDataObject> data = null;
		String[] dynParam = new String[1];
		try {
			dynParam[0] = "";
			ArrayList<String> dynamicContent = new ArrayList<String>();
			String qMarks = Utils.setDynamicParamsAndReturnQmarks(costCenterName, dynamicContent);
			dynParam[0] = qMarks;
			dq = new DBPreparedQuery("dMastersManager_GetCostCenterByName",dynParam);
			int cnt = 1;
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}
			data = dq.getResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		}finally {
			if(dq != null) {
				dq.releaseTransaction(null);
			}
		}
		return data;
	}
	
	private void resetSourceResources(){
		CommonUtils.setSourceIds(null);
		CommonUtils.setSourceNames(null);
		CommonUtils.setSourceIdsWithoutEmployeeSource(null);
		CommonUtils.setSourceNamesWithoutEmployeeSource(null);
		RecentlyUsedSources.INSTANCE.setRecentlyUsedSources(null);
	}
	
	
	public ArrayList<SkillData> getSkillListByCategoryIds(String skillCategoryIds) throws SQLException {
		ArrayList<SkillData> skillCategoryData = null;
		DBPreparedQuery dq = null;
		String[] dynParam = new String[1];
		try {
			dynParam[0] = "";
			ArrayList<String> dynamicContent = new ArrayList<String>();
			String qMarks = Utils.setDynamicParamsAndReturnQmarks(skillCategoryIds, dynamicContent);
			dynParam[0] = qMarks;			
			dq = new DBPreparedQuery("dMastersManager_GetSkillsByCategoryIds",dynParam);
			int cnt = 1;
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}
			skillCategoryData =  dq.getResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		} finally {
			if(dq != null) {
				dq.releaseTransaction(null);
			}
		}
		return skillCategoryData;
	}	
	
	public SimpleDataObject getDegreeTypeFromMaster(String degreeId) {
		DBPreparedQuery dq = null;
		SimpleDataObject sDObj = null;
		try {
			dq = new DBPreparedQuery("dMastersManager_GetDegreeTypeFromMaster");
			dq.setId(1, degreeId);
			sDObj = (SimpleDataObject)dq.getSingleObjectResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error While getting Single degree from Master", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return sDObj;
	}
	
	public int  getExistingSourceID(String sourceTitle) {
		DBPreparedQuery dq = null;
		SimpleDataObject aData = null;
		Integer sourceTitleExist=null;
		try {
			dq = new DBPreparedQuery("dApplicantManager_getSourceTitleExisting_ID");
			dq.setString(1, sourceTitle);
			aData = (SimpleDataObject) dq.getSingleObjectResult();
			sourceTitleExist = aData.getInt("source_id");
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting applicant summary", e);

		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return sourceTitleExist;
	}
	
	public static String getDegreeIdByName(String degreeName) {
		String degreeId = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dMasterManager_getDegreeId");
			dq.setString(1, degreeName);
			degreeId = dq.getIdResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return degreeId;
	}
	public ArrayList getAllDegreeTypes() throws MasterExistException {
		DBPreparedQuery dq = null;
		ArrayList employers = null;
		try {
			dq = new DBPreparedQuery("dMastersManager_GetDegreeTypes");
			employers = dq.getResult();
		}catch (Exception e) {
			TPLogger.getLogger().error("Error While getting Budget Grades", e);			
			throw new MasterExistException();
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return employers;
	}
	
	public ArrayList getAllEmployers() throws MasterExistException {
		DBPreparedQuery dq = null;
		ArrayList employers = null;
		try {
			dq = new DBPreparedQuery("dMastersManager_GetEmployers");
			employers = dq.getResult();
		}catch (Exception e) {
			TPLogger.getLogger().error("Error While getting Budget Grades", e);			
			throw new MasterExistException();
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return employers;
	}
}