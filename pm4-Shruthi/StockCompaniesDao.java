package twitterEffect.dal;

import twitterEffect.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class StockCompaniesDao {
	protected ConnectionManager connectionManager;

	private static StockCompaniesDao instance = null;
	protected StockCompaniesDao() {
		connectionManager = new ConnectionManager();
	}
	public static StockCompaniesDao getInstance() {
		if(instance == null) {
			instance = new StockCompaniesDao();
		}
		return instance;
	}

	//should summary id be autocreated?
	public StockCompanies create(StockCompanies stockCompanies) throws SQLException {
		String insertStockCompanies =
			"INSERT INTO StockCompanies(CompanyTicker, Company, MarketCap, MarketCapGroup, Sector, IndexTicker) " +
			"VALUES(?,?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		//ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			//insertStmt = connection.prepareStatement(insertBlogComment,
				//Statement.RETURN_GENERATED_KEYS);
			insertStmt = connection.prepareStatement(insertStockCompanies);
			insertStmt.setString(1, stockCompanies.getCompanyTicker());
			insertStmt.setString(2, stockCompanies.getCompany());
			insertStmt.setLong(3, stockCompanies.getMarketCap());
			insertStmt.setString(4, stockCompanies.getMarketCapGroup().name());
			insertStmt.setString(5, stockCompanies.getSector());
			insertStmt.setString(6, stockCompanies.getStockIndex().getIndexTicker());
			insertStmt.executeUpdate();
			
			
			return stockCompanies;
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
	 * Delete the stockCompanies instance.
	 */
	public StockCompanies delete(StockCompanies stockCompanies) throws SQLException {
		String deleteStockCompanies = "DELETE FROM StockCompanies WHERE CompanyTicker=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteStockCompanies);
			deleteStmt.setString(1, stockCompanies.getCompanyTicker());
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
