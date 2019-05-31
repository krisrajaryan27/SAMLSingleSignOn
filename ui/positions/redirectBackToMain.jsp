<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
		<script type="text/javascript">
			window.onload = doonload;
			
			function doonload(){
				var forwardURL = document.getElementById("forwardURL").value;
				var returnPath = document.getElementById("returnPath").value;
				var positionId = document.getElementById("positionId").value;
				var socialMediaTypeId = document.getElementById("socialMediaTypeId").value;
				if(forwardURL.length > 0)
					window.location=forwardURL;
				if(returnPath.length > 0){
				window.location = returnPath + "&positionId=" + positionId
							+ "&socialMediaTypeId=" + socialMediaTypeId;
				}
			}
		</script>
	</head>
	<body>
		<s:hidden id="forwardURL" value="%{#request['forwardURL']}"/>
		<s:hidden id="returnPath" value="%{#request['returnPath']}"/>
		<s:hidden id="positionId" value="%{#request['positionId']}"/>
		<s:hidden id="socialMediaTypeId" value="%{#request['socialMediaTypeId']}"/>
	</body>
</html>