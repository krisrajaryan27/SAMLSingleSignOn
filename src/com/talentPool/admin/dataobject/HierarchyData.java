/**
 * 
 */
package com.talentPool.admin.dataobject;

import java.util.ArrayList;

import com.talentPool.common.db.SimpleDataObject;

/**
 * @author Ajeet
 *
 */
public class HierarchyData extends SimpleDataObject {
	
	public ArrayList<HierarchyData> getChildrens() {
		return (ArrayList<HierarchyData>) getAttribute("childrens");
	}

	public void setChildrens(ArrayList<HierarchyData> childrens) {
		setAttribute("childrens", childrens);
	}
	
	public int getOrgId() {
		return getInt("orgId");
	}

	public void setOrgId(int orgId) {
		setAttribute("orgId",orgId);
	}
	
	public int getUserId() {
		return getInt("userId");
	}

	public void setUserId(int userId) {
		setAttribute("userId",userId);
	}
	
	public int getParentId() {
		return getInt("parentId");
	}

	public void setParentId(int parentId) {
		setAttribute("parentId",parentId);
	}
	
	public String getName() {
		return getString("name");
	}
	
	public void setName(String name) {
		setAttribute("name", name);
	}

	public String getSupervisorId() {
		return getString("supervisorId");
	}
	
	public String getuserType() {
		return getString("userType");
	}
}
