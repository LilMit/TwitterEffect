//Shruthi Raghuraman

package twitterEffect.model;

import java.math.BigInteger;

public class StockCompanies {


	protected String companyTicker;
	protected String company;
	protected long marketCap;
	protected MarketCapGroupType marketCapGroup;
	protected String sector;
	protected StockIndex stockIndex;
	
	public enum MarketCapGroupType {
		Mega, Large, Medium, Small, Micro
	}

	public StockCompanies(String companyTicker, String company, long marketCap, MarketCapGroupType marketCapGroup,
			String sector, StockIndex stockIndex) {
		this.companyTicker = companyTicker;
		this.company = company;
		this.marketCap = marketCap;
		this.marketCapGroup = marketCapGroup;
		this.sector = sector;
		this.stockIndex = stockIndex;
	}

	public StockIndex getStockIndex() {
		return stockIndex;
	}

	public void setStockIndex(StockIndex stockIndex) {
		this.stockIndex = stockIndex;
	}

	public String getCompanyTicker() {
		return companyTicker;
	}

	public void setCompanyTicker(String companyTicker) {
		this.companyTicker = companyTicker;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public long getMarketCap() {
		return marketCap;
	}

	public void setMarketCap(long marketCap) {
		this.marketCap = marketCap;
	}

	public MarketCapGroupType getMarketCapGroup() {
		return marketCapGroup;
	}

	public void setMarketCapGroup(MarketCapGroupType marketCapGroup) {
		this.marketCapGroup = marketCapGroup;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}
	
	
}
