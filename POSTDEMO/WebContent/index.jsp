<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Product</title>
</head>
<body>
	<form action="rest/product/add" method="post">
		Enter ProductId:<input type="text" name="id" /><br /> <br /> Enter
		ProductName:<input type="text" name="productName" /><br /> <br /> Enter
		Price:<input type="text" name="price" /><br /> <br /> <input
			type="submit" value="Add Product" />
	</form>
</body>
</html>