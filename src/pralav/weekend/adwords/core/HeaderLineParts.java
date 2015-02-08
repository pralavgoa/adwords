package pralav.weekend.adwords.core;

public class HeaderLineParts {

	private final int accountColumnNumber;
	private final int wordsColumnNumber;
	private final int matchTypeColumnNumber;
    private final int campaignNameColumnNumber;
    private final int adGroupColumnNumber;
    private final int keywordColumnNumber;
    private final int clicksColumnNumber;
    private final int impressionsColumnNumber;
	private final int ctrColumnNumber;
    private final int avgCPCColumnNumber;
    private final int costColumnNumber;
    private final int addedExcludedColumnNumber;
    private final int totalConvValueColumnNumber;
    private final int convertedClicksColumnNumber;
    
    public HeaderLineParts(String[] headerLine) {

        int accountNo = -1, searchTermNo = -1,matchTypeNo=-1, campaignNo = -1,adGroupNo = -1,
        		keywordNo = -1,	clicksNo = -1, impressionsNo = -1, ctrNo = -1,avgCPCNo =-1,
        	 costNo = -1,addedExcludedNo=-1,totalConvValueNo=-1, convertedClicksNo = -1;

        int headerColumnNo = 0;
        for (String header : headerLine) {
            switch (header.toLowerCase().trim()) {
            case "account":
            	accountNo = headerColumnNo;
            	break;
            case "search term":
                searchTermNo = headerColumnNo;
                break;
            case "match type":
            	matchTypeNo = headerColumnNo;
            	break;
            case "campaign":
                campaignNo = headerColumnNo;
                break;
            case "ad group":
            	adGroupNo = headerColumnNo;
            	break;
            case "keyword":
            	keywordNo = headerColumnNo;
            	break;
            case "clicks":
                clicksNo = headerColumnNo;
                break;
            case "impressions":
                impressionsNo = headerColumnNo;
                break;
            case "ctr":
            	ctrNo = headerColumnNo;
            	break;
            case "avg. cpc":
            	avgCPCNo = headerColumnNo;
            	break;
            case "cost":
                costNo = headerColumnNo;
                break;
            case "added/excluded":
            	addedExcludedNo = headerColumnNo;
            	break;
            case "total conv. value":
            	totalConvValueNo = headerColumnNo;
            	break;
            case "converted clicks":
                convertedClicksNo = headerColumnNo;
                break;
            default:
                System.out.println("Ignoring header column: " + header);
                break;
            }
            headerColumnNo++;
        }

        this.accountColumnNumber = accountNo;
        this.wordsColumnNumber = searchTermNo;
        this.matchTypeColumnNumber = matchTypeNo;
        this.campaignNameColumnNumber = campaignNo;
        this.adGroupColumnNumber = adGroupNo;
        this.keywordColumnNumber = keywordNo;
        this.clicksColumnNumber = clicksNo;
        this.impressionsColumnNumber = impressionsNo;
        this.ctrColumnNumber = ctrNo;
        this.avgCPCColumnNumber = avgCPCNo;
        this.costColumnNumber = costNo;
        this.addedExcludedColumnNumber = addedExcludedNo;
        this.totalConvValueColumnNumber = totalConvValueNo;
        this.convertedClicksColumnNumber = convertedClicksNo;
    }

    public int getCampaignNameColumnNumber() {
        return this.campaignNameColumnNumber;
    }

    public int getWordsColumnNumber() {
        return this.wordsColumnNumber;
    }

    public int getClicksColumnNumber() {
        return this.clicksColumnNumber;
    }

    public int getImpressionsColumnNumber() {
        return this.impressionsColumnNumber;
    }

    public int getCostColumnNumber() {
        return this.costColumnNumber;
    }

    public int getConvertedClicksColumnNumber() {
        return this.convertedClicksColumnNumber;
    }
	
    public int getAccountColumnNumber() {
		return accountColumnNumber;
	}

	public int getMatchTypeColumnNumber() {
		return matchTypeColumnNumber;
	}

	public int getAdGroupColumnNumber() {
		return adGroupColumnNumber;
	}

	public int getKeywordColumnNumber() {
		return keywordColumnNumber;
	}

	public int getCtrColumnNumber() {
		return ctrColumnNumber;
	}

	public int getAvgCPCColumnNumber() {
		return avgCPCColumnNumber;
	}

	public int getAddedExcludedColumnNumber() {
		return addedExcludedColumnNumber;
	}

	public int getTotalConvValueColumnNumber() {
		return totalConvValueColumnNumber;
	}


}
