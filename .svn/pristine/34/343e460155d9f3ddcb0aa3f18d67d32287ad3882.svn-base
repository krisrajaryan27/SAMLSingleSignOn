package com.talentPool.notifier;

public enum CandidateStateTemplateEnums {
	APPLIED("applied"), UPDATED("updated"), ADDED("added");

	private final String name;

	private CandidateStateTemplateEnums(String s) {
		name = s;
	}
	public boolean equalsName(String otherName) {
        return (otherName == null) ? false : name.equals(otherName);
    }

    public String toString() {
       return this.name;
    }
    
    public static CandidateStateTemplateEnums findByName(String name){
    	for(CandidateStateTemplateEnums c: values()){
    		if(c.equalsName(name)){
    			return c;
    		}
    	}
    	return null;
    }
}
