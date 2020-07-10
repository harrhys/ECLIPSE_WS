<%@include file="../includes/header.jsp"%>

<script type="text/javascript">
	function showProducts() {
		var request = new XMLHttpRequest();
		var text = document.getElementById("products").innerHTML;
		if (text == "") {
			request.onreadystatechange = function() {
				if (this.readyState == 4 && this.status == 200) {
					var response = this.responseText;

					document.getElementById("products").innerHTML = response;
					document.getElementById("showButton").value = "Hide Products";
				}
			};
			request.open("GET", "merchant/showProducts", true);
			request.send();
		} else {
			document.getElementById("products").innerHTML = "";
			document.getElementById("showButton").value = "Show Products";
		}

	}
</script>

<s:form action="createProduct">
	<s:textfield label="Product Name" key="name" />
	<s:textfield label="Product Code" key="code" />
	<s:textfield label="Product Price" key="price" />
	<s:textfield label="Product Description" key="desc" />
	<s:select list="categories" headerKey=""
		headerValue="Select Product Category" name="categoryId" listKey="id"
		listValue="name" />
	<s:select list="stores" headerKey="" headerValue="Select Store"
		name="storeId" listKey="id" listValue="storeName" />
	<s:submit value="Create" />
	<br>
</s:form>


<input type="button" value="Show Products" id="showButton" size="10"
	onclick="showProducts();" />
	
<p id="products"></p>

<%@include file="../includes/footer.jsp"%>