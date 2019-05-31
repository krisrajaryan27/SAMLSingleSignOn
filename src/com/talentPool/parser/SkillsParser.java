/**
 * 
 */
package com.talentPool.parser;

import java.util.ArrayList;
import java.util.Vector;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBQuery;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.utils.CommonUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.masters.dataobject.SkillAliasData;
import com.talentPool.masters.dataobject.SkillData;
import com.talentPool.parser.regex.Skill;
import com.talentPool.parser.regex.SkillMatch;
import com.talentPool.parser.utils.ParserUtils;

/**
 * @author shivprasad
 * 
 */
public class SkillsParser {
	public ArrayList<String> getArrayListOfParsedSkillIdsAndNames(String rawSkills, int noOfTopSkills) {
		ArrayList<String> idsAndNames = new ArrayList<String>();
		try {
			ArrayList skills = getParsedSkillsFromMaster(rawSkills, noOfTopSkills);
			ArrayList<String> ids = new ArrayList<String>();
			ArrayList<String> names = new ArrayList<String>();
			CommonUtils.populateIdsAndNames(skills, ids, names, "itemId", "itemName", null);
			String strIds = Utils.convertArrayListIntoCommaSptdString(ids);
			String strNames = Utils.convertArrayListIntoCommaSptdString(names);
			idsAndNames.add(0, strIds);
			idsAndNames.add(1, strNames);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error",e);
		}
		return idsAndNames;
	}

	public ArrayList<SkillData> getParsedSkillsFromMaster(String rawSkills, int noOfTopSkills) {
		ArrayList<SkillData> skills = new ArrayList<SkillData>();
		try {
			ArrayList preDefinedSkills = getSkillsWithAliases();
			// rawSkills = rawSkills.toLowerCase();
			SkillMatch skillMatch = new SkillMatch();
			if (preDefinedSkills != null) {
				for (int i = 0; i < preDefinedSkills.size(); i++) {
					SkillData skillData = (SkillData) preDefinedSkills.get(i);
					ArrayList<String> skillREs = new ArrayList<String>();
					skillREs.add(ParserUtils.getREConstructed(skillData.getItemName()));
					ArrayList aliases = skillData.getAliases();
					if (aliases != null && aliases.size() > 0) {
						for (int k = 0; k < aliases.size(); k++) {
							SkillAliasData aliasData = (SkillAliasData) aliases.get(k);
							skillREs.add(ParserUtils.getREConstructed(aliasData.getItemName()));
						}
					}

					skillMatch.addSkill(skillREs, skillData.getItemId(), skillData.getItemName());
				}
				Vector vSkills = skillMatch.matchSkills(rawSkills, noOfTopSkills);
				if (vSkills != null && vSkills.size() > 0) {
					for (int i = 0; i < vSkills.size(); i++) {
						Skill regSkill = (Skill) vSkills.get(i);
						SkillData skillData = new SkillData();
						skillData.setItemId("" + regSkill.getId());
						skillData.setItemName(regSkill.getName());
						skills.add(skillData);
					}
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while Extracting Skills", e);
		}
		return skills;
	}

	public ArrayList getSkillsWithAliases() {
		DBQuery dq = null;
		ArrayList<SkillData> skills = null;
		try {
			// dq = new DBQuery("dGetAllSkillsWithAliases");
			dq = new DBQuery("dSkillsParser_GetSkillsWithAliases");
			ArrayList rawSkills = dq.getResult();
			if (rawSkills != null && rawSkills.size() > 0) {
				skills = new ArrayList<SkillData>();
				String prevSkillId = "";
				SkillData skillData = null;
				// PositionSkillsData pSkillData = null;
				for (int i = 0; i < rawSkills.size(); i++) {
					SimpleDataObject sDo = (SimpleDataObject) rawSkills.get(i);
					String skillId = sDo.getString("skillId");
					String skillName = sDo.getString("skillName");
					String skillAlias = sDo.getString("skillAlias");
					ArrayList aliases = null;
					if (skillId.equals(prevSkillId)) {
						aliases = skillData.getAliases();
					} else {
						if (skillData != null) {
							skills.add(skillData);
						}
						skillData = new SkillData();
						skillData.setItemId(skillId);
						skillData.setItemName(skillName);
						aliases = new ArrayList();
						prevSkillId = skillId;
					}
					if (!Utils.isBlankOrNull(skillAlias)) {
						SkillAliasData aliasData = new SkillAliasData();
						aliasData.setItemId(skillId);
						aliasData.setItemName(skillAlias);
						aliases.add(aliasData);
					}
					skillData.setAliases(aliases);

					if (i == rawSkills.size() - 1) {
						skills.add(skillData);
					}
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting skills list", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return skills;
	}

}
