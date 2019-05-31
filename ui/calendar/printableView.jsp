<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<bean:define id="appointments" name="appointments" scope="request" type="java.util.List"/>
<html>
  <head>
    <title></title>
    <link rel="stylesheet" type="text/css" href="themes/default/print.css">
    <link rel="stylesheet" type="text/css" href="themes/default/default.css">
    <script language="JavaScript" src="js/calender/tpcalendar.js" type="text/javascript"></script>
    <script>
      window.onbeforeprint=beforePrint;
      window.onafterprint=afterPrint;
      document.title='<bean:message key='title.common' />';
      
      function buildTimeString(elemId, from, to) {
        timeFrom = getTime(from);
        //timeTo = getTime(to);
        elem = document.getElementById(elemId);
        if (elem) {
          //elem.innerHTML = timeFrom + ' - ' + timeTo;
          elem.innerHTML = timeFrom;
        }
      }
      
      function beforePrint() {
          document.getElementById('buttons').style.display = 'none';
      }
      
      function afterPrint() {
        document.getElementById('buttons').style.display = '';
      }
    </script>
  </head>
  <body> 
    <form>
    <table width="100%" class="printTable">
      <tr>
        <td><bean:write name="appointmentdate" scope="request" format="EEEEE MMMMM d, yyyy"/></td>
      </tr>
      <tr>
        <td><bean:message key="printable_view.label.interview_schedule"/></td>
      </tr>
      <tr>
        <td>
          <table class="printDataTable" width="100%">
            <tr>
              <td><bean:message key="printable_view.label.time"/></td>
              <td><bean:message key="printable_view.label.candidate"/></td>
              <td><bean:message key="common.position"/></td>
              <td><bean:message key="printable_view.label.interviewers"/></td>
              <td><bean:message key="printable_view.label.source"/></td>
              <td><bean:message key="printable_view.label.status"/></td>
            </tr>
            <logic:iterate id="appointment" name="appointments" type="com.talentPool.calendar.dataobject.AppointmentData">
              <tr>                
                <td nowrap="nowrap" id="time + <%=appointment.getAppointmentId()%>"></td>
                <td nowrap="nowrap"><bean:write name="appointment" property="applicantName"/></td>
                <td nowrap="nowrap"><bean:write name="appointment" property="applicantPositionTitle"/></td>
                <td><bean:write name="appointment" property="interviewer"/></td>
                <td><bean:write name="appointment" property="source"/></td>
                <td><bean:write name="appointment" property="status"/></td>
                <script>
                  buildTimeString('time + <%=appointment.getAppointmentId()%>', '<bean:write name="appointment" property="appointmentFromDate" />', '<bean:write name="appointment" property="appointmentToDate" />');
                </script>
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
      <tr>
        <td><br/><br/></td>
      </tr>
    </table>
    <div id="buttons" class="navBtn" style="float: right;margin-right:23px;">
    	<a href="#" style="width:60px;" class="active" onclick="javascript:window.print();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.print"/></a>
			<a href="#" style="width:60px; margin-left:5px;" class="active" onclick="javascript:window.close();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.close"/></a>
		</div> 
    </form>   
  </body>
</html>