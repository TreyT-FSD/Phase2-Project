<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>FlyAway - login</title>
	</head>
	<body>
		<%@ include file="nav.html" %>
		
		<h1>Admin Login</h1>
		<form action="LoginServlet" method="post">

		 	<label for="username">User Name:</label>
		 	<input type="text" id="username" name="username"><br><br>
		  
	  		<label for="password">Password:</label>
	  		<input type="password" id="password" name="password"><br><br>
	  
	  		<input type="submit" value="Submit">
		  
		</form>
	</body>
</html>