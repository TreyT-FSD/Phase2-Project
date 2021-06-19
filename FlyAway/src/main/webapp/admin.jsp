<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page import="com.app.dao.*"%>


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
				
				//request.setAttribute("airlineList", AirlineDAO.getUniqueAirlines());
				//request.setAttribute("locationList", LocationDAO.getUniqueLocations());
				//request.setAttribute("flightList", FlightDAO.getFlights());
				
				request.setAttribute("airlineList", AirlineDAO.getAirlines());
				request.setAttribute("locationList", LocationDAO.getLocations());
				request.setAttribute("flightList", FlightDAO.getFlights());
				
				
				
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
		<H1>Update Airlines, Locations, and Flights</H1>
		<br>
		<br>
		<h2>Airlines</h2>		
		<table border="1" width="90%">
			<tr>
				<th>Id</th>
				<th>Name</th>
				<th>Delete</th>
			</tr>
			<c:forEach var="elmt" items="${airlineList}" >  
			<tr>
				<td>${elmt.getAirlineId()}</td>
				<td>${elmt.getAirlineName()}</td>
				<td><a href="AirlineController?action=delete&id=${elmt.getAirlineId()}">Delete</a></td>
			</tr>  
			</c:forEach> 
		</table>
		<!-- <c:if test="${airlineActionMsg != null && airlineActionMsg.length() != 0}">${airlineActionMsg}</c:if> -->
		<form action="AirlineController" method="post">
			<label for="airlineName">Airline Name:</label>
			<input type="text" id="airlineName" name="airlineName" required="required">
			<input type="submit" value="Add Airline" >&nbsp;<c:if test="${airlineActionMsg != null && airlineActionMsg.length() != 0}">${airlineActionMsg}</c:if>
		</form>
		
		
		<h2>Locations</h2>
		<table border="1" width="90%">
			<tr>
				<th>Id</th>
				<th>Location</th>
				<th>Delete</th>
			</tr>
			<c:forEach var="elmt" items="${locationList}" >  
			<tr>
				<td>${elmt.getLocationId()}</td>
				<td>${elmt.getLocationName()}</td>  
				<td><a href="LocationController?action=delete&id=${elmt.getLocationId()}">Delete</a></td>
			</tr>  
			</c:forEach> 
		</table>
		
		<form action="LocationController" method="post">
			<label for="location">Location</label>
			<input type="text" id="locationName" name="locationName" required="required">
			<input type="submit" value="Add Location">&nbsp;<c:if test="${locationActionMsg != null && locationActionMsg.length() != 0}">${locationActionMsg}</c:if>
		</form>
		
		
		
		<h2>Flights</h2>
		<table border="1" width="90%">
			<tr>
				<th>Id</th>
				<th>Origin</th>
				<th>Destination</th>
				<th>Airline</th>
				<th>Price</th>
				<th>Delete</th>
			</tr>
			<c:forEach var="elmt" items="${flightList}" >  
				<tr>
					<td>${elmt.getFlightId()}</td>
					<td>${elmt.getOrigin().getLocationName()}</td>
					<td>${elmt.getDestination().getLocationName()}</td> 
					<td>${elmt.getAirline().getAirlineName()}</td> 
					<td>${elmt.getTicketPrice()}</td>  
					<td><a href="FlightController?action=delete&id=${elmt.getFlightId()}">Delete</a></td>
				</tr>  
			</c:forEach> 
		</table>
		
		<form action="FlightController" method="post">
			<label for="originName">Origin:</label>
			<select id="originName" name="originName" required="required" style="width: 10%;">
			<option value=""></option>
			<c:forEach var="elmt" items="${locationList}" >
				<option value="${elmt.getLocationName()}">${elmt.getLocationName()}</option>
			</c:forEach>
			</select>
			
			<label for="destinationName">Destination:</label>
			<select id="destinationName" name="destinationName" required="required" style="width: 10%;">
			<option value=""></option>
			<c:forEach var="elmt" items="${locationList}" >
				<option value="${elmt.getLocationName()}">${elmt.getLocationName()}</option>
			</c:forEach>
			</select>
			
			
			<label for="airlineName">Airline Name:</label>
			<select id="airlineName" name="airlineName" required="required" style="width: 10%;">
			<option value=""></option>
			<c:forEach var="elmt" items="${airlineList}" >
				<option value="${elmt.getAirlineName()}">${elmt.getAirlineName()}</option>
			</c:forEach>
			</select>
			
			<label for="ticketPrice">Ticket Price:</label>
			<input type="text" id="ticketPrice" name="ticketPrice" required="required" style="width: 10%;">
			
			<input type="submit" value="Add Flight">&nbsp;<c:if test="${flightActionMsg != null && flightActionMsg.length() != 0}">${flightActionMsg}</c:if>
		</form>
		
		
		
		
		
	</body>
</html>