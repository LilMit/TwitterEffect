CREATE SCHEMA IF NOT EXISTS ExchangeRates;
USE ExchangeRates;

DROP TABLE IF EXISTS CurrencyExchangeRates;

CREATE TABLE CurrencyExchangeRates (
RecordId INT,
RecordDate DATE,
Australian_dollar FLOAT4,
Brazilian_real FLOAT4,
Canadian_dollar FLOAT4,
Swiss_franc FLOAT4,
Chinese_yuan_renminbi FLOAT4,
Czech_koruna FLOAT4,
Danish_krone FLOAT4,
UK_pound_sterling FLOAT4,
Hong_Kong_dollar FLOAT4,
Croatian_kuna FLOAT4,
Hungarian_forint FLOAT4,
Indonesian_rupiah FLOAT4,
Israeli_shekel FLOAT4,
Indian_rupee FLOAT4,
Iceland_krona FLOAT4,
Japanese_yen FLOAT4,
Korean_won FLOAT4,
Mexican_peso FLOAT4,
Malaysian_ringgit FLOAT4,
Norwegian_krone FLOAT4,
New_Zealand_dollar FLOAT4,
Philippine_peso FLOAT4,
Polish_zloty FLOAT4,
Romanian_leu FLOAT4,
Russian_rouble FLOAT4,
Swedish_krona FLOAT4,
Singapore_dollar FLOAT4,
Thai_baht FLOAT4,
Turkish_lira FLOAT4,
US_dollar FLOAT4,
South_African_rand FLOAT4,
EU_euro FLOAT4,
CONSTRAINT pk_CurrencyExchangeRates_RecordId PRIMARY KEY (RecordId),
CONSTRAINT uq_CurrencyExchangeRates_RecordId UNIQUE (RecordId)
);