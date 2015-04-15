CREATE TABLE `stm_${fileName}_agg` AS
SELECT account, ad_group,word, campaign, SUM(impressions) as impressions, SUM(clicks) as clicks, 
SUM(converted_clicks) as converted_clicks, SUM(total_conv_value) as total_conv_value, SUM(cost) as cost
FROM `stm_${fileName}`
GROUP BY
account,ad_group,word,campaign;