function IdValueBean(){
	this.id;
	this.value;
	this.setId=setId;
	this.setValue=setValue;
	this.toString=getIdValueBean;
}

function setId(id){this.id = id;}
function setValue(value){this.value=value;}
function getIdValueBean(){
	var str = formatString(this.id) + "|" + formatString(this.value);
	return str;
}