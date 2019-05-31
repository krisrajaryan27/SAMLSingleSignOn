package com.talentPool.masters.dataobject;
import java.util.ArrayList;

import com.talentPool.common.db.SimpleDataObject;

public class MasterData extends SimpleDataObject{
	
	public MasterData(){
		
	}
    
	public String getItemName(){
		return getString("itemName");
	}
	
	public void setItemName(String itemName){
		setAttribute("itemName",itemName);
	}
	
	public int getItemId(){
		return getInt("itemId");
	}
	
	public void setItemId(String itemId){
		setAttribute("itemId",itemId);
	}
	
	public ArrayList getAliases(){
		return (ArrayList)getAttribute("aliases");
	}
	
	public void setAliases(ArrayList aliases){
		setAttribute("aliases", aliases);
	}

}
