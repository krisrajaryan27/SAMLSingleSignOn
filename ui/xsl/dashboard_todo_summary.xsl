<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method='html' version='1.0' encoding='UTF-8' indent='yes'/>
<xsl:template match="/">
<div class="outerDiv" style="margin: 0px; padding: 0px;border-bottom:0px;">
<xsl:for-each select="items/item" >
<div class="divNotSelected" >
<xsl:attribute name="id"><xsl:value-of select="outerContentDivId"/></xsl:attribute>
<table class="todoBoxContent" style="border: 0px;" cellpadding="0" cellspacing="0">
		<tr>
			<td style="width: 25px;" class="noBorder"><xsl:value-of select="expand"/></td>  
			<td style="width: 747px;" class="noBorder"><xsl:attribute name="title"><xsl:value-of select="f_todo"/></xsl:attribute><xsl:value-of select="todo"/></td>       
		</tr>
</table>
<table style="border: 0px;" cellpadding="0" cellspacing="0" width="100%">
	<tr>
	<td>
		<div style="display: none;border: 0px;padding-bottom:30px; " ><xsl:attribute name="id"><xsl:value-of select="contentDivId"/></xsl:attribute></div>
	</td>
	</tr>
</table>
</div>
</xsl:for-each>
</div>

</xsl:template>
</xsl:stylesheet>


