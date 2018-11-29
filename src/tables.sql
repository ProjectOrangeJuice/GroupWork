usernameBEGIN TRANSACTION;
DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
	`username`	TEXT NOT NULL UNIQUE,
	`firstName`	TEXT,
	`lastName`	TEXT,
	`telephone`	TEXT,
	`address`	TEXT,
	`postcode`	TEXT,
	`avatarPath`	TEXT,
	`accountBalance`	TEXT,
	`employmentDate`	TEXT,
	`staffType`	TEXT NOT NULL,
	PRIMARY KEY(`username`)
);
INSERT INTO `users` VALUES ('Bobby','Bob','Harrison','01234567','Nowhere','XXX YYY','image1.png','0.70',NULL,'user');
INSERT INTO `users` VALUES ('Timmy','Tim','Smith','223242','Elsewhere','XXX YYY','image2.png','5.20',NULL,'user');
INSERT INTO `users` VALUES ('Staff1','Staff','Staffy','253325','Here','XXX YYY','image6.png','0','01/02/03','staff');
INSERT INTO `users` VALUES ('Manager','Staffy2','Staffy2y','575686','There','XXX YYY','image8.png','0','03/02/01','staff');
DROP TABLE IF EXISTS `transactions`;
CREATE TABLE IF NOT EXISTS `transactions` (
	`transactionId`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
	`username`	INTEGER,
	`paid`	REAL,
	`dateTime`	TEXT
);
INSERT INTO `transactions` VALUES (1,1,5.0,NULL);
INSERT INTO `transactions` VALUES (2,1,10.0,NULL);
DROP TABLE IF EXISTS `system`;
CREATE TABLE IF NOT EXISTS `system` (
	`ver`	INTEGER
);
INSERT INTO `system` VALUES (3);
DROP TABLE IF EXISTS `resource`;
CREATE TABLE IF NOT EXISTS `resource` (
	`rId`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
	`type`	TEXT,
	`title`	TEXT,
	`description`	TEXT
);
INSERT INTO `resource` VALUES (1,'Book','Bookie',NULL);
INSERT INTO `resource` VALUES (2,'Laptop','Laptop 1',NULL);
INSERT INTO `resource` VALUES (3,'Book','Other book',NULL);
DROP TABLE IF EXISTS `fines`;
CREATE TABLE IF NOT EXISTS `fines` (
	`fineId`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
	`username`	INTEGER,
	`copyId`	INTEGER,
	`amount`	REAL,
	`dateTime`	TEXT,
	`paid`	INTEGER
);
INSERT INTO `fines` VALUES (1,1,4,5.0,NULL,1);
INSERT INTO `fines` VALUES (2,1,5,10.0,NULL,1);
DROP TABLE IF EXISTS `copies`;
CREATE TABLE IF NOT EXISTS `copies` (
	`CopyId`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
	`RId`	INTEGER NOT NULL,
	`keeper`	INTEGER,
	`date`	TEXT
);
INSERT INTO `copies` VALUES (1,1,NULL,NULL);
INSERT INTO `copies` VALUES (2,1,NULL,NULL);
INSERT INTO `copies` VALUES (3,1,NULL,NULL);
INSERT INTO `copies` VALUES (4,1,NULL,NULL);
INSERT INTO `copies` VALUES (5,2,NULL,NULL);
INSERT INTO `copies` VALUES (6,2,NULL,NULL);
INSERT INTO `copies` VALUES (7,2,NULL,NULL);
INSERT INTO `copies` VALUES (8,2,NULL,NULL);
INSERT INTO `copies` VALUES (9,3,NULL,NULL);
INSERT INTO `copies` VALUES (10,3,NULL,NULL);
DROP TABLE IF EXISTS `borrowRecords`;
CREATE TABLE IF NOT EXISTS `borrowRecords` (
	`borrowId`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
	`copyId`	INTEGER NOT NULL,
	`username`	INTEGER NOT NULL,
	`description`	TEXT
);
COMMIT;
