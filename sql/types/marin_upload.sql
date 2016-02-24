SELECT 'Account','Campaign','Ad Group','Keyword','Match Type'
UNION ALL
SELECT account,
 campaign, ad_group, word, 'Phrase' AS 'Match Type' FROM
 (
SELECT 
account,
 campaign,ad_group, word, 'Phrase' AS 'Match Type',impressions, clicks, converted_clicks, 
CONCAT('$',ROUND(cost,2)) AS cost
FROM ${dataTableName}
WHERE 
    (
    ROUND(clicks/impressions,3)<0.03 AND 
    converted_clicks>0 AND 
    ROUND(cost/converted_clicks,2)>25 AND 
    ROUND(total_conv_value/cost,2) < 1.1
  )
OR
  (
    ROUND(clicks/impressions,3)<0.03 AND 
    converted_clicks=0 AND 
    ROUND(cost,2)>25 AND 
    ROUND(total_conv_value/cost,2) < 1.1
  )
)
AS subSelect
INTO OUTFILE '${outputFile}'
FIELDS TERMINATED BY ','
    OPTIONALLY ENCLOSED BY '"'
LINES TERMINATED BY '\n';