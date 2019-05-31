package com.talentPool.employeeservice.dataobject;


import java.util.ArrayList;
import java.util.Map;

/**
 * @author praveen
 *
 */
public class EpositionFilters {
	private ArrayList<EpositionSkills> skillsList = null;
	private Map<String,String> applyReferOptions;

	/**
	 * @return the skillsList
	 */
	public ArrayList<EpositionSkills> getSkillsList() {
		return skillsList;
	}

	/**
	 * @param skillsList the skillsList to set
	 */
	public void setSkillsList(ArrayList<EpositionSkills> skillsList) {
		this.skillsList = skillsList;
	}

	/**
	 * @return the applyReferOptions
	 */
	public Map<String, String> getApplyReferOptions() {
		return applyReferOptions;
	}

	/**
	 * @param applyReferOptions the applyReferOptions to set
	 */
	public void setApplyReferOptions(Map<String, String> applyReferOptions) {
		this.applyReferOptions = applyReferOptions;
	}

}
