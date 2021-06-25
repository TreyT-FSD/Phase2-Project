<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.app.entity.Booking"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>flyAway - Booking Details</title>
</head>
<body>
	<%
		Booking booking = (Booking)request.getAttribute("booking");
		
		if(booking != null){
			request.setAttribute("flight", booking.getFlight());
			request.setAttribute("passengers", booking.getPassengers());
			request.setAttribute("totalPrice", booking.getPassengers().size() * booking.getFlight().getTicketPrice());
			request.setAttribute("tripDate", booking.getTripDate());
		}
		else{
			request.setAttribute("errorMsg", "No Flight has been select or booked.");
			request.getRequestDispatcher("error.jsp").forward(request, response);
		}
	%>

	<br>
	Trip Date: ${tripDate}
	<br>
	<br>
	<table border="1" width="90%">
		<tr>
			<th>Flight Id</th>
			<th>Origin</th>
			<th>Destination</th>
			<th>Airline</th>
			<th>Ticket Price</th>
		</tr>
		<tr>
			<td>${flight.getFlightId()}</td>
			<td>${flight.getOrigin().getLocationName()}</td>
			<td>${flight.getDestination().getLocationName()}</td> 
			<td>${flight.getAirline().getAirlineName()}</td> 
			<td>$${flight.getTicketPrice()}</td>
		</tr>
	</table>
	<h1>Passenger Information</h1>
	<table border="1" width="90%">
		<tr>
			<th>First Name</th>
			<th>Last Name</th>
		</tr>
		<c:forEach var="pass" items="${passengers}">
			<tr>
				<td>${pass.getFirstName()}</td>
				<td>${pass.getLastName()}</td>
			</tr>
		</c:forEach>
	</table>
	<br>
	Total Price: $${totalPrice}
</body>
</html>