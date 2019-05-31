/**
 * 
 */
package com.talentPool.miscutils.action;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.base.TPDispatchAction;
import com.talentPool.common.db.EncyptRecord;
import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.common.utils.DBUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.masters.action.MasterActionForAP;
import com.talentPool.miscutils.manager.MiscUtilsManager;
import com.talentPool.positions.action.PositionActionForAP;

/**
 * @author shivprasad
 * 
 */
public class MiscUtilsAction extends TPDispatchAction {
	public ActionForward cleanDocuments(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		try {
			MiscUtilsManager.cleanDocumentsFolder();
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return mapping.findForward("login");
	}
	
	public ActionForward updatetextresume(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		try {
			MiscUtilsManager.updatetextresume();
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return mapping.findForward("login");
	}
	public ActionForward encyptTableRecord(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		try {
			EncyptRecord.encyptTableRecord();
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return mapping.findForward("login");
	}
	
	public ActionForward createDemoDB(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward =  "";
		try {
			String dbNameSelectedFromPage =request.getParameter("dbName");
			String basePath = Utils.concatFilePath(TPApplicationProperties.getProperty("application.path"), TPApplicationProperties.getProperty("ui.dir"));
			String pathToDBScript = Utils.concatFilePath(basePath, "demoDBScript");
			List<File> files = (List<File>) FileUtils.listFiles(new File(pathToDBScript), TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
			StringBuilder dbNameList = new StringBuilder();
			for(File f:files){
				String tmp = f.getName();
				if(!Utils.isBlankOrNull(dbNameList.toString())){
					tmp = "||" +tmp;
				}
				dbNameList.append(tmp);
			}
			request.setAttribute("myList", dbNameList.toString());
			String dburl = TPApplicationProperties.getProperty("db.url");
			String userName = DBUtils.decryptString(TPApplicationProperties.getProperty("db.username"));
			String password = DBUtils.decryptString( TPApplicationProperties.getProperty("db.password"));
			if(!Utils.isBlankOrNull(dbNameSelectedFromPage)){
				pathToDBScript+="/" + dbNameSelectedFromPage;
				String[] tmp = FilenameUtils.getBaseName(dburl).split("\\?");
				String dbName = tmp[0];
				String script = "cmd.exe /c start cmd.exe /c \""+TPApplicationProperties.getProperty("mysql.installation.path")+" -u"+userName+" -p"+password+" "+dbName+" < "+pathToDBScript+"\"";
				Process p =  Runtime.getRuntime().exec(script);
				p.waitFor();
				request.setAttribute("dbNameUpdated", dbNameSelectedFromPage);
			}
			String flagForIncrementDB = request.getParameter("dayCntFlag");
			if(!Utils.isBlankOrNull(flagForIncrementDB)){
				String dayCnt = request.getParameter("dayCnt");
				databaseDateTimeModifier(dburl, userName, password, Integer.parseInt(dayCnt));
				request.setAttribute("timeincrementedSuccess","1");
			}
			forward = "demoDB";
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return mapping.findForward(forward);
	}
	
	private void databaseDateTimeModifier(String dbUrl, String userName, String password,int incrementDayCount){
		Connection connection = null;
		try{
			Object object;
			 connection= DriverManager.getConnection(dbUrl, userName, password);
	        String string2 = null;
	        String string3 = null;
	        String string4 = null;
	        boolean bl = false;
	        String[] arrstring2 = new String[]{"TABLE"};
	        DatabaseMetaData databaseMetaData = connection.getMetaData();
	        ResultSet resultSet = databaseMetaData.getTables(null, userName, "%", arrstring2);
	        Statement statement = connection.createStatement();
	        String string5 = "update {table-name} set {column-name} = timestampadd(DAY," + incrementDayCount + ",{column-name})";
	        String string6 = "";
	        while (resultSet.next()) {
	            bl = true;
	            string4 = resultSet.getString("TABLE_NAME");
	            object = databaseMetaData.getColumns(null, userName, string4, null);
	            while (((ResultSet) object).next()) {
	                string2 = ((ResultSet) object).getString("COLUMN_NAME");
	                string3 = ((ResultSet) object).getString("TYPE_NAME");
	                if (!string3.contains("TIMESTAMP") || Utils.isBlankOrNull(string2)) continue;
	                String string7 = string5.replaceAll("\\{table-name\\}", string4);
	                string7 = string7.replaceAll("\\{column-name\\}", string2);
	                string6 = string6 + string7;
	                statement.addBatch(string7);
	            }
	        }
	        statement.executeBatch();
	        System.out.println("Successfully updated the database");
	        
		}catch(Exception e){
			TPLogger.getLogger().error("error incrementing database date",e);
		}finally{
			if(connection!=null){
				try {
					connection.close();
				} catch (SQLException e) {
					TPLogger.getLogger().error("error closing database connection while incrementing database date",e);
				}
			}
		}
	}
	public ActionForward syncMasterDataForAsianPaints(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		try {
			MasterActionForAP.syncMasterDataForAP();
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return mapping.findForward("login");
	}
	
	public ActionForward syncPositionMasterDataForAsianPaints(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		try {
			PositionActionForAP.syncPositionDataForAP();
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return mapping.findForward("login");
	}
}
