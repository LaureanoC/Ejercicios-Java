<%@page import="java.util.LinkedList"%>
<%@page import="entities.Persona"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>UserManagement</title>

<%
	Persona p = (Persona)session.getAttribute("usuario");
	LinkedList<Persona> lp = (LinkedList<Persona>)request.getAttribute("listaPersonas");
%>
</head>
<body>
	<div>
		<table>
			<thead>
			<tr>
				<th>id</th>
				<th>nombre</th>
				<th>apellido</th>
				<th>email</th>
				<th>tel</th>
				<th>hab</th>
				<th></th>
				<th></th>
			</tr>
			</thead>
			<tbody>
			<% for (Persona per : lp){ %>
				<tr>
					<td><%=per.getId()%></td>
					<td><%= per.getNombre() %></td>
					<td><%= per.getApellido() %></td>
					<td><%= per.getEmail() %></td>
					<td><%= per.getTel() %></td>
					<td>
						<input type="checkbox" <%= per.isHabilitado()?"checked":"" %>disabled>	
					</td>
					<td></td>
					<td></td>
				</tr>
				
			<%} %>
			</tbody>
		</table>
		
	</div>
</body>
</html>