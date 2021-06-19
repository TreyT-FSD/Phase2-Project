<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page import="com.app.dao.AirlineDAO"%>


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
				request.setAttribute("list", AirlineDAO.getAirlines());
			}
			else{
				//send user to login page
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
		
		%>
		
		<%@ include file="nav.html" %>
		<a href='LogoutServlet'>Logout</a>
		<br>
		<br>
		<H1>Update airlines and flights here</H1>
		<br>
		<br>
		<h2>Airlines</h2>
		<table border="1" width="90%">
			<tr>
				<th>Id</th>
				<th>Name</th>
				<th>Edit</th>
				<th>Delete</th>
			</tr>
			<c:forEach var="elmt" items="${list}" >  
				<tr>
					<td>${elmt.getAirlineId()}</td><td>${elmt.getAirlineName()}</td> 
					<td><a href="editform.jsp?id=${elmt.getAirlineId()}">Edit</a></td>  
					<td><a href="deleteuser.jsp?id=${elmt.getAirlineId()}">Delete</a></td>
				</tr>  
			</c:forEach> 
		</table>
		<br>
		<form action="AirlineController" method="post">
			<label for="airlineName">Airline Name:</label>
			<input type="text" id="airlineName" name="airlineName">
			<input type="submit" value="Add Airline">
		</form>
		
		
		
		
		
	</body>
</html>