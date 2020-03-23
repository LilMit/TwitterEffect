//Shruthi Raghuraman
package twitterEffect.dal;

import twitterEffect.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StockIndexDao {
	protected ConnectionManager connectionManager;

	private static StockIndexDao instance = null;
	protected StockIndexDao() {
		connectionManager = new ConnectionManager();
	}
	public static StockIndexDao getInstance() {
		if(instance == null) {
			instance = new StockIndexDao();
		}
		return instance;
	}

	//Create Stock Index
	public StockIndex create(StockIndex stockIndex) throws SQLException {
		String insertStockIndex =
			"INSERT INTO StockIndex(IndexTicker, IndexName) " +
			"VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertStockIndex);
			insertStmt.setString(1, stockIndex.getIndexTicker());
			insertStmt.setString(2, stockIndex.getIndexName());
			insertStmt.executeUpdate();
			return stockIndex;
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
	

	//Delete Stock Index
	public StockIndex delete(StockIndex stockIndex) throws SQLException {
		String deleteCompany = "DELETE FROM StockIndex WHERE IndexTicker=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteCompany);
			deleteStmt.setString(1,stockIndex.getIndexTicker());
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
	
}