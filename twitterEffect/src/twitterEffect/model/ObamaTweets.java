package twitterEffect.model;

import java.sql.Time;
import java.util.Date;

public class ObamaTweets extends Tweets {
	protected String UserName;
	protected int Likes;
	protected String TweetImageURL;
	
	public ObamaTweets(String linkToTweet, Date tweetDate, Time tweetTime, String content, int retweets,
			Person personName, String userName, int likes, String tweetImageURL) {
		super(linkToTweet, tweetDate, tweetTime, content, retweets, personName);
		this.UserName = userName;
		this.Likes = likes;
		this.TweetImageURL = tweetImageURL;
	}
	
	public ObamaTweets(String linkToTweet, String userName, int likes, String tweetImageURL) {
		super(linkToTweet);
		this.UserName = userName;
		this.Likes = likes;
		this.TweetImageURL = tweetImageURL;	
	}
	
	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public int getLikes() {
		return Likes;
	}

	public void setLikes(int likes) {
		Likes = likes;
	}

	public String getTweetImageURL() {
		return TweetImageURL;
	}

	public void setTweetImageURL(String tweetImageURL) {
		TweetImageURL = tweetImageURL;
	}

}
