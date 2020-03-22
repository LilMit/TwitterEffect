package twitterEffect.dal;

import twitterEffect.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;

public class IndexDailySummaryDao {
	protected ConnectionManager connectionManager;

	private static IndexDailySummaryDao instance = null;
	protected IndexDailySummaryDao() {
		connectionManager = new ConnectionManager();
	}
	public static IndexDailySummaryDao getInstance() {
		if(instance == null) {
			instance = new IndexDailySummaryDao();
		}
		return instance;
	}

	//should summary id be autocreated?
	public IndexDailySummary create(IndexDailySummary indexDailySummary) throws SQLException {
		String insertIndexDailySummary =
			"INSERT INTO IndexDailySummary(SummaryDate,IndexTicker,OpenPrice,High,Low,ClosePrice,AdjClose,Volume,SummaryId) " +
			"VALUES(?,?,?,?,?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		//ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			//insertStmt = connection.prepareStatement(insertBlogComment,
				//Statement.RETURN_GENERATED_KEYS);
			insertStmt = connection.prepareStatement(insertIndexDailySummary);
			insertStmt.setTimestamp(1,new Timestamp(indexDailySummary.getSummaryDate().getTime()));
			insertStmt.setString(2, indexDailySummary.getStockIndex().getIndexTicker());
			insertStmt.setFloat(3, indexDailySummary.getOpen());
			insertStmt.setFloat(4, indexDailySummary.getHigh());
			insertStmt.setFloat(5, indexDailySummary.getLow());
			insertStmt.setFloat(6, indexDailySummary.getClose());
			insertStmt.setFloat(7, indexDailySummary.getAdjClose());
			insertStmt.setLong(8, indexDailySummary.getVolume());
			insertStmt.setInt(9, indexDailySummary.getSummaryId());
			insertStmt.executeUpdate();
	
			return indexDailySummary;
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
	 * Delete IndexDailySummaryInstance
	 */
	public IndexDailySummary delete(IndexDailySummary indexDailySummary) throws SQLException {
		String deleteIndexDailySummary = "DELETE FROM IndexDailySummary WHERE SummaryId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteIndexDailySummary);
			deleteStmt.setInt(1, indexDailySummary.getSummaryId());
			deleteStmt.executeUpdate();

			// Return null so the caller can no longer operate on the BlogComments instance.
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
	
}
