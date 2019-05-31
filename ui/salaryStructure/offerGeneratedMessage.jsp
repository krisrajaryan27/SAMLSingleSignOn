<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
<div class="contentDivPop" style="width: 550px;">
	<div class="outerDiv">
		<div class="popupTop">
			<table class="tblPop">
				<tr>
					<td>
						<s:text name="generate_offer_sheet.label.offer_generated"/><br/>
						<s:property value="#request['fileName']"/>
					</td>
				</tr>
			</table>
		</div>
		<div class="popupBody">
			<table class="tblPop" width="100%">	
			<tr>
				<td>&nbsp;</td>
				<td>
				<div class="navBtn" style="float: right;">
				<a href="#" style="width:60px; margin-left: 5px;" class="active" onclick="javascript: window.top.hidePopWin(true);return false;"><span class="rightC"></span><span class="leftC"></span><s:text name="common.close"/></a>
				</div>
				</td>
			</tr>			
			</table>
		</div>	
	</div>	
</div>
</body>
</html>