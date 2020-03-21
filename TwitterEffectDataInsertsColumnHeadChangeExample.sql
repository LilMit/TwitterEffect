USE TwitterEffect;

INSERT INTO StockIndex(IndexTicker, IndexName)
	VALUES('NASDAQ', 'NASDAQ Composite');
   /**
SET FOREIGN_KEY_CHECKS=0;
  */       
LOAD DATA LOCAL INFILE '/Users/elaineparr/Dropbox/Spring2020/Databases/HW/HW3/nasdaq_list.csv' INTO TABLE StockCompanies
	FIELDS TERMINATED BY ','
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES;
    
INSERT INTO Person(PersonName, Occupation)
		VALUES('George W. Bush', 'President'),
        ('Barack Obama', 'President'),
        ('Donald Trump', 'President');
      
INSERT INTO President(PersonName, Party)
	VALUES('George W. Bush', 'Republican'),
    ('Barack Obama', 'Democrat'),
    ('Donald Trump', 'Republican');
        
/**
SET FOREIGN_KEY_CHECKS=1;
*/
LOAD DATA LOCAL INFILE '/Users/elaineparr/Dropbox/Spring2020/Databases/HW/HW3/calendarDates.csv' INTO TABLE CalendarDates
	FIELDS TERMINATED BY ','
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES;

LOAD DATA LOCAL INFILE '/Users/elaineparr/Dropbox/Spring2020/Databases/HW/HW3/nasdaq_historical_prices_daily (1).csv' INTO TABLE StockCompanyDailySummary
	FIELDS TERMINATED BY ','
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES;
    
    LOAD DATA LOCAL INFILE '/Users/elaineparr/Dropbox/Spring2020/Databases/HW/HW3/^IXIC.csv' INTO TABLE IndexDailySummary
	FIELDS TERMINATED BY ','
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES;
    
    /**
    I tried changing all the tweets to tab separated values because I noticed that commas in the tweets were messing up the tables,
    but evidently tabs in the tweets had the same problem. But the syntax in the bottom row (except the tab part and the .txt
    filename) is how you insert columns from a csv with different header names into a table
    */
    LOAD DATA LOCAL INFILE '/Users/elaineparr/Dropbox/Spring2020/Databases/HW/HW3/Tweets-BarackObama.txt' INTO TABLE Tweets
	FIELDS TERMINATED BY '\t'
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES;
    
    LOAD DATA LOCAL INFILE '/Users/elaineparr/Dropbox/Spring2020/Databases/HW/HW3/trumptweetsedited.txt' INTO TABLE Tweets
	FIELDS TERMINATED BY '\t'
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES;
    
 SET FOREIGN_KEY_CHECKS=0;
 LOAD DATA LOCAL INFILE '/Users/elaineparr/Dropbox/Spring2020/Databases/HW/HW3/Tweets-BarackObama.txt' INTO TABLE ObamaTweets
	FIELDS TERMINATED BY '\t'
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES
    (@col1, @col2,@col3,@col4,@col5,@col6,@col7,@col8,@col9) set LinkToTweet=@col1,Likes=@col7,TweetImageURL=@col8,UserName=@col9;
    SET FOREIGN_KEY_CHECKS=1;