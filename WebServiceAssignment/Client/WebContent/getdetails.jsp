<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="org.json.JSONArray,org.json.JSONException,org.json.JSONObject"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Get Employee Details</title>
</head>
<body>

	<%
		JSONArray jsonarray = new JSONArray(
				(String) (request.getAttribute("json")));
	%>
	<%!JSONObject object;%>
	<%
		int index = 0;
	%>
	<%
		while (index < (jsonarray.length())) {
			object = jsonarray.getJSONObject(index);
	%>
	Employee
	<%=index + 1%>:
	<br> Id:
	<%=object.getInt("id")%><br> EmployeeName:<%=object.getString("employeeName")%><br>
	Salary:<%=object.getInt("salary")%><br> Department:<%=object.getString("department")%><br>
	<br>
	<%
		index++;
		}
	%>
	<br>
	<a href="welcome.jsp">Home</a>

</body>
</html>