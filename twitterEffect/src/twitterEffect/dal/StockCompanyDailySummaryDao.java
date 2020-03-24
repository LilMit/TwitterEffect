package twitterEffect.dal;

import twitterEffect.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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


	//Update all prices
	public StockCompanyDailySummary updatePrices(StockCompanyDailySummary stockCompanyDailySummary, 
			float Open, float High, float Low, float Close) throws SQLException {
		String updateStockCompanyDailySummary = "UPDATE StockCompanyDailySummary SET OpenPrice=?, "
				+ "High=?, Low=?, ClosePrice=? WHERE SummaryId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateStockCompanyDailySummary);
			updateStmt.setFloat(1, Open);
			updateStmt.setFloat(2, High);
			updateStmt.setFloat(3, Low);
			updateStmt.setFloat(4, Close);
			updateStmt.setInt(5, stockCompanyDailySummary.getSummaryId());
			updateStmt.executeUpdate();

			stockCompanyDailySummary.setOpenPrice(Open);;
			stockCompanyDailySummary.setClosePrice(Close);;
			stockCompanyDailySummary.setHigh(High);
			stockCompanyDailySummary.setLow(Low);

			return stockCompanyDailySummary;
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

	//Get by list of daily summaries given company ticker	

	public List<StockCompanyDailySummary> getStockCompanyDailySummaryByCompanyTicker(String companyTicker) throws SQLException {
		List<StockCompanyDailySummary> stockCompanyDailySummaries = new ArrayList<StockCompanyDailySummary>();
		String selectStockCompanyDailySummary =
				"SELECT CompanyTicker,SummaryDate,OpenPrice,High,Low,ClosePrice,Volume,SummaryId " +
						"FROM StockCompanyDailySummary " +
						"WHERE CompanyTicker=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectStockCompanyDailySummary);

			selectStmt.setString(1, companyTicker);

			results = selectStmt.executeQuery();

			StockCompaniesDao stockCompaniesDao = StockCompaniesDao.getInstance();
			while(results.next()) {
				String resultsCompanyTicker = results.getString("CompanyTicker");
				Date summaryDate =  new Date(results.getTimestamp("SummaryDate").getTime());
				float openPrice = results.getFloat("OpenPrice");
				float high = results.getFloat("High");
				float low = results.getFloat("Low");
				float closePrice = results.getFloat("ClosePrice");
				long volume = results.getLong("Volume");
				int summaryId = results.getInt("SummaryId");


				StockCompanies stockCompany = stockCompaniesDao.getCompanyByCompanyTicker(resultsCompanyTicker);

				StockCompanyDailySummary stockCompanyDailySummary = new StockCompanyDailySummary(stockCompany, 
						summaryDate, openPrice, high, low, closePrice, volume, summaryId);

				stockCompanyDailySummaries.add(stockCompanyDailySummary);
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
		return stockCompanyDailySummaries;

	}

	//get list of daily summaries by date
	public List<StockCompanyDailySummary> getStockCompanyDailySummaryByDate(Date summaryDate) throws SQLException {
		List<StockCompanyDailySummary> stockCompanyDailySummaries = new ArrayList<StockCompanyDailySummary>();
		String selectStockCompanyDailySummary =
				"SELECT CompanyTicker,SummaryDate,OpenPrice,High,Low,ClosePrice,Volume,SummaryId " +
						"FROM StockCompanyDailySummary " +
						"WHERE SummaryDate=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectStockCompanyDailySummary);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String date = sdf.format(summaryDate);
			System.out.println(date);
			selectStmt.setString(1, date.toString());

			results = selectStmt.executeQuery();

			StockCompaniesDao stockCompaniesDao = StockCompaniesDao.getInstance();
			while(results.next()) {
				String companyTicker = results.getString("CompanyTicker");
				Date resultSummaryDate =  new Date(results.getTimestamp("SummaryDate").getTime());
				float openPrice = results.getFloat("OpenPrice");
				float high = results.getFloat("High");
				float low = results.getFloat("Low");
				float closePrice = results.getFloat("ClosePrice");
				long volume = results.getLong("Volume");
				int summaryId = results.getInt("SummaryId");


				StockCompanies stockCompany = stockCompaniesDao.getCompanyByCompanyTicker(companyTicker);

				StockCompanyDailySummary stockCompanyDailySummary = new StockCompanyDailySummary(stockCompany, 
						summaryDate, openPrice, high, low, closePrice, volume, summaryId);

				stockCompanyDailySummaries.add(stockCompanyDailySummary);
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
		return stockCompanyDailySummaries;

	}

	//get by id
	public StockCompanyDailySummary getStockCompanyDailySummaryById(int summaryId) throws SQLException {
		String selectStockCompanyDailySummary =
				"SELECT CompanyTicker,SummaryDate,OpenPrice,High,Low,ClosePrice,Volume,SummaryId " +
						"FROM StockCompanyDailySummary " +
						"WHERE SummaryId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectStockCompanyDailySummary);
			selectStmt.setInt(1, summaryId);
			results = selectStmt.executeQuery();

			StockCompaniesDao stockCompaniesDao = StockCompaniesDao.getInstance();
			if(results.next()) {					

				String companyTicker = results.getString("CompanyTicker");
				Date summaryDate =  new Date(results.getTimestamp("SummaryDate").getTime());
				float openPrice = results.getFloat("OpenPrice");
				float high = results.getFloat("High");
				float low = results.getFloat("Low");
				float closePrice = results.getFloat("ClosePrice");
				long volume = results.getLong("Volume");
				int resultSummaryId = results.getInt("SummaryId");


				StockCompanies stockCompany = stockCompaniesDao.getCompanyByCompanyTicker(companyTicker);

				StockCompanyDailySummary stockCompanyDailySummary = new StockCompanyDailySummary(stockCompany, 
						summaryDate, openPrice, high, low, closePrice, volume, resultSummaryId);

				return stockCompanyDailySummary;
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
