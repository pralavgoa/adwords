package pralav.weekend.adwords.core;

public class SearchTermMetrics {

    // term metadata
    private final String token;
    private final String campaign;

    // aggregated
    private int clicks;
    private int impressions;
    private double cost;
    private int convertedClicks;
    private double totalConvValue;

    private double valuePerConvertedClick;

    public SearchTermMetrics(String token, String campaign, int clicks, int impressions, double cost,
            int convertedClicks, double totalConvValue) {

        this.token = token;
        this.campaign = campaign;

        this.clicks = clicks;
        this.impressions = impressions;
        this.cost = cost;
        this.convertedClicks = convertedClicks;
        this.totalConvValue = totalConvValue;
    }

    public String getCampaign() {
        return this.campaign;
    }

    public int getImpressions() {
        return this.impressions;
    }

    public void incrementImpressions(int impressions) {
        this.impressions += impressions;
    }

    public double getCost() {
        return this.cost;
    }

    public void incrementCost(double cost) {
        this.cost += cost;
    }

    public int getConvertedClicks() {
        return this.convertedClicks;
    }

    public void incrementConvertedClicks(int convertedClicks) {
        this.convertedClicks += convertedClicks;
    }

    public double getTotalConvValue() {
        return this.totalConvValue;
    }

    public void incrementTotalConvValue(double totalConvValue) {
        this.totalConvValue += totalConvValue;
    }

    public int getClicks() {
        return this.clicks;
    }

    public void incrementClicks(int clicks) {
        this.clicks += clicks;
    }

    /**
     * @return Cost/Clicks
     */
    public double calcAndGetCPC() {
        if (this.clicks == 0) {
            if (this.cost != 0) {
                throw new IllegalArgumentException("The number of clicks is 0, but the cost is non zero");
            }
            return 0;
        }
        return (this.cost / this.clicks);
    }

    /**
     * @Return Clicks/Impressions
     */
    public double calcAndGetCTR() {
        if (this.impressions == 0) {
            if (this.clicks != 0) {
                throw new IllegalArgumentException(
                        "The number of impressions is 0, but the number of clicks is non zero");
            }
            return 0;
        }
        return (this.clicks / this.impressions);
    }

    /**
     * @Return Converted_Clicks/Clicks
     */
    public double calcAndGetConvRate() {
        if (this.clicks == 0) {
            if (this.convertedClicks != 0) {
                throw new IllegalArgumentException(
                        "The number of clicks is 0, but the number of converted clicks is non zero");
            }
            return 0;
        }
        return (this.convertedClicks / this.clicks);
    }

    /**
     * @Return Cost/Converted Clicks. IF No Converted Clicks, then CPA = Cost
     */
    public double calcAndGetCPA() {
        return (this.convertedClicks == 0) ? this.cost : (this.cost / this.convertedClicks);
    }

    /**
     * @Return Value/Conversion / Costs
     */
    public double calcAndGetValuePerConvPerCost() {
        if (this.cost == 0) {
            if (this.valuePerConvertedClick != 0) {
                throw new IllegalArgumentException(
                        "The number of clicks is 0, but the number of converted clicks is non zero");
            }
            return 0;
        }
        return (this.valuePerConvertedClick / this.cost);
    }

    /**
     * @Return Revenue - Costs
     */
    public double calcAndGetProfit() {
        return this.totalConvValue - this.cost;
    }

    @Override
    public String toString() {
        return "clicks:" + this.clicks + "|impressions:" + this.impressions + "|cost:" + this.cost + "|covertedClicks:"
                + this.convertedClicks + "\n";
    }

    public static SearchTermMetrics createSearchTermMetric(String word, FileLineParts lineParts) {
        return new SearchTermMetrics(word, lineParts.getCampaignName(), lineParts.getClicks(),
                lineParts.getImpressions(), lineParts.getCost(), lineParts.getConvertedClicks(),
                lineParts.getTotalConvValue());
    }

    public static void updateSearchTermMetrics(SearchTermMetrics metrics, FileLineParts lineParts) {
        metrics.incrementClicks(lineParts.getClicks());
        metrics.incrementImpressions(lineParts.getImpressions());
        metrics.incrementCost(lineParts.getCost());
        metrics.incrementConvertedClicks(lineParts.getConvertedClicks());
        metrics.incrementTotalConvValue(lineParts.getTotalConvValue());
    }

    public String getToken() {
        return token;
    }

}
