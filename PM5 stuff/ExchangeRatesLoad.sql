USE ExchangeRates;

LOAD DATA LOCAL INFILE '/Users/elaineparr/Documents/GitHub/TwitterEffect/PM5 stuff/exchange_rates.csv' INTO TABLE CurrencyExchangeRates
	FIELDS TERMINATED BY ','
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES;