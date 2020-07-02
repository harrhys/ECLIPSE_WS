<%@include file="../includes/header.jsp"%>

<script type="text/javascript">
	function showStores() {
		var request = new XMLHttpRequest();
		var text = document.getElementById("stores").innerHTML;
		if (text == "") {
			request.onreadystatechange = function() {
				if (this.readyState == 4 && this.status == 200) {
					var response = this.responseText;

					document.getElementById("stores").innerHTML = response;
					document.getElementById("showButton").value = "Hide Stores";
				}
			};
			request.open("GET", "merchant/showStores", true);
			request.send();
		} else {
			document.getElementById("stores").innerHTML = "";
			document.getElementById("showButton").value = "Show Stores";
		}

	}
</script>

	<s:form action="createStore">
		<s:textfield  label="Store Name" key="name"></s:textfield>
		<s:textfield  label="Store Description" key="desc" ></s:textfield>
		<s:submit value="Create"/>
		<br></s:form>
		
		<s:if test="%{#store!=null}">
Store Created Successfully.
</s:if>
		
		<input type="button" value="Show Stores" id="showButton" size="10"
	onclick="showStores();" />
	
<p id="stores"></p>

<%@include file="../includes/footer.jsp"%>