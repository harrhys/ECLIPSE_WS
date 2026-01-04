<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

	<xsl:output method="html" indent="yes" encoding="UTF-8"/>

	<xsl:strip-space elements="*" />

    	<xsl:template match="/">
      		<html> 
			<head> 
	  			<title>Java Pet Store: Shipped Order <xsl:value-of select="//orderid" /></title> 
			</head> 
			<body bgcolor="#ffffff">
				<basefont color="black" size="7">
					<xsl:apply-templates />
				</basefont>
			</body>
		</html>
	</xsl:template>

	<xsl:template match="/Invoice">
		<bold>Thanks</bold> for placing an order with <font size="+2">Java Pet Store 1.3</font><br />
		Your order <font color="red"><xsl:value-of select="//orderid" /></font> has been shipped
		<p /> The contents of your shipment are:<br />
		<table border="1">
			<tr><td>Category</td><td>Product #</td><td>Quantity</td><td>Unit Price</td></tr>
			<xsl:apply-templates select="LineItem"/>
		</table>
	</xsl:template>

	<xsl:template match="LineItem">
		<tr>
			<td><xsl:value-of select="CategoryId" /></td>
			<td><xsl:value-of select="ProductId" /></td>
			<td align="right"><xsl:value-of select="Quantity" /></td>
			<td align="right"><xsl:value-of select="format-number(UnitPrice, '&#x24;#,##0.00')" /></td>
		</tr>
	</xsl:template>

</xsl:stylesheet>
