package pralav.weekend.adwords.core;

public class SearchTermMetrics {

	//aggregated
	private int clicks;
	private int impressions;
	private double cost;
	private int convertedClicks;
	private int totalConvValue;
	
	//term metadata
	private String campaign;
	
	
	private double valuePerConvertedClick;

	public SearchTermMetrics(String campaign,int clicks, int impressions, double cost, int convertedClicks, int totalConvValue){
		
		this.campaign = campaign;
		
		this.clicks = clicks;
		this.impressions = impressions;
		this.cost = cost;
		this.convertedClicks = convertedClicks;
		this.totalConvValue = totalConvValue;
	}
	
	public String getCampaign(){
		return this.campaign;
	}
	
	public int getImpressions() {
		return this.impressions;
	}

	public void incrementImpressions(int impressions){
		this.impressions += impressions;
	}

	public double getCost() {
		return this.cost;
	}

	public void incrementCost(double cost){
		this.cost+=cost;
	}

	public int getConvertedClicks() {
		return this.convertedClicks;
	}
	
	public void incrementConvertedClicks(int convertedClicks){
		this.convertedClicks += convertedClicks;
	}
	
	public int getTotalConvValue(){
		return this.totalConvValue;
	}
	
	public void incrementTotalConvValue(int totalConvValue){
		this.totalConvValue += totalConvValue;
	}

	public int getClicks() {
		return clicks;
	}

	public void incrementClicks(int clicks){
		this.clicks += clicks;
	}
	
	/**
	 * @return Cost/Clicks
	 */
	public double calcAndGetCPC(){
		return (this.cost/this.clicks);
	}
	
	/**
	 * @Return Clicks/Impressions
	 */
	public double calcAndGetCTR(){
		return (this.clicks/this.impressions);
	}
	
	/**
	 * @Return Converted_Clicks/Clicks
	 */
	public double calcAndGetConvRate(){
		return (this.convertedClicks/this.clicks);
	}
	
	/**
	 * @Return Cost/Converted Clicks. IF No Converted Clicks, then CPA = Cost
	 */
	public double calcAndGetCPA(){
		return (this.convertedClicks==0)?this.cost:(this.cost/this.convertedClicks);
	}
	
	/**
	 * @Return Value/Conversion / Costs
	 */
	public double calcAndGetValuePerConvPerCost(){
		return (this.valuePerConvertedClick/this.cost);
	}
	
	/**
	 * @Return Revenue - Costs
	 */
	public double calcAndGetProfit(){
		return this.totalConvValue - this.cost;
	}

	@Override
	public String toString() {
		return "clicks:" + clicks + "|impressions:" + impressions + "|cost:"
				+ cost + "|covertedClicks:" + convertedClicks + "\n";
	}
	
	public static SearchTermMetrics createSearchTermMetric(FileLineParts lineParts){
		return new SearchTermMetrics(lineParts.getCampaignName(), lineParts.getClicks(),lineParts.getImpressions(),
				lineParts.getCost(), lineParts.getConvertedClicks(), lineParts.getTotalConvValue());
	}
	
	public static void updateSearchTermMetrics(SearchTermMetrics metrics, FileLineParts lineParts){
		metrics.incrementClicks(lineParts.getClicks());
		metrics.incrementImpressions(lineParts.getImpressions());
		metrics.incrementCost(lineParts.getCost());
		metrics.incrementConvertedClicks(lineParts.getConvertedClicks());
		metrics.incrementTotalConvValue(lineParts.getTotalConvValue());
	}

}
