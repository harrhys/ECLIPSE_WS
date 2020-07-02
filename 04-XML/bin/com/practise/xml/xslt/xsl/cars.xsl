<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns="http://www.java2s.com"
                xmlns:xsd="http://www.w3.org/2000/10/XMLSchema"
                version="1.0">
 
    <xsl:output method="xml"/>

    <xsl:template match="*">
        <xsl:element name="{name(.)}">
            <xsl:for-each select="@*">
                <xsl:if test="name(.) = 'minOccurs'">1
                    <xsl:if test=". != '1'">
                        <xsl:attribute name="{name(.)}">
                            <xsl:value-of select="."/>
                        </xsl:attribute>
                    </xsl:if>
                </xsl:if>
                <xsl:if test="name(.) = 'maxOccurs'">
                    <xsl:if test=". != '1'">
                        <xsl:attribute name="{name(.)}">
                            <xsl:value-of select="."/>
                        </xsl:attribute>
                    </xsl:if>
                </xsl:if>
            </xsl:for-each>
            <xsl:apply-templates/>
        </xsl:element>
    </xsl:template>

</xsl:stylesheet>