CREATE TABLE `stm_agg_4_japan` AS
SELECT account, ad_group,word, campaign, SUM(impressions) as impressions, SUM(clicks) as clicks, 
SUM(converted_clicks) as converted_clicks, SUM(total_conv_value) as total_conv_value, SUM(cost)/1000000 as cost
FROM `stm_agg_staging_4`
WHERE account='Japan JustAnswer'
GROUP BY
account,ad_group,word,campaign;

CREATE TABLE `stm_agg_4_ja` AS
SELECT account, ad_group,word, campaign, SUM(impressions) as impressions, SUM(clicks) as clicks, 
SUM(converted_clicks) as converted_clicks, SUM(total_conv_value) as total_conv_value, SUM(cost)/1000000 as cost
FROM `stm_agg_staging_4`
WHERE account='JustAnswer'
GROUP BY
account,ad_group,word,campaign;

CREATE TABLE `stm_agg_4_ja2` AS
SELECT account, ad_group,word, campaign, SUM(impressions) as impressions, SUM(clicks) as clicks, 
SUM(converted_clicks) as converted_clicks, SUM(total_conv_value) as total_conv_value, SUM(cost)/1000000 as cost
FROM `stm_agg_staging_4`
WHERE account='JustAnswer 2'
GROUP BY
account,ad_group,word,campaign;

CREATE TABLE `stm_agg_4_ja3` AS
SELECT account, ad_group,word, campaign, SUM(impressions) as impressions, SUM(clicks) as clicks, 
SUM(converted_clicks) as converted_clicks, SUM(total_conv_value) as total_conv_value, SUM(cost)/1000000 as cost
FROM `stm_agg_staging_4`
WHERE account='JustAnswer 3'
GROUP BY
account,ad_group,word,campaign;

CREATE TABLE `stm_agg_4_ja5` AS
SELECT account, ad_group,word, campaign, SUM(impressions) as impressions, SUM(clicks) as clicks, 
SUM(converted_clicks) as converted_clicks, SUM(total_conv_value) as total_conv_value, SUM(cost)/1000000 as cost
FROM `stm_agg_staging_4`
WHERE account='JustAnswer 5'
GROUP BY
account,ad_group,word,campaign;

CREATE TABLE `stm_agg_4_spain` AS
SELECT account, ad_group,word, campaign, SUM(impressions) as impressions, SUM(clicks) as clicks, 
SUM(converted_clicks) as converted_clicks, SUM(total_conv_value) as total_conv_value, SUM(cost)/1000000 as cost
FROM `stm_agg_staging_4`
WHERE account='Spanish JustAnswer'
GROUP BY
account,ad_group,word,campaign;

CREATE TABLE `stm_agg_4_uk` AS
SELECT account, ad_group,word, campaign, SUM(impressions) as impressions, SUM(clicks) as clicks, 
SUM(converted_clicks) as converted_clicks, SUM(total_conv_value) as total_conv_value, SUM(cost)/1000000 as cost
FROM `stm_agg_staging_4`
WHERE account='UK JustAnswer'
GROUP BY
account,ad_group,word,campaign;