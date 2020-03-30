//Shruthi Raghuraman

package twitterEffect.model;

import java.util.Date;


public class IndexDailySummary {


	protected Date summaryDate;
	protected StockIndex stockIndex;
	protected float Open;
	protected float High;
	protected float Low;
	protected float Close;
	protected float AdjClose;
	protected long Volume;
	protected int summaryId;

	public IndexDailySummary(Date summaryDate, StockIndex stockIndex, float open, float high,
			float low, float close, float adjClose, long volume, int summaryId) {
		this.summaryDate = summaryDate;
		this.stockIndex = stockIndex;
		this.Open = open;
		this.High = high;
		this.Low = low;
		this.Close = close;
		this.AdjClose = adjClose;
		this.Volume = volume;
		this.summaryId = summaryId;
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



	
	public StockIndex getStockIndex() {
		return stockIndex;
	}




	public void setStockIndex(StockIndex stockIndex) {
		this.stockIndex = stockIndex;
	}




	public float getOpen() {
		return Open;
	}

	public void setOpen(float open) {
		Open = open;
	}

	public float getHigh() {
		return High;
	}

	public void setHigh(float high) {
		High = high;
	}

	public float getLow() {
		return Low;
	}

	public void setLow(float low) {
		Low = low;
	}

	public float getClose() {
		return Close;
	}

	public void setClose(float close) {
		Close = close;
	}

	public float getAdjClose() {
		return AdjClose;
	}

	public void setAdjClose(float adjClose) {
		AdjClose = adjClose;
	}

	public long getVolume() {
		return Volume;
	}

	public void setVolume(long volume) {
		Volume = volume;
	}
	

}
