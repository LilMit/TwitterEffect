<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create a StockCompanies</title>
</head>
<body>
	<h1>Create StockCompanies</h1>
	<form action="stockcompaniescreate" method="post">
		<p>
			<label for="companyticker">CompanyTicker</label>
			<input id="companyticker" name="companyticker" value="">
		</p>
		<p>
			<label for="company">Company</label>
			<input id="company" name="company" value="">
		</p>
		<p>
			<label for="marketcap">MarketCap</label>
			<input id="marketcap" name="marketcap" value="">
		</p>
		<p>
			<label for="marketcapgroup">MarketCapGroup</label>
			<input id="marketcapgroup" name="marketcapgroup" value="">
		</p>
		<p>
			<label for="sector">Sector</label>
			<input id="sector" name="sector" value="">
		</p>
		<p>
			<label for="indexticker">IndexTicker</label>
			<input id="indexticker" name="indexticker" value="">
		</p>
		<p>
			<input type="submit">
		</p>
	</form>
	<br/><br/>
	<p>
		<span id="successMessage"><b>${messages.success}</b></span>
	</p>
</body>
</html>