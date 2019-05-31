//Class For User
function User(){
	this.userId;
	this.userName;
	this.setUserId=setUserId;
	this.setUserName=setUserName;
}

function setUserId(userId){this.userId = userId;}
function setUserName(userName){this.userName=userName;}
function toString(){
	var str = "UserId = " + this.userId + "\n";
	str = str + "UserName = " + this.userName + "\n";	
	return str;
}