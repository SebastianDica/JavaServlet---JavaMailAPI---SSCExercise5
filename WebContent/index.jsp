<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SSC</title>
</head>
<body>
An e-mail application for GMail.
<form action="Controller" method="POST">
	E-mail address: <input type="text" name="username"> <br>
	Password: <input type="password" name="password"> <br>
	<input type="submit" value="Login">
	<% Object s = session.getAttribute("statusLog"); %>
    <% if (s==null) { %> 
   	<% } else { %>
   	<%=session.getAttribute("statusLog") %>
    <% } %>
</form>
</body>
</html>