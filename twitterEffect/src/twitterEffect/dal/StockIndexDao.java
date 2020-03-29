//Shruthi Raghuraman
package twitterEffect.dal;

import twitterEffect.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
	
	//Update indexname given indexticker
	public StockIndex updateIndexName(StockIndex stockIndex, String newName) throws SQLException {
		String updateStockIndexName = "UPDATE StockIndex SET IndexName=? WHERE IndexTicker=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateStockIndexName);
			updateStmt.setString(1, newName);
			updateStmt.setString(2, stockIndex.getIndexTicker());
			updateStmt.executeUpdate();

			stockIndex.setIndexName(newName);
			return stockIndex;
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
	
	//get stock index given index name
	public StockIndex getStockIndexByIndexTicker(String indexTicker) throws SQLException {
		String selectStockIndex =
			"SELECT IndexTicker, IndexName " +
			"FROM StockIndex " +
			"WHERE IndexTicker=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectStockIndex);
			selectStmt.setString(1, indexTicker);
			results = selectStmt.executeQuery();
			
			if(results.next()) {
				String resultIndexTicker = results.getString("IndexTicker");
				String indexName = results.getString("IndexName");				
				StockIndex stockIndex = new StockIndex(resultIndexTicker, indexName);
				return stockIndex;
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
	
}