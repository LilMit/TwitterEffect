USE TwitterEffectPM3;

SELECT 
    *
FROM
    CalendarDates;
SELECT 
    *
FROM
    TrumpTweets;
SELECT 
    *
FROM
    ObamaTweets;
SELECT 
    *
FROM
    Tweets;
SELECT 
    *
FROM
    President;
SELECT 
    *
FROM
    Person;
SELECT 
    *
FROM
    IndexDailySummary;
SELECT 
    *
FROM
    StockCompanyDailySummary;
SELECT 
    *
FROM
    StockCompanies;
SELECT 
    *
FROM
    StockIndex;
    

#1. How many presidents for each party are in the database?
SELECT 
    Party, COUNT(*) AS CountPresidents
FROM
    President
GROUP BY Party
ORDER BY CountPresidents ASC;

#2a. How many tweets were sent by distinct presidents per day in descending order?
SELECT 
    TweetDate,
    PersonName,
    COUNT(*) AS NumberOfTweetsPerPresident
FROM
    Tweets
GROUP BY TweetDate , PersonName
ORDER BY NumberOfTweetsPerPresident DESC;

#2b. Who sent the highest number of tweets in one day?
SELECT 
    TweetDate,
    PersonName,
    COUNT(*) AS NumberOfTweetsPerPresident
FROM
    Tweets
GROUP BY TweetDate , PersonName
ORDER BY NumberOfTweetsPerPresident DESC
LIMIT 1;

#3. Map the index daily summary to a) the tweet sent on that day, 
#b) time it was sent, c) the account it originated from.
SELECT 
    a.SummaryDate,
    a.OpenPrice,
    a.ClosePrice,
    b.Content,
    b.TweetTime,
    b.personName
FROM
    (SELECT 
        SummaryDate, OpenPrice, ClosePrice
    FROM
        IndexDailySummary) a,
    (SELECT 
        TweetDate, Content, TweetTime, PersonName
    FROM
        Tweets) b
WHERE
    a.SummaryDate = b.TweetDate
        AND b.PersonName = 'Donald Trump'
        AND a.SummaryDate = '2015-01-05';


#4a. What are top ten Barack Obama tweets based on Retweets and with party information?
SELECT 
    Tweets.Content,
    President.Party,
    Tweets.PersonName,
    Tweets.Retweets
FROM
    Tweets
        INNER JOIN
    President ON Tweets.PersonName = President.PersonName
WHERE
    Tweets.PersonName = 'Barack Obama'
ORDER BY Tweets.Retweets DESC
LIMIT 10;

#4b. What are top ten Donald Trump tweets based on Retweets and with party information?
SELECT 
    Tweets.Content,
    President.Party,
    Tweets.PersonName,
    Tweets.Retweets
FROM
    Tweets
        INNER JOIN
    President ON Tweets.PersonName = President.PersonName
WHERE
    Tweets.PersonName = 'Donald Trump'
ORDER BY Tweets.Retweets DESC
LIMIT 10;

#5. What is the average stock market volume per month for each company for every year?
# What are the number of trading days for that company?
SELECT 
    a.Company,
    a.Month,
    a.Year,
    AVG(a.Volume) AS AverageVolume,
    COUNT(*) AS NumberOfDaysTraded
FROM
    (SELECT 
        StockCompanies.Company,
            StockCompanies.MarketCap,
            StockCompanyDailySummary.SummaryDate,
            StockCompanyDailySummary.Volume,
            MONTH(StockCompanyDailySummary.SummaryDate) AS Month,
            YEAR(StockCompanyDailySummary.SummaryDate) AS Year
    FROM
        StockCompanies
    INNER JOIN StockCompanyDailySummary ON StockCompanies.CompanyTicker = StockCompanyDailySummary.CompanyTicker) a
GROUP BY a.Company , a.Year, a.Month;


#6. What is the ratio of number of tweets by Donald Trump to the NASDAQ daily summary volume 
# for Google in descending order?
SELECT 
    *,
    (a.NumberOfTweetsPerPresident / b.Volume * 100) AS RatioToTweets
FROM
    (SELECT 
        TweetDate,
            PersonName,
            COUNT(*) AS NumberOfTweetsPerPresident
    FROM
        Tweets
    GROUP BY TweetDate , PersonName
    ORDER BY NumberOfTweetsPerPresident DESC) a,
    (SELECT 
        SummaryDate, CompanyTicker, Volume
    FROM
        StockCompanyDailySummary) b
WHERE
    a.TweetDate = b.SummaryDate
        AND b.CompanyTicker = 'GOOGL'
        AND a.PersonName = 'Donald Trump'
ORDER BY RatioToTweets DESC;

# 7. How many companies are within each market cap group?
SELECT 
    MarketCapGroup, COUNT(*) AS NumberPerGroup
FROM
    StockCompanies
GROUP BY MarketCapGroup;

# 8. What are the top five micro companies with the highest MarketCap?
SELECT 
    *
FROM
    StockCompanies
WHERE
    MarketCapGroup = 'Micro'
ORDER BY MarketCap DESC
LIMIT 5;

#9. What is the maximum close price for each company?
SELECT DISTINCT
    a.CompanyTicker, a.ClosePrice, StockCompanies.Company
FROM
    (SELECT 
        CompanyTicker, MAX(ClosePrice) AS MaxClosePrice
    FROM
        StockCompanyDailySummary
    GROUP BY CompanyTicker) AS x
        INNER JOIN
    StockCompanyDailySummary AS a ON a.CompanyTicker = x.CompanyTicker
        AND a.ClosePrice = x.MaxClosePrice
        LEFT OUTER JOIN
    StockCompanies ON a.CompanyTicker = StockCompanies.CompanyTicker
ORDER BY a.ClosePrice DESC;

# 10. Identify stock market related tweets with stopwords and map them to the open
# and close price (determine difference between two prices).
SELECT 
    a.Content,
    a.PersonName,
    a.TweetDate,
    a.StopWord,
    IndexDailySummary.OpenPrice,
    IndexDailySummary.ClosePrice,
    (IndexDailySummary.ClosePrice - IndexDailySummary.OpenPrice) AS PriceDiff
FROM
    (SELECT 
        Content,
            PersonName,
            TweetDate,
            CASE
                WHEN Content LIKE '%stock%' THEN 'Stock'
                WHEN Content LIKE '%market%' THEN 'Market'
                WHEN Content LIKE '%dow jones%' THEN 'Dow Jones'
                WHEN Content LIKE '%nasdaq%' THEN 'Nasdaq'
                WHEN Content LIKE '%S&P%' THEN 'S&P'
            END AS StopWord
    FROM
        Tweets) a
        LEFT OUTER JOIN
    IndexDailySummary ON a.TweetDate = IndexDailySummary.SummaryDate
WHERE
    a.StopWord IS NOT NULL;
    
#11. Map the content of all tweets on a particular date to the stock market daily summary of 
#a particular company for that date. For example, look at which president tweeted what on 
#2019-04-10 and the daily summary on that day for Apple.

SELECT 
    Tweets.TweetDate,
    Tweets.Content,
    Tweets.PersonName,
    StockCompanyDailySummary.*
FROM
    Tweets
        INNER JOIN
    StockCompanyDailySummary ON StockCompanyDailySummary.SummaryDate = Tweets.TweetDate
WHERE
    StockCompanyDailySummary.CompanyTicker = 'AAPL'
        AND Tweets.TweetDate = '2019-04-10';