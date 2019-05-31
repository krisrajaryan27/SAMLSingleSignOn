package com.talentPool.parser.regex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.Iterator;
import java.util.Vector;

public class SkillMatch {
	HashMap idToSkillREs;
	HashMap idToSkillNames;
	TreeSet sortedOccs;

	public SkillMatch() {
		idToSkillREs = new HashMap();
		idToSkillNames = new HashMap();
		sortedOccs = new TreeSet();
	}

	/* Add reg exp for a skill */
	public void addSkill(ArrayList skillRE, int skillId, String skillName) {
		idToSkillREs.put(new Integer(skillId), skillRE);
		idToSkillNames.put(new Integer(skillId), skillName);
	}

	/*
	 * Search for most frequently occurring k skills and return an array of k Skill objects meeting the criterion if k<0 it will return all skills extracted
	 */

	public Vector matchSkills(CharSequence strBuff, int k) {
		Iterator idIt = idToSkillREs.keySet().iterator();
		while (idIt.hasNext()) {
			Integer id = (Integer) idIt.next();
			ArrayList arrSkillRE = (ArrayList) idToSkillREs.get(id);

			REMatch reMatch = new REMatch();
			for (int i = 0; i < arrSkillRE.size(); i++) {
				reMatch.addRE((String) arrSkillRE.get(i));
			}

			int matchCnt = reMatch.matchInString(strBuff).size();
			if (matchCnt > 0) {
				String skillName = (String) idToSkillNames.get(id);
				Skill sk = new Skill(id.intValue(), skillName, matchCnt);
				sortedOccs.add(sk);
			}
		}
		Iterator sortIt = sortedOccs.iterator();
		int collected = 0;
		Vector mostFreqSkills = new Vector();
		if(k<0){		
			k = sortedOccs.size();
		}
		if (k > 0) {
			while (sortIt.hasNext() && collected < k) {
				Skill sk = (Skill) sortIt.next();
				mostFreqSkills.add(sk);
				collected++;
			}
		} 
		return mostFreqSkills;
	}
}
