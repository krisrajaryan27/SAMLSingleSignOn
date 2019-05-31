<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method='html' version='1.0' encoding='UTF-8' indent='yes'/>
<xsl:template match="/">
<table class="boxContentG" style="border: 0px;" cellpadding="0" cellspacing="0">
    <xsl:for-each select="items/item" >
		<tr>
			<td style="width: 25px;" class="topBorder"><xsl:attribute name="title"><xsl:value-of select="f_flag"/></xsl:attribute><xsl:value-of select="flag"/></td>
			<td style="width: 130px;" class="topBorder"><xsl:attribute name="title"><xsl:value-of select="f_applicant"/></xsl:attribute><xsl:value-of select="applicant"/></td>       
	        <td style="width: 230px;" class="topBorder"><xsl:attribute name="title"><xsl:value-of select="f_clientTitle"/></xsl:attribute><xsl:value-of select="clientTitle"/></td>       
	        <td style="width: 280px;" class="topBorder" ><xsl:attribute name="title"><xsl:value-of select="f_actionRequired"/></xsl:attribute><xsl:value-of select="actionRequired"/></td>
	        <td style="width: 85px;" class="topBorder"><xsl:attribute name="title"><xsl:value-of select="f_dueDate"/></xsl:attribute><xsl:value-of select="dueDate"/></td>
		</tr>
	</xsl:for-each>
</table>
</xsl:template>
</xsl:stylesheet>


