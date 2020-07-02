<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:output method="html" />

	<xsl:template match="/">
		<html>
			<body>
				<table border="1">

					<xsl:apply-templates select="child::node()" />

				</table>
			</body>

		</html>
	</xsl:template>

	<xsl:template match="node()">
		<tr>
			<td width="15%">
				<xsl:choose>
					<xsl:when test="count(.|/)=1">
						<xsl:text>Root - </xsl:text>
						<xsl:value-of select="name()" />
					</xsl:when>
					<xsl:when test="self::*">
						<xsl:text>Element - </xsl:text>
						<xsl:value-of select="name()" />
					</xsl:when>
					<xsl:when test="self::text()">
						<xsl:text>Text - </xsl:text>
					</xsl:when>
					<xsl:when test="self::comment()">
						<xsl:text>Comment - </xsl:text>
					</xsl:when>
					<xsl:when test="self::processing-instruction()">
						<xsl:text>PI - </xsl:text>
					</xsl:when>
					<xsl:otherwise>
						<xsl:text>Attribute - </xsl:text>
						<xsl:value-of select="name(@*)" />
					</xsl:otherwise>
				</xsl:choose>
			</td>
			<td>
				<xsl:value-of select="name(.)" />
			</td>
			<td>
				<xsl:value-of select="." />
			</td>
		</tr>
		<xsl:apply-templates select="child::node()" />
	</xsl:template>

</xsl:stylesheet>