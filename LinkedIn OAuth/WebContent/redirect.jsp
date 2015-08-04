<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="org.json.JSONArray,org.json.JSONException,org.json.JSONObject"%>
<%@ page import="edu.learn.OAuth.AccessToken"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>LinkedIn</title>
</head>
<body>

	<%String code=request.getParameter("code"); %>

	<%AccessToken token=new AccessToken(); %>

	<%String json=token.getAccessToken(code);%>


	<%=token.getProfile(json)%>
	<%=token.postData(json) %>
</body>
</html>