
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>FlyAway - Booking</title>
</head>
<body>
	<%
		request.setAttribute("booking", session.getAttribute("completedBooking"));
	%>
	<%@ include file="nav.html" %>
	<h1>Booking Summary and Payment</h1>
	<%@ include file="booking-details.jsp" %>
	<br>
	<form action="booking-complete.jsp" method="post">
		<input type="submit" value="Submit Payment">
	</form>
</body>
</html>