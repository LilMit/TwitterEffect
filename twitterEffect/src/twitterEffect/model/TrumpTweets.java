package twitterEffect.model;

import java.sql.Timestamp;
import java.sql.Date;

public class TrumpTweets extends Tweets{
	protected long TweetId;
	protected int Favorites;
	
	public TrumpTweets(String linkToTweet, Date tweetDate, Timestamp tweetTime, String content, int retweets,
			String personName, long tweetId, int favorites) {
		super(linkToTweet, tweetDate, tweetTime, content, retweets, personName);
		this.TweetId = tweetId;
		this.Favorites = favorites;
	}
	
	public TrumpTweets(String linkToTweet, long tweetId, int favorites) {
		super(linkToTweet);
		this.TweetId = tweetId;
		this.Favorites = favorites;		
	}
	
	public long getTweetId() {
		return TweetId;
	}

	public void setTweetId(long tweetId) {
		TweetId = tweetId;
	}

	public int getFavorites() {
		return Favorites;
	}

	public void setFavorites(int favorites) {
		Favorites = favorites;
	}
	
	

}
