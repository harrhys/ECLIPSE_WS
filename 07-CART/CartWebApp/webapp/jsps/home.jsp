<%@include file="includes/header.jsp"%>
<s:if test="%{#session.user.userType=='CUSTOMER'}">
	<%@include file="customer/customerHome.jsp"%>
</s:if>
<s:elseif test="%{#session.user.userType=='MERCHANT'}">
	<%@include file="merchant/merchantHome.jsp"%></s:elseif>
<s:elseif test="%{#session.user.userType=='ADMIN'}">
	<%@include file="admin/adminHome.jsp"%></s:elseif>
<s:else></s:else>
<%@include file="includes/footer.jsp"%>

`
