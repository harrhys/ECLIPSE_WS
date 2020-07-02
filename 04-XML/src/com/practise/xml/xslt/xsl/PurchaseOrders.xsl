<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:output method="html" />

	<xsl:template match="PurchaseOrders">
		<html>
			<body>
				<xsl:apply-templates select="child::node()"/>
			</body>
		</html>
	</xsl:template>

	<xsl:template match="Customer">
		<h2 align="center">
			Customer :
			<xsl:apply-templates />
		</h2>
	</xsl:template>

	<xsl:template match="PurchaseOrder">
		<table border="1" align="center">
			<tr>
				<th align="center">
					<xsl:value-of select="name()" />
					<xsl:value-of select="name(@*)" />
				</th>
				<th align="center">
					<xsl:value-of select="name(*)" />
					<xsl:value-of select="*[1]">
					<xsl:value-of select="name(@*)" />
					</xsl:value-of>
				</th>
				<xsl:for-each select="*[1]/*">
					<th align="center">
						<xsl:value-of select="name(.)" />
					</th>
				</xsl:for-each>
				<xsl:if test="true"></xsl:if>
			</tr>
			<!-- <xsl:apply-templates /> -->
		</table>
	</xsl:template>

	<xsl:template match="OrderItem">
		<tr>
			<td align="center">
				<xsl:value-of select="../@*" />
			</td>
			<td align="center">
				<xsl:value-of select="@*" />
			</td>
			<xsl:for-each select="*">
				<td align="center">
					<xsl:value-of select="." />
				</td>
			</xsl:for-each>
		</tr>
	</xsl:template>

	<xsl:template match="OrderTotalPrice">
		<tr align="right">
			<td colspan="5" align="left"> Grand Total
			</td>
			<td align="center">
				<xsl:apply-templates />
			</td>
		</tr>
	</xsl:template>

</xsl:stylesheet>