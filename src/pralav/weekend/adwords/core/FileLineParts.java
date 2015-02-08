package pralav.weekend.adwords.core;


public class FileLineParts {

	private final String accountName;
	private final String wordline;
	private final String matchType;
    private final String campaignName;
    private final String adGroup;
    private final String keyword;
    private final int clicks;
    private final int impressions;
    private final String ctr;
    private final String avgCPC;
    private final double cost;
    private final String addedExcluded;
    private final int totalConvValue;
    private final int convertedClicks;

    public FileLineParts(HeaderLineParts headerLineParts, String[] lineParts) {
        try {
        	this.accountName = lineParts[headerLineParts.getAccountColumnNumber()];
        	this.wordline = lineParts[headerLineParts.getWordsColumnNumber()];
        	this.matchType = lineParts[headerLineParts.getMatchTypeColumnNumber()];
            this.campaignName = lineParts[headerLineParts.getCampaignNameColumnNumber()];
            this.adGroup = lineParts[headerLineParts.getAdGroupColumnNumber()];
            this.keyword = lineParts[headerLineParts.getKeywordColumnNumber()];
            this.clicks = Integer.parseInt(lineParts[headerLineParts.getClicksColumnNumber()]);
            this.impressions = Integer.parseInt(lineParts[headerLineParts.getImpressionsColumnNumber()]);
            this.ctr = lineParts[headerLineParts.getCtrColumnNumber()];
            this.avgCPC = lineParts[headerLineParts.getAvgCPCColumnNumber()];
            this.cost = Double.parseDouble(lineParts[headerLineParts.getCostColumnNumber()].replaceAll(",", ""));
            this.addedExcluded = lineParts[headerLineParts.getAddedExcludedColumnNumber()];
            this.totalConvValue = Integer.parseInt(lineParts[headerLineParts.getTotalConvValueColumnNumber()]);
            this.convertedClicks = Integer.parseInt(lineParts[headerLineParts.getConvertedClicksColumnNumber()]);
        } catch (RuntimeException e) {
            for (String part : lineParts) {
                System.out.print(part + ",");
            }
            System.out.println("");
            throw e;
        }
    }

    public String getAccountName() {
		return accountName;
	}

	public String getMatchType() {
		return matchType;
	}

	public String getAdGroup() {
		return adGroup;
	}

	public String getKeyword() {
		return keyword;
	}

	public String getCtr() {
		return ctr;
	}

	public String getAvgCPC() {
		return avgCPC;
	}

	public String getAddedExcluded() {
		return addedExcluded;
	}

	public int getTotalConvValue() {
		return totalConvValue;
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
