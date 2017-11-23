<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h2>Please input student information</h2>
	<form:form action="save" method="post">
	<form:hidden path="id"/>
		<table>
			<tr>
				<td>Name</td>
				<td><form:input path="name"/><form:errors path="name"/> </td>
			</tr>
			<tr>
				<td>Age:</td>
				<td><form:input path="age" type="number"/> <form:errors path="age" /> </td>
			</tr>
			<tr colspan="2">
				<input type="submit" value="Submit">
			</tr>
		</table>
	</form:form>
	
	<c:if test="${id != null}">
		<h1>Please upload a image</h1>
		<form:form action="avatar/save" enctype="multipart/form-data">
			<input type="hidden" name="id" value="${id}" >
			<input type="file" name="file" >
			<input type="submit" value="Upload" >
		</form:form>
	</c:if>
</body>
</html>