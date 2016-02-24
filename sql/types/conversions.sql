SELECT 'word','Campaign','Ad Group','impressions','clicks','convertedClicks','cost',
'CTR','CPC','Conversion Rate','CPA','Revenue','ROAS','Profit'
UNION ALL
SELECT 
word, campaign,ad_group, impressions, clicks, converted_clicks, 
CONCAT('$',ROUND(cost,2)) AS cost,
CONCAT(ROUND(clicks*100/impressions,2),'%') AS ctr,
CONCAT('$',ROUND(cost/clicks,2)) AS cpc,
CONCAT(ROUND(converted_clicks*100/clicks,2),'%') AS conversion_rate,
CONCAT('$',ROUND(cost,2)) AS cpa,
CONCAT('$',ROUND(total_conv_value,2)) AS revenue,
ROUND(total_conv_value/cost,2) AS roas,
CONCAT('$',ROUND(total_conv_value - cost,2)) AS profit
FROM ${dataTableName}
WHERE ${mainWhereClause}
AND account='${accountName}'
${addWhereClause}
INTO OUTFILE '${outputFile}'
FIELDS TERMINATED BY ','
    OPTIONALLY ENCLOSED BY '"'
LINES TERMINATED BY '\n';