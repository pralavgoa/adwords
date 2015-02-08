package pralav.weekend.utils;

import java.util.Map;

import pralav.weekend.adwords.core.SearchTermMetrics;

public class FilePrinters {

	public static final void printType1(Map<String, SearchTermMetrics> mapOfTerms,String outputFile){
		FileUtilsPralav.appendToFile(outputFile, "word,clicks,impressions,cost,convertedClicks");
		boolean hasWordWithNoConvertedClicks = false;
		for (String key : mapOfTerms.keySet()) {
			SearchTermMetrics metrics = mapOfTerms.get(key);
			if(metrics.getConvertedClicks()>0){
				FileUtilsPralav.appendToFile(outputFile, "\n" + key + "," + metrics.getClicks() + "," + metrics.getImpressions() + ","
						+ metrics.getCost() + "," + metrics.getConvertedClicks() + ",");
			}else{
				hasWordWithNoConvertedClicks = true;
			}
		}

		if(hasWordWithNoConvertedClicks){
			FileUtilsPralav.appendToFile(outputFile+"_0", "word,clicks,impressions,cost,convertedClicks");
			for (String key : mapOfTerms.keySet()) {
				SearchTermMetrics metrics = mapOfTerms.get(key);
				if(metrics.getConvertedClicks()==0){
					FileUtilsPralav.appendToFile(outputFile+"_0", "\n" + key + "," + metrics.getClicks() + "," + metrics.getImpressions() + ","
							+ metrics.getCost() + "," + metrics.getConvertedClicks() + ",");
				}
			}
		}
	}

	public static final void printType2(Map<String, SearchTermMetrics> mapOfTerms,String outputFile){
		FileUtilsPralav.appendToFile(outputFile, "word, campaign, clicks,impressions,cost, convertedClicks, CPC, CTR, Conversion Rate, CPA, Revenue, Profit");
		boolean hasWordWithNoConvertedClicks = false;
		for (String key : mapOfTerms.keySet()) {
			SearchTermMetrics metrics = mapOfTerms.get(key);
			if(metrics.getConvertedClicks()>0){
				FileUtilsPralav.appendToFile(outputFile, "\n" + key + ","+"_CMPGN," + metrics.getClicks() + "," + metrics.getImpressions() + ","
						+ metrics.getCost() +","+ metrics.getConvertedClicks() + ","+metrics.calcAndGetCPC()+","+metrics.calcAndGetCTR()+","+metrics.calcAndGetConvRate()+","+metrics.calcAndGetCPA()+ ","+metrics.getTotalConvValue()+","+metrics.calcAndGetProfit());
			}else{
				hasWordWithNoConvertedClicks = true;
			}
		}

		if(hasWordWithNoConvertedClicks){
			FileUtilsPralav.appendToFile(outputFile+"_0", "word, campaign, clicks,impressions,cost, convertedClicks, CPC, CTR, Conversion Rate, CPA, Revenue, Profit");
			for (String key : mapOfTerms.keySet()) {
				SearchTermMetrics metrics = mapOfTerms.get(key);
				if(metrics.getConvertedClicks()==0){
					FileUtilsPralav.appendToFile(outputFile+"_0", "\n" + key + ","+"_CMPGN," + metrics.getClicks() + "," + metrics.getImpressions() + ","
							+ metrics.getCost() +","+ metrics.getConvertedClicks() + ","+metrics.calcAndGetCPC()+","+metrics.calcAndGetCTR()+","+metrics.calcAndGetConvRate()+","+metrics.calcAndGetCPA()+ ","+metrics.getTotalConvValue()+","+metrics.calcAndGetProfit());
				}
			}
		}


	}

}
