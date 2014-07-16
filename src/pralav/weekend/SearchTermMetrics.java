package pralav.weekend;

public class SearchTermMetrics {

	private int clicks;
	private int impressions;
	private double cost;
	private int convertedClicks;

	public int getImpressions() {
		return this.impressions;
	}

	public void setImpressions(int impressions) {
		this.impressions = impressions;
	}

	public double getCost() {
		return this.cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public int getConvertedClicks() {
		return this.convertedClicks;
	}

	public void setConvertedClicks(int convertedClicks) {
		this.convertedClicks = convertedClicks;
	}

	public int getClicks() {
		return clicks;
	}

	public void setClicks(int clicks) {
		this.clicks = clicks;
	}
	
	@Override
	public String toString() {
		return "clicks:" + clicks + "|impressions:" + impressions + "|cost:"
				+ cost + "|covertedClicks:" + convertedClicks + "\n";
	}
	
}
