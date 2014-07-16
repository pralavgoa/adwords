package pralav.weekend;

public class FileLineParts {

    private final String campaignName;
    private final String wordline;

    private final int clicks;
    private final int impressions;
    private final double cost;
    private final int convertedClicks;

    public FileLineParts(HeaderLineParts headerLineParts, String[] lineParts) {
        try {
            this.campaignName = lineParts[headerLineParts.getCampaignNameColumnNumber()];
            this.wordline = lineParts[headerLineParts.getWordsColumnNumber()];
            this.clicks = Integer.parseInt(lineParts[headerLineParts.getClicksColumnNumber()]);
            this.impressions = Integer.parseInt(lineParts[headerLineParts.getImpressionsColumnNumber()]);
            this.cost = Double.parseDouble(lineParts[headerLineParts.getCostColumnNumber()].replaceAll(",", ""));
            this.convertedClicks = Integer.parseInt(lineParts[headerLineParts.getConvertedClicksColumnNumber()]);
        } catch (RuntimeException e) {
            for (String part : lineParts) {
                System.out.print(part + ",");
            }
            System.out.println("");
            throw e;
        }
    }

    public int getClicks() {
        return this.clicks;
    }

    public int getImpressions() {
        return this.impressions;
    }

    public double getCost() {
        return this.cost;
    }

    public int getConvertedClicks() {
        return this.convertedClicks;
    }

    public String getWordline() {
        return this.wordline;
    }

    public String getCampaignName() {
        return campaignName;
    }

}
