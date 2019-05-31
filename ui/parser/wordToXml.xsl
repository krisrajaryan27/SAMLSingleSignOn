<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:w="http://schemas.microsoft.com/office/word/2003/wordml" xmlns:wx="http://schemas.microsoft.com/office/word/2003/auxHint">	
	<xsl:template match="//wx:sect">
		<document>
		<html>
			<body>
				<table>
					<tr>
						<td>
							<xsl:apply-templates/>
						</td>
					</tr>
				</table>				
			</body>
		</html>
		</document>
	</xsl:template>	
	
	<xsl:template match="wx:sub-section">
		<xsl:apply-templates />
	</xsl:template>
	
	<xsl:template match="w:tbl">
		<br/>
		<table style="border:1px solid #000000;">
			<xsl:apply-templates select="w:tr"/>
		</table>		
	</xsl:template>
	
	<xsl:template match="w:p">
		<p>		
			<xsl:apply-templates /> 
		</p>		
	</xsl:template>
			
	<xsl:template match="w:tr">
		<tr>
			<xsl:apply-templates select="w:tc"/>
		</tr>		
	</xsl:template>
	
	<xsl:template match="w:tc">
		<td style="border:1px solid #000000;">			
			<xsl:apply-templates />
		</td>		
	</xsl:template>
	
	<xsl:template match="w:r">		
		<xsl:apply-templates />
	</xsl:template>
	
	<xsl:template match="w:t">						
		<text><xsl:value-of select="."/></text>
	</xsl:template>
	
	 <xsl:template match="w:b">			
		<bold/>
	</xsl:template>
	
</xsl:stylesheet>
