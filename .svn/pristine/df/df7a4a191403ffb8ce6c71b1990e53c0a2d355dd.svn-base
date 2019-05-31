<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method='html' version='1.0' encoding='UTF-8' indent='yes'/>
<xsl:template match="/">
    <table cellpadding="2" cellspacing="2" style="border:0px;">
      <xsl:for-each select="filters/filter">
      <tr>
		<td><xsl:attribute name="title"><xsl:value-of select="title"/></xsl:attribute><xsl:value-of select="link"/></td>      
      </tr>
      </xsl:for-each>
    </table>
</xsl:template>
</xsl:stylesheet>
