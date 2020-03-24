<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Find Tweets</title>
</head>
<body>
	<form action="findtweets" method="post">
		<h1>Search for a Tweets by PersonName</h1>
		<p>
			<label for="personName">PersonName</label>
			<input id="personName" name="personName" value="${fn:escapeXml(param.pesonName)}">
		</p>
		<p>
			<input type="submit">
			<br/><br/><br/>
			<span id="successMessage"><b>${messages.success}</b></span>
		</p>
	</form>
	<br/>
	<div id="tweetCreate"><a href="tweetcreate">Create Tweets</a></div>
	<br/>
	<h1>Matching Tweets</h1>
        <table border="1">
            <tr>
                <th>LinkToTweet</th>
                <th>TweetDate</th>
                <th>TweetTime</th>
                <th>Content</th>
                <th>Retweets</th>
                <th>PersonName</th>
                <th>Delete Tweet</th>
                <th>Update Tweet</th>
            </tr>
            <c:forEach items="${tweets}" var="tweet" >
                <tr>
                    <td><c:out value="${tweet.getLinkToTweet()}" /></td>
                    <td><c:out value="${tweet.getTweetToDate()}" /></td>
                    <td><c:out value="${tweet.getTweetToTime()}" /></td>
                    <td><c:out value="${tweet.getContent()}" /></td>
                    <td><c:out value="${tweet.getRetweets()}" /></td>
                    <td><c:out value="${tweet.getPersonName()}" /></td>
                    <td><a href="tweetsdelete?linkToTweet=<c:out value="${tweet.getLinkToTweet}"/>">Delete</a></td>
                    <td><a href="tweetsupdate?linkToTweet=<c:out value="${tweet.getLinkToTweet()}"/>">Update</a></td>
                </tr>
            </c:forEach>
       </table>
</body>
</html>
