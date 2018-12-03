BEGIN TRANSACTION;
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
	`staffID`	INTEGER NOT NULL,
	`staffType`	TEXT NOT NULL,
	PRIMARY KEY(`username`)
);

DROP TABLE IF EXISTS `transactions`;
CREATE TABLE IF NOT EXISTS `transactions` (
	`transactionId`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
	`username`	TEXT,
	`paid`	REAL,
	`dateTime`	TEXT
);
INSERT INTO `transactions` VALUES (1,1,5.0,"sometime");
INSERT INTO `transactions` VALUES (2,1,10.0,"othertime");

DROP TABLE IF EXISTS `system`;
CREATE TABLE IF NOT EXISTS `system` (
	`ver`	INTEGER
);
INSERT INTO `system` VALUES (3);

DROP TABLE IF EXISTS `resource`;
CREATE TABLE IF NOT EXISTS `resource` (
	`rID`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`title`	TEXT,
	`year`  INTEGER
);
INSERT INTO `resource` VALUES (1,'Bookie',1998);
INSERT INTO `resource` VALUES (2,'Laptop 1',2000);
INSERT INTO `resource` VALUES (3,'Other book',2000);

DROP TABLE IF EXISTS `fines`;
CREATE TABLE IF NOT EXISTS `fines` (
	`fineId`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
	`username`	TEXT,
	`copyId`	INTEGER,
	`daysOver`	INTEGER,
	`amount`	REAL,
	`dateTime`	TEXT,
	`paid`	INTEGER
);
INSERT INTO `fines` VALUES (1,1,4,3,5.0,NULL,1);
INSERT INTO `fines` VALUES (2,1,5,100,10.0,NULL,1);

DROP TABLE IF EXISTS `copies`;
CREATE TABLE IF NOT EXISTS `copies` (
	`copyID`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
	`rID`	INTEGER NOT NULL,
	`keeper`	INTEGER,
	`date`	TEXT
);

DROP TABLE IF EXISTS `book`;
CREATE TABLE IF NOT EXISTS `book` (
	`author`	TEXT,
	`publisher`	TEXT,
	`genre`		TEXT,
	`ISBN`		TEXT,
	`language`	TEXT,
	`rID`		INTEGER,
	PRIMARY KEY (rID),
	FOREIGN KEY (rID) REFERENCES `resource`(`rID`) ON UPDATE CASCADE ON DELETE CASCADE
);
DROP TABLE IF EXISTS `dvd`;
CREATE TABLE IF NOT EXISTS `dvd` (
	`director`	TEXT,
	`runtime`	INTEGER,
	`language`	TEXT,
	`rID`		INTEGER,
	PRIMARY KEY (rID),
	FOREIGN KEY (rID) REFERENCES `resource`(`rID`) ON UPDATE CASCADE ON DELETE CASCADE
);

INSERT INTO `dvd` VALUES ('George Lucas',200,'english',1);
INSERT INTO `dvd` VALUES ('Kubrick',100,'russian',2);

drop table if exists `subtitles`;
create table if not exists `subtitles` (
	`dvdID` integer,
	`subtitleLanguage` TEXT,
	primary key (dvdID,subtitleLanguage),
	foreign key (dvdID) references `dvd`(`rID`) ON UPDATE CASCADE ON DELETE CASCADE
);

INSERT INTO `subtitles` VALUES (1,'romanian');
INSERT INTO `subtitles` VALUES (1,'greek');
INSERT INTO `subtitles` VALUES (2,'welsh');

DROP TABLE IF EXISTS `laptop`;
CREATE TABLE IF NOT EXISTS `laptop` (
	`manufacturer`	TEXT,
	`model`			INTEGER,
	`os`			TEXT,
	`rID`			INTEGER,
	PRIMARY KEY (rID),
	FOREIGN KEY (rID) REFERENCES `resource`(`rID`) ON UPDATE CASCADE ON DELETE CASCADE
);
INSERT INTO `resource` VALUES (0,'Sapiens',1998);
INSERT INTO `book` VALUES ('Noah', 'penguin books', 'non-fiction', '111222333', 'English', 0);


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
	`username`	TEXT NOT NULL,
	`description`	TEXT
);

DROP TABLE IF EXISTS `freeCopies`;
CREATE TABLE IF NOT EXISTS `freeCopies` (
	`copyID` INTEGER,
	`rID` INTEGER,
	PRIMARY KEY (copyID),
	FOREIGN KEY (copyID) REFERENCES `copies` (`copyID`) ON UPDATE CASCADE ON DELETE CASCADE
);
COMMIT;
