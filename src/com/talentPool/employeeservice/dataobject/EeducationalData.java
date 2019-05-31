/**
 * 
 */
package com.talentPool.employeeservice.dataobject;

import java.util.Date;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.utils.Utils;

/**
 * @author Shantanu
 *
 */
public class EeducationalData {
	private String degreeTitle;
	private String major;
	private String institute;
	private Date yearOfPassing;
	private String grade;
	private Date fromYear;
	private String remarks;
	
	

	public Date getFromYear() {
		return fromYear;
	}

	public void setFromYear(Date fromYear) {
		this.fromYear = fromYear;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the degreeTitle
	 */
	public String getDegreeTitle() {
		return degreeTitle;
	}

	/**
	 * @param degreeTitle
	 *            the degreeTitle to set
	 */
	public void setDegreeTitle(String degreeTitle) {
		this.degreeTitle = degreeTitle;
	}

	/**
	 * @return the grade
	 */
	public String getGrade() {
		return grade;
	}

	/**
	 * @param grade
	 *            the grade to set
	 */
	public void setGrade(String grade) {
		this.grade = grade;
	}

	/**
	 * @return the institute
	 */
	public String getInstitute() {
		return institute;
	}

	/**
	 * @param institute
	 *            the institute to set
	 */
	public void setInstitute(String institute) {
		this.institute = institute;
	}

	/**
	 * @return the major
	 */
	public String getMajor() {
		return major;
	}

	/**
	 * @param major
	 *            the major to set
	 */
	public void setMajor(String major) {
		this.major = major;
	}

	/**
	 * @return the yearOfPassing
	 */
	public Date getYearOfPassing() {
		return yearOfPassing;
	}

	/**
	 * @param yearOfPassing
	 *            the yearOfPassing to set
	 */
	public void setYearOfPassing(Date yearOfPassing) {
		this.yearOfPassing = yearOfPassing;
	}

	public String getFormattedEducation() {
		String degree = getDegreeTitle();
		String inst = getInstitute();
		String major = getMajor();
		Date yop = getYearOfPassing();
		String grade = getGrade();
		String remarks = getRemarks();
		Date fromYear = getFromYear();
		StringBuffer sb = new StringBuffer();
		try {
			if (!Utils.isBlankOrNull(degree)) {
				sb.append(degree);
			}

			boolean brackets = false;
			if (!Utils.isBlankOrNull(inst) || !Utils.isBlankOrNull(major) || yop != null) {
				brackets = true;
			}
			if (brackets)
				sb.append(" ( ");
			if (!Utils.isBlankOrNull(inst)) {
				sb.append(inst);
			}
			if (!Utils.isBlankOrNull(major)) {
				sb.append(", ");
				sb.append(major);
			}
			if(fromYear !=null){
				sb.append(", ");
				sb.append(Utils.getDateConvertedToString(fromYear, Utils.regYYYYFormat));
			}
			if (yop != null) {
				sb.append("-");
				sb.append(Utils.getDateConvertedToString(yop, Utils.regYYYYFormat));
			}
			if (brackets)
				sb.append(" )");

			if (!Utils.isBlankOrNull(grade)) {
				sb.append(" ");
				sb.append(grade);
			}
			if(!Utils.isBlankOrNull(remarks)){
				sb.append(" Remarks: ");
				sb.append(remarks);
			}

		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
		}
		return sb.toString().trim();

	}
}
