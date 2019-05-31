<link rel="stylesheet" type="text/css" href="themes/default/website.css"/>
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script language="JavaScript" src="js/utils/updater.js"	type="text/javascript"></script>
<div id="updater">
</div>
<script>
showUpdater('updater',{setHeight: false, setWidth: false, offsetLeft: 450, offsetTop: 200});
window.onload=doOnLoad;
function doOnLoad() {
	window.location="applicantRegistrationFromWeb.do?mode=successPage";
}
</script>
