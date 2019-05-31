<%@page import="org.apache.struts.Globals"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/checkboxlist/checkboxlist.js" type="text/javascript"></script>
<% 
if(request.getAttribute(Globals.ERROR_KEY)!=null){
%>
<%@page import="com.talentPool.positions.PositionConstants"%><table  id="m_errortable" > 
  <tr>
	<td class="header">
	  <b><bean:message key="errors.following_errors"/></b>
	</td>               
  </tr>
  <tr>
    <td class="message"><html:errors/></td>               
  </tr>
</table>
<br>
<% } %>
<script>
var checkboxListSelectionStage=null;
</script>
<div class="contentDivPop">
		<table>
			<tr>
				<td style="padding-bottom:10px;font-weight: bold; ">
					Select Stage to Filter
				</td>
			</tr>
			<tr>
				<td>
					<script type="text/javascript">
				         var opts = <bean:write name="JSSelectionStageArray" scope="request" filter="false"/>;
				         var selectedIds = '<bean:write name="stepLevel" scope="request" filter="false"/>';
				         checkboxListSelectionStage = new CheckBoxList(opts,selectedIds,{namesonly:false, layerclass:'checkboxlistdiv', width:'250px', size:10, imageclass:'chkboxclass', uncheckedimg:'images/checkboxunchecked.gif', checkedimg:'images/checkboxchecked.gif'});
				         document.write(checkboxListSelectionStage.getHtml());
						//checkboxListSelectionStage.setOnChangeHandler(onChangeSelectionStageFilter);
				         checkboxListSelectionStage.init();
				    </script>	
				</td>
			</tr>
			<tr>
				<td>
					<div class="navBtn" style="float: right;">
						<a href="#" style="width:60px;" class="active" onclick="javascript: submitSatge();"><span class="rightC"></span><span class="leftC"></span>Filter</a>
						<a href="#" style="width:60px; margin-left: 5px;" class="active" onclick="javascript:window.top.hidePopWin(false);"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
					</div>
				</td>
			</tr>
		</table>
</div>
<script>
function submitSatge(){
	returnVal = checkboxListSelectionStage.getSelectedIds();
	window.top.hidePopWin(true);
}
function doOnLoad() {  	
	window.top.setPopTitle("<b>Stage Filter</b>");
}
window.onload = doOnLoad;
</script>	