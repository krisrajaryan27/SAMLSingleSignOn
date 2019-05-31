/**
 * 
 */
package com.talentPool.masters.dataobject;

/**
 * @author shivprasad
 *
 */
public class InstituteData extends MasterData{
	public InstituteData(){
		
	}
	
	public String getAlias(){
		return getString("alias");
	}
	
	public void setAlias(String alias){
		setAttribute("alias",alias);
	}
}
