<?xml version="1.0" encoding="ISO-8859-1"?>

<xsl:stylesheet
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	<xsl:output method="html" />

	<xsl:template match="/">
		<html>
			<body>
				<xsl:apply-templates />
			</body>
		</html>
	</xsl:template>

	<xsl:template match="/ARTICLE/TITLE">
		<xsl:apply-templates />
	</xsl:template>

	<xsl:template match="TITLE">
		<h1 align="left">
			<xsl:value-of select="."/>
		</h1>

	</xsl:template>

</xsl:stylesheet>


