/**
 * 
 */
package com.talentPool.admin.manager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.talentPool.admin.dataobject.HierarchyData;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.DBTransaction;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.user.UserConstants;

/**
 * @author Ajeet
 * 
 */
public class HierarchyManager {

	public String getHierarchy() {
		String str = "";
		try {
			ArrayList<HierarchyData> hierarchy = getHierarchyData();
			hierarchy = arrangeParentItems(hierarchy);
			int level = 0;
			str = printH(hierarchy, str, level);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return str;
	}

	public String printH(ArrayList<HierarchyData> hierarchy, String str, int level) {
		for (int i = 0; i < hierarchy.size(); i++) {

			HierarchyData data = hierarchy.get(i);
			String table = "<table><tr>";
			for (int j = 0; j < level; j++) {
				table += "<td style=\"border-left: dotted;border-left-width: 1px;border-left-color: #999;\"";
				table += ">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>";
			}
			table += "<td style=\"border-left: dotted;border-left-width: 1px;border-left-color: #999;border-bottom-color: #999; border-bottom: dotted;border-bottom-width: 1px; \">" + "&nbsp;" + data.getName() + "</td></tr></table>";
			str += table;
			if (data.getChildrens() != null) {
				int childlevel = level + 1;
				str = printH(data.getChildrens(), str, childlevel);
			}
		}
		return str;
	}

	public ArrayList<HierarchyData> getHierarchyData() {
		DBPreparedQuery dq = null;
		ArrayList<HierarchyData> hData = null;
		try {
			dq = new DBPreparedQuery("dHierarchyManager_FetchUsersHierarchy");
			hData = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return hData;
	}

	public static ArrayList<HierarchyData> arrangeParentItems(ArrayList<HierarchyData> units) {
		ArrayList<HierarchyData> arranged = new ArrayList<HierarchyData>();
		if (units != null) {
			while (units.size() > 0) {
				for (int i = 0; i < units.size(); i++) {
					HierarchyData data = units.get(i);
					HierarchyData parentData = getParentData(arranged, data.getParentId());
					if (parentData == null) {
						if (data.getParentId() == UserConstants.USER_TOP_PARENT) {
							arranged.add(data);
							units.remove(i);
							i = i - 1;
						}
					} else {
						ArrayList<HierarchyData> childrens = parentData.getChildrens();
						if (childrens == null) {
							childrens = new ArrayList<HierarchyData>();
						}
						childrens.add(data);
						parentData.setChildrens(childrens);
						units.remove(i);
						i = i - 1;
					}
				}
			}
		}
		return arranged;
	}

	public static HierarchyData getParentData(ArrayList<HierarchyData> arranged, int parentId) {
		if (parentId != UserConstants.USER_TOP_PARENT) {
			for (int i = 0; i < arranged.size(); i++) {
				HierarchyData data = arranged.get(i);
				if (data.getUserId() == parentId) {
					return data;
				} else {
					ArrayList<HierarchyData> childrens = data.getChildrens();
					if (childrens != null) {
						HierarchyData child = getParentData(childrens, parentId);
						if (child != null) {
							return child;
						}
					}
				}
			}
		}
		return null;
	}

	public ArrayList<HierarchyData> getUnManagedUser() {
		DBPreparedQuery dq = null;
		ArrayList<HierarchyData> unManagedUser = null;
		try {
			dq = new DBPreparedQuery("dHierarchyManager_FetchUnManagedUser");
			dq.setInt(1, UserConstants.ACTIVE);
			unManagedUser = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return unManagedUser;
	}

	public ArrayList<HierarchyData> getManagedUser() {
		DBPreparedQuery dq = null;
		ArrayList<HierarchyData> managedUser = null;
		try {
			dq = new DBPreparedQuery("dHierarchyManager_FetchManagedUser");
			dq.setInt(1, UserConstants.ACTIVE);
			managedUser = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return managedUser;
	}

	public void saveUserToHierarchy(String userId, String parentId, DBTransaction tran) {
		DBPreparedQuery dq = null;
		try {
			boolean commit = false;
			if(tran==null){
				tran = new DBTransaction();
				commit = true;
			}
			dq = new DBPreparedQuery("dHierarchyManager_InsertUserToHierarchy",tran);
			dq.setId(1, userId);
			dq.setId(2, parentId);
			dq.execute();
			
			if(commit){
				tran.commit();
			}
			
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}finally {
			if (dq != null) {
				if (tran == null) {
					dq.releaseConnection();
				} else {
					dq.closeOpenCursors();
				}

			}
		}
	}

	public void saveMovedUserToHierarchy(String userId, String parentId) {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dHierarchyManager_UpdateUserToHierarchy");
			dq.setId(1, parentId);
			dq.setId(2, userId);
			dq.execute();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}

	public ArrayList<HierarchyData> getRemovableUsers() {
		DBPreparedQuery dq = null;
		ArrayList<HierarchyData> removableUsers = null;
		try {
			dq = new DBPreparedQuery("dHierarchyManager_FetchRemovableUser");
			removableUsers = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return removableUsers;
	}

	public void deleteUserFromHierarchy(String orgId) {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dHierarchyManager_DeleteUserFromHierarchy");
			dq.setId(1, orgId);
			dq.execute();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}

	public ArrayList<HierarchyData> getXMLAssignToUser(String userId) {
		ArrayList<HierarchyData> hierarchy = getHierarchyData();
		hierarchy = arrangeParentItems(hierarchy);
		hierarchy = removeChildrenFor(hierarchy, userId);
		
		ArrayList<HierarchyData> arranged = new ArrayList<HierarchyData>();
		arranged = getArrangedHierarchy(hierarchy, arranged);
		return arranged;
	}

	public ArrayList<HierarchyData> removeChildrenFor(ArrayList<HierarchyData> hierarchy, String userId) {
		if (hierarchy != null) {
			for (int i = 0; i < hierarchy.size(); i++) {
				HierarchyData hData = hierarchy.get(i);
				if (userId.equals("" + hData.getUserId())) {
					hierarchy.remove(i);
					i--;
				} else if (hData.getChildrens() != null) {
					ArrayList<HierarchyData> childrens = removeChildrenFor(hData.getChildrens(), userId);
					hData.setChildrens(childrens);
				}
			}
		}		
		return hierarchy;
	}

	public ArrayList<HierarchyData> getArrangedHierarchy(ArrayList<HierarchyData> hierarchy, ArrayList<HierarchyData> arranged) {
		if (hierarchy != null) {
			for (int i = 0; i < hierarchy.size(); i++) {
				HierarchyData data = hierarchy.get(i);
				arranged.add(data);
				if (data.getChildrens() != null) {
					arranged = getArrangedHierarchy(data.getChildrens(), arranged);
				}
			}
		}
		return arranged;
	}

	public String getParentId(String userId) {
		DBPreparedQuery dq = null;
		String parentId = "";
		try {
			dq = new DBPreparedQuery("dHierarchyManager_GetParentId");
			dq.setId(1, userId);
			parentId = dq.getIdResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return parentId;
	}

	private List<String> getAllAncestorsForANode(ArrayList<HierarchyData> hierarchy, String node) {
		List<String> nodeAncestors = new ArrayList<String>();
		if(hierarchy != null && hierarchy.size() > 0 && !Utils.isBlankOrNull(node)) {
			String searchNode = node;
			boolean parentFound = true;
			while(!String.valueOf(UserConstants.USER_TOP_PARENT).equalsIgnoreCase(searchNode) && parentFound) {
				parentFound = false;
				Iterator<HierarchyData> itr = hierarchy.iterator();
				while(itr.hasNext()) {
					HierarchyData data = itr.next();
					if(searchNode.equalsIgnoreCase(String.valueOf(data.getUserId()))) {
						String parentId = String.valueOf(data.getParentId());
						nodeAncestors.add(parentId);
						searchNode = parentId;
						parentFound = true;
						break;
					}
				}
			}
		}
		return nodeAncestors;
	}
	
	public String getAllAncestorsForNodes(String nodeIds) {
		String returnVal = null;
		StringBuffer sb = new StringBuffer();
		if(!Utils.isBlankOrNull(nodeIds)) {
			ArrayList<HierarchyData> hierarchy = getHierarchyData();
			String[] nodes = nodeIds.split(",");
			for(int i = 0; i < nodes.length; i++) {
				if(sb.length() > 0) {
					sb.append(",");
				}
				sb.append(nodes[i]);
				List<String> nodeAncestors = getAllAncestorsForANode(hierarchy, nodes[i]);
				if(nodeAncestors != null && nodeAncestors.size() > 0) {
					for(int k = 0; k < nodeAncestors.size(); k++) {
						sb.append("_");
						sb.append(nodeAncestors.get(k));
					}
				}				
			}
		}		
		returnVal = sb.toString();
		return returnVal;
	}
	
	public String getAllAncestorsForNode(String nodeId, String removedUserId) {
		String returnVal = null;
		StringBuffer sb = new StringBuffer();
		if(!Utils.isBlankOrNull(nodeId)) {
			ArrayList<HierarchyData> hierarchy = getHierarchyData();
			List<String> nodeAncestors = getAllAncestorsForANode(hierarchy, nodeId);
			if(nodeAncestors != null && nodeAncestors.size() > 0) {
				sb.append(nodeId);
				for(int k = 0; k < nodeAncestors.size(); k++) {
					if(!nodeAncestors.get(k).equals(removedUserId)) {
						if(sb.length() > 0) {
							sb.append("_");
						}					
						sb.append(nodeAncestors.get(k));
					}					
				}
			}	
		}		
		returnVal = sb.toString();
		return returnVal;
	}
	
	public String getAllChildrens(String userId){
		ArrayList<HierarchyData> hierarchy = getHierarchyData();
		hierarchy = arrangeParentItems(hierarchy);
		hierarchy = getChildrenFor(hierarchy, userId);
		
		String users = "";
		if(hierarchy!=null && hierarchy.size()>0){
			ArrayList<HierarchyData> arranged = new ArrayList<HierarchyData>();
			arranged = getArrangedChildHierarchy(hierarchy, arranged);
			for (int i = 0; arranged!=null && i < arranged.size(); i++) {
				HierarchyData hData = arranged.get(i);
				if(Utils.isBlankOrNull(users)){
					users = ""+ hData.getUserId();
				}else{
					users +=","+hData.getUserId();
				}				
			}
		}		
		return users;
	}

	public ArrayList<HierarchyData> getChildrenFor(ArrayList<HierarchyData> hierarchy, String userId) {
		ArrayList<HierarchyData> childHierarchy = new ArrayList<HierarchyData>();
		if (hierarchy != null) {
			for (int i = 0; i < hierarchy.size(); i++) {
				HierarchyData hData = hierarchy.get(i);
				if (userId.equals("" + hData.getParentId())) {
					childHierarchy.add(hData);
				} else if (hData.getChildrens() != null) {
					childHierarchy.addAll(getChildrenFor(hData.getChildrens(), userId));
				}
			}
		}		
		return childHierarchy;
	}

	public ArrayList<HierarchyData> getArrangedChildHierarchy(ArrayList<HierarchyData> hierarchy, ArrayList<HierarchyData> arranged) {
		if (hierarchy != null) {
			for (int i = 0; i < hierarchy.size(); i++) {
				HierarchyData data = hierarchy.get(i);
				arranged.add(data);
				if (data.getChildrens() != null) {
					arranged = getArrangedHierarchy(data.getChildrens(), arranged);
				}
			}
		}
		return arranged;
	}

	public ArrayList<HierarchyData> getHierarchyData(String processId) {
		DBPreparedQuery dq = null;
		ArrayList<HierarchyData> hierarchyList = null;
		try {
			dq = new DBPreparedQuery("dHierarchyManager_GetHierarchyData");
			dq.setString(1, processId);
			hierarchyList = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return hierarchyList;
	}
	
	public void removeUserFromHierarchy(String parentId, String userId) {
		DBTransaction tran = null;
		DBPreparedQuery dq = null;
		try {
			tran = new DBTransaction();
			
			dq = new DBPreparedQuery("dHierarchyManager_UpdateParent", tran);
			dq.setString(1, parentId);
			dq.setString(2, userId);
			dq.execute();
			
			dq = new DBPreparedQuery("dHierarchyManager_RemoveUser", tran);
			dq.setString(1, userId);
			dq.execute();
			
			tran.commit();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			try {
				tran.rollback();
			} catch (SQLException e1) {
				TPLogger.getLogger().error(GlobalConstants.ERROR, e1);
			}
		} finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}
		}
	}
}
