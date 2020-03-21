CREATE SCHEMA IF NOT EXISTS TwitterEffectPM3;
USE TwitterEffectPM3;

DROP TABLE IF EXISTS CalendarDates;
DROP TABLE IF EXISTS TrumpTweets;
DROP TABLE IF EXISTS ObamaTweets;
DROP TABLE IF EXISTS Tweets;
DROP TABLE IF EXISTS President;
DROP TABLE IF EXISTS Person;
DROP TABLE IF EXISTS IndexDailySummary;
DROP TABLE IF EXISTS StockCompanyDailySummary;
DROP TABLE IF EXISTS StockCompanies;
DROP TABLE IF EXISTS StockIndex;

CREATE TABLE StockIndex (
IndexTicker VARCHAR(10),
IndexName VARCHAR(255) NOT NULL,
CONSTRAINT pk_StockIndex_IndexTicker PRIMARY KEY (IndexTicker),
CONSTRAINT uq_StockIndex_IndexName UNIQUE (IndexName)
);

CREATE TABLE StockCompanies(
CompanyTicker VARCHAR(10),
Company VARCHAR(255) NOT NULL,
MarketCap BIGINT,
MarketCapGroup ENUM('Mega', 'Large', 'Medium', 'Small', 'Micro'),
Sector VARCHAR(255),
CONSTRAINT pk_StockCompanies_CompanyTicker PRIMARY KEY (CompanyTicker)   
);

CREATE TABLE Person(
PersonName VARCHAR(255),
Occupation VARCHAR(255),
CONSTRAINT pk_Person_PersonName PRIMARY KEY (PersonName)
);

CREATE TABLE President(
PersonName VARCHAR(255),
Party ENUM('Republican', 'Democrat', 'Independent'),
CONSTRAINT pk_President_PersonName PRIMARY KEY (PersonName),
CONSTRAINT fk_President_PersonName FOREIGN KEY (PersonName)
	REFERENCES Person(PersonName)
    ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE CalendarDates(
CalendarDate DATE,
President VARCHAR(255),
CONSTRAINT pk_CalendarDate_CalendarDate PRIMARY KEY (CalendarDate),
CONSTRAINT fk_CalendarDate_President FOREIGN KEY (President)
	REFERENCES President(PersonName)
    ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE StockCompanyDailySummary(
CompanyTicker VARCHAR(10),
SummaryDate DATE,
OpenPrice FLOAT,
High FLOAT,
Low FLOAT,
ClosePrice FLOAT,
Volume BIGINT,
SummaryID INTEGER,
CONSTRAINT pk_StockCompanyDailySummary_SummaryID PRIMARY KEY (SummaryID)
);

CREATE TABLE IndexDailySummary(
SummaryDate DATE,
IndexTicker VARCHAR(10),
OpenPrice FLOAT,
High FLOAT,
Low FLOAT,
ClosePrice FLOAT,
AdjClose FLOAT,
Volume BIGINT,
SummaryID INTEGER,
CONSTRAINT pk_IndexDailySummary_SummaryID PRIMARY KEY (SummaryID),
CONSTRAINT fk_IndexDailySummary_IndexTicker FOREIGN KEY (IndexTicker)
	REFERENCES StockIndex(IndexTicker)    
);

CREATE TABLE Tweets(
LinkToTweet VARCHAR(255),
TweetDate DATE,
TweetTime TIME,
Content VARCHAR(500),
Retweets INT,
PersonName VARCHAR(255),
CONSTRAINT pk_Tweets_LinkToTweet PRIMARY KEY (LinkToTweet),
CONSTRAINT fk_Tweets_PersonName FOREIGN KEY (PersonName)
	REFERENCES Person(PersonName)
    ON UPDATE CASCADE ON DELETE SET NULL

);

CREATE TABLE ObamaTweets(
LinkToTweet VARCHAR(255),
UserName VARCHAR(255),
Likes INTEGER,
TweetImageURL VARCHAR(255),
CONSTRAINT pk_ObamaTweets_LinkToTweet PRIMARY KEY (LinkToTweet),
CONSTRAINT fk_ObamaTweets_LinkToTweet FOREIGN KEY (LinkToTweet)
	REFERENCES Tweets(LinkToTweet)
    ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE TrumpTweets(
LinkToTweet VARCHAR(255),
TweetId BIGINT,
Favorites INT,
CONSTRAINT pk_TrumpTweets_LinkToTweet PRIMARY KEY (LinkToTweet),
CONSTRAINT fk_TrumpTweets_LinkToTweet FOREIGN KEY (LinkToTweet)
	REFERENCES Tweets(LinkToTweet)
    ON UPDATE CASCADE ON DELETE CASCADE
);
