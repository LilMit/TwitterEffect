<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Find a Person</title>
</head>
<body>
	<form action="findperson" method="post">
		<h1>Search for a Person by Occupation</h1>
		<p>
			<label for="occupation">Occupation</label>
			<input id="occupation" name="occupation" value="${fn:escapeXml(param.occupation)}">
		</p>
		<p>
			<input type="submit">
			<br/><br/><br/>
			<span id="successMessage"><b>${messages.success}</b></span>
		</p>
	</form>
	<br/>
	<div id="personCreate"><a href="personcreate">Create Person</a></div>
	<br/>
	<h1>Matching Persons</h1>
        <table border="1">
            <tr>
                <th>PersonName</th>
                <th>Occupation</th>
                <th>Delete Person</th>
                <th>Update Person</th>
            </tr>
            <c:forEach items="${persons}" var="person" >
                <tr>
                    <td><c:out value="${person.getPersonName()}" /></td>
                    <td><c:out value="${person.getOccupation()}" /></td>
                    <td><a href="persondelete?username=<c:out value="${person.getPersonName()}"/>">Delete</a></td>
                    <td><a href="personupdate?username=<c:out value="${person.getPersonName()}"/>">Update</a></td>
                </tr>
            </c:forEach>
       </table>
</body>
</html>
