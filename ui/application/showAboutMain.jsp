<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>

		<table  border="0" cellpadding="0" cellspacing="0" align="center">
			<tr>
				<td width="118" height="60" valign="top" bgcolor="#FFFFFF" style="padding-top: 10px;">
					<img src="images/logo_talentpool.gif" width="165" height="50" border="0" />
				</td>
			</tr>
		</table>

<div class="TopNavL" style="float:left; margin-left:15px;">
	<table width="350" border="0" cellpadding="0" cellspacing="3" style="padding-top: 10px;">
		<tr>
			<td class="Grey">Product Name: <bean:message key="title.common"/>
			</td>
		</tr>
		<tr>
			<td class="Grey">Version: <bean:write name="version" scope="request"/>
			</td>
		</tr>
		<tr>
			<td class="Grey">Validity: <bean:write name="validity" scope="request"/>
			</td>
		</tr>

		<tr>
			<td class="Grey">
				<bean:message key="common.warning"/>
			</td>
		</tr>
		<tr>
		<td>
			<div class="navBtn" style="float: right;">
			<a href="#" style="width:50px;" class="active" onclick="javascript: window.top.hidePopWin(false);return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.ok"/></a>
			</div>
		</td>
	</tr>
	</table>
</div>