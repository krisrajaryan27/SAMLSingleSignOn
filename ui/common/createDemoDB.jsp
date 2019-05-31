<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
	<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Demo DB</title>
<script type="text/javascript">
	window.onload = function() { 
		var listReq = '<%=request.getAttribute("myList")%>';
		var list = listReq.split("||");
		var selectbox = document.createElement('select');
		selectbox.id = "selectDBBox";
		var i=0;
		for(i=0;i<list.length;i++){
			var option = document.createElement('option');
			option.value = list[i];
			option.text = list[i];
			selectbox.appendChild(option);
		}
		document.getElementById("selectDB").appendChild(selectbox);
		
		var doneDBScript = '<%=request.getAttribute("dbNameUpdated")%>';
		if(doneDBScript != 'null' && doneDBScript.length > 0){
			document.getElementById("onDone").innerHTML = "<b>"+doneDBScript+"</b> has been setup <br/><br/>"
		}
		
		var timeincrementedSuccess = '<%=request.getAttribute("timeincrementedSuccess")%>';
		if(timeincrementedSuccess != null && timeincrementedSuccess == '1'){
			document.getElementById("dayUpdateSuccess").innerHTML = "<b>Updated the database time and date </b> <br/><br/>";
		}
	}
	
	function onSubmit(){
		if(document.getElementById("dayCntF1").checked){
			document.forms[0].dayCntFlag.value = "1";
			document.forms[0].dayCnt.value = document.getElementById('dayCnt1').value;
		}else{
			var e = document.getElementById('selectDBBox');
			document.forms[0].dbName.value = e.options[e.selectedIndex].value;
		}
		document.forms[0].submit();
	}
	
	function showDaysBox(){
		if(document.getElementById("dayCntF1").checked){
			document.getElementById("daysBox").innerHTML = "<br/>Enter no. of days : <input type='text' id='dayCnt1'/> <br/>"	
		}else{
			document.getElementById("daysBox").innerHTML = '';
		}
	}
	
</script>
</head>
<body>
<form action="miscutils.do">
	<input type="hidden" name="mode" value="createDemoDB">
	<input type="hidden" name="dbName">
	<input type = "hidden" name= "dayCntFlag"/>
	<input type = "hidden" name= "dayCnt"/>
	<h2>
	Hi<br/>
	Ready to create new database <br/><br/></h2>
	<h3>Note:
		<ul>
			<li>Please place your sql files under webapps>talentpool>demoDBScript</li>
			<li>The page will refresh once the dbscript is done running. Also you will see a cmd prompt window closing as the db script ends running</li>
			<li>Make sure a talentpool database exists</li>
			<li>This will override every data on the existing talentpool database</li>
			<li>Once the database installed (post the cmd prompt closing), click on check box to enter number of days you want to increment the database timeline by</li>
			<li>Installing of database and running of date/time increment script are separate and would have to run one after the other.That is both wont run together.</li>
		</ul>
	</h3>
	Select Database :<div id="selectDB"></div>
	<br/><br/>
	<div id="onDone"></div>
	<input type="checkbox" id="dayCntF1" onclick = "javascript: showDaysBox()"> Check this box to increment database by a certain number of days
	<div id="daysBox"></div> 
	<div id="dayUpdateSuccess"></div>
	<br/><br/><br/>
	<button onclick="javascript: onSubmit()">Submit</button>
	
</form>
</body>
</html>