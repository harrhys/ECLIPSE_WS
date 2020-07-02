<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  

  <xsl:template match="/">
    <xsl:apply-templates select="/GolfCountryClub/Member"/>
  </xsl:template>

  <xsl:template match="RegularMember">
    - <xsl:value-of select="FirstName" />
  </xsl:template>

</xsl:stylesheet>