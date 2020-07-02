<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:output method="html" />

	<xsl:template match="Article">
		<html>
			<body>
				Article -
				<xsl:value-of select="/Article/Title" />
				<br />
				Authors:
				<br />
				<xsl:apply-templates select="/Article/Authors" />
			</body>
		</html>
	</xsl:template>

	<xsl:template match="Author">
		-
		<xsl:value-of select="." />
		<br />
	</xsl:template>
	
	  <xsl:template match="text()|@*"/>

</xsl:stylesheet>