<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

	<xsl:output method="html" indent="yes" encoding="UTF-8"/>

	<xsl:strip-space elements="*" />

    	<xsl:template match="/">
      		<html> 
			<head> 
	  			<title>Java ペット屋さん: 発送</title> 
			</head> 
			<body bgcolor="#ffffff">
				<basefont color="black" size="7">
					<xsl:apply-templates />
				</basefont>
			</body>
		</html>
	</xsl:template>

	<xsl:template match="/Invoice">
		<font size="+2">Java ペット屋さん 1.3</font> にご注文いただきまして<bold>ありがとうございました。</bold><br />
		ご注文 <font color="red"><xsl:value-of select="//orderid" /></font> を発送いたしました。
		<p /> 発送内容は以下のようになっております:<br /><xsl:apply-templates select="LineItem"/>
	</xsl:template>

	<xsl:template match="LineItem">
        	<br /><xsl:value-of select="concat(CategoryId, ' ', ProductId,  ' ',Quantity , '匹 @ ', format-number(UnitPrice, '￥#,##0'))" />
	</xsl:template>

</xsl:stylesheet>
