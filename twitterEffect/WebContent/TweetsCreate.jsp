<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create Tweets</title>
</head>
<body>
	<h1>Create Tweet</h1>
	<form action="tweetcreate" method="post">
		<p>
			<label for="LinkToTweet">LinkToTweet</label>
			<input id="linkToTweet" name="LinkToTweet" value="">
		</p>
		<p>
			<label for="tweetDate">TweetDate (yyyy-mm-dd)</label>
			<input id="tweetDate" name="TweetDate" value="">
		</p>
		<p>
			<label for="tweetTime">TweetTime</label>
			<input id="tweetTime" name="TweetTime" value="">
		</p>
		<p>
			<label for="content">Content</label>
			<input id="content" name="Content" value="">
		</p>
		<p>
			<label for="retweets">Retweets</label>
			<input id="retweets" name="Retweets" value="">
		</p>
		<p>
			<label for="personName">PersonName</label>
			<input id="personName" name="PersonName" value="">
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