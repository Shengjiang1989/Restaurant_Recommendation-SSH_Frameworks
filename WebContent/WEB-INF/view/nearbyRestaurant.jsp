<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Restaurants</title>

</head>
<body>

<div id = "wrapper">

	<div id = "header">
		<h2> Restaurants List</h2>
	</div>
</div>

<div id = "container">

	<div id = "content">
		
		<table>
		
			<tr>
				<th>business id</th>
				<th>name</th>
				<th>city</th>
			</tr>
			
			<c:forEach var="temp" items="${restaurants}">
				
				<tr>
					<td> ${temp.businessId} </td>
					<td> ${temp.name} </td>
					<td> ${temp.city} </td>
				</tr>
			</c:forEach>
		</table>
		
	</div>
	
</div>

</body>
</html>