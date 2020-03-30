//Shruthi Raghuraman

package twitterEffect.model;


public class StockIndex {
	protected String indexTicker;
	protected String indexName;
	
	public StockIndex(String indexTicker, String indexName) {
		this.indexTicker = indexTicker;
		this.indexName = indexName;
	}

	public String getIndexTicker() {
		return indexTicker;
	}

	public void setIndexTicker(String indexTicker) {
		this.indexTicker = indexTicker;
	}

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}
	
	
}
