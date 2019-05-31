<%@ taglib uri="/WEB-INF/tld/struts-tiles.tld" prefix="tiles"%>
<tiles:insert page="/templates/loginTemplate.jsp" flush="true">
    <tiles:put name="mainPane" value="/common/errorPageMain.jsp"/>
</tiles:insert>