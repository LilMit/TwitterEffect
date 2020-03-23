package twitterEffect.dal;

import twitterEffect.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

	//Update Company Name
	public StockCompanies updateCompanyName(StockCompanies stockCompanies, String newCompanyName) throws SQLException {
		String updateCompanyName = "UPDATE StockCompanies SET CompanyName=? WHERE CompanyTicker=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateCompanyName);
			updateStmt.setString(1, newCompanyName);;
			updateStmt.setString(2, stockCompanies.getCompanyTicker());
			updateStmt.executeUpdate();

			stockCompanies.setCompany(newCompanyName);
			return stockCompanies;
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

	//Get company by ticker
	public StockCompanies getCompanyByCompanyTicker(String companyTicker) throws SQLException {
		String selectStockCompany =
				"SELECT CompanyTicker,Company,MarketCap,MarketCapGroup,Sector,IndexTicker " +
						"FROM StockCompanies " +
						"WHERE CompanyTicker=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectStockCompany);
			selectStmt.setString(1, companyTicker);
			results = selectStmt.executeQuery();

			StockIndexDao stockIndexDao = StockIndexDao.getInstance();
			if(results.next()) {					
				String resultCompanyTicker = results.getString("CompanyTicker");
				String company = results.getString("Company");
				long marketCap = results.getLong("MarketCap");
				StockCompanies.MarketCapGroupType marketCapGroupType = StockCompanies.MarketCapGroupType.valueOf(results.getString("MarketCapGroup"));
				String sector = results.getString("Sector");
				String indexTicker = results.getString("IndexTicker");


				StockIndex stockIndex = stockIndexDao.getStockIndexByIndexTicker(indexTicker);

				StockCompanies stockCompanies = new StockCompanies(resultCompanyTicker, company, marketCap, 
						marketCapGroupType, sector, stockIndex);

				return stockCompanies;
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

	//Get list of companies given marketcapgroup type
	public List<StockCompanies> getStockCompaniesByMarketCapGroup(StockCompanies.MarketCapGroupType marketCapGroupType) throws SQLException {
		List<StockCompanies> stockCompanies = new ArrayList<StockCompanies>();
		String selectStockCompany =
				"SELECT CompanyTicker,Company,MarketCap,MarketCapGroup,Sector,IndexTicker " +
						"FROM StockCompanies " +
						"WHERE MarketCapGroup=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectStockCompany);

			
			selectStmt.setString(1, marketCapGroupType.name());

			results = selectStmt.executeQuery();

			StockIndexDao stockIndexDao = StockIndexDao.getInstance();
			while(results.next()) {
				String companyTicker = results.getString("CompanyTicker");
				String company = results.getString("Company");
				long marketCap = results.getLong("MarketCap");
				StockCompanies.MarketCapGroupType resultMarketCapGroupType = StockCompanies.MarketCapGroupType.valueOf(results.getString("MarketCapGroup"));
				String sector = results.getString("Sector");
				String indexTicker = results.getString("IndexTicker");


				StockIndex stockIndex = stockIndexDao.getStockIndexByIndexTicker(indexTicker);

				StockCompanies stockCompany = new StockCompanies(companyTicker, company, marketCap, 
						resultMarketCapGroupType, sector, stockIndex);

				stockCompanies.add(stockCompany);
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
		return stockCompanies;

	}


	//get list of all companies given sector
	public List<StockCompanies> getCompaniesBySector(String sector) throws SQLException {
		List<StockCompanies> stockCompanies = new ArrayList<StockCompanies>();
		String selectStockCompany =
				"SELECT CompanyTicker,Company,MarketCap,MarketCapGroup,Sector,IndexTicker " +
						"FROM StockCompanies " +
						"WHERE Sector=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectStockCompany);

			
			selectStmt.setString(1, sector);

			results = selectStmt.executeQuery();

			StockIndexDao stockIndexDao = StockIndexDao.getInstance();
			while(results.next()) {
				String companyTicker = results.getString("CompanyTicker");
				String company = results.getString("Company");
				long marketCap = results.getLong("MarketCap");
				StockCompanies.MarketCapGroupType marketCapGroupType = StockCompanies.MarketCapGroupType.valueOf(results.getString("MarketCapGroup"));
				String resultSector = results.getString("Sector");
				String indexTicker = results.getString("IndexTicker");


				StockIndex stockIndex = stockIndexDao.getStockIndexByIndexTicker(indexTicker);

				StockCompanies stockCompany = new StockCompanies(companyTicker, company, marketCap, 
						marketCapGroupType, resultSector, stockIndex);

				stockCompanies.add(stockCompany);
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
		return stockCompanies;

	}
}
