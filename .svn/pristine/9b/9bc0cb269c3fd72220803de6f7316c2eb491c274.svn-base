//Class For StatusMessage
function StatusMessage(){
	this.messageId;
	this.message;
	this.isDefault;
	this.setMessageId=setMessageId;
	this.setMessage=setMessage;
	this.setIsDefault=setIsDefault;
	this.toString=getStatusMessage;
}

function setMessageId(messageId){this.messageId = messageId;}
function setMessage(message){this.message=message;}
function setIsDefault(isDefault){this.isDefault=isDefault;}
function getStatusMessage(){
	var str = formatString(this.messageId) + "|" + formatString(this.message) + "|" + formatString(this.isDefault);
	return str;
}