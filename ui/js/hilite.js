// Highlight Words Script
// copyright Stephen Chapman, 17th January 2007
// you may copy this script provided that you retain the copyright notice

var kw = [];
var qsParm = []; 

function qs() {
	var query = window.location.search.substring(1); 	
	var parms = query.split('&'); 
	for (var i=0; i < parms.length; i++) {
		var pos = parms[i].indexOf('='); 
		if (pos > 0) {			
			var key = parms[i].substring(0,pos); 
			var val = parms[i].substring(pos+1); 
			qsParm[key] = val;
		}
	}
} 

qsParm['hilite'] = null; 
qs();
if (qsParm['hilite'] != null) {	
	kw = qsParm['hilite'].split(',');
}
function hiliteKeywords() {	
	var bdy = document.getElementsByTagName('body')[0].innerHTML; 
	for (var i = kw.length - 1; i >= 0; i--) {		
		var temp = kw[i].split('|');				
		var val = reformatString(temp[0]);				
		val = val.replace(/\+/g,"\\+");
		var css = '';	
		if(kw[i].indexOf('|') == -1) {
			css = "hl1";
		}	else {
			css = temp[1];
		}	
		if(val != '' && val != 'null' && val != null) {		
			// apply the span tags around text
			if(val.indexOf("+") != -1) {
				var re = new RegExp('('+val+')','ig'); 
				bdy = bdy.replace(re,'<span class="'+css+'">$1<\/span>'); 
			} else {
				var re = new RegExp('(\\b'+val+'\\b)','ig'); 
				bdy = bdy.replace(re,'<span class="'+css+'">$1<\/span>'); 
			}													
			// don't insert them inside of the existing HTML tags in our page
			var re1 = new RegExp('(<[^>]*?)<span class="'+css+'">('+val+')<\/span>(.*?>)','ig'); 
			bdy = bdy.replace(re1,'$1$2$3');
			// and don't insert them between textarea tags
			var re2 = new RegExp('(<script.*?>)<span class="'+css+'">('+val+')<\/span>(<\/script>)','ig'); 
			bdy = bdy.replace(re2,'$1$2$3'); 
			// and don't insert them between script tags
			var re3 = new RegExp('(<textarea.*?>)<span class="'+css+'">('+val+')<\/span>(<\/textarea>)','ig'); 
			bdy = bdy.replace(re3,'$1$2$3');
		}
	} 
	document.getElementsByTagName('body')[0].innerHTML = bdy;
}