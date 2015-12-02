<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SSC</title>
</head>
<body>
	<%= session.getAttribute("status") %> <br>
	<form action="ControllerMailCreateServlet" method="POST">
	From : <%= session.getAttribute("userID") %> <br>
	To: <input type="text" name="to"> <br>
	Subject: <input type="text" name="subject"> <br>
	Content: <textarea rows="10" cols="50" name=content>
	</textarea> <br>
	<input type="submit" value="Send" name="Send">
	<input type="submit" value="Log out" name="Log out">
	</form>
</body>
</html>