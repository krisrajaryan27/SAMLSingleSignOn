<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="contentDiv">
	<s:form method="POST" action="reportTemplate" >
		Report Title: <s:textfield name="reportTitle" />
		<s:submit action="saveReportBasicDetails" value="Next" />
		<s:submit action="cancel" value="Cancel" />
	</s:form>
</div>