USE TwitterEffectPM6;
SET SQL_SAFE_UPDATES = 0;


INSERT INTO StockIndex(IndexTicker, IndexName)
	VALUES('NASDAQ', 'NASDAQ Composite'),
    ('DJI', 'Dow Jones Index'),
    ('GSPC','S&P500');
   
#SET FOREIGN_KEY_CHECKS=0;

LOAD DATA LOCAL INFILE '/Users/elaineparr/Documents/GitHub/TwitterEffect/PM6/nasdaq_list.csv' INTO TABLE StockCompanies
	FIELDS TERMINATED BY ','
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES;

    
ALTER TABLE StockCompanies add column IndexTicker VARCHAR(10);
UPDATE StockCompanies t2 join
       StockIndex t1
    set t2.IndexTicker = t1.IndexTicker;
ALTER TABLE StockCompanies ADD CONSTRAINT fk_StockCompanies_IndexTicker FOREIGN KEY (IndexTicker)
	REFERENCES StockIndex(IndexTicker)
    ON UPDATE CASCADE ON DELETE SET NULL;
    
INSERT INTO Person(PersonName, Occupation)
		VALUES('George W. Bush', 'President'),
        ('Barack Obama', 'President'),
        ('Donald Trump', 'President');
      
INSERT INTO President(PersonName, Party)
	VALUES('George W. Bush', 'Republican'),
    ('Barack Obama', 'Democrat'),
    ('Donald Trump', 'Republican');
        

LOAD DATA LOCAL INFILE '/Users/elaineparr/Documents/GitHub/TwitterEffect/PM6/calendarDates.csv' INTO TABLE CalendarDates
	FIELDS TERMINATED BY ','
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES;

LOAD DATA LOCAL INFILE '/Users/elaineparr/Documents/GitHub/TwitterEffect/PM6/nasdaq_historical_prices_daily (1).csv' INTO TABLE StockCompanyDailySummary
	FIELDS TERMINATED BY ','
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES;
    
    LOAD DATA LOCAL INFILE '/Users/elaineparr/Documents/GitHub/TwitterEffect/PM6/^IXIC.csv' INTO TABLE IndexDailySummary
	FIELDS TERMINATED BY ','
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES;
    
LOAD DATA LOCAL INFILE '/Users/elaineparr/Documents/GitHub/TwitterEffect/PM6/^DJI.csv' INTO TABLE IndexDailySummary
	FIELDS TERMINATED BY ','
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES;
    
LOAD DATA LOCAL INFILE '/Users/elaineparr/Documents/GitHub/TwitterEffect/PM6/^GSPC.csv' INTO TABLE IndexDailySummary
	FIELDS TERMINATED BY ','
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES;    
    
    LOAD DATA LOCAL INFILE '/Users/elaineparr/Documents/GitHub/TwitterEffect/PM6/Tweets-BarackObama.csv' INTO TABLE Tweets
	FIELDS TERMINATED BY ','
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES;
    
    LOAD DATA LOCAL INFILE '/Users/elaineparr/Documents/GitHub/TwitterEffect/PM6/trumptweetsedited.csv' INTO TABLE Tweets
	FIELDS TERMINATED BY ','
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES;

#SET FOREIGN_KEY_CHECKS=1;
