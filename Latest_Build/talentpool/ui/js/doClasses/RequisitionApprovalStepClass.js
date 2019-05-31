//Class For RequisitionStep
function RequisitionStep(){
	this.index=-1;
	this.stepId=0;
	this.stepTitle='';
	this.userIds='';
	this.userNames='';
	
	this.setIndex=setIndex;
	this.setStepId=setStepId;
	this.setStepTitle=setStepTitle;	
	this.setUserIds=setUserIds;
	this.setUserNames=setUserNames;	
	this.toString=toString;
}

function setIndex(index){this.index=index;}
function setStepId(stepId){this.stepId=stepId;}
function setStepTitle(stepTitle){this.stepTitle=stepTitle;}
function setUserIds(userIds){this.userIds=userIds;}
function setUserNames(userNames){this.userNames=userNames;}
function toString(){
	var str = "";
	str += formatString(this.stepId) + "|";
	str += unescapeHTML(this.stepTitle) + "|";	
	str += formatString(this.userIds) + "|";
	str += (this.index + 1);
	return str;
}