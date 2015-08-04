<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script>
	function accessRequest() {
		var client_id = "75ojrwam8rrizp"
		var state = Math.floor((Math.random() * 1000000) + 1);
		var url = "https://www.linkedin.com/uas/oauth2/authorization?response_type=code&client_id="
				+ client_id
				+ "&redirect_uri=https://localhost:8443/LinkedInTest/redirect.jsp&state="
				+ state + "&scope=r_basicprofile"
		alert(url);
		window.location.href = url;
	}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>LINKEDIN REST API DEMO</title>
</head>
<body>

	<button onclick="accessRequest()">Get access Token</button>
</body>
</html>