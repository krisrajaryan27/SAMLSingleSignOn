<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="com.talentPool.applicant.dataobject.ApplicantData,
                  com.talentPool.selectionProcess.SelectionProcessConstants,
                  java.util.BitSet, 
                  com.talentPool.selectionProcess.form.SelectionProcessForm,
                  com.talentPool.common.utils.Utils,
                  java.util.ArrayList,
                  com.talentPool.common.db.SimpleDataObject"%>
<%@page import="com.talentPool.applicant.dataobject.EducationalData"%>
<link rel="stylesheet" type="text/css" href="themes/default/print.css"/>                  
<table width="100%" class="printTable" border="0">
	<tr>
	  <td><bean:message key="call_list.label.call_list"/></td>
	</tr>
  <tr>
    <td>
      <table width="660" >
		    <logic:iterate id="applicant"  name="applicants" scope="request" type="com.talentPool.common.db.SimpleDataObject">
	  	    <tr>
		  	    <td>
		    		  <%
								if(!Utils.isBlankOrNull(applicant.getString("applicantName"))){
									out.write("<b>"+Utils.escapeHTML(applicant.getString("applicantName"))+"</b>");
                }
								if(!Utils.isBlankOrNull(applicant.getString("yearsOfExp"))){
									out.write("&nbsp;(" +applicant.getString("yearsOfExp") + "yrs),&nbsp;");
								}
								ArrayList skills = (ArrayList)applicant.getAttribute("skills");
								if(skills!=null){
							    StringBuffer skillList = new StringBuffer();
							    for (int k=0;k<skills.size();k++){
								    SimpleDataObject sDo = (SimpleDataObject) skills.get(k);
								    skillList.append(sDo.getString("skill"));
								    if (k< skills.size()- 1) {
									    skillList.append(", ");
						      	}
					   			}
					   			out.write(Utils.escapeHTML(skillList.toString()));
					  		}
					  		if(!Utils.isBlankOrNull(applicant.getString("positionTitle"))){
               		out.write("&nbsp;-&nbsp;" + Utils.escapeHTML(applicant.getString("positionTitle")) + "&nbsp;(" + Utils.escapeHTML(applicant.getString("stepTitle")) + ")");
               	}
             %>
						<br>
						<%  
							ArrayList education = (ArrayList)applicant.getAttribute("education");
			        if(education!=null){
				        for(int j=0;j<education.size();j++){
				          EducationalData aDo = (EducationalData) education.get(j);
				          out.write(Utils.escapeHTML(aDo.getFormattedEducation())+"<br/>");
			          }
			        }
			        
			        if(!Utils.isBlankOrNull(applicant.getString("currentemployer"))){
								out.write(Utils.escapeHTML(applicant.getString("currentemployer"))+", "); 
							}
							if(!Utils.isBlankOrNull(applicant.getString("applicantCity"))){
             		out.write(Utils.escapeHTML(applicant.getString("applicantCity")));
             	}
              if(!Utils.isBlankOrNull(applicant.getString("currentemployer")) || !Utils.isBlankOrNull(applicant.getString("applicantCity"))){
             		out.write("<br/>");
             	}
           %>
           <bean:message key="printable_view.label.phone"/>:
           <%
							if(!Utils.isBlankOrNull(applicant.getString("applicantCellPhone"))){
								out.write(Utils.escapeHTML(applicant.getString("applicantCellPhone")));
							}
							if(!Utils.isBlankOrNull(applicant.getString("applicantWorkPhone"))){
								if(!Utils.isBlankOrNull(applicant.getString("applicantCellPhone"))){
									out.write(",&nbsp;");
								}
           			out.write(applicant.getString("applicantWorkPhone"));
							}
	         		if(!Utils.isBlankOrNull(Utils.escapeHTML(applicant.getString("applicantHomePhone")))){
	            	if(!Utils.isBlankOrNull(Utils.escapeHTML(applicant.getString("applicantWorkPhone")))){
	             		out.write(",&nbsp;");
	             	}else if(!Utils.isBlankOrNull(applicant.getString("applicantCellPhone"))){
	           			out.write(",&nbsp;");
	           		}
	         			out.write(Utils.escapeHTML(applicant.getString("applicantHomePhone")));
	         		}
	        %>
					&nbsp; <bean:message key="printable_view.label.email"/>:&nbsp;
					<%
	           if(!Utils.isBlankOrNull(applicant.getString("applicantEmail"))){
	         		out.write(applicant.getString("applicantEmail"));
	           } 
	           if(!Utils.isBlankOrNull(applicant.getString("applicantEmail2"))){
		           if(!Utils.isBlankOrNull(applicant.getString("applicantEmail"))){
		         		out.write(",&nbsp;");
		           } 
	         			out.write(applicant.getString("applicantEmail2"));
	           }
	           if(!Utils.isBlankOrNull(applicant.getString("sourceTitle"))){
	         			out.write("<br>"+Utils.escapeHTML(applicant.getString("sourceTitle")));
	           }
					%>
						</td>
	  	 	 </tr>
	  	 	 <tr>
		      	<td height="30">
		    	  </td>
	    	 </tr>
	  	</logic:iterate>
   	</table>
  	</td>
	</tr>
	<tr>
	  <td>
	    <bean:message key="printable_view.label.listGeneratedOn"/> <bean:write name="listGenratedOn" scope="request" format="dd MMMMMMMMM yyyy 'at' h:mm a"/>
	  </td>
 </tr>
</table>
<div id="buttons" class="navBtn" style="margin-top: 10px;margin-bottom: 10px;margin-left: 5px;"><a
	href="#" style="width:60px;margin-right: 5px;" class="active"
	onclick="javascript:window.print();"><span class="rightC"></span><span
	class="leftC"></span><bean:message key="common.print" /></a> 
	<a href="#" style="width:60px;" class="active"
	onclick="javascript: closeWin()"><span
	class="rightC"></span><span class="leftC"></span><bean:message
	key="common.close" /></a></div>

<script language="javascript">

$(document).ready(function() {
	var response = <%=request.getAttribute("fileResponse")%>;
	if(response) {
		console.log(response);
	}
});

function closeWin() {
	if ('<%=request.getParameter("isNewWin")%>' == 'null') {
		window.top.hidePopWin(false);return false;
	} else {
		window.close();
	}
}

window.onbeforeprint=beforePrint;
window.onafterprint=afterPrint;
          
function beforePrint() {
	document.getElementById('buttons').style.display = 'none';
}
      
function afterPrint() {
	document.getElementById('buttons').style.display = '';
}

window.onload=doOnLoad;

function doOnLoad() {
	if ('<%=request.getParameter("isNewWin")%>' != null) {
		window.document.title="<bean:message key="call_list.label.call_list"/>";
	}
}
</script>