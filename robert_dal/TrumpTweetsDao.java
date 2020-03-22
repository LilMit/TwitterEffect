package twitterEffect.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import twitterEffect.model.Person;
import twitterEffect.model.TrumpTweets;
import twitterEffect.model.Tweets;

public class TrumpTweetsDao extends TweetsDao {
	private static TrumpTweetsDao instance = null;
	protected TrumpTweetsDao() {
		super();
	}
	public static TrumpTweetsDao getInstance() {
		if(instance == null) {
			instance = new TrumpTweetsDao();
		}
		return instance;
	}

	public TrumpTweets create(TrumpTweets trumpTweets) throws SQLException {
		// Insert into the superclass table first.
		create(new Tweets(trumpTweets.getLinkToTweet(),trumpTweets.getTweetDate(),trumpTweets.getTweetTime(),
						  trumpTweets.getContent(),trumpTweets.getRetweets(),trumpTweets.getPersonName()));

		String insertTrumpTweet = "INSERT INTO TrumpTweets(LinkToTweet,TweetId,Favorites) VALUES(?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertTrumpTweet);
			insertStmt.setString(1, trumpTweets.getLinkToTweet());
			insertStmt.setLong(2, trumpTweets.getTweetId());
			insertStmt.setInt(3, trumpTweets.getFavorites());
			insertStmt.executeUpdate();
			return trumpTweets;
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
	 * Update the Content of the TrumpTweets instance.
	 * This runs a UPDATE statement.
	 */
	public TrumpTweets updateContent(TrumpTweets trumpTweet, String newContent) throws SQLException {
		// The field to update only exists in the superclass table, so we can
		// just call the superclass method.
		super.updateContent(trumpTweet, newContent);
		return trumpTweet;
	}

	/**
	 * Delete the TrumpTweets instance.
	 * This runs a DELETE statement.
	 */
	public TrumpTweets delete(TrumpTweets trumpTweet) throws SQLException {
		String deleteTrumpTweet = "DELETE FROM TrumpTweets WHERE LinkToTweet=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteTrumpTweet);
			deleteStmt.setString(1, trumpTweet.getLinkToTweet());
			int affectedRows = deleteStmt.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("No records available to delete for LinkToTweet=" + trumpTweet.getLinkToTweet());
			}
			super.delete(trumpTweet);
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

	public TrumpTweets getTrumpTweetsByLinkToTweet(String linkToTweet) throws SQLException {
		// To build an BlogUser object, we need the Persons record, too.
		String selectTrumpTweet =
			"SELECT * " +
			"FROM TrumpTweets INNER JOIN Tweets " +
			"  ON TrumpTweets.LinkToTweets = Tweets.LinkToTweets " +
			"WHERE TrumpTweets.LinkToTweets=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectTrumpTweet);
			selectStmt.setString(1, linkToTweet);
			results = selectStmt.executeQuery();
			PersonDao personsDao = PersonDao.getInstance();
			if(results.next()) {
				String resultLinkToTweet = results.getString("LinkToTweet");
				Date tweetDate = results.getDate("TweetDate");
				Timestamp tweetTime = results.getTimestamp("TweetTime");
				String content = results.getString("Content");
				int retweets = results.getInt("Retweets");
				String personName = results.getString("PersonName");
				Person person = personsDao.getPersonByPersonName(personName);
				long tweetId = results.getLong("TweetId");
				int favorites = results.getInt("Favorites");
				TrumpTweets trumpTweet = new TrumpTweets(resultLinkToTweet,tweetDate,tweetTime,content,retweets,person,
														 tweetId,favorites);
				return trumpTweet;
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

	public List<TrumpTweets> getTrumpTweetsByDate(Date tweetDate)
			throws SQLException {
		List<TrumpTweets> trumpTweets = new ArrayList<TrumpTweets>();
		String selectTrumpTweets =
			"SELECT * " +
			"FROM TrumpTweets INNER JOIN Tweets " +
			"  ON TrumpTweets.LinkToTweet = Tweets.LinkToTweets " +
			"WHERE Tweets.TweetDate=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectTrumpTweets);
			selectStmt.setDate(1, tweetDate);
			results = selectStmt.executeQuery();
			PersonDao personsDao = PersonDao.getInstance();
			while(results.next()) {
				String linkToTweet = results.getString("LinkToTweet");
				Date resultTweetDate = results.getDate("TweetDate");
				Timestamp tweetTime = results.getTimestamp("TweetTime");
				String content = results.getString("Content");
				int retweets = results.getInt("Retweets");			
				String personName = results.getString("PersonName");
				Person person = personsDao.getPersonByPersonName(personName);
				long tweetId = results.getLong("TweetId");
				int favorites = results.getInt("Favorites");
				TrumpTweets trumpTweet = new TrumpTweets(linkToTweet,resultTweetDate,tweetTime,content,retweets,person,
						 tweetId,favorites);
				trumpTweets.add(trumpTweet);
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
		return trumpTweets;
	}
}
