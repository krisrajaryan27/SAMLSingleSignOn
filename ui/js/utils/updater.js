document.write("<div id=\"updater_wait\" style=\"display: none;position: absolute;\">");
document.write("<table>");
document.write("<tr>");
document.write("	<td><img src=\"images/wait.gif\"/></td>");
document.write("	<td>Please wait...</td>");
document.write("</tr>");
document.write("</table>");
document.write("</div>");
function showUpdater(elmId, opts){
	Position.clone($(elmId), $('updater_wait'), opts);
	Element.hide(elmId);
	Element.show('updater_wait');
}
function hideUpdater(elmId){
	Element.hide('updater_wait');
	Element.show(elmId);
}
