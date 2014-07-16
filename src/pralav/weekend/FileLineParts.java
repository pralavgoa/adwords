package pralav.weekend;

public class FileLineParts {

	private final String campaignName;
	private final String wordline;
	
	private final int clicks;
	private final int impressions;
	private final double cost;
	private final int convertedClicks;
	
	public FileLineParts(String line){
		try{
		String[] lineParts = line.split(",");
		campaignName = lineParts[0];
		wordline = lineParts[2];
		clicks = Integer.parseInt(lineParts[5]);
		impressions = Integer.parseInt(lineParts[6]);
		cost = Double.parseDouble(lineParts[9]);
		convertedClicks = Integer.parseInt(lineParts[11]);
		}catch(RuntimeException e){
			System.out.println(line);
			throw e;
		}
	}

	public int getClicks() {
		return clicks;
	}

	public int getImpressions() {
		return impressions;
	}

	public double getCost() {
		return cost;
	}

	public int getConvertedClicks() {
		return convertedClicks;
	}

	public String getWordline() {
		return wordline;
	}
	
}
