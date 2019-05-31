package com.talentPool.masters.dataobject;

public class SkillData extends MasterData {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SkillData() {

	}

	/**
	 * @return the skillId
	 */
	public String getSkillId() {
		return getString("skillId");
	}

	/**
	 * @param skillId
	 *            the skillId to set
	 */
	public void setSkillId(String skillId) {
		setAttribute("skillId", skillId);
	}

	/**
	 * @return the skillName
	 */
	public String getSkillName() {
		return getString("skillName");
	}

	/**
	 * @param skillName
	 *            the skillName to set
	 */
	public void setSkillName(String skillName) {
		setAttribute("skillName", skillName);
	}

	/**
	 * @return the skillCategoryId
	 */
	public String getSkillCategoryId() {
		return getString("skillCategoryId");
	}

	/**
	 * @param skillCategoryId
	 *            the skillCategoryId to set
	 */
	public void setSkillCategoryId(String skillCategoryId) {
		setAttribute("skillCategoryId", skillCategoryId);
	}

	/**
	 * @return the skillCategoryName
	 */
	public String getSkillCategoryName() {
		return getString("skillCategoryName");
	}

	/**
	 * @param skillCategoryName
	 *            the skillCategoryName to set
	 */
	public void setSkillCategoryName(String skillCategoryName) {
		setAttribute("skillCategoryName", skillCategoryName);
	}

	/**
	 * @return the skillAlias
	 */
	public String getSkillAlias() {
		return getString("skillAlias");
	}

	/**
	 * @param skillAlias
	 *            the skillAlias to set
	 */
	public void setSkillAlias(String skillAlias) {
		setAttribute("skillAlias", skillAlias);
	}

}