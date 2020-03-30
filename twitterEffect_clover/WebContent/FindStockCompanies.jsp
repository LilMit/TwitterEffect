<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Find Stock Companies</title>
</head>
<body>
	<form action="findstockcompanies" method="post">
		<h1>Search for a StockCompanies by Sector Name</h1>
		<p>
			<label for="sector">Sector</label>
			<input id="sector" name="sector" value="${fn:escapeXml(param.sector)}">
		</p>
		<p>
			<input type="submit">
			<br/><br/><br/>
			<span id="successMessage"><b>${messages.success}</b></span>
		</p>
	</form>
	<br/>
	<div id="stockCompaniesCreate"><a href="stockcompaniescreate">Create Stock Companies</a></div>
	<br/>
	<h1>Matching StockCompanies</h1>
        <table border="1">
            <tr>
                <th>CompanyTicker</th>
                <th>Company</th>
                <th>MarketCap</th>
                <th>MarketCapGroup</th>
                <th>Sector</th>
                <th>IndexTicker</th>
                <th>Delete BlogUser</th>
                <th>Update BlogUser</th>
            </tr>
            <c:forEach items="${stockCompanies}" var="stockCompany" >
                <tr>
                    <td><c:out value="${stockCompany.getCompanyTicker()}" /></td>
                    <td><c:out value="${stockCompany.getCompany()}" /></td>
                    <td><c:out value="${stockCompany.getMarketCap()}" /></td>
                    <td><c:out value="${stockCompany.getMarketCapGroup()}" /></td>
                    <td><c:out value="${stockCompany.getSector()}" /></td>
                    <td><c:out value="${stockCompany.getStockIndex().getIndexTicker()}" /></td>                    
                    <td><a href="stockcompaniesdelete?companyticker=<c:out value="${stockCompany.getCompanyTicker()}"/>">Delete</a></td>
                    <td><a href="stockcompaniesupdate?companyticker=<c:out value="${stockCompany.getCompanyTicker()}"/>">Update</a></td>
                </tr>
            </c:forEach>
       </table>
</body>
</html>
