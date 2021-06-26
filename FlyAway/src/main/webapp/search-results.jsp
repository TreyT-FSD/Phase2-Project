<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>FlyAway - Search Results</title>
</head>
<body>
	<%@ include file="nav.html" %>
	<%
		request.setAttribute("passengerCount", request.getParameter("passengerCount"));
		request.setAttribute("tripDate", request.getParameter("tripDate"));
	%>
	<h1>Flight Search Results</h1>
	<table border="1" width="90%">
		<tr>
			<th>Flight Id</th>
			<th>Origin</th>
			<th>Destination</th>
			<th>Airline</th>
			<th>Ticket Price</th>
			<th>Delete</th>
		</tr>
		<c:forEach var="elmt" items="${matchingFlights}" >  
			<tr>
				<td>${elmt.getFlightId()}</td>
				<td>${elmt.getOrigin().getLocationName()}</td>
				<td>${elmt.getDestination().getLocationName()}</td> 
				<td>${elmt.getAirline().getAirlineName()}</td> 
				<td>$${elmt.getTicketPrice()}</td>  
				<td><a href="booking.jsp?tripDate=${tripDate}&id=${elmt.getFlightId()}&passengerCount=${passengerCount}">Select</a></td>
			</tr>  
		</c:forEach>
</body>
</html>