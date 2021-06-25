<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>FlyAway - Booking Complete</title>
</head>
<body>
	<%
		request.setAttribute("booking", session.getAttribute("completedBooking"));
	%>
	<%@ include file="booking-details.jsp" %>
	<h2>Payment was Successful</h2>
</body>
</html>