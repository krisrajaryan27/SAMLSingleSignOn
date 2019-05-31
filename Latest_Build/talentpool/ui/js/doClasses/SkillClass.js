//class for skills
function Skills(){
this.skillId;
this.skillName;
this.skillType;
this.skillCategoryId;
this.setSkillId = setSkillId;
this.setSkillName = setSkillName;
this.setSkillType = setSkillType;
this.setSkillCategoryId = setSkillCategoryId;
}

function Skills(skillId,skillName){
	this.skillId=skillId;
	this.skillName=skillName;
}

function setSkillId(skillId){
	this.skillId=skillId;
}

function setSkillName(skillName){
	this.skillName=skillName;
}

function setSkillType(skillType){
	this.skillType = skillType;
}

function setSkillCategoryId(skillCategoryId){
	this.skillCategoryId = skillCategoryId;
}

//class for skill category

function SkillCategory(){
this.skillCategoryId;
this.skillCategogyDescription;
this.skillsArray;
this.setSkillCategoryId=setSkillCategoryId;
this.setSkillCategogyDescription=setSkillCategogyDescription;
this.setSkillsArray = setSkillsArray;
this.toString=toCatString;
}

function SkillCategory(skillCategoryId, skillCategogyDescription, skillsArray){
	this.skillCategoryId = skillCategoryId;
	this.skillCategogyDescription = skillCategogyDescription
	this.skillsArray = skillsArray;
}

function setSkillCategoryId(skillCategoryId){
	this.skillCategoryId = skillCategoryId;
}

function setSkillCategogyDescription(skillCategogyDescription){
	this.skillCategogyDescription = skillCategogyDescription;
}

function setSkillsArray(skillsArray){
	this.skillsArray = skillsArray;
}

function getSkillArray(){
	return this.skillsArray;
}

function toCatString(){
	return skillCategoryId + "\n" + skillCategogyDescription;
}