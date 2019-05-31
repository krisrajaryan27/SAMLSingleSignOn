package com.talentPool.masters.dataobject;

import java.util.ArrayList;

public class SourceTypeData extends MasterData {
	
	public SourceTypeData(){
		
	}
    
	 public ArrayList getSources(){
		  return (ArrayList)getAttribute("sources");
	  }
	  
	
	  public void setSources(ArrayList sources){
		  setAttribute("sources",sources);	
	  }
	  
	  public String getSystemGenerated(){
		  return getString("systemGenerated");
	  }
	  
	  public void setSystemGenerated(String systemGenerated){
		  setAttribute("systemGenerated",systemGenerated);	
	  }
	  
	  public String getSourceTypeId(){
		  return getString("sourceTypeId");
	  }
	  
	
	  public void setSourceCvLimit(String sourceCvLimit){
		  setAttribute("sourceCvLimit",sourceCvLimit);	
	  }
	  public String getSourceCvLimit(){
		  return getString("sourceCvLimit");
	  }
	  
	
	  public void setSourceTypeId(String sourceTypeId){
		  setAttribute("sourceTypeId",sourceTypeId);	
	  }
	  public String getSourceType(){
		  return getString("sourceType");
	  }
	  
	
	  public void setSourcesType(String sourceType){
		  setAttribute("sourceType",sourceType);	
	  }
	  
	  public String getSourceTypeCategory(){
		  return getString("sourceTypeCategory");
	  }
	  
	
	  public void setSourceTypeCategory(String sourceTypeCategory){
		  setAttribute("sourceTypeCategory",sourceTypeCategory);	
	  }
	  
}
