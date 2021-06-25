<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<%@page import="com.app.dao.*"%>
<%@page import="com.app.entity.*" %>

<html>
	<head>
		<title>FlyAway Homepage</title>
	</head>
	<body>
	<c:set var = "now" value="<%= new java.util.Date()%>" />
	
	
	<%
		request.setAttribute("locationList", LocationDAO.getLocations());
		request.setAttribute("flightList", FlightDAO.getFlights());
		
		//List<Location> origins = new ArrayList<Location>();
	%>
	
		<%@ include file="nav.html" %>
		<h1>FlyAway - Flight Search</h1>
		<form action="FlyAwayController" method="get">
			
			<label for="start">Trip Date:</label>
			<input type="date" id="tripDate" name="tripDate" 
				value="<fmt:formatDate pattern = "yyyy-MM-dd" value = "${now}" />" 
				min="<fmt:formatDate pattern = "yyyy-MM-dd" value = "${now}" />" 
				required="required">
		
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
			
			<label for="passengerCount">Passengers</label>
			<select id="passengerCount" name="passengerCount" required="required" style="width: 10%;">
				<c:forEach var="count" begin="1" end="9" >
					<option value="${count}">${count}</option>
				</c:forEach>
			</select>
			
			<input type="submit" value="Search Flights">
			
		</form>
		<c:if test="${searchActionMsg != null && searchActionMsg.length() != 0}">${searchActionMsg}</c:if>
		
	</body>
</html>
