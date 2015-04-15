SELECT 'Account','Campaign','Keyword','Match Type'
UNION ALL
SELECT 'JustAnswer' AS 'Account',
 campaign,word, 'Phrase' AS 'Match Type' FROM
 (
SELECT 
'JustAnswer' AS 'Account',
 campaign,word, 'Phrase' AS 'Match Type',impressions, clicks, converted_clicks, 
CONCAT('$',ROUND(cost,2)) AS cost
FROM ${dataTableName}
WHERE 
account='${accountName}'
AND(
(ROUND(clicks/impressions,3)<0.012 AND converted_clicks>0 AND ROUND(cost/converted_clicks,2)>25 AND ROUND(total_conv_value - cost,2)<0)
OR
(ROUND(clicks/impressions,3)<0.012 AND converted_clicks=0 AND ROUND(cost,2)>25 AND ROUND(total_conv_value - cost,2)<0)
) 
)
AS subSelect
INTO OUTFILE '${outputFile}'
FIELDS TERMINATED BY ','
    OPTIONALLY ENCLOSED BY '"'
LINES TERMINATED BY '\n';