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
	<form action="findstockindex" method="post">
		<h1>Search for a Person by Occupation</h1>
		<p>
			<label for="indexticker">IndexTicker</label>
			<input id="indexticker" name="indexticker" value="${fn:escapeXml(param.indexticker)}">
		</p>
		<p>
			<input type="submit">
			<br/><br/><br/>
			<span id="successMessage"><b>${messages.success}</b></span>
		</p>
	</form>
	<br/>
	<div id="stockIndexCreate"><a href="stockindexcreate">Create StockIndex</a></div>
	<br/>
	<h1>Matching StockIndex</h1>
        <table border="1">
            <tr>
                <th>IndexTicker</th>
                <th>IndexName</th>
                <th>Delete StockIndex</th>
                <th>Update StockIndex</th>
            </tr>
            <c:forEach items="${stockindex}" var="stockindex" >
                <tr>
                    <td><c:out value="${stockindex.getIndexTicker()}" /></td>
                    <td><c:out value="${stockindex.getIndexName()}" /></td>
                    <td><a href="stockindexdelete?indexticker=<c:out value="${stockindex.getIndexTicker()}"/>">Delete</a></td>
                    <td><a href="stockindexupdate?indexticker=<c:out value="${stockindex.getIndexTicker()}"/>">Update</a></td>
                </tr>
            </c:forEach>
       </table>
</body>
</html>
