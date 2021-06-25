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
	<%@ include file="nav.html" %>
	<%
		request.setAttribute("passengerCount", request.getParameter("passengerCount"));
		request.setAttribute("id", request.getParameter("id"));
		request.setAttribute("tripDate", request.getParameter("tripDate"));
	%>
	
	<h1>Enter Passenger Details</h1>
	<form action="FlyAwayController" method="post">
		<table border="1" width="90%">
			<tr>
				<th></th>
				<th>First Name</th>
				<th>Last Name</th>
			</tr>
			<c:forEach var="count" begin="1" end="${passengerCount}" >
				<tr>
					<td>Passenger ${count}</td>
					<td><input type="text" id="passengerFirstName${count}" name="passengerFirstName${count}" required="required" style="width:99%"></td>
					<td><input type="text" id="passengerLastName${count}" name="passengerLastName${count}" required="required" style="width:99%"></td>
				</tr>
			</c:forEach>
		</table>
		<input type="text" id="id" name="id" value="${id}" hidden="true">
		<input type="text" id="passengerCount" name="passengerCount" value="${passengerCount}" hidden="true">
		<input type="text" id="tripDate" name="tripDate" value="${tripDate}" hidden="true">
		<br>
		<input type="submit" value="Submit Passenger Details">
	</form>

</body>
</html>