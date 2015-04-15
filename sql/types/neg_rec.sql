SELECT 'Account','Campaign','Keyword','Match Type','impressions','clicks','convertedClicks','cost',
'CTR','CPC','Conversion Rate','CPA','Revenue','ROAS','Profit'
UNION ALL
SELECT 
'JustAnswer' AS 'Account',
 campaign,word, 'Phrase' AS 'Match Type',impressions, clicks, converted_clicks, 
CONCAT('$',ROUND(cost,2)) AS cost,
CONCAT(ROUND(clicks*100/impressions,2),'%') AS ctr,
CONCAT('$',ROUND(cost/clicks,2)) AS cpc,
CONCAT(ROUND(converted_clicks*100/clicks,2),'%') AS conversion_rate,
CASE
	WHEN converted_clicks>0 THEN CONCAT('$',ROUND(cost/converted_clicks,2)) 
	WHEN converted_clicks=0 THEN ROUND(cost,2)
END AS cpa,
CONCAT('$',ROUND(total_conv_value,2)) AS revenue,
ROUND(total_conv_value/cost,2) AS roas,
CONCAT('$',ROUND(total_conv_value - cost,2)) AS profit
FROM ${dataTableName}
WHERE 
account='${accountName}'
AND(
(ROUND(clicks/impressions,3)<0.012 AND converted_clicks>0 AND ROUND(cost/converted_clicks,2)>25 AND ROUND(total_conv_value - cost,2)<0)
OR
(ROUND(clicks/impressions,3)<0.012 AND converted_clicks=0 AND ROUND(cost,2)>25 AND ROUND(total_conv_value - cost,2)<0)
)
INTO OUTFILE '${outputFile}'
FIELDS TERMINATED BY ','
    OPTIONALLY ENCLOSED BY '"'
LINES TERMINATED BY '\n';