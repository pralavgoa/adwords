CREATE TABLE `stm_${dataTableName}` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(100) NOT NULL,
  `ad_group` varchar(100) NOT NULL,
  `word` varchar(200) NOT NULL,
  `campaign` varchar(200) DEFAULT NULL,
  `impressions` int(11) DEFAULT NULL,
  `clicks` int(11) DEFAULT NULL,
  `converted_clicks` int(11) DEFAULT NULL,
  `total_conv_value` double DEFAULT NULL,
  `cost` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
