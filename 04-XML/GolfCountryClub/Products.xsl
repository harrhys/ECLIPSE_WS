<?xml version="1.0"?>

<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<!-- <xsl:template match="Price">
		Product ID : <xsl:apply-templates  select="Price" />
	</xsl:template> -->

	<xsl:template match="Price">
		Price -	<xsl:value-of select="" />
	</xsl:template>

</xsl:stylesheet>