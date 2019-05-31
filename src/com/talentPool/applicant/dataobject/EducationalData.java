/**
 * 
 */
package com.talentPool.applicant.dataobject;

import java.sql.Date;
import java.util.ArrayList;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.utils.CommonUtils;
import com.talentPool.common.utils.Utils;

/**
 * @author shivprasad
 * 
 */
public class EducationalData extends SimpleDataObject {

	/**
	 * @return Returns the applicantId.
	 */
	public String getApplicantId() {
		return getId("applicantId");
	}

	/**
	 * @param applicantId
	 *            The applicantId to set.
	 */
	public void setApplicantId(String applicantId) {
		setAttribute("applicantId", applicantId);
	}

	/**
	 * @return getString("Returns the degreeId.
	 */
	public int getDegreeId() {
		return getInt("degreeId");
	}

	/**
	 * @param degreeId
	 *            The degreeId to set.
	 */
	public void setDegreeId(int degreeId) {
		setAttribute("degreeId", new Integer(degreeId));
	}

	/**
	 * @return getString("Returns the institute.
	 */
	public String getDegreeTitle() {
		return getString("degreeTitle");
	}

	/**
	 * @param institute
	 *            The institute to set.
	 */
	public void setDegreeTitle(String degreeTitle) {
		setAttribute("degreeTitle", degreeTitle);
	}

	/**
	 * @return getString("Returns the educationalInfoId.
	 */
	public int getEducationalInfoId() {
		return getInt("educationalInfoId");
	}

	/**
	 * @param educationalInfoId
	 *            The educationalInfoId to set.
	 */
	public void setEducationalInfoId(int educationalInfoId) {
		setAttribute("educationalInfoId", new Integer(educationalInfoId));
	}

	/**
	 * @return getString("Returns the institute.
	 */
	public String getInstitute() {
		return getString("institute");
	}

	/**
	 * @param institute
	 *            The institute to set.
	 */
	public void setInstitute(String institute) {
		setAttribute("institute", institute);
	}

	/**
	 * @return getString("Returns the university.
	 */
	public String getUniversity() {
		return getString("university");
	}

	/**
	 * @param university
	 *            The university to set.
	 */
	public void setUniversity(String university) {
		setAttribute("university", university);
	}

	/**
	 * @return getString("Returns the typeOfProgram.
	 */
	public String getTypeOfProgram() {
		return getString("typeOfProgram");
	}

	/**
	 * @param typeOfProgram
	 *            The typeOfProgram to set.
	 */
	public void setTypeOfProgram(String typeOfProgram) {
		setAttribute("typeOfProgram", typeOfProgram);
	}

	public String getInstituteId() {
		return getId("instituteId");
	}

	/**
	 * @param applicantId
	 *            The applicantId to set.
	 */
	public void setInstituteId(String instituteId) {
		setAttribute("instituteId", instituteId);
	}

	/**
	 * @return getString("Returns the major.
	 */
	public String getMajor() {
		return getString("major");
	}

	/**
	 * @param major
	 *            The major to set.
	 */
	public void setMajor(String major) {
		setAttribute("major", major);
	}

	public int getMajorId() {
		return getInt("majorId");
	}

	/**
	 * @param degreeId
	 *            The degreeId to set.
	 */
	public void setMajorId(int majorId) {
		setAttribute("majorId", new Integer(majorId));
	}

	/**
	 * @return getString("Returns the yearOfPassing.
	 */
	public Date getYearOfPassing() {
		return getDate("yearOfPassing");
	}

	/**
	 * @param yearOfPassing
	 *            The yearOfPassing to set.
	 */
	public void setYearOfPassing(Date yearOfPassing) {
		setAttribute("yearOfPassing", yearOfPassing);
	}

	/**
	 * @return getString("Returns the startDate.
	 */
	public Date getStartDate() {
		return getDate("startDate");
	}

	/**
	 * @param startDate
	 *            The startDate to set.
	 */
	public void setStartDate(Date startDate) {
		setAttribute("startDate", startDate);
	}

	/**
	 * @return getString("Returns the endDate.
	 */
	public Date getEndDate() {
		return getDate("endDate");
	}

	/**
	 * @param endDate
	 *            The endDate to set.
	 */
	public void setEndDate(Date endDate) {
		setAttribute("endDate", endDate);
	}

	public void setGrade(String grade) {
		setAttribute("grade", grade);
	}

	public String getGrade() {
		return getString("grade");
	}

	public void setRemarks(String remarks) {
		setAttribute("remarks", remarks);
	}

	public String getRemarks() {
		return getString("remarks");
	}

	public void setFromYear(Date fromYear) {
		setAttribute("fromYear", fromYear);
	}

	public Date getFromYear() {
		return getDate("fromYear");
	}

	public String getFormattedEducation() {
		String degree = getDegreeTitle();
		String inst = getInstitute();
		String major = getMajor();
		Date strtDat = getStartDate();
		Date endDat = getEndDate();
		String university = getUniversity();
		String typeOfProgram = "";
		if(!Utils.isNumeric(getTypeOfProgram())){
			setTypeOfProgram(String.valueOf(CommonUtils.getTypeOfProgramIdByString(getTypeOfProgram())));
		}
		 typeOfProgram = (!Utils.isBlankOrNull(getTypeOfProgram())&&Utils.isNumeric(getTypeOfProgram()))?CommonUtils.getTypeOfProgramById(Integer.parseInt(getTypeOfProgram())):"";
		
		String grade = getGrade();
		StringBuffer sb = new StringBuffer();
		try {
			if (!Utils.isBlankOrNull(degree)) {
				sb.append(degree);
			}
			boolean brackets = false;
			if (!Utils.isBlankOrNull(inst) || !Utils.isBlankOrNull(major) || !Utils.isBlankOrNull(university)
					|| !Utils.isBlankOrNull(typeOfProgram) || strtDat != null || endDat != null) {
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
			if (!Utils.isBlankOrNull(university)) {
				sb.append(", ");
				sb.append(university);
			}
			if (strtDat != null) {
				sb.append(", ");
				sb.append(Utils.getDateConvertedToString(strtDat, Utils.regYYYYFormat));
			}
			if (endDat != null) {
				sb.append(" - ");
				sb.append(Utils.getDateConvertedToString(endDat, Utils.regYYYYFormat));
			}
			if (!Utils.isBlankOrNull(typeOfProgram)) {
				sb.append(", ");
				sb.append(typeOfProgram);
			}
			if (!Utils.isBlankOrNull(grade)) {
				sb.append(", ");
				sb.append(grade);
			}
			if (brackets)
				sb.append(" )");

		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
		}
		return sb.toString().trim();

	}
}
