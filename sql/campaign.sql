SELECT 'word','Campaign','impressions','clicks','convertedClicks','cost',
'CTR','CPC','Conversion Rate','CPA','Revenue','ROAS','Profit'
UNION ALL
SELECT 
word, campaign, impressions, clicks, converted_clicks, 
CONCAT('$',ROUND(cost,2)) AS cost,
CONCAT(ROUND(clicks*100/impressions,2),'%') AS ctr,
CONCAT('$',ROUND(cost/clicks,2)) AS cpc,
CONCAT(ROUND(converted_clicks*100/clicks,2),'%') AS conversion_rate,
CONCAT('$',ROUND(cost,2)) AS cpa,
CONCAT('$',ROUND(total_conv_value,2)) AS revenue,
ROUND(total_conv_value/cost,2) AS roas,
CONCAT('$',ROUND(total_conv_value - cost,2)) AS profit
FROM ${dataTableName}
WHERE 
converted_clicks=0
AND
campaign='${campaignName}'
INTO OUTFILE 'C:\\_Pralav\\personal\\projects\\adwords\\data\\output\\${inputFileName}\\campaign\\${campaignFileName}_0.csv'
FIELDS TERMINATED BY ','
    OPTIONALLY ENCLOSED BY '"'
LINES TERMINATED BY '\n';

SELECT 'word','Campaign','impressions','clicks','convertedClicks','cost',
'CTR','CPC','Conversion Rate','CPA','Revenue','ROAS','Profit'
UNION ALL
SELECT 
word, campaign, impressions, clicks, converted_clicks, 
CONCAT('$',ROUND(cost,2)) AS cost,
CONCAT(ROUND(clicks*100/impressions,2),'%') AS ctr,
CONCAT('$',ROUND(cost/clicks,2)) AS cpc,
CONCAT(ROUND(converted_clicks*100/clicks,2),'%') AS conversion_rate,
CONCAT('$',ROUND(cost/converted_clicks,2)) AS cpa,
CONCAT('$',ROUND(total_conv_value,2)) AS revenue,
ROUND(total_conv_value/cost,2) AS roas,
CONCAT('$',ROUND(total_conv_value - cost,2)) AS profit
FROM ${dataTableName}
WHERE 
converted_clicks>0
AND
campaign='${campaignName}'
INTO OUTFILE 'C:\\_Pralav\\personal\\projects\\adwords\\data\\output\\${inputFileName}\\campaign\\${campaignFileName}_1.csv'
FIELDS TERMINATED BY ','
    OPTIONALLY ENCLOSED BY '"'
LINES TERMINATED BY '\n';