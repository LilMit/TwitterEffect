package twitterEffect.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import twitterEffect.model.Tweets;

public class TweetsDao {
	
	protected ConnectionManager connectionManager;
	
	// Single pattern: instantiation is limited to one object.
	private static TweetsDao instance = null;
	protected TweetsDao() {
		connectionManager = new ConnectionManager();
	}
	public static TweetsDao getInstance() {
		if(instance == null) {
			instance = new TweetsDao();
		}
		return instance;
	}

	/**
	 * Save Tweets instance by storing it in your MySQL instance.
	 * This runs a INSERT statement.
	 */
	public Tweets create(Tweets tweet) throws SQLException {
		String insertTweet = 
				"INSERT INTO Tweets(LinkToTweet,TweetDate,TweetTime,Content,Retweets,PersonName) " +
				"VALUES(?,?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertTweet);
			insertStmt.setString(1, tweet.getLinkToTweet());
			insertStmt.setDate(2, tweet.getTweetDate());
			insertStmt.setTimestamp(3, tweet.getTweetTime());
			insertStmt.setString(4, tweet.getContent());
			insertStmt.setInt(5, tweet.getRetweets());
			insertStmt.setString(6, tweet.getPersonName());
			insertStmt.executeUpdate();
			return tweet;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(insertStmt != null) {
				insertStmt.close();
			}
		}
	}

	/**
	 * Update the Content of the Tweets instance.
	 * This runs a UPDATE statement.
	 */
	public Tweets updateContent(Tweets tweet, String content) throws SQLException {
		String updateTweet = "UPDATE Tweets SET Content=? WHERE LinkToTweet=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateTweet);
			updateStmt.setString(1, content);
			updateStmt.setString(2, tweet.getLinkToTweet());
			updateStmt.executeUpdate();
			
			// Update the person param before returning to the caller.
			tweet.setContent(content);;
			return tweet;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(updateStmt != null) {
				updateStmt.close();
			}
		}
	}

	/**
	 * Delete the Persons instance.
	 * This runs a DELETE statement.
	 */
	public Tweets delete(Tweets tweet) throws SQLException {
		String deleteTweet = "DELETE FROM Tweets WHERE LinkToTweet=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteTweet);
			deleteStmt.setString(1, tweet.getLinkToTweet());
			deleteStmt.executeUpdate();

			// Return null so the caller can no longer operate on the Persons instance.
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(deleteStmt != null) {
				deleteStmt.close();
			}
		}
	}

	/**
	 * Get the Tweets record by fetching it from your MySQL instance.
	 * This runs a SELECT statement and returns a single Tweets instance.
	 */
	public Tweets getTweetsByLinkToTweet(String linkToTweet) throws SQLException {
		String selectTweet = 
				"SELECT * " +
				"FROM Tweets " +
				"WHERE LinkToTweet=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectTweet);
			selectStmt.setString(1, linkToTweet);
			results = selectStmt.executeQuery();
			if(results.next()) {
				String resultLinkToTweet = results.getString("LinkToTweet");
				Date tweetDate = results.getDate("TweetDate");
				Timestamp tweetTime = results.getTimestamp("TweetTime");
				String content = results.getString("Content");
				int retweets = results.getInt("Retweets");			
				String userName = results.getString("UserName");
				Tweets tweet = new Tweets(resultLinkToTweet,tweetDate,tweetTime,content,retweets,userName);
				return tweet;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(selectStmt != null) {
				selectStmt.close();
			}
			if(results != null) {
				results.close();
			}
		}
		return null;
	}

	/**
	 * Get the matching Tweets records by fetching from your MySQL instance.
	 * This runs a SELECT statement and returns a list of matching Persons.
	 */
	public List<Tweets> getTweetsByPersonName(String personName) throws SQLException {
		List<Tweets> tweets = new ArrayList<Tweets>();
		String selectTweets =
			"SELECT LinkToTweet,TweetDate,TweetTime,Content,Retweets,PersonName " + 
			"FROM Tweets " + 
			"WHERE PersonName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectTweets);
			selectStmt.setString(1, personName);
			results = selectStmt.executeQuery();
			while(results.next()) {
				String linkToTweet = results.getString("LinkToTweet");
				Date tweetDate = results.getDate("TweetDate");
				Timestamp tweetTime = results.getTimestamp("TweetTime");
				String content = results.getString("Content");
				int retweets = results.getInt("Retweets");			
				String resultUserName = results.getString("UserName");
				Tweets tweet = new Tweets(linkToTweet,tweetDate,tweetTime,content,retweets,resultUserName);
				tweets.add(tweet);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(selectStmt != null) {
				selectStmt.close();
			}
			if(results != null) {
				results.close();
			}
		}
		return tweets;
	}

}
