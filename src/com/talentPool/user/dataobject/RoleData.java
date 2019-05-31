/**
 * 
 */
package com.talentPool.user.dataobject;

import com.talentPool.common.db.SimpleDataObject;



/**
 * @author shivprasad
 *
 */
public class RoleData extends SimpleDataObject{
    /** Creates a new instance of RoleData */
    public RoleData() {
    }
    
    public RoleData(int roleId) {
    	setRoleId(roleId);
    }
    
    public void setRoleId(int roleId){
        setAttribute("roleId", new Integer(roleId));
    }
    
    public int getRoleId(){
        return getInt("roleId");
    }
    
    public void setRoleTitle(String roleTitle){
        setAttribute("roleTitle", roleTitle);
    }
    
    public String getRoleTitle(){
        return getString("roleTitle");
    }
}
