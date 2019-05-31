<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method='html' version='1.0' encoding='UTF-8' indent='yes'/>
<xsl:template match="/">
<table class="boxContentG" style="border: 0px;" cellpadding="0" cellspacing="0">
    <xsl:for-each select="items/item" >
		<tr><xsl:attribute name="class"><xsl:value-of select="rowCss"/></xsl:attribute>
			<td style="width: 20px;" class="topBorder" >&amp;nbsp;</td>
			<td style="width: 20px;" class="topBorder"><xsl:attribute name="title"><xsl:value-of select="f_priority"/></xsl:attribute><xsl:value-of select="priority"/></td>
	        <td style="width: 353px;" class="topBorder"><xsl:attribute name="title"><xsl:value-of select="f_position"/></xsl:attribute><xsl:value-of select="position"/></td>       
	        <td style="width: 65px;text-align: center;" class="topBorder"><xsl:attribute name="title"><xsl:value-of select="defaultTitle"/></xsl:attribute><xsl:value-of select="noOfOpenings"/></td>
	        <td style="width: 75px;text-align: center;" class="topBorder"><xsl:attribute name="title"><xsl:value-of select="defaultTitle"/></xsl:attribute><xsl:value-of select="inProcess"/></td>
	        <td style="width: 80px;text-align: center;" class="topBorder"><xsl:attribute name="title"><xsl:value-of select="defaultTitle"/></xsl:attribute><xsl:value-of select="offered"/></td>
	        <td style="width: 40px;text-align: center;" class="topBorder"><xsl:attribute name="title"><xsl:value-of select="defaultTitle"/></xsl:attribute><xsl:value-of select="joined"/></td>
	        <td style="width: 55px;text-align: center;" class="topBorder"><xsl:attribute name="title"><xsl:value-of select="defaultTitle"/></xsl:attribute><xsl:value-of select="rejected"/></td>
		</tr>
	</xsl:for-each>
</table>
</xsl:template>
</xsl:stylesheet>


