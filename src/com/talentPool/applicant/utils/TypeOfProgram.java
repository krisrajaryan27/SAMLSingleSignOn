package com.talentPool.applicant.utils;

public enum TypeOfProgram {
 FULL_TIME(0,"Full Time"),PART_TIME(1,"Part Time"),DISTANCE(2,"distance");
	private TypeOfProgram(int id, String program) {
	this.id = id;
	this.program = program;
}
	int id;
	String program;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProgram() {
		return program;
	}
	public void setProgram(String program) {
		this.program = program;
	}
	
	public static  String getProgramByID(int id){
		switch(id) {
		case 0:
			return FULL_TIME.getProgram();
		case 1:
			return PART_TIME.getProgram();
		case 2:
			return DISTANCE.getProgram();
			default:
				return "";
		}
	}
	
}
