package twitterEffect.dal;

import twitterEffect.model.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import review.dal.CompaniesDao;
import review.model.Companies;
import review.model.Restaurants;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

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

	//Create Index Daily Summary
	public IndexDailySummary create(IndexDailySummary indexDailySummary) throws SQLException {
		String insertIndexDailySummary =
				"INSERT INTO IndexDailySummary(SummaryDate,IndexTicker,OpenPrice,High,Low,ClosePrice,AdjClose,Volume,SummaryId) " +
						"VALUES(?,?,?,?,?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
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

	//delete index daily summary
	public IndexDailySummary delete(IndexDailySummary indexDailySummary) throws SQLException {
		String deleteIndexDailySummary = "DELETE FROM IndexDailySummary WHERE SummaryId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteIndexDailySummary);
			deleteStmt.setInt(1, indexDailySummary.getSummaryId());
			deleteStmt.executeUpdate();

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

	//get daily summary by id
	public IndexDailySummary getIndexDailySummaryById(int summaryId) throws SQLException {
		String selectIndexDailySummary =
				"SELECT SummaryDate,IndexTicker,OpenPrice,High,Low,ClosePrice,AdjClose,Volume,SummaryId " +
						"FROM IndexDailySummary " +
						"WHERE SummaryId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectIndexDailySummary);
			selectStmt.setInt(1, summaryId);
			results = selectStmt.executeQuery();

			StockIndexDao stockIndexDao = StockIndexDao.getInstance();
			if(results.next()) {					

				Date summaryDate =  new Date(results.getTimestamp("SummaryDate").getTime());
				String indexTicker = results.getString("IndexTicker");
				float openPrice = results.getFloat("OpenPrice");
				float high = results.getFloat("High");
				float low = results.getFloat("Low");
				float closePrice = results.getFloat("ClosePrice");
				float adjClose = results.getFloat("AdjClose");
				long volume = results.getLong("Volume");
				int resultSummaryId = results.getInt("SummaryId");


				StockIndex stockIndex = stockIndexDao.getStockIndexByIndexTicker(indexTicker);

				IndexDailySummary indexDailySummary = new IndexDailySummary(summaryDate, 
						stockIndex, openPrice, high, low, closePrice, adjClose, volume, resultSummaryId);

				return indexDailySummary;
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

	public IndexDailySummary getIndexDailySummaryForADate(Date summaryDate) throws SQLException {
		String selectIndexDailySummary =
				"SELECT SummaryDate,IndexTicker,OpenPrice,High,Low,ClosePrice,AdjClose,Volume,SummaryId " +
						"FROM IndexDailySummary " +
						"WHERE SummaryDate=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectIndexDailySummary);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String date = sdf.format(summaryDate);
			System.out.println(date);
			selectStmt.setString(1, date.toString());

			results = selectStmt.executeQuery();

			StockIndexDao stockIndexDao = StockIndexDao.getInstance();
			
			if(results.next()) {					

				Date resultSummaryDate =  new Date(results.getTimestamp("SummaryDate").getTime());
				String indexTicker = results.getString("IndexTicker");
				float openPrice = results.getFloat("OpenPrice");
				float high = results.getFloat("High");
				float low = results.getFloat("Low");
				float closePrice = results.getFloat("ClosePrice");
				float adjClose = results.getFloat("AdjClose");
				long volume = results.getLong("Volume");
				int summaryId = results.getInt("SummaryId");


				StockIndex stockIndex = stockIndexDao.getStockIndexByIndexTicker(indexTicker);

				IndexDailySummary indexDailySummary = new IndexDailySummary(resultSummaryDate, 
						stockIndex, openPrice, high, low, closePrice, adjClose, volume, summaryId);

				return indexDailySummary;
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
		

	//Update open, high, low, close, adj close prices given summaryId
	public IndexDailySummary updatePrices(IndexDailySummary indexDailySummary, float Open, float High, float Low, float Close, float AdjClose) throws SQLException {
		String updateIndexDailySummary = "UPDATE IndexDailySummary SET OpenPrice=?, "
				+ "High=?, Low=?, ClosePrice=?, AdjClose=? WHERE SummaryId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateIndexDailySummary);
			updateStmt.setFloat(1, Open);
			updateStmt.setFloat(2, High);
			updateStmt.setFloat(3, Low);
			updateStmt.setFloat(4, Close);
			updateStmt.setFloat(5, AdjClose);
			updateStmt.setInt(6, indexDailySummary.getSummaryId());
			updateStmt.executeUpdate();

			indexDailySummary.setOpen(Open);
			indexDailySummary.setClose(Close);
			indexDailySummary.setHigh(High);
			indexDailySummary.setLow(Low);
			indexDailySummary.setAdjClose(AdjClose);
			return indexDailySummary;
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

}


