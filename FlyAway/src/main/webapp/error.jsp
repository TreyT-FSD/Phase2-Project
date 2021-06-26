<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>FlyAway - Error</title>
</head>
<body>
	<%@ include file="nav.html" %>
	An error occurred while processing the request. Please return to the homepage.
	
	<c:if test="${errorMsg != null && errorMsg.length() != 0}">
		<br>
		<br>
		Details:
		<br>
		${errorMsg}
	</c:if>

</body>
</html>