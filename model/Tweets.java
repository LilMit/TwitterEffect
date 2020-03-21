package twitterEffect.model;

import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Date;

public class Tweets {
	protected String LinkToTweet;
	protected Date TweetDate;
	protected Timestamp TweetTime;
	protected String Content;
	protected int Retweets;
	protected String PersonName;
	
	public Tweets(String linkToTweet, Date tweetDate, Timestamp tweetTime, String content, int retweets,
			String personName) {
		this.LinkToTweet = linkToTweet;
		this.TweetDate = tweetDate;
		this.TweetTime = tweetTime;
		this.Content = content;
		this.Retweets = retweets;
		this.PersonName = personName;
	}
	
	public Tweets(String linkToTweets) {
		this.LinkToTweet = linkToTweets;
	}
	
	public String getLinkToTweet() {
		return LinkToTweet;
	}

	public void setLinkToTweet(String linkToTweet) {
		this.LinkToTweet = linkToTweet;
	}

	public Date getTweetDate() {
		return TweetDate;
	}

	public void setTweetDate(Date tweetDate) {
		this.TweetDate = tweetDate;
	}

	public Timestamp getTweetTime() {
		return TweetTime;
	}

	public void setTweetTime(Timestamp tweetTime) {
		this.TweetTime = tweetTime;
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		this.Content = content;
	}

	public int getRetweets() {
		return Retweets;
	}

	public void setRetweets(int retweets) {
		this.Retweets = retweets;
	}

	public String getPersonName() {
		return PersonName;
	}

	public void setPersonName(String personName) {
		this.PersonName = personName;
	}

	
	
	

}
