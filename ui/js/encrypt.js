/*
 * Encrypt JS
 * 
 * */
function Encrypt(theText, rnd) {
	output = new String;
	output += '0101';
	Temp = new Array();
	TextSize = theText.length;
	for (i = 0; i < TextSize; i++) {
		Temp[i] = theText.charCodeAt(i) + rnd;
	}
	for (i = 0; i < TextSize; i++) {
		output += String.fromCharCode(Temp[i]);
	}
	output += '101';
	return output;
}

function unEncrypt(theText,rnd) {
	output = new String;
	Temp = new Array();
	TextSize = theText.length;
	for (i = 4; i < TextSize-3; i++) {
		Temp[i] = theText.charCodeAt(i)-rnd;
	}
	for (i = 4; i < TextSize-3; i++) {
		output += String.fromCharCode(Temp[i]);
	}
		return output;
}