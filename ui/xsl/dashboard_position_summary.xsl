<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method='html' version='1.0' encoding='UTF-8' indent='yes'/>
<xsl:template match="/">
<div class="outerDiv" style="margin: 0px; padding: 0px;border-bottom:0px;">
<xsl:for-each select="items/item" >
	<div class="divNotSelected" >
		<xsl:attribute name="id"><xsl:value-of select="positionOuterContentDivId"/></xsl:attribute>
		<table class="todoBoxContent" style="border: 0px;" cellpadding="0" cellspacing="0">
			<tr>
				<td style="width: 20px;" class="noBorder"><xsl:value-of select="expand"/></td>  
				<td style="width: 353px;" class="noBorder"><xsl:attribute name="title"><xsl:value-of select="groupedNameTitle"/></xsl:attribute><xsl:value-of select="groupedName"/></td>
				<td style="width: 20px;" class="noBorder"></td>
				<td style="width: 65px;text-align: center;" class="noBorder"><xsl:value-of select="noOfOpenings"/></td>
				<td style="width: 75px;text-align: center;" class="noBorder"><xsl:value-of select="inProcess"/></td>
				<td style="width: 80px;text-align: center;" class="noBorder"><xsl:value-of select="offered"/></td>
				<td style="width: 40px;text-align: center;" class="noBorder"><xsl:value-of select="joined"/></td>    
				<td style="width: 55px;text-align: center;" class="noBorder"><xsl:value-of select="rejected"/></td>
			</tr>
		</table>
		<table style="border: 0px;" cellpadding="0" cellspacing="0" width="100%">
			<tr>
				<td>
					<div style="display: none;border: 0px;padding-bottom:30px; " ><xsl:attribute name="id"><xsl:value-of select="positionContentDivId"/></xsl:attribute></div>
				</td>
			</tr>
		</table>
	</div>
</xsl:for-each>
</div>
<xsl:for-each select="items/total" >
	<div class="outerDiv" style="margin: 0px; padding: 0px;border-top:0px;border-bottom:0px;background-color:#f9f9f9;font-weight:bold;">
	<table class="boxContent" style="border: 0px;" cellpadding="0" cellspacing="0">
		<tr>
			<td style="width: 20px;height: 20px;" class="noBorder"></td>        
			<td style="width: 246px;color:#666;" class="noBorder">Total:</td>  
			<td style="width: 129px;" ></td>     
	        <td style="width: 64px;text-align: center;color:#666;" class="noBorder"><xsl:value-of select="vacancies"/></td>
	        <td style="width: 74px;text-align: center;color:#666;" class="noBorder"><xsl:value-of select="inprocess"/></td>
	        <td style="width: 80px;text-align: center;color:#666;" class="noBorder"><xsl:value-of select="offered"/></td>
	        <td style="width: 40px;text-align: center;color:#666;" class="noBorder"><xsl:value-of select="joined"/></td>
	        <td style="width: 55px;text-align: center;color:#666;" class="noBorder"><xsl:value-of select="rejected"/></td>
		</tr>
	</table>
	</div>
</xsl:for-each>
</xsl:template>
</xsl:stylesheet>


