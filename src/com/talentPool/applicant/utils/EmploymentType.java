package com.talentPool.applicant.utils;

public enum EmploymentType {
	
	 FULL_TIME(0,"Full Time"),CONTRACT(1,"Contract"),PART_TIME(2,"Part Time"),TRAINEE(3,"Trainee");
		private EmploymentType(int id, String empType) {
		this.id = id;
		this.empType = empType;
	}
		int id;
		String empType;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getEmpType() {
			return empType;
		}
		public void setEmpType(String empType) {
			this.empType = empType;
		}
		
		public static  String getProgramByID(int id){
			switch(id) {
			case 0:
				return FULL_TIME.getEmpType();
			case 1:
				return CONTRACT.getEmpType();
			case 2:
				return PART_TIME.getEmpType();
			case 3:
				return TRAINEE.getEmpType();	
				default:
					return "";
			}
		}
	}
