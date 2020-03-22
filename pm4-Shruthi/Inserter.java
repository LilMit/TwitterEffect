//Shruthi Raghuraman

package twitterEffect.tools;
import twitterEffect.model.*;
import twitterEffect.model.StockCompanies.MarketCapGroupType;
import twitterEffect.dal.*;

import java.util.Date;
import java.util.List;

import review.model.Users;

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
		SimpleDateFormat dateformat = new SimpleDateFormat("dd-M-yyyy");
		String strdate1 = "02-04-2013 11:35:42";
		String strdate2 = "10-20-2015 11:35:42";
		String strdate3 = "07-28-2016 11:35:42";
		String strdate4 = "02-12-2017 11:35:42";
		String strdate5 = "01-04-2018 11:35:42";
		String strdate6 = "07-28-2019 11:35:42";

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
				(float) 1719.29,(float) 1728.1,(float) 1703.52,(float) 1719.2,(float) 1719.2, 21659L, 150000);
		StockCompanies stockCompany1 = new StockCompanies("SHRUTHI", "Shruthi's Company", 12340987278378L, MarketCapGroupType.MEDIUM, "healthcare", stockIndex1);
		StockCompanyDailySummary stockCompanyDailySummary1 = new StockCompanyDailySummary(stockCompany1, date2,
				(float)1234.5, (float)1234.77, (float)2372.21, (float)2377.32, 123456789876L, 12);
		
		//Create Statements
		stockIndex1 = stockIndexDao.create(stockIndex1);
		indexDailySummary1 = indexDailySummaryDao.create(indexDailySummary1);
		stockCompany1 = stockCompaniesDao.create(stockCompany1);
		stockCompanyDailySummary1 = stockCompanyDailySummaryDao.create(stockCompanyDailySummary1);
	
	
		//Delete Statements
		stockIndexDao.delete(stockIndex1);
		indexDailySummaryDao.delete(indexDailySummary1);
		stockCompaniesDao.delete(stockCompany1);
		stockCompanyDailySummaryDao.delete(stockCompanyDailySummary1);
	}
}
