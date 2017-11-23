<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
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
			/* console.log(this.responseText); */
			var student = JSON.parse(this.responseText);
			document.getElementById('content').innerHTML = 
				'Name: ' + student.name +
				'<img src="/student/avatar/' + student.id + '"/>';
			
			var dialog =  document.getElementById('viewStudent');
			dialog.show();
		};
		xmlHttp.send();
	}
</script>
</head>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<body>
	<tiles:insertDefinition name="studentTemplate">
		<tiles:putAttribute name="body">
			<h2>List of Students</h2>
			<form action="list" method="get">
				<table border="1">
					<tr>
						<td colspan="4"><input type="text" name="q" size="30"/></td>
						<td><input type="submit" value="Submit"/></td>
					</tr>
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
			</form>
			<dialog id="viewStudent" style="width:50%;border:1px dotted black;">
				<div id="content"></div>
				<button id="hide">Close</button>
			</dialog>
			
			<script>
				(function(){
					var dialog = document.getElementById('viewStudent');
					document.getElementById('hide').onclick = function(){
						dialog.close();
					};
				})();
			</script>
		</tiles:putAttribute>
	</tiles:insertDefinition>
</body>
</html>