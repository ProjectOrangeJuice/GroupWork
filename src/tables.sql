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
	PRIMARY KEY(`username`)
);

INSERT INTO `users` VALUES ('Alexandru','Alex','Dascalu','079999999','3 irrelevant','SA2 8PP',NULL,'0');
INSERT INTO `users` VALUES ('test','testname','testSecondName','12345','1 blabla street','ABC DEF','/SavedAvatars/Avatar1.png','12');
INSERT INTO `users` VALUES ('staff','teststaff','testSecondstaff','56789','2 blabla street','ABB DEE','/SavedAvatars/Avatar1.png','100');

DROP TABLE IF EXISTS `staff`;
CREATE TABLE IF NOT EXISTS `staff` (
	`username`	TEXT,
	`staffId`	INTEGER NOT NULL UNIQUE,
	`employmentDate`	TEXT,
	FOREIGN KEY(`username`) REFERENCES `users`(`username`)
);

INSERT INTO `staff` VALUES ('staff','6237','03/11/2000');

DROP TABLE IF EXISTS `transactions`;
CREATE TABLE IF NOT EXISTS `transactions` (
	`transactionId`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
	`username`	TEXT,
	`paid`	REAL,
	`dateTime`	TEXT,
	FOREIGN KEY(`username`) REFERENCES `users`(`username`)
);
INSERT INTO `transactions` VALUES (1,'test',5.0,"sometime");
INSERT INTO `transactions` VALUES (2,'test',10.0,"othertime");

DROP TABLE IF EXISTS `system`;
CREATE TABLE IF NOT EXISTS `system` (
	`ver`	INTEGER
);
INSERT INTO `system` VALUES (6);

DROP TABLE IF EXISTS `resource`;
CREATE TABLE IF NOT EXISTS `resource` (
	`rID`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`title`	TEXT,
	`year`  INTEGER,
	`thumbnail`	TEXT
);
INSERT INTO `resource` VALUES (1,'Homo Deus',2017,'/graphics/homoDeus.png');
INSERT INTO `resource` VALUES (2,'Iron Man',2008,'/graphics/ironMan.jpg');

DROP TABLE IF EXISTS `fines`;
CREATE TABLE IF NOT EXISTS `fines` (
	`fineID`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
	`username`	TEXT,
	`rID`	INTEGER,
	`daysOver`	INTEGER,
	`amount`	REAL,
	`dateTime`	TEXT,
	`paid`	INTEGER,
	FOREIGN KEY(`username`) REFERENCES `users`(`username`),
	FOREIGN KEY(`rID`) REFERENCES `copies`(`copyID`)
);
INSERT INTO `fines` VALUES (1,"test",1,3,5.0,'hi',1);
INSERT INTO `fines` VALUES (2,"test",2,100,10.0,'always',1);

DROP TABLE IF EXISTS `copies`;
CREATE TABLE IF NOT EXISTS `copies` (
	`copyID`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
	`rID`	INTEGER NOT NULL,
	`keeper`	TEXT,
	`date`	TEXT,
	FOREIGN KEY(`keeper`) REFERENCES `users`(`username`),
	FOREIGN KEY(`rID`) REFERENCES `resource`(`rID`)
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

INSERT INTO `book` VALUES ('Yuval Noah Harari','Harvill Secker','Non-Fiction','978-191-070-187-4','English',1);

DROP TABLE IF EXISTS `dvd`;
CREATE TABLE IF NOT EXISTS `dvd` (
	`director`	TEXT,
	`runtime`	INTEGER,
	`language`	TEXT,
	`rID`		INTEGER,
	PRIMARY KEY (rID),
	FOREIGN KEY (rID) REFERENCES `resource`(`rID`) ON UPDATE CASCADE ON DELETE CASCADE
);

INSERT INTO `dvd` VALUES ('Jon Favreau',126,'english',2);

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

DROP TABLE IF EXISTS `userRequests`;
CREATE TABLE IF NOT EXISTS `userRequests` (
	`rID` INTEGER,
	`userName` TEXT,
	PRIMARY KEY (userName,rID),
	FOREIGN KEY (userName) REFERENCES `users` (`username`) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY (rID) REFERENCES `resource` (`rID`) ON UPDATE CASCADE ON DELETE CASCADE
);

INSERT INTO `userRequests` (rID,userName) VALUES (1,"Alexandru"); 
INSERT INTO `userRequests` (rID,userName) VALUES (2,"test");
COMMIT;
