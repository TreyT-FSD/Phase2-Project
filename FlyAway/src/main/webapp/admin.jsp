<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>FlyAway - Admin</title>
	</head>
	<body>
		<%
			if(session == null){
				//send user to login page
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
		
			String isAuthenticated = (String) session.getAttribute("authenticated");
			
			if(isAuthenticated != null && isAuthenticated.compareTo("True") == 0){
				//user is authorized to view this page
				//out.print("Login Successful!");
			}
			else{
				//send user to login page
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
		
		%>
		
		<%@ include file="nav.html" %>
		<br>
		<br>
		Update airlines and flights here
		<br>
		<br>
		<a href='LogoutServlet'>Logout</a>
		
	</body>
</html>