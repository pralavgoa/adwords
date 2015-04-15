INSERT INTO ${stagingTableName} (account, ad_group, word, campaign, impressions, clicks, converted_clicks, total_conv_value, cost) 
  SELECT account, ad_group, word, campaign, impressions, clicks, converted_clicks, total_conv_value, cost
  FROM `${inputTableName}`;