${where_clause} converted_clicks>0,converted_clicks=0,ctr<0.5 && cpa>0.75,
${filename} JustAnswer_1,JustAnswer_0,JustAnswer_Negatives_Recommendation,JustAnswer_Marin_UploadFile

SELECT 'word','campaign','impressions','clicks','converted_clicks','cost',
'ctr','cpc','conversion_rate','cpa','revenue','profit'
UNION ALL
SELECT 
word, campaign, impressions, clicks, converted_clicks, 
ROUND(cost,2) AS cost,
ROUND(clicks/impressions,2) AS ctr,
ROUND(cost/clicks,2) AS cpc,
ROUND(converted_clicks/clicks,2) AS conversion_rate,
ROUND(cost/converted_clicks,2) AS cpa,
ROUND(total_conv_value,2) AS revenue,
ROUND(total_conv_value - cost,2) AS profit
FROM search_term_metrics
WHERE ${where_clause}
INTO OUTFILE 'C:\\_Pralav\\personal\\projects\\adwords\\data\\${filename}.csv'
FIELDS TERMINATED BY ','
    OPTIONALLY ENCLOSED BY '"'
LINES TERMINATED BY '\n';