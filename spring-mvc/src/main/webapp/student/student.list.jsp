<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">
	function view(id){
		var xmlHttp = new XMLHttpRequest();
		
		xmlHttp.open("GET", "json/" + id, true);
		xmlHttp.onload = function(){
			if(this.status != 200) return;
			console.log(this.responseText);
		};
		xmlHttp.send();
	}
</script>
</head>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<body>
	<table border="1">
		<tr>
			<td>Id</td>
			<td>Name</td>
			<td>Age</td>
		<tr>
		<c:forEach items="${students}" var="student">
			<tr>
				<td>${student.id}</td>
				<td><a href="javascript:view(${student.id})">${student.name}</a></td>
				<td>${student.age}</td>
				<td><a href="delete/${student.id }">delete</a></td>
				<td><a href="${student.id }">edit</a></td>
				
			</tr>
		</c:forEach>
	</table>
</body>
</html>