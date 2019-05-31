//Class For trait
function Trait(){
	this.traitId;
	this.traitTitle;
	this.traitRank;
	this.isSystemGenerated=0;
	this.setTraitId=setTraitId;
	this.setTraitTitle=setTraitTitle;
	this.setTraitRank=setTraitRank;
	this.setIsSystemGenerated=setIsSystemGenerated;
}
function Trait(traitId,traitTitle){
	this.traitId=traitId;
	this.traitTitle=traitTitle;
}
function setTraitId(traitId){
	this.traitId=traitId;
}
function setTraitTitle(traitTitle){
	this.traitTitle=traitTitle;
}
function setTraitRank(traitRank){
	this.traitRank=traitRank;
}
function setIsSystemGenerated(isSystemGenerated){
	this.isSystemGenerated=isSystemGenerated;
}
//Class for steps
