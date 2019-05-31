document.write("<div id=\"updater_wait\" style=\"display: none;position: absolute;z-index: 9999;\">");
document.write("<table>");
document.write("<tr>");
document.write("	<td><img src=\"images/page-loader-gif-15.gif\" /></td>");
//document.write("	<td>Please wait...</td>");
document.write("</tr>");
document.write("</table>");
document.write("</div>");
function showUpdater(elmId, opts){
	Position.clone($(elmId), $('updater_wait'), opts);
	Element.hide(elmId);
	Element.show('updater_wait');
	document.getElementById("outerDiv").style="filter:blur(1px)";
}
function hideUpdater(elmId){
	Element.hide('updater_wait');
	Element.show(elmId);
	document.getElementById("outerDiv").style="filter:blur(0px)";
}
