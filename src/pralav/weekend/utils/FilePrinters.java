package pralav.weekend.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pralav.weekend.adwords.core.SearchTermMetrics;
import pralav.weekend.database.SQLTemplateUtil;
import freemarker.template.TemplateException;

public class FilePrinters {

    public static final String generateSQLForDB(String inputFileName, String dataTableName) {
        String generatedSQL = "";
        try {
            SQLTemplateUtil sqlTemplateUtil = new SQLTemplateUtil("sql");
            HashMap<String, Object> mapOfParams = new HashMap<String, Object>();

            mapOfParams.put("inputFileName", inputFileName);
            mapOfParams.put("dataTableName", dataTableName);
            generatedSQL = sqlTemplateUtil.getSQLFromTemplate(mapOfParams, "project.sql");
        } catch (IOException e) {
            System.out.println(e);
        } catch (TemplateException e) {
            System.out.println(e);
        }
        return generatedSQL;
    }

    public static final void printType1(Map<String, SearchTermMetrics> mapOfTerms, String outputFile) {
        FileUtilsPralav.appendToFile(outputFile, "word,clicks,impressions,cost,convertedClicks");
        boolean hasWordWithNoConvertedClicks = false;
        for (String key : mapOfTerms.keySet()) {
            SearchTermMetrics metrics = mapOfTerms.get(key);
            if (metrics.getConvertedClicks() > 0) {
                FileUtilsPralav.appendToFile(
                        outputFile,
                        "\n" + key + "," + metrics.getClicks() + "," + metrics.getImpressions() + ","
                                + metrics.getCost() + "," + metrics.getConvertedClicks() + ",");
            } else {
                hasWordWithNoConvertedClicks = true;
            }
        }

        if (hasWordWithNoConvertedClicks) {
            FileUtilsPralav.appendToFile(outputFile + "_0", "word,clicks,impressions,cost,convertedClicks");
            for (String key : mapOfTerms.keySet()) {
                SearchTermMetrics metrics = mapOfTerms.get(key);
                if (metrics.getConvertedClicks() == 0) {
                    FileUtilsPralav.appendToFile(outputFile + "_0", "\n" + key + "," + metrics.getClicks() + ","
                            + metrics.getImpressions() + "," + metrics.getCost() + "," + metrics.getConvertedClicks()
                            + ",");
                }
            }
        }
    }

    public static final void printType2(Map<String, SearchTermMetrics> mapOfTerms, String outputFile) {
        FileUtilsPralav
                .appendToFile(outputFile,
                        "word, campaign, clicks,impressions,cost, convertedClicks, CPC, CTR, Conversion Rate, CPA, Revenue, Profit");
        boolean hasWordWithNoConvertedClicks = false;
        for (String key : mapOfTerms.keySet()) {
            SearchTermMetrics metrics = mapOfTerms.get(key);
            if (metrics.getConvertedClicks() > 0) {
                FileUtilsPralav.appendToFile(
                        outputFile,
                        "\n" + key + "," + "_CMPGN," + metrics.getClicks() + "," + metrics.getImpressions() + ","
                                + metrics.getCost() + "," + metrics.getConvertedClicks() + ","
                                + metrics.calcAndGetCPC() + "," + metrics.calcAndGetCTR() + ","
                                + metrics.calcAndGetConvRate() + "," + metrics.calcAndGetCPA() + ","
                                + metrics.getTotalConvValue() + "," + metrics.calcAndGetProfit());
            } else {
                hasWordWithNoConvertedClicks = true;
            }
        }

        if (hasWordWithNoConvertedClicks) {
            FileUtilsPralav
                    .appendToFile(outputFile + "_0",
                            "word, campaign, clicks,impressions,cost, convertedClicks, CPC, CTR, Conversion Rate, CPA, Revenue, Profit");
            for (String key : mapOfTerms.keySet()) {
                SearchTermMetrics metrics = mapOfTerms.get(key);
                if (metrics.getConvertedClicks() == 0) {
                    FileUtilsPralav.appendToFile(
                            outputFile + "_0",
                            "\n" + key + "," + "_CMPGN," + metrics.getClicks() + "," + metrics.getImpressions() + ","
                                    + metrics.getCost() + "," + metrics.getConvertedClicks() + ","
                                    + metrics.calcAndGetCPC() + "," + metrics.calcAndGetCTR() + ","
                                    + metrics.calcAndGetConvRate() + "," + metrics.calcAndGetCPA() + ","
                                    + metrics.getTotalConvValue() + "," + metrics.calcAndGetProfit());
                }
            }
        }

    }

    public static String generateSQLForDBForCampaign(List<String> campaigns, String inputFileName, String dataTableName) {

        StringBuilder result = new StringBuilder();

        for (String campaign : campaigns) {
            try {
                SQLTemplateUtil sqlTemplateUtil = new SQLTemplateUtil("sql");
                HashMap<String, Object> mapOfParams = new HashMap<String, Object>();

                mapOfParams.put("inputFileName", inputFileName);
                mapOfParams.put("dataTableName", dataTableName);
                mapOfParams.put("campaignName", campaign);
                mapOfParams.put("campaignFileName", campaign.replaceAll("\\W+", "_"));
                result.append(sqlTemplateUtil.getSQLFromTemplate(mapOfParams, "campaign.sql"));
            } catch (IOException e) {
                System.out.println(e);
            } catch (TemplateException e) {
                System.out.println(e);
            }
        }

        return result.toString();
    }
}
