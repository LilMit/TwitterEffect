//Shruthi Raghuraman

package twitterEffect.model;

import java.util.Date;

public class StockCompanyDailySummary {
	//Change String to Company
	protected StockCompanies stockCompany;
	protected Date summaryDate;
	protected float openPrice;
	protected float high;
	protected float low;
	protected float closePrice;
	protected long volume;
	protected int summaryId;

	
	public StockCompanyDailySummary(StockCompanies stockCompany, Date summaryDate, float openPrice, float high,
			float low, float closePrice, long volume, int summaryId) {
		this.stockCompany = stockCompany;
		this.summaryDate = summaryDate;
		this.openPrice = openPrice;
		this.high = high;
		this.low = low;
		this.closePrice = closePrice;
		this.volume = volume;
		this.summaryId = summaryId;

	}

	public StockCompanies getStockCompany() {
		return stockCompany;
	}

	public void setStockCompany(StockCompanies stockCompany) {
		this.stockCompany = stockCompany;
	}

	public long getVolume() {
		return volume;
	}

	public void setVolume(long volume) {
		this.volume = volume;
	}

	public int getSummaryId() {
		return summaryId;
	}

	public void setSummaryId(int summaryId) {
		this.summaryId = summaryId;
	}


	public Date getSummaryDate() {
		return summaryDate;
	}

	public void setSummaryDate(Date summaryDate) {
		this.summaryDate = summaryDate;
	}

	public float getOpenPrice() {
		return openPrice;
	}

	public void setOpenPrice(float openPrice) {
		this.openPrice = openPrice;
	}

	public float getHigh() {
		return high;
	}

	public void setHigh(float high) {
		this.high = high;
	}

	public float getLow() {
		return low;
	}

	public void setLow(float low) {
		this.low = low;
	}

	public float getClosePrice() {
		return closePrice;
	}

	public void setClosePrice(float closePrice) {
		this.closePrice = closePrice;
	}


}
