<%@ taglib uri="/WEB-INF/tld/struts-tiles.tld" prefix="tiles"%>
<tiles:insert page="/templates/memberDefaultTemplate.jsp" flush="true">
	<tiles:put name="topPane" value="/common/blank.html"/>
	<tiles:put name="mainPane" value="/applicantRegistration/applicantAddedMain.jsp"/>
</tiles:insert>
