package pralav.weekend;

public class HeaderLineParts {

    private final int campaignNameColumnNumber;
    private final int wordsColumnNumber;
    private final int clicksColumnNumber;
    private final int impressionsColumnNumber;
    private final int costColumnNumber;
    private final int convertedClicksColumnNumber;

    public HeaderLineParts(String[] headerLine) {

        int campaignNo = -1, searchTermNo = -1, clicksNo = -1, impressionsNo = -1, costNo = -1, convertedClicksNo = -1;

        int headerColumnNo = 0;
        for (String header : headerLine) {
            switch (header.toLowerCase().trim()) {
            case "campaign":
                campaignNo = headerColumnNo;
                break;
            case "search term":
                searchTermNo = headerColumnNo;
                break;
            case "clicks":
                clicksNo = headerColumnNo;
                break;
            case "impressions":
                impressionsNo = headerColumnNo;
                break;
            case "cost":
                costNo = headerColumnNo;
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

        this.campaignNameColumnNumber = campaignNo;
        this.wordsColumnNumber = searchTermNo;
        this.clicksColumnNumber = clicksNo;
        this.impressionsColumnNumber = impressionsNo;
        this.costColumnNumber = costNo;
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

}
