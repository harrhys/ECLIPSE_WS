<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:output method="html" />
	<xsl:template match="Products">
		<html>
			<body>
				<table align="center" border="1">
					<tr>
						<th colspan="5" align="center">
							<xsl:value-of select="name(@*)"></xsl:value-of>
						</th>
					</tr>
					<tr>
						<!-- *[1] will select the first element node under Products -->
						<xsl:for-each select="*[1]">
							<td>
								<xsl:value-of select="name(@*)" />
							</td>
						</xsl:for-each>
						<xsl:for-each select="*[1]/*">
							<td>
								<xsl:value-of select="name(.)" />
							</td>
						</xsl:for-each>
					</tr>
					<xsl:apply-templates select="//Price" />
				</table>
			</body>
		</html>
	</xsl:template>

	<xsl:template match="Price">
		<xsl:value-of select="name(.)" />
		-
		<xsl:value-of select="." />

	</xsl:template>

</xsl:stylesheet>