package twitterEffect.dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import twitterEffect.model.ObamaTweets;
import twitterEffect.model.Tweets;

public class ObamaTweetsDao  extends TweetsDao {
	
	private static ObamaTweetsDao instance = null;
	
	protected ObamaTweetsDao() {
		super();
	}
	public static ObamaTweetsDao getInstance() {
		if(instance == null) {
			instance = new ObamaTweetsDao();
		}
		return instance;
	}

	public ObamaTweets create(ObamaTweets obamaTweets) throws SQLException {
		// Insert into the superclass table first.
		create(new Tweets(obamaTweets.getLinkToTweet(),obamaTweets.getTweetDate(),obamaTweets.getTweetTime(),
						  obamaTweets.getContent(),obamaTweets.getRetweets(),obamaTweets.getPersonName()));

		String insertObamaTweet = "INSERT INTO ObamaTweets(LinkToTweet,UserName,Likes,TweetImageURL) VALUES(?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertObamaTweet);
			insertStmt.setString(1, obamaTweets.getLinkToTweet());
			insertStmt.setString(2, obamaTweets.getUserName());
			insertStmt.setInt(3, obamaTweets.getLikes());
			insertStmt.setString(4, obamaTweets.getTweetImageURL());
			insertStmt.executeUpdate();
			return obamaTweets;
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
	 * Update the Content of the ObamaTweets instance.
	 * This runs a UPDATE statement.
	 */
	public ObamaTweets updateContent(ObamaTweets obamaTweet, String newContent) throws SQLException {
		// The field to update only exists in the superclass table, so we can
		// just call the superclass method.
		super.updateContent(obamaTweet, newContent);
		return obamaTweet;
	}

	/**
	 * Delete the TrumpTweets instance.
	 * This runs a DELETE statement.
	 */
	public ObamaTweets delete(ObamaTweets obamaTweet) throws SQLException {
		String deleteObamaTweet = "DELETE FROM ObamaTweets WHERE LinkToTweet=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteObamaTweet);
			deleteStmt.setString(1, obamaTweet.getLinkToTweet());
			int affectedRows = deleteStmt.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("No records available to delete for LinkToTweet=" + obamaTweet.getLinkToTweet());
			}
			super.delete(obamaTweet);
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

	public ObamaTweets getObamaTweetsByLinkToTweet(String linkToTweet) throws SQLException {
		// To build an BlogUser object, we need the Persons record, too.
		String selectObamaTweet =
			"SELECT * " +
			"FROM ObamaTweets INNER JOIN Tweets " +
			"  ON ObamaTweets.LinkToTweets = Obama.LinkToTweets " +
			"WHERE ObamaTweets.LinkToTweets=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectObamaTweet);
			selectStmt.setString(1, linkToTweet);
			results = selectStmt.executeQuery();
			if(results.next()) {
				String resultLinkToTweet = results.getString("LinkToTweet");
				Date tweetDate = results.getDate("TweetDate");
				Timestamp tweetTime = results.getTimestamp("TweetTime");
				String content = results.getString("Content");
				int retweets = results.getInt("Retweets");			
				String personName = results.getString("PersonName");
				String userName = results.getString("UserName");
				int likes = results.getInt("Likes");
				String tweetImageURL = results.getString("TweetImageURL");
				ObamaTweets obamaTweet = new ObamaTweets(resultLinkToTweet,tweetDate,tweetTime,content,retweets,personName,
														userName,likes,tweetImageURL);
				return obamaTweet;
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

	public List<ObamaTweets> getObamaTweetsByDate(Date tweetDate)
			throws SQLException {
		List<ObamaTweets> obamaTweets = new ArrayList<ObamaTweets>();
		String selectObamaTweets =
			"SELECT * " +
			"FROM ObamaTweets INNER JOIN Tweets " +
			"  ON ObamaTweets.LinkToTweet = Tweets.LinkToTweets " +
			"WHERE Tweets.TweetDate=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectObamaTweets);
			selectStmt.setDate(1, tweetDate);
			results = selectStmt.executeQuery();
			while(results.next()) {
				String linkToTweet = results.getString("LinkToTweet");
				Date resultTweetDate = results.getDate("TweetDate");
				Timestamp tweetTime = results.getTimestamp("TweetTime");
				String content = results.getString("Content");
				int retweets = results.getInt("Retweets");			
				String personName = results.getString("PersonName");
				String userName = results.getString("UserName");
				int likes = results.getInt("Likes");
				String tweetImageURL = results.getString("TweetImageURL");
				ObamaTweets obamaTweet = new ObamaTweets(linkToTweet,resultTweetDate,tweetTime,content,retweets,personName,
														 userName,likes,tweetImageURL);
				obamaTweets.add(obamaTweet);
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
		return obamaTweets;
	}
}
