//Shruthi Raghuraman

package twitterEffect.tools;
import twitterEffect.model.*;
import twitterEffect.model.StockCompanies.MarketCapGroupType;
import twitterEffect.dal.*;

import java.util.Date;
import java.util.List;


import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;


//DROP TABLE IF EXISTS CalendarDates;
//DROP TABLE IF EXISTS TrumpTweets;
//DROP TABLE IF EXISTS ObamaTweets;
//DROP TABLE IF EXISTS Tweets;
//DROP TABLE IF EXISTS President;
//DROP TABLE IF EXISTS Person;
//DROP TABLE IF EXISTS IndexDailySummary;
//DROP TABLE IF EXISTS StockCompanyDailySummary;
//DROP TABLE IF EXISTS StockCompanies;
//DROP TABLE IF EXISTS StockIndex;

public class Inserter {

	public static void main(String[] args) throws SQLException, ParseException {
		// DAO instances.
		StockIndexDao stockIndexDao = StockIndexDao.getInstance();
		StockCompaniesDao stockCompaniesDao = StockCompaniesDao.getInstance();
		StockCompanyDailySummaryDao stockCompanyDailySummaryDao = StockCompanyDailySummaryDao.getInstance();
		IndexDailySummaryDao indexDailySummaryDao = IndexDailySummaryDao.getInstance();
		
		//Dates

		//Here are 2 date formats --> dates and timestamps
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-M-dd");
		SimpleDateFormat dateformat1 = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");

		String strdate1 = "02-04-2013 11:35:42";
		String strdate2 = "10-20-2015 11:35:42";
		String strdate3 = "07-28-2016 11:35:42";
		String strdate4 = "2013-04-10";
		String strdate5 = "01-04-2018 11:35:42";
		String strdate6 = "07-28-2019 11:35:42";

		//You can parse it as dates and parse it as timestamps I have parsed as dates
		Date date1 = dateformat.parse(strdate1);
		Date date2 = dateformat.parse(strdate2);
		Date date3 = dateformat.parse(strdate3);
		Date date4 = dateformat.parse(strdate4);
		Date date5 = dateformat.parse(strdate5);
		Date date6 = dateformat.parse(strdate6);

		
		// INSERT objects from our model.
		//Stock Index
		// INSERT objects from our model.
		StockIndex stockIndex1 = new StockIndex("sop", "S&P Composite");
		IndexDailySummary indexDailySummary1 = new IndexDailySummary(date1, stockIndex1,
				(float) 1719.29,(float) 1728.1,(float) 1703.52,(float) 1719.2,(float) 1719.2, 21659L, 900000);
		StockCompanies stockCompany1 = new StockCompanies("SHRUTHI", "Shruthi's Company", 12340987278378L, MarketCapGroupType.Medium, "healthcare", stockIndex1);
		StockCompanyDailySummary stockCompanyDailySummary1 = new StockCompanyDailySummary(stockCompany1, date2,
				(float)1234.5, (float)1234.77, (float)2372.21, (float)2377.32, 123456789876L, 900001);
		
		//Create Statements
		stockIndex1 = stockIndexDao.create(stockIndex1);
		indexDailySummary1 = indexDailySummaryDao.create(indexDailySummary1);
		stockCompany1 = stockCompaniesDao.create(stockCompany1);
		stockCompanyDailySummary1 = stockCompanyDailySummaryDao.create(stockCompanyDailySummary1);
	
	
		//Delete Statements
//		stockIndexDao.delete(stockIndex1);
//		indexDailySummaryDao.delete(indexDailySummary1);
//		stockCompaniesDao.delete(stockCompany1);
//		stockCompanyDailySummaryDao.delete(stockCompanyDailySummary1);
		
		//Update Statements
		stockIndexDao.updateIndexName(stockIndex1, "newStockIndex");
		indexDailySummaryDao.updatePrices(indexDailySummary1, 3.2f, 3.2f, 3.2f, 3.2f, 3.2f);
		stockCompaniesDao.updateCompanyName(stockCompany1, "thebettercompanyname");
		stockCompanyDailySummaryDao.updatePrices(stockCompanyDailySummary1, 6.7f, 7.7f, 5.6f, 5.4f);
		
		//Read Statements
		StockIndex s1 = stockIndexDao.getStockIndexByIndexTicker(stockIndex1.getIndexTicker());
		System.out.format("Reading stock index: IndTick:%s IndName:%s \n", s1.getIndexTicker(), s1.getIndexName());

		IndexDailySummary ids1 = indexDailySummaryDao.getIndexDailySummaryForADate(date4);
		System.out.format("Reading index daily summaries:"
				+ " sd:%s it:%s open:%s high:%s low:%s close:%s adjclose:%s "
				+ "volume:%s summaryId:%d \n", 
				ids1.getSummaryDate(), ids1.getStockIndex().getIndexTicker(), 
				ids1.getOpen(), ids1.getHigh(), ids1.getLow(),
				ids1.getClose(), ids1.getAdjClose(), ids1.getVolume(), ids1.getSummaryId());
		
		StockCompanies sc1 = stockCompaniesDao.getCompanyByCompanyTicker("AA");
		System.out.format("Reading stock companies: CompanyTicker:%s Company:%s MarketCap:%s MarketCapGroup:%s Sector:%s IndexTicker:%s\n", 
				sc1.getCompanyTicker(), sc1.getCompany(), sc1.getMarketCap(), sc1.getMarketCapGroup(), 
				sc1.getSector(), sc1.getStockIndex().getIndexTicker());

		List<StockCompanies> scList1 = stockCompaniesDao.getStockCompaniesByMarketCapGroup(StockCompanies.MarketCapGroupType.Large);
		for(StockCompanies sc : scList1) {
			System.out.format("Looping stock companies by marketcapgroup: CompanyTicker:%s Company:%s MarketCap:%s MarketCapGroup:%s Sector:%s IndexTicker:%s\n", 
					sc.getCompanyTicker(), sc.getCompany(), sc.getMarketCap(), sc.getMarketCapGroup(), 
					sc.getSector(), sc.getStockIndex().getIndexTicker());
			}
		
		List<StockCompanies> scList2 = stockCompaniesDao.getCompaniesBySector("healthcare");
		for(StockCompanies sc : scList2) {
			System.out.format("Looping stock companies by sector: CompanyTicker:%s Company:%s MarketCap:%s MarketCapGroup:%s Sector:%s IndexTicker:%s\n", 
					sc.getCompanyTicker(), sc.getCompany(), sc.getMarketCap(), sc.getMarketCapGroup(), 
					sc.getSector(), sc.getStockIndex().getIndexTicker());
			}
		
		StockCompanyDailySummary scds1 = stockCompanyDailySummaryDao.getStockCompanyDailySummaryById(2);
		System.out.format("Reading stock company daily summary: CompanyTicker:%s SummaryDate:%s "
				+ "OpenPrice:%s High:%s Low:%s ClosePrice:%s Volume:%s SummaryId:%s\n", 
				scds1.getStockCompany().getCompanyTicker(), scds1.getSummaryDate(),
				scds1.getOpenPrice(), scds1.getHigh(), scds1.getLow(), scds1.getClosePrice(),
				scds1.getVolume(), scds1.getSummaryId());
		
		List<StockCompanyDailySummary> scdsList1 = stockCompanyDailySummaryDao.getStockCompanyDailySummaryByDate(date4);
		for(StockCompanyDailySummary scds : scdsList1) {
			System.out.format("Looping stock company daily summary by date: CompanyTicker:%s SummaryDate:%s "
					+ "OpenPrice:%s High:%s Low:%s ClosePrice:%s Volume:%s SummaryId:%s\n", 
					scds.getStockCompany().getCompanyTicker(), scds.getSummaryDate(),
					scds.getOpenPrice(), scds.getHigh(), scds.getLow(), scds.getClosePrice(),
					scds.getVolume(), scds.getSummaryId());
			}

		List<StockCompanyDailySummary> scdsList2 = stockCompanyDailySummaryDao.getStockCompanyDailySummaryByCompanyTicker("AAPL");
		for(StockCompanyDailySummary scds: scdsList2) {
			System.out.format("Looping stock company daily summary by companyticker: CompanyTicker:%s SummaryDate:%s "
					+ "OpenPrice:%s High:%s Low:%s ClosePrice:%s Volume:%s SummaryId:%s\n", 
					scds.getStockCompany().getCompanyTicker(), scds.getSummaryDate(),
					scds.getOpenPrice(), scds.getHigh(), scds.getLow(), scds.getClosePrice(),
					scds.getVolume(), scds.getSummaryId());
			}


	}
}
