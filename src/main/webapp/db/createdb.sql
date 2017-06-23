BEGIN TRANSACTION;
CREATE TABLE "mycategory" (
	`id`	INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE,
	`name`	TEXT NOT NULL,
	`star`	TEXT,
	`items`	TEXT,
	`account`	INTEGER,
	`memo`	TEXT,
	`create_time`	INTEGER
);
INSERT INTO `mycategory` VALUES (1,'测试标题','2','3rd/unitegallery/images/big/image2.jpg',10,'',1496822059472);
INSERT INTO `mycategory` VALUES (2,'name1','2','3rd/unitegallery/images/big/image3.jpg;3rd/unitegallery/images/big/image2.jpg',10,'',NULL);
INSERT INTO `mycategory` VALUES (3,'name1','3','3rd/unitegallery/images/big/image4.jpg',10,'',NULL);
INSERT INTO `mycategory` VALUES (4,'Good Item','2','',10,'',1496913301174);
INSERT INTO `mycategory` VALUES (5,'name1','1','',10,'',NULL);
INSERT INTO `mycategory` VALUES (6,'name1','3','',10,'',NULL);
INSERT INTO `mycategory` VALUES (7,'name1','4','',10,'',NULL);
INSERT INTO `mycategory` VALUES (8,'name1','4','',10,'',NULL);
INSERT INTO `mycategory` VALUES (9,'name1','4','',10,'',NULL);
INSERT INTO `mycategory` VALUES (10,'name1','4','',10,'',NULL);
INSERT INTO `mycategory` VALUES (11,'name1','2','',10,'',NULL);
INSERT INTO `mycategory` VALUES (12,'name1','2','',10,'',NULL);
INSERT INTO `mycategory` VALUES (13,'name1','2','',10,'',NULL);
INSERT INTO `mycategory` VALUES (14,'name1','2','',10,'',NULL);
INSERT INTO `mycategory` VALUES (15,'name1','2','',10,'',NULL);
INSERT INTO `mycategory` VALUES (16,'name1','2','',10,'',NULL);
INSERT INTO `mycategory` VALUES (17,'name1','2','',10,'',NULL);
INSERT INTO `mycategory` VALUES (18,'name1','2','',10,'',NULL);
INSERT INTO `mycategory` VALUES (19,'name1','2','',10,'',NULL);
INSERT INTO `mycategory` VALUES (20,'name1','2','',10,'',NULL);
INSERT INTO `mycategory` VALUES (21,'你好，这是测试数据','0','3rd/unitegallery/images/big/image3.jpg;3rd/unitegallery/images/big/image2.jpg',2,NULL,1497073983219);
CREATE TABLE "category" (
	`id`	INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE,
	`name`	TEXT NOT NULL,
	`item_table_name`	TEXT NOT NULL,
	`memo`	TEXT
);
INSERT INTO `category` VALUES (1,'My Category','mycategory',NULL);
COMMIT;