package twitterEffect.dal;

import twitterEffect.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class StockCompanyDailySummaryDao {
	protected ConnectionManager connectionManager;

	private static StockCompanyDailySummaryDao instance = null;
	protected StockCompanyDailySummaryDao() {
		connectionManager = new ConnectionManager();
	}
	public static StockCompanyDailySummaryDao getInstance() {
		if(instance == null) {
			instance = new StockCompanyDailySummaryDao();
		}
		return instance;
	}

	public StockCompanyDailySummary create(StockCompanyDailySummary stockCompanyDailySummary) throws SQLException {
		String insertStockCompanyDailySummary =
			"INSERT INTO StockCompanyDailySummary(CompanyTicker,SummaryDate,OpenPrice,High,Low,ClosePrice,Volume,SummaryId) " +
			"VALUES(?,?,?,?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();

			insertStmt = connection.prepareStatement(insertStockCompanyDailySummary);
			insertStmt.setString(1, stockCompanyDailySummary.getStockCompany().getCompanyTicker());
			insertStmt.setTimestamp(2, new Timestamp(stockCompanyDailySummary.getSummaryDate().getTime()));
			insertStmt.setFloat(3, stockCompanyDailySummary.getOpenPrice());
			insertStmt.setFloat(4, stockCompanyDailySummary.getHigh());
			insertStmt.setFloat(5, stockCompanyDailySummary.getLow());
			insertStmt.setFloat(6, stockCompanyDailySummary.getClosePrice());
			insertStmt.setLong(7, stockCompanyDailySummary.getVolume());
			insertStmt.setInt(8, stockCompanyDailySummary.getSummaryId());
			insertStmt.executeUpdate();
			
			return stockCompanyDailySummary;
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
	 * Delete the StockCompanyDailySummaryInstance
	 */
	public StockCompanyDailySummary delete(StockCompanyDailySummary stockCompanyDailySummary) throws SQLException {
		String deleteStockCompanyDailySummary = "DELETE FROM StockCompanyDailySummary WHERE SummaryId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteStockCompanyDailySummary);
			deleteStmt.setInt(1, stockCompanyDailySummary.getSummaryId());
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
