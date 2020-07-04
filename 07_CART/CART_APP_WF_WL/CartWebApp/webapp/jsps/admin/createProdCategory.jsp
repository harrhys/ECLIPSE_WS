<%@include file="../includes/header.jsp"%>

<script type="text/javascript">
	function showCategories() {
		var request = new XMLHttpRequest();
		var text = document.getElementById("categories").innerHTML;
		if(text=="")
			{
			request.onreadystatechange = function() {
				if (this.readyState == 4 && this.status == 200) {
					var response = this.responseText;
					
					document.getElementById("categories").innerHTML = response;
					document.getElementById("catButton").value = "Hide Categories";
				}
			};
			request.open("GET", "admin/showProductCategories", true);
			request.send();
			}
		else
			{
			document.getElementById("categories").innerHTML ="";
			document.getElementById("catButton").value = "Show Categories";
			}
		
	}
	
</script>

<s:form action="admin/createProdCategory">
	<s:textfield label="Product Category Name" key="name" />
	<s:textfield label="Product Category Description" key="desc" />
	<s:submit value="Create" />
	<br>
</s:form>

<input type="button" value="Show Categories" id="catButton" size="10"  onclick="showCategories();"/>

<p id="categories"></p>

<%@include file="../includes/footer.jsp"%>